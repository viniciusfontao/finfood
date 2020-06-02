package br.com.finfood.services;

import br.com.finfood.entities.Checkout;
import br.com.finfood.exceptions.EmptyDishException;
import br.com.finfood.exceptions.InvalidDishException;
import br.com.finfood.exceptions.InvalidIngredientsDishTypeException;
import br.com.finfood.exceptions.InvalidIngredientsException;

public interface CheckoutService {
    Checkout generate(Checkout checkout) throws EmptyDishException, InvalidDishException, InvalidIngredientsDishTypeException, InvalidIngredientsException;
}
