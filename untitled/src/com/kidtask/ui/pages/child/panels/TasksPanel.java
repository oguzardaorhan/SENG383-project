package com.kidtask.ui.pages.child.panels;

import com.kidtask.model.Task;
import com.kidtask.model.TaskStatus;
import com.kidtask.model.User;
import com.kidtask.store.AppStore;
import com.kidtask.ui.pages.child.ChildDashboardStyles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TasksPanel extends JPanel {

    private final User user;
    private final Runnable onProgressChanged;
    private final JPanel listPanel = new JPanel();

    public TasksPanel(User user, Runnable onProgressChanged) {
        this.user = user;
        this.onProgressChanged = onProgressChanged;

        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel title = new JLabel(" Tasks");
        title.setFont(ChildDashboardStyles.title());
        title.setForeground(ChildDashboardStyles.TEXT);

        JLabel subtitle = new JLabel("Durumunu güncelle: Bekliyor / Yapılıyor / Yapıldı / Reddedildi");
        subtitle.setFont(ChildDashboardStyles.body());
        subtitle.setForeground(ChildDashboardStyles.MUTED);

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(title);
        header.add(Box.createVerticalStrut(6));
        header.add(subtitle);

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

    // ✅ Child sekmeye her geldiğinde çağırmak için public yaptım
    public void refresh() {
        listPanel.removeAll();

        var tasks = AppStore.getTasks();
        if (tasks.isEmpty()) {
            JLabel empty = new JLabel("Henüz görev yok. Parent/Öğretmen görev ekleyince burada göreceksin.");
            empty.setFont(ChildDashboardStyles.body());
            empty.setForeground(ChildDashboardStyles.MUTED);
            empty.setBorder(new EmptyBorder(24, 0, 0, 0));
            listPanel.add(empty);
        } else {
            for (Task t : tasks) {
                listPanel.add(taskCard(t));
                listPanel.add(Box.createVerticalStrut(12));
            }
        }

        revalidate();
        repaint();
    }

    private JPanel taskCard(Task task) {
        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setBackground(new Color(255, 255, 255, 12));
        card.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(task.getTitle());
        title.setFont(ChildDashboardStyles.menu());
        title.setForeground(ChildDashboardStyles.TEXT);

        String rewardTag = task.isRewarded() ? " • ödül verildi" : "";
        JLabel meta = new JLabel(task.getType() + " • " + task.getPoints() + " puan" + rewardTag);
        meta.setFont(ChildDashboardStyles.body());
        meta.setForeground(ChildDashboardStyles.MUTED);

        info.add(title);
        info.add(Box.createVerticalStrut(6));
        info.add(meta);

        JComboBox<TaskStatus> status = new JComboBox<>(TaskStatus.values());
        status.setSelectedItem(task.getStatus());
        status.setFont(ChildDashboardStyles.body());
        status.setFocusable(false);

        status.addActionListener(e -> {
            TaskStatus newStatus = (TaskStatus) status.getSelectedItem();
            task.setStatus(newStatus);

            if (newStatus == TaskStatus.DONE && !task.isRewarded()) {
                user.addPoints(task.getPoints());
                task.markRewarded();
                onProgressChanged.run();
                refresh(); // meta “ödül verildi” güncellensin
            }
        });

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setOpaque(false);
        right.add(status);

        card.add(info, BorderLayout.CENTER);
        card.add(right, BorderLayout.EAST);
        return card;
    }
}
