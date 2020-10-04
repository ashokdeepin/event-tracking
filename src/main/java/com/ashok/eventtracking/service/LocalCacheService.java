package com.ashok.eventtracking.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ashok
 * 04/10/20
 */
@Service
@Profile(value = "dev")
public class LocalCacheService<K,V> implements  CacheService<K,V>{

    private final Logger LOG = LoggerFactory.getLogger(LocalCacheService.class);



    private Map<String,Map<K,V>> localCache = new ConcurrentHashMap<>();

    private Lock lock = new ReentrantLock();

    @Override
    public void put(String mapName, K key, V value, int ttl) {
        try{
            lock.lock();
            if(localCache.containsKey(mapName)) {
                Map<K, V> map = localCache.get(mapName);
                map.putIfAbsent(key, value);
            }else{
                Map<K,V> map = new HashMap<>();
                map.put(key, value);
                localCache.put(mapName, map);
            }
        }catch (Exception ie){
            ie.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    @Override
    public List<V> getAllValues(String mapName) {
        try{
            lock.lock();
            if(localCache.containsKey(mapName)){
                List<V> values = new ArrayList<>(localCache.get(mapName).values());
                return values;
            }
        }finally {
            lock.unlock();
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public V remove(String mapName, K key) {
        try{
            lock.lock();
            if(localCache.containsKey(mapName)){
                Map<K,V> map = localCache.get(mapName);
                return map.remove(key);
            }
        }finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public Object get(String mapName, Object key) {
        try{
            lock.lock();
            if(localCache.containsKey(mapName)){
                Map<K,V> map = localCache.get(mapName);
                return map.get(key);
            }
        }finally {
            lock.unlock();
        }
        return null;
    }
}
