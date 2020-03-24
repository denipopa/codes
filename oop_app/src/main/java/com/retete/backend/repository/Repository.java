package com.retete.backend.repository;

import java.util.List;

import com.retete.backend.model.AbstractEntity;

public interface Repository<T extends AbstractEntity> {

	T save(T entity);
	
	T findById(Long id);
	
	List<T> findAll();
	
	void delete(T entity);
}
