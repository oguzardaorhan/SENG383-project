package com.kidtask.ui.pages.login;

import com.kidtask.model.Role;
import com.kidtask.model.User;
import com.kidtask.store.AppStore;
import com.kidtask.ui.components.AnimatedSegmentedControl;
import com.kidtask.ui.components.GradientPanel;
import com.kidtask.ui.components.GlassCard;
import com.kidtask.ui.components.LabeledField;
import com.kidtask.ui.components.PrimaryButton;
import com.kidtask.ui.theme.Theme;
import com.kidtask.ui.components.RoundedImagePanel;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.function.Consumer;

public class LoginPage extends JPanel {

    private final Consumer<User> onLogin;

    public LoginPage(Consumer<User> onLogin) {
        this.onLogin = onLogin;
        setLayout(new BorderLayout());

        GradientPanel root = new GradientPanel(Theme.BG_A, Theme.BG_B);
        add(root, BorderLayout.CENTER);

        GlassCard card = new GlassCard(30, Theme.CARD_FILL, Theme.CARD_BORDER);
        card.setPreferredSize(new Dimension(860, 460));
        card.setMaximumSize(new Dimension(920, 520));

        JPanel content = new JPanel(new BorderLayout(24, 0));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(28, 28, 28, 28));

        // LEFT (form)
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setAlignmentX(Component.LEFT_ALIGNMENT);

        left.setPreferredSize(new Dimension(380, 0));
        left.setMinimumSize(new Dimension(380, 0));


        JLabel title = new JLabel("KidTask");
        title.setFont(Theme.H1);
        title.setForeground(Theme.TEXT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("");
        sub.setFont(Theme.BODY);
        sub.setForeground(Theme.MUTED);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        left.add(title);
        left.add(Box.createVerticalStrut(6));
        left.add(sub);
        left.add(Box.createVerticalStrut(18));

        AnimatedSegmentedControl role = new AnimatedSegmentedControl("Kid", "Parent", "Teacher");
        role.setPreferredSize(new Dimension(320, 36));
        role.setMaximumSize(new Dimension(320, 36));
        role.setMinimumSize(new Dimension(320, 36));

        role.setAlignmentX(Component.LEFT_ALIGNMENT);
        role.setSelectedIndex(0);
        left.add(role);
        left.add(Box.createVerticalStrut(16));

        //fotoğraf
        RoundedImagePanel photo = new RoundedImagePanel("/resources/login_photo.png");
        photo.setPreferredSize(new Dimension(100, 90));
        photo.setMaximumSize(new Dimension(100, 90));
        photo.setRadius(18);
        photo.setFitMode(RoundedImagePanel.FitMode.COVER);
        photo.setBorder(new EmptyBorder(0,150,0,0));
        photo.setAlignmentX(Component.LEFT_ALIGNMENT);

        left.add(photo);


        left.add(Box.createVerticalStrut(14));

        LabeledField nameField = new LabeledField("İsim", 14);
        nameField.setPreferredSize(new Dimension(320, 36));
        nameField.setPlaceholder("Örn: Buğra");
        //nameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameField.setBorder(new EmptyBorder(0, 50, 0, 0));

        left.add(nameField);
        left.add(Box.createVerticalStrut(12));

        PrimaryButton btn = new PrimaryButton("Login →");
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(320, 40));
        btn.setPreferredSize(new Dimension(320, 40));

        left.add(btn);

        left.add(Box.createVerticalStrut(14));
        JLabel hint = new JLabel("               Daily/Weekly homework tracking");
        hint.setFont(Theme.BODY);
        hint.setForeground(new Color(200, 210, 255, 120));

        left.add(hint);

        JPanel right = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                // big glow blobs
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.22f));
                g2.setPaint(new GradientPaint(0, 0, new Color(0x6AA6FF), w, h, new Color(0x9B5CFF)));
                g2.fill(new Ellipse2D.Double(w*0.15, h*0.05, w*0.85, w*0.85));

                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.12f));
                g2.setPaint(new GradientPaint(0, h, new Color(0x2F6BFF), w, 0, new Color(0x00E5FF)));
                g2.fill(new Ellipse2D.Double(-w*0.25, h*0.45, w*0.95, w*0.95));

                g2.dispose();
            }
        };
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(new EmptyBorder(18, 18, 18, 18));



        JLabel heroText = new JLabel("<html><div style='width:380px; line-height:1.35'>"
                + "<b>Puan → Level</b> sistemiyle motivasyon artar. "
                + "Görevleri bitirdikçe puan kazan, level atla ve dileklerin kilidini aç."
                + "</div></html>");
        heroText.setFont(Theme.BODY);
        heroText.setForeground(new Color(235, 240, 255, 210));
        heroText.setAlignmentX(Component.LEFT_ALIGNMENT);

        right.add(Box.createVerticalStrut(24));
        right.add(heroText);
        right.add(Box.createVerticalStrut(18));




        content.add(left, BorderLayout.WEST);
        content.add(right, BorderLayout.CENTER);

        card.add(content);
        root.add(card, LoginStyles.center());

        // actions
        nameField.getField().addActionListener(e -> btn.doClick());
        btn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isBlank()) {
                JOptionPane.showMessageDialog(this, "Lütfen isim gir.", "Eksik Bilgi", JOptionPane.WARNING_MESSAGE);
                nameField.getField().requestFocusInWindow();
                return;
            }

            int idx = role.getSelectedIndex();
            Role r = (idx == 0) ? Role.CHILD : (idx == 1 ? Role.PARENT : Role.TEACHER);

            User u = AppStore.getCurrentUser();

            // aynı kullanıcıysa mevcut state'i (puan vs) koru
            if (u == null || !u.name().equalsIgnoreCase(name) || u.role() != r) {
                u = new User(r, name);
                AppStore.setCurrentUser(u);   // ✅ DOĞRUSU BU
            } else {
                AppStore.setCurrentUser(u);   // (opsiyonel) zaten setli ama netlik için kalsın
            }

            onLogin.accept(u);
        });

    }
}
