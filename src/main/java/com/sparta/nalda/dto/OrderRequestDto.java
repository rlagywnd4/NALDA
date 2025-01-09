package com.sparta.nalda.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderRequestDto {
  private final Long user;
  private final Long menu;

}
