/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.view.ProductDataPanel;
import bazff.view.SizeDataView;
import bazff.view.TransactionDataPanel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 *
 * @author bisma
 */
public class TableController {
    private SizeDataView sizeDataView;
    private ProductDataPanel productDataPanel;
    private TransactionDataPanel transactionDataPanel;
    
    
    public TableController(ProductDataPanel productDataPanel){
        this.productDataPanel = productDataPanel;
    }
    
    public TableController(SizeDataView sizeDataView) {
        this.sizeDataView = sizeDataView;
    }
    
    public TableController(TransactionDataPanel transactionDataPanel){
        this.transactionDataPanel = transactionDataPanel;
    }
    
    public void setUpTableSize(){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < sizeDataView.getTableSize().getColumnCount(); i++) {
            sizeDataView.getTableSize().getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        sizeDataView.getTableSize().setRowHeight(60);
        JTableHeader header = sizeDataView.getTableSize().getTableHeader();
        header.setFont(new Font("Tahome", Font.BOLD, 20));
    }
    
    public void setUpTableProduct(){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < productDataPanel.getTableProduct().getColumnCount(); i++) {
            productDataPanel.getTableProduct().getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        productDataPanel.getTableProduct().setRowHeight(60);
        JTableHeader header = productDataPanel.getTableProduct().getTableHeader();
        header.setFont(new Font("Tahome", Font.BOLD, 20));
    }
    
    public void setUpTableTransaction(){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < transactionDataPanel.getTransactionTable().getColumnCount(); i++) {
            transactionDataPanel.getTransactionTable().getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        transactionDataPanel.getTransactionTable().setRowHeight(60);
        JTableHeader header = transactionDataPanel.getTransactionTable().getTableHeader();
        header.setFont(new Font("Tahome", Font.BOLD, 20));
    }
}
