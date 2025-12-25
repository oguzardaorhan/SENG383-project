package com.kidtask.ui.components;

import com.kidtask.ui.theme.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.function.Consumer;

public class AnimatedSegmentedControl extends JComponent {

    private final String[] items;
    private int selectedIndex = 0;

    private float thumbX = 0f;        // animasyonlu “seçili” pill pozisyonu
    private float targetThumbX = 0f;

    private Consumer<Integer> onChange;
    private  Timer anim;

    public AnimatedSegmentedControl(String... items) {
        this.items = items;

        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(0, 44));

        anim = new Timer(12, e -> {
            float dx = targetThumbX - thumbX;
            if (Math.abs(dx) < 0.5f) {
                thumbX = targetThumbX;
                anim.stop();
            } else {
                thumbX += dx * 0.18f; // easing
            }
            repaint();
        });

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mousePressed(java.awt.event.MouseEvent e) {
                int idx = hitIndex(e.getX());
                if (idx != -1) setSelectedIndex(idx);
            }
        });
    }

    public void setOnChange(Consumer<Integer> onChange) { this.onChange = onChange; }

    public int getSelectedIndex() { return selectedIndex; }

    public void setSelectedIndex(int idx) {
        if (idx < 0 || idx >= items.length) return;
        selectedIndex = idx;
        targetThumbX = thumbTargetFor(idx);
        if (!anim.isRunning()) anim.start();
        if (onChange != null) onChange.accept(selectedIndex);
        repaint();
    }

    private int hitIndex(int x) {
        int w = getWidth();
        int h = getHeight();
        int pad = 6;
        int innerW = w - pad * 2;
        int itemW = innerW / items.length;
        if (x < pad || x > w - pad) return -1;
        int idx = (x - pad) / itemW;
        if (idx >= items.length) idx = items.length - 1;
        return idx;
    }

    private float thumbTargetFor(int idx) {
        int w = getWidth();
        int pad = 6;
        int innerW = w - pad * 2;
        int itemW = innerW / items.length;
        return pad + (float) idx * itemW;
    }

    @Override public void doLayout() {
        super.doLayout();
        // ilk layout’ta thumb doğru yere “snap” olsun
        if (getWidth() > 0) {
            targetThumbX = thumbTargetFor(selectedIndex);
            if (!anim.isRunning()) thumbX = targetThumbX;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        int pad = 6;
        int innerX = pad;
        int innerY = pad;
        int innerW = w - pad * 2;
        int innerH = h - pad * 2;

        // container
        g2.setColor(new Color(255,255,255,18));
        g2.fill(new RoundRectangle2D.Double(innerX, innerY, innerW, innerH, 18, 18));
        g2.setColor(new Color(120,140,255,55));
        g2.draw(new RoundRectangle2D.Double(innerX, innerY, innerW, innerH, 18, 18));

        // thumb (animasyonlu seçili pill)
        int itemW = innerW / items.length;
        float tx = thumbX;
        g2.setPaint(new GradientPaint(0, 0, Theme.PRIMARY, w, h, new Color(0x6AA6FF)));
        g2.fill(new RoundRectangle2D.Double(tx, innerY, itemW, innerH, 16, 16));

        // text
        g2.setFont(Theme.BODY);
        FontMetrics fm = g2.getFontMetrics();

        for (int i = 0; i < items.length; i++) {
            int x = innerX + i * itemW;
            String s = items[i];

            boolean active = (i == selectedIndex);
            g2.setColor(active ? Color.WHITE : Theme.TEXT);

            int tw = fm.stringWidth(s);
            int txText = x + (itemW - tw) / 2;
            int tyText = innerY + (innerH - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(s, txText, tyText);
        }

        g2.dispose();
    }
}
