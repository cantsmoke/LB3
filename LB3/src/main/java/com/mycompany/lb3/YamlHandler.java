package com.mycompany.lb3;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class YamlHandler extends BaseHandler {
    @Override
    public List<Monster> importData(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && filePath.endsWith(".yaml")) {
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
            if (filePath.endsWith(".yaml")) {
                MonsterListWrapper wrapper = new MonsterListWrapper();
                wrapper.setCreatures(monsters);
                
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
