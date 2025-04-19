package com.mycompany.lb3;

import java.util.List;

public class ChainOfResponsibility {
    private FileHandler firstHandler;

    public ChainOfResponsibility() {
        FileHandler jsonHandler = new JsonHandler();
        FileHandler xmlHandler = new XmlHandler();
        FileHandler yamlHandler = new YamlHandler();

        jsonHandler.setNextHandler(xmlHandler);
        xmlHandler.setNextHandler(yamlHandler);

        this.firstHandler = jsonHandler;
    }

    public List<Monster> importData(String filePath) {
        return firstHandler.importData(filePath);
    }

    public void exportData(String filePath, List<Monster> monsters) {
        firstHandler.exportData(filePath, monsters);
    }
}