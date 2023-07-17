package com.techelevator.dao;

import com.techelevator.model.CartItem;
import com.techelevator.model.Product;

import java.util.List;

public interface CartItemDao {

    /**
     * Get all cart items associated with a user, ordered by cart_item_id.
     * @param userId The id of the user to get cart items from.
     * @return All cart items for a user as CartItem objects in a List.
     */
    List<CartItem> getCartItemsByUserId(int userId);

    /**
     * Retrieve cart item from datastore.
     *
     * @param cartItemId the cart item id to retrieve.
     * @return The retrieved cart item object with its new id value filled in.
     */
    CartItem getCartItemById(int cartItemId);

    /**
     * Adds a new cart item to the datastore.
     *
     * @param newItem the cart item object to add.
     * @return The added cart item object with its new id value filled in.
     */
    CartItem create(CartItem newItem);


    int delete(int cartItemId);

    void deleteAll(int userId);

    CartItem update(CartItem modifiedCartItem);
}
