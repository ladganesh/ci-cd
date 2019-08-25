/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.JLabel;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.EmployeeDR;
import trixware.erp.prodezydesktop.Model.MachineDR;
import trixware.erp.prodezydesktop.Model.ProcessDR;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.web_services.WebAPITester;
/**
 *
 * @author Rajesh
 */
public class ReportWIP2 extends javax.swing.JPanel {

    static ArrayList<ProductDR> products = null;
    ArrayList<EmployeeDR> employees = null;
    static ArrayList<ProcessDR> processes = null;
    static ArrayList<String[]> fgprocessmap = null;
    ArrayList<MachineDR> machines = null;

    ArrayList<String[]> wipMap = null;

    String selectAll = "-- select --";

    ProductDR prdr = null;
    EmployeeDR empdr = null;
    ProcessDR prcdr = null;
    MachineDR mcdr = null;
//HashMap<String, String> entity = null ;

//SimpleDateFormat sdf = null;
    static String fromDate, toDate;

    String reportLabel1 = "Process Wise / ProductWise Report for ";
    String reportLabel2 = "Date Wise report for all processes for product";
    String reportLabel3 = "Date wIse combined report for selected process";
    String reportLabel4 = "Combined report between";

    public static int resIndex = 0;
    
    ArrayList<String[]> processesMap = null;

    static Vector<Vector<Object>> data = null;
    static Vector<String> columnNames = null;
    static DefaultCategoryDataset dataSet = null;
    static ArrayList<String[]> abc = null;
    static ChartPanel cp = null;

    Calendar c1 = Calendar.getInstance ();
    Calendar c2 = Calendar.getInstance ();

    int columnCount;

    /**
     * Creates new form DailyReportForm
     */
    public ReportWIP2 () {
        initComponents ();

        loadComboBoxes ();

        Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
        int screenW = ( int ) d.getWidth ();
        int screenH = ( int ) d.getHeight ();

       // JTableHeader header = jTable1.getTableHeader ();
       // header.setBackground (  Color.DARK_GRAY  );
               
        
        jComboBox1.setVisible ( false);
        jComboBox2.setVisible ( false);
        jComboBox3.setVisible ( false);
        jComboBox4.setVisible ( false);
        
//        
//        jCheckBox1.setVisible ( false );
//        jCheckBox2.setVisible ( false );
//        jCheckBox3.setVisible ( false );
//        jCheckBox4.setVisible ( false );
        
        jLabel2.setVisible ( false);
        jLabel12.setVisible ( false);
        jLabel7.setVisible ( false);
        jLabel8.setVisible ( false);
        
      //  jButton1.setVisible ( false);
    //    jButton2.setVisible ( false);
        
        jTable1.setModel ( new DefaultTableModel ( data , columnNames ) );
    
        loadDefault ( "" , 0 );
        
        //jTable1.getColumnModel ().getColumn (0).setPreferredWidth (200 );
        
        
        
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

    // Report no 1 for 'Daily' filter
    // select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  MOVEDATE as 'Start Date', qtyout as 'Output Qty', (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid=4 order by MOVEDATE
    // Report no 2 for 'Weekly' filter
    //select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',   SUM(qtyout) as 'Output Qty', SUM((qtyout/(stoptime-starttime))) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '03-06-2018'  AND MOVEDATE <= '08-06-2018' AND fgid=4 AND PROCESS_STAGE = 1 
    //select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',   SUM(qtyout) as 'Output Qty', SUM((qtyout/(stoptime-starttime))) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '03-06-2018'  AND MOVEDATE <= '08-06-2018' AND fgid=4 AND PROCESS_STAGE = 2 
    //select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',   SUM(qtyout) as 'Output Qty', SUM((qtyout/(stoptime-starttime))) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '03-06-2018'  AND MOVEDATE <= '08-06-2018' AND fgid=4 AND PROCESS_STAGE = 3
    public JFreeChart generateBarChart ( String reportTitle , String xAxis , String yAxis ) {

        dataSet = new DefaultCategoryDataset ();

        for ( int i = 0 ; i < abc.size () ; i ++ ) {
            //try{
            dataSet.setValue ( Integer.parseInt ( abc.get ( i )[ 1 ] ) , "WIP" , abc.get ( i )[ 0 ] );
            // }catch(NumberFormatException e){
            //     System.out.println("errorrrr") ;
            // }                                                                     
        }

        JFreeChart chart = ChartFactory.createBarChart3D (
                reportTitle , xAxis , yAxis ,
                dataSet , PlotOrientation.VERTICAL , true , true , false );

        ChartPanel cp = new ChartPanel ( chart );
        cp.setPreferredSize ( new java.awt.Dimension ( 900 , 330 ) );
        cp.setBounds ( 150 , 280 , 950 , 330 );

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

//                                for(int x = 0;x<5;x++){
//                                    renderer.setSeriesItemLabelFont(x, new Font("Times New Roman",Font.PLAIN,10));
//                                    renderer.setSeriesPositiveItemLabelPosition(x,   
//                                            new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3, TextAnchor.BOTTOM_LEFT, TextAnchor.BOTTOM_LEFT, 0.0));
//                                }
        try {
            remove ( 20 );
        } catch ( Exception e ) {
            StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }

        add ( cp );

        HomeScreen3.home.pack ();
        HomeScreen3.home.revalidate ();
        HomeScreen3.home.repaint ();

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

    public void loadComboBoxes () {

        
        
         String addEmpAPICall = "finishedgoods";
          String  result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
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
               
                products.add ( new ProductDR ( Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) , emp.get ( "PART_NAME" ).toString () , emp.get ( "FG_UOM_ID" ).toString () ) );
            }

        
            addEmpAPICall = "processes";
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
            processes = new ArrayList<ProcessDR> ();
            for ( int i = 0 ; i < records.length () ; i ++ ) {

                emp = records.getJSONObject ( i );
                jComboBox4.addItem ( emp.get ( "PROCESS_NAME" ).toString () );
                processes.add ( new ProcessDR ( Integer.parseInt ( emp.get ( "PROCESS_ID" ).toString () ) , emp.get ( "PROCESS_NAME" ).toString () ) );
            }
        
        
        
        /*
        
        
        String queryOne = "SELECT FG_ID, PART_NAME FROM finished_goods";
        String queryTwo = "SELECT EmployeePK, EMP_NAME FROM employee";
        String queryThree = "SELECT MCH_ID, MACHINE_NO FROM machine";
        String queryFour = "SELECT PROCESS_ID, PROCESS_NAME FROM PROCESS_MASTER";

        ResultSet rs = null;

        try {
            rs = DB_Operations.executeSingle ( queryOne );
            //  products = new ArrayList<HashMap<String, String>> ();
            products = new ArrayList<ProductDR> ();
            while ( rs.next () ) {
                //   entity =  new  HashMap<String, String>();
                prdr = new ProductDR ();
                //    entity.put ( rs.getString(1) , rs.getString(2)) ;
                prdr.FG_ID = Integer.parseInt ( rs.getString ( 1 ) );
                prdr.FG_NAME = rs.getString ( 2 );
                products.add ( prdr );
                jComboBox1.addItem ( rs.getString ( 2 ) );
            }
        } catch ( Exception e ) {
            System.out.println ( " Error 1 " + e.getMessage () );
            StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
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
            StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
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
            StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
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
            StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }
*/
    }

    public void loadDefault ( String reportType , int process ) {

        ResultSet result, wips;

//        try {
//                String xAxisLabel;
//                // int processId = processes.get ( resIndex ).PRC_ID;
//                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showMOVEDATE  as 'Start Date',     SUM(qtyout) as 'Output Qty',     MOVEDATE,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
//            result = DB_Operations.executeSingle ( "select PROCESS_NAME, PROCESS_ID from PROCESS_MASTER " );
//            jTable1.setModel ( buildTableModel2 ( result , "singleProcess" , "xAxisLabel" ) );
//            //     generateBarChart ( "Work in process as on date" , "Product" , "WIP" );
//        } catch ( SQLException e ) {
//            StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
//        }
//        
        Thread t = new Thread(){
            
            public void run(){
                
                String addEmpAPICall = "processes";
                String    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                buildTableModelFromJSON ( result2 , "singleProcess" , "xAxisLabel" ) ;
            
            }
        };
        
        t.start();
        
        
        
    }

    @SuppressWarnings( "null" )
    public void loadEntries ( String reportType , int product ) {

        ResultSet result;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showMOVEDATE  as 'Start Date',     SUM(qtyout) as 'Output Qty',     MOVEDATE,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
            result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process', (select MACHINE_NO from machine where MCH_ID in (machine)) as 'Machine' , (select EMP_NO  from employee where EmployeePK in (empid)) as 'Employee' , SUM(BALANCE) from op_wip2 where PRODUCT = " + product + " group by PROCESS_STAGE" );
            jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

            generateBarChart ( "Work in process as on date" , "PROCESS" , "WIP" );

        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }
    }

    public void loadEntriesProductNProcess ( String reportType , int product , int process ) {

        ResultSet result;

        try {

            String xAxisLabel;

            //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showMOVEDATE  as 'Start Date',     SUM(qtyout) as 'Output Qty',     MOVEDATE,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
            result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process', (select MACHINE_NO from machine where MCH_ID in (machine)) as 'Machine' , (select EMP_NO  from employee where EmployeePK in (empid)) as 'Employee' , SUM(BALANCE) from op_wip2 where PRODUCT = " + product + " AND PROCESS_STAGE = " + process );
            jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

            generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );

        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }
    }

    public void loadEntriesProcess ( String reportType , int process ) {

        ResultSet result;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showMOVEDATE  as 'Start Date',     SUM(qtyout) as 'Output Qty',     MOVEDATE,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
            result = DB_Operations.executeSingle ( "select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process', (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product', (select MACHINE_NO from machine where MCH_ID in (machine)) as 'Machine' , (select EMP_NO  from employee where EmployeePK in (empid)) as 'Employee' , SUM(BALANCE) from op_wip2 where PROCESS_STAGE = " + process + " group by PRODUCT" );
            jTable1.setModel ( buildTableModel ( result , "singleProcess" , "xAxisLabel" ) );

            generateBarChart ( "Work in process as on date" , "Product" , "WIP" );

        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }
    }

    public void loadEntriesAllProcess ( String reportType , int product , int machine , int employee ) {

        ResultSet result;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showMOVEDATE  as 'Start Date',     SUM(qtyout) as 'Output Qty',     MOVEDATE,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',    showMOVEDATE  as 'Start Date',     SUM(qtyout) as 'Output Qty',   SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '" + formatFromDate () + "' AND MOVEDATE <= '" + formatToDate () + "' AND PRODUCT=" + product + " AND machineid=" + machine + " AND empid=" + employee );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + formatFromDate () + "' AND MOVEDATE <= '" + formatToDate () + "' AND PRODUCT=" + product + " AND machineid=" + machine + " AND empid=" + employee + " group by MOVEDATE" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Daily production report for period " + sdfTS.format ( c1.getTime () ) + " to " + sdfTS.format ( c2.getTime () ) , "DAYS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {

                showWeek ();

                xAxisLabel = "Week ";
                String fromWeekDate, toWeekDate;

                for ( int i = 0, j = i + 1 ; j < weeklyDates.size () ; i = i + 2 , j += 2 ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + weeklyDates.get ( i ) + "' AND MOVEDATE <= '" + weeklyDates.get ( j ) + "' AND PRODUCT=" + product + " AND machineid=" + machine + " AND empid=" + employee );
                    fromWeekDate = weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
                }

                generateBarChart ( "Weekly production report for period " + weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + sdfTS.format ( c2.getTime () ) , "WEEKS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = showMonths ();

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + months.get ( i ) + "' AND MOVEDATE <= '" + months.get ( j ) + "' AND PRODUCT=" + product + " AND machineid=" + machine + " AND empid=" + employee );
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );
                }

                generateBarChart ( "Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "MONTHS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = showQuarters ();

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + quarters.get ( i ) + "' AND MOVEDATE <= '" + quarters.get ( j ) + "' AND PRODUCT=" + product + " AND machineid=" + machine + " AND empid=" + employee );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );
                }

                generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                //generateBarChart ( "Quarterly production report for period "+sdfTS.format(c1.getTime())+" to "+sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {
                StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }
    }

    public void loadEntriesAllMachine ( String reportType , int product , int employee ) {

        ResultSet result;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showMOVEDATE  as 'Start Date',     SUM(qtyout) as 'Output Qty',     MOVEDATE,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',    showMOVEDATE  as 'Start Date',     SUM(qtyout) as 'Output Qty',   SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '" + formatFromDate () + "' AND MOVEDATE <= '" + formatToDate () + "' AND PRODUCT=" + product + " AND empid=" + employee );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + formatFromDate () + "' AND MOVEDATE <= '" + formatToDate () + "' AND PRODUCT=" + product + " AND empid=" + employee + " group by MOVEDATE" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Daily production report for period " + sdfTS.format ( c1.getTime () ) + " to " + sdfTS.format ( c2.getTime () ) , "DAYS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {

                showWeek ();

                xAxisLabel = "Week ";
                String fromWeekDate, toWeekDate;

                for ( int i = 0, j = i + 1 ; j < weeklyDates.size () ; i = i + 2 , j += 2 ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + weeklyDates.get ( i ) + "' AND MOVEDATE <= '" + weeklyDates.get ( j ) + "' AND PRODUCT=" + product + " AND empid=" + employee );
                    fromWeekDate = weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
                }

                generateBarChart ( "Weekly production report for period " + weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + sdfTS.format ( c2.getTime () ) , "WEEKS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = showMonths ();

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + months.get ( i ) + "' AND MOVEDATE <= '" + months.get ( j ) + "' AND PRODUCT=" + product + " AND empid=" + employee );
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );
                }

                generateBarChart ( "Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "MONTHS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = showQuarters ();

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + quarters.get ( i ) + "' AND MOVEDATE <= '" + quarters.get ( j ) + "' AND PRODUCT=" + product + " AND empid=" + employee );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );
                }

                generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                //generateBarChart ( "Quarterly production report for period "+sdfTS.format(c1.getTime())+" to "+sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {
                StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }
    }

    public void loadEntriesMachineWise ( String reportType , int product , int process , int employee ) {

        ResultSet result;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showMOVEDATE  as 'Start Date',     SUM(qtyout) as 'Output Qty',     MOVEDATE,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',    showMOVEDATE  as 'Start Date',     SUM(qtyout) as 'Output Qty',   SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence', machineid from dailyreport where MOVEDATE >= '" + formatFromDate () + "' AND MOVEDATE <= '" + formatToDate () + "' AND PRODUCT=" + product + " AND PROCESS_STAGE=" + process + " AND empid=" + employee + "   group by machineid " );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence', machineid from dailyreport where  MOVEDATE >= '" + formatFromDate () + "' AND MOVEDATE <= '" + formatToDate () + " AND PRODUCT=" + product + " AND PROCESS_STAGE=" + process + "' AND empid=" + employee + " group by machineid" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Daily production report for period " + sdfTS.format ( c1.getTime () ) + " to " + sdfTS.format ( c2.getTime () ) , "DAYS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {

                showWeek ();

                xAxisLabel = "Week ";
                String fromWeekDate, toWeekDate;

                for ( int i = 0, j = i + 1 ; j < weeklyDates.size () ; i = i + 2 , j += 2 ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                    // result = DB_Operations.executeSingle ("select (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '"+weeklyDates.get ( i)+"' AND MOVEDATE <= '"+weeklyDates.get(j)+"' AND fgid="+product+" group by fgid");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence', machineid from dailyreport where  MOVEDATE >= '" + weeklyDates.get ( i ) + "' AND MOVEDATE <= '" + weeklyDates.get ( j ) + " AND PRODUCT=" + product + " AND PROCESS_STAGE=" + process + "' AND empid=" + employee + " group by machineid " );
                    fromWeekDate = weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
                }
                generateBarChart ( "Weekly production report for period " + weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + sdfTS.format ( c2.getTime () ) , "WEEKS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = showMonths ();

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence', machineid from dailyreport where  MOVEDATE >= '" + months.get ( i ) + "' AND MOVEDATE <= '" + months.get ( j ) + " AND PRODUCT=" + product + " AND PROCESS_STAGE=" + process + "' AND empid=" + employee + "  group by machineid " );
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );

                }
                generateBarChart ( "Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "MONTHS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = showQuarters ();

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence', machineid from dailyreport where  MOVEDATE >= '" + quarters.get ( i ) + "' AND MOVEDATE <= '" + quarters.get ( j ) + " AND PRODUCT=" + product + " AND PROCESS_STAGE=" + process + "' AND empid=" + employee + "  group by machineid " );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );

                }
                generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                //generateBarChart ( "Quarterly production report for period "+sdfTS.format(c1.getTime())+" to "+sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {
            System.out.println ( "sdfsdfd" );
            StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }
    }

    public void loadEntriesProductWise ( String reportType ) {

        ResultSet result;

        int product = 0;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //   for ( int i = 0 ; i < products.size () ; i ++ ) {
                //       product = products.get ( i ).FG_ID;
                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showMOVEDATE  as 'Start Date',     SUM(qtyout) as 'Output Qty',     MOVEDATE,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',    showMOVEDATE  as 'Start Date',     SUM(qtyout) as 'Output Qty',   SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '" + formatFromDate () + "' AND MOVEDATE <= '" + formatToDate () + "'" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );
                //        previousReportValue = 0;
                //    }

                generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                //   for ( int i = 0 ; i < products.size () ; i ++ ) {
                //   product = products.get ( i ).FG_ID;
                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + formatFromDate () + "' AND MOVEDATE <= '" + formatToDate () + "' group by MOVEDATE" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );
                //       previousReportValue = 0;
                //    }
                generateBarChart ( "Daily production report for period " + sdfTS.format ( c1.getTime () ) + " to " + sdfTS.format ( c2.getTime () ) , "DAYS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {

                showWeek ();

                xAxisLabel = "Week ";
                String fromWeekDate, toWeekDate;

                for ( int i = 0, j = i + 1 ; j < weeklyDates.size () ; i = i + 2 , j += 2 ) {

                    //  for ( int k = 0 ; k < products.size () ; k ++ ) {
                    //     product = products.get ( k ).FG_ID;
                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                    // result = DB_Operations.executeSingle ("select (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '"+weeklyDates.get ( i)+"' AND MOVEDATE <= '"+weeklyDates.get(j)+"' AND fgid="+product+" group by fgid");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + weeklyDates.get ( i ) + "' AND MOVEDATE <= '" + weeklyDates.get ( j ) + "'" );
                    fromWeekDate = weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
                    //     }
                    //    previousReportValue = 0;
                }
                generateBarChart ( "Weekly production report for period " + weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + sdfTS.format ( c2.getTime () ) , "WEEKS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = showMonths ();

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //    for ( int l = 0 ; l < products.size () ; l ++ ) {
                    //       product = products.get ( l ).FG_ID;
                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + months.get ( i ) + "' AND MOVEDATE <= '" + months.get ( j ) + "'" );
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );

                    //    }
                }
                generateBarChart ( "Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "MONTHS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = showQuarters ();

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //    for ( int l = 0 ; l < products.size () ; l ++ ) {
                    //        product = products.get ( l ).FG_ID;
                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + quarters.get ( i ) + "' AND MOVEDATE <= '" + quarters.get ( j ) + "'" );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );

                    //    }
                    //   previousReportValue = 0;
                }
                generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                //generateBarChart ( "Quarterly production report for period "+sdfTS.format(c1.getTime())+" to "+sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {
            System.out.println ( "sdfsdfd" );
            StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }
    }

    public void loadEntriesOnlyProcess ( String reportType , int product , int process ) {

        ResultSet result;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showMOVEDATE  as 'Start Date',     SUM(qtyout) as 'Output Qty',     MOVEDATE,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',    showMOVEDATE  as 'Start Date',     SUM(qtyout) as 'Output Qty',   SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '" + formatFromDate () + "' AND MOVEDATE <= '" + formatToDate () + "' AND PRODUCT=" + product + " AND PROCESS_STAGE=" + process );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + formatFromDate () + "' AND MOVEDATE <= '" + formatToDate () + "' AND PRODUCT=" + product + " AND PROCESS_STAGE=" + process + " group by MOVEDATE" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Daily production report for period " + sdfTS.format ( c1.getTime () ) + " to " + sdfTS.format ( c2.getTime () ) , "DAYS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {

                showWeek ();

                xAxisLabel = "Week ";
                String fromWeekDate, toWeekDate;

                for ( int i = 0, j = i + 1 ; j < weeklyDates.size () ; i = i + 2 , j += 2 ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + weeklyDates.get ( i ) + "' AND MOVEDATE <= '" + weeklyDates.get ( j ) + "' AND PRODUCT=" + product + " AND PROCESS_STAGE=" + process );
                    fromWeekDate = weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
                }

                generateBarChart ( "Weekly production report for period " + weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + sdfTS.format ( c2.getTime () ) , "WEEKS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = showMonths ();

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by MOVEDATE");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + months.get ( i ) + "' AND MOVEDATE <= '" + months.get ( j ) + "' AND PRODUCT=" + product + " AND PROCESS_STAGE=" + process );
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );
                }

                generateBarChart ( "Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "MONTHS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = showQuarters ();

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where PRODUCT="+product+" group by MOVEDATE");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + quarters.get ( i ) + "' AND MOVEDATE <= '" + quarters.get ( j ) + "' AND PRODUCT=" + product + " AND PROCESS_STAGE=" + process );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );
                }

                generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                //generateBarChart ( "Quarterly production report for period "+sdfTS.format(c1.getTime())+" to "+sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {
                StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }
    }

    public void loadEntriesProcessAndMachine ( String reportType , int product , int process , int machine ) {

        ResultSet result;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',    showMOVEDATE  as 'Start Date',     SUM(qtyout) as 'Output Qty',     MOVEDATE,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND PRODUCT="+product);
                result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',    showMOVEDATE  as 'Start Date',     SUM(qtyout) as 'Output Qty',   SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where MOVEDATE >= '" + formatFromDate () + "' AND MOVEDATE <= '" + formatToDate () + "' AND PRODUCT=" + product + " AND PROCESS_STAGE=" + process + " AND machineid=" + machine );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where PRODUCT="+product+" group by MOVEDATE");
                result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + formatFromDate () + "' AND MOVEDATE <= '" + formatToDate () + "' AND PRODUCT=" + product + " AND PROCESS_STAGE=" + process + " AND machineid=" + machine + " group by MOVEDATE" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Daily production report for period " + sdfTS.format ( c1.getTime () ) + " to " + sdfTS.format ( c2.getTime () ) , "DAYS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Weekly" ) ) {

                showWeek ();

                xAxisLabel = "Week ";
                String fromWeekDate, toWeekDate;

                for ( int i = 0, j = i + 1 ; j < weeklyDates.size () ; i = i + 2 , j += 2 ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where PRODUCT="+product+" group by MOVEDATE");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + weeklyDates.get ( i ) + "' AND MOVEDATE <= '" + weeklyDates.get ( j ) + "' AND PRODUCT=" + product + " AND PROCESS_STAGE=" + process + " AND machineid=" + machine );
                    fromWeekDate = weeklyDates.get ( i ).split ( " " )[ 0 ].substring ( 5 );
                    toWeekDate = weeklyDates.get ( j ).split ( " " )[ 0 ].substring ( 5 );
                    jTable1.setModel ( buildTableModel ( result , reportType , fromWeekDate + " to " + toWeekDate ) );
                }

                generateBarChart ( "Weekly production report for period " + weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + sdfTS.format ( c2.getTime () ) , "WEEKS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = showMonths ();

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where PRODUCT="+product+" group by MOVEDATE");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + months.get ( i ) + "' AND MOVEDATE <= '" + months.get ( j ) + "' AND PRODUCT=" + product + " AND PROCESS_STAGE=" + process + " AND machineid=" + machine );
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );
                }

                generateBarChart ( "Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "MONTHS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = showQuarters ();

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (PROCESS_STAGE)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',    MOVEDATE,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where PRODUCT="+product+" group by MOVEDATE");
                    result = DB_Operations.executeSingle ( "select (select PART_NAME from finished_goods where FG_ID in (PRODUCT)) as 'Product',  showMOVEDATE  as 'Start Date', SUM(qtyout) as 'Output Qty',   (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where  MOVEDATE >= '" + quarters.get ( i ) + "' AND MOVEDATE <= '" + quarters.get ( j ) + "' AND PRODUCT=" + product + " AND PROCESS_STAGE=" + process + " AND machineid=" + machine );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );
                }

                generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                //generateBarChart ( "Quarterly production report for period "+sdfTS.format(c1.getTime())+" to "+sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {
                StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }
    }

    public DefaultTableModel buildTableModel2 ( ResultSet rs , String a , String b )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();
        ArrayList<String> processSum = new ArrayList<String> ();
        int processesSum = 0, productSum = 0;

        for ( int j = 0 ; j < processes.size () ; j ++ ) {
            processSum.add ( "0" );
        }

        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        int columnCount = metaData.getColumnCount ();

        columnNames.add ( "<html> <b>Product Name</b></html>" );

        for ( int column = 1 ; column < 3 ; column ++ ) {

            if ( column == 1 ) {
                for ( int i = 0 ; i < processes.size () ; i ++ ) {
                    columnNames.add ( "<html> <b>"+processes.get ( i ).PRC_NAME+"</b></html>" );
                }
            }
        }
        columnNames.add (  1,  "<html> <b>TotalAll Processes</b></html>" );

        
        
        
        Vector<Vector<String>> data = new Vector<Vector<String>> ();
        Vector<String> vector = null;

        int totalMoveQty = 0;
        int k = 0;
        //while ( rs.next () ) {
        ResultSet wips = null;
        String wipQuery = "select BALANCE from op_wip2 where PRODUCT =  AND PROCESS_STAGE = ?";
        wipMap = new ArrayList<String[]> ();
        String[] map = null;
        ArrayList<String[]> processesMap = null;

        for ( int i = 0 ; i < products.size () ; i ++ ) {
            processesSum = 0;
            productSum = 0;
            vector = new Vector<String> ();
            vector.add ( products.get ( i ).FG_PART_NO );

            boolean productMapped = false;

            String processMapQuery = "SELECT REF_PROCESS_ID, PROCESS_NAME FROM FG_PROCESS_MACH_MAP a INNER JOIN PROCESS_MASTER b on a.REF_PROCESS_ID = b.PROCESS_ID AND REF_FG_ID = " + products.get ( i ).FG_ID;
            ResultSet rs1 = null;

            try {
                rs1 = DB_Operations.executeSingle ( processMapQuery );
                //  products = new ArrayList<HashMap<String, String>> ();
                processesMap = new ArrayList<String[]> ();
                while ( rs1.next () ) {
                    processesMap.add ( new String[] { rs1.getString ( 1 ) , rs1.getString ( 2 ) } );
                }
            } catch ( Exception e ) {
                System.out.println ( " Error 1 " + e.getMessage () );
                StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
            } finally {
                rs1.close ();
            }

            for ( int j = 0 ; j < processes.size () ; j ++ ) {

                map = new String[ 5 ];

                for ( int l = 0 ; l < processesMap.size () ; l ++ ) {
                    String[] cc = new String[] { String.valueOf ( processes.get ( l ).PRC_ID ) , processes.get ( l ).PRC_NAME };
                    String[] arr = processesMap.get ( l );
                    if ( arr[ 0 ].equals ( cc[ 0 ] ) && arr[ 1 ].equals ( cc[ 1 ] ) ) {
                        productMapped = true;
                        l = processesMap.size ();
                    } 
                }

                if ( productMapped ) {

                    wipQuery = "select SUM(BALANCE) from op_wip2 where PRODUCT = " + products.get ( i ).FG_ID + " AND PROCESS_STAGE = " + processes.get ( j ).PRC_ID;
                    wips = DB_Operations.executeSingle ( wipQuery );

                    if ( wips.isBeforeFirst () ) {
                        while ( wips.next () ) {
                            map[ 0 ] = products.get ( i ).FG_ID + "";
                            map[ 1 ] = products.get ( i ).FG_PART_NO + "";
                            map[ 2 ] = processes.get ( j ).PRC_ID + "";
                            map[ 3 ] = processes.get ( j ).PRC_NAME + "";
                            map[ 4 ] = wips.getInt ( "SUM(BALANCE)" ) + "";

                            vector.add ( wips.getInt ( 1 ) + "" );
                            processesSum = processesSum + wips.getInt ( 1 );
                            processSum.set ( j , String.valueOf ( wips.getInt ( 1 ) + Integer.parseInt ( processSum.get ( j ) ) ) );

                        }
                    }
//                        }else{
//                            map[0] =  products.get(i).FG_ID +"" ;
//                            map[1] =  products.get(i).FG_NAME +"" ;
//                            map[2] =  processes.get(j).PRC_ID +"" ;
//                            map[3] =  processes.get(j).PRC_NAME+"" ;
//                            map[4] =  "NA1" ;
//                            vector.add("--");
//                        }
                    } else {
                        map[ 0 ] = products.get ( i ).FG_ID + "";
                        map[ 1 ] = products.get ( i ).FG_PART_NO + "";
                        map[ 2 ] = processes.get ( j ).PRC_ID + "";
                        map[ 3 ] = processes.get ( j ).PRC_NAME + "";
                        map[ 4 ] = "0";

                        vector.add ( "---" );
                        processesSum = 0;
                    }
                wipMap.add ( map );
                // }
            }
            vector.add ( "<html> <b>"+processesSum+"</b></html>" );
            data.add ( vector );

        }

        vector = new Vector<String> ();
        vector.add ( "<html> <b>TotalAll Products</html>" );

        for ( int j = 0 ; j < processes.size () ; j ++ ) {
            //processSum.set( j , "0" );
            vector.add ( "<html> <b>"+processSum.get ( j )+"</b></html>" );
        }
        data.add ( vector );

        try {
            rs.close ();
        } catch ( Exception e ) {
            StaticValues.writer.writeExcel ( ReportWIP2.class.getSimpleName () , ReportWIP2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );  
        }

        return new DefaultTableModel ( data , columnNames );
    }

    public void buildTableModelFromJSON ( String rs , String a , String b )       {

        int processesSum = 0, productSum = 0;
        ArrayList<String> processSum = new ArrayList<String> ();
        for ( int j = 0 ; j < processes.size () ; j ++ ) {
            processSum.add ( "0" );
        }
        
        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        columnNames.add ( "<html> <b><center>Product Name</center></b></html>" );

        for ( int column = 1 ; column < 3 ; column ++ ) {

            if ( column == 1 ) {
                for ( int i = 0 ; i < processes.size () ; i ++ ) {
                    columnNames.add ( "<html> <b>"+processes.get ( i ).PRC_NAME+"</b></html>" );
                }
            }
        }
        columnNames.add (1,  "<html> <b>TotalAll Processes</b></html>" );
        
        
        Vector<Vector<String>> data = new Vector<Vector<String>> ();
        Vector<String> vector = null;
        
        jTable1.setModel ( new DefaultTableModel ( data , columnNames ) );
        
        wipMap = new ArrayList<String[]> ();
        String[] map = null;
        
        for ( int i = 0 ; i < products.size () ; i ++ ) {
            processesSum = 0;
            productSum = 0;
            vector = new Vector<String> ();
            vector.add ( products.get ( i ).FG_PART_NO );

            boolean productMapped = false;

            //String processMapQuery = "SELECT REF_PROCESS_ID, PROCESS_NAME FROM FG_PROCESS_MACH_MAP a INNER JOIN PROCESS_MASTER b on a.REF_PROCESS_ID = b.PROCESS_ID AND REF_FG_ID = " + products.get ( i ).FG_ID;
            String addEmpAPICall = "processmachmaps?FG_ID="+products.get ( i ).FG_ID ;
            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            
            if( ! result2.contains( "\"records\":null" ) ) {
                try{

                    HashMap<String , Object> map2 = new HashMap<String , Object> ();
                    JSONObject jObject = new JSONObject ( result2 );
                    Iterator<?> keys = jObject.keys ();

                    while ( keys.hasNext () ) {
                        String key = ( String ) keys.next ();
                        Object value = jObject.get ( key );
                        map2.put ( key , value );
                    }

                    JSONObject st = ( JSONObject ) map2.get ( "data" );

                    JSONArray records = st.getJSONArray ( "records" );

                    //System.out.println ( "product process maps for  "+"fgid=" + products.get ( i ).FG_ID + "    "+ records.length ());

                    JSONObject emp = null;

                    processesMap = new ArrayList<String[]> ();
                    for ( int j = 0 ; j < records.length () ; j ++ ) {
                        emp = records.getJSONObject ( j );
                        processesMap.add ( new String[] {emp.get( "REF_PROCESS_ID" ).toString() ,emp.get( "PROCESS_NAME" ).toString() } );
                    }
                }catch( JSONException e  ){
                    System.out.println ( "FGID = "+products.get ( i ).FG_ID+"  Line No : 1224 : "+e.getMessage() );     
                }
        
                int l = 0, m=0 ;
                for ( int j = 0 ; j < processes.size () ; j ++ ) {
                    map = new String[ 5 ];
                /*    try{
                        for (  ; l < processesMap.size () && m<processes.size() ; ) {
                            String[] cc = new String[] { String.valueOf ( processes.get ( m ).PRC_ID ) , processes.get ( m ).PRC_NAME };
                            String[] arr = processesMap.get ( l );
                            if ( arr[ 0 ].equals ( cc[ 0 ] ) && arr[ 1 ].equals ( cc[ 1 ] ) ) {
                                productMapped = true;
                                l = processesMap.size ();
                            }else{
                                productMapped = false;
                            }
                             l ++; m++ ;
                        }
                    }catch( NullPointerException e  ){
                        System.out.println ( "Line No : 1226 : "+e.getMessage() );     
                    }*/

                    //if ( productMapped ) {
                    if( processesMap.size()>j ){
                    if ( isProcessMapped ( j ,  j  ) ) {    
                    //if ( isProcessMapped ( Integer.parseInt(processesMap.get(j)[0]),  processes.get ( j ).PRC_ID ) ) {

                        String addEmpAPICall2 = "progressreport?to-date=2099-12-31+00:00:00&from_date=1900-01-01+00:00:00&fgid=" + products.get ( i ).FG_ID + "&processid=" + processes.get ( j ).PRC_ID;
                       // String addEmpAPICall2 = "progressreport?to-date=2099-12-31+00:00:00&from_date=1900-01-01+00:00:00&fgid=" + products.get ( i ).FG_ID + "&processid=" + processesMap.get(m)[0];
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
                        JSONObject fgprmap = null;

                      //  System.out.println ( "Work in progress for "+"fgid=" + products.get ( i ).FG_ID + "&processid=" + processes.get ( j ).PRC_ID +"  "+records2.length ());

                        for( int k=0; k<records2.length (); k++){
                               fgprmap = records2.getJSONObject ( k );
                                map[ 0 ] = products.get ( i ).FG_ID + "";
                                map[ 1 ] = products.get ( i ).FG_PART_NO + "";
                                map[ 2 ] = processes.get ( j ).PRC_ID + "";
                                map[ 3 ] = processes.get ( j ).PRC_NAME + "";

                                map[ 4 ] = fgprmap.get ( "balance" ).toString() + "";

                                if( ! fgprmap.get ( "balance" ).toString().equals("null")  )
                                    vector.add ( "<html> <center>"+ fgprmap.get ( "balance" ).toString() +"</center></html>");
                                else
                                    vector.add ( "<html> <center>"+ "0" +"</center></html>" );

                                int currBal = 0;
                                try{
                                    currBal = Integer.parseInt(  fgprmap.get ( "balance" ).toString() ) ;
                                }catch( NumberFormatException e ){
                                    System.out.println ( ""+e.getMessage () );
                                }                            

                                processesSum = processesSum + currBal ;
                                processSum.set ( j , String.valueOf ( currBal + Integer.parseInt ( processSum.get ( j ) ) ) );
                        }
                    }else{
                        vector.add ( "<html> <center>"+ "--" +"</center></html>" );
                    }
                    wipMap.add ( map );
                }
                }
            }else{
                System.out.println ( "process map not found" );
            }
            vector.add ( 1, "<html> <b><center>"+processesSum+"</center></b></html>" );
            
            // manage null values in the table
           /* for( int counter =0; counter<vector.size() ;  counter++ ){
                if(vector.get(i)==null ){
                    vector.remove( counter ) ;
                    vector.add( counter,  "NA"  ) ;
                }
            } */
            
            data.add ( vector );
            jTable1.setModel ( new DefaultTableModel ( data , columnNames ) );   
        }
        
        vector = new Vector<String> ();
        vector.add ( "<html> <b>TotalAll Products</html>" );

        for ( int j = 0 ; j < processes.size () ; j ++ ) {
            //processSum.set( j , "0" );
            vector.add (  "<html> <b><alignment='center' >"+processSum.get ( j )+"</b></html>" );
        }
        vector.add ( 1, "<html> <b><alignment=center> </center></b></html>" );
        data.add ( 0, vector );
        
        
        jTable1.setModel ( new DefaultTableModel ( data , columnNames ) );
        
    }
    
    public boolean isProcessMapped( int idealMapIndex, int stdMapIndex  ){
        
        try{
            for ( int l = 0, m=0   ; l < processesMap.size () && m<processes.size() ;   l ++, m++ ) {
                
                if(  idealMapIndex==l && stdMapIndex==m   ){
                
                    String[] cc = new String[] { String.valueOf ( processes.get ( m ).PRC_ID ) , processes.get ( m ).PRC_NAME };
                    String[] arr = processesMap.get ( l );
                    if ( arr[ 0 ].equals ( cc[ 0 ] ) && arr[ 1 ].equals ( cc[ 1 ] ) ) {
                        //productMapped = true;
                        l = processesMap.size ();
                        return true ;
                    }else{
                        //productMapped = false;
                        return false ;
                    }
                }
                // ;
            }
        }catch( NullPointerException e  ){
            System.out.println ( "Line No : 1226 : "+e.getMessage() );     
            
        }
        return false;
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

                Vector<Object> vector = new Vector<Object> ();
                for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {

                    Object s = rs.getObject ( columnIndex );

                    if ( s != null ) {
                        vector.add ( s );
                    } else {
                        vector.add ( new String ( "--" ) );
                    }

                    String s1;

                    if ( columnIndex == 2 ) {
                        ab[ 0 ] = rs.getString ( columnIndex );

                    } else if ( columnIndex == 5 ) {
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
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setAutoscrolls(true);
        setMaximumSize(new java.awt.Dimension(1366, 760));
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(1300, 530));
        setLayout(null);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setOpaque(false);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1300, 530));

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setBackground(new java.awt.Color(255, 255, 255));
        jTable1.setForeground(new java.awt.Color(51, 51, 51));
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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setEnabled(false);
        jTable1.setOpaque(false);
        jTable1.setRowHeight(40);
        jTable1.setRowMargin(3);
        jTable1.setRowSelectionAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 1270, 530);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Product");
        add(jLabel2);
        jLabel2.setBounds(40, 90, 70, 16);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Employee");
        add(jLabel8);
        jLabel8.setBounds(40, 270, 80, 16);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Machine");
        add(jLabel7);
        jLabel7.setBounds(40, 210, 70, 16);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Process");
        add(jLabel12);
        jLabel12.setBounds(40, 150, 70, 16);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(jComboBox1);
        jComboBox1.setBounds(40, 120, 65, 26);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(jComboBox2);
        jComboBox2.setBounds(40, 150, 65, 26);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(jComboBox3);
        jComboBox3.setBounds(40, 180, 65, 26);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(jComboBox4);
        jComboBox4.setBounds(40, 210, 65, 26);
    }// </editor-fold>//GEN-END:initComponents

    SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-MM-dd" );

    public String formatFromDate () {

        String[] dateIP = new String ( "sdfsdf" ).split ( " " );

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

        //   c1.setFirstDayOfWeek ( Calendar.SUNDAY);
        //   System.out.println( "This is #"+  c1.get ( Calendar.WEEK_OF_MONTH) +" week in the month");
        //  System.out.println( "This is #"+  (c1.get ( Calendar.MONTH) +1)+"  month in the year");
        SimpleDateFormat sdf = new SimpleDateFormat ( "EEEE" );
        SimpleDateFormat sdf1 = new SimpleDateFormat ( "MMM" );
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy" );
        SimpleDateFormat sdf3 = new SimpleDateFormat ( "dd" );

        /*
         * // System.out.println( "This is #"+ (c1.get (
         * Calendar.DAY_OF_WEEK_IN_MONTH) )+" day of month in the year");
         * System.out.println( sdf.format(c1.getTime() )+" name of the day");
         * System.out.println( sdf1.format(c1.getTime() )+" name of the month");
         * System.out.println( sdf2.format(c1.getTime() )+" name of the year");
         * System.out.println( sdf3.format(c1.getTime() )+" name of the date");
         */
//          c1.add ( Calendar.DATE, 20);
//          System.out.println(  sdf.format(c1.getTime() )+" name of the day");
//          System.out.println(  sdf1.format(c1.getTime() )+"  month");
//          System.out.println(  sdf2.format(c1.getTime() )+"  year");
//          System.out.println(  sdf3.format(c1.getTime() )+"  date");
//          
        return fromDate;
    }

    public String formatToDate () {

        String[] dateIP2 = "".split ( " " );
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
    }

    ArrayList<String> weeklyDates = new ArrayList<String> ();
    ArrayList<String> months = new ArrayList<String> ();
    ArrayList<String> quarters = new ArrayList<String> ();
    SimpleDateFormat sdfTS = new SimpleDateFormat ( "yyyy-MM-dd" );

    SimpleDateFormat sdf = new SimpleDateFormat ( "EEEE" );
    SimpleDateFormat sdf1 = new SimpleDateFormat ( "MMM" );
    SimpleDateFormat sdf4 = new SimpleDateFormat ( "yyyy" );
    SimpleDateFormat sdf3 = new SimpleDateFormat ( "dd" );

    long totalNoOfDays = 0;

    public void showWeek () {

//        int i=0;
//        while( i < weeklyDates.size ()){
//            weeklyDates.remove ( i );
//            i++ ;
//        }
        weeklyDates = new ArrayList<String> ();

        formatFromDate ();
        formatToDate ();

        totalNoOfDays = ( ( ( ( ( c2.getTimeInMillis () - c1.getTimeInMillis () ) / 1000 ) / 60 ) / 60 ) / 24 ) + 1;

        System.out.println ( "Total no of days " + totalNoOfDays );

        weeklyDates.add ( sdfTS.format ( c1.getTime () ) + " 00:00:00" );

        // calculation for first week of fromDate does not fall on Sunday
        String day = sdf.format ( c1.getTime () );

        //if(! (day.equalsIgnoreCase ( "Sunday"))){
        switch ( day ) {

//                case "Saturday" :      c1.add ( Calendar.DATE, 6);  totalNoOfDays=totalNoOfDays-5;  break ;
//                case "Monday" :        c1.add ( Calendar.DATE, 5);  totalNoOfDays=totalNoOfDays-4;  break ;
//                case "Tuesday" :       c1.add ( Calendar.DATE, 4);  totalNoOfDays=totalNoOfDays-3; break ;
//                case "Wednesday" :  c1.add ( Calendar.DATE, 3);  totalNoOfDays=totalNoOfDays-2; break ;
//                case "Thursday" :      c1.add ( Calendar.DATE, 2);  totalNoOfDays=totalNoOfDays-1; break ;
//                case "Friday" :          c1.add ( Calendar.DATE, 1);  totalNoOfDays=totalNoOfDays-0; break ;
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

    public String showMonths () {

        formatFromDate ();
        Calendar c = Calendar.getInstance ();

        // System.out.println( "From month is  "+ c1.getActualMaximum ( Calendar.DAY_OF_MONTH));
        // System.out.println( "From month is  "+sdf1.format(c1.getTime()));
        for ( int i = 2 ; i <= 13 ; i ++ ) {

            c.set ( Calendar.DATE , 1 );
            c.set ( Calendar.MONTH , i + 1 );
            c.set ( Calendar.YEAR , Integer.parseInt ( sdf4.format ( c1.getTime () ) ) );

            c.set ( Calendar.HOUR , 0 );
            c.set ( Calendar.MINUTE , 0 );
            c.set ( Calendar.SECOND , 0 );

            // System.out.print( sdf2.format ( c.getTime ()) +" 00:00:00" );
            months.add ( sdf2.format ( c.getTime () ) + " 00:00:00" );

            c.set ( Calendar.DATE , c.getActualMaximum ( Calendar.DAY_OF_MONTH ) );
            c.set ( Calendar.MONTH , i + 1 );
            c.set ( Calendar.YEAR , Integer.parseInt ( sdf4.format ( c1.getTime () ) ) );

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

    public String showQuarters () {

        formatFromDate ();
        Calendar c = Calendar.getInstance ();

        // System.out.println( "From month is  "+ c1.getActualMaximum ( Calendar.DAY_OF_MONTH));
        // System.out.println( "From month is  "+sdf1.format(c1.getTime()));
        for ( int i = 3 ; i <= 14 ; i += 3 ) {

            c.set ( Calendar.DATE , 1 );
            c.set ( Calendar.MONTH , i );
            c.set ( Calendar.YEAR , Integer.parseInt ( sdf4.format ( c1.getTime () ) ) );

            c.set ( Calendar.HOUR , 0 );
            c.set ( Calendar.MINUTE , 0 );
            c.set ( Calendar.SECOND , 0 );

            // System.out.print( sdf2.format ( c.getTime ()) +" 00:00:00" );
            quarters.add ( sdf2.format ( c.getTime () ) + " 00:00:00" );

            c.set ( Calendar.MONTH , i + 2 );
            c.set ( Calendar.DATE , c.getActualMaximum ( Calendar.DAY_OF_MONTH ) );
            c.set ( Calendar.YEAR , Integer.parseInt ( sdf4.format ( c1.getTime () ) ) );

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserDialog dateChooserDialog1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

class IconHeaderRenderer extends DefaultTableCellRenderer {
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
      boolean hasFocus, int row, int column) {
    if (table != null) {
      JTableHeader header = table.getTableHeader();
      if (header != null) {
        setForeground(header.getForeground());
        setBackground(header.getBackground());
        setFont(header.getFont());
      }
    }

    if (value instanceof Icon) {
      setIcon((Icon) value);
      setText("");
    } else {
      setText((value == null) ? "" : value.toString());
      setIcon(null);
    }
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    setHorizontalAlignment(JLabel.CENTER);
    return this;
  }
}