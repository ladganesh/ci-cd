/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import trixware.erp.prodezydesktop.Utilities.DateTimeFormatter;
import datechooser.view.WeekDaysStyle;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Derived.AutoCompletion;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.RawMaterialDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Model.UOMDR;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class BatchProcessing extends javax.swing.JPanel {

    
    ArrayList<RawMaterialDR> RMList = null;
    ArrayList<String[]> RMListQtys = null;
    ArrayList<String[]> AllRMList = null;
    ArrayList<String[]> AllRMStockList = null;
    ArrayList<String[]> BatchList = null;
    
    RawMaterialDR rawm = null  ;
    
    RawMaterialDR rm = null;

    ArrayList<UOMDR> UOMList = null;
    UOMDR uom = null;

    DefaultListModel listModel = new DefaultListModel ();
    boolean newRM = false, newBatch = true;

    double OPENING = 0;
    double RECEIVED = 0, USED = 0;
    double CLOSING = 0;

    double OldOPENING = 0;
    double OldCLOSING = 0;

    double OPENING_CNT = 0;
     double RECEIVED_CNT = 0, USED_CNT = 0;
    double CLOSING_CNT = 0;
    boolean recordExist = false;
    String strDate2 = null;
    SimpleDateFormat sdf2 = null;
    ArrayList<String[]> RM_IDs = new ArrayList<String[]>();
    String resource = "" , resourceType = "" ;
    
    String addEmpAPICall  , result2 , result3;
    
    public void writeErrorLogs( String className, String filename, String exceptionName, String lineNo, String msg, String datetime ) {
        StaticValues.writer.writeExcel ( className , filename , exceptionName , lineNo , msg, datetime );
    }
    
    /**
     * Creates new form BatchProcessing
     */
    public BatchProcessing () {
        initComponents ();

        AutoCompletion.enable ( jComboBox1 );
        AutoCompletion.enable ( jComboBox2 );
        AutoCompletion.enable ( jComboBox3 );

        loadContent ();

        resourceType = "Masters" ;
        resource = "BatchProcessing" ;
//        try{
//            StaticValues.checkUserRolePermission(resourceType, resourceType);
//        }catch(Exception e){
//            StaticValues.writer.writeExcel (resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }
        
        sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
        
        dateChooserCombo2.setFormat ( DateFormat.MEDIUM );
        dateChooserCombo2.setWeekStyle ( WeekDaysStyle.NORMAL );
        dateChooserCombo2.setOpaque ( false );
        dateChooserCombo2.setBackground ( Color.WHITE );
        dateChooserCombo2.revalidate ();
        dateChooserCombo2.repaint ();        

        jButton1.setEnabled ( false );
        
        jComboBox4.setEnabled ( false );
    }

    public void loadContent () {

        ResultSet rs = null;

        
        
        if ( jComboBox1.getItemCount () == 0 ) {
                    //result = DB_Operations.getMasters ( "raw_material_type" );
                   
                    addEmpAPICall = "rawmaterials";
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    System.err.println(result2);
                    if( ! result2.contains(  "not found")   ){
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
                        RMList = new ArrayList<RawMaterialDR> ();
                        
                        for ( int i = 0 ; i < records.length () ; i ++ ) {

                            emp = records.getJSONObject ( i);
                            jComboBox1.addItem ( emp.get ( "RM_CLASS" ).toString () );
                            RMList.add( new RawMaterialDR ( Integer.parseInt(emp.get ( "RM_ID" ).toString ()), emp.get ( "RM_CLASS" ).toString ()  )); 
                        
                        }
                    }
                    //jLabel6.setText(  rawMaterials.get(jComboBox1.getSelectedIndex() ).UOM);
                    //jComboBox1.addActionListener ( productComboAction );
                }
        
        
        
        if ( jComboBox5.getItemCount () == 0 ) 
        {
                    //result = DB_Operations.getMasters ( "raw_material_type" );
                   
            addEmpAPICall = "batches";
            result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
            System.err.println(result2);
            if( ! result2.contains(  "not found")   ){
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
                BatchList = new ArrayList<String[]> ();
                for ( int i = 0 ; i < records.length () ; i ++ ) 
                {

                    emp = records.getJSONObject ( i);
                    int flag = emp.getInt("quality_flag");
                    if(flag == 1)
                    {
                    jComboBox5.addItem ( emp.get ( "BatchName" ).toString () );
                   
                    
//                     * ref_rm_id         = raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] 
//                     * inward_uid        = tkInId 
//                     * purchase_rate =  prate;    required
                    
//                      int rmId ,   
//                      int factor,                    1 
//                      double qty,                     
//                      int ref_batch_id            required

                    BatchList.add( new String[] { emp.get ( "BatchId" ).toString (), emp.get ( "BatchName" ).toString () ,emp.get ( "purchase_rate" ).toString (), emp.get ( "rm_inward_date" ).toString () }); 
                    }  
                }
            }
            //jLabel6.setText(  rawMaterials.get(jComboBox1.getSelectedIndex() ).UOM);
            //jComboBox1.addActionListener ( productComboAction );
        }
        

        try {
            jComboBox2.removeAllItems ();
            //rs = DB_Operations.getMasters ( "uom" );
            addEmpAPICall = "unitmeas";
            result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    
            if(  ! result2.contains( "not found"  )   ){        
                    
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

                    UOMList = new ArrayList<UOMDR> ();
                    //while ( rs.next () ) {
                    for ( int i = 0 ; i < records.length () ; i ++ ) {
                        emp = records.getJSONObject ( i);
                        uom = new UOMDR ();
                        uom.UOM_ID = Integer.parseInt (  emp.get ( "UOM_ID" ).toString () );
                        uom.UOM_NAME =  emp.get ( "UOM" ).toString () ;
                        UOMList.add ( uom );
                        jComboBox2.addItem ( emp.get ( "UOM" ).toString ());
                        jComboBox3.addItem ( emp.get ( "UOM" ).toString () );
                        jComboBox4.addItem ( emp.get ( "UOM" ).toString () );
                    }
            }
            //rs.close ();
        } catch ( Exception e ) {
            System.out.println ( " Error 1 " + e.getMessage () );
            writeErrorLogs (BatchProcessing.class.getSimpleName () , BatchProcessing.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );

        }

//        try {
//            //rs = DB_Operations.executeSingle ("select a.RM_ID, a.RM_NAME, a.RM_CTG, b.CLOSING from raw_material a inner join RMStock b where a.RM_ID = b.RMI_ID");
//            rs = DB_Operations.executeSingle ( "select a.RM_ID , a.RM_NAME as 'Name', a.RM_CTG as 'Category', b.CLOSING as 'Available', a.REORDER_LEVEL as 'Reorder Level'  from raw_material a inner join RMStock b where a.RM_ID = b.RMI_ID  group by a.RM_ID" );
//            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
//            if ( rs != null ) {
//
//                jTable2.setModel ( buildTableModel ( rs ) );
//
//                jTable2.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
//                jTable2.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
//
//            }
//        } catch ( SQLException e ) {
//            System.out.println ( e.getClass () + "  " + e.getMessage () );
//            writeErrorLogs (BatchProcessing.class.getSimpleName () , BatchProcessing.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
//        }
             addEmpAPICall = "rawmaterials";
             result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
             

            if ( result2 != null && ! result2.contains(  "not found") ) {
                jTable2.setModel ( buildTableModelfromJSON_RMAndStock (result2 ) );
                jTable2.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                jTable2.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
            }




//        try {
//            //rs = DB_Operations.executeSingle ("select a.RM_ID, a.RM_NAME, a.RM_CTG, b.CLOSING from raw_material a inner join RMStock b where a.RM_ID = b.RMI_ID");
//                rs = DB_Operations.executeSingle ( "select BatchName as 'Name', BatchQty as 'Quantity',  (select UOM from unit_meas where UOM_ID in (BatchUOM_ID)) as 'Unit' from BATCH_MASTER " );
//            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
//            if ( rs != null ) {
//
//                jTable1.setModel ( buildTableModel2 ( rs ) );
//
//            }
//        } catch ( SQLException e ) {
//            System.out.println ( e.getClass () + "  " + e.getMessage () );
//            writeErrorLogs (BatchProcessing.class.getSimpleName () , BatchProcessing.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }

             addEmpAPICall = "batches";
             result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

            if ( result2 != null  && ! result2.contains(  "not found")  ) {
                jTable1.setModel ( buildTableModelfromJSON_Batches ( result2 ) );
                jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
            }

    }
    
    
        public static DefaultTableModel buildTableModelfromJSON_Batches ( String employeeJSONList ) {

        Vector<String> columnNames = new Vector<String> ();
        int columnCount = 0;
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();

        JSONArray jARR = null;
        JSONObject jOB = null;

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
        columnNames.add ( "BatchId" );
        columnNames.add ( "BatchName" );
        columnNames.add ( "BatchQty" );
        columnNames.add ( "BatchCreateDate" );
        columnNames.add ( "BatchExpireDate" );
        

        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
            Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {
                emp = records.getJSONObject ( i );
                
                
                //if( emp.get ( columnNames.get ( columnIndex ) ).toString ().equals ( "BatchCreateDate") || emp.get ( columnNames.get ( columnIndex ) ).toString ().equals ( "BatchExpireDate") ){
                //if(  columnIndex == 3 || columnIndex == 4 ){
                //    vector.add (     new SimpleDateFormat("MMM dd, yyyy").format(emp.get ( columnNames.get ( columnIndex ) )    ));
                //}else{
                    vector.add (   emp.get ( columnNames.get ( columnIndex ) ) );
                //}
                
            }
            data.add ( vector );
        }
        return new DefaultTableModel ( data , columnNames );
    }
    
        
         public  DefaultTableModel buildTableModelfromJSON_RMAndStock ( String employeeJSONList ) {

        Vector<String> columnNames = new Vector<String> ();
        int columnCount = 0;
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        Vector<Object> tableValues = new Vector<Object> ();

        JSONArray jARR = null;
        JSONObject jOB = null;

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
        columnNames.add ( "RM_ID" );
        columnNames.add ( "RM_TYPE" );
        columnNames.add ( "RM_CLASS" );
        columnNames.add ( "RM_NAME" );
        columnNames.add ( "REORDER_LEVEL" );
        columnNames.add ( "Available Stock" );
        //columnNames.add("RM_CLASS");
              
        
        
        RMListQtys = new ArrayList<String[]>() ;
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );

                tableValues = new Vector<Object> ();

                String[] values = new String[ columnNames.size () ];
                for ( int columnIndex = 0 ; columnIndex <columnNames.size ()-1 ; columnIndex ++ ) {

                    //values[columnIndex] = emp.get ( columnNames.get ( columnIndex ) ).toString () ;
                    tableValues.add(   emp.get ( columnNames.get ( columnIndex ) ).toString ()       ) ;

                }

                addEmpAPICall = "latestrwamstock?rmid="+emp.get ( "RM_ID" ).toString ()  ;
                String stockString = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

                JSONObject jObject2 = new JSONObject ( stockString );
                Iterator<?> keys2 = jObject2.keys ();

                HashMap<String , Object> map2 = new HashMap<String , Object> ();
                 while ( keys2.hasNext () ) {
                     String key = ( String ) keys2.next ();
                     Object value = jObject2.get ( key );
                     map2.put ( key , value );
                 }
                 JSONObject st2 = ( JSONObject ) map2.get ( "data" );
                 JSONArray records2 = st2.getJSONArray ( "records" );

                JSONObject emp2 = null;
                  for ( int j = 0 ; j < records2.length () ; j ++ ) {
                        emp2 = records2.getJSONObject ( j );
                        tableValues.add(  emp2.get ( "CLOSING" ).toString () );
                        RMListQtys.add(  new String[]{  emp.get ( "RM_ID" ).toString ()  ,   emp2.get ( "CLOSING" ).toString ()       }    ) ;
                 }

                    data.add ( tableValues );
             }
            
             return new DefaultTableModel ( data , columnNames );
        }
            
        
        
    
        
        public  DefaultTableModel buildTableModelfromJSON_RMAndStock_OLD ( String employeeJSONList , String stockString) {

        Vector<String> columnNames = new Vector<String> ();
        int columnCount = 0;
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();

        JSONArray jARR = null;
        JSONObject jOB = null;

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
        columnNames.add ( "RM_ID" );
        columnNames.add ( "RM_TYPE" );
        columnNames.add ( "RM_CLASS" );
        columnNames.add ( "RM_CTG" );
        columnNames.add ( "REORDER_LEVEL" );
              
        
        AllRMList = new ArrayList<String[]>();
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
            emp = records.getJSONObject ( i );
            String[] values = new String[ columnNames.size () ];
            for ( int columnIndex = 0 ; columnIndex <columnNames.size () ; columnIndex ++ ) {
                
                values[columnIndex] = emp.get ( columnNames.get ( columnIndex ) ).toString () ;
                
                
                
                
            }
            AllRMList.add(values );
        }
        
        
        jObject = new JSONObject ( stockString );
        keys = jObject.keys ();

        while ( keys.hasNext () ) {
            String key = ( String ) keys.next ();
            Object value = jObject.get ( key );
            map.put ( key , value );
        }
         st = ( JSONObject ) map.get ( "data" );
         records = st.getJSONArray ( "records" );
         
        columnNames = new Vector<String> ();
        columnNames.add ( "RMStock_TR_ID" );
        columnNames.add ( "RM_ITEM_ID" );
        columnNames.add ( "CLOSING" );
        
         
        AllRMStockList = new ArrayList<String[]>();
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
            emp = records.getJSONObject ( i );
            String[] values = new String[ columnNames.size () ];
            for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {
                values[columnIndex] = emp.get ( columnNames.get ( columnIndex ) ).toString () ;
            }
            AllRMStockList.add( values );
        }
        
        columnNames = new Vector<String> ();
        columnNames.add ( "RM ID" );
        columnNames.add ( "RM Name" );
        columnNames.add ( "RM Category" );
        columnNames.add ( "Available Qty" );
        columnNames.add ( "RM Reorder Level" );
        
        for ( int i = 0 ; i < AllRMList.size (); i ++ ) {
            Vector<Object> vector = new Vector<Object> ();
            for ( int j = 0 ; j < AllRMStockList.size (); j ++ ) {
            
                    if(  AllRMList.get(i)[0].equals ( AllRMStockList.get(j)[1] )    ){
                        
                     //   if(  Integer.parseInt(AllRMStockList.get(j)[1]) > Integer.parseInt(AllRMStockList.get(j+1)[1]) ){

                            vector.add (   AllRMList.get(i)[0] );
                            vector.add (   AllRMList.get(i)[2] );
                            vector.add (   AllRMList.get(i)[3] );
                            vector.add (   AllRMStockList.get(j)[2] );
                            vector.add (   AllRMList.get(i)[4] );
                   //     }
                    }
                //"select a.RM_ID , a.RM_NAME as 'Name', a.RM_CTG as 'Category', b.CLOSING as 'Available', a.REORDER_LEVEL as 'Reorder Level'  from raw_material a inner join RMStock b where a.RM_ID = b.RMI_ID  group by a.RM_ID"
            }
            if( vector.size () >0 ){
                data.add ( vector );
            }
        }
        
        return new DefaultTableModel ( data , columnNames );
    }

        
        
     public void loadContent2 () {

        ResultSet rs = null;

        try {
            jComboBox1.removeAllItems ();
            rs = DB_Operations.getMasters ( "raw_material" );
            RMList = new ArrayList<RawMaterialDR> ();
            while ( rs.next () ) {
                rm = new RawMaterialDR ();
                rm.RM_ID = Integer.parseInt ( rs.getString ( 1 ) );
                rm.RM_CLASS = rs.getString ( 3 );
                RMList.add ( rm );
                jComboBox1.addItem ( rs.getString ( 3 ) );
            }
            rs.close ();
        } catch ( Exception e ) {
            System.out.println ( " Error 1 " + e.getMessage () );
            writeErrorLogs (BatchProcessing.class.getSimpleName () , BatchProcessing.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        try {
            jComboBox2.removeAllItems ();
            rs = DB_Operations.getMasters ( "uom" );

            UOMList = new ArrayList<UOMDR> ();
            while ( rs.next () ) {
                uom = new UOMDR ();
                uom.UOM_ID = Integer.parseInt ( rs.getString ( 1 ) );
                uom.UOM_NAME = rs.getString ( 2 );
                UOMList.add ( uom );
                jComboBox2.addItem ( rs.getString ( 2 ) );
                jComboBox3.addItem ( rs.getString ( 2 ) );
                 jComboBox4.addItem ( rs.getString ( 2 ) );
            }
            rs.close ();
        } catch ( Exception e ) {
            System.out.println ( " Error 1 " + e.getMessage () );
            writeErrorLogs (BatchProcessing.class.getSimpleName () , BatchProcessing.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );

        }

        try {
            //rs = DB_Operations.executeSingle ("select a.RM_ID, a.RM_NAME, a.RM_CTG, b.CLOSING from raw_material a inner join RMStock b where a.RM_ID = b.RMI_ID");
            rs = DB_Operations.executeSingle ( "select a.RM_ID , a.RM_NAME as 'Name', a.RM_CTG as 'Category', b.CLOSING as 'Available', a.REORDER_LEVEL as 'Reorder Level'  from raw_material a inner join RMStock b where a.RM_ID = b.RMI_ID  group by a.RM_ID" );
            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
            if ( rs != null ) {

                jTable2.setModel ( buildTableModel ( rs ) );

                jTable2.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                jTable2.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );

            }
        } catch ( SQLException e ) {
            System.out.println ( e.getClass () + "  " + e.getMessage () );
            writeErrorLogs (BatchProcessing.class.getSimpleName () , BatchProcessing.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }

        try {
            //rs = DB_Operations.executeSingle ("select a.RM_ID, a.RM_NAME, a.RM_CTG, b.CLOSING from raw_material a inner join RMStock b where a.RM_ID = b.RMI_ID");
                rs = DB_Operations.executeSingle ( "select BatchName as 'Name', BatchQty as 'Quantity',  (select UOM from unit_meas where UOM_ID in (BatchUOM_ID)) as 'Unit' from BATCH_MASTER " );
            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
            if ( rs != null ) {
                jTable1.setModel ( buildTableModel2 ( rs ) );
            }
        } catch ( SQLException e ) {
            System.out.println ( e.getClass () + "  " + e.getMessage () );
            writeErrorLogs (BatchProcessing.class.getSimpleName () , BatchProcessing.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }
    }
     
    public  DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= 5; column++) {
            columnNames.add(metaData.getColumnName(column));
        }
        columnNames.add("Re-order Quantity");

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        String[] RMQTY = null ;
        
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            RMQTY = new String[2];
            double available = 0.0, rol, roq = 0.0;
            for (int columnIndex = 1; columnIndex <= 5; columnIndex++) {
                String item = rs.getObject(columnIndex).toString () ;
                vector.add( item );
                if(columnIndex==1){
                    RMQTY[0] = item ;
                }
                
                if(columnIndex==4){
                    RMQTY[1] = item ;
                    available = Double.parseDouble(item);
                }
                
                if(columnIndex==5){
                   
                    rol = Double.parseDouble(item);
                    
                    if(available<=0 && rol!=0.0 ){
                        roq = (available * -1) + rol ;    
                    }else if(available>0 && rol!=0.0 ){
                        if(available<rol)
                            roq =  rol - available;    
                    }
                    vector.add( roq );
                }
            }
            RMQtys.add(RMQTY);
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }
    
    public  DefaultTableModel buildTableModel2(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= 3; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
 //       String[] BATCHQTY = null ;
        while (rs.next()) {
 //            BATCHQTY = new String[2] ;
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= 3; columnIndex++) {
                String item = rs.getObject(columnIndex).toString () ;
                vector.add(item);
                
//                if(columnIndex==1){
//                    BATCHQTY[0] = item ;
//                }
//                
//                if(columnIndex==4){
//                    BATCHQTY[1] = item ;
//                }
            }
         //   BATCHQtys.add(BATCHQTY);
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }
    
    
    ActionListener selectUnitAction = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
     
           // jLabel13.setText( jComboBox4.getSelectedItem ().toString () ) ;
        //    jLabel14.setText( jComboBox4.getSelectedItem ().toString () ) ;
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

        dateChooserDialog1 = new datechooser.beans.DateChooserDialog();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jLabel5 = new javax.swing.JLabel();
        dateChooserCombo2 = new datechooser.beans.DateChooserCombo();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jCheckBox1 = new javax.swing.JCheckBox();
        jTextField4 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1164, 457));
        setLayout(null);

        jComboBox1.setOpaque(false);
        add(jComboBox1);
        jComboBox1.setBounds(30, 150, 210, 34);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("RM Code");
        add(jLabel1);
        jLabel1.setBounds(30, 130, 90, 20);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Quantity");
        add(jLabel2);
        jLabel2.setBounds(270, 190, 90, 20);

        jTextField1.setOpaque(false);
        add(jTextField1);
        jTextField1.setBounds(270, 210, 100, 30);

        jComboBox2.setOpaque(false);
        add(jComboBox2);
        jComboBox2.setBounds(370, 210, 50, 30);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Add to mixture");
        jButton1.setEnabled(false);
        jButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton1.setOpaque(false);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(130, 290, 110, 30);

        jList1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jList1.setEnabled(false);
        jList1.setOpaque(false);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        add(jScrollPane1);
        jScrollPane1.setBounds(30, 330, 210, 100);

        jTextField2.setOpaque(false);
        add(jTextField2);
        jTextField2.setBounds(270, 100, 210, 30);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("New Production Batch Name");
        add(jLabel3);
        jLabel3.setBounds(270, 80, 200, 20);

        dateChooserCombo1.setFormat(2);
        add(dateChooserCombo1);
        dateChooserCombo1.setBounds(270, 330, 210, 34);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Batch Expiry Date");
        add(jLabel5);
        jLabel5.setBounds(270, 380, 120, 20);
        add(dateChooserCombo2);
        dateChooserCombo2.setBounds(270, 400, 210, 30);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Save");
        jButton2.setOpaque(false);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(50, 450, 80, 34);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Update");
        jButton3.setOpaque(false);
        add(jButton3);
        jButton3.setBounds(130, 450, 90, 34);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("Export");
        jButton4.setOpaque(false);
        add(jButton4);
        jButton4.setBounds(220, 450, 90, 34);

        jButton5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton5.setText("Import");
        jButton5.setOpaque(false);
        add(jButton5);
        jButton5.setBounds(310, 450, 90, 34);

        jButton6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton6.setText("Close");
        jButton6.setOpaque(false);
        add(jButton6);
        jButton6.setBounds(400, 450, 80, 34);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable1);

        add(jScrollPane2);
        jScrollPane2.setBounds(500, 300, 650, 180);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.setOpaque(false);
        jScrollPane3.setViewportView(jTable2);

        add(jScrollPane3);
        jScrollPane3.setBounds(500, 50, 650, 220);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("Available Raw Material and Quantities");
        add(jLabel6);
        jLabel6.setBounds(500, 16, 250, 30);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("Available Batches");
        add(jLabel7);
        jLabel7.setBounds(500, 276, 130, 20);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("Resulting Quantity");
        add(jLabel8);
        jLabel8.setBounds(270, 240, 120, 16);

        jTextField3.setOpaque(false);
        add(jTextField3);
        jTextField3.setBounds(270, 260, 100, 30);

        jComboBox3.setOpaque(false);
        add(jComboBox3);
        jComboBox3.setBounds(370, 260, 50, 30);

        jCheckBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox1.setText("<html>Check here to create a new Intermediate Raw Material else leave unchecked to continue creating a new production batch from available raw materials</html>");
        jCheckBox1.setOpaque(false);
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        add(jCheckBox1);
        jCheckBox1.setBounds(30, 10, 470, 60);

        jTextField4.setEnabled(false);
        jTextField4.setOpaque(false);
        add(jTextField4);
        jTextField4.setBounds(30, 100, 210, 30);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("New Intermediate RM Name");
        add(jLabel9);
        jLabel9.setBounds(30, 80, 200, 16);

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("*");
        add(jLabel10);
        jLabel10.setBounds(250, 270, 20, 16);

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("*");
        add(jLabel11);
        jLabel11.setBounds(10, 100, 20, 16);

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("*");
        add(jLabel12);
        jLabel12.setBounds(250, 150, 20, 16);

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("*");
        add(jLabel13);
        jLabel13.setBounds(250, 210, 20, 16);

        jLabel14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel14.setText("Batch Creation Date");
        add(jLabel14);
        jLabel14.setBounds(270, 310, 120, 20);

        jLabel15.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jLabel15.setText("<html>Select Raw Materials from the above list and put quantity in the adjacent field and click 'Add to mixure to save it to the list</html> ");
        add(jLabel15);
        jLabel15.setBounds(30, 180, 230, 50);

        add(jComboBox4);
        jComboBox4.setBounds(30, 260, 210, 30);

        jLabel16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel16.setText("Measure");
        add(jLabel16);
        jLabel16.setBounds(30, 240, 90, 16);

        jLabel17.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel17.setText("Select Available Raw Material Batches");
        add(jLabel17);
        jLabel17.setBounds(270, 130, 210, 20);

        jComboBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jComboBox5);
        jComboBox5.setBounds(270, 150, 210, 35);

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 0, 0));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("*");
        add(jLabel18);
        jLabel18.setBounds(250, 100, 20, 16);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:

        //listModel.removeAllElements (); 
        if( ! jTextField1.getText ().trim ().equals ( "") ){
        
            String rm = jComboBox1.getSelectedItem ().toString ().trim ();
            double qty = Double.parseDouble ( jTextField1.getText ().trim () );
            String uom = jComboBox2.getSelectedItem ().toString ().trim ();
            int RMID = RMList.get ( jComboBox1.getSelectedIndex () ).RM_ID ;

            
            
            for(int i = 0; i<RMListQtys.size(); i++){
                int newRMID = Integer.parseInt( RMListQtys.get(i)[0]);

                if( newRMID == RMID  ){

                    if( Double.parseDouble( RMListQtys.get(i)[1])>= qty){
                        listModel.add ( 0 , rm + " (" + qty + " " + uom + ")" );
                        jList1.setModel ( listModel );
                    }else{
                        JOptionPane.showMessageDialog ( null, "Requested quantity for given raw material is higher than available !");
                        i = RMListQtys.size() ;
                    }
                }
            }
            
            RM_IDs.add(  new String[]{ RMList.get ( jComboBox1.getSelectedIndex () ).RM_ID+"" , jTextField1.getText ().trim ()});
            
            
        }else{
            JOptionPane.showMessageDialog ( null, "Please fill all fields marked with * !");
        }
        
        
    }//GEN-LAST:event_jButton1MouseClicked

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        // TODO add your handling code here:
        int state = evt.getStateChange ();

        if ( state == ItemEvent.SELECTED ) {
            jTextField4.setEnabled ( true );
            jTextField2.setEnabled ( false );

            jButton1.setEnabled ( true );
            jList1.setEnabled ( true );
            newRM = true;
            newBatch = false;
            
            jComboBox4.setEnabled ( true );
            
        } else if ( state == ItemEvent.DESELECTED ) {
            jTextField4.setEnabled ( false );
            jTextField2.setEnabled ( true );

            jButton1.setEnabled ( false );
            jList1.setEnabled ( false );
            newRM = false;
            newBatch = true;
           
            jComboBox4.setEnabled ( false );
        }

    }//GEN-LAST:event_jCheckBox1ItemStateChanged

        public int iDFromJSON ( String json ) {
        int id = 0;

        JSONObject jObject = new JSONObject ( json );
        
        JSONObject value = ( JSONObject ) jObject.get ( "data" );

        id = Integer.parseInt ( value.get ( "insert_id" ).toString () );
        //    id = Integer.parseInt( st.getString ("insert_id" ));

        return id;
    }
    
    
    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:

        // 1. check if a new batch is to created or a new raw material is to be created
        // 2. if a batch is to be created , then  first make an entry of the batch in the batch master table
        // 3. else if a new raw material is to be created then make an entry into raw material table with category internally processed
        // 3.1 and add the quantities and uoms used in formation in batch_details table
        Calendar c2 = Calendar.getInstance ();
        strDate2 = sdf2.format ( c2.getTime () );

        
        
        if ( newRM ) 
        {
            
            if( ! jTextField4.getText ().trim().equals ( "") )
            {
            
                String RM_TYPE, RM_NAME, RM_CTG, RM_RATE, RM_CRIT, RM_EC_NO, RM_CAS_NO, GST_NO, RM_CLASS,  REORDER_LEVEL, CREATED_ON, EDITED_ON, EDITED_BY;

                 RM_TYPE = "Internally Processsed";
                 RM_CTG = "Internally Processsed";
                 RM_NAME = jTextField4.getText ().trim();
                 //RM_AUTH_SUPP = jComboBox4.getSelectedItem().toString();
                 RM_RATE = "0.0";
                 RM_CRIT = "";
                 RM_EC_NO = "";
                 RM_CAS_NO = "";
                 GST_NO = "";
                 RM_CLASS = jTextField4.getText ().trim();
                 REORDER_LEVEL = "0.0";

                 CREATED_ON = strDate2;
                 EDITED_ON = "test";
                 EDITED_BY = strDate2;


                // String result = DB_Operations.insertIntoRawMaterialMaster ( RM_TYPE , RM_NAME , RM_CTG , RM_RATE , RM_CRIT , RM_EC_NO , RM_CAS_NO , GST_NO , RM_CLASS , REORDER_LEVEL , CREATED_ON , EDITED_ON , EDITED_BY , UOMList.get(jComboBox4.getSelectedIndex ()).UOM_ID+"");
               //  JOptionPane.showMessageDialog ( null , "" + result );

                  try{
                        String addEmpAPICall = "rawmaterialadd?RM_TYPE="+URLEncoder.encode(RM_TYPE, "UTF-8")+"&RM_NAME="+URLEncoder.encode(RM_NAME, "UTF-8")+"&RM_CTG="+URLEncoder.encode(RM_CTG, "UTF-8")+"&RM_AUTH_SUPP="+URLEncoder.encode("", "UTF-8")+"&RM_RATE="+URLEncoder.encode(RM_RATE, "UTF-8")+"&RM_CRIT="+URLEncoder.encode(RM_CRIT, "UTF-8")+"&RM_EC_NO="+URLEncoder.encode(RM_EC_NO, "UTF-8")+"&RM_CAS_NO="+URLEncoder.encode(RM_CAS_NO, "UTF-8")+"&GST_NO="+URLEncoder.encode(GST_NO, "UTF-8")+"&RM_CLASS="+URLEncoder.encode(RM_CLASS,"UTF-8")+"&REORDER_LEVEL="+URLEncoder.encode(REORDER_LEVEL, "UTF-8")+"&CREATED_ON="+URLEncoder.encode(CREATED_ON, "UTF-8")+"&EDITED_ON="+URLEncoder.encode(EDITED_ON, "UTF-8")+"&EDITED_BY="+URLEncoder.encode(EDITED_BY, "UTF-8")+"&RMM_UOM_ID="+URLEncoder.encode(UOMList.get(jComboBox4.getSelectedIndex ()).UOM_ID+"", "UTF-8");
                        String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                        JOptionPane.showMessageDialog ( null , result2);

                       // System.out.println ( " new raw material inserted :  "+ result2 );
                        
                        int RMID = iDFromJSON(  result2  ) ;
                        
                        insertIntoRMStock_NewRM ( RMID,         Double.parseDouble( jTextField3.getText() ) );
                        
                      //  insertNewRMStock ( Integer.parseInt( result ) );
                        
                    }catch(UnsupportedEncodingException e){

                    }
                 
                 

                 for( int i=0; i<RM_IDs.size (); i++ ){
                        // added default value 1 as second param or calculating no of parts to be manufacured from 1 rm unit
                        
                        // added default value 1 as second param or calculating no of parts to be manufacured from 1 rm unit
                        updateRMstock (Integer.parseInt( RM_IDs.get ( i )[0] ) ,     Double.parseDouble(RM_IDs.get ( i )[1] ) );
                        //int rmId ,   int factor,  double qty, int ref_batch_id ,   Double rmIssued , int rmIssuedInCount
                 }
                 
            }else{
                 JOptionPane.showMessageDialog ( null, "Please fill all fields marked with * !");
            }
            
        } else if ( newBatch ) {

            // 4. A new batch will be added with equal or less than available qty of the  raw material, if applicable less that re-order as well
            if( ! jTextField1.getText ().trim().equals ( "") && ! jTextField2.getText ().trim().equals ( "") && ! jTextField3.getText ().trim().equals ( "") )
            {

                /*
                                    * ref_rm_id         = raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] 
                                    * inward_uid        = tkInId+ "
                                    * purchase_rate =  prate;
                                
                                    * BatchName= bName
                                    * BatchQty=bQty
                                    * BatchUOM_ID= bUOMID
                                    * BatchCreateDate= createDate
                                      BatchExpireDate= expiryDate
                                      rm_inward_date= createDate
                                    *inward_uid = createDate
                                    * purchase_rate=" 
                 
                 */
    
                /*
                try{
                    addEmpAPICall = "batchesadd?BatchName="+URLEncoder.encode(bName, "UTF-8")+"&BatchQty="+URLEncoder.encode(bQty+"", "UTF-8")
                            +"&BatchUOM_ID="+URLEncoder.encode(  bUOMID +"", "UTF-8")
                            +"&BatchCreateDate="+URLEncoder.encode ( createDate , "UTF-8" )+"&BatchExpireDate=expiryDate&ref_rm_id="+raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] 
                            + "&inward_uid=&rm_inward_date=" + URLEncoder.encode ( BatchList.get( jComboBox5.getSelectedIndex()  )[3] , "UTF-8" )+ "&purchase_rate=" + BatchList.get( jComboBox5.getSelectedIndex()  )[3];
                        result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                        //JOptionPane.showMessageDialog ( null , result2);

                    if( result2.contains("success") ){
                        batchId = iDFromJSON( result2  );

                        String updateStock = "rmstockadd?RMI_ID=" +raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] + "&RM_ITEM_ID=" +raw_materials.get(   jComboBox1.getSelectedIndex ()  )[0] 
                            + "&OPENING=" + OPENING +"&RECEIVED=" + RECEIVED+ "&USED=" + USED+ "&CLOSING=" + CLOSING 
                            + "&CREATED_ON=" + URLEncoder.encode ( new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format ( Calendar.getInstance ().getTime () ) , "UTF-8" )
                            + "&EDITED_ON=" + URLEncoder.encode (new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format ( Calendar.getInstance ().getTime () ) , "UTF-8" )+ "&EDITED_BY=" + Proman_User.getEID ()   
                            +"&ref_batch_id="+BatchList.get( jComboBox5.getSelectedIndex()  )[0];

                        result2 = WebAPITester.prepareWebCall ( updateStock , StaticValues.getHeader () , "" );
                        JOptionPane.showMessageDialog ( null , result2 );

                        }
                }catch(UnsupportedEncodingException e){
                          System.out.println ( ""+e.getMessage () );    
                }
                 */
                
                
                
                        String bName = jTextField2.getText ().trim ();
                        String createDate, expiryDate;
                        Double bQty = Double.parseDouble ( jTextField1.getText ().trim () );
                        int bUOMID = UOMList.get ( jComboBox2.getSelectedIndex () ).UOM_ID;
                        int rmId = RMList.get ( jComboBox1.getSelectedIndex () ).RM_ID;
                        createDate = DateTimeFormatter.formatFromDate ( dateChooserCombo1.getText () );
                        expiryDate = DateTimeFormatter.formatFromDate ( dateChooserCombo2.getText () );

                        
                        try{
                                String addEmpAPICall = "batchesadd?BatchName="+URLEncoder.encode(bName, "UTF-8")+"&BatchQty="+URLEncoder.encode(bQty+"", "UTF-8")
                                        +"&BatchUOM_ID="+URLEncoder.encode(bUOMID+"", "UTF-8") +"&BatchCreateDate="+URLEncoder.encode(createDate, "UTF-8")
                                        +"&BatchExpireDate="+URLEncoder.encode(expiryDate, "UTF-8") +"&rm_inward_date="+URLEncoder.encode(createDate, "UTF-8")
                                        +"&inward_uid="+URLEncoder.encode(createDate, "UTF-8") +"&purchase_rate="
                                        +"&ref_rm_id="+URLEncoder.encode(""+rmId,"UTF-8") ;//new 
                                
                                String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                                
                        }catch(UnsupportedEncodingException e){

                        }
                                
                        // added default value 1 as second param or calculating no of parts to be manufacured from 1 rm unit
                        //insertIntoRMStock (rmId ,  1 ,    Double.parseDouble( jTextField1.getText () ) );
                        updateRMstock (rmId ,     Double.parseDouble( jTextField1.getText () ) );
                        
                        loadContent ();
                        
                  
            }else{
                JOptionPane.showMessageDialog ( null, "Please fill all fields marked with * !");
            }

        }

    }//GEN-LAST:event_jButton2MouseClicked

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        if (!(listModel.getSize() < 1)) {

            int selection = jList1.getSelectedIndex();
            listModel.remove(selection );
            listModel.removeElementAt(selection);
            jList1.setModel(listModel);
            
            RM_IDs.remove ( selection );
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    @SuppressWarnings( "empty-statement" )
    public boolean insertIntoRMStock ( int rmId ,   int factor,  double qty, int ref_batch_id ,   Double rmIssued , int rmIssuedInCount , String date) {

        Calendar c2 = Calendar.getInstance ();
        strDate2 = sdf2.format ( c2.getTime () );

//        ResultSet result = DB_Operations.executeSingle ( "SELECT RM_TYPE, (OPENING),(RECEIVED),(USED),(CLOSING) FROM raw_material A INNER JOIN RMStock B ON A.RM_ID = B.RMI_ID AND A.RM_ID =" + rmId +" group by A.RM_ID" );
//
//        try {
//            if ( result != null && result.isBeforeFirst () ) {
//
//                recordExist = true;
//
//                OPENING = Double.parseDouble ( result.getString ( 5 ) );
//                RECEIVED = Integer.parseInt ( result.getString ( 3 ) );
//                USED = Integer.parseInt ( result.getString ( 4 ) );
//                OldCLOSING = Integer.parseInt ( result.getString ( 5 ) );
//
//            } else {
//                System.out.println ( "No result to show" );
//                recordExist = false;
//                OPENING = 0;
//                RECEIVED = 0;
//                USED = 0;
//                CLOSING = 0;
//            }
//
//            result.close ();
//
//            OPENING = OldCLOSING;
//            
//            USED =  qty ;
//            RECEIVED = 0;
//            CLOSING = OPENING - USED;
//
//            DB_Operations.executeSingleNR ( "INSERT INTO RMStock ( `RMI_ID`, `RM_ITEM_ID`, `OPENING` ,`RECEIVED` ,`USED` ,`CLOSING` , `CREATED_ON`,`EDITED_ON`,`EDITED_BY`  )  values ( " + rmId + ", 0 ," + OPENING + "," + RECEIVED + "," + USED + "," + CLOSING + ",'" + strDate2 + "','" + strDate2 + "', '"+Proman_User.getUsername ()+"' )" );

            
            try{
                        //String addEmpAPICall = "rmstockadd?RMI_ID="+URLEncoder.encode(rmId+"", "UTF-8")+"&RM_ITEM_ID="+URLEncoder.encode("0", "UTF-8")+"&OPENING="+URLEncoder.encode(OPENING+"", "UTF-8")+"&RECEIVED="+URLEncoder.encode(RECEIVED+"", "UTF-8")+"&USED="+URLEncoder.encode(USED+"", "UTF-8")+"&CLOSING="+URLEncoder.encode(CLOSING+"", "UTF-8")+"&CREATED_ON="+URLEncoder.encode(strDate2+"", "UTF-8")+"&EDITED_ON="+URLEncoder.encode(strDate2+"", "UTF-8")+"&EDITED_BY="+URLEncoder.encode(Proman_User.getEID ()+"", "UTF-8")  ;
                        String addEmpAPICall = "latestrwamstock?rmid="+URLEncoder.encode(rmId+"", "UTF-8") ;
                        String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                        
                        
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
                        JSONObject emp ;
                        
                         String rm_in_date  ="" ;
                        String batch_in_no   ="" ;
                        String in_uid  ="" ;
                        String p_rate   ="" ;
                         
                        for ( int j = 0 ; j < records.length () ; j ++ ) {
                               emp = records.getJSONObject ( j );
                               String opening =  emp.get ( "OPENING" ).toString () ;
                               String received =  emp.get ( "RECEIVED" ).toString () ;
                               String used =  emp.get ( "USED" ).toString () ;
                               String closing =  emp.get ( "CLOSING" ).toString () ;
                               String oldClosing = emp.get ( "CLOSING" ).toString () ;
                               
                               String oldClosing_CNT = emp.get ( "CLOSING_CNT" ).toString () ;//new
                             //   rm_in_date = emp.get ( "rm_inward_date" ).toString () ;
                             //   batch_in_no = emp.get ( "inward_batch_no" ).toString () ;
                             //   in_uid = emp.get ( "inward_uid" ).toString () ;
                             //   p_rate = emp.get ( "purchase_rate" ).toString () ;
                               
                               
                               
                                System.out.println ( ""+opening );
                                
                                OPENING = Double.parseDouble( oldClosing ) ;
                                 OPENING_CNT = Double.parseDouble( oldClosing_CNT ) ;//new
                                
                                //USED =  qty ;
                                
                               // USED =  qty / factor ;
                                USED =  rmIssued ;//new
                                USED_CNT =  rmIssuedInCount ;//new
                                
                                RECEIVED = 0;
                                CLOSING = OPENING - USED;
                                RECEIVED_CNT = 0;
                                CLOSING_CNT = OPENING_CNT - USED_CNT;//new
                        }
                        
                        if(   CLOSING >= 0  )
                        {
                            addEmpAPICall = "rmstockadd?RMI_ID="+URLEncoder.encode(rmId+"", "UTF-8")
                                                      +"&RM_ITEM_ID="+URLEncoder.encode("0", "UTF-8")
                                                      +"&OPENING="+URLEncoder.encode(OPENING+"", "UTF-8")
                                                      +"&RECEIVED="+URLEncoder.encode(RECEIVED+"", "UTF-8")
                                                      +"&USED="+URLEncoder.encode(USED+"", "UTF-8")
                                                      +"&CLOSING="+URLEncoder.encode(CLOSING+"", "UTF-8")
                                                      +"&CREATED_ON="+URLEncoder.encode(date+"", "UTF-8")
                                                      +"&EDITED_ON="+URLEncoder.encode(date+"", "UTF-8")
                                                      +"&EDITED_BY="+URLEncoder.encode(Proman_User.getEID ()+"", "UTF-8")
                                                      +"&ref_batch_id="+URLEncoder.encode( ref_batch_id +"", "UTF-8")  
                                                      +"&OPENING_CNT="+URLEncoder.encode(OPENING_CNT+"", "UTF-8")
                                                      +"&RECEIVED_CNT="+URLEncoder.encode(RECEIVED_CNT+"", "UTF-8")
						      +"&USED_CNT="+URLEncoder.encode(USED_CNT+"", "UTF-8")
					              +"&CLOSING_CNT="+URLEncoder.encode(CLOSING_CNT+"", "UTF-8");
                                        //last 4 lines
                            result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                            JOptionPane.showMessageDialog ( null , result2);

                            
                            addEmpAPICall = "batchesedit?BatchId="+ ref_batch_id     ;
                            result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                            records = new JSONObject(result2).getJSONArray ( "data" );

                            try{
                                 double presentStock =   Double.parseDouble( records.getJSONObject ( 0 ).get( "BatchQty" ).toString()) ;
                                Integer presentStockCount =   records.getJSONObject ( 0 ).getInt ( "BatchQty_CNT" )  ;
                                //presentStock = presentStock - (int)(qty / factor) ;
                                presentStock = presentStock - (rmIssued) ;
                                presentStockCount = presentStockCount - rmIssuedInCount ;
                                
                                addEmpAPICall = "batchesupdate?BatchId="+ ref_batch_id +"&BatchQty="+presentStock+"&BatchQty_CNT="+presentStockCount ;
                                result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                                JOptionPane.showMessageDialog ( null , result2);
                            }catch(  NumberFormatException f)
							{
                                Double presentStock =   records.getJSONObject ( 0 ).getDouble ( "BatchQty" )  ;
                                Double presentStockCount =   records.getJSONObject ( 0 ).getDouble ( "BatchQty_CNT" ) ;
                                //presentStock = presentStock - (qty / factor) ;
                                presentStock = presentStock - (rmIssued) ;
                                presentStockCount = presentStockCount - rmIssuedInCount  ;
                                
                                addEmpAPICall = "batchesupdate?BatchId="+ ref_batch_id +"&BatchQty="+presentStock +"&BatchQty_CNT="+presentStockCount ;                      
                                result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                                JOptionPane.showMessageDialog ( null , result2);
                            }catch(  Exception f){
                                System.out.println ( ""+f.getMessage() );   
                            }
                            
                            

                            if(  result2.contains( "success" )  ){
                                return true ;
                            }else{
                                return false ;
                            }
                            
                        }else{
                            return false ;
                        }
            
                        
                }catch(UnsupportedEncodingException  e){
                        System.out.println ( ""+e.getMessage() );
                }
            
            
//        } catch ( SQLException e ) {
//            System.out.println ( "Error Occured !" );
//            writeErrorLogs (BatchProcessing.class.getSimpleName () , BatchProcessing.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }
            return false ;
    }

    @SuppressWarnings( "empty-statement" )
    public boolean insertIntoRMStock_NewRM ( int rmId ,    double qty ) {

        Calendar c2 = Calendar.getInstance ();
        strDate2 = sdf2.format ( c2.getTime () );

            
            try{
                        //String addEmpAPICall = "rmstockadd?RMI_ID="+URLEncoder.encode(rmId+"", "UTF-8")+"&RM_ITEM_ID="+URLEncoder.encode("0", "UTF-8")+"&OPENING="+URLEncoder.encode(OPENING+"", "UTF-8")+"&RECEIVED="+URLEncoder.encode(RECEIVED+"", "UTF-8")+"&USED="+URLEncoder.encode(USED+"", "UTF-8")+"&CLOSING="+URLEncoder.encode(CLOSING+"", "UTF-8")+"&CREATED_ON="+URLEncoder.encode(strDate2+"", "UTF-8")+"&EDITED_ON="+URLEncoder.encode(strDate2+"", "UTF-8")+"&EDITED_BY="+URLEncoder.encode(Proman_User.getEID ()+"", "UTF-8")  ;
                        String addEmpAPICall = "latestrwamstock?rmid="+URLEncoder.encode(rmId+"", "UTF-8") ;
                        String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                        
                        
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
                        JSONObject emp ;

                         
                            addEmpAPICall = "rmstockadd?RMI_ID="+URLEncoder.encode(rmId+"", "UTF-8")
                                                      +"&RM_ITEM_ID="+URLEncoder.encode("0", "UTF-8")
                                                      +"&OPENING="+URLEncoder.encode("0", "UTF-8")
                                                      +"&RECEIVED="+URLEncoder.encode(qty+"", "UTF-8")
                                                      +"&USED="+URLEncoder.encode("", "UTF-8")
                                                      +"&CLOSING="+URLEncoder.encode(qty+"", "UTF-8")
                                                      +"&CREATED_ON="+URLEncoder.encode(strDate2+"", "UTF-8")
                                                      +"&EDITED_ON="+URLEncoder.encode(strDate2+"", "UTF-8")
                                                      +"&EDITED_BY="+URLEncoder.encode(Proman_User.getEID ()+"", "UTF-8")  ;
                                        //last 4 lines
                            result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                            JOptionPane.showMessageDialog ( null , result2);
                 

                            if(  result2.contains( "success" )  ){
                                return true ;
                            }else{
                                return false ;
                            }
                        
                }catch(UnsupportedEncodingException  e){
                        System.out.println ( ""+e.getMessage() );
                }
            
            
//        } catch ( SQLException e ) {
//            System.out.println ( "Error Occured !" );
//            writeErrorLogs (BatchProcessing.class.getSimpleName () , BatchProcessing.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }
            return false ;
    }
    
    
        @SuppressWarnings( "empty-statement" )
    public boolean updateRMstock ( int rmId ,    double qty) {

        Calendar c2 = Calendar.getInstance ();
        strDate2 = sdf2.format ( c2.getTime () );

        
        // 1. get the list of ram materials selected in the jList
        // 2. for each entry selected in jList (  having raw material id, and its qty  )  run following code ( step no 3.)
        // 3. for a selected raw material id, get its latest stock and deduct the qty  being used for new  intermediate raw material 
        // 4. after all raw materials stock are updated then refresh list
        
       
            
            try{
                        //String addEmpAPICall = "rmstockadd?RMI_ID="+URLEncoder.encode(rmId+"", "UTF-8")+"&RM_ITEM_ID="+URLEncoder.encode("0", "UTF-8")+"&OPENING="+URLEncoder.encode(OPENING+"", "UTF-8")+"&RECEIVED="+URLEncoder.encode(RECEIVED+"", "UTF-8")+"&USED="+URLEncoder.encode(USED+"", "UTF-8")+"&CLOSING="+URLEncoder.encode(CLOSING+"", "UTF-8")+"&CREATED_ON="+URLEncoder.encode(strDate2+"", "UTF-8")+"&EDITED_ON="+URLEncoder.encode(strDate2+"", "UTF-8")+"&EDITED_BY="+URLEncoder.encode(Proman_User.getEID ()+"", "UTF-8")  ;
                        String addEmpAPICall = "latestrwamstock?rmid="+URLEncoder.encode(rmId+"", "UTF-8") ;
                        String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                        
                        
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
                        JSONObject emp ;
                        
                         String rm_in_date  ="" ;
                        String batch_in_no   ="" ;
                        String in_uid  ="" ;
                        String p_rate   ="" ;
                         
                        for ( int j = 0 ; j < records.length () ; j ++ ) {
                               emp = records.getJSONObject ( j );
                               String opening =  emp.get ( "OPENING" ).toString () ;
                               String oldClosing = emp.get ( "CLOSING" ).toString () ;
                               
                                System.out.println ( ""+opening );
                                
                                OPENING = Double.parseDouble( oldClosing ) ;                 
                                USED =  qty ;                                
                                RECEIVED = 0;
                                CLOSING = OPENING - USED;
                        }
                        
                        if(   CLOSING >= 0  )
                        {
                            addEmpAPICall = "rmstockadd?RMI_ID="+URLEncoder.encode(rmId+"", "UTF-8")
                                                      +"&RM_ITEM_ID="+URLEncoder.encode("0", "UTF-8")
                                                      +"&OPENING="+URLEncoder.encode(OPENING+"", "UTF-8")
                                                      +"&RECEIVED="+URLEncoder.encode(RECEIVED+"", "UTF-8")
                                                      +"&USED="+URLEncoder.encode(USED+"", "UTF-8")
                                                      +"&CLOSING="+URLEncoder.encode(CLOSING+"", "UTF-8")
                                                      +"&CREATED_ON="+URLEncoder.encode(strDate2+"", "UTF-8")
                                                      +"&EDITED_ON="+URLEncoder.encode(strDate2+"", "UTF-8")
                                                      +"&EDITED_BY="+URLEncoder.encode(Proman_User.getEID ()+"", "UTF-8")                                           ;
                            
                                        //last 4 lines
                            result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                            JOptionPane.showMessageDialog ( null , result2);
                            
                            if(  result2.contains( "success" )  ){
                                return true ;
                            }else{
                                return false ;
                            }
                            
                        }else{
                            return false ;
                        }
            
                        
                }catch(UnsupportedEncodingException  e){
                        System.out.println ( ""+e.getMessage() );
                }
            
            
//        } catch ( SQLException e ) {
//            System.out.println ( "Error Occured !" );
//            writeErrorLogs (BatchProcessing.class.getSimpleName () , BatchProcessing.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }
            return false ;
    }
    
    
    
    public void insertNewRMStock ( int rmId ) {

        Calendar c2 = Calendar.getInstance ();
        strDate2 = sdf2.format ( c2.getTime () );

        ResultSet result = DB_Operations.executeSingle ( "SELECT RM_TYPE, (OPENING),(RECEIVED),(USED),(CLOSING) FROM raw_material A INNER JOIN RMStock B ON A.RM_ID = B.RMI_ID AND A.RM_ID =" + rmId + " group by A.RM_ID" );

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

            RECEIVED = Integer.parseInt ( jTextField3.getText () );
            USED = 0;
            CLOSING = OPENING + RECEIVED;

            DB_Operations.executeSingleNR ( "INSERT INTO RMStock ( `RMI_ID`, `RM_ITEM_ID`, `OPENING` ,`RECEIVED` ,`USED` ,`CLOSING` , `CREATED_ON`,`EDITED_ON`,`EDITED_BY`  )  values ( " + rmId + ", 0 ," + OPENING + "," + RECEIVED + "," + USED + "," + CLOSING + ",'" + strDate2 + "','" + strDate2 + "','"+Proman_User.getUsername ()+"' )" );

             try{
                        String addEmpAPICall = "rmstockadd?RMI_ID="+URLEncoder.encode(rmId+"", "UTF-8")+"&RM_ITEM_ID="+URLEncoder.encode("0", "UTF-8")+"&OPENING="+URLEncoder.encode(OPENING+"", "UTF-8")+"&RECEIVED="+URLEncoder.encode(RECEIVED+"", "UTF-8")+"&USED="+URLEncoder.encode(USED+"", "UTF-8")+"&CLOSING="+URLEncoder.encode(CLOSING+"", "UTF-8")+"&CREATED_ON="+URLEncoder.encode(strDate2+"", "UTF-8")+"&EDITED_ON="+URLEncoder.encode(strDate2+"", "UTF-8")+"&EDITED_BY="+URLEncoder.encode(Proman_User.getEID ()+"", "UTF-8")  ;
                        String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                        JOptionPane.showMessageDialog ( null , result2);
                }catch(UnsupportedEncodingException e){

                }
            
        } catch ( SQLException e ) {
            System.out.println ( "Error Occured !" );
            writeErrorLogs (BatchProcessing.class.getSimpleName () , BatchProcessing.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

    }

    ArrayList<String[]> RMQtys = new ArrayList<String[]>();
  //  ArrayList<String[]> BATCHQtys = new ArrayList<String[]>();

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private datechooser.beans.DateChooserCombo dateChooserCombo2;
    private datechooser.beans.DateChooserDialog dateChooserDialog1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JCheckBox jCheckBox1;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
