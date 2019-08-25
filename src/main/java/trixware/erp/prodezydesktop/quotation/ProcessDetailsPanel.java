/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.quotation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class ProcessDetailsPanel extends javax.swing.JPanel {

    ArrayList<String[]> processes = new ArrayList<String[]>() ;
    ArrayList<String[]> measunit = new ArrayList<String[]>() ;
    ArrayList<String[]> units =new ArrayList<String[]>();
    ArrayList<String[]> processes1 = new ArrayList<String[]>() ;
    DecimalFormat df = new DecimalFormat("#.##");
    double qty = 0.0 ;
    /**
     * Creates new form PartDetailsPanel
     */
    public ProcessDetailsPanel () {
        initComponents ();
    }

    ActionListener actionListener=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadUnits();
        }
    };

    ActionListener actionListener10=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadLabels();
        }
    };

    public void loadLabels()
    {
        try {
            String uom = units.get(jComboBox1.getSelectedIndex())[0];
            String uomPrice = units.get(jComboBox1.getSelectedIndex())[1];
            jTextField2.setEnabled(true);
            String uni = "";
            switch (uom.toLowerCase()) {
                case "min":
                    uni = "<html>Cycle Time<br>in min</html>";
                    break;
                case "kg":
                    uni = "<html>Part Weight<br>in kg</html>";
                    break;
                case "mg":
                    uni = "<html>Part Weight<br>in kg</html>";
                    break;
                case "grm":
                    uni = "<html>Part Weight<br>in kg</html>";
                    break;
                case "hrs":
                    uni = "<html>Cycle Time<br>in min</html>";
                    break;
                case "hours":
                    uni = "<html>Cycle Time<br>in min</html>";
                    break;
                case "sec":
                    uni = "<html>Cycle Time<br>in min</html>";
                    break;
                case "second":
                    uni = "<html>Cycle Time<br>in min</html>";
                    break;
                case "nos":
                    uni = "";
                    jTextField2.setText("0.0");
                    jTextField2.setEnabled(false);
                    break;
                default:
                    uni = uom;
                    break;
            }
            //    jLabel2.setText(uom);
            jLabel3.setText(uomPrice);
            jLabel2.setText(uni);
            calculations();
        } catch (Exception ex) {
            
        }
    }
    public void loadUnits()
    {
        int index = jComboBox2.getSelectedIndex();
        String pid=processes.get(index)[0];

            String addEmpAPICall = "unitprocessdetails?process_id="+pid;
            String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
            if (!result2.contains("not found")) {
                if (result2 != null) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jObject = new JSONObject(result2);
                    Iterator<?> keys = jObject.keys();

                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Object value = jObject.get(key);
                        map.put(key, value);
                    }

                    JSONObject st = (JSONObject) map.get("data");
                    JSONArray records = st.getJSONArray("records");
                    JSONObject emp = null;
                    jComboBox1.removeAllItems();
                    units=new ArrayList<String[]>();
                    for (int i = 0; i < records.length(); i++) {
                        emp = records.getJSONObject(i);
                        String[] data = new String[2];
                        jComboBox1.addItem(emp.get("unit_name").toString().replace(" ", ""));
                        data[0] = emp.get("unit_name").toString();
                        data[1] = emp.get("unit_cost").toString();                        
                       
                        units.add(data);
                    }       
                }
            }
            loadLabels();
    }
    
    public ProcessDetailsPanel ( ArrayList<String[]> _processes, ArrayList<String[]> _measunit, double _qty ) {
        initComponents ();
        qty = _qty ;
        processes = _processes ;
        measunit  = _measunit ;
        for ( int i = 0 ; i < processes.size(); i ++ ) {
            jComboBox2.addItem ( processes.get(i)[1]);
        }
 
        jComboBox2.addActionListener(actionListener);
        jComboBox1.addActionListener(actionListener10);
//        loadUnits();
//        for ( int i = 0 ; i < measunit.size(); i ++ ) {
////            jComboBox1.addItem ( measunit.get(i)[1]);
//              jLabel2.setText(measunit.get(i)[1]);
//        }
       
//        jLabel3.setText ( 25.0+""  );
        
//        jTextField1.setEnabled(false) ;
        jLabel3.addKeyListener ( k2 );        
        jTextField2.addKeyListener ( k2 );
        jTextField3.addKeyListener ( k2 );
        jLabel3.addFocusListener(f2);        
        jTextField2.addFocusListener(f2);
        jTextField3.addFocusListener (f2 );
        
        jTextField5.addKeyListener ( k2 );
        jTextField1.addKeyListener ( k2 );
        jTextField4.addKeyListener ( k2 );
        jTextField6.addKeyListener ( k2 );
        jTextField7.addKeyListener ( k2 );
        jTextField8.addKeyListener ( k2 );
        jTextField9.addKeyListener ( k2 );

        jTextField5.addFocusListener (f2 );
        jTextField1.addFocusListener (f2 );
        jTextField4.addFocusListener (f2 );
        jTextField6.addFocusListener (f2 );
        jTextField7.addFocusListener (f2 );
        jTextField8.addFocusListener (f2 );
        jTextField9.addFocusListener (f2 );
        
        
        loadUnits();
        loadLabels();
        revalidate ();
        repaint ();
        labelVisibility();
                
    }
    double setupCostAmt;
    double fixturingAmt;
    double inspectionAmt;
    double localTransAmt;
    double handlingAmt;
    double rejectionAmt;

    public double getSetupCostAmt() {
        return setupCostAmt;
    }

    public double getFixturingAmt() {
        return fixturingAmt;
    }

    public double getInspectionAmt() {
        return inspectionAmt;
    }

    public double getLocalTransAmt() {
        return localTransAmt;
    }

    public double getHandlingAmt() {
        return handlingAmt;
    }

    public double getRejectionAmt() {
        return rejectionAmt;
    }
   
    public void calculations()
    {        
        Double basicRate=Double.parseDouble(jLabel3.getText());        
        String unit=jComboBox1.getSelectedItem().toString().replace(" ", "");       

        String overHead = jTextField3.getText();
        String batchQuantity=jTextField5.getText();
        String setupTime=jTextField1.getText();
        String setupCost=jTextField4.getText();
        String fixturingCost=jTextField6.getText();
        String inspectionCost=jTextField7.getText();
        String localTransCost=jTextField8.getText();
        String handlingCharges=jTextField9.getText();
        
        Double effInput=0.0;
        String effInput2=jTextField2.getText().replace(" ", "");
        if(!effInput2.equals(""))
        {
            effInput=Double.parseDouble(effInput2);
        }

        Double temp=0.0;
      
        switch(unit.toLowerCase())
        {
            case "hours":
                    basicRate=basicRate/60;
                break;
            case "hrs":
                    basicRate=basicRate/60;
                break;

            case "min":
                
                break;
            case "sec": 
                basicRate=basicRate*60;
                break;                
            case "second": 
                basicRate=basicRate*60;                
                break;                
            case "kg": 
               // basicRate=basicRate/1000;
                break;                          
            case "grm": 
                basicRate=basicRate*1000;
                break;  
                
            case "nos":
                effInput=1.0;
                break;
            default:
                break;
        }
        
        temp=effInput*basicRate;
        
        if(jCheckBox1.isSelected()&& (temp!=0.0 || temp!=0) )
        {
            if (!setupTime.equals("") && !setupTime.equals("0.0") && !setupTime.equals("0") && !setupCost.equals("") && !setupCost.equals("0.0") && !setupCost.equals("0")) {
                double totalSetupCost = Double.parseDouble(setupTime) * Double.parseDouble(setupCost);
                double setupCostPerQuantity = totalSetupCost / Double.parseDouble(batchQuantity);
                setupCostAmt=setupCostPerQuantity;
                temp = temp + setupCostPerQuantity;
            }
            if (!fixturingCost.equals("") && !fixturingCost.equals("0.0") && !fixturingCost.equals("0")) {
                double fixturiCost = Double.parseDouble(fixturingCost) / Double.parseDouble(batchQuantity);
                fixturingAmt=fixturiCost;
                temp = temp + fixturiCost;
            }
            if (!inspectionCost.equals("") && !inspectionCost.equals("0.0") && !inspectionCost.equals("0")) {
                double cost = Double.parseDouble(inspectionCost) / Double.parseDouble(batchQuantity);
                inspectionAmt=cost;
                temp = temp + cost;
            }
            if (!localTransCost.equals("") && !localTransCost.equals("0.0") && !localTransCost.equals("0")) {
                double cost = Double.parseDouble(localTransCost) / Double.parseDouble(batchQuantity);
                localTransAmt=cost;
                temp = temp + cost;
            }
            if (!handlingCharges.equals("") && !handlingCharges.equals("0.0") && !handlingCharges.equals("0")) {
                double cost = Double.parseDouble(handlingCharges) / Double.parseDouble(batchQuantity);
                handlingAmt=cost;
                temp = temp + cost;
            }

            if (!overHead.equals("") && !overHead.equals("0.0") && !overHead.equals("0")) {
               
                Double TotalAmount=(temp * Double.parseDouble(overHead)) / 100;
                rejectionAmt=TotalAmount;
                temp = temp + TotalAmount;
            }            
        }else{
            setupCostAmt=0;
            fixturingAmt=0;
            inspectionAmt=0;
            localTransAmt=0;
            handlingAmt=0;
            rejectionAmt=0;
        }
        jLabel1.setText(df.format(temp));
    }
    public String getProcessID(){
        return processes.get( jComboBox2.getSelectedIndex ()  )[0] ;
    }
    public String getProcessName(){
        return processes.get( jComboBox2.getSelectedIndex ()  )[1] ;
    }    
    
    
//    public void get
//    public String getUnitID(){
//        
////        return measunit.get( jComboBox1.getSelectedIndex ())[0];
//
//    }
    public String getUnitName()
    {
        return jComboBox1.getSelectedItem().toString();
    }
    public String getProcessRate(){
        return jLabel3.getText();
    }

    public String getOtherExpence()
    {
        if(jCheckBox1.isSelected())
        {
            return "y";
        }else{
            return "n";            
        }
    }
    public void setOtherExpence(String other)
    {
        if(other.equals("y"))
        {
            jCheckBox1.setSelected(true);
        }else{
            jCheckBox1.setSelected(false);
        }
        labelVisibility();
    }
    public void setSetupCost(String setupCost)
    {
        jTextField4.setText(setupCost);
    }
    public String getSetupCost()
    {
        return jTextField4.getText();
    }
    public void setSetupTime(String setupCost)
    {
        jTextField1.setText(setupCost);
    }
    public String getSetupTime()
    {
        return jTextField1.getText();
    }
    public void setInspectionCost(String setupCost)
    {
        jTextField7.setText(setupCost);
    }
    public String getInspectionCost()
    {
        return jTextField7.getText();
    }
    public void setTransCost(String setupCost)
    {
        jTextField8.setText(setupCost);
    }
    public String getTransCost()
    {
        return jTextField8.getText();
    }
    public void setFixturingCost(String setupCost)
    {
        jTextField6.setText(setupCost);
    }
    public String getFixturingCost()
    {
        return jTextField6.getText();
    }

    public void setHandlingCost(String setupCost)
    {
        jTextField9.setText(setupCost);
    }
    public String getHandlingCost()
    {
        return jTextField9.getText();
    }

    public int getProcessLocationId(){
        if( jRadioButton1.isSelected () ){
            return 1 ;
        }else {
            return 2 ;
        }        
    }
    
    public String getProcessLocationName(){
        if( jRadioButton1.isSelected () ){
            return "I";
        }else {
            return "O" ;
        }        
    }
    
    public double getProcessCost()
    {
        try{
            double pCost=Double.parseDouble ( jLabel3.getText ());
            return pCost;
        }catch(Exception ex)
        {            
            return 0.0;
        }
    }
    public double getOutsorceCost()
    {
        try{
            double pCost=Double.parseDouble ( jTextField2.getText ());
            return pCost;
        }catch(Exception ex)
        {            
            return 0.0;
        }
    }
    public double getTextField3Cost()
    {
        try{
            double pCost=Double.parseDouble ( jTextField3.getText ());
            return pCost;
        }catch(Exception ex)
        {            
            return 0.0;
        }
    }
    
    public String getEffRate()
    {        
        return jTextField2.getText ();
    }
    
    public double getProcessOH()
    {
        try{
            double pCost=Double.parseDouble ( jTextField3.getText ());
            return pCost;
        }catch(Exception ex)
        {            
            return 0.0;
        }
    }
    
    public String getTotalProcessCost()
    {
        return jLabel1.getText() ;
    }
    
    
    public void setProcess( int id, String name ){
       
        for ( int i = 0 ; i < processes.size() ; i ++ ) {
            if( processes.get(i)[1].equals ( name ) ){
                jComboBox2.setSelectedIndex ( i);
            }
        }
    }

    public void setUnit( int id, String name ){
//        jLabel2.setText(name);
        jComboBox1.setSelectedItem(name.replace(" ", ""));
//         for ( int i = 0 ; i < measunit.size() ; i ++ ) {
//            if( measunit.get(i)[1].equals ( name ) ){
//                jComboBox1.setSelectedIndex ( i);
//            }
//        } 
    }
    
    public void setProcessLocation( int x , String name){
        if(  name.equals ("I") ){
            jRadioButton1.setSelected ( true );
        }else {
            jRadioButton2.setSelected ( true );
        }        
    }
    
    public void setProcessCost( String cost )
    {
        jLabel3.setText ( cost);
    }
    
    public void setEffRate( String cost )
    {
        jTextField2.setText ( cost );
    }
    
    public void setProcessOH( String OH )
    {
        jTextField3.setText ( OH ) ;
    }
    
    public void setTotalProcessCost( String cost )
    {
        jLabel1.setText( cost ) ;
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
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        setForeground(new java.awt.Color(153, 153, 153));
        setPreferredSize(new java.awt.Dimension(500, 190));
        setLayout(null);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jRadioButton1.setText("IN-HOUSE");
        add(jRadioButton1);
        jRadioButton1.setBounds(320, 10, 100, 20);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jRadioButton2.setText("OUTSROURCED");
        add(jRadioButton2);
        jRadioButton2.setBounds(320, 30, 105, 20);

        jTextField2.setText("0");
        jTextField2.setToolTipText("Effective Rate");
        add(jTextField2);
        jTextField2.setBounds(510, 10, 50, 30);

        jTextField3.setText("0");
        jTextField3.setToolTipText("Percentage ( % ) Overheads");
        add(jTextField3);
        jTextField3.setBounds(510, 110, 50, 30);

        add(jComboBox2);
        jComboBox2.setBounds(5, 10, 115, 30);
        add(jLabel5);
        jLabel5.setBounds(370, 30, 50, 0);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(jComboBox1);
        jComboBox1.setBounds(130, 10, 60, 30);
        add(jLabel3);
        jLabel3.setBounds(260, 10, 50, 30);
        add(jLabel2);
        jLabel2.setBounds(430, 10, 80, 30);

        jLabel6.setText("<html>Setup<br>Time in Hrs</html>");
        add(jLabel6);
        jLabel6.setBounds(10, 70, 80, 30);

        jTextField1.setText("0");
        add(jTextField1);
        jTextField1.setBounds(90, 70, 50, 30);

        jLabel7.setText("<html>Setup<br>Cost/Hrs</html>");
        add(jLabel7);
        jLabel7.setBounds(150, 70, 60, 30);

        jTextField4.setText("0");
        add(jTextField4);
        jTextField4.setBounds(220, 70, 50, 30);

        jTextField5.setText("0");
        add(jTextField5);
        jTextField5.setBounds(360, 70, 50, 30);

        jLabel8.setText("<html>Batch<br>Quantity</html>");
        add(jLabel8);
        jLabel8.setBounds(290, 70, 70, 30);

        jLabel9.setText("<html>Rejection<br>%</html>");
        add(jLabel9);
        jLabel9.setBounds(420, 110, 60, 30);

        jTextField6.setText("0");
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        add(jTextField6);
        jTextField6.setBounds(510, 70, 50, 30);

        jLabel10.setText("<html>Inspection<br>Cost</html>");
        add(jLabel10);
        jLabel10.setBounds(10, 110, 80, 30);

        jTextField7.setText("0");
        add(jTextField7);
        jTextField7.setBounds(90, 110, 50, 30);

        jLabel11.setText("<html>Local<br>Transport</html>");
        add(jLabel11);
        jLabel11.setBounds(150, 110, 60, 30);

        jTextField8.setText("0");
        add(jTextField8);
        jTextField8.setBounds(220, 110, 50, 30);

        jLabel12.setText("<html>Handling<br>charges</html>");
        add(jLabel12);
        jLabel12.setBounds(290, 110, 70, 30);

        jTextField9.setText("0");
        add(jTextField9);
        jTextField9.setBounds(360, 110, 50, 30);

        jLabel13.setText("<html>Fixturing/<br>Tooling Cost</html>");
        add(jLabel13);
        jLabel13.setBounds(420, 70, 90, 40);

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Other Expenses");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        add(jCheckBox1);
        jCheckBox1.setBounds(10, 40, 150, 23);

        jLabel14.setText("Total");
        add(jLabel14);
        jLabel14.setBounds(390, 150, 60, 30);

        jLabel1.setText("0");
        add(jLabel1);
        jLabel1.setBounds(450, 150, 110, 30);

        jLabel15.setText("<html>Basic<br>Rate in Rs</html>");
        add(jLabel15);
        jLabel15.setBounds(200, 10, 60, 30);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        labelVisibility();
        calculations();
    }//GEN-LAST:event_jCheckBox1ActionPerformed
    public void labelVisibility()
    {
        if(jCheckBox1.isSelected())
        {
            jTextField3.setVisible(true);
            jLabel9.setVisible(true);
            jLabel13.setVisible(true);
            jTextField6.setVisible(true);
            jTextField5.setVisible(true);
            jTextField5.setText(qty+"");
            jTextField5.setEditable(false);

            jLabel8.setVisible(true);
            jLabel12.setVisible(true);
            jTextField9.setVisible(true);
            jTextField4.setVisible(true);
            jTextField8.setVisible(true);
            jLabel11.setVisible(true);
            jLabel7.setVisible(true);
            jTextField1.setVisible(true);
            jLabel6.setVisible(true);
            jLabel10.setVisible(true);
            jTextField7.setVisible(true);
        }else{
//            jTextField3.setText("0.0");
            jTextField3.setVisible(false);
            jLabel9.setVisible(false);
            jLabel13.setVisible(false);
  //          jTextField6.setText("0.0");
 //           jTextField5.setText("0.0");
            jTextField6.setVisible(false);
            jTextField5.setVisible(false);
            jLabel8.setVisible(false);
            jLabel12.setVisible(false);
//            jTextField9.setText("0.0");
//            jTextField4.setText("0.0");
//            jTextField8.setText("0.0");
            jTextField9.setVisible(false);
            jTextField4.setVisible(false);
            jTextField8.setVisible(false);
            jLabel11.setVisible(false);
            jLabel7.setVisible(false);
//            jTextField1.setText("0.0");
//            jTextField7.setText("0.0");
            jTextField1.setVisible(false);
            jLabel6.setVisible(false);
            jLabel10.setVisible(false);
            jTextField7.setVisible(false);            
        }
    }
    FocusListener f2 = new FocusListener () {
       
        @Override
        public void focusGained ( FocusEvent e ) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
//            JTextField jcb = ( JTextField ) e.getSource ();    
//            jcb.selectAll ();
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            calculations();        
//            JTextField jcb = ( JTextField ) e.getSource ();    
//            String x = jcb.getText().trim ();
//            
//            if(x.equalsIgnoreCase ( "" )){
//                x = "0";
//            }
//
//            double rate  = Double.parseDouble( jTextField2.getText() ) ;
//            double percOH =  Double.parseDouble( jTextField3.getText() );
//            if( percOH  <=0  ){
//                 jcb.setText(x);
//            }else{
//                
//                if(  percOH >=0.0   ){
//                    double total = ( rate * qty )  ;
//                    double effrate = total + ( (  total  *  percOH)/100) ;
//                    jLabel1.setText( effrate+"" ) ;
//                }
//            }
         }
    };

    public void loadRejPercentage(int quantity)
    {
        ArrayList<RejectionDR> rejModel=CreateQuotationForm.rejModel;

        for(RejectionDR rej:rejModel)
        {
            if(rej.getFrom()<=quantity && quantity<=rej.getTo())
            {
                jTextField3.setText(rej.getRecoveryPercent()+"");
            }
        }
    }
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
            calculations();
        }
    };
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JTextField jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JTextField jTextField1;
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
