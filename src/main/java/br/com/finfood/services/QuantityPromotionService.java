package br.com.finfood.services;

import br.com.finfood.entities.QuantityPromotion;

import java.util.List;

public interface QuantityPromotionService {
    List<QuantityPromotion> findAll();

    QuantityPromotion save(QuantityPromotion quantityPromotion);

    Boolean delete(Long id);
}
