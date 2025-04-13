package com.mycompany.lb3;

import javax.swing.*;
import java.io.File;

public class FileChooserUtil {
    private final JFileChooser fileChooser;

    public FileChooserUtil(String defaultDirectory) {
        fileChooser = new JFileChooser(defaultDirectory);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Supported Files", "json", "xml", "yaml"));
    }

    public File chooseFile() {
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
}