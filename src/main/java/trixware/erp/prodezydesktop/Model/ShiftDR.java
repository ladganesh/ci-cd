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

public class ShiftDR {

    public ShiftDR ( int SHIFT_ID , String SHIFT_NAME ) {
        this.SHIFT_ID = SHIFT_ID;
        this.SHIFT_NAME = SHIFT_NAME;
    }

    public ShiftDR (  ) {
        
    } 
    
    public int SHIFT_ID;
    public String SHIFT_NAME;
}