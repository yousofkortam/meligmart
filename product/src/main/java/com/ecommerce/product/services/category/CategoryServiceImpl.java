package com.ecommerce.product.services.category;

import com.ecommerce.product.dto.CategoryDto;
import com.ecommerce.product.mappers.CategoryMapper;
import com.ecommerce.product.models.Category;
import com.ecommerce.product.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Category with id " + id + " not found")
        );
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(
                () -> new NoSuchElementException("Category with name " + name + " not found")
        );
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return categoryMapper.toDto(getCategory(id));
    }

    @Override
    public CategoryDto addCategory(CategoryDto category) {
        checkCategoryExists(category.getName());
        Category newCategory = categoryMapper.toEntity(category);
        newCategory = categoryRepository.save(newCategory);
        return categoryMapper.toDto(newCategory);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto category) {
        checkCategoryExists(category.getName());
        Category oldCategory = getCategory(id);
        oldCategory.setName(category.getName());
        oldCategory = categoryRepository.save(oldCategory);
        return categoryMapper.toDto(oldCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public void checkCategoryExists(String name) {
        if (existsByName(name)) {
            throw new IllegalArgumentException("Category with name " + name + " already exists");
        }
    }
}
