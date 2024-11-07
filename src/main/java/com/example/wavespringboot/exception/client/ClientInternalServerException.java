package com.example.wavespringboot.exception.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ClientInternalServerException extends RuntimeException {

    public ClientInternalServerException(String message) {
        super(message);
    }
}