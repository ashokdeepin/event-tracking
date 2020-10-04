package com.ashok.eventtracking.listeners;

import com.ashok.eventtracking.model.Event;
import com.ashok.eventtracking.model.EventLastAccess;
import com.ashok.eventtracking.service.CacheService;
import com.ashok.eventtracking.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Session;
import java.time.Instant;

/**
 * @author ashok
 * 04/10/20
 */
@Component
public class JMSListener {

    private final Logger LOG = LoggerFactory.getLogger(JMSListener.class);

    @Autowired
    private CacheService<String, EventLastAccess> cacheService;

    @Autowired
    private EventService eventService;

    @JmsListener(destination = "${jms.message.endpoint:jmsEventQueue}")
    public void receiveMessage(@Payload Event event, @Headers MessageHeaders headers, Message message, Session session){
        LOG.info("message received from jms :[{}]", event);
        EventLastAccess eventLastAccess = new EventLastAccess(event.getIpAddress(), Instant.now().toEpochMilli());
        cacheService.put("eventMap", eventLastAccess.getId(), eventLastAccess, 6);
        eventService.addEvent(event);
    }
}
