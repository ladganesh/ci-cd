/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.StaticValues;

/**
 *
 * @author Rajesh
 */
public class BillOfMaterialUI extends javax.swing.JPanel {

    ArrayList<String> RMIDS;

     ArrayList<ProductDR> products = null;
     ProductDR prdr = null;
     
     String resource = "" , resourceType = "" ;
     
     public void writeErrorLogs( String className, String filename, String exceptionName, String lineNo, String msg, String datetime ) {
        StaticValues.writer.writeExcel ( className , filename , exceptionName , lineNo , msg, datetime );
    }
     
    /**
     * Creates new form BillOfMaterialUI
     */
    public BillOfMaterialUI(int FG_ID) {
        initComponents();
        SelectedFG_ID = FG_ID;
        
        resourceType = "Masters" ;
        resource = "BillOfMaterialUI" ;

        try{
              StaticValues.checkUserRolePermission(resourceType, resourceType);
           }catch(Exception e){
              StaticValues.writer.writeExcel (resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
           }
        
        
   //     jLabel1.setText(jLabel1.getText() + "   " + String.valueOf(SelectedFG_ID));
        loadContent(  );
        
        
        
    }
    
    public BillOfMaterialUI() {
        initComponents();
      
    }

    public void loadContent(  ) {

        try {

            ResultSet result;
            
            if(jComboBox1.getItemCount() <1){
                result = DB_Operations.getMasters("raw_material");
                RMIDS = new ArrayList<String>();
                while (result.next()) {
                    jComboBox1.addItem(result.getString("RM_TYPE") + " ( " + result.getString("RM_CTG") + " )");
                    RMIDS.add(result.getString("RM_ID"));
                }
            }

             result = DB_Operations.getMasters ( "uom" );
            if ( result != null ) {
                while ( result.next () ) {
                    jComboBox2.addItem ( result.getString ( "UOM" ) ); 
                    jComboBox3.addItem ( result.getString ( "UOM" ) );
                    jComboBox4.addItem ( result.getString ( "UOM" ) );
                }
            } else {
                jComboBox4.addItem ( "Not Available" );
            }
            
            result = DB_Operations.getSingleMasters("bomsingleRM",  Integer.parseInt(RMIDS.get(0)));
           
            if (result != null) {
                jTable2.setModel(buildTableModel(result));
                jTable2.getColumnModel().getColumn(0).setMinWidth(0);
                jTable2.getColumnModel().getColumn(0).setMaxWidth(0);

                jTable2.getColumnModel().getColumn(1).setMinWidth(0);
                jTable2.getColumnModel().getColumn(1).setMaxWidth(0);
            }
            
            
            String queryOne = "SELECT FG_ID, PART_NAME FROM finished_goods";
            
            ResultSet rs = null;

        try {
            rs = DB_Operations.executeSingle ( queryOne );
            //  products = new ArrayList<HashMap<String, String>> ();
            products = new ArrayList<ProductDR> ();
            while ( rs.next () ) {
                //   entity =  new  HashMap<String, String>();
                prdr = new ProductDR ();
                //    entity.put ( rs.getString(1) /*  String.valueOf( rs.getInt(1) ) */, rs.getString(2)) ;
                prdr.FG_ID = Integer.parseInt ( rs.getString ( 1 ) );
                prdr.FG_PART_NO = rs.getString ( 2 );
                products.add ( prdr );
                jComboBox5.addItem ( rs.getString ( 2 ) );
            }

            rs.close ();
        } catch ( Exception e ) {
            System.out.println ( " Error 1 " + e.getMessage () );
            writeErrorLogs (BillOfMaterialUI.class.getSimpleName () , BillOfMaterialUI.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }
            
            
        } catch (SQLException e) {
            writeErrorLogs (BillOfMaterialUI.class.getSimpleName () , BillOfMaterialUI.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }catch (IndexOutOfBoundsException e) {
            writeErrorLogs (BillOfMaterialUI.class.getSimpleName () , BillOfMaterialUI.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= 6; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            
            for (int columnIndex = 1; columnIndex <= 6; columnIndex++) {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jComboBox4 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jComboBox5 = new javax.swing.JComboBox<>();

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

        setMinimumSize(new java.awt.Dimension(393, 474));
        setOpaque(false);
        setLayout(null);

        jLabel1.setText("BOM for FG ");
        add(jLabel1);
        jLabel1.setBounds(30, 10, 130, 20);
        add(jLabel2);
        jLabel2.setBounds(150, 10, 210, 0);

        jLabel3.setText("Select RM");
        add(jLabel3);
        jLabel3.setBounds(30, 50, 70, 20);

        jComboBox1.setNextFocusableComponent(jTextField1);
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
        jComboBox1.setBounds(170, 50, 190, 26);

        jLabel4.setText("Enter Net Weight");
        add(jLabel4);
        jLabel4.setBounds(30, 90, 110, 20);

        jTextField1.setText("0.0");
        jTextField1.setNextFocusableComponent(jComboBox2);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(170, 90, 110, 24);
        add(jComboBox2);
        jComboBox2.setBounds(290, 90, 70, 26);

        jLabel5.setText("Enter Gross Weight");
        add(jLabel5);
        jLabel5.setBounds(30, 130, 110, 20);

        jTextField2.setText("0.0");
        jTextField2.setNextFocusableComponent(jComboBox3);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });
        add(jTextField2);
        jTextField2.setBounds(170, 130, 110, 24);

        jComboBox3.setNextFocusableComponent(jTextField3);
        add(jComboBox3);
        jComboBox3.setBounds(290, 130, 70, 26);

        jLabel6.setText("Total Weight");
        add(jLabel6);
        jLabel6.setBounds(30, 170, 110, 20);

        jTextField3.setText("0.0");
        jTextField3.setNextFocusableComponent(jComboBox4);
        add(jTextField3);
        jTextField3.setBounds(170, 170, 110, 24);

        jComboBox4.setNextFocusableComponent(jButton2);
        add(jComboBox4);
        jComboBox4.setBounds(290, 170, 70, 26);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Save & Close");
        jButton1.setNextFocusableComponent(jButton4);
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(30, 260, 160, 32);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Save & Add More");
        jButton2.setNextFocusableComponent(jButton3);
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(30, 220, 160, 32);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Edit Selected");
        jButton3.setNextFocusableComponent(jButton1);
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(200, 220, 160, 32);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("Cancel");
        jButton4.setNextFocusableComponent(jTable2);
        jButton4.setOpaque(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(200, 260, 160, 32);

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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        add(jScrollPane2);
        jScrollPane2.setBounds(30, 300, 330, 150);

        jComboBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(jComboBox5);
        jComboBox5.setBounds(170, 10, 190, 26);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        int FG_ID = SelectedFG_ID;
        int RM_ID = Integer.parseInt(RMIDS.get(jComboBox1.getSelectedIndex()));
        double NT_WT = Double.parseDouble(jTextField1.getText());
        double GS_WT = Double.parseDouble(jTextField2.getText());
        double TOTAL_WT = Double.parseDouble(jTextField3.getText());

        String result = DB_Operations.insertIntoBOMMaster(FG_ID, RM_ID, NT_WT, GS_WT, TOTAL_WT, "CREATED_ON", "EDITED_BY", "EDITED_ON");

        JOptionPane.showMessageDialog(null, "" + result);

        loadContent(  );

        resetFields();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:

       
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:

        ArrayList<String> values = new ArrayList<String>();
        
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        int columnCount = 0;
        
        int selectedRowIndex = jTable2.getSelectedRow();
        
        try {
            selectedBOMId = Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString());
            selectedRMID = Integer.parseInt(RMIDS.get(jComboBox1.getSelectedIndex()));
            ResultSet result = DB_Operations.getSingleMasters("bomsingleBOM", selectedBOMId);
            if (result != null) {
                ResultSetMetaData metaData = result.getMetaData();
                columnCount = metaData.getColumnCount();
                while (result.next()) {
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        if (columnIndex < columnCount - 2) {
                            values.add(String.valueOf(result.getObject(columnIndex)));
                        }
                    }
                }
            }
        } catch (SQLException e) {

            System.out.println("Error " + e.getMessage());
            writeErrorLogs (BillOfMaterialUI.class.getSimpleName () , BillOfMaterialUI.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }

        jTextField1.setText(values.get(3).toString());
        jTextField2.setText(values.get(4).toString());
        jTextField3.setText(values.get(5).toString());
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        double NT_WT = Double.parseDouble(jTextField1.getText());
        double GS_WT = Double.parseDouble(jTextField2.getText());
        double TOTAL_WT = Double.parseDouble(jTextField3.getText());

        //System.out.println(selectedRMID + " " + selectedBOMId + "   " + SelectedFG_ID);

        String result = DB_Operations.updateBOMMaster(NT_WT, GS_WT, TOTAL_WT, "EDITED_BY", "EDITED_ON", selectedRMID, selectedBOMId, SelectedFG_ID);
        JOptionPane.showMessageDialog(null, "" + selectedRMID+" "+ selectedBOMId +" "+ SelectedFG_ID);
        loadContent( );
        resetFields();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
            
        try{
            ResultSet   result = DB_Operations.getSingleMasters("bomsingleRM",  Integer.parseInt(RMIDS.get(  jComboBox1.getSelectedIndex() )));
           
            if (result != null) {
                jTable2.setModel(buildTableModel(result));
                jTable2.getColumnModel().getColumn(0).setMinWidth(0);
                jTable2.getColumnModel().getColumn(0).setMaxWidth(0);

                jTable2.getColumnModel().getColumn(1).setMinWidth(0);
                jTable2.getColumnModel().getColumn(1).setMaxWidth(0);

            }
        }catch(IndexOutOfBoundsException e ){
            writeErrorLogs (BillOfMaterialUI.class.getSimpleName () , BillOfMaterialUI.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }catch(SQLException e ){
            writeErrorLogs (BillOfMaterialUI.class.getSimpleName () , BillOfMaterialUI.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        FinishedGoodMaster_old.bom.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
         double NT_WT = Double.parseDouble(jTextField1.getText());
        double GS_WT = Double.parseDouble(jTextField2.getText());
        jTextField3.setText(String.valueOf(NT_WT + GS_WT));
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
         double NT_WT = Double.parseDouble(jTextField1.getText());
        double GS_WT = Double.parseDouble(jTextField2.getText());
        jTextField3.setText(String.valueOf(NT_WT + GS_WT));
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        int FG_ID = SelectedFG_ID;
        int RM_ID = Integer.parseInt(RMIDS.get(jComboBox1.getSelectedIndex()));
        double NT_WT = Double.parseDouble(jTextField1.getText());
        double GS_WT = Double.parseDouble(jTextField2.getText());
        double TOTAL_WT = Double.parseDouble(jTextField3.getText());

        String result = DB_Operations.insertIntoBOMMaster(FG_ID, RM_ID, NT_WT, GS_WT, TOTAL_WT, "CREATED_ON", "EDITED_BY", "EDITED_ON");
        JOptionPane.showMessageDialog(null, "" + result);
        FinishedGoodMaster_old.bom.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    public void resetFields() {

        jComboBox1.setSelectedItem(0);
        jTextField1.getText();
        jTextField2.getText();

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

    public static int selectedBOMId = -1;
    public static int selectedRMID = -1;
    public static int SelectedFG_ID = -1;
}
