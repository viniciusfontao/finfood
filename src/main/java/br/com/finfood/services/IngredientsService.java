package br.com.finfood.services;

import br.com.finfood.entities.Ingredients;

import java.util.List;

public interface IngredientsService {
    List<Ingredients> findAll();

    Ingredients save(Ingredients ingredients);

    Boolean delete(Long id);
}
