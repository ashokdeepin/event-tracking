package com.ashok.eventtracking.service;


import org.redisson.api.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Service that handles Redis related activities/functionalities.
 *
 * @author ashok
 * 04/10/20
 */
@Service
@Profile(value = "distributed")
public class RedisCacheService<K,V> implements CacheService<K,V> {

    private Logger LOG = LoggerFactory.getLogger(RedisCacheService.class);

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void put(String mapName, K key, V value, int ttlInSecs){
        LOG.debug("putInMap called for map={}, value={}, ttl={}", mapName, value,ttlInSecs);
        RMapCache<K,V> map = redissonClient.getMapCache(mapName);
        RReadWriteLock rReadWriteLock = map.getReadWriteLock(key);
        rReadWriteLock.writeLock().lock();
        try{
            map.fastPut(key, value, ttlInSecs, TimeUnit.SECONDS);
        } finally {
            rReadWriteLock.writeLock().unlock();
        }
    }


    @Override
    public V get(String mapName, K key) {
        LOG.debug("getFromMap called for map={}, key={}", mapName, key);
        V value = null;
        RMapCache<K,V> map = redissonClient.getMapCache(mapName);
        RReadWriteLock rReadWriteLock = map.getReadWriteLock(key);
        rReadWriteLock.readLock().lock();
        try{
            value = map.get(key);
        }finally {
            rReadWriteLock.readLock().unlock();
        }
        return value;
    }

    @Override
    public List<V> getAllValues(String mapName) {
        LOG.debug("getAllValues called for map={}", mapName);
        List<V> values = null;
        RMapCache<K,V> map = redissonClient.getMapCache(mapName);
        values = new ArrayList<>( map.values());
        return values;
    }

    @Override
    public V remove(String mapName, K key) {
        LOG.debug("remove called for map={}, key={}", mapName, key);
        V value = null;
        RMapCache<K,V> map = redissonClient.getMapCache(mapName);
        RReadWriteLock rReadWriteLock = map.getReadWriteLock(key);
        rReadWriteLock.readLock().lock();
        try{
            value = map.remove(key);
        }finally {
            rReadWriteLock.readLock().unlock();
        }
        return value;
    }


}
