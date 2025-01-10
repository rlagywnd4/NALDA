package com.sparta.nalda.dto.menu;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateMenuRequestDto {

    private final Long storeId;

    @NotBlank(message = "메뉴 이름을 입력해주세요.")
    @Size(max = 20, message = "20글자 이내로 작성해주세요.")
    private final String menuName;

    @NotBlank(message = "음식 설명을 입력해주세요.")
    @Size(max = 50, message = "50글자 이내로 작성해주세요.")
    private final String menuContents;

    @NotNull(message = "가격을 설정해주세요.")
    private final Long price;

}
