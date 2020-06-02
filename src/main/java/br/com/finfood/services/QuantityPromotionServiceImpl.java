package br.com.finfood.services;

import br.com.finfood.entities.QuantityPromotion;
import br.com.finfood.repositories.QuantityPromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityPromotionServiceImpl implements QuantityPromotionService {

    private final QuantityPromotionRepository quantityPromotionRepository;

    @Autowired
    public QuantityPromotionServiceImpl(QuantityPromotionRepository quantityPromotionRepository) {
        this.quantityPromotionRepository = quantityPromotionRepository;
    }

    @Override
    public List<QuantityPromotion> findAll() {
        return (List<QuantityPromotion>) quantityPromotionRepository.findAll();
    }

    @Override
    public QuantityPromotion save(QuantityPromotion quantityPromotion) {
        return quantityPromotionRepository.save(quantityPromotion);
    }

    @Override
    public Boolean delete(Long id) {
        quantityPromotionRepository.deleteById(id);
        return true;
    }
}
