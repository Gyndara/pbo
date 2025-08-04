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
import java.awt.Component;
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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author bisma
 */
public class CartController {
    public static List<Map.Entry<ProductModel, Integer>> daftarProduct = new ArrayList<>();
    private HomePage homepage;
    private MainWindow mainWindow;
    
    public CartController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }
    
    public void setHomePage(HomePage homepage) {
        this.homepage = homepage;
    }
    
    public static JPanel[] generateDaftarPanels(CartController controller, Map<Integer, String> sizeMap, JPanel MainPanel) {
        ArrayList<JPanel> panels = new ArrayList<>();
        try {
            for (Map.Entry<ProductModel, Integer> p : daftarProduct) {
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

                // Nama Produk
                JLabel labelNama = new JLabel(p.getKey().getProductName(), JLabel.LEFT);
                labelNama.setFont(new Font("Arial", Font.BOLD, 14));

                // ✅ Ukuran: ubah ID menjadi nama ukuran
                String sizeName = sizeMap.getOrDefault(p.getKey().getSizeId(), "tidak diketahui");
                JLabel labelSize = new JLabel(" Ukuran: " + sizeName, JLabel.LEFT);
                labelSize.setFont(new Font("Arial", Font.BOLD, 14));

                // Harga
                JLabel labelHarga = new JLabel("Rp. " + p.getKey().getProductPrice(), JLabel.LEFT);
                labelHarga.setFont(new Font("Arial", Font.PLAIN, 20));

                // Panel bagian kiri
                JPanel panelKiri = new JPanel(new GridBagLayout());
                panelKiri.setOpaque(false);
                GridBagConstraints gbc = new GridBagConstraints();

                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                gbc.fill = GridBagConstraints.BOTH;

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

                gbc.gridx = 2;
                gbc.gridy = 0;
                gbc.gridheight = 1;
                panelKiri.add(labelSize, gbc);

                // Panel bagian kanan
                JPanel panelKanan = new JPanel();
                panelKanan.setLayout(new BoxLayout(panelKanan, BoxLayout.Y_AXIS));
                panelKanan.setBorder(new EmptyBorder(40, 0, 0, 60));
                panelKanan.setOpaque(false);
                
                Dimension ukuran = new Dimension(80, 40);
                

                // TODO: Tambahin tombol dan teks jumlah beli jika diperlukan
                JTextField TxtJumlah = new JTextField();
                TxtJumlah.setText(String.valueOf(p.getValue()));
                TxtJumlah.setEditable(false);
                TxtJumlah.setBackground(new Color(236,127,169));
                TxtJumlah.setForeground(new Color(148, 42, 42));
                TxtJumlah.setFont(new Font("Dialog", 1,30));
                TxtJumlah.setHorizontalAlignment(JTextField.CENTER);
                TxtJumlah.setBorder(null);
                TxtJumlah.setMinimumSize(ukuran);
                TxtJumlah.setMaximumSize(ukuran);
                TxtJumlah.setPreferredSize(ukuran);
                TxtJumlah.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                
                //Button Tambah
                JButton BtnTambah = new JButton();
                BtnTambah.setBackground(new Color(236, 127, 169));
                BtnTambah.setFont(new Font("Dialog", 1,30));
                BtnTambah.setForeground(new Color(148, 42, 42));
                BtnTambah.setText("+");
                BtnTambah.setBorder(null);
                BtnTambah.setMinimumSize(ukuran);
                BtnTambah.setMaximumSize(ukuran);
                BtnTambah.setPreferredSize(ukuran);
                BtnTambah.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                //Button Kurang
                JButton BtnKurang = new JButton();
                BtnKurang.setBackground(new Color(236, 127, 169));
                BtnKurang.setFont(new Font("Dialog", 1,30));
                BtnKurang.setForeground(new Color(148, 42, 42));
                BtnKurang.setText("-");
                BtnKurang.setBorder(null);
                BtnKurang.setMinimumSize(ukuran);
                BtnKurang.setMaximumSize(ukuran);
                BtnKurang.setPreferredSize(ukuran);
                BtnKurang.setAlignmentX(Component.CENTER_ALIGNMENT);
               
                
                //Fungsi Button Tambah
                BtnTambah.addActionListener(e -> {
                    int newQty = p.getValue() + 1;
                    p.setValue(newQty);
                    TxtJumlah.setText(String.valueOf(newQty));
                
                });
                
                //Fungsi Button Kurang
                BtnKurang.addActionListener(e -> {
                    int currentQty = p.getValue();
                    if (currentQty > 1){
                    int newQty = p.getValue() - 1;
                    p.setValue(newQty);
                    TxtJumlah.setText(String.valueOf(newQty));
                    }
                    else{
                        daftarProduct.remove(p);
                        panels.remove(panelProduk);
                        MainPanel.remove(panelProduk);
                        MainPanel.revalidate();
                        MainPanel.repaint();
                    }
                });
                
                
                //Tambah Jumlah
                panelKanan.add(BtnTambah);
                panelKanan.add(Box.createVerticalStrut(10));
                panelKanan.add(TxtJumlah);
                panelKanan.add(Box.createVerticalStrut(10));
                panelKanan.add(BtnKurang);
                
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

    public static List<Map.Entry<ProductModel, Integer>> getDaftarProduct() {
        return daftarProduct;
    }

    public static void setDaftarProduct(List<Map.Entry<ProductModel, Integer>> daftarProduct) {
        CartController.daftarProduct = daftarProduct;
    }
    
    public static void addDaftarProduct(Map.Entry<ProductModel, Integer> produk){
        CartController.daftarProduct.add(produk);
    }

    public void KeluarPage(ShoppingCartView form){
        form.setVisible(false);
        homepage.setVisible(true);
    }
    
}
