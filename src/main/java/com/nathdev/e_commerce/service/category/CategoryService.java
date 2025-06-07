package com.nathdev.e_commerce.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Service;


import com.nathdev.e_commerce.exceptions.AlreadyExistsException;
import com.nathdev.e_commerce.exceptions.ResourceNotFoundException;
import com.nathdev.e_commerce.model.Category;
import com.nathdev.e_commerce.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
               
               private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                     .orElseThrow(() -> 
                                   new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
           return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
          return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
         return Optional.ofNullable(category).filter(c -> !categoryRepository.existsByName(c.getName()))
          .map(categoryRepository :: save).orElseThrow(() 
          -> new AlreadyExistsException(category.getName()+"already exists"));
    }

    @Override
    public Category updateCategory(Category category , Long id) {
          return Optional.ofNullable(getCategoryById(id))
          .map(oldCategory -> {oldCategory.setName(category.getName());
           return categoryRepository.save(oldCategory);
        }).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public Category deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        categoryRepository.delete(category);
        return category;
    }
    
    
}
