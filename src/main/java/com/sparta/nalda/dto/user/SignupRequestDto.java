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
    @Pattern(regexp = UserValid.EMAIL_REGEX)
    private final String email;

    @NotBlank
    @Pattern(regexp = UserValid.PASSWORD_REGEX)
    private final String password;

    @NotBlank
    private final String address;

    @NotNull
    private final UserRole userRole;
}
