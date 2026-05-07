package com.learning.capstone.exception;

import com.learning.capstone.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * @ControllerAdvice — a single place to map exceptions to HTTP responses.
 *
 * Without this, IllegalStateException would become a 500 with a Spring
 * stack trace; @Valid failures would become a noisy default body.
 * Here we route them to clean, consistent JSON.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderService.NotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFound(OrderService.NotFoundException e) {
        return body(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> conflict(IllegalStateException e) {
        // Insufficient stock and friends
        return body(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validation(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
            .collect(Collectors.joining("; "));
        return body(HttpStatus.BAD_REQUEST, msg);
    }

    private ResponseEntity<Map<String, Object>> body(HttpStatus status, String msg) {
        return ResponseEntity.status(status).body(Map.of(
            "timestamp", Instant.now().toString(),
            "status",    status.value(),
            "error",     status.getReasonPhrase(),
            "message",   msg
        ));
    }
}
