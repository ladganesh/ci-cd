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

public class ShiftDRHour {

    public ShiftDRHour ( int SHIFT_ID , String SHIFT_NAME ,String fromtime,String totime) {
        this.SHIFT_ID = SHIFT_ID;
        this.SHIFT_NAME = SHIFT_NAME;
        this.fromtime=fromtime;
        this.totime=totime;
        
    }

    public ShiftDRHour (  ) {
        
    } 
    
    public int SHIFT_ID;
    public String SHIFT_NAME;
    public String  fromtime;
    public String totime;

   
}