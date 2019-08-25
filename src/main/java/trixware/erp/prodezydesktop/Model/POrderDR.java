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
public class POrderDR {

    public POrderDR () {
    }

    public POrderDR ( int MC_ID , String MC_NAME, int CUS_ID ) {
        this.ORD_ID = MC_ID;
        this.ORD_NAME = MC_NAME;
        this.CUS_ID = CUS_ID ;
    }

    public int ORD_ID;
    public String ORD_NAME;
    public int CUS_ID ;
}

