package com.ckarousis.eshopapp.controllers;

import com.ckarousis.eshopapp.model.Product;
import com.ckarousis.eshopapp.model.Review;
import com.ckarousis.eshopapp.model.ReviewDTO;
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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/eshop/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

        @PostMapping
        @ResponseBody
        public ResponseEntity<?> submitReview(
                @RequestParam Long productId,
                @RequestParam int rating,
                @RequestParam String comment) {

            Product product = productService.getProduct(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            Review review = new Review();
            review.setProduct(product);
            review.setRating(rating);
            review.setComment(comment);
            review.setCreatedAt(LocalDateTime.now());

            reviewService.saveReview(review);

            return ResponseEntity.ok().build();
        }



    @GetMapping("/{productId}")
    public String showReviewForm(@PathVariable Long productId, Model model) {
        Product product = productService.getProduct(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("product", product);
        //model.addAttribute("review", new Review());
        return "review-form";
    }

    @ResponseBody
    @GetMapping("/average")
    public Map<String, Object> getAverageRating(@RequestParam Long productId) {
        System.out.println("Fetching average for productId: " + productId);
        Double average = reviewService.getAverageRatingByProductId(productId);
        double avgRating = average != null ? average : 0.0;

        Map<String, Object> response = new HashMap<>();
        response.put("averageRating", avgRating);
        System.out.println(response);
        return response;
    }

}