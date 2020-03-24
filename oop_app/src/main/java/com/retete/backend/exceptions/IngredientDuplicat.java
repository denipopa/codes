package com.retete.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value= HttpStatus.CONFLICT, reason="Exista deja un ingredient cu acest nume ")

public class IngredientDuplicat  extends RuntimeException{
    public IngredientDuplicat(String errorMessage) {
        super(errorMessage);
    }
}
