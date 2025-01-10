package com.sparta.nalda.dto.order;

import com.sparta.nalda.util.OrderStatus;
import lombok.Getter;

@Getter
public class OrderStatusUpdateDto {
  private OrderStatus orderStatus;
}