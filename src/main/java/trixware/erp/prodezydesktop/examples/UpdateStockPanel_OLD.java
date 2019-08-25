/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Derived.AutoCompletion;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.RawMaterialDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class UpdateStockPanel_OLD extends javax.swing.JPanel {

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

    String addEmpAPICall = "";
        String result2 = "" ;
        ArrayList<RawMaterialDR> RMList = null;
        ArrayList<ProductDR> products = null;
    
    /**
     * Creates new form UpdateStockPanel
     */
    public UpdateStockPanel_OLD () {

        initComponents ();

        AutoCompletion.enable ( jComboBox1 );
        AutoCompletion.enable ( jComboBox3 );
        AutoCompletion.enable ( jComboBox5 );

         //  jComboBox1.addActionListener ( selectUnitAction);
        //  jComboBox3.addActionListener ( selectCategoryAction );
          
          loadContent();
    }

    public void loadContent () {

        //   jComboBox1.removeAllItems (); 
        try {

            ResultSet result = DB_Operations.getMasters ( "raw_material" );
            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
//            if ( result != null ) {
//
//                raw_materials = new ArrayList<String[]> ();
//
//                while ( result.next () ) {
//                    raw_materials.add ( new String[] { result.getString ( "RM_ID" ) , result.getString ( "RM_NAME" ) , result.getString ( "UOM" ) } );
//                        jComboBox1.addItem(result.getString("RM_NAME"));
//                }
//
//            } else {
//                System.out.println ( "result set empty" );
//            }

                if ( jComboBox1.getItemCount () == 0 ) {
                    //result = DB_Operations.getMasters ( "raw_material_type" );
                   
                    addEmpAPICall = "rawmaterials";
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
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
                        System.out.println ( ""+emp.get ( "RM_NAME" ).toString () );
                        jComboBox1.addItem ( emp.get ( "RM_NAME" ).toString () );
                        //RMList.add( new RawMaterialDR ( Integer.parseInt(emp.get ( "RM_ID" ).toString ()), emp.get ( "RM_NAME" ).toString ()  )); 
                        raw_materials.add ( new String[] { emp.get ( "RM_ID" ).toString () ,  emp.get ( "RM_NAME" ).toString () , emp.get ( "RMM_UOM_ID" ).toString ()  } );
                    }
                    
                    //jLabel6.setText(  rawMaterials.get(jComboBox1.getSelectedIndex() ).UOM);
                    //jComboBox1.addActionListener ( productComboAction );
                }




//            result = DB_Operations.getMasters ( "finished_goods" );
//            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
//            if ( result != null ) {
//
//                finished_goods = new ArrayList<String[]> ();
//
//                while ( result.next () ) {
//                    finished_goods.add ( new String[] { result.getString ( "FG_ID" ) , result.getString ( "FG Name" ) , result.getString ( "FG_UOM_ID" ) } );
//                        jComboBox1.addItem(result.getString("FG Name"));
//                }
//
//            } else {
//                System.out.println ( "result set empty" );
//            }

                 
                    //result = DB_Operations.getMasters ( "raw_material_type" );
                   
                    addEmpAPICall = "finishedgoods";
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
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
                    finished_goods = new ArrayList<String[]> ();
                    for ( int i = 0 ; i < records.length () ; i ++ ) {
                    
                        emp = records.getJSONObject ( i);
                       // jComboBox1.addItem ( emp.get ( "PART_NAME" ).toString () );
                        products.add( new ProductDR ( Integer.parseInt(emp.get ( "FG_ID" ).toString ()), emp.get ( "PART_NAME" ).toString () ,  emp.get ( "FG_UOM_ID" ).toString ()   ));
                        finished_goods.add ( new String[] { emp.get ( "FG_ID" ).toString () , emp.get ( "PART_NAME" ).toString (), emp.get ( "FG_UOM_ID" ).toString ()  } );
                    }
                    
                    
                    //jComboBox1.addActionListener ( productComboAction );
                

            selectedMaster = jComboBox3.getSelectedItem ().toString ();

            jComboBox1.removeAllItems ();

            if ( selectedMaster.equals ( "Raw Material" ) ) {
                for ( int i = 0 ; i < raw_materials.size () ; i ++ ) {
                    jComboBox1.addItem ( raw_materials.get ( i )[ 1 ] );
                }
                selectedItemId = Integer.parseInt ( raw_materials.get ( jComboBox1.getSelectedIndex () )[ 0 ] );
            } else {
                for ( int i = 0 ; i < finished_goods.size () ; i ++ ) {
                    jComboBox1.addItem ( finished_goods.get ( i )[ 1 ] );
                }
                selectedItemId = Integer.parseInt ( finished_goods.get ( jComboBox1.getSelectedIndex () )[ 0 ] );
            }

            result = DB_Operations.executeSingle ( "SELECT RM_TYPE, (OPENING),(RECEIVED),(USED),(CLOSING), MAX(RMStock_TR_ID) FROM raw_material A INNER JOIN RMStock B ON A.RM_ID = B.RM_ITEM_ID AND A.RM_ID =" + selectedItemId );

            if ( result != null ) {

                String a="0", b="0", c="0";
                
                
                    a = result.getString ( 2 );
                    b = result.getString ( 3 );
                    c = result.getString ( 4 );
                try{
                    OldOPENING = Integer.parseInt ( result.getString ( 5 ) );
                }catch(NullPointerException | NumberFormatException e){
                    
                    OldOPENING = 0;
                }
                System.out.println ( a + "    " + b + "    " + c + "    " + OldOPENING );

                jLabel15.setText ( OldOPENING + "" );

            } else {

                System.out.println ( "No result to show" );
            }

            result.close ();

            jComboBox3.addActionListener ( selectCategoryAction );
            jComboBox1.addActionListener ( selectUnitAction );

        } catch ( SQLException e ) {
            System.out.println ( "Error No result to show " + e.getMessage () );
            StaticValues.writer.writeExcel (UpdateStockPanel_OLD.class.getSimpleName () , UpdateStockPanel_OLD.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }
    }

    public void loadContentAfterTransaction () {

        try {

            ResultSet result = null;

            if ( selectedMaster.equals ( "Raw Material" ) ) {
                selectedItemId = Integer.parseInt ( raw_materials.get ( jComboBox1.getSelectedIndex () )[ 0 ] );
            } else {
                selectedItemId = Integer.parseInt ( finished_goods.get ( jComboBox1.getSelectedIndex () )[ 0 ] );
            }

            if ( selectedMaster.equals ( "Raw Material" ) ) {
                result = DB_Operations.executeSingle ( "SELECT RM_TYPE, (OPENING),(RECEIVED),(USED),(CLOSING), MAX(RMStock_TR_ID) FROM raw_material A INNER JOIN RMStock B ON A.RM_ID = B.RM_ITEM_ID AND A.RM_ID =" + selectedItemId );

            } else if ( selectedMaster.equals ( "Finished Goods" ) ) {
                result = DB_Operations.executeSingle ( "SELECT PART_NAME, (OPENING),(RECEIVED),(USED),(CLOSING) , MAX( FGStock_TR_ID ) FROM finished_goods A INNER JOIN FGStock B ON A.FG_ID = B.FG_ID AND A.FG_ID = " + selectedItemId );
            }

            if ( result != null ) {
                jLabel15.setText ( result.getString ( 2 ) );
                try {
                    if ( selectedTransaction.equals ( "Inward" ) ) {

                        jLabel16.setText ( result.getString ( 3 ) );
                        jLabel17.setText ( "0" );

                    } else if ( selectedTransaction.equals ( "Outward" ) ) {

                        jLabel17.setText ( result.getString ( 4 ) );
                        jLabel16.setText ( "0" );
                    }

                    CLOSING = Integer.parseInt ( result.getString ( 5 ) );
                } catch ( NumberFormatException e ) {
                    CLOSING = 0;
                    StaticValues.writer.writeExcel (UpdateStockPanel_OLD.class.getSimpleName () , UpdateStockPanel_OLD.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
                }

                jLabel18.setText ( CLOSING + "" );

            } else {
                System.out.println ( "No result to show" );
            }

            result.close ();

        } catch ( SQLException e ) {
            System.out.println ( "Error No result to show " + e.getMessage () );
            StaticValues.writer.writeExcel (UpdateStockPanel_OLD.class.getSimpleName () , UpdateStockPanel_OLD.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }
    }

    ActionListener selectUnitAction = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {

            if ( selectedMaster.equals ( "Raw Material" ) ) {
                jLabel2.setText ( raw_materials.get ( jComboBox1.getSelectedIndex () )[ 2 ] );
                selectedItemId = Integer.parseInt ( raw_materials.get ( jComboBox1.getSelectedIndex () )[ 0 ] );
            } else {
                jLabel2.setText ( finished_goods.get ( jComboBox1.getSelectedIndex () )[ 2 ] );
                selectedItemId = Integer.parseInt ( finished_goods.get ( jComboBox1.getSelectedIndex () )[ 0 ] );
            }

           ResultSet result2 = null;
            try {
                if ( selectedMaster.equals ( "Raw Material" ) ) {

                    result2 = DB_Operations.executeSingle ( "SELECT RM_TYPE, (OPENING),(RECEIVED),(USED),(CLOSING), MAX(RMStock_TR_ID) FROM raw_material A INNER JOIN RMStock B ON A.RM_ID = B.RM_ITEM_ID AND A.RM_ID =" + selectedItemId );

                } else {

                    result2 = DB_Operations.executeSingle ( "SELECT PART_NAME, (OPENING),(RECEIVED),(USED),(CLOSING) , MAX( FGStock_TR_ID ) FROM finished_goods A INNER JOIN FGStock B ON A.FG_ID = B.FG_ID AND A.FG_ID = " + selectedItemId );

                }

               
                try{    
                    OldOPENING = Integer.parseInt ( result2.getString ( 5 ) );
                    
                    jLabel16.setText ( "0" );
                    jLabel17.setText ( "0" );
                    jLabel18.setText ( "0" );
                }catch( Exception e2 ){
                    OldOPENING = 0 ;
                    jLabel16.setText ( "0" );
                    jLabel17.setText ( "0" );
                    jLabel18.setText ( "0" );
                    StaticValues.writer.writeExcel (UpdateStockPanel_OLD.class.getSimpleName () , UpdateStockPanel_OLD.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
                }
               
                jLabel15.setText ( OldOPENING + "" );
                
                result2.close ();
                
            } catch ( SQLException e2 ) {
                System.out.println ( e2.getMessage () );
                StaticValues.writer.writeExcel (UpdateStockPanel_OLD.class.getSimpleName () , UpdateStockPanel_OLD.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
            } finally {
                try {
                    result2.close ();
                } catch ( SQLException e3 ) {
                    System.out.println ( e3.getMessage () );
                    StaticValues.writer.writeExcel (UpdateStockPanel_OLD.class.getSimpleName () , UpdateStockPanel_OLD.class.getSimpleName () , e3.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e3.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
                }
            }

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

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(510, 530));
        setPreferredSize(new java.awt.Dimension(543, 545));
        setLayout(null);

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 700));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Select Master");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(60, 70, 70, 30);

        jButton1.setText("Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(360, 432, 80, 30);

        jButton2.setText("Cancel");
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
        jButton2.setBounds(269, 432, 80, 30);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Raw Material", "Finished Goods" }));
        jComboBox3.setNextFocusableComponent(jComboBox1);
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBox3);
        jComboBox3.setBounds(220, 70, 230, 30);
        jPanel1.add(jLabel10);
        jLabel10.setBounds(297, 84, 112, 0);

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
        jPanel2.setBounds(50, 270, 398, 150);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("Transaction Type");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(60, 170, 98, 30);

        jComboBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Inward", "Outward" }));
        jComboBox5.setNextFocusableComponent(jTextField1);
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox5);
        jComboBox5.setBounds(220, 170, 230, 30);

        jLabel14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel14.setText("Quantity");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(60, 220, 46, 30);

        jTextField1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1);
        jTextField1.setBounds(220, 220, 120, 30);

        jLabel19.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel19.setText("Select Item");
        jPanel1.add(jLabel19);
        jLabel19.setBounds(60, 120, 80, 30);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox1.setNextFocusableComponent(jComboBox5);
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(220, 120, 230, 30);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(370, 220, 70, 30);

        add(jPanel1);
        jPanel1.setBounds(10, 10, 510, 530);
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

        selectedMaster = jComboBox3.getSelectedItem ().toString ();

        String MASTERNAME = selectedMaster;

        if ( selectedMaster.equals ( "Raw Material" ) ) {
            selectedItemId = Integer.parseInt ( raw_materials.get ( jComboBox1.getSelectedIndex () )[ 0 ] );
        } else {
            selectedItemId = Integer.parseInt ( finished_goods.get ( jComboBox1.getSelectedIndex () )[ 0 ] );
        }

        int ItemID = selectedItemId;// get the id of the master entry selected in the
        int ID = 0;

        ResultSet result = null;

        if ( selectedMaster.equals ( "Finished Goods" ) ) {
            //          result = DB_Operations.executeSingle ( "SELECT PART_NAME, (OPENING),(RECEIVED),(USED),(CLOSING) FROM finished_goods A INNER JOIN FGStock B ON A.FG_ID = B.FG_ID AND A.FG_ID = " + selectedItemId + "    WHERE   FGStock_TR_ID = (SELECT MAX(FGStock_TR_ID)  FROM FGStock)" );
            result = DB_Operations.executeSingle ( "SELECT PART_NAME, (OPENING),(RECEIVED),(USED),(CLOSING) , MAX( FGStock_TR_ID ) FROM finished_goods A INNER JOIN FGStock B ON A.FG_ID = B.FG_ID AND A.FG_ID = " + selectedItemId );
        } else {
//            result = DB_Operations.executeSingle ( "SELECT RM_TYPE, (OPENING),(RECEIVED),(USED),(CLOSING) FROM raw_material A INNER JOIN RMStock B ON A.RM_ID = B.RMI_ID AND A.RM_ID =" + selectedItemId + " WHERE   RMStock_TR_ID = (SELECT MAX(RMStock_TR_ID)  FROM RMStock)" );
            result = DB_Operations.executeSingle ( "SELECT RM_TYPE, (OPENING),(RECEIVED),(USED),(CLOSING), MAX(RMStock_TR_ID) FROM raw_material A INNER JOIN RMStock B ON A.RM_ID = B.RM_ITEM_ID AND A.RM_ID =" + selectedItemId );
        }

        try {
            if ( result != null && result.isBeforeFirst () ) {

                recordExist = true;
                int a = 0;
                try {
                    RECEIVED = Integer.parseInt ( result.getString ( 3 ) );
                    USED = Integer.parseInt ( result.getString ( 4 ) );

                    a = Integer.parseInt ( result.getString ( 5 ) );
                } catch ( NumberFormatException e ) {
                    RECEIVED = 0;
                    USED = 0;
                    a = 0;
                    
                    StaticValues.writer.writeExcel (UpdateStockPanel_OLD.class.getSimpleName () , UpdateStockPanel_OLD.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
                }

                OPENING = a;
                OldCLOSING = a;

            } else {
                System.out.println ( "No result to show" );
                recordExist = false;
                OPENING = 0;
                RECEIVED = 0;
                USED = 0;
                CLOSING = 0;
            }

            result.close ();

        } catch ( SQLException e ) {
            System.out.println ( "Error Occured !" );
            
            StaticValues.writer.writeExcel (UpdateStockPanel_OLD.class.getSimpleName () , UpdateStockPanel_OLD.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }

        selectedTransaction = jComboBox5.getSelectedItem ().toString ();
        OPENING = OldCLOSING;
        if ( selectedTransaction.equals ( "Inward" ) ) {

            RECEIVED = Integer.parseInt ( jTextField1.getText () );
            USED = 0;
            CLOSING = OPENING + RECEIVED;
        } else if ( selectedTransaction.equals ( "Outward" ) ) {

            USED = Integer.parseInt ( jTextField1.getText () );
            RECEIVED = 0;
            CLOSING = OPENING - USED;
        }

        String CREATED_ON = strDate2;
        String EDITED_ON = "-";
        String EDITED_BY = Proman_User.getUsername ();

        int recordInterted = 0;

        recordInterted = DB_Operations.insert_STOCK_TRN ( MASTERNAME , ID , ItemID , OPENING , RECEIVED , USED , CLOSING , CREATED_ON , EDITED_ON , EDITED_BY );

        
         switch (MASTERNAME) {

                case "Raw Material":
                    try{
                            String addEmpAPICall = "rmstockadd?RMI_ID="+URLEncoder.encode(ID+"", "UTF-8")+"&RM_ITEM_ID="+URLEncoder.encode(ItemID+"", "UTF-8")+"&OPENING="+URLEncoder.encode(OPENING+"", "UTF-8")+"&RECEIVED="+URLEncoder.encode(RECEIVED+"", "UTF-8")+"&USED="+URLEncoder.encode(USED+"", "UTF-8")+"&CLOSING="+URLEncoder.encode(CLOSING+"", "UTF-8")+"&CREATED_ON="+URLEncoder.encode(strDate2+"", "UTF-8")+"&EDITED_ON="+URLEncoder.encode(strDate2+"", "UTF-8")+"&EDITED_BY="+URLEncoder.encode(Proman_User.getEID ()+"", "UTF-8")  ;
                            String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                            JOptionPane.showMessageDialog ( null , result2);
                    }catch(UnsupportedEncodingException e){

                    }
                    break;

                case "Finished Goods":
                    try{
                            String addEmpAPICall = "fgstockadd?FG_ID="+URLEncoder.encode(ID+"", "UTF-8")+"&FG_ITEM_ID="+URLEncoder.encode(ItemID+"", "UTF-8")+"&OPENING="+URLEncoder.encode(OPENING+"", "UTF-8")+"&RECEIVED="+URLEncoder.encode(RECEIVED+"", "UTF-8")+"&USED="+URLEncoder.encode(USED+"", "UTF-8")+"&CLOSING="+URLEncoder.encode(CLOSING+"", "UTF-8")+"&CREATED_ON="+URLEncoder.encode(strDate2+"", "UTF-8")+"&EDITED_ON="+URLEncoder.encode(strDate2+"", "UTF-8")+"&EDITED_BY="+URLEncoder.encode(Proman_User.getEID ()+"", "UTF-8")  ;
                            String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                            JOptionPane.showMessageDialog ( null , result2);
                    }catch(UnsupportedEncodingException e){

                    }
                    break;
            }
         
        
        if ( recordInterted > 0 ) {

            loadContentAfterTransaction ();
        }
        
        

        JOptionPane.showMessageDialog ( null , "Stock Successfuly Updated " );

        jTextField1.setText ( "" );
          loadContent();
        //  resetFields();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        if ( jComboBox1.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add item from Raw_material Master " );
        }             // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    ActionListener selectCategoryAction = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.

            jComboBox1.removeActionListener ( selectUnitAction );

            selectedMaster = jComboBox3.getSelectedItem ().toString ();

            jLabel15.setText ( "0" );
            jLabel16.setText ( "0" );
            jLabel17.setText ( "0" );
            jLabel18.setText ( "0" );

            ResultSet result = null ;

            jComboBox1.removeAllItems ();
            if ( selectedMaster.equals ( "Raw Material" ) ) {
                for ( int i = 0 ; i < raw_materials.size () ; i ++ ) {
                    jComboBox1.addItem ( raw_materials.get ( i )[ 1 ] );
                }
                selectedItemId = Integer.parseInt ( raw_materials.get ( jComboBox1.getSelectedIndex () )[ 0 ] );
                jComboBox1.addActionListener ( selectUnitAction );
                result = DB_Operations.executeSingle ( "SELECT RM_TYPE, (OPENING),(RECEIVED),(USED),(CLOSING), MAX(RMStock_TR_ID) FROM raw_material A INNER JOIN RMStock B ON A.RM_ID = B.RM_ITEM_ID AND A.RM_ID =" + selectedItemId );

            } else {
                for ( int i = 0 ; i < finished_goods.size () ; i ++ ) {
                    jComboBox1.addItem ( finished_goods.get ( i )[ 1 ] );
                }
                selectedItemId = Integer.parseInt ( finished_goods.get ( jComboBox1.getSelectedIndex () )[ 0 ] );
                jComboBox1.addActionListener ( selectUnitAction );
                result = DB_Operations.executeSingle ( "SELECT PART_NAME, (OPENING),(RECEIVED),(USED),(CLOSING) , MAX( FGStock_TR_ID ) FROM finished_goods A INNER JOIN FGStock B ON A.FG_ID = B.FG_ID AND A.FG_ID = " + selectedItemId );

            }
            
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
                        
                        StaticValues.writer.writeExcel (UpdateStockPanel_OLD.class.getSimpleName () , UpdateStockPanel_OLD.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
                }
                
                jLabel15.setText ( OldOPENING + "" );
            
        }
    };


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

}
