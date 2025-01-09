package com.sparta.nalda.service.user;

import com.sparta.nalda.dto.user.UserResponseDto;

public interface UserService {

    UserResponseDto getUser(Long id);

    void updateUserAddress(Long id, String address);
}
