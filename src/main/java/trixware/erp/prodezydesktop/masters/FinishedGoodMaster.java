/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import trixware.erp.prodezydesktop.Model.QualityPartDR;
import trixware.erp.prodezydesktop.components.QualityPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileSystemView;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import trixware.erp.prodezydesktop.Model.Main_typeModel_quality;
import trixware.erp.prodezydesktop.Model.Maintain_typeDR;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.StaticValues;
import static trixware.erp.prodezydesktop.examples.HomeScreen.home;
import trixware.erp.prodezydesktop.Utilities.Child_Parts;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.MyCustomFilter;
import trixware.erp.prodezydesktop.Utilities.UpdateBOMDialog;
import trixware.erp.prodezydesktop.components.ProgressDialog;
import trixware.erp.prodezydesktop.examples.HomeScreen3;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class FinishedGoodMaster extends javax.swing.JPanel {

    String resource = "", resourceType = "";
    FG_Quality_list fg_quality = null, fg_quality1 = null;
    ArrayList<String[]> UOM = null;
    ArrayList<String[]> UOM_selected = null;
    
    ArrayList<String[]> categories = new ArrayList<String[]>();
    ArrayList<QualityPartDR> Id_Name_List = null;
    ArrayList<String[]> docList = null;
    ArrayList<Maintain_typeDR> Quality_type = null;
    ArrayList<Main_typeModel_quality> Qty_type = null;

    /**
     * Creates new form FinishedGoodMaster
     */
    public FinishedGoodMaster() {
        initComponents();
        repaint();
        revalidate();
        resourceType = "Masters";
        resource = "FinishedGoodMaster";

        jScrollPane1.setVisible(false);
        jLabel12.setVisible(false);

//        fg_quality = new FG_Quality_list();
//        fg_quality.setBounds(420, 30, 300, 160);
//        add(fg_quality);


//        try {
//            StaticValues.checkUserRolePermission ( resourceType , resourceType );
//        } catch ( Exception e ) {
//            StaticValues.writer.writeExcel ( resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }

        jComboBox1.addActionListener(selectUnitAction);

        jTextField19.addKeyListener(k);
        jTextField5.addKeyListener(k);
        jTextField19.addFocusListener(f2);
        jTextField5.addFocusListener(f2);

        //Category Rm/part
        if (jComboBox2.getItemCount() == 0) {
            String addEmpAPICall = "category";
            String CategoryResult = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
            if (!CategoryResult.contains("not found")) {

                HashMap<String, Object> map = new HashMap<String, Object>();
                JSONObject jObject = new JSONObject(CategoryResult);
                Iterator<?> keys = jObject.keys();

                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    Object value = jObject.get(key);
                    map.put(key, value);
                }

                JSONObject st = (JSONObject) map.get("data");
                JSONArray records = st.getJSONArray("records");

                JSONObject emp = null;
                categories = new ArrayList<String[]>();
                for (int i = 0; i < records.length(); i++) {

                    emp = records.getJSONObject(i);
                    jComboBox2.addItem(emp.get("Category").toString());
                    categories.add(new String[]{emp.get("Category_ID").toString(), emp.get("Category").toString()});
                }

            }
        }
        jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane2.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        jScrollPane3.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane3.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
//*******************Role wise priority set edited by mayur****************************************       
//        int role;
//        role=Integer.parseInt(Proman_User.getRole());
//        
//        switch (role) {
//            case 1: 
//                
                jButton10.setEnabled(false);
                jButton13.setEnabled(false);
                jButton8.setEnabled(true);
                jButton1.setEnabled(true);
//                
//            break;
//
//            case 2:
//                jButton10.setEnabled(false);
//                jButton13.setEnabled(false);
//                jButton8.setEnabled(true);
//                jButton1.setEnabled(true);
//            break;
//
//            case 3:   
//                jButton10.setEnabled(false);
//                jButton13.setEnabled(false);
//                jButton8.setEnabled(false);
//                jButton1.setEnabled(false);
//            break;
//
//            default:
//        }
//        
        //*******************end Role wise priority set edited by mayur****************************************

    }

    ArrayList<String[]> machines;
    ArrayList<String[]> customers;

    public void loadContent_OLD() {

        ResultSet result = null;
        try {

            result = DB_Operations.getMasters("uom");

            if (result != null) {
                UOM = new ArrayList<String[]>();
                while (result.next()) {
                    //     jComboBox2.addItem ( result.getString ( "UOM" ) );
                    jLabel4.setText(result.getString("UOM"));
                    jComboBox1.addItem(result.getString("UOM"));
                    //jComboBox8.addItem ( result.getString ( "UOM" ) );
                    jLabel6.setText(result.getString("UOM"));
                    UOM.add(new String[]{result.getString("UOM_ID"), result.getString("UOM")});
                }
            } else {
                jComboBox1.addItem("Not Available");
                jLabel4.setText(result.getString("UOM"));
                jLabel6.setText(result.getString("UOM"));
            }

            result = DB_Operations.getMasters("finished_goods");
            jTable1.setModel(buildTableModel(result));
            jTable1.getColumnModel().getColumn(0).setMinWidth(0);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
            jTable1.getColumnModel().getColumn(10).setMinWidth(0);
            jTable1.getColumnModel().getColumn(10).setMaxWidth(0);

            result.close();

            result.close();

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            StaticValues.writer.writeExcel(FinishedGoodMaster.class.getSimpleName(), FinishedGoodMaster.class.getSimpleName(), e.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
        }

    }

    public void loadContent() {

        String addEmpAPICall = "finishedgoods";
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

        if (!result2.contains("not found")) {

            if (result2 != null) {
                jTable1.setModel(buildTableModelfromJSON(result2));
                jTable1.getColumnModel().getColumn(0).setMinWidth(0);
                jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
            }
        }

        if (jComboBox1.getItemCount() == 0) {

            addEmpAPICall = "unitmeas";
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

                JSONObject emp = null;
                UOM = new ArrayList<String[]>();
                for (int i = 0; i < records.length(); i++) {

                    emp = records.getJSONObject(i);
                    jComboBox1.addItem(emp.get("UOM").toString());
                    UOM.add(new String[]{emp.get("UOM_ID").toString(), emp.get("UOM").toString()});
                }
            }
        }

    }

    public static DefaultTableModel buildTableModelfromJSON(String employeeJSONList) {

        Vector<String> columnNames = new Vector<String>();
        int columnCount = 0;
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();

        JSONArray jARR = null;
        JSONObject jOB = null;

        HashMap<String, Object> map = new HashMap<String, Object>();
        JSONObject jObject = new JSONObject(employeeJSONList);
        Iterator<?> keys = jObject.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            Object value = jObject.get(key);
            map.put(key, value);
        }

        JSONObject st = (JSONObject) map.get("data");
        JSONArray records = st.getJSONArray("records");

        JSONObject emp = null;

        columnNames = new Vector<String>();
        columnNames.add("FG_ID");
        columnNames.add("FG_PART_UNIQUE_ID");
        columnNames.add("FG_PART_NO");
        columnNames.add("PART_NAME");
        columnNames.add("SALES_RATE");
        columnNames.add("NOTES");
        columnNames.add("PROD_COST");
        columnNames.add("FG_UOM_ID");
        columnNames.add("NT_WGHT");
        columnNames.add("GRS_WGHT");
        columnNames.add("ASSEMBLED");

        // data of the table
        for (int i = 0; i < records.length(); i++) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 0; columnIndex < columnNames.size(); columnIndex++) {
                emp = records.getJSONObject(i);
                //  vector.add ( emp.get ( columnNames.get ( columnIndex ) )  );
                if (columnNames.get(columnIndex).equals("FG_UOM_ID")) {
                    vector.add(StaticValues.getUOMName(Integer.parseInt(emp.get(columnNames.get(columnIndex)).toString())));
                } else {
                    vector.add(emp.get(columnNames.get(columnIndex)));
                }
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }

    ActionListener selectUnitAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            jLabel4.setText(" / " + jComboBox1.getSelectedItem().toString());
            jLabel6.setText(" / " + jComboBox1.getSelectedItem().toString());
        }
    };

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        try {
            rs.close();
        } catch (Exception e) {
            StaticValues.writer.writeExcel(FinishedGoodMaster.class.getSimpleName(), FinishedGoodMaster.class.getSimpleName(), e.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
        }

        return new DefaultTableModel(data, columnNames);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel29 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButton3 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(976, 477));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Part Code\\No");
        add(jLabel1);
        jLabel1.setBounds(30, 20, 130, 25);

        jTextField1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField1.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField1.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField1.setNextFocusableComponent(jTextField3);
        jTextField1.setPreferredSize(new java.awt.Dimension(200, 50));
        add(jTextField1);
        jTextField1.setBounds(160, 20, 213, 25);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Part Name");
        add(jLabel2);
        jLabel2.setBounds(30, 80, 130, 25);

        jTextField2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField2.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField2.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField2.setNextFocusableComponent(jTextField5);
        jTextField2.setPreferredSize(new java.awt.Dimension(200, 50));
        add(jTextField2);
        jTextField2.setBounds(160, 80, 213, 25);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Sales Rate /unit");
        add(jLabel5);
        jLabel5.setBounds(30, 180, 130, 25);

        jTextField5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField5.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField5.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField5.setNextFocusableComponent(jTextField19);
        jTextField5.setPreferredSize(new java.awt.Dimension(200, 50));
        add(jTextField5);
        jTextField5.setBounds(160, 180, 120, 25);

        jButton6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton6.setText("Add to Master");
        jButton6.setOpaque(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        add(jButton6);
        jButton6.setBounds(810, 20, 140, 30);

        jButton7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton7.setText("Cancel");
        jButton7.setOpaque(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        add(jButton7);
        jButton7.setBounds(810, 50, 140, 30);

        jTable1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        add(jScrollPane2);
        jScrollPane2.setBounds(20, 340, 930, 220);

        jButton8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton8.setText("Export to Excel");
        jButton8.setOpaque(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        add(jButton8);
        jButton8.setBounds(810, 200, 140, 30);

        jLabel22.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel22.setText("Part Unique Id");
        add(jLabel22);
        jLabel22.setBounds(30, 50, 130, 25);

        jTextField3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField3.setMaximumSize(new java.awt.Dimension(200, 50));
        jTextField3.setMinimumSize(new java.awt.Dimension(200, 50));
        jTextField3.setNextFocusableComponent(jTextField2);
        jTextField3.setPreferredSize(new java.awt.Dimension(200, 50));
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        add(jTextField3);
        jTextField3.setBounds(160, 50, 213, 25);

        jButton9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton9.setText("Reset");
        jButton9.setOpaque(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        add(jButton9);
        jButton9.setBounds(810, 80, 140, 30);

        jButton10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton10.setText("Edit Record");
        jButton10.setEnabled(false);
        jButton10.setOpaque(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        add(jButton10);
        jButton10.setBounds(810, 110, 140, 30);

        jButton13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton13.setText("Update BOM");
        jButton13.setEnabled(false);
        jButton13.setOpaque(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        add(jButton13);
        jButton13.setBounds(810, 170, 140, 30);

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 0, 51));
        jLabel28.setText("*");
        add(jLabel28);
        jLabel28.setBounds(400, 170, 30, 20);

        jLabel25.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel25.setText("Product Note");
        add(jLabel25);
        jLabel25.setBounds(30, 270, 120, 16);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        add(jScrollPane3);
        jScrollPane3.setBounds(160, 270, 210, 50);

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 0, 51));
        jLabel29.setText("*");
        add(jLabel29);
        jLabel29.setBounds(20, 30, 20, 10);
        add(jTextField19);
        jTextField19.setBounds(540, 170, 120, 30);

        jLabel30.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel30.setText("<html>Production <br>Cost / unit</html>");
        add(jLabel30);
        jLabel30.setBounds(410, 170, 130, 30);

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 0, 51));
        jLabel31.setText("*");
        add(jLabel31);
        jLabel31.setBounds(20, 90, 20, 10);

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 0, 51));
        jLabel32.setText("*");
        add(jLabel32);
        jLabel32.setBounds(20, 180, 10, 24);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Import Excel (xls)");
        jButton1.setOpaque(false);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(810, 230, 140, 32);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        add(jComboBox1);
        jComboBox1.setBounds(160, 150, 210, 26);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Measure");
        add(jLabel3);
        jLabel3.setBounds(30, 150, 80, 20);
        add(jLabel4);
        jLabel4.setBounds(290, 190, 80, 30);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("jLabel6");
        add(jLabel6);
        jLabel6.setBounds(670, 170, 80, 30);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Delete");
        jButton2.setOpaque(false);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(810, 260, 140, 32);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jComboBox2);
        jComboBox2.setBounds(540, 130, 210, 26);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("Category RM/Part");
        add(jLabel7);
        jLabel7.setBounds(410, 130, 120, 20);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("Net Weight");
        add(jLabel8);
        jLabel8.setBounds(30, 210, 130, 25);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("Gross Weight");
        add(jLabel9);
        jLabel9.setBounds(30, 240, 130, 25);

        jTextField4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jTextField4);
        jTextField4.setBounds(160, 210, 120, 25);

        jTextField6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jTextField6);
        jTextField6.setBounds(160, 240, 120, 25);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setText("in Grams");
        add(jLabel10);
        jLabel10.setBounds(290, 250, 70, 16);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("in Grams");
        add(jLabel11);
        jLabel11.setBounds(290, 220, 70, 16);

        jList1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        add(jScrollPane1);
        jScrollPane1.setBounds(540, 20, 210, 100);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("<html>Reference<br>Documents</html>");
        add(jLabel12);
        jLabel12.setBounds(410, 20, 130, 32);

        jLabel13.setText("Quality Param");
        add(jLabel13);
        jLabel13.setBounds(420, 10, 80, 0);

        jLabel14.setText("Bench-Mark");
        add(jLabel14);
        jLabel14.setBounds(510, 10, 70, 0);

        jLabel15.setText("LSL");
        add(jLabel15);
        jLabel15.setBounds(630, 10, 40, 0);

        jLabel16.setText("USL");
        add(jLabel16);
        jLabel16.setBounds(670, 10, 50, 0);

        jLabel17.setText("UOM");
        add(jLabel17);
        jLabel17.setBounds(580, 10, 50, 0);

        jLabel18.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel18.setText("Type");
        add(jLabel18);
        jLabel18.setBounds(30, 120, 70, 16);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jRadioButton1.setText("Assembled");
        jRadioButton1.setOpaque(false);
        add(jRadioButton1);
        jRadioButton1.setBounds(160, 120, 90, 20);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jRadioButton2.setText("Child");
        jRadioButton2.setOpaque(false);
        add(jRadioButton2);
        jRadioButton2.setBounds(280, 120, 90, 20);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("View BOM");
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(810, 140, 140, 30);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        try {
            Calendar c2 = Calendar.getInstance();
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate2 = sdf2.format(c2.getTime());

            String CATEGORY = categories.get(jComboBox2.getSelectedIndex())[0];

            String FG_PART_NO, FG_PART_UNIQUE_ID, PART_NAME, SALES_RATE, CREATED_ON, EDITED_ON, EDITED_BY, NOTES, PROD_COST, NTWGT, GRSWGT;

            Boolean assembled = jRadioButton1.isSelected();
            
            FG_PART_NO = jTextField1.getText();
            FG_PART_UNIQUE_ID = jTextField3.getText();
            PART_NAME = jTextField2.getText();
            SALES_RATE = jTextField5.getText();
            PROD_COST = jTextField19.getText();
            FG_PART_UNIQUE_ID = jTextField3.getText();
            NOTES = jTextArea1.getText().trim();

            NTWGT = jTextField4.getText().trim();
            GRSWGT = jTextField6.getText().trim();

            CREATED_ON = strDate2;
            EDITED_ON = strDate2;
            EDITED_BY = Proman_User.getUsername();
            ArrayList<Main_typeModel_quality> listDetails = fg_quality.getUllLLL();

            int unit = Integer.parseInt(UOM.get(jComboBox1.getSelectedIndex())[0]);

            // &&  ! FG_PART_UNIQUE_ID.equalsIgnoreCase ( "" )
            //&&  ! NOTES.equalsIgnoreCase ( "" )
            if (!FG_PART_NO.equalsIgnoreCase("")
                    && !PART_NAME.equalsIgnoreCase("")) {

                //String result = DB_Operations.insertIntoFinishedGoodsMaster( FG_PART_NO , FG_PART_UNIQUE_ID , PART_NAME , CUSTOMER , MOQ , SALES_RATE , GST_RATE , PACK_SIZE , MLD_CAVIT , TGT_OP_TXT , PART_DRG , RBR_BTC_NO , NT_WGHT , GRS_WGHT , OP_SKILL_LEVEL , IMPORTANCE , PART_VALID_DOC , PART_PPAP , PART_IMG , MODL_SOP , TESTNG_ID , INSP_FRQ , PREF_MCH , MTL_INST_IMG , CREATED_ON , EDITED_ON , EDITED_BY , NOTES, PROD_COST);
                String result = DB_Operations.insertIntoFinishedGoodsMaster(FG_PART_NO, FG_PART_UNIQUE_ID, PART_NAME, SALES_RATE, CREATED_ON, EDITED_ON, EDITED_BY, NOTES, PROD_COST, unit);

                try {
//                    String addEmpAPICall = "finishedgoodsadd?FG_PART_NO=" + URLEncoder.encode(FG_PART_NO, "UTF-8") + "&FG_PART_UNIQUE_ID=" + URLEncoder.encode(FG_PART_UNIQUE_ID, "UTF-8")
//                            + "&PART_NAME=" + URLEncoder.encode(PART_NAME, "UTF-8") + "&SALES_RATE=" + URLEncoder.encode(SALES_RATE, "UTF-8") + "&CREATED_ON=" + URLEncoder.encode(CREATED_ON, "UTF-8")
//                            + "&EDITED_ON=" + URLEncoder.encode(EDITED_ON, "UTF-8") + "&EDITED_BY=" + URLEncoder.encode(EDITED_BY, "UTF-8") + "&NOTES=" + URLEncoder.encode(NOTES, "UTF-8")
//                            + "&PROD_COST=" + URLEncoder.encode(PROD_COST, "UTF-8") + "&FG_UOM_ID=" + URLEncoder.encode(unit + "", "UTF-8") + "&PART_VALID_DOC=" + URLEncoder.encode(CATEGORY + "", "UTF-8")
//                            + "&NT_WGHT=" + URLEncoder.encode(NTWGT + "", "UTF-8") + "&GRS_WGHT=" + URLEncoder.encode(GRSWGT + "", "UTF-8");

                    String addEmpAPICall;

                    if(assembled)
                    {
                         addEmpAPICall = "finishedgoodsadd?FG_PART_NO=" + URLEncoder.encode ( FG_PART_NO , "UTF-8" ) + "&FG_PART_UNIQUE_ID=" + URLEncoder.encode ( FG_PART_UNIQUE_ID , "UTF-8" ) 
                            + "&PART_NAME=" + URLEncoder.encode ( PART_NAME , "UTF-8" ) + "&SALES_RATE=" + URLEncoder.encode ( SALES_RATE , "UTF-8" ) + "&CREATED_ON=" + URLEncoder.encode ( CREATED_ON , "UTF-8" ) 
                            + "&EDITED_ON=" + URLEncoder.encode ( EDITED_ON , "UTF-8" ) + "&EDITED_BY=" + URLEncoder.encode ( EDITED_BY , "UTF-8" ) + "&NOTES=" + URLEncoder.encode ( NOTES , "UTF-8" ) 
                            + "&PROD_COST=" + URLEncoder.encode ( PROD_COST , "UTF-8" ) + "&FG_UOM_ID=" + URLEncoder.encode ( unit + "" , "UTF-8" )+ "&ASSEMBLED=" + URLEncoder.encode ( 1 + "" , "UTF-8" )
                            + "&PART_VALID_DOC=" + URLEncoder.encode ( CATEGORY + "" , "UTF-8" )    + "&NT_WGHT=" + URLEncoder.encode ( NTWGT + "" , "UTF-8" )+ "&GRS_WGHT=" + URLEncoder.encode ( GRSWGT + "" , "UTF-8" ) ;
                    }
                    else
                    {
                         addEmpAPICall = "finishedgoodsadd?FG_PART_NO=" + URLEncoder.encode ( FG_PART_NO , "UTF-8" ) + "&FG_PART_UNIQUE_ID=" + URLEncoder.encode ( FG_PART_UNIQUE_ID , "UTF-8" ) 
                            + "&PART_NAME=" + URLEncoder.encode ( PART_NAME , "UTF-8" ) + "&SALES_RATE=" + URLEncoder.encode ( SALES_RATE , "UTF-8" ) + "&CREATED_ON=" + URLEncoder.encode ( CREATED_ON , "UTF-8" ) 
                            + "&EDITED_ON=" + URLEncoder.encode ( EDITED_ON , "UTF-8" ) + "&EDITED_BY=" + URLEncoder.encode ( EDITED_BY , "UTF-8" ) + "&NOTES=" + URLEncoder.encode ( NOTES , "UTF-8" ) 
                            + "&PROD_COST=" + URLEncoder.encode ( PROD_COST , "UTF-8" ) + "&FG_UOM_ID=" + URLEncoder.encode ( unit + "" , "UTF-8" )+ "&ASSEMBLED=" + URLEncoder.encode ( 0 + "" , "UTF-8" )
                            + "&PART_VALID_DOC=" + URLEncoder.encode ( CATEGORY + "" , "UTF-8" )    + "&NT_WGHT=" + URLEncoder.encode ( NTWGT + "" , "UTF-8" )+ "&GRS_WGHT=" + URLEncoder.encode ( GRSWGT + "" , "UTF-8" ) ;
                    }


                    String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                    JOptionPane.showMessageDialog(null, result2);
                    //******************Created By Harshali   FG Qualityn Master Entry
               /*     if (!result2.equals("null") && result2.contains("insert_id")) {

                        JSONObject jb = new JSONObject(result2);
                        String status = jb.getString("status");
                        JSONObject jsb = new JSONObject(result2);
                        JSONObject js = jsb.getJSONObject("data");
                        int s = js.getInt("insert_id");
                        if (status.contains("success")) {
                            if (listDetails.size() != 0) {
                                for (int j = 0; j < listDetails.size(); j++) {
                                    if ((listDetails.get(j).getLll()) != 0 && (listDetails.get(j).getUll()) != 0) {
                                        int id = listDetails.get(j).getId();
                                        Float std_mark = listDetails.get(j).getStd_mark();
                                        float Ll = listDetails.get(j).getLll();
                                        float ul = listDetails.get(j).getUll();
                                        String name = listDetails.get(j).getName();
                                        int uom_id = listDetails.get(j).getUom();
                                        String EmpAPICall = null;
                                        try {
                                            EmpAPICall = "partqty_master_add?ref_fg_id=" + URLEncoder.encode("" + s, "UTF-8") + "&part_param_name=" + URLEncoder.encode(id + "", "UTF-8") + "&part_p_mandatory=" + URLEncoder.encode("" + 1, "UTF-8") + "&standard_value=" + URLEncoder.encode(std_mark + "", "UTF-8") + "&uom_id=" + URLEncoder.encode(uom_id + "", "UTF-8") + "&LLL=" + URLEncoder.encode(Ll + "", "UTF-8") + "&ULL=" + URLEncoder.encode(ul + "", "UTF-8");
                                            String result1 = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                                            JOptionPane.showMessageDialog(null, result1);
                                        } catch (Exception e) {
                                            e.getMessage();
                                        }
                                    }

                                }
                            }

                        }
                    }*/

                    //*********************************
                } catch (UnsupportedEncodingException e) {

                }

                ///if (  ! result.contains ( "Error" ) ) {
                //   JOptionPane.showMessageDialog ( null , result );
                loadContent();
                fg_quality.reset();
                resetFields();
               // } else {
                //     JOptionPane.showMessageDialog ( null , result );
                //  }

            } else {
                JOptionPane.showMessageDialog(null, "Please fill all fields marked with * ");
            }
        } catch (Exception exx) {
            System.out.println(" " + exx.getMessage());
            StaticValues.writer.writeExcel(FinishedGoodMaster.class.getSimpleName(), FinishedGoodMaster.class.getSimpleName(), exx.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", exx.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    public void resetFields() {

        jTextField1.setText("");
        jTextField3.setText("");
        jTextField2.setText("");
        jTextArea1.setText("");
        jTextField5.setText("");
        jTextField19.setText("");

    }

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        showDialog1();
//        Workbook wb = new HSSFWorkbook ();
//
//        ArrayList<String> column_names = new ArrayList<String> ();
//        ArrayList<String[]> values = new ArrayList<String[]> ();
//        int columnCount = 0;
//
//        File file = null;
//
//        try {
//            ResultSet result = DB_Operations.getMasters ( "finished_goods" );
//            if ( result != null ) {
//                ResultSetMetaData metaData = result.getMetaData ();
//                // names of columns
//                columnCount = metaData.getColumnCount ();
//                for ( int column = 1 ; column <= columnCount ; column ++ ) {
//                    column_names.add ( metaData.getColumnName ( column ) );
//                }
//            }
//            // data of the table
//            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
//            while ( result.next () ) {
//                String[] val = new String[ columnCount ];
//                for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {
//
//                    val[ columnIndex - 1 ] = String.valueOf ( result.getObject ( columnIndex ) );
//
//                }
//                values.add ( val );
//            }
//        } catch ( SQLException e ) {
//            StaticValues.writer.writeExcel ( FinishedGoodMaster.class.getSimpleName () , FinishedGoodMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }
//
//        // Cell style for header row
//        CellStyle cs = wb.createCellStyle ();
//        //cs.setFillForegroundColor(HSSFColor.LIME.index);
//        //cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//
//        CellStyle cs2 = wb.createCellStyle ();
//        cs2.setFillForegroundColor ( HSSFColor.ROSE.index );
//        cs2.setFillPattern ( HSSFCellStyle.SOLID_FOREGROUND );
//
//        // New Sheet
//        Sheet sheet1 = null;
//        sheet1 = wb.createSheet ( "Finished Goods Master" );
//        sheet1.setColumnWidth ( 0 , ( 15 * 500 ) );
//
//        Cell c = null;
//        Row row = sheet1.createRow ( 1 );
//
//        int i = 1;
//        //add column names to the firt row of excel
//        row = sheet1.createRow ( i ++ );
//        for ( int a = 0 ; a < column_names.size () ; a ++ ) {
//            c = row.createCell ( a );
//            // c.setCellValue(cursor.getString(a));
//            c.setCellValue ( column_names.get ( a ) );
//            c.setCellStyle ( cs );
//        }
//
//        for ( int j = 0 ; j < values.size () ; j ++ ) {
//            String[] rowValues = values.get ( j );
//            Row row4 = sheet1.createRow ( i ++ );
//            for ( int k = 0 ; k < rowValues.length ; k ++ ) {
//                {
//
//                    c = row4.createCell ( k );
//                    c.setCellValue ( rowValues[ k ] );
//                    c.setCellStyle ( cs );
//                }
//            }
//            Calendar c2 = Calendar.getInstance ();
//            SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
//            String strDate2 = sdf2.format ( c2.getTime () );
//            file = new File ( "Finished Goods Report File " + strDate2 + ".xls" );
//
//            FileOutputStream os = null;
//
//            try {
//                os = new FileOutputStream ( file );
//                wb.write ( os );
//                wb.close ();
//                os.close ();
//
//            } catch ( IOException e ) {
//                JOptionPane.showMessageDialog ( null , "Error " + e.getMessage () );
//                StaticValues.writer.writeExcel ( FinishedGoodMaster.class.getSimpleName () , FinishedGoodMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//            } finally {
//
//            }
//        }
//        JOptionPane.showMessageDialog ( null , "New File generated at " + file.getAbsolutePath () );
    }//GEN-LAST:event_jButton8ActionPerformed

    ProgressDialog progress = new ProgressDialog(null);
    Thread getTableAndFilesContent = null;

    public boolean showDialog() {
        getTableAndFilesContent = new Thread() {
            public void run() {
                showValuesFromTable();
            }
        };
        getTableAndFilesContent.start();
        progress.openProgressWindow();
        return true;
    }

    public boolean hideDialog() {
        progress.closeProgressWindow();
        return true;
    }


    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:

            showDialog ();
            
            
            
            jButton6.setEnabled ( false );
            //jButton13.setEnabled ( true );
            
            jButton10.setEnabled ( true );
        
    }//GEN-LAST:event_jTable1MouseClicked

    public void showValuesFromTable() {

        progress.updateTitle("Showing selected part and reference files");
        progress.updateMessage("Getting selected part information");
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int selectedRowIndex = jTable1.getSelectedRow();

        selectedFGId = Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString());

        int assemble ;
        
        try {

            jTextField3.setText(model.getValueAt(selectedRowIndex, 1).toString());
            jTextField1.setText(model.getValueAt(selectedRowIndex, 2).toString());
            jTextField2.setText(model.getValueAt(selectedRowIndex, 3).toString());
            jTextField5.setText(model.getValueAt(selectedRowIndex, 4).toString());
            jTextField19.setText(model.getValueAt(selectedRowIndex, 6).toString());
            jTextArea1.setText(model.getValueAt(selectedRowIndex, 5).toString());

            jTextField4.setText(model.getValueAt(selectedRowIndex, 9).toString());
            // jTextField6.setText ( model.getValueAt ( selectedRowIndex , 10 ).toString () );

            
                assemble = Integer.parseInt( model.getValueAt(selectedRowIndex, 10).toString());
              
                if(assemble == 1)
                {
                    jRadioButton1.setSelected(true);
                    jButton13.setEnabled(true);
                    jButton3.setEnabled(true);
                    
                }else
                {
                    jRadioButton2.setSelected(true);
                    jButton13.setEnabled(false);
                    jButton3.setEnabled(false);
                    
                }
            
            
            for (int i = 0; i < jComboBox1.getItemCount() - 1; i++) {
                if (jComboBox1.getItemAt( i ).toString().equals(model.getValueAt(selectedRowIndex, 7).toString())) {
                    jComboBox1.setSelectedIndex(i);
                }
            }
            
            loadDocumentsForRM(selectedFGId);
            
            /*  String addEmpAPICall = "partqty_masters?fg_id="+selectedFGId;
             String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
             if(!result2.contains("not found" ))
             {

            this.fg_quality.removeAll();
            this.remove(fg_quality);
            if (fg_quality1 != null) {
                this.remove(fg_quality1);
                // fg_quality1.maintainTypeList1.clear();
            }
            revalidate();
            repaint();

            fg_quality1 = new FG_Quality_list(selectedFGId);
            fg_quality1.setBounds(420, 30, 300, 160);
            add(fg_quality1);
         // cddds

            /*}
             else
             {
             this.remove(fg_quality);
             if(fg_quality1 != null)
             {       
             fg_quality1.removeAll();
            
             }
             //fg_quality1.removeAll();
             repaint();
             revalidate();
             }*/
            
            
            

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            hideDialog();
        }

    }

    public void loadDocumentsForRM(int _selectedEmpRecordId) {

        progress.updateMessage("Getting reference files information");

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
        } else {
            progress.updateMessage("No File Found");
            hideDialog();

            // progress.disable();
        }

        String[] docTypeIdsArr = docTypeIds.split(",");
        DefaultListModel<String> docsList = null;
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
                    docList = new ArrayList<String[]>();
                    docsList = new DefaultListModel<String>();
                    for (int j = 0; j < records.length(); j++) {

                        emp = records.getJSONObject(j);

                        if (emp.get("item_id") != null) 
                        {
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

            if (docsList.size() > 0) {
                jScrollPane1.setVisible(true);
                jLabel12.setVisible(true);
                //hideDialog();
                System.out.println ( " Showing documents " );                
            } else {
                jScrollPane1.setVisible(false);
                jLabel12.setVisible(false);
                //  hideDialog();
                System.out.println ( " NOT Showing documents " );                
            }
        } catch (Exception ex) {
            System.err.println(ex);
            System.out.println ( " ERROR Showing documents " );                
        } finally {
            hideDialog();
        }

    }


    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        resetFields();
        if (fg_quality != null) {
            fg_quality.removeAll();
            remove(fg_quality);
        }
        if (fg_quality1 != null) {
            fg_quality1.removeAll();
            remove(fg_quality1);
        }
        repaint();
        revalidate();
        
        //fg_quality = new FG_Quality_list();
        //fg_quality.setBounds(420, 30, 300, 160);
        //add(fg_quality);        
        
        jButton6.setEnabled(true);
        jButton10.setEnabled(false);
        jButton13.setEnabled(false);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        home.removeForms();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        String CATEGORY;

        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String strDate2 = sdf2.format(c2.getTime());

        CATEGORY = categories.get(jComboBox2.getSelectedIndex())[0];

        String FG_PART_NO, FG_PART_UNIQUE_ID, CUSTOMER, PART_NAME, MOQ, SALES_RATE, GST_RATE, PACK_SIZE, MLD_CAVIT, TGT_OP_TXT, PART_DRG, RBR_BTC_NO, NT_WGHT, GRS_WGHT, OP_SKILL_LEVEL, IMPORTANCE, PART_VALID_DOC, PART_PPAP, PART_IMG, MODL_SOP, TESTNG_ID, INSP_FRQ, PREF_MCH, MTL_INST_IMG, PART_UNIQUE_ID, CREATED_ON, EDITED_ON, EDITED_BY, NOTES, PROD_COST;
        int ID;

        Boolean assembled = jRadioButton1.isSelected();
        
        FG_PART_NO = jTextField1.getText();
        FG_PART_UNIQUE_ID = jTextField3.getText();
        PART_NAME = jTextField2.getText();
        SALES_RATE = jTextField5.getText();
        PROD_COST = jTextField19.getText();
        NOTES = jTextArea1.getText().trim();
        NT_WGHT = jTextField4.getText();
        GRS_WGHT = jTextField6.getText();
        ID = selectedFGId;
        EDITED_ON = strDate2;
        EDITED_BY = Proman_User.getUsername();
        //ArrayList<Main_typeModel_quality> listDetails = fg_quality1.getUllLLL_update();

        int unit = Integer.parseInt(UOM.get(jComboBox1.getSelectedIndex())[0]);
//&&  ! FG_PART_UNIQUE_ID.equalsIgnoreCase ( "" )
// &&  ! NOTES.equalsIgnoreCase ( "" )
        if (!FG_PART_NO.equalsIgnoreCase("")
                && !PART_NAME.equalsIgnoreCase("")) {

            String result = DB_Operations.updateFinishedGoodsMaster(FG_PART_NO, FG_PART_UNIQUE_ID, PART_NAME, SALES_RATE, EDITED_ON, EDITED_BY, PROD_COST, NOTES, unit, ID);

            try {
//                String addEmpAPICall = "finishedgoodsupdate?FG_ID=" + selectedFGId + "&FG_PART_NO=" + URLEncoder.encode(FG_PART_NO, "UTF-8") + "&FG_PART_UNIQUE_ID=" + URLEncoder.encode(FG_PART_UNIQUE_ID, "UTF-8")
//                        + "&PART_NAME=" + URLEncoder.encode(PART_NAME, "UTF-8") + "&SALES_RATE=" + URLEncoder.encode(SALES_RATE, "UTF-8") + "&CREATED_ON=&EDITED_ON=&EDITED_BY=&NOTES=" + URLEncoder.encode(NOTES, "UTF-8")
//                        + "&PROD_COST=" + URLEncoder.encode(PROD_COST, "UTF-8") + "&FG_UOM_ID=" + URLEncoder.encode(unit + "", "UTF-8") + "&PART_VALID_DOC=" + URLEncoder.encode(CATEGORY + "", "UTF-8")
//                        + "&NT_WGHT=" + URLEncoder.encode(NT_WGHT + "", "UTF-8") + "&GRS_WGHT=" + URLEncoder.encode(GRS_WGHT + "", "UTF-8");

                String addEmpAPICall = null;
                if(assembled)
                {
                     addEmpAPICall = "finishedgoodsupdate?FG_ID="+selectedFGId+"&FG_PART_NO=" + URLEncoder.encode ( FG_PART_NO , "UTF-8" ) + "&FG_PART_UNIQUE_ID=" + URLEncoder.encode ( FG_PART_UNIQUE_ID , "UTF-8" ) 
                            + "&PART_NAME=" + URLEncoder.encode ( PART_NAME , "UTF-8" ) + "&SALES_RATE=" + URLEncoder.encode ( SALES_RATE , "UTF-8" ) + "&CREATED_ON=&EDITED_ON=&EDITED_BY=&NOTES=" + URLEncoder.encode ( NOTES , "UTF-8" ) 
                            + "&PROD_COST=" + URLEncoder.encode ( PROD_COST , "UTF-8" ) + "&FG_UOM_ID=" + URLEncoder.encode ( unit + "" , "UTF-8" )+ "&ASSEMBLED=" + URLEncoder.encode ( 1 + "" , "UTF-8" )+ "&PART_VALID_DOC=" + URLEncoder.encode ( CATEGORY + "" , "UTF-8" )
                            + "&NT_WGHT=" + URLEncoder.encode ( NT_WGHT + "" , "UTF-8" )+ "&GRS_WGHT=" + URLEncoder.encode ( GRS_WGHT + "" , "UTF-8" )  ;
                }
                else{
                     addEmpAPICall = "finishedgoodsupdate?FG_ID="+selectedFGId+"&FG_PART_NO=" + URLEncoder.encode ( FG_PART_NO , "UTF-8" ) + "&FG_PART_UNIQUE_ID=" + URLEncoder.encode ( FG_PART_UNIQUE_ID , "UTF-8" ) 
                            + "&PART_NAME=" + URLEncoder.encode ( PART_NAME , "UTF-8" ) + "&SALES_RATE=" + URLEncoder.encode ( SALES_RATE , "UTF-8" ) + "&CREATED_ON=&EDITED_ON=&EDITED_BY=&NOTES=" + URLEncoder.encode ( NOTES , "UTF-8" ) 
                            + "&PROD_COST=" + URLEncoder.encode ( PROD_COST , "UTF-8" ) + "&FG_UOM_ID=" + URLEncoder.encode ( unit + "" , "UTF-8" )+ "&ASSEMBLED=" + URLEncoder.encode ( 0 + "" , "UTF-8" )+ "&PART_VALID_DOC=" + URLEncoder.encode ( CATEGORY + "" , "UTF-8" )
                            + "&NT_WGHT=" + URLEncoder.encode ( NT_WGHT + "" , "UTF-8" )+ "&GRS_WGHT=" + URLEncoder.encode ( GRS_WGHT + "" , "UTF-8" )  ;
                }


                String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                JOptionPane.showMessageDialog(null, result2);

                //****************Created By HArshali
           /*     if (result2.contains("Finish Goods updated successfully")) {

                    if (!listDetails.isEmpty()) {
                        for (int j = 0; j < listDetails.size(); j++) {
                            if ((listDetails.get(j).getLll()) != 0 && (listDetails.get(j).getUll()) != 0) {
                                int record_id = listDetails.get(j).getRecord_id();
                                int id = listDetails.get(j).getId();
                                Float std_mark = listDetails.get(j).getStd_mark();
                                float Ll = listDetails.get(j).getLll();
                                float ul = listDetails.get(j).getUll();
                                String name = listDetails.get(j).getName();
                                int uom_id = listDetails.get(j).getUom();
                                String EmpAPICall = null;
                                if(record_id != 0)
                                {    
                                try {
                                    EmpAPICall = "partqty_master_update?part_param_id=" + record_id + "&fg_id=" + URLEncoder.encode("" + selectedFGId, "UTF-8") + "&part_param_name=" + URLEncoder.encode(id + "", "UTF-8") + "&part_p_mandatory=" + URLEncoder.encode("" + 1, "UTF-8") + "&standard_value=" + URLEncoder.encode(std_mark + "", "UTF-8") + "&uom_id=" + URLEncoder.encode(uom_id + "", "UTF-8") + "&LLL=" + URLEncoder.encode(Ll + "", "UTF-8") + "&ULL=" + URLEncoder.encode(ul + "", "UTF-8");
                                    String result1 = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                                    JOptionPane.showMessageDialog(null, result1);
                                } catch (Exception e) {
                                    e.getMessage();
                                }
                                }
                                else
                                {
                                      try {
                                            EmpAPICall = "partqty_master_add?ref_fg_id=" + URLEncoder.encode("" + selectedFGId, "UTF-8") + "&part_param_name=" + URLEncoder.encode(id + "", "UTF-8") + "&part_p_mandatory=" + URLEncoder.encode("" + 1, "UTF-8") + "&standard_value=" + URLEncoder.encode(std_mark + "", "UTF-8") + "&uom_id=" + URLEncoder.encode(uom_id + "", "UTF-8") + "&LLL=" + URLEncoder.encode(Ll + "", "UTF-8") + "&ULL=" + URLEncoder.encode(ul + "", "UTF-8");
                                            String result1 = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                                            JOptionPane.showMessageDialog(null, result1);
                                        } catch (Exception e) {
                                            e.getMessage();
                                        }   
                                }
                                
                                
                            }

                        }
                    }

                }*/
                //*****************
            } catch (UnsupportedEncodingException e) {

            }

            JOptionPane.showMessageDialog(null, "" + result);
            loadContent();
            //fg_quality1.reset1();
            resetFields();

        } else {
            JOptionPane.showMessageDialog(null, "Please fill all fields appropriately");
        }

    }//GEN-LAST:event_jButton10ActionPerformed

    KeyListener k = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            char enter = e.getKeyChar();
            String dot = Character.toString(enter);

            if (!(Character.isDigit(enter)) && !dot.equals(".")) {
                e.consume();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }
    };

    FocusListener f2 = new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = (JTextField) e.getSource();
            // jcb.setText ( "" );

        }

        @Override
        public void focusLost(FocusEvent e) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To Search Google or type URL
//change body of generated methods, choose Tools | Templates.
            JTextField jcb = (JTextField) e.getSource();
            String x = jcb.getText().trim();

            if (x.equalsIgnoreCase("")) {

            }

            try {
                int num = Integer.parseInt(String.valueOf(jcb.getText().toString()));
                if (num < 0 || num == 0) {
                    jcb.setText("");
                }

            } catch (NumberFormatException ex1) {
                jcb.setText(x);
                StaticValues.writer.writeExcel(FinishedGoodMaster.class.getSimpleName(), FinishedGoodMaster.class.getSimpleName(), ex1.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", ex1.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
            }
        }
    };

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:

        child = new Child_Parts(HomeScreen3.home , selectedFGId );
        child.setVisible ( true );
        
//        bom = new UpdateBOMDialog(HomeScreen3.home, selectedFGId);
//        bom.setVisible(true);

    }//GEN-LAST:event_jButton13ActionPerformed

    ArrayList<String[]> uploadData = new ArrayList<String[]>();
    int uploadedCount = 0;

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:

        uploadData = new ArrayList<String[]>();

        File selectedFile = null;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new MyCustomFilter("excel 97-2003"));
        fileChooser.setCurrentDirectory(new File(System.getProperty(
                "user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {

            selectedFile = fileChooser.getSelectedFile();
        }

        FileChannel inputChannel = null;
        FileChannel outputChannel = null;

        File dir = new File("dataupload\\");
        dir.mkdirs();

        try {
            inputChannel = new FileInputStream(selectedFile).getChannel();
            outputChannel = new FileOutputStream(new File(dir, selectedFile.getName())).getChannel();

            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

            inputChannel.close();
            outputChannel.close();

        } catch (FileNotFoundException fnfe) {
        } catch (IOException ioe) {
        }

        String url;
        try {

            url = "jdbc:sqlite:" + StaticValues.dbName;
            Connection con = DriverManager.getConnection(url);
            Statement stm = con.createStatement();
            PreparedStatement pst;

            FileInputStream fis;
            //String query = "INSERT INTO xl_import_test ( ID, name, address, phone, email) VALUES ( ?,?,?,?,?)";
            String query = "INSERT INTO finished_goods ( FG_PART_UNIQUE_ID,  FG_PART_NO, PART_NAME ) VALUES ( ?,?,?  )";

            // pst = con.prepareStatement ( query );
            fis = new FileInputStream(new File("dataupload\\" + selectedFile.getName()));

            HSSFWorkbook my_xls_workbook = new HSSFWorkbook(fis);
            HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);

            Iterator<Row> rowIterator = my_worksheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                String[] dataRow = new String[8];

                try {
                    //  pst.setString ( 1 , row.getCell ( 0 ).getStringCellValue () );
                    dataRow[0] = row.getCell(0).getStringCellValue();
                } catch (Exception e2) {
                    try {
                        //      pst.setInt ( 1 , ( int ) row.getCell ( 0 ).getNumericCellValue () );
                        dataRow[0] = row.getCell(0).getNumericCellValue() + "";

                    } catch (Exception e1) {
                    }
                }

                try {
                    //    pst.setString ( 2 , row.getCell ( 1 ).getStringCellValue () );
                    dataRow[1] = row.getCell(1).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        //        pst.setInt ( 2 , ( int ) row.getCell ( 1 ).getNumericCellValue () );
                        dataRow[1] = row.getCell(1).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }

                try {
                    //    pst.setString ( 3 , row.getCell ( 2 ).getStringCellValue () );
                    dataRow[2] = row.getCell(2).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        //        pst.setInt ( 3 , ( int ) row.getCell ( 2 ).getNumericCellValue () );
                        dataRow[2] = row.getCell(2).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }

                try {
                    //    pst.setString ( 3 , row.getCell ( 2 ).getStringCellValue () );
                    dataRow[3] = row.getCell(3).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        //        pst.setInt ( 3 , ( int ) row.getCell ( 2 ).getNumericCellValue () );
                        dataRow[3] = row.getCell(3).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }

                try {
                    //    pst.setString ( 4 , row.getCell ( 3 ).getStringCellValue () );
                    dataRow[4] = row.getCell(4).getStringCellValue();
                } catch (Exception e2) {
                    try {
                        //        pst.setInt ( 4 , ( int ) row.getCell ( 3 ).getNumericCellValue () );
                        dataRow[4] = row.getCell(4).getNumericCellValue() + "";

                    } catch (Exception e1) {
                    }
                }

                try {
                    //    pst.setString ( 5 , row.getCell ( 4 ).getStringCellValue () );
                    dataRow[5] = row.getCell(5).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        //        pst.setInt (5 , ( int ) row.getCell ( 4 ).getNumericCellValue () );
                        dataRow[5] = row.getCell(5).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }

                try {
                    //    pst.setString ( 5 , row.getCell ( 4 ).getStringCellValue () );
                    dataRow[6] = row.getCell(6).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        //        pst.setInt (5 , ( int ) row.getCell ( 4 ).getNumericCellValue () );
                        dataRow[6] = row.getCell(6).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }

                try {
                    //    pst.setString ( 5 , row.getCell ( 4 ).getStringCellValue () );
                    dataRow[7] = row.getCell(7).getStringCellValue() + "";
                } catch (Exception e2) {
                    try {
                        //        pst.setInt (5 , ( int ) row.getCell ( 4 ).getNumericCellValue () );
                        dataRow[7] = row.getCell(7).getNumericCellValue() + "";
                    } catch (Exception e1) {
                    }
                }

                uploadData.add(dataRow);

                try {
                    String addEmpAPICall = "finishedgoodsadd?FG_PART_NO=" + URLEncoder.encode(dataRow[1], "UTF-8") + "&FG_PART_UNIQUE_ID=" + URLEncoder.encode(dataRow[0], "UTF-8") + "&PART_NAME=" + URLEncoder.encode(dataRow[2], "UTF-8")
                            + "&SALES_RATE=" + URLEncoder.encode(dataRow[3], "UTF-8") + "&PROD_COST=" + URLEncoder.encode(dataRow[4], "UTF-8") + "&FG_UOM_ID=" + URLEncoder.encode(dataRow[5], "UTF-8")
                            + "&NT_WGHT=" + URLEncoder.encode(dataRow[6], "UTF-8") + "&GRS_WGHT=" + URLEncoder.encode(dataRow[7], "UTF-8");
                    String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                    uploadedCount++;

                } catch (UnsupportedEncodingException e) {

                }

                //  pst.addBatch ();
            }

            String addEmpAPICall = "finishedgoods";
            String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

            if (!result2.contains("not found")) {

                if (result2 != null) {
                    jTable1.setModel(buildTableModelfromJSON(result2));
                    jTable1.getColumnModel().getColumn(0).setMinWidth(0);
                    jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
                }
            }
            JOptionPane.showMessageDialog(null, uploadedCount + " records uploaded in Finished Good Master");

            fis.close();

        } catch (SQLException ex) {
            System.out.println("SQL  " + ex.getMessage());
            StaticValues.writer.writeExcel(FinishedGoodMaster.class.getSimpleName(), FinishedGoodMaster.class.getSimpleName(), ex.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", ex.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
        } catch (IOException ex) {
            System.out.println("IO  " + ex.getMessage());
            StaticValues.writer.writeExcel(FinishedGoodMaster.class.getSimpleName(), FinishedGoodMaster.class.getSimpleName(), ex.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", ex.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
        }

    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:

        int selection = JOptionPane.showConfirmDialog(null, "Deleting a record is not recommendedThe 'Delete' action may cause loss of any dependencies and may affect the transactional Data and Reports and make data inconsistent\nDeleting a plant will automatically delete all shifts for that plant\nPress 'No' to revert, Press 'Yes' to continue deleting", "Are you sure about deleting this record ?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (selection == JOptionPane.YES_OPTION) {
            deleteMaster("finishedgoodsdelete?FG_ID=" + selectedFGId);

            String addEmpAPICall = "finishedgoods";
            String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

            if (!result2.contains("not found")) {

                if (result2 != null) {
                    jTable1.setModel(buildTableModelfromJSON(result2));
                    jTable1.getColumnModel().getColumn(0).setMinWidth(0);
                    jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
                }
            }
        }

    }//GEN-LAST:event_jButton2MouseClicked

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged

    }//GEN-LAST:event_jList1ValueChanged

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

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        child = new Child_Parts(HomeScreen3.home );
        child.setVisible ( true );
        
    }//GEN-LAST:event_jButton3ActionPerformed

    public static Child_Parts child;
    
    public static String retreiveSuccessMessage(String json) {
        String id = "Not able to fetch the response for your request. Please refresh you master form and try again";
        JSONObject jObject = new JSONObject(json);
        id = jObject.getString("118");
        return id;
    }

    public void deleteMaster(String apiName) {
        String response = WebAPITester.prepareWebCall(apiName, StaticValues.getHeader(), "");
        JSONObject responseObject = (JSONObject) new JSONTokener(response).nextValue();
        JOptionPane.showMessageDialog(null, retreiveSuccessMessage(responseObject.getJSONObject("message").getJSONObject("success").toString()));
    }

    public static UpdateBOMDialog bom;

    public String createFileNameResource(String columnDocumentName) {

        Calendar c2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMMyyyy_HH_mm_a");
        String strDate2 = sdf2.format(c2.getTime());

        FileChannel inputChannel = null;
        FileChannel outputChannel = null;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new MyCustomFilter());
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            File dir = new File("UploadedFiles");
            dir.mkdir();

            partDrawingFile_name = columnDocumentName + "_(" + selectedFile.getName() + ") _saved_on" + strDate2 + selectedFile.getName().substring(selectedFile.getName().lastIndexOf('.'));

            try {
                inputChannel = new FileInputStream(selectedFile).getChannel();
                outputChannel = new FileOutputStream(new File(dir, partDrawingFile_name)).getChannel();
                outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

                inputChannel.close();
                outputChannel.close();

            } catch (FileNotFoundException e1) {
                StaticValues.writer.writeExcel(FinishedGoodMaster.class.getSimpleName(), FinishedGoodMaster.class.getSimpleName(), e1.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e1.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
            } catch (IOException e2) {
                StaticValues.writer.writeExcel(FinishedGoodMaster.class.getSimpleName(), FinishedGoodMaster.class.getSimpleName(), e2.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", e2.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
            }
        }
        return partDrawingFile_name;
    }

    public void openFile(String filename) {

        if (!filename.equals("")) {
            try {
                String fileName = filename;

                File dir = new File("uploadedFiles");

                File file = new File(dir, fileName);

                Desktop desktop = Desktop.getDesktop();
                if (file.exists()) {
                    desktop.open(file);
                }
            } catch (IOException ex) {
                StaticValues.writer.writeExcel(FinishedGoodMaster.class.getSimpleName(), FinishedGoodMaster.class.getSimpleName(), ex.getClass().toString(), Thread.currentThread().getStackTrace()[1].getLineNumber() + "", ex.getMessage(), StaticValues.sdf2.format(Calendar.getInstance().getTime()));
            }
        }
    }
    
     /*************************************Export data to excel Edited by mayur***************************************/

    ProgressDialog progressExcelData = new ProgressDialog( null ) ;
    Thread getTableAndFilesContent1 = null ;
    public boolean showDialog1(){
        getTableAndFilesContent1 = new Thread(){
            public void run(){
               
                exportDataExecel();
            }
        };
        getTableAndFilesContent1.start();
        progressExcelData.openProgressWindow ();
        return true ;
    }
    
    public boolean hideDialog1(){
        progressExcelData.closeProgressWindow ();
        return true ;
    } 
    
    
    public void exportDataExecel()
    {
        progressExcelData.updateTitle ( "All Finished data is fetching please wait...");
        progressExcelData.updateMessage ("Getting all Finished data");
        
        String FG_PART_NO="",  PART_NAME="",FG_PART_UNIQUE_ID="", SALES_RATE="", NTWGT="" ,GRSWGT ="", NOTES="", PROD_COST ="",  UOM="" ;
        
        int l = 2;

        Map<String, Object[]> data11;
        data11 = new TreeMap<String, Object[]>();
        
        String[] columns ={"Finished Part Code", "Part Name", "Part Unique Name", "Sale Rate/Unit", "Net Weight", "Gross Weight", "Product Notes", "Part Cost/Unit", "Category"};
        data11.put("1", new Object[]{"Finished Part Code", "Part Name", "Part Unique Name", "Sale Rate/Unit", "Net Weight", "Gross Weight", "Product Notes", "Part Cost/Unit", "Category"});
        l+=1;
        
        String result2, addEmpAPICall;

        addEmpAPICall = "finishedgoods";
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

            JSONObject emp = null;
            for (int i = 0; i < records.length(); i++) {

                emp = records.getJSONObject(i);
                
                FG_PART_NO = emp.get("FG_PART_NO").toString();
                PART_NAME= emp.get("PART_NAME").toString();
                FG_PART_UNIQUE_ID = emp.get("FG_PART_UNIQUE_ID").toString();
                SALES_RATE = emp.get("SALES_RATE").toString();
                NTWGT = emp.get("NT_WGHT").toString();
                GRSWGT = emp.get("GRS_WGHT").toString();
                NOTES = emp.get("NOTES").toString();
                PROD_COST = emp.get("PROD_COST").toString();
                UOM = emp.get("UOM").toString();
               
                
                data11.put("" + l, new Object[]{FG_PART_NO,PART_NAME,FG_PART_UNIQUE_ID,SALES_RATE,NTWGT,GRSWGT,NOTES,PROD_COST,UOM});
                l = l + 1;
            }
        }

        //hideDialog1();

        //Blank workbook
        Workbook workbook = new HSSFWorkbook();

        //Create a blank sheet
        HSSFSheet sheet = (HSSFSheet) workbook.createSheet("Finished good Master");

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        font.setColor(IndexedColors.AUTOMATIC.getIndex());
        style.setFont(font);

        //Iterate over data and write to sheet
        Set<String> keyset = data11.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data11.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                }
            }
        }
        
        // Resize all columns to fit the content size
                for (int i = 0; i < columns.length; i++) {
                    sheet.autoSizeColumn(i);
                }
                hideDialog1();
        
        File file = null;
        try {

            Calendar c2 = Calendar.getInstance();
            SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMMyyyy_HH_mm_a");
            String strDate2 = sdf2.format(c2.getTime());

            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setDialogTitle("Choose a directory to save your file: ");
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                if (jfc.getSelectedFile().isDirectory()) {
                    System.out.println("You selected the directory: " + jfc.getSelectedFile());
                }
            

            //Write the workbook in file system
            file = new File(jfc.getSelectedFile() + "\\" + "Finished goods Master Data " + strDate2 + ".xls");
            FileOutputStream fileop = new FileOutputStream(file);
            workbook.write(fileop);
            workbook.close();
            fileop.close();
            JOptionPane.showMessageDialog(null, "New File generated at " + file.getAbsolutePath());
        }
            //System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
        } catch (IOException Ie) {
            Ie.printStackTrace();
        }

        

    }
    
   
    /****************************************************************************/
     

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables

    int selectedFGId = 0;
    String partDrawingFile_name = "";
    ArrayList<Main_typeModel_quality> selectedparam = new ArrayList<Main_typeModel_quality>();

    class FG_Quality_list extends javax.swing.JPanel {

        ResultSet result = null;

        public ArrayList<QualityPanel> paramList = null;
        private ArrayList<Main_typeModel_quality> paramDetailList = new ArrayList<Main_typeModel_quality>();
        public ArrayList<QualityPanel> paramList1 = null;
        private ArrayList<Main_typeModel_quality> paramDetailList1 = new ArrayList<Main_typeModel_quality>();

        /**
         * Creates new form ProdDataConformationScreen
         */
        public FG_Quality_list() {
            initComponents();

            ParamList panel = new ParamList();
            panel.setBounds(0, 0, 300, 160);
            panel.setBackground(Color.white);
            add(panel);

        }

        public FG_Quality_list(int id) {
            initComponents();

            ParamList panel = new ParamList(id);
            panel.setBounds(0, 0, 300, 160);
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
            setPreferredSize(new java.awt.Dimension(300, 160));
            setLayout(null);

        }// </editor-fold>                        

        public ArrayList<Main_typeModel_quality> getUllLLL() {
            // TODO add your handling code here:
            Main_typeModel_quality tlm = null;

            paramDetailList = new ArrayList<Main_typeModel_quality>();
            for (int i = 0; i < paramList.size(); i++) {

                tlm = new Main_typeModel_quality();
                if (paramList.get(i).getUll() > 0.0 && paramList.get(i).getLll() > 00) {
                    tlm.setId(paramList.get(i).getId());
                    tlm.setName(paramList.get(i).getName());
                    tlm.setStd_mark(paramList.get(i).getStandard_mark());
                    tlm.setLll(paramList.get(i).getLll());
                    tlm.setUll(paramList.get(i).getUll());
                    tlm.setUom(paramList.get(i).getUom());

                    paramDetailList.add(tlm);
                }
            }

            Main_typeModel_quality tlm2 = null;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < paramDetailList.size(); i++) {
                tlm2 = paramDetailList.get(i);

                sb.append(tlm2.getLll());
                sb.append(tlm2.getUll());
                sb.append(tlm2.getStd_mark());
                sb.append(tlm2.getName());
                sb.append(tlm2.getId());
                sb.append(tlm2.getUom());
                sb.append("\n");
            }

            selectedparam = paramDetailList;

            return selectedparam;
        }

        public ArrayList<Main_typeModel_quality> getUllLLL_update() {
            // TODO add your handling code here:
            Main_typeModel_quality tlm = null;

            paramDetailList1 = new ArrayList<Main_typeModel_quality>();
            for (int i = 0; i < paramList1.size(); i++) {

                tlm = new Main_typeModel_quality();
                if (paramList1.get(i).getUll() > 0.0 && paramList1.get(i).getLll() > 00) {
                    tlm.setRecord_id(paramList1.get(i).getRecord_id1());
                    tlm.setId(paramList1.get(i).getId());
                    tlm.setName(paramList1.get(i).getName());
                    tlm.setStd_mark(paramList1.get(i).getStandard_mark());
                    tlm.setLll(paramList1.get(i).getLll());
                    tlm.setUll(paramList1.get(i).getUll());
                    tlm.setUom(paramList1.get(i).getUom());

                    paramDetailList1.add(tlm);
                }
            }

            Main_typeModel_quality tlm2 = null;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < paramDetailList1.size(); i++) {
                tlm2 = paramDetailList1.get(i);

                sb.append(tlm2.getLll());
                sb.append(tlm2.getUll());
                sb.append(tlm2.getStd_mark());
                sb.append(tlm2.getName());
                sb.append(tlm2.getId());
                sb.append(tlm2.getUom());

                sb.append("\n");
            }

            selectedparam = paramDetailList1;

            return selectedparam;
        }

        public void reset() {

            for (int i = 0; i < paramList.size(); i++) {

                if (paramList.get(i).getUll() > 0.0) {
                    paramList.get(i).setUll(Float.valueOf("0"));
                }
                if (paramList.get(i).getLll() > 0.0) {
                    paramList.get(i).setLll(Float.valueOf("0"));
                }
                if (paramList.get(i).getStandard_mark() > 0) {
                    paramList.get(i).setStandard_mark(Float.valueOf(0));
                }

            }

        }

        public void reset1() {
            for (int j = 0; j < paramList1.size(); j++) 
            {
                if (paramList1.get(j).getUll() > 0.0) {
                    paramList1.get(j).setUll(Float.valueOf("0"));
                }
                if (paramList1.get(j).getLll() > 0.0) {
                    paramList1.get(j).setLll(Float.valueOf("0"));
                }
                if (paramList1.get(j).getStandard_mark() > 0) {
                    paramList1.get(j).setStandard_mark(Float.valueOf(0));
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


            public ParamList() {
                // getContentPane().setLayout(new BorderLayout());       

                setLayout(new BorderLayout());

                panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                scroll = new JScrollPane(panel,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                add(scroll, BorderLayout.CENTER);

                setVisible(true);

                QualityPanel pane = null;
                if (paramList == null) 
                {
                    paramList = new ArrayList<QualityPanel>();
                    String EmpAPICall = "unitmeas";
                    String result2 = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

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

                        JSONObject emp = null;
                        UOM = new ArrayList<String[]>();
                        for (int i = 0; i < records.length(); i++) {

                            emp = records.getJSONObject(i);
                            //jComboBox1.addItem ( emp.get ( "UOM" ).toString () );
                            UOM.add(new String[]{emp.get("UOM_ID").toString(), emp.get("UOM").toString()});
                        }
                    }

                    EmpAPICall = "partqty_u_masters";
                    result2 = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                    if (!result2.contains("not found") && result2.contains("success")) {
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

                        JSONObject emp = null;
                        //list = new ArrayList<Rejection_Reasons> ();
                        panel.setBounds(0, 0, 300, 40 * records.length());
                        Id_Name_List =new ArrayList<QualityPartDR>();
                        for (int i = 0; i < records.length(); i++) 
                        {
                            emp = records.getJSONObject(i);
                            QualityPartDR dr=new QualityPartDR();
                            dr.setPartId(emp.getInt("param_id"));
                            dr.setPartName(emp.get("param_name").toString());
                            Id_Name_List.add(dr);
                            pane = new QualityPanel();
                            pane.setBounds(0, (i * 40), 300, 40);
                            pane.setId(emp.getInt("param_id"));
                            pane.setParam_name(emp.get("param_name").toString());
                            pane.setStandard_mark(Float.valueOf(0));
                            pane.setLll(Float.valueOf("0"));
                            pane.setUll(Float.valueOf("0"));
                            pane.setUom(UOM);
                            panel.add(pane);
                            panel.revalidate();
                            paramList.add(pane);

                        }

                    }
                }

            }

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

                QualityPanel pane = null;
                if (paramList1 == null) {

                    paramList1 = new ArrayList<QualityPanel>();

                    String EmpAPICall = "partqty_masters?fg_id=" + id;
                    String result2 = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                    if (!result2.contains("not found") && result2.contains("success")) 
                    {
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

                        JSONObject emp = null;
                        
                        panel.setBounds(0, 0, 280, 40 * records.length());
                        ArrayList<Integer> id_=new ArrayList<>();
                        int h=records.length();
                        UOM_selected = new ArrayList<String[]>();
                        for (int i = 0; i < records.length(); i++) 
                        {
                        
                            emp = records.getJSONObject(i);
                            
                            
                            UOM_selected.add(new String[]{emp.get("uom_id").toString(), emp.get("UOM").toString()});
                            id_.add(Integer.parseInt(emp.get("part_param_name").toString()));
                            pane = new QualityPanel();
                            pane.setBounds(0, (i * 40), 280, 40);
                            pane.setRecord_id1(emp.getInt("part_param_id"));
                            pane.setId(Integer.parseInt(emp.get("part_param_name").toString()));
                            pane.setParam_name(emp.get("param_name").toString());
                            pane.setStandard_mark(Float.parseFloat(emp.get("standard_value").toString()));
                            pane.setLll(Float.parseFloat("" + emp.getDouble("LLL")));
                            pane.setUll(Float.parseFloat("" + emp.getDouble("ULL")));
                            pane.setUom(UOM_selected);

                            panel.add(pane);
                            paramList1.add(pane);

                        }
                       
                        for(int j = 0; j < Id_Name_List.size();j++)
                        
                        {
                          String abc= "true";
                          for(int k=0 ; k< id_.size() ; k++)
                          {
                            int a  =Id_Name_List.get(j).partId;
                            int b  =id_.get(k);
                            if(  a != b )
                            {
                              abc= abc + " true";
                            }
                            else
                            {
                              abc= abc + " false";
                            }
                          }
                          
                          if( !abc.contains("false"))
                          {
                            pane = new QualityPanel();
                            pane.setBounds(0, (h++ * 40), 280, 40);
                            pane.setRecord_id1(0);
                            pane.setId(Id_Name_List.get(j).partId);
                            pane.setParam_name(Id_Name_List.get(j).partName);
                            pane.setStandard_mark(Float.valueOf(0));
                            pane.setLll(Float.parseFloat("0"));
                            pane.setUll(Float.parseFloat("0"));
                            pane.setUom(UOM);

                            panel.add(pane);
                            paramList1.add(pane);
                          }
                        }

                    }
                    else{
                        for(int j = 0; j < Id_Name_List.size();j++)
                        {
                            pane = new QualityPanel();
                            pane.setBounds(0, (j* 40), 280, 40);
                            pane.setRecord_id1(0);
                            pane.setId(Id_Name_List.get(j).partId);
                            pane.setParam_name(Id_Name_List.get(j).partName);
                            pane.setStandard_mark(Float.valueOf(0));
                            pane.setLll(Float.parseFloat("0"));
                            pane.setUll(Float.parseFloat("0"));
                            pane.setUom(UOM);

                            panel.add(pane);
                            paramList1.add(pane);
                        }
                    }
                    
                }

            }
        }
    }
}

class ShowWaitAction extends AbstractAction {

    protected static final long SLEEP_TIME = 3 * 1000;

    public ShowWaitAction(String name) {
        super(name);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {

                // mimic some long-running process here...
                Thread.sleep(SLEEP_TIME);
                return null;
            }
        };

        Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
        final JDialog dialog = new JDialog(win, "Dialog", ModalityType.APPLICATION_MODAL);

        mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("state")) {
                    if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                        dialog.dispose();
                    }
                }
            }
        });
        mySwingWorker.execute();

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(new JLabel("Please wait......."), BorderLayout.PAGE_START);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(win);
        dialog.setVisible(true);
    }

}
