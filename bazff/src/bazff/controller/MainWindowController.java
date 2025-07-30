/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.view.MainWindow;
import bazff.view.ProductDataPanel;

/**
 *
 * @author bisma
 */
public class MainWindowController {
    private ProductDataPanel ProductDataPanel;

    public MainWindowController(MainWindow window){
        this.ProductDataPanel = new ProductDataPanel(window);
    }
    
    public void setProductDataPanel(ProductDataPanel ProductDataPanel) {
        this.ProductDataPanel = ProductDataPanel;
    }
    
    
    public void tampilHalamanProduct(MainWindow window){
        //mengubah panel header
        window.getPanelHeader().removeAll();
        window.getPanelHeader().repaint();;
        window.getPanelHeader().revalidate();
        //mengubah panel main
        window.getMainPanel().removeAll();
        window.getMainPanel().repaint();
        window.getMainPanel().revalidate();
        
        window.getPanelHeader().add(ProductDataPanel.getPanelHeaderProduct());
        window.getPanelHeader().repaint();
        window.getPanelHeader().revalidate();
        
        window.getMainPanel().add(ProductDataPanel.getPanelProductData());
        window.getMainPanel().repaint();
        window.getMainPanel().revalidate();
    }
}
