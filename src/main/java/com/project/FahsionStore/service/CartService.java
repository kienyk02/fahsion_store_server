package com.project.FahsionStore.service;

import com.project.FahsionStore.model.Cart;

import java.util.List;

public interface CartService {
    public Cart addCart(Cart cart);

    public List<Cart> getCartsByUserId(int uid);

    public void updateCartQuantity(int cid, int quantity);

    public void deleteCartById(int cid);

    public void deleteCartByUserId(int uid);

}
