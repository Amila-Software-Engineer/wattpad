package com.seekescloud.pos.views.tm;


public class ItemDetailTM {
    private String productCode;
    private double unitPrice;
    private int  qty;
    private double totalCost;

    public ItemDetailTM() {
    }

    public ItemDetailTM(String productCode, double unitPrice, int qty, double totalCost) {
        this.productCode = productCode;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "OrderDetailTM{" +
                "productCode='" + productCode + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", totalCost=" + totalCost +
                '}';
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
