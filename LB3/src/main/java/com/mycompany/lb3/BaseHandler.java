package com.mycompany.lb3;

import java.util.List;

public abstract class BaseHandler implements FileHandler {
    protected FileHandler nextHandler;

    @Override
    public void setNextHandler(FileHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public List<Monster> importData(String filePath) {
        if (nextHandler != null) {
            return nextHandler.importData(filePath);
        }
        throw new UnsupportedOperationException("No handler can process the file: " + filePath);
    }

    @Override
    public void exportData(String filePath, List<Monster> monsters) {
        if (nextHandler != null) {
            nextHandler.exportData(filePath, monsters);
        } else {
            throw new UnsupportedOperationException("No handler can process the file: " + filePath);
        }
    }
}