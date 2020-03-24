package com.retete.backend.service;

import java.util.List;

import com.retete.backend.exceptions.IngredientInexistent;
import com.retete.backend.model.Ingrediente;
import com.retete.backend.repository.IngredienteRepository;


public class IngredienteService implements Service<Ingrediente> {

    private IngredienteRepository ingredienteRepository;

    public IngredienteService() {
        this.ingredienteRepository = new IngredienteRepository();
    }

    @Override
    public Ingrediente save(Ingrediente entity) {
        return ingredienteRepository.save(entity);
    }

    @Override
    public Ingrediente findById(Long id)  {
        Ingrediente ingredient=ingredienteRepository.findById(id);
        if(ingredient==null) throw new IngredientInexistent("Ingredientul cu id "+id+" nu exista");
        return ingredienteRepository.findById(id);
    }

    @Override
    public List<Ingrediente> findAll() {
        return ingredienteRepository.findAll();
    }

    @Override
    public boolean delete(Long id)  {
        Ingrediente ingredient = ingredienteRepository.findById(id);
        if (ingredient == null) {
            throw new IngredientInexistent("Ingredientul cu id " + id + " nu exista");
        } else {
            ingredienteRepository.delete(ingredient);
            return true;
        }
    }
}
