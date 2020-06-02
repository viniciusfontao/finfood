package br.com.finfood.controllers;

import br.com.finfood.entities.Ingredients;
import br.com.finfood.services.IngredientsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ingredients")
public class IngredientsController {

    private final IngredientsServiceImpl ingredientsServiceImpl;

    @Autowired
    public IngredientsController(IngredientsServiceImpl ingredientsServiceImpl) {
        this.ingredientsServiceImpl = ingredientsServiceImpl;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Ingredients>> findAll() {
        try {
            List<Ingredients> ingredientsList = ingredientsServiceImpl.findAll();
            return new ResponseEntity<>(ingredientsList, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> save(@RequestBody Ingredients ingredients) {
        try {
            ingredients = ingredientsServiceImpl.save(ingredients);
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        } catch (DataIntegrityViolationException exception) {
            return new ResponseEntity<>("There are some required fields missing!", HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            return new ResponseEntity<>(ingredients, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            ingredientsServiceImpl.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>("Couldn't delete the requested id.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}
