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
public class Rejection_Reasons {

    public Rejection_Reasons ( int REJ_ID , String REJ_DESC ) {
        this.REJ_ID = REJ_ID;
        this.REJ_DESC = REJ_DESC;
    }

    public Rejection_Reasons (  ) {
    } 
    
    public int REJ_ID;
    public String REJ_DESC;
}

