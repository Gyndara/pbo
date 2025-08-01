/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.controller;

import bazff.view.ShoppingCartView;

/**
 *
 * @author bisma
 */
public class CartController {
    private int tambahBarang;
    private int kurangBarang;
    
    public void setTambahBarang(int tambahBarang) {
        this.tambahBarang = tambahBarang;
    }

    public int getTambahBarang() {
        return tambahBarang;
    }

    public int getKurangBarang() {
        return kurangBarang;
    }

    public void setKurangBarang(int kurangBarang) {
        this.kurangBarang = kurangBarang;
    }
    
    public void tambahBarang(ShoppingCartView form){
        String current = form.getjTxtTambahBarang().getText();
        this.tambahBarang = Integer.valueOf(current);
        
        this.setTambahBarang(this.tambahBarang + 1);
        
        form.getjTxtTambahBarang().setText(String.valueOf(this.getTambahBarang()));
    }

    public void kurangBarang(ShoppingCartView form){
        String current = form.getjTxtTambahBarang().getText();
        this.kurangBarang = Integer.valueOf(current);
        
        if (this.kurangBarang > 0) {
            this.setKurangBarang(this.kurangBarang - 1);
        } else {
            this.setKurangBarang(0); // Opsional, hanya untuk memastikan tetap 0
        }
        
        form.getjTxtTambahBarang().setText(String.valueOf(this.getKurangBarang()));
    }
    
}
