package com.mycompany.lb3;

import com.mycompany.lb3.GUI.MainFrame;
import javax.swing.SwingUtilities;

public class LB3 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
