package com.ashok.eventtracking.service;

/**
 * @author ashok
 * 04/10/20
 */
public interface MessageService<M> {

    public void send(String topic, M message);
}
