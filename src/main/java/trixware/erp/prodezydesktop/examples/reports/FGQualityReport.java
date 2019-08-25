/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples.reports;

import trixware.erp.prodezydesktop.Model.Batch;
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
public class FGQualityReport extends javax.swing.JPanel {
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
   // JScrollPane scroll = null;
      public static DefaultCategoryDataset dataSet = null;
      ArrayList<Double> value1 = new ArrayList<>();

    /**
     * Creates new form RMQualityEntryForm
     */
    public FGQualityReport() 
    {
        initComponents();
        this.setBackground(Color.WHITE);
        repaint();
        revalidate();
        loadContent();
        if (order != null && !order.isEmpty()) {
            load_products(order.get(0).PO_ID);

        }
        panel = new JPanel ();
        panel.setBackground ( Color.WHITE );
        panel.setBounds ( -40,180 , 1000 , 420 );
        //add.(panel);
        panel.revalidate ();
        panel.repaint ();

       /* scroll = new JScrollPane ( panel ,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        scroll.setBounds ( 50 , 200 , 800 , 300 );
        scroll.setOpaque ( false );
        scroll.setBackground ( Color.BLACK );*/

        cp = new ChartPanel ( chart );
        cp.setPreferredSize ( new java.awt.Dimension ( 0 , 380 ) );
        cp.setBounds ( 0 , 0 ,700 , 380 );
        cp.setBackground ( Color.WHITE );

      
        panel.add ( cp );
        add(panel);
        //scroll.revalidate ();
       // scroll.repaint ();
       // add ( scroll );
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

        addEmpAPICall = "batches";
        String result = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        System.out.println(result2);
        if (!result.contains("not found")) {
            if (result != null) {
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
                batch = new ArrayList<Batch>();
                for (int i = 0; i < records.length(); i++) {
                    emp = records.getJSONObject(i);
                    Batch rm = new Batch();
                    rm.batchId = emp.getInt("BatchId");
                    rm.batchName = emp.getString("BatchName");

                    batch.add(rm);
                    jComboBox_batch.addItem(emp.getString("BatchName"));
                }
            }

        }
    }
    ActionListener orderComboAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            load_products(order.get(jComboBoxOrders.getSelectedIndex()).PO_ID);

        }
    };

    ActionListener FgComboAction = new ActionListener() 
    {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!product.isEmpty() && product != null) 
            {
                if (param.size() != 0) 
                {
                    param.clear();
                }
                if (jComboBoxParamter.getItemCount() != 0) {
                    jComboBoxParamter.removeActionListener(paramaters);
                    jComboBoxParamter.removeAllItems();
                }
                load_paramaters(product.get(jComboBox_FG.getSelectedIndex()).FG_ID);

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
                    if (cp != null) 
                    {
                        panel.remove(cp);
                        panel.repaint();
                        panel.revalidate();
           

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
                    StaticValues.writer.writeExcel(FGQualityReport.class.getSimpleName(), FGQualityReport.class.getSimpleName(), e.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e1.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
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
            load_paramaters(product.get(jComboBox_FG.getSelectedIndex()).FG_ID);

        }

    }

    public void load_paramaters(int fg_id) 
    {
        if(param.size() != 0)
        {
            param.clear();
        }
        if(jComboBoxParamter.getItemCount() != 0)
        {
             jComboBoxParamter.removeActionListener(paramaters);
            jComboBoxParamter.removeAllItems();
        }
        addEmpAPICall = "partqty_masters?fg_id=" + fg_id;
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
                part.setPartId(Integer.parseInt(emp.get("part_param_name").toString()));
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

        jComboBox_batch = new javax.swing.JComboBox();
        jComboBox_FG = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
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
        dateChooserCombo = new datechooser.beans.DateChooserCombo();

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

        jLabel9.setText("Date");

        dateChooserCombo.setCalendarPreferredSize(new java.awt.Dimension(320, 220));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(dateChooserCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel_mean, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_std_dev, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBox_batch, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel_Cp, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel_Cpk, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBox_FG, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxParamter, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_FG, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_batch, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateChooserCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBoxParamter, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBoxOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Cp)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Cpk)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_mean)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_std_dev))
                .addContainerGap(123, Short.MAX_VALUE))
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
        
        if(dataSet != null)
            {
             dataSet.clear();
            }
        if(value1.size() != 0)
        {
            value1.clear();
        }
        
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
         String Date1 = sdf2.format(dateChooserCombo.getSelectedDate().getTime());
        
      
        addEmpAPICall = "partqty_masters?fg_id="+product.get(jComboBox_FG.getSelectedIndex()).FG_ID
                                         +"&param_id="+param.get(jComboBoxParamter.getSelectedIndex()).partId;
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
                 min1= Float.parseFloat(""+emp.getDouble("LLL"));
                 max1 = Float.parseFloat(""+emp.getDouble("ULL"));
                 min= Float.parseFloat(df.format(min1));
                 max=  Float.parseFloat(df.format(max1));

            }
        }

          addEmpAPICall =  "partqty_data?order_id="+order.get(jComboBoxOrders.getSelectedIndex()).PO_ID
         +"&batch_id="+batch.get(jComboBox_batch.getSelectedIndex()).batchId 
         +"&fg_id="+product.get(jComboBox_FG.getSelectedIndex()).FG_ID
         +"&param_id="+param.get(jComboBoxParamter.getSelectedIndex()).partId
         +"&date="+Date1;
       // addEmpAPICall = "partqty_data?batch_id=4&order_id=25&fg_id=1&param_id=3";

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

                        //JSONObject jsobje = new JSONObject(result);
            JSONObject st = (JSONObject) map.get("data");
            JSONArray records = st.getJSONArray("records");

            JSONObject emp = null;

            ArrayList<String> value = new ArrayList<>();
            for (int i = 0; i < records.length(); i++) 
            {
                emp = records.getJSONObject(i);
                value.add(emp.getString("ref_prtqp_value"));
                value1.add(emp.getDouble("ref_prtqp_value"));
                
            }

            if (value.size() != 0) 
            {
                ArrayList<String> diff_ = new ArrayList<>();
                ArrayList<String> diff_Square = new ArrayList<>();
                
                for (int i = 0; i < value.size(); i++) {
                    tot = tot + Float.parseFloat(value.get(i));
                    // tot1 = tot1 + Float.parseFloat(value.get(i));
                }
                int size = value.size();

                mean1 = tot / size;
                String get_Means = df.format(mean1);
                mean =Float.parseFloat(get_Means);
                System.out.println("Mean    " + mean);
                jLabel_mean.setText(""+df_.format(mean));

                System.out.println("Diffrence");
               
                for (int i = 0; i < value.size(); i++) 
                {
                    Float diff , diff1 ;
                    diff = Float.parseFloat(value.get(i)) - mean;
                      //Double.parseDouble(df.format(diff));       
                    System.out.println(""+df.format(diff));
                    diff_.add(df.format(diff));
                }
                System.out.println("Square");
                for (int j = 0; j < diff_.size(); j++) 
                {
                    Float square ;
                    square = Float.parseFloat(diff_.get(j)) * Float.parseFloat(diff_.get(j));
                  
                    System.out.println("" + df1.format(square));
                    diff_Square.add(df1.format(square));
                }
                for (int p = 0; p < diff_Square.size(); p++) 
                {
                    tot_square = tot_square + Float.parseFloat(diff_Square.get(p));
                      String get=df1.format(tot_square);
                      tot_square = Float.parseFloat(get);
                }
                int size1 = diff_Square.size();
                  String get_tot=df1.format(tot_square);
                variance = Float.parseFloat(get_tot) / size1;
              
                Float varian = Float.parseFloat("" + variance);
                System.out.println("Variance === " +df1.format( varian));
                
               
                Double std_dev1 = Math.sqrt(variance);
                //std_dev= Double.parseDouble(df.format(std_dev1));
                Float std_dev = Float.parseFloat("" + std_dev1);
                System.out.println("std Dev === " + df1.format(std_dev));

                //dev = Float.parseFloat("0.0007561678");
              

                String get_Std=df1.format(std_dev);
                UCL = mean + (3 * Double.parseDouble(get_Std));
                // UCL= Double.parseDouble(df.format(UCL));
                System.out.println("UCL === " + UCL);

                LCL = mean - (3 * Double.parseDouble(get_Std));
                // LCL= Double.parseDouble(df.format(LCL));
                System.out.println("LCL === " + LCL);
//                max = Float.parseFloat("1.505");
//                min = Float.parseFloat("1.495");
                Double dev=Double.parseDouble(get_Std);
                String get_dev=df1.format(dev);
                Double Cp = (max - min) / (6 * Double.parseDouble(get_dev) );
                Double Cpk = (max - mean) / (3 * Double.parseDouble(get_dev));
                Double Cpk1 = (mean - min) / (3 * Double.parseDouble(get_dev));
                System.out.println("Cpk  "+Cpk + "   "+Cpk1);
                jLabel_std_dev.setText(""+df_.format(dev));
                jLabel_Cp.setText("" + df_.format(Cp));
                if(Cpk < Cpk1)
                {
                jLabel_Cpk.setText("" + df_.format(Cpk));
                }
                else
                {
                    jLabel_Cpk.setText("" + df_.format(Cpk1));
                }
                        //*****************************
                
                
                chart = generateLineChart("" , "" , "","" );
   /* GraphPanel graph = new  GraphPanel(value1,Float.valueOf("1.50"),mean,Float.valueOf("1.50"),Float.valueOf("1.49"),LCL,UCL);
    graph.setBounds(100,200, 800, 300);
    add(graph);*/
             //******************   
            }
        }
        else
        {
           try 
        {
            if(cp != null)
            {
            panel.remove ( cp );
            panel.repaint();
            panel.revalidate();
           // dataSet.clear();
          
            }
            if(dataSet != null)
            {
             dataSet.clear();
            }
            jLabel_Cp.setText("");
            jLabel_Cpk.setText("");
            jLabel_mean.setText("");
            jLabel_std_dev.setText("");
        } catch ( Exception e ) 
        {
            System.out.println(e);
            StaticValues.writer.writeExcel ( FGQualityReport.class.getSimpleName () , FGQualityReport.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } 
        }


    }//GEN-LAST:event_btn_addActionPerformed

    private void jComboBox_batchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox_batchMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_batchMouseClicked

    private void jComboBox_batchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_batchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_batchActionPerformed

    private void jComboBoxOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxOrdersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxOrdersActionPerformed

    public JFreeChart generateLineChart ( String reportTitle , String xAxis , String yAxis,String chartType ) 
    {
        try 
        {
            if(cp != null)
            {
            panel.remove ( cp );
            panel.repaint();
            panel.revalidate();
            
            }
            if(dataSet != null)
            {
             dataSet.clear();
            }
        } catch ( Exception e ) 
        {
            System.out.println(e);
            StaticValues.writer.writeExcel ( FGQualityReport.class.getSimpleName () , FGQualityReport.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }
          dataSet = new DefaultCategoryDataset ();
          yAxis = "Actual";
          xAxis = "Mean";
          String LSL ="LSL",USL="USL",LCL1="LCL",UCL1="UCL";
         
       for ( int i = 0 ; i < value1.size () ; i ++ ) 
            {
                try
                {
                    Double data=value1.get(i);
                    dataSet.setValue ( data , yAxis, ""+i );
                    dataSet.setValue(mean, xAxis, ""+i);
                    dataSet.setValue(min, LSL, ""+i);
                    dataSet.setValue(max, USL, ""+i);
                    dataSet.setValue(LCL, LCL1, ""+i);
                    dataSet.setValue(UCL, UCL1, ""+i);
                    
                    
                } catch ( Exception e )
                {
                    e.getMessage();
                  // aticValues.writer.writeExcel ( ReportsController.class.getSimpleName () , ReportsController.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }
            } 
        chart =   ChartFactory.createLineChart(reportTitle,"","",dataSet,PlotOrientation.VERTICAL,true,true,false);
        cp = new ChartPanel ( chart );
        cp.setPreferredSize ( new java.awt.Dimension ( 900 , 380 ) );
        cp.setBounds ( 0 , 0 , 700 , 380 );
        cp.revalidate ();
        cp.repaint ();  
        
        CategoryPlot plot = ( CategoryPlot ) chart.getPlot ();
        plot.setBackgroundPaint ( Color.WHITE );
        plot.setDomainGridlinePaint ( Color.WHITE );
        plot.setDomainGridlinesVisible ( true );
        plot.setRangeGridlinePaint ( Color.WHITE );
        plot.setRangeGridlinesVisible ( true );
        ValueAxis range = (ValueAxis) plot. getRangeAxis();
        range.setRange(min-0.0010,max+0.0010);
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
            
            panel.add ( cp );
            panel.revalidate ();
            panel.repaint ();
            
            return chart;
    }
    
    /*public class GraphPanel extends JPanel {

    private int width = 800;
    private int heigth = 400;
    private int padding = 25;
    private int labelPadding = 25;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private  final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;
    private List<Double> scores;
    Float bench ,mean ,min,max;
    Double LCL,UCL;

    public GraphPanel(List<Double> scores,Float bench,float mean,float min,float max,Double LCL,Double UCL) 
    {
        this.scores = scores;
        this.bench = bench;
        this.mean = mean;
        this.min = min;
        this.max = max;
        this.LCL = LCL;
        this.UCL = UCL;
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (scores.size() - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

        List<Point> graphPoints = new ArrayList<>();
        List<Point> benchPoint = new ArrayList<>();
        List<Point> meanPoint = new ArrayList<>();
          List<Point> minPoint = new ArrayList<>(); 
          List<Point> maxPoint = new ArrayList<>(); 
          List<Point> LCLPoint = new ArrayList<>();
          List<Point> UCLPoint = new ArrayList<>();
          
          
        for (int i = 0; i < scores.size(); i++) 
        {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMaxScore() - scores.get(i)) * yScale + padding);
            int y2 = (int) ((getMaxScore() - bench) * yScale + padding);
            int y3 = (int) ((getMaxScore() - mean) * yScale + padding);
            int y4 = (int) ((getMaxScore() - min) * yScale + padding);
            int y5 = (int) ((getMaxScore() - max) * yScale + padding);
            int y6 = (int) ((getMaxScore() - LCL) * yScale + padding);
            int y7 = (int) ((getMaxScore() - UCL) * yScale + padding);
            
            graphPoints.add(new Point(x1, y1));
            benchPoint.add(new Point(x1, y2));
            meanPoint.add(new Point(x1, y3));
            minPoint.add(new Point(x1, y4));
            maxPoint.add(new Point(x1, y5));
            LCLPoint.add(new Point(x1, y6));
            UCLPoint.add(new Point(x1, y7));
        }

        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        // create hatch marks and grid lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (scores.size() > 0) 
            {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        for (int i = 0; i < scores.size(); i++) {
            if (scores.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (scores.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((scores.size() / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        // create x and y axes 
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) 
        {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }
         for (int i = 0; i < benchPoint.size() - 1; i++) 
        {
            int x3 = benchPoint.get(i).x;
            int y3 = benchPoint.get(i).y;
            int x4 = benchPoint.get(i + 1).x;
            int y4 = benchPoint.get(i + 1).y;
          // g2.drawLine(x3, y3, x4, y4);
        }   
         for (int i = 0; i < meanPoint.size() - 1; i++) 
        {
         int x5 = meanPoint.get(i).x;
            int y5 = meanPoint.get(i).y;
            int x6 = meanPoint.get(i + 1).x;
            int y6 = meanPoint.get(i + 1).y;
            g2.drawLine(x5, y5, x6, y6);
        }
          for (int i = 0; i < minPoint.size() - 1; i++) 
        {
            int x7 = minPoint.get(i).x;
            int y7 = minPoint.get(i).y;
            int x8 = minPoint.get(i + 1).x;
            int y8 = minPoint.get(i + 1).y;
            g2.drawLine(x7, y7, x8, y8);
        }
           for (int i = 0; i < maxPoint.size() - 1; i++) 
        {
           int x9 = maxPoint.get(i).x;
            int y9 = maxPoint.get(i).y;
            int x10 = maxPoint.get(i + 1).x;
            int y10= maxPoint.get(i + 1).y;
            g2.drawLine(x9, y9, x10, y10);
            
        }
            for (int i = 0; i < LCLPoint.size() - 1; i++) 
            {
            int x11 = LCLPoint.get(i).x;
            int y11 = LCLPoint.get(i).y;
            int x12 = LCLPoint.get(i + 1).x;
            int y12= LCLPoint.get(i + 1).y;
            }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }

//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(width, heigth);
//    }
    private double getMinScore() {
        double minScore = Double.MAX_VALUE;
        for (Double score : scores) {
            minScore = Math.min(minScore, score);
        }
        return minScore;
    }

    private double getMaxScore() {
        double maxScore = Double.MIN_VALUE;
        for (Double score : scores) {
            maxScore = Math.max(maxScore, score);
        }
        return maxScore;
    }

    public void setScores(List<Double> scores) {
        this.scores = scores;
        invalidate();
        this.repaint();
    }

    public List<Double> getScores() {
        return scores;
    }

    private  void createAndShowGui() {
        List<Double> scores = new ArrayList<>();
        Random random = new Random();
        int maxDataPoints = 40;
        int maxScore = 10;
        for (int i = 0; i < maxDataPoints; i++) {
            scores.add((double) random.nextDouble() * maxScore);
//            scores.add((double) i);
        }
        GraphPanel mainPanel = new GraphPanel(scores,bench,mean,min,max,LCL,UCL);
        mainPanel.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame("DrawGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public  void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui();
         }
      });
   }
}*/


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private datechooser.beans.DateChooserCombo dateChooserCombo;
    private javax.swing.JComboBox jComboBoxOrders;
    private javax.swing.JComboBox jComboBoxParamter;
    private javax.swing.JComboBox jComboBox_FG;
    private javax.swing.JComboBox jComboBox_batch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
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
