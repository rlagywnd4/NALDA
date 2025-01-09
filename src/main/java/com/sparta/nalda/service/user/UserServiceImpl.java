package com.sparta.nalda.service.user;

import com.sparta.nalda.dto.user.UserResponseDto;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto getUser(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new UserResponseDto(user.getEmail(), user.getUserRole(), user.getAddress());
    }

    @Override
    @Transactional
    public void updateUserAddress(Long id, String address) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.updateAddress(address);
    }

    @Override
    @Transactional
    public void updateUserPassword(Long userId, String oldPassword, String newPassword) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if(passwordEncoder.matches(newPassword, user.getPassword())){
            throw new IllegalArgumentException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
        }

        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        user.updatePassword(passwordEncoder.encode(newPassword));
    }

}
