package com.example.effortlogger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

    @FXML
    private Button btn_logout;
    @FXML
    private Label lbl_welcome1;
    @FXML
    private Label lbl_welcome2;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            DataBaseUtils.changeScene(actionEvent, "hello-view.fxml",null,"Log In!");
            }
        });
    }

    public void setUserInformation(String username){
        lbl_welcome1.setText("Welcome to the Effort LoggerTool " + username+ " !");
    }
}
