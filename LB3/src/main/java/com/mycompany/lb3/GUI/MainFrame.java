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
    private final ChainOfResponsibility fileHandlerChain;
    private JLabel nameLabel, descriptionLabel, dangerLevelLabel, habitatLabel, firstMentionLabel,
              vulnerabilitiesLabel, immunitiesLabel, activeTimeLabel, heightLabel, weightLabel,
              recipeTypeLabel, brewingTimeLabel, effectivenessLabel, ingredientsLabel, sourceLabel, recipeLabel, helpLabel;
    private JTextField nameField, dangerLevelField, habitatField, firstMentionField,
                       vulnerabilitiesField, activeTimeField, heightField, weightField,
                       recipeTypeField, brewingTimeField, effectivenessField, sourceField;
    private JTextArea descriptionArea, immunitiesArea, ingredientsArea;

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
                displayMonsterDetails((Monster) selectedNode.getUserObject());
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
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Информация о монстре"));

        nameLabel = new JLabel("Имя:");
        nameField = new JTextField();
        nameField.setEditable(false);
        panel.add(nameLabel);
        panel.add(nameField);

        descriptionLabel = new JLabel("Описание:");
        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        panel.add(descriptionLabel);
        panel.add(descriptionScrollPane);

        dangerLevelLabel = new JLabel("Уровень опасности:");
        dangerLevelField = new JTextField();
        dangerLevelField.setEditable(true);
        panel.add(dangerLevelLabel);
        panel.add(dangerLevelField);

        habitatLabel = new JLabel("Место обитания:");
        habitatField = new JTextField();
        habitatField.setEditable(false);
        panel.add(habitatLabel);
        panel.add(habitatField);

        firstMentionLabel = new JLabel("Первое упоминание:");
        firstMentionField = new JTextField();
        firstMentionField.setEditable(false);
        panel.add(firstMentionLabel);
        panel.add(firstMentionField);

        vulnerabilitiesLabel = new JLabel("Уязвимость:");
        vulnerabilitiesField = new JTextField();
        vulnerabilitiesField.setEditable(false);
        panel.add(vulnerabilitiesLabel);
        panel.add(vulnerabilitiesField);

        immunitiesLabel = new JLabel("Иммунитеты:");
        immunitiesArea = new JTextArea();
        immunitiesArea.setLineWrap(true);
        immunitiesArea.setWrapStyleWord(true);
        immunitiesArea.setEditable(false);
        JScrollPane immunitiesScrollPane = new JScrollPane(immunitiesArea);
        panel.add(immunitiesLabel);
        panel.add(immunitiesScrollPane);

        activeTimeLabel = new JLabel("Активность:");
        activeTimeField = new JTextField();
        activeTimeField.setEditable(false);
        panel.add(activeTimeLabel);
        panel.add(activeTimeField);

        heightLabel = new JLabel("Рост:");
        heightField = new JTextField();
        heightField.setEditable(false);
        panel.add(heightLabel);
        panel.add(heightField);

        weightLabel = new JLabel("Вес:");
        weightField = new JTextField();
        weightField.setEditable(false);
        panel.add(weightLabel);
        panel.add(weightField);
        
        recipeLabel = new JLabel("Рецепт");
        helpLabel = new JLabel();
        panel.add(recipeLabel);
        panel.add(helpLabel);

        recipeTypeLabel = new JLabel("Тип рецепта:");
        recipeTypeField = new JTextField();
        recipeTypeField.setEditable(false);
        panel.add(recipeTypeLabel);
        panel.add(recipeTypeField);

        brewingTimeLabel = new JLabel("Время приготовления:");
        brewingTimeField = new JTextField();
        brewingTimeField.setEditable(true);
        panel.add(brewingTimeLabel);
        panel.add(brewingTimeField);

        effectivenessLabel = new JLabel("Эффективность:");
        effectivenessField = new JTextField();
        effectivenessField.setEditable(false);
        panel.add(effectivenessLabel);
        panel.add(effectivenessField);

        ingredientsLabel = new JLabel("Ингредиенты:");
        ingredientsArea = new JTextArea();
        ingredientsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);
        ingredientsArea.setEditable(false);
        JScrollPane ingredientsScrollPane = new JScrollPane(ingredientsArea);
        panel.add(ingredientsLabel);
        panel.add(ingredientsScrollPane);

        sourceLabel = new JLabel("Источник информации:");
        sourceField = new JTextField();
        sourceField.setEditable(false);
        panel.add(sourceLabel);
        panel.add(sourceField);

        return panel;
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
            DefaultMutableTreeNode collectionNode = new DefaultMutableTreeNode("Monster Collection");
            for (Monster monster : collection) {
                collectionNode.add(new DefaultMutableTreeNode(monster));
            }
            root.add(collectionNode);
        }
        treeModel.setRoot(root);
    }

    private void displayMonsterDetails(Monster monster) {
        if (monster == null) {
            clearMonsterFields();
            return;
        }

        nameField.setText(monster.getName());
        descriptionArea.setText(monster.getDescription());
        dangerLevelField.setText(String.valueOf(monster.getDangerLevel()));
        habitatField.setText(monster.getHabitat());
        firstMentionField.setText(monster.getFirstMention());
        vulnerabilitiesField.setText(monster.getVulnerabilities());

        List<String> immunities = monster.getImmunities();
        immunitiesArea.setText(immunities != null && !immunities.isEmpty() ? String.join(", ", immunities) : "нет");

        activeTimeField.setText(monster.getActiveTime());
        heightField.setText(monster.getHeight() != null ? monster.getHeight() : "неизвестно");
        weightField.setText(monster.getWeight() != null ? monster.getWeight() : "неизвестно");

        Recipe recipe = monster.getRecipe();
        if (recipe != null) {
            recipeTypeField.setText(recipe.getType());
            brewingTimeField.setText(String.valueOf(recipe.getBrewingTime()));
            effectivenessField.setText(recipe.getEffectiveness());
            ingredientsArea.setText(recipe.getIngredients());
        } else {
            recipeTypeField.setText("не указан");
            brewingTimeField.setText("не указано");
            effectivenessField.setText("не указана");
            ingredientsArea.setText("не указаны");
        }

        sourceField.setText(monster.getSource());
    }

    private void clearMonsterFields() {
        nameField.setText("");
        descriptionArea.setText("");
        dangerLevelField.setText("");
        habitatField.setText("");
        firstMentionField.setText("");
        vulnerabilitiesField.setText("");
        immunitiesArea.setText("");
        activeTimeField.setText("");
        heightField.setText("");
        weightField.setText("");
        recipeTypeField.setText("");
        brewingTimeField.setText("");
        effectivenessField.setText("");
        ingredientsArea.setText("");
        sourceField.setText("");
    }
}