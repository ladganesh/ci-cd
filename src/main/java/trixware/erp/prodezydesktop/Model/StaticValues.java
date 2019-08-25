/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.ErrorLogWriter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import trixware.erp.prodezydesktop.examples.SalesMaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.web_services.WebAPITester;


/**
 *
 * @author Rajesh
 */
public class StaticValues {

    public static SimpleDateFormat sdf2 = new SimpleDateFormat ( "dd-MMM-yyyy HH:mm a" );
    public static ArrayList<String[]> resourceMap = new ArrayList<String[]> ();
    //public static ArrayList<String[]> DBTablesDesc = new ArrayList<String[]>();
    public static DatabaseReference abc = null ;
    public static FirebaseDatabase database = null ;
    public static ErrorLogWriter writer = new ErrorLogWriter();

    private static String AppMode = "both";  //  options api, local, both
    private static String header = "" ;
    private static String apiURL = ""; 
    
    public static SalesMaster sm = null;    
  
        private static Date reportFromDate = null, reportToDate =null  ;
    private static  String reportTypeString ;
    private static  int reportTypeNumber= -1 ;

    public static  Date getReportFromDate () {
        
       // if(reportFromDate==null){
       //     reportFromDate = Date.from (  Calendar.getInstance ().getTime ().toInstant () );
       // }
        return reportFromDate;
    }

    public static  void setReportFromDate ( Date _reportFromDate ) {
        reportFromDate = _reportFromDate;
    }

    public static  Date getReportToDate () {
      //  if(reportToDate==null){
     //       reportToDate = Date.from (  Calendar.getInstance ().getTime ().toInstant () );
    //    }
        return reportToDate;
    }

    public static  void setReportToDate ( Date _reportToDate ) {
        reportToDate = _reportToDate;
    }

    public static  String getReportTypeString () {
        return reportTypeString;
    }

    public static  void setReportTypeString ( String _reportTypeString ) {
        reportTypeString = _reportTypeString;
    }

    public static  int getReportTypeNumber () {
        return reportTypeNumber;
    }

    public  static void setReportTypeNumber ( int _reportTypeNumber ) {
        reportTypeNumber = _reportTypeNumber;
    }
    
    public static  String getAppMode(){
        return AppMode ;
    }
    
    public static void setHeader ( String _header){
        header = _header ;
    }
    
    public static String getHeader(){
        
            return header ;
    }

    public static String getApiURL () {
        return apiURL;
    }

    public static void setApiURL ( String apiURL ) {
        StaticValues.apiURL = apiURL;
    }
        
    public static String[] UOMS = new String[] {
        "TON" ,
        "QUINTAL" ,
        "KG" ,
        "GMS" ,
        "MGMS" ,
        "MTR" ,
        "CM" ,
        "MM" ,
        "INCH" ,
        "FT" ,
        "SQFT" ,
        "SQMTR" ,
        "SQCM" ,
        "SQMM" ,
        "LTR" ,
        "ML" };

    public static ArrayList<String[]> UOM = null;
    public static void setUOMName(){
                String result2 =  WebAPITester.prepareWebCall ( "unitmeas", StaticValues.getHeader ()   , "");
                HashMap<String, Object> map = new HashMap<String, Object>();
                JSONObject jObject = new JSONObject( result2 );
                Iterator<?> keys = jObject.keys();
                while( keys.hasNext() ){
                    String key = (String)keys.next();
                    Object value = jObject.get ( key ) ; 
                    map.put(key, value);
                }
                JSONObject st = (JSONObject) map.get ( "data" );
                JSONArray records = st.getJSONArray ( "records");
                JSONObject emp = null;
                UOM = new ArrayList<String[]> ();
                for ( int i = 0 ; i < records.length () ; i ++ ) {
                    emp = records.getJSONObject ( i);
                    UOM.add( new String[]{ emp.get ( "UOM_ID" ).toString (), emp.get ( "UOM" ).toString () }   ); 
                }
    }
    
    public static String getUOMName( int UOM_ID ){
        
        if( UOM!=null && UOM.size()>0  ){
            for ( int i = 0 ; i < UOM.size () ; i ++ ) {
                 if(  Integer.parseInt( UOM.get(i)[0] ) == UOM_ID  ){
                     return UOM.get(i)[1] ;
                 }
            }
        }else{
            setUOMName() ;
            for ( int i = 0 ; i < UOM.size () ; i ++ ) {
                 if(  Integer.parseInt( UOM.get(i)[0] ) == UOM_ID  ){
                     return UOM.get(i)[1] ;
                 }
            }
        }
        return "-NA-";
    }
    
    public static JComboBox<String> UOMs = new JComboBox<String> ( UOMS );

    public static void createErrorLogExcel(){

        File file = new File ( System.getProperty ( "user.home" ) + "\\AppData\\Local\\Prodezy\\"+"app_log.xls" );

        if( ! file.exists ()){

            try {
                file.createNewFile ();
            } catch ( IOException ex ) {
            }
        
             Workbook wb = new HSSFWorkbook ();
             CellStyle cs = wb.createCellStyle ();
             CellStyle cs2 = wb.createCellStyle ();
             cs2.setFillForegroundColor ( HSSFColor.ROSE.index );
             cs2.setFillPattern ( HSSFCellStyle.SOLID_FOREGROUND );

             // New Sheet
             Sheet sheet1 = null;
    
                sheet1 = wb.createSheet ( "app_log" );
             
             //sheet1.setColumnWidth ( 0 , ( 15 * 500 ) );

             Cell c = null;
             Row row = sheet1.createRow ( 0 );
             c = row.createCell ( 0);
             c.setCellValue ( "Class Name" );

             c = row.createCell ( 1 );
             c.setCellValue ( "File Name" );

             c = row.createCell ( 2 );
             c.setCellValue ( "Exception Class Name" );

             c = row.createCell ( 3 );
             c.setCellValue ( "Line No" );

             c = row.createCell ( 4 );
             c.setCellValue ( "Message" );

             c = row.createCell (5 );
             c.setCellValue ( "Date-Time" );

             c.setCellStyle ( cs );

             FileOutputStream os = null;

             try {
                 os = new FileOutputStream ( file );
                 wb.write ( os );
                 wb.close ();
                 os.close ();
             } catch ( IOException e ) {
                 JOptionPane.showMessageDialog ( null , "Error " + e.getMessage () );
             } finally {
             }
        }        
    }

    public static void checkUserRolePermission(String resType, String res){
        
        int checkPermissionForResource = 0 ;
        
        try{
            
        switch( resType ){
                case "Masters":     checkPermissionForResource = 1 ;                        break ;
                case "Inventory":   checkPermissionForResource = 2 ;                        break ;
                case "Sales":          checkPermissionForResource = 3;                        break ;
                case "Production":   checkPermissionForResource = 4 ;                               break ;
                case "Reports":        checkPermissionForResource = 5 ;                             break ;
                case "Documents":    checkPermissionForResource = 6 ;                           break ;
                case "Dashboard":   checkPermissionForResource = 7 ;                            break ;
        }
            
            String gerURPDetails = "select READ, EDIT, DISABLE, EXPORT, CREATENEW, IMPORT from USERROLEPERM where DEPTID in ("+Proman_User.getDeptIds ()+") AND ROLE in ("+Proman_User.getRole ()+") AND RESID = "+checkPermissionForResource;
            ResultSet result = DB_Operations.executeSingle ( gerURPDetails ) ;

            int read = 0, edit = 0, disable = 0, createnew= 0, import_= 0, export = 0,   exportPDF = 0 , reports = 0   ;
            
            if( result!=null){
               read = result.getInt("READ") ;
               edit = result.getInt("EDIT") ;
               disable = result.getInt("DISABLE") ;
               createnew = result.getInt("CREATENEW") ;
               import_ = result.getInt("IMPORT") ;
               export = result.getInt("EXPORT") ;
               
            }
            
            if( read == 1 )
                Proman_User.setCanRead (  true );            

            if( edit == 1 )
                Proman_User.setCanEdit ( true );

            if( disable == 1 )
                Proman_User.setCanDisable ( true );
                                    
            if( createnew == 1 )
                Proman_User.setCanCreate ( true );

            if( import_  == 1 )
                Proman_User.setCanImport ( true );
            
            if( export == 1 )
                Proman_User.setCanExport ( true );
            
             if( exportPDF == 1 )
                Proman_User.setCanExportToPDF (true );
             
              if( reports == 1 )
                Proman_User.setCanSeeReports (true );

           
            
//            System.out.println ( ""+
//                    "Department : "+Proman_User.getDept ()+
//                    "  Role :" +Proman_User.getRole ()+
//                    "  Can Read : "+Proman_User.isCanRead ()+
//                    "  Can Edit : "+Proman_User.isCanEdit ()+
//                    "  Can Disable : "+Proman_User.isCanDisable ()+
//                    "  Can Create : "+Proman_User.isCanCreate ()+
//                    "  Can Import : "+Proman_User.isCanImport ()+
//                    "  Can Export : "+Proman_User.isCanExport () );
            
            
            
            result.close() ;
        }catch(SQLException e){
            System.out.println ( "Error from "+e.getMessage () );
          //  JOptionPane.showMessageDialog(null, "Exception in checkuserpermsission method "+e.getMessage ()) ;
        }
                
    }
    
    public static String dbName = "databases\\";
    public static String someProduct = "productID";
    public static String someProcess = "processID";
    public static String someMachine = "machineID";
    public static String someEmployee = "employeeID";
    public static String someShift = "shiftID";
    public static String someCustomer = "customerID";
    public static String someProdCost = "prodcost";
    public static String someRate = "rate";
    public static String someFromDate = "fromdate";
    public static String someToDate = "todate";

    //  Production Qty	
    public static String productionByQtyQuery = "select   showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty'  from dailyreport where ";
    //public static String productionByQtyCall = "production?";
        public static String productionByQtyCall = "allfgstock?";
    

    //Production Value	
    public static String productionByvalueQuery = "select showrdate,  SUM(qtyout*" + someRate + ") as 'Value',  SUM(qtyout) as 'Produced Qty' , ( select PART_NAME from finished_goods where FG_ID in ( " + someProduct + " )) as 'Product',  ( select CUSTOMER_NAME from customer where CUSTOMER_ID in (" + someCustomer + ") ) as 'Customer', (select SALES_RATE from FG_CUSTOMER_MAP where M_FG_ID in (" + someProduct + ") AND M_CUST_ID in (" + someCustomer + ")) as 'Sales Rate'   from dailyreport where processid = " + someProcess;
    //public static String productionByvalueCall = "productionvalues?";
    public static String productionByvalueCall = "allfgstock_value?";
    
    //WIP	
    public static String wipWithFiltersQuery = "select MOVEDATE as 'Date', SUM(BALANCE) as 'Qty In Progress', (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product'   ,  (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process', (select MACHINE_NO from machine where MCH_ID in (machineid)) as 'Machine' , (select EMP_NO  from employee where EmployeePK in (empid)) as 'Employee'  from op_wip2 where ";
    public static String wipWithFilterCall = "progressreport?";
    
    
    //Rejection	
    public static String rejectionQuery = "select showrdate as 'Date' ,   SUM(rejection) as 'Rejection'  from dailyreport where ";
    public static String rejectionCall = "productrejection?";

    //Productivity	
    public static String productivityQuery = "select showrdate as 'Date'   , \n"
            + "(( Sum(qtyout )/ SUM(actual_hours)) / (select TG_OPT_HR from FG_PROCESS_MACH_MAP where REF_PROCESS_ID in (processid)))*100 as 'Efficiency' , \n"
            + "(select TG_OPT_HR from FG_PROCESS_MACH_MAP where REF_PROCESS_ID in (processid)) as 'Expected O/P per hr',\n"
            + "total( (total_min/60)+round(CAST((total_min%60) as real)/ cast(60 as real), 2) ) as 'Total Work Hours',\n"
            + "total( (actual_min/60)+round(CAST((actual_min%60) as real)/ cast(60 as real), 2) ) as 'Actual Work Hours',\n"
            + "SUM(qtyout) as 'Qty Produced',\n"
            + "round( SUM(qtyout)/SUM(actual_hours) , 2) as 'Actual O/p per hr' \n"
            + "from dailyreport  where ";
    public static String productivityCall = "productionreport?";
    

    //PPM	
    public static String ppmQuery = "select showrdate as 'Date' ,  SUM(rejection)as 'Total Rejection', SUM((rejection*1000000)/qtyout) as 'PPM'  , (select PART_NAME from finished_goods where FG_ID in(fgid)) as 'Product', SUM(qtyout) as 'Total Production' from dailyreport where ";
    public static String ppmCall = "ppmreport?" ;
    
    
    //Cost vs Value & PMPH Aggregate	
    //public static String costValueANDPMPHAggre = "select showrdate as 'Date',   SUM(qtyout*"+someProdCost+") as 'Cost Incurred',   SUM(qtyout) as Production  ,   SUM(qtyout*(select SALES_RATE from FG_CUSTOMER_MAP where M_FG_ID  =  " + someProduct + " AND M_CUST_ID in(customer_id))) as 'Value Produced' , SUM(stoptime - starttime) as Efforts ,  SUM(qtyout*(select SALES_RATE from FG_CUSTOMER_MAP where M_FG_ID  =  " + someProduct + " AND M_CUST_ID in(customer_id)))/SUM(stoptime - starttime) as 'Value per man hour' , SUM(qtyout*"+someProdCost+")/SUM(stoptime - starttime) as 'Cost per man hour' from dailyreport where ";
    public static String costValueANDPMPHAggre = "select  showrdate as 'Date'  ,  SUM(qtyout*(select SALES_RATE from FG_CUSTOMER_MAP where M_FG_ID  =  " + someProduct + " AND M_CUST_ID in(customer_id)))/SUM(stoptime - starttime) as 'Value per man hour' , SUM(qtyout*" + someProdCost + ")/SUM(stoptime - starttime) as 'Cost per man hour'  , SUM(qtyout) as Production,  SUM(stoptime - starttime) as Efforts , SUM(qtyout*" + someProdCost + ") as 'Cost Incurred',  SUM(qtyout*(select SALES_RATE from FG_CUSTOMER_MAP where M_FG_ID  =  " + someProduct + " AND M_CUST_ID in(customer_id))) as 'Value Produced' from dailyreport where fgid =  " + someProduct + "  AND customer_id in (select  distinct(customer_id) from dailyreport where fgid =  " + someProduct + " AND processid = " + someProcess + ")";
    public static String costValueANDPMPHCall = "pmphreport?" ;
    
    
    
    public static String OEE = " select showrdate, \n"
            + "( (round((cast(SUM(a.actual_min) as real) / cast((SUM(b.DAVLHRS*60)) as real)), 2)) * \n"
            + " (round(( Sum(a.qtyout )/ SUM(a.actual_hours))/(select TG_OPT_HR from FG_PROCESS_MACH_MAP where REF_PROCESS_ID in (a.processid)), 2) ) *\n"
            + " (round(total(a.qtyout-a.rejection)/total(a.qtyout), 2)  ))*100 as 'OEE' , \n"
            + "(round( (cast(SUM(a.actual_min) as real) / cast((SUM(b.DAVLHRS*60)) as real))*100, 2)) as 'Availibility', \n"
            + "(round(( Sum(a.qtyout )/ SUM(a.actual_hours))/(select TG_OPT_HR from FG_PROCESS_MACH_MAP where REF_PROCESS_ID in (a.processid)), 2)*100 ) as 'Efficiency',\n"
            + "(round(total(a.qtyout-a.rejection)/total(a.qtyout), 2) *100) as 'Quality' \n"
            + "from dailyreport a inner join machine b where a.machineid = b.MCH_ID  AND ";
    public static String OEECall = "oeereport_revised?" ;
    
    
    public static String defectOccurences = "select  (select RR_DESC from rejection_reasons where RR_ID in(rej_reason)) as 'Defect Description', count(rej_reason) as Occurrences, sum(rej_reason) as Count from dailyreport group by rej_reason";
    public static String defectOccurancesCall = "defectreport?"  ;
    
    
    public static String productContraint = " fgid=" + someProduct + " ";
    public static String processContrainnt = " processid=" + someProcess + " ";
    public static String machineContraint = " machineid=" + someMachine + " ";
    public static String employeeContraint = " empid=" + someEmployee + " ";
    public static String customerContraint = " customer_id=" + someCustomer + " ";
    public static String shiftContraint = " shift_id=" + someShift + " ";
    public static String fromWeek = "", toWeek = "";
    public static String fromMonth = "", toMonth = "";
    public static String fromQuarter = "", toQuarter = "";
    public static String fromYTD = "", toYTD = "";

    
    public static String WIPproductContraint = " PRODUCT=" + someProduct + " ";
    public static String WIPprocessContrainnt = " PROCESS_STAGE=" + someProcess + " ";
    public static String WIPcustomerContraint = " customer_id=" + someCustomer + " ";
    public static String WIPshiftContraint = " shift_id=" + someShift + " ";
    
    
    public static String dailyFreq = "rdate >= '" + someFromDate + "' AND rdate <= '" + someToDate + "' group by rdate";
    public static String ytdFreq = "rdate >= '" + someFromDate + "' AND rdate <= '" + someToDate + "' ";
    public static String weeklyFreq = "rdate >= '" + someFromDate + "' AND rdate <= '" + someToDate + "' ";
    public static String monthFreq = "rdate >= '" + someFromDate + "' AND rdate <= '" + someToDate + "' ";
    public static String quarterFreq = "rdate >= '" + someFromDate + "' AND rdate <= '" + someToDate + "' ";
    public static String hyFreq = "rdate >= '" + someFromDate + "' AND rdate <= '" + someToDate + "' ";

    public static String WIPdailyFreq = "MOVEDATE >= '" + someFromDate + "' AND MOVEDATE <= '" + someToDate + "' group by MOVEDATE";
    public static String WIPytdFreq = "MOVEDATE >= '" + someFromDate + "' AND MOVEDATE <= '" + someToDate + "' ";
    public static String WIPweeklyFreq = "MOVEDATE >= '" + someFromDate + "' AND MOVEDATE <= '" + someToDate + "' ";
    public static String WIPmonthFreq = "MOVEDATE >= '" + someFromDate + "' AND MOVEDATE <= '" + someToDate + "' ";
    public static String WIPquarterFreq = "MOVEDATE >= '" + someFromDate + "' AND MOVEDATE <= '" + someToDate + "' ";
    public static String WIPhyFreq = "MOVEDATE >= '" + someFromDate + "' AND MOVEDATE <= '" + someToDate + "' ";
    
    
    public static String dailyFreqCall ="",     ytdFreqCall ,     weeklyFreqCall ,    monthFreqCall ,    quarterFreqCall ,    hyFreqCall ;
    
    

        
    public static String getWebCallDateFilterString( String toDate, String fromDate){

            try{
                    dailyFreqCall = "to_date="+URLEncoder.encode(toDate, "UTF-8")+"&from_date="+URLEncoder.encode(fromDate, "UTF-8");
                    
                    return dailyFreqCall  ;
                    
            }catch( UnsupportedEncodingException e){
            
                return "error";
            }
            
        }
    
     public StaticValues () {
        
        sm = new SalesMaster() ;
         
        resourceMap.add ( new String[] { "Utilities" , "Production" , "Masters" } );
        resourceMap.add ( new String[] { "Machine" , "Production" , "Masters" } );
        resourceMap.add ( new String[] { "Finished Goods" , "Production" , "Masters" } );
        resourceMap.add ( new String[] { "Employee" , "HR" , "Masters" } );
        resourceMap.add ( new String[] { "Process" , "Production" , "Masters" } );
        resourceMap.add ( new String[] { "Shifts" , "Production" , "Masters" } );
        resourceMap.add ( new String[] { "Plant" , "Production" , "Masters" } );
        resourceMap.add ( new String[] { "Customer" , "Sales" , "Masters" } );
        resourceMap.add ( new String[] { "Supplier" , "Sales" , "Masters" } );
        resourceMap.add ( new String[] { "Raw Material" , "Production" , "Masters" } );
        resourceMap.add ( new String[] { "Process Map" , "Production" , "Masters" } );
        resourceMap.add ( new String[] { "Testing" , "Production" , "Masters" } );
        resourceMap.add ( new String[] { "Product-Customer" , "Production" , "Masters Mapping" } );
        resourceMap.add ( new String[] { "Process_Employee" , "Production" , "Masters Mapping" } );
        resourceMap.add ( new String[] { "Process-Product-Machine" , "Production" , "Masters Mapping" } );
        resourceMap.add ( new String[] { "User-Role-Permission" , "HR" , "Masters" } );
        resourceMap.add ( new String[] { "Production Entries" , "Production" , "Data" } );
        resourceMap.add ( new String[] { "Sales Order" , "Sales" , "Sales Orders" } );
        resourceMap.add ( new String[] { "FG Store" , "Stores" , "FG Stores" } );
        resourceMap.add ( new String[] { "RM Inventory" , "Stores" , "Inventory RM" } );
        resourceMap.add ( new String[] { "Reports" , "Production" , "Report" } );
        resourceMap.add ( new String[] { "Documents" , "Production" , "Important Documents" } );
        resourceMap.add ( new String[] { "Setup" , "Production" , "Daily Setup" } );
    }
     
     
     
public static ArrayList<String[]> DBTablesDesc = new ArrayList<String[]>(  Arrays.asList(  
new String[]{DB_Operations.alarms_Setting,                              "ALARMS_SETTINGS", "AS_ID" , "ALARM_NAME" , "ALARM_STATE" , "ALARMS_FREQUENCY_IN_MS"},
new String[]{DB_Operations.BATCH_DETAILS , 		"BATCH_DETAILS",  "BD_ID"      , "BM_ID"   , "RM_ID"   , "RM_QTY"   , "RM_UOM"      } , 
new String[]{DB_Operations.BATCH_MASTER , 		"BATCH_MASTER",  "BatchId"      , "BatchName"   , "BatchQty"   , "BatchUOM_ID"   , "BatchCreateDate"   , "BatchExpireDate" } ,             
new String[]{DB_Operations.create_BOM_Table , 		"BillOfMaterial", "BOM_ID"      , "FG_ID" , "RM_ID" ,"NT_WT" ,"GS_WT" ,"TOTAL_WT" , "CREATED_ON"	,"EDITED_ON"	,"EDITED_BY"	, "UOMID"    } ,         
new String[]{DB_Operations.create_Current_Stock_Table ,          "CurrentStock", "Stock_ID"      , "REF_ID" , "OPENING" ,"RECEIVED" ,"USED" ,"CLOSING" , "CREATED_ON"	,"EDITED_ON"	,"EDITED_BY"} ,	            
new String[]{DB_Operations.create_INSERT_DEPARTMENT , 	"DEPARTMENTS",  "DID"      , "DNAME" } ,               
new String[]{DB_Operations.deignationsTable, 		"Designations",  "DesgID"     ,"DesgTitle" } ,   
new String[]{DB_Operations.createEmpNatureTable ,  	"EMP_NATURE", "NATURE_ID"	     ,"NATURE"            } ,
new String[]{DB_Operations.create_Finished_Goods_Table ,        "FGStock", "FGStock_TR_ID"      , "FG_ID" , "FG_ITEM_ID", "OPENING" ,"RECEIVED" ,"USED" ,"CLOSING" , "CREATED_ON"	,"EDITED_ON"	,"EDITED_BY"	} ,           
new String[]{DB_Operations.create_FG_CUST_MAP_Table , 	"FG_CUSTOMER_MAP",  "FG_C_ID"      , "M_FG_ID"   , 	"M_CUST_ID"   , "SALES_RATE"   , "S_UOM_ID" , "CREATED_ON" , "EDITED_ON" , "EDITED_BY" } ,            
new String[]{DB_Operations.create_FG_PR_MACH_MAP_Table ,  "FG_PROCESS_MACH_MAP",  "FPM_ID"      , "REF_FG_ID" , "REF_PROCESS_ID" , "REF_MCH_ID" , "SETUP_TIME" , "IDEAL_PROCESS_TIME" , "REMARKS" , "TG_OPT_HR" , "TG_OPT_SHT" , "TG_OPT_DAY" , "UOM_ID" , "VAL_AT_PROC" , "MAX_REJ_PERM"  } ,                        
new String[]{DB_Operations.gradesTable, 			"Grades", "GradeID"     ,"GradeName" }   ,
new String[]{DB_Operations.PLANT_MASTER , 		"PLANT_MASTER",  "plantid"      , "plantname" , "plantcode" , "gstno" , "plantaddress" , "plantemailaddress" , "plantweekoff" , "plantcpname" , "plantcpno" , "plantcpemail" , "shifts" , "companyname" , "complogo" , "plantcontactno"         } ,
new String[]{DB_Operations.create_PROCESS_EMPLOYEE_MAP_Table , "PROCESS_EMPLOYEE_MAP",  "PR_EMP_ID"      , "M_PR_ID"   , "M_EMP_ID"   , "EFFICIENCY"   , "CREATED_ON" , "EDITED_ON" , "EDITED_BY"  } ,                      
new String[]{DB_Operations.create_processMachine_Table ,        "PROCESS_MACH_MAP", 	"PMM_ID"	     ,	"REF_FPM_ID"	,	"MACHINE_ID"	,	"SETUP_TIME"	,	"IDEAL_PROCESS_TIME"	,	"REMARKS"	 } ,           
new String[]{DB_Operations.create_processMaster_Table ,          "PROCESS_MASTER", 	"PROCESS_ID"	     ,	"PROCESS_NAME"	,	"PROCESS_DETAILS"	, "IS_BATCH_WISE" , "IS_ITEM_WISE"  } ,          
new String[]{DB_Operations.REF_DOCUMENTS ,  		"REF_DOCUMENTS" , "RD_UID"      , "RDM_UID"   , "RD_NAME" , "CREATION_DATE" } ,         
new String[]{DB_Operations.REF_DOC_MASTER , 		"REF_DOC_MASTER",  "RDM_UID"      , "RDM_NAME" } ,       
new String[]{DB_Operations.create_INSERT_RESOURCES , 	"RESOURCE",  "RSID"      , "RSNAME"  } ,                      
new String[]{DB_Operations.create_RM_Inventory_Table ,          "RMStock", "RMStock_TR_ID"      , "RMI_ID", "RM_ITEM_ID", "OPENING" ,"RECEIVED" ,"USED" ,"CLOSING" , "CREATED_ON"	,"EDITED_ON"	,"EDITED_BY"} ,	            
new String[]{DB_Operations.createSalaryBandTable , 		"SalaryBand", "SB_ID"      , "SB_RANGE"  } ,
new String[]{DB_Operations.USERROLEPERM ,                            "USERROLEPERM",  "URP_ID"      , "DEPTID"   , "RESID"   , "ROLE"   , "READ"   , "EDIT"   , "DISABLE"   , "EXPORT"   , "CREATENEW"   , "IMPORT"   , "ACCESS_ON_MOBILE"  } ,        
new String[]{DB_Operations.calibration_masterTable, 	"calibration_master", "EQUIPPMENT_ID"     ,"EQUIPPMENT_NAME","CALIBRATION_DATE","NEXT_DUE_DATE","CALIBRATION_AGENCY","CALIBRATION_FREQUENCY","NABL_CERTICATE_NO","CERTICATE_COPY","AGENCY_CONTACT_DETAILS" ,  "INSTR_LIST" , "SUPPLIER" ,   "EQP_MAKE" , "CREATED_ON","EDITED_ON","EDITED_BY"} ,
new String[]{DB_Operations.createCategoryTable , 		"category", "Category_ID"      ,"Category"  } ,
new String[]{DB_Operations.contactPersonMasterTable, 	"contact_person", "CONT_PER_ID"     ,"CONT_EMAIL","CONT_NO","CUST_ID","SUPP_ID",  "CONT_NAME", "CREATED_ON","EDITED_ON","EDITED_BY"} ,
new String[]{DB_Operations.customerMasterTable, 		"customer", "CUSTOMER_ID"     ,"CUSTOMER_NAME","CUST_ADDRESS","CONTACT_LL","CONTACT_BRD","WEEKLY_OFF","GST_NO","OUR_VENDOR_CODE","PAYMENT_TERMS","INDUSTRY","SEGMENT","CITY","CATEGORY","CREATED_ON" ,"EDITED_ON","EDITED_BY"} ,
new String[]{DB_Operations.dailySetupMaster , 		"dailySetupMaster",  "SM_ID"      , "employeeId"   , "fgId"   , "shiftId"   , "rawMatId"   , "rawMatQty"   , "isMouldClean"   , "isPersonSkilled"   , "isMachineClit"   , "areToolsAvl"   , "setupDate" , "setupTime" , "machineId" } ,       
new String[]{DB_Operations.dailyReport ,                                  "dailyreport",  "rid"      , "rdate"   , "rtdate"   , "showrdate"   , "showrtdate"   , "fgid"   , "processid"   , "machineid"   , "starttime"   , "stoptime"   , "qtyin"   , "qtyout"   , "rejection"   , "empid"   , "showFromTime"   , "showToTime"   , "batchno"   , "rej_reason" , "act_stp_min" , "customer_id" , "shift_id" , "SMADID" , "actual_min" , "total_min" , "actual_hours" , "total_hours" , "alloted_rm" } ,         
new String[]{DB_Operations.dailyReport3 , 		"dailyreport3",  "rid"      , "rdate"   , "rtdate"   , "showrdate"   , "showrtdate"   , "fgid"   , "processid"   , "machineid"   , "starttime"   , "stoptime"   , "qtyin"   , "qtyout"   , "rejection"   , "empid"   , "showFromTime"   , "showToTime"   , "batchno"   , "rej_reason" , "act_stp_min" , "customer_id" , "shift_id" , "SMADID" , "actual_min" , "total_min" , "actual_hours" , "total_hours" , "alloted_rm"         } ,
new String[]{DB_Operations.employeeTable, 		"employee", "EmployeePK"     ,"EMP_NAME","EMP_NO","JOIN_DT","PHOTO","GENDER","DEPARTMENTID","ROLE","EDUCATION","CELL_NO","DOB","ADD_LOCAL","ADD_PERM","JOIN_SKILL","CURR_SKILL","AUTH_SKILL","DISEASE","PAN_NO","AADHAR_NO","PF_NO","ESIC_NO","SAL_BAND",  "IS_USER" , "USERNAME" ,  "PASSWORD" ,   "CREATED_ON","EDITED_ON","EDITED_BY"} ,
new String[]{DB_Operations.finishedGoodsTable,   		"finished_goods",  "FG_ID"      , "FG_PART_UNIQUE_ID"  , "FG_PART_NO" , "PART_NAME" , "CUSTOMER" , "MOQ" , "SALES_RATE" , "GST_RATE" , "PACK_SIZE" , "MLD_CAVIT" , "TGT_OP_TXT" , "PART_DRG" , "RBR_BTC_NO" , "NT_WGHT" , "GRS_WGHT" , "OP_SKILL_LEVEL" , "IMPORTANCE" , "PART_VALID_DOC" , "PART_PPAP" , "PART_IMG" , "MODL_SOP" , "TESTNG_ID" , "INSP_FRQ" , "PREF_MCH" , "MTL_INST_IMG" , "CREATED_ON" , "EDITED_ON" , "EDITED_BY" , "NOTES" , "PROD_COST" , "FG_UOM_ID"  } ,
new String[]{DB_Operations.createIndustryTable , 		"industry", "Industry_ID"      ,"Industry"   } ,         
new String[]{DB_Operations.machineMasterTable,  		"machine", "MCH_ID"     ,"MACHINE_NO","MAKE","BED_SIZE","HEATER_QTY","YR_OF_COMM","CR_MAINT_RTG","MCH_IMG","CR_MCH_OWN","MACH_OWN_IMG","MCH_BRKD_RCD","PM_ELE","PM_MEC","PM_HYD","PM_PNM","PM_CLIT","PRSS_GAUGE","TIMER","TEMP_CTRL",  "OP_RATE_HR"   , "AVL_HRS" , "DAVLHRS" ,   "INSTR_LIST"  , "CREATED_ON","EDITED_ON","EDITED_BY"} ,
new String[]{DB_Operations.create_wip_Table , 		"op_wip",  "P_ID"      , "PRODUCT" , "REJECTED" , "REASON" , "FINISHED" , "MOVEQTY" , "MOVEDATE" , "ORDERNO" , "PROCESS_STAGE" , "BALANCE" , "REJECTIONDATE" , "DRID" } ,             
new String[]{DB_Operations.op_wip2 , 			"op_wip2",  "P_ID"      , "fgid" , "REJECTED" , "REASON" , "FINISHED" , "MOVEQTY" , "rdate" , "ORDERNO" , "processid" , "BALANCE" , "REJECTIONDATE" , "DRID" , "machineid"   , "empid"         } ,
new String[]{DB_Operations.createPackSizeTable , 		"pack_size", "PackSize_ID"      ,"PackSize"      } ,   
new String[]{DB_Operations.create_po_order_details_Table,       "po_order_details", 	"oder_detail_id"	     ,	"product_id"	,	"product_qty"	,	"ref_po_id"	  ,	"finished_qty"	 , "CREATED_ON"  } ,             
new String[]{DB_Operations.raw_materialMaster , 		"raw_material",  "RM_ID"      , "RM_TYPE" , "RM_NAME" , "RM_CTG" , "RM_AUTH_SUPP" , "RM_RATE" , "RM_CRIT" , "RM_EC_NO" , "RM_CAS_NO" , "GST_NO" , "RM_CLASS" , "REORDER_LEVEL" , "CREATED_ON" , "EDITED_ON" , "EDITED_BY" , "RMM_UOM_ID"  } ,
new String[]{DB_Operations.createRawMaterialTable , 	"raw_material_type", "RMType_ID"      ,"RawMaterial"     } ,     
new String[]{DB_Operations.createRejectionReasonTable ,         "rejection_reasons", "RR_ID"      ,"RR_DESC" , "RR_CODE" , "RR_SHORT"} ,  
new String[]{DB_Operations.createRejectionDataTable,               "rejectionData","rdID","ref_pID","rrID","rQty"},  
new String[]{DB_Operations.create_saledOrder_Table , 	"sales_orders", 	"sales_po_id"	     ,	"so_customer_id"	,	"product_ids"	,	"po_date"	,	"po_copy_name"	,	"order_qty"	,	"order_no"	  ,	"order_ack"	 ,   "CREATED_ON"   } ,          
new String[]{DB_Operations.createSegmentTable , 		"segment", "Segment_ID"      ,"Segment"     } ,
new String[]{DB_Operations.shifts , 			"shifts",  "shiftid"      , "shiftno" , "shifttitle" , "shiftfromtime" , "shifttotime" , "breakduration" , "refplantid"   } ,  
new String[]{DB_Operations.supplierMasterTable , 		"supplier", "SUPP_ID"     ,"SUPP_NAME","SUPP_ADD","SUPP_CNT_LL","SUPP_CNT_BRD","WEEK_OFF","GST_NO","VENDOR_CODE","CATEGORY","PAYT_TERMS","INDUSTRY","SEGMENT","CITY", "AUTHORIZED_RM",  "CREATED_ON","EDITED_ON","EDITED_BY"} ,     
new String[]{DB_Operations.createTestingTable ,    		"testing", 	"TEST_ID"	     , "RAW_MATERIAL"	,"BTC_NO" ,"TEST_DT"	,"TEST"	,"TEST_RQ"	,"TEST_FREQ"	,    "CREATED_ON"	,"EDITED_ON"	,"EDITED_BY"	} ,
new String[]{DB_Operations.createTimeLossDaaTable,               "timeLossData" , "tldID", "ref_pID","tlrID",	"tlQty"},
new String[]{DB_Operations.createTimeLossReasonMasterTable, "timeLossReason_master","tlrm_ID","tlrm_Title"},
new String[]{DB_Operations.createUOMTable , 		"unit_meas", "UOM_ID"      ,"UOM"  } ,   
new String[]{DB_Operations.createRolesTable , 		"userRoles", "Role_ID"      ,"RoleName"      } , 
new String[]{DB_Operations.createUserMaster ,   		"users",  "USERS_USER_ID"      , "USERNAME" , "PASSWORD" , "NAME" , "ADDRESS" , "PHONE" , "DESIGNATION" , "NATUREOFEMP"  , "ROLE"  , "CREATED_ON"	,"EDITED_ON"	,"EDITED_BY"	   } ,
new String[]{DB_Operations.xl_import_test ,                             "xl_import_test",  "ID" , "name" , "address" , "phone" , "email"  }
   ) );
}
