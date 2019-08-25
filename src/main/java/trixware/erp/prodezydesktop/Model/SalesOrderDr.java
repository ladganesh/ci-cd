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
public class SalesOrderDr 
{

    public SalesOrderDr () {
    }

 
    public int PO_ID;
    public String order_no;

    public String getOrder_no() 
    {
        return order_no;
    }

    public void setOrder_no(String order_no) 
    {
        this.order_no = order_no;
    }

    public int getPO_ID() {
        return PO_ID;
    }

    public void setPO_ID(int PO_ID) {
        this.PO_ID = PO_ID;
    }

    public String getPo_date() {
        return po_date;
    }

    public void setPo_date(String po_date) {
        this.po_date = po_date;
    }
    public String po_date;
  
}

