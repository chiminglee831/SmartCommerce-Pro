package com.smartcommerce.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> {
    
    private Integer code;
    
    private String message;
    
    private T data;
    
    public static <T> ResponseResult<T> success() {
        return ResponseResult.<T>builder()
                .code(ResultCode.SUCCESS.getCode())
                .message(ResultCode.SUCCESS.getMessage())
                .build();
    }
    
    public static <T> ResponseResult<T> success(T data) {
        return ResponseResult.<T>builder()
                .code(ResultCode.SUCCESS.getCode())
                .message(ResultCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }
    
    public static <T> ResponseResult<T> success(String message, T data) {
        return ResponseResult.<T>builder()
                .code(ResultCode.SUCCESS.getCode())
                .message(message)
                .data(data)
                .build();
    }
    
    public static <T> ResponseResult<T> error() {
        return ResponseResult.<T>builder()
                .code(ResultCode.ERROR.getCode())
                .message(ResultCode.ERROR.getMessage())
                .build();
    }
    
    public static <T> ResponseResult<T> error(String message) {
        return ResponseResult.<T>builder()
                .code(ResultCode.ERROR.getCode())
                .message(message)
                .build();
    }
    
    public static <T> ResponseResult<T> error(Integer code, String message) {
        return ResponseResult.<T>builder()
                .code(code)
                .message(message)
                .build();
    }
}