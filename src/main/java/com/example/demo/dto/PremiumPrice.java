package com.example.demo.dto;

public class PremiumPrice extends Price {
    @Override
    public Float getCalculatedPrice(float price) {
        return price * 1.5F;
    }
}
