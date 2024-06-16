package com.sanketbadjate.alert_monitor.utils.dispatchStrategy;


import com.sanketbadjate.alert_monitor.services.LoggerService;
import com.sanketbadjate.alert_monitor.utils.config.ServiceEventAlertConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConsoleAlert extends Alert implements LoggerService {
    private String message;
    @Override
    public void dispatch(ServiceEventAlertConfig config) {
        logSourceWarning(message, "Alert");
    }
}
