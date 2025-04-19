package com.company.computerparts.ui.dialog;

import com.company.computerparts.model.Part;
import com.company.computerparts.service.PartService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;

public class AddPartDialog extends JDialog {
    private final PartService partService;
    private boolean partAdded = false;

    private JTextField nameField, specField;
    private JComboBox<String> typeCombo;
    private JTextField purchasePriceField, salePriceField;

    public AddPartDialog(JFrame parent, PartService partService) {
        super(parent, "添加配件", true);
        this.partService = partService;
        initializeUI();
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

    private void onSave(ActionEvent e) {
        try {
            Part part = new Part();
            part.setName(nameField.getText().trim());
            part.setSpecification(specField.getText().trim());
            part.setType((String) typeCombo.getSelectedItem());
            part.setPurchasePrice(new BigDecimal(purchasePriceField.getText()));
            part.setSalePrice(new BigDecimal(salePriceField.getText()));

            if (partService.addPart(part)) {
                partAdded = true;
                JOptionPane.showMessageDialog(this, "配件添加成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "配件添加失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "添加配件失败: " + ex.getMessage(),
                    "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isPartAdded() {
        return partAdded;
    }
}