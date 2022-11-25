package com.phone.phones.dto;

import java.math.BigDecimal;

public class AvailabilityRequest {

    private int phoneId;
    private String shop;
    private BigDecimal price;
    private int quantity;

    public AvailabilityRequest() {
    }

    public AvailabilityRequest(int phoneId, String shop, BigDecimal price, int quantity) {
        this.phoneId = phoneId;
        this.shop = shop;
        this.price = price;
        this.quantity = quantity;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "AvailabilityRequest{" +
                "phoneId=" + phoneId +
                ", shop='" + shop + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';

    }
}
