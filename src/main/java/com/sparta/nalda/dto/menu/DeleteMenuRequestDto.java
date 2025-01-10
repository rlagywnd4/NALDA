package com.sparta.nalda.dto.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeleteMenuRequestDto {

    private final Long userId;
    private final Long storeId;
}
