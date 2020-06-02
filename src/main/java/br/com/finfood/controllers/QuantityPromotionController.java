package br.com.finfood.controllers;

import br.com.finfood.entities.QuantityPromotion;
import br.com.finfood.services.QuantityPromotionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/quantityPromotion")
public class QuantityPromotionController {

    private final QuantityPromotionServiceImpl quantityPromotionServiceImpl;

    @Autowired
    public QuantityPromotionController(QuantityPromotionServiceImpl quantityPromotionServiceImpl) {
        this.quantityPromotionServiceImpl = quantityPromotionServiceImpl;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<QuantityPromotion>> findAll() {
        try {
            List<QuantityPromotion> quantityPromotionList = quantityPromotionServiceImpl.findAll();
            return new ResponseEntity<>(quantityPromotionList, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> save(@RequestBody QuantityPromotion quantityPromotion) {
        try {
            quantityPromotion = quantityPromotionServiceImpl.save(quantityPromotion);
            return new ResponseEntity<>(quantityPromotion, HttpStatus.OK);
        } catch (DataIntegrityViolationException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>("There are some required fields missing!", HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            System.out.println("ultima");
            exception.printStackTrace();
            return new ResponseEntity<>(quantityPromotion, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            quantityPromotionServiceImpl.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>("Couldn't delete the requested id.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}
