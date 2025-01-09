package com.sparta.nalda.service.review;

public interface ReviewService {
    void createReview(Long orderId, String reviewContents, Integer starScore);
}
