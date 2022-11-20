package com.seekescloud.pos.model;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private String orderId;
    private Date placeDate;
    private double total;
    private String customer;
    private ArrayList<CartItem> items;

    public Order() {
    }

    public Order(String orderId, Date placeDate, double total, String customer, ArrayList<CartItem> items) {
        this.orderId = orderId;
        this.placeDate = placeDate;
        this.total = total;
        this.customer = customer;
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", placeDate=" + placeDate +
                ", total=" + total +
                ", customer='" + customer + '\'' +
                ", items=" + items +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getPlaceDate() {
        return placeDate;
    }

    public void setPlaceDate(Date placeDate) {
        this.placeDate = placeDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartItem> items) {
        this.items = items;
    }
}
