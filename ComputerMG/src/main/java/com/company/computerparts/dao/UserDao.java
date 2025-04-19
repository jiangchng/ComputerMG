package com.company.computerparts.dao;

import com.company.computerparts.config.DatabaseConfig;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends BaseDao {
        public UserDao(DatabaseConfig dbConfig) {
            super(dbConfig);
        }

        public boolean authenticate(String username, String password){
            try {
                Connection conn = dbConfig.getConnection();
                String sql="SELECT * FROM yonghu WHERE id =? AND mima =?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet rs= preparedStatement.executeQuery();

                JDialog dialog = new JDialog();
                dialog.setLocationRelativeTo(null);
                dialog.setSize(100,80);
                if(rs.next()){
                    return true;
                }else{
                    return false;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
        }
}
