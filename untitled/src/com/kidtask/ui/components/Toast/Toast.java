package com.kidtask.ui.components;

import javax.swing.*;
import java.awt.*;

public class Toast extends JWindow {

    public static void show(Component parent, String message) {
        Window w = SwingUtilities.getWindowAncestor(parent);
        if (w == null) return;
        Toast t = new Toast(w, message);
        t.setVisible(true);
    }

    private Toast(Window owner, String message) {
        super(owner);
        setAlwaysOnTop(true);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 46, 230));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JLabel label = new JLabel(message);
        label.setForeground(new Color(235, 240, 255));
        label.setFont(new Font("SansSerif", Font.BOLD, 13));

        panel.add(label);
        add(panel);
        pack();

        Point p = owner.getLocationOnScreen();
        setLocation(
                p.x + owner.getWidth() - getWidth() - 24,
                p.y + owner.getHeight() - getHeight() - 24
        );

        Timer timer = new Timer(2200, e -> dispose());
        timer.setRepeats(false);
        timer.start();
    }
}
