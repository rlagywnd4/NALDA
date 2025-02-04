package com.sparta.nalda.controller;

import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.order.OrderListResponseDto;
import com.sparta.nalda.dto.order.OrderRequestDto;
import com.sparta.nalda.dto.order.OrderResponseDto;
import com.sparta.nalda.service.order.OrderService;
import com.sparta.nalda.util.AuthUser;
import java.util.List;
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

    orderService.createOrder(AuthUser.getId(),dto);

    return ResponseEntity.ok(new MessageResponse("주문이 완료되었습니다."));

  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDto> findById(@PathVariable Long id) {

    OrderResponseDto orderResponseDto = orderService.findById(id);

    return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
  }

  @GetMapping
  public ResponseEntity<List<OrderListResponseDto>> findAllOrders() {

    List<OrderListResponseDto> orderList = orderService.findAllOrderList();

    return ResponseEntity.ok(orderList);
  }

}


