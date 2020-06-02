package br.com.finfood.controllers;

import br.com.finfood.entities.Dishes;
import br.com.finfood.entities.Ingredients;
import br.com.finfood.services.DishesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dishes")
public class DishesController {

    private final DishesServiceImpl dishesServiceImpl;

    @Autowired
    public DishesController(DishesServiceImpl dishesServiceImpl) {
        this.dishesServiceImpl = dishesServiceImpl;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Dishes>> findAll() {
        try {
            List<Dishes> dishesList = this.dishesServiceImpl.findAll();
            return new ResponseEntity<>(dishesList, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> save(@RequestBody Dishes dishes) {
        try {
            dishes = dishesServiceImpl.save(dishes);
            return new ResponseEntity<>(dishes, HttpStatus.OK);
        } catch (DataIntegrityViolationException exception) {
            return new ResponseEntity<>("There are some required fields missing!", HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            return new ResponseEntity<>(dishes, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable long id) {

        try {
            dishesServiceImpl.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>("Couldn't delete the requested id.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}
