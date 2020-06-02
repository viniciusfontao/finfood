package br.com.finfood.services;

import br.com.finfood.entities.Ingredients;
import br.com.finfood.repositories.IngredientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientsServiceImpl implements IngredientsService {

    private final IngredientsRepository ingredientsRepository;

    @Autowired
    public IngredientsServiceImpl(IngredientsRepository ingredientsRepository) {
        this.ingredientsRepository = ingredientsRepository;
    }

    @Override
    public List<Ingredients> findAll() {
        return (List<Ingredients>) ingredientsRepository.findAll();
    }

    @Override
    public Ingredients save(Ingredients ingredients) {
        return ingredientsRepository.save(ingredients);
    }

    @Override
    public Boolean delete(Long id) {
        ingredientsRepository.deleteById(id);
        return true;
    }
}
