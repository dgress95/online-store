package com.techelevator.controller;

import com.techelevator.model.Cart;
import com.techelevator.model.CartItem;
import com.techelevator.model.User;
import com.techelevator.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cart")
@PreAuthorize("isAuthenticated()")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @CrossOrigin
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping( path = "", method = RequestMethod.GET)
    public Cart displayUserCart(Principal principal) {
        return cartService.displayUserCart(principal);
    }

    @CrossOrigin
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping( path = "/items", method = RequestMethod.POST)
    public CartItem addCartItem(@RequestBody CartItem cartItem, Principal principal) {return cartService.addItem(cartItem, principal);
    }

    @CrossOrigin
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping( path = "/items/{itemId}", method = RequestMethod.DELETE)
    public void removeCartItem(@PathVariable("itemId") int cartItemId, Principal principal) {
        if (!cartService.removeItem(cartItemId, principal)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CartItem not found");
        }
    }

    @CrossOrigin
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping( path = "", method = RequestMethod.DELETE)
    public void clearCart(Principal principal) {
        cartService.deleteAll(principal);
    }


}
