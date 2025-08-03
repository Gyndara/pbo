package bazff.controller;

import bazff.view.HomePage;
import bazff.view.MainWindow;
import bazff.view.ShoppingCartView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HomePageController {
    private ShoppingCartView shoppingCart;
    private HomePage homePage;

    public void setHomePage(HomePage homePage) {
        this.homePage = homePage;
    }
    
    public static JPanel[] generateProdukPanels(String[] namaProduk, String[] hargaProduk, String[] gambarProduk) {
        ArrayList<JPanel> panels = new ArrayList<>();

        for (int i = 0; i < namaProduk.length; i++) {
            JPanel panelProduk = new JPanel();
            panelProduk.setPreferredSize(new Dimension(250, 300));
            panelProduk.setLayout(new BorderLayout());
            panelProduk.setBackground(Color.WHITE);

            // Gambar
            JLabel labelGambar = new JLabel();
            labelGambar.setHorizontalAlignment(JLabel.CENTER);
            ImageIcon icon = new ImageIcon(HomePageController.class.getClassLoader().getResource("resources/" + gambarProduk[i]));
            Image img = icon.getImage().getScaledInstance(320, 320, Image.SCALE_SMOOTH);
            labelGambar.setIcon(new ImageIcon(img));

            // Nama
            JLabel labelNama = new JLabel(namaProduk[i], JLabel.LEFT);
            labelNama.setFont(new Font("Arial", Font.BOLD, 14));

            // Harga
            JLabel labelHarga = new JLabel(hargaProduk[i], JLabel.LEFT);
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
            int index = i;
            btnDetail.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, "Kamu memilih: " + namaProduk[index]);
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

        return panels.toArray(new JPanel[0]);
    }
    
    public void tampilHalamanCart(){
        ShoppingCartView cartView = new ShoppingCartView(this);
        cartView.setVisible(true);
        homePage.setVisible(false);
    }
}
