/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.dao;

import bazff.config.Database;
import bazff.model.ProductDetailModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author bisma
 */
public class ProductDetailDAO {
    private Connection conn;

    public ProductDetailDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertProductDetail(ProductDetailModel detail) throws SQLException {
        String sql = "INSERT INTO product_size (product_id, size_id, sku_code, quantity, product_price, product_status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, detail.getProductId());
            stmt.setInt(2, detail.getSizeId());
            stmt.setString(3, detail.getSkuCode());
            stmt.setInt(4, detail.getQuantity());
            stmt.setInt(5, detail.getProductPrice());
            stmt.setString(6, detail.getProductStatus());
            stmt.executeUpdate();
        }
    }
}

