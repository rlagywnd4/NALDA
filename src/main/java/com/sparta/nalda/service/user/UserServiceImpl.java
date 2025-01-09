package com.sparta.nalda.service.user;

import com.sparta.nalda.dto.user.UserResponseDto;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.entity.WithDrawnEmailEntity;
import com.sparta.nalda.repository.UserRepository;
import com.sparta.nalda.repository.WithDrawnEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WithDrawnEmailRepository withDrawnEmailRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto getUser(Long userId) {
        UserEntity user = userRepository.findByIdOrElseThrow(userId);
        return new UserResponseDto(user.getEmail(), user.getUserRole(), user.getAddress());
    }

    @Override
    @Transactional
    public void updateUserAddress(Long userId, String address) {
        UserEntity user = userRepository.findByIdOrElseThrow(userId);
        user.updateAddress(address);
    }

    @Override
    @Transactional
    public void updateUserPassword(Long userId, String oldPassword, String newPassword) {
        UserEntity user = userRepository.findByIdOrElseThrow(userId);

        if(passwordEncoder.matches(newPassword, user.getPassword())){
            throw new IllegalArgumentException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
        }

        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        user.updatePassword(passwordEncoder.encode(newPassword));
    }

    @Override
    public void deleteUser(Long userId, String password) {
        UserEntity user = userRepository.findByIdOrElseThrow(userId);

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        WithDrawnEmailEntity email = new WithDrawnEmailEntity(user.getEmail());

        withDrawnEmailRepository.save(email);
        userRepository.delete(user);

    }

}
