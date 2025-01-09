package com.sparta.nalda.controller;

import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.user.PasswordUpdateRequestDto;
import com.sparta.nalda.dto.user.SignupRequestDto;
import com.sparta.nalda.dto.user.UserResponseDto;
import com.sparta.nalda.service.user.UserService;
import com.sparta.nalda.util.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signup(@RequestBody @Valid SignupRequestDto dto) {
        userService.signup(dto.getEmail(), dto.getPassword(), dto.getAddress(), dto.getUserRole());
        return ResponseEntity.ok(new MessageResponse("회원가입에 성공하였습니다."));
    }

    @GetMapping("/users")
    public ResponseEntity<UserResponseDto> getUser() {
        return ResponseEntity.ok(userService.getUser(AuthUser.getId()));
    }

    @PatchMapping("/users")
    public ResponseEntity<MessageResponse> updateUser(@RequestParam String address) {
        userService.updateUserAddress(AuthUser.getId(), address);
        return ResponseEntity.ok(new MessageResponse("주소 수정이 완료되었습니다."));
    }

    @PatchMapping("/users/password")
    public ResponseEntity<MessageResponse> updatePassword(@RequestBody PasswordUpdateRequestDto dto) {
        userService.updateUserPassword(AuthUser.getId(), dto.getOldPassword(), dto.getNewPassword());
        return ResponseEntity.ok(new MessageResponse("비밀번호 수정이 완료되었습니다."));
    }

    @DeleteMapping("/users")
    public ResponseEntity<MessageResponse> deleteUser(@RequestParam String password) {
        userService.deleteUser(AuthUser.getId(), password);
        return ResponseEntity.ok(new MessageResponse("유저 삭제가 완료되었습니다."));
    }

}
