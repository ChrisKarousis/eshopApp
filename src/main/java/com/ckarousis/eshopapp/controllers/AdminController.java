package com.ckarousis.eshopapp.controllers;

import com.ckarousis.eshopapp.model.Category;
import com.ckarousis.eshopapp.model.Product;
import com.ckarousis.eshopapp.services.CategoryService;
import com.ckarousis.eshopapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/eshop/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/dashboard";
    }

    @GetMapping("/add-product")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute Product product) {
        System.out.println("POST");
        productService.createProduct(product);
        return "redirect:/eshop/admin/dashboard";
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/eshop/admin/dashboard";
    }

    @GetMapping("/add-category")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/add-category";
    }

    @PostMapping("/add-category")
    public String addCategory(@ModelAttribute Category category) {
        categoryService.createCategory(category);
        return "redirect:/eshop/admin/dashboard";
    }

    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/eshop/admin/dashboard";
    }
}

