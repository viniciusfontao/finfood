package br.com.finfood.services;

import br.com.finfood.entities.IngredientsPromotion;

import java.util.List;

public interface IngredientsPromotionService {
    List<IngredientsPromotion> findAll();

    IngredientsPromotion save(IngredientsPromotion ingredientsPromotion);

    Boolean delete(Long id);
}
