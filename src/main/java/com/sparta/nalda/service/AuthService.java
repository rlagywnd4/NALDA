package com.sparta.nalda.service;

import com.sparta.nalda.common.UserRole;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(String email, String password, String address, UserRole userRole) {

        String hashPassword = passwordEncoder.encode(password);
        UserEntity user = new UserEntity(email, hashPassword, address, userRole);
        userRepository.save(user);

    }

}
