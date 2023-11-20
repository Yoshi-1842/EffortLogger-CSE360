package com.example.effortlogger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.net.URL;
import java.sql.SQLOutput;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private Button btn_signup;
    @FXML
    private Button btn_backLogIn;
    @FXML
    private TextField txt_username;
    @FXML
    private TextField txt_password;
    @FXML
    private TextField txt_Name;
    @FXML
    private TextField txt_lastName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!txt_username.getText().trim().isEmpty() && !txt_password.getText().trim().isEmpty() && !txt_Name.getText().trim().isEmpty() && !txt_lastName.getText().trim().isEmpty()){
                    DataBaseUtils.signUpUser(event,txt_username.getText(),txt_password.getText(),txt_Name.getText(),txt_lastName.getText());
                }else{
                    System.out.println("Complete your information!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill all the information to sign up!");
                    alert.show();
                }
            }
        });

        btn_backLogIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DataBaseUtils.changeScene(event, "hello-view.fxml", null, "Log In!");

            }
        });
    }
}
