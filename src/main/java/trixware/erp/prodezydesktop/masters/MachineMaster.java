/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
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
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.MyCustomFilter;
import trixware.erp.prodezydesktop.components.ProgressDialog;
import trixware.erp.prodezydesktop.examples.HomeScreen;
import trixware.erp.prodezydesktop.helpers.ZXingHelper;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class MachineMaster extends javax.swing.JPanel {

    /**
     * Creates new form MachineMaster
     */
    String resource = "" , resourceType = "" ;
    
    ArrayList<String[]> machineTypes = new ArrayList<String[]>() ;
    
    public MachineMaster() {
        initComponents();
        
        resourceType = "Masters" ;
        resource =  "MachineMaster" ;

//        try{
//            StaticValues.checkUserRolePermission(resourceType, resourceType);
//        }catch(Exception e){
//             StaticValues.writer.writeExcel (resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }
        
        

        jTextField7.setVisible ( false );
        jTextField9.setVisible ( false );
        jTextField6.setVisible ( false );
        jTextField8.setVisible ( false );
        jTextField10.setVisible ( false );
        jTextField11.setVisible ( false );
        jTextField12.setVisible ( false );
        jTextField13.setVisible ( false );
        jTextField3.setVisible ( false );
        jTextField4.setVisible ( false );
        jTextField5.setVisible ( false );
        jTextField14.setVisible ( false );
        jComboBox1.setVisible ( false );
        jComboBox2.setVisible ( false );
        jComboBox3.setVisible ( false );
        jComboBox4.setVisible ( false );
        jComboBox5.setVisible ( false );
        jButton6.setVisible ( false);
        jList1.setVisible ( false);
        
        jLabel7.setVisible ( false );
        jLabel9.setVisible ( false );
        jTextField10.setVisible ( false );
        jLabel10.setVisible ( false );
       
        
//        if( Proman_User.isCanCreate ()  ){
//            jButton1.setVisible ( true );
//        }else {
//            jButton1.setVisible ( false );
//        }
//        
//        if( Proman_User.isCanEdit ()  ){
//            jButton4.setVisible ( true );
//        }else {
//            jButton4.setVisible ( false );
//        }
//        
//        if( Proman_User.isCanImport ()  ){
//            jButton7.setVisible ( true );
//        }else {
//            jButton7.setVisible ( false );
//        }
//        
//        if( Proman_User.isCanExport ()  ){
//            jButton3.setVisible ( true );
//        }else {
//            jButton3.setVisible ( false );
//        }
        
        jTextField15.addKeyListener ( k );
        jTextField15.addFocusListener ( f2 );
        jTextField16.addKeyListener ( k );
        jTextField16.addFocusListener ( f2 );
        jTextField17.addKeyListener ( k );
        jTextField17.addFocusListener ( f2 );
        
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        jScrollPane3.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane3.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
    }

    KeyListener k = new KeyListener () {
        @Override
        public void keyTyped ( KeyEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            char enter = e.getKeyChar ();
            if (  ! ( Character.isDigit ( enter ) ) ) {
                e.consume ();
            }
        }

        @Override
        public void keyPressed ( KeyEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyReleased ( KeyEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }
    };

    FocusListener f2 = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
          //  jcb.setText ( "" );
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To Search Google or type URL
//change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            String x = jcb.getText ().trim ();

            if ( x.equalsIgnoreCase ( "" ) ) {
            //    x = "0";
            }

            try {
                int num = Integer.parseInt ( String.valueOf ( jcb.getText ().toString () ) );
                if ( num < 0 || num == 0) {
                    jcb.setText ( "" );

                    JOptionPane.showMessageDialog ( null, "Cannot have 0 value for this field. Please enter input >0");
                    
                }
            } catch ( NumberFormatException ex1 ) {
                jcb.setText ( x );
                StaticValues.writer.writeExcel (MachineMaster.class.getSimpleName () , MachineMaster.class.getSimpleName () , ex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }
    };
    
    public void loadContent() {

//        try {
//            ResultSet result = DB_Operations.getMasters("machine");
//            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
//
//            if (result != null) {
//                jTable1.setModel(buildTableModel(result));
//
//                jTable1.getColumnModel().getColumn(0).setMinWidth(0);
//                jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
//            }    
//            result.close();
//
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog ( null, "Error. Please restart the application ");         
//            StaticValues.writer.writeExcel (MachineMaster.class.getSimpleName () , MachineMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }

        if ( jComboBox6.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            String addEmpAPICall = "machinestypes";
            String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

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
                machineTypes = new ArrayList<String[]> ();
                for ( int i = 0 ; i < records.length () ; i ++ ) {

                    emp = records.getJSONObject ( i);
                    jComboBox6.addItem ( emp.get ( "MC_TYPE" ).toString () );
                    machineTypes.add( new String[]{  emp.get ( "MC_TYPE_ID" ).toString (), emp.get ( "MC_TYPE" ).toString () }); 
                }
            }
        }

        String addEmpAPICall = "machines";
        String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

        if ( result2!= null    && ! result2.contains( "not found"    ) )  {
            jTable1.setModel ( buildTableModelfromJSON (result2 ) );
            jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
            jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
        }

    }

    public static DefaultTableModel buildTableModelfromJSON ( String  employeeJSONList )
             {

            Vector<String> columnNames = new Vector<String> ();
            int columnCount = 0;
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        
            JSONArray jARR = null;
            JSONObject jOB = null;
        
            HashMap<String, Object> map = new HashMap<String, Object>();
            JSONObject jObject = new JSONObject( employeeJSONList );
            Iterator<?> keys = jObject.keys();

            while( keys.hasNext() ){
                String key = (String)keys.next();
                Object value = jObject.get ( key ) ; 
                map.put(key, value);
            }
            
            JSONObject st = (JSONObject) map.get ( "data" );
            JSONArray records = st.getJSONArray ( "records");
            
            JSONObject emp = null;
            
                columnNames = new Vector<String> ();
                columnNames.add( "MCH_ID"  );columnNames.add( "MACHINE_NO"  );columnNames.add( "MAKE"  );columnNames.add( "OP_RATE_HR"  );columnNames.add( "AVL_HRS"  );columnNames.add( "DAVLHRS"  );
           

        // data of the table
        for( int i=0; i<records.length (); i++ ){
        Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 0 ; columnIndex <columnNames.size () ; columnIndex ++ ) {
                emp = records.getJSONObject ( i);
                vector.add ( emp.get ( columnNames.get ( columnIndex ) )  );
            }
            data.add ( vector );
        }
        return new DefaultTableModel ( data , columnNames );
    }
    
    
    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= 4; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= 4; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        rs.close();
        
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
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField1 = new JFormattedTextField("");
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel22 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1154, 551));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Machine Type");
        add(jLabel1);
        jLabel1.setBounds(140, 70, 130, 30);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Make");
        add(jLabel2);
        jLabel2.setBounds(140, 110, 130, 30);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Bed Size");
        add(jLabel3);
        jLabel3.setBounds(420, 10, 130, 0);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("No of Heaters");
        add(jLabel4);
        jLabel4.setBounds(420, 50, 130, 0);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Year of Commissioning");
        add(jLabel5);
        jLabel5.setBounds(420, 90, 130, 0);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("Maintenance Rating");
        add(jLabel6);
        jLabel6.setBounds(20, 210, 130, 0);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("Machine Photo");
        add(jLabel7);
        jLabel7.setBounds(20, 460, 130, 0);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("Machine Current Owner");
        add(jLabel8);
        jLabel8.setBounds(20, 250, 130, 0);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("<html>Machine Owners'<br>Photo</html>");
        add(jLabel9);
        jLabel9.setBounds(20, 500, 130, 0);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel11.setText("Electrical");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(10, 0, 130, 0);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Daily", "Twice Week", "Weekly", "Fortnightly", "Monthly", "Yearly", "Bienniel", " " }));
        jComboBox1.setNextFocusableComponent(jComboBox2);
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(140, 0, 210, 0);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel12.setText("Mechanical");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(10, 40, 130, 0);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Daily", "Twice Week", "Weekly", "Fortnightly", "Monthly", "Yearly", "Bienniel", " " }));
        jComboBox2.setNextFocusableComponent(jComboBox3);
        jPanel1.add(jComboBox2);
        jComboBox2.setBounds(140, 40, 210, 0);

        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel13.setText("Hydraulic");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(10, 80, 130, 0);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Daily", "Twice Week", "Weekly", "Fortnightly", "Monthly", "Yearly", "Bienniel", " " }));
        jComboBox3.setNextFocusableComponent(jComboBox4);
        jPanel1.add(jComboBox3);
        jComboBox3.setBounds(140, 80, 210, 0);

        jLabel14.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel14.setText("Pneumatic");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(10, 120, 130, 0);

        jComboBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Daily", "Twice Week", "Weekly", "Fortnightly", "Monthly", "Yearly", "Bienniel", " " }));
        jComboBox4.setNextFocusableComponent(jComboBox5);
        jPanel1.add(jComboBox4);
        jComboBox4.setBounds(140, 120, 210, 0);

        jLabel15.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel15.setText("Clit");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(10, 160, 130, 0);

        jComboBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Daily", "Twice Week", "Weekly", "Fortnightly", "Monthly", "Yearly", "Bienniel", " " }));
        jComboBox5.setNextFocusableComponent(jTextField14);
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox5);
        jComboBox5.setBounds(140, 160, 210, 0);

        add(jPanel1);
        jPanel1.setBounds(410, 150, 370, 0);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(null);

        jLabel16.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel16.setText("Pressure Gauge");
        jPanel2.add(jLabel16);
        jLabel16.setBounds(10, 10, 130, 0);

        jLabel17.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel17.setText("Timer");
        jPanel2.add(jLabel17);
        jLabel17.setBounds(10, 50, 130, 0);

        jLabel18.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel18.setText("<html>Temperature<br>Controller</html>");
        jPanel2.add(jLabel18);
        jLabel18.setBounds(10, 90, 130, 0);

        jTextField11.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField11.setNextFocusableComponent(jTextField12);
        jPanel2.add(jTextField11);
        jTextField11.setBounds(140, 10, 213, 0);

        jTextField12.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField12.setNextFocusableComponent(jTextField13);
        jPanel2.add(jTextField12);
        jTextField12.setBounds(140, 50, 213, 0);

        jTextField13.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField13.setNextFocusableComponent(jTextField3);
        jPanel2.add(jTextField13);
        jTextField13.setBounds(140, 90, 213, 0);

        add(jPanel2);
        jPanel2.setBounds(10, 320, 370, 0);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setText("<html>Machines' Breakdown<br>Record</html>");
        add(jLabel10);
        jLabel10.setBounds(20, 290, 130, 0);

        jTextField1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField1.setToolTipText("Make of the machine");
        jTextField1.setNextFocusableComponent(jTextField2);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(270, 30, 213, 30);

        jTextField2.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField2.setNextFocusableComponent(jTextField15);
        add(jTextField2);
        jTextField2.setBounds(270, 110, 213, 30);

        jTextField3.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField3.setNextFocusableComponent(jTextField4);
        add(jTextField3);
        jTextField3.setBounds(550, 10, 213, 0);

        jTextField4.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField4.setNextFocusableComponent(jTextField5);
        add(jTextField4);
        jTextField4.setBounds(550, 50, 213, 0);

        jTextField5.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField5.setNextFocusableComponent(jComboBox1);
        add(jTextField5);
        jTextField5.setBounds(550, 90, 213, 0);

        jTextField6.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField6.setNextFocusableComponent(jTextField7);
        add(jTextField6);
        jTextField6.setBounds(150, 210, 213, 0);

        jTextField7.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField7.setNextFocusableComponent(jTextField8);
        add(jTextField7);
        jTextField7.setBounds(150, 460, 213, 0);

        jTextField8.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField8.setNextFocusableComponent(jTextField9);
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });
        add(jTextField8);
        jTextField8.setBounds(150, 250, 213, 0);

        jTextField9.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField9.setNextFocusableComponent(jTextField10);
        add(jTextField9);
        jTextField9.setBounds(150, 500, 213, 0);

        jTextField10.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField10.setNextFocusableComponent(jLabel11);
        add(jTextField10);
        jTextField10.setBounds(150, 290, 213, 0);

        jTable1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(760, 20, 340, 390);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Add to Master");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(150, 290, 130, 30);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Cancel");
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(150, 320, 130, 30);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Export to Excel");
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(320, 320, 130, 30);

        jLabel19.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel19.setText("Operating Rate / Hr");
        add(jLabel19);
        jLabel19.setBounds(140, 150, 130, 30);

        jTextField15.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField15.setNextFocusableComponent(jTextField16);
        add(jTextField15);
        jTextField15.setBounds(270, 150, 213, 30);

        jLabel20.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel20.setText("<html>Designed Availablity in hrs</html>");
        add(jLabel20);
        jLabel20.setBounds(140, 230, 130, 40);

        jTextField16.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField16.setNextFocusableComponent(jTextField17);
        add(jTextField16);
        jTextField16.setBounds(270, 190, 213, 30);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("Edit Record");
        jButton4.setOpaque(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(320, 290, 130, 30);

        jButton5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton5.setText("Reset");
        jButton5.setOpaque(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        add(jButton5);
        jButton5.setBounds(150, 350, 130, 30);

        jLabel21.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel21.setText("<html> List of Instruments on Equipment </html>");
        jLabel21.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        add(jLabel21);
        jLabel21.setBounds(420, 340, 250, 0);

        jTextField14.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField14.setNextFocusableComponent(jButton6);
        jTextField14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField14ActionPerformed(evt);
            }
        });
        add(jTextField14);
        jTextField14.setBounds(420, 370, 220, 0);

        jButton6.setText("Add");
        jButton6.setOpaque(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        add(jButton6);
        jButton6.setBounds(690, 370, 70, 0);

        jList1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jList1);

        add(jScrollPane3);
        jScrollPane3.setBounds(190, 410, 0, 131);

        jLabel22.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel22.setText("Availability in hrs");
        add(jLabel22);
        jLabel22.setBounds(140, 190, 130, 30);

        jTextField17.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField17.setNextFocusableComponent(jTextField6);
        add(jTextField17);
        jTextField17.setBounds(270, 230, 213, 30);

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 0, 51));
        jLabel23.setText("*");
        add(jLabel23);
        jLabel23.setBounds(130, 40, 10, 16);

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 0, 51));
        jLabel24.setText("*");
        add(jLabel24);
        jLabel24.setBounds(130, 80, 10, 16);

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 0, 51));
        jLabel25.setText("*");
        add(jLabel25);
        jLabel25.setBounds(130, 160, 10, 16);

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 0, 51));
        jLabel26.setText("*");
        add(jLabel26);
        jLabel26.setBounds(130, 200, 10, 16);

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 51));
        jLabel27.setText("*");
        add(jLabel27);
        jLabel27.setBounds(130, 230, 10, 24);

        jButton7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton7.setText("Import Excel (xls)");
        jButton7.setOpaque(false);
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        add(jButton7);
        jButton7.setBounds(320, 350, 130, 30);

        jLabel28.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel28.setText("Preventive Maintenance Frequency");
        add(jLabel28);
        jLabel28.setBounds(420, 130, 250, 0);

        jButton8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton8.setText("Delete");
        jButton8.setOpaque(false);
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });
        add(jButton8);
        jButton8.setBounds(150, 380, 130, 30);

        jButton9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton9.setText("Create QR Code");
        jButton9.setOpaque(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        add(jButton9);
        jButton9.setBounds(320, 380, 130, 30);

        jComboBox6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jComboBox6);
        jComboBox6.setBounds(270, 70, 213, 30);

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 0, 51));
        jLabel29.setText("*");
        add(jLabel29);
        jLabel29.setBounds(130, 120, 10, 16);

        jLabel30.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel30.setText("Machine Name/No");
        add(jLabel30);
        jLabel30.setBounds(140, 30, 130, 30);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        // String machine_make, c_maint_rt, c_mach_owner, pm_f_ele, pm_f_mec, pm_f_hyd, pm_f_pnm, pm_f_clit, press_gg, timer, temp_ctrl;
        // int heater_qty ;
        // long bed_size, ;
        String yr_comm, machine_no, machine_make, c_maint_rt, mach_img, c_mach_owner, pm_f_ele, pm_f_mec, pm_f_hyd, pm_f_pnm, pm_f_clit, press_gg, timer, temp_ctrl, heater_qty, bed_size, mach_ownr_img, prev_maint_rcd, opratehrs, avalhrs, davalhrs, INSTR_LIST;

        machine_no = jTextField1.getText();
        machine_make = jTextField2.getText();
        bed_size = jTextField3.getText();
        heater_qty = jTextField4.getText();
        yr_comm = jTextField5.getText();
        c_maint_rt = jTextField6.getText();
        mach_img = jTextField7.getText();

        c_mach_owner = jTextField8.getText();
        mach_ownr_img = jTextField9.getText();
        prev_maint_rcd = jTextField10.getText();
        pm_f_ele = jComboBox1.getSelectedItem().toString();
        pm_f_mec = jComboBox2.getSelectedItem().toString();
        pm_f_hyd = jComboBox3.getSelectedItem().toString();
        pm_f_pnm = jComboBox4.getSelectedItem().toString();
        pm_f_clit = jComboBox5.getSelectedItem().toString();
        press_gg = jTextField11.getText();
        timer = jTextField12.getText();
        temp_ctrl = jTextField13.getText();
        davalhrs = jTextField17.getText ();

        INSTR_LIST = "";
        int i = 0;
        while (i < instrumentList.size()) {
            if (i == instrumentList.size() - 1) {
                INSTR_LIST = INSTR_LIST + instrumentList.get(i);
            } else {
                INSTR_LIST = INSTR_LIST + instrumentList.get(i) + ",";
            }
            i++;
        }

        opratehrs = jTextField15.getText();
        avalhrs = jTextField16.getText();

        String createdOn = "", editedOn = "" ;
            SimpleDateFormat sdf = new SimpleDateFormat(  "MMM dd, yyyy HH:mm" ) ;
            createdOn = sdf.format( Calendar.getInstance ().getTime ()  );
            editedOn =  sdf.format( Calendar.getInstance ().getTime ()  ); 
            int editedBy = Proman_User.getEID () ;
        
        String result = null ;
        if(  !machine_no.equals( "") && !machine_make.equals( "") &&  !opratehrs.equals( "")  &&  !avalhrs.equals( "") &&  !davalhrs.equals( "")  ){
        
            result = DB_Operations.insertIntoMachineMaster(machine_no, machine_make, bed_size, heater_qty, yr_comm,
                    c_maint_rt, mach_img, c_mach_owner, mach_ownr_img, prev_maint_rcd, pm_f_ele, pm_f_mec, pm_f_hyd, pm_f_pnm, pm_f_clit, press_gg, timer, temp_ctrl, opratehrs, avalhrs, davalhrs, INSTR_LIST, createdOn, editedOn, editedBy+"");

            
                try{
                    
                    String addEmpAPICall = "machineadd?MACHINE_NO="+URLEncoder.encode(machine_no, "UTF-8")+"&MAKE="+URLEncoder.encode(machine_make, "UTF-8")+"&BED_SIZE="+URLEncoder.encode(bed_size , "UTF-8")
                            +"&HEATER_QTY="+URLEncoder.encode(heater_qty, "UTF-8")+"&YR_OF_COMM="+URLEncoder.encode( yr_comm, "UTF-8")+"&CR_MAINT_RTG="+URLEncoder.encode( c_maint_rt, "UTF-8")
                            +"&MCH_IMG="+URLEncoder.encode( mach_img, "UTF-8")+"&CR_MCH_OWN="+URLEncoder.encode( c_mach_owner, "UTF-8")+"&MACH_OWN_IMG="+URLEncoder.encode( mach_ownr_img, "UTF-8")
                            +"&MCH_BRKD_RCD="+URLEncoder.encode( prev_maint_rcd, "UTF-8")+"&PM_ELE="+URLEncoder.encode( pm_f_ele, "UTF-8")+"&PM_MEC="+URLEncoder.encode( pm_f_mec, "UTF-8")
                            +"&PM_HYD="+URLEncoder.encode( pm_f_hyd, "UTF-8")+"&PM_PNM="+URLEncoder.encode( pm_f_pnm, "UTF-8")+"&PM_CLIT="+URLEncoder.encode(pm_f_clit, "UTF-8")+"&PRSS_GAUGE="+URLEncoder.encode(press_gg, "UTF-8")
                            +"&TIMER="+URLEncoder.encode(timer, "UTF-8")+"&OP_RATE_HR="+URLEncoder.encode(opratehrs, "UTF-8")+"&AVL_HRS="+URLEncoder.encode(avalhrs, "UTF-8")+"&DAVLHRS="+URLEncoder.encode(davalhrs, "UTF-8")
                            +"&INSTR_LIST="+URLEncoder.encode(INSTR_LIST, "UTF-8")+"&CREATED_ON="+URLEncoder.encode(createdOn, "UTF-8")+"&EDITED_ON="+URLEncoder.encode(editedOn, "UTF-8")
                            +"&EDITED_BY="+URLEncoder.encode(editedBy+"", "UTF-8")+"&TYPE="+URLEncoder.encode(  machineTypes.get( jComboBox6.getSelectedIndex () )[0]   +"", "UTF-8"); 
                    String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    JOptionPane.showMessageDialog ( null , result2);
                
                }catch(UnsupportedEncodingException e){
                    
                }

            
            JOptionPane.showMessageDialog(null, "Machine added successfuly");

        }else{
            JOptionPane.showMessageDialog(null, "Please fill mandatory fields marked with * " );
        }
        
        
        //////////////////////////////////////////     add data to firebase //////////////////////////////////////////////////////////////////////////
        
//             StaticValues.database = FirebaseDatabase.getInstance();
//                    DatabaseReference ref = StaticValues.database.getReference("");
//
//                        SimpleDateFormat sdf1 = new SimpleDateFormat( "dd MMM, yyyy - HH:mm" ) ;
//
//                            String key = ref.child("machine").push().getKey();
//                            Machine_Entity  post = new Machine_Entity( machine_no , machine_make, Double.parseDouble(opratehrs), Integer.parseInt( avalhrs),  Integer.parseInt( davalhrs),  sdf1.format(Calendar.getInstance().getTime ()), sdf1.format(Calendar.getInstance().getTime ()),   Proman_User.getEID ());
//                            Map<String, Object> postValues = post.toMap();
//
//                            Map<String, Object> childUpdates = new HashMap<>();
//                            childUpdates.put("/machine/" + result , postValues);
//                            
//                            ref.updateChildren(childUpdates, cListener);
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
        loadContent();
        
        resetFields();
        
        
        if( Proman_User.isCanCreate ()  ){
            jButton1.setEnabled(true);
        }
        
        if( Proman_User.isCanEdit ()  ){
             jButton4.setEnabled(false);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    
    DatabaseReference.CompletionListener cListener = new DatabaseReference.CompletionListener () {
         @Override
         public void onComplete ( DatabaseError de , DatabaseReference dr ) {
        
                JOptionPane.showMessageDialog ( null, "New 'Machine' added to server");
             
         }
     };
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        showDialog();
        
        
//        Workbook wb = new HSSFWorkbook();
//
//        ArrayList<String> column_names = new ArrayList<String>();
//        ArrayList<String[]> values = new ArrayList<String[]>();
//        int columnCount = 0;
//
//        File file = null;
//
//        try {
//            ResultSet result = DB_Operations.getMasters("machine");
//            if (result != null) {
//                ResultSetMetaData metaData = result.getMetaData();
//                // names of columns
//                columnCount = metaData.getColumnCount();
//                for (int column = 2; column <= columnCount - 3; column++) {
//                    column_names.add(metaData.getColumnName(column));
//                }
//            }
//            // data of the table
//            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
//            while (result.next()) {
//                String[] val = new String[columnCount];
//                for (int columnIndex = 2; columnIndex <= columnCount; columnIndex++) {
//                    if (columnIndex < columnCount - 2) {
//                        val[columnIndex - 2] = String.valueOf(result.getObject(columnIndex));
//                    }
//                }
//                values.add(val);
//            }
//        } catch (SQLException e) {
//            StaticValues.writer.writeExcel (MachineMaster.class.getSimpleName () , MachineMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }
//
//        // Cell style for header row
//        CellStyle cs = wb.createCellStyle();
//        //cs.setFillForegroundColor(HSSFColor.LIME.index);
//        //cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//
//        CellStyle cs2 = wb.createCellStyle();
//        cs2.setFillForegroundColor(HSSFColor.ROSE.index);
//        cs2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//
//        // New Sheet
//        Sheet sheet1 = null;
//        sheet1 = wb.createSheet("Machine Master");
//        sheet1.setColumnWidth(0, (15 * 500));
//
//        Cell c = null;
//        Cell c3 = null;
//
//        Row row = sheet1.createRow(1);
//        //  c = row.createCell(0);
//        // c.setCellValue("");
//        // c.setCellStyle(cs);
//
//        int i = 1;
//        //add column names to the firt row of excel
//        row = sheet1.createRow(i++);
//        for (int a = 0; a < column_names.size(); a++) {
//            c = row.createCell(a);
//            // c.setCellValue(cursor.getString(a));
//            c.setCellValue(column_names.get(a));
//            c.setCellStyle(cs);
//        }
//
//        for (int j = 0; j < values.size(); j++) {
//            String[] rowValues = values.get(j);
//            Row row4 = sheet1.createRow(i++);
//            for (int k = 0; k < rowValues.length; k++) {
//                {
//
//                    c = row4.createCell(k);
//                    c.setCellValue(rowValues[k]);
//                    c.setCellStyle(cs);
//                }
//            }
//            Calendar c2 = Calendar.getInstance();
//            SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMMyyyy_HH_mm_a");
//            String strDate2 = sdf2.format(c2.getTime());
//            file = new File("Machine Master File " + strDate2 + ".xls");
//
//            FileOutputStream os = null;
//
//            try {
//                os = new FileOutputStream(file);
//                wb.write(os);
//                wb.close();
//                os.close();
//
//            } catch (IOException e) {
//                JOptionPane.showMessageDialog(null, "Error " + e.getMessage());
//                StaticValues.writer.writeExcel (MachineMaster.class.getSimpleName () , MachineMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//            } finally {
//
//            }
//
//        }
//        JOptionPane.showMessageDialog(null, "New File generated at " + file.getAbsolutePath());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:

        jButton1.setEnabled(false);

        DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();
        int selectedRowIndex = jTable1.getSelectedRow ();
     
        selectedEmpRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );
        Machine_no =  model.getValueAt ( selectedRowIndex , 1 ).toString ();
        make =  model.getValueAt ( selectedRowIndex , 2 ).toString ();
        operation_rate_hr =  Integer.parseInt(  model.getValueAt ( selectedRowIndex , 3 ).toString ());  
        avail_hrs =  Integer.parseInt( model.getValueAt ( selectedRowIndex , 4 ).toString ());
        
        
        jTextField1.setText( model.getValueAt ( selectedRowIndex , 1 ).toString () ); //machine_no = 
        jTextField2.setText( model.getValueAt ( selectedRowIndex , 2 ).toString () ); //machine_make = 
        jTextField15.setText(  model.getValueAt ( selectedRowIndex , 3 ).toString ()  );//op rate / hr
        jTextField16.setText( model.getValueAt ( selectedRowIndex , 4 ).toString ()  );//avali hrs
        jTextField17.setText(  model.getValueAt ( selectedRowIndex , 5 ).toString ()  );//avali hrs
        
        
//        ArrayList<String> values = new ArrayList<String>();
//        ArrayList<byte[]> files = new ArrayList<byte[]>();
//        int columnCount = 0;
//        // get the model from the jtable
//        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//
//        // get the selected row index
//        int selectedRowIndex = jTable1.getSelectedRow();
//        // Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString() )
//        //         JOptionPane.showMessageDialog(null, "" + model.getValueAt(selectedRowIndex, 0).toString() );
//        try {
//            ResultSet result = DB_Operations.getSingleMasters("machine", Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString()));
//
//            selectedEmpRecordId = Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString());
//
//            if (result != null) {
//                ResultSetMetaData metaData = result.getMetaData();
//
//                columnCount = metaData.getColumnCount();
//
//                while (result.next()) {
//
//                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
//                      //  if (columnIndex < columnCount - 2) {
//
//                            values.add(String.valueOf(result.getObject(columnIndex)));
//                      //  }
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Error " + e.getMessage());
//            StaticValues.writer.writeExcel (MachineMaster.class.getSimpleName () , MachineMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }
//
//        jTextField1.setText(values.get(1)); //machine_no = 
//        jTextField2.setText(values.get(2)); //machine_make = 
//        jTextField3.setText(values.get(3)); //bed_size =
//        jTextField4.setText(values.get(4)); //heater_qty =
//        jTextField5.setText(values.get(5)); // yr_comm =
//        jTextField6.setText(values.get(6)); //c_maint_rt = 
//        jTextField7.setText(values.get(7)); // machine image
//
//        jTextField8.setText(values.get(8));//c_mach_owner =
//        jTextField9.setText(values.get(9)); //mach_ownr_img =
//        jTextField10.setText(values.get(10)); //prev_maint_rcd =
//        jComboBox1.setSelectedItem(0);//pm_f_ele =
//        jComboBox2.setSelectedItem(0); //pm_f_mec =
//        jComboBox3.setSelectedItem(0); //pm_f_hyd =
//        jComboBox4.setSelectedItem(0);//pm_f_pnm =
//        jComboBox5.setSelectedItem(0);//pm_f_clit =
//        jTextField11.setText(values.get(16));//press_gg =
//        jTextField12.setText(values.get(17));//timer =
//        jTextField13.setText(values.get(18));//temp_ctrl =
//
//        jTextField15.setText(values.get(19));//op rate / hr
//        jTextField16.setText(values.get(20));//avali hrs
//        jTextField17.setText(values.get(25));//avali hrs
//        
//        
//
//        String[] instrList = values.get(21).split(",");
//        instrumentList.removeAllElements();
//        for (int i = 0; i < instrList.length; i++) {
//            System.out.println(instrList[i]);
//            instrumentList.addElement(instrList[i]);
//        }
//        jList1.setModel(instrumentList);

        jButton4.setEnabled(true);

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:

        resetFields();
        jButton1.setEnabled(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        HomeScreen.home.removeForms();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        String yr_comm, machine_no, machine_make, c_maint_rt, mach_img, c_mach_owner, pm_f_ele, pm_f_mec, pm_f_hyd, pm_f_pnm, pm_f_clit, press_gg, timer, temp_ctrl, heater_qty, bed_size, mach_ownr_img, prev_maint_rcd, opratehrs, avalhrs, davalhrs, INSTR_LIST;
        int ID;

        machine_no = jTextField1.getText();
        machine_make = jTextField2.getText();
        bed_size = jTextField3.getText();
        heater_qty = jTextField4.getText();
        yr_comm = jTextField5.getText();
        c_maint_rt = jTextField6.getText();
        mach_img = jTextField7.getText();

        c_mach_owner = jTextField8.getText();
        mach_ownr_img = jTextField9.getText();
        prev_maint_rcd = jTextField10.getText();
        pm_f_ele = jComboBox1.getSelectedItem().toString();
        pm_f_mec = jComboBox2.getSelectedItem().toString();
        pm_f_hyd = jComboBox3.getSelectedItem().toString();
        pm_f_pnm = jComboBox4.getSelectedItem().toString();
        pm_f_clit = jComboBox5.getSelectedItem().toString();
        press_gg = jTextField11.getText();
        timer = jTextField12.getText();
        temp_ctrl = jTextField13.getText();
        davalhrs = jTextField17.getText();
        opratehrs = jTextField15.getText();
        avalhrs = jTextField16.getText();
        

        INSTR_LIST = "";
        int i = 0;
        while (i < instrumentList.size()) {
            if (i == instrumentList.size() - 1) {
                INSTR_LIST = INSTR_LIST + instrumentList.get(i);
            } else {
                INSTR_LIST = INSTR_LIST + instrumentList.get(i) + ",";
            }
            i++;
        }
        
        ID = selectedEmpRecordId;

         if(  !machine_no.equals( "") && !machine_make.equals( "") &&  !opratehrs.equals( "")  &&  !avalhrs.equals( "") &&  !davalhrs.equals( "")  ){
        
            String result = DB_Operations.updateMachineMaster(machine_no, machine_make, bed_size, heater_qty, yr_comm, c_maint_rt, mach_img, c_mach_owner, mach_ownr_img, prev_maint_rcd, pm_f_ele, pm_f_mec, pm_f_hyd, pm_f_pnm, pm_f_clit, press_gg, timer, temp_ctrl, opratehrs, avalhrs, davalhrs, INSTR_LIST,  "edited on", "edited by", ID);
        //    JOptionPane.showMessageDialog(null, "Machine Updated Successfuly");
        
                try{

                        String addEmpAPICall = "machinesupdate?MCH_ID="+URLEncoder.encode(selectedEmpRecordId+"", "UTF-8")+"&MACHINE_NO="+URLEncoder.encode(machine_no, "UTF-8")+"&MAKE="+URLEncoder.encode(machine_make, "UTF-8")
                                +"&BED_SIZE="+URLEncoder.encode(bed_size , "UTF-8")+"&HEATER_QTY="+URLEncoder.encode(heater_qty, "UTF-8")+"&YR_OF_COMM="+URLEncoder.encode( yr_comm, "UTF-8")
                                +"&CR_MAINT_RTG="+URLEncoder.encode( c_maint_rt, "UTF-8")+"&MCH_IMG="+URLEncoder.encode( mach_img, "UTF-8")+"&CR_MCH_OWN="+URLEncoder.encode( c_mach_owner, "UTF-8")
                                +"&MACH_OWN_IMG="+URLEncoder.encode( mach_ownr_img, "UTF-8")+"&MCH_BRKD_RCD="+URLEncoder.encode( prev_maint_rcd, "UTF-8")+"&PM_ELE="+URLEncoder.encode( pm_f_ele, "UTF-8")
                                +"&PM_MEC="+URLEncoder.encode( pm_f_mec, "UTF-8")+"&PM_HYD="+URLEncoder.encode( pm_f_hyd, "UTF-8")+"&PM_PNM="+URLEncoder.encode( pm_f_pnm, "UTF-8")+"&PM_CLIT="+URLEncoder.encode(pm_f_clit, "UTF-8")
                                +"&PRSS_GAUGE="+URLEncoder.encode(press_gg, "UTF-8")+"&TIMER="+URLEncoder.encode(timer, "UTF-8")+"&OP_RATE_HR="+URLEncoder.encode(opratehrs, "UTF-8")+"&AVL_HRS="+URLEncoder.encode(avalhrs, "UTF-8")
                                +"&DAVLHRS="+URLEncoder.encode(davalhrs, "UTF-8")+"&INSTR_LIST="+URLEncoder.encode(INSTR_LIST, "UTF-8")+"&CREATED_ON=&EDITED_ON=&EDITED_BY="+"&TYPE="
                                +URLEncoder.encode(  machineTypes.get( jComboBox6.getSelectedIndex () )[0]   +"", "UTF-8");
                        String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                        JOptionPane.showMessageDialog ( null , "Record updated Successfully..!");
                        
                        addEmpAPICall = "machines";
                         result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                         
                        if ( result2!= null ) {
                            jTable1.setModel ( buildTableModelfromJSON (result2 ) );
                            jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                            jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                        }
                        
                        resetFields();
                            
                }catch(UnsupportedEncodingException e){

                }
        
         }else{
             JOptionPane.showMessageDialog(null, "Please fill all fields marked with * ");
         }

        loadContent();


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField14ActionPerformed
    DefaultListModel<String> instrumentList = new DefaultListModel<>();
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        String instrumentName = jTextField14.getText();
        instrumentList.addElement(instrumentName);
        jList1.setModel(instrumentList);
       
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        if( jList1.getModel ().getSize () > 0 ){
            int selection = jList1.getSelectedIndex();
            instrumentList.removeElementAt(selection);
            jList1.setModel(instrumentList);
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        // TODO add your handling code here:
        
        File selectedFile = null;

        JFileChooser fileChooser = new JFileChooser ();
        fileChooser.setFileFilter ( new MyCustomFilter ("excel 97-2003") );
        fileChooser.setCurrentDirectory ( new File ( System.getProperty (
                "user.home" ) ) );
        int result = fileChooser.showOpenDialog ( this );
        if ( result == JFileChooser.APPROVE_OPTION ) {

            selectedFile = fileChooser.getSelectedFile ();

        }

        FileChannel inputChannel = null;
        FileChannel outputChannel = null;

        File dir = new File ( "dataupload\\" );
        dir.mkdirs ();

        String url;
        try {

            url = "jdbc:sqlite:" + StaticValues.dbName;
            Connection con = DriverManager.getConnection ( url );
            Statement stm = con.createStatement ();
            PreparedStatement pst;

            FileInputStream fis;
            //String query = "INSERT INTO xl_import_test ( ID, name, address, phone, email) VALUES ( ?,?,?,?,?)";
            String query = "INSERT INTO machine ( MACHINE_NO ,  MAKE ) VALUES ( ?,?  )";

            pst = con.prepareStatement ( query );
            fis = new FileInputStream ( new File ( "dataupload\\" + selectedFile.getName () ) );

            HSSFWorkbook my_xls_workbook = new HSSFWorkbook ( fis );
            HSSFSheet my_worksheet = my_xls_workbook.getSheetAt ( 0 );

            Iterator<Row> rowIterator = my_worksheet.iterator ();

            while ( rowIterator.hasNext () ) {
                Row row = rowIterator.next ();

                try {
                     pst.setString ( 1,  row.getCell ( 0 ).getStringCellValue () );
                } catch ( Exception e2 ) {
                    StaticValues.writer.writeExcel (MachineMaster.class.getSimpleName () , MachineMaster.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    try {
                         pst.setInt ( 1, ( int ) row.getCell ( 0 ).getNumericCellValue () );
                    } catch ( Exception e1 ) {
                        StaticValues.writer.writeExcel (MachineMaster.class.getSimpleName () , MachineMaster.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }
                }
                
                try {
                     pst.setString ( 2,  row.getCell ( 1 ).getStringCellValue () );
                } catch ( Exception e2 ) {
                    StaticValues.writer.writeExcel (MachineMaster.class.getSimpleName () , MachineMaster.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    try {
                         pst.setInt ( 2, ( int ) row.getCell ( 1 ).getNumericCellValue () );
                    } catch ( Exception e1 ) {
                        StaticValues.writer.writeExcel (MachineMaster.class.getSimpleName () , MachineMaster.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }
                }
                
                pst.addBatch ();
            }

             int[] totalRecords = new int[ 2 ];
            try {
                totalRecords = pst.executeBatch ();
            } catch ( BatchUpdateException e1 ) {
                totalRecords = e1.getUpdateCounts ();
                StaticValues.writer.writeExcel (MachineMaster.class.getSimpleName () , MachineMaster.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
            System.out.println ( "Total record Inserted " + totalRecords.length );
            fis.close ();
            pst.close ();

           loadContent ();
        

        } catch ( SQLException ex ) {
            System.out.println ( "SQL  " + ex.getMessage () );
            StaticValues.writer.writeExcel (MachineMaster.class.getSimpleName () , MachineMaster.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } catch ( IOException ex ) {
            System.out.println ( "IO  " + ex.getMessage () );
            StaticValues.writer.writeExcel (MachineMaster.class.getSimpleName () , MachineMaster.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        try {
            inputChannel = new FileInputStream ( selectedFile ).getChannel ();
            outputChannel = new FileOutputStream ( new File ( dir , selectedFile.getName () ) ).getChannel ();
            outputChannel.transferFrom ( inputChannel , 0 , inputChannel.size () );

            inputChannel.close ();
            outputChannel.close ();

        } catch ( FileNotFoundException e1 ) {
            StaticValues.writer.writeExcel (MachineMaster.class.getSimpleName () , MachineMaster.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } catch ( IOException e2 ) {
            StaticValues.writer.writeExcel (MachineMaster.class.getSimpleName () , MachineMaster.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }
        
    }//GEN-LAST:event_jButton7MouseClicked

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked
        // TODO add your handling code here:
        
        int selection = JOptionPane.showConfirmDialog ( null , "Deleting a record is not recommendedThe 'Delete' action may cause loss of any dependencies and may affect the transactional Data and Reports and make data inconsistent\nDeleting a plant will automatically delete all shifts for that plant\nPress 'No' to revert, Press 'Yes' to continue deleting", "Are you sure about deleting this record ?", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE);
        if ( selection == JOptionPane.YES_OPTION ) { 
            deleteMaster(  "machinedelete?MCH_ID="+selectedEmpRecordId    ) ; 
            
            String addEmpAPICall = "machines";
            String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

            if ( result2!= null    && ! result2.contains( "not found"    ) )  {
                jTable1.setModel ( buildTableModelfromJSON (result2 ) );
                jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
            }
        }    
    }//GEN-LAST:event_jButton8MouseClicked
    //*************created by harshali QR Code
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
    
        if(selectedEmpRecordId==0)
        {
         JOptionPane.showMessageDialog ( null , "Please select a record first" );
      
        }else
        {
            try
            {
                
                if(selectedEmpRecordId!=0 && !Machine_no.contains("null"))
                  {
                    JSONObject object=new JSONObject();
                    object.accumulate("machine_id", selectedEmpRecordId);
                    object.accumulate("machine_no", Machine_no); 
                    object.accumulate("make", make);
                    object.accumulate("op_rate_hrs", operation_rate_hr);
                    object.accumulate("available_hrs", avail_hrs);
                    
                    System.out.println(object.toString());
                   
                   generate_qr(Machine_no+"_"+selectedEmpRecordId,object.toString());           
                                
                  }
                  else
                  {
                      JOptionPane.showMessageDialog ( null , "No Machine_no and id found for this employee ..Please select another user" );
                        
                  }
                
            }catch(Exception e)
            {
                e.getMessage();
            }
        }
        
      
    }//GEN-LAST:event_jButton9ActionPerformed

     public static void generate_qr(String image_name,String qrCodeData) 
     {
        try 
        {
        
             byte [] QRresult =ZXingHelper.getQRCodeImage(qrCodeData, 300, 350);
                      final ImageIcon icon = new ImageIcon(QRresult);
                      Image image2 = icon.getImage().getScaledInstance(400,450,0);
            int selection= JOptionPane.showConfirmDialog(null, null,"Click Yes To Save on Desk", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, new ImageIcon(image2));
            if(selection== JOptionPane.YES_OPTION)
            {
           final JFileChooser chooser = new JFileChooser() {
              public void approveSelection() 
              {
                if (getSelectedFile().isFile()) 
                {
                    return;
                } else
                    super.approveSelection();
              }
             };

            //chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Select Folder");
            chooser.setApproveButtonText("Save");
            chooser.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.showOpenDialog(null);
            
            String filePath = ""+chooser.getSelectedFile().getAbsolutePath();
            String filename=filePath +"\\"+image_name+".jpg";
            
            File outputfile = new File(filename);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 400, 400);
            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", outputfile);
            JOptionPane.showConfirmDialog(null, "QR Code Location : "+filename, "QR Code of "+image_name, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null);
    
            }
    
        } catch (Exception e) 
        {
            System.err.println(e);
            JOptionPane.showMessageDialog ( null ,"Barcode not Saved  "+e.getMessage());
        
        }
    }

//**********************************
    public static String retreiveSuccessMessage ( String json ) {
        String id = "Not able to fetch the response for your request. Please refresh you master form and try again";
        JSONObject jObject = new JSONObject ( json );
        id = jObject.getString ( "118" );
        return id;
    }
        
    public  void deleteMaster( String apiName  ){
        String response  = WebAPITester.prepareWebCall ( apiName,  StaticValues.getHeader () , "");
        JSONObject responseObject = ( JSONObject ) new JSONTokener ( response ).nextValue ();
        JOptionPane.showMessageDialog ( null , retreiveSuccessMessage ( responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () ) );
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
    
    
    public void exportDataExecel()
    {
        progress.updateTitle ( "All Machine data is fetching please wait...");
        progress.updateMessage ("Getting all Machines data");
                String MCH_ID = "",MACHINE_NO="", MAKE = "", BED_SIZE = "", HEATER_QTY = "", YR_OF_COMM = "", CR_MAINT_RTG = "", CR_MCH_OWN = "", MCH_BRKD_RCD = "", PM_ELE = "", PM_MEC = "",PM_HYD="",PM_PNM="",PM_CLIT="",PRSS_GAUGE="",TIMER="",TEMP_CTRL="",OP_RATE_HR="",AVL_HRS="",DAVLHRS="";

                //int die_id;
                int l = 2;

                Map<String, Object[]> data11;
                data11 = new TreeMap<String, Object[]>();

                String[] columns = { "Machine No", "Make", "Bed Size", "Heater QTY", "Year of commission", "Maintenance Rating","Machine Owner","Electrical","Mechanical","Hydraulic","Pneumatic","Clit","Pressure Gauge","Timer","Temperature Controller","Opertaing Rate/HR","Available Hours","Design Availablity in Hours"};
                data11.put("1", new Object[]{ "Machine No", "Make", "Bed Size", "Heater QTY", "Year of commission", "Maintenance Rating","Machine Owner","Electrical","Mechanical","Hydraulic","Pneumatic","Clit","Pressure Gauge","Timer","Temperature Controller","Opertaing Rate/HR","Available Hours","Design Availablity in Hours"});
             

                String result2, addEmpAPICall;
                
                addEmpAPICall = "machines";
                
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
       
                        
                         MCH_ID = emp.get("MCH_ID").toString();
                         MACHINE_NO=emp.get("MACHINE_NO").toString();
                         MAKE = emp.get("MAKE").toString();
                         BED_SIZE = emp.get("BED_SIZE").toString();
                         HEATER_QTY =emp.get("HEATER_QTY").toString();
                         YR_OF_COMM = emp.get("YR_OF_COMM").toString();
                         CR_MAINT_RTG = emp.get("CR_MAINT_RTG").toString();
                         CR_MCH_OWN = emp.get("CR_MCH_OWN").toString();
                         //MCH_BRKD_RCD = emp.get("MCH_BRKD_RCD").toString();
                         PM_ELE = emp.get("PM_ELE").toString();
                         PM_MEC = emp.get("PM_MEC").toString();
                         PM_HYD=emp.get("PM_HYD").toString();
                         PM_PNM=emp.get("PM_PNM").toString();
                         PM_CLIT=emp.get("PM_CLIT").toString();
                         PRSS_GAUGE=emp.get("PRSS_GAUGE").toString();
                         TIMER=emp.get("TIMER").toString();
                         TEMP_CTRL=emp.get("TEMP_CTRL").toString();
                         OP_RATE_HR=emp.get("OP_RATE_HR").toString();
                         AVL_HRS=emp.get("AVL_HRS").toString();
                         DAVLHRS=emp.get("DAVLHRS").toString();


                        data11.put("" + l, new Object[]{ MACHINE_NO, MAKE, BED_SIZE, HEATER_QTY, YR_OF_COMM, CR_MAINT_RTG,CR_MCH_OWN,PM_ELE,PM_MEC ,PM_HYD,PM_PNM,PM_CLIT,PRSS_GAUGE,TIMER,TEMP_CTRL,OP_RATE_HR,AVL_HRS,DAVLHRS});
                        
                        l = l + 1;
                    }
                }
                        
               hideDialog ();
               
               //Blank workbook
                Workbook workbook = new HSSFWorkbook();

                //Create a blank sheet
                HSSFSheet sheet = (HSSFSheet) workbook.createSheet("Machine Master");

                
                CellStyle style = workbook.createCellStyle();
                style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
                style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                Font font = workbook.createFont();
                font.setColor(IndexedColors.AUTOMATIC.getIndex());
                style.setFont(font);

                //Iterate over data and write to sheet
                Set<String> keyset = data11.keySet();
                int rownum = 0;
                for (String key : keyset) {
                    Row row = sheet.createRow(rownum++);
                    Object[] objArr = data11.get(key);
                    int cellnum = 0;
                    for (Object obj : objArr) {
                        Cell cell = row.createCell(cellnum++);
                        if (obj instanceof String) {
                            cell.setCellValue((String) obj);
                        } else if (obj instanceof Integer) {
                            cell.setCellValue((Integer) obj);
                        }
                    }
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
                    file = new File(jfc.getSelectedFile() + "\\" + "Machines Master Data " + strDate2 + ".xls");
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
    /****************************************************************************/
    public void resetFields() {

        jTextField1.setText(""); //machine_no = 
        jTextField2.setText(""); //machine_make = 
        jTextField3.setText(""); //bed_size =
        jTextField4.setText(""); //heater_qty =
        jTextField5.setText(""); // yr_comm =
        jTextField6.setText(""); //c_maint_rt = 
        jTextField7.setText(""); // machine image

        jTextField8.setText("");//c_mach_owner =
        jTextField9.setText(""); //mach_ownr_img =
        jTextField10.setText(""); //prev_maint_rcd =
        jComboBox1.setSelectedItem(0);//pm_f_ele =
        jComboBox2.setSelectedItem(0); //pm_f_mec =
        jComboBox3.setSelectedItem(0); //pm_f_hyd =
        jComboBox4.setSelectedItem(0);//pm_f_pnm =
        jComboBox5.setSelectedItem(0);//pm_f_clit =
        jTextField11.setText("");//press_gg =
        jTextField12.setText("");//timer =
        jTextField13.setText("");//temp_ctrl =
        jTextField15.setText("");//op rate / hr
        jTextField16.setText("");//avali hrs
         jTextField14.setText("");//temp_ctrl =
         jTextField17.setText("");//temp_ctrl =
         jList1.removeAll();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    int selectedEmpRecordId = 0,operation_rate_hr,avail_hrs;
   String  Machine_no,make;
}
