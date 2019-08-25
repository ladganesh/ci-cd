/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.DateTimeFormatter;
import trixware.erp.prodezydesktop.components.ProgressDialog;
import trixware.erp.prodezydesktop.examples.HomeScreen3;
import trixware.erp.prodezydesktop.quotation.partmaster.TableModiFier;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class Raw_Material_Quotation extends javax.swing.JPanel {

    String resource = "", resourceType = "";

    /**
     * Creates new form Raw_Material
     */
    ActionListener actionListener=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadShapesparameter();
        }
    };
    public Raw_Material_Quotation () {
        initComponents ();

        resourceType = "Masters";
        resource = "Raw_Material";

//        try {
//            StaticValues.checkUserRolePermission ( resourceType , resourceType );
//        } catch ( Exception e ) {
//            StaticValues.writer.writeExcel ( resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }

        jComboBox4.addActionListener ( selectUnitAction );
        jTextField1.addKeyListener ( k );
        jTextField1.addFocusListener ( f2 );
        jTextField5.addKeyListener ( k );
        jTextField5.addFocusListener ( f2 );
        
        jTextField9.addKeyListener ( k );
        jTextField9.addFocusListener ( f2 );
        
        jTextField10.addKeyListener ( k );
        jTextField10.addFocusListener ( f2 );
        
        jTextField11.addKeyListener ( k );
        jTextField11.addFocusListener ( f2 );
        
        jTextField12.addKeyListener ( k );
        jTextField12.addFocusListener ( f2 );
        
        jScrollPane1.setVisible ( false);
        jLabel19.setVisible(false );

        jComboBox3.addActionListener(actionListener);
        loadShapesparameter();
        componentVisibility();
        loadContent();
    }

    ArrayList<String[]> UOM = null;
    ArrayList<String[]> RMType = null;
    ArrayList<String[]> category = null;
    ArrayList<String[]> rmcode=null;
    ArrayList<String[]> docList = null ;
    

    public void loadContent () {
        //            ResultSet result = DB_Operations.getMasters ( "raw_material" );
        // jTable1.setModel(DbUtils.resultSetToTableModel(result));

//                jTable1.setModel(buildTableModel(result));
//
//                jTable1.getColumnModel().getColumn(0).setMinWidth(0);
//                jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
String addEmpAPICall = "rm_quotation";
String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
if(   !  result2.contains ( "not found")  ){
    
    if ( result2 != null ) {
        jTable1.setModel ( buildTableModelfromJSON ( result2 ) );
        TableModiFier.setTableSize(jTable1,30,110);
        jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
        jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
    }
}
if ( jComboBox1.getItemCount () == 0 ) {
    //result = DB_Operations.getMasters ( "raw_material_type" );
    
    addEmpAPICall = "rmt";
    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
    
    if( !  result2.contains(  "not found"   )  ){
        HashMap<String, Object> map = new HashMap<String, Object>();
        JSONObject jObject = new JSONObject( result2 );
        Iterator<?> keys = jObject.keys();
        
        while( keys.hasNext() ){
            String key = (String)keys.next();
            Object value = jObject.get ( key ) ;
            map.put(key, value);
        }
        
        JSONObject st = (JSONObject) map.get ( "data" );
        JSONArray records = st.getJSONArray ( "records");
        
        JSONObject emp = null;
        RMType = new ArrayList<String[]>();
        for ( int i = 0 ; i < records.length () ; i ++ ) {
            
            emp = records.getJSONObject ( i);
            jComboBox1.addItem ( emp.get ( "RawMaterial" ).toString () );
            RMType.add( new String[]{ emp.get ( "RMType_ID" ).toString (), emp.get ( "RawMaterial" ).toString () }   );
        }
    }
}
if ( jComboBox2.getItemCount () == 0  ) {
    
    addEmpAPICall = "category";
    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
    
    if( !  result2.contains(  "not found"   )  ){
        HashMap<String, Object> map = new HashMap<String, Object>();
        JSONObject jObject = new JSONObject( result2 );
        Iterator<?> keys = jObject.keys();
        
        while( keys.hasNext() ){
            String key = (String)keys.next();
            Object value = jObject.get ( key ) ;
            map.put(key, value);
        }
        
        JSONObject st = (JSONObject) map.get ( "data" );
        JSONArray records = st.getJSONArray ( "records");
        
        JSONObject emp = null;
        category = new ArrayList<String[]>() ;
        for ( int i = 0 ; i < records.length () ; i ++ ) {
            
            emp = records.getJSONObject ( i);
            jComboBox2.addItem ( emp.get ( "Category" ).toString () );
            category.add( new String[]{ emp.get ( "Category_ID" ).toString (), emp.get ( "Category" ).toString () }   );
        }
    }
}
if ( jComboBox4.getItemCount () == 0  ) {
    
    addEmpAPICall = "unitmeas";
    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
    
    if( !  result2.contains(  "not found"   )  ){
        HashMap<String, Object> map = new HashMap<String, Object>();
        JSONObject jObject = new JSONObject( result2 );
        Iterator<?> keys = jObject.keys();
        
        while( keys.hasNext() ){
            String key = (String)keys.next();
            Object value = jObject.get ( key ) ;
            map.put(key, value);
        }
        
        JSONObject st = (JSONObject) map.get ( "data" );
        JSONArray records = st.getJSONArray ( "records");
        
        JSONObject emp = null;
        UOM = new ArrayList<String[]> ();
        for ( int i = 0 ; i < records.length () ; i ++ ) {
            
            emp = records.getJSONObject ( i);
            jComboBox4.addItem ( emp.get ( "UOM" ).toString () );
            UOM.add( new String[]{ emp.get ( "UOM_ID" ).toString (), emp.get ( "UOM" ).toString () }   );
        }
    }
}
//                if ( jComboBox3.getItemCount () == 0  ) {
//rm_quotation
//                addEmpAPICall = "rawmaterials";
//                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
//                    
//                    if( !  result2.contains(  "not found"   )  ){
//                        HashMap<String, Object> map = new HashMap<String, Object>();
//                        JSONObject jObject = new JSONObject( result2 );
//                        Iterator<?> keys = jObject.keys();
//
//                        while( keys.hasNext() ){
//                            String key = (String)keys.next();
//                            Object value = jObject.get ( key ) ; 
//                            map.put(key, value);
//                        }
//
//                        JSONObject st = (JSONObject) map.get ( "data" );
//                        JSONArray records = st.getJSONArray ( "records");
//
//                        JSONObject emp = null;
//                        rmcode = new ArrayList<String[]> ();
//                        for ( int i = 0 ; i < records.length () ; i ++ ) {
//
//                            emp = records.getJSONObject ( i);
//                            //jComboBox3.addItem ( emp.get ( "RM_CLASS" ).toString () );
//                            rmcode.add( new String[]{ emp.get ( "RM_ID" ).toString (), emp.get ( "RM_CLASS" ).toString () }   ); 
//                        }
//                    }
//                }
//                if ( UOM == null || UOM.size () <= 0 ) {
//                    result = DB_Operations.getMasters ( "uom" );
//                    UOM = new ArrayList<String[]> ();
//                    if ( result != null ) {
//                        while ( result.next () ) {
//                            jComboBox4.addItem ( result.getString ( "UOM" ) );
//                            UOM.add ( result.getString ( "UOM_ID" ) );
//                        }
//                    } else {
//                        jComboBox4.addItem ( "Not Available" );
//                    }
//                }
//



// JTable table = new JTable(buildTableModel(result));
// Closes the Connection
// JOptionPane.showMessageDialog(null, new JScrollPane(table));

    }

    public static DefaultTableModel buildTableModelfromJSON ( String employeeJSONList ) {

        Vector<String> columnNames = new Vector<String> ();
        Vector<String> columnNames2 = new Vector<String> ();
        int columnCount = 0;
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();

        JSONArray jARR = null;
        JSONObject jOB = null;

        HashMap<String , Object> map = new HashMap<String , Object> ();
        JSONObject jObject = new JSONObject ( employeeJSONList );
        Iterator<?> keys = jObject.keys ();

        while ( keys.hasNext () ) {
            String key = ( String ) keys.next ();
            Object value = jObject.get ( key );
            map.put ( key , value );
        }

        JSONObject st = ( JSONObject ) map.get ( "data" );
        JSONArray records = st.getJSONArray ( "records" );

        JSONObject emp = null;

        columnNames = new Vector<String> ();
        columnNames.add ( "RM_ID" );
//        columnNames.add ( "RM_CTG" );
        columnNames.add ( "RM_NAME" );
        //columnNames.add ( "RM_AUTH_SUPP" );
        columnNames.add ( "RM_RATE" );
//        columnNames.add ( "REORDER_LEVEL" );
        columnNames.add ( "RMM_UOM_ID" );

        columnNames.add ( "tax" );
        columnNames.add ( "icc" );
        columnNames.add ( "scrap_rate" );
        columnNames.add ( "density" );
        columnNames.add ( "shape" );       
        columnNames.add ( "diameter" );
        columnNames.add ( "length" );
        columnNames.add ( "width" );
        columnNames.add ( "thickness" );
        columnNames.add ( "height" );
        columnNames.add ( "in_diameter" );
        columnNames.add ( "out_diameter" );
        
        
//        columnNames.add("RM_CLASS");
//        columnNames.add ( "length" );
//        columnNames.add ( "width" );
//        columnNames.add ( "thickness" );
//        columnNames.add("density");
//        columnNames.add ( "RM_EC_NO" );
//        columnNames.add ( "RM_CAS_NO" );
//        columnNames.add ( "GST_NO" );
//        columnNames.add("RM_CRIT");
 
        
        columnNames2 = new Vector<String> ();
        columnNames2.add ( "RM_ID" );
//        columnNames.add ( "RM_CTG" );
        columnNames2.add ( "RM_NAME" );
        //columnNames.add ( "RM_AUTH_SUPP" );
        columnNames2.add ( "RM_RATE" );
//        columnNames.add ( "REORDER_LEVEL" );
        columnNames2.add ( "RMM_UOM_ID" );

        columnNames2.add ( "tax" );
        columnNames2.add ( "icc" );
        columnNames2.add ( "scrap_rate" );
        columnNames2.add ( "density" );
        columnNames2.add ( "shape" );        
        columnNames2.add ( "diameter" );
        columnNames2.add ( "length" );
        columnNames2.add ( "width" );
        columnNames2.add ( "thickness" );
        columnNames2.add ( "height" );
        columnNames2.add ( "in_diameter" );
        columnNames2.add ( "out_diameter" );
        
        
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {
            Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {
                emp = records.getJSONObject ( i );
                if(  columnNames.get ( columnIndex ).equals ( "RMM_UOM_ID") ){
                        vector.add ( StaticValues.getUOMName ( Integer.parseInt( emp.get ( columnNames.get ( columnIndex ) ).toString() ) ) );
                }else{
                    String val = emp.get ( columnNames.get ( columnIndex ) ).toString(); ;
                    if( ! val.equals ( "null" ) &&  val != null ){
                        vector.add ( val ) ;
                    }else{
                        vector.add ( "" ) ;
                    }
                }
            }
            data.add ( vector );
        }       
        return new DefaultTableModel ( data , columnNames2 );
    }

    public static DefaultTableModel buildTableModel ( ResultSet rs )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        int columnCount = metaData.getColumnCount ();
        for ( int column = 1 ; column <= 7 ; column ++ ) {
            columnNames.add ( metaData.getColumnName ( column ) );
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        while ( rs.next () ) {
            Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 1 ; columnIndex <= 7 ; columnIndex ++ ) {
                vector.add ( rs.getObject ( columnIndex ) );
            }
            data.add ( vector );
        }

        return new DefaultTableModel ( data , columnNames );

    }

    KeyListener k = new KeyListener () {
        @Override
        public void keyTyped ( KeyEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            char enter = e.getKeyChar ();

            String dot = Character.toString ( enter );

            if (  ! ( Character.isDigit ( enter ) ) &&  ! dot.equals ( "." ) ) {
                e.consume ();
            }

        }

        @Override
        public void keyPressed ( KeyEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyReleased ( KeyEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }
    };

    FocusListener f2 = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.

        }

        @Override
        public void focusLost ( FocusEvent e ) {
            //    throw new UnsupportedOperationException ( "Not supported yet." ); //To Search Google or type URL
//change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();
            String x = jcb.getText ().trim ();

            if ( x.equalsIgnoreCase ( "" ) ) {

            }

            try {
                int num = Integer.parseInt ( String.valueOf ( jcb.getText ().toString () ) );
                if ( num < 0 || num == 0 ) {
                    jcb.setText ( "" );

                }
            } catch ( NumberFormatException ex1 ) {
                jcb.setText ( x );
                StaticValues.writer.writeExcel (Raw_Material_Quotation.class.getSimpleName () , Raw_Material_Quotation.class.getSimpleName () , ex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }
    };

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setEnabled(false);
        setPreferredSize(new java.awt.Dimension(1144, 592));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Type :");
        jLabel1.setEnabled(false);
        add(jLabel1);
        jLabel1.setBounds(450, 250, 130, 30);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox1.setEnabled(false);
        jComboBox1.setNextFocusableComponent(jComboBox2);
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        add(jComboBox1);
        jComboBox1.setBounds(510, 260, 200, 30);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Category RM\\Part :");
        jLabel2.setEnabled(false);
        add(jLabel2);
        jLabel2.setBounds(550, 260, 130, 30);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox2.setEnabled(false);
        jComboBox2.setNextFocusableComponent(jTextField6);
        add(jComboBox2);
        jComboBox2.setBounds(510, 260, 200, 30);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("<html> Rate / Unit </html>");
        add(jLabel4);
        jLabel4.setBounds(40, 100, 130, 30);

        jTextField1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField1.setNextFocusableComponent(jComboBox4);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(170, 100, 100, 30);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Acceptance Criteria :");
        jLabel5.setMaximumSize(new java.awt.Dimension(0, 0));
        jLabel5.setMinimumSize(new java.awt.Dimension(0, 0));
        add(jLabel5);
        jLabel5.setBounds(750, 130, 0, 0);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setMaximumSize(new java.awt.Dimension(0, 0));
        jTextArea1.setMinimumSize(new java.awt.Dimension(0, 0));
        jTextArea1.setNextFocusableComponent(jTextField2);
        jScrollPane2.setViewportView(jTextArea1);

        add(jScrollPane2);
        jScrollPane2.setBounds(880, 130, 0, 0);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("EC No :");
        jLabel6.setMaximumSize(new java.awt.Dimension(0, 0));
        jLabel6.setMinimumSize(new java.awt.Dimension(0, 0));
        add(jLabel6);
        jLabel6.setBounds(750, 10, 0, 0);

        jTextField2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField2.setMaximumSize(new java.awt.Dimension(0, 0));
        jTextField2.setMinimumSize(new java.awt.Dimension(0, 0));
        jTextField2.setNextFocusableComponent(jTextField3);
        add(jTextField2);
        jTextField2.setBounds(880, 10, 0, 0);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("CAS No :");
        jLabel7.setMaximumSize(new java.awt.Dimension(0, 0));
        jLabel7.setMinimumSize(new java.awt.Dimension(0, 0));
        add(jLabel7);
        jLabel7.setBounds(750, 50, 0, 0);

        jTextField3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField3.setMaximumSize(new java.awt.Dimension(0, 0));
        jTextField3.setMinimumSize(new java.awt.Dimension(0, 0));
        jTextField3.setNextFocusableComponent(jTextField4);
        add(jTextField3);
        jTextField3.setBounds(880, 50, 0, 0);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("GST No :");
        jLabel8.setMaximumSize(new java.awt.Dimension(0, 0));
        jLabel8.setMinimumSize(new java.awt.Dimension(0, 0));
        add(jLabel8);
        jLabel8.setBounds(750, 90, 0, 0);

        jTextField4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField4.setMaximumSize(new java.awt.Dimension(0, 0));
        jTextField4.setMinimumSize(new java.awt.Dimension(0, 0));
        add(jTextField4);
        jTextField4.setBounds(880, 90, 0, 0);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Cancel");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(560, 250, 140, 30);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Add to Master");
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(110, 250, 140, 30);

        jTable1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
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
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTable1FocusLost(evt);
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);

        add(jScrollPane3);
        jScrollPane3.setBounds(20, 300, 1140, 230);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Export to Excel");
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(710, 250, 140, 30);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("Reorder Level");
        jLabel11.setEnabled(false);
        add(jLabel11);
        jLabel11.setBounds(390, 180, 130, 30);

        jTextField5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField5.setEnabled(false);
        add(jTextField5);
        jTextField5.setBounds(480, 180, 200, 30);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("Reset");
        jButton4.setOpaque(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(410, 250, 140, 30);

        jButton5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton5.setText("Update Record");
        jButton5.setOpaque(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        add(jButton5);
        jButton5.setBounds(260, 250, 140, 30);

        jTextField6.setEnabled(false);
        jTextField6.setNextFocusableComponent(jTextField1);
        add(jTextField6);
        jTextField6.setBounds(170, 280, 200, 30);

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 0, 51));
        jLabel26.setText("*");
        add(jLabel26);
        jLabel26.setBounds(30, 50, 20, 10);

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 51));
        jLabel27.setText("*");
        jLabel27.setEnabled(false);
        add(jLabel27);
        jLabel27.setBounds(440, 260, 20, 10);

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 0, 51));
        jLabel28.setText("*");
        add(jLabel28);
        jLabel28.setBounds(30, 110, 20, 10);

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 0, 51));
        jLabel29.setText("*");
        add(jLabel29);
        jLabel29.setBounds(620, 60, 20, 10);

        jComboBox4.setNextFocusableComponent(jTextField5);
        jComboBox4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox4MouseClicked(evt);
            }
        });
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        add(jComboBox4);
        jComboBox4.setBounds(170, 50, 200, 30);

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("per");
        add(jLabel10);
        jLabel10.setBounds(270, 100, 40, 30);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("Unit of Measurement");
        add(jLabel12);
        jLabel12.setBounds(40, 40, 120, 30);
        add(jLabel13);
        jLabel13.setBounds(310, 100, 50, 30);

        jLabel14.setEnabled(false);
        add(jLabel14);
        jLabel14.setBounds(370, 150, 60, 30);

        jButton6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton6.setText("Delete");
        jButton6.setOpaque(false);
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        add(jButton6);
        jButton6.setBounds(860, 250, 140, 30);

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 0, 51));
        jLabel30.setText("*");
        jLabel30.setEnabled(false);
        add(jLabel30);
        jLabel30.setBounds(400, 200, 20, 10);

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 0, 51));
        jLabel31.setText("*");
        add(jLabel31);
        jLabel31.setBounds(30, 20, 20, 10);

        jTextField7.setNextFocusableComponent(jTextField1);
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        add(jTextField7);
        jTextField7.setBounds(170, 10, 200, 30);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("Length");
        add(jLabel9);
        jLabel9.setBounds(450, 50, 50, 30);

        jLabel16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel16.setText("Width");
        add(jLabel16);
        jLabel16.setBounds(450, 90, 70, 30);

        jLabel17.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel17.setText("Thickness");
        add(jLabel17);
        jLabel17.setBounds(630, 90, 60, 30);

        jLabel18.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel18.setText("Density");
        add(jLabel18);
        jLabel18.setBounds(630, 50, 50, 30);
        add(jTextField9);
        jTextField9.setBounds(520, 50, 70, 30);
        add(jTextField10);
        jTextField10.setBounds(520, 90, 70, 30);
        add(jTextField11);
        jTextField11.setBounds(710, 90, 80, 30);
        add(jTextField12);
        jTextField12.setBounds(710, 50, 80, 30);

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 0, 51));
        jLabel32.setText("*");
        jLabel32.setEnabled(false);
        add(jLabel32);
        jLabel32.setBounds(430, 300, 10, 20);

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 0, 51));
        jLabel33.setText("*");
        add(jLabel33);
        jLabel33.setBounds(440, 60, 20, 10);

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 0, 51));
        jLabel34.setText("*");
        add(jLabel34);
        jLabel34.setBounds(440, 100, 20, 10);

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 0, 51));
        jLabel35.setText("*");
        add(jLabel35);
        jLabel35.setBounds(620, 100, 20, 10);

        jLabel15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel15.setText("RM Name :");
        add(jLabel15);
        jLabel15.setBounds(40, 10, 80, 30);

        jLabel38.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 0, 51));
        jLabel38.setText("*");
        jLabel38.setEnabled(false);
        add(jLabel38);
        jLabel38.setBounds(440, 280, 20, 10);

        jLabel21.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel21.setText("RM Code:");
        jLabel21.setEnabled(false);
        add(jLabel21);
        jLabel21.setBounds(30, 270, 130, 30);

        jList1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        add(jScrollPane1);
        jScrollPane1.setBounds(170, 210, 0, 0);

        jLabel19.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel19.setText("<html>Reference<br>Documents</html>");
        jLabel19.setMaximumSize(new java.awt.Dimension(0, 0));
        jLabel19.setMinimumSize(new java.awt.Dimension(0, 0));
        add(jLabel19);
        jLabel19.setBounds(30, 210, 0, 0);

        jLabel36.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 0, 51));
        jLabel36.setText("*");
        add(jLabel36);
        jLabel36.setBounds(420, 20, 20, 10);

        jLabel20.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel20.setText("Shape");
        add(jLabel20);
        jLabel20.setBounds(430, 10, 130, 30);

        jLabel37.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 0, 51));
        jLabel37.setText("*");
        add(jLabel37);
        jLabel37.setBounds(440, 100, 20, 10);

        jLabel22.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel22.setText("Diameter");
        add(jLabel22);
        jLabel22.setBounds(450, 90, 60, 30);
        add(jTextField14);
        jTextField14.setBounds(520, 90, 70, 30);

        jLabel39.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 0, 51));
        jLabel39.setText("*");
        add(jLabel39);
        jLabel39.setBounds(620, 100, 20, 10);

        jLabel23.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel23.setText("<html>Inner <br>Diameter<html>");
        add(jLabel23);
        jLabel23.setBounds(630, 90, 90, 30);

        jTextField15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField15ActionPerformed(evt);
            }
        });
        add(jTextField15);
        jTextField15.setBounds(710, 90, 80, 30);

        jLabel40.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 0, 51));
        jLabel40.setText("*");
        add(jLabel40);
        jLabel40.setBounds(440, 90, 20, 10);

        jLabel24.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel24.setText("<html>Outer<br> Diameter</html>");
        add(jLabel24);
        jLabel24.setBounds(450, 90, 60, 30);
        add(jTextField16);
        jTextField16.setBounds(520, 90, 70, 30);

        jLabel41.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 0, 51));
        jLabel41.setText("*");
        add(jLabel41);
        jLabel41.setBounds(420, 140, 20, 10);

        jLabel25.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel25.setText("Wall");
        add(jLabel25);
        jLabel25.setBounds(450, 130, 90, 30);
        add(jTextField17);
        jTextField17.setBounds(490, 130, 70, 30);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Round", "Square", "Rectangle", "Hexagonal", "Sheet", "Plate", "Tubular", "Ring", "Pipe" }));
        add(jComboBox3);
        jComboBox3.setBounds(490, 10, 270, 35);

        jLabel42.setText("Volume");
        add(jLabel42);
        jLabel42.setBounds(430, 210, 60, 30);

        jLabel43.setText("< auto calculate>");
        add(jLabel43);
        jLabel43.setBounds(490, 210, 110, 30);

        jLabel44.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jLabel44);
        jLabel44.setBounds(590, 90, 90, 30);
        add(jTextField18);
        jTextField18.setBounds(710, 90, 80, 30);

        jLabel45.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jLabel45);
        jLabel45.setBounds(590, 90, 90, 30);
        add(jTextField19);
        jTextField19.setBounds(710, 90, 80, 30);

        jLabel46.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 0, 51));
        jLabel46.setText("*");
        add(jLabel46);
        jLabel46.setBounds(620, 100, 20, 10);

        jLabel47.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 0, 51));
        jLabel47.setText("*");
        add(jLabel47);
        jLabel47.setBounds(620, 100, 20, 10);

        jLabel3.setText("Scrap Rate / Unit");
        add(jLabel3);
        jLabel3.setBounds(40, 150, 90, 14);

        jTextField8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField8.setNextFocusableComponent(jComboBox4);
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });
        add(jTextField8);
        jTextField8.setBounds(170, 150, 100, 30);

        jLabel48.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("%");
        add(jLabel48);
        jLabel48.setBounds(600, 160, 40, 30);
        add(jLabel49);
        jLabel49.setBounds(310, 150, 50, 30);

        jLabel50.setText("Tax");
        add(jLabel50);
        jLabel50.setBounds(450, 160, 80, 14);
        add(jTextField13);
        jTextField13.setBounds(520, 160, 80, 30);

        jLabel51.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("per");
        add(jLabel51);
        jLabel51.setBounds(270, 150, 40, 30);

        jLabel52.setText("ICC per Anuam");
        add(jLabel52);
        jLabel52.setBounds(40, 200, 110, 14);
        add(jTextField20);
        jTextField20.setBounds(170, 200, 100, 30);

        jLabel53.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("%");
        add(jLabel53);
        jLabel53.setBounds(270, 200, 40, 30);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String RM_TYPE, RM_NAME, RM_CTG, RM_RATE, RM_CRIT, RM_EC_NO, RM_CAS_NO, GST_NO, RM_CLASS, AUTH_SUPP, REORDER_LEVEL, CREATED_ON, EDITED_ON, EDITED_BY, RM_CODE,Height,radius;
        
        String length = "0.0", width= "0.0", thickness= "0.0", density= "0.0" ;

        String UOM_ID;

        RM_TYPE = jComboBox1.getSelectedItem ().toString ();
        RM_CTG = jComboBox2.getSelectedItem ().toString ();
        //RM_CLASS = jComboBox3.getSelectedItem ().toString ();
        RM_NAME = jTextField7.getText ();
        //RM_AUTH_SUPP = jComboBox4.getSelectedItem().toString();
        RM_RATE = jTextField1.getText ();
        RM_CRIT = "";
        RM_EC_NO ="";
        RM_CAS_NO = "";
        GST_NO = "";
        RM_CODE = jTextField6.getText();
        REORDER_LEVEL = jTextField5.getText ();

        length = jTextField9.getText ();
        width = jTextField10.getText ();
        thickness = jTextField11.getText ();
        density = jTextField12.getText ();
        Height=jTextField18.getText();
        radius="";
        String inDiam=jTextField15.getText();
        String outDiam=jTextField16.getText();
        String diam=jTextField14.getText();
        String wall=jTextField17.getText();
        String shape=jComboBox3.getSelectedItem().toString();
        UOM_ID = UOM.get( jComboBox4.getSelectedIndex () )[0];
        String scrapRate=jTextField8.getText();
        String icc=jTextField20.getText();
        String tax=jTextField13.getText();
        
        CREATED_ON = DateTimeFormatter.sdf2.format ( Calendar.getInstance ().getTime () ) + " " + DateTimeFormatter.sdf1.format ( Calendar.getInstance ().getTime () );
        EDITED_ON = Proman_User.getEID () + "";
        EDITED_BY = DateTimeFormatter.sdf2.format ( Calendar.getInstance ().getTime () ) + " " + DateTimeFormatter.sdf1.format ( Calendar.getInstance ().getTime () );

//        if (  ! RM_NAME.equals ( "" ) &&  ! RM_RATE.equals ( "" ) &&  ! length .equals (  "") && ! density.equals (  "")) {
            String result = DB_Operations.insertIntoRawMaterialMaster ( RM_TYPE , RM_NAME , RM_CTG , RM_RATE , RM_CRIT , RM_EC_NO , RM_CAS_NO , GST_NO , RM_CODE , REORDER_LEVEL , CREATED_ON , EDITED_ON , EDITED_BY , UOM_ID );

            try {
                String addEmpAPICall = "rm_quotation_add?RM_TYPE=" + URLEncoder.encode ( RM_TYPE , "UTF-8" ) + "&RM_NAME=" + URLEncoder.encode ( RM_NAME , "UTF-8" ) + "&RM_CTG=" + URLEncoder.encode ( RM_CTG , "UTF-8" ) + "&RM_AUTH_SUPP=" + URLEncoder.encode ( "" , "UTF-8" ) + "&RM_RATE=" + URLEncoder.encode ( RM_RATE , "UTF-8" ) + "&RM_CRIT=" + URLEncoder.encode ( RM_CRIT , "UTF-8" ) 
                        + "&RM_EC_NO=" + URLEncoder.encode ( RM_EC_NO , "UTF-8" ) + "&RM_CAS_NO=" + URLEncoder.encode ( RM_CAS_NO , "UTF-8" ) + "&GST_NO=" + URLEncoder.encode ( GST_NO , "UTF-8" ) + "&RM_CLASS=" + URLEncoder.encode ( RM_CODE , "UTF-8" ) + "&RM_CODE=" + URLEncoder.encode ( RM_CODE , "UTF-8" ) 
                        + "&REORDER_LEVEL=" + URLEncoder.encode ( REORDER_LEVEL , "UTF-8" ) + "&CREATED_ON=" + URLEncoder.encode ( CREATED_ON , "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( EDITED_ON , "UTF-8" ) + "&EDITED_BY=" + URLEncoder.encode ( EDITED_BY , "UTF-8" ) + "&RMM_UOM_ID=" + URLEncoder.encode ( UOM_ID , "UTF-8" )
                        + "&length="+length +"&width="+ width+ "&thickness="+ thickness+ "&density="+density+"&height="+Height+"&radius="+radius+"&in_diameter="+inDiam+"&wall="+wall+"&out_diameter="+outDiam+"&diameter="+diam+"&shape="+shape+"&icc="+icc+"&scrap_rate="+scrapRate+"&tax="+tax;
                String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
//                JOptionPane.showMessageDialog ( null , result2 );

            } catch ( UnsupportedEncodingException e ) {

            }

            try {
                if ( Integer.parseInt ( result ) > 0 ) {
                    JOptionPane.showMessageDialog ( null , "Raw Material Added Successfuly" );
                }
            } catch ( Exception e ) {
                JOptionPane.showMessageDialog ( null , "" + result );
                StaticValues.writer.writeExcel (Raw_Material_Quotation.class.getSimpleName () , Raw_Material_Quotation.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

//        } else {
//            JOptionPane.showMessageDialog ( null , "Please fill all fields marked with * " );
//        }

        loadContent ();
        resetFields ();
        
    }//GEN-LAST:event_jButton2ActionPerformed
    public void loadShapesparameter()
    {
       String shapes=jComboBox3.getSelectedItem().toString();
       switch(shapes)
        {
            case "Round":
                //Thickness
                jLabel35.setVisible(false);                
                jLabel17.setVisible(false);
                jTextField11.setVisible(false);
                jTextField11.setText("0.0");
                //width
                jLabel34.setVisible(false);
                jLabel16.setVisible(false);
                jTextField10.setVisible(false);
                jTextField10.setText("0.0");
                //Inner Diameter
                jLabel39.setVisible(false);                
                jLabel23.setVisible(false);
                jTextField15.setVisible(false);
                jTextField15.setText("0.0");
                
                //Outer Diameter
                jLabel40.setVisible(false);  
                jLabel24.setVisible(false);
                jTextField16.setVisible(false);
                jTextField16.setText("0.0");

                //Wall
                jLabel41.setVisible(false);
                jLabel25.setVisible(false);
                jTextField17.setVisible(false);      
                jTextField17.setText("0.0");

                //height
                jLabel46.setVisible(false);                
                jTextField18.setVisible(false);
                jLabel44.setVisible(false);
                jTextField18.setText("0.0");

                //radius
                jLabel47.setVisible(false);
                jTextField19.setVisible(false);
                jLabel45.setVisible(false);

                
                //Length
                jLabel33.setVisible(true);
                jTextField9.setVisible(true);
                jLabel9.setVisible(true);

                //Diameter

                jLabel37.setVisible(true);
                jLabel22.setVisible(true);
                jTextField14.setVisible(true);
                break;
            case "Square":
                //Thickness
                jLabel35.setVisible(false);
                jLabel17.setVisible(false);
                jTextField11.setVisible(false);

                //width
                jLabel34.setVisible(true);
                jLabel16.setVisible(true);
                jTextField10.setVisible(true);

                //Inner Diameter
                jLabel39.setVisible(false);
                jLabel23.setVisible(false);
                jTextField15.setVisible(false);

                //Outer Diameter
                jLabel40.setVisible(false);
                jLabel24.setVisible(false);
                jTextField16.setVisible(false);

                //Wall
                jLabel41.setVisible(false);
                jLabel25.setVisible(false);
                jTextField17.setVisible(false);      

                //height
                jLabel46.setVisible(false);
                jLabel44.setVisible(false);
                jTextField18.setVisible(false);

                //radius
                jLabel47.setVisible(false);
                jLabel45.setVisible(false);
                jTextField19.setVisible(false);
                
                //Length
                jLabel33.setVisible(true); 
                jLabel9.setVisible(true); 
                jTextField9.setVisible(true);
             
                //Diameter
                jLabel37.setVisible(false);
                jLabel22.setVisible(false);
                jTextField14.setVisible(false);
            break;
            case "Rectangle":
                //Thickness
                jLabel17.setVisible(false);
                jLabel35.setVisible(false);
                jTextField11.setVisible(false);

                //width
                jLabel34.setVisible(true);
                jLabel16.setVisible(true);
                jTextField10.setVisible(true);

                //Inner Diameter
                jLabel39.setVisible(false);
                jLabel23.setVisible(false);
                jTextField15.setVisible(false);

                //Outer Diameter
                jLabel40.setVisible(false);
                jLabel24.setVisible(false);
                jTextField16.setVisible(false);

                //Wall
                jLabel41.setVisible(false);
                jLabel25.setVisible(false);
                jTextField17.setVisible(false);      

                //height
                jLabel46.setVisible(true);
                jTextField18.setVisible(true);
                jLabel44.setText("Height");
                jLabel44.setVisible(true);

                //radius
                jLabel47.setVisible(false);
                jTextField19.setVisible(false);
                jLabel45.setVisible(false);

                
                //Length
                jLabel33.setVisible(true);
                jTextField9.setVisible(true);
                jLabel9.setVisible(true);

                //Diameter
                jLabel37.setVisible(false);
                jLabel22.setVisible(false);
                jTextField14.setVisible(false);

            break;
            case "Hexagonal":
                //Thickness
                jLabel35.setVisible(false);                
                jLabel17.setVisible(false);
                jTextField11.setVisible(false);
                jTextField11.setText("0.0");
                //width
                jLabel34.setVisible(false);
                jLabel16.setVisible(false);
                jTextField10.setVisible(false);
                jTextField10.setText("0.0");
                //Inner Diameter
                jLabel39.setVisible(false);                
                jLabel23.setVisible(false);
                jTextField15.setVisible(false);
                jTextField15.setText("0.0");
                
                //Outer Diameter
                jLabel40.setVisible(false);  
                jLabel24.setVisible(false);
                jTextField16.setVisible(false);
                jTextField16.setText("0.0");

                //Wall
                jLabel41.setVisible(false);
                jLabel25.setVisible(false);
                jTextField17.setVisible(false);      
                jTextField17.setText("0.0");

                //height
                jLabel46.setVisible(false);                
                jTextField18.setVisible(false);
                jLabel44.setVisible(false);
                jTextField18.setText("0.0");

                //radius
                jLabel47.setVisible(false);
                jTextField19.setVisible(false);
                jLabel45.setVisible(false);

                
                //Length
                jLabel33.setVisible(true);
                jTextField9.setVisible(true);
                jLabel9.setVisible(true);

                //Diameter

                jLabel37.setVisible(true);
                jLabel22.setVisible(true);
                jTextField14.setVisible(true);

            break;
            case "Sheet":
                //Thickness
                jLabel17.setVisible(true);
                jLabel35.setVisible(true);
                jTextField11.setVisible(true);

                //width
                jLabel34.setVisible(true);
                jLabel16.setVisible(true);
                jTextField10.setVisible(true);

                //Inner Diameter
                jLabel39.setVisible(false);
                jLabel23.setVisible(false);
                jTextField15.setVisible(false);

                //Outer Diameter
                jLabel40.setVisible(false);
                jLabel24.setVisible(false);
                jTextField16.setVisible(false);

                //Wall
                jLabel41.setVisible(false);
                jLabel25.setVisible(false);
                jTextField17.setVisible(false);      

                //height
                jLabel46.setVisible(false);
                jTextField18.setVisible(false);
                jLabel44.setVisible(false);

                //radius
                jLabel47.setVisible(false);
                jTextField19.setVisible(false);
                jLabel45.setVisible(false);
                
                //Length
                jLabel33.setVisible(true);
                jTextField9.setVisible(true);
                jLabel9.setVisible(true);

                //Diameter
                jLabel37.setVisible(false);
                jLabel22.setVisible(false);
                jTextField14.setVisible(false);
            break;
            case "Plate":
                //Thickness
                jLabel17.setVisible(false);
                jLabel35.setVisible(false);
                jTextField11.setVisible(false);

                //width
                jLabel34.setVisible(true);
                jLabel16.setVisible(true);
                jTextField10.setVisible(true);

                //Inner Diameter
                jLabel39.setVisible(false);
                jLabel23.setVisible(false);
                jTextField15.setVisible(false);

                //Outer Diameter
                jLabel40.setVisible(false);
                jLabel24.setVisible(false);
                jTextField16.setVisible(false);

                //Wall
                jLabel41.setVisible(false);
                jLabel25.setVisible(false);
                jTextField17.setVisible(false);      

                //height
                jLabel44.setText("Height");
                jLabel46.setVisible(true);
                jTextField18.setVisible(true);
                jLabel44.setVisible(true);

                //radius
                jLabel47.setVisible(false);
                jTextField19.setVisible(false);
                jLabel45.setVisible(false);

                
                //Length
                jLabel33.setVisible(true);
                jTextField9.setVisible(true);
                jLabel9.setVisible(true);

                //Diameter
                jLabel37.setVisible(false);
                jLabel22.setVisible(false);
                jTextField14.setVisible(false);

            break;
            case "Tubular":
                //Thickness
                jLabel17.setVisible(false);
                jLabel35.setVisible(false);
                jTextField11.setVisible(false);

                //width
                jLabel34.setVisible(false);
                jLabel16.setVisible(false);
                jTextField10.setVisible(false);

                //Inner Diameter
                jLabel39.setVisible(true);
                jLabel23.setVisible(true);
                jTextField15.setVisible(true);

                //Outer Diameter
                jLabel40.setVisible(true);
                jLabel24.setVisible(true);
                jTextField16.setVisible(true);

                //Wall
                jLabel41.setVisible(false);
                jLabel25.setVisible(false);
                jTextField17.setVisible(false);      

                //height
                jLabel46.setVisible(false);
                jTextField18.setVisible(false);
                jLabel44.setVisible(false);

                //radius
                jLabel47.setVisible(false);
                jTextField19.setVisible(false);
                jLabel45.setVisible(false);

                
                //Length
                jLabel33.setVisible(true);
                jTextField9.setVisible(true);
                jLabel9.setVisible(true);

                //Diameter
                jLabel37.setVisible(false);
                jLabel22.setVisible(false);
                jTextField14.setVisible(false);

                break;
            case "Ring":
                //Thickness
                jLabel35.setVisible(false);                
                jLabel17.setVisible(false);
                jTextField11.setVisible(false);
                jTextField11.setText("0.0");
                //width
                jLabel34.setVisible(false);
                jLabel16.setVisible(false);
                jTextField10.setVisible(false);
                jTextField10.setText("0.0");
                //Inner Diameter
                jLabel39.setVisible(false);                
                jLabel23.setVisible(false);
                jTextField15.setVisible(false);
                jTextField15.setText("0.0");
                
                //Outer Diameter
                jLabel40.setVisible(false);  
                jLabel24.setVisible(false);
                jTextField16.setVisible(false);
                jTextField16.setText("0.0");

                //Wall
                jLabel41.setVisible(false);
                jLabel25.setVisible(false);
                jTextField17.setVisible(false);      
                jTextField17.setText("0.0");

                //height
                jLabel46.setVisible(false);                
                jTextField18.setVisible(false);
                jLabel44.setVisible(false);
                jTextField18.setText("0.0");

                //radius
                jLabel47.setVisible(false);
                jTextField19.setVisible(false);
                jLabel45.setVisible(false);

                
                //Length
                jLabel33.setVisible(true);
                jTextField9.setVisible(true);
                jLabel9.setVisible(true);

                //Diameter

                jLabel37.setVisible(true);
                jLabel22.setVisible(true);
                jTextField14.setVisible(true);
                
            break;
            case "Pipe":
                //Thickness
                jLabel17.setVisible(false);
                jLabel35.setVisible(false);
                jTextField11.setVisible(false);

                //width
                jLabel34.setVisible(false);
                jLabel16.setVisible(false);
                jTextField10.setVisible(false);

                //Inner Diameter
                jLabel39.setVisible(true);
                jLabel23.setVisible(true);
                jTextField15.setVisible(true);

                //Outer Diameter
                jLabel40.setVisible(true);
                jLabel24.setVisible(true);
                jTextField16.setVisible(true);

                //Wall
                jLabel41.setVisible(false);
                jLabel25.setVisible(false);
                jTextField17.setVisible(false);      

                //height
                jLabel46.setVisible(false);
                jTextField18.setVisible(false);
                jLabel44.setVisible(false);

                //radius
                jLabel47.setVisible(false);
                jTextField19.setVisible(false);
                jLabel45.setVisible(false);

                
                //Length
                jLabel33.setVisible(true);
                jTextField9.setVisible(true);
                jLabel9.setVisible(true);

                //Diameter
                jLabel37.setVisible(false);
                jLabel22.setVisible(false);
                jTextField14.setVisible(false);
            break;
            default:
            break;
        }
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Workbook wb = new HSSFWorkbook ();

        ArrayList<String> column_names = new ArrayList<String> ();
        ArrayList<String[]> values = new ArrayList<String[]> ();
        int columnCount = 0;
        File file = null;
        try {
            ResultSet result = DB_Operations.getMasters ( "raw_material" );
            if ( result != null ) {
                ResultSetMetaData metaData = result.getMetaData ();
                // names of columns
                columnCount = metaData.getColumnCount ();
                for ( int column = 2 ; column <= columnCount - 3 ; column ++ ) {
                    column_names.add ( metaData.getColumnName ( column ) );
                }
            }
            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
            while ( result.next () ) {
                String[] val = new String[ columnCount ];
                for ( int columnIndex = 2 ; columnIndex <= columnCount ; columnIndex ++ ) {
                    if ( columnIndex < columnCount - 2 ) {
                        val[ columnIndex - 2 ] = String.valueOf ( result.getObject ( columnIndex ) );
                    }
                }
                values.add ( val );
            }
        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel (Raw_Material_Quotation.class.getSimpleName () , Raw_Material_Quotation.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
        sheet1 = wb.createSheet ( "Raw Material Master" );
        //sheet1.setColumnWidth(0, (15 * 500));

        Cell c = null;
        Cell c3 = null;

        Row row = sheet1.createRow ( 1 );
        //  c = row.createCell(0);
        // c.setCellValue("");
        // c.setCellStyle(cs);

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
            file = new File ( "Raw Material Master File " + strDate2 + ".xls" );

            FileOutputStream os = null;

            try {
                os = new FileOutputStream ( file );
                wb.write ( os );
                wb.close ();
                os.close ();
                //    JOptionPane.showMessageDialog(null, "New File generated at " + file.getAbsolutePath());
            } catch ( IOException e ) {
                JOptionPane.showMessageDialog ( null , "Error " + e.getMessage () );
                StaticValues.writer.writeExcel (Raw_Material_Quotation.class.getSimpleName () , Raw_Material_Quotation.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            } finally {

            }

        }
        JOptionPane.showMessageDialog ( null , "New File generated at " + file.getAbsolutePath () );
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        HomeScreen3.home.removeForms ();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        resetFields ();
        jButton2.setEnabled ( true );
        //    jComboBox4.setEnabled(true) ;
    }//GEN-LAST:event_jButton4ActionPerformed

    ProgressDialog progress = new ProgressDialog( null ) ;
    Thread getTableAndFilesContent = null ;
    public boolean showDialog(){
        getTableAndFilesContent = new Thread(){
            public void run(){
                showValuesFromTable ();
            }
        };
        getTableAndFilesContent.start();
        progress.openProgressWindow ();
        return true ;
    }
    
    public boolean hideDialog(){
        progress.closeProgressWindow ();
        return true ;
    }
    
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:

        showDialog ();
        
         
        /*
         * // jComboBox4.setEnabled ( false ); ArrayList<String> values = new
         * ArrayList<String>(); ArrayList<byte[]> files = new
         * ArrayList<byte[]>(); int columnCount = 0; // get the model from the
         * jtable DefaultTableModel model = (DefaultTableModel)
         * jTable1.getModel(); // get the selected row index int
         * selectedRowIndex = jTable1.getSelectedRow(); //
         * Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString() )
         * // JOptionPane.showMessageDialog(null, "" +
         * model.getValueAt(selectedRowIndex, 0).toString() ); try { //
         * ResultSet result = DB_Operations.getSingleMasters("raw_material",
         * Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString()));
         * selectedEmpRecordId =
         * Integer.parseInt(model.getValueAt(selectedRowIndex, 0).toString());
         * jTextField6.setText(model.getValueAt(selectedRowIndex, 2).toString()
         * );
         *
         * for( int i=0; i<jComboBox1.getItemCount (); i++ ){ if(
         * jComboBox1.getItemAt (i).toString().equals (
         * model.getValueAt(selectedRowIndex, 1).toString() ) ){
         * jComboBox1.setSelectedIndex ( i ) ; } }
         *
         * jTextField1.setText(model.getValueAt(selectedRowIndex, 4).toString()
         * ); jTextField5.setText(model.getValueAt(selectedRowIndex,
         * 5).toString() );
         *
         * for( int i=0; i<jComboBox2.getItemCount (); i++ ){ if(
         * jComboBox2.getItemAt (i).toString().equals (
         * model.getValueAt(selectedRowIndex, 3).toString() ) ){
         * jComboBox2.setSelectedIndex ( i ) ; } }
         *
         * for( int i=0; i<jComboBox4.getItemCount (); i++ ){ if(
         * jComboBox4.getItemAt (i).toString().equals (
         * model.getValueAt(selectedRowIndex, 6).toString() ) ){
         * jComboBox4.setSelectedIndex ( i ) ; } }
         *
         * // if (result != null) { // ResultSetMetaData metaData =
         * result.getMetaData(); // // columnCount = metaData.getColumnCount();
         * // // while (result.next()) { // // for (int columnIndex = 1;
         * columnIndex <= columnCount; columnIndex++) { // if (columnIndex <
         * columnCount - 2) { //
         * values.add(String.valueOf(result.getObject(columnIndex))); //
         * System.out.println( result.getObject(columnIndex) ); // } // }
         *
         * jLabel13.setText( model.getValueAt(selectedRowIndex,6).toString()) ;
         * jLabel14.setText( model.getValueAt(selectedRowIndex, 6).toString()) ;
         * // } //} } catch (Exception e) { System.out.println("Error " +
         * e.getMessage()); StaticValues.writer.writeExcel
         * (Raw_Material.class.getSimpleName () ,
         * Raw_Material.class.getSimpleName () , e.getClass ().toString () ,
         * Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" ,
         * e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance
         * ().getTime () ) ); }
         */
       

    }//GEN-LAST:event_jTable1MouseClicked

    public void showValuesFromTable(){
        
        jButton2.setEnabled ( false );

        DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();
        int selectedRowIndex = jTable1.getSelectedRow ();
        selectedEmpRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );
        jTextField7.setText ( model.getValueAt ( selectedRowIndex , 1 ).toString () );//rmName
        jTextField1.setText ( model.getValueAt ( selectedRowIndex , 2 ).toString () );//RM_Rate        
        jComboBox4.setSelectedItem( model.getValueAt ( selectedRowIndex , 3 ).toString ());
        jTextField13.setText ( model.getValueAt ( selectedRowIndex , 4 ).toString () );//tax
        jTextField20.setText ( model.getValueAt ( selectedRowIndex , 5 ).toString () );//icc
        jTextField8.setText ( model.getValueAt ( selectedRowIndex , 6 ).toString () );
        jTextField12.setText ( model.getValueAt ( selectedRowIndex , 7 ).toString () );
        jComboBox3.setSelectedItem( model.getValueAt ( selectedRowIndex , 8 ).toString () );
        jTextField14.setText ( model.getValueAt ( selectedRowIndex , 9 ).toString () );
        jTextField9.setText ( model.getValueAt ( selectedRowIndex , 10 ).toString () );
        jTextField10.setText ( model.getValueAt ( selectedRowIndex , 11 ).toString () );
        jTextField11.setText ( model.getValueAt ( selectedRowIndex , 12 ).toString () );
        jTextField18.setText ( model.getValueAt ( selectedRowIndex , 13 ).toString () );
        jTextField15.setText ( model.getValueAt ( selectedRowIndex , 14 ).toString () );
        jTextField16.setText ( model.getValueAt ( selectedRowIndex , 15 ).toString () );

//        UOM_ID = UOM.get( jComboBox4.getSelectedIndex () )[0];

//        jTextField7.setText ( model.getValueAt ( selectedRowIndex , 2 ).toString () );
//        jTextField1.setText ( model.getValueAt ( selectedRowIndex , 3 ).toString () );
//        jTextField5.setText ( model.getValueAt ( selectedRowIndex , 4 ).toString () );
//        jTextField6.setText ( model.getValueAt ( selectedRowIndex , 6 ).toString () );
//        
//        jTextField9.setText ( model.getValueAt ( selectedRowIndex , 7 ).toString () );
//        jTextField10.setText ( model.getValueAt ( selectedRowIndex , 8 ).toString () );
//        jTextField11.setText ( model.getValueAt ( selectedRowIndex , 9 ).toString () );
//        jTextField12.setText ( model.getValueAt ( selectedRowIndex , 10 ).toString () );
//        
//        jTextField2.setText ( model.getValueAt ( selectedRowIndex , 11 ).toString () );
//        jTextField3.setText ( model.getValueAt ( selectedRowIndex , 12 ).toString () );
//        jTextField4.setText ( model.getValueAt ( selectedRowIndex , 13 ).toString () );
//        jTextArea1.setText ( model.getValueAt ( selectedRowIndex , 14 ).toString () );
        
        
        
        
       // loadDocumentsForRM(  selectedEmpRecordId ) ;
        loadShapesparameter();
        hideDialog();
         jButton5.setEnabled ( true );
    }
    
    
    public void loadDocumentsForRM( int _selectedEmpRecordId){
        
        progress.updateMessage ("Getting reference files information");
        
        String addEmpAPICall = "rdm";
        String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
         String docTypeIds = "";            
        if( !  result2.contains(  "not found"   ) ){
            HashMap<String, Object> map = new HashMap<String, Object>();
            JSONObject jObject = new JSONObject( result2 );
            Iterator<?> keys = jObject.keys();
            while( keys.hasNext() ){
                String key = (String)keys.next();
                Object value = jObject.get ( key ) ; 
                map.put(key, value);
            }
            JSONObject st = (JSONObject) map.get ( "data" );
            JSONArray records = st.getJSONArray ( "records");
            //String[] docTypeIds = new String[];
           
            JSONObject emp = null;
            //docList = new ArrayList<String[]> ();
            for ( int i = 0 ; i < records.length () ; i ++ ) {

                emp = records.getJSONObject ( i);

                if( emp.getString ("RDM_NAME").contains("RM - ") ){
                    //docList.add( new String[]{ emp.getString ("RDM_UID"),emp.getString ("RDM_NAME")  }   ); 
                    docTypeIds = docTypeIds + emp.get ("RDM_UID").toString()+"," ;
                }
            }
        }
        
        String[] docTypeIdsArr = docTypeIds.split(",") ;
        DefaultListModel<String> docsList = null ;
        for( int i=0; i<docTypeIdsArr.length; i++ ){
            
            if( ! docTypeIdsArr[i].equals( "" ) ){
                addEmpAPICall = "refdocs?RDM_UID="+docTypeIdsArr[i];
                result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                
                if( !  result2.contains(  "not found"   ) ){
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jObject = new JSONObject( result2 );
                    Iterator<?> keys = jObject.keys();
                    while( keys.hasNext() ){
                        String key = (String)keys.next();
                        Object value = jObject.get ( key ) ; 
                        map.put(key, value);
                    }
                    JSONObject st = (JSONObject) map.get ( "data" );
                    JSONArray records = st.getJSONArray ( "records");
                    //String[] docTypeIds = new String[];

                    JSONObject emp = null;
                    docList = new ArrayList<String[]> ();
                    docsList = new DefaultListModel<String>() ;
                    for ( int j = 0 ; j < records.length () ; j ++ ) {

                        emp = records.getJSONObject ( j );

                        if(  emp.get("item_id") != null )  {
                            if(   emp.get("item_id").equals (  _selectedEmpRecordId )){
                            //if(   emp.getString ("item_id").equals( _selectedEmpRecordId )){
                                docList.add( new String[]{ emp.get ("RD_UID").toString(),emp.get("RD_NAME").toString()  }   );
                                docsList.addElement( emp.getString ("RD_NAME")  );
                            }
                        }
                    }
                }
            }
        }
        
         jList1.setModel(  docsList  ) ;
        
        if(docsList.size ()>0 ){
            jScrollPane1.setVisible ( true);
            jLabel19.setVisible( true );
        }else{
            jScrollPane1.setVisible ( false);
            jLabel19.setVisible(false );
        }
        
        hideDialog ();
        
    }
    
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:

        String RM_TYPE, RM_NAME, RM_CTG, RM_RATE, RM_CRIT, RM_EC_NO, RM_CAS_NO, GST_NO, RM_CLASS, REORDER_LEVEL, RM_CODE, EDITED_ON, EDITED_BY;

        String length = "0.0", width= "0.0", thickness= "0.0", density= "0.0" ;
        
//        RM_TYPE = jComboBox1.getSelectedItem().toString();
//        RM_NAME = jTextField6.getText ();
//        RM_CTG = jComboBox2.getSelectedItem().toString();
//        RM_RATE = jTextField1.getText();
//        RM_CRIT = jTextArea1.getText();
//        RM_EC_NO = jTextField2.getText();
//        RM_CAS_NO = jTextField3.getText();
//        GST_NO = jTextField4.getText();
//        RM_CLASS = jComboBox2.getSelectedItem().toString();
//        REORDER_LEVEL = jTextField5.getText();
       
        
        RM_TYPE = jComboBox1.getSelectedItem ().toString ();
        RM_CTG = jComboBox2.getSelectedItem ().toString ();
        RM_CLASS =  jTextField6.getText ();
        RM_NAME = jTextField7.getText ();
        
        //RM_AUTH_SUPP = jComboBox4.getSelectedItem().toString();
        RM_RATE = jTextField1.getText ();
        RM_CRIT = jTextArea1.getText ();
        RM_EC_NO = jTextField2.getText ();
        RM_CAS_NO = jTextField3.getText ();
        GST_NO = jTextField4.getText ();
        RM_CODE = jTextField6.getText();
        REORDER_LEVEL = jTextField5.getText ();
        
        
        REORDER_LEVEL = jTextField5.getText ();

        
        length = jTextField9.getText ();
        width = jTextField10.getText ();
        thickness = jTextField11.getText ();
        density = jTextField12.getText ();

        String Height=jTextField18.getText();
        String radius="";        
        String unit = UOM.get ( jComboBox4.getSelectedIndex () )[0];

        EDITED_ON = "EDITED_BY";
        EDITED_BY = "EDITED_ON";

        int ID = selectedEmpRecordId;

        length = jTextField9.getText ();
        width = jTextField10.getText ();
        thickness = jTextField11.getText ();
        density = jTextField12.getText ();
        Height=jTextField18.getText();
        radius="";
        String inDiam=jTextField15.getText();
        String outDiam=jTextField16.getText();
        String diam=jTextField14.getText();
        String wall=jTextField17.getText();
        String shape=jComboBox3.getSelectedItem().toString();
        String UOM_ID = UOM.get( jComboBox4.getSelectedIndex () )[0];
        String scrapRate=jTextField8.getText();
        String icc=jTextField20.getText();
        String tax=jTextField13.getText();

       // String result = DB_Operations.updateRawMaterialMaster ( RM_TYPE , RM_NAME , RM_CTG , RM_RATE , RM_CRIT , RM_EC_NO , RM_CAS_NO , GST_NO , RM_CLASS , REORDER_LEVEL , EDITED_ON , EDITED_BY , unit , ID );

//        if (  ! RM_TYPE.equals ( "" ) &&  ! RM_NAME.equals ( "" ) &&  ! RM_RATE.equals ( "" ) &&  ! REORDER_LEVEL.equals ( "" )   &&  ! length .equals (  "") &&   ! width.equals (  "") && ! thickness.equals (  "") && ! density.equals (  "")) {
            try {
                String addEmpAPICall = "rm_quotation_update?RM_ID="+selectedEmpRecordId+"&RM_TYPE=" + URLEncoder.encode ( RM_TYPE , "UTF-8" ) + "&RM_NAME=" + URLEncoder.encode ( RM_NAME , "UTF-8" ) + "&RM_CTG=" + URLEncoder.encode ( RM_CTG , "UTF-8" ) + "&RM_AUTH_SUPP=" + URLEncoder.encode ( "" , "UTF-8" ) + "&RM_RATE=" + URLEncoder.encode ( RM_RATE , "UTF-8" ) + "&RM_CRIT=" + URLEncoder.encode ( RM_CRIT , "UTF-8" ) 
                        + "&RM_EC_NO=" + URLEncoder.encode ( RM_EC_NO , "UTF-8" ) + "&RM_CAS_NO=" + URLEncoder.encode ( RM_CAS_NO , "UTF-8" ) + "&GST_NO=" + URLEncoder.encode ( GST_NO , "UTF-8" ) + "&RM_CLASS=" + URLEncoder.encode ( RM_CODE , "UTF-8" ) + "&RM_CODE=" + URLEncoder.encode ( RM_CODE , "UTF-8" ) 
                        + "&REORDER_LEVEL=" + URLEncoder.encode ( REORDER_LEVEL , "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( EDITED_ON , "UTF-8" ) + "&EDITED_BY=" + URLEncoder.encode ( EDITED_BY , "UTF-8" ) + "&RMM_UOM_ID=" + URLEncoder.encode ( UOM_ID , "UTF-8" )
                        + "&length="+length +"&width="+ width+ "&thickness="+ thickness+ "&density="+density+"&height="+Height+"&radius="+radius+"&in_diameter="+inDiam+"&wall="+wall+"&out_diameter="+outDiam+"&diameter="+diam+"&shape="+shape+"&icc="+icc+"&scrap_rate="+scrapRate+"&tax="+tax;
                String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                JOptionPane.showMessageDialog ( null , result2 );

            } catch ( UnsupportedEncodingException e ) {

            }
//        } else {
//            JOptionPane.showMessageDialog ( null , "Please fill all fields marked with * " );
//        }
        
        
        //JOptionPane.showMessageDialog ( null , "" + result );

        resetFields ();
        loadContent ();


    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        if ( jComboBox1.getItemCount () == 0 ) {

            JOptionPane.showMessageDialog ( null , "<html><size='06'>Add Values First in Type from Utility Master" );

        }         // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox4MouseClicked
        if ( jComboBox4.getItemCount () == 0 ) {

            JOptionPane.showMessageDialog ( null , "<html><size='06'>Add Values First in Rate from Utility Master" );

        } else {

            jLabel13.setText ( jComboBox4.getSelectedItem ().toString () );
//            jLabel14.setText ( jComboBox4.getSelectedItem ().toString () );
            jLabel49.setText ( jComboBox4.getSelectedItem ().toString () );
        }
    }//GEN-LAST:event_jComboBox4MouseClicked

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jTable1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost
        // TODO add your handling code here:
        //      JOptionPane.showMessageDialog ( null, "asdasdadasdasdass");
    }//GEN-LAST:event_jTable1FocusLost

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        
        int selection = JOptionPane.showConfirmDialog ( null , "Deleting a record is not recommendedThe 'Delete' action may cause loss of any dependencies and may affect the transactional Data and Reports and make data inconsistent\nDeleting a plant will automatically delete all shifts for that plant\nPress 'No' to revert, Press 'Yes' to continue deleting", "Are you sure about deleting this record ?", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE);
        if ( selection == JOptionPane.YES_OPTION ) { 
            deleteMaster(  "rm_quotation_delete?RM_ID="+selectedEmpRecordId    ) ;
            
            String addEmpAPICall = "rm_quotation";
            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

            if(   !  result2.contains ( "not found")  ){

                if ( result2 != null ) {
                    jTable1.setModel ( buildTableModelfromJSON ( result2 ) );
                     TableModiFier.setTableSize(jTable1,30,110);
                    jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                    jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                }
            }
        }
    }//GEN-LAST:event_jButton6MouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        String[] selectedRecord = docList.get(jList1.getSelectedIndex () ) ;

        
        String addEmpAPICall = "readrmdoc?RD_UID="+selectedRecord[0];
            String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

        if( !  result2.contains(  "not found"   )  ){
            if ( result2!= null ) {
                
                try {
                
                        String filedata =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                        JSONObject responseObject = (JSONObject) new JSONTokener(  filedata   ).nextValue() ;
                        String fileData = responseObject.getJSONObject ( "data").getString("binarydata") ;
                        byte[] encoded = Base64.decodeBase64 (fileData );

                        String tempFileName = responseObject.getJSONObject ( "data").getString("name") ;
                        File downloaded = new File( tempFileName  ) ;
                        FileUtils.writeByteArrayToFile(      downloaded   , encoded) ;
                        Desktop desktop = Desktop.getDesktop ();
                        desktop.open ( downloaded );

                    } catch ( IOException ex ) {
                        //    Logger.getLogger(FinishedGoodMaster.class.getName()).log(Level.SEVERE, null, ex);
                        //StaticValues.writer.writeExcel ( DocumentRepository.class.getSimpleName () , DocumentRepository.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }
                
                
            }
        }  
    }//GEN-LAST:event_jList1MouseClicked

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

     public static String retreiveSuccessMessage ( String json ) {
        String id = "Not able to fetch the response for your request. Please refresh you master form and try again";
        JSONObject jObject = new JSONObject ( json );
        id = jObject.getString ( "118" );
        return id;
    }
        
    public  void deleteMaster( String apiName  ){
        String response  = WebAPITester.prepareWebCall ( apiName,  StaticValues.getHeader () , "");
        JSONObject responseObject = ( JSONObject ) new JSONTokener ( response ).nextValue ();
        JOptionPane.showMessageDialog ( null , retreiveSuccessMessage ( responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () ) );
    }
        
        
    ActionListener selectUnitAction = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
            jLabel13.setText ( jComboBox4.getSelectedItem ().toString () );
//            jLabel14.setText ( jComboBox4.getSelectedItem ().toString () );
            jLabel49.setText ( jComboBox4.getSelectedItem ().toString () );

        }
    };

    public void resetFields () {

        jTextField1.setText ( "" );
        jTextArea1.setText ( "" );
        jTextField2.setText ( "" );
        jTextField3.setText ( "" );
        jTextField4.setText ( "" );
        jTextField5.setText ( "" );
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField9.setText("");
        jTextField10.setText("");
        jTextField11.setText("");
        jTextField12.setText("");
        jTextField14.setText("");
        jTextField15.setText("");
        jTextField16.setText("");
        jTextField17.setText("");
        jTextField18.setText("");
        jTextField19.setText("");

        jButton2.setEnabled ( true );
    }
    public void componentVisibility()
    {
        jLabel42.setVisible(false);
        jLabel43.setVisible(false);
        jLabel32.setVisible(false);
        jLabel38.setVisible(false);
        jLabel27.setVisible(false);
        jLabel30.setVisible(false);
        jLabel21.setVisible(false);
        jLabel2.setVisible(false);
        jLabel1.setVisible(false);
        jLabel11.setVisible(false);
        jTextField5.setVisible(false);
        jTextField6.setVisible(false);
        jComboBox1.setVisible(false);
        jComboBox2.setVisible(false);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    int selectedEmpRecordId = 0;
}
