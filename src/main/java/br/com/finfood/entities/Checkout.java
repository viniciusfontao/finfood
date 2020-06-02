package br.com.finfood.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Checkout {

    private Long dishId;
    private List<Long> ingredientsIds = new ArrayList<>();
    private UUID uuid;
    private String message;
    private BigDecimal price;

    @JsonIgnore
    private List<Ingredients> customIngredientsList;

    public Checkout() {
    }

    public Checkout(Long dishId, List<Long> ingredientsIds) {
        this.dishId = dishId;
        this.ingredientsIds = ingredientsIds;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public List<Long> getIngredientsIds() {
        return ingredientsIds;
    }

    public void setIngredientsIds(List<Long> ingredientsIds) {
        this.ingredientsIds = ingredientsIds;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Ingredients> getCustomIngredientsList() {
        return customIngredientsList;
    }

    public void setCustomIngredientsList(List<Ingredients> customIngredientsList) {
        this.customIngredientsList = customIngredientsList;
    }
}
