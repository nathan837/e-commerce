package com.nathdev.e_commerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.nathdev.e_commerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category , Long> {

    Category findByName(String name);

    boolean existsByName(String name);

    
}














