package com.sparta.nalda.service.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparta.nalda.dto.user.UserResponseDto;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.entity.WithDrawnEmailEntity;
import com.sparta.nalda.repository.UserRepository;
import com.sparta.nalda.repository.WithDrawnEmailRepository;
import com.sparta.nalda.util.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WithDrawnEmailRepository withDrawnEmailRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void signup_Success() {
        //given
        String email = "test@test.com";
        String password = "test";
        String address = "test";
        UserRole userRole = UserRole.CUSTOMER;

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(withDrawnEmailRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn("hashPassword");

        //when
        userService.signup(email, password, address, userRole);

        //then
        verify(userRepository).save(Mockito.any(UserEntity.class));
    }

    @Test
    void getUser_Success() {
        //given
        Long userId = 1L;
        UserEntity user = new UserEntity("test@test.com", "12345678", "주소", UserRole.CUSTOMER);
        when(userRepository.findByIdOrElseThrow(userId)).thenReturn(user);

        //when
        UserResponseDto result = userService.getUser(userId);

        //then
        assertNotNull(result);
        assertEquals("test@test.com", result.getEmail());
        assertEquals("주소", result.getAddress());
        assertEquals(UserRole.CUSTOMER, result.getUserRole());

    }

    @Test
    void updateUserAddress_Success() {
        //given
        Long userId = 1L;
        String address = "address";
        UserEntity user = new UserEntity("test@test.com", "12345678", "주소", UserRole.CUSTOMER);
        when(userRepository.findByIdOrElseThrow(userId)).thenReturn(user);

        //when
        userService.updateUserAddress(userId, address);

        //then
        assertEquals("address", user.getAddress());
    }

    @Test
    void updateUserPassword_Success() {
        //given
        Long userId = 1L;
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        UserEntity user = new UserEntity("test@test.com", "oldPassword", "주소", UserRole.CUSTOMER);
        when(userRepository.findByIdOrElseThrow(userId)).thenReturn(user);

        when(passwordEncoder.matches(newPassword, user.getPassword())).thenReturn(false);
        when(passwordEncoder.matches(oldPassword, user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn("newPassword");

        //when
        userService.updateUserPassword(userId, oldPassword, newPassword);

        //then
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    void updateUserPassword_OldPasswordNotMatch() {
        //given
        Long userId = 1L;
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        UserEntity user = new UserEntity("test@test.com", "oldPassword1", "주소", UserRole.CUSTOMER);
        when(userRepository.findByIdOrElseThrow(userId)).thenReturn(user);

        when(passwordEncoder.matches(newPassword, user.getPassword())).thenReturn(false);
        when(passwordEncoder.matches(oldPassword, user.getPassword())).thenReturn(false);

        //when
        Exception exception = assertThrows(Exception.class, () ->
            userService.updateUserPassword(userId, oldPassword, newPassword));


        //then
        assertEquals(exception.getMessage(), "잘못된 비밀번호입니다.");
    }

    @Test
    void updateUserPassword_NewPasswordSameOldPassword() {
        //given
        Long userId = 1L;
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        UserEntity user = new UserEntity("test@test.com", "newPassword", "주소", UserRole.CUSTOMER);
        when(userRepository.findByIdOrElseThrow(userId)).thenReturn(user);

        when(passwordEncoder.matches(newPassword, user.getPassword())).thenReturn(true);

        //when
        Exception exception = assertThrows(Exception.class, () ->
            userService.updateUserPassword(userId, oldPassword, newPassword));


        //then
        assertEquals(exception.getMessage(), "새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
    }

    @Test
    void deleteUser_Success() {
        //given
        Long userId = 1L;
        String password = "password";
        UserEntity user = new UserEntity("test@test.com", "passsword", "주소", UserRole.CUSTOMER);
        when(userRepository.findByIdOrElseThrow(userId)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        WithDrawnEmailEntity email = new WithDrawnEmailEntity();

        //when
        userService.deleteUser(userId, password);

        //then
        verify(userRepository).delete(user);
    }
}