package com.ckarousis.eshopapp.controllers;

import com.ckarousis.eshopapp.model.Product;
import com.ckarousis.eshopapp.model.Review;
import com.ckarousis.eshopapp.repository.ProductRepository;
import com.ckarousis.eshopapp.services.ProductService;
import com.ckarousis.eshopapp.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/eshop/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<String> submitReview(@RequestBody Review review) {
        if (review.getRating() < 1 || review.getRating() > 5) {
            return ResponseEntity.badRequest().body("Rating must be between 1 and 5.");
        }

        reviewService.saveReview(review);

        return ResponseEntity.ok("Review submitted successfully");
    }

    @GetMapping("/{productId}")
    public String showReviewForm(@PathVariable Long productId, Model model) {
        Product product = productService.getProduct(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("product", product);
        return "review-form";
    }

}