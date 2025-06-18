/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.nathdev.e_commerce.DTO;

import java.math.BigDecimal;

import com.nathdev.e_commerce.model.Product;

import lombok.Data;

@Data
class CartItemDto {
   private Long itemId;
   private Integer quantity;
   private BigDecimal unitPrice;
   private ProductDto product;
}
