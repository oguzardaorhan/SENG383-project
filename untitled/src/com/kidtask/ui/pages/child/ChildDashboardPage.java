package com.kidtask.ui.pages.child;

import com.kidtask.model.User;
import com.kidtask.ui.pages.child.panels.TasksPanel;
import com.kidtask.ui.pages.child.panels.WishesPanel;
import com.kidtask.ui.components.SidebarNavButton;


import javax.swing.*;
import java.awt.*;

public class ChildDashboardPage extends JPanel {

    private final JProgressBar levelBar = new JProgressBar();
    private final JLabel levelLabel = new JLabel();

    public ChildDashboardPage(User user, Runnable onLogout) {
        setLayout(new BorderLayout());
        setBackground(ChildDashboardStyles.BG);

        // SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(ChildDashboardStyles.SIDEBAR);
        sidebar.setBorder(ChildDashboardStyles.sidebarPadding());
        sidebar.setPreferredSize(new Dimension(220, 0));

        JLabel logo = new JLabel("KidTask");
        logo.setFont(ChildDashboardStyles.title());
        logo.setForeground(ChildDashboardStyles.TEXT);

        JLabel userLabel = new JLabel("👦 " + user.name());
        userLabel.setFont(ChildDashboardStyles.body());
        userLabel.setForeground(ChildDashboardStyles.MUTED);

        SidebarNavButton wishesBtn =  new SidebarNavButton("🎁 Wishes");
        SidebarNavButton tasksBtn = new SidebarNavButton("✅ Tasks");

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(userLabel);
        sidebar.add(Box.createVerticalStrut(24));
        sidebar.add(wishesBtn);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(tasksBtn);
        sidebar.add(Box.createVerticalGlue());

        SidebarNavButton logout =  new SidebarNavButton("⏻ Çıkış");
        logout.setForeground(new Color(255, 170, 170, 220));
        logout.addActionListener(e -> onLogout.run());
        sidebar.add(logout);

        // CONTENT
        CardLayout contentLayout = new CardLayout();
        JPanel contentPanel = new JPanel(contentLayout);
        contentPanel.setOpaque(false);

        WishesPanel wishesPanel = new WishesPanel(user);
        TasksPanel tasksPanel = new TasksPanel(user, () -> updateLevel(user));

        contentPanel.add(wishesPanel, "wishes");
        contentPanel.add(tasksPanel, "tasks");

        wishesBtn.addActionListener(e -> {
            wishesBtn.setActive(true);
            tasksBtn.setActive(false);
            contentLayout.show(contentPanel, "wishes");
        });

        tasksBtn.addActionListener(e -> {
            tasksPanel.refresh();
            wishesBtn.setActive(false);
            tasksBtn.setActive(true);
            contentLayout.show(contentPanel, "tasks");
        });
        wishesBtn.setActive(true);
        tasksBtn.setActive(false);


        // TOP BAR (LEVEL)
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(18, 24, 12, 24));

        levelLabel.setFont(ChildDashboardStyles.menu());
        levelLabel.setForeground(ChildDashboardStyles.TEXT);

        levelBar.setMinimum(0);
        levelBar.setMaximum(50);
        levelBar.setForeground(ChildDashboardStyles.ACCENT);
        levelBar.setBorderPainted(false);
        levelBar.setPreferredSize(new Dimension(220, 10));

        topBar.add(levelLabel, BorderLayout.WEST);
        topBar.add(levelBar, BorderLayout.EAST);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(topBar, BorderLayout.NORTH);
        wrapper.add(contentPanel, BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);
        add(wrapper, BorderLayout.CENTER);

        updateLevel(user);
        contentLayout.show(contentPanel, "wishes");
    }

    private JButton sidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(ChildDashboardStyles.menu());
        btn.setForeground(ChildDashboardStyles.TEXT);
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void updateLevel(User user) {
        int current = user.getPoints();
        int min = user.levelMin();
        int max = user.levelMax();

        levelLabel.setText("⭐ Level " + user.getLevel() + " • " + current + " puan");
        levelBar.setValue(current - min);
        levelBar.setMaximum(max - min);
    }
}
