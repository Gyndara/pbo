/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.dao;

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
}
