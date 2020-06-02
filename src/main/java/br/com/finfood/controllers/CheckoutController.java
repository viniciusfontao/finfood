package br.com.finfood.controllers;

import br.com.finfood.entities.Checkout;
import br.com.finfood.exceptions.EmptyDishException;
import br.com.finfood.exceptions.InvalidDishException;
import br.com.finfood.exceptions.InvalidIngredientsDishTypeException;
import br.com.finfood.exceptions.InvalidIngredientsException;
import br.com.finfood.services.CheckoutServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutServiceImpl checkoutServiceImpl;

    @Autowired
    public CheckoutController(CheckoutServiceImpl checkoutServiceImpl) {
        this.checkoutServiceImpl = checkoutServiceImpl;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public HttpEntity<Checkout> save(@RequestBody Checkout checkout) {
        try {
            checkoutServiceImpl.generate(checkout);
            return new ResponseEntity<>(checkout, HttpStatus.OK);
        } catch (EmptyDishException | InvalidDishException | InvalidIngredientsDishTypeException | InvalidIngredientsException e) {
            checkout.setMessage(e.getMessage());
            return new ResponseEntity<>(checkout, HttpStatus.BAD_REQUEST);
        }
    }
}
