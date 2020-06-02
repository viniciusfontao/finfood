package br.com.finfood.exceptions;

public class InvalidDishException extends Throwable {
    public InvalidDishException(String s) {
        super(s);
    }
}
