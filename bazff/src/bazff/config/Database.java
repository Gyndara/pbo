/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.config;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author bisma
 */
public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/point_of_sale";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";
    private static Connection koneksi;
     
    public static Connection getKoneksi() throws SQLException{
        if (koneksi == null){
            MysqlDataSource mysqlDS = new MysqlDataSource();
            
            mysqlDS.setURL(DB_URL);
            mysqlDS.setUser(DB_USERNAME);
            mysqlDS.setPassword(DB_PASSWORD);
            
            koneksi = mysqlDS.getConnection();
        }
        return koneksi;
    }
}
