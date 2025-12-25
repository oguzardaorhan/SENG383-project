package com.kidtask.ui.pages.child.panels;

import com.kidtask.model.User;
import com.kidtask.model.Wish;
import com.kidtask.store.AppStore;
import com.kidtask.ui.components.FormDialog;
import com.kidtask.ui.components.Toast;
import com.kidtask.ui.pages.child.ChildDashboardStyles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WishesPanel extends JPanel {

    private final User user;
    private final JPanel listPanel = new JPanel();

    public WishesPanel(User user) {
        this.user = user;

        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel title = new JLabel("🎁 Dileklerim");
        title.setFont(ChildDashboardStyles.title());
        title.setForeground(ChildDashboardStyles.TEXT);

        JButton addBtn = new JButton("+ Yeni Dilek");
        addBtn.setFont(ChildDashboardStyles.menu());
        addBtn.setForeground(ChildDashboardStyles.TEXT);
        addBtn.setFocusPainted(false);
        addBtn.setBorderPainted(false);
        addBtn.setContentAreaFilled(false);
        addBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addBtn.addActionListener(e -> openAddWishDialog());

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(title, BorderLayout.WEST);
        header.add(addBtn, BorderLayout.EAST);

        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        add(header, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        refresh();
    }

    private void openAddWishDialog() {
        JTextField name = new JTextField();
        JTextField type = new JTextField();

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        form.add(label("Dilek adı"));
        form.add(name);
        form.add(Box.createVerticalStrut(8));

        form.add(label("Tür"));
        form.add(type);

        FormDialog dialog = new FormDialog(
                SwingUtilities.getWindowAncestor(this),
                "Yeni Dilek",
                form,
                () -> {
                    String n = name.getText().trim();
                    String t = type.getText().trim();

                    if (n.isBlank() || t.isBlank()) {
                        Toast.show(this, "Alanlar boş olamaz");
                        return;
                    }

                    AppStore.addWish(new Wish(n, t, 0, 0, 0));
                    Toast.show(this, "Dilek eklendi 🎁");
                    refresh();
                }
        );

        dialog.setVisible(true);
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(ChildDashboardStyles.MUTED);
        l.setBorder(new EmptyBorder(4, 0, 4, 0));
        return l;
    }

    private void refresh() {
        listPanel.removeAll();

        var wishes = AppStore.getWishes();
        if (wishes.isEmpty()) {
            JLabel empty = new JLabel("Henüz dilek yok. + Yeni Dilek ekleyebilirsin.");
            empty.setFont(ChildDashboardStyles.body());
            empty.setForeground(ChildDashboardStyles.MUTED);
            empty.setBorder(new EmptyBorder(24, 0, 0, 0));
            listPanel.add(empty);
        } else {
            for (Wish wish : wishes) {
                listPanel.add(wishCard(wish));
                listPanel.add(Box.createVerticalStrut(14));
            }
        }

        revalidate();
        repaint();
    }

    private JPanel wishCard(Wish wish) {
        boolean hasReq = wish.getRequiredLevel() > 0 || wish.getRequiredPoints() > 0;

        boolean unlocked = !hasReq || (
                wish.isApproved()
                        && user.getLevel() >= wish.getRequiredLevel()
                        && user.getPoints() >= wish.getRequiredPoints()
        );

        Color bg;
        String badge;

        if (!wish.isApproved()) {
            bg = new Color(255, 200, 90, 35);
            badge = "⏳ BEKLİYOR";
        } else if (unlocked) {
            bg = new Color(90, 255, 160, 40);
            badge = "AÇIK";
        } else {
            bg = new Color(255, 255, 255, 14);
            badge = "🔒 KİLİTLİ";
        }

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bg);
        card.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel t = new JLabel("• " + wish.getTitle());
        t.setFont(ChildDashboardStyles.menu());
        t.setForeground(ChildDashboardStyles.TEXT);

        JLabel meta = new JLabel(
                hasReq
                        ? "Gerekli: Level " + wish.getRequiredLevel() + " • " + wish.getRequiredPoints() + " puan"
                        : "Parent/Öğretmen şart belirleyecek"
        );
        meta.setFont(ChildDashboardStyles.body());
        meta.setForeground(ChildDashboardStyles.MUTED);

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(t);
        left.add(Box.createVerticalStrut(6));
        left.add(meta);

        JLabel badgeLabel = new JLabel(badge);
        badgeLabel.setFont(ChildDashboardStyles.menu());
        badgeLabel.setForeground(ChildDashboardStyles.TEXT);

        card.add(left, BorderLayout.WEST);
        card.add(badgeLabel, BorderLayout.EAST);

        return card;
    }
}
