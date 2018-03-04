package com.example.demo.dto;

public class RegularPrice extends Price{

    @Override
    public Float getCalculatedPrice(float price) {
        return price * 1.1F;
    }
}
