package com.sanketbadjate.alert_monitor.utils.config;

import com.sanketbadjate.alert_monitor.models.Event;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Map<Event, ServiceEventAlertConfig> eventConfigMap() {
        return new HashMap<>();
    }
}