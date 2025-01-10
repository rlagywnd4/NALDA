package com.sparta.nalda.dto.user;

import com.sparta.nalda.util.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserResponseDto {

    private final String email;
    private final UserRole userRole;
    private final String address;
}
