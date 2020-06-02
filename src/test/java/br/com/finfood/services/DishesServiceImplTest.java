package br.com.finfood.services;

import br.com.finfood.entities.Dishes;
import br.com.finfood.entities.Ingredients;
import br.com.finfood.repositories.DishesRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class DishesServiceImplTest {

    @MockBean
    private DishesRepository dishesRepository;

    @Autowired
    private DishesServiceImpl dishesServiceImpl;

    @Test
    void findAllEmpty() {
        given(dishesRepository.findAll()).willReturn(new ArrayList<>());
        List<Dishes> actual = dishesServiceImpl.findAll();
        assertThat(actual).isEmpty();
    }

    @Test
    void findAllWithDishes() {
        ArrayList<Ingredients> ingredientsList = new ArrayList<>();
        Dishes dishes = new Dishes("X-BACON", false);
        ingredientsList.add(new Ingredients("Hambúrguer", new BigDecimal("3.00")));
        ingredientsList.add(new Ingredients("Queijo", new BigDecimal("1.50")));
        ingredientsList.add(new Ingredients("Bacon", new BigDecimal("2.00")));
        dishes.setIngredientsList(ingredientsList);

        List<Dishes> dishesList = new ArrayList<>();
        dishesList.add(dishes);
        dishesList.add(new Dishes("Custom", true));
        given(dishesRepository.findAll()).willReturn(dishesList);

        List<Dishes> actual = dishesServiceImpl.findAll();
        assertThat(actual)
                .isNotEmpty()
                .isEqualTo(dishesList);
        assertThat(actual.get(0))
                .isEqualTo(dishesList.get(0));
        assertThat(actual.get(1).getCustom())
                .isEqualTo(true);
        assertThat(actual.get(1).getIngredientsList())
                .isNull();

    }

    @Test
    void save() {
        ArrayList<Ingredients> ingredientsList = new ArrayList<>();
        Dishes dishes = new Dishes("X-BACON", false);
        Dishes expected = new Dishes("X-BACON", false);
        expected.setId(1L);

        ingredientsList.add(new Ingredients("Hambúrguer", new BigDecimal("3.00")));
        ingredientsList.add(new Ingredients("Queijo", new BigDecimal("1.50")));
        ingredientsList.add(new Ingredients("Bacon", new BigDecimal("2.00")));

        dishes.setIngredientsList(ingredientsList);
        expected.setIngredientsList(ingredientsList);


        given(dishesRepository.save(dishes)).willReturn(expected);

        Dishes actual = dishesServiceImpl.save(dishes);
        assertThat(actual).isEqualTo(expected);
        assertThat(actual.getId()).isEqualTo(1L);
    }

    @Test
    void delete() {
        Mockito.doNothing().when(dishesRepository).deleteById(1L);
        Boolean actual = dishesServiceImpl.delete(1L);
        assertThat(actual).isTrue();
    }
}
