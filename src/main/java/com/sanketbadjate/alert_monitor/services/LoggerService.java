package com.sanketbadjate.alert_monitor.services;


import com.sanketbadjate.alert_monitor.constants.LoggerConstants;
import com.sanketbadjate.alert_monitor.enums.LogType;

public interface LoggerService {
    default void logInfo(String message) {
        System.out.println(String.format(LoggerConstants.FORMATTED_LOG, LogType.INFO.name(), this.getClass().getSimpleName(), message));
    }

    default void logWarning(String message) {
        System.out.println(String.format(LoggerConstants.FORMATTED_LOG, LogType.WARN.name(), this.getClass().getSimpleName(), "`" + message + "`"));
    }

    default void logSourceWarning(String message, String logSource) {
        System.out.println(String.format(LoggerConstants.FORMATTED_LOG, LogType.WARN.name(), logSource, "`" + message + "`"));
    }
}
