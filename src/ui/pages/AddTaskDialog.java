package ui.pages;

import data.DataManager;
import model.Task;
import ui.style.StyleUtils;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class AddTaskDialog extends JDialog {
    private boolean isAdded = false;

    public AddTaskDialog(JFrame parent) {
        super(parent, "New Task", true);
        setSize(400, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Form Paneli
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        // Alanlar
        JTextField titleField = new JTextField();
        JTextField descField = new JTextField();
        JTextField dateField = new JTextField(LocalDate.now().toString()); // Örn: 2025-12-12

        // Puan alanı sadece sayı girilsin diye JSpinner
        JSpinner pointsSpinner = new JSpinner(new SpinnerNumberModel(10, 5, 1000, 5));

        formPanel.add(new JLabel("Task Title:"));
        formPanel.add(titleField);

        formPanel.add(new JLabel("Description:"));
        formPanel.add(descField);

        formPanel.add(new JLabel("Due Date (YYYY-MM-DD):"));
        formPanel.add(dateField);

        formPanel.add(new JLabel("Points:"));
        formPanel.add(pointsSpinner);

        // Butonlar
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JButton saveBtn = new JButton("Save Task");
        saveBtn.setBackground(StyleUtils.PRIMARY_BLUE);
        saveBtn.setForeground(Color.WHITE);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(Color.LIGHT_GRAY);

        saveBtn.addActionListener(e -> {
            String title = titleField.getText();
            String desc = descField.getText();
            String date = dateField.getText();
            int points = (int) pointsSpinner.getValue();

            if (!title.isEmpty()) {
                // Yeni görevi oluştur ve kaydet
                Task newTask = new Task(title, desc, date, points, "Pending");
                DataManager.getInstance().addTask(newTask);
                isAdded = true;
                dispose(); // Pencereyi kapat
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(cancelBtn);
        buttonPanel.add(saveBtn);

        add(new JLabel("  Add New Task"), BorderLayout.NORTH); // Basit başlık
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public boolean isTaskAdded() { return isAdded; }
}
