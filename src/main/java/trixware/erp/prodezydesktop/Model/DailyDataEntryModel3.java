/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

import java.util.ArrayList;
import trixware.erp.prodezydesktop.examples.DailyReportForm.* ;

/**
 *
 * @author Rajesh
 */
public class DailyDataEntryModel3{

    public int productId;
    public String productName ;
    public String FromDate;
    public String FromTime;
    public String ToDate;
    public String ToTime;
    public String  Machinename;
    public String MachineID;
    public String QIn;
    public String Qout;
    public String Rejected;
    
    private String  Processname;
    private String ProcessID;
    
    public ArrayList<EmployeeDR> Employee;
    public ArrayList<ProductDR> Products;
    public ArrayList<UOMDR> UOM;
    public ArrayList<Rejection_Reasons> RejRsn;
    
      private int SelShift ;
       public ArrayList<TimeLossModel> selectedtimeLossData = new ArrayList<TimeLossModel>();
    public ArrayList<RejectionModel> selectedtimeRejections = new ArrayList<RejectionModel>();
    
    public int  SelEmployee;
    public int SelMachine;
    public int SelRejRsn;

    private int UomIn;
    private int UomOut;
    private int UomOutRej;
  
    public String rdate ; 
    public String rtdate ;
           
     public String starttime ;
     public String stoptime ; 
    
    
    public DailyDataEntryModel3(){
    
    }


    public DailyDataEntryModel3(String fromDate, String fromTime, String toDate, String toTime, String processname, String processID, String QIn, String qout, String rejected, ArrayList<EmployeeDR> employee, ArrayList<ProductDR> products, ArrayList<Rejection_Reasons> rejrsn) {
        FromDate = fromDate;
        FromTime = fromTime;
        ToDate = toDate;
        ToTime = toTime;
        Machinename = processname;
        MachineID = processID;
        this.QIn = QIn;
        Qout = qout;
        Rejected = rejected;
        Employee = employee;
       Products = products;
        RejRsn = rejrsn;
    }
    
    
    public void setProductId( int productid ){
        productId = productid ;
    }
    
    
    public int getProductId(){
    
        return productId ;
    }
    
    public void setProductName( String productid ){
        productName = productid ;
    }
    
    
    public String getProductName(){
    
        return productName ;
    }

     public String getPFromDate() {
        return rdate;
    }

    public String getPFromTime() {
        return starttime;
    }
    
     public String getPToDate() {
        return rtdate;
    }

    public String getPToTime() {
        return stoptime;
    }
    
    public String getFromDate() {
        return FromDate;
    }

    public String getFromTime() {
        return FromTime;
    }

    public String getToDate() {
        return ToDate;
    }

    public String getToTime() {
        return ToTime;
    }

    public String getMachinename() {
        return Machinename;
    }

    public String getMachineID() {
        return MachineID;
    }

    public String getQIn() {
        return QIn;
    }

    public String getQout() {
        return Qout;
    }

    public String getRejected() {
        return Rejected;
    }

    public int getEmployee() {
        return SelEmployee;
    }

    public int getMachine() {
        return SelMachine;
    }
    
    public int getRej_Reason() {
        return SelRejRsn;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public void setFromTime(String fromTime) {
        FromTime = fromTime;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public void setToTime(String toTime) {
        ToTime = toTime;
    }

    public void setMachinename(String _Machinename) {
        Machinename = _Machinename;
    }

    public void setMachineID(String _MachineID) {
        MachineID = _MachineID;
    }

    public void setQIn(String QIn) {
        this.QIn = QIn;
    }

    public void setQout(String qout) {
        Qout = qout;
    }

    public void setRejected(String rejected) {
        Rejected = rejected;
    }

    public void setEmployee(int employee) {
        SelEmployee = employee;
    }

    public void setMachine(int machine) {
        SelMachine = machine;
    }
    
    public void setRejReason( int rejRsn){
        SelRejRsn = rejRsn ;
    }
    
     public void setPFromDate( String rDate ) {
        rdate = rDate ;
    }

    public void setPFromTime( String startTime) {
       //rtdate = rtDate ;
       starttime = startTime;
    }
    
     public void setPToDate( String rtDate) {
        rtdate = rtDate ;
    }

    public void setPToTime( String stopTime ) {
        stoptime = stopTime ;
    }
    
      public void setShiftData ( int shiftId ) {
        this.SelShift = shiftId;
    }
      
     public void setProcessname(String processname) {
        Processname = processname;
    }

    public void setProcessID(String processID) {
        ProcessID = processID;
    }
      
        public String getProcessname() {
        return Processname;
    }

    public String getProcessID() {
        return ProcessID;
    }
    public void setUomIn(int uin) {
        UomIn = uin;
    }

    public int getUomIn() {
        return UomIn;
    }

    public void setUomOut(int uout) {
        UomOut = uout;
    }

    public int getUomOut() {
        return UomOut;
    }

    public void setUomOutRej(int urej) {
        UomOutRej = urej;
    }

    public int getUomOutRej() {
        return UomOutRej;
    } 

}