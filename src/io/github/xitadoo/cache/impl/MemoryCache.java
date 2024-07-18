package io.github.xitadoo.cache.impl;

import io.github.xitadoo.cache.exceptions.CacheException;
import io.github.xitadoo.cache.helper.CacheEntry;
import io.github.xitadoo.cache.interfaces.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MemoryCache<K, V> implements Cache<K, V> {

    private final Map<K, CacheEntry<V>> cache;
    private final long expirationTimeMillis;
    private Timer timer;

    public MemoryCache(long expirationTimeMillis) {
        this.cache = new HashMap<>();
        this.expirationTimeMillis = expirationTimeMillis;
        this.timer = new Timer(true);

        if (expirationTimeMillis > 0) {
            this.timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    cleanupExpiredEntries();
                }
            }, expirationTimeMillis, expirationTimeMillis);
        } else {
            this.timer = null;
        }
    }

    private MemoryCache() {
        this.cache = new HashMap<>();
        this.expirationTimeMillis = -1;
        this.timer = null;
    }

    @Override
    public V get(K key) {
        CacheEntry<V> entry = cache.get(key);
        if (entry != null) {
            if (expirationTimeMillis > 0 && isEntryExpired(entry)) {
                cache.remove(key);
                throw new CacheException("Entry with key '" + key + "' has expired and was removed from cache.");
            }
            return entry.getValue();
        }
        throw new CacheException("Entry with key '" + key + "' not found in cache.");
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, new CacheEntry<>(value));
    }

    @Override
    public void remove(K key) {
        if (cache.remove(key) == null) {
            throw new CacheException("Entry with key '" + key + "' not found in cache.");
        }
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    private void cleanupExpiredEntries() {
        long now = System.currentTimeMillis();
        cache.entrySet().removeIf(entry -> isEntryExpired(entry.getValue()));
    }

    private boolean isEntryExpired(CacheEntry<V> entry) {
        return expirationTimeMillis > 0 && (System.currentTimeMillis() - entry.getCreationTime()) > expirationTimeMillis;
    }
}
