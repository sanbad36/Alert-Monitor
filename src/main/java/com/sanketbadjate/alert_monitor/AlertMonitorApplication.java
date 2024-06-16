package com.sanketbadjate.alert_monitor;

import com.sanketbadjate.alert_monitor.enums.Client;
import com.sanketbadjate.alert_monitor.enums.EventType;
import com.sanketbadjate.alert_monitor.models.Event;
import com.sanketbadjate.alert_monitor.services.AlertingService;
import com.sanketbadjate.alert_monitor.services.MonitoringService;
import com.sanketbadjate.alert_monitor.services.repositoryServices.EventsRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
public class AlertMonitorApplication implements CommandLineRunner {

	@Autowired
	private MonitoringService monitoringService;

	public static void main(String[] args) {
		SpringApplication.run(AlertMonitorApplication.class, args);
	}

	@Override
	public void run(String... args) throws InterruptedException {
		runTests();
	}

	public void runTests() throws InterruptedException {
		LocalDateTime currentTime = LocalDateTime.now();

		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.PAYMENT_SERVICE_EXCEPTION).eventTime(currentTime.minusSeconds(10)).build());
        monitoringService.processEvent(Event.builder().client(Client.Y).eventType(EventType.PAYMENT_SERVICE_EXCEPTION).eventTime(currentTime.minusSeconds(8)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.PAYMENT_SERVICE_EXCEPTION).eventTime(currentTime.minusSeconds(7)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.PAYMENT_SERVICE_EXCEPTION).eventTime(currentTime.minusSeconds(6)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.PAYMENT_SERVICE_EXCEPTION).eventTime(currentTime.minusSeconds(5)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.USER_SERVICE_EXCEPTION).eventTime(currentTime.minusSeconds(4)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.USER_SERVICE_EXCEPTION).eventTime(currentTime.minusSeconds(3)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.USER_SERVICE_EXCEPTION).eventTime(currentTime.minusSeconds(2)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.UNAUTHORIZED_EXCEPTION).eventTime(currentTime.minusSeconds(2)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.UNAUTHORIZED_EXCEPTION).eventTime(currentTime.minusSeconds(1)).build());
		monitoringService.processEvent(Event.builder().client(Client.X).eventType(EventType.UNAUTHORIZED_EXCEPTION).eventTime(currentTime.minusSeconds(10)).build());
	}

}
