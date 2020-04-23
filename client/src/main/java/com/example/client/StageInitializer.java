package com.example.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<UiApplication.StageReadyEvent> {
    @Value("classpath:/fxml/MainMenu.fxml") //tells spring to where to find the file
    private Resource uiResource;

    @Value("${spring.application.ui.windowWidth}")
    private int windowWidth;

    @Value("${spring.application.ui.windowHeight}")
    private int windowHeight;

    private String applicationTitle;
    private ApplicationContext applicationContext;
    public static Stage parentStage;

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle, ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(UiApplication.StageReadyEvent stageReadyEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(uiResource.getURL());
            fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            Parent parent = fxmlLoader.load();
            Stage stage = stageReadyEvent.getStage();
            parentStage = stage;
            stage.setScene(new Scene(parent, windowWidth, windowHeight));
            stage.setTitle(applicationTitle);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
