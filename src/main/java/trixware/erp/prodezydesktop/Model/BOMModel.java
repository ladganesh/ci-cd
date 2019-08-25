/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

import java.util.ArrayList;

/**
 *
 * @author Rajesh
 */
public class BOMModel {
    
    int fgid ;
    int rmid;
    double qty;
    ArrayList<UOMDR> uomList ;
    String rmName;
    int selectedUOM ;

    public BOMModel(){
        
    }
    
    public int getFgid () {
        return fgid;
    }

    public void setFgid ( int fgid ) {
        this.fgid = fgid;
    }

    public int getRmid () {
        return rmid;
    }

    public void setRmid ( int rmid ) {
        this.rmid = rmid;
    }

    public double getQty () {
        return qty;
    }

    public void setQty ( double qty ) {
        this.qty = qty;
    }

   
     public String getRMName () {
        return rmName;
    }

    public void setRMName ( String _rmname ) {
        this.rmName = _rmname;
    }

    public ArrayList<UOMDR> getUomList () {
        return uomList;
    }

    public void setUomList ( ArrayList<UOMDR> uomList ) {
        this.uomList = uomList;
    }


    public void setUom ( int selectedUOM ) {
        this.selectedUOM = selectedUOM;
    }
    public int getUom (  ) {
        return selectedUOM ;
    }

    
    public BOMModel ( int fgid , int rmid , double qty , ArrayList<UOMDR> uomlist , String _fgname) {
        this.fgid = fgid;
        this.rmid = rmid;
        this.qty = qty;
        this.uomList = uomlist;
        this.rmName = _fgname ;
    }
    
    
    
}
