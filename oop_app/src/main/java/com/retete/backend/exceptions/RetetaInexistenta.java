package com.retete.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value= HttpStatus.NOT_FOUND, reason="Reteta inexistenta")
public class RetetaInexistenta extends RuntimeException {
    public RetetaInexistenta(String errorMessage) {
        super(errorMessage);
    }
}
