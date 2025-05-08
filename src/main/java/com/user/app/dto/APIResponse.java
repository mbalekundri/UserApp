package com.user.app.dto;

public record APIResponse(
        String message,
        boolean result,
        Object data
) {
    public APIResponse(String message, boolean result) {
        this(message, result, null);
    }

    public APIResponse(String message, boolean result, Object data) {
        this.message = message;
        this.result = result;
        this.data = data;
    }
}
