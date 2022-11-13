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

public class SignUpFormController {
    public JFXPasswordField txtPassword;
    public JFXTextField txtEmail;
    public JFXTextField txtContact;
    public JFXTextField txtFullName;
    public JFXPasswordField txtRePassword;
    public AnchorPane singUpFormContext;

    public void singUpOnAction(ActionEvent actionEvent) throws InterruptedException, IOException {
        // check password match?
        String realPwd =txtPassword.getText().trim();
        String matchPwd =txtRePassword.getText().trim();

        //if real password and match password is not match alert user
        if(!realPwd.equals(matchPwd)){
            new Alert(Alert.AlertType.WARNING, "Both Passwords Should match").show();
            return;
        }
        //
        User u = new User(
                txtEmail.getText().trim(),
                txtFullName.getText(),
                txtContact.getText(),
                realPwd
        );
        // save or not save warning tryagain
        if(saveUser(u)){
            new Alert(Alert.AlertType.CONFIRMATION, "User Registered").show();
            clearFields();
            Thread.sleep(2000);
            setUi("DashboardForm", u.getFullName());
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again !!!").show();
        }
    }

    private void clearFields() {
        txtEmail.clear();
        txtRePassword.clear();
        txtContact.clear();
        txtPassword.clear();
        txtFullName.clear();
    }


    private boolean saveUser(User u){
        return Database.usertable.add(u);
    }

    public void alreadyHaveAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm","Login Form");
    }
    public void setUi(String location, String title) throws IOException {
        Stage window =(Stage) singUpFormContext.getScene().getWindow();
        window.setTitle(title);
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));

    }
}


