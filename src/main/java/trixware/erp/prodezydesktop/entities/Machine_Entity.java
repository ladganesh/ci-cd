/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Rajesh
 */
public class Machine_Entity {

    int MCH_ID;
    String MACHINE_NO;
    String MAKE;
    double OP_RATE_HR;
    int AVL_HRS;
    int DAVLHRS;
    String CREATED_ON;
    String EDITED_ON;
    int EDITED_BY;

    public Machine_Entity () {
    }

    public Machine_Entity (  String MACHINE_NO , String MAKE , double OP_RATE_HR , int AVL_HRS , int DAVLHRS , String CREATED_ON , String EDITED_ON , int EDITED_BY ) {
        
        this.MACHINE_NO = MACHINE_NO;
        this.MAKE = MAKE;
        this.OP_RATE_HR = OP_RATE_HR;
        this.AVL_HRS = AVL_HRS;
        this.DAVLHRS = DAVLHRS;
        this.CREATED_ON = CREATED_ON;
        this.EDITED_ON = EDITED_ON;
        this.EDITED_BY = EDITED_BY;
    }

    public int getMCH_ID () {
        return MCH_ID;
    }

    public void setMCH_ID ( int MCH_ID ) {
        this.MCH_ID = MCH_ID;
    }

    public String getMACHINE_NO () {
        return MACHINE_NO;
    }

    public void setMACHINE_NO ( String MACHINE_NO ) {
        this.MACHINE_NO = MACHINE_NO;
    }

    public String getMAKE () {
        return MAKE;
    }

    public void setMAKE ( String MAKE ) {
        this.MAKE = MAKE;
    }

    public double getOP_RATE_HR () {
        return OP_RATE_HR;
    }

    public void setOP_RATE_HR ( double OP_RATE_HR ) {
        this.OP_RATE_HR = OP_RATE_HR;
    }

    public int getAVL_HRS () {
        return AVL_HRS;
    }

    public void setAVL_HRS ( int AVL_HRS ) {
        this.AVL_HRS = AVL_HRS;
    }

    public int getDAVLHRS () {
        return DAVLHRS;
    }

    public void setDAVLHRS ( int DAVLHRS ) {
        this.DAVLHRS = DAVLHRS;
    }

    public String getCREATED_ON () {
        return CREATED_ON;
    }

    public void setCREATED_ON ( String CREATED_ON ) {
        this.CREATED_ON = CREATED_ON;
    }

    public String getEDITED_ON () {
        return EDITED_ON;
    }

    public void setEDITED_ON ( String EDITED_ON ) {
        this.EDITED_ON = EDITED_ON;
    }

    public int getEDITED_BY () {
        return EDITED_BY;
    }

    public void setEDITED_BY ( int EDITED_BY ) {
        this.EDITED_BY = EDITED_BY;
    }

    
    public Map<String, Object> toMap(   ) {
        HashMap<String, Object> result = new HashMap<>();
        result.put( "machine_NO",  MACHINE_NO);
        result.put( "make",  MAKE);
        result.put( "op_RATE_HR",  OP_RATE_HR);
        result.put( "avl_HRS", AVL_HRS );
         result.put( "davlhrs",  DAVLHRS);
          result.put( "created_ON", CREATED_ON );
           result.put( "edited_ON", EDITED_ON );
            result.put( "edited_BY", EDITED_BY );
            
            result.put( "bed_SIZE", "" );
            result.put( "cr_MAINT_RTG", "" );
            result.put( "cr_MCH_OWN", "" );
            result.put( "heater_QTY", "" );
            result.put( "instr_QTY", "" );
            result.put( "mach_OWN_IMG", "" );
            result.put( "mch_BRKD_RCD", "" );
            result.put( "mch_IMG", "" );
            result.put( "pm_CLIT", "" );
            result.put( "pm_ELE", "" );
            result.put( "pm_HYD", "" );
            result.put( "pm_MEC", "" );
            result.put( "pm_PNM", "" );
            result.put( "prss_GAUGE", "" );
            result.put( "temp_CTRL", "" );
            result.put( "timer", "" );
            result.put( "yr_OF_COMM", "" );
            
       
        return result;
    }
    
}
