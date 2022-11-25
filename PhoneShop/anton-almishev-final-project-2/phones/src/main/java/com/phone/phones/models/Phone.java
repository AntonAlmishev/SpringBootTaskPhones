package com.phone.phones.models;

import java.time.LocalDate;
import java.util.Date;


public class Phone {

    private int phoneId;
    private String brand;
    private String modelPhone;
    private   LocalDate date;

    public Phone() {
    }

    public Phone(int phoneId, String brand, String modelPhone, LocalDate date) {
        this.phoneId = phoneId;
        this.brand = brand;
        this.modelPhone = modelPhone;
        this.date = date;
    }

    public int getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "phoneId=" + phoneId +
                ", brand='" + brand + '\'' +
                ", modelPhone='" + modelPhone + '\'' +
                ", date=" + date +
                '}';
    }

}
