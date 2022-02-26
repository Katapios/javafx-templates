package com.katapios.controllers;

import com.katapios.DB;
import com.katapios.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

    @FXML
    private Button btn_exit, btn_add_article;

    @FXML
    private VBox paneVBox;
    private DB db = new DB();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        ResultSet res = null;

        res = db.getArticles();

        while (res.next()){
            Node node = null;
            try {
                node = (Parent) new FXMLLoader().load(getClass().getResourceAsStream("/scenes/article.fxml"));
                Label title = (Label) node.lookup("#title");
                title.setText(res.getString("title"));
                Label intro = (Label) node.lookup("#intro");
                intro.setText(res.getString("intro"));

                final Node nodeSet = node;

                node.setOnMouseEntered(event -> {
                    nodeSet.setStyle("-fx-background-color: #707173");
                });

                node.setOnMouseExited(event -> {
                    nodeSet.setStyle("-fx-background-color: #343434");
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
            HBox hBox = new HBox();
            hBox.getChildren().add(node);
            hBox.setAlignment(Pos.BASELINE_CENTER);
            paneVBox.getChildren().add(hBox);
            paneVBox.setSpacing(10);
        }

        btn_exit.setOnAction(event ->{
            try {
                FileOutputStream fos = new FileOutputStream("user.settings");
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(new User(""));
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String fxmlFile = "/scenes/sample.fxml";
            FXMLLoader loader = new FXMLLoader();
            Parent rootNode = null;
            try {
                rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
                Scene scene = new Scene(rootNode, 600, 400);
                scene.getStylesheets().add("/styles/style.css");

                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

                stage.setTitle("JavaFX Auth");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn_add_article.setOnAction(event -> {
            String fxmlFile = "/scenes/addArticle.fxml";
            FXMLLoader loader = new FXMLLoader();
            Parent rootNode = null;
            try {
                rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
                Scene scene = new Scene(rootNode, 600, 400);
                scene.getStylesheets().add("/styles/text-area.css");

                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

                stage.setTitle("JavaFX Auth");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
