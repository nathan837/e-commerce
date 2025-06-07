package com.nathdev.e_commerce.service.category;

import java.util.List;

import com.nathdev.e_commerce.model.Category;

public interface ICategoryService {
      
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category , Long id);
    Category deleteCategoryById(Long id);


}
