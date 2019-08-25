/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import java.awt.Dimension;
import java.awt.Toolkit;
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
import javax.swing.DefaultListModel;
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
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.components.ProgressDialog;
import trixware.erp.prodezydesktop.examples.HomeScreen;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class SupplierMaster extends javax.swing.JPanel {

    String resource = "" , resourceType = "" ;
    
    ArrayList<String[]> categories = new ArrayList<String[]>();
    ArrayList<String[]> segments = new ArrayList<String[]>();
    ArrayList<String[]> industries = new ArrayList<String[]>();
    ArrayList<String[]> rmlist = new ArrayList<String[]>();
    
    /**
     * Creates new form CustomerMaster
     */
    public SupplierMaster() {
        initComponents();
        
        resourceType = "Masters" ;
        resource =  "SupplierMaster" ;

//	try{
//      		StaticValues.checkUserRolePermission(resourceType, resourceType);
//	   }catch(Exception e){
//      		StaticValues.writer.writeExcel (resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//   	   }
      
   jTextField7.addKeyListener(k) ;
        jTextField8.addKeyListener(k) ;
        jTextField21.addKeyListener(k) ;
        jTextField9.addKeyListener(k) ;
        jTextField11.addKeyListener(k) ;
        
        jTextField7.addFocusListener ( f2 );
         jTextField8.addFocusListener ( f2 );
          jTextField21.addFocusListener ( f2 );
           jTextField9.addFocusListener ( f2 );
            jTextField11.addFocusListener (f2);
            
            
            
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
             
              if ( jComboBox6.getItemCount () == 0 ) {
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
                        jComboBox6.addItem ( emp.get ( "Segment" ).toString () );
                        segments.add(   new String[]{   emp.get ( "Segment_ID" ).toString ()  , emp.get ( "Segment" ).toString () } );
                    }
                    
            }
        }
              
               if ( jComboBox7.getItemCount () == 0 ) {
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
                        jComboBox7.addItem ( emp.get ( "Industry" ).toString () );
                        industries.add(   new String[]{   emp.get ( "Industry_ID" ).toString ()  , emp.get ( "Industry" ).toString () } );
                    }
                    
            }
               }
                
                
                
                 if ( jComboBox2.getItemCount () == 0 ) {
                String rmlistCall = "rawmaterials";
                String rmResult = WebAPITester.prepareWebCall ( rmlistCall , StaticValues.getHeader () , "" );
                if( ! rmResult.contains(   "not found" )  ){

                    HashMap<String , Object> map = new HashMap<String , Object> ();
                    JSONObject jObject = new JSONObject ( rmResult );
                    Iterator<?> keys = jObject.keys ();

                    while ( keys.hasNext () ) {
                        String key = ( String ) keys.next ();
                        Object value = jObject.get ( key );
                        map.put ( key , value );
                    }

                    JSONObject st = ( JSONObject ) map.get ( "data" );
                    JSONArray records = st.getJSONArray ( "records" );

                    JSONObject emp = null;
                    rmlist = new ArrayList<String[]>();
                    for ( int i = 0 ; i < records.length () ; i ++ ) {

                        emp = records.getJSONObject ( i );
                        jComboBox2.addItem ( emp.get ( "RM_NAME" ).toString () );
                        rmlist.add(   new String[]{   emp.get ( "RM_ID" ).toString ()  , emp.get ( "RM_NAME" ).toString () } );
                    }
                    
            }
        }
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        jScrollPane3.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane3.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10)); 
        jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane2.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));

            
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
                StaticValues.writer.writeExcel (SupplierMaster.class.getSimpleName () , SupplierMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            }
        }
    };

    public void loadContent() {
//        try {
//            ResultSet result = DB_Operations.getMasters("supplier");
//
//            if (result != null) {
//                jTable1.setModel(buildTableModel(result));
//
//                jTable1.getColumnModel().getColumn(0).setMinWidth(0);
//                jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
//            }
//
//            result = DB_Operations.getMasters("raw_material");
//
//            while (result.next()) {
//                //jComboBox2.addItem(result.getString("RM_TYPE") + " ( " + result.getString("RM_CTG") + " )");
//                jComboBox2.addItem(result.getString( "RM_NAME"));
//            }
//
//        } catch (Exception e) {
//            StaticValues.writer.writeExcel (SupplierMaster.class.getSimpleName () , SupplierMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
//        }


              String addEmpAPICall = "suppliers";
                String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

                if ( result2!= null && ! result2.contains( "not found"  )) {
                    jTable1.setModel ( buildTableModelfromJSON (result2 ) );
                    jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                    jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                }



        jButton1.setEnabled(false);
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
                columnNames.add( "SUPP_ID"  );columnNames.add( "SUPP_NAME"  );columnNames.add( "SUPP_ADD"  );columnNames.add( "SUPP_CNT_LL"  );columnNames.add( "SUPP_CNT_BRD"  );columnNames.add( "WEEK_OFF"  );columnNames.add( "GST_NO"  );columnNames.add( "VENDOR_CODE"  );columnNames.add( "PAYT_TERMS"  );
           
                

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
        jLabel21 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
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
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jTextField15 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField5 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel23 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();

        jLabel21.setText("City");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mumbai", "Pune", "Nagpur", "Nashik", "Thane", "Pune", "Aurangabad", "Solapur", "Thane", "Palghar", "Thane", "Amravati", "Thane", "Akola", "Thane", "Dhule", "Jalgaon", "Nanded", "Kolhapur", "Latur", "Raigad", "Thane", "Sangli", "Nashik", "Ahmednagar", "Kolhapur", "Chandrapur", "Parbhani" }));

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1174, 577));
        setLayout(null);

        jPanel1.setMinimumSize(Toolkit.getDefaultToolkit().getScreenSize());
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1310, 600));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Supplier Name:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 10, 130, 30);

        jTextField1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField1.setNextFocusableComponent(jTextArea1);
        jPanel1.add(jTextField1);
        jTextField1.setBounds(150, 10, 213, 30);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Address:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 50, 130, 30);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(213, 100));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setNextFocusableComponent(jComboBox5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(150, 50, 210, 90);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Contact No (LL)");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(20, 190, 130, 30);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Contact No (BOARD)");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 230, 130, 30);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Weekly Off:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 270, 130, 30);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" }));
        jComboBox1.setNextFocusableComponent(jTextField13);
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(150, 270, 213, 30);

        jTextField7.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField7.setNextFocusableComponent(jTextField8);
        jPanel1.add(jTextField7);
        jTextField7.setBounds(150, 190, 213, 30);

        jTextField8.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField8.setNextFocusableComponent(jComboBox1);
        jPanel1.add(jTextField8);
        jTextField8.setBounds(150, 230, 213, 30);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact Person 1"));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("Contact No");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(20, 60, 130, 30);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("Email");
        jPanel3.add(jLabel9);
        jLabel9.setBounds(20, 100, 130, 30);

        jTextField9.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField9.setNextFocusableComponent(jTextField10);
        jPanel3.add(jTextField9);
        jTextField9.setBounds(150, 60, 150, 30);

        jLabel19.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel19.setText("Name");
        jPanel3.add(jLabel19);
        jLabel19.setBounds(20, 20, 130, 30);

        jTextField10.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField10.setNextFocusableComponent(jTextField23);
        jPanel3.add(jTextField10);
        jTextField10.setBounds(150, 100, 150, 30);

        jTextField4.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField4.setNextFocusableComponent(jTextField9);
        jPanel3.add(jTextField4);
        jTextField4.setBounds(150, 20, 150, 30);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(390, 10, 340, 140);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact Person 3"));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(null);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setText("Contact No");
        jPanel4.add(jLabel10);
        jLabel10.setBounds(20, 60, 130, 30);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("Email");
        jPanel4.add(jLabel11);
        jLabel11.setBounds(20, 100, 130, 30);

        jTextField11.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField11.setNextFocusableComponent(jTextField12);
        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField11);
        jTextField11.setBounds(150, 60, 150, 30);

        jTextField12.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField12.setNextFocusableComponent(jTextField15);
        jPanel4.add(jTextField12);
        jTextField12.setBounds(150, 100, 150, 30);

        jLabel18.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel18.setText("Name");
        jPanel4.add(jLabel18);
        jLabel18.setBounds(20, 20, 130, 30);

        jTextField3.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField3.setNextFocusableComponent(jTextField11);
        jPanel4.add(jTextField3);
        jTextField3.setBounds(150, 20, 150, 30);

        jPanel1.add(jPanel4);
        jPanel4.setBounds(390, 290, 340, 140);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("GST No");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(20, 310, 130, 30);

        jTextField13.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField13.setNextFocusableComponent(jTextField14);
        jTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField13);
        jTextField13.setBounds(150, 310, 213, 30);

        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel13.setText("Our Vendor Code");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(20, 350, 130, 30);

        jTextField14.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField14.setNextFocusableComponent(jTextField5);
        jTextField14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField14ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField14);
        jTextField14.setBounds(150, 350, 213, 30);

        jLabel14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel14.setText("Payment Terms");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(390, 450, 130, 30);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Edit Record");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(950, 450, 120, 30);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Add to Master");
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(790, 450, 120, 30);

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
        jScrollPane3.setBounds(760, 20, 390, 403);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Export to Excel");
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(950, 480, 120, 30);

        jTextField15.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField15.setNextFocusableComponent(jTextField16);
        jTextField15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField15ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField15);
        jTextField15.setBounds(510, 440, 0, 30);

        jLabel16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel16.setText("Segment");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(390, 450, 0, 0);

        jTextField16.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField16.setNextFocusableComponent(jTextField17);
        jPanel1.add(jTextField16);
        jTextField16.setBounds(510, 480, 0, 30);

        jLabel17.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel17.setText("Industry");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(390, 490, 0, 0);

        jTextField17.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField17.setNextFocusableComponent(jButton2);
        jPanel1.add(jTextField17);
        jTextField17.setBounds(510, 520, 0, 30);

        jLabel22.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel22.setText("City");
        jPanel1.add(jLabel22);
        jLabel22.setBounds(20, 150, 130, 30);

        jComboBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mumbai", "Pune", "Nagpur", "Nashik", "Thane", "Pune", "Aurangabad", "Solapur", "Thane", "Palghar", "Thane", "Amravati", "Thane", "Akola", "Thane", "Dhule", "Jalgaon", "Nanded", "Kolhapur", "Latur", "Raigad", "Thane", "Sangli", "Nashik", "Ahmednagar", "Kolhapur", "Chandrapur", "Parbhani" }));
        jComboBox5.setNextFocusableComponent(jTextField7);
        jPanel1.add(jComboBox5);
        jComboBox5.setBounds(150, 150, 213, 30);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact Person 2"));
        jPanel6.setOpaque(false);
        jPanel6.setLayout(null);

        jLabel25.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel25.setText("Contact No");
        jPanel6.add(jLabel25);
        jLabel25.setBounds(20, 60, 130, 30);

        jLabel26.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel26.setText("Email");
        jPanel6.add(jLabel26);
        jLabel26.setBounds(20, 100, 130, 30);

        jTextField21.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField21.setNextFocusableComponent(jLabel22);
        jPanel6.add(jTextField21);
        jTextField21.setBounds(150, 60, 150, 30);

        jLabel27.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel27.setText("Name");
        jPanel6.add(jLabel27);
        jLabel27.setBounds(20, 20, 130, 30);

        jTextField22.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField22.setNextFocusableComponent(jTextField3);
        jPanel6.add(jTextField22);
        jTextField22.setBounds(150, 100, 150, 30);

        jTextField23.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField23.setNextFocusableComponent(jTextField21);
        jPanel6.add(jTextField23);
        jTextField23.setBounds(150, 20, 150, 30);

        jPanel1.add(jPanel6);
        jPanel6.setBounds(390, 150, 340, 140);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("Authorised For");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(20, 390, 130, 30);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jComboBox2.setNextFocusableComponent(jList1);
        jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox2MouseClicked(evt);
            }
        });
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox2);
        jComboBox2.setBounds(150, 390, 213, 30);

        jTextField5.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextField5.setNextFocusableComponent(jComboBox2);
        jPanel1.add(jTextField5);
        jTextField5.setBounds(520, 450, 210, 30);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("Cancel");
        jButton4.setOpaque(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(790, 480, 120, 30);

        jButton5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton5.setText("Reset");
        jButton5.setOpaque(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5);
        jButton5.setBounds(790, 510, 120, 30);

        jList1.setMaximumSize(new java.awt.Dimension(213, 100));
        jList1.setMinimumSize(new java.awt.Dimension(213, 100));
        jList1.setNextFocusableComponent(jTextField4);
        jList1.setPreferredSize(new java.awt.Dimension(213, 100));
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(150, 430, 210, 100);

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 0, 51));
        jLabel23.setText("*");
        jPanel1.add(jLabel23);
        jLabel23.setBounds(10, 20, 10, 16);

        jComboBox6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jPanel1.add(jComboBox6);
        jComboBox6.setBounds(500, 450, 0, 0);

        jComboBox7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jPanel1.add(jComboBox7);
        jComboBox7.setBounds(500, 490, 0, 0);

        jButton6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton6.setText("Delete");
        jButton6.setOpaque(false);
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jPanel1.add(jButton6);
        jButton6.setBounds(950, 510, 120, 32);

        add(jPanel1);
        jPanel1.setBounds(0, 0, 1170, 550);
    }// </editor-fold>//GEN-END:initComponents

    public int iDFromJSON ( String json ) {
        int id = 0;

        JSONObject jObject = new JSONObject ( json );
        Iterator<?> keys = jObject.keys ();

        JSONObject value = ( JSONObject ) jObject.get ( "data" );

        id = Integer.parseInt ( value.get ( "insert_id" ).toString () );
        //    id = Integer.parseInt( st.getString ("insert_id" ));

        return id;
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        int result = 0 ;
        String success;
        String SUPP_NAME, SUPP_ADD, SUPP_CNT_LL, SUPP_CNT_BRD, WEEK_OFF, GST_NO, VENDOR_CODE, PAYT_TERMS, CITY, SEGMENT, INDUSTRY, CATEGORY, AUTHORIZED_RM, CREATED_ON, EDITED_ON, EDITED_BY;

        SUPP_NAME = jTextField1.getText();
        SUPP_ADD = jTextArea1.getText();
        SUPP_CNT_LL = jTextField7.getText();
        SUPP_CNT_BRD = jTextField8.getText();
        WEEK_OFF = jComboBox1.getSelectedItem().toString();
        GST_NO = jTextField13.getText();
        VENDOR_CODE = jTextField14.getText();
        PAYT_TERMS = jTextField5.getText();

        CITY = jComboBox5.getSelectedItem().toString();
//        SEGMENT = jTextField16.getText();
//        INDUSTRY = jTextField17.getText();
//        CATEGORY = jTextField15.getText();
        
      //  SEGMENT = segments.get( jComboBox6.getSelectedIndex ())[0];
      //  INDUSTRY = industries.get( jComboBox7.getSelectedIndex ())[0];
        //CATEGORY = categories.get( jComboBox3.getSelectedIndex ())[0];
        

        AUTHORIZED_RM = "";
        int i = 0;
        while (i < rawMaterialModel.size()) {
            if (i == rawMaterialModel.size() - 1) {
                AUTHORIZED_RM = AUTHORIZED_RM + rawMaterialModel.get(i);
            } else {
                AUTHORIZED_RM = AUTHORIZED_RM + rawMaterialModel.get(i) + ",";
            }
            i++;
        }

        CREATED_ON = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss" ).format ( Calendar.getInstance ().getTime () );
        EDITED_ON = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss" ).format ( Calendar.getInstance ().getTime () ) ;
        EDITED_BY = String.valueOf ( Proman_User.getEID ()) ;

        if( !SUPP_NAME.equalsIgnoreCase ( "")      ){
           /// result = DB_Operations.insertIntoSupplierMaster(SUPP_NAME, SUPP_ADD, SUPP_CNT_LL, SUPP_CNT_BRD, WEEK_OFF, GST_NO, VENDOR_CODE, PAYT_TERMS, CITY, SEGMENT, INDUSTRY, "null", AUTHORIZED_RM, CREATED_ON, EDITED_ON, EDITED_BY);

             try{
                    
                    String addEmpAPICall = "suppliersadd?SUPP_NAME="+URLEncoder.encode(SUPP_NAME, "UTF-8")+"&SUPP_ADD="+URLEncoder.encode(SUPP_ADD, "UTF-8")+"&SUPP_CNT_LL="+URLEncoder.encode(SUPP_CNT_LL , "UTF-8")+"&SUPP_CNT_BRD="+URLEncoder.encode(SUPP_CNT_BRD, "UTF-8")+"&WEEK_OFF="+URLEncoder.encode( WEEK_OFF, "UTF-8")+"&GST_NO="+URLEncoder.encode( GST_NO, "UTF-8")+"&VENDOR_CODE="+URLEncoder.encode( VENDOR_CODE, "UTF-8")+"&PAYT_TERMS="+URLEncoder.encode( PAYT_TERMS, "UTF-8")+"&CATEGORY="+URLEncoder.encode( "null", "UTF-8")+"&CITY="+URLEncoder.encode( CITY, "UTF-8")+"&AUTHORIZED_RM="+URLEncoder.encode( AUTHORIZED_RM, "UTF-8")+"&CREATED_ON="+URLEncoder.encode( CREATED_ON, "UTF-8")+"&EDITED_ON="+URLEncoder.encode( EDITED_ON, "UTF-8")+"&EDITED_BY="+URLEncoder.encode(EDITED_BY, "UTF-8");
                    String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    JOptionPane.showMessageDialog ( null , result2);
                    
                    result = iDFromJSON ( result2 ) ;
                    
                }catch(UnsupportedEncodingException e){
                    
                }
            
            
            
            String ContactPerson1_NAME = jTextField4.getText();
            String ContactPerson1_CONTACT_NO = jTextField9.getText();
            String ContactPerson1_EMAIL = jTextField10.getText();

            if (!ContactPerson1_NAME.equals("") || !ContactPerson1_CONTACT_NO.equals("") || !ContactPerson1_EMAIL.equals("")) {
 
                if (result != 0) {
                
                         try{
                            String addContactPersonCall = "contactadd?CONT_EMAIL="+URLEncoder.encode(ContactPerson1_EMAIL, "UTF-8")+"&CONT_NO="+URLEncoder.encode(ContactPerson1_CONTACT_NO, "UTF-8")+"&CUST_ID="+URLEncoder.encode("0", "UTF-8")+"&SUPP_ID="+URLEncoder.encode(result+""+"", "UTF-8")+"&CONT_NAME="+URLEncoder.encode(ContactPerson1_NAME,"UTF-8")+"&CREATED_ON="+URLEncoder.encode(CREATED_ON, "UTF-8")+"&EDITED_ON="+URLEncoder.encode(EDITED_ON, "UTF-8")+"&EDITED_BY="+URLEncoder.encode(EDITED_BY, "UTF-8");
                            String result2 =  WebAPITester.prepareWebCall ( addContactPersonCall, StaticValues.getHeader ()   , "");
                            JOptionPane.showMessageDialog ( null , result2);
                        }catch(UnsupportedEncodingException  ex){
                            
                        }
                        
                        ContactPerson1_NAME = jTextField23.getText();
                        ContactPerson1_CONTACT_NO = jTextField21.getText();
                        ContactPerson1_EMAIL = jTextField22.getText();

                        
                        if (!ContactPerson1_NAME.equals("") || !ContactPerson1_CONTACT_NO.equals("") || !ContactPerson1_EMAIL.equals("")) {

                            try{
                                String addContactPersonCall = "contactadd?CONT_EMAIL="+URLEncoder.encode(ContactPerson1_EMAIL, "UTF-8")+"&CONT_NO="+URLEncoder.encode(ContactPerson1_CONTACT_NO, "UTF-8")+"&CUST_ID="+URLEncoder.encode("0", "UTF-8")+"&SUPP_ID="+URLEncoder.encode(result+""+"", "UTF-8")+"&CONT_NAME="+URLEncoder.encode(ContactPerson1_NAME,"UTF-8")+"&CREATED_ON="+URLEncoder.encode(CREATED_ON, "UTF-8")+"&EDITED_ON="+URLEncoder.encode(EDITED_ON, "UTF-8")+"&EDITED_BY="+URLEncoder.encode(EDITED_BY, "UTF-8");
                                String result2 =  WebAPITester.prepareWebCall ( addContactPersonCall, StaticValues.getHeader ()   , "");
                                JOptionPane.showMessageDialog ( null , result2);
                            }catch(UnsupportedEncodingException  ex){

                            }
                        }
                        
                        ContactPerson1_NAME = jTextField3.getText();
                        ContactPerson1_CONTACT_NO = jTextField11.getText();
                        ContactPerson1_EMAIL = jTextField12.getText();

                        if (!ContactPerson1_NAME.equals("") || !ContactPerson1_CONTACT_NO.equals("") || !ContactPerson1_EMAIL.equals("")) {
                        
                            try{
                                String addContactPersonCall = "contactadd?CONT_EMAIL="+URLEncoder.encode(ContactPerson1_EMAIL, "UTF-8")+"&CONT_NO="+URLEncoder.encode(ContactPerson1_CONTACT_NO, "UTF-8")+"&CUST_ID="+URLEncoder.encode("0", "UTF-8")+"&SUPP_ID="+URLEncoder.encode(result+""+"", "UTF-8")+"&CONT_NAME="+URLEncoder.encode(ContactPerson1_NAME,"UTF-8")+"&CREATED_ON="+URLEncoder.encode(CREATED_ON, "UTF-8")+"&EDITED_ON="+URLEncoder.encode(EDITED_ON, "UTF-8")+"&EDITED_BY="+URLEncoder.encode(EDITED_BY, "UTF-8");
                                String result2 =  WebAPITester.prepareWebCall ( addContactPersonCall, StaticValues.getHeader ()   , "");
                                JOptionPane.showMessageDialog ( null , result2);
                            }catch(UnsupportedEncodingException  ex){

                            }
                        }
                    }
            } else {

            }

            JOptionPane.showMessageDialog(null, "Successfully added new supplier" );
        }else{
            JOptionPane.showMessageDialog(null, "Please fill all fields appropriately" );
        }

        resetFields();
        loadContent();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jTextField15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        showDialog();
//        Workbook wb = new HSSFWorkbook();
//
//        ArrayList<String> column_names = new ArrayList<String>();
//        ArrayList<String[]> values = new ArrayList<String[]>();
//        int columnCount = 0;
//        File file = null;
//        try {
//            ResultSet result = DB_Operations.getMasters("supplier");
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
//            StaticValues.writer.writeExcel (SupplierMaster.class.getSimpleName () , SupplierMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
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
//        sheet1 = wb.createSheet("Supplier Master");
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
//            file = new File("Supplier Master File " + strDate2 + ".xls");
//
//            FileOutputStream os = null;
//
//            try {
//                os = new FileOutputStream(file);
//                wb.write(os);
//                wb.close();
//                os.close();
//                //JOptionPane.showMessageDialog(null, "New File generated at " + file.getAbsolutePath());
//            } catch (IOException e) {
//                JOptionPane.showMessageDialog(null, "Error " + e.getMessage());
//                StaticValues.writer.writeExcel (SupplierMaster.class.getSimpleName () , SupplierMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
//            } finally {
//
//            }
//
//        }
//        JOptionPane.showMessageDialog(null, "New File generated at " + file.getAbsolutePath());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField14ActionPerformed

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:

        resetFields();


    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        HomeScreen.home.removeForms();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:

        jButton2.setEnabled(false);

        resetFields();
        
          DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();
        int selectedRowIndex = jTable1.getSelectedRow ();
        selectedEmpRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );

        /* CUSTOMER NAME */             jTextField1.setText( model.getValueAt ( selectedRowIndex , 1 ).toString () );
        /* CUSTOMER address */          jTextArea1.setText( model.getValueAt ( selectedRowIndex , 2 ).toString () );
        /* CUSTOMER contact ll */        jTextField7.setText( model.getValueAt ( selectedRowIndex , 3 ).toString () );
        /* CUSTOMER contact brd */     jTextField8.setText( model.getValueAt ( selectedRowIndex , 4 ).toString () );
        /* CUSTOMER weekly off */      jComboBox1.setSelectedIndex(0);
        /* CUSTOMER GST */               jTextField13.setText(  model.getValueAt ( selectedRowIndex , 6 ).toString ()  );
        /* CUSTOMER VENDOR CODE */jTextField14.setText(  model.getValueAt ( selectedRowIndex , 7 ).toString ()  );
        /* CUSTOMER credit period*/   jTextField5.setText(  model.getValueAt ( selectedRowIndex , 8 ).toString ()  );
        
         String addEmpAPICall = "contacts?supplier_id=" + selectedEmpRecordId;
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
                        jTextField4.setText( emp.get("CONT_NAME" ).toString());
                        jTextField9.setText(emp.get( "CONT_NO" ).toString());
                        jTextField10.setText(emp.get( "CONT_EMAIL" ).toString());

                    }catch( Exception e){
                        System.out.println ( "Contact Person 1 not found" );
                    }
                    
                     try{
                        emp = records.getJSONObject ( 1 );
                        jTextField23.setText( emp.get("CONT_NAME" ).toString());
                        jTextField21.setText(emp.get( "CONT_NO" ).toString());
                        jTextField22.setText(emp.get( "CONT_EMAIL" ).toString());
                        
                    }catch( Exception e){
                        System.out.println ( "Contact Person 2 not found" );
                    }
                     
                      try{
                        emp = records.getJSONObject ( 2 );
                        jTextField3.setText(emp.get("CONT_NAME" ).toString());
                        jTextField11.setText(emp.get( "CONT_NO" ).toString());
                        jTextField12.setText(emp.get( "CONT_EMAIL" ).toString());

                    }catch( Exception e){
                        System.out.println ( "Contact Person 3 not found" );
                    }
                    
                }
        
        jButton1.setEnabled(true);


    }//GEN-LAST:event_jTable1MouseClicked

    DefaultListModel<String> rawMaterialModel = new DefaultListModel();
    
    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
      if(!rawMaterialModel.contains(jComboBox2.getSelectedItem().toString())) {    
                 rawMaterialModel.addElement(jComboBox2.getSelectedItem().toString());
            jList1.setModel(rawMaterialModel);
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String SUPP_NAME, SUPP_ADD, SUPP_CNT_LL, SUPP_CNT_BRD, WEEK_OFF, GST_NO, VENDOR_CODE, PAYT_TERMS, CITY, SEGMENT, INDUSTRY, CATEGORY, AUTHORIZED_RM, EDITED_ON, EDITED_BY;

        SUPP_NAME = jTextField1.getText();
        SUPP_ADD = jTextArea1.getText();
        SUPP_CNT_LL = jTextField7.getText();
        SUPP_CNT_BRD = jTextField8.getText();
        WEEK_OFF = jComboBox1.getSelectedItem().toString();
        GST_NO = jTextField13.getText();
        VENDOR_CODE = jTextField14.getText();
        PAYT_TERMS = jTextField5.getText();
        CITY = jComboBox5.getSelectedItem().toString();
//        SEGMENT = jTextField16.getText();
//        INDUSTRY = jTextField17.getText();
//        CATEGORY = jTextField15.getText();
        //SEGMENT = segments.get( jComboBox6.getSelectedIndex ())[0];
        //INDUSTRY = industries.get( jComboBox7.getSelectedIndex ())[0];
        //CATEGORY = categories.get( jComboBox3.getSelectedIndex ())[0];
        

        AUTHORIZED_RM = "";
        int i = 0;
        while (i < rawMaterialModel.size()) {
            if (i == rawMaterialModel.size() - 1) {
                AUTHORIZED_RM = AUTHORIZED_RM + rawMaterialModel.get(i);
            } else {
                AUTHORIZED_RM = AUTHORIZED_RM + rawMaterialModel.get(i) + ",";
            }
            i++;
        }

        EDITED_ON = "EDITED_BY";
        EDITED_BY = "EDITED_ON";

        int ID = selectedEmpRecordId;

        //String result = DB_Operations.updateSuppliersMaster(SUPP_NAME, SUPP_ADD, SUPP_CNT_LL, SUPP_CNT_BRD, WEEK_OFF, GST_NO, VENDOR_CODE, PAYT_TERMS, CITY, SEGMENT, INDUSTRY, CATEGORY, AUTHORIZED_RM, EDITED_ON, EDITED_BY, ID);
        try{

                String addEmpAPICall = "supplierssupdate?SUPP_ID="+selectedEmpRecordId+"&SUPP_NAME="+URLEncoder.encode(SUPP_NAME, "UTF-8")+"&SUPP_ADD="+URLEncoder.encode(SUPP_ADD, "UTF-8")+"&SUPP_CNT_LL="+URLEncoder.encode(SUPP_CNT_LL , "UTF-8")+"&SUPP_CNT_BRD="+URLEncoder.encode(SUPP_CNT_BRD, "UTF-8")+"&WEEK_OFF="+URLEncoder.encode( WEEK_OFF, "UTF-8")+"&GST_NO="+URLEncoder.encode( GST_NO, "UTF-8")+"&VENDOR_CODE="+URLEncoder.encode( VENDOR_CODE, "UTF-8")+"&PAYT_TERMS="+URLEncoder.encode( PAYT_TERMS, "UTF-8")+"&CITY="+URLEncoder.encode( CITY, "UTF-8")+"&CREATED_ON=&EDITED_ON=&EDITED_BY=";
                String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                JOptionPane.showMessageDialog ( null , result2);

                loadContent();
                
        }catch(UnsupportedEncodingException e){

        }
        
        
        String ContactPerson1_NAME = jTextField4.getText();
        String ContactPerson1_CONTACT_NO = jTextField9.getText();
        String ContactPerson1_EMAIL = jTextField10.getText();
        DB_Operations.updateContactMaster(ContactPerson1_EMAIL, ContactPerson1_CONTACT_NO, ContactPerson1_NAME, 0, selectedEmpRecordId, contactIDs[0]);
     
                                                                                                                                                        

        ContactPerson1_NAME = jTextField23.getText();
        ContactPerson1_CONTACT_NO = jTextField21.getText();
        ContactPerson1_EMAIL = jTextField22.getText();
        DB_Operations.updateContactMaster(ContactPerson1_EMAIL, ContactPerson1_CONTACT_NO, ContactPerson1_NAME, 0, selectedEmpRecordId, contactIDs[1]);

        ContactPerson1_NAME = jTextField3.getText();
        ContactPerson1_CONTACT_NO = jTextField11.getText();
        ContactPerson1_EMAIL = jTextField12.getText();
        DB_Operations.updateContactMaster(ContactPerson1_EMAIL, ContactPerson1_CONTACT_NO, ContactPerson1_NAME, 0, selectedEmpRecordId, contactIDs[2]);

        

        resetFields();
        

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        int selection = jList1.getSelectedIndex();
        rawMaterialModel.removeElementAt(selection);
        jList1.setModel(rawMaterialModel);
    }//GEN-LAST:event_jList1MouseClicked
//changes start
    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
    if(jComboBox2.getItemCount()==0){
        
    JOptionPane.showMessageDialog(null, "<html><size='06'>Add Values First in RawMaterial Master");

    }     
    
//changes end
    }//GEN-LAST:event_jComboBox2MouseClicked

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        // TODO add your handling code here:
        
        int selection = JOptionPane.showConfirmDialog ( null , "Deleting a record is not recommendedThe 'Delete' action may cause loss of any dependencies and may affect the transactional Data and Reports and make data inconsistent\nDeleting a plant will automatically delete all shifts for that plant\nPress 'No' to revert, Press 'Yes' to continue deleting", "Are you sure about deleting this record ?", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE);
        if ( selection == JOptionPane.YES_OPTION ) { 

            
            deleteMaster(  "suppliersdelete?SUPP_ID="+selectedEmpRecordId    ) ;    
            
            String response  = WebAPITester.prepareWebCall ( "contacts?supplier_id="+selectedEmpRecordId,  StaticValues.getHeader () , "");
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
            
            String addEmpAPICall = "suppliers";
                String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

                if ( result2!= null && ! result2.contains( "not found"  )) {
                    jTable1.setModel ( buildTableModelfromJSON (result2 ) );
                    jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                    jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                }
                resetFields ();
        }
        
    }//GEN-LAST:event_jButton6MouseClicked

    
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
    
    
    public void resetFields() {

        jTextField1.setText("");
        jTextArea1.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jComboBox1.getSelectedItem().toString();
        jTextField13.setText("");
        jTextField14.setText("");
        jTextField5.setText("");

        jComboBox5.getSelectedItem().toString();
        jTextField16.setText("");
        jTextField17.setText("");
        jTextField15.setText("");

        rawMaterialModel.removeAllElements();
        jList1.setModel(rawMaterialModel);

       
            jTextField4.setText( "" );
            jTextField9.setText( "" );
            jTextField10.setText( "" );
            jTextField23.setText( "" );
            jTextField21.setText( "" );
            jTextField22.setText( "" );
            jTextField3.setText( "" );
            jTextField11.setText( "" );
            jTextField12.setText( "" );
              
        jButton2.setEnabled(true);
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
        progress.updateTitle ( "All Supplier data is fetching please wait...");
        progress.updateMessage ("Getting all Supplier data");
                //String MCH_ID = "",MACHINE_NO="", MAKE = "", BED_SIZE = "", HEATER_QTY = "", YR_OF_COMM = "", CR_MAINT_RTG = "", CR_MCH_OWN = "", MCH_BRKD_RCD = "", PM_ELE = "", PM_MEC = "",PM_HYD="",PM_PNM="",PM_CLIT="",PRSS_GAUGE="",TIMER="",TEMP_CTRL="",OP_RATE_HR="",AVL_HRS="",DAVLHRS="";
                String SUPP_NAME="", SUPP_ADD="", SUPP_CNT_LL="", SUPP_CNT_BRD="", WEEK_OFF="", GST_NO="", VENDOR_CODE="", PAYT_TERMS="", CITY="", SEGMENT="", INDUSTRY="", CATEGORY="", AUTHORIZED_RM="";

                //int die_id;
                int l = 2;

                Map<String, Object[]> data11;
                data11 = new TreeMap<String, Object[]>();

                String[] columns = { "Supplier Name", "Supplier Address", "Landline NO", "Contact Board", "Weekly off", "GST No","Vendor Code","Payment Terms","City","Authorized RM"};
                
                data11.put("1", new Object[]{ "Supplier Name", "Supplier Address", "Landline NO", "Contact Board", "Weekly off", "GST No","Vendor Code","Payment Terms","City","Authorized RM"});

                String result2, addEmpAPICall;
                
                addEmpAPICall = "suppliers";
                
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
       
                        SUPP_NAME=emp.get("SUPP_NAME").toString();
                        SUPP_ADD=emp.get("SUPP_ADD").toString();
                        SUPP_CNT_LL=emp.get("SUPP_CNT_LL").toString();
                        SUPP_CNT_BRD=emp.get("SUPP_CNT_BRD").toString();
                        WEEK_OFF=emp.get("WEEK_OFF").toString();
                        GST_NO=emp.get("GST_NO").toString();
                        VENDOR_CODE=emp.get("VENDOR_CODE").toString();
                        PAYT_TERMS=emp.get("PAYT_TERMS").toString();
                        CITY=emp.get("CITY").toString();
                        //SEGMENT=emp.get("SEGMENT").toString();
                        //INDUSTRY=emp.get("INDUSTRY").toString();
                        //CATEGORY=emp.get("CATEGORY").toString();
                        AUTHORIZED_RM=emp.get("AUTHORIZED_RM").toString();

                         

                        data11.put("" + l, new Object[]{SUPP_NAME, SUPP_ADD, SUPP_CNT_LL, SUPP_CNT_BRD, WEEK_OFF, GST_NO, VENDOR_CODE, PAYT_TERMS, CITY, AUTHORIZED_RM});
                        
                        l = l + 1;
                    }
                }
                        
               hideDialog ();
               
               //Blank workbook
                Workbook workbook = new HSSFWorkbook();

                //Create a blank sheet
                HSSFSheet sheet = (HSSFSheet) workbook.createSheet("Supplier Master");

                
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
                    file = new File(jfc.getSelectedFile() + "\\" + "Supplier Master Data " + strDate2 + ".xls");
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private static javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
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
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    int selectedEmpRecordId = 0;
    int contactIDs[] = new int[]{0, 0, 0};
}
