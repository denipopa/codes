package com.retete.backend.transformer;

import com.retete.backend.model.Ingrediente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredienteTransformer implements Transformer<Ingrediente>  {

    @Override
    public Ingrediente toModel(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return buildIngredient(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ingrediente> toModelList(ResultSet resultSet) {
        List<Ingrediente> ingrediente = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Ingrediente ingredient = buildIngredient(resultSet);
                if (ingredient != null) {
                    ingrediente.add(ingredient);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingrediente;
    }

    private Ingrediente buildIngredient(ResultSet resultSet) throws SQLException {
        Ingrediente ingredient = new Ingrediente();
        ingredient.setId(resultSet.getLong("id"));
        ingredient.setDenumire_ingredient(resultSet.getString("denumire_ingredient"));
        ingredient.setDePost(resultSet.getBoolean("dePost"));
        ingredient.setUnitate_de_masura(resultSet.getString("unitate_de_masura"));
        return ingredient;
    }


}
