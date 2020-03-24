package com.retete.backend.repository;
import java.util.ArrayList;
import java.util.List;

import com.retete.backend.exceptions.RetetaDuplicat;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import com.retete.backend.model.Reteta;
import com.retete.backend.service.HibernateService;
import com.retete.backend.transformer.RetetaTransformer;

public class RetetaRepository implements Repository<Reteta> {

    private RetetaTransformer retetaTransformer;

    public RetetaRepository() {
        this.retetaTransformer = new RetetaTransformer();
    }

    public Reteta save(Reteta entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        try {
            databaseSession.saveOrUpdate(entity);
            databaseSession.save(entity);
            databaseSession.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new  RetetaDuplicat(entity.getNumeReteta());
        }


        // Some words about entity.getId():
        //   if the id is null it means we want to create a new db entry (INSERT statement)
        //   if the id is non-null it means we want to update a db entry (UPDATE statement)
        //
        //
        // create the insert/update statement out of entity attributes
        // execute query using DBConnector
        // fetch the just inserted entity with a select statement
        // transform ResultSet to Person and return it
        return entity;
    }

    public Reteta findById(Long id) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("from Reteta R where R.id = " + id);
        List<Reteta> result = query.list();
        try {
            return result.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        // create the select query
        // execute it
        // transform ResultSet to Person and return id
    }

    public List<Reteta> findAll() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("from Reteta");
        List<Reteta> result = query.list();
        return result;
    }

    public List<Reteta> findAllDePost() {
        List<Reteta> reteteDePost = new ArrayList<>();
        for (Reteta reteta : findAll()) {
            if (reteta.eDePost()) {
                reteteDePost.add(reteta);
            }
        }
        return reteteDePost;
    }

    public void delete(Reteta entity) {
		Session databaseSession = HibernateService.getSessionFactory().openSession();
		databaseSession.beginTransaction();
		databaseSession.delete(entity);
		databaseSession.getTransaction().commit();
    }

}
