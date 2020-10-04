package com.ashok.eventtracking.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Event entity representation
 *
 * @author ashok
 * 03/10/20
 */
@Entity
@Table(name = "events")
public class Event implements Serializable {
    private long id;
    private String ipAddress;
    private String requestMethod;
    private Date requestTime;


    public Event() {
    }

    public Event(String ipAddress, String requestMethod, Date requestTime) {
        this.ipAddress = ipAddress;
        this.requestMethod = requestMethod;
        this.requestTime = requestTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name="ip_address", nullable = false)
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Column(name = "request_method", nullable = false)
    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    @Column(name = "request_time", nullable = false)
    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", ipAddress='" + ipAddress + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", requestTime=" + requestTime +
                '}';
    }
}
