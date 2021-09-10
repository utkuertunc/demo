package com.migros.demo.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CourierNotFoundException extends RuntimeException {
    // Custom exception for courier not found

    private static final long serialVersionUID = 1L;
    private String message;

    public CourierNotFoundException(String message) {
        this.message = message;
    }
}
