package com.example.wavespringboot.exception.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ClientForbiddenException extends RuntimeException {

    public ClientForbiddenException(String message) {
        super(message);
    }
}

