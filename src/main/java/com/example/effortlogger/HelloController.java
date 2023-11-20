package com.example.effortlogger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Button btn_login;
    @FXML
    private Button btn_sign_up;
    @FXML
    private TextField txt_Password;
    @FXML
    private TextField txt_Username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DataBaseUtils.logInUser(event,txt_Username.getText(),txt_Password.getText());
            }
        });

        btn_sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DataBaseUtils.changeScene(event, "sign-up.fxml", null, "Sign Up");

            }
        });
    }
}