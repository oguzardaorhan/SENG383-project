package com.kidtask.ui.pages.child;
import javax.swing.border.Border;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChildDashboardStyles {

    public static final Color BG = new Color(0x0B1020);
    public static final Color SIDEBAR = new Color(18, 24, 48);
    public static final Color CONTENT = new Color(14, 20, 40);

    public static final Color TEXT = new Color(235, 240, 255);
    public static final Color MUTED = new Color(235, 240, 255, 140);
    public static final Color ACCENT = new Color(108, 99, 255);

    public static Font title() {
        return new Font("SansSerif", Font.BOLD, 22);
    }

    public static Font menu() {
        return new Font("SansSerif", Font.BOLD, 14);
    }

    public static Font body() {
        return new Font("SansSerif", Font.PLAIN, 13);
    }

    public static Border sidebarPadding() {
        return new EmptyBorder(24, 20, 24, 20);
    }

    public static Border contentPadding() {
        return new EmptyBorder(24, 24, 24, 24);
    }
}
