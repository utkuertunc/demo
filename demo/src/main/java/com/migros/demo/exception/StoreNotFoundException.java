package com.migros.demo.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class StoreNotFoundException extends RuntimeException {
    // Custom exception for store not found

    private static final long serialVersionUID = 1L;
    private String message;

    public StoreNotFoundException(String message) {
        this.message = message;
    }
}
