/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples.reports;

import static trixware.erp.prodezydesktop.examples.HomeScreen.home;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import datechooser.view.WeekDaysStyle;
import eu.hansolo.steelseries.gauges.Radial;
import eu.hansolo.steelseries.tools.GaugeType;
import eu.hansolo.steelseries.tools.LedColor;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.UnsupportedEncodingException;
import static java.lang.Thread.sleep;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.Customer;
import trixware.erp.prodezydesktop.Model.EmployeeDR;
import trixware.erp.prodezydesktop.Model.MachineDR;
import trixware.erp.prodezydesktop.Model.ProcessDR;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.ShiftDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.Export_Print_Utility;
import trixware.erp.prodezydesktop.examples.HomeScreen;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class DynamicDashboard_Oee extends javax.swing.JPanel {

    static ArrayList<ProductDR> products = null;
    ArrayList<EmployeeDR> employees = null;
    static ArrayList<ProcessDR> processes = null;
    ArrayList<MachineDR> machines = null;
    ArrayList<ShiftDR> shifts = null;
    ArrayList<Customer> customer = null;

    String selectAll = "-- select --";

    ProductDR prdr = null;
    EmployeeDR empdr = null;
    ProcessDR prcdr = null;
    MachineDR mcdr = null;
    ShiftDR sftdr = null;
    Customer cust = null;
//HashMap<String, String> entity = null ;

//SimpleDateFormat sdf = null;
    static String fromDate, toDate;

    String reportLabel1 = "Process Wise / ProductWise Report for ";
    String reportLabel2 = "Date Wise report for all processes for product";
    String reportLabel3 = "Date wIse combined report for selected process";
    String reportLabel4 = "Combined report between";

    public static int resIndex = 0;

    static Vector<Vector<Object>> data = null;
    static Vector<String> columnNames = null;
    static Vector<String> columnNames2 = null;
    public static DefaultCategoryDataset dataSet = null , dataSetOee = null;
    static ArrayList<String[]> abc = null;
    static ArrayList<String[]> reportData = null , reportDataOee = null;
    static ChartPanel cp = null  , oee_cp = null;

    Calendar c1 = Calendar.getInstance ();
    Calendar c2 = Calendar.getInstance ();

    int columnCount;

    JFreeChart chart = null;
    JFreeChart oee_chart = null;

    String reportMasterType;
    JScrollPane scroll = null , oee_scroll = null;
    JPanel panel = null , oee_panel = null;
    
    public static String firstChartParameter="";
    public static String secondChartParameter="";
    public static String thirdChartParameter="";
    public static String ReportType="";
    
    final Radial gauge = new Radial();
    
    double oeeValue = 0.0;
    
    /**
     * Creates new form DailyReportForm
     * @param reportMasterType
     */

    public DynamicDashboard_Oee ( String reportMasterType ) {
        initComponents ();
        currentYear = new SimpleDateFormat("yyyy").format ( Calendar.getInstance ().getTime() );
        _months = new String[]{ "April "+currentYear, "May "+currentYear, "June "+currentYear, "July "+currentYear, "August "+currentYear, "September "+currentYear, "October "+currentYear, "November "+currentYear, "December "+currentYear, "January "+nextYear, "February  "+nextYear, "March "+nextYear  };

        jComboBox5.addActionListener(   selectFreq  );

//        showDurationDates(  dateChooserCombo3.getSelectedDate ().getTime () ,  dateChooserCombo4.getSelectedDate ().getTime ()  );
        
        
        //String selection  = jComboBox5.getSelectedItem().toString() ;
        String selection = "Duration" ;
        // 10 90   140 30
        // 10 140 140 30

        if(  selection.equals( "Duration" ) ){
            dateChooserCombo3.setEnabled ( true );
            dateChooserCombo4.setEnabled ( true );
            dateChooserCombo3.setVisible ( true );
            dateChooserCombo4.setVisible ( true );
            jComboBox8.setEnabled(false);
            jLabel5.setVisible ( false);
            jLabel1.setVisible ( true);
            jLabel11.setVisible ( true);
        }else{
            dateChooserCombo3.setEnabled ( false );
            dateChooserCombo4.setEnabled ( false );
            dateChooserCombo3.setVisible ( false );
            dateChooserCombo4.setVisible ( false );
            jLabel1.setVisible ( false);
            jLabel11.setVisible ( false);
            jComboBox8.setEnabled(true);
            jLabel5.setVisible ( true);
        }
        
        //jLabel5.setText ( reportMasterType );
        this.reportMasterType = reportMasterType;

        
        //dateChooserCombo4.setText ( dateChooserCombo3.getText () );
        dateChooserCombo4.setFormat ( DateFormat.MEDIUM );
        dateChooserCombo3.setFormat ( DateFormat.MEDIUM );
        dateChooserCombo4.setWeekStyle ( WeekDaysStyle.NORMAL );
        dateChooserCombo3.setWeekStyle ( WeekDaysStyle.NORMAL );
        dateChooserCombo4.revalidate ();
        dateChooserCombo3.repaint ();

        
        if( StaticValues.getReportTypeNumber () >-1 ){
            jComboBox5.setSelectedIndex ( StaticValues.getReportTypeNumber ());
        }

        
        createOeeChartPanel();
        
        Thread t = new Thread(){
            public void run(){
                while(true){
                    try{   
                        gauge.setValueAnimated(  oeeValue );
                        loadDefaultReport_OEE ();
                        sleep( 60000 ) ;
                    }catch(   InterruptedException e   ){

                    }
            }
            }
        };
        
        t.start( );
        
//        Thread oeeThread = new Thread(){
//           public void run(){
//               try{
//                   while(true){
//                        createOeeChartPanel();
//                        loadDefaultReport_OEE ();
//                        sleep( 60000 );
//                   }
//               }catch(InterruptedException e){
//               }
//           }
//        };
//        oeeThread.start() ;

    }

    public void createOeeChartPanel(){
        
//        dataSetOee = new DefaultCategoryDataset ();
//        for ( int i = 0 ; i < 5 ; i ++ ) {
//            dataSetOee.setValue ( 0 , "OEE" , String.valueOf ( i ) );
//        }
//        
//        oee_chart =         ChartFactory.createBarChart3D (
//                "Title Value" , "xAxis" , "yAxis" ,
//                dataSetOee , PlotOrientation.VERTICAL , true , true , false );
        
        oee_panel = new JPanel ();
        oee_panel.setBackground ( Color.white );
        oee_panel.revalidate ();
        oee_panel.repaint ();

        oee_scroll = new JScrollPane ( oee_panel ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        oee_scroll.setBounds ( 0 , 0 , 250 , 280 );
        oee_scroll.setOpaque ( false );
        oee_scroll.setBackground ( Color.BLACK );
        oee_scroll.setBorder ( BorderFactory.createEmptyBorder ());

//        oee_cp = new ChartPanel ( oee_chart );
//        oee_cp.setPreferredSize ( new java.awt.Dimension ( 390 , 290 ) );
//        oee_cp.setBounds ( 0 , 0 , 390 , 290 );
//        oee_cp.setBackground ( Color.WHITE );
//        
//        try {
//            oee_panel.remove ( oee_cp );
//        } catch ( Exception e ) {
//            System.out.println ( "" );
//        }
//        
//        oee_panel.add ( oee_cp );
        
        gauge.setMaxValue ( 100 );
        gauge.setTitle("OEE");
        gauge.setThreshold ( 25 );
        gauge.setGaugeType ( GaugeType.TYPE2 );
        
        gauge.setLedColor ( LedColor.GREEN_LED );
        gauge.setUnitString("%");
        oee_panel.add(gauge, BorderLayout.CENTER);

        oee_scroll.revalidate ();
        oee_scroll.repaint ();
        add ( oee_scroll );
    }
    public void loadDefaultReport_OEE(){

        
        Calendar c = Calendar.getInstance();
        c.set ( Calendar.DATE , Calendar.getInstance().get (Calendar.DAY_OF_MONTH) )  ;
        dateChooserCombo3.setSelectedDate ( c  );
        c.set ( Calendar.DATE , Calendar.getInstance().get(Calendar.DAY_OF_MONTH) )  ;
        dateChooserCombo4.setSelectedDate ( c  );  
        
        showDurationDates(  dateChooserCombo3.getSelectedDate ().getTime () ,  dateChooserCombo4.getSelectedDate ().getTime ()  );
        
        StaticValues.setReportFromDate(   dateChooserCombo3.getSelectedDate ().getTime ()   ) ;
        StaticValues.setReportToDate    (   dateChooserCombo4.getSelectedDate ().getTime ()   ) ;
        
        StaticValues.setReportTypeNumber ( jComboBox5.getSelectedIndex ());
        StaticValues.setReportTypeString    (jComboBox5.getSelectedItem().toString());
        
        String chartType=chartChoice.getSelectedItem().toString().replace(" ", "");

        
        String reportType = jComboBox5.getSelectedItem ().toString ();
        ReportType=reportType;
        System.out.println ( "Total no of days " + ( ( ( ( ( c2.getTimeInMillis () - c1.getTimeInMillis () ) / 1000 ) / 60 ) / 60 ) / 24 ) );

        int lastProcess;
        float rate, prod_cost;

        data = new Vector<Vector<Object>> ();
        columnNames = new Vector<String> ();
        abc = new ArrayList<String[]> ();
        reportDataOee = new ArrayList<String[]> ();

         sb = new StringBuilder ();
         sb1 = new StringBuilder ();


        sb.append ( StaticValues.OEE );
        sb1.append ( StaticValues.OEECall );


        switch ( reportType ) {
            
            case "Duration":

                Thread t2 = new Thread(){
                    
                    public void run(){
                        
                                try {
                                    sb1.append ( "to_date=" + URLEncoder.encode ( formatToDate_old (  ) , "UTF-8" ) + "&from_date=" + URLEncoder.encode ( formatFromDate_old ( )  , "UTF-8" ) );
                                } catch ( UnsupportedEncodingException e ) {
                                    
                                }
                                sb1.append ( "&freq=daily" );

                                String addEmpAPICall = sb1.toString ();
                                String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                                
                                if ( result2 != null  ) {
                                        
                                        gauge.setValueAnimated(  buildTableModelfromJSON_OEE (result2 , reportType ,  ""  )   );
                                }
                               
                                firstChartParameter=reportType + " " + reportMasterType;
                                secondChartParameter=reportType;
                                thirdChartParameter=reportMasterType;
                                //chart = generateBarChartFromAPIData ( reportType + " " + reportMasterType , reportType , reportMasterType,chartType );
                                //oee_chart = generateBarChartFromAPIData_OEE ( "" , reportType , reportMasterType,chartType );
                               
                            }
                };
            
                t2.start();
                
            break ;
            
        }
    }
    public DecimalFormat df = new DecimalFormat ( "#.##" );
    public JFreeChart generateBarChartFromAPIData_OEE ( String reportTitle , String xAxis , String yAxis,String chartType )     {

        try 
        {
            oee_panel.remove ( oee_cp );
        } catch ( Exception e ) 
        {
            //System.err.println(e);
            //StaticValues.writer.writeExcel (DynamicDashboard_One.class.getSimpleName () , DynamicDashboard_One.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        dataSetOee = new DefaultCategoryDataset ();

        //t.start();
        int count = 0;
        String label = "";
       
        if(chartType.equals("Bar-Chart"))
        {
            
            for ( int i = 0 ; i < reportDataOee.size () ; i ++ ) {
                
                try {
                    dataSetOee.setValue ( Integer.parseInt ( reportDataOee.get ( i )[ 1 ] ) , yAxis, reportDataOee.get ( i )[ 0 ] );
                } catch ( Exception e )
                {
                    try {
                        dataSetOee.setValue ( Float.parseFloat ( df.format ( Float.parseFloat ( reportDataOee.get ( i )[ 1 ] ) ) ) , yAxis , reportDataOee.get ( i )[ 0 ] );
                    } catch ( Exception e2 ) {
                        System.out.println ( ""+e2.getMessage () );
                    }
                }
            }


            oee_chart = ChartFactory.createBarChart3D (reportTitle , xAxis , yAxis ,dataSetOee , PlotOrientation.VERTICAL , true , true , false );
            
        }else if(chartType.equals("Line-Chart"))
        {

            for ( int i = 0 ; i < reportDataOee.size () ; i ++ ) 
            {
                float addQumerative=0;
                try {
                    
                    for(int j=i;j>=0;j--)
                    {                  
                        addQumerative=addQumerative+Integer.parseInt(reportDataOee.get ( j )[ 1 ]);                      
                    }
                    dataSetOee.setValue ( addQumerative , yAxis, reportDataOee.get ( i )[ 0 ] );
                } catch ( Exception e )
                {
                    try {
                        for(int j=i;j>=0;j--)
                        {                          
                            addQumerative=addQumerative+Float.parseFloat ( df.format ( Float.parseFloat ( reportDataOee.get ( j )[ 1 ] ) ) );
                        }
                        dataSetOee.setValue ( addQumerative , yAxis , reportDataOee.get ( i )[ 0 ] );
                    } catch ( Exception e2 ) {
                    }
                }
            }

            oee_chart =   ChartFactory.createLineChart(reportTitle,xAxis,yAxis,dataSetOee,PlotOrientation.VERTICAL,true,true,false);
        }
           
        oee_cp = new ChartPanel ( oee_chart );
        oee_cp.setPreferredSize ( new java.awt.Dimension ( 390 , 290 ) );
        oee_cp.setBounds ( 10 , 10 , 390 , 290 );
        oee_cp.revalidate ();
        oee_cp.repaint ();

        //chart.getCategoryPlot().getRangeAxis().setLowerBound(9.0);
        CategoryPlot plot = ( CategoryPlot ) oee_chart.getPlot ();
        plot.setBackgroundPaint ( Color.WHITE );
        plot.setDomainGridlinePaint ( Color.WHITE );
        plot.setDomainGridlinesVisible ( true );
        plot.setRangeGridlinePaint ( Color.WHITE );
        plot.setRangeGridlinesVisible ( true );
        plot.getRangeAxis().setLabelFont(new Font ( "Times New Roman" , Font.PLAIN , 10 ));
        plot.getRangeAxis().setTickLabelFont ( new Font ( "Times New Roman" , Font.PLAIN , 10 ));
        plot.getDomainAxis().setLabelFont(new Font ( "Times New Roman" , Font.PLAIN , 10 ));
        plot.getDomainAxis().setTickLabelFont ( new Font ( "Times New Roman" , Font.PLAIN , 10 ));
        //CategoryAxis axis = plot.getDomainAxis();
        //axis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 0));

        LegendTitle legend = oee_chart.getLegend();
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setVisible(false);
        
        if ( reportMasterType.equals ( "Cost VS Value" ) || reportMasterType.equals ( "PPM" ) ) {
            ValueMarker marker = new ValueMarker ( 50000 );
            marker.setLabel ( "Reference Value" );
            marker.setLabelTextAnchor ( TextAnchor.CENTER_LEFT );
            marker.setPaint ( Color.WHITE );
            plot.addRangeMarker ( marker );
        }


            CategoryAxis domainAxis = plot.getDomainAxis ();
            domainAxis.setCategoryLabelPositions ( CategoryLabelPositions.UP_45 );

        
        if (chartType.equals("Bar-Chart")) 
        {
           
           
           BarRenderer renderer = ( BarRenderer ) plot.getRenderer ();
            renderer.setBaseItemLabelsVisible(true);
            renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBarPainter ( new StandardBarPainter () );
            renderer.setSeriesPaint(0, Color.decode("#58ACFA"));
            renderer.setSeriesItemLabelFont(1, new Font("Times New Roman", Font.PLAIN, 8));
            renderer.setSeriesItemLabelPaint(0, Color.BLACK);
            renderer.setSeriesItemLabelFont(0, new Font("Times New Roman", Font.PLAIN, 8));
            renderer.setSeriesItemLabelPaint(1, Color.BLACK);

        } else if (chartType.equals("Line-Chart")) 
        {        
            
            LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//        BarRenderer renderer = ( BarRenderer ) plot.getRenderer ();
            renderer.setBaseItemLabelsVisible(true);
            renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setSeriesPaint(1, Color.CYAN);
            renderer.setSeriesPaint(1, Color.MAGENTA);
          //  renderer.setSeriesLinesVisible(0, true);
            renderer.setSeriesItemLabelFont(1, new Font("Times New Roman", Font.PLAIN, 8));
            renderer.setSeriesItemLabelPaint(0, Color.BLACK);
            renderer.setSeriesItemLabelFont(0, new Font("Times New Roman", Font.PLAIN, 8));
            renderer.setSeriesItemLabelPaint(1, Color.BLACK);
            
        }

        //add ( cp );
        oee_panel.add ( oee_cp );
        oee_panel.revalidate ();
        oee_panel.repaint ();

        //   HomeScreen.home.pack ();
        HomeScreen.home.revalidate ();
        HomeScreen.home.repaint ();

        return oee_chart;
    }
    public double buildTableModelfromJSON_OEE ( String employeeJSONList , String reportType , String label ) {
        HashMap<String , Object> map = new HashMap<String , Object> ();
        System.out.println ( "OEE call from dynamic dashboard view result : " );
        System.err.println ( " "+employeeJSONList );
        
          
        
        try{
            JSONObject jObject = new JSONObject ( employeeJSONList );
            Iterator<?> keys = jObject.keys ();
            
            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }


            JSONObject st1 = null;

            try{

                st1 = ( JSONObject ) map.get ( "data" );
                JSONArray records2 = st1.getJSONArray ( "records" );


                columnNames = new Vector<String> ();
                columnNames2 = new Vector<String> ();

                columnNames.add ( "date" );
                columnNames2.add ( "Date" );                        
                columnNames.add ( "oee" );           
                columnNames2.add ( "actual_min" );                        

                for ( counter = 0 ; counter < records2.length () ; counter ++ ) {
                    Vector<Object> vector = new Vector<Object> ();

                    String[] ab = new String[ columnNames.size () ];

                    for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {
                        JSONObject emp2 = records2.getJSONObject ( counter );

                            try {
                                if ( columnIndex == 1 ) {
                                    ab[ 1 ] = df.format(Float.parseFloat ( emp2.get ( columnNames.get ( 1 ) ).toString () )) ;
                                    
                                    oeeValue = Double.parseDouble( emp2.get ( columnNames.get ( 1 ) ).toString () )   ;
                                    
                                } else if(columnIndex==0){
                                    ab[ 0 ] = emp2.get ( columnNames.get ( 0 ) ).toString ();
                                }
                            } catch ( NullPointerException | NumberFormatException r ) {
                                ab[ 1 ] = "-- ";
                            }
                    }

                    reportDataOee.add ( ab );
                }
            }catch( JSONException e ){
                
            }

        } catch ( JSONException | ClassCastException e ) {

                
        }

        return oeeValue ;
    } 

    public JSONObject emp = null;
    public int counter =0 ;
    public JSONArray records = null;
    
     
     Thread t = new Thread () {

        public void run () {

            try {
                sleep ( 500 );
            } catch ( InterruptedException IE1 ) {
            }

            int[] highest1 = new int[ abc.size () ];
            double[] highest2 = new double[ abc.size () ];

            graph[] graphs = new graph[ abc.size () ];

            graph _graph;

            for ( int i = 0 ; i < abc.size () ; i ++ ) {
                try {
                    dataSet.setValue ( 0 , "Asdadsasd" , abc.get ( i )[ 0 ] );
                    if ( Integer.parseInt ( abc.get ( i )[ 1 ] ) > highest1[ i ] ) {
                        highest1[ i ] = Integer.parseInt ( abc.get ( i )[ 1 ] );
                    }

                    chart.getCategoryPlot ().getRangeAxis ().setUpperBound ( Double.parseDouble ( ( highest1[ i ] + ( highest1[ i ] * 0.5 ) ) + "" ) );

                    _graph = new graph ( abc.get ( i ) , "Asdadsasd" , abc.get ( i )[ 0 ] , highest1[ i ] , 0.0 );
                    _graph.start ();

                } catch ( NumberFormatException e ) {
                    dataSet.setValue ( 0.0 , "Asdadsasd" , abc.get ( i )[ 0 ] );
                    if ( Double.parseDouble ( df.format ( Float.parseFloat ( abc.get ( i )[ 1 ] ) ) ) > highest2[ i ] ) {
                        highest2[ i ] = Double.parseDouble ( df.format ( Float.parseFloat ( abc.get ( i )[ 1 ] ) ) );
                    }

                    chart.getCategoryPlot ().getRangeAxis ().setUpperBound ( highest2[ i ] + ( highest1[ i ] * 0.5 ) );

                    _graph = new graph ( abc.get ( i ) , "Asdadsasd" , abc.get ( i )[ 0 ] , 0 , highest2[ i ] );
                    _graph.start ();

                    StaticValues.writer.writeExcel (DynamicDashboard_Oee.class.getSimpleName () , DynamicDashboard_Oee.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

            }

//            for ( int i = 0 ; i < abc.size () ; i ++ ) {
//                int total = 0;
//                int step =  highest1[i]/10 ;
//                int rem = highest1[i]%10  ;
//                try {
//                   for ( int k = 1 ; k <= 10 ;  k++ ) {
//                       total = step * k ;
//                       dataSet.addValue (  step * k , "Asdadsasd" , abc.get ( i )[ 0 ] );
//                        try {                           sleep ( 10*k );
//                        } catch ( InterruptedException IE1 ) {    }
//                    }
//                    dataSet.addValue (  total+rem , "Asdadsasd" , abc.get ( i )[ 0 ] );
//                } catch ( NumberFormatException e ) {
//                     double tota = 0.0 ;
//                     double step1 =  highest2[i]/10 ;
//                     double rem1 = highest2[i]%10  ;
//                    for ( int k = 1 ; k <=10 ;  k++  ) {
//                        tota = step1* k ;
//                        dataSet.addValue ( step1*k , "Asdadsasd" , abc.get ( i )[ 0 ] );
//                         try {       sleep ( 10*k );
//                    } catch ( InterruptedException IE1 ) {  StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , IE1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , IE1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );    }
//                      StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//                    }
//                    dataSet.addValue (  tota+rem1 , "Asdadsasd" , abc.get ( i )[ 0 ] );
//                }
//            }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dateChooserCombo3 = new datechooser.beans.DateChooserCombo();
        jLabel11 = new javax.swing.JLabel();
        dateChooserCombo4 = new datechooser.beans.DateChooserCombo();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jCheckBox4 = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jCheckBox5 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jCheckBox6 = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        chartChoice = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setAutoscrolls(true);
        setMaximumSize(new java.awt.Dimension(1366, 760));
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(400, 250));
        setLayout(null);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(270, 20, 0, 0);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("From Date");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 70, 70, 20);

        dateChooserCombo3.setCalendarBackground(new java.awt.Color(204, 255, 51));
        dateChooserCombo3.setCalendarPreferredSize(new java.awt.Dimension(330, 230));
        jPanel1.add(dateChooserCombo3);
        dateChooserCombo3.setBounds(10, 90, 0, 0);

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("To Date");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(10, 120, 70, 20);

        dateChooserCombo4.setCalendarPreferredSize(new java.awt.Dimension(330, 230));
        jPanel1.add(dateChooserCombo4);
        dateChooserCombo4.setBounds(10, 140, 140, 30);

        jComboBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Duration", "Daily", "Weekly", "Monthly", "Quarterly" }));
        jComboBox5.setNextFocusableComponent(jComboBox1);
        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });
        jPanel1.add(jComboBox5);
        jComboBox5.setBounds(10, 30, 150, 30);

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Part Code");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 180, 0, 0);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox1.setEnabled(false);
        jComboBox1.setNextFocusableComponent(jCheckBox1);
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(10, 200, 0, 0);

        jCheckBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Select All");
        jCheckBox1.setNextFocusableComponent(jComboBox4);
        jCheckBox1.setOpaque(false);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox1);
        jCheckBox1.setBounds(160, 200, 0, 0);

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Process");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(10, 230, 0, 0);

        jCheckBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox2.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox2.setSelected(true);
        jCheckBox2.setText("Select All");
        jCheckBox2.setNextFocusableComponent(jComboBox2);
        jCheckBox2.setOpaque(false);
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox2);
        jCheckBox2.setBounds(160, 250, 0, 0);

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Machine");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(10, 280, 0, 0);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox2.setEnabled(false);
        jComboBox2.setNextFocusableComponent(jCheckBox3);
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox2);
        jComboBox2.setBounds(10, 300, 0, 0);

        jCheckBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox3.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox3.setSelected(true);
        jCheckBox3.setText("Select All");
        jCheckBox3.setNextFocusableComponent(jComboBox3);
        jCheckBox3.setOpaque(false);
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox3);
        jCheckBox3.setBounds(160, 300, 0, 0);

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Employee");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(10, 330, 0, 0);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox3.setEnabled(false);
        jComboBox3.setNextFocusableComponent(jCheckBox4);
        jPanel1.add(jComboBox3);
        jComboBox3.setBounds(10, 350, 0, 0);

        jCheckBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox4.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox4.setSelected(true);
        jCheckBox4.setText("Select All");
        jCheckBox4.setNextFocusableComponent(jButton1);
        jCheckBox4.setOpaque(false);
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox4);
        jCheckBox4.setBounds(160, 350, 0, 0);

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Shift");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(10, 380, 0, 0);

        jComboBox6.setEnabled(false);
        jPanel1.add(jComboBox6);
        jComboBox6.setBounds(10, 400, 0, 0);

        jCheckBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox5.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox5.setSelected(true);
        jCheckBox5.setText("Select All");
        jCheckBox5.setOpaque(false);
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox5);
        jCheckBox5.setBounds(160, 400, 0, 0);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Generate Report");
        jButton1.setOpaque(false);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(10, 485, 120, 35);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Close");
        jButton2.setOpaque(false);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(130, 485, 0, 0);

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Frequency");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(10, 10, 80, 16);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Hide");
        jLabel6.setOpaque(true);
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel6);
        jLabel6.setBounds(200, 10, 0, 0);

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Customer");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(10, 430, 0, 0);

        jComboBox7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox7.setEnabled(false);
        jPanel1.add(jComboBox7);
        jComboBox7.setBounds(10, 450, 0, 0);

        jCheckBox6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox6.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox6.setSelected(true);
        jCheckBox6.setText("Select All");
        jCheckBox6.setOpaque(false);
        jCheckBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox6ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox6);
        jCheckBox6.setBounds(160, 450, 0, 0);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Print");
        jButton3.setOpaque(false);
        jPanel1.add(jButton3);
        jButton3.setBounds(10, 525, 0, 0);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("Export");
        jButton4.setOpaque(false);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(130, 525, 0, 0);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Select Month");
        jLabel5.setPreferredSize(new java.awt.Dimension(70, 30));
        jPanel1.add(jLabel5);
        jLabel5.setBounds(10, 70, 80, 20);

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox8);
        jComboBox8.setBounds(10, 90, 0, 0);

        jComboBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox4.setEnabled(false);
        jComboBox4.setNextFocusableComponent(jCheckBox2);
        jPanel1.add(jComboBox4);
        jComboBox4.setBounds(10, 250, 0, 0);

        add(jPanel1);
        jPanel1.setBounds(0, 0, 0, 0);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Tabular View");
        add(jLabel9);
        jLabel9.setBounds(270, 0, 0, 0);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Graphical View");
        add(jLabel10);
        jLabel10.setBounds(270, 260, 0, 0);

        chartChoice.setFont(new java.awt.Font("Leelawadee UI", 0, 8)); // NOI18N
        chartChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bar-Chart", "Line-Chart" }));
        chartChoice.setNextFocusableComponent(jCheckBox2);
        chartChoice.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chartChoiceItemStateChanged(evt);
            }
        });
        add(chartChoice);
        chartChoice.setBounds(0, 0, 0, 0);
    }// </editor-fold>//GEN-END:initComponents

    SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-MM-dd" );

    public String formatFromDate  (  String _currentYear,  String _nextYear, String _currentMonth) {

        String[] dateIP = dateChooserCombo3.getText ().split ( " " );

        String fromDate = null;

//        if ( dateIP[ 1 ].length () == 3 ) {
//            fromDate = dateIP[ 1 ].substring ( 0 , 2 ) + "-";
//            c1.set ( Calendar.DATE , Integer.parseInt ( dateIP[ 1 ].substring ( 0 , 2 ) ) );
//        } else if ( dateIP[ 1 ].length () == 2 ) {
//            fromDate = "0" + dateIP[ 1 ].substring ( 0 , 1 ) + "-";
//            c1.set ( Calendar.DATE , Integer.parseInt ( dateIP[ 1 ].substring ( 0 , 1 ) ) );
//        }

        //String mon = dateIP[ 0 ];
        String mon = _currentMonth.split ( " ")[0] ;

        switch ( mon ) {

            case "January " :
                fromDate = fromDate + "01-";
                c1.set ( Calendar.MONTH , 0 );
                break;
            case "February":
                fromDate = fromDate + "02-";
                c1.set ( Calendar.MONTH , 1 );
                break;
            case "March":
                fromDate = fromDate + "03-";
                c1.set ( Calendar.MONTH , 2 );
                break;
            case "April":
                fromDate = fromDate + "04-";
                c1.set ( Calendar.MONTH , 3 );
                break;
            case "May":
                fromDate = fromDate + "05-";
                c1.set ( Calendar.MONTH , 4 );
                break;
            case "June":
                fromDate = fromDate + "06-";
                c1.set ( Calendar.MONTH , 5 );
                break;
            case "July":
                fromDate = fromDate + "07-";
                c1.set ( Calendar.MONTH , 6 );
                break;
            case "August":
                fromDate = fromDate + "08-";
                c1.set ( Calendar.MONTH , 7 );
                break;
            case "Sepember":
                fromDate = fromDate + "09-";
                c1.set ( Calendar.MONTH , 8 );
                break;
            case "October":
                fromDate = fromDate + "10-";
                c1.set ( Calendar.MONTH , 9 );
                break;
            case "November":
                fromDate = fromDate + "11-";
                c1.set ( Calendar.MONTH , 10 );
                break;
            case "December":
                fromDate = fromDate + "12-";
                c1.set ( Calendar.MONTH , 11 );
                break;

        }

        if( mon.equals( "January"  ) || mon.equals( "February"  ) || mon.equals( "March"  ) ){
            // fromDate = fromDate + dateIP[2];
            c1.set ( Calendar.YEAR , Integer.parseInt ( nextYear ) );
        }else{
            c1.set ( Calendar.YEAR , Integer.parseInt ( _currentYear ) );
        }
        
        
        fromDate = sdf2.format ( c1.getTime () ) + " 00:00:00";

        c1.set ( Calendar.DATE, 1) ;
        
        SimpleDateFormat sdf = new SimpleDateFormat ( "EEEE" );
        SimpleDateFormat sdf1 = new SimpleDateFormat ( "MMM" );
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy" );
        SimpleDateFormat sdf3 = new SimpleDateFormat ( "dd" );

        //return sdf2.format(  dateChooserCombo3.getSelectedDate ().getTime () );
        return fromDate;
    }

    public String formatToDate (   String _currentYear, String _nextYear, String _currentMonth   )  {

        String[] dateIP2 = dateChooserCombo4.getText ().split ( " " );
        String toDate = null;

//        if ( dateIP2[ 1 ].length () == 3 ) {
//            toDate = dateIP2[ 1 ].substring ( 0 , 2 ) + "-";
//            c2.set ( Calendar.DATE , Integer.parseInt ( dateIP2[ 1 ].substring ( 0 , 2 ) ) );
//        } else if ( dateIP2[ 1 ].length () == 2 ) {
//            toDate = "0" + dateIP2[ 1 ].substring ( 0 , 1 ) + "-";
//            c2.set ( Calendar.DATE , Integer.parseInt ( dateIP2[ 1 ].substring ( 0 , 1 ) ) );
//        }



        //String mon2 = dateIP2[ 0 ];
        String mon2 =   _currentMonth.split ( " ")[0] ; 

        switch ( mon2 ) {

            case "January":
                toDate = toDate + "01-";
                c2.set ( Calendar.MONTH , 0 );
                break;
            case "February":
                toDate = toDate + "02-";
                c2.set ( Calendar.MONTH , 1 );
                break;
            case "March":
                toDate = toDate + "03-";
                c2.set ( Calendar.MONTH , 2 );
                break;
            case "April":
                toDate = toDate + "04-";
                c2.set ( Calendar.MONTH , 3 );
                break;
            case "May":
                toDate = toDate + "05-";
                c2.set ( Calendar.MONTH , 4 );
                break;
            case "June":
                toDate = toDate + "06-";
                c2.set ( Calendar.MONTH , 5 );
                break;
            case "July":
                toDate = toDate + "07-";
                c2.set ( Calendar.MONTH , 6 );
                break;
            case "August":
                toDate = toDate + "08-";
                c2.set ( Calendar.MONTH , 7 );
                break;
            case "September":
                toDate = toDate + "09-";
                c2.set ( Calendar.MONTH , 8 );
                break;
            case "October":
                toDate = toDate + "10-";
                c2.set ( Calendar.MONTH , 9 );
                break;
            case "November":
                toDate = toDate + "11-";
                c2.set ( Calendar.MONTH , 10 );
                break;
            case "December":
                toDate = toDate + "12-";
                c2.set ( Calendar.MONTH , 11 );
                break;

        }

        //toDate = toDate + dateIP2[2];
        //c2.set ( Calendar.YEAR , Integer.parseInt ( dateIP2[ 2 ] ) );
        if( mon2.equals( "January"  ) || mon2.equals( "February"  ) || mon2.equals( "March"  ) ){
            // fromDate = fromDate + dateIP[2];
            c2.set ( Calendar.YEAR , Integer.parseInt ( nextYear ) );
        }else{
            c2.set ( Calendar.YEAR , Integer.parseInt ( _currentYear ) );
        }
        
        c2.set ( Calendar.DATE , Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH) );
        
        toDate = sdf2.format ( c2.getTime () ) + " 00:00:00";

        c2.setFirstDayOfWeek ( Calendar.SUNDAY );
        //   System.out.println( "This is #"+  c2.get ( Calendar.WEEK_OF_MONTH) +" week in the month");
        //  System.out.println( "This is #"+  (c2.get ( Calendar.MONTH) +1)+" month in the year");

        return toDate;
        //return sdf2.format(  dateChooserCombo4.getSelectedDate ().getTime () );
    }
    
  

    
    StringBuilder sb = new StringBuilder ();
    
    StringBuilder sb1 = new StringBuilder ();
    
    String process = "", product = "", machine = "", employee = "", shift = "", customerid = "";
    
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked

    }//GEN-LAST:event_jButton1MouseClicked

   
    


    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed

    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        home.removeForms ();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
    
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jCheckBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox6ActionPerformed
    }//GEN-LAST:event_jCheckBox6ActionPerformed

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        File f = new File ( "Printed Reports" );//C:\Users\WIN7\Desktop
        f.mkdir ();   
        Export_Print_Utility.writeChartToPDF ( chart , 800 , 400 , data , columnNames , "Printed Reports\\" + reportMasterType + ".pdf" );
    }//GEN-LAST:event_jButton4MouseClicked

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void chartChoiceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chartChoiceItemStateChanged
  /*     try{
        String chartType=chartChoice.getSelectedItem().toString().replace(" ", "");
        String reportType=ReportType;
        switch ( reportType ) {
            
            case "Duration":
                if (reportMasterType.equals("Cost VS Value")) {
                    chart = generateBarChart2(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                } else if(reportMasterType.equals("Defect Occurrances"))
                {
                    chart =stackedBarChart(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                }else {
                    chart = generateBarChartFromAPIData(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                }
            
                break;
                          

            case "Daily":
                if (reportMasterType.equals("Cost VS Value")) {
                   chart = generateBarChart2(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                } else if(reportMasterType.equals("Defect Occurrances"))
                {
                    chart =stackedBarChart(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                } else {
                   chart = generateBarChartFromAPIData(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                }
                break;
            case "Weekly":
                if ( reportMasterType.equals("Cost VS Value")) {
                   chart = generateBarChart2(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                }else if(reportMasterType.equals("Defect Occurrances"))
                {
                    chart =stackedBarChart(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                }else {
                   chart = generateBarChartFromAPIData(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                }            
                break;
            case "Monthly":
                if (reportMasterType.equals("Cost VS Value")) {
                   chart = generateBarChart2(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                } else if(reportMasterType.equals("Defect Occurrances"))
                {
                    chart =stackedBarChart(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                } else {
                   chart = generateBarChartFromAPIData(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                }            
                break;
            case "Quarterly":
                if (reportMasterType.equals("Cost VS Value")) {
                   chart = generateBarChart2(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                }else if(reportMasterType.equals("Defect Occurrances"))
                {
                    chart =stackedBarChart(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                }else {
                   chart = generateBarChartFromAPIData(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                }            
                break;
            case "Yearly":
                if (reportMasterType.equals("Defect Occurrances")) {
 //                  chart = generateBarChart2(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                    chart =stackedBarChart(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                } else {
                   chart = generateBarChartFromAPIData(firstChartParameter, secondChartParameter, thirdChartParameter, chartType);
                }            
                break;
        }
        
       }catch(Exception ex)
       {
           System.err.println(ex);
       }
*/
    }//GEN-LAST:event_chartChoiceItemStateChanged

    ArrayList<String>    weeklyDates = new ArrayList<String> ();
    ArrayList<String>    months = new ArrayList<String> ();
    ArrayList<String[]> singleMonth = new ArrayList<String[]> ();
    ArrayList<String[]> durationDates = new ArrayList<String[]> ();
    ArrayList<String>    quarters = new ArrayList<String> ();
    SimpleDateFormat   sdfTS = new SimpleDateFormat ( "yyyy-MM-dd" );

    SimpleDateFormat sdf = new SimpleDateFormat ( "EEEE" );
    SimpleDateFormat sdf1 = new SimpleDateFormat ( "MMM" );
    SimpleDateFormat sdf4 = new SimpleDateFormat ( "yyyy" );
    SimpleDateFormat sdf3 = new SimpleDateFormat ( "dd" );

    long totalNoOfDays = 0;



    public String formatFromDate_old () {

        String[] dateIP = dateChooserCombo3.getText ().split ( " " );

        String fromDate = null;

        if ( dateIP[ 1 ].length () == 3 ) {
            fromDate = dateIP[ 1 ].substring ( 0 , 2 ) + "-";
            c1.set ( Calendar.DATE , Integer.parseInt ( dateIP[ 1 ].substring ( 0 , 2 ) ) );
        } else if ( dateIP[ 1 ].length () == 2 ) {
            fromDate = "0" + dateIP[ 1 ].substring ( 0 , 1 ) + "-";
            c1.set ( Calendar.DATE , Integer.parseInt ( dateIP[ 1 ].substring ( 0 , 1 ) ) );
        }

        String mon = dateIP[ 0 ];

        switch ( mon ) {

            case "Jan":
                fromDate = fromDate + "01-";
                c1.set ( Calendar.MONTH , 0 );
                break;
            case "Feb":
                fromDate = fromDate + "02-";
                c1.set ( Calendar.MONTH , 1 );
                break;
            case "Mar":
                fromDate = fromDate + "03-";
                c1.set ( Calendar.MONTH , 2 );
                break;
            case "Apr":
                fromDate = fromDate + "04-";
                c1.set ( Calendar.MONTH , 3 );
                break;
            case "May":
                fromDate = fromDate + "05-";
                c1.set ( Calendar.MONTH , 4 );
                break;
            case "Jun":
                fromDate = fromDate + "06-";
                c1.set ( Calendar.MONTH , 5 );
                break;
            case "Jul":
                fromDate = fromDate + "07-";
                c1.set ( Calendar.MONTH , 6 );
                break;
            case "Aug":
                fromDate = fromDate + "08-";
                c1.set ( Calendar.MONTH , 7 );
                break;
            case "Sep":
                fromDate = fromDate + "09-";
                c1.set ( Calendar.MONTH , 8 );
                break;
            case "Oct":
                fromDate = fromDate + "10-";
                c1.set ( Calendar.MONTH , 9 );
                break;
            case "Nov":
                fromDate = fromDate + "11-";
                c1.set ( Calendar.MONTH , 10 );
                break;
            case "Dec":
                fromDate = fromDate + "12-";
                c1.set ( Calendar.MONTH , 11 );
                break;

        }

        // fromDate = fromDate + dateIP[2];
        c1.set ( Calendar.YEAR , Integer.parseInt ( dateIP[ 2 ] ) );
        fromDate = sdf2.format ( c1.getTime () ) + " 00:00:00";

        SimpleDateFormat sdf = new SimpleDateFormat ( "EEEE" );
        SimpleDateFormat sdf1 = new SimpleDateFormat ( "MMM" );
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy" );
        SimpleDateFormat sdf3 = new SimpleDateFormat ( "dd" );

        //return sdf2.format(  dateChooserCombo3.getSelectedDate ().getTime () );
        return fromDate;
    }
    
    public String formatToDate_old () {

        String[] dateIP2 = dateChooserCombo4.getText ().split ( " " );
        String toDate = null;

        if ( dateIP2[ 1 ].length () == 3 ) {
            toDate = dateIP2[ 1 ].substring ( 0 , 2 ) + "-";
            c2.set ( Calendar.DATE , Integer.parseInt ( dateIP2[ 1 ].substring ( 0 , 2 ) ) );
        } else if ( dateIP2[ 1 ].length () == 2 ) {
            toDate = "0" + dateIP2[ 1 ].substring ( 0 , 1 ) + "-";
            c2.set ( Calendar.DATE , Integer.parseInt ( dateIP2[ 1 ].substring ( 0 , 1 ) ) );
        }
        String mon2 = dateIP2[ 0 ];

        switch ( mon2 ) {

            case "Jan":
                toDate = toDate + "01-";
                c2.set ( Calendar.MONTH , 0 );
                break;
            case "Feb":
                toDate = toDate + "02-";
                c2.set ( Calendar.MONTH , 1 );
                break;
            case "Mar":
                toDate = toDate + "03-";
                c2.set ( Calendar.MONTH , 2 );
                break;
            case "Apr":
                toDate = toDate + "04-";
                c2.set ( Calendar.MONTH , 3 );
                break;
            case "May":
                toDate = toDate + "05-";
                c2.set ( Calendar.MONTH , 4 );
                break;
            case "Jun":
                toDate = toDate + "06-";
                c2.set ( Calendar.MONTH , 5 );
                break;
            case "Jul":
                toDate = toDate + "07-";
                c2.set ( Calendar.MONTH , 6 );
                break;
            case "Aug":
                toDate = toDate + "08-";
                c2.set ( Calendar.MONTH , 7 );
                break;
            case "Sep":
                toDate = toDate + "09-";
                c2.set ( Calendar.MONTH , 8 );
                break;
            case "Oct":
                toDate = toDate + "10-";
                c2.set ( Calendar.MONTH , 9 );
                break;
            case "Nov":
                toDate = toDate + "11-";
                c2.set ( Calendar.MONTH , 10 );
                break;
            case "Dec":
                toDate = toDate + "12-";
                c2.set ( Calendar.MONTH , 11 );
                break;

        }

        //toDate = toDate + dateIP2[2];
        c2.set ( Calendar.YEAR , Integer.parseInt ( dateIP2[ 2 ] ) );
        toDate = sdf2.format ( c2.getTime () ) + " 00:00:00";

        c2.setFirstDayOfWeek ( Calendar.SUNDAY );
        //   System.out.println( "This is #"+  c2.get ( Calendar.WEEK_OF_MONTH) +" week in the month");
        //  System.out.println( "This is #"+  (c2.get ( Calendar.MONTH) +1)+" month in the year");

        return toDate;
        //return sdf2.format(  dateChooserCombo4.getSelectedDate ().getTime () );
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> chartChoice;
    private datechooser.beans.DateChooserCombo dateChooserCombo3;
    private datechooser.beans.DateChooserCombo dateChooserCombo4;
    private datechooser.beans.DateChooserDialog dateChooserDialog1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private static javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    
     String currentYear , nextYear, selectedMonth;
     String[] _months ;
     ActionListener selectFreq = new ActionListener(){
        @Override
        public void actionPerformed ( ActionEvent e ) {
            
                currentYear = new SimpleDateFormat("yyyy").format ( Calendar.getInstance ().getTime() );
                selectedMonth = new SimpleDateFormat("MMMM").format ( Calendar.getInstance ().getTime() );
                nextYear = String.valueOf( Integer.parseInt( currentYear)+1 );
                _months = new String[]{ "April "+currentYear, "May "+currentYear, "June "+currentYear, "July "+currentYear, "August "+currentYear, "September "+currentYear, "October "+currentYear, "November "+currentYear, "December "+currentYear, "January "+nextYear, "February  "+nextYear, "March "+nextYear  };
            
            
                JComboBox freq = (JComboBox) e.getSource();
                String selection  = freq.getSelectedItem().toString() ;
                // 10 90   140 30
                // 10 140 140 30
                if(  selection.equals( "Duration" ) ){
                    dateChooserCombo3.setEnabled ( true );
                    dateChooserCombo4.setEnabled ( true );
                    dateChooserCombo3.setVisible ( true );
                    dateChooserCombo4.setVisible ( true );
                    jComboBox8.setEnabled(false);
                    jLabel5.setVisible ( false);
                    jLabel1.setVisible ( true);
                    jLabel11.setVisible ( true);
                }else{
                    dateChooserCombo3.setEnabled ( false );
                    dateChooserCombo4.setEnabled ( false );
                    dateChooserCombo3.setVisible ( false );
                    dateChooserCombo4.setVisible ( false );
                    jLabel1.setVisible ( false);
                    jLabel11.setVisible ( false);
                    jComboBox8.setEnabled(true);
                    jLabel5.setVisible ( true);
                    
                    if( selection.equals( "Weekly" )  || selection.equals( "Daily" )   ){
                        jLabel5.setText( "Select Month" );
                        jComboBox8.removeAllItems();
                        
//                        if( selectedMonth.equals( "January"  ) || selectedMonth.equals( "February"  ) || selectedMonth.equals( "March"  ) ){
//                            nextYear = currentYear ;
//                            currentYear = String.valueOf ( Integer.parseInt( currentYear) -1 );
//                            _months = new String[]{ "April "+currentYear, "May "+currentYear, "June "+currentYear, "July "+currentYear, "August "+currentYear, "September "+currentYear, "October "+currentYear, "November "+currentYear, "December "+currentYear, "January "+nextYear, "February  "+nextYear, "March "+nextYear  };
//                        }else{
//                            nextYear = String.valueOf ( Integer.parseInt( currentYear) +1 );
//                        }
//                        
//                        for( int i=0 ; i < _months.length; i++  ){
//                            jComboBox8.addItem(  _months[i] ) ;
//                        }  
//                        for( int i=0 ; i < _months.length; i++  ){
//                            if( selectedMonth.equals (_months[i]  )  ){
//                                jComboBox8.setSelectedIndex ( i);
//                            }
//                        }
                        
                    }else {
                        jLabel5.setVisible ( true );
                        jLabel5.setText(  "F. Y. "+currentYear );
                        jComboBox8.removeAllItems();
                        jComboBox8.setEnabled( false );
                    }
                    
                    String[] months_ = new String[]{ "April", "May", "June", "July", "August", "September", "October", "November", "December", "January", "February", "March"  };

                    if( selectedMonth.equals( "January"  ) || selectedMonth.equals( "February"  ) || selectedMonth.equals( "March"  ) ){
                        nextYear = currentYear ;
                        currentYear = String.valueOf ( Integer.parseInt( currentYear) -1 );
                        _months = new String[]{ "April "+currentYear, "May "+currentYear, "June "+currentYear, "July "+currentYear, "August "+currentYear, "September "+currentYear, "October "+currentYear, "November "+currentYear, "December "+currentYear, "January "+nextYear, "February  "+nextYear, "March "+nextYear  };
                    }else{
                        nextYear = String.valueOf ( Integer.parseInt( currentYear) +1 );
                    }

                    for( int i=0 ; i < _months.length; i++  ){
                        jComboBox8.addItem(  _months[i] ) ;
                    }  
                    for( int i=0 ; i < months_.length; i++  ){
                        if( selectedMonth.equals (months_[i]  )  ){
                            jComboBox8.setSelectedIndex ( i);
                        }
                    }
                    
                }
                
                // jComboBox8.addActionListener(  selectMonth );                
        }
    };
     ActionListener selectMonth = new ActionListener(){
        @Override
        public void actionPerformed ( ActionEvent e ) {
            
/*            selectedMonth = _months[  jComboBox8.getSelectedIndex() ];
            
            if( selectedMonth.equals( "January"  ) || selectedMonth.equals( "February"  ) || selectedMonth.equals( "March"  ) ){
                nextYear = currentYear ;
                currentYear = String.valueOf ( Integer.parseInt( currentYear) -1 );
            }else{
                nextYear = String.valueOf ( Integer.parseInt( currentYear) -1 );
            }
            
            _months = new String[]{ "April "+currentYear, "May "+currentYear, "June "+currentYear, "July "+currentYear, "August "+currentYear, "September "+currentYear, "October "+currentYear, "November "+currentYear, "December "+currentYear, "January "+nextYear, "February  "+nextYear, "March "+nextYear  };
            jComboBox8.removeAllItems();
            
            for( int i=0 ; i < _months.length; i++  ){
                jComboBox8.addItem(  _months[i] ) ;
            }  
            
            for( int i=0 ; i < _months.length; i++  ){
                if( selectedMonth.equals (_months[i]  )  ){
                    jComboBox8.setSelectedIndex ( i);
                }
            }           */
        }
   };
    
         public String showDurationDates (  Date fromDate,    Date toDate   ) {

        durationDates = new ArrayList<String[]>();   
        
        //formatFromDate (    );
        Calendar c = Calendar.getInstance ();
        
        c.setTime ( fromDate );
        durationDates.add ( new String[]{ sdf2.format ( c.getTime () ) + " 00:00:00" ,  new SimpleDateFormat("MMM-dd").format( c.getTime () ) });
        
        long noOfDays =  ( toDate.getTime() - fromDate.getTime() )/1000  ;
        noOfDays = noOfDays / 60 ;
        noOfDays = noOfDays / 60 ;
        noOfDays = noOfDays / 24 ;
        

        // System.out.println( "From month is  "+ c1.getActualMaximum ( Calendar.DAY_OF_MONTH));
        // System.out.println( "From month is  "+sdf1.format(c1.getTime()));
        for ( int i = 1 ; i <= noOfDays ; i ++ ) {

            c.set ( Calendar.HOUR , 24 );
            durationDates.add ( new String[]{ sdf2.format ( c.getTime () ) + " 00:00:00" ,  new SimpleDateFormat("MMM-dd").format( c.getTime () ) });

        }

        // System.out.println( "");
        // Calculation for the week if fromDate fall on Sunday and all subsequent weeks till toDate does not fall on friday
        // Calculation for the last week when toDate fall on Friday
        return sdf4.format ( c1.getTime () );
    }
     
    public JFreeChart generateBarChart2 ( String reportTitle , String xAxis , String yAxis,String chartType ) {

        try {
            panel.remove ( cp );
        } catch ( Exception e ) {
//               System.out.println("Error component "+e.getMessage ());
            StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        dataSet = new DefaultCategoryDataset ();

        if(chartType.equals("Bar-Chart"))
        {
            for (int i = 0; i < reportData.size(); i++) {
                try {
                    
                    dataSet.setValue(Integer.parseInt(reportData.get(i)[1]), xAxis, reportData.get(i)[0]);

                } catch (Exception e) {
                    try {
                        dataSet.setValue(Float.parseFloat(df.format(Float.parseFloat(reportData.get(i)[1]))), xAxis, reportData.get(i)[0]);
                    } catch (Exception e2) {
                        StaticValues.writer.writeExcel(ReportsController2.class.getSimpleName(), ReportsController2.class.getSimpleName(), e2.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e2.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
                        System.out.println("" + e2);
                    }
                }
            }
        
            for (int i = 0; i < reportData.size(); i++) {
                try {
                    dataSet.setValue(Integer.parseInt(reportData.get(i)[2]), yAxis, reportData.get(i)[0]);

                } catch (Exception e) {
                    try {
                        dataSet.setValue(Float.parseFloat(df.format(Float.parseFloat(reportData.get(i)[2]))), yAxis, reportData.get(i)[0]);
                    } catch (Exception e2) {
                        System.out.println("" + e2);
                        //     StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }
                    System.out.println("" + e);
                    //    System.out.println ( " "+ Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 2 ] ) ) ) +  "    "+ abc.get ( i )[ 1 ]  );
                    //StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }
            }
        }else if(chartType.equals("Line-Chart"))
        {
           

            for (int i = 0; i < reportData.size(); i++) 
            {
                 float qumericChart=0;
                try {
                                       
                    for(int j=i;j>=0;j--)
                    {                    
                        qumericChart = qumericChart + Integer.parseInt(reportData.get(j)[1]);
                    }
                                    
                    dataSet.setValue(qumericChart, xAxis, reportData.get(i)[0]);
                 } catch (Exception e) {
                    try {
                                        
                    for(int j=i;j>=0;j--)
                    {             
                        qumericChart=qumericChart+Float.parseFloat(reportData.get(j)[1]);//Float.parseFloat(df.format(Float.parseFloat(reportData.get(i)[1])));
                    }
                   
                    dataSet.setValue(qumericChart, xAxis, reportData.get(i)[0]);
                    } catch (Exception e2) {
                        StaticValues.writer.writeExcel(ReportsController2.class.getSimpleName(), ReportsController2.class.getSimpleName(), e2.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e2.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
                        System.out.println("" + e2);
                    }
                }
            }
            
            for (int i = 0; i < reportData.size(); i++) {
                float qumericChart1=0;
                try {
                                        
                    for(int j=i;j>=0;j--)
                    {
                        qumericChart1=qumericChart1+Integer.parseInt(reportData.get(j)[2]);
                    }
                    
                    dataSet.setValue(qumericChart1, yAxis, reportData.get(i)[0]);
                } catch (Exception e) {
                    try {
                    
                        for(int j=i;j>=0;j--)
                        {
                            qumericChart1=qumericChart1+Float.parseFloat(df.format(Float.parseFloat(reportData.get(j)[2])));
                        }
                       
                        dataSet.setValue(qumericChart1, yAxis, reportData.get(i)[0]);
                    } catch (Exception e2) {
                        System.out.println("" + e2);
                        //     StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }
                    System.out.println("" + e);
                    //    System.out.println ( " "+ Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 2 ] ) ) ) +  "    "+ abc.get ( i )[ 1 ]  );
                    //StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }
            }
        }
        JFreeChart chart = null;
        if(chartType.equals("Bar-Chart"))
        {
            chart=ChartFactory.createBarChart3D (
            reportTitle , xAxis , yAxis ,
            dataSet , PlotOrientation.VERTICAL , true , true , false );
        }else if(chartType.equals("Line-Chart")){
            chart =ChartFactory.createLineChart(reportTitle,xAxis,yAxis,dataSet,PlotOrientation.VERTICAL, true,true,false); 
        }

        cp = new ChartPanel ( chart );
        
//        if( (abc.size ()*90) >340){
//             cp.setPreferredSize ( new java.awt.Dimension ( 530 , 280 ) );
//            cp.setBounds ( 300 , 310 , 530 , 280 );
//        }else{
        cp.setPreferredSize ( new java.awt.Dimension ( 1000 , 280 ) );
        cp.setBounds ( 300 , 310 , 1000 , 280 );
        //}
        cp.revalidate ();
        cp.repaint ();


        
        if (chartType.equals("Bar-Chart")) {
            CategoryPlot plot = ( CategoryPlot ) chart.getPlot ();
            plot.setBackgroundPaint ( Color.white );
            plot.setDomainGridlinePaint ( Color.white );
            plot.setDomainGridlinesVisible ( false );
            plot.setRangeGridlinePaint ( Color.white );

        
            ValueMarker marker = new ValueMarker ( 50000 );
            marker.setLabel ( "Reference Value" );
            marker.setLabelTextAnchor ( TextAnchor.CENTER_LEFT );
            marker.setPaint ( Color.WHITE );
            plot.addRangeMarker ( marker );
        

        if ( reportData.size () > 2 ) {
            CategoryAxis domainAxis = plot.getDomainAxis ();
            domainAxis.setCategoryLabelPositions ( CategoryLabelPositions.UP_45 );
        }
         //   LineRenderer3D renderer = (LineRenderer3D) plot.getRenderer();
           BarRenderer renderer = ( BarRenderer ) plot.getRenderer ();
            renderer.setBaseItemLabelsVisible(true);
            renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBarPainter ( new StandardBarPainter () );
            renderer.setSeriesPaint(0, Color.decode("#58ACFA"));
            renderer.setSeriesPaint(1, Color.decode("#5882FA"));
            renderer.setSeriesItemLabelFont(1, new Font("Times New Roman", Font.PLAIN, 8));
            renderer.setSeriesItemLabelPaint(0, Color.BLACK);
            renderer.setSeriesItemLabelFont(0, new Font("Times New Roman", Font.PLAIN, 8));
            renderer.setSeriesItemLabelPaint(1, Color.BLACK);
        } else if (chartType.equals("Line-Chart")) {
            try{
                CategoryPlot plot = ( CategoryPlot ) chart.getPlot ();
                plot.setBackgroundPaint ( Color.white );
                plot.setDomainGridlinePaint ( Color.white );
                plot.setDomainGridlinesVisible ( false );
                plot.setRangeGridlinePaint ( Color.white );   
                LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//        BarRenderer renderer = ( BarRenderer ) plot.getRenderer ();
                renderer.setBaseItemLabelsVisible(true);
                renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
           // renderer.setWallPaint((Paint) new StandardBarPainter ());
                renderer.setSeriesPaint(1, Color.CYAN);
                renderer.setSeriesPaint(1, Color.MAGENTA); 
        //        renderer.setSeriesLinesVisible(0, true);
                renderer.setSeriesItemLabelFont(1, new Font("Times New Roman", Font.PLAIN, 8));
                renderer.setSeriesItemLabelPaint(0, Color.BLACK);
                renderer.setSeriesItemLabelFont(0, new Font("Times New Roman", Font.PLAIN, 8));
                renderer.setSeriesItemLabelPaint(1, Color.BLACK);
            }catch(Exception ex)
            {
                System.err.println(ex.getMessage());
            }
        }
       

        //add ( cp );
        try{
        panel.add(cp);
        panel.revalidate ();
        panel.repaint ();
        }catch(Exception ex){
            System.err.println("Panel "+ex);
        }
        // HomeScreen.home.pack ();
        //HomeScreen.home.revalidate ();
        //HomeScreen.home.repaint ();

        return chart;
    }
    public  JFreeChart stackedBarChart(String reportTitle , String xAxis , String yAxis,String chartType)
    {
        try {
            panel.remove ( cp );
        } catch ( Exception e ) {
//               System.out.println("Error component "+e.getMessage ());
            StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        dataSet = new DefaultCategoryDataset ();
        JFreeChart chart = null;

        if(chartType.equals("Bar-Chart"))
        {
                Comparable[] comp=new Comparable[reportData.size()];
                for(int i=0;i<reportData.size();i++)
                {
                    comp[i]="R-ID("+reportData.get(i)[3]+") "+reportData.get(i)[0];
                }
                
                double[][] dat=createDataset();
                Comparable[] comparatorRowData=new Comparable[2];
                comparatorRowData[0]="Occurences";
                comparatorRowData[1]="Count";
                
                CategoryDataset dataset =DatasetUtilities.createCategoryDataset(comparatorRowData,comp, dat);
                    
                chart=ChartFactory.createStackedBarChart (reportTitle , xAxis , yAxis ,dataset , PlotOrientation.VERTICAL , true , true , false );
//            for (int i = 0; i < reportData.size(); i++) {
//                try {
//                    System.err.println("reportData.get("+i+")[1] "+reportData.get(i)[1] +"reportData.get("+i+")[0]"+reportData.get(i)[0]);
//                    
//                    dataSet.setValue(/*Integer.parseInt(reportData.get(i)[1])*/d, xAxis, reportData.get(i)[0]);
//
//                } catch (Exception e) {
//                    try {
//                     System.err.println("reportData.get("+i+")[1] "+reportData.get(i)[1] +"reportData.get("+i+")[0]"+reportData.get(i)[0]);
//
//                        dataSet.setValue(Float.parseFloat(df.format(Float.parseFloat(reportData.get(i)[1]))), xAxis, reportData.get(i)[0]);
//                    } catch (Exception e2) {
//                        StaticValues.writer.writeExcel(ReportsController2.class.getSimpleName(), ReportsController2.class.getSimpleName(), e2.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e2.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
//                        System.out.println("" + e2);
//                    }
//                }
//            }
//        
//            for (int i = 0; i < reportData.size(); i++) {
//                try {
//                        System.err.println("reportData.get("+i+")[2] "+reportData.get(i)[2] +"reportData.get("+i+")[0]"+reportData.get(i)[0]);
//                        //Double[] d={reportData.get(i)[2],reportData.get(i)[2]};
//                    dataSet.setValue(Integer.parseInt(reportData.get(i)[2]), yAxis, reportData.get(i)[0]);
//
//                } catch (Exception e) {
//                    try {
//                        dataSet.setValue(Float.parseFloat(df.format(Float.parseFloat(reportData.get(i)[2]))), yAxis, reportData.get(i)[0]);
//                    } catch (Exception e2) {
//                        System.out.println("" + e2);
//                        //     StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//                    }
//                    System.out.println("" + e);
//                    //    System.out.println ( " "+ Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 2 ] ) ) ) +  "    "+ abc.get ( i )[ 1 ]  );
//                    //StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//                }
//            }
        }else if(chartType.equals("Line-Chart"))
        {
           
            for (int i = 0; i < reportData.size(); i++) {
                 float qumericChart=0;
                try {
                                       
                    for(int j=i;j>=0;j--)
                    {                    
                        qumericChart = qumericChart + Integer.parseInt(reportData.get(j)[1]);
                    }
                   
                    dataSet.setValue(qumericChart, xAxis, reportData.get(i)[0]);
                } catch (Exception e) 
                {
                    try {
                                        
                    for(int j=i;j>=0;j--)
                    {             
                        qumericChart=qumericChart+Float.parseFloat(reportData.get(j)[1]);//Float.parseFloat(df.format(Float.parseFloat(reportData.get(i)[1])));
                    }

                   
                    dataSet.setValue(qumericChart, xAxis, reportData.get(i)[0]);
                    } catch (Exception e2) {
                        StaticValues.writer.writeExcel(ReportsController2.class.getSimpleName(), ReportsController2.class.getSimpleName(), e2.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e2.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
                        System.out.println("" + e2);
                    }
                }
            }
            
            for (int i = 0; i < reportData.size(); i++) {
                float qumericChart1=0;
                try {
                                        
                    for(int j=i;j>=0;j--)
                    {
                        qumericChart1=qumericChart1+Integer.parseInt(reportData.get(j)[2]);
                    }
                    
                    dataSet.setValue(qumericChart1, yAxis, reportData.get(i)[0]);
                } catch (Exception e) {
                    try {
                    
                        for(int j=i;j>=0;j--)
                        {
                            qumericChart1=qumericChart1+Float.parseFloat(df.format(Float.parseFloat(reportData.get(j)[2])));
                        }
                       
                        dataSet.setValue(qumericChart1, yAxis, reportData.get(i)[0]);
                    } catch (Exception e2) {
                        System.out.println("" + e2);
                        //     StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }
                    System.out.println("" + e);
                    //    System.out.println ( " "+ Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 2 ] ) ) ) +  "    "+ abc.get ( i )[ 1 ]  );
                    //StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }
            }
        }
        
        if(chartType.equals("Line-Chart"))
        {
            chart =ChartFactory.createLineChart(reportTitle,xAxis,yAxis,dataSet,PlotOrientation.VERTICAL, true,true,false); 
        }

        cp = new ChartPanel ( chart );
        
//        if( (abc.size ()*90) >340){
//             cp.setPreferredSize ( new java.awt.Dimension ( 530 , 280 ) );
//            cp.setBounds ( 300 , 310 , 530 , 280 );
//        }else{
        cp.setPreferredSize ( new java.awt.Dimension ( 1000 , 280 ) );
        cp.setBounds ( 300 , 310 , 1000 , 280 );
        //}
        cp.revalidate ();
        cp.repaint ();


        
        if (chartType.equals("Bar-Chart")) {
            CategoryPlot plot = ( CategoryPlot ) chart.getPlot ();
            plot.setBackgroundPaint ( Color.white );
            plot.setDomainGridlinePaint ( Color.white );
            plot.setDomainGridlinesVisible ( false );
            plot.setRangeGridlinePaint ( Color.white );
            ValueMarker marker = new ValueMarker ( 50000 );
            marker.setLabel ( "Reference Value" );
            marker.setLabelTextAnchor ( TextAnchor.CENTER_LEFT );
            marker.setPaint ( Color.WHITE );
            plot.addRangeMarker ( marker );
        

        if ( reportData.size () > 2 ) {
            CategoryAxis domainAxis = plot.getDomainAxis ();
            domainAxis.setCategoryLabelPositions ( CategoryLabelPositions.UP_45 );
        }
         //   LineRenderer3D renderer = (LineRenderer3D) plot.getRenderer();
           BarRenderer renderer = ( BarRenderer ) plot.getRenderer ();
            renderer.setBaseItemLabelsVisible(true);
            renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBarPainter ( new StandardBarPainter () );
            renderer.setSeriesPaint(0, Color.decode("#58ACFA"));
            renderer.setSeriesPaint(1, Color.decode("#5882FA"));
            renderer.setSeriesItemLabelFont(1, new Font("Times New Roman", Font.PLAIN, 8));
            renderer.setSeriesItemLabelPaint(0, Color.BLACK);
            renderer.setSeriesItemLabelFont(0, new Font("Times New Roman", Font.PLAIN, 8));
            renderer.setSeriesItemLabelPaint(1, Color.BLACK);
        } else if (chartType.equals("Line-Chart")) {
            try{
                CategoryPlot plot = ( CategoryPlot ) chart.getPlot ();
                plot.setBackgroundPaint ( Color.white );
                plot.setDomainGridlinePaint ( Color.white );
                plot.setDomainGridlinesVisible ( false );
                plot.setRangeGridlinePaint ( Color.white ); 
                
                LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        //        BarRenderer renderer = ( BarRenderer ) plot.getRenderer ();
                renderer.setBaseItemLabelsVisible(true);
                renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
           // renderer.setWallPaint((Paint) new StandardBarPainter ());
              // renderer.setSeriesLinesVisible(0, true);
                renderer.setSeriesPaint(1, Color.CYAN);
                renderer.setSeriesPaint(1, Color.MAGENTA);
                renderer.setSeriesItemLabelFont(1, new Font("Times New Roman", Font.PLAIN, 8));
                renderer.setSeriesItemLabelPaint(0, Color.BLACK);
                renderer.setSeriesItemLabelFont(0, new Font("Times New Roman", Font.PLAIN, 8));
                renderer.setSeriesItemLabelPaint(1, Color.BLACK);
            }catch(Exception ex)
            {
                System.err.println(ex.getMessage());
            }
        }
       

        //add ( cp );
        try{
        panel.add(cp);
        panel.revalidate ();
        panel.repaint ();
        }catch(Exception ex){
            System.err.println("Panel "+ex);
        }

        return chart;
    }
    private double[][] createDataset() {
      double d[] = new double[reportData.size()];
      double d1[] = new double[reportData.size()];
     
      for(int i=0;i<reportData.size();i++)
      {
          try{
              d[i]=Integer.parseInt(reportData.get(i)[1]);
              d1[i]=Integer.parseInt(reportData.get(i)[2]);
            
            } catch (Exception e) {
                    try {
                        d[i]=Float.parseFloat(df.format(Float.parseFloat(reportData.get(i)[1])));
                        d1[i]=Float.parseFloat(df.format(Float.parseFloat(reportData.get(i)[2])));
                    }catch(Exception ex){
                        System.err.println("EE"+ex);
                    }
            }
      }
      double[][] data = new double[][]{d,d1};
      
    return data;//DatasetUtilities.createCategoryDataset(
            //"Team ", "Match", data);
  }
}
