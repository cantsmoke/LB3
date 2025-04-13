package com.mycompany.lb3;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class XmlHandler extends BaseHandler {
    @Override
    public List<Monster> importData(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && filePath.endsWith(".xml")) {
                JAXBContext context = JAXBContext.newInstance(MonsterListWrapper.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                MonsterListWrapper wrapper = (MonsterListWrapper) unmarshaller.unmarshal(file);
                return wrapper.getMonsters();
            }
        } catch (JAXBException e) {
            System.err.println("Error importing XML data: " + e.getMessage());
        }
        return nextHandler != null ? nextHandler.importData(filePath) : null;
    }

    @Override
    public void exportData(String filePath, List<Monster> monsters) {
        try {
            if (filePath.endsWith(".xml")) {
                JAXBContext context = JAXBContext.newInstance(MonsterListWrapper.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                MonsterListWrapper wrapper = new MonsterListWrapper();
                wrapper.setMonsters(monsters);
                marshaller.marshal(wrapper, new File(filePath));
                System.out.println("Data successfully exported to XML file.");
                return;
            }
        } catch (JAXBException e) {
            System.err.println("Error exporting XML data: " + e.getMessage());
        }
        if (nextHandler != null) {
            nextHandler.exportData(filePath, monsters);
        }
    }
}