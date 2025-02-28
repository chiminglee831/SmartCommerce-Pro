package com.smartcommerce.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.smartcommerce.user.dto.UserDto;
import com.smartcommerce.user.entity.Role;
import com.smartcommerce.user.entity.User;
import com.smartcommerce.user.entity.UserRole;
import com.smartcommerce.user.exception.BusinessException;
import com.smartcommerce.user.repository.RoleRepository;
import com.smartcommerce.user.repository.UserRepository;
import com.smartcommerce.user.repository.UserRoleRepository;
import com.smartcommerce.user.service.UserService;
import com.smartcommerce.user.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public UserDto.UserInfoResponse register(UserDto.RegisterRequest registerRequest) {
        // Check if username already exists
        if (userRepository.findByUsername(registerRequest.getUsername()) != null) {
            throw new BusinessException("Username already exists");
        }

        // Check if email already exists
        if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
            throw new BusinessException("Email already exists");
        }

        // Check if mobile number already exists
        if (userRepository.findByMobile(registerRequest.getMobile()) != null) {
            throw new BusinessException("Mobile number already exists");
        }

        // Create user
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .mobile(registerRequest.getMobile())
                .nickname(StrUtil.isBlank(registerRequest.getNickname()) ? registerRequest.getUsername() : registerRequest.getNickname())
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        userRepository.insert(user);

        // Assign default role (regular user)
        Role userRole = roleRepository.selectById(2L); // Assuming ID 2 is the regular user role
        if (userRole == null) {
            log.error("Default user role not found");
            throw new BusinessException("Registration failed, default role not found");
        }

        UserRole userRoleRelation = UserRole.builder()
                .userId(user.getId())
                .roleId(userRole.getId())
                .createTime(LocalDateTime.now())
                .build();

        userRoleRepository.insert(userRoleRelation);

        // Return user information
        return mapToUserInfoResponse(user);
    }

    @Override
    public UserDto.LoginResponse login(UserDto.LoginRequest loginRequest) {
        try {
            // Authenticate username and password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Set authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtTokenUtil.generateToken(userDetails);
            String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

            // Update user's last login time
            User user = (User) userDetails;
            user.setLastLoginTime(LocalDateTime.now());
            // In a real application, the request IP can be retrieved
            // user.setLastLoginIp(IpUtil.getIpAddress(request));
            userRepository.updateById(user);

            // Return login response
            UserDto.UserInfoResponse userInfo = mapToUserInfoResponse(user);
            return UserDto.LoginResponse.builder()
                    .token(token)
                    .refreshToken(refreshToken)
                    .userInfo(userInfo)
                    .build();
        } catch (Exception e) {
            log.error("User login failed: {}", e.getMessage(), e);
            throw new BusinessException(401, "Invalid username or password");
        }
    }

    @Override
    public UserDto.LoginResponse refreshToken(UserDto.RefreshTokenRequest refreshTokenRequest) {
        try {
            String refreshToken = refreshTokenRequest.getRefreshToken();
            String username = jwtTokenUtil.getUsernameFromToken(refreshToken);

            if (StrUtil.isBlank(username) || jwtTokenUtil.isTokenExpired(refreshToken)) {
                throw new BusinessException(401, "Refresh token has expired");
            }

            User user = findByUsername(username);
            if (user == null) {
                throw new BusinessException(401, "User does not exist");
            }

            // Generate new tokens
            String token = jwtTokenUtil.generateToken(user);
            String newRefreshToken = jwtTokenUtil.generateRefreshToken(user);

            // Return login response
            UserDto.UserInfoResponse userInfo = mapToUserInfoResponse(user);
            return UserDto.LoginResponse.builder()
                    .token(token)
                    .refreshToken(newRefreshToken)
                    .userInfo(userInfo)
                    .build();
        } catch (Exception e) {
            log.error("Refresh token failed: {}", e.getMessage(), e);
            throw new BusinessException(401, "Failed to refresh token");
        }
    }

    @Override
    public UserDto.UserInfoResponse getUserInfo(String username) {
        User user = findByUsername(username);
        if (user == null) {
            throw new BusinessException("User does not exist");
        }
        return mapToUserInfoResponse(user);
    }

    @Override
    public UserDto.UserInfoResponse getUserInfoById(Long id) {
        User user = userRepository.selectById(id);
        if (user == null) {
            throw new BusinessException("User does not exist");
        }
        return mapToUserInfoResponse(user);
    }

    @Override
    @Transactional
    public UserDto.UserInfoResponse updateUserInfo(String username, UserDto.UpdateRequest updateRequest) {
        User user = findByUsername(username);
        if (user == null) {
            throw new BusinessException("User does not exist");
        }

        // Update user information
        if (StrUtil.isNotBlank(updateRequest.getNickname())) {
            user.setNickname(updateRequest.getNickname());
        }
        if (StrUtil.isNotBlank(updateRequest.getAvatar())) {
            user.setAvatar(updateRequest.getAvatar());
        }
        if (StrUtil.isNotBlank(updateRequest.getGender())) {
            user.setGender(updateRequest.getGender());
        }
        if (updateRequest.getBirthday() != null) {
            user.setBirthday(updateRequest.getBirthday());
        }

        user.setUpdateTime(LocalDateTime.now());
        userRepository.updateById(user);

        return mapToUserInfoResponse(user);
    }

    @Override
    @Transactional
    public boolean changePassword(String username, UserDto.ChangePasswordRequest changePasswordRequest) {
        User user = findByUsername(username);
        if (user == null) {
            throw new BusinessException("User does not exist");
        }

        // Verify old password
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new BusinessException("Incorrect old password");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        user.setUpdateTime(LocalDateTime.now());
        userRepository.updateById(user);

        return true;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User does not exist");
        }

        // Load user roles
        List<Role> roles = roleRepository.findRolesByUserId(user.getId());
        user.setRoles(roles);

        return user;
    }

    /**
     * Convert User to UserInfoResponse
     *
     * @param user User
     * @return UserInfoResponse
     */
    private UserDto.UserInfoResponse mapToUserInfoResponse(User user) {
        List<String> roleNames = user.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());

        return UserDto.UserInfoResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .roles(roleNames)
                .build();
    }
}
