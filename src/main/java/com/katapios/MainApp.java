package com.katapios;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        log.info("Starting JavaFX Calculator and Maven demonstration application");
        String fxmlFile = "/scenes/sample.fxml";
        File file = new File("user.settings");
        boolean exist = file.exists();

        if (exist){
            FileInputStream fis = new FileInputStream("user.settings");
            ObjectInputStream ois = new ObjectInputStream(fis);
            User user = (User) ois.readObject();
            if (!user.getLogin().equals("")) {
                fxmlFile = "/scenes/main.fxml";
            }
            ois.close();
        }

        log.debug("Loading FXML for main view from: {}", fxmlFile);
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

        log.debug("Showing JFX scene");
        Scene scene = new Scene(rootNode, 600, 400);
        scene.getStylesheets().add("/styles/style.css");

        stage.setTitle("JavaFX Auth");
        stage.setScene(scene);
        stage.show();
    }
}
