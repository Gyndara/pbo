/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.view.MainWindow;
import bazff.view.PaymentPopUp;
import bazff.view.ReceiptPopUp;
import java.awt.Point;
import java.util.Locale;

/**
 *
 * @author bisma
 */
public class TransactionController {
    private MainWindow mainWindow;
    private PaymentPopUp paymentPopUp;
    private ReceiptPopUp receiptPopUp;
    
    public TransactionController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
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
}
