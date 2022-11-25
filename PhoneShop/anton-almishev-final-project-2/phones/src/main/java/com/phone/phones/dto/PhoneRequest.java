package com.phone.phones.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

public class PhoneRequest {
    private String brand;
    private String model;
    private LocalDate date;

    public PhoneRequest() {
    }

    public PhoneRequest(String brand, String model, LocalDate date) {
        this.brand = brand;
        this.model = model;
        this.date = date;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PhoneRequest{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", date=" + date +
                '}';
    }

}
