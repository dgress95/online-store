package com.techelevator.service;

import com.techelevator.dao.CartItemDao;
import com.techelevator.dao.ProductDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CartService {

    private CartItemDao cartItemDao;
    private UserDao userDao;
    private ProductDao productDao;

    public CartService(CartItemDao cartItemDao, UserDao userDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    public List<CartItem> getCartItemsForUser(Principal principal) {
        User user = getUser(principal);
        List<CartItem> results = new ArrayList<>();
        results = cartItemDao.getCartItemsByUserId(user.getId());
        return results;
    }

    public Cart getUserCart(Principal principal) {
        User user = getUser(principal);
        Cart userCart = new Cart();
        userCart.setUserId(user.getId());
        List<CartItem> userItems = getCartItemsForUser(principal);
        for (CartItem item : userItems) {
            item.setProduct(productDao.getProductByCartItemId(item.getCartItemId()));
        }
        userCart.setCartItems(userItems);
        return userCart;
    }

    public Cart displayUserCart(Principal principal) {
        Cart userCart = getUserCart(principal);
        userCart.setTotalWithTax(getCartTotalWithTax(principal));
        return userCart;
    }

    public BigDecimal getCartTotalWithTax(Principal principal) {
        TaxService taxService = new TaxService();
        Cart userCart = getUserCart(principal);
        User user = getUser(principal);

        BigDecimal subtotal = userCart.getSubtotal();
        String stateCode = user.getStateCode();
        BigDecimal salesTax = taxService.getSalesTaxForState(stateCode);

        BigDecimal result =subtotal.add((salesTax.divide(new BigDecimal(100)).multiply(subtotal)));
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result;
    }

    public CartItem addItem(CartItem newItem, Principal principal) {        // taking in new item (product id & quantity) and current user
        User user = getUser(principal);                                     // setting user by principal
        Cart userCart = getUserCart(principal);                             // getting user cart (which contains current cart items)
        int quantityToAdd = newItem.getQuantity();                          // get quantity to add if updating
        List<CartItem> userItems = userCart.getCartItems();                 // create List of cart items from user cart

        for (CartItem item : userItems) {                                   // loop through cart items
            if (item.getProductId() == newItem.getProductId()) {            // if cart item product id matches new item product id
                int currentQuantity = item.getQuantity();                   // get current quantity of item
                item.setQuantity(currentQuantity + quantityToAdd);          // set new quantity for item
                cartItemDao.update(item);                                   // calls update method and sets quantity
                return item;                                                // returns item
            }
        }

        newItem.setUserId(user.getId());                                    // if the item does not match the product id above, sets userID
        cartItemDao.create(newItem);                                        // call create method
        newItem.setProduct(productDao.getProductByCartItemId(newItem.getCartItemId()));     // sets product for newly created item
        return newItem;                                                     // returns new item
    }

    public boolean removeItem(int cartItemId, Principal principal) {
        CartItem cartItem = cartItemDao.getCartItemById(cartItemId);
        if (cartItem == null) {
            return false;
        }

        User user = getUser(principal);
        if (cartItem.getUserId() == user.getId()) {
            int deleteCount = cartItemDao.delete(cartItemId);
            return (deleteCount != 0);
        }
        return false;
    }

    public void deleteAll(Principal principal) {
        User user = getUser(principal);
        cartItemDao.deleteAll(user.getId());
    }
    public CartItem updateItem(CartItem modifiedItem, Principal principal) {
        CartItem existingCartItem = cartItemDao.getCartItemById(modifiedItem.getCartItemId());
        if (existingCartItem == null) {
            return null;
        }

        User user = getUser(principal);
        if (existingCartItem.getUserId() == user.getId()) {
            return cartItemDao.update(modifiedItem);
        }
        return null;
    }

    private User getUser(Principal principal) {
        String username = principal.getName();
        User user = userDao.findByUsername(username);
        return user;
    }

}