package com.sparta.nalda.dto;

import com.sparta.nalda.entity.MenuEntity;
import com.sparta.nalda.entity.StoreEntity;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.util.OrderStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderResponseDto {

  private final OrderStatus orderStatus;

  private final UserEntity user;

  private final MenuEntity menu;

  private final String storeName;

  private final Long menuPrice;

}
