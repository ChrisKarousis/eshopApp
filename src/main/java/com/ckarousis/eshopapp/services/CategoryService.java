package com.ckarousis.eshopapp.services;

import com.ckarousis.eshopapp.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategory(Long id);
    Category createCategory(Category c);
    Category updateCategory(Long id, Category c);
    void deleteCategory(Long id);
}
