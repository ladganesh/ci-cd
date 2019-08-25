/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.MyCustomFilter;
import trixware.erp.prodezydesktop.examples.HomeScreen;

/**
 *
 * @author Rajesh
 */
public class Employee_HR_Master_old extends javax.swing.JPanel {

    
    public void writeErrorLogs( String className, String filename, String exceptionName, String lineNo, String msg, String datetime ) {
        StaticValues.writer.writeExcel ( className , filename , exceptionName , lineNo , msg, datetime );
    }
    
    /**
     * Creates new form Employee_HR_Master
     */
    int selectedEmpRecordId = 0;

    public Employee_HR_Master_old () {
        initComponents ();

        jLabel32.setVisible ( false );
        jLabel33.setVisible ( false );
        jLabel30.setVisible ( false );

    }

    ArrayList<String> DepartmentList = new ArrayList<String>() ;
    
    public void loadContent () {
        ResultSet result = null; 
        try {
             result = DB_Operations.getMasters ( "employee" );

            if ( result != null ) {
                jTable1.setModel ( buildTableModel ( result ) );

                jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
            }

            if ( jComboBox1.getModel ().getSize () == 0 ) {
                result = DB_Operations.getMasters ( "grade" );
                if ( result != null ) {
                    while ( result.next () ) {
                        jComboBox1.addItem ( result.getString ( "GradeName" ) );
                    }
                } else {
                    jComboBox1.addItem ( "Not Available" );
                }
            }

            if ( jComboBox2.getModel ().getSize () == 0 ) {
                result = DB_Operations.getMasters ( "designation" );
                if ( result != null ) {
                    while ( result.next () ) {
                        jComboBox2.addItem ( result.getString ( "DesgTitle" ) );
                    }
                } else {
                    jComboBox2.addItem ( "Not Available" );
                }
            }

            if ( jComboBox9.getModel ().getSize () == 0 ) {
                result = DB_Operations.getMasters ( "salaryband" );
                if ( result != null ) {
                    while ( result.next () ) {
                        jComboBox9.addItem ( result.getString ( "SB_RANGE" ) );
                    }
                } else {
                    jComboBox9.addItem ( "Not Available" );
                }
            }

            if ( jComboBox3.getModel ().getSize () == 0 ) {
                result = DB_Operations.getMasters ( "employeenature" );
                if ( result != null ) {
                    while ( result.next () ) {
                        jComboBox3.addItem ( result.getString ( "NATURE" ) );
                    }
                } else {
                    jComboBox3.addItem ( "Not Available" );
                }
            }

            
            if ( jComboBox4.getModel ().getSize () == 0 ) {
                result = DB_Operations.getMasters ( "departments" );
                if ( result != null ) {
                    while ( result.next () ) {
                        jComboBox4.addItem ( result.getString ( "DNAME" ) );
                        DepartmentList.add ( result.getString ( "DID" ) ) ;
                    }
                } else {
                    jComboBox4.addItem ( "Not Available" );
                }
            }
            
            result.close() ;
            
            //  JTable table = new JTable(buildTableModel(result));
            // Closes the Connection
            // JOptionPane.showMessageDialog(null, new JScrollPane(table));
        } catch ( SQLException e ) {
            writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            try {
                result.close() ;
            } catch ( SQLException ex ) {
                System.out.println ( "" );
                writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            }
        }
    }

    public static DefaultTableModel buildTableModel ( ResultSet rs )
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

        dateChooserDialog1 = new datechooser.beans.DateChooserDialog();
        dateChooserDialog2 = new datechooser.beans.DateChooserDialog();
        dateChooserDialog3 = new datechooser.beans.DateChooserDialog();
        testDayPicker1 = new com.qt.datapicker.TestDayPicker();
        testDayPicker2 = new com.qt.datapicker.TestDayPicker();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jComboBox10 = new javax.swing.JComboBox<>();
        jComboBox11 = new javax.swing.JComboBox<>();
        jTextField12 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        dateChooserCombo2 = new datechooser.beans.DateChooserCombo();
        jTextField7 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox12 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jCheckBox2 = new javax.swing.JCheckBox();
        jButton6 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox<>();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();

        setBackground(new java.awt.Color(255, 255, 255));
        setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        setMinimumSize(Toolkit.getDefaultToolkit().getScreenSize());
        setPreferredSize(new java.awt.Dimension(1120, 550));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Name");
        add(jLabel1);
        jLabel1.setBounds(20, 10, 130, 20);

        jTextField1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField1.setNextFocusableComponent(jTextField2);
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(150, 10, 213, 27);
        jTextField1.getAccessibleContext().setAccessibleName("nameField");

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Employee No");
        add(jLabel2);
        jLabel2.setBounds(20, 40, 130, 30);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Date of Joining");
        add(jLabel3);
        jLabel3.setBounds(20, 100, 130, 20);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Photo");
        add(jLabel4);
        jLabel4.setBounds(20, 170, 130, 20);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Pan Card No");
        add(jLabel5);
        jLabel5.setBounds(390, 220, 130, 20);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("Aadhar No");
        add(jLabel6);
        jLabel6.setBounds(390, 160, 130, 20);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("ESIC No");
        add(jLabel7);
        jLabel7.setBounds(390, 320, 130, 30);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("PF No");
        add(jLabel8);
        jLabel8.setBounds(390, 100, 130, 20);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setText("Local Address");
        add(jLabel10);
        jLabel10.setBounds(390, 350, 130, 20);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel11.setText("<html>Permanent<br>Address</html>");
        add(jLabel11);
        jLabel11.setBounds(390, 450, 130, 40);

        jTextField2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField2.setNextFocusableComponent(jComboBox1);
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });
        add(jTextField2);
        jTextField2.setBounds(150, 40, 213, 27);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("<html>Select &<br> Upload</html>");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setMaximumSize(new java.awt.Dimension(50, 50));
        jButton1.setMinimumSize(new java.awt.Dimension(50, 50));
        jButton1.setNextFocusableComponent(jComboBox10);
        jButton1.setPreferredSize(new java.awt.Dimension(80, 50));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(280, 170, 80, 50);

        jTextField5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField5.setNextFocusableComponent(jTextField6);
        add(jTextField5);
        jTextField5.setBounds(520, 190, 213, 27);

        jTextField4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField4.setNextFocusableComponent(jTextField5);
        add(jTextField4);
        jTextField4.setBounds(520, 160, 213, 27);

        jTextField6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField6.setNextFocusableComponent(jComboBox9);
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        add(jTextField6);
        jTextField6.setBounds(520, 220, 213, 27);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("Date of Birth");
        add(jLabel12);
        jLabel12.setBounds(20, 380, 130, 20);

        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel13.setText("Cell No");
        add(jLabel13);
        jLabel13.setBounds(390, 190, 130, 20);

        jTextField8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField8.setNextFocusableComponent(jTextArea1);
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });
        add(jTextField8);
        jTextField8.setBounds(520, 320, 213, 27);

        jLabel14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel14.setText("Joining Skill Level");
        add(jLabel14);
        jLabel14.setBounds(390, 10, 130, 20);

        jComboBox7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Beginner", "Itermediate", "Proficient", "Expert" }));
        jComboBox7.setBorder(null);
        jComboBox7.setNextFocusableComponent(jComboBox11);
        jComboBox7.setOpaque(false);
        add(jComboBox7);
        jComboBox7.setBounds(520, 10, 213, 27);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(null);
        jTextArea1.setNextFocusableComponent(jCheckBox1);
        jScrollPane1.setViewportView(jTextArea1);

        add(jScrollPane1);
        jScrollPane1.setBounds(520, 350, 214, 83);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setBorder(null);
        jScrollPane2.setViewportView(jTextArea2);

        add(jScrollPane2);
        jScrollPane2.setBounds(520, 450, 215, 90);

        jCheckBox1.setText("<html>Same as<br>above<html>");
        jCheckBox1.setNextFocusableComponent(jTextArea2);
        jCheckBox1.setOpaque(false);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        add(jCheckBox1);
        jCheckBox1.setBounds(390, 490, 90, 40);

        jLabel15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel15.setText("Current Skill Level");
        add(jLabel15);
        jLabel15.setBounds(390, 40, 130, 20);

        jLabel16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel16.setText("Authorised Skill");
        add(jLabel16);
        jLabel16.setBounds(390, 70, 130, 20);

        jLabel17.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel17.setText("Department");
        add(jLabel17);
        jLabel17.setBounds(20, 290, 130, 30);

        jLabel18.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel18.setText("Role");
        add(jLabel18);
        jLabel18.setBounds(20, 320, 130, 20);

        jLabel19.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel19.setText("Disease");
        add(jLabel19);
        jLabel19.setBounds(390, 140, 130, 20);

        jLabel20.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel20.setText("Education");
        add(jLabel20);
        jLabel20.setBounds(20, 350, 130, 20);

        jComboBox8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SSC", "HSC", "Bachelor", "Master", "PHD", " " }));
        jComboBox8.setBorder(null);
        jComboBox8.setNextFocusableComponent(jCheckBox2);
        add(jComboBox8);
        jComboBox8.setBounds(150, 350, 213, 27);

        jLabel21.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel21.setText("Salary Band");
        add(jLabel21);
        jLabel21.setBounds(390, 250, 130, 20);

        jComboBox9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox9.setBorder(null);
        jComboBox9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox9MouseClicked(evt);
            }
        });
        add(jComboBox9);
        jComboBox9.setBounds(520, 250, 213, 27);

        jLabel22.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel22.setText("Gender");
        add(jLabel22);
        jLabel22.setBounds(20, 260, 130, 20);

        jComboBox10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        jComboBox10.setBorder(null);
        jComboBox10.setNextFocusableComponent(jCheckBox4);
        jComboBox10.setOpaque(false);
        add(jComboBox10);
        jComboBox10.setBounds(150, 260, 213, 27);

        jComboBox11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Beginner", "Itermediate", "Proficient", "Expert" }));
        jComboBox11.setBorder(null);
        jComboBox11.setNextFocusableComponent(jComboBox12);
        add(jComboBox11);
        jComboBox11.setBounds(520, 40, 213, 27);

        jTextField12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField12.setNextFocusableComponent(jLabel4);
        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });
        add(jTextField12);
        jTextField12.setBounds(520, 130, 213, 27);

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        jTable1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
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
        jTable1.setRowHeight(20);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable1);

        add(jScrollPane4);
        jScrollPane4.setBounds(760, 10, 340, 410);
        add(jLabel9);
        jLabel9.setBounds(281, 127, 0, 0);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("user img");
        jLabel23.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        add(jLabel23);
        jLabel23.setBounds(150, 170, 120, 90);

        dateChooserCombo1.setCalendarPreferredSize(new java.awt.Dimension(400, 320));
        dateChooserCombo1.setFieldFont(new java.awt.Font("Leelawadee UI", java.awt.Font.PLAIN, 14));
        add(dateChooserCombo1);
        dateChooserCombo1.setBounds(150, 100, 214, 27);

        dateChooserCombo2.setCalendarPreferredSize(new java.awt.Dimension(400, 320));
        dateChooserCombo2.setFieldFont(new java.awt.Font("Leelawadee UI", java.awt.Font.PLAIN, 14));
        add(dateChooserCombo2);
        dateChooserCombo2.setBounds(150, 380, 214, 25);

        jTextField7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField7.setNextFocusableComponent(jTextField12);
        add(jTextField7);
        jTextField7.setBounds(520, 100, 213, 27);

        jLabel24.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel24.setText("Grade");
        add(jLabel24);
        jLabel24.setBounds(20, 70, 130, 20);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox1.setMaximumRowCount(50);
        jComboBox1.setBorder(null);
        jComboBox1.setNextFocusableComponent(jComboBox2);
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
        jComboBox1.setBounds(150, 70, 213, 27);

        jLabel25.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel25.setText("Designation");
        add(jLabel25);
        jLabel25.setBounds(20, 140, 130, 20);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox2.setNextFocusableComponent(jComboBox10);
        jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox2MouseClicked(evt);
            }
        });
        add(jComboBox2);
        jComboBox2.setBounds(150, 130, 213, 27);

        jComboBox12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Beginner", "Itermediate", "Proficient", "Expert" }));
        jComboBox12.setBorder(null);
        jComboBox12.setNextFocusableComponent(jTextField7);
        add(jComboBox12);
        jComboBox12.setBounds(520, 70, 213, 27);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jButton3.setText("Add to Master");
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setMaximumSize(new java.awt.Dimension(120, 20));
        jButton3.setMinimumSize(new java.awt.Dimension(120, 20));
        jButton3.setPreferredSize(new java.awt.Dimension(120, 20));
        jButton3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButton3FocusGained(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(770, 430, 130, 30);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jButton4.setText("Export to Excel");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(770, 470, 130, 30);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(960, 470, 130, 30);

        jButton5.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jButton5.setText("Edit Record");
        jButton5.setEnabled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        add(jButton5);
        jButton5.setBounds(960, 430, 130, 30);

        jLabel26.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel26.setText("<html> Nature of <br>Employment</html>");
        add(jLabel26);
        jLabel26.setBounds(390, 270, 130, 50);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox3.setBorder(null);
        jComboBox3.setNextFocusableComponent(jTextField8);
        jComboBox3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox3MouseClicked(evt);
            }
        });
        add(jComboBox3);
        jComboBox3.setBounds(520, 280, 213, 27);

        jLabel27.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel27.setText("Username ");
        add(jLabel27);
        jLabel27.setBounds(20, 430, 130, 20);

        jLabel28.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel28.setText("Password");
        add(jLabel28);
        jLabel28.setBounds(20, 470, 130, 20);

        jLabel29.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel29.setText("Re-password ");
        add(jLabel29);
        jLabel29.setBounds(20, 510, 130, 20);

        jTextField13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField13.setEnabled(false);
        add(jTextField13);
        jTextField13.setBounds(150, 430, 213, 27);

        jCheckBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jCheckBox2.setText("Is a user ?");
        jCheckBox2.setNextFocusableComponent(jTextField13);
        jCheckBox2.setOpaque(false);
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        add(jCheckBox2);
        jCheckBox2.setBounds(150, 400, 120, 30);

        jButton6.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jButton6.setText("Reset");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        add(jButton6);
        jButton6.setBounds(770, 510, 130, 30);

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 0, 51));
        jLabel30.setText("*");
        add(jLabel30);
        jLabel30.setBounds(10, 510, 20, 20);

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 0, 51));
        jLabel31.setText("*");
        add(jLabel31);
        jLabel31.setBounds(10, 10, 20, 20);

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 0, 51));
        jLabel32.setText("*");
        add(jLabel32);
        jLabel32.setBounds(10, 430, 20, 20);

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 0, 51));
        jLabel33.setText("*");
        add(jLabel33);
        jLabel33.setBounds(10, 470, 20, 20);

        jButton7.setText("Import Excel (xls)");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        add(jButton7);
        jButton7.setBounds(960, 510, 130, 32);

        jComboBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox4.setMinimumSize(new java.awt.Dimension(73, 30));
        jComboBox4.setNextFocusableComponent(jCheckBox3);
        jComboBox4.setOpaque(false);
        jComboBox4.setPreferredSize(new java.awt.Dimension(73, 30));
        add(jComboBox4);
        jComboBox4.setBounds(150, 290, 213, 27);

        jCheckBox3.setText("Checker");
        jCheckBox3.setNextFocusableComponent(jCheckBox4);
        jCheckBox3.setOpaque(false);
        jCheckBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox3ItemStateChanged(evt);
            }
        });
        add(jCheckBox3);
        jCheckBox3.setBounds(150, 320, 76, 24);

        jCheckBox4.setText("Maker");
        jCheckBox4.setNextFocusableComponent(jComboBox8);
        jCheckBox4.setOpaque(false);
        add(jCheckBox4);
        jCheckBox4.setBounds(270, 320, 70, 24);

        jPasswordField1.setOpaque(false);
        add(jPasswordField1);
        jPasswordField1.setBounds(150, 470, 210, 30);

        jPasswordField2.setOpaque(false);
        add(jPasswordField2);
        jPasswordField2.setBounds(150, 510, 210, 30);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:

        String copyaddress;

        //if (jCheckBox1.isSelected() ){
        copyaddress = jTextArea1.getText ();
        jTextArea2.setText ( copyaddress );
        //}    
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        
        
        String name, gender, education, addr_local, add_perm, jn_skill, cur_skill, auth_skill, disease, pan_no, pf_no, esic, sal, DOB, DOJ, cell, aadhar, emp_no, department, role="Maker",  isUser, uname, password;

        name = jTextField1.getText ();
        emp_no = jTextField2.getText ();
        gender = jComboBox10.getSelectedItem ().toString ();
        education = jComboBox8.getSelectedItem ().toString ();
        addr_local = jTextArea1.getText ();
        add_perm = jTextArea2.getText ();
        jn_skill = jComboBox7.getSelectedItem ().toString ();
        cur_skill = jComboBox11.getSelectedItem ().toString ();
        auth_skill = jComboBox12.getSelectedItem ().toString ();
        disease = jTextField12.getText ();
        pan_no = jTextField6.getText ();
        pf_no = jTextField7.getText ();
        esic = jTextField8.getText ();
        sal = jComboBox9.getSelectedItem ().toString ();

        // DOB = jComboBox4.getSelectedItem().toString() + jComboBox5.getSelectedItem().toString() + "-" + jComboBox6.getSelectedItem().toString();
        DOB = dateChooserCombo2.getText ().toString ();
//DOJ = jComboBox1.getSelectedItem().toString() + jComboBox2.getSelectedItem().toString() + "-" + jComboBox3.getSelectedItem().toString();
        DOJ = dateChooserCombo1.getText ().toString ();
        cell = jTextField5.getText ();
        aadhar = jTextField4.getText ();

//    ht = jTextField9.getText ();
//        wt = jTextField10.getText ();
        department =  DepartmentList.get ( jComboBox4.getSelectedIndex()) ;
        
        if( jCheckBox3.isSelected () )
            role = "Checker" ;
        
        if( jCheckBox4.isSelected () )
            role = "Maker" ;
        
        
        uname = password = "";
        isUser = "false";
        if ( jCheckBox2.isSelected () ) {
            uname = jTextField13.getText ();
            String pass1, pass2;
            pass1 = jPasswordField1.getText ().trim ();
            pass2 = jPasswordField2.getText ().trim ();

            if (  ! pass1.equals ( pass2 ) ) {
                JOptionPane.showMessageDialog ( null , "Please repeat password correctly !" );
            } else {
                password = pass1;
                isUser = "true";
            }
        }

        
        
        if (  ! name.equals ( "" ) ) {

            
            String result = DB_Operations.insertIntoEmployeeMaster (    name ,  emp_no , DOJ , image ,   gender ,  department , role , education ,  cell ,  DOB ,  addr_local , add_perm , jn_skill ,  cur_skill ,  auth_skill ,  disease ,  pan_no ,  aadhar ,  esic , pf_no , sal  , isUser , uname ,   password , "created on" , "edited on" ,99999 );

            JOptionPane.showMessageDialog ( null , "Successfuly added new employee " );

        } else {
            JOptionPane.showMessageDialog ( null , "Please fill fields marked with * " );
        }

        jTextField1.setText ( "" );
        jTextField2.setText ( "" );
        jLabel23.setIcon ( null );
        jComboBox10.setSelectedItem ( 0 );
        jComboBox8.setSelectedItem ( 0 );
        jTextArea1.setText ( "" );
        jTextArea2.setText ( "" );
        jComboBox7.setSelectedItem ( 0 );
        jComboBox7.setSelectedItem ( 0 );
        //  jTextField11.setText("");
        jTextField12.setText ( "" );
        jTextField7.setText ( "" );
        jTextField6.setText ( "" );
        jTextField5.setText ( "" );
        jComboBox9.setSelectedItem ( 0 );

       
        jTextField8.setText ( "" );
        jTextField4.setText ( "" );

        jTextField13.setText ( "" );

        jPasswordField1.setText ( "" );
        jTextField1.setText ( "" );
        jPasswordField2.setText ( "" );

        loadContent ();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar ();
        if ( ( Character.isDigit ( enter ) ) ) {
            evt.consume ();
        }
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar ();
        if (  ! ( Character.isDigit ( enter ) ) ) {
            evt.consume ();
        }
    }//GEN-LAST:event_jTextField2KeyTyped


    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Workbook wb = new HSSFWorkbook ();
        File file = null;
        ArrayList<String> column_names = new ArrayList<String> ();
        ArrayList<String[]> values = new ArrayList<String[]> ();
        int columnCount = 0;

        try {
            ResultSet result = DB_Operations.getMasters ( "employee" );
            if ( result != null ) {
                ResultSetMetaData metaData = result.getMetaData ();
                // names of columns
                columnCount = metaData.getColumnCount ();
                for ( int column = 2 ; column <= columnCount - 3 ; column ++ ) {
                    column_names.add ( metaData.getColumnName ( column ) );
                }
            }
            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
            while ( result.next () ) {
                String[] val = new String[ columnCount ];
                for ( int columnIndex = 2 ; columnIndex <= columnCount ; columnIndex ++ ) {
                    if ( columnIndex < columnCount - 2 ) {
                        val[ columnIndex - 2 ] = String.valueOf ( result.getObject ( columnIndex ) );
                    }
                }
                values.add ( val );
            }
        } catch ( SQLException e ) {
            writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }

        // Cell style for header row
        CellStyle cs = wb.createCellStyle ();
        //cs.setFillForegroundColor(HSSFColor.LIME.index);
        //cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        CellStyle cs2 = wb.createCellStyle ();
        cs2.setFillForegroundColor ( HSSFColor.ROSE.index );
        cs2.setFillPattern ( HSSFCellStyle.SOLID_FOREGROUND );

        // New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet ( "Employee Master" );
        sheet1.setColumnWidth ( 0 , ( 15 * 500 ) );

        Cell c = null;
        Cell c3 = null;

        Row row = sheet1.createRow ( 1 );
      
        int i = 1;
        //add column names to the firt row of excel
        row = sheet1.createRow ( i ++ );
        for ( int a = 0 ; a < column_names.size () ; a ++ ) {
            c = row.createCell ( a );
            // c.setCellValue(cursor.getString(a));
            c.setCellValue ( column_names.get ( a ) );
            c.setCellStyle ( cs );
        }

        for ( int j = 0 ; j < values.size () ; j ++ ) {
            String[] rowValues = values.get ( j );
            Row row4 = sheet1.createRow ( i ++ );
            for ( int k = 0 ; k < rowValues.length ; k ++ ) {
                {

                    c = row4.createCell ( k );
                    c.setCellValue ( rowValues[ k ] );
                    c.setCellStyle ( cs );
                }
            }
            Calendar c2 = Calendar.getInstance ();
            SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
            String strDate2 = sdf2.format ( c2.getTime () );
            file = new File ( "Employee Report File " + strDate2 + ".xls" );

            FileOutputStream os = null;

            try {
                os = new FileOutputStream ( file );
                wb.write ( os );
                wb.close ();
                os.close ();

            } catch ( IOException e ) {
                JOptionPane.showMessageDialog ( null , "Error " + e.getMessage () );
                writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            } finally {

            }
        }

        JOptionPane.showMessageDialog ( null , "New File generated at " + file.getAbsolutePath () );

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        JFileChooser fileChooser = new JFileChooser ();
        fileChooser.setFileFilter ( new MyCustomFilter ("image") );
        fileChooser.setCurrentDirectory ( new File ( System.getProperty ( "user.home" ) ) );
        int result = fileChooser.showOpenDialog ( this );
        if ( result == JFileChooser.APPROVE_OPTION ) {
            File selectedFile = fileChooser.getSelectedFile ();
            //   System.out.println("Selected file: " + selectedFile.getAbsolutePath());

            BufferedImage image1;
            try {
                image1 = ImageIO.read ( new File ( selectedFile.getAbsolutePath () ) );

                BufferedImage inputImage = ImageIO.read ( selectedFile );

                BufferedImage outputImage = new BufferedImage ( jLabel23.getWidth () , jLabel23.getHeight () , inputImage.getType () );

                //  Image img = image1.getScaledInstance(jLabel23.getWidth(), jLabel23.getHeight(), Image.SCALE_SMOOTH);
                //  ImageIcon imageIcon = new ImageIcon(img);
                // scales the input image to the output image
                Graphics2D g2d = outputImage.createGraphics ();
                g2d.drawImage ( inputImage , 0 , 0 , jLabel23.getWidth () , jLabel23.getHeight () , null );
                g2d.dispose ();

                // extracts extension of output file
                String formatName = selectedFile.getAbsolutePath ().substring ( selectedFile.getAbsolutePath ().lastIndexOf ( "." ) + 1 );
                ImageIO.write ( outputImage , formatName , new File ( "new_Image_1.jpg" ) );

            } catch ( IOException ex ) {
                //Logger.getLogger(Employee_HR_Master.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println ( ex.getMessage () );
                writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            }

            FileInputStream fis;
            ByteArrayOutputStream bos;
            try {
                //fis = new FileInputStream(selectedFile);
                fis = new FileInputStream ( new File ( "new_Image_1.jpg" ) );
                bos = new ByteArrayOutputStream ();
                byte[] buf = new byte[ 1024 ];

                for ( int readNum ; ( readNum = fis.read ( buf ) ) != -1 ; ) {
                    bos.write ( buf , 0 , readNum );
                }

                image = bos.toByteArray ();
                ImageIcon img = new ImageIcon ( image );
                jLabel23.setIcon ( img );

            } catch ( FileNotFoundException ex ) {
                writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            } catch ( IOException ex ) {
                writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            }

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:

        jButton3.setEnabled ( false );

        ArrayList<String> values = new ArrayList<String> ();
        ArrayList<byte[]> files = new ArrayList<byte[]> ();
        int columnCount = 0;
        // get the model from the jtable
        DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();

        // get the selected row index
        int selectedRowIndex = jTable1.getSelectedRow ();
     
        ResultSet result = null ;
        try {
             result = DB_Operations.getSingleMasters ( "employee" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );

            selectedEmpRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );

            if ( result != null ) {
                ResultSetMetaData metaData = result.getMetaData ();

                columnCount = metaData.getColumnCount ();

                while ( result.next () ) {

                    for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {
                        if ( columnIndex < columnCount  ) {

                            if ( columnIndex == 5 ) {
                                files.add ( result.getBytes ( columnIndex ) );
                            } else {
                                values.add ( String.valueOf ( result.getObject ( columnIndex ) ) );
                            }
                        }
                    }
                }
            }
            
            result.close() ;
        } catch ( SQLException e ) {
            writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            try {
                result.close() ;
            } catch ( SQLException ex ) {
                System.out.println ( "" );
                writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            }
            System.out.println ( "Error " + e.getMessage () );
        }

        jTextField1.setText ( values.get ( 1 ) );
        jTextField2.setText ( values.get ( 2 ) );
        jComboBox10.setSelectedItem ( 0 );
        jComboBox8.setSelectedItem ( 0 );
        jTextArea1.setText ( values.get ( 10 ) );
        jTextArea2.setText ( values.get ( 11 ) );
        jComboBox7.setSelectedItem ( 0 );
        jComboBox7.setSelectedItem ( 0 );
        jComboBox12.setSelectedIndex ( 0 );
        jTextField12.setText ( values.get ( 15 ) );
        jTextField5.setText ( values.get ( 8 ) );
        jComboBox9.setSelectedItem ( 0 );

        jTextField8.setText ( values.get ( 19 ) );

        jTextField4.setText ( values.get ( 17 ) );


        jTextField7.setText ( values.get ( 18 ) );

        jTextField6.setText ( values.get ( 16 ) );

        jTextField13.setText ( values.get ( 17 ) );

        try {
            jLabel23.setIcon ( null );
            ImageIcon img = new ImageIcon ( files.get ( 0 ) );
            jLabel23.setIcon ( img );
        } catch ( NullPointerException e ) {
            writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }
        
        //21 is user
        // 22 username
        // 5 department
        // 6 role
        
        String isUserValue = values.get ( 21 ) ;
        
        if(isUserValue.equals ( "true")){
            jCheckBox2.setSelected ( true );
            jTextField13.setEnabled ( true );
            jPasswordField1.setEnabled ( true );
            jPasswordField2.setEnabled ( true );
            jLabel32.setVisible ( true );
            jLabel33.setVisible ( true );
            jLabel30.setVisible ( true );
        }else{
            jCheckBox2.setSelected ( false );
            jTextField13.setEnabled ( false );
            jPasswordField1.setEnabled ( false );
            jPasswordField2.setEnabled ( false );
            jLabel32.setVisible ( false );
            jLabel33.setVisible ( false );
            jLabel30.setVisible ( false );
        }
        jTextField13.setText( values.get ( 22 )  );

        for(   int i=0; i<jComboBox4.getItemCount (); i++     ){
                if( DepartmentList.get(i).equals ( values.get(5) )  ){
                   jComboBox4.setSelectedIndex (  i  ) ;
               }
        }
        
        if(values.get(6).equals ( "Checker")){
            jCheckBox3.setSelected ( true );
            jCheckBox4.setSelected ( false );
        }else if(values.get(6).equals ( "Maker")){
            jCheckBox3.setSelected ( false );
            jCheckBox4.setSelected ( true );
        }
        
        jButton5.setEnabled ( true );

    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        String name, gender, education, addr_local, add_perm, jn_skill, cur_skill, auth_skill, disease, pan_no, pf_no, esic, sal, DOB, DOJ, cell, aadhar, emp_no, department, role="Maker", isUser, uname, pword1, pword2;

        name = jTextField1.getText ();
        emp_no = jTextField2.getText ();
        gender = jComboBox10.getSelectedItem ().toString ();
        education = jComboBox8.getSelectedItem ().toString ();
        addr_local = jTextArea1.getText ();
        add_perm = jTextArea2.getText ();
        jn_skill = jComboBox7.getSelectedItem ().toString ();
        cur_skill = jComboBox11.getSelectedItem ().toString ();
        auth_skill = jComboBox12.getSelectedItem ().toString ();
        disease = jTextField12.getText ();
        pan_no = jTextField6.getText ();
        pf_no = jTextField7.getText ();
        esic = jTextField8.getText ();
        sal = jComboBox9.getSelectedItem ().toString ();
        
       

        if ( jCheckBox2.isSelected () ) {
            isUser = "true";
            uname = jTextField13.getText().trim();
            pword1 = jPasswordField1.getText().trim();
            pword2 = jPasswordField2.getText().trim();
            
            if( ! pword1.equals ( pword2)){
                JOptionPane.showMessageDialog ( null, "Passwords do not match. Please enter appropriate password");
                pword1 = "";
                pword2 = "";
                jPasswordField1.setText("");
                jPasswordField2.setText("");
            }
            
        } else {
            isUser = "false";
            uname = "" ;
            pword1 = "";
            pword2 = "";
        }

        DOB = dateChooserCombo2.getText ().toString ();
        DOJ = dateChooserCombo1.getText ().toString ();
        cell = jTextField5.getText ();
        aadhar = jTextField4.getText ();

//        ht = jTextField9.getText ();
//        wt = jTextField10.getText ();
       department =  DepartmentList.get ( jComboBox4.getSelectedIndex()) ;
        
        if( jCheckBox3.isSelected () )
            role = "Checker" ;
        
        if( jCheckBox4.isSelected () )
            role = "Maker" ;
        

        if ( selectedEmpRecordId == 0 ) {

            JOptionPane.showMessageDialog ( null , "Please select a record first" );

        } else {

            if( ! name.equals ( "")   ){
                
                if(isUser.equals ( "true")){

                    if( ! uname.equals ( "") && ! pword1.equals ( "") ){
                    
                        String result = DB_Operations.updateEmployeeMaster ( name ,  emp_no ,DOJ ,image , gender , department ,  role , education , cell , DOB ,  addr_local ,  add_perm , jn_skill , cur_skill ,  auth_skill ,  disease ,   pan_no , aadhar ,  esic , pf_no ,  sal ,  isUser, uname,  pword1,  "edited on" , 99999 , selectedEmpRecordId );
                        JOptionPane.showMessageDialog ( null , "" + result );
                    }else{
                        JOptionPane.showMessageDialog ( null , "" + "Please enter valid username and password" );
                    }
                }else{
                    String result = DB_Operations.updateEmployeeMaster ( name ,  emp_no ,DOJ ,image , gender , department ,  role , education , cell , DOB ,  addr_local ,  add_perm , jn_skill , cur_skill ,  auth_skill ,  disease ,   pan_no , aadhar ,  esic , pf_no ,  sal ,  isUser, uname,  pword1,  "edited on" , 99999 , selectedEmpRecordId );
                        JOptionPane.showMessageDialog ( null , "" + result );
                }
            }else{
                JOptionPane.showMessageDialog ( null , "" + "Please enter all mandatory field marked with * " );
            }
        }

        jTextField1.setText ( "" );
        jTextField2.setText ( "" );
        jLabel23.setIcon ( null );
        jComboBox10.setSelectedItem ( 0 );
        jComboBox8.setSelectedItem ( 0 );
        jTextArea1.setText ( "" );
        jTextArea2.setText ( "" );
        jComboBox7.setSelectedItem ( 0 );
        jComboBox7.setSelectedItem ( 0 );
        jTextField12.setText ( "" );
        jTextField7.setText ( "" );
        jTextField6.setText ( "" );
        jTextField5.setText ( "" );
        jComboBox9.setSelectedItem ( 0 );
        jTextField8.setText ( "" );
        jTextField4.setText ( "" );
        

        jTextField13.setText ( "" );

        jPasswordField1.setText ( "" );
        jTextField1.setText ( "" );
        jPasswordField2.setText ( "" );

        loadContent ();

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        HomeScreen.home.removeForms ();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        jTextField1.setText ( "" );
        jTextField2.setText ( "" );
        jLabel23.setIcon ( null );
        jComboBox10.setSelectedItem ( 0 );
        jComboBox8.setSelectedItem ( 0 );
        jTextArea1.setText ( "" );
        jTextArea2.setText ( "" );
        jComboBox7.setSelectedItem ( 0 );
        jComboBox7.setSelectedItem ( 0 );
        jTextField12.setText ( "" );
        jTextField7.setText ( "" );
        jTextField6.setText ( "" );
        jTextField5.setText ( "" );
        jComboBox9.setSelectedItem ( 0 );
        jTextField8.setText ( "" );
        jTextField4.setText ( "" );
       

        jTextField13.setText ( "" );

        jPasswordField1.setText ( "" );
        jTextField1.setText ( "" );

        jButton3.setEnabled ( true );
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButton3FocusGained
        // TODO add your handling code here:


    }//GEN-LAST:event_jButton3FocusGained

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:

        JCheckBox cb = ( JCheckBox ) evt.getSource ();

        if ( cb.isSelected () ) {

            jTextField13.setEnabled ( true );
            jPasswordField1.setEnabled ( true );
            jPasswordField2.setEnabled ( true );
            jLabel32.setVisible ( true );
            jLabel33.setVisible ( true );
            jLabel30.setVisible ( true );

        } else {
            jTextField13.setEnabled ( false );
            jPasswordField1.setEnabled ( false );
            jPasswordField2.setEnabled ( false );
            jLabel32.setVisible ( false );
            jLabel33.setVisible ( false );
            jLabel30.setVisible ( false );

        }

    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked

        if ( jComboBox1.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add values in grade from Utility  Master " );
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
        if ( jComboBox2.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add values in designation from Utility  Master " );
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2MouseClicked

    private void jComboBox9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox9MouseClicked
        if ( jComboBox9.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add values in Salary Band from Utility  Master " );
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox9MouseClicked

    private void jComboBox3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox3MouseClicked
        if ( jComboBox3.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add values in Nature of Employment from Utility  Master " );
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3MouseClicked

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        // TODO add your handling code here:

        File selectedFile = null;

        JFileChooser fileChooser = new JFileChooser ();
        fileChooser.setFileFilter ( new MyCustomFilter ("excel 97-2003") );
        fileChooser.setCurrentDirectory ( new File ( System.getProperty (
                "user.home" ) ) );
        int result = fileChooser.showOpenDialog ( this );
        if ( result == JFileChooser.APPROVE_OPTION ) {

            selectedFile = fileChooser.getSelectedFile ();

        }

        FileChannel inputChannel = null;
        FileChannel outputChannel = null;

        File dir = new File ( "dataupload\\" );
        dir.mkdirs ();

        String url;
        try {

            url = "jdbc:sqlite:" + StaticValues.dbName;
            Connection con = DriverManager.getConnection ( url );
            Statement stm = con.createStatement ();
            PreparedStatement pst;

            FileInputStream fis;
            //String query = "INSERT INTO xl_import_test ( ID, name, address, phone, email) VALUES ( ?,?,?,?,?)";
            String query = "INSERT INTO employee (  EMP_NAME ) VALUES ( ?  )";

            pst = con.prepareStatement ( query );
            fis = new FileInputStream ( new File ( "dataupload\\" + selectedFile.getName () ) );

            HSSFWorkbook my_xls_workbook = new HSSFWorkbook ( fis );
            HSSFSheet my_worksheet = my_xls_workbook.getSheetAt ( 0 );

            Iterator<Row> rowIterator = my_worksheet.iterator ();

            while ( rowIterator.hasNext () ) {
                Row row = rowIterator.next ();

                try {
                    //System.out.println ( row.getCell ( 0 ).getStringCellValue () );
                     pst.setString ( 1,  row.getCell ( 0 ).getStringCellValue () );
                } catch ( Exception e2 ) {
                    writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
                    try {

                         pst.setInt ( 1, ( int ) row.getCell ( 0 ).getNumericCellValue () );

                    } catch ( Exception e1 ) {
                        writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
                    }

                }
                pst.addBatch ();
            }

             int[] totalRecords = new int[ 2 ];
            try {
                totalRecords = pst.executeBatch ();
            } catch ( BatchUpdateException e1 ) {
                totalRecords = e1.getUpdateCounts ();
                writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            }
            System.out.println ( "Total record Inserted " + totalRecords.length );
            fis.close ();
            pst.close ();

            ResultSet result1 = DB_Operations.getMasters ( "employee" );

            if ( result1 != null ) {
                jTable1.setModel ( buildTableModel ( result1 ) );
                jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
            }
            

        } catch ( SQLException ex ) {
            System.out.println ( "SQL  " + ex.getMessage () );
            writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        } catch ( IOException ex ) {
            System.out.println ( "IO  " + ex.getMessage () );
            writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }

        try {
            inputChannel = new FileInputStream ( selectedFile ).getChannel ();
            outputChannel = new FileOutputStream ( new File ( dir , selectedFile.getName () ) ).getChannel ();
            outputChannel.transferFrom ( inputChannel , 0 , inputChannel.size () );

            inputChannel.close ();
            outputChannel.close ();

        } catch ( FileNotFoundException e1 ) {
            writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        } catch ( IOException e2 ) {
            writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }


    }//GEN-LAST:event_jButton7MouseClicked

    private void jCheckBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox3ItemStateChanged
        // TODO add your handling code here:
        
//        if( ! jCheckBox4.isSelected () ){
//            jCheckBox4.setSelected ( true );
//        }
    }//GEN-LAST:event_jCheckBox3ItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private datechooser.beans.DateChooserCombo dateChooserCombo2;
    private datechooser.beans.DateChooserDialog dateChooserDialog1;
    private datechooser.beans.DateChooserDialog dateChooserDialog2;
    private datechooser.beans.DateChooserDialog dateChooserDialog3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private com.qt.datapicker.TestDayPicker testDayPicker1;
    private com.qt.datapicker.TestDayPicker testDayPicker2;
    // End of variables declaration//GEN-END:variables

    byte[] image = null;

}
