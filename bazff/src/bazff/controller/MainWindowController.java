/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.view.MainWindow;
import bazff.view.ProductDataPanel;
import bazff.view.DashBoardPanel;

/**
 *
 * @author bisma
 */
public class MainWindowController {
    private ProductDataPanel ProductDataPanel;
    private DashBoardPanel dashBoardPanel;

    public MainWindowController(MainWindow window){
        this.ProductDataPanel = new ProductDataPanel(window);
        this.dashBoardPanel = new DashBoardPanel(window);
    }
    
    public void setProductDataPanel(ProductDataPanel ProductDataPanel) {
        this.ProductDataPanel = ProductDataPanel;
    }

    public void setDashBoardPanel(DashBoardPanel dashBoardPanel) {
        this.dashBoardPanel = dashBoardPanel;
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

    public void tampilHalamanUtama(MainWindow window) {
        window.getPanelHeader().removeAll();
        window.getPanelHeader().add(dashBoardPanel.getHeaderHomePanel());
        window.getPanelHeader().repaint();
        window.getPanelHeader().revalidate();
        
        window.getMainPanel().removeAll();      
        window.getMainPanel().add(dashBoardPanel.getPanelHome());
        window.getMainPanel().repaint();
        window.getMainPanel().revalidate();
        
    }
}
