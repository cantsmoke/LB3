package com.mycompany.lb3;

import java.util.List;

public class ChainOfResponsibility {
    private FileHandler firstHandler;

    public ChainOfResponsibility() {
        // Создаем цепочку обработчиков
        FileHandler jsonHandler = new JsonHandler();
        FileHandler xmlHandler = new XmlHandler();
        FileHandler yamlHandler = new YamlHandler();

        // Устанавливаем порядок обработчиков
        jsonHandler.setNextHandler(xmlHandler);
        xmlHandler.setNextHandler(yamlHandler);

        // Первый обработчик в цепочке
        this.firstHandler = jsonHandler;
    }

    public List<Monster> importData(String filePath) {
        return firstHandler.importData(filePath);
    }

    public void exportData(String filePath, List<Monster> monsters) {
        firstHandler.exportData(filePath, monsters);
    }
}