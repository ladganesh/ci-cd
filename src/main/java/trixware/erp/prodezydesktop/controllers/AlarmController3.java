/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.controllers;

import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.examples.HomeScreen;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 *
 * @author Rajesh
 */
import trixware.erp.prodezydesktop.Model.AlarmData;
import trixware.erp.prodezydesktop.Model.StaticValues;
import Utilities.CodeResourceUtility;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.web_services.WebAPITester;
public class AlarmController3 {
    
    public static ArrayList<AlarmData> alarmData = new ArrayList<AlarmData>();
    
    public static boolean runningState = false;
    public static Status pc = null;
    public static Thread1 t3 = null;
    public static Thread2 t4 = null;
    
    boolean[] alarmsStates = null;
    String[] alarmsNames = null;
    int[] alarmsFreq = null;
    
    public String ac = new String ();
    
    public AlarmController3 () {
    }
    
    
    
    
    public AlarmController3 ( boolean state ) {
        
        pc = new Status ();
        runningState = state;
    }
    
    public static void startAlarm (String msg,  boolean state , ArrayList<AlarmData> _alarmData) {
        alarmData = _alarmData ;
        runningState = state ;
        //while( true ){
            if ( runningState ) {
                HomeScreen.jLabel1.setText ( msg );
                //System.out.println ( "Size: " + alarmsList.size () + " -- index : " + i + " - " + alarmsList.get ( i ) );
                if( pc==null ){
                    pc = new Status ();
                }
                    t3 = new Thread1 ( pc );
                    t4 = new Thread2 ( pc );
                    t3.start ();
                    t4.start ();

                
                
            }else{
                        HomeScreen.jLabel1.setText ( "" );
            }        
    }
    
    public static void showAlarm () {
        
        runningState = true;
    }
    
    public static void hideAlarm () {
        runningState = false;
    }

    // Create a thread object that calls pc.produce()
    Thread t1 = new Thread ( new Runnable () {
        @Override
        public void run () {
            try {
                pc.getFreashData ();
                
            } catch ( InterruptedException e ) {
                    StaticValues.writer.writeExcel ( AlarmController3.class.getSimpleName () , AlarmController3.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }
    } );

    // Create another thread object that calls
    // pc.consume()
    Thread t2 = new Thread ( new Runnable () {
        @Override
        public void run () {
            try {
                pc.showData ();
            } catch ( InterruptedException e ) {
              StaticValues.writer.writeExcel( AlarmController3.class.getSimpleName () , this.getClass ().toString (), e.getClass ().toString (), Thread.currentThread().getStackTrace()[2].getLineNumber()+"", e.getMessage (), StaticValues.sdf2.format(Calendar.getInstance ().getTime ())  );
            }catch ( ArithmeticException e ) {
              StaticValues.writer.writeExcel( AlarmController3.class.getSimpleName () , this.getClass ().toString (), e.getClass ().toString (), Thread.currentThread().getStackTrace()[2].getLineNumber()+"", e.getMessage (), StaticValues.sdf2.format(Calendar.getInstance ().getTime ())  );
            }
        }
    } );    
}





class Thread1 extends Thread {
    
    Status pc;
    
    public Thread1 ( Status pc1 ) {
        pc = pc1;
    }
    
    public void run () {
        
        try {
            pc.getFreashData ();
            Thread.sleep ( 1000 );
        } catch ( InterruptedException e ) {
            System.out.println ( "error3" );
            StaticValues.writer.writeExcel( AlarmController3.class.getSimpleName ()  , this.getClass ().toString (), e.getClass ().toString (), Thread.currentThread().getStackTrace()[1].getLineNumber()+"", e.getMessage (), StaticValues.sdf2.format(Calendar.getInstance ().getTime ())  );
        }catch( ArithmeticException e ){
            StaticValues.writer.writeExcel( AlarmController3.class.getSimpleName ()  , this.getClass ().toString (), e.getClass ().toString (), Thread.currentThread().getStackTrace()[1].getLineNumber()+"", e.getMessage (), StaticValues.sdf2.format(Calendar.getInstance ().getTime ())  );
        }
    }
    
}

class Thread2 extends Thread {
    
    Status pc;
    
    public Thread2 ( Status pc1 ) {
        pc = pc1;
    }
    
    public void run () {
        
        try {
            pc.showData ();
            Thread.sleep ( 1000 );
        } catch ( InterruptedException e ) {
            System.out.println ( "error3" );
            StaticValues.writer.writeExcel( AlarmController3.class.getSimpleName () , this.getClass ().toString (), e.getClass ().toString (), Thread.currentThread().getStackTrace()[1].getLineNumber()+"", e.getMessage (), StaticValues.sdf2.format(Calendar.getInstance ().getTime ())  );
        }catch( ArithmeticException e ){
            StaticValues.writer.writeExcel( AlarmController3.class.getSimpleName ()  , this.getClass ().toString (), e.getClass ().toString (), Thread.currentThread().getStackTrace()[1].getLineNumber()+"", e.getMessage (), StaticValues.sdf2.format(Calendar.getInstance ().getTime ())  );
        }
        
    }
}

class Status {
    
    AlarmController3 asdlk = new AlarmController3 ();

//    private final Object lock = new Object();
    ArrayList<String[]> processMapValues = new ArrayList<String[]> ();
    ArrayList<String> alarmsList = new ArrayList<String> ();
    
    public void getFreashData () throws InterruptedException {
        
        synchronized ( this ) {
            
            alarmsList = new ArrayList<String> ();
            
            try {
                Thread.sleep ( 2000 );
            } catch ( InterruptedException e ) {
                //StaticValues.writer.write(" Custom Error Message :"+e.getMessage());
                StaticValues.writer.writeExcel ( AlarmController3.class.getSimpleName () , AlarmController3.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
            
            if ( AlarmController3.runningState ) {
                
                for( int i=0; i<AlarmController3.alarmData.size(); i++  ){
                    AlarmData ad = AlarmController3.alarmData.get(i);
                    switch( ad.getAlarmName ()){
                        
                        case "PPM for the day" :
                            if(ad.isAlarmState ())
                                getTodaysTotalPPM ();
                            break ;
                            
                        case  "Total production for the day" :
                                if(ad.isAlarmState ())
                                getTodaysTotalProduction ();
                                break;
                                
                        case "Raw Materials stock close to ROL":
                                if(ad.isAlarmState ())
                                getRMatROL ();
                            break;
                            
                        case "Rejections for the day":
                                if(ad.isAlarmState ())
                                getRejectionForProducts ();
                            break ;
                            
                    }
                } 
                
            }else{
                        HomeScreen.jLabel1.setText ( "" );
                    }
            
            try {
                Thread.sleep ( 1000 );
            } catch ( InterruptedException e ) {
                StaticValues.writer.write(" Custom Error Message :"+e.getMessage());
            }
            
            wait ();            
            
            try {
                Thread.sleep ( 1000 );
            } catch ( InterruptedException e ) {
                StaticValues.writer.write(" Custom Error Message :"+e.getMessage());
                StaticValues.writer.writeExcel ( AlarmController3.class.getSimpleName () , AlarmController3.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
            
            AlarmController3.startAlarm (  "Refreshing notifications", AlarmController3.runningState,  AlarmController3.alarmData);
            
        }
        
    }
    
    public void showData () throws InterruptedException {
        
        synchronized ( this ) {
            
            try {
                Thread.sleep ( 1000 );
            } catch ( InterruptedException e ) {
                StaticValues.writer.write(" Custom Error Message :"+e.getMessage());
            }
            for ( int j = 0 ; j < ( ( 60000 / 3000 ) / alarmsList.size () ) ; j ++ ) {
            //for ( int j = 0 ; j < 2 ; j ++ ) {
                for ( int i = 0 ; i < alarmsList.size () ; i ++ ) {
                    if ( AlarmController3.runningState ) {
                        HomeScreen.jLabel1.setText ( alarmsList.get ( i ) );
                        System.out.println ( "Size: " + alarmsList.size () + " -- index : " + i + " - " + alarmsList.get ( i ) );
                        try {
                            Thread.sleep ( 3000 );
                        } catch ( InterruptedException e ) {
                            StaticValues.writer.write(" Custom Error Message :"+e.getMessage());
                        }
                    }else{
                        HomeScreen.jLabel1.setText ( "" );
                    }
                }
            }
            
            notify ();
            
        }
        
    }
    
    public void getTodaysTotalProduction_old () {
        
        System.out.println ( "Refreshing production data " );
        
        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-dd-MM" );

        String strDate2 = sdf2.format ( c2.getTime () ) + " 00:00:00";
        //String strDate2 = "2018-09-27 00:00:00";

        //alarmsList = new ArrayList<String> ();
        processMapValues = new ArrayList<String[]> ();
        
        ResultSet rs = null;
        //processMapValues = new ArrayList<String[]> ();
        try {
            rs = DB_Operations.executeSingle ( "select \n"
                    + "fgid,\n"
                    + "FG_PROCESS_MACH_MAP.REF_PROCESS_ID,\n"
                    + "qtyout,  \n"
                    + "FG_PROCESS_MACH_MAP.TG_OPT_DAY \n"
                    + "from dailyreport\n"
                    + "inner join FG_PROCESS_MACH_MAP ON FG_PROCESS_MACH_MAP.REF_FG_ID = dailyreport.fgid\n"
                    + "AND  FG_PROCESS_MACH_MAP.REF_PROCESS_ID = dailyreport.processid \n"
                    + "WHERE dailyreport.rdate = '" + strDate2 + "'" );
            while ( rs.next () ) {
                processMapValues.add ( new String[] { "" + rs.getString ( 1 ) , "" + rs.getString ( 2 ) , "" + rs.getString ( 3 ) , "" + rs.getString ( 4 ) } );
            }
        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel ( AlarmController3.class.getSimpleName () , AlarmController3.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            System.out.println ( "" );
        } finally {
            try {
                rs.close ();
            } catch ( SQLException ex ) {
                StaticValues.writer.write(" Custom Error Message :"+ex.getMessage());
                System.out.println ( "1" + ex.getMessage () );
            }
        }
        
        for ( int i = 0 ; i < processMapValues.size () ; i ++ ) {
            
            int l1 = 0, l2 = 0;
            try {
                l1 = Integer.parseInt ( processMapValues.get ( i )[ 2 ] );
                l2 = Integer.parseInt ( processMapValues.get ( i )[ 3 ] );
            } catch ( NumberFormatException e ) {
                StaticValues.writer.writeExcel ( AlarmController3.class.getSimpleName () , AlarmController3.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
                System.out.println ();
            } finally {
                try {
                    rs.close ();
                } catch ( SQLException ex ) {
                    StaticValues.writer.write(" Custom Error Message :"+ex.getMessage());
                    System.out.println ( "5" + ex.getMessage () );
                }
            }
            
            if ( l1 >= l2 && l1 != 0 && l2 != 0 ) {
                //processMapValues.add( new String[]{  ""+l1, ""+l2  }); 
                alarmsList.add ( "Product " + processMapValues.get ( i )[ 0 ] + " at " + processMapValues.get ( i )[ 1 ] + "  Target Production: " + l2 + ",  Todays production: " + l1 );
            }
        }
        
    }
    
    public void getTodaysTotalProduction () {
        
        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-MM=dd" );
        String strDate2 = sdf2.format ( c2.getTime () ) + " 00:00:00";
        
        //alarmsList = new ArrayList<String> ();
        processMapValues = new ArrayList<String[]> ();
        
        ResultSet rs = null;
        //processMapValues = new ArrayList<String[]> ();
        try {
            
            //    product name          process             target output           total output for today
         String result2 ;
        
         try {
            String addEmpAPICall = "production?to_date=" + URLEncoder.encode ( strDate2 , "UTF-8" ) + "&from_date=" + URLEncoder.encode ( strDate2 , "UTF-8" )+"&freq=&fgid=&processid=&machineid=&empid=&customer_id=&shift_id=" ;
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            
            //"ppm":"3812.4285"
            
            if ( result2 != null && ! result2.contains( "not found" )  && ! result2.contains( "null" ) ) {
                alarmsList.add ( "Total production for today : " + Double.parseDouble (  buildTableModelfromJSON ( result2 ,  "outputqty"  )  ) );
            }else{
                alarmsList.add ( "Total production for today not available now "   );
            }    
        } catch ( UnsupportedEncodingException e ) {
        }
            
                processMapValues.add ( new String[] { "" + rs.getString ( 1 ) , "" + rs.getString ( 2 ) , "" + rs.getString ( 3 ) , "" + rs.getString ( 4 ) } );
            
        }catch(   Exception e  ){
            
        }
        
    }
    
    public void getTodaysTotalPPM_old () {
        
        System.out.println ( "Refreshing PPM data " );

        //c2 = Calendar.getInstance () ;
        Calendar c2 = Calendar.getInstance ();
        
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-dd-MM" );
        String strDate2 = sdf2.format ( c2.getTime () ) + " 00:00:00";

       /// String strDate2 = "2018-09-12 00:00:00";

        //alarmsList = new ArrayList<String> ();
        ResultSet rs = null;
        try {
            rs = DB_Operations.executeSingle ( "select round( CAST(((TOTAL(rejection)/TOTAL(qtyout))*1000000) as real) , 2) as 'TOTAL PPM' from dailyreport where rdate = '" + strDate2 + "' " );
            alarmsList.add ( "Plant PPM for today : " + Double.parseDouble ( rs.getString ( "TOTAL PPM" ) ) );
            
        } catch ( SQLException | NullPointerException e ) {
            StaticValues.writer.writeExcel ( AlarmController3.class.getSimpleName () , AlarmController3.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            System.out.println ( "PPM function " + e.getClass () + " " + e.getMessage () );
        } finally {
            try {
                rs.close ();
            } catch ( Exception sql2 ) {
                StaticValues.writer.write(" Custom Error Message :"+sql2.getMessage());
            }
        }
    }
    
    public void getTodaysTotalPPM () {
        
        System.out.println ( "Refreshing PPM data " );
        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-MM-dd" );
        String strDate2 = sdf2.format ( c2.getTime () ) + " 00:00:00";

        String result2 ;
        
         try {
            String addEmpAPICall = "ppmreport?to_date=" + URLEncoder.encode ( strDate2 , "UTF-8" ) + "&from_date=" + URLEncoder.encode ( strDate2 , "UTF-8" )+"&freq=&fgid=&processid=&machineid=&empid=&customer_id=&shift_id=" ;
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            
            //"ppm":"3812.4285"
            
            if ( result2 != null && ! result2.contains( "not found" )  && ! result2.contains( "null" ) ) {
                alarmsList.add ( "Plant PPM for today : " + Double.parseDouble (  buildTableModelfromJSON ( result2 ,  "ppm"  )  ) );
            }else{
                alarmsList.add ( "PPM data not available now "   );
            }    
        } catch ( UnsupportedEncodingException e ) {
        }
          
    }
    
    public void getRMatROL_OLD () {
        
        System.out.println ( "Refreshing RM at ROL " );
        
        ResultSet rs = null;
        try {
            //rs = DB_Operations.executeSingle ("select a.RM_ID, a.RM_NAME, a.RM_CTG, b.CLOSING from raw_material a inner join RMStock b where a.RM_ID = b.RMI_ID");
            rs = DB_Operations.executeSingle ( "select a.RM_ID , a.RM_NAME as 'Name', a.RM_CTG as 'Category', b.CLOSING as 'Available', a.REORDER_LEVEL as 'Reorder Level'  from raw_material a inner join RMStock b where a.RM_ID = b.RMI_ID  group by a.RM_ID" );
            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
            if ( rs != null ) {
                
                while ( rs.next () ) {
                    
                    double available = 0.0, rol, roq = 0.0;
                    String rmname = rs.getString ( 2 );
                    available = Double.parseDouble ( rs.getString ( 4 ) );
                    rol = Double.parseDouble ( rs.getString ( 5 ) );
                    
                    if ( available <= 0 && rol != 0.0 ) {
                        
                        alarmsList.add ( "Raw Material " + rmname + " available quantity is 0" );
                    } else if ( available > 0 && rol != 0.0 ) {
                        if ( available < rol ) {
                            alarmsList.add ( "Raw Material " + rmname + " available quantity " + available + " is less than ROL " + rol );
                        }
                    }
                }
            }
            rs.close ();
        } catch ( SQLException e ) {
            System.out.println ( e.getClass () + "  " + e.getMessage () );
            StaticValues.writer.writeExcel ( AlarmController3.class.getSimpleName () , AlarmController3.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } finally {
            try {
                rs.close ();
            } catch ( SQLException sql2 ) {
                StaticValues.writer.write(" Custom Error Message :"+sql2.getMessage());
            }
        }
        
    }
    
    public void getRMatROL () {
        
       String result2 ;
        
         try {
             
            String getRMList = "rawmaterials" ;
            result2 = WebAPITester.prepareWebCall ( getRMList , StaticValues.getHeader () , "" );
            
            if(  result2 != null && ! result2.contains( "not found" )  && ! result2.contains( "null" )   ){
                ArrayList<String[]> rmList = buildListfromJSON ( result2, new String[]{ "RM_ID", "RM_NAME" ,  "REORDER_LEVEL" } ) ;

                for(   int i=0;  i<rmList.size() ; i++   ){

                    String getRMStock = "latestrwamstock?rmid="+rmList.get ( i )[0] ;

                    String getRMStockResult = WebAPITester.prepareWebCall ( getRMStock , StaticValues.getHeader () , "" );

                    if ( getRMStockResult != null && ! getRMStockResult.contains( "not found" )  && ! getRMStockResult.contains( "null" ) ) {

                        double closing = Double.parseDouble( buildTableModelfromJSON ( getRMStockResult ,  "CLOSING"  ) ) ;

                        if( closing <=  Double.parseDouble( rmList.get ( i )[2] ) ){
                            alarmsList.add ( "Raw Material " +rmList.get ( i )[1] + " available quantity " +  closing + " is less than ROL " + rmList.get ( i )[2]    ) ;
                        }

                    }else{
                        alarmsList.add ( "Raw Material not available now "   );
                    }
                }
            }else{
                    alarmsList.add ( "Raw Material not available now "   );
                }
            
        } catch ( Exception e ) {
             System.out.println ( ""+ e.getMessage () ); 
        }
        
    }
    
    public void getRejectionForProducts_OLD () {
        
        System.out.println ( "Refreshing Rejection for product " );
        
        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-dd-MM" );
        String strDate2 = sdf2.format ( c2.getTime () ) + " 00:00:00";
        //String strDate2 = "2018-09-25 00:00:00";
        
        ArrayList<String[]> ProcProdRej = new ArrayList<String[]> ();
        ArrayList<String[]> TodaysRej = new ArrayList<String[]> ();

        // 1. get the details of processes , products that are in progress today and actual rejections for the day in correspoding processes and products
        //     select processid, fgid, rejection from dailyreport where rdate = strDate2 AND rejection > 0
        // 2. get the details of processes , products and corresponding rejection limits
        //     select MAX_REJ_PERM from FG_PROCESS_MACH_MAP where REF_FG_ID = 11 AND REF_PROCESS_ID = 8
        ResultSet rs = null;
        try {

            //for ( int i = 0 ; i < TodaysRej.size () ; i ++ ) {
            rs = DB_Operations.executeSingle ( "SELECT \n"
                    + "processid, fgid, rejection,\n"
                    + "FG_PROCESS_MACH_MAP.MAX_REJ_PERM\n"
                    + "FROM dailyreport\n"
                    + "INNER JOIN FG_PROCESS_MACH_MAP ON FG_PROCESS_MACH_MAP.REF_FG_ID = dailyreport.fgid \n"
                    + "WHERE FG_PROCESS_MACH_MAP.REF_PROCESS_ID = dailyreport.processid \n"
                    + "AND dailyreport.rdate = '"+strDate2+"' \n"
                    + "AND dailyreport.rejection >= FG_PROCESS_MACH_MAP.MAX_REJ_PERM" );
            while ( rs.next () ) {
                int actualRej = rs.getInt ( 3 );
                int maxRej = rs.getInt ( 4 );
                ///ProcProdRej.add ( new String[] { TodaysRej.get ( i )[ 0 ] , TodaysRej.get ( i )[ 1 ] , rs.getString ( 1 ) } );
                if ( actualRej > maxRej ) {
                    alarmsList.add ( "Rejection in process " + rs.getString ( 1 ) + " for product " + rs.getString ( 2 ) + " is " + rs.getInt ( 3 ) + " - higher than max permissible  " + rs.getInt ( 4 ) );
                } else if ( actualRej == maxRej ) {
                    alarmsList.add ( "Rejection in process " + rs.getString ( 1 ) + " for product " + rs.getInt ( 2 ) + " is " + rs.getInt ( 3 ) + " - reached max permissible  " + rs.getInt ( 4 ) );
                }
            }
            rs.close ();
            //}

        } catch ( SQLException e ) {
            StaticValues.writer.write(" Custom Error Message :"+e.getMessage());
            try {
                rs.close ();
            } catch ( SQLException ex ) {
                StaticValues.writer.write(" Custom Error Message :"+e.getMessage());
                
            }
            StaticValues.writer.writeExcel ( AlarmController3.class.getSimpleName () , AlarmController3.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } finally {
            try {
                //            try {
//                rs.close ();
//            } catch ( SQLException sql2 ) {
//                StaticValues.writer.write(" Custom Error Message :"+e.getMessage());
//            }

                rs.close ();
            } catch ( SQLException ex ) {
                Logger.getLogger ( Status.class.getName() ).log ( Level.SEVERE , null , ex );
            }
        }
        
    }
    
    public void getRejectionForProducts () {
        
        System.out.println ( "Refreshing PPM data " );
        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-MM-dd" );
        String strDate2 = sdf2.format ( c2.getTime () ) + " 00:00:00";

        String result2 ;
        
         try {
            String addEmpAPICall = "productrejection?to_date=" + URLEncoder.encode ( strDate2 , "UTF-8" ) + "&from_date=" + URLEncoder.encode ( strDate2 , "UTF-8" )+"&freq=&fgid=&processid=&machineid=&empid=&customer_id=&shift_id=" ;
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            
            //"ppm":"3812.4285"
            
            if ( result2 != null && ! result2.contains( "not found" )  && ! result2.contains( "null" ) ) {
                alarmsList.add ( "Total rejections for today : " + Double.parseDouble (  buildTableModelfromJSON ( result2 ,  "rejection"  )  ) );
            }else{
                alarmsList.add ( "Total rejections not available now "   );
            }    
        } catch ( UnsupportedEncodingException e ) {
            
        }
    }

    public String buildTableModelfromJSON ( String employeeJSONList , String _value  ) {

        JSONArray records = null;
        JSONObject emp = null;
        HashMap<String , Object> map = new HashMap<String , Object> ();
        JSONObject jObject = new JSONObject ( employeeJSONList );
        Iterator<?> keys = jObject.keys ();
        while ( keys.hasNext () ) {
            String key = ( String ) keys.next ();
            Object value = jObject.get ( key );
            map.put ( key , value );
        }
        JSONObject st = null;
        try {
            st = ( JSONObject ) map.get ( "data" );
            
            records = st.getJSONArray ( "records" );
            emp = records.getJSONObject ( 0 );
            return emp.get (  _value  ).toString ()  ;
        } catch ( JSONException | ClassCastException e ) {
            System.out.println ( "AlarmController 3 : line no 625 "+ e.getMessage () ); 
            return "-NA-" ;
        }
    }
    
    
    public ArrayList<String[]> buildListfromJSON ( String employeeJSONList , String[] _values  ) {

        ArrayList<String[]> data = null ;
        
        JSONArray records = null;
        JSONObject emp = null;
        HashMap<String , Object> map = new HashMap<String , Object> ();
        JSONObject jObject = new JSONObject ( employeeJSONList );
        Iterator<?> keys = jObject.keys ();

        while ( keys.hasNext () ) {
            String key = ( String ) keys.next ();
            Object value = jObject.get ( key );
            map.put ( key , value );
        }

        
        JSONObject st = null;

        data  = new  ArrayList<String[]>();
        try {

            st = ( JSONObject ) map.get ( "data" );
            records = st.getJSONArray ( "records" );
            
            for( int i=0;  i<records.length (); i++  ){
                String[] dataItem = new String[ _values.length ];
                for( int j=0  ;    j<_values.length ;   j++  ){
                    emp = records.getJSONObject ( i );
                    dataItem[ j ] = emp.get( _values[j] ).toString() ;
                }
                data.add(   dataItem   );
            }
            
            return  data    ;
        } catch ( JSONException | ClassCastException e ) {
            System.out.println ( "AlarmController 3 : line no 667 " + e.getMessage () ); 
            return new ArrayList<String[]>( Arrays.asList(  new String[]{"-NA-"} , new String[]{"-NA-"})) ;
        }
    }
}
