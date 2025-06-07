package com.nathdev.e_commerce.service.cart;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.nathdev.e_commerce.exceptions.ResourceNotFoundException;
import com.nathdev.e_commerce.model.Cart;
import com.nathdev.e_commerce.model.CartItem;
import com.nathdev.e_commerce.model.Product;
import com.nathdev.e_commerce.repository.CartItemRepository;
import com.nathdev.e_commerce.repository.CartRepository;
import com.nathdev.e_commerce.service.product.I_ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{
    
    private final CartItemRepository cartItemRepository;
    private final I_ProductService productService;
    private final CartRepository cartRepository;
    private ICartService cartService;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {

      Cart cart = cartService.getCart(cartId);
      Product product = productService.getProductById(productId);
      CartItem cartItem = cart.getItems()
      .stream().filter(item -> item.getProduct().equals(productId)).findFirst().orElse(new CartItem());

      if (cartItem.getId() == null) {
          cartItem.setCart(cart);
          cartItem.setProduct(product);
          cartItem.setQuantity(quantity);
          cartItem.setUnitPrice(product.getPrice());
      }
      else{
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
      }
      cartItem.setTotalPrice();
      cart.addItem(cartItem); 
      cartItemRepository.save(cartItem);
      cartRepository.save(cart);

    }

    @Override
    public void removeItemTOcart(Long cartId, Long productId) {
      Cart cart = cartService.getCart(cartId);
      CartItem itemToRemove = getCartItem(cartId, productId);
       cart.removeItem(itemToRemove);
       cartRepository.save(cart);

    }

    @Override
    public void updateItemQuantity(Long cartId, Long prodcutId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getItem().stream().filter(item -> item.getProduct().getId().equals(productId))
        .findFirst().ifPresent(item -> {item.setQuantity(quantity); item.setUnitPrice(item.getProduct().getPrice());
            item.setTotalPrice();
        });
        BigDecimal totalAmount = cart.getTotalAmount(); 
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart); 
    }
    @Override
    public CartItem getCartItem(Long cartId , Long productId){
        Cart cart = cartService.getCart(cartId);
      CartItem itemToRemove = cart.getItems().stream().filter(item -> item.getProduct().getId()
      .equals(productId)).findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }
}
 