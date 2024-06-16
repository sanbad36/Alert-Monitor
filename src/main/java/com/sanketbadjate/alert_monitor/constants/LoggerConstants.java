package com.sanketbadjate.alert_monitor.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LoggerConstants {
    public final String FORMATTED_LOG = "[%s] %s: %s"; // LogType, ServiceName, Message
    public final String THRESHOLD_BREACHED = "Client %s %s threshold breached"; // client, eventType
}
