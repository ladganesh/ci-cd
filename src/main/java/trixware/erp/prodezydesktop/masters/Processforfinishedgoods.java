/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import trixware.erp.prodezydesktop.components.QualityPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import trixware.erp.prodezydesktop.Derived.AutoCompletion;
import trixware.erp.prodezydesktop.Model.DieDR;
import trixware.erp.prodezydesktop.Model.MachineDR;
import trixware.erp.prodezydesktop.Model.Main_typeModel_quality;
import trixware.erp.prodezydesktop.Model.ProcessDR;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Model.processMachineMapsExcelData;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.components.ProgressDialog;
import trixware.erp.prodezydesktop.components.TimeDetailsForm;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Owner
 */
public class Processforfinishedgoods extends javax.swing.JPanel 
{

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
   // static ArrayList<String[]>  uom4, uom7, uom5, uom6, uom8;
 ProcessQualityList qualitylist=null;
    ArrayList<ProductDR> products = null;
    ArrayList<DieDR> die = null;
    ArrayList<String[]> UOM=null;
    ArrayList<ProcessDR> processes = null;
    ArrayList<MachineDR> machines = null;
    ArrayList<processMachineMapsExcelData> mcmaps = null;
    //ArrayList<DieToolDR> die_tool = null;

    ProductDR prdr = null;
    ProcessDR prcdr = null;
    MachineDR mcdr = null;
    
    int selecteddie_ID;
    
    
    String resource = "" , resourceType = "" ;
    
    TimeDetailsForm setupTime = null , cycleTime = null ;

    public Processforfinishedgoods () {
        initComponents ();
        
//        qualitylist=new ProcessQualityList();
//        qualitylist.setBounds ( 650 , 40 , 300 , 200 );
//        add( qualitylist );
            
        Thread t  = new Thread(){
            public void run(){
                loadContent ();
            }
        };
        t.start() ;
        
        resourceType = "Masters" ;
        resource = "Processforfinishedgoods" ;
        
        //jButton7.setEnabled(false) ;

//        try{
//                StaticValues.checkUserRolePermission(resourceType, resourceType);
//           }catch(Exception e){
//                StaticValues.writer.writeExcel (resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//           }

            AutoCompletion.enable ( jComboBox1 );
            AutoCompletion.enable ( jComboBox2 );
            AutoCompletion.enable ( jComboBox3 );
            AutoCompletion.enable ( jComboBox4 );


        Thread t2  = new Thread(){
            public void run(){
                loadDefaultMap ();
            }
        };
        t2.start() ;

        

               // jTextField1.addKeyListener(k);
               // jTextField1.addFocusListener(f2) ;
              //  jTextField2.addKeyListener(k);
              //  jTextField2.addFocusListener(f2) ;
                
                jTextField4.addKeyListener(k);
                jTextField4.addFocusListener(f2) ;
                
                jTextField5.addKeyListener(k);
                jTextField5.addFocusListener(f2) ;
                
                jTextField6.addKeyListener(k);
                jTextField6.addFocusListener(f2) ;
                
                jTextField7.addKeyListener(k);
                jTextField7.addFocusListener(f2) ;
                
                
                setupTime = new TimeDetailsForm ("Setup Time", true, "Setup time is mandatory" );
                setupTime.setBounds( 70, 210 , 267, 64 );
                add( setupTime );
                
                cycleTime = new TimeDetailsForm ("Cycle Time", true, "Cycle time is mandatory" );
                cycleTime.setBounds( 70, 275 , 267, 64 );
                add( cycleTime );
               
        jScrollPane3.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane3.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10)); 
        jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane2.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        
    }

    public void loadContent () {

        String addEmpAPICall = "";
        String result2 = "" ;
        
        
        if ( jComboBox1.getItemCount () == 0 ) {
                    //result = DB_Operations.getMasters ( "raw_material_type" );
                   
                    addEmpAPICall = "finishedgoods";
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    
                    if( ! result2.contains( "not found" )){
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            JSONObject jObject = new JSONObject( result2 );
                            Iterator<?> keys = jObject.keys();

                            while( keys.hasNext() ){
                                String key = (String)keys.next();
                                Object value = jObject.get ( key ) ; 
                                map.put(key, value);
                            }

                            JSONObject st = (JSONObject) map.get ( "data" );
                            JSONArray records = st.getJSONArray ( "records");

                            JSONObject emp = null;
                            products = new ArrayList<ProductDR> ();
                            for ( int i = 0 ; i < records.length () ; i ++ ) {

                                emp = records.getJSONObject ( i);
                                jComboBox1.addItem ( emp.get ( "FG_PART_NO" ).toString () );
                                products.add( new ProductDR ( Integer.parseInt(emp.get ( "FG_ID" ).toString ()), emp.get ( "PART_NAME" ).toString () ,  emp.get ( "FG_UOM_ID" ).toString ()   )); 
                            }
                    
                        jLabel6.setText(  StaticValues.getUOMName (  Integer.parseInt( products.get(jComboBox1.getSelectedIndex() ).UOM) ));
                        jComboBox1.addActionListener ( productComboAction );
                        
                    }
                     
                }
      
         if ( jComboBox2.getItemCount () == 0 ) {
                    //result = DB_Operations.getMasters ( "raw_material_type" );
                   
                    addEmpAPICall = "processes";
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    
                    if( ! result2.contains( "not found" )){
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        JSONObject jObject = new JSONObject( result2 );
                        Iterator<?> keys = jObject.keys();

                        while( keys.hasNext() ){
                            String key = (String)keys.next();
                            Object value = jObject.get ( key ) ; 
                            map.put(key, value);
                        }

                        JSONObject st = (JSONObject) map.get ( "data" );
                        JSONArray records = st.getJSONArray ( "records");

                        JSONObject emp = null;
                        processes = new ArrayList<ProcessDR> ();
                        for ( int i = 0 ; i < records.length () ; i ++ ) {

                            emp = records.getJSONObject ( i);
                            jComboBox2.addItem ( emp.get ( "PROCESS_NAME" ).toString () );
                            processes.add( new ProcessDR ( Integer.parseInt(emp.get ( "PROCESS_ID" ).toString ()), emp.get ( "PROCESS_NAME" ).toString ()   )); 
                        }
                    }
                }
        
          if ( jComboBox3.getItemCount () == 0 ) {
                    //result = DB_Operations.getMasters ( "raw_material_type" );
                   
                    //addEmpAPICall = "machines";
                    addEmpAPICall = "machinestypes";
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    
                    if( ! result2.contains( "not found" )){
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            JSONObject jObject = new JSONObject( result2 );
                            Iterator<?> keys = jObject.keys();

                            while( keys.hasNext() ){
                                String key = (String)keys.next();
                                Object value = jObject.get ( key ) ; 
                                map.put(key, value);
                            }

                            JSONObject st = (JSONObject) map.get ( "data" );
                            JSONArray records = st.getJSONArray ( "records");

                            JSONObject emp = null;
                            machines = new ArrayList<MachineDR> ();
                            for ( int i = 0 ; i < records.length () ; i ++ ) {

                                emp = records.getJSONObject ( i);
//                                jComboBox3.addItem ( emp.get ( "MACHINE_NO" ).toString () );
//                                machines.add( new MachineDR ( Integer.parseInt(emp.get ( "MCH_ID" ).toString ()), emp.get ( "MACHINE_NO" ).toString ()   )); 
                                jComboBox3.addItem ( emp.get ( "MC_TYPE" ).toString () );
                                machines.add( new MachineDR ( Integer.parseInt(emp.get ( "MC_TYPE_ID" ).toString ()), emp.get ( "MC_TYPE" ).toString ()   )); 
                            }
                    }
                }
          
           if ( jComboBox4.getItemCount () == 0 ) {
                    //result = DB_Operations.getMasters ( "raw_material_type" );
                   
                    addEmpAPICall = "die_tool";
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    
                    if( ! result2.contains( "not found" )){
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            JSONObject jObject = new JSONObject( result2 );
                            Iterator<?> keys = jObject.keys();

                            while( keys.hasNext() ){
                                String key = (String)keys.next();
                                Object value = jObject.get ( key ) ; 
                                map.put(key, value);
                            }

                            JSONObject st = (JSONObject) map.get ( "data" );
                            JSONArray records = st.getJSONArray ( "records");

                            JSONObject emp = null;
                            die = new ArrayList<DieDR> ();
                            for ( int i = 0 ; i < records.length () ; i ++ ) {

                                emp = records.getJSONObject ( i);
                                jComboBox4.addItem ( emp.get ( "die_tool_no" ).toString () );
                                die.add( new DieDR ( Integer.parseInt(emp.get ( "DT_ID" ).toString ()), emp.get ( "die_tool_no" ).toString (),Float.parseFloat(emp.get ( "cost_maintenance" ).toString ()))); 
                            }
                    }
                }
          
                
//                 if ( jComboBox4.getItemCount () == 0 ) {
//                    //result = DB_Operations.getMasters ( "raw_material_type" );
//                   
//                    addEmpAPICall = "die_tool";
//                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
//                    
//                    if( ! result2.contains( "not found" )){
//                            HashMap<String, Object> map = new HashMap<String, Object>();
//                            JSONObject jObject = new JSONObject( result2 );
//                            Iterator<?> keys = jObject.keys();
//
//                            while( keys.hasNext() ){
//                                String key = (String)keys.next();
//                                Object value = jObject.get ( key ) ; 
//                                map.put(key, value);
//                            }
//
//                            JSONObject st = (JSONObject) map.get ( "data" );
//                            JSONArray records = st.getJSONArray ( "records");
//
//                            JSONObject emp = null;
//                            die_tool = new ArrayList<DieToolDR> ();
//                            for ( int i = 0 ; i < records.length () ; i ++ ) {
//                                emp = records.getJSONObject ( i);
//                                jComboBox4.addItem ( emp.get ( "die_tool_no" ).toString () );
//                                die_tool.add( new DieToolDR ( Integer.parseInt(emp.get ( "DT_ID" ).toString ()), emp.get ( "die_tool_no" ).toString ()   )); 
//                            }
//                    }
//                }
          
          
                loadDefaultMap ();
         
         
//        try {
//            String getAllProcesses = "SELECT PROCESS_NAME, MACHINE_NO, MAKE, SETUP_TIME, IDEAL_PROCESS_TIME, REMARKS FROM FG_PROCESS_MACH_MAP, PROCESS_MASTER, machine WHERE FG_PROCESS_MACH_MAP.REF_PROCESS_ID  = PROCESS_MASTER.PROCESS_ID AND FG_PROCESS_MACH_MAP.REF_MCH_ID = machine.MCH_ID  AND FG_PROCESS_MACH_MAP.REF_FG_ID=" + selectedFGID;
//            pst = con.prepareStatement ( getAllProcesses );
//            rs = pst.executeQuery ();
//            jTable1.setModel ( buildTableModel ( rs ) );
//            rs.close ();
//        } catch ( SQLException ex ) {
//            JOptionPane.showMessageDialog ( null , "" + ex.getMessage () );
//            System.out.println ( "DB not Connected" );
//            StaticValues.writer.writeExcel (Processforfinishedgoods.class.getSimpleName () , Processforfinishedgoods.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }
    }

    public static DefaultTableModel buildTableModel ( ResultSet rs )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        int columnCount = metaData.getColumnCount ();
        for ( int column = 1 ; column <= columnCount ; column ++ ) {

            columnNames.add ( metaData.getColumnName ( column ) );
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        while ( rs.next () ) {
            Vector<Object> vector = new Vector<Object> ();

            for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {

                vector.add ( rs.getObject ( columnIndex ) );

            }
            data.add ( vector );
        }

        return new DefaultTableModel ( data , columnNames );

    }

    
    KeyListener k = new KeyListener () {
        @Override
        public void keyTyped ( KeyEvent e ) {
            
            char enter = e.getKeyChar ();
            String abc = Character.toString(enter );
            if (  ! ( Character.isDigit ( enter ) )  && ! ! abc.equals ( ".") ) {
                e.consume ();
            }
        }

        @Override
        public void keyPressed ( KeyEvent e ) {
            
        }

        @Override
        public void keyReleased ( KeyEvent e ) {
            
        }
    };

    FocusListener f2 = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {

        }

        @Override
        public void focusLost ( FocusEvent e ) {
            
            JTextField jcb = ( JTextField ) e.getSource ();
            String x = jcb.getText ().trim ();

            if ( x.equalsIgnoreCase ( "" ) ) {
            
            }

            try {
                int num = Integer.parseInt ( String.valueOf ( jcb.getText ().toString () ) );
                if ( num < 0 || num == 0) {
                    jcb.setText ( "" );
                }
            } catch ( NumberFormatException ex1 ) {
                jcb.setText ( x );
           //     StaticValues.writer.writeExcel (Processforfinishedgoods.class.getSimpleName () , Processforfinishedgoods.class.getSimpleName () , ex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
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
        jLabel8 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(972, 625));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jLabel1.setText("<html>Part Code\\No:</html>");
        add(jLabel1);
        jLabel1.setBounds(30, 30, 140, 20);

        jLabel2.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jLabel2.setText("Process :");
        add(jLabel2);
        jLabel2.setBounds(30, 70, 80, 20);

        jLabel3.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jLabel3.setText("Machine Type:");
        add(jLabel3);
        jLabel3.setBounds(30, 110, 80, 20);

        jLabel7.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jLabel7.setText("Remarks :");
        add(jLabel7);
        jLabel7.setBounds(670, 10, 70, 20);

        jComboBox1.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jComboBox1.setNextFocusableComponent(jComboBox2);
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        add(jComboBox1);
        jComboBox1.setBounds(120, 30, 213, 30);

        jComboBox2.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jComboBox2.setNextFocusableComponent(jComboBox3);
        jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox2MouseClicked(evt);
            }
        });
        add(jComboBox2);
        jComboBox2.setBounds(120, 70, 213, 30);

        jComboBox3.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jComboBox3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox3MouseClicked(evt);
            }
        });
        add(jComboBox3);
        jComboBox3.setBounds(120, 110, 213, 30);

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        add(jScrollPane2);
        jScrollPane2.setBounds(670, 30, 240, 90);

        jButton1.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jButton1.setText("Add");
        jButton1.setOpaque(false);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(650, 150, 130, 40);

        jButton2.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jButton2.setText("Update");
        jButton2.setEnabled(false);
        jButton2.setOpaque(false);
        add(jButton2);
        jButton2.setBounds(650, 190, 130, 40);

        jButton3.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jButton3.setText("Reset");
        jButton3.setOpaque(false);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(810, 190, 130, 40);

        jButton4.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jButton4.setText("Cancel");
        jButton4.setOpaque(false);
        add(jButton4);
        jButton4.setBounds(810, 230, 130, 40);

        jButton5.setFont(new java.awt.Font("Leelawadee", 0, 12)); // NOI18N
        jButton5.setText("Export to Excel");
        jButton5.setOpaque(false);
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        add(jButton5);
        jButton5.setBounds(650, 230, 130, 40);

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
        jTable1.setRowHeight(20);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);

        add(jScrollPane3);
        jScrollPane3.setBounds(0, 320, 970, 290);

        jButton6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton6.setText("Load Map");
        jButton6.setOpaque(false);
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        add(jButton6);
        jButton6.setBounds(810, 150, 130, 40);

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 51));
        jLabel27.setText("*");
        add(jLabel27);
        jLabel27.setBounds(20, 30, 10, 20);

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 0, 51));
        jLabel28.setText("*");
        add(jLabel28);
        jLabel28.setBounds(20, 70, 30, 20);

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 0, 51));
        jLabel29.setText("*");
        add(jLabel29);
        jLabel29.setBounds(20, 150, 30, 20);

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 0, 51));
        jLabel30.setText("*");
        add(jLabel30);
        jLabel30.setBounds(360, 210, 30, 20);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("Target output per shift");
        add(jLabel8);
        jLabel8.setBounds(370, 60, 180, 20);

        jTextField4.setNextFocusableComponent(jTextField5);
        add(jTextField4);
        jTextField4.setBounds(390, 30, 90, 30);

        jTextField5.setNextFocusableComponent(jTextField6);
        add(jTextField5);
        jTextField5.setBounds(390, 80, 90, 30);

        jTextField6.setNextFocusableComponent(jTextField7);
        add(jTextField6);
        jTextField6.setBounds(390, 130, 90, 30);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("Target output per hour");
        add(jLabel9);
        jLabel9.setBounds(370, 10, 180, 20);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setText("Target output per day");
        add(jLabel10);
        jLabel10.setBounds(370, 110, 140, 16);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("<html>Cost of the RM / IRM after this process</html>");
        add(jLabel11);
        jLabel11.setBounds(370, 160, 240, 20);

        jTextField7.setNextFocusableComponent(jTextField3);
        add(jTextField7);
        jTextField7.setBounds(390, 180, 90, 30);

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 0, 51));
        jLabel33.setText("*");
        add(jLabel33);
        jLabel33.setBounds(360, 10, 30, 20);

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 0, 51));
        jLabel34.setText("*");
        add(jLabel34);
        jLabel34.setBounds(360, 70, 30, 10);

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 0, 51));
        jLabel35.setText("*");
        add(jLabel35);
        jLabel35.setBounds(360, 110, 30, 20);

        jLabel36.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 0, 51));
        jLabel36.setText("*");
        add(jLabel36);
        jLabel36.setBounds(360, 160, 30, 20);

        jTextField3.setText("0");
        jTextField3.setNextFocusableComponent(jTextArea2);
        add(jTextField3);
        jTextField3.setBounds(390, 230, 90, 30);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("<html>Maximum permissible rejection/day</html>");
        add(jLabel12);
        jLabel12.setBounds(370, 210, 240, 20);
        add(jLabel6);
        jLabel6.setBounds(500, 30, 60, 30);

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 0, 51));
        jLabel31.setText("*");
        add(jLabel31);
        jLabel31.setBounds(20, 110, 30, 20);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Die / Tool");
        add(jLabel4);
        jLabel4.setBounds(30, 150, 80, 16);

        jComboBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jComboBox4);
        jComboBox4.setBounds(120, 150, 213, 30);

        jLabel20.setText("Param-name");
        add(jLabel20);
        jLabel20.setBounds(650, 10, 80, 0);

        jLabel22.setText("Bench-mark");
        add(jLabel22);
        jLabel22.setBounds(730, 10, 70, 0);

        jLabel23.setText("UOM");
        add(jLabel23);
        jLabel23.setBounds(810, 10, 30, 0);

        jLabel5.setText("LSL");
        add(jLabel5);
        jLabel5.setBounds(860, 10, 30, 0);

        jLabel13.setText("USL");
        add(jLabel13);
        jLabel13.setBounds(900, 10, 30, 0);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        resetFields ();
   //     qualitylist=new ProcessQualityList();
  //        qualitylist.setBounds ( 650 , 20 , 280 , 200 );
   //         add( qualitylist );
    }//GEN-LAST:event_jButton3MouseClicked

    public void resetFields () {
    //    jTextField1.setText ( "" );
   //     jTextField2.setText ( "" );
        jTextField4.setText ( "" );
        jTextField5.setText ( "" );
        jTextField6.setText ( "" );
        jTextField7.setText ( "" );
        jTextField3.setText ( "" );
        jTextArea2.setText ( "" );
    }

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:

        //4     5     6
        //6     8     9
        int targetOutputPerHour = Integer.parseInt ( jTextField4.getText ().trim () );
        int targetOutputPerShift = Integer.parseInt ( jTextField5.getText ().trim () );
        int targetOutputPerDay = Integer.parseInt ( jTextField6.getText ().trim () );

       // int selectedUOM = Integer.parseInt ( uom4.get ( jComboBox6.getSelectedIndex () )[ 0 ] );

        float val_at_process = Float.parseFloat ( jTextField7.getText ().trim () );

        int max_perm_rejection = Integer.parseInt ( jTextField3.getText ().trim () );

        String query;
//        try {

            String setuptime = "", stdtime, outperhour, remarks;

//            if (  ! jTextField1.getText ().equals ( "" ) ) {
//                setuptime = jTextField1.getText ();
//            } else {
//                setuptime = "";   //            }
//

                
                setuptime = setupTime.getSelectedTime ()+"";
                int sec = Integer.parseInt( setuptime );
                
                setuptime = new DecimalFormat("#.##").format(sec/60.0) ;
                

//            if (  ! jTextField2.getText ().equals ( "" ) ) {
//                stdtime = jTextField2.getText ();
//            } else {
//                stdtime = "";
//            }

                stdtime = cycleTime.getSelectedTime ()+"" ;
                sec = Integer.parseInt( stdtime );
                stdtime = new DecimalFormat("#.##").format(sec/60.0) ;

                
                
                
            if (  ! jTextField4.getText ().equals ( "" ) ) {
                outperhour = jTextField4.getText ();
            } else {
                outperhour = "";
            }

            if (  ! jTextArea2.getText ().equals ( "" ) ) {
                remarks = jTextArea2.getText ().trim ();
            } else {
                remarks = "";
            }

            if (  ! setuptime.equals ( "" ) &&  ! stdtime.equals ( "" ) &&  ! outperhour.equals ( "" ) ) {

//                PreparedStatement pst = DB_Operations.getPreparedStatement ().prepareStatement ( "INSERT INTO FG_PROCESS_MACH_MAP (  'REF_FG_ID',   'REF_PROCESS_ID',   'REF_MCH_ID',   'SETUP_TIME' , 'IDEAL_PROCESS_TIME' , 'REMARKS', 'TG_OPT_HR' , 'TG_OPT_SHT',  'TG_OPT_DAY',   'VAL_AT_PROC' , 'MAX_REJ_PERM') VALUES ( ?,?,?,?,?,?,?,?,?,?,? )" );
//                pst.setString ( 1 , "" + products.get ( jComboBox1.getSelectedIndex () ).FG_ID );
//                pst.setString ( 2 , "" + processes.get ( jComboBox2.getSelectedIndex () ).PRC_ID );
//                pst.setString ( 3 , "" + machines.get ( jComboBox3.getSelectedIndex () ).MC_ID );
//                pst.setString ( 4 , setuptime );
//                pst.setString ( 5 , stdtime );
//                pst.setString ( 6 , remarks );
//                pst.setInt ( 7 , targetOutputPerHour );
//                pst.setInt ( 8 , targetOutputPerShift );
//                pst.setInt ( 9 , targetOutputPerDay );
//                pst.setFloat ( 10 , val_at_process );
//                pst.setFloat ( 11 , max_perm_rejection );
//
//                pst.executeUpdate ();


                selecteddie_ID  = (die.get ( jComboBox4.getSelectedIndex () ).DIE_ID);
//                ArrayList<Main_typeModel_quality> listLllUll =  qualitylist.getUllLLL( );    
                try{
                        String addEmpAPICall ;
                    
                        addEmpAPICall = "processmachmapadd?REF_FG_ID="+URLEncoder.encode((""+ products.get ( jComboBox1.getSelectedIndex () ).FG_ID ), "UTF-8")
                                +"&REF_PROCESS_ID="+URLEncoder.encode(("" + processes.get ( jComboBox2.getSelectedIndex ()).PRC_ID),"UTF-8")+"&REF_MCH_ID="+URLEncoder.encode(("" + machines.get (jComboBox3.getSelectedIndex()).MC_ID), "UTF-8")
                                +"&SETUP_TIME="+URLEncoder.encode(setuptime,"UTF-8")+"&IDEAL_PROCESS_TIME="+URLEncoder.encode(stdtime,"UTF-8")+"&REMARKS="+URLEncoder.encode(remarks, "UTF-8")
                                +"&TG_OPT_HR="+URLEncoder.encode(targetOutputPerHour+"","UTF-8")+"&TG_OPT_SHT="+URLEncoder.encode(targetOutputPerShift+"" ,"UTF-8")+"&TG_OPT_DAY="+URLEncoder.encode(targetOutputPerDay+"", "UTF-8")
                                +"&UOM_ID="+URLEncoder.encode( products.get ( jComboBox1.getSelectedIndex () ).UOM ,"UTF-8")+"&VAL_AT_PROC="+URLEncoder.encode((val_at_process+""),"UTF-8")
                                +"&MAX_REJ_PERM="+URLEncoder.encode((max_perm_rejection+""), "UTF-8")  +"&REF_TOOL_CODE="+URLEncoder.encode(selecteddie_ID +""  ,"UTF-8")  ;
                        String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                        JOptionPane.showMessageDialog ( null , result2);
                        //**********Created by harshali 
                        //********* Process Quality Master Entry
                /*        if(result2.contains("Process Mach Map created successfully"))
                        {
                        JSONObject jb=new JSONObject(result2);
                        String status=jb.getString("status");
                        JSONObject jsb=new JSONObject(result2);
                        JSONObject js=jsb.getJSONObject("data");
                        int s=js.getInt("insert_id");
                        if(status.contains("success"))
                        {
                           if(listLllUll.size()!=0)
                           {
                                for(  int j=0 ;  j<listLllUll.size(); j++ )
                                {
                                    if((listLllUll.get(j).getLll())!=0 && (listLllUll.get(j).getUll())!=0 )
                                  {
                                      int id=listLllUll.get(j).getId();
                                      Float std_mark=listLllUll.get(j).getStd_mark();
                                      float Ll=listLllUll.get(j).getLll();
                                      float ul=listLllUll.get(j).getUll();
                                      String name=listLllUll.get(j).getName();
                                      int uom_id = listLllUll.get(j).getUom();
                                  String EmpAPICall=null;
                                  try{ 
                                  EmpAPICall = "proc_qty_master_add?ref_proc_id="+URLEncoder.encode(""+s, "UTF-8")+"&proc_param_name="+URLEncoder.encode(id+"", "UTF-8")+"&proc_p_mandatory="+URLEncoder.encode(""+1, "UTF-8")+"&standard_value="+URLEncoder.encode(std_mark+"", "UTF-8")+"&uom_id="+URLEncoder.encode(uom_id+"", "UTF-8")+"&LLL="+URLEncoder.encode( Ll+"", "UTF-8")+"&ULL="+URLEncoder.encode( ul+"", "UTF-8");
                                 String result1 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
              
                                   JOptionPane.showMessageDialog ( null , result1 );
                                  }catch(Exception e)
                                  {
                                      e.getMessage();
                                  }
                                  }
                         
                                }
                           }
                        }
                        }*/
                      //***************************
                        //value in text value+=strokes daily submit 
                        //addEmpAPICall = "die_tool_update?DT_ID="+URLEncoder.encode((""+ die.get ( jComboBox1.getSelectedIndex () ).DT_ID ), "UTF-8")+"&value in every time increase="+URLEncoder.encode((value+=strokes.PRC_ID),"UTF-8");//
                        //String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                        //JOptionPane.showMessageDialog ( null , result2);
                    }catch(UnsupportedEncodingException e){
                    e.getMessage();
                    }
                
                
                //loadContent ();
                resetFields ();
//                qualitylist.reset();
                JOptionPane.showMessageDialog ( null , "Information Saved Succesfull!!" );
                loadDefaultMap ();
                
            } else {
                JOptionPane.showMessageDialog ( null , "Please fill all fields marked with *" );
            }
            

//        } catch ( SQLException e ) {
//            JOptionPane.showMessageDialog ( null , e.getMessage () , "Message" , JOptionPane.WARNING_MESSAGE );
//            System.out.println ( e.getMessage () );
//            StaticValues.writer.writeExcel (Processforfinishedgoods.class.getSimpleName () , Processforfinishedgoods.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        } finally {
//           
//        }
    }//GEN-LAST:event_jButton1MouseClicked

  
    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        if ( jComboBox1.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add values in FinishedGoods from FinishedGoods Master" );
        }else{
            jLabel6.setText(  products.get(jComboBox1.getSelectedIndex() ).UOM);
        }
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        // TODO add your handling code here:
        loadDefaultMap ();
    }//GEN-LAST:event_jButton6MouseClicked

    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
        if ( jComboBox2.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add values in process from Process Master" );
        }            // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2MouseClicked

    private void jComboBox3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox3MouseClicked
        if ( jComboBox3.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add values in Machine from Finished Good Master" );
        }            // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3MouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        // TODO add your handling code here:
        showDialog();
//                Workbook wb = new HSSFWorkbook ();
//
//        ArrayList<String> column_names = new ArrayList<String> ();
//        ArrayList<String[]> values = new ArrayList<String[]> ();
//        int columnCount = 0;
//
//        File file = null;
//
//        try {
//            ResultSet result = DB_Operations.executeSingle ( "select * from FG_PROCESS_MACH_MAP" ) ;
//            if ( result != null ) {
//                ResultSetMetaData metaData = result.getMetaData ();
//                // names of columns
//                columnCount = metaData.getColumnCount ();
//                for ( int column = 1 ; column <= columnCount  ; column ++ ) {
//                    column_names.add ( metaData.getColumnName ( column ) );
//                }
//            }
//            // data of the table
//            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
//            while ( result.next () ) {
//                String[] val = new String[ columnCount ];
//                for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {
//                   
//                        val[ columnIndex-1 ] = String.valueOf ( result.getObject ( columnIndex ) );
//                    
//                }
//                values.add ( val );
//            }
//        } catch ( SQLException e ) {
//            StaticValues.writer.writeExcel (FinishedGoodMaster.class.getSimpleName () , FinishedGoodMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
//        }
//
//        // Cell style for header row
//        CellStyle cs = wb.createCellStyle ();
//        //cs.setFillForegroundColor(HSSFColor.LIME.index);
//        //cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//
//        CellStyle cs2 = wb.createCellStyle ();
//        cs2.setFillForegroundColor ( HSSFColor.ROSE.index );
//        cs2.setFillPattern ( HSSFCellStyle.SOLID_FOREGROUND );
//
//        // New Sheet
//        Sheet sheet1 = null;
//        sheet1 = wb.createSheet ( "Product-Process-Machine Master" );
//        sheet1.setColumnWidth ( 0 , ( 15 * 500 ) );
//
//        Cell c = null;
//        Row row = sheet1.createRow ( 1 );
//        
//        int i = 1;
//        //add column names to the firt row of excel
//        row = sheet1.createRow ( i ++ );
//        for ( int a = 0 ; a < column_names.size () ; a ++ ) {
//            c = row.createCell ( a );
//            // c.setCellValue(cursor.getString(a));
//            c.setCellValue ( column_names.get ( a ) );
//            c.setCellStyle ( cs );
//        }
//
//        for ( int j = 0 ; j < values.size () ; j ++ ) {
//            String[] rowValues = values.get ( j );
//            Row row4 = sheet1.createRow ( i ++ );
//            for ( int k = 0 ; k < rowValues.length ; k ++ ) {
//                {
//
//                    c = row4.createCell ( k );
//                    c.setCellValue ( rowValues[ k ] );
//                    c.setCellStyle ( cs );
//                }
//            }
//            Calendar c2 = Calendar.getInstance ();
//            SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
//            String strDate2 = sdf2.format ( c2.getTime () );
//            file = new File ( "Product-Process-Machine Master File " + strDate2 + ".xls" );
//
//            FileOutputStream os = null;
//
//            try {
//                os = new FileOutputStream ( file );
//                wb.write ( os );
//                wb.close ();
//                os.close ();
//
//            } catch ( IOException e ) {
//                JOptionPane.showMessageDialog ( null , "Error " + e.getMessage () );
//                StaticValues.writer.writeExcel (FinishedGoodMaster.class.getSimpleName () , FinishedGoodMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
//            } finally {
//
//            }
//        }
//        JOptionPane.showMessageDialog ( null , "New File generated at " + file.getAbsolutePath () );
        
    }//GEN-LAST:event_jButton5MouseClicked

    private void jComboBox4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox4MouseClicked
         if ( jComboBox4.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add values in Die from DIE Master" );
        } // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4MouseClicked

        int selectedPMMRecordId = 0;
    
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        
       // jButton7.setEnabled(true) ;
        
        DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();
        int selectedRowIndex = jTable1.getSelectedRow ();     
        selectedPMMRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 13 ).toString () );
        
    }//GEN-LAST:event_jTable1MouseClicked

    public  void deleteMaster( String apiName  ){
        String response  = WebAPITester.prepareWebCall ( apiName,  StaticValues.getHeader () , "");
        JSONObject responseObject = ( JSONObject ) new JSONTokener ( response ).nextValue ();
        JOptionPane.showMessageDialog ( null , retreiveSuccessMessage ( responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () ) );
    }
    
    public static String retreiveSuccessMessage ( String json ) {
        String id = "Not able to fetch the response for your request. Please refresh you master form and try again";
        JSONObject jObject = new JSONObject ( json );
        id = jObject.getString ( "118" );
        return id;
    }
    
    ActionListener productComboAction = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
            jLabel6.setText(  StaticValues.getUOMName ( Integer.parseInt(  products.get(jComboBox1.getSelectedIndex() ).UOM  )) );
         loadDefaultMap ();
        }
    };
    
    public void loadDefaultMap () {

        if ( jComboBox1.getItemCount () > 0 ) {
            selectedFGID = products.get ( jComboBox1.getSelectedIndex () ).FG_ID;
            jLabel6.setText(  StaticValues.getUOMName ( Integer.parseInt(  products.get(jComboBox1.getSelectedIndex() ).UOM  ) ));
            
            String addEmpAPICall = "processmachmaps";
            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

            if ( result2 != null && ! result2.contains( "not found" )) {
                jTable1.setModel ( buildTableModelfromJSON ( result2 ) );
                jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                jTable1.getColumnModel ().getColumn ( 13 ).setMinWidth ( 0 );
                jTable1.getColumnModel ().getColumn ( 13 ).setMaxWidth ( 0 );
                
            }
        }
        
       // selectedFGID = 1;
        
    }
    
    public  DefaultTableModel buildTableModelfromJSON ( String employeeJSONList ) {

        Vector<String> columnNames = new Vector<String> ();
        Vector<String> tableHeader = new Vector<String> ();
        
        int columnCount = 0;
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();

        JSONArray jARR = null;
        JSONObject jOB = null;

        HashMap<String , Object> map = new HashMap<String , Object> ();
        JSONObject jObject = new JSONObject ( employeeJSONList );
        Iterator<?> keys = jObject.keys ();

        while ( keys.hasNext () ) {
            String key = ( String ) keys.next ();
            Object value = jObject.get ( key );
            map.put ( key , value );
        }

        JSONObject st = ( JSONObject ) map.get ( "data" );
        JSONArray records = st.getJSONArray ( "records" );

        JSONObject emp = null;

        columnNames = new Vector<String> ();
        tableHeader = new Vector<String> ();
        
        String fgTitle, processTitle, machineTitle ;
        
        /* ArrayList<ProductDR> products = null;
            ArrayList<ProcessDR> processes = null;
            ArrayList<MachineDR> machines = null;*/
        
        
        columnNames.add ( "REF_FG_ID" );
        columnNames.add ( "REF_PROCESS_ID" );
        columnNames.add ( "REF_MCH_ID" );
        columnNames.add ("REF_TOOL_CODE");
        columnNames.add ( "SETUP_TIME" );
        columnNames.add ( "IDEAL_PROCESS_TIME" );
        columnNames.add ( "REMARKS" );
        columnNames.add ( "TG_OPT_HR" );
        columnNames.add ( "TG_OPT_SHT" );
        columnNames.add ( "TG_OPT_DAY" );
        columnNames.add ( "UOM_ID" );
        columnNames.add ( "VAL_AT_PROC" );
        columnNames.add ( "MAX_REJ_PERM" );
        columnNames.add ( "FPM_ID" );
        
        tableHeader.add ( "Part" );
        tableHeader.add ( "Process" );
        tableHeader.add ( "Machine" );
        tableHeader.add ( "Die/Tool Code" );
        tableHeader.add ( "Setup Time" );
        tableHeader.add ( "Ideal Cycle Time" );
        tableHeader.add ( "Remarks" );
        tableHeader.add ( "Tg O/P per Hr" );
        tableHeader.add ( "Tg O/P per Shift" );
        tableHeader.add ( "Tg O/P per Day" );
        tableHeader.add ( "Part unit" );
        tableHeader.add ( "<html>Effective value of<br>Part at this process</html>" );
        tableHeader.add ( "<html>Maximum permissible rejection<br>in a day</html>" );
        tableHeader.add ( "ID" );
        
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
             boolean fgmatched = false ;
             
            Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {
                emp = records.getJSONObject ( i );
                
                fgmatched = false ;
                try{
                    if( selectedFGID ==  Integer.parseInt( emp.get ( columnNames.get ( 0 ) ).toString()  )   ){
                        fgmatched = true ;
                    }
                }catch( NumberFormatException e ){
                    
                }
                
                if( fgmatched ){
                    if( columnIndex == 0){
                        for( int j=0; j<products.size ();j++){
                                if( products.get ( j).FG_ID == Integer.parseInt( emp.get ( columnNames.get ( columnIndex ) ).toString() )){
                                    vector.add ( products.get ( j).FG_PART_NO );
                                }
                        }
                    }else if( columnIndex == 1){
                        for( int k=0; k<processes.size ();k++){
                            if( processes.get ( k).PRC_ID == Integer.parseInt( emp.get ( columnNames.get ( columnIndex ) ).toString() )){
                                vector.add ( processes.get ( k).PRC_NAME );
                            }
                        }
                    } else if( columnIndex == 2){
                        for( int k=0; k<machines.size ();k++){
                            if( machines.get ( k).MC_ID == Integer.parseInt( emp.get ( columnNames.get ( columnIndex ) ).toString() )){
                                vector.add ( machines.get ( k).MC_NAME );
                            }
                        }
                    }else if( columnIndex == 3){
                        for( int k=0; k<die.size ();k++){
                            if( die.get ( k).DIE_ID == Integer.parseInt( emp.get ( columnNames.get ( columnIndex ) ).toString() )){
                                vector.add ( die.get ( k).die_tool_no );
                            }
                        }
                    }
                    else if( columnIndex == 10){
                        
                        vector.add ( StaticValues.getUOMName ( Integer.parseInt( emp.get ( columnNames.get ( columnIndex ) ).toString() ) ) );
                        
                    }else{

                        vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                    }
                }
            }
             if( fgmatched ){
                data.add ( vector );
             }
        }
                    
                    
                    
        return new DefaultTableModel ( data , tableHeader );
    }
     
    public void loadDefaultMap_OLD () {

        if ( jComboBox1.getItemCount () > 0 ) {
            selectedFGID = products.get ( jComboBox1.getSelectedIndex () ).FG_ID;
             jLabel6.setText(  products.get(jComboBox1.getSelectedIndex() ).UOM);
            try {

                String getAllProcesses = "SELECT PROCESS_NAME, MACHINE_NO, MAKE, SETUP_TIME, IDEAL_PROCESS_TIME, REMARKS FROM FG_PROCESS_MACH_MAP, PROCESS_MASTER, machine WHERE FG_PROCESS_MACH_MAP.REF_PROCESS_ID  = PROCESS_MASTER.PROCESS_ID AND FG_PROCESS_MACH_MAP.REF_MCH_ID = machine.MCH_ID  AND FG_PROCESS_MACH_MAP.REF_FG_ID=" + selectedFGID;
                rs = DB_Operations.executeSingle ( getAllProcesses );
                jTable1.setModel ( buildTableModel ( rs ) );
                rs.close ();

                
                
            } catch ( SQLException ex ) {
                JOptionPane.showMessageDialog ( null , "" + ex.getMessage () );
                System.out.println ( "DB not Connected" );
                StaticValues.writer.writeExcel (Processforfinishedgoods.class.getSimpleName () , Processforfinishedgoods.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

        }
    }
    
    /*************************************Export data to excel Edited by mayur***************************************/

     ProgressDialog progress = new ProgressDialog( null ) ;
    Thread getTableAndFilesContent = null ;
    public boolean showDialog(){
        getTableAndFilesContent = new Thread(){
            public void run(){
               
                exportDataExecel();
            }
        };
        getTableAndFilesContent.start();
        progress.openProgressWindow ();
        return true ;
    }
    
    public boolean hideDialog(){
        progress.closeProgressWindow ();
        return true ;
    } 
    
    
    public void exportDataExecel() {
        progress.updateTitle("All Product Machine Map data is fetching please wait...");
        progress.updateMessage("Getting all Product map data");

        String FPM_ID = "", REF_FG_ID = "", REF_PROCESS_ID = "", REF_MCH_ID = "", SETUP_TIME = "", IDEAL_PROCESS_TIME = "", REMARKS = "", TG_OPT_HR = "", TG_OPT_SHT = "", TG_OPT_DAY = "", UOM_ID = "", VAL_AT_PROC = "", MAX_REJ_PERM = "", REF_TOOL_CODE = "", PROCESS_NAME = "";

        String FG_ID = "", FG_PART_NO = "",mcname="",processname="",temp="",die_tool="",remarks="";
        
        
        int l = 2;

        //Map<String, Object[]> data11;
        //data11 = new TreeMap<String, Object[]>();
        
        mcmaps = new ArrayList<>();
                
                if(mcmaps.size()>0)
                {
                    mcmaps.clear();
                }

        String[] columns = {"PART NAME", "PROCESS NAME", "MACHINE NAME", "DIE TOOL NO", "SETUP TIME", "CYCLE TIME", "TARGET OUTPUT PER HOUR","TARGET OUTPUT PER SHIFT","TARGET OUTPUT PER DAY","COST OF RAW MATERIAL","MAXIMUM PERMISSIBLE REJECTION/DAY","REMARKS"};
        //data11.put("1", new Object[]{"PART NAME", "PROCESS NAME", "MACHINE NAME", "DIE TOOL NO", "SETUP TIME", "CYCLE TIME", "TARGET OUTPUT PER HOUR","TARGET OUTPUT PER SHIFT","TARGET OUTPUT PER DAY","COST OF RAW MATERIAL","MAXIMUM PERMISSIBLE REJECTION/DAY","REMARKS"});
        String result2, addEmpAPICall;

        addEmpAPICall = "finishedgoods";
        //processmachmaps?FG_ID=1

        result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

        if (!result2.contains("not found")) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            JSONObject jObject = new JSONObject(result2);
            Iterator<?> keys = jObject.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                Object value = jObject.get(key);
                map.put(key, value);
            }

            JSONObject st = (JSONObject) map.get("data");
            JSONArray records = st.getJSONArray("records");

            JSONObject emp = null;
            for (int i = 0; i < records.length(); i++) {

                emp = records.getJSONObject(i);
                //jComboBox1.addItem ( emp.get ( "die_tool_no" ).toString () );
                //CUSTOMER_ID=emp.get("CUSTOMER_ID").toString();

                FG_ID = emp.get("FG_ID").toString();
                FG_PART_NO = emp.get("FG_PART_NO").toString();

                String EmpAPICall = "processmachmaps?FG_ID=" + FG_ID;
                String tab = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");
                
                //data11.put("" + l, new Object[]{FG_PART_NO,temp,temp,temp,temp,temp,temp,temp,temp,temp,temp,temp});
                mcmaps.add(new processMachineMapsExcelData(FG_PART_NO,temp,temp,temp,temp,temp,temp,temp,temp,temp,temp,temp));
                l+=1;
                
                if (!tab.contains("not found")) {
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    JSONObject jObject1 = new JSONObject(tab);
                    Iterator<?> keys1 = jObject1.keys();

                    while (keys1.hasNext()) {
                        String key1 = (String) keys1.next();
                        Object value1 = jObject1.get(key1);
                        map1.put(key1, value1);
                    }

                    JSONObject st1 = (JSONObject) map1.get("data");
                    JSONArray records1 = st1.getJSONArray("records");

                    JSONObject emp1 = null;

                    for (int j = 0; j < records1.length(); j++) {

                        emp1 = records1.getJSONObject(j);

                        //FPM_ID = emp.get("FPM_ID").toString();
                        //REF_FG_ID = emp.get("REF_FG_ID").toString();
                        REF_PROCESS_ID = emp1.get("REF_PROCESS_ID").toString();
                        REF_MCH_ID = emp1.get("REF_MCH_ID").toString();
                        mcname=machineName(REF_MCH_ID);
                        SETUP_TIME = emp1.get("SETUP_TIME").toString();
                        IDEAL_PROCESS_TIME = emp1.get("IDEAL_PROCESS_TIME").toString();
                        REMARKS = emp1.get("REMARKS").toString();
                        TG_OPT_HR = emp1.get("TG_OPT_HR").toString();
                        TG_OPT_SHT = emp1.get("TG_OPT_SHT").toString();
                        TG_OPT_DAY = emp1.get("TG_OPT_DAY").toString();
                        UOM_ID = emp1.get("UOM_ID").toString();
                        VAL_AT_PROC = emp1.get("VAL_AT_PROC").toString();
                        MAX_REJ_PERM = emp1.get("MAX_REJ_PERM").toString();
                        REF_TOOL_CODE = emp1.get("REF_TOOL_CODE").toString();
                        die_tool=dieName(REF_TOOL_CODE);
                        PROCESS_NAME = emp1.get("PROCESS_NAME").toString();
                        remarks=emp1.get("REMARKS").toString();
                        //data11.put("" + l, new Object[]{temp,PROCESS_NAME,mcname,die_tool,SETUP_TIME,IDEAL_PROCESS_TIME,TG_OPT_HR,TG_OPT_SHT,TG_OPT_DAY,VAL_AT_PROC,MAX_REJ_PERM,remarks});
                        mcmaps.add(new processMachineMapsExcelData(temp,PROCESS_NAME,mcname,die_tool,SETUP_TIME,IDEAL_PROCESS_TIME,TG_OPT_HR,TG_OPT_SHT,TG_OPT_DAY,VAL_AT_PROC,MAX_REJ_PERM,remarks));
                        l+=1;
                    }
                }
                 //data11.put("" + l, new Object[]{temp,temp,temp,temp,temp,temp,temp,temp,temp,temp,temp,temp});
                mcmaps.add(new processMachineMapsExcelData(temp,temp,temp,temp,temp,temp,temp,temp,temp,temp,temp,temp));
                l = l + 1;
            }
        }

        hideDialog();

        //blank workbook
                Workbook workbook = new HSSFWorkbook();

                //Create a blank sheet
                HSSFSheet sheet = (HSSFSheet) workbook.createSheet("Process machine map master data");
        
                 // Create a Row
                Row headerRow = sheet.createRow(0);
                
                CellStyle style = workbook.createCellStyle();
                style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
                style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                Font font = workbook.createFont();
                font.setColor(IndexedColors.AUTOMATIC.getIndex());
                style.setFont(font);
                
                // Create cells
                for (int i = 0; i < columns.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columns[i]);
                    //cell.setCellStyle(headerCellStyle);
                }

                // Create Other rows and cells with employees data
                int rowNum = 1;
                for (processMachineMapsExcelData exelData : mcmaps) {
                    Row row = sheet.createRow(rowNum++);

                    row.createCell(0)
                            .setCellValue(exelData.getFG_PART_NO()); 
                    
                    row.createCell(1)
                            .setCellValue(exelData.getPROCESS_NAME());

                    row.createCell(2)
                            .setCellValue(exelData.getMcname());

                    row.createCell(3)
                            .setCellValue(exelData.getDie_tool());
                    row.createCell(4)
                            .setCellValue(exelData.getSETUP_TIME());
                    row.createCell(5)
                            .setCellValue(exelData.getIDEAL_PROCESS_TIME());
                    row.createCell(6)
                            .setCellValue(exelData.getTG_OPT_HR());
                    row.createCell(7)
                            .setCellValue(exelData.getTG_OPT_SHT());
                    row.createCell(8)
                            .setCellValue(exelData.getTG_OPT_DAY());
                    row.createCell(9)
                            .setCellValue(exelData.getVAL_AT_PROC());
                    row.createCell(10)
                            .setCellValue(exelData.getMAX_REJ_PERM());
                    row.createCell(11)
                            .setCellValue(exelData.getREMARKS());
                    
                }

                // Resize all columns to fit the content size
                for (int i = 0; i < columns.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                File file = null;
                try {

                    Calendar c2 = Calendar.getInstance();
                    SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMMyyyy_HH_mm_a");
                    String strDate2 = sdf2.format(c2.getTime());

                    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    jfc.setDialogTitle("Choose a directory to save your file: ");
                    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    int returnValue = jfc.showSaveDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        if (jfc.getSelectedFile().isDirectory()) {
                            System.out.println("You selected the directory: " + jfc.getSelectedFile());
                        }
                    

                    //Write the workbook in file system
                    file = new File(jfc.getSelectedFile() + "\\" + "Process machine map Data  " + strDate2 + ".xls");
                    FileOutputStream fileop = new FileOutputStream(file);
                    workbook.write(fileop);
                    workbook.close();
                    fileop.close();
                    JOptionPane.showMessageDialog(null, "New File generated at " + file.getAbsolutePath());
                    
                }
                    //System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
                } catch (IOException Ie) {
                    Ie.printStackTrace();
                }

                

    }
    
    public String machineName(String id) {
        String mcname = "";

        String EmpAPICall = "machineedit?MCH_ID=" + id;
        String tab = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

        if (!tab.contains("not found")) {
           

            JSONObject jsobje = new JSONObject(tab);
            String page11 = jsobje.getJSONArray("data").toString();
            JSONArray machinejsarray = new JSONArray(page11);

            for (int j = 0; j < machinejsarray.length(); j++) {

                JSONObject jsonobject = machinejsarray.getJSONObject(j);

                mcname = jsonobject.optString("MACHINE_NO");
            }
           
        }
         return mcname;
    }
    
    public String dieName(String id) {
        String diename = "";

        String EmpAPICall = "die_tool_edit?DT_ID=" + id;
        String tab = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

        if (!tab.contains("not found")) {
            
            JSONObject jsobje = new JSONObject(tab);
            String page11 = jsobje.getJSONArray("data").toString();
            JSONArray machinejsarray = new JSONArray(page11);

            for (int j = 0; j < machinejsarray.length(); j++) {

                JSONObject jsonobject = machinejsarray.getJSONObject(j);

                diename = jsonobject.optString("die_tool_no");
            }
           
        }
         return diename;
    }
   
    /****************************************************************************/


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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables

    public int selectedFGID = 0;
///***********************Created by Harshali Process Quality Master
    ArrayList<Main_typeModel_quality> selectedparam = new ArrayList<Main_typeModel_quality> ();

    class ProcessQualityList extends javax.swing.JPanel {

        ResultSet result = null;
       
        public ArrayList<QualityPanel> paramList = null;
        private ArrayList<Main_typeModel_quality> paramDetailList = new ArrayList<Main_typeModel_quality> ();
    public ArrayList<QualityPanel> paramList1 = null;
        private ArrayList<Main_typeModel_quality> paramDetailList1 = new ArrayList<Main_typeModel_quality> ();

        /**
         * Creates new form ProdDataConformationScreen
         */
        public ProcessQualityList () {
            initComponents ();

            ParamList panel = new ParamList ();
            panel.setBounds ( 0 , 0 , 300 , 200 );
            panel.setBackground ( Color.white );
            add ( panel );

        }
        
        @SuppressWarnings( "unchecked" )
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents () {

            jButton1 = new javax.swing.JButton ();
            jButton2 = new javax.swing.JButton ();
            jLabel1 = new javax.swing.JLabel ();

            setBackground ( new java.awt.Color ( 255 , 255 , 255 ) );
            setBorder ( javax.swing.BorderFactory.createTitledBorder ( "" ) );
            setPreferredSize ( new java.awt.Dimension ( 300 , 200 ) );
            setLayout ( null );

        }// </editor-fold>                        

        public ArrayList<Main_typeModel_quality> getUllLLL( ) 
          {
            // TODO add your handling code here:
            Main_typeModel_quality tlm = null;

            
            paramDetailList = new ArrayList<Main_typeModel_quality> () ;
            for ( int i = 0 ; i < paramList.size () ; i ++ ) 
            {

                tlm = new Main_typeModel_quality();
                if ( paramList.get ( i ).getUll() > 0.0 && paramList.get ( i ).getLll() >00) 
                {
                    tlm.setId(paramList.get ( i ).getId());
                    tlm.setName(paramList.get ( i ).getName());
                    tlm.setStd_mark(paramList.get(i).getStandard_mark());
                    tlm.setLll(paramList.get ( i ).getLll());
                    tlm.setUll(paramList.get ( i ).getUll());
                    tlm.setUom(paramList.get(i).getUom());
                    paramDetailList.add ( tlm );
                }
            }
            
            
                Main_typeModel_quality tlm2 = null;
                StringBuilder sb = new StringBuilder ();
                for ( int i = 0 ; i < paramDetailList.size () ; i ++ ) 
                {
                    tlm2 = paramDetailList.get ( i );

                    sb.append ( tlm2.getLll());
                    sb.append ( tlm2.getUll());
                    sb.append ( tlm2.getStd_mark());
                    sb.append ( tlm2.getName());
                    sb.append ( tlm2.getId());
                    sb.append ( tlm2.getUom());
                    sb.append ( "\n" );
                }

                selectedparam= paramDetailList;
                
            
            return selectedparam ;
        }

        
         
            public void reset()
		{
            
             for ( int i = 0 ; i < paramList.size () ; i ++ ) 
             {

                if ( paramList.get ( i ).getUll()> 0.0 ) 
                {
                    paramList.get ( i ).setUll(Float.valueOf("0")) ;
                }
                if ( paramList.get ( i ).getLll()> 0.0 ) 
                {
                    paramList.get ( i ).setLll(Float.valueOf("0") ) ;
                }
                if ( paramList.get ( i ).getStandard_mark()> 0 ) 
                {
                    paramList.get ( i ).setStandard_mark(Float.valueOf(0) ) ;
                }
            }
           
            
        }
       
        // Variables declaration - do not modify                     
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        // End of variables declaration                   

        class ParamList extends JPanel 
        {

            private JPanel panel;

            private JScrollPane scroll;
            private JButton btnAddType;

//    public ArrayList<RMQtyPanel> rmBomList = null ;
            public ParamList () 
            {
                // getContentPane().setLayout(new BorderLayout());       

                setLayout ( new BorderLayout () );

                panel = new JPanel ();
                panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
                
                scroll = new JScrollPane ( panel ,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );

                add ( scroll , BorderLayout.CENTER );

                setVisible ( true );

                QualityPanel  pane = null;
            if( paramList == null  )
                {
                paramList = new ArrayList<QualityPanel> ();

                String    EmpAPICall = "unitmeas";
                  String  result2 =  WebAPITester.prepareWebCall ( EmpAPICall, StaticValues.getHeader ()   , "");
                    
                    if( !  result2.contains(  "not found"   )  ){
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        JSONObject jObject = new JSONObject( result2 );
                        Iterator<?> keys = jObject.keys();

                        while( keys.hasNext() ){
                            String key = (String)keys.next();
                            Object value = jObject.get ( key ) ; 
                            map.put(key, value);
                        }

                        JSONObject st = (JSONObject) map.get ( "data" );
                        JSONArray records = st.getJSONArray ( "records");

                        JSONObject emp = null;
                        UOM = new ArrayList<String[]> ();
                        for ( int i = 0 ; i < records.length () ; i ++ ) {

                            emp = records.getJSONObject ( i);
                            //jComboBox1.addItem ( emp.get ( "UOM" ).toString () );
                            UOM.add( new String[]{ emp.get ( "UOM_ID" ).toString (), emp.get ( "UOM" ).toString () }   ); 
                        }
                    }   
               
                     EmpAPICall = "procqty_u_master";
                         result2 =  WebAPITester.prepareWebCall ( EmpAPICall, StaticValues.getHeader ()   , "");

                        if( ! result2.contains( "not found" )   && result2.contains("success"))
                       {  
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        JSONObject jObject = new JSONObject( result2 );
                        Iterator<?> keys = jObject.keys();

                        while( keys.hasNext() ){
                            String key = (String)keys.next();
                            Object value = jObject.get ( key ) ; 
                            map.put(key, value);
                        }

                        JSONObject st = (JSONObject) map.get ( "data" );
                        JSONArray records = st.getJSONArray ( "records");

                        JSONObject emp = null;
                    //list = new ArrayList<Rejection_Reasons> ();
                    panel.setBounds ( 0 , 0 , 300 , 40 * records.length () );
                    
                     for ( int i = 0 ; i < records.length () ; i ++ ) 
                        {
                            emp = records.getJSONObject ( i);
                           // maintain_type.add( new Maintain_typeDR ( emp.getInt("param_id" ), emp.get ( "param_name" ).toString ()   ) ); 

                            pane = new QualityPanel();
                            pane.setBounds(0,(i*40),300,40);
                            pane.setId(emp.getInt( "param_id" ));
                            pane.setParam_name(emp.get ( "param_name" ).toString () );
                            pane.setStandard_mark(Float.valueOf(0));
                            pane.setLll(Float.valueOf("0"));
                            pane.setUll(Float.valueOf("0"));
                            pane.setUom(UOM);
                            panel.add ( pane );
                            panel.revalidate ();
                            paramList.add ( pane );
                           
                        }
                    

                    } 
            }
            
               
            }
            
        } 
    }
    //**********************
}
//        jLabel31.setBounds(70, 110, 30, 20);
//
//        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
//        jLabel4.setText("Die / Tool");
//        add(jLabel4);
//        jLabel4.setBounds(80, 160, 80, 16);
//
//        jComboBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
//        add(jComboBox4);
//        jComboBox4.setBounds(120, 180, 213, 30);
//
//        jLabel20.setText("Param-name");
//        add(jLabel20);
//        jLabel20.setBounds(650, 10, 80, 14);
//
//        jLabel22.setText("Bench-mark");
//        add(jLabel22);
//        jLabel22.setBounds(730, 10, 70, 14);
//
//        jLabel23.setText("UOM");
//        add(jLabel23);
//        jLabel23.setBounds(810, 10, 30, 14);
//
//        jLabel5.setText("LSL");
//        add(jLabel5);
//        jLabel5.setBounds(860, 10, 30, 14);

//        jLabel13.setText("USL");
//        add(jLabel13);
//        jLabel13.setBounds(900, 10, 30, 14);
