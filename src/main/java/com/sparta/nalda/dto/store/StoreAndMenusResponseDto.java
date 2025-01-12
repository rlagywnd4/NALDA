package com.sparta.nalda.dto.store;

import com.sparta.nalda.entity.StoreEntity;
import com.sparta.nalda.entity.MenuEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class StoreAndMenusResponseDto {
    private final StoreDto store;
    private final List<StoreMenuDto> menus;

    public StoreAndMenusResponseDto(StoreEntity store, List<MenuEntity> menus, String averageStarScore){
        this.store = new StoreDto(
                store.getStoreName(),
                store.getStoreContents(),
                store.getMinOrderPrice(),
                store.getOpenTime(),
                store.getCloseTime(),
                averageStarScore
        );

        this.menus = new ArrayList<>();
        for(MenuEntity menu: menus){
            this.menus.add(new StoreMenuDto(
                    menu.getMenuName(),
                    menu.getMenuContents(),
                    menu.getPrice()));
        }
    }

    @Getter
    @AllArgsConstructor
    class StoreDto{
        private final String storeName;
        private final String storeContents;

        private final Long minOrderPrice;

        private final LocalTime openTime;
        private final LocalTime closeTime;

        private final String averageStarScore;
    }

    @Getter
    @AllArgsConstructor
    class StoreMenuDto {
        private final String menuName;

        private final String menuContents;

        private final Long price;
    }

}
