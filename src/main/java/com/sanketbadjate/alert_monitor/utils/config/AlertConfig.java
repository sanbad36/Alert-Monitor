package com.sanketbadjate.alert_monitor.utils.config;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sanketbadjate.alert_monitor.enums.AlertStrategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SlidingWindowAlertConfig.class, name = "SLIDING_WINDOW"),
        @JsonSubTypes.Type(value = TumblingWindowAlertConfig.class, name = "TUMBLING_WINDOW"),
        @JsonSubTypes.Type(value = SimpleAlertConfig.class, name = "SIMPLE_COUNT"),
})
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public abstract class AlertConfig implements Serializable {
    private AlertStrategy type;
    private Integer count;
}
