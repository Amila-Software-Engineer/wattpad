package com.seekescloud.pos.views.tm;


import javafx.scene.control.Button;

public class OrderDetailsTM {
    private String orderId;
    private String Date;
    private double total;
    private String Customer;
    private Button btn;

    public OrderDetailsTM() {
    }

    public OrderDetailsTM(String orderId, String date, double total, String customer, Button btn) {
        this.orderId = orderId;
        Date = date;
        this.total = total;
        Customer = customer;
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "OrderDetailsTM{" +
                "orderId='" + orderId + '\'' +
                ", Date='" + Date + '\'' +
                ", total=" + total +
                ", Customer='" + Customer + '\'' +
                ", btn=" + btn +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
