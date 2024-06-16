package com.sanketbadjate.alert_monitor.utils.eventProcessor;


import com.sanketbadjate.alert_monitor.models.Event;
import com.sanketbadjate.alert_monitor.services.repositoryServices.EventsRepositoryService;
import com.sanketbadjate.alert_monitor.utils.config.WindowAlertConfig;

import java.time.Duration;
import java.time.LocalDateTime;

public class TumblingWindowEventProcessor extends WindowEventProcessor {

    public TumblingWindowEventProcessor(WindowAlertConfig config, EventsRepositoryService eventsRepositoryService) {
        super(config, eventsRepositoryService);
    }

    private LocalDateTime getStartTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime midNight = currentTime.toLocalDate().atStartOfDay();
        Integer noOfTumblesTillNow = (int) (Duration.between(midNight, currentTime).getSeconds() / config.getWindowSizeInSecs());
        return midNight.plusSeconds((long) noOfTumblesTillNow * config.getWindowSizeInSecs());
    }

    @Override
    public Boolean isThresholdReached(Event event) {
        LocalDateTime startTime = getStartTime();
        LocalDateTime endTime = startTime.plusSeconds(config.getWindowSizeInSecs());
        return eventsRepositoryService.countAllOccurrencesBetween(event, startTime, endTime) > config.getCount();
    }
}
