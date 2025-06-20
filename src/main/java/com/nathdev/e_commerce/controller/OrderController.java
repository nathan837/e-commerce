package com.nathdev.e_commerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nathdev.e_commerce.DTO.OrderDto;
import com.nathdev.e_commerce.model.Order;
import com.nathdev.e_commerce.response.ApiResponse;
import com.nathdev.e_commerce.service.order.IOrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final IOrderService orderService;

@PostMapping("/order")
  public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
    try{
    Order order = orderService.placeOrder(userId);
    OrderDto orderDto = orderService.convertToDto(order);
    return ResponseEntity.ok( new ApiResponse("Item Order Success", orderDto));
    }catch(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occured! ", e.getMessage()));
    }
}
@GetMapping("/{orderId}/order")
  public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
    try {        
        OrderDto order = orderService.getOrder(orderId);
        return ResponseEntity.ok(new ApiResponse("Item Order Success", order));
  } catch (Exception e) {
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Oops!" , e.getMessage()));
    }
  }

  @GetMapping("/{userId}/order")
  public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
    try {        
        List<OrderDto> order = orderService.getUserOrders(userId);
        return ResponseEntity.ok(new ApiResponse("Item Order Success", order));
    } catch (Exception e) {
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Oops!" , e.getMessage()));
    }

  }
 

}
