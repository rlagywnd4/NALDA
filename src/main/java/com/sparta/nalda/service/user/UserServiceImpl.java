package com.sparta.nalda.service.user;

import com.sparta.nalda.dto.user.UserResponseDto;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto getUser(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new UserResponseDto(user.getEmail(), user.getUserRole(), user.getAddress());
    }
}
