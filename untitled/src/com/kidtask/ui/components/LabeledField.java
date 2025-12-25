package com.kidtask.ui.components;

import com.kidtask.ui.theme.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class LabeledField extends JPanel {

    private final JTextField field;
    private boolean focused = false;
    private String placeholder = "";

    public LabeledField(String label, int columns) {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(4));

        JLabel l = new JLabel(label);
        l.setFont(Theme.BODY);
        l.setForeground(Theme.MUTED);
        add(l, BorderLayout.NORTH);

        field = new JTextField(columns) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // placeholder
                if (!focused && (getText() == null || getText().isBlank()) && !placeholder.isBlank()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2.setColor(new Color(200, 210, 255, 90));
                    g2.setFont(Theme.BODY);
                    Insets in = getInsets();
                    g2.drawString(placeholder, in.left + 2, getHeight()/2 + 5);
                    g2.dispose();
                }
            }
        };

        field.setFont(Theme.BODY);
        field.setForeground(Theme.TEXT);
        field.setBackground(Theme.INPUT_BG);
        field.setCaretColor(Theme.TEXT);
        field.setOpaque(true);

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.INPUT_BORDER, 1, true),
                new EmptyBorder(12, 12, 12, 12)
        ));

        // ✅ gerçek input yüksekliği
        field.setMaximumSize(new Dimension(320, 36));
        field.setPreferredSize(new Dimension(320, 36));


        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                focused = true;
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(120, 160, 255, 170), 2, true),
                        new EmptyBorder(11, 11, 11, 11)
                ));
                repaint();
            }

            @Override public void focusLost(FocusEvent e) {
                focused = false;
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Theme.INPUT_BORDER, 1, true),
                        new EmptyBorder(12, 12, 12, 12)
                ));
                repaint();
            }
        });

        add(field, BorderLayout.CENTER);
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder == null ? "" : placeholder;
        repaint();
    }

    public String getText() {
        String t = field.getText();
        return t == null ? "" : t.trim();
    }

    public JTextField getField() { return field; }
}
