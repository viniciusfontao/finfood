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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class CheckoutServiceImplTest {

    @MockBean
    private DishesRepository dishesRepository;

    @MockBean
    private IngredientsRepository ingredientsRepository;

    @MockBean
    private QuantityPromotionRepository quantityPromotionRepository;

    @MockBean
    private IngredientsPromotionRepository ingredientsPromotionRepository;

    @Autowired
    private CheckoutServiceImpl checkoutServiceImpl;

    @Test
    public void validateDishDishIdNull() {
        assertThrows(EmptyDishException.class, () -> checkoutServiceImpl.generate(new Checkout()));
    }

    @Test
    public void validateDishNotPresent() {
        Checkout checkout = new Checkout();
        checkout.setDishId(1L);
        given(dishesRepository.findById(1L)).willReturn(Optional.empty());
        assertThrows(InvalidDishException.class, () -> checkoutServiceImpl.generate(checkout));
    }

    @Test
    public void validateIngredientsCustomAndEmptyIngredients() {
        Checkout checkout = new Checkout();
        checkout.setDishId(1L);
        Dishes dishes = new Dishes("Custom", Boolean.TRUE);
        given(dishesRepository.findById(1L)).willReturn(Optional.of(dishes));
        assertThrows(InvalidIngredientsDishTypeException.class, () -> checkoutServiceImpl.generate(checkout));
    }

    @Test
    public void validateIngredientsEmptyIngredientsIds() {
        Checkout checkout = new Checkout();
        CheckoutServiceImpl spyCheckoutService = Mockito.spy(checkoutServiceImpl);
        checkout.setDishId(1L);
        Dishes dishes = new Dishes("Custom", Boolean.FALSE);
        given(dishesRepository.findById(1L)).willReturn(Optional.of(dishes));
        try {
            Mockito.doReturn(BigDecimal.TEN).when(spyCheckoutService).calculatePrice(Mockito.any(), Mockito.any());
            Checkout actual = spyCheckoutService.generate(checkout);
            assertThat(actual.getPrice())
                    .isEqualTo(BigDecimal.TEN);
            assertThat(actual.getMessage())
                    .isEqualTo("Checkout complete!");
        } catch (Exception | EmptyDishException | InvalidDishException | InvalidIngredientsDishTypeException | InvalidIngredientsException ignored) {
        }
    }

    @Test
    public void validateIngredientsInvalidIngredientsValues() {
        Checkout checkout = new Checkout();
        checkout.setIngredientsIds(Arrays.asList(1L, 2L));
        checkout.setDishId(1L);

        Dishes dishes = new Dishes("X-BACON", Boolean.FALSE);

        given(dishesRepository.findById(1L)).willReturn(Optional.of(dishes));

        Ingredients ingredients = new Ingredients();
        ingredients.setId(1L);
        List<Ingredients> ingredientsList = new ArrayList<>();
        ingredientsList.add(ingredients);
        given(ingredientsRepository.findAllById(new TreeSet<>(Arrays.asList(1L, 2L)))).willReturn(ingredientsList);

        assertThrows(InvalidIngredientsException.class, () -> checkoutServiceImpl.generate(checkout));
    }

    @Test
    public void validateIngredientsReturnCustomIngredients() {
        Checkout checkout = new Checkout();
        checkout.setIngredientsIds(Collections.singletonList(1L));
        checkout.setDishId(1L);

        Dishes dishes = new Dishes("X-BACON", Boolean.FALSE);

        given(dishesRepository.findById(1L)).willReturn(Optional.of(dishes));

        Ingredients ingredients = new Ingredients();
        ingredients.setId(1L);
        List<Ingredients> ingredientsList = new ArrayList<>();
        ingredientsList.add(ingredients);
        given(ingredientsRepository.findAllById(new TreeSet<>(Collections.singletonList(1L)))).willReturn(ingredientsList);

        CheckoutServiceImpl spyCheckoutService = Mockito.spy(checkoutServiceImpl);

        Mockito.doReturn(BigDecimal.TEN).when(spyCheckoutService).calculatePrice(Mockito.any(), Mockito.any());
        try {
            Checkout actual = spyCheckoutService.generate(checkout);
            assertThat(actual.getPrice())
                    .isEqualTo(BigDecimal.TEN);
            assertThat(actual.getMessage())
                    .isEqualTo("Checkout complete!");
        } catch (EmptyDishException | InvalidDishException | InvalidIngredientsDishTypeException | InvalidIngredientsException ignored) {
        }
    }

    @Test
    public void calculatePriceSumTotalIngredientsPrice() {
        Checkout checkout = new Checkout();
        checkout.setDishId(1L);
        checkout.setIngredientsIds(Collections.singletonList(2L));
        Ingredients ingredients = new Ingredients();
        ingredients.setId(2L);
        ingredients.setPrice(BigDecimal.TEN);
        checkout.setCustomIngredientsList(Collections.singletonList(ingredients));

        ingredients = new Ingredients();
        ingredients.setId(1L);
        ingredients.setPrice(BigDecimal.TEN);
        Dishes dishes = new Dishes("X-BACON", Collections.singletonList(ingredients), Boolean.FALSE);

        CheckoutServiceImpl spyCheckoutService = Mockito.spy(checkoutServiceImpl);
        Mockito.doReturn(null).when(spyCheckoutService).calculatePriceByIngredientsPromotion(Mockito.any(), Mockito.any());
        Mockito.doReturn(null).when(spyCheckoutService).calculatePriceByQuantityPromotion(Mockito.any(), Mockito.any());
        BigDecimal actual = spyCheckoutService.calculatePrice(checkout, dishes);
        assertThat(actual).isEqualTo(new BigDecimal("20"));
    }

    @Test
    public void calculatePriceCalculatePriceByIngredientsPromotionNotNull() {
        CheckoutServiceImpl spyCheckoutService = Mockito.spy(checkoutServiceImpl);
        Mockito.doReturn(BigDecimal.TEN).when(spyCheckoutService).calculatePriceByIngredientsPromotion(Mockito.any(), Mockito.any());
        BigDecimal actual = spyCheckoutService.calculatePrice(new Checkout(), new Dishes());
        assertThat(actual).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void calculatePriceCalculatePriceByQuantityPromotionNotNull() {
        CheckoutServiceImpl spyCheckoutService = Mockito.spy(checkoutServiceImpl);
        Mockito.doReturn(null).when(spyCheckoutService).calculatePriceByIngredientsPromotion(Mockito.any(), Mockito.any());
        Mockito.doReturn(BigDecimal.TEN).when(spyCheckoutService).calculatePriceByQuantityPromotion(Mockito.any(), Mockito.any());
        BigDecimal actual = spyCheckoutService.calculatePrice(new Checkout(), new Dishes());
        assertThat(actual).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void calculatePriceByIngredientsPromotionEmptyIngredientsPromotionList() {
        Checkout checkout = new Checkout();
        checkout.setDishId(1L);
        checkout.setIngredientsIds(Collections.singletonList(2L));

        Ingredients ingredients = new Ingredients();
        ingredients.setId(1L);
        ingredients.setPrice(BigDecimal.TEN);
        Dishes dishes = new Dishes("X-BACON", Collections.singletonList(ingredients), Boolean.FALSE);
        given(ingredientsPromotionRepository.findIngredientsPromotionsByIngredientHaveId(Arrays.asList(2L, 1L))).willReturn(new ArrayList<>());
        BigDecimal actual = checkoutServiceImpl.calculatePriceByIngredientsPromotion(checkout, dishes);
        assertThat(actual).isNull();
    }

    @Test
    public void calculatePriceByIngredientsPromotionDiscountPaymentNull() {
        Checkout checkout = new Checkout();
        checkout.setDishId(1L);
        checkout.setIngredientsIds(Collections.singletonList(2L));

        Ingredients ingredients = new Ingredients();
        ingredients.setId(1L);
        ingredients.setPrice(BigDecimal.TEN);
        Dishes dishes = new Dishes("X-BACON", Collections.singletonList(ingredients), Boolean.FALSE);

        IngredientsPromotion ingredientsPromotion = new IngredientsPromotion();
        ingredientsPromotion.setIngredientHave(ingredients);

        ingredients = new Ingredients();
        ingredients.setId(2L);
        ingredientsPromotion.setIngredientDontHave(ingredients);

        List<IngredientsPromotion> ingredientsPromotionList = Collections.singletonList(ingredientsPromotion);

        given(ingredientsPromotionRepository.findIngredientsPromotionsByIngredientHaveId(Arrays.asList(1L, 2L))).willReturn(ingredientsPromotionList);
        BigDecimal actual = checkoutServiceImpl.calculatePriceByIngredientsPromotion(checkout, dishes);
        assertThat(actual).isNull();
    }

    @Test
    public void calculatePriceByIngredientsPromotionDiscountPaymentNotNull() {
        Checkout checkout = new Checkout();
        checkout.setDishId(1L);
        checkout.setIngredientsIds(Collections.singletonList(1L));
        Ingredients ingredients = new Ingredients();
        ingredients.setId(1L);
        ingredients.setPrice(BigDecimal.TEN);
        checkout.setCustomIngredientsList(Collections.singletonList(ingredients));

        ingredients = new Ingredients();
        ingredients.setId(2L);
        ingredients.setPrice(BigDecimal.TEN);
        Dishes dishes = new Dishes("X-BACON", Collections.singletonList(ingredients), Boolean.FALSE);

        IngredientsPromotion ingredientsPromotion = new IngredientsPromotion();
        ingredientsPromotion.setIngredientHave(ingredients);
        ingredientsPromotion.setDiscountPayment(10);

        ingredients = new Ingredients();
        ingredients.setId(3L);
        ingredientsPromotion.setIngredientDontHave(ingredients);

        List<IngredientsPromotion> ingredientsPromotionList = Collections.singletonList(ingredientsPromotion);

        given(ingredientsPromotionRepository.findIngredientsPromotionsByIngredientHaveId(Arrays.asList(2L, 1L))).willReturn(ingredientsPromotionList);
        BigDecimal actual = checkoutServiceImpl.calculatePriceByIngredientsPromotion(checkout, dishes);
        assertThat(actual).isEqualTo(new BigDecimal("18.00"));
    }

    @Test
    public void calculatePriceByQuantityPromotionEmptyQuantityPromotionList() {
        Checkout checkout = new Checkout();
        checkout.setDishId(1L);
        checkout.setIngredientsIds(Collections.singletonList(2L));

        Ingredients ingredients = new Ingredients();
        ingredients.setId(1L);
        ingredients.setPrice(BigDecimal.TEN);
        Dishes dishes = new Dishes("X-BACON", Collections.singletonList(ingredients), Boolean.FALSE);
        given(quantityPromotionRepository.findQuantityPromotionsByIngredientIsIn(Arrays.asList(2L, 1L))).willReturn(new ArrayList<>());
        BigDecimal actual = checkoutServiceImpl.calculatePriceByQuantityPromotion(checkout, dishes);
        assertThat(actual).isNull();
    }

    @Test
    public void calculatePriceByQuantityPromotionWithPromotion() {
        Checkout checkout = new Checkout();
        checkout.setDishId(1L);
        checkout.setIngredientsIds(Arrays.asList(1L, 1L, 2L));

        Ingredients ingredients = new Ingredients();
        ingredients.setId(1L);
        ingredients.setPrice(BigDecimal.TEN);
        Dishes dishes = new Dishes("X-BACON", Collections.singletonList(ingredients), Boolean.FALSE);

        QuantityPromotion quantityPromotion = new QuantityPromotion("Promotion 1", ingredients, 3, 2);

        given(quantityPromotionRepository.findQuantityPromotionsByIngredientIsIn(Arrays.asList(1L, 1L, 1L, 2L))).willReturn(Collections.singletonList(quantityPromotion));

        given(ingredientsRepository.getPriceById(1L)).willReturn(ingredients);
        ingredients = new Ingredients();
        ingredients.setId(1L);
        ingredients.setPrice(BigDecimal.TEN);
        given(ingredientsRepository.getPriceById(2L)).willReturn(ingredients);

        BigDecimal actual = checkoutServiceImpl.calculatePriceByQuantityPromotion(checkout, dishes);
        assertThat(actual).isEqualTo(new BigDecimal("30.00"));
    }
}
