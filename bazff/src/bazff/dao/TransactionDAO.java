/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.dao;

import bazff.model.TransactionModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bisma
 */
public class TransactionDAO {
    private Connection conn;
    
    public TransactionDAO(Connection conn){
        this.conn = conn;
    }
    
    public List<TransactionModel> getTransactionSummary() throws SQLException {
        final String sql = "SELECT t.id AS transaksi_id, t.created_at, u.nama_pegawai AS cashier_name, t.total_harga, SUM(dt.quantity) AS total_barang FROM transaction t JOIN user u ON t.user_id = u.id JOIN detail_transaction dt ON t.id = dt.transaksi_id GROUP BY t.id, t.created_at, u.nama_pegawai, t.total_harga ORDER BY t.created_at DESC";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<TransactionModel> daftarTransaksi = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                TransactionModel transaksi = new TransactionModel(
                    rs.getDate("created_at"),
                    rs.getString("cashier_name"),
                    rs.getInt("total_harga"),
                    rs.getInt("total_barang") 
                );
                daftarTransaksi.add(transaksi);
            }
        } catch (SQLException e) {
            System.out.println("Terjadi exception: " + e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                // Optional log
            }
        }
        return daftarTransaksi;
    }

    
    public int getTotalBarangTerjual() throws SQLException {
        final String sql = "SELECT SUM(quantity) AS total_terjual FROM detail_transaction;";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        int total = 0;

        try {
            stmt = this.conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total_terjual");
            }
        } catch (SQLException e) {
            System.out.println("Terjadi exception: " + e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                // Optional: log error closing resources
            }
        }
        return total;
    }
}
