/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.dao;

import bazff.config.Database;
import bazff.model.SizeModel;
import java.sql.Connection;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author bisma
 */
public class SizeDAO {
    private Connection conn;
    
    public SizeDAO(Connection conn){
        this.conn = conn;
    }
    
    public List<SizeModel> getSizeDisplay() throws SQLException{
        final String sql = "SELECT s.size_name FROM size s";
                
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<SizeModel> daftarSize = new ArrayList<>();
        
        try {
            stmt = this.conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()){
                SizeModel size = new SizeModel();
                
                size.setSizeName(rs.getString("size_name"));
                
                daftarSize.add(size);
            }
        } catch (Exception e) {
            System.out.println("Terjadi exception: " + e);
        }  finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
            }
        }
        return daftarSize;
    }
    
    public void insertSize(String sizeName) throws SQLException {
        String sql = "INSERT INTO size (size_name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sizeName);
            stmt.executeUpdate();
        }
    }
    
    public void deleteSize(String sizeName) throws SQLException {
        String sql = "DELETE FROM size WHERE size_name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sizeName);
            stmt.executeUpdate();
        }
    }
    
    public List<String> getAllSizeNames() throws SQLException {
        final String sql = "SELECT size_name FROM size"; // sesuaikan nama kolom jika berbeda

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> sizes = new ArrayList<>();

        try {
            conn = Database.getKoneksi();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                sizes.add(rs.getString("size_name")); // sesuaikan jika nama kolom berbeda
            }
        } catch (Exception e) {
            System.out.println("Terjadi exception: " + e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
            }
        }

        return sizes;
    }
    
    public int getSizeIdByName(String sizeName) throws SQLException {
        final String sql = "SELECT id FROM size WHERE size_name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sizeName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1; // Jika tidak ditemukan
    }
}
