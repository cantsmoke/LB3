package com.mycompany.lb3.GUI;

import com.mycompany.lb3.Monster;
import com.mycompany.lb3.Recipe;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MonsterDetailsPanel extends JPanel {
    private final JLabel nameLabel, descriptionLabel, dangerLevelLabel, habitatLabel, firstMentionLabel,
            vulnerabilitiesLabel, immunitiesLabel, activeTimeLabel, heightLabel, weightLabel,
            recipeTypeLabel, brewingTimeLabel, effectivenessLabel, ingredientsLabel, sourceLabel, recipeLabel, helpLabel;
    private final JTextField nameField, dangerLevelField, habitatField, firstMentionField,
            vulnerabilitiesField, activeTimeField, heightField, weightField,
            recipeTypeField, brewingTimeField, effectivenessField, sourceField;
    private final JTextArea descriptionArea, immunitiesArea, ingredientsArea;

    public MonsterDetailsPanel() {
        setLayout(new GridLayout(0, 2));
        setBorder(BorderFactory.createTitledBorder("Информация о монстре"));

        nameLabel = new JLabel("Имя:");
        nameField = createTextField(false);
        add(nameLabel);
        add(nameField);

        descriptionLabel = new JLabel("Описание:");
        descriptionArea = createTextArea(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        add(descriptionLabel);
        add(descriptionScrollPane);

        dangerLevelLabel = new JLabel("Уровень опасности:");
        dangerLevelField = createTextField(true);
        add(dangerLevelLabel);
        add(dangerLevelField);

        habitatLabel = new JLabel("Место обитания:");
        habitatField = createTextField(false);
        add(habitatLabel);
        add(habitatField);

        firstMentionLabel = new JLabel("Первое упоминание:");
        firstMentionField = createTextField(false);
        add(firstMentionLabel);
        add(firstMentionField);

        vulnerabilitiesLabel = new JLabel("Уязвимость:");
        vulnerabilitiesField = createTextField(false);
        add(vulnerabilitiesLabel);
        add(vulnerabilitiesField);

        immunitiesLabel = new JLabel("Иммунитеты:");
        immunitiesArea = createTextArea(false);
        JScrollPane immunitiesScrollPane = new JScrollPane(immunitiesArea);
        add(immunitiesLabel);
        add(immunitiesScrollPane);

        activeTimeLabel = new JLabel("Активность:");
        activeTimeField = createTextField(false);
        add(activeTimeLabel);
        add(activeTimeField);

        heightLabel = new JLabel("Рост:");
        heightField = createTextField(false);
        add(heightLabel);
        add(heightField);

        weightLabel = new JLabel("Вес:");
        weightField = createTextField(false);
        add(weightLabel);
        add(weightField);

        recipeLabel = new JLabel("Рецепт");
        helpLabel = new JLabel();
        add(recipeLabel);
        add(helpLabel);

        recipeTypeLabel = new JLabel("Тип рецепта:");
        recipeTypeField = createTextField(false);
        add(recipeTypeLabel);
        add(recipeTypeField);

        brewingTimeLabel = new JLabel("Время приготовления:");
        brewingTimeField = createTextField(true);
        add(brewingTimeLabel);
        add(brewingTimeField);

        effectivenessLabel = new JLabel("Эффективность:");
        effectivenessField = createTextField(false);
        add(effectivenessLabel);
        add(effectivenessField);

        ingredientsLabel = new JLabel("Ингредиенты:");
        ingredientsArea = createTextArea(false);
        JScrollPane ingredientsScrollPane = new JScrollPane(ingredientsArea);
        add(ingredientsLabel);
        add(ingredientsScrollPane);

        sourceLabel = new JLabel("Источник информации:");
        sourceField = createTextField(false);
        add(sourceLabel);
        add(sourceField);
    }

    private JTextField createTextField(boolean editable) {
        JTextField field = new JTextField();
        field.setEditable(editable);
        return field;
    }

    private JTextArea createTextArea(boolean editable) {
        JTextArea area = new JTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(editable);
        return area;
    }

    public void displayMonsterDetails(Monster monster) {
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

    public void clearMonsterFields() {
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