/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import trixware.erp.prodezydesktop.Model.EmployeeDR;
import trixware.erp.prodezydesktop.Model.MachineDR;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.RawMaterialDR;
import trixware.erp.prodezydesktop.Model.ShiftDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
/**
 *
 * @author Rajesh
 */
public class SetupForm extends javax.swing.JPanel {

    ArrayList<ProductDR> products = null;
    ArrayList<EmployeeDR> employees = null;
    ArrayList<MachineDR> machines = null;
    ArrayList<ShiftDR> shifts = null;
    ArrayList<RawMaterialDR> rawMaterials = null ;
    
    ProductDR prdr = null;
    EmployeeDR empdr = null;
    MachineDR mcdr = null;
    ShiftDR shift = null ;
    RawMaterialDR rm = null ;
    
    boolean validQty = false ;
    boolean validHour = false ;
    boolean validMin = false ;
    
    
    /**
     * Creates new form SetupForm
     */
    public SetupForm () {
        initComponents ();
        
        jTextField1.addFocusListener ( hourField );
        jTextField1.addKeyListener ( k );
        jTextField2.addFocusListener ( minuteField );
        jTextField2.addKeyListener ( k );
        jTextField3.addFocusListener ( valueField );
        jTextField3.addKeyListener ( k );
        
        loadComboBoxes () ;
    }


    
    public void loadComboBoxes () {

        String queryOne = "SELECT FG_ID, PART_NAME FROM finished_goods";
        String queryTwo = "SELECT EmployeePK, EMP_NAME FROM employee";
        String queryThree = "SELECT MCH_ID, MACHINE_NO FROM machine";
        String querySix = "SELECT shiftid, shifttitle FROM shifts";
        String querySeven = "SELECT RM_ID, RM_NAME FROM raw_material";
        
        
       
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
                jComboBox3.addItem ( rs.getString ( 2 ) );
            }

            rs.close ();
        } catch ( Exception e ) {
            System.out.println ( " Error 1 " + e.getMessage () );
            StaticValues.writer.writeExcel ( SetupForm.class.getSimpleName () , SetupForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
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
                jComboBox1.addItem ( rs.getString ( 2 ) );
            }

        } catch ( Exception e ) {
            System.out.println ( " Error 2 " + e.getMessage () );
            StaticValues.writer.writeExcel ( SetupForm.class.getSimpleName () , SetupForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }

        try {
            rs = DB_Operations.executeSingle ( queryThree );
            machines = new ArrayList<MachineDR> ();

            while ( rs.next () ) {
                mcdr = new MachineDR ();

                mcdr.MC_ID = Integer.parseInt ( rs.getString ( 1 ) );
                mcdr.MC_NAME = rs.getString ( 2 );
                machines.add ( mcdr );
                jComboBox5.addItem ( rs.getString ( 2 ) );
            }

        } catch ( Exception e ) {
            System.out.println ( " Error 2 " + e.getMessage () );
            StaticValues.writer.writeExcel ( SetupForm.class.getSimpleName () , SetupForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }
        
        try {
            rs = DB_Operations.executeSingle ( querySix );
            shifts = new ArrayList<ShiftDR> ();

            while ( rs.next () ) {
                shift = new ShiftDR ();

                shift.SHIFT_ID = Integer.parseInt ( rs.getString ( 1 ) );
                shift.SHIFT_NAME = rs.getString ( 2 );
                shifts.add ( shift );
                jComboBox2.addItem ( rs.getString ( 2 ) );
            }

        } catch ( Exception e ) {
            System.out.println ( " Error 2 " + e.getMessage () );
            StaticValues.writer.writeExcel ( SetupForm.class.getSimpleName () , SetupForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }
        
        
        try {
            rs = DB_Operations.executeSingle ( querySeven );
            rawMaterials = new ArrayList<RawMaterialDR> ();

            while ( rs.next () ) {
                rm = new RawMaterialDR ();
                rm.RM_ID = Integer.parseInt ( rs.getString ( 1 ) );
                rm.RM_CLASS = rs.getString ( 2 );
                rawMaterials.add ( rm );
                jComboBox4.addItem ( rs.getString ( 2 ) );                
          //      rmCombo.addElement ( rm );
            }            
        //    jComboBox4.setModel ( (DefaultComboBoxModel) rmCombo );
        } catch ( Exception e ) {
            System.out.println ( " Error 2 " + e.getMessage () );
            StaticValues.writer.writeExcel ( SetupForm.class.getSimpleName () , SetupForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }
       
    }

    
    FocusListener minuteField = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            jcb.setText ( "" );
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
                StaticValues.writer.writeExcel ( SetupForm.class.getSimpleName () , SetupForm.class.getSimpleName () , ex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
            }
        }
    };
    
    FocusListener hourField = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            jcb.setText ( "" );
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            try {
                int num = Integer.parseInt ( String.valueOf ( jcb.getText ().toString () ) );
                if ( num < 1 || num > 12 ) {
                    jcb.setText ( "0" );
                    //evt.consume ();
                }
            } catch ( NumberFormatException ex1 ) {
                jcb.setText ( "0" );
                StaticValues.writer.writeExcel ( SetupForm.class.getSimpleName () , SetupForm.class.getSimpleName () , ex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
            }
        }
    };
    
    FocusListener valueField = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            jcb.setText ( "" );
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            try {
                int num = Integer.parseInt ( String.valueOf ( jcb.getText ().toString () ) );
                if ( num < 1 ) {
                    jcb.setText ( "0" );
                    //evt.consume ();
                }
            } catch ( NumberFormatException ex1 ) {
                jcb.setText ( "0" );
                StaticValues.writer.writeExcel ( SetupForm.class.getSimpleName () , SetupForm.class.getSimpleName () , ex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
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

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(711, 360));
        setLayout(null);

        jLabel1.setText("Employee Name");
        add(jLabel1);
        jLabel1.setBounds(70, 40, 110, 16);

        jLabel2.setText("Shift Name");
        add(jLabel2);
        jLabel2.setBounds(70, 70, 80, 16);

        jLabel3.setText("Product / Part");
        add(jLabel3);
        jLabel3.setBounds(70, 100, 100, 16);

        jLabel4.setText("Raw Material");
        add(jLabel4);
        jLabel4.setBounds(70, 130, 100, 16);

        jLabel6.setText("Date & Time");
        add(jLabel6);
        jLabel6.setBounds(410, 160, 100, 16);

        jLabel9.setText("Is mold clean ?");
        add(jLabel9);
        jLabel9.setBounds(410, 50, 100, 16);

        jLabel10.setText("Person deputed is skilled ?");
        add(jLabel10);
        jLabel10.setBounds(410, 70, 160, 20);

        jLabel11.setText("Machine Clit");
        add(jLabel11);
        jLabel11.setBounds(410, 100, 80, 16);

        jLabel12.setText("Accessory Tools Available ?");
        add(jLabel12);
        jLabel12.setBounds(410, 130, 159, 16);

        add(jComboBox1);
        jComboBox1.setBounds(200, 40, 140, 26);

        add(jComboBox2);
        jComboBox2.setBounds(200, 70, 140, 26);

        add(jComboBox3);
        jComboBox3.setBounds(200, 100, 140, 26);

        add(jComboBox4);
        jComboBox4.setBounds(200, 130, 140, 26);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(null);

        dateChooserCombo1.setCalendarPreferredSize(new java.awt.Dimension(400, 250));
        dateChooserCombo1.setFormat(2);
        jPanel1.add(dateChooserCombo1);
        dateChooserCombo1.setBounds(10, 10, 150, 30);
        jPanel1.add(jTextField1);
        jTextField1.setBounds(10, 50, 40, 30);
        jPanel1.add(jTextField2);
        jTextField2.setBounds(60, 50, 40, 30);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText(":");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(50, 50, 10, 20);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("AM");
        jPanel1.add(jRadioButton1);
        jRadioButton1.setBounds(110, 50, 50, 20);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("PM");
        jPanel1.add(jRadioButton2);
        jRadioButton2.setBounds(110, 70, 50, 20);

        add(jPanel1);
        jPanel1.setBounds(480, 190, 170, 100);

        jCheckBox1.setText("No");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        add(jCheckBox1);
        jCheckBox1.setBounds(590, 40, 50, 30);

        jCheckBox2.setText("No");
        add(jCheckBox2);
        jCheckBox2.setBounds(590, 70, 50, 24);

        jCheckBox3.setText("No");
        add(jCheckBox3);
        jCheckBox3.setBounds(590, 100, 50, 24);

        jCheckBox4.setText("No");
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });
        add(jCheckBox4);
        jCheckBox4.setBounds(590, 130, 50, 24);

        jButton1.setText("Submit");
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
        jButton1.setBounds(480, 300, 77, 32);

        jButton2.setText("Cancel");
        jButton2.setOpaque(false);
        add(jButton2);
        jButton2.setBounds(570, 300, 77, 32);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(null);

        jLabel14.setText("Status : ");
        jPanel2.add(jLabel14);
        jLabel14.setBounds(10, 10, 46, 16);

        jLabel15.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel2.add(jLabel15);
        jLabel15.setBounds(70, 10, 190, 90);

        add(jPanel2);
        jPanel2.setBounds(70, 220, 270, 110);

        jLabel16.setText("Machine");
        add(jLabel16);
        jLabel16.setBounds(70, 190, 48, 16);

        add(jComboBox5);
        jComboBox5.setBounds(200, 190, 140, 26);

        jLabel7.setText("Quantity");
        add(jLabel7);
        jLabel7.setBounds(70, 160, 80, 20);
        add(jTextField3);
        jTextField3.setBounds(200, 160, 90, 24);

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 0, 0));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("*");
        add(jLabel17);
        jLabel17.setBounds(50, 160, 20, 19);

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 0, 0));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("*");
        add(jLabel18);
        jLabel18.setBounds(460, 200, 20, 19);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
                
       
       if(  ! jTextField1.getText ().trim ().equals ( "")  && ! jTextField1.getText ().trim ().equals ( "0") 
               && ! jTextField2.getText ().trim ().equals ( "")   && ! jTextField2.getText ().trim ().equals ( "0") 
               && ! jTextField3.getText ().trim ().equals ( "")   && ! jTextField3.getText ().trim ().equals ( "0") )
       {
           
           Calendar c1 = Calendar.getInstance ();
        c1.set ( Calendar.HOUR,  Integer.parseInt( jTextField1.getText ().trim ()) );
        c1.set ( Calendar.MINUTE ,  Integer.parseInt( jTextField2.getText ().trim ()) );
        c1.set ( Calendar.AM_PM,  ((  jRadioButton1.isSelected ()) ? Calendar.AM : Calendar.PM ) );

           
       String insertSetupQuery = "insert into dailySetupMaster ( employeeId, fgId, shiftId, rawMatid, rawMatQty, isMouldClean, isPersonSkilled, ismachineClit, areToolsAvl, setupdate, setupTime, machineId ) values"
               + " ( "+ employees.get ( jComboBox1.getSelectedIndex () ).EMP_ID
        +", "+products.get( jComboBox3.getSelectedIndex () ).FG_ID 
       + ", "+shifts.get(  jComboBox2.getSelectedIndex () ).SHIFT_ID
       + ", "+rawMaterials.get ( jComboBox4.getSelectedIndex () ).RM_ID
       + ", "+Double.parseDouble (  jTextField3.getText ().trim() )
       + ", "+   (  jCheckBox1.isSelected () ? "'true'" : "'false'" ) 
       + ", "+ (  jCheckBox2.isSelected () ? "'true'" : "'false'" ) 
       + ", "+ (  jCheckBox3.isSelected () ? "'true'" : "'false'" ) 
       + ", "+ (  jCheckBox4.isSelected () ? "'true'" : "'false'" ) 
       + ", '"+ new SimpleDateFormat ("yyyy-MM-dd").format ( dateChooserCombo1.getSelectedDate ().getTime () )+" "+new SimpleDateFormat ("HH:mm").format ( c1.getTime ()) 
       + "', '"+new SimpleDateFormat ("HH:mm").format ( c1.getTime ())  
        +"',  "+  machines.get(jComboBox5.getSelectedIndex ()).MC_ID  +"  )"  ; 
        
       // System.out.println ( ""+insertSetupQuery );
        
       DB_Operations.executeInsertRandom ( insertSetupQuery );
        
       insertIntoRMStock (  rawMaterials.get ( jComboBox4.getSelectedIndex () ).RM_ID ,  Double.parseDouble (  jTextField3.getText ().trim() )) ;
       
     //   jLabel15.setText(insertSetupQuery) ;
        
       }else{
           JOptionPane.showMessageDialog ( null, "Please enter appropriate values in fields marked with * ");
       }
    }//GEN-LAST:event_jButton1ActionPerformed

    double OPENING = 0;
    double RECEIVED = 0, USED = 0;
    double CLOSING = 0;

    double OldOPENING = 0;
    double OldCLOSING = 0;

    boolean recordExist = false;
    
    public void insertIntoRMStock ( int rmId , double qty) {

        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMMyyyy_HH_mm_a") ;
        String strDate2 = sdf2.format ( c2.getTime () );

        ResultSet result = DB_Operations.executeSingle ( "SELECT RM_TYPE, (OPENING),(RECEIVED),(USED),(CLOSING) FROM raw_material A INNER JOIN RMStock B ON A.RM_ID = B.RMI_ID AND A.RM_ID =" + rmId +" group by A.RM_ID" );

        try {
            if ( result != null && result.isBeforeFirst () ) {

                recordExist = true;

                OPENING = Double.parseDouble ( result.getString ( 5 ) );
                RECEIVED = Integer.parseInt ( result.getString ( 3 ) );
                USED = Integer.parseInt ( result.getString ( 4 ) );
                OldCLOSING = Integer.parseInt ( result.getString ( 5 ) );

            } else {
                System.out.println ( "No result to show" );
                recordExist = false;
                OPENING = 0;
                RECEIVED = 0;
                USED = 0;
                CLOSING = 0;
            }

            result.close ();

            OPENING = OldCLOSING;
            
            USED =  qty ;
            RECEIVED = 0;
            CLOSING = OPENING - USED;

            DB_Operations.executeSingleNR ( "INSERT INTO RMStock ( `RMI_ID`, `RM_ITEM_ID`, `OPENING` ,`RECEIVED` ,`USED` ,`CLOSING` , `CREATED_ON`,`EDITED_ON`,`EDITED_BY`  )  values ( " + rmId + ", 0 ," + OPENING + "," + RECEIVED + "," + USED + "," + CLOSING + ",'" + strDate2 + "','" + strDate2 + "', '"+Proman_User.getUsername ()+"' )" );

        } catch ( SQLException e ) {
            System.out.println ( "Error Occured !" );
            StaticValues.writer.writeExcel ( SetupForm.class.getSimpleName () , SetupForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }

    }
    
    DefaultComboBoxModel<RawMaterialDR> rmCombo = new DefaultComboBoxModel<RawMaterialDR>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
