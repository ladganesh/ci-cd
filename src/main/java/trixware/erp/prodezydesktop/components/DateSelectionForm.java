/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.components;

import trixware.erp.prodezydesktop.Derived.DateAutoCompletion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Rajesh
 */
public class DateSelectionForm extends javax.swing.JPanel {

    boolean mandatory = false; 
    boolean showError = false ;
    String labelValue, textValue, errorValue ;
    int totalTime ;
    
    
    /**
     * Creates new form SpinnerForm
     */
    
    public DateSelectionForm () {
        initComponents ();
         jLabel3.setVisible ( false );
         
        jComboBox1.addFocusListener(  getDate  );
        jComboBox2.addFocusListener(  getDate  );
        jComboBox3.addFocusListener(  getDate  );
         
        DateAutoCompletion.enable(  jComboBox1 ) ;
        DateAutoCompletion.enable(  jComboBox2 ) ;
        DateAutoCompletion.enable(  jComboBox3 ) ;
        
        // Load all years with current year item index selected
        getYears() ;
        
        // Select current month item index
        // Select year --> Load Dates as per selected Year 

        jLabel3.addFocusListener( getDate );
        
    }
    
    public DateSelectionForm( String labelVal,  boolean isMandatory, String errorString ){
        initComponents ();
        mandatory = isMandatory ;
        jLabel2.setVisible ( mandatory );
        errorValue = errorString ;
        jLabel3.setToolTipText ( errorValue );
        jLabel1.setText( labelVal  ) ;
        jLabel3.setVisible ( false );

        jComboBox1.addFocusListener(  getDate  );
        jComboBox2.addFocusListener(  getDate  );
        jComboBox3.addFocusListener(  getDate  );
        
        
        DateAutoCompletion.enable(  jComboBox1 ) ;
        DateAutoCompletion.enable(  jComboBox2 ) ;
        DateAutoCompletion.enable(  jComboBox3 ) ;
        
        // Load all years with current year item index selected
        getYears() ;
        
        // Select current month item index
        // Select year --> Load Dates as per selected Year 
        
        jLabel3.addFocusListener( getDate );
    }

    public void getYears(){
        
        int startYear = 1900 ;
        int currentYear = Integer.parseInt( new SimpleDateFormat("yyyy").format(  Calendar.getInstance().getTime()  ) );        
        String month = new SimpleDateFormat("MMM").format(  Calendar.getInstance().getTime()  ) ;        
        int date = Integer.parseInt( new SimpleDateFormat("d").format(  Calendar.getInstance().getTime()  )  );        
        
        System.out.println ( month );
        
        for ( int i = startYear ; i <= currentYear  ; i ++ ) {
            jComboBox3.addItem ( i+""  );
        }
        jComboBox3.addItem ( (currentYear+1)+""  );
        
        jComboBox3.addActionListener(  yearSelection  ) ;
        jComboBox1.addActionListener(  monthSelection  ) ;
        
        jComboBox3.setSelectedIndex ( jComboBox3.getItemCount()-2  );
        
        for(  int i = 0; i < 11 ; i++  ){
            if( jComboBox1.getItemAt ( i ).equals(  month ) ){
                jComboBox1.setSelectedIndex ( i  );
            }
        }
        
        for(  int i = 0; i < 31 ; i++  ){
            if( jComboBox2.getItemAt ( i ) ==  String.valueOf( date )  ){
                jComboBox2.setSelectedIndex ( i  );
            }
        }
        
        jLabel5.setText(  jComboBox1.getSelectedItem().toString()   ) ;
        jLabel9.setText(  jComboBox2.getSelectedItem().toString()   ) ;
        jLabel10.setText(  jComboBox3.getSelectedItem().toString()   ) ;
        
        jComboBox1.addItemListener ( itemChanged );
        jComboBox2.addItemListener ( itemChanged );
        jComboBox3.addItemListener ( itemChanged );
    }
    
    ActionListener yearSelection = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
            jComboBox2.removeAllItems() ;
            int year = Integer.parseInt ( jComboBox3.getSelectedItem().toString() );
            int month = jComboBox1.getSelectedIndex() ;
            int lastDate ;
            
            Calendar c = Calendar.getInstance() ;
            c.set( Calendar.YEAR , year );
            c.set( Calendar.MONTH ,  month );
            
            lastDate = c.getActualMaximum ( Calendar.DAY_OF_MONTH) ;
            
            for ( int i = 1 ; i <= lastDate ; i ++ ) {
                jComboBox2.addItem ( i+""  );
            }
        }
    };
    
    ActionListener monthSelection = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
             jComboBox2.removeAllItems() ;
            int year = Integer.parseInt ( jComboBox3.getSelectedItem().toString() );
            int month = jComboBox1.getSelectedIndex() ;
            int lastDate ;
            
            Calendar c = Calendar.getInstance() ;
            c.set( Calendar.YEAR , year );
            c.set( Calendar.MONTH ,  month );
            
            lastDate = c.getActualMaximum ( Calendar.DAY_OF_MONTH) ;

            for ( int i = 1 ; i <= lastDate ; i ++ ) {
                            jComboBox2.addItem ( i+""  );
            }
        }
    };
    
    public Date getSelectedDate(){
        
        Calendar c = Calendar.getInstance() ;
        
        c.set( Calendar.HOUR_OF_DAY ,  0 ) ;
        c.set( Calendar.MINUTE ,  0 ) ;
        c.set( Calendar.SECOND ,  0 ) ;
        c.set( Calendar.YEAR ,  Integer.parseInt( jComboBox3.getSelectedItem().toString ()) ) ;
        c.set( Calendar.MONTH ,  Integer.parseInt( jComboBox1.getSelectedIndex()+"" ) ) ;
        c.set( Calendar.DAY_OF_MONTH ,  Integer.parseInt( jComboBox2.getSelectedItem().toString ()) ) ;
        
        
        
        return c.getTime () ;
    }
    
    ItemListener itemChanged = new ItemListener () {
        @Override
        public void itemStateChanged ( ItemEvent e ) {
            jLabel5.setText(  jComboBox1.getSelectedItem().toString()   ) ;
            jLabel10.setText(  jComboBox3.getSelectedItem().toString()   ) ;
            
            if( jComboBox2.getItemCount() > 0 ){
                jLabel9.setText(  jComboBox2.getSelectedItem().toString()   ) ;
            }
        }
    };
    
    FocusListener getDate = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
            jLabel5.setText(  jComboBox2.getSelectedItem().toString()   ) ;
            jLabel9.setText(  jComboBox1.getSelectedItem().toString()   ) ;
            jLabel3.setText(  jComboBox3.getSelectedItem().toString()   ) ;
                    
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            jLabel5.setText(  jComboBox2.getSelectedItem().toString()   ) ;
            jLabel9.setText(  jComboBox1.getSelectedItem().toString()   ) ;
            jLabel3.setText(  jComboBox3.getSelectedItem().toString()   ) ;
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

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(267, 64));
        setLayout(null);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("*");
        add(jLabel2);
        jLabel2.setBounds(0, 0, 10, 19);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Label Name");
        jLabel1.setNextFocusableComponent(jComboBox2);
        add(jLabel1);
        jLabel1.setBounds(14, 0, 230, 16);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        add(jLabel3);
        jLabel3.setBounds(0, 50, 260, 15);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 8)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        add(jLabel4);
        jLabel4.setBounds(20, 40, 30, 0);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("month");
        add(jLabel5);
        jLabel5.setBounds(0, 20, 40, 20);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 8)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Month");
        add(jLabel6);
        jLabel6.setBounds(120, 10, 50, 11);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 8)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Year");
        add(jLabel7);
        jLabel7.setBounds(60, 10, 40, 11);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 8)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Date");
        add(jLabel8);
        jLabel8.setBounds(190, 10, 30, 11);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox3.setNextFocusableComponent(jComboBox1);
        add(jComboBox3);
        jComboBox3.setBounds(80, 22, 55, 26);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }));
        jComboBox1.setNextFocusableComponent(jComboBox2);
        add(jComboBox1);
        jComboBox1.setBounds(140, 22, 55, 26);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox2.setNextFocusableComponent(jLabel1);
        add(jComboBox2);
        jComboBox2.setBounds(200, 22, 50, 26);

        jLabel9.setBackground(new java.awt.Color(51, 51, 51));
        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Dt");
        add(jLabel9);
        jLabel9.setBounds(40, 15, 30, 25);

        jLabel10.setBackground(new java.awt.Color(102, 102, 102));
        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("year");
        add(jLabel10);
        jLabel10.setBounds(20, 40, 50, 10);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
