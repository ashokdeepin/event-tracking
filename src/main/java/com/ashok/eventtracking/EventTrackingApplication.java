package com.ashok.eventtracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class EventTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventTrackingApplication.class, args);
    }

}
