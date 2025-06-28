package com.ridhokhalis.linkshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AnalyticsEvent {
    private String shortCode;
    private LocalDateTime timestamp;
    private String ipAddress;
    private String userAgent;
    private String referrer;
}
