package com.mycompany.lb3;

import java.io.File;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class YamlHandler extends BaseHandler {
    @Override
    public List<Monster> importData(String filePath) {
        try {
            if (filePath.endsWith(".yaml")) {
                Yaml yaml = new Yaml();
                FileInputStream inputStream = new FileInputStream(filePath);
                MonsterListWrapper wrapper = yaml.loadAs(inputStream, MonsterListWrapper.class);
                inputStream.close();
                return wrapper.getMonsters();
            }
        } catch (Exception e) {
            System.err.println("Error importing YAML data: " + e.getMessage());
        }
        return nextHandler != null ? nextHandler.importData(filePath) : null;
    }

    @Override
    public void exportData(String filePath, List<Monster> monsters) {
        try {
            if (filePath.endsWith(".yaml")) {
                DumperOptions options = new DumperOptions();
                options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                Yaml yaml = new Yaml(options);
                FileWriter writer = new FileWriter(filePath);
                MonsterListWrapper wrapper = new MonsterListWrapper();
                wrapper.setMonsters(monsters);
                yaml.dump(wrapper, writer);
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