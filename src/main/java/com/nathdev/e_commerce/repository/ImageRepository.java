package com.nathdev.e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nathdev.e_commerce.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{
    
    List<Image> findByProductId(Long productId);
    
}