package br.com.finfood.services;

import br.com.finfood.entities.Dishes;
import br.com.finfood.repositories.DishesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishesServiceImpl implements DishesService {

    private final DishesRepository dishesRepository;

    @Autowired
    public DishesServiceImpl(DishesRepository dishesRepository) {
        this.dishesRepository = dishesRepository;
    }

    @Override
    public List<Dishes> findAll() {
        return (List<Dishes>) this.dishesRepository.findAll();
    }

    @Override
    public Dishes save(Dishes dishes) {
        return dishesRepository.save(dishes);
    }

    @Override
    public Boolean delete(Long id) {
        dishesRepository.deleteById(id);
        return true;
    }
}
