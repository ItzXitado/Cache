package io.github.xitadoo.cache;

import io.github.xitadoo.cache.impl.MemoryCache;

public class CacheBuilder<K, V> {

    private long expirationTimeMillis = -1;

    public CacheBuilder<K, V> expirationTime(long expirationTimeMillis) {
        this.expirationTimeMillis = expirationTimeMillis;
        return this;
    }

    public MemoryCache<K, V> build() {
        return new MemoryCache<>(expirationTimeMillis);
    }
}
