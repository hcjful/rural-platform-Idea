package com.rural.platform.service.impl;

import com.rural.platform.entity.Review;
import com.rural.platform.mapper.ReviewMapper;
import com.rural.platform.service.ReviewService;
import com.rural.platform.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.lang.reflect.Field;

@Service
class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    @Transactional
    public Review createReview(Review review, List<MultipartFile> images) {
        if (images != null && !images.isEmpty()) {
            List<String> imageUrls = images.stream()
                    .map(file -> {
                        try {
                            return FileUploadUtils.upload(file, "reviews");
                        } catch (IOException e) {
                            throw new RuntimeException("上传评价图片失败", e);
                        }
                    })
                    .collect(Collectors.toList());
            
            // 使用反射设置images字段
            try {
                Field imagesField = Review.class.getDeclaredField("images");
                imagesField.setAccessible(true);
                imagesField.set(review, String.join(",", imageUrls));
            } catch (Exception e) {
                throw new RuntimeException("设置评价图片失败", e);
            }
        }

        reviewMapper.insert(review);
        return review;
    }

    @Override
    public List<Review> getProductReviews(Long productId) {
        return reviewMapper.findByProductId(productId);
    }

    @Override
    public List<Review> getUserReviews(Long userId) {
        return reviewMapper.findByUserId(userId);
    }

    @Override
    public double getProductAverageRating(Long productId) {
        Double avgRating = reviewMapper.getAverageRating(productId);
        return avgRating != null ? avgRating : 0.0;
    }
}
