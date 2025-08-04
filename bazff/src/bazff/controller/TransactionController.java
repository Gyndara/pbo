/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.config.Database;
import bazff.dao.TransactionDAO;
import bazff.view.MainWindow;
import bazff.view.PaymentPopUp;
import bazff.view.ReceiptPopUp;
import java.awt.Point;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

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
    
    public void paymentPopUp(){
        paymentPopUp = new PaymentPopUp(mainWindow, this);
        paymentPopUp.setLocationRelativeTo(mainWindow);
        paymentPopUp.setVisible(true);
    }
    
    public void receiptPopUp(PaymentPopUp paymentPopUp){
        Point posisi = paymentPopUp.getLocation();
        paymentPopUp.setVisible(false);
        ReceiptPopUp receiptPopUp = new ReceiptPopUp(mainWindow, true);
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
