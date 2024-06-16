package com.sanketbadjate.alert_monitor.utils.dispatchStrategy;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sanketbadjate.alert_monitor.enums.AlertType;
import com.sanketbadjate.alert_monitor.utils.config.ServiceEventAlertConfig;
import lombok.Data;
import lombok.ToString;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ConsoleAlert.class, name = "CONSOLE"),
        @JsonSubTypes.Type(value = EmailAlert.class, name = "EMAIL")
})
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Data
public abstract class Alert {
    private AlertType type;
    public abstract void dispatch(ServiceEventAlertConfig config);
}
