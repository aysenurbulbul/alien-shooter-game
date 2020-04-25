package com.example.client;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		Application.launch(UiApplication.class, args);
	}

}
