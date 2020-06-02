package br.com.finfood.services;

import br.com.finfood.entities.IngredientsPromotion;
import br.com.finfood.repositories.IngredientsPromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientsPromotionServiceImpl implements IngredientsPromotionService {

    private final IngredientsPromotionRepository ingredientsPromotionRepository;

    @Autowired
    public IngredientsPromotionServiceImpl(IngredientsPromotionRepository ingredientsPromotionRepository) {
        this.ingredientsPromotionRepository = ingredientsPromotionRepository;
    }

    @Override
    public List<IngredientsPromotion> findAll() {
        return (List<IngredientsPromotion>) ingredientsPromotionRepository.findAll();
    }

    @Override
    public IngredientsPromotion save(IngredientsPromotion ingredientsPromotion) {
        return ingredientsPromotionRepository.save(ingredientsPromotion);
    }

    @Override
    public Boolean delete(Long id) {
        ingredientsPromotionRepository.deleteById(id);
        return true;
    }
}
