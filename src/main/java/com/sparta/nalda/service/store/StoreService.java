package com.sparta.nalda.service.store;

import com.sparta.nalda.dto.store.StoreAndMenusResponseDto;

import java.time.LocalTime;

public interface StoreService {
    void saveStore(
            Long userId,
            String storeName,
            String storeContents,
            Long minOrderPrice,
            LocalTime openTime,
            LocalTime closeTime
    );

    StoreAndMenusResponseDto getStoreAndMenus(Long storeId);
}
