/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.awt.Color;
import java.awt.SystemColor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
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
import trixware.erp.prodezydesktop.Utilities.DB_Operations;

/**
 *
 * @author Rajesh
 */
public class ProductionNWIP1 extends javax.swing.JPanel {

//    ArrayList<HashMap<String, String>> products = null ; 
//    ArrayList<HashMap<String, String>> employees = null ; 
//    ArrayList<HashMap<String, String>> machines = null ; 
//    ArrayList<HashMap<String, String>> processes;
    static ArrayList<ProductDR> products = null;
    ArrayList<EmployeeDR> employees = null;
    static ArrayList<ProcessDR> processes = null;
    ArrayList<MachineDR> machines = null;

    ProductDR prdr = null;
    EmployeeDR empdr = null;
    ProcessDR prcdr = null;
    MachineDR mcdr = null;
//    HashMap<String, String> entity = null ;

    Calendar c2 = null;
    SimpleDateFormat sdf = null;
    
    static String fromDate, toDate ;
    
    String reportLabel1 = "Process Wise / ProductWise Report for ";
    String reportLabel2 = "Date Wise report for all processes for product";
    String reportLabel3 = "Date wIse combined report for selected process";
    String reportLabel4 = "Combined report between";
    
    public static int resIndex = 0;
    
    static Vector<Vector<Object>> data = null;
    static Vector<String> columnNames = null ;
    static DefaultCategoryDataset dataSet = null;
    static ArrayList<String[]> abc = null;
    static ChartPanel cp  = null ;
    
    /**
     * Creates new form DailyReportForm
     */
    public ProductionNWIP1 () {
        initComponents ();

        loadComboBoxes ();
        
        

       // loadEntries( fromDate, toDate,0,0,0,0);
        
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
//        cal.clear(Calendar.MINUTE);
//        cal.clear(Calendar.SECOND);
//        cal.clear(Calendar.MILLISECOND);
//
//        // get start of this week in milliseconds
//        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
//        System.out.println("Start of this week:       " + cal.getTime());
//        
       
       // generateBarChart ();
    
    }
    
    // Report no 1 for 'Daily' filter
    // select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  rdate as 'Start Date', qtyout as 'Output Qty', (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid=4 order by rdate
    
    // Report no 2 for 'Weekly' filter
    //select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',   SUM(qtyout) as 'Output Qty', SUM((qtyout/(stoptime-starttime))) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '03-06-2018'  AND rdate <= '08-06-2018' AND fgid=4 AND processid = 1 
    //select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',   SUM(qtyout) as 'Output Qty', SUM((qtyout/(stoptime-starttime))) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '03-06-2018'  AND rdate <= '08-06-2018' AND fgid=4 AND processid = 2 
    //select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',   SUM(qtyout) as 'Output Qty', SUM((qtyout/(stoptime-starttime))) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '03-06-2018'  AND rdate <= '08-06-2018' AND fgid=4 AND processid = 3
    
    
    public  JFreeChart generateBarChart() {
		
        dataSet = new DefaultCategoryDataset();
//		dataSet.setValue(791, "Population", "1750 AD");
//		dataSet.setValue(978, "Population", "1800 AD");
//		dataSet.setValue(1262, "Population", "1850 AD");
//		dataSet.setValue(1650, "Population", "1900 AD");
//		dataSet.setValue(2519, "Population", "1950 AD");
//		dataSet.setValue(6070, "Population", "2000 AD");

                                for(int i=0; i<abc.size (); i++){
                                      dataSet.setValue( Integer.parseInt(abc.get(i)[1]), "Production",  abc.get(i)[0] );                                                                     
                                }    

//		chart = ChartFactory.createBarChart3D(
//				"Daily Production", "Dates", "Production",
//				dataSet, PlotOrientation.VERTICAL, true, true, false);

                               
                                JFreeChart chart  = ChartFactory.createBarChart3D(
				"Daily Production", "Dates", "Production",
				dataSet, PlotOrientation.VERTICAL, true, true, false);
                                
                                ChartPanel cp = new ChartPanel( chart );
                                cp.setPreferredSize ( new java.awt.Dimension(940,350));
                                cp.setBounds ( 40, 180, 940, 350);
                                
                                
                                CategoryPlot cplot = (CategoryPlot)chart.getPlot();
                                cplot.setBackgroundPaint(SystemColor.inactiveCaption);//change background color

                                //set  bar chart color
                                ((BarRenderer)cplot.getRenderer()).setBarPainter(new StandardBarPainter());
                                BarRenderer r = (BarRenderer)chart.getCategoryPlot().getRenderer();
                                r.setSeriesPaint(0, Color.blue);
                                
                                add ( cp );
                               HomeScreen3.home.pack ();
                
		return chart;
	}
    
    public static JFreeChart generatePieChart() {
		DefaultPieDataset dataSet = new DefaultPieDataset();
		dataSet.setValue("China", 19.64);
		dataSet.setValue("India", 17.3);
		dataSet.setValue("United States", 4.54);
		dataSet.setValue("Indonesia", 3.4);
		dataSet.setValue("Brazil", 2.83);
		dataSet.setValue("Pakistan", 2.48);
		dataSet.setValue("Bangladesh", 2.38);

		JFreeChart chart = ChartFactory.createPieChart(
				"Daily Production", dataSet, true, true, false);
                 
		return chart;
	}

    public void loadComboBoxes () {

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
                //    entity.put ( rs.getString(1) /*  String.valueOf( rs.getInt(1) ) */, rs.getString(2)) ;
                prdr.FG_ID = Integer.parseInt ( rs.getString ( 1 ) );
                prdr.FG_PART_NO = rs.getString ( 2 );
                products.add ( prdr );
                jComboBox1.addItem ( rs.getString ( 2 ) );
            }
        } catch ( Exception e ) {
            System.out.println ( " Error 1 " + e.getMessage () );
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
        }

        try {
            rs = DB_Operations.executeSingle ( queryThree );
            machines = new ArrayList<MachineDR>();

            while ( rs.next () ) {
                mcdr = new MachineDR ();
                
                mcdr.MC_ID = Integer.parseInt ( rs.getString ( 1 ) );
                mcdr.MC_NAME = rs.getString ( 2 );
                machines.add ( mcdr );
                jComboBox2.addItem ( rs.getString ( 2 ) );
            }
        } catch ( Exception e ) {
            System.out.println ( " Error 2 " + e.getMessage () );
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
        }
        
        
        try{
            
                        
            
        }catch(Exception e ){
            
        }
        

    }

    
    @SuppressWarnings( "null" )
    public void loadEntries(String reportType, int product){
        
        ResultSet result ;
        
        try {
            
            int processId;
            //for( resIndex=0; resIndex<processes.size ();  resIndex++){ 

                System.out.println ( reportType );
                
                processId = processes.get ( resIndex ).PRC_ID; 
                
                if(reportType.equalsIgnoreCase ( "Yearly") ){
                    
                    //select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',   SUM(qtyout) as 'Output Qty', SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '01-01-2018' AND rtdate <= '31-01-2018' AND fgid=4 AND processid=1
                    //select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',   SUM(qtyout) as 'Output Qty', SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '01-01-2018' AND rtdate <= '31-01-2018' AND fgid=4 AND processid=2
                    //select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',   SUM(qtyout) as 'Output Qty', SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '01-01-2018' AND rtdate <= '31-01-2018' AND fgid=4 AND processid=3
                    
                    System.out.println ( "Inside yearly report" );
                    
                    data = new Vector<Vector<Object>>();
                    columnNames = new Vector<String>();
                     abc = new ArrayList<String[]>();
                    
//                    result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',   SUM(qtyout) as 'Output Qty', SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '"+"01-01-2018"+"' AND rtdate <= '"+"31-01-2018"+"' AND fgid="+product+" AND processid="+1);
//                    jTable1.setModel(buildTableModel(result));
//                    result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',   SUM(qtyout) as 'Output Qty', SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '"+"01-01-2018"+"' AND rtdate <= '"+"31-01-2018"+"' AND fgid="+product+" AND processid="+2);
//                    jTable1.setModel(buildTableModel(result));
//                    result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',   SUM(qtyout) as 'Output Qty', SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '"+"01-01-2018"+"' AND rtdate <= '"+"31-01-2018"+"' AND fgid="+product+" AND processid="+3);
//                    jTable1.setModel(buildTableModel(result));

                    result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',    showrdate  as 'Start Date',     SUM(qtyout) as 'Output Qty',     rdate,        SUM(qtyout/(stoptime-starttime)) as 'Speed', SUM(stoptime-starttime) as 'Hrs', SUM(qtyin-qtyout) as 'Balence' from dailyreport where rdate >= '"+"01-04-2018"+"' AND rtdate <= '"+"31-03-2018"+"' AND fgid="+product);
                    jTable1.setModel(buildTableModel(result));
                    
                    
                }else if(reportType.equalsIgnoreCase ( "Daily") ){
            
                    data = new Vector<Vector<Object>>();
                    columnNames = new Vector<String>();
                     abc = new ArrayList<String[]>();
                    
                    //result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  rdate as 'Start Date', qtyout as 'Output Qty', (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where rdate = '"+fromdate+"'  AND fgid="+product+" order by processid");
                    result = DB_Operations.executeSingle ("select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  showrdate  as 'Start Date', SUM(qtyout) as 'Output Qty',    rdate,                  (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid="+product+" group by rdate");
                    
                    // sum of records by process title
                    //select (select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , (select PART_NAME from finished_goods where FG_ID in (fgid)) as 'Product',  (rdate) as 'Start Date', SUM(qtyout) as 'Output Qty', (qtyout/(stoptime-starttime)) as 'Speed', (stoptime-starttime) as 'Hrs', (qtyin-qtyout) as 'Balence' from dailyreport where fgid=4 group by processid
                   
                    jTable1.setModel(buildTableModel(result));
                    
                }else if(reportType.equalsIgnoreCase ( "Weekly") ){
                    
                }else if(reportType.equalsIgnoreCase ( "Monthly") ){
                    
                }else if(reportType.equalsIgnoreCase ( "Quarterly") ){
                    
                }
            
                jLabel3.setText ( reportLabel1  );

            
            
        } catch (SQLException e) {

        }
    }
    
    public  DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {
 
        int columnCount = 0;
        
            if(rs!=null){
                ResultSetMetaData metaData = rs.getMetaData();

                // names of columns
                
                columnCount = metaData.getColumnCount();
                if(columnNames.size ()<columnCount)
                {
                    for (int column = 1; column <= columnCount; column++) {
                        columnNames.add(metaData.getColumnName(column));
                    }
                }

                int production = 100;
                String date = "";
                // data of the table
                while (rs.next()) {

                    String[] ab = new String[2];
                    
                    Vector<Object> vector = new Vector<Object>();
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        vector.add(rs.getObject(columnIndex));
                        
                        if(columnIndex==3 ){
                            ab[0] =  rs.getString(columnIndex) ;
                        }else if( columnIndex==4){
                           ab[1] = rs.getString(columnIndex) ;
                          
                        }
                      }
                    
                    data.add(vector);
                    abc.add(ab);
                }
                
                generateBarChart ();
            }else{
            
                
    /*            
                if(columnCount==0l){
                    columnCount = 6;
                }
                              
                if(columnNames==null){
                    columnNames = new Vector<String>();
                }else{
                    columnNames.add("Process");
                    columnNames.add("Product");
                    columnNames.add("Start Date");
                    columnNames.add("Output Qty");
                    columnNames.add("Rejected");
                    columnNames.add("Balence");
                }

                if(data==null){
                    data = new Vector<Vector<Object>>();
                }else{
                    for(int i=0; i<columnCount; i++){
                        Vector<Object> vector = new Vector<Object>();
                        
                        Object abc = new Object();
                        
                        vector.add( (Object)processes.get ( resIndex ).PRC_NAME  );
                        vector.add( (Object)products.get( jComboBox1.getSelectedIndex () ).FG_NAME   );
                        vector.add( (Object)fromDate  );
                        
                        
                        for (int columnIndex = 4; columnIndex <= columnCount; columnIndex++) {
                            vector.add((Object)"--");
                        }
                        data.add(vector);
                    }
                }
               */ 
            }
        

        return new DefaultTableModel(data, columnNames);

        /* report query                 */ 
        // date wise 
        // select distinct(select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , rdate, qtyin, qtyout, rejection, (qtyin-qtyout) as 'Production for day' from dailyreport where rdate = '2-06-2018'
        
        // date wise for combined processes
        //select rdate, sum(qtyin), sum(qtyout), sum(rejection), sum(qtyin-qtyout) as 'Balence Qty' from dailyreport where rdate = '2-06-2018' order by processid 

        
        // date wise, selected process
        //select distinct(select PROCESS_NAME from PROCESS_MASTER where PROCESS_ID in (processid)) as 'Process' , rdate, qtyin, qtyout, rejection, (qtyin-qtyout) as 'Balence Qty' from dailyreport where processid = 1 order by processid 
        
        // date wise, selected process
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        dateChooserCombo3 = new datechooser.beans.DateChooserCombo();
        dateChooserCombo4 = new datechooser.beans.DateChooserCombo();
        jComboBox5 = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(0, 153, 255));
        setForeground(new java.awt.Color(51, 153, 255));
        setAutoscrolls(true);
        setMinimumSize(new java.awt.Dimension(1120, 500));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("From Date");
        add(jLabel1);
        jLabel1.setBounds(40, 30, 80, 16);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Product");
        add(jLabel2);
        jLabel2.setBounds(500, 30, 70, 16);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("Employee");
        add(jLabel8);
        jLabel8.setBounds(880, 30, 80, 16);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jComboBox1);
        jComboBox1.setBounds(500, 50, 110, 30);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox2.setEnabled(false);
        add(jComboBox2);
        jComboBox2.setBounds(760, 50, 110, 30);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jComboBox3);
        jComboBox3.setBounds(880, 50, 110, 30);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Generate Report");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(770, 90, 130, 32);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Close");
        add(jButton2);
        jButton2.setBounds(910, 90, 77, 32);

        jTable1.setBackground(new java.awt.Color(64, 106, 150));
        jTable1.setForeground(new java.awt.Color(255, 255, 255));
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
        jScrollPane1.setBounds(60, 480, 0, 0);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("Machine");
        add(jLabel7);
        jLabel7.setBounds(760, 30, 70, 16);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("To Date");
        add(jLabel11);
        jLabel11.setBounds(40, 80, 80, 16);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Process Wise / ProductWise Report for ");
        add(jLabel3);
        jLabel3.setBounds(40, 150, 340, 16);

        jComboBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox4.setEnabled(false);
        add(jComboBox4);
        jComboBox4.setBounds(630, 50, 110, 30);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("Process");
        add(jLabel12);
        jLabel12.setBounds(630, 30, 70, 16);
        add(dateChooserCombo3);
        dateChooserCombo3.setBounds(40, 50, 155, 30);

        try {
            dateChooserCombo4.setDefaultPeriods(new datechooser.model.multiple.PeriodSet(new datechooser.model.multiple.Period(new java.util.GregorianCalendar(2018, 5, 26),
                new java.util.GregorianCalendar(2018, 5, 26))));
    } catch (datechooser.model.exeptions.IncompatibleDataExeption e1) {
        e1.printStackTrace();
    }
    add(dateChooserCombo4);
    dateChooserCombo4.setBounds(40, 100, 155, 30);

    jComboBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
    jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Daily", "Weekly", "Monthly", "Quarterly", "Yearly" }));
    add(jComboBox5);
    jComboBox5.setBounds(320, 50, 150, 30);
    }// </editor-fold>//GEN-END:initComponents

    
    public String formatFromDate(){
        
         String[] dateIP = dateChooserCombo3.getText ().split ( " ");
         
           String fromDate = null ;
           
            if( dateIP[1].length ()==3 ){
                fromDate = dateIP[1].substring ( 0, 2)+"-";
            }else if( dateIP[1].length ()==2 ){
                fromDate = "0"+dateIP[1].substring ( 0, 1)+"-";
            }
          
         
          String mon = dateIP[0];
          
          switch(mon){
              
              case "Jan": fromDate = fromDate + "01-";  break;
              case "Feb": fromDate = fromDate + "02-";  break;
              case "Mar": fromDate = fromDate + "03-";  break;
              case "Apr": fromDate = fromDate + "04-";   break;
              case "May": fromDate = fromDate + "05-";  break;
              case "Jun": fromDate = fromDate + "06-";   break;
              case "Jul;": fromDate = fromDate + "07-";   break;
              case "Aug": fromDate = fromDate + "08-";   break;
              case "Sep": fromDate = fromDate + "09-";   break;
              case "Oct": fromDate = fromDate + "10-";   break;
              case "Nov": fromDate = fromDate + "11-";  break;
              case "Dec": fromDate = fromDate + "12-";  break;
                                
          }
          
          fromDate = fromDate + dateIP[2];
          
          return fromDate ;
    }
    
    public String formatToDate(){
        
        String[] dateIP2 = dateChooserCombo4.getText ().split ( " ");
          String toDate = null ;
           
            if( dateIP2[1].length ()==3 ){
                toDate = dateIP2[1].substring ( 0, 2)+"-";
            }else if( dateIP2[1].length ()==2 ){
                toDate = "0"+dateIP2[1].substring ( 0, 1)+"-";
            }
          String mon2 = dateIP2[0];
          
          switch(mon2){
              
              case "Jan": toDate = toDate + "01-"; break;
              case "Feb": toDate = toDate + "02-"; break;
              case "Mar": toDate = toDate + "03-"; break;
              case "Apr": toDate = toDate + "04-"; break;
              case "May": toDate = toDate + "05-"; break;
              case "Jun": toDate = toDate + "06-"; break;
              case "Jul;": toDate = toDate + "07-"; break;
              case "Aug": toDate = toDate + "08-"; break;
              case "Sep": toDate = toDate + "09-"; break;
              case "Oct": toDate = toDate + "10-"; break;
              case "Nov": toDate = toDate + "11-"; break;
              case "Dec": toDate = toDate + "12-"; break;
                                
          }
          
          toDate = toDate + dateIP2[2];
          
          return toDate ;
    }
    
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
       
        
        
       //   System.out.println(processes.get ( jComboBox2.getSelectedIndex () ).PRC_ID ); 
          
       
        int process, product, machine, employee;
       
        product = products.get ( jComboBox1.getSelectedIndex () ).FG_ID ; 
        machine = machines.get ( jComboBox2.getSelectedIndex () ).MC_ID  ; 
        process = processes.get ( jComboBox4.getSelectedIndex () ).PRC_ID ;
        employee = employees.get ( jComboBox3.getSelectedIndex () ).EMP_ID ;
          
         
        //fromDate = formatFromDate ();
          
        //toDate = formatToDate ();
 
 
        String reportType = jComboBox5.getSelectedItem ().toString();
        //System.out.println( reportType ); 
        
         
                                
        loadEntries( reportType , product  );
                           

    }//GEN-LAST:event_jButton1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo3;
    private datechooser.beans.DateChooserCombo dateChooserCombo4;
    private datechooser.beans.DateChooserDialog dateChooserDialog1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private static javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

