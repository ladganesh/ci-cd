/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.DailyDataEntryModelHour;
import trixware.erp.prodezydesktop.Model.EmployeeDR;
import trixware.erp.prodezydesktop.Model.MachineDR;
import trixware.erp.prodezydesktop.Model.POrderDR;
import trixware.erp.prodezydesktop.Model.ProcessDR;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.RMBatchDR;
import trixware.erp.prodezydesktop.Model.Rejection_Reasons;
import trixware.erp.prodezydesktop.Model.ShiftDRHour;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author dell
 */
public class DailyReportFormHourWise extends javax.swing.JPanel {

   
    ArrayList<RMBatchDR> batches = null;
    ArrayList<POrderDR> orders = null;
    ArrayList<ShiftDRHour> shifthour =null;
    
    ArrayList<ProductDR> products = null;
    ArrayList<EmployeeDR> employees = null;
    ArrayList<ProcessDR> processes = null;
    ArrayList<MachineDR> machines = null;
    ArrayList<Rejection_Reasons> rej_reasons = null;
    
     ArrayList<DateValue> Store=new ArrayList<>();
    
     boolean processRowsPresent = false;
     
     String from_time,to_time,d1,d;
     int incompleteDataDialog = 0;
     int totalTimeLossInMinutes = 0;
     double totalTimeLossInHours = 0;
    
    SingleHourDE_FormHourWise[] forms = null;
    
    JPanel panel = null;
    JScrollPane scroll = null;
    
    DailyDataEntryModelHour data;    
    int selectedShiftID;
    int selectedFG_ID;
    public static int i=0;
    /**
     * Creates new form DailyReportFormHourWise
     */
    public DailyReportFormHourWise() {
        initComponents();
        
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
        
        loadcombobox();
        dateChooserCombo3.setEnabled(false);
        callMethod();
    }
    
    public void loadcombobox()
    {
         JSONObject JSONObject;
        String addEmpAPICall,result2;
        //HashMap<String , Object> map = new HashMap<String , Object> ();

//        if ( jComboBox1.getItemCount () == 0 ) {
//            //result = DB_Operations.getMasters ( "raw_material_type" );
//
//            addEmpAPICall = "batches";
//            result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
//            JSONObject jObject = new JSONObject ( result2 );
//            map = new HashMap<String, Object>();
//            JSONObject = new JSONObject( result2 );
//            Iterator<?> keys = jObject.keys ();
//            
//            JSONObject st = ( JSONObject ) map.get ( "data" );
//            JSONArray records;
//            JSONObject emp = null;
//            
//            keys = jObject.keys();
//            while( keys.hasNext() ){
//                String key = (String)keys.next();
//                Object value = jObject.get ( key ) ; 
//                map.put(key, value);
//            }                    
//            st = (JSONObject) map.get ( "data" );
//            records = st.getJSONArray ( "records");
//            emp = null;
//            batches = new ArrayList<RMBatchDR> ();
//            for ( int i = 0 ; i < records.length () ; i ++ ) {                    
//                emp = records.getJSONObject ( i);
//                jComboBox1.addItem ( emp.get ( "BatchName" ).toString () );
//                batches.add ( new RMBatchDR ( Integer.parseInt ( emp.get ( "BatchId" ).toString () ) , emp.get ( "BatchName" ).toString () , Integer.parseInt ( emp.get ( "ref_rm_id" ).toString () ) ,  emp.get ( "BatchQty" ).toString () ) );
//            }
//        }

        jComboBox2.removeActionListener ( productSelection );
        if ( jComboBox2.getItemCount () == 0 ) {

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
                jComboBox2.addItem ( emp.get ( "FG_PART_NO" ).toString () );
                products.add ( new ProductDR ( Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) , emp.get ( "PART_NAME" ).toString () , emp.get ( "FG_UOM_ID" ).toString () ) );

            }
        }
        //jComboBox2.addActionListener ( productSelection );
        HashMap<String , Object> map = new HashMap<String , Object> ();

        //   ORDER SELECTION COMBO
        if ( jComboBox4.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            addEmpAPICall = "salesorders";
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            //map = new HashMap<String , Object> ();
            JSONObject jObject = new JSONObject ( result2 );
            Iterator<?> keys = jObject.keys ();
            keys = jObject.keys ();

            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }
            JSONObject st = ( JSONObject ) map.get ( "data" );
            JSONArray records;
            JSONObject emp = null;

            st = ( JSONObject ) map.get ( "data" );
            records = st.getJSONArray ( "records" );

            emp = null;
            orders = new ArrayList<POrderDR> ();
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );
                jComboBox4.addItem ( emp.get ( "order_no" ).toString () );
                orders.add ( new POrderDR ( Integer.parseInt ( emp.get ( "sales_po_id" ).toString () ) , emp.get ( "order_no" ).toString () , Integer.parseInt ( emp.get ( "so_customer_id" ).toString () ) ) );
            }
        }
        
//         if ( jComboBox2.getItemCount () == 0 ) {
//            //result = DB_Operations.getMasters ( "raw_material_type" );
//
            addEmpAPICall = "shifts";
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            map = new HashMap<String , Object> ();
            JSONObject jObject = new JSONObject ( result2 );
            Iterator<?> keys = jObject.keys ();
            keys = jObject.keys ();

            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }
            JSONObject st = ( JSONObject ) map.get ( "data" );
            JSONArray records;
            JSONObject emp = null;

            st = ( JSONObject ) map.get ( "data" );
            records = st.getJSONArray ( "records" );

            emp = null;
            shifthour = new ArrayList<ShiftDRHour> ();
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );
                //jComboBox2.addItem ( emp.get ( "shifttitle" ).toString ());
                shifthour.add ( new ShiftDRHour ( Integer.parseInt ( emp.get ( "shiftid" ).toString () ) , emp.get ( "shifttitle" ).toString () ,( emp.get ( "shiftfromtime" ).toString () ), ( emp.get ( "shifttotime" ).toString () ) ) );
            }
//        }
         
        
        String result3 = WebAPITester.prepareWebCall ( "processes" , StaticValues.getHeader () , "" );

        jObject = new JSONObject ( result3 );
        //Iterator<?> keys = jObject.keys ();

        while ( keys.hasNext () ) {
            String key = ( String ) keys.next ();
            Object value = jObject.get ( key );
            map.put ( key , value );
        }

        st = ( JSONObject ) map.get ( "data" );
        //JSONArray records;
        //JSONObject emp = null;

        try {
            records = st.getJSONArray ( "records" );


            processes = new ArrayList<ProcessDR> ();
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );
                processes.add ( new ProcessDR ( Integer.parseInt ( emp.get ( "PROCESS_ID" ).toString () ) , emp.get ( "PROCESS_NAME" ).toString () ) );

            }
        } catch ( Exception e ) {
        }
        
        
//        addEmpAPICall = "finishedgoods";
//        result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
//        map = new HashMap<String , Object> ();
//        jObject = new JSONObject ( result2 );
//        keys = jObject.keys ();
//
//        while ( keys.hasNext () ) {
//            String key = ( String ) keys.next ();
//            Object value = jObject.get ( key );
//            map.put ( key , value );
//        }
//
//        st = ( JSONObject ) map.get ( "data" );
//        records = st.getJSONArray ( "records" );
//
//        emp = null;
//        products = new ArrayList<ProductDR> ();
//        for ( int i = 0 ; i < records.length () ; i ++ ) {
//            emp = records.getJSONObject ( i );
//            products.add ( new ProductDR ( Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) , emp.get ( "FG_PART_NO" ).toString () , emp.get ( "FG_UOM_ID" ).toString () ) );
//        }
    

        
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
        
        jComboBox2.addActionListener ( productSelection );
         
        checkFromToDateAdd();
        removeAnyOldComponents();
        forms = new SingleHourDE_FormHourWise[ Store.size () ];
        loadDE_Label ();
        loadDE_Form1 ();
        
    }

    public void checkFromToDateAdd()
    {
        Store.removeAll(Store);
        if(shifthour.size()!=0)
        {
//           //if(jComboBox2.getItemCount()==0)
//           //{
//                from_time=(shifthour.get(0).fromtime);
//                to_time=(shifthour.get(0).totime);
//        
//                //format for getting time 
//                Date date=new Date();
//                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
//                SimpleDateFormat sdf1 = new SimpleDateFormat ( "HH:mm:ss" );
//                try{
//                    date=sdf.parse(from_time);
//                }catch(ParseException ex){JOptionPane.showMessageDialog(null, "Date error variable 3");}
//             
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(date);
//                calendar.add(Calendar.HOUR, 1);
//                date = calendar.getTime();
//                
//                d=sdf1.format(date);
//                System.out.println(d);     
//            
//                Date date1=new Date();
//                try{
//                    date1=sdf.parse(to_time);
//                }catch(ParseException ex){JOptionPane.showMessageDialog(null, "Date error variable 3");}
//             
//                calendar.setTime(date1);
//                calendar.add(Calendar.HOUR, 1);
//                date1 = calendar.getTime();
//                
//                d1=sdf1.format(date1);
//                System.out.println(d1);    
//            
//                d=date.getTime()+"01:00:00";
//                d1=date1.getTime()+"01:00:00";
//           }
           //else{
            
            //selectedShiftID = (shifthour.get(jComboBox2.getSelectedIndex()).SHIFT_ID);
            //from_time=(shifthour.get(jComboBox2.getSelectedIndex()).fromtime);
            
            selectedShiftID = (shifthour.get(0).SHIFT_ID);
            from_time=(shifthour.get(0).fromtime);
            //to_time=(shifthour.get(jComboBox2.getSelectedIndex()).totime);
        
            //format for getting time 
            Date date=new Date();
            SimpleDateFormat FormatYearTime=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            SimpleDateFormat FormatTime = new SimpleDateFormat ( "HH:mm:ss" );
//            try{
//                    date=sdf.parse(from_time);
//            }catch(ParseException ex){JOptionPane.showMessageDialog(null, "Date error variable 3");}
            
//            d=sdf1.format(date);
//            System.out.println(d);     
            
            String ftime=null,ttime;
            ftime=from_time;
            
            for(int i=0;i<24;i++)
            {
                
                SimpleDateFormat hour = new SimpleDateFormat ( "HH" );
                SimpleDateFormat min = new SimpleDateFormat ( "mm" );
                //ftime=("0000-00-00 "+ftime+":00");
                try{
                    date=FormatYearTime.parse(ftime);
                }catch(ParseException ex){JOptionPane.showMessageDialog(null, "Date error ");}
                
                
                int hr;
                String mn;
                hr=Integer.parseInt(hour.format(date));
                mn=(min.format(date));
                
                Calendar calendar = Calendar.getInstance();
               
                if(hr <= 24)
                {
             
                    calendar.setTime(date);
                    ftime=FormatTime.format(date);
                    
                    calendar.add(Calendar.HOUR, 1);
                    date = calendar.getTime();
                    
                    ttime=FormatTime.format(date);
                    
                    Store.add(new DateValue(ftime, ttime));
                    ftime=ttime;  
                    ftime="0000-00-00 "+ftime;
                }
                else
                {
                    ftime= "00:"+min+":00";
                    ftime=("0000-00-00 "+ftime);
                    try{
                        date=FormatYearTime.parse(ftime);
                    }catch(ParseException ex){JOptionPane.showMessageDialog(null, "Date error ");}
                    calendar.setTime(date);
                    
                    ftime=FormatTime.format(date);
                    
                    calendar.add(Calendar.HOUR, 1);
                    date = calendar.getTime();
                    
                    ttime=FormatTime.format(date);
                    
                    Store.add(new DateValue(ftime, ttime));
                    ftime=ttime;  
                    ftime="0000-00-00 "+ftime;
                    
                }
           // }
//            Date date1=new Date();
//            try{
//                    date1=sdf.parse(to_time);
//            }catch(ParseException ex){JOptionPane.showMessageDialog(null, "Date error variable 3");}
//            
//            d1=sdf1.format(date1);
//            System.out.println(d1);    
            
            //d=date.getTime()+"01:00:00";
            //d1=date1.getTime()+"01:00:00";
            
            
           }
            
        }
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
            columnNames.add("showFromTime");
            columnNames.add("showToTime");
            columnNames.add ( "BatchName" );
            columnNames.add ( "showrdate" );
            columnNames.add ( "target_qty" );
            columnNames.add ( "no_operators" );
            columnNames.add ( "actual_qty" );
            columnNames.add ( "rejection" );
            columnNames2 = new Vector<String> ();
            columnNames2.add ( "Part" );
            columnNames2.add ( "showFromTime" );
            columnNames2.add ( "showToTime" );
            columnNames2.add ( "Batch" );
            columnNames2.add ( "Date" );
            columnNames2.add ( "Target QTY" );
            columnNames2.add ( "No of Operators" );
            columnNames2.add ( "Actual_QTY" );
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

    
    @SuppressWarnings("null")
    public void loadDE_Form1 () {

        String recordExists;
        SingleHourDE_FormHourWise abc = null;
        removeAnyOldComponents();
        checkFromToDateAdd();
        
        
        for ( int i = 0 ; i < Store.size() ; i ++ ) {

            //  recordExists = "select * from dailyreport where batchno = '"+ jTextField1.getText ().trim ()  +"'  AND fgid = "+products.get(jComboBox1.getSelectedIndex ()).FG_ID+" AND processid = "+processes.get(i).PRC_ID;
            //    ResultSet yesRecordExist = DB_Operations.executeSingle ( recordExists );
//            int hr,min,currenthr,currentmin; 
//            int tohr,tomin;
//            String ftime,totime;
//            Date date=new Date();
//            Date todate=new Date();
//            Date currentdate=new Date();
//            
//            SimpleDateFormat FormatYearTime=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
//            SimpleDateFormat hour = new SimpleDateFormat ( "HH" );
//            SimpleDateFormat mints = new SimpleDateFormat ( "mm" );
//            
//            DateFormat df = new SimpleDateFormat("HH");
//            DateFormat df1=new  SimpleDateFormat("mm");
//            
//            Calendar calobj = Calendar.getInstance();
//            currenthr=Integer.parseInt(df.format(calobj.getTime()));
//            currentmin=Integer.parseInt(df1.format(calobj.getTime()));
            //System.out.println(df.format(calobj.getTime()));
            
            
            int hr,min,currentmin; 
            int tohr,tomin;
            String ftime,totime,currenthr;
//            Date date=new Date();
//            Date todate=new Date();
//            Date currentdate=new Date();
            
            SimpleDateFormat FormatYearTime=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            SimpleDateFormat FormatTime=new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat hour = new SimpleDateFormat ( "HH" );
            SimpleDateFormat mints = new SimpleDateFormat ( "mm" );
            //try{
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            //DateFormat df1=new  SimpleDateFormat("mm");
            
             Calendar currentCalObj = Calendar.getInstance();
             currenthr=(df.format(currentCalObj.getTime()));
            
             Calendar calendar2 = Calendar.getInstance();
             Calendar calendar1 = Calendar.getInstance(); 
             Calendar calendar3 = Calendar.getInstance();
            
                //ftime="0000-00-00 "+Store.get(i).ft;
                //totime="0000-00-00 "+Store.get(i).tt;
                  // date=FormatYearTime.parse(ftime);
                  // todate=FormatYearTime.parse(totime);
                try{   
                String string1 = Store.get(i).ft;
                Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
                
                calendar1.setTime(time1);
                calendar1.add(Calendar.DATE, 1);

                String string2 = Store.get(i).tt;
                Date time2;
                //time2 = new SimpleDateFormat("HH:mm").parse(string2);
                //time2 = new SimpleDateFormat("mm").parse(string2);
                //hourform=  new SimpleDateFormat("HH").parse(string2);
                //int temphr=0,tempmint=0;
                //tempmint=15+(Integer.parseInt(mints.format(time2)));
                //temphr=Integer.parseInt(hour.format(hourform));
                //string2= String.valueOf(temphr)+":"+String.valueOf(tempmint)+":00";
                time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
                calendar2.setTime(time2);
                calendar2.add(Calendar.MINUTE, 10);
                String newtime=FormatTime.format(calendar2.getTime());
                
                time2 = new SimpleDateFormat("HH:mm:ss").parse(newtime);
                calendar2.setTime(time2);
                calendar2.add(Calendar.DATE, 1);

                Date d = new SimpleDateFormat("HH:mm:ss").parse(currenthr);
                calendar3.setTime(d);
               
                }catch(ParseException pe){pe.getMessage();}
                 calendar3.add(Calendar.DATE, 1);
//            hr=Integer.parseInt(hour.format(date));
//            min=Integer.parseInt(mints.format(date));
//            
//            tohr=Integer.parseInt(hour.format(todate));
//            tomin=Integer.parseInt(mints.format(todate));

//            abc.setName ( "formno" + i );
//            int extramin=0;
//            extramin=10+tomin;
            //abc.setFromDate(shifthour.get ( i ).fromtime );
            
            abc = new SingleHourDE_FormHourWise ();
            Date x = calendar3.getTime();
            
            if(x.after(calendar1.getTime()) && x.before(calendar2.getTime()))
            {
                
                //if(min < currentmin && tomin < extramin)
                //{
                String value="True";
                abc.setFrmTime(Store.get(i).ft,value);
                abc.setToTime(Store.get(i).tt,value);
                //abc.setProducts ( products ,value);
                abc.setRejectedrea("",value);
                abc.setRejectionReasons(rej_reasons );
                abc.setTargetQTY("",value);
                abc.setNoOfOp("",value);
                abc.setActualQTY("",value);
                
                //}
            }
            else
            {
                String value="False";
                abc.setFrmTime(Store.get(i).ft,value);
                abc.setToTime(Store.get(i).tt, value);
                //abc.setProducts ( products ,value);
                abc.setRejectedrea("",value);
                abc.setRejectionReasons ( rej_reasons );
                abc.setTargetQTY("",value);
                abc.setNoOfOp("",value);
                abc.setActualQTY("",value);
                
            }
            //}catch(NullPointerException |ParseException  epx){epx.getMessage();}
            
            //abc.setFromTime(shifthour.get(jComboBox2.getSelectedIndex()).fromtime);
            //abc.setEmployee ( employees );
            
            //abc.setMachineID(machines);
            //abc.setProcessID(processes);      
            panel.add (abc);
            forms[ i ] = abc;
        }


        panel.setBackground ( Color.WHITE );
        panel.setBounds ( 0 , 0 , 1240 , 270 );
        panel.revalidate ();
        panel.repaint ();

        add ( scroll , BorderLayout.CENTER );

        revalidate();
        repaint();

        if ( forms.length > 1 ) {
            processRowsPresent = true;
        }
        
        getFreshList();
        
    }
    
    public void loadBatchesformProduct()
    {
  //////////////////////////////////////////////          Start    -  Load batches from batch master for selection   -    Start         ///////////////////////////////////////////////////////////       
        selectedFG_ID = products.get ( jComboBox2.getSelectedIndex () ).FG_ID;
        
        jComboBox1.removeAllItems ();
        jComboBox1.removeActionListener ( selectBatch );
        if ( jComboBox1.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );
            HashMap<String , Object> map = new HashMap<String , Object> ();
            String addEmpAPICall = "billofmaterials?FG_ID=" + selectedFG_ID;
            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            
            JSONObject jObject = new JSONObject ( result2 );
            Iterator<?> keys = jObject.keys ();
            
            if( ! result2.contains ( "not found") ){
                //map = new HashMap<String , Object> ();
                jObject = new JSONObject ( result2 );
                keys = jObject.keys ();
                while ( keys.hasNext () ) {
                    String key = ( String ) keys.next ();
                    Object value = jObject.get ( key );
                    map.put ( key , value );
                }
                ArrayList<String[]> rmids = new ArrayList<String[]> ();
                
                JSONObject st = ( JSONObject ) map.get ( "data" );
                JSONArray records = st.getJSONArray ( "records" );
                
               JSONObject emp = null;
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

                                if ( stockVal != 0 || stockVal2 != 0.0 ) {
                                    jComboBox1.addItem ( emp.get ( "BatchName" ).toString () );
                                    try {
                                        //batches.add( new RMBatchDR ( Integer.parseInt(emp.get ( "RMStock_TR_ID" ).toString ()),  Integer.parseInt(emp.get ( "RMI_ID" ).toString ()),   emp.get ( "inward_batch_no" ).toString () ,  Integer.parseInt( rmids.get(j)[1] ),  emp.get( "CLOSING").toString() , emp.get ( "RM_CODE" ).toString () )); 
                                        batches.add ( new RMBatchDR ( Integer.parseInt ( emp.get ( "BatchId" ).toString () ) , emp.get ( "BatchName" ).toString () , Integer.parseInt ( emp.get ( "ref_rm_id" ).toString () ) , Integer.parseInt ( rmids.get ( j )[ 1 ] ) , emp.get ( "BatchQty" ).toString () ) );
                                    } catch ( Exception e ) {
                                        System.out.println ( "" + e.getMessage () );
                                    }
                                }
                            }
                        }
                    } catch ( JSONException e ) {
                        System.out.println ( "" + e.getMessage () );
                    }
                }
            }

            if ( jComboBox1.getItemCount () > 0 ) {
                jComboBox1.addActionListener ( selectBatch );
                double availbleQty;
                int FGQty;
                RMBatchDR selectedBtch = batches.get ( jComboBox1.getSelectedIndex () );
                availbleQty = Double.parseDouble ( selectedBtch.availbleQty );
                FGQty = selectedBtch.FGQty;
               // jLabel26.setText ( String.valueOf ( availbleQty ) );
               // jLabel28.setText ( String.valueOf ( availbleQty * FGQty ) );
            }
        }
//////////////////////////////////////////////          End    -  Load batches from batch master for selection   -    End         /////////////////////////////////////////////////////////// 


    }
    
     public void loadDE_Label () {

        SingleHourDE_LabelsHours abc = new SingleHourDE_LabelsHours ();
        abc.setBounds ( 30 , 50 , 1280 , 35  );
        add ( abc );

        revalidate ();
        repaint ();
    }
     
      public void removeAnyOldComponents () {

        if ( processRowsPresent ) {
            for ( int i = 0 ; i < Store.size () ; i ++ ) {

                panel.remove ( panel.getComponentCount () - 1 );
                panel.revalidate ();
                panel.repaint ();
                System.out.println ( "Remove item at  " + getComponentCount () );
            }

            processRowsPresent = false;
        }

    }

//    public void removeAnyOldComponents () {
//
//        if ( processRowsPresent ) {
//            for ( int i = 0 ; i < shifthour.size () ; i ++ ) {
//
//                 panel.remove ( panel.getComponentCount () - 1 );
//                panel.revalidate ();
//                panel.repaint ();
//                System.out.println ( "Remove item at  " + getComponentCount () );
//            }
//
//            processRowsPresent = false;
//        }
//
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        dateChooserCombo3 = new datechooser.beans.DateChooserCombo();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1120, 500));

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Batch NO");

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("OA Number");

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

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Add");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Close");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        dateChooserCombo3.setCalendarPreferredSize(new java.awt.Dimension(320, 220));

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("Date");

        jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox2MouseClicked(evt);
            }
        });
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Select Part Code");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateChooserCombo3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(1110, 1137, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10, 10, 10)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 48, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateChooserCombo3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(4, 4, 4)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 360, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 377, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 193, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents
       ArrayList<DailyDataEntryModelHour> dataList;
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:

        //LoadingDialog.showLoadingDialog ( HomeScreen.home );

        DailyDataEntryModelHour data;
        //incompleteDataDialog = 0;

        SingleHourDE_FormHourWise abc1 = null;
        String abc = "";
        dataList = new ArrayList<DailyDataEntryModelHour> ();

        String rejectionData = "";

        for ( int i = 0 ; i < forms.length ; i ++ ) {

            abc1 = forms[ i ];
            abc1.getFrmTime();
            abc1.getTotime();
            //abc1.formatFromDate ();
            //abc1.formatToDate ();
              try{
                String currenthr;
                  
            SimpleDateFormat FormatYearTime=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            SimpleDateFormat FormatTime=new SimpleDateFormat("HH:mm:ss");

            //try{
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            //DateFormat df1=new  SimpleDateFormat("mm");
            
             Calendar currentCalObj = Calendar.getInstance();
             currenthr=(df.format(currentCalObj.getTime()));
            
             Calendar calendar2 = Calendar.getInstance();
             Calendar calendar1 = Calendar.getInstance(); 
             Calendar calendar3 = Calendar.getInstance();
                  
                String string1 =abc1.getFrmTime();
                Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
                
                calendar1.setTime(time1);
                calendar1.add(Calendar.DATE, 1);

                String string2 = abc1.getTotime();
                Date time2;
               
                time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
                calendar2.setTime(time2);
                calendar2.add(Calendar.MINUTE, 10);
                String newtime=FormatTime.format(calendar2.getTime());
                
                time2 = new SimpleDateFormat("HH:mm:ss").parse(newtime);
                calendar2.setTime(time2);
                calendar2.add(Calendar.DATE, 1);

                Date d = new SimpleDateFormat("HH:mm:ss").parse(currenthr);
                calendar3.setTime(d);
               
                 calendar3.add(Calendar.DATE, 1);

                 Date x = calendar3.getTime();
            
            if(x.after(calendar1.getTime()) && x.before(calendar2.getTime()))
            {
                  
                  
                if ( checkIfEmpty ( abc1.getTargetQTY () ,  abc1.getNoOfOp(), abc1.getActualQTY()) ) {

                    incompleteDataDialog = 1;

                    data = new DailyDataEntryModelHour ();

                    //data.setPFromDate ( abc1.getRDate () );
                    //data.setPToDate ( abc1.getRTDate () );
                    data.setFromDate ( abc1.getFromDate());
                    //data.setToDate ( abc1.getToDate () );
                    //data.setProductId ( abc1.getProductId ());
                    data.setFrmTime(abc1.getFrmTime () );
                    data.setToTime ( abc1.getTotime());
                    data.setTargetQTY(abc1.getTargetQTY());
                    data.setNoOfOp ( abc1.getNoOfOp() );
                    data.setRejectedrea(abc1.getRejectedrea());
                    data.setActualQTY(abc1.getActualQTY());
                    //data.setRejReason ( abc1.getRejectionReason () );
                    //data.setPFromTime ( abc1.fromTime () );
                    //data.setPToTime ( abc1.toTime () );
                    //data.setShiftData ( Integer.parseInt ( abc1.getShift () ) );

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
                    dataList.add ( data );
                }
                // }catch( NullPointerException e){
                //        JOptionPane.showMessageDialog ( null, "Exception :  "+e.getMessage ());
                // }
            }
            }catch(ParseException ed){ed.getMessage();}
        }   
        // save the daily data entry report for every process in the datalist arraylist
        for ( int i = 0 ; i < dataList.size () ; i ++ ) {

            String fdate=dataList.get(i).getFromDate();
                
                Date date=new Date();
                String showdate1=null,dateForStToTime=null;
                 try{
                     SimpleDateFormat FormatYr=new SimpleDateFormat("MMM dd, yyyy");
                     SimpleDateFormat initDate = new SimpleDateFormat("yyyy-MM-dd");
                     
                     date=initDate.parse(fdate);
                     
                     dateForStToTime=initDate.format(date);
                     showdate1=FormatYr.format(dateChooserCombo3.getSelectedDate ().getTime ());
                     //initDate.applyPattern("yyyy-MM-dd HH:mm:ss");
                     //System.out.println(initDate.format(date));
                     //showdate1=FormatYr.format(date);
                     //SimpleDateFormat FormatYearTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             
                    //showdate1=FormatYearTime.format(date);
                    
                }catch(Exception ex)
                {ex.getMessage();}
            try{
              
                //String x1=null;
                //String date1;
                //String fromTime = dataList.get ( i ).getFrmTime().replace( " 00:00:00", " "+dataList.get ( i ).getFrmTime());
                //String toTime = dataList.get ( i ).getToTime().replace( " 00:00:00", " "+dataList.get ( i ).getToTime ()  );
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //x1 = sdf.format(dateChooserCombo3.getSelectedDate().getTime());
                //date1=x1;
                String addEmpAPICall =   "dailyreportadd?showrdate="+URLEncoder.encode( showdate1, "UTF-8")+"&showrtdate="+URLEncoder.encode( showdate1, "UTF-8")+"&rdate="+URLEncoder.encode( fdate+"", "UTF-8")+"&rtdate="+URLEncoder.encode( fdate+"", "UTF-8")+"&showFromTime="+URLEncoder.encode( dateForStToTime+" "+dataList.get ( i ).getFrmTime(), "UTF-8")+"&showToTime="+URLEncoder.encode(dateForStToTime+" "+dataList.get ( i ).getToTime(), "UTF-8")+"&starttime="+URLEncoder.encode(dateForStToTime+" "+dataList.get ( i ).getFrmTime(), "UTF-8")+"&stoptime="+URLEncoder.encode(dateForStToTime+" "+dataList.get ( i ).getToTime(), "UTF-8")+"&target_qty="+URLEncoder.encode(dataList.get ( i ).getTargetQTY(), "UTF-8")+"&no_operators="+URLEncoder.encode(dataList.get ( i ).getNoOfOp()+"","UTF-8")+"&actual_qty="+URLEncoder.encode(dataList.get ( i ).getActualQTY()+"","UTF-8")+"&batchno="+URLEncoder.encode(  batches.get ( jComboBox1.getSelectedIndex () ).BTC_ID+"", "UTF-8")+"&actual_min="+URLEncoder.encode((dataList.get(i).getTotalTimeInMinutes () - dataList.get(i).getTotalTimeLossInMinutes ())+"", "UTF-8")+"&total_min="+URLEncoder.encode(dataList.get(i).getTotalTimeInMinutes ()+"", "UTF-8")+"&actual_hours="+URLEncoder.encode( (dataList.get(i).getTotalTimeInHours ()-dataList.get(i).getTotalTimeLossInHours ( )) +"", "UTF-8")+"&rejection="+URLEncoder.encode(  dataList.get ( i ).getRejectedrea()+"", "UTF-8")+"&total_hours="+URLEncoder.encode( dataList.get(i).getTotalTimeInHours ()+"", "UTF-8")+"&customer_id="+URLEncoder.encode(orders.get ( jComboBox4.getSelectedIndex () ).ORD_ID + "" +"", "UTF-8")+"&fgid="+URLEncoder.encode( products.get ( jComboBox1.getSelectedIndex () ).FG_ID   +"", "UTF-8")  ; 
                //JOptionPane.showMessageDialog(null, addEmpAPICall);
                //String addEmpAPICall =   "dailyreportadd?rdate="+URLEncoder.encode( dataList.get ( i ).getPFromDate ()+" 00:00:00", "UTF-8")+"&rtdate="+URLEncoder.encode(dataList.get ( i ).getPToDate (), "UTF-8")+"&showrdate="+URLEncoder.encode( dataList.get ( i ).getFromDate () , "UTF-8")+"&showrtdate="+URLEncoder.encode(  dataList.get ( i ).getToDate () , "UTF-8")+"&fgid="+URLEncoder.encode( dataList.get ( i ).getProductId ()  +"", "UTF-8")+"&machineid="+URLEncoder.encode(machines.get ( jComboBox3.getSelectedIndex () ).MC_ID+"", "UTF-8")+"&processid="+URLEncoder.encode( dataList.get(i).getProcessID () +"","UTF-8")+"&starttime="+URLEncoder.encode( fromTime+":00" ,"UTF-8")+"&stoptime="+URLEncoder.encode(  toTime+":00"  ,"UTF-8")+"&qtyin="+URLEncoder.encode(dataList.get ( i ).getQIn (), "UTF-8")+"&qtyout="+URLEncoder.encode(dataList.get ( i ).getQout ()+"","UTF-8")+"&rejection="+URLEncoder.encode(  dataList.get ( i ).getRejected ()  +"", "UTF-8")+"&empid="+URLEncoder.encode( dataList.get ( i).getEmployee ()+"", "UTF-8")+"&showFromTime="+URLEncoder.encode( fromTime, "UTF-8")+"&showToTime="+URLEncoder.encode( toTime, "UTF-8")+"&batchno="+URLEncoder.encode(  batches.get ( jComboBox1.getSelectedIndex () ).RMID+"", "UTF-8")+"&rej_reason="+URLEncoder.encode(rej_reasons.get ( dataList.get ( i ).getRej_Reason () ).REJ_ID+"", "UTF-8")+"&shift_id="+URLEncoder.encode(dataList.get(i).getShiftID ()+"", "UTF-8")+"&actual_min="+URLEncoder.encode((dataList.get(i).getTotalTimeInMinutes () - dataList.get(i).getTotalTimeLossInMinutes ())+"", "UTF-8")+"&total_min="+URLEncoder.encode(dataList.get(i).getTotalTimeInMinutes ()+"", "UTF-8")+"&actual_hours="+URLEncoder.encode( (dataList.get(i).getTotalTimeInHours ()-dataList.get(i).getTotalTimeLossInHours ( )) +"", "UTF-8")+"&total_hours="+URLEncoder.encode( dataList.get(i).getTotalTimeInHours ()+"", "UTF-8")+"&customer_id="+URLEncoder.encode(customerId+"", "UTF-8")  ;
                String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                JOptionPane.showMessageDialog(null, result2);
                //timeLossList =  dataList.get(i).selectedtimeLossData    ;
                //rejectionList =  dataList.get(i).selectedtimeRejections  ;

                //int recordId = iDFromJSON ( result2 ) ;
//                for(  int j=0 ;  j<timeLossList.size(); j++ ){
//                    totalTimeLossInMinutes = totalTimeLossInMinutes + timeLossList.get(j).getMinutes () ;
//                    addEmpAPICall =  "timelossadd?ref_pID="+recordId+"&tlrID="+timeLossList.get(j).getId ()+"&tlQty="+timeLossList.get(j).getMinutes ()  ;
//                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
//                }
//
//                for ( int j = 0 ; j < rejectionList.size () ; j ++ ) {
//                    //totalRejection = totalRejection + rejectionList.get ( j ).getRejectionForReason ();
//                    addEmpAPICall = "rejdataadd?ref_pID=" + recordId + "&rrID=" + rejectionList.get ( j ).getRejectionReasonId () + "&rQty=" + rejectionList.get ( j ).getRejectionForReason ();
//                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
//                }

//                String getWIPforSelectedData = "opwip2add?fgid=" + dataList.get ( i ).getProductId () + "&processid=" + dataList.get ( i ).getProcessID () + "&empid=" + dataList.get ( i ).getEmployee () + "&machine=" + machines.get ( jComboBox3.getSelectedIndex () ).MC_ID + "&ORDERNO=" + orders.get ( jComboBox4.getSelectedIndex () ).ORD_ID + "&FINISHED=" + dataList.get ( i ).getQout () + "&MOVEQTY=" + dataList.get ( i ).getQIn () + "&MOVEDATE=" + URLEncoder.encode ( dataList.get ( i ).getPFromDate () + " 00:00:00" , "UTF-8" ) + "&BALANCE=" + ( Integer.parseInt ( dataList.get ( i ).getQIn () ) - Integer.parseInt ( dataList.get ( i ).getQout () ) );
//                WebAPITester.prepareWebCall ( getWIPforSelectedData , StaticValues.getHeader () , "" );
//
//                //addToStores ( products.get ( jComboBox1.getSelectedIndex () ).FG_ID ,  Integer.parseInt( dataList.get ( i ).getProcessID () ), Integer.parseInt( dataList.get ( i ).getQout ()),  Integer.parseInt(  dataList.get ( i ).getRejected ()));
//                addToStores ( products.get ( jComboBox1.getSelectedIndex () ).FG_ID , Integer.parseInt ( dataList.get ( i ).getProcessID () ) , Integer.parseInt ( dataList.get ( i ).getQout () ) , Integer.parseInt ( dataList.get ( i ).getRejected () ) );

                //                JOptionPane.showMessageDialog ( null , result2);

            }catch(Exception e3){
            }
            //loadEntries ();
            //LoadingDialog.hideLoadingDialog ();
        }

//        /*        // save the required details from datalist arraylist into op_wip2 for  every process
//        int QInSUm = -1, QOutSum = -1;
//        for ( int i = 0 ; i < dataList.size () ; i ++ ) {
//            ResultSet yesRecordExist = null;
//            try {
//                String recordExists;
//                boolean recordExist = false;
//                recordExists = "select *  from op_wip2 where PRODUCT = " + dataList.get (i).getProductId () + " AND PROCESS_STAGE = " + processes.get ( jComboBox2.getSelectedIndex () ).PRC_ID + " AND machine = " + machines.get ( jComboBox3.getSelectedIndex () ).MC_ID + " AND empid =  " + employees.get ( dataList.get ( i ).getEmployee () ).EMP_ID;
//                yesRecordExist = DB_Operations.executeSingle ( recordExists );
//
//                if ( yesRecordExist.isBeforeFirst () ) {
//                    recordExist = true;
//                    try {
//                        yesRecordExist.close ();
//
//                    } catch ( Exception ex ) {
//                        StaticValues.writer.writeExcel ( DailyReportFormMachineWise.class.getSimpleName () , DailyReportFormMachineWise.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//                    }
//                }
//
//                String getAccumulatedEntries = "select SUM(qtyin) as 'Qty In',  SUM ( qtyout ) as 'Qty Out' from dailyreport where fgid = " + dataList.get (i).getProductId () + " AND processid = " + processes.get ( jComboBox2.getSelectedIndex () ).PRC_ID + " AND machineid   = " + machines.get ( jComboBox3.getSelectedIndex () ).MC_ID + " AND empid = " + employees.get ( dataList.get ( i ).getEmployee () ).EMP_ID + " group by          fgid";
//                yesRecordExist = DB_Operations.executeSingle ( getAccumulatedEntries );
//                if ( yesRecordExist.isBeforeFirst () ) {
//                    QInSUm = yesRecordExist.getInt ( 1 );
//                    QOutSum = yesRecordExist.getInt ( 2 );
//                }
//                yesRecordExist.close ();
//
//                String queryInsert = null;
//
//                if ( recordExist ) {
//                    // queryInsert = "update op_wip2 set FINISHED = " + dataList.get ( i ).getQout () + ", MOVEQTY = " + dataList.get ( i ).getQIn () + ", BALANCE = " + ( Integer.parseInt ( dataList.get ( i ).getQIn () ) - Integer.parseInt ( dataList.get ( i ).getQout () ) ) + " where PRODUCT = " + products.get ( jComboBox1.getSelectedIndex () ).FG_ID + " AND PROCESS_STAGE = " + processes.get ( i ).PRC_ID + " AND machine   = " + machines.get ( i ).MC_ID + " AND empid = " + employees.get ( i ).EMP_ID;
//                    // queryInsert = "update op_wip2 set FINISHED = " + QOutSum + ", MOVEQTY = " + QInSUm + ", BALANCE = " + ( QInSUm - QOutSum ) + " where PRODUCT = " + dataList.get (i).getProductId () + " AND PROCESS_STAGE = " + processes.get ( jComboBox2.getSelectedIndex () ).PRC_ID + " AND machine = " + machines.get ( dataList.get ( i ).getMachine () ).MC_ID + " AND empid =  " + employees.get ( dataList.get ( i ).getEmployee () ).EMP_ID;
//                    // DB_Operations.executeInsertRandom ( queryInsert );
//                } else {
//                    queryInsert = "insert into op_wip2 ( PRODUCT, REJECTED, REASON, FINISHED,   MOVEQTY, MOVEDATE, ORDERNO, PROCESS_STAGE, BALANCE, REJECTIONDATE,  DRID, machine, empid ) values ( " +dataList.get (i).getProductId () + "," + dataList.get ( i ).getRejected () + ",' NA ', " + dataList.get ( i ).getQout () + "," + dataList.get ( i ).getQIn () + ",'" + dataList.get ( i ).getPFromDate () + "', 'NA', " + processes.get ( jComboBox2.getSelectedIndex () ).PRC_ID + ", (" + ( Integer.parseInt ( dataList.get ( i ).getQIn () ) - Integer.parseInt ( dataList.get ( i ).getQout () ) ) + "), 'NA'," + 0 + ", " + machines.get ( dataList.get ( i ).getMachine () ).MC_ID   + "," + employees.get ( dataList.get ( i ).getEmployee () ).EMP_ID + " )";
//                    DB_Operations.executeInsertRandom ( queryInsert );
//                }
//
//                removeAnyOldComponents() ;
//                loadDE_Form ();
//
//            } catch ( Exception e ) {
//                System.out.println ( "Exception in op_wip2    " + e.getMessage () );
//                StaticValues.writer.writeExcel ( DailyReportFormMachineWise.class.getSimpleName () , DailyReportFormMachineWise.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//            }
//
//        }  */
        //}
        getFreshList ();
        loadDE_Form1();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        HomeScreen.home.removeForms ();

        //  removeAnyOldComponents ();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
        if ( jComboBox2.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add values in PartName from Finished Good Master" );
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2MouseClicked

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:

        //        removeAnyOldComponents ();
        //        loadProcessesForProduct ();
        //        forms = new SingleProcessDE_Form[ processes.size () ];
        //        loadDE_Label ();
        //        loadDE_Form ();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    public boolean checkIfEmpty ( String testVal , String  a , String b  ) {

        boolean formcomplete = true;

        if ( testVal.equalsIgnoreCase ( "" ) || testVal.equalsIgnoreCase ( "0" ) ) {
            formcomplete = false;
        }
        if ( a.equalsIgnoreCase ( "" ) || a.equalsIgnoreCase ( "0" ) ) {
                formcomplete = false;
            }
        if ( b.equalsIgnoreCase ( "" ) || b.equalsIgnoreCase ( "0" ) ) {
                formcomplete = false;
            }
        
        if(formcomplete == false )
        {
            JOptionPane.showMessageDialog(null, "Please fill all fields grater than 0 or non empty field");
        }

//        if ( a == 0 ) {
//            if ( b == 0 ) {
//                formcomplete = false;
//            }
//        }
//
//        if ( c == 0 ) {
//            if ( d == 0 ) {
//                formcomplete = false;
//            }
//        }
//        if (  ! formcomplete ) {
//            showIncompleteFormDialog ( "<html><b>Incomplete form.</b> <br>Please fill all fields grater than 0 or non empty field</html>" );
//            
//        }

        return formcomplete;
    }
    public void showIncompleteFormDialog ( String message ) {

        if ( incompleteDataDialog == 0 ) {
            incompleteDataDialog = 1;
            JOptionPane.showMessageDialog ( null , message );
        }
    }
    public void callMethod() {
        //if(i==0)
        //{
            i=i+1;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.print("time is over new hour is started wait");
                loadDE_Form1();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 100, 600000);    //1 s = 1000ms //1 m = 60s  //1 h = 60 m
        //}
    }
        public ActionListener productSelection = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
            
            loadBatchesformProduct();

            }
        };
        ActionListener selectBatch = new ActionListener () {
        @Override
        public void actionPerformed ( java.awt.event.ActionEvent e ) {

            if ( jComboBox1.getItemCount () > 0 ) {
                //  jComboBox7.addActionListener ( selectBatch );
                double availbleQty;
                int FGQty, RMID, BTC_ID;
                RMBatchDR selectedBtch = batches.get ( jComboBox1.getSelectedIndex () );
//             BTC_NAME = selectedBtch.BTC_NAME ;
                availbleQty = Double.parseDouble ( selectedBtch.availbleQty );
                RMID = selectedBtch.RMID ;
                FGQty = selectedBtch.FGQty;
                BTC_ID    = selectedBtch.BTC_ID ;    
               // jLabel26.setText ( String.valueOf ( availbleQty ) );
               // jLabel28.setText ( String.valueOf ( availbleQty * FGQty ) );
            }
        }
    };
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
class DateValue {
    public String ft;
    public String tt;

    public DateValue(String ft, String tt) {
        this.ft = ft;
        this.tt = tt;
    }
  }
}
