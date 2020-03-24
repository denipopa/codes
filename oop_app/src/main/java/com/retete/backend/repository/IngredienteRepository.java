package com.retete.backend.repository;
import java.util.List;

import com.retete.backend.exceptions.IngredientDuplicat;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.retete.backend.model.Ingrediente;
import com.retete.backend.service.HibernateService;
import com.retete.backend.transformer.IngredienteTransformer;

public class IngredienteRepository implements Repository<Ingrediente> {

    private IngredienteTransformer ingredienteTransformer;

    public IngredienteRepository() {
        this.ingredienteTransformer = new IngredienteTransformer();
    }

    public Ingrediente save(Ingrediente entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        try {
            databaseSession.saveOrUpdate(entity);
            databaseSession.save(entity);
            databaseSession.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new IngredientDuplicat(entity.getDenumire_ingredient());
        }
        return entity;
    }

    public Ingrediente findById(Long id) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("from Ingrediente I where I.id = " + id);
        List<Ingrediente> result = query.list();
        try {
            return result.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        // create the select query
        // execute it
        // transform ResultSet to Person and return it
    }

    public List<Ingrediente> findAll() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("from Ingrediente");
        List<Ingrediente> result = query.list();
        return result;
    }

    public void delete(Ingrediente entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        databaseSession.delete(entity);
        databaseSession.getTransaction().commit();
    }

}
