package com.example.sklep.product.cache;

public interface EvictPolicy<K, V> {
    K evict();

    void put(K key, V value);

    void updateTime(K key);
}
