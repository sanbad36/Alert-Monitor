package com.sanketbadjate.alert_monitor.utils.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanketbadjate.alert_monitor.constants.ApplicationConstants;
import com.sanketbadjate.alert_monitor.models.Event;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class ConfigUpdater {

    private final RestTemplate restTemplate;
    private final Map<Event, ServiceEventAlertConfig> eventConfigMap;
    private String currentConfigHash;

    @Value("${github.raw.config.url}")
    private String githubRawUrl = "https://raw.githubusercontent.com/sanbad36/alert-monitoring-config/main/ServiceEventAlertConfig.json";

    @Value("${current.config.hash}")
    private void setCurrentConfigHash(String currentConfigHash) {
        this.currentConfigHash = currentConfigHash;
    }

    private final ObjectMapper mapper = new ObjectMapper();

    public ConfigUpdater(RestTemplate restTemplate,
                         Map<Event, ServiceEventAlertConfig> eventConfigMap) {
        this.restTemplate = restTemplate;
        this.eventConfigMap = eventConfigMap;
    }

    @Scheduled(fixedRateString = "${config.update.interval:3000}")
    public void updateConfigFromGithub() {
        try {
            String newConfigContent = restTemplate.getForObject(githubRawUrl, String.class);
            String newConfigHash = DigestUtils.sha256Hex(newConfigContent.getBytes());
            if (!Objects.equals(newConfigHash, currentConfigHash)) {
                Map<Event, ServiceEventAlertConfig> newConfigMap = parseConfig(newConfigContent);
                eventConfigMap.clear();
                eventConfigMap.putAll(newConfigMap);
                currentConfigHash = newConfigHash;
                setCurrentConfigHash(newConfigHash);
                System.out.println("Config updated from GitHub");
            } else {
                System.out.println("Config unchanged");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            String responseBody = ex.getResponseBodyAsString();
            System.err.println("HTTP error while fetching config from GitHub: " + responseBody);
            fallbackToLocalConfig();
        } catch (Exception ex) {
            System.err.println("Failed to update config from GitHub: " + ex.getMessage());
            fallbackToLocalConfig();
        }
    }

    private void fallbackToLocalConfig() {
        try {
            File file = new File(ApplicationConstants.USER_DIRECTORY + ApplicationConstants.SERVICE_EVENT_ALERT_CONFIG_PATH);
            List<ServiceEventAlertConfig> localConfig = mapper.readValue(file, new TypeReference<>() {});
            Map<Event, ServiceEventAlertConfig> localConfigMap = new HashMap<>();
            for (ServiceEventAlertConfig config : localConfig) {
                localConfigMap.put(Event.builder().client(config.getClient()).eventType(config.getEventType()).build(), config);
            }
            eventConfigMap.clear();
            eventConfigMap.putAll(localConfigMap);
            System.out.println("Config updated from local file");
        } catch (IOException e) {
            System.err.println("Failed to update config from local file: " + e.getMessage());
        }
    }

    private Map<Event, ServiceEventAlertConfig> parseConfig(String configContent) throws IOException {
        List<ServiceEventAlertConfig> configList = mapper.readValue(configContent, new TypeReference<>() {});
        Map<Event, ServiceEventAlertConfig> configMap = new HashMap<>();
        for (ServiceEventAlertConfig config : configList) {
            configMap.put(Event.builder().client(config.getClient()).eventType(config.getEventType()).build(), config);
        }
        return configMap;
    }

    public Map<Event, ServiceEventAlertConfig> getEventConfigMap() {
        return eventConfigMap;
    }
}
