package com.retete.backend.controller;

import java.util.ArrayList;
import java.util.List;

import com.retete.backend.exceptions.IngredientInexistent;
import com.retete.backend.exceptions.RetetaInexistenta;
import com.retete.backend.model.RetetaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.retete.backend.model.Reteta;
import com.retete.backend.service.RetetaService;

import javax.validation.Valid;

@RestController
public class RetetaController {

    private RetetaService retetaService = new RetetaService();

    @GetMapping("/retete")
    @ResponseBody
    public RetetaResponse getAllReteteDePost(@RequestParam(required = false, value = "dePost") boolean dePost) {
        List<Reteta> retete;
        if (dePost) {
            retete = retetaService.findAllDePost();
        } else {
            retete = retetaService.findAll();
        }
        return new RetetaResponse(retete);
    }

    @GetMapping("/retete/{id}")
    @ResponseBody
    public RetetaResponse getRetetaById(@PathVariable(value = "id") Long id) {
        return new RetetaResponse(retetaService.findById(id));
    }

    @PostMapping("/retete")
    @ResponseBody
    public ResponseEntity saveReteta(@Valid @RequestBody Reteta reteta) {
        if (reteta != null) {
            try {
            	reteta.setId(null);
                return ResponseEntity.ok(new RetetaResponse(retetaService.save(reteta)));
            } catch (IngredientInexistent ingredientInexistent) {
                return new ResponseEntity<>("{\"error\":\"Unul sau mai multe ingrediente nu exista in baza de date\"}", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }
        return null;
    }

    @PutMapping("/retete/{id}")
    @ResponseBody
    public ResponseEntity updateReteta(@Valid@RequestBody Reteta reteta, @PathVariable("id") Long id) {
        if (reteta != null && id != null) {
			try {
				retetaService.findById(id);
			} catch (RetetaInexistenta retetaInexistenta) {
				return new ResponseEntity<>("{\"error\":\"Nu exista reteta cu id-ul "+id+"\"}", HttpStatus.UNPROCESSABLE_ENTITY);
			}
            reteta.setId(id);
            try {
                return ResponseEntity.ok(new RetetaResponse(retetaService.save(reteta)));
            } catch (IngredientInexistent ingredientInexistent) {
				return new ResponseEntity<>("{\"error\":\"Unul sau mai multe ingrediente nu exista in baza de date\"}", HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }
        return null;
    }

    @DeleteMapping("/retete/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteReteta(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(retetaService.delete(id));
    }
}
