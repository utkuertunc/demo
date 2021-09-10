package com.migros.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Global exception handling management with @ControllerAdvice
    // Used builder for creating response

    @ExceptionHandler(CourierNotFoundException.class)
    @ResponseBody
    public ResponseError handleCourierNotFoundException(CourierNotFoundException courierEx) {

        return ResponseError.builder()
                .errorMessage(courierEx.getMessage())
                .statusCode(HttpStatus.NO_CONTENT.value())
                .build();
    }

    @ExceptionHandler(StoreNotFoundException.class)
    @ResponseBody
    public ResponseError handleStoreNotFoundException(StoreNotFoundException storeEx) {

        return ResponseError.builder()
                .errorMessage(storeEx.getMessage())
                .statusCode(HttpStatus.NO_CONTENT.value())
                .build();
    }
}
