package com.ckarousis.eshopapp.services;

import com.ckarousis.eshopapp.model.Review;

import java.util.List;

public interface ReviewService {
    void saveReview(Review review);

    List<Review> getAllReviews();
    Double getAverageRatingByProductId(Long productId);
}
