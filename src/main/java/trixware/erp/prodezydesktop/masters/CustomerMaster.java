/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import java.awt.Dimension;
import java.awt.Toolkit;
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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.JFileChooser;
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
import static trixware.erp.prodezydesktop.examples.HomeScreen.home;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.components.ProgressDialog;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class CustomerMaster extends javax.swing.JPanel implements  KeyListener{

    public static void writeErrorLogs( String className, String filename, String exceptionName, String lineNo, String msg, String datetime ) {
        StaticValues.writer.writeExcel ( className , filename , exceptionName , lineNo , msg, datetime );
    }
    
    String resource = "" , resourceType = "" ;
    
    ArrayList<String[]> categories = new ArrayList<String[]>();
    ArrayList<String[]> segments = new ArrayList<String[]>();
    ArrayList<String[]> industries = new ArrayList<String[]>();
    
    /**
     * Creates new form CustomerMaster
     */
    public CustomerMaster() {
        
        initComponents();
        
        resourceType = "Masters" ;
        resource        = "CustomerMaster" ;

        jComboBox3.addActionListener ( customerSelect );
        
        jTextField7.addKeyListener(k) ;
        jTextField8.addKeyListener(k) ;
        jTextField5.addKeyListener(k) ;
        jTextField9.addKeyListener(k) ;
        jTextField11.addKeyListener(k) ;
        
        jTextField7.addFocusListener ( f2 );
         jTextField8.addFocusListener ( f2 );
        //  jTextField5.addFocusListener ( f2 );
        //   jTextField9.addFocusListener ( f2 );
        //    jTextField11.addFocusListener (f2);
            
        jComboBox4.setVisible ( false);
        jComboBox5.setVisible ( false);
        jLabel16.setVisible ( false );
        jLabel17.setVisible ( false );
        
        
            jLabel23.setVisible ( false);
            
            
//             if ( jComboBox3.getItemCount () == 0 ) {
//                String addEmpAPICall = "category";
//                String CategoryResult = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
//                if( ! CategoryResult.contains(   "not found" )  ){
//
//                    HashMap<String , Object> map = new HashMap<String , Object> ();
//                    JSONObject jObject = new JSONObject ( CategoryResult );
//                    Iterator<?> keys = jObject.keys ();
//
//                    while ( keys.hasNext () ) {
//                        String key = ( String ) keys.next ();
//                        Object value = jObject.get ( key );
//                        map.put ( key , value );
//                    }
//
//                    JSONObject st = ( JSONObject ) map.get ( "data" );
//                    JSONArray records = st.getJSONArray ( "records" );
//
//                    JSONObject emp = null;
//                    categories = new ArrayList<String[]>();
//                    for ( int i = 0 ; i < records.length () ; i ++ ) {
//
//                        emp = records.getJSONObject ( i );
//                        jComboBox3.addItem ( emp.get ( "Category" ).toString () );
//                        categories.add(   new String[]{   emp.get ( "Category_ID" ).toString ()  , emp.get ( "Category" ).toString () } );
//                    }
//                    
//            }
//        }
             
              if ( jComboBox4.getItemCount () == 0 ) {
                String addEmpAPICall = "segment";
                String SegmentResult = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                if( ! SegmentResult.contains(   "not found" )  ){

                    HashMap<String , Object> map = new HashMap<String , Object> ();
                    JSONObject jObject = new JSONObject ( SegmentResult );
                    Iterator<?> keys = jObject.keys ();

                    while ( keys.hasNext () ) {
                        String key = ( String ) keys.next ();
                        Object value = jObject.get ( key );
                        map.put ( key , value );
                    }

                    JSONObject st = ( JSONObject ) map.get ( "data" );
                    JSONArray records = st.getJSONArray ( "records" );

                    JSONObject emp = null;
                    segments = new ArrayList<String[]>();
                    for ( int i = 0 ; i < records.length () ; i ++ ) {

                        emp = records.getJSONObject ( i );
                        jComboBox4.addItem ( emp.get ( "Segment" ).toString () );
                        segments.add(   new String[]{   emp.get ( "Segment_ID" ).toString ()  , emp.get ( "Segment" ).toString () } );
                    }

                }
            }
              
               if ( jComboBox5.getItemCount () == 0 ) {
                String addEmpAPICall = "industry";
                String IndustryResult = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                if( ! IndustryResult.contains(   "not found" )  ){

                    HashMap<String , Object> map = new HashMap<String , Object> ();
                    JSONObject jObject = new JSONObject ( IndustryResult );
                    Iterator<?> keys = jObject.keys ();

                    while ( keys.hasNext () ) {
                        String key = ( String ) keys.next ();
                        Object value = jObject.get ( key );
                        map.put ( key , value );
                    }

                    JSONObject st = ( JSONObject ) map.get ( "data" );
                    JSONArray records = st.getJSONArray ( "records" );

                    JSONObject emp = null;
                    industries = new ArrayList<String[]>();
                    for ( int i = 0 ; i < records.length () ; i ++ ) {

                        emp = records.getJSONObject ( i );
                        jComboBox5.addItem ( emp.get ( "Industry" ).toString () );
                        industries.add(   new String[]{   emp.get ( "Industry_ID" ).toString ()  , emp.get ( "Industry" ).toString () } );
                    }
                    
            }
        }
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        jScrollPane3.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane3.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10)); 
        jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane2.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
          //*******************Role wise priority set edited by mayur****************************************       
//        int role;
//        role=Integer.parseInt(Proman_User.getRole());
//        
//        switch (role) {
//            case 1: 
//                
//                jButton3.setEnabled(true);
//                jButton6.setEnabled(true);
//                
//            break;
//
//            case 2:
//                jButton3.setEnabled(true);
//                jButton6.setEnabled(false);
//            break;
//
//            case 3:   
//                jButton3.setEnabled(false);
//                jButton6.setEnabled(false);
//            break;
//
//            default:
//        }
//        
        //*******************end Role wise priority set edited by mayur****************************************
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
           // jcb.setText ( "" );
           jLabel23.setVisible ( false );
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To Search Google or type URL
//change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            String x = jcb.getText ().trim ();

            if ( x.equalsIgnoreCase ( "" ) ) {
            
            }

            try {
                int num = Integer.parseInt ( String.valueOf ( jcb.getText ().toString () ) );
                if ( num < 0 || num == 0) {
                    jcb.setText ( "" );
                    String number = jcb.getText ().toString ()  ;
                    if(number.length () <10 && number.length ()>11){
                        jLabel23.setVisible ( true);
                        jLabel23.setText (  "Please enter valid telephone / cellular phone number !" );
                    }
                }
            } catch ( NumberFormatException ex1 ) {
                jcb.setText ( x );
                writeErrorLogs (CustomerMaster.class.getSimpleName () , CustomerMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            }
        }
    };
    
    public void loadContent() {
//        try {
//            ResultSet result = DB_Operations.getMasters("customer");
//            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
//            if (result != null) {
//                jTable1.setModel(buildTableModel(result));
//            }
//            jTable1.getColumnModel().getColumn(0).setMinWidth(0);
//            jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
//
//        } catch (SQLException e) {
//            writeErrorLogs (CustomerMaster.class.getSimpleName () , CustomerMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
//        }

            String addEmpAPICall = "customers";
            String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

            if ( result2!= null && ! result2.contains( "not found"  )) {
                jTable1.setModel ( buildTableModelfromJSON (result2 ) );
                jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
            }
                
            if( ! jComboBox3.getSelectedItem().toString ().equals( "Select Existing Customer" )   )   {
                jTextField1.setEnabled(  false   ) ;
                jTextField3.setEnabled(  false   ) ;
            } 
    }

    public static DefaultTableModel buildTableModelfromJSON ( String  employeeJSONList )
    {

            Vector<String> columnNames = new Vector<String> ();
            int columnCount = 0;
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        
            
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
            columnNames.add( "CUSTOMER_ID"  );columnNames.add( "PLANT_CODE"  );columnNames.add( "CITY"  );columnNames.add( "CUSTOMER_NAME"  );columnNames.add( "CUST_ADDRESS"  );columnNames.add( "CONTACT_LL"  );columnNames.add( "CONTACT_BRD"  );columnNames.add( "WEEKLY_OFF"  );columnNames.add( "GST_NO"  );columnNames.add( "OUR_VENDOR_CODE"  );columnNames.add( "PAYMENT_TERMS"  );columnNames.add( "CUST_CODE"  );
           
        // data of the table
        for( int i=0; i<records.length (); i++ ){
        Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 0 ; columnIndex <columnNames.size () ; columnIndex ++ ) {
                emp = records.getJSONObject ( i);
                vector.add ( emp.get ( columnNames.get ( columnIndex ) )  );
            }
            jComboBox3.addItem ( emp.get ( "PLANT_CODE" ).toString() +" ( "+emp.get ("CUSTOMER_NAME" ).toString() +" )" );
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

        try{
            rs.close();
        }catch(Exception e){
            writeErrorLogs (CustomerMaster.class.getSimpleName () , CustomerMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
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

        jTextField2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(Toolkit.getDefaultToolkit().getScreenSize());
        setPreferredSize(new java.awt.Dimension(1133, 513));
        setLayout(null);

        jPanel1.setMinimumSize(new java.awt.Dimension(0, 33));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1310, 600));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Customer Name:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 10, 130, 40);

        jTextField1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField1.setNextFocusableComponent(jTextArea1);
        jPanel1.add(jTextField1);
        jTextField1.setBounds(150, 60, 213, 30);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Address:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 100, 130, 30);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setTabSize(0);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setNextFocusableComponent(jComboBox2);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(150, 100, 210, 70);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Contact No (LL)");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(20, 300, 130, 30);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Contact No (BOARD)");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 340, 130, 30);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Weekly Off:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 380, 130, 30);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" }));
        jComboBox1.setNextFocusableComponent(jLabel13);
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(150, 380, 213, 30);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact Person 1"));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(null);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("Contact No");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(20, 70, 110, 30);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("Email");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(20, 110, 110, 30);

        jTextField5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField5.setNextFocusableComponent(jTextField6);
        jPanel2.add(jTextField5);
        jTextField5.setBounds(130, 70, 150, 30);

        jTextField6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField6.setNextFocusableComponent(jTextField19);
        jPanel2.add(jTextField6);
        jTextField6.setBounds(130, 110, 150, 30);

        jLabel18.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel18.setText("Name");
        jPanel2.add(jLabel18);
        jLabel18.setBounds(20, 30, 110, 30);

        jTextField18.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField18.setNextFocusableComponent(jTextField5);
        jPanel2.add(jTextField18);
        jTextField18.setBounds(130, 30, 150, 30);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(380, 60, 340, 150);

        jTextField7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField7.setNextFocusableComponent(jTextField8);
        jPanel1.add(jTextField7);
        jTextField7.setBounds(150, 300, 213, 30);

        jTextField8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField8.setNextFocusableComponent(jComboBox1);
        jPanel1.add(jTextField8);
        jTextField8.setBounds(150, 340, 213, 30);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact Person 2"));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("Contact No");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(20, 70, 110, 30);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("Email");
        jPanel3.add(jLabel9);
        jLabel9.setBounds(20, 110, 110, 30);

        jTextField9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField9.setNextFocusableComponent(jTextField10);
        jPanel3.add(jTextField9);
        jTextField9.setBounds(130, 70, 150, 30);

        jTextField10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField10.setNextFocusableComponent(jTextField20);
        jPanel3.add(jTextField10);
        jTextField10.setBounds(130, 110, 150, 30);

        jLabel19.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel19.setText("Name");
        jPanel3.add(jLabel19);
        jLabel19.setBounds(20, 30, 110, 30);

        jTextField19.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField19.setNextFocusableComponent(jTextField9);
        jPanel3.add(jTextField19);
        jTextField19.setBounds(130, 30, 150, 30);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(380, 220, 340, 150);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact Person 3"));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(null);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setText("Contact No");
        jPanel4.add(jLabel10);
        jLabel10.setBounds(20, 70, 110, 30);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("Email");
        jPanel4.add(jLabel11);
        jLabel11.setBounds(20, 110, 110, 30);

        jTextField11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField11.setNextFocusableComponent(jTextField12);
        jPanel4.add(jTextField11);
        jTextField11.setBounds(130, 70, 150, 30);

        jTextField12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jPanel4.add(jTextField12);
        jTextField12.setBounds(130, 110, 150, 30);

        jLabel20.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel20.setText("Name");
        jPanel4.add(jLabel20);
        jLabel20.setBounds(20, 30, 110, 30);

        jTextField20.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField20.setNextFocusableComponent(jTextField11);
        jPanel4.add(jTextField20);
        jTextField20.setBounds(130, 30, 150, 30);

        jPanel1.add(jPanel4);
        jPanel4.setBounds(380, 390, 340, 150);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("GST No");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(20, 420, 130, 30);

        jTextField13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField13.setNextFocusableComponent(jTextField14);
        jPanel1.add(jTextField13);
        jTextField13.setBounds(150, 420, 213, 30);

        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel13.setText("Our Vendor Code");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(20, 460, 130, 30);

        jTextField14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField14.setNextFocusableComponent(jTextArea2);
        jPanel1.add(jTextField14);
        jTextField14.setBounds(150, 460, 213, 30);

        jLabel14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel14.setText("Credit Period");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(20, 500, 130, 30);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextArea2.setRows(5);
        jTextArea2.setNextFocusableComponent(jTextField15);
        jScrollPane2.setViewportView(jTextArea2);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(150, 500, 210, 40);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Add to Master");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(750, 420, 130, 30);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Cancel");
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(750, 450, 130, 30);

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
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(740, 20, 370, 390);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Export to Excel");
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(910, 450, 130, 30);

        jLabel16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel16.setText("Segment");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(20, 430, 130, 0);

        jLabel17.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel17.setText("Industry");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(20, 470, 130, 0);

        jTextField15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField15.setNextFocusableComponent(jTextField16);
        jTextField15.setPreferredSize(new java.awt.Dimension(0, 24));
        jPanel1.add(jTextField15);
        jTextField15.setBounds(150, 430, 0, 30);

        jTextField16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField16.setNextFocusableComponent(jTextField17);
        jPanel1.add(jTextField16);
        jTextField16.setBounds(150, 470, 0, 30);

        jTextField17.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField17.setNextFocusableComponent(jTextField18);
        jPanel1.add(jTextField17);
        jTextField17.setBounds(510, 470, 0, 30);

        jLabel21.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel21.setText("City");
        jPanel1.add(jLabel21);
        jLabel21.setBounds(20, 180, 130, 30);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nashik", "Chennai", "Jaipur", "Rudrapur", "Zaheerabad", "Mumbai", "Pune", "Nagpur", "Thane", "Pune", "Aurangabad", "Solapur", "Thane", "Palghar", "Thane", "Amravati", "Thane", "Akola", "Thane", "Dhule", "Jalgaon", "Nanded", "Kolhapur", "Latur", "Raigad", "Thane", "Sangli", "Nashik", "Ahmednagar", "Kolhapur", "Chandrapur", "Parbhani" }));
        jComboBox2.setNextFocusableComponent(jTextField7);
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox2);
        jComboBox2.setBounds(150, 180, 213, 30);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("Edit");
        jButton4.setEnabled(false);
        jButton4.setOpaque(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(910, 420, 130, 30);

        jButton5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton5.setText("Reset");
        jButton5.setOpaque(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5);
        jButton5.setBounds(750, 480, 130, 30);

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 0, 51));
        jLabel22.setText("*");
        jPanel1.add(jLabel22);
        jLabel22.setBounds(10, 270, 10, 16);

        jLabel23.setText("Error : ");
        jPanel1.add(jLabel23);
        jLabel23.setBounds(30, 0, 700, 1);

        jComboBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jPanel1.add(jComboBox4);
        jComboBox4.setBounds(150, 430, 210, 0);

        jComboBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jPanel1.add(jComboBox5);
        jComboBox5.setBounds(150, 470, 210, 0);

        jButton6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton6.setText("Delete");
        jButton6.setOpaque(false);
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jPanel1.add(jButton6);
        jButton6.setBounds(910, 480, 130, 30);

        jLabel15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel15.setText("Customer Code");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(20, 220, 130, 30);

        jLabel24.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel24.setText("Plant Code");
        jPanel1.add(jLabel24);
        jLabel24.setBounds(20, 260, 130, 30);
        jPanel1.add(jTextField3);
        jTextField3.setBounds(150, 220, 213, 30);
        jPanel1.add(jTextField4);
        jTextField4.setBounds(150, 260, 213, 30);

        jLabel25.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel25.setText("Customer Name:");
        jPanel1.add(jLabel25);
        jLabel25.setBounds(20, 60, 130, 30);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Existing Customer" }));
        jComboBox3.setBorder(null);
        jPanel1.add(jComboBox3);
        jComboBox3.setBounds(150, 15, 340, 35);

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 0, 51));
        jLabel26.setText("*");
        jPanel1.add(jLabel26);
        jLabel26.setBounds(10, 70, 10, 16);

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 51));
        jLabel27.setText("*");
        jPanel1.add(jLabel27);
        jLabel27.setBounds(10, 230, 10, 16);

        add(jPanel1);
        jPanel1.setBounds(0, 0, 1120, 560);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:

        resetFields();
        jButton1.setEnabled(true);
        jButton2.setEnabled(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss" );
        String strDate2 = sdf2.format(c2.getTime());

        String CUSTOMER_NAME, CUST_ADDRESS, CONTACT_LL, CONTACT_BRD, WEEKLY_OFF, GST_NO, OUR_VENDOR_CODE, PAYMENT_TERMS, CITY, SEGMENT, INDUSTRY, CATEGORY, EDITED_ON, EDITED_BY, PLANT_CODE , CUST_CODE ;
        int ID;

        CUSTOMER_NAME = jTextField1.getText();
        CUST_ADDRESS = jTextArea1.getText();
        CONTACT_LL = jTextField7.getText();
        CONTACT_BRD = jTextField8.getText();
        WEEKLY_OFF = jComboBox1.getSelectedItem().toString();
        GST_NO = jTextField14.getText();
        OUR_VENDOR_CODE = jTextField13.getText();
        PAYMENT_TERMS = jTextArea2.getText();
        
      //  PLANT_CODE=plantcd.getText();
      //  CUST_CODE=custcd.getText();

        CUST_CODE   = jTextField3.getText();
        PLANT_CODE  = jTextField4.getText();
        
        CITY = jComboBox2.getSelectedItem().toString();
//        SEGMENT = jTextField16.getText();
//        INDUSTRY = jTextField17.getText();
//        CATEGORY = jTextField15.getText();
       // SEGMENT = segments.get( jComboBox4.getSelectedIndex ())[0];
       // INDUSTRY = industries.get( jComboBox5.getSelectedIndex ())[0];
        //CATEGORY = categories.get( jComboBox3.getSelectedIndex ())[0];


        ID = selecteCustomerId;
        EDITED_ON = strDate2;
        EDITED_BY = "EDITED_ON";

        if( !CUSTOMER_NAME.equalsIgnoreCase ( "" ) &&  !PLANT_CODE.equalsIgnoreCase ( "" ) &&   !CUST_CODE.equalsIgnoreCase ( "" )   ){

           // String result = DB_Operations.updateCustomerMaster(CUSTOMER_NAME, CUST_ADDRESS, CONTACT_LL, CONTACT_BRD, WEEKLY_OFF, GST_NO, OUR_VENDOR_CODE, PAYMENT_TERMS, CITY, SEGMENT, INDUSTRY, "null", EDITED_ON, EDITED_BY, ID);

            
            try{

                    String addEmpAPICall = "customerupdate?CUSTOMER_ID="+selecteCustomerId+"&CUSTOMER_NAME="+URLEncoder.encode(CUSTOMER_NAME, "UTF-8")
                            +"&CUST_ADDRESS="+URLEncoder.encode(CUST_ADDRESS, "UTF-8")+"&CONTACT_LL="+URLEncoder.encode(CONTACT_LL , "UTF-8")
                            +"&CONTACT_BRD="+URLEncoder.encode(CONTACT_BRD, "UTF-8")+"&WEEKLY_OFF="+URLEncoder.encode( WEEKLY_OFF, "UTF-8")
                            +"&GST_NO="+URLEncoder.encode( GST_NO, "UTF-8")+"&OUR_VENDOR_CODE="+URLEncoder.encode( OUR_VENDOR_CODE, "UTF-8")
                            +"&PAYMENT_TERMS="+URLEncoder.encode( PAYMENT_TERMS, "UTF-8")+"&CITY="+URLEncoder.encode( CITY, "UTF-8")
                            +"&SEGMENT=&INDUSTRY=&CATEGORY=&CREATED_ON=&EDITED_ON=&EDITED_BY="
                             +"&PLANT_CODE="+PLANT_CODE+"&CUST_CODE="+CUST_CODE;
                    String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    JOptionPane.showMessageDialog ( null , result2);

            }catch(UnsupportedEncodingException e){

            }
            
            
            String ContactPerson1_NAME = jTextField18.getText();
            String ContactPerson1_CONTACT_NO = jTextField5.getText();
            String ContactPerson1_EMAIL = jTextField6.getText();
            DB_Operations.updateContactMaster(ContactPerson1_EMAIL, ContactPerson1_CONTACT_NO, ContactPerson1_NAME, selecteCustomerId, 0, contactIDs[0]);

            ContactPerson1_NAME = jTextField19.getText();
            ContactPerson1_CONTACT_NO = jTextField9.getText();
            ContactPerson1_EMAIL = jTextField10.getText();
            DB_Operations.updateContactMaster(ContactPerson1_EMAIL, ContactPerson1_CONTACT_NO, ContactPerson1_NAME, selecteCustomerId, 0, contactIDs[1]);

            ContactPerson1_NAME = jTextField20.getText();
            ContactPerson1_CONTACT_NO = jTextField11.getText();
            ContactPerson1_EMAIL = jTextField12.getText();
            DB_Operations.updateContactMaster(ContactPerson1_EMAIL, ContactPerson1_CONTACT_NO, ContactPerson1_NAME, selecteCustomerId, 0, contactIDs[2]);

            //JOptionPane.showMessageDialog(null, "" + result);

            resetFields();
            loadContent();

        }

    }//GEN-LAST:event_jButton4ActionPerformed

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
//            ResultSet result = DB_Operations.getMasters("customer");
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
//            writeErrorLogs (CustomerMaster.class.getSimpleName () , CustomerMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
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
//        sheet1 = wb.createSheet("Customer Master");
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
//            file = new File("Customer Master File " + strDate2 + ".xls");
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
//                writeErrorLogs (CustomerMaster.class.getSimpleName () , CustomerMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
//            } finally {
//
//            }
//        }
//        JOptionPane.showMessageDialog(null, "New File generated at " + file.getAbsolutePath());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        
        selectRecordInTable();
        
    }//GEN-LAST:event_jTable1MouseClicked

    public void selectRecordInTable(){
        
        //jButton1.setEnabled(false);

        DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();
        int selectedRowIndex = jTable1.getSelectedRow ();
        selecteCustomerId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );

        /* CUSTOMER NAME */ jTextField1.setText( model.getValueAt ( selectedRowIndex , 3 ).toString () );
        /* CUSTOMER address */ jTextArea1.setText( model.getValueAt ( selectedRowIndex ,4 ).toString () );
        /* CUSTOMER contact ll */ jTextField7.setText( model.getValueAt ( selectedRowIndex , 5 ).toString () );
        /* CUSTOMER contact brd */ jTextField8.setText( model.getValueAt ( selectedRowIndex , 6 ).toString () );
        String weekOff = model.getValueAt ( selectedRowIndex , 7 ).toString () ;
        for ( int i = 0 ; i < jComboBox1.getItemCount () ; i ++ ) {
            if( weekOff.equals( jComboBox1.getItemAt ( i ) )  ){
                jComboBox1.setSelectedIndex ( i );
            }
        }
        
        ///* CUSTOMER weekly off */ jComboBox1.setSelectedIndex(0);
        /* CUSTOMER GST */ jTextField13.setText(  model.getValueAt ( selectedRowIndex , 8 ).toString ()  );
        /* CUSTOMER VENDOR CODE */ jTextField14.setText(  model.getValueAt ( selectedRowIndex , 9 ).toString ()  );
      //  /* CUSTOMER plant CODE */plantcd.setText(model.getValueAt ( selectedRowIndex , 9 ).toString () );
      //  /* CUSTOMER cust CODE */custcd.setText(model.getValueAt ( selectedRowIndex , 10 ).toString () );
        /* CUSTOMER credit period
         * 
         */ jTextArea2.setText ( model.getValueAt ( selectedRowIndex , 10 ).toString () );
         jTextField3.setText(  model.getValueAt ( selectedRowIndex , 11 ).toString ()  );
         jTextField4.setText(  model.getValueAt ( selectedRowIndex , 1 ).toString ()  );
         
         String city = model.getValueAt ( selectedRowIndex , 2 ).toString () ;
               
         for ( int i=0; i< jComboBox2.getItemCount(); i++   ){
             if( city.equals ( jComboBox2.getItemAt ( i) )   ){
                 jComboBox2.setSelectedIndex ( i);
             }
         }
                 
         
        String addEmpAPICall = "contacts?customer_id=" + selecteCustomerId;
        String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

        if ( result2 != null &&  ! result2.contains ( "not found" ) ) {

            HashMap<String , Object> map = new HashMap<String , Object> ();
            JSONObject jObject = new JSONObject ( result2 );
            Iterator<?> keys = jObject.keys ();

            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }

            JSONObject st = ( JSONObject ) map.get ( "data" );
            JSONArray records = st.getJSONArray ( "records" );

            JSONObject emp = null;

            // data of the table
                    try{
                        emp = records.getJSONObject ( 0 );
                        jTextField18.setText( emp.get("CONT_NAME" ).toString());
                        jTextField5.setText(emp.get( "CONT_NO" ).toString());
                        jTextField6.setText(emp.get( "CONT_EMAIL" ).toString());

                    }catch( Exception e){
                        System.out.println ( "Contact Person 1 not found" );
                    }
                    
                     try{
                        emp = records.getJSONObject ( 1 );
                        jTextField19.setText( emp.get("CONT_NAME" ).toString());
                        jTextField9.setText(emp.get( "CONT_NO" ).toString());
                        jTextField10.setText(emp.get( "CONT_EMAIL" ).toString());
                        
                    }catch( Exception e){
                        System.out.println ( "Contact Person 2 not found" );
                    }
                     
                      try{
                        emp = records.getJSONObject ( 2 );
                        jTextField20.setText(emp.get("CONT_NAME" ).toString());
                        jTextField11.setText(emp.get( "CONT_NO" ).toString());
                        jTextField12.setText(emp.get( "CONT_EMAIL" ).toString());

                    }catch( Exception e){
                        System.out.println ( "Contact Person 3 not found" );
                    }
                    
                }

        jButton4.setEnabled(true);
        
    }
    
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        home.removeForms();
    }//GEN-LAST:event_jButton2ActionPerformed

    public int iDFromJSON ( String json ) {
        int id = 0;

        JSONObject jObject = new JSONObject ( json );
        Iterator<?> keys = jObject.keys ();

        JSONObject value = ( JSONObject ) jObject.get ( "data" );

        id = Integer.parseInt ( value.get ( "insert_id" ).toString () );
        //    id = Integer.parseInt( st.getString ("insert_id" ));

        return id;
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss" );
        String strDate2 = sdf2.format(c2.getTime());

        String success = " Failed to save the record to DataBase ";

        int result = 0;

        String CUSTOMER_NAME, CUST_ADDRESS, CONTACT_LL, CONTACT_BRD, WEEKLY_OFF, GST_NO, OUR_VENDOR_CODE, PAYMENT_TERMS, CITY, SEGMENT, INDUSTRY, CATEGORY, CREATED_ON, EDITED_ON, EDITED_BY, PLANT_CODE, CUST_CODE;

        CUSTOMER_NAME = jTextField1.getText();
        CUST_ADDRESS = jTextArea1.getText();
        CONTACT_LL = jTextField7.getText();
        CONTACT_BRD = jTextField8.getText();
        WEEKLY_OFF = jComboBox1.getSelectedItem().toString();
        GST_NO = jTextField14.getText();
        OUR_VENDOR_CODE = jTextField13.getText();
        PAYMENT_TERMS = jTextArea2.getText();
        
        //new
//        PLANT_CODE=plantcd.getText();
//        CUST_CODE=custcd.getText();
//        

        CUST_CODE   = jTextField3.getText();
        PLANT_CODE = jTextField4.getText();
        
        CITY = jComboBox2.getSelectedItem().toString();
//        SEGMENT = jTextField16.getText();
//        INDUSTRY = jTextField17.getText();
//        CATEGORY = jTextField15.getText();

        //SEGMENT = segments.get( jComboBox4.getSelectedIndex ())[0];
        //INDUSTRY = industries.get( jComboBox5.getSelectedIndex ())[0];
        //CATEGORY = categories.get( jComboBox3.getSelectedIndex ())[0];
        

        CREATED_ON = strDate2;
        EDITED_ON = strDate2;
        EDITED_BY =  Proman_User.getEID ()+"" ;

            //                  if( !CUSTOMER_NAME.equalsIgnoreCase ( "")  &&
            //                !CUST_ADDRESS.equalsIgnoreCase ( "")  &&
            //                !CONTACT_LL.equalsIgnoreCase ( "")  &&
            //                !CONTACT_BRD.equalsIgnoreCase ( "")  &&
            //                !WEEKLY_OFF.equalsIgnoreCase ( "")  &&
            //                !GST_NO.equalsIgnoreCase ( "")  &&
            //                !OUR_VENDOR_CODE.equalsIgnoreCase ( "")  &&
            //                !PAYMENT_TERMS.equalsIgnoreCase ( "")  &&
            //                !CITY.equalsIgnoreCase ( "")  &&
            //                !SEGMENT.equalsIgnoreCase ( "")  &&
            //                !INDUSTRY.equalsIgnoreCase ( "")  &&
            //                !CREATED_ON.equalsIgnoreCase ( "")  &&
            //                !EDITED_ON.equalsIgnoreCase ( "")  &&
            //                !EDITED_BY.equalsIgnoreCase ( "")
            //                ){

            if( !CUSTOMER_NAME.equalsIgnoreCase ( "")   &&  !PLANT_CODE.equalsIgnoreCase ( "" ) &&   !CUST_CODE.equalsIgnoreCase ( "" )   ){

//                result = DB_Operations.insertIntoCustomerMaster(CUSTOMER_NAME, CUST_ADDRESS, CONTACT_LL, CONTACT_BRD, WEEKLY_OFF, GST_NO, OUR_VENDOR_CODE, PAYMENT_TERMS, CITY, "null", CREATED_ON, EDITED_ON, EDITED_BY);
                //changes starts

                JOptionPane.showMessageDialog(null, " Successfully inserted customer details ! " );
                
                 try{
                    
                     String addEmpAPICall = "" ;
                     
                     if( ! jComboBox3.getSelectedItem().toString ().equals( "Select Existing Customer" )   )   {
                        
                            addEmpAPICall = "customeradd?CUSTOMER_NAME="+URLEncoder.encode(CUSTOMER_NAME, "UTF-8")+"&CUST_ADDRESS="+URLEncoder.encode(CUST_ADDRESS, "UTF-8")
                            +"&CONTACT_LL="+URLEncoder.encode(CONTACT_LL , "UTF-8")+"&CONTACT_BRD="+URLEncoder.encode(CONTACT_BRD, "UTF-8")
                            +"&WEEKLY_OFF="+URLEncoder.encode( WEEKLY_OFF, "UTF-8")+"&GST_NO="+URLEncoder.encode( GST_NO, "UTF-8")
                            +"&OUR_VENDOR_CODE="+URLEncoder.encode( OUR_VENDOR_CODE, "UTF-8")+"&PAYMENT_TERMS="+URLEncoder.encode( PAYMENT_TERMS, "UTF-8")
                            +"&CITY="+URLEncoder.encode( CITY, "UTF-8")+"&SEGMENT=&INDUSTRY=&CATEGORY=&CREATED_ON="+URLEncoder.encode( CREATED_ON, "UTF-8")
                            +"&EDITED_ON="+URLEncoder.encode( EDITED_ON, "UTF-8")+"&EDITED_BY="+URLEncoder.encode(EDITED_BY, "UTF-8")
                            +"&PLANT_CODE="+PLANT_CODE+"&CUST_CODE="+CUST_CODE;
                         
                        
                            
                     }else{
                         
                            addEmpAPICall = "customeradd?CUSTOMER_NAME="+URLEncoder.encode(CUSTOMER_NAME, "UTF-8")+"&CUST_ADDRESS="+URLEncoder.encode(CUST_ADDRESS, "UTF-8")
                            +"&CONTACT_LL="+URLEncoder.encode(CONTACT_LL , "UTF-8")+"&CONTACT_BRD="+URLEncoder.encode(CONTACT_BRD, "UTF-8")
                            +"&WEEKLY_OFF="+URLEncoder.encode( WEEKLY_OFF, "UTF-8")+"&GST_NO="+URLEncoder.encode( GST_NO, "UTF-8")
                            +"&OUR_VENDOR_CODE="+URLEncoder.encode( OUR_VENDOR_CODE, "UTF-8")+"&PAYMENT_TERMS="+URLEncoder.encode( PAYMENT_TERMS, "UTF-8")
                            +"&CITY="+URLEncoder.encode( CITY, "UTF-8")+"&SEGMENT=&INDUSTRY=&CATEGORY=&CREATED_ON="+URLEncoder.encode( CREATED_ON, "UTF-8")
                            +"&EDITED_ON="+URLEncoder.encode( EDITED_ON, "UTF-8")+"&EDITED_BY="+URLEncoder.encode(EDITED_BY, "UTF-8")
                            +"&PLANT_CODE="+PLANT_CODE+"&CUST_CODE="+CUST_CODE;
                         
                            
                     }
                     
                     
                            
                    String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    JOptionPane.showMessageDialog ( null , result2);
                
                    result = iDFromJSON( result2 );    
                    
                }catch(UnsupportedEncodingException e){
                    
                }
                
                String ContactPerson1_NAME = jTextField18.getText();
                String ContactPerson1_CONTACT_NO = jTextField5.getText();
                String ContactPerson1_EMAIL = jTextField6.getText();

                if (!ContactPerson1_NAME.equals("") || !ContactPerson1_CONTACT_NO.equals("") || !ContactPerson1_EMAIL.equals("")) {

                    String CONT_EMAIL, CONT_NO, CONT_NAME;

                    CONT_EMAIL = ContactPerson1_EMAIL;
                    CONT_NO = ContactPerson1_CONTACT_NO;
                    CONT_NAME = ContactPerson1_NAME;

                    if (result != 0) {

                        int supp_id = 0;
                        
                        
//                        success = DB_Operations.insertIntoContactMaster(CONT_EMAIL, CONT_NO, result, 0, CONT_NAME, CREATED_ON, EDITED_ON, EDITED_BY);
                         try{
                            String addContactPersonCall = "contactadd?CONT_EMAIL="+URLEncoder.encode(ContactPerson1_EMAIL, "UTF-8")+"&CONT_NO="+URLEncoder.encode(CONT_NO, "UTF-8")+"&CUST_ID="+URLEncoder.encode(result+"", "UTF-8")+"&SUPP_ID="+URLEncoder.encode(supp_id+"", "UTF-8")+"&CONT_NAME="+URLEncoder.encode(CONT_NAME,"UTF-8")+"&CREATED_ON="+URLEncoder.encode(CREATED_ON, "UTF-8")+"&EDITED_ON="+URLEncoder.encode(EDITED_ON, "UTF-8")+"&EDITED_BY="+URLEncoder.encode(EDITED_BY, "UTF-8");
                            String result2 =  WebAPITester.prepareWebCall ( addContactPersonCall, StaticValues.getHeader ()   , "");
                            JOptionPane.showMessageDialog ( null , result2);
                        }catch(UnsupportedEncodingException  ex){
                            
                        }
                        
                        
                        ContactPerson1_NAME = jTextField19.getText();
                        ContactPerson1_CONTACT_NO = jTextField9.getText();
                        ContactPerson1_EMAIL = jTextField10.getText();
                        
                        if (!ContactPerson1_NAME.equals("") || !ContactPerson1_CONTACT_NO.equals("") || !ContactPerson1_EMAIL.equals("")) {
//                            success = DB_Operations.insertIntoContactMaster(ContactPerson1_EMAIL, ContactPerson1_CONTACT_NO, result, 0, ContactPerson1_NAME, CREATED_ON, EDITED_ON, EDITED_BY);

                            try{
                                String addContactPersonCall = "contactadd?CONT_EMAIL="+URLEncoder.encode(ContactPerson1_EMAIL, "UTF-8")+"&CONT_NO="+URLEncoder.encode(CONT_NO, "UTF-8")+"&CUST_ID="+URLEncoder.encode(result+"", "UTF-8")+"&SUPP_ID="+URLEncoder.encode(supp_id+"", "UTF-8")+"&CONT_NAME="+URLEncoder.encode(CONT_NAME,"UTF-8")+"&CREATED_ON="+URLEncoder.encode(CREATED_ON, "UTF-8")+"&EDITED_ON="+URLEncoder.encode(EDITED_ON, "UTF-8")+"&EDITED_BY="+URLEncoder.encode(EDITED_BY, "UTF-8");
                                String result2 =  WebAPITester.prepareWebCall ( addContactPersonCall, StaticValues.getHeader ()   , "");
                                JOptionPane.showMessageDialog ( null , result2);
                            }catch(UnsupportedEncodingException  ex){

                            }
                        }
                        
                        
                        
                        ContactPerson1_NAME = jTextField20.getText();
                        ContactPerson1_CONTACT_NO = jTextField11.getText();
                        ContactPerson1_EMAIL = jTextField12.getText();
                        
                        if (!ContactPerson1_NAME.equals("") || !ContactPerson1_CONTACT_NO.equals("") || !ContactPerson1_EMAIL.equals("")) {
//                            success = DB_Operations.insertIntoContactMaster(ContactPerson1_EMAIL, ContactPerson1_CONTACT_NO, result, 0, ContactPerson1_NAME, CREATED_ON, EDITED_ON, EDITED_BY);
                        
                            try{
                                String addContactPersonCall = "contactadd?CONT_EMAIL="+URLEncoder.encode(ContactPerson1_EMAIL, "UTF-8")+"&CONT_NO="+URLEncoder.encode(CONT_NO, "UTF-8")+"&CUST_ID="+URLEncoder.encode(result+"", "UTF-8")+"&SUPP_ID="+URLEncoder.encode(supp_id+"", "UTF-8")+"&CONT_NAME="+URLEncoder.encode(CONT_NAME,"UTF-8")+"&CREATED_ON="+URLEncoder.encode(CREATED_ON, "UTF-8")+"&EDITED_ON="+URLEncoder.encode(EDITED_ON, "UTF-8")+"&EDITED_BY="+URLEncoder.encode(EDITED_BY, "UTF-8");
                                String result2 =  WebAPITester.prepareWebCall ( addContactPersonCall, StaticValues.getHeader ()   , "");
                                JOptionPane.showMessageDialog ( null , result2);
                            }catch(UnsupportedEncodingException  ex){

                            }
                        }
                    }
                } else {

                }
                

            }else{
                JOptionPane.showMessageDialog(null, "<html><font face='Times New Roman' size='05'>please fill in all mandatory fields marked with <html><font face='Times New Roman' size='6' color='red'>*" );
            }
            resetFields();
            loadContent();

            /* changes end */

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        // TODO add your handling code here:
        
        int selection = JOptionPane.showConfirmDialog ( null , "Deleting a record is not recommendedThe 'Delete' action may cause loss of any dependencies and may affect the transactional Data and Reports and make data inconsistent\nDeleting a plant will automatically delete all shifts for that plant\nPress 'No' to revert, Press 'Yes' to continue deleting", "Are you sure about deleting this record ?", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE);
        if ( selection == JOptionPane.YES_OPTION ) { 
            //createDialog();
            //dlg.setVisible(true);
            deleteMaster(  "customerdelete?CUSTOMER_ID="+selecteCustomerId    ) ;    
            
            String response  = WebAPITester.prepareWebCall ( "contacts?customer_id="+selecteCustomerId,  StaticValues.getHeader () , "");
            HashMap<String , Object> map = new HashMap<String , Object> ();
            JSONObject jObject = new JSONObject ( response );
            Iterator<?> keys = jObject.keys ();
            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }
            JSONObject st = ( JSONObject ) map.get ( "data" );
            JSONArray records = st.getJSONArray ( "records" );
            JSONObject emp = null;

            int count =0 ;
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );
                deleteShifts(  "contactdelete?CONT_PER_ID="+emp.get("CONT_PER_ID")    ) ;  
                count++ ;
            }
            
            if(count>0){
                JOptionPane.showMessageDialog ( null , " "+count+" Contacts deleted successfuly" ) ;
            }
            
            String addEmpAPICall = "customers";
                String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

                if ( result2!= null && ! result2.contains( "not found"  )) {
                    jTable1.setModel ( buildTableModelfromJSON (result2 ) );
                    jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                    jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                }
                
                resetFields ();
        }
        
    }//GEN-LAST:event_jButton6MouseClicked

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    public static  String retreiveSuccessMessage( String json){
        String id = "Not able to fetch the response for your request. Please refresh you master form and try again";
        JSONObject jObject = new JSONObject ( json );
        id = jObject.getString("118" );
        return id ;
    }
    
    public  void deleteMaster( String apiName  ){
        String response  = WebAPITester.prepareWebCall ( apiName,  StaticValues.getHeader () , "");
        JSONObject responseObject = ( JSONObject ) new JSONTokener ( response ).nextValue ();
        JOptionPane.showMessageDialog ( null , retreiveSuccessMessage ( responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () ) );
    }
   
    
    public  String deleteShifts( String apiName  ){
        String response  = WebAPITester.prepareWebCall ( apiName,  StaticValues.getHeader () , "");
        JSONObject responseObject = ( JSONObject ) new JSONTokener ( response ).nextValue ();
        //JOptionPane.showMessageDialog ( null , retreiveSuccessMessage ( responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () ) );
        return retreiveSuccessMessage ( responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () ) ;
    }

    public void submitForm(){
        JOptionPane.showMessageDialog ( null , "You've Submitted the name " );
    }

    
    public void resetFields() {

        jTextField1.setText("");
        jTextArea1.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jComboBox1.getSelectedItem().toString();
        jTextField14.setText("");
        jTextField13.setText("");
        jTextArea2.setText("");

        jComboBox2.getSelectedItem().toString();
        jTextField16.setText("");
        jTextField17.setText("");
        jTextField15.setText("");

        jTextField18.setText("");
        jTextField5.setText("");
        jTextField6.setText("");

        jTextField19.setText("");
        jTextField9.setText("");
        jTextField10.setText("");

        jTextField20.setText("");
        jTextField11.setText("");
        jTextField12.setText("");

        jTextField3.setText("");
        jTextField4.setText("");
        
        jComboBox3.setSelectedIndex ( 0);
        jTable1.clearSelection ();
        selecteCustomerId = 0;
    }

    
    ActionListener customerSelect = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
       
            if( ! jComboBox3.getSelectedItem().toString ().equals( "Select Existing Customer" )   )   {
                jTextField1.setEnabled(  false   ) ;
                jTextField3.setEnabled(  false   ) ;
                
                jTable1.changeSelection ( jComboBox3.getSelectedIndex ()-1, 0, false, false );
                selectRecordInTable ();
                
            }else{
                jTextField1.setEnabled(  true   ) ;
                jTextField3.setEnabled(  true   ) ;
                resetFields () ; 
            } 
            
        }
    };
    
   
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
        progress.updateTitle ( "All Customer data is fetching please wait...");
        progress.updateMessage ("Getting all Customer data");
               
                String CUSTOMER_ID, CUSTOMER_NAME, CUST_ADDRESS, CONTACT_LL, CONTACT_BRD, WEEKLY_OFF, GST_NO, OUR_VENDOR_CODE, PAYMENT_TERMS, CITY, SEGMENT, INDUSTRY, CATEGORY, PLANT_CODE, CUST_CODE;    
                
                int l = 2;

                Map<String, Object[]> data11;
                data11 = new TreeMap<String, Object[]>();

                String[] columns = {"CUSTOMER NAME", "CUSTOMER ADDRESS", "CONTACT_Landline", "CONTACT BOARD", "WEEKLY OFF", "GST NO", "VENDOR CODE","PAYMENT TERMS","CITY","PLANT CODE","CUSTOMER CODE"};
                data11.put("1", new Object[]{"CUSTOMER NAME", "CUSTOMER ADDRESS", "CONTACT_Landline", "CONTACT BOARD", "WEEKLY OFF", "GST NO", "VENDOR CODE","PAYMENT TERMS","CITY","PLANT CODE","CUSTOMER CODE"});
             

                String result2, addEmpAPICall;
                
                addEmpAPICall = "customers";
                
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
                        CUSTOMER_NAME = emp.get("CUSTOMER_NAME").toString();
                        CUST_ADDRESS= emp.get("CUST_ADDRESS").toString();
                        CONTACT_LL= emp.get("CONTACT_LL").toString();
                        CONTACT_BRD= emp.get("CONTACT_BRD").toString();
                        WEEKLY_OFF= emp.get("WEEKLY_OFF").toString();
                        GST_NO= emp.get("GST_NO").toString();
                        OUR_VENDOR_CODE= emp.get("OUR_VENDOR_CODE").toString();
                        PAYMENT_TERMS= emp.get("PAYMENT_TERMS").toString();
                        CITY= emp.get("CITY").toString();
                        SEGMENT= emp.get("SEGMENT").toString();
                        INDUSTRY= emp.get("INDUSTRY").toString();
                        CATEGORY = emp.get("CATEGORY").toString();
                        PLANT_CODE= emp.get("PLANT_CODE").toString();
                        CUST_CODE= emp.get("CUST_CODE").toString();    
                         


                        data11.put("" + l, new Object[]{CUSTOMER_NAME,CUST_ADDRESS,CONTACT_LL,CONTACT_BRD,WEEKLY_OFF,GST_NO,OUR_VENDOR_CODE,PAYMENT_TERMS,CITY,PLANT_CODE,CUST_CODE });
                        
                        l = l + 1;
                    }
                }
                        
               hideDialog ();
               
               //Blank workbook
                Workbook workbook = new HSSFWorkbook();

                //Create a blank sheet
                HSSFSheet sheet = (HSSFSheet) workbook.createSheet("Customer Master");

                
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
                    file = new File(jfc.getSelectedFile() + "\\" + "Customer Master Data " + strDate2 + ".xls");
                    FileOutputStream fileop = new FileOutputStream(file);
                    workbook.write(fileop);
                    workbook.close();
                    fileop.close();
                    JOptionPane.showMessageDialog(null, "New File generated at " + file.getAbsolutePath());
                    }
                    
                } catch (IOException Ie) {
                    Ie.printStackTrace();
                }

                
               
               
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
    public static javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    int selecteCustomerId = 0;
    int contactIDs[] = new int[]{0, 0, 0};

    @Override
    public void keyTyped ( KeyEvent e ) {
     //   throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
     if( e.getKeyCode () == KeyEvent.VK_ENTER ) {
    //         submitForm ();
     }
    }

    @Override
    public void keyPressed ( KeyEvent e ) {
     //   throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased ( KeyEvent e ) {
    //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }
}
