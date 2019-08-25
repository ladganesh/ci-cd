/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class DeliveryScheduleReport extends javax.swing.JPanel {

    /**
     * Creates new form DeliveryScheduleReport
     */
    public DeliveryScheduleReport () {
        initComponents ();
        
        String getcustomerList = "salesorders";
        String salesOrderData = WebAPITester.prepareWebCall ( getcustomerList , StaticValues.getHeader () , "" );

        if( !  salesOrderData.contains( "not found"  )  ){
            jTable1.setModel ( buildTableModelfromJSON ( salesOrderData ) );
            jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
            jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
            jTable1.getColumnModel ().getColumn ( 4 ).setMinWidth ( 0 );
            jTable1.getColumnModel ().getColumn ( 4 ).setMaxWidth ( 0 );
        }
        
        SalesMaster.hideDialog () ;
    }

    public DefaultTableModel buildTableModelfromJSON ( String employeeJSONList ) {

        SalesMaster.progress.updateTitle ( "Showing Full Delivery Schedue Report" );
        SalesMaster.progress.updateMessage ( "Processing Data ");
        
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
        columnNames.add ( "CUSTOMER_NAME" );
        columnNames.add ( "order_no" );
        columnNames.add ( "po_date" );
        columnNames.add ( "oder_detail_id" );
        columnNames.add ( "PART_NAME" );
        columnNames.add ( "product_qty" );
        columnNames.add ( "eff_rate" );
        columnNames.add ( "delry_qty" );
        columnNames.add ( "dlry_date" );
        columnNames.add ( "delry_status" );
        
        
        columnNames2 = new Vector<String> ();
        columnNames2.add ( "PO ID" );
        columnNames2.add ( "Customer" );
        columnNames2.add ( "Order NO" );
        columnNames2.add ( "Order Date" );
        columnNames2.add ( "oder_detail_id" );
         columnNames2.add ( "Part Name" );
         columnNames2.add ( "Total Qty" );
        columnNames2.add ( "Rate" );
         columnNames2.add ( "Part Qty" );
        columnNames2.add ( "Del. Date" );
        columnNames2.add ( "Del. Status" );

        
        Vector<Object> vector = new Vector<Object> ();
        Vector<Object> vector2 = new Vector<Object> ();
        Vector<Object> vector3 = new Vector<Object> ();
       
        int counter1 = 0, counter2 = 1, counter3 =2;
        for ( int i = 0 ; i < records.length () ; i ++ ) {
            
            vector = new Vector<Object> ();
            emp = records.getJSONObject ( i );

            for ( int columnIndex = 0 ; columnIndex < 4 ; columnIndex ++ ) {
                
                vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                
                if( columnIndex == 0  ){
                    
                   String getcustomerList1 = "podetails?ref_po_id="+ emp.get ( columnNames.get ( columnIndex ) ) ;
                    String salesOrderData1 = WebAPITester.prepareWebCall ( getcustomerList1 , StaticValues.getHeader () , "" );

                    HashMap<String , Object> map1 = new HashMap<String , Object> ();
                    JSONObject jObject1 = new JSONObject ( salesOrderData1 );
                    Iterator<?> keys1 = jObject1.keys ();

                    while ( keys1.hasNext () ) {
                        String key = ( String ) keys1.next ();
                        Object value = jObject1.get ( key );
                        map1.put ( key , value );
                    }

                    JSONObject st1 = ( JSONObject ) map1.get ( "data" );
                    JSONArray records1 = st1.getJSONArray ( "records" );

                    JSONObject parts = null;

                    for ( int j = 0 ; j < records1.length () ; j ++ ) {
                         vector2 = new Vector<Object> ();
                         for ( int columnIndex4 = 0 ; columnIndex4 < 4 ; columnIndex4 ++ ) {
                                vector2.add ( ""  );
                            }
                        parts = records1.getJSONObject ( j );
                        for ( int columnIndex2 = 4 ; columnIndex2 < 8 ; columnIndex2 ++ ) {
                           
                            //System.out.println ( ""+columnNames.get ( columnIndex2 ) + "    "+parts.get ( columnNames.get ( columnIndex2 ) ) );
                            vector2.add ( parts.get ( columnNames.get ( columnIndex2 ) ) );
                            
                            if( columnIndex2 == 4  ){ 
                               
                            
                                String getcustomerList2 = "deliveryschedules?po_details_id="+ parts.get ( columnNames.get ( columnIndex2 ) ) ;
                                String salesOrderData2 = WebAPITester.prepareWebCall ( getcustomerList2 , StaticValues.getHeader () , "" );

                                if( ! salesOrderData2.contains ( "\"totalrecords\":0" ) && ! salesOrderData2.contains ( "\"results\":\"null\"" ) ){ 
                                    HashMap<String , Object> map2 = new HashMap<String , Object> ();
                                    JSONObject jObject2 = new JSONObject ( salesOrderData2 );
                                    Iterator<?> keys2 = jObject2.keys ();

                                    while ( keys2.hasNext () ) {
                                        String key = ( String ) keys2.next ();
                                        Object value = jObject2.get ( key );
                                        map2.put ( key , value );
                                    }

                                    JSONObject st2 = ( JSONObject ) map2.get ( "data" );
                                    JSONArray records2 = st2.getJSONArray ( "records" );

                                    JSONObject schedule = null;
                                    for ( int k = 0 ; k < records2.length () ; k++ ) {
                                         vector3 = new Vector<Object> ();
                                         for ( int columnIndex4 = 0 ; columnIndex4 < 8 ; columnIndex4 ++ ) {
                                                vector3.add ( "" );
                                         }
                                        schedule = records2.getJSONObject ( k );
                                        for ( int columnIndex3 = 8 ; columnIndex3 < 10 ; columnIndex3 ++ ) {                                      
                                            vector3.add ( schedule.get ( columnNames.get ( columnIndex3 ) ) );
                                        }

                                        data.add ( 0 , vector3 );
                                    }
                                }
                            }
                        }
                        
                        data.add( 0,  vector2 );
                    
                    }
                }
            }
            data.add ( 0,   vector );
            
        }
        return new DefaultTableModel ( data , columnNames2 );
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

        setLayout(null);

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
        jScrollPane1.setBounds(0, 0, 1080, 500);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
