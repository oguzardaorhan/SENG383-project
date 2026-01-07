package ui.pages;

import data.DataManager;
import model.User;
import ui.style.StyleUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ProgressPanel extends JPanel {
    private JLabel lvlLabel;
    private JLabel pointsLabel;
    private JProgressBar progressBar;

    public ProgressPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(StyleUtils.BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // --- Başlıklar ---
        JLabel title = new JLabel("Progress & Points");
        title.setFont(StyleUtils.FONT_HEADER);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Track your achievements and level");
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- Mavi Kart (Bileşenleri önce oluşturuyoruz) ---
        JPanel levelCard = new JPanel();
        levelCard.setLayout(null);
        levelCard.setBackground(StyleUtils.PRIMARY_BLUE);
        levelCard.setMaximumSize(new Dimension(1000, 180));
        levelCard.setPreferredSize(new Dimension(800, 180));

        lvlLabel = new JLabel("Level 1"); // Başlangıç değeri
        lvlLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lvlLabel.setForeground(Color.WHITE);
        lvlLabel.setBounds(30, 20, 200, 50);

        pointsLabel = new JLabel("0 / 100 points"); // Başlangıç değeri
        pointsLabel.setForeground(Color.WHITE);
        pointsLabel.setBounds(30, 80, 200, 20);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setBounds(30, 110, 600, 15);
        progressBar.setBackground(new Color(255, 255, 255, 100));
        progressBar.setForeground(Color.ORANGE);
        progressBar.setBorderPainted(false);

        JLabel stars = new JLabel("★ ★ ★ ☆ ☆");
        stars.setFont(new Font("Segoe UI", Font.BOLD, 30));
        stars.setForeground(Color.ORANGE);
        stars.setBounds(650, 30, 200, 40);

        levelCard.add(lvlLabel);
        levelCard.add(pointsLabel);
        levelCard.add(progressBar);
        levelCard.add(stars);
        levelCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- Rozetler ---
        JLabel badgeTitle = new JLabel("Achievements");
        badgeTitle.setFont(StyleUtils.FONT_BOLD);
        badgeTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel badgePanel = new JPanel(new GridLayout(1, 3, 20, 0));
        badgePanel.setBackground(StyleUtils.BG_COLOR);
        badgePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        badgePanel.setMaximumSize(new Dimension(1000, 120));

        badgePanel.add(createBadge("First Task", "Complete your first task", true));
        badgePanel.add(createBadge("10 Tasks Champion", "Complete 10 tasks", false));
        badgePanel.add(createBadge("Week Warrior", "5 tasks in a week", false));

        add(title);
        add(subtitle);
        add(Box.createVerticalStrut(20));
        add(levelCard);
        add(Box.createVerticalStrut(30));
        add(badgeTitle);
        add(Box.createVerticalStrut(10));
        add(badgePanel);

        // --- ÖNEMLİ KISIM: Sayfa açıldığında verileri güncelle ---
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                updateData();
            }
        });

        // İlk açılış için de çalıştır
        updateData();
    }

    // Verileri DataManager'dan çekip ekrana basan metot
    private void updateData() {
        User user = DataManager.getInstance().getCurrentUser();
        if (user != null) {
            int points = user.getTotalPoints();
            int level = (points / 100) + 1; // Her 100 puanda 1 level
            int nextLevelPoints = level * 100;

            lvlLabel.setText("Level " + level);
            pointsLabel.setText(points + " / " + nextLevelPoints + " points");

            progressBar.setMaximum(nextLevelPoints);
            progressBar.setValue(points);
        }
    }

    private JPanel createBadge(String title, String desc, boolean unlocked) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel icon = new JLabel(unlocked ? "🏆" : "🔒");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        icon.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel t = new JLabel(title);
        t.setFont(StyleUtils.FONT_BOLD);
        t.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel d = new JLabel("<html><center>" + desc + "</center></html>");
        d.setForeground(Color.GRAY);
        d.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        d.setHorizontalAlignment(SwingConstants.CENTER);

        if (unlocked) t.setForeground(new Color(255, 140, 0));
        else t.setForeground(Color.GRAY);

        p.add(icon, BorderLayout.NORTH);
        p.add(t, BorderLayout.CENTER);
        p.add(d, BorderLayout.SOUTH);
        return p;
    }
}
