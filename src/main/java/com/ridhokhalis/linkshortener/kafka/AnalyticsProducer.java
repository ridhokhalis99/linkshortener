package com.ridhokhalis.linkshortener.kafka;

import org.springframework.stereotype.Component;

@Component
public class AnalyticsProducer {

    public void sendAccessEvent(String shortCode) {
        System.out.println("[Kafka placeholder] Access event for shortCode: " + shortCode);
    }
}
