package com.kidtask.ui.components;

import com.kidtask.ui.theme.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PrimaryButton extends JButton {

    private boolean hover = false;

    public PrimaryButton(String text) {
        super(text);
        setFont(Theme.H2);
        setForeground(Color.WHITE);
        setBorder(new EmptyBorder(12, 16, 12, 16));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
            @Override public void mouseExited(MouseEvent e) { hover = false; repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // shadow
        g2.setColor(new Color(0,0,0,90));
        g2.fill(new RoundRectangle2D.Double(4, 5, w - 8, h - 8, 18, 18));

        // gradient button
        Color a = hover ? Theme.PRIMARY_HOVER : Theme.PRIMARY;
        Color b = hover ? new Color(0x6AA6FF) : new Color(0x5A9BFF);

        g2.setPaint(new GradientPaint(0, 0, a, w, h, b));
        g2.fill(new RoundRectangle2D.Double(3, 3, w - 6, h - 6, 18, 18));

        // subtle highlight
        g2.setColor(new Color(255,255,255,40));
        g2.draw(new RoundRectangle2D.Double(3, 3, w - 6, h - 6, 18, 18));

        g2.dispose();
        super.paintComponent(g);
    }
}
