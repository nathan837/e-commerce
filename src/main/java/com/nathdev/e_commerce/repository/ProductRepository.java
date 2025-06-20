package com.nathdev.e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nathdev.e_commerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

    List<Product> findByCategory_Name(String categoryName);

    List<Product> findByBrand(String brand);

    List<Product> findByCategory_NameAndBrand(String categoryName, String brand);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    Long countByBrandAndName(String brand, String name);

    boolean existsByNameAndBrand(String name, String brand);
    
}
