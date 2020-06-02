package br.com.finfood.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ingredients_promotion",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"ingredient_have_id"}),
                @UniqueConstraint(columnNames = {"ingredient_dont_have_id"})
        })
public class IngredientsPromotion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(optional = false)
    private Ingredients ingredientHave;

    @OneToOne(optional = false)
    private Ingredients ingredientDontHave;

    @Column(name = "discount_payment", nullable = false)
    private Integer discountPayment;

    public IngredientsPromotion() {

    }

    public IngredientsPromotion(String name, Ingredients ingredientHave, Ingredients ingredientDontHave, Integer discountPayment) {
        this.name = name;
        this.ingredientHave = ingredientHave;
        this.ingredientDontHave = ingredientDontHave;
        this.discountPayment = discountPayment;
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

    public Ingredients getIngredientHave() {
        return ingredientHave;
    }

    public void setIngredientHave(Ingredients ingredientHave) {
        this.ingredientHave = ingredientHave;
    }

    public Ingredients getIngredientDontHave() {
        return ingredientDontHave;
    }

    public void setIngredientDontHave(Ingredients ingredientDontHave) {
        this.ingredientDontHave = ingredientDontHave;
    }

    public Integer getDiscountPayment() {
        return discountPayment;
    }

    public void setDiscountPayment(Integer discountPayment) {
        this.discountPayment = discountPayment;
    }
}
