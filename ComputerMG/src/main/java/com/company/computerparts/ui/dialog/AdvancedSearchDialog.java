package com.company.computerparts.ui.dialog;

import com.company.computerparts.model.Part;
import com.company.computerparts.service.PartService;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdvancedSearchDialog extends JDialog {
    private final PartService partService;
    private boolean searchPerformed = false;
    private List<Part> searchResults;

    private JTextField idField, nameField, specField;
    private JComboBox<String> typeCombo;
    private JSpinner minPriceSpinner, maxPriceSpinner;

    public AdvancedSearchDialog(JFrame parent, PartService partService) {
        super(parent, "高级查询", true);
        this.partService = partService;
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        // 添加表单字段
        formPanel.add(new JLabel("配件编号:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("配件名称:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("规格:"));
        specField = new JTextField();
        formPanel.add(specField);

        formPanel.add(new JLabel("类型:"));
        typeCombo = new JComboBox<>(new String[]{"", "CPU", "GPU", "内存", "硬盘"});
        formPanel.add(typeCombo);

        formPanel.add(new JLabel("最低价格:"));
        minPriceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 99999.0, 10.0));
        formPanel.add(minPriceSpinner);

        formPanel.add(new JLabel("最高价格:"));
        maxPriceSpinner = new JSpinner(new SpinnerNumberModel(99999.0, 0.0, 99999.0, 10.0));
        formPanel.add(maxPriceSpinner);

        JButton searchButton = new JButton("查询");
        searchButton.addActionListener(e -> performSearch());

        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(searchButton);
        buttonPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void performSearch() {
        try {
            // 构建查询条件
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String spec = specField.getText().trim();
            String type = (String) typeCombo.getSelectedItem();
            double minPrice = (Double) minPriceSpinner.getValue();
            double maxPrice = (Double) maxPriceSpinner.getValue();

            // 执行查询
            searchResults = partService.advancedSearch(id, name, spec, type, minPrice, maxPrice);
            searchPerformed = true;
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "查询失败: " + e.getMessage(),
                    "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSearchPerformed() {
        return searchPerformed;
    }

    public List<Part> getSearchResults() {
        return searchResults;
    }
}