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
public class NatureOfEmpDR {

    public NatureOfEmpDR () {
    }

    public NatureOfEmpDR ( int MC_ID , String MC_NAME ) {
        this.NE_ID = MC_ID;
        this.NE_NAME = MC_NAME;
    }

    public int NE_ID;
    public String NE_NAME;
}

