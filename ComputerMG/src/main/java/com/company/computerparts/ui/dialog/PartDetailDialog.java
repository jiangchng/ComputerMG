package com.company.computerparts.ui.dialog;

import com.company.computerparts.model.Part;
import javax.swing.*;
import java.awt.*;

public class PartDetailDialog extends JDialog {
    public PartDetailDialog(JFrame parent, Part part) {
        super(parent, "配件详情", true);
        initializeUI(part);
    }

    private void initializeUI(Part part) {
        setSize(300, 250);
        setLocationRelativeTo(getParent());

        JPanel detailPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        detailPanel.add(new JLabel("配件编号:"));
        detailPanel.add(new JLabel(String.valueOf(part.getId())));

        detailPanel.add(new JLabel("名称:"));
        detailPanel.add(new JLabel(part.getName()));

        detailPanel.add(new JLabel("规格:"));
        detailPanel.add(new JLabel(part.getSpecification()));

        detailPanel.add(new JLabel("类型:"));
        detailPanel.add(new JLabel(part.getType()));

        detailPanel.add(new JLabel("进价:"));
        detailPanel.add(new JLabel(part.getPurchasePrice().toString()));

        detailPanel.add(new JLabel("售价:"));
        detailPanel.add(new JLabel(part.getSalePrice().toString()));

        detailPanel.add(new JLabel("库存数量:"));
//        detailPanel.add(new JLabel(String.valueOf(part.getStockQuantity())));

        JButton closeButton = new JButton("关闭");
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);

        setLayout(new BorderLayout());
        add(detailPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}