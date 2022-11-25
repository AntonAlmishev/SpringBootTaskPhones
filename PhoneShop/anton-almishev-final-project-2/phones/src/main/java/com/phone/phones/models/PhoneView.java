package com.phone.phones.models;




import java.math.BigDecimal;


public class PhoneView {

    private String brand;
    private String modelPhone;
    private BigDecimal price;

    public PhoneView() {
    }

    public PhoneView(String brand, String modelPhone, BigDecimal price) {
        this.brand = brand;
        this.modelPhone = modelPhone;
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelPhone() {
        return modelPhone;
    }

    public void setModelPhone(String modelPhone) {
        this.modelPhone = modelPhone;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PhoneView{" +
                "brand='" + brand + '\'' +
                ", modelPhone='" + modelPhone + '\'' +
                ", price=" + price +
                '}';
    }
}
