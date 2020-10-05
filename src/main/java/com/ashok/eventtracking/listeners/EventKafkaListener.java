package com.ashok.eventtracking.listeners;

import com.ashok.eventtracking.model.Event;
import com.ashok.eventtracking.model.EventLastAccess;
import com.ashok.eventtracking.service.CacheService;
import com.ashok.eventtracking.service.EventService;

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
    private CacheService<String, EventLastAccess> cacheService;

    @Autowired
    private EventService eventService;

    @KafkaListener(topics = "application.events", groupId = "eventGroup")
    void eventListener(Event event){
        LOG.info("[kafka] Client activity received :[ip-address={},activityTime={}]", event.getIpAddress(), event.getRequestTime());
        EventLastAccess eventLastAccess = new EventLastAccess(event.getIpAddress(), Instant.now().toEpochMilli());
        cacheService.put("eventMap", eventLastAccess.getId(), eventLastAccess, 6);
        eventService.addEvent(event);
    }
}
