package com.retete.backend.service;

import java.util.ArrayList;
import java.util.List;

import com.retete.backend.exceptions.IngredientInexistent;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import com.retete.backend.model.Ingrediente;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IngredienteServiceTest {

    private IngredienteService ingredienteService = new IngredienteService();
    private static List<Long> idIngrediente = new ArrayList<>();

    @Test
    public void a_testFindAll(){
        Ingrediente newIngredient = new Ingrediente();
        newIngredient.setDenumire_ingredient("faina de grau"+System.currentTimeMillis());
        newIngredient.setDePost(true);
        newIngredient.setUnitate_de_masura("kilogram");
        newIngredient = ingredienteService.save(newIngredient);
        Assert.assertNotNull("Ingredientul salvat nu contine id!", newIngredient.getId());
        idIngrediente.add(newIngredient.getId());
        List<Ingrediente> ingrediente = ingredienteService.findAll();
        Assert.assertNotNull(ingrediente);
        Assert.assertTrue("Ingredientul introdus nu a fost gasit!", ingrediente.contains(newIngredient));
    }


    @Test
    public void b_testUpdateIngredient() {
        Ingrediente newIngredient = new Ingrediente();
        newIngredient.setDenumire_ingredient("oua"+System.currentTimeMillis());
        newIngredient.setDePost(false);
        newIngredient.setUnitate_de_masura("bucata");
        newIngredient = ingredienteService.save(newIngredient);

        idIngrediente.add(newIngredient.getId());
        Assert.assertNotNull("Ingredientul salvat nu contine id!", newIngredient.getId());
        Long id = newIngredient.getId();
        String numeNou="ingredient Actualizat"+System.currentTimeMillis();
        newIngredient.setDenumire_ingredient(numeNou);
        newIngredient = ingredienteService.save(newIngredient);
        newIngredient = ingredienteService.findById(id);
        Assert.assertNotNull(newIngredient);
        Assert.assertEquals("Ingredientul nu a fost actualizat corect!", numeNou, newIngredient.getDenumire_ingredient());
    }


    @Test
    public void c_testDelete(){
        for (Long id : idIngrediente) {
            ingredienteService.delete(id);
            Ingrediente ingredient;
            try {
                ingredient = ingredienteService.findById(id);
            } catch (IngredientInexistent ingredientInexistent) {
               ingredient=null;
            }
            Assert.assertNull("Ingredientul nu a fost sters", ingredient);
        }
    }
}