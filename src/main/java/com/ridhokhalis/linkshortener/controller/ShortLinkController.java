package com.ridhokhalis.linkshortener.controller;

import com.ridhokhalis.linkshortener.config.AppProperties;
import com.ridhokhalis.linkshortener.model.ShortLink;
import com.ridhokhalis.linkshortener.service.ShortLinkService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortLinkService shortLinkService;
    private final AppProperties appProperties;

    @PostMapping("/links")
    public ResponseEntity<Map<String, String>> createShortLink(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        ShortLink link = shortLinkService.createShortLink(originalUrl);
        return ResponseEntity.ok(Map.of(
                "shortCode", link.getShortCode(),
                "shortUrl", appProperties.getBaseUrl() + link.getShortCode()
        ));
    }
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(
            @PathVariable String shortCode,
            HttpServletRequest request
    ) {
        String originalUrl = shortLinkService.resolveShortCode(shortCode, request);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
