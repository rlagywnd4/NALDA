package com.sparta.nalda.dto.menu;

import com.sparta.nalda.entity.MenuEntity;
import lombok.Getter;

@Getter
public class MenuResponseDto {

    private final Long id;
    private final String menuName;
    private final String menuContents;
    private final Long price;

    public MenuResponseDto(MenuEntity menu) {
        this.id = menu.getId();
        this.menuName = menu.getMenuName();
        this.menuContents = menu.getMenuContents();
        this.price = menu.getPrice();
    }
}
