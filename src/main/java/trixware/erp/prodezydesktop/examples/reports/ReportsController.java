/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples.reports;

import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineRenderer3D;
//import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import datechooser.view.WeekDaysStyle;
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
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.TextAnchor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Derived.AutoCompletion;
import trixware.erp.prodezydesktop.Model.Customer;
import trixware.erp.prodezydesktop.Model.EmployeeDR;
import trixware.erp.prodezydesktop.Model.MachineDR;
import trixware.erp.prodezydesktop.Model.ProcessDR;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.ShiftDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.Export_Print_Utility;
import trixware.erp.prodezydesktop.examples.HomeScreen;
import trixware.erp.prodezydesktop.web_services.WebAPITester;


/**
 *
 * @author Rajesh
 */
public class ReportsController extends javax.swing.JPanel {

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
    public static DefaultCategoryDataset dataSet = null;
    static ArrayList<String[]> abc = null;
    static ArrayList<String[]> reportData = null;
    static ChartPanel cp = null;

    Calendar c1 = Calendar.getInstance ();
    Calendar c2 = Calendar.getInstance ();

    int columnCount;

    JFreeChart chart = null;

    String reportMasterType;
    JScrollPane scroll = null;
    JPanel panel = null;
    
    public static String firstChartParameter="";
    public static String secondChartParameter="";
    public static String thirdChartParameter="";
    public static String ReportType="";
    /**
     * Creates new form DailyReportForm
     * @param reportMasterType
     */
//    public ReportsController () {
//
////        chartChoice.addItem("Bar-Chart");
////        chartChoice.addItem("Line-Chart");
//
//        initComponents ();        
//        
//    }

    public ReportsController ( String reportMasterType ) {
        initComponents ();
        currentYear = new SimpleDateFormat("yyyy").format ( Calendar.getInstance ().getTime() );
        _months = new String[]{ "April "+currentYear, "May "+currentYear, "June "+currentYear, "July "+currentYear, "August "+currentYear, "September "+currentYear, "October "+currentYear, "November "+currentYear, "December "+currentYear, "January "+nextYear, "February  "+nextYear, "March "+nextYear  };

        jComboBox5.addActionListener(   selectFreq  );

        showDurationDates(  dateChooserCombo3.getSelectedDate ().getTime () ,  dateChooserCombo4.getSelectedDate ().getTime ()  );
        
        
        String selection  = jComboBox5.getSelectedItem().toString() ;
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

        loadComboBoxes ();

        AutoCompletion.enable ( jComboBox1 );
        AutoCompletion.enable ( jComboBox2 );
        AutoCompletion.enable ( jComboBox3 );
        AutoCompletion.enable ( jComboBox4 );
        AutoCompletion.enable ( jComboBox5 );
        AutoCompletion.enable ( jComboBox6 );
        AutoCompletion.enable ( jComboBox7 );
        
        //dateChooserCombo4.setText ( dateChooserCombo3.getText () );
        dateChooserCombo4.setFormat ( DateFormat.MEDIUM );
        dateChooserCombo3.setFormat ( DateFormat.MEDIUM );
        dateChooserCombo4.setWeekStyle ( WeekDaysStyle.NORMAL );
        dateChooserCombo3.setWeekStyle ( WeekDaysStyle.NORMAL );
        dateChooserCombo4.setOpaque ( false );
        dateChooserCombo3.setOpaque ( false );
        dateChooserCombo4.setBackground ( Color.WHITE );
        dateChooserCombo3.setBackground ( Color.WHITE );
        dateChooserCombo4.revalidate ();
        dateChooserCombo3.repaint ();

/*        Calendar c = Calendar.getInstance();
        c.set ( Calendar.DATE , Calendar.getInstance().getActualMinimum (Calendar.DAY_OF_MONTH) )  ;
        dateChooserCombo3.setSelectedDate ( c  );
        c.set ( Calendar.DATE , Calendar.getInstance().getActualMaximum (Calendar.DAY_OF_MONTH) )  ;
        dateChooserCombo4.setSelectedDate ( c  );           */
        
        Calendar c = Calendar.getInstance();
        
        if( StaticValues.getReportFromDate( )  != null  ){
            c.setTime (StaticValues.getReportFromDate( ) )  ;
            dateChooserCombo3.setSelectedDate ( c  );
        }else{
            c.set ( Calendar.DATE , Calendar.getInstance().getActualMinimum (Calendar.DAY_OF_MONTH) )  ;
            dateChooserCombo3.setSelectedDate ( c  );
        }
    
        if(   StaticValues.getReportToDate () != null  ){
            c.setTime (StaticValues.getReportToDate( ) )  ;
            dateChooserCombo4.setSelectedDate ( c  );
        }else{
            c.set ( Calendar.DATE , Calendar.getInstance().getActualMaximum (Calendar.DAY_OF_MONTH) )  ;
            dateChooserCombo4.setSelectedDate ( c  );
        }
        
        if( StaticValues.getReportTypeNumber () >-1 ){
            jComboBox5.setSelectedIndex ( StaticValues.getReportTypeNumber ());
        }
        
       // StaticValues.setReportFromDate(   dateChooserCombo3.getSelectedDate ().getTime ()   ) ;
      //  StaticValues.setReportToDate    (   dateChooserCombo4.getSelectedDate ().getTime ()   ) ;
      //  StaticValues.setReportTypeNumber ( jComboBox5.getSelectedIndex ());
      //  StaticValues.setReportTypeString    (jComboBox5.getSelectedItem().toString());

        
        dataSet = new DefaultCategoryDataset ();

        for ( int i = 0 ; i < 5 ; i ++ ) {
            dataSet.setValue ( 0 , "Production" , String.valueOf ( i ) );
        }

        chart = 
//                ChartFactory.createLineChart3D( 
//                "Title Value" , "xAxis" , "yAxis",dataSet,PlotOrientation.VERTICAL,
//                true,true,false);
        ChartFactory.createBarChart3D (
                "Title Value" , "xAxis" , "yAxis" ,
                dataSet , PlotOrientation.VERTICAL , true , true , false );

        panel = new JPanel ();
        panel.setBackground ( Color.WHITE );
        //panel.setBounds ( 0,0 , 1000 , 300 );
        panel.revalidate ();
        panel.repaint ();

        scroll = new JScrollPane ( panel ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        scroll.setBounds ( 270 , 290 , 1000 , 280 );
        scroll.setOpaque ( false );
        scroll.setBackground ( Color.BLACK );

        cp = new ChartPanel ( chart );
        cp.setPreferredSize ( new java.awt.Dimension ( 1000 , 280 ) );
        cp.setBounds ( 0 , 0 , 1000 , 280 );
        cp.setBackground ( Color.WHITE );
        panel.add ( cp );
        scroll.revalidate ();
        scroll.repaint ();
        add ( scroll );
//        cp = new ChartPanel ( chart );
//        cp.setPreferredSize ( new java.awt.Dimension ( 980 , 320 ) );
//        cp.setBounds ( 300 , 280 , 980 , 340 );
//        cp.setBackground ( Color.DARK_GRAY );
//        scroll = new JScrollPane ( cp ,
//                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
//                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
//
//            add ( scroll , BorderLayout.CENTER );
//        cp.revalidate ();
//        cp.repaint ();
//        
        

        
       // callDefaultReportView ();
        
        
//        add ( cp );
    }

//    @Override
//    protected void paintComponent ( Graphics g ) {
//        super.paintComponent ( g );
//        Graphics2D g2d = ( Graphics2D ) g;
//        g2d.setRenderingHint ( RenderingHints.KEY_RENDERING , RenderingHints.VALUE_RENDER_QUALITY );
//        int w = getWidth ();
//        int h = getHeight ();
//        Color color1 = Color.GRAY;
//        Color color2 = Color.BLACK;
//        GradientPaint gp = new GradientPaint ( 0 , 0 , color1 , 0 , h , color2 );
//        g2d.setPaint ( gp );
//        g2d.fillRect ( 0 , 0 , w , h );
//    }
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

                    StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

                //graphs[ i  ] = _graph ;
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

    public DecimalFormat df = new DecimalFormat ( "#.##" );

    public JFreeChart generateBarChart ( String reportTitle , String xAxis , String yAxis ) 
    {

        try {
            panel.remove ( cp );
        } catch ( Exception e ) {
//               System.out.println("Error component "+e.getMessage ());
            StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        dataSet = new DefaultCategoryDataset ();

        //t.start();
        for ( int i = 0 ; i < abc.size () ; i ++ ) {
            try {
                dataSet.setValue ( Integer.parseInt ( abc.get ( i )[ 1 ] ) , "Occurences" , abc.get ( i )[ 0 ] );

            } catch ( Exception e ) {
                try {
                    dataSet.setValue ( Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 1 ] ) ) ) , "Occurences" , abc.get ( i )[ 0 ] );
                } catch ( Exception e2 ) {
                    StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }
                StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }

        if ( reportMasterType.equals ( "Cost VS Value" ) ) {

            for ( int i = 0 ; i < abc.size () ; i ++ ) {
                try {
                    dataSet.setValue ( Integer.parseInt ( abc.get ( i )[ 2 ] ) , "Production Value" , abc.get ( i )[ 0 ] );

                } catch ( NumberFormatException e ) {
                    dataSet.setValue ( Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 2 ] ) ) ) , "Production Value" , abc.get ( i )[ 0 ] );
                    //    System.out.println ( " "+ Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 2 ] ) ) ) +  "    "+ abc.get ( i )[ 1 ]  );
                    StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }
            }
        }

        if ( reportMasterType.equals ( "PPM" ) ) {

            for ( int i = 0 ; i < abc.size () ; i ++ ) {
                try {
                    dataSet.setValue ( Integer.parseInt ( abc.get ( i )[ 2 ] ) , "Parts per million" , abc.get ( i )[ 0 ] );

                } catch ( NumberFormatException e ) {
                    dataSet.setValue ( Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 2 ] ) ) ) , "Parts per million" , abc.get ( i )[ 0 ] );
                    //    System.out.println ( " "+ Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 2 ] ) ) ) +  "    "+ abc.get ( i )[ 1 ]  );
                    StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }
            }
        }

        chart = 
                ChartFactory.createLineChart3D( 
                reportTitle , xAxis , yAxis,dataSet,PlotOrientation.VERTICAL,
                true,true,false);
//                ChartFactory.createBarChart3D (
//                reportTitle , xAxis , yAxis ,
//                dataSet , PlotOrientation.VERTICAL , true , true , false );

        cp = new ChartPanel ( chart );
        cp.setPreferredSize ( new java.awt.Dimension ( 900 , 330 ) );
        cp.setBounds ( 270 , 290 , 980 , 300 );
        cp.revalidate ();
        cp.repaint ();

        //chart.getCategoryPlot().getRangeAxis().setLowerBound(9.0);
        CategoryPlot plot = ( CategoryPlot ) chart.getPlot ();
        plot.setBackgroundPaint ( Color.white );
        plot.setDomainGridlinePaint ( Color.white );
        plot.setDomainGridlinesVisible ( false );
        plot.setRangeGridlinePaint ( Color.white );

        if ( reportMasterType.equals ( "Cost VS Value" ) || reportMasterType.equals ( "PPM" ) ) {
            ValueMarker marker = new ValueMarker ( 50000 );
            marker.setLabel ( "Reference Value" );
            marker.setLabelTextAnchor ( TextAnchor.CENTER_LEFT );
            marker.setPaint ( Color.WHITE );
            plot.addRangeMarker ( marker );
        }

        if ( abc.size () > 2 ) {
            CategoryAxis domainAxis = plot.getDomainAxis ();
            domainAxis.setCategoryLabelPositions ( CategoryLabelPositions.UP_45 );
        }

        LineRenderer3D renderer = ( LineRenderer3D ) plot.getRenderer ();
//        BarRenderer renderer = ( BarRenderer ) plot.getRenderer ();
        renderer.setBaseItemLabelsVisible ( true );
        renderer.setBaseItemLabelGenerator ( new StandardCategoryItemLabelGenerator () );
        
//        renderer.setBarPainter ( new StandardBarPainter () );
        renderer.setSeriesPaint ( 1 , Color.CYAN );
        renderer.setSeriesPaint ( 0 , Color.GREEN );
        renderer.setSeriesItemLabelFont ( 1 , new Font ( "Times New Roman" , Font.PLAIN , 8 ) );
        renderer.setSeriesItemLabelPaint ( 0 , Color.BLACK );
        renderer.setSeriesItemLabelFont ( 0 , new Font ( "Times New Roman" , Font.PLAIN , 8 ) );
        renderer.setSeriesItemLabelPaint ( 1 , Color.BLACK );

        //add ( cp );
        panel.add ( cp );
        panel.revalidate ();
        panel.repaint ();

        //   HomeScreen.home.pack ();
        HomeScreen.home.revalidate ();
        HomeScreen.home.repaint ();

        return chart;
    }

    public JFreeChart generateBarChartFromAPIData ( String reportTitle , String xAxis , String yAxis,String chartType ) 
    {

        try 
        {
            panel.remove ( cp );
        } catch ( Exception e ) 
        {
            System.err.println(e);
            StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        dataSet = new DefaultCategoryDataset ();

        //t.start();
        int count = 0;
        String label = "";
       
        if(chartType.equals("Bar-Chart"))
        {
            
            for ( int i = 0 ; i < reportData.size () ; i ++ ) {
                
                try {
                    dataSet.setValue ( Integer.parseInt ( reportData.get ( i )[ 1 ] ) , yAxis, reportData.get ( i )[ 0 ] );
                } catch ( Exception e )
                {
                    try {
                        dataSet.setValue ( Float.parseFloat ( df.format ( Float.parseFloat ( reportData.get ( i )[ 1 ] ) ) ) , yAxis , reportData.get ( i )[ 0 ] );
                    } catch ( Exception e2 ) {
                    //StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                        System.out.println ( ""+e2.getMessage () );
                    }
                // StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }
            }

//            if ( reportMasterType.equals ( "Cost VS Value" ) ) {
//                for ( int i = 0 ; i < abc.size () ; i ++ ) {
//                    try {
//                        dataSet.setValue ( Integer.parseInt ( reportData.get ( i )[ 2 ] ) , yAxis , reportData.get ( i )[ 0 ] );
//
//                    } catch ( NumberFormatException e ) {
//                        dataSet.setValue ( Float.parseFloat ( df.format ( Float.parseFloat ( reportData.get ( i )[ 2 ] ) ) ) , yAxis , reportData.get ( i )[ 0 ] );
//                        //    System.out.println ( " "+ Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 2 ] ) ) ) +  "    "+ abc.get ( i )[ 1 ]  );
//                        StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//                    }
//                }
//            }
//
//            if ( reportMasterType.equals ( "PPM" ) ) {
//
//                for ( int i = 0 ; i < abc.size () ; i ++ ) {
//                    try {
//                        dataSet.setValue ( Integer.parseInt ( reportData.get ( i )[ 2 ] ) , "Parts per million" , reportData.get ( i )[ 0 ] );
//
//                    } catch ( NumberFormatException e ) {
//                        dataSet.setValue ( Float.parseFloat ( df.format ( Float.parseFloat ( reportData.get ( i )[ 2 ] ) ) ) , "Parts per million" , reportData.get ( i )[ 0 ] );
//                        //    System.out.println ( " "+ Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 2 ] ) ) ) +  "    "+ abc.get ( i )[ 1 ]  );
//                        StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//                    }
//                }
//            }
            chart = ChartFactory.createBarChart3D (reportTitle , xAxis , yAxis ,dataSet , PlotOrientation.VERTICAL , true , true , false );
            
        }else if(chartType.equals("Line-Chart"))
        {

     
           
            for ( int i = 0 ; i < reportData.size () ; i ++ ) 
            {
                float addQumerative=0;
             //   float addQumerative1=0;
                try {
                    
                    for(int j=i;j>=0;j--)
                    {                  
                        addQumerative=addQumerative+Integer.parseInt(reportData.get ( j )[ 1 ]);                      
                    }

                    dataSet.setValue ( addQumerative , yAxis, reportData.get ( i )[ 0 ] );
                } catch ( Exception e )
                {
                    try {
                        
                        for(int j=i;j>=0;j--)
                        {                          
                            addQumerative=addQumerative+Float.parseFloat ( df.format ( Float.parseFloat ( reportData.get ( j )[ 1 ] ) ) );
                        }

                        dataSet.setValue ( addQumerative , yAxis , reportData.get ( i )[ 0 ] );
                    } catch ( Exception e2 ) {
                    //StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }
                // StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }
            }
//           for ( int i = 0 ; i < reportData.size () ; i ++ ) {
//                try {                                      
//                    for(int j=i;j>=0;j--)
//                    {
//                        addQumerative=addQumerative+100;
//                    }
//                    dataSet.setValue ( addQumerative , "Target Line", reportData.get ( i )[ 0 ] );
//                } catch ( Exception e )
//                {
//                    try {
//                        
//                        for(int j=i;j>=0;j--)
//                        {
//                            addQumerative=addQumerative+100;
//                        }
//                        dataSet.setValue ( addQumerative1 , "Target Line" , reportData.get ( i )[ 0 ] );
//                    } catch ( Exception e2 ) {
//                    //StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//                    }
//                // StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//                }
//            }

            if ( reportMasterType.equals ( "Cost VS Value" ) ) {
                for ( int i = 0 ; i < abc.size () ; i ++ ) {
                         int addQumerative=0;
                        float addQumerative1=0;
                    try {
                   
                        for(int j=i;j>=0;j--)
                        {
                            addQumerative=addQumerative+Integer.parseInt ( reportData.get ( i )[ 2 ] );
                        }    
                        dataSet.setValue (addQumerative  , yAxis , reportData.get ( i )[ 0 ] );

                    } catch ( NumberFormatException e ) {
                  
                        for(int j=i;j>=0;j--)
                        {
                            addQumerative1=addQumerative1+Float.parseFloat ( df.format ( Float.parseFloat ( reportData.get ( i )[ 2 ] ) ) );
                        }
                        dataSet.setValue ( addQumerative , yAxis , reportData.get ( i )[ 0 ] );
                        //    System.out.println ( " "+ Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 2 ] ) ) ) +  "    "+ abc.get ( i )[ 1 ]  );
                        StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }
                }
            }

            if ( reportMasterType.equals ( "PPM" ) ) {

                for ( int i = 0 ; i < abc.size () ; i ++ ) 
                {
                    int addQumerative=0;
                    float addQumerative1=0;
                    try {
                      
                        for(int j=i;j>=0;j--)
                        {
                            addQumerative=addQumerative+Integer.parseInt ( reportData.get ( i )[ 2 ] );
                        }
                        dataSet.setValue ( addQumerative , "Parts per million" , reportData.get ( i )[ 0 ] );

                    } catch ( NumberFormatException e ) {
                     
                        for(int j=i;j>=0;j--)
                        {
                            addQumerative1=addQumerative1+Float.parseFloat ( df.format ( Float.parseFloat ( reportData.get ( i )[ 2 ] ) ) );
                            
                        }
                        dataSet.setValue ( addQumerative , "Parts per million" , reportData.get ( i )[ 0 ] );
                        //    System.out.println ( " "+ Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 2 ] ) ) ) +  "    "+ abc.get ( i )[ 1 ]  );
                        StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }
                }
            }
            chart =   ChartFactory.createLineChart(reportTitle,xAxis,yAxis,dataSet,PlotOrientation.VERTICAL,true,true,false);
        }
           
        cp = new ChartPanel ( chart );
        cp.setPreferredSize ( new java.awt.Dimension ( 900 , 330 ) );
        cp.setBounds ( 270 , 290 , 980 , 300 );
        cp.revalidate ();
        cp.repaint ();

        //chart.getCategoryPlot().getRangeAxis().setLowerBound(9.0);
        CategoryPlot plot = ( CategoryPlot ) chart.getPlot ();
        plot.setBackgroundPaint ( Color.WHITE );
        plot.setDomainGridlinePaint ( Color.WHITE );
        plot.setDomainGridlinesVisible ( true );
        plot.setRangeGridlinePaint ( Color.WHITE );
        plot.setRangeGridlinesVisible ( true );

        if ( reportMasterType.equals ( "Cost VS Value" ) || reportMasterType.equals ( "PPM" ) ) {
            ValueMarker marker = new ValueMarker ( 50000 );
            marker.setLabel ( "Reference Value" );
            marker.setLabelTextAnchor ( TextAnchor.CENTER_LEFT );
            marker.setPaint ( Color.WHITE );
            plot.addRangeMarker ( marker );
        }

        if ( reportData.size () > 2 ) 
        {
            CategoryAxis domainAxis = plot.getDomainAxis ();
            domainAxis.setCategoryLabelPositions ( CategoryLabelPositions.UP_45 );
        }

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
        panel.add ( cp );
        panel.revalidate ();
        panel.repaint ();

        //   HomeScreen.home.pack ();
        HomeScreen.home.revalidate ();
        HomeScreen.home.repaint ();

        return chart;
    }

    public static JFreeChart generatePieChart () 
    {
        DefaultPieDataset dataSet = new DefaultPieDataset ();
        dataSet.setValue ( "China" , 19.64 );
        dataSet.setValue ( "India" , 17.3 );
        dataSet.setValue ( "United States" , 4.54 );
        dataSet.setValue ( "Indonesia" , 3.4 );
        dataSet.setValue ( "Brazil" , 2.83 );
        dataSet.setValue ( "Pakistan" , 2.48 );
        dataSet.setValue ( "Bangladesh" , 2.38 );

        JFreeChart chart = ChartFactory.createPieChart ( 
                "Daily Production" , dataSet , true , true , false );

        return chart;
    }

    public void loadComboBoxes_old () {

        String queryOne = "SELECT FG_ID, PART_NAME FROM finished_goods";
        String queryTwo = "SELECT EmployeePK, EMP_NAME FROM employee";
        String queryThree = "SELECT MCH_ID, MACHINE_NO FROM machine";
        String queryFour = "SELECT PROCESS_ID, PROCESS_NAME FROM PROCESS_MASTER";
        String queryFive = "SELECT shiftid, shifttitle FROM shifts";
        String querySix = "SELECT CUSTOMER_ID, CUSTOMER_NAME FROM customer";

        ResultSet rs = null;

        try {
            rs = DB_Operations.executeSingle ( querySix );
            //  products = new ArrayList<HashMap<String, String>> ();
            customer = new ArrayList<Customer> ();
            while ( rs.next () ) {
                //   entity =  new  HashMap<String, String>();
                cust = new Customer ();
                //    entity.put ( rs.getString(1) /*  String.valueOf( rs.getInt(1) ) */, rs.getString(2)) ;
                cust.CUST_ID = Integer.parseInt ( rs.getString ( 1 ) );
                cust.CUST_NAME = rs.getString ( 2 );
                customer.add ( cust );
                jComboBox7.addItem ( rs.getString ( 2 ) );
            }
            System.out.println ( "done with shift dropdown " );
        } catch ( Exception e ) {
            System.out.println ( " Error 1 " + e.getMessage () );
            StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        try {
            rs = DB_Operations.executeSingle ( queryFive );
            //  products = new ArrayList<HashMap<String, String>> ();
            shifts = new ArrayList<ShiftDR> ();
            while ( rs.next () ) {
                //   entity =  new  HashMap<String, String>();
                sftdr = new ShiftDR ();
                //    entity.put ( rs.getString(1) /*  String.valueOf( rs.getInt(1) ) */, rs.getString(2)) ;
                sftdr.SHIFT_ID = Integer.parseInt ( rs.getString ( 1 ) );
                sftdr.SHIFT_NAME = rs.getString ( 2 );
                shifts.add ( sftdr );
                jComboBox6.addItem ( rs.getString ( 2 ) );
            }
            System.out.println ( "done with shift dropdown " );
        } catch ( Exception e ) {
            System.out.println ( " Error 1 " + e.getMessage () );
            StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        // ------------------------------------------------------------------------------------
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
        } catch ( Exception e ) {
            System.out.println ( " Error 1 " + e.getMessage () );
            StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
            StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
            StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
            StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

    }

    public void loadComboBoxes () {
        String addEmpAPICall, result2;

        if(chartChoice.getItemCount()==0)
        {
            chartChoice.addItem("Bar-Chart");
            chartChoice.addItem("Line-Chart");
          //  chartChoice.setVisible(true);
        }
        
        if ( jComboBox1.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

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
                jComboBox1.addItem ( emp.get ( "FG_PART_NO" ).toString () );
                products.add ( new ProductDR ( Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) , emp.get ( "PART_NAME" ).toString () , emp.get ( "FG_UOM_ID" ).toString () ) );
            }

        }

        if ( jComboBox4.getItemCount () == 0 ) 
        {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            addEmpAPICall = "processes";
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
                processes.add ( new ProcessDR ( Integer.parseInt ( emp.get ( "PROCESS_ID" ).toString () ) , emp.get ( "PROCESS_NAME" ).toString () ) );
            }
        }

        if ( jComboBox2.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            addEmpAPICall = "machines";
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

        if ( jComboBox3.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            addEmpAPICall = "employees";
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
            employees = new ArrayList<EmployeeDR> ();
            for ( int i = 0 ; i < records.length () ; i ++ ) {

                emp = records.getJSONObject ( i );
                jComboBox3.addItem ( emp.get ( "EMP_NAME" ).toString () );
                employees.add ( new EmployeeDR ( Integer.parseInt ( emp.get ( "EmployeePK" ).toString () ) , emp.get ( "EMP_NAME" ).toString () ) );
            }

        }

        if ( jComboBox6.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            addEmpAPICall = "shifts";
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
            shifts = new ArrayList<ShiftDR> ();
            for ( int i = 0 ; i < records.length () ; i ++ ) {

                emp = records.getJSONObject ( i );
                jComboBox6.addItem ( emp.get ( "shifttitle" ).toString () );
                shifts.add ( new ShiftDR ( Integer.parseInt ( emp.get ( "shiftid" ).toString () ) , emp.get ( "shifttitle" ).toString () ) );
            }
        }

        if ( jComboBox7.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            addEmpAPICall = "customers";
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
            customer = new ArrayList<Customer> ();
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );
                jComboBox7.addItem ( emp.get ( "CUSTOMER_NAME" ).toString () );
                customer.add ( new Customer ( Integer.parseInt ( emp.get ( "CUSTOMER_ID" ).toString () ) , emp.get ( "CUSTOMER_NAME" ).toString () ) );
            }
        }
    }

    
     public JSONObject emp = null;
     public int counter =0 ;
     public JSONArray records = null;
    
    public void buildTableModelfromJSON ( String employeeJSONList , String reportType , String label ) {
        HashMap<String , Object> map = new HashMap<String , Object> ();
        JSONObject jObject = new JSONObject ( employeeJSONList );
        Iterator<?> keys = jObject.keys ();

        while ( keys.hasNext () ) {
            String key = ( String ) keys.next ();
            Object value = jObject.get ( key );
            map.put ( key , value );
        }

        
        JSONObject st = null;

        try {

            st = ( JSONObject ) map.get ( "data" );
            records = st.getJSONArray ( "records" );
 

            columnNames = new Vector<String> ();
            columnNames2 = new Vector<String> ();

            if ( reportMasterType.equals ( "Production Quantity" ) ) 
            {
                
                columnNames.add ( "part" );                //columnNames.add ( "outputqty" );                  
                columnNames2.add ( "Part" );  
                columnNames.add ( "outputqty" );                //columnNames.add ( "outputqty" );                  
                columnNames2.add ( "Production" );          //columnNames2.add ( "Production" );
                columnNames.add ( "showrdate" );               //columnNames.add ( "showrdate" );               
                columnNames2.add ( "Date" );                    //columnNames2.add ( "Date" );

            } else if ( reportMasterType.equals ( "Production Value" ) ) 
            {

                columnNames.add ( "part" );           
                columnNames2.add ( "Part Name" );
                columnNames.add ( "value" );                  
                columnNames2.add ( "Production Value" );
                //columnNames.add ( "outputqty" );           
                //columnNames2.add ( "Production" );
                //columnNames.add ( "sales_rate" );        
                //columnNames2.add ( "Selling Price" );
                columnNames.add ( "showrdate" );                 
                columnNames2.add ( "Date" );
            }else if (  reportMasterType.equals ( "Rejection" ) ) 
            {
                columnNames.add ( "showrdate" );            
                columnNames2.add ( "Date" );
                columnNames.add ( "rejection" );          
                columnNames2.add ( "Rejected Qty" );
            }else if ( reportMasterType.equals ( "Productivity" ) ) 
            {
                columnNames.add ( "showrdate" );        
                columnNames2.add ( "Date" );
                columnNames.add ( "prod" );        
                columnNames2.add ( "Productivity" );
               // columnNames.add ( "actual_hours" );         
               // columnNames.add ( "exp_outputqty" );        
               // columnNames.add ( "total_hours" );            

            }else if ( reportMasterType.equals ( "PPM" ) ) 
            {
                columnNames.add ( "showrdate" );       
                columnNames2.add ( "Date" );
                columnNames.add ( "ppm" );              
                columnNames2.add ( "PPM" );
                columnNames.add ( "rejection" );        
                columnNames2.add ( "Rejected Qty" );
                columnNames.add ( "outputqty" );        
                columnNames2.add ( "Production" );
                columnNames.add ( "product" );          
                columnNames2.add ( "Part Name" );
                
            }else if ( reportMasterType.equals ( "Work In Progress" ) ) 
            {
                
                columnNames.add ( "MOVEDATE" );           
                columnNames2.add ( "Date" );
                columnNames.add ( "balance" );          
                columnNames2.add ( "Production Bal." );
                columnNames.add ( "process_name" );         
                columnNames2.add ( "Process" );
                columnNames.add ( "machine" );             
                columnNames2.add ( "Machine" );
                columnNames.add ( "emp_no" );               
                columnNames2.add ( "Employee" );
                columnNames.add ( "product" );              
                columnNames2.add ( "Part Name" );
                
            }else if ( reportMasterType.equals ( "Defect Occurrances" ) ) 
            {
                columnNames.add ( "RR_DESC" );               
                columnNames2.add ( "Rejection Reason" );
                columnNames.add ( "occurences" );          
                columnNames2.add ( "Occurrances" );
                columnNames.add ( "count" );               
                columnNames2.add ( "Count" );
                columnNames.add ( "rrID" );           
                columnNames2.add ( "Rejection ID" );
                
            }else if ( reportMasterType.equals ( "Cost VS Value" ) ) 
            {
                columnNames.add ( "showrdate" );             
                columnNames2.add ( "Date" );
                columnNames.add ( "Cost" );               
                columnNames2.add ( "Incurred Cost" );
                columnNames.add ( "Value" );             
                columnNames2.add ( "Produced Value" );
            }else if ( reportMasterType.equals ( "PMPH" ) ) 
            {
                columnNames.add ( "showrdate" );                
                columnNames2.add ( "Date" );
                columnNames.add ( "PMPH" );                  
                columnNames2.add ( "PMPH" );
            }else if ( reportMasterType.equals ( "COPQ" ) ) 
            {
                columnNames.add ( "showrdate" );              
                columnNames2.add ( "Date" );
                columnNames.add ( "COPQ" );                  
                columnNames2.add ( "Cost of poor Quality" );
            }else if ( reportMasterType.equals ( "OEE" ) ) 
            {
                columnNames.add ( "date" );
              //  columnNames.add ( "showrdate" );                 
                columnNames2.add ( "Date" );                        
                columnNames.add ( "oee" );           
                columnNames2.add ( "actual_min" );                        
                /*columnNames.add ( "DAVLHRS" );                
                columnNames2.add ( "Availability" );                     
                columnNames.add ( "outputqty" );               
                columnNames2.add ( "Efficiency" );                        
                columnNames.add ( "actual_hours" );              
                columnNames2.add ( "Quality" );                          
                columnNames.add ( "rejection" ); 
                columnNames.add ("IDEAL_PROCESS_TIME");
                columnNames.add ( "TG_OPT_HR" ); 
                columnNames.add ( "total_min" ); 
                columnNames.add ( "product" ); 
                columnNames.add ( "customer_name" ); 
                columnNames.add ( "sales_rate" );*/ 
                
            }

            for ( counter = 0 ; counter < records.length () ; counter ++ ) {
                Vector<Object> vector = new Vector<Object> ();

                String[] ab = new String[ columnNames.size () ];

//                        if ( reportMasterType.equals ( "Cost VS Value" ) || reportMasterType.equals ( "PPM" ) ) {
//                            ab = new String[ 3 ];
//                        }
                for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {
                    emp = records.getJSONObject ( counter );
                    
//                            if ( reportMasterType.equals ( "Production Value" ) && columnIndex == 1 ) {
//                                try {
//                                    vector.add ( new String ( df.format ( Float.parseFloat ( emp.get ( columnNames.get ( 2 ) ).toString () ) * Float.parseFloat ( emp.get ( columnNames.get ( 4 ) ).toString () ) ) ) );
//                                } catch ( NullPointerException | NumberFormatException e ) {
//                                    vector.add ( "--" );
//                                }
//                            } else 
                    if ( reportMasterType.equals ( "Productivity" ) ) {

                        if ( columnIndex == 0 ) {
                            
                            if ( reportType.equalsIgnoreCase ( "Daily" ) ) {
                                    vector.add ( label );
                            }else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {
                                vector.add ( label );
                            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {
                                vector.add ( label );
                            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {
                                vector.add ( label );
                            } else {
                                vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                            }
                            
                        } else {
                            try {
                                //    vector.add ( new String ( df.format ( ( Float.parseFloat ( emp.get ( columnNames.get ( 1 ) ).toString () ) / (Float.parseFloat ( emp.get ( columnNames.get ( 3 ) ).toString () )  * Float.parseFloat ( emp.get ( columnNames.get ( 4 ) ).toString () ) ) )  *100 ) ) );
                                    vector.add ( new String ( df.format (  Float.parseFloat ( emp.get ( columnNames.get ( 1 ) ).toString () )     *100 ) ) );
                                } catch ( NullPointerException | NumberFormatException e ) {
                                    vector.add ( "--" );
                                }
                            
                        }

                    } else if ( reportMasterType.equals ( "Cost VS Value" ) ) {

                        try {

                            if ( columnIndex == 0 ) {

                                if ( reportType.equalsIgnoreCase ( "Daily" ) ) {
                                    vector.add ( label );
                                }else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {
                                    vector.add ( label );
                                } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {
                                    vector.add ( label );
                                } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {
                                    vector.add ( label );
                                } else {
                                    vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                                }

                            } else {
                                
                                try {
                                    vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                                } catch ( NullPointerException | NumberFormatException e ) {
                                    vector.add ( "--" );
                                }
                            }

                        } catch ( NullPointerException | NumberFormatException r ) {
                            //  ab[ columnIndex ] = "-- ";
                        }

                    } else if ( reportMasterType.equals ( "PMPH" ) ) {
                        
                          try {

                            if ( columnIndex == 0 ) {

                                if ( reportType.equalsIgnoreCase ( "Daily" ) ) {
                                    vector.add ( label );
                                }else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {
                                    vector.add ( label );
                                } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {
                                    vector.add ( label );
                                } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {
                                    vector.add ( label );
                                } else {
                                    vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                                }

                            } else {
                                
                                try {
                                    vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                                } catch ( NullPointerException | NumberFormatException e ) {
                                    vector.add ( "--" );
                                }
                            }
                        
                         } catch ( NullPointerException | NumberFormatException r ) {
                            //  ab[ columnIndex ] = "-- ";
                        }
                        
                        
                        
                    }else if ( reportMasterType.equals ( "PMPH" ) ) {
                        
                          try {

                            if ( columnIndex == 0 ) {

                                if ( reportType.equalsIgnoreCase ( "Daily" ) ) {
                                    vector.add ( label );
                                }else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {
                                    vector.add ( label );
                                } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {
                                    vector.add ( label );
                                } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {
                                    vector.add ( label );
                                } else {
                                    vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                                }

                            } else {
                                
                                try {
                                    vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                                } catch ( NullPointerException | NumberFormatException e ) {
                                    vector.add ( "--" );
                                }
                            }
                        
                         } catch ( NullPointerException | NumberFormatException r ) {
                            //  ab[ columnIndex ] = "-- ";
                        }
                        
                        
                        
                    }else if ( reportMasterType.equals ( "OEE" ) ) {
                      
                       
//                        System.err.println(df.format ( Float.parseFloat ( emp.get ( columnNames.get (1 ) ).toString () )));
                       if ( columnIndex == 0 ) {
                           try{
                            if ( reportType.equalsIgnoreCase ( "Daily" ) ) {
                                    vector.add ( label );
                            }else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {
                                    vector.add ( label );
                            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {
                                    vector.add ( label );
                            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {
                                    vector.add ( label );
                            } else {
                                
                                    vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                            }
                           }catch(Exception ex){System.err.println(ex);}

                        } else if ( columnIndex == 1 ) {
                            try {
//                            System.err.println(df.format ( Float.parseFloat ( emp.get ( columnNames.get (2 ) ).toString () )));
                              vector.add ( new String (df.format ( Float.parseFloat ( emp.get ( columnNames.get (1) ).toString () ))));
                            } catch ( NullPointerException | NumberFormatException e ) {
                                vector.add ( "--" );
                            }
                        } /*else if ( columnIndex == 2 ) {
                            try {
                                vector.add ( new String ( df.format ( ( Float.parseFloat ( emp.get ( columnNames.get ( 1 ) ).toString () ) / ( Float.parseFloat ( emp.get ( columnNames.get ( 2 ) ).toString () ) * 60 ) ) * 100 ) ) );
                            } catch ( NullPointerException | NumberFormatException e ) {
                                vector.add ( "--" );
                            }
                        } else if ( columnIndex == 3 ) {
                            try {
                                String str = new String ( df.format ( ( ( ( Float.parseFloat ( emp.get ( columnNames.get ( 3 ) ).toString () ) * Float.parseFloat ( emp.get ( columnNames.get ( 6 ) ).toString () ) ) / ( Float.parseFloat ( emp.get ( columnNames.get ( 4 ) ).toString () ) * 60 ) ) ) ) );
                                //String str  = new String(df.format( ( ( Float.parseFloat(emp.get(columnNames.get(3)).toString ())  *  Float.parseFloat(emp.get(columnNames.get(6)).toString ())  ) / Float.parseFloat(emp.get(columnNames.get(4)).toString ())  * 60  )*100 ) ) ;
                                //System.out.println ( "  "+Float.parseFloat(emp.get(columnNames.get(3)).toString ()) + "   "+Float.parseFloat(emp.get(columnNames.get(6)).toString ()) + "   "+Float.parseFloat(emp.get(columnNames.get(4)).toString ())*60);

                                vector.add ( str );
                            } catch ( NullPointerException | NumberFormatException e ) {
                                vector.add ( "--" );
                            }
                        } else if ( columnIndex == 4 ) {
                            try {
                                vector.add ( new String ( df.format ( ( ( Float.parseFloat ( emp.get ( columnNames.get ( 3 ) ).toString () ) - Float.parseFloat ( emp.get ( columnNames.get ( 5 ) ).toString () ) ) / Float.parseFloat ( emp.get ( columnNames.get ( 3 ) ).toString () ) ) ) ) );
                            } catch ( NullPointerException | NumberFormatException e ) {
                                vector.add ( "--" );
                            }
                        }*/
                    } else {

                        Object s = emp.get ( columnNames.get ( columnIndex ) );
                        String s1 = emp.get ( columnNames.get ( columnIndex ) ).toString();
                        try {
                            
                                if( columnIndex>0 ){
                                    
                                    if( s!=null && ! s1.equals("null") ){
                                    
                                        if ( s instanceof Double ) {
                                            vector.add ( new String ( df.format ( Float.parseFloat ( emp.get ( columnNames.get ( columnIndex ) ).toString () ) ) ) );
                                        } else if ( s instanceof Integer ) {
                                            vector.add ( new String ( df.format ( Integer.parseInt ( emp.get ( columnNames.get ( columnIndex ) ).toString () ) ) ) );
                                        } else {
                                            vector.add ( s );
                                        }
                                        
                                    }else{
                                       vector.add ( "0" );
                                    }
                                }else{
                                    if ( reportType.equalsIgnoreCase ( "Daily" ) ) {
                                        vector.add ( label );
                                    }else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {
                                        vector.add ( label );
                                    } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {
                                        vector.add ( label );
                                    } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {
                                        vector.add ( label );
                                    } else {
                                        vector.add ( s );
                                    }
                                }

                        } catch ( NullPointerException e ) {
                            vector.add ( "--" );
                        }
                    }

                    String s1;
                    Object abc;

//                            if ( reportMasterType.equals ( "Production Value" ) && columnIndex == 1 ) {
//                                try {
//                                    ab[ 1 ] = df.format ( Integer.parseInt ( emp.get ( columnNames.get ( 2 ) ).toString () ) * Integer.parseInt ( emp.get ( columnNames.get ( 4 ) ).toString () ) );
//                                } catch ( NullPointerException | NumberFormatException e ) {
//                                    vector.add ( "--" );
//                                }
//                            } else
                    if ( reportMasterType.equals ( "Productivity" ) ) {

                        if ( columnIndex > 0 ) {
                            
                            if(columnIndex==1)
                            {
                                double  a  = Float.parseFloat ( emp.get ( columnNames.get ( 1 ) ).toString ()) ;
                                //   double b = Float.parseFloat ( emp.get ( columnNames.get ( 3 ) ).toString () ) *   Float.parseFloat ( emp.get ( columnNames.get ( 4 ) ).toString () ) ;
                                
                                ab[ columnIndex ] = new String ( df.format ( (a ) *100   )   ) ;
                            }
                            /*                            
                            ab[ columnIndex ] = "0";
                            abc = emp.get ( columnNames.get ( columnIndex ) ).toString();    
                            
                            System.err.println();
                            if( ! abc.equals( "null")   ){
                                
                                if ( abc instanceof Double ) {
                                    try {
                                        ab[ columnIndex ] = df.format ( Float.parseFloat ( abc.toString() )  );
                                        System.err.println("COLOUMN-INDEX :"+columnIndex);
                            
                                    } catch ( NullPointerException | NumberFormatException r ) {
                                        //vector.add ( "--" );
                                        ab[ columnIndex ] = "0.0" ;
                                    }
                                } else if ( abc instanceof Integer ) {
                                    try {
                                        
                                        ab[ columnIndex ] = df.format ( Integer.parseInt ( abc.toString ()));
                                        System.err.println("INTCOLOUMN-INDEX :"+columnIndex);
                                        System.err.println("INTTEST VALUE    :"+df.format ( Integer.parseInt ( abc.toString ())));

                                    } catch ( NullPointerException | NumberFormatException r ) {
                                         ab[ columnIndex ] = "0" ;
                                    }
                                }else{

                                    ab[ columnIndex ] =   abc.toString () ;
                                }
                            }else{
                                ab[ columnIndex ] = "0";
                            }
                        } else {*/
                            
                            if ( reportType.equalsIgnoreCase ( "Daily" ) ) {
                                    ab[ 0 ] = label ;
                            }else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {
                                ab[ 0 ] = label;
                            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {
                                ab[ 0 ] = label;
                            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {
                                ab[ 0 ] = label;
                            } else {
                                ab[ 0 ] = emp.get ( columnNames.get ( 0 ) ).toString ();
                            }
                        }

                    } else if ( reportMasterType.equals ( "Cost VS Value" ) ) {

                        if ( columnIndex > 0 ) {
                            abc = emp.get ( columnNames.get ( columnIndex ) ).toString();                           
                            if( ! abc.equals( "null")   ){
                                if ( abc instanceof Double ) {
                                    try {
                                        ab[ columnIndex ] = df.format ( Float.parseFloat ( abc.toString() )  );
                                    } catch ( NullPointerException | NumberFormatException r ) {
                                        //vector.add ( "--" );
                                        ab[ columnIndex ] = "0.0" ;
                                    }
                                } else if ( abc instanceof Integer ) {
                                    try {
                                        ab[ columnIndex ] = df.format ( Integer.parseInt ( abc.toString () ) );
                                    } catch ( NullPointerException | NumberFormatException r ) {
                                         ab[ columnIndex ] = "0" ;
                                    }
                                }else{

                                    ab[ columnIndex ] =   abc.toString () ;
                                }
                            }else{
                                ab[ columnIndex ] = "0";
                            }
                        } else {
                            
                            if ( reportType.equalsIgnoreCase ( "Daily" ) ) {
                                    ab[ 0 ] = label ;
                            }else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {
                                ab[ 0 ] = label;
                            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {
                                ab[ 0 ] = label;
                            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {
                                ab[ 0 ] = label;
                            } else {
                                ab[ 0 ] = emp.get ( columnNames.get ( 0 ) ).toString ();
                            }
                        }

                    }else if ( reportMasterType.equals ( "PMPH" ) ) {
                        if ( columnIndex > 0 ) {
                            abc = emp.get ( columnNames.get ( columnIndex ) ).toString();                           
                            if( ! abc.equals( "null")   ){
                                if ( abc instanceof Double ) {
                                    try {
                                        ab[ columnIndex ] = df.format ( Float.parseFloat ( abc.toString() )  );
                                    } catch ( NullPointerException | NumberFormatException r ) {
                                        //vector.add ( "--" );
                                        ab[ columnIndex ] = "0.0" ;
                                    }
                                } else if ( abc instanceof Integer ) {
                                    try {
                                        ab[ columnIndex ] = df.format ( Integer.parseInt ( abc.toString () ) );
                                    } catch ( NullPointerException | NumberFormatException r ) {
                                         ab[ columnIndex ] = "0" ;
                                    }
                                }else{

                                    ab[ columnIndex ] =   abc.toString () ;
                                }
                            }else{
                                ab[ columnIndex ] = "0";
                            }
                        } else {
                            
                            if ( reportType.equalsIgnoreCase ( "Daily" ) ) {
                                    ab[ 0 ] = label ;
                            }else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {
                                ab[ 0 ] = label;
                            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {
                                ab[ 0 ] = label;
                            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {
                                ab[ 0 ] = label;
                            } else {
                                ab[ 0 ] = emp.get ( columnNames.get ( 0 ) ).toString ();
                            }
                        }
                      

                    }else if ( reportMasterType.equals ( "COPQ" ) ) {

                        
                        if ( columnIndex > 0 ) {
                            abc = emp.get ( columnNames.get ( columnIndex ) ).toString();                           
                            if( ! abc.equals( "null")   ){
                                if ( abc instanceof Double ) {
                                    try {
                                        ab[ columnIndex ] = df.format ( Float.parseFloat ( abc.toString() )  );
                                    } catch ( NullPointerException | NumberFormatException r ) {
                                        //vector.add ( "--" );
                                        ab[ columnIndex ] = "0.0" ;
                                    }
                                } else if ( abc instanceof Integer ) {
                                    try {
                                        ab[ columnIndex ] = df.format ( Integer.parseInt ( abc.toString () ) );
                                    } catch ( NullPointerException | NumberFormatException r ) {
                                         ab[ columnIndex ] = "0" ;
                                    }
                                }else{

                                    ab[ columnIndex ] =   abc.toString () ;
                                }
                            }else{
                                ab[ columnIndex ] = "0";
                            }
                        } else {
                            
                            if ( reportType.equalsIgnoreCase ( "Daily" ) ) {
                                    ab[ 0 ] = label ;
                            }else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {
                                ab[ 0 ] = label;
                            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {
                                ab[ 0 ] = label;
                            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {
                                ab[ 0 ] = label;
                            } else {
                                ab[ 0 ] = emp.get ( columnNames.get ( 0 ) ).toString ();
                            }
                        }
                      

                    } else if ( reportMasterType.equals ( "OEE" ) ) {
                       
                        try {
                            if ( columnIndex == 1 ) {
                                
                                ab[ 1 ] = df.format(Float.parseFloat ( emp.get ( columnNames.get ( 1 ) ).toString () )) ;
                            } else if(columnIndex==0){
                                ab[ 0 ] = emp.get ( columnNames.get ( 0 ) ).toString ();
                            }
                        } catch ( NullPointerException | NumberFormatException r ) {
                            ab[ 1 ] = "-- ";
                        }

                    } else {
                        
                        
                        if ( columnIndex > 0 ) {
                            abc = emp.get ( columnNames.get ( columnIndex ) ).toString();                           
                            if( ! abc.equals( "null")   ){
                                if ( abc instanceof Double ) {
                                    try {
                                        ab[ columnIndex ] = df.format ( Float.parseFloat ( abc.toString() )  );
                                    } catch ( NullPointerException | NumberFormatException r ) {
                                        //vector.add ( "--" );
                                        ab[ columnIndex ] = "0.0" ;
                                    }
                                } else if ( abc instanceof Integer ) {
                                    try {
                                        ab[ columnIndex ] = df.format ( Integer.parseInt ( abc.toString () ) );
                                    } catch ( NullPointerException | NumberFormatException r ) {
                                         ab[ columnIndex ] = "0" ;
                                    }
                                }else{

                                    ab[ columnIndex ] =   abc.toString () ;
                                }
                            }else{
                                ab[ columnIndex ] = "0";
                            }
                        } else {
                            
                            if ( reportType.equalsIgnoreCase ( "Daily" ) ) {
                                    ab[ 0 ] = label ;
                            }else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {
                                ab[ 0 ] = label;
                            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {
                                ab[ 0 ] = label;
                            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {
                                ab[ 0 ] = label;
                            } else {
                                ab[ 0 ] = emp.get ( columnNames.get ( 0 ) ).toString ();
                            }
                        }
                    }
                }

                

                if ( vector.get ( 0 ) != null && vector.get ( 1 ) != null ) {
                    data.add ( vector );
                }
                
                reportData.add ( ab );
//                reportData.remove (  0  );
//                reportData.add (  ab );

                jTable1.setModel ( new DefaultTableModel ( data , columnNames2 ) );

            }

        } catch ( JSONException | ClassCastException e ) {

            Vector<Object> emptyVal = new Vector<Object> ();
            //  empty.add();
            //  data.add( emptyVal );
            //data = null;
            //reportData = null;
            //columnNames = null;
        }


    }

    
    public void buildTableModelfromJSON_Custom_Prod ( String employeeJSONList , String reportType , String label ) {
        
            if( !  employeeJSONList.equals( "NA"  )       ){
                HashMap<String , Object> map = new HashMap<String , Object> ();
                JSONObject jObject = new JSONObject ( employeeJSONList );
                Iterator<?> keys = jObject.keys ();

                while ( keys.hasNext () ) {
                    String key = ( String ) keys.next ();
                    Object value = jObject.get ( key );
                    map.put ( key , value );
                }


                JSONObject st = null;

                try {

                    st = ( JSONObject ) map.get ( "data" );
                    records = st.getJSONArray ( "records" );


                    columnNames = new Vector<String> ();
                    columnNames2 = new Vector<String> ();

                    if ( reportMasterType.equals ( "Production Quantity" ) ) 
                    {

                        columnNames.add ( "showrdate" );               //columnNames.add ( "showrdate" );               
                        columnNames2.add ( "Date" );                    //columnNames2.add ( "Date" );
                        columnNames.add ( "outputqty" );                //columnNames.add ( "outputqty" );                  
                        columnNames2.add ( "Production" );          //columnNames2.add ( "Production" );


                    } else if ( reportMasterType.equals ( "Production Value" ) ) 
                    {

                        columnNames.add ( "showrdate" );                 
                        columnNames2.add ( "Date" );
                        columnNames.add ( "value" );                  
                        columnNames2.add ( "Production Value" );
                    }

                    Vector<Object> vector = new Vector<Object> ();
                     String[] ab = new String[ 2 ];
                     double productionCount = 0.0 ;
                    for ( counter = 0 ; counter < records.length () ; counter ++ ) {
                       
                        
                        Object s  = null;
                        for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {
                            emp = records.getJSONObject ( counter );

                                s = emp.get ( columnNames.get ( columnIndex ) );
                                String s1 = emp.get ( columnNames.get ( columnIndex ) ).toString();
                                try {

                                        if( columnIndex>0 ){

                                            if( s!=null && ! s1.equals("null") ){

                                              //  if ( s instanceof Integer ) {
                                             //       productionCount = productionCount + ( Integer.parseInt ( emp.get ( columnNames.get ( columnIndex ) ).toString () )  * 1.0 );
                                            //    }else if ( s instanceof Double ) {
                                                    productionCount = productionCount + Float.parseFloat ( emp.get ( columnNames.get ( columnIndex ) ).toString () ) ;
                                           //     } 
                                                
                                            }
                                        }

                                } catch ( NullPointerException e ) {
                                    //vector.add ( "--" );
                                    System.out.println ( ""+e.getMessage () );
                                }
                            
                            
                        }
                    }
                    
//--------------------------------------------                                          ----------  Production count  / Production Value  Table  START------------------   --------------------------------------------------------------------------------
                        vector.add ( label );
                        vector.add( productionCount  );
//--------------------------------------------                                          ----------  Production count  / Production Value  Table  END   ------------------   --------------------------------------------------------------------------------                                                                                                              

//--------------------------------------------                                          ----------  Production count  / Production Value  Graph  START------------------   --------------------------------------------------------------------------------
                        ab[ 0 ] = label ;
                        ab[1] = productionCount+"" ;
//--------------------------------------------                                          ----------  Production count  / Production Value  Graph  END   ------------------   --------------------------------------------------------------------------------    
                    
                    if ( vector.get ( 0 ) != null && vector.get ( 1 ) != null ) {
                            data.add ( vector );
                        }
                        reportData.add ( ab );
                        jTable1.setModel ( new DefaultTableModel ( data , columnNames2 ) );

                } catch ( JSONException | ClassCastException e ) {

                    Vector<Object> emptyVal = new Vector<Object> ();

                }
            }else{
                Vector<Object> vector = new Vector<Object> ();

                String[] ab = new String[ 2 ];
                
                vector.add ( label );
                vector.add( 0  );
                
                ab[ 0 ] = label ;
                ab[1] = 0+"" ;
                
                if ( vector.get ( 0 ) != null && vector.get ( 1 ) != null ) {
                            data.add ( vector );
                }

                reportData.add ( ab );
                jTable1.setModel ( new DefaultTableModel ( data , columnNames2 ) );
                
            }

    }
    
    
    public DefaultTableModel buildTableModel ( ResultSet rs , String reportType , String label )
            throws SQLException {

        //columnCount = 0;
        if ( rs != null ) {
            ResultSetMetaData metaData = rs.getMetaData ();

            // names of columns
            columnCount = metaData.getColumnCount ();
            if ( columnNames.size () < columnCount ) {
                for ( int column = 1 ; column <= columnCount ; column ++ ) {
                    columnNames.add ( metaData.getColumnName ( column ) );
                }
            }

            int j = 1;
            // data of the table
            while ( rs.next () ) {

                String[] ab = new String[ 2 ];

                if ( reportMasterType.equals ( "Cost VS Value" ) || reportMasterType.equals ( "PPM" ) ) {
                    ab = new String[ 3 ];
                }

                Vector<Object> vector = new Vector<Object> ();
                for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {

                    Object s = rs.getObject ( columnIndex );

                    if ( columnIndex == 1 ) {

                        if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {
                            vector.add ( label );
                        } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {
                            vector.add ( label );
                        } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {
                            vector.add ( label );
                        } else {
                            vector.add ( s );
                        }

                    } else {
                        if ( s instanceof Double ) {
                            vector.add ( new String ( df.format ( Float.parseFloat ( rs.getString ( columnIndex ) ) ) ) );
                        } else {
                            vector.add ( s );
                        }
                    }

                    String s1;
                    Object abc;

                    if ( columnIndex == 1 ) {

                        if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {
                            ab[ 0 ] = label;
                        } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {
                            ab[ 0 ] = label;
                        } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {
                            ab[ 0 ] = label;
                        } else {
                            ab[ 0 ] = rs.getString ( columnIndex );
                        }

                    } else if ( columnIndex == 2 ) {
                        s1 = rs.getString ( columnIndex );
                        abc = rs.getObject ( columnIndex );
                        if ( abc != null ) {

                            if ( abc instanceof Double ) {
                                ab[ 1 ] = df.format ( Float.parseFloat ( rs.getString ( columnIndex ) ) );

                            } else {
                                ab[ 1 ] = rs.getString ( columnIndex );
                            }

                        } else {
                            ab[ 1 ] = "0";
                        }

                    } else if ( columnIndex == 3 ) {

                        if ( reportMasterType.equals ( "Cost VS Value" ) || reportMasterType.equals ( "PPM" ) ) {
                            s1 = rs.getString ( columnIndex );
                            abc = rs.getObject ( columnIndex );
                            if ( abc != null ) {

                                if ( abc instanceof Double ) {
                                    ab[ 2 ] = df.format ( Float.parseFloat ( rs.getString ( columnIndex ) ) );

                                } else {
                                    ab[ 2 ] = rs.getString ( columnIndex );
                                }

                            } else {
                                ab[ 2 ] = "0";
                            }
                        }
                    }
                }
                data.add ( vector );
                abc.add ( ab );
            }
            //    generateBarChart ();
        } else {

            data = null;
            columnNames = null;
        }

        return new DefaultTableModel ( data , columnNames );

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
        setPreferredSize(new java.awt.Dimension(1282, 569));
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
        jScrollPane1.setBounds(270, 20, 1010, 240);

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
        dateChooserCombo3.setBounds(10, 90, 140, 30);

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
        jLabel2.setBounds(10, 180, 90, 20);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox1.setEnabled(false);
        jComboBox1.setNextFocusableComponent(jCheckBox1);
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(10, 200, 150, 30);

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
        jCheckBox1.setBounds(160, 200, 80, 30);

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Process");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(10, 230, 60, 20);

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
        jCheckBox2.setBounds(160, 250, 80, 30);

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Machine");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(10, 280, 60, 20);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox2.setEnabled(false);
        jComboBox2.setNextFocusableComponent(jCheckBox3);
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox2);
        jComboBox2.setBounds(10, 300, 150, 30);

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
        jCheckBox3.setBounds(160, 300, 80, 30);

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Employee");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(10, 330, 70, 20);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox3.setEnabled(false);
        jComboBox3.setNextFocusableComponent(jCheckBox4);
        jPanel1.add(jComboBox3);
        jComboBox3.setBounds(10, 350, 150, 30);

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
        jCheckBox4.setBounds(160, 350, 80, 30);

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Shift");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(10, 380, 30, 20);

        jComboBox6.setEnabled(false);
        jPanel1.add(jComboBox6);
        jComboBox6.setBounds(10, 400, 150, 30);

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
        jCheckBox5.setBounds(160, 400, 80, 30);

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
        jButton2.setBounds(130, 485, 110, 35);

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
        jLabel6.setBounds(200, 10, 50, 16);

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Customer");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(10, 430, 70, 20);

        jComboBox7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox7.setEnabled(false);
        jPanel1.add(jComboBox7);
        jComboBox7.setBounds(10, 450, 150, 30);

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
        jCheckBox6.setBounds(160, 450, 80, 24);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Print");
        jButton3.setOpaque(false);
        jPanel1.add(jButton3);
        jButton3.setBounds(10, 525, 120, 35);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("Export");
        jButton4.setOpaque(false);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(130, 525, 110, 35);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Select Month");
        jLabel5.setPreferredSize(new java.awt.Dimension(70, 30));
        jPanel1.add(jLabel5);
        jLabel5.setBounds(10, 70, 80, 20);

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox8);
        jComboBox8.setBounds(10, 90, 140, 30);

        jComboBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox4.setEnabled(false);
        jComboBox4.setNextFocusableComponent(jCheckBox2);
        jPanel1.add(jComboBox4);
        jComboBox4.setBounds(10, 250, 150, 30);

        add(jPanel1);
        jPanel1.setBounds(0, 0, 250, 570);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Tabular View");
        add(jLabel9);
        jLabel9.setBounds(270, 0, 160, 20);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Graphical View");
        add(jLabel10);
        jLabel10.setBounds(270, 260, 180, 20);

        chartChoice.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        chartChoice.setNextFocusableComponent(jCheckBox2);
        chartChoice.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chartChoiceItemStateChanged(evt);
            }
        });
        add(chartChoice);
        chartChoice.setBounds(1110, 260, 140, 26);
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
    
  
/*
    public int getLastProcess ( String productId ) {

        ResultSet queryLastProcess = DB_Operations.executeSingle ( "SELECT REF_PROCESS_ID, max(FPM_ID) FROM FG_PROCESS_MACH_MAP WHERE REF_FG_ID = " + productId );

        try {
            if ( queryLastProcess != null && queryLastProcess.isBeforeFirst () ) {
                return queryLastProcess.getInt ( 1 );
            }
        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        return 0;
    }

    public float getRateforProductAndCustomer ( String productId , String customerId ) {

        ResultSet queryLastProcess = DB_Operations.executeSingle ( "SELECT SALES_RATE FROM FG_CUSTOMER_MAP WHERE M_FG_ID = " + productId + " AND  M_CUST_ID = " + customerId );

        try {
            if ( queryLastProcess != null && queryLastProcess.isBeforeFirst () ) {
                return queryLastProcess.getInt ( 1 );
            }
        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        return 0;
    }

    public float getProductionCost ( String productId ) {

        ResultSet queryProdCost = DB_Operations.executeSingle ( "SELECT PROD_COST FROM finished_goods WHERE FG_ID = " + productId );

        try {
            if ( queryProdCost != null && queryProdCost.isBeforeFirst () ) {
                return queryProdCost.getInt ( 1 );
            }
        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        return 0;
    }   */

    
    public void callDefaultReportView(){
        
        StringBuilder sb1 = new StringBuilder ();
        
         if ( reportMasterType.equals ( "Production Quantity" ) ) {
        sb1.append ( StaticValues.productionByQtyCall );
             } else if ( reportMasterType.equals ( "Production Value" ) ) {
                  sb1.append ( StaticValues.productionByvalueCall );
             } else if ( reportMasterType.equals ( "Work In Progress" ) ) {
                  sb1.append ( StaticValues.wipWithFilterCall );
             }else if ( reportMasterType.equals ( "Rejection" ) ) {
                 sb1.append( StaticValues.rejectionCall ) ;
             }else if ( reportMasterType.equals ( "Productivity" ) ) {
                 sb1.append ( StaticValues.productivityCall );
             }else if ( reportMasterType.equals ( "PPM" ) ) {
                 sb1.append ( StaticValues.ppmCall );
             }else if ( reportMasterType.equals ( "Cost VS Value" ) ) {
                 sb1.append ( StaticValues.costValueANDPMPHCall );
             }else if ( reportMasterType.equals ( "OEE" ) ) {
                 sb1.append ( StaticValues.OEECall );
             }else if ( reportMasterType.equals ( "Defect Occurrances" ) ) {
                 sb1.append ( StaticValues.defectOccurancesCall );
             }else if ( reportMasterType.equals ("Production to Sales Coverage Ratio" ) ) {
                 loadProductionTOSalesReport();
             }else if ( reportMasterType.equals ("Raw Material Efficiency" ) ) {
                 loadRMEfficiencyReport();
             }
        
             try {
                    sb1.append ( "to_date=" + URLEncoder.encode ( formatToDate_old (  ) , "UTF-8" ) + "&from_date=" + URLEncoder.encode ( formatFromDate_old ( )  , "UTF-8" ) );
                } catch ( UnsupportedEncodingException e ) {
                }
                sb1.append ( "&freq=daily" );
                sb1.append ( "&fgid="  );
                sb1.append ( "&processid="  );
                sb1.append ( "&machineid="  );
                sb1.append ( "&empid="  );
                sb1.append ( "&customer_id="  );
                sb1.append ( "&shift_id="  );
                String addEmpAPICall = sb1.toString ();
                String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                System.out.println ( "" + addEmpAPICall );
                if ( result2 != null ) {
                    buildTableModelfromJSON ( result2 , "Daily" , "xAxisLabel" ) ;
                }
//                if( reportMasterType.equals ( "Defect Occurrances")  ){
//                    chart = generateBarChart2 ( "Defect Occurrences" , "Defects" , "Occurrences" );
//                }else if( reportMasterType.equals ( "Cost VS Value")) {
//                   chart = generateBarChart2 ( "Defect Occurrences" , "Defects" , "Occurrences" );
//                }else if( reportMasterType.equals ( "Defect Occurrances")  ){
//                    chart = generateBarChartFromAPIData ( "Daily" + " " + reportMasterType , "Daily" , reportMasterType );
//                }else {
//                    chart = generateBarChartFromAPIData ( "Daily" + " " + reportMasterType , "Daily" , reportMasterType );
//                }
    }
    
    
    StringBuilder sb = new StringBuilder ();
    StringBuilder sb1 = new StringBuilder ();
    
    String process = "", product = "", machine = "", employee = "", shift = "", customerid = "";
    
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked

        showDurationDates(  dateChooserCombo3.getSelectedDate ().getTime () ,  dateChooserCombo4.getSelectedDate ().getTime ()  );
        
        StaticValues.setReportFromDate(   dateChooserCombo3.getSelectedDate ().getTime ()   ) ;
        StaticValues.setReportToDate    (   dateChooserCombo4.getSelectedDate ().getTime ()   ) ;
        
        StaticValues.setReportTypeNumber ( jComboBox5.getSelectedIndex ());
        StaticValues.setReportTypeString    (jComboBox5.getSelectedItem().toString());
        
        boolean allProduct = jCheckBox1.isSelected ();
        boolean allProcess = jCheckBox2.isSelected ();
        boolean allMachine = jCheckBox3.isSelected ();
        boolean allEmployee = jCheckBox4.isSelected ();
        boolean allShifts = jCheckBox5.isSelected ();
        boolean allCustomers = jCheckBox6.isSelected ();

        String chartType=chartChoice.getSelectedItem().toString().replace(" ", "");

        try {
            if (  ! allProduct ) {
                product = products.get ( jComboBox1.getSelectedIndex () ).FG_ID + "";
            } else {
            }
            if (  ! allEmployee ) {
                employee = employees.get ( jComboBox3.getSelectedIndex () ).EMP_ID + "";
            } else {
            }
            if (  ! allMachine ) {
                shift = shifts.get ( jComboBox6.getSelectedIndex () ).SHIFT_ID + "";
            } else {
            }
            if (  ! allProcess ) {
                process = processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID + "";
            } else {
            }
            if (  ! allShifts ) {
                machine = machines.get ( jComboBox2.getSelectedIndex () ).MC_ID + "";
            } else {
            }
            if (  ! allCustomers ) {
                customerid = customer.get ( jComboBox7.getSelectedIndex () ).CUST_ID + "";
            } else {
            }
        } catch ( Exception e ) {
            StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        String reportType = jComboBox5.getSelectedItem ().toString ();
        ReportType=reportType;
        System.out.println ( "Total no of days " + ( ( ( ( ( c2.getTimeInMillis () - c1.getTimeInMillis () ) / 1000 ) / 60 ) / 60 ) / 24 ) );

        int lastProcess;
        float rate, prod_cost;

        data = new Vector<Vector<Object>> ();
        columnNames = new Vector<String> ();
        abc = new ArrayList<String[]> ();
        reportData = new ArrayList<String[]> ();

        String masterQuery = "";
         sb = new StringBuilder ();
         sb1 = new StringBuilder ();

        boolean addAnd = false;

        if ( reportMasterType.equals ( "Production Quantity" ) ) {

            sb.append ( StaticValues.productionByQtyQuery );
            sb1.append ( StaticValues.productionByQtyCall );

        } else if ( reportMasterType.equals ( "Production Value" ) ) {

          //  lastProcess = getLastProcess ( product );
         //   rate = getRateforProductAndCustomer ( product , customerid );

            masterQuery = StaticValues.productionByvalueQuery.replace ( "productID" , product + "" );
            masterQuery = masterQuery.replace ( "customerID" , customerid + "" );
           // masterQuery = masterQuery.replace ( "processID" , lastProcess + "" );
         //   masterQuery = masterQuery.replace ( "rate" , rate + "" );
            sb.append ( masterQuery );

            // System.out.println ( ""+sb.toString () );
            allProcess = true;
            addAnd = true;
            jComboBox4.setEnabled ( false );
            jCheckBox2.setEnabled ( false );
            //jCheckBox1.setEnabled ( false );
            jCheckBox6.setEnabled ( false );

            sb1.append ( StaticValues.productionByvalueCall );

        } else if ( reportMasterType.equals ( "Work In Progress" ) ) {

            sb.append ( StaticValues.wipWithFiltersQuery );
            sb1.append ( StaticValues.wipWithFilterCall );

        } else if ( reportMasterType.equals ( "Rejection" ) ) {

            sb.append ( StaticValues.rejectionQuery );
            sb1.append( StaticValues.rejectionCall ) ;

        } else if ( reportMasterType.equals ( "Productivity" ) ) {

            sb.append ( StaticValues.productivityQuery );
            sb1.append ( StaticValues.productivityCall );

        } else if ( reportMasterType.equals ( "PPM" ) ) {

            sb.append ( StaticValues.ppmQuery );
            sb1.append ( StaticValues.ppmCall );

        } else if ( reportMasterType.equals ( "Cost VS Value" ) ) {

    
            masterQuery = StaticValues.costValueANDPMPHAggre.replace ( "productID" , product + "" );
     
            sb.append ( masterQuery );
            sb1.append ( StaticValues.costValueANDPMPHCall );
            
        
        }else if ( reportMasterType.equals ( "PMPH" ) ) {

    
            masterQuery = StaticValues.costValueANDPMPHAggre.replace ( "productID" , product + "" );
     
            sb.append ( masterQuery );
            sb1.append ( StaticValues.costValueANDPMPHCall );
            
        
        } else if ( reportMasterType.equals ( "COPQ" ) ) {

    
            masterQuery = StaticValues.costValueANDPMPHAggre.replace ( "productID" , product + "" );
     
            sb.append ( masterQuery );
            sb1.append ( StaticValues.costValueANDPMPHCall );
            
        
        }  else if ( reportMasterType.equals ( "OEE" ) ) {

            sb.append ( StaticValues.OEE );
            sb1.append ( StaticValues.OEECall );
        }else if ( reportMasterType.equals ( "Defect Occurrances" ) ) {

            sb.append ( StaticValues.defectOccurences );
            sb1.append ( StaticValues.defectOccurancesCall );
        }else if ( reportMasterType.equals ("Production to Sales Coverage Ratio" ) ) {
            
            loadProductionTOSalesReport();
            
        }else if ( reportMasterType.equals ("Raw Material Efficiency" ) ) {
            loadRMEfficiencyReport();
        }
        
        String contraintClause = "";

        if (  ! allProduct ) {

            if ( addAnd ) {
                sb.append ( " AND " );
            } else {
                addAnd = true;
            }

            if ( reportMasterType.equals ( "Work In Progress" ) ) {
                contraintClause = StaticValues.WIPproductContraint;
            } else {
                contraintClause = StaticValues.productContraint;
            }

            sb.append ( contraintClause.replace ( "productID" , product + "" ) );

        }

        if (  ! allProcess ) {

            if ( addAnd ) {
                sb.append ( " AND " );
            } else {
                addAnd = true;
            }

            if ( reportMasterType.equals ( "Work In Progress" ) ) {
                contraintClause = StaticValues.WIPprocessContrainnt;
            } else {
                contraintClause = StaticValues.processContrainnt;
            }

            sb.append ( contraintClause.replace ( "processID" , process + "" ) );
        }

        if (  ! allMachine ) {

            if ( addAnd ) {
                sb.append ( " AND " );
            } else {
                addAnd = true;
            }
            contraintClause = StaticValues.machineContraint;

        }

        if (  ! allEmployee ) {

            if ( addAnd ) {
                sb.append ( " AND " );
            } else {
                addAnd = true;
            }
            contraintClause = StaticValues.employeeContraint;
            sb.append ( contraintClause.replace ( "employeeID" , employee + "" ) );
        }

        if (  ! allShifts ) {

            if ( addAnd ) {
                sb.append ( " AND " );
            } else {
                addAnd = true;
            }
            contraintClause = StaticValues.shiftContraint;
            sb.append ( contraintClause.replace ( "shiftID" , shift + "" ) );
        }

        if (  ! allCustomers ) {

            if ( addAnd ) {
                sb.append ( " AND " );
            } else {
                addAnd = true;
            }
            contraintClause = StaticValues.customerContraint;
            sb.append ( contraintClause.replace ( "customerID" , customerid + "" ) );
        }

        String finalQuery = "", year = "";

        ResultSet result;

        switch ( reportType ) {
            
            case "Duration":

                            
//                            if ( result2 != null ) {
//                                buildTableModelfromJSON ( result2 , reportType ,  ""  ) ;
//                            }
                Thread t2 = new Thread(){
                    
                    public void run(){
                        
                             
                            if( reportMasterType.equals ( "Production Quantity" ) ||  reportMasterType.equals ( "Production Value" ) ){
                        
                                for ( int i = 0;  i < durationDates.size () ; i++ ) {

                                        try {
                                            sb1.append ( "to_date=" + URLEncoder.encode ( durationDates.get(i)[0] , "UTF-8" ) + "&from_date=" + URLEncoder.encode ( durationDates.get(i)[0]  , "UTF-8" ) );
                                        } catch ( UnsupportedEncodingException e ) {
                                        }
                                        sb1.append ( "&freq=daily" );
                                        sb1.append ( "&fgid=" + product );
                                        sb1.append ( "&processid=" + process );
                                        sb1.append ( "&machineid=" + machine );
                                        sb1.append ( "&empid=" + employee );
                                        sb1.append ( "&customer_id=" + customerid );
                                        sb1.append ( "&shift_id=" + shift );


                                        String addEmpAPICall = sb1.toString ();
                                        String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                                    
                                        System.out.println ( "" + addEmpAPICall );
                                        
                                        if ( result2 != null  && result2.contains("success")) {
                                            buildTableModelfromJSON_Custom_Prod ( result2 , reportType ,  durationDates.get(i)[1]  ) ;
                                        }else{
                                            buildTableModelfromJSON_Custom_Prod ( "NA" , reportType ,  durationDates.get(i)[1]  ) ;
                                        }

                                        sb1 = new StringBuilder ();

                                        if ( reportMasterType.equals ( "Production Quantity" ) ) {
                                            sb1.append ( StaticValues.productionByQtyCall );
                                        } else if ( reportMasterType.equals ( "Production Value" ) ) {
                                            sb1.append ( StaticValues.productionByvalueCall );
                                        }

                                        firstChartParameter=reportType + " " + reportMasterType;
                                        secondChartParameter=reportType;
                                        thirdChartParameter=reportMasterType;
                                        chart = generateBarChartFromAPIData ( reportType + " " + reportMasterType , reportType , reportMasterType ,chartType);


                                }
                            }else{
                                
                                try {
                                    sb1.append ( "to_date=" + URLEncoder.encode ( formatToDate_old (  ) , "UTF-8" ) + "&from_date=" + URLEncoder.encode ( formatFromDate_old ( )  , "UTF-8" ) );
                                } catch ( UnsupportedEncodingException e ) {
                                }
                                sb1.append ( "&freq=daily" );
                                sb1.append ( "&fgid=" + product );
                                sb1.append ( "&processid=" + process );
                                sb1.append ( "&machineid=" + machine );
                                sb1.append ( "&empid=" + employee );
                                sb1.append ( "&customer_id=" + customerid );
                                sb1.append ( "&shift_id=" + shift );


                                String addEmpAPICall = sb1.toString ();
                                String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                                
                                if ( result2 != null  ) {
                                        buildTableModelfromJSON ( result2 , reportType ,  ""  ) ;
                                }
                                
                                
                                sb1 = new StringBuilder ();

                                if ( reportMasterType.equals ( "Rejection" ) ) {
                                    sb1.append ( StaticValues.rejectionCall );
                                }else if ( reportMasterType.equals ( "PPM" ) ) {
                                    sb1.append ( StaticValues.ppmCall );
                                }else if ( reportMasterType.equals ( "Defect Occurrances" ) ) {
                                    sb1.append ( StaticValues.defectOccurancesCall );
                                }else if ( reportMasterType.equals ( "Productivity" ) ) {
                                    sb1.append ( StaticValues.productivityCall );
                                }else if ( reportMasterType.equals ( "Cost VS Value" ) ) {
                                    sb1.append ( StaticValues.costValueANDPMPHCall );
                                }else if ( reportMasterType.equals ( "PMPH" ) ) {
                                    sb1.append ( StaticValues.costValueANDPMPHCall );
                                }else if ( reportMasterType.equals ( "COPQ" ) ) {
                                    sb1.append ( StaticValues.costValueANDPMPHCall );
                                }else if ( reportMasterType.equals ( "Work In Progress" ) ) {
                                    sb1.append ( StaticValues.wipWithFilterCall );
                                } 
    //                            cp.revalidate ();
    //                            cp.repaint ();
                                if( reportMasterType.equals ( "Defect Occurrances")  ){

                                    firstChartParameter="Defect Occurrences";
                                    secondChartParameter="Defects";
                                    thirdChartParameter="Occurrences";
                                   chart = stackedBarChart("Defect Occurrences" , "Defects" , "Occurrences",chartType );         
                                  //  chart = generateBarChart2 ( "Defect Occurrences" , "Defects" , "Occurrences",chartType );
                                }else if( reportMasterType.equals ( "Cost VS Value")) {
                                    firstChartParameter="Cost VS Value";
                                    secondChartParameter="Cost";
                                    thirdChartParameter="Value";

                                   chart = generateBarChart2 ( "Cost VS Value" , "Cost" , "Value",chartType );
                                }else if( reportMasterType.equals ( "Defect Occurrances")  ){
                                    firstChartParameter=reportType + " " + reportMasterType;
                                    secondChartParameter=reportType;
                                    thirdChartParameter=reportMasterType;

                                    chart = generateBarChartFromAPIData ( reportType + " " + reportMasterType , reportType , reportMasterType ,chartType);                                
                                }else {
                                    firstChartParameter=reportType + " " + reportMasterType;
                                    secondChartParameter=reportType;
                                    thirdChartParameter=reportMasterType;
                                    chart = generateBarChartFromAPIData ( reportType + " " + reportMasterType , reportType , reportMasterType,chartType );
                                }

                                
                            }
                    }
                };
            
                t2.start();
                
            break ;
            
            case "Daily":

                showSingleMonths (  ) ;
                
                if ( reportMasterType.equals ( "Work In Progress" ) ) {
                    finalQuery = StaticValues.WIPdailyFreq;
                } else {
                    finalQuery = StaticValues.dailyFreq;
                }

                if ( addAnd ) {
                    sb.append ( " AND " );
                } else {
                    addAnd = true;
                }
                
                finalQuery = finalQuery.replace ( "fromdate" , formatFromDate_old( )  );
                finalQuery = finalQuery.replace ( "todate" , formatToDate_old (  )   );

                sb.append ( finalQuery );
//                for(String[] a:singleMonth)
//                {
//                    for(String s:a)
//                    {
//                      System.err.println("TEST SINGLE MONTH :-"+s);
//                        
//                    }
//                }
                
                Thread t = new Thread(){
                    
                    public void run(){
                        
                        
                        for ( int i = 0;  i < singleMonth.size () ; i++ ) {
                            
                            try {
                                //sb1.append ( "to_date=" + URLEncoder.encode ( formatToDate_old (  ) , "UTF-8" ) + "&from_date=" + URLEncoder.encode ( formatFromDate_old ( )  , "UTF-8" ) );
                                sb1.append ( "to_date=" + URLEncoder.encode ( singleMonth.get ( i )[0] , "UTF-8" ) + "&from_date=" + URLEncoder.encode ( singleMonth.get ( i )[0] , "UTF-8" ) );
                            } catch ( UnsupportedEncodingException e ) {
                            }
                            sb1.append ( "&freq=" );
                            sb1.append ( "&fgid=" + product );
                            sb1.append ( "&processid=" + process );
                            sb1.append ( "&machineid=" + machine );
                            sb1.append ( "&empid=" + employee );
                            sb1.append ( "&customer_id=" + customerid );
                            sb1.append ( "&shift_id=" + shift );


                            String addEmpAPICall = sb1.toString ();
                            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                            System.out.println ( "" + addEmpAPICall );
                            
                            if( reportMasterType.equals ( "Production Quantity" ) ||  reportMasterType.equals ( "Production Value" ) ){

                                if ( result2 != null  && result2.contains("success")) {
                                    buildTableModelfromJSON_Custom_Prod ( result2 , reportType ,   singleMonth.get ( i )[1]  ) ;
                                }else{
                                    buildTableModelfromJSON_Custom_Prod ( "NA" , reportType ,  singleMonth.get ( i )[1]  ) ;
                                }
    
                            }else{
                                    
                                if ( result2 != null  ) {
                                        buildTableModelfromJSON ( result2 , reportType ,  singleMonth.get ( i )[1]  ) ;
                                }
                            }

                            sb1 = new StringBuilder ();

                            if ( reportMasterType.equals ( "Production Quantity" ) ) {
                                sb1.append ( StaticValues.productionByQtyCall );
                            } else if ( reportMasterType.equals ( "Production Value" ) ) {
                                sb1.append ( StaticValues.productionByvalueCall );
                            }else if ( reportMasterType.equals ( "Rejection" ) ) {
                                sb1.append ( StaticValues.rejectionCall );
                            }else if ( reportMasterType.equals ( "PPM" ) ) {
                                sb1.append ( StaticValues.ppmCall );
                            }else if ( reportMasterType.equals ( "Defect Occurrances" ) ) {
                                sb1.append ( StaticValues.defectOccurancesCall );
                            }else if ( reportMasterType.equals ( "Productivity" ) ) {
                                sb1.append ( StaticValues.productivityCall );
                            }else if ( reportMasterType.equals ( "Cost VS Value" ) ) {
                                sb1.append ( StaticValues.costValueANDPMPHCall );
                            }else if ( reportMasterType.equals ( "PMPH" ) ) {
                                sb1.append ( StaticValues.costValueANDPMPHCall );
                            }else if ( reportMasterType.equals ( "COPQ" ) ) {
                                sb1.append ( StaticValues.costValueANDPMPHCall );
                            }else if ( reportMasterType.equals ( "Work In Progress" ) ) {
                                sb1.append ( StaticValues.wipWithFilterCall );
                            } 

                            if( reportMasterType.equals ( "Defect Occurrances")  ){
                                firstChartParameter="Defect Occurrences";
                                secondChartParameter="Defects";
                                thirdChartParameter="Occurrences";
       
                                chart = stackedBarChart("Defect Occurrences" , "Defects" , "Occurrences",chartType );
                            }else if( reportMasterType.equals ( "Cost VS Value")) {
                                firstChartParameter="Cost VS Value";
                                secondChartParameter="Defects";
                                thirdChartParameter="Occurrences";
                               chart = generateBarChart2 ( "Defect Occurrences" , "Defects" , "Occurrences" ,chartType);
                            }else if( reportMasterType.equals ( "Defect Occurrances")  ){
                                 firstChartParameter=reportType + " " + reportMasterType;
                                secondChartParameter=reportType;
                                thirdChartParameter=reportMasterType;
                                chart = generateBarChartFromAPIData ( reportType + " " + reportMasterType , reportType , reportMasterType,chartType );
                            }else {
                                firstChartParameter=reportType + " " + reportMasterType;
                                secondChartParameter=reportType;
                                thirdChartParameter=reportMasterType;
                                chart = generateBarChartFromAPIData ( reportType + " " + reportMasterType , reportType , reportMasterType ,chartType);
                            }
                            
                        }
                   }
                };
                
                t.start();
                
                break;

            case "Weekly":
                
                showWeek ();

                if ( addAnd ) {
                    sb.append ( " AND " );
                } else {
                    addAnd = true;
                }

                String fromWeekDate,
                 toWeekDate;

                for ( int i = 0, j = i + 1 ; j < weeklyDates.size () ; i = i + 2 , j += 2 ) {
                    try {
                        sb1.append ( "to_date=" + URLEncoder.encode ( weeklyDates.get ( j ) , "UTF-8" ) + "&from_date=" + URLEncoder.encode ( weeklyDates.get ( i ) , "UTF-8" ) );
                    } catch ( UnsupportedEncodingException e ) {
                    }
                    
                  //  sb1.append ( "&freq=weekly" );
                    sb1.append ( "&fgid=" + product );
                    sb1.append ( "&processid=" + process );
                    sb1.append ( "&machineid=" + machine );
                    sb1.append ( "&empid=" + employee );
                    sb1.append ( "&customer_id=" + customerid );
                    sb1.append ( "&shift_id=" + shift );

                    fromWeekDate = weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    
                    String addEmpAPICall = sb1.toString ();
                    String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

                    System.out.println ( "" + addEmpAPICall );
                    
                    if( reportMasterType.equals ( "Production Quantity" ) ||  reportMasterType.equals ( "Production Value" ) ){
                            if ( result2 != null  && result2.contains("success")) {
                                buildTableModelfromJSON_Custom_Prod ( result2 , reportType ,  fromWeekDate + " to " + toWeekDate  ) ;
                            }else{
                                buildTableModelfromJSON_Custom_Prod ( "NA" , reportType , fromWeekDate + " to " + toWeekDate  ) ;
                            }
                        }else{
                            if ( result2 != null  ) {
                                    buildTableModelfromJSON ( result2 , reportType ,  fromWeekDate + " to " + toWeekDate  ) ;
                            }
                    }

                    sb1 = new StringBuilder ();
                    
                    if ( reportMasterType.equals ( "Production Quantity" ) ) {
                        sb1.append ( StaticValues.productionByQtyCall );
                    } else if ( reportMasterType.equals ( "Production Value" ) ) {
                        sb1.append ( StaticValues.productionByvalueCall );
                    }else if ( reportMasterType.equals ( "Rejection" ) ) {
                        sb1.append ( StaticValues.rejectionCall );
                    }else if ( reportMasterType.equals ( "PPM" ) ) {
                        sb1.append ( StaticValues.ppmCall );
                    }else if ( reportMasterType.equals ( "Defect Occurrances" ) ) {
                        sb1.append ( StaticValues.defectOccurancesCall );
                    }else if ( reportMasterType.equals ( "Productivity" ) ) {
                        sb1.append ( StaticValues.productivityCall );
                    }else if ( reportMasterType.equals ( "Cost VS Value" ) ) {
                        sb1.append ( StaticValues.costValueANDPMPHCall );
                    }else if ( reportMasterType.equals ( "PMPH" ) ) {
                        sb1.append ( StaticValues.costValueANDPMPHCall );
                    }else if ( reportMasterType.equals ( "COPQ" ) ) {
                        sb1.append ( StaticValues.costValueANDPMPHCall );
                    }else if ( reportMasterType.equals ( "Work In Progress" ) ) {
                        sb1.append ( StaticValues.wipWithFilterCall );
                    } 
                    
                }

                 if( reportMasterType.equals ( "Defect Occurrances")  ){
                    firstChartParameter="Defect Occurrences";
                    secondChartParameter="Defects";
                    thirdChartParameter="Occurrences";  
                    chart = stackedBarChart ( "Defect Occurrences" , "Defects" , "Occurrences",chartType );
                }else if( reportMasterType.equals ( "Cost VS Value")) {

                    firstChartParameter="Cost VS Value";
                    secondChartParameter="Cost";
                    thirdChartParameter="Value";
                   chart = generateBarChart2 ( "Cost VS Value" , "Cost" , "Value",chartType );
                }else if( reportMasterType.equals ( "Defect Occurrances")  ){
                    firstChartParameter=reportType + " " + reportMasterType;
                    secondChartParameter=reportType;
                    thirdChartParameter=reportMasterType;
                    chart = generateBarChartFromAPIData ( reportType + " " + reportMasterType , reportType , reportMasterType,chartType );
                }else{
                    firstChartParameter=reportType + " " + reportMasterType  + " report for period " + weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + sdfTS.format ( c2.getTime () );
                    secondChartParameter=reportType;
                    thirdChartParameter=reportMasterType;
                    chart = generateBarChartFromAPIData ( reportType + " " + reportMasterType  + " report for period " + weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + sdfTS.format ( c2.getTime () ) , reportType , reportMasterType ,chartType);
                }

//                try {
//                    for ( int i = 0, j = i + 1 ; j < weeklyDates.size () ; i = i + 2 , j += 2 ) {
//
//                        if ( reportMasterType.equals ( "Work In Progress" ) ) {
//                            finalQuery = StaticValues.WIPweeklyFreq;
//                        } else {
//                            finalQuery = StaticValues.weeklyFreq;
//                        }
//
//                        finalQuery = finalQuery.replace ( "fromdate" , weeklyDates.get ( i ) );
//                        finalQuery = finalQuery.replace ( "todate" , weeklyDates.get ( j ) );
//                        //sb.append ( finalQuery );
//                        result = DB_Operations.executeSingle ( sb.toString () + " " + finalQuery );
//                        fromWeekDate = weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
//                        toWeekDate = weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
//                        jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
//                    }
//                    chart = generateBarChart ( reportType + " " + reportMasterType + " report for period " + weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + sdfTS.format ( c2.getTime () ) , reportType , reportMasterType );
//                } catch ( SQLException e2 ) {
//                    StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//                }
                break;

            case "Monthly":
                //sb.append ( StaticValues.monthFreq );
                
                year = showMonths ( selectedMonth );

                if ( addAnd ) {
                    sb.append ( " AND " );
                } else {
                    addAnd = true;
                }

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {
                
                    try {
                        sb1.append ( "to_date=" + URLEncoder.encode ( months.get ( j ) , "UTF-8" ) + "&from_date=" + URLEncoder.encode ( months.get ( i ) , "UTF-8" ) );
                    } catch ( UnsupportedEncodingException e ) {
                    }
                    
                  //  sb1.append ( "&freq=weekly" );
                    sb1.append ( "&fgid=" + product );
                    sb1.append ( "&processid=" + process );
                    sb1.append ( "&machineid=" + machine );
                    sb1.append ( "&empid=" + employee );
                    sb1.append ( "&customer_id=" + customerid );
                    sb1.append ( "&shift_id=" + shift );

                    String addEmpAPICall = sb1.toString ();
                    String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    
                    System.out.println ( "" + addEmpAPICall );
                  
                    if( reportMasterType.equals ( "Production Quantity" ) ||  reportMasterType.equals ( "Production Value" ) ){
                                if ( result2 != null  && result2.contains("success")) {
                                    buildTableModelfromJSON_Custom_Prod ( result2 , reportType ,  monthsTitles[ k ]  ) ;
                                }else{
                                    buildTableModelfromJSON_Custom_Prod ( "NA" , reportType , monthsTitles[ k ]  ) ;
                                }
                            }else{
                                if ( result2 != null  ) {
                                        buildTableModelfromJSON ( result2 , reportType ,  monthsTitles[ k ]  ) ;
                                }
                        }
                
                    
                    sb1 = new StringBuilder ();
                    
                    if ( reportMasterType.equals ( "Production Quantity" ) ) {
                        sb1.append ( StaticValues.productionByQtyCall );
                    } else if ( reportMasterType.equals ( "Production Value" ) ) {
                        sb1.append ( StaticValues.productionByvalueCall );
                    }else if ( reportMasterType.equals ( "Rejection" ) ) {
                        sb1.append ( StaticValues.rejectionCall );
                    }else if ( reportMasterType.equals ( "PPM" ) ) {
                        sb1.append ( StaticValues.ppmCall );
                    }else if ( reportMasterType.equals ( "Defect Occurrances" ) ) {
                        sb1.append ( StaticValues.defectOccurancesCall );
                    }else if ( reportMasterType.equals ( "Productivity" ) ) {
                        sb1.append ( StaticValues.productivityCall );
                    }else if ( reportMasterType.equals ( "Cost VS Value" ) ) {
                        sb1.append ( StaticValues.costValueANDPMPHCall );
                    }else if ( reportMasterType.equals ( "PMPH" ) ) {
                                sb1.append ( StaticValues.costValueANDPMPHCall );
                    }else if ( reportMasterType.equals ( "COPQ" ) ) {
                        sb1.append ( StaticValues.costValueANDPMPHCall );
                    }else if ( reportMasterType.equals ( "Work In Progress" ) ) {
                        sb1.append ( StaticValues.wipWithFilterCall );
                    }else if ( reportMasterType.equals ( "Per Man Per Hour" ) ) {
                        sb1.append ( StaticValues.costValueANDPMPHCall );
                    }  
                    
                }
                
                 if( reportMasterType.equals ( "Defect Occurrances")  ){
  
                    firstChartParameter="Defect Occurrences";
                    secondChartParameter="Defects";
                    thirdChartParameter="Occurrences";
   
                    chart = stackedBarChart ( "Defect Occurrences" , "Defects" , "Occurrences",chartType );
                }else if( reportMasterType.equals ( "Cost VS Value")) {
                    
                    firstChartParameter="Cost VS Value";
                    secondChartParameter= "Cost";
                    thirdChartParameter="Value";

                    chart = generateBarChart2 ( "Cost VS Value" , "Cost" , "Value",chartType );
                }else if( reportMasterType.equals ( "Defect Occurrances")  ){
                    firstChartParameter=reportType + " " + reportMasterType;
                    secondChartParameter=reportType;
                    thirdChartParameter=reportMasterType;

                    chart = generateBarChartFromAPIData ( reportType + " " + reportMasterType , reportType , reportMasterType,chartType );
                }else{
                    firstChartParameter=reportType + " " + reportMasterType  + " report for period " + months.get ( 0 ).split ( " " )[ 0 ] + " to " + months.get ( months.size () - 1 ).split ( " " )[ 0 ];
                    secondChartParameter="MONTHS";
                    thirdChartParameter=reportMasterType;

                    chart = generateBarChartFromAPIData ( reportType + " " + reportMasterType  + " report for period " + months.get ( 0 ).split ( " " )[ 0 ] + " to " + months.get ( months.size () - 1 ).split ( " " )[ 0 ] , "MONTHS", reportMasterType ,chartType);
                }
                
//                try {
//                    for ( int i = 0, j = i + 1, k = 0 ; j < months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {
//                        if ( reportMasterType.equals ( "Work In Progress" ) ) {
//                            finalQuery = StaticValues.WIPmonthFreq;
//                        } else {
//                            finalQuery = StaticValues.monthFreq;
//                        }
//
//                        finalQuery = finalQuery.replace ( "fromdate" , months.get ( i ) );
//                        finalQuery = finalQuery.replace ( "todate" , months.get ( j ) );
//
//                        result = DB_Operations.executeSingle ( sb.toString () + " " + finalQuery );
//                        jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );
//                    }
//                } catch ( SQLException e4 ) {
//                    StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e4.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e4.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//                }
//                chart = generateBarChart ( reportType + " " + reportMasterType + " report for period " + months.get ( 0 ).split ( " " )[ 0 ] + " to " + months.get ( months.size () - 1 ).split ( " " )[ 0 ] , "MONTHS" , reportMasterType );

                break;

            case "Quarterly":

                year = showQuarters (  selectedMonth );

                if ( addAnd ) {
                    sb.append ( " AND " );
                } else {
                    addAnd = true;
                }

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {
                
                try {
                        sb1.append ( "to_date=" + URLEncoder.encode ( quarters.get ( j ), "UTF-8" ) + "&from_date=" + URLEncoder.encode ( quarters.get ( i ) , "UTF-8" ) );
                    } catch ( UnsupportedEncodingException e ) {
                    }
                    
                  //  sb1.append ( "&freq=weekly" );
                    sb1.append ( "&fgid=" + product );
                    sb1.append ( "&processid=" + process );
                    sb1.append ( "&machineid=" + machine );
                    sb1.append ( "&empid=" + employee );
                    sb1.append ( "&customer_id=" + customerid );
                    sb1.append ( "&shift_id=" + shift );

                    String addEmpAPICall = sb1.toString ();
                    String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    
                    System.out.println ( "" + addEmpAPICall );
                    
                    
                    if( reportMasterType.equals ( "Production Quantity" ) ||  reportMasterType.equals ( "Production Value" ) ){
                                if ( result2 != null  && result2.contains("success")) {
                                    buildTableModelfromJSON_Custom_Prod ( result2 , reportType ,  quarterTitles[ k ]  ) ;
                                }else{
                                    buildTableModelfromJSON_Custom_Prod ( "NA" , reportType , quarterTitles[ k ]  ) ;
                                }
                            }else{
                                if ( result2 != null  ) {
                                        buildTableModelfromJSON ( result2 , reportType ,  quarterTitles[ k ] ) ;
                                }
                        }
                    
                    sb1 = new StringBuilder ();
                    
                    if ( reportMasterType.equals ( "Production Quantity" ) ) {
                        sb1.append ( StaticValues.productionByQtyCall );
                    } else if ( reportMasterType.equals ( "Production Value" ) ) {
                        sb1.append ( StaticValues.productionByvalueCall );
                    }else if ( reportMasterType.equals ( "Rejection" ) ) {
                        sb1.append ( StaticValues.rejectionCall );
                    }else if ( reportMasterType.equals ( "PPM" ) ) {
                        sb1.append ( StaticValues.ppmCall );
                    }else if ( reportMasterType.equals ( "Defect Occurrances" ) ) {
                        sb1.append ( StaticValues.defectOccurancesCall );
                    }else if ( reportMasterType.equals ( "Productivity" ) ) {
                        sb1.append ( StaticValues.productivityCall );
                    }else if ( reportMasterType.equals ( "Cost VS Value" ) ) {
                        sb1.append ( StaticValues.costValueANDPMPHCall );
                    }else if ( reportMasterType.equals ( "PMPH" ) ) {
                                sb1.append ( StaticValues.costValueANDPMPHCall );
                    }else if ( reportMasterType.equals ( "COPQ" ) ) {
                                sb1.append ( StaticValues.costValueANDPMPHCall );
                    }else if ( reportMasterType.equals ( "Work In Progress" ) ) {
                        sb1.append ( StaticValues.wipWithFilterCall );
                    } 
                }
                
                 if( reportMasterType.equals ( "Defect Occurrances")  ){
                     firstChartParameter="Defect Occurrences";
                    secondChartParameter="Defects";
                    thirdChartParameter="Occurrences";
                     chart = stackedBarChart ( "Defect Occurrences" , "Defects" , "Occurrences",chartType );
                }else if( reportMasterType.equals ( "Cost VS Value")) {
                    firstChartParameter="Cost VS Value";
                    secondChartParameter="Cost";
                    thirdChartParameter="Value";
                   chart = generateBarChart2 ( "Cost VS Value" , "Cost" , "Value",chartType );
                }else if( reportMasterType.equals ( "Defect Occurrances")  ){
                    firstChartParameter=reportType + " " + reportMasterType;
                    secondChartParameter=reportType;
                    thirdChartParameter=reportMasterType;
                    chart = generateBarChartFromAPIData ( reportType + " " + reportMasterType , reportType , reportMasterType ,chartType);
                }else{
                    firstChartParameter="Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 );
                    secondChartParameter="Quarters";
                    thirdChartParameter=reportMasterType;
                    chart = generateBarChartFromAPIData (  "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 )  , "Quarters" , reportMasterType ,chartType);
                }
                
//                try {
//                    for ( int i = 0, j = i + 1, k = 0 ; j < quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {
//
//                        if ( reportMasterType.equals ( "Work In Progress" ) ) {
//                            finalQuery = StaticValues.WIPquarterFreq;
//                        } else {
//                            finalQuery = StaticValues.quarterFreq;
//                        }
//
//                        finalQuery = finalQuery.replace ( "fromdate" , quarters.get ( i ) );
//                        finalQuery = finalQuery.replace ( "todate" , quarters.get ( j ) );
//                        result = DB_Operations.executeSingle ( sb.toString () + " " + finalQuery );
//                        jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );
//                    }
//                } catch ( SQLException e55 ) {
//                    StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e55.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e55.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//                }
//
//                chart = generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                break;

            case "Half Yearly":
                sb.append ( StaticValues.hyFreq );
                break;

            case "Yearly":
                //sb.append ( StaticValues.ytdFreq );
                year = showQuarters (  selectedMonth  );

                try {
                        sb1.append ( "to_date=" + URLEncoder.encode ( (Integer.parseInt ( year )+ 1) + "-03-31 00:00:00", "UTF-8" ) + "&from_date=" + URLEncoder.encode ( "" + year + "-04-01 00:00:00" , "UTF-8" ) );
                    } catch ( UnsupportedEncodingException e ) {
                    }
                    
                  //  sb1.append ( "&freq=weekly" );
                    sb1.append ( "&fgid=" + product );
                    sb1.append ( "&processid=" + process );
                    sb1.append ( "&machineid=" + machine );
                    sb1.append ( "&empid=" + employee );
                    sb1.append ( "&customer_id=" + customerid );
                    sb1.append ( "&shift_id=" + shift );

                     String addEmpAPICall = sb1.toString ();
                     String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    
                   // System.out.println ( "" + addEmpAPICall );
                   // System.out.println ( "" + addEmpAPICall );
                    
                    if( reportMasterType.equals ( "Production Quantity" ) ||  reportMasterType.equals ( "Production Value" ) ){
                                if ( result2 != null  && result2.contains("success")) {
                                    buildTableModelfromJSON_Custom_Prod ( result2 , reportType ,  reportMasterType  ) ;
                                }else{
                                    buildTableModelfromJSON_Custom_Prod ( "NA" , reportType , reportMasterType ) ;
                                }
                            }else{
                                if ( result2 != null  ) {
                                        buildTableModelfromJSON ( result2 , reportType ,  reportMasterType ) ;
                                }
                        }
                
                    if (reportMasterType.equals("Defect Occurrances")) {
                        firstChartParameter = "Defect Occurrences";
                        secondChartParameter = "Defects";
                        thirdChartParameter = "Occurrences";
                        chart = stackedBarChart("Defect Occurrences", "Defects", "Occurrences",chartType);
                     } else {
                        firstChartParameter="Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 );
                        secondChartParameter="Quarters";
                        thirdChartParameter=reportMasterType;
                        chart = generateBarChartFromAPIData("Quarterly production report for year " + year + " - " + (Integer.parseInt(year) + 1), "Quarters", reportMasterType,chartType);
                    }
                    
//                if ( reportMasterType.equals ( "Work In Progress" ) ) {
//                    finalQuery = StaticValues.WIPytdFreq;
//                } else {
//                    finalQuery = StaticValues.ytdFreq;
//                }
//                if ( addAnd ) {
//                    sb.append ( " AND " );
//                } else {
//                    addAnd = true;
//                }
//                finalQuery = finalQuery.replace ( "fromdate" , "" + year + "-04-01 00:00:00" );
//                finalQuery = finalQuery.replace ( "todate" , "" + Integer.parseInt ( year + 1 ) + "-03-31 00:00:00" );
//                sb.append ( finalQuery );
//                System.out.println ( "" + finalQuery );
//                try {
//                    result = DB_Operations.executeSingle ( sb.toString () );
//                    jTable1.setModel ( buildTableModel ( result , reportType , year + " - " + Integer.parseInt ( year + 1 ) ) );
//                    chart = generateBarChart ( reportType + " " + reportMasterType , reportType , reportMasterType );
//                } catch ( SQLException e ) {
//                    StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//                }

                break;

        }

        System.out.println ( "  " + sb.toString () );


    }//GEN-LAST:event_jButton1MouseClicked

    
    public void loadProductionTOSalesReport(){
        
        String _product, _totalOrderQty, _totalOrdervalueDefault, _totalOrderValueCustomerWise, _stockPresent, _valueInHand, _qtyShort;
        String apiName, apiResult  ;
        
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        Vector<String> item = new Vector<String>();
        
        ArrayList<String[]> _salesOrders = new ArrayList<String[]>();
        ArrayList<String[]> _customers = new ArrayList<String[]>();
        
        apiResult = WebAPITester.prepareWebCall ( "salesorders" , StaticValues.getHeader () , "" );
        HashMap<String , Object> map = new HashMap<String , Object> ();
        JSONObject jObject = new JSONObject ( apiResult );
        Iterator<?> keys = jObject.keys ();

        while ( keys.hasNext () ) {
            String key = ( String ) keys.next ();
            Object value = jObject.get ( key );
            map.put ( key , value );
        }

        JSONObject st = ( JSONObject ) map.get ( "data" );
        JSONArray records = st.getJSONArray ( "records" );

        JSONObject emp = null;
        _salesOrders = new ArrayList<String[]>();
        String[] values ;
        for ( int i = 0 ; i < records.length () ; i ++ ) {
            values = new String[  8  ] ;
            emp = records.getJSONObject ( i );
            
            values[0] =  emp.get ( "sales_po_id" ).toString () ;
            values[1] = emp.get ( "so_customer_id" ).toString () ;
            values[2] = emp.get ( "po_date" ).toString () ;
            values[3] = emp.get ( "order_no" ).toString () ;
            
            String apiResult2 = WebAPITester.prepareWebCall ( "podetailedit?order_detail_id="+emp.get ( "sales_po_id" ).toString () , StaticValues.getHeader () , "" );
            HashMap<String , Object> map2 = new HashMap<String , Object> ();
            JSONObject jObject2 = new JSONObject ( apiResult2 );
            Iterator<?> keys2 = jObject2.keys ();
            while ( keys2.hasNext () ) {
                String key = ( String ) keys2.next ();
                Object value = jObject2.get ( key );
                map2.put ( key , value );
            }
            
            try{
                //JSONObject st2 = ( JSONObject ) map2.get ( "data" );
                JSONArray records2 =  (JSONArray)     map2.get ( "data" );
                JSONObject  emp2 = null;
                for ( int j = 0 ; j < records2.length () ; j ++ ) {
                    emp2 = records2.getJSONObject ( j );

                    if( emp.get ( "sales_po_id" ).toString ().equals (   emp2.get ( "ref_po_id" ).toString ()  )){
                        values[4] =  emp2.get ( "product_id" ).toString () ;
                        values[5] = emp2.get ( "product_qty" ).toString () ;


                                String apiResult3 = WebAPITester.prepareWebCall ( "customermapedit?FG_C_ID="+emp2.get ( "product_id" ).toString ()+"&M_FG_ID="+emp.get ( "so_customer_id" ).toString () , StaticValues.getHeader () , "" );
                                HashMap<String , Object> map3 = new HashMap<String , Object> ();
                                JSONObject jObject3 = new JSONObject ( apiResult3 );


                                    Iterator<?> keys3 = jObject3.keys ();
                                    while ( keys3.hasNext () ) {
                                        String key = ( String ) keys3.next ();
                                        Object value = jObject3.get ( key );
                                        map3.put ( key , value );
                                    }

                                    JSONObject st3 = ( JSONObject ) map3.get ( "data" );
                                    JSONArray records3 = st3.getJSONArray ( "records" );
                                    JSONObject  emp3 = null;
                                    for ( int k = 0 ; j < records3.length () ; k ++ ) {
                                        emp3 = records3.getJSONObject ( k );

                                        if( emp.get ( "sales_po_id" ).toString ().equals (   emp2.get ( "ref_po_id" ).toString ()  )){
                                            values[6] =  emp3.get ( "SALES_RATE" ).toString () ;
                                        }                    
                                        //_products.add ( new String[]{  emp.get ( "FG_ID" ).toString () , emp.get ( "PART_NAME" ).toString () , emp.get ( "FG_UOM_ID" ).toString (), emp.get ( "SALES_RATE" ).toString (), } );
                                    }


                    }                    
                    //_products.add ( new String[]{  emp.get ( "FG_ID" ).toString () , emp.get ( "PART_NAME" ).toString () , emp.get ( "FG_UOM_ID" ).toString (), emp.get ( "SALES_RATE" ).toString (), } );
                }
            }catch(     Exception e ){
                
            }
            
            _salesOrders.add( values  ) ;
        }
        
        System.out.println ( ""+_salesOrders );
        
    }
            
    public void loadRMEfficiencyReport(){
                
    }        
    
    public void loadEntries000 ( String reportType ) {

        ResultSet result;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty',     rdate,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty'  from dailyreport where rdate >= '" + formatFromDate ( currentYear, nextYear,  _months[ jComboBox8.getSelectedIndex()]   ) + "' AND rdate <= '" + formatToDate ( currentYear, nextYear, _months[ jComboBox8.getSelectedIndex()]  ) + "'" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );
                firstChartParameter="Year to Date Production ";
                secondChartParameter="YEAR";
                thirdChartParameter="PRODUCTION";    
                generateBarChart (firstChartParameter,secondChartParameter,thirdChartParameter);

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                String query = "select  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + formatFromDate ( currentYear,  nextYear,   _months[ jComboBox8.getSelectedIndex()]  ) + "' AND rdate <= '" + formatToDate ( currentYear,  nextYear, _months[ jComboBox8.getSelectedIndex()]  ) + "'";
                result = DB_Operations.executeSingle ( query );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );
               
                firstChartParameter="Daily production report for period " + sdfTS.format ( c1.getTime () ) + " to " + sdfTS.format ( c2.getTime () );
                secondChartParameter="DAYS";
                thirdChartParameter="PRODUCTION";
                
                generateBarChart( firstChartParameter ,secondChartParameter,thirdChartParameter );

            } else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {

                showWeek ();

                xAxisLabel = "Week ";
                String fromWeekDate, toWeekDate;

                for ( int i = 0, j = i + 1 ; j < weeklyDates.size () ; i = i + 2 , j += 2 ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + weeklyDates.get ( i ) + "' AND rdate <= '" + weeklyDates.get ( j ) + "'" );
                    fromWeekDate = weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
                }
                firstChartParameter="Weekly production report for period " + weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + sdfTS.format ( c2.getTime () );
                secondChartParameter="WEEKS";
                thirdChartParameter="PRODUCTION";
                generateBarChart ( firstChartParameter , secondChartParameter , thirdChartParameter);

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = showMonths (  selectedMonth );

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + months.get ( i ) + "' AND rdate <= '" + months.get ( j ) + "'" );
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );
                }

                firstChartParameter="Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 );
                secondChartParameter="MONTHS";
                thirdChartParameter="PRODUCTION";
                generateBarChart ( firstChartParameter , secondChartParameter , thirdChartParameter );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = showQuarters(  selectedMonth  );

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + quarters.get ( i ) + "' AND rdate <= '" + quarters.get ( j ) + "'" );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );
                }

                firstChartParameter="Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 );
                secondChartParameter="Quarters";
                thirdChartParameter="PRODUCTION";
                
                generateBarChart ( firstChartParameter , secondChartParameter , thirdChartParameter );
                //generateBarChart ( "Quarterly production report for period "+sdfTS.format(c1.getTime())+" to "+sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }
    }

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        JCheckBox c = ( JCheckBox ) evt.getSource ();
        if ( c.isSelected () ) {
            jComboBox1.setEnabled ( false );

        } else {
            jComboBox1.setEnabled ( true );

        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
        // TODO add your handling code here:
        JCheckBox c = ( JCheckBox ) evt.getSource ();
        if ( c.isSelected () ) {
            jComboBox3.setEnabled ( false );

        } else {
            jComboBox3.setEnabled ( true );

        }
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
        JCheckBox c = ( JCheckBox ) evt.getSource ();
        if ( c.isSelected () ) {
            jComboBox4.setEnabled ( false );

        } else {
            jComboBox4.setEnabled ( true );

        }
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
        JCheckBox c = ( JCheckBox ) evt.getSource ();
        if ( c.isSelected () ) {
            jComboBox2.setEnabled ( false );

        } else {
            jComboBox2.setEnabled ( true );

        }
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        HomeScreen.home.removeForms ();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
        // TODO add your handling code here:
        JCheckBox c = ( JCheckBox ) evt.getSource ();
        if ( c.isSelected () ) {
            jComboBox6.setEnabled ( false );

        } else {
            jComboBox6.setEnabled ( true );

        }
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        if ( jLabel6.getText ().equals ( "Hide" ) ) {

            jPanel1.setBounds ( -220 , 0 , 260 , 610 );
            jLabel6.setText ( "Show" );

            jScrollPane1.setBounds ( 57 , 20 , 1233 , 240 );
            // cp.setBounds ( 60 , 280 , 1230 , 340 );
            //cp.setBounds ( 0 , 0 ,  , 340 );
            scroll.setBounds ( 57 , 290 , 1233 , 280 );

            jLabel9.setBounds ( 57 , 0 , 160 , 20 );
            jLabel10.setBounds ( 57 , 270 , 180 , 20 );
        } else if ( jLabel6.getText ().equals ( "Show" ) ) {

            jPanel1.setBounds ( 0 , 0 , 260 , 610 );
            jLabel6.setText ( "Hide" );

            jScrollPane1.setBounds ( 297 , 20 , 1000 , 240 );
            //cp.setBounds ( 300 , 280 , 980 , 340 );
            scroll.setBounds ( 297 , 290 , 1000 , 280 );

            jLabel9.setBounds ( 290 , 0 , 160 , 20 );
            jLabel10.setBounds ( 290 , 270 , 180 , 20 );
        }
        cp.revalidate ();
        cp.repaint ();
        repaint ();
        revalidate ();

    }//GEN-LAST:event_jLabel6MouseClicked

    private void jCheckBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox6ActionPerformed
        // TODO add your handling code here:
        JCheckBox c = ( JCheckBox ) evt.getSource ();
        if ( c.isSelected () ) {
            jComboBox7.setEnabled ( false );

        } else {
            jComboBox7.setEnabled ( true );

        }
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
       try{
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

    
    
    public void    showWeek () {

//        int i=0;
//        while( i < weeklyDates.size ()){
//            weeklyDates.remove ( i );
//            i++ ;
//        }
        weeklyDates = new ArrayList<String> ();

        formatFromDate ( currentYear, nextYear,  _months[ jComboBox8.getSelectedIndex()]   );
        formatToDate (  currentYear,  nextYear,  _months[ jComboBox8.getSelectedIndex()]   );

        totalNoOfDays = ( ( ( ( ( c2.getTimeInMillis () - c1.getTimeInMillis () ) / 1000 ) / 60 ) / 60 ) / 24 ) + 1;

        System.out.println ( "Total no of days " + totalNoOfDays );

        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );

        // calculation for first week of fromDate does not fall on Sunday
        String day = sdf.format ( c1.getTime () );

        //if(! (day.equalsIgnoreCase ( "Sunday"))){
        switch ( day ) {

            case "Sunday":
                c1.add ( Calendar.DATE , 0 );
                totalNoOfDays = totalNoOfDays - 0;
                break;
            case "Saturday":
                c1.add ( Calendar.DATE , 6 );
                totalNoOfDays = totalNoOfDays - 1;
                break;
            case "Monday":
                c1.add ( Calendar.DATE , 5 );
                totalNoOfDays = totalNoOfDays - 6;
                break;
            case "Tuesday":
                c1.add ( Calendar.DATE , 4 );
                totalNoOfDays = totalNoOfDays - 5;
                break;
            case "Wednesday":
                c1.add ( Calendar.DATE , 3 );
                totalNoOfDays = totalNoOfDays - 4;
                break;
            case "Thursday":
                c1.add ( Calendar.DATE , 2 );
                totalNoOfDays = totalNoOfDays - 3;
                break;
            case "Friday":
                c1.add ( Calendar.DATE , 1 );
                totalNoOfDays = totalNoOfDays - 2;
                break;

        }
        //}

        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );

        System.out.println ( "" );

        // Calculation for the week if fromDate fall on Sunday and all subsequent weeks till toDate does not fall on friday
        // Calculation for the last week when toDate fall on Friday
        // Calculation for inbetween weeks
        while ( totalNoOfDays > 1 ) {

            c1.add ( Calendar.DATE , 1 );
            weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );

            if ( ( int ) totalNoOfDays < 7 ) {
                switch ( ( int ) totalNoOfDays ) {
                    case 0:
                        c1.add ( Calendar.DATE , 0 );
                        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );
                        totalNoOfDays = totalNoOfDays - 0;
                        break;
                    case 1:
                        c1.add ( Calendar.DATE , 0 );
                        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );
                        totalNoOfDays = totalNoOfDays - 1;
                        break;
                    case 2:
                        c1.add ( Calendar.DATE , 1 );
                        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );
                        totalNoOfDays = totalNoOfDays - 2;
                        break;
                    case 3:
                        c1.add ( Calendar.DATE , 2 );
                        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );
                        totalNoOfDays = totalNoOfDays - 3;
                        break;
                    case 4:
                        c1.add ( Calendar.DATE , 3 );
                        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );
                        totalNoOfDays = totalNoOfDays - 4;
                        break;
                    case 5:
                        c1.add ( Calendar.DATE , 4 );
                        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );
                        totalNoOfDays = totalNoOfDays - 5;
                        break;
                    case 6:
                        c1.add ( Calendar.DATE , 5 );
                        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );
                        totalNoOfDays = totalNoOfDays - 5;
                        break;
                }
            } else {
                
                c1.add ( Calendar.DATE , 6 );
                weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );
                totalNoOfDays = totalNoOfDays - 7;
                
            }   
        }

        //   for( int i=0, j=i+1; j<weeklyDates.size ();  i=i+2, j+=2 ){
        //    System.out.println( weeklyDates.get ( i) +" "+  weeklyDates.get(j));
        //    }
    }
    
    public String showSingleMonths ( ) {

        singleMonth = new ArrayList<String[]>();   
        
        currentYear = new SimpleDateFormat("yyyy").format ( Calendar.getInstance ().getTime() );
        selectedMonth = new SimpleDateFormat("MMMM").format ( Calendar.getInstance ().getTime() );
        
        //formatFromDate (    );
        Calendar c = Calendar.getInstance ();
        
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
        
        
        
                

        // System.out.println( "From month is  "+ c1.getActualMaximum ( Calendar.DAY_OF_MONTH));
        // System.out.println( "From month is  "+sdf1.format(c1.getTime()));
        for ( int i = 1 ; i <= lastDateOfMonth ; i ++ ) {

            c.set ( Calendar.DATE , i );
            c.set ( Calendar.YEAR , Integer.parseInt (currentYear) );
            c.set ( Calendar.HOUR , 0 );
            c.set ( Calendar.MINUTE , 0 );
            c.set ( Calendar.SECOND , 0 );
            singleMonth.add ( new String[]{ sdf2.format ( c.getTime () ) + " 00:00:00" ,  new SimpleDateFormat("MMM-dd").format( c.getTime () ) });

//            c.set ( Calendar.DATE , i );
//            c.set ( Calendar.YEAR , Integer.parseInt ( nextYear )  );
//            c.set ( Calendar.HOUR , 0 );
//            c.set ( Calendar.MINUTE , 0 );
//            c.set ( Calendar.SECOND , 0 );
//            singleMonth.add ( sdf2.format ( c.getTime () ) + " 00:00:00" );

        }

        // System.out.println( "");
        // Calculation for the week if fromDate fall on Sunday and all subsequent weeks till toDate does not fall on friday
        // Calculation for the last week when toDate fall on Friday
        return sdf4.format ( c1.getTime () );
    }
    
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
    
    public String showMonths ( String _currentMonth ) {

        currentYear = new SimpleDateFormat("yyyy").format ( Calendar.getInstance ().getTime() );
        selectedMonth = new SimpleDateFormat("MMMM").format ( Calendar.getInstance ().getTime() );
           
        //formatFromDate (    );
        Calendar c = Calendar.getInstance ();
        
        int lastDateOfMonth = c1.getActualMaximum ( Calendar.DAY_OF_MONTH );
        if( selectedMonth.equals( "January"  ) || selectedMonth.equals( "February"  ) || selectedMonth.equals( "March"  ) ){
            nextYear =  currentYear ;
            currentYear = String.valueOf(  Integer.parseInt(  currentYear  ) -  1 )  ;
        }
        

        // System.out.println( "From month is  "+ c1.getActualMaximum ( Calendar.DAY_OF_MONTH));
        // System.out.println( "From month is  "+sdf1.format(c1.getTime()));
        for ( int i = 2 ; i <= 13 ; i ++ ) {

            c.set ( Calendar.DATE , 1 );
            c.set ( Calendar.MONTH , i + 1 );
            //c.set ( Calendar.YEAR , Integer.parseInt ( sdf4.format ( c1.getTime () ) ) );
            c.set ( Calendar.YEAR , Integer.parseInt (currentYear) );

            c.set ( Calendar.HOUR , 0 );
            c.set ( Calendar.MINUTE , 0 );
            c.set ( Calendar.SECOND , 0 );

            // System.out.print( sdf2.format ( c.getTime ()) +" 00:00:00" );
            months.add ( sdf2.format ( c.getTime () ) + " 00:00:00" );

            c.set ( Calendar.DATE , c.getActualMaximum ( Calendar.DAY_OF_MONTH ) );
            c.set ( Calendar.MONTH , i + 1 );
            //c.set ( Calendar.YEAR , Integer.parseInt ( sdf4.format ( c1.getTime () ) ) );
            c.set ( Calendar.YEAR , Integer.parseInt ( currentYear )  );

            c.set ( Calendar.HOUR , 0 );
            c.set ( Calendar.MINUTE , 0 );
            c.set ( Calendar.SECOND , 0 );

            //  System.out.println( "    "+sdf2.format ( c.getTime ())  +" 00:00:00" );
            months.add ( sdf2.format ( c.getTime () ) + " 00:00:00" );

        }

        // System.out.println( "");
        // Calculation for the week if fromDate fall on Sunday and all subsequent weeks till toDate does not fall on friday
        // Calculation for the last week when toDate fall on Friday
        return sdf4.format ( c1.getTime () );
    }
    public String showQuarters (  String _currentMonth ) {

       currentYear = new SimpleDateFormat("yyyy").format ( Calendar.getInstance ().getTime() );
        selectedMonth = new SimpleDateFormat("MMMM").format ( Calendar.getInstance ().getTime() );
           
        //formatFromDate (    );
        Calendar c = Calendar.getInstance ();
        
        int lastDateOfMonth = c1.getActualMaximum ( Calendar.DAY_OF_MONTH );
        if( selectedMonth.equals( "January"  ) || selectedMonth.equals( "February"  ) || selectedMonth.equals( "March"  ) ){
               nextYear =  currentYear ;
                currentYear = String.valueOf(  Integer.parseInt(  currentYear  ) -  1 )  ;
        }
        
        
        // System.out.println( "From month is  "+ c1.getActualMaximum ( Calendar.DAY_OF_MONTH));
        // System.out.println( "From month is  "+sdf1.format(c1.getTime()));
        for ( int i = 3 ; i <= 14 ; i += 3 ) {

            c.set ( Calendar.DATE , 1 );
            c.set ( Calendar.MONTH , i );
            //c.set ( Calendar.YEAR , Integer.parseInt ( sdf4.format ( c1.getTime () ) ) );
            c.set ( Calendar.YEAR , Integer.parseInt ( currentYear));

            c.set ( Calendar.HOUR , 0 );
            c.set ( Calendar.MINUTE , 0 );
            c.set ( Calendar.SECOND , 0 );

            // System.out.print( sdf2.format ( c.getTime ()) +" 00:00:00" );
            quarters.add ( sdf2.format ( c.getTime () ) + " 00:00:00" );

            c.set ( Calendar.MONTH , i + 2 );
            c.set ( Calendar.DATE , c.getActualMaximum ( Calendar.DAY_OF_MONTH ) );
            //c.set ( Calendar.YEAR , Integer.parseInt ( sdf4.format ( c1.getTime () ) ) );
            c.set ( Calendar.YEAR , Integer.parseInt ( currentYear));

            c.set ( Calendar.HOUR , 0 );
            c.set ( Calendar.MINUTE , 0 );
            c.set ( Calendar.SECOND , 0 );

            //  System.out.println( "    "+sdf2.format ( c.getTime ())  +" 00:00:00" );
            quarters.add ( sdf2.format ( c.getTime () ) + " 00:00:00" );

        }

        for ( int i = 0, j = i + 1 ; j < quarters.size () ; i = i + 2 , j += 2 ) {
            
            System.out.println ( quarters.get ( i ) + " " + quarters.get ( j ) );
        }

        // System.out.println( "");
        // Calculation for the week if fromDate fall on Sunday and all subsequent weeks till toDate does not fall on friday
        // Calculation for the last week when toDate fall on Friday
        return sdf4.format ( c1.getTime () );
    }

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

class graph extends Thread {

    String[] data;
    String reportTitle;
    String label;
    int max1;
    double max2;

    public graph ( String[] _data , String _reportTitle , String _label , int _max1 , double _max2 ) {

        data = _data;
        reportTitle = _reportTitle;
        label = _label;
        max1 = _max1;
        max2 = _max2;
    }

    @Override
    public void run () {
        super.run (); //To change body of generated methods, choose Tools | Templates.

        //      for ( int i = 0 ; i < data.length ; i ++ ) {
        int total = 0;
        int step = max1 / 20;
        int rem = max1 % 20;

        try {
            for ( int k = 1 ; k <= 20 ; k ++ ) {
                total = step * k;

                ReportsController.dataSet.addValue ( step * k , reportTitle , label );

                try {
                    sleep ( 10 + k );
                } catch ( InterruptedException IE1 ) {
                }
            }

            ReportsController.dataSet.addValue ( total + rem , reportTitle , label );

        } catch ( NumberFormatException e ) {

            double tota = 0.0;
            double step1 = max2 / 20;
            double rem1 = max2 % 20;

            for ( int k = 1 ; k <= 20 ; k ++ ) {

                tota = step1 * k;

                ReportsController.dataSet.addValue ( step1 * k , reportTitle , label );

                try {
                    sleep ( 10 + k );
                } catch ( InterruptedException IE1 ) {
                }
            }

            ReportsController.dataSet.addValue ( tota + rem1 , reportTitle , label );

            StaticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }
    }
}
