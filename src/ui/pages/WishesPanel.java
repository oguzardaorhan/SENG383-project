package ui.pages;

import data.DataManager;
import model.Wish;
import ui.style.StyleUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class WishesPanel extends JPanel {
    private DefaultTableModel tableModel;

    public WishesPanel() {
        setLayout(new BorderLayout());
        setBackground(StyleUtils.BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // --- Header ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(StyleUtils.BG_COLOR);

        JLabel titleLabel = new JLabel("Wishes");
        titleLabel.setFont(StyleUtils.FONT_HEADER);

        JButton addWishBtn = new JButton("+ Add Wish");
        addWishBtn.setBackground(StyleUtils.PRIMARY_BLUE);
        addWishBtn.setForeground(Color.WHITE);

        addWishBtn.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            AddWishDialog dialog = new AddWishDialog(topFrame);
            dialog.setVisible(true);
            if (dialog.isWishAdded()) refreshTableData();
        });

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(addWishBtn, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // --- Table ---
        String[] columns = {"Wish Title", "Type", "Description", "Required Level", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(40);
        table.getTableHeader().setFont(StyleUtils.FONT_BOLD);

        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshTableData();
    }

    private void refreshTableData() {
        tableModel.setRowCount(0);
        List<Wish> wishes = DataManager.getInstance().getWishes();
        for (Wish w : wishes) {
            tableModel.addRow(new Object[]{
                    w.getTitle(), w.getType(), w.getDescription(),
                    "Level " + w.getRequiredLevel(), w.getStatus()
            });
        }
    }
}
