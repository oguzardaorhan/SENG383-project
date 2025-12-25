package com.kidtask.ui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FormDialog extends JDialog {

    public FormDialog(Window owner, String title, JComponent content, Runnable onConfirm) {
        super(owner);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBorder(new EmptyBorder(18, 18, 18, 18));
        root.setBackground(new Color(26, 28, 44));

        JLabel header = new JLabel(title);
        header.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.setForeground(new Color(235, 240, 255));

        content.setOpaque(false);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        footer.setOpaque(false);

        JButton cancel = new JButton("İptal");
        JButton save = new JButton("Kaydet");

        style(cancel, true);
        style(save, false);

        cancel.addActionListener(e -> dispose());
        save.addActionListener(e -> {
            onConfirm.run();
            dispose();
        });

        footer.add(cancel);
        footer.add(save);

        root.add(header, BorderLayout.NORTH);
        root.add(content, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setLocationRelativeTo(owner);
    }

    private void style(JButton b, boolean ghost) {
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setForeground(new Color(235, 240, 255));

        if (ghost) {
            b.setContentAreaFilled(false);
        } else {
            b.setBackground(new Color(108, 99, 255));
            b.setOpaque(true);
        }
    }
}
