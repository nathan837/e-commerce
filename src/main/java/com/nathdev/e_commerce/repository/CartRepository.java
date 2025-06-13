package com.nathdev.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nathdev.e_commerce.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

    public Cart findByUserId(Long userId);

}
