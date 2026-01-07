package ui.pages;

import data.DataManager;
import model.Task;
import ui.style.StyleUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TasksPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public TasksPanel() {
        setLayout(new BorderLayout());
        setBackground(StyleUtils.BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // --- 1. ÜST KISIM (Header + Buton) ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(StyleUtils.BG_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("Tasks");
        titleLabel.setFont(StyleUtils.FONT_HEADER);

        JLabel subtitleLabel = new JLabel("Manage and track your tasks");
        subtitleLabel.setForeground(Color.GRAY);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(StyleUtils.BG_COLOR);
        textPanel.add(titleLabel);
        textPanel.add(subtitleLabel);

        JButton addTaskBtn = new JButton("+ Add Task");
        addTaskBtn.setBackground(StyleUtils.PRIMARY_BLUE);
        addTaskBtn.setForeground(Color.WHITE);
        addTaskBtn.setFocusPainted(false);
        addTaskBtn.setFont(StyleUtils.FONT_BOLD);

        // Butona basınca AddTaskDialog açılır
        addTaskBtn.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            AddTaskDialog dialog = new AddTaskDialog(topFrame);
            dialog.setVisible(true);

            // Pencere kapandığında tabloyu yenile
            if (dialog.isTaskAdded()) {
                refreshTableData();
            }
        });

        headerPanel.add(textPanel, BorderLayout.WEST);
        headerPanel.add(addTaskBtn, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // --- 2. TABLO (Liste) ---
        String[] columns = {"Title", "Description", "Due Date", "Points", "Status"};
        tableModel = new DefaultTableModel(columns, 0);

        table = new JTable(tableModel);
        table.setRowHeight(40);
        table.setFont(StyleUtils.FONT_NORMAL);
        table.getTableHeader().setFont(StyleUtils.FONT_BOLD);
        table.getTableHeader().setBackground(Color.WHITE);
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(230, 240, 255));

        // --- SAĞ TIK MENÜSÜ ---
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem completeItem = new JMenuItem("Mark as Completed");
        JMenuItem deleteItem = new JMenuItem("Delete Task");

        popupMenu.add(completeItem);
        popupMenu.add(deleteItem);

        table.setComponentPopupMenu(popupMenu);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < table.getRowCount()) {
                        table.setRowSelectionInterval(row, row);
                    }
                }
            }
        });

        // "Mark as Completed" İşlemi (GÜNCELLENDİ: Dosyaya Kaydetme Eklendi)
        completeItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Seçilen görevi bul
                Task task = DataManager.getInstance().getTasks().get(selectedRow);

                // Durumu güncelle
                task.setStatus("Completed");

                // Puanı güncelle
                int currentPoints = DataManager.getInstance().getCurrentUser().getTotalPoints();
                DataManager.getInstance().getCurrentUser().setTotalPoints(currentPoints + task.getPoints());

                // VERİLERİ DOSYAYA KAYDET
                DataManager.getInstance().saveTasks();

                refreshTableData();
                JOptionPane.showMessageDialog(this, "Task completed! Saved.");
            }
        });

        // "Delete Task" İşlemi (GÜNCELLENDİ: Dosyaya Kaydetme Eklendi)
        deleteItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                DataManager.getInstance().getTasks().remove(selectedRow);

                // Sildikten sonra dosyayı güncelle
                DataManager.getInstance().saveTasks();

                refreshTableData();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(scrollPane, BorderLayout.CENTER);

        // Başlangıç verilerini yükle
        refreshTableData();
    }

    private void refreshTableData() {
        tableModel.setRowCount(0);
        List<Task> tasks = DataManager.getInstance().getTasks();

        for (Task t : tasks) {
            Object[] row = {
                    t.getTitle(),
                    t.getDescription(),
                    t.getDueDate(),
                    t.getPoints() + " pts",
                    t.getStatus()
            };
            tableModel.addRow(row);
        }
    }
}