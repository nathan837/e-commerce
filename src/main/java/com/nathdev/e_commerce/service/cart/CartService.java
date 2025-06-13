package com.nathdev.e_commerce.service.cart;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.nathdev.e_commerce.exceptions.ResourceNotFoundException;
import com.nathdev.e_commerce.model.Cart;
import com.nathdev.e_commerce.model.CartItem;
import com.nathdev.e_commerce.repository.CartItemRepository;
import com.nathdev.e_commerce.repository.CartRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
       
        private final CartRepository cartRepository;
        private final CartItemRepository cartItemRepository;
        private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) {
          Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("cart not found!"));
          BigDecimal totalAmount = cart.getTotalAmount();
          cart.setTotalAmount(totalAmount);
          return cartRepository.save(cart);
    }
    
    @Transactional
    @Override
    public void clearCart(Long id) {
       Cart cart = getCart(id);
       cartItemRepository.deleteAllById(id);
       cart.getItems().clear();
       cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
    Cart cart = getCart(id);
    return cart.getTotalAmount();
    }
    
   @Override
    public  Long initializeNewCart(){
        Cart newCart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return cartRepository.save(newCart).getId();

    }

   @Override
   public Cart getCartByUserId(Long userId) {
    // TODO Auto-generated method stub
    return cartRepository.findByUserId(userId);
   }

    
}
