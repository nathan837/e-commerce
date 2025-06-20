package com.nathdev.e_commerce.service.cart;

import java.math.BigDecimal;

import com.nathdev.e_commerce.model.Cart;
import com.nathdev.e_commerce.model.User;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Cart initializeNewCart(User user);
    Cart getCartByUserId (Long userId);
    
}
