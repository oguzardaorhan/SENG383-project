package com.kidtask.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SidebarNavButton extends JButton {

    private boolean hover = false;
    private boolean active = false;
    private int radius = 12;

    public SidebarNavButton(String text) {
        super(text);
        setOpaque(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setHorizontalAlignment(SwingConstants.LEFT);
        setFont(new Font("SansSerif", Font.BOLD, 13));
        setForeground(new Color(235, 240, 255, 210));
        setMargin(new Insets(10, 12, 10, 12));

        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
            @Override public void mouseExited(MouseEvent e) { hover = false; repaint(); }
        });
    }

    public void setActive(boolean active) {
        this.active = active;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // background
        Color bg = new Color(255, 255, 255, 8);
        if (hover) bg = new Color(255, 255, 255, 14);
        if (active) bg = new Color(108, 99, 255, 40);

        g2.setColor(bg);
        g2.fillRoundRect(0, 0, w, h, radius, radius);

        // active glow bar
        if (active) {
            g2.setColor(new Color(108, 99, 255, 200));
            g2.fillRoundRect(0, 6, 4, h - 12, 6, 6);
        }

        super.paintComponent(g2);
        g2.dispose();
    }
}
