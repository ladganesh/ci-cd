/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.awt.Font;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class InventoryWidget extends javax.swing.JPanel {

    ArrayList<String[]> rmList = new ArrayList<String[]> ();
    ArrayList<String[]> rmList2 = new ArrayList<String[]> ();
    public static ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>> ();
    public static DecimalFormat df = new DecimalFormat ( "#.##" );

    /**
     * Creates new form InventoryWidget
     */
    public InventoryWidget () {
        initComponents ();

        loadContent();
        loadContentFGABC();
        
        Thread t = new Thread(){
            public void run(){
            //    loadContent();
            //    loadContentFGABC();
            }
        };
        
        Thread t2 = new Thread(){
            public void run(){
                 
            }
        };
        
        t.start ();
       // t2.start();
        
    }

    public static void buildTableModelRMABC ( ArrayList<String[]> rs  )
            throws SQLException {

        

        double totalStock = 0.0;
        double totalStockValue = 0.0;
        Vector<String> columnNames = new Vector<String>();
        
            columnNames.add ( "RM Name " );
            columnNames.add ( "Reorder Level " );
            columnNames.add ( "Closing Stock" );
            columnNames.add ( "Rate" );
            columnNames.add ( "Stock Value" );
            columnNames.add ( "% of Value " );
            columnNames.add ( "Cummulative" );
            columnNames.add ( "Category" );
            
            
//                    rmValues[0] =  emp.get( "RM_ID" ).toString() ;
//                    rmValues[1] =  emp.get( "RM_NAME" ).toString() ;
//                    rmValues[2] =  emp.get( "REORDER_LEVEL" ).toString() ;
//                    rmValues[3] =  emp.get( "RM_RATE" ).toString() ;
//                    rmValues[4] = emp2.get( "CLOSING" ).toString() ;
            

            //}
            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
            for(     int rmListCounter =0 ;     rmListCounter<rs.size();    rmListCounter++       ){
                    Vector<Object> vector = new Vector<Object> ();
                    String[] values = rs.get( rmListCounter );
                    for(    int valuesCounter =1;   valuesCounter<values.length;     valuesCounter++       ){
           
                        vector.add(     values[  valuesCounter  ]  );
                        
//                        if( valuesCounter ==2  ){
//                            try{
//                                totalStock = totalStock + Double.parseDouble ( values[  valuesCounter  ] );
//                            }catch(  NumberFormatException e   ){
//                                //totalStock = totalStock + Double.parseDouble ( values[  valuesCounter  ] );
//                            }
//                        }
                        
                    }
                    
                    try{
                        vector.add(4,   Double.parseDouble( vector.get(2).toString ()) *  Double.parseDouble( vector.get(3).toString ()) ) ;
                        totalStockValue = totalStockValue +  Double.parseDouble( vector.get(2).toString ()) *  Double.parseDouble( vector.get(3).toString ()) ;

                        data.add ( vector );
                    }catch(   NumberFormatException | NullPointerException e  ){
                        
                    }
            }
        
        
        
            for ( int i = 0 ; i < data.size () - 1 ; i ++ ) {

                for ( int j = i + 1 ; j < data.size () ; j ++ ) {

                    Vector<Object> abc1 = data.get ( i );
                    Vector<Object> abc2 = data.get ( j );

                    try{
                        if ( Double.parseDouble ( abc1.get ( 3 ).toString () ) < Double.parseDouble ( abc2.get ( 3 ).toString () ) ) {
                            //   data.remove ( i );
                            data.remove ( j );
                            data.add ( i , abc2 );
                            //  data.add ( j, abc1 );
                        }
                    }catch( NumberFormatException e){
                        
                    }
                }
            }

            double singleStock, percent, oldPercent = 0.0;

            for ( int i = 0 ; i < data.size () ; i ++ ) {

                Vector<Object> abc = data.get ( i );

                try{
                    singleStock = Double.parseDouble ( abc.get ( 4 ).toString () );
                }catch( NumberFormatException e){
                    singleStock = 0 ;
                }

                //abc.add ( 4 ,   (Double.parseDouble(abc.get(2).toString())*Double.parseDouble(abc.get(3).toString())) ) ;
                //totalStock = totalStock +  (Double.parseDouble(abc.get(2).toString())*Double.parseDouble(abc.get(3).toString()) );
                
                
                percent = ( singleStock / totalStockValue ) * 100;
                //percent = ( Double.parseDouble(abc.get(4).toString()) / totalStock ) * 100;

                abc.add ( 5 , df.format ( percent ) );
                abc.add ( 6 , df.format ( percent + oldPercent ) );
                oldPercent = oldPercent + percent;

                abc.add ( 7 , "Category" );

                data.remove ( i );
                data.add ( i , abc );
            }

            jTable2.setModel ( new DefaultTableModel ( data , columnNames ) );

           

    }
    
    public void loadContent () {
        ResultSet result = null;
        try {

            String addEmpAPICall = "rawmaterials";
            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
           
            
            if( !  result2.contains(    "not found"   )  ){
                HashMap<String , Object> map = new HashMap<String , Object> ();
                JSONObject jObject = new JSONObject ( result2 );
                Iterator<?> keys = jObject.keys ();


                String addEmpAPICall2 ;
                String result3 ;
                HashMap<String , Object> map2 ;
                JSONObject jObject2 ;
                Iterator<?> keys2 ;
                JSONObject st2 ;
                JSONArray records2 ;
                 JSONObject emp2 = null;

                while ( keys.hasNext () ) {
                    String key = ( String ) keys.next ();
                    Object value = jObject.get ( key );
                    map.put ( key , value );
                }

                JSONObject st = ( JSONObject ) map.get ( "data" );
                JSONArray records = st.getJSONArray ( "records" );

                JSONObject emp = null;

                String [] rmValues  = null; 

                for ( int i = 0 ; i < records.length () ; i ++ ) {
                    emp = records.getJSONObject ( i );
                    rmValues = new String[6] ; 
                    rmValues[0] =  emp.get( "RM_ID" ).toString() ;
                    rmValues[1] =  emp.get( "RM_NAME" ).toString() ;
                    rmValues[2] =  emp.get( "REORDER_LEVEL" ).toString() ;
                    

                    //values = new String[]{    emp.get( "RM_ID" ).toString() , emp.get( "RM_NAME" ).toString(), emp.get( "REORDER_LEVEL" ).toString(), emp.get( "RMM_UOM_ID" ).toString() , emp.get( "RM_RATE" ).toString()       }  ;
                    //rmList.add(   new String[]{    emp.get( "RM_ID" ).toString() , emp.get( "RM_NAME" ).toString(), emp.get( "REORDER_LEVEL" ).toString(), emp.get( "RMM_UOM_ID" ).toString() , emp.get( "RM_RATE" ).toString()       }           )    ;

                    addEmpAPICall2 = "latestrwamstock?rmid="+emp.get( "RM_ID" ).toString();
                    result3 = WebAPITester.prepareWebCall ( addEmpAPICall2 , StaticValues.getHeader() , "" );
                    map2 = new HashMap<String , Object> ();
                    jObject2 = new JSONObject ( result3 );
                    keys2 = jObject2.keys ();

                    while ( keys2.hasNext () ) {
                        String key = ( String ) keys2.next ();
                        Object value = jObject2.get ( key );
                        map2.put ( key , value );
                    }

                    st2 = ( JSONObject ) map2.get ( "data" );
                    records2 = st2.getJSONArray ( "records" );

                    emp2 = null;

                    for ( int j = 0 ; j < records2.length () ; j ++ ) {
                        emp2 = records2.getJSONObject ( j );
                        rmValues[3] = emp2.get( "CLOSING" ).toString() ;
                        //rmList.add(   new String[]{    emp.get( "RM_ID" ).toString() , emp.get( "RM_NAME" ).toString(), emp.get( "REORDER_LEVEL" ).toString(), emp.get( "RMM_UOM_ID" ).toString() , emp.get( "RM_RATE" ).toString()       }           )    ;
                    }

                   //// rmValues[4] =  emp.get( "RMM_UOM_ID" ).toString() ;
                   // rmValues[4] =  emp.get( "RM_RATE" ).toString() ;
                   rmValues[4] =  emp2.get( "purchase_rate" ).toString() ;
                   
                    
                    rmList.add( rmValues);
                }

                buildTableModelRMABC( rmList );

                System.out.println ( ""+rmList.size() );
            
            }
            


        } catch ( SQLException e ) {
            //System.out.println( "Error No result to show " +e.getMessage());
            try {
                result.close ();
            } catch ( Exception e2 ) {
                System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e2.toString () );
                StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
            StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

    }
    
    public void loadContentFGABC(){
        
            String addEmpAPICall = "finishedgoods";
            
            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            
            if( !  result2.contains (  "not found"   )){
            
                    HashMap<String , Object> map = new HashMap<String , Object> ();
                    JSONObject jObject = new JSONObject ( result2 );
                    Iterator<?> keys = jObject.keys ();


                    String addEmpAPICall2 ;
                    String result3 ;
                    HashMap<String , Object> map2 ;
                    JSONObject jObject2 ;
                    Iterator<?> keys2 ;
                    JSONObject st2 ;
                    JSONArray records2 ;
                     JSONObject emp2 = null;

                    while ( keys.hasNext () ) {
                        String key = ( String ) keys.next ();
                        Object value = jObject.get ( key );
                        map.put ( key , value );
                    }

                    JSONObject st = ( JSONObject ) map.get ( "data" );
                    JSONArray records = st.getJSONArray ( "records" );

                    JSONObject emp = null;

                    String [] rmValues  = null; 

                    for ( int i = 0 ; i < records.length () ; i ++ ) {
                        emp = records.getJSONObject ( i );
                        rmValues = new String[6] ; 
                        rmValues[0] =  emp.get( "FG_ID" ).toString() ;
                        rmValues[1] =  emp.get( "PART_NAME" ).toString() ;
                        ///rmValues[2] =  emp.get( "REORDER_LEVEL" ).toString() ;
                        //rmValues[2] =  emp.get( "FG_UOM_ID" ).toString() ;
                       

                        //values = new String[]{    emp.get( "RM_ID" ).toString() , emp.get( "RM_NAME" ).toString(), emp.get( "REORDER_LEVEL" ).toString(), emp.get( "RMM_UOM_ID" ).toString() , emp.get( "RM_RATE" ).toString()       }  ;
                        //rmList.add(   new String[]{    emp.get( "RM_ID" ).toString() , emp.get( "RM_NAME" ).toString(), emp.get( "REORDER_LEVEL" ).toString(), emp.get( "RMM_UOM_ID" ).toString() , emp.get( "RM_RATE" ).toString()       }           )    ;

                        addEmpAPICall2 = "latestfgstock?fgid="+emp.get( "FG_ID" ).toString();
                        result3 = WebAPITester.prepareWebCall ( addEmpAPICall2 , StaticValues.getHeader() , "" );
                        map2 = new HashMap<String , Object> ();
                        jObject2 = new JSONObject ( result3 );
                        keys2 = jObject2.keys ();

                        while ( keys2.hasNext () ) {
                            String key = ( String ) keys2.next ();
                            Object value = jObject2.get ( key );
                            map2.put ( key , value );
                        }

                        st2 = ( JSONObject ) map2.get ( "data" );
                        records2 = st2.getJSONArray ( "records" );

                        emp2 = null;

                        for ( int j = 0 ; j < records2.length () ; j ++ ) {
                            emp2 = records2.getJSONObject ( j );
                            rmValues[2] = emp2.get( "CLOSING" ).toString() ;
                            //rmList.add(   new String[]{    emp.get( "RM_ID" ).toString() , emp.get( "RM_NAME" ).toString(), emp.get( "REORDER_LEVEL" ).toString(), emp.get( "RMM_UOM_ID" ).toString() , emp.get( "RM_RATE" ).toString()       }           )    ;
                        }

                         rmValues[3] =  emp.get( "PROD_COST" ).toString() ;
                        
                        rmList2.add( rmValues);
                    }

                    buildTableModelFGABC( rmList2 );

                    System.out.println ( ""+rmList2.size() );
            }
    }
    
    public void loadSalesVsProduction(){

        // select a part from finishedgoods
        // select customer and order_quantities  from sales order data for each part
        //  
    }
    
    
    public static void buildTableModelFGABC ( ArrayList<String[]> rs  )
             {

        

        double totalStock = 0.0;
        double totalStockValue = 0.0;
        Vector<String> columnNames = new Vector<String>();
        
            columnNames.add ( "FG Name " );
            columnNames.add ( "Closing Stock" );
            columnNames.add ( "Cost" );
            columnNames.add ( "Stock Value" );
            columnNames.add ( "% of Value " );
            columnNames.add ( "Cummulative" );
            columnNames.add ( "Category" );

            //}
            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
            for(     int rmListCounter =0 ;     rmListCounter<rs.size();    rmListCounter++       ){
                    Vector<Object> vector = new Vector<Object> ();
                    String[] values = rs.get( rmListCounter );
                    for(    int valuesCounter =1;   valuesCounter<values.length;     valuesCounter++       ){
           
                        vector.add(     values[  valuesCounter  ]  );
                        
//                        if( valuesCounter == 4  ){
//                            try{
//                                totalStock = totalStock + Double.parseDouble ( values[  valuesCounter  ] );
//                            }catch(  NumberFormatException e   ){
//                                //totalStock = totalStock + Double.parseDouble ( values[  valuesCounter  ] );
//                            }
//                        }
                    }
                    
                    try{
                        vector.add(3,   Double.parseDouble( vector.get(1).toString ()) *  Double.parseDouble( vector.get(2).toString ()) ) ;
                        totalStockValue = totalStockValue +  Double.parseDouble( vector.get(1).toString ()) *  Double.parseDouble( vector.get(2).toString ()) ;
                        
                        data.add ( vector );
                        
                    }catch( NumberFormatException | NullPointerException e ){
                        
                    }
                    

            }
        
        
        
            for ( int i = 0 ; i < data.size () - 1 ; i ++ ) {

                for ( int j = i + 1 ; j < data.size () ; j ++ ) {

                    Vector<Object> abc1 = data.get ( i );
                    Vector<Object> abc2 = data.get ( j );

                    try{
                        if ( Double.parseDouble ( abc1.get ( 2 ).toString () ) < Double.parseDouble ( abc2.get ( 2 ).toString () ) ) {
                            //   data.remove ( i );
                            data.remove ( j );
                            data.add ( i , abc2 );
                            //  data.add ( j, abc1 );
                        }
                    }catch( NumberFormatException e){
                        
                    }
                }
            }

            double singleStock, percent, oldPercent = 0.0;

            for ( int i = 0 ; i < data.size () ; i ++ ) {

                Vector<Object> abc = data.get ( i );

                try{
                    singleStock = Double.parseDouble ( abc.get ( 3 ).toString () );
                }catch( NumberFormatException e){
                    singleStock = 0 ;
                }


                percent = ( singleStock / totalStockValue ) * 100;

                abc.add ( 4 , df.format ( percent ) );
                abc.add ( 5 , df.format ( percent + oldPercent ) );
                oldPercent = oldPercent + percent;

                abc.add ( 6 , "Category" );

                data.remove ( i );
                data.add ( i , abc );
            }

            jTable1.setModel ( new DefaultTableModel ( data , columnNames ) );

           

    }
    
    public void loadContent_OLD () {
        ResultSet result = null;
        try {
//            result = DB_Operations.executeSingle ( "SELECT MAX(RMStock_TR_ID), RM_TYPE, SUM(OPENING),SUM(RECEIVED),SUM(USED),SUM(CLOSING) FROM raw_material A INNER JOIN RMStock B ON A.RM_ID = B.RMI_ID GROUP BY RM_TYPE" );
//            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
//            if ( result != null ) {
//                jTable2.setModel ( buildTableModel ( result , "RM" ) );
//                jTable2.getTableHeader ().setFont ( new Font ( "Leelawadee UI" , Font.PLAIN , 10 ) );
//            } else {
//
//                System.out.println ( "No result to show" );
//            }
//
//            try {
//                result.close ();
//            } catch ( SQLException e ) {
//                System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
//            }

            result = DB_Operations.executeSingle ( "select * from RMABCVIEW" );
            //jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
            if ( result != null ) {
                //jTable2.setModel ( buildTableModelRMABC ( result , "RM" ) );
                buildTableModelRMABC_OLD ( result , "RM" );
                jTable2.getTableHeader ().setFont ( new Font ( "Leelawadee UI" , Font.PLAIN , 10 ) );
            } else {

                System.out.println ( "No result to show" );
            }

            try {
                result.close ();
            } catch ( SQLException e ) {
                System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

            //result = DB_Operations.executeSingle ( "SELECT MAX(FGStock_TR_ID), PART_NAME, OPENING,RECEIVED,USED,CLOSING FROM finished_goods A INNER JOIN FGStock B ON A.FG_ID = B.FG_ID GROUP BY PART_NAME" );
            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
            
             result = DB_Operations.executeSingle ( "select * from FGABCVIEW" );
            if ( result != null ) {
                //jTable1.setModel ( buildTableModel ( result , "FG" ) );
                buildTableModelFGABC_OLD ( result , "FG" );
                jTable1.getTableHeader ().setFont ( new Font ( "Leelawadee UI" , Font.PLAIN , 10 ) );
            } else {
                System.out.println ( "No result to show" );
            }

            try {
                result.close ();
            } catch ( SQLException e ) {
                System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

//  --------------------------------------------------- calculate purchase order wise and product wise ordered quantities and sum of its customer wise values  --------------------------------------------
            //ArrayList<customerproductrateqty> data = new ArrayList<customerproductrateqty>();
            ArrayList<String[]> products = new ArrayList<String[]> ();
            ArrayList<String> customers = new ArrayList<String> ();

            ArrayList<String> values = null;

            result = DB_Operations.executeSingle ( "select distinct(so_customer_id) from sales_orders" );

            if ( result.isBeforeFirst () ) {
                while ( result.next () ) {
                    //data.add( new customerproductrateqty ( result.getInt ( 1), result.getInt ( 2),result.getInt ( 3)) );
                    customers.add ( result.getString ( 1 ) );
                }
            }

            try {
                result.close ();
            } catch ( SQLException e ) {
                System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

            result = DB_Operations.executeSingle ( "select distinct(product_id), (select PART_NAME from finished_goods where FG_ID in (product_id) ) from po_order_details  " );
            if ( result.isBeforeFirst () ) {
                while ( result.next () ) {
                    //data.add( new customerproductrateqty ( result.getInt ( 1), result.getInt ( 2),result.getInt ( 3)) );
                    products.add ( new String[] { result.getString ( 1 ) , result.getString ( 2 ) } );

                }
            }

            try {
                result.close ();
            } catch ( SQLException e ) {
                System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

            for ( int i = 0 ; i < products.size () ; i ++ ) {
                values = new ArrayList<String> ();
                result = DB_Operations.executeSingle ( "select so_customer_id, product_id,  SUM(product_qty)  from sales_orders A inner join po_order_details B where A.sales_po_id = B.ref_po_id  AND B.product_id = " + products.get ( i )[ 0 ] );

                values.add ( products.get ( i )[ 1 ] );
                if ( result.isBeforeFirst () ) {
                    //   while(result.next ()){ 
                    //data.add( new customerproductrateqty ( result.getInt ( 1), result.getInt ( 2),result.getInt ( 3)) );
                    values.add ( result.getString ( 3 ) );
                    //    }
                }

                try {
                    result.close ();
                } catch ( SQLException e ) {
                    System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                    StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

                double orderValue = 0.0;
                for ( int j = 0 ; j < customers.size () ; j ++ ) {

                    result = DB_Operations.executeSingle ( "select so_customer_id, product_id,  SUM(product_qty), ( select SALES_RATE from FG_CUSTOMER_MAP where M_FG_ID = " + products.get ( i )[ 0 ] + " AND M_CUST_ID = " + customers.get ( j ) + " )*SUM(product_qty) as'Value'  from sales_orders A inner join po_order_details B where A.sales_po_id = B.ref_po_id AND A.so_customer_id = " + customers.get ( j ) + " AND B.product_id = " + products.get ( i )[ 0 ] + "" );

                    if ( result.isBeforeFirst () ) {
                        try {
                            orderValue = orderValue + Double.parseDouble ( result.getString ( 4 ) );
                        } catch ( NullPointerException e ) {
                             orderValue = 0 ;
                            
                            StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                        }
                    }
                }

                try {
                    result.close ();
                } catch ( SQLException e ) {
                    System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                    StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

                values.add ( df.format ( Double.parseDouble ( "" + orderValue ) ) );

                result = DB_Operations.executeSingle ( "select MAX(FGStock_TR_ID), CLOSING from FGStock where FG_ID =" + products.get ( i )[ 0 ] );
                if ( result.isBeforeFirst () && result != null ) {
                    if ( result.getString ( 2 ) != null ) {
                        values.add ( result.getString ( 2 ) );
                    } else {
                        values.add ( "0" );
                    }
                }

                try {
                    result.close ();
                } catch ( SQLException e ) {
                    System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                    StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

                double producedValue = 0.0;
                for ( int j = 0 ; j < products.size () ; j ++ ) {
                    producedValue = 0.0;
                    result = DB_Operations.executeSingle ( " select  ( select SALES_RATE from finished_goods where FG_ID = " + products.get ( i )[ 0 ] + " )*  ( select CLOSING from FGStock where FG_ID = " + products.get ( i )[ 0 ] + " AND FGStock_TR_ID = ( select MAX(FGStock_TR_ID) from FGStock ) ) " );

                    if ( result.isBeforeFirst () ) {
                        try {
                            producedValue = producedValue + Double.parseDouble ( result.getString ( 1 ) );
                        } catch ( NullPointerException e ) {
                            StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                        }
                    }
                }

                values.add ( df.format ( Double.parseDouble ( "" + producedValue ) ) );

                try {
                    result.close ();
                } catch ( SQLException e ) {
                    System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                    StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

                int targetProdcution = ( Integer.parseInt ( values.get ( 3 ) ) - Integer.parseInt ( values.get ( 1 ) ) );
                if ( targetProdcution >= 0 ) {
                    values.add ( "Not Required" );
                } else {
                    values.add ( "" + targetProdcution );
                }

                data.add ( values );

                values = null;
            }

            jTable3.setModel ( buildTableModelUsingArrayList () );
            jTable3.getTableHeader ().setFont ( new Font ( "Leelawadee UI" , Font.PLAIN , 10 ) );

        } catch ( SQLException e ) {
            //System.out.println( "Error No result to show " +e.getMessage());
            try {
                result.close ();
            } catch ( Exception e2 ) {
                System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e2.toString () );
                StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
            StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

    }
    
    public static void buildTableModelRMABC_OLD ( ResultSet rs , String table )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        double totalStock = 0.0;
        Vector<String> columnNames = null;
        try {
            // names of columns
            columnNames = new Vector<String> ();
            int columnCount = metaData.getColumnCount ();
            for ( int column = 1 ; column <= columnCount ; column ++ ) {
                columnNames.add ( metaData.getColumnName ( column ) );
            }
            columnNames.add ( "% of Value " );
            columnNames.add ( "Cummulative" );
            columnNames.add ( "Category" );

            //}
            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
            while ( rs.next () ) {
                Vector<Object> vector = new Vector<Object> ();
                for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {

                    String abc = rs.getObject ( columnIndex ).toString ();
                    vector.add ( abc );

                    if ( columnIndex == 5 ) {
                        totalStock = totalStock + Double.parseDouble ( abc );
                    }
                }

                data.add ( vector );
            }

            for ( int i = 0 ; i < data.size () - 1 ; i ++ ) {

                for ( int j = i + 1 ; j < data.size () ; j ++ ) {

                    Vector<Object> abc1 = data.get ( i );
                    Vector<Object> abc2 = data.get ( j );

                    if ( Double.parseDouble ( abc1.get ( 4 ).toString () ) < Double.parseDouble ( abc2.get ( 4 ).toString () ) ) {
                        //   data.remove ( i );
                        data.remove ( j );
                        data.add ( i , abc2 );
                        //  data.add ( j, abc1 );

                    }
                }
            }

            double singleStock, percent, oldPercent = 0.0;

            for ( int i = 0 ; i < data.size () ; i ++ ) {

                Vector<Object> abc = data.get ( i );
                singleStock = Double.parseDouble ( abc.get ( 4 ).toString () );
                percent = ( singleStock / totalStock ) * 100;

                abc.add ( 5 , df.format ( percent ) );
                abc.add ( 6 , df.format ( percent + oldPercent ) );
                oldPercent = oldPercent + percent;

                abc.add ( 7 , "Category" );

                data.remove ( i );
                data.add ( i , abc );
            }

            jTable2.setModel ( new DefaultTableModel ( data , columnNames ) );

            try {
                rs.close ();
            } catch ( SQLException e ) {
                System.out.println ( "" );
                StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

            //  return new DefaultTableModel ( data , columnNames );
        } catch ( Exception e ) {
            System.out.println ( "" );
            StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            //   return null ;
        }

    }

    public static void buildTableModelFGABC_OLD ( ResultSet rs , String table )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        double totalStock = 0.0;
        Vector<String> columnNames = null;
        try {
            // names of columns
            columnNames = new Vector<String> ();
            int columnCount = metaData.getColumnCount ();
            for ( int column = 1 ; column <= columnCount ; column ++ ) {
                columnNames.add ( metaData.getColumnName ( column ) );
            }
            columnNames.add ( "% of Value " );
            columnNames.add ( "Cummulative" );
            columnNames.add ( "Category" );

            //}
            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
            while ( rs.next () ) {
                Vector<Object> vector = new Vector<Object> ();
                for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {

                    String abc = rs.getObject ( columnIndex ).toString ();
                    vector.add ( abc );

                    if ( columnIndex == 5 ) {
                        totalStock = totalStock + Double.parseDouble ( abc );
                    }
                }

                data.add ( vector );
            }

            for ( int i = 0 ; i < data.size () - 1 ; i ++ ) {

                for ( int j = i + 1 ; j < data.size () ; j ++ ) {

                    Vector<Object> abc1 = data.get ( i );
                    Vector<Object> abc2 = data.get ( j );

                    if ( Double.parseDouble ( abc1.get ( 4 ).toString () ) < Double.parseDouble ( abc2.get ( 4 ).toString () ) ) {
                        //   data.remove ( i );
                        data.remove ( j );
                        data.add ( i , abc2 );
                        //  data.add ( j, abc1 );

                    }
                }
            }

            double singleStock, percent, oldPercent = 0.0;

            for ( int i = 0 ; i < data.size () ; i ++ ) {

                Vector<Object> abc = data.get ( i );
                singleStock = Double.parseDouble ( abc.get ( 4 ).toString () );
                percent = ( singleStock / totalStock ) * 100;

                abc.add ( 5 , df.format ( percent ) );
                abc.add ( 6 , df.format ( percent + oldPercent ) );
                oldPercent = oldPercent + percent;

                abc.add ( 7 , "Category" );

                data.remove ( i );
                data.add ( i , abc );
            }

            jTable1.setModel ( new DefaultTableModel ( data , columnNames ) );

            try {
                rs.close ();
            } catch ( SQLException e ) {
                System.out.println ( "" );
                StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

            //  return new DefaultTableModel ( data , columnNames );
        } catch ( Exception e ) {
            System.out.println ( "" );
            StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            //   return null ;
        }

    }

    
//        public void update dataRMDataSequence(){
//            
//            
//            
//        }
    public static DefaultTableModel buildTableModel ( ResultSet rs , String table )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        int columnCount = metaData.getColumnCount ();
        //for (int column = 1; column <= 5; column++) {
        if ( table.equalsIgnoreCase ( "RM" ) ) {
            columnNames.add ( "RM" );
        } else if ( table.equalsIgnoreCase ( "FG" ) ) {
            columnNames.add ( "FG" );
        }
        columnNames.add ( "OP" );
        columnNames.add ( "RC" );
        columnNames.add ( "US" );
        columnNames.add ( "CL" );

        //}
        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        while ( rs.next () ) {
            Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 2 ; columnIndex <= 6 ; columnIndex ++ ) {
                vector.add ( rs.getObject ( columnIndex ) );

            }
            data.add ( vector );
        }

        try {
            rs.close ();
        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        return new DefaultTableModel ( data , columnNames );

    }

    public DefaultTableModel buildTableModelUsingArrayList ()
            throws SQLException {

        // names of columns
        Vector<String> columnNames = new Vector<String> ();

        columnNames.add ( "<html><center>Product<br>Name</center></home>" );
        columnNames.add ( "<html><center>Cummulative<br>Order Qty</center></home>" );
        columnNames.add ( "<html><center>Cummulative<br>Order Value</center></home>" );
        columnNames.add ( "<html><center>Stock <br>In Hand</center></home>" );
        columnNames.add ( "<html><center>Produced <br>Value</center></home>" );
        columnNames.add ( "<html><center>Shortfall</center></home>" );

        //}
        // data of the table
        Vector<Vector<String>> tableData = new Vector<Vector<String>> ();

        Vector<String> vector = null;
        ArrayList<String> val = null;
        for ( int columnIndex = 0 ; columnIndex < data.size () ; columnIndex ++ ) {
            vector = new Vector<String> ();
            val = new ArrayList<String> ();
            val = data.get ( columnIndex );
            for ( int i = 0 ; i < 6 ; i ++ ) {
                vector.add ( val.get ( i ) );
            }

            tableData.add ( vector );
        }

        return new DefaultTableModel ( tableData , columnNames );

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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1310, 600));
        setLayout(null);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Finished Good ABC Analysis Report", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Leelawadee UI", 0, 13))); // NOI18N
        jScrollPane1.setOpaque(false);

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
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
        jScrollPane1.setBounds(0, 160, 460, 150);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Raw Material ABC Analysis Report", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Leelawadee UI", 0, 13))); // NOI18N
        jScrollPane2.setOpaque(false);

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setShowHorizontalLines(false);
        jTable2.setShowVerticalLines(false);
        jTable2.getTableHeader().setResizingAllowed(false);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);

        add(jScrollPane2);
        jScrollPane2.setBounds(0, 0, 460, 160);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Product wise order quantities, ustomer wise order values, shortfall in order quantity and current stocks", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Leelawadee UI", 0, 13))); // NOI18N
        jScrollPane3.setOpaque(false);

        jTable3.setFont(new java.awt.Font("Leelawadee UI", 0, 10));
        jTable3.setAutoCreateRowSorter(true);
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "<html><center>Product<br>Name</center></html>", "<html><center>Cummulative<br>Order Qty</center></html>", "<html><center>Cummulative<br>Order Value</center></html>", "<html><center>Stock <br>In Hand</center></html>", "<html><center>Prodution<br> Required</center></html>"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setOpaque(false);
        jTable3.setShowHorizontalLines(false);
        jTable3.setShowVerticalLines(false);
        jTable3.getTableHeader().setResizingAllowed(false);
        jTable3.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable3);

        add(jScrollPane3);
        jScrollPane3.setBounds(460, 0, 540, 160);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTable jTable1;
    protected static javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables
}


        
//  --------------------------------------------------- calculate purchase order wise and product wise ordered quantities and sum of its customer wise values  --------------------------------------------
            //ArrayList<customerproductrateqty> data = new ArrayList<customerproductrateqty>();
/*            ArrayList<String[]> products = new ArrayList<String[]> ();
            ArrayList<String> customers = new ArrayList<String> ();

            ArrayList<String> values = null;

            result = DB_Operations.executeSingle ( "select distinct(so_customer_id) from sales_orders" );

            if ( result.isBeforeFirst () ) {
                while ( result.next () ) {
                    //data.add( new customerproductrateqty ( result.getInt ( 1), result.getInt ( 2),result.getInt ( 3)) );
                    customers.add ( result.getString ( 1 ) );
                }
            }

            try {
                result.close ();
            } catch ( SQLException e ) {
                System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

            result = DB_Operations.executeSingle ( "select distinct(product_id), (select PART_NAME from finished_goods where FG_ID in (product_id) ) from po_order_details  " );
            if ( result.isBeforeFirst () ) {
                while ( result.next () ) {
                    //data.add( new customerproductrateqty ( result.getInt ( 1), result.getInt ( 2),result.getInt ( 3)) );
                    products.add ( new String[] { result.getString ( 1 ) , result.getString ( 2 ) } );

                }
            }

            try {
                result.close ();
            } catch ( SQLException e ) {
                System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

            for ( int i = 0 ; i < products.size () ; i ++ ) {
                values = new ArrayList<String> ();
                result = DB_Operations.executeSingle ( "select so_customer_id, product_id,  SUM(product_qty)  from sales_orders A inner join po_order_details B where A.sales_po_id = B.ref_po_id  AND B.product_id = " + products.get ( i )[ 0 ] );

                values.add ( products.get ( i )[ 1 ] );
                if ( result.isBeforeFirst () ) {
                    //   while(result.next ()){ 
                    //data.add( new customerproductrateqty ( result.getInt ( 1), result.getInt ( 2),result.getInt ( 3)) );
                    values.add ( result.getString ( 3 ) );
                    //    }
                }

                try {
                    result.close ();
                } catch ( SQLException e ) {
                    System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                    StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

                double orderValue = 0.0;
                for ( int j = 0 ; j < customers.size () ; j ++ ) {

                    result = DB_Operations.executeSingle ( "select so_customer_id, product_id,  SUM(product_qty), ( select SALES_RATE from FG_CUSTOMER_MAP where M_FG_ID = " + products.get ( i )[ 0 ] + " AND M_CUST_ID = " + customers.get ( j ) + " )*SUM(product_qty) as'Value'  from sales_orders A inner join po_order_details B where A.sales_po_id = B.ref_po_id AND A.so_customer_id = " + customers.get ( j ) + " AND B.product_id = " + products.get ( i )[ 0 ] + "" );

                    if ( result.isBeforeFirst () ) {
                        try {
                            orderValue = orderValue + Double.parseDouble ( result.getString ( 4 ) );
                        } catch ( NullPointerException e ) {
                             orderValue = 0 ;
                            
                            StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                        }
                    }
                }

                try {
                    result.close ();
                } catch ( SQLException e ) {
                    System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                    StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

                values.add ( df.format ( Double.parseDouble ( "" + orderValue ) ) );

                result = DB_Operations.executeSingle ( "select MAX(FGStock_TR_ID), CLOSING from FGStock where FG_ID =" + products.get ( i )[ 0 ] );
                if ( result.isBeforeFirst () && result != null ) {
                    if ( result.getString ( 2 ) != null ) {
                        values.add ( result.getString ( 2 ) );
                    } else {
                        values.add ( "0" );
                    }
                }

                try {
                    result.close ();
                } catch ( SQLException e ) {
                    System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                    StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

                double producedValue = 0.0;
                for ( int j = 0 ; j < products.size () ; j ++ ) {
                    producedValue = 0.0;
                    result = DB_Operations.executeSingle ( " select  ( select SALES_RATE from finished_goods where FG_ID = " + products.get ( i )[ 0 ] + " )*  ( select CLOSING from FGStock where FG_ID = " + products.get ( i )[ 0 ] + " AND FGStock_TR_ID = ( select MAX(FGStock_TR_ID) from FGStock ) ) " );

                    if ( result.isBeforeFirst () ) {
                        try {
                            producedValue = producedValue + Double.parseDouble ( result.getString ( 1 ) );
                        } catch ( NullPointerException e ) {
                            StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                        }
                    }
                }

                values.add ( df.format ( Double.parseDouble ( "" + producedValue ) ) );

                try {
                    result.close ();
                } catch ( SQLException e ) {
                    System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                    StaticValues.writer.writeExcel ( InventoryWidget.class.getSimpleName () , InventoryWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

                int targetProdcution = ( Integer.parseInt ( values.get ( 3 ) ) - Integer.parseInt ( values.get ( 1 ) ) );
                if ( targetProdcution >= 0 ) {
                    values.add ( "Not Required" );
                } else {
                    values.add ( "" + targetProdcution );
                }

                data.add ( values );

                values = null;
            }

            jTable3.setModel ( buildTableModelUsingArrayList () );
            jTable3.getTableHeader ().setFont ( new Font ( "Leelawadee UI" , Font.PLAIN , 10 ) );
*/