/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.planning;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.json.JSONArray;

/**
 *
 * @author Rajesh
 */
public class PlanningPanelChildren extends javax.swing.JPanel {

    private String orderNo, partNo  ; 
    private int orderId, partId , allocQty ;
    double prodQty ,  orderQty  , cycleTime ;
    JSONArray partProcMap ;
    
    /**
     * Creates new form PlanningPanelChildren
     */
    public PlanningPanelChildren () {
        initComponents ();
        jTextField1.addFocusListener ( f2 );
    }

    public String getOrderNo () {
        
        return jLabel2.getText () ;
    }

    public void setOrderNo ( String orderNo ) {
        this.orderNo = orderNo;
        jLabel2.setText ( orderNo  );
    }

    public String getPartNo () {
        return jLabel3.getText () ;
    }

    public void setPartNo ( String partNo ) {
        this.partNo = partNo;
        jLabel2.setText (partNo) ;
    }

    public double getOrderQty () {
        //return orderQty;
        String val =  jLabel1.getText();
        try{
            return Integer.parseInt( val  )*1.0 ;
        }catch(  NumberFormatException e   ){
            return Double.parseDouble( val  )*1.0 ;    
        }
    }

    public void setOrderQty ( double orderQty ) {
        this.orderQty = orderQty ;
        jLabel1.setText (orderQty +"") ;
    }

    public int getOrderId () {
        return orderId;
    }

    public void setOrderId ( int orderId ) {
        this.orderId = orderId;
    }

    public int getPartId () {
        return partId;
    }

    public void setPartId ( int partId ) {
        this.partId = partId;
    }

    public int getAllocQty () {
        //return allocQty;
        return Integer.parseInt( jTextField1.getText() ) ;
    }

    public void setAllocQty ( int allocQty ) {
        this.allocQty = allocQty;
        jTextField1.setText(    allocQty+""   ) ;
    }

    public double getProdQty () {
        //return prodQty;
        String val =  jTextField1.getText();
        try{
            return Integer.parseInt( val  )*1.0 ;
        }catch(  NumberFormatException e   ){
            return Double.parseDouble( val  )*1.0 ;    
        }
    }

    public void setProdQty ( double prodQty ) {
        this.prodQty = prodQty ;
        jTextField1.setText(    prodQty+""    );
    }

    public double getTotalCycleTime () {
        return cycleTime;
    }

    public void setTotalCycleTime ( double cycleTime ) {
        this.cycleTime = cycleTime;
    }

    public JSONArray getPartProcMap () {
        return partProcMap;
    }

    public void setPartProcMap ( JSONArray partProcMap ) {
        this.partProcMap = partProcMap;
    }

    
    
    
    // user enter qty to produce in the text field against required qty
    // 1. load the process machine time availability table chart - wherever the time available is 0 disable that field
    // 2. get the processwise cycle time of part 
    // 3. calculate the total time required produce the qty entered by user
    // 4. compare the total time required and the max time available 
    // 5. if total time required is greater than max available time then show error
    //     else if total time required is less or equal max available time then create an entry of this time in plan and subtract the allocated time from max availability
    // 6. repeat step 1

     
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
                    for ( int i = 0 ; i < partProcMap.length() ; i ++ ) {
                        if(   ( cycleTime * orderQty )  >   Double.parseDouble(   partProcMap.getJSONObject(i).get( "maxtm").toString()    )      ){
                                JOptionPane.showMessageDialog ( null, "Cannot produce this much qty in specified time");
                        }else{
                                JOptionPane.showMessageDialog ( null, "Production Qty accepted");    
                        }
                    }
                }  
            }catch(NumberFormatException ex1){

            }
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

        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(171, 75));
        setLayout(null);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Order1", "Order2", "Order3", "Order4" }));
        jComboBox1.setOpaque(false);
        add(jComboBox1);
        jComboBox1.setBounds(0, 0, 20, 25);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Part1", "Part2", "Part3", "Part4", " " }));
        jComboBox2.setOpaque(false);
        add(jComboBox2);
        jComboBox2.setBounds(0, 23, 20, 25);

        jLabel1.setBackground(new java.awt.Color(102, 102, 102));
        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("1253");
        add(jLabel1);
        jLabel1.setBounds(0, 50, 70, 20);

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(102, 102, 102));
        jTextField1.setText("0");
        add(jTextField1);
        jTextField1.setBounds(70, 49, 90, 23);

        jLabel2.setBackground(new java.awt.Color(51, 51, 51));
        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jLabel2);
        jLabel2.setBounds(30, 0, 130, 20);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jLabel3);
        jLabel3.setBounds(30, 26, 130, 20);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
