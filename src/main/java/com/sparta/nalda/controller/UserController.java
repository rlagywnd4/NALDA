package com.sparta.nalda.controller;

import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.user.UserResponseDto;
import com.sparta.nalda.service.user.UserService;
import com.sparta.nalda.service.user.UserServiceImpl;
import com.sparta.nalda.util.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDto> getUser() {
        return ResponseEntity.ok(userService.getUser(AuthUser.getUserId()));
    }

    @PatchMapping
    public ResponseEntity<MessageResponse> updateUser(@RequestParam String address) {
        userService.updateUserAddress(AuthUser.getUserId(), address);
        return ResponseEntity.ok(new MessageResponse("주소 수정이 완료되었습니다."));
    }

}
