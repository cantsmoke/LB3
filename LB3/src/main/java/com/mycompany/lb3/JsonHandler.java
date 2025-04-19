package com.mycompany.lb3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonHandler extends BaseHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public JsonHandler() {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    @Override
    public List<Monster> importData(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && filePath.endsWith(".json")) {
                ObjectMapper objectMapper = new ObjectMapper();
                MonsterListWrapper wrapper = objectMapper.readValue(file, MonsterListWrapper.class);
                return wrapper.getCreatures();
            }
        } catch (Exception e) {
            System.err.println("Error importing JSON data: " + e.getMessage());
        }
        return nextHandler != null ? nextHandler.importData(filePath) : null;
    }

    @Override
    public void exportData(String filePath, List<Monster> monsters) {
        try {
            if (filePath.endsWith(".json")) {
                objectMapper.writeValue(new File(filePath), monsters);
                System.out.println("Data successfully exported to JSON file.");
                return;
            }
        } catch (IOException e) {
            System.err.println("Error exporting JSON data: " + e.getMessage());
        }
        if (nextHandler != null) {
            nextHandler.exportData(filePath, monsters);
        }
    }
}