package com.nathdev.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nathdev.e_commerce.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    void deleteAllById(Long id);

}
