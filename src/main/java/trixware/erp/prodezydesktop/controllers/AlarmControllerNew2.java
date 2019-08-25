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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Calendar;

/**
 *
 * @author Rajesh
 */
class PrintDemo {

    //ArrayList<String> alarmsList = new ArrayList<String> ();
    ArrayList<String[]> processMapValues = new ArrayList<String[]> ();

    public void printCount () throws InterruptedException {

        for ( int i = 0 ; i < AlarmControllerNew2.alarmsList.size () ; i ++ ) {
            try {
                Thread.sleep ( 3000 );
            } catch ( InterruptedException e ) {
            }
            System.out.println ( AlarmControllerNew2.alarmsList.size ()+ "    " + AlarmControllerNew2.alarmsList.get ( i ) );
            HomeScreen.jLabel1.setText ( AlarmControllerNew2.alarmsList.get ( i ) );
        }
    }

    public void getTodaysTotalProduction () {

        System.out.println ( "Refreshing production data " );

        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-dd-MM" );

        //String strDate2 = sdf2.format ( c2.getTime () ) + " 00:00:00";
        String strDate2 = "2018-09-27 00:00:00";

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
            System.out.println ( "" );
        } finally {
            try {
                rs.close ();
            } catch ( SQLException ex ) {
                System.out.println ( "1" + ex.getMessage () );
            }
        }

        for ( int i = 0 ; i < processMapValues.size () ; i ++ ) {

            int l1 = 0, l2 = 0;
            try {
                l1 = Integer.parseInt ( processMapValues.get ( i )[ 2 ] );
                l2 = Integer.parseInt ( processMapValues.get ( i )[ 3 ] );
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
                AlarmControllerNew2.alarmsList.add ( "Product " + processMapValues.get ( i )[ 0 ] + " at " + processMapValues.get ( i )[ 1 ] + "  Target Production: " + l2 + ",  Todays production: " + l1 );
            }
        }

    }

}

class PrintDemo2 {

    //ArrayList<String> alarmsList = new ArrayList<String> ();
    public void printCount () throws InterruptedException {

        if ( AlarmControllerNew2.alarmsList.size () > 0 ) {
            try {
                Thread.sleep ( 3000 );
            } catch ( InterruptedException e ) {
            }
            System.out.println (AlarmControllerNew2.alarmsList.size ()+ "    " + AlarmControllerNew2.alarmsList.get ( 0 ) );
            HomeScreen.jLabel1.setText ( AlarmControllerNew2.alarmsList.get ( 0 ) );
        }
    }

    public void getTodaysTotalPPM () {

        System.out.println ( "Refreshing PPM data " );

        //c2 = Calendar.getInstance () ;
        Calendar c2 = Calendar.getInstance ();

        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-dd-MM" );
        //String strDate2 = sdf2.format ( c2.getTime () ) + " 00:00:00";

        String strDate2 = "2018-09-27 00:00:00";

        //alarmsList = new ArrayList<String> ();
        ResultSet rs = null;
        try {

            rs = DB_Operations.executeSingle ( "select round( CAST(((TOTAL(rejection)/TOTAL(qtyout))*1000000) as real) , 2) as 'TOTAL PPM' from dailyreport where rdate = '" + strDate2 + "' " );
            AlarmControllerNew2.alarmsList.add ( "Plant PPM for today : " + Double.parseDouble ( rs.getString ( "TOTAL PPM" ) ) );

        } catch ( SQLException | NullPointerException e ) {
            System.out.println ( "PPM function " + e.getClass () + " " + e.getMessage () );
        } finally {
            try {
                rs.close ();
            } catch ( Exception sql2 ) {
            }
        }
    }
}


class PrintDemo3 {

    //ArrayList<String> alarmsList = new ArrayList<String> ();
    public void printCount () throws InterruptedException {

        if ( AlarmControllerNew2.alarmsList.size () > 0 ) {
            try {
                Thread.sleep ( 3000 );
            } catch ( InterruptedException e ) {
            }
            System.out.println (AlarmControllerNew2.alarmsList.size ()+ "    " + AlarmControllerNew2.alarmsList.get ( 0 ) );
            HomeScreen.jLabel1.setText ( AlarmControllerNew2.alarmsList.get ( 0 ) );
        }
    }    
    public  void getRMatROL () {
        
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

                        AlarmControllerNew2.alarmsList.add ( "Raw Material " + rmname + " available quantity is 0" );
                    } else if ( available > 0 && rol != 0.0 ) {
                        if ( available < rol ) {
                        AlarmControllerNew2.alarmsList.add ( "Raw Material " + rmname + " available quantity " + available + " is less than ROL " + rol );
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


class PrintDemo4 {

    //ArrayList<String> alarmsList = new ArrayList<String> ();
    public void printCount () throws InterruptedException {

        if ( AlarmControllerNew2.alarmsList.size () > 0 ) {
            try {
                Thread.sleep ( 3000 );
            } catch ( InterruptedException e ) {
            }
            System.out.println (AlarmControllerNew2.alarmsList.size ()+ "    " + AlarmControllerNew2.alarmsList.get ( 0 ) );
            HomeScreen.jLabel1.setText ( AlarmControllerNew2.alarmsList.get ( 0 ) );
        }
    }
    public void getRejectionForProducts () {

        System.out.println ( "Refreshing Rejection for product " );
        
        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-dd-MM" );
        //String strDate2 = sdf2.format ( c2.getTime () ) + " 00:00:00";
        String strDate2 = "2018-09-25 00:00:00";

        ArrayList<String[]> ProcProdRej = new ArrayList<String[]> ();
        ArrayList<String[]> TodaysRej = new ArrayList<String[]> ();

        // 1. get the details of processes , products that are in progress today and actual rejections for the day in correspoding processes and products
        //     select processid, fgid, rejection from dailyreport where rdate = strDate2 AND rejection > 0

        // 2. get the details of processes , products and corresponding rejection limits
        //     select MAX_REJ_PERM from FG_PROCESS_MACH_MAP where REF_FG_ID = 11 AND REF_PROCESS_ID = 8
         ResultSet rs = null ;
        try {
         
            //for ( int i = 0 ; i < TodaysRej.size () ; i ++ ) {
                rs = DB_Operations.executeSingle ( "SELECT \n" +
                        "processid, fgid, rejection,\n" +
                        "FG_PROCESS_MACH_MAP.MAX_REJ_PERM\n" +
                        "FROM dailyreport\n" +
                        "INNER JOIN FG_PROCESS_MACH_MAP ON FG_PROCESS_MACH_MAP.REF_FG_ID = dailyreport.fgid \n" +
                        "WHERE FG_PROCESS_MACH_MAP.REF_PROCESS_ID = dailyreport.processid \n" +
                        "AND dailyreport.rdate = '2018-09-27 00:00:00' \n" +
                        "AND dailyreport.rejection >= FG_PROCESS_MACH_MAP.MAX_REJ_PERM" );
                while ( rs.next () ) {
                    int actualRej =  rs.getInt(3);
                    int maxRej=  rs.getInt(4);
                    ///ProcProdRej.add ( new String[] { TodaysRej.get ( i )[ 0 ] , TodaysRej.get ( i )[ 1 ] , rs.getString ( 1 ) } );
                    if ( actualRej > maxRej ) {
                        AlarmControllerNew2.alarmsList.add ( "Rejection in process " + rs.getString(1) + " for product " + rs.getString(2) + " is " + rs.getInt(3) + " - higher than max permissible  " + rs.getInt(4) );
                    } else if ( actualRej == maxRej ) {
                        AlarmControllerNew2.alarmsList.add ( "Rejection in process " + rs.getString(1)  + " for product " + rs.getInt(2) + " is " + rs.getInt(3) + " - reached max permissible  " + rs.getInt(4) );
                    }
                }
                rs.close ();
            //}

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
//        for ( int i = 0 ; i < TodaysRej.size () ; i ++ ) {
//
//            for ( int j = 0 ; j < ProcProdRej.size () ; j ++ ) {
//
//                if ( TodaysRej.get ( i )[ 0 ].equals ( ProcProdRej.get ( j )[ 0 ] ) && TodaysRej.get ( i )[ 1 ].equals ( ProcProdRej.get ( j )[ 1 ] ) ) {
//                    int actualRej = Integer.parseInt ( TodaysRej.get ( i )[ 2 ] );
//                    int maxRej = Integer.parseInt ( ProcProdRej.get ( i )[ 2 ] );
//
//                    if ( actualRej > maxRej ) {
//                        AlarmControllerNew2.alarmsList.add ( "Rejection in process " + TodaysRej.get ( i )[ 0 ] + " for product " + TodaysRej.get ( i )[ 1 ] + " is " + actualRej + " - higher than max permissible  " + maxRej );
//                    } else if ( actualRej == maxRej ) {
//                        AlarmControllerNew2.alarmsList.add ( "Rejection in process " + TodaysRej.get ( i )[ 0 ] + " for product " + TodaysRej.get ( i )[ 1 ] + " is " + actualRej + " - reached max permissible  " + maxRej );
//                    }
//                }
//            }
//        }

    }
}



class ThreadDemo extends Thread {

    private Thread t;
    private String threadName;
    PrintDemo PD;
    boolean running = true;

    ThreadDemo ( String name , PrintDemo pd ) {
        threadName = name;
        PD = pd;
    }

    public void run () {
        while ( running ) {
            try {
                synchronized ( this ) {
                    PD.printCount();
                }
            } catch ( InterruptedException ex ) {
                System.out.println ( " " + ex.getMessage () );
            }
        }

    }
    public void stopThis () {
        running = false;
        System.out.println ( " Running " + running );
    }
    public void start () {
        //System.out.println("Starting " +  threadName );
        if ( t == null ) {
            t = new Thread ( this , threadName );
            t.start ();
        }
    }
}

class ThreadDemo2 extends Thread {

    private Thread t;
    private String threadName;
    PrintDemo2 PD2;
    boolean running = true;

    ThreadDemo2 ( String name , PrintDemo2 pd ) {
        threadName = name;
        PD2 = pd;
    }

    public void run () {
        while ( running ) {
            try {
                synchronized ( this ) {
                    PD2.printCount ();
                }
            } catch ( InterruptedException ex ) {
                System.out.println ( " " + ex.getMessage () );
            }
        }
    }
    public void stopThis () {
        running = false;
        System.out.println ( " Running " + running );
    }
    public void start () {
        //System.out.println("Starting " +  threadName );
        if ( t == null ) {
            t = new Thread ( this , threadName );
            t.start ();
        }
    }
}

class ThreadDemo3 extends Thread {

    private Thread t;
    private String threadName;
    PrintDemo3 PD3;
    boolean running = true;

    ThreadDemo3( String name , PrintDemo3 pd ) {
        threadName = name;
        PD3 = pd;
    }

    public void run () {
        while ( running ) {
            try {
                synchronized ( this ) {
                    PD3.printCount ();
                }
            } catch ( InterruptedException ex ) {
                System.out.println ( " " + ex.getMessage () );
            }
        }
    }
    public void stopThis () {
        running = false;
        System.out.println ( " Running " + running );
    }
    public void start () {
        //System.out.println("Starting " +  threadName );
        if ( t == null ) {
            t = new Thread ( this , threadName );
            t.start ();
        }
    }
}

class ThreadDemo4 extends Thread {

    private Thread t;
    private String threadName;
    PrintDemo4 PD4;
    boolean running = true;

    ThreadDemo4 ( String name , PrintDemo4 pd4 ) {
        threadName = name;
        PD4 = pd4;
    }

    public void run () {
        while ( running ) {
            try {
                synchronized ( this ) {
                    PD4.printCount ();
                }
            } catch ( InterruptedException ex ) {
                System.out.println ( " " + ex.getMessage () );
            }
        }
    }
    public void stopThis () {
        running = false;
        System.out.println ( " Running " + running );
    }
    public void start () {
        //System.out.println("Starting " +  threadName );
        if ( t == null ) {
            t = new Thread ( this , threadName );
            t.start ();
        }
    }
}

class RefrehData extends Thread {

    private Thread t;
    PrintDemo PD;
    PrintDemo2 PD2;
    PrintDemo3 PD3 ; 
    PrintDemo4 PD4 ;
    boolean running = true;

    RefrehData ( PrintDemo pd , PrintDemo2 pd2,  PrintDemo3 pd3 , PrintDemo4 pd4 ) {
        //threadName = name;
        PD = pd;
        PD2 = pd2;
        PD3 = pd3;
        PD4 = pd4;
    }

    public void run () {
        while ( running ) {
            synchronized ( this ) {
                AlarmControllerNew2.alarmsList = new ArrayList<String> ();
                try {
                    synchronized ( this ) {
                        PD.getTodaysTotalProduction ();
                        PD2.getTodaysTotalPPM ();
                        PD3.getRMatROL ();
                        PD4.getRejectionForProducts ();
                        Thread.sleep ( 20000 );
                    }
                } catch ( InterruptedException ex ) {
                }
            }
            // System.out.println("Thread " +  threadName + " exiting.");
        }
    }

    public void stopThis () {
        running = false;
        System.out.println ( " Running " + running );
    }

    public void start () {
        System.out.println ( "Refreshing data " );
        if ( t == null ) {
            t = new Thread ( this );
            t.start ();
        }
    }
}

public class AlarmControllerNew2 {

    boolean[] alarmsStates = null;
    String[] alarmsNames = null;
    int[] alarmsFreq = null;

    PrintDemo PD = null;
    PrintDemo2 PD2 = null;
    PrintDemo3 PD3 = null;
    PrintDemo4 PD4 = null;

    ThreadDemo T1 = null;
    ThreadDemo2 T2 = null;
    ThreadDemo3 T4 = null;
    ThreadDemo4 T5 = null;
    
    RefrehData T3 = null;

    public static ArrayList<String> alarmsList = new ArrayList<String> ();

    public AlarmControllerNew2 () {

        PD = new PrintDemo ();
        PD2 = new PrintDemo2 ();
        PD3 = new PrintDemo3();
        PD4 = new PrintDemo4()  ;

        T1 = new ThreadDemo ( "Showing alarm - 1 " , PD );
        T2 = new ThreadDemo2 ( "Showing alarm - 2 " , PD2 );
        T4 = new ThreadDemo3 ( "Showing alarm - 1 " , PD3 );
        T5 = new ThreadDemo4 ( "Showing alarm - 2 " , PD4 );

        T3 = new RefrehData ( PD , PD2, PD3, PD4 );

        //T1.start ();
        //T2.start ();
        //T3.start ();
        // wait for threads to end
//        try {
//            T1.join ();
//            T2.join ();
//            T3.join ();
//
//        } catch ( Exception e ) {
//            System.out.println ( "Interrupted" );
//        }
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

            if ( alarmsStates[ 0 ] && alarmsNames[ 0 ].equals ( "PPM for the day" ) ) {
                T2.start ();
                try {
                    T2.join ();
                } catch ( InterruptedException ex ) {
                }
            }
            if ( alarmsStates[ 1 ] && alarmsNames[ 1 ].equals ( "Total production for the day" ) ) {
                T1.start ();
                try {
                    T1.join ();
                } catch ( InterruptedException ex ) {
                }
            }
            if ( alarmsStates[ 2 ] && alarmsNames[ 2 ].equals ( "Raw Materials stock close to ROL" ) ) {
                T4.start ();
                try {
                    T4.join ();
                } catch ( InterruptedException ex ) {
                }
            }
            if ( alarmsStates[ 3 ] && alarmsNames[ 3 ].equals ( "Rejections for the day" ) ) {
                T5.start ();
                try {
                    T5.join ();
                } catch ( InterruptedException ex ) {
                }
            }

            if ( alarmsStates[ 0 ] || alarmsStates[ 1 ] || alarmsStates[ 2 ] || alarmsStates[ 3 ] ) {
                T3.start ();
                try {
                    T3.join ();
                } catch ( InterruptedException ex ) {
                }
            }else{
                stopThreads ();
            }

        } catch ( SQLException sql1 ) {
            System.out.println ( " Error in Alarm Controller : " + sql1 );
        } finally {
            try {
                rs.close ();
            } catch ( SQLException sql2 ) {
            }
        }
    }

    public void stopThreads () {

        if ( T1 != null ) {
            T1.stopThis ();
        }

        if ( T2 != null ) {
            T2.stopThis ();
        }

        if ( T3 != null ) {
            T3.stopThis ();
        }
        
        if ( T4 != null ) {
            T4.stopThis ();
        }
        
        if ( T4 != null ) {
            T4.stopThis ();
        }
    }
}


/*
 * 
 *select 
fgid,
FG_PROCESS_MACH_MAP.REF_PROCESS_ID,
qtyout,  
FG_PROCESS_MACH_MAP.TG_OPT_DAY 
from dailyreport
inner join FG_PROCESS_MACH_MAP ON FG_PROCESS_MACH_MAP.REF_FG_ID = dailyreport.fgid
WHERE  FG_PROCESS_MACH_MAP.REF_PROCESS_ID = dailyreport.processid 
AND dailyreport.rdate = '2018-09-27 00:00:00'
AND FG_PROCESS_MACH_MAP.TG_OPT_DAY <= dailyreport.qtyout
 * 
 */