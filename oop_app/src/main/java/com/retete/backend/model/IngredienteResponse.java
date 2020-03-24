package com.retete.backend.model;

import java.util.ArrayList;
import java.util.List;

public class IngredienteResponse {
    List<Ingrediente> ingrediente= new ArrayList<>();

    public List<Ingrediente> getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(List<Ingrediente> ingrediente) {
        this.ingrediente = ingrediente;
    }


    public IngredienteResponse(Ingrediente ingredient){
        ingrediente.add(ingredient);
    }

    public IngredienteResponse(List<Ingrediente> lista) {
        ingrediente.addAll(lista);
    }
}
