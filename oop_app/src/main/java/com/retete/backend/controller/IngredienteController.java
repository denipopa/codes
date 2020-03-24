package com.retete.backend.controller;
import com.retete.backend.exceptions.IngredientInexistent;
import com.retete.backend.model.Ingrediente;
import com.retete.backend.model.IngredienteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.retete.backend.service.IngredienteService;
import javax.validation.Valid;

@RestController
public class IngredienteController {

    private IngredienteService ingredienteService = new IngredienteService();

    @GetMapping("/ingrediente")
    @ResponseBody
    public IngredienteResponse getAllIngrediente() {
        return new IngredienteResponse( ingredienteService.findAll());
    }

    @GetMapping("/ingrediente/{id}")
    @ResponseBody
    public Ingrediente getIngredientById(@PathVariable(value = "id") Long id) {

        return ingredienteService.findById(id);
    }

    @PostMapping("/ingrediente")
    @ResponseBody
    public Ingrediente saveIngredient(@Valid @RequestBody Ingrediente ingredient) {
        if (ingredient != null) {
            ingredient.setId(null);
            return ingredienteService.save(ingredient);
        }
        return null;
    }

    @PutMapping("/ingrediente/{id}")
    @ResponseBody
    public ResponseEntity updateIngredient(@Valid@RequestBody Ingrediente ingredient, @PathVariable("id") Long id) {
        if (ingredient != null && id != null) {
            try{
                ingredienteService.findById(id);

            }catch(IngredientInexistent ingredientInexistent){
                return new ResponseEntity<>("{\"error\":\"Nu exista ingredientul cu id-ul "+id+"\"}", HttpStatus.UNPROCESSABLE_ENTITY);
            }
            ingredient.setId(id);
                return  ResponseEntity.ok(ingredienteService.save(ingredient));
        }
        return null;
    }

    @DeleteMapping("/ingrediente/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteIngredient(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(ingredienteService.delete(id));
    }
}
