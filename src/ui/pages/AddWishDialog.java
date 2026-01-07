package ui.pages;

import data.DataManager;
import model.Wish;
import ui.style.StyleUtils;

import javax.swing.*;
import java.awt.*;

public class AddWishDialog extends JDialog {
    private boolean isAdded = false;

    public AddWishDialog(JFrame parent) {
        super(parent, "New Wish", true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        JTextField titleField = new JTextField();
        JTextField descField = new JTextField();

        // Wish Type (Seçmeli)
        String[] types = {"Product", "Activity"};
        JComboBox<String> typeCombo = new JComboBox<>(types);

        // Required Level (1-10 arası)
        JSpinner levelSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        formPanel.add(new JLabel("Wish Title:"));
        formPanel.add(titleField);

        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeCombo);

        formPanel.add(new JLabel("Description:"));
        formPanel.add(descField);

        formPanel.add(new JLabel("Required Level:"));
        formPanel.add(levelSpinner);

        JButton saveBtn = new JButton("Add Wish");
        saveBtn.setBackground(StyleUtils.PRIMARY_BLUE);
        saveBtn.setForeground(Color.WHITE);

        saveBtn.addActionListener(e -> {
            if (!titleField.getText().isEmpty()) {
                Wish newWish = new Wish(
                        titleField.getText(),
                        (String) typeCombo.getSelectedItem(),
                        descField.getText(),
                        (int) levelSpinner.getValue(),
                        "Pending"
                );
                DataManager.getInstance().addWish(newWish);
                isAdded = true;
                dispose();
            }
        });

        add(new JLabel("  Make a Wish!"), BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(saveBtn, BorderLayout.SOUTH);
    }

    public boolean isWishAdded() { return isAdded; }
}
