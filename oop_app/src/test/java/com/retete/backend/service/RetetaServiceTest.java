package com.retete.backend.service;

import java.util.ArrayList;
import java.util.List;

import com.retete.backend.exceptions.IngredientInexistent;
import com.retete.backend.exceptions.RetetaInexistenta;
import org.junit.Assert;
import org.junit.Test;


import com.retete.backend.model.Reteta;

public class RetetaServiceTest {

    private RetetaService retetaService = new RetetaService();
    private static List<Long> idRetete =new ArrayList<>();

    @Test
    public void testFindAll() {
        Reteta newReteta = new Reteta();
        newReteta.setNumeReteta("Mamaliga cu branza de test "+System.currentTimeMillis());
        newReteta.setIngredient1(4L);//faina de porumb
        newReteta.setIngredient2(5L);
        newReteta.setTimp_de_preparare("30 de minute");
        newReteta = retetaService.save(newReteta);
        Assert.assertNotNull("Reteta salvata nu contine id!", newReteta.getId());
        idRetete.add(newReteta.getId());
        List<Reteta> retete = retetaService.findAll();
        Assert.assertNotNull(retete);
        Assert.assertTrue("Reteta introdusa nu a fost gasita!", retete.contains(newReteta));
    }

    @Test
    public void testFindDePost() {
        Reteta newReteta = new Reteta();
        newReteta.setNumeReteta("Mamaliga de post"+System.currentTimeMillis());
        newReteta.setIngredient1(4L);
        newReteta.setIngredient2(6L);
        newReteta.setIngredient3(8L);
        newReteta.setIngredient4(10L);
        newReteta.setTimp_de_preparare("25 de minute");
        newReteta = retetaService.save(newReteta);
        idRetete.add(newReteta.getId());
        List<Reteta> retete = retetaService.findAllDePost();
        Assert.assertNotNull(retete);
        Assert.assertTrue("Reteta de post introdusa nu a fost gasita!", retete.contains(newReteta));
        for (Reteta r : retete) {
            Assert.assertFalse("O reteta de dulce a fost gasita!", r.getNumeReteta().contains("Mamaliga cu branza de test"));
        }
    }


    @Test
    public void testUpdateReteta() {
        Reteta newReteta = new Reteta();
        newReteta.setNumeReteta("Reteta update test"+System.currentTimeMillis());
        newReteta.setIngredient1(8L);//faina de porumb
        newReteta.setIngredient2(11L);
        newReteta.setIngredient3(12L);
        newReteta.setTimp_de_preparare("3 ore");
        newReteta = retetaService.save(newReteta);
        idRetete.add(newReteta.getId());
        Assert.assertNotNull("Reteta salvata nu contine id!", newReteta.getId());
        Long id=newReteta.getId();
        String numeNou="reteta Actualizata"+System.currentTimeMillis();
        newReteta.setNumeReteta(numeNou);
        newReteta = retetaService.save(newReteta);
        newReteta=retetaService.findById(id);
        Assert.assertNotNull(newReteta);
        Assert.assertEquals("Reteta nu a fost actualizata corect!", numeNou, newReteta.getNumeReteta());
    }

    @Test
    public void testDelete() throws RetetaInexistenta {
        for (Long id : idRetete) {
            retetaService.delete(id);
            Reteta reteta;
            try {
                reteta = retetaService.findById(id);
            } catch (RetetaInexistenta retetaInexistenta) {
                reteta=null;
            }
            Assert.assertNull("Reteta nu a fost stearsa", reteta);
        }
    }
}
