package com.ridhokhalis.linkshortener.service;

import com.ridhokhalis.linkshortener.model.ShortLink;
import com.ridhokhalis.linkshortener.repository.ShortLinkRepository;
import com.ridhokhalis.linkshortener.kafka.AnalyticsProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShortLinkService {

    private final ShortLinkRepository repository;
    private final AnalyticsProducer analyticsProducer;

    public ShortLink createShortLink(String originalUrl) {
        Optional<ShortLink> existing = repository.findByOriginalUrl(originalUrl);
        if (existing.isPresent()) return existing.get();

        String shortCode = generateShortCode();

        ShortLink link = ShortLink.builder()
                .shortCode(shortCode)
                .originalUrl(originalUrl)
                .createdAt(LocalDateTime.now())
                .build();

        return repository.save(link);
    }

    public String resolveShortCode(String shortCode) {
        ShortLink link = repository.findById(shortCode)
                .orElseThrow(() -> new RuntimeException("Short code not found"));

        analyticsProducer.sendAccessEvent(shortCode);

        return link.getOriginalUrl();
    }

    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
