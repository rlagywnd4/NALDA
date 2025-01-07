package com.sparta.nalda.dto;

import com.sparta.nalda.common.UserRole;

public record SignupRequestDto(String email, String password, String address, UserRole userRole) {
}
