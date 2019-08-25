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
public class DesignationDR {

    public DesignationDR () {
    }

    public DesignationDR ( int MC_ID , String MC_NAME ) {
        this.DS_ID = MC_ID;
        this.DS_NAME = MC_NAME;
    }

    public int DS_ID;
    public String DS_NAME;
}

