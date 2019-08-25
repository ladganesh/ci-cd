/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Derived.AutoCompletion;
import trixware.erp.prodezydesktop.Model.DailyDataEntryModel;
import trixware.erp.prodezydesktop.Model.EmployeeDR;
import trixware.erp.prodezydesktop.Model.MachineDR;
import trixware.erp.prodezydesktop.Model.POrderDR;
import trixware.erp.prodezydesktop.Model.ProcessDR;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.RMBatchDR;
import trixware.erp.prodezydesktop.Model.RejectionModel;
import trixware.erp.prodezydesktop.Model.Rejection_Reasons;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Model.TimeLossModel;
import trixware.erp.prodezydesktop.Model.UOMDR;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.masters.BatchProcessing;
import trixware.erp.prodezydesktop.web_services.WebAPITester;
/**
 *
 * @author Rajesh
 */
public class DailyReportFormEmployeeWise extends javax.swing.JPanel {

//    ArrayList<HashMap<String, String>> products = null ; 
//    ArrayList<HashMap<String, String>> employees = null ; 
//    ArrayList<HashMap<String, String>> machines = null ; 
//    ArrayList<HashMap<String, String>> processes;
    ArrayList<ProductDR> products = null;
    ArrayList<EmployeeDR> employees = null;
    ArrayList<ProcessDR> processes = null;
    ArrayList<MachineDR> machines = null;
    ArrayList<Rejection_Reasons> rej_reasons = null ;
    ArrayList<UOMDR> UOM=null;
    int incompleteDataDialog = 0 ;
    int totalTimeLossInMinutes = 0;
    double totalTimeLossInHours = 0 ;
    
    JPanel panel = null;
    JScrollPane scroll = null;

    
    ProductDR prdr = null;
    EmployeeDR empdr = null;
    ProcessDR prcdr = null;
    MachineDR mcdr = null;
    Rejection_Reasons rejrsn = null ;
//    HashMap<String, String> entity = null ;

    Calendar c2 = null;
    SimpleDateFormat sdf = null;
    
    SingleProcessDE_FormEmployeeWise[] forms = null;
    
    boolean processRowsPresent = false ;
    
    String existingBatchNo;
    int existingProductCode;
    int existingProcessCode;
    int existingMachineCode;

    ArrayList<RMBatchDR> batches = null;
    ArrayList<POrderDR> orders = null;
    public ArrayList<TimeLossModel> timeLossList ;
    public ArrayList<RejectionModel> rejectionList ;
    
    /**
     * Creates new form DailyReportForm
     */
    public DailyReportFormEmployeeWise () {
        initComponents ();

        AutoCompletion.enable ( jComboBox1 );
        AutoCompletion.enable ( jComboBox2 );
        
        panel = new JPanel ();
        panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );

        scroll = new JScrollPane ( panel ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        scroll.setBounds ( 30 , 95 , 1280 , 270 );
        scroll.setOpaque ( false );
        scroll.setBackground ( Color.WHITE );
        scroll.revalidate ();
        scroll.repaint ();
        
         loadComboBoxes ();
         //loadEntries ();
        Thread t = new Thread(){
         public void run(){
        
                getFreshList ();
            }
        };
        
         t.start();
         
    }

    public void getFreshList () {
        String getProductionData = "dailyreports";
        String productionDataresult2 = WebAPITester.prepareWebCall ( getProductionData , StaticValues.getHeader () , "" );

        if (  ! productionDataresult2.contains ( "not found" ) ) {
            Vector<String> columnNames = new Vector<String> ();
            Vector<String> columnNames2 = new Vector<String> ();
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();

            HashMap<String , Object> map = new HashMap<String , Object> ();
            JSONObject jObject = new JSONObject ( productionDataresult2 );
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
            columnNames.add ( "PART_NAME" );
            columnNames.add ( "PROCESS_NAME" );
            columnNames.add ( "MACHINE_NO" );
            columnNames.add ( "EMP_NAME" );
            columnNames.add ( "shifttitle" );
            columnNames.add ( "BatchName" );
            columnNames.add ( "showrdate" );
            columnNames.add ( "qtyin" );
            columnNames.add ( "qtyout" );
            columnNames.add ( "rejection" );
            columnNames2 = new Vector<String> ();
            columnNames2.add ( "Part" );
            columnNames2.add ( "Process" );
            columnNames2.add ( "Machine" );
            columnNames2.add ( "Employee" );
            columnNames2.add ( "Shift" );
            columnNames2.add ( "Batch" );
            columnNames2.add ( "Date" );
            columnNames2.add ( "Parts In" );
            columnNames2.add ( "Parts Out" );
            columnNames2.add ( "Rejected" );

            for ( int i = 0 ; i < records.length () ; i ++ ) {
                Vector<Object> vector = new Vector<Object> ();
                for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {
                    emp = records.getJSONObject ( i );
                    if ( emp.get ( columnNames.get ( columnIndex ) ) != null &&  ! emp.get ( columnNames.get ( columnIndex ) ).toString ().equals ( "null" ) ) {
                        vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                    } else {
                        vector.add ( "--" );
                    }
                }
                data.add ( vector );

                try {
                    jTable1.setModel ( new DefaultTableModel ( data , columnNames2 ) );
                } catch ( IndexOutOfBoundsException e ) {

                }
            }

        }
    }
    
    public void loadProcessesForProduct () {

        try {

            int selectedFG_ID = products.get ( jComboBox1.getSelectedIndex () ).FG_ID;

            processes = new ArrayList<ProcessDR> ();

            addEmpAPICall = "processmachmaps?FG_ID=" + selectedFG_ID;
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            String result3 = WebAPITester.prepareWebCall ( "processes" , StaticValues.getHeader () , "" );

            HashMap<String , Object> map = new HashMap<String , Object> ();
            JSONObject jObject = new JSONObject ( result2 );
            Iterator<?> keys = jObject.keys ();

            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }

            HashMap<String , Object> map2 = new HashMap<String , Object> ();
            jObject = new JSONObject ( result3 );
            keys = jObject.keys ();

            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map2.put ( key , value );
            }

            JSONObject st = ( JSONObject ) map.get ( "data" );
            JSONObject st2 = ( JSONObject ) map2.get ( "data" );
            JSONArray records, records2;
            JSONObject emp = null, emp2 = null;

            try {
                records = st.getJSONArray ( "records" );
                records2 = st2.getJSONArray ( "records" );

                processes = new ArrayList<ProcessDR> ();
                for ( int i = 0 ; i < records.length () ; i ++ ) {
                    emp = records.getJSONObject ( i );
                    for ( int j = 0 ; j < records2.length () ; j ++ ) {
                        emp2 = records2.getJSONObject ( j );

                        if ( emp.get ( "REF_PROCESS_ID" ).toString ().equals ( emp2.get ( "PROCESS_ID" ).toString () ) ) {
                            processes.add ( new ProcessDR ( Integer.parseInt ( emp.get ( "REF_PROCESS_ID" ).toString () ) , emp2.get ( "PROCESS_NAME" ).toString () ) );
                        }

                    }
                }
            } catch ( Exception e ) {
            }

            

            // MACHINE COMBO
            //result = DB_Operations.getMasters ( "raw_material_type" );
            addEmpAPICall = "machines";
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            map = new HashMap<String , Object> ();
            jObject = new JSONObject ( result2 );
            keys = jObject.keys ();

            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }

            st = ( JSONObject ) map.get ( "data" );
            records = st.getJSONArray ( "records" );

            emp = null;
            machines = new ArrayList<MachineDR> ();
            for ( int i = 0 ; i < records.length () ; i ++ ) {

                emp = records.getJSONObject ( i );
                machines.add ( new MachineDR ( Integer.parseInt ( emp.get ( "MCH_ID" ).toString () ) , emp.get ( "MACHINE_NO" ).toString () ) );
            }

            addEmpAPICall = "unitmeas";
            result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    
            if (!result2.contains("not found")) {
                HashMap<String, Object> mapUOM = new HashMap<String, Object>();
                JSONObject jObjectUOM = new JSONObject(result2);
                Iterator<?> keysUOM = jObjectUOM.keys();
                
                while (keysUOM.hasNext()) {
                    String key = (String) keysUOM.next();
                    Object value = jObjectUOM.get(key);                    
                    mapUOM.put(key, value);
                }
                
                JSONObject stUOM = (JSONObject) mapUOM.get("data");
                JSONArray recordsUOM = stUOM.getJSONArray("records");
                
                JSONObject uom = null;
                UOM = new ArrayList<UOMDR>();
                for (int i = 0; i < recordsUOM.length(); i++) {                   
                    uom = recordsUOM.getJSONObject(i);
                    UOM.add(new UOMDR(Integer.parseInt(uom.get("UOM_ID").toString()), uom.get("UOM").toString()));                    
                }
            }
            
            addEmpAPICall = "rejreasons";
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            map = new HashMap<String , Object> ();
            jObject = new JSONObject ( result2 );
            keys = jObject.keys ();

            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }

            st = ( JSONObject ) map.get ( "data" );
            records = st.getJSONArray ( "records" );

            emp = null;
            rej_reasons = new ArrayList<Rejection_Reasons> ();
            panel.setBounds ( 0 , 0 , 250 , 36 * records.length () );
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );
                rej_reasons.add ( new Rejection_Reasons ( Integer.parseInt ( emp.get ( "RR_ID" ).toString () ) , emp.get ( "RR_DESC" ).toString () ) );
            }

//////////////////////////////////////////////          Start    -  Load batches from batch master for selection   -    Start         ///////////////////////////////////////////////////////////       
        jComboBox3.removeAllItems ();
        jComboBox3.removeActionListener(selectBatch);
        if ( jComboBox3.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            addEmpAPICall = "billofmaterials?FG_ID=" + selectedFG_ID;
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            map = new HashMap<String , Object> ();
            jObject = new JSONObject ( result2 );
            keys = jObject.keys ();
            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }
            ArrayList<String[]> rmids = new ArrayList<String[]> ();
            st = ( JSONObject ) map.get ( "data" );
            records = st.getJSONArray ( "records" );
            emp = null;
            batches = new ArrayList<RMBatchDR> ();
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );
                rmids.add ( new String[] { emp.get ( "RM_ID" ).toString () , emp.get ( "TOTAL_WT" ).toString () } );
            }

            batches = new ArrayList<RMBatchDR> ();
            for ( int j = 0 ; j < rmids.size () ; j ++ ) {

                //addEmpAPICall = "latestrwamstock?rmid="+rmids.get(j)[0];
                addEmpAPICall = "batches?rm_id=" + rmids.get ( j )[ 0 ];
                result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                map = new HashMap<String , Object> ();
                jObject = new JSONObject ( result2 );
                keys = jObject.keys ();

                while ( keys.hasNext () ) {
                    String key = ( String ) keys.next ();
                    Object value = jObject.get ( key );
                    map.put ( key , value );
                }

                try {
                    st = ( JSONObject ) map.get ( "data" );
                    records = st.getJSONArray ( "records" );
                    emp = null;
                    for ( int i = 0 ; i < records.length () ; i ++ ) {
                        emp = records.getJSONObject ( i );
           
                        int stockVal = 0;
                        double stockVal2 = 0.0;
                        try {
                            stockVal = Integer.parseInt ( emp.get ( "BatchQty" ).toString () );
                        } catch ( NumberFormatException e ) {
                            stockVal2 = Double.parseDouble ( emp.get ( "BatchQty" ).toString () );
                        }

                        if (  ! emp.get ( "BatchId" ).toString ().equals ( "null" ) ) {

                            if ( stockVal != 0 || stockVal2 != 0.0 ) 
                            {
                                int flag = emp.getInt("quality_flag");
                                if(flag == 1)
                                {    
                                jComboBox3.addItem ( emp.get ( "BatchName" ).toString () );
                                try {
                                    //batches.add( new RMBatchDR ( Integer.parseInt(emp.get ( "RMStock_TR_ID" ).toString ()),  Integer.parseInt(emp.get ( "RMI_ID" ).toString ()),   emp.get ( "inward_batch_no" ).toString () ,  Integer.parseInt( rmids.get(j)[1] ),  emp.get( "CLOSING").toString() , emp.get ( "RM_CODE" ).toString () )); 
                                    batches.add ( new RMBatchDR ( Integer.parseInt ( emp.get ( "BatchId" ).toString () ) , emp.get ( "BatchName" ).toString () , Integer.parseInt ( emp.get ( "ref_rm_id" ).toString () ) , Integer.parseInt ( rmids.get ( j )[ 1 ] ) , emp.get ( "BatchQty" ).toString () ) );
                                } catch ( Exception e ) {
                                    System.out.println ( "" + e.getMessage () );
                                }
                                }
                            }
                        }
                    }
                } catch ( JSONException e ) {
                    System.out.println ( "" + e.getMessage () );
                }
            }

            if ( jComboBox3.getItemCount () > 0 ) {
                jComboBox3.addActionListener ( selectBatch );
                double availbleQty;
                int FGQty;
                RMBatchDR selectedBtch = batches.get ( jComboBox3.getSelectedIndex () );
                availbleQty = Double.parseDouble ( selectedBtch.availbleQty );
                FGQty = selectedBtch.FGQty;
               // jLabel26.setText ( String.valueOf ( availbleQty ) );
               // jLabel28.setText ( String.valueOf ( availbleQty * FGQty ) );
            }
        }
//////////////////////////////////////////////          End    -  Load batches from batch master for selection   -    End         /////////////////////////////////////////////////////////// 

        } catch ( NullPointerException e ) {
            JOptionPane.showMessageDialog ( null , "Error1 : " + e.getMessage () );
            StaticValues.writer.writeExcel ( DailyReportForm2.class.getSimpleName () , DailyReportForm2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

    }
    
    ActionListener selectBatch = new ActionListener () {
        @Override
        public void actionPerformed ( java.awt.event.ActionEvent e ) {

            if ( jComboBox3.getItemCount () > 0 ) {
                //  jComboBox7.addActionListener ( selectBatch );
                double availbleQty;
                int FGQty, RMID, BTC_ID;
                RMBatchDR selectedBtch = batches.get ( jComboBox3.getSelectedIndex () );
                // BTC_NAME = selectedBtch.BTC_NAME ;
                availbleQty = Double.parseDouble ( selectedBtch.availbleQty );
                RMID = selectedBtch.RMID ;
                FGQty = selectedBtch.FGQty;
                BTC_ID    = selectedBtch.BTC_ID ;    
               // jLabel26.setText ( String.valueOf ( availbleQty ) );
               // jLabel28.setText ( String.valueOf ( availbleQty * FGQty ) );
            }
        }
    };
    
    public void loadProcessesForProduct_old(){
        
        ResultSet orderDetails = null, result2 = null;
       
        try {

        String queryTwo = "SELECT EmployeePK, EMP_NAME FROM employee";
        String queryThree = "SELECT MCH_ID, MACHINE_NO FROM machine";
        String queryFour = "SELECT RR_ID, RR_DESC FROM rejection_reasons";
        
            int selectedFG_ID =  products.get(jComboBox1.getSelectedIndex ()).FG_ID;
            result2 = DB_Operations.executeSingle ( "SELECT REF_PROCESS_ID, PROCESS_NAME FROM FG_PROCESS_MACH_MAP a INNER JOIN PROCESS_MASTER b on a.REF_PROCESS_ID = b.PROCESS_ID AND REF_FG_ID = " + selectedFG_ID );

            if ( result2 != null ) {
              //  jComboBox1.removeAllItems ();
                
                processes = new ArrayList<ProcessDR> ();
               
                while ( result2.next () ) {

                    prcdr = new ProcessDR ();
                    prcdr.PRC_ID = Integer.parseInt ( result2.getString ( 1 ) );
                    prcdr.PRC_NAME = result2.getString ( 2 );
                    processes.add ( prcdr );
                }
            }
            
            
           
            result2 = DB_Operations.executeSingle ( queryThree );
            machines = new ArrayList<MachineDR>();

            while ( result2.next () ) {
                mcdr = new MachineDR ();
                
                mcdr.MC_ID = Integer.parseInt ( result2.getString ( 1 ) );
                mcdr.MC_NAME = result2.getString ( 2 );
                machines.add ( mcdr );
            
            }

       
            
            
            
            result2 = DB_Operations.executeSingle ( queryFour );
            // employees = new ArrayList<HashMap<String, String>> ();

            rej_reasons = new ArrayList<Rejection_Reasons> ();
            while ( result2.next () ) {
                rejrsn = new Rejection_Reasons ();
                
                rejrsn.REJ_ID = Integer.parseInt ( result2.getString ( 1 ) );
                rejrsn.REJ_DESC = result2.getString ( 2 );
                rej_reasons.add ( rejrsn );
               
            }
            
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog ( null, "Error1 : "+e.getMessage () );
            StaticValues.writer.writeExcel ( DailyReportFormEmployeeWise.class.getSimpleName () , DailyReportFormEmployeeWise.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }catch(SQLException e){
            JOptionPane.showMessageDialog ( null, "Error2 : "+e.getMessage () );
            StaticValues.writer.writeExcel ( DailyReportFormEmployeeWise.class.getSimpleName () , DailyReportFormEmployeeWise.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }finally{
                 try {
                   result2.close();
               } catch ( Exception e ) {
                   StaticValues.writer.writeExcel ( DailyReportFormEmployeeWise.class.getSimpleName () , DailyReportFormEmployeeWise.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
               }
        }
    }
    
    public void loadComboBoxes () {


 
        jComboBox1.removeActionListener ( productSelection );
        if ( jComboBox1.getItemCount () == 0 ) {

            addEmpAPICall = "finishedgoods";
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
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
            products = new ArrayList<ProductDR> ();
            for ( int i = 0 ; i < records.length () ; i ++ ) {

                emp = records.getJSONObject ( i );
                jComboBox1.addItem ( emp.get ( "FG_PART_NO" ).toString () );
                products.add ( new ProductDR ( Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) , emp.get ( "FG_PART_NO" ).toString () , emp.get ( "FG_UOM_ID" ).toString () ) );

            }
        }
        
           if( jComboBox1.getItemCount () > 0 ){
                jComboBox1.addActionListener ( productSelection );
            }

        //   ORDER SELECTION COMBO
        if ( jComboBox4.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            addEmpAPICall = "salesorders";
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
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
            orders = new ArrayList<POrderDR> ();
            for ( int i = 0 ; i < records.length () ; i ++ ) {

                emp = records.getJSONObject ( i );
                jComboBox4.addItem ( emp.get ( "order_no" ).toString () );
                orders.add ( new POrderDR ( Integer.parseInt ( emp.get ( "sales_po_id" ).toString () ) , emp.get ( "order_no" ).toString () , Integer.parseInt ( emp.get ( "so_customer_id" ).toString () ) ) );
            }
        }
        
        if ( jComboBox2.getItemCount () == 0 ) {
            addEmpAPICall = "employees";
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
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
            employees = new ArrayList<EmployeeDR> ();
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );
                jComboBox2.addItem ( emp.get ( "EMP_NAME" ).toString () );
                employees.add ( new EmployeeDR ( Integer.parseInt ( emp.get ( "EmployeePK" ).toString () ) , emp.get ( "EMP_NAME" ).toString () ) );
            }
        }
        

    }
    
    public void loadComboBoxes_old () {

        String queryOne     = "SELECT FG_ID, PART_NAME FROM finished_goods";
        String queryTwo     = "SELECT EmployeePK, EMP_NAME FROM employee";
        String queryThree  = "SELECT MCH_ID, MACHINE_NO FROM machine";
        String queryFour    = "SELECT PROCESS_ID, PROCESS_NAME FROM PROCESS_MASTER";

        ResultSet rs = null;
      
     //   jScrollPane2.setViewportView ( jPanel1);
        
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
            StaticValues.writer.writeExcel ( DailyReportFormEmployeeWise.class.getSimpleName () , DailyReportFormEmployeeWise.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }finally{
                 try {
                   rs.close();
               } catch ( Exception e ) {
                   StaticValues.writer.writeExcel ( DailyReportFormEmployeeWise.class.getSimpleName () , DailyReportFormEmployeeWise.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
               }
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
                jComboBox2.addItem ( rs.getString ( 2 ) );
            }
        
        } catch ( Exception e ) {
            System.out.println ( " Error 1 " + e.getMessage () );
            StaticValues.writer.writeExcel ( DailyReportFormEmployeeWise.class.getSimpleName () , DailyReportFormEmployeeWise.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }finally{
                 try {
                   rs.close();
               } catch ( Exception e ) {
                   StaticValues.writer.writeExcel ( DailyReportFormEmployeeWise.class.getSimpleName () , DailyReportFormEmployeeWise.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
               }
        }
        
    }

    public ActionListener productSelection = new ActionListener () {
        @Override
        public void actionPerformed ( java.awt.event.ActionEvent e ) {
            removeAnyOldComponents ();
            loadProcessesForProduct ();
            forms = new SingleProcessDE_FormEmployeeWise[ processes.size () ];
            loadDE_Label ();
            loadDE_Form ();
        }
    };    
    
    public void loadDE_Form(  ){
       
        String recordExists ;
        SingleProcessDE_FormEmployeeWise abc = null ;
        List<String> uni=new ArrayList<String>();
        
        int uomid=Integer.parseInt(products.get(jComboBox1.getSelectedIndex()).UOM);
        for(int j=0;j<UOM.size();j++)
        {
            if(uomid==UOM.get(j).UOM_ID)
            {
                  uni.add(UOM.get(j).UOM_NAME);
            }
        }                    
        for(int i=0; i<processes.size (); i++){
                     
                 //recordExists = "select * from dailyreport where batchno = '"+ jTextField1.getText ().trim ()  +"'  AND fgid = "+products.get(jComboBox1.getSelectedIndex ()).FG_ID+" AND processid = "+processes.get(i).PRC_ID;                    
                // ResultSet yesRecordExist = DB_Operations.executeSingle ( recordExists );
            abc = new SingleProcessDE_FormEmployeeWise();
            abc.setName("formno" + i);
            abc.setProcessID(processes.get(i).PRC_ID + "");
            abc.setProcessname(processes.get(i).PRC_NAME);

            // abc.setEmployee ( employees );
            abc.setMachine(machines);
            abc.setRejectionReasons(rej_reasons);
            abc.setUnit(UOM);
            abc.setUnits(uni);
            int pid = processes.get(i).PRC_ID;
             int FGID = products.get(jComboBox1.getSelectedIndex()).FG_ID;
            if (verifyFirstOrOnlyProcess(FGID, pid)) {
                try {
                    int RMID = batches.get(jComboBox3.getSelectedIndex()).RMID;
                   
                    int unitValue = DailyReportForm.getUnitNumber(RMID, FGID);
//                    System.err.println(unitValue);
                    String unit = "";
                    for (int j = 0; j < UOM.size(); j++) {
                        if (unitValue == UOM.get(j).UOM_ID) {
                            unit = UOM.get(j).UOM_NAME;
                        }
                    }
                    abc.setUni(unit);
                } catch (Exception ex) {
                    abc.setUni("NA");
                }
            }
                  /*   try{
                            if( yesRecordExist !=null ){
                                while(yesRecordExist.next ()){
                                 System.out.println( "QIn : "+yesRecordExist.getString(11)+ " QOut : "+yesRecordExist.getString(12)+ " Rejected : "+yesRecordExist.getString(13)  ); 
                                 abc.setQIn ( yesRecordExist.getString(11)  );
                                 abc.setQout ( yesRecordExist.getString(12) );
                                 abc.setRejected (yesRecordExist.getString(13) );
                                }
                            }
                    }catch(SQLException e){
                    }  */
                   // abc.setBounds( 50, 120 +(60*(i)), 1250, 60 );
//                    add (abc);
//                    revalidate();
//                    repaint();

                    panel.add (abc);

                    forms[i] = abc ;
               //     System.out.println( "Total components now "+getComponentCount () );
                }
                 
//                jButton1.setBounds ( 1110, (forms.length*60+130), 77, 35);  
//                jButton2.setBounds ( 1200, (forms.length*60+130), 77, 35);
//                jScrollPane1.setBounds( 50, (forms.length*60+175), 1250, 250 );
                
                panel.setBackground ( Color.WHITE );
                panel.setBounds ( 0 , 0 , 1240 , 270 );
                panel.revalidate ();
                panel.repaint ();

                add ( scroll , BorderLayout.CENTER );
        
                    revalidate();
                    repaint();
                 
        if( forms.length >1 ){
            processRowsPresent = true;
        }
    }
    
    public void removeAnyOldComponents(){
       
        if( processRowsPresent ){
            for(int i=0; i<processes.size (); i++){

                    panel.remove ( panel.getComponentCount () - 1 );
                panel.revalidate ();
                panel.repaint ();
                    System.out.println( "Remove item at  "+getComponentCount () );
        }
            
            processRowsPresent = false ;
        }
        
    } 
           
    public void loadDE_Label( ){
        
                SingleProcessDE_LabelsEmpWise abc = new SingleProcessDE_LabelsEmpWise();
                abc.setBounds(30 , 50 , 1280 , 35  );
                add(abc);
                    
                revalidate ();
                repaint ();
    }
    
    public void loadEntries(){
        
        try {
            ResultSet result = DB_Operations.executeSingle ("select showrdate as 'From Date', starttime as 'From Time', showrtdate as 'To Date',  stoptime as 'To Time', (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product', (select MACHINE_NO from machine where MCH_ID in (machineid)) as 'Machine', (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process',  qtyin as 'Qty In', qtyout as 'Qty Out', rejection as 'Rejected', (select EMP_NAME from employee where EmployeePK in (empid)) as 'Employee' from dailyreport");
            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
            if(result!=null )
                jTable1.setModel(buildTableModel(result));

        } catch (SQLException e) {
                StaticValues.writer.writeExcel ( DailyReportFormEmployeeWise.class.getSimpleName () , DailyReportFormEmployeeWise.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }
    }
    
    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

        /* report query                 */
        
        // date wise 
        // select distinct(select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , rdate, qtyin, qtyout, rejection, (qtyin-qtyout) as 'Production for day' from dailyreport where rdate = '2-06-2018'
        
        // date wise for combined processes
        //select rdate, sum(qtyin), sum(qtyout), sum(rejection), sum(qtyin-qtyout) as 'Balence Qty' from dailyreport where rdate = '2-06-2018' order by processid 
        
        // date wise, selected process
        //select distinct(select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , rdate, qtyin, qtyout, rejection, (qtyin-qtyout) as 'Balence Qty' from dailyreport where processid = 1 order by processid 
        
        //  selected process
        //select distinct(select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , SUM(qtyin), SUM(qtyout), SUM(rejection), SUM(qtyin-qtyout) as 'Balence Qty' from dailyreport where processid = 1
     
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1120, 500));
        setPreferredSize(new java.awt.Dimension(1310, 600));
        setLayout(null);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Add");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(1140, 370, 77, 32);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Close");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(1230, 370, 77, 32);

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
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(30, 410, 1280, 190);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
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
        jComboBox1.setBounds(280, 20, 130, 30);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Select Product");
        add(jLabel1);
        jLabel1.setBounds(280, 0, 90, 20);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Batch NO");
        add(jLabel4);
        jLabel4.setBounds(150, 0, 51, 20);

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(140, 20, 2, 30);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("OA Number");
        add(jLabel5);
        jLabel5.setBounds(20, 0, 100, 20);
        add(jTextField2);
        jTextField2.setBounds(10, 20, 2, 30);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Select Employee");
        add(jLabel2);
        jLabel2.setBounds(420, 0, 130, 20);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox2MouseClicked(evt);
            }
        });
        add(jComboBox2);
        jComboBox2.setBounds(420, 20, 140, 30);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jComboBox3);
        jComboBox3.setBounds(150, 20, 120, 30);

        jComboBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jComboBox4);
        jComboBox4.setBounds(20, 20, 120, 30);
    }// </editor-fold>//GEN-END:initComponents

    ArrayList<DailyDataEntryModel> dataList;
    
     public int iDFromJSON ( String json ) {
        int id = 0;

        JSONObject jObject = new JSONObject ( json );

        JSONObject value = ( JSONObject ) jObject.get ( "data" );

        id = Integer.parseInt ( value.get ( "insert_id" ).toString () );
        //    id = Integer.parseInt( st.getString ("insert_id" ));

        return id;
    }
    
    
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:

        String orderDetails = null ;
        String customerId = null;
                if( ! jTextField2.getText().trim().equals ( "" ) ){
                    orderDetails =     jTextField2.getText().trim()  ;
                    ResultSet rs = DB_Operations.executeSingle ( "select * from sales_orders where order_no = '"+orderDetails+"'") ;
                    try{
                        if(rs!=null && rs.isBeforeFirst ()){
                                customerId = rs.getInt( "so_customer_id" ) + "";
                        }
                    }catch(SQLException e){
                        customerId = "0";
                        System.out.println ( ""+e.getMessage () );
                        StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }finally{
                        try{
                            rs.close();
                        }catch(Exception e2){
                            System.out.println ( ""+e2.getMessage () );
                            StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                        }
                    }
                }
        
        
        DailyDataEntryModel data ;
        
        incompleteDataDialog = 0;
        
        SingleProcessDE_FormEmployeeWise abc1 = null;
        String abc = "" ;
        dataList = new ArrayList<DailyDataEntryModel>();
        
        String rejectionData = "";
        
        for( int i=0; i<forms.length; i++ ){
            
            abc1 = forms[i];
            abc1.fromTime ();
            abc1.toTime ();

             abc1.formatFromDate ( );
             abc1.formatToDate () ;
          //  try{
                    if( checkIfEmpty ( abc1.getQIn (), abc1.fromHr, abc1.fromMin, abc1.toHr, abc1.toMin )){
            
                        incompleteDataDialog = 1;
                        
                        data = new DailyDataEntryModel();
                        data.setPFromDate ( abc1.getRDate () );
                        data.setPToDate ( abc1.getRTDate () );
                        data.setFromDate ( abc1.getFromDate () );
                        data.setToDate ( abc1.getToDate ()  );
                        data.setMachine ( abc1.getMachine () );
                        data.setProcessID (abc1.getProcessID ());
                        data.setProcessname (abc1.getProcessname ());
                        data.setFromTime ( abc1.getFromTime () );
                        data.setToTime ( abc1.getToTime ()  );
                        data.setQIn ( abc1.getQIn () );
                        data.setQout (abc1.getQout ());
                        data.setRejected (abc1.getRejected ());
                       // data.setEmployee ( abc1.getEmployee () );
                        data.setRejReason (abc1.getRejectionReason ());
                        data.setPFromTime ( abc1.fromTime () );
                        data.setPToTime ( abc1.toTime () );
                        data.setUomIn(abc1.getUOMIn());
                        data.setUomOut(abc1.getUOMOut());
                        data.setUomOutRej(abc1.getUOMOutRej());
                        
                        data.setShiftData ( Integer.parseInt ( abc1.getShift () ) );
                
                
                data.setTotalTimeInHours (    data.getTotalTimeInHours ());
                
                data.setTimeLossData ( abc1.getTimeLossReasons () );
                totalTimeLossInMinutes = 0 ;
                for(  int j=0 ;  j<abc1.getTimeLossReasons ().size(); j++ ){ 
                        //timelossData = timelossData+"  ,  " + abc1.getTimeLossReasons ().get(j).getMinutes () +"  -  " + abc1.getTimeLossReasons ().get(j).getReason ()+"  \n  " ;
                        totalTimeLossInMinutes = totalTimeLossInMinutes + abc1.getTimeLossReasons ().get(j).getMinutes () ;
                }

                data.setTotalTimeLossInMinutes ( totalTimeLossInMinutes );
                data.setTotalTimeLossInHours ( data.getTotalTimeLossInHours (  ) ) ;
                
                data.setRejectionData (  abc1.getRejectionReasons ()  );
                if( abc1.getRejectionReasons ().size() >0 ){
                    for(  int j=0 ;  j<abc1.getRejectionReasons ().size()  ; j++ ){ 
                        rejectionData = rejectionData+"  ,  " + abc1.getRejectionReasons ().get(j).getRejectionReasonId ()+"  -  " + abc1.getRejectionReasons ().get(j).getRejectionReasonStr ()+"  -  " + abc1.getRejectionReasons ().get(j).getRejectionForReason ()+"  \n  " ;
                    }
                }
                
                        dataList.add(data);
                }
           // }catch( NullPointerException e){
           //        JOptionPane.showMessageDialog ( null, "Exception :  "+e.getMessage ());
           // }
        }
        
        // save the daily data entry report for every process in the datalist arraylist 
        for(int i=0; i<dataList.size(); i++){
            
            //String queryInsert = "insert into dailyreport ( rdate, rtdate,showrdate, showrtdate, fgid, machineid, processid, starttime, stoptime, qtyin, qtyout, rejection, empid, showFromTime, showToTime, batchno, rej_reason, shift_id , actual_min ,  total_min ,  actual_hours , total_hours  , customer_id ) values ( '" + dataList.get(i).getPFromDate ()  +"','"  + dataList.get(i).getPToDate (  ) + "','" + dataList.get(i).getFromDate () +"','" +                                                                                                                                                                          dataList.get(i).getToDate () +  "','" +                                                     products.get ( jComboBox1.getSelectedIndex () ).FG_ID +  "','" +                                                                machines.get ( dataList.get(i).getMachine ()).MC_ID+ "',  '" +                                                                          dataList.get(i).getProcessID () + "', '" +                                                          dataList.get(i).getFromTime () + "', '" +                                                       dataList.get(i).getToTime () + "',  "  +                                                    dataList.get(i).getQIn () + ", " +                                                          dataList.get(i).getQout () +  ", "+                                                             dataList.get(i).getRejected ()+ ",   " +                                                        employees.get ( jComboBox2.getSelectedIndex () ).EMP_ID + ",'" +                                                                                dataList.get(i).getPFromTime ()+ "', '" +                                                                       dataList.get(i).getPToTime ()  + "',  '"+                                                           jTextField1.getText ().trim ()+"', " +                                                                   rej_reasons.get(dataList.get(i).getRej_Reason ()).REJ_ID + ", "+                                                               dataList.get(i).getShiftID ()+","+                                                                          (dataList.get(i).getTotalTimeInMinutes () - dataList.get(i).getTotalTimeLossInMinutes ())+ ","+                                                             dataList.get(i).getTotalTimeInMinutes () +","+                                                                      (dataList.get(i).getTotalTimeInHours ()-dataList.get(i).getTotalTimeLossInHours ( )) +","+                                                                      dataList.get(i).getTotalTimeInHours () +",'"+                                                                   customerId+"')";
            //DB_Operations.executeInsertRandom ( queryInsert) ;
            
            String fromTime = dataList.get ( i ).getPFromDate ().replace( " 00:00:00", " "+dataList.get ( i ).getFromTime ()  ); 
            String toTime = dataList.get ( i ).getPToDate ().replace( " 00:00:00", " "+dataList.get ( i ).getToTime ()  );
            
            Date d1, d2;
                try{
                    d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse( fromTime );
                    d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse( toTime );
                    
                    if( d1.getTime() < d2.getTime() ){
            
                    try{
                        String addEmpAPICall =   "dailyreportadd?rdate="+URLEncoder.encode( dataList.get ( i ).getPFromDate ()+" 00:00:00", "UTF-8")+"&rtdate="+URLEncoder.encode(dataList.get ( i ).getPToDate ()+" 00:00:00", "UTF-8")+"&showrdate="+URLEncoder.encode(dataList.get ( i ).getFromDate (), "UTF-8")+"&showrtdate="+URLEncoder.encode(dataList.get ( i ).getToDate () , "UTF-8")+"&fgid="+URLEncoder.encode(products.get ( jComboBox1.getSelectedIndex () ).FG_ID+"", "UTF-8")+"&machineid="+URLEncoder.encode(machines.get ( dataList.get ( i ).getMachine () ).MC_ID+"", "UTF-8")+"&processid="+URLEncoder.encode(dataList.get ( i ).getProcessID ()+"","UTF-8")+"&starttime="+URLEncoder.encode( fromTime  ,"UTF-8")+"&stoptime="+URLEncoder.encode( toTime ,"UTF-8")+"&qtyin="+URLEncoder.encode(dataList.get ( i ).getQIn (), "UTF-8")+"&qtyout="+URLEncoder.encode(dataList.get ( i ).getQout ()+"","UTF-8")+"&rejection="+URLEncoder.encode(  dataList.get ( i ).getRejected ()  +"", "UTF-8")+"&empid="+URLEncoder.encode(employees.get ( jComboBox2.getSelectedIndex () ).EMP_ID+"", "UTF-8")+"&showFromTime="+URLEncoder.encode(  fromTime+":00" , "UTF-8")+"&showToTime="+URLEncoder.encode( toTime+":00"  , "UTF-8")+"&batchno="+URLEncoder.encode( batches.get ( jComboBox3.getSelectedIndex () ).BTC_ID + "", "UTF-8")+"&rej_reason="+URLEncoder.encode(rej_reasons.get ( dataList.get ( i ).getRej_Reason () ).REJ_ID+"", "UTF-8")+"&shift_id="+URLEncoder.encode(dataList.get(i).getShiftID ()+"", "UTF-8")+"&actual_min="+URLEncoder.encode((dataList.get(i).getTotalTimeInMinutes () - dataList.get(i).getTotalTimeLossInMinutes ())+"", "UTF-8")+"&total_min="+URLEncoder.encode(dataList.get(i).getTotalTimeInMinutes ()+"", "UTF-8")+"&actual_hours="+URLEncoder.encode( (dataList.get(i).getTotalTimeInHours ()-dataList.get(i).getTotalTimeLossInHours ( )) +"", "UTF-8")+"&total_hours="+URLEncoder.encode( dataList.get(i).getTotalTimeInHours ()+"", "UTF-8")+"&customer_id="+URLEncoder.encode(customerId+"", "UTF-8")  ;
                        String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                        JOptionPane.showMessageDialog ( null , result2);

                        timeLossList =  dataList.get(i).selectedtimeLossData    ; 
                        rejectionList =  dataList.get(i).selectedtimeRejections  ;

                        int recordId = iDFromJSON ( result2 ) ;
                        for(  int j=0 ;  j<timeLossList.size(); j++ ){ 
                                totalTimeLossInMinutes = totalTimeLossInMinutes + timeLossList.get(j).getMinutes () ;
                                addEmpAPICall =  "timelossadd?ref_pID="+recordId+"&tlrID="+timeLossList.get(j).getId ()+"&tlQty="+timeLossList.get(j).getMinutes ()  ;
                                result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                        }

                        for ( int j = 0 ; j < rejectionList.size () ; j ++ ) {
                            //totalRejection = totalRejection + rejectionList.get ( j ).getRejectionForReason ();
                            addEmpAPICall = "rejdataadd?ref_pID=" + recordId + "&rrID=" + rejectionList.get ( j ).getRejectionReasonId () + "&rQty=" + rejectionList.get ( j ).getRejectionForReason ();
                            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                        }

                        int rmid = batches.get ( jComboBox3.getSelectedIndex () ).RMID;
                        int fgid = products.get ( jComboBox1.getSelectedIndex () ).FG_ID;

                        if(verifyFirstOrOnlyProcess( fgid , Integer.parseInt ( dataList.get ( i ).getProcessID ())))
                        {
                            try{
                                int unit=DailyReportForm.getUnitFrom(rmid, fgid);

                                int bal=( (Integer.parseInt ( dataList.get ( i ).getQIn () )*unit) - Integer.parseInt ( dataList.get ( i ).getQout () ) );
                                if(bal>0)
                                {
                                    String getWIPforSelectedData = "opwip2add?fgid=" + products.get ( jComboBox1.getSelectedIndex () ).FG_ID + "&processid=" + dataList.get ( i ).getProcessID () + "&empid=" + employees.get ( jComboBox3.getSelectedIndex () ).EMP_ID + "&machine=" + machines.get ( jComboBox2.getSelectedIndex () ).MC_ID + "&ORDERNO=" + orders.get ( jComboBox2.getSelectedIndex () ).ORD_ID + "&FINISHED=" + dataList.get ( i ).getQout () + "&MOVEQTY=" + dataList.get ( i ).getQIn () + "&MOVEDATE=" + URLEncoder.encode ( dataList.get ( i ).getPFromDate () + " 00:00:00" , "UTF-8" ) + "&BALANCE=" + bal;
                                    WebAPITester.prepareWebCall ( getWIPforSelectedData , StaticValues.getHeader () , "" );
                                }
                            }catch(Exception ex){}
                        }else{
                            int bal=( Integer.parseInt ( dataList.get ( i ).getQIn () ) - Integer.parseInt ( dataList.get ( i ).getQout () ) );
                            if(bal>0)
                            {
                                String getWIPforSelectedData = "opwip2add?fgid=" + products.get ( jComboBox1.getSelectedIndex () ).FG_ID + "&processid=" + dataList.get ( i ).getProcessID () + "&empid=" + employees.get ( jComboBox3.getSelectedIndex () ).EMP_ID + "&machine=" + machines.get ( jComboBox2.getSelectedIndex () ).MC_ID + "&ORDERNO=" + orders.get ( jComboBox2.getSelectedIndex () ).ORD_ID + "&FINISHED=" + dataList.get ( i ).getQout () + "&MOVEQTY=" + dataList.get ( i ).getQIn () + "&MOVEDATE=" + URLEncoder.encode ( dataList.get ( i ).getPFromDate () + " 00:00:00" , "UTF-8" ) + "&BALANCE=" + bal;
                                WebAPITester.prepareWebCall ( getWIPforSelectedData , StaticValues.getHeader () , "" );
                            }
                        }
                        //addToStores ( products.get ( jComboBox1.getSelectedIndex () ).FG_ID ,  Integer.parseInt( dataList.get ( i ).getProcessID () ), Integer.parseInt( dataList.get ( i ).getQout ()),  Integer.parseInt(  dataList.get ( i ).getRejected ()));
                        addToStores ( products.get ( jComboBox1.getSelectedIndex () ).FG_ID , Integer.parseInt ( dataList.get ( i ).getProcessID () ) , Integer.parseInt ( dataList.get ( i ).getQout () ) , Integer.parseInt ( dataList.get ( i ).getRejected () )  ,  dataList.get ( i ).getPFromDate ()+" 00:00:00" );            
                    }catch(UnsupportedEncodingException e){

                    }

                    loadEntries (); 
                }else{

                     JOptionPane.showMessageDialog( null, "Incorrect From Time and To Time selected. Please correct the input and try again "  )  ;
                 }

             }catch( ParseException e  ){

             } 
        }
        
        // save the required details from datalist arraylist into op_wip2 for every process
            int QInSUm = -1, QOutSum = -1;
            for(int i=0; i<dataList.size(); i++){
             ResultSet yesRecordExist = null;
                try{
                String recordExists ;
                boolean recordExist = false;
                recordExists = "select * from op_wip2 where  PRODUCT = "+products.get(jComboBox1.getSelectedIndex ()).FG_ID+" AND PROCESS_STAGE = "+dataList.get(i).getProcessID ()+" AND machine = "+machines.get(dataList.get(i).getMachine ()).MC_ID + " AND empid="+employees.get( jComboBox2.getSelectedIndex () ).EMP_ID;
                yesRecordExist = DB_Operations.executeSingle ( recordExists );

                if( yesRecordExist.isBeforeFirst ()){
                    recordExist = true ;
                    try {
                        yesRecordExist.close ();
                        
                    } catch ( Exception ex ) {      
                        StaticValues.writer.writeExcel ( DailyReportFormEmployeeWise.class.getSimpleName () , DailyReportFormEmployeeWise.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }
                }
                
                String getAccumulatedEntries = "select SUM(qtyin) as 'Qty In', SUM(qtyout) as 'Qty Out' from dailyreport where fgid="+products.get(jComboBox1.getSelectedIndex ()).FG_ID+" AND processid="+dataList.get(i).getProcessID ()+" AND machineid = "+machines.get(dataList.get(i).getMachine ()).MC_ID+" AND empid = "+employees.get( jComboBox2.getSelectedIndex () ).EMP_ID+" group by fgid" ; 
                yesRecordExist = DB_Operations.executeSingle ( getAccumulatedEntries);
                if( yesRecordExist.isBeforeFirst ()){
                        QInSUm = yesRecordExist.getInt(1);
                        QOutSum = yesRecordExist.getInt(2);
                }
                yesRecordExist.close ();

                String queryInsert = null;
            
                if(recordExist){
                        
                        //queryInsert = "update op_wip2 set  FINISHED = " + dataList.get(i).getQout () + ", MOVEQTY = " + dataList.get(i).getQIn () + ",  BALANCE = "+ (Integer.parseInt(dataList.get(i).getQIn ()) - Integer.parseInt( dataList.get(i).getQout ())) +" where  PRODUCT = "+products.get(jComboBox1.getSelectedIndex ()).FG_ID+" AND PROCESS_STAGE = "+processes.get(i).PRC_ID+" AND machine = "+machines.get(i).MC_ID + " AND empid="+employees.get(i).EMP_ID ;
                        queryInsert = "update op_wip2 set  FINISHED = " + QOutSum+ ", MOVEQTY = " + QInSUm+ ",  BALANCE = "+ (QInSUm - QOutSum) +" where  PRODUCT = "+products.get(jComboBox1.getSelectedIndex ()).FG_ID+" AND PROCESS_STAGE = "+dataList.get(i).getProcessID ()+" AND machine = "+machines.get(dataList.get(i).getMachine ()).MC_ID + " AND empid="+employees.get( jComboBox2.getSelectedIndex () ).EMP_ID ;
                        DB_Operations.executeInsertRandom ( queryInsert) ;
                }else{
                        queryInsert = "insert into op_wip2 ( PRODUCT, REJECTED, REASON, FINISHED, MOVEQTY, MOVEDATE, ORDERNO, PROCESS_STAGE, BALANCE, REJECTIONDATE, DRID, machine, empid  ) values ( " + products.get ( jComboBox1.getSelectedIndex () ).FG_ID  +","  + dataList.get(i).getRejected () + ",' NA ', " + dataList.get(i).getQout () +  "," +  dataList.get(i).getQIn () +  ",'" +  dataList.get(i).getPFromDate ()+ "',  'NA', " + dataList.get(i).getProcessID () + ", (" + (Integer.parseInt(dataList.get(i).getQIn ()) - Integer.parseInt( dataList.get(i).getQout ()))   + "),   'NA'," + 0 + ", " + machines.get ( dataList.get(i).getMachine ()).MC_ID + ","+employees.get( jComboBox2.getSelectedIndex () ).EMP_ID+" )";
                        DB_Operations.executeInsertRandom( queryInsert) ;
                }                               
                removeAnyOldComponents() ;
                loadDE_Form ();           
             }catch( Exception e ){
                System.out.println("Exception in op_wip2 "+e.getMessage ());
                StaticValues.writer.writeExcel ( DailyReportFormEmployeeWise.class.getSimpleName () , DailyReportFormEmployeeWise.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
             }            
        }                 
    }//GEN-LAST:event_jButton1MouseClicked

    public void addToStores ( int productId , int processId , int quot , int rej   , String date) {

        // check if the qty is processed from last process
        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );

        int OPENING = 0;
        int RECEIVED = 0, USED = 0;
        int CLOSING = 0;

        int OldOPENING = 0;
        int OldCLOSING = 0;
        boolean recordExist = false;

        String addEmpAPICall = "latestfgstock?fgid=" + productId;
        String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

        try {

            if (  ! result2.contains ( "\"101\":\"Report record\\/s not found.\"" ) &&  ! result2.contains ( "\"OPENING\":null,\"RECEIVED\":null,\"USED\":null,\"CLOSING\":null" ) ) {

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

                if ( records.length () > 0 ) {
                    recordExist = true;
                    OPENING = Integer.parseInt ( records.getJSONObject ( 0 ).get ( "OPENING" ).toString () );
                    RECEIVED = Integer.parseInt ( records.getJSONObject ( 0 ).get ( "RECEIVED" ).toString () );
                    USED = Integer.parseInt ( records.getJSONObject ( 0 ).get ( "USED" ).toString () );
                    OldCLOSING = Integer.parseInt ( records.getJSONObject ( 0 ).get ( "CLOSING" ).toString () );
                    OldOPENING = Integer.parseInt ( records.getJSONObject ( 0 ).get ( "OPENING" ).toString () );

                } else {
                    System.out.println ( "No result to show" );
                    recordExist = false;
                    OPENING = 0;
                    RECEIVED = 0;
                    USED = 0;
                    CLOSING = 0;
                }

                OPENING = OldCLOSING;

                RECEIVED = quot - rej;
                USED = 0;
                CLOSING = OPENING + RECEIVED;

                if ( verifyLastProcess ( productId , processId ) ) {
                    String updateStock = "fgstockadd?FG_ID=" + productId + "&FG_ITEM_ID=" + productId + "&OPENING=" + OPENING + "&RECEIVED=" + RECEIVED + "&USED=" + USED + "&CLOSING=" + CLOSING + "&CREATED_ON=" + URLEncoder.encode ( new SimpleDateFormat ( "yyyy-mm-dd hh:mm:ss" ).format ( Calendar.getInstance ().getTime () ) , "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( new SimpleDateFormat ( "yyyy-mm-dd hh:mm:ss" ).format ( Calendar.getInstance ().getTime () ) , "UTF-8" ) + "&EDITED_BY=" + Proman_User.getEID ();
                    result2 = WebAPITester.prepareWebCall ( updateStock , StaticValues.getHeader () , "" );
                    JOptionPane.showMessageDialog ( null , result2 );
                }

                if ( verifyFirstOrOnlyProcess ( productId , processId ) ) {

                    ////////////////////////////////////////////////    START   -     Code to reduce raw material according to parts created and added to FG Stock   -  START //////////////////////////////////////////////////////////////////////////////                                    
                    BatchProcessing batch = new BatchProcessing ();
                      //temp changes
                    RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( batches.get ( jComboBox3.getSelectedIndex () ).RMID , batches.get ( jComboBox3.getSelectedIndex () ).FGQty , Double.parseDouble(""+quot) , batches.get ( jComboBox3.getSelectedIndex () ).BTC_ID ,Double.parseDouble(dataList.get ( 0 ).getQIn ()),Integer.parseInt(dataList.get ( 0 ).getQIn ())  , date ) ;
                    ////////////////////////////////////////////////    END       -     Code to reduce raw material according to parts created and added to FG Stock   -  END          //////////////////////////////////////////////////////////////////////////////  
                }

            } else {

                OPENING = 0;

                RECEIVED = quot - rej;
                USED = 0;
                CLOSING = OPENING + RECEIVED;

                if ( verifyLastProcess ( productId , processId ) ) {

                    addEmpAPICall = "fgstockadd?FG_ID=" + productId + "&FG_ITEM_ID=" + productId + "&OPENING=" + OPENING + "&RECEIVED=" + RECEIVED + "&USED=" + USED + "&CLOSING=" + CLOSING + "&CREATED_ON=" + URLEncoder.encode ( new SimpleDateFormat ( "yyyy-mm-dd hh:mm:ss" ).format ( Calendar.getInstance ().getTime () ) , "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( new SimpleDateFormat ( "yyyy-mm-dd hh:mm:ss" ).format ( Calendar.getInstance ().getTime () ) , "UTF-8" ) + "&EDITED_BY=" + Proman_User.getEID ();
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    JOptionPane.showMessageDialog ( null , result2 );
                }

                if ( verifyFirstOrOnlyProcess ( productId , processId ) ) {

                    ////////////////////////////////////////////////    START   -     Code to reduce raw material according to parts created and added to FG Stock   -  START //////////////////////////////////////////////////////////////////////////////                                    
                    BatchProcessing batch = new BatchProcessing ();
                    //temp changes
                    RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( batches.get ( jComboBox3.getSelectedIndex () ).RMID , batches.get ( jComboBox3.getSelectedIndex () ).FGQty ,Double.parseDouble(""+ quot) , batches.get ( jComboBox3.getSelectedIndex () ).BTC_ID,Double.parseDouble(dataList.get ( 0 ).getQIn ()) ,Integer.parseInt(dataList.get ( 0 ).getQIn ())  , date   );
                    ////////////////////////////////////////////////    END       -     Code to reduce raw material according to parts created and added to FG Stock   -  END          //////////////////////////////////////////////////////////////////////////////  
                }

            }
        } catch ( UnsupportedEncodingException e ) {
            System.out.println ( "" + e.getMessage () );
        }

    }

    String addEmpAPICall, result2 ;
    boolean RMStockUpdatedOnfirstProcess = false;

    public boolean verifyLastProcess ( int productId , int processId ) {

        int lastProcess = 0;

        addEmpAPICall = "processmachmaps?FG_ID=" + productId;
        result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
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

        JSONObject emp = records.getJSONObject ( records.length () - 1 );
        lastProcess = Integer.parseInt ( emp.get ( "REF_PROCESS_ID" ).toString () );

        if ( processId == lastProcess ) {
            return true;
        } else {
            return false;
        }

    }

    public boolean verifyFirstOrOnlyProcess ( int productId , int processId ) {

        int lastProcess = 0;

        addEmpAPICall = "processmachmaps?FG_ID=" + productId;
        result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
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

        JSONObject emp = records.getJSONObject ( 0 );
        lastProcess = Integer.parseInt ( emp.get ( "REF_PROCESS_ID" ).toString () );

        if ( processId == lastProcess ) {
            return true;
        } else {
            return false;
        }

    }

    
    
    public boolean checkIfEmpty( String testVal , int a, int b, int c, int d ){
     
        boolean formcomplete = true ;
        
        if( testVal.equalsIgnoreCase ( "" ) || testVal.equalsIgnoreCase ( "0" )  ){
            formcomplete =  false ;
        }
        
//        if( a==0 ){
//            if(b==0){
//             formcomplete = false ;
//            }
//        }
//        
//        
//        if( c==0 ){
//            if(d==0){
//                formcomplete = false ;
//            }
//        }
        
        if(!formcomplete){
            showIncompleteFormDialog ("<html><b>Incomplete form.</b> <br>Please fill atleast one process data with fromtime, totime and production details</html>");
        }
        
        return formcomplete ;
} 

    public void showIncompleteFormDialog(String message){
        
        if(incompleteDataDialog==0){
            incompleteDataDialog = 1 ;
            JOptionPane.showMessageDialog ( null, message);
        }
    }
    
    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
if(jComboBox1.getItemCount()==0){
    JOptionPane.showMessageDialog(null, "<html><size='06'>First add product from Processes Master ");
}             // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
HomeScreen.home.removeForms ();
                
    //    removeAnyOldComponents ();
        
    }//GEN-LAST:event_jButton2MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        
//        removeAnyOldComponents();
//        loadProcessesForProduct ();
//        forms = new SingleProcessDE_FormEmployeeWise[ processes.size () ];
//        
//        loadDE_Label ();
//        loadDE_Form (  );
            
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
if(jComboBox2.getItemCount()==0){
    JOptionPane.showMessageDialog(null, "<html><size='06'>First add Employee from Employee Master ");
}             // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2MouseClicked

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1FocusLost

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

}


