package br.com.finfood.repositories;

import br.com.finfood.entities.Ingredients;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IngredientsRepository extends CrudRepository<Ingredients, Long> {

    @Query(value = "select id, price from ingredients where id = :id", nativeQuery = true)
    Ingredients getPriceById(@Param("id") Long id);

    Ingredients getByName(String name);
}
