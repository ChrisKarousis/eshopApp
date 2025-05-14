package com.ckarousis.eshopapp.controllers;

import com.ckarousis.eshopapp.model.Product;
import com.ckarousis.eshopapp.model.Review;
import com.ckarousis.eshopapp.repository.ProductRepository;
import com.ckarousis.eshopapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/eshop/reviews")
public class ReviewController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}")
    public String showReviewForm(@PathVariable Long productId, Model model) {
        Product product = productService.getProduct(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("product", product);
        model.addAttribute("review", new Review());
        return "review-form";
    }

}