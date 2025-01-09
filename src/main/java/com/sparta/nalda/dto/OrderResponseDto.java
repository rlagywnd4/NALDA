package com.sparta.nalda.dto;

import com.sparta.nalda.entity.MenuEntity;
import com.sparta.nalda.entity.StoreEntity;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.util.OrderStatus;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderResponseDto {

  private final OrderStatus orderStatus;

  private final LocalDateTime orderTime;

  private final String menuName;

  private final String storeName;

  private final Long menuPrice;

}
