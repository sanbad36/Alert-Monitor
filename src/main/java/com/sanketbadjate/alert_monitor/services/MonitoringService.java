package com.sanketbadjate.alert_monitor.services;

import com.sanketbadjate.alert_monitor.constants.LoggerConstants;
import com.sanketbadjate.alert_monitor.enums.AlertStrategy;
import com.sanketbadjate.alert_monitor.models.Event;
import com.sanketbadjate.alert_monitor.services.repositoryServices.EventsRepositoryService;
import com.sanketbadjate.alert_monitor.utils.config.ConfigUpdater;
import com.sanketbadjate.alert_monitor.utils.config.ServiceEventAlertConfig;
import com.sanketbadjate.alert_monitor.utils.eventProcessor.EventProcessor;
import com.sanketbadjate.alert_monitor.utils.eventProcessor.EventProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
public class MonitoringService implements LoggerService {
    private final AlertingService alertingService;
    private final EventsRepositoryService eventsRepositoryService;
    private final Map<Event, ServiceEventAlertConfig> eventConfigMap;

    @Autowired
    private ConfigUpdater configUpdater;

    public MonitoringService(EventsRepositoryService eventsRepositoryService, AlertingService alertingService, Map<Event, ServiceEventAlertConfig> eventConfigMap) {
        this.eventsRepositoryService = eventsRepositoryService;
        this.alertingService = alertingService;
        this.eventConfigMap = eventConfigMap;
        configUpdater = new ConfigUpdater(new RestTemplate() , eventConfigMap);
        // Initialize or update configuration from GitHub during service initialization
        updateConfigFromGitHub();
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

    public void updateConfigFromGitHub() {
        try {
            // Use ConfigUpdater to update configuration from GitHub
            configUpdater.updateConfigFromGithub();
        } catch (Exception e) {
            // Handle exceptions appropriately (logging, throwing, etc.)
            System.out.println("Failed to update configuration from GitHub: " + e.getMessage());
        }
    }
}
