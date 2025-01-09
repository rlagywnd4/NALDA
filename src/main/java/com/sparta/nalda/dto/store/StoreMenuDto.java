package com.sparta.nalda.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreMenuDto {
    private String menuName;

    private String menuContents;

    private Long price;
}
