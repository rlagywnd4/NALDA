package com.sparta.nalda.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PasswordUpdateRequestDto {

    private final String oldPassword;
    private final String newPassword;

}
