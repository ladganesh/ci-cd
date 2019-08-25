/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.Batch;
import trixware.erp.prodezydesktop.Model.ProcessDR;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.QualityDataModel;
import trixware.erp.prodezydesktop.Model.Rejection_Reasons;
import trixware.erp.prodezydesktop.Model.SalesOrderDr;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.components.QualityDataPanel;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Harshali
 */
//Created By Harshali  Process Quality Entry
public class ProcessQualityEntryForm extends javax.swing.JPanel {

    String result2;
    ArrayList<ProcessDR> process = null;
    ArrayList<ProductDR> product = new ArrayList<>();
    QualityParamList proclist = null;
    ArrayList<SalesOrderDr> order = null;
    ArrayList<Batch> batch = null;
    ArrayList<Rejection_Reasons> list = null;
    String addEmpAPICall;

    /**
     * Creates new form ProcessQualityEntryForm
     */
    public ProcessQualityEntryForm() {
        initComponents();
        this.setBackground(Color.WHITE);
        repaint();
        revalidate();
        loadContent();
        if (order != null && !order.isEmpty()) {
            load_products(order.get(0).PO_ID);
            if (product != null && !product.isEmpty()) {
                load_process(product.get(0).FG_ID);

                if (process != null && !process.isEmpty()) {
                    proclist = new QualityParamList(process.get(0).PRC_ID);
                    proclist.setBounds(60, 220, 700, 260);
                    add(proclist);
                }
            }
        }

    }

    public void loadContent() {

        addEmpAPICall = "salesorders";
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
                    jComboBox_order.addItem(emp.getString("order_no") + " ( " + date[0] + " )");
                }
                jComboBox_order.addActionListener(orderComboAction);

            }

        }

    }
    ActionListener orderComboAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (proclist != null) {
                proclist.removeAll();
                remove(proclist);
            }
            repaint();
            revalidate();
            load_products(order.get(jComboBox_order.getSelectedIndex()).PO_ID);

            try {
                if (!product.isEmpty() && product != null) {
                    load_process(product.get(0).FG_ID);

                    if (process != null && !process.isEmpty()) {
                        proclist = new QualityParamList(process.get(0).PRC_ID);
                        proclist.setBounds(60, 220, 700, 260);
                        add(proclist);
                    }
                }
            } catch (Exception d) {
                d.getMessage();
            }
        }
    };

    ActionListener FgComboAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (proclist != null) {
                proclist.removeAll();
                remove(proclist);
                repaint();
                revalidate();
            }
            if (!product.isEmpty() && product != null) {

                load_process(product.get(jComboBox_Fg.getSelectedIndex()).FG_ID);

                if (process != null && !process.isEmpty()) {
                    proclist = new QualityParamList(process.get(jComboBox_Proc.getSelectedIndex()).PRC_ID);
                    proclist.setBounds(60, 220, 700, 260);
                    add(proclist);
                } else {
                    // for(int k=0;k<Integer.parseInt(proclist.size().toString());k++)
                    if (proclist != null) {
                        proclist.removeAll();
                        remove(proclist);
                        proclist.repaint();
                        proclist.revalidate();
                        repaint();
                        revalidate();
                    }

                }
            }

        }
    };

    public void load_products(int po_id) {

        if (jComboBox_Fg.getItemCount() != 0) {
            jComboBox_Fg.removeActionListener(FgComboAction);
            jComboBox_Fg.removeAllItems();

        }

        // product=null;
        if (product.size() != 0) {
            product.clear();
        }
        if (jComboBox_Proc.getItemCount() != 0) {
            jComboBox_Proc.removeActionListener(procComboAction);
            jComboBox_Proc.removeAllItems();
        }
        addEmpAPICall = "podetails?ref_po_id=" + po_id;
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        System.out.println(result2);
        ArrayList<String> product1 = null;
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
                product1 = new ArrayList<String>();
                for (int i = 0; i < records.length(); i++) {
                    emp = records.getJSONObject(i);

                    product1.add(emp.get("product_id").toString());

                }
            }

        }
        if (product1 != null && !product1.isEmpty()) {
            //product = new ArrayList<ProductDR> ();
            jComboBox_Fg.removeActionListener(FgComboAction);
            for (int j = 0; j < product1.size(); j++) {
                addEmpAPICall = "finishedgoodsedit?FG_ID=" + product1.get(j);
                result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                System.out.println(result2);

                if (result2 != null && !result2.contains("not found")) {

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
                        jComboBox_Fg.addItem(emp.getString("FG_PART_NO"));
                    }

                }

            }
            jComboBox_Fg.addActionListener(FgComboAction);
            //load_process(product.get(jComboBox_Fg.getSelectedIndex()).FG_ID);

        } else {
            if (jComboBox_Proc.getItemCount() != 0) {
                jComboBox_Proc.removeActionListener(procComboAction);
                jComboBox_Proc.removeAllItems();
            }
        }

    }

    public void load_process(int fg_id) {

        if (jComboBox_Proc.getItemCount() != 0) {
            jComboBox_Proc.removeActionListener(procComboAction);
            jComboBox_Proc.removeAllItems();
            if (!process.isEmpty() && process != null) {
                process.clear();
            }
            if (proclist != null) {
                this.proclist.removeAll();
                this.remove(proclist);
                proclist.repaint();
                proclist.revalidate();
            }
        }
        addEmpAPICall = "processmachmaps?FG_ID=" + fg_id;
        result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
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
            process = new ArrayList<ProcessDR>();
            for (int i = 0; i < records.length(); i++) {
                emp = records.getJSONObject(i);
                ProcessDR rm = new ProcessDR();
                rm.PRC_ID = emp.getInt("FPM_ID");
                rm.PRC_NAME = emp.getString("PROCESS_NAME");

                process.add(rm);
                jComboBox_Proc.addItem(emp.getString("PROCESS_NAME"));
            }
            jComboBox_Proc.addActionListener(procComboAction);

        } else {
            if (proclist != null) {
                proclist.removeAll();
                this.remove(proclist);
                proclist.repaint();
                proclist.revalidate();
                this.repaint();
                this.revalidate();
            }
        }

    }
    ActionListener procComboAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                if (proclist != null) {
                    proclist.removeAll();
                    remove(proclist);
                    repaint();
                    revalidate();
                }
                if (!process.isEmpty() && process != null) {

                    proclist = new QualityParamList(process.get(jComboBox_Proc.getSelectedIndex()).PRC_ID);
                    proclist.setBounds(60, 220, 700, 260);
                    add(proclist);
                }
            } catch (Exception u) {
                u.getMessage();
            }

        }
    };

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox_Fg = new javax.swing.JComboBox();
        dateChooserCombo = new datechooser.beans.DateChooserCombo();
        jComboBox_Proc = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btn_add = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        btn_Cancel = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jComboBox_order = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(900, 300));

        dateChooserCombo.setCalendarPreferredSize(new java.awt.Dimension(320, 220));

        jComboBox_Proc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_ProcActionPerformed(evt);
            }
        });

        jLabel1.setText("Date");

        jLabel2.setText("Part No / Code");

        jLabel3.setText("Proccess");

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

        jLabel6.setText("Orders No.");

        jLabel7.setText("Bench Mark");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateChooserCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_order, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(118, 118, 118)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox_Proc, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_Fg, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btn_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(132, 132, 132)
                        .addComponent(jLabel5)))
                .addContainerGap(133, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateChooserCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox_Fg, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_Proc, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_order, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // TODO add your handling code here:

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<QualityDataModel> list = proclist.getList();
        String result = null;
        if (list.size() != 0) {
            int size = list.size();
            int count = 0, notSubmitedd = 0;
            for (int j = 0; j < list.size(); j++) {
                Float min = null, max = null, min1, max1;
                DecimalFormat df = new DecimalFormat("#,###,##0.0000");
                addEmpAPICall = "proc_qty_master?proc_id=" + process.get(jComboBox_Proc.getSelectedIndex()).PRC_ID
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
                            EmpAPICall = "proc_qty_data_add?ref_fg_id=" + URLEncoder.encode("" + product.get(jComboBox_Fg.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBox_order.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_proc_id=" + URLEncoder.encode("" + process.get(jComboBox_Proc.getSelectedIndex()).PRC_ID, "UTF-8")
                                    + "&ref_prcqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prcqp_value=" + URLEncoder.encode("" + list.get(j).getValue1(), "UTF-8")
                                    + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog ( null , result1 );
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
                            EmpAPICall = "proc_qty_data_add?ref_fg_id=" + URLEncoder.encode("" + product.get(jComboBox_Fg.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBox_order.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_proc_id=" + URLEncoder.encode("" + process.get(jComboBox_Proc.getSelectedIndex()).PRC_ID, "UTF-8")
                                    + "&ref_prcqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prcqp_value=" + URLEncoder.encode("" + list.get(j).getValue2(), "UTF-8")
                                    + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog ( null , result1 );
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
                            EmpAPICall = "proc_qty_data_add?ref_fg_id=" + URLEncoder.encode("" + product.get(jComboBox_Fg.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBox_order.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_proc_id=" + URLEncoder.encode("" + process.get(jComboBox_Proc.getSelectedIndex()).PRC_ID, "UTF-8")
                                    + "&ref_prcqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prcqp_value=" + URLEncoder.encode("" + list.get(j).getValue3(), "UTF-8")
                                    + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog ( null , result1 );
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
                            EmpAPICall = "proc_qty_data_add?ref_fg_id=" + URLEncoder.encode("" + product.get(jComboBox_Fg.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBox_order.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_proc_id=" + URLEncoder.encode("" + process.get(jComboBox_Proc.getSelectedIndex()).PRC_ID, "UTF-8")
                                    + "&ref_prcqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prcqp_value=" + URLEncoder.encode("" + list.get(j).getValue4(), "UTF-8")
                                    + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog ( null , result1 );
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
                            EmpAPICall = "proc_qty_data_add?ref_fg_id=" + URLEncoder.encode("" + product.get(jComboBox_Fg.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBox_order.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_proc_id=" + URLEncoder.encode("" + process.get(jComboBox_Proc.getSelectedIndex()).PRC_ID, "UTF-8")
                                    + "&ref_prcqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prcqp_value=" + URLEncoder.encode("" + list.get(j).getValue5(), "UTF-8")
                                    + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog ( null , result1 );
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
                            EmpAPICall = "proc_qty_data_add?ref_fg_id=" + URLEncoder.encode("" + product.get(jComboBox_Fg.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBox_order.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_proc_id=" + URLEncoder.encode("" + process.get(jComboBox_Proc.getSelectedIndex()).PRC_ID, "UTF-8")
                                    + "&ref_prcqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prcqp_value=" + URLEncoder.encode("" + list.get(j).getValue6(), "UTF-8")
                                    + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog ( null , result1 );
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
                if ((list.get(j).getValue7()) != 0) {
                    if (list.get(j).getValue7() >= min && list.get(j).getValue7() <= max) {
                        try {
                            EmpAPICall = "proc_qty_data_add?ref_fg_id=" + URLEncoder.encode("" + product.get(jComboBox_Fg.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBox_order.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_proc_id=" + URLEncoder.encode("" + process.get(jComboBox_Proc.getSelectedIndex()).PRC_ID, "UTF-8")
                                    + "&ref_prcqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prcqp_value=" + URLEncoder.encode("" + list.get(j).getValue7(), "UTF-8")
                                    + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog ( null , result1 );
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
                            EmpAPICall = "proc_qty_data_add?ref_fg_id=" + URLEncoder.encode("" + product.get(jComboBox_Fg.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBox_order.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_proc_id=" + URLEncoder.encode("" + process.get(jComboBox_Proc.getSelectedIndex()).PRC_ID, "UTF-8")
                                    + "&ref_prcqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prcqp_value=" + URLEncoder.encode("" + list.get(j).getValue8(), "UTF-8")
                                    + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog ( null , result1 );
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
                            EmpAPICall = "proc_qty_data_add?ref_fg_id=" + URLEncoder.encode("" + product.get(jComboBox_Fg.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBox_order.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_proc_id=" + URLEncoder.encode("" + process.get(jComboBox_Proc.getSelectedIndex()).PRC_ID, "UTF-8")
                                    + "&ref_prcqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prcqp_value=" + URLEncoder.encode("" + list.get(j).getValue9(), "UTF-8")
                                    + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //JOptionPane.showMessageDialog ( null , result1 );
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
                            EmpAPICall = "proc_qty_data_add?ref_fg_id=" + URLEncoder.encode("" + product.get(jComboBox_Fg.getSelectedIndex()).FG_ID, "UTF-8")
                                    + "&ref_order_id=" + URLEncoder.encode("" + order.get(jComboBox_order.getSelectedIndex()).PO_ID, "UTF-8")
                                    + "&ref_proc_id=" + URLEncoder.encode("" + process.get(jComboBox_Proc.getSelectedIndex()).PRC_ID, "UTF-8")
                                    + "&ref_prcqp_id=" + URLEncoder.encode(id + "", "UTF-8")
                                    + "&ref_prcqp_value=" + URLEncoder.encode("" + list.get(j).getValue10(), "UTF-8")
                                    + "&insp_date=" + URLEncoder.encode(Date1 + "", "UTF-8");
                            result = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                            //  JOptionPane.showMessageDialog ( null , result1 );
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
        proclist.reset();
    }//GEN-LAST:event_btn_addActionPerformed

    private void jComboBox_ProcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_ProcActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_ProcActionPerformed

    ArrayList<QualityDataModel> selectedparam = new ArrayList<QualityDataModel>();

    class QualityParamList extends javax.swing.JPanel {

        ResultSet result = null;

        public ArrayList<QualityDataPanel> paramList = null;
        private ArrayList<QualityDataModel> paramDetailList = new ArrayList<QualityDataModel>();

        /**
         * Creates new form ProdDataConformationScreen
         */
        public QualityParamList(int id) {
            initComponents();

            ParamList panel = new ParamList(id);
            panel.setBounds(0, 0, 700, 260);
            panel.setBackground(Color.WHITE);
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
                sb.append(tlm2.getId());
                sb.append(tlm2.getParam_name());
                sb.append("\n");
            }

            selectedparam = paramDetailList;
            //setproclists (proclistDetailList );

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

//    public ArrayList<RMQtyPanel> rmBomList = null ;
            public ParamList(int id) {
                // getContentPane().setLayout(new BorderLayout());       

                setLayout(new BorderLayout());

                panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                scroll = new JScrollPane(panel,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                add(scroll, BorderLayout.CENTER);

                setVisible(true);

                QualityDataPanel pane = null;

                paramList = new ArrayList<QualityDataPanel>();

                addEmpAPICall = "proc_qty_master?proc_id=" + id;
                String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

                if (!result2.contains("not found") && result2.contains("success")) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jObject = new JSONObject(result2);
                    Iterator<?> keys = jObject.keys();

                   // if (!result2.contains("not found") && result2.contains("success")) {
                      //  map = new HashMap<String, Object>();
                      //  jObject = new JSONObject(result2);
                       // keys = jObject.keys();

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

                            //list.add ( new Rejection_Reasons ( Integer.parseInt ( emp.get ( "param_id" ).toString () ) , emp.get ( "param_name" ).toString () ) );
                            pane = new QualityDataPanel();

                            pane.setBounds(0, (i * 40), 250, 40);
                            pane.setBackground(Color.WHITE);
                            pane.setId(Integer.parseInt(emp.get("proc_param_name").toString()));
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
                            panel.revalidate();
                            paramList.add(pane);

                        }
                        panel.repaint();
                        panel.revalidate();

                    }

              //  }
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Cancel;
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_reset;
    private datechooser.beans.DateChooserCombo dateChooserCombo;
    private javax.swing.JComboBox jComboBox_Fg;
    private javax.swing.JComboBox jComboBox_Proc;
    private javax.swing.JComboBox jComboBox_order;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
