package com.katapios;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        log.info("Starting JavaFX Calculator and Maven demonstration application");

        String fxmlFile = "/fxml/sample.fxml";
        log.debug("Loading FXML for main view from: {}", fxmlFile);
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

        log.debug("Showing JFX scene");
        Scene scene = new Scene(rootNode, 235, 300);
        scene.getStylesheets().add("/styles/main.css");

        stage.setTitle("JavaFX Calculator");
        stage.setScene(scene);
        stage.show();
    }
}
