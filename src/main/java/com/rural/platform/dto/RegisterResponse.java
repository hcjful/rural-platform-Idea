package com.rural.platform.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class RegisterResponse {
    private boolean success;
    private String message;
    private Object data;
}