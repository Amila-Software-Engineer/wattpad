package com.seekescloud.pos.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.seekescloud.pos.db.Database;
import com.seekescloud.pos.model.Product;
import com.seekescloud.pos.views.tm.ProductTM;
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

public class ProductFormController {
    public JFXTextField txtProductCode;
    public JFXTextField txtProductDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtSearch;
    public JFXButton btnSaveUpdate;
    public TableView<ProductTM> tblProduct;
    public TableColumn colProductId;
    public TableColumn colProductDescription;
    public TableColumn colProductUnitPrice;
    public TableColumn colProductQtyOnHand;
    public TableColumn colQR;
    public TableColumn colOption;
    public AnchorPane productFormContext;

    private String searchText ="";

    public void initialize(){
        setTableData(searchText);
        setItemCode();
        colProductId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colProductDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colProductUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colProductQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colQR.setCellValueFactory(new PropertyValueFactory<>("qr"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        txtSearch.textProperty().addListener((observable, oldValue, newValue)->{
            searchText=newValue;
            setTableData( searchText);
        });

        tblProduct.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue){
                setProductData(newValue);
            }
        });

    }



    private void setTableData(String searchText){
        searchText = searchText.toLowerCase();
        // bring the data from the product table
        ArrayList<Product> productArrayList = Database.producttable;
        ObservableList<ProductTM> oblist = FXCollections.observableArrayList();
        for(Product p: productArrayList){
            if(p.getDescription().toLowerCase().contains(searchText)){
                Button btn =new Button("Delete");
                ProductTM tm = new ProductTM(p.getCode(),p.getDescription(),p.getUnitPrice(),p.getQtyOnHand(),"QR",btn);
                oblist.add(tm);

                String finalSearchText = searchText;
                String finalSearchText1 = searchText;
                btn.setOnAction(e ->{
                    Alert alert =new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to this product.",ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> val = alert.showAndWait();
                    if(val.get() == ButtonType.YES){
                        Database.producttable.remove(p);
                        new Alert(Alert.AlertType.INFORMATION, "Product Deleted!.").show();
                        setTableData("");
                    }
                });
            }
        }
        tblProduct.setItems(oblist);

    }
    public void addNewProductOnAction(ActionEvent actionEvent) {
        clear();
    }

    public void saveUpdateProductOnAction(ActionEvent actionEvent) {

        Product product = new Product(
                txtProductCode.getText(),
                txtProductDescription.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText()
                ));

        if(btnSaveUpdate.getText().equalsIgnoreCase("Save Product")){
            // save
            if(Database.producttable.add(product)){
               new Alert(Alert.AlertType.CONFIRMATION,"Product Saved !.").show();
               setTableData(searchText);
               setItemCode();
              clear();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Try Again !.").show();

            }
        }else{
            //update
            for(Product p: Database.producttable) {
                if (txtProductCode.getText().equalsIgnoreCase(p.getCode())) {
                    p.setDescription(txtProductDescription.getText());
                    p.setQtyOnHand(Integer.parseInt(txtQtyOnHand.getText()));
                    p.setUnitPrice(Double.parseDouble(txtUnitPrice.getText()));
                    new Alert(Alert.AlertType.CONFIRMATION,"Product Saved !.").show();
                    setTableData(searchText);
                    setItemCode();
                    clear();
                }

            }

        }
    }

    public void clear(){
        btnSaveUpdate.setText("Save Product");
        txtProductDescription.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
        setItemCode();
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm", "Dashboard Form");
    }

    public void setProductData(ProductTM tm){
        btnSaveUpdate.setText("Product Update");
        txtProductCode.setText(tm.getCode());
        txtProductDescription.setText(tm.getDescription());
        txtUnitPrice.setText(String.valueOf(tm.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(tm.getQtyOnHand()));
    }


    public void setUi(String location, String title) throws IOException {
        Stage window = (Stage) productFormContext.getScene().getWindow();
        window.setTitle(title);
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
    }

    private void setItemCode() {
        // get last saved customer
        // catch the id c001
        // separate the number from the character
        // increment the separated number
        // concat the character again to the incremented number
        //set customer
        if(!Database.producttable.isEmpty()){
            Product p = Database.producttable.get(Database.producttable.size()-1);
            String id = p.getCode();//c-001
            String dataArray[] = id.split("-");
            id = dataArray[1];
            int oldNumber = Integer.parseInt(id);
            oldNumber++;
            if(oldNumber<9){
                txtProductCode.setText("C-00"+oldNumber);
            } else if (oldNumber<99) {
                txtProductCode.setText("C-0"+oldNumber);
            }else {
                txtProductCode.setText("C-"+oldNumber);
            }
        }else {
            txtProductCode.setText("C-001");
        }
    }
}
