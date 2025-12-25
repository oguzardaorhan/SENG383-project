package com.kidtask.ui.pages.teacher;

import com.kidtask.model.Task;
import com.kidtask.model.TaskStatus;
import com.kidtask.model.User;
import com.kidtask.model.Wish;
import com.kidtask.store.AppStore;
import com.kidtask.ui.components.Toast;
import com.kidtask.ui.components.FormDialog;



import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TeacherDashboardPage extends JPanel {

    private final JPanel wishList = new JPanel();

    public TeacherDashboardPage(User user, Runnable onLogout) {
        setLayout(new BorderLayout());
        setBackground(TeacherDashboardStyles.BG);

        // TOP
        JLabel title = new JLabel("Öğretmen Paneli — " + user.name());
        title.setForeground(TeacherDashboardStyles.TEXT);
        title.setFont(TeacherDashboardStyles.title());
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

        // BODY (Teacher için ayrı tablar)
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Wish Onay", wishApprovalPanel());
        tabs.addTab("Task Ekle", taskCreatePanel());

        // ileride teacher’a özel: "Sınıf Notu", "Ödev Geri Bildirimi", vs.
        tabs.addTab("Notlar (yakında)", comingSoonPanel());

        add(tabs, BorderLayout.CENTER);

        refreshWishList();
    }

    private JPanel wishApprovalPanel() {
        JPanel root = new JPanel(new BorderLayout());
        root.setOpaque(false);
        root.setBorder(TeacherDashboardStyles.pagePadding());

        JLabel hint = new JLabel("Çocuğun eklediği dilekleri onayla ve şartlarını (level/puan) belirle.");
        hint.setForeground(TeacherDashboardStyles.MUTED);
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
        root.setBorder(TeacherDashboardStyles.pagePadding());
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

        JButton add = new JButton("Görevi Ekle");
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

    private JPanel comingSoonPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(TeacherDashboardStyles.pagePadding());

        JLabel t = new JLabel("Bu sekme öğretmen için özel özelliklere ayrıldı.");
        t.setForeground(TeacherDashboardStyles.MUTED);
        t.setFont(TeacherDashboardStyles.body());

        p.add(t, BorderLayout.NORTH);
        return p;
    }

    private JLabel label(String s) {
        JLabel l = new JLabel(s);
        l.setForeground(TeacherDashboardStyles.MUTED);
        l.setBorder(new EmptyBorder(6, 0, 6, 0));
        return l;
    }

    private void refreshWishList() {
        wishList.removeAll();

        var wishes = AppStore.getWishes();
        if (wishes.isEmpty()) {
            JLabel empty = new JLabel("Henüz dilek yok.");
            empty.setForeground(TeacherDashboardStyles.MUTED);
            empty.setBorder(new EmptyBorder(12, 0, 0, 0));
            wishList.add(empty);
        } else {
            for (Wish w : wishes) {
                wishList.add(wishRow(w));
                wishList.add(Box.createVerticalStrut(10));
            }
        }

        wishList.revalidate();
        wishList.repaint();
    }

    private JPanel wishRow(Wish wish) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(new Color(255, 255, 255, 12));
        row.setBorder(new EmptyBorder(12, 12, 12, 12));

        String status = wish.isApproved()
                ? ("✅ Onaylı • L" + wish.getRequiredLevel() + " / " + wish.getRequiredPoints() + "p")
                : "⏳ Bekliyor";

        JLabel title = new JLabel(wish.getTitle() + "  (" + wish.getType() + ") — " + status);
        title.setForeground(TeacherDashboardStyles.TEXT);

        JButton edit = new JButton("Şart Belirle");
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

        form.add(label("Gerekli Level"));
        form.add(level);
        form.add(Box.createVerticalStrut(8));

        form.add(label("Gerekli Puan"));
        form.add(points);

        FormDialog dialog = new FormDialog(
                SwingUtilities.getWindowAncestor(this),
                "Wish Şartları (Teacher)",
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
                        Toast.show(this, "Wish onaylandı 🎓");
                    } catch (NumberFormatException ex) {
                        Toast.show(this, "Level / puan sayı olmalı");
                    }
                }
        );

        dialog.setVisible(true);
    }
}
