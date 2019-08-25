/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

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
import Utilities.ScrollPaneABC;
import java.awt.BorderLayout;
import java.sql.Statement;
import java.util.ArrayList;
import trixware.erp.prodezydesktop.Model.DeptRolePerm;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.Alarms;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.ImportMasters;
import trixware.erp.prodezydesktop.Utilities.LoginDialog;
import trixware.erp.prodezydesktop.examples.reports.RMEfficienctReport;
import trixware.erp.prodezydesktop.examples.reports.ReportsController2;
import trixware.erp.prodezydesktop.examples.reports.ReportsController3;
import trixware.erp.prodezydesktop.examples.reports.ReportsController_V09;
import trixware.erp.prodezydesktop.examples.reports.UserRolePermissionMaster;
import trixware.erp.prodezydesktop.masters.BatchProcessing;
import trixware.erp.prodezydesktop.masters.BillOfMaterialUI2;
import trixware.erp.prodezydesktop.masters.Calibration;
import trixware.erp.prodezydesktop.masters.CustomerMaster;
import trixware.erp.prodezydesktop.masters.EmployeeProcessMap;
import trixware.erp.prodezydesktop.masters.Employee_HR_Master;
import trixware.erp.prodezydesktop.masters.FinishedGoodMaster;
import trixware.erp.prodezydesktop.masters.MachineMaster;
import trixware.erp.prodezydesktop.masters.PlantMaster2;
import trixware.erp.prodezydesktop.masters.Process_Master_Form;
import trixware.erp.prodezydesktop.masters.Processforfinishedgoods;
import trixware.erp.prodezydesktop.masters.ProductCustomerMap;
import trixware.erp.prodezydesktop.masters.Raw_Material;
import trixware.erp.prodezydesktop.masters.SupplierMaster;
import trixware.erp.prodezydesktop.masters.TestingMaster;
import trixware.erp.prodezydesktop.masters.UtilitiyMasters;

/**
 *
 * @author Rajesh
 */
public class HomeScreen_old extends javax.swing.JFrame {

    //   static JPanel parentPanel;
    public static HomeScreen_old home;
    public LoginDialog loginDlg = new LoginDialog ( HomeScreen_old.this );
    JFrame frame = null;
    public static String settingsDBName;

    public static int width = 0, height = 0;

    public static DeptRolePerm user = null;

    private static boolean loginSessionActive = false;
    public GraphicsEnvironment ge = null;

    public static ScrollPaneABC jScrollPane1 = null ;
    
    /**
     * Creates new form HomeScreen
     */
    public HomeScreen_old () {

        initComponents ();
        setExtendedState ( JFrame.MAXIMIZED_BOTH );
       
        jScrollPane1 = new ScrollPaneABC();
        
    }

    public static void initializeApplication () {

        try {

            PreparedStatement st = null;

            File f = new File ( "SSDsdIadc_xlp.db" );

            if (  ! f.exists () ) {

                jMenuBar1.setVisible ( false );

                appSettings process = new appSettings ();
                Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
                int screenW = ( int ) d.getWidth ();
                int screenH = ( int ) d.getHeight ();
                int paneW = 1300;

                process.setBounds ( ( int ) ( screenW - paneW ) / 2 , 0 , paneW , 650 );

                home.jScrollPane1.removeAll ();
                home.jScrollPane1.setLayout ( null );
                home.jScrollPane1.add ( process );
                home.revalidate ();
                home.repaint ();

                home.setExtendedState ( JFrame.MAXIMIZED_BOTH );
                
             //   JOptionPane.showMessageDialog ( null, "Database  exsits. Configuring settings");

            } else {

                Connection _con = DriverManager.getConnection ( "jdbc:sqlite:SSDsdIadc_xlp.db" );
                String selectAllSettings = "select * from appSSJ_22154sdl_dsk_021";
                st = _con.prepareStatement ( selectAllSettings );
                ResultSet rs = st.executeQuery ();
                settingsDBName = rs.getString ( "quattuor" );
                StaticValues.dbName = StaticValues.dbName + settingsDBName;
                //   System.out.println ( "" + StaticValues.dbName );

                rs.close ();

             //   JOptionPane.showMessageDialog ( null, "Database not exist. Creating new Database");
            }

        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog ( null , "" + e.getMessage () );
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
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
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
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
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
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
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
        getContentPane().setLayout(null);

        jLabel3.setBackground(new java.awt.Color(51, 153, 255));
        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("jLabel3");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(660, 0, 170, 18);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("jLabel4");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(830, 0, 230, 18);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 0, 510, 25);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Utilities/assets/logout.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel5);
        jLabel5.setBounds(1080, 0, 32, 26);

        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(50, 0, 41, 16);

        jLabel6.setText("jLabel6");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(100, 0, 160, 20);

        jMenuBar1.setBackground(new java.awt.Color(51, 153, 255));
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

        setJMenuBar(jMenuBar1);

        getAccessibleContext().setAccessibleName("homeFrame");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void removeForms () {

        jMenuBar1.setVisible ( true );

        home.jScrollPane1.removeAll ();

        home.jScrollPane1.add ( jLabel2 );
        home.jScrollPane1.setLayout ( null );
        home.revalidate ();
        home.repaint ();

        System.gc ();
    }

    public static void LoadDashboard () {
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );

        InventoryWidget1 ant = new InventoryWidget1 ();
        ant.setBounds ( 28 , 52 , home.jScrollPane1.getWidth () , home.jScrollPane1.getHeight () );
        ant.loadContent ();
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( ant );

        //  home.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        home.revalidate ();
        home.repaint ();
    }

    private void showCustomerScreen () {

        CustomerMaster customer = new CustomerMaster ();

        customer.setBounds ( 28 , 52 , customer.getPreferredSize ().width , customer.getPreferredSize ().height );
        customer.loadContent ();
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( customer );
       
        jLabel6.setText ( "  Customer Master" );
        home.jScrollPane1.add ( jLabel6 );
         
        home.revalidate ();
        home.repaint ();

        //     JOptionPane.showMessageDialog(null, "Please enter valid text"  );
    }

    private void showSupplierScreen () {
//jComboBox2

        SupplierMaster supplier = new SupplierMaster ();
        supplier.setBounds (28 , 52 , supplier.getPreferredSize ().width , supplier.getPreferredSize ().height );

        supplier.loadContent ();
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( supplier );
        
         jLabel6.setText ( "  Supplier Master" );
        home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();

    }

    private void showEmployeeScreen () {

        Employee_HR_Master emplHRMaster = new Employee_HR_Master ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        emplHRMaster.setBounds ( 28 , 52 , 1310 , 600 );
        emplHRMaster.loadContent ();
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( emplHRMaster );
        
        jLabel6.setText ( "  Employee/User Master" );
        home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();

    }

    private void showMachineScreen () {

            MachineMaster machine = new MachineMaster ();
           
            machine.setBounds (  28 , 52 , 1310, 600 );
          
            machine.loadContent ();
            
            home.jScrollPane1.removeAll ();
            home.jScrollPane1.setLayout ( null );
            home.jScrollPane1.add ( machine );

            jLabel6.setText ( "  Machine Master" );
             home.jScrollPane1.add ( jLabel6 );
            home.revalidate ();
            home.repaint ();
      
    }

    private void showRawMaterialScreen () {

        Raw_Material rawM = new Raw_Material ();

       
        rawM.setBounds ( 28 , 52 , 1310, 600 );

        rawM.loadContent ();
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( rawM );
        
        jLabel6.setText ( "  Raw Material Master" );
         home.jScrollPane1.add ( jLabel6 );
         
        home.revalidate ();
        home.repaint ();

    }

    private void showUtilitiesScreen () {

        UtilitiyMasters utilities = new UtilitiyMasters ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        //utilities.setBounds ( ( screenW - 1250 ) / 2 , 0 , 1250 , 660 );
        utilities.setBounds ( 28 , 52 , utilities.getPreferredSize ().width , utilities.getPreferredSize ().height );

        utilities.loadContent ( "grade" );
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( utilities );
        jLabel6.setText ( "  Utilities Master" );
//        try {
//            Font f = Font.createFont(Font.TRUETYPE_FONT, new File( getClass().getResource("Utilities\\assets\\fonts\\ProximaNova-Regular.ttf").toURI ()) )   ; 
//            jLabel6.setFont ( f );
//        } catch ( FontFormatException ex ) {
//            System.out.println ( ex.getClass()+" : "+ex.getMessage () );
//        } catch ( IOException ex ) {
//            System.out.println ( ex.getClass()+" : "+ex.getMessage () );
//        }catch ( URISyntaxException ex ) {
//            System.out.println ( ex.getClass()+" : "+ex.getMessage () );
//        }

        home.jScrollPane1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();

    }

    private void showTestingScreen () {

        TestingMaster testingM = new TestingMaster ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        testingM.setBounds ( ( screenW - 1250 ) / 2 , 0 , 1250 , 660 );
        testingM.loadContent ();
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( testingM );
        
        jLabel6.setText ( "  Testing Master" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();

    }

    private void showCalibrationScreen () {

        Calibration calibration = new Calibration ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        // calibration.setBounds(0, 0, home.jScrollPane1.getWidth(), home.jScrollPane1.getHeight());
        calibration.setBounds ( ( screenW - 1250 ) / 2 , 0 , 1250 , 660 );
        calibration.loadContent ();
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( calibration );
        
        jLabel6.setText ( "  Calibration Master" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();

    }

    private void showFinishedGoodsScreen () {

        FinishedGoodMaster finishedGoods = new FinishedGoodMaster ();
        
        finishedGoods.setBounds ( 28 , 52 , 1310 , 600 );
     
        finishedGoods.loadContent ();
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( finishedGoods );
        
        jLabel6.setText ( "  Finished Goods Master" );
         home.jScrollPane1.add ( jLabel6 );
        
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

            String checkActiveSession = "select MAX(LID) as lid, UUID,  ISACTIVE, DEPARTMENT, ROLE from lilosessIONS";
            Statement st = _con.createStatement ();
            ResultSet rs = st.executeQuery ( checkActiveSession );
            int lid, uid, isactive;
            String litime, role, deptid;

            lid = rs.getInt ( "lid" );
            uid = rs.getInt ( "UUID" );
            isactive = rs.getInt ( "ISACTIVE" );
            deptid = rs.getString ( "DEPARTMENT" );
            role = rs.getString ( "ROLE" );

            rs.close ();
            st.close ();

            if ( isactive == 1 ) {
                String logSession = "update lilosessIONS set   'LITIME' = '" + strDate2 + "' where LID = " + lid;

                st = _con.createStatement ();
                st.execute ( logSession );

                String getUserName = "select EMP_NAME from employee where EmployeePK = " + uid;
                rs = DB_Operations.executeSingle ( getUserName );
                Proman_User.setUsername ( rs.getString ( "EMP_NAME" ) );
                Proman_User.setEID ( uid );
                Proman_User.setDeptIds ( deptid );
                Proman_User.setRole ( role );

                loginSessionActive = true;

                Proman_User.setIsActivelyLoggedIn ( true );

                HomeScreen_old.home.jLabel3.setText ( "Welcome " + Proman_User.getUsername () + " " );
                HomeScreen_old.home.LoadDashboard ();

                return true;
            }
        } catch ( SQLException e ) {
            return false;
        }

        return false;
    }

    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem29ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showFinishedGoodsScreen ();
        } else if ( showLogin () ) {

            showFinishedGoodsScreen ();
        }
    }//GEN-LAST:event_jMenuItem29ActionPerformed

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

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {
            showMachineScreen ();
        } else if ( showLogin () ) {
            showMachineScreen ();
        }

            
        
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showRawMaterialScreen ();
        } else if ( showLogin () ) {

            showRawMaterialScreen ();
        }
    }//GEN-LAST:event_jMenuItem28ActionPerformed

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

    private void jMenuItem33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem33ActionPerformed
        // TODO add your handling code here:

        if ( Proman_User.isActivelyLoggedIn () ) {

            showUtilitiesScreen ();
        } else if ( showLogin () ) {

            showUtilitiesScreen ();
        }
    }//GEN-LAST:event_jMenuItem33ActionPerformed

    private void jMenuItem34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem34ActionPerformed
        // TODO add your handling code here:
        Process_Master_Form process = new Process_Master_Form ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int paneW = 830;

        process.setBounds ( ( int ) ( ( screenW - paneW ) / 2 ) , 20 , paneW , 460 );

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( process );
        
        jLabel6.setText ( "  Process Master" );
         home.jScrollPane1.add ( jLabel6 );
                
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem34ActionPerformed

    private void jMenuItem51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem51ActionPerformed
        // TODO add your handling code here:

        UpdateStockPanel updateStockRM = new UpdateStockPanel ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int paneW = 550;

        System.out.println ( screenW + " " + paneW + " " );

        updateStockRM.setBounds ( ( int ) ( ( screenW - paneW ) / 2 ) , 20 , paneW , paneW );

        updateStockRM.loadContent ();
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( updateStockRM );
        
        jLabel6.setText ( "  Inventory/Stock Master" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem51ActionPerformed

    private void jMenuItem38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem38ActionPerformed
        // TODO add your handling code here:

        SalesForm salesForm = new SalesForm ();
        salesForm.setBounds ( 0 , 0 , home.jScrollPane1.getWidth () , home.jScrollPane1.getHeight () );

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( salesForm );
        
        jLabel6.setText ( "  Sales Master" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem38ActionPerformed

    private void jMenuItem52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem52ActionPerformed
        // TODO add your handling code here:
        Processforfinishedgoods process = new Processforfinishedgoods ();
       
        process.setBounds ( 28 , 52 , 1310 , 600 );

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( process );
        
        jLabel6.setText ( "  Product-Process-Machine Map Master" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem52ActionPerformed

    private void jMenuItem40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem40ActionPerformed
        // TODO add your handling code here:
        DailyReportForm2 process = new DailyReportForm2 ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        process.setBounds ( ( int ) ( ( screenW - paneW ) / 2 ) , 0 , paneW , 650 );

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( process );
        
        jLabel6.setText ( "  Production Data Entry" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem40ActionPerformed

    private void jMenuItem50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem50ActionPerformed
        // TODO add your handling code here:
        if ( Proman_User.isActivelyLoggedIn () ) {
            LoadDashboard ();
        } else if ( showLogin () ) {
            LoadDashboard ();
        }
    }//GEN-LAST:event_jMenuItem50ActionPerformed

    private void jMenuItem44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem44ActionPerformed
        // TODO add your handling code here:
        //  ProductionNWIP2 process = new ProductionNWIP2 ();
        ReportsController_V09 ppmReport = new ReportsController_V09 ( "Production Quantity" );
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        ppmReport.setBounds (28 , 52 , 1310 , 600 );

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( ppmReport );
        
        jLabel6.setText ( "  Production Quantity Report" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem44ActionPerformed

    private void jMenuItem53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem53ActionPerformed
        // TODO add your handling code here:
        //     ReportWIP1 process = new ReportWIP1();
        ReportsController_V09 process = new ReportsController_V09 ( "Work In Progress" );
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        process.setBounds (28 , 52 , 1310 , 600 );

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( process );
        
        jLabel6.setText ( "  Work In Progress Report" );
         home.jScrollPane1.add ( jLabel6 );
        
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

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( process );
        
        jLabel6.setText ( "  Work In Progress Report" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem54ActionPerformed

    private void jMenuItem49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem49ActionPerformed
        // TODO add your handling code here:
//        ReportRejection process = new ReportRejection();
        ReportsController_V09 process = new ReportsController_V09 ( "Rejection" );
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        process.setBounds (28 , 52 , 1310 , 600 );

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( process );
        
        jLabel6.setText ( "  Rejection Report" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem49ActionPerformed

    private void jMenuItem41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem41ActionPerformed
        // TODO add your handling code here:
        DailyReportFormProcessWise process = new DailyReportFormProcessWise ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        process.setBounds ( ( int ) ( screenW - paneW ) / 2 , 0 , paneW , 650 );

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( process );
        
        jLabel6.setText ( "  Production Data Entry" );
         home.jScrollPane1.add ( jLabel6 );
        
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

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( process );
        
        jLabel6.setText ( "  Production Data Entry" );
         home.jScrollPane1.add ( jLabel6 );
        
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

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( process );
        
        jLabel6.setText ( "  Production Data Entry" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem43ActionPerformed

    private void jMenuItem46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem46ActionPerformed
        // TODO add your handling code here:
        
        PlantMaster2 plantMaster = new PlantMaster2 ();
        JOptionPane.showMessageDialog ( null, "Showing Master");
        plantMaster.setBounds ( 28 , 52 ,  plantMaster.getPreferredSize ().width,  plantMaster.getPreferredSize ().height);
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( plantMaster );
        jLabel6.setText ( "  Plant Master" );
        home.jScrollPane1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem46ActionPerformed

    private void jMenuItem45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem45ActionPerformed
        // TODO add your handling code here:
        //      ReportProductionValue process = new ReportProductionValue ();
        ReportsController_V09 process = new ReportsController_V09 ( "Production Value" );
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        process.setBounds (28 , 52 , 1310 , 600 );

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( process );
        
        jLabel6.setText ( "  Production Value Report" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem45ActionPerformed

    private void jMenuItem48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem48ActionPerformed
        // TODO add your handling code here:
//        ReportProductivity process = new ReportProductivity ();
        // OperatorEfficiencyReport effReport = new OperatorEfficiencyReport();
        ReportsController_V09 process = new ReportsController_V09 ( "Productivity" );
        
       
        process.setBounds (28 , 52 , 1310 , 600 );

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( process );
        
        jLabel6.setText ( "  Productivity Report" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem48ActionPerformed

    private void jMenuItem39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem39ActionPerformed
        // TODO add your handling code here:
        DailyReportForm process = new DailyReportForm ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1250;

        process.setBounds ( ( ( screenW - paneW ) / 2 ) , 20 , paneW , 650 );

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( process );
        
        jLabel6.setText ( "  Production Data Entry" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem39ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        //DocumentManagement process = new DocumentManagement ();
        DocumentRepository process = new DocumentRepository ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1030;

        process.setBounds ( ( ( screenW - paneW ) / 2 ) , 20 , paneW , 550 );

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( process );
        
        jLabel6.setText ( "  Documents Master" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        
            ProductCustomerMap process = new ProductCustomerMap ();
            //home.jScrollPane1.setBounds ( ((screenW-paneW)/2), 0, paneW, 650 );
            process.setBounds ( 0 , 0  , 1310 , 600 );
            process.revalidate ();
            process.repaint ();
            
            jScrollPane1 = new ScrollPaneABC ();
               
            
           // jScrollPane1.abc.add( process );
            jScrollPane1.abc.add( process );
            jScrollPane1.abc.revalidate ();
            jScrollPane1.abc.repaint ();
           
            
             home.add ( jScrollPane1,  BorderLayout.CENTER );
            jLabel6.setText ( "  Product-Customer Map Master" );
            jScrollPane1.add ( jLabel6 );
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
//        PPM_Report ppmReport = new PPM_Report ();
        ReportsController_V09 ppmReport = new ReportsController_V09 ( "Total Rejection" );
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        ppmReport.setBounds (28 , 52 , 1310 , 600 );

        // ppmReport.setBounds( ((screenW-jScrollPane1.getWidth ())/2), 0, jScrollPane1.getWidth(), jScrollPane1.getHeight ());
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( ppmReport );
        
        jLabel6.setText ( "  Total Rejection Report" );
         home.jScrollPane1.add ( jLabel6 );
        
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

        ppmReport.setBounds (28 , 52 , 1310 , 600 );

        // ppmReport.setBounds( ((screenW-jScrollPane1.getWidth ())/2), 0, jScrollPane1.getWidth(), jScrollPane1.getHeight ());
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( ppmReport );
        
        jLabel6.setText ( "  Cost VS Value & PMPH Report" );
         home.jScrollPane1.add ( jLabel6 );
        
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
        // ppmReport.setBounds( ((screenW-jScrollPane1.getWidth ())/2), 0, jScrollPane1.getWidth(), jScrollPane1.getHeight ());
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        // home.jScrollPane1.add(ppmReport);
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:

        EmployeeProcessMap process = new EmployeeProcessMap ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1250;

        process.setBounds (28 , 52 , 1310 , 600 );

        //  process.setBounds( ((screenW-jScrollPane1.getWidth ())/2), 0, jScrollPane1.getWidth(), jScrollPane1.getHeight ());
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( process );
        
        jLabel6.setText ( "  Employee-Process Map Master" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenu12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu12ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jMenu12ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:

        Alarms process = new Alarms ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1250;

        process.setBounds ( ( ( screenW - paneW ) / 2 ) , 0 , paneW , 650 );
        //process.setBounds( ((screenW-jScrollPane1.getWidth ())/2), 0, jScrollPane1.getWidth(), jScrollPane1.getHeight ());

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( process );
        home.revalidate ();
        home.repaint ();
        //home.pack ();


    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:

        ReportsController_V09 ppmReport = new ReportsController_V09 ( "OEE" );
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        ppmReport.setBounds (28 , 52 , 1310 , 600 );

        // ppmReport.setBounds( ((screenW-jScrollPane1.getWidth ())/2), 0, jScrollPane1.getWidth(), jScrollPane1.getHeight ());
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( ppmReport );
        
        jLabel6.setText ( "  Overall Equippment Efficiency Report" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();
        //home.pack ();


    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:

        SetupForm ppmReport = new SetupForm ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        ppmReport.setBounds (28 , 52 , 1310 , 600 );

        // ppmReport.setBounds( ((screenW-jScrollPane1.getWidth ())/2), 0, jScrollPane1.getWidth(), jScrollPane1.getHeight ());
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( ppmReport );
        
        //jLabel6.setText ( "  Daily Setup Request & Approval Master" );
        //home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();
        //home.pack ();

    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        UserRolePermissionMaster ppmReport = new UserRolePermissionMaster ();
       ppmReport.setBounds (28 , 52 , 1310 , 600 );

        // ppmReport.setBounds( ((screenW-jScrollPane1.getWidth ())/2), 0, jScrollPane1.getWidth(), jScrollPane1.getHeight ());
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( ppmReport );
        
        jLabel6.setText ( "  Users-Roles-Permissions Master" );
        home.jScrollPane1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();

    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:

        ImportMasters process = new ImportMasters ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1250;

        process.setBounds (28 , 52 , 1310 , 600 );

        //  process.setBounds( ((screenW-jScrollPane1.getWidth ())/2), 0, jScrollPane1.getWidth(), jScrollPane1.getHeight ());
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( process );
        
        
        
        home.revalidate ();
        home.repaint ();
        //home.pack ();


    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:

        int selection = JOptionPane.showConfirmDialog ( null , "You will be logged out from current session. You want to condinue ? " );

        if ( selection == JOptionPane.YES_OPTION ) {
            logout ();
        }

    }//GEN-LAST:event_jLabel5MouseClicked

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        ReportsController2 ppmReport = new ReportsController2 ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        ppmReport.setBounds (28 , 52 , 1310 , 600 );

        // ppmReport.setBounds( ((screenW-jScrollPane1.getWidth ())/2), 0, jScrollPane1.getWidth(), jScrollPane1.getHeight ());
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( ppmReport );
        
        jLabel6.setText ( "  Defect Occurrance Report" );
         home.jScrollPane1.add ( jLabel6 );
        
        home.revalidate ();
        home.repaint ();
        // //home.pack ();
        //   
        home.setExtendedState ( JFrame.MAXIMIZED_BOTH );
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        // TODO add your handling code here:
        BillOfMaterialUI2 bom = new BillOfMaterialUI2 ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        //utilities.setBounds ( ( screenW - 1250 ) / 2 , 0 , 1250 , 660 );
        bom.setBounds ( 28 , 52 , bom.getPreferredSize ().width , bom.getPreferredSize ().height );

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( bom );
        jLabel6.setText ( "  Bill of Material Master" );

        home.jScrollPane1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenu8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu8ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jMenu8ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        BatchProcessing batch = new BatchProcessing ();
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        //utilities.setBounds ( ( screenW - 1250 ) / 2 , 0 , 1250 , 660 );
        batch.setBounds ( 28 , 52 , batch.getPreferredSize ().width , batch.getPreferredSize ().height );

        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( batch );
        jLabel6.setText ( "  Raw Material Processing-Batch Master" );

        home.jScrollPane1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
//        appSettings process = new appSettings ();
//                Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
//                int screenW = ( int ) d.getWidth ();
//                int screenH = ( int ) d.getHeight ();
//                int paneW = 1300;
//
//                process.setBounds ( ( int ) ( screenW - paneW ) / 2 , 0 , paneW , 650 );
//
//                home.jScrollPane1.removeAll ();
//                home.jScrollPane1.setLayout ( null );
//                home.jScrollPane1.add ( process );
//                home.revalidate ();
//                home.repaint ();


    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        // TODO add your handling code here:
        ReportsController3 ppmReport = new ReportsController3 ("Production to Sales Coverage Ratio");
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        jLabel6.setText ( "  Production to Sales Coverage Ratio Report" );
        home.jScrollPane1.add ( jLabel6 );
        ppmReport.setBounds ( 28 , 52 ,  paneW , 600 );

        // ppmReport.setBounds( ((screenW-jScrollPane1.getWidth ())/2), 0, jScrollPane1.getWidth(), jScrollPane1.getHeight ());
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( ppmReport );
        
        home.jScrollPane1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();
        // //home.pack ();
        //   
        home.setExtendedState ( JFrame.MAXIMIZED_BOTH );
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenu11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu11ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        // TODO add your handling code here:
        
         RMEfficienctReport RMEReport = new RMEfficienctReport ("Raw Material Efficiency");
        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();
        int paneW = 1300;

        jLabel6.setText ( "  Raw Material Efficiency Report" );
        
        RMEReport.setBounds ( 28 , 52 ,  paneW , 600 );

        // ppmReport.setBounds( ((screenW-jScrollPane1.getWidth ())/2), 0, jScrollPane1.getWidth(), jScrollPane1.getHeight ());
        home.jScrollPane1.removeAll ();
        home.jScrollPane1.setLayout ( null );
        home.jScrollPane1.add ( RMEReport );
        
        home.jScrollPane1.add ( jLabel6 );
        home.revalidate ();
        home.repaint ();
        // //home.pack ();
        //   
        home.setExtendedState ( JFrame.MAXIMIZED_BOTH );
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main ( String args[] ) {

        java.awt.EventQueue.invokeLater (new Runnable () {
            public void run () {

                
                
                home = new HomeScreen_old ();

                            
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
                

                home.setVisible ( true );
                home.setTitle ( "PROD-EZY (Demo Version)" );
                home.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 14 ) ); // NOI18N;
                home.getContentPane ().setBackground ( Color.DARK_GRAY );
           //     home.jScrollPane1.setBackground ( Color.WHITE);
                
                //home.jScrollPane1 = new MyHomePanel ();
//                MyHomePanel p = new MyHomePanel ();
//                
//                p.setBounds ( 0 , 0, 1366, 667);
//                home.jScrollPane1.add(p);
//                
//                home.jScrollPane1.setBackground ( Color.WHITE);
               // home.setExtendedState ( JFrame.MAXIMIZED_BOTH );

             
                
               
                Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
                int screenW = ( int ) d.getWidth ();
              
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
                            }
                        }
                    }
                };

                t.start ();

               
                ppm = 0.0;
                alarmsList = new ArrayList<String> ();
                Thread t2 = new Thread () {
                    public void run () {
                        while ( true ) {

                            try {
                                while ( true ) {
                                    for ( int i = 0 ; i < alarmsList.size () ; i ++ ) {
                                        alarmsList.remove ( i );
                                    }
                               //     getTodaysTotalPPM ();
                               //     getTodaysTotalProduction ();
                               //     getRMatROL ();
                               //     getRejectionForProducts ();
                                    for ( int i = 0 ; i < alarmsList.size () ; i ++ ) {
                                        Thread.sleep ( 6000 );
                                        jLabel1.setText ( alarmsList.get ( i ) );
                                    }

                                }
                            } catch ( InterruptedException e ) {
                            }
                        }
                    }
                };
                t2.start ();

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

    public static void getTodaysTotalPPM () {

        c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-dd-MM" );
        //String strDate2 = sdf2.format ( c2.getTime () ) + " 00:00:00";

        String strDate2 = "2018-09-27 00:00:00";

        try {
            ResultSet rs = DB_Operations.executeSingle ( "select round( CAST(((TOTAL(rejection)/TOTAL(qtyout))*1000000) as real) , 2) as 'TOTAL PPM' from dailyreport where rdate = '" + strDate2 + "' " );
            alarmsList.add ( "Plant PPM for today : " + Double.parseDouble ( rs.getString ( "TOTAL PPM" ) ) );
        } catch ( SQLException | NullPointerException e ) {
            System.out.println ( "PPM function " + e.getClass () + " " + e.getMessage () );
        }
    }

    public static double getTodaysTotalProduction () {

        c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-dd-MM" );
        String strDate2 = sdf2.format ( c2.getTime () ) + " 00:00:00";

        //String strDate2 = "2018-09-27 00:00:00";
        ResultSet rs = null;
        processMapValues = new ArrayList<String[]> ();
        try {
            rs = DB_Operations.executeSingle ( "select REF_FG_ID , REF_PROCESS_ID from FG_PROCESS_MACH_MAP " );
            while ( rs.next () ) {
                processMapValues.add ( new String[] { "" + rs.getString ( 1 ) , "" + rs.getString ( 2 ) } );
            }
        } catch ( SQLException | NullPointerException e ) {
            //      System.out.println ( "PRODUCTION function 1 "+e.getClass ()+" "+e.getMessage () );
        }

        try {
            rs.close ();
        } catch ( SQLException ex ) {
            System.out.println ( "" + ex.getMessage () );
        }

        productionTargetVSActual = new ArrayList<String[]> ();
        int l1 = 0, l2 = 0;
        try {
            for ( int i = 0 ; i < processMapValues.size () ; i ++ ) {
                rs = DB_Operations.executeSingle ( "select  SUM(qtyout) as 'Total production for the day',  (select TG_OPT_DAY from FG_PROCESS_MACH_MAP ) as 'Target production for the day'  from dailyreport  where fgid = " + processMapValues.get ( i )[ 0 ] + " AND processid = " + processMapValues.get ( i )[ 1 ] + " AND rdate = '" + strDate2 + "' " );
                try {
                    l1 = Integer.parseInt ( rs.getString ( 1 ) );
                    l2 = Integer.parseInt ( rs.getString ( 2 ) );
                    if ( l1 >= l2 && l1 != 0 && l2 != 0 ) {
                        //processMapValues.add( new String[]{  ""+l1, ""+l2  }); 
                        alarmsList.add ( "Product " + processMapValues.get ( i )[ 0 ] + " at " + processMapValues.get ( i )[ 1 ] + "  Target Production: " + l2 + ",  Todays production: " + l1 );
                    }

                } catch ( NumberFormatException e ) {
                    try {
                rs.close ();
            } catch ( SQLException ex ) {
                System.out.println ( "" + ex.getMessage () );
            }
                    //       System.out.println ("PRODUCTION function 2 "+ e.getClass ()+" "+e.getMessage () );
                }

            }

            double totalpPM = Double.parseDouble ( rs.getString ( "TOTAL PPM" ) );
            try {
                rs.close ();
            } catch ( SQLException ex ) {
                System.out.println ( "" + ex.getMessage () );
            }
            return totalpPM;
        } catch ( SQLException | NullPointerException e ) {
            
            try {
                rs.close ();
            } catch ( SQLException ex ) {
                System.out.println ( "" + ex.getMessage () );
            }
            return 0.0;
            
        }
    }

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

                        alarmsList.add ( "Raw Material " + rmname + " available quantity is 0" );
                    } else if ( available > 0 && rol != 0.0 ) {
                        if ( available < rol ) {
                            alarmsList.add ( "Raw Material " + rmname + " available quantity " + available + " is less than ROL " + rol );
                        }
                    }
                }
            }
        } catch ( SQLException e ) {
            System.out.println ( e.getClass () + "  " + e.getMessage () );
        }

    }

    public static void getRejectionForProducts () {

        c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-dd-MM" );
        //String strDate2 = sdf2.format ( c2.getTime () ) + " 00:00:00";
        String strDate2 = "2018-10-25 00:00:00" ;
        
        
        ArrayList<String[]> ProcProdRej = new ArrayList<String[]>();
        ArrayList<String[]> TodaysRej = new ArrayList<String[]>();
        
        // 1. get the details of processes , products that are in progress today and actual rejections for the day in correspoding processes and products
        //     select processid, fgid, rejection from dailyreport where rdate = strDate2 AND rejection > 0
        ResultSet rs = DB_Operations.executeSingle ( "select processid, fgid, rejection from dailyreport where rdate = '"+strDate2+"' AND rejection > 0" ) ;
        try{
            while(rs.next()){
                        TodaysRej.add( new String[]{ rs.getString(1),  rs.getString(2),rs.getString(3)}  ); 
            }
            rs.close();
        }catch(SQLException e){
            try {
                rs.close();
            } catch ( SQLException ex ) {
               
            }
        }
        
        
        // 2. get the details of processes , products and corresponding rejection limits
        //     select MAX_REJ_PERM from FG_PROCESS_MACH_MAP where REF_FG_ID = 11 AND REF_PROCESS_ID = 8
        try{
            
            for ( int i = 0 ; i < TodaysRej.size () ; i ++ ) {
                rs = DB_Operations.executeSingle ( "select MAX_REJ_PERM from FG_PROCESS_MACH_MAP where REF_FG_ID = "+TodaysRej.get(i)[1]+" AND REF_PROCESS_ID = "+TodaysRej.get(i)[0] ) ;
                while(rs.next()){
                            ProcProdRej.add( new String[]{ TodaysRej.get(i)[0],  TodaysRej.get(i)[1],rs.getString(1)}  ); 
                }         
                rs.close();
            }
            
        }catch(SQLException e){
            try {
                rs.close();
            } catch ( SQLException ex ) {
               
            }
        }
        
        // 3. compare corresponding processe-products and actual vs maximum rejection
        
        for ( int i = 0; i < TodaysRej.size() ; i ++  ) {
            
            for ( int j=0 ;  j< ProcProdRej.size();  j++ ) {
                
                if(  TodaysRej.get(i)[0].equals (  ProcProdRej.get(j)[0] )   && TodaysRej.get(i)[1].equals (  ProcProdRej.get(j)[1] ))
                {
                    int actualRej   = Integer.parseInt( TodaysRej.get(i)[2] );
                    int maxRej      = Integer.parseInt( ProcProdRej.get(i)[2] );

                        if( actualRej > maxRej ){
                                alarmsList.add ( "Rejection in process "+TodaysRej.get(i)[0]+" for product "+ TodaysRej.get(i)[1] +" is "+actualRej+" - higher than max permissible  "+maxRej   );                
                        }else  if( actualRej == maxRej ){

                                alarmsList.add ( "Rejection in process "+TodaysRej.get(i)[0]+" for product "+ TodaysRej.get(i)[1] +" is "+actualRej+" - reached max permissible  "+maxRej   );                
                        }
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

            st.close();
            
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
            removeForms ();

        } catch ( SQLException e ) {
            System.out.println ( ""+e.getMessage () );
            JOptionPane.showMessageDialog ( null, ""+e.getMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public static javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu14;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu3;
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
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
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
    // End of variables declaration//GEN-END:variables

    static Calendar c2;
    static SimpleDateFormat sdf2;

    static int[] values = new int[ 4 ];
    static String[] titles = new String[ 4 ];
    static Double ppm;

    public static ArrayList<String[]> processMapValues = null;
    public static ArrayList<String[]> productionTargetVSActual = null;
    public static ArrayList<String> alarmsList = new ArrayList<String> ();

    public static boolean checkUserRolePermission () {

        boolean canAccessResource = false;

        return canAccessResource;
    }

}
