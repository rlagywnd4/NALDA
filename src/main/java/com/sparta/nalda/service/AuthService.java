package com.sparta.nalda.service;

import com.sparta.nalda.common.UserRole;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public void signup(String email, String password, String address, UserRole userRole) {

        UserEntity user = new UserEntity(email, password, address, userRole);
        userRepository.save(user);

    }

}
