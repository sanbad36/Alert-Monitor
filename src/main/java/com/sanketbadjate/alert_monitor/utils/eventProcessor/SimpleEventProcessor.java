package com.sanketbadjate.alert_monitor.utils.eventProcessor;


import com.sanketbadjate.alert_monitor.models.Event;
import com.sanketbadjate.alert_monitor.services.repositoryServices.EventsRepositoryService;
import com.sanketbadjate.alert_monitor.utils.config.AlertConfig;

public class SimpleEventProcessor extends EventProcessor {
    AlertConfig config;
    public SimpleEventProcessor(AlertConfig config, EventsRepositoryService eventsRepositoryService) {
        super(eventsRepositoryService);
        this.config = config;
    }

    @Override
    public Boolean isThresholdReached(Event event) {
//        System.out.println("** current count :" + eventsRepositoryService.countAllOccurrences(event));
//        System.out.println("*** config count" + config.getCount());
        Boolean thresholdExceeded = eventsRepositoryService.countAllOccurrences(event) > config.getCount();
        if (thresholdExceeded) {
            reset(event);
        }
        return thresholdExceeded;
    }

    private void reset(Event event) {
        // TODO: have some reset logic else every event, after the threshold is reached, will generate alert.
        //  This will ultimately result in noise
    }

    @Override
    public void processEvent(Event event) {
        eventsRepositoryService.saveTimeToEvent(event);
    }
}
