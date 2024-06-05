package com.AchintyaNigam.demo.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.jcache.JCacheCacheManager;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {

    @Bean
    @Override
    public CacheManager cacheManager() {
        URI uri = null;
        try {
            uri = getClass().getResource("/ehcache.xml").toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        CachingProvider cachingProvider = Caching.getCachingProvider();
        javax.cache.CacheManager cacheManager = cachingProvider.getCacheManager(uri, getClass().getClassLoader());
        return new JCacheCacheManager(cacheManager);
    }

    @Bean
    public KeyGenerator customKeyGenerator() {
        return new MyCustomKeyGenerator();
    }
}




