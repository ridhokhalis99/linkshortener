package com.ridhokhalis.linkshortener.repository;

import com.ridhokhalis.linkshortener.model.ShortLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortLinkRepository extends JpaRepository<ShortLink, String> {
    Optional<ShortLink> findByOriginalUrl(String originalUrl);
}
