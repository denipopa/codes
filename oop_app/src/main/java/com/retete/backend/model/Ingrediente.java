package com.retete.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "ingrediente")
public class Ingrediente extends AbstractEntity {

    @Column
    @NotBlank(message="Numele ingredientului este obligatoriu")
    private String denumire_ingredient;
    @Column
    @NotNull(message="Tipul ingredientului este obligatoriu")
    private Boolean dePost;

    @Column
    private String unitate_de_masura;

    public Ingrediente(){
        super();
    }
    public Ingrediente(String denumire){
        this.denumire_ingredient=denumire;
    }
        public String getDenumire_ingredient() {
        return denumire_ingredient;
    }

    public void setDenumire_ingredient(String denumire_ingredient) {
        this.denumire_ingredient = denumire_ingredient.trim();
    }

    public Boolean getDePost() {
        return dePost;
    }

    public void setDePost(Boolean dePost) {
        this.dePost = dePost;
    }

    public String getUnitate_de_masura() {
        return unitate_de_masura;
    }

    public void setUnitate_de_masura(String unitate_de_masura) {
        this.unitate_de_masura = unitate_de_masura;
    }

    @Override
    public String toString() {
        return "Ingrediente{" +
                "denumire_ingredient='" + denumire_ingredient + '\'' +
                ", dePost=" + dePost +
                ", unitate_de_masura='" + unitate_de_masura + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingrediente)) return false;
        Ingrediente that = (Ingrediente) o;
        return Objects.equals(getDenumire_ingredient(), that.getDenumire_ingredient()) &&
                Objects.equals(getDePost(), that.getDePost()) &&
                Objects.equals(getUnitate_de_masura(), that.getUnitate_de_masura());
    }
}
