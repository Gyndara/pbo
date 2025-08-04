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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.PopupMenu;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author bisma
 */
public class CartController {
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
    
   public static JPanel[] generateDaftarPanels(CartController controller) {
        ArrayList<JPanel> panels = new ArrayList<>();
        try {
            
            for (ProductModel p : daftarProduct) {
                
                JPanel panelProduk = new JPanel();
                panelProduk.setMaximumSize(new Dimension(1920, 220));
                panelProduk.setLayout(new BorderLayout());
                panelProduk.setBackground(new Color(255, 230, 248));

                // Gambar
                JLabel labelGambar = new JLabel();
                labelGambar.setHorizontalAlignment(JLabel.CENTER);
                ImageIcon icon = new ImageIcon(HomePageController.class.getClassLoader().getResource("resources/Work_shirt.png"));
                Image img = icon.getImage().getScaledInstance(320, 320, Image.SCALE_SMOOTH);
                labelGambar.setIcon(new ImageIcon(img));

                // Nama
                JLabel labelNama = new JLabel(p.getProductName(), JLabel.LEFT);
                labelNama.setFont(new Font("Arial", Font.BOLD, 14));
                
                // Ukuran
                JLabel labelSize = new JLabel("  " + p.getSizeId(), JLabel.LEFT);
                labelSize.setFont(new Font("Arial", Font.BOLD, 14));

                // Harga
                JLabel labelHarga = new JLabel("Rp. " + p.getProductPrice(), JLabel.LEFT);
                labelHarga.setFont(new Font("Arial", Font.PLAIN, 13));

                // Panel bagian kiri
                JPanel panelKiri = new JPanel(new GridBagLayout());
                panelKiri.setOpaque(false);
                GridBagConstraints gbc = new GridBagConstraints();
                
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                gbc.fill=GridBagConstraints.BOTH;
                
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridheight = 2;
                panelKiri.add(labelGambar, gbc);
                
                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.gridheight = 1;
                panelKiri.add(labelNama, gbc);
                
                gbc.gridx = 1;
                gbc.gridy = 1;
                gbc.gridheight = 1;
                panelKiri.add(labelHarga, gbc);
                
                //TODO: Ganti Angka ID jadi ukuran S M L XL
                gbc.gridx = 2;
                gbc.gridy = 0;
                gbc.gridheight = 1;
                panelKiri.add(labelSize, gbc);
                
                // Panel bagian kanan
                JPanel panelKanan = new JPanel(new BorderLayout());
                panelKiri.setOpaque(false);
                
                //TODO: Tambahin tombol dan teks jumlah beli
                //
                //
                //

                // Gabung semua
                panelProduk.add(panelKiri, BorderLayout.WEST);
                panelProduk.add(panelKanan, BorderLayout.EAST);

                panels.add(panelProduk);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return panels.toArray(new JPanel[0]);
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

    public void KeluarPage(ShoppingCartView form){
        form.setVisible(false);
        homepage.setVisible(true);
    }
    
}
