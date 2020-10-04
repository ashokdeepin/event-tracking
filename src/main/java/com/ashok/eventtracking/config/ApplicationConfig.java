package com.ashok.eventtracking.config;

import com.ashok.eventtracking.model.Event;
import com.ashok.eventtracking.model.EventLastAccess;
import com.ashok.eventtracking.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author ashok
 * 04/10/20
 */
@Configuration
public class ApplicationConfig {

    @Bean
    @Profile(value = "dev")
    public CacheService<String, EventLastAccess> getLocalCacheService(){
        return new LocalCacheService();
    }

    @Bean
    @Profile(value = "distributed")
    public CacheService<String, EventLastAccess> getDistributedCacheService(){
        return new RedisCacheService();
    }

    @Bean
    @Profile(value = "dev")
    public MessageService<Event> getLocalMessageService(){
        return new JMSMessageService();
    }

    @Bean
    @Profile(value = "distributed")
    public MessageService<Event> getDistributedMessageService(){
        return new KafkaMessageService();
    }
}
