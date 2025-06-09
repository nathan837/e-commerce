package com.nathdev.e_commerce.service.cart;

import com.nathdev.e_commerce.model.CartItem;

public interface ICartItemService {

    void addItemToCart(Long cartId , Long productId, int quantity);
    void removeItemToCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId , Long prodcutId , int quantity);
    CartItem getCartItem(Long cartId ,Long productId);
}
