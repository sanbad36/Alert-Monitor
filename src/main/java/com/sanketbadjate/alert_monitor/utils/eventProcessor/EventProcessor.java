package com.sanketbadjate.alert_monitor.utils.eventProcessor;


import com.sanketbadjate.alert_monitor.models.Event;
import com.sanketbadjate.alert_monitor.services.repositoryServices.EventsRepositoryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class EventProcessor {
    public EventsRepositoryService eventsRepositoryService;

    public EventProcessor(EventsRepositoryService eventsRepositoryService) {
        this.eventsRepositoryService = eventsRepositoryService;
    }

    public abstract Boolean isThresholdReached(Event event);

    public abstract void processEvent(Event event);
}
