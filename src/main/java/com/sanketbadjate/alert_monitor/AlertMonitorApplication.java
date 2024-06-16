package com.sanketbadjate.alert_monitor;

import com.sanketbadjate.alert_monitor.enums.Client;
import com.sanketbadjate.alert_monitor.enums.EventType;
import com.sanketbadjate.alert_monitor.models.Event;
import com.sanketbadjate.alert_monitor.repositories.EventRepository;
import com.sanketbadjate.alert_monitor.services.AlertingService;
import com.sanketbadjate.alert_monitor.services.MonitoringService;
import com.sanketbadjate.alert_monitor.services.repositoryServices.EventsRepositoryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.time.LocalDateTime;

@SpringBootApplication
public class AlertMonitorApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlertMonitorApplication.class, args);
		runTests();
	}
	public static void runTests() {
		EventsRepositoryService eventsRepositoryService = new EventsRepositoryService(new EventRepository());
		AlertingService alertingService = AlertingService.getInstance();
		MonitoringService monitoringService;
		try {
			monitoringService = MonitoringService.getInstance(eventsRepositoryService, alertingService);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		LocalDateTime currentTime = LocalDateTime.now();

		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.PAYMENT_SERVICE_EXCEPTION).eventTime(currentTime.minusSeconds(10)).build());
//		monitoringService.processEvent(Event.builder().client(Client.Y).eventType(EventType.PAYMENT_SERVICE_EXCEPTION).eventTime(currentTime.minusSeconds(8)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.PAYMENT_SERVICE_EXCEPTION).eventTime(currentTime.minusSeconds(7)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.PAYMENT_SERVICE_EXCEPTION).eventTime(currentTime.minusSeconds(6)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.PAYMENT_SERVICE_EXCEPTION).eventTime(currentTime.minusSeconds(5)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.USER_SERVICE_EXCEPTION).eventTime(currentTime.minusSeconds(4)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.USER_SERVICE_EXCEPTION).eventTime(currentTime.minusSeconds(3)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.USER_SERVICE_EXCEPTION).eventTime(currentTime.minusSeconds(2)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.UNAUTHORIZED_EXCEPTION).eventTime(currentTime.minusSeconds(2)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.UNAUTHORIZED_EXCEPTION).eventTime(currentTime.minusSeconds(1)).build());
	}
}

