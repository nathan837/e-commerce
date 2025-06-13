package com.nathdev.e_commerce.service.order;

import java.util.List;

import com.nathdev.e_commerce.model.Cart;
import com.nathdev.e_commerce.model.Order;

public interface IOrderService {
    
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
    List<Order> getUserOrders(Long userId);
    

    
}
