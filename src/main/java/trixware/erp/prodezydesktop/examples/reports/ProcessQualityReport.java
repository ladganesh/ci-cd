/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples.reports;

import trixware.erp.prodezydesktop.Model.Batch;
import trixware.erp.prodezydesktop.Model.ProcessDR;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.QualityPartDR;
import trixware.erp.prodezydesktop.Model.Rejection_Reasons;
import trixware.erp.prodezydesktop.Model.SalesOrderDr;
import trixware.erp.prodezydesktop.Model.StaticValues;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JPanel;
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
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author
 */
//Created By Harshali  FG Quality Report
public class ProcessQualityReport extends javax.swing.JPanel {

    Float tot = Float.valueOf(0), mean, tot_square = Float.valueOf(0), variance, tot1, mean1, tot_square1, variance1;
    double LCL = 0, UCL = 0;
    Float min, max, min1, max1;
    ArrayList<ProductDR> product = new ArrayList<>();
    ArrayList<SalesOrderDr> order = null;
    ArrayList<Batch> batch = null;
    ArrayList<Rejection_Reasons> list = null;
    ArrayList<QualityPartDR> param = new ArrayList<>();
    String addEmpAPICall;
    JFreeChart chart = null;
    static ChartPanel cp = null;
    JPanel panel = null;
    ArrayList<ProcessDR> process = new ArrayList<>();
    // JScrollPane scroll = null;
    public static DefaultCategoryDataset dataSet = null;
    ArrayList<Double> value1 = new ArrayList<>();

    /**
     * Creates new form RMQualityEntryForm
     */
    public ProcessQualityReport() {
        initComponents();
        this.setBackground(Color.WHITE);
        repaint();
        revalidate();
        loadContent();
        if (order != null && !order.isEmpty()) {
            load_products(order.get(0).PO_ID);

            if (product != null && !product.isEmpty()) {
                load_process(product.get(0).FG_ID);

                if (param.size() != 0) {
                    load_paramaters(process.get(0).PRC_ID);
                }
            }

        }
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(-40, 180, 1000, 420);
        //add.(panel);
        panel.revalidate();
        panel.repaint();

       
        cp = new ChartPanel(chart);
        cp.setPreferredSize(new java.awt.Dimension(0, 380));
        cp.setBounds(0, 0, 700, 380);
        cp.setBackground(Color.WHITE);

        panel.add(cp);
        add(panel);
       
    }

    public void loadContent() {

        addEmpAPICall = "salesorders";
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        System.out.println(result2);

        if (result2 != null) {
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
                JSONArray records = null;
                records = st.getJSONArray("records");

                JSONObject emp = null;
                order = new ArrayList<SalesOrderDr>();
                for (int i = 0; i < records.length(); i++) {
                    emp = records.getJSONObject(i);
                    SalesOrderDr or = new SalesOrderDr();
                    or.PO_ID = emp.getInt("sales_po_id");
                    or.po_date = emp.getString("po_date");
                    or.order_no = emp.getString("order_no");

                    String[] date = emp.getString("po_date").split(" ");
                    order.add(or);
                    jComboBoxOrders.addItem(emp.getString("order_no") + " ( " + date[0] + " )");
                }
                jComboBoxOrders.addActionListener(orderComboAction);

            }

        }

    }

    public void load_process(int fg_id) {

        if (jComboBox_process.getItemCount() != 0) {
            jComboBox_process.removeActionListener(procComboAction);
            jComboBox_process.removeAllItems();
            if (!process.isEmpty() && process != null) {
                process.clear();
            }

        }
        addEmpAPICall = "processmachmaps?FG_ID=" + fg_id;
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        System.out.println(result2);
        // jComboBox_Proc.removeActionListener(FgComboAction);
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
            JSONArray records = null;
            records = st.getJSONArray("records");

            JSONObject emp = null;
            //process = new ArrayList<ProcessDR> ();
            for (int i = 0; i < records.length(); i++) {
                emp = records.getJSONObject(i);
                ProcessDR rm = new ProcessDR();
                rm.PRC_ID = emp.getInt("REF_PROCESS_ID");
                rm.PRC_NAME = emp.getString("PROCESS_NAME");

                process.add(rm);
                jComboBox_process.addItem(emp.getString("PROCESS_NAME"));
            }
            jComboBox_process.addActionListener(procComboAction);
            load_paramaters(process.get(jComboBox_process.getSelectedIndex()).PRC_ID);

        }

    }
    ActionListener orderComboAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (order.size() != 0) 
            {
                if (jComboBox_FG.getItemCount() != 0) {
                    product.clear();
                    jComboBox_FG.removeActionListener(FgComboAction);
                    jComboBox_FG.removeAllItems();
                }
                if (jComboBox_process.getItemCount() != 0) {
                    process.clear();
                    jComboBox_process.removeActionListener(procComboAction);
                    jComboBox_process.removeAllItems();
                }
                if (jComboBoxParamter.getItemCount() != 0) {
                    jComboBoxParamter.removeActionListener(paramaters);
                    param.clear();
                    
                    jComboBoxParamter.removeAllItems();
                }

                  try {
                    if (cp != null) {
                        panel.remove(cp);
                        panel.repaint();
                        panel.revalidate();
           // dataSet.clear();

                    }
                    if (dataSet != null) {
                        dataSet.clear();
                    }
                    jLabel_Cp.setText("");
                    jLabel_Cpk.setText("");
                    jLabel_mean.setText("");
                    jLabel_std_dev.setText("");
                } catch (Exception e1) {
                    System.out.println(e1);
                    StaticValues.writer.writeExcel(ProcessQualityReport.class.getSimpleName(), ProcessQualityReport.class.getSimpleName(), e.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e1.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
                }
                load_products(order.get(jComboBoxOrders.getSelectedIndex()).PO_ID);
            }
        }
    };

    ActionListener FgComboAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!product.isEmpty() && product != null) {

                if (jComboBox_process.getItemCount() != 0) {
                    process.clear();
                    jComboBox_process.removeActionListener(procComboAction);
                    jComboBox_process.removeAllItems();
                }

                if (jComboBoxParamter.getItemCount() != 0) {
                    param.clear();
                     jComboBoxParamter.removeActionListener(paramaters);
                    jComboBoxParamter.removeAllItems();
                }
                try {
                    if (cp != null) {
                        panel.remove(cp);
                        panel.repaint();
                        panel.revalidate();
           // dataSet.clear();

                    }
                    if (dataSet != null) {
                        dataSet.clear();
                    }
                    jLabel_Cp.setText("");
                    jLabel_Cpk.setText("");
                    jLabel_mean.setText("");
                    jLabel_std_dev.setText("");
                } catch (Exception e1) {
                    System.out.println(e1);
                    StaticValues.writer.writeExcel(ProcessQualityReport.class.getSimpleName(), ProcessQualityReport.class.getSimpleName(), e.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e1.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
                }
                load_process(product.get(jComboBox_FG.getSelectedIndex()).FG_ID);

            }

        }
    };

    ActionListener procComboAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!process.isEmpty() && process != null) {
                try {
                    if (cp != null) {
                        panel.remove(cp);
                        panel.repaint();
                        panel.revalidate();
           // dataSet.clear();

                    }
                    if (dataSet != null) {
                        dataSet.clear();
                    }
                    jLabel_Cp.setText("");
                    jLabel_Cpk.setText("");
                    jLabel_mean.setText("");
                    jLabel_std_dev.setText("");
                } catch (Exception e1) {
                    System.out.println(e1);
                    StaticValues.writer.writeExcel(ProcessQualityReport.class.getSimpleName(), ProcessQualityReport.class.getSimpleName(), e.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e1.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
                }
                if (param.size() != 0) {
                    param.clear();
                }
                if (jComboBoxParamter.getItemCount() != 0) {
                     jComboBoxParamter.removeActionListener(paramaters);
                    jComboBoxParamter.removeAllItems();
                }

                load_paramaters(process.get(jComboBox_process.getSelectedIndex()).PRC_ID);

            }

        }
    };

    ActionListener paramaters = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(param.size() != 0)
            {
                try {
                    if (cp != null) {
                        panel.remove(cp);
                        panel.repaint();
                        panel.revalidate();
           // dataSet.clear();

                    }
                    if (dataSet != null) {
                        dataSet.clear();
                    }
                    jLabel_Cp.setText("");
                    jLabel_Cpk.setText("");
                    jLabel_mean.setText("");
                    jLabel_std_dev.setText("");
                } catch (Exception e1) {
                    System.out.println(e1);
                    StaticValues.writer.writeExcel(ProcessQualityReport.class.getSimpleName(), ProcessQualityReport.class.getSimpleName(), e.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e1.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
                }
            }
           

        }
    };
    public void load_products(int po_id) {

        if (jComboBox_FG.getItemCount() != 0) {
            jComboBox_FG.removeActionListener(FgComboAction);
            jComboBox_FG.removeAllItems();
            // product.clear();
        }
        // product=null;
        if (product.size() != 0) {
            product.clear();
        }
        addEmpAPICall = "podetails?ref_po_id=" + po_id;
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        System.out.println(result2);

        ArrayList<String> product1 = null;
        if (result2 != null && !result2.contains("not found")) {

            HashMap<String, Object> map = new HashMap<String, Object>();
            JSONObject jObject = new JSONObject(result2);
            Iterator<?> keys = jObject.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                Object value = jObject.get(key);
                map.put(key, value);
            }

            JSONObject st = (JSONObject) map.get("data");
            JSONArray records = null;
            records = st.getJSONArray("records");

            JSONObject emp = null;
            product1 = new ArrayList<String>();
            for (int i = 0; i < records.length(); i++) {
                emp = records.getJSONObject(i);

                product1.add(emp.get("product_id").toString());

            }

        }
        if (product1 != null && !product1.isEmpty()) {
            // product = new ArrayList<ProductDR>();
            for (int j = 0; j < product1.size(); j++) {
                addEmpAPICall = "finishedgoodsedit?FG_ID=" + product1.get(j);
                result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                System.out.println(result2);

                if (result2 != null) {
                    if (!result2.contains("not found")) {

                        HashMap<String, Object> map = new HashMap<String, Object>();
                        JSONObject jObject = new JSONObject(result2);
                        Iterator<?> keys = jObject.keys();

                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            Object value = jObject.get(key);
                            map.put(key, value);
                        }

                        JSONObject jsobje = new JSONObject(result2);
                        String page = jsobje.getJSONArray("data").toString();
                        JSONArray records = new JSONArray(page);

                        JSONObject emp = null;

                        for (int i = 0; i < records.length(); i++) {
                            emp = records.getJSONObject(i);
                            ProductDR fg = new ProductDR();
                            fg.FG_ID = emp.getInt("FG_ID");
                            fg.FG_PART_NO = emp.getString("FG_PART_NO");

                            product.add(fg);
                            jComboBox_FG.addItem(emp.getString("FG_PART_NO"));
                        }

                    }

                }
            }
            jComboBox_FG.addActionListener(FgComboAction);
            load_process(product.get(jComboBox_FG.getSelectedIndex()).FG_ID);

        }

    }

    public void load_paramaters(int proc_id) {
        if (param.size() != 0) {
            param.clear();
        }
        if (jComboBoxParamter.getItemCount() != 0) {
            jComboBoxParamter.removeActionListener(paramaters);
            jComboBoxParamter.removeAllItems();
        }
        addEmpAPICall = "proc_qty_master?proc_id=" + proc_id;
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
                part.setPartId(Integer.parseInt(emp.get("proc_param_name").toString()));
                part.setPartName(emp.get("param_name").toString());
                param.add(part);
                jComboBoxParamter.addItem(emp.get("param_name").toString());
            }

           jComboBoxParamter.addActionListener(paramaters);
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

        jComboBox_FG = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        btn_add = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxOrders = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxParamter = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel_Cp = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel_Cpk = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel_mean = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel_std_dev = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox_process = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        dateChooserCombo = new datechooser.beans.DateChooserCombo();

        setPreferredSize(new java.awt.Dimension(900, 300));

        jLabel3.setText("Part No / Code");

        btn_add.setText("Submit");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        jLabel6.setText("Order No.");

        jComboBoxOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxOrdersActionPerformed(evt);
            }
        });

        jLabel8.setText("Parameter");

        jComboBoxParamter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxParamterActionPerformed(evt);
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
        jLabel5.setText("Mean");

        jLabel_mean.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_mean.setText("jLabel7");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel7.setText("Std Deviation:");

        jLabel_std_dev.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_std_dev.setText("jLabel9");

        jLabel9.setText("Process No.");

        jLabel10.setText("Date");

        dateChooserCombo.setCalendarPreferredSize(new java.awt.Dimension(320, 220));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateChooserCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_FG, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel_mean, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_std_dev, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jLabel_Cp, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel_Cpk, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox_process, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxParamter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_process, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBoxParamter, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBox_FG, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dateChooserCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Cp)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Cpk)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_mean)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_std_dev))
                .addContainerGap(148, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // TODO add your handling code here:

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

        if (dataSet != null) 
        {
            dataSet.clear();
        }
        if (value1.size() != 0) 
        {
            value1.clear();
        }
         SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
         String Date1 = sdf2.format(dateChooserCombo.getSelectedDate().getTime());
        
        addEmpAPICall = "proc_qty_master?proc_id=" + process.get(jComboBox_process.getSelectedIndex()).PRC_ID
                + "&param_id=" + param.get(jComboBoxParamter.getSelectedIndex()).partId;
        // addEmpAPICall = "partqty_masters?fg_id=2&param_id=3";
        String result = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        System.out.println(result);

        DecimalFormat df = new DecimalFormat("#,###,##0.0000");
        DecimalFormat df1 = new DecimalFormat("#,###,##0.0000000");
        DecimalFormat df_ = new DecimalFormat("#,###,##0.000");
       // df.setMaximumFractionDigits(4);
        // df.set
        // System.out.println(df.format(decimalNumber));
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

            // ArrayList<String> value=new ArrayList<>();
            for (int i = 0; i < records.length(); i++) {
                emp = records.getJSONObject(i);
                min1 = Float.parseFloat("" + emp.getDouble("LLL"));
                max1 = Float.parseFloat("" + emp.getDouble("ULL"));
                min = Float.parseFloat(df.format(min1));
                max = Float.parseFloat(df.format(max1));

            }
        }

        addEmpAPICall = "proc_qty_data?order_id=" + order.get(jComboBoxOrders.getSelectedIndex()).PO_ID
                + "&fg_id=" + product.get(jComboBox_FG.getSelectedIndex()).FG_ID
                + "&proc_id=" + process.get(jComboBox_process.getSelectedIndex()).PRC_ID
                + "&param_id=" + param.get(jComboBoxParamter.getSelectedIndex()).partId
                + "&date="+Date1;
        // addEmpAPICall = "partqty_data?batch_id=4&order_id=25&fg_id=1&param_id=3";

        result = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
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

            ArrayList<String> value = new ArrayList<>();
            for (int i = 0; i < records.length(); i++) {
                emp = records.getJSONObject(i);
                value.add(emp.getString("ref_prcqp_value"));
                value1.add(emp.getDouble("ref_prcqp_value"));

            }

            if (value.size() != 0) {
                ArrayList<String> diff_ = new ArrayList<>();
                ArrayList<String> diff_Square = new ArrayList<>();

                for (int i = 0; i < value.size(); i++) {
                    tot = tot + Float.parseFloat(value.get(i));
                    // tot1 = tot1 + Float.parseFloat(value.get(i));
                }
                int size = value.size();

                mean1 = tot / size;
                String get_Means = df.format(mean1);
                mean = Float.parseFloat(get_Means);
                System.out.println("Mean    " + mean);
                jLabel_mean.setText("" + df_.format(mean));

                System.out.println("Diffrence");

                for (int i = 0; i < value.size(); i++) {
                    Float diff, diff1;
                    diff = Float.parseFloat(value.get(i)) - mean;
                    //Double.parseDouble(df.format(diff));       
                    System.out.println("" + df.format(diff));
                    diff_.add(df.format(diff));
                }
                System.out.println("Square");
                for (int j = 0; j < diff_.size(); j++) {
                    Float square;
                    square = Float.parseFloat(diff_.get(j)) * Float.parseFloat(diff_.get(j));

                    System.out.println("" + df1.format(square));
                    diff_Square.add(df1.format(square));
                }
                for (int p = 0; p < diff_Square.size(); p++) {
                    tot_square = tot_square + Float.parseFloat(diff_Square.get(p));
                    String get = df1.format(tot_square);
                    tot_square = Float.parseFloat(get);
                }
                int size1 = diff_Square.size();
                String get_tot = df1.format(tot_square);
                variance = Float.parseFloat(get_tot) / size1;

                Float varian = Float.parseFloat("" + variance);
                System.out.println("Variance === " + df1.format(varian));

                Double std_dev1 = Math.sqrt(variance);
                //std_dev= Double.parseDouble(df.format(std_dev1));
                Float std_dev = Float.parseFloat("" + std_dev1);
                System.out.println("std Dev === " + df1.format(std_dev));

                //dev = Float.parseFloat("0.0007561678");
                String get_Std = df1.format(std_dev);
                UCL = mean + (3 * Double.parseDouble(get_Std));
                // UCL= Double.parseDouble(df.format(UCL));
                System.out.println("UCL === " + UCL);

                LCL = mean - (3 * Double.parseDouble(get_Std));
                // LCL= Double.parseDouble(df.format(LCL));
                System.out.println("LCL === " + LCL);
                
                Double dev = Double.parseDouble(get_Std);
                String get_dev = df1.format(dev);
                Double Cp = (max - min) / (6 * Double.parseDouble(get_dev));
                Double Cpk = (max - mean) / (3 * Double.parseDouble(get_dev));
                Double Cpk1 = (mean - min) / (3 * Double.parseDouble(get_dev));
                System.out.println("Cpk  " + Cpk + "   " + Cpk1);
                jLabel_std_dev.setText("" + df_.format(dev));
                jLabel_Cp.setText("" + df_.format(Cp));
                if (Cpk < Cpk1) {
                    jLabel_Cpk.setText("" + df_.format(Cpk));
                } else {
                    jLabel_Cpk.setText("" + df_.format(Cpk1));
                }
                        //*****************************

                chart = generateLineChart("", "", "", "");
                /* GraphPanel graph = new  GraphPanel(value1,Float.valueOf("1.50"),mean,Float.valueOf("1.50"),Float.valueOf("1.49"),LCL,UCL);
                 graph.setBounds(100,200, 800, 300);
                 add(graph);*/
                //******************   
            }
        } else {
            try {
                if (cp != null) {
                    panel.remove(cp);
                    panel.repaint();
                    panel.revalidate();
           // dataSet.clear();

                }
                if (dataSet != null) {
                    dataSet.clear();
                }
                jLabel_Cp.setText("");
                jLabel_Cpk.setText("");
                jLabel_mean.setText("");
                jLabel_std_dev.setText("");
            } catch (Exception e) {
                System.out.println(e);
                StaticValues.writer.writeExcel(ProcessQualityReport.class.getSimpleName(), ProcessQualityReport.class.getSimpleName(), e.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
            }
        }


    }//GEN-LAST:event_btn_addActionPerformed

    private void jComboBoxOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxOrdersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxOrdersActionPerformed

    private void jComboBoxParamterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxParamterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxParamterActionPerformed

    public JFreeChart generateLineChart(String reportTitle, String xAxis, String yAxis, String chartType) {
        try {
            if (cp != null) {
                panel.remove(cp);
                panel.repaint();
                panel.revalidate();

            }
            if (dataSet != null) {
                dataSet.clear();
            }
        } catch (Exception e) {
            System.out.println(e);
            StaticValues.writer.writeExcel(ProcessQualityReport.class.getSimpleName(), ProcessQualityReport.class.getSimpleName(), e.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
        }
        dataSet = new DefaultCategoryDataset();
        yAxis = "Actual";
        xAxis = "Mean";
        String LSL = "LSL", USL = "USL", LCL1 = "LCL", UCL1 = "UCL";

        for (int i = 0; i < value1.size(); i++) {
            try {
                Double data = value1.get(i);
                dataSet.setValue(data, yAxis, "" + i);
                dataSet.setValue(mean, xAxis, "" + i);
                dataSet.setValue(min, LSL, "" + i);
                dataSet.setValue(max, USL, "" + i);
                dataSet.setValue(LCL, LCL1, "" + i);
                dataSet.setValue(UCL, UCL1, "" + i);

            } catch (Exception e) {
                e.getMessage();
                // aticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }
        chart = ChartFactory.createLineChart(reportTitle, "", "", dataSet, PlotOrientation.VERTICAL, true, true, false);
        cp = new ChartPanel(chart);
        cp.setPreferredSize(new java.awt.Dimension(900, 380));
        cp.setBounds(0, 0, 700, 380);
        cp.revalidate();
        cp.repaint();

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.WHITE);
        plot.setRangeGridlinesVisible(true);
        ValueAxis range = (ValueAxis) plot.getRangeAxis();
        range.setRange(min - 0.0010, max + 0.0010);
       // range.setRange(value1.get(0),0.0020+value1.get(value1.size()-1));
        //range.set  setTickUnit(new NumberTickUnit(0.1));

        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//        BarRenderer renderer = ( BarRenderer ) plot.getRenderer ();
        renderer.setBaseItemLabelsVisible(false);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setSeriesPaint(1, Color.CYAN);
        renderer.setSeriesPaint(1, Color.MAGENTA);
        //  renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesItemLabelFont(1, new Font("Times New Roman", Font.PLAIN, 8));
        renderer.setSeriesItemLabelPaint(0, Color.BLACK);
        renderer.setSeriesItemLabelFont(0, new Font("Times New Roman", Font.PLAIN, 8));
        renderer.setSeriesItemLabelPaint(1, Color.BLACK);

        plot.getRenderer().setSeriesPaint(2, new Color(0x00, 0xFF, 0x00));
        plot.getRenderer().setSeriesPaint(4, new Color(0x00, 0x00, 0x00));
        plot.getRenderer().setSeriesPaint(3, new Color(0x00, 0xFF, 0x00));
        plot.getRenderer().setSeriesPaint(5, new Color(0x00, 0x00, 0x00));

        panel.add(cp);
        panel.revalidate();
        panel.repaint();

        return chart;
    }

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private datechooser.beans.DateChooserCombo dateChooserCombo;
    private javax.swing.JComboBox jComboBoxOrders;
    private javax.swing.JComboBox jComboBoxParamter;
    private javax.swing.JComboBox jComboBox_FG;
    private javax.swing.JComboBox jComboBox_process;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Cp;
    private javax.swing.JLabel jLabel_Cpk;
    private javax.swing.JLabel jLabel_mean;
    private javax.swing.JLabel jLabel_std_dev;
    // End of variables declaration//GEN-END:variables
}
