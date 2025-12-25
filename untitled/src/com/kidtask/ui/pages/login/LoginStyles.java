package com.kidtask.ui.pages.login;

import com.kidtask.ui.theme.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class LoginStyles {
    private LoginStyles() {}

    public static GridBagConstraints center() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }

    public static JLabel title(String text) {
        JLabel l = new JLabel(text);
        l.setFont(Theme.H1);
        l.setForeground(Theme.TEXT);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    public static JLabel subtitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(Theme.BODY);
        l.setForeground(Theme.MUTED);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    public static JPanel roleRow(JRadioButton... buttons) {
        JPanel p = new JPanel(new GridLayout(1, buttons.length, 10, 0));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        ButtonGroup g = new ButtonGroup();
        for (JRadioButton b : buttons) {
            b.setOpaque(false);
            b.setFont(Theme.BODY);
            b.setForeground(Theme.TEXT);
            g.add(b);
            p.add(b);
        }
        return p;
    }

    public static Component gap(int h) { return Box.createVerticalStrut(h); }

    public static JPanel contentPadding(JComponent inner) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(26, 26, 26, 26));
        p.add(inner, BorderLayout.CENTER);
        return p;
    }
}
