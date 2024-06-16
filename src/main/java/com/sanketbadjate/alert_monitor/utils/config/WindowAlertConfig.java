package com.sanketbadjate.alert_monitor.utils.config;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(callSuper = true)
public abstract class WindowAlertConfig extends AlertConfig {
    private Integer windowSizeInSecs;
}
