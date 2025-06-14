package com.nathdev.e_commerce.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.nathdev.e_commerce.model.Category;

import lombok.Data;

@Data
public class OrderDto {
      private Long id;
    private Long userId;
    private  LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemDto> item;
}
