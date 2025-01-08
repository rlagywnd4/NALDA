package com.sparta.nalda.service.store;

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
}
