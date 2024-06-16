package com.sanketbadjate.alert_monitor.utils.dispatchStrategy;

import com.sanketbadjate.alert_monitor.utils.config.ServiceEventAlertConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailAlert extends Alert {
    private String subject;
    @Override
    public void dispatch(ServiceEventAlertConfig config) {
//        System.out.println("Dispatching to Email");
    }
}
