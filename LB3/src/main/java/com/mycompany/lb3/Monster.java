package com.mycompany.lb3;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD) // Для JAXB
public class Monster {
    @JsonProperty("name") // Для Jackson
    @XmlElement(name = "name") // Для JAXB
    private String name;

    @JsonProperty("description")
    @XmlElement(name = "description")
    private String description;

    @JsonProperty("danger_level")
    @XmlElement(name = "dangerLevel")
    private int dangerLevel;

    @JsonProperty("habitat")
    @XmlElement(name = "habitat")
    private String habitat;

    @JsonProperty("first_mention")
    @XmlElement(name = "firstMention")
    private String firstMention;

    @JsonProperty("vulnerabilities")
    @XmlElement(name = "vulnerabilities")
    private String vulnerabilities;

    @JsonProperty("immunities")
    @XmlElementWrapper(name = "immunities") // Для JAXB
    @XmlElement(name = "immunity") // Для JAXB
    private List<String> immunities;

    @JsonProperty("active_time")
    @XmlElement(name = "activeTime")
    private String activeTime;

    @XmlElement(name = "height")
    @JsonProperty("height")
    private String height; // Было Object

    @XmlElement(name = "weight")
    @JsonProperty("weight")
    private String weight; // Было Object

    @JsonProperty("recipe")
    @XmlElement(name = "recipe")
    private Recipe recipe; // Ссылка на рецепт

    @JsonProperty("source") // Новый атрибут для хранения источника информации
    @XmlElement(name = "source")
    private String source;

    // Конструктор по умолчанию
    public Monster() {}

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(int dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getFirstMention() {
        return firstMention;
    }

    public void setFirstMention(String firstMention) {
        this.firstMention = firstMention;
    }

    public String getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(String vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    public List<String> getImmunities() {
        return immunities;
    }

    public void setImmunities(List<String> immunities) {
        this.immunities = immunities;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}