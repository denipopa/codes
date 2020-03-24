package com.retete.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value= HttpStatus.NOT_FOUND, reason="Ingredient inexistent")
public class IngredientInexistent extends RuntimeException {
    public IngredientInexistent(String errorMessage) {
        super(errorMessage);
    }
}
