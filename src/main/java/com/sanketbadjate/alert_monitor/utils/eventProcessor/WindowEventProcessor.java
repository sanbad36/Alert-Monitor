package com.sanketbadjate.alert_monitor.utils.eventProcessor;


import com.sanketbadjate.alert_monitor.models.Event;
import com.sanketbadjate.alert_monitor.services.repositoryServices.EventsRepositoryService;
import com.sanketbadjate.alert_monitor.utils.config.WindowAlertConfig;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class WindowEventProcessor extends EventProcessor {
    public WindowAlertConfig config;

    public WindowEventProcessor(WindowAlertConfig config, EventsRepositoryService eventsRepositoryService) {
        super(eventsRepositoryService);
        this.config = config;
    }

    @Override
    public void processEvent(Event event) {
        eventsRepositoryService.saveEventToTime(event);
    }
}
