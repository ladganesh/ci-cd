/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.quotation;

import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.components.ProgressDialog;
import static trixware.erp.prodezydesktop.examples.HomeScreen.home;
import static trixware.erp.prodezydesktop.examples.HomeScreen.jLabel6;
import static trixware.erp.prodezydesktop.examples.HomeScreen.scroll;
import static trixware.erp.prodezydesktop.examples.HomeScreen.width;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.quotation.partmaster.ProcessRMData;
import trixware.erp.prodezydesktop.quotation.partmaster.TableModiFier;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class EnquiriesForm extends javax.swing.JPanel {
   
    /**
     * Creates new form EnquiriesForm
     */
    Vector<Vector<Object>> data = null;
    Vector<String> columnNames = null;
    Vector<String> columnNames2 = null;   
    ArrayList<ENQModelDR> enqModel=null;
    
    public EnquiriesForm () {
        initComponents ();
        showDialog();
    }
    
    public static int empId; 

    public void loadUser()
    {      
        String result2 = WebAPITester.prepareWebCall ( "useredit?user_id="+Proman_User.getEID()  , StaticValues.getHeader () , "" );        
        System.out.println(result2);
        if(  result2.contains("success")   ) {
            HashMap<String , Object> map = new HashMap<String , Object> ();
            JSONObject jObject = new JSONObject ( result2 );
            Iterator<?> keys = jObject.keys ();

            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }

            JSONArray records = ( JSONArray ) map.get ( "data" );

            JSONObject emp = null;
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );
                empId = Integer.parseInt(  emp.get ( "ref_emp_id" ).toString () );                             
            }

            System.err.println("Employee ID"+empId);
        }
        System.err.println("Employee "+empId);
    }
    ProgressDialog progress = new ProgressDialog( null ) ;
    Thread getTableAndFilesContent = null ;
    int count=0;
    public boolean showDialog(){
        
        getTableAndFilesContent = new Thread(){
            @Override
            public void run(){
                progress.updateTitle("Please wait..");
                progress.updateMessage("<html>The task is being executed.<br> Please wait until it is completed.</html>");
                try{
                    loadUser();

                progress.updateTitle("Please wait..");
                progress.updateMessage("<html>Loading.. Enquiries.<br> Please wait until it is completed.</html>");

                loadTable();
                try {
                    new ProcessRMData().getData(progress);
                } catch (Exception e) {
                } finally {
                        hideDialog();
                    }                  
                }catch(Exception ex){
//                    count++;
//                    if(count<5)
//                    {                       
//                        showDialog();                        
//                    }
                }
            }
        };
        getTableAndFilesContent.start();
        progress.openProgressWindow ();
        return true ;
    }
    
    public boolean hideDialog(){
        count=0;
        progress.updateTitle("");
        progress.updateMessage("");
        progress.closeProgressWindow ();
        return true ;
    }

    public void loadTable() throws Exception
    {
        enqModel=new ArrayList<ENQModelDR>();
        data =new Vector<Vector<Object>>();
        columnNames=new Vector<String>();
        columnNames2=new Vector<String>();
        
        columnNames.add("enq_no");
        columnNames2.add("ENQ_NO");
        columnNames.add("enq_latest_version");
        columnNames2.add("ENQ Latest Version");
        columnNames.add("CUSTOMER_NAME");
        columnNames2.add("Customer Name");
        columnNames.add("assly_no");
        columnNames2.add("Assembly No");
        columnNames.add("CONT_NAME");
        columnNames2.add("Contact Person");
        columnNames.add("CONT_NO");
        columnNames2.add("Contact No");

        String addEmpAPICall="enquiries?ref_emp_id="+empId;
        String result2 ="";
        result2=WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
       
        if( !  result2.contains(  "not found"   )  ){
            if ( result2!= null ) 
            {
                String enqId="";
                String enqNo="";
                String enqVersion="";
                String custId="";
                String custName="";
                String asslyId="";
                String contactName="";
                String contactNo="";
                HashMap<String, Object> map=new HashMap<String, Object>();
                JSONObject jObject = new JSONObject( result2 );
                Iterator<?> keys =jObject.keys();
                
                while( keys.hasNext() ){
                    String key = (String)keys.next();
                    Object value = jObject.get ( key ) ; 
                    map.put(key, value);
                }
    
                JSONObject st =  (JSONObject) map.get ( "data" );
                JSONArray records = st.getJSONArray ( "records");

                JSONObject enq = null;
                
                for( int i=0; i<records.length (); i++ )
                {
                    Vector<Object> vector=new Vector<Object>();
                    enq = records.getJSONObject ( i);
                    enqId=enq.get("enq_id").toString();
                    custId=enq.get("customer").toString();
                    enqVersion=enq.get ( "enq_latest_version" ).toString();
                    enqNo=enq.get ( "enq_no" ).toString();
                    vector.add(enq.get ( "enq_no" ));
                    vector.add(enq.get ( "enq_latest_version" ));
   
                    addEmpAPICall="customeredit?CUSTOMER_ID="+custId;
                    
                    result2=WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    
                    if ( result2!= null && !result2.contains("not found")) 
                    {
                        HashMap<String, Object> map1=new HashMap<String, Object>();
                        JSONObject jObject1 = new JSONObject( result2 );
                        Iterator<?> keys1 =jObject1.keys();
                        while( keys1.hasNext() ){
                            String key = (String)keys1.next();
                        Object value = jObject1.get ( key ) ; 
                        map1.put(key, value);
                        
                     }
                      JSONArray records1 = (JSONArray) map1.get("data");
 
                      JSONObject cust = null;//st1.getJSONObject("CUSTOMER_NAME");
                      cust = records1.getJSONObject ( 0);
                      custName=cust.get("CUSTOMER_NAME").toString();
                       vector.add(cust.get("CUSTOMER_NAME"));
                    }
                 
                    addEmpAPICall="assemblies?ref_enq_id="+enqId;
                    result2=WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
 
                    if ( result2!= null ) 
                    {
                        HashMap<String, Object> map2=new HashMap<String, Object>();
                        JSONObject jObject2 = new JSONObject( result2 );
                        Iterator<?> keys2 =jObject2.keys();

                        while( keys2.hasNext() ){
                            String key = (String)keys2.next();
                            Object value = jObject2.get ( key ) ; 
                            map2.put(key, value);
                        }
                        
                        JSONObject st1 = (JSONObject) map2.get ( "data" );
                        JSONArray records1 = st1.getJSONArray ( "records");
                        Object assembly=null;
                        JSONObject assly = null;
                        for( int i1=0; i1<records1.length (); i1++ )
                        {
                            assly = records1.getJSONObject ( i1);
                            asslyId=assly.get("assly_id").toString();
                            assembly=assly.get("assly_no");
//                            customer.add(new CustomerDR(Integer.parseInt(cust.get ( "CUSTOMER_ID" ).toString()),cust.get ( "CUSTOMER_NAME" ).toString(),cust.get ( "CITY" ).toString() ,cust.get ( "CUST_ADDRESS" ).toString()));
                        }
                        vector.add(assembly);                   
                    }

                    addEmpAPICall="contacts?customer_id="+custId;
                    result2=WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
 
                    if ( result2!= null && !result2.contains("\"records\":null")) 
                    {
                        HashMap<String, Object> map2=new HashMap<String, Object>();
                        JSONObject jObject2 = new JSONObject( result2 );
                        Iterator<?> keys2 =jObject2.keys();

                        while( keys2.hasNext() ){
                            String key = (String)keys2.next();
                            Object value = jObject2.get ( key ) ; 
                            map2.put(key, value);
                        }
                        
                        JSONObject st2 = (JSONObject) map2.get ( "data" );
       
                        JSONArray records1 = st2.getJSONArray ( "records");
                        JSONObject assly = null;
                        for( int i1=0; i1<records1.length (); i1++ )
                        {
                            assly = records1.getJSONObject ( i1);
                            contactName=assly.get("CONT_NAME").toString();
                            contactNo=assly.get("CONT_NO").toString();
//                            customer.add(new CustomerDR(Integer.parseInt(cust.get ( "CUSTOMER_ID" ).toString()),cust.get ( "CUSTOMER_NAME" ).toString(),cust.get ( "CITY" ).toString() ,cust.get ( "CUST_ADDRESS" ).toString()));
                        }
                        vector.add(contactName);  
                        vector.add(contactNo);  
                    }
                    enqModel.add(new ENQModelDR(enqId,enqNo,asslyId, custId,custName, enqVersion));
                    data.add(vector);
                }                
                jTable1.setModel ( new DefaultTableModel ( data , columnNames2 ) );
                TableModiFier.setTableSize(jTable1,30,100);
            }
        }
//        hideDialog();
    }
    public void showENQialog(  String id ,String enqNo,String custName,String revNo ){

        ENQDetailsDialog process1 = new ENQDetailsDialog( id,enqNo,custName,revNo) ;
        process1.setVisible( true );        
    }
    EnquiryDetails enq=null;
    class ENQDetailsDialog extends JDialog{
        
        int totalPanels = 1 ;
        
        JScrollPane jScrollPane1 = null ;
        
        public ENQDetailsDialog (String enqId,String enqNo,String custName,String revNo) {
            jButton5 = new javax.swing.JButton();
            jButton3 = new javax.swing.JButton();
            jButton4 = new javax.swing.JButton();

            setLayout(null);

            jButton5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton5.setText("Next");
            jButton5.setOpaque(false);
            jButton5.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton5ActionPerformed(evt);
                }
            });
            add(jButton5);
            jButton5.setBounds(340, 410, 180, 32);
            
            jButton3.setBounds(400, 7, 110, 32);
            jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton4.setText("Discard & Close");
            add(jButton4);
            jButton4.setBounds(200, 410, 140, 32);
            
            setBounds ( 300, 200, 710, 500);
            setVisible ( true );
            setLayout ( null );
            setDefaultCloseOperation ( JDialog.DISPOSE_ON_CLOSE);
            enq=new EnquiryDetails();
            enq.loadTable(enqId,enqNo,custName,revNo);
            enq.setBounds ( 0, 0, 710, 400);
            
            add(  enq );
            
            revalidate ();
            repaint ();
            
        }
        private void jButton5ActionPerformed(ActionEvent evt) {
           
            List<String> list=enq.getList();
            
            CreateEnquiryForm process = new CreateEnquiryForm(Integer.parseInt(list.get(7)),true);
               process.setLabels(list);
                scroll = new JScrollPane ( process ,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

                int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
                process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
                scroll.setBounds ( leftMargin - 15 , 5 , width - leftMargin , 580 );

                scroll.setBackground ( Color.WHITE );
                scroll.setBorder ( BorderFactory.createEmptyBorder () );

                home.jPanel1.removeAll ();
                home.jPanel1.setLayout ( null );
                home.jPanel1.add ( scroll , BorderLayout.CENTER );
                home.jLabel6.setText ( "  Create Enquiry" );

                home.revalidate ();
                home.repaint ();      
                
                dispose();            
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1100, 550));
        setLayout(null);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Enq no", "Assembly no", "Total rev", "Enquiry Status"
            }
        ));
        jTable1.setAlignmentX(1.0F);
        jTable1.setAlignmentY(1.0F);
        jTable1.setFillsViewportHeight(true);
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(10, 60, 1060, 460);

        jButton1.setText("Create New Enquiry");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(920, 10, 150, 30);

        jButton2.setText("Rejection Master");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(780, 10, 130, 30);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        CreateEnquiryForm process = new CreateEnquiryForm();
        
        scroll = new JScrollPane ( process ,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

            //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
            //scroll.setBounds ( 5 , 5 , width-25 , 605 );
            int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
            process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
            scroll.setBounds ( leftMargin - 15 , 5 , width - leftMargin , 580 );

            scroll.setBackground ( Color.WHITE );
            scroll.setBorder ( BorderFactory.createEmptyBorder () );

            home.jPanel1.removeAll ();
            home.jPanel1.setLayout ( null );
            //home.jPanel1.add ( customer );
            home.jPanel1.add ( scroll , BorderLayout.CENTER );
            //home.jPanel1.add ( bp1 );
            jLabel6.setText ( "  Create Enquiry" );

            home.revalidate ();
            home.repaint ();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int index=jTable1.getSelectedRow();     
        String enqId=enqModel.get(index).getEnqId();
        String enqNo=enqModel.get(index).getEnqNo();
        String custName=enqModel.get(index).getCustomerName();
        String revNo=enqModel.get(index).getEnqLatestVersion();
        //  new ENQDetailsDialog(enqId);     
        showENQialog(enqId,enqNo,custName,revNo);
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        RecoveryMaster process = new RecoveryMaster();
        
        scroll = new JScrollPane ( process ,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

            //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
            //scroll.setBounds ( 5 , 5 , width-25 , 605 );
            int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
            process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
            scroll.setBounds ( /*leftMargin - 15*/10 , 5 ,/* width - leftMargin*/900 , 580 );

            scroll.setBackground ( Color.WHITE );
            scroll.setBorder ( BorderFactory.createEmptyBorder () );

            home.jPanel1.removeAll ();
            home.jPanel1.setLayout ( null );
            //home.jPanel1.add ( customer );
            home.jPanel1.add ( scroll , BorderLayout.CENTER );
            //home.jPanel1.add ( bp1 );
            jLabel6.setText ( "Rejection Master" );

            home.revalidate ();
            home.repaint ();

    }//GEN-LAST:event_jButton2ActionPerformed
 private javax.swing.JButton jButton5;
        private javax.swing.JButton jButton3;
        private javax.swing.JButton jButton4;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
