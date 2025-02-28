package com.smartcommerce.user.controller;

import com.smartcommerce.user.dto.ResponseResult;
import com.smartcommerce.user.dto.UserDto;
import com.smartcommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseResult<UserDto.UserInfoResponse> register(@Valid @RequestBody UserDto.RegisterRequest registerRequest) {
        UserDto.UserInfoResponse userInfoResponse = userService.register(registerRequest);
        return ResponseResult.success("Registration successful", userInfoResponse);
    }

    @PostMapping("/login")
    public ResponseResult<UserDto.LoginResponse> login(@Valid @RequestBody UserDto.LoginRequest loginRequest) {
        UserDto.LoginResponse loginResponse = userService.login(loginRequest);
        return ResponseResult.success("Login successful", loginResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseResult<UserDto.LoginResponse> refreshToken(@Valid @RequestBody UserDto.RefreshTokenRequest refreshTokenRequest) {
        UserDto.LoginResponse loginResponse = userService.refreshToken(refreshTokenRequest);
        return ResponseResult.success("Token refreshed successfully", loginResponse);
    }
}
