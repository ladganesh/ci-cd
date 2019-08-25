/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import components.CustomScrollPane1;
import trixware.erp.prodezydesktop.Derived.AutoCompletion;
import trixware.erp.prodezydesktop.Model.Batch;
import trixware.erp.prodezydesktop.Model.DailySetupModel;
import trixware.erp.prodezydesktop.Model.EmployeeDR;
import trixware.erp.prodezydesktop.Model.MachineDR;
import trixware.erp.prodezydesktop.Model.POrderDR;
import trixware.erp.prodezydesktop.Model.ProcessDR;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.RMBatchDR;
import trixware.erp.prodezydesktop.Model.RejectionModel;
import trixware.erp.prodezydesktop.Model.Rejection_Reasons;
import trixware.erp.prodezydesktop.Model.ShiftDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Model.TimeLossModel;
import trixware.erp.prodezydesktop.Model.UOMDR;
import trixware.erp.prodezydesktop.Utilities.ConfirmProdDataImport;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.MyCustomFilter;

import trixware.erp.prodezydesktop.components.DateSelectionForm;
import trixware.erp.prodezydesktop.components.ProgressDialog;
import trixware.erp.prodezydesktop.components.RejectionDetailPanel;
import trixware.erp.prodezydesktop.components.TimeLossDetailPanel;
import datechooser.view.WeekDaysStyle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.nio.channels.FileChannel;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Locale;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import trixware.erp.prodezydesktop.masters.BatchProcessing;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.TimeLoss_Reasons;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class DailyReportForm extends javax.swing.JPanel {

//    ArrayList<HashMap<String, String>> products = null ; 
//    ArrayList<HashMap<String, String>> employees = null ; 
//    ArrayList<HashMap<String, String>> machines = null ; 
//    ArrayList<HashMap<String, String>> processes;

    ArrayList<ProductDR> products = null;
    ArrayList<EmployeeDR> employees = null;
    ArrayList<UOMDR> UOM = null;
    ArrayList<ProcessDR> processes = null;
    ArrayList<MachineDR> machines = null;
    ArrayList<RMBatchDR> batches = null;
    ArrayList<POrderDR> orders = null;
    ArrayList<Rejection_Reasons> rej_reasons = null;
    ArrayList<TimeLoss_Reasons> timeloss_reasons = null;
    ArrayList<ShiftDR> shifts = null;

    ShiftsList confirm = null;
    TimeLossList timeLoss = null;
    RejectionsList rejection = null;

    int incompleteDataDialog = 0;

    ProductDR prdr = null;
    EmployeeDR empdr = null;
    ProcessDR prcdr = null;
    MachineDR mcdr = null;
    Rejection_Reasons rejrsn = null;
    ShiftDR shift = null;
    Batch batch = null;
 //    HashMap<String, String> entity = null ;

    Calendar c2 = null;
    SimpleDateFormat sdf = null;

    DailySetupModel dSM = null;

    JSpinner fromTimeSpinner = new JSpinner ( new SpinnerDateModel () );
    JSpinner toTimeSpinner = new JSpinner ( new SpinnerDateModel () );
    JSpinner dateSpinner = new JSpinner ( new SpinnerDateModel () );
    JSpinner.DateEditor fromTimeEditor = new JSpinner.DateEditor ( fromTimeSpinner , "hh:mm a" );
    JSpinner.DateEditor toTimeEditor = new JSpinner.DateEditor ( toTimeSpinner , "hh:mm a" );
    
    DateSelectionForm selectDate =  new DateSelectionForm ("From Date", true, "Setup time is mandatory" );
    
    boolean RMStockUpdatedOnfirstProcess = false;

    public Thread t;

    //new
    double convertRate = 0.0 ;
    int conversionFactor = 0 ;
    
    int wip =0 ,wip_id =0;
        double wip_double = 0.0 ;
    
    /**
     * Creates new form DailyReportForm
     */
    public DailyReportForm () 
    {
        initComponents ();

        AutoCompletion.enable ( jComboBox1 );
        AutoCompletion.enable ( jComboBox2 );
        AutoCompletion.enable ( jComboBox3 );
        AutoCompletion.enable ( jComboBox4 );
        AutoCompletion.enable ( jComboBox5 );

        add(jCheckBox_wip);
        jCheckBox_wip.setText("Consider WIP");
        jCheckBox_wip.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBox_wipMouseClicked(evt);
            }
        });
        jCheckBox_wip.setBounds(170, 130, 150, 23);

//        jTextField6.addFocusListener(Qin_focus);
        
        dateChooserCombo1.setText ( dateChooserCombo3.getText () );

        dateChooserCombo1.setFormat ( DateFormat.MEDIUM );
        dateChooserCombo3.setFormat ( DateFormat.MEDIUM );
        dateChooserCombo1.setWeekStyle ( WeekDaysStyle.NORMAL );
        dateChooserCombo3.setWeekStyle ( WeekDaysStyle.NORMAL );
        dateChooserCombo1.setOpaque ( false );
        dateChooserCombo3.setOpaque ( false );
        dateChooserCombo1.setBackground ( Color.WHITE );
        dateChooserCombo3.setBackground ( Color.WHITE );
        dateChooserCombo1.revalidate ();
        dateChooserCombo3.repaint ();

        jTextField1.addFocusListener ( f );
        jTextField1.addKeyListener ( k );

        jTextField2.addFocusListener ( f2 );
        jTextField2.addKeyListener ( k );

        jTextField3.addFocusListener ( f );
        jTextField3.addKeyListener ( k );

        jTextField4.addFocusListener ( f2 );
        jTextField4.addKeyListener ( k );

        jTextField5.addFocusListener ( f4 );
        jTextField6.addFocusListener ( f4 );
        jTextField7.addFocusListener ( f3 );
        jTextField5.addKeyListener ( k );
        jTextField6.addKeyListener ( k );
        jTextField7.addKeyListener ( k );

        jTextField10.addKeyListener ( k );
        jTextField11.addKeyListener ( k );
        jTextField12.addKeyListener ( k );
        jTextField13.addKeyListener ( k );
        jTextField14.addKeyListener ( k );
        jTextField15.addKeyListener ( k );
        jTextField16.addKeyListener ( k );
        jTextField17.addKeyListener ( k );
        jTextField10.addFocusListener ( minuteFieldTimeLoss );
        jTextField11.addFocusListener ( minuteFieldTimeLoss );
        jTextField12.addFocusListener ( minuteFieldTimeLoss );
        jTextField13.addFocusListener ( minuteFieldTimeLoss );
        jTextField14.addFocusListener ( minuteFieldTimeLoss );
        jTextField15.addFocusListener ( minuteFieldTimeLoss );
        jTextField16.addFocusListener ( minuteFieldTimeLoss );
        jTextField17.addFocusListener ( minuteFieldTimeLoss );
        
        
        
        fromTimeSpinner.setEditor ( fromTimeEditor );
        fromTimeSpinner.setValue ( new Date () );
        fromTimeSpinner.setBounds ( 310 , 40 , 70 , 32 );
        fromTimeSpinner.setOpaque ( false );
        fromTimeSpinner.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 12 ) );
        add ( fromTimeSpinner );

        toTimeSpinner.setEditor ( toTimeEditor );
        toTimeSpinner.setValue ( new Date () );
        toTimeSpinner.setBounds ( 310 , 90 , 70 , 32 );
        toTimeSpinner.setOpaque ( false );
        toTimeSpinner.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 12 ) );
        add ( toTimeSpinner );

        
        // X 340   Y 230
        
        selectDate.setBounds ( 350 , 250 , 260 , 64 );
        
        add ( selectDate );
        
        
        confirm = new ShiftsList ();
        confirm.setBounds ( 400 , 40 , 160 , 160 );
        add ( confirm );

        timeLoss = new TimeLossList ();
        timeLoss.setBounds ( 570 , 40 , 260 , 260 );
        add ( timeLoss );

        rejection = new RejectionsList ();
        rejection.setBounds ( 840 , 40 , 260 , 260 );
        add ( rejection );

        loadComboBoxes ();
        loadEntries ();

        //estimateProductionFromGivenBatch();
        //jButton3.setVisible ( false );
        t = new Thread () {

            public void run () {
                getFreshlist ();
            }
        };
        t.start ();
    }

    public void getFreshlist () {

        String getProductionData = "dailyreports";
        String productionDataresult2 = WebAPITester.prepareWebCall ( getProductionData , StaticValues.getHeader () , "" );

        Vector<String> columnNames = new Vector<String> ();
            Vector<String> columnNames2 = new Vector<String> ();
             Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        if (  ! productionDataresult2.contains ( "not found" ) ) {
            
           

            HashMap<String , Object> map = new HashMap<String , Object> ();
            JSONObject jObject = new JSONObject ( productionDataresult2 );
            Iterator<?> keys = jObject.keys ();

            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }
            
            
                JSONObject st = (JSONObject) map.get ( "data" );
                JSONArray records = st.getJSONArray ( "records");

                JSONObject emp = null;

                columnNames = new Vector<String> ();
                columnNames.add( "PART_NAME"  );columnNames.add( "PROCESS_NAME"  );columnNames.add( "MACHINE_NO"  );columnNames.add( "EMP_NAME"  );columnNames.add( "shifttitle"  );columnNames.add( "BatchName"  );columnNames.add( "showrdate"  );columnNames.add( "qtyin"  );columnNames.add( "qtyout"  );columnNames.add( "rejection"  );
                columnNames2 = new Vector<String> ();
                columnNames2.add( "Part Code"   );columnNames2.add( "Process"  );columnNames2.add( "Machine"  );columnNames2.add( "Employee"  );columnNames2.add( "Shift"  );columnNames2.add( "Batch"  );columnNames2.add( "Date"  );columnNames2.add( "Parts In"  );columnNames2.add( "Parts Out"  );columnNames2.add( "Rejected"  );

                 for( int i=0; i<records.length (); i++ ){
                 Vector<Object> vector = new Vector<Object> ();
                     for ( int columnIndex = 0 ; columnIndex <columnNames.size () ; columnIndex ++ ) {
                         emp = records.getJSONObject ( i);
                         if( emp.get ( columnNames.get ( columnIndex ) ) != null   && !emp.get ( columnNames.get ( columnIndex ) ).toString().equals("null")){
                             vector.add ( emp.get ( columnNames.get ( columnIndex ) )  );
                         }else{
                             vector.add ( "--" );
                         }
                     }
                     data.add ( vector );
                     jTable1.setModel( new DefaultTableModel ( data , columnNames2 ) );
                    

                 }
             
            }
/*
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
                
                try{
                    jTable1.setModel ( new DefaultTableModel ( data , columnNames2 ) );
                }catch( Exception e  ){
                    
                }
            }*/

        }


    KeyListener k = new KeyListener () {
        @Override
        public void keyTyped ( KeyEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            char enter = e.getKeyChar ();
            if (  ! ( Character.isDigit ( enter ) ) ) {
                e.consume ();
            }
        }

        @Override
        public void keyPressed ( KeyEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyReleased ( KeyEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }
    };

    FocusListener f2 = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
            JTextField jcb = ( JTextField ) e.getSource ();

            jcb.selectAll ();
        }

        @Override
        public void focusLost ( FocusEvent e ) {

            JTextField jcb = ( JTextField ) e.getSource ();
            String x = jcb.getText ().trim ();

            if ( x.equalsIgnoreCase ( "" ) ) {
                x = "0";
            }

            try {
                int num = Integer.parseInt ( String.valueOf ( jcb.getText ().toString () ) );
                if ( num < 0 || num > 59 ) {
                    jcb.setText ( "0" );
                }
            } catch ( NumberFormatException ex1 ) {
                jcb.setText ( x );
                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , ex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }
    };

    FocusListener minuteFieldTimeLoss = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
            JTextField jcb = ( JTextField ) e.getSource ();
            jcb.selectAll ();
        }

        @Override
        public void focusLost ( FocusEvent e ) {

            JTextField jcb = ( JTextField ) e.getSource ();
            String x = jcb.getText ().trim ();

            if ( x.equalsIgnoreCase ( "" ) ) {
                x = "0";
            }

            try {
                int num = Integer.parseInt ( String.valueOf ( jcb.getText ().toString () ) );
                if ( num < 0 || num > 720 ) {
                    jcb.setText ( "0" );
                }
            } catch ( NumberFormatException ex1 ) {
                jcb.setText ( "0" );
                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , ex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }
    };

    double out = 0.0 ;
    
    /*                System.out.println ( "conversionFactor    "+conversionFactor );
                double in = Double.parseDouble(jTextField6.getText( ) );
                if( conversionFactor > 0 && conversionFactor > 0.0 )
                out = in * conversionFactor ;
                jTextField5.setText( out + ""); */
    
    
    
    public void resetFields () {
//        jTextField1.setText ( "0" );
//        jTextField2.setText ( "0" );
//        jTextField3.setText ( "0" );
//        jTextField4.setText ( "0" );
        jTextField5.setText ( "0" );
        jTextField6.setText ( "0" );
        jTextField7.setText ( "0" );
//        jTextField8.setText ( "" );
//        jTextField9.setText ( "" );

        remove ( fromTimeSpinner );
        remove ( toTimeSpinner );
        remove ( confirm );
        remove ( timeLoss );
        remove ( rejection );

        fromTimeSpinner.setEditor ( fromTimeEditor );
        fromTimeSpinner.setValue ( new Date () );
        fromTimeSpinner.setBounds ( 310 , 40 , 70 , 32 );
        fromTimeSpinner.setOpaque ( false );
        fromTimeSpinner.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 12 ) );
        add ( fromTimeSpinner );

        toTimeSpinner.setEditor ( toTimeEditor );
        toTimeSpinner.setValue ( new Date () );
        toTimeSpinner.setBounds ( 310 , 90 , 70 , 32 );
        toTimeSpinner.setOpaque ( false );
        toTimeSpinner.setFont ( new java.awt.Font ( "Leelawadee UI" , 0 , 12 ) );
        add ( toTimeSpinner );

        confirm = new ShiftsList ();
        confirm.setBounds ( 400 , 40 , 160 , 160 );
        add ( confirm );

        timeLoss = new TimeLossList ();
        timeLoss.setBounds ( 570 , 40 , 260 , 260 );
        add ( timeLoss );

        rejection = new RejectionsList ();
        rejection.setBounds ( 840 , 40 , 260 , 260 );
        add ( rejection );

    }

    public void addToStores ( int productId , int processId  ,int inQty ) {

        // check if the qty is processed from last process
        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
        String strDate2 = sdf2.format ( c2.getTime () );
        int recordPK = 0;

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

                    recordPK = Integer.parseInt ( records.getJSONObject ( 0 ).get ( "FGStock_TR_ID" ).toString () );
                } else {
                    System.out.println ( "No result to show" );
                    recordExist = false;
                    OPENING = 0;
                    RECEIVED = 0;
                    USED = 0;
                    CLOSING = 0;
                }

                OPENING = OldCLOSING;

                RECEIVED = Integer.parseInt ( jTextField5.getText () ) - Integer.parseInt ( jTextField7.getText () );
                USED = 0;
                CLOSING = OPENING + RECEIVED;

                //if ( verifyLastProcess ( productId , processId ) ) {
                if ( verifyFirstOrOnlyProcess ( productId , processId ).equals( "lastProcess" )  )
                {
                    String updateStock = "fgstockadd?FG_ID=" + productId + "&FG_ITEM_ID=" + productId + "&OPENING=" + OPENING + "&RECEIVED=" + RECEIVED + "&USED=" + USED + "&CLOSING=" + CLOSING + "&CREATED_ON=" + URLEncoder.encode ( new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00", "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00" , "UTF-8" ) + "&EDITED_BY=" + Proman_User.getEID () + "&SALE_RATE=" + products.get ( jComboBox1.getSelectedIndex () ).getRate ();
                    result2 = WebAPITester.prepareWebCall ( updateStock , StaticValues.getHeader () , "" );
                    JOptionPane.showMessageDialog ( null , result2 );
                }//else if ( verifyFirstOrOnlyProcess ( productId , processId ) ) {
                else if ( verifyFirstOrOnlyProcess ( productId , processId ).equals( "firstProcess" ) ) 
                {

                  // New code for updating rmstock and batch replace line no 557
                    
                    ////////////////////////////////////////////////    START   -     Code to reduce raw material according to parts created and added to FG Stock   -  START //////////////////////////////////////////////////////////////////////////////                                    
                    BatchProcessing batch = new BatchProcessing ();
                    
                    
                    if( UOMIN.getItemCount() > 0 )
                    {
                        if(   UOMIN.getSelectedItem().toString().equals ( "NOs.") )
                        {
                            RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( 
							batches.get ( jComboBox7.getSelectedIndex () ).RMID , 
							batches.get ( jComboBox7.getSelectedIndex () ).FGQty , 
							Integer.parseInt ( jTextField5.getText () ) ,
							batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID ,
							inQty *convertRate , 
							inQty ,
                                                                                                                    new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00" );
                            if(  RMStockUpdatedOnfirstProcess   ){
		updateRMStockCounter ( batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID );
                            }
                            
                        }else if(   UOMIN.getSelectedItem().toString().equals ( "KG") )
						{
                            if(convertRate>0.0)
                                RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( batches.get ( jComboBox7.getSelectedIndex () ).RMID , batches.get ( jComboBox7.getSelectedIndex () ).FGQty , Integer.parseInt ( jTextField5.getText () ) , batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID , inQty*1.0  , (int)(inQty / convertRate)  , new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00" );
                            else
                                RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( batches.get ( jComboBox7.getSelectedIndex () ).RMID , batches.get ( jComboBox7.getSelectedIndex () ).FGQty , Integer.parseInt ( jTextField5.getText () ) , batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID , inQty*1.0  , inQty  ,  new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00" );

                            if(  RMStockUpdatedOnfirstProcess   )
							{
                                updateRMStockCounter ( batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID );
                            }

                        }else
						{
                            RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( batches.get ( jComboBox7.getSelectedIndex () ).RMID , batches.get ( jComboBox7.getSelectedIndex () ).FGQty , Integer.parseInt ( jTextField5.getText () ) , batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID , inQty *convertRate , inQty ,  new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00");    
                            if(  RMStockUpdatedOnfirstProcess   ){
                                updateRMStockCounter ( batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID );
                            }
                        }
                        
                    }else {
                        JOptionPane.showMessageDialog(  null, "Please select unit for RM / Part being processed "    );
                    }
                    
                    ////////////////////////////////////////////////    END       -     Code to reduce raw material according to parts created and added to FG Stock   -  END          //////////////////////////////////////////////////////////////////////////////  
                }else if ( verifyFirstOrOnlyProcess ( productId , processId ).equals( "onlyProcess" ) )                 {

                    String updateStock = "fgstockadd?FG_ID=" + productId + "&FG_ITEM_ID=" + productId + "&OPENING=" + OPENING + "&RECEIVED=" + RECEIVED + "&USED=" + USED + "&CLOSING=" + CLOSING + "&CREATED_ON=" + URLEncoder.encode ( new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00" , "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00" , "UTF-8" ) + "&EDITED_BY=" + Proman_User.getEID () + "&SALE_RATE=" +  products.get( jComboBox1.getSelectedIndex () ).getRate() ;
                    result2 = WebAPITester.prepareWebCall ( updateStock , StaticValues.getHeader () , "" );
                    JOptionPane.showMessageDialog ( null , result2 );
                    
                ////////////////////////////////////////////////    START   -     Code to reduce raw material according to parts created and added to FG Stock   -  START //////////////////////////////////////////////////////////////////////////////                                    
                    BatchProcessing batch = new BatchProcessing ();
                    
                    if( UOMIN.getItemCount() > 0 )
                    {
                        if(   UOMIN.getSelectedItem().toString().equals ( "NOs.") )
                        {
                            RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( 
							batches.get ( jComboBox7.getSelectedIndex () ).RMID , 
							batches.get ( jComboBox7.getSelectedIndex () ).FGQty , 
							Integer.parseInt ( jTextField5.getText () ) ,
							batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID ,
							inQty *convertRate , 
							inQty ,
                                                                                                                new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00");
                            if(  RMStockUpdatedOnfirstProcess   )
							{
                                updateRMStockCounter ( batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID );
                            }
                        }else if(   UOMIN.getSelectedItem().toString().equals ( "KG") )
						{
                            if(convertRate>0.0)
                                RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( batches.get ( jComboBox7.getSelectedIndex () ).RMID , batches.get ( jComboBox7.getSelectedIndex () ).FGQty , Integer.parseInt ( jTextField5.getText () ) , batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID , inQty*1.0  , (int)(inQty / convertRate) ,  new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00" );
                            else
                                RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( batches.get ( jComboBox7.getSelectedIndex () ).RMID , batches.get ( jComboBox7.getSelectedIndex () ).FGQty , Integer.parseInt ( jTextField5.getText () ) , batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID , inQty*1.0  , inQty  ,  new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00");

                            if(  RMStockUpdatedOnfirstProcess   )
							{
                                updateRMStockCounter ( batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID );
                            }

                        }else
						{
                            RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( batches.get ( jComboBox7.getSelectedIndex () ).RMID , batches.get ( jComboBox7.getSelectedIndex () ).FGQty , Integer.parseInt ( jTextField5.getText () ) , batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID , inQty *convertRate , inQty ,  new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00");    
                            if(  RMStockUpdatedOnfirstProcess   ){
                                updateRMStockCounter ( batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID );
                            }
                        }
                        
                    }else {
                        JOptionPane.showMessageDialog(  null, "Please select unit for RM / Part being processed "    );
                    }
                    
                ////////////////////////////////////////////////    END       -     Code to reduce raw material according to parts created and added to FG Stock   -  END          //////////////////////////////////////////////////////////////////////////////  
                }

            } else {

                OPENING = 0;

                RECEIVED = Integer.parseInt ( jTextField5.getText () ) - Integer.parseInt ( jTextField7.getText () );
                USED = 0;
                CLOSING = OPENING + RECEIVED;

                //if ( verifyLastProcess ( productId , processId ) ) {
                if ( verifyFirstOrOnlyProcess ( productId , processId ).equals( "lastProcess" )  ){
                    addEmpAPICall = "fgstockadd?FG_ID=" + productId + "&FG_ITEM_ID=" + productId + "&OPENING=" + OPENING + "&RECEIVED=" + RECEIVED + "&USED=" + USED + "&CLOSING=" + CLOSING + "&CREATED_ON=" + URLEncoder.encode ( new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00" , "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00" , "UTF-8" ) + "&EDITED_BY=" + Proman_User.getEID () + "&SALE_RATE=" + products.get ( jComboBox1.getSelectedIndex () ).getRate () ;
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    JOptionPane.showMessageDialog ( null , result2 );
                }else if ( verifyFirstOrOnlyProcess ( productId , processId ).equals( "firstProcess" ) ) 
                {

                   //    New code for updating rmstock and batch replace line no 583
////////////////////////////////////////////////    START   -     Code to reduce raw material according to parts created and added to FG Stock   -  START //////////////////////////////////////////////////////////////////////////////                                    
                    BatchProcessing batch = new BatchProcessing ();
                    
                          if( UOMIN.getItemCount() > 0 )
                          {
                         if(   UOMIN.getSelectedItem().toString().equals ( "NOs.") )
                         {
                            RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( batches.get ( jComboBox7.getSelectedIndex () ).RMID , batches.get ( jComboBox7.getSelectedIndex () ).FGQty , Integer.parseInt ( jTextField5.getText () ) , batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID , inQty *convertRate , inQty , new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00");
                            if(  RMStockUpdatedOnfirstProcess   ){
                                updateRMStockCounter ( batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID );
                            }
                        }else if(   UOMIN.getSelectedItem().toString().equals ( "KG") )
                        {
                            if(convertRate>0.0)
                                RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( batches.get ( jComboBox7.getSelectedIndex () ).RMID , batches.get ( jComboBox7.getSelectedIndex () ).FGQty , Integer.parseInt ( jTextField5.getText () ) , batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID , inQty*1.0  ,  (int)(inQty / convertRate)  ,  new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00");
                            else
                                RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( batches.get ( jComboBox7.getSelectedIndex () ).RMID , batches.get ( jComboBox7.getSelectedIndex () ).FGQty , Integer.parseInt ( jTextField5.getText () ) , batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID , inQty*1.0  , inQty  ,  new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00");

                            if(  RMStockUpdatedOnfirstProcess   )
                            {
                                updateRMStockCounter ( batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID );
                            }

                        }else{
                            RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( batches.get ( jComboBox7.getSelectedIndex () ).RMID , batches.get ( jComboBox7.getSelectedIndex () ).FGQty , Integer.parseInt ( jTextField5.getText () ) , batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID , inQty *convertRate , inQty ,  new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00");    
                            if(  RMStockUpdatedOnfirstProcess   ){
                                updateRMStockCounter ( batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID );
                            }
                        }
                        
                    }else {
                        JOptionPane.showMessageDialog(  null, "Please select unit for RM / Part being processed "    );
                    }

                    
                    int FGID=products.get(jComboBox1.getSelectedIndex()).FG_ID;
                    int RMID=batches.get(jComboBox7.getSelectedIndex()).RMID;
                    ////////////////////////////////////////////////    END       -     Code to reduce raw material according to parts created and added to FG Stock   -  END          //////////////////////////////////////////////////////////////////////////////  
                } else if ( verifyFirstOrOnlyProcess ( productId , processId ).equals ( "onlyProcess" ) ) {

                    String updateStock = "fgstockadd?FG_ID=" + productId + "&FG_ITEM_ID=" + productId + "&OPENING=" + OPENING + "&RECEIVED=" + RECEIVED + "&USED=" + USED + "&CLOSING=" + CLOSING + "&CREATED_ON=" + URLEncoder.encode ( new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime () ) + " 00:00:00" , "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime () ) + " 00:00:00" , "UTF-8" ) + "&EDITED_BY=" + Proman_User.getEID () + "&SALE_RATE=" + products.get ( jComboBox1.getSelectedIndex () ).getRate ();
                    result2 = WebAPITester.prepareWebCall ( updateStock , StaticValues.getHeader () , "" );
                    JOptionPane.showMessageDialog ( null , result2 );

                    ////////////////////////////////////////////////    START   -     Code to reduce raw material according to parts created and added to FG Stock   -  START //////////////////////////////////////////////////////////////////////////////                                    
                    BatchProcessing batch = new BatchProcessing ();
                    
                          if( UOMIN.getItemCount() > 0 )
                          {
                         if(   UOMIN.getSelectedItem().toString().equals ( "NOs.") )
                         {
                            RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( batches.get ( jComboBox7.getSelectedIndex () ).RMID , batches.get ( jComboBox7.getSelectedIndex () ).FGQty , Integer.parseInt ( jTextField5.getText () ) , batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID , inQty *convertRate , inQty ,  new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00");
                            if(  RMStockUpdatedOnfirstProcess   ){
                                updateRMStockCounter ( batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID );
                            }
                        }else if(   UOMIN.getSelectedItem().toString().equals ( "KG") )
                        {
                            if(convertRate>0.0)
                                RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( batches.get ( jComboBox7.getSelectedIndex () ).RMID , batches.get ( jComboBox7.getSelectedIndex () ).FGQty , Integer.parseInt ( jTextField5.getText () ) , batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID , inQty*1.0  ,  (int)(inQty / convertRate)  ,  new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00");
                            else
                                RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( batches.get ( jComboBox7.getSelectedIndex () ).RMID , batches.get ( jComboBox7.getSelectedIndex () ).FGQty , Integer.parseInt ( jTextField5.getText () ) , batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID , inQty*1.0  , inQty  ,  new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00");

                            if(  RMStockUpdatedOnfirstProcess   )
                            {
                                updateRMStockCounter ( batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID );
                            }

                        }else{
                            RMStockUpdatedOnfirstProcess = batch.insertIntoRMStock ( batches.get ( jComboBox7.getSelectedIndex () ).RMID , batches.get ( jComboBox7.getSelectedIndex () ).FGQty , Integer.parseInt ( jTextField5.getText () ) , batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID , inQty *convertRate , inQty , new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00");    
                            if(  RMStockUpdatedOnfirstProcess   ){
                                updateRMStockCounter ( batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID );
                            }
                        }
                        
                    }else {
                        JOptionPane.showMessageDialog(  null, "Please select unit for RM / Part being processed "    );
                    }

                    ////////////////////////////////////////////////    END       -     Code to reduce raw material according to parts created and added to FG Stock   -  END          //////////////////////////////////////////////////////////////////////////////  
                }

            }
        } catch ( UnsupportedEncodingException e ) {
            System.out.println ( "" + e.getMessage () );
        }

    }
    
    public static int getUnitFrom(int RM_ID,int FG_ID) throws Exception
    {
            String total_wt=null;
            String addEmpAPI = "billofmaterials?FG_ID=" + FG_ID+"&RM_ID="+RM_ID;
            String  result = WebAPITester.prepareWebCall ( addEmpAPI , StaticValues.getHeader () , "" );
            HashMap<String , Object> map1 = new HashMap<String , Object> ();
            JSONObject  jObject1 = new JSONObject ( result );
            Iterator<?> keys = jObject1.keys ();

        
            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject1.get ( key );
                map1.put ( key , value );
            }
            
            JSONObject st = ( JSONObject ) map1.get ( "data" );
            JSONArray records = st.getJSONArray ( "records" );
           JSONObject emp = null;
            
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );
                
                total_wt=emp.get ( "TOTAL_WT" ).toString ();
            }
            
            int no=Integer.parseInt(total_wt);
            return no;
    }
    
    public static int getUnitNumber(int RM_ID,int FG_ID)
    {
        String total_wt=null;
        String addEmpAPI = "billofmaterials?FG_ID=" + FG_ID+"&RM_ID="+RM_ID;
        String  result = WebAPITester.prepareWebCall ( addEmpAPI , StaticValues.getHeader () , "" );
        HashMap<String , Object> map1 = new HashMap<String , Object> ();
        JSONObject  jObject1 = new JSONObject ( result );
        Iterator<?> keys = jObject1.keys ();

        
            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject1.get ( key );
                map1.put ( key , value );
            }
            
            JSONObject st = ( JSONObject ) map1.get ( "data" );
            JSONArray records = st.getJSONArray ( "records" );
           JSONObject emp = null;
            
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );
                
                total_wt=emp.get ( "RMM_UOM_ID" ).toString ();
            }
            
            int no=Integer.parseInt(total_wt);
            return no;
    }
    
    public void updateRMStockCounter ( int btcid ) {

        
//                addEmpAPICall = "batchesedit?BatchId="+ btcid     ;
//                result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
//                records = new JSONObject(result2).getJSONArray ( "data" );
//
//                try{
//                    Integer presentStock =   records.getJSONObject ( 0 ).getInt ( "BatchQty" ) ;
//                    jLabel28.setText(   ""+ ( presentStock / batches.get( j).FGQty  )  ) ;
//                }catch( NumberFormatException e ){
//                    Double presentStock =   records.getJSONObject ( 0 ).getDouble ( "BatchQty" ) ;
//                    jLabel28.setText(   ""+ ( presentStock / batches.get( j).FGQty  )  ) ;
//                }
    //  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------                   
             //addEmpAPICall = "latestrwamstock?rmid="+rmid;
                addEmpAPICall = "batches?BatchId=" + btcid;
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
                        
                        double stockVal2  = 0.0;
                        stockVal2   = Integer.parseInt(  emp.get ( "BatchQty" ).toString()  ) ;
                        
                        if(  stockVal2 !=0.0  ){
                            try {
                                for ( int j = 0 ; j < batches.size() ; j ++ ) {
                                    if( Integer.parseInt(  emp.get ( "BatchId" ).toString()) ==  btcid ){

                                        batches.get( j).availbleQty = emp.get ( "BatchQty" ).toString() ;
                                        jLabel26.setText(   new DecimalFormat("#.##").format(     stockVal2 * 1.0 )  ) ;
                                        
                                        jLabel28.setText(    new DecimalFormat("#.##").format(  ( stockVal2 * batches.get( j).FGQty  ) * 1.0 )  ) ;
                                        j = batches.size() ;
                                    }
                                }
                            } catch ( Exception e ) {
                                System.out.println ( ""+e.getMessage() );
                                JOptionPane.showMessageDialog(  null , ""+e.getMessage()    ) ;
                            }
                        }
                    }
                } catch ( JSONException e ) {
                    System.out.println ( ""+e.getMessage() );
                }
    }

    public void loadComboBoxes_OLD () {

        String queryOne = "SELECT FG_ID, PART_NAME FROM finished_goods";
        String queryTwo = "SELECT EmployeePK, EMP_NAME FROM employee";
        String queryThree = "SELECT MCH_ID, MACHINE_NO FROM machine";
        String queryFour = "SELECT PROCESS_ID, PROCESS_NAME FROM PROCESS_MASTER";
        String queryFive = "SELECT RR_ID, RR_DESC FROM rejection_reasons";

        String querySix = "SELECT shiftid, shifttitle FROM shifts";

        String queryBatch = "SELECT BatchId, BatchName, BatchQty FROM BATCH_MASTER";

        ResultSet rs = null;

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

            rs.close ();
        } catch ( Exception e ) {
            System.out.println ( " Error 1 " + e.getMessage () );
            StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } finally {
            try {
                rs.close ();
            } catch ( Exception e ) {
                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                jComboBox3.addItem ( rs.getString ( 2 ) );
            }

        } catch ( Exception e ) {
            System.out.println ( " Error 2 " + e.getMessage () );
            StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } finally {
            try {
                rs.close ();
            } catch ( Exception e ) {
                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }

        try {
            rs = DB_Operations.executeSingle ( queryThree );
            machines = new ArrayList<MachineDR> ();

            while ( rs.next () ) {
                mcdr = new MachineDR ();

                mcdr.MC_ID = Integer.parseInt ( rs.getString ( 1 ) );
                mcdr.MC_NAME = rs.getString ( 2 );
                machines.add ( mcdr );
                jComboBox2.addItem ( rs.getString ( 2 ) );
            }

        } catch ( Exception e ) {
            System.out.println ( " Error 2 " + e.getMessage () );
            StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } finally {
            try {
                rs.close ();
            } catch ( Exception e ) {
                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }

        try {
            rs = DB_Operations.executeSingle ( queryFour );
            processes = new ArrayList<ProcessDR> ();

            while ( rs.next () ) {
                prcdr = new ProcessDR ();

                prcdr.PRC_ID = Integer.parseInt ( rs.getString ( 1 ) );
                prcdr.PRC_NAME = rs.getString ( 2 );
                processes.add ( prcdr );
                jComboBox4.addItem ( rs.getString ( 2 ) );
            }

        } catch ( Exception e ) {
            System.out.println ( " Error 2 " + e.getMessage () );
            StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } finally {
            try {
                rs.close ();
            } catch ( Exception e ) {
                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }

        try {
            rs = DB_Operations.executeSingle ( queryFive );
            // employees = new ArrayList<HashMap<String, String>> ();

            rej_reasons = new ArrayList<Rejection_Reasons> ();
            while ( rs.next () ) {
                rejrsn = new Rejection_Reasons ();

                rejrsn.REJ_ID = Integer.parseInt ( rs.getString ( 1 ) );
                rejrsn.REJ_DESC = rs.getString ( 2 );
                rej_reasons.add ( rejrsn );
                jComboBox5.addItem ( rs.getString ( 2 ) );
            }
        } catch ( Exception e ) {
            System.out.println ( " Error 2 " + e.getMessage () );
            StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } finally {
            try {
                rs.close ();
            } catch ( Exception e ) {
                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }

//        try {
//            rs = DB_Operations.executeSingle ( queryBatch );
//            //  products = new ArrayList<HashMap<String, String>> ();
//            
//            while ( rs.next () ) {
//                //   entity =  new  HashMap<String, String>();
//                batch = new Batch ();
//                //    entity.put ( rs.getString(1) /*  String.valueOf( rs.getInt(1) ) */, rs.getString(2)) ;
//                batch.setBatchId (  Integer.parseInt ( rs.getString ( 1 ) ) );
//                batch.setBatchName (  rs.getString ( 2 ) );
//                batch.setBatchQty ( Double.parseDouble( rs.getString ( 3 ) ) );
//                batches.add ( batch );
//                jComboBox6.addItem ( rs.getString ( 2 ) );
//            }
//
//            rs.close ();
//        } catch ( Exception e ) {
//            System.out.println ( " Error 1 " + e.getMessage () );
//        }
        try {
            rs = DB_Operations.executeSingle ( querySix );
            shifts = new ArrayList<ShiftDR> ();
            int i = 0;
            while ( rs.next () ) {
                shift = new ShiftDR ();
                shift.SHIFT_ID = Integer.parseInt ( rs.getString ( 1 ) );
                shift.SHIFT_NAME = rs.getString ( 2 );
                shifts.add ( shift );

                switch ( i ) {
                    case 0:
                        jRadioButton1.setText ( shift.SHIFT_NAME );
                        jRadioButton1.setEnabled ( true );
                        break;
                    case 1:
                        jRadioButton2.setText ( shift.SHIFT_NAME );
                        jRadioButton2.setEnabled ( true );
                        break;
                    case 2:
                        jRadioButton3.setText ( shift.SHIFT_NAME );
                        jRadioButton3.setEnabled ( true );
                        break;
                    case 3:
                        jRadioButton4.setText ( shift.SHIFT_NAME );
                        jRadioButton4.setEnabled ( true );
                        break;
                }
                i ++;
            }
        } catch ( Exception e ) {
            System.out.println ( " Error 2 " + e.getMessage () );
            StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } finally {
            try {
                rs.close ();
            } catch ( Exception e ) {
                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }
    }

    String addEmpAPICall, result2;

    public void loadComboBoxes () {

        // PRODUCT COMBO         
            addEmpAPICall = "unitmeas";
            result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

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
                    
                    String unitName=uom.get("UOM").toString();
                    UOM.add(new UOMDR(Integer.parseInt(uom.get("UOM_ID").toString()), unitName));
                }
            }            
        
        if ( jComboBox1.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            addEmpAPICall = "finishedgoods";
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            if (  ! result2.contains ( "not found" ) ) {

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
                for ( int i = 0 ; i < records.length () ; i ++ ) 
                {

                    emp = records.getJSONObject ( i );
                    jComboBox1.addItem ( emp.get ( "FG_PART_NO" ).toString () );
                    //System.out.println("UOMMMM"+emp.get ( "FG_UOM_ID" ).toString ());
                    products.add ( new ProductDR ( Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) , emp.get ( "PART_NAME" ).toString () , emp.get ( "FG_UOM_ID" ).toString ()  ,  Double.parseDouble(emp.get ( "SALES_RATE" ).toString () ) , Integer.parseInt ( emp.get ( "ASSEMBLED" ).toString () ) ) );
                }
                
                jComboBox1.addItem ( "-- Select Part --"  );
                
                jComboBox1.setSelectedIndex ( jComboBox1.getItemCount()-1  );
                products.add ( new ProductDR ( Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) , emp.get ( "PART_NAME" ).toString () , emp.get ( "FG_UOM_ID" ).toString () ,  Double.parseDouble(emp.get ( "SALES_RATE" ).toString () ) , Integer.parseInt ( emp.get ( "ASSEMBLED" ).toString () )  ) );
                jComboBox1.addActionListener ( productComboAction );
            }
        }

        loadUnits();
        // EMPLOYEE COMBO
        if ( jComboBox3.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            addEmpAPICall = "employees";
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            if (  ! result2.contains ( "not found" ) ) {

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
                    jComboBox3.addItem ( emp.get ( "EMP_NAME" ).toString () );
                    employees.add ( new EmployeeDR ( Integer.parseInt ( emp.get ( "EmployeePK" ).toString () ) , emp.get ( "EMP_NAME" ).toString () ) );
                }
            }
        }


        // PROCESS COMBO
         jComboBox4.removeActionListener(    loadMachinesProcessesWise      ) ;
        if ( jComboBox4.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            //addEmpAPICall = "processes";
            addEmpAPICall = "processmachmaps?FG_ID=" + products.get ( jComboBox1.getSelectedIndex () ).FG_ID;
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

            if (  ! result2.contains ( "not found" ) ) {
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
                processes = new ArrayList<ProcessDR> ();
                for ( int i = 0 ; i < records.length () ; i ++ ) {

                    emp = records.getJSONObject ( i );
                    //     jComboBox4.addItem ( emp.get ( "PROCESS_NAME" ).toString () );
                    //     processes.add( new ProcessDR ( Integer.parseInt(emp.get ( "PROCESS_ID" ).toString ()), emp.get ( "PROCESS_NAME" ).toString ()   )); 
                    jComboBox4.addItem ( emp.get ( "PROCESS_NAME" ).toString () );
                    processes.add ( new ProcessDR ( Integer.parseInt ( emp.get ( "REF_PROCESS_ID" ).toString () ) , emp.get ( "PROCESS_NAME" ).toString () , Integer.parseInt( emp.get ( "REF_MCH_ID" ).toString () ) ) );
                }
            }
        }
        
        if( processes!=null &&  processes.size() > 0 && jComboBox2.getItemCount () == 0){
            jComboBox4.addActionListener( loadMachinesProcessesWise );
        
            // MACHINE COMBO
            //result = DB_Operations.getMasters ( "raw_material_type" );
            //addEmpAPICall = "machines";
            //result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            
            int selectedProcessIndex =  jComboBox4.getSelectedIndex() ;
            int machineType = processes.get( selectedProcessIndex ).machine_type ;
            //JOptionPane.showMessageDialog( null, "selected machine type is "+machineType );
            addEmpAPICall = "machines?MACHINE_TYPE=" + machineType;
            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

            if (  ! result2.contains ( "not found" ) ) {
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
                machines = new ArrayList<MachineDR> ();
                for ( int i = 0 ; i < records.length () ; i ++ ) {

                    emp = records.getJSONObject ( i );
                    jComboBox2.addItem ( emp.get ( "MACHINE_NO" ).toString () );
                    machines.add ( new MachineDR ( Integer.parseInt ( emp.get ( "MCH_ID" ).toString () ) , emp.get ( "MACHINE_NO" ).toString () ) );
                }
            }
        }
        
        
        

        //   BATCH SELECTION COMBO
//        if ( jComboBox7.getItemCount () == 0 ) {
//                    //result = DB_Operations.getMasters ( "raw_material_type" );
//                   
//                    addEmpAPICall = "batches";
//                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
//            if( ! result2.contains(   "not found" )  ){
//                    
//                    HashMap<String, Object> map = new HashMap<String, Object>();
//                    JSONObject jObject = new JSONObject( result2 );
//                    Iterator<?> keys = jObject.keys();
//
//                    while( keys.hasNext() ){
//                        String key = (String)keys.next();
//                        Object value = jObject.get ( key ) ; 
//                        map.put(key, value);
//                    }
//                    
//                    JSONObject st = (JSONObject) map.get ( "data" );
//                    JSONArray records = st.getJSONArray ( "records");
//
//                    JSONObject emp = null;
//                    batches = new ArrayList<RMBatchDR> ();
//                    for ( int i = 0 ; i < records.length () ; i ++ ) {
//                    
//                        emp = records.getJSONObject ( i);
//                        jComboBox7.addItem ( emp.get ( "BatchName" ).toString () );
//                        batches.add( new RMBatchDR ( Integer.parseInt(emp.get ( "BatchId" ).toString ()), emp.get ( "BatchName" ).toString ()   )); 
//                    }
//                }
//        }
        //////////////////////////////////////////////          Start    -  Load batches from batch master for selection   -    Start         ///////////////////////////////////////////////////////////       
//        if ( jComboBox7.getItemCount () == 0 ) {
//                    //result = DB_Operations.getMasters ( "raw_material_type" );
//                   
//                    addEmpAPICall = "rmstocks";
//                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
//                    HashMap<String, Object> map = new HashMap<String, Object>();
//                    JSONObject jObject = new JSONObject( result2 );
//                    Iterator<?> keys = jObject.keys();
//
//                    while( keys.hasNext() ){
//                        String key = (String)keys.next();
//                        Object value = jObject.get ( key ) ; 
//                        map.put(key, value);
//                    }
//                    
//                    JSONObject st = (JSONObject) map.get ( "data" );
//                    JSONArray records = st.getJSONArray ( "records");
//
//                    JSONObject emp = null;
//                    batches = new ArrayList<RMBatchDR> ();
//                    for ( int i = 0 ; i < records.length () ; i ++ ) {
//                    
//                        emp = records.getJSONObject ( i);
//                        if(  emp.get ( "inward_batch_no" ).toString ().equals ( "null")  ){
//                            jComboBox7.addItem ( emp.get ( "inward_batch_no" ).toString () +" ( "+emp.get ( "RM_CODE" ).toString ()+" )" );
//                            batches.add( new RMBatchDR ( Integer.parseInt(emp.get ( "RMStock_TR_ID" ).toString ()),  Integer.parseInt(emp.get ( "RMStock_TR_ID" ).toString ()),   emp.get ( "inward_batch_no" ).toString ()    )); 
//                        }
//                        
//                    }
//                }
//////////////////////////////////////////////          End    -  Load batches from batch master for selection   -    End         /////////////////////////////////////////////////////////// 
        //   ORDER SELECTION COMBO
        if ( jComboBox6.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            addEmpAPICall = "salesorders";
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            if (  ! result2.contains ( "not found" ) ) {

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
                    jComboBox6.addItem ( emp.get ( "order_no" ).toString () );
                    orders.add ( new POrderDR ( Integer.parseInt ( emp.get ( "sales_po_id" ).toString () ) , emp.get ( "order_no" ).toString () , Integer.parseInt ( emp.get ( "so_customer_id" ).toString () ) ) );
                }
            }
        }

        // REJECTION REASON
        // BATCH SELECTION
        // SHIFTS SELECTION
    }

    ActionListener productComboAction = new ActionListener () 
    {
        @Override
        public void actionPerformed ( ActionEvent e ) {
          //  System.out.println("UOM");  products.get(jComboBox1.getSelectedIndex() ).UOM);
          
        int flag = products.get( jComboBox1.getSelectedIndex() ).getAssembled () ;
          
        if(   flag==1  ){
              childParts() ;
              jComboBox7.removeAllItems();
              jComboBox7.setEnabled(  false   ) ;
              
              loadDefaultMap_assembled(  products.get(jComboBox1.getSelectedIndex()).FG_ID + ""  ) ;
        }else{
              
            jComboBox7.setEnabled(true);
            loadDefaultMap(products.get(jComboBox1.getSelectedIndex()).FG_ID + "");
        }
        //  loadDefaultMap ( products.get ( jComboBox1.getSelectedIndex () ).FG_ID + "" );
            
        loadUnits();
        //System.err.println(products.get(jC));
        }
    };
    
    ActionListener loadUnitsForSelectedProcess = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadUnits();
        }
    };
    
    // changes in this method
    public void loadUnits()
    {
            int uom = Integer.parseInt(products.get(jComboBox1.getSelectedIndex()).UOM);
            String units = "";
            for (int i = 0; i < UOM.size(); i++) {
                if (uom == UOM.get(i).UOM_ID) {
                    units = UOM.get(i).UOM_NAME;
                }   
            }
            try{
            int FGID=products.get(jComboBox1.getSelectedIndex()).FG_ID;
            if(verifyFirstOrOnlyProcess (FGID , processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID ).equals( "firstProcess" ) || verifyFirstOrOnlyProcess (FGID , processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID ).equals( "onlyProcess" ))
            {
                try{
                    int RMID=batches.get(jComboBox7.getSelectedIndex()).RMID;
                    int unit=getUnitNumber(RMID, FGID);
                    String rmunit="";
                    for (int i = 0; i < UOM.size(); i++) {
                        if (unit == UOM.get(i).UOM_ID) {
                            rmunit = UOM.get(i).UOM_NAME;
                        }
                    }
                    UOMIN.removeAllItems();
                    UOMOUT.removeAllItems();
                    UOMOUT1.removeAllItems();
            
                    //UOMIN.addItem(rmunit);
                    UOMIN.addItem ( "NOs." );    
                    UOMIN.addItem ( "KG" );
                        
                    
                    UOMOUT.addItem(units);
                    UOMOUT1.addItem(units);
                }catch(Exception ex){
                UOMIN.removeAllItems ();
                UOMIN.addItem ( "NOs." );        
                UOMIN.addItem ( "KG" );
                        
                }
        }else{
            UOMIN.removeAllItems();
            UOMOUT.removeAllItems();
            UOMOUT1.removeAllItems();

            UOMIN.addItem(units);
            UOMOUT.addItem(units);
            UOMOUT1.addItem(units);
        }
        }catch(Exception ex){
                        UOMIN.removeAllItems();
            UOMOUT.removeAllItems();
            UOMOUT1.removeAllItems();

            UOMIN.addItem(units);
            UOMOUT.addItem(units);
            UOMOUT1.addItem(units);

        }
            
    }
    
    public void loadDefaultMap ( String selectedFGID )
    {

        jComboBox4.removeActionListener(    loadMachinesProcessesWise      ) ;
        jComboBox4.removeAllItems ();
        addEmpAPICall = "processmachmaps?FG_ID=" + selectedFGID;
        String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
        if ( result2 != null &&  ! result2.contains ( "not found" ) ) 
        {
            addEmpAPICall = "processmachmaps?FG_ID=" + products.get ( jComboBox1.getSelectedIndex () ).FG_ID;
            if (  ! result2.contains ( "not found" ) ) {
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
                processes = new ArrayList<ProcessDR> ();
                for ( int i = 0 ; i < records.length () ; i ++ ) {
                    emp = records.getJSONObject ( i );
                    jComboBox4.addItem ( emp.get ( "PROCESS_NAME" ).toString () );
                    processes.add ( new ProcessDR ( Integer.parseInt ( emp.get ( "REF_PROCESS_ID" ).toString () ) , emp.get ( "PROCESS_NAME" ).toString () , Integer.parseInt( emp.get( "REF_MCH_ID" ).toString() ) ) );
                }
            }
        }
        
        wip_double = check_If_WIP_Exists(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID) * 1.0 ;
        wip = (int) wip_double ;
        
        if( processes!=null &&  processes.size()>0){
            jComboBox4.addActionListener(    loadMachinesProcessesWise      ) ;
        }else{
            jComboBox4.removeActionListener(    loadMachinesProcessesWise      ) ;
        }
        

        if(  jComboBox4.getItemCount () == 0  ){
            
            JOptionPane.showMessageDialog( null,  "<html>No processes are mapped for selected part. Please create process map for this part to make entries for production</html>"   );
            jButton1.setEnabled ( false);
        }else{
            jButton1.setEnabled ( true );
        }
        
//////////////////////////////////////////////          Start    -  Load batches from batch master for selection   -    Start         ///////////////////////////////////////////////////////////       
        jComboBox7.removeAllItems ();
        if ( jComboBox7.getItemCount () == 0 ) 
        {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            addEmpAPICall = "billofmaterials?FG_ID=" + selectedFGID;
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            
            if( ! result2.contains( "\"records\":null" )  ){
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
                    for ( int i = 0 ; i < records.length () ; i ++ ) 
                    {
                        emp = records.getJSONObject ( i );
                        rmids.add ( new String[] { emp.get ( "RM_ID" ).toString () , emp.get ( "TOTAL_WT" ).toString () } );
                    }

                    batches = new ArrayList<RMBatchDR> ();
                    for ( int j = 0 ; j < rmids.size () ; j ++ ) 
                    {

                        //addEmpAPICall = "latestrwamstock?rmid="+rmids.get(j)[0];
                       String  addEmpAPICall1 = "batches?rm_id=" + rmids.get ( j )[ 0 ];
                        String batchResult = WebAPITester.prepareWebCall ( addEmpAPICall1 , StaticValues.getHeader () , "" );
                        HashMap<String , Object> map1 = new HashMap<String , Object> ();
                        JSONObject  jObject1 = new JSONObject ( batchResult );
                        Iterator<?> keys1 = jObject1.keys ();

                        while ( keys1.hasNext () ) {
                            String key = ( String ) keys1.next ();
                            Object value = jObject1.get ( key );
                            map1.put ( key , value );
                        }

                        try {
                            JSONObject  st1 = ( JSONObject ) map1.get ( "data" );
                            JSONArray records1 = st1.getJSONArray ( "records" );
                            JSONObject emp1 = null;
                            for ( int i = 0 ; i < records1.length () ; i ++ ) 
                            {
                                emp1 = records1.getJSONObject ( i );

                                int stockVal = 0;
                                double stockVal2 = 0.0;
                                try 
                                {
                                    stockVal = Integer.parseInt ( emp1.get ( "BatchQty" ).toString () );
                                } catch ( NumberFormatException e ) 
                                {
                                    stockVal2 = Double.parseDouble ( emp1.get ( "BatchQty" ).toString () );
                                }

                                if (  ! emp1.get ( "BatchId" ).toString ().equals ( "null" ) ) {

                                    if ( stockVal != 0 || stockVal2 != 0.0 ) {
                                     //   int flag = emp1.getInt ( "quality_flag" );
                                     //   if ( flag == 1 ) {
                                            jComboBox7.addItem ( emp1.get ( "BatchName" ).toString () );
                                            try {

                                                //New code here - replace line no 1386
                                                //new code for conversionFactor variable - need to insert
                                                //batches.add( new RMBatchDR ( Integer.parseInt(emp.get ( "RMStock_TR_ID" ).toString ()),  Integer.parseInt(emp.get ( "RMI_ID" ).toString ()),   emp.get ( "inward_batch_no" ).toString () ,  Integer.parseInt( rmids.get(j)[1] ),  emp.get( "CLOSING").toString() , emp.get ( "RM_CODE" ).toString () )); 
                                                //batches.add ( new RMBatchDR ( Integer.parseInt ( emp.get ( "BatchId" ).toString () ) , emp.get ( "BatchName" ).toString () , Integer.parseInt ( emp.get ( "ref_rm_id" ).toString () ) , Integer.parseInt ( rmids.get ( j )[ 1 ] ) , emp.get ( "BatchQty" ).toString () ) );
                                                try {
                                                    conversionFactor = Integer.parseInt ( rmids.get ( j )[ 1 ] );
                                                } catch ( NumberFormatException ee ) {
                                                    conversionFactor = ( int ) Double.parseDouble ( rmids.get ( j )[ 1 ] );
                                                }

                                                batches.add ( new RMBatchDR ( Integer.parseInt ( emp1.get ( "BatchId" ).toString () ) ,
                                                        emp1.get ( "BatchName" ).toString () ,
                                                        Integer.parseInt ( emp1.get ( "ref_rm_id" ).toString () ) ,
                                                        conversionFactor ,
                                                        emp1.get ( "BatchQty" ).toString () ,
                                                        Double.parseDouble ( emp1.get ( "conversion_value" ).toString () ) ,
                                                        Integer.parseInt ( emp1.get ( "BatchQty_CNT" ).toString () ) ) );
                                            } catch ( Exception e ) {
                                                System.out.println ( "" + e.getMessage () );
                                            }
                                        //}
                                    }
                                }
                            }
                        } catch ( JSONException e ) {
                            System.out.println ( "" + e.getMessage () );
                        }
                    }

                    // new Code here - replace entire if condition
                    
                    if ( jComboBox7.getItemCount () > 0 ) 
                    {
                        jComboBox7.addActionListener ( selectBatch );
                        double availbleQty;
                        int availableCount ;
                        int FGQty, FGQtyCnt;
                        try{
                            if(  batches.size () >0 ){
                                RMBatchDR selectedBtch = batches.get ( jComboBox7.getSelectedIndex () );
                                availbleQty = Double.parseDouble ( selectedBtch.availbleQty );
                                availableCount =  selectedBtch.FGQtyCnt ;
                                FGQty = selectedBtch.FGQty;
                                FGQtyCnt = selectedBtch.FGQtyCnt;

                                convertRate = selectedBtch._convertRate ;
                                conversionFactor = selectedBtch.FGQty ;
                                
                                jLabel26.setText ( String.valueOf ( availbleQty ) +"( "+FGQtyCnt+" )" );
                                //jLabel26.setText ( String.valueOf ( availbleQty ) );
                                
                                if(   UOMIN.getSelectedItem().toString().equals ( "NOs.") )
                                {
                                    jLabel28.setText ( String.valueOf ( availbleQty * conversionFactor ) );
                                }else{
                                    jLabel28.setText ( String.valueOf ( FGQtyCnt * conversionFactor ) );
                                }
                            }
                        }catch(  Exception er ){
                                JOptionPane.showMessageDialog(      null, "Error from line no 1422 "+ er.getMessage ()   ) ; 
                        }
                    }
            }
        }
        
    /*    if(  jComboBox7.getItemCount () == 0  ){
            
            JOptionPane.showMessageDialog( null,  "<html>No raw material batches found required for this part. Please update raw material stock and batch details</html>"   );
            jButton1.setEnabled ( false);
        }else{
            jButton1.setEnabled ( true );
        }*/
        
        
//////////////////////////////////////////////          End    -  Load batches from batch master for selection   -    End         /////////////////////////////////////////////////////////// 
    }

    
    
    
    
    
    ActionListener loadMachinesProcessesWise = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
            int selectedProcessIndex =  jComboBox4.getSelectedIndex() ;
            int machineType = processes.get( selectedProcessIndex ).machine_type ;
            //JOptionPane.showMessageDialog( null, "selected machine type is "+machineType );
            
            wip_double = check_If_WIP_Exists(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID) * 1.0 ;
            wip = (int) wip_double ;
            
            jComboBox2.removeAllItems() ;
            
            addEmpAPICall = "machines?MACHINE_TYPE=" + machineType;
            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

            if ( result2 != null &&  ! result2.contains ( "not found" ) ) 
            {
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

                machines = new ArrayList<MachineDR> ();
                for ( int i = 0 ; i < records.length () ; i ++ ) {
                    emp = records.getJSONObject ( i );
                    jComboBox2.addItem ( emp.get ( "MACHINE_NO" ).toString () );
                    machines.add ( new MachineDR ( Integer.parseInt ( emp.get ( "MCH_ID" ).toString () ) , emp.get ( "MACHINE_NO" ).toString () ) );
                }
            }
        }
    };
    
    // selectedFGID = 1;
    private static boolean jobRunning = true;

    public void printFile () {

        try {
            InputStream is = new BufferedInputStream ( new FileInputStream ( "C:\\Users\\Rajesh\\Desktop\\dsdf.txt" ) );

            // create a PDF doc flavor
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;

            // Locate the default print service for this environment.
            PrintService service = PrintServiceLookup.lookupDefaultPrintService ();

            // Create and return a PrintJob capable of handling data from
            // any of the supported document flavors.
            DocPrintJob printJob = service.createPrintJob ();

            // register a listener to get notified when the job is complete
            printJob.addPrintJobListener ( new JobCompleteMonitor () );

            // Construct a SimpleDoc with the specified
            // print data, doc flavor and doc attribute set.
            Doc doc = new SimpleDoc ( is , flavor , null );

            try {
                // Print a document with the specified job attributes.
                printJob.print ( doc , null );
            } catch ( PrintException e ) {
                System.out.println ( "Print Exception " + e.getMessage () );
                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

//            while ( jobRunning ) {
//                try{
//                    Thread.sleep ( 1000 );
//                }catch( InterruptedException e ){
//                    
//                }
//            }
            System.out.println ( "Exiting app" );
            is.close ();
        } catch ( FileNotFoundException fe ) {
            System.out.println ( "Print Exception " + fe.getMessage () );
            StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , fe.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , fe.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } catch ( IOException fe2 ) {
            System.out.println ( "Print Exception " + fe2.getMessage () );
            StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , fe2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , fe2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }
    }

    private static class JobCompleteMonitor extends PrintJobAdapter {

        @Override
        public void printJobCompleted ( PrintJobEvent jobEvent ) {

            System.out.println ( "Job completed" );

            jobRunning = false;

        }

        @Override
        public void printJobFailed ( PrintJobEvent pje ) {
            System.out.println ( "Job failed " );
        }

    }

    public static void loadEntries () {

        //        try {
        //            ResultSet result = DB_Operations.executeSingle ( "select showrdate as 'From Date', starttime as 'From Time', showrtdate as 'To Date',  stoptime as 'To Time', (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product', (select MACHINE_NO from machine where MCH_ID in (machineid)) as 'Machine', (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process',  qtyin as 'Qty In', qtyout as 'Qty Out', rejection as 'Rejected', (select EMP_NAME from employee where EmployeePK in (empid)) as 'Employee' from dailyreport" );
        //            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 
        //            if ( result != null ) {
        //                jTable1.setModel ( buildTableModel ( result ) );
        //            }
        //
        //        } catch ( SQLException e ) {
        //                System.out.println ( "Error "+e.getMessage () );
        //                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        //        }
    }

    public static DefaultTableModel buildTableModel ( ResultSet rs )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        int columnCount = metaData.getColumnCount ();
        for ( int column = 1 ; column <= columnCount ; column ++ ) {
            columnNames.add ( metaData.getColumnName ( column ) );
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        while ( rs.next () ) {

            Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {
                vector.add ( rs.getObject ( columnIndex ) );
            }
            data.add ( vector );
        }

        return new DefaultTableModel ( data , columnNames );

        /*
         * report query
         */
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

        dateChooserDialog1 = new datechooser.beans.DateChooserDialog();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jSpinner1 = new javax.swing.JSpinner();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jSpinner2 = new javax.swing.JSpinner();
        jTextField5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new CustomScrollPane1();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        dateChooserCombo3 = new datechooser.beans.DateChooserCombo();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        UOMOUT1 = new javax.swing.JComboBox<>();
        UOMOUT = new javax.swing.JComboBox<>();
        UOMIN = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1120, 500));
        setPreferredSize(new java.awt.Dimension(1115, 529));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("From Date");
        add(jLabel1);
        jLabel1.setBounds(170, 20, 60, 16);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Product Code\\No");
        add(jLabel2);
        jLabel2.setBounds(20, 20, 100, 16);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Process");
        add(jLabel3);
        jLabel3.setBounds(20, 70, 100, 16);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("From Time");
        add(jLabel4);
        jLabel4.setBounds(290, 20, 90, 16);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("To Time");
        add(jLabel5);
        jLabel5.setBounds(290, 70, 60, 16);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("Qty Out");
        add(jLabel6);
        jLabel6.setBounds(230, 150, 50, 16);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("Employee");
        add(jLabel8);
        jLabel8.setBounds(20, 230, 120, 16);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("Rejected");
        add(jLabel9);
        jLabel9.setBounds(290, 150, 50, 16);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox1.setNextFocusableComponent(jComboBox4);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        add(jComboBox1);
        jComboBox1.setBounds(20, 40, 140, 30);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox2.setNextFocusableComponent(jComboBox3);
        add(jComboBox2);
        jComboBox2.setBounds(20, 200, 140, 30);

        jTextField1.setText("5");
        jTextField1.setNextFocusableComponent(jTextField2);
        jTextField1.setPreferredSize(new java.awt.Dimension(0, 0));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(470, 90, 0, 0);

        jTextField2.setText("5");
        jTextField2.setNextFocusableComponent(jSpinner1);
        jTextField2.setPreferredSize(new java.awt.Dimension(0, 0));
        add(jTextField2);
        jTextField2.setBounds(520, 90, 0, 0);

        jSpinner1.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner1.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner1.setNextFocusableComponent(jTextField6);
        jSpinner1.setPreferredSize(new java.awt.Dimension(0, 0));
        add(jSpinner1);
        jSpinner1.setBounds(570, 90, 0, 0);

        jTextField3.setText("0");
        jTextField3.setNextFocusableComponent(jTextField4);
        jTextField3.setPreferredSize(new java.awt.Dimension(0, 0));
        add(jTextField3);
        jTextField3.setBounds(470, 40, 0, 0);

        jTextField4.setText("0");
        jTextField4.setNextFocusableComponent(jSpinner2);
        jTextField4.setPreferredSize(new java.awt.Dimension(0, 0));
        add(jTextField4);
        jTextField4.setBounds(520, 40, 0, 0);

        jSpinner2.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner2.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner2.setNextFocusableComponent(jTextField1);
        jSpinner2.setPreferredSize(new java.awt.Dimension(0, 0));
        add(jSpinner2);
        jSpinner2.setBounds(570, 40, 0, 0);

        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setText("0");
        jTextField5.setNextFocusableComponent(jTextField7);
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        add(jTextField5);
        jTextField5.setBounds(230, 170, 50, 30);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setText("Qty In");
        add(jLabel10);
        jLabel10.setBounds(170, 150, 40, 16);

        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField6.setText("0");
        jTextField6.setNextFocusableComponent(jTextField5);
        add(jTextField6);
        jTextField6.setBounds(170, 170, 50, 30);

        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField7.setText("0");
        jTextField7.setNextFocusableComponent(jComboBox5);
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        add(jTextField7);
        jTextField7.setBounds(290, 170, 50, 30);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox3.setNextFocusableComponent(jTextField9);
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        add(jComboBox3);
        jComboBox3.setBounds(20, 250, 140, 30);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Add");
        jButton1.setOpaque(false);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(800, 310, 77, 32);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Close");
        jButton2.setOpaque(false);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(880, 310, 77, 32);

        jTable1.setAutoCreateRowSorter(true);
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
        jTable1.setOpaque(false);
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(20, 345, 1085, 240);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("Machine");
        add(jLabel7);
        jLabel7.setBounds(20, 180, 100, 16);

        jComboBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox4.setNextFocusableComponent(jTextField8);
        add(jComboBox4);
        jComboBox4.setBounds(20, 90, 140, 30);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("To Date");
        add(jLabel11);
        jLabel11.setBounds(170, 70, 60, 16);

        dateChooserCombo3.setCalendarPreferredSize(new java.awt.Dimension(320, 220));
        dateChooserCombo3.setFormat(2);
        dateChooserCombo3.addSelectionChangedListener(new datechooser.events.SelectionChangedListener() {
            public void onSelectionChange(datechooser.events.SelectionChangedEvent evt) {
                dateChooserCombo3OnSelectionChange(evt);
            }
        });
        add(dateChooserCombo3);
        dateChooserCombo3.setBounds(170, 40, 110, 30);

        dateChooserCombo1.setCalendarPreferredSize(new java.awt.Dimension(320, 220));
        try {
            dateChooserCombo1.setDefaultPeriods(dateChooserCombo3.getDefaultPeriods());
        } catch (datechooser.model.exeptions.IncompatibleDataExeption e1) {
            e1.printStackTrace();
        }
        dateChooserCombo1.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                dateChooserCombo1OnCommit(evt);
            }
        });
        add(dateChooserCombo1);
        dateChooserCombo1.setBounds(170, 90, 110, 30);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("Order Number");
        add(jLabel12);
        jLabel12.setBounds(20, 120, 100, 20);

        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel13.setText("RM Batch No");
        add(jLabel13);
        jLabel13.setBounds(20, 280, 140, 20);

        jTextField8.setNextFocusableComponent(jComboBox2);
        jTextField8.setPreferredSize(new java.awt.Dimension(0, 24));
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });
        add(jTextField8);
        jTextField8.setBounds(180, 300, 0, 30);

        jTextField9.setNextFocusableComponent(jTextField3);
        add(jTextField9);
        jTextField9.setBounds(210, 300, 0, 30);

        jComboBox5.setEnabled(false);
        jComboBox5.setNextFocusableComponent(jButton1);
        jComboBox5.setPreferredSize(new java.awt.Dimension(0, 0));
        add(jComboBox5);
        jComboBox5.setBounds(350, 190, 0, 0);

        jLabel14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel14.setText("Rejection Reasons & Count");
        add(jLabel14);
        jLabel14.setBounds(830, 20, 150, 20);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(0, 0));

        jLabel16.setText("Shift");
        jPanel1.add(jLabel16);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Shift 1 Name");
        jRadioButton1.setEnabled(false);
        jPanel1.add(jRadioButton1);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jRadioButton2.setText("Shift 2 Name");
        jRadioButton2.setEnabled(false);
        jPanel1.add(jRadioButton2);

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jRadioButton3.setText("Shift 3 Name");
        jRadioButton3.setEnabled(false);
        jPanel1.add(jRadioButton3);

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jRadioButton4.setText("Shift 4 Name");
        jRadioButton4.setEnabled(false);
        jPanel1.add(jRadioButton4);

        jCheckBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox1.setText("Done over time ?");
        jPanel1.add(jCheckBox1);

        add(jPanel1);
        jPanel1.setBounds(680, 20, 0, 0);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(0, 0));
        jPanel2.setLayout(null);

        jLabel17.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel17.setText("Machine Maintenance");
        jPanel2.add(jLabel17);
        jLabel17.setBounds(20, 30, 130, 30);

        jLabel18.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel18.setText("Tools Maintenance");
        jPanel2.add(jLabel18);
        jLabel18.setBounds(20, 60, 130, 30);

        jLabel19.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel19.setText("No Load");
        jPanel2.add(jLabel19);
        jLabel19.setBounds(20, 90, 130, 30);

        jLabel20.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel20.setText("No Manpower");
        jPanel2.add(jLabel20);
        jLabel20.setBounds(20, 120, 130, 30);

        jLabel21.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel21.setText("Setup Time");
        jPanel2.add(jLabel21);
        jLabel21.setBounds(20, 150, 130, 30);

        jLabel22.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel22.setText("Breaks");
        jPanel2.add(jLabel22);
        jLabel22.setBounds(20, 180, 130, 30);

        jLabel23.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel23.setText("Power Loss / Power Fail");
        jPanel2.add(jLabel23);
        jLabel23.setBounds(20, 210, 130, 30);

        jTextField10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField10.setText("0");
        jPanel2.add(jTextField10);
        jTextField10.setBounds(150, 30, 50, 24);

        jTextField11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField11.setText("0");
        jPanel2.add(jTextField11);
        jTextField11.setBounds(150, 60, 50, 24);

        jTextField12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField12.setText("0");
        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField12);
        jTextField12.setBounds(150, 90, 50, 24);

        jTextField13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField13.setText("0");
        jPanel2.add(jTextField13);
        jTextField13.setBounds(150, 120, 50, 24);

        jTextField14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField14.setText("0");
        jPanel2.add(jTextField14);
        jTextField14.setBounds(150, 150, 50, 24);

        jTextField15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField15.setText("0");
        jPanel2.add(jTextField15);
        jTextField15.setBounds(150, 180, 50, 24);

        jTextField16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField16.setText("0");
        jPanel2.add(jTextField16);
        jTextField16.setBounds(150, 210, 50, 24);

        jLabel24.setText("Time loss in minutes");
        jPanel2.add(jLabel24);
        jLabel24.setBounds(40, 0, 130, 30);

        jLabel29.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel29.setText("Other");
        jPanel2.add(jLabel29);
        jLabel29.setBounds(20, 240, 130, 30);

        jTextField17.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField17.setText("0");
        jPanel2.add(jTextField17);
        jTextField17.setBounds(150, 240, 50, 24);

        add(jPanel2);
        jPanel2.setBounds(880, 20, 0, 0);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Import Data (.xls)");
        jButton3.setOpaque(false);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(960, 310, 150, 32);

        jLabel25.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel25.setText("Available Raw Material Qty");
        add(jLabel25);
        jLabel25.setBounds(170, 230, 170, 16);

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(jLabel26);
        jLabel26.setBounds(170, 250, 160, 30);

        jLabel27.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel27.setText("Estimated Parts");
        add(jLabel27);
        jLabel27.setBounds(170, 280, 110, 16);

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(jLabel28);
        jLabel28.setBounds(170, 300, 160, 30);

        jLabel15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel15.setText("Select Shift");
        add(jLabel15);
        jLabel15.setBounds(390, 20, 110, 20);

        jLabel30.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel30.setText("Time Loss Reasons & Time");
        add(jLabel30);
        jLabel30.setBounds(570, 20, 160, 20);

        add(jComboBox6);
        jComboBox6.setBounds(20, 140, 140, 30);

        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });
        add(jComboBox7);
        jComboBox7.setBounds(20, 300, 140, 30);

        jLabel31.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Raw Material Requied");
        add(jLabel31);
        jLabel31.setBounds(20, 340, 150, 16);

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(jLabel32);
        jLabel32.setBounds(170, 200, 170, 30);

        UOMOUT1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(UOMOUT1);
        UOMOUT1.setBounds(290, 200, 50, 26);

        UOMOUT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(UOMOUT);
        UOMOUT.setBounds(230, 200, 50, 26);

        UOMIN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(UOMIN);
        UOMIN.setBounds(170, 200, 50, 26);
    }// </editor-fold>//GEN-END:initComponents

    HashMap<String , Object> map;
    JSONObject jObject;
    Iterator<?> keys = null;
    JSONObject st = null;
    JSONArray records = null;
    JSONObject emp = null;

    public int iDFromJSON ( String json ) {
        int id = 0;

        jObject = new JSONObject ( json );
        keys = jObject.keys ();

        JSONObject value = ( JSONObject ) jObject.get ( "data" );

        id = Integer.parseInt ( value.get ( "insert_id" ).toString () );
        //    id = Integer.parseInt( st.getString ("insert_id" ));

        return id;
    }

    public String msgFromJSON ( String json ) {

        result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
        map = new HashMap<String , Object> ();
        jObject = new JSONObject ( result2 );
        keys = jObject.keys ();

        while ( keys.hasNext () ) {
            String key = ( String ) keys.next ();
            Object value = jObject.get ( key );
            map.put ( key , value );
        }

        st = ( JSONObject ) map.get ( "message" );
        st = ( JSONObject ) map.get ( "success" );

        return st.get ( "104" ).toString ();
    }

    
    ProgressDialog progress = new ProgressDialog( null ) ;
    Thread getTableAndFilesContent = null ;
    
    public void showDialog(){
         
        
        progress.updateTitle ( "Updating Production Details" );
        progress.updateMessage ( "<html>Updating production details...</html>");
        
        getTableAndFilesContent = new Thread(){
            public void run(){
                try{                    sleep( 1000);                }catch( InterruptedException ee ){                        }
                //addToDailyProductionReport() ; 
                
                int flag = products.get(jComboBox1.getSelectedIndex()).getAssembled ();
                if (flag == 1) {

                    addToDailyProductionReport_assembled();
                } else {
                    addToDailyProductionReport();
                }
                
            }
        };
        getTableAndFilesContent.start();
        progress.openProgressWindow ();
        
        
    }
    
    public  boolean hideDialog(){
        progress.closeProgressWindow ();
        return true ;
    }
  
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        
        showDialog() ;
    }//GEN-LAST:event_jButton1MouseClicked

    
    public void addToDailyProductionReport(){
        
        if( ! jComboBox1.getSelectedItem ().toString ().equals ( "-- Select Part --")  ){      
            int totalTimeLossInMinutes = 0;
            int totalRejection = 0;
            double totalTimeLossInHours = 0;

            //c2 = Calendar.getInstance ();
            // sdf = new SimpleDateFormat ( "dd-MM-yyyy HH:mm:ss" );
            // sdf = new SimpleDateFormat ( "dd-MM-yyyy" );
            //String strDate2 = sdf.format ( c2.getTime () );
            // System.out.println(processes.get ( jComboBox2.getSelectedIndex () ).PRC_ID ); 
            incompleteDataDialog = 0;

            Calendar c1 = Calendar.getInstance ();
            Calendar c2 = Calendar.getInstance ();
            Calendar c3 = Calendar.getInstance ();
            Calendar c4 = Calendar.getInstance ();
            SimpleDateFormat sdf1 = new SimpleDateFormat ( "HH:mm:ss" );
            SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-MM-dd" );
            //String strDate2 = sdf2.format ( c2.getTime () );

    //        String[] dateIP = dateChooserCombo3.getText ().split ( " " );
    //
            String fromDate = null;
            String toDate = null;

            int fromHr = 0, fromMin = 0, toHr = 0, toMin = 0;

            int selectedShiftid = 0;

            selectedShiftid = confirm.getSelectedShift ();

            ArrayList<TimeLossModel> timeLossList = timeLoss.getTimeLoss ();
            ArrayList<RejectionModel> rejectionList = rejection.getRejection ();

            totalTimeLossInMinutes = 0;
            for ( int j = 0 ; j < timeLossList.size () ; j ++ ) {
                totalTimeLossInMinutes = totalTimeLossInMinutes + timeLossList.get ( j ).getMinutes ();
            }

            if ( totalTimeLossInMinutes > 720 ) {
                JOptionPane.showMessageDialog ( null , "Maximum time loss in minutes for single shift can be 720 minutes only" );
                totalTimeLossInMinutes = 720;
            }

            DecimalFormat df = new DecimalFormat ( "#.##" );
            double hr = 0.0, min = 0.0;
            double hr2 = 0.0, min2 = 0.0;
            if ( totalTimeLossInMinutes > 59 ) {
                hr = ( totalTimeLossInMinutes / 60 );
                //totalTimeLossInHours = (totalTimeLossInMinutes / 60) ;
                //min = Double.parseDouble( df.format( (( totalTimeLossInMinutes % 60 )/60)*100)) ;
                //String remMin = df.format( totalTimeLossInMinutes % 60 )  ;
                min = Double.parseDouble ( df.format ( Double.parseDouble ( df.format ( totalTimeLossInMinutes % 60 ) ) / 60 ) );
            } else {
                //hr = (totalTimeLossInMinutes / 60) ;
                hr = Double.parseDouble ( df.format ( Double.parseDouble ( df.format ( totalTimeLossInMinutes % 60 ) ) / 60 ) );
            }
            //JOptionPane.showMessageDialog ( null, "<html>actual Gross time loss in hours is "+(hr+min)+"<br> actual Gross  Time Loss In Minutes "+totalTimeLossInMinutes+"</html>");

            long totalMin;
            double totalHrs;

            // String fromTime = sdf2.format ( c1.getTime ()) +" " + String.valueOf ( fromHr + ":" + fromMin ) ;
            // String toTime = sdf2.format ( c2.getTime ()) +" " + String.valueOf ( toHr + ":" + toMin ) ;
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern ( "yyyy-MM-dd HH:mm" , Locale.ENGLISH );

            String fromTime = "", toTime = "";
            fromDate = sdf2.format ( dateChooserCombo3.getSelectedDate ().getTime () );
            toDate = sdf2.format ( dateChooserCombo1.getSelectedDate ().getTime () );
            try {
    //                    fromTime  = new SimpleDateFormat ( "yyyy-MM-dd HH:mm" ).format ( ( Date ) new SimpleDateFormat ( "E MMM dd HH:mm:ss Z yyyy" ).parse ( fromTimeSpinner.getValue ().toString () ) );
    //                    toTime      = new SimpleDateFormat ( "yyyy-MM-dd HH:mm" ).format ( ( Date ) new SimpleDateFormat ( "E MMM dd HH:mm:ss Z yyyy" ).parse ( toTimeSpinner.getValue ().toString () ) );
                fromTime = sdf2.format ( dateChooserCombo3.getSelectedDate ().getTime () ) + " " + new SimpleDateFormat ( "HH:mm" ).format ( ( Date ) new SimpleDateFormat ( "E MMM dd HH:mm:ss Z yyyy" ).parse ( fromTimeSpinner.getValue ().toString () ) );
                toTime = sdf2.format ( dateChooserCombo1.getSelectedDate ().getTime () ) + " " + new SimpleDateFormat ( "HH:mm" ).format ( ( Date ) new SimpleDateFormat ( "E MMM dd HH:mm:ss Z yyyy" ).parse ( toTimeSpinner.getValue ().toString () ) );
            } catch ( ParseException e ) {

            }

            LocalDateTime d1 = LocalDateTime.parse ( fromTime , formatter );
            LocalDateTime d2 = LocalDateTime.parse ( toTime , formatter );
            Duration difference = Duration.between ( d1 , d2 );

            totalMin = difference.toMinutes ();

            try {
                fromTime    = new SimpleDateFormat ( "HH:mm:ss" ).format ( ( Date ) new SimpleDateFormat ( "E MMM dd HH:mm:ss Z yyyy" ).parse ( fromTimeSpinner.getValue ().toString () ) );
                toTime        = new SimpleDateFormat ( "HH:mm:ss" ).format ( ( Date ) new SimpleDateFormat ( "E MMM dd HH:mm:ss Z yyyy" ).parse ( toTimeSpinner.getValue ().toString () ) );

                fromHr = Integer.parseInt ( fromTime.split ( ":" )[ 0 ] );
                toHr = Integer.parseInt ( toTime.split ( ":" )[ 0 ] );

                fromMin = Integer.parseInt ( fromTime.split ( ":" )[ 1 ] );
                toMin = Integer.parseInt ( toTime.split ( ":" )[ 1 ] );

            } catch ( ParseException e ) {

            }

            if ( totalMin > 59 ) {
                totalHrs = ( totalMin / 60 );
                //min = Double.parseDouble( df.format( (( totalTimeLossInMinutes % 60 )/60)*100)) ;
                //String remMin = df.format( totalMin % 60 )  ;
                min2 = Double.parseDouble ( df.format ( Double.parseDouble ( df.format ( totalMin % 60 ) ) / 60 ) );
            } else {
                // totalHrs = (totalMin / 60) ;
                totalHrs = Double.parseDouble ( df.format ( Double.parseDouble ( df.format ( totalMin % 60 ) ) / 60 ) );
            }

            int totalRejections = 0;

            //  if( totalRejections != Integer.parseInt( jTextField7.getText()) ){
            //      JOptionPane.showMessageDialog ( null, "Total Rejection does not macth to total of all rejections");
            //  }
            if ( fromDate.equalsIgnoreCase ( toDate ) ) {
                if ( toHr < fromHr ) {
                    //if ( true ) {
                    JOptionPane.showMessageDialog ( null , "Invalid 'from' and 'to' time for same day" );
                } else if ( toHr == fromHr ) {
                    //} else if ( true ) {
                    if ( toMin == fromMin ) {
                        JOptionPane.showMessageDialog ( null , "Invalid date and/or time selection" );
                    } else {

                        //  String queryInsert = "insert into dailyreport ( rdate, rtdate,showrdate, showrtdate, fgid, machineid, processid, starttime, stoptime, qtyin, qtyout, rejection, empid, showFromTime, showToTime, batchno, rej_reason, shift_id , actual_min ,  total_min ,  actual_hours , total_hours  , customer_id  ) values ( '" + sdf2.format ( c1.getTime () ) + " 00:00:00',  '" + sdf2.format ( c2.getTime () ) + " 00:00:00',  '" + dateChooserCombo3.getText () + "','" + dateChooserCombo1.getText () + "','" + products.get ( jComboBox1.getSelectedIndex () ).FG_ID + "',' " + machines.get ( jComboBox2.getSelectedIndex () ).MC_ID + "',  '" + processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID + "', '" + sdf2.format ( c1.getTime () ) +" "+ fromTime + "',  '" + sdf2.format ( c2.getTime () ) +" "+ toTime + "',  " + jTextField6.getText () + ", " + jTextField5.getText () + ", " + jTextField7.getText () + ",   " + employees.get ( jComboBox3.getSelectedIndex () ).EMP_ID + ",'" + sdf2.format ( c1.getTime () ) +" "+ fromTime + "',  '" + sdf2.format ( c2.getTime () ) +" "+ toTime + "',  '" + jTextField9.getText ().trim () + "', " + rej_reasons.get ( jComboBox5.getSelectedIndex () ).REJ_ID + "  ,"+selectedShiftid +"  , "+(totalMin - totalTimeLossInMinutes)+", "+ totalMin+", "+ ((totalHrs+min2)- (hr+min))+", "+(totalHrs+min2)+" , "+1+"  )";
                        //  DB_Operations.executeInsertRandom ( queryInsert );
                        try {
                            String addEmpAPICall = "dailyreportadd?rdate=" + URLEncoder.encode ( fromDate + " 00:00:00" , "UTF-8" ) + "&rtdate=" + URLEncoder.encode ( toDate + " 00:00:00" , "UTF-8" ) + "&showrdate=" + URLEncoder.encode ( new SimpleDateFormat ( "MMM dd, yyyy" ).format ( dateChooserCombo3.getSelectedDate ().getTime () ) , "UTF-8" ) + "&showrtdate=" + URLEncoder.encode ( new SimpleDateFormat ( "MMM dd, yyyy" ).format ( dateChooserCombo1.getSelectedDate ().getTime () ) , "UTF-8" ) + "&fgid=" + URLEncoder.encode ( products.get ( jComboBox1.getSelectedIndex () ).FG_ID + "" , "UTF-8" ) + "&machineid=" + URLEncoder.encode ( machines.get ( jComboBox2.getSelectedIndex () ).MC_ID + "" , "UTF-8" ) + "&processid=" + URLEncoder.encode ( processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID + "" , "UTF-8" ) + "&starttime=" + URLEncoder.encode ( fromDate + " " + fromTime , "UTF-8" ) + "&stoptime=" + URLEncoder.encode ( toDate + " " + toTime , "UTF-8" ) + "&qtyin=" + URLEncoder.encode ( jTextField6.getText () , "UTF-8" ) + "&qtyout=" + URLEncoder.encode ( jTextField5.getText () + "" , "UTF-8" ) + "&rejection=" + URLEncoder.encode ( jTextField7.getText () + "" , "UTF-8" ) + "&empid=" + URLEncoder.encode ( employees.get ( jComboBox3.getSelectedIndex () ).EMP_ID + "" , "UTF-8" ) + "&showFromTime=" + URLEncoder.encode ( fromDate + " " + fromTime + "" , "UTF-8" ) + "&showToTime=" + URLEncoder.encode ( toDate + " " + toTime + "" , "UTF-8" ) + "&batchno=" + URLEncoder.encode ( batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID + "" , "UTF-8" ) + "&rej_reason=" + URLEncoder.encode ( rej_reasons.get ( jComboBox5.getSelectedIndex () ).REJ_ID + "" , "UTF-8" ) + "&shift_id=" + URLEncoder.encode ( selectedShiftid + "" , "UTF-8" ) + "&actual_min=" + URLEncoder.encode ( ( totalMin - totalTimeLossInMinutes ) + "" , "UTF-8" ) + "&total_min=" + URLEncoder.encode ( totalMin + "" , "UTF-8" ) + "&actual_hours=" + URLEncoder.encode ( ( ( totalHrs + min2 ) - ( hr + min ) ) + "" , "UTF-8" ) + "&total_hours=" + URLEncoder.encode ( ( totalHrs + min2 ) + "" , "UTF-8" ) + "&customer_id=" + URLEncoder.encode ( orders.get ( jComboBox6.getSelectedIndex () ).ORD_ID + "" , "UTF-8" );
                            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                            //JOptionPane.showMessageDialog ( null , result2 );

                            System.out.println ( "" + result2 );
                            int recordId = iDFromJSON ( result2 );
                            progress.updateMessage ( "<html>Production details added to daily production report</htm>");
                            
                            
                            for ( int j = 0 ; j < timeLossList.size () ; j ++ ) {
                                totalTimeLossInMinutes = totalTimeLossInMinutes + timeLossList.get ( j ).getMinutes ();
                                addEmpAPICall = "timelossadd?ref_pID=" + recordId + "&tlrID=" + timeLossList.get ( j ).getId () + "&tlQty=" + timeLossList.get ( j ).getMinutes ();
                                result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                            }
                            
                            
                            for ( int j = 0 ; j < rejectionList.size () ; j ++ ) {
                                totalRejection = totalRejection + rejectionList.get ( j ).getRejectionForReason ();
                                addEmpAPICall = "rejdataadd?ref_pID=" + recordId + "&rrID=" + rejectionList.get ( j ).getRejectionReasonId () + "&rQty=" + rejectionList.get ( j ).getRejectionForReason ();
                                result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                            }

                            //JOptionPane.showMessageDialog ( null , result2);
                            //opwip2add?fgid=1                                                                                    &processid=1&ORDERNO=1                                                                  &empid=1                                                                                                &machine=1                                                                                                                                                                              &BALANCE=5&FINISHED=20&MOVEQTY=25&MOVEDATE=2019-02-27+00:00:00//
                            int FGID=products.get(jComboBox1.getSelectedIndex()).FG_ID;
                            int RMID=batches.get(jComboBox7.getSelectedIndex()).RMID;
                            if(verifyFirstOrOnlyProcess ( FGID , processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID ).equals( "firstProcess" )  || verifyFirstOrOnlyProcess ( FGID , processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID ).equals( "onlyProcess" ))
                            {
                                try{
                                    int value=getUnitFrom(RMID, FGID);
                                    int bal  =( (Integer.parseInt ( jTextField6.getText () )*value) - Integer.parseInt ( jTextField5.getText () ) );
                                    double existing_wip = check_If_WIP_Exists(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID);
                                    
                                   if (jCheckBox_wip.isSelected()  && existing_wip > 0.0 ) {
                                        double   rmforwip = existing_wip / value ;
                                        rmforwip = (rmRequiredForNewProd*1.0) + rmforwip ;
                                        int   totalRM =   Integer.parseInt ( jTextField6.getText () ) ;
                                        double actualRM = ( rmforwip - (totalRM*1.0)) ;
                                        double         actualWIP = 0 ;
                                        actualWIP = (int)actualRM *value ;
                                        System.out.println ( " wip after considering old wip "+ actualWIP );
                                        bal = (int)actualWIP ;
                                    }
                                    
                                    if (existing_wip > 0.0) {
                                        if (jCheckBox_wip.isSelected()) 
                                        {
                                            //   bal = (int)existing_wip ;
                                            if (bal >= 0) {
                                                addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                        + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                        + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                        + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                        + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                        + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                        + "&FINISHED=" + jTextField5.getText()
                                                        + "&MOVEQTY=" + jTextField6.getText()
                                                        + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                        + "&BALANCE=" + bal;
                                                WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                            }
                                        } else {
                                            if (bal > 0) {
                                                bal = bal + (int) existing_wip;
                                                addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                        + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                        + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                        + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                        + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                        + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                        + "&FINISHED=" + jTextField5.getText()
                                                        + "&MOVEQTY=" + jTextField6.getText()
                                                        + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                        + "&BALANCE=" + bal;
                                                WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                            }
                                        }

                                    } else if (existing_wip == 0.0 && wip_id != 0) {
                                        addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                + "&FINISHED=" + jTextField5.getText()
                                                + "&MOVEQTY=" + jTextField6.getText()
                                                + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                + "&BALANCE=" + bal;
                                        WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                    } else if (bal > 0){
                                        String getWIPforSelectedData = "opwip2add?fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                + "&FINISHED=" + jTextField5.getText()
                                                + "&MOVEQTY=" + jTextField6.getText()
                                                + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                + "&BALANCE=" + bal;
                                        WebAPITester.prepareWebCall(getWIPforSelectedData, StaticValues.getHeader(), "");
                                    }
                                    
                                }catch(Exception e){}
                            }else{
                                int bal  =( (Integer.parseInt ( jTextField6.getText () )) - Integer.parseInt ( jTextField5.getText () ) );
                                double existing_wip = check_If_WIP_Exists(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID);
                                
                                if (existing_wip > 0.0) {
                                    if (jCheckBox_wip.isSelected()) 
                                    {
                                        //   bal = (int)existing_wip ;
                                        if (bal >= 0) {
                                            addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                    + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                    + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                    + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                    + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                    + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                    + "&FINISHED=" + jTextField5.getText()
                                                    + "&MOVEQTY=" + jTextField6.getText()
                                                    + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                    + "&BALANCE=" + bal;
                                            WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                        }
                                    } else {
                                        if (bal > 0) {
                                            bal = bal + (int) existing_wip;
                                            addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                    + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                    + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                    + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                    + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                    + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                    + "&FINISHED=" + jTextField5.getText()
                                                    + "&MOVEQTY=" + jTextField6.getText()
                                                    + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                    + "&BALANCE=" + bal;
                                            WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                        }
                                    }

                                } else if (existing_wip == 0.0 && wip_id != 0) {
                                    addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                            + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                            + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                            + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                            + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                            + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                            + "&FINISHED=" + jTextField5.getText()
                                            + "&MOVEQTY=" + jTextField6.getText()
                                            + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                            + "&BALANCE=" + bal;
                                    WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                } else if( bal >0  ){
                                    String getWIPforSelectedData = "opwip2add?fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                            + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                            + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                            + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                            + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                            + "&FINISHED=" + jTextField5.getText()
                                            + "&MOVEQTY=" + jTextField6.getText()
                                            + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                            + "&BALANCE=" + bal;
                                    WebAPITester.prepareWebCall(getWIPforSelectedData, StaticValues.getHeader(), "");
                                }                                  
                            }
                            progress.updateMessage ( "<html>Balence items updated to Work In Progress report</htm>");
                            
                            
                        } catch ( UnsupportedEncodingException e ) {
                            JOptionPane.showMessageDialog ( null , "URL Encoding " + e.getMessage () );
                        }
                        addToStores ( products.get ( jComboBox1.getSelectedIndex () ).FG_ID , processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID , Integer.parseInt(  jTextField6.getText () )   );
                        loadEntries ();

                        t = new Thread () {

                            public void run () {
                                progress.updateMessage ( "<html>Refreshing production report table</htm>");
                                getFreshlist ();
                            }
                        };
                        t.start ();
                    }
                } else {


                    try {
                        //String addEmpAPICall = "dailyreportadd?rdate="+URLEncoder.encode( sdf2.format (c1.getTime())+" 00:00:00", "UTF-8")+"&rtdate="+URLEncoder.encode(sdf2.format ( c2.getTime () ) + " 00:00:00", "UTF-8")+"&showrdate="+URLEncoder.encode(dateChooserCombo3.getText (), "UTF-8")+"&showrtdate="+URLEncoder.encode(dateChooserCombo1.getText (), "UTF-8")+"&fgid="+URLEncoder.encode(products.get ( jComboBox1.getSelectedIndex () ).FG_ID+"", "UTF-8")+"&machineid="+URLEncoder.encode(machines.get ( jComboBox2.getSelectedIndex () ).MC_ID+"", "UTF-8")+"&processid="+URLEncoder.encode(processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID+"","UTF-8")+"&starttime="+URLEncoder.encode(fromTime,"UTF-8")+"&stoptime="+URLEncoder.encode(toTime,"UTF-8")+"&qtyin="+URLEncoder.encode(jTextField6.getText (), "UTF-8")+"&qtyout="+URLEncoder.encode(jTextField5.getText ()+"","UTF-8")+"&rejection="+URLEncoder.encode(jTextField7.getText ()+"", "UTF-8")+"&empid="+URLEncoder.encode(employees.get ( jComboBox3.getSelectedIndex () ).EMP_ID+"", "UTF-8")+"&showFromTime,="+URLEncoder.encode(fromTime+"", "UTF-8")+"&showToTime,="+URLEncoder.encode(toTime+"", "UTF-8")+"&batchno,="+URLEncoder.encode(jTextField9.getText ().trim ()+"", "UTF-8")+"&rej_reason="+URLEncoder.encode(rej_reasons.get ( jComboBox5.getSelectedIndex () ).REJ_ID+"", "UTF-8")+"&shift_id="+URLEncoder.encode(selectedShiftid+"", "UTF-8")+"&actual_min="+URLEncoder.encode((totalMin - totalTimeLossInMinutes)+"", "UTF-8")+"&total_min="+URLEncoder.encode(totalMin+"", "UTF-8")+"&actual_hours="+URLEncoder.encode(((totalHrs+min2)- (hr+min))+"", "UTF-8")+"&total_hours="+URLEncoder.encode((totalHrs+min2)+"", "UTF-8")+"&customer_id="+URLEncoder.encode(customerId+"", "UTF-8")  ;
                        String addEmpAPICall = "dailyreportadd?rdate=" + URLEncoder.encode ( fromDate + " 00:00:00" , "UTF-8" ) + "&rtdate=" + URLEncoder.encode ( toDate + " 00:00:00" , "UTF-8" ) + "&showrdate=" + URLEncoder.encode ( new SimpleDateFormat ( "MMM dd, yyyy" ).format ( dateChooserCombo3.getSelectedDate ().getTime () ) , "UTF-8" ) + "&showrtdate=" + URLEncoder.encode ( new SimpleDateFormat ( "MMM dd, yyyy" ).format ( dateChooserCombo1.getSelectedDate ().getTime () ) , "UTF-8" ) + "&fgid=" + URLEncoder.encode ( products.get ( jComboBox1.getSelectedIndex () ).FG_ID + "" , "UTF-8" ) + "&machineid=" + URLEncoder.encode ( machines.get ( jComboBox2.getSelectedIndex () ).MC_ID + "" , "UTF-8" ) + "&processid=" + URLEncoder.encode ( processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID + "" , "UTF-8" ) + "&starttime=" + URLEncoder.encode ( fromDate + " " + fromTime , "UTF-8" ) + "&stoptime=" + URLEncoder.encode ( toDate + " " + toTime , "UTF-8" ) + "&qtyin=" + URLEncoder.encode ( jTextField6.getText () , "UTF-8" ) + "&qtyout=" + URLEncoder.encode ( jTextField5.getText () + "" , "UTF-8" ) + "&rejection=" + URLEncoder.encode ( jTextField7.getText () + "" , "UTF-8" ) + "&empid=" + URLEncoder.encode ( employees.get ( jComboBox3.getSelectedIndex () ).EMP_ID + "" , "UTF-8" ) + "&showFromTime=" + URLEncoder.encode ( fromDate + " " + fromTime + "" , "UTF-8" ) + "&showToTime=" + URLEncoder.encode ( toDate + " " + toTime + "" , "UTF-8" ) + "&batchno=" + URLEncoder.encode ( batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID + "" , "UTF-8" ) + "&rej_reason=" + URLEncoder.encode ( rej_reasons.get ( 1 ).REJ_ID + "" , "UTF-8" ) + "&shift_id=" + URLEncoder.encode ( selectedShiftid + "" , "UTF-8" ) + "&actual_min=" + URLEncoder.encode ( ( totalMin - totalTimeLossInMinutes ) + "" , "UTF-8" ) + "&total_min=" + URLEncoder.encode ( totalMin + "" , "UTF-8" ) + "&actual_hours=" + URLEncoder.encode ( ( ( totalHrs + min2 ) - ( hr + min ) ) + "" , "UTF-8" ) + "&total_hours=" + URLEncoder.encode ( ( totalHrs + min2 ) + "" , "UTF-8" ) + "&customer_id=" + URLEncoder.encode ( orders.get ( jComboBox6.getSelectedIndex () ).CUS_ID + "" , "UTF-8" );
                        String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                        //JOptionPane.showMessageDialog ( null , result2 );
                        progress.updateMessage ( "<html>Production details added to daily production report</htm>");
                        
                        System.out.println ( "" + result2 );

                        int recordId = iDFromJSON ( result2 );

                        for ( int j = 0 ; j < timeLossList.size () ; j ++ ) {
                            totalTimeLossInMinutes = totalTimeLossInMinutes + timeLossList.get ( j ).getMinutes ();
                            addEmpAPICall = "timelossadd?ref_pID=" + recordId + "&tlrID=" + timeLossList.get ( j ).getId () + "&tlQty=" + timeLossList.get ( j ).getMinutes ();
                            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                        }
                        JOptionPane.showMessageDialog ( null , result2 );

                        for ( int j = 0 ; j < rejectionList.size () ; j ++ ) {
                            totalRejection = totalRejection + rejectionList.get ( j ).getRejectionForReason ();
                            addEmpAPICall = "rejdataadd?ref_pID=" + recordId + "&rrID=" + rejectionList.get ( j ).getRejectionReasonId () + "&rQty=" + rejectionList.get ( j ).getRejectionForReason ();
                            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                        }

                            int FGID=products.get(jComboBox1.getSelectedIndex()).FG_ID;
                            int RMID=batches.get(jComboBox7.getSelectedIndex()).RMID;
                            String chk=processes.get ( jComboBox4.getSelectedIndex () ).PRC_NAME.replace(" ","");
                            if( verifyFirstOrOnlyProcess ( products.get ( jComboBox1.getSelectedIndex () ).FG_ID , processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID ).equals( "firstProcess" )  || verifyFirstOrOnlyProcess ( products.get ( jComboBox1.getSelectedIndex () ).FG_ID , processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID ).equals( "onlyProcess" ) )
                            {
                                try{
                                    int value=getUnitFrom(RMID, FGID);
                                    int bal  =( (Integer.parseInt ( jTextField6.getText () )*value) - Integer.parseInt ( jTextField5.getText () ) );
                                    
                                    double existing_wip = check_If_WIP_Exists(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID);
                                    
                                    if (jCheckBox_wip.isSelected()  && existing_wip > 0.0 ) {
                                        double   rmforwip = existing_wip / value ;
                                        rmforwip = (rmRequiredForNewProd*1.0) + rmforwip ;
                                        int   totalRM =   Integer.parseInt ( jTextField6.getText () ) ;
                                        double actualRM = (  rmforwip  -   (totalRM*1.0) ) ;
                                        double         actualWIP = 0 ;
                                        actualWIP = actualRM *value ;
                                        System.out.println ( " wip after considering old wip "+ actualWIP );
                                        bal = (int)actualWIP ;
                                    }
                                    
                                    if (existing_wip > 0.0) {
                                        if (jCheckBox_wip.isSelected()) 
                                        {
                                            //   bal = (int)existing_wip ;
                                            if (bal >= 0) {
                                                addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                        + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                        + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                        + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                        + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                        + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                        + "&FINISHED=" + jTextField5.getText()
                                                        + "&MOVEQTY=" + jTextField6.getText()
                                                        + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                        + "&BALANCE=" + bal;
                                                WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                            }
                                        } else {
                                            if (bal > 0) {
                                                bal = bal + (int) existing_wip;
                                                addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                        + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                        + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                        + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                        + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                        + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                        + "&FINISHED=" + jTextField5.getText()
                                                        + "&MOVEQTY=" + jTextField6.getText()
                                                        + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                        + "&BALANCE=" + bal;
                                                WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                            }
                                        }

                                    } else if (existing_wip == 0.0 && wip_id != 0) {
                                        addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                + "&FINISHED=" + jTextField5.getText()
                                                + "&MOVEQTY=" + jTextField6.getText()
                                                + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                + "&BALANCE=" + bal;
                                        WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                    } else if ( bal > 0 ) {
                                        String getWIPforSelectedData = "opwip2add?fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                + "&FINISHED=" + jTextField5.getText()
                                                + "&MOVEQTY=" + jTextField6.getText()
                                                + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                + "&BALANCE=" + bal;
                                        WebAPITester.prepareWebCall(getWIPforSelectedData, StaticValues.getHeader(), "");
                                    }
                                }catch(Exception e){}
                            }else{
                                int bal  =( (Integer.parseInt ( jTextField6.getText () )) - Integer.parseInt ( jTextField5.getText () ) );
                                
                                double existing_wip = check_If_WIP_Exists(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID);
                                if (existing_wip > 0.0) {
                                    if (jCheckBox_wip.isSelected()) 
                                    {
                                        //   bal = (int)existing_wip ;
                                        if (bal >= 0) {
                                            addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                    + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                    + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                    + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                    + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                    + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                    + "&FINISHED=" + jTextField5.getText()
                                                    + "&MOVEQTY=" + jTextField6.getText()
                                                    + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                    + "&BALANCE=" + bal;
                                            WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                        }
                                    } else {
                                        if (bal > 0) {
                                            bal = bal + (int) existing_wip;
                                            addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                    + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                    + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                    + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                    + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                    + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                    + "&FINISHED=" + jTextField5.getText()
                                                    + "&MOVEQTY=" + jTextField6.getText()
                                                    + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                    + "&BALANCE=" + bal;
                                            WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                        }
                                    }

                                } else if (existing_wip == 0.0 && wip_id != 0) {
                                    addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                            + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                            + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                            + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                            + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                            + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                            + "&FINISHED=" + jTextField5.getText()
                                            + "&MOVEQTY=" + jTextField6.getText()
                                            + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                            + "&BALANCE=" + bal;
                                    WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                } else if ( bal > 0 ) {
                                    String getWIPforSelectedData = "opwip2add?fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                            + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                            + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                            + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                            + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                            + "&FINISHED=" + jTextField5.getText()
                                            + "&MOVEQTY=" + jTextField6.getText()
                                            + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                            + "&BALANCE=" + bal;
                                    WebAPITester.prepareWebCall(getWIPforSelectedData, StaticValues.getHeader(), "");
                                }                                  
                            }
                             progress.updateMessage ( "<html>Balence items updated to Work In Progress report</htm>");
                            
                    } catch ( UnsupportedEncodingException e ) {
                        JOptionPane.showMessageDialog ( null , "URL Encoding " + e.getMessage () );
                    }

                    addToStores ( products.get ( jComboBox1.getSelectedIndex () ).FG_ID , processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID , Integer.parseInt(  jTextField6.getText () )  );

                    //loadEntries ();
                    t = new Thread () {

                        public void run () {
                             progress.updateMessage ( "<html>Refreshing production report table</htm>");
                            getFreshlist ();
                        }
                    };
                    t.start ();
                }
            } else {

                try {
                    String addEmpAPICall = "dailyreportadd?rdate=" + URLEncoder.encode ( fromDate + " 00:00:00" , "UTF-8" ) + "&rtdate=" + URLEncoder.encode ( toDate + " 00:00:00" , "UTF-8" ) + "&showrdate=" + URLEncoder.encode ( new SimpleDateFormat ( "MMM dd, yyyy" ).format ( dateChooserCombo3.getSelectedDate ().getTime () ) , "UTF-8" ) + "&showrtdate=" + URLEncoder.encode ( new SimpleDateFormat ( "MMM dd, yyyy" ).format ( dateChooserCombo1.getSelectedDate ().getTime () ) , "UTF-8" ) + "&fgid=" + URLEncoder.encode ( products.get ( jComboBox1.getSelectedIndex () ).FG_ID + "" , "UTF-8" ) + "&machineid=" + URLEncoder.encode ( machines.get ( jComboBox2.getSelectedIndex () ).MC_ID + "" , "UTF-8" ) + "&processid=" + URLEncoder.encode ( processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID + "" , "UTF-8" ) + "&starttime=" + URLEncoder.encode ( fromDate + " " + fromTime , "UTF-8" ) + "&stoptime=" + URLEncoder.encode ( toDate + " " + toTime , "UTF-8" ) + "&qtyin=" + URLEncoder.encode ( jTextField6.getText () , "UTF-8" ) + "&qtyout=" + URLEncoder.encode ( jTextField5.getText () + "" , "UTF-8" ) + "&rejection=" + URLEncoder.encode ( jTextField7.getText () + "" , "UTF-8" ) + "&empid=" + URLEncoder.encode ( employees.get ( jComboBox3.getSelectedIndex () ).EMP_ID + "" , "UTF-8" ) + "&showFromTime=" + URLEncoder.encode ( fromDate + " " + fromTime + "" , "UTF-8" ) + "&showToTime=" + URLEncoder.encode ( toDate + " " + toTime + "" , "UTF-8" ) + "&batchno=" + URLEncoder.encode ( batches.get ( jComboBox7.getSelectedIndex () ).BTC_ID + "" , "UTF-8" ) + "&rej_reason=" + URLEncoder.encode ( rej_reasons.get ( jComboBox5.getSelectedIndex () ).REJ_ID + "" , "UTF-8" ) + "&shift_id=" + URLEncoder.encode ( selectedShiftid + "" , "UTF-8" ) + "&actual_min=" + URLEncoder.encode ( ( totalMin - totalTimeLossInMinutes ) + "" , "UTF-8" ) + "&total_min=" + URLEncoder.encode ( totalMin + "" , "UTF-8" ) + "&actual_hours=" + URLEncoder.encode ( ( ( totalHrs + min2 ) - ( hr + min ) ) + "" , "UTF-8" ) + "&total_hours=" + URLEncoder.encode ( ( totalHrs + min2 ) + "" , "UTF-8" ) + "&customer_id=" + URLEncoder.encode ( orders.get ( jComboBox6.getSelectedIndex () ).CUS_ID + "" , "UTF-8" );
                    String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    //JOptionPane.showMessageDialog ( null , result2 );
                    System.out.println ( "" + result2 );

                    int recordId = iDFromJSON ( result2 );

                    for ( int j = 0 ; j < timeLossList.size () ; j ++ ) {
                        totalTimeLossInMinutes = totalTimeLossInMinutes + timeLossList.get ( j ).getMinutes ();
                        addEmpAPICall = "timelossadd?ref_pID=" + recordId + "&tlrID=" + timeLossList.get ( j ).getId () + "&tlQty=" + timeLossList.get ( j ).getMinutes ();
                        result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    }

                    for ( int j = 0 ; j < rejectionList.size () ; j ++ ) {
                        totalRejection = totalRejection + rejectionList.get ( j ).getRejectionForReason ();
                        addEmpAPICall = "rejdataadd?ref_pID=" + recordId + "&rrID=" + rejectionList.get ( j ).getRejectionReasonId () + "&rQty=" + rejectionList.get ( j ).getRejectionForReason ();
                        result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

                    }

                    int FGID = products.get(jComboBox1.getSelectedIndex()).FG_ID;
                    int RMID = batches.get(jComboBox7.getSelectedIndex()).RMID;

                    String chk = processes.get(jComboBox4.getSelectedIndex()).PRC_NAME.replace(" ", "");

  /*                  if(verifyFirstOrOnlyProcess ( products.get ( jComboBox1.getSelectedIndex () ).FG_ID , processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID ).equals( "firstOrOnlyProcess" )){
                        try{
                            int value = getUnitFrom(RMID, FGID);
                            int bal = ((Integer.parseInt(jTextField6.getText()) * value) - Integer.parseInt(jTextField5.getText()));
                            if (bal > 0) {
                                String getWIPforSelectedData = "opwip2add?fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID + "&FINISHED=" + jTextField5.getText() + "&MOVEQTY=" + jTextField6.getText() + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8") + "&BALANCE=" + bal;
                                WebAPITester.prepareWebCall(getWIPforSelectedData, StaticValues.getHeader(), "");
                            }
                        }catch(Exception e){}
                    } else {
                        int bal = ((Integer.parseInt(jTextField6.getText())) - Integer.parseInt(jTextField5.getText()));
                        if (bal > 0) {
                            String getWIPforSelectedData = "opwip2add?fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID + "&FINISHED=" + jTextField5.getText() + "&MOVEQTY=" + jTextField6.getText() + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8") + "&BALANCE=" + bal;
                            WebAPITester.prepareWebCall(getWIPforSelectedData, StaticValues.getHeader(), "");
                        }
                    } */
  
                    if(verifyFirstOrOnlyProcess ( products.get ( jComboBox1.getSelectedIndex () ).FG_ID , processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID ).equals( "firstProcess" )  || verifyFirstOrOnlyProcess ( products.get ( jComboBox1.getSelectedIndex () ).FG_ID , processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID ).equals( "onlyProcess" ))
                            {
                                try{
                                    int value=getUnitFrom(RMID, FGID);
                                    int bal  =( (Integer.parseInt ( jTextField6.getText () )*value) - Integer.parseInt ( jTextField5.getText () ) );
                                    double existing_wip = check_If_WIP_Exists(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID);
                                    
                                   if (jCheckBox_wip.isSelected()  && existing_wip > 0.0 ) {
                                        double   rmforwip = existing_wip / value ;
                                        
                                        int   totalRM =   Integer.parseInt ( jTextField6.getText () ) ;
                                        double actualRM = (  rmforwip - ( totalRM*1.0)) ;
                                        double         actualWIP = 0 ;
                                        actualWIP = (int)actualRM *value ;
                                        System.out.println ( " wip after considering old wip "+ actualWIP );
                                        bal = (int)actualWIP ;
                                    }                                    
                                    
                                        if (existing_wip > 0.0) {
                                            if (jCheckBox_wip.isSelected()) 
                                            {
                                                //   bal = (int)existing_wip ;
                                                if (bal >= 0) {
                                                    addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                            + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                            + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                            + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                            + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                            + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                            + "&FINISHED=" + jTextField5.getText()
                                                            + "&MOVEQTY=" + jTextField6.getText()
                                                            + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                            + "&BALANCE=" + bal;
                                                    WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                                }
                                            } else {
                                                if (bal > 0) {
                                                    bal = bal + (int) existing_wip;
                                                    addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                            + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                            + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                            + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                            + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                            + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                            + "&FINISHED=" + jTextField5.getText()
                                                            + "&MOVEQTY=" + jTextField6.getText()
                                                            + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                            + "&BALANCE=" + bal;
                                                    WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                                }
                                            }

                                        } else if (existing_wip == 0.0 && wip_id != 0) {
                                            addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                    + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                    + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                    + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                    + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                    + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                    + "&FINISHED=" + jTextField5.getText()
                                                    + "&MOVEQTY=" + jTextField6.getText()
                                                    + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                    + "&BALANCE=" + bal;
                                            WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                        } else if ( bal > 0 )  {
                                            String getWIPforSelectedData = "opwip2add?fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                    + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                    + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                    + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                    + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                    + "&FINISHED=" + jTextField5.getText()
                                                    + "&MOVEQTY=" + jTextField6.getText()
                                                    + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                    + "&BALANCE=" + bal;
                                            WebAPITester.prepareWebCall(getWIPforSelectedData, StaticValues.getHeader(), "");
                                        }
                                }catch(Exception e){}
                            }else{
                                int bal  =( (Integer.parseInt ( jTextField6.getText () )) - Integer.parseInt ( jTextField5.getText () ) );
                                double existing_wip = check_If_WIP_Exists(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID);
                                if (existing_wip > 0.0) {
                                    if (jCheckBox_wip.isSelected()) 
                                    {
                                        //   bal = (int)existing_wip ;
                                        if (bal >= 0) {
                                            addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                    + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                    + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                    + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                    + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                    + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                    + "&FINISHED=" + jTextField5.getText()
                                                    + "&MOVEQTY=" + jTextField6.getText()
                                                    + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                    + "&BALANCE=" + bal;
                                            WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                        }
                                    } else {
                                        if (bal > 0) {
                                            bal = bal + (int) existing_wip;
                                            addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                    + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                    + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                    + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                    + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                    + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                    + "&FINISHED=" + jTextField5.getText()
                                                    + "&MOVEQTY=" + jTextField6.getText()
                                                    + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                    + "&BALANCE=" + bal;
                                            WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                        }
                                    }

                                } else if (existing_wip == 0.0 && wip_id != 0) {
                                    addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                            + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                            + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                            + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                            + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                            + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                            + "&FINISHED=" + jTextField5.getText()
                                            + "&MOVEQTY=" + jTextField6.getText()
                                            + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                            + "&BALANCE=" + bal;
                                    WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                } else if ( bal > 0 )  {
                                    String getWIPforSelectedData = "opwip2add?fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                            + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                            + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                            + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                            + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                            + "&FINISHED=" + jTextField5.getText()
                                            + "&MOVEQTY=" + jTextField6.getText()
                                            + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                            + "&BALANCE=" + bal;
                                    WebAPITester.prepareWebCall(getWIPforSelectedData, StaticValues.getHeader(), "");
                                }                                  
                    }
                } catch ( UnsupportedEncodingException e ) {
                    JOptionPane.showMessageDialog ( null , "URL Encoding " + e.getMessage () );
                }

                addToStores ( products.get ( jComboBox1.getSelectedIndex () ).FG_ID , processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID , Integer.parseInt(  jTextField6.getText () )  );
                //loadEntries ();

                t = new Thread () {

                    public void run () {
                        getFreshlist ();
                    }
                };
                t.start ();

            }

            hideDialog ();
            
            resetFields ();
        }
        
    }
    
    //      --------------------------   Rajesh P - 8 July 2019 ------------   START   -----------------------------------------------------------------
    //    New method to check if a record exists in work in progress table against provided part id and process id 
    //      if yes then the existing record should be updated with new work in progress quantity
    //      if no then the new record should be created in work in progress
    
    public double check_If_WIP_Exists(  int part_id,  int process_id  ){
        
        String addEmpAPICall2 = "progressreport?fgid=" + part_id + "&processid=" + process_id;
        String result3 = WebAPITester.prepareWebCall ( addEmpAPICall2 , StaticValues.getHeader () , "" );
        HashMap<String , Object> map3 = new HashMap<String , Object> ();
        JSONObject jObject2 = new JSONObject ( result3 );
        Iterator<?> keys2 = jObject2.keys ();

        while ( keys2.hasNext () ) {
            String key = ( String ) keys2.next ();
            Object value = jObject2.get ( key );
            map3.put ( key , value );
        }

        JSONObject st2 = ( JSONObject ) map3.get ( "data" );
        JSONArray records2 = st2.getJSONArray ( "records" );
        int totalRecords = Integer.parseInt(st2.get( "totalrecords" ).toString() );
        
           for( int k=0; k<records2.length (); k++){
               
                st2 = records2.getJSONObject( k );
                
                String id = st2.get("P_ID").toString();
                if(id != null && ! id.contains("null") )
                {
                    wip_id     = Integer.parseInt(id) ;
                }
                else
                {
                     wip_id = 0;
                }
                
                
                String moveDate="", balence="", process_name="",  product=""  ;
                moveDate            =   st2.get( "MOVEDATE"  ).toString() ;
                balence                 =   st2.get( "balance"  ).toString() ;
                process_name        =   st2.get( "process_name"  ).toString() ;
                product                 =   st2.get( "product"  ).toString() ;
                
                int wip_int  = 0;
                double wip_double = 0.0 ;
                
                if( moveDate.equals(  "null"  )  &&  balence.equals( "null" )  && process_name.equals( "null" )  && product.equals( "null" )   )
                {
                        return 0.0 ;                
                }else if( ! balence.equals(  "null"  ) ) {
                        
                        try{
                            wip_int =  Integer.parseInt(st2.get( "balance" ).toString() );
                            wip_double = wip_int * 1.0 ;
                            return wip_double ;
                        }catch (   NumberFormatException e    ) {
                            wip_double =  Double.parseDouble(st2.get( "balance" ).toString() );
                            return wip_double ;
                        }
                }
           }
        
        return 0.0 ;
    } 
    
   // -----------------------------------------------------   END -------------------------------------------------------------------------------------------------- 
    
          
    
    
    public int getShiftForTime ( String time ) {

        return 0;
    }

    public boolean verifyLastProcess ( int productId , int processId ) {

//        //String findLastProcess =  "SELECT MAX(FPM_ID) FROM FG_PROCESS_MACH_MAP WHERE REF_FG_ID = "+productId+" AND REF_PROCESS_ID = "+processId ;
//        String findLastProcess = "SELECT MAX(FPM_ID) , REF_PROCESS_ID FROM FG_PROCESS_MACH_MAP WHERE REF_FG_ID =  " + productId;
//
//        ResultSet rs = DB_Operations.executeSingle ( findLastProcess );
//
//        try {
//            if ( rs.isBeforeFirst () ) {
//
//                int _processId = rs.getInt ( 2 );
//                int lastProcessInMap = rs.getInt ( 1 );
//
//                if ( processId == _processId ) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//         } catch ( SQLException e ) {
//                System.out.println( "Error "+e.getMessage ());
//                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }finally{
//            try{
//                rs.close() ;
//            }catch(SQLException er){
//                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , er.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , er.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//            }
//        }       
        int lastProcess = 0;

        addEmpAPICall = "processmachmaps?FG_ID=" + productId;
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

        
        emp = records.getJSONObject ( records.length () - 1 );
        lastProcess = Integer.parseInt ( emp.get ( "REF_PROCESS_ID" ).toString () );

        if ( processId == lastProcess ) {
            return true;
        } else {
            return false;
        }

    }

    public String  verifyFirstOrOnlyProcess ( int productId , int processId ) {

        int lastProcess = 0;
        int firstProcess = 0 ;
        int onlyProcess = 0 ;

        String addEmpAPICall127 = "processmachmaps?FG_ID=" + productId;
        String result127 = WebAPITester.prepareWebCall ( addEmpAPICall127 , StaticValues.getHeader () , "" );
        HashMap<String , Object> map = new HashMap<String , Object> ();
        JSONObject jObject = new JSONObject ( result127 );
        Iterator<?> keys = jObject.keys ();

        while ( keys.hasNext () ) {
            String key = ( String ) keys.next ();
            Object value = jObject.get ( key );
            map.put ( key , value );
        }

        JSONObject st = ( JSONObject ) map.get ( "data" );
        JSONArray records ;
        JSONObject record ;
        try
        {
            records = st.getJSONArray ( "records" );
        }catch(  JSONException r )
        {
            record = st.getJSONObject ( "records" );
            //emp = record.getJSONObject ( 0 );
            records = null ;
        }catch(  Exception r )
        {
            System.out.println ( " "+r.getMessage ());
            records = null ;
        }

        if( records != null  ){
            if(   records.length () > 1    ){
                
                emp = records.getJSONObject ( records.length () - 1 );
                lastProcess = Integer.parseInt ( emp.get ( "REF_PROCESS_ID" ).toString () );
                
                
                emp = records.getJSONObject ( 0 );
                firstProcess = Integer.parseInt ( emp.get ( "REF_PROCESS_ID" ).toString () );         
                
            }else  if(   records.length () == 1    ){   
                emp = records.getJSONObject ( 0 );
                onlyProcess = Integer.parseInt ( emp.get ( "REF_PROCESS_ID" ).toString () );         
            }
        }else{
            System.out.println ( "records does not exist " );
        }
        
        if ( processId == onlyProcess ) {
            return "onlyProcess";
        } else if ( processId == lastProcess ) {
            return "lastProcess";
        } else  if ( processId == firstProcess ) {
            return "firstProcess";
        }else{
            return "NA";
        }

    }

    public boolean checkIfEmpty ( String testVal , int a , int b , int c , int d ) {

        boolean formcomplete = true;

        if ( testVal.equalsIgnoreCase ( "" ) || testVal.equalsIgnoreCase ( "0" ) ) {
            formcomplete = false;
        }

        if ( a == 0 ) {
            if ( b == 0 ) {
                formcomplete = false;
            }
        }

        if ( c == 0 ) {
            if ( d == 0 ) {
                formcomplete = false;
            }
        }

        if (  ! formcomplete ) {
            showIncompleteFormDialog ( "<html><b>Incomplete form.</b> <br>Please fill atleast one process data with fromtime, totime and production details</html>" );
        }

        return true;
    }

    public void showIncompleteFormDialog ( String message ) {

        if ( incompleteDataDialog == 0 ) {
            incompleteDataDialog = 1;
            JOptionPane.showMessageDialog ( null , message );
        }
    }

    private void dateChooserCombo1OnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_dateChooserCombo1OnCommit
        // TODO add your handling code here:
    }//GEN-LAST:event_dateChooserCombo1OnCommit

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        HomeScreen.home.removeForms ();
    }//GEN-LAST:event_jButton2MouseClicked

    public File selectedFile = null;
    public boolean mastersMissing = false;


    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:

        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser ();
        fileChooser.setFileFilter ( new MyCustomFilter ( "excel 97-2003" ) );

        fileChooser.setCurrentDirectory ( new File ( System.getProperty ( "user.home" ) ) );

        int result2 = fileChooser.showOpenDialog ( this );

        if ( result2 == JFileChooser.APPROVE_OPTION ) {

            selectedFile = fileChooser.getSelectedFile ();

            FileChannel inputChannel = null;
            FileChannel outputChannel = null;

            File dir = new File ( "dataupload\\" );
            dir.mkdirs ();

            fetchDataFromExcel ();

            try {
                inputChannel = new FileInputStream ( selectedFile ).getChannel ();
                outputChannel = new FileOutputStream ( new File ( dir , selectedFile.getName () ) ).getChannel ();

                outputChannel.transferFrom ( inputChannel , 0 , inputChannel.size () );

                inputChannel.close ();
                outputChannel.close ();

            } catch ( FileNotFoundException fnfe ) {
                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , fnfe.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , fnfe.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            } catch ( IOException ioe ) {
                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , ioe.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ioe.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

        }
    }//GEN-LAST:event_jButton3MouseClicked

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:

//        BOMModel bom = new BOMModel();
//        
//        Double RMQTY, BOMQTY ;
//        
//        try{
//            int empId = employees.get( jComboBox3.getSelectedIndex () ).EMP_ID ;
//            String getSetupDetailsQuery = "select * from dailySetupMaster where employeeId = "+empId;
//            ResultSet rs = DB_Operations.executeSingle ( getSetupDetailsQuery );
//            dSM = new DailySetupModel();
//            dSM = dSM.getDailySetupInstance ( rs ) ;
//            
//            jLabel26.setText( String.valueOf( dSM.getRawMatQty () ) ) ;
//            
//            RMQTY = dSM.getRawMatQty ();
//            rs.close() ;
//            
//            rs = DB_Operations.executeSingle ( "select _RM_ID, TOTAL_WT from BillOfMaterial where _FG_ID = "+products.get ( jComboBox1.getSelectedIndex ()).FG_ID );
//            bom.setRmid ( rs.getInt( "_RM_ID" ));
//            bom.setQty ( rs.getDouble( "TOTAL_WT" )  );
//            BOMQTY = bom.getQty() ;
//            
//            jLabel28.setText(  String.valueOf( RMQTY/BOMQTY ) ) ;
//            
//            rs.close() ;
//            
//        }catch( Exception e  ){
//            
//        } 
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void dateChooserCombo3OnSelectionChange(datechooser.events.SelectionChangedEvent evt) {//GEN-FIRST:event_dateChooserCombo3OnSelectionChange
        // TODO add your handling code here:
    }//GEN-LAST:event_dateChooserCombo3OnSelectionChange

    
    
    ActionListener selectBatch = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {

//            addEmpAPICall = "latestrwamstock?rmid="+ selectedBtch.RMID;
//            result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
//            map = new HashMap<String, Object>();
//            jObject = new JSONObject( result2 );
//            keys = jObject.keys();
//
//            while( keys.hasNext() ){
//                String key = (String)keys.next();
//                Object value = jObject.get ( key ) ; 
//                map.put(key, value);
//            }
//
//            st = (JSONObject) map.get ( "data" );
//            records = st.getJSONArray ( "records");
//
//            emp = null;
//
//                emp = records.getJSONObject ( 0  );
//                if( ! emp.get ( "inward_batch_no" ).toString ().equals ( "null")  ){
//
//                }
            loadUnits();
            if ( jComboBox7.getItemCount () > 0 ) {
                //  jComboBox7.addActionListener ( selectBatch );
                double availbleQty;
                int FGQty, RMID, BTC_ID;
                try{
                    RMBatchDR selectedBtch = batches.get ( jComboBox7.getSelectedIndex () );
        //             BTC_NAME = selectedBtch.BTC_NAME ;
                    availbleQty = Double.parseDouble ( selectedBtch.availbleQty );
                    RMID = selectedBtch.RMID ;
                    FGQty = selectedBtch.FGQty;
                    BTC_ID    = selectedBtch.BTC_ID ;    
                    jLabel26.setText ( String.valueOf ( availbleQty ) );
                    jLabel28.setText ( String.valueOf ( availbleQty * FGQty ) );
                }catch( Exception ee  ){
                    
                }
            }
        }
    };

    public void fetchDataFromExcel () {

        ArrayList<String[]> values = new ArrayList<String[]> ();
        String[] valuesArr = null;
        String url;
        try {

            url = "jdbc:sqlite:" + StaticValues.dbName;
            Connection con = DriverManager.getConnection ( url );
            Statement stm = con.createStatement ();
            PreparedStatement pst;

            FileInputStream fis;
            String query = "INSERT INTO dailyreport3 (  rdate, rtdate,showrdate,showrtdate, "
                    + "shift_id,"
                    + "machineid,"
                    + "empid,"
                    + "fgid,"
                    + "processid,"
                    + "starttime,stoptime, showFromTime,showToTime,"
                    + "act_stp_min,  total_min, actual_min, actual_hours,total_hours,"
                    + " qtyin, "
                    + "qtyout,"
                    + "rejection,"
                    + "batchno,"
                    + "rej_reason,"
                    + "customer_id ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

            pst = con.prepareStatement ( query );
            //fis = new FileInputStream ( new File ( "C:\\Users\\Rajesh\\Desktop\\SM Auto.xls" ) );
            fis = new FileInputStream ( new File ( "dataupload\\" + selectedFile.getName () ) );

            HSSFWorkbook my_xls_workbook = new HSSFWorkbook ( fis );
            HSSFSheet my_worksheet = my_xls_workbook.getSheetAt ( 0 );

            Iterator<Row> rowIterator = my_worksheet.iterator ();

            while ( rowIterator.hasNext () ) {

                valuesArr = new String[ 24 ];
                Row row = rowIterator.next ();

                Cell cell = row.getCell ( 0 );

                try {

                    if ( cell != null ) {

                        if ( cell.getCellType () != Cell.CELL_TYPE_BLANK ) {

                            int date, month;
                            try {

                                cell.setCellType ( Cell.CELL_TYPE_NUMERIC );
                                Cell cell2 = row.getCell ( 1 );
                                cell2.setCellType ( Cell.CELL_TYPE_NUMERIC );

                                date = ( int ) cell.getNumericCellValue ();
                                month = ( int ) cell2.getNumericCellValue ();

                                pst.setString ( 1 , getDate ( date , month ) );
                                pst.setString ( 2 , getDate ( date , month ) );
                                pst.setString ( 3 , getDate_2 ( date , month ) );
                                pst.setString ( 4 , getDate_2 ( date , month ) );

                                valuesArr[ 0 ] = String.valueOf ( getDate ( date , month ) );
                                valuesArr[ 1 ] = String.valueOf ( getDate ( date , month ) );
                                valuesArr[ 2 ] = String.valueOf ( getDate_2 ( date , month ) );
                                valuesArr[ 3 ] = String.valueOf ( getDate_2 ( date , month ) );

                            } catch ( Exception e2 ) {
                                try {

                                    cell.setCellType ( Cell.CELL_TYPE_NUMERIC );
                                    Cell cell2 = row.getCell ( 1 );
                                    cell2.setCellType ( Cell.CELL_TYPE_NUMERIC );

                                    date = Integer.parseInt ( cell.getStringCellValue () );
                                    month = Integer.parseInt ( cell2.getStringCellValue () );

                                    pst.setString ( 1 , getDate ( date , month ) );
                                    pst.setString ( 2 , getDate ( date , month ) );
                                    pst.setString ( 3 , getDate_2 ( date , month ) );
                                    pst.setString ( 4 , getDate_2 ( date , month ) );

                                    valuesArr[ 0 ] = String.valueOf ( getDate ( date , month ) );
                                    valuesArr[ 1 ] = String.valueOf ( getDate ( date , month ) );
                                    valuesArr[ 2 ] = String.valueOf ( getDate_2 ( date , month ) );
                                    valuesArr[ 3 ] = String.valueOf ( getDate_2 ( date , month ) );
                                } catch ( Exception e3 ) {
                                    System.out.println ( "Error " + e2.getMessage () );
                                    StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                                }
                                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                            }

                            try {
                                pst.setInt ( 5 , ( int ) Math.round ( row.getCell ( 2 ).getNumericCellValue () ) );
                                valuesArr[ 4 ] = String.valueOf ( ( int ) Math.round ( row.getCell ( 2 ).getNumericCellValue () ) );
                            } catch ( Exception e1 ) {
                                try {
                                    pst.setString ( 5 , String.valueOf ( ( int ) Math.round ( row.getCell ( 2 ).getNumericCellValue () ) ) );
                                    valuesArr[ 4 ] = String.valueOf ( ( int ) Math.round ( row.getCell ( 2 ).getNumericCellValue () ) );
                                } catch ( Exception e2 ) {
                                    StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                                }
                                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                            }

                            String machine = "";
                            try {
                                machine = String.valueOf ( ( int ) row.getCell ( 3 ).getNumericCellValue () );
                                valuesArr[ 5 ] = String.valueOf ( ( int ) row.getCell ( 3 ).getNumericCellValue () );
                            } catch ( Exception e1 ) {
                                try {
                                    machine = String.valueOf ( ( int ) row.getCell ( 3 ).getNumericCellValue () );
                                    valuesArr[ 5 ] = String.valueOf ( ( int ) row.getCell ( 3 ).getNumericCellValue () );
                                } catch ( Exception e2 ) {
                                    mastersMissing = true;
                                    StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                                }
                            }
                            for ( int i = 0 ; i < machines.size () ; i ++ ) {

                                if ( machines.get ( i ).MC_NAME.equals ( machine ) ) {
                                    pst.setInt ( 6 , machines.get ( i ).MC_ID );
                                    i = machines.size ();
                                }
                            }

                            String employee = "";
                            try {
                                employee = row.getCell ( 4 ).getStringCellValue ();
                            } catch ( Exception e2 ) {
                                mastersMissing = true;
                            }
                            valuesArr[ 6 ] = employee;
                            for ( int i = 0 ; i < employees.size () ; i ++ ) {
                                if ( employees.get ( i ).EMP_NAME.equals ( employee ) ) {
                                    pst.setInt ( 7 , employees.get ( i ).EMP_ID );
                                    i = employees.size ();
                                }
                            }

                            String product = "";
                            try {
                                product = row.getCell ( 5 ).getStringCellValue ();
                            } catch ( Exception e1 ) {
                                try {
                                    product = String.valueOf ( ( long ) row.getCell ( 5 ).getNumericCellValue () );
                                } catch ( Exception e2 ) {
                                    System.out.println ( "" + e2.getMessage () );
                                    mastersMissing = true;
                                    StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                                }
                                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                            }
                            valuesArr[ 7 ] = product;
                            for ( int i = 0 ; i < products.size () ; i ++ ) {

                                if ( products.get ( i ).FG_PART_NO.equals ( product ) ) {
                                    pst.setInt ( 8 , products.get ( i ).FG_ID );
                                    i = products.size ();
                                }
                            }

                            String process = "";
                            try {
                                process = row.getCell ( 6 ).getStringCellValue ();
                            } catch ( Exception e3 ) {
                                try {
                                    process = String.valueOf ( row.getCell ( 6 ).getNumericCellValue () );
                                } catch ( Exception e4 ) {
                                    mastersMissing = true;
                                    StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e4.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e4.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                                }
                                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e3.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e3.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                            }
                            valuesArr[ 8 ] = process;
                            for ( int i = 0 ; i < processes.size () ; i ++ ) {

                                if ( processes.get ( i ).PRC_NAME.equals ( process ) ) {
                                    pst.setInt ( 9 , processes.get ( i ).PRC_ID );
                                    i = processes.size ();
                                }
                            }

                            try {
                                String time1, time2, time3, time4;

                                time1 = sdf1.format ( row.getCell ( 7 ).getDateCellValue () );
                                time2 = sdf1.format ( row.getCell ( 8 ).getDateCellValue () );
                                time3 = sdf1.format ( row.getCell ( 7 ).getDateCellValue () );
                                time4 = sdf1.format ( row.getCell ( 8 ).getDateCellValue () );

                                pst.setString ( 10 , time1 );
                                pst.setString ( 11 , time2 );
                                pst.setString ( 12 , time3 );
                                pst.setString ( 13 , time4 );

                                valuesArr[ 9 ] = time1;
                                valuesArr[ 10 ] = time2;
                                valuesArr[ 11 ] = time3;
                                valuesArr[ 12 ] = time4;
                            } catch ( Exception e1 ) {
                                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                            }

                            int lossinmin = 0;
                            try {
                                lossinmin = ( int ) row.getCell ( 25 ).getNumericCellValue ();
                                pst.setInt ( 14 , lossinmin );
                            } catch ( Exception e1 ) {
                                try {
                                    lossinmin = Integer.parseInt ( row.getCell ( 25 ).getStringCellValue () );
                                    pst.setInt ( 14 , lossinmin );
                                } catch ( Exception e2 ) {
                                    StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                                }
                                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );

                            }
                            valuesArr[ 13 ] = String.valueOf ( lossinmin );

                            int totalTimeinMin = 0;
                            try {
                                totalTimeinMin = ( int ) row.getCell ( 10 ).getNumericCellValue ();
                            } catch ( Exception e1 ) {
                                try {
                                    totalTimeinMin = Integer.parseInt ( row.getCell ( 10 ).getStringCellValue () );
                                } catch ( Exception e2 ) {
                                    StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                                }
                                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );

                            }
                            pst.setInt ( 15 , totalTimeinMin );
                            valuesArr[ 14 ] = String.valueOf ( totalTimeinMin );

                            pst.setInt ( 16 , ( totalTimeinMin - lossinmin ) );
                            valuesArr[ 15 ] = String.valueOf ( ( totalTimeinMin - lossinmin ) );

                            pst.setString ( 17 , ( ( totalTimeinMin - lossinmin ) / 60 ) + "." + ( ( totalTimeinMin - lossinmin ) / 60 ) );
                            valuesArr[ 16 ] = String.valueOf ( ( ( totalTimeinMin - lossinmin ) / 60 ) + "." + ( ( totalTimeinMin - lossinmin ) / 60 ) );

                            pst.setString ( 18 , ( ( totalTimeinMin ) / 60 ) + "." + ( ( totalTimeinMin ) / 60 ) );
                            valuesArr[ 17 ] = String.valueOf ( ( totalTimeinMin ) / 60 ) + "." + ( ( totalTimeinMin ) / 60 );

                            try {

                                int partsIn = ( int ) row.getCell ( 30 ).getNumericCellValue ();
                                pst.setInt ( 19 , partsIn );
                                pst.setInt ( 20 , partsIn );
                                valuesArr[ 18 ] = String.valueOf ( partsIn );
                                valuesArr[ 19 ] = String.valueOf ( partsIn );
                            } catch ( Exception e1 ) {
                                try {

                                    int partsIn = Integer.parseInt ( row.getCell ( 30 ).getStringCellValue () );
                                    pst.setInt ( 19 , partsIn );
                                    pst.setInt ( 20 , partsIn );
                                    valuesArr[ 18 ] = String.valueOf ( partsIn );
                                    valuesArr[ 19 ] = String.valueOf ( partsIn );
                                } catch ( Exception e2 ) {
                                    StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                                }
                                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                            }

                            try {

                                int rejection = Integer.parseInt ( row.getCell ( 31 ).getStringCellValue () );

                                pst.setInt ( 21 , rejection );

                                valuesArr[ 20 ] = String.valueOf ( rejection );
                            } catch ( Exception e1 ) {
                                try {

                                    int rejection = ( int ) row.getCell ( 31 ).getNumericCellValue ();
                                    pst.setInt ( 21 , rejection );
                                    valuesArr[ 20 ] = String.valueOf ( rejection );
                                } catch ( Exception e2 ) {
                                    StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                                }
                                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                            }

                            pst.setInt ( 22 , 0 );
                            pst.setInt ( 23 , 0 );
                            pst.setInt ( 24 , 0 );
                            valuesArr[ 21 ] = String.valueOf ( 0 );
                            valuesArr[ 22 ] = String.valueOf ( 0 );
                            valuesArr[ 23 ] = String.valueOf ( 0 );

                            values.add ( valuesArr );
                            pst.addBatch ();

                        }
                    }
                } catch ( Exception e5 ) {
                    System.out.println ( "Reached end of file" );
                    StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e5.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e5.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }
            }

//            for ( int i = 0 ; i < values.size () ; i ++ ) {
//                String[] val = values.get ( i );
//                for ( int j = 0 ; j < valuesArr.length ; j ++ ) {
//                    System.out.println ( "" + val[ j ] );
//                }
//            }
            int[] totalRecords = new int[ 2 ];
            try {
                totalRecords = pst.executeBatch ();
            } catch ( BatchUpdateException e1 ) {
                totalRecords = e1.getUpdateCounts ();
                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
            System.out.println ( "Total record Inserted " + totalRecords.length );

//            if(totalRecords.length>0){
//                JOptionPane.showMessageDialog( null, totalRecords.length+"  Records inserted ");
//            }
            fis.close ();
            pst.close ();

            dialog = new ConfirmProdDataImport ( HomeScreen.home );
            dialog.setVisible ( true );

        } catch ( SQLException ex ) {

            System.out.println ( "SQL  " + ex.getMessage () );
            //JOptionPane.showMessageDialog(null, "<html>Error Occurred ! Please ensure  that <h3>'Finished Goods', 'Process', 'Employee', 'Machine' & 'Shift' masters</h3> are filled with all necessary entries</html>"  ) ;
            JOptionPane.showMessageDialog ( null , " Error Occurred ! " );
            StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } catch ( IOException ex ) {
            System.out.println ( "IO  " + ex.getMessage () );
            JOptionPane.showMessageDialog ( null , "<html>Error Occurred while reading the content from file.  </html>" );
            StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

    }

    public static ConfirmProdDataImport dialog = null;

    Calendar c1 = Calendar.getInstance ();
    SimpleDateFormat sdf3 = new SimpleDateFormat ( "MMM dd, yyyy" );
    SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" );
    SimpleDateFormat sdf1 = new SimpleDateFormat ( "HH:mm" );

    public String getDate ( int date , int month ) {

        c1.set ( Calendar.DATE , date );

        switch ( month ) {

            case 1:
                c1.set ( Calendar.MONTH , 0 );
                break;
            case 2:
                c1.set ( Calendar.MONTH , 1 );
                break;
            case 3:
                c1.set ( Calendar.MONTH , 2 );
                break;
            case 4:
                c1.set ( Calendar.MONTH , 3 );
                break;
            case 5:
                c1.set ( Calendar.MONTH , 4 );
                break;
            case 6:
                c1.set ( Calendar.MONTH , 5 );
                break;
            case 7:
                c1.set ( Calendar.MONTH , 6 );
                break;
            case 8:
                c1.set ( Calendar.MONTH , 7 );
                break;
            case 9:
                c1.set ( Calendar.MONTH , 8 );
                break;
            case 10:
                c1.set ( Calendar.MONTH , 9 );
                break;
            case 11:
                c1.set ( Calendar.MONTH , 10 );
                break;
            case 12:
                c1.set ( Calendar.MONTH , 11 );
                break;
        }

        c1.set ( Calendar.YEAR , 2018 );
        c1.set ( Calendar.HOUR , 0 );
        c1.set ( Calendar.MINUTE , 0 );

        //   System.out.println ( ""+sdf2.format ( c1.getTime () ) );
        return sdf2.format ( c1.getTime () );
    }

    public String getDate_2 ( int date , int month ) {

        c1.set ( Calendar.DATE , date );

        switch ( month ) {
            case 1:
                c1.set ( Calendar.MONTH , 0 );
                break;
            case 2:
                c1.set ( Calendar.MONTH , 1 );
                break;
            case 3:
                c1.set ( Calendar.MONTH , 2 );
                break;
            case 4:
                c1.set ( Calendar.MONTH , 3 );
                break;
            case 5:
                c1.set ( Calendar.MONTH , 4 );
                break;
            case 6:
                c1.set ( Calendar.MONTH , 5 );
                break;
            case 7:
                c1.set ( Calendar.MONTH , 6 );
                break;
            case 8:
                c1.set ( Calendar.MONTH , 7 );
                break;
            case 9:
                c1.set ( Calendar.MONTH , 8 );
                break;
            case 10:
                c1.set ( Calendar.MONTH , 9 );
                break;
            case 11:
                c1.set ( Calendar.MONTH , 10 );
                break;
            case 12:
                c1.set ( Calendar.MONTH , 11 );
                break;
        }

        c1.set ( Calendar.YEAR , 2018 );

        return sdf3.format ( c1.getTime () );
    }

    FocusListener f = new FocusListener () {

        @Override
        public void focusGained ( FocusEvent e ) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();

            jcb.selectAll ();
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            try {
                int num = Integer.parseInt ( String.valueOf ( jcb.getText ().toString () ) );
                if ( num < 1 || num > 12 ) {
                    jcb.setText ( "0" );
                    //evt.consume ();
                }
            } catch ( NumberFormatException ex1 ) {
                jcb.setText ( "0" );
                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , ex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }
    };

    FocusListener f3 = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            //jcb.setText ( "" );
            jcb.selectAll ();
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To Search Google or type URL
//change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            String x = jcb.getText ().trim ();

            if ( x.equalsIgnoreCase ( "" ) ) {
                x = "0";
            }

            try {
                int num = Integer.parseInt ( String.valueOf ( jcb.getText ().toString () ) );
                if ( num < 0 || num == 0 ) {
                    jcb.setText ( x );
                    jComboBox5.setEnabled ( false );
                } else {
                    jComboBox5.setEnabled ( true );
                }
            } catch ( NumberFormatException ex1 ) {
                jcb.setText ( x );
            }

        }
    };

    FocusListener f4 = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            //jcb.setText ( "" );
            jcb.selectAll ();
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To Search Google or type URL
            //      change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            String x = jcb.getText ().trim ();

            if ( x.equalsIgnoreCase ( "" ) ) {
                x = "0";
            }

            try {
                int num = Integer.parseInt ( String.valueOf ( jcb.getText ().toString () ) );
                if ( num < 0 || num == 0 ) {
                    jcb.setText ( x );
                }
            } catch ( NumberFormatException ex1 ) {
                jcb.setText ( x );
                StaticValues.writer.writeExcel ( DailyReportForm.class.getSimpleName () , DailyReportForm.class.getSimpleName () , ex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

        }
    };
    
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> UOMIN;
    private javax.swing.JComboBox<String> UOMOUT;
    private javax.swing.JComboBox<String> UOMOUT1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private datechooser.beans.DateChooserCombo dateChooserCombo3;
    private datechooser.beans.DateChooserDialog dateChooserDialog1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    public javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    public static javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JCheckBox jCheckBox_wip = new javax.swing.JCheckBox(); 
    
    ArrayList<Integer[]> child_part = null;
    ArrayList<Integer[]> boughtout_rm = null;
    
     int  text = 0 ;
     int total = 0  ;
     int rmRequiredForNewProd ;
    private void jCheckBox_wipMouseClicked(java.awt.event.MouseEvent evt) {                                           
       
        int flag = products.get(jComboBox1.getSelectedIndex()).getAssembled ();
        
        if(jCheckBox_wip.isSelected())
        {
                jTextField6.setEnabled(false) ;
                text = Integer.parseInt(jTextField6.getText());
                rmRequiredForNewProd = Integer.parseInt(jTextField6.getText());
                if (flag == 1) {
                    total =  wip    +  text ;
                }else{
                    total =  ( wip / conversionFactor )   +  text ;
                }                
                jTextField6.setText(""+total);
             
        }
        else
        {
                jTextField6.setEnabled(true) ;
                text = Integer.parseInt(jTextField6.getText());
                if (flag == 1) {
                    total =  wip    -  text ;
                }else{
                    total =     text - (wip / conversionFactor) ;
                }
                jTextField6.setText(""+total);
                rmRequiredForNewProd = total;
        }
    }
    
    FocusListener Qin_focus = new FocusListener() {
      

        @Override
        public void focusGained(FocusEvent e) 
        {
             
              jTextField6.setText("");
              if (!jComboBox1.getSelectedItem().toString().equals("-- Select Part --")) 
              {
                  if(processes.size() != 0)

                  {
                      double as = check_If_WIP_Exists(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID);
                      
                      wip = (int) as;
                     // System.err.println("WIP  "+as);
                    
                  }
              }
        }

        @Override
        public void focusLost(FocusEvent e) 
        {
           String data = jTextField6.getText();
           if(data.isEmpty() || data == null)
           {
                if(jCheckBox_wip.isSelected())
           {
               jTextField6.setText(""+wip);
           }
                else
                {
               jTextField6.setText("0");
                }
           }
           if(jCheckBox_wip.isSelected())
           {
           int total = wip + Integer.parseInt(data);
           jTextField6.setText(""+total);
           }
        }
    };
    

    
    public void childParts() {
        try {
            String apicall = "assembledparts?assembled_id=" + products.get(jComboBox1.getSelectedIndex()).FG_ID;
            result2 = WebAPITester.prepareWebCall(apicall, StaticValues.getHeader(), "");
            if (!result2.contains("not found")) {

                HashMap<String, Object> map = new HashMap<String, Object>();
                JSONObject jObject = new JSONObject(result2);
                Iterator<?> keys = jObject.keys();

                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    Object value = jObject.get(key);
                    map.put(key, value);
                }

                JSONObject st = (JSONObject) map.get("data");
                JSONArray records = st.getJSONArray("records");

                JSONObject emp = null;
                child_part = new ArrayList<>();
                boughtout_rm = new ArrayList<>();
                for (int i = 0; i < records.length(); i++) {
                    emp = records.getJSONObject(i);
                    if (emp.getInt("child_part_id") != 0 && emp.getInt("child_part_qty") != 0) {
                        child_part.add(new Integer[]{emp.getInt("child_part_id"), emp.getInt("child_part_qty")});
                    }
                    if (emp.getInt("boughtout_rmid") != 0 && emp.getInt("boughtout_rmqty") != 0) {
                        boughtout_rm.add(new Integer[]{emp.getInt("boughtout_rmid"), emp.getInt("boughtout_rmqty")});
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        System.out.println ( " Child Parts "+child_part.size() + "   bought out rm "+boughtout_rm.size() );
    }
	
    public void loadDefaultMap_assembled(String selectedFGID) {

        jComboBox4.removeActionListener(loadMachinesProcessesWise);
        jComboBox4.removeAllItems();
        addEmpAPICall = "processmachmaps?FG_ID=" + selectedFGID;
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        if (result2 != null && !result2.contains("not found")) {
            addEmpAPICall = "processmachmaps?FG_ID=" + products.get(jComboBox1.getSelectedIndex()).FG_ID;
            if (!result2.contains("not found")) {
                result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                HashMap<String, Object> map = new HashMap<String, Object>();
                JSONObject jObject = new JSONObject(result2);
                Iterator<?> keys = jObject.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    Object value = jObject.get(key);
                    map.put(key, value);
                }
                JSONObject st = (JSONObject) map.get("data");
                JSONArray records = st.getJSONArray("records");
                JSONObject emp = null;
                processes = new ArrayList<ProcessDR>();
                for (int i = 0; i < records.length(); i++) {
                    emp = records.getJSONObject(i);
                    jComboBox4.addItem(emp.get("PROCESS_NAME").toString());
                    processes.add(new ProcessDR(Integer.parseInt(emp.get("REF_PROCESS_ID").toString()), emp.get("PROCESS_NAME").toString(), Integer.parseInt(emp.get("REF_MCH_ID").toString())));
                }
            }
        }

        if (processes != null && processes.size() > 0) {
            jComboBox4.addActionListener(loadMachinesProcessesWise);
        } else {
            jComboBox4.removeActionListener(loadMachinesProcessesWise);
        }

        if (jComboBox4.getItemCount() == 0) 
        {
            JOptionPane.showMessageDialog(null, "<html>No processes are mapped for selected part. Please create process map for this part to make entries for production</html>");
            jButton1.setEnabled(false);
        } else {
            jButton1.setEnabled(true);
        }

    }
    
    public void addToDailyProductionReport_assembled() 
    {

        if (!jComboBox1.getSelectedItem().toString().equals("-- Select Part --")) 
        {
            if (verifyFirstOrOnlyProcess(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID).equals("firstProcess") || verifyFirstOrOnlyProcess(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID).equals("onlyProcess") ) 
            {
                String check = check_childStockOf_Assembled(Integer.parseInt(jTextField5.getText().toString()));
                System.out.println("Final : "+check);
                if (!check.contains("false")) 
                {
                    addData();
                } else 
                {
                    hideDialog();

                    JOptionPane.showMessageDialog(null, "Not sufficient child parts ..please update the stock");
                }
            } else 
            {
                addData();
            }

        }

    }
  
    public String check_childStockOf_Assembled(int q_out) {
        String APICall;
        ArrayList<Integer> record_id = null;
        int OPENING = 0;
        int RECEIVED = 0, USED = 0;
        int CLOSING = 0;
        int OldOPENING = 0;
        int OldCLOSING = 0;
        boolean recordExist = false;
        String check = null;
        if (child_part.size() != 0) {
            record_id = new ArrayList<>();
            System.err.println("SIZE    " + child_part.size());
            for (int i = 0; i < child_part.size(); i++) {
                int count_Required = q_out * child_part.get(i)[1];
                System.err.println("Needed : " + count_Required);

                APICall = "latestfgstock?fgid=" + child_part.get(i)[0];
                String result2 = WebAPITester.prepareWebCall(APICall, StaticValues.getHeader(), "");

                try {

                    if (!result2.contains("\"101\":\"Report record\\/s not found.\"") && !result2.contains("\"OPENING\":null,\"RECEIVED\":null,\"USED\":null,\"CLOSING\":null")) {

                        HashMap<String, Object> map = new HashMap<String, Object>();
                        JSONObject jObject = new JSONObject(result2);
                        Iterator<?> keys = jObject.keys();

                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            Object value = jObject.get(key);
                            map.put(key, value);
                        }

                        JSONObject st = (JSONObject) map.get("data");
                        JSONArray records = st.getJSONArray("records");
                        JSONObject emp = null;

                        if (records.length() > 0) {
                            recordExist = true;
                            OPENING = Integer.parseInt(records.getJSONObject(0).get("OPENING").toString());
                            RECEIVED = Integer.parseInt(records.getJSONObject(0).get("RECEIVED").toString());
                            USED = Integer.parseInt(records.getJSONObject(0).get("USED").toString());
                            OldCLOSING = Integer.parseInt(records.getJSONObject(0).get("CLOSING").toString());
                            OldOPENING = Integer.parseInt(records.getJSONObject(0).get("OPENING").toString());
                            System.err.println("Available : " + OldCLOSING);
                            record_id.add(Integer.parseInt(records.getJSONObject(0).get("FGStock_TR_ID").toString()));
                            if (OldCLOSING >= count_Required) {
                                if (i == 0) {
                                    check = "true ";
                                } else {
                                    check = check + "true ";
                                }
                            } else {
                                if (i == 0) {
                                    check = "false ";
                                } else {
                                    check = check + "false ";
                                }
                            }
                        } else {
                            System.out.println("No result to show");
                            recordExist = false;
                            OPENING = 0;
                            RECEIVED = 0;
                            USED = 0;
                            CLOSING = 0;
                        }

                    } else {
                        check = check + "false ";
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }

            }
            System.err.println(check);

        }
        if (boughtout_rm.size() > 0) 
        {

            double OPENING_CNT = 0, OPENING1 = 0;
            double RECEIVED_CNT = 0, USED_CNT = 0;
            double CLOSING_CNT = 0;

            double OldOPENING_CNT = 0;
            double OldCLOSING_CNT = 0;
            for (int i = 0; i < boughtout_rm.size(); i++) 
            {
                int count_Required = q_out * boughtout_rm.get(i)[1];
                String addEmpAPICall = "latestrwamstock?rmid=" + boughtout_rm.get(i)[0];
                String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                System.err.println(result2);
                if (!result2.contains("not found") && result2 != null) {

                    HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jObject = new JSONObject(result2);
                    Iterator<?> keys = jObject.keys();

                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Object value = jObject.get(key);
                        map.put(key, value);
                    }

                    JSONObject st = (JSONObject) map.get("data");
                    JSONArray records = st.getJSONArray("records");
                    JSONObject emp;

                    String rm_in_date = "";
                    String batch_in_no = "";
                    String in_uid = "";
                    String p_rate = "";

                    String conversionRate = "";
                    String oldClosing = null;
                    for (int j = 0; j < records.length(); j++) 
                    {
                        emp = records.getJSONObject(j);
                        String opening = emp.get("OPENING_CNT").toString();
                        String received = emp.get("RECEIVED_CNT").toString();
                        String used = emp.get("USED_CNT").toString();
                        String closing = emp.get("CLOSING_CNT").toString();
                        oldClosing = emp.get("CLOSING_CNT").toString();

                        String oldClosing_CNT = emp.get("CLOSING_CNT").toString();
                        System.out.println("" + opening);

                        if(oldClosing.contains("null"))
                        {
                            oldClosing = "0";
                        }
                        OPENING1 = Double.parseDouble(oldClosing);
                        if(oldClosing_CNT.contains("null"))
                        {
                            oldClosing_CNT = "0";
                        }
                        OPENING_CNT = Double.parseDouble(oldClosing_CNT);

                        RECEIVED = 0;
                        CLOSING = OPENING - USED;

                        RECEIVED_CNT = 0;
                        CLOSING_CNT = OPENING_CNT - USED_CNT;
                    }
                    if (Integer.parseInt(oldClosing) >= count_Required) 
                    {
                        check = check + "true ";

                    } else 
                    {
                        check = check + "false ";
                    }
                }

            }
        }
        return check;

    }
    
    public void addData() {
        int totalTimeLossInMinutes = 0;
        int totalRejection = 0;
        double totalTimeLossInHours = 0;

        incompleteDataDialog = 0;

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        Calendar c4 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

        String fromDate = null;
        String toDate = null;

        int fromHr = 0, fromMin = 0, toHr = 0, toMin = 0;

        int selectedShiftid = 0;

        selectedShiftid = confirm.getSelectedShift();

        ArrayList<TimeLossModel> timeLossList = timeLoss.getTimeLoss();
        ArrayList<RejectionModel> rejectionList = rejection.getRejection();

        totalTimeLossInMinutes = 0;
        for (int j = 0; j < timeLossList.size(); j++) {
            totalTimeLossInMinutes = totalTimeLossInMinutes + timeLossList.get(j).getMinutes();
        }

        if (totalTimeLossInMinutes > 720) {
            JOptionPane.showMessageDialog(null, "Maximum time loss in minutes for single shift can be 720 minutes only");
            totalTimeLossInMinutes = 720;
        }

        DecimalFormat df = new DecimalFormat("#.##");
        double hr = 0.0, min = 0.0;
        double hr2 = 0.0, min2 = 0.0;
        if (totalTimeLossInMinutes > 59) {
            hr = (totalTimeLossInMinutes / 60);

            min = Double.parseDouble(df.format(Double.parseDouble(df.format(totalTimeLossInMinutes % 60)) / 60));
        } else {

            hr = Double.parseDouble(df.format(Double.parseDouble(df.format(totalTimeLossInMinutes % 60)) / 60));
        }

        long totalMin;
        double totalHrs;

        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);

        String fromTime = "", toTime = "";
        fromDate = sdf2.format(dateChooserCombo3.getSelectedDate().getTime());
        toDate = sdf2.format(dateChooserCombo1.getSelectedDate().getTime());
        try {

            fromTime = sdf2.format(dateChooserCombo3.getSelectedDate().getTime()) + " " + new SimpleDateFormat("HH:mm").format((Date) new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy").parse(fromTimeSpinner.getValue().toString()));
            toTime = sdf2.format(dateChooserCombo1.getSelectedDate().getTime()) + " " + new SimpleDateFormat("HH:mm").format((Date) new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy").parse(toTimeSpinner.getValue().toString()));
        } catch (ParseException e) {

        }

        LocalDateTime d1 = LocalDateTime.parse(fromTime, formatter);
        LocalDateTime d2 = LocalDateTime.parse(toTime, formatter);
        Duration difference = Duration.between(d1, d2);

        totalMin = difference.toMinutes();

        try {
            fromTime = new SimpleDateFormat("HH:mm:ss").format((Date) new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy").parse(fromTimeSpinner.getValue().toString()));
            toTime = new SimpleDateFormat("HH:mm:ss").format((Date) new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy").parse(toTimeSpinner.getValue().toString()));

            fromHr = Integer.parseInt(fromTime.split(":")[0]);
            toHr = Integer.parseInt(toTime.split(":")[0]);

            fromMin = Integer.parseInt(fromTime.split(":")[1]);
            toMin = Integer.parseInt(toTime.split(":")[1]);

        } catch (ParseException e) {

        }

        if (totalMin > 59) {
            totalHrs = (totalMin / 60);
            min2 = Double.parseDouble(df.format(Double.parseDouble(df.format(totalMin % 60)) / 60));
        } else {
            totalHrs = Double.parseDouble(df.format(Double.parseDouble(df.format(totalMin % 60)) / 60));
        }

        int totalRejections = 0;

        if (fromDate.equalsIgnoreCase(toDate)) {
            if (toHr < fromHr) {
                JOptionPane.showMessageDialog(null, "Invalid 'from' and 'to' time for same day");
            } else if (toHr == fromHr) 
            {
                if (toMin == fromMin) 
                {
                    JOptionPane.showMessageDialog(null, "Invalid date and/or time selection");
                } else 
                {
                    try {
                        String addEmpAPICall = "dailyreportadd?rdate=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                + "&rtdate=" + URLEncoder.encode(toDate + " 00:00:00", "UTF-8")
                                + "&showrdate=" + URLEncoder.encode(new SimpleDateFormat("MMM dd, yyyy").format(dateChooserCombo3.getSelectedDate().getTime()), "UTF-8")
                                + "&showrtdate=" + URLEncoder.encode(new SimpleDateFormat("MMM dd, yyyy").format(dateChooserCombo1.getSelectedDate().getTime()), "UTF-8")
                                + "&fgid=" + URLEncoder.encode(products.get(jComboBox1.getSelectedIndex()).FG_ID + "", "UTF-8")
                                + "&machineid=" + URLEncoder.encode(machines.get(jComboBox2.getSelectedIndex()).MC_ID + "", "UTF-8")
                                + "&processid=" + URLEncoder.encode(processes.get(jComboBox4.getSelectedIndex()).PRC_ID + "", "UTF-8")
                                + "&starttime=" + URLEncoder.encode(fromDate + " " + fromTime, "UTF-8")
                                + "&stoptime=" + URLEncoder.encode(toDate + " " + toTime, "UTF-8")
                                + "&qtyin=" + URLEncoder.encode(jTextField6.getText(), "UTF-8")
                                + "&qtyout=" + URLEncoder.encode(jTextField5.getText() + "", "UTF-8")
                                + "&rejection=" + URLEncoder.encode(jTextField7.getText() + "", "UTF-8")
                                + "&empid=" + URLEncoder.encode(employees.get(jComboBox3.getSelectedIndex()).EMP_ID + "", "UTF-8")
                                + "&showFromTime=" + URLEncoder.encode(fromDate + " " + fromTime + "", "UTF-8")
                                + "&showToTime=" + URLEncoder.encode(toDate + " " + toTime + "", "UTF-8")
                                + "&batchno=" + URLEncoder.encode("0", "UTF-8")
                                + "&rej_reason=" + URLEncoder.encode(rej_reasons.get(jComboBox5.getSelectedIndex()).REJ_ID + "", "UTF-8")
                                + "&shift_id=" + URLEncoder.encode(selectedShiftid + "", "UTF-8")
                                + "&actual_min=" + URLEncoder.encode((totalMin - totalTimeLossInMinutes) + "", "UTF-8")
                                + "&total_min=" + URLEncoder.encode(totalMin + "", "UTF-8")
                                + "&actual_hours=" + URLEncoder.encode(((totalHrs + min2) - (hr + min)) + "", "UTF-8")
                                + "&total_hours=" + URLEncoder.encode((totalHrs + min2) + "", "UTF-8")
                                + "&customer_id=" + URLEncoder.encode(orders.get(jComboBox6.getSelectedIndex()).ORD_ID + "", "UTF-8");
                        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                        JOptionPane.showMessageDialog(null, result2);

                        System.out.println("" + result2);
                        int recordId = iDFromJSON(result2);
                        progress.updateMessage("<html>Production details added to daily production report</htm>");

                        for (int j = 0; j < timeLossList.size(); j++) 
                        {
                            totalTimeLossInMinutes = totalTimeLossInMinutes + timeLossList.get(j).getMinutes();
                            addEmpAPICall = "timelossadd?ref_pID=" + recordId + "&tlrID=" + timeLossList.get(j).getId() + "&tlQty=" + timeLossList.get(j).getMinutes();
                            result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                            JOptionPane.showMessageDialog(null, result2);
                        }

                        for (int j = 0; j < rejectionList.size(); j++) 
                        {
                            totalRejection = totalRejection + rejectionList.get(j).getRejectionForReason();
                            addEmpAPICall = "rejdataadd?ref_pID=" + recordId + "&rrID=" + rejectionList.get(j).getRejectionReasonId() + "&rQty=" + rejectionList.get(j).getRejectionForReason();
                            result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                            JOptionPane.showMessageDialog(null, result2);
                        }
                       
                       
                         int bal = Integer.parseInt(jTextField6.getText()) - Integer.parseInt(jTextField5.getText());
                         
                        double existing_wip = check_If_WIP_Exists(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID);

                        progress.updateMessage("<html>Balence items updated to Work In Progress report</htm>");
                        
                        if (existing_wip > 0.0) {
                                        if (jCheckBox_wip.isSelected()) 
                                        {
                                            //   bal = (int)existing_wip ;
                                            if (bal >= 0) {
                                                addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                        + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                        + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                        + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                        + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                        + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                        + "&FINISHED=" + jTextField5.getText()
                                                        + "&MOVEQTY=" + jTextField6.getText()
                                                        + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                        + "&BALANCE=" + bal;
                                                WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                            }
                                        } else {
                                            if (bal > 0) {
                                                bal = bal + (int) existing_wip;
                                                addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                        + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                        + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                        + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                        + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                        + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                        + "&FINISHED=" + jTextField5.getText()
                                                        + "&MOVEQTY=" + jTextField6.getText()
                                                        + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                        + "&BALANCE=" + bal;
                                                WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                            }
                                        }

                                    } else if (existing_wip == 0.0 && wip_id != 0) {
                                        addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                + "&FINISHED=" + jTextField5.getText()
                                                + "&MOVEQTY=" + jTextField6.getText()
                                                + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                + "&BALANCE=" + bal;
                                        WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                    } else if (bal > 0){
                                        String getWIPforSelectedData = "opwip2add?fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                + "&FINISHED=" + jTextField5.getText()
                                                + "&MOVEQTY=" + jTextField6.getText()
                                                + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                + "&BALANCE=" + bal;
                                        WebAPITester.prepareWebCall(getWIPforSelectedData, StaticValues.getHeader(), "");
                                    }
                         
                    } catch (UnsupportedEncodingException e) 
                    {
                        JOptionPane.showMessageDialog(null, "URL Encoding " + e.getMessage());
                    }
                    addToStores_assembled(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID, Integer.parseInt(jTextField5.getText()));
                    loadEntries();

                    t = new Thread() {

                        public void run() {
                            progress.updateMessage("<html>Refreshing production report table</htm>");
                            getFreshlist();
                        }
                    };
                    t.start();
                }
            } else 
            {

                try {

                    String addEmpAPICall = "dailyreportadd?rdate=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                            + "&rtdate=" + URLEncoder.encode(toDate + " 00:00:00", "UTF-8")
                            + "&showrdate=" + URLEncoder.encode(new SimpleDateFormat("MMM dd, yyyy").format(dateChooserCombo3.getSelectedDate().getTime()), "UTF-8")
                            + "&showrtdate=" + URLEncoder.encode(new SimpleDateFormat("MMM dd, yyyy").format(dateChooserCombo1.getSelectedDate().getTime()), "UTF-8")
                            + "&fgid=" + URLEncoder.encode(products.get(jComboBox1.getSelectedIndex()).FG_ID + "", "UTF-8")
                            + "&machineid=" + URLEncoder.encode(machines.get(jComboBox2.getSelectedIndex()).MC_ID + "", "UTF-8")
                            + "&processid=" + URLEncoder.encode(processes.get(jComboBox4.getSelectedIndex()).PRC_ID + "", "UTF-8")
                            + "&starttime=" + URLEncoder.encode(fromDate + " " + fromTime, "UTF-8")
                            + "&stoptime=" + URLEncoder.encode(toDate + " " + toTime, "UTF-8")
                            + "&qtyin=" + URLEncoder.encode(jTextField6.getText(), "UTF-8")
                            + "&qtyout=" + URLEncoder.encode(jTextField5.getText() + "", "UTF-8")
                            + "&rejection=" + URLEncoder.encode(jTextField7.getText() + "", "UTF-8")
                            + "&empid=" + URLEncoder.encode(employees.get(jComboBox3.getSelectedIndex()).EMP_ID + "", "UTF-8")
                            + "&showFromTime=" + URLEncoder.encode(fromDate + " " + fromTime + "", "UTF-8")
                            + "&showToTime=" + URLEncoder.encode(toDate + " " + toTime + "", "UTF-8")
                            + "&batchno=" + URLEncoder.encode("0", "UTF-8")
                            + "&rej_reason=" + URLEncoder.encode(rej_reasons.get(1).REJ_ID + "", "UTF-8")
                            + "&shift_id=" + URLEncoder.encode(selectedShiftid + "", "UTF-8")
                            + "&actual_min=" + URLEncoder.encode((totalMin - totalTimeLossInMinutes) + "", "UTF-8")
                            + "&total_min=" + URLEncoder.encode(totalMin + "", "UTF-8")
                            + "&actual_hours=" + URLEncoder.encode(((totalHrs + min2) - (hr + min)) + "", "UTF-8")
                            + "&total_hours=" + URLEncoder.encode((totalHrs + min2) + "", "UTF-8")
                            + "&customer_id=" + URLEncoder.encode(orders.get(jComboBox6.getSelectedIndex()).CUS_ID + "", "UTF-8");
                    String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                    JOptionPane.showMessageDialog(null, result2);
                    progress.updateMessage("<html>Production details added to daily production report</htm>");

                    System.out.println("" + result2);

                    int recordId = iDFromJSON(result2);

                    for (int j = 0; j < timeLossList.size(); j++) {
                        totalTimeLossInMinutes = totalTimeLossInMinutes + timeLossList.get(j).getMinutes();
                        addEmpAPICall = "timelossadd?ref_pID=" + recordId + "&tlrID=" + timeLossList.get(j).getId() + "&tlQty=" + timeLossList.get(j).getMinutes();
                        result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                        System.err.println(result2);
                    }
                    JOptionPane.showMessageDialog(null, result2);

                    for (int j = 0; j < rejectionList.size(); j++) {
                        totalRejection = totalRejection + rejectionList.get(j).getRejectionForReason();
                        addEmpAPICall = "rejdataadd?ref_pID=" + recordId + "&rrID=" + rejectionList.get(j).getRejectionReasonId() + "&rQty=" + rejectionList.get(j).getRejectionForReason();
                        result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                        System.err.println(result2);
                    }

                    int bal = Integer.parseInt(jTextField6.getText()) - Integer.parseInt(jTextField5.getText());
                    double existing_wip = check_If_WIP_Exists(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID);

                    progress.updateMessage("<html>Balence items updated to Work In Progress report</htm>"); 
                    
                    if (existing_wip > 0.0) {
                                        if (jCheckBox_wip.isSelected()) 
                                        {
                                            //   bal = (int)existing_wip ;
                                            if (bal >= 0) {
                                                addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                        + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                        + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                        + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                        + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                        + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                        + "&FINISHED=" + jTextField5.getText()
                                                        + "&MOVEQTY=" + jTextField6.getText()
                                                        + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                        + "&BALANCE=" + bal;
                                                WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                            }
                                        } else {
                                            if (bal > 0) {
                                                bal = bal + (int) existing_wip;
                                                addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                        + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                        + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                        + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                        + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                        + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                        + "&FINISHED=" + jTextField5.getText()
                                                        + "&MOVEQTY=" + jTextField6.getText()
                                                        + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                        + "&BALANCE=" + bal;
                                                WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                            }
                                        }

                                    } else if (existing_wip == 0.0 && wip_id != 0) {
                                        addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                + "&FINISHED=" + jTextField5.getText()
                                                + "&MOVEQTY=" + jTextField6.getText()
                                                + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                + "&BALANCE=" + bal;
                                        WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                    } else if (bal > 0){
                                        String getWIPforSelectedData = "opwip2add?fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                + "&FINISHED=" + jTextField5.getText()
                                                + "&MOVEQTY=" + jTextField6.getText()
                                                + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                + "&BALANCE=" + bal;
                                        WebAPITester.prepareWebCall(getWIPforSelectedData, StaticValues.getHeader(), "");
                                    }
                        
                } catch (UnsupportedEncodingException e) {
                    JOptionPane.showMessageDialog(null, "URL Encoding " + e.getMessage());
                }

                addToStores_assembled(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID, Integer.parseInt(jTextField5.getText()));

                //loadEntries ();
                t = new Thread() {

                    public void run() {
                        progress.updateMessage("<html>Refreshing production report table</htm>");
                        getFreshlist();
                    }
                };
                t.start();
            }
        } else {

            try {
                String addEmpAPICall = "dailyreportadd?rdate=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8") + "&rtdate=" + URLEncoder.encode(toDate + " 00:00:00", "UTF-8")
                        + "&showrdate=" + URLEncoder.encode(new SimpleDateFormat("MMM dd, yyyy").format(dateChooserCombo3.getSelectedDate().getTime()), "UTF-8") + "&showrtdate=" + URLEncoder.encode(new SimpleDateFormat("MMM dd, yyyy").format(dateChooserCombo1.getSelectedDate().getTime()), "UTF-8")
                        + "&fgid=" + URLEncoder.encode(products.get(jComboBox1.getSelectedIndex()).FG_ID + "", "UTF-8")
                        + "&machineid=" + URLEncoder.encode(machines.get(jComboBox2.getSelectedIndex()).MC_ID + "", "UTF-8")
                        + "&processid=" + URLEncoder.encode(processes.get(jComboBox4.getSelectedIndex()).PRC_ID + "", "UTF-8")
                        + "&starttime=" + URLEncoder.encode(fromDate + " " + fromTime, "UTF-8")
                        + "&stoptime=" + URLEncoder.encode(toDate + " " + toTime, "UTF-8")
                        + "&qtyin=" + URLEncoder.encode(jTextField6.getText(), "UTF-8")
                        + "&qtyout=" + URLEncoder.encode(jTextField5.getText() + "", "UTF-8")
                        + "&rejection=" + URLEncoder.encode(jTextField7.getText() + "", "UTF-8")
                        + "&empid=" + URLEncoder.encode(employees.get(jComboBox3.getSelectedIndex()).EMP_ID + "", "UTF-8")
                        + "&showFromTime=" + URLEncoder.encode(fromDate + " " + fromTime + "", "UTF-8")
                        + "&showToTime=" + URLEncoder.encode(toDate + " " + toTime + "", "UTF-8")
                        + "&batchno=" + URLEncoder.encode("0", "UTF-8")
                        + "&rej_reason=" + URLEncoder.encode(rej_reasons.get(jComboBox5.getSelectedIndex()).REJ_ID + "", "UTF-8")
                        + "&shift_id=" + URLEncoder.encode(selectedShiftid + "", "UTF-8")
                        + "&actual_min=" + URLEncoder.encode((totalMin - totalTimeLossInMinutes) + "", "UTF-8")
                        + "&total_min=" + URLEncoder.encode(totalMin + "", "UTF-8") + "&actual_hours=" + URLEncoder.encode(((totalHrs + min2) - (hr + min)) + "", "UTF-8") + "&total_hours=" + URLEncoder.encode((totalHrs + min2) + "", "UTF-8") + "&customer_id=" + URLEncoder.encode(orders.get(jComboBox6.getSelectedIndex()).CUS_ID + "", "UTF-8");
                String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

                JOptionPane.showMessageDialog(null, result2);
                System.out.println("" + result2);

                int recordId = iDFromJSON(result2);

                for (int j = 0; j < timeLossList.size(); j++) {
                    totalTimeLossInMinutes = totalTimeLossInMinutes + timeLossList.get(j).getMinutes();
                    addEmpAPICall = "timelossadd?ref_pID=" + recordId + "&tlrID=" + timeLossList.get(j).getId() + "&tlQty=" + timeLossList.get(j).getMinutes();
                    result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                    System.err.println(result2);
                }

                for (int j = 0; j < rejectionList.size(); j++) {
                    totalRejection = totalRejection + rejectionList.get(j).getRejectionForReason();
                    addEmpAPICall = "rejdataadd?ref_pID=" + recordId + "&rrID=" + rejectionList.get(j).getRejectionReasonId() + "&rQty=" + rejectionList.get(j).getRejectionForReason();
                    result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                     System.err.println(result2);
                }

                int bal = Integer.parseInt(jTextField6.getText()) - Integer.parseInt(jTextField5.getText());
                double existing_wip = check_If_WIP_Exists(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID);

                progress.updateMessage("<html>Balence items updated to Work In Progress report</htm>"); 
                
                if (existing_wip > 0.0) {
                                        if (jCheckBox_wip.isSelected()) 
                                        {
                                            //   bal = (int)existing_wip ;
                                            if (bal >= 0) {
                                                addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                        + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                        + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                        + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                        + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                        + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                        + "&FINISHED=" + jTextField5.getText()
                                                        + "&MOVEQTY=" + jTextField6.getText()
                                                        + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                        + "&BALANCE=" + bal;
                                                WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                            }
                                        } else {
                                            if (bal > 0) {
                                                bal = bal + (int) existing_wip;
                                                addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                        + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                        + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                        + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                        + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                        + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                        + "&FINISHED=" + jTextField5.getText()
                                                        + "&MOVEQTY=" + jTextField6.getText()
                                                        + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                        + "&BALANCE=" + bal;
                                                WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                            }
                                        }

                                    } else if (existing_wip == 0.0 && wip_id != 0) {
                                        addEmpAPICall = "opwip2update?P_ID=" + wip_id
                                                + "fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                + "&FINISHED=" + jTextField5.getText()
                                                + "&MOVEQTY=" + jTextField6.getText()
                                                + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                + "&BALANCE=" + bal;
                                        WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                    } else if (bal > 0){
                                        String getWIPforSelectedData = "opwip2add?fgid=" + products.get(jComboBox1.getSelectedIndex()).FG_ID
                                                + "&processid=" + processes.get(jComboBox4.getSelectedIndex()).PRC_ID
                                                + "&empid=" + employees.get(jComboBox3.getSelectedIndex()).EMP_ID
                                                + "&machine=" + machines.get(jComboBox2.getSelectedIndex()).MC_ID
                                                + "&ORDERNO=" + orders.get(jComboBox6.getSelectedIndex()).CUS_ID
                                                + "&FINISHED=" + jTextField5.getText()
                                                + "&MOVEQTY=" + jTextField6.getText()
                                                + "&MOVEDATE=" + URLEncoder.encode(fromDate + " 00:00:00", "UTF-8")
                                                + "&BALANCE=" + bal;
                                        WebAPITester.prepareWebCall(getWIPforSelectedData, StaticValues.getHeader(), "");
                                    }
                         
                         progress.updateMessage("<html>Balence items updated to Work In Progress report</htm>"); 
                        
            } catch (UnsupportedEncodingException e) {
                JOptionPane.showMessageDialog(null, "URL Encoding " + e.getMessage());
            }

            addToStores_assembled(products.get(jComboBox1.getSelectedIndex()).FG_ID, processes.get(jComboBox4.getSelectedIndex()).PRC_ID, Integer.parseInt(jTextField5.getText()));
            //loadEntries ();

            t = new Thread() {

                public void run() {
                    getFreshlist();
                }
            };
            t.start();

        }

        hideDialog();

        resetFields();
    }
  
    public void addToStores_assembled(int productId, int processId, int outqty) 
    {

        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMMyyyy_HH_mm_a");
        String strDate2 = sdf2.format(c2.getTime());
        int recordPK = 0;

        int OPENING = 0;
        int RECEIVED = 0, USED = 0;
        int CLOSING = 0;

        int OldOPENING = 0;
        int OldCLOSING = 0;
        boolean recordExist = false;

        String addEmpAPICall = "latestfgstock?fgid=" + productId;
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

        try {
              //  System.err.println("4831  "+result2);
            if (! result2.contains("\"101\":\"Report record\\/s not found.\"") && !result2.contains(",\"OPENING\":null,\"RECEIVED\":null,\"USED\":null,\"CLOSING\":null,")) 
            {

                HashMap<String, Object> map = new HashMap<String, Object>();
                JSONObject jObject = new JSONObject(result2);
                Iterator<?> keys = jObject.keys();

                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    Object value = jObject.get(key);
                    map.put(key, value);
                }

                JSONObject st = (JSONObject) map.get("data");
                JSONArray records = st.getJSONArray("records");
                JSONObject emp = null;

                if (records.length() > 0) {
                    recordExist = true;
                    OPENING = Integer.parseInt(records.getJSONObject(0).get("OPENING").toString());
                    RECEIVED = Integer.parseInt(records.getJSONObject(0).get("RECEIVED").toString());
                    USED = Integer.parseInt(records.getJSONObject(0).get("USED").toString());
                    OldCLOSING = Integer.parseInt(records.getJSONObject(0).get("CLOSING").toString());
                    OldOPENING = Integer.parseInt(records.getJSONObject(0).get("OPENING").toString());

                    recordPK = Integer.parseInt(records.getJSONObject(0).get("FGStock_TR_ID").toString());
                } else {
                    System.out.println("No result to show");
                    recordExist = false;
                    OPENING = 0;
                    RECEIVED = 0;
                    USED = 0;
                    CLOSING = 0;
                }

                OPENING = OldCLOSING;

                RECEIVED = Integer.parseInt(jTextField5.getText()) - Integer.parseInt(jTextField7.getText());
                USED = 0;
                CLOSING = OPENING + RECEIVED;

                if (verifyFirstOrOnlyProcess(productId, processId).equals("lastProcess")) 
                {
                    String updateStock52 = "fgstockadd?FG_ID=" + productId + "&FG_ITEM_ID=" + productId
                            + "&OPENING=" + OPENING + "&RECEIVED=" + RECEIVED
                            + "&USED=" + USED + "&CLOSING=" + CLOSING
                            + "&CREATED_ON=" + URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00"  , "UTF-8")
                            + "&EDITED_ON=" + URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00"  , "UTF-8")
                            + "&EDITED_BY=" + Proman_User.getEID();
                    String result52 = WebAPITester.prepareWebCall(updateStock52, StaticValues.getHeader(), "");
                    JOptionPane.showMessageDialog(null, result52);
                    
                    
                } else if (verifyFirstOrOnlyProcess(productId, processId).equals("firstProcess")) 
                {
                    if (child_part.size() > 0) 
                    {
                        for (int i = 0; i < child_part.size(); i++) 
                        {
                            int OPENING1, RECEIVED1, USED1, OldCLOSING1 = 0, OldOPENING1, CLOSING1;
                            String addEmpAPICall62 = "latestfgstock?fgid=" + child_part.get(i)[0];
                            String result62 = WebAPITester.prepareWebCall(addEmpAPICall62 , StaticValues.getHeader(), "");

                            try {

                                if (!result62.contains("\"101\":\"Report record\\/s not found.\"") && !result62.contains("\"OPENING\":null,\"RECEIVED\":null,\"USED\":null,\"CLOSING\":null")) {

                                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                                    JSONObject jObject1 = new JSONObject(result62);
                                    Iterator<?> keys1 = jObject1.keys();

                                    while (keys1.hasNext()) {
                                        String key = (String) keys1.next();
                                        Object value = jObject1.get(key);
                                        map1.put(key, value);
                                    }

                                    JSONObject st1 = (JSONObject) map1.get("data");
                                    JSONArray record = st1.getJSONArray("records");
                                    JSONObject emp1 = null;

                                    if (record.length() > 0) {
                                        recordExist = true;
                                        OPENING1 = Integer.parseInt(record.getJSONObject(0).get("OPENING").toString());
                                        RECEIVED1 = Integer.parseInt(record.getJSONObject(0).get("RECEIVED").toString());
                                        USED1 = Integer.parseInt(record.getJSONObject(0).get("USED").toString());
                                        OldCLOSING1 = Integer.parseInt(record.getJSONObject(0).get("CLOSING").toString());
                                        OldOPENING1 = Integer.parseInt(record.getJSONObject(0).get("OPENING").toString());

                                    }

                                    OPENING1 = OldCLOSING1;
                                    RECEIVED1 = 0;
                                    USED1 = outqty * child_part.get(i)[1];
                                    CLOSING1 = OPENING1 - USED1;
                                    String updateStock72 = "fgstockadd?FG_ID=" + +child_part.get(i)[0] + "&FG_ITEM_ID=" + +child_part.get(i)[0]
                                            + "&OPENING=" + OPENING1 + "&RECEIVED=" + RECEIVED1
                                            + "&USED=" + USED1 + "&CLOSING=" + CLOSING1
                                            + "&CREATED_ON=" + URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00", "UTF-8")
                                            + "&EDITED_ON=" + URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00", "UTF-8")
                                            + "&EDITED_BY=" + Proman_User.getEID();
                                    String result72 = WebAPITester.prepareWebCall(updateStock72, StaticValues.getHeader(), "");
                                    JOptionPane.showMessageDialog(null, result72);
                                    //System.err.println(result2);

                                }
                            } catch (Exception e) {
                                System.err.println("12 "+e.getMessage());
                            }

                        }
                    }
                    if (boughtout_rm.size() > 0) 
                    {
                        for (int j = 0; j < boughtout_rm.size(); j++) 
                        {
                            String addEmpAPICall82 = "latestrwamstock?rmid=" + boughtout_rm.get(j)[0];
                            String result82 = WebAPITester.prepareWebCall(addEmpAPICall82, StaticValues.getHeader(), "");

                            if (!result82.contains("not found") && result82 != null) 
                            {

                                HashMap<String, Object> map2 = new HashMap<String, Object>();
                                JSONObject jObject2 = new JSONObject(result82);
                                Iterator<?> keys2 = jObject2.keys();

                                while (keys2.hasNext()) {
                                    String key = (String) keys2.next();
                                    Object value = jObject2.get(key);
                                    map2.put(key, value);
                                }

                                JSONObject st2 = (JSONObject) map2.get("data");
                                JSONArray records2 = st2.getJSONArray("records");
                                JSONObject emp2;

                                String conversionRate = "";
                                int oldClosing = 0 ,opening ,received,used,closing;
                                int OPENING1, OPENING_CNT, RECEIVED_CNT, CLOSING_CNT, USED_CNT = 0;
                                for (int j1 = 0; j1 < records2.length(); j1++) 
                                {
                                    emp2 = records2.getJSONObject(j1);
                                     opening = emp2.getInt("OPENING_CNT");
                                     received = emp2.getInt("RECEIVED_CNT");
                                     used = emp2.getInt("USED_CNT");
                                     closing = emp2.getInt("CLOSING_CNT");
                                    oldClosing = emp2.getInt("CLOSING_CNT");

                                    int oldClosing_CNT = emp2.getInt("CLOSING_CNT");
                                    System.out.println("" + opening);

                                    OPENING1 = oldClosing;
                                    OPENING_CNT = oldClosing_CNT;

                                    RECEIVED = 0;
                                    CLOSING = OPENING - USED;

                                    RECEIVED_CNT = 0;
                                    CLOSING_CNT = OPENING_CNT - USED_CNT;
                                  }
                                   OPENING1 = oldClosing;
                                    received = 0;
                                    USED_CNT = outqty * boughtout_rm.get(j)[1];
                                    CLOSING = OPENING1 - USED_CNT;
                                   String addEmpAPICall29 = "rmstockadd?RMI_ID="+URLEncoder.encode(boughtout_rm.get(j)[0]+"", "UTF-8")
                                                            +"&RM_ITEM_ID="+URLEncoder.encode("0", "UTF-8")
                                                            +"&OPENING="+URLEncoder.encode(OPENING1+"", "UTF-8")
                                                            +"&RECEIVED="+URLEncoder.encode(received+"", "UTF-8")
                                                            +"&USED="+URLEncoder.encode(USED_CNT+"", "UTF-8")
                                                            +"&CLOSING="+URLEncoder.encode(CLOSING+"", "UTF-8")
                                                            +"&CREATED_ON="+URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00"+"", "UTF-8")
                                                            +"&EDITED_ON="+URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00"+"", "UTF-8")+"&EDITED_BY="+URLEncoder.encode(Proman_User.getEID ()+"", "UTF-8")
                                                            +"&ref_batch_id="+URLEncoder.encode( "0", "UTF-8")    
                                                            +"&OPENING_CNT="+URLEncoder.encode(OPENING1+"", "UTF-8")
                                                            +"&RECEIVED_CNT="+URLEncoder.encode(received+"", "UTF-8")
                                                            +"&USED_CNT="+URLEncoder.encode(USED_CNT+"", "UTF-8")
                                                            +"&CLOSING_CNT="+URLEncoder.encode(CLOSING+"", "UTF-8")   ;
                                   String result29 = WebAPITester.prepareWebCall(addEmpAPICall29 , StaticValues.getHeader(), "");
                                   JOptionPane.showMessageDialog(null, result29);
                               //    System.err.println(result2);
                            }
                        }
                    }
                }else if ( verifyFirstOrOnlyProcess(productId, processId).equals("onlyProcess")  ){
                
                    
                        // 			update fg stock
                     String updateStock = "fgstockadd?FG_ID=" + productId + "&FG_ITEM_ID=" + productId
                            + "&OPENING=" + OPENING + "&RECEIVED=" + RECEIVED
                            + "&USED=" + USED + "&CLOSING=" + CLOSING
                            + "&CREATED_ON=" + URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00", "UTF-8")
                            + "&EDITED_ON=" + URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00", "UTF-8")
                            + "&EDITED_BY=" + Proman_User.getEID();
                    String result92 = WebAPITester.prepareWebCall(updateStock, StaticValues.getHeader(), "");
                    JOptionPane.showMessageDialog(null, result92);


                    // update child fg and bought out rm stock
                    if (child_part.size() > 0) 
                    {
                        for (int i = 0; i < child_part.size(); i++) 
                        {
                            int OPENING1, RECEIVED1, USED1, OldCLOSING1 = 0, OldOPENING1, CLOSING1;
                            String addEmpAPICall2 = "latestfgstock?fgid=" + child_part.get(i)[0];
                            String result3 = WebAPITester.prepareWebCall(addEmpAPICall2, StaticValues.getHeader(), "");

                            try {

                                if (!result3.contains("\"101\":\"Report record\\/s not found.\"") && !result3.contains("\"OPENING\":null,\"RECEIVED\":null,\"USED\":null,\"CLOSING\":null")) {

                                    JSONObject jObject2 = new JSONObject(result3);
                                    HashMap<String, Object> map2 = new HashMap<String, Object>();
                                    Iterator<?> keys2 = jObject2.keys();

                                    while (keys2.hasNext()) {
                                        String key = (String) keys2.next();
                                        Object value = jObject2.get(key);
                                        map2.put(key, value);
                                    }

                                    JSONObject st2 = (JSONObject) map2.get("data");
                                    JSONArray record2 = st2.getJSONArray("records");
                                    
                                    if (record2.length() > 0) {
                                        recordExist = true;
                                        OPENING1 = Integer.parseInt(record2.getJSONObject(0).get("OPENING").toString());
                                        RECEIVED1 = Integer.parseInt(record2.getJSONObject(0).get("RECEIVED").toString());
                                        USED1 = Integer.parseInt(record2.getJSONObject(0).get("USED").toString());
                                        OldCLOSING1 = Integer.parseInt(record2.getJSONObject(0).get("CLOSING").toString());
                                        OldOPENING1 = Integer.parseInt(record2.getJSONObject(0).get("OPENING").toString());

                                    }

                                    OPENING1 = OldCLOSING1;
                                    RECEIVED1 = 0;
                                    USED1 = outqty * child_part.get(i)[1];
                                    CLOSING1 = OPENING1 - USED1;
                                    String updateStock2 = "fgstockadd?FG_ID=" + +child_part.get(i)[0] + "&FG_ITEM_ID=" + +child_part.get(i)[0]
                                            + "&OPENING=" + OPENING1 + "&RECEIVED=" + RECEIVED1
                                            + "&USED=" + USED1 + "&CLOSING=" + CLOSING1
                                            + "&CREATED_ON=" + URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00", "UTF-8")
                                            + "&EDITED_ON=" + URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00", "UTF-8")
                                            + "&EDITED_BY=" + Proman_User.getEID();
                                    String result4 = WebAPITester.prepareWebCall(updateStock2, StaticValues.getHeader(), "");
                                    JOptionPane.showMessageDialog(null, result4);
                                    //System.err.println(result2);

                                }
                            } catch (Exception e) {
                                System.err.println("12 "+e.getMessage());
                            }

                        }
                    }
                    if (boughtout_rm.size() > 0) 
                    {
                        for (int j = 0; j < boughtout_rm.size(); j++) 
                        {
                            String addEmpAPICall3 = "latestrwamstock?rmid=" + boughtout_rm.get(j)[0];
                            String result5 = WebAPITester.prepareWebCall(addEmpAPICall3, StaticValues.getHeader(), "");

                            if (!result5.contains("not found") && result5 != null) 
                            {

                                HashMap<String, Object> map2 = new HashMap<String, Object>();
                                JSONObject jObject2 = new JSONObject(result5);
                                Iterator<?> keys2 = jObject2.keys();

                                while (keys2.hasNext()) {
                                    String key = (String) keys2.next();
                                    Object value = jObject2.get(key);
                                    map2.put(key, value);
                                }

                                JSONObject st2 = (JSONObject) map2.get("data");
                                JSONArray records2 = st2.getJSONArray("records");
                                JSONObject emp2;

                                String conversionRate = "";
                                int oldClosing = 0 ,opening ,received,used,closing;
                                int OPENING1, OPENING_CNT, RECEIVED_CNT, CLOSING_CNT, USED_CNT = 0;
                                for (int j1 = 0; j1 < records2.length(); j1++) 
                                {
                                    emp2 = records2.getJSONObject(j1);
                                     opening = emp2.getInt("OPENING_CNT");
                                     received = emp2.getInt("RECEIVED_CNT");
                                     used = emp2.getInt("USED_CNT");
                                     closing = emp2.getInt("CLOSING_CNT");
                                    oldClosing = emp2.getInt("CLOSING_CNT");

                                    int oldClosing_CNT = emp2.getInt("CLOSING_CNT");
                                    System.out.println("" + opening);

                                    OPENING1 = oldClosing;
                                    OPENING_CNT = oldClosing_CNT;

                                    RECEIVED = 0;
                                    CLOSING = OPENING - USED;

                                    RECEIVED_CNT = 0;
                                    CLOSING_CNT = OPENING_CNT - USED_CNT;
                                  }
                                   OPENING1 = oldClosing;
                                    received = 0;
                                    USED_CNT = outqty * boughtout_rm.get(j)[1];
                                    CLOSING = OPENING1 - USED_CNT;
                                  String  addEmpAPICall4 = "rmstockadd?RMI_ID="+URLEncoder.encode(boughtout_rm.get(j)[0]+"", "UTF-8")
                                                            +"&RM_ITEM_ID="+URLEncoder.encode("0", "UTF-8")
                                                            +"&OPENING="+URLEncoder.encode(OPENING1+"", "UTF-8")
                                                            +"&RECEIVED="+URLEncoder.encode(received+"", "UTF-8")
                                                            +"&USED="+URLEncoder.encode(USED_CNT+"", "UTF-8")
                                                            +"&CLOSING="+URLEncoder.encode(CLOSING+"", "UTF-8")
                                                            +"&CREATED_ON="+URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00"+"", "UTF-8")
                                                            +"&EDITED_ON="+URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00"+"", "UTF-8")+"&EDITED_BY="+URLEncoder.encode(Proman_User.getEID ()+"", "UTF-8")
                                                            +"&ref_batch_id="+URLEncoder.encode( "0", "UTF-8")    
                                                            +"&OPENING_CNT="+URLEncoder.encode(OPENING1+"", "UTF-8")
                                                            +"&RECEIVED_CNT="+URLEncoder.encode(received+"", "UTF-8")
                                                            +"&USED_CNT="+URLEncoder.encode(USED_CNT+"", "UTF-8")
                                                            +"&CLOSING_CNT="+URLEncoder.encode(CLOSING+"", "UTF-8")   ;
                                   String result6 = WebAPITester.prepareWebCall(addEmpAPICall4 , StaticValues.getHeader(), "");
                                   JOptionPane.showMessageDialog(null, result6);
                               //    System.err.println(result2);
                            }
                        }
                    }
                    
                }
                
            } else 
            {

                OPENING = 0;

                RECEIVED = Integer.parseInt(jTextField5.getText()) - Integer.parseInt(jTextField7.getText());
               
                USED = 0;
                CLOSING = OPENING + RECEIVED;

                //if ( verifyLastProcess ( productId , processId ) ) {
                if (verifyFirstOrOnlyProcess(productId, processId).equals("lastProcess")) 
                {
                    String addEmpAPICall6 = "fgstockadd?FG_ID=" + productId + "&FG_ITEM_ID=" + productId + "&OPENING=" + OPENING + "&RECEIVED=" + RECEIVED + "&USED=" + USED + "&CLOSING=" + CLOSING +
                            "&CREATED_ON=" + URLEncoder.encode ( new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00" , "UTF-8" ) +
                            "&EDITED_ON=" + URLEncoder.encode ( new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00" , "UTF-8" ) +
                            "&EDITED_BY=" + Proman_User.getEID ();
                    String result7 = WebAPITester.prepareWebCall(addEmpAPICall6 , StaticValues.getHeader(), "");
                    JOptionPane.showMessageDialog(null, result7);
                    
                } else if (verifyFirstOrOnlyProcess(productId, processId).equals("firstProcess")) 
                {
                 if (child_part.size() > 0) 
                    {
                        for (int i = 0; i < child_part.size(); i++) 
                        {
                            int OPENING1, RECEIVED1, USED1, OldCLOSING1 = 0, OldOPENING1, CLOSING1;
                            String addEmpAPICall7 = "latestfgstock?fgid=" + child_part.get(i)[0];
                            String result8  = WebAPITester.prepareWebCall(addEmpAPICall7 , StaticValues.getHeader(), "");
                            //   System.err.println("5043 "+ result2);
                            try {

                                if (!result8.contains("\"101\":\"Report record\\/s not found.\"") && !result8.contains("\"OPENING\":null,\"RECEIVED\":null,\"USED\":null,\"CLOSING\":null")) {

                                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                                    JSONObject jObject1 = new JSONObject(result8);
                                    Iterator<?> keys1 = jObject1.keys();

                                    while (keys1.hasNext()) {
                                        String key = (String) keys1.next();
                                        Object value = jObject1.get(key);
                                        map1.put(key, value);
                                    }

                                    JSONObject st1 = (JSONObject) map1.get("data");
                                    JSONArray record = st1.getJSONArray("records");
                                    JSONObject emp1 = null;

                                    if (record.length() > 0) 
                                    {
                                        recordExist = true;
                                        OPENING1 = Integer.parseInt(record.getJSONObject(0).get("OPENING").toString());
                                        RECEIVED1 = Integer.parseInt(record.getJSONObject(0).get("RECEIVED").toString());
                                        USED1 = Integer.parseInt(record.getJSONObject(0).get("USED").toString());
                                        OldCLOSING1 = Integer.parseInt(record.getJSONObject(0).get("CLOSING").toString());
                                        OldOPENING1 = Integer.parseInt(record.getJSONObject(0).get("OPENING").toString());

                                    }

                                    OPENING1 = OldCLOSING1;
                                    RECEIVED1 = 0;
                                    USED1 = outqty * child_part.get(i)[1];
                                    CLOSING1 = OPENING1 - USED1;
                                    String updateStock14 = "fgstockadd?FG_ID=" + +child_part.get(i)[0] + "&FG_ITEM_ID=" + +child_part.get(i)[0]
                                            + "&OPENING=" + OPENING1 + "&RECEIVED=" + RECEIVED1
                                            + "&USED=" + USED1 + "&CLOSING=" + CLOSING1
                                            + "&CREATED_ON=" + URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00", "UTF-8")
                                            + "&EDITED_ON=" + URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00", "UTF-8")
                                            + "&EDITED_BY=" + Proman_User.getEID();
                                    String result14 = WebAPITester.prepareWebCall(updateStock14 , StaticValues.getHeader(), "");
                                    JOptionPane.showMessageDialog(null, result14);
                               //     System.err.println(result2);
                                }
                            } catch (Exception e) 
                            {
                                System.err.println("11 "+e.getMessage());
                            }

                        }
                    }
                    if (boughtout_rm.size() > 0) 
                    {
                        for (int j = 0; j < boughtout_rm.size(); j++) 
                        {
                            String addEmpAPICall15 = "latestrwamstock?rmid=" + boughtout_rm.get(j)[0];
                            String result15 = WebAPITester.prepareWebCall(addEmpAPICall15 , StaticValues.getHeader(), "");
                            System.err.println(result2);
                            if (!result15.contains("not found") && result15 != null) 
                            {

                                HashMap<String, Object> map2 = new HashMap<String, Object>();
                                JSONObject jObject2 = new JSONObject(result15);
                                Iterator<?> keys2 = jObject2.keys();

                                while (keys2.hasNext()) 
                                {
                                    String key = (String) keys2.next();
                                    Object value = jObject2.get(key);
                                    map2.put(key, value);
                                }

                                JSONObject st2 = (JSONObject) map2.get("data");
                                JSONArray records2 = st2.getJSONArray("records");
                                JSONObject emp2;

                                String conversionRate = "";
                                int oldClosing = 0 ,opening ,received,used,closing;
                                int OPENING1, OPENING_CNT, RECEIVED_CNT, CLOSING_CNT, USED_CNT = 0;
                                for (int j1 = 0; j1 < records2.length(); j1++) 
                                {
                                    emp2 = records2.getJSONObject(j1);
                                     opening = emp2.getInt("OPENING_CNT");
                                     received = emp2.getInt("RECEIVED_CNT");
                                     used = emp2.getInt("USED_CNT");
                                     closing = emp2.getInt("CLOSING_CNT");
                                    oldClosing = emp2.getInt("CLOSING_CNT");

                                    int oldClosing_CNT = emp2.getInt("CLOSING_CNT");
                                    System.out.println("" + opening);

                                    OPENING1 = oldClosing;
                                    OPENING_CNT = oldClosing_CNT;

                                    RECEIVED = 0;
                                    CLOSING = OPENING - USED;

                                    RECEIVED_CNT = 0;
                                    CLOSING_CNT = OPENING_CNT - USED_CNT;
                                  }
                                   OPENING1 = oldClosing;
                                    received = 0;
                                    USED_CNT = outqty * boughtout_rm.get(j)[1];
                                    CLOSING = OPENING1 - USED_CNT;
                                   String addEmpAPICall16 = "rmstockadd?RMI_ID="+URLEncoder.encode(boughtout_rm.get(j)[0]+"", "UTF-8")
                                                            +"&RM_ITEM_ID="+URLEncoder.encode("0", "UTF-8")
                                                            +"&OPENING="+URLEncoder.encode(OPENING1+"", "UTF-8")
                                                            +"&RECEIVED="+URLEncoder.encode(received+"", "UTF-8")
                                                            +"&USED="+URLEncoder.encode(USED_CNT+"", "UTF-8")
                                                            +"&CLOSING="+URLEncoder.encode(CLOSING+"", "UTF-8")
                                                            +"&CREATED_ON="+URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00"+"", "UTF-8")
                                                            +"&EDITED_ON="+URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00"+"", "UTF-8")+"&EDITED_BY="+URLEncoder.encode(Proman_User.getEID ()+"", "UTF-8")
                                                            +"&ref_batch_id="+URLEncoder.encode( "0", "UTF-8")    
                                                            +"&OPENING_CNT="+URLEncoder.encode(OPENING1+"", "UTF-8")
                                                            +"&RECEIVED_CNT="+URLEncoder.encode(received+"", "UTF-8")
                                                            +"&USED_CNT="+URLEncoder.encode(USED_CNT+"", "UTF-8")
                                                            +"&CLOSING_CNT="+URLEncoder.encode(CLOSING+"", "UTF-8")   ;
                                   String result16 = WebAPITester.prepareWebCall(addEmpAPICall16 , StaticValues.getHeader(), "");
                                   JOptionPane.showMessageDialog(null, result16);
                                //   System.err.println(result2);
                            }
                           

                        }
                    }
                }else if ( verifyFirstOrOnlyProcess(productId, processId).equals("onlyProcess")  ){
                
                    
                        // 			update fg stock
                     String updateStock17 = "fgstockadd?FG_ID=" + productId + "&FG_ITEM_ID=" + productId
                            + "&OPENING=" + OPENING + "&RECEIVED=" + RECEIVED
                            + "&USED=" + USED + "&CLOSING=" + CLOSING
                            + "&CREATED_ON=" + URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00", "UTF-8")
                            + "&EDITED_ON=" + URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00", "UTF-8")
                            + "&EDITED_BY=" + Proman_User.getEID();
                    String result17 = WebAPITester.prepareWebCall(updateStock17, StaticValues.getHeader(), "");
                    JOptionPane.showMessageDialog(null, result17);


                    // 		update child fg and bought out rm stock
                    if (child_part.size() > 0) 
                    {
                        for (int i = 0; i < child_part.size(); i++) 
                        {
                            int OPENING1, RECEIVED1, USED1, OldCLOSING1 = 0, OldOPENING1, CLOSING1;
                            String addEmpAPICall18 = "latestfgstock?fgid=" + child_part.get(i)[0];
                            String result18 = WebAPITester.prepareWebCall(addEmpAPICall18 , StaticValues.getHeader(), "");

                            try {

                                if (!result18.contains("\"101\":\"Report record\\/s not found.\"") && !result18.contains("\"OPENING\":null,\"RECEIVED\":null,\"USED\":null,\"CLOSING\":null")) {

                                    HashMap<String, Object> map19 = new HashMap<String, Object>();
                                    JSONObject jObject19 = new JSONObject(result18);
                                    Iterator<?> keys19 = jObject19.keys();

                                    while (keys19.hasNext()) {
                                        String key = (String) keys19.next();
                                        Object value = jObject19.get(key);
                                        map19.put(key, value);
                                    }

                                    JSONObject st19 = (JSONObject) map19.get("data");
                                    JSONArray record19 = st19.getJSONArray("records");
                                    JSONObject emp1 = null;

                                    if (record19.length() > 0) {
                                        recordExist = true;
                                        OPENING1 = Integer.parseInt(record19.getJSONObject(0).get("OPENING").toString());
                                        RECEIVED1 = Integer.parseInt(record19.getJSONObject(0).get("RECEIVED").toString());
                                        USED1 = Integer.parseInt(record19.getJSONObject(0).get("USED").toString());
                                        OldCLOSING1 = Integer.parseInt(record19.getJSONObject(0).get("CLOSING").toString());
                                        OldOPENING1 = Integer.parseInt(record19.getJSONObject(0).get("OPENING").toString());

                                    }

                                    OPENING1 = OldCLOSING1;
                                    RECEIVED1 = 0;
                                    USED1 = outqty * child_part.get(i)[1];
                                    CLOSING1 = OPENING1 - USED1;
                                    String updateStock19 = "fgstockadd?FG_ID=" + +child_part.get(i)[0] + "&FG_ITEM_ID=" + +child_part.get(i)[0]
                                            + "&OPENING=" + OPENING1 + "&RECEIVED=" + RECEIVED1
                                            + "&USED=" + USED1 + "&CLOSING=" + CLOSING1
                                            + "&CREATED_ON=" + URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00", "UTF-8")
                                            + "&EDITED_ON=" + URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00", "UTF-8")
                                            + "&EDITED_BY=" + Proman_User.getEID();
                                    String result19 = WebAPITester.prepareWebCall(updateStock19 , StaticValues.getHeader(), "");
                                    JOptionPane.showMessageDialog(null, result19);
                                    //System.err.println(result2);

                                }
                            } catch (Exception e) {
                                System.err.println("12 "+e.getMessage());
                            }

                        }
                    }
                    if (boughtout_rm.size() > 0) 
                    {
                        for (int j = 0; j < boughtout_rm.size(); j++) 
                        {
                            String addEmpAPICall20 = "latestrwamstock?rmid=" + boughtout_rm.get(j)[0];
                            String result20 = WebAPITester.prepareWebCall(addEmpAPICall20 , StaticValues.getHeader(), "");

                            if (!result20.contains("not found") && result20 != null) 
                            {
                                JSONObject jObject20 = new JSONObject(result20);    
                                HashMap<String, Object> map12 = new HashMap<String, Object>();
                                Iterator<?> keys20 = jObject20.keys();

                                while (keys20.hasNext()) {
                                    String key = (String) keys20.next();
                                    Object value = jObject20.get(key);
                                    map12.put(key, value);
                                }

                                JSONObject st20 = (JSONObject) map12.get("data");
                                JSONArray records20 = st20.getJSONArray("records");
                                JSONObject emp20;

                                String conversionRate = "";
                                int oldClosing = 0 ,opening ,received,used,closing;
                                int OPENING1, OPENING_CNT, RECEIVED_CNT, CLOSING_CNT, USED_CNT = 0;
                                for (int j1 = 0; j1 < records20.length(); j1++) 
                                {
                                    emp20 = records20.getJSONObject(j1);
                                     opening = emp20.getInt("OPENING_CNT");
                                     received = emp20.getInt("RECEIVED_CNT");
                                     used = emp20.getInt("USED_CNT");
                                     closing = emp20.getInt("CLOSING_CNT");
                                    oldClosing = emp20.getInt("CLOSING_CNT");

                                    int oldClosing_CNT = emp20.getInt("CLOSING_CNT");
                                    System.out.println("" + opening);

                                    OPENING1 = oldClosing;
                                    OPENING_CNT = oldClosing_CNT;

                                    RECEIVED = 0;
                                    CLOSING = OPENING - USED;

                                    RECEIVED_CNT = 0;
                                    CLOSING_CNT = OPENING_CNT - USED_CNT;
                                  }
                                   OPENING1 = oldClosing;
                                    received = 0;
                                    USED_CNT = outqty * boughtout_rm.get(j)[1];
                                    CLOSING = OPENING1 - USED_CNT;
                                   String addEmpAPICall21 = "rmstockadd?RMI_ID="+URLEncoder.encode(boughtout_rm.get(j)[0]+"", "UTF-8")
                                                            +"&RM_ITEM_ID="+URLEncoder.encode("0", "UTF-8")
                                                            +"&OPENING="+URLEncoder.encode(OPENING1+"", "UTF-8")
                                                            +"&RECEIVED="+URLEncoder.encode(received+"", "UTF-8")
                                                            +"&USED="+URLEncoder.encode(USED_CNT+"", "UTF-8")
                                                            +"&CLOSING="+URLEncoder.encode(CLOSING+"", "UTF-8")
                                                            +"&CREATED_ON="+URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00"+"", "UTF-8")
                                                            +"&EDITED_ON="+URLEncoder.encode(new SimpleDateFormat ( "yyyy-MM-dd" ).format ( dateChooserCombo3.getSelectedDate ().getTime ()  )+" 00:00:00"+"", "UTF-8")+"&EDITED_BY="+URLEncoder.encode(Proman_User.getEID ()+"", "UTF-8")
                                                            +"&ref_batch_id="+URLEncoder.encode( "0", "UTF-8")    
                                                            +"&OPENING_CNT="+URLEncoder.encode(OPENING1+"", "UTF-8")
                                                            +"&RECEIVED_CNT="+URLEncoder.encode(received+"", "UTF-8")
                                                            +"&USED_CNT="+URLEncoder.encode(USED_CNT+"", "UTF-8")
                                                            +"&CLOSING_CNT="+URLEncoder.encode(CLOSING+"", "UTF-8")   ;
                                  String result25 = WebAPITester.prepareWebCall(addEmpAPICall21, StaticValues.getHeader(), "");
                                   JOptionPane.showMessageDialog(null, result25);
                               //    System.err.println(result2);
                            }
                        }
                    }
                    
                }

            }
        } catch (UnsupportedEncodingException e) 
        {
            System.out.println("" + e.getMessage());
        }

    }
    
    
    int selectedShiftId = 0;
    ArrayList<TimeLossModel> selectedTimeLoss = new ArrayList<TimeLossModel> ();
    ArrayList<RejectionModel> selectedrejections = new ArrayList<RejectionModel> ();

    class ShiftsList extends javax.swing.JPanel {

        ResultSet result = null;

        ArrayList<ShiftDR> shifts = new ArrayList<ShiftDR> ();
        DefaultListModel<String> list = null;

        String selectedShiftStr = "";

        /**
         * Creates new form ProdDataConformationScreen
         */
        public ShiftsList () {
            initComponents ();

            jList1.setVisibleRowCount ( 4 );

            list = new DefaultListModel<> ();

            addEmpAPICall = "shifts";
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

            if (  ! result2.contains ( "not found" ) ) {
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
                shifts = new ArrayList<ShiftDR> ();
                for ( int i = 0 ; i < records.length () ; i ++ ) {

                    emp = records.getJSONObject ( i );
                    ShiftDR sdr1 = new ShiftDR ();
                    sdr1.SHIFT_ID = Integer.parseInt ( emp.get ( "shiftid" ).toString () );
                    sdr1.SHIFT_NAME = emp.get ( "shifttitle" ).toString ();
                    list.addElement ( emp.get ( "shifttitle" ).toString () );
                    shifts.add ( sdr1 );
                }

                jList1.setModel ( list );
            }

            //  jList1.setBounds(10 , 10 , 200, 200);
        }

        public DefaultTableModel buildTableModel ( ResultSet rs )
                throws SQLException {

            ResultSetMetaData metaData = rs.getMetaData ();

            // names of columns
            Vector<String> columnNames = new Vector<String> ();
            int columnCount = metaData.getColumnCount ();
            for ( int column = 1 ; column <= columnCount ; column ++ ) {
                columnNames.add ( metaData.getColumnName ( column ) );
            }

            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
            while ( rs.next () ) {

                Vector<Object> vector = new Vector<Object> ();
                for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {
                    vector.add ( rs.getObject ( columnIndex ) );
                }
                data.add ( vector );
            }

            return new DefaultTableModel ( data , columnNames );

        }

        @SuppressWarnings( "unchecked" )
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents () {

            jScrollPane2 = new CustomScrollPane1 ();
                              jScrollPane2.setVerticalScrollBarPolicy ( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
                  jScrollPane2.setHorizontalScrollBarPolicy ( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
                  
            jList1 = new javax.swing.JList<> ();

            setBackground ( new java.awt.Color ( 255 , 255 , 255 ) );
            setPreferredSize ( new java.awt.Dimension ( 260 , 260 ) );
            setLayout ( null );

            jList1.setModel ( new javax.swing.AbstractListModel<String> () {
                String[] strings = { "Item 1" , "Item 2" , "Item 3" , "Item 4" , "Item 5" };

                public int getSize () {
                    return strings.length;
                }

                public String getElementAt ( int i ) {
                    return strings[ i ];
                }
            } );
            jList1.setVisibleRowCount ( 4 );
            jList1.setFixedCellHeight ( 40 );
            jList1.setSelectionMode ( javax.swing.ListSelectionModel.SINGLE_SELECTION );
            jScrollPane2.setViewportView ( jList1 );

            add ( jScrollPane2 );
            jScrollPane2.setBounds ( 0 , 0 , 160 , 160 );
        }

        public int getSelectedShift () {

            selectedShiftId = shifts.get ( jList1.getSelectedIndex () ).SHIFT_ID;
            selectedShiftStr = shifts.get ( jList1.getSelectedIndex () ).SHIFT_NAME;
            //setShift ( selectedShiftId + "" );

            return selectedShiftId;
        }

        // Variables declaration - do not modify                     
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JList<String> jList1;
        private CustomScrollPane1 jScrollPane2;
        // End of variables declaration                   

    }

    class TimeLossList extends javax.swing.JPanel {

        ResultSet result = null;

        public ArrayList<TimeLossDetailPanel> timeLossList = null;
        private ArrayList<TimeLossModel> timeLossDetailList = new ArrayList<TimeLossModel> ();

        /**
         * Creates new form ProdDataConformationScreen
         */
        public TimeLossList () {
            initComponents ();

            TimeLossReasonList panel = new TimeLossReasonList ();
            panel.setBounds ( 0 , 0 , 260 , 260 );
            panel.setBackground ( Color.white );
            add ( panel );
        }

        public DefaultTableModel buildTableModel ( ResultSet rs )
                throws SQLException {

            ResultSetMetaData metaData = rs.getMetaData ();

            // names of columns
            Vector<String> columnNames = new Vector<String> ();
            int columnCount = metaData.getColumnCount ();
            for ( int column = 1 ; column <= columnCount ; column ++ ) {
                columnNames.add ( metaData.getColumnName ( column ) );
            }

            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
            while ( rs.next () ) {

                Vector<Object> vector = new Vector<Object> ();
                for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {
                    vector.add ( rs.getObject ( columnIndex ) );
                }
                data.add ( vector );
            }

            return new DefaultTableModel ( data , columnNames );

        }

        /**
         * This method is called from within the constructor to initialize the
         * form. WARNING: Do NOT modify this code. The content of this method is
         * always regenerated by the Form Editor.
         */
        @SuppressWarnings( "unchecked" )
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents () {

            jButton1 = new javax.swing.JButton ();
            jButton2 = new javax.swing.JButton ();
            jLabel1 = new javax.swing.JLabel ();

            setBackground ( new java.awt.Color ( 255 , 255 , 255 ) );
            setBorder ( javax.swing.BorderFactory.createTitledBorder ( "" ) );
            setPreferredSize ( new java.awt.Dimension ( 260 , 260 ) );
            setLayout ( null );

        }// </editor-fold>                        

        public ArrayList<TimeLossModel> getTimeLoss () {
            // TODO add your handling code here:
            TimeLossModel tlm = null;

            int totalTimeLoss = 0;

            for ( int i = 0 ; i < timeLossList.size () ; i ++ ) {

                tlm = new TimeLossModel ();
                if ( timeLossList.get ( i ).getMinutes () > 0 ) {
                    tlm.setId ( timeLossList.get ( i ).getTRLid () );
                    tlm.setReason ( timeLossList.get ( i ).getReasonStr () );
                    tlm.setTimelossForReason ( timeLossList.get ( i ).getMinutes () );
                    tlm.setMinutes ( timeLossList.get ( i ).getMinutes () );
                    timeLossDetailList.add ( tlm );
                }
            }

            TimeLossModel tlm2 = null;
            StringBuilder sb = new StringBuilder ();
            for ( int i = 0 ; i < timeLossDetailList.size () ; i ++ ) {
                tlm2 = timeLossDetailList.get ( i );

                sb.append ( tlm2.getId () );
                sb.append ( tlm2.getReason () );
                sb.append ( tlm2.getMinutes () );
                sb.append ( "\n" );
            }

            selectedTimeLoss = timeLossDetailList;
            // setTimeLoss ( selectedTimeLoss );

            return selectedTimeLoss;
        }

        public ArrayList<TimeLossModel> getTimeLossList () {
            return timeLossDetailList;
        }

        // Variables declaration - do not modify                     
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        // End of variables declaration                   

        class TimeLossReasonList extends JPanel {

            private JPanel panel;

            private CustomScrollPane1 scroll;
            private JButton btnAddType;

//    public ArrayList<RMQtyPanel> rmBomList = null ;
            public TimeLossReasonList () {
                // getContentPane().setLayout(new BorderLayout());       

                setLayout ( new BorderLayout () );

                panel = new JPanel ();
                panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
                //  scroll = new CustomScrollPane1 ( panel ,  JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
                  scroll = new CustomScrollPane1 ();
                  scroll.setVerticalScrollBarPolicy ( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
                  scroll.setHorizontalScrollBarPolicy ( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
                  scroll.setViewportView( panel ) ;

                add ( scroll , BorderLayout.CENTER );

                setVisible ( true );

                TimeLossDetailPanel pane = null;

                timeLossList = new ArrayList<TimeLossDetailPanel> ();

                addEmpAPICall = "timeloss_reason";
                result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

                if (  ! result2.contains ( "not found" ) && result2.contains ( "success" ) ) {
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
                    timeloss_reasons = new ArrayList<TimeLoss_Reasons> ();
                    panel.setBounds ( 0 , 0 , 255 , 36 * records.length () );
                    for ( int i = 0 ; i < records.length () ; i ++ ) {

                        emp = records.getJSONObject ( i );

                        timeloss_reasons.add ( new TimeLoss_Reasons ( Integer.parseInt ( emp.get ( "TLR_ID" ).toString () ) , emp.get ( "TLR_DESC" ).toString () ) );
                        pane = new TimeLossDetailPanel ();
                        pane.setBounds ( 0 , ( i * 40 ) , 255 , 36 );
                        pane.setTRLid ( Integer.parseInt ( emp.get ( "TLR_ID" ).toString () ) );
                        pane.setReasonStr ( emp.get ( "TLR_DESC" ).toString () );
                        pane.setMinutes ( 0 );

                        panel.add ( pane );
                        panel.revalidate ();
                        timeLossList.add ( pane );
                    }

                }

            }
        }

    }

    class RejectionsList extends javax.swing.JPanel {

        ResultSet result = null;

        public ArrayList<RejectionDetailPanel> rejectionList = null;
        private ArrayList<RejectionModel> rejectionDetailList = new ArrayList<RejectionModel> ();

        /**
         * Creates new form ProdDataConformationScreen
         */
        public RejectionsList () {
            initComponents ();

            RejectionReasonList panel = new RejectionReasonList ();
            panel.setBounds ( 0 , 0 , 260 , 260 );
            panel.setBackground ( Color.white );
            add ( panel );

        }

        @SuppressWarnings( "unchecked" )
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents () {

            jButton1 = new javax.swing.JButton ();
            jButton2 = new javax.swing.JButton ();
            jLabel1 = new javax.swing.JLabel ();

            setBackground ( new java.awt.Color ( 255 , 255 , 255 ) );
            setBorder ( javax.swing.BorderFactory.createTitledBorder ( "" ) );
            setPreferredSize ( new java.awt.Dimension ( 260 , 260 ) );
            setLayout ( null );

        }// </editor-fold>                        

        public ArrayList<RejectionModel> getRejection () {
            // TODO add your handling code here:
            RejectionModel tlm = null;

            int totalRejection = 0;

            for ( int i = 0 ; i < rejectionList.size () ; i ++ ) {

                tlm = new RejectionModel ();
                if ( rejectionList.get ( i ).getRejectionForReason () > 0 ) {
                    tlm.setRejectionReasonId ( rejectionList.get ( i ).getRejectionReasonId () );
                    tlm.setRejectionReasonStr ( rejectionList.get ( i ).getRejectionReasonStr () );
                    tlm.setRejectionForReason ( rejectionList.get ( i ).getRejectionForReason () );
                    tlm.setTotalRejection ( rejectionList.get ( i ).getTotalRejection () );

                    totalRejection = totalRejection + rejectionList.get ( i ).getRejectionForReason ();

                    //lm.setReason ( rejectionList.get ( i ).getReasonStr () );
                    rejectionDetailList.add ( tlm );
                }
            }

            if ( totalRejection == Integer.parseInt ( jTextField7.getText () ) ) {
                RejectionModel tlm2 = null;
                StringBuilder sb = new StringBuilder ();
                for ( int i = 0 ; i < rejectionDetailList.size () ; i ++ ) {
                    tlm2 = rejectionDetailList.get ( i );

                    sb.append ( tlm2.getRejectionForReason () );
                    sb.append ( tlm2.getRejectionReasonId () );
                    sb.append ( tlm2.getRejectionReasonStr () );
                    sb.append ( "\n" );
                }

                selectedrejections = rejectionDetailList;
                //setrejections (rejectionDetailList );

            } else {

                JOptionPane.showMessageDialog ( null , "Total rejection count cannot be greater than " + Integer.parseInt ( jTextField7.getText () ) );
            }

            return selectedrejections;
        }

        // Variables declaration - do not modify                     
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        // End of variables declaration                   

        class RejectionReasonList extends JPanel {

            private JPanel panel;

            private CustomScrollPane1 scroll;
            private JButton btnAddType;

//    public ArrayList<RMQtyPanel> rmBomList = null ;
            public RejectionReasonList () {
                // getContentPane().setLayout(new BorderLayout());       

                setLayout ( new BorderLayout () );

                panel = new JPanel ();
                panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
                
              //  scroll = new CustomScrollPane1 ( panel ,  JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
                  scroll = new CustomScrollPane1 ();
                                    scroll.setVerticalScrollBarPolicy ( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
                  scroll.setHorizontalScrollBarPolicy ( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
                  scroll.setViewportView( panel ) ;
              
                add ( scroll , BorderLayout.CENTER );

                setVisible ( true );

                RejectionDetailPanel pane = null;

                rejectionList = new ArrayList<RejectionDetailPanel> ();

                addEmpAPICall = "rejreasons";
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    
                    if( ! result2.contains( "not found" )   && result2.contains("success"))
                    {  
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jObject = new JSONObject( result2 );
                    Iterator<?> keys = jObject.keys();

                if (  ! result2.contains ( "not found" ) && result2.contains ( "success" ) ) {
                    map = new HashMap<String , Object> ();
                    jObject = new JSONObject ( result2 );
                    keys = jObject.keys ();

                    while ( keys.hasNext () ) {
                        String key = ( String ) keys.next ();
                        Object value = jObject.get ( key );
                        map.put ( key , value );
                    }

                    JSONObject st = ( JSONObject ) map.get ( "data" );
                    JSONArray records = st.getJSONArray ( "records" );

                    JSONObject emp = null;
                    rej_reasons = new ArrayList<Rejection_Reasons> ();
                    panel.setBounds ( 0 , 0 , 250 , 40 * records.length () );
                    for ( int i = 0 ; i < records.length () ; i ++ ) 
                    {

                        emp = records.getJSONObject ( i );

                        rej_reasons.add ( new Rejection_Reasons ( Integer.parseInt ( emp.get ( "RR_ID" ).toString () ) , emp.get ( "RR_DESC" ).toString () ) );
                        pane = new RejectionDetailPanel ();
                        pane.setBounds ( 0 , ( i * 40 ) , 250 , 40 );
                        pane.setRejectionReasonId ( Integer.parseInt ( emp.get ( "RR_ID" ).toString () ) );
                        pane.setRejectionReasonStr ( emp.get ( "RR_DESC" ).toString () );
                        pane.setRejectionForReason ( 0 );
                        pane.setTotalRejection ( 0 );
                        panel.add ( pane );
                        panel.revalidate ();
                        rejectionList.add ( pane );
                    }

                    }       
                //   System.out.println ( pane.getRmid ()+" "+pane.getRMName ());
            }
        } 
    }
}
}
// class ProductDR {
//
//    public int FG_ID;
//    public String FG_NAME;
//    public String UOM;
//}
//
// class EmployeeDR {
//
//    public int EMP_ID;
//    public String EMP_NAME;
//}
//
// class MachineDR {
//
//    public int MC_ID;
//    public String MC_NAME;
//}
//
// class ProcessDR {
//
//    public int PRC_ID;
//    public String PRC_NAME;
//}
//
// class ShiftDR {
//
//    public int SHIFT_ID;
//    public String SHIFT_NAME;
//}
//
