/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.model;

import java.sql.Date;

/**
 *
 * @author bisma
 */
public class TransactionModel {
    private Date tanggal;
    private String cashierName;
    private int total;
    private int quantity;

    public TransactionModel(Date tanggal, String cashierName, int quantity,int total) {
        this.tanggal = tanggal;
        this.cashierName = cashierName;
        this.quantity = quantity;
        this.total = total;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public String getCashierName() {
        return cashierName;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public int getTotal() {
        return total;
    }
}
