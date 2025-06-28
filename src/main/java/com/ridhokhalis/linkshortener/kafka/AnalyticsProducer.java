package com.ridhokhalis.linkshortener.kafka;

import com.ridhokhalis.linkshortener.dto.AnalyticsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnalyticsProducer {

    private final KafkaTemplate<String, AnalyticsEvent> kafkaTemplate;

    private static final String TOPIC = "analytics-events";

    public void sendAccessEvent(AnalyticsEvent event) {
        kafkaTemplate.send(TOPIC, event.getShortCode(), event);
    }
}
