package com.ashok.eventtracking.listeners;

import com.ashok.eventtracking.model.Event;
import com.ashok.eventtracking.model.RedisEventLastAccess;
import com.ashok.eventtracking.service.EventService;
import com.ashok.eventtracking.service.RedisCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

/**
 * Kafka consumer, which pulls the events from the topic and process the event to publish the last access time to
 * redis data store which will be used by EventTimeOutCalculatorJob to calculate the idle time out clients. It also persists
 * the events to database.
 *
 * @author ashok
 * 03/10/20
 */
@Component
public class EventKafkaListener {
    private final Logger LOG = LoggerFactory.getLogger(EventKafkaListener.class);

    @Autowired
    private RedisCacheService redisCacheService;

    @Autowired
    private EventService eventService;

    @KafkaListener(topics = "application.events", groupId = "eventGroup")
    void eventListener(Event event){
        LOG.info("message received :[{}]", event);
        RedisEventLastAccess eventLastAccess = new RedisEventLastAccess(event.getIpAddress(), Instant.now().toEpochMilli());
        redisCacheService.putInMap("eventMap", eventLastAccess, 6);
        eventService.addEvent(event);
    }
}
