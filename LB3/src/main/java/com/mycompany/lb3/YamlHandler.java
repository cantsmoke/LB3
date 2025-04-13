package com.mycompany.lb3;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class YamlHandler extends BaseHandler {
    @Override
    public List<Monster> importData(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && filePath.endsWith(".yaml")) {
                // Создаем YamlReader с указанием кодировки UTF-8
                YamlReader reader = new YamlReader(new FileReader(file, java.nio.charset.StandardCharsets.UTF_8));
                
                // Читаем данные в обертку
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
            if (filePath.endsWith(".yaml")) {
                // Создаем обертку и записываем данные
                MonsterListWrapper wrapper = new MonsterListWrapper();
                wrapper.setCreatures(monsters);
                
                // Настраиваем YamlWriter
                YamlWriter writer = new YamlWriter(new FileWriter(filePath, java.nio.charset.StandardCharsets.UTF_8));
                writer.getConfig().setClassTag("!MonsterListWrapper", MonsterListWrapper.class);
                writer.write(wrapper);
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
}
