package com.sparta.nalda.controller;

import com.sparta.nalda.dto.OrderRequestDto;
import com.sparta.nalda.entity.OrderEntity;
import com.sparta.nalda.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderRequestDto dto) {
    OrderEntity createdOrder = orderService.createOrder(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
  }

}
