package com.ridhokhalis.linkshortener.service;

import com.ridhokhalis.linkshortener.dto.AnalyticsEvent;
import com.ridhokhalis.linkshortener.entity.ShortLinkEntity;
import com.ridhokhalis.linkshortener.repository.ShortLinkRepository;
import com.ridhokhalis.linkshortener.kafka.AnalyticsProducer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShortLinkService {

    private final ShortLinkRepository repository;
    private final AnalyticsProducer analyticsProducer;

    public ShortLinkEntity createShortLink(String originalUrl) {
        Optional<ShortLinkEntity> existing = repository.findByOriginalUrl(originalUrl);
        if (existing.isPresent()) return existing.get();

        String shortCode = generateShortCode();

        ShortLinkEntity link = ShortLinkEntity.builder()
                .shortCode(shortCode)
                .originalUrl(originalUrl)
                .createdAt(LocalDateTime.now())
                .build();

        return repository.save(link);
    }

    public String resolveShortCode(String shortCode, HttpServletRequest request) {
        ShortLinkEntity link = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "URL not found"));

        AnalyticsEvent event = new AnalyticsEvent(
                shortCode,
                LocalDateTime.now(),
                request.getRemoteAddr(),
                request.getHeader("User-Agent"),
                request.getHeader("Referer")
        );

        analyticsProducer.sendAccessEvent(event);

        return link.getOriginalUrl();
    }

    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
