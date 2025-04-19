package com.company.computerparts.dao;

import com.company.computerparts.config.DatabaseConfig;
import com.company.computerparts.model.Part;
import com.company.computerparts.model.PartQuery;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartDao extends BaseDao {
    public PartDao(DatabaseConfig dbConfig) {
        super(dbConfig);
    }

    public boolean addPart(Part part) throws SQLException {
        String sql = "INSERT INTO Parts (PartName, Specification, Typle, InitPrice, UnitPrice) VALUES (?,?,?,?,?)";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, part.getName());
            stmt.setString(2, part.getSpecification());
            stmt.setString(3, part.getType());
            stmt.setBigDecimal(4, part.getPurchasePrice());
            stmt.setBigDecimal(5, part.getSalePrice());

            return stmt.executeUpdate() > 0;
        }
    }

    public List<Part> searchParts(PartQuery query) throws SQLException {
        String sql = buildSearchSql(query);

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setSearchParameters(stmt, query);

            ResultSet rs = stmt.executeQuery();
            List<Part> parts = new ArrayList<>();

            while (rs.next()) {
                Part part = new Part();
                part.setId(rs.getInt("PartID"));
                part.setName(rs.getString("PartName"));
                part.setSpecification(rs.getString("Specification"));
                part.setType(rs.getString("Typle"));
                part.setPurchasePrice(rs.getBigDecimal("InitPrice"));
                part.setSalePrice(rs.getBigDecimal("UnitPrice"));

                parts.add(part);
            }

            return parts;
        }
    }

    private String buildSearchSql(PartQuery query) {
        switch (query.getSearchType()) {
            case "编号":
                return "SELECT * FROM Parts WHERE PartID = ?";
            case "名称":
                return "SELECT * FROM Parts WHERE PartName LIKE ?";
            case "类型":
                return "SELECT * FROM Parts WHERE Typle = ?";
            default:
                return "SELECT * FROM Parts";
        }
    }

    private void setSearchParameters(PreparedStatement stmt, PartQuery query)
            throws SQLException {

        switch (query.getSearchType()) {
            case "编号":
                stmt.setString(1, query.getSearchValue());
                break;
            case "名称":
                stmt.setString(1, "%" + query.getSearchValue() + "%");
                break;
            case "类型":
                stmt.setString(1, query.getSearchValue());
                break;
        }
    }

    public List<Part> findAllParts() throws SQLException {
        String sql = "SELECT p.*, i.Quantity FROM Parts p LEFT JOIN Inventory i ON p.PartID = i.PartID";
        List<Part> parts = new ArrayList<>();

        try (Connection conn = dbConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Part part = new Part();
                part.setId(rs.getInt("PartID"));
                part.setName(rs.getString("PartName"));
                part.setSpecification(rs.getString("Specification"));
                part.setType(rs.getString("Typle"));
                part.setPurchasePrice(rs.getBigDecimal("InitPrice"));
                part.setSalePrice(rs.getBigDecimal("UnitPrice"));
                part.setStockQuantity(rs.getInt("Quantity"));

                parts.add(part);
            }
        }

        return parts;
    }

    public Part findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM Parts WHERE PartID = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Part part = new Part();
                    part.setId(rs.getInt("PartID"));
                    part.setName(rs.getString("PartName"));
                    part.setSpecification(rs.getString("Specification"));
                    part.setType(rs.getString("Typle"));
                    part.setPurchasePrice(rs.getBigDecimal("InitPrice"));
                    part.setSalePrice(rs.getBigDecimal("UnitPrice"));
                    // 如果需要库存信息，可以添加JOIN查询
                    return part;
                }
            }
        }
        return null;
    }

    public boolean deletePart(Integer id) throws SQLException {
        String sql = "DELETE FROM Parts WHERE PartID = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public List<Part> advancedSearch(String id, String name, String specification, String type, BigDecimal minPrice, BigDecimal maxPrice) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT p.*, i.Quantity FROM Parts p LEFT JOIN Inventory i ON p.PartID = i.PartID WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (!id.isEmpty()) {
            sql.append(" AND p.PartID = ?");
            params.add(Integer.parseInt(id));
        }
        if (!name.isEmpty()) {
            sql.append(" AND p.PartName LIKE ?");
            params.add("%" + name + "%");
        }
        if (!specification.isEmpty()) {
            sql.append(" AND p.Specification LIKE ?");
            params.add("%" + specification + "%");
        }
        if (type != null && !type.isEmpty()) {
            sql.append(" AND p.Type = ?");
            params.add(type);
        }
        sql.append(" AND p.UnitPrice BETWEEN ? AND ?");
        params.add(minPrice);
        params.add(maxPrice);

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            // Set all parameters
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            List<Part> parts = new ArrayList<>();

            while (rs.next()) {
                Part part = new Part();
                part.setId(rs.getInt("PartID"));
                part.setName(rs.getString("PartName"));
                part.setSpecification(rs.getString("Specification"));
                part.setType(rs.getString("Typle"));
                part.setPurchasePrice(rs.getBigDecimal("InitPrice"));
                part.setSalePrice(rs.getBigDecimal("UnitPrice"));
                part.setStockQuantity(rs.getInt("Quantity"));
                parts.add(part);
            }

            return parts;
        }
    }

    // 其他CRUD操作...
}