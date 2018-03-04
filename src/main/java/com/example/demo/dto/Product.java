package com.example.demo.dto;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable{

    private long id;
    private String name;
    private String desc;
    private Float price;
    private Date created;
    private String type;
    private Date expire;

    public Product() {
    }

    public Product(long id, String name, String desc, Float price, Date created, String type) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.created = created;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    // Calculating price based on calculator
    public Float getPrice() {
        Price priceCalculator;
        type = type == null ? "Regular" : type;
        switch (type) {
            case "Premium":
                priceCalculator = new PremiumPrice();
                break;
            default:
                priceCalculator = new RegularPrice();
        }
        price = priceCalculator.getCalculatedPrice(price);
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setExpire(Date expire) {
        Price priceCalPrice = new RegularPrice();
        this.expire  = priceCalPrice.getExpirationDate(created);
    }

    public Date getExpire() {
        return expire;
    }
}
