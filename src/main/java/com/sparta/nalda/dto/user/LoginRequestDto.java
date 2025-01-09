package com.sparta.nalda.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotNull
    @Pattern(regexp = UserValid.EMAIL_REGEX)
    private final String email;

    @NotBlank
    @Pattern(regexp = UserValid.PASSWORD_REGEX)
    private final String password;

    @JsonCreator
    public LoginRequestDto(
        @JsonProperty("email") String email,
        @JsonProperty("password") String password
    ) {
        this.email = email;
        this.password = password;
    }
}
