package com.sparta.nalda.dto.order;

import com.sparta.nalda.util.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OrderStatusUpdateDto {
  private OrderStatus orderStatus;
}
