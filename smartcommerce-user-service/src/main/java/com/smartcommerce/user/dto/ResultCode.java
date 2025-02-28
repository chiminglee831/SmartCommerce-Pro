package com.smartcommerce.user.dto;

public enum ResultCode {
    SUCCESS(200, "Operation successful"),
    ERROR(500, "Operation failed"),
    UNAUTHORIZED(401, "Not logged in or token has expired"),
    FORBIDDEN(403, "No relevant permissions"),
    VALIDATE_FAILED(404, "Parameter validation failed");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
