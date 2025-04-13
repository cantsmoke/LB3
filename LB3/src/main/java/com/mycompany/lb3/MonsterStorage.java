/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb3;

import java.util.ArrayList;
import java.util.List;

public class MonsterStorage {
    private final List<List<Monster>> monsterCollections = new ArrayList<>();

    // Добавление новой коллекции монстров
    public void addMonsterCollection(List<Monster> monsters) {
        monsterCollections.add(new ArrayList<>(monsters));
    }

    // Получение всех коллекций монстров
    public List<List<Monster>> getMonsterCollections() {
        return new ArrayList<>(monsterCollections);
    }

    // Получение всех монстров из всех коллекций
    public List<Monster> getAllMonsters() {
        List<Monster> allMonsters = new ArrayList<>();
        for (List<Monster> collection : monsterCollections) {
            allMonsters.addAll(collection);
        }
        return allMonsters;
    }
}