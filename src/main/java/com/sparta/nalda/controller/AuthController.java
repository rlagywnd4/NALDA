package com.sparta.nalda.controller;

import com.sparta.nalda.common.SuccessResponse;
import com.sparta.nalda.dto.SignupRequestDto;
import com.sparta.nalda.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse> signup(@RequestBody SignupRequestDto dto) {
        authService.signup(dto.email(), dto.password(), dto.address(), dto.userRole());
        return ResponseEntity.ok(new SuccessResponse("회원가입에 성공하였습니다."));
    }
}
