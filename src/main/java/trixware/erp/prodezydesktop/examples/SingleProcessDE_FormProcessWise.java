/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import trixware.erp.prodezydesktop.Derived.AutoCompletion;
import trixware.erp.prodezydesktop.Model.DailyDataEntryModel;
import trixware.erp.prodezydesktop.Model.DailyDataEntryModel2;
import trixware.erp.prodezydesktop.Model.RejectionModel;
import trixware.erp.prodezydesktop.Model.Rejection_Reasons;
import trixware.erp.prodezydesktop.Model.ShiftDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Model.TimeLossModel;
import com.sun.corba.se.impl.util.Utility;
import trixware.erp.prodezydesktop.components.RejectionDetailPanel;
import trixware.erp.prodezydesktop.components.TimeLossDetailPanel;
import datechooser.view.WeekDaysStyle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.EmployeeDR;
import trixware.erp.prodezydesktop.Model.TimeLoss_Reasons;
import trixware.erp.prodezydesktop.Model.UOMDR;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class SingleProcessDE_FormProcessWise extends javax.swing.JPanel {

    DailyDataEntryModel2 data;

    String qin, qout, rej;

    boolean formIncomplete = false;
    
    String addEmpAPICall ,  result2 ;

    JSpinner fromTimeSpinner = new JSpinner ( new SpinnerDateModel () );
    JSpinner toTimeSpinner = new JSpinner ( new SpinnerDateModel () );
    JSpinner.DateEditor fromTimeEditor = new JSpinner.DateEditor ( fromTimeSpinner , "hh:mm a" );
    JSpinner.DateEditor toTimeEditor = new JSpinner.DateEditor ( toTimeSpinner , "hh:mm a" );

    JComboBox<JPanel> jComboBox4 = new JComboBox<JPanel> ();
    // public ArrayList<String> list = = new ArrayList<String>(    Arrays.asList(   new String{ "Time Pass","Load shading", "Chaha Nashta","Zop" }  )  );
    // public List<String> list =  new ArrayList<String>( Arrays.asList ( new String[] { "Time Pass" , "Load shading" , "Chaha Nashta" , "Zop" } );

    public ShowShiftSelection dialog = null;
    public ShowTimeLossForm dialog2 = null;
    public ShowRejectionsForm dialog3 = null;

    private int selectedShiftId = 0;
    private ArrayList<TimeLossModel> selectedTimeLoss = new ArrayList<TimeLossModel> ();
    private ArrayList<RejectionModel> selectedrejections = new ArrayList<RejectionModel> ();
    
     ArrayList<TimeLoss_Reasons> timeloss_reasons = null;
     ArrayList<Rejection_Reasons> rej_reasons = null;

    /**
     * Creates new form SingleProcessDE_Form
     */
    public SingleProcessDE_FormProcessWise () {

        initComponents ();

        //  AutoCompletion.enable ( jComboBox1 );
        //  AutoCompletion.enable ( jComboBox2 );
        // AutoCompletion.enable ( jComboBox3 );
        data = new DailyDataEntryModel2 ();
        jLabel1.setVisible ( false );

        dateChooserCombo1.setText ( dateChooserCombo3.getText () );

        dateChooserCombo1.setFormat ( DateFormat.MEDIUM );
        dateChooserCombo3.setFormat ( DateFormat.MEDIUM );
        dateChooserCombo1.setWeekStyle ( WeekDaysStyle.NORMAL );
        dateChooserCombo3.setWeekStyle ( WeekDaysStyle.NORMAL );
        dateChooserCombo1.setOpaque ( false );
        dateChooserCombo3.setOpaque ( false );
        dateChooserCombo1.setBackground ( Color.WHITE );
        dateChooserCombo3.setBackground ( Color.WHITE );
        dateChooserCombo1.revalidate ();
        dateChooserCombo3.repaint ();

        jTextField1.addKeyListener ( k );
        jTextField2.addKeyListener ( k );
        jTextField3.addKeyListener ( k );
        jTextField1.addFocusListener ( f2 );
        jTextField2.addFocusListener ( f2 );
        jTextField3.addFocusListener ( f3 );

        jTextField4.addKeyListener ( k );
        jTextField4.addFocusListener ( f );
        jTextField5.addFocusListener ( f );
        jTextField5.addKeyListener ( k );
        jTextField6.addFocusListener ( f );
        jTextField6.addKeyListener ( k );
        jTextField7.addFocusListener ( f );
        jTextField7.addKeyListener ( k );

        fromTimeSpinner.setEditor ( fromTimeEditor );
        fromTimeSpinner.setValue ( new Date () );
        fromTimeSpinner.setBounds ( 125 , 10 , 70 , 32 );
        fromTimeSpinner.setOpaque ( false );
        fromTimeSpinner.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 12 ) );
        add ( fromTimeSpinner );

        toTimeSpinner.setEditor ( toTimeEditor );
        toTimeSpinner.setValue ( new Date () );
        toTimeSpinner.setBounds ( 325 , 10 , 70 , 32 );
        toTimeSpinner.setOpaque ( false );
        toTimeSpinner.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 12 ) );
        add ( toTimeSpinner );

        jButton1.addActionListener ( shift );
        jButton2.addActionListener ( timeloss );
        jButton3.addActionListener ( rejections );

        revalidate ();
        repaint ();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jSpinner1 = new javax.swing.JSpinner();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jSpinner2 = new javax.swing.JSpinner();
        dateChooserCombo3 = new datechooser.beans.DateChooserCombo();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        UOMOUT = new javax.swing.JComboBox<>();
        UOMOUT1 = new javax.swing.JComboBox<>();
        UOMIN = new javax.swing.JComboBox<>();

        setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1310, 60));
        setLayout(null);

        jTextField1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("0");
        jTextField1.setToolTipText("");
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(570, 10, 50, 30);

        jTextField2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("0");
        jTextField2.setToolTipText("");
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });
        add(jTextField2);
        jTextField2.setBounds(630, 10, 50, 30);

        jTextField3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setText("0");
        jTextField3.setToolTipText("");
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });
        add(jTextField3);
        jTextField3.setBounds(690, 10, 50, 30);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jComboBox2);
        jComboBox2.setBounds(860, 10, 120, 22);

        jTextField4.setText("0");
        jTextField4.setPreferredSize(new java.awt.Dimension(0, 0));
        add(jTextField4);
        jTextField4.setBounds(370, 10, 0, 0);

        jTextField5.setText("0");
        jTextField5.setPreferredSize(new java.awt.Dimension(0, 0));
        add(jTextField5);
        jTextField5.setBounds(400, 10, 0, 0);

        jSpinner1.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner1.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner1.setPreferredSize(new java.awt.Dimension(0, 0));
        add(jSpinner1);
        jSpinner1.setBounds(430, 10, 0, 0);

        jTextField6.setText("0");
        jTextField6.setPreferredSize(new java.awt.Dimension(0, 0));
        jTextField6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField6FocusLost(evt);
            }
        });
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField6KeyTyped(evt);
            }
        });
        add(jTextField6);
        jTextField6.setBounds(120, 10, 0, 0);

        jTextField7.setText("0");
        jTextField7.setPreferredSize(new java.awt.Dimension(0, 0));
        add(jTextField7);
        jTextField7.setBounds(150, 10, 0, 0);

        jSpinner2.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner2.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner2.setPreferredSize(new java.awt.Dimension(0, 0));
        add(jSpinner2);
        jSpinner2.setBounds(180, 10, 0, 0);

        dateChooserCombo3.setCalendarPreferredSize(new java.awt.Dimension(320, 220));
        dateChooserCombo3.setFormat(2);
        add(dateChooserCombo3);
        dateChooserCombo3.setBounds(10, 10, 110, 30);

        dateChooserCombo1.setCalendarPreferredSize(new java.awt.Dimension(320, 220));
        try {
            dateChooserCombo1.setDefaultPeriods(dateChooserCombo3.getDefaultPeriods());
        } catch (datechooser.model.exeptions.IncompatibleDataExeption e1) {
            e1.printStackTrace();
        }
        dateChooserCombo1.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                dateChooserCombo1OnCommit(evt);
            }
        });
        add(dateChooserCombo1);
        dateChooserCombo1.setBounds(200, 10, 120, 30);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox3.setEnabled(false);
        jComboBox3.setPreferredSize(new java.awt.Dimension(0, 0));
        add(jComboBox3);
        jComboBox3.setBounds(890, 10, 0, 0);

        jLabel1.setText("jLabel1");
        add(jLabel1);
        jLabel1.setBounds(500, 10, 10, 30);

        jLabel2.setText("jLabel2");
        add(jLabel2);
        jLabel2.setBounds(450, 16, 80, 20);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jButton3.setText("Rejections");
        add(jButton3);
        jButton3.setBounds(760, 10, 90, 23);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jButton1.setText("Select Shift");
        add(jButton1);
        jButton1.setBounds(990, 10, 120, 23);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jButton2.setText("AddTime Loss Details");
        add(jButton2);
        jButton2.setBounds(1120, 10, 140, 23);

        UOMOUT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(UOMOUT);
        UOMOUT.setBounds(630, 40, 50, 20);

        UOMOUT1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(UOMOUT1);
        UOMOUT1.setBounds(690, 40, 50, 20);

        UOMIN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(UOMIN);
        UOMIN.setBounds(570, 40, 50, 20);
    }// </editor-fold>//GEN-END:initComponents

    ActionListener shift = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {

            dialog = new ShowShiftSelection ( HomeScreen.home );
            dialog.setVisible ( true );

        }
    };

    ActionListener timeloss = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
            dialog2 = new ShowTimeLossForm ( HomeScreen.home );
            dialog2.setVisible ( true );
        }
    };

    ActionListener rejections = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
            dialog3 = new ShowRejectionsForm ( HomeScreen.home );
            dialog3.setVisible ( true );
        }
    };

    private void dateChooserCombo1OnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_dateChooserCombo1OnCommit
        // TODO add your handling code here:
    }//GEN-LAST:event_dateChooserCombo1OnCommit

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar ();
        if (  ! ( Character.isDigit ( enter ) ) ) {
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

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar ();
        if (  ! ( Character.isDigit ( enter ) ) ) {
            evt.consume ();
        }
    }//GEN-LAST:event_jTextField3KeyTyped

    private void jTextField6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyTyped
        // TODO add your handling code here:   
    }//GEN-LAST:event_jTextField6KeyTyped

    private void jTextField6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField6FocusLost
        // TODO add your handling code here:        
    }//GEN-LAST:event_jTextField6FocusLost

    FocusListener f = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            //   jcb.setText("");
            jcb.selectAll ();
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            try {
                int num = Integer.parseInt ( String.valueOf ( jcb.getText ().toString () ) );
                if ( num < 1 || num > 59 ) {
                    jcb.setText ( "0" );
                    //evt.consume ();
                }
            } catch ( NumberFormatException ex1 ) {
                jcb.setText ( "0" );
                StaticValues.writer.writeExcel ( SingleProcessDE_FormProcessWise.class.getSimpleName () , SingleProcessDE_FormProcessWise.class.getSimpleName () , ex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }
    };

    FocusListener f2 = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            //jcb.setText("");
            jcb.selectAll ();
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To Search Google or type URL
//change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            String x = jcb.getText ().trim ();

            if ( x.equalsIgnoreCase ( "" ) ) {
                x = "0";
            }

            try {
                int num = Integer.parseInt ( String.valueOf ( jcb.getText ().toString () ) );
                if ( num < 0 ) {
                    jcb.setText ( x );

                    //evt.consume ();
                }
            } catch ( NumberFormatException ex1 ) {
                jcb.setText ( x );
                StaticValues.writer.writeExcel ( SingleProcessDE_FormProcessWise.class.getSimpleName () , SingleProcessDE_FormProcessWise.class.getSimpleName () , ex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }
    };

    FocusListener f3 = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            //   jcb.setText("");
            jcb.selectAll ();
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To Search Google or type URL
//change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            String x = jcb.getText ().trim ();

            if ( x.equalsIgnoreCase ( "" ) ) {
                x = "0";
            }

            try {
                int num = Integer.parseInt ( String.valueOf ( jcb.getText ().toString () ) );
                if ( num < 0 || num == 0 ) {
                    jcb.setText ( x );
                    jComboBox3.setEnabled ( false );
                } else {
                    jComboBox3.setEnabled ( true );
                }
            } catch ( NumberFormatException ex1 ) {
                jcb.setText ( x );
                StaticValues.writer.writeExcel ( SingleProcessDE_FormProcessWise.class.getSimpleName () , SingleProcessDE_FormProcessWise.class.getSimpleName () , ex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

        }
    };

    KeyListener k = new KeyListener () {
        @Override
        public void keyTyped ( KeyEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            char enter = e.getKeyChar ();
            if (  ! ( Character.isDigit ( enter ) ) ) {
                e.consume ();
            }
        }

        @Override
        public void keyPressed ( KeyEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyReleased ( KeyEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }
    };

//    String FromDate;
//    String FromTime;
//    String ToDate;
//    String ToTime;
//    String  Processname;
//    String ProcessID;
//    String QIn;
//    String Qout;
//    String Rejected;
//    ArrayList<EmployeeDR> Employee;
//    ArrayList<MachineDR> Machine;
    public String getRDate () {
        return sdf2.format ( dateChooserCombo3.getSelectedDate ().getTime () ) + " 00:00:00";
    }

    public String getRTDate () {
        return sdf2.format ( dateChooserCombo1.getSelectedDate ().getTime () ) + " 00:00:00";
    }

    public String getSFromTime () {
        try {
            return new SimpleDateFormat ( "HH:mm" ).format ( ( Date ) new SimpleDateFormat ( "E MMM dd HH:mm:ss Z yyyy" ).parse ( fromTimeSpinner.getValue ().toString () ) );
        } catch ( ParseException e ) {
            return "";
        }
        //return fromTime();
    }

    public String getSToTime () {
        try {
            return new SimpleDateFormat ( "HH:mm" ).format ( ( Date ) new SimpleDateFormat ( "E MMM dd HH:mm:ss Z yyyy" ).parse ( toTimeSpinner.getValue ().toString () ) );
        } catch ( ParseException e ) {
            return "";
        }
        // return toTime();
    }

    public String getFromDate () {
        return dateChooserCombo3.getText ();
    }

    public String getFromTime () {
        //return fromHr + ":" + fromMin;
        try {
            return new SimpleDateFormat ( "HH:mm" ).format ( ( Date ) new SimpleDateFormat ( "E MMM dd HH:mm:ss Z yyyy" ).parse ( fromTimeSpinner.getValue ().toString () ) );
        } catch ( ParseException e ) {
            return "";
        }
    }

    public String getToDate () {
        return dateChooserCombo1.getText ();
    }

    public String getToTime () {
        //return toHr + ":" + toMin;
        try {
            return new SimpleDateFormat ( "HH:mm" ).format ( ( Date ) new SimpleDateFormat ( "E MMM dd HH:mm:ss Z yyyy" ).parse ( toTimeSpinner.getValue ().toString () ) );
        } catch ( ParseException e ) {
            return "";
        }
    }

    public String getMachinename () {
        return jLabel2.getText ();
    }

    public String getMachineID () {
        return jLabel1.getText ();
    }

    public String getQIn () {
        return jTextField1.getText ();
    }

    public String getQout () {
        return jTextField2.getText ();
    }

    public String getRejected () {
        return jTextField3.getText ();
    }

    public int getEmployee () {
        //return Employee;
        return jComboBox2.getSelectedIndex ();
    }
    
    public int getUOMIn () {
        //return Employee;
        return UOMIN.getSelectedIndex ();
    }
    public int getUOMOut () {
        //return Employee;
        return UOMOUT.getSelectedIndex ();
    }
    public int getUOMOutRej () {
        //return Employee;
        return UOMOUT1.getSelectedIndex ();
    }
//    public int getMachine() {
//        //return Machine;
//        return jComboBox1.getSelectedIndex () ;
//    }
    public int getRejectionReason () {
        //return Machine;
        return jComboBox3.getSelectedIndex ();
    }

    public String getShift () {
        //selectedShiftId = dialog.getSelectedShift ();
        return selectedShiftId + "";
    }

    public ArrayList<TimeLossModel> getTimeLossReasons () {

        return selectedTimeLoss;
    }

    public ArrayList<RejectionModel> getRejectionReasons () {

        return selectedrejections;
    }

    public void setFromDate ( String fromDate ) {
        data.FromDate = fromDate;
    }

    public void setFromTime ( String fromTime ) {
        data.FromTime = fromTime;
    }

    public void setToDate ( String toDate ) {
        data.ToDate = toDate;
    }

    public void setToTime ( String toTime ) {
        data.ToTime = toTime;
    }

    public void setMachinename ( String processname ) {
        data.Machinename = processname;
        jLabel2.setText ( processname );
    }

    public void setMachineID ( String processID ) {
        data.MachineID = processID;
        jLabel1.setText ( processID );
    }

    public void setQIn ( String QIn ) {
        data.QIn = QIn;
        // jTextField1.setText ( QIn );
    }

    public void setQout ( String qout ) {
        data.Qout = qout;
        //  jTextField2.setText (qout );
    }

    public void setRejected ( String rejected ) {
        data.Rejected = rejected;
        // jTextField3.setText ( rejected );
    }

    public void setEmployee ( ArrayList<EmployeeDR> employee ) {
        data.Employee = employee;
        jComboBox2.removeAllItems ();
        for ( int i = 0 ; i < employee.size () ; i ++ ) {
            jComboBox2.addItem ( employee.get ( i ).EMP_NAME );
        }
    }

    public void setUnit(ArrayList<UOMDR> oum) {
        data.UOM = oum;
//        UOMIN.removeAllItems();
//        U   UOMIN.addItem(oum.get(i).UOM_NAME);
//            UOMOUT.addItem(oum.get(i).UOM_NAME);
//            UOMOUT1.addItem(oum.get(i).UOM_NAME);
//        }OMOUT.removeAllItems();
//        UOMOUT1.removeAllItems();
//        
//        for (int i = 0; i < oum.size(); i++) {
//            UOMIN.addItem(oum.get(i).UOM_NAME);
//            UOMOUT.addItem(oum.get(i).UOM_NAME);
//            UOMOUT1.addItem(oum.get(i).UOM_NAME);
//        }
    }
    public void setUnits(List<String> c)
    {
        UOMIN.removeAllItems();
        UOMOUT.removeAllItems();
        UOMOUT1.removeAllItems();
        for(String unit:c)
        {
            UOMIN.addItem(c.get(0));
            UOMOUT.addItem(c.get(0));
            UOMOUT1.addItem(c.get(0));
        }
//        String process=jLabel2.getText().replace(" ", "");
//        if(process.equals("Blanking"))
//        {
//            UOMIN.removeAllItems();
//            for(int i=0;i<b.size();i++)
//            {
//                 uname=b.get(0);
//            }            
//            UOMIN.addItem();
//        }
        
    }
    public void setUni(String uname)
    {
        UOMIN.removeAllItems();
        UOMIN.addItem(uname);
        
    }
    public void setShiftList ( ArrayList<ShiftDR> shifts ) {
        data.shiftData = shifts;
    }

    public void setShift ( String shift ) {
        //data.SelShift = Integer.parseInt ( shift );
        data.setShiftData ( Integer.parseInt ( shift ) );
        selectedShiftId = Integer.parseInt ( shift );
        // jButton1.setText ( shift );
    }

    public void setTimeLoss ( ArrayList<TimeLossModel> timeLossList ) {
        data.selectedtimeLossData = timeLossList;
        selectedTimeLoss = timeLossList;
    }

    public void setrejections ( ArrayList<RejectionModel> rejectionList ) {
        data.selectedtimeRejections = rejectionList;
        selectedrejections = rejectionList;
    }

//    public void setMachine(ArrayList<MachineDR> machine) {
//        data.Machine = machine;
//        jComboBox1.removeAll ();
//        for( int i=0; i<machine.size (); i++ ){
//            jComboBox1.addItem ( machine.get ( i ).MC_NAME );
//        }
//    }
    public void setRejectionReasons ( ArrayList<Rejection_Reasons> rejectionReasons ) {
        data.RejRsn = rejectionReasons;
        jComboBox3.removeAllItems ();
        for ( int i = 0 ; i < rejectionReasons.size () ; i ++ ) {
            jComboBox3.addItem ( rejectionReasons.get ( i ).REJ_DESC );
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> UOMIN;
    private javax.swing.JComboBox<String> UOMOUT;
    private javax.swing.JComboBox<String> UOMOUT1;
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private datechooser.beans.DateChooserCombo dateChooserCombo3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables

    Calendar c1 = null, c2 = null;
    SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-MM-dd" );
    SimpleDateFormat sdf1 = new SimpleDateFormat ( "HH:mm:ss" );

    Calendar c3 = Calendar.getInstance ();
    Calendar c4 = Calendar.getInstance ();

    int fromHr, fromMin, toHr, toMin;

    public String fromTime () {

//        if ( jSpinner2.getValue ().equals ( "PM" ) ) {
//            fromHr = Integer.parseInt ( jTextField6.getText () ) + 12;
//            //    c3.set ( Calendar.AM_PM, Calendar.PM);
//        } else {
//            fromHr = Integer.parseInt ( jTextField6.getText () );
//            //   c3.set ( Calendar.AM_PM, Calendar.AM);
//        }
//        fromMin = Integer.parseInt ( jTextField7.getText () );
//
//        c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField6.getText () ) );
//        c3.set ( Calendar.MINUTE , fromMin );
//        if ( jSpinner2.getValue ().equals ( "PM" ) ) {
//            c3.set ( Calendar.AM_PM , Calendar.PM );
//        } else {
//            c3.set ( Calendar.AM_PM , Calendar.AM );
//        }
//
//        return sdf1.format ( c3.getTime () );

           try {
            return new SimpleDateFormat ( "HH:mm" ).format ( ( Date ) new SimpleDateFormat ( "E MMM dd HH:mm:ss Z yyyy" ).parse ( fromTimeSpinner.getValue ().toString () ) );
        } catch ( ParseException e ) {
            return "";
        }

    }

    public String toTime () {

//        if ( jSpinner1.getValue ().equals ( "PM" ) ) {
//            toHr = Integer.parseInt ( jTextField4.getText () ) + 12;
//            //    c4.set ( Calendar.AM_PM, Calendar.PM);
//        } else {
//            toHr = Integer.parseInt ( jTextField4.getText () );
//            //    c4.set ( Calendar.AM_PM, Calendar.AM);
//        }
//        toMin = Integer.parseInt ( jTextField5.getText () );
//
//        c4.set ( Calendar.HOUR , Integer.parseInt ( jTextField4.getText () ) );
//        c4.set ( Calendar.MINUTE , toMin );
//        if ( jSpinner1.getValue ().equals ( "PM" ) ) {
//            c4.set ( Calendar.AM_PM , Calendar.PM );
//        } else {
//            c4.set ( Calendar.AM_PM , Calendar.AM );
//        }
//
//        return sdf1.format ( c4.getTime () );

        try {
            return new SimpleDateFormat ( "HH:mm" ).format ( ( Date ) new SimpleDateFormat ( "E MMM dd HH:mm:ss Z yyyy" ).parse ( toTimeSpinner.getValue ().toString () ) );
        } catch ( ParseException e ) {
            return "";
        }

    }

    public String formatFromDate () {

//        c1 = Calendar.getInstance ();
//    
//        String[] dateIP = dateChooserCombo3.getText ().split ( " " );
//
//        String fromDate = null;
//
//        if ( dateIP[ 1 ].length () == 3 ) {
//            fromDate = dateIP[ 1 ].substring ( 0 , 2 ) + "-";
//            c1.set ( Calendar.DATE , Integer.parseInt ( dateIP[ 1 ].substring ( 0 , 2 ) ) );
//        } else if ( dateIP[ 1 ].length () == 2 ) {
//            fromDate = "0" + dateIP[ 1 ].substring ( 0 , 1 ) + "-";
//            c1.set ( Calendar.DATE , Integer.parseInt ( dateIP[ 1 ].substring ( 0 , 1 ) ) );
//        }
//
//        String mon = dateIP[ 0 ];
//
//        switch ( mon ) {
//
//            case "Jan":
//                fromDate = fromDate + "01-";
//                c1.set ( Calendar.MONTH , 0 );
//                break;
//            case "Feb":
//                fromDate = fromDate + "02-";
//                c1.set ( Calendar.MONTH , 1 );
//                break;
//            case "Mar":
//                fromDate = fromDate + "03-";
//                c1.set ( Calendar.MONTH , 2 );
//                break;
//            case "Apr":
//                fromDate = fromDate + "04-";
//                c1.set ( Calendar.MONTH , 3 );
//                break;
//            case "May":
//                fromDate = fromDate + "05-";
//                c1.set ( Calendar.MONTH , 4 );
//                break;
//            case "Jun":
//                fromDate = fromDate + "06-";
//                c1.set ( Calendar.MONTH , 5 );
//                break;
//            case "Jul":
//                fromDate = fromDate + "07-";
//                c1.set ( Calendar.MONTH , 6 );
//                break;
//            case "Aug":
//                fromDate = fromDate + "08-";
//                c1.set ( Calendar.MONTH , 7 );
//                break;
//            case "Sep":
//                fromDate = fromDate + "09-";
//                c1.set ( Calendar.MONTH , 8 );
//                break;
//            case "Oct":
//                fromDate = fromDate + "10-";
//                c1.set ( Calendar.MONTH , 9 );
//                break;
//            case "Nov":
//                fromDate = fromDate + "11-";
//                c1.set ( Calendar.MONTH , 10 );
//                break;
//            case "Dec":
//                fromDate = fromDate + "12-";
//                c1.set ( Calendar.MONTH , 11 );
//                break;
//
//        }
//
//        // fromDate = fromDate + dateIP[2];
//        c1.set ( Calendar.YEAR , Integer.parseInt ( dateIP[ 2 ] ) );
//        fromDate = sdf2.format ( c1.getTime () ) + " 00:00:00";
//
//        //   c1.setFirstDayOfWeek ( Calendar.SUNDAY);
//        //   System.out.println( "This is #"+  c1.get ( Calendar.WEEK_OF_MONTH) +" week in the month");
//        //  System.out.println( "This is #"+  (c1.get ( Calendar.MONTH) +1)+"  month in the year");
//        SimpleDateFormat sdf = new SimpleDateFormat ( "EEEE" );
//        SimpleDateFormat sdf1 = new SimpleDateFormat ( "MMM" );
//        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy" );
//        SimpleDateFormat sdf3 = new SimpleDateFormat ( "dd" );
        try {
            return new SimpleDateFormat ( "HH:mm" ).format ( ( Date ) new SimpleDateFormat ( "E MMM dd HH:mm:ss Z yyyy" ).parse ( fromTimeSpinner.getValue ().toString () ) );
        } catch ( ParseException e ) {
            return "";
        }

        //return fromDate;
    }

    public String formatToDate () {

//        c2 = Calendar.getInstance () ;
//         
//        String[] dateIP2 =   dateChooserCombo1.getText ().split ( " " );
//        String toDate = null;
//
//        if ( dateIP2[ 1 ].length () == 3 ) {
//            toDate = dateIP2[ 1 ].substring ( 0 , 2 ) + "-";
//            c2.set ( Calendar.DATE , Integer.parseInt ( dateIP2[ 1 ].substring ( 0 , 2 ) ) );
//        } else if ( dateIP2[ 1 ].length () == 2 ) {
//            toDate = "0" + dateIP2[ 1 ].substring ( 0 , 1 ) + "-";
//            c2.set ( Calendar.DATE , Integer.parseInt ( dateIP2[ 1 ].substring ( 0 , 1 ) ) );
//        }
//        String mon2 = dateIP2[ 0 ];
//
//        switch ( mon2 ) {
//
//            case "Jan":
//                toDate = toDate + "01-";
//                c2.set ( Calendar.MONTH , 0 );
//                break;
//            case "Feb":
//                toDate = toDate + "02-";
//                c2.set ( Calendar.MONTH , 1 );
//                break;
//            case "Mar":
//                toDate = toDate + "03-";
//                c2.set ( Calendar.MONTH , 2 );
//                break;
//            case "Apr":
//                toDate = toDate + "04-";
//                c2.set ( Calendar.MONTH , 3 );
//                break;
//            case "May":
//                toDate = toDate + "05-";
//                c2.set ( Calendar.MONTH , 4 );
//                break;
//            case "Jun":
//                toDate = toDate + "06-";
//                c2.set ( Calendar.MONTH , 5 );
//                break;
//            case "Jul":
//                toDate = toDate + "07-";
//                c2.set ( Calendar.MONTH , 6 );
//                break;
//            case "Aug":
//                toDate = toDate + "08-";
//                c2.set ( Calendar.MONTH , 7 );
//                break;
//            case "Sep":
//                toDate = toDate + "09-";
//                c2.set ( Calendar.MONTH , 8 );
//                break;
//            case "Oct":
//                toDate = toDate + "10-";
//                c2.set ( Calendar.MONTH , 9 );
//                break;
//            case "Nov":
//                toDate = toDate + "11-";
//                c2.set ( Calendar.MONTH , 10 );
//                break;
//            case "Dec":
//                toDate = toDate + "12-";
//                c2.set ( Calendar.MONTH , 11 );
//                break;
//
//        }
//
//        //toDate = toDate + dateIP2[2];
//        c2.set ( Calendar.YEAR , Integer.parseInt ( dateIP2[ 2 ] ) );
//        toDate = sdf2.format ( c2.getTime () ) + " 00:00:00";
//
//        c2.setFirstDayOfWeek ( Calendar.SUNDAY );
        //   System.out.println( "This is #"+  c2.get ( Calendar.WEEK_OF_MONTH) +" week in the month");
        //  System.out.println( "This is #"+  (c2.get ( Calendar.MONTH) +1)+" month in the year");
        //  return toDate;
        // return sdf2.format ( dateChooserCombo1.getSelectedDate ().getTime () ) + " 00:00:00";
        try {
            return new SimpleDateFormat ( "HH:mm" ).format ( ( Date ) new SimpleDateFormat ( "E MMM dd HH:mm:ss Z yyyy" ).parse ( toTimeSpinner.getValue ().toString () ) );
        } catch ( ParseException e ) {
            return "";
        }

    }

      class ShowShiftSelection extends JDialog {

        ShiftsList confirm;

        public ShowShiftSelection ( Frame parent ) {
            super ( parent , "Login" , true );

            setUndecorated ( true );
            getRootPane ().setWindowDecorationStyle ( JRootPane.NONE );
            confirm = new ShiftsList ();
            confirm.setBounds ( 10 , 10 , 200 , 225 );

            getContentPane ().add ( confirm , BorderLayout.CENTER );
            setBackground ( Color.yellow );
            pack ();
            setResizable ( false );
            setBounds ( 0 , 0 , 220 , 255 );

            setLocationRelativeTo ( parent );

            //  this.getRootPane().setDefaultButton(btnLogin);
        }

        public int getSelectedShift () {
            return confirm.getSelectedShiftId ();
        }

        public void closeBOMWindow () {
            dispose ();
        }
    }

    class ShiftsList extends javax.swing.JPanel {

        ResultSet result = null;

        ArrayList<ShiftDR> shifts = new ArrayList<ShiftDR> ();
        DefaultListModel<String> list = null;

        
        String selectedShiftStr = "";

        /**
         * Creates new form ProdDataConformationScreen
         */
        public ShiftsList () {
            initComponents ();

            jList1.setVisibleRowCount ( 4 );
            
            list = new DefaultListModel<> ();

             addEmpAPICall = "shifts";
            result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
            
            try{
                if( ! result2.contains(  "not found" ) ){
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
                        shifts = new ArrayList<ShiftDR> ();
                        for ( int i = 0 ; i < records.length () ; i ++ ) {

                            emp = records.getJSONObject ( i);
                            ShiftDR sdr1 = new ShiftDR ();
                            sdr1.SHIFT_ID = Integer.parseInt(emp.get ( "shiftid" ).toString ()) ;
                            sdr1.SHIFT_NAME = emp.get ( "shifttitle" ).toString () ;
                            list.addElement ( emp.get ( "shifttitle" ).toString ());
                             shifts.add(  sdr1 ); 
                        }
                
                    jList1.setModel ( list );
                    
                }
                
                 }catch(ArrayIndexOutOfBoundsException e){
                        System.out.println ( ""+e.getMessage () );
                }
                
          //  jList1.setBounds(10 , 10 , 200, 200);
        }
            
 
        public DefaultTableModel buildTableModel ( ResultSet rs )
                throws SQLException {

            ResultSetMetaData metaData = rs.getMetaData ();

            // names of columns
            Vector<String> columnNames = new Vector<String> ();
            int columnCount = metaData.getColumnCount ();
            for ( int column = 1 ; column <= columnCount ; column ++ ) {
                columnNames.add ( metaData.getColumnName ( column ) );
            }

            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
            while ( rs.next () ) {

                Vector<Object> vector = new Vector<Object> ();
                for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {
                    vector.add ( rs.getObject ( columnIndex ) );
                }
                data.add ( vector );
            }

            return new DefaultTableModel ( data , columnNames );

        }

        
        @SuppressWarnings( "unchecked" )
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents () {

            jButton1 = new javax.swing.JButton ();
            jButton2 = new javax.swing.JButton ();
            jLabel1 = new javax.swing.JLabel ();
            jScrollPane2 = new javax.swing.JScrollPane ();
            jList1 = new javax.swing.JList<> ();

            
            setLayout ( null );

            jButton1.setText ( "Select" );
            jButton1.setOpaque ( false );
            jButton1.addMouseListener ( new java.awt.event.MouseAdapter () {
                public void mouseClicked ( java.awt.event.MouseEvent evt ) {
                    jButton1MouseClicked ( evt );
                }
            } );
            add ( jButton1 );
            jButton1.setBounds ( 10 , 220 , 90 , 30 );

            jButton2.setText ( "Cancel" );
            jButton2.setOpaque ( false );
            jButton2.addMouseListener ( new java.awt.event.MouseAdapter () {
                public void mouseClicked ( java.awt.event.MouseEvent evt ) {
                    jButton2MouseClicked ( evt );
                }
            } );
            add ( jButton2 );
            jButton2.setBounds ( 100 , 220 , 100 , 30 );

            jLabel1.setHorizontalAlignment ( javax.swing.SwingConstants.CENTER );
            jLabel1.setText ( "Select Shift" );
            jLabel1.setForeground ( Color.WHITE );
            add ( jLabel1 );
            jLabel1.setBounds ( 0 , 0 , 200 , 30 );

            jList1.setModel ( new javax.swing.AbstractListModel<String> () {
                String[] strings = { "Item 1" , "Item 2" , "Item 3" , "Item 4" , "Item 5" };

                public int getSize () {
                    return strings.length;
                }

                public String getElementAt ( int i ) {
                    return strings[ i ];
                }
            } );
            jList1.setVisibleRowCount ( 4);
            jList1.setFixedCellHeight ( 40);
            jList1.setSelectionMode ( javax.swing.ListSelectionModel.SINGLE_SELECTION );
            jScrollPane2.setViewportView ( jList1 );

            add ( jScrollPane2 );
            jScrollPane2.setBounds ( 15, 50, 180, 160 );
            setBackground ( new java.awt.Color ( 60,63,65 ) );
            setPreferredSize ( new java.awt.Dimension ( 200 , 300 ) );
        }// </editor-fold>                        

        private void jButton1MouseClicked ( java.awt.event.MouseEvent evt ) {
           
            selectedShiftId  = shifts.get(  jList1.getSelectedIndex () ).SHIFT_ID;
            selectedShiftStr = shifts.get( jList1.getSelectedIndex () ).SHIFT_NAME;
            setShift ( selectedShiftId + "" );
            
            dialog.dispose ();
        }

        private void jButton2MouseClicked ( java.awt.event.MouseEvent evt ) {
            // TODO add your handling code here:

            dialog.dispose ();
        }

        public int getSelectedShiftId () {
            return selectedShiftId;
        }

        public String getSelectedShiftStr () {
            return selectedShiftStr;
        }

        // Variables declaration - do not modify                     
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JList<String> jList1;
        private javax.swing.JScrollPane jScrollPane2;
        // End of variables declaration                   

    }

    class ShowTimeLossForm extends JDialog {

        private TimeLossList timeLoss;

        public ShowTimeLossForm ( Frame parent ) {
            super ( parent , "Login" , true );

            setUndecorated ( true );
            getRootPane ().setWindowDecorationStyle ( JRootPane.NONE );
            timeLoss = new TimeLossList ();
            timeLoss.setBounds ( 0 , 0 , 400 , 380 );
            setBackground ( Color.yellow );
            getContentPane ().add ( timeLoss , BorderLayout.CENTER );

            pack ();
            setResizable ( false );
            setBounds ( 0 , 0 , 400 , 380 );

            setLocationRelativeTo ( parent );

            //  this.getRootPane().setDefaultButton(btnLogin);
        }

        public ArrayList<TimeLossModel> getTImeLossList () {

            return timeLoss.getTimeLossList ();
        }

        public void closeBOMWindow () {
            dispose ();
        }
    }

    class TimeLossList extends javax.swing.JPanel {

        ResultSet result = null;

        public ArrayList<TimeLossDetailPanel> timeLossList = null;
        private ArrayList<TimeLossModel> timeLossDetailList = new ArrayList<TimeLossModel> ();

        /**
         * Creates new form ProdDataConformationScreen
         */
        public TimeLossList () {
            initComponents ();

            TimeLossReasonList panel = new TimeLossReasonList ();
            panel.setBounds ( 100 , 50 , 260 , 260 );
            panel.setBackground ( Color.white );
            add ( panel );

        }

        public DefaultTableModel buildTableModel ( ResultSet rs )
                throws SQLException {

            ResultSetMetaData metaData = rs.getMetaData ();

            // names of columns
            Vector<String> columnNames = new Vector<String> ();
            int columnCount = metaData.getColumnCount ();
            for ( int column = 1 ; column <= columnCount ; column ++ ) {
                columnNames.add ( metaData.getColumnName ( column ) );
            }

            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
            while ( rs.next () ) {

                Vector<Object> vector = new Vector<Object> ();
                for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {
                    vector.add ( rs.getObject ( columnIndex ) );
                }
                data.add ( vector );
            }

            return new DefaultTableModel ( data , columnNames );

        }

        /**
         * This method is called from within the constructor to initialize the
         * form. WARNING: Do NOT modify this code. The content of this method is
         * always regenerated by the Form Editor.
         */
        @SuppressWarnings( "unchecked" )
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents () {

            jButton1 = new javax.swing.JButton ();
            jButton2 = new javax.swing.JButton ();
            jLabel1 = new javax.swing.JLabel ();

             setBackground ( new java.awt.Color ( 255 , 255 , 255 ) );
            setBorder ( javax.swing.BorderFactory.createTitledBorder ( "" ) );
            setMinimumSize ( new java.awt.Dimension ( 1150 , 500 ) );
            setPreferredSize ( new java.awt.Dimension ( 1100 , 500 ) );
            setLayout ( null );

            jButton1.setText ( "Submit" );
            jButton1.setOpaque ( false );
            jButton1.addMouseListener ( new java.awt.event.MouseAdapter () {
                public void mouseClicked ( java.awt.event.MouseEvent evt ) {
                    jButton1MouseClicked ( evt );
                }
            } );
            add ( jButton1 );
            jButton1.setBounds ( 100 , 332 , 100 , 40 );

            jButton2.setText ( "Cancel" );
            jButton2.setOpaque ( false );
            jButton2.addMouseListener ( new java.awt.event.MouseAdapter () {
                public void mouseClicked ( java.awt.event.MouseEvent evt ) {
                    jButton2MouseClicked ( evt );
                }
            } );
            add ( jButton2 );
            jButton2.setBounds ( 200 , 332 , 100 , 40 );

            jLabel1.setHorizontalAlignment ( javax.swing.SwingConstants.CENTER );
            jLabel1.setText ( "Add time in minutes for applicable timeloss reasons" );
            add ( jLabel1 );
            jLabel1.setBounds ( 0 , 0 , 410 , 30 );

        }// </editor-fold>                        

        private void jButton1MouseClicked ( java.awt.event.MouseEvent evt ) {
            // TODO add your handling code here:
           
            TimeLossModel tlm = null;

            int totalTimeLoss = 0;

            for ( int i = 0 ; i < timeLossList.size () ; i ++ ) {

                tlm = new TimeLossModel ();
                if ( timeLossList.get ( i ).getMinutes () > 0 ) {
                    tlm.setId ( timeLossList.get ( i ).getTRLid () );
                    tlm.setReason ( timeLossList.get ( i ).getReasonStr () );
                    tlm.setTimelossForReason ( timeLossList.get ( i ).getMinutes () );
                    tlm.setMinutes ( timeLossList.get ( i ).getMinutes () );
                    timeLossDetailList.add ( tlm );
                }
            }

            TimeLossModel tlm2 = null;
            StringBuilder sb = new StringBuilder ();
            for ( int i = 0 ; i < timeLossDetailList.size () ; i ++ ) {
                tlm2 = timeLossDetailList.get ( i );

                sb.append ( tlm2.getId () );
                sb.append ( tlm2.getReason () );
                sb.append ( tlm2.getMinutes () );
                sb.append ( "\n" );
            }

            selectedTimeLoss = timeLossDetailList;
            dialog2.dispose ();
            
        }

        private void jButton2MouseClicked ( java.awt.event.MouseEvent evt ) {
            // TODO add your handling code here:

            dialog2.dispose ();
        }
        
        
        
        public ArrayList<TimeLossModel> getTimeLoss () {
            // TODO add your handling code here:
            TimeLossModel tlm = null;

            int totalTimeLoss = 0;

            for ( int i = 0 ; i < timeLossList.size () ; i ++ ) {

                tlm = new TimeLossModel ();
                if ( timeLossList.get ( i ).getMinutes () > 0 ) {
                    tlm.setId ( timeLossList.get ( i ).getTRLid () );
                    tlm.setReason ( timeLossList.get ( i ).getReasonStr () );
                    tlm.setTimelossForReason ( timeLossList.get ( i ).getMinutes () );
                    tlm.setMinutes ( timeLossList.get ( i ).getMinutes () );
                    timeLossDetailList.add ( tlm );
                }
            }

            TimeLossModel tlm2 = null;
            StringBuilder sb = new StringBuilder ();
            for ( int i = 0 ; i < timeLossDetailList.size () ; i ++ ) {
                tlm2 = timeLossDetailList.get ( i );

                sb.append ( tlm2.getId () );
                sb.append ( tlm2.getReason () );
                sb.append ( tlm2.getMinutes () );
                sb.append ( "\n" );
            }

            selectedTimeLoss = timeLossDetailList;
            // setTimeLoss ( selectedTimeLoss );

            return selectedTimeLoss;
        }

        public ArrayList<TimeLossModel> getTimeLossList () {
            return timeLossDetailList;
        }

        // Variables declaration - do not modify                     
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        // End of variables declaration                   

        class TimeLossReasonList extends JPanel {

            private JPanel panel;

            private JScrollPane scroll;
            private JButton btnAddType;


            public TimeLossReasonList () {
                  

                setLayout ( new BorderLayout () );

                panel = new JPanel ();
                panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
                scroll = new JScrollPane ( panel ,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );

                add ( scroll , BorderLayout.CENTER );

                setVisible ( true );

                TimeLossDetailPanel pane = null;

                timeLossList = new ArrayList<TimeLossDetailPanel> ();

                addEmpAPICall = "timeloss_reason";
                result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

                if (  ! result2.contains ( "not found" ) && result2.contains ( "success" ) ) {
                    HashMap<String , Object> map = new HashMap<String , Object> ();
                    JSONObject jObject = new JSONObject ( result2 );
                    Iterator<?> keys = jObject.keys ();

                    while ( keys.hasNext () ) {
                        String key = ( String ) keys.next ();
                        Object value = jObject.get ( key );
                        map.put ( key , value );
                    }

                    JSONObject st = ( JSONObject ) map.get ( "data" );
                    JSONArray records = st.getJSONArray ( "records" );

                    JSONObject emp = null;
                    timeloss_reasons = new ArrayList<TimeLoss_Reasons> ();
                    panel.setBounds ( 0 , 0 , 250 , 36 * records.length () );
                    for ( int i = 0 ; i < records.length () ; i ++ ) {

                        emp = records.getJSONObject ( i );

                        timeloss_reasons.add ( new TimeLoss_Reasons (Integer.parseInt ( emp.get ( "TLR_ID" ).toString () ) , emp.get ( "TLR_DESC" ).toString () ) );
                        pane = new TimeLossDetailPanel ();
                        pane.setBounds ( 0 , ( i * 40 ) , 250 , 36 );
                        pane.setTRLid ( Integer.parseInt ( emp.get ( "TLR_ID" ).toString () ) );
                        pane.setReasonStr ( emp.get ( "TLR_DESC" ).toString () );
                        pane.setMinutes ( 0 );

                        panel.add ( pane );
                        panel.revalidate ();
                        timeLossList.add ( pane );
                    }

                }

            }
        }

    }
    
    class ShowRejectionsForm extends JDialog {

        private RejectionsList rejection;

        public ShowRejectionsForm ( Frame parent ) {
            super ( parent , "Login" , true );

            setUndecorated ( true );
            getRootPane ().setWindowDecorationStyle ( JRootPane.NONE );
            rejection = new RejectionsList ();
            rejection.setBounds ( 0 , 0 , 400 , 380 );
            setBackground ( Color.yellow );
            getContentPane ().add ( rejection , BorderLayout.CENTER );

            pack ();
            setResizable ( false );
            setBounds ( 0 , 0 , 400 , 380 );
            
            setLocationRelativeTo ( parent );

            //  this.getRootPane().setDefaultButton(btnLogin);
        }

//        public ArrayList<TimeLossModel> getTImeLossList () {
//
//            return rejection.getRe
//        }

        public void closeBOMWindow () {
            dispose ();
        }
    }    
    
    class RejectionsList extends javax.swing.JPanel {

        ResultSet result = null;

        public ArrayList<RejectionDetailPanel> rejectionList = null;
        private ArrayList<RejectionModel> rejectionDetailList = new ArrayList<RejectionModel> ();

        
        public RejectionsList () {
            initComponents ();

            RejectionReasonList panel = new RejectionReasonList ();
            panel.setBounds ( 10 , 50 , 360 , 280 );
            panel.setBackground ( Color.white );
            add ( panel );

        }

        @SuppressWarnings( "unchecked" )
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents () {

            jButton1 = new javax.swing.JButton ();
            jButton2 = new javax.swing.JButton ();
            jLabel1 = new javax.swing.JLabel ();

            setBackground ( new java.awt.Color ( 255 , 255 , 255 ) );
            setBorder ( javax.swing.BorderFactory.createTitledBorder ( "" ) );
            setMinimumSize ( new java.awt.Dimension ( 1150 , 500 ) );
            setPreferredSize ( new java.awt.Dimension ( 1100 , 500 ) );
            setLayout ( null );

            jButton1.setText ( "Submit" );
            jButton1.setOpaque ( false );
            jButton1.addMouseListener ( new java.awt.event.MouseAdapter () {
                public void mouseClicked ( java.awt.event.MouseEvent evt ) {
                    jButton1MouseClicked ( evt );
                }
            } );
            add ( jButton1 );
            jButton1.setBounds ( 100 , 332 , 100 , 40 );

            jButton2.setText ( "Cancel" );
            jButton2.setOpaque ( false );
            jButton2.addMouseListener ( new java.awt.event.MouseAdapter () {
                public void mouseClicked ( java.awt.event.MouseEvent evt ) {
                    jButton2MouseClicked ( evt );
                }
            } );
            add ( jButton2 );
            jButton2.setBounds ( 200 , 332 , 100 , 40 );

            jLabel1.setHorizontalAlignment ( javax.swing.SwingConstants.CENTER );
            jLabel1.setText ( "" );
            add ( jLabel1 );
            jLabel1.setBounds ( 0 , 0 , 410 , 30 );
        }// </editor-fold>                        

        private void jButton1MouseClicked ( java.awt.event.MouseEvent evt ) {
            // TODO add your handling code here:
            RejectionModel tlm = null;

            int totalRejection  = 0 ;
            
            for ( int i = 0 ; i < rejectionList.size () ; i ++ ) {

                tlm = new RejectionModel ();
                if ( rejectionList.get ( i ).getRejectionForReason () > 0 ) {
                    tlm.setRejectionReasonId ( rejectionList.get ( i ).getRejectionReasonId ());
                    tlm.setRejectionReasonStr (  rejectionList.get ( i ).getRejectionReasonStr () );
                    tlm.setRejectionForReason ( rejectionList.get ( i ).getRejectionForReason () );
                    tlm.setTotalRejection ( rejectionList.get ( i ).getTotalRejection () );
                    
                    totalRejection = totalRejection + rejectionList.get ( i ).getRejectionForReason () ;
                    
                    //lm.setReason ( rejectionList.get ( i ).getReasonStr () );
                   rejectionDetailList.add ( tlm );
                }
            }
            
            
            if(  totalRejection == Integer.parseInt( getRejected () ) ){
                RejectionModel tlm2 = null;
                StringBuilder sb = new StringBuilder ();
                for ( int i = 0 ; i < rejectionDetailList.size () ; i ++ ) {
                    tlm2 = rejectionDetailList.get ( i );

                    sb.append ( tlm2.getRejectionForReason ());
                    sb.append ( tlm2.getRejectionReasonId () );
                    sb.append ( tlm2.getRejectionReasonStr () );
                    sb.append ( "\n" );
                }

                selectedrejections = rejectionDetailList;
                setrejections (rejectionDetailList );

                //   JOptionPane.showMessageDialog( null, sb.toString () );
                dialog3.dispose ();
            }else{
                
                JOptionPane.showMessageDialog (  null, "Total rejection count cannot be greater than "+getRejected () );
            }
        }

        private void jButton2MouseClicked ( java.awt.event.MouseEvent evt ) {
            // TODO add your handling code here:

            dialog3.dispose ();
        }

      

        // Variables declaration - do not modify                     
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        // End of variables declaration                   

        class RejectionReasonList extends JPanel {

            private JPanel panel;

            private JScrollPane scroll;
            private JButton btnAddType;

//    public ArrayList<RMQtyPanel> rmBomList = null ;
            public RejectionReasonList () {
                // getContentPane().setLayout(new BorderLayout());       

                setLayout ( new BorderLayout () );

                panel = new JPanel ();
                panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
                scroll = new JScrollPane ( panel ,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );

                add ( scroll , BorderLayout.CENTER );

                setVisible ( true );

                RejectionDetailPanel pane = null;

                rejectionList = new ArrayList<RejectionDetailPanel> ();

                addEmpAPICall = "rejreasons";
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
                    rej_reasons = new ArrayList<Rejection_Reasons> ();
                    panel.setBounds(0,0,250,36*records.length ());
                    for ( int i = 0 ; i < records.length () ; i ++ ) {
                    
                        emp = records.getJSONObject ( i);
                        
                        rej_reasons.add( new Rejection_Reasons ( Integer.parseInt(emp.get ( "RR_ID" ).toString ()), emp.get ( "RR_DESC" ).toString ()   )); 
                        pane = new RejectionDetailPanel ();
                        pane.setBounds(0,(i*40),250,36);
                        pane.setRejectionReasonId ( Integer.parseInt(emp.get ( "RR_ID" ).toString ()));
                        pane.setRejectionReasonStr ( emp.get ( "RR_DESC" ).toString () );
                        pane.setRejectionForReason ( 0);
                        pane.setTotalRejection ( 0 );
                        panel.add ( pane );
                        panel.revalidate ();
                        rejectionList.add ( pane );
                    }
                
           
            }
        }
        
    }  

}
/*
 * Logic for 
 * 
 yourJTextField.getDocument().addDocumentListener(new DocumentListener() {
  public void changedUpdate(DocumentEvent e) {
    changed();
  }
  public void removeUpdate(DocumentEvent e) {
    changed();
  }
  public void insertUpdate(DocumentEvent e) {
    changed();
  }

  public void changed() {
     if (yourJTextField.getText().equals("")){
       loginButton.setEnabled(false);
     }
     else {
       loginButton.setEnabled(true);
    }

  }
});
 */
