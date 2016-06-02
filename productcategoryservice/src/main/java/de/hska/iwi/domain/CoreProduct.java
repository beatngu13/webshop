package de.hska.iwi.domain;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This class contains details about products.
 */
@Entity
@Table(name = "product")
public class CoreProduct implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int id;
    private String name;
    private double price;
    private int category;
    private String details;

    public CoreProduct() {}

    public CoreProduct(String name, double price, int category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public CoreProduct(String name, double price, int category, String details) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.details = details;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategory() {
        return this.category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
