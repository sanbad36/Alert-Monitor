package com.sanketbadjate.alert_monitor.utils.eventProcessor;

import com.sanketbadjate.alert_monitor.models.Event;
import com.sanketbadjate.alert_monitor.services.repositoryServices.EventsRepositoryService;
import com.sanketbadjate.alert_monitor.utils.config.WindowAlertConfig;

import java.time.LocalDateTime;

public class SlidingWindowEventProcessor extends WindowEventProcessor {
//    PriorityQueue<Event> eventsQueue = new PriorityQueue(new Comparator<Event>() {
//        @Override
//        public int compare(Event o1, Event o2) {
//            return o1.getEventTime().compareTo(o2.getEventTime());
//        }
//    });

    public SlidingWindowEventProcessor(WindowAlertConfig config, EventsRepositoryService eventsRepositoryService) {
        super(config, eventsRepositoryService);
    }

    @Override
    public Boolean isThresholdReached(Event event) {
        LocalDateTime currentTime = LocalDateTime.now();

//        eventsQueue.add(event);
//        while(eventsQueue.peek().getEventTime().compareTo(currentTime.minusSeconds(config.getWindowSizeInSecs())) < 0) {
//            eventsQueue.remove();
//        }

//        return eventsQueue.size() > config.getCount();
        return eventsRepositoryService
                .countAllOccurrencesBetween(
                        event,
                        currentTime
                                .minusSeconds(config.getWindowSizeInSecs()), currentTime.plusSeconds(1)
                ) > config.getCount();
    }
}
