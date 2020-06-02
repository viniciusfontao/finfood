package br.com.finfood.repositories;

import br.com.finfood.entities.IngredientsPromotion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngredientsPromotionRepository extends CrudRepository<IngredientsPromotion, Long> {

    @Query(value = "select * from ingredients_promotion where ingredient_have_id IN (:ingredientHaveId)", nativeQuery = true)
    List<IngredientsPromotion> findIngredientsPromotionsByIngredientHaveId(@Param("ingredientHaveId")  List<Long> ingredientHaveIds);
}
