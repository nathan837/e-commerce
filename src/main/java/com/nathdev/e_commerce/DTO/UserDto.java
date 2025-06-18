package com.nathdev.e_commerce.DTO;

import java.util.List;

import com.nathdev.e_commerce.model.Cart;
import com.nathdev.e_commerce.model.Order;

import lombok.Data;

@Data
public class UserDto {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
}