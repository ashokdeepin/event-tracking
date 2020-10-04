package com.ashok.eventtracking.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ashok
 * 04/10/20
 */
@Service
@Profile(value = "dev")
public class JMSMessageService<M> implements MessageService<M>{

    private final Logger LOG = LoggerFactory.getLogger(JMSMessageService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${jms.message.endpoint:jmsEventQueue}")
    private String jmsEndPoint;

    @Override
    public void send(String topic, M message) {
        jmsTemplate.convertAndSend(jmsEndPoint, message);
    }
}
