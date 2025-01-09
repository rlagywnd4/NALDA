package com.sparta.nalda.service;

import com.sparta.nalda.dto.OrderListResponseDto;
import com.sparta.nalda.dto.OrderRequestDto;
import com.sparta.nalda.dto.OrderResponseDto;
import com.sparta.nalda.entity.MenuEntity;
import com.sparta.nalda.entity.OrderEntity;
import com.sparta.nalda.entity.StoreEntity;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.repository.MenuRepository;
import com.sparta.nalda.repository.OrderRepository;
import com.sparta.nalda.repository.UserRepository;
import com.sparta.nalda.util.OrderStatus;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final UserRepository userRepository;
  private final OrderRepository orderRepository;
  private final MenuRepository menuRepository;

  @Transactional
  public OrderEntity createOrder(OrderRequestDto requestDto) {
    // 사용자와 메뉴를 DB에서 조회
    UserEntity user = userRepository.findById(requestDto.getUser())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    MenuEntity menu = menuRepository.findById(requestDto.getMenu())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));

    // 주문 엔티티 생성
    OrderEntity order = new OrderEntity(OrderStatus.PENDING, user, menu);

    // 주문 저장
    return orderRepository.save(order);

  }

  public OrderResponseDto findById(Long id) {


    // 주문 조회
    OrderEntity findOrder = orderRepository.findByIdOrElseThrow(id);

    StoreEntity store = findOrder.getMenu().getStore();
    MenuEntity menu = findOrder.getMenu();

    //상태,유저,메뉴,가게이름,가격
    return new OrderResponseDto(
        findOrder.getOrderStatus(),
        findOrder.getCreatedAt(),
        findOrder.getMenu().getMenuName(),
        store.getStoreName(),
        menu.getPrice());
  }

  public List<OrderListResponseDto> findAllOrderList() {

    List<OrderEntity> orders = orderRepository.findAll();

    //리스트 형식으로 변환
    return orders.stream()
    //        .map(OrderListResponseDto::order)
        .map(order -> new OrderListResponseDto(
            order.getOrderStatus(),
            order.getCreatedAt(),
            order.getMenu().getMenuName(),
            order.getMenu().getStore().getStoreName(),
            order.getMenu().getPrice()
        ))
        .toList();

  }

}