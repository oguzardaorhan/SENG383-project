package com.kidtask.ui.theme;

import javax.swing.*;
import java.awt.*;

public final class Theme {
    private Theme() {}

    // Background gradient colors
    public static final Color BG_A = new Color(0x0B1020);  // deep navy
    public static final Color BG_B = new Color(0x1B0F2E);  // purple night

    // Glass card colors
    public static final Color CARD_FILL = new Color(18, 24, 45, 210);   // semi-transparent
    public static final Color CARD_BORDER = new Color(120, 140, 255, 70);

    public static final Color TEXT = new Color(0xEAF0FF);
    public static final Color MUTED = new Color(0xA8B3D6);

    // Primary accent (electric blue)
    public static final Color PRIMARY = new Color(0x4F8CFF);
    public static final Color PRIMARY_HOVER = new Color(0x2F6BFF);

    // Input
    public static final Color INPUT_BG = new Color(10, 14, 28, 220);
    public static final Color INPUT_BORDER = new Color(120, 140, 255, 60);

    // Fonts
    public static final Font H1 = new Font("SansSerif", Font.BOLD, 24);
    public static final Font H2 = new Font("SansSerif", Font.BOLD, 16);
    public static final Font BODY = new Font("SansSerif", Font.PLAIN, 13);

    public static void install() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        UIManager.put("Label.foreground", TEXT);
        UIManager.put("RadioButton.foreground", TEXT);
        UIManager.put("RadioButton.background", new Color(0,0,0,0));
        UIManager.put("Panel.background", new Color(0,0,0,0));
    }
}
