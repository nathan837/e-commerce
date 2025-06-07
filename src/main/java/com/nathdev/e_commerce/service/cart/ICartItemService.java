package com.nathdev.e_commerce.service.cart;

public interface ICartItemService {

    void addItemToCart(Long cartId , Long productId, int quantity);
    void removeItemTOcart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId , Long prodcutId , int quantity);
}
