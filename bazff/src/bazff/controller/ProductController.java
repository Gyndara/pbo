/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.view.MainWindow;
import bazff.view.ProductDataPanel;
import bazff.view.UpdatePopUp1;
import java.awt.Component;
import java.awt.Window;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author bisma
 */
public class ProductController {
    private ProductDataPanel panelProductData;
    
    public ProductController(){
        
    }
    
    public ProductController(MainWindow window){
        this.panelProductData = new ProductDataPanel(window);
    } 

    public ProductDataPanel getPanelProductData() {
        return panelProductData;
    }    
    
    public void tampilUpdatePopUp1(Component comp){
        Window window = SwingUtilities.getWindowAncestor(comp);

        if (window instanceof JFrame) {
            JFrame frame = (JFrame) window;
            UpdatePopUp1 dialog = new UpdatePopUp1(frame, true);
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        } else {
            System.err.println("Tidak dapat menemukan frame utama.");
        }
    }
}
