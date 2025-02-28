package com.smartcommerce.user.controller;

import com.smartcommerce.user.dto.ResponseResult;
import com.smartcommerce.user.dto.UserDto;
import com.smartcommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseResult<UserDto.UserInfoResponse> getCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDto.UserInfoResponse userInfoResponse = userService.getUserInfo(username);
        return ResponseResult.success(userInfoResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseResult<UserDto.UserInfoResponse> getUserInfoById(@PathVariable Long id) {
        UserDto.UserInfoResponse userInfoResponse = userService.getUserInfoById(id);
        return ResponseResult.success(userInfoResponse);
    }

    @PutMapping("/me")
    public ResponseResult<UserDto.UserInfoResponse> updateUserInfo(@Valid @RequestBody UserDto.UpdateRequest updateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDto.UserInfoResponse userInfoResponse = userService.updateUserInfo(username, updateRequest);
        return ResponseResult.success("Update successful", userInfoResponse);
    }

    @PutMapping("/me/password")
    public ResponseResult<Void> changePassword(@Valid @RequestBody UserDto.ChangePasswordRequest changePasswordRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean result = userService.changePassword(username, changePasswordRequest);

        if (result) {
            // Use a success response method without parameters and set the message
            return ResponseResult.<Void>builder()
                    .code(200)
                    .message("Password changed successfully")
                    .build();
        } else {
            return ResponseResult.error("Password change failed");
        }
    }
}
