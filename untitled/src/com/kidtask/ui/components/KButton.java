package com.kidtask.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class KButton extends JButton {

    public enum Variant { PRIMARY, GHOST, DANGER }

    private Variant variant = Variant.PRIMARY;
    private boolean hover = false;
    private boolean pressed = false;

    private int radius = 14;

    public KButton(String text, Variant variant) {
        super(text);
        this.variant = variant;

        setOpaque(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(new Font("SansSerif", Font.BOLD, 13));
        setMargin(new Insets(10, 14, 10, 14));

        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
            @Override public void mouseExited(MouseEvent e) { hover = false; pressed = false; repaint(); }
            @Override public void mousePressed(MouseEvent e) { pressed = true; repaint(); }
            @Override public void mouseReleased(MouseEvent e) { pressed = false; repaint(); }
        });
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        Color bg;
        Color fg;

        switch (variant) {
            case PRIMARY -> {
                bg = new Color(108, 99, 255, 200);
                if (hover) bg = new Color(108, 99, 255, 230);
                if (pressed) bg = new Color(90, 82, 240, 240);
                fg = new Color(245, 247, 255);
            }
            case DANGER -> {
                bg = new Color(255, 100, 100, 190);
                if (hover) bg = new Color(255, 100, 100, 220);
                if (pressed) bg = new Color(230, 80, 80, 235);
                fg = new Color(255, 245, 245);
            }
            default -> { // GHOST
                bg = hover ? new Color(255, 255, 255, 18) : new Color(255, 255, 255, 8);
                if (pressed) bg = new Color(255, 255, 255, 24);
                fg = new Color(235, 240, 255, 220);
            }
        }

        g2.setColor(bg);
        g2.fillRoundRect(0, 0, w, h, radius, radius);

        super.paintComponent(g2);
        g2.dispose();

        setForeground(fg);
    }
}
