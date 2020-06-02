package br.com.finfood.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "quantity_promotion", uniqueConstraints = {@UniqueConstraint(columnNames = {"ingredient_id"})})
public class QuantityPromotion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(optional = false)
    private Ingredients ingredient;

    @Column(name = "ingredient_quantity", nullable = false)
    private Integer ingredientQuantity;

    @Column(name = "ingredient_payment", nullable = false)
    private Integer ingredientPayment;

    public QuantityPromotion() {
    }

    public QuantityPromotion(String name, Ingredients ingredient, Integer ingredientQuantity, Integer ingredientPayment) {
        this.name = name;
        this.ingredient = ingredient;
        this.ingredientQuantity = ingredientQuantity;
        this.ingredientPayment = ingredientPayment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ingredients getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredients ingredient) {
        this.ingredient = ingredient;
    }

    public Integer getIngredientQuantity() {
        return ingredientQuantity;
    }

    public void setIngredientQuantity(Integer ingredientQuantity) {
        this.ingredientQuantity = ingredientQuantity;
    }

    public Integer getIngredientPayment() {
        return ingredientPayment;
    }

    public void setIngredientPayment(Integer ingredientPayment) {
        this.ingredientPayment = ingredientPayment;
    }
}
