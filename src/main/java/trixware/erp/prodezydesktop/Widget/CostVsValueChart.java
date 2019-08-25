/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Widget;

import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.examples.HomeScreen;
import static trixware.erp.prodezydesktop.examples.HomeScreen.dataSet;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.web_services.WebAPITester;
import static trixware.erp.prodezydesktop.web_services.WebAPITester.header;

/**
 *
 * @author dell
 */
public class CostVsValueChart extends javax.swing.JPanel {

    Vector<String> columnNames = null;
    Vector<String> columnNames2 = null;
    Vector<Vector<Object>> data1 = null;

    private  String firstChartParameter = "";
    private  String secondChartParameter = "";
    private  String thirdChartParameter = "";
    private  String ReportType = "";
    private JFreeChart chart = null;
    private ChartPanel cp = null;

    private Calendar c1 = Calendar.getInstance();
    private Calendar c2 = Calendar.getInstance();
    private Calendar c3 = Calendar.getInstance();

    private String currentYear, nextYear, selectedMonth;

    private ArrayList<String> months = new ArrayList<String>();
    private ArrayList<String[]> singleMonth = new ArrayList<String[]>();
    private ArrayList<String[]> reportData1 = null;

    private SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy");
    private SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-mm-dd");

    private JScrollPane scroll = null;
    private String reportMasterType = "Cost VS Value";

    double value=0;
    
    /**
     * Creates new form ProductionChart
     */
    public CostVsValueChart() {
        initComponents();

        //******************report for month edited by mayur****************************
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM");
        //Date date = new Date();
        //Calender ca1=new Calender();
        //System.out.println(dateFormat.format(date));
        //String todate=dateFormat.format(date);
        //String fromdate=dateFormat1.format(date);
        //fromdate=fromdate+"-01";
        //System.out.println("from date: "+fromdate+"  to date: "+todate);
        //String chartType=chartChoice.getSelectedItem().toString().replace(" ", "");
        String reportType = "Daily";

        showSingleMonths();

        data1 = new Vector<Vector<Object>>();
        reportData1 = new ArrayList<String[]>();

        for (int i = 0; i < singleMonth.size(); i++) {

            String cvvcaddEmpAPICall = "pmphreport?to_date=" + singleMonth.get(i)[0] + "&from_date=" + singleMonth.get(i)[0] + "&freq=";
            //String cvvcaddEmpAPICall = "production?to_date=2019-04-26&from_date=2019-04-26&freq=";
            //String cvvcrpt = WebAPITester.prepareWebCall(cvvcaddEmpAPICall, StaticValues.getHeader(), "");
            String cvvcrpt = new WebAPITester().executeWebCall(cvvcaddEmpAPICall);
            //System.out.println("Copq result  "+cvvcrpt);
            //System.out.println("" + addEmpAPICall);
            if (cvvcrpt != null && !cvvcrpt.contains("records\":null")) {
                cvvcbuildTableModelfromJSON(cvvcrpt, reportType, singleMonth.get(i)[1]);
            }

            firstChartParameter = reportType + " " + reportMasterType;
            secondChartParameter = reportType;
            thirdChartParameter = reportMasterType;
            chart = generatelineChartFromAPIData1(" ", "", reportMasterType, "");
        }

        ///panel = new JPanel ();
        //panel.setBackground ( Color.DARK_GRAY );
        //panel.setBounds ( 0,0 , 1000 , 300 );
        // panel.revalidate ();
        // panel.repaint ();
//        scroll = new JScrollPane ( jPanel ,
//                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
//                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        //scroll.setBounds ( 270 , 290 , 1000 , 280 );
        // scroll.setOpaque ( false );
        // scroll.setBackground ( Color.BLACK );
        cp = new ChartPanel(chart);
        cp.setPreferredSize(new java.awt.Dimension(350, 250));
        cp.setBounds(0, 0, 350, 250);
        cp.setBackground(Color.WHITE);
        //panel.add ( cp );
        add(cp);
        //scroll.revalidate ();
        //scroll.repaint ();
        //add ( scroll );

        //******************end report for month edited by mayur****************************
    }

    private  JFreeChart generatelineChartFromAPIData1(String reportTitle, String xAxis, String yAxis, String chartType) {

        dataSet = new DefaultCategoryDataset();
        int count = 0;
        String label = "";

        float addQumerative = 0;
            float addQumerative1 = 0;
        for (int i = 0; i < reportData1.size(); i++) {
           
            try {

                for (int j = i; j <= i; j++) {
                    addQumerative = addQumerative +Integer.parseInt(reportData1.get(j)[1]);
                    //addQumerative = Integer.parseInt(reportData1.get(j)[1]);
                }

                //System.out.println("Integer data "+addQumerative );
                dataSet.setValue(addQumerative, yAxis, reportData1.get(i)[0]);
            } catch (Exception e) {
                try {

                    for (int j = i; j <= i; j++) {
                        addQumerative = addQumerative + Float.parseFloat(df.format(Float.parseFloat(reportData1.get(j)[1])));
                        //addQumerative = Float.parseFloat(df.format(Float.parseFloat(reportData1.get(j)[1])));
                    }
                    // System.out.println("data "+addQumerative );
                    dataSet.setValue(addQumerative, yAxis, reportData1.get(i)[0]);
                } catch (Exception e2) {
                    //StaticValues.writer.writeExcel ( ReportsController_V09.class.getSimpleName () , ReportsController_V09.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }
                //StaticValues.writer.writeExcel ( ReportsController_V09.class.getSimpleName () , ReportsController_V09.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }

        chart = ChartFactory.createLineChart(reportTitle, xAxis, yAxis, dataSet, PlotOrientation.VERTICAL, true, true, false);

        cp = new ChartPanel(chart);
        cp.setPreferredSize(new java.awt.Dimension(350, 250));
        cp.setBounds(270, 290, 350, 250);
        //cp.setBounds(270, 290, 500, 300);
        cp.revalidate();
        cp.repaint();

        //chart.getCategoryPlot().getRangeAxis().setLowerBound(9.0);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinePaint(Color.WHITE);
        plot.setRangeGridlinesVisible(false);
        plot.setOutlinePaint(null);
        //plot.getDomainAxis().setLabelFont(new Font ( "Times New Roman" , Font.PLAIN , 10 ));
        //plot.getDomainAxis().setTickLabelFont ( new Font ( "Times New Roman" , Font.PLAIN , 10 ));
        plot.getRangeAxis().setLabelFont(new Font ( "Times New Roman" , Font.PLAIN , 10 ));
        plot.getRangeAxis().setTickLabelFont ( new Font ( "Times New Roman" , Font.PLAIN , 10 ));
        
        LegendTitle legend = chart.getLegend();
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setVisible(false);
        
        if (reportData1.size() > 2) {
            CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        }

        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//        BarRenderer renderer = ( BarRenderer ) plot.getRenderer ();
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        //renderer.setSeriesPaint(1, Color.CYAN);
        renderer.setSeriesPaint(1, Color.MAGENTA);
        //  renderer.setSeriesLinesVisible(0, true);
        //renderer.setSeriesItemLabelFont(1, new Font("Times New Roman", Font.PLAIN, 2));
        renderer.setSeriesItemLabelPaint(0, Color.BLACK);
        renderer.setSeriesItemLabelFont(0, new Font("Times New Roman", Font.PLAIN, 1));
        //renderer.setLegendTextFont(0, new Font("Times New Roman", Font.PLAIN, 2));
        renderer.setSeriesItemLabelPaint(1, Color.BLACK);
        renderer.setLegendTextPaint(0, null);
        // font3 = new Font("Times New Roman", Font.PLAIN, 10);
         //plot.getDomainAxis().setLabelFont(font3);
        //plot.getRangeAxis().setLabelFont(font3);
      
        CategoryAxis axis = plot.getDomainAxis();
        axis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 0));
        ValueAxis yAxis1 = plot.getRangeAxis(); 
        
        value=(double)(addQumerative);
        
            if(value >= 0 && value <= 100)
            {
                 yAxis1.setRange(0, (value+50));
            }
            else if(value >= 100 && value <= 500)
            {
                yAxis1.setRange(0, (value+100));
            }
            else if(value >= 500 && value <= 1000 )
            {
                yAxis1.setRange(0, (value+100));
            }
            else if(value >= 1000 && value <= 100000)
            {
                yAxis1.setRange(0, value+1000);
            }
            else if(value >= 100000 && value <= 1000000)
            {
                yAxis1.setRange(0, value+10000);
            }
            else if(value >= 0 && value <= 50)
            {
               yAxis1.setRange(0, value+10); 
            }
            else{
                yAxis1.setRange(0, value); 
            }
      //  plot.get
//        panel.add(cp);
//        panel.revalidate();
//        panel.repaint();
        add(cp);
        revalidate();
        repaint();

        //   HomeScreen.home.pack ();
        HomeScreen.home.revalidate();
        HomeScreen.home.repaint();
        return chart;

    }

    private  String showSingleMonths() {

        singleMonth = new ArrayList<String[]>();

        currentYear = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
        selectedMonth = new SimpleDateFormat("MMMM").format(Calendar.getInstance().getTime());

        //formatFromDate (    );
        Calendar c = Calendar.getInstance();

        int lastDateOfMonth = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (selectedMonth.equals("January") || selectedMonth.equals("February") || selectedMonth.equals("March")) {
            nextYear = currentYear;
            currentYear = String.valueOf(Integer.parseInt(currentYear) - 1);
        }
        switch (selectedMonth) {
            case "April":
                c.set(Calendar.MONTH, 3);
                break;
            case "May":
                c.set(Calendar.MONTH, 4);
                break;
            case "June":
                c.set(Calendar.MONTH, 5);
                break;
            case "July":
                c.set(Calendar.MONTH, 6);
                break;
            case "August":
                c.set(Calendar.MONTH, 7);
                break;
            case "September":
                c.set(Calendar.MONTH, 8);
                break;
            case "October":
                c.set(Calendar.MONTH, 9);
                break;
            case "November":
                c.set(Calendar.MONTH, 10);
                break;
            case "December":
                c.set(Calendar.MONTH, 11);
                break;
            case "January":
                c.set(Calendar.MONTH, 0);
                break;
            case "February":
                c.set(Calendar.MONTH, 1);
                break;
            case "March":
                c.set(Calendar.MONTH, 2);
                break;
        }

        // System.out.println( "From month is  "+ c1.getActualMaximum ( Calendar.DAY_OF_MONTH));
        // System.out.println( "From month is  "+sdf1.format(c1.getTime()));
        for (int i = 1; i <= lastDateOfMonth; i++) {

            c.set(Calendar.DATE, i);
            c.set(Calendar.YEAR, Integer.parseInt(currentYear));
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            //singleMonth.add ( new String[]{ sdf5.format ( c.getTime () ) + " 00:00:00" ,  new SimpleDateFormat("MMM-dd").format( c.getTime () ) });
            singleMonth.add(new String[]{new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()), new SimpleDateFormat("dd").format(c.getTime())});
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
        return sdf4.format(c1.getTime());
    }

    private  JSONObject jobj = null;
    private  int counter = 0;
    private  JSONArray records = null;
    private  DecimalFormat df = new DecimalFormat("#.##");
    // public String reportMasterType = "Production Quantity";

    private  void cvvcbuildTableModelfromJSON(String cvvcemployeeJSONList1, String reportType, String label) {

        HashMap<String, Object> map1 = new HashMap<String, Object>();
        JSONObject jObject1 = new JSONObject(cvvcemployeeJSONList1);
        Iterator<?> keys = jObject1.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            Object value = jObject1.get(key);
            map1.put(key, value);
        }

        JSONObject st = null;

        try {

            st = (JSONObject) map1.get("data");
            records = st.getJSONArray("records");

            columnNames = new Vector<String>();
            columnNames2 = new Vector<String>();

           if ( reportMasterType.equals ( "Cost VS Value" ) ) {
                columnNames.add ( "showrdate" );             
                columnNames2.add ( "Date" );
                columnNames.add ( "Cost" );               
                columnNames2.add ( "Incurred Cost" );
                columnNames.add ( "Value" );             
                columnNames2.add ( "Produced Value" );
            }
            for (counter = 0; counter < records.length(); counter++) {
                Vector<Object> vector = new Vector<Object>();

                String[] ab = new String[columnNames.size()];

//                        if ( reportMasterType.equals ( "Cost VS Value" ) || reportMasterType.equals ( "Total Rejection" ) ) {
//                            ab = new String[ 3 ];
//                        }
                for (int columnIndex = 0; columnIndex < columnNames.size(); columnIndex++) {
                    jobj = records.getJSONObject(counter);


                    if ( reportMasterType.equals ( "Cost VS Value" ) ) {

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
                                    vector.add ( jobj.get ( columnNames.get ( columnIndex ) ) );
                                }

                            } else {
                                
                                try {
                                    vector.add ( jobj.get ( columnNames.get ( columnIndex ) ) );
                                } catch ( NullPointerException | NumberFormatException e ) {
                                    vector.add ( "--" );
                                }
                            }

                        } catch ( NullPointerException | NumberFormatException r ) {
                            //  ab[ columnIndex ] = "-- ";
                        }

                    }

                    String s1;
                    Object abc;
                    
                    if ( reportMasterType.equals ( "Cost VS Value" ) ) {

                        if ( columnIndex > 0 ) {
                            abc = jobj.get ( columnNames.get ( columnIndex ) ).toString();                           
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
                            } else {
                                ab[ 0 ] = jobj.get ( columnNames.get ( 0 ) ).toString ();
                            }
                        }

                    }
                }

                if (vector.get(0) != null && vector.get(1) != null) {
                    data1.add(vector);
                }
//                 double chk=Double.parseDouble(ab[1].toString());
//                if(value < chk)
//                {
//                    value = chk;
//                }

                reportData1.add(ab);

            }

        } catch (JSONException | ClassCastException e) {

            Vector<Object> emptyVal = new Vector<Object>();

        }
    }
    private  String REQUEST_METHOD = "POST";
    private  int READ_TIMEOUT = 60000;
    private  int CONNECTION_TIMEOUT = 60000;
        
    private String executeWebCall (String apiNameANDparameters ) {
        
       String url = "http://"+StaticValues.getApiURL ()+"/projects/erp/public/"+apiNameANDparameters;
//         System.err.println(apiNameANDparameters);
//         System.err.println(url);
         URL myUrl = null;
         HttpURLConnection connection = null;
        String result,inputLine; 
        try {
            myUrl = new URL ( url );
        } catch ( MalformedURLException e1 ) {
            System.out.println ( ""+e1.getMessage () );
        }

        try {
            connection = ( HttpURLConnection ) myUrl.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout ( READ_TIMEOUT );
            connection.setConnectTimeout ( CONNECTION_TIMEOUT );
            connection.setDoInput ( true );
            connection.setDoOutput ( true );
            connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            
            if( !apiNameANDparameters.contains ("auth/login" )){
                 connection.addRequestProperty ( "Authorization" , "encoded " + header ); 
            }
            connection.connect ();
            
            DataOutputStream request = new DataOutputStream ( connection.getOutputStream () );
          
            
            request.flush ();
            request.close ();
            connection.connect ();

            InputStreamReader streamReader = new InputStreamReader ( connection.getInputStream () );

            BufferedReader reader = new BufferedReader ( streamReader );
            StringBuilder stringBuilder = new StringBuilder ();

            while ( ( inputLine = reader.readLine () ) != null ) {
                stringBuilder.append ( inputLine );
            }

            reader.close ();
            streamReader.close ();

            result = stringBuilder.toString ();
            return result;

        } catch ( IOException e2 ) {
            return e2.getMessage ();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(350, 250));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 404, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 236, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
