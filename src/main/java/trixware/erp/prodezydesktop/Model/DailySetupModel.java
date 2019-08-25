/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Rajesh
 */

public class DailySetupModel  {

    int SM_ID, empId, fgId, shiftId, rawMatId,  machineId ;
    boolean isMouldClean, isPersonSkilled, isMachineClit, areToolsAvl  ;
    String  setupDate, setuoTime  ;
    double rawMatQty ;

    public DailySetupModel () {
    }
    
    public  DailySetupModel getDailySetupInstance ( ResultSet rs ) {
        
        DailySetupModel dsm1  = null ;
        
        try{
            SM_ID = rs.getInt ( 1) ;
            empId = rs.getInt ( 2) ;
            fgId = rs.getInt ( 3) ;
            shiftId = rs.getInt ( 4) ;
            rawMatId = rs.getInt ( 5) ;
            rawMatQty = rs.getDouble ( 6) ;
            machineId = rs.getInt ( 7 ) ;

            isMouldClean = Boolean.parseBoolean ( rs.getString(8)  );
            isPersonSkilled  = Boolean.parseBoolean ( rs.getString(9) ) ;
            isMachineClit  = Boolean.parseBoolean ( rs.getString(10)  ) ;
            areToolsAvl = Boolean.parseBoolean ( rs.getString(11) ) ;
            
            setupDate = rs.getString(12) ;
            setuoTime = rs.getString(13)  ;
            
            dsm1 = new DailySetupModel (   SM_ID, empId, fgId, shiftId, rawMatId, rawMatQty, machineId , isMouldClean, isPersonSkilled, isMachineClit, areToolsAvl  ,  setupDate, setuoTime    );
            
        }catch( SQLException e ){
            
        }
        return dsm1;
    }

    public DailySetupModel ( int SM_ID , int empId , int fgId , int shiftId , int rawMatId , double rawMatQty , int machineId , boolean isMouldClean , boolean isPersonSkilled , boolean isMachineClit , boolean areToolsAvl , String setupDate , String setuoTime ) {
        this.SM_ID = SM_ID;
        this.empId = empId;
        this.fgId = fgId;
        this.shiftId = shiftId;
        this.rawMatId = rawMatId;
        this.rawMatQty = rawMatQty;
        this.machineId = machineId;
        this.isMouldClean = isMouldClean;
        this.isPersonSkilled = isPersonSkilled;
        this.isMachineClit = isMachineClit;
        this.areToolsAvl = areToolsAvl;
        this.setupDate = setupDate;
        this.setuoTime = setuoTime;
    }

    
    
    public int getSM_ID () {
        return SM_ID;
    }

    public void setSM_ID ( int SM_ID ) {
        this.SM_ID = SM_ID;
    }

    public int getEmpId () {
        return empId;
    }

    public void setEmpId ( int empId ) {
        this.empId = empId;
    }

    public int getFgId () {
        return fgId;
    }

    public void setFgId ( int fgId ) {
        this.fgId = fgId;
    }

    public int getShiftId () {
        return shiftId;
    }

    public void setShiftId ( int shiftId ) {
        this.shiftId = shiftId;
    }

    public int getRawMatId () {
        return rawMatId;
    }

    public void setRawMatId ( int rawMatId ) {
        this.rawMatId = rawMatId;
    }

    public double getRawMatQty () {
        return rawMatQty;
    }

    public void setRawMatQty ( double rawMatQty ) {
        this.rawMatQty = rawMatQty;
    }

    public int getMachineId () {
        return machineId;
    }

    public void setMachineId ( int machineId ) {
        this.machineId = machineId;
    }

    public boolean isIsMouldClean () {
        return isMouldClean;
    }

    public void setIsMouldClean ( boolean isMouldClean ) {
        this.isMouldClean = isMouldClean;
    }

    public boolean isIsPersonSkilled () {
        return isPersonSkilled;
    }

    public void setIsPersonSkilled ( boolean isPersonSkilled ) {
        this.isPersonSkilled = isPersonSkilled;
    }

    public boolean isIsMachineClit () {
        return isMachineClit;
    }

    public void setIsMachineClit ( boolean isMachineClit ) {
        this.isMachineClit = isMachineClit;
    }

    public boolean isAreToolsAvl () {
        return areToolsAvl;
    }

    public void setAreToolsAvl ( boolean areToolsAvl ) {
        this.areToolsAvl = areToolsAvl;
    }

    public String getSetupDate () {
        return setupDate;
    }

    public void setSetupDate ( String setupDate ) {
        this.setupDate = setupDate;
    }

    public String getSetuoTime () {
        return setuoTime;
    }

    public void setSetuoTime ( String setuoTime ) {
        this.setuoTime = setuoTime;
    }
    
    
    
}
