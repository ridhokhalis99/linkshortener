package com.ridhokhalis.linkshortener;

import com.ridhokhalis.linkshortener.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class LinkshortenerApplication {
	public static void main(String[] args) {
		SpringApplication.run(LinkshortenerApplication.class, args);
	}

}
