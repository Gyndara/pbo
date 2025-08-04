/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.config.Database;
import static bazff.controller.CartController.daftarProduct;
import bazff.dao.TransactionDAO;
import bazff.model.ProductModel;
import bazff.view.MainWindow;
import bazff.view.PaymentPopUp;
import bazff.view.ReceiptPopUp;
import java.awt.Point;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author bisma
 */
public class TransactionController {
    private MainWindow mainWindow;
    private PaymentPopUp paymentPopUp;
    private ReceiptPopUp receiptPopUp;
    private TransactionDAO transactionDAO;
    
    public TransactionController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }
    
    public TransactionController(Connection conn) {
        this.transactionDAO = new TransactionDAO(conn);
    }
    
    public void paymentPopUp() {
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
        Point posisi = paymentPopUp.getLocation();
        paymentPopUp.setVisible(false);
        ReceiptPopUp receiptPopUp = new ReceiptPopUp(mainWindow, true);
        
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
        double kembalian = bayar - total2;
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
