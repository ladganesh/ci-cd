/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import trixware.erp.prodezydesktop.Derived.AutoCompletion;
import trixware.erp.prodezydesktop.Model.MachineDR;
import trixware.erp.prodezydesktop.Model.ProcessDR;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;

/**
 *
 * @author Owner
 */
public class Processforfinishedgoods2 extends javax.swing.JPanel {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    static ArrayList<String[]> finishedGoodsList, processesList, machinesList , uom4, uom7, uom5, uom6, uom8;
    
    ArrayList<ProductDR> products = null;
    ArrayList<ProcessDR> processes = null;
    ArrayList<MachineDR> machines = null;
  
    String resource = "" , resourceType = "" ;
    
    ProductDR prdr = null;
    ProcessDR prcdr = null;
    MachineDR mcdr = null;
    
    
    public Processforfinishedgoods2() {
        initComponents();
        
      //  showDetailsInTable() ;
      
      resourceType = "Masters" ;
	resource = "Processforfinishedgoods2"  ;

	try{
      		StaticValues.checkUserRolePermission(resourceType, resourceType);
	   }catch(Exception e){
      		StaticValues.writer.writeExcel (resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
   	   }
        
      String queryOne = "SELECT FG_ID, PART_NAME FROM finished_goods";
        String queryThree = "SELECT MCH_ID, MACHINE_NO FROM machine";
        String queryFour = "SELECT PROCESS_ID, PROCESS_NAME FROM PROCESS_MASTER";
      
        try {

         //   File f = new File("srirubber-v3.db");
         //   if (f.exists()) {
            
//                con = DriverManager.getConnection("jdbc:sqlite:"+StaticValues.dbName);
//                String createProcessMasterTable = "CREATE TABLE IF NOT EXISTS FG_PROCESS_MACH_MAP ( 'FPM_ID'  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,  'REF_FG_ID' INTEGER, '_REF_PROCESS_ID' INTEGER,  'REF_MCH_ID' INTEGER,   'SETUP_TIME' TEXT, 'IDEAL_PROCESS_TIME' TEXT, 'REMARKS' TEXT )" ;
//                pst = con.prepareStatement ( createProcessMasterTable  ) ;
//                pst.execute ();
                               
//                String loadAllFGs = "SELECT * FROM finished_goods";
//                pst = con.prepareStatement ( loadAllFGs );
//                rs = pst.executeQuery ( ) ;
//                    rs = DB_Operations.getMasters ( "finished_goods" ) ;
//                finishedGoodsList = new ArrayList<String[]>();
//                while(rs.next()){
//               
//                    finishedGoodsList.add( new String[]{ String.valueOf(rs.getString("FG_ID")), rs.getString("FG_PART_NO"),  rs.getString("PART_NAME")}  );
//                    jComboBox1.addItem(rs.getString("FG_PART_NO")+",  FG NAME: "+  rs.getString("PART_NAME") );
//               }

                    try {
                        rs = DB_Operations.executeSingle ( queryOne );
                        //  products = new ArrayList<HashMap<String, String>> ();
                        products = new ArrayList<ProductDR> ();
                        while ( rs.next () ) {
                            //   entity =  new  HashMap<String, String>();
                            prdr = new ProductDR ();
                            //    entity.put ( rs.getString(1) /*  String.valueOf( rs.getInt(1) ) */, rs.getString(2)) ;
                            prdr.FG_ID = Integer.parseInt ( rs.getString ( 1 ) );
                            prdr.FG_PART_NO = rs.getString ( 2 );
                            products.add ( prdr );
                            jComboBox1.addItem ( rs.getString ( 2 ) );
                        }

                        rs.close ();
                    } catch ( Exception e ) {
                        System.out.println ( " Error 1 " + e.getMessage () );
                    }

                    
                    try {
            rs = DB_Operations.executeSingle ( queryThree );
            machines = new ArrayList<MachineDR> ();

            while ( rs.next () ) {
                mcdr = new MachineDR ();

                mcdr.MC_ID = Integer.parseInt ( rs.getString ( 1 ) );
                mcdr.MC_NAME = rs.getString ( 2 );
                machines.add ( mcdr );
                jComboBox3.addItem ( rs.getString ( 2 ) );
            }
            
            rs.close() ;

        } catch ( Exception e ) {
            System.out.println ( " Error 2 " + e.getMessage () );
        }

        try {
            rs = DB_Operations.executeSingle ( queryFour );
            processes = new ArrayList<ProcessDR> ();

            while ( rs.next () ) {
                prcdr = new ProcessDR ();

                prcdr.PRC_ID = Integer.parseInt ( rs.getString ( 1 ) );
                prcdr.PRC_NAME = rs.getString ( 2 );
                processes.add ( prcdr );
                jComboBox2.addItem ( rs.getString ( 2 ) );
            }

            rs.close();
        } catch ( Exception e ) {
            System.out.println ( " Error 2 " + e.getMessage () );
        }
                
//                String loadAllProcesses = "SELECT * FROM PROCESS_MASTER";
//                pst = con.prepareStatement ( loadAllProcesses );
//                rs = pst.executeQuery ( ) ;
//                processesList = new ArrayList<String[]>();
//                while(rs.next()){
//               
//                    processesList.add( new String[]{ String.valueOf(rs.getString("PROCESS_ID")), rs.getString("PROCESS_NAME")}  );
//                    jComboBox2.addItem( rs.getString("PROCESS_NAME") );
//               }
//                
//               
//                String loadAllMachines = "SELECT * FROM machine";
//                pst = con.prepareStatement ( loadAllMachines );
//                rs = pst.executeQuery ( ) ;
//                machinesList = new ArrayList<String[]>();
//                while(rs.next()){
//               
//                    machinesList.add( new String[]{ String.valueOf(rs.getString("MCH_ID")), rs.getString("MACHINE_NO"), rs.getString("MAKE") }  );
//                    jComboBox3.addItem( rs.getString("MACHINE_NO")+",  "+  rs.getString("MAKE") );
//               }
            
                ResultSet result ;
                 result = DB_Operations.getMasters ( "uom" );
                 jComboBox4.removeAllItems ( );
                //    jComboBox5.removeAllItems ( );
                 //   jComboBox5.removeAllItems (  );
                if ( result != null ) {
                    while ( result.next () ) {
                    
                    uom4 =  uom7 =  uom5 =  uom6 = uom8 = new ArrayList<String[]>();
                    
                    uom4.add(  new String[]{  result.getString ( "UOM_ID" ), result.getString ( "UOM" )  }); 
                    uom7.add(  new String[]{  result.getString ( "UOM_ID" ), result.getString ( "UOM" )  }); 
                    uom5.add(  new String[]{  result.getString ( "UOM_ID" ), result.getString ( "UOM" )  }); 
                    uom6.add(  new String[]{  result.getString ( "UOM_ID" ), result.getString ( "UOM" )  }); 
                    uom8.add(  new String[]{  result.getString ( "UOM_ID" ), result.getString ( "UOM" )  }); 
                    jComboBox4.addItem ( result.getString ( "UOM" ) );
                    jComboBox7.addItem ( result.getString ( "UOM" ) );
                   
                    jComboBox6.addItem ( result.getString ( "UOM" ) );
                    jComboBox8.addItem ( result.getString ( "UOM" ) );
                }
                    
                    
            } else {
                jComboBox4.addItem ( "Not Available" );
                jComboBox7.addItem ( "Not Available" );
                
                jComboBox6.addItem ( "Not Available" );
                jComboBox8.addItem ( "Not Available" );
            }
                
                result.close() ;
                
                AutoCompletion.enable ( jComboBox1);
                AutoCompletion.enable ( jComboBox2);
                AutoCompletion.enable ( jComboBox3);
                
             //   loadContent ();
          //  }
        } catch (SQLException ex) {
           System.out.println ( ex.getMessage () );
        }
        
        loadDefaultMap ();
                
    }

    
     public void loadContent(){
        
        try{
               // String getAllProcesses = "SELECT PROCESS_NAME, MACHINE_NO, MAKE, SETUP_TIME, IDEAL_PROCESS_TIME, REMARKS FROM FG_PROCESS_MACH_MAP, PROCESS_MASTER, machine WHERE FG_PROCESS_MACH_MAP.REF_PROCESS_ID  = PROCESS_MASTER.PROCESS_ID AND FG_PROCESS_MACH_MAP.REF_MCH_ID = machine.MCH_ID    ";
              String getAllProcesses = "SELECT PROCESS_NAME, MACHINE_NO, MAKE, SETUP_TIME, IDEAL_PROCESS_TIME, REMARKS FROM FG_PROCESS_MACH_MAP, PROCESS_MASTER, machine WHERE FG_PROCESS_MACH_MAP.REF_PROCESS_ID  = PROCESS_MASTER.PROCESS_ID AND FG_PROCESS_MACH_MAP.REF_MCH_ID = machine.MCH_ID  AND FG_PROCESS_MACH_MAP.REF_FG_ID="+selectedFGID;
               
                pst = con.prepareStatement ( getAllProcesses  ) ;
                rs = pst.executeQuery ();

                jTable1.setModel( buildTableModel ( rs ) );
                
                rs.close();
    
        } catch (SQLException ex) {
            // Logger.getLogger(DB_Operations.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "" + ex.getMessage());
            System.out.println("DB not Connected");
        }
    }

      public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            
             columnNames.add(metaData.getColumnName(column));
        }

        
        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
                
                
            for (int columnIndex = 1; columnIndex <= columnCount ; columnIndex++) {
          
                vector.add(rs.getObject(columnIndex));
                
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }
    
      
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jComboBox6 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(500, 300));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jLabel1.setText("<html>Finished Goods :</html>");
        add(jLabel1);
        jLabel1.setBounds(80, 30, 140, 20);

        jLabel2.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jLabel2.setText("Process :");
        add(jLabel2);
        jLabel2.setBounds(80, 80, 80, 20);

        jLabel3.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jLabel3.setText("Machine :");
        add(jLabel3);
        jLabel3.setBounds(80, 130, 80, 20);

        jLabel4.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jLabel4.setText("Setup Time :");
        add(jLabel4);
        jLabel4.setBounds(80, 180, 80, 20);

        jLabel5.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jLabel5.setText("Std Time/Unit :");
        add(jLabel5);
        jLabel5.setBounds(80, 230, 80, 20);

        jLabel7.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jLabel7.setText("Remarks :");
        add(jLabel7);
        jLabel7.setBounds(600, 30, 70, 20);

        jComboBox1.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jComboBox1.setNextFocusableComponent(jComboBox2);
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        add(jComboBox1);
        jComboBox1.setBounds(120, 50, 213, 30);

        jComboBox2.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jComboBox2.setNextFocusableComponent(jComboBox3);
        jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox2MouseClicked(evt);
            }
        });
        add(jComboBox2);
        jComboBox2.setBounds(120, 100, 213, 30);

        jComboBox3.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jComboBox3.setNextFocusableComponent(jTextField1);
        jComboBox3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox3MouseClicked(evt);
            }
        });
        add(jComboBox3);
        jComboBox3.setBounds(120, 150, 213, 30);

        jTextField1.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jTextField1.setNextFocusableComponent(jComboBox4);
        add(jTextField1);
        jTextField1.setBounds(120, 200, 110, 30);

        jTextField2.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        add(jTextField2);
        jTextField2.setBounds(120, 250, 110, 30);

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        add(jScrollPane2);
        jScrollPane2.setBounds(640, 50, 280, 110);

        jButton1.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jButton1.setText("Add");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(640, 180, 130, 40);

        jButton2.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jButton2.setText("Update");
        add(jButton2);
        jButton2.setBounds(640, 220, 130, 40);

        jButton3.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jButton3.setText("Reset");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(790, 220, 130, 40);

        jButton4.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jButton4.setText("Cancel");
        add(jButton4);
        jButton4.setBounds(790, 260, 130, 40);

        jButton5.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jButton5.setText("Export to Excel");
        add(jButton5);
        jButton5.setBounds(640, 260, 130, 40);

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setOpaque(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);

        add(jScrollPane3);
        jScrollPane3.setBounds(40, 340, 880, 250);

        jButton6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton6.setText("Load Map");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        add(jButton6);
        jButton6.setBounds(790, 180, 130, 40);

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 51));
        jLabel27.setText("*");
        add(jLabel27);
        jLabel27.setBounds(70, 30, 10, 20);

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 0, 51));
        jLabel28.setText("*");
        add(jLabel28);
        jLabel28.setBounds(70, 80, 30, 20);

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 0, 51));
        jLabel29.setText("*");
        add(jLabel29);
        jLabel29.setBounds(70, 130, 30, 20);

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 0, 51));
        jLabel30.setText("*");
        add(jLabel30);
        jLabel30.setBounds(360, 230, 30, 20);

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 0, 51));
        jLabel31.setText("*");
        add(jLabel31);
        jLabel31.setBounds(70, 230, 30, 20);

        jComboBox4.setNextFocusableComponent(jTextField2);
        jComboBox4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox4MouseClicked(evt);
            }
        });
        add(jComboBox4);
        jComboBox4.setBounds(240, 200, 90, 30);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("Target output per shift");
        add(jLabel8);
        jLabel8.setBounds(370, 80, 180, 20);

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 0, 51));
        jLabel32.setText("*");
        add(jLabel32);
        jLabel32.setBounds(70, 180, 30, 10);
        add(jTextField4);
        jTextField4.setBounds(390, 50, 90, 30);

        jComboBox6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jComboBox6);
        jComboBox6.setBounds(490, 50, 90, 30);

        jComboBox7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jComboBox7);
        jComboBox7.setBounds(240, 250, 90, 30);
        add(jTextField5);
        jTextField5.setBounds(390, 100, 90, 30);
        add(jTextField6);
        jTextField6.setBounds(390, 150, 90, 30);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("Target output per hour");
        add(jLabel9);
        jLabel9.setBounds(370, 30, 180, 20);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setText("Target output per day");
        add(jLabel10);
        jLabel10.setBounds(370, 130, 140, 16);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("<html>Cost of the RM / IRM after this process</html>");
        add(jLabel11);
        jLabel11.setBounds(370, 180, 240, 20);
        add(jTextField7);
        jTextField7.setBounds(390, 200, 90, 30);

        jComboBox8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jComboBox8);
        jComboBox8.setBounds(490, 200, 90, 30);

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 0, 51));
        jLabel33.setText("*");
        add(jLabel33);
        jLabel33.setBounds(360, 30, 30, 20);

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 0, 51));
        jLabel34.setText("*");
        add(jLabel34);
        jLabel34.setBounds(360, 90, 30, 10);

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 0, 51));
        jLabel35.setText("*");
        add(jLabel35);
        jLabel35.setBounds(360, 130, 30, 20);

        jLabel36.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 0, 51));
        jLabel36.setText("*");
        add(jLabel36);
        jLabel36.setBounds(360, 180, 30, 20);

        jTextField3.setText("0");
        add(jTextField3);
        jTextField3.setBounds(390, 250, 90, 30);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("<html>Maximum permissible rejection</html>");
        add(jLabel12);
        jLabel12.setBounds(370, 230, 240, 20);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
       resetFields ();
    }//GEN-LAST:event_jButton3MouseClicked

    public void resetFields(){
         jTextField1.setText("");
        jTextField2.setText("");
        jTextField4.setText("");
         jTextField5.setText("");
        jTextField6.setText("");
         jTextField7.setText("");
        jTextArea2.setText("");
         jTextField3.setText("");
    }
    
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:

        //4     5   6
        //6     8   9
        
        int targetOutputPerHour = Integer.parseInt(  jTextField4.getText().trim()  );
        int targetOutputPerShift = Integer.parseInt(  jTextField5.getText().trim()  );
        int targetOutputPerDay = Integer.parseInt(  jTextField6.getText().trim()  );
        
        int selectedUOM =  Integer.parseInt(  uom4.get ( jComboBox6.getSelectedIndex ())[0] ); 
        
        float val_at_process = Float.parseFloat(jTextField7.getText().trim()  );
        
        int max_perm_rejection =  Integer.parseInt(  jTextField3.getText ().trim() ); 
        
        String query;
        try {
            
            String setuptime, stdtime, outperhour, remarks;
            
            if( ! jTextField1.getText().equals("") )
                setuptime       =  jTextField1.getText();
            else
                setuptime       =  "";           
            
            if( ! jTextField2.getText().equals("") )
                stdtime           = jTextField2.getText();
            else
                stdtime = "";
            
            if( ! jTextField4.getText().equals("") )
                outperhour      = jTextField4.getText();
            else
                outperhour = "";
            
            if( ! jTextArea2.getText().equals("") )
                remarks          = jTextArea2.getText ().trim();
            else
                remarks = "";
            
            
            
            if( ! setuptime.equals ( "") && ! stdtime.equals ( "") && ! outperhour.equals ( "")   ){
            
                
                PreparedStatement pst = DB_Operations.getPreparedStatement().prepareStatement("INSERT INTO FG_PROCESS_MACH_MAP (  'REF_FG_ID',   'REF_PROCESS_ID',   'REF_MCH_ID',   'SETUP_TIME' , 'IDEAL_PROCESS_TIME' , 'REMARKS', 'TG_OPT_HR' , 'TG_OPT_SHT',  'TG_OPT_DAY', 'UOM_ID'  ,  'VAL_AT_PROC' , 'MAX_REJ_PERM') VALUES ( ?,?,?,?,?,?,?,?,?,?,?,? )");
                pst.setString(1, ""+products.get(jComboBox1.getSelectedIndex()).FG_ID);
                pst.setString(2, ""+processes.get( jComboBox2.getSelectedIndex ()).PRC_ID);
                pst.setString(3, ""+machines.get( jComboBox3.getSelectedIndex ()).MC_ID);
                pst.setString(4, setuptime);
                pst.setString(5, stdtime);
                pst.setString(6, remarks);
                pst.setInt(7,targetOutputPerHour);
                pst.setInt(8, targetOutputPerShift);
                pst.setInt(9, targetOutputPerDay);
                pst.setInt(10, selectedUOM );
                pst.setFloat(11, val_at_process );
                pst.setFloat(12, max_perm_rejection );
                
                pst.executeUpdate();

        //        showDetailsInTable();
                    loadContent ();
                    resetFields ();
    

                JOptionPane.showMessageDialog(null, "Information Saved Succesfull!!");
            }else{
                    JOptionPane.showMessageDialog(null, "Please fill all fields marked with *");
            
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Message", JOptionPane.WARNING_MESSAGE);
            System.out.println(e.getMessage());
        }finally{
            try{
                pst.close();
            }catch (Exception e) {
                
            }
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
//        String url1;
//        String query1;
//        try {
//            url1 = "jdbc:sqlite:D:/Vishal Kadam/DataBase/processmaster.db";
//            con = DriverManager.getConnection(url1);
//            query1 = "SELECT * FROM PROCESS_MACH_MAP";
//            
//            pst = con.prepareStatement(query1);
//            
//        } catch (Exception e1) {
//        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
if(jComboBox1.getItemCount()==0){               
            JOptionPane.showMessageDialog(null, "<html><size='06'>First add values in FinishedGoods from FinishedGoods Master");
    }        // TODO add your handling code here:
       
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        // TODO add your handling code here:
        loadDefaultMap ();
    }//GEN-LAST:event_jButton6MouseClicked

    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
 if(jComboBox2.getItemCount()==0){               
            JOptionPane.showMessageDialog(null, "<html><size='06'>First add values in process from Process Master");
    }            // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2MouseClicked

    private void jComboBox3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox3MouseClicked
 if(jComboBox3.getItemCount()==0){               
            JOptionPane.showMessageDialog(null, "<html><size='06'>First add values in Machine from Finished Good Master");
    }            // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3MouseClicked

    private void jComboBox4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox4MouseClicked
 if(jComboBox4.getItemCount()==0){               
            JOptionPane.showMessageDialog(null, "<html><size='06'>First add values in setup Time from Utility Master");
    }            // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4MouseClicked

    
    public void loadDefaultMap(){
        
        if( jComboBox1.getItemCount () >0  ){
            selectedFGID =  products.get ( jComboBox1.getSelectedIndex () ).FG_ID ;
            try{
                
                String getAllProcesses = "SELECT PROCESS_NAME, MACHINE_NO, MAKE, SETUP_TIME, IDEAL_PROCESS_TIME, REMARKS FROM FG_PROCESS_MACH_MAP, PROCESS_MASTER, machine WHERE FG_PROCESS_MACH_MAP.REF_PROCESS_ID  = PROCESS_MASTER.PROCESS_ID AND FG_PROCESS_MACH_MAP.REF_MCH_ID = machine.MCH_ID  AND FG_PROCESS_MACH_MAP.REF_FG_ID="+selectedFGID;
                //pst = con.prepareStatement ( getAllProcesses  ) ;
                //rs = pst.executeQuery ();
                rs = DB_Operations.executeSingle (getAllProcesses ) ;
                
                jTable1.setModel( buildTableModel ( rs ) );
                

                rs.close() ;
        } catch (SQLException ex) {
            // Logger.getLogger(DB_Operations.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "" + ex.getMessage());
            System.out.println("DB not Connected");
        }
        
    }
    }
    
    public void showDetailsInTable(){
        
        ResultSet rs = null ;
        // #1   create select query to read table and store in resultset
        String selectRecords = "SELECT * FROM FG_PROCESS_MACH_MAP";
        
        // #2   create a DefaultTableModel to show details in table
        try{
            
            pst = con.prepareStatement( selectRecords );
            rs = pst.executeQuery() ;
            
            DefaultTableModel tabelModel = new DefaultTableModel();
            Vector<String> columnList = new Vector<String>();
            ResultSetMetaData rsmd = rs.getMetaData();
            int noOfCOlumns = rsmd.getColumnCount();
            
            for( int i = 1 ; i<=noOfCOlumns ;  i++ ){
                
                columnList.add(  rsmd.getColumnName( i ) );
            }
            
            Vector<Vector<String>> data = new Vector<Vector<String>>();
            Vector<String> row ;
            while(rs.next()){
                row = new Vector<String>();
                try{
                    for( int i = 1 ; i <=noOfCOlumns ; i++ ){
                        row.add( rs.getString( i ) );                    
                    }
                }catch(Exception e){
                    for( int i = 1 ; i <=noOfCOlumns ; i++ ){
                        
                        
                        
                        row.add( String.valueOf(rs.getInt( i ) ) );                    
                    }
                }
                data.add( row );
            }
            tabelModel.setDataVector(data, columnList);
            jTable1.setModel( tabelModel );
            
        }catch( SQLException e){
            System.out.println(e.getMessage()) ;
        }
        catch( NullPointerException e){
            System.out.println(e.getMessage()) ;
        }finally{
            try{
                pst.close();
            }catch (Exception e) {
                
            }
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables

       int selectedFGID = 0;
}
