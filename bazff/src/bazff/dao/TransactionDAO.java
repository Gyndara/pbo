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
        final String sql = "SELECT t.created_at, u.nama_pegawai AS cashier_name, t.total_harga FROM transaction t JOIN user u ON t.user_id = u.id ORDER BY t.created_at DESC";

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
                    rs.getInt("total_harga")
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
                
            }
        }
        return daftarTransaksi;
    }
}
