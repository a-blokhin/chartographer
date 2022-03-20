package com.ablokhin.chartographer.configuration;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.image.BufferedImage;


@Configuration
public class CacheConfiguration {

    private static final long MAX_CACHE_SIZE = (long) 1024 * 1024 * 1024; // 1GB

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(String name) {

                return new ConcurrentMapCache(
                        name,
                        CacheBuilder.newBuilder()
                                .weigher((key, value) -> {
                                    if (value instanceof BufferedImage) {
                                        return ((BufferedImage) value).getWidth() * ((BufferedImage) value).getHeight() * 3;
                                    }
                                    throw new IllegalStateException("This cache is only supported for byte arrays");
                                })
                                .maximumWeight(MAX_CACHE_SIZE)
                                .build().asMap(),
                        false);
            }
        };
    }

}