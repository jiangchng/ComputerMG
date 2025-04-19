package com.company.computerparts.ui.frames;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends CommonFrame {

    //组件
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("电脑配件管理系统 - 登录");
        setSize(340, 256);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JLabel usernameLabel = new JLabel("用户名:");
        JLabel passwordLabel = new JLabel("密码:");
        usernameField = new JTextField(12);
        passwordField = new JPasswordField(12);
        usernameLabel.setBounds(52, 33, 54, 15);
        usernameField.setBounds(116, 30, 139, 21);
        passwordLabel.setBounds(52, 74, 54, 15);
        passwordField.setBounds(116, 71, 139, 21);

        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        JButton loginButton = new JButton("登录");
        JButton closeButton = new JButton("退出");
//        JButton registButton = new JButton("注册");
        loginButton.setBounds(64, 116, 69, 23);
        closeButton.setBounds(174, 116, 69, 23);
//        registButton.setBounds(169, 80, 69, 23 );

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                onLogin();
            }
        });

        mainPanel.add(loginButton);
        mainPanel.add(closeButton);
//        mainPanel.add(registButton);

        add(mainPanel);
    }

    //验证身份信息
    private void onLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (USER_SERVICE.authenticate(username, password)) {
            openMainFrame();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "用户名或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
        }
    }

    //主页面
    private void openMainFrame() {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}