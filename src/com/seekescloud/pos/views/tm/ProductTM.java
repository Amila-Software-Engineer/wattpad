package com.seekescloud.pos.views.tm;


import javafx.scene.control.Button;

public class ProductTM {
    private String code;
    private String description;
    private double unitPrice;
    private int  qtyOnHand;
    private String qr;
    private Button btn;

    public ProductTM() {
    }

    public ProductTM(String code, String description, double unitPrice, int qtyOnHand, String qr, Button btn) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.qr = qr;
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "ProductTM{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", qr='" + qr + '\'' +
                ", btn=" + btn +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
