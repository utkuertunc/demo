package com.migros.demo.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder // Lombok prepares automatically builder for this class (Builder Pattern)
public class ResponseError {
    // Response for custom exception

    private String errorMessage;
    private int statusCode;
}
