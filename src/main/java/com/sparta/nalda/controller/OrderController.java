package com.sparta.nalda.controller;

import com.sparta.nalda.dto.OrderRequestDto;
import com.sparta.nalda.dto.OrderResponseDto;
import com.sparta.nalda.entity.MenuEntity;
import com.sparta.nalda.entity.OrderEntity;
import com.sparta.nalda.entity.StoreEntity;
import com.sparta.nalda.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto dto) {
    OrderEntity order = orderService.createOrder(dto.getUserId(), dto.getMenuId());

    OrderResponseDto responseDto = new OrderResponseDto(
        order.getOrderStatus(),
        order.getUser(),
        order.getMenu(),
        order.getMenu().getStoreId().getStoreName(),
        order.getMenu().getPrice()
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

  }

  @GetMapping("/orders/{id}")
  public ResponseEntity<OrderResponseDto> findById(@PathVariable Long id) {
    // 서비스 계층 호출
    OrderEntity order = orderService.findById(id);

    // 컨트롤러에서 DTO 생성
    StoreEntity store = order.getMenu().getStoreId();
    MenuEntity menu = order.getMenu();
    OrderResponseDto responseDto = new OrderResponseDto(
        order.getOrderStatus(),
        order.getUser(),
        order.getMenu(),
        store.getStoreName(),
        menu.getPrice()
    );

    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }
}


