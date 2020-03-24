package com.retete.backend.model;

import com.retete.backend.repository.IngredienteRepository;

import java.util.Objects;

public class RetetaCompleta {

    private String numeReteta;
    private Ingrediente ingredient1;
    private Ingrediente ingredient2;
    private Ingrediente ingredient3;
    private Ingrediente ingredient4;
    private Ingrediente ingredient5;
    private Ingrediente ingredient6;
    private Ingrediente ingredient7;
    private Ingrediente ingredient8;
    private Ingrediente ingredient9;
    private Ingrediente ingredient10;
    private Long id;
    private String timp_de_preparare;
    private Boolean depost;

    public RetetaCompleta(Reteta reteta) {
        this.numeReteta = reteta.getNumeReteta();
        this.id = reteta.getId();
        this.timp_de_preparare = reteta.getTimp_de_preparare();
        this.depost = reteta.eDePost();
        IngredienteRepository ingredienteRepository = new IngredienteRepository();
        Long ingredientId = reteta.getIngredient1();
        if (ingredientId != null) {

            this.ingredient1 = ingredienteRepository.findById(ingredientId);
            if (this.ingredient1 == null) {
                this.ingredient1 = new Ingrediente("Ingredientul a fost sters din baza de date. Va rog corectati reteta!");
            }
        }
        ingredientId = reteta.getIngredient2();
        if (ingredientId != null) {

            this.ingredient2 = ingredienteRepository.findById(ingredientId);
            if (this.ingredient2 == null) {
                this.ingredient2 = new Ingrediente("Ingredientul a fost sters din baza de date. Va rog corectati reteta!");
            }
        }
        ingredientId = reteta.getIngredient3();
        if (ingredientId != null) {


            this.ingredient3 = ingredienteRepository.findById(ingredientId);
            if (this.ingredient3 == null) {
                this.ingredient3 = new Ingrediente("Ingredientul a fost sters din baza de date. Va rog corectati reteta!");
            }
        }
        ingredientId = reteta.getIngredient4();
        if (ingredientId != null) {

            this.ingredient4 = ingredienteRepository.findById(ingredientId);
            if (this.ingredient4 == null) {
                this.ingredient4 = new Ingrediente("Ingredientul a fost sters din baza de date. Va rog corectati reteta!");
            }
        }
        ingredientId = reteta.getIngredient5();
        if (ingredientId != null) {

            this.ingredient5 = ingredienteRepository.findById(ingredientId);
            if (this.ingredient5 == null) {
                this.ingredient5 = new Ingrediente("Ingredientul a fost sters din baza de date. Va rog corectati reteta!");
            }
        }
        ingredientId = reteta.getIngredient6();
        if (ingredientId != null) {

            this.ingredient6 = ingredienteRepository.findById(ingredientId);
            if (this.ingredient6 == null) {
                this.ingredient6 = new Ingrediente("Ingredientul a fost sters din baza de date. Va rog corectati reteta!");
            }
        }
        ingredientId = reteta.getIngredient7();
        if (ingredientId != null) {


            this.ingredient7 = ingredienteRepository.findById(ingredientId);
            if (this.ingredient7 == null) {
                this.ingredient7 = new Ingrediente("Ingredientul a fost sters din baza de date. Va rog corectati reteta!");
            }
        }
        ingredientId = reteta.getIngredient8();
        if (ingredientId != null) {

            this.ingredient8 = ingredienteRepository.findById(ingredientId);
            if (this.ingredient8 == null) {
                this.ingredient8 = new Ingrediente("Ingredientul a fost sters din baza de date. Va rog corectati reteta!");
            }
        }
        ingredientId = reteta.getIngredient9();
        if (ingredientId != null) {

            this.ingredient9 = ingredienteRepository.findById(ingredientId);
            if (this.ingredient9 == null) {
                this.ingredient9 = new Ingrediente("Ingredientul a fost sters din baza de date. Va rog corectati reteta!");
            }
        }
        ingredientId = reteta.getIngredient10();
        if (ingredientId != null) {

            this.ingredient10 = ingredienteRepository.findById(ingredientId);
            if (this.ingredient10 == null) {
                this.ingredient10 = new Ingrediente("Ingredientul a fost sters din baza de date. Va rog corectati reteta!");
            }
        }
    }

    public String getNumeReteta() {
        return numeReteta;
    }

    public void setNumeReteta(String numeReteta) {
        this.numeReteta = numeReteta;
    }

    public Ingrediente getIngredient1() {
        return ingredient1;
    }

    public void setIngredient1(Ingrediente ingredient1) {
        this.ingredient1 = ingredient1;
    }

    public Ingrediente getIngredient2() {
        return ingredient2;
    }

    public void setIngredient2(Ingrediente ingredient2) {
        this.ingredient2 = ingredient2;
    }

    public Ingrediente getIngredient3() {
        return ingredient3;
    }

    public void setIngredient3(Ingrediente ingredient3) {
        this.ingredient3 = ingredient3;
    }

    public Ingrediente getIngredient4() {
        return ingredient4;
    }

    public void setIngredient4(Ingrediente ingredient4) {
        this.ingredient4 = ingredient4;
    }

    public Ingrediente getIngredient5() {
        return ingredient5;
    }

    public void setIngredient5(Ingrediente ingredient5) {
        this.ingredient5 = ingredient5;
    }

    public Ingrediente getIngredient6() {
        return ingredient6;
    }

    public void setIngredient6(Ingrediente ingredient6) {
        this.ingredient6 = ingredient6;
    }

    public Ingrediente getIngredient7() {
        return ingredient7;
    }

    public void setIngredient7(Ingrediente ingredient7) {
        this.ingredient7 = ingredient7;
    }

    public Ingrediente getIngredient8() {
        return ingredient8;
    }

    public void setIngredient8(Ingrediente ingredient8) {
        this.ingredient8 = ingredient8;
    }

    public Ingrediente getIngredient9() {
        return ingredient9;
    }

    public void setIngredient9(Ingrediente ingredient9) {
        this.ingredient9 = ingredient9;
    }

    public Ingrediente getIngredient10() {
        return ingredient10;
    }

    public void setIngredient10(Ingrediente ingredient10) {
        this.ingredient10 = ingredient10;
    }

    public Boolean getDepost() {
        return depost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimp_de_preparare() {
        return timp_de_preparare;
    }

    public void setTimp_de_preparare(String timp_de_preparare) {
        this.timp_de_preparare = timp_de_preparare;
    }

    public Boolean isDepost() {
        return depost;
    }

    public void setDepost(Boolean depost) {
        this.depost = depost;
    }

    @Override
    public String toString() {
        return "RetetaCompleta{" +
                "numeReteta='" + numeReteta + '\'' +
                ", ingredient1='" + ingredient1 + '\'' +
                ", ingredient2='" + ingredient2 + '\'' +
                ", ingredient3='" + ingredient3 + '\'' +
                ", ingredient4='" + ingredient4 + '\'' +
                ", ingredient5='" + ingredient5 + '\'' +
                ", ingredient6='" + ingredient6 + '\'' +
                ", ingredient7='" + ingredient7 + '\'' +
                ", ingredient8='" + ingredient8 + '\'' +
                ", ingredient9='" + ingredient9 + '\'' +
                ", ingredient10='" + ingredient10 + '\'' +
                ", id=" + id +
                ", timp_de_preparare='" + timp_de_preparare + '\'' +
                ", depost=" + depost +
                '}';
    }
}
