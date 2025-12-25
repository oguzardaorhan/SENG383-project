package com.kidtask.ui.pages.teacher;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TeacherDashboardStyles {

    public static final Color BG = new Color(0x0B1020);
    public static final Color PANEL = new Color(255, 255, 255, 12);

    public static final Color TEXT = new Color(235, 240, 255, 220);
    public static final Color MUTED = new Color(235, 240, 255, 150);

    public static Font title() {
        return new Font("SansSerif", Font.BOLD, 22);
    }

    public static Font body() {
        return new Font("SansSerif", Font.PLAIN, 13);
    }

    public static Border pagePadding() {
        return new EmptyBorder(18, 18, 18, 18);
    }
}
