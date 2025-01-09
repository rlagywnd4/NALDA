package com.sparta.nalda.controller;

import com.sparta.nalda.dto.order.OwnerOrderResponseDto;
import com.sparta.nalda.service.order.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

}
