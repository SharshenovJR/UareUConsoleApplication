package com.digitalPersona.UareU.exception;

import com.digitalpersona.uareu.UareUException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class Exception extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UareUException.class)
    protected ResponseEntity<Object> handleUareUException(UareUException e) {
        return ResponseEntity.badRequest()
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "error", e.toString()));
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest()
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "error", e.getMessage()));
    }
}
