package com.ecommerce.product.services.category;


import com.ecommerce.product.dto.CategoryDto;
import com.ecommerce.product.models.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    Category getCategory(Long id);
    Category getCategoryByName(String name);
    boolean existsByName(String name);
    CategoryDto getCategoryById(Long id);
    CategoryDto addCategory(CategoryDto category);
    CategoryDto updateCategory(Long id, CategoryDto category);
    void deleteCategory(Long id);
}
