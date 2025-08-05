/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.model;

import bazff.config.Session;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author bisma
 */
public class TransactionModel {
    private Date tanggal;
    private int userId;
    private int total;
    private int bayar;
    private int quantity;
    private String cashierName;
    private List<DetailTransModel> details;

    public TransactionModel(Date tanggal, int userId ,int total, int bayar) {
        this.tanggal = tanggal;
        this.userId = userId;
        this.total = total;
        this.bayar = bayar;
    }
    
    public TransactionModel(Date tanggal, int userId, String cashierName, int total, int quantity) {
        this.tanggal = tanggal;
        this.userId = userId;
        this.cashierName = cashierName;
        this.total = total;
        this.quantity = quantity;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
}


    public List<DetailTransModel> getDetails() {
        return details;
    }

    public void setDetails(List<DetailTransModel> details) {
        this.details = details;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getBayar() {
        return bayar;
    }

    public void setBayar(int bayar) {
        this.bayar = bayar;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
}
