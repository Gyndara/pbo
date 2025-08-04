/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.config.Database;
import bazff.dao.SizeDAO;
import bazff.view.DeleteSizePopUp1;
import bazff.view.MainWindow;
import bazff.view.SizePopUp1;
import bazff.view.SizePopUp2;
import java.sql.SQLException;

/**
 *
 * @author bisma
 */
public class SizeController {
    private MainWindow mainWindow;
    private SizePopUp1 sizePopUp1;
    private DeleteSizePopUp1 deleteSizePopUp1;
    private SizeDAO sizeDao; 
    
    public SizeController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        try {
            this.sizeDao = new SizeDAO(Database.getKoneksi());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void insertSize(String sizeName) {
        try {
            sizeDao.insertSize(sizeName);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteSize(String sizeName) {
        try {
            sizeDao.deleteSize(sizeName);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void addSizeDataPopUp(){
        sizePopUp1 = new SizePopUp1(mainWindow, this);
        sizePopUp1.setLocationRelativeTo(mainWindow);
        sizePopUp1.setVisible(true);
    }
    
    public void deleteSizeDataPopUp(){
        deleteSizePopUp1 = new DeleteSizePopUp1(mainWindow, this);
        deleteSizePopUp1.setLocationRelativeTo(mainWindow);
        deleteSizePopUp1.setVisible(true);
    }
}
