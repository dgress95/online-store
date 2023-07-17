package com.techelevator.model;

import javax.validation.constraints.NotBlank;

// did not have time to build out the wishlist functions
// might come back to this later

public class Wishlist {
    private int wishlistId;
    private int userId;
    @NotBlank( message = "The field 'name' is required.")
    private String name;

    public Wishlist() {}

    public Wishlist(int wishlistId, int userId, String name) {
        this.wishlistId = wishlistId;
        this.userId = userId;
        this.name = name;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "wishlistId=" + wishlistId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
