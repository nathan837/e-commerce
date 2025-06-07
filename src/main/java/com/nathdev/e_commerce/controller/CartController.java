package com.nathdev.e_commerce.controller;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nathdev.e_commerce.exceptions.ResourceNotFoundException;
import com.nathdev.e_commerce.model.Cart;
import com.nathdev.e_commerce.response.ApiResponse;
import com.nathdev.e_commerce.service.cart.ICartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart (@PathVariable Long cartId){
        try{
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(new ApiResponse("SUCCESS" , cart));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
        cartService.clearCart(cartId);
        return ResponseEntity.ok(new ApiResponse("Clear cart SUCCESS", null));
    }
    @GetMapping("")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId){
        BigDecimal totalPrice = cartService.getTotalPrice(cartId);
        return ResponseEntity.ok(new ApiResponse("Total Price" , totalPrice));
    }
}
 