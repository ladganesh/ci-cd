/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.HintTextField;
import trixware.erp.prodezydesktop.Utilities.MyCustomFilter;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class SalesForm extends javax.swing.JPanel {

    boolean flag_required_master_data_NA = false;
   // public static ArrayList<String> products;
    public ArrayList<String[]> productRates;
    public static ArrayList<String> customers;
    public static ArrayList<String[]> product_ids_qtys;
    public int previousOA = 0;

    int selectedCustomer = 0;
    double totalOrderValue = 0.0;
    
    ArrayList<String[]> customerCombo = null;
    
    ArrayList<ProductDR> products = null;

    /**
     * Creates new form SalesForm
     */
    public SalesForm () {
        initComponents ();

        ResultSet result = DB_Operations.getMasters ( "customer" );

        jComboBox2.removeAllItems ();
        jComboBox1.removeAllItems ();

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
                        products.add ( new ProductDR ( Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) , emp.get ( "FG_PART_NO" ).toString () , emp.get ( "FG_UOM_ID" ).toString () ) );

                  //      System.out.println ( ""+Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) +"       "+emp.get ( "FG_PART_NO" ).toString () +"    "+ emp.get ( "FG_UOM_ID" ).toString () );
                    }

                    jLabel4.setText ( products.get ( jComboBox1.getSelectedIndex () ).UOM );
                //    jComboBox1.addActionListener ( productComboAction );
            }
        }
            



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

                   String addEmpAPICall = "customermaps";
        String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

        if ( result2 != null    &&  !result2.contains( "not found" )) {
             buildTableModelfromJSON2 ( result2 ) ;
        }
                   

//            try {
//                result = DB_Operations.executeSingle ( "select SALES_RATE, (select PART_NAME from finished_goods where FG_ID in(M_FG_ID)) as 'product' from FG_CUSTOMER_MAP where M_CUST_ID =" + customers.get ( 0 ) );
//                if ( result != null ) {
//                    productRates = new ArrayList<String[]> ();
//                    while ( result.next () ) {
//                        productRates.add ( new String[] { result.getString ( "product" ) , result.getString ( "SALES_RATE" ) } );
//                    }
//                }
//                result.close ();
//            } catch ( SQLException e ) {
//                StaticValues.writer.writeExcel ( SalesForm.class.getSimpleName () , SalesForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
//            }

                
                
                



//                result = DB_Operations.executeSingle ( "SELECT MAX(order_ack) from sales_orders" );
//                if ( result != null ) {                  
//                    try {
//                        previousOA = Integer.parseInt ( result.getString ( "MAX(order_ack)" ) );
//                    } catch ( NumberFormatException e ) {
//                        StaticValues.writer.writeExcel ( SalesForm.class.getSimpleName () , SalesForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
//                    }                 
//                    jTextField4.setText ( String.valueOf ( previousOA + 1 ) );                 
//                } else {
//
//                }

//      loadContent ();

//        } catch ( SQLException e ) {
//
//            System.out.println ( "   " + e.getMessage () );
//            StaticValues.writer.writeExcel ( SalesForm.class.getSimpleName () , SalesForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
//        } finally {
//
//            try {
//                result.close ();
//            } catch ( SQLException e ) {
//                StaticValues.writer.writeExcel ( SalesForm.class.getSimpleName () , SalesForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
//            }
//        }

        jComboBox2.addActionListener ( ComboBoxAction );
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
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
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
        jLabel12 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
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

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox1.setNextFocusableComponent(jTextField2);
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
        jComboBox1.setBounds(260, 30, 270, 40);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Customer");
        add(jLabel3);
        jLabel3.setBounds(20, 10, 130, 20);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox2.setNextFocusableComponent(jTextField1);
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });
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
        add(jComboBox2);
        jComboBox2.setBounds(20, 30, 230, 40);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Number");
        add(jLabel4);
        jLabel4.setBounds(150, 170, 100, 20);

        jTextField1.setNextFocusableComponent(jTextField4);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(20, 190, 230, 30);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Upload PO");
        jButton1.setNextFocusableComponent(jComboBox1);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(160, 320, 90, 40);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Order Details");
        add(jLabel5);
        jLabel5.setBounds(260, 170, 240, 20);

        jTextField2.setText("1");
        jTextField2.setNextFocusableComponent(jButton2);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        add(jTextField2);
        jTextField2.setBounds(260, 120, 160, 40);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("OA Number");
        add(jLabel9);
        jLabel9.setBounds(20, 230, 230, 20);

        jTextField4.setEditable(false);
        jTextField4.setNextFocusableComponent(jTextField5);
        add(jTextField4);
        jTextField4.setBounds(20, 250, 230, 30);

        dateChooserCombo1.setCalendarPreferredSize(new java.awt.Dimension(400, 250));
        dateChooserCombo1.setFormat(2);
        add(dateChooserCombo1);
        dateChooserCombo1.setBounds(20, 120, 230, 30);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setText("Order Date");
        add(jLabel10);
        jLabel10.setBounds(20, 100, 90, 20);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("Purchase Order");
        add(jLabel11);
        jLabel11.setBounds(20, 170, 210, 20);

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
        jScrollPane1.setBounds(550, 30, 380, 330);

        jTextField5.setNextFocusableComponent(jButton1);
        jTextField5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField5MouseClicked(evt);
            }
        });
        add(jTextField5);
        jTextField5.setBounds(20, 290, 230, 30);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("Order Qty ");
        add(jLabel12);
        jLabel12.setBounds(260, 100, 240, 20);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(440, 120, 90, 40);

        jList1.setAutoscrolls(false);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jList1);

        add(jScrollPane3);
        jScrollPane3.setBounds(260, 190, 270, 170);

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

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

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
        while ( i < orderDetails.size () ) 
        {
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
                        e.getMessage();
                    }
                }

                }catch(UnsupportedEncodingException e){

                }
        }else{
            
            JOptionPane.showMessageDialog ( null , "Please ensure valid PO no is provided and products are selected");
            
        }
        
        //int result = DB_Operations.insertIntoSalesOrderAndOrderDetails ( Integer.parseInt ( customer_id ) , po_date , po_copy_name , order_no , product_ids_qtys , previousOA , totalOrderValue + "" );
        loadContent ();
        
        
        jButton2.setEnabled ( false );

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
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        //    jList1.removeAll();
        String orderQty = jTextField2.getText ();
        String selectedProduct = products.get( jComboBox1.getSelectedIndex () ).FG_ID +"" ;
        selectedCustomer = Integer.parseInt( customerCombo.get( jComboBox2.getSelectedIndex ())[0] );

        double rate  = 0.0 ;

        
        for ( int i = 0 ; i < productRates.size () ; i ++ ) {

                if ( selectedProduct.equals ( productRates.get ( i )[ 0 ] ) && selectedCustomer == Integer.parseInt( productRates.get ( i )[1 ]  ) ) {
                    //totalOrderValue = totalOrderValue + ( Double.parseDouble ( productRates.get ( i )[ 2 ] ) * Double.parseDouble ( temp.qty ) );
                    rate = Double.parseDouble ( productRates.get ( i )[ 2 ] );
                }
        }
        
        JOptionPane.showMessageDialog( null, ""+selectedProductIndex );
        
        if( rate != 0.0 && !orderQty.equals ( "")){
        
                System.out.println ( "Selected product index "+ selectedProductIndex);

                if ( selectedProductIndex >=0  ) {

                    String tempQty = orderDetails.get ( selectedProductIndex ).qty;
                    orderDetails.remove ( selectedProductIndex );
                    orderDetailsList.removeElementAt ( selectedProductIndex );

                    OrderDetails temp = new OrderDetails ();
                    temp.O_ID =   selectedProduct ;
                    temp.productName = products.get( jComboBox1.getSelectedIndex () ).FG_PART_NO;
                    temp.qty = String.valueOf ( Integer.parseInt ( orderQty ) + Integer.parseInt ( tempQty ) );
                    temp.rate = rate ;
                    totalOrderValue =   ( rate * Double.parseDouble ( temp.qty ) );

                    jLabel6.setText ( totalOrderValue + "" );

                    orderDetails.add ( temp );
                    orderDetailsList.addElement ( temp.productName + "  Qty :  " + temp.qty );
                
                } else {


                    OrderDetails temp = new OrderDetails ();
                
                    temp.O_ID =   selectedProduct ;
                    temp.productName = products.get( jComboBox1.getSelectedIndex () ).FG_PART_NO;
                    temp.qty = orderQty;
                    
                    for ( int i = 0 ; i < productRates.size () ; i ++ ) {

                        if ( selectedProduct.equals ( productRates.get ( i )[ 0 ] ) && selectedCustomer == Integer.parseInt( productRates.get ( i )[1 ]  ) ) {
                            totalOrderValue = totalOrderValue + ( rate * Double.parseDouble ( temp.qty ) );
                            temp.rate = rate;
                        }
                    }
                    orderDetails.add ( temp );
                    //orderDetailsList.addElement( selectedProduct + " - Qty" + orderQty ); 
                    orderDetailsList.addElement ( temp.productName + "  Qty :  " + temp.qty );

                    
                    
                    jLabel6.setText ( totalOrderValue + "" );
                
                }

                jList1.setModel ( orderDetailsList );

               if ( orderDetails.size () >= 1) {
                    int _selectedProduct =  products.get(  jComboBox1.getSelectedIndex() ).FG_ID;
                    for ( int count = 0 ; count < orderDetails.size () ; count ++ ) {
                        if ( _selectedProduct == Integer.parseInt( orderDetails.get ( count ).O_ID ) )  {
                            selectedProductIndex = count;
                        }
                    }
                }
        }else{
        
            if( rate == 0.0  )
                JOptionPane.showMessageDialog(  null, "<html>Selling rate for selected product and customer is not defined<br>Please create an entry in product customer map."    );
        
            if( orderQty.equals ( "") )
                JOptionPane.showMessageDialog(  null, "<html>Please enter valid order quantity</html>"    );
            
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    int selectedProductIndex = -1;
    int newProductIndex = -1;

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        jButton2.setEnabled ( true );
        if (  orderDetails.size () > 0) {
            int _selectedProduct =  products.get(  jComboBox1.getSelectedIndex() ).FG_ID;
            for ( int count = 0 ; count < orderDetails.size () ; count ++ ) {
                if ( _selectedProduct ==  Integer.parseInt( orderDetails.get ( count ).O_ID ) )  {
                    selectedProductIndex = count;
                }else{
                    selectedProductIndex = -1 ;
                }
            }
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:

        int choice =    JOptionPane.showConfirmDialog ( null, "Are you sure you want to remove the item from list ?", "Confirm Remove Action", JOptionPane.YES_NO_OPTION);
        
        // selectedCustomer = Integer.parseInt( customerCombo.get( jComboBox2.getSelectedIndex ())[0] );
        
        if( choice ==JOptionPane.YES_OPTION ){
        
            if (  ! ( orderDetailsList.getSize () < 1 ) ) {

                int selection = jList1.getSelectedIndex ();

                for ( int i = 0 ; i < productRates.size () ; i ++ ) {

                    if ( orderDetails.get(selection).O_ID.equals ( productRates.get ( i )[ 0 ] ) && selectedCustomer == Integer.parseInt( productRates.get ( i )[1 ] ) ){
                        totalOrderValue = totalOrderValue - ( Double.parseDouble ( productRates.get ( i )[ 2 ] ) * Double.parseDouble ( orderDetails.get(selection).qty ) );
                        i=productRates.size () ;
                    }
                }

                jLabel6.setText ( totalOrderValue + "" );
                orderDetails.remove ( selection );
                orderDetailsList.removeElementAt ( selection );
                jList1.setModel ( orderDetailsList );

            }
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox2MouseClicked

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        if ( jComboBox1.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add product from Finished Good Master " );
        }             // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:

         jTextField1.setText("");
        jTextField2.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        
        jComboBox1.setSelectedIndex ( 0);
        
        orderDetailsList.removeAllElements ();
        while( orderDetails.size()>0){
            orderDetails.remove (orderDetails.size()-1 ) ;
        }
        totalOrderValue = 0.0 ;
        jLabel6.setText ( "0.0");
        selectedProductIndex = -1 ;
    }//GEN-LAST:event_jComboBox2ActionPerformed

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

                


            }
        }
    };

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        
        jComboBox1.setSelectedIndex ( 0);
        jComboBox2.setSelectedIndex ( 0);
        orderDetailsList.removeAllElements ();
        while( orderDetails.size()>0){
            orderDetails.remove (orderDetails.size()-1 ) ;
        }
        totalOrderValue = 0.0 ;
        jLabel6.setText ( "0.0");
        selectedProductIndex = -1 ;
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
                StaticValues.writer.writeExcel ( SalesForm.class.getSimpleName () , SalesForm.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
            } catch ( IOException e2 ) {
                StaticValues.writer.writeExcel ( SalesForm.class.getSimpleName () , SalesForm.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
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
                StaticValues.writer.writeExcel ( SalesForm.class.getSimpleName () , SalesForm.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables

    String purchaseOrderFile_name = "";

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
