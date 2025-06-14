package org.example.config;

import jakarta.annotation.Nullable;
import org.springframework.data.redis.cache.CacheStatistics;
import org.springframework.data.redis.cache.CacheStatisticsCollector;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;

public class LoggingRedisCacheWriter implements RedisCacheWriter {

    private final RedisCacheWriter delegate;

    public LoggingRedisCacheWriter(RedisCacheWriter delegate) {
        this.delegate = delegate;
    }

    @Override
    public void put(String name, byte[] key, byte[] value, @Nullable Duration ttl) {
        System.out.println("Cache PUT - Cache: " + name + ", Key: " + new String(key));
        delegate.put(name, key, value, ttl);
    }

    @Override
    public byte[] get(String name, byte[] key) {
        System.out.println("Cache GET - Cache: " + name + ", Key: " + new String(key));
        return delegate.get(name, key);
    }

    @Override
    public byte[] putIfAbsent(String name, byte[] key, byte[] value, @Nullable Duration ttl) {
        return delegate.putIfAbsent(name, key, value, ttl);
    }

    @Override
    public void remove(String name, byte[] key) {
        System.out.println("Cache REMOVE - Cache: " + name + ", Key: " + new String(key));
        delegate.remove(name, key);
    }

    @Override
    public void clean(String name, byte[] pattern) {
        delegate.clean(name, pattern);
    }

    @Override
    public void clearStatistics(String name) {
        delegate.clearStatistics(name);
    }

    @Override
    public RedisCacheWriter withStatisticsCollector(CacheStatisticsCollector cacheStatisticsCollector) {
        return delegate.withStatisticsCollector(cacheStatisticsCollector);
    }

    @Override
    public CacheStatistics getCacheStatistics(String cacheName) {
        return delegate.getCacheStatistics(cacheName);
    }
}
