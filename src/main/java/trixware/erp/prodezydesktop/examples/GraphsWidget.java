/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class GraphsWidget extends javax.swing.JPanel {

    ArrayList<String[]> rmList = new ArrayList<String[]> ();
    ArrayList<String[]> rmList2 = new ArrayList<String[]> ();
    public static ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>> ();
    public static DecimalFormat df = new DecimalFormat ( "#.##" );

    static ChartPanel cp = null , cp1 = null , cp2 = null, cp3 = null;
    JFreeChart chart = null;
    public static DefaultCategoryDataset dataSet = null;
    StringBuilder sb1 = null ;
    
    /**
     * Creates new form InventoryWidget
     */
    public GraphsWidget () {
        initComponents ();

        sb1 = new StringBuilder ();
        sb1.append ( StaticValues.productionByQtyCall );
        
        cp1= new CommonGraphPanel1(   "Production Analysis 1" , "", ""  ).getPreparedCP ();
        cp1.setBounds ( 0 , 0 , 320 , 200 );
        add ( cp1 );
        
        cp2= new CommonGraphPanel2(  "Production Analysis 2" , "", ""  ).getPreparedCP ();
        cp2.setBounds ( 325 , 0,  320 , 200 );
        add ( cp2 );
        
        cp3= new CommonGraphPanel1(  "Production Analysis 3" , "", ""  ).getPreparedCP ();
        cp3.setBounds ( 650  , 0 ,  320 , 200 );
        add ( cp3 );
        
        cp= new CommonGraphPanel2(   "Production Analysis 4" , "", ""  ).getPreparedCP ();
        cp.setBounds ( 0 , 210 , 320 , 200 );
        add ( cp );
        
        
        
        
        Thread t = new Thread(){
            public void run(){
                //loadContent();
                //loadContentFGABC();
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
            

            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
            for(     int rmListCounter =0 ;     rmListCounter<rs.size();    rmListCounter++       ){
                    Vector<Object> vector = new Vector<Object> ();
                    String[] values = rs.get( rmListCounter );
                    for(    int valuesCounter =1;   valuesCounter<values.length;     valuesCounter++       ){
           
                        vector.add(     values[  valuesCounter  ]  );
                        

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
                
                percent = ( singleStock / totalStockValue ) * 100;
 
                abc.add ( 5 , df.format ( percent ) );
                abc.add ( 6 , df.format ( percent + oldPercent ) );
                oldPercent = oldPercent + percent;

                abc.add ( 7 , "Category" );

                data.remove ( i );
                data.add ( i , abc );
            }
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
                StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
            StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                        }

                         rmValues[3] =  emp.get( "PROD_COST" ).toString() ;
                        
                        rmList2.add( rmValues);
                    }

                    buildTableModelFGABC( rmList2 );

                    System.out.println ( ""+rmList2.size() );
            }
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
    }
    
    public void loadContent_OLD () {
        ResultSet result = null;
        try {

            result = DB_Operations.executeSingle ( "select * from RMABCVIEW" );
            //jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
            if ( result != null ) {
                //jTable2.setModel ( buildTableModelRMABC ( result , "RM" ) );
                buildTableModelRMABC_OLD ( result , "RM" );
            } else {

                System.out.println ( "No result to show" );
            }

            try {
                result.close ();
            } catch ( SQLException e ) {
                System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

         
             result = DB_Operations.executeSingle ( "select * from FGABCVIEW" );
            if ( result != null ) {
                //jTable1.setModel ( buildTableModel ( result , "FG" ) );
                buildTableModelFGABC_OLD ( result , "FG" );
            } else {
                System.out.println ( "No result to show" );
            }

            try {
                result.close ();
            } catch ( SQLException e ) {
                System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                    StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

                double orderValue = 0.0;
                for ( int j = 0 ; j < customers.size () ; j ++ ) {

                    result = DB_Operations.executeSingle ( "select so_customer_id, product_id,  SUM(product_qty), ( select SALES_RATE from FG_CUSTOMER_MAP where M_FG_ID = " + products.get ( i )[ 0 ] + " AND M_CUST_ID = " + customers.get ( j ) + " )*SUM(product_qty) as'Value'  from sales_orders A inner join po_order_details B where A.sales_po_id = B.ref_po_id AND A.so_customer_id = " + customers.get ( j ) + " AND B.product_id = " + products.get ( i )[ 0 ] + "" );

                    if ( result.isBeforeFirst () ) {
                        try {
                            orderValue = orderValue + Double.parseDouble ( result.getString ( 4 ) );
                        } catch ( NullPointerException e ) {
                             orderValue = 0 ;
                            
                            StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                        }
                    }
                }

                try {
                    result.close ();
                } catch ( SQLException e ) {
                    System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                    StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                    StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

                double producedValue = 0.0;
                for ( int j = 0 ; j < products.size () ; j ++ ) {
                    producedValue = 0.0;
                    result = DB_Operations.executeSingle ( " select  ( select SALES_RATE from finished_goods where FG_ID = " + products.get ( i )[ 0 ] + " )*  ( select CLOSING from FGStock where FG_ID = " + products.get ( i )[ 0 ] + " AND FGStock_TR_ID = ( select MAX(FGStock_TR_ID) from FGStock ) ) " );

                    if ( result.isBeforeFirst () ) {
                        try {
                            producedValue = producedValue + Double.parseDouble ( result.getString ( 1 ) );
                        } catch ( NullPointerException e ) {
                            StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                        }
                    }
                }

                values.add ( df.format ( Double.parseDouble ( "" + producedValue ) ) );

                try {
                    result.close ();
                } catch ( SQLException e ) {
                    System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                    StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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

        } catch ( SQLException e ) {
            //System.out.println( "Error No result to show " +e.getMessage());
            try {
                result.close ();
            } catch ( Exception e2 ) {
                System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e2.toString () );
                StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
            StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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

            try {
                rs.close ();
            } catch ( SQLException e ) {
                System.out.println ( "" );
                StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

            //  return new DefaultTableModel ( data , columnNames );

            //  return new DefaultTableModel ( data , columnNames );
        } catch ( Exception e ) {
            System.out.println ( "" );
            StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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

            try {
                rs.close ();
            } catch ( SQLException e ) {
                System.out.println ( "" );
                StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

            //  return new DefaultTableModel ( data , columnNames );

            //  return new DefaultTableModel ( data , columnNames );
        } catch ( Exception e ) {
            System.out.println ( "" );
            StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
            StaticValues.writer.writeExcel (GraphsWidget.class.getSimpleName () , GraphsWidget.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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

    ArrayList<String[]> singleMonth = new ArrayList<String[]> ();
     String currentYear , nextYear, selectedMonth;
     SimpleDateFormat sdf2 = null ;
    public void showSingleMonths ( ) {

        singleMonth = new ArrayList<String[]>();   
        
        currentYear = new SimpleDateFormat("yyyy").format ( Calendar.getInstance ().getTime() );
        selectedMonth = new SimpleDateFormat("MMMM").format ( Calendar.getInstance ().getTime() );
        
        //formatFromDate (    );
        Calendar c = Calendar.getInstance ();
        Calendar c1 = Calendar.getInstance ();
        
        int lastDateOfMonth = c1.getActualMaximum ( Calendar.DAY_OF_MONTH );
        if( selectedMonth.equals( "January"  ) || selectedMonth.equals( "February"  ) || selectedMonth.equals( "March"  ) ){
               nextYear =  currentYear ;
                currentYear = String.valueOf(  Integer.parseInt(  currentYear  ) -  1 )  ;
        }
        switch ( selectedMonth ) {
             case "April":
                 c.set  ( Calendar.MONTH , 3 );
                break;
            case "May":
                c.set  ( Calendar.MONTH , 4 );
                break;
            case "June":
                 c.set ( Calendar.MONTH , 5 );
                break;
            case "July":
                 c.set  ( Calendar.MONTH , 6 );
                break;
            case "August":
                 c.set  ( Calendar.MONTH , 7 );
                break;
            case "September":
                 c.set  ( Calendar.MONTH , 8 );
                break;
            case "October":
                 c.set ( Calendar.MONTH , 9 );
                break;
            case "November":
                 c.set  ( Calendar.MONTH , 10 );
                break;
            case "December":
                 c.set  ( Calendar.MONTH , 11 );
                break;
            case "January":
                 c.set  ( Calendar.MONTH , 0 );
                break;
            case "February":
                 c.set  ( Calendar.MONTH , 1 );
                break;
            case "March":
                 c.set  ( Calendar.MONTH , 2 );
                break;
        }
        
        
        for ( int i = 1 ; i <= lastDateOfMonth ; i ++ ) {

            c.set ( Calendar.DATE , i );
            c.set ( Calendar.YEAR , Integer.parseInt (currentYear) );
            c.set ( Calendar.HOUR , 0 );
            c.set ( Calendar.MINUTE , 0 );
            c.set ( Calendar.SECOND , 0 );
            singleMonth.add ( new String[]{ sdf2.format ( c.getTime () ) + " 00:00:00" ,  new SimpleDateFormat("MMM-dd").format( c.getTime () ) });

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

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1310, 600));
        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
