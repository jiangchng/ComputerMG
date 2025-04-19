package com.company.computerparts;

import com.company.computerparts.ui.frames.LoginFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // 启动UI
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}