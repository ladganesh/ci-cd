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
public class Batch {
    
   public String batchName;
    public int batchId;
    public int ref_rm_id;

    public int getRef_rm_id() {
        return ref_rm_id;
    }

    public void setRef_rm_id(int ref_rm_id) {
        this.ref_rm_id = ref_rm_id;
    }
    public double batchQty;

    public Batch ( String batchName , int batchId , double batchQty ) {
        this.batchName = batchName;
        this.batchId = batchId;
        this.batchQty = batchQty;
    }

    public Batch () {
    }

    
    public String getBatchName () {
        return batchName;
    }

    public void setBatchName ( String batchName ) {
        this.batchName = batchName;
    }

    public int getBatchId () {
        return batchId;
    }

    public void setBatchId ( int batchId ) {
        this.batchId = batchId;
    }

    public double getBatchQty () {
        return batchQty;
    }

    public void setBatchQty ( double batchQty ) {
        this.batchQty = batchQty;
    }
    
    
}
