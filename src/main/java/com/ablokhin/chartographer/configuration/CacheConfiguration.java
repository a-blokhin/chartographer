package com.ablokhin.chartographer.configuration;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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
                                    if (value instanceof byte[]) {
                                        return ((byte[]) value).length;
                                    }
                                    throw new IllegalStateException("Using array size is only supported for byte arrays"); //$NON-NLS-1$
                                })
                                .maximumWeight(MAX_CACHE_SIZE)
                                .build().asMap(),
                        false);
            }
        };
    }

}