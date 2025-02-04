package com.sparta.nalda.dto.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateMenuRequestDto {

    private final Long storeId;
    private final String menuName;
    private final String menuContents;
    private final Long price;
}
