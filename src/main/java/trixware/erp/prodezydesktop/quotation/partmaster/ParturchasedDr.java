/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.quotation.partmaster;

/**
 *
 * @author Akshay
 */
public class ParturchasedDr {
    private String partid;
    private String partNo;
    private String partName;
    private String partType;   
    private String supplier;
    private String price;
    public ParturchasedDr(String id,String partNo,String partName,String partType,String supplier,String price)
    {
        this.partid=id;
        this.partName=partName;
        this.partNo=partNo;
        this.partType=partType;
        this.supplier=supplier;
        this.price=price;
    }
    public String getPartid() {
        return partid;
    }

    public String getPartNo() {
        return partNo;
    }

    public String getPartName() {
        return partName;
    }

    public String getPartType() {
        return partType;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getPrice() {
        return price;
    }

}
