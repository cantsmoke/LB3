package com.mycompany.lb3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.util.List;

public class YamlHandler extends BaseHandler {
    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    @Override
    public List<Monster> importData(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && (filePath.endsWith(".yaml") || filePath.endsWith(".yml"))) {
                MonsterListWrapper wrapper = yamlMapper.readValue(file, MonsterListWrapper.class);
                return wrapper.getCreatures();
            }
        } catch (Exception e) {
            System.err.println("YAML import error: " + e.getMessage());
            e.printStackTrace();
        }
        return nextHandler != null ? nextHandler.importData(filePath) : null;
    }

    @Override
    public void exportData(String filePath, List<Monster> monsters) {
        try {
            if (filePath.endsWith(".yaml") || filePath.endsWith(".yml")) {
                MonsterListWrapper wrapper = new MonsterListWrapper();
                wrapper.setCreatures(monsters);
                yamlMapper.writeValue(new File(filePath), wrapper);
                System.out.println("YAML export successful");
                return;
            }
        } catch (Exception e) {
            System.err.println("YAML export error: " + e.getMessage());
        }
        if (nextHandler != null) {
            nextHandler.exportData(filePath, monsters);
        }
    }
}