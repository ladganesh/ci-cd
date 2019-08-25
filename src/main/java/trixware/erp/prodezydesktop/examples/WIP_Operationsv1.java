/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JOptionPane;
import java.lang.Object;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import trixware.erp.prodezydesktop.Derived.AutoCompletion;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.HintTextField;

/**
 *
 * @author Rajesh
 */
public class WIP_Operationsv1 extends javax.swing.JPanel {

    ArrayList<String> orders;
    ArrayList<String> customers;
    ArrayList<String[]> products;
    ArrayList<String[]> processes ;

    Connection con;

    String selectectedOrder, selectectedCustomer;

    /**
     * Creates new form WIP_Operations
     */
    public WIP_Operationsv1 () {
        initComponents ();
        AutoCompletion.enable ( jComboBox1 );
        AutoCompletion.enable ( jComboBox2 );
        AutoCompletion.enable ( jComboBox3 );
        AutoCompletion.enable ( jComboBox4 );

        ResultSet result = null;//DB_Operations.getTransactions("sales_orders");

        try {

//            if (result != null) {
//                salesdata = new ArrayList<String[]>();
//                jComboBox4.removeAllItems();
//                while (result.next()) {
//
//                    salesdata.add(new String[]{result.getString("customer_id"),
//                        result.getString("product_id"),
//                        result.getString("product_qty"),
//                        result.getString("order_no")});
//                }
//                
//            } else {
//
//                jComboBox4.addItem("Not Available");
//                flag_required_master_data_NA = true;
//                show_data_NA_message("sales orders");
//            }
            result = DB_Operations.getTransactions ( "orders" );

            if ( result != null ) {
                jComboBox4.removeAllItems ();
                orders = new ArrayList<String> ();
                while ( result.next () ) {

                    jComboBox4.addItem ( result.getString ( "order_no" ) );
                    orders.add ( result.getString ( "sales_po_id" ) );

                }

            } else {

                jComboBox4.addItem ( "Not Available" );
                flag_required_master_data_NA = true;
                show_data_NA_message ( "sales orders" );
            }

            ResultSet result2 = DB_Operations.executeSingle ( "SELECT DISTINCT(CUSTOMER_ID), CUSTOMER_NAME FROM customer a INNER JOIN sales_orders b where  a.CUSTOMER_ID = b.so_customer_id" );

            if ( result != null ) {
                jComboBox2.removeAllItems ();
                customers = new ArrayList<String> ();
                while ( result2.next () ) {

                    jComboBox2.addItem ( result2.getString ( "CUSTOMER_NAME" ) );
                    customers.add ( result2.getString ( "CUSTOMER_ID" ) );
                }

            } else {

                jComboBox4.addItem ( "Not Available" );
                flag_required_master_data_NA = true;
                show_data_NA_message ( "sales orders" );

            }
           
            selectectedOrder = orders.get ( 0 );
            selectectedCustomer = customers.get ( 0 );

            loadProductsAndQtys ( selectectedOrder , selectectedCustomer );

        } catch ( SQLException e ) {
            System.out.println ( e.getMessage () );
        } catch ( NullPointerException e ) {
            System.out.println ( e.getMessage () );
        } finally {

            try {
                result.close ();

            } catch ( SQLException e ) {

            }
        }

//        String selectedProduct = jComboBox1.getSelectedItem().toString();
        //      String selectedCustomer = jComboBox2.getSelectedItem().toString();
        //     System.out.println(" product " + selectedProduct + '\n' + "customer  " + selectedCustomer);
        //     loadOrders(selectedProduct, selectedCustomer);
    }

    public void loadProductsAndQtys ( String selctOrder , String selectCustomer ) {
        try {

            jComboBox1.removeAllItems ();
            jTextField1.setText ( "" );

            ResultSet result = DB_Operations.executeSingle ( "SELECT DISTINCT(product_id), product_qty FROM po_order_details a INNER JOIN sales_orders b where b.so_customer_id = " + selectCustomer + " AND a.ref_po_id=" + selctOrder );

            if ( result != null ) {
                products = new ArrayList<String[]> ();
                while ( result.next () ) {

                    products.add ( new String[] { result.getString ( "product_id" ) , result.getString ( "product_qty" ) } );
               //     System.out.println ( result.getString ( "product_id" ) + '\n' + result.getString ( "product_qty" ) + '\n' );
                }

            } else {

                //   jComboBox1.addItem("Not Available");
                flag_required_master_data_NA = true;
                show_data_NA_message ( "sales orders" );
            }

            String productList = "";

            for ( int i = 0 ; i < products.size () ; i ++ ) {
                if ( i < products.size () - 1 ) {
                    productList = productList + products.get ( i )[ 0 ] + ",";
                } else {
                    productList = productList + products.get ( i )[ 0 ];
                }
            }

            //  System.out.println( productList );
            result = DB_Operations.executeSingle ( "SELECT PART_NAME FROM finished_goods where FG_ID in( " + productList + " )" );

            if ( result != null ) {

                while ( result.next () ) {

                    jComboBox1.addItem ( result.getString ( "PART_NAME" ) );

                }
                jTextField1.setText ( products.get ( jComboBox1.getSelectedIndex () )[ 1 ] );
            } else {

                jComboBox1.addItem ( "Not Available" );
                flag_required_master_data_NA = true;
                show_data_NA_message ( "sales orders" );
            }
        } catch ( SQLException e1 ) {
            System.out.println ( e1.getMessage () );
        } catch ( ArrayIndexOutOfBoundsException e2 ) {
            System.out.println ( e2.getMessage () );
        }

    }

    //ArrayList<String> processes = new ArrayList<String> ( Arrays.asList ( "Process1" , "Process2" , "Process3" , "Process4" , "Process5" , "Process6" , "Process7" , "Process8" , "Process9" , "Process10" , "Process11" ) );

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel23 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new HintTextField( "Move" );
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();

        setLayout(null);

        jLabel23.setText("Nos");
        add(jLabel23);
        jLabel23.setBounds(690, 180, 40, 16);

        jTable1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Process1-Qty", "Process2-Qty", "Process3-Qty", "Process4-Qty", "Process5-Qty", "Process6-Qty", "Process7-Qty", "Process8-Qty", "Process9-Qty", "Process10-Qty", "Process11-Qty", "Finished"
            }
        ));
        jTable1.setRowHeight(60);
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(26, 369, 1230, 170);

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        add(jComboBox1);
        jComboBox1.setBounds(310, 50, 180, 40);

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        add(jComboBox2);
        jComboBox2.setBounds(80, 110, 180, 40);

        jTextField1.setEditable(false);
        jTextField1.setToolTipText("Total Qty");
        add(jTextField1);
        jTextField1.setBounds(310, 110, 180, 40);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Move to process");
        add(jLabel1);
        jLabel1.setBounds(550, 30, 180, 16);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Process1", "Process2", "Process3", "Process4", "Process5", "Process6", "Process7", "Process8", "Process9", "Process10", "Process11" }));
        jComboBox3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox3MouseClicked(evt);
            }
        });
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        add(jComboBox3);
        jComboBox3.setBounds(550, 50, 180, 40);

        jLabel2.setText("Nos");
        add(jLabel2);
        jLabel2.setBounds(690, 120, 40, 20);

        jTextField2.setToolTipText("Move");
        add(jTextField2);
        jTextField2.setBounds(550, 110, 180, 40);

        jButton1.setText("Move");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(750, 170, 150, 40);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Order QTY");
        add(jLabel3);
        jLabel3.setBounds(310, 90, 70, 20);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Order No");
        add(jLabel4);
        jLabel4.setBounds(80, 30, 70, 20);

        jSlider1.setMaximum(11);
        jSlider1.setMinimum(1);
        jSlider1.setMinorTickSpacing(1);
        jSlider1.setValue(2);
        jSlider1.setEnabled(false);
        add(jSlider1);
        jSlider1.setBounds(20, 250, 1240, 20);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jLabel5.setText("Process1");
        add(jLabel5);
        jLabel5.setBounds(20, 270, 50, 20);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("Product");
        add(jLabel6);
        jLabel6.setBounds(310, 30, 80, 20);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("Customer");
        add(jLabel7);
        jLabel7.setBounds(80, 90, 80, 16);

        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        add(jComboBox4);
        jComboBox4.setBounds(80, 50, 180, 40);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("Product Proecss Path");
        add(jLabel8);
        jLabel8.setBounds(30, 230, 160, 20);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jLabel9.setText("Process2");
        add(jLabel9);
        jLabel9.setBounds(130, 270, 50, 20);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jLabel10.setText("Process3");
        add(jLabel10);
        jLabel10.setBounds(250, 270, 50, 20);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jLabel11.setText("Process4");
        add(jLabel11);
        jLabel11.setBounds(380, 270, 50, 20);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jLabel12.setText("Process5");
        add(jLabel12);
        jLabel12.setBounds(500, 270, 50, 20);

        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jLabel13.setText("Process6");
        add(jLabel13);
        jLabel13.setBounds(620, 270, 50, 20);

        jLabel14.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jLabel14.setText("Process7");
        add(jLabel14);
        jLabel14.setBounds(740, 270, 50, 20);

        jLabel15.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jLabel15.setText("Process8");
        add(jLabel15);
        jLabel15.setBounds(870, 270, 50, 20);

        jLabel16.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jLabel16.setText("Process9");
        add(jLabel16);
        jLabel16.setBounds(990, 270, 50, 20);

        jLabel17.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jLabel17.setText("Process10");
        add(jLabel17);
        jLabel17.setBounds(1110, 270, 50, 20);

        jLabel18.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jLabel18.setText("Process11");
        add(jLabel18);
        jLabel18.setBounds(1220, 270, 50, 20);

        jButton2.setText("Load Orders");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(80, 170, 180, 40);

        jButton3.setText("Load work in progress");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(310, 170, 180, 40);

        jLabel19.setText("Processed Qty");
        add(jLabel19);
        jLabel19.setBounds(550, 90, 180, 16);

        jLabel20.setText("Rejected Qty");
        add(jLabel20);
        jLabel20.setBounds(550, 150, 90, 16);
        add(jTextField3);
        jTextField3.setBounds(550, 170, 180, 40);

        jLabel21.setText("Nos");
        add(jLabel21);
        jLabel21.setBounds(690, 120, 40, 20);

        jLabel22.setText("Nos");
        add(jLabel22);
        jLabel22.setBounds(690, 180, 40, 20);
    }// </editor-fold>//GEN-END:initComponents

    static Calendar c2;
    static SimpleDateFormat sdf2;

    boolean flag_required_master_data_NA = false;
    String selectedProcess;
    String updateWIP_ID;
    String OP_TYPE = "insert";
    String updateQty="0";
    ArrayList<String[]> oldProcesses;
    
    String previousProcess;
    int previousQty;
    int previousProcId =0;
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        c2 = Calendar.getInstance ();
        sdf2 = new SimpleDateFormat ( "dd-MM-yyyy-HH-mm-ss" );
        String strDate2 = sdf2.format ( c2.getTime () );

        String PRODUCT = jComboBox1.getSelectedItem ().toString ();
        String CUSTOMER = jComboBox2.getSelectedItem ().toString ();
        int TARGETQTY = Integer.parseInt ( jTextField1.getText () );
        String P = jComboBox3.getSelectedItem ().toString ();
        selectedProcess = jComboBox3.getSelectedItem ().toString ();
        String MOVEDATE = strDate2;
        String ORDERNO = jComboBox4.getSelectedItem ().toString ();
        int MOVEQTY = Integer.parseInt ( jTextField2.getText () );

        String verificationQuery = "SELECT P_ID,  MOVEQTY, PROCESS_STAGE, TARGETQTY FROM op_wip WHERE CUSTOMER = '" + customers.get ( jComboBox2.getSelectedIndex () ) + "' AND PRODUCT = '" + products.get ( jComboBox1.getSelectedIndex () )[ 0 ] + "'  AND ORDERNO = '" + orders.get ( jComboBox4.getSelectedIndex () ) + "' ORDER BY PROCESS_STAGE ";
        ResultSet pastProcesses = null;
        int lastMoveQty = 0;

        try {

            pastProcesses = DB_Operations.executeSingle ( verificationQuery );

            oldProcesses = new ArrayList<String[]>();
            int count = 0;
            int stage = 0;
            while ( pastProcesses.next () ) {

                if ( selectedProcess.equals ( pastProcesses.getString ( "PROCESS_STAGE" ) ) ) {
                    
                    if ( ( MOVEQTY + Integer.parseInt ( pastProcesses.getString ( "MOVEQTY" ) ) ) > Integer.parseInt ( pastProcesses.getString ( "TARGETQTY" ) ) ) {

                        JOptionPane.showMessageDialog ( null , "Qunatity to move cannot exceed the target qty !" );
                    } else {
                        stage = count=1;
                        OP_TYPE = "update";
                        updateQty = String.valueOf ( MOVEQTY + Integer.parseInt ( pastProcesses.getString ( "MOVEQTY" ) ) );
                        lastMoveQty = Integer.parseInt(pastProcesses.getString ( "MOVEQTY" ) );
                        updateWIP_ID = pastProcesses.getString ( "P_ID" );
                    }
                    
                
                    
                    
                }else{
                        count ++;
                }
//                if ( lastMoveQty == 0 || lastMoveQty > Integer.parseInt ( pastProcesses.getString ( "MOVEQTY" ) ) ) {
//                    lastMoveQty = Integer.parseInt ( pastProcesses.getString ( "MOVEQTY" ) );
//                }
                  oldProcesses.add( new String[]{ pastProcesses.getString ( "P_ID" ), pastProcesses.getString ( "MOVEQTY" ) } );  
                  
            }

            //if( oldProcesses.size ()>1  ){
//                previousProcId = Integer.parseInt (oldProcesses.get( stage )[0] );
//                previousQty     = Integer.parseInt (oldProcesses.get( stage   )[1] );
//            }else if( oldProcesses.size () ==1  ){
//                previousProcId = Integer.parseInt (oldProcesses.get( oldProcesses.indexOf ( new String[]{ updateWIP_ID, String.valueOf(lastMoveQty) }  ) )[0] );
//                previousQty = Integer.parseInt (oldProcesses.get( oldProcesses.indexOf ( new String[]{ updateWIP_ID, String.valueOf( lastMoveQty) }  )  )[1] );
//                
//            }
                
        //    System.out.println ( previousProcId +",  "+ previousQty );

        } catch ( SQLException e ) {

        } finally {

            try {
                pastProcesses.close ();
            } catch ( SQLException e ) {

            }
        }

       try{
                if ( lastMoveQty != 0 && lastMoveQty < MOVEQTY ) {
                    JOptionPane.showMessageDialog ( null , "Cannot move qty more than previous stage" );

                } else if ( MOVEQTY > TARGETQTY ) {
                    JOptionPane.showMessageDialog ( null , " Cannot move qty more than target qty " );

                } else {

                    if ( OP_TYPE.equals ( "insert" ) ) {

                         if( processes.indexOf ( selectedProcess) >0){

                            String test1 = "insert into op_wip ( PRODUCT,CUSTOMER,TARGETQTY,PROCESS_STAGE,MOVEDATE,ORDERNO, MOVEQTY ) values ( '" + products.get ( jComboBox1.getSelectedIndex () )[ 0 ] + "','" + customers.get ( jComboBox2.getSelectedIndex () ) + "'," + TARGETQTY + ",'" + P + "','" + strDate2 + "','" + orders.get ( jComboBox4.getSelectedIndex () ) + "'," + MOVEQTY + " )";
                            DB_Operations.executeInsertRandom ( test1 );
                        
                            int x = lastMoveQty - Integer.parseInt ( updateQty );
                            int y = Integer.parseInt ( updateWIP_ID ) - 1;
    
                            String test2 = "update op_wip set  MOVEDATE='" + strDate2 + "',  MOVEQTY = " + x + "  where P_ID = " + y;
                            DB_Operations.executeInsertRandom ( test2 );

                        }

                        JOptionPane.showMessageDialog ( null , "Work in process updated !" );
                        loadOrders ( products.get ( jComboBox1.getSelectedIndex () )[ 0 ] , customers.get ( jComboBox2.getSelectedIndex () ) );
                        
                    } else if ( OP_TYPE.equals ( "update" ) ) {

                        String test = "update op_wip set  MOVEDATE='" + strDate2 + "',  MOVEQTY = '" + updateQty + "'  where P_ID = " + updateWIP_ID;
                        DB_Operations.executeInsertRandom ( test );
    //                    System.out.println ( updateWIP_ID +",  "+ updateQty +"  "+previousQty  + "  "  +previousProcId);
                        
    //                    String test2 = "update op_wip set  MOVEDATE = '" + strDate2 + "',  MOVEQTY = '" +previousQty  + "'  where P_ID = " +previousProcId;
                        
    //                    DB_Operations.executeInsertRandom ( test2 );
                                                
                        JOptionPane.showMessageDialog ( null , "Work in process updated !" );
                        loadOrders ( products.get ( jComboBox1.getSelectedIndex () )[ 0 ] , customers.get ( jComboBox2.getSelectedIndex () ) );
                    }

                }
       }catch(Exception nef){
           System.out.println( "Number Format Wrong " +nef.getMessage () );
       }

       

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:

//        try{
//                 selectectedOrder        = orders.get(  jComboBox4.getSelectedIndex()  );
//                 selectectedCustomer   = customers.get( jComboBox2.getSelectedIndex() );
//                 
//                // System.out.println( "Selected customer and order id "+selectectedCustomer +" " +selectectedOrder );
//                 
//                 loadProductsAndQtys(selectectedOrder, selectectedCustomer);
//                 
//        }catch(Exception e){
//            System.out.println( e.getMessage() );
//        }
    }//GEN-LAST:event_jComboBox4ActionPerformed


    private void jComboBox3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox3MouseClicked
        // TODO add your handling code here:
        for ( int i = 0 ; i < processes.size () ; i ++ ) {
            {
                if ( processes.get ( i ).equals ( jComboBox3.getSelectedItem ().toString () ) ) {
                    jSlider1.setValue ( i );
                    break;
                }
            }
        }
    }//GEN-LAST:event_jComboBox3MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
//        try {
//           
//
        jTextField1.setText ( products.get ( jComboBox1.getSelectedIndex () )[ 1 ] );
//            System.out.println( products.get( jComboBox1.getSelectedIndex() )[0]  +", "+  selectectedCustomer  );
//            loadOrders(   products.get( jComboBox1.getSelectedIndex() )[0]  ,  customers.get( jComboBox2.getSelectedIndex() ) );
//            
//        } catch (NullPointerException e) {
//                System.out.println( e.getMessage() );
//        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:

//        try {
//            String selectedProduct = jComboBox1.getSelectedItem().toString();
//            String selectedCustomer = jComboBox2.getSelectedItem().toString();
//
//            loadOrders( products.get( jComboBox1.getSelectedIndex() )[0]  ,  customers.get( jComboBox2.getSelectedIndex() ) );
//           
//           // loadOrders( "3" ,  "1" );
//           
//        } catch (NullPointerException e) {
//
//        }

    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        try {
            selectectedOrder = orders.get ( jComboBox4.getSelectedIndex () );
            selectectedCustomer = customers.get ( jComboBox2.getSelectedIndex () );

            // System.out.println( "Selected customer and order id "+selectectedCustomer +" " +selectectedOrder );
            loadProductsAndQtys ( selectectedOrder , selectectedCustomer );

        } catch ( Exception e ) {
            System.out.println ( e.getMessage () );
        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:

        try {
            
            jTextField1.setText ( products.get ( jComboBox1.getSelectedIndex () )[ 1 ] );
         //   System.out.println ( products.get ( jComboBox1.getSelectedIndex () )[ 0 ] + ", " + selectectedCustomer );
            loadOrders ( products.get ( jComboBox1.getSelectedIndex () )[ 0 ] , customers.get ( jComboBox2.getSelectedIndex () ) );

        } catch ( NullPointerException e ) {
            System.out.println ( e.getMessage () );
        }

    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    public void loadOrders ( String selectedProduct , String selectedCustomer ) {

        ResultSet orderDetails = null ;
                
        try {
            
             int selectedFG_ID = Integer.parseInt(selectedProduct );
           ResultSet result2 = DB_Operations.executeSingle ( "SELECT REF_PROCESS_ID, PROCESS_NAME FROM FG_PROCESS_MACH_MAP a INNER JOIN PROCESS_MASTER b on a.REF_PROCESS_ID = b.PROCESS_ID AND REF_FG_ID = "+selectedFG_ID );

            if ( result2 != null ) {
                jComboBox3.removeAllItems ();
                processes = new ArrayList<String[]> ();
                while ( result2.next () ) {

                    jComboBox3.addItem ( result2.getString ( "PROCESS_NAME" ) );
                    processes.add ( new String[]{ String.valueOf(result2.getInt ( "REF_PROCESS_ID" )), result2.getString ( "PROCESS_NAME" )} );
                        
                }

            } else {

                jComboBox4.addItem ( "Not Available" );
                flag_required_master_data_NA = true;
                show_data_NA_message ( "sales orders" );

            }
            
            //jTable1.setModel(buildTableModel2(null));

            String loadOrderDetailsIfAvail = "SELECT DISTINCT ORDERNO, TARGETQTY, PROCESS_STAGE FROM op_wip WHERE CUSTOMER = '" + selectedCustomer + "' AND PRODUCT = '" + selectedProduct + "'";

            orderDetails = DB_Operations.executeSingle ( loadOrderDetailsIfAvail );

            orderNos = new ArrayList<String> ();
            orderValues = new ArrayList<String> ();
            past_processes = new ArrayList<String> ();

            if ( orderDetails != null ) {
                while ( orderDetails.next () ) {
                    orderNos.add ( orderDetails.getString ( "ORDERNO" ) );
                    orderValues.add ( orderDetails.getString ( "TARGETQTY" ) );
                    past_processes.add ( orderDetails.getString ( "PROCESS_STAGE" ) );
                    
                    
                }
            }

            orderDetails.close ();
            
            String lastProcess = "";
            if ( past_processes.size () > 1 && past_processes.size () < processes.size () ) {
                lastProcess = past_processes.get ( past_processes.size () - 1 );
            } else if ( past_processes.size () == 1 ) {
                lastProcess = past_processes.get ( 0 );
            } else if ( past_processes.size () == processes.size () ) {
                lastProcess = past_processes.get ( past_processes.size () - 1 );
            }

            int selectedIndex = processes.indexOf ( lastProcess );

//            jComboBox3.removeAllItems();
//            for (int i = selectedIndex + 1; i < processes.size(); i++) {
//                {
//                    jComboBox3.addItem(processes.get(i));
//                }
//            }
            jSlider1.setValue ( selectedIndex + 1 );
            //jLabel5.setText( lastProcess ) ;

            jLabel5.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
            jLabel9.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
            jLabel10.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
            jLabel11.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
            jLabel12.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
            jLabel13.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
            jLabel14.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
            jLabel15.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
            jLabel16.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
            jLabel17.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
            jLabel18.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );

            switch ( selectedIndex + 1 ) {

                case 1:
                    jLabel5.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
                    break;
                case 2:
                    jLabel9.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
                    break;
                case 3:
                    jLabel10.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
                    break;
                case 4:
                    jLabel11.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
                    break;
                case 5:
                    jLabel12.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
                    break;
                case 6:
                    jLabel13.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
                    break;
                case 7:
                    jLabel14.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
                    break;
                case 8:
                    jLabel15.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
                    break;
                case 9:
                    jLabel16.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
                    break;
                case 10:
                    jLabel17.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
                    break;
                case 11:
                    jLabel18.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
                    break;

            }

//            int selectedOrder = jComboBox4.getSelectedIndex();
//
//            if (selectedOrder != -1) {
//
//                jTextField1.setText(String.valueOf(orderValues.get(selectedOrder)));
//            }
            jTextField1.setText ( products.get ( jComboBox1.getSelectedIndex () )[ 1 ] );

            loadOrderDetailsIfAvail = "SELECT  MOVEDATE, MOVEQTY, PROCESS_STAGE    FROM op_wip WHERE CUSTOMER = '" + selectedCustomer + "' AND PRODUCT ='" + selectedProduct + "'";
            //orderDetails = pst.executeQuery(loadOrderDetailsIfAvail);
            orderDetails = DB_Operations.executeSingle ( loadOrderDetailsIfAvail );

            ResultSet rs2 = DB_Operations.executeSingle ( "SELECT  MIN(MOVEQTY), TARGETQTY  FROM op_wip WHERE CUSTOMER = '" + selectedCustomer + "' AND PRODUCT ='" + selectedProduct + "'" );

            jTable1.setModel ( buildTableModel2 ( orderDetails , rs2 ) );

            /*
             * for (int i = 0; i < processes.size(); i++) { if
             * (processes.get(i).equals(jComboBox3.getSelectedItem().toString()))
             * { jSlider1.setValue(i); break; } }
             */
        } catch ( SQLException e ) {
            System.out.println ( " Error :  " + e.getMessage () );
        } catch ( IndexOutOfBoundsException e ) {
            System.out.println ( " Error :  " + e.getMessage () );
        }
    }

    public DefaultTableModel buildTableModel2 ( ResultSet rs , ResultSet rs2 )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        int columnCount = metaData.getColumnCount ();

        columnNames.add ( "<html>Balance to<br>process<html>" );
        for ( int column = 1 ; column < 3 ; column ++ ) {

            if ( column == 1 ) {
                for ( int i = 0 ; i < processes.size () ; i ++ ) {
                    columnNames.add ( processes.get ( i )[1] );
                }
            }
            //columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<String>> data = new Vector<Vector<String>> ();
        Vector<String> vector = new Vector<String> ();

        try {
            vector.add ( "<html> <b>" + String.valueOf ( Integer.parseInt ( rs2.getString ( "TARGETQTY" ) ) - Integer.parseInt ( rs2.getString ( "MIN(MOVEQTY)" ) ) ) + "</b>  </html>" );
        } catch ( NumberFormatException nef ) {

        }


//        int totalMoveQty = 0;
//        int k = 0;
//        while ( rs.next () ) {
//            //  if(  rs.getObject(3).equals(processes.get(k))  ){
//            for ( int i = k ; i < processes.size () ; i ++ ) {
//                if ( rs.getObject ( "PROCESS_STAGE" ).equals ( processes.get ( i )[1] ) ) {
//                    vector.add ( "<html> <b>" + rs.getString ( "MOVEQTY" ) + "</b>  <br>" + rs.getString ( "MOVEDATE" ) + "</html>" );
//                   i = processes.size ();
//                } else {
//                    vector.add ( "--" );
//                }
//                k ++;
//            }
//
//            totalMoveQty = totalMoveQty + Integer.parseInt ( rs.getString ( 2 ) );
//        }

        int totalMoveQty = 0;
        int k = 0;
        while ( rs.next () ) {
             
                    vector.add ( "<html> <b>" + rs.getString ( "MOVEQTY" ) + "</b>  <br>" + rs.getString ( "MOVEDATE" ) + "</html>" );
              

            totalMoveQty = totalMoveQty + Integer.parseInt ( rs.getString ( 2 ) );
        }


        data.add ( vector );

        rs.close ();
        rs2.close ();
        return new DefaultTableModel ( data , columnNames );

    }

    public DefaultTableModel buildTableModel ( ResultSet rs )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        int columnCount = metaData.getColumnCount ();
        for ( int column = 3 ; column <= 5 ; column ++ ) {

            if ( column == 3 ) {
                for ( int i = 0 ; i < processes.size () ; i ++ ) {
                    columnNames.add ( processes.get ( i )[1] );
                }

            }
            columnNames.add ( metaData.getColumnName ( column ) );
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        while ( rs.next () ) {
            Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 3 ; columnIndex <= 5 ; columnIndex ++ ) {

                if ( columnIndex == 3 ) {

                    for ( int i = 0 ; i < processes.size () ; i ++ ) {

                        if ( rs.getObject ( 7 ).equals ( processes.get ( i ) ) ) {
                            vector.add ( rs.getObject ( 5 ) );
                        } else {
                            vector.add ( "--" );
                        }
                    }
                }

                vector.add ( rs.getObject ( columnIndex ) );
            }
            data.add ( vector );
        }

        return new DefaultTableModel ( data , columnNames );

    }

    public void show_data_NA_message ( String data ) {

        JOptionPane.showMessageDialog ( null , data + " details not availabel in masters. this information is important for carriying out this activity. Consider updating the master first ! " , "Data Required " , 0 );
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

    // Vector<String[2]> value1 = new Vector<String[2]>();
    // Vector<Vector<Object>> values1 = new Vector<Vector<Object>>();
    ArrayList<String> orderNos;
    ArrayList<String> orderValues;
    ArrayList<String> past_processes;

}
