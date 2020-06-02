package br.com.finfood.exceptions;

public class InvalidIngredientsDishTypeException extends Throwable {
    public InvalidIngredientsDishTypeException(String s) {
        super(s);
    }
}
