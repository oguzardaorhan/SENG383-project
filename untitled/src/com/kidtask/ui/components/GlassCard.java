package com.kidtask.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class GlassCard extends JPanel {

    private final int arc;
    private final Color fill;
    private final Color border;

    public GlassCard(int arc, Color fill, Color border) {
        this.arc = arc;
        this.fill = fill;
        this.border = border;
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // shadow
        g2.setColor(new Color(0, 0, 0, 90));
        g2.fill(new RoundRectangle2D.Double(8, 10, w - 16, h - 16, arc, arc));

        // card
        g2.setColor(fill);
        g2.fill(new RoundRectangle2D.Double(6, 6, w - 12, h - 12, arc, arc));

        // border
        g2.setColor(border);
        g2.draw(new RoundRectangle2D.Double(6, 6, w - 12, h - 12, arc, arc));

        g2.dispose();
        super.paintComponent(g);
    }
}
