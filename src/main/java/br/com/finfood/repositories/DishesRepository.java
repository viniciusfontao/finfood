package br.com.finfood.repositories;

import br.com.finfood.entities.Dishes;
import org.springframework.data.repository.CrudRepository;

public interface DishesRepository extends CrudRepository<Dishes, Long> {
}
