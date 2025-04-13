package com.mycompany.lb3;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD) // Для JAXB
public class Recipe {
    @JsonProperty("type")
    @XmlElement(name = "type")
    private String recipeType;

    @JsonProperty("ingredients")
    @XmlElement(name = "ingredients")
    private String ingredients; // Ингредиенты и их количество

    @JsonProperty("brewing_time")
    @XmlElement(name = "brewingTime")
    private int brewingTime; // Время приготовления

    @JsonProperty("effectiveness")
    @XmlElement(name = "effectiveness")
    private String effectiveness; // Эффективность

    // Конструктор по умолчанию
    public Recipe() {}

    // Геттеры и сеттеры
    public String getType() {
        return recipeType;
    }

    public void setType(String recipeType) {
        this.recipeType = recipeType;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getBrewingTime() {
        return brewingTime;
    }

    public void setBrewingTime(int brewingTime) {
        this.brewingTime = brewingTime;
    }

    public String getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(String effectiveness) {
        this.effectiveness = effectiveness;
    }
}