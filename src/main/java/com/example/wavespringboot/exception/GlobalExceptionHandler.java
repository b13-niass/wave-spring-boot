package com.example.wavespringboot.exception;

import com.example.wavespringboot.exception.client.ClientNotFoundException;
import com.example.wavespringboot.exception.client.ClientUnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();

        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        errors.put("errors", errorMessages);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<String> handlePromoNotFoundException(ClientNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientUnauthorizedException.class)
    public ResponseEntity<String> handlePromoUnauthorizedException(ClientUnauthorizedException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleSecurityException(Exception exception) {
        String errorMessage;

        // Log or send the exception details to an observability tool, if needed
        exception.printStackTrace();

        // Customize error message and status based on the exception type
        if (exception instanceof BadCredentialsException) {
            errorMessage = "The username or password is incorrect";
            return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
        }

        if (exception instanceof AccountStatusException) {
            errorMessage = "The account is locked or disabled";
            return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
        }

        if (exception instanceof AccessDeniedException) {
            errorMessage = "You are not authorized to access this resource";
            return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
        }

        if (exception instanceof SignatureException) {
            errorMessage = "The JWT signature is invalid";
            return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
        }

        if (exception instanceof ExpiredJwtException) {
            errorMessage = "The JWT token has expired";
            return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
        }

        // Fallback for other exceptions
        errorMessage = "Unknown internal server error.";
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>("Accès refusé : Vous n'avez pas l'autorisation d'accéder à cette ressource.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredJwtException(ExpiredJwtException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("error", "Unauthorized");
        response.put("message", "JWT token has expired");
        response.put("expiredAt", ex.getClaims().getExpiration());  // Optional: to include the expiration time
        response.put("path", "/your-endpoint");  // Optional: customize or dynamically get the endpoint path

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
