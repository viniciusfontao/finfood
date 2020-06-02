package br.com.finfood.services;

import br.com.finfood.entities.Ingredients;
import br.com.finfood.repositories.IngredientsRepository;
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
public class IngredientsServiceImplTest {

    @MockBean
    private IngredientsRepository ingredientsRepository;

    @Autowired
    private IngredientsServiceImpl ingredientsServiceImpl;

    @Test
    void findAllEmpty() {
        given(ingredientsRepository.findAll()).willReturn(new ArrayList<>());
        List<Ingredients> actual = ingredientsServiceImpl.findAll();
        assertThat(actual).isEmpty();
    }

    @Test
    void findAllWithIngredients() {
        ArrayList<Ingredients> ingredientsList = new ArrayList<>();
        ingredientsList.add(new Ingredients("Alface", new BigDecimal("0.40")));
        ingredientsList.add(new Ingredients("Bacon", new BigDecimal("2.00")));
        given(ingredientsRepository.findAll()).willReturn(ingredientsList);

        List<Ingredients> actual = ingredientsServiceImpl.findAll();
        assertThat(actual)
                .isNotEmpty()
                .isSameAs(ingredientsList);
        assertThat(actual.get(0))
                .isEqualTo(ingredientsList.get(0));
    }

    @Test
    void save() {
        Ingredients ingredients = new Ingredients("Ingredient Name", new BigDecimal("10"));
        Ingredients expected = new Ingredients("Ingredient Name", new BigDecimal("10"));
        expected.setId(1L);

        given(ingredientsRepository.save(ingredients)).willReturn(expected);

        Ingredients actual = ingredientsServiceImpl.save(ingredients);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void delete() {
        Mockito.doNothing().when(ingredientsRepository).deleteById(1L);
        Boolean actual = ingredientsServiceImpl.delete(1L);
        assertThat(actual).isTrue();
    }
}
