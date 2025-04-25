package com.mycompany.lb3;

import com.esotericsoftware.yamlbeans.YamlReader;
import java.io.File;
import java.io.FileReader;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class YamlHandler extends BaseHandler {
    private final Yaml yaml;

    public YamlHandler() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(options);
    }

    @Override
    public List<Monster> importData(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && (filePath.endsWith(".yaml") || filePath.endsWith(".yml"))) {
                YamlReader reader = new YamlReader(new FileReader(file, java.nio.charset.StandardCharsets.UTF_8));
                
                MonsterListWrapper wrapper = reader.read(MonsterListWrapper.class);
                reader.close();
                
                return wrapper != null ? wrapper.getCreatures() : null;
            }
        } catch (IOException e) {
            System.err.println("Error importing YAML data: " + e.getMessage());
        }
        return nextHandler != null ? nextHandler.importData(filePath) : null;
    }

    @Override
    public void exportData(String filePath, List<Monster> monsters) {
        try {
            if (filePath.endsWith(".yaml") || filePath.endsWith(".yml")) {
                MonsterListWrapper wrapper = new MonsterListWrapper();
                wrapper.setCreatures(monsters);

                List<Map<String, Object>> orderedMonsters = monsters.stream()
                        .map(this::convertToOrderedMap)
                        .toList();

                Map<String, Object> root = new LinkedHashMap<>();
                root.put("creatures", orderedMonsters);

                FileWriter writer = new FileWriter(filePath, java.nio.charset.StandardCharsets.UTF_8);
                yaml.dump(root, writer);
                writer.close();

                System.out.println("Data successfully exported to YAML file.");
                return;
            }
        } catch (IOException e) {
            System.err.println("Error exporting YAML data: " + e.getMessage());
        }
        if (nextHandler != null) {
            nextHandler.exportData(filePath, monsters);
        }
    }

    private Map<String, Object> convertToOrderedMap(Monster monster) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", monster.getName());
        map.put("description", monster.getDescription());
        map.put("dangerLevel", monster.getDangerLevel());
        map.put("habitat", monster.getHabitat());
        map.put("firstMention", monster.getFirstMention());
        map.put("vulnerabilities", monster.getVulnerabilities());
        map.put("immunities", monster.getImmunities());
        map.put("activeTime", monster.getActiveTime());
        map.put("height", monster.getHeight());
        map.put("weight", monster.getWeight());
        map.put("recipe", convertRecipeToMap(monster.getRecipe()));
        map.put("source", monster.getSource());
        return map;
    }
    
    private Map<String, Object> convertRecipeToMap(Recipe recipe) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", recipe.getType());
        map.put("ingredients", recipe.getIngredients());
        map.put("brewingTime", recipe.getBrewingTime());
        map.put("effectiveness", recipe.getEffectiveness());
        return map;
    }
}