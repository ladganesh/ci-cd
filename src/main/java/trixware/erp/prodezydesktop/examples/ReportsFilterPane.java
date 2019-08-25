/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import static java.lang.Thread.sleep;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;
import trixware.erp.prodezydesktop.Model.EmployeeDR;
import trixware.erp.prodezydesktop.Model.MachineDR;
import trixware.erp.prodezydesktop.Model.ProcessDR;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.Rejection_Reasons;
import trixware.erp.prodezydesktop.Model.ShiftDR;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
/**
 *
 * @author Rajesh
 */
public class ReportsFilterPane extends javax.swing.JPanel {

    static ArrayList<ProductDR> products = null;
    ArrayList<EmployeeDR> employees = null;
    static ArrayList<ProcessDR> processes = null;
    ArrayList<MachineDR> machines = null;
    ArrayList<ShiftDR> shifts = null;

    String selectAll = "-- select --";

    ProductDR prdr = null;
    EmployeeDR empdr = null;
    ProcessDR prcdr = null;
    MachineDR mcdr = null;
    ShiftDR sftdr = null;
//HashMap<String, String> entity = null ;

//SimpleDateFormat sdf = null;
    static String fromDate, toDate;

    String reportLabel1 = "Process Wise / ProductWise Report for ";
    String reportLabel2 = "Date Wise report for all processes for product";
    String reportLabel3 = "Date wIse combined report for selected process";
    String reportLabel4 = "Combined report between";

    public static int resIndex = 0;

    static Vector<Vector<Object>> data = null;
    static Vector<String> columnNames = null;
    static DefaultCategoryDataset dataSet = null;
    static ArrayList<String[]> abc = null;
    static ChartPanel cp = null;

    Calendar c1 = Calendar.getInstance ();
    Calendar c2 = Calendar.getInstance ();

    int columnCount;
    ArrayList<Rejection_Reasons> rejection_reasons = null;

    Rejection_Reasons rejRsn = null;

    /**
     * Creates new form ReportsFilterPane
     */
    public ReportsFilterPane () {
        initComponents ();
        loadComboBoxes ();
    }

    
    public void loadComboBoxes () {

        String queryOne = "SELECT FG_ID, PART_NAME FROM finished_goods";
        String queryTwo = "SELECT EmployeePK, EMP_NAME FROM employee";
        String queryThree = "SELECT MCH_ID, MACHINE_NO FROM machine";
        String queryFour = "SELECT PROCESS_ID, PROCESS_NAME FROM PROCESS_MASTER";
        String queryFive = "SELECT shiftid, shifttitle FROM shifts";
        

        ResultSet rs = null;

        try {
            rs = DB_Operations.executeSingle ( queryFive );
            //  products = new ArrayList<HashMap<String, String>> ();
            shifts = new ArrayList<ShiftDR> ();
            while ( rs.next () ) {
                //   entity =  new  HashMap<String, String>();
                sftdr = new ShiftDR ();
                //    entity.put ( rs.getString(1) /*  String.valueOf( rs.getInt(1) ) */, rs.getString(2)) ;
                sftdr.SHIFT_ID = Integer.parseInt ( rs.getString ( 1 ) );
                sftdr.SHIFT_NAME  = rs.getString ( 2 );
                shifts.add ( sftdr );
                jComboBox6.addItem ( rs.getString ( 2 ) );
            }
            System.out.println ( "done with shift dropdown ");
        } catch ( Exception e ) {
            System.out.println ( " Error 1 " + e.getMessage () );
        }
        
        // ------------------------------------------------------------------------------------
        
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
                jComboBox1.addItem ( rs.getString ( 2 ) );
            }
        } catch ( Exception e ) {
            System.out.println ( " Error 1 " + e.getMessage () );
        }

        try {
            rs = DB_Operations.executeSingle ( queryTwo );
            // employees = new ArrayList<HashMap<String, String>> ();

            employees = new ArrayList<EmployeeDR> ();
            while ( rs.next () ) {
                empdr = new EmployeeDR ();

                empdr.EMP_ID = Integer.parseInt ( rs.getString ( 1 ) );
                empdr.EMP_NAME = rs.getString ( 2 );
                employees.add ( empdr );
                jComboBox3.addItem ( rs.getString ( 2 ) );
            }
        } catch ( Exception e ) {
            System.out.println ( " Error 2 " + e.getMessage () );
        }

        try {
            rs = DB_Operations.executeSingle ( queryThree );
            machines = new ArrayList<MachineDR> ();

            while ( rs.next () ) {
                mcdr = new MachineDR ();

                mcdr.MC_ID = Integer.parseInt ( rs.getString ( 1 ) );
                mcdr.MC_NAME = rs.getString ( 2 );
                machines.add ( mcdr );
                jComboBox2.addItem ( rs.getString ( 2 ) );
            }
        } catch ( Exception e ) {
            System.out.println ( " Error 2 " + e.getMessage () );
        }

        try {
            rs = DB_Operations.executeSingle ( queryFour );
            processes = new ArrayList<ProcessDR> ();

            while ( rs.next () ) {
                prcdr = new ProcessDR ();

                prcdr.PRC_ID = Integer.parseInt ( rs.getString ( 1 ) );
                prcdr.PRC_NAME = rs.getString ( 2 );
                processes.add ( prcdr );
                jComboBox4.addItem ( rs.getString ( 2 ) );
            }

        } catch ( Exception e ) {
            System.out.println ( " Error 2 " + e.getMessage () );
        }

        try {
            
            rs = DB_Operations.executeSingle ( queryFive );
            rejection_reasons = new ArrayList<Rejection_Reasons> ();

            while ( rs.next () ) {
                rejRsn = new Rejection_Reasons ();

                rejRsn.REJ_ID = Integer.parseInt ( rs.getString ( 1 ) );
                rejRsn.REJ_DESC = rs.getString ( 2 );
                rejection_reasons.add ( rejRsn );
                jComboBox6.addItem ( rs.getString ( 2 ) );
            }

        } catch ( SQLException e ) {
                System.out.println ( " Error in rejection reason " + e.getMessage () );
        }

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jCheckBox4 = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jCheckBox5 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        dateChooserCombo2 = new datechooser.beans.DateChooserCombo();

        setPreferredSize(new java.awt.Dimension(45, 650));
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                formFocusLost(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                formPropertyChange(evt);
            }
        });
        setLayout(null);

        dateChooserCombo1.setFormat(2);
        try {
            dateChooserCombo1.setDefaultPeriods(new datechooser.model.multiple.PeriodSet(new datechooser.model.multiple.Period(new java.util.GregorianCalendar(2018, 9, 1),
                new java.util.GregorianCalendar(2018, 9, 1))));
    } catch (datechooser.model.exeptions.IncompatibleDataExeption e1) {
        e1.printStackTrace();
    }
    add(dateChooserCombo1);
    dateChooserCombo1.setBounds(30, 90, 155, 20);

    jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jLabel1.setForeground(new java.awt.Color(255, 255, 255));
    jLabel1.setText("From Date");
    add(jLabel1);
    jLabel1.setBounds(30, 70, 80, 16);

    jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jLabel11.setForeground(new java.awt.Color(255, 255, 255));
    jLabel11.setText("To Date");
    add(jLabel11);
    jLabel11.setBounds(30, 120, 80, 16);

    jComboBox5.setBackground(new java.awt.Color(195, 238, 66));
    jComboBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Daily", "Weekly", "Monthly", "Quarterly", "Yearly" }));
    jComboBox5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jComboBox5ActionPerformed(evt);
        }
    });
    add(jComboBox5);
    jComboBox5.setBounds(30, 190, 160, 30);

    jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jLabel2.setForeground(new java.awt.Color(255, 255, 255));
    jLabel2.setText("Product");
    add(jLabel2);
    jLabel2.setBounds(30, 230, 70, 16);

    jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jComboBox1MouseClicked(evt);
        }
    });
    add(jComboBox1);
    jComboBox1.setBounds(30, 250, 160, 30);

    jCheckBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jCheckBox1.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox1.setText("Select All");
    jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jCheckBox1ActionPerformed(evt);
        }
    });
    add(jCheckBox1);
    jCheckBox1.setBounds(200, 250, 76, 24);

    jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jLabel12.setForeground(new java.awt.Color(255, 255, 255));
    jLabel12.setText("Process");
    add(jLabel12);
    jLabel12.setBounds(30, 290, 70, 16);

    jComboBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jComboBox4.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jComboBox4MouseClicked(evt);
        }
    });
    add(jComboBox4);
    jComboBox4.setBounds(30, 310, 160, 30);

    jCheckBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jCheckBox2.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox2.setSelected(true);
    jCheckBox2.setText("Select All");
    jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jCheckBox2ActionPerformed(evt);
        }
    });
    add(jCheckBox2);
    jCheckBox2.setBounds(200, 310, 76, 24);

    jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jLabel7.setForeground(new java.awt.Color(255, 255, 255));
    jLabel7.setText("Machine");
    add(jLabel7);
    jLabel7.setBounds(30, 350, 70, 16);

    jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jComboBox2MouseClicked(evt);
        }
    });
    add(jComboBox2);
    jComboBox2.setBounds(30, 370, 160, 30);

    jCheckBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jCheckBox3.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox3.setSelected(true);
    jCheckBox3.setText("Select All");
    jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jCheckBox3ActionPerformed(evt);
        }
    });
    add(jCheckBox3);
    jCheckBox3.setBounds(200, 370, 76, 24);

    jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jLabel8.setForeground(new java.awt.Color(255, 255, 255));
    jLabel8.setText("Employee");
    add(jLabel8);
    jLabel8.setBounds(30, 410, 80, 16);

    jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jComboBox3.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jComboBox3MouseClicked(evt);
        }
    });
    add(jComboBox3);
    jComboBox3.setBounds(30, 430, 160, 30);

    jCheckBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jCheckBox4.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox4.setSelected(true);
    jCheckBox4.setText("Select All");
    jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jCheckBox4ActionPerformed(evt);
        }
    });
    add(jCheckBox4);
    jCheckBox4.setBounds(200, 430, 76, 24);

    jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jLabel3.setForeground(new java.awt.Color(255, 255, 255));
    jLabel3.setText("Rejection Reasons");
    add(jLabel3);
    jLabel3.setBounds(30, 470, 130, 16);

    jComboBox6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    add(jComboBox6);
    jComboBox6.setBounds(30, 490, 160, 30);

    jCheckBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jCheckBox5.setForeground(new java.awt.Color(255, 255, 255));
    jCheckBox5.setSelected(true);
    jCheckBox5.setText("Select All");
    jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jCheckBox5ActionPerformed(evt);
        }
    });
    add(jCheckBox5);
    jCheckBox5.setBounds(200, 490, 76, 30);

    jButton1.setBackground(new java.awt.Color(204, 255, 102));
    jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jButton1.setText("Generate Report");
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
    jButton1.setBounds(120, 560, 160, 32);

    jButton2.setBackground(new java.awt.Color(204, 255, 51));
    jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jButton2.setText("Close");
    jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jButton2MouseClicked(evt);
        }
    });
    add(jButton2);
    jButton2.setBounds(120, 600, 160, 32);

    jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Utilities/assets/right_arrow.png"))); // NOI18N
    add(jLabel4);
    jLabel4.setBounds(0, 10, 45, 50);

    try {
        dateChooserCombo2.setDefaultPeriods(new datechooser.model.multiple.PeriodSet(new datechooser.model.multiple.Period(new java.util.GregorianCalendar(2018, 9, 31),
            new java.util.GregorianCalendar(2018, 9, 31))));
} catch (datechooser.model.exeptions.IncompatibleDataExeption e1) {
    e1.printStackTrace();
    }
    add(dateChooserCombo2);
    dateChooserCombo2.setBounds(30, 150, 155, 20);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
        //        JComboBox filter = (JComboBox) evt.getSource();
        //        String abc = filter.getSelectedItem ().toString () ;
        //
        //         if(abc.equals ( "Daily" )){
        //             dateChooserCombo3.setEnabled ( true );
        //             dateChooserCombo4.setEnabled ( true );
        //         }else{
        //             dateChooserCombo3.setEnabled ( false );
        //             dateChooserCombo4.setEnabled ( false);
        //         }
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        if ( jComboBox1.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add product from Finished_Good Master " );
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        JCheckBox c = ( JCheckBox ) evt.getSource ();
        if ( c.isSelected () ) {
            jComboBox1.setEnabled ( false );
        } else {
            jComboBox1.setEnabled ( true );
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jComboBox4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox4MouseClicked
        if ( jComboBox4.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add process from Processes Master " );
        }         // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4MouseClicked

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
        JCheckBox c = ( JCheckBox ) evt.getSource ();
        if ( c.isSelected () ) {
            jComboBox4.setEnabled ( false );
        } else {
            jComboBox4.setEnabled ( true );
        }
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
        if ( jComboBox2.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add Machine from Machine Master " );
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2MouseClicked

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
        JCheckBox c = ( JCheckBox ) evt.getSource ();
        if ( c.isSelected () ) {
            jComboBox2.setEnabled ( false );
        } else {
            jComboBox2.setEnabled ( true );
        }
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jComboBox3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox3MouseClicked
        if ( jComboBox3.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add Employee from Employee Master " );
        }         // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3MouseClicked

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
        // TODO add your handling code here:
        JCheckBox c = ( JCheckBox ) evt.getSource ();
        if ( c.isSelected () ) {
            jComboBox3.setEnabled ( false );
        } else {
            jComboBox3.setEnabled ( true );
        }
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
        // TODO add your handling code here:
        JCheckBox c = ( JCheckBox ) evt.getSource ();
        if ( c.isSelected () ) {
            jComboBox6.setEnabled ( false );

        } else {
            jComboBox6.setEnabled ( true );

        }
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked

        int process, product, machine, employee, rejReason;

        System.out.println ( "  class name "+getParent ().getClass ().getName () );
        ProductionNWIP3 obj = null ;
        if(  getParent ().getClass ().getName ().equals ( "examples.ProductionNWIP3") ){
            
            obj = new ProductionNWIP3();
            
        }
        
        formatFromDate ();
        formatToDate ();
       
        
        product = products.get ( jComboBox1.getSelectedIndex () ).FG_ID;
        machine = machines.get ( jComboBox2.getSelectedIndex () ).MC_ID;
        process = processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID;
        employee = employees.get ( jComboBox3.getSelectedIndex () ).EMP_ID;
        rejReason = rejection_reasons.get ( jComboBox6.getSelectedIndex () ).REJ_ID;

        String reportType = jComboBox5.getSelectedItem ().toString ();

        //   System.out.println(  "The From date is "+formatFromDate ()  );
        //   System.out.println(  "The To date is "+formatToDate ()  );
        System.out.println ( "Total no of days " + ( ( ( ( ( c2.getTimeInMillis () - c1.getTimeInMillis () ) / 1000 ) / 60 ) / 60 ) / 24 ) );

        //    WeekCalculation week = new WeekCalculation (formatFromDate (), formatToDate ());
        

        //jCheckBox2
        //machine
        //jCheckBox3
        //employee
        //jCheckBox4
        //process
        if (  ! jCheckBox1.isSelected () && jCheckBox2.isSelected () && jCheckBox3.isSelected () && jCheckBox4.isSelected () && jCheckBox5.isSelected () ) {

            obj.loadEntries( reportType, product , formatFromDate (), formatToDate ());
            
            
        } else if ( jCheckBox1.isSelected () && jCheckBox2.isSelected () && jCheckBox3.isSelected () && jCheckBox4.isSelected () && jCheckBox5.isSelected () ) {

            //for a product     for a process       for a  machine      for an employee
            //  loadEntriesAll (reportType , product );
            //        loadEntriesAll ( reportType , product );
        } else if (  ! jCheckBox1.isSelected () &&  ! jCheckBox2.isSelected () && jCheckBox3.isSelected () && jCheckBox4.isSelected () && jCheckBox5.isSelected () ) {

            //for a product     for a process       for a  machine      for an employee
            //       loadEntriesOnlyProcess (reportType , product, process );
        } else if (  ! jCheckBox1.isSelected () &&  ! jCheckBox2.isSelected () &&  ! jCheckBox3.isSelected () && jCheckBox4.isSelected () && jCheckBox5.isSelected () ) {

            //for a product     for a process       for a  machine      for an employee
            //       loadEntriesProcessAndMachine (reportType , product, process, machine );
        } else if (  ! jCheckBox1.isSelected () &&  ! jCheckBox2.isSelected () &&  ! jCheckBox3.isSelected () &&  ! jCheckBox4.isSelected () && jCheckBox5.isSelected () ) {

            //for a product     for a process       for a  machine      for an employee
            //     loadEntriesAllSelected (reportType , product, process, machine, employee );
        } else if (  ! jCheckBox1.isSelected () &&  ! jCheckBox2.isSelected () &&  ! jCheckBox3.isSelected () &&  ! jCheckBox4.isSelected () &&  ! jCheckBox5.isSelected () ) {

            //for a product     for a process       for a  machine      for an employee
            //      loadEntriesAllProcess (reportType , product, process, machine, employee,  rejReason);
        } else if ( jCheckBox1.isSelected () && jCheckBox2.isSelected () && jCheckBox3.isSelected () && jCheckBox4.isSelected () &&  ! jCheckBox5.isSelected () ) {

            //for a product     for a process       for a  machine      for an employee
            //        loadEntriesAllMachine (reportType , product, rejReason );
        }
        //else if ( jCheckBox1.isSelected () ) {
        //
        //            loadEntriesProductWise ( reportType );
        //
        //        } else if ( jCheckBox2.isSelected () ) {
        //
        //            loadEntriesMachineWise ( reportType, product, process, employee );
        //
        //        } else {
        //
        //            loadEntries ( reportType , product );
        //
        //        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        HomeScreen.home.removeForms ();
    }//GEN-LAST:event_jButton2MouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        try {
            if ( getWidth () == 45 ) {

             //   t.start ();
             ControlSubThread t1 = new ControlSubThread ( 6000 );
             t1.start();
             jLabel4.setVisible ( false );
            } else {

                setBounds ( 0 , 0 , 45 , 1300 );
             jLabel4.setVisible ( true );   
            }

        } catch ( IllegalThreadStateException er ) {
           
        }
    }//GEN-LAST:event_formMouseClicked

    private void formFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusLost
        // TODO add your handling code here:
        //t2.start();
    }//GEN-LAST:event_formFocusLost

    private void formPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_formPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_formPropertyChange


    class ControlSubThread implements Runnable {
 
    private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private int interval;
 
    public ControlSubThread(int sleepInterval) {
        interval = sleepInterval;
    }
  
    public void start() {
        worker = new Thread(this);
        worker.start();
    }
  
    public void stop() {
        running.set(false);
    }
 
    public void run() { 
        running.set(true);
        while (running.get()) {
           try {

                if ( getWidth () == 45 ) {

                    for ( int i = 5 ; i <= 30 ; i ++ ) {

                        if ( i == 30 ) {
                            setBounds ( 0 , 0 , 301 , 1300 );
                            
                        //    stop();
                        running.set(false);
                        }else{
                            setBounds ( 0 , 0 , 10*i , 1300 );
                        }

                        
                        sleep ( 10 );
                           
                    }

                }
                revalidate ();
                repaint ();
            } catch ( InterruptedException e ) {
               
            }
         } 
    } 
}
   

    SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-MM-dd" );

    public   String formatFromDate () {

        String[] dateIP = dateChooserCombo1.getText ().split ( " " );

        String fromDate = null;

        if ( dateIP[ 1 ].length () == 3 ) {
            fromDate = dateIP[ 1 ].substring ( 0 , 2 ) + "-";
            c1.set ( Calendar.DATE , Integer.parseInt ( dateIP[ 1 ].substring ( 0 , 2 ) ) );
        } else if ( dateIP[ 1 ].length () == 2 ) {
            fromDate = "0" + dateIP[ 1 ].substring ( 0 , 1 ) + "-";
            c1.set ( Calendar.DATE , Integer.parseInt ( dateIP[ 1 ].substring ( 0 , 1 ) ) );
        }

        String mon = dateIP[ 0 ];

        switch ( mon ) {

            case "Jan":
                fromDate = fromDate + "01-";
                c1.set ( Calendar.MONTH , 0 );
                break;
            case "Feb":
                fromDate = fromDate + "02-";
                c1.set ( Calendar.MONTH , 1 );
                break;
            case "Mar":
                fromDate = fromDate + "03-";
                c1.set ( Calendar.MONTH , 2 );
                break;
            case "Apr":
                fromDate = fromDate + "04-";
                c1.set ( Calendar.MONTH , 3 );
                break;
            case "May":
                fromDate = fromDate + "05-";
                c1.set ( Calendar.MONTH , 4 );
                break;
            case "Jun":
                fromDate = fromDate + "06-";
                c1.set ( Calendar.MONTH , 5 );
                break;
            case "Jul":
                fromDate = fromDate + "07-";
                c1.set ( Calendar.MONTH , 6 );
                break;
            case "Aug":
                fromDate = fromDate + "08-";
                c1.set ( Calendar.MONTH , 7 );
                break;
            case "Sep":
                fromDate = fromDate + "09-";
                c1.set ( Calendar.MONTH , 8 );
                break;
            case "Oct":
                fromDate = fromDate + "10-";
                c1.set ( Calendar.MONTH , 9 );
                break;
            case "Nov":
                fromDate = fromDate + "11-";
                c1.set ( Calendar.MONTH , 10 );
                break;
            case "Dec":
                fromDate = fromDate + "12-";
                c1.set ( Calendar.MONTH , 11 );
                break;

        }

        // fromDate = fromDate + dateIP[2];
        c1.set ( Calendar.YEAR , Integer.parseInt ( dateIP[ 2 ] ) );
        fromDate = sdf2.format ( c1.getTime () ) + " 00:00:00";

        //   c1.setFirstDayOfWeek ( Calendar.SUNDAY);
        //   System.out.println( "This is #"+  c1.get ( Calendar.WEEK_OF_MONTH) +" week in the month");
        //  System.out.println( "This is #"+  (c1.get ( Calendar.MONTH) +1)+"  month in the year");
        SimpleDateFormat sdf = new SimpleDateFormat ( "EEEE" );
        SimpleDateFormat sdf1 = new SimpleDateFormat ( "MMM" );
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy" );
        SimpleDateFormat sdf3 = new SimpleDateFormat ( "dd" );

     
        return fromDate;
    }

    public   String formatToDate () {

        String[] dateIP2 = dateChooserCombo2.getText ().split ( " " );
        String toDate = null;

        if ( dateIP2[ 1 ].length () == 3 ) {
            toDate = dateIP2[ 1 ].substring ( 0 , 2 ) + "-";
            c2.set ( Calendar.DATE , Integer.parseInt ( dateIP2[ 1 ].substring ( 0 , 2 ) ) );
        } else if ( dateIP2[ 1 ].length () == 2 ) {
            toDate = "0" + dateIP2[ 1 ].substring ( 0 , 1 ) + "-";
            c2.set ( Calendar.DATE , Integer.parseInt ( dateIP2[ 1 ].substring ( 0 , 1 ) ) );
        }
        String mon2 = dateIP2[ 0 ];

        switch ( mon2 ) {

            case "Jan":
                toDate = toDate + "01-";
                c2.set ( Calendar.MONTH , 0 );
                break;
            case "Feb":
                toDate = toDate + "02-";
                c2.set ( Calendar.MONTH , 1 );
                break;
            case "Mar":
                toDate = toDate + "03-";
                c2.set ( Calendar.MONTH , 2 );
                break;
            case "Apr":
                toDate = toDate + "04-";
                c2.set ( Calendar.MONTH , 3 );
                break;
            case "May":
                toDate = toDate + "05-";
                c2.set ( Calendar.MONTH , 4 );
                break;
            case "Jun":
                toDate = toDate + "06-";
                c2.set ( Calendar.MONTH , 5 );
                break;
            case "Jul":
                toDate = toDate + "07-";
                c2.set ( Calendar.MONTH , 6 );
                break;
            case "Aug":
                toDate = toDate + "08-";
                c2.set ( Calendar.MONTH , 7 );
                break;
            case "Sep":
                toDate = toDate + "09-";
                c2.set ( Calendar.MONTH , 8 );
                break;
            case "Oct":
                toDate = toDate + "10-";
                c2.set ( Calendar.MONTH , 9 );
                break;
            case "Nov":
                toDate = toDate + "11-";
                c2.set ( Calendar.MONTH , 10 );
                break;
            case "Dec":
                toDate = toDate + "12-";
                c2.set ( Calendar.MONTH , 11 );
                break;

        }

        //toDate = toDate + dateIP2[2];
        c2.set ( Calendar.YEAR , Integer.parseInt ( dateIP2[ 2 ] ) );
        toDate = sdf2.format ( c2.getTime () ) + " 00:00:00";

        c2.setFirstDayOfWeek ( Calendar.SUNDAY );
        //   System.out.println( "This is #"+  c2.get ( Calendar.WEEK_OF_MONTH) +" week in the month");
        //  System.out.println( "This is #"+  (c2.get ( Calendar.MONTH) +1)+" month in the year");

        return toDate;
    }

   
    
    
    ArrayList<String> weeklyDates = new ArrayList<String> ();
    ArrayList<String> months = new ArrayList<String> ();
    ArrayList<String> quarters = new ArrayList<String> ();
    SimpleDateFormat sdfTS = new SimpleDateFormat ( "yyyy-MM-dd" );

    SimpleDateFormat sdf = new SimpleDateFormat ( "EEEE" );
    SimpleDateFormat sdf1 = new SimpleDateFormat ( "MMM" );
    SimpleDateFormat sdf4 = new SimpleDateFormat ( "yyyy" );
    SimpleDateFormat sdf3 = new SimpleDateFormat ( "dd" );

    long totalNoOfDays = 0;

    public   void showWeek () {

//        int i=0;
//        while( i < weeklyDates.size ()){
//            weeklyDates.remove ( i );
//            i++ ;
//        }
        weeklyDates = new ArrayList<String> ();

        formatFromDate ();
        formatToDate ();

        totalNoOfDays = ( ( ( ( ( c2.getTimeInMillis () - c1.getTimeInMillis () ) / 1000 ) / 60 ) / 60 ) / 24 ) + 1;

        System.out.println ( "Total no of days " + totalNoOfDays );

        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );

        // calculation for first week of fromDate does not fall on Sunday
        String day = sdf.format ( c1.getTime () );

        //if(! (day.equalsIgnoreCase ( "Sunday"))){
        switch ( day ) {

//                case "Saturday" :      c1.add ( Calendar.DATE, 6);  totalNoOfDays=totalNoOfDays-5;  break ;
//                case "Monday" :        c1.add ( Calendar.DATE, 5);  totalNoOfDays=totalNoOfDays-4;  break ;
//                case "Tuesday" :       c1.add ( Calendar.DATE, 4);  totalNoOfDays=totalNoOfDays-3; break ;
//                case "Wednesday" :  c1.add ( Calendar.DATE, 3);  totalNoOfDays=totalNoOfDays-2; break ;
//                case "Thursday" :      c1.add ( Calendar.DATE, 2);  totalNoOfDays=totalNoOfDays-1; break ;
//                case "Friday" :          c1.add ( Calendar.DATE, 1);  totalNoOfDays=totalNoOfDays-0; break ;
            case "Sunday":
                c1.add ( Calendar.DATE , 0 );
                totalNoOfDays = totalNoOfDays - 0;
                break;
            case "Saturday":
                c1.add ( Calendar.DATE , 6 );
                totalNoOfDays = totalNoOfDays - 1;
                break;
            case "Monday":
                c1.add ( Calendar.DATE , 5 );
                totalNoOfDays = totalNoOfDays - 6;
                break;
            case "Tuesday":
                c1.add ( Calendar.DATE , 4 );
                totalNoOfDays = totalNoOfDays - 5;
                break;
            case "Wednesday":
                c1.add ( Calendar.DATE , 3 );
                totalNoOfDays = totalNoOfDays - 4;
                break;
            case "Thursday":
                c1.add ( Calendar.DATE , 2 );
                totalNoOfDays = totalNoOfDays - 3;
                break;
            case "Friday":
                c1.add ( Calendar.DATE , 1 );
                totalNoOfDays = totalNoOfDays - 2;
                break;

        }
        //}

        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );

        System.out.println ( "" );

        // Calculation for the week if fromDate fall on Sunday and all subsequent weeks till toDate does not fall on friday
        // Calculation for the last week when toDate fall on Friday
        // Calculation for inbetween weeks
        while ( totalNoOfDays > 1 ) {

            c1.add ( Calendar.DATE , 1 );
            weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );

            if ( ( int ) totalNoOfDays < 7 ) {
                switch ( ( int ) totalNoOfDays ) {
                    case 0:
                        c1.add ( Calendar.DATE , 0 );
                        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );
                        totalNoOfDays = totalNoOfDays - 0;
                        break;
                    case 1:
                        c1.add ( Calendar.DATE , 0 );
                        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );
                        totalNoOfDays = totalNoOfDays - 1;
                        break;
                    case 2:
                        c1.add ( Calendar.DATE , 1 );
                        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );
                        totalNoOfDays = totalNoOfDays - 2;
                        break;
                    case 3:
                        c1.add ( Calendar.DATE , 2 );
                        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );
                        totalNoOfDays = totalNoOfDays - 3;
                        break;
                    case 4:
                        c1.add ( Calendar.DATE , 3 );
                        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );
                        totalNoOfDays = totalNoOfDays - 4;
                        break;
                    case 5:
                        c1.add ( Calendar.DATE , 4 );
                        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );
                        totalNoOfDays = totalNoOfDays - 5;
                        break;
                    case 6:
                        c1.add ( Calendar.DATE , 5 );
                        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );
                        totalNoOfDays = totalNoOfDays - 5;
                        break;
                }
            } else {

                c1.add ( Calendar.DATE , 6 );
                weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );
                totalNoOfDays = totalNoOfDays - 7;

            }
        }

        //   for( int i=0, j=i+1; j<weeklyDates.size ();  i=i+2, j+=2 ){
        //    System.out.println( weeklyDates.get ( i) +" "+  weeklyDates.get(j));
        //    }
    }

    public   String showMonths () {

        formatFromDate ();
        Calendar c = Calendar.getInstance ();

        // System.out.println( "From month is  "+ c1.getActualMaximum ( Calendar.DAY_OF_MONTH));
        // System.out.println( "From month is  "+sdf1.format(c1.getTime()));
        for ( int i = 2 ; i <= 13 ; i ++ ) {

            c.set ( Calendar.DATE , 1 );
            c.set ( Calendar.MONTH , i + 1 );
            c.set ( Calendar.YEAR , Integer.parseInt ( sdf4.format ( c1.getTime () ) ) );

            c.set ( Calendar.HOUR , 0 );
            c.set ( Calendar.MINUTE , 0 );
            c.set ( Calendar.SECOND , 0 );

            // System.out.print( sdf2.format ( c.getTime ()) +" 00:00:00" );
            months.add ( sdf2.format ( c.getTime () ) + " 00:00:00" );

            c.set ( Calendar.DATE , c.getActualMaximum ( Calendar.DAY_OF_MONTH ) );
            c.set ( Calendar.MONTH , i + 1 );
            c.set ( Calendar.YEAR , Integer.parseInt ( sdf4.format ( c1.getTime () ) ) );

            c.set ( Calendar.HOUR , 0 );
            c.set ( Calendar.MINUTE , 0 );
            c.set ( Calendar.SECOND , 0 );

            //  System.out.println( "    "+sdf2.format ( c.getTime ())  +" 00:00:00" );
            months.add ( sdf2.format ( c.getTime () ) + " 00:00:00" );

        }

        // System.out.println( "");
        // Calculation for the week if fromDate fall on Sunday and all subsequent weeks till toDate does not fall on friday
        // Calculation for the last week when toDate fall on Friday
        return sdf4.format ( c1.getTime () );
    }

    public  String showQuarters () {

        formatFromDate ();
        Calendar c = Calendar.getInstance ();

        // System.out.println( "From month is  "+ c1.getActualMaximum ( Calendar.DAY_OF_MONTH));
        // System.out.println( "From month is  "+sdf1.format(c1.getTime()));
        for ( int i = 3 ; i <= 14 ; i += 3 ) {

            c.set ( Calendar.DATE , 1 );
            c.set ( Calendar.MONTH , i );
            c.set ( Calendar.YEAR , Integer.parseInt ( sdf4.format ( c1.getTime () ) ) );

            c.set ( Calendar.HOUR , 0 );
            c.set ( Calendar.MINUTE , 0 );
            c.set ( Calendar.SECOND , 0 );

            // System.out.print( sdf2.format ( c.getTime ()) +" 00:00:00" );
            quarters.add ( sdf2.format ( c.getTime () ) + " 00:00:00" );

            c.set ( Calendar.MONTH , i + 2 );
            c.set ( Calendar.DATE , c.getActualMaximum ( Calendar.DAY_OF_MONTH ) );
            c.set ( Calendar.YEAR , Integer.parseInt ( sdf4.format ( c1.getTime () ) ) );

            c.set ( Calendar.HOUR , 0 );
            c.set ( Calendar.MINUTE , 0 );
            c.set ( Calendar.SECOND , 0 );

            //  System.out.println( "    "+sdf2.format ( c.getTime ())  +" 00:00:00" );
            quarters.add ( sdf2.format ( c.getTime () ) + " 00:00:00" );

        }

        for ( int i = 0, j = i + 1 ; j < quarters.size () ; i = i + 2 , j += 2 ) {

            System.out.println ( quarters.get ( i ) + " " + quarters.get ( j ) );
        }

        // System.out.println( "");
        // Calculation for the week if fromDate fall on Sunday and all subsequent weeks till toDate does not fall on friday
        // Calculation for the last week when toDate fall on Friday
        return sdf4.format ( c1.getTime () );
    }

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private datechooser.beans.DateChooserCombo dateChooserCombo2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private static javax.swing.JComboBox<String> jComboBox1;
    private static javax.swing.JComboBox<String> jComboBox2;
    private static javax.swing.JComboBox<String> jComboBox3;
    private static javax.swing.JComboBox<String> jComboBox4;
    private static javax.swing.JComboBox<String> jComboBox5;
    private static javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    // End of variables declaration//GEN-END:variables
}

