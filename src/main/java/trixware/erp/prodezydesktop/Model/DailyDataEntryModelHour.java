/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

import java.util.ArrayList;
import trixware.erp.prodezydesktop.examples.DailyReportForm.* ;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 *
 * @author Dell
 * Created By mayur 
 * Adding the values in getter setter method for hourly screen
 */
public class DailyDataEntryModelHour{

    public int productId;
    public String FromDate;
      public String FromTime;
//    public String ToDate;
    public String ToTime;
    private String  Processname;
    public int ProcessID;
    public String Machinename;
    public int MachineID;
    public String TargetQTY;
    public String NoOfOp;
    public String actualQTY;
    //public String QIn;
    //public String Qout;
    public String Rejected;
    public ArrayList<EmployeeDR> Employee;
    public ArrayList<MachineDR> Machine;
    public ArrayList<ProcessDR> Processes;
    public ArrayList<ProductDR> Products;

    public ArrayList<Rejection_Reasons> RejRsn;
    
    
    public int  SelEmployee;
    public int SelMachine;
    public int SelProduct;
    public int SelProcess; 
    public int SelRejRsn;
    private int SelShift ;
    public ArrayList<TimeLossModel> selectedtimeLossData = new ArrayList<TimeLossModel>();
    public ArrayList<RejectionModel> selectedtimeRejections = new ArrayList<RejectionModel>();

    public  ArrayList<ShiftDRHour> shiftData ;
    
    public String rdate ; 
    public String rtdate ;
           
    public String starttime ;
    public String stoptime ; 
    
    private int totalTimeLossInMinutes = 0;
    private double totalTimeLossInHours = 0.0 ;
    
    private long totalTimeInMinutes = 0 ;
    private double totalTimeInHours = 0.0 ;
    
    SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-MM-dd" );
    
    public DailyDataEntryModelHour(){
    
    }

    public DailyDataEntryModelHour(String fromDate, String fromTime,String toTime,String rejected, String TargetQTY, String NoOfOp, String actualQTY, ArrayList<ProcessDR> processes,ArrayList<EmployeeDR> employee, ArrayList<MachineDR> machine,ArrayList<ProductDR> products, ArrayList<Rejection_Reasons> rejrsn) {
        FromDate = fromDate;
        FromTime = fromTime;
        //ToDate = toDate;
        ToTime = toTime;
        //Processname = processname;
        Processes = processes;
        this.TargetQTY=TargetQTY;
        this.NoOfOp=NoOfOp;
        this.actualQTY=actualQTY;
        //this.QIn = QIn;
        //Qout = qout;
        Rejected = rejected;
        Employee = employee;
        Machine = machine;
        Products=products;
        RejRsn = rejrsn;
    }

    public String getActualQTY() {
        return actualQTY;
    }

    public void setActualQTY(String actualQTY) {
        this.actualQTY = actualQTY;
    }

    public String getTargetQTY() {
        return TargetQTY;
    }

    public void setTargetQTY(String TargetQTY) {
        this.TargetQTY = TargetQTY;
    }

    public String getNoOfOp() {
        return NoOfOp;
    }

    public void setNoOfOp(String NoOfOp) {
        this.NoOfOp = NoOfOp;
    }
     public void setFrmTime(String fromTime) {
        FromTime = fromTime;
    }
    
    public void setToTime(String toTime) {
        ToTime = toTime;
    }
    public String getFrmTime() {
        return FromTime;
    }
    public String getToTime() {
        return ToTime;
    }
    public void setProductId( int productid ){
        SelProduct = productid ;
    }
    public int getProductId(){
    
        return SelProduct ;
    }
    public String getFromDate() {
        return FromDate;
    }
    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }
//    public void setEmployee(int employee) {
//        SelEmployee = employee;
//    }
//    
//    public int getEmployee() {
//        return SelEmployee;
//    }
    
//    public void setMachineID ( int mchID ) {
//        SelMachine = mchID;
//    }
//    
//    public int  getMachineID (  ) {
//        return SelMachine ;
//    }
    
//     public int getProcessID() {
//        return SelProcess;
//    }
//    public void setProcessID ( int ProcessID ) {
//        SelProcess = ProcessID;
//    }
   
//    public String getPFromDate() {
//        return rdate;
//    }
//    public String getPFromTime() {
//        return starttime;
//    }
//    public String getPToDate() {
//        return rtdate;
//    }
//    public String getPToTime() {
//        return stoptime;
//    }
    
    
//    public String getToDate() {
//        return ToDate;
//    }
//    public String getToTime() {
//        return ToTime;
//    }
//    public String getQIn() {
//        return QIn;
//    }
//    public String getQout() {
//        return Qout;
//    }
    public String getRejectedrea() {
        return Rejected;
    }
    
//    public String getProcessname() {
//        return Processname;
//    }
//
//    public void setProcessname ( String Processname ) {
//        this.Processname = Processname;
//    }

   
//     public void setMachinename ( String mchname ) {
//        this.Machinename = mchname;
//    }

//    public int getRej_Reason() {
//        return SelRejRsn;
//    }

    

//    public void setToDate(String toDate) {
//        ToDate = toDate;
//    }
//
//    public void setToTime(String toTime) {
//        ToTime = toTime;
//    }

//    public void setQIn(String QIn) {
//        this.QIn = QIn;
//    }
//
//    public void setQout(String qout) {
//        Qout = qout;
//    }
//
    public void setRejectedrea(String rejected) {
        Rejected = rejected;
    }

//    public void setRejReason( int rejRsn){
//        SelRejRsn = rejRsn ;
//    }
    
//     public void setPFromDate( String rDate ) {
//        rdate = rDate ;
//    }
//
//    public void setPFromTime( String startTime) {
//       //rtdate = rtDate ;
//       starttime = startTime;
//    }
    
//     public void setPToDate( String rtDate) {
//        rtdate = rtDate ;
//    }
//
//    public void setPToTime( String stopTime ) {
//        stoptime = stopTime ;
//    }
//    
//    
//    public int getShiftID() {
//        return SelShift ;
//    }
//
//    public void setShiftData ( int shiftId ) {
//        this.SelShift = shiftId;
//    }

    public void setTimeLossData(  ArrayList<TimeLossModel> _timeLossData ){
        selectedtimeLossData = _timeLossData;
    }
    
    public int getTotalTimeLossInMinutes () {
        return totalTimeLossInMinutes;
    }

    public void setTotalTimeLossInMinutes ( int totalTimeLossInMinutes ) {
        this.totalTimeLossInMinutes = totalTimeLossInMinutes;
    }

//    public double getTotalTimeLossInHours () {
//        return totalTimeLossInHours;
//    }

    public void setRejectionData(  ArrayList<RejectionModel> _rejectionData ){
        selectedtimeRejections = _rejectionData;
    }
    
    public ArrayList<RejectionModel> getRejectionData(){
        return selectedtimeRejections ;
    }
  
    public void setTotalTimeLossInHours ( double totalTimeLossInHours ) {
        this.totalTimeLossInHours = totalTimeLossInHours;
    }
    
    public double getTotalTimeLossInHours( ){
    
                if(totalTimeLossInMinutes > 720 ){
                    JOptionPane.showMessageDialog ( null, "Maximum time loss in minutes for single shift can be 720 minutes only");
                    totalTimeLossInMinutes = 720 ;
                }
                
                DecimalFormat df = new DecimalFormat("#.##");
                double hr = 0.0, min = 0.0 ;
                double hr2 = 0.0, min2 = 0.0 ;
                if( totalTimeLossInMinutes > 59  ){
                     hr = (totalTimeLossInMinutes / 60) ; 
                     min = Double.parseDouble(  df.format( Double.parseDouble(df.format( totalTimeLossInMinutes % 60 ) )/60 )   );                    
                }     
                else{
                    hr = Double.parseDouble(  df.format( Double.parseDouble(df.format( totalTimeLossInMinutes % 60 ) )/60 )   );  
                }
                
                return hr+min ;

        
    }

    public long getTotalTimeInMinutes () {
        return totalTimeInMinutes;
    }

    public void setTotalTimeInMinutes ( int totalTimeInMinutes ) {
        this.totalTimeInMinutes = totalTimeInMinutes;
    }

//    public double getTotalTimeInHours () {
//        return totalTimeInHours;
//    }

    public void setTotalTimeInHours ( double totalTimeInHours ) {
        this.totalTimeInHours = totalTimeInHours;
    }
    
    public double getTotalTimeInHours (  ) {

            DecimalFormat df = new DecimalFormat("#.##");
            long totalMin;
            double totalHrs , min2 = 0.0 ;

            String fromTime = "";
            String toTime = "";
            
           //try{ 
            String sDate1=getFromDate (); 
             SimpleDateFormat FormatYearTime=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
             SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date dt=new Date();
            try{
                  dt=FormatYearTime.parse(getFromDate());
               }catch(ParseException ex){//JOptionPane.showMessageDialog(null, "Date error ");
               }
            
            String fdate,tdate;
            fdate=(sdf.format(dt));
            tdate=(sdf.format(dt));
            
            fromTime = fdate+" " + getFrmTime ();
            toTime = tdate+" " + getToTime ();
            
            //Date date1=new SimpleDateFormat("yyyy-MM-dd").parse( getFromDate()); 
             // fromTime = new SimpleDateFormat("MMM dd, yyyy").format(date1)+" " + getFrmTime ();
            //fromTime = new SimpleDateFormat("yyyy-MM-dd").format(date1)+" " + getFrmTime ();
              //date1=new SimpleDateFormat("yyyy-MM-dd").parse( getFromDate ()); 
             //toTime = new SimpleDateFormat("MMM dd, yyyy").format(date1)+" " + getToTime () ;
                //toTime =new SimpleDateFormat("yyyy-MM-dd").format(date1)+" " + getToTime () ;
             
           //}catch( ParseException e ){
//               System.out.println ( ""+e.getMessage () );
//           }

             LocalDateTime d1 = null , d2 = null ;
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            //java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);
            
                d1 = LocalDateTime.parse(   fromTime , formatter);
                d2 = LocalDateTime.parse(   toTime, formatter);
           
            
            Duration difference = Duration.between(d1, d2);

            totalMin = difference.toMinutes ();
            totalTimeInMinutes = totalMin ;

             if( totalMin > 59  ){
                 totalHrs  = (totalMin / 60) ; 
                 min2 = Double.parseDouble(  df.format( Double.parseDouble(df.format( totalMin % 60 ) )/60 )   );                    
            }     
            else{
               totalHrs = Double.parseDouble(  df.format( Double.parseDouble(df.format( totalMin % 60 ) )/60 )   );        
            }

            return totalHrs+min2;    
    }
    
}