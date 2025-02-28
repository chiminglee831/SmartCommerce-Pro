package com.smartcommerce.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class UserDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        
        @NotBlank(message = "Username cannot be blank")
        @Size(min = 4, max = 20, message = "Username length must be between 4 and 20 characters")
        private String username;
        
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, max = 32, message = "Password length must be between 6 and 32 characters")
        private String password;
        
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        private String email;
        
        @NotBlank(message = "Mobile number cannot be blank")
        @Pattern(regexp = "^1[3-9]\\d{9}$", message = "Invalid mobile number format")
        private String mobile;
        
        private String nickname;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        
        @NotBlank(message = "Username cannot be blank")
        private String username;
        
        @NotBlank(message = "Password cannot be blank")
        private String password;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        
        private String token;
        
        private String refreshToken;
        
        private UserInfoResponse userInfo;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoResponse {
        
        private Long id;
        
        private String username;
        
        private String email;
        
        private String mobile;
        
        private String nickname;
        
        private String avatar;
        
        private String gender;
        
        private LocalDateTime birthday;
        
        private List<String> roles;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequest {
        
        private String nickname;
        
        private String avatar;
        
        private String gender;
        
        private LocalDateTime birthday;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChangePasswordRequest {
        
        @NotBlank(message = "Old password cannot be blank")
        private String oldPassword;
        
        @NotBlank(message = "New password cannot be blank")
        @Size(min = 6, max = 32, message = "Password length must be between 6 and 32 characters")
        private String newPassword;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefreshTokenRequest {
        
        @NotBlank(message = "Refresh token cannot be blank")
        private String refreshToken;
    }
}
