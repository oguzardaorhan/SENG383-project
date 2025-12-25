package com.kidtask.app;

import com.kidtask.model.Role;
import com.kidtask.model.User;
import com.kidtask.ui.pages.child.ChildDashboardPage;
import com.kidtask.ui.pages.login.LoginPage;
import com.kidtask.ui.pages.parent.ParentDashboardPage;
import com.kidtask.ui.pages.teacher.TeacherDashboardPage;
import com.kidtask.ui.theme.Theme;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {

    private final CardLayout layout = new CardLayout();
    private final JPanel root = new JPanel(layout);

    public AppFrame() {
        super("KidTask");

        Theme.install();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 680));
        setLocationRelativeTo(null);

        // Pages
        root.add(new LoginPage(this::onLogin), "login");
        // Dashboard sayfalarını login sonrası ekliyoruz (user bilgisini taşıyabilmek için)

        setContentPane(root);
        layout.show(root, "login");
    }

    private void onLogin(User user) {
        // ✅ Popup yok. Direkt ana ekrana geç.
        showDashboard(user);
    }

    private void showDashboard(User user) {
        String key;

        if (user.role() == Role.CHILD) {
            key = "child";
            root.add(new ChildDashboardPage(user, this::logout), key);
        } else if (user.role() == Role.PARENT) {
            key = "parent";
            root.add(new ParentDashboardPage(user, this::logout), key);
        } else {
            key = "teacher";
            root.add(new TeacherDashboardPage(user, this::logout), key);
        }

        layout.show(root, key);
        revalidate();
        repaint();
    }

    private void logout() {
        layout.show(root, "login");
        revalidate();
        repaint();
    }
}
