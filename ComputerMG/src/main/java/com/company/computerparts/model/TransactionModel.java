package com.company.computerparts.model;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionModel extends AbstractTableModel {
    String[] columnNames = {"记录单号", "记录类型", "配件ID", "配件名称","日期", "数量", "备注"};
    List<InventoryRecord> data = new ArrayList<>();

    public void setData(List<InventoryRecord> data) {
        this.data = data;
        fireTableDataChanged();
    }

    public InventoryRecord getPartAt(int row) {
        return data.get(row);
    }


    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        InventoryRecord part = data.get(row);
        switch (column) {
            case 0: return part.getId();
            case 1: return part.getType();
            case 2: return part.getPart_id();
            case 3: return part.getPart_name();
            case 4: return part.getData();
            case 5: return part.getQuantity();
            case 6: return part.getRemarks();
            default: return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: case 2: case 5:return Integer.class;
            default: return String.class;
        }
    }
}