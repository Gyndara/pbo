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

public class HomePageController {
    private MainWindow mainWindow;
    private HomePage homePage;
    private DetailProduct detailProduct;
    private CartController cartController;
    
    public HomePageController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }
    
    public void setHomePage(HomePage homePage) {
        this.homePage = homePage;
    }
    
    public HomePage getHomePage() {
        return homePage;
    }
        
    public void detailProduct(String productCode){
        detailProduct = new DetailProduct(this, productCode);
        detailProduct.setVisible(true);
        homePage.setVisible(false);
    }
    
    public void keluarPageDetail(DetailProduct form){
        form.dispose();
        homePage.setVisible(true);
    }
    
    public static JPanel[] generateProdukPanels(HomePageController controller) {
        ArrayList<JPanel> panels = new ArrayList<>();
        try {
            Connection conn = Database.getKoneksi();
            ProductDAO productDAO = new ProductDAO(conn);
            List<ProductModel> produkList = productDAO.getProductHomepage();
            Map<String, ProductModel> uniqueProducts = new LinkedHashMap<>();
            
            for (ProductModel p : produkList) {
                if (uniqueProducts.containsKey(p.getProductCode())) {
                    continue;
                }
                uniqueProducts.put(p.getProductCode(), p);
                
                JPanel panelProduk = new JPanel();
                panelProduk.setPreferredSize(new Dimension(250, 300));
                panelProduk.setLayout(new BorderLayout());
                panelProduk.setBackground(Color.WHITE);

                // Gambar
                JLabel labelGambar = new JLabel();
                labelGambar.setHorizontalAlignment(JLabel.CENTER);
                ImageIcon icon = new ImageIcon(HomePageController.class.getClassLoader().getResource("resources/Work_shirt.png"));
                Image img = icon.getImage().getScaledInstance(320, 320, Image.SCALE_SMOOTH);
                labelGambar.setIcon(new ImageIcon(img));

                // Nama
                JLabel labelNama = new JLabel(p.getProductName(), JLabel.LEFT);
                labelNama.setFont(new Font("Arial", Font.BOLD, 14));

                // Harga
                JLabel labelHarga = new JLabel("Rp. " + p.getProductPrice(), JLabel.LEFT);
                labelHarga.setFont(new Font("Arial", Font.PLAIN, 13));

                // Tombol
                JButton btnDetail = new JButton("Lihat Detail");
                btnDetail.setBackground(Color.decode("#FFE6F8"));
                btnDetail.setForeground(Color.decode("#EC7FA9"));
                btnDetail.setFocusPainted(false);
                btnDetail.setFont(new Font("Arial", Font.BOLD, 12));
                btnDetail.setPreferredSize(new Dimension(180, 35));
                btnDetail.setBorder(BorderFactory.createEmptyBorder());

                // Event klik
                String productCode = p.getProductCode();
                btnDetail.addActionListener(e -> {
                    controller.detailProduct(productCode);
                });

                // Panel bagian bawah
                JPanel panelBawah = new JPanel(new GridLayout(3, 1));
                panelBawah.setBackground(Color.WHITE);
                panelBawah.add(labelNama);
                panelBawah.add(labelHarga);
                panelBawah.add(btnDetail);

                // Gabung semua
                panelProduk.add(labelGambar, BorderLayout.CENTER);
                panelProduk.add(panelBawah, BorderLayout.SOUTH);

                panels.add(panelProduk);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return panels.toArray(new JPanel[0]);
    }
    
    public void tampilHalamanCart(){
        ShoppingCartView cartView = new ShoppingCartView(mainWindow);
        cartView.setVisible(true);
        homePage.setVisible(false);
    }
}
