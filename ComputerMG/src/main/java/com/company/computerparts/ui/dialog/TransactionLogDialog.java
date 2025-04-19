package com.company.computerparts.ui.dialog;

import com.company.computerparts.model.InventoryRecord;
import com.company.computerparts.model.TransactionModel;
import com.company.computerparts.service.TransactionService;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TransactionLogDialog extends JDialog {
    private final TransactionService transactionService;

    private static final String[] TRANSACTION_TYPES = {"全部", "入库", "出库"};

    private JTable transactionTable;
    private JComboBox<String> typeCombo;
    private JButton refreshButton;
    private TransactionModel transactionModel;
    private JLabel statusLabel;

    public TransactionLogDialog(JFrame parent, TransactionService transactionService) {
        super(parent, "交易记录", true);
        this.transactionService = transactionService;
        initializeUI();
        loadTransactionData();
    }

    private void initializeUI() {
        setSize(800, 500); // 增大窗口尺寸
        setLocationRelativeTo(getParent());

        // 主面板使用BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 筛选面板
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.add(new JLabel("交易类型:"));
        typeCombo = new JComboBox<>(TRANSACTION_TYPES);
        typeCombo.setPreferredSize(new Dimension(100, 25));
        filterPanel.add(typeCombo);

        refreshButton = new JButton("刷新");
        refreshButton.addActionListener(e -> loadTransactionData());
        filterPanel.add(refreshButton);

        // 状态标签
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.GRAY);
        filterPanel.add(statusLabel);

        // 表格模型和表格
        transactionModel = new TransactionModel();
        transactionTable = new JTable(transactionModel);

        // 表格优化
        transactionTable.setAutoCreateRowSorter(true);
        transactionTable.setFillsViewportHeight(true);
        transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setPreferredSize(new Dimension(750, 400));

        // 添加组件到主面板
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 添加关闭按钮
        JButton closeButton = new JButton("关闭");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void loadTransactionData() {
        SwingWorker<List<InventoryRecord>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<InventoryRecord> doInBackground() throws Exception {
                String type = (String) typeCombo.getSelectedItem();
                return transactionService.getTransactionsType(type);
            }

            @Override
            protected void done() {
                try {
                    List<InventoryRecord> transactions = get();
                    if (transactions.isEmpty()) {
                        statusLabel.setText("没有找到交易记录");
                    } else {
                        statusLabel.setText("已加载 " + transactions.size() + " 条记录");
                    }
                    transactionModel.setData(transactions);
                } catch (Exception e) {
                    statusLabel.setText("加载数据失败");
                    JOptionPane.showMessageDialog(TransactionLogDialog.this,
                            "加载交易记录失败: " + e.getMessage(),
                            "错误",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        statusLabel.setText("正在加载数据...");
        worker.execute();
    }
}