package com.ckarousis.eshopapp.services;

import com.ckarousis.eshopapp.model.Category;
import com.ckarousis.eshopapp.model.User;
import com.ckarousis.eshopapp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategory(Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category category) {
        Optional<Category> categoryOptional = getCategory(id);
        if (categoryOptional.isPresent()) {
            Category c = categoryOptional.get();
            c.setName(category.getName());
            c.setDescription(category.getDescription());
            return categoryRepository.save(c);
        } else {
            throw new RuntimeException("Category not found with ID: " + id);
        }
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
