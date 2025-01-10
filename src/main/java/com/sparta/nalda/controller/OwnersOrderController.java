package com.sparta.nalda.controller;

import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.order.OrderStatusUpdateDto;
import com.sparta.nalda.dto.order.OwnerOrderResponseDto;
import com.sparta.nalda.service.order.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owners")
public class OwnersOrderController {

  private final OrderService orderService;

  @GetMapping()
  public ResponseEntity<List<OwnerOrderResponseDto>> findAllOwnerOrderResponseDto() {

    Long ownerId = 1L;

    List<OwnerOrderResponseDto> orderListOwner = orderService.findAllOrderListOwner(ownerId);

    return ResponseEntity.ok(orderListOwner);

  }

  @PatchMapping("/orders")
  public ResponseEntity<MessageResponse> updateOrderStatus(@RequestBody OrderStatusUpdateDto requestDto) {

    Long orderId = 1L;
    // 주문 상태 업데이트
    orderService.updateOrderStatus(orderId, requestDto.getOrderStatus());

    return ResponseEntity.ok(new MessageResponse("주문이 성공적으로 변경되었습니다."));
  }
}


