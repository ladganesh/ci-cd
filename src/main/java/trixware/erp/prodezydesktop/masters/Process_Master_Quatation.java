/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.BatchUpdateException;
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
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.MyCustomFilter;
import trixware.erp.prodezydesktop.examples.HomeScreen;
import trixware.erp.prodezydesktop.quotation.ProcessUnits;
import trixware.erp.prodezydesktop.quotation.UnitsLabelPanel;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class Process_Master_Quatation extends javax.swing.JPanel {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    String resource = "" , resourceType = "" ;
    ArrayList<String[]> UOM = null;    
    /**
     * Creates new form Process_Master_Form
     */
    public Process_Master_Quatation () {
        initComponents ();
        
        resourceType = "Masters" ;
	resource = "Process_Master_Form" ;
        
//	try{
//      		StaticValues.checkUserRolePermission(resourceType, resourceType);
//	   }catch(Exception e){
//      		StaticValues.writer.writeExcel (resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//   	   }
        
        loadContent ();
        loadComboBox();
        componentVisibility();
    }
    public void componentVisibility()
    {
        jLabel28.setVisible(false);
        jLabel3.setVisible(false);
        jLabel5.setVisible(true);
        jLabel29.setVisible(true);
        jLabel4.setVisible(false);
        jLabel26.setVisible(false);
    
        jCheckBox1.setVisible(false);
        jCheckBox2.setVisible(false);
        jComboBox1.setVisible(false);
        jTextField2.setVisible(false);

    }
    public void loadComboBox()
    {
 
        String addEmpAPICall = "unitmeas";
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

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
            jComboBox1.removeAllItems();
            for (int i = 0; i < records.length(); i++) {

                emp = records.getJSONObject(i);
                jComboBox1.addItem(emp.get("UOM").toString());
                UOM.add(new String[]{emp.get("UOM_ID").toString(), emp.get("UOM").toString()});
            }
        }
       
    }
    public void loadContent(){
        
                String addEmpAPICall = "process_qt";
                String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

                if( ! result2.contains ( "not found")  ){
                    if ( result2!= null ) {
                        jTable1.setModel ( buildTableModelfromJSON (result2 ) );
                        jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                        jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                    }
                }
                
    }

     public static DefaultTableModel buildTableModelfromJSON ( String  employeeJSONList ) {

            Vector<String> columnNames = new Vector<String> ();
            int columnCount = 0;
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        
            JSONArray jARR = null;
            JSONObject jOB = null;
        
            HashMap<String, Object> map = new HashMap<String, Object>();
            JSONObject jObject = new JSONObject( employeeJSONList );
            Iterator<?> keys = jObject.keys();

            while( keys.hasNext() ){
                String key = (String)keys.next();
                Object value = jObject.get ( key ) ; 
                map.put(key, value);
            }
            
            JSONObject st = (JSONObject) map.get ( "data" );
            JSONArray records = st.getJSONArray ( "records");
            JSONObject emp = null;
            
            columnNames = new Vector<String> ();
            columnNames.add( "PROCESS_ID"  );
            columnNames.add( "PROCESS_NAME"  );
            columnNames.add( "PROCESS_DETAILS"  );
//            columnNames.add( "IS_BATCH_WISE"  );
//            columnNames.add( "IS_ITEM_WISE"  );
           

        // data of the table
        for( int i=0; i<records.length (); i++ ){
        Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 0 ; columnIndex <columnNames.size () ; columnIndex ++ ) {
                emp = records.getJSONObject ( i);
                
                if(  columnIndex == 4 || columnIndex == 3   ){
//                
//                    if( emp.get ( columnNames.get ( columnIndex ) ).toString ().equalsIgnoreCase ( "true")  ){
//                            vector.add ( "Yes" );
//                    }else if( emp.get ( columnNames.get ( columnIndex ) ).toString ().equalsIgnoreCase ( "false")  ){
//                        vector.add ( "No" );
//                    }
                    
                }else{
                    vector.add ( emp.get ( columnNames.get ( columnIndex ) )  );}
                }
            data.add ( vector );
        }
        return new DefaultTableModel ( data , columnNames );
    }
    
    
    public void loadContent_old(){
        
        try{
               
                rs = DB_Operations.getMasters ( "process");
                
                jTable1.setModel( buildTableModel ( rs ) );
                jTable1.getColumnModel().getColumn(0).setMinWidth(0);
                jTable1.getColumnModel().getColumn(0).setMaxWidth(0);

        } catch (SQLException ex) {
            // Logger.getLogger(DB_Operations.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "" + ex.getMessage());
            System.out.println("DB not Connected");
            StaticValues.writer.writeExcel (Process_Master_Quatation.class.getSimpleName () , Process_Master_Quatation.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }
    }
    
    
    
    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= 3; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= 3 ; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel27 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(698, 456));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Process Name");
        add(jLabel1);
        jLabel1.setBounds(60, 30, 83, 30);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Add to master");
        jButton1.setNextFocusableComponent(jButton2);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(60, 230, 120, 25);

        jTextField1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField1.setNextFocusableComponent(jCheckBox1);
        add(jTextField1);
        jTextField1.setBounds(180, 30, 330, 30);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Update");
        jButton2.setNextFocusableComponent(jButton3);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(190, 230, 120, 25);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Reset Form");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(320, 230, 120, 25);

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("Cancel");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(450, 230, 90, 25);

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton5.setText("Export to Excel");
        jButton5.setEnabled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        add(jButton5);
        jButton5.setBounds(650, 230, 10, 25);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setNextFocusableComponent(jButton1);
        jScrollPane3.setViewportView(jTextArea1);

        add(jScrollPane3);
        jScrollPane3.setBounds(180, 160, 330, 50);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Process Details");
        add(jLabel2);
        jLabel2.setBounds(60, 170, 88, 16);

        jTable1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Process Title"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        add(jScrollPane2);
        jScrollPane2.setBounds(40, 280, 640, 160);

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 0, 51));
        jLabel26.setText("*");
        add(jLabel26);
        jLabel26.setBounds(50, 140, 90, 10);

        jLabel3.setText("Type");
        add(jLabel3);
        jLabel3.setBounds(60, 60, 90, 40);

        jCheckBox1.setText("Batch Wise");
        jCheckBox1.setNextFocusableComponent(jCheckBox2);
        add(jCheckBox1);
        jCheckBox1.setBounds(180, 70, 79, 23);

        jCheckBox2.setText("Item Wise");
        jCheckBox2.setNextFocusableComponent(jTextArea1);
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        add(jCheckBox2);
        jCheckBox2.setBounds(290, 70, 73, 23);

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 51));
        jLabel27.setText("*");
        add(jLabel27);
        jLabel27.setBounds(50, 40, 30, 10);

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton6.setText("Import Excel (xls)");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        add(jButton6);
        jButton6.setBounds(520, 280, 120, 25);

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton7.setText("Delete");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        add(jButton7);
        jButton7.setBounds(550, 230, 100, 25);

        jLabel4.setText("Rate per unit");
        add(jLabel4);
        jLabel4.setBounds(60, 150, 70, 14);

        jLabel5.setText("Units");
        add(jLabel5);
        jLabel5.setBounds(60, 110, 100, 20);

        jTextField2.setText(" ");
        add(jTextField2);
        jTextField2.setBounds(180, 140, 80, 30);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        add(jComboBox1);
        jComboBox1.setBounds(290, 100, 170, 30);

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 0, 51));
        jLabel28.setText("*");
        add(jLabel28);
        jLabel28.setBounds(50, 70, 90, 10);

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 0, 51));
        jLabel29.setText("*");
        add(jLabel29);
        jLabel29.setBounds(50, 110, 90, 10);

        jButton8.setText("Add Units");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        add(jButton8);
        jButton8.setBounds(180, 100, 100, 30);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        
        Workbook wb = new HSSFWorkbook ();

        ArrayList<String> column_names = new ArrayList<String> ();
        ArrayList<String[]> values = new ArrayList<String[]> ();
        int columnCount = 0;

        File file = null;

        try {
            ResultSet result = DB_Operations.getMasters ( "process" );
            if ( result != null ) {
                ResultSetMetaData metaData = result.getMetaData ();
                // names of columns
                columnCount = metaData.getColumnCount ();
                for ( int column = 1 ; column <= columnCount  ; column ++ ) {
                    column_names.add ( metaData.getColumnName ( column ) );
                }
            }
            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
            while ( result.next () ) {
                String[] val = new String[ columnCount ];
                for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {
                   
                        val[ columnIndex-1 ] = String.valueOf ( result.getObject ( columnIndex ) );
                    
                }
                values.add ( val );
            }
        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel (FinishedGoodMaster.class.getSimpleName () , FinishedGoodMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }

        // Cell style for header row
        CellStyle cs = wb.createCellStyle ();
        //cs.setFillForegroundColor(HSSFColor.LIME.index);
        //cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        CellStyle cs2 = wb.createCellStyle ();
        cs2.setFillForegroundColor ( HSSFColor.ROSE.index );
        cs2.setFillPattern ( HSSFCellStyle.SOLID_FOREGROUND );

        // New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet ( "Processes Master" );
        sheet1.setColumnWidth ( 0 , ( 15 * 500 ) );

        Cell c = null;
        Row row = sheet1.createRow ( 1 );
        
        int i = 1;
        //add column names to the firt row of excel
        row = sheet1.createRow ( i ++ );
        for ( int a = 0 ; a < column_names.size () ; a ++ ) {
            c = row.createCell ( a );
            // c.setCellValue(cursor.getString(a));
            c.setCellValue ( column_names.get ( a ) );
            c.setCellStyle ( cs );
        }

        for ( int j = 0 ; j < values.size () ; j ++ ) {
            String[] rowValues = values.get ( j );
            Row row4 = sheet1.createRow ( i ++ );
            for ( int k = 0 ; k < rowValues.length ; k ++ ) {
                {

                    c = row4.createCell ( k );
                    c.setCellValue ( rowValues[ k ] );
                    c.setCellStyle ( cs );
                }
            }
            Calendar c2 = Calendar.getInstance ();
            SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
            String strDate2 = sdf2.format ( c2.getTime () );
            file = new File ( "Processes Master File " + strDate2 + ".xls" );

            FileOutputStream os = null;

            try {
                os = new FileOutputStream ( file );
                wb.write ( os );
                wb.close ();
                os.close ();

            } catch ( IOException e ) {
                JOptionPane.showMessageDialog ( null , "Error " + e.getMessage () );
                StaticValues.writer.writeExcel (FinishedGoodMaster.class.getSimpleName () , FinishedGoodMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            } finally {

            }
        }
        JOptionPane.showMessageDialog ( null , "New File generated at " + file.getAbsolutePath () );
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        HomeScreen.home.removeForms ();
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton1MouseClicked

    public void resetForm(){
        jTextField2.setText("");
        jTextArea1.setText ( "" );
        jTextField1.setText ( "" );
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        
        jCheckBox1.setSelected ( false );
        jCheckBox2.setSelected ( false );
        processingDetails.clear();
        
        
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
       
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
          // TODO add your handling code here:
        jButton1.setEnabled(false);
        jButton2.setEnabled(true);
             
        DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();
        int selectedRowIndex = jTable1.getSelectedRow ();
        selectedProcessId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );

        jTextArea1.setText(   model.getValueAt ( selectedRowIndex , 2 ).toString ()      );
        jTextField1.setText(   model.getValueAt ( selectedRowIndex , 1 ).toString ()     );
           
          // 3 batchwise  CB1       4 itemwise CB2
            
            String addEmpAPICall = "unitprocessdetails?process_id="+selectedProcessId;
            String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
            if (!result2.contains("not found")) {
                if (result2 != null) {
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
                    processUnitId=new ArrayList<String>();
                    processingDetails = new ArrayList<String[]>();
                    for (int i = 0; i < records.length(); i++) {
                        emp = records.getJSONObject(i);
                        String[] data = new String[3];
                        processUnitId.add(emp.get("unit_id").toString());
                        data[0]="";
                        data[1] = emp.get("unit_name").toString();
                        data[2] = emp.get("unit_cost").toString();                        
                        processingDetails.add(data);
                    }
                }
            }

////            double totalCost1 = 0.0;
//            for (ProcessUnits p : unitDetaile) {
//                String[] data = new String[]{p.getUnitId(), p.getUnitName(), p.getUnitCost()};
//                processingDetails.add(data);
//            }
//          if( model.getValueAt ( selectedRowIndex , 3 ).toString () .equals ( "Yes")  ){
//              jCheckBox1.setSelected ( true );
//          }else{
//              jCheckBox1.setSelected ( false );
//          }
//          
//           if(  model.getValueAt ( selectedRowIndex , 4 ).toString () .equals ( "Yes")  ){
//              jCheckBox2.setSelected ( true );
//          }else{
//              jCheckBox2.setSelected ( false );
//          }
        
/*        ArrayList<String> values = new ArrayList<String>();
        // get the model from the jtable
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int columnCount = 0;
        // get the selected row index
        int selectedRowIndex = jTable1.getSelectedRow();
        // Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString() )
        // JOptionPane.showMessageDialog(null, "" + model.getValueAt(selectedRowIndex, 0).toString() );
        try {
            
            selectedProcessId = Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString());

//                String getAllProcesses = "SELECT * FROM PROCESS_MASTER WHERE PROCESS_ID = "+selectedProcessId;
//                pst = con.prepareStatement ( getAllProcesses  ) ;
//                rs = pst.executeQuery ();
                
                rs = DB_Operations.getSingleMasters ( "processes" , selectedProcessId) ;
            
            if (rs != null) {
                ResultSetMetaData metaData = rs.getMetaData();

                columnCount = metaData.getColumnCount();

                while (rs.next()) {

                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        
                           values.add(String.valueOf(rs.getObject(columnIndex)));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
            StaticValues.writer.writeExcel (Process_Master_Form.class.getSimpleName () , Process_Master_Form.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }

          jTextArea1.setText(values.get(2));
          jTextField1.setText(values.get(1));
           
          // 3 batchwise  CB1       4 itemwise CB2
          
          if( values.get(3).equals ( "true")  ){
              jCheckBox1.setSelected ( true );
          }else{
              jCheckBox1.setSelected ( false );
          }
          
           if( values.get(4).equals ( "true")  ){
              jCheckBox2.setSelected ( true );
          }else{
              jCheckBox2.setSelected ( false );
          }*/
       
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        resetForm ();
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        
        String process_name = jTextField1.getText ().toString () ;
        String process_details = jTextArea1.getText ().toString () ;
        
         String batchWise = "false", itemWise = "false";
        
            if( !process_name.equalsIgnoreCase ( "" )){

                       if( jCheckBox1.isSelected () )
                               batchWise = "true" ;

                       if( jCheckBox2.isSelected () )
                               itemWise = "true" ;
                       
                        DB_Operations.updateProcessMaster ( process_name , process_details , batchWise , itemWise , selectedProcessId ) ;
            
                        try{
                            for(String uid:processUnitId)
                            {
                                String addEmpAPICall = "unitprocessdetails_delete?unit_id="+uid;
                                String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                            }

                            String addEmpAPICall = "process_qt_update?PROCESS_ID="+selectedProcessId+"&PROCESS_NAME="+URLEncoder.encode(process_name, "UTF-8")+"&PROCESS_DETAILS="+URLEncoder.encode(process_details, "UTF-8");//+"&IS_BATCH_WISE="+URLEncoder.encode(batchWise, "UTF-8")+"&IS_ITEM_WISE="+URLEncoder.encode(itemWise, "UTF-8") ;
                            String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                            
                            for(String[] p:processingDetails)
                            {
                                String addEmpAPICall1 = "unitprocessdetails_add?ref_process_id="+selectedProcessId+"&unit_name="+URLEncoder.encode(p[1], "UTF-8")+"&unit_cost="+p[2].replace(" ", "") ;
                                String result3 =  WebAPITester.prepareWebCall ( addEmpAPICall1, StaticValues.getHeader ()   , "");                                       
                            }
                            
//                            JOptionPane.showMessageDialog ( null , result2);
                        }catch(UnsupportedEncodingException e){
                        }
                        
                        loadContent ();
                        resetForm ();
                        loadComboBox();
            }
            
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String process_name = jTextField1.getText ().toString () ;
        String process_details = jTextArea1.getText ().toString () ;
        String rateperunit=jTextField2.getText().replace(" ", "");
        String uomid=UOM.get( jComboBox1.getSelectedIndex () )[0];
        String batchWise ="false", itemWise ="false";
        
        if( !process_name.equalsIgnoreCase ( "" )){
            
            if( jCheckBox1.isSelected () )
                    batchWise = "true" ;
            
            if( jCheckBox2.isSelected () )
                    itemWise = "true" ;
                
            String result = DB_Operations.insertIntoProcessMaster ( process_name , process_details , batchWise , itemWise ) ;
            
             try{
                    String addEmpAPICall = "process_qt_add?PROCESS_NAME="+URLEncoder.encode(process_name, "UTF-8")+"&PROCESS_DETAILS="+URLEncoder.encode(process_details, "UTF-8")+"&IS_BATCH_WISE="+URLEncoder.encode(batchWise, "UTF-8")+"&IS_ITEM_WISE="+URLEncoder.encode(itemWise, "UTF-8");//+"&rateperunit="+rateperunit+"&UOM="+uomid;
                    String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    String processId=WebAPITester.getInsertedId(result2);
                        
                    for(String[] p:processingDetails)
                    {
                            String addEmpAPICall1 = "unitprocessdetails_add?ref_process_id="+processId+"&unit_name="+URLEncoder.encode(p[1], "UTF-8")+"&unit_cost="+p[2].replace(" ", "") ;
                            String result3 =  WebAPITester.prepareWebCall ( addEmpAPICall1, StaticValues.getHeader ()   , "");                                       
                    }
//                        JOptionPane.showMessageDialog ( null , result2);
                    }catch(UnsupportedEncodingException e){
                    
                    }
            
           // JOptionPane.showMessageDialog(null, " ");
            
            loadContent ();
        
            resetForm() ;
                    
            jTextField1.requestFocus ();
            for (String[] p : processingDetails) {
                System.err.println("UnitId"+p[0]);
                System.err.println("UnitName"+p[1]);
                System.err.println("UnitCost"+p[2]);
            }
            
        }else{
            
            JOptionPane.showMessageDialog(null, " Please insert appropriate process name ! ");
            
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        // TODO add your handling code here:
        
        File selectedFile = null;

        JFileChooser fileChooser = new JFileChooser ();
        fileChooser.setFileFilter ( new MyCustomFilter ("excel 97-2003") );
        fileChooser.setCurrentDirectory ( new File ( System.getProperty (
                "user.home" ) ) );
        int result = fileChooser.showOpenDialog ( this );
        if ( result == JFileChooser.APPROVE_OPTION ) {

            selectedFile = fileChooser.getSelectedFile ();

        }

        FileChannel inputChannel = null;
        FileChannel outputChannel = null;

        File dir = new File ( "dataupload\\" );
        dir.mkdirs ();

        String url;
        try {

            url = "jdbc:sqlite:" + StaticValues.dbName;
            Connection con = DriverManager.getConnection ( url );
            Statement stm = con.createStatement ();
            PreparedStatement pst;

            FileInputStream fis;
            //String query = "INSERT INTO xl_import_test ( ID, name, address, phone, email) VALUES ( ?,?,?,?,?)";
            String query = "INSERT INTO PROCESS_MASTER ( PROCESS_NAME ) VALUES ( ?  )";

            pst = con.prepareStatement ( query );
            fis = new FileInputStream ( new File ( "dataupload\\" + selectedFile.getName () ) );

            HSSFWorkbook my_xls_workbook = new HSSFWorkbook ( fis );
            HSSFSheet my_worksheet = my_xls_workbook.getSheetAt ( 0 );

            Iterator<Row> rowIterator = my_worksheet.iterator ();

            while ( rowIterator.hasNext () ) {
                Row row = rowIterator.next ();

                try {
                     pst.setString ( 1,  row.getCell ( 0 ).getStringCellValue () );
                } catch ( Exception e2 ) {
                    try {
                         pst.setInt ( 1, ( int ) row.getCell ( 0 ).getNumericCellValue () );
                    } catch ( Exception e1 ) {
                        StaticValues.writer.writeExcel (Process_Master_Quatation.class.getSimpleName () , Process_Master_Quatation.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
                    }
                    StaticValues.writer.writeExcel (Process_Master_Quatation.class.getSimpleName () , Process_Master_Quatation.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
                }
                
                pst.addBatch ();
            }

             int[] totalRecords = new int[ 2 ];
            try {
                totalRecords = pst.executeBatch ();
            } catch ( BatchUpdateException e1 ) {
                totalRecords = e1.getUpdateCounts ();
                StaticValues.writer.writeExcel (Process_Master_Quatation.class.getSimpleName () , Process_Master_Quatation.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            }
            System.out.println ( "Total record Inserted " + totalRecords.length );
            fis.close ();
            pst.close ();

           loadContent ();
        

        } catch ( SQLException ex ) {
            System.out.println ( "SQL  " + ex.getMessage () );
            StaticValues.writer.writeExcel (Process_Master_Quatation.class.getSimpleName () , Process_Master_Quatation.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        } catch ( IOException ex ) {
            System.out.println ( "IO  " + ex.getMessage () );
            StaticValues.writer.writeExcel (Process_Master_Quatation.class.getSimpleName () , Process_Master_Quatation.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }

        try {
            inputChannel = new FileInputStream ( selectedFile ).getChannel ();
            outputChannel = new FileOutputStream ( new File ( dir , selectedFile.getName () ) ).getChannel ();
            outputChannel.transferFrom ( inputChannel , 0 , inputChannel.size () );

            inputChannel.close ();
            outputChannel.close ();

        } catch ( FileNotFoundException e1 ) {
            StaticValues.writer.writeExcel (Process_Master_Quatation.class.getSimpleName () , Process_Master_Quatation.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        } catch ( IOException e2 ) {
            StaticValues.writer.writeExcel (Process_Master_Quatation.class.getSimpleName () , Process_Master_Quatation.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }
        
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        // TODO add your handling code here:
        int selection = JOptionPane.showConfirmDialog ( null , "Deleting a record is not recommendedThe 'Delete' action may cause loss of any dependencies and may affect the transactional Data and Reports and make data inconsistent\nDeleting a plant will automatically delete all shifts for that plant\nPress 'No' to revert, Press 'Yes' to continue deleting", "Are you sure about deleting this record ?", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE);
        if ( selection == JOptionPane.YES_OPTION ) {
            
        for (String uid : processUnitId) {
            try{
            String addEmpAPICall = "unitprocessdetails_delete?unit_id=" + uid;
            String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
            }catch(Exception ex){}
        }
        deleteMaster(  "process_qt_delete?PROCESS_ID="+selectedProcessId    ) ;  
            
            resetForm ();
            loadContent();
            }
    }//GEN-LAST:event_jButton7MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if(processingDetails!=null)
        {
            UnitsMapDialog process=new UnitsMapDialog(UOM,processingDetails);
            process.setVisible(true);
        }else{
            UnitsMapDialog process=new UnitsMapDialog(UOM);
            process.setVisible(true);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed
    
    
        public  void deleteMaster( String apiName  ){
        String response  = WebAPITester.prepareWebCall ( apiName,  StaticValues.getHeader () , "");
        JSONObject responseObject = ( JSONObject ) new JSONTokener ( response ).nextValue ();
        JOptionPane.showMessageDialog ( null , retreiveSuccessMessage ( responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () ) );
        }
    
     public static  String retreiveSuccessMessage( String json){
        String id = "Not able to fetch the response for your request. Please refresh you master form and try again";
        JSONObject jObject = new JSONObject ( json );
        id = jObject.getString("118" );
        return id ;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

    int selectedProcessId = 0;
    ArrayList<ProcessUnits> unitDetaile=null;
    ArrayList<String[]> processingDetails=null;
    List<String> processUnitId=null;
    class UnitsMapDialog extends JDialog {

        int totalPanels = 1;
        JPanel listPanel = null;
        JScrollPane jScrollPane1 = null;

            ArrayList<String[]> units=null;

        public UnitsMapDialog(ArrayList<String[]>units) {
            unitDetaile = new ArrayList<ProcessUnits>();
            this.units=units;
            jButton1 = new javax.swing.JButton();
            jButton3 = new javax.swing.JButton();
            jButton4 = new javax.swing.JButton();
            setLayout(null);

            jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton1.setText("Submit Schedule & Close");
            jButton1.setOpaque(false);
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });
            add(jButton1);
            jButton1.setBounds(280, 400, 180, 32);
            jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton3.setText("Add New Row");
            jButton3.setOpaque(false);
            jButton3.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton3ActionPerformed(evt);
                }
            });
            add(jButton3);
            jButton3.setBounds(300, 7, 110, 32);
            jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton4.setText("Discard & Close");
            add(jButton4);
            jButton4.setBounds(140, 400, 140, 32);

            setBounds(350, 200, 550, 500);
            setVisible(true);
            setLayout(null);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setBackground(Color.WHITE);

            listPanel = new JPanel();
            listPanel.setBackground(Color.white);
            listPanel.setBounds(0, 0, 500,300);

            listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
            //listPanel.setLayout (null );

            jScrollPane1 = new JScrollPane(listPanel,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            jScrollPane1.setBounds(20, 70, 500, 300);
            add(jScrollPane1);

            UnitsLabelPanel labels = new UnitsLabelPanel();
            labels.setBounds(0, 40, 600, 26);
            add(labels);

//            if (qty > 0.0) {
//                ProcessDetailsPanel p1 = new ProcessDetailsPanel(processes, measunit, 2.0);
//                p1.setBounds(0, 0, 590, 60);
//                listPanel.add(p1);
//                processDetaile.add(p1);
//                revalidate();
//                repaint();
//            } else {
//                JOptionPane.showMessageDialog(null, "Please enter non zero quantity");
//            }

        }

        public UnitsMapDialog(ArrayList<String[]> units,ArrayList<String[]> processDetaile) {
            this.units=units;
            unitDetaile = new ArrayList<ProcessUnits>();
            jButton1 = new javax.swing.JButton();
            jButton3 = new javax.swing.JButton();
            jButton4 = new javax.swing.JButton();

            setLayout(null);

            jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton1.setText("Submit Schedule & Close");
            jButton1.setOpaque(false);
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });
            add(jButton1);
            jButton1.setBounds(280, 420, 180, 32);
            jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton3.setText("Add New Row");
            jButton3.setOpaque(false);
            jButton3.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton3ActionPerformed(evt);
                }
            });
            add(jButton3);
            jButton3.setBounds(500, 7, 110, 32);
            jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            jButton4.setText("Discard & Close");
            add(jButton4);
            jButton4.setBounds(140, 420, 140, 32);

            setBounds(350, 200, 650, 500);
            setVisible(true);
            setLayout(null);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setBackground(Color.WHITE);

            listPanel = new JPanel();
            listPanel.setBackground(Color.white);
            listPanel.setBounds(0, 0, 650, 50);

            listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
            //listPanel.setLayout (null );

            jScrollPane1 = new JScrollPane(listPanel,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            jScrollPane1.setBounds(20, 70, 620, 350);
            add(jScrollPane1);

//            UnitLabelsPanel labels = new UnitLabelsPanel();
//            labels.setBounds(0, 40, 600, 26);
//            add(labels);

            for (int i = 0; i < processDetaile.size(); i++) {

                ProcessUnits p1 = new ProcessUnits(UOM);
                p1.setUnitName(processDetaile.get(i)[1]);
                p1.setUnitCost(processDetaile.get(i)[2]);

                p1.setBounds(0, 0, 590, 60);
                listPanel.add(p1);
                unitDetaile.add(p1);
                revalidate();
                repaint();
            }
        }

        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
            ProcessUnits p1 = new ProcessUnits(units);
//            p1.setBounds(0, (totalPanels) * 50, 590, 50);
            listPanel.add(p1);
            unitDetaile.add(p1);

//            totalPanels++;

            revalidate();
            repaint();
        }

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
//            // TODO add your handling code here:
//            double totalProcessCost = 0.0;
//            StringBuilder sb = new StringBuilder();
            processingDetails = new ArrayList<String[]>();
//            double totalCost1 = 0.0;
            for (ProcessUnits p : unitDetaile) {
                String[] data = new String[]{p.getUnitId(), p.getUnitName(), p.getUnitCost()};
                processingDetails.add(data);
            }

//            setCalculations();
//
//            //JOptionPane.showMessageDialog( null,  sb.toString ()   )  ;
            dispose();
        }

        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton3;
        private javax.swing.JButton jButton4;
    }
}
