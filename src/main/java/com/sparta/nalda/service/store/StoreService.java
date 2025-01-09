package com.sparta.nalda.service.store;

import com.sparta.nalda.dto.store.StoreAndMenusResponseDto;
import jakarta.validation.constraints.*;

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

    void updateStore(Long id, String storeName, String storeContents, Long minOrderPrice, LocalTime openTime, LocalTime closeTime);
}
