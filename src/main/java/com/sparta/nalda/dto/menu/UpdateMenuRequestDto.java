package com.sparta.nalda.dto.menu;

import lombok.Getter;

@Getter
public class UpdateMenuRequestDto {

    private Long userId;
    private Long storeId;
    private String menuName;
    private String menuContents;
    private Long price;
}
