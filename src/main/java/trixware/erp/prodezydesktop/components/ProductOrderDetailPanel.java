/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.components;

import trixware.erp.prodezydesktop.Model.DeliveryScheduleModel;
import datechooser.view.WeekDaysStyle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import trixware.erp.prodezydesktop.examples.HomeScreen;
import trixware.erp.prodezydesktop.examples.SalesMaster;

import static trixware.erp.prodezydesktop.examples.HomeScreen.width ;
import static trixware.erp.prodezydesktop.examples.HomeScreen.height ;



/**
 *
 * @author Rajesh
 */
public class ProductOrderDetailPanel extends javax.swing.JPanel {

    
    //ProductOrderDetailPanel p_o_Model ;
    int part_id ;
    String partName_Code , partQty, edd, rate, unit ;

    ArrayList<DeliveryScheduleModel> schdListForItem = null ;
    
    
    
    /**
     * Creates new form RMQtys
     */
    public ProductOrderDetailPanel ( boolean checked  ) {
        initComponents ();
        
      //  p_o_Model = new ProductOrderDetailPanel();
   
         if( checked)
        {
            jTextField2.setEditable(false);
        }
        else{
            jTextField2.setEditable(true);
        }
      
        jTextField1.addKeyListener ( k );
        jTextField1.addFocusListener ( f2 );        
        jTextField2.addKeyListener ( k2 );
        jTextField2.addFocusListener ( f );        

        dateChooserCombo1.setFormat ( DateFormat.MEDIUM );
        dateChooserCombo1.setWeekStyle ( WeekDaysStyle.NORMAL );
        dateChooserCombo1.setOpaque ( false );
        dateChooserCombo1.setBackground ( Color.WHITE );
        dateChooserCombo1.revalidate ();
        dateChooserCombo1.repaint ();

    }
 
    public int getPart_id () {
        return  Integer.parseInt( jLabel1.getText() );
    }

    public void setPart_id ( int part_id ) {
        
        this.part_id = part_id;
        this.jLabel1.setText( part_id+"" ) ;
    }

    public String getPartName_Code () {
        return this.jLabel2.getText();
    }

    public void setPartName_Code ( String partName_Code ) {
        this.partName_Code = partName_Code;
        this.jLabel2.setText( partName_Code  ) ;
    }

    public String getPartQty () {
        return jTextField1.getText();
    }

    public void setPartQty ( String partQty ) {
        this.partQty = partQty;
        this.jTextField1.setText( partQty );
    }

    public String getEdd () {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( dateChooserCombo1.getSelectedDate().getTime() );
    }

    public void setEdd ( String edd ) {
        this.edd = edd;
        this.dateChooserCombo1.setText (  edd  ) ;
    }

    public String getRate () {
        //return rate;
        return jTextField2.getText ( ).trim ();
    
    }

    public void setRate ( String rate ) {
        this.rate = rate;
        this.jTextField2.setText (  rate );
    }

    public String getUnit () {
        return unit;
    }

    public void setUnit ( String unit ) {
        this.unit = unit;
    }
    
    
   public String getPartOrderValue(){
       
        int qty = Integer.parseInt(  getPartQty () )  ;
        double rate = Double.parseDouble(  getRate ())  ;
       
        
       
       return String.valueOf(  qty*rate ) ;
   }
    
   public ArrayList< DeliverySchDetailPanel  > getSchedule(){
       
       return dialog.getSchedule () ;      
   }
   
   public void setOrderSchedule( ArrayList< DeliveryScheduleModel  > schedule  ){
        schdListForItem = schedule  ;
    }
    
    public ArrayList< DeliveryScheduleModel  > getOrderScheduleForItem(  ){
        //return shcdListForOrder.get( index  ) ;
        return schdListForItem ;
    }

//    public ArrayList<ArrayList<DeliveryScheduleModel>> getOrderFullSchedule(  ){
//        return shcdListForOrder ;
//    }    
   
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(650, 50));
        setLayout(null);

        jLabel1.setOpaque(true);
        add(jLabel1);
        jLabel1.setBounds(0, 0, 0, 0);
        add(jLabel2);
        jLabel2.setBounds(10, 12, 260, 30);

        jTextField1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(290, 10, 90, 30);

        dateChooserCombo1.setFieldFont(new java.awt.Font("Leelawadee UI", java.awt.Font.PLAIN, 14));
        add(dateChooserCombo1);
        dateChooserCombo1.setBounds(390, 10, 120, 30);

        jLabel3.setText("jLabel3");
        jLabel3.setPreferredSize(new java.awt.Dimension(0, 0));
        add(jLabel3);
        jLabel3.setBounds(140, 0, 0, 0);

        jTextField2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        add(jTextField2);
        jTextField2.setBounds(520, 10, 90, 30);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jButton1.setText("Schedule");
        jButton1.setToolTipText("<html>Add Schedule for sinlge deliverable item from a Sales Order<br>This can be added to updated after adding the sales order</html>");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(620, 10, 90, 30);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1FocusGained

    DeliveryScheduleDialog2 dialog = null ;
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        dialog = new DeliveryScheduleDialog2 ( HomeScreen.home , Integer.parseInt( jTextField1.getText() ) );
        dialog.setVisible ( true );
        
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    KeyListener k = new KeyListener () {
        @Override
        public void keyTyped ( KeyEvent e ) {
           char enter = e.getKeyChar();
            if (!(Character.isDigit(enter))) {
                e.consume();
            }else{
                int qty = e.getKeyChar(); 
                  if( qty<=0  ){                     
                }else{
             //     SalesMaster.jLabel6.setText ( SalesMaster.updateGrandTotal()+"" );
            //          SalesMaster.jLabel6.setText ( "11321238" );
            //          StaticValues sv = new StaticValues() ;
            //          sv.sm.jLabel6.setText ( sv.sm.updateGrandTotal () +"") ;
                }
            }
        }

        @Override
        public void keyPressed ( KeyEvent e ) {
        }

        @Override
        public void keyReleased ( KeyEvent e ) {
            
            char enter = e.getKeyChar();
            if (!(Character.isDigit(enter))) {
                e.consume();
            }else{
              /*  int qty = e.getKeyChar(); 
                if( qty<0  ){
                     
                }else{
                    
                        
                        
                    //SalesMaster.jLabel6.setText ( SalesMaster.updateGrandTotal () +""   );
                    //SalesMaster.so.updateGrandTotal () ;
                }*/
            }           
        }
    };
    
    KeyListener k2 = new KeyListener () {
        @Override
        public void keyTyped ( KeyEvent e ) {
           char enter = e.getKeyChar();
           String dot = Character.toString ( enter );
           
            if (!(Character.isDigit(enter))   && ! dot.equals ( "." )  ) {
                e.consume();
            }
        }

        @Override
        public void keyPressed ( KeyEvent e ) {
        }

        @Override
        public void keyReleased ( KeyEvent e ) {
        }
    };
    
    FocusListener f2 = new FocusListener () {
        
        double oldQty = 0 ;
        
        @Override
        public void focusGained ( FocusEvent e ) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();    
            oldQty = Integer.parseInt( jcb.getText() )*1.0;
            jcb.selectAll ();
        }

        @Override
        public void focusLost ( FocusEvent e ) {
        //    throw new UnsupportedOperationException ( "Not supported yet." ); //To Search Google or type URL
//change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();    
            String x = jcb.getText().trim ();
            
            if(x.equalsIgnoreCase ( "")){
                x = "0";
            }
            
            try{    
                int num  = Integer.parseInt( String.valueOf( jcb.getText().toString() ) );
                if( num<0  ){
                     jcb.setText(x);
                     //evt.consume ();
                }else{
                    
                    double currentQty = Integer.parseInt( getPartQty () ) *1.0 ;
                    // old qty is greater than current qty
                    // old qty and current qty is same
                    // old qty is less than current qty
                    
                    if( oldQty > currentQty ){
                        double diff = oldQty - currentQty ; 
                        double value = ( diff )  * ( Integer.parseInt( getRate()) * 1.0 ) ;
                        jLabel3.setText ( value +"" ) ;
                        //double value2 = Double.parseDouble( SalesMaster.jLabel17.getText ()  );
                        
                            double value2 =  Double.parseDouble( SalesMaster.jLabel6.getText ()  );
                            SalesMaster.jLabel6.setText ( value2- value +"" ) ;
                        
                    }else if( oldQty == currentQty ){
                        
                        double value = ( Integer.parseInt( getPartQty () ) *1.0)  * ( Integer.parseInt( getRate()) * 1.0 ) ;
                        jLabel3.setText ( value +"" ) ;
                        //double value2 = Double.parseDouble( SalesMaster.jLabel17.getText ()  );
                        
                        if( value == 0.0   ){
                            if( oldQty > 0  ){
                                value = ( oldQty *1.0)  * ( Integer.parseInt( getRate()) * 1.0 ) ;
                                
                                double value2 =  Double.parseDouble( SalesMaster.jLabel6.getText ()  );
                                //SalesMaster.jLabel6.setText ( value2- value +"" ) ;
                            }
                        } else{
                            double value2 =  Double.parseDouble( SalesMaster.jLabel6.getText ()  );
                            //SalesMaster.jLabel6.setText ( value+ value2 +"" ) ;
                        }
                    }else if( oldQty < currentQty ){
                    
                        double diff = currentQty - oldQty ;
                        
                        double value = ( diff )  * ( Integer.parseInt( getRate()) * 1.0 ) ;
                        jLabel3.setText ( value +"" ) ;
                        //double value2 = Double.parseDouble( SalesMaster.jLabel17.getText ()  );
                        
                        if( value == 0.0   ){
                            if( oldQty > 0  ){
                                value = ( oldQty *1.0)  * ( Integer.parseInt( getRate()) * 1.0 ) ;
                                double value2 =  Double.parseDouble( SalesMaster.jLabel6.getText ()  );
                                SalesMaster.jLabel6.setText ( value2- value +"" ) ;
                            }
                        } else{
                            double value2 =  Double.parseDouble( SalesMaster.jLabel6.getText ()  );
                            SalesMaster.jLabel6.setText ( value+ value2 +"" ) ;
                        }
                    }
                }  
            }catch(NumberFormatException ex1){

            }
        }
    };
    
    FocusListener f = new FocusListener () {
        
        double oldQty = 0 ;
        
        @Override
        public void focusGained ( FocusEvent e ) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();    
            oldQty = Integer.parseInt( jcb.getText() )*1.0;
            jcb.selectAll ();
        }

        @Override
        public void focusLost ( FocusEvent e ) {
        //    throw new UnsupportedOperationException ( "Not supported yet." ); //To Search Google or type URL
//change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();    
            String x = jcb.getText().trim ();
            
            if(x.equalsIgnoreCase ( "")){
                x = "0";
            }
            
            try{    
                int num  = Integer.parseInt( String.valueOf( jcb.getText().toString() ) );
                if( num<0  ){
                     jcb.setText(x);
                     //evt.consume ();
                }else{
                    
                }  
            }catch(NumberFormatException ex1){

            }
        }
    };

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

        class DeliveryScheduleDialog2 extends JDialog {

        private JTextField tfUsername;
        private JPasswordField pfPassword;
        private JLabel lbUsername;
        private JLabel lbPassword;
        private JButton btnLogin;
        private JButton btnCancel;
        private boolean succeeded;

        DeliveryScheduleForm2 confirm ;

        public DeliveryScheduleDialog2(Frame parent  , int targetQty) {
            super(parent, "Delivery Schedule", true);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ();
            GraphicsDevice[] gs = ge.getScreenDevices ();
            for ( int i = 0 ; i < gs.length ; i ++ ) {
                DisplayMode dm = gs[ i ].getDisplayMode ();
                if ( width > dm.getWidth () || width == 0 ) {
                    width = dm.getWidth ();
                    height = dm.getHeight ();
                }
            }

            if(    schdListForItem != null && schdListForItem.size() > 0     ){
                    confirm = new DeliveryScheduleForm2( 1  , targetQty  );
                    confirm.setBounds(  0    , 0, 432,  385);    
            }else{
                    confirm = new DeliveryScheduleForm2(  targetQty  );
                    confirm.setBounds(  0    , 0, 432,  385);
            }
                    
            getContentPane().add( confirm  , BorderLayout.CENTER);

            pack();
            setResizable(true);
            setBounds(  0    , 0,  450,420 );
            setLocationRelativeTo(parent);

        }

        public ArrayList< DeliverySchDetailPanel  > getSchedule(){

            
            return confirm.getSchedule() ;
        }

        public void closeBOMWindow(){
             dispose();
        }

        class DeliveryScheduleForm2 extends javax.swing.JPanel {

        JPanel listPanel = null ;
        JScrollPane jScrollPane1 = null ;

        double _targetQty = 0.0;
        
        ArrayList< DeliverySchDetailPanel  >  shcdPanelList = new ArrayList< DeliverySchDetailPanel  > () ;
        public ArrayList< DeliverySchDetailPanel  >  filledShcdPanelList = new ArrayList< DeliverySchDetailPanel  > () ;

        /**
         * Creates new form DeliveryScheduleForm
         */
        public DeliveryScheduleForm2 (  int target ) {
            initComponents ();

            listPanel = new JPanel() ;
            listPanel.setBackground( Color.white  );
            listPanel.setBounds( 0,0, 250, 5 ) ;
            
            _targetQty = target  *1.0;
                    
            listPanel.setLayout ( new BoxLayout ( listPanel , BoxLayout.Y_AXIS ) );
            //listPanel.setLayout (null );

            jScrollPane1 = new JScrollPane( listPanel, 
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED   ) ;

            jScrollPane1.setBounds(  20, 50, 400, 280  ) ;

            add(jScrollPane1);

           dp = new DeliverySchDetailPanel ();
           dp.setBounds ( 0, 0, 250, 50);
           //dp.setDate (  new SimpleDateFormat( "MMM dd, yyyy" ).format ( dateChooserCombo1.getSelectedDate ().getTime ()   )) ;
           dp.setQty ( 0.0 );
            
           listPanel.add(dp);
           shcdPanelList.add(   dp   ) ;

            revalidate ();
            repaint ();
        }
        
        public DeliveryScheduleForm2 (  int x , int target) {
            initComponents ();

            listPanel = new JPanel() ;
            listPanel.setBackground( Color.white  );
            listPanel.setBounds( 0,0, 250, 5 ) ;

            _targetQty = target *1.0;
            
            listPanel.setLayout ( new BoxLayout ( listPanel , BoxLayout.Y_AXIS ) );
            jScrollPane1 = new JScrollPane( listPanel, 
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED   ) ;

            jScrollPane1.setBounds(  20, 50, 400, 280  ) ;
            add(jScrollPane1);

            
            if( schdListForItem!=null && schdListForItem.size()>0 ){
                
                for(  int i=0; i< schdListForItem.size() ; i++   ){
                    try {
                        dp = new DeliverySchDetailPanel ();
                        
                        DeliveryScheduleModel schedule = schdListForItem.get(i) ;
                        
                        dp.setBounds ( 0, i*50, 250, 50);
                        
                        
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = sdf.parse(  schedule.getDate()   );
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        dp.setDate (  cal  ) ;
                        
                        dp.setQty ( schedule.getQty ()    );
                        listPanel.add(dp);
                        shcdPanelList.add(   dp   ) ;
                    } catch ( ParseException ex ) {
                    }
                }
                
            }else{
                dp = new DeliverySchDetailPanel ();
                dp.setBounds ( 0, 0, 250, 50);
              //  dp.setDate (  new SimpleDateFormat().format ( dateChooserCombo1.getSelectedDate ().getTime ()   )) ;
                dp.setQty ( 0.0 );
                listPanel.add(dp);
                shcdPanelList.add(   dp   ) ;
            }
            revalidate ();
            repaint ();
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings( "unchecked" )
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {

            jButton2 = new javax.swing.JButton();
            jButton1 = new javax.swing.JButton();
            jButton3 = new javax.swing.JButton();

            jButton2.setText("jButton2");

            setLayout(null);

            jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton1.setText("Save & Close");
            jButton1.setOpaque(false);
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });
            add(jButton1);
            jButton1.setBounds(290, 340, 130, 32);
            
            
            jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton2.setText("Discard & Close");
            jButton2.setOpaque(false);
            jButton2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton2ActionPerformed(evt);
                }
            });
            add(jButton2);
            jButton2.setBounds(150, 340, 130, 32);

            jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton3.setText("Add New Row");
            jButton3.setOpaque(false);
            jButton3.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton3ActionPerformed(evt);
                }
            });
            add(jButton3);
            jButton3.setBounds(307, 10, 110, 32);
        }// </editor-fold>                        

        DeliverySchDetailPanel dp = null ;
        int totalPanels = 1 ;
        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
            // TODO add your handling code here:
            dp = new DeliverySchDetailPanel ();
            dp.setBounds ( 0, (totalPanels)*40, 250, 50);
           // dp.setDate (  new SimpleDateFormat().format ( dateChooserCombo1.getSelectedDate ().getTime ()   )) ;
            dp.setQty ( 0.0 );
            
            listPanel.add(dp);
            shcdPanelList.add(   dp   ) ;
            
            totalPanels++ ;
            revalidate ();
            repaint ();
        }                                        

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
            // TODO add your handling code here:
            
            double totalTarget = 0.0 ;
            
            DeliveryScheduleModel dsm = null ;
            schdListForItem  = new ArrayList<DeliveryScheduleModel> () ;
            for (   int i=0; i< shcdPanelList.size () ; i++   )  {
                dp = shcdPanelList.get(i);
                double qty = 0.0 ;
                if( ( qty = dp.getQty () )  > 0.0   ){
                    
                    dsm = new DeliveryScheduleModel ();
                    dsm.setDate ( dp.getDate ()  );
                    dsm.setQty( qty );
                    schdListForItem.add(  dsm ) ;

                    totalTarget = totalTarget + qty ;
                }
            }
            
            if( _targetQty < totalTarget ){
                schdListForItem  = null ;
                JOptionPane.showMessageDialog ( null, "Total target deliverable quantity is less than total of schedule", "Deliverable qunatity total do not match", JOptionPane.ERROR_MESSAGE);
            }  else{
                closeBOMWindow ();
            }
        }                
        
        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
            // TODO add your handling code here:
            closeBOMWindow ();
        }                


        public ArrayList< DeliverySchDetailPanel  > getSchedule(){
            return filledShcdPanelList ;
        }

        // Variables declaration - do not modify                     
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JButton jButton3;
        // End of variables declaration                   
        }
    }

}
