/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.MyCustomFilter;
import trixware.erp.prodezydesktop.Utilities.UpdateBOMDialog;
import trixware.erp.prodezydesktop.examples.HomeScreen3;
import static trixware.erp.prodezydesktop.examples.HomeScreen.home;
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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Rajesh
 */
public class FinishedGoodMaster_old extends javax.swing.JPanel {

    /**
     * Creates new form FinishedGoodMaster
     */
    public FinishedGoodMaster_old () {
        initComponents ();
        
        jTextField1.setNextFocusableComponent ( jTextField3 );
        jTextField3.setNextFocusableComponent ( jTextField2 );
        jTextField2.setNextFocusableComponent ( jComboBox1 );
        jComboBox1.setNextFocusableComponent ( jTextField4 );
    }

    ArrayList<String[]> machines;
    ArrayList<String[]> customers;

    public void loadContent () {

        ResultSet result = null;
        try {
            result = DB_Operations.getMasters ( "finished_goods" );
            jTable1.setModel ( buildTableModel ( result ) );
            jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
            jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );

            result = DB_Operations.getMasters ( "uom" );
            if ( result != null ) {
                while ( result.next () ) {
                    jComboBox4.addItem ( result.getString ( "UOM" ) );
                }
            } else {
                jComboBox4.addItem ( "Not Available" );
            }

            result = DB_Operations.getMasters ( "customer" );
            if ( result != null ) {
                //   customers = new ArrayList<String[]>();
                while ( result.next () ) {
                    jComboBox1.addItem ( result.getString ( "CUSTOMER_NAME" ) );
                }
            } else {

                jComboBox1.addItem ( "Not Available" );
            }

            jComboBox7.removeAllItems ();
            result = DB_Operations.getMasters ( "machine" );
            if ( result != null ) {
                machines = new ArrayList<String[]> ();
                while ( result.next () ) {
                    machines.add(  new String[]{ result.getString("MCH_ID"),  result.getString("MACHINE_NO"), result.getString("MAKE") } );
                    jComboBox7.addItem ( result.getString ( "MACHINE_NO" ) + "  " + result.getString ( "MAKE" ) );
                }
            } else {

                jComboBox7.addItem ( "Not Available" );
            }
            
            
            

        } catch ( SQLException e ) {
            System.out.println( "Error : "+e.getMessage () );
        } finally {
            try {
                result.close ();
            } catch ( SQLException e ) {

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

        try{
            rs.close();
        }catch(Exception e){
            
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

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel8 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox<String>(StaticValues.UOMS);
        jLabel22 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jTextField18 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel29 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox8 = new javax.swing.JComboBox<>();

        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Finished Goods", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Leelawadee UI", 0, 14))); // NOI18N
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Part No");
        add(jLabel1);
        jLabel1.setBounds(90, 60, 130, 25);

        jTextField1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField1.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField1.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField1.setNextFocusableComponent(jTextField3);
        jTextField1.setPreferredSize(new java.awt.Dimension(200, 50));
        add(jTextField1);
        jTextField1.setBounds(220, 60, 213, 25);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Part Name");
        add(jLabel2);
        jLabel2.setBounds(90, 120, 130, 25);

        jTextField2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField2.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField2.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField2.setNextFocusableComponent(jTextArea1);
        jTextField2.setPreferredSize(new java.awt.Dimension(200, 50));
        add(jTextField2);
        jTextField2.setBounds(220, 120, 213, 25);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Customer");
        add(jLabel3);
        jLabel3.setBounds(90, 150, 130, 25);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox1.setMaximumSize(new java.awt.Dimension(200, 50));
        jComboBox1.setMinimumSize(new java.awt.Dimension(200, 50));
        jComboBox1.setNextFocusableComponent(jTextField4);
        jComboBox1.setPreferredSize(new java.awt.Dimension(200, 50));
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        add(jComboBox1);
        jComboBox1.setBounds(220, 150, 213, 30);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("MOQ");
        add(jLabel4);
        jLabel4.setBounds(90, 180, 130, 25);

        jTextField4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField4.setNextFocusableComponent(jComboBox4);
        add(jTextField4);
        jTextField4.setBounds(220, 180, 120, 24);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Sales Rate /unit");
        add(jLabel5);
        jLabel5.setBounds(90, 210, 130, 25);

        jTextField5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField5.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField5.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField5.setNextFocusableComponent(jList1);
        jTextField5.setPreferredSize(new java.awt.Dimension(200, 50));
        add(jTextField5);
        jTextField5.setBounds(220, 210, 120, 25);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("GST Rate");
        add(jLabel6);
        jLabel6.setBounds(90, 350, 130, 25);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("Pack Size");
        add(jLabel7);
        jLabel7.setBounds(90, 300, 130, 25);

        jList1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Size1", "Size2", "Size3" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.setMaximumSize(new java.awt.Dimension(200, 50));
        jList1.setMinimumSize(new java.awt.Dimension(200, 50));
        jList1.setNextFocusableComponent(jTextField18);
        jList1.setPreferredSize(new java.awt.Dimension(200, 50));
        jScrollPane1.setViewportView(jList1);

        add(jScrollPane1);
        jScrollPane1.setBounds(220, 280, 213, 70);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("Mould Cavities");
        add(jLabel8);
        jLabel8.setBounds(90, 380, 130, 25);

        jTextField6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField6.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField6.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField6.setNextFocusableComponent(jTextField11);
        jTextField6.setPreferredSize(new java.awt.Dimension(200, 50));
        add(jTextField6);
        jTextField6.setBounds(220, 380, 213, 25);

        jTextField7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField7.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField7.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField7.setNextFocusableComponent(jTextField9);
        jTextField7.setPreferredSize(new java.awt.Dimension(200, 50));
        add(jTextField7);
        jTextField7.setBounds(220, 540, 213, 25);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("Per Shift Target Output");
        add(jLabel9);
        jLabel9.setBounds(90, 540, 130, 25);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setText("Part Drawing");
        add(jLabel10);
        jLabel10.setBounds(90, 410, 130, 25);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Select File");
        jButton1.setNextFocusableComponent(jTextField8);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(300, 440, 130, 30);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("Rubber Batch No");
        add(jLabel11);
        jLabel11.setBounds(90, 510, 130, 25);

        jTextField8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField8.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField8.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField8.setNextFocusableComponent(jTextField10);
        jTextField8.setPreferredSize(new java.awt.Dimension(200, 50));
        add(jTextField8);
        jTextField8.setBounds(220, 480, 213, 25);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("Net Weight");
        add(jLabel12);
        jLabel12.setBounds(90, 480, 130, 25);

        jTextField9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField9.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField9.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField9.setNextFocusableComponent(jComboBox3);
        jTextField9.setPreferredSize(new java.awt.Dimension(200, 50));
        add(jTextField9);
        jTextField9.setBounds(220, 570, 213, 25);

        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel13.setText("Gross Weight");
        add(jLabel13);
        jLabel13.setBounds(90, 570, 130, 25);

        jTextField10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField10.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField10.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField10.setNextFocusableComponent(jTextField7);
        jTextField10.setPreferredSize(new java.awt.Dimension(200, 50));
        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });
        add(jTextField10);
        jTextField10.setBounds(220, 510, 213, 25);

        jLabel14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel14.setText("Importance");
        add(jLabel14);
        jLabel14.setBounds(480, 150, 130, 25);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Beginner", "Intermediate", "Proficient", "Expert" }));
        jComboBox3.setMaximumSize(new java.awt.Dimension(200, 50));
        jComboBox3.setMinimumSize(new java.awt.Dimension(200, 50));
        jComboBox3.setNextFocusableComponent(jComboBox5);
        jComboBox3.setPreferredSize(new java.awt.Dimension(200, 50));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        add(jComboBox3);
        jComboBox3.setBounds(220, 600, 213, 30);

        jLabel15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel15.setText("Part Validation Doc");
        add(jLabel15);
        jLabel15.setBounds(480, 190, 130, 25);

        jLabel16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel16.setText("Part PPAP");
        add(jLabel16);
        jLabel16.setBounds(480, 250, 130, 25);

        jLabel17.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel17.setText("Part Photo");
        add(jLabel17);
        jLabel17.setBounds(480, 310, 130, 25);

        jLabel18.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel18.setText("Testing");
        add(jLabel18);
        jLabel18.setBounds(480, 430, 130, 25);

        jTextField13.setEditable(false);
        jTextField13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField13.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField13.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField13.setNextFocusableComponent(jButton11);
        jTextField13.setPreferredSize(new java.awt.Dimension(200, 50));
        jTextField13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField13MouseClicked(evt);
            }
        });
        add(jTextField13);
        jTextField13.setBounds(610, 430, 213, 25);

        jLabel19.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel19.setText("<html>Mold Layout <br>Inspection Freq.</html>");
        add(jLabel19);
        jLabel19.setBounds(480, 490, 130, 40);

        jLabel20.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel20.setText("Metal Insert Photo");
        add(jLabel20);
        jLabel20.setBounds(480, 550, 130, 25);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("View Photo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(690, 580, 130, 30);

        jLabel21.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel21.setText("Operator Skill Level");
        add(jLabel21);
        jLabel21.setBounds(90, 600, 130, 25);

        jComboBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "Medium", "High", "Critical" }));
        jComboBox5.setMaximumSize(new java.awt.Dimension(200, 50));
        jComboBox5.setMinimumSize(new java.awt.Dimension(200, 50));
        jComboBox5.setNextFocusableComponent(jTextField12);
        jComboBox5.setPreferredSize(new java.awt.Dimension(200, 50));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        add(jComboBox5);
        jComboBox5.setBounds(610, 150, 213, 30);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Select Doc");
        jButton3.setMaximumSize(new java.awt.Dimension(200, 50));
        jButton3.setMinimumSize(new java.awt.Dimension(200, 50));
        jButton3.setNextFocusableComponent(jTextField14);
        jButton3.setPreferredSize(new java.awt.Dimension(200, 50));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(690, 220, 130, 30);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("View Photo");
        jButton4.setMaximumSize(new java.awt.Dimension(200, 50));
        jButton4.setMinimumSize(new java.awt.Dimension(200, 50));
        jButton4.setNextFocusableComponent(jTextField16);
        jButton4.setPreferredSize(new java.awt.Dimension(200, 50));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(690, 340, 130, 30);

        jLabel23.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel23.setText("Molding SOP");
        add(jLabel23);
        jLabel23.setBounds(480, 370, 130, 25);

        jButton5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton5.setText("View Document");
        jButton5.setMaximumSize(new java.awt.Dimension(200, 50));
        jButton5.setMinimumSize(new java.awt.Dimension(200, 50));
        jButton5.setNextFocusableComponent(jTextField15);
        jButton5.setPreferredSize(new java.awt.Dimension(200, 50));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        add(jButton5);
        jButton5.setBounds(690, 280, 130, 30);

        jComboBox6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Daily", "Monthly", "Weekly" }));
        jComboBox6.setMaximumSize(new java.awt.Dimension(200, 50));
        jComboBox6.setMinimumSize(new java.awt.Dimension(200, 50));
        jComboBox6.setNextFocusableComponent(jComboBox7);
        jComboBox6.setPreferredSize(new java.awt.Dimension(200, 50));
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });
        add(jComboBox6);
        jComboBox6.setBounds(610, 490, 213, 30);

        jLabel24.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel24.setText("Preferred Machine");
        add(jLabel24);
        jLabel24.setBounds(480, 520, 130, 25);

        jComboBox7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Machine 1", "Machine 2", "Machine 3" }));
        jComboBox7.setMaximumSize(new java.awt.Dimension(200, 50));
        jComboBox7.setMinimumSize(new java.awt.Dimension(200, 50));
        jComboBox7.setNextFocusableComponent(jTextField17);
        jComboBox7.setPreferredSize(new java.awt.Dimension(200, 50));
        jComboBox7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox7MouseClicked(evt);
            }
        });
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });
        add(jComboBox7);
        jComboBox7.setBounds(610, 520, 213, 30);

        jButton6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton6.setText("Add to Master");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        add(jButton6);
        jButton6.setBounds(890, 490, 140, 30);

        jButton7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton7.setText("Cancel");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        add(jButton7);
        jButton7.setBounds(890, 520, 140, 30);

        jTextField11.setEditable(false);
        jTextField11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField11.setText("Selected File Path");
        jTextField11.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField11.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField11.setNextFocusableComponent(jButton1);
        jTextField11.setPreferredSize(new java.awt.Dimension(200, 50));
        jTextField11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField11MouseClicked(evt);
            }
        });
        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });
        add(jTextField11);
        jTextField11.setBounds(220, 410, 213, 30);

        jTextField12.setEditable(false);
        jTextField12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField12.setText("Selected Doc path");
        jTextField12.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField12.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField12.setNextFocusableComponent(jButton3);
        jTextField12.setPreferredSize(new java.awt.Dimension(200, 50));
        jTextField12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField12MouseClicked(evt);
            }
        });
        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });
        add(jTextField12);
        jTextField12.setBounds(610, 190, 213, 30);

        jTextField14.setEditable(false);
        jTextField14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField14.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField14.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField14.setNextFocusableComponent(jButton5);
        jTextField14.setPreferredSize(new java.awt.Dimension(200, 50));
        jTextField14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField14MouseClicked(evt);
            }
        });
        add(jTextField14);
        jTextField14.setBounds(610, 250, 213, 25);

        jTextField15.setEditable(false);
        jTextField15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField15.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField15.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField15.setNextFocusableComponent(jButton4);
        jTextField15.setPreferredSize(new java.awt.Dimension(200, 50));
        jTextField15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField15MouseClicked(evt);
            }
        });
        add(jTextField15);
        jTextField15.setBounds(610, 310, 213, 25);

        jTextField16.setEditable(false);
        jTextField16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField16.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField16.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField16.setNextFocusableComponent(jButton12);
        jTextField16.setPreferredSize(new java.awt.Dimension(200, 50));
        jTextField16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField16MouseClicked(evt);
            }
        });
        add(jTextField16);
        jTextField16.setBounds(610, 370, 213, 25);

        jTextField17.setEditable(false);
        jTextField17.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField17.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField17.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField17.setNextFocusableComponent(jButton2);
        jTextField17.setPreferredSize(new java.awt.Dimension(200, 50));
        jTextField17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField17MouseClicked(evt);
            }
        });
        add(jTextField17);
        jTextField17.setBounds(610, 550, 213, 25);

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        add(jScrollPane2);
        jScrollPane2.setBounds(880, 80, 320, 402);

        jButton8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton8.setText("Export to Excel");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        add(jButton8);
        jButton8.setBounds(1060, 520, 130, 30);

        jComboBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox4.setNextFocusableComponent(jTextField5);
        jComboBox4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox4MouseClicked(evt);
            }
        });
        add(jComboBox4);
        jComboBox4.setBounds(340, 180, 90, 30);

        jLabel22.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel22.setText("Part Unique Id");
        add(jLabel22);
        jLabel22.setBounds(90, 90, 130, 25);

        jTextField3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField3.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField3.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField3.setNextFocusableComponent(jTextField2);
        jTextField3.setPreferredSize(new java.awt.Dimension(200, 50));
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        add(jTextField3);
        jTextField3.setBounds(220, 90, 213, 25);

        jButton9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton9.setText("Reset");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        add(jButton9);
        jButton9.setBounds(890, 550, 140, 30);

        jButton10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton10.setText("Edit Record");
        jButton10.setEnabled(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        add(jButton10);
        jButton10.setBounds(1060, 490, 130, 30);

        jTextField18.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField18.setNextFocusableComponent(jTextField6);
        add(jTextField18);
        jTextField18.setBounds(220, 350, 213, 25);

        jButton11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton11.setText("View Document");
        jButton11.setMaximumSize(new java.awt.Dimension(200, 50));
        jButton11.setMinimumSize(new java.awt.Dimension(200, 50));
        jButton11.setNextFocusableComponent(jComboBox6);
        jButton11.setPreferredSize(new java.awt.Dimension(200, 50));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        add(jButton11);
        jButton11.setBounds(690, 460, 130, 30);

        jButton12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton12.setText("View Document");
        jButton12.setMaximumSize(new java.awt.Dimension(200, 50));
        jButton12.setMinimumSize(new java.awt.Dimension(200, 50));
        jButton12.setNextFocusableComponent(jTextField13);
        jButton12.setPreferredSize(new java.awt.Dimension(200, 50));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        add(jButton12);
        jButton12.setBounds(690, 400, 130, 30);

        jButton13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton13.setText("Update BOM");
        jButton13.setEnabled(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        add(jButton13);
        jButton13.setBounds(1060, 550, 130, 30);

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 0, 51));
        jLabel26.setText("*");
        add(jLabel26);
        jLabel26.setBounds(470, 60, 20, 20);

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 51));
        jLabel27.setText("*");
        add(jLabel27);
        jLabel27.setBounds(80, 100, 20, 10);

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 0, 51));
        jLabel28.setText("*");
        add(jLabel28);
        jLabel28.setBounds(80, 130, 20, 10);

        jLabel25.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel25.setText("Product Note");
        add(jLabel25);
        jLabel25.setBounds(480, 60, 90, 16);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setNextFocusableComponent(jComboBox1);
        jScrollPane3.setViewportView(jTextArea1);

        add(jScrollPane3);
        jScrollPane3.setBounds(610, 60, 210, 80);

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 0, 51));
        jLabel29.setText("*");
        add(jLabel29);
        jLabel29.setBounds(80, 70, 20, 10);
        add(jTextField19);
        jTextField19.setBounds(220, 240, 120, 30);

        jLabel30.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel30.setText("<html>Production <br>Cost / unit</html>");
        add(jLabel30);
        jLabel30.setBounds(90, 240, 130, 30);

        add(jComboBox2);
        jComboBox2.setBounds(340, 210, 90, 30);

        add(jComboBox8);
        jComboBox8.setBounds(340, 240, 90, 30);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        try{
            Calendar c2 = Calendar.getInstance ();
            SimpleDateFormat sdf2 = new SimpleDateFormat ( "dd-MM-yyyy HH:mm:ss" );
            String strDate2 = sdf2.format ( c2.getTime () );

            String FG_PART_NO, FG_PART_UNIQUE_ID, CUSTOMER, PART_NAME, MOQ, SALES_RATE, GST_RATE, PACK_SIZE, MLD_CAVIT, TGT_OP_TXT, PART_DRG, RBR_BTC_NO, NT_WGHT, GRS_WGHT, OP_SKILL_LEVEL, IMPORTANCE, PART_VALID_DOC, PART_PPAP, PART_IMG, MODL_SOP, TESTNG_ID, INSP_FRQ, PREF_MCH, MTL_INST_IMG, PART_UNIQUE_ID, CREATED_ON, EDITED_ON, EDITED_BY, NOTES, PROD_COST;

            FG_PART_NO = jTextField1.getText ();
            FG_PART_UNIQUE_ID = jTextField3.getText ();
            PART_NAME = jTextField2.getText ();
            CUSTOMER = jComboBox1.getSelectedItem ().toString ();
            MOQ = jTextField4.getText ();
            SALES_RATE = jTextField5.getText ();
            // GST_RATE = String.valueOf(jComboBox2.getSelectedItem().toString());
            GST_RATE = jTextField18.getText ();
            PACK_SIZE = jList1.getSelectedValue ();
            MLD_CAVIT = jTextField6.getText ();
            TGT_OP_TXT = jTextField7.getText ();
            PART_DRG = jTextField11.getText ();
            // PART_DRG = partDrawingFile_name ;
            GRS_WGHT = jTextField9.getText ();
            NT_WGHT = jTextField8.getText ();
            RBR_BTC_NO = jTextField10.getText ();
            OP_SKILL_LEVEL = String.valueOf ( jComboBox3.getSelectedItem ().toString () );
            IMPORTANCE = String.valueOf ( jComboBox5.getSelectedItem ().toString () );
            PART_VALID_DOC = jTextField12.getText ();
            PART_PPAP = jTextField14.getText ();
            PART_IMG = jTextField15.getText ();
            MODL_SOP = jTextField16.getText ();
            TESTNG_ID = jTextField13.getText ();
            INSP_FRQ = String.valueOf ( jComboBox6.getSelectedItem ().toString () );
            PREF_MCH = String.valueOf ( machines.get(jComboBox7.getSelectedIndex())[0] );
            MTL_INST_IMG = jTextField17.getText ();
            PROD_COST = jTextField5.getText ();

            PART_UNIQUE_ID = jTextField3.getText ();
            NOTES = jTextArea1.getText ().trim();

            CREATED_ON = strDate2;
            EDITED_ON = strDate2;
            EDITED_BY = Proman_User.getUsername();

            if (  ! FG_PART_NO.equalsIgnoreCase ( "" )
                    &&  ! FG_PART_UNIQUE_ID.equalsIgnoreCase ( "" )
                    &&  ! PART_NAME.equalsIgnoreCase ( "" )
                    &&  ! NOTES.equalsIgnoreCase ( "" ) ) {

                    String result = "";//DB_Operations.insertIntoFinishedGoodsMaster ( FG_PART_NO , FG_PART_UNIQUE_ID , PART_NAME , CUSTOMER , MOQ , SALES_RATE , GST_RATE , PACK_SIZE , MLD_CAVIT , TGT_OP_TXT , PART_DRG , RBR_BTC_NO , NT_WGHT , GRS_WGHT , OP_SKILL_LEVEL , IMPORTANCE , PART_VALID_DOC , PART_PPAP , PART_IMG , MODL_SOP , TESTNG_ID , INSP_FRQ , PREF_MCH , MTL_INST_IMG , CREATED_ON , EDITED_ON , EDITED_BY , NOTES, PROD_COST);

                    if(!result.contains ( "Error")){
                        JOptionPane.showMessageDialog ( null ,  result );
                        loadContent ();
                        resetFields ();
                    }else{
                        JOptionPane.showMessageDialog ( null ,  result );
                    }

                }else{
                        JOptionPane.showMessageDialog ( null , "Please fill all fields marked with * "  );
                }
        }catch(Exception exx){
            System.out.println ( " "+exx.getMessage () );
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    public void resetFields () {

        jTextField1.setText ( "" );
        jTextField3.setText ( "" );
        jTextField2.setText ( "" );

        jTextField4.setText ( "" );
        jTextField5.setText ( "" );

        jList1.getSelectedValue ();
        jTextField6.setText ( "" );
        jTextField7.setText ( "" );
        jTextField11.setText ( "" );
        jTextField8.setText ( "" );
        jTextField9.setText ( "" );
        jTextField10.setText ( "" );

        jTextField12.setText ( "" );
        jTextField14.setText ( "" );
        jTextField15.setText ( "" );
        jTextField16.setText ( "" );
        jTextField13.setText ( "" );
        jTextField18.setText ( "" );

        jTextField17.setText ( "" );
         jTextArea1.setText ( "" );
          jTextField5.setText ( "" );

        jTextField3.setText ( "" );

    }

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        Workbook wb = new HSSFWorkbook ();

        ArrayList<String> column_names = new ArrayList<String> ();
        ArrayList<String[]> values = new ArrayList<String[]> ();
        int columnCount = 0;

        File file = null;

        try {
            ResultSet result = DB_Operations.getMasters ( "finished_goods" );
            if ( result != null ) {
                ResultSetMetaData metaData = result.getMetaData ();
                // names of columns
                columnCount = metaData.getColumnCount ();
                for ( int column = 1 ; column <= columnCount - 3 ; column ++ ) {
                    column_names.add ( metaData.getColumnName ( column ) );
                }
            }
            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
            while ( result.next () ) {
                String[] val = new String[ columnCount ];
                for ( int columnIndex = 0 ; columnIndex < columnCount ; columnIndex ++ ) {
                   // if ( columnIndex < columnCount - 2 ) {
                        val[ columnIndex - 2 ] = String.valueOf ( result.getObject ( columnIndex ) );
                 //   }
                }
                values.add ( val );
            }
        } catch ( SQLException e ) {

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
        sheet1 = wb.createSheet ( "Finished Goods Master" );
        sheet1.setColumnWidth ( 0 , ( 15 * 500 ) );

        Cell c = null;
        Cell c3 = null;

        Row row = sheet1.createRow ( 1 );
        //  c = row.createCell(0);
        // c.setCellValue("");
        // c.setCellStyle(cs);

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
            file = new File ( "Finished Goods Report File " + strDate2 + ".xls" );

            FileOutputStream os = null;

            try {
                os = new FileOutputStream ( file );
                wb.write ( os );
                wb.close ();
                os.close ();

            } catch ( IOException e ) {
                JOptionPane.showMessageDialog ( null , "Error " + e.getMessage () );
            } finally {

            }
        }
        JOptionPane.showMessageDialog ( null , "New File generated at " + file.getAbsolutePath () );
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        jButton6.setEnabled ( false );
        jButton13.setEnabled ( true );

        ArrayList<String> values = new ArrayList<String> ();
        // get the model from the jtable
        DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();
        int columnCount = 0;
        // get the selected row index
        int selectedRowIndex = jTable1.getSelectedRow ();
        // Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString() )
        //       JOptionPane.showMessageDialog(null, "" + model.getValueAt(selectedRowIndex, 0).toString() );
        try {
            ResultSet result = DB_Operations.getSingleMasters ( "finished_goods" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );

            selectedFGId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );

            if ( result != null ) {
                ResultSetMetaData metaData = result.getMetaData ();

                columnCount = metaData.getColumnCount ();

                while ( result.next () ) {

                    for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {
                        if ( columnIndex < columnCount - 2 ) {

                            values.add ( String.valueOf ( result.getObject ( columnIndex ) ) );
                        }
                    }
                }
            }
        } catch ( SQLException e ) {

            System.out.println ( "Error " + e.getMessage () );
        }

        jTextField1.setText ( values.get ( 2 ) );
        jTextField3.setText ( values.get ( 1 ) );
        jTextField2.setText ( values.get ( 3 ) );
        jTextField4.setText ( values.get ( 5 ) );
        jTextField5.setText ( values.get ( 6 ) );
        jTextField18.setText ( values.get ( 7 ) );
        jTextField6.setText ( values.get ( 9 ) );
        jTextField7.setText ( values.get ( 10 ) );
        jTextField11.setText ( values.get ( 11 ) );
        jTextField8.setText ( values.get ( 12 ) );
        jTextField9.setText ( values.get ( 13 ) );
        jTextField10.setText ( values.get ( 14 ) );
        jTextField12.setText ( values.get ( 17 ) );
        jTextField14.setText ( values.get ( 18 ) );
        jTextField15.setText ( values.get ( 19 ) );
        jTextField16.setText ( values.get ( 20 ) );
        jTextField13.setText ( values.get ( 21 ) );
        jTextField17.setText ( values.get ( 24 ) );

        jButton10.setEnabled ( true );


    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jTextField12.setText ( createFileNameResource ( "PartValidation" ) );
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        jTextField17.setText ( createFileNameResource ( "MetalInsertPhoto" ) );

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        resetFields ();
        jButton6.setEnabled ( true );
        jButton10.setEnabled ( false );
        jButton13.setEnabled ( false );
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        home.removeForms ();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "dd-MM-yyyy HH:mm:ss" );
        String strDate2 = sdf2.format ( c2.getTime () );

        String FG_PART_NO, FG_PART_UNIQUE_ID, CUSTOMER, PART_NAME, MOQ, SALES_RATE, GST_RATE, PACK_SIZE, MLD_CAVIT, TGT_OP_TXT, PART_DRG, RBR_BTC_NO, NT_WGHT, GRS_WGHT, OP_SKILL_LEVEL, IMPORTANCE, PART_VALID_DOC, PART_PPAP, PART_IMG, MODL_SOP, TESTNG_ID, INSP_FRQ, PREF_MCH, MTL_INST_IMG, PART_UNIQUE_ID, CREATED_ON, EDITED_ON, EDITED_BY, NOTES, PROD_COST;
        int ID;

        FG_PART_NO = jTextField1.getText ();
        FG_PART_UNIQUE_ID = jTextField3.getText ();
        PART_NAME = jTextField2.getText ();
        CUSTOMER = jComboBox1.getSelectedItem ().toString ();
        MOQ = jTextField4.getText ();
        SALES_RATE = jTextField5.getText ();
        GST_RATE = jTextField18.getText ();
        PACK_SIZE = jList1.getSelectedValue ();
        MLD_CAVIT = jTextField6.getText ();
        TGT_OP_TXT = jTextField7.getText ();
        PART_DRG = jTextField11.getText ();
        RBR_BTC_NO = jTextField8.getText ();
        NT_WGHT = jTextField9.getText ();
        GRS_WGHT = jTextField10.getText ();
        OP_SKILL_LEVEL = String.valueOf ( jComboBox3.getSelectedItem ().toString () );
        IMPORTANCE = String.valueOf ( jComboBox5.getSelectedItem ().toString () );
        PART_VALID_DOC = jTextField12.getText ();
        PART_PPAP = jTextField14.getText ();
        PART_IMG = jTextField15.getText ();
        MODL_SOP = jTextField16.getText ();
        TESTNG_ID = jTextField13.getText ();
        INSP_FRQ = String.valueOf ( jComboBox6.getSelectedItem ().toString () );
        PREF_MCH = String.valueOf ( jComboBox7.getSelectedItem ().toString () );
        MTL_INST_IMG = jTextField17.getText ();
        PROD_COST = jTextField5.getText ();

        PART_UNIQUE_ID = jTextField3.getText ();
        
        NOTES = jTextArea1.getText ().trim();
        

        ID = selectedFGId;
        EDITED_ON = strDate2;
        EDITED_BY = Proman_User.getUsername();

        if (   ! FG_PART_NO.equalsIgnoreCase ( "" )
                &&  ! FG_PART_UNIQUE_ID.equalsIgnoreCase ( "" )
                &&  ! PART_NAME.equalsIgnoreCase ( "" )
                  &&  ! NOTES.equalsIgnoreCase ( "" ) ) {

                    String result = "";//DB_Operations.updateFinishedGoodsMaster ( FG_PART_NO , FG_PART_UNIQUE_ID , PART_NAME , CUSTOMER , MOQ , SALES_RATE , GST_RATE , PACK_SIZE , MLD_CAVIT , TGT_OP_TXT , PART_DRG , RBR_BTC_NO , NT_WGHT , GRS_WGHT , OP_SKILL_LEVEL , IMPORTANCE , PART_VALID_DOC , PART_PPAP , PART_IMG , MODL_SOP , TESTNG_ID , INSP_FRQ , PREF_MCH , MTL_INST_IMG , EDITED_ON , EDITED_BY , PROD_COST, ID );

                    JOptionPane.showMessageDialog ( null , "" + result );

                    loadContent ();

                    resetFields ();
                }else{
                    JOptionPane.showMessageDialog ( null , "Please fill all fields appropriately"  );
                }

    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        jTextField11.setText ( createFileNameResource ( "PartDrawingFile" ) );
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField11MouseClicked
        // TODO add your handling code here:

        openFile ( jTextField11.getText () );
    }//GEN-LAST:event_jTextField11MouseClicked

    private void jTextField12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField12MouseClicked
        // TODO add your handling code here:

        openFile ( jTextField12.getText () );
    }//GEN-LAST:event_jTextField12MouseClicked

    private void jTextField14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField14MouseClicked
        // TODO add your handling code here:
        openFile ( jTextField14.getText () );
    }//GEN-LAST:event_jTextField14MouseClicked

    private void jTextField15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField15MouseClicked
        // TODO add your handling code here:
        openFile ( jTextField15.getText () );
    }//GEN-LAST:event_jTextField15MouseClicked

    private void jTextField16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField16MouseClicked
        // TODO add your handling code here:
        openFile ( jTextField16.getText () );
    }//GEN-LAST:event_jTextField16MouseClicked

    private void jTextField13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField13MouseClicked
        // TODO add your handling code here:
        openFile ( jTextField13.getText () );
    }//GEN-LAST:event_jTextField13MouseClicked

    private void jTextField17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField17MouseClicked
        // TODO add your handling code here:
        openFile ( jTextField17.getText () );
    }//GEN-LAST:event_jTextField17MouseClicked

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        jTextField13.setText ( createFileNameResource ( "FG_Testing" ) );
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:

        jTextField16.setText ( createFileNameResource ( "MoldingSOP" ) );

    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        jTextField15.setText ( createFileNameResource ( "PartPhoto" ) );
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        jTextField14.setText ( createFileNameResource ( "PPAP" ) );
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:

        bom = new UpdateBOMDialog ( HomeScreen3.home , selectedFGId );
        bom.setVisible ( true );

    }//GEN-LAST:event_jButton13ActionPerformed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked

        if(jComboBox1.getItemCount()==0){               
            JOptionPane.showMessageDialog(null, "<html><size='06'>First add values in customer from Customer Master");
    }           // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox4MouseClicked
      if(jComboBox4.getItemCount()==0){  
     JOptionPane.showMessageDialog(null, "<html><size='06'>First add values in MOQ from Utility Master");    
    }   
    }//GEN-LAST:event_jComboBox4MouseClicked

    private void jComboBox7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox7MouseClicked
if(jComboBox7.getItemCount()==0){
        
    JOptionPane.showMessageDialog(null, "<html><size='06'>First add values in prefered machine from Machine Master");

    }          // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7MouseClicked

    public static UpdateBOMDialog bom;
    public String createFileNameResource ( String columnDocumentName ) {

        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
        String strDate2 = sdf2.format ( c2.getTime () );

        FileChannel inputChannel = null;
        FileChannel outputChannel = null;

        JFileChooser fileChooser = new JFileChooser ();
        fileChooser.setFileFilter ( new MyCustomFilter() );
        fileChooser.setCurrentDirectory ( new File ( System.getProperty ( "user.home" ) ) );
        int result = fileChooser.showOpenDialog ( this );
        if ( result == JFileChooser.APPROVE_OPTION ) {
            File selectedFile = fileChooser.getSelectedFile ();

            File dir = new File ( "UploadedFiles" );
            dir.mkdir ();

            partDrawingFile_name = columnDocumentName + "_(" + selectedFile.getName () + ") _saved_on" + strDate2 + selectedFile.getName ().substring ( selectedFile.getName ().lastIndexOf ( '.' ) );

            try {
                inputChannel = new FileInputStream ( selectedFile ).getChannel ();
                outputChannel = new FileOutputStream ( new File ( dir , partDrawingFile_name ) ).getChannel ();
                outputChannel.transferFrom ( inputChannel , 0 , inputChannel.size () );

                inputChannel.close ();
                outputChannel.close ();

            } catch ( FileNotFoundException e1 ) {

            } catch ( IOException e2 ) {

            }
        }
        return partDrawingFile_name;
    }

    public void openFile ( String filename ) {

        if (  ! filename.equals ( "" ) ) {
            try {
                String fileName = filename;

                File dir = new File ( "uploadedFiles" );

                File file = new File ( dir , fileName );

                Desktop desktop = Desktop.getDesktop ();
                if ( file.exists () ) {
                    desktop.open ( file );
                }
            } catch ( IOException ex ) {
                //    Logger.getLogger(FinishedGoodMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    int selectedFGId = 0;
    String partDrawingFile_name = "";

}
