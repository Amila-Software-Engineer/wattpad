package com.seekescloud.pos.controllers;

import com.seekescloud.pos.db.Database;
import com.seekescloud.pos.model.Order;
import com.seekescloud.pos.views.tm.OrderDetailsTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class OrdersFormController {
    public AnchorPane orderContext;
    public TableView tblOrders;
    public TableColumn colOrderId;
    public TableColumn colDate;
    public TableColumn colCustomerId;
    public TableColumn colOption;
    public TableColumn colTotal;

    public void initialize(){
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadData();
        tblOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                try {
                    openDetailsUI((OrderDetailsTM) newValue);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void openDetailsUI(OrderDetailsTM newValue) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/OrderDetailsForm.fxml"));
        Parent parent = fxmlLoader.load();
        OrderDetailsFormController orderDetailsFormController=fxmlLoader.getController();
        orderDetailsFormController.loadData(newValue.getOrderId());
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    private void loadData() {
        ObservableList<OrderDetailsTM> tmList = FXCollections.observableArrayList();
        for(Order o: Database.ordertable){
            Button btn = new Button("Delete");
            OrderDetailsTM tm=  new OrderDetailsTM(o.getOrderId(),
                    new SimpleDateFormat("YYYY-MM-DD").format(o.getPlaceDate()),
                          o.getTotal(),o.getCustomer(), btn);
            tmList.add(tm);
            btn.setOnAction(e->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this?.",
                        ButtonType.YES ,ButtonType.NO);
                Optional<ButtonType> val = alert.showAndWait();
                if(val.get() == ButtonType.YES ){
                    Database.ordertable.remove(o);
                    new Alert( Alert.AlertType.CONFIRMATION,"Order Deleted." ).show();
                    loadData();
                }

            });
        }
        tblOrders.setItems(tmList);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm", "Dashboard Form");
    }
    public void setUi(String location, String title) throws IOException {
        Stage window = (Stage) orderContext.getScene().getWindow();
        window.setTitle(title);
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
    }
}
