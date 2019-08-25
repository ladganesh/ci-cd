/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

import trixware.erp.prodezydesktop.Utilities.Alarms;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.ImportMasters;
import trixware.erp.prodezydesktop.Utilities.LoginDialog;
import trixware.erp.prodezydesktop.masters.Calibration;
import trixware.erp.prodezydesktop.masters.CustomerMaster;
import trixware.erp.prodezydesktop.examples.DailyReportForm;
import trixware.erp.prodezydesktop.examples.DailyReportForm2;
import trixware.erp.prodezydesktop.examples.DailyReportFormEmployeeWise;
import trixware.erp.prodezydesktop.examples.DailyReportFormMachineWise;
import trixware.erp.prodezydesktop.examples.DailyReportFormProcessWise;
import trixware.erp.prodezydesktop.examples.DocumentRepository;
import trixware.erp.prodezydesktop.masters.EmployeeProcessMap;
import trixware.erp.prodezydesktop.masters.Employee_HR_Master;
import trixware.erp.prodezydesktop.masters.FinishedGoodMaster;
import trixware.erp.prodezydesktop.examples.HomeScreen;
import trixware.erp.prodezydesktop.examples.InventoryWidget1;
import trixware.erp.prodezydesktop.masters.MachineMaster;
import trixware.erp.prodezydesktop.masters.PlantMaster2;
import trixware.erp.prodezydesktop.masters.Process_Master_Form;
import trixware.erp.prodezydesktop.masters.Raw_Material;
import trixware.erp.prodezydesktop.masters.SupplierMaster;
import trixware.erp.prodezydesktop.masters.TestingMaster;
import trixware.erp.prodezydesktop.masters.UtilitiyMasters;
import trixware.erp.prodezydesktop.examples.appSettings;
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
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import trixware.erp.prodezydesktop.masters.Processforfinishedgoods;
import trixware.erp.prodezydesktop.masters.ProductCustomerMap;
import trixware.erp.prodezydesktop.examples.ReportWIP2;
import trixware.erp.prodezydesktop.examples.SalesForm;
import trixware.erp.prodezydesktop.examples.SetupForm;
import trixware.erp.prodezydesktop.examples.UpdateStockPanel;
import trixware.erp.prodezydesktop.examples.reports.ReportsController_V09;
import trixware.erp.prodezydesktop.examples.reports.ReportsController2;
import trixware.erp.prodezydesktop.examples.reports.UserRolePermissionMaster;
/**
 *
 * @author Rajesh
 */
public class Test extends javax.swing.JFrame {

    public LoginDialog loginDlg = new LoginDialog ( Test.this );
    JFrame frame = null;
    public static String settingsDBName;

    public static int width = 0, height = 0;

    public static DeptRolePerm user = null ;
    
    
    public static Test home = null ;
    /**
     * Creates new form Test
     */
    public Test () {
        initComponents ();
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
    }

    public static void initializeApplication () {

        try {

            PreparedStatement st = null;

            File f = new File ( "SSDsdIadc_xlp.db" );

            if (  ! f.exists () ) {

//                String create_appSettings_table = " create table appSSJ_22154sdl_dsk_021 ( 'unus' TEXT , 'duo' TEXT, 'tria' datetime, 'quattuor' TEXT , 'quinque' INTEGER, 'sx' TEXT , 'septem' TEXT )  " ;
//                Connection _con = DriverManager.getConnection( "jdbc:sqlite:SSDsdIadc_xlp" );
//                st = _con.prepareStatement(create_appSettings_table);
//                st.execute();
//            
               

                appSettings process = new appSettings ();
                Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
                int screenW = ( int ) d.getWidth ();
                int screenH = ( int ) d.getHeight ();
                int paneW = 1300;

                process.setBounds ( ( int ) ( screenW - paneW ) / 2 , 0 , paneW , 650 );

                
                home.revalidate ();
                home.repaint ();
               
               
               home.setExtendedState(JFrame.MAXIMIZED_BOTH); 
               

            } else {

                Connection _con = DriverManager.getConnection ( "jdbc:sqlite:SSDsdIadc_xlp.db" );
                String selectAllSettings = "select * from appSSJ_22154sdl_dsk_021";
                st = _con.prepareStatement ( selectAllSettings );
                ResultSet rs = st.executeQuery ();
                settingsDBName = rs.getString ( "quattuor" );
                StaticValues.dbName = StaticValues.dbName + settingsDBName;
             //   System.out.println ( "" + StaticValues.dbName );
             
                rs.close();
                
            }

        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog ( null , "" + e.getMessage () );
        }

    }
   
    
    private void showCustomerScreen () {

        CustomerMaster customer = new CustomerMaster ();

        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();

        customer.setBounds ( ( screenW - 1150 ) / 2 , 0 , 1150 , 660 );
        customer.loadContent ();
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( customer );
        home.revalidate ();
        home.repaint ();
        

        //     JOptionPane.showMessageDialog(null, "Please enter valid text"  );
    }

    private void showSupplierScreen () {
//jComboBox2
        SupplierMaster supplier = new SupplierMaster ();

        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        supplier.setBounds ( ( screenW - 1150 ) / 2 , 0 , 1150 , 660 );
        supplier.loadContent ();
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( supplier );
        home.revalidate ();
        home.repaint ();
        
    }

    private void showEmployeeScreen () {

        Employee_HR_Master emplHRMaster = new Employee_HR_Master ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        emplHRMaster.setBounds ( ( screenW - 1250 ) / 2 , 0 , 1250 , 660 );
        emplHRMaster.loadContent ();
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( emplHRMaster );
        home.revalidate ();
        home.repaint ();
        
    }

    private void showMachineScreen () {

        MachineMaster machine = new MachineMaster ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        //machine.setBounds((screenW-1250)/2, 0, 1250, 660);
        machine.setBounds ( 0 , 0 , 1250 , 800 );
        machine.loadContent ();
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( machine );
        //home.pack ();
        home.revalidate ();
        home.repaint ();
        
    }

    private void showRawMaterialScreen () {

        Raw_Material rawM = new Raw_Material ();
        rawM.setBounds ( 0 , 0 , home.jPanel1.getWidth () , home.jPanel1.getHeight () );

        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int paneW = 900;

        System.out.println ( screenW + " " + paneW + " " );

        rawM.setBounds ( ( int ) ( ( screenW - paneW ) / 2 ) , 0 , paneW , 630 );

        rawM.loadContent ();
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( rawM );
        home.revalidate ();
        home.repaint ();
        

    }

    private void showUtilitiesScreen () {

        UtilitiyMasters utilities = new UtilitiyMasters ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        utilities.setBounds ( ( screenW - 1250 ) / 2 , 0 , 1250 , 660 );

        utilities.loadContent ( "grade" );
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( utilities );
        home.revalidate ();
        home.repaint ();
        
    }

    private void showTestingScreen () {

        TestingMaster testingM = new TestingMaster ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        testingM.setBounds ( ( screenW - 1250 ) / 2 , 0 , 1250 , 660 );
        testingM.loadContent ();
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( testingM );
        home.revalidate ();
        home.repaint ();
        
    }

    private void showCalibrationScreen () {

        Calibration calibration = new Calibration ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        // calibration.setBounds(0, 0, home.jPanel1.getWidth(), home.jPanel1.getHeight());
        calibration.setBounds ( ( screenW - 1250 ) / 2 , 0 , 1250 , 660 );
        calibration.loadContent ();
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( calibration );
        home.revalidate ();
        home.repaint ();
        
    }

    private void showFinishedGoodsScreen () {

        FinishedGoodMaster finishedGoods = new FinishedGoodMaster ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        finishedGoods.setBounds ( ( screenW - 1250 ) / 2 , 0 , 1250 , 660 );
        // finishedGoods.setBounds(0, 0, home.jPanel1.getWidth(), home.jPanel1.getHeight());

        finishedGoods.loadContent ();
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( finishedGoods );
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
    
    public static  boolean isSessionActive(){
        try{
            
            Connection _con = DriverManager.getConnection ( "jdbc:sqlite:SSDsdIadc_xlp.db" );
            Calendar c2 = Calendar.getInstance ();
            SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
            String strDate2 = sdf2.format ( c2.getTime () );

            String checkActiveSession = "select MAX(LID) as lid, UUID,  ISACTIVE, DEPARTMENT, ROLE from lilosessIONS" ;
            Statement st = _con.createStatement ();
            ResultSet rs =  st.executeQuery (checkActiveSession );
            int lid, uid, isactive;
            String deptid ;
            String litime , role;
            
            lid = rs.getInt("lid");
            uid = rs.getInt("UUID");
            isactive = rs.getInt("ISACTIVE");
            deptid= rs.getString("DEPARTMENT");
            role = rs.getString("ROLE");
            
            rs.close();
            st.close();
            
            
            if( isactive==1 ){
                String logSession = "update lilosessIONS set   'LITIME' = '"+strDate2+"' where LID = "+lid  ;

                st = _con.createStatement ();
                st.execute ( logSession );
                
                String getUserName = "select EMP_NAME from employee where EmployeePK = "+uid;
                rs = DB_Operations.executeSingle ( getUserName) ;
                Proman_User.setUsername( rs.getString("EMP_NAME" ));    
                Proman_User.setEID(  uid  );
                Proman_User.setDeptIds ( deptid );
                        Proman_User.setRole ( role );
                
                

                Proman_User.setIsActivelyLoggedIn(true);

                HomeScreen.home.jLabel3.setText("Welcome "+Proman_User.getUsername()+" ");
                HomeScreen.home.LoadDashboard();
                
                return true ;
            }
        }catch(SQLException e){
            return false ;
        }
                            
        return false ;
    }
   
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();
        jMenuItem31 = new javax.swing.JMenuItem();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenuItem52 = new javax.swing.JMenuItem();
        jMenuItem46 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
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
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
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
        jMenu13 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 213, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        jMenuBar1.setBackground(new java.awt.Color(51, 153, 255));
        jMenuBar1.setForeground(new java.awt.Color(51, 153, 255));
        jMenuBar1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N

        jMenu8.setText("Masters");
        jMenu8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu8.setMargin(new java.awt.Insets(3, 3, 3, 2));

        jMenuItem33.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem33.setText("Utilities");
        jMenuItem33.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem33ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem33);

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

        jMenuItem30.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem30.setText("Testing");
        jMenuItem30.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem30);

        jMenuItem31.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem31.setText("Employee");
        jMenuItem31.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem31);

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

        jMenuItem46.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem46.setText("Plant Master");
        jMenuItem46.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem46ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem46);

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

        jMenuItem13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem13.setText("User Roles Permissions");
        jMenuItem13.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem13);

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
        jMenuItem51.setText("Update");
        jMenuItem51.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem51ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem51);

        jMenuBar1.add(jMenu10);

        jMenu11.setText("User Reports");
        jMenu11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu11.setMargin(new java.awt.Insets(3, 3, 3, 2));

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

        jMenuItem8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenuItem8.setText("Reports");
        jMenuItem8.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem8);

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

        jMenuBar1.add(jMenu12);

        jMenu13.setText("MIS Reports");
        jMenu13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jMenu13.setMargin(new java.awt.Insets(3, 3, 3, 2));
        jMenuBar1.add(jMenu13);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem33ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showUtilitiesScreen ();
        } else if ( showLogin () ) {

            showUtilitiesScreen ();
        }
    }//GEN-LAST:event_jMenuItem33ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showMachineScreen ();
        } else if ( showLogin () ) {

            showMachineScreen ();
        }
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showCustomerScreen ();
        } else if ( showLogin () ) {

            showCustomerScreen ();
        }
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showSupplierScreen ();
        } else if ( showLogin () ) {

            showSupplierScreen ();
        }
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showRawMaterialScreen ();
        } else if ( showLogin () ) {

            showRawMaterialScreen ();
        }
    }//GEN-LAST:event_jMenuItem28ActionPerformed

    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem29ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showFinishedGoodsScreen ();
        } else if ( showLogin () ) {

            showFinishedGoodsScreen ();
        }
    }//GEN-LAST:event_jMenuItem29ActionPerformed

    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem30ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showTestingScreen ();
        } else if ( showLogin () ) {

            showTestingScreen ();
        }
    }//GEN-LAST:event_jMenuItem30ActionPerformed

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem31ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showEmployeeScreen ();
        } else if ( showLogin () ) {

            showEmployeeScreen ();
        }
    }//GEN-LAST:event_jMenuItem31ActionPerformed

    private void jMenuItem34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem34ActionPerformed
        // TODO add your handling code here:
        Process_Master_Form process = new Process_Master_Form ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int paneW = 830;

        process.setBounds ( ( int ) ( ( screenW - paneW ) / 2 ) , 20 , paneW , 460 );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( process );
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem34ActionPerformed

    private void jMenuItem52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem52ActionPerformed
        // TODO add your handling code here:
        Processforfinishedgoods process = new Processforfinishedgoods ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int paneW = 970;

        process.setBounds ( ( int ) ( ( screenW - paneW ) / 2 ) , 20 , paneW , 650 );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( process );
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem52ActionPerformed

    private void jMenuItem46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem46ActionPerformed
        // TODO add your handling code here:
        PlantMaster2 plantMaster = new PlantMaster2 ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1366;
        // 854, 613

        plantMaster.setBounds ( ( ( screenW - paneW ) / 2 ) , 0 , paneW , 652 );
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( plantMaster );
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem46ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        ProductCustomerMap process = new ProductCustomerMap ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1250;

        // home.jPanel1.setBounds ( ((screenW-paneW)/2), 0, paneW, 650 );
        process.setBounds ( ( ( screenW - paneW ) / 2 ) , 0 , paneW , 650 );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( process );
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:

        EmployeeProcessMap process = new EmployeeProcessMap ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1250;

        process.setBounds ( ( ( screenW - paneW ) / 2 ) , 0 , paneW , 650 );

        //  process.setBounds( ((screenW-jPanel1.getWidth ())/2), 0, jPanel1.getWidth(), jPanel1.getHeight ());
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( process );
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        UserRolePermissionMaster ppmReport = new UserRolePermissionMaster ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        ppmReport.setBounds ( ( ( screenW - paneW ) / 2 ) , 0 , paneW , 650 );

        // ppmReport.setBounds( ((screenW-jPanel1.getWidth ())/2), 0, jPanel1.getWidth(), jPanel1.getHeight ());
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( ppmReport );
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem40ActionPerformed
        // TODO add your handling code here:
        DailyReportForm2 process = new DailyReportForm2 ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        process.setBounds ( ( int ) ( ( screenW - paneW ) / 2 ) , 0 , paneW , 650 );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( process );
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem40ActionPerformed

    private void jMenuItem41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem41ActionPerformed
        // TODO add your handling code here:
        DailyReportFormProcessWise process = new DailyReportFormProcessWise ();
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

    }//GEN-LAST:event_jMenuItem41ActionPerformed

    private void jMenuItem42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem42ActionPerformed
        // TODO add your handling code here:
        DailyReportFormMachineWise process = new DailyReportFormMachineWise ();
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

    }//GEN-LAST:event_jMenuItem42ActionPerformed

    private void jMenuItem43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem43ActionPerformed
        // TODO add your handling code here:
        DailyReportFormEmployeeWise process = new DailyReportFormEmployeeWise ();
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

    }//GEN-LAST:event_jMenuItem43ActionPerformed

    private void jMenuItem39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem39ActionPerformed
        // TODO add your handling code here:
        DailyReportForm process = new DailyReportForm ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1250;

        process.setBounds ( ( ( screenW - paneW ) / 2 ) , 20 , paneW , 650 );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( process );
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem39ActionPerformed

    private void jMenuItem38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem38ActionPerformed
        // TODO add your handling code here:

        SalesForm salesForm = new SalesForm ();
        salesForm.setBounds ( 0 , 0 , home.jPanel1.getWidth () , home.jPanel1.getHeight () );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( salesForm );
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem38ActionPerformed

    private void jMenuItem51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem51ActionPerformed
        // TODO add your handling code here:

        UpdateStockPanel updateStockRM = new UpdateStockPanel ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int paneW = 550;

        System.out.println ( screenW + " " + paneW + " " );

        updateStockRM.setBounds ( ( int ) ( ( screenW - paneW ) / 2 ) , 20 , paneW , paneW );

        updateStockRM.loadContent ();
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( updateStockRM );
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem51ActionPerformed

    private void jMenuItem44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem44ActionPerformed
        // TODO add your handling code here:
        //  ProductionNWIP2 process = new ProductionNWIP2 ();
        ReportsController_V09 ppmReport = new ReportsController_V09 ( "Production Quantity" );
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        ppmReport.setBounds ( ( int ) ( ( screenW - paneW ) / 2 ) , 0 , paneW , 650 );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( ppmReport );
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem44ActionPerformed

    private void jMenuItem45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem45ActionPerformed
        // TODO add your handling code here:
        //      ReportProductionValue process = new ReportProductionValue ();
        ReportsController_V09 process = new ReportsController_V09 ( "Production Value" );
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        process.setBounds ( ( int ) ( screenW - paneW ) / 2 , 0 , paneW , 670 );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( process );
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem45ActionPerformed

    private void jMenuItem53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem53ActionPerformed
        // TODO add your handling code here:
        //     ReportWIP1 process = new ReportWIP1();
        ReportsController_V09 process = new ReportsController_V09 ( "Work In Progress" );
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

    }//GEN-LAST:event_jMenuItem53ActionPerformed

    private void jMenuItem54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem54ActionPerformed
        // TODO add your handling code here:
        ReportWIP2 process = new ReportWIP2 ();
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

    }//GEN-LAST:event_jMenuItem54ActionPerformed

    private void jMenuItem48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem48ActionPerformed
        // TODO add your handling code here:
        //        ReportProductivity process = new ReportProductivity ();
        // OperatorEfficiencyReport effReport = new OperatorEfficiencyReport();
        ReportsController_V09 process = new ReportsController_V09 ( "Productivity" );
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

    }//GEN-LAST:event_jMenuItem48ActionPerformed

    private void jMenuItem49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem49ActionPerformed
        // TODO add your handling code here:
        //        ReportRejection process = new ReportRejection();
        ReportsController_V09 process = new ReportsController_V09 ( "Rejection" );
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

    }//GEN-LAST:event_jMenuItem49ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        //        PPM_Report ppmReport = new PPM_Report ();
        ReportsController_V09 ppmReport = new ReportsController_V09 ( "Total Rejection" );
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        ppmReport.setBounds ( ( ( screenW - paneW ) / 2 ) , 0 , paneW , 650 );

        // ppmReport.setBounds( ((screenW-jPanel1.getWidth ())/2), 0, jPanel1.getWidth(), jPanel1.getHeight ());
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( ppmReport );
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        //ReportCostValue ppmReport = new ReportCostValue ();
        ReportsController_V09 ppmReport = new ReportsController_V09 ( "Cost VS Value & PMPH" );
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        ppmReport.setBounds ( ( ( screenW - paneW ) / 2 ) , 0 , paneW , 650 );

        // ppmReport.setBounds( ((screenW-jPanel1.getWidth ())/2), 0, jPanel1.getWidth(), jPanel1.getHeight ());
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( ppmReport );
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        //ReportsController ppmReport = new ReportsController_V09 ( "Production Quantity"  );
        //ReportsController ppmReport = new ReportsController_V09 ( "Production Value"  );
        //ReportsController ppmReport = new ReportsController_V09 ( "Work In Progress" );
        //ReportsController ppmReport = new ReportsController_V09 ( "Rejection" );
        //ReportsController ppmReport = new ReportsController_V09 ( "Productivity" );
        //ReportsController ppmReport = new ReportsController_V09 ( "Parts Per Million" );
        //ReportsController ppmReport = new ReportsController_V09 ("Cost VS Value & PMPH" );

        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        //  ppmReport.setBounds ( ((screenW-paneW)/2), 0, paneW, 650 );
        // ppmReport.setBounds( ((screenW-jPanel1.getWidth ())/2), 0, jPanel1.getWidth(), jPanel1.getHeight ());
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        // home.jPanel1.add(ppmReport);
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:

        ReportsController_V09 ppmReport = new ReportsController_V09 ( "OEE" );
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        ppmReport.setBounds ( ( ( screenW - paneW ) / 2 ) , 0 , paneW , 650 );

        // ppmReport.setBounds( ((screenW-jPanel1.getWidth ())/2), 0, jPanel1.getWidth(), jPanel1.getHeight ());
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( ppmReport );
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        ReportsController2 ppmReport = new ReportsController2 ( );
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        ppmReport.setBounds ( ( ( screenW - paneW ) / 2 ) , 0 , paneW , 650 );

        // ppmReport.setBounds( ((screenW-jPanel1.getWidth ())/2), 0, jPanel1.getWidth(), jPanel1.getHeight ());
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( ppmReport );
        home.revalidate ();
        home.repaint ();
        // //home.pack ();
        //
        home.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem50ActionPerformed
        // TODO add your handling code here:
        if ( Proman_User.isActivelyLoggedIn () ) {
            LoadDashboard ();
        } else if ( showLogin () ) {
            LoadDashboard ();
        }
    }//GEN-LAST:event_jMenuItem50ActionPerformed

     public static void LoadDashboard () {
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.revalidate ();
        home.repaint ();

        InventoryWidget1 ant = new InventoryWidget1 ();
        ant.setBounds ( 0 , 0 , home.jPanel1.getWidth () , home.jPanel1.getHeight () );
        ant.loadContent ();
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( ant );
     
        home.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    }
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        //DocumentManagement process = new DocumentManagement ();
        DocumentRepository process = new DocumentRepository ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1030;

        process.setBounds ( ( ( screenW - paneW ) / 2 ) , 20 , paneW , 550 );

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( process );
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:

        Alarms process = new Alarms ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1250;

        process.setBounds ( ( ( screenW - paneW ) / 2 ) , 0 , paneW , 650 );
        //process.setBounds( ((screenW-jPanel1.getWidth ())/2), 0, jPanel1.getWidth(), jPanel1.getHeight ());

        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( process );
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:

        SetupForm ppmReport = new SetupForm ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        ppmReport.setBounds ( ( ( screenW - paneW ) / 2 ) , 0 , paneW , 650 );

        // ppmReport.setBounds( ((screenW-jPanel1.getWidth ())/2), 0, jPanel1.getWidth(), jPanel1.getHeight ());
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( ppmReport );
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:

        ImportMasters process = new ImportMasters ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1250;

        process.setBounds ( ( ( screenW - paneW ) / 2 ) , 0 , paneW , 650 );

        //  process.setBounds( ((screenW-jPanel1.getWidth ())/2), 0, jPanel1.getWidth(), jPanel1.getHeight ());
        home.jPanel1.removeAll ();
        home.jPanel1.setLayout ( null );
        home.jPanel1.add ( process );
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenu12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu12ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jMenu12ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main ( String args[] ) {   
        
        java.awt.EventQueue.invokeLater ( new Runnable () {
            public void run () {
                new Test ().setVisible ( true );
                
                initializeApplication ();

                home.setVisible ( true );
                home.setTitle ( "PROD-EZY (Demo Version)" );
                home.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 14 ) ); // NOI18N;
                home.getContentPane ().setBackground ( Color.DARK_GRAY );
                home.setExtendedState(JFrame.MAXIMIZED_BOTH); 
                
           

                Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
                int screenW = ( int ) d.getWidth ();
                

               

                

                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ();
                GraphicsDevice[] gs = ge.getScreenDevices ();
                
                if ( gs.length >= 2 ) {
                    JOptionPane.showMessageDialog ( null , "Multuple Displayed Detected. Resize application for better viewing experience" );
                }

                for ( int i = 0 ; i < gs.length ; i ++ ) {
                    DisplayMode dm = gs[ i ].getDisplayMode ();
                    if ( width > dm.getWidth () || width == 0 ) {
                        width = dm.getWidth ();
                        height = dm.getHeight ();
                    }
                    
                }
                
            }
        } );
    }

   
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu14;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private static javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem36;
    private javax.swing.JMenuItem jMenuItem37;
    private javax.swing.JMenuItem jMenuItem38;
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
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
