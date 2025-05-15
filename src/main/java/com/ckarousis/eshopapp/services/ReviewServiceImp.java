package com.ckarousis.eshopapp.services;

import com.ckarousis.eshopapp.model.Product;
import com.ckarousis.eshopapp.model.Review;
import com.ckarousis.eshopapp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImp implements ReviewService{
    @Autowired
    private ReviewRepository reviewRepository;

    public void saveReview(Review review){
        reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Double getAverageRatingByProductId(Long productId){
        return reviewRepository.findAverageRatingByProductId(productId);
    }
}
