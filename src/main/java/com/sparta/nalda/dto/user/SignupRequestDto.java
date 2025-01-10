package com.sparta.nalda.dto.user;

import com.sparta.nalda.util.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignupRequestDto {

    @NotNull
    @Pattern(regexp = UserValid.EMAIL_REGEX, message = "이메일 형식이 올바르지 않습니다.")
    private final String email;

    @NotBlank
    @Pattern(regexp = UserValid.PASSWORD_REGEX, message = "8자이상의 영문 대/소문자, 숫자, 특수문자를 사용해 주세요.")
    private final String password;

    @NotBlank(message = "주소를 입력해주세요.")
    private final String address;

    @NotNull(message = "회원 유형을 선택해주세요.")
    private final UserRole userRole;
}
