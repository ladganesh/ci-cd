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

/**
 *
 * @author Rajesh
 */
public class AlarmsController_old {
    
    alarms1 t1 = null ;
    alarms2 t2 = null ;
    alarms3 t3 = null ;
    alarms4 t4 = null ;
    public static showStatusThread ttt = new showStatusThread ();
    
    boolean[] alarmsStates = null;
        String[] alarmsNames = null;
        int[] alarmsFreq = null;
    
    public void getCurrentAlarmState(){
        
        ResultSet rs = DB_Operations.executeSingle ( "select * from ALARMS_SETTINGS" );
        boolean startAlarm = false ;
        try{
            int count = rs.getMetaData ().getColumnCount ();
            alarmsStates = new boolean[ count  ];
            alarmsNames = new String[count];
            alarmsFreq = new int[ count ];

            int i = 0;
            if( rs.isBeforeFirst () ){
                while(rs.next()){
                    alarmsNames[i] =   rs.getString(2) ;
                    alarmsStates[i] = Boolean.parseBoolean( rs.getString(3) );
                    alarmsFreq[i] = Integer.parseInt( rs.getString(4) );
                i++ ;
                }
            }
            //System.out.println ( "checking alarms" );
            
            startAlarms ();
            
        }catch( SQLException sql1 ){
            System.out.println ( ""+sql1 );
        }finally{   try{  
            rs.close() ;
        }catch(SQLException sql2){} }
        

//        if(startAlarm){
//            if( t2!= null && ! t2.isAlive () )
//                t2.start() ;
//            else{
//                for ( int i = 0 ; i < alarmsList.size () ; i ++ ) {
//                    try {
//                        Thread.sleep ( 5000 );
//                    } catch ( InterruptedException ex ) {
//                    }
//                        jLabel1.setText ( alarmsList.get ( i ) );
//                }
//            }
//        }

    }
    
    public void startAlarms(   ){
        
        if ( alarmsStates[0] &&  alarmsNames[0].equals ( "PPM for the day" )) {
                startAlarm1();
        }
        if ( alarmsStates[1] &&  alarmsNames[1].equals ( "Total production for the day"   )) {
                startAlarm2();
        }
        if ( alarmsStates[2] &&  alarmsNames[2].equals (  "Raw Materials stock close to ROL" )) {
                startAlarm3();
        }
        if ( alarmsStates[3] &&  alarmsNames[3].equals ( "Rejections for the day"  )) {
                startAlarm4();
        }
               
     //   if(   alarmsStates[0]  || alarmsStates[1]  || alarmsStates[2]  || alarmsStates[3]   ){
         
            
           
      //  }
        
    }
    
    public void updateAlertMessage(){
        
    }
    
    public void startAlarm1(   ){
        
//        if( AlarmsController.ttt !=null )
//                AlarmsController.ttt.resume();
//            else{
//                AlarmsController.ttt = new showStatusThread ();
//                AlarmsController.ttt.start() ;
//            }
        
            //System.out.println ( " Alarms started at  "+new SimpleDateFormat( "dd MMM, yyyy HH:mm" ).format( Calendar.getInstance ().getTime () )  );
            //HomeScreen.jLabel1.setText ( " Alarms started at  "+new SimpleDateFormat( "dd MMM, yyyy HH:mm:ss" ).format( Calendar.getInstance ().getTime () )  ) ;
            if( t1!=null )
                t1.resume();
            else{
                t1 = new alarms1();
                t1.start() ;
            }
            
    }
    
    public void startAlarm2(){
           //HomeScreen.jLabel1.setText ( " Alarm2 started at  "+new SimpleDateFormat( "dd MMM, yyyy HH:mm:ss" ).format( Calendar.getInstance ().getTime () )  ) ;
            
//           if( AlarmsController.ttt !=null )
//                AlarmsController.ttt.resume();
//            else{
//                AlarmsController.ttt = new showStatusThread ();
//                AlarmsController.ttt.start() ;
//            }
           
            if( t2!=null )
                t2.resume();
            else{
                t2 = new alarms2();
                t2.start() ;
            }
    }
    
    public void startAlarm3(){
           //HomeScreen.jLabel1.setText ( " Alarm3 started at  "+new SimpleDateFormat( "dd MMM, yyyy HH:mm:ss" ).format( Calendar.getInstance ().getTime () )  ) ;
            
//           if( AlarmsController.ttt !=null )
//                AlarmsController.ttt.resume();
//            else{
//                AlarmsController.ttt = new showStatusThread ();
//                AlarmsController.ttt.start() ;
//            }
           
            if( t3!=null )
                t3.resume();
            else{
                t3 = new alarms3();
                t3.start() ;
            }
    }
    
    public void startAlarm4(){
           //HomeScreen.jLabel1.setText ( " Alarm2 started at  "+new SimpleDateFormat( "dd MMM, yyyy HH:mm:ss" ).format( Calendar.getInstance ().getTime () )  ) ;
            
//           if( AlarmsController.ttt !=null )
//                AlarmsController.ttt.resume();
//            else{
//                AlarmsController.ttt = new showStatusThread ();
//                AlarmsController.ttt.start() ;
//            }
           
            if( t4!=null )
                t4.resume();
            else{
                t4 = new alarms4();
                t4.start() ;
            }
    }
    
    public void stopAlarm(){
        
        if( t1!=null && t1.isAlive ())
                t1.suspend();
        
        if( t2!=null && t2.isAlive ())
                t2.suspend();
        
        if( t3!=null && t3.isAlive ())
                t3.suspend();
        
        if( t4!=null && t4.isAlive ())
                t4.suspend();
        
        if( ttt!=null && ttt.isAlive ())
                ttt.suspend();
        
        t1 = null ;
        t2 = null ;
        t3 = null ;
        t4 = null ;
        
        ttt = null ;
        
        HomeScreen.jLabel1.setText ("Alarms Off");
    }
    
    public void showAlarm(){
//        t1.start ();
    }
    
    public void hideAlarms(){
        //System.out.println ( " Stopping alarms at  "+new SimpleDateFormat( "dd MMM, yyyy HH:mm:ss" ).format( Calendar.getInstance ().getTime () )  );
        stopAlarm ();
    }
    
    
    // 1. check what is current alarm state  ?
    
    // If alarms showing then which alarms are being shown ?
    // If alarms are turned on then its should start showing alarms on home screen
    // if alarms are turned off then it should stop showing alarms on home screen
    
    //  whenever the application starts and if alarms are already on the its should start showing alarms
    //  whenever the application starts and if alarms are off then nothing should happen about alarms
    
    //  if alarms screen is open then check for all possibilities of database lock occurrence
    
    // if any master / transaction screen is opne dealing with alarms then alarms for that particular master should be temporarily turned off
    
}

class alarms1  extends Thread{
    @Override
    public void run () {
        super.run (); //To change body of generated methods, choose Tools | Templates.
        
//        boolean fresh = true ;
//        
//        if( AlarmsController.ttt !=null )
//                AlarmsController.ttt.resume();
//            else{
//                AlarmsController.ttt = new showStatusThread ();
//                AlarmsController.ttt.start() ;
//            }
//        
    int i=0;
        while(true ){
 //          HomeScreen.jLabel1.setText ("Showing daily ppm)");
 //           HomeScreen.getTodaysTotalPPM ();
 //           HomeScreen.alarmsList.remove ( HomeScreen.alarmsList.size ()-1 );
           
                    HomeScreen.jLabel1.setText ("Showing daily ppm  "+i++ );
            try{       
                
                sleep(5000);
            } catch ( InterruptedException ex ) {
            }
        }            
    }    
}

class alarms2  extends Thread{
    @Override
    public void run () {
        super.run (); //To change body of generated methods, choose Tools | Templates.
        
//        boolean fresh = true ;
//        
//         if( AlarmsController.ttt !=null )
//                AlarmsController.ttt.resume();
//            else{
//                AlarmsController.ttt = new showStatusThread ();
//                AlarmsController.ttt.start() ;
//            }
        int i=0 ;
        while(true ){
        
//            HomeScreen.getTodaysTotalProduction ();
  //              HomeScreen.jLabel1.setText ("Showing daily production target status)");

//HomeScreen.alarmsList.remove ( HomeScreen.alarmsList.size ()-1 );
   //         HomeScreen.alarmsList.add(  HomeScreen.alarmsList.size ()-1 , "Showing Prodction alarm "+(i++) );
            
             try{      
                    HomeScreen.jLabel1.setText ("Production target  "+i++ );
                
                sleep(5000);
            } catch ( InterruptedException ex ) {
            }
        }            
    }    
}

class alarms3  extends Thread{
    @Override
    public void run () {
        super.run (); //To change body of generated methods, choose Tools | Templates.
        
//        boolean fresh = true ;
//        if( AlarmsController.ttt !=null )
//                AlarmsController.ttt.resume();
//            else{
//                AlarmsController.ttt = new showStatusThread ();
//                AlarmsController.ttt.start() ;
//            }
        int i = 0;
        while(true ){
            
//            HomeScreen.getRMatROL ();
  //                     HomeScreen.jLabel1.setText ("Showing RM levels)");

 ///HomeScreen.alarmsList.remove ( HomeScreen.alarmsList.size ()-1 );
  //          HomeScreen.alarmsList.add(  HomeScreen.alarmsList.size ()-1 ,  "Showing RM level alarm "+i++ );
                    
                    HomeScreen.jLabel1.setText ("Showing RM ROL  "+i++ );
             try{      
                
                sleep(5000);
            } catch ( InterruptedException ex ) {
            }
        }            
    }    
}

class alarms4  extends Thread{
    @Override
    public void run () {
        super.run (); //To change body of generated methods, choose Tools | Templates.
        
        boolean fresh = true ;
        
//        if( AlarmsController.ttt !=null )
//                AlarmsController.ttt.resume();
//            else{
//                AlarmsController.ttt = new showStatusThread ();
//                AlarmsController.ttt.start() ;
//            }
        
        int i = 0;

        while(true ){
            
//            HomeScreen.getRejectionForProducts ();
         
 //HomeScreen.alarmsList.remove ( HomeScreen.alarmsList.size ()-1 );
 //           HomeScreen.alarmsList.add( HomeScreen.alarmsList.size ()-1, "Showing Rejection alarm "+i++ );
                    HomeScreen.jLabel1.setText ("Showing daily rejection  "+i++ );
            try{       
            
                 
                sleep(5000);
            } catch ( InterruptedException ex ) {
            }
        }            
    }    
}



class showStatusThread  extends Thread{
    @Override
    public void run () {
        super.run (); //To change body of generated methods, choose Tools | Templates.
        
        boolean fresh = true ;
        
        while(true ){
            for ( int i = 0 ; i < HomeScreen.alarmsList.size () ; i ++ ) {
               // HomeScreen.jLabel1.setText ( HomeScreen.alarmsList.get ( i ) );
                
                try {
                    sleep(5000);
                } catch ( InterruptedException ex ) {
                }
            }
           
//            for ( int i = 0 ; i < HomeScreen.alarmsList.size () ; i ++ ) {
//                HomeScreen.alarmsList.remove (i  );
//            }
        }            
    }    
}
