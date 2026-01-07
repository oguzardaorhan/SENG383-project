package ui.pages;

import data.DataManager;
import model.User;
import ui.style.StyleUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DashboardPanel extends JPanel {
    private JLabel lblPoints;
    private JLabel lblLevel;
    private JLabel lblPending;

    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(StyleUtils.BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // --- Header ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(StyleUtils.BG_COLOR);

        JLabel title = new JLabel("Hello, " + DataManager.getInstance().getCurrentUser().getUsername() + "!");
        title.setFont(StyleUtils.FONT_HEADER);
        JLabel subtitle = new JLabel("Ready for today's tasks?");
        subtitle.setForeground(Color.GRAY);

        JPanel titleBox = new JPanel(new GridLayout(2, 1));
        titleBox.setBackground(StyleUtils.BG_COLOR);
        titleBox.add(title);
        titleBox.add(subtitle);
        header.add(titleBox, BorderLayout.WEST);

        add(header, BorderLayout.NORTH);

        // --- Cards Area ---
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        cardsPanel.setBackground(StyleUtils.BG_COLOR);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Kartları oluştur (Boş değerlerle, updateData dolduracak)
        lblPoints = new JLabel("0");
        lblLevel = new JLabel("1");
        lblPending = new JLabel("0");

        cardsPanel.add(createSummaryCard("Total Points", lblPoints, new Color(255, 240, 230)));
        cardsPanel.add(createSummaryCard("Current Level", lblLevel, new Color(230, 245, 255)));
        cardsPanel.add(createSummaryCard("Pending Tasks", lblPending, new Color(235, 255, 235)));

        add(cardsPanel, BorderLayout.CENTER);

        // --- ÖNEMLİ: Görünür olduğunda verileri güncelle ---
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                updateData();
            }
        });

        updateData(); // İlk açılış
    }

    private void updateData() {
        User user = DataManager.getInstance().getCurrentUser();
        if (user != null) {
            lblPoints.setText(String.valueOf(user.getTotalPoints()));

            // Level hesaplama mantığı: Her 100 puan 1 level
            int level = (user.getTotalPoints() / 100) + 1;
            lblLevel.setText(String.valueOf(level));

            // Bekleyen görev sayısı (Tasks listesinden sayabiliriz)
            long pendingCount = DataManager.getInstance().getTasks().stream()
                    .filter(t -> !t.getStatus().equals("Completed"))
                    .count();
            lblPending.setText(String.valueOf(pendingCount));
        }
    }

    private JPanel createSummaryCard(String title, JLabel valueLabel, Color bgColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Basit gölgelendirme efekti için trick: Border
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,0,0,0.05f), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLbl.setForeground(new Color(80, 80, 80));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        valueLabel.setForeground(Color.BLACK);

        card.add(titleLbl, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }
}
