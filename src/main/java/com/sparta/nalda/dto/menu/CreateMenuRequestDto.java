package com.sparta.nalda.dto.menu;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateMenuRequestDto {

    private Long userId;

    private Long storeId;

    @NotBlank(message = "메뉴 이름을 입력해주세요.")
    @Size(max = 20, message = "20글자 이내로 작성해주세요.")
    private String menuName;

    @NotBlank(message = "음식 설명을 입력해주세요.")
    @Size(max = 50, message = "50글자 이내로 작성해주세요.")
    private String menuContents;

    @NotNull(message = "가격을 설정해주세요.")
    private Long price;

}
