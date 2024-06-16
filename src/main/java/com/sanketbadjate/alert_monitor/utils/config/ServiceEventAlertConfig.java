package com.sanketbadjate.alert_monitor.utils.config;

import com.sanketbadjate.alert_monitor.enums.Client;
import com.sanketbadjate.alert_monitor.enums.EventType;
import com.sanketbadjate.alert_monitor.utils.dispatchStrategy.Alert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ServiceEventAlertConfig {
    private Client client;
    private EventType eventType;
    private AlertConfig alertConfig;
    private List<Alert> dispatchStrategyList;
}
