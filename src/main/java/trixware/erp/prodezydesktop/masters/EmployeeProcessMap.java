/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import trixware.erp.prodezydesktop.Derived.AutoCompletion;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.examples.HomeScreen;

/**
 *
 * @author Rajesh
 */
public class EmployeeProcessMap extends javax.swing.JPanel {

    public void writeErrorLogs( String className, String filename, String exceptionName, String lineNo, String msg, String datetime ) {
        StaticValues.writer.writeExcel ( className , filename , exceptionName , lineNo , msg, datetime );
    }
    
    String resource = "" , resourceType = "" ;
    
    ResultSet result = null ;
    
    ArrayList<String[]> employee = null;
    ArrayList<String[]> process = null;
    ArrayList<String> rating = null;
    
     DecimalFormat df = new DecimalFormat( "#.##" );
        
     int selectedRecord = 0;
        
    /**
     * Creates new form ProductCustomerMap
     */
    public EmployeeProcessMap () {
        initComponents ();
        
        AutoCompletion.enable(jComboBox1);
        AutoCompletion.enable(jComboBox2);
        AutoCompletion.enable(jComboBox3);
        
        resourceType = "Masters" ;
resource = "EmployeeProcessMap" ;

//try{
//      StaticValues.checkUserRolePermission(resourceType, resourceType);
//   }catch(Exception e){
//      StaticValues.writer.writeExcel (resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//   }
        
   //     df.setMaximumFractionDigits ( 2);
   
        rating = new ArrayList<String>() ;
   
        rating.add("5");
        rating.add("4");
        rating.add("3");
        rating.add("2");
        rating.add("1");
        
        
        try{
            
                result = DB_Operations.getMasters ( "employee" );
            if ( result != null ) {
                employee = new ArrayList<String[]>();
                while ( result.next () ) {
                    employee.add(new String[]{result.getString("EmployeePK"), result.getString("EMP_NAME")});
                    jComboBox2.addItem ( result.getString ( "EMP_NAME" ) );
                }
            } else {
                    jComboBox2.addItem ( "Not Available" );
            }
            
            
            result = DB_Operations.getMasters("process");
            if (result != null) {
                process = new ArrayList<String[]>();
                while (result.next()) {
                    process.add(new String[]{result.getString("PROCESS_ID"), result.getString("PROCESS_NAME")});
                    jComboBox1.addItem(result.getString("PROCESS_NAME"));
                }
            } else {
                jComboBox1.addItem ( "Not Available" );
            }
               
            }catch(SQLException e){
                writeErrorLogs (EmployeeProcessMap.class.getSimpleName () , EmployeeProcessMap.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            }
            
        
        
        
        loadContent();
        
             
        jButton1.setEnabled ( true );
                
    }
    
  
    public void loadContent(){
        
         try{
            
            result = DB_Operations.executeSingle (DB_Operations.SELECT_PR_EMP_MAP );
            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
            jTable1.setModel ( buildTableModel ( result ) );

            jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
            jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
            
         }catch(SQLException e){
             
             System.out.println (e.getMessage ()+"  ERRORRRR");
             writeErrorLogs (EmployeeProcessMap.class.getSimpleName () , EmployeeProcessMap.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
         }
        
    }
    
    public void resetForm(){
        
        jComboBox1.setSelectedIndex ( 0);
        jComboBox2.setSelectedIndex ( 0);
        jComboBox3.setSelectedIndex ( 0);
       
        
        jButton1.setEnabled ( true );
        
    }
    
    public  DefaultTableModel buildTableModel ( ResultSet rs )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        int columnCount = metaData.getColumnCount ();
        for ( int column = 1 ; column <= 4 ; column ++ ) {
            columnNames.add ( metaData.getColumnName ( column ) );
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        while ( rs.next () ) {
            Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 1 ; columnIndex <= 4 ; columnIndex ++ ) {
               
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
        jButton1 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1034, 300));
        setLayout(null);

        jComboBox1.setNextFocusableComponent(jComboBox2);
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        add(jComboBox1);
        jComboBox1.setBounds(130, 20, 220, 35);

        jComboBox2.setNextFocusableComponent(jComboBox3);
        jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox2MouseClicked(evt);
            }
        });
        add(jComboBox2);
        jComboBox2.setBounds(130, 70, 220, 35);

        jButton1.setText("Add");
        jButton1.setEnabled(false);
        jButton1.setOpaque(false);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(130, 180, 90, 40);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5 ( Master )", "4 ( Expert )", "3 ( Proficient )", "2 ( Skilled )", "1 ( Beginner )" }));
        jComboBox3.setNextFocusableComponent(jButton1);
        add(jComboBox3);
        jComboBox3.setBounds(130, 120, 220, 35);

        jLabel1.setText("Process");
        add(jLabel1);
        jLabel1.setBounds(30, 20, 80, 30);

        jLabel2.setText("Employee");
        add(jLabel2);
        jLabel2.setBounds(30, 70, 80, 30);

        jLabel3.setText("Efficiency");
        add(jLabel3);
        jLabel3.setBounds(30, 120, 90, 30);

        jButton2.setText("Close");
        jButton2.setOpaque(false);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(250, 230, 100, 40);

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
        jScrollPane1.setBounds(370, 20, 640, 250);

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 0, 51));
        jLabel26.setText("*");
        add(jLabel26);
        jLabel26.setBounds(20, 130, 20, 10);

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 51));
        jLabel27.setText("*");
        add(jLabel27);
        jLabel27.setBounds(20, 30, 20, 10);

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 0, 51));
        jLabel28.setText("*");
        add(jLabel28);
        jLabel28.setBounds(20, 70, 20, 20);

        jButton3.setText("Update");
        jButton3.setOpaque(false);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(250, 180, 100, 40);

        jButton4.setText("Reset");
        jButton4.setOpaque(false);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(130, 230, 90, 40);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:

//        jButton1.setEnabled ( false );
        
        String timestamp = calculateTimeStamp();
        
        int selectedEmployee = 0, selectedProcess = 0, selectedUOM = -1;
        int sales_rate;
        String selectedAction = ((JButton)evt.getSource ()).getActionCommand();

        try{
            
                        selectedProcess = Integer.parseInt( process.get ( jComboBox1.getSelectedIndex () )[0] );
                        selectedEmployee = Integer.parseInt( employee.get ( jComboBox2.getSelectedIndex () )[0] );
                        selectedUOM = Integer.parseInt( rating.get ( jComboBox3.getSelectedIndex () ) );

                        if(   selectedProcess!=0  && selectedEmployee!=0 && selectedUOM!=-1 ){

                            String result = DB_Operations.insertIntoPR_EMP_MAP (selectedProcess,  selectedEmployee, selectedUOM, calculateTimeStamp (), calculateTimeStamp (), 1  );

                            JOptionPane.showMessageDialog ( null, result);

                            loadContent ();
                            resetForm();

                        }else{

                            JOptionPane.showMessageDialog ( null, "Please fill / select all fields marked with *");
                        }
                
        }catch(Exception e){
            JOptionPane.showMessageDialog ( null, " Error : "+e.getMessage ());
            writeErrorLogs (EmployeeProcessMap.class.getSimpleName () , EmployeeProcessMap.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }
        
        
    }//GEN-LAST:event_jButton1MouseClicked

    public void setValueInComboBox( JComboBox myCombo, ArrayList<String[]> values, String value ){
        
        for( int i=0; i<values.size(); i++){
            if( values.get(i)[1].equals ( value ) ){
                myCombo.setSelectedIndex ( i);
            }
        }          
    }
    
        public void setValueInComboBox2( JComboBox myCombo, ArrayList<String> values, String value ){
        
        for( int i=0; i<values.size(); i++){
            if( values.get(i).equals ( value ) ){
                myCombo.setSelectedIndex ( i);
            }
        }          
    }
    
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
      //  jButton1.setText ( "Update" );
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        // get the selected row index
        int selectedRowIndex = jTable1.getSelectedRow();
        // Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString() )

        selectedRecord = Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString());
                        
        setValueInComboBox ( jComboBox1 , process , model.getValueAt(selectedRowIndex, 1).toString() );
        setValueInComboBox ( jComboBox2 , employee , model.getValueAt(selectedRowIndex, 2).toString() );
        setValueInComboBox2 ( jComboBox3 , rating , model.getValueAt(selectedRowIndex, 3).toString() );
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost
        // TODO add your handling code here:
    //    jButton1.setText ( "Add" );
    }//GEN-LAST:event_jTable1FocusLost

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        HomeScreen.home.removeForms ();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
    if(jComboBox1.getItemCount()==0){               
            JOptionPane.showMessageDialog(null, "<html><size='06'>First add values in process from Processes Master");
    }         // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
    if(jComboBox2.getItemCount()==0){               
            JOptionPane.showMessageDialog(null, "<html><size='06'>First add values in EmployeeName from Employee Master");
    }         // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        jTable1.clearSelection (); 
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
          String timestamp = calculateTimeStamp();
        
        int selectedEmployee = 0, selectedProcess = 0, selectedUOM = -1;
                         
                selectedProcess = Integer.parseInt( process.get ( jComboBox1.getSelectedIndex () )[0] );
                selectedEmployee = Integer.parseInt( employee.get ( jComboBox2.getSelectedIndex () )[0] );
                selectedUOM = Integer.parseInt( rating.get ( jComboBox3.getSelectedIndex () ) );

                 DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

                // get the selected row index
                int selectedRowIndex = jTable1.getSelectedRow();
                // Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString() )

                selectedRecord = Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString());

                if(   selectedProcess!=0  && selectedEmployee!=0 && selectedUOM!=0 ){

                    String result = DB_Operations.updatePR_EMP_MAP (selectedProcess,  selectedEmployee, selectedUOM, calculateTimeStamp (), 1  , selectedRecord);

                    JOptionPane.showMessageDialog ( null, result);

                    loadContent ();
                    resetForm();

                }else{

                    JOptionPane.showMessageDialog ( null, "Please fill / select all fields marked with *");
                }
                
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    public String calculateTimeStamp(){
        
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
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
