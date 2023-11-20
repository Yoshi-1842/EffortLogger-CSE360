package com.example.effortlogger;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.print.PrintSides;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

//SQL Libraries
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.IOException;

public class DataBaseUtils {
    public static void changeScene(ActionEvent event, String fmxlFile, String username, String title){
        Parent root = null;

        if(username != null){
            try{
                FXMLLoader loader = new FXMLLoader(DataBaseUtils.class.getResource(fmxlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation((username));

            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            try{
                root = FXMLLoader.load(DataBaseUtils.class.getResource(fmxlFile));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static  void signUpUser(ActionEvent event, String username, String password, String name, String lastName){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/effort-logger","root","Pontiac1842#");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM user_information WHERE username =?");
            psCheckUserExists.setString(1,username);
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("The User already exist!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You Shouldn't use this Username");
                alert.show();
            }else{
                psInsert = connection.prepareStatement("INSERT INTO user_information (username, password, name, lastName) VALUES (?,?,?,? )");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.setString(3,name);
                psInsert.setString(4,lastName);
                psInsert.executeUpdate();

                changeScene(event,"logged-up.fxml",username,"Welcome");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists != null){
                try{
                    psCheckUserExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert != null){
                try{
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/effort-logger", "root", "Pontiac1842#");
            preparedStatement = connection.prepareStatement("SELECT password, name FROM user_information WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the DataBase!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The credentials are incorrect!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedName = resultSet.getString("name");

                    if (retrievedPassword.equals(password)) {
                        changeScene(event, "logged-up.fxml", username, "Welcome");
                    } else {
                        System.out.println("The password could be incorrect!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The password is incorrect!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closing resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
