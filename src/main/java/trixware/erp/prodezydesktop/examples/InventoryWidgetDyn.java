/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Widget.COPQChart;
import trixware.erp.prodezydesktop.Widget.CostVsValueChart;
import trixware.erp.prodezydesktop.Widget.DefectOccurencesChart;
import trixware.erp.prodezydesktop.Widget.OEEChart;
import trixware.erp.prodezydesktop.Widget.PMPHChart;
import trixware.erp.prodezydesktop.Widget.ProductionChart;
import trixware.erp.prodezydesktop.Widget.ProductionValueChart;
import trixware.erp.prodezydesktop.Widget.ProductivityChart;
import trixware.erp.prodezydesktop.Widget.RejectionChart;
import trixware.erp.prodezydesktop.Widget.WorkInPrograssChart;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class InventoryWidgetDyn extends javax.swing.JPanel{

    ArrayList<String[]> rmList = new ArrayList<String[]> ();
    ArrayList<String[]> rmList2 = new ArrayList<String[]> ();
    public static ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>> ();
    public static DecimalFormat df = new DecimalFormat ( "#.##" );
    
    ProductionChart pc=null;
    ProductivityChart prodc=null;
    PMPHChart pmph=null;
    OEEChart oee=null;
    CostVsValueChart cvvc=null;
    COPQChart copq=null;
    WorkInPrograssChart wipc=null;
    ProductionValueChart pvc=null;
    RejectionChart rc=null;
    DefectOccurencesChart doc=null;
    
    JScrollPane scroll=null,sp=null;

    JPanel reportPanel;
    //JPanel tablePanel;

    /**
     * Creates new form InventoryWidgetDyn
     */
    public InventoryWidgetDyn() {
        initComponents();
//////*****************Report on dashboard edited by mayur*********************
        //try{

        // prodc=new ProductivityChart();
        //prodc.setBounds ( 600 , 350 , 300 , 200 );    
        //prodc.setBackground(Color.WHITE);
        //add(prodc);
        //}catch(Exception e){e.getMessage();JOptionPane.showMessageDialog(null, "Data not found..!");}
      
//        scroll = new JScrollPane ( pc ,
//                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
//                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
//
//        scroll.setBounds ( 270 , 290 , 1000 , 280 );
//        scroll.setOpaque ( false );
//        scroll.setBackground ( Color.BLACK );
//        
//        scroll.revalidate ();
//        scroll.repaint ();

        reportPanel = new JPanel();
        //reportPanel.setLayout(null);
        reportPanel.setLayout(new GridLayout(6 ,2 ,0 ,0));
        //reportPanel.setLayout(new BoxLayout(reportPanel, BoxLayout.PAGE_AXIS));
         sp = new JScrollPane(reportPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setPreferredSize(new Dimension(500,500));
        sp.setBounds(530, 0, 800, 580);
        sp.setBorder(null);
        sp.setForeground(new java.awt.Color(204, 204, 255));
        //sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sp.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        sp.setEnabled(false);
        sp.setFocusable(false);
        sp.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        //reportPanel.add(Box.createRigidArea(new Dimension(0,5)));
        add(sp);
        reportPanel.setBackground(Color.WHITE);
        setVisible(true);
        sp.revalidate();
        sp.repaint();
        
        jScrollPane10.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        
         final CounterTask task = new CounterTask();
         task.execute();
        //jPanel1 = new JPanel();
        //tablpanel.setLayout(new GridLayout(5 ,1 ,0 ,0));
        //reportPanel.setLayout(new BoxLayout(reportPanel, BoxLayout.PAGE_AXIS));
//        JScrollPane sp1 = new JScrollPane(jPanel1,
//                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        sp1.setPreferredSize(new Dimension(500,400));
//        //sp1.setBounds(0, 0, 580, 550);
//        //reportPanel.add(Box.createRigidArea(new Dimension(0,5)));
//        add(sp1);
//        //tablePanel.setBackground(Color.WHITE);
//        setVisible(true);
//        sp1.revalidate();
//        sp1.repaint();
//        tablePanel = new JPanel();
//        tablePanel.setLayout(new GridLayout(10 ,1 ,5 ,5));
//        //reportPanel.setLayout(new BoxLayout(reportPanel, BoxLayout.PAGE_AXIS));
//        JScrollPane sp1 = new JScrollPane(tablePanel,
//                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        sp1.setPreferredSize(new Dimension(500,400));
//        sp1.setBounds(0, 450, 820, 600);
//        //reportPanel.add(Box.createRigidArea(new Dimension(0,5)));
//        add(sp1);
//        tablePanel.setBackground(Color.WHITE);
//        setVisible(true);
//        sp1.revalidate();
//        sp1.repaint();
//         jTable1.setVisible(false);
//                jTable2.setVisible(false);
              //  jTable3.setVisible(false);
        
        

//        Thread t = new Thread() {
//            public void run() {
//                pmph = new PMPHChart();
//                pmph.setBounds(0, 0, 350, 250);
//                pmph.setBackground(Color.WHITE);
//                reportPanel.add(pmph);
//                System.out.println("Panel pmph");
//                //sp.repaint();
//                reportPanel.revalidate();
//                reportPanel.repaint();
//                revalidate();
//                repaint();
//                
////                cvvc = new CostVsValueChart();
////                cvvc.setBounds(400, 0, 350, 250);
////                cvvc.setBackground(Color.WHITE);
////                reportPanel.add(cvvc);
////                System.out.println("Panel cost vs value");
////                //sp.repaint();
////                reportPanel.revalidate();
////                reportPanel.repaint();
////                revalidate();
////                repaint();
////                
////                oee = new OEEChart();
////                oee.setBounds(0, 250, 350, 250);
////                oee.setBackground(Color.WHITE);
////                reportPanel.add(oee);
////                System.out.println("Panel OEEE");
////                //sp.repaint();
////                reportPanel.revalidate();
////                reportPanel.repaint();
////                revalidate();
////                repaint();
////                
////                pc = new ProductionChart();
////                pc.setBounds(400, 250, 350, 250);
////                pc.setBackground(Color.WHITE);
////                reportPanel.add(pc);
////                System.out.println("Panel production");
////                //sp.repaint();
////                reportPanel.revalidate();
////                reportPanel.repaint();
////                revalidate();
////                repaint();
////                
////                copq = new COPQChart();
////                copq.setBounds(0, 500, 350, 250);
////                copq.setBackground(Color.WHITE);
////                reportPanel.add(copq);
////                System.out.println("Panel COPQ");
////                //sp.repaint();
////                reportPanel.revalidate();
////                reportPanel.repaint();
////                revalidate();
////                repaint();
////                
////                 wipc = new WorkInPrograssChart();
////                wipc.setBounds(400, 500, 350, 250);
////                wipc.setBackground(Color.WHITE);
////                reportPanel.add(wipc);
////                System.out.println("Panel WIP");
////                //sp.repaint();
////                reportPanel.revalidate();
////                reportPanel.repaint();
////                revalidate();
////                repaint();
////                
////                pvc = new ProductionValueChart();
////                pvc.setBounds(0, 750, 350, 250);
////                pvc.setBackground(Color.WHITE);
////                reportPanel.add(pvc);
////                System.out.println("Panel PRod value");
////                //sp.repaint();
////                reportPanel.revalidate();
////                reportPanel.repaint();
////                revalidate();
////                repaint();
////                
////                rc = new RejectionChart();
////                rc.setBounds(400, 750, 350, 250);
////                rc.setBackground(Color.WHITE);
////                reportPanel.add(rc);
////                System.out.println("Panel Rejection");
////                //sp.repaint();
////                revalidate();
////                reportPanel.revalidate();
////                reportPanel.repaint();
////                repaint();
////                
////                doc = new DefectOccurencesChart();
////                doc.setBounds(0, 1000, 350, 250);
////                doc.setBackground(Color.WHITE);
////                reportPanel.add(doc);
////                System.out.println("Panel Defect");
////                reportPanel.revalidate();
////                reportPanel.repaint();              
////                sp.revalidate();
////                sp.repaint();
////                revalidate();
////                repaint();
//                
//                
//            }
//        };
//        
//        //////*****************End Report on dashboard edited by mayur*********************  
//        
// 
//                
//
//        Thread t3 = new Thread() {
//            public void run() {
//                cvvc = new CostVsValueChart();
//                cvvc.setBounds(400, 0, 350, 250);
//                cvvc.setBackground(Color.WHITE);
//                reportPanel.add(cvvc);
//                System.out.println("Panel cost vs value");
//                sp.repaint();
//                reportPanel.revalidate();
//                reportPanel.repaint();
//                revalidate();
//            }
//        };
//
//        Thread t4 = new Thread() {
//            public void run() {
//                oee = new OEEChart();
//                oee.setBounds(0, 250, 350, 250);
//                oee.setBackground(Color.WHITE);
//                reportPanel.add(oee);
//                System.out.println("Panel OEEE");
//                sp.repaint();
//                reportPanel.revalidate();
//                reportPanel.repaint();
//                revalidate();
//            }
//        };
//        Thread t5 = new Thread() {
//            public void run() {
//                pc = new ProductionChart();
//                pc.setBounds(400, 250, 350, 250);
//                pc.setBackground(Color.WHITE);
//                reportPanel.add(pc);
//                System.out.println("Panel production");
//                sp.repaint();
//                reportPanel.revalidate();
//                reportPanel.repaint();
//                revalidate();
//            }
//        };
//        Thread t6 = new Thread() {
//            public void run() {
//                copq = new COPQChart();
//                copq.setBounds(0, 500, 350, 250);
//                copq.setBackground(Color.WHITE);
//                reportPanel.add(copq);
//                System.out.println("Panel COPQ");
//                sp.repaint();
//                reportPanel.revalidate();
//                reportPanel.repaint();
//                revalidate();
//            }
//        };
//        Thread t7 = new Thread() {
//            public void run() {
//                wipc = new WorkInPrograssChart();
//                wipc.setBounds(400, 500, 350, 250);
//                wipc.setBackground(Color.WHITE);
//                reportPanel.add(wipc);
//                System.out.println("Panel WIP");
//                sp.repaint();
//                reportPanel.revalidate();
//                reportPanel.repaint();
//                revalidate();
//            }
//        };
//        Thread t8 = new Thread() {
//            public void run() {
//                pvc = new ProductionValueChart();
//                pvc.setBounds(0, 750, 350, 250);
//                pvc.setBackground(Color.WHITE);
//                reportPanel.add(pvc);
//                System.out.println("Panel PRod value");
//                sp.repaint();
//                reportPanel.revalidate();
//                reportPanel.repaint();
//                revalidate();
//
//            }
//        };
//        Thread t9 = new Thread() {
//            public void run() {
//                rc = new RejectionChart();
//                rc.setBounds(400, 750, 350, 250);
//                rc.setBackground(Color.WHITE);
//                reportPanel.add(rc);
//                System.out.println("Panel Rejection");
//                sp.repaint();
//                revalidate();
//                reportPanel.revalidate();
//                reportPanel.repaint();
//
//            }
//        };
//        Thread t10 = new Thread() {
//            public void run() {
//                doc = new DefectOccurencesChart();
//                doc.setBounds(0, 1000, 350, 250);
//                doc.setBackground(Color.WHITE);
//                reportPanel.add(doc);
//                System.out.println("Panel Defect");
//                sp.repaint();
//                reportPanel.revalidate();
//                reportPanel.repaint();
//                revalidate();
//
//            }
//        };
//        Thread t11 = new Thread() {
//            public void run() {
//                try{
//                      getLatestRMStock();
//                
//                }catch(Exception e){e.getMessage();}
//                try{
//                       loadContentFGABC();
//                           
//                }catch(Exception e){e.getMessage();}
//                try{
//                        saleOrderData();
//                }catch(Exception e){e.getMessage();}
//                try{
//                        getRMStockLedger();    
//                }catch(Exception e){e.getMessage();}
//           
//
//            }
//        };
//       
//        t.setPriority(Thread.MAX_PRIORITY);
//         t.start();
//         
//         t3.setPriority(Thread.MAX_PRIORITY);
//          t3.start();
//          
//          t4.setPriority(Thread.MAX_PRIORITY);
//           t4.start();
//           
//           t5.setPriority(Thread.MIN_PRIORITY);
//            t5.start();
//            
//           t6.setPriority(Thread.MIN_PRIORITY);
//             t6.start();
//             
//            t7.setPriority(Thread.MIN_PRIORITY);
//              t7.start();
//            
//             t8.setPriority(Thread.NORM_PRIORITY);
//               t8.start();
//               
//               t9.setPriority(Thread.NORM_PRIORITY);
//                t9.start();
//                
//                t10.setPriority(Thread.NORM_PRIORITY);
//                 t10.start();
//                 
//                 t11.setPriority(Thread.NORM_PRIORITY);
//                  t11.start();
//                   
//                    revalidate();
//                     repaint();

    }

  

    public void buildTableModelRMABC ( ArrayList<String[]> rs  )
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
    public void getLatestRMStock(){
        
        String result2 = WebAPITester.prepareWebCall ( "rawmaterials" , StaticValues.getHeader () , "" );


       if ( result2 != null && ! result2.contains(  "not found") ) {
           jTable2.setModel ( buildTableModelfromJSON_RMAndStock (result2 ) );
           jTable2.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
           jTable2.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
       }
    }
    
    public  DefaultTableModel buildTableModelfromJSON_RMAndStock ( String employeeJSONList ) {

        Vector<String> columnNames = new Vector<String> ();
        Vector<String> columnNames2 = new Vector<String> ();
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
        columnNames.add ( "RM_ID" );                columnNames2.add ( "RM_ID" );
        columnNames.add ( "RM_TYPE" );              columnNames2.add ( "RM Type" );
        columnNames.add ( "RM_CLASS" );             columnNames2.add ( "RM Code" );
        //columnNames.add ( "RM_CTG" );
        columnNames.add ( "REORDER_LEVEL" );    columnNames2.add ( "Reorder Level" );
        columnNames.add ( "Available Stock" ); columnNames2.add ( "Available Stock" );     
        //columnNames.add ( "Batch" );      columnNames2.add ( "Batch" );
        
        //columnNames.add("RM_CLASS");
              
        
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );

                tableValues = new Vector<Object> ();

                String[] values = new String[ columnNames.size () ];
                for ( int columnIndex = 0 ; columnIndex <columnNames.size ()-2 ; columnIndex ++ ) {

                    //values[columnIndex] = emp.get ( columnNames.get ( columnIndex ) ).toString () ;
                    tableValues.add(   emp.get ( columnNames.get ( columnIndex ) ).toString ()       ) ;

                }

                String addEmpAPICall = "latestrwamstock?rmid="+emp.get ( "RM_ID" ).toString ()  ;
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
                        
                        if( ! emp2.get ( "CLOSING" ).toString ().equals ( "null") )
                            tableValues.add(  emp2.get ( "CLOSING" ).toString () );
                        else
                            tableValues.add(  "0"  );
                                               
                        //tableValues.add(  emp2.get ( "Batch" ).toString () );
                 }

                    data.add ( tableValues );
             }
            
             return new DefaultTableModel ( data , columnNames2 );
        }
    
    public void loadContent() {
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
                StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                    StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

                double orderValue = 0.0;
                for ( int j = 0 ; j < customers.size () ; j ++ ) {

                    result = DB_Operations.executeSingle ( "select so_customer_id, product_id,  SUM(product_qty), ( select SALES_RATE from FG_CUSTOMER_MAP where M_FG_ID = " + products.get ( i )[ 0 ] + " AND M_CUST_ID = " + customers.get ( j ) + " )*SUM(product_qty) as'Value'  from sales_orders A inner join po_order_details B where A.sales_po_id = B.ref_po_id AND A.so_customer_id = " + customers.get ( j ) + " AND B.product_id = " + products.get ( i )[ 0 ] + "" );

                    if ( result.isBeforeFirst () ) {
                        try {
                            orderValue = orderValue + Double.parseDouble ( result.getString ( 4 ) );
                        } catch ( NullPointerException e ) {
                             orderValue = 0 ;
                            
                            StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                        }
                    }
                }

                try {
                    result.close ();
                } catch ( SQLException e ) {
                    System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                    StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                    StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

                double producedValue = 0.0;
                for ( int j = 0 ; j < products.size () ; j ++ ) {
                    producedValue = 0.0;
                    result = DB_Operations.executeSingle ( " select  ( select SALES_RATE from finished_goods where FG_ID = " + products.get ( i )[ 0 ] + " )*  ( select CLOSING from FGStock where FG_ID = " + products.get ( i )[ 0 ] + " AND FGStock_TR_ID = ( select MAX(FGStock_TR_ID) from FGStock ) ) " );

                    if ( result.isBeforeFirst () ) {
                        try {
                            producedValue = producedValue + Double.parseDouble ( result.getString ( 1 ) );
                        } catch ( NullPointerException e ) {
                            StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                        }
                    }
                }

                values.add ( df.format ( Double.parseDouble ( "" + producedValue ) ) );

                try {
                    result.close ();
                } catch ( SQLException e ) {
                    System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                    StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
        } catch ( SQLException e ) {
            //System.out.println( "Error No result to show " +e.getMessage());
            try {
                result.close ();
            } catch ( Exception e2 ) {
                System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e2.toString () );
                StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
            StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                    StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

                double orderValue = 0.0;
                for ( int j = 0 ; j < customers.size () ; j ++ ) {

                    result = DB_Operations.executeSingle ( "select so_customer_id, product_id,  SUM(product_qty), ( select SALES_RATE from FG_CUSTOMER_MAP where M_FG_ID = " + products.get ( i )[ 0 ] + " AND M_CUST_ID = " + customers.get ( j ) + " )*SUM(product_qty) as'Value'  from sales_orders A inner join po_order_details B where A.sales_po_id = B.ref_po_id AND A.so_customer_id = " + customers.get ( j ) + " AND B.product_id = " + products.get ( i )[ 0 ] + "" );

                    if ( result.isBeforeFirst () ) {
                        try {
                            orderValue = orderValue + Double.parseDouble ( result.getString ( 4 ) );
                        } catch ( NullPointerException e ) {
                             orderValue = 0 ;
                            
                            StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                        }
                    }
                }

                try {
                    result.close ();
                } catch ( SQLException e ) {
                    System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                    StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                    StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

                double producedValue = 0.0;
                for ( int j = 0 ; j < products.size () ; j ++ ) {
                    producedValue = 0.0;
                    result = DB_Operations.executeSingle ( " select  ( select SALES_RATE from finished_goods where FG_ID = " + products.get ( i )[ 0 ] + " )*  ( select CLOSING from FGStock where FG_ID = " + products.get ( i )[ 0 ] + " AND FGStock_TR_ID = ( select MAX(FGStock_TR_ID) from FGStock ) ) " );

                    if ( result.isBeforeFirst () ) {
                        try {
                            producedValue = producedValue + Double.parseDouble ( result.getString ( 1 ) );
                        } catch ( NullPointerException e ) {
                            StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                        }
                    }
                }

                values.add ( df.format ( Double.parseDouble ( "" + producedValue ) ) );

                try {
                    result.close ();
                } catch ( SQLException e ) {
                    System.out.println ( "Cannot close resultset in Inventory Widget loadContent " + e.toString () );
                    StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
            StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

    }
    
    public void buildTableModelRMABC_OLD ( ResultSet rs , String table )
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
                StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

            //  return new DefaultTableModel ( data , columnNames );
        } catch ( Exception e ) {
            System.out.println ( "" );
            StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            //   return null ;
        }

    }

    public void buildTableModelFGABC_OLD ( ResultSet rs , String table )
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
                StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

            //  return new DefaultTableModel ( data , columnNames );
        } catch ( Exception e ) {
            System.out.println ( "" );
            StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
            StaticValues.writer.writeExcel ( InventoryWidgetDyn.class.getSimpleName () , InventoryWidgetDyn.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
    
    public void getRMStockLedger(){
        
        String result2 = WebAPITester.prepareWebCall ( "rmstocks" , StaticValues.getHeader () , "" );


       if ( result2 != null && ! result2.contains(  "not found") ) {
           jTable4.setModel ( buildTableModelfromJSON_RMStockLedger(result2 ) );
       }
    }
      public  DefaultTableModel buildTableModelfromJSON_RMStockLedger ( String employeeJSONList ) {

        Vector<String> columnNames = new Vector<String> ();
        Vector<String> columnNames2 = new Vector<String> ();
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
        columnNames.add ( "RM_CODE" );                columnNames2.add ( "RM Code" );
        columnNames.add ( "OPENING" );              columnNames2.add ( "Opening" );
        columnNames.add ( "RECEIVED" );             columnNames2.add ( "Inward" );
        columnNames.add ( "USED" );    columnNames2.add ( "Issued" );
        columnNames.add ( "CLOSING" );      columnNames2.add ( "Available" );
        //columnNames.add ( "rm_inward_date" );      columnNames2.add ( "In Date" );
        //columnNames.add ( "inward_batch_no" );      columnNames2.add ( "Batch" );
        //columnNames.add ( "inward_uid" );      columnNames2.add ( "In Code" );
       // columnNames.add ( "purchase_rate" );      columnNames2.add ( "Rate" );
       // columnNames.add ( "created_at" );      columnNames2.add ( "Entry Date" );
        
        //columnNames.add("RM_CLASS");
              
        
        
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );

                tableValues = new Vector<Object> ();
                for ( int columnIndex = 0 ; columnIndex <columnNames.size () ; columnIndex ++ ) {
                     
                    if(  emp.get ( columnNames.get ( columnIndex ) ).toString ().equals ( "null") )
                        tableValues.add(   "--"      ) ;
                     else
                        tableValues.add(   emp.get ( columnNames.get ( columnIndex ) ).toString ()       ) ; 
                }
                    data.add ( tableValues );
             }
            
             return new DefaultTableModel ( data , columnNames2 );
        }
      
      public void saleOrderData()
      {
           String getcustomerList = "salesorders";
        String salesOrderData = WebAPITester.prepareWebCall ( getcustomerList , StaticValues.getHeader () , "" );

        if( !  salesOrderData.contains( "not found"  )  ){
            jTable6.setModel ( saleOrderbuildTableModelfromJSON ( salesOrderData ) );
            jTable6.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
            jTable6.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
        }
      }
      
    public DefaultTableModel saleOrderbuildTableModelfromJSON ( String employeeJSONList ) {

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
        columnNames2.add ( "Order Qty" );
        columnNames2.add ( "Order No" );
        columnNames2.add ( "Order Date" );

        
        
        
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
            boolean fgmatched = false;

            Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {
                emp = records.getJSONObject ( i );
                vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
             }
            data.add ( vector );
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

        jScrollPane10 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1310, 600));
        setLayout(null);

        jScrollPane10.setBorder(null);
        jScrollPane10.setForeground(new java.awt.Color(204, 204, 255));
        jScrollPane10.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane10.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jScrollPane10.setEnabled(false);
        jScrollPane10.setFocusable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(51, 51, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Raw Material ABC Analysis Report", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Leelawadee UI", 0, 13))); // NOI18N
        jScrollPane2.setOpaque(false);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(400, 200));

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

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 440, 150));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Finished Good ABC Analysis Report", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Leelawadee UI", 0, 13))); // NOI18N
        jScrollPane1.setOpaque(false);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 150));

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
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 440, -1));

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Product wise order quantities, ustomer wise order values, shortfall in order quantity and current stocks", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Leelawadee UI", 0, 13))); // NOI18N
        jScrollPane3.setOpaque(false);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(400, 200));

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

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 440, 150));

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder("Raw Material Stock Ledger"));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(400, 200));

        jTable4.setAutoCreateRowSorter(true);
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable4.setShowHorizontalLines(false);
        jTable4.setShowVerticalLines(false);
        jScrollPane4.setViewportView(jTable4);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 440, 130));

        jScrollPane6.setBorder(javax.swing.BorderFactory.createTitledBorder("dsadasda"));
        jScrollPane6.setPreferredSize(new java.awt.Dimension(400, 200));

        jTable5.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        jTable5.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(jTable5);

        jScrollPane6.setViewportView(jScrollPane7);

        jPanel1.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 120, 450, 120));

        jScrollPane8.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane8.setBorder(javax.swing.BorderFactory.createTitledBorder("Sales Order Analysis Data"));

        jTable6.setAutoCreateRowSorter(true);
        jTable6.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable6.setShowHorizontalLines(false);
        jTable6.setShowVerticalLines(false);
        jScrollPane8.setViewportView(jTable6);

        jPanel1.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, 440, 140));

        jScrollPane10.setViewportView(jPanel1);

        add(jScrollPane10);
        jScrollPane10.setBounds(20, 0, 480, 580);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    public static javax.swing.JTable jTable1;
    protected static javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    // End of variables declaration//GEN-END:variables

    class CounterTask extends SwingWorker {
        int DELAY = 1000;
        @Override
        protected Integer doInBackground() throws Exception {
                pmph = new PMPHChart();
                pmph.setBounds(0, 0, 350, 250);
                pmph.setBackground(Color.WHITE);
                reportPanel.add(pmph);
                System.out.println("Panel pmph");
                //sp.repaint();
                reportPanel.revalidate();
                reportPanel.repaint();
                revalidate();
                repaint();
                
                cvvc = new CostVsValueChart();
                cvvc.setBounds(400, 0, 350, 250);
                cvvc.setBackground(Color.WHITE);
                reportPanel.add(cvvc);
                System.out.println("Panel cost vs value");
                //sp.repaint();
                reportPanel.revalidate();
                reportPanel.repaint();
                revalidate();
                repaint();
                
                oee = new OEEChart();
                oee.setBounds(0, 250, 350, 250);
                oee.setBackground(Color.WHITE);
                reportPanel.add(oee);
                System.out.println("Panel OEEE");
                //sp.repaint();
                reportPanel.revalidate();
                reportPanel.repaint();
                revalidate();
                repaint();
                
                pc = new ProductionChart();
                pc.setBounds(400, 250, 350, 250);
                pc.setBackground(Color.WHITE);
                reportPanel.add(pc);
                System.out.println("Panel production");
                //sp.repaint();
                reportPanel.revalidate();
                reportPanel.repaint();
                revalidate();
                repaint();
                
                copq = new COPQChart();
                copq.setBounds(0, 500, 350, 250);
                copq.setBackground(Color.WHITE);
                reportPanel.add(copq);
                System.out.println("Panel COPQ");
                //sp.repaint();
                reportPanel.revalidate();
                reportPanel.repaint();
                revalidate();
                repaint();
                
                 wipc = new WorkInPrograssChart();
                wipc.setBounds(400, 500, 350, 250);
                wipc.setBackground(Color.WHITE);
                reportPanel.add(wipc);
                System.out.println("Panel WIP");
                //sp.repaint();
                reportPanel.revalidate();
                reportPanel.repaint();
                revalidate();
                repaint();
                
                pvc = new ProductionValueChart();
                pvc.setBounds(0, 750, 350, 250);
                pvc.setBackground(Color.WHITE);
                reportPanel.add(pvc);
                System.out.println("Panel PRod value");
                //sp.repaint();
                reportPanel.revalidate();
                reportPanel.repaint();
                revalidate();
                repaint();
                
                rc = new RejectionChart();
                rc.setBounds(400, 750, 350, 250);
                rc.setBackground(Color.WHITE);
                reportPanel.add(rc);
                System.out.println("Panel Rejection");
                //sp.repaint();
                revalidate();
                reportPanel.revalidate();
                reportPanel.repaint();
                repaint();
                
                doc = new DefectOccurencesChart();
                doc.setBounds(0, 1000, 350, 250);
                doc.setBackground(Color.WHITE);
                reportPanel.add(doc);
                System.out.println("Panel Defect");
                reportPanel.revalidate();
                reportPanel.repaint();              
                sp.revalidate();
                sp.repaint();
                revalidate();
                repaint();
                
                try{
                      getLatestRMStock();
                     System.out.println("table1"); 
                
                }catch(Exception e){e.getMessage();}
                try{
                       loadContentFGABC();
                        System.out.println("table2"); 
                           
                }catch(Exception e){e.getMessage();}
                try{
                        saleOrderData();
                         System.out.println("table3"); 
                }catch(Exception e){e.getMessage();}
                try{
                        getRMStockLedger();   
                         System.out.println("table4"); 
                }catch(Exception e){e.getMessage();}
                
            Thread.sleep(DELAY);
    
        return null;
  }   
//      protected void process(ListModel<Integer> chunks) {
//          System.out.println(chunks);
//      }
//      @Override
//      protected void done() {
//      if (isCancelled())
//          System.out.println("Cancelled !");
//      else
//          System.out.println("Done !");
//      }
    }  
}

