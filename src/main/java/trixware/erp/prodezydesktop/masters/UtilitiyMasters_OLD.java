/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONObject;
import org.json.JSONTokener;
import trixware.erp.prodezydesktop.Model.SelectedObjects;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class UtilitiyMasters_OLD extends javax.swing.JPanel {

    String resource = "" , resourceType = "" ;
    
    /**
     * Creates new form UtilitiyMasters
     */
    public UtilitiyMasters_OLD () {
        initComponents ();
        //  this.setDefaultButton(jButton9);

        resourceType = "Masters" ;
	resource = "UtilitiyMasters" ;

	try{
      		StaticValues.checkUserRolePermission(resourceType, resourceType);
	   }catch(Exception e){
      		StaticValues.writer.writeExcel (resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
   	   }
        
    }

    public void loadContent ( String val ) {

        ResultSet result = null;
        try {

            SelectedObjects ob;

            //System.out.println( val );
            result = DB_Operations.getMasters ( val );

            if ( result != null ) {
                jTable2.setModel ( buildTableModel ( result , val ) );

                jTable2.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                jTable2.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
            }
//      
            System.out.println("test");
            result.close ();
        } catch ( SQLException e ) {

            try {
                result.close ();
            } catch ( SQLException ex ) {
                StaticValues.writer.writeExcel ( UtilitiyMasters.class.getSimpleName () , UtilitiyMasters.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

            System.out.println ( "" + e.getMessage () );
            StaticValues.writer.writeExcel ( UtilitiyMasters.class.getSimpleName () , UtilitiyMasters.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

    }

    public static DefaultTableModel buildTableModel ( ResultSet rs , String column_name )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();

        columnNames.add ( metaData.getColumnName ( 1 ) );

        switch ( column_name ) {
            case "grade":
                columnNames.add ( "Grades" );
                break;

            case "designation":
                columnNames.add ( "Designations" );
                break;
            case "salaryband":
                columnNames.add ( "Salary Band" );
                break;
            case "employeenature":
                columnNames.add ( "Nature of Employement" );
                break;
            case "uom":
                columnNames.add ( "Unit of Measurements" );
                break;

            case "pack_size":
                columnNames.add ( "Pack Size" );
                break;

            case "raw_material_type":
                columnNames.add ( "Raw Material Type" );
                break;
            case "category":
                columnNames.add ( "Category" );
                break;
            case "segment":
                columnNames.add ( "Segment" );
                break;
            case "industry":
                columnNames.add ( "Industry" );
                break;

            case "rejectionReason":
                columnNames.add ( "RejectionReasons" );
                break;

            case "documentType":
                columnNames.add ( "DocumentType" );
                break;

            case "departments":
                columnNames.add ( "Departments" );
                break;

            case "resources":
                columnNames.add ( "Resource" );
                break;
                
                case "roles":
                columnNames.add ( "User Roles" );
                break;

        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        while ( rs.next () ) {
            Vector<Object> vector = new Vector<Object> ();

            vector.add ( rs.getObject ( 1 ) );
            vector.add ( rs.getObject ( 2 ) );

            //     System.out.println(rs.getObject(2) );
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTextField9 = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jList9 = new javax.swing.JList<>();
        jTextField6 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();

        jButton15.setText("Add");

        jButton16.setText("Edit");

        jList9.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "No Items Found" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane9.setViewportView(jList9);

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(286, 356));
        setLayout(null);

        jTextField6.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        add(jTextField6);
        jTextField6.setBounds(10, 50, 270, 30);

        jButton9.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jButton9.setText("Add");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        add(jButton9);
        jButton9.setBounds(110, 90, 80, 30);

        jButton10.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jButton10.setText("Edit");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        add(jButton10);
        jButton10.setBounds(200, 90, 80, 30);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Grades", "Designations", "Salary Band", "Nature of Employment", "Unit of Measurement", "Pack Size", "Raw Material Type", "Category", "Segment", "Industry", "Rejection Reason", "Document Types", "Departments", "Resources", "User Roles" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        add(jComboBox1);
        jComboBox1.setBounds(10, 0, 270, 30);

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
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
        jTable2.setRowHeight(20);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        add(jScrollPane2);
        jScrollPane2.setBounds(10, 140, 270, 210);

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 0, 51));
        jLabel26.setText("*");
        add(jLabel26);
        jLabel26.setBounds(0, 50, 20, 10);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        addTODB ();
    }//GEN-LAST:event_jButton9ActionPerformed

    public void addTODB () {

        String selectedMaster = jComboBox1.getSelectedItem ().toString ();

        String value = jTextField6.getText ().trim ();
        String res = "";

        String addEmpAPICall = null;
        String result2 =  null ;
        JSONObject responseObject = null ;
        
        if (  ! value.equalsIgnoreCase ( "" ) ) {

            switch ( selectedMaster ) {

                case "Grades":
                    res = DB_Operations.insertIntoGradesMaster ( value );
                    showDataInParticularList ( "grade" , "GradeName" );
                    
                     addEmpAPICall = "gradeadd?GradeName="+value;
                     result2 =  WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader ()   , "");
                     responseObject = (JSONObject) new JSONTokener(result2).nextValue() ;
                    JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "message").getJSONObject ("success").toString()  );
                    
                    break;

                case "Designations":
                    res = DB_Operations.insertIntoDesignationsMaster ( value );
                    showDataInParticularList ( "designation" , "DesgTitle" );
                    
                     addEmpAPICall = "designationsadd?DesgTitle="+value;
                     result2 =  WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader()   , "");
                     responseObject = (JSONObject) new JSONTokener(result2).nextValue() ;
                     JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "message").getJSONObject ("success").toString()  );
                    
                    break;

                case "Salary Band":
                    res = DB_Operations.insertIntoSalaryMaster ( value );
                    showDataInParticularList ( "salaryband" , "SB_RANGE" );
                    
                    addEmpAPICall = "salarybandadd?SB_RANGE="+value;
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader ()   , "");
                    responseObject = (JSONObject) new JSONTokener(result2).nextValue() ;
                    JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "message").getJSONObject ("success").toString()  );
                    
                    break;

                case "Nature of Employment":
                    res = DB_Operations.insertIntoEmpNatureMaster ( value );
                    showDataInParticularList ( "employeenature" , "NATURE" );
                    
                    
                    
                    
                    break;

                case "Unit of Measurement":
                    res = DB_Operations.insertIntoUOMMaster ( value );
                    showDataInParticularList ( "uom" , "UOM" );
                    
                    addEmpAPICall = "unitmeasadd?UOM="+value;
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader ()   , "");
                    responseObject = (JSONObject) new JSONTokener(result2).nextValue() ;
                    JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "message").getJSONObject ("success").toString()  );
                    
                    break;

                case "Pack Size":
                    res = DB_Operations.insertUtilitiesMaster ( "PackSize" , value );
                    showDataInParticularList ( "pack_size" , "PackSize" );
                    
                    addEmpAPICall = "packsizeadd?UOM="+value;
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader ()   , "");
                    responseObject = (JSONObject) new JSONTokener(result2).nextValue() ;
                    JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "message").getJSONObject ("success").toString()  );
                    
                    
                    break;

                case "Raw Material Type":
                    res = DB_Operations.insertUtilitiesMaster ( "RawMaterial" , value );
                    showDataInParticularList ( "raw_material_type" , "RawMaterialType" );
                    
                    addEmpAPICall = "rmtadd?RawMaterial="+value;
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader ()   , "");
                    responseObject = (JSONObject) new JSONTokener(result2).nextValue() ;
                    JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "message").getJSONObject ("success").toString()  );
                    
                    break;

                case "Category":
                    res = DB_Operations.insertUtilitiesMaster ( "Category" , value );
                    showDataInParticularList ( "category" , "Category" );
                    
                    addEmpAPICall = "categoryadd?Category="+value;
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader ()   , "");
                    responseObject = (JSONObject) new JSONTokener(result2).nextValue() ;
                    JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "message").getJSONObject ("success").toString()  );
                    
                    break;

                case "Segment":
                    res = DB_Operations.insertUtilitiesMaster ( "Segment" , value );
                    showDataInParticularList ( "segment" , "Segment" );
                    
                    
                    addEmpAPICall = "segmentadd?Category="+value;
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader ()   , "");
                    responseObject = (JSONObject) new JSONTokener(result2).nextValue() ;
                    JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "message").getJSONObject ("success").toString()  );
                    
                    break;

                case "Industry":
                    res = DB_Operations.insertUtilitiesMaster ( "Industry" , value );
                    showDataInParticularList ( "industry" , "Industry" );
                    
                    addEmpAPICall = "industryadd?Industry="+value;
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader ()   , "");
                    responseObject = (JSONObject) new JSONTokener(result2).nextValue() ;
                    JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "message").getJSONObject ("success").toString()  );
                    
                    break;

                case "Rejection Reason":
                    res = DB_Operations.insertUtilitiesMaster ( "RejectionReason" , value );
                    showDataInParticularList ( "rejectionReason" , "RejectionReasons" );
                    
                    addEmpAPICall = "rejreasonsadd?RR_DESC="+value;
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader ()   , "");
                    responseObject = (JSONObject) new JSONTokener(result2).nextValue() ;
                    JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "message").getJSONObject ("success").toString()  );
                    
                    break;

                case "Document Types":
                    res = DB_Operations.insertUtilitiesMaster ( "DocumentType" , value );
                    showDataInParticularList ( "documentType" , "DocumentType" );
                    
                        
                    
                    break;

                case "Departments":
                    res = DB_Operations.insertUtilitiesMaster ( "Departments" , value );
                    showDataInParticularList ( "departments" , "Departments" );
                    
                    
                    addEmpAPICall = "departmentadd?DNAME="+value;
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader ()   , "");
                    responseObject = (JSONObject) new JSONTokener(result2).nextValue() ;
                    JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "message").getJSONObject ("success").toString()  );
                    
                    break;

                case "Resources":
                    res = DB_Operations.insertUtilitiesMaster ( "Resources" , value );
                    showDataInParticularList ( "resources" , "Resources" );
                    
                    addEmpAPICall = "resourceadd?RSNAME="+value;
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader ()   , "");
                    responseObject = (JSONObject) new JSONTokener(result2).nextValue() ;
                    JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "message").getJSONObject ("success").toString()  );
                    
                    break;
                    
                    case "User Roles":
                    res = DB_Operations.insertUtilitiesMaster ( "User Roles" , value );
                    showDataInParticularList ( "roles" , "User Roles" );
                    
                    addEmpAPICall = "userrolesadd?RoleName="+value;
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader ()   , "");
                    responseObject = (JSONObject) new JSONTokener(result2).nextValue() ;
                    JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "message").getJSONObject ("success").toString()  );
                    
                    break;                    
            }
            
        } else {
            JOptionPane.showMessageDialog ( null , "Please enter valid text" );
        }

        jTextField6.setText ( "" );
        jTextField6.requestFocus ();
    }

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:

        String selectedMaster = jComboBox1.getSelectedItem ().toString ();
        String grade;
        grade = jTextField6.getText ();
        String res = "";

        switch ( selectedMaster ) {

            case "Grades":

                res = DB_Operations.updateIntoGradesMaster ( grade , selectedRecordId );
                showDataInParticularList ( "grade" , "GradeName" );
                break;

            case "Designations":
                res = DB_Operations.updateIntoDesignationsMaster ( grade , selectedRecordId );

                showDataInParticularList ( "designation" , "DesgTitle" );
                break;

            case "Salary Band":
                res = DB_Operations.updateIntoSalaryMaster ( grade , selectedRecordId );
                showDataInParticularList ( "salaryband" , "SB_RANGE" );

                break;

            case "Nature of Employment":
                res = DB_Operations.updateIntoEmpNatureMaster ( grade , selectedRecordId );
                showDataInParticularList ( "employeenature" , "NATURE" );

                break;

            case "Unit of Measurement":
                res = DB_Operations.updateIntoUOMMaster ( grade , selectedRecordId );
                showDataInParticularList ( "uom" , "UOM" );
                break;

            case "Pack Size":

                res = DB_Operations.updateUtilitiesMaster ( "PackSize" , grade , selectedRecordId );
                showDataInParticularList ( "pack_size" , "PackSize" );
                break;

            case "Raw Material Type":
                res = DB_Operations.updateUtilitiesMaster ( "RawMaterial" , grade , selectedRecordId );

                //   System.out.println(   grade + "  " + selectedRecordId );
                showDataInParticularList ( "raw_material_type" , "RawMaterial" );
                break;

            case "Category":
                res = DB_Operations.updateUtilitiesMaster ( "Category" , grade , selectedRecordId );
                showDataInParticularList ( "category" , "Category" );

                break;

            case "Segment":
                res = DB_Operations.updateUtilitiesMaster ( "Segment" , grade , selectedRecordId );
                showDataInParticularList ( "segment" , "Segment" );

                break;

            case "Industry":
                res = DB_Operations.updateUtilitiesMaster ( "Industry" , grade , selectedRecordId );
                showDataInParticularList ( "industry" , "Industry" );
                break;

            case "Departments":
                res = DB_Operations.updateUtilitiesMaster ( "Departments" , grade , selectedRecordId );
                showDataInParticularList ( "departments" , "Departments" );
                break;

            case "Resources":
                res = DB_Operations.updateUtilitiesMaster ( "Resources" , grade , selectedRecordId );
                showDataInParticularList ( "resources" , "Resources" );
                break;
                
            case "User Roles":
                res = DB_Operations.updateUtilitiesMaster ( "User Roles" , grade , selectedRecordId );
                showDataInParticularList ( "roles" , "User Roles" );
                break;
        }

        jTextField6.setText ( "" );
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:

        String selectedMaster = jComboBox1.getSelectedItem ().toString ();
        String res = "";

        switch ( selectedMaster ) {

            case "Grades":
                showDataInParticularList ( "grade" , "GradeName" );
                break;

            case "Designations":
                showDataInParticularList ( "designation" , "DesgTitle" );
                break;

            case "Salary Band":
                showDataInParticularList ( "salaryband" , "SB_RANGE" );
                break;

            case "Nature of Employment":
                showDataInParticularList ( "employeenature" , "NATURE" );
                break;

            case "Unit of Measurement":
                showDataInParticularList ( "uom" , "UOM" );
                break;

            case "Pack Size":
                showDataInParticularList ( "pack_size" , "PackSize" );
                break;

            case "Raw Material Type":
                showDataInParticularList ( "raw_material_type" , "RawMaterial" );
                break;

            case "Category":
                showDataInParticularList ( "category" , "Category" );
                break;

            case "Segment":
                showDataInParticularList ( "segment" , "Segment" );
                break;

            case "Industry":
                showDataInParticularList ( "industry" , "Industry" );
                break;

            case "Rejection Reason":
                showDataInParticularList ( "rejectionReason" , "RejectionReasons" );
                break;

            case "Document Types":
                showDataInParticularList ( "documentType" , "DocumentType" );
                break;

            case "Departments":
                showDataInParticularList ( "departments" , "Departments" );
                break;

            case "Resources":
                showDataInParticularList ( "resources" , "Resources" );
                break;
                
            case "User Roles":
                showDataInParticularList ( "roles" , "User Roles" );
                break;
                
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        String selectedMaster = jComboBox1.getSelectedItem ().toString ();

        ArrayList<String> values = new ArrayList<String> ();

        int columnCount = 0;
        // get the model from the jtable
        DefaultTableModel model = ( DefaultTableModel ) jTable2.getModel ();

        // get the selected row index
        int selectedRowIndex = jTable2.getSelectedRow ();
        // Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString() )
        //         JOptionPane.showMessageDialog(null, "" + model.getValueAt(selectedRowIndex, 0).toString() );

        ResultSet result = null;

        try {

            System.out.println ( Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );

            switch ( selectedMaster ) {

                case "Grades":
                    result = DB_Operations.getSingleMasters ( "grade" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );
                    break;

                case "Designations":
                    result = DB_Operations.getSingleMasters ( "designation" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );
                    break;

                case "Salary Band":
                    result = DB_Operations.getSingleMasters ( "salaryband" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );
                    break;

                case "Nature of Employment":
                    result = DB_Operations.getSingleMasters ( "employeenature" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );
                    break;

                case "Unit of Measurement":
                    result = DB_Operations.getSingleMasters ( "uom" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );
                    break;

                case "Pack Size":
                    result = DB_Operations.getSingleMasters ( "pack_size" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );
                    break;

                case "Raw Material Type":
                    result = DB_Operations.getSingleMasters ( "raw_material_type" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );
                    break;

                case "Category":
                    result = DB_Operations.getSingleMasters ( "category" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );
                    break;

                case "Segment":
                    result = DB_Operations.getSingleMasters ( "segment" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );
                    break;

                case "Industry":
                    result = DB_Operations.getSingleMasters ( "industry" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );
                    break;

                case "Departments":
                    result = DB_Operations.getSingleMasters ( "departments" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );
                    break;

                case "Resources":
                    result = DB_Operations.getSingleMasters ( "resources" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );
                    break;
                    
                     case "User Roles":
                    result = DB_Operations.getSingleMasters ( "roles" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );
                    break;
                    
                    case "RejectionReasons":
                    result = DB_Operations.getSingleMasters ( "rejectionReason" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );
                    break;

            }

            selectedRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );

            if ( result != null ) {

                while ( result.next () ) {

                    values.add ( String.valueOf ( result.getObject ( 2 ) ) );
                }
            }

            result.close ();

        } catch ( SQLException e ) {
            System.out.println ( "Error " + e.getMessage () );

            try {
                result.close ();
            } catch ( SQLException ex ) {
                StaticValues.writer.writeExcel ( UtilitiyMasters.class.getSimpleName () , UtilitiyMasters.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
            StaticValues.writer.writeExcel ( UtilitiyMasters.class.getSimpleName () , UtilitiyMasters.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        jTextField6.setText ( values.get ( 0 ) );

    }//GEN-LAST:event_jTable2MouseClicked

    public void showDataInParticularList ( String Mastername , String keyName ) {

        loadContent ( Mastername );

        /*
         * try { SelectedObjects ob; ResultSet result =
         * DB_Operations.getMasters("grade"); if (result != null) { int length =
         * result.getFetchSize();
         *
         * while (result.next()) { ob = new SelectedObjects();
         * ob.setId(result.getInt("GradeID"));
         * ob.setValue(result.getString("GradeName")); dataList.addElement(ob);
         * length++; } // jList6.setModel(dataList); valuesList = new
         * JList<>(dataList); } } catch (SQLException e) {
         *
         * }
         */
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    public static javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JList<String> jList9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    DefaultListModel<SelectedObjects> dataList = new DefaultListModel<> ();
    int selectedIndex = -1, selectedRecordId = -1;
    JList<SelectedObjects> valuesList;

}

