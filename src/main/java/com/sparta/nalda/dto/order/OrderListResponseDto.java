package com.sparta.nalda.dto.order;

import com.sparta.nalda.entity.OrderEntity;
import com.sparta.nalda.util.OrderStatus;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public class OrderListResponseDto {

  private final OrderStatus orderStatus;

  private final LocalDateTime orderTime;

  private final String menuName;

  private final String storeName;

  private final Long menuPrice;

  public OrderListResponseDto(OrderEntity orderEntity) {
    this.orderStatus = orderEntity.getOrderStatus();
    this.orderTime = orderEntity.getCreatedAt();
    this.storeName = orderEntity.getMenu().getStore().getStoreName();
    this.menuName = orderEntity.getMenu().getMenuName();
    this.menuPrice = orderEntity.getMenu().getPrice();
  }

}
