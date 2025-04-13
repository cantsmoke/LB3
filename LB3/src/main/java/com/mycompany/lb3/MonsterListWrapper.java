/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb3;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "creatures")
@XmlAccessorType(XmlAccessType.FIELD)
public class MonsterListWrapper {
    @XmlElement(name = "creature")
    @JsonProperty("creatures")
    private List<Monster> creatures;

    public List<Monster> getCreatures() {
        return creatures;
    }

    public void setCreatures(List<Monster> creatures) {
        this.creatures = creatures;
    }
}