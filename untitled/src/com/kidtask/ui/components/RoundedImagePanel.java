package com.kidtask.ui.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class RoundedImagePanel extends JComponent {

    public enum FitMode { CONTAIN, COVER }

    private BufferedImage image;
    private int radius = 18;
    private FitMode fitMode = FitMode.COVER;

    public RoundedImagePanel() {
        setOpaque(false);
    }

    public RoundedImagePanel(String resourcePath) {
        this();
        setImageFromResource(resourcePath);
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    public void setFitMode(FitMode fitMode) {
        this.fitMode = fitMode;
        repaint();
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    public void setImageFromResource(String resourcePath) {
        try {
            // örn: "/images/login_hero.png"
            URL url = getClass().getResource(resourcePath);
            if (url == null) throw new IllegalArgumentException("Resource bulunamadı: " + resourcePath);
            this.image = ImageIO.read(url);
            repaint();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int w = getWidth();
        int h = getHeight();

        // clip: rounded rect
        Shape clip = new RoundRectangle2D.Float(0, 0, w, h, radius, radius);
        g2.setClip(clip);

        // background (image yoksa)
        g2.setColor(new Color(255, 255, 255, 10));
        g2.fillRect(0, 0, w, h);

        if (image != null) {
            int iw = image.getWidth();
            int ih = image.getHeight();

            double sx = (double) w / iw;
            double sy = (double) h / ih;

            double scale = (fitMode == FitMode.COVER) ? Math.max(sx, sy) : Math.min(sx, sy);

            int dw = (int) Math.round(iw * scale);
            int dh = (int) Math.round(ih * scale);

            int x = (w - dw) / 2;
            int y = (h - dh) / 2;

            g2.drawImage(image, x, y, dw, dh, null);
        }

        // border
        g2.setClip(null);
        g2.setColor(new Color(255, 255, 255, 28));
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(0, 0, w - 1, h - 1, radius, radius);

        g2.dispose();
    }
}
