/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.view.MainWindow;
import bazff.view.ProductDataPanel;
import bazff.view.DashBoardPanel;
import bazff.view.SizeDataView;
import bazff.view.TransactionDataPanel;

/**
 *
 * @author bisma
 */
public class MainWindowController {
    private ProductDataPanel ProductDataPanel;
    private DashBoardPanel dashBoardPanel;
    private TransactionDataPanel transactionDataPanel;
    private SizeDataView sizeDataView;

    public MainWindowController(MainWindow window){
        this.ProductDataPanel = new ProductDataPanel(window);
        this.dashBoardPanel = new DashBoardPanel(window);
        this.transactionDataPanel = new TransactionDataPanel(window);
        this.sizeDataView = new SizeDataView(window);
    }

    public void setTransactionDataPanel(TransactionDataPanel transactionDataPanel) {
        this.transactionDataPanel = transactionDataPanel;
    }
    
    public void setProductDataPanel(ProductDataPanel ProductDataPanel) {
        this.ProductDataPanel = ProductDataPanel;
    }

    public void setDashBoardPanel(DashBoardPanel dashBoardPanel) {
        this.dashBoardPanel = dashBoardPanel;
    }

    public void setSizeDataView(SizeDataView sizeDataView) {
        this.sizeDataView = sizeDataView;
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
    
    public void tampilHalamanTransaksi(MainWindow window){
        window.getPanelHeader().removeAll();
        window.getPanelHeader().repaint();
        window.getPanelHeader().revalidate();
        
        window.getMainPanel().removeAll();
        window.getMainPanel().repaint();
        window.getMainPanel().revalidate();
        
        window.getPanelHeader().add(transactionDataPanel.getPanelHeaderTransaction());
        window.getPanelHeader().repaint();
        window.getPanelHeader().revalidate();
        
        window.getMainPanel().add(transactionDataPanel.getPanelTransaction());
        window.getMainPanel().repaint();
        window.getMainPanel().revalidate();
    }
    
    public void tampilHalamanSizeData(MainWindow window){
        window.getPanelHeader().removeAll();
        window.getPanelHeader().repaint();
        window.getPanelHeader().revalidate();
        
        window.getMainPanel().removeAll();
        window.getMainPanel().repaint();
        window.getMainPanel().revalidate();
        
        window.getPanelHeader().add(sizeDataView.getPanelHeaderSizeData());
        window.getPanelHeader().repaint();
        window.getPanelHeader().revalidate();
        
        window.getMainPanel().add(sizeDataView.getPanelSizeData());
        window.getMainPanel().repaint();
        window.getMainPanel().revalidate();
    }
}
