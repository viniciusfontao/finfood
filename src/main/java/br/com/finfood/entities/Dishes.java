package br.com.finfood.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "dishes")
public class Dishes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "custom", nullable = false)
    private Boolean custom;

    @ManyToMany
    @JoinTable(
            name = "dishes_ingredients",
            joinColumns = @JoinColumn(name = "dishes_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredients_id")
    )
    private List<Ingredients> ingredientsList;

    public Dishes() {
    }

    public Dishes(@NotNull String name, @NotNull Boolean custom) {
        this.name = name;
        this.custom = custom;
    }

    public Dishes(@NotNull String name, @NotNull List<Ingredients> ingredientsList, @NotNull Boolean custom) {
        this(name, custom);
        this.ingredientsList = ingredientsList;
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

    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public Boolean getCustom() {
        return custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }
}
