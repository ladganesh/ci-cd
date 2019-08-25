/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Derived.AutoCompletion;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.examples.HomeScreen;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class ProductCustomerMap extends javax.swing.JPanel {

    ResultSet result = null;

    ArrayList<String[]> finished_goods = null;
    ArrayList<String[]> customer = null;
    ArrayList<String[]> uom = null;

    DecimalFormat df = new DecimalFormat ( "#.##" );

    int selectedRecord = 0;

    String resource = "", resourceType = "";

    ArrayList<ProductDR> products = null;
    ArrayList<String[]> customerCombo = null;

    /**
     * Creates new form ProductCustomerMap
     */
    public ProductCustomerMap () {
        initComponents ();

        resourceType = "Masters";
        resource = "ProductCustomerMap";

//        try {
//            StaticValues.checkUserRolePermission ( resourceType , resourceType );
//        } catch ( Exception e ) {
//            StaticValues.writer.writeExcel ( resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }

        AutoCompletion.enable ( jComboBox1 );
        AutoCompletion.enable ( jComboBox2 );

//        try {
            loadContent ();
//        } catch ( Exception e ) {
//            StaticValues.writer.writeExcel ( ProductCustomerMap.class.getSimpleName () , ProductCustomerMap.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }
//
//        try {
//
//            result = DB_Operations.getMasters ( "customer" );
//            if ( result != null ) {
//                customer = new ArrayList<String[]> ();
//                while ( result.next () ) {
//                    customer.add ( new String[] { result.getString ( "CUSTOMER_ID" ) , result.getString ( "CUSTOMER_NAME" ) } );
//                    jComboBox2.addItem ( result.getString ( "CUSTOMER_NAME" ) );
//                }
//            } else {
//                jComboBox2.addItem ( "Not Available" );
//            }
//
//            result = DB_Operations.getMasters ( "finished_goods" );
//            if ( result != null ) {
//                finished_goods = new ArrayList<String[]> ();
//                while ( result.next () ) {
//                    finished_goods.add ( new String[] { result.getString ( "FG_ID" ) , result.getString ( "FG Name" ) , result.getString ( "FG_UOM_ID" ) } );
//                    jComboBox1.addItem ( result.getString ( "FG Name" ) );
//                }
//            } else {
//                jComboBox1.addItem ( "Not Available" );
//            }
//
//            jComboBox1.addActionListener ( productComboAction );
//            jLabel4.setText ( finished_goods.get ( jComboBox1.getSelectedIndex () )[ 2 ] );
//
//
//            result.close ();
//        } catch ( Exception e ) {
//            System.out.println ( " " + e.getMessage () );
//            StaticValues.writer.writeExcel ( ProductCustomerMap.class.getSimpleName () , ProductCustomerMap.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }

        jButton1.setEnabled ( true );

        jTextField1.addKeyListener ( k );
        jTextField1.addFocusListener ( f2 );

    }

    KeyListener k = new KeyListener () {
        @Override
        public void keyTyped ( KeyEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            char enter = e.getKeyChar ();
            String dot = Character.toString ( enter );

            if (  ! ( Character.isDigit ( enter ) ) &&  ! dot.equals ( "." ) ) {
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
                if ( num < 0 || num == 0 ) {
                    jcb.setText ( "" );
                }

            } catch ( NumberFormatException ex1 ) {
                jcb.setText ( x );
                StaticValues.writer.writeExcel ( ProductCustomerMap.class.getSimpleName () , ProductCustomerMap.class.getSimpleName () , ex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }
    };

    ActionListener productComboAction = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {

            Vector<String> columnNames = new Vector<String> ();
            columnNames.add ( "Part" );
            columnNames.add ( "Customer" );
            columnNames.add ( "Selling Rate" );
            columnNames.add ( "Unit" );
            jTable1.setModel ( new DefaultTableModel ( null , columnNames )  );
            
            
            jLabel4.setText ( StaticValues.getUOMName ( Integer.parseInt( products.get ( jComboBox1.getSelectedIndex () ).UOM )) );
            
            if( products.size ()>0 ){
                    selectedFGId = products.get( jComboBox1.getSelectedIndex () ).FG_ID ;
            }
            
            String addEmpAPICall = "customermaps?FG_ID="+selectedFGId;
            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

            if ( ! result2.contains( "\"records\":null" ) ){
                jTable1.setModel ( buildTableModelfromJSON_Selection (result2 ) );
                jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
            }
        }
    };

    public void loadContent () {

        if ( jComboBox1.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            String getFGList = "finishedgoods";
            String fgString = WebAPITester.prepareWebCall ( getFGList , StaticValues.getHeader () , "" );
            
            if( ! fgString.contains( "not found"  ) ){
                    HashMap<String , Object> map = new HashMap<String , Object> ();
                    JSONObject jObject = new JSONObject ( fgString );
                    Iterator<?> keys = jObject.keys ();

                    while ( keys.hasNext () ) {
                        String key = ( String ) keys.next ();
                        Object value = jObject.get ( key );
                        map.put ( key , value );
                    }

                    JSONObject st = ( JSONObject ) map.get ( "data" );
                    JSONArray records = st.getJSONArray ( "records" );

                    JSONObject emp = null;
                    products = new ArrayList<ProductDR> ();
                    for ( int i = 0 ; i < records.length () ; i ++ ) {

                        emp = records.getJSONObject ( i );
                        jComboBox1.addItem ( emp.get ( "FG_PART_NO" ).toString () );
                        products.add ( new ProductDR ( Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) , emp.get ( "PART_NAME" ).toString () , emp.get ( "FG_UOM_ID" ).toString () ) );

                        System.out.println ( ""+Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) +"       "+emp.get ( "PART_NAME" ).toString () +"    "+ emp.get ( "FG_UOM_ID" ).toString () );
                    }

                    jLabel4.setText ( StaticValues.getUOMName (  Integer.parseInt(  products.get ( jComboBox1.getSelectedIndex () ).UOM )) );
                    jComboBox1.addActionListener ( productComboAction );
            }
        }

        if ( jComboBox2.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            String getcustomerList = "customers";
            String customerString = WebAPITester.prepareWebCall ( getcustomerList , StaticValues.getHeader () , "" );
            
            
            if( !  customerString.contains( "not found"  )  ){
                    HashMap<String , Object> map = new HashMap<String , Object> ();
                    JSONObject jObject = new JSONObject ( customerString );
                    Iterator<?> keys = jObject.keys ();

                    while ( keys.hasNext () ) {
                        String key = ( String ) keys.next ();
                        Object value = jObject.get ( key );
                        map.put ( key , value );
                    }

                    JSONObject st = ( JSONObject ) map.get ( "data" );
                    JSONArray records = st.getJSONArray ( "records" );

                    JSONObject emp = null;
                    customerCombo = new ArrayList<String[]> ();
                    for ( int i = 0 ; i < records.length () ; i ++ ) {

                        emp = records.getJSONObject ( i );
                        jComboBox2.addItem ( emp.get ( "PLANT_CODE" ).toString() +" ( "+emp.get ("CUSTOMER_NAME" ).toString() +" )" );
                        customerCombo.add ( new String[] { emp.get ( "CUSTOMER_ID" ).toString () , emp.get ( "PLANT_CODE" ).toString() +" ( "+emp.get ("CUSTOMER_NAME" ).toString() +" )" } );
                    }
            }
        }

        String addEmpAPICall = "customermaps?FG_ID="+products.get( jComboBox1.getSelectedIndex () ).FG_ID ;
        String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

        if ( result2 != null    &&  !result2.contains( "not found" )) {
            jTable1.setModel ( buildTableModelfromJSON_Selection (result2 ) );
            jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
            jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
        }

    }

    public DefaultTableModel buildTableModelfromJSON ( String employeeJSONList ) {

        Vector<String> columnNames = new Vector<String> ();
         Vector<String> columnNames2 = new Vector<String> ();
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();

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
        columnNames.add ( "FG_C_ID" );
        columnNames.add ( "M_FG_ID" );
        columnNames.add ( "M_CUST_ID" );
        columnNames.add ( "SALES_RATE" );
        columnNames.add ( "S_UOM_ID" );

        columnNames2.add ( "FG_C_ID" );
        columnNames2.add ( "Part" );
            columnNames2.add ( "Customer" );
            columnNames2.add ( "Selling Rate" );
            columnNames2.add ( "Unit" );
        
        
        
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
            boolean fgmatched = false;

            Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {
                emp = records.getJSONObject ( i );

                fgmatched = false;
                try{
                    if( selectedFGId ==  Integer.parseInt( emp.get ( columnNames.get ( 1 ) ).toString()  )   ){
                        fgmatched = true ;
                    }else if( selectedFGId == 0 ){
                         fgmatched = true ;
                    }
                }catch( NumberFormatException e ){
                    
                }
                
                if(fgmatched){
                        if ( columnIndex == 1 ) {
                            for ( int j = 0 ; j < products.size () ; j ++ ) {
                                if ( products.get ( j ).FG_ID == Integer.parseInt ( emp.get ( columnNames.get ( columnIndex ) ).toString () ) ) {
                                    vector.add ( products.get ( j ).FG_PART_NO );
                                }
                            }
                        }else if( columnIndex == 2){
                            for( int k=0; k<customerCombo.size ();k++){
                                System.out.println ( Integer.parseInt( customerCombo.get ( k)[0])+"     "+Integer.parseInt( emp.get ( columnNames.get ( columnIndex ) ).toString())  );
                                
                                if( Integer.parseInt( customerCombo.get ( k)[0]) ==Integer.parseInt( emp.get ( columnNames.get ( columnIndex ) ).toString())  ){
                                    vector.add ( customerCombo.get ( k)[1] );
                                }
                            }
                        }else if( columnIndex == 4){
                            
                            vector.add ( StaticValues.getUOMName ( Integer.parseInt( emp.get ( columnNames.get ( columnIndex ) ).toString() ) ) );
                            
                        }else{

                            vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                        }
                //vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                }
            }
            
             if( fgmatched ){
                data.add ( vector );
             }
            
        }
        return new DefaultTableModel ( data , columnNames2 );
    }
    
    
     public DefaultTableModel buildTableModelfromJSON_Selection ( String employeeJSONList ) {

        Vector<String> columnNames = new Vector<String> ();
        Vector<String> columnNames2 = new Vector<String> ();
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();

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
        columnNames.add ( "FG_C_ID" );
        columnNames.add ( "M_FG_ID" );
        columnNames.add ( "M_CUST_ID" );
        columnNames.add ( "SALES_RATE" );
        columnNames.add ( "S_UOM_ID" );

        columnNames2.add ( "FG_C_ID" );
        columnNames2.add ( "Part" );
            columnNames2.add ( "Customer" );
            columnNames2.add ( "Selling Rate" );
            columnNames2.add ( "Unit" );
        
        
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
            boolean fgmatched = false;

            Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {
                emp = records.getJSONObject ( i );

               
                        if ( columnIndex == 1 ) {
                            for ( int j = 0 ; j < products.size () ; j ++ ) {
                                if ( products.get ( j ).FG_ID == Integer.parseInt ( emp.get ( columnNames.get ( columnIndex ) ).toString () ) ) {
                                    vector.add ( products.get ( j ).FG_PART_NO );
                                }
                            }
                        }else if( columnIndex == 2){
                            for( int k=0; k<customerCombo.size ();k++){
                                System.out.println ( Integer.parseInt( customerCombo.get ( k)[0])+"     "+Integer.parseInt( emp.get ( columnNames.get ( columnIndex ) ).toString())  );
                                
                                if( Integer.parseInt( customerCombo.get ( k)[0]) ==Integer.parseInt( emp.get ( columnNames.get ( columnIndex ) ).toString())  ){
                                    vector.add ( customerCombo.get ( k)[1] );
                                }
                            }
                        }else if( columnIndex == 4){
                            
                            vector.add ( StaticValues.getUOMName ( Integer.parseInt( emp.get ( columnNames.get ( columnIndex ) ).toString() ) ) );
                            
                        }else{

                            vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                        }
                //vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                }
            
            
                data.add ( vector );
           
        }
        return new DefaultTableModel ( data , columnNames2 );
    }
    
   
    public void loadContent_old () {

        try {

            result = DB_Operations.executeSingle ( DB_Operations.SELECT_FG_CUS_MAP );
            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
            jTable1.setModel ( buildTableModel ( result ) );

            jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
            jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );

        } catch ( SQLException e ) {
            System.out.println ( e.getMessage () + "  ERRORRRR" );
            StaticValues.writer.writeExcel ( ProductCustomerMap.class.getSimpleName () , ProductCustomerMap.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

    }

    public void resetForm () {

//        jComboBox1.setSelectedIndex ( 0 );
//        jComboBox2.setSelectedIndex ( 0 );
        jTextField1.setText ( "" );

        jButton1.setEnabled ( true );
        jButton1.setText ( "Add" );
        jButton4.setEnabled ( false );

    }

    public DefaultTableModel buildTableModel ( ResultSet rs )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        int columnCount = metaData.getColumnCount ();
        for ( int column = 1 ; column <= 5 ; column ++ ) {
            columnNames.add ( metaData.getColumnName ( column ) );
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        while ( rs.next () ) {
            Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 1 ; columnIndex <= 5 ; columnIndex ++ ) {

                vector.add ( rs.getObject ( columnIndex ) );

            }
            data.add ( vector );
        }

        return new DefaultTableModel ( data , columnNames );

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1134, 356));
        setLayout(null);

        jComboBox1.setNextFocusableComponent(jComboBox2);
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        add(jComboBox1);
        jComboBox1.setBounds(150, 40, 220, 35);

        jComboBox2.setNextFocusableComponent(jTextField1);
        jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox2MouseClicked(evt);
            }
        });
        add(jComboBox2);
        jComboBox2.setBounds(150, 90, 220, 35);

        jTextField1.setOpaque(false);
        add(jTextField1);
        jTextField1.setBounds(150, 160, 110, 35);

        jButton1.setText("Add");
        jButton1.setOpaque(false);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(400, 310, 90, 40);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Part Code\\No");
        add(jLabel1);
        jLabel1.setBounds(40, 40, 80, 30);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Customer");
        add(jLabel2);
        jLabel2.setBounds(40, 90, 80, 30);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Sales Rate");
        add(jLabel3);
        jLabel3.setBounds(40, 160, 90, 30);

        jButton2.setText("Close");
        jButton2.setOpaque(false);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(700, 310, 90, 40);

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
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTable1FocusLost(evt);
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(400, 40, 710, 250);

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 0, 51));
        jLabel26.setText("*");
        add(jLabel26);
        jLabel26.setBounds(30, 170, 20, 10);

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 51));
        jLabel27.setText("*");
        add(jLabel27);
        jLabel27.setBounds(30, 50, 20, 10);

        jButton3.setText("Reset");
        jButton3.setOpaque(false);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(600, 310, 90, 40);

        jButton4.setText("Update");
        jButton4.setEnabled(false);
        jButton4.setOpaque(false);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(500, 310, 90, 40);

        jLabel4.setText("jLabel4");
        add(jLabel4);
        jLabel4.setBounds(290, 160, 80, 40);

        jLabel5.setText("per");
        add(jLabel5);
        jLabel5.setBounds(260, 170, 20, 16);

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 0, 51));
        jLabel29.setText("*");
        add(jLabel29);
        jLabel29.setBounds(30, 100, 20, 10);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:

        //    jButton1.setEnabled ( false );
        String timestamp = calculateTimeStamp ();

        int selectedProduct = 0, selectedCustomer = 0, selectedUOM = 0;
        double sales_rate;
        String UOM;

        try {

            selectedProduct =  products.get ( jComboBox1.getSelectedIndex () ).FG_ID  ;
            selectedCustomer = Integer.parseInt ( customerCombo.get ( jComboBox2.getSelectedIndex () )[0] );
            UOM =  products.get ( jComboBox1.getSelectedIndex () ).UOM;
//            try {
//                ResultSet rs = DB_Operations.executeSingle ( "select UOM_ID from unit_meas where UOM = '" + UOM + "'" );
//                selectedUOM = rs.getInt ( "UOM_ID" );
//                //selectedUOM = 0;
//                rs.close ();
//                
//                
//            } catch ( SQLException e ) {
//
//            }
            
         
            

            if (  ! jTextField1.getText ().equals ( "" ) && selectedProduct != 0 && selectedCustomer != 0 ) {

                sales_rate = Double.parseDouble ( df.format ( Double.parseDouble ( jTextField1.getText ().trim () ) ) );

                String result = DB_Operations.insertIntoFG_CUS_MAP ( selectedProduct , selectedCustomer , sales_rate , selectedUOM , calculateTimeStamp () , calculateTimeStamp () , 1 );
                //JOptionPane.showMessageDialog ( null , result );
                
                String mapExist = WebAPITester.prepareWebCall ( "customermaps?FG_ID="+selectedProduct+"&CUSTOMER_ID="+selectedCustomer , StaticValues.getHeader () , "" );

                if(   mapExist.contains ( "success")  && mapExist.contains ( "not found") ){
                        String addEmpAPICall = "customermapadd?M_FG_ID="+selectedProduct+"&M_CUST_ID="+selectedCustomer+"&SALES_RATE="+sales_rate+"&S_UOM_ID="+UOM;
                        String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

                        if( result2.contains ( "success") ){
                                JOptionPane.showMessageDialog ( null , "Successfuly created new entry Part - Customer Relation master" );

                                 addEmpAPICall = "customermaps?FG_ID="+selectedFGId;
                                 result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

                                if ( result2 != null ) {
                                    jTable1.setModel ( buildTableModelfromJSON ( result2 ) );
                                    jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                                    jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                                }

                        }
                        
                }else{
                    
                    JOptionPane.showMessageDialog ( null , "Relationship already exist !" );
                }

               // loadContent ();
                resetForm ();
            } else {
                JOptionPane.showMessageDialog ( null , "Please fill / select all fields marked with *" );
                jButton1.setEnabled ( true );
            }
        } catch ( Exception e ) {
            jButton1.setEnabled ( true );
            StaticValues.writer.writeExcel ( ProductCustomerMap.class.getSimpleName () , ProductCustomerMap.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

    }//GEN-LAST:event_jButton1MouseClicked

    public void setValueInComboBox ( JComboBox myCombo , ArrayList<String[]> values , String value ) {

        for ( int i = 0 ; i < values.size () ; i ++ ) {
            if ( values.get ( i )[ 1 ].equals ( value ) ) {
                myCombo.setSelectedIndex ( i );
            }
        }
    }

    public void setValueInComboBoxProduct ( JComboBox myCombo , ArrayList<ProductDR> values , String value ) {

        for ( int i = 0 ; i < values.size () ; i ++ ) {
            if ( values.get ( i ).FG_PART_NO.equals ( value ) ) {
                myCombo.setSelectedIndex ( i );
            }
        }
    }
    
    int selectedRowIndex = 0 ;
    
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        jButton4.setEnabled ( true );
        DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();

        // get the selected row index
        selectedRowIndex = jTable1.getSelectedRow ();
        // Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString() )

        selectedRecord = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );

        setValueInComboBoxProduct ( jComboBox1 , products , model.getValueAt ( selectedRowIndex , 1 ).toString () );
        setValueInComboBox ( jComboBox2 , customerCombo , model.getValueAt ( selectedRowIndex , 2 ).toString () );

        jTextField1.setText ( model.getValueAt ( selectedRowIndex , 3 ).toString () );
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost
        // TODO add your handling code here:
        //     jButton1.setText ( "Add" );
    }//GEN-LAST:event_jTable1FocusLost

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        HomeScreen.home.removeForms ();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        resetForm ();
        loadContent ();
    }//GEN-LAST:event_jButton3MouseClicked

    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
        if ( jComboBox2.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add values in Customer from Customer Master" );
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2MouseClicked

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
       // if ( jComboBox1.getItemCount () == 0 ) {
       //     JOptionPane.showMessageDialog ( null , "<html><size='06'>First add values in product from Finished Good Master" );
       // }        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        String timestamp = calculateTimeStamp ();

        int selectedProduct = 0, selectedCustomer = 0, selectedUOM = 0;
        double sales_rate;
        try {
            selectedProduct =  products.get ( jComboBox1.getSelectedIndex () ).FG_ID ;
            selectedCustomer = Integer.parseInt ( customerCombo.get ( jComboBox2.getSelectedIndex () )[ 0 ] );
            selectedUOM = Integer.parseInt( products.get ( jComboBox1.getSelectedIndex () ).UOM );

            DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();


            selectedRecord = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );

            if (  ! jTextField1.getText ().equals ( "" ) && selectedProduct != 0 && selectedCustomer != 0 ) {

                sales_rate = Double.parseDouble( df.format ( Float.parseFloat ( jTextField1.getText ().trim () ) ) );
//                String result = DB_Operations.updateFG_CUS_MAP ( selectedProduct , selectedCustomer , sales_rate , selectedUOM , calculateTimeStamp () , 1 , selectedRecord );
//                JOptionPane.showMessageDialog ( null , result );

                //customermapedit
                String addEmpAPICall = "customermapupdate?FG_C_ID="+selectedRecord+"&SALES_RATE="+sales_rate;
                String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

                if( result2.contains ( "success") ){
                        JOptionPane.showMessageDialog ( null , "Successfuly updated entry in Part - Customer Relation master" );

                         addEmpAPICall = "customermaps?FG_ID="+selectedProduct;
                         result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

                        if ( result2 != null ) {
                            jTable1.setModel ( buildTableModelfromJSON ( result2 ) );
                            jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                            jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                        }
                }

                //loadContent ();
                resetForm ();

            } else {

                JOptionPane.showMessageDialog ( null , "Please fill / select all fields marked with *" );
                //    jButton1.setEnabled ( true );
            }

        } catch ( Exception e ) {
            StaticValues.writer.writeExcel ( ProductCustomerMap.class.getSimpleName () , ProductCustomerMap.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            //jButton1.setEnabled ( true );
            System.out.println ( ""+e.getMessage () );
        }

    }//GEN-LAST:event_jButton4MouseClicked

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
//                if( products.size ()>0 ){
//                    selectedFGId = products.get( jComboBox1.getSelectedIndex () ).FG_ID ;
//                }
//              //  loadContent ();
//              String addEmpAPICall = "customermaps";
//            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
//
//            if ( result2 != null ) {
//                jTable1.setModel ( buildTableModelfromJSON ( result2 ) );
//                jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
//                jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
//            }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    int selectedFGId = 0;
    
    public String calculateTimeStamp () {

        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "dd-MM-yyyy HH:mm:ss" );

        return sdf2.format ( c2.getTime () );

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
