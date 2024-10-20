package com.project.FahsionStore.controller;

import com.project.FahsionStore.model.Cart;
import com.project.FahsionStore.model.User;
import com.project.FahsionStore.security.CustomUserDetails;
import com.project.FahsionStore.service.CartService;
import com.project.FahsionStore.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@Data
@RequestMapping("/api")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;

    private User getUserRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return userService.findByAccountEmail(customUserDetails.getEmail());
    }

    @PostMapping("/add-cart")
    public Cart addCart(@RequestBody Cart cart) {
        User currentUser = getUserRequest();
        User userCart = new User();
        userCart.setId(currentUser.getId());
        cart.setUser(userCart);
        return cartService.addCart(cart);
    }

    @GetMapping("/carts")
    public List<Cart> getCartsByUser() {
        List<Cart> carts = cartService.getCartsByUserId(getUserRequest().getId());
        return carts != null ? carts : new ArrayList<>();
    }

    @PostMapping("/cart/update/{cart_id}")
    public void updateCartQuantity(
            @PathVariable("cart_id") int cart_id,
            @RequestParam("quantity") int quantity
    ) {
        cartService.updateCartQuantity(cart_id, quantity);
    }

    @DeleteMapping("/cart/delete/{cart_id}")
    public void deleteCartById(@PathVariable("cart_id") int cart_id) {
        cartService.deleteCartById(cart_id);
    }

    @DeleteMapping("/cart/delete-all")
    public void deleteCartByUser() {
        cartService.deleteCartByUserId(getUserRequest().getId());
    }

}
