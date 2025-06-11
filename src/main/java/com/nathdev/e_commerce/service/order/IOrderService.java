package com.nathdev.e_commerce.service.order;

import com.nathdev.e_commerce.model.Cart;
import com.nathdev.e_commerce.model.Order;

public interface IOrderService {
    
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
    
}
