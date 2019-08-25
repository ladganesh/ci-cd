/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import trixware.erp.prodezydesktop.Model.QualityRMDR;
import trixware.erp.prodezydesktop.Utilities.DateTimeFormatter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileSystemView;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import trixware.erp.prodezydesktop.Model.Main_typeModel_quality;
import trixware.erp.prodezydesktop.Model.Maintain_typeDR;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.components.ProgressDialog;
import trixware.erp.prodezydesktop.components.QualityPanel;
import trixware.erp.prodezydesktop.examples.HomeScreen3;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class Raw_Material extends javax.swing.JPanel 
{
    JPanel jp1;
    
    ArrayList<String[]> UOM_selected = null;
    ArrayList<QualityRMDR> Id_Name_List = null;
    RMQualityParamList maintain = null,maintain1=null ; 
    String resource = "", resourceType = "";
    ArrayList<Maintain_typeDR> maintain_type = null;
    ArrayList<Main_typeModel_quality> maintain_type1=null;
    /**
     * Creates new form Raw_Material
     */
    public Raw_Material () 
    {
        initComponents ();
        
        

        resourceType = "Masters";
        resource = "Raw_Material";

//        try {
//            StaticValues.checkUserRolePermission ( resourceType , resourceType );
//        } catch ( Exception e ) {
//            StaticValues.writer.writeExcel ( resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }
         

//  Quality Code Hidden
      //  maintain = new RMQualityParamList ();
      //  maintain.setBounds ( 700 , 20 , 300 , 200 );
       // add ( maintain );
       // maintain.setBounds ( 800 , 20 , 260 , 260 );
//  Quality Code Hidden
      
        
        
        
      // jScrollPane2.setVisible(false);
      // jTextArea1.setVisible(false);
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
        
        //jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        //sjScrollPane2.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        jScrollPane3.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane3.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        
        jScrollPane1.setVisible ( false);
        jLabel19.setVisible(false );
        
        
         repaint();
          revalidate();
         
          setBackground(Color.WHITE);
          //setVisible(true);

    }

    ArrayList<String[]> UOM = null;
    ArrayList<String[]> RMType = null;
    ArrayList<String[]> category = null;
    ArrayList<String[]> rmcode=null;
    ArrayList<String[]> docList = null ;
    

    public void loadContent () 
    {
        try {
            ResultSet result = DB_Operations.getMasters ( "raw_material" );
            // jTable1.setModel(DbUtils.resultSetToTableModel(result)); 

//                jTable1.setModel(buildTableModel(result));
//
//                jTable1.getColumnModel().getColumn(0).setMinWidth(0);
//                jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
            String addEmpAPICall = "rawmaterials";
            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

            if(   !  result2.contains ( "not found")  ){

                if ( result2 != null ) 
                {
                    jTable1.setModel ( buildTableModelfromJSON ( result2 ) );
                    jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                    jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                    jTable1.getColumnModel ().getColumn ( 15 ).setMinWidth ( 0 );
                    jTable1.getColumnModel ().getColumn ( 15 ).setMaxWidth ( 0 );
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
//
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
                
                
                result.close ();
            
            // JTable table = new JTable(buildTableModel(result));
            // Closes the Connection
            // JOptionPane.showMessageDialog(null, new JScrollPane(table));
        } catch ( SQLException e ) {
            System.out.println ( "" + e.getMessage () );
            StaticValues.writer.writeExcel ( Raw_Material.class.getSimpleName () , Raw_Material.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }
     
    }

    public static DefaultTableModel buildTableModelfromJSON ( String employeeJSONList ) 
    {

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
        columnNames.add ( "RM_CTG" );
        columnNames.add ( "RM_NAME" );
        //columnNames.add ( "RM_AUTH_SUPP" );
        columnNames.add ( "RM_RATE" );
        columnNames.add ( "REORDER_LEVEL" );
        columnNames.add ( "RMM_UOM_ID" );
        columnNames.add("RM_CLASS");
        columnNames.add ( "length" );
        columnNames.add ( "width" );
        columnNames.add ( "thickness" );
        columnNames.add("density");

        columnNames.add ( "RM_EC_NO" );
        columnNames.add ( "RM_CAS_NO" );
        columnNames.add ( "GST_NO" );
        columnNames.add("RM_CRIT");
        columnNames.add("bought_out");
 
        
        columnNames2 = new Vector<String> ();
        columnNames2.add ( "RM_ID" );
        columnNames2.add ( "RM_CTG" );
        columnNames2.add ( "RM_NAME" );
        //columnNames.add ( "RM_AUTH_SUPP" );
        columnNames2.add ( "RM_RATE" );
        columnNames2.add ( "REORDER_LEVEL" );
        columnNames2.add ( "RMM_UOM_ID" );
        columnNames2.add("RM_CODE");
        columnNames2.add ( "length" );
        columnNames2.add ( "width" );
        columnNames2.add ( "thickness" );
        columnNames2.add("density");

        columnNames2.add ( "RM_EC_NO" );
        columnNames2.add ( "RM_CAS_NO" );
        columnNames2.add ( "GST_NO" );
        columnNames2.add("RM_CRIT");
        columnNames2.add("bought_out");
        
        
                
        
        
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) 
        {
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
                StaticValues.writer.writeExcel ( Raw_Material.class.getSimpleName () , Raw_Material.class.getSimpleName () , ex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
        jLabel3 = new javax.swing.JLabel();
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
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel36 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1144, 592));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Type :");
        add(jLabel1);
        jLabel1.setBounds(40, 90, 130, 30);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
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
        jComboBox1.setBounds(170, 90, 200, 30);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Category RM\\Part :");
        add(jLabel2);
        jLabel2.setBounds(40, 130, 130, 30);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox2.setNextFocusableComponent(jTextField6);
        add(jComboBox2);
        jComboBox2.setBounds(170, 130, 200, 30);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("<html> Rate / Unit </html>");
        add(jLabel4);
        jLabel4.setBounds(390, 210, 130, 30);

        jTextField1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField1.setNextFocusableComponent(jComboBox4);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(490, 210, 100, 30);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Acceptance Criteria :");
        add(jLabel5);
        jLabel5.setBounds(750, 130, 130, 0);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("EC No :");
        add(jLabel6);
        jLabel6.setBounds(750, 10, 130, 0);

        jTextField2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField2.setNextFocusableComponent(jTextField3);
        add(jTextField2);
        jTextField2.setBounds(880, 10, 200, 0);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("CAS No :");
        add(jLabel7);
        jLabel7.setBounds(750, 50, 130, 0);

        jTextField3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField3.setNextFocusableComponent(jTextField4);
        add(jTextField3);
        jTextField3.setBounds(880, 50, 200, 0);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("GST No :");
        add(jLabel8);
        jLabel8.setBounds(750, 90, 130, 0);

        jTextField4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jTextField4);
        jTextField4.setBounds(880, 90, 200, 0);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Cancel");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(630, 300, 140, 30);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Add to Master");
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(630, 270, 140, 30);

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
        jScrollPane3.setBounds(30, 340, 1050, 210);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Export to Excel");
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(780, 300, 140, 30);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("Reorder Level");
        add(jLabel11);
        jLabel11.setBounds(390, 170, 130, 30);

        jTextField5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jTextField5);
        jTextField5.setBounds(490, 170, 200, 30);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("Reset");
        jButton4.setOpaque(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(930, 270, 140, 30);

        jButton5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton5.setText("Edit Record");
        jButton5.setOpaque(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        add(jButton5);
        jButton5.setBounds(780, 270, 140, 30);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("RM Name :");
        add(jLabel3);
        jLabel3.setBounds(40, 170, 80, 30);

        jTextField6.setNextFocusableComponent(jTextField1);
        add(jTextField6);
        jTextField6.setBounds(170, 50, 200, 30);

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 0, 51));
        jLabel26.setText("*");
        add(jLabel26);
        jLabel26.setBounds(30, 140, 20, 10);

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 51));
        jLabel27.setText("*");
        add(jLabel27);
        jLabel27.setBounds(30, 180, 20, 10);

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 0, 51));
        jLabel28.setText("*");
        add(jLabel28);
        jLabel28.setBounds(380, 220, 20, 10);

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 0, 51));
        jLabel29.setText("*");
        add(jLabel29);
        jLabel29.setBounds(380, 140, 20, 10);

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
        jComboBox4.setBounds(170, 210, 200, 30);

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("per");
        add(jLabel10);
        jLabel10.setBounds(600, 210, 40, 30);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("Unit of Measurement");
        add(jLabel12);
        jLabel12.setBounds(30, 210, 120, 30);
        add(jLabel13);
        jLabel13.setBounds(340, 210, 70, 30);
        add(jLabel14);
        jLabel14.setBounds(640, 210, 70, 30);

        jButton6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton6.setText("Delete");
        jButton6.setOpaque(false);
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        add(jButton6);
        jButton6.setBounds(930, 300, 140, 30);

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 0, 51));
        jLabel30.setText("*");
        add(jLabel30);
        jLabel30.setBounds(30, 100, 20, 10);

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 0, 51));
        jLabel31.setText("*");
        add(jLabel31);
        jLabel31.setBounds(30, 60, 20, 10);

        jTextField7.setNextFocusableComponent(jTextField1);
        add(jTextField7);
        jTextField7.setBounds(170, 170, 200, 30);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("Length");
        add(jLabel9);
        jLabel9.setBounds(390, 10, 130, 30);

        jLabel16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel16.setText("Width");
        add(jLabel16);
        jLabel16.setBounds(390, 50, 130, 30);

        jLabel17.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel17.setText("Thickness");
        add(jLabel17);
        jLabel17.setBounds(390, 90, 130, 30);

        jLabel18.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel18.setText("Density");
        add(jLabel18);
        jLabel18.setBounds(390, 130, 130, 30);

        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });
        add(jTextField9);
        jTextField9.setBounds(490, 10, 200, 30);
        add(jTextField10);
        jTextField10.setBounds(490, 50, 200, 30);
        add(jTextField11);
        jTextField11.setBounds(490, 90, 200, 30);
        add(jTextField12);
        jTextField12.setBounds(490, 130, 200, 30);

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 0, 51));
        jLabel32.setText("*");
        add(jLabel32);
        jLabel32.setBounds(380, 170, 20, 20);

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 0, 51));
        jLabel33.setText("*");
        add(jLabel33);
        jLabel33.setBounds(380, 20, 20, 10);

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 0, 51));
        jLabel34.setText("*");
        add(jLabel34);
        jLabel34.setBounds(380, 60, 20, 10);

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 0, 51));
        jLabel35.setText("*");
        add(jLabel35);
        jLabel35.setBounds(380, 100, 20, 10);

        jLabel15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel15.setText("RM Name :");
        add(jLabel15);
        jLabel15.setBounds(40, 170, 80, 30);

        jLabel38.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 0, 51));
        jLabel38.setText("*");
        add(jLabel38);
        jLabel38.setBounds(30, 180, 20, 10);

        jLabel21.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel21.setText("RM Code:");
        add(jLabel21);
        jLabel21.setBounds(40, 50, 130, 30);

        jList1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        add(jScrollPane1);
        jScrollPane1.setBounds(170, 250, 200, 80);

        jLabel19.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel19.setText("<html>Reference<br>Documents</html>");
        add(jLabel19);
        jLabel19.setBounds(30, 250, 120, 32);

        jLabel20.setText("Param-name");
        add(jLabel20);
        jLabel20.setBounds(700, 0, 80, 0);

        jLabel22.setText("Bench-mark");
        add(jLabel22);
        jLabel22.setBounds(780, 0, 70, 0);

        jLabel23.setText("UOM");
        add(jLabel23);
        jLabel23.setBounds(860, 0, 30, 0);

        jLabel24.setText("USL");
        add(jLabel24);
        jLabel24.setBounds(960, 0, 30, 0);

        jLabel25.setText("LSL");
        add(jLabel25);
        jLabel25.setBounds(920, 0, 30, 0);

        jCheckBox1.setOpaque(false);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        add(jCheckBox1);
        jCheckBox1.setBounds(170, 10, 200, 30);

        jLabel36.setText("<html>Is a Consumable ?");
        add(jLabel36);
        jLabel36.setBounds(30, 10, 140, 30);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String RM_TYPE, RM_NAME, RM_CTG, RM_RATE, RM_CRIT, RM_EC_NO, RM_CAS_NO, GST_NO, RM_CLASS, AUTH_SUPP, REORDER_LEVEL, CREATED_ON, EDITED_ON, EDITED_BY, RM_CODE;
        
        String length = "0.0", width= "0.0", thickness= "0.0", density= "0.0" ;
                Boolean boughtout;


        String UOM_ID;
        
        

        RM_TYPE = jComboBox1.getSelectedItem ().toString ();
        RM_CTG = jComboBox2.getSelectedItem ().toString ();
        //RM_CLASS = jComboBox3.getSelectedItem ().toString ();
        RM_NAME = jTextField7.getText ();
        //RM_AUTH_SUPP = jComboBox4.getSelectedItem().toString();
        RM_RATE = jTextField1.getText ();
 //       RM_CRIT = jTextArea1.getText ();
//        RM_EC_NO = jTextField2.getText ();
//        RM_CAS_NO = jTextField3.getText ();
//        GST_NO = jTextField4.getText ();
        RM_CRIT = "";
        RM_EC_NO = "";
        RM_CAS_NO = "";
        GST_NO = "";
        RM_CODE = jTextField6.getText();
        REORDER_LEVEL = jTextField5.getText ();

        length = jTextField9.getText ();
        width = jTextField10.getText ();
        thickness = jTextField11.getText ();
        density = jTextField12.getText ();
        UOM_ID = UOM.get( jComboBox4.getSelectedIndex () )[0];

        ArrayList<Main_typeModel_quality> listLllUll =  maintain.getUllLLL();
        
        boughtout = jCheckBox1.isSelected();  // Added by harshali  on 28-Jun-2019
        
        
        CREATED_ON = DateTimeFormatter.sdf2.format ( Calendar.getInstance ().getTime () ) + " " + DateTimeFormatter.sdf1.format ( Calendar.getInstance ().getTime () );
        EDITED_ON = Proman_User.getEID () + "";
        EDITED_BY = DateTimeFormatter.sdf2.format ( Calendar.getInstance ().getTime () ) + " " + DateTimeFormatter.sdf1.format ( Calendar.getInstance ().getTime () );

        if (  ! RM_TYPE.equals ( "" ) &&  ! RM_NAME.equals ( "" ) &&  ! RM_RATE.equals ( "" ) &&  ! REORDER_LEVEL.equals ( "" )   &&  ! length .equals (  "") &&   ! width.equals (  "") && ! thickness.equals (  "") && ! density.equals (  "")) {
            String result = DB_Operations.insertIntoRawMaterialMaster ( RM_TYPE , RM_NAME , RM_CTG , RM_RATE , RM_CRIT , RM_EC_NO , RM_CAS_NO , GST_NO , RM_CODE , REORDER_LEVEL , CREATED_ON , EDITED_ON , EDITED_BY , UOM_ID );

            try {
                String addEmpAPICall  ;
//                String addEmpAPICall = "rawmaterialadd?RM_TYPE=" + URLEncoder.encode ( RM_TYPE , "UTF-8" ) + "&RM_NAME=" + URLEncoder.encode ( RM_NAME , "UTF-8" ) + "&RM_CTG=" + URLEncoder.encode ( RM_CTG , "UTF-8" ) + "&RM_AUTH_SUPP=" + URLEncoder.encode ( "" , "UTF-8" ) + "&RM_RATE=" + URLEncoder.encode ( RM_RATE , "UTF-8" ) + "&RM_CRIT=" + URLEncoder.encode ( RM_CRIT , "UTF-8" ) 
//                        + "&RM_EC_NO=" + URLEncoder.encode ( RM_EC_NO , "UTF-8" ) + "&RM_CAS_NO=" + URLEncoder.encode ( RM_CAS_NO , "UTF-8" ) + "&GST_NO=" + URLEncoder.encode ( GST_NO , "UTF-8" ) + "&RM_CLASS=" + URLEncoder.encode ( RM_CODE , "UTF-8" ) + "&RM_CODE=" + URLEncoder.encode ( RM_CODE , "UTF-8" ) 
//                        + "&REORDER_LEVEL=" + URLEncoder.encode ( REORDER_LEVEL , "UTF-8" ) + "&CREATED_ON=" + URLEncoder.encode ( CREATED_ON , "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( EDITED_ON , "UTF-8" ) + "&EDITED_BY=" + URLEncoder.encode ( EDITED_BY , "UTF-8" ) + "&RMM_UOM_ID=" + URLEncoder.encode ( UOM_ID , "UTF-8" )
//                        + "&length="+length +"&width="+ width+ "&thickness="+ thickness+ "&density="+density;
//                     String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

                if(boughtout)
                {
                addEmpAPICall = "rawmaterialadd?RM_TYPE=" + URLEncoder.encode ( RM_TYPE , "UTF-8" ) + "&RM_NAME=" + URLEncoder.encode ( RM_NAME , "UTF-8" ) + "&RM_CTG=" + URLEncoder.encode ( RM_CTG , "UTF-8" ) + "&RM_AUTH_SUPP=" + URLEncoder.encode ( "" , "UTF-8" ) + "&RM_RATE=" + URLEncoder.encode ( RM_RATE , "UTF-8" )+ "&bought_out=" + URLEncoder.encode ( 1+"" , "UTF-8" ) + "&RM_CRIT=" + URLEncoder.encode ( RM_CRIT , "UTF-8" ) 
                        + "&RM_EC_NO=" + URLEncoder.encode ( RM_EC_NO , "UTF-8" ) + "&RM_CAS_NO=" + URLEncoder.encode ( RM_CAS_NO , "UTF-8" ) + "&GST_NO=" + URLEncoder.encode ( GST_NO , "UTF-8" ) + "&RM_CLASS=" + URLEncoder.encode ( RM_CODE , "UTF-8" ) + "&RM_CODE=" + URLEncoder.encode ( RM_CODE , "UTF-8" ) 
                        + "&REORDER_LEVEL=" + URLEncoder.encode ( REORDER_LEVEL , "UTF-8" ) + "&CREATED_ON=" + URLEncoder.encode ( CREATED_ON , "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( EDITED_ON , "UTF-8" ) + "&EDITED_BY=" + URLEncoder.encode ( EDITED_BY , "UTF-8" ) + "&RMM_UOM_ID=" + URLEncoder.encode ( UOM_ID , "UTF-8" )
                        + "&length="+length +"&width="+ width+ "&thickness="+ thickness+ "&density="+density;
                }
                else
                {
                    addEmpAPICall = "rawmaterialadd?RM_TYPE=" + URLEncoder.encode ( RM_TYPE , "UTF-8" ) + "&RM_NAME=" + URLEncoder.encode ( RM_NAME , "UTF-8" ) + "&RM_CTG=" + URLEncoder.encode ( RM_CTG , "UTF-8" ) + "&RM_AUTH_SUPP=" + URLEncoder.encode ( "" , "UTF-8" ) + "&RM_RATE=" + URLEncoder.encode ( RM_RATE , "UTF-8" ) + "&bought_out=" + URLEncoder.encode ( 0+"" , "UTF-8" ) + "&RM_CRIT=" + URLEncoder.encode ( RM_CRIT , "UTF-8" ) 
                        + "&RM_EC_NO=" + URLEncoder.encode ( RM_EC_NO , "UTF-8" ) + "&RM_CAS_NO=" + URLEncoder.encode ( RM_CAS_NO , "UTF-8" ) + "&GST_NO=" + URLEncoder.encode ( GST_NO , "UTF-8" ) + "&RM_CLASS=" + URLEncoder.encode ( RM_CODE , "UTF-8" ) + "&RM_CODE=" + URLEncoder.encode ( RM_CODE , "UTF-8" ) 
                        + "&REORDER_LEVEL=" + URLEncoder.encode ( REORDER_LEVEL , "UTF-8" ) + "&CREATED_ON=" + URLEncoder.encode ( CREATED_ON , "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( EDITED_ON , "UTF-8" ) + "&EDITED_BY=" + URLEncoder.encode ( EDITED_BY , "UTF-8" ) + "&RMM_UOM_ID=" + URLEncoder.encode ( UOM_ID , "UTF-8" )
                        + "&length="+length +"&width="+ width+ "&thickness="+ thickness+ "&density="+density;
                }
           
                String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );                     
                     
       //               JOptionPane.showMessageDialog ( null , result2 );
                      //****************created by Harshali 
                      //***************RM Quality Master Entry  Hidden
           /*         if(! result2.equals("null"))
                    {
                       
                      
                       JSONObject jb=new JSONObject(result2);
                       String status=jb.getString("status");
                       JSONObject jsb=new JSONObject(result2);
                       JSONObject js=jsb.getJSONObject("data");
                       int s=js.getInt("insert_id");
                       if(status.contains("success"))
                       {
                           if(listLllUll.size()!=0)
                           {
                                for(  int j=0 ;  j<listLllUll.size(); j++ )
                                {
                                    if((listLllUll.get(j).getLll())!=0 && (listLllUll.get(j).getUll())!=0 )
                                  {
                                      int id=listLllUll.get(j).getId();
                                      Float std_mark=listLllUll.get(j).getStd_mark();
                                      float Ll=listLllUll.get(j).getLll();
                                      float ul=listLllUll.get(j).getUll();
                                      String name=listLllUll.get(j).getName();
                                      int uom_id=listLllUll.get(j).getUom();
                                  String EmpAPICall=null;
                                  try{ 
                                  EmpAPICall = "rmqty_master_add?ref_rm_id="+URLEncoder.encode(""+s, "UTF-8")+"&rm_param_name="+URLEncoder.encode(id+"", "UTF-8")+"&rm_p_mandatory="+URLEncoder.encode(""+1, "UTF-8")+"&standard_value="+URLEncoder.encode(std_mark+"", "UTF-8")+"&uom_id="+URLEncoder.encode(uom_id+"", "UTF-8")+"&LLL="+URLEncoder.encode( Ll+"", "UTF-8")+"&ULL="+URLEncoder.encode( ul+"", "UTF-8");
                                 String result1 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
              
                                   JOptionPane.showMessageDialog ( null , result1 );
                                  }catch(Exception e)
                                  {
                                      e.getMessage();
                                  }
                                  }
                         
                                }
                           }
                           
                           
                       }
                    }*/
                      //************
                 

            } catch ( UnsupportedEncodingException e ) {

            }

            try {
                if ( Integer.parseInt ( result ) > 0 ) 
                {
                    JOptionPane.showMessageDialog ( null , "Raw Material Added Successfuly" );
                }
            } catch ( Exception e ) 
            {
                JOptionPane.showMessageDialog ( null , "" + result );
                StaticValues.writer.writeExcel ( Raw_Material.class.getSimpleName () , Raw_Material.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

        } else {
            JOptionPane.showMessageDialog ( null , "Please fill all fields marked with * " );
        }

        loadContent ();
        maintain.reset();
        resetFields ();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
//        // TODO add your handling code here:

         showDialog1();
//        Workbook wb = new HSSFWorkbook ();
//
//        ArrayList<String> column_names = new ArrayList<String> ();
//        ArrayList<String[]> values = new ArrayList<String[]> ();
//        int columnCount = 0;
//        File file = null;
//        try {
//            ResultSet result = DB_Operations.getMasters ( "raw_material" );
//            if ( result != null ) {
//                ResultSetMetaData metaData = result.getMetaData ();
//                // names of columns
//                columnCount = metaData.getColumnCount ();
//                for ( int column = 2 ; column <= columnCount - 3 ; column ++ ) {
//                    column_names.add ( metaData.getColumnName ( column ) );
//                }
//            }
//            // data of the table
//            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
//            while ( result.next () ) {
//                String[] val = new String[ columnCount ];
//                for ( int columnIndex = 2 ; columnIndex <= columnCount ; columnIndex ++ ) {
//                    if ( columnIndex < columnCount - 2 ) {
//                        val[ columnIndex - 2 ] = String.valueOf ( result.getObject ( columnIndex ) );
//                    }
//                }
//                values.add ( val );
//            }
//        } catch ( SQLException e ) {
//            StaticValues.writer.writeExcel ( Raw_Material.class.getSimpleName () , Raw_Material.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
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
//        sheet1 = wb.createSheet ( "Raw Material Master" );
//        //sheet1.setColumnWidth(0, (15 * 500));
//
//        Cell c = null;
//        Cell c3 = null;
//
//        Row row = sheet1.createRow ( 1 );
//        //  c = row.createCell(0);
//        // c.setCellValue("");
//        // c.setCellStyle(cs);
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
//            file = new File ( "Raw Material Master File " + strDate2 + ".xls" );
//
//            FileOutputStream os = null;
//
//            try {
//                os = new FileOutputStream ( file );
//                wb.write ( os );
//                wb.close ();
//                os.close ();
//                //    JOptionPane.showMessageDialog(null, "New File generated at " + file.getAbsolutePath());
//            } catch ( IOException e ) {
//                JOptionPane.showMessageDialog ( null , "Error " + e.getMessage () );
//                StaticValues.writer.writeExcel ( Raw_Material.class.getSimpleName () , Raw_Material.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//            } finally {
//
//            }
//
//        }
//        JOptionPane.showMessageDialog ( null , "New File generated at " + file.getAbsolutePath () );
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        HomeScreen3.home.removeForms ();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        resetFields ();
        if(maintain != null)
        {
            maintain.removeAll();
            remove(maintain);
        }
        if(maintain1 != null)
        {
            maintain1.removeAll();
            remove(maintain1);
        }
        repaint();
        revalidate();
        
        //maintain = new RMQualityParamList ();
        //maintain.setBounds (700 , 20 , 300 , 200 );
        //add( maintain );
        
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

        jTextField7.setText ( model.getValueAt ( selectedRowIndex , 2 ).toString () );
        jTextField1.setText ( model.getValueAt ( selectedRowIndex , 3 ).toString () );
        jTextField5.setText ( model.getValueAt ( selectedRowIndex , 4 ).toString () );
        jTextField6.setText ( model.getValueAt ( selectedRowIndex , 6 ).toString () );
        
        jTextField9.setText ( model.getValueAt ( selectedRowIndex , 7 ).toString () );
        jTextField10.setText ( model.getValueAt ( selectedRowIndex , 8 ).toString () );
        jTextField11.setText ( model.getValueAt ( selectedRowIndex , 9 ).toString () );
        jTextField12.setText ( model.getValueAt ( selectedRowIndex , 10 ).toString () );
        
        jTextField2.setText ( model.getValueAt ( selectedRowIndex , 11 ).toString () );
        jTextField3.setText ( model.getValueAt ( selectedRowIndex , 12 ).toString () );
        jTextField4.setText ( model.getValueAt ( selectedRowIndex , 13 ).toString () );
        //jTextArea1.setText ( model.getValueAt ( selectedRowIndex , 14 ).toString () );
        
        int bought_out = Integer.parseInt(model.getValueAt( selectedRowIndex , 15).toString());
        if(bought_out == 1)
        {
             jCheckBox1.setSelected(true);
        }else
        {
             jCheckBox1.setSelected(false);
        }
        
        loadDocumentsForRM(  selectedEmpRecordId ) ;
        
       /*  String addEmpAPICall = "rmqty_master?rm_id="+selectedEmpRecordId;
        String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
        if(!result2.contains("not found" ))
        {
           
          this.maintain.removeAll();// (maintain);
          this.remove(maintain);
          if(maintain1!=null)
          {
              this.remove(maintain1);
             // maintain1.maintainTypeList1.clear();
          }
          revalidate();
          repaint();
         
           maintain1 = new RMQualityParamList (selectedEmpRecordId);
           maintain1.setBounds ( 700 , 20 , 300 , 200 );
           add( maintain1 );
            revalidate();
            repaint();
          
       /* }
        else
        {
            //System.err.println("CHEACK MAINTAIN1");
            this.remove(maintain);
            if(maintain1 != null)
            {       
                maintain1.removeAll();
            
            }
             repaint();
             revalidate();
        }*/
        
        
         jButton5.setEnabled ( true );
    }
    
    
    public void loadDocumentsForRM( int _selectedEmpRecordId)
    {
        
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
                                docsList.addElement( emp.getString ("RD_NAME"));
                            }
                        }
                    }
                }
            }
        }
        try{
            jList1.setModel(  docsList  ) ;
            if(docsList.size ()>0 ){
                jScrollPane1.setVisible ( true);
                jLabel19.setVisible( true );
            }else{
                jScrollPane1.setVisible ( false);
                jLabel19.setVisible(false );
            }
        }catch(Exception ex)
        {
            System.err.println("TEST  "+ex);
        }finally{
            hideDialog ();
        }
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
       // RM_CRIT = jTextArea1.getText ();
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
        
        String unit = UOM.get ( jComboBox4.getSelectedIndex () )[0];

        
        boolean out = jCheckBox1.isSelected(); // added by harshali
        
        EDITED_ON = "EDITED_BY";
        EDITED_BY = "EDITED_ON";

        int ID = selectedEmpRecordId;
     
       // String result = DB_Operations.updateRawMaterialMaster ( RM_TYPE , RM_NAME , RM_CTG , RM_RATE , RM_CRIT , RM_EC_NO , RM_CAS_NO , GST_NO , RM_CLASS , REORDER_LEVEL , EDITED_ON , EDITED_BY , unit , ID );

        if (  ! RM_TYPE.equals ( "" ) &&  ! RM_NAME.equals ( "" ) &&  ! RM_RATE.equals ( "" ) &&  ! REORDER_LEVEL.equals ( "" )   &&  ! length .equals (  "") &&   ! width.equals (  "") && ! thickness.equals (  "") && ! density.equals (  "")) {
            try {

//                String addEmpAPICall = "rawmaterialsupdate?RM_ID="+selectedEmpRecordId+"&RM_TYPE=" + URLEncoder.encode ( RM_TYPE , "UTF-8" ) + "&RM_NAME=" 
//                        + URLEncoder.encode ( RM_NAME , "UTF-8" ) + "&RM_CTG=" + URLEncoder.encode ( RM_CTG , "UTF-8" ) + "&RM_AUTH_SUPP=" + URLEncoder.encode ( "" , "UTF-8" ) 
//                        + "&RM_RATE=" + URLEncoder.encode ( RM_RATE , "UTF-8" ) + "&RM_CRIT=" + URLEncoder.encode ( "" , "UTF-8" ) + "&RM_EC_NO=" 
//                        + URLEncoder.encode ( RM_EC_NO , "UTF-8" ) + "&RM_CAS_NO=" + URLEncoder.encode ( RM_CAS_NO , "UTF-8" ) + "&GST_NO=" 
//                        + URLEncoder.encode ( GST_NO , "UTF-8" ) + "&RM_CLASS=" + URLEncoder.encode ( RM_CLASS , "UTF-8" ) 
//                        + "&REORDER_LEVEL=" + URLEncoder.encode ( REORDER_LEVEL , "UTF-8" ) + "&CREATED_ON=&EDITED_ON=&EDITED_BY=&RMM_UOM_ID=" + URLEncoder.encode ( unit , "UTF-8" )
//                        + "&length="+length +"&width="+ width+ "&thickness="+ thickness+ "&density="+density;

                String addEmpAPICall ;
                          
                if(out)
                {
                addEmpAPICall = "rawmaterialsupdate?RM_ID="+selectedEmpRecordId+"&RM_TYPE=" + URLEncoder.encode ( RM_TYPE , "UTF-8" ) + "&RM_NAME=" 
                        + URLEncoder.encode ( RM_NAME , "UTF-8" ) + "&RM_CTG=" + URLEncoder.encode ( RM_CTG , "UTF-8" ) + "&RM_AUTH_SUPP=" + URLEncoder.encode ( "" , "UTF-8" ) 
                        + "&RM_RATE=" + URLEncoder.encode ( RM_RATE , "UTF-8" ) + "&bought_out=" + URLEncoder.encode ( ""+1 , "UTF-8" ) + "&RM_CRIT=" + URLEncoder.encode ( "" , "UTF-8" ) + "&RM_EC_NO=" 
                        + URLEncoder.encode ( RM_EC_NO , "UTF-8" ) + "&RM_CAS_NO=" + URLEncoder.encode ( RM_CAS_NO , "UTF-8" ) + "&GST_NO=" 
                        + URLEncoder.encode ( GST_NO , "UTF-8" ) + "&RM_CLASS=" + URLEncoder.encode ( RM_CLASS , "UTF-8" ) 
                        + "&REORDER_LEVEL=" + URLEncoder.encode ( REORDER_LEVEL , "UTF-8" ) + "&CREATED_ON=&EDITED_ON=&EDITED_BY=&RMM_UOM_ID=" + URLEncoder.encode ( unit , "UTF-8" )
                        + "&length="+length +"&width="+ width+ "&thickness="+ thickness+ "&density="+density;
                }else
                {
                      addEmpAPICall = "rawmaterialsupdate?RM_ID="+selectedEmpRecordId+"&RM_TYPE=" + URLEncoder.encode ( RM_TYPE , "UTF-8" ) + "&RM_NAME=" 
                        + URLEncoder.encode ( RM_NAME , "UTF-8" ) + "&RM_CTG=" + URLEncoder.encode ( RM_CTG , "UTF-8" ) + "&RM_AUTH_SUPP=" + URLEncoder.encode ( "" , "UTF-8" ) 
                        + "&RM_RATE=" + URLEncoder.encode ( RM_RATE , "UTF-8" ) + "&bought_out=" + URLEncoder.encode ( ""+0 , "UTF-8" )+ "&RM_CRIT=" + URLEncoder.encode ( "" , "UTF-8" ) + "&RM_EC_NO=" 
                        + URLEncoder.encode ( RM_EC_NO , "UTF-8" ) + "&RM_CAS_NO=" + URLEncoder.encode ( RM_CAS_NO , "UTF-8" ) + "&GST_NO=" 
                        + URLEncoder.encode ( GST_NO , "UTF-8" ) + "&RM_CLASS=" + URLEncoder.encode ( RM_CLASS , "UTF-8" ) 
                        + "&REORDER_LEVEL=" + URLEncoder.encode ( REORDER_LEVEL , "UTF-8" ) + "&CREATED_ON=&EDITED_ON=&EDITED_BY=&RMM_UOM_ID=" + URLEncoder.encode ( unit , "UTF-8" )
                        + "&length="+length +"&width="+ width+ "&thickness="+ thickness+ "&density="+density;
                }


                String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                JOptionPane.showMessageDialog ( null , result2 );
                System.out.println(result2);
                //****************Created By HArshali
        /*        if(result2.contains("Raw Material updated successfully"))
                {
                    ArrayList<Main_typeModel_quality> listLllUll =  maintain1.getUllLLL_update();
                    
                    if(!listLllUll.isEmpty())
                           {
                                for(  int j=0 ;  j<listLllUll.size(); j++ )
                                {
                                    if((listLllUll.get(j).getLll())!=0 && (listLllUll.get(j).getUll())!=0 )
                                  {
                                      int record_id=listLllUll.get(j).getRecord_id();
                                      int id=listLllUll.get(j).getId();
                                      Float std_mark=listLllUll.get(j).getStd_mark();
                                      float Ll=listLllUll.get(j).getLll();
                                      float ul=listLllUll.get(j).getUll();
                                      String name=listLllUll.get(j).getName();
                                      int uom_id=listLllUll.get(j).getUom();
                                        String EmpAPICall=null;
                                       if(record_id != 0)
                                       {   
                                        try{ 
                                        EmpAPICall = "rmqty_master_update?rm_param_id="+record_id+"rm_id="+URLEncoder.encode(""+selectedEmpRecordId, "UTF-8")+"&rm_param_name="+URLEncoder.encode(id+"", "UTF-8")+"&rm_p_mandatory="+URLEncoder.encode(""+1, "UTF-8")+"&standard_value="+URLEncoder.encode(std_mark+"", "UTF-8")+"&uom_id="+URLEncoder.encode(uom_id+"", "UTF-8")+"&LLL="+URLEncoder.encode( Ll+"", "UTF-8")+"&ULL="+URLEncoder.encode( ul+"", "UTF-8");
                                        String result1 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );

                                         JOptionPane.showMessageDialog ( null , result1 );
                                        }catch(Exception e)
                                  {
                                      e.getMessage();
                                  }
                                   }
                                       else{
                                           try{ 
                                  EmpAPICall = "rmqty_master_add?ref_rm_id="+URLEncoder.encode(""+selectedEmpRecordId, "UTF-8")+"&rm_param_name="+URLEncoder.encode(id+"", "UTF-8")+"&rm_p_mandatory="+URLEncoder.encode(""+1, "UTF-8")+"&standard_value="+URLEncoder.encode(std_mark+"", "UTF-8")+"&uom_id="+URLEncoder.encode(uom_id+"", "UTF-8")+"&LLL="+URLEncoder.encode( Ll+"", "UTF-8")+"&ULL="+URLEncoder.encode( ul+"", "UTF-8");
                                 String result1 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
              
                                   JOptionPane.showMessageDialog ( null , result1 );
                                  }catch(Exception e)
                                  {
                                      e.getMessage();
                                  }
                                       }
                                  }
                         
                                }
                           }
                           
                
                }*/
                //*****************
            
            } catch ( UnsupportedEncodingException e ) 
            {

            }
        } else {
            JOptionPane.showMessageDialog ( null , "Please fill all fields marked with * " );
        }
        
        
        

        resetFields ();
        maintain1.reset1();
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
            jLabel14.setText ( jComboBox4.getSelectedItem ().toString () );
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
            deleteMaster(  "rawmaterialdelete?RM_ID="+selectedEmpRecordId    ) ;
            
            String addEmpAPICall = "rawmaterials";
            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

            if(   !  result2.contains ( "not found")  ){

                if ( result2 != null ) {
                    jTable1.setModel ( buildTableModelfromJSON ( result2 ) );
                    jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                    jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                    
                    jTable1.getColumnModel ().getColumn ( 15 ).setMinWidth ( 0 );
                    jTable1.getColumnModel ().getColumn ( 15 ).setMaxWidth ( 0 );
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

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

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
            jLabel14.setText ( jComboBox4.getSelectedItem ().toString () );
        }
    };

    public void resetFields () {

        jTextField1.setText ( "" );
        //jTextArea1.setText ( "" );
        jTextField2.setText ( "" );
        jTextField3.setText ( "" );
        jTextField4.setText ( "" );
        jTextField5.setText ( "" );
        jTextField9.setText ( "" );
        jTextField10.setText ( "" );
        jTextField11.setText ( "" );
        jTextField12.setText ( "" );

        jTextField6.setText("");
        jTextField7.setText("");
        jButton2.setEnabled ( true );
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
        progressExcelData.updateTitle ( "All Machine data is fetching please wait...");
        progressExcelData.updateMessage ("Getting all Machines data");
        //String MCH_ID = "",MACHINE_NO="", MAKE = "", BED_SIZE = "", HEATER_QTY = "", YR_OF_COMM = "", CR_MAINT_RTG = "", CR_MCH_OWN = "", MCH_BRKD_RCD = "", PM_ELE = "", PM_MEC = "",PM_HYD="",PM_PNM="",PM_CLIT="",PRSS_GAUGE="",TIMER="",TEMP_CTRL="",OP_RATE_HR="",AVL_HRS="",DAVLHRS="";
        String RM_TYPE="", RM_NAME="", RM_CTG="", RM_RATE="", RM_CRIT="", RM_EC_NO="", RM_CAS_NO="", GST_NO="", RM_AUTH_SUPP="", REORDER_LEVEL="", RM_CODE="",length="",width="",thickness="",density="",RMM_UOM_ID="",UOM_NAME="";
        
        
        int l = 2;

        Map<String, Object[]> data11;
        data11 = new TreeMap<String, Object[]>();

        String[] columns ={"Raw Material Code", "Name", "Type", "Categary", "Unit of Measurement", "Length", "Width", "Thickness", "Desity", "Reorder Level", "Rate/Unit", "EC No", "CAS No"};
        data11.put("1", new Object[]{"Raw Material Code", "Name", "Type", "Categary", "Unit of Measurement", "Length", "Width", "Thickness", "Desity", "Reorder Level", "Rate/Unit", "EC No", "CAS No"});
        l+=1;
        
        String result2, addEmpAPICall;

        addEmpAPICall = "rawmaterials";
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
                

                RM_TYPE =emp.get("RM_TYPE").toString();
                RM_NAME =emp.get("RM_NAME").toString();
                RM_CTG =emp.get("RM_CTG").toString();
                RM_RATE =emp.get("RM_RATE").toString();
                RM_CRIT =emp.get("RM_CRIT").toString();
                RM_EC_NO =emp.get("RM_EC_NO").toString();
                RM_CAS_NO =emp.get("RM_CAS_NO").toString(); 
                GST_NO =emp.get("GST_NO").toString(); 
                RM_AUTH_SUPP =emp.get("RM_AUTH_SUPP").toString();
                REORDER_LEVEL =emp.get("REORDER_LEVEL").toString();
                RM_CODE =emp.get("RM_CODE").toString();
                length =emp.get("length").toString();
                width =emp.get("width").toString();
                thickness =emp.get("thickness").toString();
                density =emp.get("density").toString();
                RMM_UOM_ID=emp.get("RMM_UOM_ID").toString();
                UOM_NAME=uomName(RMM_UOM_ID);
                
                
                data11.put("" + l, new Object[]{RM_CODE, RM_NAME, RM_TYPE, RM_CTG, UOM_NAME,length ,width , thickness, density, REORDER_LEVEL, RM_RATE, RM_EC_NO, RM_CAS_NO});
                l = l + 1;
            }
        }

        

        //Blank workbook
        Workbook workbook = new HSSFWorkbook();

        //Create a blank sheet
        HSSFSheet sheet = (HSSFSheet) workbook.createSheet("Raw Materials Master");

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
            file = new File(jfc.getSelectedFile() + "\\" + "Raw Material Master Data " + strDate2 + ".xls");
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
    
    public String uomName(String id)
    {
        //unitmeasedit?UOM_ID=5
        String uomName="";
         String EmpAPICall = "unitmeasedit?UOM_ID=" + id;
        String tab = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

        if (!tab.contains("not found")) {
           

            JSONObject jsobje = new JSONObject(tab);
            String page11 = jsobje.getJSONArray("data").toString();
            JSONArray machinejsarray = new JSONArray(page11);

            for (int j = 0; j < machinejsarray.length(); j++) {

                JSONObject jsonobject = machinejsarray.getJSONObject(j);

                uomName = jsonobject.optString("UOM");
            }
        }
        
        return uomName;
    }
    /****************************************************************************/
     
     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
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
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    int selectedEmpRecordId = 0;
    
    ///***********************Created by HArshali RM Quality Master
     ArrayList<Main_typeModel_quality> selectedparam = new ArrayList<Main_typeModel_quality> ();

    class RMQualityParamList extends javax.swing.JPanel {

        ResultSet result = null;
       
        public ArrayList<QualityPanel> paramList = null;
        private ArrayList<Main_typeModel_quality> paramDetailList = new ArrayList<Main_typeModel_quality> ();
    public ArrayList<QualityPanel> paramList1 = null;
        private ArrayList<Main_typeModel_quality> paramDetailList1 = new ArrayList<Main_typeModel_quality> ();

        /**
         * Creates new form ProdDataConformationScreen
         */
        public RMQualityParamList () {
            initComponents ();

            ParamList panel = new ParamList ();
            panel.setBounds ( 0 , 0 , 300 , 200 );
            panel.setBackground ( Color.white );
            add ( panel );

        }
        public RMQualityParamList (int id) {
            initComponents ();

            ParamList panel = new ParamList (id);
            panel.setBounds ( 0 , 0 , 300 , 200 );
            panel.setBackground ( Color.white );
            add ( panel );

        }

        @SuppressWarnings( "unchecked" )
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents () {

            jButton1 = new javax.swing.JButton ();
            jButton2 = new javax.swing.JButton ();
            jLabel1 = new javax.swing.JLabel ();

            setBackground ( new java.awt.Color ( 255 , 255 , 255 ) );
            setBorder ( javax.swing.BorderFactory.createTitledBorder ( "" ) );
            setPreferredSize ( new java.awt.Dimension ( 300 , 200 ) );
            setLayout ( null );

        }// </editor-fold>                        

        public ArrayList<Main_typeModel_quality> getUllLLL( ) 
          {
            // TODO add your handling code here:
            Main_typeModel_quality tlm = null;

            
            paramDetailList = new ArrayList<Main_typeModel_quality> () ;
            for ( int i = 0 ; i < paramList.size () ; i ++ ) 
            {

                tlm = new Main_typeModel_quality();
                if ( paramList.get ( i ).getUll() > 0.0 && paramList.get ( i ).getLll() >00) 
                {
                    tlm.setId(paramList.get ( i ).getId());
                    tlm.setName(paramList.get ( i ).getName());
                    tlm.setStd_mark(paramList.get(i).getStandard_mark());
                    tlm.setLll(paramList.get ( i ).getLll());
                    tlm.setUll(paramList.get ( i ).getUll());
                    tlm.setUom(paramList.get ( i ).getUom());
                    
                     paramDetailList.add ( tlm );
                }
            }
            
            
                Main_typeModel_quality tlm2 = null;
                StringBuilder sb = new StringBuilder ();
                for ( int i = 0 ; i < paramDetailList.size () ; i ++ ) 
                {
                    tlm2 = paramDetailList.get ( i );

                    sb.append ( tlm2.getLll());
                    sb.append ( tlm2.getUll());
                    sb.append ( tlm2.getStd_mark());
                    sb.append ( tlm2.getName());
                    sb.append ( tlm2.getId());
                    sb.append ( tlm2.getUom());
                    sb.append ( "\n" );
                }

                selectedparam= paramDetailList;
                
            
            return selectedparam ;
        }

        
         public ArrayList<Main_typeModel_quality> getUllLLL_update( ) 
          {
            // TODO add your handling code here:
            Main_typeModel_quality tlm = null;

            
            paramDetailList1 = new ArrayList<Main_typeModel_quality> () ;
             for ( int i = 0 ; i < paramList1.size () ; i ++ ) 
            {

                tlm = new Main_typeModel_quality();
                if ( paramList1.get ( i ).getUll() > 0.0 && paramList1.get ( i ).getLll() >00) 
                {
                    tlm.setRecord_id(paramList1.get ( i ).getRecord_id1());
                    tlm.setId(paramList1.get ( i ).getId());
                    tlm.setName(paramList1.get ( i ).getName());
                    tlm.setStd_mark(paramList1.get(i).getStandard_mark());
                    tlm.setLll(paramList1.get ( i ).getLll());
                    tlm.setUll(paramList1.get ( i ).getUll());
                    tlm.setUom(paramList1.get ( i ).getUom());
                    
                      paramDetailList1.add ( tlm );
                }
            }
            
            
                Main_typeModel_quality tlm2 = null;
                StringBuilder sb = new StringBuilder ();
                for ( int i = 0 ; i < paramDetailList1.size () ; i ++ ) 
                {
                    tlm2 = paramDetailList1.get ( i );

                    sb.append ( tlm2.getLll());
                    sb.append ( tlm2.getUll());
                    sb.append ( tlm2.getStd_mark());
                    sb.append ( tlm2.getName());
                    sb.append ( tlm2.getId());
                    sb.append ( tlm2.getUom());
                    sb.append ( "\n" );
                }

                selectedparam= paramDetailList1;
                
            
            return selectedparam ;
        }
            public void reset()
		{
            
             for ( int i = 0 ; i < paramList.size () ; i ++ ) 
             {

                if ( paramList.get ( i ).getUll()> 0.0 ) 
                {
                    paramList.get ( i ).setUll(Float.valueOf("0")) ;
                }
                if ( paramList.get ( i ).getLll()> 0.0 ) 
                {
                    paramList.get ( i ).setLll(Float.valueOf("0") ) ;
                }
                if ( paramList.get ( i ).getStandard_mark()> 0 ) 
                {
                    paramList.get ( i ).setStandard_mark(Float.valueOf(0) ) ;
                }
            }
           
            
        }
        public void reset1()
        {
             for(int j=0;j<paramList1.size() ;j++)
            {
                 if ( paramList1.get ( j ).getUll()> 0.0 ) 
                {
                    paramList1.get ( j ).setUll(Float.valueOf("0")) ;
                }
                if ( paramList1.get ( j ).getLll()> 0.0 ) 
                {
                    paramList1.get ( j ).setLll(Float.valueOf("0") ) ;
                }
                if ( paramList1.get ( j ).getStandard_mark()> 0 ) 
                {
                    paramList1.get ( j ).setStandard_mark(Float.valueOf(0) ) ;
                }
            }
        }

        // Variables declaration - do not modify                     
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        // End of variables declaration                   

        class ParamList extends JPanel 
        {

            private JPanel panel;

            private JScrollPane scroll;
            private JButton btnAddType;

//    public ArrayList<RMQtyPanel> rmBomList = null ;
            public ParamList () 
            {
                // getContentPane().setLayout(new BorderLayout());       

                setLayout ( new BorderLayout () );

                panel = new JPanel ();
                panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
                
                scroll = new JScrollPane ( panel ,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );

                add ( scroll , BorderLayout.CENTER );

                setVisible ( true );

                QualityPanel  pane = null;
            if( paramList == null  )
                {
                    
                    String    EmpAPICall = "unitmeas";
                  String  result2 =  WebAPITester.prepareWebCall ( EmpAPICall, StaticValues.getHeader ()   , "");
                    
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
                            //jComboBox1.addItem ( emp.get ( "UOM" ).toString () );
                            UOM.add( new String[]{ emp.get ( "UOM_ID" ).toString (), emp.get ( "UOM" ).toString () }   ); 
                        }
                    }   
                paramList = new ArrayList<QualityPanel> ();

               
                     EmpAPICall = "rmqty_u_master";
                         result2 =  WebAPITester.prepareWebCall ( EmpAPICall, StaticValues.getHeader ()   , "");

                        if( ! result2.contains( "not found" )   && result2.contains("success"))
                       {  
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
                    //list = new ArrayList<Rejection_Reasons> ();
                     panel.setBounds ( 0 , 0 , 300 , 40 * records.length () );
                     Id_Name_List =new ArrayList<QualityRMDR>();
                    
                     for ( int i = 0 ; i < records.length () ; i ++ ) 
                        {
                            emp = records.getJSONObject ( i);
                            QualityRMDR dr=new  QualityRMDR();
                            dr.setRMQP_ID(emp.getInt("param_id"));
                            dr.setRMQP_Name(emp.get("param_name").toString());
                            Id_Name_List.add(dr);
                            pane = new QualityPanel();
                            pane.setBounds(0,(i*40),300,40);
                            pane.setId(emp.getInt( "param_id" ));
                            pane.setParam_name(emp.get ( "param_name" ).toString () );
                            pane.setStandard_mark(Float.valueOf(0));
                            pane.setLll(Float.valueOf("0"));
                            pane.setUll(Float.valueOf("0"));
                            pane.setUom(UOM);
                            panel.add ( pane );
                            panel.revalidate ();
                            paramList.add ( pane );
                           
                        }
                    

                    } 
            }
            
               
            }
             public ParamList (int id) 
            {
                // getContentPane().setLayout(new BorderLayout());       

                setLayout ( new BorderLayout () );

                panel = new JPanel ();
                panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
                
                scroll = new JScrollPane ( panel ,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );

                add ( scroll , BorderLayout.CENTER );

                setVisible ( true );

                QualityPanel  pane = null;
            if( paramList1 == null  )
                {
                paramList1 = new ArrayList<QualityPanel> ();

               
                    String EmpAPICall = "rmqty_master?rm_id="+id;
                    String     result2 =  WebAPITester.prepareWebCall ( EmpAPICall, StaticValues.getHeader ()   , "");

                        if( ! result2.contains( "not found" )   && result2.contains("success"))
                       {  
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
                    //list = new ArrayList<Rejection_Reasons> ();
                      panel.setBounds ( 0 , 0 , 300 , 40 * records.length () );
                      ArrayList<Integer> id_=new ArrayList<>();
                        int h=records.length();
                        UOM_selected = new ArrayList<String[]>();
                     for ( int i = 0 ; i < records.length () ; i ++ ) 
                        {
                            emp = records.getJSONObject ( i);
                           
                            UOM_selected.add( new String[]{ emp.get ( "uom_id" ).toString (), emp.get ( "UOM" ).toString () }   ); 
                            id_.add(Integer.parseInt(emp.get("rm_param_name").toString()));
                            pane = new QualityPanel();
                            pane.setBounds(0,(i*40),300,40);
                            pane.setRecord_id1(emp.getInt("rm_param_id"));
                            pane.setId(Integer.parseInt(emp.get( "rm_param_name" ).toString()));
                            pane.setParam_name(emp.get ( "param_name" ).toString () );
                            pane.setStandard_mark(Float.parseFloat(emp.get("standard_value").toString()));
                            pane.setLll(Float.parseFloat(""+emp.getDouble("LLL")));
                            pane.setUll(Float.parseFloat(""+emp.getDouble("ULL")));
                            pane.setUom(UOM_selected);
                            panel.add ( pane );
                            paramList1.add ( pane );
                           
                        }
                      for(int j = 0; j < Id_Name_List.size();j++)
                        
                        {
                          String abc= "true";
                          for(int k=0 ; k< id_.size() ; k++)
                          {
                            int a  =Id_Name_List.get(j).RMQP_ID;
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
                            pane.setId(Id_Name_List.get(j).RMQP_ID);
                            pane.setParam_name(Id_Name_List.get(j).RMQP_Name);
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
                            pane.setId(Id_Name_List.get(j).RMQP_ID);
                            pane.setParam_name(Id_Name_List.get(j).RMQP_Name);
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
    
    
    
    //**********************

