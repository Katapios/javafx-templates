package com.katapios.controllers;

import com.katapios.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.katapios.DB;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class RegController {

    @FXML
    private TextField login_reg;

    @FXML
    private TextField email_reg;

    @FXML
    private PasswordField pass_reg;

    @FXML
    private CheckBox confidentials;

    @FXML
    private Button btn_reg;

    @FXML
    private TextField login_auth;

    @FXML
    private PasswordField pass_auth;

    @FXML
    private Button btn_auth;

    private DB db = new DB();

    @FXML
    void initialize() {

        btn_reg.setOnAction(event -> {
            login_reg.setStyle("-fx-border-color: #fafafa");
            email_reg.setStyle("-fx-border-color: #fafafa");
            pass_reg.setStyle("-fx-border-color: #fafafa");
            btn_reg.setText("Зарегистрироваться");

            if (login_reg.getCharacters().length() <= 3) {
                login_reg.setStyle("-fx-border-color: red");
                return;
            } else if (email_reg.getCharacters().length() <= 5) {
                email_reg.setStyle("-fx-border-color: red");
                return;
            } else if (pass_reg.getCharacters().length() <= 3) {
                pass_reg.setStyle("-fx-border-color: red");
                return;
            } else if (!confidentials.isSelected()) {
                btn_reg.setText("Поставьте галочку");
                return;
            }

            String pass = md5String(pass_reg.getCharacters().toString());

            try {
                boolean isReg = db.regUser(login_reg.getCharacters().toString(), email_reg.getCharacters().toString(), pass);
                if (isReg) {
                    login_reg.setText("");
                    email_reg.setText("");
                    pass_reg.setText("");
                    btn_reg.setText("Готово");
                } else {
                    btn_reg.setText("Логин уже существует");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        btn_auth.setOnAction(event -> {
            login_auth.setStyle("-fx-border-color: #fafafa");
            pass_auth.setStyle("-fx-border-color: #fafafa");

            if (login_auth.getCharacters().length() <= 3) {
                login_auth.setStyle("-fx-border-color: red");
                return;
            } else if (pass_auth.getCharacters().length() <= 3) {
                pass_auth.setStyle("-fx-border-color: red");
                return;
            }

            String pass = md5String(pass_auth.getCharacters().toString());

            try {
                boolean isAuth = db.authUser(login_auth.getCharacters().toString(), pass);
                if (isAuth) {
                    FileOutputStream fos = new FileOutputStream("user.settings");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);

                    oos.writeObject(new User(login_auth.getCharacters().toString()));
                    oos.close();

                    login_auth.setText("");
                    pass_auth.setText("");
                    btn_reg.setText("Готово");

                    String fxmlFile = "/scenes/main.fxml";
                    FXMLLoader loader = new FXMLLoader();
                    Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

                    Scene scene = new Scene(rootNode, 600, 400);
                    scene.getStylesheets().add("/styles/style.css");

                    Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

                    stage.setTitle("JavaFX Auth");
                    stage.setScene(scene);
                    stage.show();


                } else {
                    btn_auth.setText("Юзер нe найден");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static String md5String(String pass) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(pass.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger bigInteger = new BigInteger(1, digest);
        String md5Hex = bigInteger.toString(16);
        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;

    }
}