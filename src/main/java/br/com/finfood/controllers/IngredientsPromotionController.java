package br.com.finfood.controllers;

import br.com.finfood.entities.IngredientsPromotion;
import br.com.finfood.services.IngredientsPromotionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ingredientsPromotion")
public class IngredientsPromotionController {

    private final IngredientsPromotionServiceImpl ingredientsPromotionServiceImpl;

    @Autowired
    public IngredientsPromotionController(IngredientsPromotionServiceImpl ingredientsPromotionServiceImpl) {
        this.ingredientsPromotionServiceImpl = ingredientsPromotionServiceImpl;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<IngredientsPromotion>> findAll() {
        try {
            List<IngredientsPromotion> ingredientsPromotionList = ingredientsPromotionServiceImpl.findAll();
            return new ResponseEntity<>(ingredientsPromotionList, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> save(@RequestBody IngredientsPromotion ingredientsPromotion) {
        try {
            ingredientsPromotion = ingredientsPromotionServiceImpl.save(ingredientsPromotion);
            return new ResponseEntity<>(ingredientsPromotion, HttpStatus.OK);
        } catch (DataIntegrityViolationException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>("There are some required fields missing!", HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            System.out.println("ultima");
            exception.printStackTrace();
            return new ResponseEntity<>(ingredientsPromotion, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            ingredientsPromotionServiceImpl.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>("Couldn't delete the requested id.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}
