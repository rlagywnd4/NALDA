package com.sparta.nalda.service;

import com.sparta.nalda.dto.OrderRequestDto;
import com.sparta.nalda.dto.OrderResponseDto;
import com.sparta.nalda.entity.MenuEntity;
import com.sparta.nalda.entity.OrderEntity;
import com.sparta.nalda.entity.StoreEntity;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.repository.OrderRepository;
import com.sparta.nalda.util.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final UesrRepository userRepository;
  private final OrderRepository orderRepository;
  private final MenuRepository menuRepository;

  @Transactional
  public OrderEntity createOrder(Long userId, Long menuId) {
    // 사용자와 메뉴를 DB에서 조회
    UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    MenuEntity menu = menuRepository.findById(menuId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));

    // 주문 엔티티 생성
    OrderEntity order = new OrderEntity(OrderStatus.PENDING, user, menu);

    // 주문 저장
    return orderRepository.save(order);
  }

  public OrderEntity findById(Long id) {


    // 주문 조회
    OrderEntity findOrder = orderRepository.findByIdOrElseThrow(id);

    return orderRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
  }

}
