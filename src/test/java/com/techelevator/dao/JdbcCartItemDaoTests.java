package com.techelevator.dao;

import com.techelevator.model.CartItem;
import com.techelevator.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JdbcCartItemDaoTests extends BaseDaoTests {

    private static final User USER_1 = new User("user1","user1","ROLE_USER", "User1 Name", "User1 address", "Cleveland", "OH", "44123");
    private static final User USER_2 = new User("user2","user2","ROLE_USER", "User2 Name", null, "Beverly Hills", "CA", "90210");

    private static final CartItem CART_ITEM_1 =
            new CartItem(1, 1, 2, 2);
    private static final CartItem CART_ITEM_2 =
            new CartItem(2, 1, 3, 3);
    private static final CartItem CART_ITEM_3 =
            new CartItem(3, 2, 5, 1);
    private static final CartItem CART_ITEM_4 =
            new CartItem(4, 2, 3, 3);

    private JdbcCartItemDao dao;

    @Before
    public void setup() {
        dao = new JdbcCartItemDao(dataSource);
    }

    @Test
    public void getCartItemById_returns_correct_cart_item() {
        CartItem result = dao.getCartItemById(CART_ITEM_1.getCartItemId());
        Assert.assertNotNull("getCartItemById returned null", result);
        assertCartItemsMatch("getCartItemById returned wrong or partial data", CART_ITEM_1, result);
    }

    @Test
    public void getCartItemByUserId_returns_correct_list() {
        List<CartItem> results = dao.getCartItemsByUserId(CART_ITEM_2.getUserId());
        Assert.assertEquals("getCartItemByUserId failed to return all sales", 2, results.size());
        assertCartItemsMatch("getCartItemByUserId returned wrong or partial data", CART_ITEM_1, results.get(0));
        assertCartItemsMatch("getCartItemByUserId returned wrong or partial data", CART_ITEM_2, results.get(1));
    }

    @Test
    public void create_created_new_cart_item_with_expected_values_and_id() {
        CartItem newItem = new CartItem();
        newItem.setUserId(CART_ITEM_1.getUserId());
        newItem.setProductId(4);
        newItem.setQuantity(2);

        CartItem createdCartItem = dao.create(newItem);
        Assert.assertNotNull("create returned null", createdCartItem);

        int newId = createdCartItem.getCartItemId();
        Assert.assertTrue("create returned a cart item without an id", newId > 0);

        newItem.setCartItemId(newId);
        newItem.setQuantity(CART_ITEM_1.getQuantity());
        assertCartItemsMatch("create does not create cart item with expected values and id", newItem, createdCartItem);
    }

    @Test
    public void updated_cart_item_has_expected_values_when_retrieved() {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(CART_ITEM_3.getCartItemId());

        cartItem.setUserId(1);
        cartItem.setProductId(7);
        cartItem.setQuantity(3);

        CartItem updatedItem = dao.update(cartItem);
        int newId = updatedItem.getCartItemId();
        CartItem retrievedItem = dao.getCartItemById(newId);

        assertCartItemsMatch("update did not save correct data", updatedItem, retrievedItem);
    }


    @Test
    public void delete_removes_cart_item() {
        dao.delete(CART_ITEM_4.getCartItemId());
        CartItem cartItem = dao.getCartItemById(CART_ITEM_4.getCartItemId());
        Assert.assertNull(cartItem);
    }

    private void assertCartItemsMatch(String message, CartItem expected, CartItem actual) {
        Assert.assertEquals("CartItem Id does not match expected value", expected.getCartItemId(), actual.getCartItemId());
        Assert.assertEquals("UserId does not match expected value", expected.getUserId(), actual.getUserId());
        Assert.assertEquals("ProductId does not match expected value", expected.getProductId(), actual.getProductId());
        Assert.assertEquals("Quantity does not match expected value", expected.getQuantity(), actual.getQuantity());
    }

}
