package br.com.finfood.repositories;

import br.com.finfood.entities.QuantityPromotion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuantityPromotionRepository extends CrudRepository<QuantityPromotion, Long> {

    @Query(value = "select * from quantity_promotion where ingredient_id IN (:ingredientIds)", nativeQuery = true)
    List<QuantityPromotion> findQuantityPromotionsByIngredientIsIn(@Param("ingredientIds") List<Long> ingredientIds);
}
