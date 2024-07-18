package io.github.xitadoo.cache.interfaces;

public interface Cache<K, V> {
    V get(K key);
    void put(K key, V value);
    void remove(K key);
    void clear();
    boolean containsKey(K key);
}
