/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.view.MainWindow;
import bazff.view.PaymentPopUp;

/**
 *
 * @author bisma
 */
public class TransactionController {
    private MainWindow mainWindow;
    private PaymentPopUp paymentPopUp;
    
    public TransactionController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }
    
    public void paymentPopUp(){
        paymentPopUp = new PaymentPopUp(mainWindow, this);
        paymentPopUp.setLocationRelativeTo(mainWindow);
        paymentPopUp.setVisible(true);
    }
}
