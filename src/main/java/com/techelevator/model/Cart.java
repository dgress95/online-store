package com.techelevator.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private int cartId;
    private int userId;
    private BigDecimal subtotal;
    private BigDecimal totalWithTax;
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart() {};

    public Cart(int cartId, int userId, BigDecimal subtotal, BigDecimal totalWithTax, List<CartItem> cartItems) {
        this.cartId = cartId;
        this.userId = userId;
        this.subtotal = subtotal;
        this.totalWithTax = totalWithTax;
        this.cartItems = cartItems;
    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = new BigDecimal(0.00);
        subtotal = subtotal.setScale(2);
        BigDecimal itemPrice;
        double itemQuantity;
        for (CartItem item : cartItems) {
            itemPrice = item.getProduct().getPrice();
            itemQuantity = item.getQuantity();
            subtotal = subtotal.add(itemPrice.multiply(new BigDecimal(itemQuantity)));
        }
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotalWithTax() {
        return totalWithTax;
    }

    public void setTotalWithTax(BigDecimal totalWithTax) {
        this.totalWithTax = totalWithTax;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }


}
