/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.config.Database;
import bazff.dao.ProductDAO;
import bazff.model.ProductModel;
import bazff.view.ProductDataPanel;
import bazff.view.SizeDataView;
import bazff.view.TransactionDataPanel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author bisma
 */
public class TableController {
    private SizeDataView sizeDataView;
    private ProductDataPanel productDataPanel;
    private TransactionDataPanel transactionDataPanel;
    private ProductController productController;
    
    
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
        try {
            Connection conn = Database.getKoneksi();
            ProductDAO productDAO = new ProductDAO(conn);
            List<ProductModel> produkList = productDAO.getProductDisplay();

            DefaultTableModel model = (DefaultTableModel) productDataPanel.getTableProduct().getModel();
            model.setRowCount(0); // Bersihkan isi tabel sebelum diisi ulang

            for (ProductModel p : produkList) {
                model.addRow(new Object[]{
                    p.getProductCode(),
                    p.getSkuCode(),
                    p.getProductName(),
                    p.getQuantity(),
                    p.getProductStatus()
                });
            }

        // Center align isi tabel
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < productDataPanel.getTableProduct().getColumnCount(); i++) {
            productDataPanel.getTableProduct().getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Styling tambahan
        productDataPanel.getTableProduct().setRowHeight(60);
        JTableHeader header = productDataPanel.getTableProduct().getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 20));

        } catch (SQLException e) {
            e.printStackTrace(); // atau tampilkan di UI
        }
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
