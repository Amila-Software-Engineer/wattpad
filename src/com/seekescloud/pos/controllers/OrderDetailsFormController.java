package com.seekescloud.pos.controllers;

import com.jfoenix.controls.JFXTextField;
import com.seekescloud.pos.db.Database;
import com.seekescloud.pos.model.CartItem;
import com.seekescloud.pos.model.Customer;
import com.seekescloud.pos.model.Order;
import com.seekescloud.pos.views.tm.ItemDetailTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;

public class OrderDetailsFormController {
    public AnchorPane orderDetailContext;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerName;
    public JFXTextField txtOrderId;
    public JFXTextField txtDate;
    public JFXTextField txtTotalCost;
    public TableView<ItemDetailTM> tblItemDetails;
    public TableColumn colProductCode;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colTotalCost;
    public JFXTextField txtCustomerSalary;

    public void initialize(){
            colProductCode.setCellValueFactory( new PropertyValueFactory<>("productCode"));
            colUnitPrice.setCellValueFactory( new PropertyValueFactory<>("unitPrice"));
            colQty.setCellValueFactory( new PropertyValueFactory<>("qty"));
            colTotalCost.setCellValueFactory( new PropertyValueFactory<>("totalCost"));
    }

    public void loadData(String orderId){
        if(orderId == null){
            removeUi();
            return;
        }
        Order order =Database.ordertable.stream().filter(e -> e.getOrderId().equals(orderId)).findFirst().orElse(null);

        if(order != null){
            Customer customer =Database.customertable.stream().filter(e -> e.getId().equals(order.getCustomer())).findFirst().orElse(null);
            if(customer !=null){
                //load
                txtCustomerId.setText(customer.getId());
                txtCustomerAddress.setText(customer.getAddress());
                txtCustomerName.setText(customer.getName());
                txtCustomerSalary.setText(String.valueOf(customer.getSalary()));

                //==========================
                txtOrderId.setText(order.getOrderId());
                txtDate.setText(new SimpleDateFormat("YYYY-MM-DD").format(order.getPlaceDate()));
                txtTotalCost.setText(String.valueOf(order.getTotal()));

                //==========================
                ObservableList<ItemDetailTM> tmList= FXCollections.observableArrayList();
                for (CartItem item: order.getItems()){
                    tmList.add(new ItemDetailTM(item.getCode(),item.getUnitPrice(),item.getQty(), (item.getQty()* item.getUnitPrice())));
                }
                tblItemDetails.setItems(tmList);

                //==========================


            }else{
            removeUi();
            }
        }else{
            removeUi();
        }
    }

    private void removeUi() {
        Stage stage = (Stage) orderDetailContext.getScene().getWindow();
        stage.close();
        new Alert(Alert.AlertType.ERROR,"Something went wrong.").show();
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
    }
}
