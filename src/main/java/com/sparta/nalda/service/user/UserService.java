package com.sparta.nalda.service.user;

import com.sparta.nalda.dto.user.UserResponseDto;
import com.sparta.nalda.util.UserRole;
import java.util.List;

public interface UserService {

    UserResponseDto getUser(Long id);

    void updateUserAddress(Long id, String address);

    void updateUserPassword(Long userId, String oldPassword, String newPassword);

    void deleteUser(Long userId, String password);

    void signup(String email, String password, String address, UserRole userRole);

    List<UserResponseDto> getUsers();
}
