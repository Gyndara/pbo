/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.config.Database;
import bazff.config.Session;
import static bazff.controller.CartController.daftarProduct;
import bazff.dao.TransactionDAO;
import bazff.model.DetailTransModel;
import bazff.model.ProductModel;
import bazff.model.TransactionModel;
import bazff.view.MainWindow;
import bazff.view.PaymentPopUp;
import bazff.view.ReceiptPopUp;
import bazff.view.ShoppingCartView;
import java.awt.Point;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author bisma
 */
public class TransactionController {
    private MainWindow mainWindow;
    private PaymentPopUp paymentPopUp;
    private ReceiptPopUp receiptPopUp;
    private TransactionDAO transactionDAO;
    private ShoppingCartView view;
    
    public TransactionController(MainWindow mainWindow, ShoppingCartView view){
        this.mainWindow = mainWindow;
        this.view = view;
    }
    
    public TransactionController(MainWindow mainWindow){
       this(mainWindow, null);
    }
    
    public TransactionController(Connection conn) {
        this.transactionDAO = new TransactionDAO(conn);
    }
    
    public void paymentPopUp() {
        if (daftarProduct.isEmpty()) {
            JOptionPane.showMessageDialog(mainWindow, "Shopping Cart kosong!", "Cart Kosong", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        paymentPopUp = new PaymentPopUp(mainWindow, this);

        // Mulai isi data struk di jTextArea2
        StringBuilder isiPembayaran = new StringBuilder();
        double total = 0;
        double ppn = 0;
        double total2 = 0;

        for (Map.Entry<ProductModel, Integer> entry : daftarProduct) {
            ProductModel produk = entry.getKey();
            int jumlah = entry.getValue();
            int harga = produk.getProductPrice();
            int subtotal = jumlah * harga;

            isiPembayaran.append(produk.getProductName())
                         .append(" - ")
                         .append(jumlah).append(" x Rp. ").append(harga)
                         .append(" = Rp. ").append(subtotal)
                         .append("\n");

            total += subtotal;
        }

        ppn = total * 0.12;
        total2 = total + ppn;
        paymentPopUp.getjTextArea2().setText(isiPembayaran.toString());
        paymentPopUp.getjTextArea1().setText(String.valueOf("SUB-TOTAL = Rp." + total + "\nPPN 12% = Rp." + ppn + "\nTOTAL = Rp." + total2));

        // Tampilkan popup
        paymentPopUp.setLocationRelativeTo(mainWindow);
        paymentPopUp.setVisible(true);
    }
    
    public void receiptPopUp(PaymentPopUp paymentPopUp){
        double bayar = Double.parseDouble(paymentPopUp.getjTextField1().getText());
        StringBuilder isiPembayaran = new StringBuilder();
        double total = 0;
        double ppn = 0;
        double total2 = 0;

        for (Map.Entry<ProductModel, Integer> entry : daftarProduct) {
            ProductModel produk = entry.getKey();
            int jumlah = entry.getValue();
            int harga = produk.getProductPrice();
            int subtotal = jumlah * harga;

            isiPembayaran.append(produk.getProductName())
                         .append(" - ")
                         .append(jumlah).append(" x Rp. ").append(harga)
                         .append(" = Rp. ").append(subtotal)
                         .append("\n");

            total += subtotal;
        }
        
        ppn = total * 0.12;
        total2 = total + ppn;
        
        if (bayar < total2) {
            double kurang = total2 - bayar;
            JOptionPane.showMessageDialog(paymentPopUp, 
            "Uang yang dibayarkan kurang dari total harga!\nTotal: Rp. " + total2 + "\nBayar: Rp. " + bayar + "\nKurang: Rp. " + kurang,
            "Pembayaran Gagal", 
            JOptionPane.ERROR_MESSAGE);
            return; 
        }
        
        double kembalian = bayar - total2;
        
        try {
            Connection conn = Database.getKoneksi();
            TransactionDAO dao = new TransactionDAO(conn);

            // Calculate totals
            int subtotal = (int) total;
            int totalWithPPN = (int) total2;

            // Prepare TransactionModel
            TransactionModel transaksi = new TransactionModel(
                Date.valueOf(LocalDate.now()),
                Session.getUserId(),
                totalWithPPN,
                (int) bayar
            );

            // Create list of details
            List<DetailTransModel> detailList = new ArrayList<>();
            for (Map.Entry<ProductModel, Integer> entry : daftarProduct) {
                ProductModel produk = entry.getKey();
                int jumlah = entry.getValue();

                DetailTransModel detail = new DetailTransModel(
                    produk.getSizeId(),  // ← Ensure this method exists
                    jumlah,
                    produk.getProductPrice()
                );
                detailList.add(detail);
            }

            transaksi.setDetails(detailList);

            // Insert to database
            boolean inserted = dao.insertTransactionWithDetails(transaksi);
            if (!inserted) {
                JOptionPane.showMessageDialog(paymentPopUp, "Gagal menyimpan transaksi ke database.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(paymentPopUp, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
        
        CartController.setDaftarProduct(new ArrayList<>());
        
        Point posisi = paymentPopUp.getLocation();
        paymentPopUp.setVisible(false);
        ReceiptPopUp receiptPopUp = new ReceiptPopUp(mainWindow, true, view);
        receiptPopUp.getjTextArea2().setText(isiPembayaran.toString());
        receiptPopUp.getjTextArea3().setText(String.valueOf("SUB-TOTAL = Rp." + total + "\nPPN 12% = Rp." + ppn + "\nTOTAL = Rp." + total2));
        receiptPopUp.getjTextArea1().setText(String.valueOf("Bayar = Rp." + bayar + "\nKembalian = Rp." + kembalian));
        receiptPopUp.setLocation(posisi);
        receiptPopUp.setVisible(true);
    }
    
    public int ambilTotalBarangTerjual(){
        try {
            TransactionDAO transactionDAO = new TransactionDAO(Database.getKoneksi());
            return transactionDAO.getTotalBarangTerjual();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}
