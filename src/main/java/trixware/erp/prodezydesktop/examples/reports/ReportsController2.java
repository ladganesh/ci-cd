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
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import datechooser.view.WeekDaysStyle;
import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import trixware.erp.prodezydesktop.Model.Customer;
import trixware.erp.prodezydesktop.Model.EmployeeDR;
import trixware.erp.prodezydesktop.Model.MachineDR;
import trixware.erp.prodezydesktop.Model.ProcessDR;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.ShiftDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import static trixware.erp.prodezydesktop.examples.HomeScreen.home;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.Export_Print_Utility;

/**
 *
 * @author Rajesh
 */
public class ReportsController2 extends javax.swing.JPanel {

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
    static Vector<Vector<Object>> data2 = null;
    static Vector<String> columnNames = null;
    static DefaultCategoryDataset dataSet = null;
    static DefaultPieDataset  dataSet2 = null ;
    static ArrayList<String[]> abc = null;
    static ChartPanel cp = null, cp2 = null;

    Calendar c1 = Calendar.getInstance ();
    Calendar c2 = Calendar.getInstance ();

    int columnCount;

    JFreeChart chart = null;
    JFreeChart chart2 = null;

    String reportMasterType;
    
    JScrollPane scroll = null ;
     JPanel panel = null ;
     
        JScrollPane scroll2 = null ;
     JPanel panel2 = null ;

    /**
     * Creates new form DailyReportForm
     */
    public ReportsController2 () {

        initComponents ();

    }

    public ReportsController2 ( String reportMasterType ) {
        initComponents ();

       

        this.reportMasterType = reportMasterType;

        loadComboBoxes ();

         dateChooserCombo4.setText ( dateChooserCombo3.getText () );
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

        dataSet = new DefaultCategoryDataset ();

        for ( int i = 0 ; i < 5 ; i ++ ) {
            dataSet.setValue ( 0 , "Defect Occurrences" , String.valueOf ( i ) );
        }

        chart = ChartFactory.createBarChart3D (
                "Title Value" , "xAxis" , "yAxis" ,
                dataSet , PlotOrientation.VERTICAL , true , true , false );

         panel =  new JPanel();
         panel.setBackground ( Color.WHITE);
         //panel.setBounds ( 0,0 , 1000 , 300 );
         panel.revalidate ();
         panel.repaint ();
         
         panel2 =  new JPanel();
         panel2.setBackground ( Color.WHITE);
         //panel.setBounds ( 0,0 , 1000 , 300 );
         panel2.revalidate ();
         panel2.repaint ();
        
         
         scroll2 = new JScrollPane ( panel2 ,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        
        scroll2.setBounds ( 267 , 290 , 500 , 280 );
        scroll2.setOpaque ( false);
        scroll2.setBackground ( Color.WHITE );
        
         
        scroll = new JScrollPane ( panel ,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        
        scroll.setBounds ( 780 , 290 , 500 , 280 );
        scroll.setOpaque ( false);
        scroll.setBackground ( Color.WHITE );
        
        
        
        
        
        cp = new ChartPanel ( chart );
        cp.setPreferredSize ( new java.awt.Dimension ( 500 , 240 ) );
        cp.setBounds ( 0, 0 , 530 , 280 );
        
        
        panel.add( cp );
        scroll.revalidate ();
        scroll.repaint ();
        add( scroll );
        
        
        cp2 = new ChartPanel ( chart2 );
        cp2.setPreferredSize ( new java.awt.Dimension ( 500 , 240 ) );
        cp.setBounds ( 0, 0 , 530 , 280 );
        
        
        panel2.add( cp2 );
        scroll2.revalidate ();
        scroll2.repaint ();
        add( scroll2 );
        
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

   

    DecimalFormat df = new DecimalFormat ( "#.##" );

    public JFreeChart generateBarChart ( String reportTitle , String xAxis , String yAxis ) {

        try {
            panel.remove ( cp );
        } catch ( Exception e ) {
//               System.out.println("Error component "+e.getMessage ());
            StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        dataSet = new DefaultCategoryDataset ();

        for ( int i = 0 ; i < abc.size () ; i ++ ) {
            try {
                dataSet.setValue ( Integer.parseInt ( abc.get ( i )[ 1 ] ) , "Occurences", abc.get ( i )[ 0 ] );
                
            } catch ( Exception e ) {
                try{
                    dataSet.setValue ( Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 1 ] ) ) ) ,"Occurences" , abc.get ( i )[ 0 ] );
                }catch(Exception e2){
                    StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }
        //        System.out.println ( " "+ Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 1 ] ) ) ) +  "    "+ abc.get ( i )[ 1 ]  );
                StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }

       

            for ( int i = 0 ; i < abc.size () ; i ++ ) {
                try {
                    dataSet.setValue ( Integer.parseInt ( abc.get ( i )[ 2 ] ) , "Count" , abc.get ( i )[ 0 ] );
                     
                } catch ( Exception e ) {
                    try{
                        dataSet.setValue ( Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 2 ] ) ) ) , "Count"  , abc.get ( i )[ 0 ] );
                    }catch(Exception e2){
                        StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }
                //    System.out.println ( " "+ Float.parseFloat ( df.format ( Float.parseFloat ( abc.get ( i )[ 2 ] ) ) ) +  "    "+ abc.get ( i )[ 1 ]  );
                StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }
            }
        


        JFreeChart chart = ChartFactory.createBarChart3D (
                reportTitle , xAxis , yAxis ,
                dataSet , PlotOrientation.VERTICAL , true , true , false );

        cp = new ChartPanel ( chart );
        
//        if( (abc.size ()*90) >340){
//             cp.setPreferredSize ( new java.awt.Dimension ( 530 , 280 ) );
//            cp.setBounds ( 300 , 310 , 530 , 280 );
//        }else{
             cp.setPreferredSize ( new java.awt.Dimension ( 500 , 280 ) );
            cp.setBounds ( 300 , 310 , 500 , 280 );
        //}
        cp.revalidate ();
        cp.repaint ();

        CategoryPlot plot = ( CategoryPlot ) chart.getPlot ();
        plot.setBackgroundPaint ( Color.lightGray );
        plot.setDomainGridlinePaint ( Color.white );
        plot.setDomainGridlinesVisible ( false );
        plot.setRangeGridlinePaint ( Color.white );

        
            ValueMarker marker = new ValueMarker ( 50000 );
            marker.setLabel ( "Reference Value" );
            marker.setLabelTextAnchor ( TextAnchor.CENTER_LEFT );
            marker.setPaint ( Color.WHITE );
            plot.addRangeMarker ( marker );
        

        if ( abc.size () > 2 ) {
            CategoryAxis domainAxis = plot.getDomainAxis ();
            domainAxis.setCategoryLabelPositions ( CategoryLabelPositions.UP_45 );
        }

        BarRenderer renderer = ( BarRenderer ) plot.getRenderer ();
        renderer.setBaseItemLabelsVisible ( true );
        renderer.setBaseItemLabelGenerator ( new StandardCategoryItemLabelGenerator () );
        renderer.setBarPainter ( new StandardBarPainter () );
        renderer.setSeriesPaint ( 1 , Color.CYAN );
        renderer.setSeriesPaint ( 1 , Color.MAGENTA );
        renderer.setSeriesItemLabelFont ( 1 , new Font ( "Times New Roman" , Font.PLAIN , 8 ) );
        renderer.setSeriesItemLabelPaint ( 0 , Color.BLACK );
         renderer.setSeriesItemLabelFont ( 0 , new Font ( "Times New Roman" , Font.PLAIN , 8 ) );
        renderer.setSeriesItemLabelPaint ( 1 , Color.BLACK );

        //add ( cp );
        panel.add(cp);
        panel.revalidate ();
        panel.repaint ();

       // HomeScreen.home.pack ();
        //HomeScreen.home.revalidate ();
        //HomeScreen.home.repaint ();

        return chart;
    }

    public  void  generatePieChart () {
//        DefaultPieDataset dataSet = new DefaultPieDataset ();
//        dataSet.setValue ( "China" , 19.64 );
//        dataSet.setValue ( "India" , 17.3 );
        
        dataSet2 = new DefaultPieDataset ();

        try {
            panel2.remove ( cp2 );
        } catch ( Exception e ) {
            StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        for ( int i = 0 ; i < abc.size () ; i ++ ) {
                try {
                    dataSet2.setValue (  abc.get ( i )[ 0 ] ,  Double.parseDouble( abc.get ( i )[ 1 ] ) );
                     
                } catch ( Exception e ) {
                    try{
                        dataSet2.setValue (   abc.get ( i )[ 0 ]   ,  Double.parseDouble( abc.get ( i )[ 1 ]) );
                    }catch(Exception e2){
                        System.out.println ( ""+e2.getMessage () );   
                        StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }
                    StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }
            }

//        JFreeChart chart = ChartFactory.createPieChart (
//                "Defect Occcrrence" , dataSet2 , true , true , false );

        JFreeChart chart = ChartFactory.createPieChart3D ( "Defect Occcrrence" , dataSet2 , true , true , false ) ;
        chart.setBackgroundPaint(Color.white) ;
// 
        
        PiePlot plot = (PiePlot) chart.getPlot ();
//        StandardPieSectionLabelGenerator labels = new StandardPieSectionLabelGenerator("{0} = {2}");
//        plot.setLabelGenerator(labels);
        plot.setInteriorGap(0);
        plot.setBackgroundPaint(Color.white);
        plot.setBaseSectionOutlinePaint(Color.blue);
        plot.setBaseSectionPaint(Color.green);
        plot.setShadowPaint(Color.black);
        plot.setShadowXOffset(0);
        plot.setShadowYOffset(0);
        plot.setOutlineVisible(false);
        //plot.setExplodePercent(abc.get ( 1 )[ 0 ], 0.10);
        plot.setSimpleLabels(true);

        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
            "{0}: {1} ", new DecimalFormat("0"), new DecimalFormat("0%"));
        plot.setLabelGenerator(gen);
        
        chart.setPadding(new RectangleInsets(0, 0, 0, 0));
        chart.setBackgroundPaint(Color.white) ;
        chart.setBorderVisible(false);
        //chart.clearSubtitles();
        
        
        cp2 = new ChartPanel ( chart );
//        if( (abc.size ()*90) >340){
//            cp2.setPreferredSize ( new java.awt.Dimension ( (abc.size ()*100) , (abc.size ()*90) ) );
//            cp2.setBounds ( 300 , 310 , (abc.size ()*100) ,  (abc.size ()*100) );
//        }else{
//            cp2.setPreferredSize ( new java.awt.Dimension ( (abc.size ()*100) , (abc.size ()*90) ) );
//            cp2.setBounds ( 300 , 310 , (abc.size ()*100) ,  340 );
                cp2.setPreferredSize ( new java.awt.Dimension ( 500 , 280) );
                cp2.setBounds ( 300 , 310 , 500 , 280 );
//        }
        cp2.revalidate ();
        cp2.repaint ();
        
        panel2.add(cp2);
        panel2.revalidate ();
        panel2.repaint ();
        
    }

    public void loadComboBoxes () {

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
            StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
            StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
            StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
            StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
            StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
            StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
                    columnNames.add ( "<html><center>"+ metaData.getColumnName ( column ) +"</center></html>");
                }
            }

            int j = 1;
            // data of the table
            while ( rs.next () ) {

                String[] ab = new String[ 2 ];
                
               
                    ab = new String[ 3 ];
                

                Vector<Object> vector = new Vector<Object> ();
                for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {

                    Object s = rs.getObject ( columnIndex );

                    if ( columnIndex == 1 ) {
                            vector.add ( s );
                    } else {
                            if( s instanceof  Double){
                                vector.add(new String(df.format ( Float.parseFloat ( rs.getString ( columnIndex ) ) )));                                
                            }else{
                                vector.add ( s );
                            }
                    }

                    String s1;
                    Object abc ; 

                    if ( columnIndex == 1 ) {
                        
                            ab[ 0 ] = rs.getString ( columnIndex );

                    } else if ( columnIndex == 2 ) {
                        s1 = rs.getString ( columnIndex );
                        abc = rs.getObject ( columnIndex );
                        if ( abc != null ) {
                            
                            if( abc instanceof  Double){
                                ab[ 1 ] =df.format ( Float.parseFloat ( rs.getString ( columnIndex ) ) );
                                
                            }else{
                                ab[ 1 ] =  rs.getString ( columnIndex );
                            }
                         
                        } else {
                            ab[ 1 ] = "0";
                        }

                    }else if ( columnIndex == 3 ) {
                        
                            s1 = rs.getString ( columnIndex );
                            abc = rs.getObject ( columnIndex );
                            if ( abc != null ) {
                            
                                if( abc instanceof  Double){
                                    ab[ 2 ] = df.format ( Float.parseFloat ( rs.getString ( columnIndex ) ) );
                                
                                }else{
                                    ab[ 2 ] =  rs.getString ( columnIndex ) ;
                                }
                            
                            
                            } else {
                                ab[ 2 ] = "0";
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
        jComboBox4 = new javax.swing.JComboBox<>();
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
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(0, 204, 204));
        setAutoscrolls(true);
        setMaximumSize(new java.awt.Dimension(1366, 760));
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(1293, 590));
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
        jScrollPane1.setBounds(267, 20, 1020, 240);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setLayout(null);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("From Date");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 0, 80, 20);

        dateChooserCombo3.setCalendarPreferredSize(new java.awt.Dimension(330, 230));
        dateChooserCombo3.setFormat(2);
        jPanel1.add(dateChooserCombo3);
        dateChooserCombo3.setBounds(20, 20, 155, 30);

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("To Date");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(20, 50, 80, 20);

        dateChooserCombo4.setCalendarPreferredSize(new java.awt.Dimension(330, 230));
        jPanel1.add(dateChooserCombo4);
        dateChooserCombo4.setBounds(20, 70, 155, 30);

        jComboBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Daily", "Weekly", "Monthly", "Quarterly", "Yearly" }));
        jComboBox5.setNextFocusableComponent(jComboBox1);
        jPanel1.add(jComboBox5);
        jComboBox5.setBounds(20, 140, 160, 30);

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Product");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 190, 70, 20);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox1.setEnabled(false);
        jComboBox1.setNextFocusableComponent(jCheckBox1);
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(10, 210, 160, 30);

        jCheckBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(0, 0, 0));
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
        jCheckBox1.setBounds(170, 210, 80, 30);

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Process");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(10, 240, 70, 20);

        jComboBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox4.setEnabled(false);
        jComboBox4.setNextFocusableComponent(jCheckBox2);
        jPanel1.add(jComboBox4);
        jComboBox4.setBounds(10, 260, 160, 30);

        jCheckBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox2.setForeground(new java.awt.Color(0, 0, 0));
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
        jCheckBox2.setBounds(170, 260, 80, 30);

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Machine");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(10, 290, 70, 20);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox2.setEnabled(false);
        jComboBox2.setNextFocusableComponent(jCheckBox3);
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox2);
        jComboBox2.setBounds(10, 310, 160, 30);

        jCheckBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox3.setForeground(new java.awt.Color(0, 0, 0));
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
        jCheckBox3.setBounds(170, 310, 80, 30);

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Employee");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(10, 340, 80, 20);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox3.setEnabled(false);
        jComboBox3.setNextFocusableComponent(jCheckBox4);
        jPanel1.add(jComboBox3);
        jComboBox3.setBounds(10, 360, 160, 30);

        jCheckBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox4.setForeground(new java.awt.Color(0, 0, 0));
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
        jCheckBox4.setBounds(170, 360, 80, 30);

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Shift");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(10, 390, 40, 20);

        jComboBox6.setEnabled(false);
        jPanel1.add(jComboBox6);
        jComboBox6.setBounds(10, 410, 160, 30);

        jCheckBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox5.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox5.setSelected(true);
        jCheckBox5.setText("Select All");
        jCheckBox5.setOpaque(false);
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox5);
        jCheckBox5.setBounds(170, 410, 80, 30);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Generate Report");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(10, 500, 120, 40);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Close");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(130, 500, 120, 40);

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Frequency");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 120, 59, 16);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Hide");
        jLabel6.setOpaque(true);
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel6);
        jLabel6.setBounds(210, 10, 50, 16);

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Customer");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(10, 440, 80, 20);

        jComboBox7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox7.setEnabled(false);
        jPanel1.add(jComboBox7);
        jComboBox7.setBounds(10, 460, 160, 30);

        jCheckBox6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox6.setSelected(true);
        jCheckBox6.setText("Select All");
        jCheckBox6.setOpaque(false);
        jCheckBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox6ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox6);
        jCheckBox6.setBounds(170, 460, 80, 24);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Print");
        jPanel1.add(jButton3);
        jButton3.setBounds(10, 540, 120, 40);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("Export");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(130, 540, 120, 40);

        add(jPanel1);
        jPanel1.setBounds(0, 0, 260, 590);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Tabular View");
        add(jLabel9);
        jLabel9.setBounds(290, 0, 160, 20);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Graphical View");
        add(jLabel10);
        jLabel10.setBounds(290, 270, 180, 20);
    }// </editor-fold>//GEN-END:initComponents

    SimpleDateFormat sdf2 = new SimpleDateFormat ( "yyyy-MM-dd" );

    public String formatFromDate () {

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

        return fromDate;
    }

    public String formatToDate () {

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
    }

    public int getLastProcess ( int productId ) {

        ResultSet queryLastProcess = DB_Operations.executeSingle ( "SELECT REF_PROCESS_ID, max(FPM_ID) FROM FG_PROCESS_MACH_MAP WHERE REF_FG_ID = " + productId );

        try {
            if ( queryLastProcess != null && queryLastProcess.isBeforeFirst () ) {
                return queryLastProcess.getInt ( 1 );
            }
        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        return 0;
    }

    public float getRateforProductAndCustomer ( int productId , int customerId ) {

        ResultSet queryLastProcess = DB_Operations.executeSingle ( "SELECT SALES_RATE FROM FG_CUSTOMER_MAP WHERE M_FG_ID = " + productId + " AND  M_CUST_ID = " + customerId );

        try {
            if ( queryLastProcess != null && queryLastProcess.isBeforeFirst () ) {
                return queryLastProcess.getInt ( 1 );
            }
        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        return 0;
    }

    public float getProductionCost ( int productId ) {

        ResultSet queryProdCost = DB_Operations.executeSingle ( "SELECT PROD_COST FROM finished_goods WHERE FG_ID = " + productId );

        try {
            if ( queryProdCost != null && queryProdCost.isBeforeFirst () ) {
                return queryProdCost.getInt ( 1 );
            }
        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        return 0;
    }

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked

     
        data = new Vector<Vector<Object>> ();
        columnNames = new Vector<String> ();
        abc = new ArrayList<String[]> ();

        ResultSet result;

        String DOQuery = StaticValues.defectOccurences ;

        try{
                result = DB_Operations.executeSingle ( DOQuery ) ;
                jTable1.setModel ( buildTableModel ( result , "Defect Occurrences" , "xAxisLabel" ) );
                generateBarChart ( "Defect Occurrences" , "Defects" , "Occurrences" );
                
                generatePieChart () ;
                
        }catch( SQLException sqlex1 ){
                StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , sqlex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , sqlex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

    }//GEN-LAST:event_jButton1MouseClicked

    public void loadEntries000 ( String reportType ) {

        ResultSet result;

        try {

            String xAxisLabel;

            int processId = processes.get ( resIndex ).PRC_ID;

            if ( reportType.equalsIgnoreCase ( "Yearly" ) ) {

                //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty',     rdate,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty'  from dailyreport where rdate >= '" + formatFromDate () + "' AND rdate <= '" + formatToDate () + "'" );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Year to Date Production " , "YEAR" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Daily" ) ) {

                String query = "select  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + formatFromDate () + "' AND rdate <= '" + formatToDate () + "'";
                result = DB_Operations.executeSingle ( query );
                jTable1.setModel ( buildTableModel ( result , reportType , "xAxisLabel" ) );

                generateBarChart ( "Daily production report for period " + sdfTS.format ( c1.getTime () ) + " to " + sdfTS.format ( c2.getTime () ) , "DAYS" , "PRODUCTION" );

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

                generateBarChart ( "Weekly production report for period " + weeklyDates.get ( 0 ).split ( " " )[ 0 ] + " to " + sdfTS.format ( c2.getTime () ) , "WEEKS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Monthly" ) ) {

                String year = showMonths ();

                String[] monthsTitles = new String[] { "April" , "May" , "June" , "July" , "August" , "September" , "October" , "November" , "December" , "January" , "February" , "March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < months.size () && k < 12 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + months.get ( i ) + "' AND rdate <= '" + months.get ( j ) + "'" );
                    jTable1.setModel ( buildTableModel ( result , reportType , monthsTitles[ k ] ) );
                }

                generateBarChart ( "Monthly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "MONTHS" , "PRODUCTION" );

            } else if ( reportType.equalsIgnoreCase ( "Quarterly" ) ) {

                String year = showQuarters ();

                String[] quarterTitles = new String[] { "April - June" , "July - September" , "October - December" , "January - March" };

                for ( int i = 0, j = i + 1, k = 0 ; j < quarters.size () && k < 4 ; i = i + 2 , j += 2 , k ++ ) {

                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    result = DB_Operations.executeSingle ( "select   showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty'  from dailyreport where  rdate >= '" + quarters.get ( i ) + "' AND rdate <= '" + quarters.get ( j ) + "'" );
                    jTable1.setModel ( buildTableModel ( result , reportType , quarterTitles[ k ] ) );
                }

                generateBarChart ( "Quarterly production report for year " + year + " - " + ( Integer.parseInt ( year ) + 1 ) , "Quarters" , "PRODUCTION" );

                //generateBarChart ( "Quarterly production report for period "+sdfTS.format(c1.getTime())+" to "+sdfTS.format(c2.getTime()),   "QUARTERS", "PRODUCTION" );
            }

        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel ( ReportsController2.class.getSimpleName () , ReportsController2.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
        home.removeForms ();
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
          //  cp.setBounds ( 60 , 280 , 600 , 280 );
            scroll.setBounds ( 57 , 290 , 560 , 280  );
           // cp2.setBounds ( 580 , 280 , 600 , 280 );
            scroll2.setBounds ( 580 , 290 , 560 , 280  );
            
            
            jLabel9.setBounds( 57, 0, 160, 20 ); 
            jLabel10.setBounds( 57, 270 , 180, 20 );       
        } else if ( jLabel6.getText ().equals ( "Show" ) ) {

            jPanel1.setBounds ( 0 , 0 , 260 , 610 );
            jLabel6.setText ( "Hide" );

            jScrollPane1.setBounds ( 297 , 20 , 1000 , 240 );
            //cp.setBounds ( 300 , 280 , 450 , 280 );
            scroll.setBounds ( 780 , 290 , 480 , 280  );
           // cp2.setBounds ( 760 , 280 , 450 , 280 );
            scroll2.setBounds ( 267 , 290 , 480 , 280  );
            
            jLabel9.setBounds( 290, 0, 160, 20 ); 
            jLabel10.setBounds( 290, 270 , 180, 20 );     
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
        File f = new File("Printed Reports");
        f.mkdir ();
        Export_Print_Utility.writeChartToPDF ( chart ,  800, 400 , data, columnNames, "Printed Reports\\"+reportMasterType+".pdf" );
    }//GEN-LAST:event_jButton4MouseClicked

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
