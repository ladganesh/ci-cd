/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.ProductForCustomer;
import trixware.erp.prodezydesktop.Model.Product_CustomerModel;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.System.exit;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import trixware.erp.prodezydesktop.Utilities.HintTextField;
import trixware.erp.prodezydesktop.Utilities.MyCustomFilter;
import trixware.erp.prodezydesktop.components.ProductforCustPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class SalesForm_New extends javax.swing.JPanel {

    boolean flag_required_master_data_NA = false;
   // public static ArrayList<String> products;
    public ArrayList<String[]> productRates;
    public static ArrayList<String> customers;
    public static ArrayList<String[]> product_ids_qtys;
    public int previousOA = 0;

    int selectedCustomer = 0;
    double totalOrderValue = 0.0;
    
    ArrayList<String[]> customerCombo = null;
   
    ArrayList<String > prod_ids= new ArrayList<String>();
    
    ArrayList<ProductDR> products = new ArrayList<ProductDR>();
    
    public ArrayList<ProductforCustPanel> ProductTypeList  ;

    //ArrayList<Model.ProductDR> maintain_type = null;
    //Model.ProductForCustomer prod_cust = null ;
    ProductforCustomer prod_cust = null ;
    ArrayList<Product_CustomerModel> maintenance_type_DetailList=null;
    
    int selected_cust_id;
    
    String load_Cust_prod,result_for_cust_prod;
    /**
     * Creates new form SalesForm
     */
    public SalesForm_New () {
        initComponents ();

        ResultSet result = DB_Operations.getMasters ( "customer" );

        jComboBox2.removeAllItems ();
        //jComboBox1.removeAllItems ();

//        try {
//            if ( result != null ) {
//                customers = new ArrayList<String> ();
//                while ( result.next () ) {
//                    jComboBox2.addItem ( result.getString ( "CUSTOMER_NAME" ) );
//                    customers.add ( String.valueOf ( result.getInt ( "CUSTOMER_ID" ) ) );
//                }
//                result.close ();
//            } else {
//
//                jComboBox2.addItem ( "Not Available" );
//                flag_required_master_data_NA = true;
//                show_data_NA_message ( "Customer" );
//            }

//            if ( jComboBox1.getItemCount () == 0 ) {
//            //result = DB_Operations.getMasters ( "raw_material_type" );
//
//            String getFGList = "finishedgoods";
//            String fgString = WebAPITester.prepareWebCall ( getFGList , StaticValues.getHeader () , "" );
//            
//            if( ! fgString.contains( "not found"  ) ){
//                    HashMap<String , Object> map = new HashMap<String , Object> ();
//                    JSONObject jObject = new JSONObject ( fgString );
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
//                    products = new ArrayList<ProductDR> ();
//                    for ( int i = 0 ; i < records.length () ; i ++ ) {
//
//                        emp = records.getJSONObject ( i );
//                        jComboBox1.addItem ( emp.get ( "FG_PART_NO" ).toString () );
//                        products.add ( new ProductDR ( Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) , emp.get ( "FG_PART_NO" ).toString () , emp.get ( "FG_UOM_ID" ).toString () ) );
//
//                  //      System.out.println ( ""+Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) +"       "+emp.get ( "FG_PART_NO" ).toString () +"    "+ emp.get ( "FG_UOM_ID" ).toString () );
//                    }
//
//                    //jLabel4.setText ( products.get ( jComboBox1.getSelectedIndex () ).UOM );
//                //    jComboBox1.addActionListener ( productComboAction );
//            }
//        }
            



//            result = DB_Operations.getMasters ( "finished_goods" );
//            if ( result != null ) {
//                products = new ArrayList<String> ();
//                while ( result.next () ) {
//                    jComboBox1.addItem ( result.getString ( "FG Name" ) );
//                    products.add ( result.getString ( "FG_ID" ) );
//                }
//                result.close ();
//            } else {
//
//                jComboBox1.addItem ( "Not Available" );
//                flag_required_master_data_NA = true;
//                show_data_NA_message ( "Finished Goods / Product" );
//            }

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
                

        String getcustomerList = "salesorders";
        String salesOrderData = WebAPITester.prepareWebCall ( getcustomerList , StaticValues.getHeader () , "" );

        if( !  salesOrderData.contains( "not found"  )  ){
            jTable1.setModel ( buildTableModelfromJSON ( salesOrderData ) );
            jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
            jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
        }

//                   load_Cust_prod = "customermaps?CUSTOMER_ID=1";
//        result_for_cust_prod = WebAPITester.prepareWebCall ( load_Cust_prod , StaticValues.getHeader () , "" );

        

        jComboBox2.addActionListener ( ComboBoxAction );
        
        
//        prod_cust = new ProductforCustomer ();
//        prod_cust.setBounds ( 280 , 50 , 315 , 260 );
//        add( prod_cust );
//        revalidate();
//        repaint();
        
    }
    
    public void show_data_NA_message ( String data ) {

        JOptionPane.showMessageDialog ( null , data + " details not availabel in masters. This information is important for carriying out this activity. Consider updating the master first ! " , "Data Required " , 0 );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField5 = new HintTextField("Upload PO Copy");
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(962, 518));
        setLayout(null);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Product Code");
        add(jLabel2);
        jLabel2.setBounds(260, 10, 190, 20);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Customer");
        add(jLabel3);
        jLabel3.setBounds(10, 10, 130, 20);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox2.setNextFocusableComponent(jTextField1);
        add(jComboBox2);
        jComboBox2.setBounds(10, 30, 230, 40);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Number");
        add(jLabel4);
        jLabel4.setBounds(140, 170, 100, 20);

        jTextField1.setNextFocusableComponent(jTextField4);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(10, 190, 230, 30);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Upload PO");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(150, 320, 90, 40);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("OA Number");
        add(jLabel9);
        jLabel9.setBounds(10, 230, 230, 20);

        jTextField4.setEditable(false);
        jTextField4.setNextFocusableComponent(jTextField5);
        add(jTextField4);
        jTextField4.setBounds(10, 250, 230, 30);

        dateChooserCombo1.setCalendarPreferredSize(new java.awt.Dimension(400, 250));
        dateChooserCombo1.setFormat(2);
        add(dateChooserCombo1);
        dateChooserCombo1.setBounds(10, 120, 230, 30);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setText("Order Date");
        add(jLabel10);
        jLabel10.setBounds(10, 100, 90, 20);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("Purchase Order");
        add(jLabel11);
        jLabel11.setBounds(10, 170, 210, 20);

        jButton6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton6.setText("Add Order");
        jButton6.setOpaque(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        add(jButton6);
        jButton6.setBounds(550, 382, 90, 40);

        jButton8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton8.setText("Cancel");
        jButton8.setOpaque(false);
        add(jButton8);
        jButton8.setBounds(640, 382, 90, 40);

        jButton9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton9.setText("Export to Excel");
        jButton9.setOpaque(false);
        add(jButton9);
        jButton9.setBounds(730, 382, 110, 40);

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

        add(jScrollPane1);
        jScrollPane1.setBounds(600, 30, 380, 330);

        jTextField5.setNextFocusableComponent(jButton1);
        jTextField5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField5MouseClicked(evt);
            }
        });
        add(jTextField5);
        jTextField5.setBounds(10, 290, 230, 30);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Total Order Value");
        add(jLabel1);
        jLabel1.setBounds(420, 370, 110, 16);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 18)); // NOI18N
        jLabel6.setText(totalOrderValue+"");
        add(jLabel6);
        jLabel6.setBounds(390, 390, 140, 36);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Reset ");
        jButton3.setRequestFocusEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(840, 382, 70, 40);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jTextField5.setText ( createFileNameResource ( "PartDrawingFile" ) );
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField5MouseClicked
        // TODO add your handling code here:
        openFile ( jTextField5.getText () );
    }//GEN-LAST:event_jTextField5MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        String customer_id, po_date, po_copy_name, order_no;

        //customer_id = customers.get ( jComboBox2.getSelectedIndex () );
        customer_id =  customerCombo.get ( jComboBox2.getSelectedIndex () )[0];
        
        po_date = dateChooserCombo1.getText ();
        po_copy_name = purchaseOrderFile_name;
        order_no = jTextField1.getText ();

        int i = 0;
        product_ids_qtys = new ArrayList<String[]> ();
        while ( i < orderDetails.size () ) {
            product_ids_qtys.add ( new String[] { orderDetails.get ( i ).O_ID , orderDetails.get ( i ).qty , orderDetails.get ( i ).rate+"" } );
            i ++;
        }

        
        if( ! jTextField1.getText().toString().equals( "") &&  product_ids_qtys.size ()>0){
        

                try{
                    String addEmpAPICall = "salesorderadd?so_customer_id="+URLEncoder.encode(  customer_id  , "UTF-8")+"&product_ids="+URLEncoder.encode( "", "UTF-8")+"&po_date="+URLEncoder.encode(  new SimpleDateFormat("yyyy-MM-dd").format ( dateChooserCombo1.getSelectedDate ().getTime ())+" 00:00:00"  , "UTF-8")+"&po_copy_name="+URLEncoder.encode( po_copy_name, "UTF-8")+"&order_qty="+URLEncoder.encode( totalOrderValue+"", "UTF-8")+"&order_no="+URLEncoder.encode( order_no, "UTF-8")+"&order_ack="+URLEncoder.encode( previousOA+"", "UTF-8") ;
                    String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    JOptionPane.showMessageDialog ( null ,msgFromJSON( result2));

                    int recordId = iDFromJSON ( result2 ) ;

                    for( int j=0; j<product_ids_qtys.size(); j++ ){

                     try{
                                String addPO = "podetailadd?product_id="+URLEncoder.encode(  product_ids_qtys.get(j)[0]  , "UTF-8")+"&product_qty="+URLEncoder.encode( product_ids_qtys.get(j)[1] , "UTF-8")+"&finished_qty="+URLEncoder.encode( "0", "UTF-8")+"&ref_po_id="+URLEncoder.encode( recordId+"", "UTF-8") +"&eff_rate="+URLEncoder.encode( product_ids_qtys.get(j)[2]+"", "UTF-8") ;
                                String addPOResult =  WebAPITester.prepareWebCall ( addPO, StaticValues.getHeader ()   , "");
                                JOptionPane.showMessageDialog ( null , msgFromJSON(addPOResult));
                    }catch(UnsupportedEncodingException e){

                    }
                }

                }catch(UnsupportedEncodingException e){

                }
        }else{
            
            JOptionPane.showMessageDialog ( null , "Please ensure valid PO no is provided and products are selected");
            
        }
        
        //int result = DB_Operations.insertIntoSalesOrderAndOrderDetails ( Integer.parseInt ( customer_id ) , po_date , po_copy_name , order_no , product_ids_qtys , previousOA , totalOrderValue + "" );
        loadContent ();
        
        
        //jButton2.setEnabled ( false );

    }//GEN-LAST:event_jButton6ActionPerformed

    public String msgFromJSON(  String json){
        
   
        HashMap<String, Object> map = new HashMap<String , Object> ();
        JSONObject jObject = new JSONObject ( json );
        Iterator<?> keys = jObject.keys ();

            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }

           JSONObject st = ( JSONObject ) map.get ( "message" );
           st = ( JSONObject ) st.get ( "success" );
              
        return st.get ("104" ).toString();
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
        columnNames.add ( "sales_po_id" );
        //columnNames.add ( "so_customer_id" );
        columnNames.add ( "CUSTOMER_NAME" );
        columnNames.add ( "order_qty" );
        columnNames.add ( "order_no" );
        columnNames.add ( "po_date" );
        
        
        columnNames2 = new Vector<String> ();
        columnNames2.add ( "PO ID" );
        columnNames2.add ( "Customer" );
        columnNames2.add ( "Order Qty" );
        columnNames2.add ( "Order No" );
        columnNames2.add ( "Order Date" );

        
        
        
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
            boolean fgmatched = false;

            Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {
                emp = records.getJSONObject ( i );
                vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
             }
            data.add ( vector );
        }
        return new DefaultTableModel ( data , columnNames2 );
    }    
    
    public void buildTableModelfromJSON2 ( String employeeJSONList ) {

        Vector<String> columnNames = new Vector<String> ();
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
        columnNames.add ( "M_FG_ID" );
        columnNames.add ( "M_CUST_ID" );
        columnNames.add ( "SALES_RATE" );
    
        productRates = new ArrayList<String[]> ();
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
            emp = records.getJSONObject ( i );
            productRates.add ( new String[] { emp.get ( "M_FG_ID" ).toString() ,emp.get ( "M_CUST_ID" ).toString() , emp.get ( "SALES_RATE" ).toString()  } );
       //     System.out.println ( ""+emp.get ( "M_FG_ID" ).toString() +"       "+emp.get ( "M_CUST_ID" ).toString() +"       "+ emp.get ( "SALES_RATE" ).toString()  );
        }
        
        
    }
    
    public void loadContent () {

         String addEmpAPICall = "salesorders";
            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

            if ( result2 != null && ! result2.contains( "not found" )) {
                jTable1.setModel ( buildTableModelfromJSON ( result2 ) );
                jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
            }
       
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

    class OrderDetails {

        public String O_ID;
        public String productName;
        public String qty;
        public double totalVal = 0.0;
        public double rate ;
    }

    public int iDFromJSON( String json){
        int id = 0;
       
        JSONObject jObject = new JSONObject ( json );
        Iterator<?> keys = jObject.keys ();
    
        JSONObject value = (JSONObject) jObject.get ( "data" );
            
        id = Integer.parseInt(value.get ("insert_id" ).toString());
        //    id = Integer.parseInt( st.getString ("insert_id" ));
        
        return id ;
    }
    
    
    ArrayList<OrderDetails> orderDetails = new ArrayList<OrderDetails> ();
    DefaultListModel orderDetailsList = new DefaultListModel ();
    int selectedProductIndex = -1;
    int newProductIndex = -1;

    ActionListener ComboBoxAction = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
            if ( jComboBox2.getItemCount () == 0 ) {
                JOptionPane.showMessageDialog ( null , "<html><size='06'>First add Customer from Customer Master " );
            } else {
//                try {
//                    ResultSet result = DB_Operations.executeSingle ( "select SALES_RATE, (select PART_NAME from finished_goods where FG_ID in(M_FG_ID)) as 'product' from FG_CUSTOMER_MAP where M_CUST_ID =" + customers.get ( jComboBox2.getSelectedIndex () ) );
//                    if ( result != null ) {
//                        productRates = new ArrayList<String[]> ();
//                        while ( result.next () ) {
//                            productRates.add ( new String[] { result.getString ( "product" ) , result.getString ( "SALES_RATE" ) } );
//                        }
//                    }
//                    result.close ();
//                } catch ( SQLException ec ) {
//                    StaticValues.writer.writeExcel ( SalesForm.class.getSimpleName () , SalesForm.class.getSimpleName () , ec.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ec.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
//                }

             jTextField1.setText("");
            // jTextField2.setText("");
            jTextField4.setText("");
            jTextField5.setText("");
        
            totalOrderValue = 0.0 ;
            jLabel6.setText ( "0.0");
        
            //load_data_prodCust();
            //cust_prod_details();
             selected_cust_id =  Integer.parseInt(customerCombo.get ( jComboBox2.getSelectedIndex () )[0]);  
            try{
            if((ProductTypeList.size()) !=0)
            {
                ProductTypeList.clear();
                prod_cust.removeAll();
            }
            }catch(Exception ed){ed.getMessage();}
            load_data_prodCust();
            cust_prod_details();
             //prod_cust = new ProductForCustomer (selected_cust_id);
            prod_cust = new ProductforCustomer ();
            //prod_cust.load_data_prodCust(selected_cust_id);
            prod_cust.setBounds ( 280 , 50 , 315 , 260 );
            add( prod_cust );
            prod_cust.revalidate();
            prod_cust.repaint();
            
            }
        }
    };
    

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
//        jTextField1.setText("");
//        jTextField2.setText("");
//        jTextField4.setText("");
//        jTextField5.setText("");
//        
//        jComboBox1.setSelectedIndex ( 0);
//        jComboBox2.setSelectedIndex ( 0);
//        orderDetailsList.removeAllElements ();
//        while( orderDetails.size()>0){
//            orderDetails.remove (orderDetails.size()-1 ) ;
//        }
//        totalOrderValue = 0.0 ;
//        jLabel6.setText ( "0.0");
//        selectedProductIndex = -1 ;
        prod_cust.reset();
    }//GEN-LAST:event_jButton3ActionPerformed

    public String createFileNameResource ( String columnDocumentName ) {

        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
        String strDate2 = sdf2.format ( c2.getTime () );

        FileChannel inputChannel = null;
        FileChannel outputChannel = null;

        JFileChooser fileChooser = new JFileChooser ();
        fileChooser.setFileFilter ( new MyCustomFilter ( "pdf" ) );
        fileChooser.setCurrentDirectory ( new File ( System.getProperty ( "user.home" ) ) );
        int result = fileChooser.showOpenDialog ( this );
        if ( result == JFileChooser.APPROVE_OPTION ) {
            File selectedFile = fileChooser.getSelectedFile ();

            File dir = new File ( "UploadedFiles\\SalesOrders" );
            dir.mkdir ();

            purchaseOrderFile_name = columnDocumentName + "_(" + selectedFile.getName () + ") _saved_on" + strDate2 + selectedFile.getName ().substring ( selectedFile.getName ().lastIndexOf ( '.' ) );

            try {
                inputChannel = new FileInputStream ( selectedFile ).getChannel ();
                outputChannel = new FileOutputStream ( new File ( dir , purchaseOrderFile_name ) ).getChannel ();
                outputChannel.transferFrom ( inputChannel , 0 , inputChannel.size () );

                inputChannel.close ();
                outputChannel.close ();

            } catch ( FileNotFoundException e1 ) {
                StaticValues.writer.writeExcel (SalesForm_New.class.getSimpleName () , SalesForm_New.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
            } catch ( IOException e2 ) {
                StaticValues.writer.writeExcel (SalesForm_New.class.getSimpleName () , SalesForm_New.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
            }
        }
        return purchaseOrderFile_name;
    }

    public void openFile ( String filename ) {

        if (  ! filename.equals ( "" ) ) {
            try {
                String fileName = filename;

                File dir = new File ( "Uploaded Files\\Salers Orders" );

                File file = new File ( dir , fileName );

                Desktop desktop = Desktop.getDesktop ();
                if ( file.exists () ) {
                    desktop.open ( file );
                }
            } catch ( IOException ex ) {
                //    Logger.getLogger(FinishedGoodMaster.class.getName()).log(Level.SEVERE, null, ex);
                StaticValues.writer.writeExcel (SalesForm_New.class.getSimpleName () , SalesForm_New.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
            }
        }
    }
    
    public void load_data_prodCust()
    {
        prod_ids.clear();
        selected_cust_id =  Integer.parseInt(customerCombo.get ( jComboBox2.getSelectedIndex () )[0]);
        
         load_Cust_prod = "customermaps?CUSTOMER_ID="+selected_cust_id;
        result_for_cust_prod = WebAPITester.prepareWebCall ( load_Cust_prod , StaticValues.getHeader () , "" );
        
        if( !  result_for_cust_prod.contains( "not found"  )  ){
                    HashMap<String , Object> map = new HashMap<String , Object> ();
                    JSONObject jObject = new JSONObject ( result_for_cust_prod );
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
                        //jComboBox2.addItem ( emp.get ( "" ).toString() +" ( "+emp.get ("CUSTOMER_NAME" ).toString() +" )" );
                        //customerCombo.add ( new String[] { emp.get ( "CUSTOMER_ID" ).toString () , emp.get ( "PLANT_CODE" ).toString() +" ( "+emp.get ("CUSTOMER_NAME" ).toString() +" )" } );
                        prod_ids.add((emp.get("M_FG_ID")).toString());
                    }
            }
    }
    
    public void cust_prod_details()
    {
       if(products.size()!=0)
       {
            prod_cust.removeAll();
       }
        
        if(prod_ids.size()==0 )
        {
            if(products.size()==0)
            {
                System.out.print("Product data is already null");
                JOptionPane.showMessageDialog(null, "finish goods data not found");
            }
            else
            {
             products.clear();
             prod_cust.removeAll();
             //prod_cust=null;
            JOptionPane.showMessageDialog(null, "finish goods data not found");
            }
        }
        else
        {
            products.clear();
            for(int j=0;j<prod_ids.size();j++)
            {
            load_Cust_prod = "finishedgoodsedit?FG_ID="+prod_ids.get(j);
            result_for_cust_prod = WebAPITester.prepareWebCall ( load_Cust_prod , StaticValues.getHeader () , "" );
            try
               {
                    if( !  result_for_cust_prod.contains( "not found"  )  )
                    {
                       
                    JSONObject jsobje = new JSONObject(result_for_cust_prod);
                   String page11 = jsobje.getJSONArray("data").toString();
                    JSONArray machinejsarray = new JSONArray(page11);

                    ProductDR pro =new ProductDR();
                    for (int i = 0; i < machinejsarray.length(); i++) 
                    {

                     JSONObject jsonobject = machinejsarray.getJSONObject(i);
                     
                     
                     pro.setFG_ID(jsonobject.optInt("FG_ID"));
                     pro.setFG_PART_NO(jsonobject.optString("FG_PART_NO"));
                     pro.setUOM(jsonobject.optString("UOM"));
                    
                     products.add(pro);
                     
                    }
                     //prod_cust=null;
                     //prod_cust.removeAll();
                 }
               }catch(Exception e)
               {
                   e.getMessage();
               } 
            }
           //prod_cust=null;
           
//           prod_cust.removeAll();
           //prod_cust.revalidate();
           //prod_cust.repaint();
           
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables

    String purchaseOrderFile_name = "";
    
    ArrayList<Product_CustomerModel> selectedmaintain_type = new ArrayList<Product_CustomerModel>() ;
    
class ProductforCustomer extends javax.swing.JPanel {

        ResultSet result = null;

        //public ArrayList<ProductforCustPanel> ProductTypeList  = null ;
        private ArrayList<Product_CustomerModel> ProductCustDetailList  = null ;

        /**
         * Creates new form ProdDataConformationScreen
         */
        public ProductforCustomer () {
            initComponents ();

            ProductTypeList panel = new ProductTypeList ();
            panel.setBounds ( 0 , 0 , 315 , 260 );
            panel.setBackground ( Color.WHITE);
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
            setPreferredSize ( new java.awt.Dimension ( 260 , 260 ) );
            setLayout ( null );

            
        }// </editor-fold>                        

        public ArrayList<Product_CustomerModel> getMaintain_type ( ) {
            // TODO add your handling code here:
            Product_CustomerModel tlm = null;

            float totalmaintainCost  = 0 ;
            ProductCustDetailList = new ArrayList<Product_CustomerModel> () ;
            for ( int i = 0 ; i < ProductTypeList.size () ; i ++ ) {

                tlm = new Product_CustomerModel();
                if ( ProductTypeList.get ( i ).getTlQtyvalue()> 0 ) {
                    tlm.setProd_typeid(ProductTypeList.get ( i ).getProd_typeid());
                    tlm .setProd_nm(ProductTypeList.get ( i ).getProd_nm() );
                    tlm.setTlQtyvalue(ProductTypeList.get ( i ).getTlQtyvalue () );
                    tlm.setTotalQty(ProductTypeList.get ( i ).getTotalQty());
                    tlm.setProd_size(ProductTypeList.get(i).getProd_size());
                    
                    totalmaintainCost = totalmaintainCost + ProductTypeList.get ( i ).getTlQtyvalue();
                    
                    //lm.setReason ( rejectionList.get ( i ).getReasonStr () );
                   ProductCustDetailList.add ( tlm );
                }
            }
            
            
           // if(  totalmaintainCost == Integer.parseInt( tcm.getText().toString()) ){
                Product_CustomerModel tlm2 = null;
                StringBuilder sb = new StringBuilder ();
                for ( int i = 0 ; i < ProductCustDetailList.size () ; i ++ ) {
                    tlm2 = ProductCustDetailList.get ( i );

                    sb.append ( tlm2.getTlQtyvalue ());
                    sb.append ( tlm2.getProd_typeid());
                    sb.append ( tlm2.getProd_nm());
                    sb.append ( tlm2.getProd_size());
                    sb.append ( "\n" );
                }

                selectedmaintain_type= ProductCustDetailList;
                //setrejections (rejectionDetailList );
                
                System.out.println ( "filled maintenance activities size = "+ProductCustDetailList.size()+""); 
                
            //}else{
                
             //   JOptionPane.showMessageDialog (  null, "Total cost maintenance cannot be greater than "+ Integer.parseInt( tcm.getText() ) );
           // }
            
            return selectedmaintain_type ;
        }

        public void reset(){
            
             for ( int i = 0 ; i < ProductTypeList.size () ; i ++ ) {

                if ( ProductTypeList.get ( i ).getTlQtyvalue()> 0 ) {
                    ProductTypeList.get ( i ).setTlQtyvalue ( 0 ) ;
                }
            }
            
        }
        
        
        // Variables declaration - do not modify                     
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        // End of variables declaration                   

        class ProductTypeList extends JPanel {

            private JPanel panel;
            private JScrollPane scroll;
            private JButton btnAddType;

            public ProductTypeList () {

                setLayout ( new BorderLayout () );
                panel = new JPanel ();
                panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
                //panel.setLayout ( null );
                scroll = new JScrollPane ( panel ,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
                add ( scroll , BorderLayout.CENTER );
                setVisible ( true );
                ProductforCustPanel pane = null;

                //if( ProductTypeList == null  ){
                    
                    ProductTypeList = new ArrayList<ProductforCustPanel> ();
                    

//
//                        JSONObject emp = null;
                       // products = new ArrayList<ProductDR> ();
                        //panel.setBounds(0,0,170,500);
                        panel.setBounds(0,0,170,36*products.size());
                        
                        for ( int i = 0 ; i < products.size() ; i ++ ) {
                            //emp = records.getJSONObject ( i);
                            //products.add( new ProductDR ( Integer.parseInt(emp.get ( "FG_ID" ).toString ()), emp.get ( "FG_PART_NO" ).toString (),emp.get ( "UOM" ).toString ()    )); 
                            //ProductDR pro=new ProductDR();
                            pane = new ProductforCustPanel();
                            pane.setBounds(0,(i*40),315,36);
                            pane.setProd_typeid(products.get(i).FG_ID);
                            pane.setProd_nm( products.get(i).FG_PART_NO);
                            pane.setProd_size(products.get(i).UOM);
                            pane.setTlQtyvalue(0); 
                            pane.setTotalQty(0 );
                            pane.revalidate();
                            pane.repaint();
                            panel.add ( pane );
                            panel.revalidate ();
                            panel.repaint();
                            ProductTypeList.add ( pane );
                            panel.revalidate ();
                            panel.repaint();
                        }
                    //}
                }
            }
        }
    }



/*
 *
 * CREATE TABLE `sales_orders` ( `sales_po_id`	INTEGER NOT NULL PRIMARY KEY
 * AUTOINCREMENT UNIQUE, `so_customer_id`	INTEGER, `product_ids`	TEXT, `po_date`
 * TEXT, `po_copy_name`	TEXT, `order_qty`	INTEGER, `order_no`	TEXT );
 *
 *
 * CREATE TABLE `po_order_details` ( `oder_detail_id`	INTEGER NOT NULL PRIMARY
 * KEY AUTOINCREMENT UNIQUE, `product_id`	INTEGER, `product_qty`	INTEGER,
 * `ref_po_id`	INTEGER NOT NULL );
 *
 * CREATE TABLE `op_wip` ( `P_ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
 * UNIQUE, `PRODUCT`	INTEGER, `CUSTOMER`	INTEGER, `TARGETQTY`	INTEGER,
 * `FINISHED`	INTEGER, `MOVEQTY`	INTEGER, `MOVEDATE`	TEXT, `ORDERNO`	INTEGER,
 * `PROCESS_STAGE`	INTEGER );
 *
 */
