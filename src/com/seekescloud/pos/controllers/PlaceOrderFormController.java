package com.seekescloud.pos.controllers;

import com.jfoenix.controls.JFXButton;
import com.seekescloud.pos.db.Database;
import com.seekescloud.pos.model.CartItem;
import com.seekescloud.pos.model.Customer;
import com.seekescloud.pos.model.Order;
import com.seekescloud.pos.model.Product;
import com.seekescloud.pos.views.tm.CartTM;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

public class PlaceOrderFormController {
    public AnchorPane placeOrderFormContext;
    public ComboBox<String> cmbCustomerCode;
    public ComboBox<String> cmbItemCode;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TextField txtOrderId;
    public TextField txtItemCount;
    public TextField txtOrderDate;
    public TextField txtOrderTotal;
    public TextField txtQty;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQuantityOnHand;
    public TableView<CartTM> tblCart;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colTotal;
    public TableColumn colOption;
    public JFXButton addToCartButton;


    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadCustomerIds();
        loadItemIds();
        setDate();
        generateOrderId();

        //==================== listener ================
        cmbCustomerCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCustomerData(newValue);
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            setProductData(newValue);
        } );
    }

    private void setDate() {
        txtOrderDate.setText(new SimpleDateFormat("YYYY-MM-DD").format(new Date()));
    }

    private void setProductData(String code) {
        txtQty.requestFocus();
        Product product = Database.producttable.stream().filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
        if(product != null){
            txtDescription.setText(product.getDescription());
            txtUnitPrice.setText(String.valueOf(product.getUnitPrice()));
            txtQuantityOnHand.setText(String.valueOf(product.getQtyOnHand()));
        }
    }

    private void setCustomerData(String id) {
        Stream<Customer> customerStream = Database.customertable.stream().filter(e->e.getId().equals(id));
        Optional<Customer> first = customerStream.findFirst();
        if(first.isPresent()){
            Customer customer = first.get();
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(String.valueOf(customer.getSalary()));
        }
    }


    private void loadItemIds() {
        ObservableList<String>  oblList = FXCollections.observableArrayList();
        for(Product p: Database.producttable){
            oblList.add(p.getCode());
        }

        cmbItemCode.setItems(oblList);
    }

    private void loadCustomerIds() {
        ObservableList<String>  oblList = FXCollections.observableArrayList();
        for(Customer c: Database.customertable){
            oblList.add(c.getId());
        }

        cmbCustomerCode.setItems(oblList);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm", "Dashboard Form");
    }

    public void setUi(String location, String title) throws IOException {
        Stage window = (Stage) placeOrderFormContext.getScene().getWindow();
        window.setTitle(title);
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"))));
    }

    ObservableList<CartTM> tmList = FXCollections.observableArrayList();
    public void AddToCartOnAction(ActionEvent actionEvent) {
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double total = qty* unitPrice;

        if(!checkQuantity(qty)){
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }

        Button btn =  new Button("Remove");
        CartTM existTm = isExists(cmbItemCode.getValue());
        CartTM tm =  new CartTM(cmbItemCode.getValue(),txtDescription.getText(), Double.parseDouble(txtUnitPrice.getText()), qty, total, btn );

        if(existTm != null){
                // update exist one

            if( !checkQuantity(existTm.getQty() + qty)){
                new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
                return;
            }
            existTm.setQty(existTm.getQty() + qty);
            existTm.setTotal(existTm.getTotal()+ total);

        }else{
            //  add new one
            tmList.add(tm);
        }
        btn.setOnAction(e ->{
            tmList.remove(tm);

            tblCart.setItems(tmList);
            tblCart.refresh();
            setTotalAndCount();
        });

        tblCart.setItems(tmList);
        tblCart.refresh();
        setTotalAndCount();
        clearFields();

    }
    private boolean checkQuantity(int qty){
        if(Integer.parseInt(txtQuantityOnHand.getText())< qty){
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return false;
        }
        return true;
    }

    private void clearFields() {
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQuantityOnHand.clear();
        txtQty.clear();
        cmbItemCode.requestFocus();
    }

    private CartTM isExists(String id){
        return tmList.stream().filter(e ->e.getCode().equals(id)).findFirst().orElse(null);
    }
    private void setTotalAndCount(){
        double cost=0;
        for(CartTM tm: tmList){
            cost += tm.getTotal();
        }
//        tmList.forEach(e ->{
//            txtOrderTotal.setText(String.valueOf(Double.parseDouble(txtOrderTotal.getText())+ e.getTotal()));
//        });
        txtOrderTotal.setText(String.valueOf(cost));
        txtItemCount.setText(String.valueOf(tmList.size()));
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        addToCartButton.fire();
    }

    public void newCustomerONAction(ActionEvent actionEvent) throws IOException {
        setUi("CustomerForm", "Customer Management");
    }

    private void placeOrder(){
        ArrayList<CartItem> items = new ArrayList<>();
        for(CartTM tm: tmList){
            items.add(new CartItem(tm.getCode(),tm.getQty(),tm.getUnitPrice()));
        }
        // create items array list  [all cart data]
        Order order = new Order(txtOrderId.getText(),new Date(),Double.parseDouble(txtOrderTotal.getText()),
                cmbCustomerCode.getValue(),items);
        // create order object
        Database.ordertable.add(order);
        // save order

        if(manageQty(items)){
            new Alert(Alert.AlertType.INFORMATION, "Order Placed !").show();
            generateOrderId();
            setFreshUI();
        }else {
            //error
            new Alert(Alert.AlertType.INFORMATION, "Order Placed failed !").show();
        }
        // update QTY [all items]
    }

    private void generateOrderId() {
        if(!Database.ordertable.isEmpty()){
            Order o = Database.ordertable.get(Database.ordertable.size()-1);
            String id = o.getOrderId();//c-001
            String dataArray[] = id.split("[a-zA-Z]");//A001 a001
            id = dataArray[1];
            int oldNumber = Integer.parseInt(id);
            oldNumber++;
            if(oldNumber<9){
                txtOrderId.setText("B00"+oldNumber);
            } else if (oldNumber<99) {
                txtOrderId.setText("B0"+oldNumber);
            }else {
                txtOrderId.setText("B"+oldNumber);
            }
        }else {
            txtOrderId.setText("B001");
        }
    }

    private void setFreshUI(){
        cmbCustomerCode.setValue(null);
        txtName.clear();
        txtAddress.clear();

        tmList.clear();
        tblCart.refresh();
        txtOrderTotal.setText("0");
        txtItemCount.setText("0");

    }

    private boolean manageQty(ArrayList<CartItem> items){
        for (CartItem i: items){
               Product product = Database.producttable.stream().filter(e-> e.getCode().equals(i.getCode())).findFirst().orElse(null);
               if(product != null){
                       product.setQtyOnHand(product.getQtyOnHand() - i.getQty());
               }{
                   //return false;
               }
        }
       return true;
    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
        placeOrder();
    }
}
