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
public class WIP_Operationsv3 extends javax.swing.JPanel {

    ArrayList<String[]> orders;
    ArrayList<String> customers;
    ArrayList<String[]> products;
    ArrayList<String[]> processes;
    ArrayList<String> processesIDs;
    ArrayList<String> processesNames;
    int totalProcessedItems = 0;

    boolean MoveToStores = false;

    Connection con;

    String selectectedOrder, selectectedCustomer;

    /**
     * Creates new form WIP_Operations
     */
    public WIP_Operationsv3 () {
        initComponents ();
        AutoCompletion.enable ( jComboBox1 );
        AutoCompletion.enable ( jComboBox2 );
        AutoCompletion.enable ( jComboBox3 );
        AutoCompletion.enable ( jComboBox4 );
        AutoCompletion.enable ( jComboBox5 );
        AutoCompletion.enable ( jComboBox6 );

        ResultSet result = null;//DB_Operations.getTransactions("sales_orders");

        try {

            result = DB_Operations.getTransactions ( "orders" );

            if ( result != null ) {
                jComboBox4.removeAllItems ();
                orders = new ArrayList<String[]> ();
                while ( result.next () ) {

                    jComboBox4.addItem ( result.getString ( "order_no" ) );
                    jComboBox5.addItem ( result.getString ( "order_ack" ) );
                    orders.add ( new String[] { result.getString ( "sales_po_id" ) , result.getString ( "order_ack" ) } );
                }

            } else {

                jComboBox4.addItem ( "Not Available" );
                flag_required_master_data_NA = true;
                //     show_data_NA_message ( "sales orders" );
            }

            selectectedOrder = orders.get ( 0 )[ 0 ];

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

        try {
            selectectedOrder = orders.get ( jComboBox4.getSelectedIndex () )[ 0 ];
            jComboBox5.setSelectedIndex ( jComboBox4.getSelectedIndex () );

            loadProductsAndQtys ( selectectedOrder );

        } catch ( Exception e ) {
            System.out.println ( e.getMessage () );
        }

    }

    public void loadProductsAndQtys ( String selctOrder ) {
        try {

            jComboBox1.removeAllItems ();
            jComboBox2.removeAllItems ();
            jTextField1.setText ( "" );

            //      ResultSet result = DB_Operations.executeSingle ( "SELECT (SELECT CUSTOMER_NAME from customer where CUSTOMER_ID in (CUSTOMER)) , DISTINCT(product_id), product_qty FROM po_order_details a INNER JOIN sales_orders b where  a.ref_po_id=" + selctOrder );
            ResultSet result = DB_Operations.executeSingle ( "SELECT (SELECT distinct(CUSTOMER_NAME) from customer where CUSTOMER_ID in (so_customer_id)) as CustomerName  FROM sales_orders where sales_po_id = " + selectectedOrder );
            if ( result != null ) {

                while ( result.next () ) {
                    jComboBox2.addItem ( result.getString ( "CustomerName" ) );
                }
            } else {
                flag_required_master_data_NA = true;
            }

            result = DB_Operations.executeSingle ( "SELECT product_id,  (SELECT PART_NAME from finished_goods where FG_ID in (product_id)) as ProductName,  product_qty FROM po_order_details a INNER JOIN sales_orders b where  b.sales_po_id= " + selectectedOrder + " AND a.ref_po_id =" + selectectedOrder );

            if ( result != null ) {
                products = new ArrayList<String[]> ();
                while ( result.next () ) {

                    products.add ( new String[] { String.valueOf ( result.getInt ( "product_id" ) ) , result.getString ( "ProductName" ) , String.valueOf ( result.getInt ( "product_qty" ) ) } );
                    jComboBox1.addItem ( result.getString ( "ProductName" ) );
                }
            }

            ResultSet rs = null;
            try {
                rs = DB_Operations.executeSingle ( "SELECT product_qty as TARGETQTY  FROM po_order_details WHERE product_id = " + products.get ( jComboBox1.getSelectedIndex () )[ 0 ] + "  AND ref_po_id = " + orders.get ( jComboBox4.getSelectedIndex () )[ 0 ] );
                targetQty = Integer.parseInt ( rs.getString ( "TARGETQTY" ) );
            } catch ( SQLException e ) {

            } finally {
                try {
                    rs.close ();
                } catch ( SQLException e ) {

                }
            }

        } catch ( SQLException e1 ) {
            System.out.println ( e1.getMessage () );
        } catch ( ArrayIndexOutOfBoundsException e2 ) {
            System.out.println ( e2.getMessage () );
        }

    }

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
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();

        setLayout(null);

        jLabel23.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel23.setText("Nos");
        add(jLabel23);
        jLabel23.setBounds(1160, 120, 40, 16);

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
        jComboBox1.setBounds(550, 50, 180, 40);

        add(jComboBox2);
        jComboBox2.setBounds(310, 50, 180, 40);

        jTextField1.setEditable(false);
        jTextField1.setText("0");
        jTextField1.setToolTipText("Total Qty");
        add(jTextField1);
        jTextField1.setBounds(550, 110, 180, 40);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Move to process");
        add(jLabel1);
        jLabel1.setBounds(790, 30, 180, 16);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Process1", "Process2", "Process3", "Process4", "Process5", "Process6", "Process7", "Process8", "Process9", "Process10", "Process11" }));
        jComboBox3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox3MouseClicked(evt);
            }
        });
        add(jComboBox3);
        jComboBox3.setBounds(790, 50, 180, 40);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Nos");
        add(jLabel2);
        jLabel2.setBounds(930, 120, 40, 20);

        jTextField2.setText("0");
        jTextField2.setToolTipText("Move");
        add(jTextField2);
        jTextField2.setBounds(790, 110, 180, 40);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Move");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(1020, 250, 180, 40);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Order QTY");
        add(jLabel3);
        jLabel3.setBounds(550, 90, 70, 20);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Order No");
        add(jLabel4);
        jLabel4.setBounds(80, 30, 70, 20);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("Product");
        add(jLabel6);
        jLabel6.setBounds(550, 30, 80, 20);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("Customer");
        add(jLabel7);
        jLabel7.setBounds(310, 30, 80, 16);

        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        add(jComboBox4);
        jComboBox4.setBounds(80, 50, 180, 40);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Load Orders");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(310, 110, 180, 40);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Load work in progress");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(550, 170, 180, 40);

        jLabel19.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel19.setText("Processed Qty");
        add(jLabel19);
        jLabel19.setBounds(790, 90, 180, 16);

        jLabel20.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel20.setText("Rejection");
        add(jLabel20);
        jLabel20.setBounds(1020, 30, 90, 20);

        jTextField3.setText("0");
        add(jTextField3);
        jTextField3.setBounds(1020, 110, 180, 40);

        jLabel22.setText("Nos");
        add(jLabel22);
        jLabel22.setBounds(1160, 120, 40, 20);

        jLabel24.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel24.setText("OA No");
        add(jLabel24);
        jLabel24.setBounds(80, 90, 140, 16);

        jComboBox5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox5MouseClicked(evt);
            }
        });
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        add(jComboBox5);
        jComboBox5.setBounds(80, 110, 180, 40);

        jComboBox6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(jComboBox6);
        jComboBox6.setBounds(1020, 50, 180, 40);

        jCheckBox1.setText("Move to stores");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        add(jCheckBox1);
        jCheckBox1.setBounds(790, 170, 180, 24);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setAutoscrolls(false);
        jScrollPane2.setViewportView(jTextArea1);

        add(jScrollPane2);
        jScrollPane2.setBounds(1020, 170, 180, 70);

        jLabel21.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel21.setText("Rejected Qty");
        add(jLabel21);
        jLabel21.setBounds(1020, 90, 90, 16);

        jLabel25.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel25.setText("Reason");
        add(jLabel25);
        jLabel25.setBounds(1020, 146, 90, 30);
    }// </editor-fold>//GEN-END:initComponents

    static Calendar c2;
    static SimpleDateFormat sdf2;

    boolean flag_required_master_data_NA = false;

    int updateWIP_ID = -1;
    String OP_TYPE = "insert";
    String updateQty = "0";
    ArrayList<String> oldProcesses;
    ArrayList<String> oldValues;

    String previousProcess;
    int previousQty;
    int previousProcId = 0;
    int targetQty;

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        if ( MoveToStores ) {

            updateStock ();
        } else {
            updateWIP ();
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    public void updateWIP () {

        c2 = Calendar.getInstance ();
        sdf2 = new SimpleDateFormat ( "dd-MM-yyyy HH:mm:ss" );
        String strDate2 = sdf2.format ( c2.getTime () );

        int P = Integer.parseInt ( processes.get ( jComboBox3.getSelectedIndex () )[ 0 ] );

        int P2 = Integer.parseInt ( processes.get ( jComboBox6.getSelectedIndex () )[ 0 ] );

        int MOVEQTY = 0;
        int REJECTQTY = 0;

        try {
            MOVEQTY = Integer.parseInt ( jTextField2.getText () );
            REJECTQTY = Integer.parseInt ( jTextField3.getText () );
        } catch ( NullPointerException e ) {

        }

        ///////////////////////////////////////////////  OPERATION FOR MOVING THE PARTS AMONGST PRODUCTION STAGES  //////////////////////////////////////////
        if ( MOVEQTY > 0 ) {

            String verificationQuery = "SELECT P_ID,  MOVEQTY, PROCESS_STAGE, product_qty as TARGETQTY  FROM op_wip a INNER JOIN po_order_details b WHERE   PRODUCT = " + products.get ( jComboBox1.getSelectedIndex () )[ 0 ] + "  AND ORDERNO = " + orders.get ( jComboBox4.getSelectedIndex () )[ 0 ] + " AND a.PRODUCT = b.product_id  AND a.ORDERNO = b.ref_po_id ";
            ResultSet pastProcesses = null;
            int lastMoveQty = 0;

            try {

                pastProcesses = DB_Operations.executeSingle ( verificationQuery );

                oldProcesses = new ArrayList<String> ();
                oldValues = new ArrayList<String> ();
                int count = 0;
                int stage = 0;
                int recordCount = 0;
                int previousId = -1;
                int previousQty = 0;

                while ( pastProcesses.next () ) {

                    recordCount ++;

                    if ( P == Integer.parseInt ( pastProcesses.getString ( "PROCESS_STAGE" ) ) ) {

                        //stage = count=1;
                        OP_TYPE = "update";
                        updateWIP_ID = Integer.parseInt ( pastProcesses.getString ( "P_ID" ) );
                        updateQty = String.valueOf ( MOVEQTY + Integer.parseInt ( pastProcesses.getString ( "MOVEQTY" ) ) );

                    } else {
                        count ++;
                    }

                    lastMoveQty = Integer.parseInt ( pastProcesses.getString ( "MOVEQTY" ) );
                    oldProcesses.add ( pastProcesses.getString ( "P_ID" ) );
                    oldValues.add ( pastProcesses.getString ( "MOVEQTY" ) );
                }

            } catch ( SQLException e ) {

                System.out.println ( "Cannot fetch past processes" );
            } finally {
                try {
                    pastProcesses.close ();
                } catch ( SQLException e ) {
                }
            }

            try {

                if ( ( targetQty - ( totalProcessedItems ) ) >= 0 ) {

                    if ( OP_TYPE.equals ( "insert" ) ) {

                        if ( oldProcesses.size () >= 1 ) {
                            int previousId = -1;
                            try {
                                previousId = Integer.parseInt ( oldProcesses.get ( oldProcesses.size () - 1 ) );
                                previousQty = Integer.parseInt ( oldValues.get ( oldProcesses.size () - 1 ) );
                            } catch ( Exception e ) {
                            }
                            if ( previousId >= 1 && previousQty > 0 && previousQty >= MOVEQTY ) {
                                String test1 = "insert into op_wip ( PRODUCT,PROCESS_STAGE,MOVEDATE,ORDERNO, MOVEQTY, REJECTED, BALANCE ) values ( '" + products.get ( jComboBox1.getSelectedIndex () )[ 0 ] + "' ," + P + ",'" + strDate2 + "','" + orders.get ( jComboBox4.getSelectedIndex () )[ 0 ] + "'," + MOVEQTY + "," + 0 + "," + ( targetQty - totalProcessedItems ) + " )";
                                int newRecordId = DB_Operations.executeInsertRandom ( test1 );

                                String test2 = "update op_wip set  MOVEDATE= '" + strDate2 + "'  ,  MOVEQTY =  (MOVEQTY - " + MOVEQTY + ")  where P_ID = " + previousId + " AND MOVEQTY > 0";
                                DB_Operations.executeInsertRandom ( test2 );

                                JOptionPane.showMessageDialog ( null , "Work in process updated !" );
                            } else {

                                String test1 = "insert into op_wip ( PRODUCT,PROCESS_STAGE,MOVEDATE,ORDERNO, MOVEQTY , REJECTED, BALANCE) values ( '" + products.get ( jComboBox1.getSelectedIndex () )[ 0 ] + "' ," + P + ",'" + strDate2 + "','" + orders.get ( jComboBox4.getSelectedIndex () )[ 0 ] + "'," + MOVEQTY + "," + 0 + "," + ( targetQty - totalProcessedItems ) + " )";
                                int newRecordId = DB_Operations.executeInsertRandom ( test1 );
                                JOptionPane.showMessageDialog ( null , "Work in process updated !" );
                            }
                        } else {

                            String test1 = "insert into op_wip ( PRODUCT,PROCESS_STAGE,MOVEDATE,ORDERNO, MOVEQTY , REJECTED, BALANCE) values ( '" + products.get ( jComboBox1.getSelectedIndex () )[ 0 ] + "' ," + P + ",'" + strDate2 + "','" + orders.get ( jComboBox4.getSelectedIndex () )[ 0 ] + "'," + MOVEQTY + "," + 0 + "," + ( targetQty - totalProcessedItems ) + " )";
                            int newRecordId = DB_Operations.executeInsertRandom ( test1 );
                            JOptionPane.showMessageDialog ( null , "Work in process updated !" );
                        }

                        loadOrders ( orders.get ( jComboBox4.getSelectedIndex () )[ 0 ] , products.get ( jComboBox1.getSelectedIndex () )[ 0 ] );

                    } else if ( OP_TYPE.equals ( "update" ) ) {

                        if ( oldProcesses.size () >= 1 ) {
                            int previousId = -1;
                            try {
                                previousId = Integer.parseInt ( oldProcesses.get ( oldProcesses.indexOf ( String.valueOf ( updateWIP_ID ) ) - 1 ) );
                                previousQty = Integer.parseInt ( oldValues.get ( oldProcesses.indexOf ( String.valueOf ( updateWIP_ID ) ) - 1 ) );
                            } catch ( Exception e ) {
                                System.out.println ( e.getMessage () );
                            }

                            if ( previousId >= 1 && previousQty > 0 && previousQty >= MOVEQTY ) {
                                String test = "update op_wip set  MOVEDATE='" + strDate2 + "',  MOVEQTY = (MOVEQTY + " + MOVEQTY + ")  where P_ID = " + updateWIP_ID;
                                DB_Operations.executeInsertRandom ( test );

                                String test2 = "update op_wip set  MOVEDATE= '" + strDate2 + "'  ,  MOVEQTY =  (MOVEQTY - " + MOVEQTY + ")  where P_ID = " + previousId + " AND MOVEQTY > 0";
                                DB_Operations.executeInsertRandom ( test2 );
                                JOptionPane.showMessageDialog ( null , "Work in process updated !" );
                            } else if ( previousQty > 0 || previousId == -1 ) {
                                if ( ( targetQty - ( totalProcessedItems + MOVEQTY ) ) > 0 ) {
                                    String test = "update op_wip set  MOVEDATE='" + strDate2 + "',  MOVEQTY = (MOVEQTY + " + MOVEQTY + ")  where P_ID = " + updateWIP_ID;
                                    DB_Operations.executeInsertRandom ( test );
                                    JOptionPane.showMessageDialog ( null , "Work in process updated !" );
                                }
                            }
                        }

                        loadOrders ( orders.get ( jComboBox4.getSelectedIndex () )[ 0 ] , products.get ( jComboBox1.getSelectedIndex () )[ 0 ] );

                    }
                } else {
                    JOptionPane.showMessageDialog ( null , "Please enter correct qty for transaction !" );
                }

            } catch ( NumberFormatException nef2 ) {
                System.out.println ( "Number Format Wrong " + nef2.getMessage () );
            } catch ( Exception nef2 ) {
                System.out.println ( "Number Format Wrong " + nef2.getMessage () );
            }

            OP_TYPE = "insert";
        }

        ///////////////////////////////////   OPERATION FOR REJECTING THE QTY AND SEND BACK TO THE FIRST STAGE PROCESS   /////////////////////////////////////////////////////
        if ( REJECTQTY > 0 ) {

            String rejectionReason =  jTextArea1.getText();
            
            String verificationQuery = "SELECT P_ID,  MOVEQTY, PROCESS_STAGE, product_qty as TARGETQTY  FROM op_wip a INNER JOIN po_order_details b WHERE   PRODUCT = " + products.get ( jComboBox1.getSelectedIndex () )[ 0 ] + "  AND ORDERNO = " + orders.get ( jComboBox4.getSelectedIndex () )[ 0 ] + " AND a.PRODUCT = b.product_id  AND a.ORDERNO = b.ref_po_id ";
            ResultSet pastProcesses = null;

            try {
                pastProcesses = DB_Operations.executeSingle ( verificationQuery );
                oldProcesses = new ArrayList<String> ();
                oldValues = new ArrayList<String> ();
                int previousQty = 0;

                while ( pastProcesses.next () ) {

                    if ( P2 == Integer.parseInt ( pastProcesses.getString ( "PROCESS_STAGE" ) ) ) {
                        updateWIP_ID = Integer.parseInt ( pastProcesses.getString ( "P_ID" ) );
                    }
                    oldProcesses.add ( pastProcesses.getString ( "P_ID" ) );
                    oldValues.add ( pastProcesses.getString ( "MOVEQTY" ) );
                }

            } catch ( SQLException e ) {
                System.out.println ( "Cannot fetch past processes" );
            } finally {
                try {
                    pastProcesses.close ();
                } catch ( SQLException e ) {
                }
            }

            try {
                if ( ( targetQty - totalProcessedItems ) >= 0 ) {

                    if ( oldProcesses.size () >= 1 ) {
                        int previousId = -1;
                        try {
                            previousQty = Integer.parseInt ( oldValues.get ( oldProcesses.indexOf ( String.valueOf ( updateWIP_ID ) ) ) );
                        } catch ( Exception e ) {
                            System.out.println ( e.getMessage () );
                        }

                        if ( previousQty > 0 ) {
                            String test2 = "update op_wip set  REJECTIONDATE= '" + strDate2 + "'  ,  MOVEQTY =  (MOVEQTY - " + REJECTQTY + "), REJECTED =  (REJECTED + " + REJECTQTY + "), REASON =  '"+rejectionReason+"'   where P_ID = " + updateWIP_ID + " AND MOVEQTY > 0";
                            DB_Operations.executeInsertRandom ( test2 );
                            JOptionPane.showMessageDialog ( null , "Work in process updated !" );
                        }

                        loadOrders ( orders.get ( jComboBox4.getSelectedIndex () )[ 0 ] , products.get ( jComboBox1.getSelectedIndex () )[ 0 ] );
                    }
                } else {
                    JOptionPane.showMessageDialog ( null , "Please enter correct qty for transaction !" );
                }

            } catch ( NumberFormatException nef2 ) {
                System.out.println ( "Number Format Wrong " + nef2.getMessage () );
            } catch ( Exception nef2 ) {
                System.out.println ( "Number Format Wrong " + nef2.getMessage () );
            }

        }

        jTextField2.setText ( "0" );
        jTextField3.setText ( "0" );
        
    }

    public void updateStock () {

    }


    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed

        try {
            selectectedOrder = orders.get ( jComboBox4.getSelectedIndex () )[ 0 ];
            jComboBox5.setSelectedIndex ( jComboBox4.getSelectedIndex () );

            loadProductsAndQtys ( selectectedOrder );

        } catch ( Exception e ) {
            System.out.println ( e.getMessage () );
        }
    }//GEN-LAST:event_jComboBox4ActionPerformed


    private void jComboBox3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox3MouseClicked
        // TODO add your handling code here:
//        for ( int i = 0 ; i < processes.size () ; i ++ ) {
//            {
//                if ( processes.get ( i ).equals ( jComboBox3.getSelectedItem ().toString () ) ) {
//                    jSlider1.setValue ( i );
//                    break;
//                }
//            }
//        }
    }//GEN-LAST:event_jComboBox3MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:

        jTextField1.setText ( String.valueOf ( targetQty ) );

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
//        try {
//            selectectedOrder = orders.get ( jComboBox4.getSelectedIndex () )[0];
//      
//            loadProductsAndQtys ( selectectedOrder  );
//
//        } catch ( IndexOutOfBoundsException e ) {
//            System.out.println ( "Indext of of bounds :"+e.getMessage () );
//        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:

        try {

            jTextField1.setText ( String.valueOf ( targetQty ) );

            //    loadOrders ( products.get ( jComboBox1.getSelectedIndex () )[ 0 ] , customers.get ( jComboBox2.getSelectedIndex () ) );
            loadOrders ( orders.get ( jComboBox4.getSelectedIndex () )[ 0 ] , products.get ( jComboBox1.getSelectedIndex () )[ 0 ] );

        } catch ( NullPointerException e ) {
            System.out.println ( e.getMessage () );
        }

    }//GEN-LAST:event_jButton3MouseClicked

    private void jComboBox5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox5MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox5MouseClicked

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
        try {
            jComboBox4.setSelectedIndex ( jComboBox5.getSelectedIndex () );
            selectectedOrder = orders.get ( jComboBox5.getSelectedIndex () )[ 0 ];

            loadProductsAndQtys ( selectectedOrder );

        } catch ( Exception e ) {
            System.out.println ( "Combobox selection exception : " + e.getMessage () );
        }
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        if ( jCheckBox1.isSelected () ) {
            MoveToStores = true;
        } else {
            MoveToStores = false;
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    public void loadOrders ( String selectedOrder , String selectedProduct ) {

        ResultSet orderDetails = null;

        totalProcessedItems = 0;

        try {

            int selectedFG_ID = Integer.parseInt ( selectedProduct );
            ResultSet result2 = DB_Operations.executeSingle ( "SELECT REF_PROCESS_ID, PROCESS_NAME FROM FG_PROCESS_MACH_MAP a INNER JOIN PROCESS_MASTER b on a.REF_PROCESS_ID = b.PROCESS_ID AND REF_FG_ID = " + selectedFG_ID );

            if ( result2 != null ) {
                jComboBox3.removeAllItems ();
                jComboBox6.removeAllItems ();
                processes = new ArrayList<String[]> ();
                processesIDs = new ArrayList<String> ();
                processesNames = new ArrayList<String> ();
                while ( result2.next () ) {

                    jComboBox3.addItem ( result2.getString ( "PROCESS_NAME" ) );
                    jComboBox6.addItem ( result2.getString ( "PROCESS_NAME" ) );
                    processes.add ( new String[] { String.valueOf ( result2.getInt ( "REF_PROCESS_ID" ) ) , result2.getString ( "PROCESS_NAME" ) } );
                    processesIDs.add ( result2.getString ( "REF_PROCESS_ID" ) );
                    processesNames.add ( result2.getString ( "PROCESS_NAME" ) );

                }
            }

            String loadOrderDetailsIfAvail = "SELECT  P_ID, PROCESS_STAGE, MOVEQTY, PROCESS_NAME FROM op_wip a INNER JOIN PROCESS_MASTER b WHERE PRODUCT = '" + selectedProduct + "' AND  ORDERNO = '" + selectedOrder + "'  AND a.PROCESS_STAGE = b.PROCESS_ID ";

            orderDetails = DB_Operations.executeSingle ( loadOrderDetailsIfAvail );

            orderNos = new ArrayList<String> ();
            orderValues = new ArrayList<String> ();
            past_processes = new ArrayList<String[]> ();

            if ( orderDetails != null ) {
                while ( orderDetails.next () ) {

                    orderValues.add ( orderDetails.getString ( "MOVEQTY" ) );
                    totalProcessedItems = totalProcessedItems + Integer.parseInt ( orderDetails.getString ( "MOVEQTY" ) );

                    past_processes.add ( new String[] { orderDetails.getString ( "P_ID" ) , orderDetails.getString ( "PROCESS_STAGE" ) , orderDetails.getString ( "PROCESS_NAME" ) } );

                }
            }

           
//            String[] lastProcess = null;
//            if ( past_processes.size () > 1 && past_processes.size () < processes.size () ) {
//                lastProcess = past_processes.get ( past_processes.size () - 1 );
//            } else if ( past_processes.size () == 1 ) {
//                lastProcess = past_processes.get ( 0 );
//            } else if ( past_processes.size () == processes.size () ) {
//                lastProcess = past_processes.get ( past_processes.size () - 1 );
//            }
//
//            int selectedIndex = -1;
//            if ( lastProcess != null ) {
//                for ( int i = 0 ; i < processes.size () ; i ++ ) {
//                    String[] tempArr = processes.get ( i );
//                    if ( tempArr[ 1 ].equals ( lastProcess[ 2 ] ) ) {
//                        selectedIndex = i;
//                    }
//                }
//            }
//
//            jSlider1.setMaximum ( processes.size () );
//
//            jSlider1.setValue ( selectedIndex + 1 );
//
//            jLabel5.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
//            jLabel9.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
//            jLabel10.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
//            jLabel11.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
//            jLabel12.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
//            jLabel13.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
//            jLabel14.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
//            jLabel15.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
//            jLabel16.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
//            jLabel17.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
//            jLabel18.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 11 ) );
//
//            switch ( selectedIndex + 1 ) {
//
//                case 1:
//                    jLabel5.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
//                    break;
//                case 2:
//                    jLabel9.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
//                    break;
//                case 3:
//                    jLabel10.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
//                    break;
//                case 4:
//                    jLabel11.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
//                    break;
//                case 5:
//                    jLabel12.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
//                    break;
//                case 6:
//                    jLabel13.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
//                    break;
//                case 7:
//                    jLabel14.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
//                    break;
//                case 8:
//                    jLabel15.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
//                    break;
//                case 9:
//                    jLabel16.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
//                    break;
//                case 10:
//                    jLabel17.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
//                    break;
//                case 11:
//                    jLabel18.setFont ( new java.awt.Font ( "Leelawadee UI" , 1 , 12 ) );
//                    break;
//
//            }

            jTextField1.setText ( String.valueOf ( targetQty ) );

            loadOrderDetailsIfAvail = "SELECT MOVEDATE,  MOVEQTY, PROCESS_STAGE, product_qty as TARGETQTY  FROM op_wip a INNER JOIN po_order_details b WHERE   PRODUCT =" + selectedProduct + " AND ORDERNO = " + selectedOrder + " AND a.PRODUCT = b.product_id  AND a.ORDERNO = b.ref_po_id";

            orderDetails = DB_Operations.executeSingle ( loadOrderDetailsIfAvail );

            jTable1.setModel ( buildTableModel2 ( orderDetails ) );

        } catch ( SQLException e ) {
            System.out.println ( " Error :  " + e.getMessage () );
        } catch ( IndexOutOfBoundsException e ) {
            System.out.println ( " Error :  " + e.getMessage () );
        } catch ( Exception e ) {
            System.out.println ( " Error :  " + e.getMessage () );
        }
    }

    public DefaultTableModel buildTableModel2 ( ResultSet rs )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        int columnCount = metaData.getColumnCount ();

        columnNames.add ( "<html>Not processed<br>or rejected<html>" );
        for ( int column = 1 ; column < 3 ; column ++ ) {

            if ( column == 1 ) {
                for ( int i = 0 ; i < processes.size () ; i ++ ) {
                    //  columnNames.add ( String.valueOf(targetQty - totalProcessedItems  ));
                    columnNames.add ( processes.get ( i )[ 1 ] );
                }
            }
            //columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<String>> data = new Vector<Vector<String>> ();
        Vector<String> vector = new Vector<String> ();

        try {
            //    vector.add ( "<html> <b>" + String.valueOf ( Integer.parseInt ( rs.getString ( "TARGETQTY" ) ) - Integer.parseInt ( rs.getString ( "MOVEQTY" ) ) ) + "</b>  </html>" );
            vector.add ( "<html> <b>" + String.valueOf ( targetQty - totalProcessedItems ) + "</b>  </html>" );
        } catch ( NumberFormatException nef ) {

        }

        int totalMoveQty = 0;
        int k = 0;
        while ( rs.next () ) {

            vector.add ( "<html> <b>" + rs.getString ( "MOVEQTY" ) + "</b>  <br>" + rs.getString ( "MOVEDATE" ) + "</html>" );

            totalMoveQty = totalMoveQty + Integer.parseInt ( rs.getString ( 2 ) );
        }

        data.add ( vector );

        rs.close ();

        return new DefaultTableModel ( data , columnNames );

    }

    public void show_data_NA_message ( String data ) {

        JOptionPane.showMessageDialog ( null , data + " details not availabel in masters. this information is important for carriying out this activity. Consider updating the master first ! " , "Data Required " , 0 );
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

    // Vector<String[2]> value1 = new Vector<String[2]>();
    // Vector<Vector<Object>> values1 = new Vector<Vector<Object>>();
    ArrayList<String> orderNos;
    ArrayList<String> orderValues;
    ArrayList<String[]> past_processes;

}
