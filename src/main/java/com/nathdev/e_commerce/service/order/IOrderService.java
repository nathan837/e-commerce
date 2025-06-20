package com.nathdev.e_commerce.service.order;

import java.util.List;

import com.nathdev.e_commerce.DTO.OrderDto;
import com.nathdev.e_commerce.model.Cart;
import com.nathdev.e_commerce.model.Order;

public interface IOrderService {
    
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
    OrderDto convertToDto( Order order);

    
}
