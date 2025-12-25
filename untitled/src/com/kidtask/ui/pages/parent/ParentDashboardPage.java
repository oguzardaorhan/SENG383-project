package com.kidtask.ui.pages.parent;

import com.kidtask.model.Task;
import com.kidtask.model.TaskStatus;
import com.kidtask.model.User;
import com.kidtask.model.Wish;
import com.kidtask.store.AppStore;
import com.kidtask.ui.components.KButton;
import com.kidtask.ui.components.Toast;
import com.kidtask.ui.components.FormDialog;



import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ParentDashboardPage extends JPanel {

    private final JPanel wishList = new JPanel();

    public ParentDashboardPage(User user, Runnable onLogout) {
        setLayout(new BorderLayout());
        setBackground(new Color(0x0B1020));

        // TOP
        JLabel title = new JLabel("Parent Paneli — " + user.name());
        title.setForeground(new Color(235, 240, 255, 220));
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setBorder(new EmptyBorder(18, 22, 10, 22));

        JButton logout = new JButton("Çıkış");
        logout.addActionListener(e -> onLogout.run());

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(title, BorderLayout.WEST);

        JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 14));
        topRight.setOpaque(false);
        topRight.add(logout);
        top.add(topRight, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        // BODY: Tabs (Wish Onay / Task Ekle)
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Wish Onay", wishApprovalPanel());
        tabs.addTab("Task Ekle", taskCreatePanel());
        add(tabs, BorderLayout.CENTER);

        refreshWishList();
    }

    private JPanel wishApprovalPanel() {
        JPanel root = new JPanel(new BorderLayout());
        root.setOpaque(false);
        root.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel hint = new JLabel("Çocuğun eklediği dilekleri onayla ve şartlarını (level/puan) belirle.");
        hint.setForeground(new Color(235, 240, 255, 160));
        hint.setBorder(new EmptyBorder(0, 0, 12, 0));

        wishList.setOpaque(false);
        wishList.setLayout(new BoxLayout(wishList, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(wishList);
        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);

        root.add(hint, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);

        return root;
    }

    private JPanel taskCreatePanel() {
        JPanel root = new JPanel();
        root.setOpaque(false);
        root.setBorder(new EmptyBorder(18, 18, 18, 18));
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));

        JTextField title = new JTextField();
        JTextField type = new JTextField();
        JTextField points = new JTextField();

        root.add(label("Görev adı"));
        root.add(title);
        root.add(Box.createVerticalStrut(10));

        root.add(label("Tür"));
        root.add(type);
        root.add(Box.createVerticalStrut(10));

        root.add(label("Puan"));
        root.add(points);
        root.add(Box.createVerticalStrut(14));

        KButton add = new KButton("Görevi Ekle", KButton.Variant.PRIMARY);

        add.addActionListener(e -> {
            try {
                String t = title.getText().trim();
                String ty = type.getText().trim();
                int p = Integer.parseInt(points.getText().trim());
                if (t.isBlank() || ty.isBlank()) {
                    JOptionPane.showMessageDialog(this, "Alanları doldur.", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                AppStore.addTask(new Task(t, ty, p, TaskStatus.TODO));
                Toast.show(this, "Görev eklendi ✔");
                title.setText("");
                type.setText("");
                points.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Puan sayı olmalı.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        root.add(add);
        return root;
    }

    private JLabel label(String s) {
        JLabel l = new JLabel(s);
        l.setForeground(new Color(235, 240, 255, 170));
        l.setBorder(new EmptyBorder(6, 0, 6, 0));
        return l;
    }

    private void refreshWishList() {
        wishList.removeAll();

        for (Wish w : AppStore.getWishes()) {
            wishList.add(wishRow(w));
            wishList.add(Box.createVerticalStrut(10));
        }

        wishList.revalidate();
        wishList.repaint();
    }

    private JPanel wishRow(Wish wish) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(new Color(255, 255, 255, 12));
        row.setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel title = new JLabel(wish.getTitle() + "  (" + wish.getType() + ")");
        title.setForeground(new Color(235, 240, 255, 220));

        KButton edit = new KButton("Şart Belirle", KButton.Variant.GHOST);
        edit.addActionListener(e -> openRequirementDialog(wish));

        row.add(title, BorderLayout.CENTER);
        row.add(edit, BorderLayout.EAST);

        return row;
    }

    private void openRequirementDialog(Wish wish) {
        JTextField level = new JTextField(String.valueOf(wish.getRequiredLevel()));
        JTextField points = new JTextField(String.valueOf(wish.getRequiredPoints()));

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        form.add(label("Gerekli Level (örn: 3)"));
        form.add(level);
        form.add(Box.createVerticalStrut(8));

        form.add(label("Gerekli Puan (örn: 150)"));
        form.add(points);

        FormDialog dialog = new FormDialog(
                SwingUtilities.getWindowAncestor(this),
                "Wish Şartları",
                form,
                () -> {
                    try {
                        int lv = Integer.parseInt(level.getText().trim());
                        int pt = Integer.parseInt(points.getText().trim());

                        if (lv < 1 || pt < 0) {
                            Toast.show(this, "Geçerli değer gir");
                            return;
                        }

                        wish.setRequiredLevel(lv);
                        wish.setRequiredPoints(pt);
                        wish.approve();

                        refreshWishList();
                        Toast.show(this, "Wish onaylandı ✅");
                    } catch (NumberFormatException ex) {
                        Toast.show(this, "Level/puan sayı olmalı");
                    }
                }
        );

        dialog.setVisible(true);
    }
}