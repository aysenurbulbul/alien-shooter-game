package com.example.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class UiApplication extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init(){
        applicationContext = new SpringApplicationBuilder(ClientApplication.class).run();
    }

    @Override
    public void start(Stage stage){
        applicationContext.publishEvent(new StageReadyEvent(stage));

    }

    static class StageReadyEvent extends ApplicationEvent {
        StageReadyEvent(Stage stage) {
            super(stage);
        }

        Stage getStage() {
            return ((Stage) getSource());
        }
    }
}
