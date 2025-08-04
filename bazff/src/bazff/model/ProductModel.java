/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.model;

import java.sql.Connection;

/**
 *
 * @author bisma
 */
public class ProductModel {
    private String productCode;
    private String productName;
    private String skuCode;
    private int quantity;
    private String productStatus;
    private int sizeId;
    private int productPrice;
    
    public ProductModel(){
        
    }
    
    public ProductModel(String productCode, String productName, String skuCode, int quantity, String productStatus) {
        this.productCode = productCode;
        this.productName = productName;
        this.skuCode = skuCode;
        this.quantity = quantity;
        this.productStatus = productStatus;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
    
    @Override
    public String toString() {
        return productName + " (Rp. " + productPrice + ")";
    }
}
