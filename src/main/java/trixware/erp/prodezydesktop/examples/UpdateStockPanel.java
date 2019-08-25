/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import datechooser.view.WeekDaysStyle;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Derived.AutoCompletion;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.RawMaterialDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.MyCustomFilter;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class UpdateStockPanel extends javax.swing.JPanel {

    String selectedMaster = "";
    ArrayList<String[]> finished_goods = null;
    ArrayList<String[]> raw_materials = null;
    ///   ArrayList<String[]> masters = null;

    int OPENING = 0;
    int RECEIVED = 0, USED = 0;
    int CLOSING = 0;

    int OldOPENING = 0;
    int OldCLOSING = 0;

    boolean recordExist = false;

    String selectedTransaction = "";
    int selectedItemId = 0;

    private String stkInDate = "";
    private String stkInBatchNo = "" ;
    private String stkInId = "" ;
    private String prate = "0.0" ;
    
    
    String addEmpAPICall = "";
        String result2 = "" ;
        ArrayList<RawMaterialDR> RMList = null;
        ArrayList<ProductDR> products = null;
    public int RMD_UID = 0; 
    public Thread loadScreenTask ;
    
    /**
     * Creates new form UpdateStockPanel
     */
    public UpdateStockPanel () {

         initComponents ();

        AutoCompletion.enable ( jComboBox1 );
        AutoCompletion.enable ( jComboBox3 );
        AutoCompletion.enable ( jComboBox5 );

        loadScreenTask = new Thread(){
            public void run(){
                     loadContent();
                     getLatestRMStock ();
                     getRMStockLedger ();
            }
        };
        loadScreenTask.start ();
        
        dateChooserCombo1.setFormat ( DateFormat.MEDIUM );
        dateChooserCombo1.setWeekStyle ( WeekDaysStyle.NORMAL );
        dateChooserCombo1.setOpaque ( false );
        dateChooserCombo1.setBackground ( Color.WHITE );
        dateChooserCombo1.revalidate ();
        dateChooserCombo1.repaint ();          
    }
    
    
    
    public void getLatestRMStock(){
        
        String result2 = WebAPITester.prepareWebCall ( "rawmaterials" , StaticValues.getHeader () , "" );


       if ( result2 != null && ! result2.contains(  "not found") ) {
           jTable1.setModel ( buildTableModelfromJSON_RMAndStock (result2 ) );
           jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
           jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
       }
    }
    
    public  DefaultTableModel buildTableModelfromJSON_RMAndStock ( String employeeJSONList ) {

        Vector<String> columnNames = new Vector<String> ();
        Vector<String> columnNames2 = new Vector<String> ();
        int columnCount = 0;
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        Vector<Object> tableValues = new Vector<Object> ();

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
        columnNames.add ( "RM_ID" );                columnNames2.add ( "RM_ID" );
        columnNames.add ( "RM_TYPE" );              columnNames2.add ( "RM Type" );
        columnNames.add ( "RM_CLASS" );             columnNames2.add ( "RM Code" );
        //columnNames.add ( "RM_CTG" );
        columnNames.add ( "REORDER_LEVEL" );    columnNames2.add ( "Reorder Level" );
        columnNames.add ( "Available Stock" ); columnNames2.add ( "Available Stock (Wt)" );     
        columnNames.add ( "Available Stock" ); columnNames2.add ( "Available Stock (Count)" );     
        columnNames.add ( "Batch" );      columnNames2.add ( "Batch" );
        
        
        //columnNames.add("RM_CLASS");
              
        
        
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );

                tableValues = new Vector<Object> ();

                String[] values = new String[ columnNames.size () ];
                for ( int columnIndex = 0 ; columnIndex <columnNames.size ()-3 ; columnIndex ++ ) {

                    //values[columnIndex] = emp.get ( columnNames.get ( columnIndex ) ).toString () ;
                    tableValues.add(   emp.get ( columnNames.get ( columnIndex ) ).toString ()       ) ;

                }

                addEmpAPICall = "latestrwamstock?rmid="+emp.get ( "RM_ID" ).toString ()  ;
                String stockString = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

                JSONObject jObject2 = new JSONObject ( stockString );
                Iterator<?> keys2 = jObject2.keys ();

                HashMap<String , Object> map2 = new HashMap<String , Object> ();
                 while ( keys2.hasNext () ) {
                     String key = ( String ) keys2.next ();
                     Object value = jObject2.get ( key );
                     map2.put ( key , value );
                 }
                 JSONObject st2 = ( JSONObject ) map2.get ( "data" );
                 JSONArray records2 = st2.getJSONArray ( "records" );

                JSONObject emp2 = null;
                  for ( int j = 0 ; j < records2.length () ; j ++ ) {
                        emp2 = records2.getJSONObject ( j );
                        
                        if( ! emp2.get ( "CLOSING" ).toString ().equals ( "null") )
                            tableValues.add(  emp2.get ( "CLOSING" ).toString () );
                        else
                            tableValues.add(  "0"  );
                        
                        
                        if( ! emp2.get ( "CLOSING_CNT" ).toString ().equals ( "null") )
                            tableValues.add(  emp2.get ( "CLOSING_CNT" ).toString () );
                        else
                            tableValues.add(  "0"  );
                        
                        
                        tableValues.add(  emp2.get ( "Batch" ).toString () );
                        
                 }

                    data.add ( tableValues );
             }
            
             return new DefaultTableModel ( data , columnNames2 );
        }
    
    
    public void getRMStockLedger(){
        
        String result2 = WebAPITester.prepareWebCall ( "rmstocks" , StaticValues.getHeader () , "" );


       if ( result2 != null && ! result2.contains(  "not found") ) {
           jTable2.setModel ( buildTableModelfromJSON_RMStockLedger (result2 ) );
       }
    }
    
    public  DefaultTableModel buildTableModelfromJSON_RMStockLedger ( String employeeJSONList ) {

        Vector<String> columnNames = new Vector<String> ();
        Vector<String> columnNames2 = new Vector<String> ();
        int columnCount = 0;
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        Vector<Object> tableValues = new Vector<Object> ();

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
        columnNames.add ( "RM_CODE" );                columnNames2.add ( "RM Code" );
        columnNames.add ( "OPENING" );              columnNames2.add ( "Opening (Wt)" );
        columnNames.add ( "RECEIVED" );             columnNames2.add ( "Inward (Wt)" );
        columnNames.add ( "USED" );    columnNames2.add ( "Issued (Wt)" );
        columnNames.add ( "CLOSING" );      columnNames2.add ( "Available (Wt)" );
        columnNames.add ( "OPENING_CNT" );              columnNames2.add ( "Opening (Nos)" );
        columnNames.add ( "RECEIVED_CNT" );             columnNames2.add ( "Inward (Nos)" );
        columnNames.add ( "USED_CNT" );    columnNames2.add ( "Issued (Nos)" );
        columnNames.add ( "CLOSING_CNT" );      columnNames2.add ( "Available (Nos)" );
        columnNames.add ( "CREATED_ON" );      columnNames2.add ( "Trans. Date" );
        //columnNames.add ( "rm_inward_date" );      columnNames2.add ( "In Date" );
        //columnNames.add ( "inward_batch_no" );      columnNames2.add ( "Batch" );
        //columnNames.add ( "inward_uid" );      columnNames2.add ( "In Code" );
       // columnNames.add ( "purchase_rate" );      columnNames2.add ( "Rate" );
       // columnNames.add ( "created_at" );      columnNames2.add ( "Entry Date" );
        
        //columnNames.add("RM_CLASS");
              
        
        
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );

                tableValues = new Vector<Object> ();
                for ( int columnIndex = 0 ; columnIndex <columnNames.size () ; columnIndex ++ ) {
                     
                    if(  emp.get ( columnNames.get ( columnIndex ) ).toString ().equals ( "null") )
                        tableValues.add(   "--"      ) ;
                     else
                        tableValues.add(   emp.get ( columnNames.get ( columnIndex ) ).toString ()       ) ; 
                }
                    data.add ( tableValues );
             }
            
             return new DefaultTableModel ( data , columnNames2 );
        }
    
            
    
    
    public void loadContent () {

            ResultSet result = DB_Operations.getMasters ( "raw_material" );

                if ( jComboBox1.getItemCount () == 0 ) {
                    //result = DB_Operations.getMasters ( "raw_material_type" );
                   
                    addEmpAPICall = "rawmaterials";
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
           
                    if( !  result2.contains("not found")   ){
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
                            RMList = new ArrayList<RawMaterialDR> ();
                            raw_materials = new ArrayList<String[]> ();
                            for ( int i = 0 ; i < records.length () ; i ++ ) {

                                emp = records.getJSONObject ( i);
                                
                                String dim = "" ;
                                if(   !  emp.get ( "length" ).toString ().equals ( "null") &&  ! emp.get ( "width" ).toString ().equals ( "null")     &&      ! emp.get ( "thickness" ).toString ().equals ( "null") ){
                                dim = emp.get ( "length" ).toString ()+"x"+
                                                    emp.get ( "width" ).toString ()+"x"+
                                                    emp.get ( "thickness" ).toString () ;
                                }
                                
                                jComboBox1.addItem (  emp.get ( "RM_CLASS" ).toString ()+"  "+dim );
                                //RMList.add( new RawMaterialDR ( Integer.parseInt(emp.get ( "RM_ID" ).toString ()), emp.get ( "RM_NAME" ).toString ()  )); 
                                raw_materials.add ( new String[] { emp.get ( "RM_ID" ).toString () ,  emp.get ( "RM_CODE" ).toString () , emp.get ( "RMM_UOM_ID" ).toString ()  } );
                            }

                            jLabel2.setText(  StaticValues.getUOMName ( Integer.parseInt( raw_materials.get(jComboBox1.getSelectedIndex() )[2] ) ) );
                            jLabel11.setText(  StaticValues.getUOMName ( Integer.parseInt( raw_materials.get(jComboBox1.getSelectedIndex() )[2] ) ) );
                             jComboBox1.addActionListener ( selectUnitAction);
                    }
                }
                
                
                RMD_UID = loadDocumentsForRM ( );
                
                   
                loadContentAtBeginning ();
                jLabel16.setText("0");
                jLabel17.setText("0");
                jLabel18.setText("0");
                
            //   jComboBox1.addActionListener ( selectUnitAction );

    }

    
    public int loadDocumentsForRM(  ){
        
        String addEmpAPICall = "rdm";
        String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
        
        if (  ! result2.contains ( "not found" ) ) {
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
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );
                if ( emp.getString ( "RDM_NAME" ).equals( "RM - Testing Certificate" ) ) {
                    return Integer.parseInt( emp.get ( "RDM_UID" ).toString () );
                }
            }
        }
        
        return 0;
    }
    
    
    public void loadContentAfterTransaction () {

            selectedItemId = Integer.parseInt ( raw_materials.get ( jComboBox1.getSelectedIndex () )[ 0 ] );
            String addEmpAPICall = "latestrwamstock?rmid=" +raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] ;
            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            
            if(   ! result2.contains ( "\"101\":\"Report record\\/s not found.\"") &&  ! result2.contains ( "\"OPENING\":null,\"RECEIVED\":null,\"USED\":null,\"CLOSING\":null")          ){
                
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


                if (   records.length () >0 ) {
                    try{
                        OPENING = Integer.parseInt( records.getJSONObject ( 0 ).get( "OPENING" ).toString());
                        OldCLOSING = Integer.parseInt( records.getJSONObject ( 0 ).get( "CLOSING" ).toString());
                        RECEIVED = Integer.parseInt( records.getJSONObject ( 0 ).get( "RECEIVED" ).toString());
                        USED =  Integer.parseInt( records.getJSONObject ( 0 ).get( "USED" ).toString());
                    }catch(   NumberFormatException | NullPointerException e   ){
                        OPENING = 0 ;
                        OldCLOSING = 0 ;
                    }

                  //  RECEIVED = 0 ;
                  //  USED = 0 ;


                    //15 16 17 18
                    jLabel15.setText(OPENING+"");
                    jLabel16.setText(""+RECEIVED);
                    jLabel17.setText("");
                    jLabel18.setText(""+OldCLOSING);
                }else{
                    jLabel15.setText("0");
                    jLabel16.setText("0");
                    jLabel17.setText("0");
                    jLabel18.setText("0");
                }
            }
    } 
    
     public void loadContentAtBeginning () {

            selectedItemId = Integer.parseInt ( raw_materials.get ( jComboBox1.getSelectedIndex () )[ 0 ] );
            String addEmpAPICall = "latestrwamstock?rmid=" +raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] ;
            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            
            if(   ! result2.contains ( "\"101\":\"Report record\\/s not found.\"") &&  ! result2.contains ( "\"OPENING\":null,\"RECEIVED\":null,\"USED\":null,\"CLOSING\":null")          ){
                
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


                if (   records.length () >0 ) {
                    try{
                        OPENING = Integer.parseInt( records.getJSONObject ( 0 ).get( "CLOSING" ).toString());
                        OldCLOSING = Integer.parseInt( records.getJSONObject ( 0 ).get( "CLOSING" ).toString());
                    }catch(   NumberFormatException | NullPointerException e   ){
                        OPENING = 0 ;
                        OldCLOSING = 0 ;
                    }

                    RECEIVED = 0 ;
                    USED = 0 ;


                    //15 16 17 18
                    jLabel15.setText(OldCLOSING+"");
                    jLabel16.setText("0");
                    jLabel17.setText("0");
                    jLabel18.setText("0");
                }else{
                    jLabel15.setText("0");
                    jLabel16.setText("0");
                    jLabel17.setText("0");
                    jLabel18.setText("0");
                }
            }else{
                jLabel15.setText("0");
                    jLabel16.setText("0");
                    jLabel17.setText("0");
                    jLabel18.setText("0");
            }
    } 

    ActionListener selectUnitAction = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {

                jLabel2.setText(  StaticValues.getUOMName ( Integer.parseInt( raw_materials.get(jComboBox1.getSelectedIndex() )[2] ) ) );
                jLabel11.setText(  StaticValues.getUOMName ( Integer.parseInt( raw_materials.get(jComboBox1.getSelectedIndex() )[2] ) ) );
                selectedItemId = Integer.parseInt ( raw_materials.get ( jComboBox1.getSelectedIndex () )[ 0 ] );
          
                loadContentAtBeginning ();
              

        }
    };

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.c
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(510, 530));
        setPreferredSize(new java.awt.Dimension(1126, 545));
        setLayout(null);

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 700));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Select Master");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 15, 70, 30);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Submit");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(170, 530, 80, 30);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Cancel");
        jButton2.setOpaque(false);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(330, 530, 80, 30);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Raw Material", "Finished Goods" }));
        jComboBox3.setEnabled(false);
        jComboBox3.setNextFocusableComponent(jComboBox1);
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBox3);
        jComboBox3.setBounds(180, 15, 230, 30);
        jPanel1.add(jLabel10);
        jLabel10.setBounds(270, 80, 112, 0);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Current Stocks"));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(null);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("Closing Stock");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(10, 110, 72, 16);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("Outward Stock");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(11, 82, 78, 16);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Opening Stock");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(11, 24, 78, 16);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Inward Stock");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(10, 50, 68, 16);

        jLabel17.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel17.setText("0");
        jPanel2.add(jLabel17);
        jLabel17.setBounds(150, 82, 110, 16);

        jLabel18.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel18.setText("0");
        jPanel2.add(jLabel18);
        jLabel18.setBounds(150, 110, 110, 16);

        jLabel16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel16.setText("0");
        jPanel2.add(jLabel16);
        jLabel16.setBounds(150, 50, 110, 16);

        jLabel15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel15.setText("0");
        jPanel2.add(jLabel15);
        jLabel15.setBounds(150, 24, 110, 16);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 380, 398, 150);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("Transaction Type");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(20, 85, 98, 30);

        jComboBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Inward" }));
        jComboBox5.setNextFocusableComponent(jTextField1);
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox5);
        jComboBox5.setBounds(180, 85, 230, 30);

        jLabel14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel14.setText("Quantity (Weight)");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(20, 120, 110, 30);

        jTextField1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1);
        jTextField1.setBounds(180, 120, 120, 30);

        jLabel19.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel19.setText("Select Item");
        jPanel1.add(jLabel19);
        jLabel19.setBounds(20, 50, 80, 30);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox1.setNextFocusableComponent(jComboBox5);
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(180, 50, 230, 30);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(330, 280, 70, 30);

        jLabel20.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel20.setText("Date");
        jPanel1.add(jLabel20);
        jLabel20.setBounds(20, 190, 130, 30);
        jPanel1.add(jTextField8);
        jTextField8.setBounds(180, 220, 230, 30);

        jLabel21.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel21.setText("RM Batch No / Code");
        jPanel1.add(jLabel21);
        jLabel21.setBounds(20, 220, 130, 30);

        jLabel22.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel22.setText("Inward Code");
        jPanel1.add(jLabel22);
        jLabel22.setBounds(20, 250, 130, 30);
        jPanel1.add(jTextField14);
        jTextField14.setBounds(180, 250, 230, 30);
        jPanel1.add(dateChooserCombo1);
        dateChooserCombo1.setBounds(180, 190, 230, 30);

        jTable1.setAutoCreateRowSorter(true);
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
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(440, 30, 670, 220);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Upload TC");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(20, 310, 130, 30);
        jPanel1.add(jTextField2);
        jTextField2.setBounds(180, 280, 120, 30);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("Latest Stock");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(440, 10, 90, 20);

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(jTable2);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(440, 300, 670, 260);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("Stock Ledger");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(440, 260, 110, 30);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jPanel1.add(jLabel11);
        jLabel11.setBounds(340, 120, 70, 30);

        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 0, 18)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("/");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(300, 280, 30, 30);

        jLabel23.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel23.setText("Purchase Rate");
        jPanel1.add(jLabel23);
        jLabel23.setBounds(20, 280, 130, 30);

        jLabel24.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel24.setText("<< selected file >>");
        jPanel1.add(jLabel24);
        jLabel24.setBounds(180, 310, 230, 40);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Browse");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(180, 350, 90, 32);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("Delete");
        jButton4.setOpaque(false);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(250, 530, 77, 32);

        jLabel25.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel25.setText("Quantity in Nos");
        jPanel1.add(jLabel25);
        jLabel25.setBounds(20, 156, 130, 30);

        jTextField3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jPanel1.add(jTextField3);
        jTextField3.setBounds(180, 154, 120, 30);

        add(jPanel1);
        jPanel1.setBounds(0, 0, 1130, 570);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        // TODO add your handling code here:


    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        HomeScreen.home.removeForms ();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
        String strDate2 = sdf2.format ( c2.getTime () );

            int OPENING = 0;
            int RECEIVED = 0, USED = 0;
            int CLOSING = 0;

            int OldOPENING = 0;
            int OldCLOSING = 0;
            boolean recordExist = false;
            
            
            int OPENING_CNT=0  ,  RECEIVED_CNT=0 ,  USED_CNT=0 ,  CLOSING_CNT=0 , OLD_OPENING_CNT =0  ,  OLD_CLOSING_CNT=0 ;
            
            double convValue = 0.0 ;
            
            stkInBatchNo = jTextField8.getText();
            stkInId = jTextField14.getText () ;
            stkInDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( dateChooserCombo1.getSelectedDate ().getTime () ) ;
            prate = jTextField2.getText() ;
            
            int qtyInNos = Integer.parseInt(  jTextField3.getText() );
            double singleRMWt = 0.0;

            String addEmpAPICall = "latestrwamstock?rmid=" +raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] ;
            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

             

                 if( ! result2.contains ( "\"101\":\"Report record\\/s not found.\"") &&  ! result2.contains ( "\"OPENING\":null,\"RECEIVED\":null,\"USED\":null,\"CLOSING\":null") ){

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


                            if (   records.length () >0 ) {
                                recordExist = true;
                                OPENING = Integer.parseInt( records.getJSONObject ( 0 ).get( "OPENING" ).toString());
                                RECEIVED = Integer.parseInt( records.getJSONObject ( 0 ).get( "RECEIVED" ).toString()) ;
                                USED = Integer.parseInt( records.getJSONObject ( 0 ).get( "USED" ).toString());
                                OldCLOSING = Integer.parseInt( records.getJSONObject ( 0 ).get( "CLOSING" ).toString());
                                OldOPENING = Integer.parseInt( records.getJSONObject ( 0 ).get( "OPENING" ).toString());
                                
                                OPENING_CNT = Integer.parseInt( records.getJSONObject ( 0 ).get( "OPENING_CNT" ).toString());
                                RECEIVED_CNT = Integer.parseInt( records.getJSONObject ( 0 ).get( "RECEIVED_CNT" ).toString()) ;
                                USED_CNT = Integer.parseInt( records.getJSONObject ( 0 ).get( "USED_CNT" ).toString());
                                OLD_CLOSING_CNT = Integer.parseInt( records.getJSONObject ( 0 ).get( "CLOSING_CNT" ).toString());
                                OLD_OPENING_CNT = Integer.parseInt( records.getJSONObject ( 0 ).get( "OPENING_CNT" ).toString());
                                
                                

                            } else {
                                System.out.println ( "No result to show" );
                                recordExist = false;
                                OPENING = 0;
                                RECEIVED = 0;
                                USED = 0;
                                CLOSING = 0;
                                
                                OPENING_CNT = 0;
                                RECEIVED_CNT = 0 ;
                                USED_CNT = 0;
                                CLOSING_CNT = 0 ;
                                
                            }

                            OPENING = OldCLOSING;
                            OPENING_CNT = OLD_CLOSING_CNT;

                            if(   jComboBox5.getSelectedItem ().toString().equals ( "Inward" )  ){

                                RECEIVED = Integer.parseInt ( jTextField1.getText () ) ;
                                
                                if( qtyInNos >0 ){
                                    singleRMWt = Double.parseDouble(  new DecimalFormat("#.##").format( RECEIVED *1.0 / qtyInNos *1.0 ) ) ;
                                }
                                
                                USED = 0;
                                CLOSING = OPENING + RECEIVED;
                                RECEIVED = Integer.parseInt ( jTextField1.getText () ) ;
                                USED = 0;
                                CLOSING = OPENING + RECEIVED;
 
                                
                                RECEIVED_CNT = Integer.parseInt ( jTextField3.getText () ) ;
                                USED_CNT = 0;
                                CLOSING_CNT  = OPENING_CNT + RECEIVED_CNT;
                                
                                //RECEIVED_CNT = Integer.parseInt ( jTextField3.getText () ) ;
                                //USED_CNT = 0;
                                //CLOSING_CNT = OPENING_CNT + RECEIVED_CNT;
                                
                                
                            }else{

                                RECEIVED = 0 ;
                                USED = Integer.parseInt ( jTextField1.getText () ) ;
                                CLOSING = OPENING - USED;
                                
                                RECEIVED_CNT = 0 ;
                                USED_CNT = Integer.parseInt ( jTextField3.getText () ) ;
                                CLOSING_CNT = OPENING_CNT - USED_CNT;
                            }
                            
                            
                            int batchId  ;
                            
                            try{
                                addEmpAPICall = "batchesadd?BatchName="+URLEncoder.encode(stkInBatchNo, "UTF-8")+"&BatchQty="+URLEncoder.encode(RECEIVED+"", "UTF-8")+"&BatchQty_CNT="+URLEncoder.encode(RECEIVED_CNT+"", "UTF-8")
                                        +"&BatchUOM_ID="+URLEncoder.encode(  raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] +"", "UTF-8")
                                        +"&BatchCreateDate="+URLEncoder.encode ( stkInDate , "UTF-8" )+"&BatchExpireDate=&ref_rm_id="+raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] 
                                        + "&inward_uid=" + stkInId+ "&rm_inward_date=" + URLEncoder.encode ( stkInDate , "UTF-8" )+ "&purchase_rate=" + prate     + "&conversion_value="+singleRMWt     ;
                                result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                                //JOptionPane.showMessageDialog ( null , result2);
                                
                                if( result2.contains("success") ){
                                    batchId = iDFromJSON( result2  );
     
                                            String updateStock = "rmstockadd?RMI_ID=" +raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] + "&RM_ITEM_ID=" +raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] 
                                                + "&OPENING=" + OPENING +"&RECEIVED=" + RECEIVED+ "&USED=" + USED+ "&CLOSING=" + CLOSING 
                                                + "&CREATED_ON=" + URLEncoder.encode ( new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format ( Calendar.getInstance ().getTime () ) , "UTF-8" )
                                                + "&EDITED_ON=" + URLEncoder.encode (new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format ( Calendar.getInstance ().getTime () ) , "UTF-8" )+ "&EDITED_BY=" + Proman_User.getEID ()   
                                                +"&ref_batch_id="+batchId+  
                                                     "&OPENING_CNT=" + OPENING_CNT      + "&RECEIVED_CNT=" + RECEIVED_CNT+ "&USED_CNT=" + USED_CNT+ "&CLOSING_CNT=" + CLOSING_CNT
                                                     ;

                                            result2 = WebAPITester.prepareWebCall ( updateStock , StaticValues.getHeader () , "" );
                                            JOptionPane.showMessageDialog ( null , result2 );
                                    }
                                }catch(UnsupportedEncodingException e){
                                          System.out.println ( ""+e.getMessage () );    
                                }

                        }else{

                            OPENING = 0;
                            OPENING_CNT = 0;
                            jLabel15.setText("0");
                            
                            if(   jComboBox5.getSelectedItem ().toString().equals ( "Inward" )  ){
                                
                                RECEIVED = Integer.parseInt ( jTextField1.getText () ) ;
                                
                                if( qtyInNos >0 ){
                                    singleRMWt = Double.parseDouble(  new DecimalFormat("#.##").format( RECEIVED *1.0  / qtyInNos*1.0 ) ) ;
                                }
                                
                                USED = 0;
                                CLOSING = OPENING + RECEIVED;
                                jLabel16.setText(RECEIVED+"");
                                
                                RECEIVED_CNT = Integer.parseInt ( jTextField3.getText () ) ;
                                USED_CNT = 0;
                                CLOSING_CNT = OPENING_CNT + RECEIVED_CNT;
                                jLabel16.setText(RECEIVED_CNT+"");
                                
                            }else{
                                RECEIVED = 0 ;
                                USED = Integer.parseInt ( jTextField1.getText () ) ;
                                CLOSING = OPENING - USED;
                                jLabel17.setText(""+USED);
                                
                                RECEIVED_CNT = 0 ;
                                USED_CNT = Integer.parseInt ( jTextField3.getText () ) ;
                                CLOSING_CNT = OPENING_CNT - USED_CNT;
                                jLabel17.setText(""+USED_CNT);
                            }

                            int batchId  ;
                            
                                try{
                                    addEmpAPICall = "batchesadd?BatchName="+URLEncoder.encode(stkInBatchNo, "UTF-8")+"&BatchQty="+URLEncoder.encode(RECEIVED+"", "UTF-8")+"&BatchQty_CNT="+URLEncoder.encode(RECEIVED_CNT+"", "UTF-8")
                                        +"&BatchUOM_ID="+URLEncoder.encode(  raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] +"", "UTF-8")
                                        +"&BatchCreateDate="+URLEncoder.encode ( stkInDate , "UTF-8" )+"&BatchExpireDate=&ref_rm_id="+raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] 
                                        + "&inward_uid=" + stkInId+ "&rm_inward_date=" + URLEncoder.encode ( stkInDate , "UTF-8" )+ "&purchase_rate=" + prate  + "&conversion_value="+singleRMWt ;
                                result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                                //JOptionPane.showMessageDialog ( null , result2);
                                
                                if( result2.contains("success") ){
                                    batchId = iDFromJSON( result2  );

                                    String updateStock = "rmstockadd?RMI_ID=" +raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] + "&RM_ITEM_ID=" +raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] 
                                        + "&OPENING=" + OPENING +"&RECEIVED=" + RECEIVED+ "&USED=" + USED+ "&CLOSING=" + CLOSING 
                                        + "&CREATED_ON=" + URLEncoder.encode ( new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format ( Calendar.getInstance ().getTime () ) , "UTF-8" )
                                        + "&EDITED_ON=" + URLEncoder.encode (new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format ( Calendar.getInstance ().getTime () ) , "UTF-8" )+ "&EDITED_BY=" + Proman_User.getEID ()   
                                        +"&ref_batch_id="+batchId  +
                                            "&OPENING_CNT=" + OPENING_CNT      + "&RECEIVED_CNT=" + RECEIVED_CNT+ "&USED_CNT=" + USED_CNT+ "&CLOSING_CNT=" + CLOSING_CNT 
                                            + "&conversion_value="+singleRMWt ;

                                    result2 = WebAPITester.prepareWebCall ( updateStock , StaticValues.getHeader () , "" );
                                    JOptionPane.showMessageDialog ( null , result2 );

                                    }
                                }catch(UnsupportedEncodingException e){
                                          System.out.println ( ""+e.getMessage () );    
                                }    
                        jLabel18.setText(CLOSING+"");
                 
                 }
                 
                try {
            
                    String fileName = selectedFile.getAbsoluteFile ().getName ()  ;
                    
                    if( encodedString!=null &&  !encodedString.equals("")  &&  selectedFile !=null  && ! formatName.equals("") && RMD_UID!=0)
                    {
                        String docAddCallResult =  WebAPITester.prepareWebCall3 ( "refdocadd",   StaticValues.getHeader ()   , "&RD_NAME="+URLEncoder.encode( "BatchCode-"+stkInBatchNo+"-"+fileName  , "UTF-8")+"&RDM_UID="+URLEncoder.encode(  RMD_UID+"", "UTF-8")+"&ext="+URLEncoder.encode(  formatName  , "UTF-8") + "&item_id=" + URLEncoder.encode( raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] +"", "UTF-8")+ "&binarydata=" + URLEncoder.encode(encodedString, "UTF-8")  );
                    }
                } catch ( UnsupportedEncodingException ex ) {

                }catch(Exception e){}


                loadContentAfterTransaction ();
                loadScreenTask = new Thread(){
                public void run(){
                         loadContent();
                         getLatestRMStock ();
                         getRMStockLedger ();
                }
            };
            loadScreenTask.start ();
                //JOptionPane.showMessageDialog ( null , "Stock Successfuly Updated " );
                jTextField1.setText ( "" );
                jTextField14.setText ( "" );
                jTextField2.setText ( "" );
                jTextField8.setText ( "" );
                jLabel24.setText ( "" );
                selectedFile = null ;
                encodedString = null ;
                
    }//GEN-LAST:event_jButton1ActionPerformed

    
     public int iDFromJSON( String json){
        int id = 0;
       
        JSONObject jObject = new JSONObject ( json );
        Iterator<?> keys = jObject.keys ();
    
        JSONObject value = (JSONObject) jObject.get ( "data" );
            
        id = Integer.parseInt(value.get ("insert_id" ).toString());
        //    id = Integer.parseInt( st.getString ("insert_id" ));
        
        return id ;
    }
    
    
    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        if ( jComboBox1.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add item from Raw_material Master " );
        }             // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    File selectedFile = null;
    String encodedString = "";
    String formatName = "" ;
    byte[] fileContent = null;
    byte[] image = null;
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
                // TODO add your handling code here:
        
        JFileChooser fileChooser = new JFileChooser (  );
        fileChooser.setFileFilter ( new MyCustomFilter ("*") );
        fileChooser.setCurrentDirectory ( new File ( System.getProperty ( "user.home" ) ) );
        int result = fileChooser.showOpenDialog ( this );
        if ( result == JFileChooser.APPROVE_OPTION ) {

            selectedFile = fileChooser.getSelectedFile ();
            jLabel24.setText ( selectedFile.getAbsolutePath () );
            
            File selectedFile = fileChooser.getSelectedFile ();
            
            FileInputStream fis;
            ByteArrayOutputStream bos;
            try {
                fis = new FileInputStream(selectedFile);
                //fis = new FileInputStream ( new File ( "temp_doc."+formatName ) );
                bos = new ByteArrayOutputStream ();
                byte[] buf = new byte[ 1024 ];

                for ( int readNum ; ( readNum = fis.read ( buf ) ) != -1 ; ) {
                    bos.write ( buf , 0 , readNum );
                }

                formatName = selectedFile.getAbsolutePath ().substring ( selectedFile.getAbsolutePath ().lastIndexOf ( "." ) + 1 );
                image = bos.toByteArray ();
                
                byte[] encoded = Base64.encodeBase64 (image);
                fileContent = Base64.encodeBase64 (image);
                encodedString = new String(encoded);
               
                
            
                String tempFileName = "TestConvertFile."+formatName ;
                File downloaded = new File( tempFileName  ) ;
                encoded = Base64.decodeBase64 (encodedString );
                FileUtils.writeByteArrayToFile(      downloaded   , encoded ) ;
                
                
            } catch ( FileNotFoundException ex ) {
                System.out.println ( "Cannot process file "+ex.getMessage() );
            } catch ( IOException ex ) {
                System.out.println ( "Cannot process file "+ex.getMessage() );
            }

        }
    
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        
//        int selection = JOptionPane.showConfirmDialog ( null , "Deleting a record is not recommendedThe 'Delete' action may cause loss of any dependencies and may affect the transactional Data and Reports and make data inconsistent\nDeleting a plant will automatically delete all shifts for that plant\nPress 'No' to revert, Press 'Yes' to continue deleting", "Are you sure about deleting this record ?", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE);
//        if ( selection == JOptionPane.YES_OPTION ) { 
//            deleteMaster(  "finishedgoodsdelete?FG_ID="+selectedFGId    ) ; 
//            
//            String addEmpAPICall = "finishedgoods";
//                String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
//
//                if( !  result2.contains(  "not found"   )  ){
//
//                    if ( result2!= null ) {
//                        jTable1.setModel ( buildTableModelfromJSON (result2 ) );
//                        jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
//                        jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
//                    }
//                }
//        }        
    }//GEN-LAST:event_jButton4MouseClicked

    ActionListener selectCategoryAction = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.

            //jComboBox1.removeActionListener ( selectUnitAction );

           

            jLabel15.setText ( "0" );
            jLabel16.setText ( "0" );
            jLabel17.setText ( "0" );
            jLabel18.setText ( "0" );

            ResultSet result = null ;

            //jComboBox1.removeAllItems ();
            //if ( selectedMaster.equals ( "Raw Material" ) ) {
            //   for ( int i = 0 ; i < raw_materials.size () ; i ++ ) {
            //        jComboBox1.addItem ( raw_materials.get ( i )[ 1 ] );
            //   }
                    selectedItemId = Integer.parseInt ( raw_materials.get ( jComboBox1.getSelectedIndex () )[ 0 ] );
                    //jComboBox1.addActionListener ( selectUnitAction );
            //   result = DB_Operations.executeSingle ( "SELECT RM_TYPE, (OPENING),(RECEIVED),(USED),(CLOSING), MAX(RMStock_TR_ID) FROM raw_material A INNER JOIN RMStock B ON A.RM_ID = B.RM_ITEM_ID AND A.RM_ID =" + selectedItemId );
            //}
            
                try{
                    if ( result != null ) {

                        String a, b, c;
                        a = result.getString ( 2 );
                        b = result.getString ( 3 );
                        c = result.getString ( 4 );
                        
                        OldOPENING = Integer.parseInt ( result.getString ( 5 ) );
                        
                    }
            
                    result.close ();
            
                }catch(  Exception e1 ){
                    
                        OldOPENING = 0 ;
                        
                        StaticValues.writer.writeExcel ( UpdateStockPanel.class.getSimpleName () , UpdateStockPanel.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
                }
                
                jLabel15.setText ( OldOPENING + "" );
            
        }
    };


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox3;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables

}
