package com.sparta.nalda.controller;

import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.user.LoginRequestDto;
import com.sparta.nalda.dto.user.LoginResponseDto;
import com.sparta.nalda.dto.user.SignupRequestDto;
import com.sparta.nalda.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signup(@RequestBody @Valid SignupRequestDto dto) {
        authService.signup(dto.getEmail(), dto.getPassword(), dto.getAddress(), dto.getUserRole());
        return ResponseEntity.ok(new MessageResponse("회원가입에 성공하였습니다."));
    }

}
