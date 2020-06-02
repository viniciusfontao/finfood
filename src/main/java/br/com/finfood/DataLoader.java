package br.com.finfood;

import br.com.finfood.entities.Dishes;
import br.com.finfood.entities.Ingredients;
import br.com.finfood.entities.IngredientsPromotion;
import br.com.finfood.entities.QuantityPromotion;
import br.com.finfood.repositories.DishesRepository;
import br.com.finfood.repositories.IngredientsPromotionRepository;
import br.com.finfood.repositories.IngredientsRepository;
import br.com.finfood.repositories.QuantityPromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataLoader implements ApplicationRunner {

    private final IngredientsRepository ingredientsRepository;
    private final DishesRepository dishesRepository;
    private final IngredientsPromotionRepository ingredientsPromotionRepository;
    private final QuantityPromotionRepository quantityPromotionRepository;

    @Autowired
    public DataLoader(IngredientsRepository ingredientsRepository, DishesRepository dishesRepository, IngredientsPromotionRepository ingredientsPromotionRepository, QuantityPromotionRepository quantityPromotionRepository) {
        this.ingredientsRepository = ingredientsRepository;
        this.dishesRepository = dishesRepository;
        this.ingredientsPromotionRepository = ingredientsPromotionRepository;
        this.quantityPromotionRepository = quantityPromotionRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (ingredientsRepository.count() == 0) {
            ingredientsRepository.saveAll(createIngredientsList());
        }

        if (dishesRepository.count() == 0) {
            dishesRepository.saveAll(createDishesList());
        }

        if (ingredientsPromotionRepository.count() == 0) {
            ingredientsPromotionRepository.save(new IngredientsPromotion("Light", getIngredientsByName("Alface"), getIngredientsByName("Bacon"), 10));
        }

        if (quantityPromotionRepository.count() == 0) {
            quantityPromotionRepository.saveAll(createQuantityPromotionList());
        }
    }

    private List<Ingredients> createIngredientsList() {
        List<Ingredients> ingredientsList = new ArrayList<>();
        ingredientsList.add(new Ingredients("Alface", new BigDecimal("0.40")));
        ingredientsList.add(new Ingredients("Bacon", new BigDecimal("2.00")));
        ingredientsList.add(new Ingredients("Hambúrguer", new BigDecimal("3.00")));
        ingredientsList.add(new Ingredients("Ovo", new BigDecimal("0.80")));
        ingredientsList.add(new Ingredients("Queijo", new BigDecimal("1.50")));
        return ingredientsList;
    }

    private List<Dishes> createDishesList() {
        List<Dishes> dishesList = new ArrayList<>();
        dishesList.add(new Dishes("X-Bacon", this.getIngredientsByName(Arrays.asList("Hambúrguer", "Bacon", "Queijo")), Boolean.FALSE));
        dishesList.add(new Dishes("X-Burguer", this.getIngredientsByName(Arrays.asList("Hambúrguer", "Queijo")), Boolean.FALSE));
        dishesList.add(new Dishes("X-Egg", this.getIngredientsByName(Arrays.asList("Hambúrguer", "Ovo", "Queijo")), Boolean.FALSE));
        dishesList.add(new Dishes("X-Egg Bacon", this.getIngredientsByName(Arrays.asList("Hambúrguer", "Bacon", "Queijo", "Ovo")), Boolean.FALSE));
        dishesList.add(new Dishes("Monte o seu", Boolean.TRUE));
        return dishesList;
    }

    private List<Ingredients> getIngredientsByName(List<String> ingredientsName) {
        List<Ingredients> ingredientsList = (List<Ingredients>) ingredientsRepository.findAll();
        return ingredientsList
                .stream()
                .filter(ingredients -> ingredientsName.contains(ingredients.getName()))
                .collect(Collectors.toList());
    }

    private Ingredients getIngredientsByName(String ingredientName) {
        return ingredientsRepository.getByName(ingredientName);
    }

    private List<QuantityPromotion> createQuantityPromotionList() {
        List<QuantityPromotion> quantityPromotionList = new ArrayList<>();
        quantityPromotionList.add(new QuantityPromotion("Muito queijo", getIngredientsByName("Queijo"), 3, 2));
        quantityPromotionList.add(new QuantityPromotion("Muita carne", getIngredientsByName("Hambúrguer"), 3, 2));
        return quantityPromotionList;
    }
}
