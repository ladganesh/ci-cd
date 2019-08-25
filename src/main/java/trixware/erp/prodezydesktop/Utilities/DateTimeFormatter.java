/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Rajesh
 */
public class DateTimeFormatter {
    
    
    static boolean formIncomplete = false ;
    static Calendar c1 = null, c2 = null;
    public static SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-MM-dd" );
    public static SimpleDateFormat sdf1 = new SimpleDateFormat ( "HH:mm:ss" );
    
    static Calendar c3 = Calendar.getInstance ();
        static Calendar c4 = Calendar.getInstance ();
        
        static int fromHr, fromMin, toHr, toMin ;
        
        public static  String fromTime( String _fromTime){
            
            String[] time = _fromTime.split(" ");
            
             if(time[2].equals ( "PM")){
                        fromHr   = Integer.parseInt( time[0]) + 12;
                    //    c3.set ( Calendar.AM_PM, Calendar.PM);
                    }else{
                        fromHr   = Integer.parseInt( time[0]) ;
                     //   c3.set ( Calendar.AM_PM, Calendar.AM);
                    }
                    fromMin = Integer.parseInt(time[1]);
                   
                    if(fromHr == 0 ){
                        if(fromMin==0){
                            formIncomplete = true ;
                           }else{
                            formIncomplete = false ;
                        }
                    }else{
                            formIncomplete = false ;
                    }
                    
                    
                    c3.set ( Calendar.HOUR , Integer.parseInt( time[0]));
                    c3.set ( Calendar.MINUTE , fromMin);
                    if(time[2].equals ( "PM")){
                        c3.set ( Calendar.AM_PM, Calendar.PM);
                    }else{
                        c3.set ( Calendar.AM_PM, Calendar.AM);
                    }
                    
                   return sdf1.format(c3.getTime()) ;
            
        }
        
        public static String toTime( String _toTime ){
            
            String[] time  = _toTime.split(" ");
            
                     if( time[2].equals ( "PM")){
                        toHr   = Integer.parseInt( time[0] ) + 12;
                    //    c4.set ( Calendar.AM_PM, Calendar.PM);
                    }else{
                        toHr   = Integer.parseInt( time[0] ) ;
                    //    c4.set ( Calendar.AM_PM, Calendar.AM);
                    }
                    toMin = Integer.parseInt( time[1] );
                    
                    if(toHr == 0 ){
                        if(toMin==0){
                            formIncomplete = true ;
                        }else{
                            formIncomplete = false ;
                        }
                    }else{
                            formIncomplete = false ;
                    }
                    
                    c4.set ( Calendar.HOUR , Integer.parseInt( time[0] ));
                    c4.set ( Calendar.MINUTE , toMin);
                    if( time[2].equals ( "PM")){
                         c4.set ( Calendar.AM_PM, Calendar.PM);
                    }else{
                         c4.set ( Calendar.AM_PM, Calendar.AM);
                    }
            
                    
                    return sdf1.format(c4.getTime()); 
        }
        
    public static String formatFromDate (   String _fromDate ) {

        
        c1 = Calendar.getInstance ();
    
        String[] dateIP = _fromDate.split ( " " );

        String fromDate = null;

        if ( dateIP[ 1 ].length () == 3 ) {
            fromDate = dateIP[ 1 ].substring ( 0 , 2 ) + "-";
            c1.set ( Calendar.DATE , Integer.parseInt ( dateIP[ 1 ].substring ( 0 , 2 ) ) );
        } else if ( dateIP[ 1 ].length () == 2 ) {
            fromDate = "0" + dateIP[ 1 ].substring ( 0 , 1 ) + "-";
            c1.set ( Calendar.DATE , Integer.parseInt ( dateIP[ 1 ].substring ( 0 , 1 ) ) );
        }

        String mon = dateIP[ 0 ];

        switch ( mon ) {

            case "Jan":
                fromDate = fromDate + "01-";
                c1.set ( Calendar.MONTH , 0 );
                break;
            case "Feb":
                fromDate = fromDate + "02-";
                c1.set ( Calendar.MONTH , 1 );
                break;
            case "Mar":
                fromDate = fromDate + "03-";
                c1.set ( Calendar.MONTH , 2 );
                break;
            case "Apr":
                fromDate = fromDate + "04-";
                c1.set ( Calendar.MONTH , 3 );
                break;
            case "May":
                fromDate = fromDate + "05-";
                c1.set ( Calendar.MONTH , 4 );
                break;
            case "Jun":
                fromDate = fromDate + "06-";
                c1.set ( Calendar.MONTH , 5 );
                break;
            case "Jul":
                fromDate = fromDate + "07-";
                c1.set ( Calendar.MONTH , 6 );
                break;
            case "Aug":
                fromDate = fromDate + "08-";
                c1.set ( Calendar.MONTH , 7 );
                break;
            case "Sep":
                fromDate = fromDate + "09-";
                c1.set ( Calendar.MONTH , 8 );
                break;
            case "Oct":
                fromDate = fromDate + "10-";
                c1.set ( Calendar.MONTH , 9 );
                break;
            case "Nov":
                fromDate = fromDate + "11-";
                c1.set ( Calendar.MONTH , 10 );
                break;
            case "Dec":
                fromDate = fromDate + "12-";
                c1.set ( Calendar.MONTH , 11 );
                break;

        }

        // fromDate = fromDate + dateIP[2];
        c1.set ( Calendar.YEAR , Integer.parseInt ( dateIP[ 2 ] ) );
        fromDate = sdf2.format ( c1.getTime () ) + " 00:00:00";
        

        //   c1.setFirstDayOfWeek ( Calendar.SUNDAY);
        //   System.out.println( "This is #"+  c1.get ( Calendar.WEEK_OF_MONTH) +" week in the month");
        //  System.out.println( "This is #"+  (c1.get ( Calendar.MONTH) +1)+"  month in the year");
//        SimpleDateFormat sdf = new SimpleDateFormat ( "EEEE" );
//        SimpleDateFormat sdf1 = new SimpleDateFormat ( "MMM" );
//        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy" );
//        SimpleDateFormat sdf3 = new SimpleDateFormat ( "dd" );

    
        return fromDate;
    }

    public static String formatToDate (String _toDate ) {

        c2 = Calendar.getInstance () ;
         
        String[] dateIP2 =   _toDate.split ( " " );
        String toDate = null;

        if ( dateIP2[ 1 ].length () == 3 ) {
            toDate = dateIP2[ 1 ].substring ( 0 , 2 ) + "-";
            c2.set ( Calendar.DATE , Integer.parseInt ( dateIP2[ 1 ].substring ( 0 , 2 ) ) );
        } else if ( dateIP2[ 1 ].length () == 2 ) {
            toDate = "0" + dateIP2[ 1 ].substring ( 0 , 1 ) + "-";
            c2.set ( Calendar.DATE , Integer.parseInt ( dateIP2[ 1 ].substring ( 0 , 1 ) ) );
        }
        String mon2 = dateIP2[ 0 ];

        switch ( mon2 ) {

            case "Jan":
                toDate = toDate + "01-";
                c2.set ( Calendar.MONTH , 0 );
                break;
            case "Feb":
                toDate = toDate + "02-";
                c2.set ( Calendar.MONTH , 1 );
                break;
            case "Mar":
                toDate = toDate + "03-";
                c2.set ( Calendar.MONTH , 2 );
                break;
            case "Apr":
                toDate = toDate + "04-";
                c2.set ( Calendar.MONTH , 3 );
                break;
            case "May":
                toDate = toDate + "05-";
                c2.set ( Calendar.MONTH , 4 );
                break;
            case "Jun":
                toDate = toDate + "06-";
                c2.set ( Calendar.MONTH , 5 );
                break;
            case "Jul":
                toDate = toDate + "07-";
                c2.set ( Calendar.MONTH , 6 );
                break;
            case "Aug":
                toDate = toDate + "08-";
                c2.set ( Calendar.MONTH , 7 );
                break;
            case "Sep":
                toDate = toDate + "09-";
                c2.set ( Calendar.MONTH , 8 );
                break;
            case "Oct":
                toDate = toDate + "10-";
                c2.set ( Calendar.MONTH , 9 );
                break;
            case "Nov":
                toDate = toDate + "11-";
                c2.set ( Calendar.MONTH , 10 );
                break;
            case "Dec":
                toDate = toDate + "12-";
                c2.set ( Calendar.MONTH , 11 );
                break;

        }

        //toDate = toDate + dateIP2[2];
        c2.set ( Calendar.YEAR , Integer.parseInt ( dateIP2[ 2 ] ) );
        toDate = sdf2.format ( c2.getTime () ) + " 00:00:00";

        c2.setFirstDayOfWeek ( Calendar.SUNDAY );
        //   System.out.println( "This is #"+  c2.get ( Calendar.WEEK_OF_MONTH) +" week in the month");
        //  System.out.println( "This is #"+  (c2.get ( Calendar.MONTH) +1)+" month in the year");

       return toDate;
    }
    
}
