package br.com.finfood.services;

import br.com.finfood.entities.Dishes;

import java.util.List;

public interface DishesService {
    List<Dishes> findAll();

    Dishes save(Dishes dishes);

    Boolean delete(Long id);
}
