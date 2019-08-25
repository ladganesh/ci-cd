/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

/**
 *
 * @author Rajesh
 */
public class ProductDR {

    public ProductDR ( int FG_ID , String FG_PART_NO , String UOM ) {
        this.FG_ID = FG_ID;
        this.FG_PART_NO = FG_PART_NO;
        this.UOM = UOM;
    }
    
    public ProductDR ( int FG_ID , String FG_PART_NO , String UOM , double _rate) {
        this.FG_ID = FG_ID;
        this.FG_PART_NO = FG_PART_NO;
        this.UOM = UOM;
        this.rate = _rate;
    }
    public ProductDR ( int FG_ID , String FG_PART_NO , String UOM , double _rate , int _assembled) {
        this.FG_ID = FG_ID;
        this.FG_PART_NO = FG_PART_NO;
        this.UOM = UOM;
        this.rate = _rate;
        this.assembled = _assembled ;
    }
    

    public ProductDR () {
    }

    
    private int assembled;
    public int FG_ID;
    public String FG_PART_NO;
    public String UOM ;
    private double rate ;

    
    public int getFG_ID() {
        return FG_ID;
    }

    public void setFG_ID(int FG_ID) {
        this.FG_ID = FG_ID;
    }

    public String getFG_PART_NO() {
        return FG_PART_NO;
    }

    public void setFG_PART_NO(String FG_PART_NO) {
        this.FG_PART_NO = FG_PART_NO;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public int getAssembled () {
        return assembled;
    }

    public void setAssembled ( int assembled ) {
        this.assembled = assembled;
    }

    public double getRate () {
        return rate;
    }

    public void setRate ( double rate ) {
        this.rate = rate;
    }
    
    
}


