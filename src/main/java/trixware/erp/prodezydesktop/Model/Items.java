/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Rajesh
 */
public class Items {

    private int itemUID;
    private String itemDesc;
    private String itemCode;
    private String itemSize;
    private String itemUnit;
    private int itemOpeningQty;
    private int itemInwardQty;
    private int itemOutwardQty;
    private int itemClosingQty;

    public Items(String itemName){
        this.itemDesc = itemName;
    }
    
    public void setItemUID(int uid) {
        this.itemUID = uid;
    }

    public int getItemUID() {
        return this.itemUID;
    }

    public void setItemDesc(String itemName) {
        this.itemDesc = itemName;
    }

    public String getItemDesc() {
        return this.itemDesc;
    }
}
