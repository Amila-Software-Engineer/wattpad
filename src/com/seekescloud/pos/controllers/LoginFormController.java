package com.seekescloud.pos.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane loginFormContext;

    public void createAnAccountOnAction(ActionEvent actionEvent) {

    }

    private void setUi(String location) throws IOException {
        Stage window = (Stage)loginFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/"+location+".fxml"))));
    }
}
