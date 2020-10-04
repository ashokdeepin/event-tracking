package com.ashok.eventtracking.model;


import java.io.Serializable;

/**
 * RedisEventLastAccess entity representation (stored in redis data store)
 *
 * @author ashok
 * 03/10/20
 */

public class EventLastAccess implements Serializable {

    private String id;
    private long lastAccessTimeStamp;

    public EventLastAccess() {
    }

    public EventLastAccess(String id, long lastAccessTime) {
        this.id = id;
        this.lastAccessTimeStamp = lastAccessTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getLastAccessTime() {
        return lastAccessTimeStamp;
    }

    public void setLastAccessTime(long lastAccessTimeStamp) {
        this.lastAccessTimeStamp = lastAccessTimeStamp;
    }

    @Override
    public String toString() {
        return "RedisEventLastAccess{" +
                "id='" + id + '\'' +
                ", lastAccessTimeStamp=" + lastAccessTimeStamp +
                '}';
    }
}
