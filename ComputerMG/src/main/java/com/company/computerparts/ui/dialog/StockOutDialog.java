package com.company.computerparts.ui.dialog;

import com.company.computerparts.service.InventoryService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;

public class StockOutDialog extends JDialog {
    private String partName;
    private final InventoryService inventoryService;
    private boolean transactionCompleted = false;

    private JTextField partIdField, quantityField, remarksField;
    private JLabel partNameLabel;
    private JComboBox<Integer> yearCombo, monthCombo, dayCombo;


    public StockOutDialog(JFrame parent, InventoryService inventoryService) {
        super(parent, "配件出库", true);
        this.inventoryService = inventoryService;
        initializeUI();
    }

    private void initializeUI() {
        setSize(500, 300); // Increased size to accommodate new fields
        setLocationRelativeTo(getParent());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // Changed to 6 rows

        // Current date
        LocalDate today = LocalDate.now();

        // Part ID and Name
        formPanel.add(new JLabel("配件编号:"));
        partIdField = new JTextField();
        partIdField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updatePartName();
            }
        });
        formPanel.add(partIdField);

        formPanel.add(new JLabel("配件名称:"));
        partNameLabel = new JLabel(" ");
        formPanel.add(partNameLabel);

        // Date components
        formPanel.add(new JLabel("入库日期:"));
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        yearCombo = new JComboBox<>(new Integer[]{today.getYear(), today.getYear()+1});
        monthCombo = new JComboBox<>(new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12});
        dayCombo = new JComboBox<>(new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31});

        yearCombo.setSelectedItem(today.getYear());
        monthCombo.setSelectedItem(today.getMonthValue());
        dayCombo.setSelectedItem(today.getDayOfMonth());

        datePanel.add(yearCombo);
        datePanel.add(new JLabel("年"));
        datePanel.add(monthCombo);
        datePanel.add(new JLabel("月"));
        datePanel.add(dayCombo);
        datePanel.add(new JLabel("日"));
        formPanel.add(datePanel);

        formPanel.add(new JLabel("数量:"));
        quantityField = new JTextField();
        formPanel.add(quantityField);

        formPanel.add(new JLabel("备注:"));
        remarksField = new JTextField();
        formPanel.add(remarksField);

        JButton confirmButton = new JButton("确认出库");
        confirmButton.addActionListener(this::onConfirm);

        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onConfirm(ActionEvent e) {
        try {
            int partId = Integer.parseInt(partIdField.getText());
            int year = (int) yearCombo.getSelectedItem();
            int month = (int) monthCombo.getSelectedItem();
            int day = (int) dayCombo.getSelectedItem();
            int quantity = Integer.parseInt(quantityField.getText());
            String remarks = remarksField.getText();

            LocalDate stockdata = LocalDate.of(year, month, day);

            if (inventoryService.stockOut(partId, partName, stockdata, quantity, remarks)) {
                transactionCompleted = true;
                JOptionPane.showMessageDialog(this, "出库成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "出库失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "出库操作失败: " + ex.getMessage(),
                    "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isTransactionCompleted() {
        return transactionCompleted;
    }

    private void updatePartName() {
        try {
            String partIdText = partIdField.getText().trim();
            if (!partIdText.isEmpty()) {
                int partId = Integer.parseInt(partIdText);
                partName = inventoryService.getPartNameById(partId);
                System.out.println(partName);
                partNameLabel.setText(partName != null ? partName : "未知配件");
            } else {
                partNameLabel.setText(" ");
            }
        } catch (NumberFormatException ex) {
            partNameLabel.setText("无效ID");
        } catch (Exception ex) {
            partNameLabel.setText("查询失败: " + ex.getMessage());
        }
    }
}