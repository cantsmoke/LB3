/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lb3.GUI;

import com.mycompany.lb3.ChainOfResponsibility;
import com.mycompany.lb3.Monster;
import com.mycompany.lb3.MonsterStorage;
import com.mycompany.lb3.Recipe;
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
    private JTextArea monsterDetailsArea;
    private final ChainOfResponsibility fileHandlerChain;

    public MainFrame() {
        setTitle("Monster Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Инициализация цепочки обязанностей
        fileHandlerChain = new ChainOfResponsibility();

        // Создание панелей
        JPanel topPanel = createTopPanel();
        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();
        JPanel bottomPanel = createBottomPanel();

        // Добавление панелей в окно
        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Настройка одинаковой ширины для левой и правой панелей
        leftPanel.setPreferredSize(new Dimension(300, getHeight()));
        rightPanel.setPreferredSize(new Dimension(300, getHeight()));

        // Инициализация JTree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Monsters");
        treeModel = new DefaultTreeModel(root);
        monsterTree = new JTree(treeModel);
        monsterTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) monsterTree.getLastSelectedPathComponent();
            if (selectedNode != null && selectedNode.getUserObject() instanceof Monster) {
                displayMonsterDetails((Monster) selectedNode.getUserObject());
            }
        });

        // Добавление JTree в левую панель
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
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Информация о монстре"));

        monsterDetailsArea = new JTextArea();
        monsterDetailsArea.setEditable(false);
        JScrollPane detailsScrollPane = new JScrollPane(monsterDetailsArea);

        panel.add(detailsScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();
        JButton exportButton = new JButton("Экспорт данных");

        exportButton.addActionListener(this::exportData);

        panel.add(exportButton);
        return panel;
    }

    private String chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Поддерживаемые форматы", "json", "xml", "yaml", "yml"));
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    private void importData() {
        String filePath = chooseFile();
        if (filePath != null) {
            List<Monster> importedMonsters = fileHandlerChain.importData(filePath);
            if (importedMonsters != null) {
                 for (Monster monster : importedMonsters) {
                    monster.setSource(filePath);
                }
                monsterStorage.addMonsterCollection(importedMonsters);
                updateMonsterTree();
            }
        }
    }

    private void exportData(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Поддерживаемые форматы", "json", "xml", "yaml", "yml"));
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            fileHandlerChain.exportData(filePath, monsterStorage.getAllMonsters());
        }
    }

    private void updateMonsterTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Monsters");
        for (List<Monster> collection : monsterStorage.getMonsterCollections()) {
            DefaultMutableTreeNode collectionNode = new DefaultMutableTreeNode("Collection");
            for (Monster monster : collection) {
                collectionNode.add(new DefaultMutableTreeNode(monster));
            }
            root.add(collectionNode);
        }
        treeModel.setRoot(root);
    }

    private void displayMonsterDetails(Monster monster) {
        StringBuilder details = new StringBuilder();

        // Основная информация
        details.append("Имя: ").append(monster.getName()).append("\n");
        details.append("Описание: ").append(monster.getDescription()).append("\n");
        details.append("Уровень опасности: ").append(monster.getDangerLevel()).append("\n");
        details.append("Место обитания: ").append(monster.getHabitat()).append("\n");
        details.append("Первое упоминание: ").append(monster.getFirstMention()).append("\n");

        // Уязвимости и иммунитеты
        details.append("Уязвимость: ").append(monster.getVulnerabilities()).append("\n");
        if (monster.getImmunities() != null && !monster.getImmunities().isEmpty()) {
            details.append("Иммунитеты: ").append(String.join(", ", monster.getImmunities())).append("\n");
        }

        // Время активности
        details.append("Активность: ").append(monster.getActiveTime()).append("\n");

        // Размеры
        // Рост
        details.append("Рост: ");
        if (monster.getHeight() != null && !monster.getHeight().isEmpty()) {
            try {
                // Пробуем преобразовать в число
                double heightValue = Integer.parseInt(monster.getHeight());
                details.append(heightValue).append(" м");
            } catch (NumberFormatException e) {
                // Если не число, выводим как есть
                details.append(monster.getHeight());
            }
        } else {
            details.append("неизвестно");
        }
        details.append("\n");

        // Вес
        details.append("Вес: ");
        if (monster.getWeight() != null && !monster.getWeight().isEmpty()) {
            try {
                // Пробуем преобразовать в число
                double weightValue = Integer.parseInt(monster.getWeight());
                details.append(weightValue).append(" кг");
            } catch (NumberFormatException e) {
                // Если не число, выводим как есть
                details.append(monster.getWeight());
            }
        } else {
            details.append("неизвестно");
        }
        details.append("\n");

            // Рецепт
            Recipe recipe = monster.getRecipe();
            if (recipe != null) {
                details.append("Тип рецепта: ").append(recipe.getType()).append("\n");
                details.append("Время приготовления: ").append(recipe.getBrewingTime()).append(" мин\n");
                details.append("Эффективность: ").append(recipe.getEffectiveness()).append("\n");
                details.append("Ингредиенты:").append(recipe.getIngredients()).append("\n");

            // Источник информации
            details.append("Источник информации: ").append(monster.getSource()).append("\n");

            // Обновление текстового поля
            monsterDetailsArea.setText(details.toString());
        }
    }
}