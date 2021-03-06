/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Widget;

import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.examples.InventoryWidget1;
import static trixware.erp.prodezydesktop.examples.InventoryWidget1.df;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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
 * raw material analysis report show in widget
 */
public class RowMaterialAnalysisTable extends javax.swing.JPanel {

    /**
     * Creates new form RowMaterialAnalysisTable
     */
    ArrayList<String[]> rmList = new ArrayList<String[]> ();
    
    public RowMaterialAnalysisTable() {
        initComponents();
        
        //jScrollPane2.setBounds(0, 0, 300, 150);
        setSize(400, 200);
        loadContent();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
   public void buildTableModelRMABC ( ArrayList<String[]> rs  ) {

        

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

           //jTable2.setPreferredScrollableViewportSize(jTable1.getPreferredSize());

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
            
        } catch ( Exception e ) {
            //System.out.println( "Error No result to show " +e.getMessage());
           
            StaticValues.writer.writeExcel (InventoryWidget1.class.getSimpleName () , InventoryWidget1.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

    }        
            
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(400, 200));

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 442, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 191, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(23, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    protected static javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
