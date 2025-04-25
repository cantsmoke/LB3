package com.mycompany.lb3.GUI;

import com.mycompany.lb3.ChainOfResponsibility;
import com.mycompany.lb3.Monster;
import com.mycompany.lb3.MonsterStorage;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

public class MainFrame extends JFrame {
    private final MonsterStorage monsterStorage = new MonsterStorage();
    private final JTree monsterTree;
    private final DefaultTreeModel treeModel;
    private final ChainOfResponsibility fileHandlerChain;
    private MonsterDetailsPanel monsterDetailsPanel;
    private static int fileOrderCounter = 0;

    public MainFrame() {
        setTitle("Monster Manager");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        fileHandlerChain = new ChainOfResponsibility();

        JPanel topPanel = createTopPanel();
        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();
        JPanel bottomPanel = createBottomPanel();

        add(topPanel, BorderLayout.NORTH); 
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        leftPanel.setPreferredSize(new Dimension(300, getHeight()));
        rightPanel.setPreferredSize(new Dimension(300, getHeight()));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Monsters");
        treeModel = new DefaultTreeModel(root);
        monsterTree = new JTree(treeModel);
        monsterTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) monsterTree.getLastSelectedPathComponent();
            if (selectedNode != null && selectedNode.getUserObject() instanceof Monster) {
                monsterDetailsPanel.displayMonsterDetails((Monster) selectedNode.getUserObject());
            }
        });

        JScrollPane treeScrollPane = new JScrollPane(monsterTree);
        leftPanel.add(treeScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        JButton importDataButton = new JButton("Импортировать данные");
        importDataButton.addActionListener(e -> importData());

        panel.add(importDataButton);
        return panel;
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Список монстров"));
        return panel;
    }

    private JPanel createRightPanel() {
        monsterDetailsPanel = new MonsterDetailsPanel(monsterStorage);
        return monsterDetailsPanel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();
        JButton exportButton = new JButton("Экспорт данных");

        exportButton.addActionListener(this::exportData);

        panel.add(exportButton);
        return panel;
    }
    
    private boolean isFileAlreadyImported(String filePath) {
        for (Monster monster : monsterStorage.getAllMonsters()) {
            if (filePath.equals(monster.getSource())) {
                return true;
            }
        }
        return false;
    }

    private String chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Поддерживаемые форматы", "json", "xml", "yaml", "yml"));
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            if (isFileAlreadyImported(filePath)) {
                JOptionPane.showMessageDialog(this, "Данные из файла '" + filePath + "' уже были импортированы.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            return filePath;
        }
        return null;
    }

    private void importData() {
        String filePath = chooseFile();
        if (filePath == null){
            return;
        }
        String fileExtension = getFileExtension(filePath);
        fileOrderCounter++;
        if (filePath != null) {
            List<Monster> importedMonsters = fileHandlerChain.importData(filePath);
            if (importedMonsters != null) {
                for (Monster monster : importedMonsters) {
                    monster.setSource(filePath);
                }
                monsterStorage.addMonsterCollection(importedMonsters);
                for (Monster monster : monsterStorage.getAllMonsters()) {
                    if (monsterStorage.getDangerLevelValueChanges().containsKey(monster.getName())){
                        monster.setDangerLevel(monsterStorage.getDangerLevelValueChanges().get(monster.getName())); 
                    }
                }
                updateMonsterTree();
            }
        }
        monsterStorage.addCollectionSources(fileOrderCounter, fileExtension);
    }
    
    private String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filePath.length() - 1) {
            return filePath.substring(dotIndex + 1).toUpperCase();
        }
        return "";
    }

    private void exportData(ActionEvent e) {
        List<List<Monster>> collections = monsterStorage.getMonsterCollections();

        String[] options = new String[collections.size()];
        for (int i = 0; i < collections.size(); i++) {
            options[i] = "Monster Collection " + (i + 1) + " " + monsterStorage.getCollectionSources().get(i + 1);
        }

        String selectedOption = (String) JOptionPane.showInputDialog(
                this,
                "Выберите коллекцию для экспорта:",
                "Выбор коллекции",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        int selectedIndex = -1;
        if (selectedOption != null) {
            selectedIndex = Integer.parseInt(selectedOption.split(" ")[2]) - 1;
        }

        if (selectedIndex >= 0 && selectedIndex < collections.size()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Поддерживаемые форматы", "json", "xml", "yaml", "yml"));
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                fileHandlerChain.exportData(filePath, collections.get(selectedIndex));
                JOptionPane.showMessageDialog(this, "Данные успешно экспортированы в: " + filePath);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Экспорт отменен. Коллекция не выбрана.");
        }
    }

    private void updateMonsterTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Monsters");
        int collectionNum = fileOrderCounter - (fileOrderCounter - 1);
        for (List<Monster> collection : monsterStorage.getMonsterCollections()) {
            DefaultMutableTreeNode collectionNode = new DefaultMutableTreeNode("Monster Collection " + collectionNum);
            collectionNum++;
            for (Monster monster : collection) {
                collectionNode.add(new DefaultMutableTreeNode(monster));
            }
            root.add(collectionNode);
        }
        treeModel.setRoot(root);
    }
}