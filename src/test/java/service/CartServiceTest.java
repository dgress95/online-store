package service;

import com.techelevator.dao.CartItemDao;
import com.techelevator.dao.ProductDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.CartItem;
import com.techelevator.model.User;
import com.techelevator.service.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

    private static final User USER_1 = new User(1, "user1","user1","ROLE_USER", "User1 Name", "User1 address", "Cleveland", "OH", "44123");
    private static final User USER_2 = new User(2, "user2","user2","ROLE_USER", "User2 Name", null, "Beverly Hills", "CA", "90210");

    private static final CartItem CART_ITEM_1 =
            new CartItem(1, 1, 2, 2);
    private static final CartItem CART_ITEM_2 =
            new CartItem(2, 1, 3, 3);
    private static final CartItem CART_ITEM_3 =
            new CartItem(3, 2, 5, 1);
    private static final CartItem CART_ITEM_4 =
            new CartItem(4, 2, 3, 3);

    @Mock
    private Principal principal;

    @Mock
    private CartItemDao mockCartItemDao;

    @Mock
    private ProductDao mockProductDao;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private CartService cartItemService;

    @Test
    public void getCartItemsForUser_returns_correct_list_for_user() {
        setupMocksForUser(USER_1);
        List<CartItem> expected = new ArrayList<>();
        when(mockCartItemDao.getCartItemsByUserId(USER_1.getId())).thenReturn(expected);

        List<CartItem> actual = cartItemService.getCartItemsForUser(principal);

        assertEquals("getCartItemsForUser did not return correct list for user.", expected, actual);
    }

    @Test
    public void addItem_creates_cart_item_associated_to_logged_in_user() {
        setupMocksForUser(USER_1);
        CartItem expected = new CartItem();
        expected.setUserId(USER_1.getId());
        mockCartItemDao.create(expected);


        CartItem cartItemData = new CartItem();
        cartItemData.setUserId(999); // not the user id
        CartItem actual = cartItemService.addItem(cartItemData, principal);
        assertNotNull("addItem returned null", actual);
    }

    @Test
    public void removeItem_removes_cart_item_for_user() {
        setupMocksForUser(USER_1);
        CartItem cartItem = new CartItem();
        cartItem.setUserId(99);
        cartItem.setUserId(USER_1.getId());
        when(mockCartItemDao.getCartItemById(cartItem.getCartItemId())).thenReturn(cartItem);
        when(mockCartItemDao.delete(cartItem.getCartItemId())).thenReturn(3);

        boolean result = cartItemService.removeItem(cartItem.getCartItemId(), principal);
        assertTrue("Cart Item was not deleted", result);
    }

    @Test
    public void updateItem_updates_users_item_correctly() {
        setupMocksForUser(USER_2);
        CartItem original = new CartItem(8, USER_2.getId(), 3, 1);
        when(mockCartItemDao.getCartItemById(original.getCartItemId())).thenReturn(original);

        CartItem expected = new CartItem(original.getCartItemId(), original.getUserId(), 2, 2);
        CartItem updateData = new CartItem(original.getCartItemId(), original.getUserId(), 2, 2);
        when(mockCartItemDao.update(refEq(updateData))).thenReturn(expected);

        CartItem actual = cartItemService.updateItem(updateData, principal);
        assertNotNull("updateItem returned null.", actual);
    }

    private void setupMocksForUser(User user) {
        String username = user.getUsername();
        when(principal.getName()).thenReturn(username);
        when(userDao.findByUsername(username)).thenReturn(user);
    }



}
