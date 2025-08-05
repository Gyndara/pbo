/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.dao;

import bazff.config.Database;
import bazff.model.MainProductModel;
import bazff.model.ProductModel;
import java.sql.Connection;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author bisma
 */
public class ProductDAO {
    private Connection conn;
    
    public ProductDAO(Connection conn){
        this.conn = conn;
    }
    
    public List<ProductModel> getProductDisplay() throws SQLException {
        final String sql = "SELECT p.product_code, p.product_name, ps.sku_code, ps.quantity, ps.product_status FROM product p JOIN product_size ps ON p.id = ps.product_id";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ProductModel> daftarProduk = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ProductModel produk = new ProductModel();

                produk.setProductCode(rs.getString("product_code"));
                produk.setProductName(rs.getString("product_name"));
                produk.setSkuCode(rs.getString("sku_code"));
                produk.setQuantity(rs.getInt("quantity"));
                produk.setProductStatus(rs.getString("product_status"));

                daftarProduk.add(produk);
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
        return daftarProduk;
    }
    
    public List<ProductModel> getProductHomepage() throws SQLException {
        final String sql = "SELECT p.product_code, p.product_name, ps.sku_code, ps.size_id, ps.quantity, ps.product_price, ps.product_status FROM product p JOIN product_size ps ON p.id = ps.product_id where ps.quantity > 0 AND ps.product_status = 'ready'";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ProductModel> daftarProduk = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ProductModel produk = new ProductModel();

                produk.setProductCode(rs.getString("product_code"));
                produk.setProductName(rs.getString("product_name"));
                produk.setSkuCode(rs.getString("sku_code"));
                produk.setQuantity(rs.getInt("quantity"));
                produk.setProductStatus(rs.getString("product_status"));
                produk.setProductPrice(rs.getInt("product_price"));
                produk.setSizeId(rs.getInt("size_id"));

                daftarProduk.add(produk);
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
        return daftarProduk;
    }
        
    public List<ProductModel> getSizesByProductCode(String productCode) throws SQLException {
        List<ProductModel> sizeList = new ArrayList<>();

        String query = "SELECT p.product_code, p.product_name, " +
                       "ps.sku_code, ps.size_id, ps.quantity, ps.product_price, ps.product_status " +
                       "FROM product p " +
                       "JOIN product_size ps ON p.id = ps.product_id " +
                       "WHERE p.product_code = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, productCode);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProductModel product = new ProductModel();

                    product.setProductCode(rs.getString("product_code"));
                    product.setProductName(rs.getString("product_name"));
                    product.setSkuCode(rs.getString("sku_code"));
                    product.setSizeId(rs.getInt("size_id"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setProductPrice(rs.getInt("product_price"));
                    product.setProductStatus(rs.getString("product_status"));

                    sizeList.add(product);
                }
            }
        }

        return sizeList;

    }
    
    public void deleteBySku(String skuCode) throws SQLException{
        String sql = "DELETE FROM product_size WHERE sku_code = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, skuCode);
            stmt.executeUpdate();
        }
    }
    
    public int getTotalBarangMasuk() throws SQLException {
        final String sql = "SELECT SUM(ps.quantity) AS total_quantity FROM product_size ps JOIN product p ON ps.product_id = p.id WHERE ps.product_status = 'ready';";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        int total = 0;

        try {
            stmt = this.conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total_quantity");
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
    
    public void updateProductBySku(String skuCode, Integer quantity, Integer price, String status) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE product_size SET ");
        List<Object> params = new ArrayList<>();

        // Bangun bagian SET berdasarkan parameter yang tidak null
        if (quantity != null) {
            sql.append("quantity = quantity + ?, ");
            params.add(quantity);
        }

        if (price != null) {
            sql.append("product_price = ?, ");
            params.add(price);
        }

        if (status != null && !status.isEmpty()) {
            sql.append("product_status = ?, ");
            params.add(status);
        }

        // Hapus koma terakhir dan tambahkan WHERE
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE sku_code = ?");
        params.add(skuCode);

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            stmt.executeUpdate();
        } finally {
            if (stmt != null) stmt.close();
        }
    }
    
    public void insertProduct(MainProductModel product) throws SQLException {
        String sql = "INSERT INTO product (product_code, product_name, product_image) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getProductCode());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getProductImage());
            stmt.executeUpdate();
        }
    }

    public int getProductIdByCode(String code) throws SQLException {
        String sql = "SELECT id FROM product WHERE product_code = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        }
        return -1;
    }
}
