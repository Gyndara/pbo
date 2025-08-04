/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.config.Database;
import bazff.dao.ProductDAO;
import bazff.dao.ProductDetailDAO;
import bazff.dao.SizeDAO;
import bazff.model.MainProductModel;
import bazff.model.ProductDetailModel;
import bazff.view.AddProduct;
import bazff.view.DeleteProductPopUp;
import bazff.view.MainWindow;
import bazff.view.SizePopUp2;
import bazff.view.SizePopUp3;
import bazff.view.UpdatePopUp1;
import bazff.view.UpdatePopUp2;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.sql.Connection;
/**
 *
 * @author bisma
 */
public class ProductController {
    private MainWindow mainWindow;
    private UpdatePopUp1 dialog1;
    private DeleteProductPopUp dialog2;
    private SizePopUp2 sizePopUp2;
    private AddProduct addProduct;
    private TableController tableController;

    public ProductController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void updatePopUp1() {
        dialog1 = new UpdatePopUp1(mainWindow, this);
        dialog1.setLocationRelativeTo(mainWindow);
        dialog1.setVisible(true);
    }

    public void updatePopUp2(UpdatePopUp1 dialog1) {
        Point posisi = dialog1.getLocation();
        String skuCode = dialog1.getjTxtSkuUpdate().getText();

        dialog1.setVisible(false);

        
        UpdatePopUp2 dialog2 = new UpdatePopUp2(mainWindow, skuCode, this);
        dialog2.setLocation(posisi);
        dialog2.setVisible(true);
    }

    public void deletePopUp() {
        dialog2 = new DeleteProductPopUp(mainWindow, true);
        dialog2.setLocationRelativeTo(mainWindow);
        dialog2.setVisible(true);
    }
    
    public void addPopUp1(){
        sizePopUp2 = new SizePopUp2(mainWindow, this);
        sizePopUp2.setLocationRelativeTo(mainWindow);
        sizePopUp2.setVisible(true);
    }
    
    public void addPopUp2(SizePopUp2 sizePopUp2){
        Point posisi = sizePopUp2.getLocation();
        String productCode = sizePopUp2.getjTextFieldProductCode().getText();
        
        sizePopUp2.setVisible(false);
        
        SizePopUp3 sizePopUp3 = new SizePopUp3(mainWindow, productCode);
        sizePopUp3.setLocation(posisi);
        sizePopUp3.setVisible(true);
    }
    
    public void insertProductDetailFromPopup(String productCode, String selectedSizeName, String priceText) {
        try {
            Connection conn = Database.getKoneksi();

            SizeDAO sizeDAO = new SizeDAO(conn);
            ProductDAO productDAO = new ProductDAO(conn);
            ProductDetailDAO detailDAO = new ProductDetailDAO(conn);

            int sizeId = sizeDAO.getSizeIdByName(selectedSizeName);
            int price = Integer.parseInt(priceText.trim());
            int productId = productDAO.getProductIdByCode(productCode);

            String sku = productCode + sizeId;

            ProductDetailModel detail = new ProductDetailModel();
            detail.setProductId(productId);
            detail.setSizeId(sizeId);
            detail.setSkuCode(sku);
            detail.setProductPrice(price);
            detail.setQuantity(0);
            detail.setProductStatus("not_ready");

            detailDAO.insertProductDetail(detail);

            JOptionPane.showMessageDialog(null, "Data ukuran berhasil ditambahkan!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menambahkan detail produk: " + e.getMessage());
        }
    }
    
    
    public String imageChosser(JLabel LabelImage, JTextField pathField){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pilih Gambar");
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Gambar (JPG, PNG)", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(mainWindow);
        if (result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            String imagePath = selectedFile.getAbsolutePath();
            
            if (pathField != null){
                pathField.setText(imagePath);
            }
            
            if(LabelImage != null){
                ImageIcon icon = new ImageIcon(imagePath);
                Image image = icon.getImage().getScaledInstance(LabelImage.getWidth(), LabelImage.getHeight(), Image.SCALE_SMOOTH);
                LabelImage.setIcon(new ImageIcon(image));
            }
            return imagePath;
        }
        return null;
    }
    
    public void updateProduct(String skuCode, String quantityText, String priceText, String status) {
        try {
            Integer quantity = quantityText.isEmpty() ? null : Integer.valueOf(quantityText);
            Integer price = priceText.isEmpty() ? null : Integer.valueOf(priceText);
            String productStatus = (status == null || status.isEmpty()) ? null : status;

            ProductDAO dao = new ProductDAO(Database.getKoneksi());
            dao.updateProductBySku(skuCode, quantity, price, productStatus);

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteProduct(String skuCode){
        try {
            ProductDAO product = new ProductDAO(Database.getKoneksi());
            product.deleteBySku(skuCode);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public int ambilTotalBarangMasuk() {
        try {
            ProductDAO productDAO = new ProductDAO(Database.getKoneksi());
            return productDAO.getTotalBarangMasuk(); // method ini ada di ProductDAO
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public void refreshProductPanel(){
        mainWindow.getMainPanel().revalidate();
        mainWindow.getMainPanel().repaint();
    }
    
    public void insertProductFromForm(JTextField txtCode, JTextField txtName, JTextField txtPrice, JComboBox comboSize, File imageFile) {
        Connection conn = null;

        try {
            conn = Database.getKoneksi();
            
            conn.setAutoCommit(false); // 🔥 Autocommit dimatikan

            String code = txtCode.getText().trim();
            String name = txtName.getText().trim();
            int price = Integer.parseInt(txtPrice.getText().trim());
            String size = comboSize.getSelectedItem().toString();
            String imagePath = saveImageToDisk(imageFile);

            // Siapkan model dan DAO untuk product
            MainProductModel product = new MainProductModel();
            product.setProductCode(code);
            product.setProductName(name);
            product.setProductImage(imagePath);

            ProductDAO productDAO = new ProductDAO(conn);
            productDAO.insertProduct(product);
            int productId = productDAO.getProductIdByCode(code);

            // Ambil ID size
            SizeDAO sizeDAO = new SizeDAO(conn);
            int sizeId = sizeDAO.getSizeIdByName(size);
            if (sizeId == -1) throw new SQLException("Size tidak ditemukan: " + size);

            // Siapkan model detail produk
            String sku = code + sizeId;
            ProductDetailModel detail = new ProductDetailModel();
            detail.setProductId(productId);
            detail.setSizeId(sizeId);
            detail.setSkuCode(sku);
            detail.setProductPrice(price);
            detail.setQuantity(0);
            detail.setProductStatus("not_ready");

            System.out.println("debug Insert ke product_size:");
            System.out.println("product_id: " + detail.getProductId());
            System.out.println("size_id: " + detail.getSizeId());
            System.out.println("sku_code: " + detail.getSkuCode());
            System.out.println("price: " + detail.getProductPrice());
            System.out.println("quantity: " + detail.getQuantity());
            System.out.println("status: " + detail.getProductStatus());
       
            ProductDetailDAO detailDAO = new ProductDetailDAO(conn);
            try {
                detailDAO.insertProductDetail(detail);
            } catch (SQLException ex) {
                throw new SQLException("Gagal insert ke product_size: " + ex.getMessage(), ex);
            }

            conn.commit(); 
            JOptionPane.showMessageDialog(null, "Produk berhasil ditambahkan!");

        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(null, "Gagal menambahkan produk: " + e.getMessage());

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // 🔁 Kembalikan ke autocommit true
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private String saveImageToDisk(File imageFile) throws IOException {
        String folder = "resources/";
        Files.createDirectories(Paths.get(folder));
        String destPath = folder + imageFile.getName();
        Files.copy(imageFile.toPath(), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
        return destPath;
    }
}
