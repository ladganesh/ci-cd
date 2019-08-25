/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import trixware.erp.prodezydesktop.components.QualityDataPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import trixware.erp.prodezydesktop.Model.Batch;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.QualityDataModel;
import trixware.erp.prodezydesktop.Model.Rejection_Reasons;
import trixware.erp.prodezydesktop.Model.SalesOrderDr;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author
 */
//Created By Harshali  FG Quality Entry
public class FGQualityEntryForm extends javax.swing.JPanel {

    ArrayList<String[]> docList = null;
    ArrayList<ProductDR> product = new ArrayList<>();
    ArrayList<SalesOrderDr> order = null;
    ArrayList<QualityParamList> qp = null;
    QualityParamList fgList = null, fgList1 = null;
    ArrayList<Batch> batch = null;
    ArrayList<Rejection_Reasons> list = null;
    String addEmpAPICall;

    /**
     * Creates new form RMQualityEntryForm
     */
    public FGQualityEntryForm() {
        initComponents();
        this.setBackground(Color.WHITE);
        repaint();
        revalidate();
        loadContent();
        //fgList =new QualityParamList();
        if (order != null && !order.isEmpty()) {
            load_products(order.get(0).PO_ID);

            if (product != null && !product.isEmpty()) {
                fgList = new QualityParamList(product.get(0).FG_ID);
                fgList.setBounds(60, 230, 700, 260);
                add(fgList);
                loadDocumentsForFG(product.get(0).FG_ID);

            }
        }

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
                    
                    int flag = emp.getInt("quality_flag");
                    
                    if(flag == 1)
                    {
                    Batch rm = new Batch();
                    rm.batchId = emp.getInt("BatchId");
                    rm.batchName = emp.getString("BatchName");
                    batch.add(rm);
                    jComboBox_batch.addItem(emp.getString("BatchName"));
                    }
                }
            }

        }
    }
    ActionListener orderComboAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (fgList != null) {
                fgList.removeAll();
                remove(fgList);
                repaint();
                revalidate();

            }
            if (product.size() != 0) {
                product.clear();
            }

            load_products(order.get(jComboBoxOrders.getSelectedIndex()).PO_ID);

            try {
                if (!product.isEmpty() && product != null && product.size() != 0) {
                    loadDocumentsForFG(product.get(0).FG_ID);
                    fgList = new QualityParamList(product.get(0).FG_ID);
                    fgList.setBounds(60, 230, 700, 260);
                    add(fgList);
                }
            } catch (Exception d) {
                d.getMessage();
                System.out.println("" + d.getMessage() + "  Error............");
            }
        }
    };

    ActionListener FgComboAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!product.isEmpty() && product != null) {
                if (fgList != null) {
                    fgList.removeAll();
                    remove(fgList);
                    repaint();
                    revalidate();

                }
                // if(paramList.size != 0)
                loadDocumentsForFG(product.get(jComboBox_FG.getSelectedIndex()).FG_ID);
                fgList = new QualityParamList(product.get(jComboBox_FG.getSelectedIndex()).FG_ID);
                fgList.setBounds(60, 230, 700, 260);
                add(fgList);
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

        } else {

            if (fgList != null) {
//              fgList.removeAll();
//              remove(fgList);
//              fgList.repaint();
//              fgList.revalidate();
                fgList = null;
                revalidate();
                repaint();
            }
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
        dateChooserCombo = new datechooser.beans.DateChooserCombo();
        jComboBox_FG = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btn_add = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        btn_Cancel = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxOrders = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel8 = new javax.swing.JLabel();

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

        dateChooserCombo.setCalendarPreferredSize(new java.awt.Dimension(320, 220));

        jLabel1.setText("Date");

        jLabel2.setText("Batch No.");

        jLabel3.setText("Part No / Code");

        jLabel4.setText("Parameter");

        jLabel5.setText("Measured Value");

        btn_add.setText("Submit");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_reset.setText("Reset");

        btn_Cancel.setText("Cancel");

        jLabel6.setText("Order No.");

        jComboBoxOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxOrdersActionPerformed(evt);
            }
        });

        jLabel7.setText("Bench Mark");

        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel8.setText("Reference Documents");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(243, 243, 243)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(btn_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBox_batch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(dateChooserCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(78, 78, 78)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBoxOrders, 0, 166, Short.MAX_VALUE)
                                    .addComponent(jComboBox_FG, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(157, 157, 157)
                                .addComponent(jLabel5)))
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateChooserCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBoxOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_batch, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox_FG, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 224, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(29, 29, 29))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // TODO add your handling code here:

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<QualityDataModel> list = fgList.getList();
        String result = null;
        if (list.size() != 0) {

            int size = list.size();
            int count = 0, notSubmitedd = 0;
            for (int j = 0; j < list.size(); j++) {
                Float min = null, max = null, min1, max1;
                DecimalFormat df = new DecimalFormat("#,###,##0.0000");
                addEmpAPICall = "partqty_masters?fg_id=" + product.get(jComboBox_FG.getSelectedIndex()).FG_ID
                        + "&param_id=" + list.get(j).getId();

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

                    // ArrayList<String> value=new ArrayList<>();
                    for (int i = 0; i < records.length(); i++) {
                        emp = records.getJSONObject(i);
                        min1 = Float.parseFloat("" + emp.getDouble("LLL"));
                        max1 = Float.parseFloat("" + emp.getDouble("ULL"));
                        min = Float.parseFloat(df.format(min1));
                        max = Float.parseFloat(df.format(max1));

                    }
                }

                int id = list.get(j).getId();
                String name = list.get(j).getParam_name();
                String Date1 = sdf2.format(dateChooserCombo.getSelectedDate().getTime());

                String EmpAPICall = null;
                if ((list.get(j).getValue1()) != 0) {

                    if (list.get(j).getValue1() >= min && list.get(j).getValue1() <= max) {
                        try {
                            EmpAPICall = "partqty_data_add?ref_batch_id=" + URLEncoder.encode("" + batch.get(jComboBox_batch.getSelectedIndex()).batchId, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBoxOrders.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_part_id=" + URLEncoder.encode("" + product.get(jComboBox_FG.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_prtqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prtqp_value=" + URLEncoder.encode("" + Float.parseFloat("" + list.get(j).getValue1()), "UTF-8")
                                    + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog(null, result1);
                            list.get(j).setValue1(0);
                            count++;
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    } else {
                        notSubmitedd++;
                        list.get(j).setValue1(0);
                    }
                }
                if ((list.get(j).getValue2()) != 0) {
                    if (list.get(j).getValue2() >= min && list.get(j).getValue2() <= max) {
                        try {
                            EmpAPICall = "partqty_data_add?ref_batch_id=" + URLEncoder.encode("" + batch.get(jComboBox_batch.getSelectedIndex()).batchId, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBoxOrders.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_part_id=" + URLEncoder.encode("" + product.get(jComboBox_FG.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_prtqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prtqp_value=" + URLEncoder.encode("" + Float.parseFloat("" + list.get(j).getValue2()), "UTF-8") + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog(null, result1);
                            list.get(j).setValue2(0);
                            count++;
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    } else {
                        notSubmitedd++;
                        list.get(j).setValue2(0);
                    }
                }
                if ((list.get(j).getValue3()) != 0) {

                    if (list.get(j).getValue3() >= min && list.get(j).getValue3() <= max) {
                        try {
                            EmpAPICall = "partqty_data_add?ref_batch_id=" + URLEncoder.encode("" + batch.get(jComboBox_batch.getSelectedIndex()).batchId, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBoxOrders.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_part_id=" + URLEncoder.encode("" + product.get(jComboBox_FG.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_prtqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prtqp_value=" + URLEncoder.encode("" + Float.parseFloat("" + list.get(j).getValue3()), "UTF-8") + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog(null, result1);
                            list.get(j).setValue3(0);
                            count++;
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    } else {
                        notSubmitedd++;
                        list.get(j).setValue3(0);
                    }
                }
                if ((list.get(j).getValue4()) != 0) {
                    if (list.get(j).getValue4() >= min && list.get(j).getValue4() <= max) {
                        try {
                            EmpAPICall = "partqty_data_add?ref_batch_id=" + URLEncoder.encode("" + batch.get(jComboBox_batch.getSelectedIndex()).batchId, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBoxOrders.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_part_id=" + URLEncoder.encode("" + product.get(jComboBox_FG.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_prtqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prtqp_value=" + URLEncoder.encode("" + Float.parseFloat("" + list.get(j).getValue4()), "UTF-8") + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            // JOptionPane.showMessageDialog(null, result1);
                            list.get(j).setValue4(0);
                            count++;
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    } else {
                        notSubmitedd++;
                        list.get(j).setValue4(0);
                    }
                }
                if ((list.get(j).getValue5()) != 0) {

                    if (list.get(j).getValue5() >= min && list.get(j).getValue5() <= max) {
                        try {
                            EmpAPICall = "partqty_data_add?ref_batch_id=" + URLEncoder.encode("" + batch.get(jComboBox_batch.getSelectedIndex()).batchId, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBoxOrders.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_part_id=" + URLEncoder.encode("" + product.get(jComboBox_FG.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_prtqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prtqp_value=" + URLEncoder.encode("" + Float.parseFloat("" + list.get(j).getValue5()), "UTF-8") + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog(null, result1);
                            list.get(j).setValue5(0);
                            count++;
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    } else {
                        notSubmitedd++;
                        list.get(j).setValue5(0);
                    }
                }

                if ((list.get(j).getValue6()) != 0) {
                    if (list.get(j).getValue6() >= min && list.get(j).getValue6() <= max) {
                        try {
                            EmpAPICall = "partqty_data_add?ref_batch_id=" + URLEncoder.encode("" + batch.get(jComboBox_batch.getSelectedIndex()).batchId, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBoxOrders.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_part_id=" + URLEncoder.encode("" + product.get(jComboBox_FG.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_prtqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prtqp_value=" + URLEncoder.encode("" + Float.parseFloat("" + list.get(j).getValue6()), "UTF-8") + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog(null, result1);
                            list.get(j).setValue6(0);
                            count++;
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    } else {
                        notSubmitedd++;
                        list.get(j).setValue6(0);
                    }
                }
                if ((list.get(j).getValue7()) != 0) 
                {

                    if (list.get(j).getValue7() >= min && list.get(j).getValue7() <= max) {
                        try {
                            EmpAPICall = "partqty_data_add?ref_batch_id=" + URLEncoder.encode("" + batch.get(jComboBox_batch.getSelectedIndex()).batchId, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBoxOrders.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_part_id=" + URLEncoder.encode("" + product.get(jComboBox_FG.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_prtqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prtqp_value=" + URLEncoder.encode("" + Float.parseFloat("" + list.get(j).getValue7()), "UTF-8") + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            // JOptionPane.showMessageDialog(null, result1);
                            list.get(j).setValue7(0);
                            count++;
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    } else {
                        notSubmitedd++;
                        list.get(j).setValue7(0);
                    }
                }
                if ((list.get(j).getValue8()) != 0) {
                    if (list.get(j).getValue8() >= min && list.get(j).getValue8() <= max) {
                        try {
                            EmpAPICall = "partqty_data_add?ref_batch_id=" + URLEncoder.encode("" + batch.get(jComboBox_batch.getSelectedIndex()).batchId, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBoxOrders.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_part_id=" + URLEncoder.encode("" + product.get(jComboBox_FG.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_prtqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prtqp_value=" + URLEncoder.encode("" + Float.parseFloat("" + list.get(j).getValue8()), "UTF-8") + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog(null, result1);
                            list.get(j).setValue8(0);
                            count++;
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    } else {
                        notSubmitedd++;
                        list.get(j).setValue8(0);
                    }
                }
                if ((list.get(j).getValue9()) != 0) {
                    if (list.get(j).getValue9() >= min && list.get(j).getValue9() <= max) {
                        try {
                            EmpAPICall = "partqty_data_add?ref_batch_id=" + URLEncoder.encode("" + batch.get(jComboBox_batch.getSelectedIndex()).batchId, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBoxOrders.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_part_id=" + URLEncoder.encode("" + product.get(jComboBox_FG.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_prtqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prtqp_value=" + URLEncoder.encode("" + Float.parseFloat("" + list.get(j).getValue9()), "UTF-8") + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog(null, result1);
                            list.get(j).setValue9(0);
                            count++;
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    } else {
                        notSubmitedd++;
                        list.get(j).setValue9(0);
                    }
                }
                if ((list.get(j).getValue10()) != 0) {
                    if (list.get(j).getValue10() >= min && list.get(j).getValue10() <= max) {
                        try {
                            EmpAPICall = "partqty_data_add?ref_batch_id=" + URLEncoder.encode("" + batch.get(jComboBox_batch.getSelectedIndex()).batchId, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBoxOrders.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_part_id=" + URLEncoder.encode("" + product.get(jComboBox_FG.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_prtqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prtqp_value=" + URLEncoder.encode("" + Float.parseFloat("" + list.get(j).getValue10()), "UTF-8") + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog(null, result1);
                            list.get(j).setValue10(0);
                            count++;
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    } else {
                        notSubmitedd++;
                        list.get(j).setValue10(0);
                    }
                }

            }

            if (notSubmitedd != 0) 
            {

                JOptionPane.showMessageDialog(null, count + " entries submitted.  " + notSubmitedd + " Out of range entries not submitted");
            } else 
            {
                JOptionPane.showMessageDialog(null, count + " entries submitted. ");
            }
        }
        fgList.reset();
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

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:

        String[] selectedRecord = docList.get(jList1.getSelectedIndex());

        String addEmpAPICall = "readrmdoc?RD_UID=" + selectedRecord[0];
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

        if (!result2.contains("not found")) {
            if (result2 != null) {

                try {

                    String filedata = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                    JSONObject responseObject = (JSONObject) new JSONTokener(filedata).nextValue();
                    String fileData = responseObject.getJSONObject("data").getString("binarydata");
                    byte[] encoded = Base64.decodeBase64(fileData);

                    String tempFileName = responseObject.getJSONObject("data").getString("name");
                    File downloaded = new File(tempFileName);
                    FileUtils.writeByteArrayToFile(downloaded, encoded);
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(downloaded);

                } catch (IOException ex) {
                    //    Logger.getLogger(FinishedGoodMaster.class.getName()).log(Level.SEVERE, null, ex);
                    //StaticValues.writer.writeExcel ( DocumentRepository.class.getSimpleName () , DocumentRepository.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

            }
        }
    }//GEN-LAST:event_jList1MouseClicked

    public void loadDocumentsForFG(int _selectedEmpRecordId) {

        String addEmpAPICall = "rdm";
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        String docTypeIds = "";
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
            JSONArray records = st.getJSONArray("records");
            //String[] docTypeIds = new String[];

            JSONObject emp = null;
            //docList = new ArrayList<String[]> ();
            for (int i = 0; i < records.length(); i++) {

                emp = records.getJSONObject(i);

                if (emp.getString("RDM_NAME").contains("Part - ")) {
                    //docList.add( new String[]{ emp.getString ("RDM_UID"),emp.getString ("RDM_NAME")  }   ); 
                    docTypeIds = docTypeIds + emp.get("RDM_UID").toString() + ",";
                }
            }
        }

        String[] docTypeIdsArr = docTypeIds.split(",");
        DefaultListModel<String> docsList = null;
        docList = new ArrayList<String[]>();
        docsList = new DefaultListModel<String>();
        for (int i = 0; i < docTypeIdsArr.length; i++) {

            if (!docTypeIdsArr[i].equals("")) {
                addEmpAPICall = "refdocs?RDM_UID=" + docTypeIdsArr[i];
                result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

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
                    JSONArray records = st.getJSONArray("records");
                    //String[] docTypeIds = new String[];

                    JSONObject emp = null;

                    for (int j = 0; j < records.length(); j++) {

                        emp = records.getJSONObject(j);

                        if (emp.get("item_id") != null) {
                            if (emp.get("item_id").equals(_selectedEmpRecordId)) {
                                //if(   emp.getString ("item_id").equals( _selectedEmpRecordId )){
                                docList.add(new String[]{emp.get("RD_UID").toString(), emp.get("RD_NAME").toString()});
                                docsList.addElement(emp.getString("RD_NAME"));
                            }
                        }
                    }
                }
            }
        }
        try {
            jList1.setModel(docsList);

        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            // hideDialog();
        }

    }
    ArrayList<QualityDataModel> selectedparam = new ArrayList<QualityDataModel>();

    class QualityParamList extends javax.swing.JPanel {

        ResultSet result = null;

        public ArrayList<QualityDataPanel> paramList = new ArrayList<>();
        private ArrayList<QualityDataModel> paramDetailList = new ArrayList<QualityDataModel>();

        /**
         * Creates new form ProdDataConformationScreen
         */
        /*    public QualityParamList () {
         initComponents ();
         ParamList panel = new ParamList ();
         panel.setBounds ( 0 , 0 , 700 , 260 );
         panel.setBackground ( Color.WHITE );
         add ( panel );
         }
         */
        public QualityParamList(int id) {
            initComponents();
            if (paramList.size() != 0) {
                paramList.clear();
            }

            ParamList panel = new ParamList(id);
            panel.setBounds(0, 0, 700, 260);
            panel.setBackground(Color.white);
            add(panel);

        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {

            jButton1 = new javax.swing.JButton();
            jButton2 = new javax.swing.JButton();
            jLabel1 = new javax.swing.JLabel();

            setBackground(new java.awt.Color(255, 255, 255));
            setBorder(javax.swing.BorderFactory.createTitledBorder(""));
            setPreferredSize(new java.awt.Dimension(700, 260));
            setLayout(null);

        }// </editor-fold>                        

        public ArrayList<QualityDataModel> getList() {
            // TODO add your handling code here:
            QualityDataModel tlm = null;

            int totalRejection = 0;

            for (int i = 0; i < paramList.size(); i++) {

                tlm = new QualityDataModel();
                if (paramList.get(i).getValue1() > 0) {
                    tlm.setId(paramList.get(i).getId());
                    tlm.setParam_name(paramList.get(i).getParam_name());
                    tlm.setValue1(paramList.get(i).getValue1());
                    tlm.setValue2(paramList.get(i).getValue2());
                    tlm.setValue3(paramList.get(i).getValue3());
                    tlm.setValue4(paramList.get(i).getValue4());
                    tlm.setValue5(paramList.get(i).getValue5());
                    tlm.setValue6(paramList.get(i).getValue6());
                    tlm.setValue7(paramList.get(i).getValue7());
                    tlm.setValue8(paramList.get(i).getValue8());
                    tlm.setValue9(paramList.get(i).getValue9());
                    tlm.setValue10(paramList.get(i).getValue10());
                    paramDetailList.add(tlm);
                }
            }

            QualityDataModel tlm2 = null;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < paramDetailList.size(); i++) {
                tlm2 = paramDetailList.get(i);

                sb.append(tlm2.getId());
                sb.append(tlm2.getParam_name());
                sb.append(tlm2.getValue1());
                sb.append(tlm2.getValue2());
                sb.append(tlm2.getValue3());
                sb.append(tlm2.getValue4());
                sb.append(tlm2.getValue5());
                sb.append(tlm2.getValue6());
                sb.append(tlm2.getValue7());
                sb.append(tlm2.getValue8());
                sb.append(tlm2.getValue9());
                sb.append(tlm2.getValue10());
                sb.append("\n");
            }

            selectedparam = paramDetailList;

            return selectedparam;
        }

        public void reset() {
            for (int i = 0; i < paramList.size(); i++) {

                if (paramList.get(i).getValue1() > 0.0) {
                    paramList.get(i).setValue1(Float.valueOf("0.0"));
                }
                if (paramList.get(i).getValue2() > 0.0) {
                    paramList.get(i).setValue2(Float.valueOf("0.0"));
                }
                if (paramList.get(i).getValue3() > 0.0) {
                    paramList.get(i).setValue3(Float.valueOf("0.0"));
                }
                if (paramList.get(i).getValue4() > 0.0) {
                    paramList.get(i).setValue4(Float.valueOf("0.0"));
                }
                if (paramList.get(i).getValue5() > 0.0) {
                    paramList.get(i).setValue5(Float.valueOf("0.0"));
                }
                if (paramList.get(i).getValue6() > 0.0) {
                    paramList.get(i).setValue6(Float.valueOf("0.0"));
                }
                if (paramList.get(i).getValue7() > 0.0) {
                    paramList.get(i).setValue7(Float.valueOf("0.0"));
                }
                if (paramList.get(i).getValue8() > 0.0) {
                    paramList.get(i).setValue8(Float.valueOf("0.0"));
                }
                if (paramList.get(i).getValue9() > 0.0) {
                    paramList.get(i).setValue9(Float.valueOf("0.0"));
                }
                if (paramList.get(i).getValue10() > 0.0) {
                    paramList.get(i).setValue10(Float.valueOf("0.0"));
                }

            }

        }

        // Variables declaration - do not modify                     
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        // End of variables declaration                   

        class ParamList extends JPanel {

            private JPanel panel;

            private JScrollPane scroll;
            private JButton btnAddType;
            /* public ParamList()
             {
             setLayout ( new BorderLayout () );

             panel = new JPanel ();
             panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );

             scroll = new JScrollPane ( panel ,
             JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
             JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );

             add ( scroll , BorderLayout.CENTER );

             setVisible ( true );
             }*/

            public ParamList(int id) {
                setLayout(new BorderLayout());

                panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                scroll = new JScrollPane(panel,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                add(scroll, BorderLayout.CENTER);

                setVisible(true);

                QualityDataPanel pane = null;

                //paramList = new ArrayList<QualityDataPanel>();
                addEmpAPICall = "partqty_masters?fg_id=" + id;
                String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

                if (!result2.contains("not found") && result2.contains("success")) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jObject = new JSONObject(result2);
                    Iterator<?> keys = jObject.keys();

                    if (!result2.contains("not found") && result2.contains("success")) {
                        map = new HashMap<String, Object>();
                        jObject = new JSONObject(result2);
                        keys = jObject.keys();

                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            Object value = jObject.get(key);
                            map.put(key, value);
                        }

                        JSONObject st = (JSONObject) map.get("data");
                        JSONArray records = st.getJSONArray("records");

                        JSONObject emp = null;
                        list = new ArrayList<Rejection_Reasons>();
                        panel.setBounds(0, 0, 250, 40 * records.length());
                        for (int i = 0; i < records.length(); i++) {

                            emp = records.getJSONObject(i);

                            // list.add ( new Rejection_Reasons ( Integer.parseInt ( emp.get ( "param_id" ).toString () ) , emp.get ( "param_name" ).toString () ) );
                            pane = new QualityDataPanel();
                            pane.setBounds(0, (i * 40), 250, 40);
                            pane.setBackground(Color.WHITE);
                            pane.setId(Integer.parseInt(emp.get("part_param_name").toString()));
                            pane.setParam_name(emp.get("param_name").toString());
                            pane.setStd(Float.parseFloat(emp.get("standard_value").toString()));
                            pane.setLower(Float.parseFloat(emp.get("LLL").toString()));
                            pane.setUpper(Float.parseFloat(emp.get("ULL").toString()));
                            pane.setValue1(0);
                            pane.setValue2(0);
                            pane.setValue3(0);
                            pane.setValue4(0);
                            pane.setValue5(0);
                            pane.setValue6(0);
                            pane.setValue7(0);
                            pane.setValue8(0);
                            pane.setValue9(0);
                            pane.setValue10(0);
                            panel.add(pane);
                            panel.repaint();
                            panel.revalidate();
                            paramList.add(pane);
                        }

                    }

                }
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Cancel;
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_reset;
    private datechooser.beans.DateChooserCombo dateChooserCombo;
    private javax.swing.JComboBox jComboBoxOrders;
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
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
