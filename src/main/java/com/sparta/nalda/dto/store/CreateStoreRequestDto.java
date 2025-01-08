package com.sparta.nalda.dto.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class CreateStoreRequestDto {

    @NotBlank(message = "가게 이름을 입력해주세요.")
    @Size(max = 30, message = "30글자 이내로 작성해주세요.")
    String storeName;

    @NotBlank(message = "가게 설명을 입력해주세요.")
    @Size(max = 50, message = "50글자 이내로 작성해주세요.")
    String storeContents;

    @PositiveOrZero(message = "음수는 입력할 수 없습니다.")
    @NotNull(message = "최소주문금액을 설정해주세요.")
    Long minOrderPrice;

    @NotNull(message = "가게 오픈 시간을 입력해주세요.")
    LocalTime openTime;
    @NotNull(message = "가게 마감 시간을 입력해주세요.")
    LocalTime closeTime;
}
