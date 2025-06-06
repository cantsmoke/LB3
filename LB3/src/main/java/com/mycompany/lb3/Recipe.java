package com.mycompany.lb3;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Recipe {
    @JsonProperty("type")
    @XmlElement(name = "type")
    private String type;

    @JsonProperty("ingredients")
    @XmlElement(name = "ingredients")
    private String ingredients;

    @JsonProperty("brewing_time")
    @XmlElement(name = "brewingTime")
    private int brewingTime;

    @JsonProperty("effectiveness")
    @XmlElement(name = "effectiveness")
    private String effectiveness;

    public Recipe() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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