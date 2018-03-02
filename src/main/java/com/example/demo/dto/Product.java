package com.example.demo.dto;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable{

    private long id;
    private String name;
    private String desc;
    private Float price;
    private Date created;

    public Product() {
    }

    public Product(long id, String name, String desc, Float price, Date created) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.created = created;
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

    public Float getPrice() {
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
}
