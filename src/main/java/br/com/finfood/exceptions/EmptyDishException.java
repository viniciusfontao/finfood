package br.com.finfood.exceptions;

public class EmptyDishException extends Throwable {
    public EmptyDishException(String s) {
        super(s);
    }
}
