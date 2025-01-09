package com.sparta.nalda.controller;

import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.review.ReviewRequestDto;
import com.sparta.nalda.service.review.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 생성
     * @param orderId
     * @param dto
     * @return
     */
    @PostMapping("/{orderId}")
    public ResponseEntity<MessageResponse> createReview (@PathVariable Long orderId, @Valid @RequestBody ReviewRequestDto dto) {
        reviewService.createReview(orderId, dto.getReviewContents(), dto.getStarSore());

        return ResponseEntity.ok(new MessageResponse("리뷰 작성이 완료되었습니다."));
    }
}
