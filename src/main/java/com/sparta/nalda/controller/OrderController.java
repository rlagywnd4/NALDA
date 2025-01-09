package com.sparta.nalda.controller;

import com.sparta.nalda.common.MessageResponse;
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
  public ResponseEntity<MessageResponse> createOrder(@RequestBody OrderRequestDto dto) {

    orderService.createOrder(dto);

    return ResponseEntity.ok(new MessageResponse("주문이 완료되었습니다."));

  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDto> findById(@PathVariable Long id) {

    OrderResponseDto orderResponseDto = orderService.findById(id);

    return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
  }
}


