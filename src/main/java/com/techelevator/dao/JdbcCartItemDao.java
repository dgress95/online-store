package com.techelevator.dao;

import com.techelevator.model.CartItem;
import com.techelevator.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCartItemDao implements CartItemDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcCartItemDao(DataSource datasource) {
        jdbcTemplate = new JdbcTemplate(datasource);
    }
    @Override
    public List<CartItem> getCartItemsByUserId(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "select cart_item_id, user_id, product_id, quantity " +
                "from cart_item " +
                "where user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            cartItems.add(mapRowToCartItem(results));
        }
        return cartItems;
    }

    @Override
    public CartItem getCartItemById(int cartItemId) {
        CartItem cartItem = null;
        String sql = "select cart_item_id, user_id, product_id, quantity " +
                "from cart_item " +
                "where cart_item_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cartItemId);
        if (results.next()) {
            cartItem = mapRowToCartItem(results);
        }
        return cartItem;
    }

    @Override
    public CartItem create(CartItem newItem) {
        Integer id;
        String sql = "INSERT INTO cart_item (user_id, product_id, quantity) " +
                "VALUES (?, ?, ?) " +
                "RETURNING cart_item_id;";
        id = jdbcTemplate.queryForObject(sql, Integer.class, newItem.getUserId(), newItem.getProductId(),
                newItem.getQuantity());
        return getCartItemById(id);
    }

    public CartItem update(CartItem modifiedCartItem) {
        String sql = "UPDATE cart_item SET user_id=?, product_id=?, quantity=? " +
                "WHERE cart_item_id = ?;";
        jdbcTemplate.update(sql, modifiedCartItem.getUserId(), modifiedCartItem.getProductId(), modifiedCartItem.getQuantity(),
                modifiedCartItem.getCartItemId());
        return getCartItemById(modifiedCartItem.getCartItemId());
    }

    public int delete(int cartItemId) {
        int count = jdbcTemplate.update("DELETE FROM cart_item WHERE cart_item_id=?", cartItemId);
        count += jdbcTemplate.update("DELETE FROM cart_item WHERE cart_item_id=?", cartItemId);
        return count;
    }

    public void deleteAll(int userId) {
        jdbcTemplate.update("DELETE FROM cart_item " +
                "WHERE user_id = ?;", userId);
    }

    public CartItem mapRowToCartItem(SqlRowSet rowSet) {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(rowSet.getInt("cart_item_id"));
        cartItem.setUserId(rowSet.getInt("user_id"));
        cartItem.setProductId(rowSet.getInt("product_id"));
        cartItem.setQuantity(rowSet.getInt("quantity"));
        return cartItem;
    }
}
