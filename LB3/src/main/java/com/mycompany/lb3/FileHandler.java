package com.mycompany.lb3;

import java.util.List;

public interface FileHandler {
    List<Monster> importData(String filePath);
    void exportData(String filePath, List<Monster> monsters);
    void setNextHandler(FileHandler nextHandler);
}