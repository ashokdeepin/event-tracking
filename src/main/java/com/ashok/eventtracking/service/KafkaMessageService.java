package com.ashok.eventtracking.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ashok
 * 04/10/20
 */
@Service
@Profile(value = "distributed")
public class KafkaMessageService<M> implements MessageService<M>{

    private final Logger LOG = LoggerFactory.getLogger(KafkaMessageService.class);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public void send(String topic, M message) {
        kafkaTemplate.send(topic, message);
    }
}
