/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.config.Database;
import bazff.dao.ProductDAO;
import bazff.model.ProductModel;
import bazff.view.DetailProduct;
import bazff.view.HomePage;
import bazff.view.MainWindow;
import bazff.view.ShoppingCartView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.PopupMenu;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author bisma
 */
public class CartController {
    private int tambahBarang;
    private int kurangBarang;
    public static List<ProductModel> daftarProduct = new ArrayList<>();
    private HomePage homepage;
    private MainWindow mainWindow;

    public CartController(){
        
    }
    
    public CartController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }
    
    public void setHomePage(HomePage homepage) {
        this.homepage = homepage;
    }
    
    public void setTambahBarang(int tambahBarang) {
        this.tambahBarang = tambahBarang;
        
    }
    
   

    public static List<ProductModel> getDaftarProduct() {
        return daftarProduct;
    }

    public static void setDaftarProduct(List<ProductModel> daftarProduct) {
        CartController.daftarProduct = daftarProduct;
    }
    
    public static void addDaftarProduct(ProductModel produk){
        CartController.daftarProduct.add(produk);
    }

    public int getTambahBarang() {
        return tambahBarang;
    }

    public int getKurangBarang() {
        return kurangBarang;
    }

    public void setKurangBarang(int kurangBarang) {
        this.kurangBarang = kurangBarang;
    }
    
    public void tambahBarang(ShoppingCartView form){
        String current = form.getjTxtTambahBarang().getText();
        this.tambahBarang = Integer.valueOf(current);
        
        this.setTambahBarang(this.tambahBarang + 1);
        
        form.getjTxtTambahBarang().setText(String.valueOf(this.getTambahBarang()));
    }

    public void kurangBarang(ShoppingCartView form){
        String current = form.getjTxtTambahBarang().getText();
        this.kurangBarang = Integer.valueOf(current);
        
        if (this.kurangBarang > 0) {
            this.setKurangBarang(this.kurangBarang - 1);
        } else {
            this.setKurangBarang(0); // Opsional, hanya untuk memastikan tetap 0
        }
        
        form.getjTxtTambahBarang().setText(String.valueOf(this.getKurangBarang()));
    }

    public void KeluarPage(ShoppingCartView form){
        form.setVisible(false);
        homepage.setVisible(true);
    }
    
}
