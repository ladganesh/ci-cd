/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.controllers;

import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.examples.HomeScreen;
import static java.lang.Thread.sleep;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 *
 * @author Rajesh
 */
public class AlarmsController {

    public Calendar c2;
    public static ArrayList<String[]> processMapValues = new ArrayList<String[]> ();
    public static ArrayList<String[]> productionTargetVSActual = new ArrayList<String[]> ();
    showStatusThread2 abc = new showStatusThread2 ();
    public static ShowProductionAlarm a1;
    public static ShowPPMAlarm a2;

    
    
    
    public AlarmsController () {

    }

    public void getCurrentAlarmState () {

        ResultSet rs = DB_Operations.executeSingle ( "select * from ALARMS_SETTINGS" );
        boolean startAlarm = false;
        try {
            int count = rs.getMetaData ().getColumnCount ();
            alarmsStates = new boolean[ count ];
            alarmsNames = new String[ count ];
            alarmsFreq = new int[ count ];

            int i = 0;
            if ( rs.isBeforeFirst () ) {
                while ( rs.next () ) {
                    alarmsNames[ i ] = rs.getString ( 2 );
                    alarmsStates[ i ] = Boolean.parseBoolean ( rs.getString ( 3 ) );
                    alarmsFreq[ i ] = Integer.parseInt ( rs.getString ( 4 ) );
                    i ++;
                }
            }
            //System.out.println ( "checking alarms" );

            startAlarms ();

        } catch ( SQLException sql1 ) {
            System.out.println ( "" + sql1 );
        } finally {
            try {
                rs.close ();
            } catch ( SQLException sql2 ) {
            }
        }

    }

    boolean[] alarmsStates = null;
    String[] alarmsNames = null;
    int[] alarmsFreq = null;

    public void startAlarms () {
        
        

        if ( alarmsStates[ 0 ] && alarmsNames[ 0 ].equals ( "PPM for the day" ) ) {
            new showStatusThread2 ().start ();
            //a2 = new ShowPPMAlarm ();
            a1 = new ShowProductionAlarm ();
            
        }else{
            for ( int i = 0 ; i < HomeScreen.alarmsList.size () ; i ++ ) 
            {
                HomeScreen.alarmsList.remove ( i )  ;
                HomeScreen.jLabel1.setText ("" );
            }    
        }
        if ( alarmsStates[1] &&  alarmsNames[1].equals ( "Total production for the day"   )) {
             new showStatusThread2 ().start ();
                a1 = new ShowProductionAlarm ();
        }
//        if ( alarmsStates[2] &&  alarmsNames[2].equals (  "Raw Materials stock close to ROL" )) {
//                startAlarm3();
//        }
//        if ( alarmsStates[3] &&  alarmsNames[3].equals ( "Rejections for the day"  )) {
//                startAlarm4();
//        }           

    }

}

class showStatusThread2 extends Thread {

    static boolean stopAlarm = false;

    public static void stopThread () {

        stopAlarm = true;

    }

    @Override
    public void run () {
        super.run (); //To change body of generated methods, choose Tools | Templates.

        if (  ! stopAlarm ) {
            while ( true ) {

                for ( int i = 0 ; i < HomeScreen.alarmsList.size () ; i ++ ) {
                    HomeScreen.alarmsList.remove ( i );
                }

                HomeScreen.jLabel1.setText ( "" );
               // a1 = null;
               /// a2 = null;

//                a1 = new ShowProductionAlarm ();
//                a2 = new ShowPPMAlarm ();

                for ( int i = 0 ; i < HomeScreen.alarmsList.size () ; i ++ ) {
                    HomeScreen.jLabel1.setText ( AlarmsController.a1.productionAlarmsList.get ( i ) );
                    System.out.println ( "" + AlarmsController.a1.productionAlarmsList.get ( i ));
                }
                try {
                    sleep ( 5000 );
                } catch ( InterruptedException ex ) {
                }
                
                System.out.println ( "\n");
            }
         
        }else{
                  
        }
    }
}



class ShowPPMAlarm implements Runnable {

    public static Calendar c2;
    
    public static ArrayList<String> ppmAlarmsList = new ArrayList<String>();

    public static void getTodaysTotalPPM () {

        //c2 = Calendar.getInstance () ;
        c2 = Calendar.getInstance ();

        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-dd-MM" );
        //String strDate2 = sdf2.format ( c2.getTime () ) + " 00:00:00";

        String strDate2 = "2018-09-27 00:00:00";

         ppmAlarmsList = new ArrayList<String>();
         
        ResultSet rs = null;
        try {

           
            rs = DB_Operations.executeSingle ( "select round( CAST(((TOTAL(rejection)/TOTAL(qtyout))*1000000) as real) , 2) as 'TOTAL PPM' from dailyreport where rdate = '" + strDate2 + "' " );
            ppmAlarmsList.add (  "Plant PPM for today : " + Double.parseDouble ( rs.getString ( "TOTAL PPM" ) )  );

        } catch ( SQLException | NullPointerException e ) {
            System.out.println ( "PPM function " + e.getClass () + " " + e.getMessage () );
        } finally {
            try {
                rs.close ();
            } catch ( Exception sql2 ) {
            }
        }
    }

    public ShowPPMAlarm () {

        Thread t = new Thread ( this );
        t.start ();
        try {
            t.join ();
        } catch ( InterruptedException ex ) {
        }
    }

    @Override
    public void run () {
        if (  ! stopPPMAlarm ) {
            try {
                while ( true ) {
                    getTodaysTotalPPM ();
                    Thread.sleep ( 5000 );
                }
            } catch ( InterruptedException x ) {
            }
        }
    }

    public static void stopThread () {

        stopPPMAlarm = true;

    }

    public static boolean stopPPMAlarm = false;
}

class ShowProductionAlarm implements Runnable {

    public static Calendar c2;
    
    public static ArrayList<String> productionAlarmsList = new ArrayList<String>();

    public ShowProductionAlarm () {

        Thread t = new Thread ( this );
        t.start ();
        try {
            t.join ();
        } catch ( InterruptedException ex ) {
        }
    }

    public static void getTodaysTotalProduction () {

        c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-dd-MM" );

        //String strDate2 = sdf2.format ( c2.getTime () ) + " 00:00:00";
        String strDate2 = "2018-09-27 00:00:00";

        //productionAlarmsList = new ArrayList<String>();
        
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
                AlarmsController.processMapValues.add ( new String[] { "" + rs.getString ( 1 ) , "" + rs.getString ( 2 ) , "" + rs.getString ( 3 ) , "" + rs.getString ( 4 ) } );
            }
        } catch ( SQLException e ) {
            System.out.println ( "" );
        } finally {
            try {
                rs.close ();
            } catch ( SQLException ex ) {
                System.out.println ( "1" + ex.getMessage () );
            }
        }
      
        for ( int i = 0 ; i < AlarmsController.processMapValues.size () ; i ++ ) {

            int l1 = 0, l2 = 0;
            try {
                l1 = Integer.parseInt ( AlarmsController.processMapValues.get ( i )[ 2 ] );
                l2 = Integer.parseInt ( AlarmsController.processMapValues.get ( i )[ 3 ] );
            } catch ( NumberFormatException e ) {
                System.out.println ();
            } finally {
                try {
                    rs.close ();
                } catch ( SQLException ex ) {
                    System.out.println ( "5" + ex.getMessage () );
                }
            }

            if ( l1 >= l2 && l1 != 0 && l2 != 0 ) {
                //processMapValues.add( new String[]{  ""+l1, ""+l2  }); 
                productionAlarmsList.add (  "Product " + AlarmsController.processMapValues.get ( i )[ 0 ] + " at " + AlarmsController.processMapValues.get ( i )[ 1 ] + "  Target Production: " + l2 + ",  Todays production: " + l1 );
            }
        }

    }

    @Override
    public void run () {

        if (  ! stopPPMAlarm ) {
            try {
                while ( true ) {
                    getTodaysTotalProduction ();
                    Thread.sleep ( 5000 );
                }
            } catch ( InterruptedException x ) {
            }
        }

    }

    public static void stopThread () {

        stopPPMAlarm = true;

    }

    public static boolean stopPPMAlarm = false;

}

class ShowRMAtROL {

    public static void getRMatROL () {
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

                        //         HomeScreen.alarmsList.add ( "Raw Material " + rmname + " available quantity is 0" );
                    } else if ( available > 0 && rol != 0.0 ) {
                        if ( available < rol ) {
                            //             HomeScreen.alarmsList.add ( "Raw Material " + rmname + " available quantity " + available + " is less than ROL " + rol );
                        }
                    }
                }
            }
            rs.close ();
        } catch ( SQLException e ) {
            System.out.println ( e.getClass () + "  " + e.getMessage () );
        } finally {
            try {
                rs.close ();
            } catch ( SQLException sql2 ) {
            }
        }

    }

}

class ShowRejectionProductWiseAlarm {

    public static Calendar c2 = null;

    public static void getRejectionForProducts () {

        c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-dd-MM" );
        //String strDate2 = sdf2.format ( c2.getTime () ) + " 00:00:00";
        String strDate2 = "2018-09-25 00:00:00";

        ArrayList<String[]> ProcProdRej = new ArrayList<String[]> ();
        ArrayList<String[]> TodaysRej = new ArrayList<String[]> ();

        // 1. get the details of processes , products that are in progress today and actual rejections for the day in correspoding processes and products
        //     select processid, fgid, rejection from dailyreport where rdate = strDate2 AND rejection > 0
//        ResultSet rs = DB_Operations.executeSingle ( "select processid, fgid, rejection from dailyreport where rdate = '" + strDate2 + "' AND rejection > 0" );
//        try {
//            while ( rs.next () ) {
//                TodaysRej.add ( new String[] { rs.getString ( 1 ) , rs.getString ( 2 ) , rs.getString ( 3 ) } );
//            }
//            rs.close ();
//        } catch ( SQLException e ) {
//            try {
//                rs.close ();
//            } catch ( SQLException ex ) {
//
//            }
//        } finally {
//            try {
//                rs.close ();
//            } catch ( SQLException sql2 ) {
//            }
//        }

        // 2. get the details of processes , products and corresponding rejection limits
        //     select MAX_REJ_PERM from FG_PROCESS_MACH_MAP where REF_FG_ID = 11 AND REF_PROCESS_ID = 8
         ResultSet rs = null ;
        try {
         
            for ( int i = 0 ; i < TodaysRej.size () ; i ++ ) {
                rs = DB_Operations.executeSingle ( "SELECT \n" +
                        "processid, fgid, rejection,\n" +
                        "FG_PROCESS_MACH_MAP.MAX_REJ_PERM\n" +
                        "FROM dailyreport\n" +
                        "INNER JOIN FG_PROCESS_MACH_MAP ON FG_PROCESS_MACH_MAP.REF_FG_ID = dailyreport.fgid \n" +
                        "WHERE FG_PROCESS_MACH_MAP.REF_PROCESS_ID = dailyreport.processid \n" +
                        "AND dailyreport.rdate = '2018-09-27 00:00:00' \n" +
                        "AND dailyreport.rejection >= FG_PROCESS_MACH_MAP.MAX_REJ_PERM" );
                while ( rs.next () ) {
                    ProcProdRej.add ( new String[] { TodaysRej.get ( i )[ 0 ] , TodaysRej.get ( i )[ 1 ] , rs.getString ( 1 ) } );
                }
                rs.close ();
            }

        } catch ( SQLException e ) {
            try {
                rs.close ();
            } catch ( SQLException ex ) {

            }
        } finally {
            try {
                rs.close ();
            } catch ( SQLException sql2 ) {
            }
        }

        // 3. compare corresponding processe-products and actual vs maximum rejection
        for ( int i = 0 ; i < TodaysRej.size () ; i ++ ) {

            for ( int j = 0 ; j < ProcProdRej.size () ; j ++ ) {

                if ( TodaysRej.get ( i )[ 0 ].equals ( ProcProdRej.get ( j )[ 0 ] ) && TodaysRej.get ( i )[ 1 ].equals ( ProcProdRej.get ( j )[ 1 ] ) ) {
                    int actualRej = Integer.parseInt ( TodaysRej.get ( i )[ 2 ] );
                    int maxRej = Integer.parseInt ( ProcProdRej.get ( i )[ 2 ] );

                    if ( actualRej > maxRej ) {
                        //          HomeScreen.alarmsList.add ( "Rejection in process " + TodaysRej.get ( i )[ 0 ] + " for product " + TodaysRej.get ( i )[ 1 ] + " is " + actualRej + " - higher than max permissible  " + maxRej );
                    } else if ( actualRej == maxRej ) {
                        //          HomeScreen.alarmsList.add ( "Rejection in process " + TodaysRej.get ( i )[ 0 ] + " for product " + TodaysRej.get ( i )[ 1 ] + " is " + actualRej + " - reached max permissible  " + maxRej );
                    }
                }
            }
        }

    }
}

class Notification {
    // Prints a string and waits for consume()

    public void produce () throws InterruptedException {
        // synchronized block ensures only one thread
        // running at a time.
        synchronized ( this ) {
            System.out.println ( "producer thread running" );

            // releases the lock on shared resource
            wait ();

            // and waits till some other method invokes notify().
            System.out.println ( "Resumed" );
        }
    }

    // Sleeps for some time and waits for a key press. After key
    // is pressed, it notifies produce().
    public void consume () throws InterruptedException {
        // this makes the produce thread to run first.
        Thread.sleep ( 1000 );

        // synchronized block ensures only one thread
        // running at a time.
        synchronized ( this ) {
            System.out.println ( "Waiting for return key." );

            System.out.println ( "Return key pressed" );

            // notifies the produce thread that it
            // can wake up.
            notify ();

            // Sleep
            Thread.sleep ( 2000 );
        }
    }
}