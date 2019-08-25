/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

import trixware.erp.prodezydesktop.controllers.AlarmController3;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import trixware.erp.prodezydesktop.Model.AlarmData;
import trixware.erp.prodezydesktop.Model.StaticValues;
import java.util.Calendar;
/**
 *
 * @author Rajesh
 */
public class Alarms extends javax.swing.JPanel {

    String alarmsDefaultSettingQuery = null ;
    public boolean[] alarmsStates = null;
    public  String[] alarmsNames = null;
    public  int[] alarmsFreq = null;
    
        ArrayList<AlarmData> alarmData = new ArrayList<AlarmData>();
        
    /**
     * Creates new form Alarms
     */
    public Alarms () {
        initComponents ();
        
        
        
        jCheckBox1.addActionListener( mainSwitch  ) ;
        
        jCheckBox2.addActionListener( subSwitch  ) ;
        jCheckBox3.addActionListener( subSwitch  ) ;
        jCheckBox4.addActionListener( subSwitch  ) ; 
        jCheckBox5.addActionListener( subSwitch  ) ;
        
        jButton2.addActionListener ( save );
        
        
        
        
/*
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
            
        }catch( SQLException sql1 ){
            System.out.println ( ""+sql1 );
            StaticValues.writer.writeExcel ( Alarms.class.getSimpleName () , Alarms.class.getSimpleName () , sql1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , sql1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }finally{   try{  
            rs.close() ;
        }catch(SQLException sql2){} }
       */
            
        alarmsStates = new boolean[ 4  ];
        alarmsNames = new String[4];
        alarmsFreq = new int[ 4 ];

        alarmsNames[0] =  "PPM for the day" ;
        alarmsStates[0] = true;
        alarmsFreq[0] = 10000;

        
        alarmsNames[1] =  "Total production for the day" ;
        alarmsStates[1] = true;
        alarmsFreq[1] = 10000;
        
        alarmsNames[2] =   "Raw Materials stock close to ROL" ;
        alarmsStates[2] = true;
        alarmsFreq[2] = 10000;
        
        alarmsNames[3] =  "Rejections for the day" ;
        alarmsStates[3] = true;
        alarmsFreq[3] = 10000;
        


                       
                            
                        
         

        if(   alarmsStates[0]  || alarmsStates[1]  || alarmsStates[2]  || alarmsStates[3]   ){
        
            jCheckBox1.setEnabled ( true );
            jCheckBox1.setSelected ( true );
            jCheckBox1.setText ( "On" );
            
            jCheckBox2.setEnabled ( true );
            jCheckBox2.setSelected (alarmsStates[0] );
            if( alarmsStates[0] ){
                jCheckBox2.setText ( "On" );
            }

            jCheckBox3.setEnabled ( true );
            jCheckBox3.setSelected ( alarmsStates[1] );
            if( alarmsStates[1] ){
                jCheckBox3.setText ( "On" );
            }

            jCheckBox4.setEnabled ( true );
            jCheckBox4.setSelected ( alarmsStates[2] );
            if( alarmsStates[2] ){
                jCheckBox4.setText ( "On" );
            }

            jCheckBox5.setEnabled ( true );
            jCheckBox5.setSelected ( alarmsStates[3] );
            if( alarmsStates[3] ){
                jCheckBox5.setText ( "On" );
            }
        }
    }
    
    ActionListener mainSwitch = new ActionListener(){
        @Override
        public void actionPerformed ( ActionEvent e ) {
            
            JCheckBox abc = (JCheckBox) e.getSource ();
            
            if( abc.isSelected () ){
                jCheckBox2.setEnabled ( true );
                jCheckBox3.setEnabled ( true );
                jCheckBox4.setEnabled ( true );
                jCheckBox5.setEnabled ( true );
                
                jCheckBox2.setSelected (true );
                jCheckBox3.setSelected  ( true );
                jCheckBox4.setSelected  ( true );
                jCheckBox5.setSelected  ( true );
                
                jCheckBox1.setText ( "On" );
                
                jCheckBox2.setText ( "On" );
                jCheckBox3.setText ( "On" );
                jCheckBox4.setText ( "On" );
                jCheckBox5.setText ( "On" );
                
            }else{
                
                jCheckBox2.setEnabled ( false );
                jCheckBox3.setEnabled ( false );
                jCheckBox4.setEnabled ( false );
                jCheckBox5.setEnabled ( false );
                
                jCheckBox2.setSelected   (false );
                jCheckBox3.setSelected  ( false );
                jCheckBox4.setSelected  ( false );
                jCheckBox5.setSelected  ( false );
                
                jCheckBox1.setText ( "Off" );
                
                jCheckBox2.setText ( "Off" );
                jCheckBox3.setText ( "Off" );
                jCheckBox4.setText ( "Off" );
                jCheckBox5.setText ( "Off" );
            }
        }
    };
    
    ActionListener subSwitch = new ActionListener(){
        @Override
        public void actionPerformed ( ActionEvent e ) {
            
            JCheckBox abc = (JCheckBox) e.getSource ();
            String label = abc.getActionCommand() ;
            
            if( abc.isSelected () ){
                abc.setText ( "On" );
            }else{
                abc.setText ( "Off" );
            }                
        }
    };
    
    ActionListener save = new ActionListener(){
        @Override
        public void actionPerformed ( ActionEvent e ) {
         
                boolean currentState = jCheckBox2.isSelected ();
                boolean showAlarm = false ;
                try{

                alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = ? where ALARM_NAME = ? ";

                if(  jCheckBox2.isSelected () ){
                        showAlarm = true ;
                        alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = 'true' where ALARM_NAME = 'PPM for the day' ";
                }else{

                        alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = 'false' where ALARM_NAME = 'PPM for the day' ";
                }

                    DB_Operations.executeSingleNR ( alarmsDefaultSettingQuery );


                if(  jCheckBox3.isSelected () ){
                        showAlarm = true ;
                        alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = 'true' where ALARM_NAME = 'Total production for the day' ";
                }else{

                        alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = 'false' where ALARM_NAME = 'Total production for the day' ";
                }

                    DB_Operations.executeSingleNR ( alarmsDefaultSettingQuery );


                if(  jCheckBox4.isSelected () ){
                        showAlarm = true ;    
                        alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = 'true' where ALARM_NAME = 'Raw Materials stock close to ROL' ";
                }else{

                        alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = 'false' where ALARM_NAME = 'Raw Materials stock close to ROL' ";
                }


                    DB_Operations.executeSingleNR ( alarmsDefaultSettingQuery );


                if(  jCheckBox5.isSelected () ){
                        showAlarm = true ;                    
                        alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = 'true' where ALARM_NAME = 'Rejections for the day' ";
                }else{

                        alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = 'false' where ALARM_NAME = 'Rejections for the day' ";
                }

                    DB_Operations.executeSingleNR ( alarmsDefaultSettingQuery );
                    
                    
                    alarmData = new  ArrayList<AlarmData>();
                    getAlarmState ();
                    
                    
                    if(showAlarm){
                        AlarmController3.startAlarm ( "Preparing notifications", showAlarm, alarmData );
                    }else{
                        AlarmController3.hideAlarm() ;
                    }
                

                
            }catch( Exception e1 ){
                System.out.println ( " Error in Alarm Setting : "+e1.getMessage () );
                StaticValues.writer.writeExcel ( Alarms.class.getSimpleName () , Alarms.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

            //HomeScreen.alarm.getCurrentAlarmState ();
        }
    };
    
    
/*      public ArrayList<AlarmData> getAlarmState(){
        
         alarmData = new  ArrayList<AlarmData>();
          
        ResultSet rs = DB_Operations.executeSingle ( "select * from ALARMS_SETTINGS" );
        boolean startAlarm = false ;
        try{
            int count = rs.getMetaData ().getColumnCount ();
            alarmsStates = new boolean[ count  ];
            alarmsNames = new String[count];
            alarmsFreq = new int[ count ];
            AlarmData ad = null ;
            int i = 0;
            if( rs.isBeforeFirst () ){
                while(rs.next()){
                    alarmsNames[i] =   rs.getString(2) ;
                    alarmsStates[i] = Boolean.parseBoolean( rs.getString(3) );
                    alarmsFreq[i] = Integer.parseInt( rs.getString(4) );
                    
                    ad = new AlarmData( alarmsNames[i], alarmsFreq[i],  alarmsStates[i] ) ;
                    alarmData.add(ad);
                    
                i++ ;
                }
            }
            
        }catch( SQLException sql1 ){
            System.out.println ( ""+sql1 );
            StaticValues.writer.writeExcel ( Alarms.class.getSimpleName () , Alarms.class.getSimpleName () , sql1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , sql1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }finally{   try{  
            rs.close() ;
        }catch(SQLException sql2){} }

        return alarmData ;
    }
   */

    public ArrayList<AlarmData> getAlarmState(){
        
        alarmData = new  ArrayList<AlarmData>();
         
        
        boolean startAlarm = false ;
        
            alarmsStates = new boolean[ 4  ];
            alarmsNames = new String[4];
            alarmsFreq = new int[ 4 ];
            AlarmData ad = null ;
            
            alarmsNames[0] =   "PPM for the day" ;
                    alarmsStates[0] = true;
                    alarmsFreq[0] = 10000;
                    
                    ad = new AlarmData( alarmsNames[0], alarmsFreq[0],  alarmsStates[0] ) ;
                    alarmData.add(ad);
                    
                    alarmsNames[1] =   "Total production for the day" ;
                    alarmsStates[1] = true;
                    alarmsFreq[1] = 10000;                    

                    ad = new AlarmData( alarmsNames[1], alarmsFreq[1],  alarmsStates[1] ) ;
                    alarmData.add(ad);
                    
                    alarmsNames[2] =   "Raw Materials stock close to ROL";
                    alarmsStates[2] = true;
                    alarmsFreq[2] = 10000;
                    
                    ad = new AlarmData( alarmsNames[2], alarmsFreq[2],  alarmsStates[2] ) ;
                    alarmData.add(ad);
                    
                    alarmsNames[3] =  "Rejections for the day" ;
                    alarmsStates[3] = true;
                    alarmsFreq[3] = 10000;
                    
                    ad = new AlarmData( alarmsNames[3], alarmsFreq[3],  alarmsStates[3] ) ;
                    alarmData.add(ad);
                    
        
        return alarmData ;
    }


    ActionListener save_old = new ActionListener(){
        @Override
        public void actionPerformed ( ActionEvent e ) {
         
           // HomeScreen.alarm.hideAlarms ();
                            
            //Connection con = DB_Operations.getPreparedStatement () ;
            //PreparedStatement st = null ;

            try{

                alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = ? where ALARM_NAME = ? ";

//                 st = con.prepareStatement (alarmsDefaultSettingQuery  );
                if(  jCheckBox2.isSelected () ){
//                    st.setString( 1, "true" );
//                    st.setString( 2, "PPM for the day" );   
                        alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = 'true' where ALARM_NAME = 'PPM for the day' ";
                }else{
//                    st.setString( 1, "false" );
//                    st.setString( 2, "PPM for the day" ); 
                        alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = 'false' where ALARM_NAME = 'PPM for the day' ";
                }
//                st.executeUpdate ();
//                st.close() ;
                    DB_Operations.executeSingleNR ( alarmsDefaultSettingQuery );

//                st = con.prepareStatement (alarmsDefaultSettingQuery  );
                if(  jCheckBox3.isSelected () ){
//                    st.setString( 1, "true" );
//                    st.setString( 2, "Total production for the day" );     
                        alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = 'true' where ALARM_NAME = 'Total production for the day' ";
                }else{
//                    st.setString( 1, "false" );
//                    st.setString( 2, "Total production for the day" ); 
                        alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = 'false' where ALARM_NAME = 'Total production for the day' ";
                }
//                st.executeUpdate ();
//                st.close() ;
                    DB_Operations.executeSingleNR ( alarmsDefaultSettingQuery );


//              st = con.prepareStatement (alarmsDefaultSettingQuery  );
                if(  jCheckBox4.isSelected () ){
//                    st.setString( 1, "true" );
//                    st.setString( 2, "Raw Materials stock close to ROL" );       
                        alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = 'true' where ALARM_NAME = 'Raw Materials stock close to ROL' ";
                }else{
//                    st.setString( 1, "false" );
///                    st.setString( 2, "Raw Materials stock close to ROL" ); 
                        alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = 'false' where ALARM_NAME = 'Raw Materials stock close to ROL' ";
                }
//                st.executeUpdate ();
//                st.close() ;
                    DB_Operations.executeSingleNR ( alarmsDefaultSettingQuery );

//                st = con.prepareStatement (alarmsDefaultSettingQuery  );
                if(  jCheckBox5.isSelected () ){
//                    st.setString( 1, "true" );
//                    st.setString( 2, "Rejections for the day" );                      
                        alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = 'true' where ALARM_NAME = 'Rejections for the day' ";
                }else{
//                    st.setString( 1, "false" );
//                    st.setString( 2, "Rejections for the day" ); 
                        alarmsDefaultSettingQuery = "update ALARMS_SETTINGS set ALARM_STATE = 'false' where ALARM_NAME = 'Rejections for the day' ";
                }
//                st.executeUpdate ();
//                st.close() ;
                    DB_Operations.executeSingleNR ( alarmsDefaultSettingQuery );
//                con.close() ;

            }catch( Exception e1 ){
                System.out.println ( ""+e1.getMessage () );
                StaticValues.writer.writeExcel ( Alarms.class.getSimpleName () , Alarms.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

       //     HomeScreen.alarm.getCurrentAlarmState ();
        }
    };
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(440, 466));
        setLayout(null);

        jLabel1.setText("Rejection Level");
        add(jLabel1);
        jLabel1.setBounds(110, 140, 100, 30);

        jLabel3.setText("Targets Achieved");
        add(jLabel3);
        jLabel3.setBounds(110, 180, 100, 30);

        jLabel4.setText("Machine Monitoring");
        add(jLabel4);
        jLabel4.setBounds(110, 210, 130, 30);

        jLabel5.setText("Process Monitoring");
        add(jLabel5);
        jLabel5.setBounds(110, 250, 120, 30);

        jLabel6.setText("Alarms State");
        add(jLabel6);
        jLabel6.setBounds(110, 90, 100, 30);

        jCheckBox1.setText("Off");
        add(jCheckBox1);
        jCheckBox1.setBounds(290, 90, 45, 30);

        jButton2.setText("Update");
        add(jButton2);
        jButton2.setBounds(110, 310, 90, 32);

        jButton3.setText("Close");
        add(jButton3);
        jButton3.setBounds(240, 310, 100, 32);

        jCheckBox2.setText("Off");
        jCheckBox2.setEnabled(false);
        add(jCheckBox2);
        jCheckBox2.setBounds(290, 140, 45, 30);

        jCheckBox3.setText("Off");
        jCheckBox3.setEnabled(false);
        add(jCheckBox3);
        jCheckBox3.setBounds(290, 180, 45, 24);

        jCheckBox4.setText("Off");
        jCheckBox4.setEnabled(false);
        add(jCheckBox4);
        jCheckBox4.setBounds(290, 210, 45, 30);

        jCheckBox5.setText("Off");
        jCheckBox5.setEnabled(false);
        add(jCheckBox5);
        jCheckBox5.setBounds(290, 250, 45, 30);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    // End of variables declaration//GEN-END:variables
}


