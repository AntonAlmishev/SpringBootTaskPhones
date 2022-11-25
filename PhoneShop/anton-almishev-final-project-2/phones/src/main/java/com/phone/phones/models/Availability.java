package com.phone.phones.models;

import java.math.BigDecimal;
import java.util.Date;


public class Availability  {

    private int stockId;
    private int phoneId;
    private String shop;
    private int quantity;
    private BigDecimal price;

    public Availability() {
    }

    public Availability(int stockId, int phoneId, String shop, int quantity, BigDecimal price) {
        this.stockId = stockId;
        this.phoneId = phoneId;
        this.shop = shop;
        this.quantity = quantity;
        this.price = price;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Availability{" +
                "stockId=" + stockId +
                ", phoneId=" + phoneId +
                ", shop=" + shop + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
