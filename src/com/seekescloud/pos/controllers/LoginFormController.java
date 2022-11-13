package com.seekescloud.pos.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.seekescloud.pos.db.Database;
import com.seekescloud.pos.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane loginFormContext;
    public JFXTextField txtUsername;
    public JFXPasswordField txtPassword;

    public void createAnAccountOnAction(ActionEvent actionEvent) throws IOException {
            setUi("SignUpForm","SingUp Form");
    }

    private void setUi(String location, String title) throws IOException {
        Stage window = (Stage)loginFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource( "../views/"+location +".fxml"))));
    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        //get user details from the interface
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        // find the user with inserted email -> user table

        for(User u: Database.usertable){
            if(u.getEmail().equals(username)) {
                if(u.getPassword().equals(password)) {
                    setUi("DashboardForm", u.getFullName());
                    // must be encrypted
                    // check password
                }else{
                    new Alert(Alert.AlertType.WARNING,"Password is incorrect.");
                    // if correct => redirect to the dashboard otherwise the system must show an error
                }
                return; 
            }
        }
        new Alert(Alert.AlertType.INFORMATION,"Can't find any  user with this user details.");
    }
}
