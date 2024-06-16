package com.sanketbadjate.alert_monitor.utils.eventProcessor;


import com.sanketbadjate.alert_monitor.services.repositoryServices.EventsRepositoryService;
import com.sanketbadjate.alert_monitor.utils.config.AlertConfig;
import com.sanketbadjate.alert_monitor.utils.config.WindowAlertConfig;

public class EventProcessorFactory {
    public static EventProcessor getEventProcessor(AlertConfig config, EventsRepositoryService eventsRepositoryService) {
        switch (config.getType()) {
            case SIMPLE_COUNT -> {
                return new SimpleEventProcessor(config, eventsRepositoryService);
            }
            case SLIDING_WINDOW -> {
                return new SlidingWindowEventProcessor((WindowAlertConfig) config, eventsRepositoryService);
            }
            case TUMBLING_WINDOW -> {
                return new TumblingWindowEventProcessor((WindowAlertConfig) config, eventsRepositoryService);
            }
            default -> throw new RuntimeException("No EventProcessor found for type " + config.getType());
        }
    }
}
