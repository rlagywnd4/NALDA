package com.sparta.nalda.service;

import com.sparta.nalda.dto.OrderRequestDto;
import com.sparta.nalda.entity.MenuEntity;
import com.sparta.nalda.entity.OrderEntity;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.repository.OrderRepository;
import com.sparta.nalda.util.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;

  @Transactional
  public OrderEntity createOrder(OrderRequestDto requestDto) {
    // 사용자와 메뉴를 DB에서 조회
    UserEntity user = userRepository.findById(requestDto.getUserId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    MenuEntity menu = menuRepository.findById(requestDto.getMenuId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));

    // 주문 엔티티 생성
    OrderEntity order = new OrderEntity(OrderStatus.PENDING, user, menu);

    // 주문 저장
    return orderRepository.save(order);
  }

}
