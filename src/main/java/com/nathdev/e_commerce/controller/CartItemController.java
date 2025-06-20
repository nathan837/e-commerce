package com.nathdev.e_commerce.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nathdev.e_commerce.exceptions.ResourceNotFoundException;
import com.nathdev.e_commerce.model.Cart;
import com.nathdev.e_commerce.model.User;
import com.nathdev.e_commerce.response.ApiResponse;
import com.nathdev.e_commerce.service.cart.ICartItemService;
import com.nathdev.e_commerce.service.cart.ICartService;
import com.nathdev.e_commerce.service.user.IUserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("${api.prefix}/cartItem")
@RequiredArgsConstructor
public class CartItemController {
    
        private final ICartItemService cartItemService;
        private final ICartService cartService;
        private final IUserService userService;

@PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart( @RequestParam Long productId , @RequestParam Integer quantity){
        try{
              User user = userService.getUserById(1L);
              Cart cart = cartService.initializeNewCart(user);
            
          cartItemService.addItemToCart(cart.getId(), productId, quantity);
          return ResponseEntity.ok(new ApiResponse("Add Item SUCCESS", null));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
@DeleteMapping("/cart/{cartId}/item{itemId}/remove")  
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId , @PathVariable Long itemId){
        try{
        cartItemService.removeItemToCart(cartId, itemId);
        return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
@PutMapping("/cart/{cartId}/item/{itemId}/update")
  public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId , @PathVariable Long itemId, @RequestParam Integer quantity){
     try {
        cartItemService.updateItemQuantity(cartId, itemId, quantity);
        return ResponseEntity.ok( new ApiResponse("UPDATE ITEM SUCCESS", null));
     } catch (ResourceNotFoundException e) {
        // TODO: handle exceptio
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse( e.getMessage(), null));
     }
  }

}