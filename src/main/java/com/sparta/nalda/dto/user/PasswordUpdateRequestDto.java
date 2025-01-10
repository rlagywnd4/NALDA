package com.sparta.nalda.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PasswordUpdateRequestDto {

    @NotBlank
    @Pattern(regexp = UserValid.PASSWORD_REGEX)
    private final String oldPassword;

    @NotBlank
    @Pattern(regexp = UserValid.PASSWORD_REGEX)
    private final String newPassword;

}
