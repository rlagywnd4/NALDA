package com.sparta.nalda.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class StoreDto {
    private String storeName;
    private String storeContents;

    private Long minOrderPrice;

    private LocalTime openTime;
    private LocalTime closeTime;
}
