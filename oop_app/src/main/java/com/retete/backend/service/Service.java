package com.retete.backend.service;

import com.retete.backend.exceptions.IngredientInexistent;
import com.retete.backend.exceptions.RetetaInexistenta;

import java.util.List;

public interface Service<T> {

	T save(T entity) throws IngredientInexistent;
	
	T findById(Long id) throws IngredientInexistent;
	
	List<T> findAll();
	
	boolean delete(Long id) throws RetetaInexistenta, IngredientInexistent;
}
