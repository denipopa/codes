package com.retete.backend.service;
import java.util.List;
import com.retete.backend.exceptions.IngredientInexistent;
import com.retete.backend.exceptions.RetetaInexistenta;
import com.retete.backend.model.Reteta;
import com.retete.backend.repository.IngredienteRepository;
import com.retete.backend.repository.RetetaRepository;


public class RetetaService implements Service<Reteta> {

    private RetetaRepository retetaRepository;
    private IngredienteRepository ingredienteRepository;

    public RetetaService() {
        this.retetaRepository = new RetetaRepository();
        this.ingredienteRepository= new IngredienteRepository();
    }

    @Override
    public Reteta save(Reteta entity) throws IngredientInexistent {
        if (entity.getIngredient1() != null) {
            if (ingredienteRepository.findById(entity.getIngredient1()) == null) {
                throw new IngredientInexistent("Ingredientul cu id " + entity.getIngredient1() + " nu exista!");
            }
        }
		if (entity.getIngredient2() != null) {
			if (ingredienteRepository.findById(entity.getIngredient2()) == null) {
				throw new IngredientInexistent("Ingredientul cu id " + entity.getIngredient2() + " nu exista!");
			}
		}
		if (entity.getIngredient3() != null) {
			if (ingredienteRepository.findById(entity.getIngredient3()) == null) {
				throw new IngredientInexistent("Ingredientul cu id " + entity.getIngredient3() + " nu exista!");
			}
		}
		if (entity.getIngredient4() != null) {
			if (ingredienteRepository.findById(entity.getIngredient4()) == null) {
				throw new IngredientInexistent("Ingredientul cu id " + entity.getIngredient4() + " nu exista!");
			}
		}
		if (entity.getIngredient5() != null) {
			if (ingredienteRepository.findById(entity.getIngredient5()) == null) {
				throw new IngredientInexistent("Ingredientul cu id " + entity.getIngredient5() + " nu exista!");
			}
		}
		if (entity.getIngredient6() != null) {
			if (ingredienteRepository.findById(entity.getIngredient6()) == null) {
				throw new IngredientInexistent("Ingredientul cu id " + entity.getIngredient6() + " nu exista!");
			}
		}
		if (entity.getIngredient7() != null) {
			if (ingredienteRepository.findById(entity.getIngredient7()) == null) {
				throw new IngredientInexistent("Ingredientul cu id " + entity.getIngredient7() + " nu exista!");
			}
		}
		if (entity.getIngredient8() != null) {
			if (ingredienteRepository.findById(entity.getIngredient8()) == null) {
				throw new IngredientInexistent("Ingredientul cu id " + entity.getIngredient8() + " nu exista!");
			}
		}
		if (entity.getIngredient9() != null) {
			if (ingredienteRepository.findById(entity.getIngredient9()) == null) {
				throw new IngredientInexistent("Ingredientul cu id " + entity.getIngredient9() + " nu exista!");
			}
		}
		if (entity.getIngredient10() != null) {
			if (ingredienteRepository.findById(entity.getIngredient10()) == null) {
				throw new IngredientInexistent("Ingredientul cu id " + entity.getIngredient10() + " nu exista!");
			}
		}
        return retetaRepository.save(entity);
    }

    @Override
    public Reteta findById(Long id) {
        Reteta reteta=retetaRepository.findById(id);
        if(reteta==null) throw new RetetaInexistenta("Reteta cu id "+id+" nu exista");
    	return retetaRepository.findById(id);
    }

    @Override
    public List<Reteta> findAll() {
        return retetaRepository.findAll();
    }


    public List<Reteta> findAllDePost() {
        return retetaRepository.findAllDePost();
    }

    @Override
    public boolean delete(Long id) throws RetetaInexistenta{
        Reteta reteta = retetaRepository.findById(id);
        if (reteta == null) {
            throw new RetetaInexistenta("Reteta cu id " + id + " nu exista");
		} else {
            retetaRepository.delete(reteta);
            return true;
        }
    }
}
