package com.ashok.eventtracking.service;

import java.util.List;

/**
 * Cache service interface.
 *
 * @author ashok
 * 04/10/20
 */
public interface CacheService<K,V> {

    public void put(String mapName, K key, V value, int ttl);

    public V get(String mapName, K key);

    public List<V> getAllValues(String mapName);

    public V remove(String mapName, K key);

}
