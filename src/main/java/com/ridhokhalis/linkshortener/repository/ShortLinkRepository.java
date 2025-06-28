package com.ridhokhalis.linkshortener.repository;

import com.ridhokhalis.linkshortener.entity.ShortLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortLinkRepository extends JpaRepository<ShortLinkEntity, String> {
    Optional<ShortLinkEntity> findByOriginalUrl(String originalUrl);
    Optional<ShortLinkEntity> findByShortCode(String shortCode);
}
