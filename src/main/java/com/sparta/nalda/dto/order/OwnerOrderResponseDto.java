package com.sparta.nalda.dto.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OwnerOrderResponseDto {

  private final Long orderId;

  private final String userEmail;

  private final String userAddress;

  private final String menuName;

  private final String storeName;

  private final Long menuPrice;
}
