package com.company.computerparts.dao;

import com.company.computerparts.config.DatabaseConfig;
import com.company.computerparts.model.InventoryRecord;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao extends BaseDao {

    // 使用常量定义日期格式和SQL字符串
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
    private static final String ALL_RECORDS_SQL = "SELECT * FROM inventory_records";
    private static final String OUTBOUND_SQL = "SELECT * FROM inventory_records WHERE record_type = 'out'";
    private static final String INBOUND_SQL = "SELECT * FROM inventory_records WHERE record_type = 'in'";

    public TransactionDao(DatabaseConfig databaseConfig) {
        super(databaseConfig);
    }

    public List<InventoryRecord> findType(String type) {
        List<InventoryRecord> inventoryRecords = new ArrayList<>();
        String sql = getSafeSearchSql(type);

        if (sql.isEmpty()) {
            return inventoryRecords; // 返回空列表而不是执行无效查询
        }

        try (Connection connection = dbConfig.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                InventoryRecord record = mapResultSetToInventoryRecord(rs);
                inventoryRecords.add(record);
            }
        } catch (SQLException e) {
            throw new InventoryQueryException("Failed to fetch inventory records by type: " + type, e);
        }
        return inventoryRecords;
    }

    private InventoryRecord mapResultSetToInventoryRecord(ResultSet rs) throws SQLException {
        InventoryRecord record = new InventoryRecord();
        record.setId(rs.getInt("record_id"));
        record.setType(rs.getString("record_type"));
        record.setPart_id(rs.getInt("part_id"));
        record.setPart_name(rs.getString("part_name"));
        record.setQuantity(rs.getInt("quantity"));
        record.setRemarks(rs.getString("notes"));

        // 安全的日期处理
        java.sql.Date sqlDate = rs.getDate("record_date");
        if (sqlDate != null) {
            record.setData(sqlDate.toLocalDate().format(DATE_FORMATTER));
        } else {
            record.setData("无日期记录");
        }

        return record;
    }

    private String getSafeSearchSql(String type) {
        if (type == null) {
            return "";
        }

        switch (type.trim()) {
            case "全部": return ALL_RECORDS_SQL;
            case "出库": return OUTBOUND_SQL;
            case "入库": return INBOUND_SQL;
            default: return "";
        }
    }

    // 自定义异常类
    public static class InventoryQueryException extends RuntimeException {
        public InventoryQueryException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}