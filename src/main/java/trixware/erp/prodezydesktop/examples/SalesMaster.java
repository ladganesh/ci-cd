/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.awt.BorderLayout;
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
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import datechooser.view.WeekDaysStyle;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Derived.AutoCompletion;
import trixware.erp.prodezydesktop.Model.DeliveryScheduleModel;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.ProductOrderModel;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.HintTextField;
import trixware.erp.prodezydesktop.Utilities.MyCustomFilter;
import trixware.erp.prodezydesktop.components.ProductOrderDetailPanel;
import trixware.erp.prodezydesktop.components.ProgressDialog;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class SalesMaster extends javax.swing.JPanel {

    boolean flag_required_master_data_NA = false;
   // public static ArrayList<String> products;
    public ArrayList<String[]> productRates;
    public static ArrayList<String> customers;
    public static ArrayList<String[]> product_ids_qtys;
    public int previousOA = 0;
    int selectedCustomer = 0;
    double totalOrderValue = 0.0;
    ArrayList<String[]> customerCombo = null;
    ArrayList<ProductDR> products = null;
    ArrayList<ProductOrderModel> partDetails ;

    public static ProductOrderList productOrderListPanel ;

    public static  double grandTotal = 0.0 ;

    public static SalesMaster so = new SalesMaster();
    
        ArrayList<String[]> orderData = new ArrayList<String[]>();
    
    
    /**
     * Creates new form SalesForm
     */
    public SalesMaster () {
        try
        {
        initComponents ();

        AutoCompletion.enable ( jComboBox2 );
        
        jComboBox2.removeAllItems ();

                if ( jComboBox2.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            String getcustomerList = "customers";
            String customerString = WebAPITester.prepareWebCall ( getcustomerList , StaticValues.getHeader () , "" );
            
            
            if( !  customerString.contains( "not found"  )  ){
                    HashMap<String , Object> map = new HashMap<String , Object> ();
                    JSONObject jObject = new JSONObject ( customerString );
                    Iterator<?> keys = jObject.keys ();

                    while ( keys.hasNext () ) {
                        String key = ( String ) keys.next ();
                        Object value = jObject.get ( key );
                        map.put ( key , value );
                    }

                    JSONObject st = ( JSONObject ) map.get ( "data" );
                    JSONArray records = st.getJSONArray ( "records" );

                    JSONObject emp = null;
                    customerCombo = new ArrayList<String[]> ();
                    for ( int i = 0 ; i < records.length () ; i ++ ) {

                        emp = records.getJSONObject ( i );
                        jComboBox2.addItem ( emp.get ( "PLANT_CODE" ).toString() +" ( "+emp.get ("CUSTOMER_NAME" ).toString() +" )" );
                        customerCombo.add ( new String[] { emp.get ( "CUSTOMER_ID" ).toString () , emp.get ( "PLANT_CODE" ).toString() +" ( "+emp.get ("CUSTOMER_NAME" ).toString() +" )" } );
                    }
            }
        }

        productOrderListPanel = new ProductOrderList ();
        productOrderListPanel.setBounds ( 20 , 160 , 730 , 300 );
        add ( productOrderListPanel );        
                

        String getcustomerList = "salesorders";
        String salesOrderData = WebAPITester.prepareWebCall ( getcustomerList , StaticValues.getHeader () , "" );

        if( !  salesOrderData.contains( "not found"  )  ){
            jTable1.setModel ( buildTableModelfromJSON ( salesOrderData ) );
            jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
            jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
        }

        String addEmpAPICall = "customermaps";
        String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

        if ( result2 != null    &&  !result2.contains( "not found" )) {
             buildTableModelfromJSON2 ( result2 ) ;
        }

        jComboBox2.addActionListener ( ComboBoxAction );
        
        dateChooserCombo1.setFormat ( DateFormat.MEDIUM );
        dateChooserCombo1.setWeekStyle ( WeekDaysStyle.NORMAL );
        dateChooserCombo1.setOpaque ( false );
        dateChooserCombo1.setBackground ( Color.WHITE );
        dateChooserCombo1.revalidate ();
        dateChooserCombo1.repaint ();
        
        RMD_UID = loadDocumentsForRM (); 
        }catch(Exception e)
        {
            e.getMessage();
        }
    }
    
    public static  ProgressDialog progress = new ProgressDialog( null ) ;
    Thread getTableAndFilesContent = null ;
    public boolean showDialog(){
        getTableAndFilesContent = new Thread(){
            public void run(){
                addOrderDetails ();
            }
        };
        getTableAndFilesContent.start();
        progress.openProgressWindow ();
        return true ;
    }
    
    public static boolean hideDialog(){
        progress.closeProgressWindow ();
        return true ;
    }

    
    
    public void show_data_NA_message ( String data ) {

        JOptionPane.showMessageDialog ( null , data + " details not availabel in masters. This information is important for carriying out this activity. Consider updating the master first ! " , "Data Required " , 0 );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField5 = new HintTextField("Upload PO Copy");
        jLabel12 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jComboBox3 = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1190, 474));
        setLayout(null);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Product Code");
        add(jLabel2);
        jLabel2.setBounds(260, 10, 0, 0);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox1.setNextFocusableComponent(jTextField2);
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
        jComboBox1.setBounds(260, 30, 0, 0);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Customer");
        add(jLabel3);
        jLabel3.setBounds(20, 10, 130, 20);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox2.setNextFocusableComponent(jTextField1);
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });
        jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox2MouseClicked(evt);
            }
        });
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        add(jComboBox2);
        jComboBox2.setBounds(20, 30, 230, 33);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Number");
        add(jLabel4);
        jLabel4.setBounds(650, 10, 100, 20);

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setNextFocusableComponent(jTextField4);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(520, 30, 230, 33);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Upload PO");
        jButton1.setNextFocusableComponent(jComboBox1);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(520, 90, 110, 30);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Order Details");
        add(jLabel5);
        jLabel5.setBounds(260, 170, 0, 0);

        jTextField2.setText("1");
        jTextField2.setNextFocusableComponent(jButton2);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        add(jTextField2);
        jTextField2.setBounds(260, 120, 0, 0);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("OA Number");
        add(jLabel9);
        jLabel9.setBounds(20, 70, 230, 20);

        jTextField4.setEditable(false);
        jTextField4.setNextFocusableComponent(jTextField5);
        add(jTextField4);
        jTextField4.setBounds(20, 90, 100, 30);

        dateChooserCombo1.setCalendarPreferredSize(new java.awt.Dimension(400, 250));
        add(dateChooserCombo1);
        dateChooserCombo1.setBounds(130, 90, 120, 30);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setText("Order Date");
        add(jLabel10);
        jLabel10.setBounds(130, 70, 90, 20);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("Purchase Order");
        add(jLabel11);
        jLabel11.setBounds(520, 10, 210, 20);

        jButton6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton6.setText("Add Order");

        jButton6.setOpaque(false);
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        add(jButton6);
        jButton6.setBounds(770, 470, 100, 40);

        jButton8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton8.setText("Cancel");
        jButton8.setOpaque(false);
        add(jButton8);
        jButton8.setBounds(890, 470, 90, 40);

        jButton9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton9.setText("Export to Excel");
        jButton9.setOpaque(false);
        add(jButton9);
        jButton9.setBounds(990, 470, 110, 40);

        jTable1.setBackground(new java.awt.Color(255, 255, 255));
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
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(770, 20, 400, 440);

        jTextField5.setBackground(new java.awt.Color(255, 255, 255));
        jTextField5.setNextFocusableComponent(jButton1);
        jTextField5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField5MouseClicked(evt);
            }
        });
        add(jTextField5);
        jTextField5.setBounds(270, 90, 230, 30);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("Order Qty ");
        add(jLabel12);
        jLabel12.setBounds(260, 100, 0, 0);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(440, 120, 0, 0);

        jList1.setAutoscrolls(false);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jList1);

        add(jScrollPane3);
        jScrollPane3.setBounds(260, 190, 0, 0);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Total Order Value");
        add(jLabel1);
        jLabel1.setBounds(480, 500, 110, 16);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 18)); // NOI18N
        jLabel6.setText(totalOrderValue+"");
        add(jLabel6);
        jLabel6.setBounds(620, 490, 140, 36);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Reset ");
        jButton3.setOpaque(false);
        jButton3.setRequestFocusEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(990, 512, 110, 40);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("Parts ");
        add(jLabel7);
        jLabel7.setBounds(120, 140, 29, 16);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("Quantity");
        add(jLabel8);
        jLabel8.setBounds(336, 140, 60, 16);

        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel13.setText("EDD");
        add(jLabel13);
        jLabel13.setBounds(420, 140, 80, 16);

        jLabel14.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jLabel14.setText("<html>Rate for selected<br>customer</html>");
        add(jLabel14);
        jLabel14.setBounds(540, 132, 150, 30);

        jLabel15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel15.setText("Upload PO Copy");
        add(jLabel15);
        jLabel15.setBounds(270, 70, 88, 16);
        add(jSeparator1);
        jSeparator1.setBounds(10, 130, 750, 10);

        jLabel16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel16.setText("Add/Update");
        add(jLabel16);
        jLabel16.setBounds(660, 130, 70, 30);

        jLabel17.setText("jLabel17");
        add(jLabel17);
        jLabel17.setBounds(490, 470, 0, 0);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("View Orders");
        jButton4.setOpaque(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(770, 512, 100, 40);

        jButton5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton5.setText("Monthly Schedule");
        jButton5.setOpaque(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        add(jButton5);
        jButton5.setBounds(890, 512, 90, 40);

        jLabel18.setForeground(new java.awt.Color(204, 0, 0));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("*");
        add(jLabel18);
        jLabel18.setBounds(500, 10, 20, 20);

        jCheckBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox1.setText("Select Existing Orders");
        jCheckBox1.setOpaque(false);
        jCheckBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBox1MouseClicked(evt);
            }
        });
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        add(jCheckBox1);
        jCheckBox1.setBounds(265, 10, 210, 15);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jComboBox3);
        jComboBox3.setBounds(270, 30, 230, 33);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    File selectedFile = null ;
    String formatName = "";
    byte[] image = null ;
    byte[] fileContent = null;
    String encodedString = "";
    public int RMD_UID = 0; 
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    //    jTextField5.setText ( createFileNameResource ( "PartDrawingFile" ) );
    
    
    JFileChooser fileChooser = new JFileChooser (  );
        fileChooser.setFileFilter ( new MyCustomFilter ("*") );
        fileChooser.setCurrentDirectory ( new File ( System.getProperty ( "user.home" ) ) );
        int result = fileChooser.showOpenDialog ( this );
        if ( result == JFileChooser.APPROVE_OPTION ) {

            selectedFile = fileChooser.getSelectedFile ();
            jTextField5.setText ( selectedFile.getAbsolutePath () );
            
            
            FileInputStream fis;
            ByteArrayOutputStream bos;
            try {
                fis = new FileInputStream(selectedFile);
                //fis = new FileInputStream ( new File ( "temp_doc."+formatName ) );
                bos = new ByteArrayOutputStream ();
                byte[] buf = new byte[ 1024 ];

                for ( int readNum ; ( readNum = fis.read ( buf ) ) != -1 ; ) {
                    bos.write ( buf , 0 , readNum );
                }

                formatName = selectedFile.getAbsolutePath ().substring ( selectedFile.getAbsolutePath ().lastIndexOf ( "." ) + 1 );
                image = bos.toByteArray ();
                
                byte[] encoded = Base64.encodeBase64 (image);
                fileContent = Base64.encodeBase64 (image);
                encodedString = new String(encoded);
               
                
            
                String tempFileName = "TestConvertFile."+formatName ;
                File downloaded = new File( tempFileName  ) ;
                encoded = Base64.decodeBase64 (encodedString );
                FileUtils.writeByteArrayToFile(      downloaded   , encoded ) ;
                
                
            } catch ( FileNotFoundException ex ) {
                System.out.println ( "Cannot process file "+ex.getMessage() );
            } catch ( IOException ex ) {
                System.out.println ( "Cannot process file "+ex.getMessage() );
            }

        }
    
    
    
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField5MouseClicked
        // TODO add your handling code here:
        openFile ( jTextField5.getText () );
    }//GEN-LAST:event_jTextField5MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        if (jCheckBox1.isSelected()) 
        {
            showDialog();
        } else 
        {
            String order_no = jTextField1.getText();
            if (!order_no.equals("")) {
                showDialog();
            } else {
                JOptionPane.showMessageDialog(null, "Please fill * field Mandatory");
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    public void addOrderDetails() {

        ArrayList<ProductOrderModel> product_ids_qtys = productOrderListPanel.getRejection();

       // ProductOrderModel  tlm = null ;
        char customer_id = customerCombo.get(jComboBox2.getSelectedIndex())[0].charAt(0);
        String po_date = dateChooserCombo1.getText();
        String po_copy_name = purchaseOrderFile_name;

        double orderVal = 0.0;

        for (int i = 0; i < product_ids_qtys.size(); i++) {
            orderVal = orderVal + (Double.parseDouble(product_ids_qtys.get(i).getPartQty()) * Double.parseDouble(product_ids_qtys.get(i).getRate()));
        }

        orderVal = Double.parseDouble(new DecimalFormat("#.##").format(orderVal));

        //   add conditions to check if all fields are filled and nothing is left blank by the user
        
      //******** start code added by harshali on 29-Jun-2019 update schedule of existing order 
        if (jCheckBox1.isSelected()) 
        {
            if (product_ids_qtys.size() > 0) 
            {
                try {

                    String addEmpAPICall = "salesorderedit?sales_po_id=" + URLEncoder.encode(orderData.get(jComboBox3.getSelectedIndex())[0], "UTF-8");
                    String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                    if (result2 != null && !result2.contains("not found")) {
                        Double available = 0.0, total_qty;
                        int sales_id = 0;
                        JSONObject jsobje = new JSONObject(result2);
                        JSONArray record = jsobje.getJSONArray("data");
                       // JSONArray record = new JSONArray(page);
                        JSONObject emp = null;
                        for (int i = 0; i < record.length(); i++) 
                        {
                            emp = record.getJSONObject(i);
                            sales_id = emp.getInt("sales_po_id");
                            available = emp.getDouble("order_qty");

                        }
                        total_qty = available + orderVal;
                        addEmpAPICall = "salesorderupdate?sales_po_id=" + URLEncoder.encode(sales_id + "", "UTF-8")
                                + "&so_customer_id=" + URLEncoder.encode(customer_id + "", "UTF-8")
                                + "&product_ids=" + URLEncoder.encode("", "UTF-8")
                                + "&po_date=" + URLEncoder.encode(new SimpleDateFormat("yyyy-MM-dd").format(dateChooserCombo1.getSelectedDate().getTime()) + " 00:00:00", "UTF-8")
                                + "&po_copy_name=" + URLEncoder.encode(po_copy_name, "UTF-8")
                                + "&order_qty=" + URLEncoder.encode(total_qty + "", "UTF-8")
                                + "&order_no=" + URLEncoder.encode(orderData.get(jComboBox3.getSelectedIndex())[1], "UTF-8")
                                + "&order_ack=" + URLEncoder.encode(previousOA + "", "UTF-8");
                        result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                        JOptionPane.showMessageDialog(null, result2);

                        if (result2.contains("updated successfully")) 
                        {
                            for (int j = 0; j < product_ids_qtys.size(); j++) 
                            {
                                addEmpAPICall = "podetails?ref_po_id=" + sales_id + "&fg_id=" + product_ids_qtys.get(j).getPart_id();
                                result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
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

            
                                int product_qty = 0, tot_product_qty, po_order_id = 0;
                                for (int i = 0; i < records.length(); i++) {

                                    JSONObject jsonobject = records.getJSONObject(i);
                                    product_qty = jsonobject.getInt("product_qty");
                                    po_order_id = jsonobject.getInt("oder_detail_id");

                                }
                                tot_product_qty = product_qty + Integer.parseInt(product_ids_qtys.get(j).getPartQty());

                                String addPO = "podetailupdate?oder_detail_id=" + URLEncoder.encode(po_order_id + "", "UTF-8")
                                        + "product_id=" + URLEncoder.encode(product_ids_qtys.get(j).getPart_id() + "", "UTF-8")
                                        + "&product_qty=" + URLEncoder.encode(tot_product_qty + "", "UTF-8")
                                        + "&finished_qty=" + URLEncoder.encode("0", "UTF-8")
                                        + "&ref_po_id=" + URLEncoder.encode("" + sales_id, "UTF-8")
                                        + "&eff_rate=" + URLEncoder.encode(product_ids_qtys.get(j).getRate() + "", "UTF-8");
                                String addPOResult = WebAPITester.prepareWebCall(addPO, StaticValues.getHeader(), "");
                                JOptionPane.showMessageDialog(null, addPOResult);
                                
                                ArrayList<DeliveryScheduleModel> schedule = product_ids_qtys.get(j).getOrderScheduleForItem(0);

                            String addSchedule = "";
                           
                           

                            if (schedule != null) 
                            {
                                for (int k = 0; k < schedule.size(); k++) 
                                {

                                    addSchedule = "deliveryschedule_add?sales_po_id=" + sales_id + "&ref_po_det_id=" + po_order_id
                                            + "&delry_qty=" + schedule.get(k).getQty() + "&dlry_date=" + URLEncoder.encode(schedule.get(k).getDate() + "", "UTF-8")
                                            + "&delry_status=" + (0 + "");

                                    addPOResult = WebAPITester.prepareWebCall(addSchedule, StaticValues.getHeader(), "");
                                    
                                        JOptionPane.showMessageDialog(null, addPOResult);
                                }
                            }
                            }
                            
                            
                              try {

                            String fileName = selectedFile.getAbsoluteFile().getName();

                            if (encodedString != null && !encodedString.equals("") && selectedFile != null && !formatName.equals("") && sales_id != 0) {
                                String docAddCallResult = WebAPITester.prepareWebCall3("refdocadd", StaticValues.getHeader(), "&RD_NAME=" + URLEncoder.encode(fileName, "UTF-8") + "&RDM_UID=" + RMD_UID + "&ext=" + URLEncoder.encode(formatName, "UTF-8") + "&item_id=" + sales_id + "&binarydata=" + URLEncoder.encode(encodedString, "UTF-8"));
                            }
                        } catch (UnsupportedEncodingException ex) {

                        } catch (Exception e) {
                        }

                       

                        remove(productOrderListPanel);
                        productOrderListPanel = new ProductOrderList();
                        productOrderListPanel.setBounds(20, 160, 730, 300);
                        add(productOrderListPanel);

                        jLabel6.setText("0.0");

                        hideDialog();

                            
                            

                        }

                    }

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } else {

                JOptionPane.showMessageDialog(null, "Please add some orders with non zero order quantity");
            }
        }
        else {
    // ******** End code added by harshali on 29-Jun-2019 update schedule of existing order 
            String order_no = jTextField1.getText();
            if (!order_no.equals("")) {

                if (product_ids_qtys.size() > 0) {
                    try {

                        String addEmpAPICall = "salesorderadd?so_customer_id=" + URLEncoder.encode(customer_id + "", "UTF-8")
                                + "&product_ids=" + URLEncoder.encode("", "UTF-8")
                                + "&po_date=" + URLEncoder.encode(new SimpleDateFormat("yyyy-MM-dd").format(dateChooserCombo1.getSelectedDate().getTime()) + " 00:00:00", "UTF-8")
                                + "&po_copy_name=" + URLEncoder.encode(po_copy_name, "UTF-8")
                                + "&order_qty=" + URLEncoder.encode(orderVal + "", "UTF-8")
                                + "&order_no=" + URLEncoder.encode(order_no, "UTF-8")
                                + "&order_ack=" + URLEncoder.encode(previousOA + "", "UTF-8");
                        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                        JOptionPane.showMessageDialog(null, msgFromJSON(result2));

                        int recordId = iDFromJSON(result2);

                        //                 ""+ tlm.getPart_id () +"  "+tlm.getPartName_Code ()+"  "+tlm.getPartQty ()+"  "+tlm.getRate ()+"   "+tlm.getEdd ()+"  "+tlm.getUnit () ) 
                        String addPO = "";
                        String addPOResult = "";
                        String addScheduleResult = "";

                        int j = 0;
                        int k = 0;

                        for (; j < product_ids_qtys.size(); j++) 
                        {

                            addPO = "podetailadd?product_id=" + URLEncoder.encode(product_ids_qtys.get(j).getPart_id() + "", "UTF-8")
                                    + "&product_qty=" + URLEncoder.encode(product_ids_qtys.get(j).getPartQty(), "UTF-8")
                                    + "&finished_qty=" + URLEncoder.encode("0", "UTF-8")
                                    + "&ref_po_id=" + URLEncoder.encode(recordId + "", "UTF-8")
                                    + "&eff_rate=" + URLEncoder.encode(product_ids_qtys.get(j).getRate() + "", "UTF-8");
                            addPOResult = WebAPITester.prepareWebCall(addPO, StaticValues.getHeader(), "");

                            int recordId2 = iDFromJSON(addPOResult);

                            ArrayList<DeliveryScheduleModel> schedule = product_ids_qtys.get(j).getOrderScheduleForItem(0);

                            String addSchedule = "";
                            addScheduleResult = "";
                            k = 0;

                            if (schedule != null) 
                            {
                                for (; k < schedule.size(); k++) 
                                {

                                    addSchedule = "deliveryschedule_add?sales_po_id=" + recordId + "&ref_po_det_id=" + recordId2
                                            + "&delry_qty=" + schedule.get(k).getQty() + "&dlry_date=" + URLEncoder.encode(schedule.get(k).getDate() + "", "UTF-8")
                                            + "&delry_status=" + (0 + "");

                                    addScheduleResult = WebAPITester.prepareWebCall(addSchedule, StaticValues.getHeader(), "");

                                }
                            }

                        }

                        try {

                            String fileName = selectedFile.getAbsoluteFile().getName();

                            if (encodedString != null && !encodedString.equals("") && selectedFile != null && !formatName.equals("") && recordId != 0) {
                                String docAddCallResult = WebAPITester.prepareWebCall3("refdocadd", StaticValues.getHeader(), "&RD_NAME=" + URLEncoder.encode(fileName, "UTF-8") + "&RDM_UID=" + RMD_UID + "&ext=" + URLEncoder.encode(formatName, "UTF-8") + "&item_id=" + recordId + "&binarydata=" + URLEncoder.encode(encodedString, "UTF-8"));
                            }
                        } catch (UnsupportedEncodingException ex) {

                        } catch (Exception e) {
                        }

                        if (!addPOResult.equals("")) {
                            JOptionPane.showMessageDialog(null, j + " new " + msgFromJSON(addPOResult));
                        }

                        if (!addScheduleResult.equals("")) {
                            JOptionPane.showMessageDialog(null, k + " new " + msgFromJSON(addPOResult));
                        }

                        remove(productOrderListPanel);
                        productOrderListPanel = new ProductOrderList();
                        productOrderListPanel.setBounds(20, 160, 730, 300);
                        add(productOrderListPanel);

                        jLabel6.setText("0.0");

                        hideDialog();

                    } catch (UnsupportedEncodingException e) {

                    } catch (Exception e) {
                        hideDialog();
                    }
                } else {

                    JOptionPane.showMessageDialog(null, "Please add some orders with non zero order quantity");
                }

            } else {

                JOptionPane.showMessageDialog(null, "Please fill the order number field with valid data");
            }

        }

    }
    
    
    public int loadDocumentsForRM(  ){
        
        String addEmpAPICall = "rdm";
        String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
        
        if (  ! result2.contains ( "not found" ) ) {
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
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );
                if ( emp.getString ( "RDM_NAME" ).equals( "Sales Orders" ) ) {
                    return Integer.parseInt( emp.get ( "RDM_UID" ).toString () );
                }
            }
        }
        
        return 0;
    }
    
    
    public static double updateGrandTotal(    ){
        ArrayList<ProductOrderModel> product_ids_qtys1 = productOrderListPanel.getRejection () ;
        grandTotal = 0.0 ;
        if(  product_ids_qtys1.size () == 0 ) {
            grandTotal = 0.0 ;
        } else{
            for ( int i=0;    i < product_ids_qtys1.size () ; i ++ ) {
                grandTotal = grandTotal + (  Double.parseDouble( product_ids_qtys1.get( i ).getPartQty() )*  Double.parseDouble( product_ids_qtys1.get(i).getRate () ) );
            }
        }
        return grandTotal ;
    }

    public String msgFromJSON(  String json){
        
   
        HashMap<String, Object> map = new HashMap<String , Object> ();
        JSONObject jObject = new JSONObject ( json );
        Iterator<?> keys = jObject.keys ();

            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }

           JSONObject st = ( JSONObject ) map.get ( "message" );
           st = ( JSONObject ) st.get ( "success" );
              
        return st.get ("104" ).toString();
    }
    
    public DefaultTableModel buildTableModelfromJSON ( String employeeJSONList ) {

        Vector<String> columnNames = new Vector<String> ();
        Vector<String> columnNames2 = new Vector<String> ();
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();

        HashMap<String , Object> map = new HashMap<String , Object> ();
        JSONObject jObject = new JSONObject ( employeeJSONList );
        Iterator<?> keys = jObject.keys ();

        while ( keys.hasNext () ) {
            String key = ( String ) keys.next ();
            Object value = jObject.get ( key );
            map.put ( key , value );
        }

        JSONObject st = ( JSONObject ) map.get ( "data" );
        JSONArray records = st.getJSONArray ( "records" );

        JSONObject emp = null;

        columnNames = new Vector<String> ();
        columnNames.add ( "sales_po_id" );
        //columnNames.add ( "so_customer_id" );
        columnNames.add ( "CUSTOMER_NAME" );
        columnNames.add ( "order_qty" );
        columnNames.add ( "order_no" );
        columnNames.add ( "po_date" );
        
        columnNames2 = new Vector<String> ();
        columnNames2.add ( "PO ID" );
        columnNames2.add ( "Customer" );
        columnNames2.add ( "Order Value" );
        columnNames2.add ( "Order No" );
        columnNames2.add ( "Order Date" );
        
        orderData = new ArrayList<String[]>();
        jComboBox3.removeAllItems(  );
        
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
            boolean fgmatched = false;

            Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {
                emp = records.getJSONObject ( i );
                vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
             }
            data.add ( vector );
            
            orderData.add(new String[]{emp.get("sales_po_id").toString(), emp.get("order_no").toString(), emp.get("order_qty").toString()});
            jComboBox3.addItem(emp.get("order_no").toString());
            
        }
        return new DefaultTableModel ( data , columnNames2 );
    }    
    
    public void buildTableModelfromJSON2 ( String employeeJSONList ) {

        Vector<String> columnNames = new Vector<String> ();
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();

        HashMap<String , Object> map = new HashMap<String , Object> ();
        JSONObject jObject = new JSONObject ( employeeJSONList );
        Iterator<?> keys = jObject.keys ();

        while ( keys.hasNext () ) {
            String key = ( String ) keys.next ();
            Object value = jObject.get ( key );
            map.put ( key , value );
        }

        JSONObject st = ( JSONObject ) map.get ( "data" );
        JSONArray records = st.getJSONArray ( "records" );

        JSONObject emp = null;

        columnNames = new Vector<String> ();
        columnNames.add ( "M_FG_ID" );
        columnNames.add ( "M_CUST_ID" );
        columnNames.add ( "SALES_RATE" );
    
        productRates = new ArrayList<String[]> ();
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
            emp = records.getJSONObject ( i );
            productRates.add ( new String[] { emp.get ( "M_FG_ID" ).toString() ,emp.get ( "M_CUST_ID" ).toString() , emp.get ( "SALES_RATE" ).toString()  } );
       //     System.out.println ( ""+emp.get ( "M_FG_ID" ).toString() +"       "+emp.get ( "M_CUST_ID" ).toString() +"       "+ emp.get ( "SALES_RATE" ).toString()  );
        }
        
        
    }
    
    public void loadContent () {

         String addEmpAPICall = "salesorders";
            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

            if ( result2 != null && ! result2.contains( "not found" )) {
                jTable1.setModel ( buildTableModelfromJSON ( result2 ) );
                jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
            }
       
    }

    public static DefaultTableModel buildTableModel ( ResultSet rs )
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

    class OrderDetails {

        public String O_ID;
        public String productName;
        public String qty;
        public double totalVal = 0.0;
        public double rate ;
    }

    public int iDFromJSON( String json){
        int id = 0;
       
        JSONObject jObject = new JSONObject ( json );
        Iterator<?> keys = jObject.keys ();
    
        JSONObject value = (JSONObject) jObject.get ( "data" );
            
        id = Integer.parseInt(value.get ("insert_id" ).toString());
        //    id = Integer.parseInt( st.getString ("insert_id" ));
        
        return id ;
    }
    
    
    ArrayList<OrderDetails> orderDetails = new ArrayList<OrderDetails> ();
    DefaultListModel orderDetailsList = new DefaultListModel ();
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        //    jList1.removeAll();
        String orderQty = jTextField2.getText ();
        
        String selectedProduct = products.get( jComboBox1.getSelectedIndex () ).FG_ID +"" ;
        
        
        selectedCustomer = Integer.parseInt( customerCombo.get( jComboBox2.getSelectedIndex ())[0] );

        double rate  = 0.0 ;

        
        for ( int i = 0 ; i < productRates.size () ; i ++ ) {

                if ( selectedProduct.equals ( productRates.get ( i )[ 0 ] ) && selectedCustomer == Integer.parseInt( productRates.get ( i )[1 ]  ) ) {
                    //totalOrderValue = totalOrderValue + ( Double.parseDouble ( productRates.get ( i )[ 2 ] ) * Double.parseDouble ( temp.qty ) );
                    rate = Double.parseDouble ( productRates.get ( i )[ 2 ] );
                }
        }
        
        JOptionPane.showMessageDialog( null, ""+selectedProductIndex );
        
        if( rate != 0.0 && !orderQty.equals ( "")){
        
                System.out.println ( "Selected product index "+ selectedProductIndex);

                if ( selectedProductIndex >=0  ) {

                    String tempQty = orderDetails.get ( selectedProductIndex ).qty;
                    orderDetails.remove ( selectedProductIndex );
                    orderDetailsList.removeElementAt ( selectedProductIndex );

                    OrderDetails temp = new OrderDetails ();
                    temp.O_ID =   selectedProduct ;
                    temp.productName = products.get( jComboBox1.getSelectedIndex () ).FG_PART_NO;
                    temp.qty = String.valueOf ( Integer.parseInt ( orderQty ) + Integer.parseInt ( tempQty ) );
                    temp.rate = rate ;
                    totalOrderValue =   ( rate * Double.parseDouble ( temp.qty ) );

                    jLabel6.setText ( totalOrderValue + "" );

                    orderDetails.add ( temp );
                    orderDetailsList.addElement ( temp.productName + "  Qty :  " + temp.qty );
                
                } else {


                    OrderDetails temp = new OrderDetails ();
                
                    temp.O_ID =   selectedProduct ;
                    temp.productName = products.get( jComboBox1.getSelectedIndex () ).FG_PART_NO;
                    temp.qty = orderQty;
                    
                    for ( int i = 0 ; i < productRates.size () ; i ++ ) {

                        if ( selectedProduct.equals ( productRates.get ( i )[ 0 ] ) && selectedCustomer == Integer.parseInt( productRates.get ( i )[1 ]  ) ) {
                            totalOrderValue = totalOrderValue + ( rate * Double.parseDouble ( temp.qty ) );
                            temp.rate = rate;
                        }
                    }
                    orderDetails.add ( temp );
                    //orderDetailsList.addElement( selectedProduct + " - Qty" + orderQty ); 
                    orderDetailsList.addElement ( temp.productName + "  Qty :  " + temp.qty );

                    
                    
                    jLabel6.setText ( totalOrderValue + "" );
                
                }

                jList1.setModel ( orderDetailsList );

               if ( orderDetails.size () >= 1) {
                    int _selectedProduct =  products.get(  jComboBox1.getSelectedIndex() ).FG_ID;
                    for ( int count = 0 ; count < orderDetails.size () ; count ++ ) {
                        if ( _selectedProduct == Integer.parseInt( orderDetails.get ( count ).O_ID ) )  {
                            selectedProductIndex = count;
                        }
                    }
                }
        }else{
        
            if( rate == 0.0  )
                JOptionPane.showMessageDialog(  null, "<html>Selling rate for selected product and customer is not defined<br>Please create an entry in product customer map."    );
        
            if( orderQty.equals ( "") )
                JOptionPane.showMessageDialog(  null, "<html>Please enter valid order quantity</html>"    );
            
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    

    
    int selectedProductIndex = -1;
    int newProductIndex = -1;

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        jButton2.setEnabled ( true );
        if (  orderDetails.size () > 0) {
            int _selectedProduct =  products.get(  jComboBox1.getSelectedIndex() ).FG_ID;
            for ( int count = 0 ; count < orderDetails.size () ; count ++ ) {
                if ( _selectedProduct ==  Integer.parseInt( orderDetails.get ( count ).O_ID ) )  {
                    selectedProductIndex = count;
                }else{
                    selectedProductIndex = -1 ;
                }
            }
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:

        int choice =    JOptionPane.showConfirmDialog ( null, "Are you sure you want to remove the item from list ?", "Confirm Remove Action", JOptionPane.YES_NO_OPTION);
        
        // selectedCustomer = Integer.parseInt( customerCombo.get( jComboBox2.getSelectedIndex ())[0] );
        
        if( choice ==JOptionPane.YES_OPTION ){
        
            if (  ! ( orderDetailsList.getSize () < 1 ) ) {

                int selection = jList1.getSelectedIndex ();

                for ( int i = 0 ; i < productRates.size () ; i ++ ) {

                    if ( orderDetails.get(selection).O_ID.equals ( productRates.get ( i )[ 0 ] ) && selectedCustomer == Integer.parseInt( productRates.get ( i )[1 ] ) ){
                        totalOrderValue = totalOrderValue - ( Double.parseDouble ( productRates.get ( i )[ 2 ] ) * Double.parseDouble ( orderDetails.get(selection).qty ) );
                        i=productRates.size () ;
                    }
                }

                jLabel6.setText ( totalOrderValue + "" );
                orderDetails.remove ( selection );
                orderDetailsList.removeElementAt ( selection );
                jList1.setModel ( orderDetailsList );

            }
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox2MouseClicked

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        if ( jComboBox1.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add product from Finished Good Master " );
        }             // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:

        jTextField1.setText("");
        jTextField2.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        
     //   jComboBox1.setSelectedIndex ( 0);
        
     /*   orderDetailsList.removeAllElements ();
        while( orderDetails.size()>0){
            orderDetails.remove (orderDetails.size()-1 ) ;
        }
        totalOrderValue = 0.0 ;
        jLabel6.setText ( "0.0");
        selectedProductIndex = -1 ;  */
    }//GEN-LAST:event_jComboBox2ActionPerformed

    ActionListener ComboBoxAction = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
            if ( jComboBox2.getItemCount () == 0 ) {
                JOptionPane.showMessageDialog ( null , "<html><size='06'>First add Customer from Customer Master " );
            } else {

                remove( productOrderListPanel  )  ;
                productOrderListPanel = new ProductOrderList ();
                productOrderListPanel.setBounds ( 20 , 160 , 730 , 300 );
                add ( productOrderListPanel );  
                
            }
        }
    };

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        
        jComboBox1.setSelectedIndex ( 0);
        jComboBox2.setSelectedIndex ( 0);
        orderDetailsList.removeAllElements ();
        while( orderDetails.size()>0){
            orderDetails.remove (orderDetails.size()-1 ) ;
        }
        totalOrderValue = 0.0 ;
        jLabel6.setText ( "0.0");
        selectedProductIndex = -1 ;
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTable1MouseClicked

    
    
    
    DeliveryScheduleReportDialog reportDiag = null ;
    
    Thread fullDetails = null ;
    
    public boolean showFullDeliveryScheduleReport(){
        fullDetails = new Thread(){
            public void run(){
                reportDiag = new DeliveryScheduleReportDialog ( HomeScreen.home , "FullDetails" ); //new DeliveryScheduleReportDialog( HomeScreen.home)  ;
                reportDiag.setVisible ( true);
            }
        };
        fullDetails.start(  );
        progress.openProgressWindow ();
        progress.updateTitle ( "Showing Full Delivery Schedue Report" );
        progress.updateMessage ( "Getting Data" );
        return true ;        
    }
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        showFullDeliveryScheduleReport() ;
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        reportDiag = new DeliveryScheduleReportDialog ( HomeScreen.home , "monthly" ); //new DeliveryScheduleReportDialog( HomeScreen.home)  ;
        reportDiag.setVisible ( true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jCheckBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBox1MouseClicked
        // TODO add your handling code here:
                if (jCheckBox1.isSelected()) 
        {
            jComboBox3.setEnabled(true);
            jTextField1.setEnabled(false);
            jLabel18.setVisible(false);

            remove(productOrderListPanel);
            productOrderListPanel = new ProductOrderList();
            productOrderListPanel.setBounds(20, 160, 730, 300);
            add(productOrderListPanel);

        } else {
       
            jComboBox3.setEnabled(false);
            jTextField1.setEnabled(true);
            jLabel18.setVisible(true);
            remove(productOrderListPanel);
            productOrderListPanel = new ProductOrderList();
            productOrderListPanel.setBounds(20, 160, 730, 300);
            add(productOrderListPanel);
        }
    }//GEN-LAST:event_jCheckBox1MouseClicked

    public String createFileNameResource ( String columnDocumentName ) {

        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
        String strDate2 = sdf2.format ( c2.getTime () );

        FileChannel inputChannel = null;
        FileChannel outputChannel = null;

        JFileChooser fileChooser = new JFileChooser ();
        fileChooser.setFileFilter ( new MyCustomFilter ( "pdf" ) );
        fileChooser.setCurrentDirectory ( new File ( System.getProperty ( "user.home" ) ) );
        int result = fileChooser.showOpenDialog ( this );
        if ( result == JFileChooser.APPROVE_OPTION ) {
            File selectedFile = fileChooser.getSelectedFile ();

            File dir = new File ( "UploadedFiles\\SalesOrders" );
            dir.mkdir ();

            purchaseOrderFile_name = columnDocumentName + "_(" + selectedFile.getName () + ") _saved_on" + strDate2 + selectedFile.getName ().substring ( selectedFile.getName ().lastIndexOf ( '.' ) );

            try {
                inputChannel = new FileInputStream ( selectedFile ).getChannel ();
                outputChannel = new FileOutputStream ( new File ( dir , purchaseOrderFile_name ) ).getChannel ();
                outputChannel.transferFrom ( inputChannel , 0 , inputChannel.size () );

                inputChannel.close ();
                outputChannel.close ();

            } catch ( FileNotFoundException e1 ) {
                StaticValues.writer.writeExcel (SalesMaster.class.getSimpleName () , SalesMaster.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
            } catch ( IOException e2 ) {
                StaticValues.writer.writeExcel (SalesMaster.class.getSimpleName () , SalesMaster.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
            }
        }
        return purchaseOrderFile_name;
    }

    public void openFile ( String filename ) {

        if (  ! filename.equals ( "" ) ) {
            try {
                String fileName = filename;

                File dir = new File ( "Uploaded Files\\Salers Orders" );

                File file = new File ( dir , fileName );

                Desktop desktop = Desktop.getDesktop ();
                if ( file.exists () ) {
                    desktop.open ( file );
                }
            } catch ( IOException ex ) {
                //    Logger.getLogger(FinishedGoodMaster.class.getName()).log(Level.SEVERE, null, ex);
                StaticValues.writer.writeExcel (SalesMaster.class.getSimpleName () , SalesMaster.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    public static javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    public static javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
    
    
    
    String purchaseOrderFile_name = "";

    class ProductOrderList extends javax.swing.JPanel {

        ResultSet result = null;

        public ArrayList<ProductOrderDetailPanel> partOrderDetailsList = null;
        private ArrayList<ProductOrderModel> orderDetailList = new ArrayList<ProductOrderModel> ();

      
        public ProductOrderList () {
            initComponents ();

            PartOrdersList panel = new PartOrdersList ();
            panel.setBounds ( 0 , 0 , 730 , 300 );
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
            setPreferredSize ( new java.awt.Dimension ( 260 , 260 ) );
            setLayout ( null );

        }// </editor-fold>                        

        public ArrayList<ProductOrderModel> getRejection () {
            // TODO add your handling code here:
            ProductOrderModel tlm = null;

            int totalRejection = 0;

            for ( int i = 0 ; i < partOrderDetailsList.size () ; i ++ ) {

                tlm = new ProductOrderModel ();
                if ( Integer.parseInt( partOrderDetailsList.get ( i ).getPartQty () ) > 0 ) {
                    tlm.setPart_id (  partOrderDetailsList.get ( i ).getPart_id () );
                    tlm.setPartName_Code (partOrderDetailsList.get ( i ).getPartName_Code () );
                    tlm.setPartQty ( partOrderDetailsList.get ( i ).getPartQty ());
                    tlm.setRate (partOrderDetailsList.get ( i ).getRate ());
                    tlm.setEdd ( partOrderDetailsList.get ( i ).getEdd () );
                    tlm.setUnit ( partOrderDetailsList.get ( i ).getUnit () );
                    
                    tlm.setOrderSchedule ( partOrderDetailsList.get ( i ).getOrderScheduleForItem ( )   );
                    
                    //lm.setReason ( rejectionList.get ( i ).getReasonStr () );
                    orderDetailList.add ( tlm );
                }
            }
            
            return orderDetailList ;
        }

        // Variables declaration - do not modify                     
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        // End of variables declaration                   

        class PartOrdersList extends JPanel {

            private JPanel panel;

            private JScrollPane scroll;
            private JButton btnAddType;

//    public ArrayList<RMQtyPanel> rmBomList = null ;
            public PartOrdersList () {
                // getContentPane().setLayout(new BorderLayout());       

                setLayout ( new BorderLayout () );

                panel = new JPanel ();
                panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
                
                scroll = new JScrollPane ( panel ,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

                add ( scroll , BorderLayout.CENTER );

                setVisible ( true );

                

                partOrderDetailsList = new ArrayList<ProductOrderDetailPanel> ();

                String addEmpAPICall = "customermaps?CUSTOMER_ID="+ customerCombo.get( jComboBox2.getSelectedIndex ()  )[0]  ;
                String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    
                    if( ! result2.contains( "not found" )   && result2.contains("success"))
                    {  
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jObject = new JSONObject( result2 );
                    Iterator<?> keys = jObject.keys();

/*                if (  ! result2.contains ( "not found" ) && result2.contains ( "success" ) ) {
                    map = new HashMap<String , Object> ();
                    jObject = new JSONObject ( result2 );
                    keys = jObject.keys ();  */

                    while ( keys.hasNext () ) {
                        String key = ( String ) keys.next ();
                        Object value = jObject.get ( key );
                        map.put ( key , value );
                    }

                    JSONObject st = ( JSONObject ) map.get ( "data" );
                    JSONArray records = st.getJSONArray ( "records" );

                    JSONObject emp = null;
                    partDetails = new ArrayList<ProductOrderModel> ();
                    panel.setBounds ( 0 , 0 , 730 , 50 * records.length () );
                    ProductOrderDetailPanel pane = null;
                    for ( int i = 0 ; i < records.length () ; i ++ ) {

                        emp = records.getJSONObject ( i );

                        //  int part_id , String partName_Code , String partQty , String edd , String rate , String unit
                        partDetails.add ( 
                                new ProductOrderModel ( 
                                        Integer.parseInt ( emp.get ( "M_FG_ID" ).toString () ) ,
                                        emp.get ( "FG_PART_NO" ).toString () ,
                                        "0", 
                                        new SimpleDateFormat( "MMM dd, yyyy" ).format( Calendar.getInstance ().getTime ()  )   ,
                                        emp.get ( "SALES_RATE" ).toString () ,
                                        emp.get ( "S_UOM_ID" ).toString ()  )  );

                        try{
                            Boolean check = jCheckBox1.isSelected();
                            pane = new ProductOrderDetailPanel ( check );
                        }catch( Exception e ){
                            System.out.println ( ""+e.getMessage() );
                        }
                        
                        pane.setBounds ( 0 , ( i * 50 ) , 730 , 50 );
                        pane.setPart_id ( Integer.parseInt ( emp.get ( "M_FG_ID" ).toString () )   );
                        pane.setPartName_Code ( emp.get ( "FG_PART_NO" ).toString ()  );
                        pane.setPartQty ( "0" );
                        pane.setEdd( new SimpleDateFormat( "MMM dd, yyyy" ).format( Calendar.getInstance ().getTime ()  )   ) ;
                        pane.setRate ( emp.get ( "SALES_RATE" ).toString ()   );
                        pane.setUnit ( emp.get ( "S_UOM_ID" ).toString ()  )  ;

                        panel.add ( pane );
                        panel.revalidate ();
                        partOrderDetailsList.add ( pane );
                    }
                }       
            }
        } 
    }
    
    class DeliveryScheduleReportDialog extends JDialog {
        
        DeliveryScheduleReport confirm ;
        DeliveryScheduleMonthlyReport confirm2 ;
        
        public  DeliveryScheduleReportDialog( Frame parent , String reportType){
            
            if( reportType.equals( "FullDetails" ) ){
                confirm = new DeliveryScheduleReport(    );
                confirm.setBounds(  0    , 0, 1080,  500);    
                 getContentPane().add( confirm  , BorderLayout.CENTER);
            }else if( reportType.equals( "monthly" ) ){
                confirm2 = new DeliveryScheduleMonthlyReport()  ;
                confirm2.setBounds(  0    , 0, 1080,  500);    
                 getContentPane().add( confirm2  , BorderLayout.CENTER);
            }
            
           

            pack();
            setResizable(true);
            setBounds(  0    , 0,  1100,550 );
            setLocationRelativeTo(parent);
            
        }
        

    }
    
}
    
    
    


/*
 *
 * CREATE TABLE `sales_orders` ( `sales_po_id`	INTEGER NOT NULL PRIMARY KEY
 * AUTOINCREMENT UNIQUE, `so_customer_id`	INTEGER, `product_ids`	TEXT, `po_date`
 * TEXT, `po_copy_name`	TEXT, `order_qty`	INTEGER, `order_no`	TEXT );
 *
 *
 * CREATE TABLE `po_order_details` ( `oder_detail_id`	INTEGER NOT NULL PRIMARY
 * KEY AUTOINCREMENT UNIQUE, `product_id`	INTEGER, `product_qty`	INTEGER,
 * `ref_po_id`	INTEGER NOT NULL );
 *
 * CREATE TABLE `op_wip` ( `P_ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT
 * UNIQUE, `PRODUCT`	INTEGER, `CUSTOMER`	INTEGER, `TARGETQTY`	INTEGER,
 * `FINISHED`	INTEGER, `MOVEQTY`	INTEGER, `MOVEDATE`	TEXT, `ORDERNO`	INTEGER,
 * `PROCESS_STAGE`	INTEGER );
 *
 */
