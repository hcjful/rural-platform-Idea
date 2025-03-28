package com.rural.platform.service;

import com.rural.platform.entity.Review;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {
    Review createReview(Review review, List<MultipartFile> images);
    List<Review> getProductReviews(Long productId);
    List<Review> getUserReviews(Long userId);
    double getProductAverageRating(Long productId);
}

