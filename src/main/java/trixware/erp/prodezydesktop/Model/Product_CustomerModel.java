/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

/**
 *
 * @author dell
 */
public class Product_CustomerModel {
    
    int totalQty;
    
    int tlQtyvalue;
    int prod_typeid;
    String prod_nm;
    String prod_size;

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public int getTlQtyvalue() {
        return tlQtyvalue;
    }

    public void setTlQtyvalue(int tlQtyvalue) {
        this.tlQtyvalue = tlQtyvalue;
    }

    public int getProd_typeid() {
        return prod_typeid;
    }

    public void setProd_typeid(int prod_typeid) {
        this.prod_typeid = prod_typeid;
    }

    public String getProd_nm() {
        return prod_nm;
    }

    public void setProd_nm(String prod_nm) {
        this.prod_nm = prod_nm;
    }

    public String getProd_size() {
        return prod_size;
    }

    public void setProd_size(String prod_size) {
        this.prod_size = prod_size;
    }
    
    
}
