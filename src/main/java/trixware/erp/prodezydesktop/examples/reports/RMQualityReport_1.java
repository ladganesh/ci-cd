/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples.reports;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.Batch;
import trixware.erp.prodezydesktop.Model.QualityPartDR;
import trixware.erp.prodezydesktop.Model.Rejection_Reasons;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author
 */
//Created By Harshali  RM Quality Report
public class RMQualityReport_1 extends javax.swing.JPanel {

    Float tot = Float.valueOf(0), mean, tot_square = Float.valueOf(0), variance, tot1, mean1, tot_square1, variance1;
    double LCL = 0, UCL = 0,dev =0,Cp=0,Cpk=0,Cpk1=0;
    Float min, max, min1, max1,LCl1,UCl1;

    ArrayList<Batch> batch = new ArrayList<>();
    ArrayList<Rejection_Reasons> list = null;
    ArrayList<QualityPartDR> param = new ArrayList<>();
    String addEmpAPICall;
    JFreeChart chart = null,chart1= null,chart2 = null,chart3 = null;
    //static ChartPanel cp = null,cp1 = null,cp2 = null,cp3= null;
    JPanel panel = null;
     JScrollPane scroll = null;
   // public static DefaultCategoryDataset dataSet = null;
   // ArrayList<Double> value1 = new ArrayList<>();

    /**
     * Creates new form RMQualityEntryForm
     */
    public RMQualityReport_1() 
    {
        initComponents();
        
        jLabel_mean_panel = new javax.swing.JLabel();
        jLabel_cp_panel =  new javax.swing.JLabel();
        jLabel_cpk_panel = new javax.swing.JLabel();
        jLabel_std_panel = new javax.swing.JLabel();
        
        this.setBackground(Color.WHITE);
        repaint();
        revalidate();
        loadContent();
        if (batch != null && !batch.isEmpty()) 
        {
            load_paramaters(batch.get(0).ref_rm_id);

        }
          
        jLabel_Cp.setVisible(false);
        jLabel_Cpk.setVisible(false);
        jLabel_mean.setVisible(false);
        jLabel_std_dev.setVisible(false);
        jLabel1.setVisible(false);
        jLabel5.setVisible(false);
        jLabel7.setVisible(false);  
        jLabel4.setVisible(false);
        
         
       // panel = new JPanel(new GridLayout(2 ,2 ,0 ,0));
        panel=new JPanel();
        panel.setLayout(new GridLayout(2 ,2 ,0 ,20));
        panel.setBackground(Color.WHITE);
        panel.setBounds(10, 80, 900, 500);
        panel.revalidate();
        panel.repaint();

//        cp = new ChartPanel(chart);
//        cp.setPreferredSize(new java.awt.Dimension(0, 380));
//        cp.setBounds(0, 0, 500, 270);
//        // cp.setBounds(0, 0, 700, 380);
//        cp.setBackground(Color.WHITE);
//        panel.add(cp);
        add(panel);

    }

    public void loadContent() {

        addEmpAPICall = "batches";
        String result = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        System.out.println(result);
        if (!result.contains("not found") && result != null) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            JSONObject jObject = new JSONObject(result);
            Iterator<?> keys = jObject.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                Object value = jObject.get(key);
                map.put(key, value);
            }

            JSONObject st = (JSONObject) map.get("data");
            JSONArray records = st.getJSONArray("records");

            JSONObject emp = null;
            // batch = new ArrayList<Batch>();
            for (int i = 0; i < records.length(); i++) 
            {
                emp = records.getJSONObject(i);
                
                int a = emp.getInt ( "ref_rm_id");
                
                if(a != 0)
                    
                {
                Batch rm = new Batch();
                rm.batchId = emp.getInt("BatchId");
                rm.batchName = emp.getString("BatchName");
                rm.ref_rm_id = emp.getInt("ref_rm_id");

                batch.add(rm);
                jComboBox_batch.addItem(emp.getString("BatchName"));
                }
            }
            jComboBox_batch.addActionListener(RMComboAction);
        }

    }
    ActionListener RMComboAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (batch.size() != 0) {
//                
                try {
//                    if (cp != null) {
//                        panel.remove(cp);
//                        panel.repaint();
//                        panel.revalidate();
//
//                    }
//                    if (dataSet != null) {
//                        dataSet.clear();
//                    }
                    jLabel_Cp.setText("");
                    jLabel_Cpk.setText("");
                    jLabel_mean.setText("");
                    jLabel_std_dev.setText("");
                } catch (Exception e1) {
                    System.out.println(e1);
                    StaticValues.writer.writeExcel(RMQualityReport.class.getSimpleName(), RMQualityReport.class.getSimpleName(), e.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e1.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
                }
                load_paramaters(batch.get(jComboBox_batch.getSelectedIndex()).ref_rm_id);
            }
        }
    };

    ActionListener paramaters = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (param.size() != 0) {
                try {
//                    if (cp != null) {
//                        panel.remove(cp);
//                        panel.repaint();
//                        panel.revalidate();
//
//                    }
//                    if (dataSet != null) {
//                        dataSet.clear();
//                    }
                    jLabel_Cp.setText("");
                    jLabel_Cpk.setText("");
                    jLabel_mean.setText("");
                    jLabel_std_dev.setText("");
                } catch (Exception e1) {
                    System.out.println(e1);
                    StaticValues.writer.writeExcel(RMQualityReport.class.getSimpleName(), RMQualityReport.class.getSimpleName(), e.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e1.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
                }
            }

        }
    };

    public void load_paramaters(int rm_id) 
    {
        if (param.size() != 0) {
            param.clear();
        }
//        if (jComboBoxParamter.getItemCount() != 0) {
//            jComboBoxParamter.removeActionListener(paramaters);
//            jComboBoxParamter.removeAllItems();
//        }
        addEmpAPICall = "rmqty_master?rm_id=" + rm_id;
        String result = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        System.out.println(result);

        if (result != null && !result.contains("not found")) {

            HashMap<String, Object> map = new HashMap<String, Object>();
            JSONObject jObject = new JSONObject(result);
            Iterator<?> keys = jObject.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                Object value = jObject.get(key);
                map.put(key, value);
            }

            //JSONObject jsobje = new JSONObject(result);
            JSONObject st = (JSONObject) map.get("data");
            JSONArray records = st.getJSONArray("records");

            JSONObject emp = null;

          ////  param = new ArrayList<>();
            //  JSONObject emp = null;
            for (int i = 0; i < records.length(); i++) {
                emp = records.getJSONObject(i);
                QualityPartDR part = new QualityPartDR();
                part.setPartId(Integer.parseInt(emp.get("rm_param_name").toString()));
                part.setPartName(emp.get("param_name").toString());
                param.add(part);
               // jComboBoxParamter.addItem(emp.get("param_name").toString());
            }

            //jComboBoxParamter.addActionListener(paramaters);

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

        jComboBox_batch = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        btn_add = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel_Cp = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel_Cpk = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel_mean = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel_std_dev = new javax.swing.JLabel();
        dateChooserCombo = new datechooser.beans.DateChooserCombo();
        jLabel3 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(900, 300));

        jComboBox_batch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox_batchMouseClicked(evt);
            }
        });
        jComboBox_batch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_batchActionPerformed(evt);
            }
        });

        jLabel2.setText("Batch No.");

        btn_add.setText("Submit");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setText("Cp  :");

        jLabel_Cp.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_Cp.setText("jLabel4");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel4.setText("Cpk  :");

        jLabel_Cpk.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_Cpk.setText("jLabel5");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel5.setText("Mean  :");

        jLabel_mean.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_mean.setText("jLabel7");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel7.setText("Std Deviation:");

        jLabel_std_dev.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_std_dev.setText("jLabel9");

        dateChooserCombo.setCalendarPreferredSize(new java.awt.Dimension(320, 220));

        jLabel3.setText("Date");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel_mean, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dateChooserCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel_std_dev, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBox_batch, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_Cp, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_Cpk, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateChooserCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox_batch, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_Cpk)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Cp)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_std_dev)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_mean)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(212, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // TODO add your handling code here:
       if(panel != null)
       {
            panel.removeAll();
            repaint();
            revalidate();
       }
        
        if(param.size() != 0)
        {
        for(int l=0;l<param.size();l++)
        {    
          chart = generateLineChart("", "", "", "",l);
          repaint();
          revalidate();
        }
        }
        else{
              JOptionPane.showMessageDialog(null,  " no paramater found");
        }
    }//GEN-LAST:event_btn_addActionPerformed

    private void jComboBox_batchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox_batchMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_batchMouseClicked

    private void jComboBox_batchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_batchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_batchActionPerformed

    public JFreeChart generateLineChart(String reportTitle, String xAxis, String yAxis, String chartType,int y) {
        
        
        tot = Float.valueOf(0);
        mean = Float.valueOf(0);
        tot_square = Float.valueOf(0);
        variance = Float.valueOf(0);
        tot1 = Float.valueOf(0);
        mean1 = Float.valueOf(0);
        tot_square1 = Float.valueOf(0);
        variance1 = Float.valueOf(0);
        LCL = 0;
        UCL = 0;
        min = Float.valueOf(0);
        max = Float.valueOf(0);
        min1 = Float.valueOf(0);
        max1 = Float.valueOf(0);
        dev = 0;
        Cp = 0 ;
        Cpk = 0;
        Cpk = 0 ;
        double cpk = 0;
          ArrayList<Double> value1 = new ArrayList<>();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String Date1 = sdf2.format(dateChooserCombo.getSelectedDate().getTime());

        addEmpAPICall = "rmqty_master?rm_id=" + batch.get(jComboBox_batch.getSelectedIndex()).ref_rm_id
                + "&param_id=" + param.get(y).partId;

        String result = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        System.out.println(result);

        DecimalFormat df = new DecimalFormat("#,###,##0.0000");
        DecimalFormat df1 = new DecimalFormat("#,###,##0.0000000");
        DecimalFormat df_ = new DecimalFormat("#,###,##0.000");

        if (result != null && !result.contains("not found")) 
        {

            HashMap<String, Object> map = new HashMap<String, Object>();
            JSONObject jObject = new JSONObject(result);
            Iterator<?> keys = jObject.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                Object value = jObject.get(key);
                map.put(key, value);
            }

            //JSONObject jsobje = new JSONObject(result);
            JSONObject st = (JSONObject) map.get("data");
            JSONArray records = st.getJSONArray("records");

            JSONObject emp = null;

            // ArrayList<String> value=new ArrayList<>();
            for (int i = 0; i < records.length(); i++) {
                emp = records.getJSONObject(i);
                min1 = Float.parseFloat("" + emp.getDouble("LLL"));
                max1 = Float.parseFloat("" + emp.getDouble("ULL"));
                min = Float.parseFloat(df.format(min1));
                max = Float.parseFloat(df.format(max1));

            }
        }

        addEmpAPICall = "rmqty_data?batch_id=" + batch.get(jComboBox_batch.getSelectedIndex()).batchId
                + "&param_id=" + param.get(y).partId + "&date=" + Date1;

        result = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        System.out.println(result);

        if (result != null && !result.contains("not found")) 
        {

            HashMap<String, Object> map = new HashMap<String, Object>();
            JSONObject jObject = new JSONObject(result);
            Iterator<?> keys = jObject.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                Object value = jObject.get(key);
                map.put(key, value);
            }

            JSONObject st = (JSONObject) map.get("data");
            JSONArray records = st.getJSONArray("records");

            JSONObject emp = null;

            ArrayList<String> value = new ArrayList<>();
            for (int i = 0; i < records.length(); i++) {
                emp = records.getJSONObject(i);
                String a= emp.getString("ref_rmqp_value");
                if(a.contains("null") || a == null  || a.isEmpty())
               {
                   a ="0";
               }
                value.add(a);
                value1.add(Double.parseDouble(a));

            }

            if (value.size() != 0) 
            {
               
                try {
                    ArrayList<Double> diff_ = new ArrayList<>();
                    ArrayList<Double> diff_Square = new ArrayList<>();

                    for (int i = 0; i < value.size(); i++) {
                        tot = tot + Float.parseFloat(value.get(i));

                    }
                    int size = value.size();

                    mean1 = tot / size;
                    String get_Means = df.format(mean1);
                    mean = Float.parseFloat(get_Means);
                    System.out.println("Mean    " + mean);
                    jLabel_mean_panel.setText("Mean  : " + df_.format(mean));

                    System.out.println("Diffrence");

                    for (int i = 0; i < value.size(); i++) {
                        Float diff, diff1;
                        diff = Float.parseFloat(value.get(i)) - mean;

                        System.out.println("" + df.format(diff));
                        Double data = Double.parseDouble(df.format(diff));
                        diff_.add(data);
                    }
                    System.out.println("Square");
                    for (int j = 0; j < diff_.size(); j++) {
                        Float square;
                        square = Float.parseFloat("" + diff_.get(j)) * Float.parseFloat("" + diff_.get(j));

                        System.out.println("" + df1.format(square));
                        Double data1 = Double.parseDouble(df1.format(square));
                        diff_Square.add(data1);
                    }
                    for (int p = 0; p < diff_Square.size(); p++) {
                        tot_square = tot_square + Float.parseFloat("" + diff_Square.get(p));
                        String get = df1.format(tot_square);
                        tot_square = Float.parseFloat(get);
                    }
                    int size1 = diff_Square.size();
                    String get_tot = df1.format(tot_square);
                    variance = Float.parseFloat(get_tot) / size1;

                    Float varian = Float.parseFloat("" + variance);
                    System.out.println("Variance === " + df1.format(varian));

                    Double std_dev1 = Math.sqrt(variance);

                    Float std_dev = Float.parseFloat("" + std_dev1);
                    System.out.println("std Dev === " + df1.format(std_dev));

                    String get_Std = df1.format(std_dev);
                    UCL = mean + (3 * Double.parseDouble(get_Std));

                    System.out.println("UCL === " + df_.format(UCL));

                    LCL = mean - (3 * Double.parseDouble(get_Std));

                    System.out.println("LCL === " + df_.format(LCL));

                     dev = Double.parseDouble(get_Std);
                    String get_dev = df1.format(dev);
                     Cp = (max - min) / (6 * Double.parseDouble(get_dev));
                     Cpk = (max - mean) / (3 * Double.parseDouble(get_dev));
                     Cpk1 = (mean - min) / (3 * Double.parseDouble(get_dev));
                    System.out.println("Cpk  :  " + Cpk + "   " + Cpk1);
                    jLabel_std_panel.setText("Std Dev  :  " + df_.format(dev));
                    jLabel_cp_panel.setText("Cp  :  " + df_.format(Cp));
                    if (Cpk < Cpk1) 
                    {
                        cpk=Cpk;
                        jLabel_cpk_panel.setText("Cpk  :  " + df_.format(Cpk));
                    } else {
                        cpk=Cpk1;
                        jLabel_cpk_panel.setText("Cpk  :  " + df_.format(Cpk1));
                    }
                        //*****************************

                  //  chart = generateLineChart("", "", "", "");

                    //******************   
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        } 

        
      DefaultCategoryDataset dataSet  = new DefaultCategoryDataset();
        yAxis = "Actual";
        xAxis = "Mean";
        String LSL = "LSL", USL = "USL", LCL1 = "LCL", UCL1 = "UCL";

        for (int i = 0; i < value1.size(); i++) 
        {
            try 
            
            {
                Double data = value1.get(i);
                dataSet.setValue(data, yAxis, "" + i);
                dataSet.setValue(mean, xAxis, "" + i);
                dataSet.setValue(min, LSL, "" + i);
                dataSet.setValue(max, USL, "" + i);
                dataSet.setValue(LCL, LCL1, "" + i);
                dataSet.setValue(UCL, UCL1, "" + i);

            } catch (Exception e) {
                e.getMessage();

            }
        }
    if(value1.size() != 0)
    {    
        chart = ChartFactory.createLineChart("Mean  : " + df_.format(mean) +"  Std Dev  :  " + df_.format(dev)+"  Cp  :  " + df_.format(Cp) + "  Cpk  : "+df_.format(cpk), "", "", dataSet, PlotOrientation.VERTICAL, true, true, false);
        chart.getTitle().setFont(new Font("Tahoma", Font.BOLD, 14));
        ChartPanel  cp = new ChartPanel(chart);
        

        //cp.add(jLabel_mean_panel);
      
        //cp.add(jLabel_std_panel);

       // cp.add(jLabel_cp_panel);
      
       // cp.add(jLabel_cpk_panel);
        cp.setPreferredSize(new java.awt.Dimension(300, 150));
        cp.setBounds(0,0, 300, 150);
        cp.repaint();
        cp.revalidate();
       // LCl1 = Float.parseFloat(df_.format(LCL));
       //  UCl1 = Float.parseFloat(df_.format(UCL));

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.WHITE);
        plot.setRangeGridlinesVisible(true);
        ValueAxis range = (ValueAxis) plot.getRangeAxis();
      // range.setRange(LCl1 - 0.0080, UCl1 + 0.0080);
       range.setRange(min - 0.0050, max + 0.0050);

        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseItemLabelsVisible(false);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setSeriesPaint(1, Color.CYAN);
        renderer.setSeriesPaint(1, Color.MAGENTA);

        renderer.setSeriesItemLabelFont(1, new Font("Times New Roman", Font.PLAIN, 8));
        renderer.setSeriesItemLabelPaint(0, Color.BLACK);
        renderer.setSeriesItemLabelFont(0, new Font("Times New Roman", Font.PLAIN, 8));
        renderer.setSeriesItemLabelPaint(1, Color.BLACK);

        plot.getRenderer().setSeriesPaint(2, new Color(0x00, 0xFF, 0x00));
        plot.getRenderer().setSeriesPaint(4, new Color(0x00, 0x00, 0x00));
        plot.getRenderer().setSeriesPaint(3, new Color(0x00, 0xFF, 0x00));
        plot.getRenderer().setSeriesPaint(5, new Color(0x00, 0x00, 0x00));
        
        
        panel.add(cp);
        panel.repaint();
        panel.revalidate();
        revalidate();
        repaint();
       
    }

        return chart;
    }

    public class Allcharts {

    }

    public static javax.swing.JLabel jLabel_mean_panel;
    public static javax.swing.JLabel jLabel_std_panel;
    public static javax.swing.JLabel jLabel_cp_panel;
    public static javax.swing.JLabel jLabel_cpk_panel;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private datechooser.beans.DateChooserCombo dateChooserCombo;
    private javax.swing.JComboBox jComboBox_batch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel_Cp;
    private javax.swing.JLabel jLabel_Cpk;
    private javax.swing.JLabel jLabel_mean;
    private javax.swing.JLabel jLabel_std_dev;
    // End of variables declaration//GEN-END:variables
}
