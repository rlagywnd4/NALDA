package com.sparta.nalda.service.review;

import com.sparta.nalda.entity.OrderEntity;
import com.sparta.nalda.entity.ReviewEntity;
import com.sparta.nalda.repository.OrderRepository;
import com.sparta.nalda.repository.ReviewRepository;
import com.sparta.nalda.util.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    /**
     * 리뷰 생성
     * @param orderId
     * @param reviewContents
     * @param starScore
     */
    @Override
    public void createReview(Long orderId, String reviewContents, Integer starScore) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        //주문 상태 확인
        if (!OrderStatus.DELIVERED.equals(order.getOrderStatus())) {
            throw new IllegalArgumentException("배달 완료 상태에서만 리뷰 작성이 가능합니다.");
        }

        //1주문당 1리뷰
        if (order.getReview() != null) {
            throw new IllegalArgumentException("하나의 주문에 하나의 리뷰만 작성이 가능합니다.");
        }

        ReviewEntity review = new ReviewEntity(order, reviewContents, starScore);

        reviewRepository.save(review);
    }
}
