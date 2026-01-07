package ui;

import data.DataManager;
import ui.pages.*;
import ui.style.StyleUtils;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        setTitle("KidTask - Task & Wish Management");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Sidebar (Sol Menü)
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // 2. İçerik Alanı (Sağ Taraf)
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(StyleUtils.BG_COLOR);

        // Panelleri Ekle
        contentPanel.add(new DashboardPanel(), "HOME");
        contentPanel.add(new TasksPanel(), "TASKS");
        contentPanel.add(new WishesPanel(), "WISHES");

        // --- BU SATIRI EKLEMEN GEREKİYOR ---
        contentPanel.add(new ProgressPanel(), "PROGRESS");
        // ------------------------------------

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(250, 800));
        sidebar.setBackground(Color.WHITE);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(230, 230, 230)));

        // Logo Alanı
        JLabel logoLabel = new JLabel("KidTask");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logoLabel.setForeground(StyleUtils.PRIMARY_BLUE);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 50, 30));
        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(logoLabel);

        // Menü Butonları
        sidebar.add(createMenuButton("Home", "HOME"));
        sidebar.add(createMenuButton("Tasks", "TASKS"));
        sidebar.add(createMenuButton("Wishes", "WISHES"));

        // Buradaki "PROGRESS" ismi ile yukarıda eklediğimiz isim aynı olmalı (Büyük harf duyarlı)
        sidebar.add(createMenuButton("Progress", "PROGRESS"));

        // Boşluk bırak
        sidebar.add(Box.createVerticalGlue());

        // Kullanıcı Profili (Sol alt)
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String role = DataManager.getInstance().getCurrentUser().getRole();
        String name = DataManager.getInstance().getCurrentUser().getUsername();

        JLabel userLabel = new JLabel("<html><b>" + name + "</b><br><span style='font-size:10px; color:gray'>" + role + "</span></html>");
        JLabel avatar = new JLabel(" ● ");
        avatar.setForeground(StyleUtils.PRIMARY_BLUE);
        avatar.setFont(new Font("Arial", Font.PLAIN, 24));

        profilePanel.add(avatar);
        profilePanel.add(userLabel);
        profilePanel.setMaximumSize(new Dimension(250, 80));
        profilePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(profilePanel);

        return sidebar;
    }

    private JButton createMenuButton(String text, String panelName) {
        JButton btn = new JButton("  " + text);
        btn.setFont(StyleUtils.FONT_NORMAL);
        btn.setForeground(StyleUtils.TEXT_DARK);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(250, 50));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(245, 245, 255));
                btn.setForeground(StyleUtils.PRIMARY_BLUE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(StyleUtils.TEXT_DARK);
            }
        });

        btn.addActionListener(e -> cardLayout.show(contentPanel, panelName));
        return btn;
    }
}
