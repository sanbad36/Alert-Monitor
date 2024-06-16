package com.sanketbadjate.alert_monitor.services;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanketbadjate.alert_monitor.constants.ApplicationConstants;
import com.sanketbadjate.alert_monitor.constants.LoggerConstants;
import com.sanketbadjate.alert_monitor.enums.AlertStrategy;
import com.sanketbadjate.alert_monitor.models.Event;
import com.sanketbadjate.alert_monitor.services.repositoryServices.EventsRepositoryService;
import com.sanketbadjate.alert_monitor.utils.config.ServiceEventAlertConfig;
import com.sanketbadjate.alert_monitor.utils.eventProcessor.EventProcessor;
import com.sanketbadjate.alert_monitor.utils.eventProcessor.EventProcessorFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MonitoringService implements LoggerService {
    private static volatile MonitoringService INSTANCE;
    private final AlertingService alertingService;
    private final EventsRepositoryService eventsRepositoryService;

    File file;
    ObjectMapper mapper;
    List<ServiceEventAlertConfig> serviceEventAlertConfig ;
    Map<Event, ServiceEventAlertConfig> eventConfigMap = new HashMap<>();

    public static MonitoringService getInstance(EventsRepositoryService eventsRepositoryService, AlertingService alertingService) throws IOException {
        if (INSTANCE == null) {
            synchronized (MonitoringService.class) {
                if (INSTANCE == null)
                    INSTANCE = new MonitoringService(eventsRepositoryService, alertingService);
            }
        }
        return INSTANCE;
    }

    private MonitoringService(EventsRepositoryService eventsRepositoryService, AlertingService alertingService) throws IOException {
        this.eventsRepositoryService = eventsRepositoryService;
        this.alertingService = alertingService;

        // read configuration from json configuration
//        file = new File(System.getProperty(ApplicationConstants.USER_DIRECTORY) + ApplicationConstants.SERVICE_EVENT_ALERT_CONFIG_PATH);
        file = new File(ApplicationConstants.USER_DIRECTORY + ApplicationConstants.SERVICE_EVENT_ALERT_CONFIG_PATH);

        mapper = new ObjectMapper();
        serviceEventAlertConfig = mapper.readValue(file, new TypeReference<>(){});

        // store config corresponding to specific event from a specific client
        for (ServiceEventAlertConfig config : serviceEventAlertConfig) {
            eventConfigMap.put(Event.builder().client(config.getClient()).eventType(config.getEventType()).build(), config);
            if (AlertStrategy.TUMBLING_WINDOW.equals(config.getAlertConfig().getType())) {
                // TODO: implement this
                // register CRON which tumbling window for config.getAlertConfig().getClient() + config.getAlertConfig().getType()
                // this logs start and end logs to make it look exactly like sample output
            }
        }
    }

    public Boolean shouldTriggerAlert(EventProcessor eventProcessor, Event event, ServiceEventAlertConfig config) {
        Boolean isBreached = eventProcessor.isThresholdReached(event);
        if (isBreached)
            logInfo(String.format(LoggerConstants.THRESHOLD_BREACHED, config.getClient().name(), config.getEventType().name()));
        return isBreached;
    }

    public void processEvent(Event event) {
        ServiceEventAlertConfig config = eventConfigMap.getOrDefault(event, null);
        if (Objects.isNull(config)) {
            System.out.println("No Configuration found for event: " + event);
            return;
        }

        EventProcessor eventProcessor = EventProcessorFactory.getEventProcessor(config.getAlertConfig(), eventsRepositoryService);
        eventProcessor.processEvent(event);

        if (shouldTriggerAlert(eventProcessor, event, config)) {
            alertingService.triggerAlert(config);
        }
    }
}
