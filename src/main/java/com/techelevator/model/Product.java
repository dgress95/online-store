package com.techelevator.model;

import org.springframework.security.core.parameters.P;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class Product {
    private int productId;

    @NotBlank( message = "The field 'sku' is required.")
    private String productSku;
    @NotBlank( message = "The field 'name' is required.")
    private String name;
    private String description;

    @NotBlank( message = "The field 'price' is required.")
    private BigDecimal price;
    private String imageName;

    public Product(){};

    public Product(int productId, String productSku, String name, String description, BigDecimal price, String imageName) {
        this.productId = productId;
        this.productSku = productSku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageName = imageName;
    }

    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public String getProductSku() {
        return productSku;
    }
    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getImageName() {
        return imageName;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productSku='" + productSku + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
