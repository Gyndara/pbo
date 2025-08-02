/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.view.MainWindow;
import bazff.view.SizePopUp1;
import bazff.view.SizePopUp2;

/**
 *
 * @author bisma
 */
public class SizeController {
    private MainWindow mainWindow;
    private SizePopUp1 sizePopUp1;
    private SizePopUp2 sizePopUp2;
    
    public SizeController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }
    
    public void addSizeDataPopUp(){
        sizePopUp1 = new SizePopUp1(mainWindow, this);
        sizePopUp1.setLocationRelativeTo(mainWindow);
        sizePopUp1.setVisible(true);
    }
}
