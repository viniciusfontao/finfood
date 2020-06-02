package br.com.finfood.services;

import br.com.finfood.entities.*;
import br.com.finfood.exceptions.EmptyDishException;
import br.com.finfood.exceptions.InvalidDishException;
import br.com.finfood.exceptions.InvalidIngredientsDishTypeException;
import br.com.finfood.exceptions.InvalidIngredientsException;
import br.com.finfood.repositories.DishesRepository;
import br.com.finfood.repositories.IngredientsPromotionRepository;
import br.com.finfood.repositories.IngredientsRepository;
import br.com.finfood.repositories.QuantityPromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final DishesRepository dishesRepository;
    private final IngredientsRepository ingredientsRepository;
    private final IngredientsPromotionRepository ingredientsPromotionRepository;
    private final QuantityPromotionRepository quantityPromotionRepository;

    @Autowired
    public CheckoutServiceImpl(DishesRepository dishesRepository, IngredientsRepository ingredientsRepository, IngredientsPromotionRepository ingredientsPromotionRepository, QuantityPromotionRepository quantityPromotionRepository) {
        this.dishesRepository = dishesRepository;
        this.ingredientsRepository = ingredientsRepository;
        this.ingredientsPromotionRepository = ingredientsPromotionRepository;
        this.quantityPromotionRepository = quantityPromotionRepository;
    }

    @Override
    public Checkout generate(Checkout checkout) throws EmptyDishException,
            InvalidDishException,
            InvalidIngredientsDishTypeException,
            InvalidIngredientsException {
        Dishes dish = validateDish(checkout.getDishId());
        List<Ingredients> customIngredientsList = validateIngredients(dish, checkout.getIngredientsIds());
        checkout.setCustomIngredientsList(customIngredientsList);
        BigDecimal price = calculatePrice(checkout, dish);
        checkout.setPrice(price);
        checkout.setUuid(UUID.randomUUID());
        checkout.setMessage("Checkout complete!");
        return checkout;
    }

    private Dishes validateDish(Long dishId) throws EmptyDishException, InvalidDishException {
        if (Objects.isNull(dishId)) {
            throw new EmptyDishException("The dish can't be empty.");
        }

        Optional<Dishes> dishes = this.dishesRepository.findById(dishId);
        if (!dishes.isPresent()) {
            throw new InvalidDishException("Invalid dish id.");
        }
        return dishes.get();
    }

    private List<Ingredients> validateIngredients(Dishes dish, List<Long> ingredientsIds) throws InvalidIngredientsDishTypeException, InvalidIngredientsException {
        if (dish.getCustom() &&
                CollectionUtils.isEmpty(ingredientsIds)) {
            throw new InvalidIngredientsDishTypeException("You need to specify the ingredients for the current dish");
        }

        if (CollectionUtils.isEmpty(ingredientsIds)) {
            return new ArrayList<>();
        }

        TreeSet<Long> setIngredientsIds = new TreeSet<>(ingredientsIds);
        List<Ingredients> customIngredientsList = (List<Ingredients>) ingredientsRepository.findAllById(setIngredientsIds);

        boolean existsAllIds = setIngredientsIds.size() == customIngredientsList.size();
        if (!existsAllIds) {
            throw new InvalidIngredientsException("The current ingredients list contains invalid values.");
        }

        return customIngredientsList;
    }

    public BigDecimal calculatePrice(Checkout checkout, Dishes dish) {

        BigDecimal priceWithDiscount = calculatePriceByIngredientsPromotion(checkout, dish);
        if (!Objects.isNull(priceWithDiscount)) {
            return priceWithDiscount;
        }

        priceWithDiscount = calculatePriceByQuantityPromotion(checkout, dish);

        if (!Objects.isNull(priceWithDiscount)) {
            return priceWithDiscount;
        }

        return sumTotalIngredientsPrice(checkout, dish);
    }

    private BigDecimal calculatePriceByIngredientsPromotion(Checkout checkout, Dishes dish) {

        List<Long> ingredientsIds = getIngredientsIdsForPromotions(checkout, dish);
        List<IngredientsPromotion> ingredientsPromotionList = ingredientsPromotionRepository.findIngredientsPromotionsByIngredientHaveId(ingredientsIds);
        if (CollectionUtils.isEmpty(ingredientsPromotionList)) {
            return null;
        }

        Integer discountPayment = null;
        for (IngredientsPromotion ingredientsPromotion : ingredientsPromotionList) {
            if (ingredientsIds.contains(ingredientsPromotion.getIngredientDontHave().getId())) {
                discountPayment = ingredientsPromotion.getDiscountPayment();
                break;
            }
        }

        if (Objects.isNull(discountPayment)) {
            return null;
        }

        BigDecimal totalPrice = sumTotalIngredientsPrice(checkout, dish);
        float discountPerc = (float) discountPayment / 100;
        BigDecimal discount = totalPrice.multiply(BigDecimal.valueOf(discountPerc));
        return totalPrice.subtract(discount).setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal sumTotalIngredientsPrice(Checkout checkout, Dishes dish) {
        BigDecimal dishIngredientsPrice = dish.getIngredientsList()
                .stream()
                .map(Ingredients::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        BigDecimal customIngredientsPrice = checkout.getCustomIngredientsList()
                .stream()
                .map(Ingredients::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        return dishIngredientsPrice.add(customIngredientsPrice);
    }

    private BigDecimal calculatePriceByQuantityPromotion(Checkout checkout, Dishes dish) {
        List<Long> ingredientsIds = getIngredientsIdsForPromotions(checkout, dish);
        List<QuantityPromotion> quantityPromotionList = quantityPromotionRepository.findQuantityPromotionsByIngredientIsIn(ingredientsIds);
        if (CollectionUtils.isEmpty(quantityPromotionList)) {
            return null;
        }

        Map<Long, Integer> ingredientsQuantityMap = getIngredientsQuantityMap(ingredientsIds);

        BigDecimal price = BigDecimal.ZERO;
        boolean foundPromotion = false;
        for (Long ingredientId : ingredientsQuantityMap.keySet()) {
            Integer ingredientQuantity = ingredientsQuantityMap.get(ingredientId);
            QuantityPromotion quantityPromotionItem = quantityPromotionList.stream()
                    .filter(quantityPromotion -> ingredientId.equals(quantityPromotion.getIngredient().getId()) &&
                            ingredientQuantity.compareTo(quantityPromotion.getIngredientQuantity()) >= 0)
                    .findFirst()
                    .orElse(null);

            Ingredients ingredient = ingredientsRepository.getPriceById(ingredientId);
            if (!foundPromotion && !Objects.isNull(quantityPromotionItem)) {
                foundPromotion = Boolean.TRUE;
                int div = Math.floorDiv(ingredientQuantity, quantityPromotionItem.getIngredientQuantity());
                int mod = Math.floorMod(ingredientQuantity, quantityPromotionItem.getIngredientQuantity());
                int quantityToPay = mod + (quantityPromotionItem.getIngredientPayment() * div);
                BigDecimal total = ingredient.getPrice().multiply(BigDecimal.valueOf(quantityToPay));
                price = price.add(total);
                continue;
            }

            price = price.add(ingredient.getPrice().multiply(BigDecimal.valueOf(ingredientQuantity))).setScale(2, RoundingMode.HALF_EVEN);
        }
        return price;
    }

    private Map<Long, Integer> getIngredientsQuantityMap(List<Long> ingredientsIds) {
        Map<Long, Integer> ingredientsQuantityMap = new HashMap<>();
        ingredientsIds.forEach(ingredientId -> {
            if (ingredientsQuantityMap.containsKey(ingredientId)) {
                ingredientsQuantityMap.put(ingredientId, ingredientsQuantityMap.get(ingredientId) + 1);
            } else {
                ingredientsQuantityMap.put(ingredientId, 1);
            }
        });
        return ingredientsQuantityMap;
    }

    private List<Long> getIngredientsIdsForPromotions(Checkout checkout, Dishes dish) {
        List<Long> ingredientsIds = dish.getIngredientsList()
                .stream()
                .map(Ingredients::getId)
                .collect(Collectors.toList());

        ingredientsIds.addAll(checkout.getIngredientsIds());
        return ingredientsIds;
    }
}
