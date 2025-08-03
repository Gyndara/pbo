/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.dao;

import bazff.model.ProductModel;
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
}
