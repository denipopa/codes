package com.retete.backend.model;

import com.retete.backend.repository.IngredienteRepository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "reteta")
public class Reteta extends AbstractEntity {

    @Column
    @NotBlank(message="Numele retetei este obligatoriu")
    private String numeReteta;
    @Column
    @NotNull(message="Primul ingredient este obligatoriu")
    private Long ingredient1;
    @Column
    private Long ingredient2;
    @Column
    private Long ingredient3;
    @Column
    private Long ingredient4;
    @Column
    private Long ingredient5;
    @Column
    private Long ingredient6;
    @Column
    private Long ingredient7;
    @Column
    private Long ingredient8;
    @Column
    private Long ingredient9;
    @Column
    private Long ingredient10;

    @Column
    private String timp_de_preparare;


    public String getNumeReteta() {
        return numeReteta;
    }

    public void setNumeReteta(String numeReteta) {
        this.numeReteta = numeReteta.trim();
    }

    public Long getIngredient1() {
        return ingredient1;
    }

    public void setIngredient1(Long ingredient1) {
        this.ingredient1 = ingredient1;
    }

    public Long getIngredient2() {
        return ingredient2;
    }

    public void setIngredient2(Long ingredient2) {
        this.ingredient2 = ingredient2;
    }

    public Long getIngredient3() {
        return ingredient3;
    }

    public void setIngredient3(Long ingredient3) {
        this.ingredient3 = ingredient3;
    }

    public Long getIngredient4() {
        return ingredient4;
    }

    public void setIngredient4(Long ingredient4) {
        this.ingredient4 = ingredient4;
    }

    public Long getIngredient5() {
        return ingredient5;
    }

    public void setIngredient5(Long ingredient5) {
        this.ingredient5 = ingredient5;
    }

    public Long getIngredient6() {
        return ingredient6;
    }

    public void setIngredient6(Long ingredient6) {
        this.ingredient6 = ingredient6;
    }

    public Long getIngredient7() {
        return ingredient7;
    }

    public void setIngredient7(Long ingredient7) {
        this.ingredient7 = ingredient7;
    }

    public Long getIngredient8() {
        return ingredient8;
    }

    public void setIngredient8(Long ingredient8) {
        this.ingredient8 = ingredient8;
    }

    public Long getIngredient9() {
        return ingredient9;
    }

    public void setIngredient9(Long ingredient9) {
        this.ingredient9 = ingredient9;
    }

    public Long getIngredient10() {
        return ingredient10;
    }

    public void setIngredient10(Long ingredient10) {
        this.ingredient10 = ingredient10;
    }

    public String getTimp_de_preparare() {
        return timp_de_preparare;
    }

    public void setTimp_de_preparare(String timp_de_preparare) {
        this.timp_de_preparare = timp_de_preparare;
    }

    @Override
    public String toString() {
        return "Reteta{" +
                "numeReteta='" + numeReteta + '\'' +
                ", ingredient1=" + ingredient1 +
                ", ingredient2=" + ingredient2 +
                ", ingredient3=" + ingredient3 +
                ", ingredient4=" + ingredient4 +
                ", ingredient5=" + ingredient5 +
                ", ingredient6=" + ingredient6 +
                ", ingredient7=" + ingredient7 +
                ", ingredient8=" + ingredient8 +
                ", ingredient9=" + ingredient9 +
                ", ingredient10=" + ingredient10 +
                ", timp_de_preparare='" + timp_de_preparare + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reteta)) return false;
        Reteta reteta = (Reteta) o;
        return Objects.equals(getNumeReteta(), reteta.getNumeReteta()) &&
                Objects.equals(getIngredient1(), reteta.getIngredient1()) &&
                Objects.equals(getIngredient2(), reteta.getIngredient2()) &&
                Objects.equals(getIngredient3(), reteta.getIngredient3()) &&
                Objects.equals(getIngredient4(), reteta.getIngredient4()) &&
                Objects.equals(getIngredient5(), reteta.getIngredient5()) &&
                Objects.equals(getIngredient6(), reteta.getIngredient6()) &&
                Objects.equals(getIngredient7(), reteta.getIngredient7()) &&
                Objects.equals(getIngredient8(), reteta.getIngredient8()) &&
                Objects.equals(getIngredient9(), reteta.getIngredient9()) &&
                Objects.equals(getIngredient10(), reteta.getIngredient10()) &&
                Objects.equals(getTimp_de_preparare(), reteta.getTimp_de_preparare());
    }

    public Boolean eDePost() {
        IngredienteRepository ingredienteRepository = new IngredienteRepository();
        boolean dePost = true;
        try {
            if (ingredient1 != null && !ingredienteRepository.findById(ingredient1).getDePost()) dePost = false;
            if (ingredient2 != null && !ingredienteRepository.findById(ingredient2).getDePost()) dePost = false;
            if (ingredient3 != null && !ingredienteRepository.findById(ingredient3).getDePost()) dePost = false;
            if (ingredient4 != null && !ingredienteRepository.findById(ingredient4).getDePost()) dePost = false;
            if (ingredient5 != null && !ingredienteRepository.findById(ingredient5).getDePost()) dePost = false;
            if (ingredient6 != null && !ingredienteRepository.findById(ingredient6).getDePost()) dePost = false;
            if (ingredient7 != null && !ingredienteRepository.findById(ingredient7).getDePost()) dePost = false;
            if (ingredient8 != null && !ingredienteRepository.findById(ingredient8).getDePost()) dePost = false;
            if (ingredient9 != null && !ingredienteRepository.findById(ingredient9).getDePost()) dePost = false;
            if (ingredient10 != null && !ingredienteRepository.findById(ingredient10).getDePost()) dePost = false;
            return dePost;
        } catch (NullPointerException e) {
            return false;
        }
    }
}
