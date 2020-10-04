package com.ashok.eventtracking.service;

import com.ashok.eventtracking.model.Event;
import com.ashok.eventtracking.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to handle Event related activitied/functionalities.
 *
 * @author ashok
 * 04/10/20
 */
@Service
public class EventService {

    private Logger LOG = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventRepository eventRepository;

    public Event addEvent(Event event){
        LOG.debug("addEvent called for [{}]",event);
        return eventRepository.save(event);
    }

    public Event getEventById(long id){
        LOG.debug("getEventById called for id : {}", id);
        return eventRepository.findById(id).get();
    }
}
