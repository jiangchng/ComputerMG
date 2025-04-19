package com.company.computerparts.dao;

import com.company.computerparts.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class InventoryDao extends BaseDao{

    public InventoryDao(DatabaseConfig dbConfig){
        super(dbConfig);
    }

    public String getPartNameById(int partid){
        String sql = "SELECT PartName FROM Parts where PartID = ?";
        try(Connection connection = dbConfig.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setInt(1,partid);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getString("PartName");
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean stockIn(int partId, String partName, LocalDate stockdata, int quantity, String remarks){
        String sql1 = "INSERT INTO inventory_records (record_type, part_id, part_name, record_date, quantity, notes) VALUES (?,?,?,?,?,?)";
        String sql2 = "UPDATE inventory SET quantity = quantity + ? WHERE PartID = ?";
        try (Connection con = dbConfig.getConnection();
             PreparedStatement stmt1 = con.prepareStatement(sql1);
             PreparedStatement stmt2 = con.prepareStatement(sql2)){
            stmt1.setString(1, "in");
            stmt1.setInt(2, partId);
            stmt1.setString(3, partName);
            stmt1.setObject(4, stockdata);
            stmt1.setInt(5, quantity);
            stmt1.setString(6, remarks);

            stmt2.setInt(1, quantity);
            stmt2.setInt(2, partId);

            stmt2.executeUpdate();

            return stmt1.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean stockOut(int partId, String partName, LocalDate stockdata, int quantity, String remarks){
        String sql1 = "INSERT INTO inventory_records (record_type, part_id, part_name, record_date, quantity, notes) VALUES (?,?,?,?,?,?)";
        String sql2 = "UPDATE inventory SET quantity = quantity - ? WHERE PartID = ?";
        try (Connection con = dbConfig.getConnection();
             PreparedStatement stmt1 = con.prepareStatement(sql1);
             PreparedStatement stmt2 = con.prepareStatement(sql2)){
            stmt1.setString(1, "out");
            stmt1.setInt(2, partId);
            stmt1.setString(3, partName);
            stmt1.setObject(4, stockdata);
            stmt1.setInt(5, quantity);
            stmt1.setString(6, remarks);

            stmt2.setInt(1, quantity);
            stmt2.setInt(2, partId);

            stmt2.executeUpdate();

            return stmt1.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
