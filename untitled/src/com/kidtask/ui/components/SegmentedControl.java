package com.kidtask.ui.components;

import com.kidtask.ui.theme.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.function.Consumer;

public class SegmentedControl extends JPanel {

    private int selectedIndex = 0;
    private Consumer<Integer> onChange;

    public SegmentedControl(String... items) {
        setOpaque(false);
        setLayout(new GridLayout(1, items.length, 10, 0));
        setBorder(new EmptyBorder(8, 8, 8, 8));

        for (int i = 0; i < items.length; i++) {
            final int idx = i;
            JButton b = new JButton(items[i]) {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    int w = getWidth();
                    int h = getHeight();
                    boolean active = (idx == selectedIndex);

                    // background
                    if (active) {
                        g2.setPaint(new GradientPaint(0, 0, Theme.PRIMARY, w, h, new Color(0x6AA6FF)));
                        g2.fill(new RoundRectangle2D.Double(0, 0, w, h, 18, 18));
                    } else {
                        g2.setColor(new Color(255, 255, 255, 10));
                        g2.fill(new RoundRectangle2D.Double(0, 0, w, h, 18, 18));
                        g2.setColor(new Color(120, 140, 255, 55));
                        g2.draw(new RoundRectangle2D.Double(0, 0, w - 1, h - 1, 18, 18));
                    }

                    // text
                    g2.setFont(Theme.BODY);
                    g2.setColor(active ? Color.WHITE : Theme.TEXT);
                    FontMetrics fm = g2.getFontMetrics();
                    int tx = (w - fm.stringWidth(getText())) / 2;
                    int ty = (h - fm.getHeight()) / 2 + fm.getAscent();
                    g2.drawString(getText(), tx, ty);

                    g2.dispose();
                }
            };

            b.setFont(Theme.BODY);
            b.setForeground(Theme.TEXT);
            b.setOpaque(false);
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.addActionListener(e -> setSelectedIndex(idx));

            add(b);
        }

        setPreferredSize(new Dimension(0, 42));
    }

    public void setOnChange(Consumer<Integer> onChange) {
        this.onChange = onChange;
    }

    public void setSelectedIndex(int idx) {
        selectedIndex = idx;
        repaint();
        if (onChange != null) onChange.accept(selectedIndex);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
}
