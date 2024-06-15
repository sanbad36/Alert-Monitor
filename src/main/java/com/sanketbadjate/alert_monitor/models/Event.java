package com.sanketbadjate.alert_monitor.models;

import com.sanketbadjate.alert_monitor.enums.Client;
import com.sanketbadjate.alert_monitor.enums.EventType;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(of = {"client", "eventType"}) // Uniqueness of an Event is determined combined by client and eventType
@ToString
@Builder
public class Event implements Serializable {
    private Client client;
    private EventType eventType;
    private LocalDateTime eventTime;
}
