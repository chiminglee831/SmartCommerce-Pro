package com.smartcommerce.user.service;

import com.smartcommerce.user.dto.UserDto;
import com.smartcommerce.user.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * User registration
     *
     * @param registerRequest Registration request
     * @return User information
     */
    UserDto.UserInfoResponse register(UserDto.RegisterRequest registerRequest);

    /**
     * User login
     *
     * @param loginRequest Login request
     * @return Login response
     */
    UserDto.LoginResponse login(UserDto.LoginRequest loginRequest);

    /**
     * Refresh token
     *
     * @param refreshTokenRequest Refresh token request
     * @return Login response
     */
    UserDto.LoginResponse refreshToken(UserDto.RefreshTokenRequest refreshTokenRequest);

    /**
     * Retrieve user information
     *
     * @param username Username
     * @return User information
     */
    UserDto.UserInfoResponse getUserInfo(String username);

    /**
     * Retrieve user information by ID
     *
     * @param id User ID
     * @return User information
     */
    UserDto.UserInfoResponse getUserInfoById(Long id);

    /**
     * Update user information
     *
     * @param username      Username
     * @param updateRequest Update request
     * @return User information
     */
    UserDto.UserInfoResponse updateUserInfo(String username, UserDto.UpdateRequest updateRequest);

    /**
     * Change password
     *
     * @param username            Username
     * @param changePasswordRequest Change password request
     * @return Whether successful
     */
    boolean changePassword(String username, UserDto.ChangePasswordRequest changePasswordRequest);

    /**
     * Find user by username
     *
     * @param username Username
     * @return User
     */
    User findByUsername(String username);
}
