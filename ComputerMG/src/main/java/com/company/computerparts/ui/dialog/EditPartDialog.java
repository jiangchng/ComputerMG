package com.company.computerparts.ui.dialog;

import com.company.computerparts.model.Part;
import com.company.computerparts.service.PartService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;

public class EditPartDialog extends JDialog {
    private final PartService partService;
    private final Part originalPart;
    private boolean partUpdated = false;

    private JTextField nameField, specField;
    private JComboBox<String> typeCombo;
    private JTextField purchasePriceField, salePriceField;

    public EditPartDialog(JFrame parent, PartService partService, Part part) {
        super(parent, "编辑配件", true);
        this.partService = partService;
        this.originalPart = part;
        initializeUI();
        populateFields();
    }

    private void initializeUI() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        formPanel.add(new JLabel("配件名称:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("规格:"));
        specField = new JTextField();
        formPanel.add(specField);

        formPanel.add(new JLabel("类型:"));
        typeCombo = new JComboBox<>(new String[]{"CPU", "GPU", "内存", "硬盘"});
        formPanel.add(typeCombo);

        formPanel.add(new JLabel("进价:"));
        purchasePriceField = new JTextField();
        formPanel.add(purchasePriceField);

        formPanel.add(new JLabel("售价:"));
        salePriceField = new JTextField();
        formPanel.add(salePriceField);

        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(this::onSave);

        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void populateFields() {
        nameField.setText(originalPart.getName());
        specField.setText(originalPart.getSpecification());
        typeCombo.setSelectedItem(originalPart.getType());
        purchasePriceField.setText(originalPart.getPurchasePrice().toString());
        salePriceField.setText(originalPart.getSalePrice().toString());
    }

    private void onSave(ActionEvent e) {
        try {
            Part updatedPart = new Part();
            updatedPart.setId(originalPart.getId());
            updatedPart.setName(nameField.getText().trim());
            updatedPart.setSpecification(specField.getText().trim());
            updatedPart.setType((String) typeCombo.getSelectedItem());
            updatedPart.setPurchasePrice(new BigDecimal(purchasePriceField.getText()));
            updatedPart.setSalePrice(new BigDecimal(salePriceField.getText()));

            if (partService.updatePart(updatedPart)) {
                partUpdated = true;
                JOptionPane.showMessageDialog(this, "配件更新成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "配件更新失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "更新配件失败: " + ex.getMessage(),
                    "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isPartUpdated() {
        return partUpdated;
    }
}