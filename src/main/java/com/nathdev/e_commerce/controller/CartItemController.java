package com.nathdev.e_commerce.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nathdev.e_commerce.exceptions.ResourceNotFoundException;
import com.nathdev.e_commerce.response.ApiResponse;
import com.nathdev.e_commerce.service.cart.ICartItemService;
import com.nathdev.e_commerce.service.cart.ICartService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("${api.prefix}/cartItem")
@RequiredArgsConstructor
public class CartItemController {
    
        private final ICartItemService cartItemService;
        private final ICartService cartService;

@PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId , @RequestParam Long productId , @RequestParam Integer quantity){
        try{
            if (cartId == null) {
               cartId =  cartService.initializeNewCart();


            }
          cartItemService.addItemToCart(cartId, productId, quantity);
          return ResponseEntity.ok(new ApiResponse("Add Item SUCCESS", null));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
@DeleteMapping("/cart/{cartId}/item{itemId}/remove")  
    public ResponseEntity<ApiResponse> removeItemFromCart(@pathvariable Long cartId , @pathvariable Long itemId){
        try{
        cartItemService.removeItemToCart(cartId, itemId);
        return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
@PutMapping("/cart/{cartId}/item/{itemId}/update")
  public ResponseEntity<ApiResponse> updateItemQuantity(@pathvariable Long cartId , @pathvariable Long itemId, @RequestParam Integer quantity){
     try {
        cartItemService.updateItemQuantity(cartId, itemId, quantity);
        return ResponseEntity.ok( new ApiResponse("UPDATE ITEM SUCCESS", null));
     } catch (ResourceNotFoundException e) {
        // TODO: handle exceptio
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse( e.getMessage(), null));
     }
  }

}