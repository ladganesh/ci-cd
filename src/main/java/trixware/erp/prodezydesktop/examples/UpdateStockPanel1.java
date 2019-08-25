/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import trixware.erp.prodezydesktop.Derived.AutoCompletion;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;

/**
 *
 * @author Rajesh
 */
public class UpdateStockPanel1 extends javax.swing.JPanel {

    
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
                
    /**
     * Creates new form UpdateStockPanel
     */
    public UpdateStockPanel1() {
        
        initComponents();
        
        
        AutoCompletion.enable ( jComboBox1 );
        AutoCompletion.enable ( jComboBox3 );
        AutoCompletion.enable ( jComboBox5 );
         AutoCompletion.enable ( jComboBox2 );
    }

    public void loadContent() {
        
        

     //   jComboBox1.removeAllItems (); 
        try {
            
              
            ResultSet result = DB_Operations.getMasters("raw_material");
            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
            if (result != null) {

                raw_materials = new ArrayList<String[]>();

                while (result.next()) {
                    raw_materials.add(new String[]{result.getString("RM_ID"), result.getString("RM_TYPE")});
                //    jComboBox1.addItem(result.getString("RM_TYPE"));
                }

            } else {
                System.out.println("result set empty");
            }

            
            result = DB_Operations.getMasters("finished_goods");
            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
            if (result != null) {

                finished_goods = new ArrayList<String[]>();

                while (result.next()) {
                    finished_goods.add(new String[]{result.getString("FG_ID"), result.getString("PART_NAME")});
                //    jComboBox1.addItem(result.getString("PART_NAME"));
                }

            } else {
                System.out.println("result set empty");
            }
            
            
             result = DB_Operations.getMasters ( "uom" );
                if ( result != null ) {
                    while ( result.next () ) {
                        jComboBox2.addItem ( result.getString ( "UOM" ) );
                        
                    }
                } else {
                    jComboBox3.addItem ( "Not Available" );
                    
                }
                
        
            selectedMaster = jComboBox3.getSelectedItem ().toString ();
        
            jComboBox1.removeAllItems ();
            
            if(selectedMaster.equals ( "Raw Material" )){
                for(int i = 0 ; i<raw_materials.size (); i++){
                    jComboBox1.addItem( raw_materials.get(i)[1]);
                }
                selectedItemId = Integer.parseInt(raw_materials.get( jComboBox1.getSelectedIndex() )[0]);
            }else{
                 for(int i = 0 ; i<finished_goods.size (); i++){
                    jComboBox1.addItem( finished_goods.get(i)[1]);
                }
                selectedItemId = Integer.parseInt(finished_goods.get( jComboBox1.getSelectedIndex() )[0]);
            }
            result = DB_Operations.executeSingle("SELECT RM_TYPE, (OPENING),(RECEIVED),(USED),(CLOSING) FROM raw_material A INNER JOIN RMStock B ON A.RM_ID = B.RMI_ID AND A.RM_ID ="+selectedItemId+" WHERE   RMStock_TR_ID = (SELECT MAX(RMStock_TR_ID)  FROM RMStock)" );
            if (result != null) {

                
                System.out.println( result.getString ( 2)  +"    "+ result.getString ( 3)  +"    "+result.getString ( 4)  +"    "+result.getString (5) );
                
                     OldOPENING = Integer.parseInt( result.getString ( 5) );
                     jLabel15.setText ( OldOPENING+"" );
              
            }else{
                
                System.out.println( "No result to show" );
            }
            
           jComboBox1.addActionListener ( a );

           
           
           
           
           result.close();
           
        } catch (SQLException e) {
            System.out.println( "Error No result to show " +e.getMessage());
            StaticValues.writer.writeExcel ( UpdateStockPanel1.class.getSimpleName () , UpdateStockPanel1.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
    }

    
    public void loadContentAfterTransaction() {

   //     jComboBox1.removeAllItems (); 
        try {
            
           ResultSet result = null ;     
        
            if(selectedMaster.equals ( "Raw Material" )){
                selectedItemId = Integer.parseInt(raw_materials.get( jComboBox1.getSelectedIndex() )[0]);
            }else{
                selectedItemId = Integer.parseInt(finished_goods.get( jComboBox1.getSelectedIndex() )[0]);
            }
            
            if(selectedMaster.equals ( "Raw Material" ))
                result = DB_Operations.executeSingle("SELECT RM_TYPE, (OPENING),(RECEIVED),(USED),(CLOSING) FROM raw_material A INNER JOIN RMStock B ON A.RM_ID = B.RMI_ID AND A.RM_ID ="+selectedItemId+"   WHERE   RMStock_TR_ID = (SELECT MAX(RMStock_TR_ID)  FROM RMStock)" );
            else if(selectedMaster.equals ( "Finished Goods" ))
            result = DB_Operations.executeSingle("SELECT PART_NAME, (OPENING),(RECEIVED),(USED),(CLOSING) FROM finished_goods A INNER JOIN FGStock B ON A.FG_ID = B.FG_ID AND A.FG_ID = "+selectedItemId+"    WHERE   FGStock_TR_ID = (SELECT MAX(FGStock_TR_ID)  FROM FGStock)" );
            
            if (result != null) {

                     
                    CLOSING = Integer.parseInt( result.getString ( 5) );
                     jLabel18.setText ( CLOSING+"" );
                     
                     jLabel15.setText ( result.getString ( 2)  ); 
              
                     
                     if (selectedTransaction.equals("Inward")) {
            
                          jLabel16.setText ( result.getString ( 3) );
                          jLabel17.setText ( "0" ); 
                        
                    } else if (selectedTransaction.equals("Outward")) {

                        jLabel17.setText ( result.getString ( 4 ) );
                        jLabel16.setText ( "0" ); 
                    }
                     
                     
            }else{
                
                System.out.println( "No result to show" );
            }
            
           jComboBox1.addActionListener ( a );

           
           result.close();
           
        } catch (SQLException e) {
            System.out.println( "Error No result to show " +e.getMessage());
            StaticValues.writer.writeExcel ( UpdateStockPanel1.class.getSimpleName () , UpdateStockPanel1.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.c
     */
    @SuppressWarnings("unchecked")
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
        jComboBox2 = new javax.swing.JComboBox<>();

        setMinimumSize(new java.awt.Dimension(510, 530));
        setPreferredSize(new java.awt.Dimension(600, 700));
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
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox3);
        jComboBox3.setBounds(220, 70, 230, 30);
        jPanel1.add(jLabel10);
        jLabel10.setBounds(297, 84, 112, 0);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Current Stocks"));
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
        jTextField1.setNextFocusableComponent(jComboBox2);
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

        jComboBox2.setNextFocusableComponent(jButton1);
        jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox2MouseClicked(evt);
            }
        });
        jPanel1.add(jComboBox2);
        jComboBox2.setBounds(350, 220, 100, 30);

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

        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMMyyyy_HH_mm_a");
        String strDate2 = sdf2.format(c2.getTime());

        
        selectedMaster = jComboBox3.getSelectedItem().toString();
                
        String MASTERNAME = selectedMaster;
        
        
        if(selectedMaster.equals ( "Raw Material" )){
                selectedItemId = Integer.parseInt(raw_materials.get( jComboBox1.getSelectedIndex() )[0]);
            }else{
                selectedItemId = Integer.parseInt(finished_goods.get( jComboBox1.getSelectedIndex() )[0]);
            }

        int ItemID = selectedItemId;// get the id of the master entry selected in the
        int ID = 0;
               
        ResultSet result  = null ;
        
        if(selectedMaster.equals ( "Finished Goods" ))
            result = DB_Operations.executeSingle("SELECT PART_NAME, (OPENING),(RECEIVED),(USED),(CLOSING) FROM finished_goods A INNER JOIN FGStock B ON A.FG_ID = B.FG_ID AND A.FG_ID = "+selectedItemId+"    WHERE   FGStock_TR_ID = (SELECT MAX(FGStock_TR_ID)  FROM FGStock)" );
        else
            result = DB_Operations.executeSingle("SELECT RM_TYPE, (OPENING),(RECEIVED),(USED),(CLOSING) FROM raw_material A INNER JOIN RMStock B ON A.RM_ID = B.RMI_ID AND A.RM_ID ="+selectedItemId+" WHERE   RMStock_TR_ID = (SELECT MAX(RMStock_TR_ID)  FROM RMStock)" );

        try{
            if (result != null  && result.isBeforeFirst ()) {
                
                    recordExist = true;
                
                    OPENING = Integer.parseInt( result.getString ( 5) );
                    RECEIVED = Integer.parseInt( result.getString ( 3) );
                    USED =  Integer.parseInt( result.getString ( 4) );
                    OldCLOSING =  Integer.parseInt( result.getString ( 5) );
                    
            }else{
                System.out.println( "No result to show" );
                    recordExist = false;
                    OPENING = 0;
                    RECEIVED = 0;
                    USED =  0;
                    CLOSING =  0;
            }
            
             result.close ();
           
        }catch(SQLException e){
            System.out.println( "Error Occured !"  );
            StaticValues.writer.writeExcel ( UpdateStockPanel1.class.getSimpleName () , UpdateStockPanel1.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        selectedTransaction = jComboBox5.getSelectedItem().toString();
            OPENING = OldCLOSING ;
        if (selectedTransaction.equals("Inward")) {
            
            RECEIVED = Integer.parseInt(jTextField1.getText());
            USED = 0;
            CLOSING  = OPENING + RECEIVED ;
        } else if (selectedTransaction.equals("Outward")) {
           
            USED       = Integer.parseInt(jTextField1.getText());
            RECEIVED = 0 ;
            CLOSING  = OPENING  - USED ;
        }

        
        String CREATED_ON = strDate2;
        String EDITED_ON = "-";
        String EDITED_BY = Proman_User.getUsername();

        int recordInterted = 0;
                
        recordInterted = DB_Operations.insert_STOCK_TRN(  MASTERNAME, ID, ItemID, OPENING, RECEIVED, USED, CLOSING, CREATED_ON, EDITED_ON, EDITED_BY  );
       
        if(recordInterted >0){
            
            loadContentAfterTransaction ();
        }
       
        JOptionPane.showMessageDialog(null, "Stock Successfuly Updated "  );
        
        jTextField1.setText ( "");
        //  loadContent();
        //  resetFields();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        
        jComboBox1.removeActionListener ( a );
        
        selectedMaster = jComboBox3.getSelectedItem().toString();
        
        jComboBox1.removeAllItems();

        try {
            ResultSet result = null;
            
            jComboBox1.removeActionListener ( a );

            
              if(selectedMaster.equals ( "Raw Material" )){
                for(int i = 0 ; i<raw_materials.size (); i++){
                    jComboBox1.addItem( raw_materials.get(i)[1]);
                }
                selectedItemId = Integer.parseInt(raw_materials.get( jComboBox1.getSelectedIndex() )[0]);
                
                result = DB_Operations.executeSingle("SELECT RM_TYPE, (OPENING),(RECEIVED),(USED),(CLOSING) FROM raw_material A INNER JOIN RMStock B ON A.RM_ID = B.RMI_ID AND A.RM_ID ="+selectedItemId+" WHERE   RMStock_TR_ID = (SELECT MAX(RMStock_TR_ID)  FROM RMStock)" );
                
            }else{
                 for(int i = 0 ; i<finished_goods.size (); i++){
                    jComboBox1.addItem( finished_goods.get(i)[1]);
                }
                selectedItemId = Integer.parseInt(finished_goods.get( jComboBox1.getSelectedIndex() )[0]);
                
                result = DB_Operations.executeSingle("SELECT PART_NAME, (OPENING),(RECEIVED),(USED),(CLOSING) FROM finished_goods A INNER JOIN FGStock B ON A.FG_ID = B.FG_ID AND A.FG_ID = "+selectedItemId+"    WHERE   FGStock_TR_ID = (SELECT MAX(FGStock_TR_ID)  FROM FGStock)" );
            }    
           
           jComboBox1.addActionListener ( a );
            
           
            if (result != null) {
                     OldOPENING = Integer.parseInt( result.getString ( 5) );
                     jLabel15.setText ( OldOPENING+"" );
                    
//                    OPENING = Integer.parseInt(  result.getString ( 2) );
//                    RECEIVED = Integer.parseInt( result.getString ( 3) );
//                    USED =  Integer.parseInt(       result.getString ( 4) );
//                    CLOSING =  Integer.parseInt( result.getString ( 5) );
                    
            }else{
                System.out.println( "No result to show" );
            }
          
            result.close() ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            StaticValues.writer.writeExcel ( UpdateStockPanel1.class.getSimpleName () , UpdateStockPanel1.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
if(jComboBox1.getItemCount()==0){
    JOptionPane.showMessageDialog(null, "<html><size='06'>First add item from Raw_material Master ");
}             // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
if(jComboBox2.getItemCount()==0){
    JOptionPane.showMessageDialog(null, "<html><size='06'>First add units from Utility Master ");
}             // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2MouseClicked

    ActionListener a = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
        //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        
                    jLabel15.setText ( "0" );
                    jLabel16.setText ( "0" );
                    jLabel17.setText ( "0" );
                    jLabel18.setText ( "0" );
           
                     try {
            
            ResultSet result;

                    
             jComboBox1.removeAllItems ();
        if(selectedMaster.equals ( "Raw Material" )){
                for(int i = 0 ; i<raw_materials.size (); i++){
                    jComboBox1.addItem( raw_materials.get(i)[1]);
                }
                selectedItemId = Integer.parseInt(raw_materials.get( jComboBox1.getSelectedIndex() )[0]);
                
                result = DB_Operations.executeSingle("SELECT RM_TYPE, (OPENING),(RECEIVED),(USED),(CLOSING) FROM raw_material A INNER JOIN RMStock B ON A.RM_ID = B.RMI_ID AND A.RM_ID ="+selectedItemId+" WHERE   RMStock_TR_ID = (SELECT MAX(RMStock_TR_ID)  FROM RMStock)" );
                
            }else{
                 for(int i = 0 ; i<finished_goods.size (); i++){
                    jComboBox1.addItem( finished_goods.get(i)[1]);
                }
                selectedItemId = Integer.parseInt(finished_goods.get( jComboBox1.getSelectedIndex() )[0]);
                
                result = DB_Operations.executeSingle("SELECT PART_NAME, (OPENING),(RECEIVED),(USED),(CLOSING) FROM finished_goods A INNER JOIN FGStock B ON A.FG_ID = B.FG_ID AND A.FG_ID = "+selectedItemId+"    WHERE   FGStock_TR_ID = (SELECT MAX(FGStock_TR_ID)  FROM FGStock)" );
            }    
        
       
                
            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
            if (result != null) {
                   
                System.out.println( result.getString ( 2)  +"    "+ result.getString ( 3)  +"    "+result.getString ( 4)  +"    "+result.getString (5) );
                
                     OldOPENING = Integer.parseInt( result.getString ( 5) );
                     jLabel15.setText ( OldOPENING+"" );
              
                    
            }else{
                System.out.println( "No result to show" );
            }
      
            result.close();
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
            StaticValues.writer.writeExcel ( UpdateStockPanel1.class.getSimpleName () , UpdateStockPanel1.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
        }
    };
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

   
}
