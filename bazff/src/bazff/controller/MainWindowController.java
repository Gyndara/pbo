/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.view.AddProduct;
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
    private AddProduct addProduct;

    public MainWindowController(MainWindow window){
        this.ProductDataPanel = new ProductDataPanel(window);
        this.dashBoardPanel = new DashBoardPanel(window);
        this.transactionDataPanel = new TransactionDataPanel(window);
        this.sizeDataView = new SizeDataView(window);
        this.addProduct = new AddProduct(window);
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

    public void setAddProduct(AddProduct addProduct) {
        this.addProduct = addProduct;
    }
    
    public void tampilHalamanProduct(MainWindow window){
        ProductDataPanel panelBaru = new ProductDataPanel(window);
        this.ProductDataPanel = panelBaru;

        // Bersihkan dan tampilkan header
        window.getPanelHeader().removeAll();
        window.getPanelHeader().add(panelBaru.getPanelHeaderProduct());
        window.getPanelHeader().repaint();
        window.getPanelHeader().revalidate();

        // Bersihkan dan tampilkan isi
        window.getMainPanel().removeAll();
        window.getMainPanel().add(panelBaru.getPanelProductData());
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
        // Buat ulang panel baru setiap kali tombol diklik
        SizeDataView panelBaru = new SizeDataView(window);
        this.sizeDataView = panelBaru;

        // Refresh panel header
        window.getPanelHeader().removeAll();
        window.getPanelHeader().add(panelBaru.getPanelHeaderSizeData());
        window.getPanelHeader().revalidate();
        window.getPanelHeader().repaint();

        // Refresh panel isi
        window.getMainPanel().removeAll();
        window.getMainPanel().add(panelBaru.getPanelSizeData());
        window.getMainPanel().revalidate();
        window.getMainPanel().repaint();
    }
    
    public void tampilHalamanAddProduct(MainWindow window){
        window.getPanelHeader().removeAll();
        window.getPanelHeader().repaint();
        window.getPanelHeader().revalidate();
        
        window.getMainPanel().removeAll();
        window.getMainPanel().repaint();
        window.getMainPanel().revalidate();
        
        window.getPanelHeader().add(addProduct.getPanelHeaderAddProduct());
        window.getPanelHeader().repaint();
        window.getPanelHeader().revalidate();
        
        window.getMainPanel().add(addProduct.getPanelAddProduct());
        window.getMainPanel().repaint();
        window.getMainPanel().revalidate();
    }
}
