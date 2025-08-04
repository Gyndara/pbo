/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bazff.model;

/**
 *
 * @author bisma
 */
public class SizeModel {
    private int id;
    private String sizeName;
    
    
    public SizeModel(){
        
    }
    
    public SizeModel(String sizeName){
        this.sizeName = sizeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
    
}
