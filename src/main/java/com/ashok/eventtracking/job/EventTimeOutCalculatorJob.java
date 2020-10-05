package com.ashok.eventtracking.job;

import com.ashok.eventtracking.model.EventLastAccess;
import com.ashok.eventtracking.service.CacheService;
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
import java.util.Date;
import java.util.List;

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
    private CacheService<String, EventLastAccess> cacheService;

    @Value("${event.mapName:eventMap}")
    private String mapName;

    @Value("${event.idleTimeout:3000}")
    private long idleTimeout;
    @Scheduled(fixedRate = 1000)
    public void calculateTimeOutEvents(){


        LOG.debug("EventTimeOutCalculatorJob triggered");

        List<EventLastAccess> values = cacheService.getAllValues(mapName);
        try{
            for(EventLastAccess event : values){
                calculateTimeOut(event);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void calculateTimeOut(EventLastAccess event){
            if(Instant.now().toEpochMilli() - event.getLastAccessTime() > idleTimeout){
                LOG.info("Idle Time out triggered [ip-address={},currentTime={}, lastActivityTime={}]", event.getId(), new Date(), new Date(event.getLastAccessTime()));
                cacheService.remove(mapName,event.getId());
            }
    }
}
