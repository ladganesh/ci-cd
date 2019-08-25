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
public class DeliveryScheduleModel {
    
    private int del_sch_id, sales_po_id, ref_po_det_id ;
    private double sch_qty ;
    private double target ;
    private String date ;
    private boolean status ;

    public int getDel_sch_id () {
        return del_sch_id;
    }

    public void setDel_sch_id ( int del_sch_id ) {
        this.del_sch_id = del_sch_id;
    }

    public int getSales_po_id () {
        return sales_po_id;
    }

    public void setSales_po_id ( int sales_po_id ) {
        this.sales_po_id = sales_po_id;
    }

    public int getRef_po_det_id () {
        return ref_po_det_id;
    }

    public void setRef_po_det_id ( int ref_po_det_id ) {
        this.ref_po_det_id = ref_po_det_id;
    }

    public double getQty () {
        return sch_qty;
    }

    public void setQty ( double qty ) {
        this.sch_qty = qty;
    }

    public String getDate () {
        return date;
    }

    public void setDate ( String date ) {
        this.date = date;
    }

    public boolean isStatus () {
        return status;
    }

    public void setStatus ( boolean status ) {
        this.status = status;
    }

    public double getTarget () {
        return target;
    }

    public void setTarget ( double target ) {
        this.target = target;
    }
    
    
    
    
}
