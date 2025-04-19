package com.company.computerparts.ui.frames;

import com.company.computerparts.model.Part;
import com.company.computerparts.model.PartQuery;
import com.company.computerparts.model.ResultTableModel;
import com.company.computerparts.ui.dialog.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MainFrame extends CommonFrame {

    // 菜单组件
    private JMenuBar menuBar;
    private JMenu partMenu, inventoryMenu;
    private JMenuItem addPartItem, editPartItem, deletePartItem;
    private JMenuItem stockInItem, stockOutItem, viewLogsItem;

    // 查询组件
    private JPanel searchPanel;
    private JTextField searchField;
    private JComboBox<String> searchTypeCombo;
    private JButton searchButton, refreshButton, advancedSearchButton;

    // 结果显示组件
    private JTable resultTable;
    private ResultTableModel tableModel;
    private JScrollPane scrollPane;

    // 状态栏
    private JLabel statusLabel = new JLabel();

    public MainFrame() {

        initializeUI();
        loadInitialData();
    }

    private void initializeUI() {
        setTitle("电脑配件管理系统");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initMenuBar();
        initSearchPanel();
        initResultTable();
        initStatusBar();
        loadInitialData();

        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();

        // 配件管理菜单
        partMenu = new JMenu("配件管理");
        addPartItem = new JMenuItem("添加配件");
        editPartItem = new JMenuItem("修改配件");
        deletePartItem = new JMenuItem("删除配件");

        addPartItem.addActionListener(this::onAddPart);
        editPartItem.addActionListener(this::onEditPart);
        deletePartItem.addActionListener(this::onDeletePart);

        partMenu.add(addPartItem);
        partMenu.add(editPartItem);
        partMenu.add(deletePartItem);

        // 库存管理菜单
        inventoryMenu = new JMenu("库存管理");
        stockInItem = new JMenuItem("入库");
        stockOutItem = new JMenuItem("出库");
        viewLogsItem = new JMenuItem("查看记录");

        stockInItem.addActionListener(this::onStockIn);
        stockOutItem.addActionListener(this::onStockOut);
        viewLogsItem.addActionListener(this::onViewLogs);

        inventoryMenu.add(stockInItem);
        inventoryMenu.add(stockOutItem);
        inventoryMenu.add(viewLogsItem);

        menuBar.add(partMenu);
        menuBar.add(inventoryMenu);

        setJMenuBar(menuBar);
    }

    //功能模块
    private void initSearchPanel() {
        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        searchField = new JTextField(20);
        searchTypeCombo = new JComboBox<>(new String[]{"编号", "名称", "类型"});
        searchButton = new JButton("查询");
        refreshButton = new JButton("刷新");
        advancedSearchButton = new JButton("高级查询");

        searchButton.addActionListener(this::onSearch);
        refreshButton.addActionListener(e -> refreshData());
        advancedSearchButton.addActionListener(this::onAdvancedSearch);

        searchPanel.add(new JLabel("查询条件:"));
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("查询类型:"));
        searchPanel.add(searchTypeCombo);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);
        searchPanel.add(advancedSearchButton);
    }

    private void initResultTable() {
        tableModel = new ResultTableModel();
        resultTable = new JTable(tableModel);

        // 设置表格属性
        resultTable.setAutoCreateRowSorter(true);
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 双击行事件
        resultTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    showSelectedPartDetails();
                }
            }
        });

        scrollPane = new JScrollPane(resultTable);
    }

    private void initStatusBar() {
        statusLabel = new JLabel("就绪");
        statusLabel.setBorder(BorderFactory.createEtchedBorder());
    }

    private void loadInitialData() {
        List<Part> parts = PART_SERVICE.getAllParts();
        tableModel.setData(parts);
    }

    // ========== 事件处理方法 ==========

    private void onSearch(ActionEvent e) {
        String searchValue = searchField.getText().trim();
        String searchType = (String) searchTypeCombo.getSelectedItem();

        if (searchValue.isEmpty()) {
            showError("请输入查询条件");
            return;
        }

        try {
            PartQuery query = new PartQuery(searchType, searchValue);
            List<Part> results = PART_SERVICE.searchParts(query);
            tableModel.setData(results);
            updateStatus("找到 " + results.size() + " 条匹配记录");
        } catch (Exception ex) {
            showError("查询失败: " + ex.getMessage());
        }
    }

    private void onAdvancedSearch(ActionEvent e) {
        AdvancedSearchDialog dialog = new AdvancedSearchDialog(this, PART_SERVICE);
        dialog.setVisible(true);

        // 对话框关闭后刷新数据
        if (dialog.isSearchPerformed()) {
            tableModel.setData(dialog.getSearchResults());
            updateStatus("高级查询找到 " + dialog.getSearchResults().size() + " 条记录");
        }
    }

    private void onAddPart(ActionEvent e) {
        AddPartDialog dialog = new AddPartDialog(this, PART_SERVICE);
        dialog.setVisible(true);

        if (dialog.isPartAdded()) {
            refreshData();
        }
    }

    private void onEditPart(ActionEvent e) {
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("请先选择要修改的配件");
            return;
        }

        Part selectedPart = tableModel.getPartAt(selectedRow);
        EditPartDialog dialog = new EditPartDialog(this, PART_SERVICE, selectedPart);
        dialog.setVisible(true);

        if (dialog.isPartUpdated()) {
            refreshData();
        }
    }

    private void onDeletePart(ActionEvent e) {
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("请先选择要删除的配件");
            return;
        }

        Part selectedPart = tableModel.getPartAt(selectedRow);
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "确定要删除配件 " + selectedPart.getName() + " 吗?",
                "确认删除",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                PART_SERVICE.deletePart(selectedPart.getId());
                refreshData();
                updateStatus("配件删除成功");
            } catch (Exception ex) {
                showError("删除失败: " + ex.getMessage());
            }
        }
    }

    private void onStockIn(ActionEvent e) {
        StockInDialog dialog = new StockInDialog(this, INVENTORY_SERVICE);
        dialog.setVisible(true);

        if (dialog.isTransactionCompleted()) {
            refreshData();
        }
    }

    private void onStockOut(ActionEvent e) {
        StockOutDialog dialog = new StockOutDialog(this, INVENTORY_SERVICE);
        dialog.setVisible(true);

        if (dialog.isTransactionCompleted()) {
            refreshData();
        }
    }

    private void onViewLogs(ActionEvent e) {
        TransactionLogDialog dialog = new TransactionLogDialog(this, TRANSACTION_SERVICE);
        dialog.setVisible(true);
    }

    // ========== 辅助方法 ==========

    private void refreshData() {
        loadInitialData();
    }

    private void showSelectedPartDetails() {
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow != -1) {
            Part selectedPart = tableModel.getPartAt(selectedRow);
            PartDetailDialog dialog = new PartDetailDialog(this, selectedPart);
            dialog.setVisible(true);
        }
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "错误", JOptionPane.ERROR_MESSAGE);
        statusLabel.setText("错误: " + message);
    }

}
