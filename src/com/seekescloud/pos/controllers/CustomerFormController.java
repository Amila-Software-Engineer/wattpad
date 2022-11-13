package com.seekescloud.pos.controllers;

import com.jfoenix.controls.JFXTextField;
import com.seekescloud.pos.db.Database;
import com.seekescloud.pos.model.Customer;
import com.seekescloud.pos.views.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class CustomerFormController {
    public JFXTextField txtId;
    public JFXTextField txtname;
    public JFXTextField txtAddress;
    public JFXTextField txtSalary;
    public JFXTextField txtSearch;
    public AnchorPane customerFormContext;
    public TableView<CustomerTm> tblCustomer;
    public TableColumn colCustomerId;
    public TableColumn colCustomerName;
    public TableColumn colCustomerAddress;
    public TableColumn colCustomerSalary;
    public TableColumn colCustomerOption;


    public void initialize(){
        setTableData();
        setCustomerId();
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCustomerOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }


    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm", "Dashboard Form");
    }

    public void saveUpdateOnAction(ActionEvent actionEvent) {
        Customer  customer = new Customer(
                txtId.getText(),
                txtname.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );
        if(Database.customertable.add(customer)){
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved!! ").show();
            setTableData();
            setCustomerId();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION, "Try again ").show();
        }
    }

    public void setUi(String location, String title) throws IOException {
        Stage window = (Stage) customerFormContext.getScene().getWindow();
        window.setTitle(title);
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
    }

    private void setTableData(){
        ArrayList<Customer> customerArrayList = Database.customertable;
        ObservableList<CustomerTm> oblist = FXCollections.observableArrayList();
        for(Customer c: customerArrayList){
            Button btn = new Button("Delete");
            CustomerTm tm = new CustomerTm(c.getId(), c.getName(),c.getAddress(), c.getSalary(),btn);
            oblist.add(tm);
        }
        tblCustomer.setItems(oblist);
    }

    private void setCustomerId() {
        // get last saved customer
        // catch the id c001
        // separate the number from the character
        // increment the separated number
        // concat the character again to the incremented number
        //set customer
        if(!Database.customertable.isEmpty()){
            Customer c = Database.customertable.get(Database.customertable.size()-1);
            String id = c.getId();//c-001
            String dataArray[] = id.split("-");
            id = dataArray[1];
            int oldNumber = Integer.parseInt(id);
            oldNumber++;
            if(oldNumber<9){
                txtId.setText("C-00"+oldNumber);
            } else if (oldNumber<99) {
                txtId.setText("C-0"+oldNumber);
            }else {
                txtId.setText("C-"+oldNumber);
            }
        }else {
            txtId.setText("C-001");
        }
    }

}
