/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Rajesh
 */
public class WeekCalculation {
    
    static String fromDate ;
    static String toDate ;
    
    ArrayList<String> WeeklyDates = new ArrayList<String>();
    String[] days = new String[]{  "Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"   } ;
    String firstDayOfWeek = "Sunday" ;
    
    
         SimpleDateFormat sdf = new SimpleDateFormat ("EEEE");
         SimpleDateFormat sdf1 = new SimpleDateFormat ("MMM");
         SimpleDateFormat sdf2 = new SimpleDateFormat ("yyyy");
         SimpleDateFormat sdf3 = new SimpleDateFormat ("dd");
    
         SimpleDateFormat sdfTS = new SimpleDateFormat( "yyyy-MM-dd" );
         
    Calendar c1, c2;
    long totalNoOfDays = 0;
    
    public WeekCalculation( String _fromDate, String _toDate ){
        
        this.fromDate = _fromDate ;
        this.toDate = _toDate ;
        
        System.out.println(_fromDate+ "  From date is :"+formatFromDate (  ));
        System.out.println( _toDate+"  To  date is :"+formatFromDate (  ));
        
        totalNoOfDays = ((((c2.getTimeInMillis ()- c1.getTimeInMillis ())/1000)/60)/60)/24;
        
        // Logic for excluding the holiday
        if(  totalNoOfDays > 6 ){
            if(  totalNoOfDays % 7 ==0){
                System.out.println( "" );


            }
        }
        
        System.out.println("Total no of days "+totalNoOfDays);
        
        showWeek ();
    }
    
    public String formatFromDate( ){
        
          c1 = Calendar.getInstance ();
                  
          String[] dateIP = fromDate.split ( " ");
          
          dateIP = dateIP[0].split ( "-");
         
           fromDate = "" ;
           
            if( dateIP[2].length ()==3 ){
                fromDate = dateIP[2].substring ( 0, 2)+"-";
                c1.set ( Calendar.DATE, Integer.parseInt(dateIP[2].substring ( 0, 2)));
            }else if( dateIP[2].length ()==2 ){
                fromDate = "0"+dateIP[2].substring ( 0, 1)+"-";
                c1.set ( Calendar.DATE, Integer.parseInt(dateIP[2].substring ( 0, 1)));
            }
            
           
          String mon = dateIP[1];
          
          switch(mon){
              
              case "01": fromDate = fromDate + "01-";  c1.set(Calendar.MONTH, 1);         break;
              case "02": fromDate = fromDate + "02-";  c1.set(Calendar.MONTH, 2);     break;
              case "03": fromDate = fromDate + "03-";  c1.set(Calendar.MONTH, 3);     break;
              case "04": fromDate = fromDate + "04-";  c1.set(Calendar.MONTH, 4);     break;
              case "05": fromDate = fromDate + "05-";  c1.set(Calendar.MONTH, 5);     break;
              case "06": fromDate = fromDate + "06-";  c1.set(Calendar.MONTH, 6);     break;
              case "07": fromDate = fromDate + "07-";   c1.set(Calendar.MONTH,7);     break;
              case "08": fromDate = fromDate + "08-";  c1.set(Calendar.MONTH, 8);     break;
              case "09": fromDate = fromDate + "09-";  c1.set(Calendar.MONTH, 9);     break;
              case "10": fromDate = fromDate + "10-";  c1.set(Calendar.MONTH, 10);     break;
              case "11": fromDate = fromDate + "11-";  c1.set(Calendar.MONTH, 11);     break;
              case "12": fromDate = fromDate + "12-";  c1.set(Calendar.MONTH, 12);     break;
                                
          } 
    
    
          
          
         // fromDate = fromDate + dateIP[2];
          c1.set(Calendar.YEAR, Integer.parseInt(dateIP[0]));
          fromDate = sdfTS.format(c1.getTime()) +" 00:00:00" ;
          
 //         System.out.println(  sdf.format(c1.getTime() )+" name of the day");
 //         System.out.println(  sdf1.format(c1.getTime() )+"  month");
 //         System.out.println(  sdf2.format(c1.getTime() )+"  year");
//          System.out.println(  sdf3.format(c1.getTime() )+"  date");  
   
       
//          System.out.println(  sdf.format(c1.getTime() )+" name of the day");
//          System.out.println(  sdf1.format(c1.getTime() )+"  month");
//          System.out.println(  sdf2.format(c1.getTime() )+"  year");
//          System.out.println(  sdf3.format(c1.getTime() )+"  date");
         
         
          return fromDate ;
    }
    
    
    public void showWeek(){
        
        
          WeeklyDates.add ( sdfTS.format(c1.getTime()) +" 00:00:00" );
          
          
          // calculation for first week of fromDate does not fall on Sunday
          String day = sdf.format(c1.getTime() ) ;
          
          if(! (day.equalsIgnoreCase ( "Sunday"))){
            switch(day){

                case "Monday" :        c1.add ( Calendar.DATE, 4);  totalNoOfDays=totalNoOfDays-4;  break ;
                case "Tuesday" :       c1.add ( Calendar.DATE, 3);  totalNoOfDays=totalNoOfDays-3; break ;
                case "Wednesday" :   c1.add ( Calendar.DATE, 2);  totalNoOfDays=totalNoOfDays-2; break ;
                case "Thursday" :      c1.add ( Calendar.DATE, 1);  totalNoOfDays=totalNoOfDays-1; break ;
                case "Friday" :          c1.add ( Calendar.DATE, 0);  totalNoOfDays=totalNoOfDays-0; break ;

            }
          }

           WeeklyDates.add ( sdfTS.format(c1.getTime()) +" 00:00:00" );
            
          
          // Calculation for the week if fromDate fall on Sunday and all subsequent weeks till toDate does not fall on friday
          
          
          
          // Calculation for the last week when toDate fall on Friday
          
          
          
          // Calculation for inbetween weeks
                while( totalNoOfDays>=0 ){
                    
                    c1.add ( Calendar.DATE, 2);
                    WeeklyDates.add ( sdfTS.format(c1.getTime()) +" 00:00:00" );
                    
                    
                    c1.add ( Calendar.DATE, 5);
                    WeeklyDates.add ( sdfTS.format(c1.getTime()) +" 00:00:00" );
                    
                    totalNoOfDays = totalNoOfDays - 7 ;
                    
                }
          
          
                for( int i=0; i<WeeklyDates.size ()/2;  i++ ){
                    System.out.println( WeeklyDates.get ( i) +" "+  WeeklyDates.get(i++));
                }

        
    }
    
    
    
    public String formatToDate(){
        
        c2 = Calendar.getInstance ();
        
        String[] dateIP2 = toDate.split ( " ");
        
        dateIP2 = dateIP2[0].split ( "-");
        
        toDate = "" ;
           
            if( dateIP2[2].length ()==3 ){
                toDate = dateIP2[2].substring ( 0, 2)+"-";
                c2.set ( Calendar.DATE, Integer.parseInt(dateIP2[2].substring ( 0, 2)));
            }else if( dateIP2[2].length ()==2 ){
                toDate = "0"+dateIP2[2].substring ( 0, 1)+"-";
                c2.set ( Calendar.DATE, Integer.parseInt(dateIP2[2].substring ( 0, 1)));
            }
          String mon2 = dateIP2[1];
          
          switch(mon2){
              
              case "01": toDate = toDate + "01-";  c2.set(Calendar.MONTH, 1); break;
              case "02": toDate = toDate + "02-";c2.set(Calendar.MONTH, 2);   break;
              case "03": toDate = toDate + "03-"; c2.set(Calendar.MONTH,3);  break;
              case "04": toDate = toDate + "04-";c2.set(Calendar.MONTH, 4);   break;
              case "05": toDate = toDate + "05-"; c2.set(Calendar.MONTH, 5);  break;
              case "06": toDate = toDate + "06-"; c2.set(Calendar.MONTH, 6);  break;
              case "07": toDate = toDate + "07-"; c2.set(Calendar.MONTH, 7);   break;
              case "08": toDate = toDate + "08-"; c2.set(Calendar.MONTH, 8);  break;
              case "09": toDate = toDate + "09-"; c2.set(Calendar.MONTH, 9);  break;
              case "10": toDate = toDate + "10-"; c2.set(Calendar.MONTH, 10);  break;
              case "11": toDate = toDate + "11-";c2.set(Calendar.MONTH, 11); break;
              case "12": toDate = toDate + "12-";c2.set(Calendar.MONTH, 12);  break;
                                
          }
          
          //toDate = toDate + dateIP2[2];
           c2.set(Calendar.YEAR, Integer.parseInt(dateIP2[0]));
           toDate = sdfTS.format(c2.getTime()) +" 00:00:00" ;
        
            c2.setFirstDayOfWeek ( Calendar.SUNDAY);
        //  System.out.println(  sdf.format(c2.getTime() )+" name of the day");
        //  System.out.println(  sdf1.format(c2.getTime() )+"  month");
         // System.out.println(  sdf2.format(c2.getTime() )+"  year");
         // System.out.println(  sdf3.format(c2.getTime() )+"  date");            
          return toDate ; 
    }
    
    
    
    
}
