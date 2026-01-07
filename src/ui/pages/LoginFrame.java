package ui.pages;

import data.DataManager;
import model.User;
import ui.MainFrame;
import ui.style.StyleUtils;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("KidTask Login");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout()); // Öğeleri ortalamak için
        getContentPane().setBackground(StyleUtils.BG_COLOR);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Başlık
        JLabel title = new JLabel("KidTask");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(StyleUtils.PRIMARY_BLUE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Giriş Yap / Rolünü Seç");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Rol Seçimi (Radio Button)
        JPanel rolePanel = new JPanel();
        rolePanel.setBackground(Color.WHITE);
        ButtonGroup group = new ButtonGroup();
        JRadioButton childBtn = new JRadioButton("Child");
        JRadioButton parentBtn = new JRadioButton("Parent");
        childBtn.setSelected(true);
        childBtn.setBackground(Color.WHITE);
        parentBtn.setBackground(Color.WHITE);
        group.add(childBtn);
        group.add(parentBtn);
        rolePanel.add(childBtn);
        rolePanel.add(parentBtn);

        // Kullanıcı Adı
        JTextField userField = new JTextField(15);
        userField.setMaximumSize(new Dimension(300, 35));

        // Buton
        JButton loginBtn = new JButton("Continue");
        loginBtn.setBackground(StyleUtils.PRIMARY_BLUE);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginBtn.addActionListener(e -> {
            String role = childBtn.isSelected() ? "Child" : "Parent";
            String username = userField.getText();
            if(username.isEmpty()) username = "User"; // Varsayılan

            // Kullanıcıyı oluştur ve kaydet
            DataManager.getInstance().setCurrentUser(new User(username, role));

            // Ana ekranı aç, bunu kapat
            new MainFrame().setVisible(true);
            this.dispose();
        });

        // Ekleme sırası
        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(20));
        panel.add(rolePanel);
        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(loginBtn);

        add(panel);
    }
}
