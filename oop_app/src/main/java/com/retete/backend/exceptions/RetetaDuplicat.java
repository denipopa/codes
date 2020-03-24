package com.retete.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value= HttpStatus.CONFLICT, reason="Exista deja o reteta cu acest nume ")
public class RetetaDuplicat extends RuntimeException {
    public RetetaDuplicat(String errorMessage) {
        super(errorMessage);
    }
}
