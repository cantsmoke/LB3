package com.mycompany.lb3;

import java.util.ArrayList;
import java.util.List;

public class MonsterStorage {
    private final List<List<Monster>> monsterCollections = new ArrayList<>();

    public void addMonsterCollection(List<Monster> monsters) {
        monsterCollections.add(new ArrayList<>(monsters));
    }

    public List<List<Monster>> getMonsterCollections() {
        return new ArrayList<>(monsterCollections);
    }

    public List<Monster> getAllMonsters() {
        List<Monster> allMonsters = new ArrayList<>();
        for (List<Monster> collection : monsterCollections) {
            allMonsters.addAll(collection);
        }
        return allMonsters;
    }
}