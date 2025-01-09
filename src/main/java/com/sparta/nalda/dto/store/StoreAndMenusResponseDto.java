package com.sparta.nalda.dto.store;

import com.sparta.nalda.entity.StoreEntity;
import com.sparta.nalda.entity.MenuEntity;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StoreAndMenusResponseDto {
    private final StoreDto store;
    private final List<StoreMenuDto> menus;

    public StoreAndMenusResponseDto(StoreEntity store, List<MenuEntity> menus){
        this.store = new StoreDto(
                store.getStoreName(),
                store.getStoreContents(),
                store.getMinOrderPrice(),
                store.getOpenTime(),
                store.getCloseTime()
        );

        this.menus = new ArrayList<>();
        for(MenuEntity menu: menus){
            this.menus.add(new StoreMenuDto(
                    menu.getMenuName(),
                    menu.getMenuContents(),
                    menu.getPrice()));
        }
    }
}
