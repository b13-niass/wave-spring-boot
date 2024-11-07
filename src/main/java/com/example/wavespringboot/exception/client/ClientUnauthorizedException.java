package com.example.wavespringboot.exception.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class ClientUnauthorizedException extends RuntimeException {

    public ClientUnauthorizedException(String message) {
        super(message);
    }
}