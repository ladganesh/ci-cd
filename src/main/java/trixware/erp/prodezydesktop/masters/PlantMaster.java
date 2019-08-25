/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;

/**
 *
 * @author Rajesh
 */
public class PlantMaster extends javax.swing.JPanel {

    
    String resource = "" , resourceType = "" ;
    
    /**
     * Creates new form PlantMaster
     */
    public PlantMaster () {
        initComponents ();
        
        resourceType = "Masters" ;
	resource =  "PlantMaster";

//	try{
//      		StaticValues.checkUserRolePermission(resourceType, resourceType);
//	   }catch(Exception e){
//      		StaticValues.writer.writeExcel (resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//   	   }
        
        setBackground ( new Color(255,255,255,0));
        jScrollPane1.setBackground ( new Color(255,255,255,0)   );
        jPanel1.setBackground ( new Color(255,255,255,100)   );
    
        loadPlantAndShiftDetails();
    }

    public void loadPlantAndShiftDetails(){
        
        ResultSet rs = DB_Operations.executeSingle ("SELECT  `plantname` AS 'NAME' , `plantcode` AS 'CODE' , `gstno` AS 'GST' , `plantaddress` AS 'ADDRESS' , `plantemailaddress` AS 'PLANT EMAIL' , `plantweekoff` AS 'WEEKLY OFF' , `plantcpname` AS 'CONTACT PERSON ' , `plantcpno` AS 'CONTACT NO' , `plantcpemail` AS 'EMAIL'  , `plantcontactno` AS 'CONTACT NO' FROM PLANT_MASTER") ;
        try{
            jTable1.setModel(buildTableModelPlant ( rs ));
        }catch(SQLException e){
            System.out.println("Error 1");
            StaticValues.writer.writeExcel (PlantMaster.class.getSimpleName () , PlantMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }finally{
            try{
                rs.close();
            }catch(SQLException e){
                System.out.println("Error 1.1");
                StaticValues.writer.writeExcel (PlantMaster.class.getSimpleName () , PlantMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            }
        }
        
        rs = DB_Operations.executeSingle ("SELECT   `shiftno` AS '# SHIFT', `shifttitle` AS 'TITLE', `shiftfromtime` AS 'FROM TIME', `shifttotime` AS 'TO TIME', `breakduration` AS 'BREAK DURATION' FROM shifts WHERE refplantid = "+6) ;
        try{
            jTable2.setModel(buildTableModelSHifts (rs ));
        }catch(SQLException e){
            System.out.println("Error 2");
            StaticValues.writer.writeExcel (PlantMaster.class.getSimpleName () , PlantMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }finally{
            try{
                rs.close();
            }catch(SQLException e){
                System.out.println("Error 2.1");
                StaticValues.writer.writeExcel (PlantMaster.class.getSimpleName () , PlantMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            }
        }        
    }
    
    public  DefaultTableModel buildTableModelSHifts(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        while (rs.next()) {
            Vector<String> vector = new Vector<String>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getString(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }
    
    public  DefaultTableModel buildTableModelPlant(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        while (rs.next()) {
            Vector<String> vector = new Vector<String>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getString(columnIndex));
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
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField9 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jSpinner2 = new javax.swing.JSpinner();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setLayout(null);

        jScrollPane1.setMinimumSize(getPreferredSize());
        jScrollPane1.setPreferredSize(getMaximumSize());

        jPanel1.setLayout(null);

        jLabel1.setText("Company name");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(120, 80, 140, 16);

        jLabel2.setText("Plant Code");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(120, 140, 100, 16);

        jLabel3.setText("GST No");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(120, 200, 60, 16);

        jLabel4.setText("Contact Person Name");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(450, 80, 190, 16);
        jPanel1.add(jTextField1);
        jTextField1.setBounds(180, 100, 220, 30);
        jPanel1.add(jTextField2);
        jTextField2.setBounds(180, 160, 220, 30);
        jPanel1.add(jTextField3);
        jTextField3.setBounds(180, 220, 220, 30);
        jPanel1.add(jTextField4);
        jTextField4.setBounds(510, 100, 220, 30);

        jLabel6.setText("Contact No");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(450, 140, 150, 16);
        jPanel1.add(jTextField5);
        jTextField5.setBounds(510, 160, 220, 30);

        jLabel7.setText("Plant Address");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(450, 260, 120, 16);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(510, 280, 223, 90);

        jLabel8.setText("Email Address");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(450, 200, 90, 16);
        jPanel1.add(jTextField6);
        jTextField6.setBounds(510, 220, 220, 30);

        jLabel9.setText("Plant Contact NO");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(120, 260, 120, 16);
        jPanel1.add(jTextField7);
        jTextField7.setBounds(180, 280, 220, 30);

        jLabel10.setText("Plant Email Address");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(120, 320, 180, 16);
        jPanel1.add(jTextField8);
        jTextField8.setBounds(180, 340, 220, 30);

        jLabel11.setText("Weekly Off");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(120, 380, 200, 16);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", " " }));
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(180, 400, 220, 30);

        jLabel12.setText("Shift Title");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(920, 80, 90, 16);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Shift1", "Shift2", "Shift3", "Shift4", "Shift5", " " }));
        jPanel1.add(jComboBox2);
        jComboBox2.setBounds(840, 100, 80, 30);

        jTextField9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jPanel1.add(jTextField9);
        jTextField9.setBounds(940, 100, 120, 30);

        jScrollPane2.setViewportView(jList1);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(840, 280, 220, 90);

        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(900, 400, 70, 32);

        jButton2.setText("Close");
        jPanel1.add(jButton2);
        jButton2.setBounds(980, 400, 77, 32);

        jButton3.setText("Save");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(830, 400, 58, 32);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 24)); // NOI18N
        jLabel5.setText("Plant Master");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(30, 10, 420, 40);

        jTextField12.setText("0");
        jTextField12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField12FocusLost(evt);
            }
        });
        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });
        jTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField12KeyTyped(evt);
            }
        });
        jPanel1.add(jTextField12);
        jTextField12.setBounds(880, 150, 50, 30);

        jTextField13.setText("0");
        jPanel1.add(jTextField13);
        jTextField13.setBounds(950, 150, 50, 30);

        jSpinner2.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner2.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner2.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner2);
        jSpinner2.setBounds(1010, 150, 50, 31);

        jTextField14.setText("0");
        jPanel1.add(jTextField14);
        jTextField14.setBounds(880, 180, 50, 30);

        jTextField15.setText("0");
        jPanel1.add(jTextField15);
        jTextField15.setBounds(950, 180, 50, 30);

        jSpinner1.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner1.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner1.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner1);
        jSpinner1.setBounds(1010, 180, 50, 31);

        jLabel13.setText("From Time");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(780, 150, 90, 16);

        jLabel14.setText("To Time");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(780, 180, 90, 16);

        jLabel15.setText("Shifts");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(780, 80, 90, 16);

        jLabel16.setText("Break Duration");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(780, 220, 100, 16);
        jPanel1.add(jTextField10);
        jTextField10.setBounds(840, 240, 70, 30);
        jTextField10.getAccessibleContext().setAccessibleDescription("Break duration can be enterd in minutes as 90 minutes instead of 1.5 hours or hours in case of round figures like 1 hour");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minutes", "Hours" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox3);
        jComboBox3.setBounds(920, 240, 80, 30);

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 51));
        jLabel27.setText("*");
        jPanel1.add(jLabel27);
        jLabel27.setBounds(770, 180, 30, 20);

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 0, 51));
        jLabel28.setText("*");
        jPanel1.add(jLabel28);
        jLabel28.setBounds(110, 80, 30, 10);

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 0, 51));
        jLabel29.setText("*");
        jPanel1.add(jLabel29);
        jLabel29.setBounds(110, 80, 30, 10);

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 0, 51));
        jLabel30.setText("*");
        jPanel1.add(jLabel30);
        jLabel30.setBounds(110, 140, 30, 10);

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 0, 51));
        jLabel31.setText("*");
        jPanel1.add(jLabel31);
        jLabel31.setBounds(110, 200, 30, 10);

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 0, 51));
        jLabel32.setText("*");
        jPanel1.add(jLabel32);
        jLabel32.setBounds(110, 260, 30, 10);

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 0, 51));
        jLabel33.setText("*");
        jPanel1.add(jLabel33);
        jLabel33.setBounds(440, 260, 30, 10);

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 0, 51));
        jLabel34.setText("*");
        jPanel1.add(jLabel34);
        jLabel34.setBounds(110, 320, 30, 10);

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 0, 51));
        jLabel35.setText("*");
        jPanel1.add(jLabel35);
        jLabel35.setBounds(110, 380, 30, 10);

        jLabel36.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 0, 51));
        jLabel36.setText("*");
        jPanel1.add(jLabel36);
        jLabel36.setBounds(440, 80, 30, 10);

        jLabel37.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 0, 51));
        jLabel37.setText("*");
        jPanel1.add(jLabel37);
        jLabel37.setBounds(770, 220, 30, 10);

        jLabel38.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 0, 51));
        jLabel38.setText("*");
        jPanel1.add(jLabel38);
        jLabel38.setBounds(440, 140, 30, 10);

        jLabel39.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 0, 51));
        jLabel39.setText("*");
        jPanel1.add(jLabel39);
        jLabel39.setBounds(440, 200, 30, 20);

        jButton4.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButton4.setText("Add");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(1000, 237, 60, 33);
        jButton4.getAccessibleContext().setAccessibleDescription("Add shifts filling up the form for shifts and clicking the add button to add it to the list. When  Save/Submit buttion is clicked all shitfs shall get added with the record.");

        jLabel40.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 0, 51));
        jLabel40.setText("*");
        jPanel1.add(jLabel40);
        jLabel40.setBounds(910, 80, 30, 10);

        jLabel41.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 0, 51));
        jLabel41.setText("*");
        jPanel1.add(jLabel41);
        jLabel41.setBounds(770, 150, 30, 10);

        jLabel17.setText(":");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(940, 180, 10, 20);

        jLabel18.setText(":");
        jPanel1.add(jLabel18);
        jLabel18.setBounds(940, 150, 10, 20);

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
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable1);

        jPanel1.add(jScrollPane4);
        jScrollPane4.setBounds(40, 450, 590, 180);

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
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTable2);

        jPanel1.add(jScrollPane5);
        jScrollPane5.setBounds(653, 450, 410, 180);

        jScrollPane1.setViewportView(jPanel1);

        add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 1120, 650);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField12FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12FocusLost

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jTextField12KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField12KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12KeyTyped

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        //  total hours with all shifs put together should not be greater than 24 for a single dat and date
        //  the provision for break also need to be added

        int newRecordId = 0;
        String COMPANYNAME = "", PLANTCODE = "", GSTNO = "", PLANTADDRESS = "", PLANTEMAIL = "", WEEKOFF = "", CPNAME = "", PLANTCONTACT = "", CPEMAIL = "", CPCONTACT = "";

        COMPANYNAME = jTextField1.getText ().trim ();
        PLANTCODE = jTextField2.getText ().trim ();
        GSTNO = jTextField3.getText ().trim ();
        PLANTADDRESS = jTextArea2.getText ().trim ();
        PLANTEMAIL = jTextField8.getText ().trim ();
        WEEKOFF = jComboBox1.getSelectedItem ().toString ();
        CPNAME = jTextField4.getText ().trim ();
        PLANTCONTACT = jTextField7.getText ().trim ();
        CPEMAIL = jTextField6.getText ().trim ();
        CPCONTACT = jTextField5.getText ().trim ();

        if (  ! COMPANYNAME.equals ( "" ) &&  ! PLANTCODE.equals ( "" ) &&  ! GSTNO.equals ( "" ) &&  ! PLANTADDRESS.equals ( "" ) &&  ! PLANTEMAIL.equals ( "" ) &&  ! WEEKOFF.equals ( "" ) &&  ! CPNAME.equals ( "" ) &&  ! PLANTCONTACT.equals ( "" ) &&  ! CPEMAIL.equals ( "" ) &&  ! CPCONTACT.equals ( "" ) && !shiftList.isEmpty ()) {

            String plantMasterQuery = "INSERT INTO PLANT_MASTER ( `plantname`,`plantcode`,`gstno`,`plantaddress`,`plantemailaddress`,`plantweekoff`,`plantcpname`,`plantcpno`,`plantcpemail`,  `plantcontactno`)  VALUES   ( '" + COMPANYNAME + "', '" + PLANTCODE + "', '" + GSTNO + "',  '" + PLANTADDRESS + "', '" + PLANTEMAIL + "', '" + WEEKOFF + "', '" + CPNAME + "', '" + CPCONTACT + "', '" + CPEMAIL + "'  , '" + CPCONTACT + "')";
            newRecordId = DB_Operations.executeInsertRandom ( plantMasterQuery );

            String shiftQuery = "INSERT INTO shifts ( shiftno, shifttitle, shiftfromtime, shifttotime, breakduration, refplantid ) values ( ?,?,?,?,?,?  )" ;
            for( int i=0; i<shiftList.getSize (); i++ ){
                String[] shiftValues = shiftList.getElementAt ( i).toString ().split ( ",");
                shiftQuery = "INSERT INTO shifts ( shiftno, shifttitle, shiftfromtime, shifttotime, breakduration, refplantid ) values ( "+Integer.parseInt(shiftValues[0].trim())+",'"+shiftValues[1].trim()+"','"+shiftValues[2].trim()+"','"+shiftValues[3].trim()+"',"+Integer.parseInt(shiftValues[4].trim())+","+newRecordId+"  )" ;
                DB_Operations.executeInsertRandom ( shiftQuery ) ;
            }
            
        } else {
            
            JOptionPane.showMessageDialog ( null , "Please fill all field marked with * with appropriate values" );
        }

        if ( newRecordId > 0 ) {
            JOptionPane.showMessageDialog ( null , "Successfully inserted new plant" );
        }

    }//GEN-LAST:event_jButton3MouseClicked

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    
    DefaultListModel shiftList = new DefaultListModel ();
    SimpleDateFormat sdf1 = new SimpleDateFormat ( "HH:mm:ss" );
    Calendar c3 = Calendar.getInstance ();
    
    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:

        String SHIFTTITLE="", STARTTIME="",  ENDTIME=""  ;
        int BREAKDURATION = 0;
        int SHIFTNO = 0;
        
        SHIFTTITLE = jTextField9.getText ().trim();
        SHIFTNO =  jComboBox2.getSelectedIndex ()+1 ;
        
         c3.set ( Calendar.HOUR , Integer.parseInt( jTextField12.getText ().trim() ));
         c3.set ( Calendar.MINUTE , Integer.parseInt( jTextField13.getText ().trim() ));
        
         STARTTIME = sdf1.format ( c3.getTime() );
         
         c3.set ( Calendar.HOUR , Integer.parseInt( jTextField14.getText ().trim() ));
         c3.set ( Calendar.MINUTE , Integer.parseInt( jTextField15.getText ().trim() ));
        
         ENDTIME = sdf1.format ( c3.getTime() );
         
        if( ! jTextField10.getText ().trim().equals ( "") ) {
            BREAKDURATION = Integer.parseInt( jTextField10.getText ().trim() );
            
            if( jComboBox3.getSelectedItem ().toString ().equals ( "Hours") )
            {
                BREAKDURATION = BREAKDURATION+ 60 ;
            }
        
            if( !SHIFTTITLE.equals ( "")  &&  !STARTTIME.equals ( "")  && !ENDTIME.equals ( "")  && BREAKDURATION >=0 && SHIFTNO>0){
            
                shiftList.addElement ( SHIFTNO +","+ SHIFTTITLE+", "+STARTTIME+", "+ENDTIME+", "+BREAKDURATION );
                jList1.setModel ( shiftList );
                
            }else{
                JOptionPane.showMessageDialog ( null, "Please enter valid shift details ");
            }
            
        }else{
            JOptionPane.showMessageDialog ( null, "Please enter valid number for break duration ");
        }
        
    }//GEN-LAST:event_jButton4MouseClicked


    class Shifts{
        
        String shifTitle , fromTime, toTime;
        int breakduration, shiftno ; 
                
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
