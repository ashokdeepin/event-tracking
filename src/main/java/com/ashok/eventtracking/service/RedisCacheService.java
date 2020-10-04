package com.ashok.eventtracking.service;

import com.ashok.eventtracking.model.RedisEventLastAccess;
import org.redisson.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Service that handles Redis related activities/functionalities.
 *
 * @author ashok
 * 04/10/20
 */
@Service
public class RedisCacheService {

    private Logger LOG = LoggerFactory.getLogger(RedisCacheService.class);

    @Autowired
    private RedissonClient redissonClient;

    public void putInMap(String mapName, RedisEventLastAccess eventLastAccess, int ttlInSecs){
        LOG.debug("putInMap called for map={}, eventLastAccess={}, ttl={}", mapName, eventLastAccess,ttlInSecs);
        RMapCache<String,RedisEventLastAccess> map = redissonClient.getMapCache(mapName);
        RReadWriteLock rReadWriteLock = map.getReadWriteLock(eventLastAccess.getId());
        rReadWriteLock.writeLock().lock();
        try{
            map.fastPutIfAbsent(eventLastAccess.getId(), eventLastAccess, ttlInSecs, TimeUnit.SECONDS);
        } finally {
            rReadWriteLock.writeLock().unlock();
        }
    }

    public RedisEventLastAccess getFromMap(String mapName, String key) {
        LOG.debug("getFromMap called for map={}, key={}", mapName, key);
        RedisEventLastAccess eventLastAccess = null;
        RMapCache<String,RedisEventLastAccess> map = redissonClient.getMapCache(mapName);
        RReadWriteLock rReadWriteLock = map.getReadWriteLock(key);
        rReadWriteLock.readLock().lock();
        try{
            eventLastAccess = map.get(key);
        }finally {
            rReadWriteLock.readLock().unlock();
        }
        return eventLastAccess;
    }
}
