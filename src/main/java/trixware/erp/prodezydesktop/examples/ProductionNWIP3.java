/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;


import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;

import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import trixware.erp.prodezydesktop.Model.EmployeeDR;
import trixware.erp.prodezydesktop.Model.MachineDR;
import trixware.erp.prodezydesktop.Model.ProcessDR;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.ShiftDR;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
/**
 *
 * @author Rajesh
 */
public class ProductionNWIP3 extends javax.swing.JPanel {

    static ArrayList<ProductDR> products = null;
    ArrayList<EmployeeDR> employees = null;
    static ArrayList<ProcessDR> processes = null;
    ArrayList<MachineDR> machines = null;
    ArrayList<ShiftDR> shifts = null;

    String selectAll = "-- select --";

    ProductDR prdr = null;
    EmployeeDR empdr = null;
    ProcessDR prcdr = null;
    MachineDR mcdr = null;
    ShiftDR sftdr = null ;
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
    static DefaultCategoryDataset dataSet = null;
    static ArrayList<String[]> abc = null;
    ChartPanel cp = null;

    static Calendar c1 = Calendar.getInstance ();
    static Calendar c2 = Calendar.getInstance ();

    int columnCount;

    JFreeChart chart = null;
    static ReportsFilterPane filterPane = null;
    /**
     * Creates new form DailyReportForm
     */
    public ProductionNWIP3 () {
        initComponents ();

        filterPane = new ReportsFilterPane ();
        filterPane.setBounds ( 0, 0, 50, 650);
        add(filterPane);
        filterPane.setVisible ( true );
        revalidate ();
        repaint ();

        cp = new ChartPanel ( chart );
        cp.setPreferredSize ( new java.awt.Dimension ( 900 , 330 ) );
        cp.setBounds ( 320 , 300 , 950 , 330 );

        dataSet = new DefaultCategoryDataset ();

        for ( int i = 0 ; i < 5 ; i ++ ) {
            dataSet.setValue ( 0 , "Production" , String.valueOf ( i ) );
        }

        chart = ChartFactory.createBarChart3D (
                "Title Value" , "xAxis" , "yAxis" ,
                dataSet , PlotOrientation.VERTICAL , true , true , false );

        try {
            remove ( cp );
        } catch ( Exception e ) {
        }

       // add ( cp );

     //   HomeScreen.home.pack ();
     //  HomeScreen.home.revalidate ();
     //   HomeScreen.home.repaint ();

        //  jCheckBox1.setVisible ( false);
    }

    @Override
    protected void paintComponent ( Graphics g ) {
        super.paintComponent ( g );
        Graphics2D g2d = ( Graphics2D ) g;
        g2d.setRenderingHint ( RenderingHints.KEY_RENDERING , RenderingHints.VALUE_RENDER_QUALITY );
        int w = getWidth ();
        int h = getHeight ();
        Color color1 = Color.GRAY;
        Color color2 = Color.BLACK;
        GradientPaint gp = new GradientPaint ( 0 , 0 , color1 , 0 , h , color2 );
        g2d.setPaint ( gp );
        g2d.fillRect ( 0 , 0 , w , h );
    }

    public static void getReports(  String _reportType){
        
        
        
    }
    
      public JFreeChart generateBarChart2 ( String reportTitle , String xAxis , String yAxis ) {

          try {
            remove ( cp );
        } catch ( Exception e ) {
        }
          
        dataSet = new DefaultCategoryDataset ();

        for ( int i = 0 ; i < abc.size () ; i ++ ) {
            dataSet.setValue ( 0 , "Production" , abc.get ( i )[ 0 ] );
        }

        chart = ChartFactory.createBarChart3D (
                reportTitle , xAxis , yAxis ,
                dataSet , PlotOrientation.VERTICAL , true , true , false );

        cp = new ChartPanel ( chart );
        cp.setPreferredSize ( new java.awt.Dimension ( 900 , 330 ) );
        cp.setBounds ( 320 , 300 , 950 , 330 );

        

        add ( cp );

        HomeScreen.home.pack ();
        HomeScreen.home.revalidate ();
        HomeScreen.home.repaint ();

        Thread t = new Thread () {

            public void run () {

                try {
                    for ( int i = 0 ; i < abc.size () ; i ++ ) {

                        for ( int j = 0 ; j <= Integer.parseInt ( abc.get ( i )[ 1 ] ) ; j = j + 10 ) {

                            dataSet.setValue ( j , "Production" , abc.get ( i )[ 0 ] );
                            sleep ( 5 );

                            chart = ChartFactory.createBarChart3D (
                                    reportTitle , xAxis , yAxis ,
                                    dataSet , PlotOrientation.VERTICAL , true , true , false );

                            cp = new ChartPanel ( chart );
                            cp.setPreferredSize ( new java.awt.Dimension ( 900 , 330 ) );
                            cp.setBounds ( 320 , 300 , 950 , 330 );

                            

                            add ( cp );

                            HomeScreen.home.pack ();
                            HomeScreen.home.revalidate ();
                            HomeScreen.home.repaint ();
                        }
                    }
                } catch ( InterruptedException e ) {
                    System.out.println ( "Error while loading graph" );
                }
            }
        };

        t.start ();

        return chart;
    }

    public JFreeChart generateBarChart ( String reportTitle , String xAxis , String yAxis ) {

        try {
           // System.out.println("total components "+getComponentCount());
            //remove ( getComponentCount() );
            remove(cp ) ;
        } catch ( Exception e ) {
            System.out.println("Error component "+e.getMessage ());
        }
        
        dataSet = new DefaultCategoryDataset ();

        for ( int i = 0 ; i < abc.size () ; i ++ ) {
            dataSet.setValue ( Integer.parseInt ( abc.get ( i )[ 1 ] ) , "Production" , abc.get ( i )[ 0 ] );
        }

//		chart = ChartFactory.createBarChart3D(
//				"Daily Production", "Dates", "Production",
//				dataSet, PlotOrientation.VERTICAL, true, true, false);
        JFreeChart chart = ChartFactory.createBarChart3D (
                reportTitle, "Dates" , "Production" ,
                dataSet , PlotOrientation.VERTICAL , true , true , false );

       
        cp = new ChartPanel ( chart );
        cp.setPreferredSize ( new java.awt.Dimension ( 1270 , 330 ) );
        cp.setBounds ( 50 , 300 , 1270 , 330 );

        CategoryPlot plot = ( CategoryPlot ) chart.getPlot ();
        plot.setBackgroundPaint ( Color.lightGray );
        plot.setDomainGridlinePaint ( Color.white );
        plot.setDomainGridlinesVisible ( false );
        plot.setRangeGridlinePaint ( Color.white );

        CategoryAxis domainAxis = plot.getDomainAxis ();
        domainAxis.setCategoryLabelPositions ( CategoryLabelPositions.UP_45 );

        BarRenderer renderer = ( BarRenderer ) plot.getRenderer ();
        renderer.setBaseItemLabelsVisible ( true );
        renderer.setBaseItemLabelGenerator ( new StandardCategoryItemLabelGenerator () );
        renderer.setBarPainter ( new StandardBarPainter () );
        renderer.setSeriesPaint ( 0 , Color.blue );
        renderer.setSeriesItemLabelFont ( 1 , new Font ( "Times New Roman" , Font.BOLD , 10 ) );
        renderer.setSeriesItemLabelPaint ( 0 , Color.white );

        

        add ( cp );

        HomeScreen.home.pack ();
        HomeScreen.home.revalidate ();
        HomeScreen.home.repaint ();

        return chart;
    }
   
    
    public static JFreeChart generatePieChart () {
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

    
    
    
    

    @SuppressWarnings( "null" )
    public  void loadEntries ( String reportType , int product , String fromDate, String toDate) {

        ResultSet result;
        data = new Vector<Vector<Object>> ();
        columnNames = new Vector<String> ();
        abc = new ArrayList<String[]> ();

        remove(filterPane);
        revalidate ();
        repaint ();
        
        try {

            String xAxisLabel;

        //    int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty',     rdate,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty'  from dailyreport where rdate >= '" + fromDate + "' AND rdate <= '" +toDate + "' AND fgid=" + product );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );
                
                revalidate ();
                repaint ();

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                
                String query = "select  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + fromDate + "' AND rdate <= '" + toDate + "' AND fgid=" + product + " group by rdate";
                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                result = DB_Operations.executeSingle ( query );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                //generateBarChart ( "Daily production report for period " + filterPane.sdfTS.format (filterPane.c1.getTime () ) + " to " + filterPane.sdfTS.format ( filterPane.c2.getTime () ) , "DAYS" , "PRODUCTION" );

                generateBarChart ( "Daily production report for period " + fromDate + " to " + toDate , "DAYS" , "PRODUCTION" );
                
                
                
            } else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {

                filterPane.showWeek ();

                xAxisLabel = "Week ";
                String fromWeekDate, toWeekDate;

                for ( int i = 0, j = i + 1 ; j < filterPane.weeklyDates.size () ; i = i + 2 , j += 2 ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.weeklyDates.get ( i ) + "' AND rdate <= '" + filterPane.weeklyDates.get ( j ) + "' AND fgid=" + product );
                    fromWeekDate = filterPane.weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = filterPane.weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
                }

                generateBarChart ( "Weekly production report for period " + filterPane.weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "WEEKS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = filterPane.showMonths ();

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.months.get ( i ) + "' AND rdate <= '" + filterPane.months.get ( j ) + "' AND fgid=" + product );
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );
                }

                generateBarChart ( "Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "MONTHS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = filterPane.showQuarters ();

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j <filterPane.quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.quarters.get ( i ) + "' AND rdate <= '" + filterPane.quarters.get ( j ) + "' AND fgid=" + product );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );
                }

                generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                //generateBarChart ( "Quarterly production report for period "+sdfTS.format(c1.getTime())+" to "+sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {

        }
    }

    @SuppressWarnings( "null" )
    public  void loadEntriesNoFilters ( String reportType ) {

        ResultSet result;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty',     rdate,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                result = DB_Operations.executeSingle ( "select    showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty'  from dailyreport where rdate >= '" + filterPane.formatFromDate () + "' AND rdate <= '" + filterPane.formatToDate () + "'" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.formatFromDate () + "' AND rdate <= '" + filterPane.formatToDate () + "' group by rdate" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Daily production report for period " + filterPane.sdfTS.format ( c1.getTime () ) + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "DAYS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {

                filterPane.showWeek ();

                xAxisLabel = "Week ";
                String fromWeekDate, toWeekDate;

                for ( int i = 0, j = i + 1 ; j < filterPane.weeklyDates.size () ; i = i + 2 , j += 2 ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.weeklyDates.get ( i ) + "' AND rdate <= '" + filterPane.weeklyDates.get ( j ) + "'" );
                    fromWeekDate = filterPane.weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = filterPane.weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
                }

                generateBarChart ( "Weekly production report for period " + filterPane.weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "WEEKS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = filterPane.showMonths ();

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.months.get ( i ) + "' AND rdate <= '" + filterPane.months.get ( j ) + "'" );
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );
                }

                generateBarChart ( "Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "MONTHS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = filterPane.showQuarters ();

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.quarters.get ( i ) + "' AND rdate <= '" + filterPane.quarters.get ( j ) + "'" );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );
                }

                generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                //generateBarChart ( "Quarterly production report for period "+sdfTS.format(c1.getTime())+" to "+sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {

        }
    }

    public  void loadEntriesAllSelected ( String reportType , int product , int process , int machine , int employee, int shift ) {

        ResultSet result;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty',     rdate,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                result = DB_Operations.executeSingle ( "select     showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty'  from dailyreport where rdate >= '" + filterPane.formatFromDate () + "' AND rdate <= '" + filterPane.formatToDate () + "' AND fgid=" + product + " AND processid=" + process + " AND machineid=" + machine + " AND empid=" + employee+ " AND shift_id=" + shift );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.formatFromDate () + "' AND rdate <= '" + filterPane.formatToDate () + "' AND fgid=" + product + " AND processid=" + process + " AND machineid=" + machine + " AND empid=" + employee + " AND shift_id=" + shift+ " group by rdate" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Daily production report for period " + filterPane.sdfTS.format ( c1.getTime () ) + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "DAYS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {

                filterPane.showWeek ();

                xAxisLabel = "Week ";
                String fromWeekDate, toWeekDate;

                for ( int i = 0, j = i + 1 ; j < filterPane.weeklyDates.size () ; i = i + 2 , j += 2 ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.weeklyDates.get ( i ) + "' AND rdate <= '" + filterPane.weeklyDates.get ( j ) + "' AND fgid=" + product + " AND processid=" + process + " AND machineid=" + machine + " AND empid=" + employee + " AND shift_id=" + shift);
                    fromWeekDate = filterPane.weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = filterPane.weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
                }

                generateBarChart ( "Weekly production report for period " + filterPane.weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "WEEKS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = filterPane.showMonths ();

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.months.get ( i ) + "' AND rdate <= '" + filterPane.months.get ( j ) + "' AND fgid=" + product + " AND processid=" + process + " AND machineid=" + machine + " AND empid=" + employee + " AND shift_id=" + shift);
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );
                }

                generateBarChart ( "Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "MONTHS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = filterPane.showQuarters ();

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.quarters.get ( i ) + "' AND rdate <= '" +filterPane.quarters.get ( j ) + "' AND fgid=" + product + " AND processid=" + process + " AND machineid=" + machine + " AND empid=" + employee+ " AND shift_id=" + shift );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );
                }

                generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                //generateBarChart ( "Quarterly production report for period "+sdfTS.format(c1.getTime())+" to "+sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {

        }
    }

    public  void loadEntriesAllProcess ( String reportType , int product , int machine , int employee ) {

        ResultSet result;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty',     rdate,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                result = DB_Operations.executeSingle ( "select     showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty'  from dailyreport where rdate >= '" + filterPane.formatFromDate () + "' AND rdate <= '" + filterPane.formatToDate () + "' AND fgid=" + product + " AND machineid=" + machine + " AND empid=" + employee );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.formatFromDate () + "' AND rdate <= '" +filterPane.formatToDate () + "' AND fgid=" + product + " AND machineid=" + machine + " AND empid=" + employee + " group by rdate" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Daily production report for period " + filterPane.sdfTS.format ( c1.getTime () ) + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "DAYS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {

                filterPane.showWeek ();

                xAxisLabel = "Week ";
                String fromWeekDate, toWeekDate;

                for ( int i = 0, j = i + 1 ; j < filterPane.weeklyDates.size () ; i = i + 2 , j += 2 ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.weeklyDates.get ( i ) + "' AND rdate <= '" + filterPane.weeklyDates.get ( j ) + "' AND fgid=" + product + " AND machineid=" + machine + " AND empid=" + employee );
                    fromWeekDate = filterPane.weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = filterPane.weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
                }

                generateBarChart ( "Weekly production report for period " + filterPane.weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "WEEKS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = filterPane.showMonths ();

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.months.get ( i ) + "' AND rdate <= '" + filterPane.months.get ( j ) + "' AND fgid=" + product + " AND machineid=" + machine + " AND empid=" + employee );
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );
                }

                generateBarChart ( "Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "MONTHS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = filterPane.showQuarters ();

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.quarters.get ( i ) + "' AND rdate <= '" + filterPane.quarters.get ( j ) + "' AND fgid=" + product + " AND machineid=" + machine + " AND empid=" + employee );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );
                }

                generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                //generateBarChart ( "Quarterly production report for period "+sdfTS.format(c1.getTime())+" to "+sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {

        }
    }

    public  void loadEntriesAllMachine ( String reportType , int product , int employee ) {

        ResultSet result;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty',     rdate,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                result = DB_Operations.executeSingle ( "select     showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty'  from dailyreport where rdate >= '" + filterPane.formatFromDate () + "' AND rdate <= '" + filterPane.formatToDate () + "' AND fgid=" + product + " AND empid=" + employee );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.formatFromDate () + "' AND rdate <= '" + filterPane.formatToDate () + "' AND fgid=" + product + " AND empid=" + employee + " group by rdate" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Daily production report for period " + filterPane.sdfTS.format ( c1.getTime () ) + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "DAYS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {

                filterPane.showWeek ();

                xAxisLabel = "Week ";
                String fromWeekDate, toWeekDate;

                for ( int i = 0, j = i + 1 ; j < filterPane.weeklyDates.size () ; i = i + 2 , j += 2 ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.weeklyDates.get ( i ) + "' AND rdate <= '" + filterPane.weeklyDates.get ( j ) + "' AND fgid=" + product + " AND empid=" + employee );
                    fromWeekDate = filterPane.weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = filterPane.weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
                }

                generateBarChart ( "Weekly production report for period " + filterPane.weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "WEEKS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = filterPane.showMonths ();

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.months.get ( i ) + "' AND rdate <= '" + filterPane.months.get ( j ) + "' AND fgid=" + product + " AND empid=" + employee );
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );
                }

                generateBarChart ( "Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "MONTHS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = filterPane.showQuarters ();

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.quarters.get ( i ) + "' AND rdate <= '" + filterPane.quarters.get ( j ) + "' AND fgid=" + product + " AND empid=" + employee );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );
                }

                generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                //generateBarChart ( "Quarterly production report for period "+sdfTS.format(c1.getTime())+" to "+sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {

        }
    }

    public  void loadEntriesMachineWise ( String reportType , int product , int process , int employee ) {

        ResultSet result;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty',     rdate,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty' , machineid from dailyreport where rdate >= '" + filterPane.formatFromDate () + "' AND rdate <= '" + filterPane.formatToDate () + "' AND fgid=" + product + " AND processid=" + process + " AND empid=" + employee );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty' , machineid from dailyreport where  rdate >= '" + filterPane.formatFromDate () + "' AND rdate <= '" + filterPane.formatToDate () + " AND fgid=" + product + " AND processid=" + process + "' AND empid=" + employee + " group by rdate" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Daily production report for period " + filterPane.sdfTS.format ( c1.getTime () ) + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "DAYS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {

                filterPane.showWeek ();

                xAxisLabel = "Week ";
                String fromWeekDate, toWeekDate;

                for ( int i = 0, j = i + 1 ; j < filterPane.weeklyDates.size () ; i = i + 2 , j += 2 ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    // result = DB_Operations.executeSingle ("select (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '"+filterPane.weeklyDates.get ( i)+"' AND rdate <= '"+filterPane.weeklyDates.get(j)+"' AND fgid="+product+" group by fgid");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty' , machineid from dailyreport where  rdate >= '" + filterPane.weeklyDates.get ( i ) + "' AND rdate <= '" + filterPane.weeklyDates.get ( j ) + " AND fgid=" + product + " AND processid=" + process + "' AND empid=" + employee );
                    fromWeekDate = filterPane.weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = filterPane.weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
                }
                generateBarChart ( "Weekly production report for period " + filterPane.weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "WEEKS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = filterPane.showMonths ();

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty' , machineid from dailyreport where  rdate >= '" + filterPane.months.get ( i ) + "' AND rdate <= '" + filterPane.months.get ( j ) + " AND fgid=" + product + " AND processid=" + process + "' AND empid=" + employee );
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );

                }
                generateBarChart ( "Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "MONTHS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = filterPane.showQuarters ();

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty' , machineid from dailyreport where  rdate >= '" + filterPane.quarters.get ( i ) + "' AND rdate <= '" + filterPane.quarters.get ( j ) + " AND fgid=" + product + " AND processid=" + process + "' AND empid=" + employee );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );

                }
                generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                //generateBarChart ( "Quarterly production report for period "+filterPane.sdfTS.format(c1.getTime())+" to "+filterPane.sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {
            System.out.println ( "sdfsdfd" );
        }
    }

    public  void loadEntriesProductWise ( String reportType ) {

        ResultSet result;

        int product = 0;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //   for ( int i = 0 ; i < products.size () ; i ++ ) {
                //       product = products.get ( i ).FG_ID;
                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty',     rdate,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                result = DB_Operations.executeSingle ( "select    showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty'  from dailyreport where rdate >= '" + filterPane.formatFromDate () + "' AND rdate <= '" + filterPane.formatToDate () + "'" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );
                //        previousReportValue = 0;
                //    }

                generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                //   for ( int i = 0 ; i < products.size () ; i ++ ) {
                //   product = products.get ( i ).FG_ID;
                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.formatFromDate () + "' AND rdate <= '" + filterPane.formatToDate () + "' group by rdate" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );
                //       previousReportValue = 0;
                //    }
                generateBarChart ( "Daily production report for period " + filterPane.sdfTS.format ( c1.getTime () ) + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "DAYS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {

                filterPane.showWeek ();

                xAxisLabel = "Week ";
                String fromWeekDate, toWeekDate;

                for ( int i = 0, j = i + 1 ; j < filterPane.weeklyDates.size () ; i = i + 2 , j += 2 ) {

                    //  for ( int k = 0 ; k < products.size () ; k ++ ) {
                    //     product = products.get ( k ).FG_ID;
                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    // result = DB_Operations.executeSingle ("select (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '"+filterPane.weeklyDates.get ( i)+"' AND rdate <= '"+filterPane.weeklyDates.get(j)+"' AND fgid="+product+" group by fgid");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.weeklyDates.get ( i ) + "' AND rdate <= '" + filterPane.weeklyDates.get ( j ) + "'" );
                    fromWeekDate = filterPane.weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = filterPane.weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
                    //     }
                    //    previousReportValue = 0;
                }
                generateBarChart ( "Weekly production report for period " + filterPane.weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "WEEKS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = filterPane.showMonths ();

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //    for ( int l = 0 ; l < products.size () ; l ++ ) {
                    //       product = products.get ( l ).FG_ID;
                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.months.get ( i ) + "' AND rdate <= '" + filterPane.months.get ( j ) + "'" );
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );

                    //    }
                }
                generateBarChart ( "Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "MONTHS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = filterPane.showQuarters ();

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //    for ( int l = 0 ; l < products.size () ; l ++ ) {
                    //        product = products.get ( l ).FG_ID;
                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.quarters.get ( i ) + "' AND rdate <= '" + filterPane.quarters.get ( j ) + "'" );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );

                    //    }
                    //   previousReportValue = 0;
                }
                generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                //generateBarChart ( "Quarterly production report for period "+filterPane.sdfTS.format(c1.getTime())+" to "+filterPane.sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {
            System.out.println ( "sdfsdfd" );
        }
    }

    public  void loadEntriesOnlyProcess ( String reportType , int product , int process ) {

        ResultSet result;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty',     rdate,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                result = DB_Operations.executeSingle ( "select    showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty'  from dailyreport where rdate >= '" + filterPane.formatFromDate () + "' AND rdate <= '" + filterPane.formatToDate () + "' AND fgid=" + product + " AND processid=" + process );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.formatFromDate () + "' AND rdate <= '" + filterPane.formatToDate () + "' AND fgid=" + product + " AND processid=" + process + " group by rdate" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Daily production report for period " + filterPane.sdfTS.format ( c1.getTime () ) + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "DAYS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {

                filterPane.showWeek ();

                xAxisLabel = "Week ";
                String fromWeekDate, toWeekDate;

                for ( int i = 0, j = i + 1 ; j < filterPane.weeklyDates.size () ; i = i + 2 , j += 2 ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.weeklyDates.get ( i ) + "' AND rdate <= '" + filterPane.weeklyDates.get ( j ) + "' AND fgid=" + product + " AND processid=" + process );
                    fromWeekDate = filterPane.weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = filterPane.weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
                }

                generateBarChart ( "Weekly production report for period " + filterPane.weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "WEEKS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = filterPane.showMonths ();

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.months.get ( i ) + "' AND rdate <= '" + filterPane.months.get ( j ) + "' AND fgid=" + product + " AND processid=" + process );
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );
                }

                generateBarChart ( "Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "MONTHS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = filterPane.showQuarters ();

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.quarters.get ( i ) + "' AND rdate <= '" + filterPane.quarters.get ( j ) + "' AND fgid=" + product + " AND processid=" + process );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );
                }

                generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                //generateBarChart ( "Quarterly production report for period "+filterPane.sdfTS.format(c1.getTime())+" to "+filterPane.sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {

        }
    }

    public  void loadEntriesProcessAndMachine ( String reportType , int product , int process , int machine ) {

        ResultSet result;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty',     rdate,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                result = DB_Operations.executeSingle ( "select    showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty'  from dailyreport where rdate >= '" + filterPane.formatFromDate () + "' AND rdate <= '" + filterPane.formatToDate () + "' AND fgid=" + product + " AND processid=" + process + " AND machineid=" + machine );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.formatFromDate () + "' AND rdate <= '" + filterPane.formatToDate () + "' AND fgid=" + product + " AND processid=" + process + " AND machineid=" + machine + " group by rdate" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Daily production report for period " + filterPane.sdfTS.format ( c1.getTime () ) + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "DAYS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {

                filterPane.showWeek ();

                xAxisLabel = "Week ";
                String fromWeekDate, toWeekDate;

                for ( int i = 0, j = i + 1 ; j < filterPane.weeklyDates.size () ; i = i + 2 , j += 2 ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.weeklyDates.get ( i ) + "' AND rdate <= '" + filterPane.weeklyDates.get ( j ) + "' AND fgid=" + product + " AND processid=" + process + " AND machineid=" + machine );
                    fromWeekDate = filterPane.weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = filterPane.weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
                }

                generateBarChart ( "Weekly production report for period " + filterPane.weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + filterPane.sdfTS.format ( c2.getTime () ) , "WEEKS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = filterPane.showMonths ();

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.months.get ( i ) + "' AND rdate <= '" + filterPane.months.get ( j ) + "' AND fgid=" + product + " AND processid=" + process + " AND machineid=" + machine );
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );
                }

                generateBarChart ( "Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "MONTHS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = filterPane.showQuarters ();

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < filterPane.quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + filterPane.quarters.get ( i ) + "' AND rdate <= '" + filterPane.quarters.get ( j ) + "' AND fgid=" + product + " AND processid=" + process + " AND machineid=" + machine );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );
                }

                generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                //generateBarChart ( "Quarterly production report for period "+filterPane.sdfTS.format(c1.getTime())+" to "+filterPane.sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {

        }
    }

    public  DefaultTableModel buildTableModel ( ResultSet rs , String reportType , String label )
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
                        vector.add ( s );
                    }

//                    if ( s != null ) {
//                        vector.add ( s );
//                    } else {
//                        vector.add ( new String ( "--" ) );
//                    }
                    String s1;

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

                        if ( s1 != null ) {
                            ab[ 1 ] = rs.getString ( columnIndex );
                        } else {
                            ab[ 1 ] = "0";
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
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 255, 0));
        setForeground(new java.awt.Color(0, 204, 204));
        setAutoscrolls(true);
        setMaximumSize(new java.awt.Dimension(1366, 760));
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(0, 0));
        setLayout(null);

        jTable1.setBackground(new java.awt.Color(255, 255, 255));
        jTable1.setForeground(new java.awt.Color(0, 0, 0));
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
        jScrollPane1.setBounds(430, 70, 880, 200);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Production Quantity Report");
        add(jLabel5);
        jLabel5.setBounds(150, 10, 440, 40);
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserDialog dateChooserDialog1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
