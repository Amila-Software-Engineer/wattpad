package com.seekescloud.pos.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.seekescloud.pos.db.Database;
import com.seekescloud.pos.model.Customer;
import com.seekescloud.pos.views.tm.CustomerTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class CustomerFormController {
    public JFXTextField txtId;
    public JFXTextField txtname;
    public JFXTextField txtAddress;
    public JFXTextField txtSalary;
    public JFXTextField txtSearch;
    public AnchorPane customerFormContext;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colCustomerId;
    public TableColumn colCustomerName;
    public TableColumn colCustomerAddress;
    public TableColumn colCustomerSalary;
    public TableColumn colCustomerOption;
    public JFXButton btnSaveUpdate;

    private String  searchText ="";


    public void initialize(){
        setTableData(searchText);
        setCustomerId();
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCustomerOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        txtSearch.textProperty().addListener((observable, oldValue, newValue)->{
            searchText=newValue;
            setTableData( searchText);
        });
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue){
                setCustomerData(newValue);
            }
        });
    }
//=========================================
    private void setCustomerData(CustomerTM tm) {
        txtId.setText(tm.getId());
        txtname.setText(tm.getName());
        txtAddress.setText(tm.getAddress());
        txtSalary.setText(String.valueOf(tm.getId()));
        btnSaveUpdate.setText("Update Customer");

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

        if(btnSaveUpdate.getText().equalsIgnoreCase("save Customer")){
                //save
            if(Database.customertable.add(customer)){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved!! ").show();
                setTableData(searchText);
                setCustomerId();
                clear();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION, "Try again ").show();
            }
        }else{
                //update
            for(Customer c: Database.customertable){
                if(txtId.getText().equalsIgnoreCase(c.getId())){
                    c.setName(txtname.getText());
                    c.setAddress(txtAddress.getText());
                    c.setSalary(Double.parseDouble(txtSalary.getText()));
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated!! ").show();
                    setTableData(searchText);
                    clear();
                }
            }
        }
    }

    public void setUi(String location, String title) throws IOException {
        Stage window = (Stage) customerFormContext.getScene().getWindow();
        window.setTitle(title);
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
    }

    private void clear(){
        btnSaveUpdate.setText("Save Customer");
        txtname.clear();
        txtAddress.clear();
        txtSalary.clear();
        setCustomerId();
    }

    private void setTableData(String text){
        text = text.toLowerCase();
        ArrayList<Customer> customerArrayList = Database.customertable;
        ObservableList<CustomerTM> oblist = FXCollections.observableArrayList();
        for(Customer c: customerArrayList){
            if(c.getName().toLowerCase().contains(text) || c.getAddress().toLowerCase().contains(text)) {


                Button btn = new Button("Delete");
                CustomerTM tm = new CustomerTM(c.getId(), c.getName(), c.getAddress(), c.getSalary(), btn);
                oblist.add(tm);

                btn.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer",
                            ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> val = alert.showAndWait();
                    if (val.get() == ButtonType.YES) {
                        Database.customertable.remove(c);
                        new Alert(Alert.AlertType.INFORMATION, "Customer Deleted!.").show();
                        setTableData(searchText);
                    }
                });
            }
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

    public void newCustomerOnAction(ActionEvent actionEvent) {
        clear();
    }


}
