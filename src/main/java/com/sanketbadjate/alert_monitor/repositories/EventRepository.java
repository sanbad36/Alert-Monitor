package com.sanketbadjate.alert_monitor.repositories;


import com.sanketbadjate.alert_monitor.models.Event;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Repository
public class EventRepository {
    // similar to time-series database storage
    // this can be further optimized to have Map<Client, Map<EventType, Event>> instead of List<Event> in value of
    // avoided above optimization in favor of simplicity and time in hand
    private SortedMap<LocalDateTime, List<Event>> eventToTimeMap; // For Window type processing
    private AbstractMap<Event, List<LocalDateTime>> timeToEventMap; // For Simple Count processing

    public EventRepository() {
        // used treeMap as our focus is on extracting data in a time range
        this.eventToTimeMap = new TreeMap<>();
        this.timeToEventMap = new HashMap<>();
    }

    public void saveEventToTime(Event event) {
        if (eventToTimeMap.containsKey(event.getEventTime())) {
            eventToTimeMap.computeIfPresent(event.getEventTime(), (key, value) -> {
                value.add(event);
                return value;
            });
            return;
        }
        eventToTimeMap.computeIfAbsent(event.getEventTime(), k -> new ArrayList<>()).add(event);
    }

    public void saveTimeToEvent(Event event) {
        if (timeToEventMap.containsKey(event)) {
            timeToEventMap.computeIfPresent(event, (key, value) -> {
                value.add(event.getEventTime());
                return value;
            });
            return;
        }
        timeToEventMap.computeIfAbsent(event, k -> new ArrayList<>()).add(event.getEventTime());
    }

    public List<LocalDateTime> findAllEventOccurrences(Event event) {
        return timeToEventMap.get(event);
    }

    public List<Event> findBetween(Event event, LocalDateTime start, LocalDateTime end) {
        return eventToTimeMap
                .subMap(start, end)
                .values().stream()
                .flatMap(List::stream)
                .filter(currEvent -> currEvent.equals(event))
                .collect(Collectors.toList());
    }

    public void remove(Event event) {
        timeToEventMap.remove(event);
    }
}