/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.dao;

import bazff.model.DetailTransModel;
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
    
//    public List<TransactionModel> getTransactionSummary() throws SQLException {
//        final String sql = "SELECT t.id AS transaksi_id, t.created_at, u.nama_pegawai AS cashier_name, t.total_harga, SUM(dt.quantity) AS total_barang FROM transaction t JOIN user u ON t.user_id = u.id JOIN detail_transaction dt ON t.id = dt.transaksi_id GROUP BY t.id, t.created_at, u.nama_pegawai, t.total_harga ORDER BY t.created_at DESC";
//
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//        List<TransactionModel> daftarTransaksi = new ArrayList<>();
//
//        try {
//            stmt = this.conn.prepareStatement(sql);
//            rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                TransactionModel transaksi = new TransactionModel(
//                    rs.getDate("created_at"),
//                    rs.getString("cashier_name"),
//                    rs.getInt("total_harga"),
//                    rs.getInt("total_barang") 
//                );
//                daftarTransaksi.add(transaksi);
//            }
//        } catch (SQLException e) {
//            System.out.println("Terjadi exception: " + e);
//        } finally {
//            try {
//                if (rs != null) rs.close();
//                if (stmt != null) stmt.close();
//            } catch (SQLException e) {
//                // Optional log
//            }
//        }
//        return daftarTransaksi;
//    }
    
    public List<TransactionModel> getTransactionSummary() throws SQLException {
        final String sql = "SELECT t.id AS transaksi_id, t.created_at, t.user_id, u.nama_pegawai AS cashier_name, SUM(dt.quantity * dt.harga_satuan) AS subtotal, SUM(dt.quantity) AS total_barang FROM transaction t JOIN user u ON t.user_id = u.id JOIN detail_transaction dt ON t.id = dt.transaksi_id GROUP BY t.id, t.created_at, t.user_id, u.nama_pegawai ORDER BY t.created_at DESC";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<TransactionModel> daftarTransaksi = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                TransactionModel transaksi = new TransactionModel(
                    rs.getDate("created_at"),
                    rs.getInt("user_id"),
                    rs.getString("cashier_name"),
                    rs.getInt("subtotal"),
                    rs.getInt("total_barang")
                );
                daftarTransaksi.add(transaksi);
            }
        } catch (SQLException e) {
            System.out.println("Terjadi exception: " + e);
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
        return daftarTransaksi;
    }

    public boolean insertTransactionWithDetails(TransactionModel transaction) throws SQLException {
        String insertTransactionSQL = "INSERT INTO transaction (user_id, total_harga, bayar, created_at) VALUES (?, ?, ?, ?)";
        String insertDetailSQL = "INSERT INTO detail_transaction (transaksi_id, product_size_id, quantity, harga_satuan) VALUES (?, ?, ?, ?)";

        PreparedStatement transStmt = null;
        PreparedStatement detailStmt = null;
        ResultSet generatedKeys = null;

        try {
            // Disable auto-commit for transaction management
            conn.setAutoCommit(false);

            // Insert into main transaction table
            transStmt = conn.prepareStatement(insertTransactionSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            transStmt.setInt(1, transaction.getUserId());
            transStmt.setInt(2, transaction.getTotal());
            transStmt.setInt(3, transaction.getBayar());
            transStmt.setDate(4, transaction.getTanggal());

            int affectedRows = transStmt.executeUpdate();
            if (affectedRows == 0) {
                conn.rollback();
                throw new SQLException("Failed to insert transaction, no rows affected.");
            }

            // Get the generated transaction ID
            generatedKeys = transStmt.getGeneratedKeys();
            if (!generatedKeys.next()) {
                conn.rollback();
                throw new SQLException("Failed to obtain transaction ID.");
            }
            int transactionId = generatedKeys.getInt(1);

            // Insert each detail
            detailStmt = conn.prepareStatement(insertDetailSQL);
            for (DetailTransModel detail : transaction.getDetails()) {
                detailStmt.setInt(1, transactionId);
                detailStmt.setInt(2, detail.getProductSizeId()); // Make sure this method exists in your model
                detailStmt.setInt(3, detail.getQuantity());
                detailStmt.setInt(4, detail.getHargaSatuan());
                detailStmt.addBatch();
            }
            detailStmt.executeBatch();

            // Commit transaction
            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.out.println("Rollback failed: " + rollbackEx);
                }
            }
            System.out.println("Insert transaction failed: " + e);
            return false;

        } finally {
            // Reset autocommit
            if (conn != null) {
                conn.setAutoCommit(true);
            }

            if (generatedKeys != null) generatedKeys.close();
            if (transStmt != null) transStmt.close();
            if (detailStmt != null) detailStmt.close();
        }
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
