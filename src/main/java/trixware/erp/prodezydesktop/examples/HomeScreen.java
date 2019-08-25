
//<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;


import trixware.erp.prodezydesktop.masters.UtilitiyMasters;
import trixware.erp.prodezydesktop.Model.AlarmData;
import trixware.erp.prodezydesktop.Model.AlarmsEntity;
import trixware.erp.prodezydesktop.masters.Employee_HR_Master;
import trixware.erp.prodezydesktop.masters.BatchProcessing;
import trixware.erp.prodezydesktop.masters.PlantMaster2;
import trixware.erp.prodezydesktop.masters.ProductCustomerMap;
import trixware.erp.prodezydesktop.masters.CustomerMaster;
import trixware.erp.prodezydesktop.masters.BillOfMaterialUI2;
import trixware.erp.prodezydesktop.masters.Raw_Material;
import trixware.erp.prodezydesktop.masters.SupplierMaster;
import trixware.erp.prodezydesktop.masters.FinishedGoodMaster;
import trixware.erp.prodezydesktop.masters.Processforfinishedgoods;
import trixware.erp.prodezydesktop.masters.EmployeeProcessMap;
import trixware.erp.prodezydesktop.masters.MachineMaster;
import trixware.erp.prodezydesktop.masters.Process_Master_Form;
import trixware.erp.prodezydesktop.Model.DeptRolePerm;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.Alarms;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.LoginDialog;
import trixware.erp.prodezydesktop.Utilities.LoginForm;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Utilities.assets.BGJPanelTop;
import java.sql.Statement;
import java.util.ArrayList;
import trixware.erp.prodezydesktop.Utilities.assets.RoundPanel;
import trixware.erp.prodezydesktop.Utilities.assets.RoundPanel2;
import java.util.Date;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import com.google.firebase.FirebaseOptions;
import trixware.erp.prodezydesktop.controllers.AlarmController3;
import trixware.erp.prodezydesktop.examples.reports.DynamicDashboard_One;
import trixware.erp.prodezydesktop.examples.reports.FGQualityReport_1;
import trixware.erp.prodezydesktop.examples.reports.PanelForDynamicGrpahs;
import trixware.erp.prodezydesktop.examples.reports.ProcessQualityReport;
import trixware.erp.prodezydesktop.examples.reports.ProcessQualityReport_1;
import trixware.erp.prodezydesktop.examples.reports.RMQualityReport;
import trixware.erp.prodezydesktop.examples.reports.RMQualityReport_1;
import trixware.erp.prodezydesktop.examples.reports.RMandPartCodeform;
import trixware.erp.prodezydesktop.examples.reports.ReportsController;
import trixware.erp.prodezydesktop.examples.reports.UserRolePermissionMaster;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.UnsupportedLookAndFeelException;
import trixware.erp.prodezydesktop. masters.Create_User;
import trixware.erp.prodezydesktop. masters.Die_master1;
import trixware.erp.prodezydesktop. masters.Process_Master_Quatation;
import trixware.erp.prodezydesktop. masters.Raw_Material_Quotation;
import trixware.erp.prodezydesktop. newUI_Masters.QualityMaster;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop. planning.PanningUI_One;
import trixware.erp.prodezydesktop. quotation.EnquiriesForm;
import trixware.erp.prodezydesktop. web_services.WebAPITester;


/**
 *
 * @author Rajesh
 */
public class HomeScreen extends javax.swing.JFrame {

    //   static JPanel parentPanel;
    public static HomeScreen home;
    public LoginDialog loginDlg = new LoginDialog ( HomeScreen.this );
   
     public static DefaultCategoryDataset dataSet = null;
    
    JFrame frame = null;
    public static String settingsDBName;

    public static int width = 0, height = 0;

    public static DeptRolePerm user = null;

    private static boolean loginSessionActive = false;
    public GraphicsEnvironment ge = null;

    public static BGJPanelTop topPanel = null;
    static Calendar c2 = Calendar.getInstance ();

    // public static FirebaseOptions options = null;
    public static FirebaseOptions options = null;

    public static AlarmController3 alarmController = null ;
    public static Alarms alarm = null;
    //  public static  ThreadCommunication alarm = null ;
    
    public static LoginForm loginF = null ;
    private JMenuItem jMenuItem23;
   
    

    /**
     * Creates new form HomeScreen
     */
    public HomeScreen () {

 //       JOptionPane.showMessageDialog(  null, "ssdfsdfsdfsdfsd") ;
        
        initComponents ();
        setExtendedState ( JFrame.MAXIMIZED_BOTH );
       
        jMenuItem14.setVisible ( false );
        jMenuItem18.setVisible ( false );
        jMenuItem35.setVisible ( false );
        hourscreen.setVisible(false);
        jMenuItem36.setVisible ( false );
        jMenuItem37.setVisible ( false );
        jMenuItem47.setVisible ( false );
        jMenuItem30.setVisible ( true );
        jMenuItem10.setVisible ( false );
        jMenuItem13.setVisible ( false );
        //jMenuItem16.setVisible( false );
        //jMenuItem17.setVisible( false );
        jMenuItem8.setVisible ( false );
        //jMenuItem11.setVisible ( false );
        jMenuItem19.setVisible ( false );
        jMenuItem20.setVisible ( false );
        
        jMenuItem9.setVisible(false);
        jMenu13.setVisible ( false );
        jMenu1.setVisible ( false );

        jMenuBar1.setVisible(false);
        
        try{
            setIconImage ( Toolkit.getDefaultToolkit ().getImage ( getClass ().getResource ( "/trixware/erp/prodezydesktop/Utilities/assets/prodezy32x32img.png" ) ) );
        }catch( NullPointerException e ){
            
        } 
        //bp1.jButton1.addActionListener ( listemBPClose );

//        //System.out.println ( WebServices_Controller.getCategories ());
//        String response = WebServices_Controller.getToken ();
//        JSONObject responseObject = (JSONObject) new JSONTokener(response).nextValue() ;
//        System.out.println ( ""+responseObject.getJSONObject ( "data").getString("token") );
//        StaticValues.setHeader( responseObject.getJSONObject ( "data").getString("token") );

        

    }

   
    
    public static void initializeApplication () {
        
   //    Proman_User.setIsActivelyLoggedIn ( true );
    //   StaticValues.setHeader ( "eyJpdiI6IkxaMGZjS2ZFQ2tMZDJEc2N2RklQR0E9PSIsInZhbHVlIjoieDkzTXFHbGVPQVlVcHJWOHZoSXV3NjBrdlNtc1pBeExkbVEyajBhSHhNMVAwd0RVRkFjYkQxQWtJTVZNWjQxTVE5Z3hWVUljbkZBK0p2Y2xGQkc5VTZkXC9KXC9jYUpBTUhpZnk1QjNvaEI5UHpNQUhPdUVwT25acTU3UGV3bCtiMzdNVlwvc0xpZ0hUanRtOE8xOVpMdEtJcU5TdWJHXC9OWUlNZ2Z1a1VIaWlQK0RJVWJjT1dBYTZPY2Z6b1NRVlZLU3RUdElZQk5zNFE2SVpBbFdDRmlkdkdOTk40Y0psTURVZnZQZjlmQlI5Z2VhejlcLzJUSEgyT1FOcXBENW14MkNOIiwibWFjIjoiYTk0N2ZhYjE1YWJlZWZjNmZmNWE2MTJlYTJlYzkzOTQwMTc5OGYxZWVlOTUwNzUyYWU3NzAyODgzYjc0YzY4YSJ9");
       
//          try{ 
//            if( ! InetAddress.getLocalHost ().isReachable ( 500) ){
//               JOptionPane.showMessageDialog( null, "No Internet " );
//            }else{
//                JOptionPane.showMessageDialog( null, " Internet Available " );
//            }
//        }catch( IOException e ){
//             JOptionPane.showMessageDialog( null, "  "+e.getMessage () );
//        }     
//          create an error log file that will have enter all exception events in this file
//          File f = new File();

            jLabel5.setVisible ( false );

            PreparedStatement st = null;

            File f = new File ( "SSDsdIadc_xlp.db" );
            if (  ! f.exists () ) {

          //       try {
                    jMenuBar1.setVisible ( false );

                    appSettings process = new appSettings ();
                    Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
                    int screenW = ( int ) d.getWidth ();
                    int screenH = ( int ) d.getHeight ();
                    int paneW = 1300;

                    process.setBounds ( ( int ) ( screenW - paneW ) / 2 , 0 , paneW , 650 );

                    home.jPanel1.removeAll ();
                    home.jPanel1.setLayout ( null );
                    home.jPanel1.add ( process );
                    home.revalidate ();
                    home.repaint ();

                    home.setExtendedState ( JFrame.MAXIMIZED_BOTH );

                //   JOptionPane.showMessageDialog ( null, "Database  exsits. Configuring settings");
                
//                } catch ( SQLException e ) {
//                    JOptionPane.showMessageDialog ( null , "" + e.getMessage () );
//                    StaticValues.writer.writeExcel ( HomeScreen.class.getSimpleName () , HomeScreen.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 2 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//                }
            } else {

                //Copy the settings database file in secured folder for unanemous access
                File file = new File ( System.getProperty ( "user.home" ) + "\\AppData\\Local\\" + "Prodezy" );
                if (  ! file.exists () ) {
                    file.mkdir ();
                }
                file = new File ( System.getProperty ( "user.home" ) + "\\AppData\\Local\\Prodezy\\" + "databases" );
                if (  ! file.exists () ) {
                    file.mkdir ();
                }
                file = new File ( System.getProperty ( "user.home" ) + "\\AppData\\Local\\Prodezy\\" + "app_log.txt" );

                if (  ! file.exists () ) {
                    try {
                        file.createNewFile ();
                    } catch ( IOException ex ) {
                        StaticValues.writer.writeExcel ( HomeScreen.class.getSimpleName () , HomeScreen.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 2 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                        JOptionPane.showMessageDialog ( null , "Error !  Please contact your sevice provider" );
                    }
                }

                //Create a new excel file for recording logs of all errors in structured way
                StaticValues.createErrorLogExcel ();

                Path sourceDirectory = Paths.get ( "SSDsdIadc_xlp.db" );
                Path targetDirectory = Paths.get ( System.getProperty ( "user.home" ) + "\\AppData\\Local\\Prodezy\\" + "SSDsdIadc_xlp.db" );
                file = targetDirectory.toFile ();
                if (  ! file.exists () ) {
                    try {
                        Files.copy ( sourceDirectory , targetDirectory );
                    } catch ( IOException ex ) {
                        JOptionPane.showMessageDialog ( null , "Setting DB Copy Error " + ex.getMessage () );
                        StaticValues.writer.writeExcel ( HomeScreen.class.getSimpleName () , HomeScreen.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 2 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );

                    }
                }

                sourceDirectory = Paths.get ( StaticValues.dbName );
                targetDirectory = Paths.get ( System.getProperty ( "user.home" ) + "\\AppData\\Local\\Prodezy\\" + StaticValues.dbName );
                file = targetDirectory.toFile ();
                if (  ! file.exists () ) {
                    try {
                        Files.copy ( sourceDirectory , targetDirectory );

                    } catch ( IOException | NullPointerException ex ) {
                        JOptionPane.showMessageDialog ( null , "Data DB Copy Error " + ex.getMessage () );
                        StaticValues.writer.writeExcel ( HomeScreen.class.getSimpleName () , HomeScreen.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 2 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }
                } 
                
                try {

                        Connection _con = DriverManager.getConnection ( "jdbc:sqlite:" + System.getProperty ( "user.home" ) + "\\AppData\\Local\\Prodezy\\" + "SSDsdIadc_xlp.db" );

                        Statement st1 = _con.createStatement ();
                        // Check if the installation date is older than 15 days
                        ResultSet rs = st1.executeQuery ( "select * from appSSJ_22154sdl_dsk_021" );
                        DateFormat sdf = new SimpleDateFormat ( "MMM d, yyyy" );
                        SimpleDateFormat sdf2 = new SimpleDateFormat ( "MMM d, yyyy" );
                    
                        Date installationDate = sdf.parse ( rs.getString ( "tria" ) );
                        Date todaysDate = sdf.parse ( sdf2.format ( Calendar.getInstance ().getTime () ) );

                        String licenseeName = rs.getString ( "unus" );
                        String licenseKey = rs.getString ( "duo" );
                        String customerRepName = rs.getString ( "quinque" );
                        String vendorRepName = rs.getString ( "sx" );
                        String databaseName =  rs.getString ( "quattuor" ); 
                        String apiURL = rs.getString ( "svn" ) ;
                        StaticValues.setApiURL ( apiURL );
                        
                        System.out.println ( "Web API URL fetched from database and set as "+StaticValues.getApiURL () );

                        long _time = todaysDate.getTime () - installationDate.getTime ();
                        int _days = ( int ) ( ( ( _time / 1000 ) / 60 ) / 60 / 24 );
                        st1.close ();

//                        if ( _days > 15 ) {
//                            int choice = JOptionPane.showConfirmDialog ( null , "Dear customer, your trial period is expired. Please contact your service provider" , "Trial Period Is Expired" , JOptionPane.OK_OPTION );
//                            if ( choice == JOptionPane.OK_OPTION ) {
//                                jMenuBar1.setVisible ( false );
//                            } else if ( choice == JOptionPane.NO_OPTION ) {
//                                jMenuBar1.setVisible ( false );
//                            } else if ( choice == JOptionPane.CANCEL_OPTION ) {
//                                jMenuBar1.setVisible ( false );
//                            }
//                            appSettings process = new appSettings ( licenseeName , licenseKey , customerRepName , vendorRepName , databaseName);
//                            process.setBounds ( 5 , 5 , width , 605 );
//                            home.jPanel1.removeAll ();
//                            home.jPanel1.setLayout ( null );
//                            home.jPanel1.add ( process );
//                            home.revalidate ();
//                            home.repaint ();
//
//                        } else {
                             _con = DriverManager.getConnection ( "jdbc:sqlite:SSDsdIadc_xlp.db" );

                            String selectAllSettings = "select * from appSSJ_22154sdl_dsk_021";
                            st = _con.prepareStatement ( selectAllSettings );
                            rs = st.executeQuery ();
                            settingsDBName = rs.getString ( "quattuor" );
                            StaticValues.dbName = StaticValues.dbName + settingsDBName;
                            apiURL = rs.getString ( "svn" ) ;
                            StaticValues.setApiURL ( apiURL );
                            //   System.out.println ( "" + StaticValues.dbName );
                            //new DB_Synchronization ( StaticValues.dbName );

                            rs.close ();

                            
                            if (  ! isSessionActive () ) 
                            {
                                home.jLabel5.setVisible ( false );
                                home.jLabel3.setText ( "Welcome Guest !!" );
                                
                                loginF = new LoginForm();
                                
                                loginF.setBounds(  100, 200, 300, 300      );
                                jLabel2.setBounds(  560, 75, 800, 360    );
                                jLabel8.setBounds(  80, 70, 400, 55    );
                                jLabel7.setBounds(  0, 450, 1366, 150    );
                                
                                // jPanel1.removeAll();
                                jPanel1.add( loginF ) ;
                                //checkVersionFromJSON();
                            }
                            
                            //checkVersionFromJSON();
           //           Temporarily hinding the provision for dynamically showing and hiding alarm setting 
           //           Now on the alaems label shall always show 
           //           Change specfically made for DG Corporation
                            /*
                            alarm = new Alarms ();
                            ArrayList<AlarmData> alarmData = new ArrayList<AlarmData> ();
                            alarmData = alarm.getAlarmState ();
                            boolean showAlarm = false;
                            for ( int i = 0 ; i < alarm.alarmsStates.length ; i ++ ) {
                                if ( alarm.alarmsStates[ i ] ) {
                                    showAlarm = true;
                                }
                            }
                            AlarmController3 alarmController = new AlarmController3 ( showAlarm );
                            if ( showAlarm ) {
                                alarmController.startAlarm ( "Preparing notifications" , showAlarm , alarmData );
                            } else {
                                alarmController.hideAlarm ();
                            }
                            */
                            
     
                    //    }
                    }catch(SQLException | ParseException e){
                        
                        JOptionPane.showMessageDialog ( null , e.getMessage (), "Error", JOptionPane.ERROR_MESSAGE);
                        
                            System.out.println ( ""+e.getMessage() );
                            home.jLabel5.setVisible ( false );
                            home.jLabel3.setText ( "Welcome Guest !!" );

                            loginF = new LoginForm();
                            loginF.setBounds(  100, 200, 300, 300      );
                            jLabel2.setBounds(  560, 75, 800, 360    );
                            jLabel8.setBounds(  80, 70, 400, 55    );
                            jLabel7.setBounds(  0, 450, 1366, 150    );

                            // jPanel1.removeAll();
                            jPanel1.add( loginF ) ;
                            
                            
                    }
                   
        }
    }

    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jMenu3 = new javax.swing.JMenu();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new RoundPanel();
        jLabel2 = new javax.swing.JLabel();
         hourscreen = new javax.swing.JMenuItem();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new RoundPanel2 ();
        jLabel6 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem46 = new javax.swing.JMenuItem();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenuItem31 = new javax.swing.JMenuItem();
       
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();
        RMFGcode = new javax.swing.JMenuItem();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenuItem52 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenu14 = new javax.swing.JMenu();
        jMenuItemQuality=new javax.swing.JMenu();
        jMenuQualityReport=new javax.swing.JMenu();
        jMenuItem40 = new javax.swing.JMenuItem();
        jMenuItem41 = new javax.swing.JMenuItem();
        jMenuItem42 = new javax.swing.JMenuItem();
        jMenuItem43 = new javax.swing.JMenuItem();
        jMenuItem39 = new javax.swing.JMenuItem();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem36 = new javax.swing.JMenuItem();
        jMenuItem37 = new javax.swing.JMenuItem();
        jMenuItem38 = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem51 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        jMenuItem44 = new javax.swing.JMenuItem();
        jMenuItem45 = new javax.swing.JMenuItem();
        jMenu15 =     new javax.swing.JMenu();
        jMenuItem53 = new javax.swing.JMenuItem();
        jMenuItem54 = new javax.swing.JMenuItem();
        COPQ        = new javax.swing.JMenuItem();
        diefrm     =new javax.swing.JMenuItem();
        othercopq = new javax.swing.JMenuItem();
        jMenuItem47 = new javax.swing.JMenuItem();
        jMenuItem48 = new javax.swing.JMenuItem();
        jMenuItem49 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        //jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        maintain_history = new javax.swing.JMenuItem();
        jMenu12 = new javax.swing.JMenu();
        jMenuItem50 = new javax.swing.JMenuItem();
        jMenuItem55 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItemRM = new javax.swing.JMenuItem();
        jMenuItemFG = new javax.swing.JMenuItem();
        jMenuItemProc = new javax.swing.JMenuItem();
        jMenuItemRMReport = new javax.swing.JMenuItem();
        jMenuItemFGReport = new javax.swing.JMenuItem();
        jMenuItemProcReport = new javax.swing.JMenuItem();
        jMenu13 = new javax.swing.JMenu();
        jMenuItem32 = new javax.swing.JMenuItem();
        processQuotation = new javax.swing.JMenuItem();
        quotation = new javax.swing.JMenuItem();
        rm_quotation = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem56 = new javax.swing.JMenuItem();
        

        jMenu3.setText("jMenu3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1366, 768));
        getContentPane().setLayout(null);

        jLabel3.setBackground(new java.awt.Color(51, 153, 255));
        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setPreferredSize(new java.awt.Dimension(300, 30));
        getContentPane().add(jLabel3);
        jLabel3.setBounds(660, 0, 170, 30);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setPreferredSize(new java.awt.Dimension(300, 30));
        getContentPane().add(jLabel4);
        jLabel4.setBounds(830, 0, 230, 30);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(550, 30, 800, 25);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        try{
            jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/trixware/erp/prodezydesktop/Utilities/assets/logout.png"))); // NOI18N
        }catch( NullPointerException e ){
            
        }
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel5);
        jLabel5.setBounds(1080, 0, 32, 26);

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1310, 600));
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentResized(evt);
            }
        });
        jPanel1.setLayout(null);

        
        try{
            jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/trixware/erp/prodezydesktop/Utilities/assets/shopfloorImage.png"))); // NOI18N
        }catch( NullPointerException e ){
            
        }
        
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setMaximumSize(new java.awt.Dimension(1325, 610));
        jLabel2.setMinimumSize(new java.awt.Dimension(1325, 610));
        jLabel2.setPreferredSize(new java.awt.Dimension(1325, 610));
        jPanel1.add(jLabel2);
        jLabel2.setBounds(560, 75, 800, 360);

        try{
            jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/trixware/erp/prodezydesktop//Utilities/assets/titles_.png"))); // NOI18N
        }catch( NullPointerException e ){
            
        }
        jPanel1.add(jLabel7);
        jLabel7.setBounds(0, 450, 1366, 150);

        try{
            jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/trixware/erp/prodezydesktop/Utilities/assets/AppTitle.png"))); // NOI18N
        }catch( NullPointerException e ){
            
        }
        jPanel1.add(jLabel8);
        jLabel8.setBounds(80, 70, 400, 50);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1360, 600);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(null);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel2.add(jLabel6);
        jLabel6.setBounds(5, 5, 400, 30);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(40, 10, 500, 40);

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N

        jMenu8.setText("Masters");
        jMenu8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu8.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenu8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu8ActionPerformed(evt);
            }
        });

        processQuotation.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        processQuotation.setText("Process Quotation");
        processQuotation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processQuotationActionPerformed(evt);
            }
        });
        //jMenu8.add(processQuotation);
        
          rm_quotation.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        rm_quotation.setText("Raw Material Quotation");
        rm_quotation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rm_quotationActionPerformed(evt);
            }
        });
        //jMenu8.add(rm_quotation);
        
        quotation.setText("Quotation");
        quotation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quotationActionPerformed(evt);
            }
        });
        //jMenu9.add(quotation);
        
        jMenuItem46.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem46.setText("Plant Master");
        jMenuItem46.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem46ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem46);
        
        hourscreen.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        hourscreen.setText("Hour-Wise");
        hourscreen.setMargin(new java.awt.Insets(3, 3, 3, 2));
        hourscreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hourscreenActionPerformed(evt);
            }
        });
        jMenu14.add(hourscreen);

        jMenuItem33.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem33.setText("Utilities");
        jMenuItem33.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem33ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem33);

       

        jMenuItem31.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem31.setText("Employee");
        jMenuItem31.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem31);

        
        jMenuItem55.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem55.setText("Create User");
        jMenuItem55.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem55ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem55);        
        
        jMenuItem13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem13.setText("User Roles Permissions");
        jMenuItem13.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem13);

        jMenuItem28.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem28.setText("Raw Material");
        jMenuItem28.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem28);

        jMenuItem29.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem29.setText("Finished Goods");
        jMenuItem29.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem29ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem29);
        
        jMenuItem27.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem27.setText("Machine");
        jMenuItem27.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem27);

        jMenuItem25.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem25.setText("Customer");
        jMenuItem25.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem25);
        
        RMFGcode.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); 
        RMFGcode.setText("RM and FG code");
        RMFGcode.setMargin(new java.awt.Insets(3, 3, 3, 2));
        RMFGcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RMFGcodeActionPerformed(evt);
            }
        });
        jMenu8.add(RMFGcode);

        jMenuItem26.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem26.setText("Supplier");
        jMenuItem26.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem26);
        
        diefrm.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        diefrm.setText("Die Master");
        diefrm.setMargin(new java.awt.Insets(3, 3, 3, 2));
        diefrm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diefrmActionPerformed(evt);
            }
        });
        jMenu8.add(diefrm);

        jMenuItem30.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem30.setText("Testing");
        jMenuItem30.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        //jMenu8.add(jMenuItem30);

        jMenuItem34.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem34.setText("Processes");
        jMenuItem34.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem34ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem34);

        jMenuItem52.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem52.setText("Process Map");
        jMenuItem52.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem52ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem52);

        jMenuItem2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem2.setText("Product Customer Map");
        jMenuItem2.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem2);

        jMenuItem9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem9.setText("Process-Employee Map");
        jMenuItem9.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        
        jMenu8.add(jMenuItem9);

        jMenuItem16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem16.setText("BOM Master");
        jMenuItem16.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem16);

       
        
        jMenuItem17.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem17.setText("Batch Master");
        jMenuItem17.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem17);

        jMenuBar1.add(jMenu8);

        jMenu9.setText("Entries");
        jMenu9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu9.setMargin(new java.awt.Insets(3, 3, 3, 2));

        jMenu14.setText("Production Entry");
        jMenu14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu14.setMargin(new java.awt.Insets(3, 3, 3, 2));

        jMenuItem40.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem40.setText("Product-wise");
        jMenuItem40.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem40ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem40);

        jMenuItem41.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem41.setText("Process-wise");
        jMenuItem41.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem41ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem41);
       
        jMenuItem42.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem42.setText("Machine-wise");
        jMenuItem42.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem42ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem42);

        jMenuItem43.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem43.setText("Employee-wise");
        jMenuItem43.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem43ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem43);

        jMenuItem39.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem39.setText("Date-wise");
        jMenuItem39.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem39ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem39);

        jMenu9.add(jMenu14);
        
        
        //************Created By Harshali for Quality Module
        
        jMenuItemQuality.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItemQuality.setText("Quality Entry");
        jMenuItemQuality.setMargin(new java.awt.Insets(3, 3, 3, 2));
              
        jMenuItemRM.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItemRM.setText("RM Quality Entry");
        jMenuItemRM.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItemRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRMActionPerformed(evt);
            }
        });
        //jMenuItemQuality.add(jMenuItemRM);
        
        jMenuItemFG.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItemFG.setText("Parts Quality Entry");
        jMenuItemFG.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItemFG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFGActionPerformed(evt);
            }
        });
       // jMenuItemQuality.add(jMenuItemFG);
        
        
        jMenuItemProc.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItemProc.setText("Process Quality Entry");
        jMenuItemProc.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItemProc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemProcActionPerformed(evt);
            }
        });
        //jMenuItemQuality.add(jMenuItemProc);
        
        //jMenu9.add(jMenuItemQuality);
        
        
        
        jMenuQualityReport.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuQualityReport.setText("Quality Reports");
        jMenuQualityReport.setMargin(new java.awt.Insets(3, 3, 3, 2));
                
        jMenuItemRMReport.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItemRMReport.setText("RM Quality Report");
        jMenuItemRMReport.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItemRMReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRMReportActionPerformed(evt);
            }
          });
        //jMenuQualityReport.add(jMenuItemRMReport);
        
        jMenuItemFGReport.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItemFGReport.setText("Part Quality Report");
        jMenuItemFGReport.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItemFGReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPartReportActionPerformed(evt);
            }
          });
       // jMenuQualityReport.add(jMenuItemFGReport);
        
        jMenuItemProcReport.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItemProcReport.setText("Process Quality Report");
        jMenuItemProcReport.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItemProcReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemProcReportActionPerformed(evt);
            }
          });
        //jMenuQualityReport.add(jMenuItemProcReport);
        
        //jMenu11.add(jMenuQualityReport);
        //****************************
        

        jMenuItem35.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem35.setText("Purchase");
        jMenuItem35.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenu9.add(jMenuItem35);

        jMenuItem36.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem36.setText("GRN");
        jMenuItem36.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenu9.add(jMenuItem36);

        jMenuItem37.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem37.setText("GIN");
        jMenuItem37.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenu9.add(jMenuItem37);

        jMenuItem38.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem38.setText("Sales Order");
        jMenuItem38.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem38ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem38);

        jMenuBar1.add(jMenu9);
        
        jMenu10.setText("Inventory");
        jMenu10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu10.setMargin(new java.awt.Insets(3, 3, 3, 2));

        jMenuItem51.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem51.setText("Update RM");
        jMenuItem51.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem51ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem51);

        jMenuItem8.setText("Update FG");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem8);

        jMenuBar1.add(jMenu10);

        jMenu11.setText("User Reports");
        jMenu11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu11.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenu11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
            }
        });

        jMenuItem44.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem44.setText("Production Qty");
        jMenuItem44.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem44ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem44);

        jMenuItem45.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem45.setText("Production Value");
        jMenuItem45.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem45ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem45);

        jMenu15.setText("WIP");
        jMenu15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu15.setMargin(new java.awt.Insets(3, 3, 3, 2));

        jMenuItem53.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem53.setText("WIP 1");
        jMenuItem53.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem53ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem53);

        jMenuItem54.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem54.setText("WIP 2");
        jMenuItem54.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem54ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem54);
        
        
        COPQ.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        COPQ.setText("COPQ");
        COPQ.setMargin(new java.awt.Insets(3, 3, 3, 2));
        COPQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                COPQActionPerformed(evt);
            }
        });
        jMenu11.add(COPQ);
        
        jMenu11.add(jMenu15);

        jMenuItem47.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem47.setText("Raw Material");
        jMenuItem47.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenu11.add(jMenuItem47);

        jMenuItem48.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem48.setText("Productivity");
        jMenuItem48.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem48ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem48);

        jMenuItem49.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem49.setText("Rejection");
        jMenuItem49.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem49ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem49);

        jMenuItem6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem6.setText("PPM");
        jMenuItem6.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem6);

        jMenuItem7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem7.setText("Cost / Value");
        jMenuItem7.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem7);

        jMenuItem11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem11.setText("OEE");
        jMenuItem11.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem11);

        jMenuItem15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem15.setText("Defect Occurences");
        jMenuItem15.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem15);

        jMenuItem19.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem19.setText("Production to Sales Coverage");
        jMenuItem19.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem19);

        jMenuItem20.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem20.setText("Raw Material Efficiency");
        jMenuItem20.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem20);

        jMenuItem21.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem21.setText("PMPH");
        jMenuItem21.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem21);

        jMenuItem22.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem22.setText("Q M");
        jMenuItem22.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }  
        });
        
        //jMenu8.add(jMenuItem22);
        
        maintain_history.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); 
        maintain_history.setText("Maintenance History");
        maintain_history.setMargin(new java.awt.Insets(3, 3, 3, 2));
        maintain_history.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maintain_historyActionPerformed(evt);
            }
        });
        jMenu9.add(maintain_history);
        
        


        jMenuBar1.add(jMenu11);

        jMenu12.setText("Tools");
        jMenu12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu12.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenu12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu12ActionPerformed(evt);
            }
        });

        jMenuItem50.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem50.setText("Widgets");
        jMenuItem50.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem50ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem50);

        jMenuItem1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem1.setText("Documents");
        jMenuItem1.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem1);

        jMenu1.setText("<html>Reference<br>Material</html>");
        jMenu1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu1.setMargin(new java.awt.Insets(3, 3, 3, 2));

        jMenuItem3.setText("User Manual");
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Troubleshooting");
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText("Other");
        jMenu1.add(jMenuItem5);

        jMenu12.add(jMenu1);

        jMenuItem10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem10.setText("Alarms");
        jMenuItem10.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem10);

        jMenuItem12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem12.setText("Setup");
        jMenuItem12.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem12);

        jMenuItem14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem14.setText("Import");
        jMenuItem14.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem14);

        jMenuItem18.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem18.setText("App Setting");
        jMenuItem18.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem18);

        jMenuBar1.add(jMenu12);

        jMenu13.setText("MIS Reports");
        jMenu13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu13.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuBar1.add(jMenu13);

        jMenu2.setText("Planning");
        jMenu2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu2.setMargin(new java.awt.Insets(3, 3, 3, 2));

        jMenuItem56.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem56.setText("FirstActivity");
        jMenuItem56.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem56ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem56);

        jMenuBar1.add(jMenu2);
        
        
        
        setJMenuBar(jMenuBar1);

        
        
        getAccessibleContext().setAccessibleName("homeFrame");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    public void removeForms () {
       
        home.jPanel1.removeAll ();
        jLabel2.setBounds(  560, 75, 800, 360    );
        jLabel8.setBounds(  80, 70, 400, 55    );
        jLabel7.setBounds(  0, 450, 1366, 150    );
        home.jPanel1.add ( jLabel2 );
        home.jPanel1.add ( jLabel8 );
        home.jPanel1.add ( jLabel7 );
        
        home.jPanel1.setLayout ( null );
        home.revalidate ();
        home.repaint ();
        System.gc ();
    }
    

    public static void LoadDashboard () {
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );

//         try{
//            StaticValues.checkUserRolePermission( "Dashboard", "Dashboard");
//        }catch(Exception e){
//            StaticValues.writer.writeExcel ( "Dashboard", "Dashboard" , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }
        
       // if(  Proman_User.isCanRead () ){
            
        //    InventoryWidget ant = new InventoryWidget ();
        //    GraphsWidget ant = new GraphsWidget( );
        PanelForDynamicGrpahs ant = new PanelForDynamicGrpahs("Production Quantity") ;
        
            scroll = new JScrollPane ( ant ,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
           // ant.loadContent ();

            ant.setBounds ( ( int ) ( width - ant.getPreferredSize ().width ) / 2 , 5 , ant.getPreferredSize ().width , ant.getPreferredSize ().height );
            scroll.setBounds ( 5 , 5 , width - 25 , 605 );

            scroll.setBackground ( Color.WHITE );
            scroll.setBorder ( BorderFactory.createEmptyBorder () );

            home.jPanel1.removeAll ();
            home.jPanel1.setLayout ( null );
            //home.jPanel1.add ( customer );
            home.jPanel1.add ( scroll , BorderLayout.CENTER );

            jLabel6.setText ( "  ProdEZY Dashboard" );
            //  home.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            home.revalidate ();
            home.repaint ();
       // }else{
            
      //  }
       
    }
    
    public static void LoadDynamicDashboard () {
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );

//         try{
//            StaticValues.checkUserRolePermission( "Dashboard", "Dashboard");
//        }catch(Exception e){
//            StaticValues.writer.writeExcel ( "Dashboard", "Dashboard" , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }
        
       // if(  Proman_User.isCanRead () ){
            
            InventoryWidgetDyn ant = new InventoryWidgetDyn ();
          //  GraphsWidget ant = new GraphsWidget( );
            scroll = new JScrollPane ( ant ,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
           // ant.loadContent ();

            ant.setBounds ( ( int ) ( width - ant.getPreferredSize ().width ) / 2 , 5 , ant.getPreferredSize ().width , ant.getPreferredSize ().height );
            scroll.setBounds ( 5 , 5 , width - 25 , 600 );

            scroll.setBackground ( Color.WHITE );
            scroll.setBorder ( BorderFactory.createEmptyBorder () );

            home.jPanel1.removeAll ();
            home.jPanel1.setLayout ( null );
            //home.jPanel1.add ( customer );
            home.jPanel1.add ( scroll , BorderLayout.CENTER );

            jLabel6.setText ( "  ProdEZY Dashboard" );
            //  home.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            home.revalidate ();
            home.repaint ();
       // }else{
            
      //  }
       
    }
    
    public static void LoadDefaultDataEntry () {
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );

        DailyReportForm process = new DailyReportForm ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
       
        jLabel6.setText ( "  Production Data Entry" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
      
       
    }

    private void showCustomerScreen () {

   //     bp1 = new ButtonPanel ();
        //bp1.setBounds ( ( int ) ( width - bp1.getPreferredSize ().width ) / 2 , 554 , bp1.getPreferredSize ().width , bp1.getPreferredSize ().height );
     //   bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        //   jMenuBar1.add ( jMenu12, 4) ;        
        CustomerMaster customer = new CustomerMaster ();
        scroll = new JScrollPane ( customer ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        customer.loadContent ();

        //customer.setBounds ( ( int ) ( width - customer.getPreferredSize ().width ) / 2 , 5 , customer.getPreferredSize ().width , customer.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - customer.getPreferredSize ().width ) / 2;
        customer.setBounds ( leftMargin , 5 , customer.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Customer Master" );

        home.revalidate ();
        home.repaint ();

        //     JOptionPane.showMessageDialog(null, "Please enter valid text"  );
    }

    private void showSupplierScreen () {
//jComboBox2
  //      bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        SupplierMaster supplier = new SupplierMaster ();
        scroll = new JScrollPane ( supplier ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        supplier.loadContent ();

        //supplier.setBounds ( ( int ) ( width - supplier.getPreferredSize ().width ) / 2 , 5 , supplier.getPreferredSize ().width , supplier.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - supplier.getPreferredSize ().width ) / 2;
        supplier.setBounds ( leftMargin , 5 , supplier.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( supplier );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Supplier Master" );

        home.revalidate ();
        home.repaint ();

    }

    private void showEmployeeScreen () {

   //     bp1 = new ButtonPanel ();
   //     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        Employee_HR_Master emplHRMaster = new Employee_HR_Master ();

        scroll = new JScrollPane ( emplHRMaster ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        emplHRMaster.loadContent ();
        emplHRMaster.revalidate();
        emplHRMaster.repaint();
        //emplHRMaster.setBounds ( ( int ) ( width - emplHRMaster.getPreferredSize ().width ) / 2 , 5 , emplHRMaster.getPreferredSize ().width , emplHRMaster.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - emplHRMaster.getPreferredSize ().width ) / 2;
        emplHRMaster.setBounds ( leftMargin , 5 , emplHRMaster.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Employee Master" );

        home.revalidate ();
        home.repaint ();

    }

    private void showMachineScreen () {

    //    bp1 = new ButtonPanel ();
    //    bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        //    jMenuBar1.remove ( jMenu12);
        MachineMaster machine = new MachineMaster ();

        scroll = new JScrollPane ( machine ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //machine.setBounds ( ( int ) ( width - machine.getPreferredSize ().width ) / 2 , 5 , machine.getPreferredSize ().width , machine.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - machine.getPreferredSize ().width ) / 2;
        machine.setBounds ( leftMargin , 5 , machine.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        machine.loadContent ();

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Machine Master" );
        home.revalidate ();
        home.repaint ();

    }

    private void showRawMaterialScreen () {
   //     bp1 = new ButtonPanel ();
   //     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        Raw_Material rawM = new Raw_Material ();
        scroll = new JScrollPane ( rawM ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        rawM.loadContent ();
            
        //rawM.setBounds ( ( int ) ( width - rawM.getPreferredSize ().width ) / 2 , 5 , rawM.getPreferredSize ().width , 495 );
        //scroll.setBounds ( ( int ) ( width - rawM.getPreferredSize ().width ) / 2 , 5 , width-10 , 500 );
        //rawM.setBounds ( ( int ) ( width - rawM.getPreferredSize ().width ) / 2 , 5 , rawM.getPreferredSize ().width , 495 );
        //scroll.setBounds ( 5 , 5 , width-25 , 500 );
        System.out.println("SIZEEEEEEEEE  "+rawM.getPreferredSize());
       // rawM.setPreferredSize(new java.awt.Dimension(1000,592));//set by harshali
         int leftMargin = ( int ) ( width - rawM.getPreferredSize ().width ) / 2;
        rawM.setBounds ( leftMargin , 5 , rawM.getPreferredSize ().width , 700 );
         rawM.repaint();
         rawM.revalidate();
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );


        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Raw Material Master" );
        //       home.jPanel1.add ( jLabel6 )

        home.revalidate ();
        home.repaint ();

    }

    private void showUtilitiesScreen () {
    //    bp1 = new ButtonPanel ();
    //    bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        UtilitiyMasters utilities = new UtilitiyMasters ();
        scroll = new JScrollPane ( utilities ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //utilities.setBounds ( ( int ) ( width - utilities.getPreferredSize ().width ) / 2 , 5 , utilities.getPreferredSize ().width , utilities.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width , 605 );
        //utilities.setBounds ( ( int ) ( width - utilities.getPreferredSize ().width ) / 2 , 5 , utilities.getPreferredSize ().width , 495 );
        //scroll.setBounds ( ( int ) ( width - utilities.getPreferredSize ().width ) / 2 , 5 , width-25 , 500 );
        int leftMargin = ( int ) ( width - utilities.getPreferredSize ().width ) / 2;
        utilities.setBounds ( leftMargin , 5 , utilities.getPreferredSize ().width , 580 );
        utilities.loadContent ( "grade");
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Utilities Master" );
        home.revalidate ();
        home.repaint ();

    }

    private void showTestingScreen () {

        
         //InventoryWidgetNew utilities = new InventoryWidgetNew();
         InventoryWidget1 utilities = new InventoryWidget1();
        scroll = new JScrollPane ( utilities ,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER ,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );

        //utilities.setBounds ( ( int ) ( width - utilities.getPreferredSize ().width ) / 2 , 5 , utilities.getPreferredSize ().width , utilities.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width , 605 );
        //utilities.setBounds ( ( int ) ( width - utilities.getPreferredSize ().width ) / 2 , 5 , utilities.getPreferredSize ().width , 495 );
        //scroll.setBounds ( ( int ) ( width - utilities.getPreferredSize ().width ) / 2 , 5 , width-25 , 500 );
        int leftMargin = ( int ) ( width - utilities.getPreferredSize ().width ) / 2;
        utilities.setBounds ( leftMargin , 5 , utilities.getPreferredSize ().width , 580 );
        //utilities.loadContent ( "grade");
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( " Graph" );
        home.revalidate ();
        home.repaint ();
//        TestingMaster testingM = new TestingMaster ();
//        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
//        int screenW = ( int ) d.getWidth ();
//        testingM.setBounds ( ( screenW - 1250 ) / 2 , 0 , 1250 , 660 );
//        testingM.loadContent ();
//        home.jPanel1.removeAll ();
//        home.jPanel1.setLayout ( null );
//        home.jPanel1.add ( testingM );
//        
//        jLabel6.setText ( "  Testing Master" );
////         home.jPanel1.add ( jLabel6 );
//        
//        home.revalidate ();
//        home.repaint ();
    }

    private void showCalibrationScreen () {

//        Calibration calibration = new Calibration ();
//        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
//        int screenW = ( int ) d.getWidth ();
//        // calibration.setBounds(0, 0, home.jPanel1.getWidth(), home.jPanel1.getHeight());
//        calibration.setBounds ( ( screenW - 1250 ) / 2 , 0 , 1250 , 660 );
//        calibration.loadContent ();
//        home.jPanel1.removeAll ();
//        home.jPanel1.setLayout ( null );
//        home.jPanel1.add ( calibration );
//        
//        jLabel6.setText ( "  Calibration Master" );
// //        home.jPanel1.add ( jLabel6 );
//        
//        home.revalidate ();
//        home.repaint ();
    }

    private void showFinishedGoodsScreen () {

    //    bp1 = new ButtonPanel ();
   //     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        FinishedGoodMaster finishedGoods = new FinishedGoodMaster ();
        scroll = new JScrollPane ( finishedGoods ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        finishedGoods.loadContent ();
//        finishedGoods.setBounds ( ( int ) ( width - finishedGoods.getPreferredSize ().width ) / 2 , 5 , finishedGoods.getPreferredSize ().width , finishedGoods.getPreferredSize ().height );
//        scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - finishedGoods.getPreferredSize ().width ) / 2;
        finishedGoods.setBounds ( leftMargin , 5 , finishedGoods.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        jLabel6.setText ( "  Finished Goods Master" );
        //home.jPanel1.add ( bp1 );
        //        home.jPanel1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();

    }
    
    private void updateStockPanel(){
   //     bp1 = new ButtonPanel ();
   //     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        UpdateStockPanel updateStockRM = new UpdateStockPanel ();
        scroll = new JScrollPane ( updateStockRM ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //updateStockRM.setBounds ( ( int ) ( width - updateStockRM.getPreferredSize ().width ) / 2 , 5 , updateStockRM.getPreferredSize ().width , updateStockRM.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );up
        
      //  updateStockRM.setPreferredSize(new java.awt.Dimension(1000,600));
        int leftMargin = ( int ) ( width - updateStockRM.getPreferredSize ().width ) / 2;
        updateStockRM.setBounds ( leftMargin , 5 , updateStockRM.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Inventory/Stock Master" );
        //     home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
    }
    
    private void updateFGStockPanel(){
   //     bp1 = new ButtonPanel ();
   //     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        UpdateStockPanel2 updateStockRM = new UpdateStockPanel2 ();
        scroll = new JScrollPane ( updateStockRM ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //updateStockRM.setBounds ( ( int ) ( width - updateStockRM.getPreferredSize ().width ) / 2 , 5 , updateStockRM.getPreferredSize ().width , updateStockRM.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - updateStockRM.getPreferredSize ().width ) / 2;
        updateStockRM.setBounds ( leftMargin , 5 , updateStockRM.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Finished Goods Stock Master" );
        //     home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
    }
    
    private void showProcessMasterForm(){
        
   //     bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        Process_Master_Form process = new Process_Master_Form ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Process Master" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        
    }
    
    private void showFirstPlanningForm(){
        
   //     bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        PanningUI_One process = new PanningUI_One ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , process.getPreferredSize ().width , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Process Master" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        
    }
    
    private void showSalesForm(){
        
   //      bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

  
        //SalesForm salesForm = new SalesForm ();
        SalesMaster salesForm = new SalesMaster() ;
        scroll = new JScrollPane ( salesForm ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //salesForm.setBounds ( ( int ) ( width - salesForm.getPreferredSize ().width ) / 2 , 5 , salesForm.getPreferredSize ().width , salesForm.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        //salesForm.setPreferredSize(new java.awt.Dimension(1000,580));
        int leftMargin = ( int ) ( width - salesForm.getPreferredSize ().width ) / 2;
        salesForm.setBounds ( leftMargin , 5 , salesForm.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Sales Master" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        
}

    public boolean showLogin () {

        loginDlg.setVisible ( true );
        // if logon successfully
        if ( loginDlg.isSucceeded () ) {
            Proman_User.setUsername ( "" );
            Proman_User.setPassword ( "" );
            Proman_User.setIsActivelyLoggedIn ( true );

            return true;
        } else {
            return false;
        }
    }

    public static boolean isSessionActive () {
        try {

            Connection _con = DriverManager.getConnection ( "jdbc:sqlite:SSDsdIadc_xlp.db" );
            Calendar c2 = Calendar.getInstance ();
            SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
            String strDate2 = sdf2.format ( c2.getTime () );

            String checkActiveSession = "select MAX(LID) as lid, UUID,  ISACTIVE, DEPARTMENT, ROLE, FULLNAME, UNAME from lilosessIONS";
            Statement st = _con.createStatement ();
            ResultSet rs = st.executeQuery ( checkActiveSession );
            int lid, uid, isactive;
            String litime, role, deptid, uname;

            lid = rs.getInt ( "lid" );
            uid = rs.getInt ( "UUID" );
            isactive = rs.getInt ( "ISACTIVE" );
            deptid = rs.getString ( "DEPARTMENT" );
            role = rs.getString ( "ROLE" );
            uname = rs.getString ( "UNAME" ) ;
            
            StaticValues.setHeader(   rs.getString ( "FULLNAME" )  ) ;
            
            rs.close ();
            st.close ();

            if ( isactive == 1 ) {
                
                //String getRolesPermissions = ""
                
                
//                try{
//                    String response = WebServices_Controller.getToken ( "","" );
//                    JSONObject responseObject = (JSONObject) new JSONTokener(response).nextValue() ;
//                    StaticValues.setHeader( responseObject.getJSONObject ( "data").getString("token") );
//                }catch(Exception e){
//                    
//                }                
//                jMenu8.setVisible ( true );
//                //jMenu9.setVisible ( true );
//                jMenu10.setVisible ( true );
//                jMenu11.setVisible ( true );
//                jMenu12.setVisible ( true );
//                jMenu13.setVisible ( true );




                loginSessionActive = true;
                rs.close ();
                Proman_User.setIsActivelyLoggedIn ( true );
                HomeScreen.home.jLabel3.setText ( "Welcome " + Proman_User.getUsername () + " " );
                jLabel5.setVisible ( true );


                String logSession = "update lilosessIONS set   'LITIME' = '" + strDate2 + "' where LID = " + lid;

                st = _con.createStatement ();
                st.execute ( logSession );

               // String getUserName = "select EMP_NAME from employee where EmployeePK = " + uid;
               // rs = DB_Operations.executeSingle ( getUserName );
              //  Proman_User.setUsername ( rs.getString ( "EMP_NAME" ) );
                Proman_User.setEID ( uid );
                Proman_User.setDeptIds ( deptid );
                Proman_User.setRole ( role );
                Proman_User.setUsername (   uname        ) ;

                String[] deptRole  ;
                
                deptRole = filterMenusAndScreens( uid  ) ;
                
                ArrayList<AlarmData> alarmData  = null ;
                
                if( ! deptRole[0].equals (  "Failed : " )){
                
                int _dept = Integer.parseInt( deptRole[0] );
                int _role = Integer.parseInt( deptRole[1] );
                
                //department 1 
                // Role 1 : Admin  ,     Role 2 = Supervisor  ,   Role 3 = Operator
                
                switch(   _role  )
                {

                                case 1:
                                        jMenuItem31.setVisible ( true );
                                       // jMenuItem13.setVisible ( true );
                                        jMenu8.setVisible( true ) ;
                                        jMenu10.setVisible( true ) ;
                                        jMenu11.setVisible( true ) ;
                                        jMenu12.setVisible( true ) ;
                                        jMenu13.setVisible( false ) ;
                                        jMenuItem38.setVisible( true  );
                                        HomeScreen.jMenuBar1.setVisible(true);
                                        HomeScreen.home.LoadDashboard ();
                                        
                                        alarmController = new AlarmController3 ( true );
                                        alarm = new Alarms ();
                                        alarmData = new ArrayList<AlarmData> ();
                                        alarmData = alarm.getAlarmState ();
                                        alarmController.startAlarm ( "Preparing notifications" , true , alarmData );
                                        
                                    break ;

                                case 2:
                                        jMenuItem31.setVisible ( false );
                                        jMenuItem13.setVisible ( false );
                                        jMenu8.setVisible( true ) ;
                                        jMenu10.setVisible( true ) ;
                                        jMenu11.setVisible( true ) ;
                                        jMenu12.setVisible( true ) ;
                                        jMenu13.setVisible( false ) ;
                                        jMenuItem38.setVisible( true  );
                                        HomeScreen.jMenuBar1.setVisible(true);
                                        HomeScreen.home.LoadDefaultDataEntry ();
                                        
                                        alarmController = new AlarmController3 ( true );
                                        alarm = new Alarms ();
                                        alarmData = new ArrayList<AlarmData> ();
                                        alarmData = alarm.getAlarmState ();
                                        alarmController.startAlarm ( "Preparing notifications" , true , alarmData );
                                        
                                    break ;

                                case 3:
                                        jMenu8.setVisible( false ) ;
                                        jMenu10.setVisible( false ) ;
                                        jMenu11.setVisible( false ) ;
                                        jMenu12.setVisible( false ) ;
                                        jMenu13.setVisible( false ) ;
                                        jMenuItem38.setVisible( false  );
                                        jMenuItem13.setVisible ( false );
                                        HomeScreen.jMenuBar1.setVisible(true); 
                                        HomeScreen.home.LoadDefaultDataEntry ();
                                    break ;

                                default :

                            }
            //checkVersionFromJSON();                
//   //********************hide menus by product level*************************************
//        String addEmpAPICall, res;
//        addEmpAPICall = "switch";
//        res = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
//
//        if (!res.contains("not found")) {
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            JSONObject jObject = new JSONObject(res);
//            Iterator<?> keys = jObject.keys();
//
//            while (keys.hasNext()) {
//                String key = (String) keys.next();
//                Object value = jObject.get(key);
//                map.put(key, value);
//            }
//
//            JSONObject st1 = (JSONObject) map.get("data");
//            JSONArray records = st1.getJSONArray("records");
//
//            JSONObject emp = null;
//
//            for (int i = 0; i < records.length(); i++) {
//
//                emp = records.getJSONObject(i);
//                String strchk="";
//                        strchk= emp.get("s_app").toString();
//                if (strchk.equals( "enterprise")) {
//                    
//                     jMenuItem14.setVisible(false);
//                } else if (strchk.equals ("advance")) {
//                    
//                   jMenuItem38.setVisible(false); 
//                   maintain_history.setVisible(false);
//                   jMenuItem51.setVisible(false);
//                   jMenuItem6.setVisible(false);
//                   jMenuItem11.setVisible(false);
//                   COPQ.setVisible(false);
//                   jMenuItem21.setVisible(false);
//                   diefrm.setVisible(false);
//
//                } else if (strchk.equals( "basic")) {
//                    jMenu9.setVisible(false);
//                    jMenuItem2.setVisible(false);
//                    jMenuItem16.setVisible(false);
//                    jMenuItem17.setVisible(false);
//                    //RMFGcode1.setVisible(false);
//                    jMenuItem52.setVisible(false);
//                    
//                } else {
//                    //diefrm.setVisible(false);
//                    jMenuItem14.setVisible(false);
//                    jMenuItem18.setVisible(false);
//                    jMenuItem35.setVisible(false);
//                    //hourscreen.setVisible(false);
//                    jMenuItem36.setVisible(false);
//                    jMenuItem37.setVisible(false);
//                    jMenuItem47.setVisible(false);
//                    jMenuItem30.setVisible(false);
//                    jMenuItem10.setVisible(false);
//                    jMenuItem13.setVisible(false);
//                    jMenuItem8.setVisible(false);
//                    //jMenuItem11.setVisible ( false );
//                    jMenuItem19.setVisible(false);
//                    jMenuItem20.setVisible(false);
//
//                    jMenuItem9.setVisible(false);
//                    jMenu13.setVisible(false);
//                    jMenu1.setVisible(false);
//
//                    jMenuBar1.setVisible(false);
//                }
//            }
//        }
//        
//        //********************end hide menus by product level*************************************
                
                return true;
                
                }else{
                    
                }
                
            }
        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel ( HomeScreen.class.getSimpleName () , HomeScreen.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 2 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            return false;
        }

        return false;
    }
    
    public static void checkVersionFromJSON()
    {
         //********************hide menus by product level*************************************
//        String addEmpAPICall, res;
//        addEmpAPICall = "switch";
//        res = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
//
//        if (!res.contains("not found")) {
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            JSONObject jObject = new JSONObject(res);
//            Iterator<?> keys = jObject.keys();
//
//            while (keys.hasNext()) {
//                String key = (String) keys.next();
//                Object value = jObject.get(key);
//                map.put(key, value);
//            }
//
//            JSONObject st1 = (JSONObject) map.get("data");
//            JSONArray records = st1.getJSONArray("records");
//
//            JSONObject emp = null;
//
//            for (int i = 0; i < records.length(); i++) {
//
//                emp = records.getJSONObject(i);
//                String strchk="";
//                        strchk= emp.get("s_app").toString();
//                if (strchk.equals( "enterprise")) {
//                    
//                     jMenuItem14.setVisible(false);
//                } else if (strchk.equals ("advance")) {
//                    
////                   jMenuItem38.setVisible(false); 
////                   maintain_history.setVisible(false);
////                   jMenuItem51.setVisible(false);
////                   jMenuItem6.setVisible(false);
////                   jMenuItem11.setVisible(false);
////                   COPQ.setVisible(false);
////                   jMenuItem21.setVisible(false);
//                   diefrm.setVisible(true);
//
//                } else if (strchk.equals( "basic")) {
////                    jMenu9.setVisible(false);
////                    jMenuItem2.setVisible(false);
////                    jMenuItem16.setVisible(false);
////                    jMenuItem17.setVisible(false);
////                    //RMFGcode1.setVisible(false);
////                    jMenuItem52.setVisible(false);
//                    diefrm.setVisible(false);
//                    
//                } else {
//                    //diefrm.setVisible(false);
//                    jMenuItem14.setVisible(false);
//                    jMenuItem18.setVisible(false);
//                    jMenuItem35.setVisible(false);
//                    //hourscreen.setVisible(false);
//                    jMenuItem36.setVisible(false);
//                    jMenuItem37.setVisible(false);
//                    jMenuItem47.setVisible(false);
//                    jMenuItem30.setVisible(false);
//                    jMenuItem10.setVisible(false);
//                    jMenuItem13.setVisible(false);
//                    jMenuItem8.setVisible(false);
//                    //jMenuItem11.setVisible ( false );
//                    jMenuItem19.setVisible(false);
//                    jMenuItem20.setVisible(false);
//
//                    jMenuItem9.setVisible(false);
//                    jMenu13.setVisible(false);
//                    jMenu1.setVisible(false);
//
//                    jMenuBar1.setVisible(false);
//                }
//            }
//        }
        
        //********************end hide menus by product level*************************************

    }

    public static String[] filterMenusAndScreens(  int id ){
        
        int department=0, role=0 ;
        
            //&parent_id=5
        String result2 = WebAPITester.prepareWebCall ( "useredit?user_id="+id  , StaticValues.getHeader () , "" );
        
        if(  result2.contains("success")   ) {
            HashMap<String , Object> map = new HashMap<String , Object> ();
            JSONObject jObject = new JSONObject ( result2 );
            Iterator<?> keys = jObject.keys ();

            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }

             JSONArray records = ( JSONArray ) map.get ( "data" );

            JSONObject emp = null;
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );

                try{    department = Integer.parseInt(  emp.get ( "department_id" ).toString () );   }catch(   NumberFormatException e   ){    department = 0 ;            }
                try{    role = Integer.parseInt(  emp.get ( "role_id" ).toString () );      }catch(  NumberFormatException e   ){  role = 0 ;     }

                Proman_User.setUsername ( emp.get ( "firstname" ).toString () );
            }

            return new String[]{ department+"", role+"" };
            
        }else{
           
            return new String[] {  "Failed : ", result2  } ;
        }
        
        
    }
    
    
    
    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {
            showFinishedGoodsScreen ();
        } else if ( showLogin () ) {
          //  showFinishedGoodsScreen ();
        }
    }                                           

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showCustomerScreen ();
        } else if ( showLogin () ) {

        //    showCustomerScreen ();
        }
    }                                           

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showSupplierScreen ();
        } else if ( showLogin () ) {

        //    showSupplierScreen ();
        }
    }                                           

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {
            showMachineScreen ();
        } else if ( showLogin () ) {
           // showMachineScreen ();
        }


    }                                           

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showRawMaterialScreen ();
        } else if ( showLogin () ) {

         //   showRawMaterialScreen ();
        }
    }                                           

    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showTestingScreen ();
        } else if ( showLogin () ) {

        //    showTestingScreen ();
        }
    }                                           

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) 
        {

            showEmployeeScreen ();
        } else if ( showLogin () ) {

       //     showEmployeeScreen ();
        }
    }                                           

    private void jMenuItem33ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showUtilitiesScreen ();
        } else if ( showLogin () ) {

       //     showUtilitiesScreen ();
        }
    }                                           

    private void jMenuItem34ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        if ( Proman_User.isActivelyLoggedIn () ) {

            showProcessMasterForm ()        ;
        } else if ( showLogin () ) {
        //    showProcessMasterForm ()        ;
        }
    
    }                                           
    
    private void jMenuItem56ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        if ( Proman_User.isActivelyLoggedIn () ) {
            showFirstPlanningForm ()        ;
        } else if ( showLogin () ) {
        //    showProcessMasterForm ()        ;
        }
    
    } 

    private void jMenuItem51ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:

          if ( Proman_User.isActivelyLoggedIn () ) {
            updateStockPanel();
         } else if ( showLogin () ) {
       //    updateStockPanel();
        }
    }                                           

    private void jMenuItem38ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {
            showSalesForm();
        }else if ( showLogin () ) {
   //         showSalesForm();
        }

    }                                           

    private void jMenuItem52ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:

      //  bp1 = new ButtonPanel ();
     //   bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

     if ( Proman_User.isActivelyLoggedIn () ) {
            
         Processforfinishedgoods process = new Processforfinishedgoods ();

        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Product-Process-Machine Map Master" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

     /*        Processforfinishedgoods process = new Processforfinishedgoods ();

        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Product-Process-Machine Map Master" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();*/

	 } 
     
        

    }                                           

    private void jMenuItem40ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    //    bp1 = new ButtonPanel ();
    //    bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

    if ( Proman_User.isActivelyLoggedIn () ) {
            
        DailyReportForm2 process = new DailyReportForm2 ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        //process.setPreferredSize(new  java.awt.Dimension(1000, 600));
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Production Entry - Product wise" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

 /*            DailyReportForm2 process = new DailyReportForm2 ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Production Entry - Product wise" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
*/
	 } 
    
        

    }                                           

    private void jMenuItem50ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        if ( Proman_User.isActivelyLoggedIn () ) {
            LoadDashboard ();
        } else if ( showLogin () ) {
            LoadDashboard ();
        }
    }                                           

    public static JScrollPane scroll = null;

    private void jMenuItem44ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        //  ProductionNWIP2 process = new ProductionNWIP2 ();
    //    bp1 = new ButtonPanel ();
    ///    bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

    if ( Proman_User.isActivelyLoggedIn () ) {
            
        ReportsController process = new ReportsController ( "Production Quantity" );

        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Production Quantity Report" );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

   /*          ReportsController process = new ReportsController ( "Production Quantity" );

        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Production Quantity Report" );

        home.revalidate ();
        home.repaint ();
*/
	 } 
    
        

    }                                           

    private void jMenuItem53ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        //     ReportWIP1 process = new ReportWIP1();
   //     bp1 = new ButtonPanel ();
   //     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

   if ( Proman_User.isActivelyLoggedIn () ) {

       ReportsController process = new ReportsController ( "Work In Progress" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Work In Progress Report" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

 /*            ReportsController process = new ReportsController ( "Work In Progress" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Work In Progress Report" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
*/
	 } 
   
        

    }                                           

    private void jMenuItem54ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
     //   bp1 = new ButtonPanel ();
    //    bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

    if ( Proman_User.isActivelyLoggedIn () ) {

         ReportWIP2 process = new ReportWIP2 ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );
        
            
        
        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Work In Progress Report" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {
/*
              ReportWIP2 process = new ReportWIP2 ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );
        
            
        
        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Work In Progress Report" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
*/
	 }
    
       

    }                                           

    private void jMenuItem49ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
//        ReportRejection process = new ReportRejection();
    //    bp1 = new ButtonPanel ();
   //     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

   if ( Proman_User.isActivelyLoggedIn () ) {

        ReportsController process = new ReportsController ( "Rejection" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

//        process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
//        scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Rejection Report" );
        //      home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {
/*
              ReportsController process = new ReportsController ( "Rejection" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

//        process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
//        scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Rejection Report" );
        //      home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
*/
	 }
    }                                           
   //*************Created By Harshali **************************
    
     private void jMenuItemRMActionPerformed(java.awt.event.ActionEvent evt) {
        if ( Proman_User.isActivelyLoggedIn () ) 
         {
            RMQualityEntryForm rmquality=new RMQualityEntryForm();
             scroll = new JScrollPane ( rmquality ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - rmquality.getPreferredSize ().width ) / 2 ;
        rmquality.setBounds ( leftMargin , 5 , rmquality.getPreferredSize ().width , 580 );
        //user.setBounds ( 100 , 5 , user.getPreferredSize ().width , 200 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );
        //scroll.setBounds ( 100 - 20 , 5 , width - leftMargin , 500 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
       
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        jLabel6.setText ( "Raw-Material Quality Entry " );
   

        home.revalidate ();
        home.repaint ();
            
         }

     
     }
     private void jMenuItemFGActionPerformed(java.awt.event.ActionEvent evt) {
     
      if ( Proman_User.isActivelyLoggedIn () ) 
         {
            FGQualityEntryForm fgquality=new FGQualityEntryForm();
             scroll = new JScrollPane ( fgquality ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - fgquality.getPreferredSize ().width ) / 2 ;
        fgquality.setBounds ( leftMargin , 5 , fgquality.getPreferredSize ().width , 580 );
        //user.setBounds ( 100 , 5 , user.getPreferredSize ().width , 200 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );
        //scroll.setBounds ( 100 - 20 , 5 , width - leftMargin , 500 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
       
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        jLabel6.setText ( "Parts Quality Entry " );
   

        home.revalidate ();
        home.repaint ();
            
         }

     
     
     }
     
     
     private void jMenuItemProcActionPerformed(java.awt.event.ActionEvent evt) {
     
      if ( Proman_User.isActivelyLoggedIn () ) 
         {
            ProcessQualityEntryForm proquality=new ProcessQualityEntryForm();
             scroll = new JScrollPane ( proquality ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - proquality.getPreferredSize ().width ) / 2 ;
        proquality.setBounds ( leftMargin , 5 , proquality.getPreferredSize ().width , 580 );
        //user.setBounds ( 100 , 5 , user.getPreferredSize ().width , 200 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );
        //scroll.setBounds ( 100 - 20 , 5 , width - leftMargin , 500 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
       
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        jLabel6.setText ( "Proccess Quality Entry " );
   

        home.revalidate ();
        home.repaint ();
            
         }

     
     
     }
     
     private void jMenuItemProcReportActionPerformed(java.awt.event.ActionEvent evt) {
             if ( Proman_User.isActivelyLoggedIn () ) 
         {
            ProcessQualityReport_1 procquality=new ProcessQualityReport_1();
             scroll = new JScrollPane ( procquality ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
           //procquality.setPreferredSize(new java.awt.Dimension ( 900, 700 ));
        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
         int leftMargin = ( int ) ( width - procquality.getPreferredSize ().width ) / 2;
        procquality.setBounds ( leftMargin , 5 , procquality.getPreferredSize ().width , 700 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        jLabel6.setText ( "Parts Quality Report " );
   

        home.revalidate ();
        home.repaint ();
            
         }
     }
     
     private void jMenuItemRMReportActionPerformed(java.awt.event.ActionEvent evt) {
          if ( Proman_User.isActivelyLoggedIn () ) 
         {
           RMQualityReport_1 rmquality=new RMQualityReport_1();
             scroll = new JScrollPane ( rmquality ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
           //rmquality.setPreferredSize(new java.awt.Dimension ( 900, 700 ));
        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
         int leftMargin = ( int ) ( width - rmquality.getPreferredSize ().width ) / 2;
        rmquality.setBounds ( leftMargin , 5 , rmquality.getPreferredSize ().width , 700 );
         rmquality.repaint();
         rmquality.revalidate();
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        jLabel6.setText ( "RM Quality Report " );
   

        home.revalidate ();
        home.repaint ();
            
         }   
     }
     private void jMenuItemPartReportActionPerformed(java.awt.event.ActionEvent evt) {
            if ( Proman_User.isActivelyLoggedIn () ) 
         {
            FGQualityReport_1 fgquality=new FGQualityReport_1();
             scroll = new JScrollPane ( fgquality ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
           //fgquality.setPreferredSize(new java.awt.Dimension ( 900, 700 ));
        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
         int leftMargin = ( int ) ( width - fgquality.getPreferredSize ().width ) / 2;
        fgquality.setBounds ( leftMargin , 5 , fgquality.getPreferredSize ().width , 700 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        jLabel6.setText ( "Parts Quality Report " );
   

        home.revalidate ();
        home.repaint ();
            
         }
     }
     
     
     //**************************************
     
    private void jMenuItem41ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
  //      bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

                if ( Proman_User.isActivelyLoggedIn () ) 
                {

                    DailyReportFormProcessWise process = new DailyReportFormProcessWise ();
                scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Entry - Process wise" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {
/*
             DailyReportFormProcessWise process = new DailyReportFormProcessWise ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Entry - Process wise" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
*/
	 }
    }      
    
    private void hourscreenActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
            if ( Proman_User.isActivelyLoggedIn () ) {

            DailyReportFormHourWise hscreen = new DailyReportFormHourWise ();
          scroll = new JScrollPane ( hscreen ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - hscreen.getPreferredSize ().width ) / 2;
        hscreen.setBounds ( leftMargin , 5 , hscreen.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Entry - Hour wise" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {
/*
             DailyReportFormProcessWise process = new DailyReportFormProcessWise ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Entry - Process wise" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
*/
	 }
    } 
    
    
    private void diefrmActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
         if ( Proman_User.isActivelyLoggedIn () ) {

             //Die_Master_old die = new Die_Master_old();
             Die_master1  die =new Die_master1();
        scroll = new JScrollPane ( die ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - die.getPreferredSize ().width ) / 2;
        die.setBounds ( leftMargin , 5 , die.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Die Master" );
        //     home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

 /*            DailyReportFormMachineWise process = new DailyReportFormMachineWise ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Entry - Machine wise" );
        //     home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
*/
	 }
    }                                      

    

    private void jMenuItem42ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    //    bp1 = new ButtonPanel ();
   //     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

   if ( Proman_User.isActivelyLoggedIn () ) {

       DailyReportFormMachineWise process = new DailyReportFormMachineWise ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Entry - Machine wise" );
        //     home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

 /*            DailyReportFormMachineWise process = new DailyReportFormMachineWise ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Entry - Machine wise" );
        //     home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
*/
         }
    }                 
    private void processQuotationActionPerformed(java.awt.event.ActionEvent evt) {
          
          if ( Proman_User.isActivelyLoggedIn () ) 
          {
              Process_Master_Quatation user=new Process_Master_Quatation();
          scroll = new JScrollPane ( user ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
         

        //emplHRMaster.setBounds ( ( int ) ( width - emplHRMaster.getPreferredSize ().width ) / 2 , 5 , emplHRMaster.getPreferredSize ().width , emplHRMaster.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - user.getPreferredSize ().width ) / 2;
        user.setBounds ( leftMargin , 5 , user.getPreferredSize ().width , 580 );
        //user.setBounds ( 100 , 5 , user.getPreferredSize ().width , 200 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );
        //scroll.setBounds ( 100 - 20 , 5 , width - leftMargin , 500 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "Process Quotation" );

        home.revalidate ();
        home.repaint ();

          
          }
      }
    private void maintain_historyActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
        if ( Proman_User.isActivelyLoggedIn () ) {

       Maintenance_history mh = new Maintenance_history ();
        scroll = new JScrollPane ( mh ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - mh.getPreferredSize ().width ) / 2;
        mh.setBounds ( leftMargin , 5 , mh.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Maintenance History" );
        //     home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

 /*            DailyReportFormMachineWise process = new DailyReportFormMachineWise ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Entry - Machine wise" );
        //     home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
*/
         }
    }                                                


    private void jMenuItem43ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    //    bp1 = new ButtonPanel ();
   ///     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

   if ( Proman_User.isActivelyLoggedIn () ) {

       DailyReportFormEmployeeWise dailyemp = new DailyReportFormEmployeeWise();
        scroll = new JScrollPane ( dailyemp ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - dailyemp.getPreferredSize ().width ) / 2;
        dailyemp.setBounds ( leftMargin , 5 , dailyemp.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Production Entry - Employee wise" );
        //     home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {
 /* 
             DailyReportFormEmployeeWise process = new DailyReportFormEmployeeWise ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Production Entry - Employee wise" );
        //     home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
 */
	 }
   
        

    }                                           

    //***********Created by harshali
    //**** for create user menu
      private void jMenuItem55ActionPerformed(java.awt.event.ActionEvent evt) {
          
          if ( Proman_User.isActivelyLoggedIn () ) 
          {
          Create_User user=new Create_User();
          scroll = new JScrollPane ( user ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
           user.loadcontent();

        //emplHRMaster.setBounds ( ( int ) ( width - emplHRMaster.getPreferredSize ().width ) / 2 , 5 , emplHRMaster.getPreferredSize ().width , emplHRMaster.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - user.getPreferredSize ().width ) / 2;
        user.setBounds ( leftMargin , 5 , user.getPreferredSize ().width , 580 );
        //user.setBounds ( 100 , 5 , user.getPreferredSize ().width , 200 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );
        //scroll.setBounds ( 100 - 20 , 5 , width - leftMargin , 500 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Create User" );

        home.revalidate ();
        home.repaint ();

          
          }
      }
 ////*************************   
    
    private void jMenuItem46ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
   //     bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

  if ( Proman_User.isActivelyLoggedIn () ) 
  {
            
      PlantMaster2 process = new PlantMaster2 ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Plant Master" );
        //     home.jPanel1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

/*               PlantMaster2 process = new PlantMaster2 ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Plant Master" );
        //     home.jPanel1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();
  */
	 }
  
        

    }                                           

    private void jMenuItem45ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        //      ReportProductionValue process = new ReportProductionValue ();
    //    bp1 = new ButtonPanel ();
    //    bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

    if ( Proman_User.isActivelyLoggedIn () ) {
            
        ReportsController process = new ReportsController ( "Production Value" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Value Report" );
        //       home.jPanel1.add ( jLabel6 );
        //      
        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

/*               ReportsController process = new ReportsController ( "Production Value" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Value Report" );
        //       home.jPanel1.add ( jLabel6 );
        //      
        home.revalidate ();
        home.repaint ();
 */
	 }
    
        

    }                                           

    private void jMenuItem48ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
//        ReportProductivity process = new ReportProductivity ();
        // OperatorEfficiencyReport effReport = new OperatorEfficiencyReport();
   //     bp1 = new ButtonPanel ();
   //     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );
   
   
   if ( Proman_User.isActivelyLoggedIn () ) {
            
       ReportsController process = new ReportsController ( "Productivity" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Productivity Report" );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

/*  
             ReportsController process = new ReportsController ( "Productivity" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Productivity Report" );

        home.revalidate ();
        home.repaint ();
  */
	 }
   
        

    }                                           

    private void jMenuItem39ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
  //      bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );
       
  
  if ( Proman_User.isActivelyLoggedIn () ) 
  {
            
      DailyReportForm process = new DailyReportForm ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
       // process.setPreferredSize(new java.awt.Dimension(1000, 600));
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        ////home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Data Entry" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();

	 } else if ( showLogin () ) {

 /*              DailyReportForm process = new DailyReportForm ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Data Entry" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();
  */
	 }
  
  
  

    }   
    private void quotationActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
        if ( Proman_User.isActivelyLoggedIn () ) {

            EnquiriesForm mh = new EnquiriesForm ();
        scroll = new JScrollPane ( mh ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - mh.getPreferredSize ().width ) / 2;
        mh.setBounds ( leftMargin , 5 , mh.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "Enquiries " );
        //     home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

 /*            DailyReportFormMachineWise process = new DailyReportFormMachineWise ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Entry - Machine wise" );
        //     home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
*/
         }
    } 

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        //DocumentManagement process = new DocumentManagement ();
   ///     bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

  
  if ( Proman_User.isActivelyLoggedIn () ) {

      DocumentRepository process = new DocumentRepository ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Documents Master" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();

	 } else if ( showLogin () ) {

/*               
             DocumentRepository process = new DocumentRepository ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        ////home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Documents Master" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();
  */
	 }
  
        

    }                                          

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
 //       bp1 = new ButtonPanel ();
 //       bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        if ( Proman_User.isActivelyLoggedIn () ) {
            
            ProductCustomerMap process = new ProductCustomerMap ();
            // home.jPanel1.setBounds ( ((screenW-paneW)/2), 0, paneW, 650 );
            scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        ////home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Product-Customer Map Master" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();

	 } else if ( showLogin () ) {

 /*  
             ProductCustomerMap process = new ProductCustomerMap ();
        // home.jPanel1.setBounds ( ((screenW-paneW)/2), 0, paneW, 650 );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        ////home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Product-Customer Map Master" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();
  */
	 }
 
        

    }                                          

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
//        PPM_Report ppmReport = new PPM_Report ();
  //      bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

  
  if ( Proman_User.isActivelyLoggedIn () ) {
            
      //ReportsController process = new ReportsController ( "Total Rejection" );
      ReportsController process = new ReportsController ( "PPM" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        ////home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Total Rejection Report" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();

	 } else if ( showLogin () ) {

/*               ReportsController process = new ReportsController ( "Total Rejection" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Total Rejection Report" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();
  */
	 }
  
        

    }                                          

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    //    bp1 = new ButtonPanel ();
   //     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

   if ( Proman_User.isActivelyLoggedIn () ) {
            
       //ReportCostValue ppmReport = new ReportCostValue ();
        ReportsController process = new ReportsController ( "Cost VS Value" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Cost VS Value Report" );
//         home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();

	 } else if ( showLogin () ) {

/*               
             //ReportCostValue ppmReport = new ReportCostValue ();
        ReportsController process = new ReportsController ( "Cost VS Value & PMPH" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Cost VS Value & PMPH Report" );
//         home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();
        
        */
	 }
   
   
        


    }                                          

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:

   //     bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        if ( Proman_User.isActivelyLoggedIn () ) {
            
            EmployeeProcessMap process = new EmployeeProcessMap ();
             scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

//        process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
//        scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Employee-Process Map Master" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();

	 } else if ( showLogin () ) {

 /*              
             EmployeeProcessMap process = new EmployeeProcessMap ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

//        process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
//        scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Employee-Process Map Master" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();
  */
	 }
  
    }                                          

    private void jMenu12ActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:


    }                                       

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
     //   bp1 = new ButtonPanel ();
    //    bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        if ( Proman_User.isActivelyLoggedIn () ) {
            
            Alarms process = new Alarms ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 10 , 5 , process.getPreferredSize ().width + 10 , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Alarm Settings" );
        home.revalidate ();
        home.repaint ();

        } else if ( showLogin () ) {

/*              
            
            Alarms process = new Alarms ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 10 , 5 , process.getPreferredSize ().width + 10 , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Alarm Settings" );
        home.revalidate ();
        home.repaint ();

  */
        }
    
        


    }    
      private void rm_quotationActionPerformed(java.awt.event.ActionEvent evt) {                                                 
           if ( Proman_User.isActivelyLoggedIn () ) 
          {
              Raw_Material_Quotation user=new Raw_Material_Quotation();
          scroll = new JScrollPane ( user ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
         

        //emplHRMaster.setBounds ( ( int ) ( width - emplHRMaster.getPreferredSize ().width ) / 2 , 5 , emplHRMaster.getPreferredSize ().width , emplHRMaster.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - user.getPreferredSize ().width ) / 2;
        user.setBounds ( leftMargin , 5 , user.getPreferredSize ().width , 580 );
        //user.setBounds ( 100 , 5 , user.getPreferredSize ().width , 200 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );
        //scroll.setBounds ( 100 - 20 , 5 , width - leftMargin , 500 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Raw Material Quotation" );

        home.revalidate ();
        home.repaint ();
       }
    }   

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    //    bp1 = new ButtonPanel ();
    //    bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        if ( Proman_User.isActivelyLoggedIn () ) {
            
            ReportsController process = new ReportsController ( "OEE" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

//        process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
//    scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Overall Equippment Efficiency Report" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();

	 } else if ( showLogin () ) {

 /*         ReportsController process = new ReportsController ( "OEE" );
        scroll = new JScrollPane ( process ,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

//        process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
//    scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Overall Equippment Efficiency Report" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();

  */
	 }
    

    }                                           

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:

    //    bp1 = new ButtonPanel ();
    //    bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

    if ( Proman_User.isActivelyLoggedIn () ) {
            
        SetupForm process = new SetupForm ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Daily Setup Request & Approval Master" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();

    } else if ( showLogin () ) {

 /*         SetupForm process = new SetupForm ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Daily Setup Request & Approval Master" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();
  */
    }
    
    
        

    }                                           

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
   //     bp1 = new ButtonPanel ();
    //    bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        if ( Proman_User.isActivelyLoggedIn () ) {
            
            UserRolePermissionMaster process = new UserRolePermissionMaster ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 495 );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Users-Roles-Permissions Master" );
        //       home.jPanel1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();

        } else if ( showLogin () ) {

 /*             UserRolePermissionMaster process = new UserRolePermissionMaster ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 495 );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Users-Roles-Permissions Master" );
        //       home.jPanel1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();
  */
        }
    
        

    }                                           

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:

//        ImportMasters process = new ImportMasters ();
//        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
//        int screenW = ( int ) d.getWidth ();
//        int screenH = ( int ) d.getHeight ();
//        int paneW = 1250;
//
//        process.setBounds (5 , 5 , 1310 , 600 );
//
//        home.jPanel1.removeAll ();
//        home.jPanel1.setLayout ( null );
//        home.jPanel1.add ( process );
//        home.revalidate ();
//        home.repaint ();
    }                                           

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:

        int selection = JOptionPane.showConfirmDialog ( null , "You will be logged out from current session. You want to condinue ? " );

        if ( selection == JOptionPane.YES_OPTION ) {
            logout ();
        }

    }                                    

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
  //      bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        if ( Proman_User.isActivelyLoggedIn () ) {
            
            ReportsController process = new ReportsController ( "Defect Occurrances" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

//        process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
//        scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 15 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Defect Occurrance Report" );

        home.revalidate ();
        home.repaint ();

        home.setExtendedState ( JFrame.MAXIMIZED_BOTH );

    } else if ( showLogin () ) {

 /*         ReportsController2 process = new ReportsController2 ( "OEE" );
           scroll = new JScrollPane ( process ,
                   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                   JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

   //        process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
   //        scroll.setBounds ( 5 , 5 , width-25 , 605 );
           int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
           process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
           scroll.setBounds ( leftMargin - 15 , 5 , width - leftMargin , 580 );

           scroll.setBackground ( Color.WHITE );
           scroll.setBorder ( BorderFactory.createEmptyBorder () );

           home.jPanel1.removeAll ();
           home.jPanel1.setLayout ( null );
           home.jPanel1.add ( scroll , BorderLayout.CENTER );
           //home.jPanel1.add ( bp1 );
           jLabel6.setText ( "  Defect Occurrance Report" );

           home.revalidate ();
           home.repaint ();

           home.setExtendedState ( JFrame.MAXIMIZED_BOTH );
  */
    }
  
  
        
    }                                           

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
  //      bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

  if ( Proman_User.isActivelyLoggedIn () ) {
          BillOfMaterialUI2 process = new BillOfMaterialUI2 ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

//        process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
//        scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 15 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Bill of Material Master" );

        //      home.jPanel1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();  

 } else if ( showLogin () ) {
/*  
     BillOfMaterialUI2 process = new BillOfMaterialUI2 ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

//        process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
//        scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 15 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Bill of Material Master" );

        //      home.jPanel1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();
  */
 }
  
        
    }                                           

    private void jMenu8ActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:


    }                                      

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
  //      bp1 = new ButtonPanel ();
 //       bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

 if ( Proman_User.isActivelyLoggedIn () ) {
          
     BatchProcessing process = new BatchProcessing ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        //process.setPreferredSize(new java.awt.Dimension(1000, 600));
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 15 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Raw Material Processing-Batch Master" );

        //       home.jPanel1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();

 } else if ( showLogin () ) {

 /*      
     BatchProcessing process = new BatchProcessing ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 15 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Raw Material Processing-Batch Master" );

        //       home.jPanel1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();
 */
 }
 
        
    }                                           

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {                                            
// TODO add your handling code here:
//        appSettings process = new appSettings ();
//                Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
//                int screenW = ( int ) d.getWidth ();
//                int screenH = ( int ) d.getHeight ();
//                int paneW = 1300;
//
//                process.setBounds ( ( int ) ( screenW - paneW ) / 2 , 0 , paneW , 650 );
//
//                home.jPanel1.removeAll ();
//                home.jPanel1.setLayout ( null );
//                home.jPanel1.add ( process );
//                home.revalidate ();
//                home.repaint ();


    }                                           

    
    private void RMFGcodeActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
         if ( Proman_User.isActivelyLoggedIn () ) {
            // ReportsController3 process = new ReportsController3 ( "Production to Sales Coverage Ratio" );
             RMandPartCodeform process = new RMandPartCodeform();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        //process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 25 , 5 , width - leftMargin , 590 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( " Raw Material and Part Code" );

        home.revalidate ();
        home.repaint ();

 } else if ( showLogin () ) {
  
     /*  
     ReportsController process = new ReportsController ( "Production to Sales Coverage Ratio" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production to Sales Coverage Ratio Report" );

        home.revalidate ();
        home.repaint ();
  */
        }
    } 
    
    
    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
   //     bp1 = new ButtonPanel ();
   ///     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        if ( Proman_User.isActivelyLoggedIn () ) {
            // ReportsController3 process = new ReportsController3 ( "Production to Sales Coverage Ratio" );
        ReportsController process = new ReportsController ( "Production to Sales Coverage Ratio" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production to Sales Coverage Ratio Report" );

        home.revalidate ();
        home.repaint ();

 } else if ( showLogin () ) {
  
     /*  
     ReportsController process = new ReportsController ( "Production to Sales Coverage Ratio" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production to Sales Coverage Ratio Report" );

        home.revalidate ();
        home.repaint ();
  */
 }
   
       

    }  
      
    
//     private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {                                            
//        // TODO add your handling code here:
//         if ( Proman_User.isActivelyLoggedIn () ) {
//
//        RMandPartCodeform rmpart=new RMandPartCodeform();
//       // ReportsController process = new ReportsController ("Raw Material Efficiency");
//      //  RMEfficienctReport process = new RMEfficienctReport ( "Raw Material Efficiency" );
//        scroll = new JScrollPane ( rmpart ,
//                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
//                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
//
//        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
//        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
//        int leftMargin = ( int ) ( width - rmpart.getPreferredSize ().width ) / 2;
//        rmpart.setBounds ( leftMargin , 5 , rmpart.getPreferredSize ().width , 580 );
//        scroll.setBounds ( leftMargin - 15 , 5 , width - leftMargin , 580 );
//
//        scroll.setBackground ( Color.WHITE );
//        scroll.setBorder ( BorderFactory.createEmptyBorder () );
//
//        home.jPanel1.removeAll ();
//        home.jPanel1.setLayout ( null );
//        //home.jPanel1.add ( customer );
//        home.jPanel1.add ( scroll , BorderLayout.CENTER );
//        //home.jPanel1.add ( bp1 );
//        jLabel6.setText ( "  Raw Material and Part code " );
//
//        home.revalidate ();
//        home.repaint ();
//        
//    }  else if ( showLogin () ) {
//
///*          
//        
//        ReportsController process = new ReportsController ("Raw Material Efficiency");
//        //RMEfficienctReport process = new RMEfficienctReport ( "Raw Material Efficiency" );
//        scroll = new JScrollPane ( process ,
//                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
//                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
//
//        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
//        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
//        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
//        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
//        scroll.setBounds ( leftMargin - 15 , 5 , width - leftMargin , 580 );
//
//        scroll.setBackground ( Color.WHITE );
//        scroll.setBorder ( BorderFactory.createEmptyBorder () );
//
//        home.jPanel1.removeAll ();
//        home.jPanel1.setLayout ( null );
//        //home.jPanel1.add ( customer );
//        home.jPanel1.add ( scroll , BorderLayout.CENTER );
//        //home.jPanel1.add ( bp1 );
//        jLabel6.setText ( "  Raw Material Efficiency Report" );
//
//        home.revalidate ();
//        home.repaint ();
//    */     
//    }
//    }                                           


                                       

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
  //      bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

    if ( Proman_User.isActivelyLoggedIn () ) {

        ReportsController process = new ReportsController ("Raw Material Efficiency");
      //  RMEfficienctReport process = new RMEfficienctReport ( "Raw Material Efficiency" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 15 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Raw Material Efficiency Report" );

        home.revalidate ();
        home.repaint ();
        
    }  else if ( showLogin () ) {

        
/*          
        
        ReportsController process = new ReportsController ("Raw Material Efficiency");
        //RMEfficienctReport process = new RMEfficienctReport ( "Raw Material Efficiency" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 15 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Raw Material Efficiency Report" );

        home.revalidate ();
        home.repaint ();
    */     
    }
       
    
    }                                           

    private void jPanel1ComponentResized(java.awt.event.ComponentEvent evt) {                                         
        // TODO add your handling code here:

    }                                        

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        
           if ( Proman_User.isActivelyLoggedIn () ) {
                updateStockPanel();
                updateFGStockPanel();
             } else if ( showLogin () ) {
           //    updateStockPanel();
            }
        
    }                                          
    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt){
        if ( Proman_User.isActivelyLoggedIn () ) {

            QualityMaster process = new QualityMaster();// ( "PMPH" );
            //  RMEfficienctReport process = new RMEfficienctReport ( "Raw Material Efficiency" );
            scroll = new JScrollPane ( process ,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

            process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
            scroll.setBounds ( 0 , 0 , width-25 , 605 );
//            int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
//            process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
//            scroll.setBounds ( leftMargin - 15 , 5 , width - leftMargin , 580 );

            scroll.setBackground ( Color.WHITE );
            scroll.setBorder ( BorderFactory.createEmptyBorder () );

            home.jPanel1.removeAll ();
            home.jPanel1.setLayout ( null );
            //home.jPanel1.add ( customer );
            home.jPanel1.add ( scroll , BorderLayout.CENTER );
            //home.jPanel1.add ( bp1 );
            jLabel6.setText ( "Quality Master" );

            home.revalidate ();
            home.repaint ();

        } else if ( showLogin () ) {
        }
    }

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        // TODO add your handling code here:
        if ( Proman_User.isActivelyLoggedIn () ) {

            ReportsController process = new ReportsController ( "PMPH" );
            //  RMEfficienctReport process = new RMEfficienctReport ( "Raw Material Efficiency" );
            scroll = new JScrollPane ( process ,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

            //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
            //scroll.setBounds ( 5 , 5 , width-25 , 605 );
            int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
            process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
            scroll.setBounds ( leftMargin - 15 , 5 , width - leftMargin , 580 );

            scroll.setBackground ( Color.WHITE );
            scroll.setBorder ( BorderFactory.createEmptyBorder () );

            home.jPanel1.removeAll ();
            home.jPanel1.setLayout ( null );
            //home.jPanel1.add ( customer );
            home.jPanel1.add ( scroll , BorderLayout.CENTER );
            //home.jPanel1.add ( bp1 );
            jLabel6.setText ( "  Per Man Per Hour" );

            home.revalidate ();
            home.repaint ();

        } else if ( showLogin () ) {
        }

    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void COPQActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        
          if ( Proman_User.isActivelyLoggedIn () ) {

            ReportsController process = new ReportsController ( "COPQ" );
            //  RMEfficienctReport process = new RMEfficienctReport ( "Raw Material Efficiency" );
            scroll = new JScrollPane ( process ,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

            //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
            //scroll.setBounds ( 5 , 5 , width-25 , 605 );
            int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
            process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
            scroll.setBounds ( leftMargin - 15 , 5 , width - leftMargin , 580 );

            scroll.setBackground ( Color.WHITE );
            scroll.setBorder ( BorderFactory.createEmptyBorder () );

            home.jPanel1.removeAll ();
            home.jPanel1.setLayout ( null );
            //home.jPanel1.add ( customer );
            home.jPanel1.add ( scroll , BorderLayout.CENTER );
            //home.jPanel1.add ( bp1 );
            jLabel6.setText ( "  Cost of Poor Quality" );

            home.revalidate ();
            home.repaint ();

        } else if ( showLogin () ) 
        {
        }
    }      
    
    
    
    private void loadDynamicWidgets(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        if ( Proman_User.isActivelyLoggedIn () ) {
            LoadDynamicDashboard ();
        } else if ( showLogin () ) {
            LoadDashboard ();
        }
    }       
    
    

    
    /**
     * @param args the command line arguments
     */
    public static void main ( String args[] ) throws InstantiationException, IllegalAccessException {

        java.awt.EventQueue.invokeLater ( new Runnable () {
            public void run () {

                
                home = new HomeScreen ();

                //jMenu2.setVisible ( false );

                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ();
                GraphicsDevice[] gs = ge.getScreenDevices ();
                for ( int i = 0 ; i < gs.length ; i ++ ) {
                    DisplayMode dm = gs[ i ].getDisplayMode ();
                    if ( width > dm.getWidth () || width == 0 ) {
                        width = dm.getWidth ();
                        height = dm.getHeight ();
                    }
                }
                int scrollPaneHeight = ( height - 25 );

                topPanel = new BGJPanelTop ();

                jLabel6.setBounds ( 30 , 5 , 400 , 30 );
                home.jPanel1.setBounds ( 10 , 65 , ( width - 20 ) , 615 );
                topPanel.setBounds ( 0 , 0 , width , 150 );
                home.jPanel2.setBounds ( 10 , 32 , 500 , 30 );
                home.add ( topPanel );

                home.jLabel6.setBounds ( 10 , 0 , 400 , 30 );

                home.setVisible ( true );
                home.setTitle ( "PROD-EZY V1.0" );

                home.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 14 ) ); // NOI18N;
                home.getContentPane ().setBackground ( Color.DARK_GRAY );

                initializeApplication ();

//////////////////////////////////  START OF FIREBASE CONNECTION CODE    /////////////////////////////////////////////////////////////////
//                try {
//                   
//                    File f = new File (  "prodezyapp-firebase-adminsdk-cefl5-5692dda161.json" );
//                    FileInputStream serviceAccount = new FileInputStream ( f );
//
//                    options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream ( serviceAccount ) ).setDatabaseUrl ( "https://prodezyapp.firebaseio.com/" ).build ();
//                    
//                    
//                    FirebaseApp.initializeApp (options);
//
//                    try {
//                        Thread.sleep ( 5000);
//                    } catch ( InterruptedException ex ) {
//                    }
//                    
//                   System.out.println ( "Connected to firebase database" );
//                   //JOptionPane.showMessageDialog(null, "Connected to firebase database");
//                } catch ( FileNotFoundException e ) {
//                    System.out.println ( "FileNotFoundException : " + e.getMessage () );
//                } catch ( IOException e ) {
//                    System.out.println ( "IOException : " + e.getMessage () );
//                } //catch ( URISyntaxException e ) {
//                  //  System.out.println ( "URISyntaxException : " + e.getMessage () );
//              }
//////////////////////////////////   END OF FIREBASE CONNECTION CODE    /////////////////////////////////////////////////////////////////
                

                jLabel2.setBounds(  560, 75, 800, 360    );
                jLabel8.setBounds(  80, 70, 400, 55    );
                jLabel7.setBounds(  0, 450, 1366, 150    );


                //   jLabel1.setBounds ( (int)((screenW)/2), 546, 500, 100);
                Thread t = new Thread () {
                    public void run () {
                        while ( true ) {
                            try {
                                c2 = Calendar.getInstance ();
                                sdf2 = new SimpleDateFormat ( "dd MMM yyyy -  HH : mm : ss a" );
                                String strDate2 = sdf2.format ( c2.getTime () );
                                Thread.sleep ( 1000 );
                                home.jLabel4.setText ( strDate2 );
                            } catch ( InterruptedException ex ) {
                                StaticValues.writer.writeExcel ( HomeScreen.class.getSimpleName () , HomeScreen.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 2 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                            }
                        }
                    }
                };

                t.start ();

                if ( gs.length >= 2 ) {
                    JOptionPane.showMessageDialog ( null , "Multiple Displayed Detected. Resize application for better viewing experience" );
                }

                for ( int i = 0 ; i < gs.length ; i ++ ) {
                    DisplayMode dm = gs[ i ].getDisplayMode ();
                    if ( width > dm.getWidth () || width == 0 ) {
                        width = dm.getWidth ();
                        height = dm.getHeight ();
                    }
                }

                
                //*****************change the look of application edited by mayur mahale***************************
                try{

                   for( javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()){
                    if("Windows".equals(info.getName()))
                    {
                         javax.swing.UIManager.setLookAndFeel(info.getClassName());

                         break;
                    }
                }

                }catch(ClassNotFoundException e)
                {
                    e.getMessage();
                }catch( UnsupportedLookAndFeelException e1)
                {e1.getMessage();} catch ( InstantiationException ex ) {
                    Logger.getLogger ( HomeScreen.class.getName() ).log ( Level.SEVERE , null , ex );
                } catch ( IllegalAccessException ex ) {
                    Logger.getLogger ( HomeScreen.class.getName() ).log ( Level.SEVERE , null , ex );
                }
              //*****************end change the look of application edited by mayur mahale***************************
                
            }

        } );
        
        //*****************change the look of application edited by mayur mahale***************************
        try{
            
           for( javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()){
            if("Windows".equals(info.getName()))
            {
                 javax.swing.UIManager.setLookAndFeel(info.getClassName());
                 
                 break;
            }
        }
            
        }catch(ClassNotFoundException e)
        {
            e.getMessage();
        }catch( UnsupportedLookAndFeelException e1)
        {e1.getMessage();}
      //*****************end change the look of application edited by mayur mahale***************************
        
    }


    
    public static Thread t2 = null;

    public static void checkAlarm () {

//        alarm = new AlarmsController() ;        
//        alarm.startAlarm ();
//        alarm.showAlarm ();        
        boolean[] alarmsStates = null;
        String[] alarmsNames = null;
        int[] alarmsFreq = null;

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
            System.out.println ( "checking alarms" );
        } catch ( SQLException sql1 ) {
            StaticValues.writer.writeExcel ( HomeScreen.class.getSimpleName () , HomeScreen.class.getSimpleName () , sql1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 2 ].getLineNumber () + "" , sql1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } finally {
            try {
                rs.close ();
            } catch ( SQLException sql2 ) {
                StaticValues.writer.writeExcel ( HomeScreen.class.getSimpleName () , HomeScreen.class.getSimpleName () , sql2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 2 ].getLineNumber () + "" , sql2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }

//        if ( alarmsStates[0] &&  alarmsNames[0].equals ( "PPM for the day" )) {
//            getTodaysTotalPPM ();
//            startAlarm = true ;
//        }
//        if ( alarmsStates[1] &&  alarmsNames[1].equals ( "Total production for the day"   )) {
//            getTodaysTotalProduction ();
//            startAlarm = true ;
//        }
//        if ( alarmsStates[2] &&  alarmsNames[2].equals (  "Raw Materials stock close to ROL" )) {
//            getRMatROL ();
//            startAlarm = true ;
//        }
//        if ( alarmsStates[3] &&  alarmsNames[3].equals ( "Rejections for the day"  )) {
//            getRejectionForProducts ();
//            startAlarm = true ;
//        }
        if ( startAlarm ) {
            if ( t2 != null &&  ! t2.isAlive () ) {
                t2.start ();
            } else {
                for ( int i = 0 ; i < alarmsList.size () ; i ++ ) {
                    try {
                        Thread.sleep ( 5000 );
                    } catch ( InterruptedException ex ) {
                        StaticValues.writer.writeExcel ( HomeScreen.class.getSimpleName () , HomeScreen.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 2 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }
                    //       jLabel1.setText ( alarmsList.get ( i ) );
                }
            }
        }
    }

    public void logout () {

        try {
            Connection _con = DriverManager.getConnection ( "jdbc:sqlite:SSDsdIadc_xlp.db" );

            Calendar c2 = Calendar.getInstance ();
            SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
            String strDate2 = sdf2.format ( c2.getTime () );

            String logSession = "insert into lilosessIONS (   'LITIME', 'LOTIME',  'UUID', 'ISACTIVE' ) values (   'NA',   '" + strDate2 + "', " + Proman_User.getEID () + ",  0) ";
            Statement st = _con.createStatement ();

            st.execute ( logSession );

            st.close ();

            Proman_User.setEID ( -1 );
            Proman_User.setUsername ( "Guest" );
            Proman_User.setIsActivelyLoggedIn ( false );
            Proman_User.setCanCreate ( false );
            Proman_User.setCanRead ( false );
            Proman_User.setCanEdit ( false );
            Proman_User.setCanDisable ( false );
            Proman_User.setCanImport ( false );
            Proman_User.setCanExport ( false );
            Proman_User.setDeptIds ( "-1" );
            Proman_User.setRole ( "" );

            jLabel3.setText ( "Welcome Guest " );

            jLabel5.setVisible ( false );

            jMenuBar1.setVisible ( false );
            jLabel6.setText("");
            
          //  jMenu2.setVisible ( false );
            removeForms ();
            
            alarmController.hideAlarm ( );
            
            loginF = new LoginForm();
            loginF.setBounds(  100, 200, 300, 300      );
            //jPanel1.removeAll();
            jPanel1.add( loginF ) ;
            

        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel ( HomeScreen.class.getSimpleName () , HomeScreen.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 2 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            JOptionPane.showMessageDialog ( null , "" + e.getMessage () );
        }
    }
    
    


    // Variables declaration - do not modify                     
    public static javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel2;
     public static javax.swing.JMenuItem hourscreen;
   public static javax.swing.JLabel jLabel3;
    public static javax.swing.JLabel jLabel4;
    public static javax.swing.JLabel jLabel5;
    public static javax.swing.JLabel jLabel6;
    public static javax.swing.JLabel jLabel7;
    public static javax.swing.JLabel jLabel8;
    public static javax.swing.JMenu jMenu1;
    public static javax.swing.JMenu jMenu10;
    public static javax.swing.JMenu jMenu11;
    public static javax.swing.JMenu jMenu12;
    public static javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu14;
    private javax.swing.JMenu jMenuItemQuality;
    private javax.swing.JMenu jMenuQualityReport;
    
    private javax.swing.JMenuItem RMFGcode1;
    private javax.swing.JMenuItem diefrm;
    private javax.swing.JMenuItem RMFGcode;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem COPQ;
    private javax.swing.JMenuItem othercopq;
    public static javax.swing.JMenu jMenu8;
    public static javax.swing.JMenu jMenu9;
    public static javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
     private javax.swing.JMenuItem jMenuItem22;
    public static javax.swing.JMenuItem jMenuItem13;
    public static javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    public static javax.swing.JMenuItem jMenuItem16;
    public static javax.swing.JMenuItem jMenuItem17;
    public static javax.swing.JMenuItem jMenuItem18;
   public static javax.swing.JMenuItem jMenuItem19;
    public static javax.swing.JMenuItem jMenuItem2;
    public static javax.swing.JMenuItem jMenuItem20;
   public static javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    public static javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem24;
    public static javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    public static javax.swing.JMenuItem jMenuItem35;
    public static javax.swing.JMenuItem jMenuItem36;
    public static javax.swing.JMenuItem jMenuItem37;
    public static javax.swing.JMenuItem jMenuItem38;
    private javax.swing.JMenuItem jMenuItem39;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem40;
    private javax.swing.JMenuItem jMenuItem41;
    private javax.swing.JMenuItem jMenuItem42;
    private javax.swing.JMenuItem jMenuItem43;
    private javax.swing.JMenuItem jMenuItem44;
    private javax.swing.JMenuItem jMenuItem45;
    private javax.swing.JMenuItem jMenuItem46;
    public static javax.swing.JMenuItem jMenuItem47;
    private javax.swing.JMenuItem jMenuItem48;
    private javax.swing.JMenuItem jMenuItem49;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem50;
    public static javax.swing.JMenuItem jMenuItem51;
    public static javax.swing.JMenuItem jMenuItem52;
    private javax.swing.JMenuItem jMenuItem53;
    private javax.swing.JMenuItem jMenuItem54;
    private javax.swing.JMenuItem jMenuItem55;
    public static javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem jMenuItemRM;
    private javax.swing.JMenuItem jMenuItemFG;
    private javax.swing.JMenuItem jMenuItemProc;
     private javax.swing.JMenuItem jMenuItemRMReport;
    private javax.swing.JMenuItem jMenuItemFGReport;
    private javax.swing.JMenuItem jMenuItemProcReport;
    private javax.swing.JMenuItem processQuotation;
    private javax.swing.JMenuItem quotation;
    private javax.swing.JMenuItem rm_quotation;
    
    private javax.swing.JMenuItem maintain_history ;
    public static javax.swing.JPanel jPanel1;
    public static javax.swing.JPanel jPanel2;
     private javax.swing.JMenuItem jMenuItem32;
        private javax.swing.JMenu jMenu2;
        private javax.swing.JMenuItem jMenuItem56;
    
    
    // End of variables declaration                   


   
    
    static SimpleDateFormat sdf2;

    static int[] values = new int[ 4 ];
    static String[] titles = new String[ 4 ];
    static Double ppm;

    public ButtonPanel bp1 = new ButtonPanel ();

    public static ArrayList<AlarmsEntity> alarmsList = new ArrayList<AlarmsEntity> ();

    public static boolean checkUserRolePermission () {
        boolean canAccessResource = false;
        return canAccessResource;
    }

}

/*
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jMenu3 = new javax.swing.JMenu();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new RoundPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new RoundPanel2 ();
        jLabel6 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem46 = new javax.swing.JMenuItem();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenuItem31 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenuItem52 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        RMFGcode = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenu14 = new javax.swing.JMenu();
        jMenuItem40 = new javax.swing.JMenuItem();
        jMenuItem41 = new javax.swing.JMenuItem();
        jMenuItem42 = new javax.swing.JMenuItem();
        jMenuItem43 = new javax.swing.JMenuItem();
        jMenuItem39 = new javax.swing.JMenuItem();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem36 = new javax.swing.JMenuItem();
        jMenuItem37 = new javax.swing.JMenuItem();
        jMenuItem38 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem51 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        jMenuItem44 = new javax.swing.JMenuItem();
        jMenuItem45 = new javax.swing.JMenuItem();
        jMenu15 = new javax.swing.JMenu();
        jMenuItem53 = new javax.swing.JMenuItem();
        jMenuItem54 = new javax.swing.JMenuItem();
        jMenuItem47 = new javax.swing.JMenuItem();
        jMenuItem48 = new javax.swing.JMenuItem();
        jMenuItem49 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenu12 = new javax.swing.JMenu();
        jMenuItem50 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenu13 = new javax.swing.JMenu();

        jMenu3.setText("jMenu3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1366, 768));
        getContentPane().setLayout(null);

        jLabel3.setBackground(new java.awt.Color(51, 153, 255));
        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setPreferredSize(new java.awt.Dimension(300, 30));
        getContentPane().add(jLabel3);
        jLabel3.setBounds(660, 0, 170, 30);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setPreferredSize(new java.awt.Dimension(300, 30));
        getContentPane().add(jLabel4);
        jLabel4.setBounds(830, 0, 230, 30);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(550, 30, 800, 25);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Utilities/assets/logout.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel5);
        jLabel5.setBounds(1080, 0, 32, 26);

    public static int width = 0, height = 0;

    public static DeptRolePerm user = null;

    private static boolean loginSessionActive = false;
    public GraphicsEnvironment ge = null;

    public static BGJPanelTop topPanel = null;

    // public static FirebaseOptions options = null;
    public static FirebaseOptions options = null;

    public static AlarmController3 alarmController = null ;
    public static Alarms alarm = null;
    //  public static  ThreadCommunication alarm = null ;
    
    public static LoginForm loginF = null ;
    

    
    public HomeScreen () {

        initComponents ();
        setExtendedState ( JFrame.MAXIMIZED_BOTH );

        jMenuItem14.setVisible ( false );
        jMenuItem18.setVisible ( false );
        jMenuItem35.setVisible ( false );
        jMenuItem36.setVisible ( false );
        jMenuItem37.setVisible ( false );
        jMenuItem47.setVisible ( false );
        jMenuItem30.setVisible ( false );
        jMenuItem10.setVisible ( false );
        jMenuItem13.setVisible ( false );
        jMenuItem9.setVisible(false);
        jMenu13.setVisible ( false );
        jMenu1.setVisible ( false );

            jMenuBar1.setVisible(false);
        
        
        setIconImage ( Toolkit.getDefaultToolkit ().getImage ( getClass ().getResource ( "img/prodezy32x32img.png" ) ) );
        //bp1.jButton1.addActionListener ( listemBPClose );
       
//        //System.out.println ( WebServices_Controller.getCategories ());
//        String response = WebServices_Controller.getToken ();
//        JSONObject responseObject = (JSONObject) new JSONTokener(response).nextValue() ;
//        System.out.println ( ""+responseObject.getJSONObject ( "data").getString("token") );
//        StaticValues.setHeader( responseObject.getJSONObject ( "data").getString("token") );
    }

   
    
    public static void initializeApplication () {
        
   //    Proman_User.setIsActivelyLoggedIn ( true );
    //   StaticValues.setHeader ( "eyJpdiI6IkxaMGZjS2ZFQ2tMZDJEc2N2RklQR0E9PSIsInZhbHVlIjoieDkzTXFHbGVPQVlVcHJWOHZoSXV3NjBrdlNtc1pBeExkbVEyajBhSHhNMVAwd0RVRkFjYkQxQWtJTVZNWjQxTVE5Z3hWVUljbkZBK0p2Y2xGQkc5VTZkXC9KXC9jYUpBTUhpZnk1QjNvaEI5UHpNQUhPdUVwT25acTU3UGV3bCtiMzdNVlwvc0xpZ0hUanRtOE8xOVpMdEtJcU5TdWJHXC9OWUlNZ2Z1a1VIaWlQK0RJVWJjT1dBYTZPY2Z6b1NRVlZLU3RUdElZQk5zNFE2SVpBbFdDRmlkdkdOTk40Y0psTURVZnZQZjlmQlI5Z2VhejlcLzJUSEgyT1FOcXBENW14MkNOIiwibWFjIjoiYTk0N2ZhYjE1YWJlZWZjNmZmNWE2MTJlYTJlYzkzOTQwMTc5OGYxZWVlOTUwNzUyYWU3NzAyODgzYjc0YzY4YSJ9");
       
//          try{ 
//            if( ! InetAddress.getLocalHost ().isReachable ( 500) ){
//               JOptionPane.showMessageDialog( null, "No Internet " );
//            }else{
//                JOptionPane.showMessageDialog( null, " Internet Available " );
//            }
//        }catch( IOException e ){
//             JOptionPane.showMessageDialog( null, "  "+e.getMessage () );
//        }     
//          create an error log file that will have enter all exception events in this file
//          File f = new File();

            jLabel5.setVisible ( false );

            PreparedStatement st = null;

            File f = new File ( "SSDsdIadc_xlp.db" );
            if (  ! f.exists () ) {

          //       try {
                    jMenuBar1.setVisible ( false );

                    appSettings process = new appSettings ();
                    Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
                    int screenW = ( int ) d.getWidth ();
                    int screenH = ( int ) d.getHeight ();
                    int paneW = 1300;

                    process.setBounds ( ( int ) ( screenW - paneW ) / 2 , 0 , paneW , 650 );

                    home.jPanel1.removeAll ();
                    home.jPanel1.setLayout ( null );
                    home.jPanel1.add ( process );
                    home.revalidate ();
                    home.repaint ();

                    home.setExtendedState ( JFrame.MAXIMIZED_BOTH );

                //   JOptionPane.showMessageDialog ( null, "Database  exsits. Configuring settings");
                
//                } catch ( SQLException e ) {
//                    JOptionPane.showMessageDialog ( null , "" + e.getMessage () );
//                    StaticValues.writer.writeExcel ( HomeScreen.class.getSimpleName () , HomeScreen.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 2 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//                }
            } else {

                //Copy the settings database file in secured folder for unanemous access
                File file = new File ( System.getProperty ( "user.home" ) + "\\AppData\\Local\\" + "Prodezy" );
                if (  ! file.exists () ) {
                    file.mkdir ();
                }
                file = new File ( System.getProperty ( "user.home" ) + "\\AppData\\Local\\Prodezy\\" + "databases" );
                if (  ! file.exists () ) {
                    file.mkdir ();
                }
                file = new File ( System.getProperty ( "user.home" ) + "\\AppData\\Local\\Prodezy\\" + "app_log.txt" );

                if (  ! file.exists () ) {
                    try {
                        file.createNewFile ();
                    } catch ( IOException ex ) {
                        StaticValues.writer.writeExcel ( HomeScreen.class.getSimpleName () , HomeScreen.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 2 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                        JOptionPane.showMessageDialog ( null , "Error !  Please contact your sevice provider" );
                    }
                }

                //Create a new excel file for recording logs of all errors in structured way
                StaticValues.createErrorLogExcel ();

                Path sourceDirectory = Paths.get ( "SSDsdIadc_xlp.db" );
                Path targetDirectory = Paths.get ( System.getProperty ( "user.home" ) + "\\AppData\\Local\\Prodezy\\" + "SSDsdIadc_xlp.db" );
                file = targetDirectory.toFile ();
                if (  ! file.exists () ) {
                    try {
                        Files.copy ( sourceDirectory , targetDirectory );
                    } catch ( IOException ex ) {
                        JOptionPane.showMessageDialog ( null , "Setting DB Copy Error " + ex.getMessage () );
                        StaticValues.writer.writeExcel ( HomeScreen.class.getSimpleName () , HomeScreen.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 2 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );

                    }
                }

                sourceDirectory = Paths.get ( StaticValues.dbName );
                targetDirectory = Paths.get ( System.getProperty ( "user.home" ) + "\\AppData\\Local\\Prodezy\\" + StaticValues.dbName );
                file = targetDirectory.toFile ();
                if (  ! file.exists () ) {
                    try {
                        Files.copy ( sourceDirectory , targetDirectory );

                    } catch ( IOException | NullPointerException ex ) {
                        JOptionPane.showMessageDialog ( null , "Data DB Copy Error " + ex.getMessage () );
                        StaticValues.writer.writeExcel ( HomeScreen.class.getSimpleName () , HomeScreen.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 2 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }
                } 
                
                try {

                        Connection _con = DriverManager.getConnection ( "jdbc:sqlite:" + System.getProperty ( "user.home" ) + "\\AppData\\Local\\Prodezy\\" + "SSDsdIadc_xlp.db" );

                        Statement st1 = _con.createStatement ();
                        // Check if the installation date is older than 15 days
                        ResultSet rs = st1.executeQuery ( "select * from appSSJ_22154sdl_dsk_021" );
                        DateFormat sdf = new SimpleDateFormat ( "MMM d, yyyy" );
                        SimpleDateFormat sdf2 = new SimpleDateFormat ( "MMM d, yyyy" );
                    
                        Date installationDate = sdf.parse ( rs.getString ( "tria" ) );
                        Date todaysDate = sdf.parse ( sdf2.format ( Calendar.getInstance ().getTime () ) );

                        String licenseeName = rs.getString ( "unus" );
                        String licenseKey = rs.getString ( "duo" );
                        String customerRepName = rs.getString ( "quinque" );
                        String vendorRepName = rs.getString ( "sx" );
                        String databaseName =  rs.getString ( "quattuor" ); 
                        String apiURL = rs.getString ( "svn" ) ;
                        StaticValues.setApiURL ( apiURL );
                        
                        System.out.println ( "Web API URL feteched from database and set as "+StaticValues.getApiURL () );

                        long _time = todaysDate.getTime () - installationDate.getTime ();
                        int _days = ( int ) ( ( ( _time / 1000 ) / 60 ) / 60 / 24 );
                        st1.close ();

//                        if ( _days > 15 ) {
//                            int choice = JOptionPane.showConfirmDialog ( null , "Dear customer, your trial period is expired. Please contact your service provider" , "Trial Period Is Expired" , JOptionPane.OK_OPTION );
//                            if ( choice == JOptionPane.OK_OPTION ) {
//                                jMenuBar1.setVisible ( false );
//                            } else if ( choice == JOptionPane.NO_OPTION ) {
//                                jMenuBar1.setVisible ( false );
//                            } else if ( choice == JOptionPane.CANCEL_OPTION ) {
//                                jMenuBar1.setVisible ( false );
//                            }
//
//                            appSettings process = new appSettings ( licenseeName , licenseKey , customerRepName , vendorRepName , databaseName);
//                            process.setBounds ( 5 , 5 , width , 605 );
//                            home.jPanel1.removeAll ();
//                            home.jPanel1.setLayout ( null );
//                            home.jPanel1.add ( process );
//                            home.revalidate ();
//                            home.repaint ();
//
//                        } else {
                             _con = DriverManager.getConnection ( "jdbc:sqlite:SSDsdIadc_xlp.db" );

                            String selectAllSettings = "select * from appSSJ_22154sdl_dsk_021";
                            st = _con.prepareStatement ( selectAllSettings );
                            rs = st.executeQuery ();
                            settingsDBName = rs.getString ( "quattuor" );
                            StaticValues.dbName = StaticValues.dbName + settingsDBName;
                            apiURL = rs.getString ( "svn" ) ;
                            StaticValues.setApiURL ( apiURL );
                            //   System.out.println ( "" + StaticValues.dbName );
                            //new DB_Synchronization ( StaticValues.dbName );

                            rs.close ();

                            
                            if (  ! isSessionActive () ) {
                                
                                
                                home.jLabel5.setVisible ( false );
                                home.jLabel3.setText ( "Welcome Guest !!" );
                            
                                loginF = new LoginForm();
                                loginF.setBounds(  100, 200, 300, 300      );
                                jLabel2.setBounds(  560, 75, 800, 360    );
                                jLabel8.setBounds(  80, 70, 400, 55    );
                                jLabel7.setBounds(  0, 450, 1366, 150    );
                                
                                // jPanel1.removeAll();
                                jPanel1.add( loginF ) ;
                            }
                            
                            
           //           Temporarily hinding the provision for dynamically showing and hiding alarm setting 
           //           Now on the alaems label shall always show 
           //           Change specfically made for DG Corporation
                            
//                            alarm = new Alarms ();
//                            ArrayList<AlarmData> alarmData = new ArrayList<AlarmData> ();
//                            alarmData = alarm.getAlarmState ();
//                            boolean showAlarm = false;
//                            for ( int i = 0 ; i < alarm.alarmsStates.length ; i ++ ) {
//                                if ( alarm.alarmsStates[ i ] ) {
//                                    showAlarm = true;
//                                }
//                            }
//                            AlarmController3 alarmController = new AlarmController3 ( showAlarm );
//                            if ( showAlarm ) {
//                                alarmController.startAlarm ( "Preparing notifications" , showAlarm , alarmData );
//                            } else {
//                                alarmController.hideAlarm ();
//                            }
//                            
                            
     
                    //    }
                    }catch(SQLException | ParseException e){
                            System.out.println ( ""+e.getMessage() );
//                                                            JOptionPane.showMessageDialog(null, "Some error in initializing application "+e.getMessage());

                            
                    }
                   
        }
    }

    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu3 = new javax.swing.JMenu();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new RoundPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new RoundPanel2 ();
        jLabel6 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem46 = new javax.swing.JMenuItem();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenuItem31 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenuItem52 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        RMFGcode1 = new javax.swing.JMenuItem();
        diefrm = new javax.swing.JMenuItem();
        processQuotation = new javax.swing.JMenuItem();
        rm_quotation = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenu14 = new javax.swing.JMenu();
        jMenuItem40 = new javax.swing.JMenuItem();
        jMenuItem41 = new javax.swing.JMenuItem();
        jMenuItem42 = new javax.swing.JMenuItem();
        jMenuItem43 = new javax.swing.JMenuItem();
        jMenuItem39 = new javax.swing.JMenuItem();
        hourscreen = new javax.swing.JMenuItem();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem36 = new javax.swing.JMenuItem();
        jMenuItem37 = new javax.swing.JMenuItem();
        jMenuItem38 = new javax.swing.JMenuItem();
        maintain_history = new javax.swing.JMenuItem();
        quotation = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem51 = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        jMenuItem44 = new javax.swing.JMenuItem();
        jMenuItem45 = new javax.swing.JMenuItem();
        jMenu15 = new javax.swing.JMenu();
        jMenuItem53 = new javax.swing.JMenuItem();
        jMenuItem54 = new javax.swing.JMenuItem();
        jMenuItem47 = new javax.swing.JMenuItem();
        jMenuItem48 = new javax.swing.JMenuItem();
        jMenuItem49 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        COPQ = new javax.swing.JMenuItem();
        jMenu12 = new javax.swing.JMenu();
        jMenuItem50 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem32 = new javax.swing.JMenuItem();
        jMenu13 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem56 = new javax.swing.JMenuItem();

        jMenu3.setText("jMenu3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1366, 768));
        getContentPane().setLayout(null);

        jLabel3.setBackground(new java.awt.Color(51, 153, 255));
        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setPreferredSize(new java.awt.Dimension(300, 30));
        getContentPane().add(jLabel3);
        jLabel3.setBounds(660, 0, 170, 30);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setPreferredSize(new java.awt.Dimension(300, 30));
        getContentPane().add(jLabel4);
        jLabel4.setBounds(830, 0, 230, 30);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(550, 30, 800, 25);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/trixware/erp/prodezydesktop/Utilities/assets/logout.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel5);
        jLabel5.setBounds(1080, 0, 32, 26);

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1310, 600));
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentResized(evt);
            }
        });
        jPanel1.setLayout(null);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/trixware/erp/prodezydesktop/Utilities/assets/shopfloorImage.png"))); // NOI18N
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setMaximumSize(new java.awt.Dimension(1325, 610));
        jLabel2.setMinimumSize(new java.awt.Dimension(1325, 610));
        jLabel2.setPreferredSize(new java.awt.Dimension(1325, 610));
        jPanel1.add(jLabel2);
        jLabel2.setBounds(560, 75, 800, 360);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/trixware/erp/prodezydesktop/Utilities/assets/titles_.png"))); // NOI18N
        jPanel1.add(jLabel7);
        jLabel7.setBounds(0, 450, 1366, 150);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/trixware/erp/prodezydesktop/Utilities/assets/AppTitle.png"))); // NOI18N
        jPanel1.add(jLabel8);
        jLabel8.setBounds(80, 65, 400, 55);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, -10, 1360, 600);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(null);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel2.add(jLabel6);
        jLabel6.setBounds(5, 5, 400, 30);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(40, 10, 500, 40);

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N

        jMenu8.setText("Masters");
        jMenu8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenu8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu8.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenu8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu8ActionPerformed(evt);
            }
        });

        jMenuItem46.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem46.setText("Plant Master");
        jMenuItem46.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem46ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem46);

        jMenuItem33.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem33.setText("Utilities");
        jMenuItem33.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem33ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem33);

        jMenuItem31.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem31.setText("Employee");
        jMenuItem31.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem31);

        jMenuItem13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem13.setText("User Roles Permissions");
        jMenuItem13.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem13);

        jMenuItem28.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem28.setText("Raw Material");
        jMenuItem28.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem28);

        jMenuItem29.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem29.setText("Finished Goods");
        jMenuItem29.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem29ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem29);

        jMenuItem27.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem27.setText("Machine");
        jMenuItem27.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem27);

        jMenuItem25.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem25.setText("Customer");
        jMenuItem25.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem25);

        jMenuItem26.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem26.setText("Supplier");
        jMenuItem26.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem26);

        jMenuItem30.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem30.setText("Testing");
        jMenuItem30.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem30);

        jMenuItem34.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem34.setText("Processes");
        jMenuItem34.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem34ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem34);

        jMenuItem52.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem52.setText("Process Map");
        jMenuItem52.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem52ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem52);

        jMenuItem2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem2.setText("Product Customer Map");
        jMenuItem2.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem2);

        jMenuItem9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem9.setText("Process-Employee Map");
        jMenuItem9.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem9);

        jMenuItem16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem16.setText("BOM Master");
        jMenuItem16.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem16);

        jMenuItem17.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem17.setText("Batch Master");
        jMenuItem17.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem17);

        RMFGcode1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        RMFGcode1.setText("RM and FG form");
        RMFGcode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RMFGcode1ActionPerformed(evt);
            }
        });
        jMenu8.add(RMFGcode1);

        diefrm.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        diefrm.setText("Die Master");
        diefrm.setMargin(new java.awt.Insets(3, 3, 3, 2));
        diefrm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diefrmActionPerformed(evt);
            }
        });
        jMenu8.add(diefrm);

        processQuotation.setText("Process Quotation");
        jMenu8.add(processQuotation);

        rm_quotation.setText("Raw Material Quotation");
        jMenu8.add(rm_quotation);

        jMenuBar1.add(jMenu8);

        jMenu9.setText("Entries");
        jMenu9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu9.setMargin(new java.awt.Insets(3, 3, 3, 2));

        jMenu14.setText("Production Entry");
        jMenu14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu14.setMargin(new java.awt.Insets(3, 3, 3, 2));

        jMenuItem40.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem40.setText("Product-wise");
        jMenuItem40.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem40ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem40);

        jMenuItem41.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem41.setText("Process-wise");
        jMenuItem41.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem41ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem41);

        jMenuItem42.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem42.setText("Machine-wise");
        jMenuItem42.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem42ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem42);

        jMenuItem43.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem43.setText("Employee-wise");
        jMenuItem43.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem43ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem43);

        jMenuItem39.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem39.setText("Date-wise");
        jMenuItem39.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem39ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem39);

        hourscreen.setText("Hour-Wise");
        hourscreen.setEnabled(false);
        hourscreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hourscreenActionPerformed(evt);
            }
        });
        jMenu14.add(hourscreen);

        jMenu9.add(jMenu14);

        jMenuItem35.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem35.setText("Purchase");
        jMenuItem35.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenu9.add(jMenuItem35);

        jMenuItem36.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem36.setText("GRN");
        jMenuItem36.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenu9.add(jMenuItem36);

        jMenuItem37.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem37.setText("GIN");
        jMenuItem37.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenu9.add(jMenuItem37);

        jMenuItem38.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem38.setText("Sales Order");
        jMenuItem38.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem38ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem38);

        maintain_history.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        maintain_history.setText("Maintenance History");
        maintain_history.setMargin(new java.awt.Insets(3, 3, 3, 2));
        maintain_history.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maintain_historyActionPerformed(evt);
            }
        });
        jMenu9.add(maintain_history);

        quotation.setText("Quotation");
        jMenu9.add(quotation);

        jMenuBar1.add(jMenu9);

        jMenu10.setText("Inventory");
        jMenu10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu10.setMargin(new java.awt.Insets(3, 3, 3, 2));

        jMenuItem51.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem51.setText("Update RM");
        jMenuItem51.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem51ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem51);

        jMenuBar1.add(jMenu10);

        jMenu11.setText("User Reports");
        jMenu11.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenu11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu11ActionPerformed(evt);
            }
        });

        jMenuItem44.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem44.setText("Production Qty");
        jMenuItem44.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem44ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem44);

        jMenuItem45.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem45.setText("Production Value");
        jMenuItem45.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem45ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem45);

        jMenu15.setText("WIP");
        jMenu15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu15.setMargin(new java.awt.Insets(3, 3, 3, 2));

        jMenuItem53.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem53.setText("WIP 1");
        jMenuItem53.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem53ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem53);

        jMenuItem54.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem54.setText("WIP 2");
        jMenuItem54.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem54ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem54);

        jMenu11.add(jMenu15);

        jMenuItem47.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem47.setText("Raw Material");
        jMenuItem47.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem47ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem47);

        jMenuItem48.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem48.setText("Productivity");
        jMenuItem48.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem48ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem48);

        jMenuItem49.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem49.setText("Rejection");
        jMenuItem49.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem49ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem49);

        jMenuItem6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem6.setText("PPM");
        jMenuItem6.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem6);

        jMenuItem7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem7.setText("Cost / Value");
        jMenuItem7.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem7);

        jMenuItem11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem11.setText("OEE");
        jMenuItem11.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem11);

        jMenuItem15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem15.setText("Defect Occurences");
        jMenuItem15.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem15);

        jMenuItem19.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem19.setText("Production to Sales Coverage");
        jMenuItem19.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem19);

        jMenuItem20.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem20.setText("Raw Material Efficiency");
        jMenuItem20.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem20);

        jMenuItem21.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem21.setText("PMPH");
        jMenuItem21.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem21);

        COPQ.setText("COPQ");
        COPQ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                COPQActionPerformed(evt);
            }
        });
        jMenu11.add(COPQ);

        jMenuBar1.add(jMenu11);

        jMenu12.setText("Tools");
        jMenu12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu12.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenu12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu12ActionPerformed(evt);
            }
        });

        jMenuItem50.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem50.setText("Widgets");
        jMenuItem50.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem50ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem50);

        jMenuItem1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem1.setText("Documents");
        jMenuItem1.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem1);

        jMenu1.setText("<html>Reference<br>Material</html>");
        jMenu1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu1.setMargin(new java.awt.Insets(3, 3, 3, 2));

        jMenuItem3.setText("User Manual");
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Troubleshooting");
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText("Other");
        jMenu1.add(jMenuItem5);

        jMenu12.add(jMenu1);

        jMenuItem10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem10.setText("Alarms");
        jMenuItem10.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem10);

        jMenuItem12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem12.setText("Setup");
        jMenuItem12.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem12);

        jMenuItem14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem14.setText("Import");
        jMenuItem14.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem14);

        jMenuItem18.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem18.setText("App Setting");
        jMenuItem18.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem18);

        jMenuItem32.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem32.setText("Dynamic Widgets");
        jMenu12.add(jMenuItem32);

        jMenuBar1.add(jMenu12);

        jMenu13.setText("MIS Reports");
        jMenu13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu13.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuBar1.add(jMenu13);

        jMenu2.setText("Planning");
        jMenu2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu2.setMargin(new java.awt.Insets(3, 3, 3, 2));

        jMenuItem56.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem56.setText("FirstActivity");
        jMenuItem56.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenu2.add(jMenuItem56);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        getAccessibleContext().setAccessibleName("homeFrame");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );

                try{    department = Integer.parseInt(  emp.get ( "department_id" ).toString () );   }catch(   NumberFormatException e   ){    department = 0 ;            }
                try{    role = Integer.parseInt(  emp.get ( "role_id" ).toString () );      }catch(  NumberFormatException e   ){  role = 0 ;     }

                Proman_User.setUsername ( emp.get ( "firstname" ).toString () );
            }

            return new String[]{ department+"", role+"" };
            
        }else{
           
            return new String[] {  "Failed : ", result2  } ;
        }
    }
    
    
    
    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem29ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {
            showFinishedGoodsScreen ();
        } else if ( showLogin () ) {
          //  showFinishedGoodsScreen ();
        }
    }//GEN-LAST:event_jMenuItem29ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showCustomerScreen ();
        } else if ( showLogin () ) {

        //    showCustomerScreen ();
        }
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showSupplierScreen ();
        } else if ( showLogin () ) {

        //    showSupplierScreen ();
        }
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {
            showMachineScreen ();
        } else if ( showLogin () ) {
           // showMachineScreen ();
        }


    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showRawMaterialScreen ();
        } else if ( showLogin () ) {

         //   showRawMaterialScreen ();
        }
    }//GEN-LAST:event_jMenuItem28ActionPerformed

    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem30ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showTestingScreen ();
        } else if ( showLogin () ) {

        //    showTestingScreen ();
        }
    }//GEN-LAST:event_jMenuItem30ActionPerformed

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem31ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showEmployeeScreen ();
        } else if ( showLogin () ) {

       //     showEmployeeScreen ();
        }
    }//GEN-LAST:event_jMenuItem31ActionPerformed

    private void jMenuItem33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem33ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showUtilitiesScreen ();
        } else if ( showLogin () ) {

       //     showUtilitiesScreen ();
        }
    }//GEN-LAST:event_jMenuItem33ActionPerformed

    private void jMenuItem34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem34ActionPerformed
        // TODO add your handling code here:
        if ( Proman_User.isActivelyLoggedIn () ) {

            showProcessMasterForm ()        ;
        } else if ( showLogin () ) {
        //    showProcessMasterForm ()        ;
        }
    
    }//GEN-LAST:event_jMenuItem34ActionPerformed

    private void jMenuItem51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem51ActionPerformed
        // TODO add your handling code here:

          if ( Proman_User.isActivelyLoggedIn () ) {
            updateStockPanel();
         } else if ( showLogin () ) {
       //    updateStockPanel();
        }
    }//GEN-LAST:event_jMenuItem51ActionPerformed

    private void jMenuItem38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem38ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {
            showSalesForm();
        }else if ( showLogin () ) {
   //         showSalesForm();
        }

    }//GEN-LAST:event_jMenuItem38ActionPerformed

    private void jMenuItem52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem52ActionPerformed
        // TODO add your handling code here:

      //  bp1 = new ButtonPanel ();
     //   bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

     if ( Proman_User.isActivelyLoggedIn () ) {
            
         Processforfinishedgoods process = new Processforfinishedgoods ();

        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Product-Process-Machine Map Master" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

    
	 } 
     
        

    }//GEN-LAST:event_jMenuItem52ActionPerformed

    private void jMenuItem40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem40ActionPerformed
        // TODO add your handling code here:
    //    bp1 = new ButtonPanel ();
    //    bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

    if ( Proman_User.isActivelyLoggedIn () ) {
            
        DailyReportForm2 process = new DailyReportForm2 ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Production Entry - Product wise" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

 
	 } 
    
        

    }//GEN-LAST:event_jMenuItem40ActionPerformed

    private void jMenuItem50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem50ActionPerformed
        // TODO add your handling code here:
        if ( Proman_User.isActivelyLoggedIn () ) {
            LoadDashboard ();
        } else if ( showLogin () ) {
            LoadDashboard ();
        }
    }//GEN-LAST:event_jMenuItem50ActionPerformed

    public static JScrollPane scroll = null;

    private void jMenuItem44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem44ActionPerformed
        // TODO add your handling code here:
        //  ProductionNWIP2 process = new ProductionNWIP2 ();
    //    bp1 = new ButtonPanel ();
    ///    bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

    if ( Proman_User.isActivelyLoggedIn () ) {
            
        ReportsController process = new ReportsController ( "Production Quantity" );

        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Production Quantity Report" );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {


	 } 
    
        

    }//GEN-LAST:event_jMenuItem44ActionPerformed

    private void jMenuItem53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem53ActionPerformed
        // TODO add your handling code here:
        //     ReportWIP1 process = new ReportWIP1();
   //     bp1 = new ButtonPanel ();
   //     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

   if ( Proman_User.isActivelyLoggedIn () ) {

       ReportsController process = new ReportsController ( "Work In Progress" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Work In Progress Report" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

 
	 } 
   
        

    }//GEN-LAST:event_jMenuItem53ActionPerformed

    private void jMenuItem54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem54ActionPerformed
        // TODO add your handling code here:
     //   bp1 = new ButtonPanel ();
    //    bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

    if ( Proman_User.isActivelyLoggedIn () ) {

         ReportWIP2 process = new ReportWIP2 ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );
        
            
        
        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Work In Progress Report" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

	 }
    
       

    }//GEN-LAST:event_jMenuItem54ActionPerformed

    private void jMenuItem49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem49ActionPerformed
        // TODO add your handling code here:
//        ReportRejection process = new ReportRejection();
    //    bp1 = new ButtonPanel ();
   //     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

   if ( Proman_User.isActivelyLoggedIn () ) {

        ReportsController process = new ReportsController ( "Rejection" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

//        process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
//        scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Rejection Report" );
        //      home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

	 }
   
   
       

    }//GEN-LAST:event_jMenuItem49ActionPerformed

    private void jMenuItem41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem41ActionPerformed
        // TODO add your handling code here:
  //      bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

                if ( Proman_User.isActivelyLoggedIn () ) {

                    DailyReportFormProcessWise process = new DailyReportFormProcessWise ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Entry - Process wise" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

	 }
  
        

    }//GEN-LAST:event_jMenuItem41ActionPerformed

    private void jMenuItem42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem42ActionPerformed
        // TODO add your handling code here:
    //    bp1 = new ButtonPanel ();
   //     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

   if ( Proman_User.isActivelyLoggedIn () ) {

       DailyReportFormMachineWise process = new DailyReportFormMachineWise ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Entry - Machine wise" );
        //     home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

 
	 }
   
   
        

    }//GEN-LAST:event_jMenuItem42ActionPerformed

    private void jMenuItem43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem43ActionPerformed
        // TODO add your handling code here:
    //    bp1 = new ButtonPanel ();
   ///     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

   if ( Proman_User.isActivelyLoggedIn () ) {

       DailyReportFormEmployeeWise process = new DailyReportFormEmployeeWise ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );

        jLabel6.setText ( "  Production Entry - Employee wise" );
        //     home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {

	 }
   
        

    }//GEN-LAST:event_jMenuItem43ActionPerformed

    private void jMenuItem46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem46ActionPerformed
        // TODO add your handling code here:
   //     bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

  if ( Proman_User.isActivelyLoggedIn () ) {
            
      PlantMaster2 process = new PlantMaster2 ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Plant Master" );
        //     home.jPanel1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {


	 }
  
        

    }//GEN-LAST:event_jMenuItem46ActionPerformed

    private void jMenuItem45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem45ActionPerformed
        // TODO add your handling code here:
        //      ReportProductionValue process = new ReportProductionValue ();
    //    bp1 = new ButtonPanel ();
    //    bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

    if ( Proman_User.isActivelyLoggedIn () ) {
            
        ReportsController process = new ReportsController ( "Production Value" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Value Report" );
        //       home.jPanel1.add ( jLabel6 );
        //      
        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {


	 }
    
        

    }//GEN-LAST:event_jMenuItem45ActionPerformed

    private void jMenuItem48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem48ActionPerformed
        // TODO add your handling code here:
//        ReportProductivity process = new ReportProductivity ();
        // OperatorEfficiencyReport effReport = new OperatorEfficiencyReport();
   //     bp1 = new ButtonPanel ();
   //     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );
   
   
   if ( Proman_User.isActivelyLoggedIn () ) {
            
       ReportsController process = new ReportsController ( "Productivity" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Productivity Report" );

        home.revalidate ();
        home.repaint ();

	 } else if ( showLogin () ) {


	 }
   
        

    }//GEN-LAST:event_jMenuItem48ActionPerformed

    private void jMenuItem39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem39ActionPerformed
        // TODO add your handling code here:
  //      bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );
       
  
  if ( Proman_User.isActivelyLoggedIn () ) {
            
      DailyReportForm process = new DailyReportForm ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        ////home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Production Data Entry" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();

	 } else if ( showLogin () ) {


	 }
  
  
  

    }//GEN-LAST:event_jMenuItem39ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        //DocumentManagement process = new DocumentManagement ();
   ///     bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

  
  if ( Proman_User.isActivelyLoggedIn () ) {

      DocumentRepository process = new DocumentRepository ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Documents Master" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();

	 } else if ( showLogin () ) {


	 }
  
        

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
 //       bp1 = new ButtonPanel ();
 //       bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        if ( Proman_User.isActivelyLoggedIn () ) {
            
            ProductCustomerMap process = new ProductCustomerMap ();
            // home.jPanel1.setBounds ( ((screenW-paneW)/2), 0, paneW, 650 );
            scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        ////home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Product-Customer Map Master" );
        //       home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();

	 } else if ( showLogin () ) {


	 }
 
        

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
//        PPM_Report ppmReport = new PPM_Report ();
  //      bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

  
  if ( Proman_User.isActivelyLoggedIn () ) {
            
      ReportsController process = new ReportsController ( "Total Rejection" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        ////home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Total Rejection Report" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();

	 } else if ( showLogin () ) {

/*               ReportsController process = new ReportsController ( "Total Rejection" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,

	 }
  
        

    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
    //    bp1 = new ButtonPanel ();
   //     bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

   if ( Proman_User.isActivelyLoggedIn () ) {
            
       //ReportCostValue ppmReport = new ReportCostValue ();
        ReportsController process = new ReportsController ( "Cost VS Value & PMPH" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Cost VS Value & PMPH Report" );
//         home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();

	 } else if ( showLogin () ) {


	 }
   
   
        


    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:

   //     bp1 = new ButtonPanel ();
  //      bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        if ( Proman_User.isActivelyLoggedIn () ) {
            
            EmployeeProcessMap process = new EmployeeProcessMap ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

//        process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
//        scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Employee-Process Map Master" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();

	 } else if ( showLogin () ) {

 
	 }
  
  
        

    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenu12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu12ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {
            rmcodepart();
        } else if ( showLogin () ) {
           // showMachineScreen ();
        }
    }//GEN-LAST:event_jMenu12ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
     //   bp1 = new ButtonPanel ();
    //    bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        if ( Proman_User.isActivelyLoggedIn () ) {
            
            Alarms process = new Alarms ();
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
        //scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 10 , 5 , process.getPreferredSize ().width + 10 , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Alarm Settings" );
        home.revalidate ();
        home.repaint ();

        } else if ( showLogin () ) {


        }
    
        

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {                                            
    // TODO add your handling code here:
    }                                           


                                               

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
//         TODO add your handling code here:
    //    bp1 = new ButtonPanel ();
    //    bp1.setBounds ( -2 , 555, width+2 , bp1.getPreferredSize ().height );

        if ( Proman_User.isActivelyLoggedIn () ) {
            
            ReportsController process = new ReportsController ( "OEE" );
        scroll = new JScrollPane ( process ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

//        process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
//    scroll.setBounds ( 5 , 5 , width-25 , 605 );
        int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
        process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
        scroll.setBounds ( leftMargin - 20 , 5 , width - leftMargin , 580 );

        scroll.setBackground ( Color.WHITE );
        scroll.setBorder ( BorderFactory.createEmptyBorder () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        //home.jPanel1.add ( customer );
        home.jPanel1.add ( scroll , BorderLayout.CENTER );
        //home.jPanel1.add ( bp1 );
        jLabel6.setText ( "  Overall Equippment Efficiency Report" );
        //        home.jPanel1.add ( jLabel6 );

        home.revalidate ();
        home.repaint ();
        //home.pack ();

	 } else if ( showLogin () ) {

 
	 }
    
    
        


    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        
    
        

    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed


     
    
        

    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
 
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jLabel5MouseClicked

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        
  
        
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
 
 }
  
        
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenu8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu8ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jMenu8ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
   
 
        
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed

    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
     
 }
   
       

    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenu11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu11ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
   

    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jPanel1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1ComponentResized
        // TODO add your handling code here:

    }//GEN-LAST:event_jPanel1ComponentResized

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
    
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void maintain_historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maintain_historyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maintain_historyActionPerformed

    private void COPQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_COPQActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_COPQActionPerformed

    private void RMFGcode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RMFGcode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RMFGcode1ActionPerformed

    private void diefrmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diefrmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_diefrmActionPerformed

    private void hourscreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hourscreenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hourscreenActionPerformed

    private void jMenuItem47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem47ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem47ActionPerformed

    public static void main ( String args[] ) {

     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem COPQ;
    private javax.swing.JMenuItem RMFGcode1;
    private javax.swing.JMenuItem diefrm;
    private javax.swing.JMenuItem hourscreen;
    public static javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public static javax.swing.JLabel jLabel5;
    public static javax.swing.JLabel jLabel6;
    public static javax.swing.JLabel jLabel7;
    public static javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    public static javax.swing.JMenu jMenu10;
    public static javax.swing.JMenu jMenu11;
    public static javax.swing.JMenu jMenu12;
    public static javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu14;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    public static javax.swing.JMenu jMenu8;
    public static javax.swing.JMenu jMenu9;
    public static javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    public static javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem30;
    public static javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem32;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem36;
    private javax.swing.JMenuItem jMenuItem37;
    public static javax.swing.JMenuItem jMenuItem38;
    private javax.swing.JMenuItem jMenuItem39;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem40;
    private javax.swing.JMenuItem jMenuItem41;
    private javax.swing.JMenuItem jMenuItem42;
    private javax.swing.JMenuItem jMenuItem43;
    private javax.swing.JMenuItem jMenuItem44;
    private javax.swing.JMenuItem jMenuItem45;
    private javax.swing.JMenuItem jMenuItem46;
    private javax.swing.JMenuItem jMenuItem47;
    private javax.swing.JMenuItem jMenuItem48;
    private javax.swing.JMenuItem jMenuItem49;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem50;
    private javax.swing.JMenuItem jMenuItem51;
    private javax.swing.JMenuItem jMenuItem52;
    private javax.swing.JMenuItem jMenuItem53;
    private javax.swing.JMenuItem jMenuItem54;
    private javax.swing.JMenuItem jMenuItem56;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem9;
    public static javax.swing.JPanel jPanel1;
    public static javax.swing.JPanel jPanel2;
    private javax.swing.JMenuItem maintain_history;
    private javax.swing.JMenuItem processQuotation;
    private javax.swing.JMenuItem quotation;
    private javax.swing.JMenuItem rm_quotation;
    // End of variables declaration//GEN-END:variables

    static Calendar c2;
    static SimpleDateFormat sdf2;

    static int[] values = new int[ 4 ];
    static String[] titles = new String[ 4 ];
    static Double ppm;

    public ButtonPanel bp1 = new ButtonPanel ();

    public static ArrayList<AlarmsEntity> alarmsList = new ArrayList<AlarmsEntity> ();

        scroll.setBackground ( Color.WHITE );
    }
}

    */
