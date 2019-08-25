/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import trixware.erp.prodezydesktop.components.ColumnRenderer;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class DeliveryScheduleMonthlyReport extends javax.swing.JPanel {

    ArrayList<String[]> parts = new ArrayList<String[]>();
    
    /**
     * Creates new form DeliveryScheduleReport
     */
    public DeliveryScheduleMonthlyReport () {
        initComponents ();
        
        {
            String getcustomerList = "finishedgoods";
            String salesOrderData = WebAPITester.prepareWebCall ( getcustomerList , StaticValues.getHeader () , "" );
            HashMap<String , Object> map = new HashMap<String , Object> ();
            JSONObject jObject = new JSONObject ( salesOrderData );
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
                parts.add( new String[]{  emp.get( "FG_ID").toString()  , emp.get( "PART_NAME").toString() }  );
            }
        }
    
    }

    public DefaultTableModel buildTableModelfromJSON ( String employeeJSONList ) {
        
        if( ! employeeJSONList.contains ( "not found")  ){
            Vector<String> columnNames = new Vector<String> ();
            Vector<String> columnNames2 = new Vector<String> ();
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();

            Vector<Object> vector = new Vector<Object> ();

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
            columnNames.add ( "PART_NAME" );
            columnNames.add ( "dlry_date" );
            columnNames.add ( "delry_qty" );


            columnNames2 = new Vector<String> ();
            columnNames2.add ( "Part Name" );
            columnNames2.add ( "Target Date" );
            columnNames2.add ( "Quantity" );

            for ( int i = 0 ; i < records.length () ; i ++ ) {

                 vector = new Vector<Object> ();

                 for ( int columnIndex = 0 ; columnIndex < columnNames.size() ; columnIndex++ ) {
                        emp = records.getJSONObject ( i );
                        if(  columnNames.get(columnIndex).equals ( "dlry_date")  ){
                            try{
                                vector.add(  "<html><center>"+  new SimpleDateFormat( "MMM dd,yyyy").format( (Date)new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss").parse( emp.get ( columnNames.get(columnIndex) ).toString())) +"</center></html>");
                            }catch( ParseException e ){

                            }
                        }else{
                            vector.add(  "<html><center>"+ emp.get ( columnNames.get(columnIndex) ).toString() +"</center></html>");
                        }
                        
                 }
                data.add(vector) ;
            }

            return new DefaultTableModel ( data , columnNames2 );
        }
        
        return null ;
    }
    
    public DefaultTableModel buildTableModelfromJSON_old ( String employeeJSONList ) {

        Vector<String> columnNames = new Vector<String> ();
        Vector<String> columnNames2 = new Vector<String> ();
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        
        Vector<Object> dates = new Vector<Object> ();
        dates.add( "  " ) ;
        for( int i=1; i<=31; i++       ){
            if( i%9>0  ){
                dates.add( "0"+1  )            ;
            }else
                dates.add( i+""   )           ; 
        }        
        
        data.add (  dates );
        
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
        columnNames.add ( "PART_NAME" );
        columnNames.add ( "delry_qty" );
        columnNames.add ( "dlry_date" );

        
        
        columnNames2 = new Vector<String> ();
        columnNames2.add ( "Part Name" );
        
        for( int i=1; i<=31; i++       ){
            if( i%9>0  ){
                columnNames2.add( "0"+1  )            ;
            }else
                columnNames2.add( i+""   )           ; 
        } 
        

        Vector<Object> vector = null ;
        
       
         String part_id = "";
        String partname = "";  
        for ( int i = 0 ; i < records.length () ; i ++ ) {
          
                partname = emp.get ( "PART_NAME" ).toString() ;
                part_id  =  emp.get ( "product_id" ).toString() ;
            
                for( int x=0; x<parts.size(); x++ ){
                
                    if(  partname.equals( parts.get (x )[1]) && part_id.equals( parts.get (x )[0] )  ){
                        
                        
                    }
                    
                    vector = new Vector<Object> ();
                    emp = records.getJSONObject ( i );
                    
                    vector.add( partname );
                    
                    for( int j=0; j<dates.size(); j++       ){
                    try{
                        String dateVal = new SimpleDateFormat("dd").format ( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse( emp.get ( "dlry_date" ).toString()  ) );

                            if( dateVal.equals( dates.get( j ) ) ){
                                vector.add( emp.get ( "delry_qty" ).toString() );
                                j = dates.size( );
                            }else if( ! dateVal.equals( dates.get( j ) ) ){
                                vector.add( "");    
                            }    
                        }catch(  ParseException e ){

                        }
                    }    
                  
                }
            data.add (  vector );   
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

        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1100, 550));
        setLayout(null);
        add(dateChooserCombo1);
        dateChooserCombo1.setBounds(350, 5, 0, 30);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Get Report");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(530, 5, 140, 32);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "April", "May", "June", "July", "August", "September", "October", "November", "December", "January", "February", "March" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        add(jComboBox1);
        jComboBox1.setBounds(370, 6, 140, 30);

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
        jScrollPane1.setBounds(10, 40, 1080, 403);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    ArrayList<String> dates = new ArrayList<String>() ;
    
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        
        Calendar cal = Calendar.getInstance () ;
        String months[] = new String[]{ "January","February","March", "April","May","June","July","August","September","October","November","December" } ;

        String selMonth = jComboBox1.getSelectedItem ().toString() ;
        for( int counter = 0; counter<months.length ; counter++ ){
            if( months[ counter].equals( selMonth  )   ){
                cal.set ( Calendar.MONTH, counter);
                if( counter<=2 ){
                    cal.set ( Calendar.YEAR, cal.get(Calendar.YEAR)+1);
                }
            } 
        }
        
        int minDate = cal.getActualMinimum ( Calendar.DATE  ) ;
        int maxDate = cal.getActualMaximum (Calendar.DATE  ) ;
        
        System.out.println ( minDate+"  "+maxDate );
        //String yearMonth = new SimpleDateFormat( "yyyy-MM-"  ).format( cal.getTime () ) ;
        
        cal.set ( Calendar.DATE, minDate);
        
        
        String fromdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()) ;
        cal.set ( Calendar.DATE, maxDate);
        String todate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()) ;
        
        String getcustomerList = "" ;
        //String getcustomerList = "deliveryscheduledetails?from_date=2019-05-01+00:00:00&to_date=2019-05-31+00:00:00";
        try{
            getcustomerList = "deliveryscheduledetails?from_date="+URLEncoder.encode( fromdate  , "UTF-8")+"&to_date="+URLEncoder.encode( todate  , "UTF-8");
            System.out.println ( ""+getcustomerList );
        }catch( UnsupportedEncodingException  e ){
            
        }
             
        String salesOrderData = WebAPITester.prepareWebCall ( getcustomerList , StaticValues.getHeader () , "" );
        if( !  salesOrderData.contains( "not found"  )  ){
            jTable1.setModel ( buildTableModelfromJSON ( salesOrderData ) );
            ColumnRenderer clr = new ColumnRenderer() ;
            jTable1.setDefaultRenderer(String.class, clr.getColumnRenderer());
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
