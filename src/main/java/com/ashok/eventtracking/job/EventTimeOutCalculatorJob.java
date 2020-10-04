package com.ashok.eventtracking.job;

import com.ashok.eventtracking.model.RedisEventLastAccess;
import com.ashok.eventtracking.service.RedisCacheService;
import org.redisson.api.RKeys;
import org.redisson.api.RMapCache;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;

/**
 * Job scheduled every second to check if there are any events in redis central data store which crossed
 * the configured idleTimeout and evict them from the redis data store. We log the idle timeout message for that client.
 *
 * @author ashok
 * 04/10/20
 */
@Configuration
@EnableScheduling
public class EventTimeOutCalculatorJob {

    private final Logger LOG = LoggerFactory.getLogger(EventTimeOutCalculatorJob.class);

    @Autowired
    private RedissonClient redissonClient;

    @Value("${event.mapName:eventMap}")
    private String mapName;

    @Value("${event.idleTimeout:3000}")
    private long idleTimeout;
    @Scheduled(fixedRate = 1000)
    public void calculateTimeOutEvents(){


        LOG.debug("EventTimeOutCalculatorJob triggered");

        RMapCache<String, RedisEventLastAccess> map = redissonClient.getMapCache(mapName);
        RReadWriteLock rReadWriteLock = map.getReadWriteLock(mapName);
        rReadWriteLock.readLock().lock();
        try{
            for(RedisEventLastAccess event : map.readAllValues()){
                calculateTimeOut(event, map);
            }
        }finally {
            rReadWriteLock.readLock().unlock();
        }
    }

    private void calculateTimeOut(RedisEventLastAccess event, RMapCache<String, RedisEventLastAccess> map){
            if(Instant.now().toEpochMilli() - event.getLastAccessTime() > idleTimeout){
                LOG.info("Idle Time out triggered for ipAddress={}", event.getId());
                map.remove(event.getId());
            }
    }
}
