/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.quotation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.json.JSONArray;
import org.json.JSONObject;
import quotation.partmaster.purchasedPartNoDr;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class PartDetailsPanel extends javax.swing.JPanel {

    private double quantity ;
    
    private int index ;
    private boolean purchasedMaster=false;
    
    private ArrayList<String[]> processingDetails = null ;
    private ArrayList<String[]> rmDetails = null ;
    ArrayList<String[]> processes = null ;
    ArrayList<String[]> measunit = null ;
    ArrayList<String[]> rawmaterial = null ;
    ArrayList<String[]> rawMaterialDetail=null;
    
    public void setQuantity( String qty ){
        quantity = Double.parseDouble(qty)  ;
        jTextField1.setText( qty ) ;
        jTextField1.setVisible(false);
        jTextField1.addKeyListener(quantityKeyListner);
    }

    public boolean getPurchasedMaster()
    {
        return purchasedMaster;
    }
    public void setPurchasedMaster(boolean pa)
    {
        this.purchasedMaster=pa;
    }
    public String getPurchasedCategory()
    {
        return jComboBox3.getSelectedItem().toString().trim();
    }

    public void setPurchasedCategory(String catego)
    {
        jComboBox3.setSelectedItem(catego);
        loadSupplierCombo();        
    }

    public String getPurchasedSupplier()
    {
        return jComboBox2.getSelectedItem().toString().trim();
    }

    public void setPurchasedSupplier(String catego)
    {
        jComboBox2.setSelectedItem(catego);
    }


    public double getQuantity(  ){        
        return Double.parseDouble( jTextField1.getText() );
    }    
    
    /**
     * Creates new form PartDetailsPanel
     */
    public PartDetailsPanel () {
        initComponents ();
    }
    
    public PartDetailsPanel ( int srNo , ArrayList<String[]> processes, ArrayList<String[]> measunit , ArrayList<String[]> rawmateria) {
        
        initComponents ();
        index = srNo ;
        this.processes = processes ;
        this.measunit = measunit ;
    }
    

    KeyListener purchasedCostKey=new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
          //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyPressed(KeyEvent e) {
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyReleased(KeyEvent e) {
      //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            Double purchased=Double.parseDouble(jTextField2.getText());
//            Double quan=Double.parseDouble(jTextField2.getText());
//            Double total=quan*purchased;
//            jLabel4.setText(total+"");
              setCalculations();
        }
    };
    
    ActionListener action=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadSupplierCombo();
        }
    };
    
    ActionListener action24=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            purchasedCalculations();
            setCalculations();
        }
    };
    
    KeyListener quantityKeyListner=new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
         //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyPressed(KeyEvent e) {
          //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyReleased(KeyEvent e) {
            setCalculations();
//            Double quan=Double.parseDouble(jTextField1.getText());
//            Double prevTotal=Double.parseDouble(jLabel4.getText());
//            Double total=prevTotal*quan;
//            jLabel4.setText(total+"");
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    
    public PartDetailsPanel ( int srNo,  String desc, String drwNO,  String category, String revNO, String revDate,  String qty  , ArrayList<String[]> processes, ArrayList<String[]> measunit  ,  ArrayList<String[]> rawmateria  ) {
     
        initComponents ();
        jLabel1.setText(  desc )  ;
        jLabel5.setText(  drwNO )  ;
        jLabel9.setText(  revNO )  ;
        jLabel10.setText(  revDate )  ;
        jLabel6.setText(  category)  ;
        jTextField1.setText( qty  ) ;
        
        jTextField1.setVisible(false);
        jTextField1.addKeyListener(quantityKeyListner);

        this.processes = processes ;
        this.measunit = measunit ;
        
        this.rawmaterial = rawmateria ; 
        
        index = srNo ;
        
        if( category.equals( "Purchased" ) ){
            jButton1.setEnabled( false  ) ;
            jButton1.setVisible( false  ) ;
            jTextField2.setVisible( true );
            jTextField2.setEnabled( true );
            jTextField2.addKeyListener(purchasedCostKey);            
            jButton2.setEnabled( false  ) ;
            jButton2.setVisible( false  ) ;
            purchasedData();
        }else{
            jComboBox2.setVisible(false);
            jComboBox3.setVisible(false);
            jButton1.setEnabled( true  ) ;
            jTextField2.setVisible( false );
            jTextField2.setEnabled( false );
        }        
    }
    public PartDetailsPanel ( int srNo,  String desc, String drwNO,  String category, String revNO, String revDate,  String qty,String totalBomCost  , ArrayList<String[]> processes, ArrayList<String[]> measunit  ,  ArrayList<String[]> rawmateria ,String bomId,String[] rmData,String purchasedCost,String scope,String isMaster,String purchaseCate,String purchasedSupplier) {
        initComponents ();
        jLabel1.setText(  desc )  ;
        jLabel5.setText(  drwNO )  ;
        jLabel9.setText(  revNO )  ;
        jLabel10.setText(  revDate )  ;
        jLabel6.setText(  category)  ;
        jTextField1.setText( qty  ) ;
        jTextField1.setVisible(false);
        jLabel4.setText(totalBomCost);
        jTextField1.addKeyListener(quantityKeyListner);
        rawMaterialDetail= new ArrayList<String[]>();
        rawMaterialDetail.add(rmData);

        setProcessingData(bomId);

        this.processes = processes ;
        this.measunit = measunit ;        
        this.rawmaterial = rawmateria ; 
        
        index = srNo ;
        
        if( category.equals( "Purchased" ) ){
          
            jButton1.setEnabled( false  ) ;
            jButton1.setVisible( false  ) ;
            jTextField2.setText(purchasedCost);
            jTextField2.addKeyListener(purchasedCostKey);
            jButton2.setEnabled( false  ) ;
            jButton2.setVisible( false  ) ;
           
            if(isMaster.equals("y"))
            {
                purchasedData();        
               
                setPurchasedMaster(true);
                setPurchasedCategory(purchaseCate);
                setPurchasedSupplier(purchasedSupplier);
            }else{
                jComboBox2.setVisible(false);
                jComboBox3.setVisible(false);                            
                purchasedMaster=false;              
            }
            
        }else{
            jComboBox2.setVisible(false);
            jComboBox3.setVisible(false);            

            jButton1.setEnabled( true  ) ;
            jTextField2.setVisible( false );
            jTextField2.setEnabled( false );
        }        
        setScope(scope);
    }

    public void setProcessingData(String bomId) {
        String addEmpAPICall = "bomprocessdetails?ref_bom_id=" + bomId;

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
                JSONArray records = (JSONArray) st.getJSONArray("records");
                JSONObject emp = null;
                processingDetails = new ArrayList<>();
                for (int i = 0; i < records.length(); i++) {
                    emp = records.getJSONObject(i);
                    String[] data = new String[]{emp.get("process_name").toString(), emp.get("process_unit").toString(), emp.get("process_rate").toString(), emp.get("pro_loc").toString(), emp.get("process_eff_rate").toString(), emp.get("process_oh").toString(), emp.get("pro_total_cost").toString(), emp.get("process_id").toString(),emp.get("other_expences").toString(),emp.get("setup_time").toString(),emp.get("setup_cost").toString(),emp.get("fixturing_cost").toString(),emp.get("inspection_cost").toString(),emp.get("local_transport").toString(),emp.get("handling_cost").toString(),emp.get("handling_amt").toString(),emp.get("fixturing_amt").toString(),emp.get("setup_amt").toString(),emp.get("local_trans_amt").toString(),emp.get("rej_amt").toString(),emp.get("inspection_amt").toString()};
                    processingDetails.add(data);
                }
            }
        }
    }
    ArrayList<purchasedPartNoDr> array;

    public void purchasedData() {
        String prtNo = jLabel1.getText().trim();

        array = new ArrayList<purchasedPartNoDr>();
        List<String[]> a = new ArrayList<>();
        String addEmpAPICall = "purchaseparts?part_no=" + prtNo;
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

        if (result2 != null) {
            HashMap<String, Object> map2 = new HashMap<String, Object>();
            JSONObject jObject2 = new JSONObject(result2);
            Iterator<?> keys2 = jObject2.keys();

            while (keys2.hasNext()) {
                String key = (String) keys2.next();
                Object value = jObject2.get(key);
                map2.put(key, value);
            }
            JSONObject st1 = (JSONObject) map2.get("data");
            int totalRec = Integer.parseInt(st1.get("totalrecords").toString());

           if(totalRec!=0)
           {
               jComboBox2.setVisible(true);
               jComboBox3.setVisible(true);
               jComboBox2.addActionListener(action24);
               jComboBox3.addActionListener(action);
               purchasedMaster=true;

               JSONArray records1 = st1.getJSONArray("records");
               JSONObject assly = null;
               for (int i1 = 0; i1 < records1.length(); i1++) {
                   assly = records1.getJSONObject(i1);
                   String part_id = assly.get("part_id").toString();
                   String part_no = assly.get("part_no").toString();
                   String part_name = assly.get("part_name").toString();
                   String part_type = assly.get("part_type").toString();
                   String supplier = assly.get("supplier").toString();
                   String price = assly.get("price").toString();
                   String data[] = {part_type, supplier, price};

                   if(i1==0)
                   {
                       a.add(data);
                   }else{

                       for(int i4=0;i4<a.size();i4++)
                       {
                           if(!data[0].equals(a.get(i4)[0]))
                           {
                               a.add(data);
                           }
                       }
                   }
               }

               for (int i2 = 0; i2 < a.size(); i2++) {
                
                   try {
                       String addEmpAPICall1 = "purchaseparts?part_no=" + prtNo+"&part_type="+URLEncoder.encode( a.get(i2)[0],"UTF-8");
                       String result3 = WebAPITester.prepareWebCall(addEmpAPICall1, StaticValues.getHeader(), "");

                       if (result3 != null) {
                           HashMap<String, Object> map3 = new HashMap<String, Object>();
                           JSONObject jObject3 = new JSONObject(result3);
                           Iterator<?> keys3 = jObject3.keys();
                           
                           while (keys3.hasNext()) {
                               String key = (String) keys3.next();
                               Object value = jObject3.get(key);
                               map3.put(key, value);
                           }
                           JSONObject st2 = (JSONObject) map3.get("data");

                           JSONArray records3 = st2.getJSONArray("records");
                           
                           JSONObject assly1 = null;
                          
                           purchasedPartNoDr p=new purchasedPartNoDr();
                           
                           p.setPartType(a.get(i2)[0]);
                           
                           List<String[]> list=new ArrayList<String[]>();
                           
                           for (int i1 = 0; i1 < records3.length(); i1++) {
                               assly1 = records3.getJSONObject(i1);
                               String part_id = assly1.get("part_id").toString();
                               String part_no = assly1.get("part_no").toString();
                               String part_name = assly1.get("part_name").toString();
                               String part_type = assly1.get("part_type").toString();
                               String supplier = assly1.get("supplier").toString();
                               String price = assly1.get("price").toString();
                               String data[] = {supplier, price};
                               list.add(data);
                           }                           
                           p.setLst(list);
                           array.add(p);
                       }  
                   } catch (UnsupportedEncodingException ex) {
                    System.err.println(ex);
                }
            }
            loadComboBoxes();
        }else{
               jComboBox2.setVisible(false);
               jComboBox3.setVisible(false);
           }
        }
    }
    public void loadComboBoxes()
    {
        jComboBox3.removeAllItems(); 
        for(int j=0;j<array.size();j++)
        {
            String partType=array.get(j).getPartType();        
            jComboBox3.addItem(partType);
        }
        loadSupplierCombo();
    }
    
    
    
    public void loadSupplierCombo()
    {
//     try{   
        int index1=jComboBox3.getSelectedIndex();
         if(index1!=-1)
         {
            List<String[]> a=array.get(index1).getLst();
            jComboBox2.removeAllItems();
        
            for(int i=0;i<a.size(); i++)
            {
                jComboBox2.addItem(a.get(i)[0]);
            }
         }
//        purchasedCalculations();                   
//     }catch(Exception e){System.err.println(e);}
    }

    public void purchasedCalculations()
    {
        int index1=jComboBox3.getSelectedIndex();        
        int index2=jComboBox2.getSelectedIndex();        
        if(index2!=-1)
        {
            String total="";
            List<String[]> a=array.get(index1).getLst();
        
            total=a.get(index2)[1];
            jTextField2.setText(total);
        }
        
    }

    public void setProcessData(  ArrayList<String[]> processData   ) {
        processingDetails = processData ;
    }
    public void setScope(String scope)
    {
        jComboBox1.setSelectedItem(scope);
    }
    public String getScope()
    {
        return jComboBox1.getSelectedItem().toString();
    }
    public ArrayList<String[]> getProcessData(){
        return processingDetails ;
    }
    public String getPartName()
    {
        return jLabel1.getText();
    }
    public int getIndex(){
        return index ;
    }
    
    public String getPurchasedCost()
    {
        return jTextField2.getText();
    }
    public String getQuantity1(){
        return jTextField1.getText();
    }
    public String getTotal(){
        return jLabel4.getText();
    }
    public String getDrNo()
    {
        return jLabel5.getText();
    }
    public String getDrRevNo()
    {
        return jLabel9.getText();
    }
    public String getDrRevDate()
    {
        return jLabel10.getText();
    }

    public String getCategory()
    {
        return jLabel6.getText();
    }
    public ArrayList<String[]> getRMData(){
        return rawMaterialDetail;
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
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1100, 60));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Part Description");
        add(jLabel1);
        jLabel1.setBounds(10, 0, 160, 50);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Drawing No");
        add(jLabel5);
        jLabel5.setBounds(170, 0, 90, 50);

        jButton1.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jButton1.setText("Processing Cost");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(880, 10, 110, 40);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("<html>Purchase or Manufactured</html>");
        add(jLabel6);
        jLabel6.setBounds(490, 0, 110, 50);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Revision No");
        add(jLabel9);
        jLabel9.setBounds(260, 0, 60, 50);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Revision Date");
        add(jLabel10);
        jLabel10.setBounds(330, 0, 160, 50);

        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        add(jTextField1);
        jTextField1.setBounds(1010, 10, 70, 40);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("0.0");
        add(jLabel4);
        jLabel4.setBounds(1020, 10, 110, 40);

        jSeparator1.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));
        add(jSeparator1);
        jSeparator1.setBounds(0, 55, 1150, 2);

        jTextField2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        add(jTextField2);
        jTextField2.setBounds(910, 10, 80, 40);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jButton2.setText("RM Cost");
        jButton2.setOpaque(false);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(770, 10, 100, 40);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AEPL", "Customer"}));
        add(jComboBox1);
        jComboBox1.setBounds(600, 10, 70, 40);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        add(jComboBox2);
        jComboBox2.setBounds(790, 10, 80, 40);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(jComboBox3);
        jComboBox3.setBounds(680, 10, 80, 40);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
       /* CreateQuotationForm cqf = new CreateQuotationForm ();
        
        if( processingDetails.size()>0  )
            cqf.showDialog ( getQuantity () , processingDetails);
        else
            cqf.showDialog ( getQuantity () );*/

        if( processingDetails!=null &&  processingDetails.size()>0  )
            showDialog ( getQuantity () , processingDetails);
        else
            showDialog ( getQuantity() );
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
/*        CreateQuotationForm cqf = new CreateQuotationForm ();
            cqf.showRMDialog ();*/

            if( rmDetails!=null &&  rmDetails.size()>0  )
                showRMDialog ( getQuantity () , rmDetails);
            else
                showRMDialog ( getQuantity () );

            
    }//GEN-LAST:event_jButton2MouseClicked

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        
        purchasedCalculations();
    }//GEN-LAST:event_jComboBox2ActionPerformed
   ProcessMapDialog process1;
   RMCostDialog rmProcessDialog;
   public void dialogueCheack()
   {
        if(process1!=null)
        {
            if(process1.isVisible())
            {
                process1.setVisible(false);
            }
        }
        if(rmProcessDialog!=null)
        {
            if(rmProcessDialog.isVisible())
            {
                rmProcessDialog.setVisible(false);
            }
        }        
   }
   public void showDialog( double qty ){
        dialogueCheack();
        process1 = new ProcessMapDialog( qty ) ;
        process1.setVisible( true );        
    }
    
    public void showDialog( double qty , ArrayList<String[]> processData){
        dialogueCheack();
        process1= new ProcessMapDialog( qty , processData) ;
        process1.setVisible( true );        
    }

    public void showRMDialog(  double qty  ){
        dialogueCheack();
        rmProcessDialog = new RMCostDialog( qty ) ;
        rmProcessDialog.setVisible( true );        
    }
    
    public void showRMDialog(  double qty , ArrayList<String[]> rmData  ){
        dialogueCheack();
        rmProcessDialog = new RMCostDialog(   qty, rmData   ) ;
        rmProcessDialog.setVisible( true );        
    }
     ArrayList<ProcessDetailsPanel> processDetaile=null;
    
    class ProcessMapDialog extends JDialog{

        int totalPanels = 1 ;
        JPanel listPanel = null ;
        JScrollPane jScrollPane1 = null ;
        
        public ProcessMapDialog ( ) {
            
        }
        
        public ProcessMapDialog ( double qty ) {
            quantity=qty;
            processDetaile=new ArrayList<ProcessDetailsPanel> ();
            jButton1 = new javax.swing.JButton();
            jButton3 = new javax.swing.JButton();
            jButton4 = new javax.swing.JButton();

            setLayout(null);

            jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton1.setText("Submit Schedule & Close");
            jButton1.setOpaque(false);
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });
            add(jButton1);
            jButton1.setBounds(280, 420, 180, 32);
            jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton3.setText("Add New Row");
            jButton3.setOpaque(false);
            jButton3.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton3ActionPerformed(evt);
                }
            });
            add(jButton3);
            jButton3.setBounds(500, 7, 110, 32);
            jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton4.setText("Discard & Close");
            jButton4.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton4ActionPerformed(evt);
                }

                private void jButton4ActionPerformed(ActionEvent evt) {
                    dispose();
                }
            });
            add(jButton4);
            jButton4.setBounds(140, 420, 140, 32);
            
            setBounds ( 350, 200, 650, 500);
            setVisible ( true );
            setLayout ( null );
            setDefaultCloseOperation ( JDialog.DISPOSE_ON_CLOSE);
            setBackground( Color.WHITE);
            
            listPanel = new JPanel() ;
            listPanel.setBackground( Color.white  );
            listPanel.setBounds( 0,0, 650, 50 ) ;
            
            listPanel.setLayout ( new BoxLayout ( listPanel , BoxLayout.Y_AXIS ) );
            //listPanel.setLayout (null );

            jScrollPane1 = new JScrollPane( listPanel, 
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED   ) ;

            jScrollPane1.setBounds(  20, 70, 620, 350  ) ;
            add(jScrollPane1);
            
//            ProcessLabelsPanel labels = new ProcessLabelsPanel() ;
//            labels.setBounds( 0,40,600,26);
//            add(labels) ;
            
            if( qty >0.0 ) 
            {
                ProcessDetailsPanel p1 = new ProcessDetailsPanel( processes, measunit , qty ) ;
                p1.setBounds(  0 , 0 , 590,  300  ) ;
                p1.loadRejPercentage((int)qty);
                listPanel.add(p1) ;
                processDetaile.add ( p1 );
                revalidate ();
                repaint ();
            }else{
                JOptionPane.showMessageDialog( null, "Please enter non zero quantity" );
            }
            
        }
        
        public ProcessMapDialog ( double qty , ArrayList<String[]> processData) {
            quantity=qty;
            processDetaile=new ArrayList<ProcessDetailsPanel> ();
            jButton1 = new javax.swing.JButton();
            jButton3 = new javax.swing.JButton();
            jButton4 = new javax.swing.JButton();

            setLayout(null);

            jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton1.setText("Submit Schedule & Close");
            jButton1.setOpaque(false);
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });
            add(jButton1);
            jButton1.setBounds(280, 420, 180, 32);
            jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton3.setText("Add New Row");
            jButton3.setOpaque(false);
            jButton3.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton3ActionPerformed(evt);
                }
            });
            add(jButton3);
            jButton3.setBounds(500, 7, 110, 32);
            jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton4.setText("Discard & Close");
            jButton4.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton4ActionPerformed(evt);
                }

                private void jButton4ActionPerformed(ActionEvent evt) {
                    dispose();
                }
            });

            add(jButton4);
            jButton4.setBounds(140, 420, 140, 32);
            
            setBounds ( 350, 200, 650, 500);
            setVisible ( true );
            setLayout ( null );
            setDefaultCloseOperation ( JDialog.DISPOSE_ON_CLOSE);
            setBackground( Color.WHITE);
            
            listPanel = new JPanel() ;
            listPanel.setBackground( Color.white  );
            listPanel.setBounds( 0,0, 650, 50 ) ;
            
            listPanel.setLayout ( new BoxLayout ( listPanel , BoxLayout.Y_AXIS ) );
            //listPanel.setLayout (null );

            jScrollPane1 = new JScrollPane( listPanel, 
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED   ) ;

            jScrollPane1.setBounds(  20, 70, 620, 350  ) ;
            add(jScrollPane1);
            
//            ProcessLabelsPanel labels = new ProcessLabelsPanel() ;
//            labels.setBounds( 0,40,600,26);
//            add(labels) ;
            
            for ( int i = 0 ; i < processData.size() ; i ++ ) {
                
                ProcessDetailsPanel p1 = new ProcessDetailsPanel( processes, measunit , qty ) ;
                p1.setProcess ( 0, processData.get(i)[0] );
                p1.setUnit ( 0, processData.get(i)[1] );
                p1.setProcessCost ( processData.get(i)[2] );
                p1.setProcessLocation ( 0,  processData.get(i)[3]+""  );
                p1.setEffRate ( processData.get(i)[4] );
                p1.setProcessOH ( processData.get(i)[5] );
                p1.setTotalProcessCost ( processData.get(i)[6] );
                p1.setOtherExpence(processData.get(i)[8]);
                p1.setSetupTime(processData.get(i)[9]);
                p1.setSetupCost(processData.get(i)[10]);
                p1.setFixturingCost(processData.get(i)[11]);
                p1.setInspectionCost(processData.get(i)[12]);
                p1.setTransCost(processData.get(i)[13]);
                p1.setHandlingCost(processData.get(i)[14]);

                p1.setBounds(  0 , 0 , 590, 300  ) ;
                listPanel.add(p1) ;
                processDetaile.add ( p1 );
                revalidate ();
                repaint ();
            }
        }
        
        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
            // TODO add your handling code here:
            ProcessDetailsPanel p1 = new ProcessDetailsPanel(  processes, measunit , quantity ) ;
            p1.loadRejPercentage((int)quantity);
           
            p1.setBounds ( 0, (totalPanels)*50, 585, 300);
            listPanel.add(p1);
            processDetaile.add ( p1 );

            totalPanels++ ;
            
            revalidate ();
            repaint ();
            }                                        
  
        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
            // TODO add your handling code here:
            double totalProcessCost=0.0;
            StringBuilder sb = new StringBuilder() ;
            processingDetails = new ArrayList<String[]>() ;
            double totalCost1 = 0.0 ;
            for(ProcessDetailsPanel p:processDetaile)
            {
            //   sb.append(   p.getProcessID ()+"-"+p.getProcessName () +" : "  ) ;             //   sb.append(   p.getUnitID ()+"-"+p.getUnitName ()  +" : " ) ;             //   sb.append(   p.getProcessCost () +" : "  ) ;             //   sb.append(   p.getProcessLocationId ()+" - "+p.getProcessLocationName () +" : "   ) ;             ///   sb.append(   p.getEffRate ()+" : "   ) ;             //   sb.append(   p.getProcessOH ()+" : "   ) ;             //   sb.append(   p.getTotalProcessCost ()   ) ;                //totalProcessCost=totalProcessCost+p.getProcessCost ()+p.getOutsorceCost ()+p.getTextField3Cost ();
                String[] data = new String[]{  p.getProcessName (), p.getUnitName () ,  p.getProcessCost ()+"" , p.getProcessLocationName () ,  p.getEffRate ()+"" , p.getProcessOH ()+"", p.getTotalProcessCost(),p.getProcessID(),p.getOtherExpence(),p.getSetupTime(),p.getSetupCost(),p.getFixturingCost(),p.getInspectionCost(),p.getTransCost(),p.getHandlingCost(),p.getHandlingAmt()+"",p.getFixturingAmt()+"",p.getSetupCostAmt()+"",p.getLocalTransAmt()+"",p.getRejectionAmt()+"",p.getInspectionAmt()+""} ;
//                System.err.println("Other Expence "+ p.getOtherExpence());
                processingDetails.add( data ) ;                   
            }          
            setCalculations();

            //JOptionPane.showMessageDialog( null,  sb.toString ()   )  ;            
            dispose ();
        }
        
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton3;
        private javax.swing.JButton jButton4;
    }
    
    public void setCalculations()
    {
        double quan=0.0;
        try{
           quan= Double.parseDouble(jTextField1.getText());
        }catch(Exception ex){}
        double fTotal=0.0;

        if(jLabel6.getText().equals("Purchased"))
        {
            try{
                double purchasedCost=Double.parseDouble(jTextField2.getText());
             //   double Total=purchasedCost*quan;
                jLabel4.setText(purchasedCost +"" ) ;
            }catch(Exception ex){
                System.err.println(ex);
            }
        }else{
//            double totalCost1 = 0.0 ;
            if(processDetaile==null && rmProcessPanel==null)
            {
                fTotal=0.0;
            }else if(processDetaile==null && rmProcessPanel!=null){
                double totalCost=Double.parseDouble(rmProcessPanel.getTotal());
                fTotal=totalCost;//*quan;
            }else if(processDetaile!=null && rmProcessPanel==null){
                double totalCost1 = 0.0 ;
                for(ProcessDetailsPanel p:processDetaile)
                {
                    totalCost1 = totalCost1 + Double.parseDouble( p.getTotalProcessCost() );
                }                
                fTotal = totalCost1;//*quan ;
            }else if(processDetaile!=null && rmProcessPanel!=null){
                double totalCost1 = 0.0 ;
                for(ProcessDetailsPanel p:processDetaile)
                {
                    totalCost1 = totalCost1 + Double.parseDouble( p.getTotalProcessCost() );
                }
                double totalCost=Double.parseDouble(rmProcessPanel.getTotal());
                totalCost1 = totalCost1+totalCost ;
                fTotal=totalCost1;//*quan;

            }
            //    double prevCost = Double.parseDouble( jLabel4.getText() );
            jLabel4.setText(fTotal +"" );
        
                
        }
    }
      RMProcessPanel rmProcessPanel = null;
    class RMCostDialog extends JDialog{
        
      ArrayList<ProcessDetailsPanel> processDetaile=null;


        int totalPanels = 1 ;
        
        
        JScrollPane jScrollPane1 = null ;
        
        public RMCostDialog () {
//            rawMaterialDetail=new ArrayList<String[]>();

//            processDetaile=new ArrayList<ProcessDetailsPanel> ();
            jButton1 = new javax.swing.JButton();
            jButton3 = new javax.swing.JButton();
            jButton4 = new javax.swing.JButton();

            setLayout(null);

            jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton1.setText("Submit Schedule & Close");
            jButton1.setOpaque(false);
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });
            add(jButton1);
            jButton1.setBounds(340, 410, 180, 32);
            
            jButton3.setBounds(400, 7, 110, 32);
            jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton4.setText("Discard & Close");
            add(jButton4);
            jButton4.setBounds(200, 410, 140, 32);
            
            setBounds ( 300, 200, 710, 500);
            setVisible ( true );
            setLayout ( null );
            setDefaultCloseOperation ( JDialog.DISPOSE_ON_CLOSE);
            rmProcessPanel=new RMProcessPanel ();
            rmProcessPanel.setBounds ( 0, 0, 710, 400);
            
            add(  rmProcessPanel );
            
            revalidate ();
            repaint ();
            
        }
        
        public RMCostDialog ( double qty  ) {
//            rawMaterialDetail=new ArrayList<String[]>();
            jButton1 = new javax.swing.JButton();
            jButton3 = new javax.swing.JButton();
            jButton4 = new javax.swing.JButton();

            setLayout(null);

            jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton1.setText("Submit Schedule & Close");
            jButton1.setOpaque(false);
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });
            add(jButton1);
            jButton1.setBounds(340, 410, 180, 32);
            
            jButton3.setBounds(400, 7, 110, 32);
            jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton4.setText("Discard & Close");
                    jButton4.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton4ActionPerformed(evt);
                }

                private void jButton4ActionPerformed(ActionEvent evt) {
                    dispose();
                }
            });

            add(jButton4);
            jButton4.setBounds(200, 410, 140, 32);
            
            setBounds ( 300, 200, 710, 500);
            setVisible ( true );
            setLayout ( null );
            setDefaultCloseOperation ( JDialog.DISPOSE_ON_CLOSE);
            
            rmProcessPanel = new RMProcessPanel ( rawmaterial,  qty  ,rawMaterialDetail );
            rmProcessPanel.setBounds ( 0, 0, 710, 400);
            
            add(  rmProcessPanel );
            
            revalidate ();
            repaint ();
            
        }
        
        public RMCostDialog (  double qty,  ArrayList<String[]> rmData  ) {
//            rawMaterialDetail=new ArrayList<String[]>();
            jButton1 = new javax.swing.JButton();
            jButton3 = new javax.swing.JButton();
            jButton4 = new javax.swing.JButton();

            setLayout(null);

            jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton1.setText("Submit Schedule & Close");
            jButton1.setOpaque(false);
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });
            add(jButton1);
            jButton1.setBounds(340, 410, 180, 32);
            
            jButton3.setBounds(400, 7, 110, 32);
            jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton4.setText("Discard & Close");
                        jButton4.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton4ActionPerformed(evt);
                }

                private void jButton4ActionPerformed(ActionEvent evt) {
                    dispose();
                }
            });

            add(jButton4);
            jButton4.setBounds(200, 410, 140, 32);
            jButton4.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton4ActionPerformed(evt);
                }
                
            });
            setBounds ( 300, 200, 710, 500);
            setVisible ( true );
            setLayout ( null );
            setDefaultCloseOperation ( JDialog.DISPOSE_ON_CLOSE);
            
            rmProcessPanel = new RMProcessPanel ();
            rmProcessPanel.setBounds ( 0, 0, 710, 400);
            
            add(  rmProcessPanel );
            
            revalidate ();
            repaint ();
            
        }
        
        private void jButton4ActionPerformed(ActionEvent evt) {
            rmProcessPanel.removeAll();
        }
        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
         rawMaterialDetail=new ArrayList<>();
            // TODO add your handling code here:
            String[] data={rmProcessPanel.getRM_ID(),rmProcessPanel.getRM_NAME(),rmProcessPanel.getRM_RATE(),
                           rmProcessPanel.getRmUnit(),/*rmProcessPanel.getRM_CODE()*/"",rmProcessPanel.getRMType(),
                           rmProcessPanel.getRM_CTG(),rmProcessPanel.getShape(),rmProcessPanel.getLength(),
                           rmProcessPanel.getThickness(),rmProcessPanel.getDiameter(),rmProcessPanel.getWall(),
                           rmProcessPanel.getDensity(),rmProcessPanel.getWidth1(),rmProcessPanel.getIn_diameter(),
                           rmProcessPanel.getOut_diameter(),rmProcessPanel.getVolume(),rmProcessPanel.getWeight(),rmProcessPanel.getTransport(),
                           rmProcessPanel.getInspection(),rmProcessPanel.getRateFlu(),rmProcessPanel.getScrap(),
                           rmProcessPanel.getICC(),rmProcessPanel.getOther(),rmProcessPanel.getTaxes(),rmProcessPanel.getTotal(),rmProcessPanel.getRMTotal(),rmProcessPanel.getFCWeight(),
                           rmProcessPanel.getScStatus(),rmProcessPanel.getScRecovery(),rmProcessPanel.getScRecoveryPerc(),rmProcessPanel.getScWeight(),rmProcessPanel.getCsArea()
                           ,rmProcessPanel.getRMPackaging(),rmProcessPanel.getIccMonth(),rmProcessPanel.getTaxAmt()+"",
                           rmProcessPanel.getIccAmt()+"",rmProcessPanel.getFluctAmt()+""
            };
                    rawMaterialDetail.add(data);
//                Double prevTotal=Double.parseDouble(jLabel4.getText());
//                Double Total=Double.parseDouble(rmProcessPanel.getTotal())+prevTotal;
//                jLabel4.setText(Total+"");
                setCalculations();
                dispose();
        }
         
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton3;
        private javax.swing.JButton jButton4;
    }

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
