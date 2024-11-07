package com.example.wavespringboot.exception.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ClientSoldeInsuffisantException extends RuntimeException {
    public ClientSoldeInsuffisantException(String message) {
        super(message);
    }
}
