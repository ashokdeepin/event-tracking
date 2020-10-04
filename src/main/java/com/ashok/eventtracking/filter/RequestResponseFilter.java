package com.ashok.eventtracking.filter;

import com.ashok.eventtracking.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Filter to intercept the Request and Response of a request and publish the event message to Kafka topic.
 *
 * @author ashok
 * 03/10/20
 */
@Component
@Order(1)
public class RequestResponseFilter implements Filter {

    private final Logger LOG = LoggerFactory.getLogger(RequestResponseFilter.class);

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        LOG.info(
                "Logging Request  {} : {}", req.getMethod(),
                req.getRequestURI());
        kafkaTemplate.send("application.events",constructEventObject(req));
        filterChain.doFilter(servletRequest, servletResponse);
        LOG.info(
                "Logging Response :{}",
                res.getContentType());
    }

    private Event constructEventObject(HttpServletRequest request){
        Event event = new Event(request.getRemoteAddr(), request.getMethod(), new Date());
        return event;
    }
}
