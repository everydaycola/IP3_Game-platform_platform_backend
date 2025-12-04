package be.kdg.ipj3.platformbackend.shared.api;

import be.kdg.ipj3.platformbackend.shared.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PlatformExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleGameNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}