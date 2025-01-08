package com.sparta.nalda.service.store;

import jakarta.transaction.Transactional;

import java.time.LocalTime;

public interface StoreService {
    @Transactional
    void saveStore(
            Long userId,
            String storeName,
            String storeContents,
            Long minOrderPrice,
            LocalTime openTime,
            LocalTime closeTime
    );
}
