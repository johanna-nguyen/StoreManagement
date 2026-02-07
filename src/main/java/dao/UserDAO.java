/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DatabaseConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

/**
 *
 * @author nguye
 */
public class UserDAO {
    private DatabaseConnection db = new DatabaseConnection();

    public boolean login(String email, String password)
            throws SQLException, ClassNotFoundException {

        String sql = "SELECT password FROM USERS WHERE email = ?";
        db.open();

        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String dbPassword = rs.getString("password");
            return dbPassword.equals(password);
        }

        db.close();
        return false;
    }

    
}
