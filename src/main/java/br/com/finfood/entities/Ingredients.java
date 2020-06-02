package br.com.finfood.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "ingredients")
public class Ingredients implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "price", nullable = false)
    @Min(value = 1L, message = "Please select")
    private BigDecimal price;

    @JsonIgnore
    @ManyToMany(mappedBy = "ingredientsList", cascade = CascadeType.REMOVE)
    private List<Dishes> dishesList;

    @JsonIgnore
    @OneToOne(mappedBy = "ingredientHave", cascade = CascadeType.REMOVE)
    private IngredientsPromotion ingredientsPromotionHave;

    @JsonIgnore
    @OneToOne(mappedBy = "ingredientDontHave", cascade = CascadeType.REMOVE)
    private IngredientsPromotion ingredientsPromotionDontHave;

    public Ingredients() {
    }

    public Ingredients(@NotNull String name, @NotNull BigDecimal price) {
        this.name = name;
        this.price = price;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Dishes> getDishesList() {
        return dishesList;
    }

    public void setDishesList(List<Dishes> dishesList) {
        this.dishesList = dishesList;
    }

    public IngredientsPromotion getIngredientsPromotionHave() {
        return ingredientsPromotionHave;
    }

    public void setIngredientsPromotionHave(IngredientsPromotion ingredientsPromotionHave) {
        this.ingredientsPromotionHave = ingredientsPromotionHave;
    }

    public IngredientsPromotion getIngredientsPromotionDontHave() {
        return ingredientsPromotionDontHave;
    }

    public void setIngredientsPromotionDontHave(IngredientsPromotion ingredientsPromotionDontHave) {
        this.ingredientsPromotionDontHave = ingredientsPromotionDontHave;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
