package com.seekescloud.pos.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DashboardFormController {
    public Label lblDate;
    public Label lblTime;
    public AnchorPane dashboardFormContext;

    public void initialize(){
        setDateAndTime();
    }
    public void setDateAndTime(){
        // set date
//        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
//        String formatedDate = dateFormat.format(date);
//        lblDate.setText(formatedDate);

        lblDate.setText( new SimpleDateFormat("YYYY-MM-dd").format(new Date()));

        // set Time
        final DateFormat format = DateFormat.getDateInstance();
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e->{
//            Calendar cal = Calendar.getInstance();
            lblTime.setText(LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("HH:mm:ss")
            ));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    public void openCustomerManagementOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("CustomerForm" , "Customer Form");
    }

    public void openProductsOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("ProductForm", "Product Form");
    }

    public void openoOrderDetailsOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("OrderDetailsForm", "Order Details Form ");
    }

    public void openPlaceOrderOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("PlaceOrderForm", "Place Order Form");
    }

    public void openStatisticsOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("StatisticForm", "Statistic Form");
    }

    public void setUi(String location, String title) throws IOException {
        Stage window = (Stage) dashboardFormContext.getScene().getWindow();
        window.setTitle(title);
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
    }
}
