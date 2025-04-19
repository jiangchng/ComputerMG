package com.company.computerparts.model;

import com.company.computerparts.model.Part;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ResultTableModel extends AbstractTableModel {
    private final String[] columnNames = {"编号", "名称", "规格", "类型", "进价", "售价", "库存"};
    private List<Part> data = new ArrayList<>();

    public void setData(List<Part> data) {
        this.data = data;
        fireTableDataChanged();
    }

    public Part getPartAt(int row) {
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
        Part part = data.get(row);
        switch (column) {
            case 0: return part.getId();
            case 1: return part.getName();
            case 2: return part.getSpecification();
            case 3: return part.getType();
            case 4: return part.getPurchasePrice();
            case 5: return part.getSalePrice();
            case 6: return part.getStockQuantity();
            default: return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return Integer.class;
            case 4: case 5: return Double.class;
            case 6: return Integer.class;
            default: return String.class;
        }
    }
}