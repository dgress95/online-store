package com.techelevator.model;

import javax.validation.constraints.NotBlank;

public class CartItem {
    private int cartItemId;

    @NotBlank ( message = "Cart Items must include a userId.")
    private int userId;

    private int quantity;

    @NotBlank ( message = "Cart Items must contain a productId.")
    private int productId;

    private Product product;

    public CartItem(){}

    public CartItem(int cartItemId, int userId, int quantity, int productId) {
        this.cartItemId = cartItemId;
        this.userId = userId;
        this.quantity = quantity;
        this.productId = productId;
    }

    public CartItem(int cartItemId, int userId, int quantity, int productId, Product product) {
        this.cartItemId = cartItemId;
        this.userId = userId;
        this.quantity = quantity;
        this.productId = productId;
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
