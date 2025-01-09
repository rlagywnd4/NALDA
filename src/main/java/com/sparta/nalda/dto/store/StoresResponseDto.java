package com.sparta.nalda.dto.store;

import com.sparta.nalda.entity.StoreEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class StoresResponseDto {
    private final String storeName;
    private final String storeContents;

    private final Long minOrderPrice;

    private final LocalTime openTime;
    private final LocalTime closeTime;

    private final String averageStarScore;

    public static StoresResponseDto of(StoreEntity store, String averageStarScore){
        return new StoresResponseDto(
                store.getStoreName(),
                store.getStoreContents(),
                store.getMinOrderPrice(),
                store.getOpenTime(),
                store.getCloseTime(),
                averageStarScore
        );
    }

}
