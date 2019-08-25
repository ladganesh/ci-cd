/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Widget;

import trixware.erp.prodezydesktop.Model.StaticValues;
import static trixware.erp.prodezydesktop.examples.InventoryWidget1.df;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author dell
 */

public class SaleOrderAnalysisTable extends javax.swing.JPanel {

    /**
     * Creates new form FinishedGoodAnalysisTable
     * Finished goods analysis report show in widget
     */
     ArrayList<String[]> rmList2 = new ArrayList<String[]> ();
     
    public SaleOrderAnalysisTable() {
        initComponents();
        setSize(400, 200);
        
        loadContentFGABC();
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
     public void buildTableModelFGABC ( ArrayList<String[]> rs  )
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(400, 200));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Finished Good ABC Analysis Report", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Leelawadee UI", 0, 13))); // NOI18N
        jScrollPane1.setOpaque(false);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(100, 100));

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane1.getAccessibleContext().setAccessibleName("Sales Order ABC Analysis Report");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
