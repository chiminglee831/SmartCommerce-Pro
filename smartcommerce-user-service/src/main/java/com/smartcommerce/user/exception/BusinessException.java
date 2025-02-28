package com.smartcommerce.user.exception;

import lombok.Getter;

public class BusinessException extends RuntimeException {
    
    @Getter
    private final Integer code;
    
    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }
    
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}