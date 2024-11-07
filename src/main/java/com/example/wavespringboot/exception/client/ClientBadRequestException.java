package com.example.wavespringboot.exception.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ClientBadRequestException extends RuntimeException {

    public ClientBadRequestException(String message) {
        super(message);
    }
}
