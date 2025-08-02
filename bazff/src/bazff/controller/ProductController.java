/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.view.DeleteProductPopUp;
import bazff.view.MainWindow;
import bazff.view.SizePopUp2;
import bazff.view.SizePopUp3;
import bazff.view.UpdatePopUp1;
import bazff.view.UpdatePopUp2;
import java.awt.Point;
/**
 *
 * @author bisma
 */
public class ProductController {
    private MainWindow mainWindow;
    private UpdatePopUp1 dialog1;
    private DeleteProductPopUp dialog2;
    private SizePopUp2 sizePopUp2;

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
        dialog1.setVisible(false);
        UpdatePopUp2 dialog2 = new UpdatePopUp2(mainWindow, true);
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
        sizePopUp2.setVisible(false);
        SizePopUp3 sizePopUp3 = new SizePopUp3(mainWindow, true);
        sizePopUp3.setLocation(posisi);
        sizePopUp3.setVisible(true);
    }
}
