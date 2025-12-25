package com.kidtask.ui.components;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {

    private final Color topLeft;
    private final Color bottomRight;

    public GradientPanel(Color topLeft, Color bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        setOpaque(false);
        setLayout(new GridBagLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        GradientPaint gp = new GradientPaint(0, 0, topLeft, w, h, bottomRight);
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        // Hafif vignette
        g2.setPaint(new GradientPaint(0, 0, new Color(0,0,0,0), 0, h, new Color(0,0,0,70)));
        g2.fillRect(0, 0, w, h);

        g2.dispose();
        super.paintComponent(g);
    }
}
