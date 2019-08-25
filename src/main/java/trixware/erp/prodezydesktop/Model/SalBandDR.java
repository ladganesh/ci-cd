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
public class SalBandDR {

    public SalBandDR () {
    }

    public SalBandDR ( int MC_ID , String MC_NAME ) {
        this.SB_ID = MC_ID;
        this.SB_NAME = MC_NAME;
    }

    public int SB_ID;
    public String SB_NAME;
}

