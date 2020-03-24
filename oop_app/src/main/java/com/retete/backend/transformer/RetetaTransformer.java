package com.retete.backend.transformer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.retete.backend.model.Reteta;


public class RetetaTransformer implements Transformer<Reteta> {

	@Override
	public Reteta toModel(ResultSet resultSet) {
		try {
			if (resultSet.next()) {
				return buildReteta(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Reteta> toModelList(ResultSet resultSet) {
		List<Reteta> retete = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Reteta reteta = buildReteta(resultSet);
				if (reteta != null) {
					retete.add(reteta);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retete;
	}

	private Reteta buildReteta(ResultSet resultSet) throws SQLException {
		Reteta reteta = new Reteta();
		reteta.setId(resultSet.getLong("id"));
		reteta.setNumeReteta( resultSet.getString("DenumireR"));
		reteta.setIngredient1(resultSet.getLong("Ingredient1"));
		reteta.setIngredient2(resultSet.getLong("Ingredient2"));
		reteta.setIngredient3(resultSet.getLong("Ingredient3"));
		reteta.setIngredient4(resultSet.getLong("Ingredient4"));
		reteta.setIngredient5(resultSet.getLong("Ingredient5"));
		reteta.setTimp_de_preparare(resultSet.getString("timp"));
		return reteta;
	}

}
