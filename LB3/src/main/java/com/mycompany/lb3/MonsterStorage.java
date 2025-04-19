package com.mycompany.lb3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonsterStorage {
    private final List<List<Monster>> monsterCollections = new ArrayList<>();
    private static Map<String, Integer> dangerLevelValueChanges = new HashMap<>();
    private static Map<Integer, String> collectionSources = new HashMap<>();

    public void addMonsterCollection(List<Monster> monsters) {
        monsterCollections.add(new ArrayList<>(monsters));
    }
    
    public void addCollectionSources(Integer num, String extension) {
        collectionSources.put(num, extension);
    }
    
    public Map<Integer, String> getCollectionSources(){
        return this.collectionSources;
    }
    
    public void addDangerLevelValueChanges(String name, int dangerLevel) {
        dangerLevelValueChanges.put(name, dangerLevel);
    }
    
    public Map<String, Integer> getDangerLevelValueChanges(){
        return this.dangerLevelValueChanges;
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