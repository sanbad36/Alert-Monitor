package com.sanketbadjate.alert_monitor.services.repositoryServices;

import com.sanketbadjate.alert_monitor.models.Event;
import com.sanketbadjate.alert_monitor.repositories.EventRepository;

import java.time.LocalDateTime;

public class EventsRepositoryService {
    private EventRepository eventRepository;

    public EventsRepositoryService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void saveEventToTime(Event event) {
        eventRepository.saveEventToTime(event);
        // NOTE: uncomment to see state right after an event
//        System.out.println("event to time map" + eventRepository.getEventToTimeMap());
    }

    public void saveTimeToEvent(Event event) {
        eventRepository.saveTimeToEvent(event);
        // NOTE: uncomment to see state right after an event
//       System.out.println("time to event map" + eventRepository.getTimeToEventMap());
    }

    public Integer countAllOccurrences(Event event) {
        return eventRepository.findAllEventOccurrences(event).size();
    }

    public Integer countAllOccurrencesBetween(Event event, LocalDateTime start, LocalDateTime end) {
        return eventRepository.findBetween(event, start, end).size();
    }
}
