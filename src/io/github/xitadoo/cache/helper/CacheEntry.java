package io.github.xitadoo.cache.helper;

public class CacheEntry<V> {
    private final V value;
    private final long creationTime;

    public CacheEntry(V value) {
        this.value = value;
        this.creationTime = System.currentTimeMillis();
    }

    public V getValue() {
        return value;
    }

    public long getCreationTime() {
        return creationTime;
    }
}

