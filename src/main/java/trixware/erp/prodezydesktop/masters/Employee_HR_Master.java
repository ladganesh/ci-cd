/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import trixware.erp.prodezydesktop.Model.GradeDR;
import trixware.erp.prodezydesktop.Model.NatureOfEmpDR;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.DepartmentMultiSelect;
import trixware.erp.prodezydesktop.Utilities.DeptSelectionPanel;
import trixware.erp.prodezydesktop.Utilities.MyCustomFilter;
import trixware.erp.prodezydesktop.Utilities.RoleSelectionPanel;
import trixware.erp.prodezydesktop.Utilities.RolesMultiSelect;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


import trixware.erp.prodezydesktop.examples.HomeScreen;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import trixware.erp.prodezydesktop.Model.DesignationDR;
import trixware.erp.prodezydesktop.Model.SalBandDR;
import trixware.erp.prodezydesktop.components.ProgressDialog;
import trixware.erp.prodezydesktop.components.dialogCreateUserPanel;
import trixware.erp.prodezydesktop.helpers.ZXingHelper;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class Employee_HR_Master extends javax.swing.JPanel {

    DepartmentMultiSelect  deptpanel = new DepartmentMultiSelect();
    RolesMultiSelect  rolepanel = new RolesMultiSelect() ;
    String resource = "" , resourceType = "" ;
   // private static final String QR_CODE_IMAGE_PATH;
    JScrollPane rolePanel = null , deptPanel = null ;
    
    DesignationDR designation = null ;
    GradeDR grade = null ;
    SalBandDR salband = null ;
    NatureOfEmpDR natOfEmp = null ;
    
      ArrayList<DesignationDR> designations = null ;
    ArrayList<GradeDR> grades = null ;
    ArrayList<SalBandDR> salbands = null ;
    ArrayList<NatureOfEmpDR> natOfEmps = null ;
    
    
    public void writeErrorLogs( String className, String filename, String exceptionName, String lineNo, String msg, String datetime ) {
        StaticValues.writer.writeExcel ( className , filename , exceptionName , lineNo , msg, datetime );
    }
    
    /**
     * Creates new form Employee_HR_Master
     */
    int selectedEmpRecordId = 0,Role;
    String selectedEmpRecordName,user_id,selectedEmpRecordNo,selectedEmpRecordJDate,selectedEmpRecordGender,Local_add,Perm_add,UserName;

    public Employee_HR_Master () 
    {
        initComponents ();
        revalidate();
        repaint();
        
        resourceType = "Masters" ;
        resource = "Employee_HR_Master" ;

//        try{
//                StaticValues.checkUserRolePermission(resourceType, resourceType);
//           }catch(Exception e){
//                StaticValues.writer.writeExcel (resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//           }
        
        
        jLabel32.setVisible ( false );
        jLabel33.setVisible ( false );
        jLabel30.setVisible ( false );
        
        jCheckBox3.setVisible ( false);
        jComboBox4.setVisible ( false );
        jCheckBox4.setVisible ( false );

        
        dateChooserCombo2.setFocusable ( true );
        
        
        jScrollPane4.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane4.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0,10));
        jScrollPane2.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
   //*******************Role wise priority set edited by mayur****************************************       
//        int role;
//        role=Integer.parseInt(Proman_User.getRole());
//        
//        switch (role) {
//            case 1: 
//                
                jButton4.setEnabled(true);
                jButton7.setEnabled(true);
                jButton5.setEnabled(false);
                jButton9.setEnabled(false);
                jButton8.setEnabled(false);
//                
//            break;
//
//            case 2:
//                jButton4.setEnabled(true);
//                jButton7.setEnabled(false);
//                jButton5.setEnabled(false);
//                jButton9.setEnabled(true);
//                jButton8.setEnabled(false);
//            break;
//
//            case 3:   
//                jButton4.setEnabled(false);
//                jButton7.setEnabled(false);
//                jButton5.setEnabled(false);
//                jButton9.setEnabled(false);
//                jButton8.setEnabled(false);
//            break;
//
//            default:
//        }
        
        //*******************end Role wise priority set edited by mayur****************************************
    }

    ArrayList<String> DepartmentList = new ArrayList<String>() ;
    
    String addEmpAPICall , result2 ;
    
    public void loadContent ()
    {
        ResultSet result = null; 
        try {
            
            
        
//                result = DB_Operations.getMasters ( "employee" );
//
//                if ( result != null ) {
//                    jTable1.setModel ( buildTableModel ( result ) );
//                    jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
//                    jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
//                }

                
                try {
                //result = DB_Operations.getMasters ( "departments" );
                //deptpanel = new DepartmenMultiSelect ( result );
                addEmpAPICall = "department";
                result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                if( !  result2.contains(  "not found"   )  ){
                        deptPanel = deptpanel.getDeptMultiSelectList ( result2 ) ;
                        deptPanel.setBounds ( 150 , 160 , 220 , 90 );
                        add ( deptPanel );
                }
   //             result.close ();
            } catch ( Exception e ) {
//                try {
//                    result.close ();
//                } catch ( SQLException ex ) {
//                }
            }


            try {
                //result = DB_Operations.getMasters ( "roles" );
                //rolepanel = new RolesMultiSelect ( result  );
                addEmpAPICall = "userroles";
                result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                
                if( !  result2.contains(  "not found"   )  ){
                    rolePanel = rolepanel.getRolesMultiSelectList ( result2 ) ;
                    rolePanel.setBounds ( 150 , 250 , 220 , 90 );
                    add ( rolePanel );
                }
                
                
                
            } catch ( Exception e ) {

            }
                
            
//                result = DB_Operations.getSingleMasters ( "employee" , Proman_User.getEID () );
//
//                if ( result != null ) {
//                    jTable1.setModel ( buildTableModel ( result ) );
//                    jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
//                    jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
//                }

                String addEmpAPICall = "employees";
                String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

                if( !  result2.contains(  "not found"   )  ){
                    if ( result2!= null ) 
                    {
                        jTable1.setModel ( buildTableModelfromJSON (result2 ) );
                        jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                        jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                        jTable1.getColumnModel ().getColumn ( 11 ).setMinWidth ( 0 );
                        jTable1.getColumnModel ().getColumn ( 11 ).setMaxWidth ( 0 );
                        jTable1.getColumnModel ().getColumn ( 12 ).setMinWidth ( 0 );
                        jTable1.getColumnModel ().getColumn ( 12 ).setMaxWidth ( 0 );
                    }
                }
                
                

            

//////////////////////////////////////////////////////////////////          GRADES MASTER COMBOBOX                  ///////////////////////////////////////////////////////////////////////////////////////            
            
//            if ( jComboBox1.getModel ().getSize () == 0 ) {
//                result = DB_Operations.getMasters ( "grade" );
//                if ( result != null ) {
//                    while ( result.next () ) {
//                        jComboBox1.addItem ( result.getString ( "GradeName" ) );
//                    }
//                } else {
//                    jComboBox1.addItem ( "Not Available" );
//                }
//            }
            

            if ( jComboBox1.getItemCount () == 0 ) {
                    //result = DB_Operations.getMasters ( "raw_material_type" );
                   
                    addEmpAPICall = "grade";
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
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
                    grades = new ArrayList<GradeDR> ();
                    for ( int i = 0 ; i < records.length () ; i ++ ) {
                    
                        emp = records.getJSONObject ( i);
                        jComboBox1.addItem ( emp.get ( "GradeName" ).toString () );
                        grades.add( new GradeDR ( Integer.parseInt(emp.get ( "GradeID" ).toString ()), emp.get ( "GradeName" ).toString ()   )); 

                    }
                }
            
            
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX    GRADES MASTER COMBOBOX END      XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

            
            
//////////////////////////////////////////////////////////////////          DESIGNATION MASTER COMBOBOX                  ///////////////////////////////////////////////////////////////////////////////////////
/*            if ( jComboBox2.getModel ().getSize () == 0 ) {
                result = DB_Operations.getMasters ( "designation" );
                if ( result != null ) {
                    while ( result.next () ) {
                        jComboBox2.addItem ( result.getString ( "DesgTitle" ) );
                    }
                } else {
                    jComboBox2.addItem ( "Not Available" );
                }
            }*/


        if ( jComboBox2.getItemCount () == 0 ) {
                    //result = DB_Operations.getMasters ( "raw_material_type" );
                   
                    addEmpAPICall = "designations";
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
                            designations = new ArrayList<DesignationDR> ();
                            for ( int i = 0 ; i < records.length () ; i ++ ) {

                                emp = records.getJSONObject ( i);
                                jComboBox2.addItem ( emp.get ( "DesgTitle" ).toString () );
                                designations.add( new DesignationDR ( Integer.parseInt(emp.get ( "DesgID" ).toString ()), emp.get ( "DesgTitle" ).toString ()   )); 

                            }
                    }
                }

//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX    DESIGNATION MASTER COMBOBOX END      XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX


////////////////////////////////////////////////////////////////////////////////////////      SALARY BAND MASTER COMBOBOX   ////////////////////////////////////////////////////////////////////////////////
//            if ( jComboBox9.getModel ().getSize () == 0 ) {
//                result = DB_Operations.getMasters ( "salaryband" );
//                if ( result != null ) {
//                    while ( result.next () ) {
//                        jComboBox9.addItem ( result.getString ( "SB_RANGE" ) );
//                    }
//                } else {
//                    jComboBox9.addItem ( "Not Available" );
//                }
//            }

                
        if ( jComboBox9.getItemCount () == 0 ) {
                    //result = DB_Operations.getMasters ( "raw_material_type" );
                   
                    addEmpAPICall = "salaryband";
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
                        salbands = new ArrayList<SalBandDR> ();
                        for ( int i = 0 ; i < records.length () ; i ++ ) {

                            emp = records.getJSONObject ( i);
                            jComboBox9.addItem ( emp.get ( "SB_RANGE" ).toString () );
                            salbands.add( new SalBandDR ( Integer.parseInt(emp.get ( "SB_ID" ).toString ()), emp.get ( "SB_RANGE" ).toString ()   )); 

                        }
                    }
                }


//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX   SALARY BAND MASTER COMBOBOX   END   XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX



            if ( jComboBox3.getModel ().getSize () == 0 ) {
                result = DB_Operations.getMasters ( "employeenature" );
                if ( result != null ) {
                    while ( result.next () ) {
                        jComboBox3.addItem ( result.getString ( "NATURE" ) );
                    }
                } else {
                    jComboBox3.addItem ( "Not Available" );
                }
            }

            
////////////////////////////////////////////////////////////////////////////////////////      DEPARTMENTS MASTER COMBOBOX   ////////////////////////////////////////////////////////////////////////////////
            
            
//            if ( jComboBox4.getModel ().getSize () == 0 ) {
//                result = DB_Operations.getMasters ( "departments" );
//                if ( result != null ) {
//                    while ( result.next () ) {
//                        jComboBox4.addItem ( result.getString ( "DNAME" ) );
//                        DepartmentList.add ( result.getString ( "DID" ) ) ;
//                    }
//                } else {
//                    jComboBox4.addItem ( "Not Available" );
//                }
//            }



//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX    DEPARTMENTS  MASTER COMBOBOX END      XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX


            
            
//            result.close() ;
            
              
            
            
            //  JTable table = new JTable(buildTableModel(result));
            // Closes the Connection
            // JOptionPane.showMessageDialog(null, new JScrollPane(table));
        } catch ( SQLException e ) {
            writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            try {
                result.close() ;
            } catch ( SQLException ex ) {
                System.out.println ( "" );
                writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            }
        }
    }

    
    public void loadContentFromAPI(){
        
        
        // call employee details
        
        // salary band
        
        // designations
        
        // departments 
        
        // roles
                
        // nature of employment
        
        // some other
        
        
    }
    public static void generate_qr(String image_name,String qrCodeData) 
     {
        try 
        {
          
             byte [] QRresult =ZXingHelper.getQRCodeImage(qrCodeData, 300, 350);
                      final ImageIcon icon = new ImageIcon(QRresult);
                     Image image2 = icon.getImage().getScaledInstance(400,450,0);
                     
          //JOptionPane.showMessageDialog(null,"QR Code Location : "+filename,"QR Code of "+image_name,JOptionPane.PLAIN_MESSAGE, new ImageIcon(image2));
            int selection= JOptionPane.showConfirmDialog(null, null,"Click Yes To Save on Desk", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, new ImageIcon(image2));
            if(selection== JOptionPane.YES_OPTION)
            {
            
               
               final JFileChooser chooser = new JFileChooser() {
              public void approveSelection() 
              {
                if (getSelectedFile().isFile()) 
                {
                    return;
                } else
                    super.approveSelection();
              }
             };

            //chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Select Folder");
            chooser.setApproveButtonText("Save");
            chooser.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.showOpenDialog(null);
            String filePath = ""+chooser.getSelectedFile().getAbsolutePath();
            System.out.println("getSelectedFile() : "  +  chooser.getSelectedFile().getAbsolutePath());
            String filename=filePath +"\\"+image_name+".jpg";
            File outputfile = new File(filename);
           
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 400, 400);
            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", outputfile);
         
            JOptionPane.showConfirmDialog(null, "QR Code Location : "+filename, "QR Code of "+image_name, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null);
    
            }
    
          
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public static DefaultTableModel buildTableModel ( ResultSet rs )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        int columnCount = metaData.getColumnCount ();
        for ( int column = 1 ; column <= 4 ; column ++ ) {
            columnNames.add ( metaData.getColumnName ( column ) );
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        while ( rs.next () ) {
            Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 1 ; columnIndex <= 4 ; columnIndex ++ ) {
                vector.add ( rs.getObject ( columnIndex ) );
            }
            data.add ( vector );
        }
        return new DefaultTableModel ( data , columnNames );
    }
    
    public static DefaultTableModel buildTableModelfromJSON ( String  employeeJSONList )
            {

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
                columnNames.add( "EmployeePK"  );//0
                columnNames.add( "EMP_NAME"  );//1
                columnNames.add( "EMP_NO"  );//2
                columnNames.add( "JOIN_DT"  );//3
                columnNames.add( "PHOTO"  );//4
                columnNames.add( "GENDER"  );//5
                columnNames.add( "DEPARTMENTID"  );//6
                columnNames.add( "ROLE"  );//7
                columnNames.add( "IS_USER" );//8
                columnNames.add( "ADD_LOCAL"  );//9
                columnNames.add( "ADD_PERM"  );//10
                columnNames.add( "username"  );//11
                columnNames.add( "user_id"  );//12
                 
//            columnNames.add( "EDUCATION"  );
//            columnNames.add( "DOB"  );
//                columnNames.add( "JOIN_SKILL"  );
//                columnNames.add( "CURR_SKILL"  );
//                columnNames.add( "AUTH_SKILL"  );
//                columnNames.add( "DISEASE"  );
//                columnNames.add( "PAN_NO"  );
//                columnNames.add( "AADHAR_NO"  );
//                columnNames.add( "PF_NO"  );
//                columnNames.add( "ESIC_NO"  );
//                columnNames.add( "SAL_BAND"  );
//                columnNames.add( "IS_USER"  );
               // columnNames.add( "USERNAME"  );
           

        // data of the table
        for( int i=0; i<records.length (); i++ )
        {
        Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 0 ; columnIndex <columnNames.size () ; columnIndex ++ ) {
                emp = records.getJSONObject ( i);
                vector.add ( emp.get ( columnNames.get ( columnIndex ) )  );
            }
            data.add ( vector );
        }
        return new DefaultTableModel ( data , columnNames );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooserDialog1 = new datechooser.beans.DateChooserDialog();
        dateChooserDialog2 = new datechooser.beans.DateChooserDialog();
        dateChooserDialog3 = new datechooser.beans.DateChooserDialog();
        testDayPicker1 = new com.qt.datapicker.TestDayPicker();
        testDayPicker2 = new com.qt.datapicker.TestDayPicker();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jComboBox10 = new javax.swing.JComboBox<>();
        jComboBox11 = new javax.swing.JComboBox<>();
        jTextField12 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        dateChooserCombo1 = new datechooser.beans.DateChooserCombo();
        dateChooserCombo2 = new datechooser.beans.DateChooserCombo();
        jTextField7 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox12 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jCheckBox2 = new javax.swing.JCheckBox();
        jButton6 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox<>();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        dialogCreateUser = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        setMinimumSize(Toolkit.getDefaultToolkit().getScreenSize());
        setPreferredSize(new java.awt.Dimension(1120, 616));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Name");
        add(jLabel1);
        jLabel1.setBounds(20, 10, 130, 20);

        jTextField1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField1.setNextFocusableComponent(jCheckBox2);
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(150, 10, 220, 27);
        jTextField1.getAccessibleContext().setAccessibleName("nameField");

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Employee No");
        add(jLabel2);
        jLabel2.setBounds(20, 60, 130, 30);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Date of Joining");
        add(jLabel3);
        jLabel3.setBounds(390, 10, 130, 20);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Photo");
        add(jLabel4);
        jLabel4.setBounds(30, 420, 120, 20);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Pan Card No");
        jLabel5.setPreferredSize(new java.awt.Dimension(0, 16));
        add(jLabel5);
        jLabel5.setBounds(390, 410, 0, 20);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("Aadhar No");
        jLabel6.setPreferredSize(new java.awt.Dimension(0, 16));
        add(jLabel6);
        jLabel6.setBounds(390, 380, 0, 20);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("ESIC No");
        jLabel7.setPreferredSize(new java.awt.Dimension(0, 16));
        add(jLabel7);
        jLabel7.setBounds(390, 440, 0, 30);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("PF No");
        jLabel8.setPreferredSize(new java.awt.Dimension(0, 16));
        add(jLabel8);
        jLabel8.setBounds(390, 320, 0, 20);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setText("Local Address");
        add(jLabel10);
        jLabel10.setBounds(390, 340, 120, 20);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("<html>Permanent<br>Address</html>");
        add(jLabel11);
        jLabel11.setBounds(390, 440, 130, 40);

        jTextField2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField2.setNextFocusableComponent(jComboBox1);
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });
        add(jTextField2);
        jTextField2.setBounds(150, 60, 220, 27);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("<html>Select &<br> Upload</html>");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setMaximumSize(new java.awt.Dimension(50, 50));
        jButton1.setMinimumSize(new java.awt.Dimension(50, 50));
        jButton1.setNextFocusableComponent(jComboBox10);
        jButton1.setPreferredSize(new java.awt.Dimension(80, 50));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(290, 420, 80, 50);

        jTextField5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField5.setNextFocusableComponent(jTextField6);
        jTextField5.setPreferredSize(new java.awt.Dimension(0, 22));
        add(jTextField5);
        jTextField5.setBounds(520, 470, 0, 27);

        jTextField4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField4.setNextFocusableComponent(jTextField5);
        jTextField4.setPreferredSize(new java.awt.Dimension(0, 22));
        add(jTextField4);
        jTextField4.setBounds(520, 380, 0, 27);

        jTextField6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField6.setNextFocusableComponent(jComboBox9);
        jTextField6.setPreferredSize(new java.awt.Dimension(0, 22));
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        add(jTextField6);
        jTextField6.setBounds(520, 410, 0, 27);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("Date of Birth");
        add(jLabel12);
        jLabel12.setBounds(20, 350, 130, 20);

        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel13.setText("Cell No");
        jLabel13.setPreferredSize(new java.awt.Dimension(0, 16));
        add(jLabel13);
        jLabel13.setBounds(390, 470, 0, 30);

        jTextField8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField8.setNextFocusableComponent(jTextArea1);
        jTextField8.setPreferredSize(new java.awt.Dimension(0, 22));
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });
        add(jTextField8);
        jTextField8.setBounds(520, 440, 0, 27);

        jLabel14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel14.setText("Joining Skill Level");
        add(jLabel14);
        jLabel14.setBounds(390, 130, 130, 20);

        jComboBox7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Beginner", "Itermediate", "Proficient", "Expert" }));
        jComboBox7.setBorder(null);
        jComboBox7.setNextFocusableComponent(jComboBox11);
        jComboBox7.setOpaque(false);
        add(jComboBox7);
        jComboBox7.setBounds(520, 130, 213, 27);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(null);
        jTextArea1.setNextFocusableComponent(jCheckBox1);
        jScrollPane1.setViewportView(jTextArea1);

        add(jScrollPane1);
        jScrollPane1.setBounds(520, 340, 220, 90);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setBorder(null);
        jScrollPane2.setViewportView(jTextArea2);

        add(jScrollPane2);
        jScrollPane2.setBounds(520, 440, 220, 90);

        jCheckBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox1.setText("<html>Same as<br>above<html>");
        jCheckBox1.setNextFocusableComponent(jTextArea2);
        jCheckBox1.setOpaque(false);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        add(jCheckBox1);
        jCheckBox1.setBounds(390, 480, 90, 40);

        jLabel15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel15.setText("Current Skill Level");
        add(jLabel15);
        jLabel15.setBounds(390, 170, 130, 20);

        jLabel16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel16.setText("Authorised Skill");
        add(jLabel16);
        jLabel16.setBounds(390, 210, 130, 20);

        jLabel17.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel17.setText("Department");
        add(jLabel17);
        jLabel17.setBounds(20, 160, 130, 20);

        jLabel18.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel18.setText("Role");
        add(jLabel18);
        jLabel18.setBounds(30, 250, 130, 20);

        jLabel19.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel19.setText("Disease");
        jLabel19.setPreferredSize(new java.awt.Dimension(0, 16));
        add(jLabel19);
        jLabel19.setBounds(390, 350, 0, 20);

        jLabel20.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel20.setText("Education");
        add(jLabel20);
        jLabel20.setBounds(390, 90, 130, 20);

        jComboBox8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SSC", "HSC", "Bachelor", "Master", "PHD", " " }));
        jComboBox8.setBorder(null);
        jComboBox8.setNextFocusableComponent(jCheckBox2);
        jComboBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox8ActionPerformed(evt);
            }
        });
        add(jComboBox8);
        jComboBox8.setBounds(520, 90, 213, 27);

        jLabel21.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel21.setText("Salary Band");
        add(jLabel21);
        jLabel21.setBounds(390, 250, 130, 20);

        jComboBox9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox9.setBorder(null);
        jComboBox9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox9MouseClicked(evt);
            }
        });
        add(jComboBox9);
        jComboBox9.setBounds(520, 250, 213, 27);

        jLabel22.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel22.setText("Gender");
        add(jLabel22);
        jLabel22.setBounds(20, 380, 130, 20);

        jComboBox10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        jComboBox10.setBorder(null);
        jComboBox10.setNextFocusableComponent(jCheckBox4);
        jComboBox10.setOpaque(false);
        add(jComboBox10);
        jComboBox10.setBounds(150, 380, 220, 30);

        jComboBox11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Beginner", "Itermediate", "Proficient", "Expert" }));
        jComboBox11.setBorder(null);
        jComboBox11.setNextFocusableComponent(jComboBox12);
        add(jComboBox11);
        jComboBox11.setBounds(520, 170, 213, 27);

        jTextField12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField12.setNextFocusableComponent(jLabel4);
        jTextField12.setPreferredSize(new java.awt.Dimension(0, 22));
        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });
        add(jTextField12);
        jTextField12.setBounds(520, 350, 0, 27);

        jTable1.setAutoCreateRowSorter(true);
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
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setRowHeight(20);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable1);

        add(jScrollPane4);
        jScrollPane4.setBounds(760, 10, 340, 400);
        add(jLabel9);
        jLabel9.setBounds(281, 127, 0, 0);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("user img");
        jLabel23.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        add(jLabel23);
        jLabel23.setBounds(150, 420, 120, 90);

        dateChooserCombo1.setCalendarPreferredSize(new java.awt.Dimension(400, 320));
        dateChooserCombo1.setFormat(2);
        dateChooserCombo1.setFieldFont(new java.awt.Font("Leelawadee UI", java.awt.Font.PLAIN, 12));
        add(dateChooserCombo1);
        dateChooserCombo1.setBounds(520, 10, 210, 27);

        dateChooserCombo2.setCalendarPreferredSize(new java.awt.Dimension(400, 320));
        dateChooserCombo2.setFieldFont(new java.awt.Font("Leelawadee UI", java.awt.Font.PLAIN, 12));
        add(dateChooserCombo2);
        dateChooserCombo2.setBounds(150, 350, 220, 25);

        jTextField7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField7.setNextFocusableComponent(jTextField12);
        jTextField7.setPreferredSize(new java.awt.Dimension(0, 22));
        add(jTextField7);
        jTextField7.setBounds(520, 320, 0, 27);

        jLabel24.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel24.setText("Grade");
        add(jLabel24);
        jLabel24.setBounds(20, 110, 130, 20);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox1.setMaximumRowCount(50);
        jComboBox1.setBorder(null);
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
        jComboBox1.setBounds(150, 110, 220, 27);

        jLabel25.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel25.setText("Designation");
        add(jLabel25);
        jLabel25.setBounds(390, 50, 130, 20);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox2.setNextFocusableComponent(jComboBox10);
        jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox2MouseClicked(evt);
            }
        });
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        add(jComboBox2);
        jComboBox2.setBounds(520, 50, 210, 27);

        jComboBox12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Beginner", "Itermediate", "Proficient", "Expert" }));
        jComboBox12.setBorder(null);
        jComboBox12.setNextFocusableComponent(jTextField7);
        add(jComboBox12);
        jComboBox12.setBounds(520, 210, 213, 27);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jButton3.setText("Add to Master");
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setMaximumSize(new java.awt.Dimension(120, 20));
        jButton3.setMinimumSize(new java.awt.Dimension(120, 20));
        jButton3.setPreferredSize(new java.awt.Dimension(120, 20));
        jButton3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jButton3FocusGained(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(770, 430, 130, 30);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jButton4.setText("Export to Excel");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(770, 470, 130, 30);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(960, 470, 130, 30);

        jButton5.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jButton5.setText("Edit Record");
        jButton5.setEnabled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        add(jButton5);
        jButton5.setBounds(960, 430, 130, 30);

        jLabel26.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel26.setText("<html> Nature of <br>Employment</html>");
        add(jLabel26);
        jLabel26.setBounds(390, 280, 130, 50);

        jComboBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Permanent", "On Contract", "Casual", "On Daily Wages", "Other", " " }));
        jComboBox3.setBorder(null);
        jComboBox3.setNextFocusableComponent(jTextField8);
        jComboBox3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox3MouseClicked(evt);
            }
        });
        add(jComboBox3);
        jComboBox3.setBounds(520, 290, 213, 27);

        jLabel27.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel27.setText("Username ");
        add(jLabel27);
        jLabel27.setBounds(20, 60, 120, 0);

        jLabel28.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel28.setText("Password");
        add(jLabel28);
        jLabel28.setBounds(20, 90, 120, 0);

        jLabel29.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel29.setText("Re-password ");
        add(jLabel29);
        jLabel29.setBounds(20, 120, 120, 0);

        jTextField13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField13.setEnabled(false);
        jTextField13.setNextFocusableComponent(jPasswordField1);
        add(jTextField13);
        jTextField13.setBounds(150, 60, 220, 0);

        jCheckBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox2.setText("Is a user ?");
        jCheckBox2.setNextFocusableComponent(jTextField13);
        jCheckBox2.setOpaque(false);
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        add(jCheckBox2);
        jCheckBox2.setBounds(150, 40, 120, 0);

        jButton6.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jButton6.setText("Reset");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        add(jButton6);
        jButton6.setBounds(770, 510, 130, 30);

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 0, 51));
        jLabel30.setText("*");
        add(jLabel30);
        jLabel30.setBounds(10, 120, 10, 0);

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 0, 51));
        jLabel31.setText("*");
        add(jLabel31);
        jLabel31.setBounds(20, 250, 10, 20);

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 0, 51));
        jLabel32.setText("*");
        add(jLabel32);
        jLabel32.setBounds(10, 60, 10, 0);

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 0, 51));
        jLabel33.setText("*");
        add(jLabel33);
        jLabel33.setBounds(10, 90, 10, 0);

        jButton7.setText("Import Excel (xls)");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        add(jButton7);
        jButton7.setBounds(960, 510, 130, 23);

        jComboBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox4.setMinimumSize(new java.awt.Dimension(73, 30));
        jComboBox4.setNextFocusableComponent(jTextField13);
        jComboBox4.setOpaque(false);
        jComboBox4.setPreferredSize(new java.awt.Dimension(73, 30));
        add(jComboBox4);
        jComboBox4.setBounds(150, 160, 220, 27);

        jCheckBox3.setText("Checker");
        jCheckBox3.setNextFocusableComponent(jCheckBox4);
        jCheckBox3.setOpaque(false);
        jCheckBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox3ItemStateChanged(evt);
            }
        });
        add(jCheckBox3);
        jCheckBox3.setBounds(160, 250, 65, 23);

        jCheckBox4.setText("Maker");
        jCheckBox4.setNextFocusableComponent(jComboBox8);
        jCheckBox4.setOpaque(false);
        add(jCheckBox4);
        jCheckBox4.setBounds(280, 250, 70, 23);

        jPasswordField1.setEnabled(false);
        jPasswordField1.setNextFocusableComponent(jPasswordField2);
        jPasswordField1.setOpaque(false);
        add(jPasswordField1);
        jPasswordField1.setBounds(150, 90, 220, 0);

        jPasswordField2.setEnabled(false);
        jPasswordField2.setOpaque(false);
        add(jPasswordField2);
        jPasswordField2.setBounds(150, 120, 220, 0);

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 0, 51));
        jLabel34.setText("*");
        add(jLabel34);
        jLabel34.setBounds(10, 10, 10, 20);

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("*");
        add(jLabel35);
        jLabel35.setBounds(10, 160, 10, 20);

        jButton8.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jButton8.setText("Delete");
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });
        add(jButton8);
        jButton8.setBounds(770, 550, 130, 29);

        jButton9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton9.setText("Create QR Code");
        jButton9.setEnabled(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        add(jButton9);
        jButton9.setBounds(960, 550, 130, 25);

        dialogCreateUser.setText("Create User");
        dialogCreateUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dialogCreateUserMouseClicked(evt);
            }
        });
        add(dialogCreateUser);
        dialogCreateUser.setBounds(770, 590, 130, 23);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:

        String copyaddress;

        //if (jCheckBox1.isSelected() ){
        copyaddress = jTextArea1.getText ();
        jTextArea2.setText ( copyaddress );
        //}    
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        String name, gender, education, addr_local, add_perm, jn_skill, cur_skill, auth_skill, disease, pan_no, pf_no, esic, sal, DOB, DOJ, cell, aadhar, emp_no, department, role="Maker";//,  isUser, uname, password;

        name = jTextField1.getText ().trim();
        emp_no = jTextField2.getText ();
        gender = jComboBox10.getSelectedItem ().toString ();
        education = jComboBox8.getSelectedItem ().toString ();
        addr_local = jTextArea1.getText ();
        add_perm = jTextArea2.getText ();
        jn_skill = jComboBox7.getSelectedItem ().toString ();
        cur_skill = jComboBox11.getSelectedItem ().toString ();
        auth_skill = jComboBox12.getSelectedItem ().toString ();
       disease = jTextField12.getText ();
        pan_no = jTextField6.getText ();
       pf_no = jTextField7.getText ();
        esic = jTextField8.getText ();
        sal = jComboBox9.getSelectedItem ().toString ();

        // DOB = jComboBox4.getSelectedItem().toString() + jComboBox5.getSelectedItem().toString() + "-" + jComboBox6.getSelectedItem().toString();
        DOB = dateChooserCombo2.getText ().toString ();
        //DOJ = jComboBox1.getSelectedItem().toString() + jComboBox2.getSelectedItem().toString() + "-" + jComboBox3.getSelectedItem().toString();
        DOJ = dateChooserCombo1.getText ().toString ();
        cell = jTextField5.getText ();
        aadhar = jTextField4.getText ();

        //ht = jTextField9.getText ();
        //wt = jTextField10.getText ();
        //department =  DepartmentList.get ( jComboBox4.getSelectedIndex()) ;
        department = "" ;
        DeptSelectionPanel dept ;
        
        for(  int i=0; i<deptpanel.departmentsList.size (); i++  ){
            dept = deptpanel.departmentsList.get ( i );           
                if( dept.getSelection () ){
                    department = department + dept.getDeptId ()+"," ; 
                }
        }
        if( department.length ()>0 ){
            department = department.substring ( 0,   department.length ()-1 ) ;
        }
        
        role = "";
        RoleSelectionPanel roles ;
        
        for(  int i=0; i<rolepanel.rolesList.size (); i++  )
        {
            roles = rolepanel.rolesList.get ( i );            
            if( roles.getSelection () ){
                    role = role + roles.getDeptId () +","; 
            }
        }
        if(  role.length ()>0 ){
            role = role.substring (  0,   role.length ()-1  ) ;
        }
        
        if(role.length () == 0){
                role = "3";
            }
        
//        if( jCheckBox3.isSelected () )
//            role = "Checker" ;
//        if( jCheckBox4.isSelected () )
//            role = "Maker" ;
        
        if (  ! name.equals ( "" ) &&  role.length () > 0  ) 
        {
        
            
            
            

//             uname = password = "";
//            isUser = "false";
//            if ( jCheckBox2.isSelected () ) 
//            {
//                uname = jTextField13.getText ();
//                String pass1, pass2;
//                pass1 = jPasswordField1.getText ().trim ();
//                pass2 = jPasswordField2.getText ().trim ();
//
//                if (  ! pass1.equals ( pass2 ) ) 
//                {
//                    JOptionPane.showMessageDialog ( null , "Please repeat password correctly !" );
//                } else 
//                {
//                    password = pass1;
//                    isUser = "true";
//                }
//            }
            
            String createdOn = "", editedOn = "" ;
            SimpleDateFormat sdf = new SimpleDateFormat(  "MMM dd, yyyy HH:mm" ) ;
            createdOn = sdf.format( Calendar.getInstance ().getTime ()  );
            editedOn =  sdf.format( Calendar.getInstance ().getTime ()  ); 
            int editedBy = Proman_User.getEID () ;
            
         //   if(StaticValues.getAppMode ().equalsIgnoreCase ( "both")){
                String result = DB_Operations.insertIntoEmployeeMaster (    name ,  emp_no , DOJ , image ,   gender ,  department , role , education ,  cell ,  DOB ,  addr_local , add_perm , jn_skill ,  cur_skill ,  auth_skill ,  disease ,  pan_no ,  aadhar ,  esic , pf_no , sal  , "false" , "null" ,   "null" , createdOn , editedOn , editedBy );
                //JOptionPane.showMessageDialog ( null , "Successfuly added new employee " );
                
                
                try{
                    
                    if(image==null){   image = new String("myimage").getBytes ();}
                    
                    String addEmpAPICall = "employeeadd?EMP_NAME="+URLEncoder.encode(name, "UTF-8")+"&EMP_NO="+URLEncoder.encode(emp_no, "UTF-8")+"&JOIN_DT="+URLEncoder.encode(DOJ , "UTF-8")+"&PHOTO="+URLEncoder.encode(image.toString(), "UTF-8")+"&GENDER="+URLEncoder.encode( gender, "UTF-8")+"&DEPARTMENTID="+URLEncoder.encode( department, "UTF-8")+"&ROLE="+URLEncoder.encode( role, "UTF-8")+"&EDUCATION="+URLEncoder.encode( education, "UTF-8")+"&CELL_NO="+URLEncoder.encode( cell, "UTF-8")+"&DOB="+URLEncoder.encode( DOB, "UTF-8")+"&ADD_LOCAL="+URLEncoder.encode( addr_local, "UTF-8")+"&ADD_PERM="+URLEncoder.encode( add_perm, "UTF-8")+"&JOIN_SKILL="+URLEncoder.encode( jn_skill, "UTF-8")+"&CURR_SKILL="+URLEncoder.encode( cur_skill, "UTF-8")+"&AUTH_SKILL="+URLEncoder.encode(auth_skill, "UTF-8")+"&DISEASE="+URLEncoder.encode( disease, "UTF-8")+"&PAN_NO="+URLEncoder.encode( pan_no, "UTF-8")+"&AADHAR_NO="+URLEncoder.encode( aadhar, "UTF-8")+"&PF_NO="+URLEncoder.encode( pf_no, "UTF-8")+"&ESIC_NO="+URLEncoder.encode( esic, "UTF-8")+"&SAL_BAND="+URLEncoder.encode( sal, "UTF-8")+"&IS_USER="+URLEncoder.encode( "false", "UTF-8")+"&USERNAME="+URLEncoder.encode( "null", "UTF-8")+"&PASSWORD="+URLEncoder.encode( "null", "UTF-8")+"&CREATED_ON="+URLEncoder.encode( createdOn, "UTF-8")+"&EDITED_ON="+URLEncoder.encode( editedOn, "UTF-8")+"&EDITED_BY="+URLEncoder.encode( editedBy+"", "UTF-8");
                        String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                        JOptionPane.showMessageDialog ( null , result2);
                        System.out.println(result2);
                        //{"data":{"insert_id":50},"message":{"success":{"104":"Employee created successfully."}},"status":"success"}
                        JSONObject responseObject = (JSONObject) new JSONTokener(  result2   ).nextValue() ;
                        int userParentId = responseObject.getJSONObject ( "data").getInt("insert_id") ;
                    
//                        if(isUser.equals ( "true" )  )
//                        {
//
//                            //String createUserAPICall = "useradd?username="+URLEncoder.encode(uname,"UTF-8")+"&password="+URLEncoder.encode(password,"UTF-8")+"&password_confirmation="+URLEncoder.encode(password,"UTF-8")+"&user_id=&parent_id="+URLEncoder.encode(VALUE,"UTF-8")+"&acc_expiry_option="+URLEncoder.encode(VALUE,"UTF-8")+"&email="+URLEncoder.encode(VALUE,"UTF-8")+"&firstname="+URLEncoder.encode(VALUE,"UTF-8")+"&lastname="+URLEncoder.encode(VALUE,"UTF-8")+"&email="+URLEncoder.encode(VALUE,"UTF-8")+"&role_id="+URLEncoder.encode(VALUE,"UTF-8")+"&user_type="+URLEncoder.encode(VALUE,"UTF-8")+"&contactno="+URLEncoder.encode(VALUE,"UTF-8")+"&theme="+URLEncoder.encode(VALUE,"UTF-8")+"&profile_photo="+URLEncoder.encode(VALUE,"UTF-8")+"&department_id="+URLEncoder.encode(VALUE,"UTF-8")+"&acc_expiry_option="+URLEncoder.encode(VALUE,"UTF-8")+"&acc_expiry_option="+URLEncoder.encode(VALUE,"UTF-8")+"&expiry_date="+URLEncoder.encode(VALUE,"UTF-8") ;
//                            // String createUserAPICall = "useradd?username="+URLEncoder.encode(uname,"UTF-8")+"&password="+URLEncoder.encode(password,"UTF-8")+"&password_confirmation="+URLEncoder.encode(password,"UTF-8")+"&parent_id="+userParentId+"&acc_expiry_option=&email=&firstname="+URLEncoder.encode(name,"UTF-8")+"&lastname="+URLEncoder.encode(name,"UTF-8")+"&email="+URLEncoder.encode("trixwaretech@gmail.com","UTF-8")+"&role_id="+URLEncoder.encode(role+"","UTF-8")+"&user_type=consumer&contactno="+URLEncoder.encode(cell,"UTF-8")+"&theme=&profile_photo=&department_id="+URLEncoder.encode(department+"","UTF-8")+"&acc_expiry_option=&expiry_date=" ;
//
//                            if( !uname.contains ( " ")  && !password.contains( " " ) )
//                            {
//                                String[] names ;
//                                String createUserAPICall = null;
//                                if(  name.contains(" ")){
//                                    names = name.split(" ");
//                                    createUserAPICall = "useradd?username="+URLEncoder.encode(uname,"UTF-8")+"&password="+URLEncoder.encode(password,"UTF-8")+"&password_confirmation="+URLEncoder.encode(password,"UTF-8")+"&acc_expiry_option=n&email=example@example.com&firstname="+URLEncoder.encode(names[0],"UTF-8")+"&lastname="+URLEncoder.encode(names[1],"UTF-8")+"&email=example@example.com&role_id="+URLEncoder.encode(role+"","UTF-8")+"&user_type=operator&contactno="+URLEncoder.encode(cell,"UTF-8")+"&theme=&profile_photo=&department_id=4,3&designation_id="+URLEncoder.encode(department+"","UTF-8")+"&acc_expiry_option=&expiry_date=&ref_emp_id="+URLEncoder.encode(""+userParentId,"UTF-8");
//                                }else{
//                                    createUserAPICall = "useradd?username="+URLEncoder.encode(uname,"UTF-8")+"&password="+URLEncoder.encode(password,"UTF-8")+"&password_confirmation="+URLEncoder.encode(password,"UTF-8")+"&acc_expiry_option=n&email=example@example.com&firstname="+URLEncoder.encode(name,"UTF-8")+"&lastname="+URLEncoder.encode(name,"UTF-8")+"&email=example@example.com&role_id="+URLEncoder.encode(role+"","UTF-8")+"&user_type=operator&contactno="+URLEncoder.encode(cell,"UTF-8")+"&theme=&profile_photo=&department_id=4,3&designation_id="+URLEncoder.encode(department+"","UTF-8")+"&acc_expiry_option=&expiry_date=&ref_emp_id="+URLEncoder.encode(""+userParentId,"UTF-8");
//                                }
//                                System.out.println(createUserAPICall);
//                                result2 =  WebAPITester.prepareWebCall ( createUserAPICall , StaticValues.getHeader ()   , "");
//                                JOptionPane.showMessageDialog ( null , result2);
//                            }else{
//                                JOptionPane.showMessageDialog ( null , "Please enter appropriate username and password " );        
//                            }
//                        }else{
//
//                                
//                        }
//                    
                    
                }catch(UnsupportedEncodingException e){
                    
                }
                
        //    }
        } else {
            
            JOptionPane.showMessageDialog ( null , "Please fill fields marked with * " );            
        }
        
       

        jTextField1.setText ( "" );
        jTextField2.setText ( "" );
        jLabel23.setIcon ( null );
        jComboBox10.setSelectedItem ( 0 );
        jComboBox8.setSelectedItem ( 0 );
        jTextArea1.setText ( "" );
        jTextArea2.setText ( "" );
        jComboBox7.setSelectedItem ( 0 );
        jComboBox7.setSelectedItem ( 0 );
        //  jTextField11.setText("");
        jTextField12.setText ( "" );
        jTextField7.setText ( "" );
        jTextField6.setText ( "" );
        jTextField5.setText ( "" );
        jComboBox9.setSelectedItem ( 0 );
       
        jTextField8.setText ( "" );
        jTextField4.setText ( "" );
        jTextField13.setText ( "" );
        jPasswordField1.setText ( "" );
        jTextField1.setText ( "" );
        jPasswordField2.setText ( "" );

        if( deptpanel.departmentsList !=null  ){
            if( deptpanel.departmentsList.size ()>0  ){
                for(  int i=0; i<deptpanel.departmentsList.size (); i++  ){
                    //deptpanel.departmentsList.get ( i ).setSelection ( false );           
                    deptpanel.departmentsList.get ( i ).setSelection ( false );           
                }
            }
        }

        if( rolepanel.rolesList !=null  ){
            if( rolepanel.rolesList.size ()>0  ){
                for(  int i=0; i<rolepanel.rolesList.size (); i++  ){
                    rolepanel.rolesList.get ( i ).setSelection ( false ); 
                }
            }
        }

          

       //   loadContent ();
       String addEmpAPICall = "employees";
                String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

                if( !  result2.contains(  "not found"   )  ){
                    if ( result2!= null ) {
                        jTable1.setModel ( buildTableModelfromJSON (result2 ) );
                        jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                        jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                    }
                }
          
        
        
//         try {
//             ResultSet result = null ;
//             if( Proman_User.getEID () == 1){
//        
//                result = DB_Operations.getMasters ( "employee" );
//
//                if ( result != null ) {
//                    jTable1.setModel ( buildTableModel ( result ) );
//                    jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
//                    jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
//                }
//                
//            }else{
//                result = DB_Operations.getSingleMasters ( "employee" , Proman_User.getEID () );
//
//                if ( result != null ) {
//                    jTable1.setModel ( buildTableModel ( result ) );
//                    jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
//                    jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
//                }
//                
//                jButton3.setVisible ( false );
//                jButton4.setVisible ( false );
//                jButton6.setVisible ( false );
//                jButton7.setVisible ( false );
//                
//                
//                jLabel35.setVisible( false );
//                jLabel17.setVisible( false );
//                jLabel31.setVisible( false );
//                jLabel18.setVisible( false );
//                
//            }
//            result.close() ;
//         }catch( SQLException e ){
//             
//         }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar ();
        if ( ( Character.isDigit ( enter ) ) ) {
            evt.consume ();
        }
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        // TODO add your handling code here:
        char enter = evt.getKeyChar ();
        if (  ! ( Character.isDigit ( enter ) ) ) {
            evt.consume ();
        }
    }//GEN-LAST:event_jTextField2KeyTyped


    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Workbook wb = new HSSFWorkbook ();
        File file = null;
        ArrayList<String> column_names = new ArrayList<String> ();
        ArrayList<String[]> values = new ArrayList<String[]> ();
        int columnCount = 0;

        try {
            ResultSet result = DB_Operations.getMasters ( "employee" );
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
            writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
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
        sheet1 = wb.createSheet ( "Employee Master" );
        sheet1.setColumnWidth ( 0 , ( 15 * 500 ) );

        Cell c = null;
        Cell c3 = null;

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
            file = new File ( "Employee Report File " + strDate2 + ".xls" );

            FileOutputStream os = null;

            try {
                os = new FileOutputStream ( file );
                wb.write ( os );
                wb.close ();
                os.close ();

            } catch ( IOException e ) {
                JOptionPane.showMessageDialog ( null , "Error " + e.getMessage () );
                writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            } finally {

            }
        }

        JOptionPane.showMessageDialog ( null , "New File generated at " + file.getAbsolutePath () );

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        String formatName = "" ;
        JFileChooser fileChooser = new JFileChooser ();
        fileChooser.setFileFilter ( new MyCustomFilter ("image") );
        fileChooser.setCurrentDirectory ( new File ( System.getProperty ( "user.home" ) ) );
        int result = fileChooser.showOpenDialog ( this );
        if ( result == JFileChooser.APPROVE_OPTION ) {
            File selectedFile = fileChooser.getSelectedFile ();
            
            BufferedImage image1;
            try {
                image1 = ImageIO.read ( new File ( selectedFile.getAbsolutePath () ) );

                BufferedImage inputImage = ImageIO.read ( selectedFile );

                BufferedImage outputImage = new BufferedImage ( inputImage.getWidth () , inputImage.getHeight () , inputImage.getType () );

                Graphics2D g2d = outputImage.createGraphics ();
                g2d.drawImage ( inputImage , 0 , 0 , inputImage.getWidth () , inputImage.getHeight () , null );
                g2d.dispose ();

                // extracts extension of output file
                formatName = selectedFile.getAbsolutePath ().substring ( selectedFile.getAbsolutePath ().lastIndexOf ( "." ) + 1 );
                ImageIO.write ( outputImage , formatName , new File ( "new_Image_1.jpg" ) );

            } catch ( IOException ex ) {
                //Logger.getLogger(Employee_HR_Master.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println ( ex.getMessage () );
                writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            }

            FileInputStream fis;
            ByteArrayOutputStream bos;
            try {
                //fis = new FileInputStream(selectedFile);
                fis = new FileInputStream ( new File ( "new_Image_1.jpg" ) );
                bos = new ByteArrayOutputStream ();
                byte[] buf = new byte[ 1024 ];

                for ( int readNum ; ( readNum = fis.read ( buf ) ) != -1 ; ) {
                    bos.write ( buf , 0 , readNum );
                }

                image = bos.toByteArray ();
                ImageIcon img = new ImageIcon ( image );
                jLabel23.setIcon ( img );

                
                byte[] encoded = Base64.encodeBase64 (image);
                String encodedString = new String(encoded);
                
                
                //String encodedString   = Base64.encodeBase64URLSafeString ( image );
                //String addEmpAPICall  = "refdocadd?binarydata="+  URLEncoder.encode(encodedString, "UTF-8")+"&RD_NAME="+URLEncoder.encode(selectedFile.getAbsoluteFile ().getName (), "UTF-8")+"&RDM_UID="+URLEncoder.encode(1+"" , "UTF-8")+"&ext="+URLEncoder.encode(  formatName  , "UTF-8") ;
                //String result2             =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                //String result2             =  WebAPITester.prepareWebCall3 ( "refdocadd"   , StaticValues.getHeader (),  "binarydata="+URLEncoder.encode(encodedString, "UTF-8")+"&RD_NAME="+URLEncoder.encode(selectedFile.getAbsoluteFile ().getName (), "UTF-8")+"&RDM_UID="+URLEncoder.encode(1+"" , "UTF-8")+"&ext="+URLEncoder.encode(  formatName  , "UTF-8"));
                //JOptionPane.showMessageDialog ( null , result2);
                System.out.println( result2);
                
            } catch ( FileNotFoundException ex ) {
                writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            } catch ( IOException ex ) {
                writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
try
{
    
    
        //jButton3.setEnabled ( false );

        ArrayList<String> values = new ArrayList<String> ();
        ArrayList<byte[]> files = new ArrayList<byte[]> ();
        int columnCount = 0;
        // get the model from the jtable
        DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();

        // get the selected row index
        int selectedRowIndex = jTable1.getSelectedRow ();
     
        selectedEmpRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );
       
        //jButton5.setEnabled ( true );
        //jButton9.setEnabled(true);
        //*************created by harshali
        selectedEmpRecordName = model.getValueAt(selectedRowIndex, 1).toString();
        selectedEmpRecordNo = model.getValueAt(selectedRowIndex, 2).toString();
        selectedEmpRecordJDate = model.getValueAt(selectedRowIndex, 3).toString();
        selectedEmpRecordGender = model.getValueAt(selectedRowIndex, 5).toString();
        Role = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 7 ).toString () );
        UserName = model.getValueAt(selectedRowIndex, 11).toString();
        user_id =  model.getValueAt ( selectedRowIndex , 12 ).toString () ;
      //********************
        // columnNames.add( "EmployeePK"  );columnNames.add( "EMP_NAME"  );columnNames.add( "EMP_NO"  );columnNames.add( "JOIN_DT"  );columnNames.add( "DEPARTMENTID"  );columnNames.add( "ROLE"  );columnNames.add( "EDUCATION"  );columnNames.add( "CELL_NO"  );columnNames.add( "DOB"  );columnNames.add( "ADD_LOCAL"  );columnNames.add( "ADD_PERM"  );columnNames.add( "JOIN_SKILL"  );columnNames.add( "CURR_SKILL"  );columnNames.add( "AUTH_SKILL"  );columnNames.add( "DISEASE"  );columnNames.add( "PAN_NO"  );columnNames.add( "AADHAR_NO"  );columnNames.add( "PF_NO"  );columnNames.add( "ESIC_NO"  );columnNames.add( "SAL_BAND"  );columnNames.add( "IS_USER"  );columnNames.add( "USERNAME"  );
       jTextField1.setText ( model.getValueAt ( selectedRowIndex , 1 ).toString ()  );
       
       //String isuser = model.getValueAt ( selectedRowIndex , 8).toString ()  ;
        
//        if(isuser.equalsIgnoreCase ("true"))
//        {
//            jCheckBox2.setSelected ( true );
//            jTextField13.setEnabled ( false );
//            jTextField13.setText ( model.getValueAt ( selectedRowIndex , 11).toString () );
//            jPasswordField1.setEnabled ( true );
//            jPasswordField2.setEnabled ( true );
//            jLabel32.setVisible ( true );
//            jLabel33.setVisible ( true );
//            jLabel30.setVisible ( true );
//            
//        }else{
//            jCheckBox2.setSelected ( false );
//            jTextField13.setEnabled ( false );
//            jPasswordField1.setEnabled ( false );
//            jPasswordField2.setEnabled ( false );
//            jLabel32.setVisible ( false );
//            jLabel33.setVisible ( false );
//            jLabel30.setVisible ( false );
//        }

        jTextField1.setText( model.getValueAt( selectedRowIndex, 1).toString());
        jTextField2.setText( model.getValueAt( selectedRowIndex, 2).toString());

         jTextArea1.setText ( model.getValueAt ( selectedRowIndex , 9).toString ());
         jTextArea2.setText ( model.getValueAt ( selectedRowIndex , 10).toString () );
        // jTextField12.setText ( model.getValueAt ( selectedRowIndex , 14).toString () );
        // jTextField5.setText ( model.getValueAt ( selectedRowIndex , 7 ).toString ()  );
         //jTextField8.setText ( model.getValueAt ( selectedRowIndex , 18).toString ());
         //jTextField4.setText ( model.getValueAt ( selectedRowIndex , 16).toString () );
         //jTextField7.setText ( model.getValueAt ( selectedRowIndex , 17).toString () );
         //jTextField6.setText ( model.getValueAt ( selectedRowIndex , 15).toString () );
        String[] departmentsArr = model.getValueAt ( selectedRowIndex , 6 ).toString ().split ( ",") ;
        String[] roelsArr = model.getValueAt ( selectedRowIndex , 7).toString ().split ( ",") ;
//        jComboBox7.setSelectedItem ( 0 );
//        jComboBox7.setSelectedItem ( 0 );
//        jComboBox12.setSelectedIndex ( 0 );  
//        jTextField2.setText ( values.get ( 2 ) );
         //       jComboBox9.setSelectedItem ( 0 );
        //        jTextField13.setText ( values.get ( 17 ) );
//        jComboBox10.setSelectedItem ( 0 );
//        jComboBox8.setSelectedItem ( 0 );      
//        jTextField13.setEnabled(false);
//        
        if( deptpanel.departmentsList !=null  ){
            if( deptpanel.departmentsList.size ()>0  ){
                for(  int i=0; i<deptpanel.departmentsList.size (); i++  ){
                    //deptpanel.departmentsList.get ( i ).setSelection ( false );           
                    deptpanel.departmentsList.get ( i ).setSelection ( false );           
                }
                
                for(  int i=0; i<deptpanel.departmentsList.size (); i++  ){
                    for(  int j=0; j<departmentsArr.length ; j++  ){
                         if( Integer.parseInt( departmentsArr[j] ) ==   deptpanel.departmentsList.get ( i ).getDeptId ()){
                              deptpanel.departmentsList.get ( i ).setSelection ( true );
                         }
                     }
                }      
            }
        }

        if( rolepanel.rolesList !=null  ){
            if( rolepanel.rolesList.size ()>0  ){
                for(  int i=0; i<rolepanel.rolesList.size (); i++  ){
                    rolepanel.rolesList.get ( i ).setSelection ( false ); 
                }
                
                for(  int i=0; i<rolepanel.rolesList.size (); i++  ){
                    for(  int j=0; j<roelsArr.length ; j++  ){
                        if(  Integer.parseInt( roelsArr[j] ) ==   rolepanel.rolesList.get ( i ).getDeptId () ){
                               rolepanel.rolesList.get ( i ).setSelection ( true ); 
                        }
                    }
                }
            }
        }

      //*******************Role wise priority set edited by mayur****************************************    
//                int role;
//        role=Integer.parseInt(Proman_User.getRole());
        
//        switch (role) {
//            case 1: 
                jButton3.setEnabled ( false );
                jButton4.setEnabled(true);
                jButton7.setEnabled(true);
                jButton5.setEnabled ( true );
                jButton9.setEnabled(true);
                jButton8.setEnabled(false);
//                
//            break;
//
//            case 2:
//                jButton3.setEnabled ( false );
//                jButton4.setEnabled(true);
//                jButton7.setEnabled(false);
//                jButton5.setEnabled(true );
//                jButton9.setEnabled(true);
//                jButton8.setEnabled(false);
//            break;
//
//            case 3:   
//                jButton3.setEnabled ( false );
//                jButton4.setEnabled(false);
//                jButton7.setEnabled(false);
//                jButton5.setEnabled(false);
//                jButton9.setEnabled(false);
//                jButton8.setEnabled(false);
//            break;
//
//            default:
//        }
        
        //*******************end Role wise priority set edited by mayur****************************************
        
        
 /*       ArrayList<String> values = new ArrayList<String> ();
        ArrayList<byte[]> files = new ArrayList<byte[]> ();
        int columnCount = 0;
        // get the model from the jtable
        DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();

        // get the selected row index
        int selectedRowIndex = jTable1.getSelectedRow ();
     
        ResultSet result = null ;
        try {
            result = DB_Operations.getSingleMasters ( "employee" , Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ) );
            selectedEmpRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );

            JOptionPane.showMessageDialog ( null, selectedEmpRecordId);
            
            if ( result != null ) {
                ResultSetMetaData metaData = result.getMetaData ();

                columnCount = metaData.getColumnCount ();

                while ( result.next () ) {

                    for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {
                        if ( columnIndex < columnCount  ) {

                            if ( columnIndex == 5 ) {
                                files.add ( result.getBytes ( columnIndex ) );
                            } else {
                                values.add ( String.valueOf ( result.getObject ( columnIndex ) ) );
                            }
                        }
                    }
                }
            }
            
            result.close() ;
        } catch ( SQLException e ) {
            writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            try {
                result.close() ;
            } catch ( SQLException ex ) {
                System.out.println ( "" );
                writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            }
            System.out.println ( "Error " + e.getMessage () );
        }

        jTextField1.setText ( values.get ( 1 ) );
        jTextField2.setText ( values.get ( 2 ) );
        jComboBox10.setSelectedItem ( 0 );
        jComboBox8.setSelectedItem ( 0 );
        jTextArea1.setText ( values.get ( 10 ) );
        jTextArea2.setText ( values.get ( 11 ) );
        jComboBox7.setSelectedItem ( 0 );
        jComboBox7.setSelectedItem ( 0 );
        jComboBox12.setSelectedIndex ( 0 );
        jTextField12.setText ( values.get ( 15 ) );
        jTextField5.setText ( values.get ( 8 ) );
        jComboBox9.setSelectedItem ( 0 );
        jTextField8.setText ( values.get ( 19 ) );
        jTextField4.setText ( values.get ( 17 ) );
       jTextField7.setText ( values.get ( 18 ) );
        jTextField6.setText ( values.get ( 16 ) );
        jTextField13.setText ( values.get ( 17 ) );

        try {
            jLabel23.setIcon ( null );
            ImageIcon img = new ImageIcon ( files.get ( 0 ) );
            jLabel23.setIcon ( img );
        } catch ( NullPointerException e ) {
            writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
        }
        
        //21 is user
        // 22 username
        // 5 department
        // 6 role
        
        String isUserValue = values.get ( 21 ) ;
        
        if(isUserValue.equals ( "true")){
            jCheckBox2.setSelected ( true );
            jTextField13.setEnabled ( true );
            jPasswordField1.setEnabled ( true );
            jPasswordField2.setEnabled ( true );
            jLabel32.setVisible ( true );
            jLabel33.setVisible ( true );
            jLabel30.setVisible ( true );
        }else{
            jCheckBox2.setSelected ( false );
            jTextField13.setEnabled ( false );
            jPasswordField1.setEnabled ( false );
            jPasswordField2.setEnabled ( false );
            jLabel32.setVisible ( false );
            jLabel33.setVisible ( false );
            jLabel30.setVisible ( false );
        }
        jTextField13.setText( values.get ( 22 )  );
       
        String[] departmentsArr = values.get(5).split ( ",") ;
        String[] roelsArr = values.get(6).split ( ",") ;
        
        jTextField13.setEnabled(false);
        
        if( deptpanel.departmentsList !=null  ){
            if( deptpanel.departmentsList.size ()>0  ){
                for(  int i=0; i<deptpanel.departmentsList.size (); i++  ){
                    //deptpanel.departmentsList.get ( i ).setSelection ( false );           
                    deptpanel.departmentsList.get ( i ).setSelection ( false );           
                }
                
                for(  int i=0; i<deptpanel.departmentsList.size (); i++  ){
                    for(  int j=0; j<departmentsArr.length ; j++  ){
                         if( Integer.parseInt( departmentsArr[j] ) ==   deptpanel.departmentsList.get ( i ).getDeptId ()){
                              deptpanel.departmentsList.get ( i ).setSelection ( true );
                         }
                     }
                }      
            }
        }

        if( rolepanel.rolesList !=null  ){
            if( rolepanel.rolesList.size ()>0  ){
                for(  int i=0; i<rolepanel.rolesList.size (); i++  ){
                    rolepanel.rolesList.get ( i ).setSelection ( false ); 
                }
                
                for(  int i=0; i<rolepanel.rolesList.size (); i++  ){
                    for(  int j=0; j<roelsArr.length ; j++  ){
                        if(  Integer.parseInt( roelsArr[j] ) ==   rolepanel.rolesList.get ( i ).getDeptId () ){
                               rolepanel.rolesList.get ( i ).setSelection ( true ); 
                        }
                    }
                }
            }
        }
          */
 
 
          
          //jButton9.setEnabled ( true );
}catch(Exception e)
{
    e.getMessage();
}
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        String name, gender, education, addr_local, add_perm, jn_skill, cur_skill, auth_skill, disease, pan_no, pf_no, esic, sal, DOB, DOJ, cell, aadhar, emp_no, department, role="Maker";//, isUser, uname, pword1, pword2;

        name = jTextField1.getText ();
        emp_no = jTextField2.getText ();
        gender = jComboBox10.getSelectedItem ().toString ();
        education = jComboBox8.getSelectedItem ().toString ();
        addr_local = jTextArea1.getText ();
        add_perm = jTextArea2.getText ();
        jn_skill = jComboBox7.getSelectedItem ().toString ();
        cur_skill = jComboBox11.getSelectedItem ().toString ();
        auth_skill = jComboBox12.getSelectedItem ().toString ();
        disease = jTextField12.getText ();
        pan_no = jTextField6.getText ();
        pf_no = jTextField7.getText ();
        esic = jTextField8.getText ();
        sal = jComboBox9.getSelectedItem ().toString ();
        
       

//        if ( jCheckBox2.isSelected () ) 
//        {
//            isUser = "true";
//            uname = jTextField13.getText().trim();
//            pword1 = jPasswordField1.getText().trim();
//            pword2 = jPasswordField2.getText().trim();
//            
//            if( ! pword1.equals ( pword2)){
//                JOptionPane.showMessageDialog ( null, "Passwords do not match. Please enter appropriate password");
//                pword1 = "";
//                pword2 = "";
//                jPasswordField1.setText("");
//                jPasswordField2.setText("");
//            }
//            
//        } else 
//        {
//            isUser = "false";
//            uname = "" ;
//            pword1 = "";
//            pword2 = "";
//        }

        DOB = dateChooserCombo2.getText ().toString ();
        DOJ = dateChooserCombo1.getText ().toString ();
        cell = jTextField5.getText ();
        aadhar = jTextField4.getText ();

//       department =  DepartmentList.get ( jComboBox4.getSelectedIndex()) ;
//        
//        if( jCheckBox3.isSelected () )
//            role = "Checker" ;
//        
//        if( jCheckBox4.isSelected () )
//            role = "Maker" ;

        department = "" ;
        DeptSelectionPanel dept ;
        
        if( deptpanel.departmentsList !=null  ){
            if( deptpanel.departmentsList.size ()>0  ){
                for(  int i=0; i<deptpanel.departmentsList.size (); i++  ){
                    dept = deptpanel.departmentsList.get ( i );           
                        if( dept.getSelection () ){
                            department = department + dept.getDeptId ()+"," ; 
                        }
                }
                if( department.length ()>0 ){
                    department = department.substring ( 0,   department.length ()-1 ) ;
                }
            }
        }
        
        
        
        role = "";
        RoleSelectionPanel roles ;
        
        if( rolepanel.rolesList !=null  ){
            if( rolepanel.rolesList.size ()>0  ){
                for(  int i=0; i<rolepanel.rolesList.size (); i++  ){
                    roles = rolepanel.rolesList.get ( i );            
                    if( roles.getSelection () ){
                            role = role + roles.getDeptId () +","; 
                    }
                }
                if(  role.length ()>0 ){
                    role = role.substring (  0,   role.length ()-1  ) ;
                }
            }
        }

        if ( selectedEmpRecordId == 0 ) 
        {
            JOptionPane.showMessageDialog ( null , "Please select a record first" );
        } else 
        {

            if( ! name.equals ( "")   )
            {
                
                String editedOn = "" ;
                SimpleDateFormat sdf = new SimpleDateFormat(  "MMM dd, yyyy HH:mm" ) ;
                editedOn =  sdf.format( Calendar.getInstance ().getTime ()  ); 
                int editedBy = Proman_User.getEID () ;
                        
//                if(isUser.equals ( "true"))
//                {
//                    if( ! uname.equals ( "") && ! pword1.equals ( "") ){
//                    //    String result = DB_Operations.updateEmployeeMaster ( name ,  emp_no ,DOJ ,image , gender , department ,  role , education , cell , DOB ,  addr_local ,  add_perm , jn_skill , cur_skill ,  auth_skill ,  disease ,   pan_no , aadhar ,  esic , pf_no ,  sal ,  isUser, uname,  pword1,  editedOn , editedBy , selectedEmpRecordId );
//                    //    JOptionPane.showMessageDialog ( null , "" + result );
//                        try{
//                    
//                            if(image==null)
//                            { 
//                                image = new String("myimage").getBytes ();
//                            }
//
//                            String addEmpAPICall = "employeeupdate?EmployeePK="+URLEncoder.encode(selectedEmpRecordId+"", "UTF-8")+"&EMP_NAME="+URLEncoder.encode(name, "UTF-8")+"&EMP_NO="+URLEncoder.encode(emp_no, "UTF-8")+"&JOIN_DT="+URLEncoder.encode(DOJ , "UTF-8")+"&PHOTO="+URLEncoder.encode(image.toString(), "UTF-8")+"&GENDER="+URLEncoder.encode( gender, "UTF-8")+"&DEPARTMENTID="+URLEncoder.encode( department, "UTF-8")+"&ROLE="+URLEncoder.encode( role, "UTF-8")+"&EDUCATION="+URLEncoder.encode( education, "UTF-8")+"&CELL_NO="+URLEncoder.encode( cell, "UTF-8")+"&DOB="+URLEncoder.encode( DOB, "UTF-8")+"&ADD_LOCAL="+URLEncoder.encode( addr_local, "UTF-8")+"&ADD_PERM="+URLEncoder.encode( add_perm, "UTF-8")+"&JOIN_SKILL="+URLEncoder.encode( jn_skill, "UTF-8")+"&CURR_SKILL="+URLEncoder.encode( cur_skill, "UTF-8")+"&AUTH_SKILL="+URLEncoder.encode(auth_skill, "UTF-8")+"&DISEASE="+URLEncoder.encode( disease, "UTF-8")+"&PAN_NO="+URLEncoder.encode( pan_no, "UTF-8")+"&AADHAR_NO="+URLEncoder.encode( aadhar, "UTF-8")+"&PF_NO="+URLEncoder.encode( pf_no, "UTF-8")+"&ESIC_NO="+URLEncoder.encode( esic, "UTF-8")+"&SAL_BAND="+URLEncoder.encode( sal, "UTF-8")+"&IS_USER="+URLEncoder.encode( isUser, "UTF-8")+"&USERNAME="+URLEncoder.encode( uname, "UTF-8")+"&PASSWORD="+URLEncoder.encode( pword1, "UTF-8")+"&CREATED_ON=&EDITED_ON="+URLEncoder.encode( editedOn, "UTF-8")+"&EDITED_BY="+URLEncoder.encode( editedBy+"", "UTF-8");
//                            String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
//                            JOptionPane.showMessageDialog ( null , retreiveSuccessMessage2( result2) );
//                       
//                        
//                        }catch(UnsupportedEncodingException e){
//                        }
//                    }else
//                    {
//                        JOptionPane.showMessageDialog ( null , "" + "Please enter valid username and password" );
//                    }
//                }else
//                {
//                    String result = DB_Operations.updateEmployeeMaster ( name ,  emp_no ,DOJ ,image , gender , department ,  role , education , cell , DOB ,  addr_local ,  add_perm , jn_skill , cur_skill ,  auth_skill ,  disease ,   pan_no , aadhar ,  esic , pf_no ,  sal ,  isUser, uname,  pword1,  editedOn , editedBy , selectedEmpRecordId );
//                        JOptionPane.showMessageDialog ( null , "" + result );
                        try{
                            if(image==null){   image = new String("myimage").getBytes ();}
                            String addEmpAPICall = "employeeupdate?EmployeePK="+URLEncoder.encode(selectedEmpRecordId+"", "UTF-8")+"&EMP_NAME="+URLEncoder.encode(name, "UTF-8")+"&EMP_NO="+URLEncoder.encode(emp_no, "UTF-8")+"&JOIN_DT="+URLEncoder.encode(DOJ , "UTF-8")+"&PHOTO="+URLEncoder.encode(image.toString(), "UTF-8")+"&GENDER="+URLEncoder.encode( gender, "UTF-8")+"&DEPARTMENTID="+URLEncoder.encode( department, "UTF-8")+"&ROLE="+URLEncoder.encode( role, "UTF-8")+"&EDUCATION="+URLEncoder.encode( education, "UTF-8")+"&CELL_NO="+URLEncoder.encode( cell, "UTF-8")+"&DOB="+URLEncoder.encode( DOB, "UTF-8")+"&ADD_LOCAL="+URLEncoder.encode( addr_local, "UTF-8")+"&ADD_PERM="+URLEncoder.encode( add_perm, "UTF-8")+"&JOIN_SKILL="+URLEncoder.encode( jn_skill, "UTF-8")+"&CURR_SKILL="+URLEncoder.encode( cur_skill, "UTF-8")+"&AUTH_SKILL="+URLEncoder.encode(auth_skill, "UTF-8")+"&DISEASE="+URLEncoder.encode( disease, "UTF-8")+"&PAN_NO="+URLEncoder.encode( pan_no, "UTF-8")+"&AADHAR_NO="+URLEncoder.encode( aadhar, "UTF-8")+"&PF_NO="+URLEncoder.encode( pf_no, "UTF-8")+"&ESIC_NO="+URLEncoder.encode( esic, "UTF-8")+"&SAL_BAND="+URLEncoder.encode( sal, "UTF-8")+"&IS_USER="+URLEncoder.encode( "false", "UTF-8")+"&USERNAME="+URLEncoder.encode( "null", "UTF-8")+"&PASSWORD="+URLEncoder.encode( "null", "UTF-8")+"&CREATED_ON=&EDITED_ON="+URLEncoder.encode( editedOn, "UTF-8")+"&EDITED_BY="+URLEncoder.encode( editedBy+"", "UTF-8");
                            String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                            //JOptionPane.showMessageDialog ( null , retreiveSuccessMessage2( result2));
                            JOptionPane.showMessageDialog(null, "Updated Successfully...!");
                        }catch(UnsupportedEncodingException e){
                        }
                //}
            }else{
                JOptionPane.showMessageDialog ( null , "" + "Please enter all mandatory field marked with * " );
            }
        }

        jTextField1.setText ( "" );
        jTextField2.setText ( "" );
        jLabel23.setIcon ( null );
        jComboBox10.setSelectedItem ( 0 );
        jComboBox8.setSelectedItem ( 0 );
        jTextArea1.setText ( "" );
        jTextArea2.setText ( "" );
        jComboBox7.setSelectedItem ( 0 );
        jComboBox7.setSelectedItem ( 0 );
        jTextField12.setText ( "" );
        jTextField7.setText ( "" );
        jTextField6.setText ( "" );
        jTextField5.setText ( "" );
        jComboBox9.setSelectedItem ( 0 );
        jTextField8.setText ( "" );
        jTextField4.setText ( "" );
        

        jTextField13.setText ( "" );

        jPasswordField1.setText ( "" );
        jTextField1.setText ( "" );
        jPasswordField2.setText ( "" );

       // loadContent ();
        String addEmpAPICall = "employees";
                String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

                if( !  result2.contains(  "not found"   )  ){
                    if ( result2!= null ) {
                        jTable1.setModel ( buildTableModelfromJSON (result2 ) );
                        jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                        jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                    }
                }

    }//GEN-LAST:event_jButton5ActionPerformed

    
    public static  String retreiveSuccessMessage2( String json){
        String id = "Not able to fetch the response for your request. Please refresh you master form and try again";
        JSONObject jObject = new JSONObject ( json );
        id = jObject.getString("106" );
        return id ;
    }
    
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        HomeScreen.home.removeForms ();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        jTextField1.setText ( "" );
        jTextField2.setText ( "" );
        jLabel23.setIcon ( null );
        jComboBox10.setSelectedItem ( 0 );
        jComboBox8.setSelectedItem ( 0 );
        jTextArea1.setText ( "" );
        jTextArea2.setText ( "" );
        jComboBox7.setSelectedItem ( 0 );
        jComboBox7.setSelectedItem ( 0 );
        jTextField12.setText ( "" );
        jTextField7.setText ( "" );
        jTextField6.setText ( "" );
        jTextField5.setText ( "" );
        jComboBox9.setSelectedItem ( 0 );
        jTextField8.setText ( "" );
        jTextField4.setText ( "" );
       
        jTextField13.setText ( "" );

        jPasswordField1.setText ( "" );
        jTextField1.setText ( "" );

        jButton3.setEnabled ( true );
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jButton3FocusGained
        // TODO add your handling code here:


    }//GEN-LAST:event_jButton3FocusGained

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:

        JCheckBox cb = ( JCheckBox ) evt.getSource ();

        if ( cb.isSelected () ) {

            jTextField13.setEnabled ( true );
            jPasswordField1.setEnabled ( true );
            jPasswordField2.setEnabled ( true );
            jLabel32.setVisible ( true );
            jLabel33.setVisible ( true );
            jLabel30.setVisible ( true );

        } else {
            jTextField13.setEnabled ( false );
            jPasswordField1.setEnabled ( false );
            jPasswordField2.setEnabled ( false );
            jLabel32.setVisible ( false );
            jLabel33.setVisible ( false );
            jLabel30.setVisible ( false );

        }

    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked

        if ( jComboBox1.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add values in grade from Utility  Master " );
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
        if ( jComboBox2.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add values in designation from Utility  Master " );
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2MouseClicked

    private void jComboBox9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox9MouseClicked
        if ( jComboBox9.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add values in Salary Band from Utility  Master " );
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox9MouseClicked

    private void jComboBox3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox3MouseClicked
        if ( jComboBox3.getItemCount () == 0 ) {
            JOptionPane.showMessageDialog ( null , "<html><size='06'>First add values in Nature of Employment from Utility  Master " );
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3MouseClicked

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        // TODO add your handling code here:

        
//        File selectedFile = null;
//
//        JFileChooser fileChooser = new JFileChooser ();
//        fileChooser.setFileFilter ( new MyCustomFilter ("excel 97-2003") );
//        fileChooser.setCurrentDirectory ( new File ( System.getProperty (
//                "user.home" ) ) );
//        int result = fileChooser.showOpenDialog ( this );
//        if ( result == JFileChooser.APPROVE_OPTION ) {
//
//            selectedFile = fileChooser.getSelectedFile ();
//
//        }
//
//        FileChannel inputChannel = null;
//        FileChannel outputChannel = null;
//
//        File dir = new File ( "dataupload\\" );
//        dir.mkdirs ();
//
//        String url;
//        try {
//
//            url = "jdbc:sqlite:" + StaticValues.dbName;
//            Connection con = DriverManager.getConnection ( url );
//            Statement stm = con.createStatement ();
//            PreparedStatement pst;
//
//            FileInputStream fis;
//            //String query = "INSERT INTO xl_import_test ( ID, name, address, phone, email) VALUES ( ?,?,?,?,?)";
//            String query = "INSERT INTO employee (  EMP_NAME ) VALUES ( ?  )";
//
//            pst = con.prepareStatement ( query );
//            fis = new FileInputStream ( new File ( "dataupload\\" + selectedFile.getName () ) );
//
//            HSSFWorkbook my_xls_workbook = new HSSFWorkbook ( fis );
//            HSSFSheet my_worksheet = my_xls_workbook.getSheetAt ( 0 );
//
//            Iterator<Row> rowIterator = my_worksheet.iterator ();
//
//            while ( rowIterator.hasNext () ) {
//                Row row = rowIterator.next ();
//
//                try {
//                    //System.out.println ( row.getCell ( 0 ).getStringCellValue () );
//                     pst.setString ( 1,  row.getCell ( 0 ).getStringCellValue () );
//                } catch ( Exception e2 ) {
//                    writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
//                    try {
//
//                         pst.setInt ( 1, ( int ) row.getCell ( 0 ).getNumericCellValue () );
//
//                    } catch ( Exception e1 ) {
//                        writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
//                    }
//
//                }
//                pst.addBatch ();
//            }
//
//             int[] totalRecords = new int[ 2 ];
//            try {
//                totalRecords = pst.executeBatch ();
//            } catch ( BatchUpdateException e1 ) {
//                totalRecords = e1.getUpdateCounts ();
//                writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
//            }
//            System.out.println ( "Total record Inserted " + totalRecords.length );
//            fis.close ();
//            pst.close ();
//
//            ResultSet result1 = DB_Operations.getMasters ( "employee" );
//
//            if ( result1 != null ) {
//                jTable1.setModel ( buildTableModel ( result1 ) );
//                jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
//                jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
//            }
//            
//
//        } catch ( SQLException ex ) {
//            System.out.println ( "SQL  " + ex.getMessage () );
//            writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
//        } catch ( IOException ex ) {
//            System.out.println ( "IO  " + ex.getMessage () );
//            writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
//        }
//
//        try {
//            inputChannel = new FileInputStream ( selectedFile ).getChannel ();
//            outputChannel = new FileOutputStream ( new File ( dir , selectedFile.getName () ) ).getChannel ();
//            outputChannel.transferFrom ( inputChannel , 0 , inputChannel.size () );
//
//            inputChannel.close ();
//            outputChannel.close ();
//
//        } catch ( FileNotFoundException e1 ) {
//            writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
//        } catch ( IOException e2 ) {
//            writeErrorLogs (Employee_HR_Master.class.getSimpleName () , Employee_HR_Master.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
//        }


    }//GEN-LAST:event_jButton7MouseClicked

    private void jCheckBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox3ItemStateChanged
        //      TODO add your handling code here:
        //      if( ! jCheckBox4.isSelected () ){
        //          jCheckBox4.setSelected ( true );
        //      }
    }//GEN-LAST:event_jCheckBox3ItemStateChanged

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked
        // TODO add your handling code here:
    
        int selection = JOptionPane.showConfirmDialog ( null , "Deleting a record is not recommendedThe 'Delete' action may cause loss of any dependencies and may affect the transactional Data and Reports and make data inconsistent\nDeleting a plant will automatically delete all shifts for that plant\nPress 'No' to revert, Press 'Yes' to continue deleting", "Are you sure about deleting this record ?", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE);
        if ( selection == JOptionPane.YES_OPTION ) { 
            deleteMaster(  "employeedelete?EmployeePK="+selectedEmpRecordId    ) ;    
       // deleteMaster(  "userdelete?user_id="+selectedEmpRecordId    ) ;   
            String addEmpAPICall = "employees";
            String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

            if( !  result2.contains(  "not found"   )  ){
                if ( result2!= null ) {
                    jTable1.setModel ( buildTableModelfromJSON (result2 ) );
                    jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                    jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                }
            }
        }
    }//GEN-LAST:event_jButton8MouseClicked

//  created by harshali 17th april
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
       if ( selectedEmpRecordId == 0 )
        {
            JOptionPane.showMessageDialog ( null , "Please select a record first" );
        }
        else
        { 
            try
            {
                if(!user_id.contains("null")&& !UserName.contains("null"))
                  {
                    JSONObject object=new JSONObject();
                    object.accumulate("id", selectedEmpRecordId);
                    object.accumulate("Emp_name", selectedEmpRecordName); 
                    object.accumulate("Emp_no", selectedEmpRecordNo);
                    object.accumulate("Join_date", selectedEmpRecordJDate);
                    object.accumulate("Gender", selectedEmpRecordGender);
                    object.accumulate("Role", Role);
                    object.accumulate("Username", UserName);
                    object.accumulate("Userid", user_id);
                    System.out.println(object.toString());
                   
                   generate_qr(selectedEmpRecordName+"_"+selectedEmpRecordId+"_"+user_id,object.toString());           
                                
                  }
                  else
                  {
                      JOptionPane.showMessageDialog ( null , "No username and id found for this employee ..Please select another user" );
                        
                  }
             
                         
            }catch(Exception e)
            {
                e.getMessage();
                JOptionPane.showMessageDialog ( null , "Cannot save file 7" );
            }

        }

    }//GEN-LAST:event_jButton9ActionPerformed

    private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox8ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void dialogCreateUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dialogCreateUserMouseClicked
        CreateUserPanel process = new CreateUserPanel(selectedEmpRecordId,selectedEmpRecordName);
            process.setVisible(true);
    }//GEN-LAST:event_dialogCreateUserMouseClicked

    
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
    ProgressDialog progress = new ProgressDialog( null ) ;
    Thread getTableAndFilesContent = null ;
    public boolean showDialog(){
        getTableAndFilesContent = new Thread(){
            public void run(){
                //storeDataExel ();
                employeeMasterDataExport();
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
     
     public void employeeMasterDataExport()
     {
        progress.updateTitle ( "All Tool data is fetching please wait...");
        progress.updateMessage ("Getting all Tool data");
        String emp_nm = "",emp_no="", grade = "", dept = "", role = "", dob = "", gender = "", doj = "", desg = "", education = "", joining_skill = "",current_skill = "",authorised_skill = "",salary_band = "",nature_emp = "",local_addr = "",permant_addr = "";

                int emp_id;
                int l = 2;

                Map<String, Object[]> data;
                data = new TreeMap<String, Object[]>();

                //String[] columns = {"Die/Tool code","Die Purchase Cost", "Maintenance Date", "Maintenance By(Employee Name)", "Strokes at the time of Maintenance", "Activity", "Activity Cost", "Total_cost"};
                data.put("1", new Object[]{"Employee ID", "Employee Name", "Grade","Department","Role","Date of Birthday","Gender","Date of Joining","Designation","Education","Joining Skill level","Current Skill level","Authorised Skill","Salary Band","Nature of Employement","Local Address","Permanent Address"});

                String result2, addEmpAPICall;
                
                addEmpAPICall = "employees";                
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
                        //jComboBox1.addItem ( emp.get ( "die_tool_no" ).toString () );
                        emp_id = Integer.parseInt(emp.get("EmployeePK").toString());
                        emp.get("EMP_NAME").toString();
                        emp.get("EMP_NO").toString();
                        emp.get("JOIN_DT").toString();
                        emp.get("GENDER").toString();
                        emp.get("DEPARTMENTID").toString();
                        emp.get("ROLE").toString();
                        emp.get("ADD_LOCAL").toString();
                        
                        //data.put("" + l, new Object[]{die_tool, temp, temp, temp, temp, temp, temp});
                        
//                        l = l + 1;
//
//                        //all maintenance history logs by die id
//                        String EmpAPICall = "maintenance_history?DT_ID=" + die_id;
//                        String tab = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");
//
//                        if (!tab.contains("not found")) {
//                            HashMap<String, Object> map1 = new HashMap<String, Object>();
//                            JSONObject jObject1 = new JSONObject(tab);
//                            Iterator<?> keys1 = jObject1.keys();
//
//                            while (keys1.hasNext()) {
//                                String key1 = (String) keys1.next();
//                                Object value1 = jObject1.get(key1);
//                                map1.put(key1, value1);
//                            }
//
//                            JSONObject st1 = (JSONObject) map1.get("data");
//                            JSONArray records1 = st1.getJSONArray("records");
//
//                            JSONObject emp1 = null;
//
//                            for (int j = 0; j < records1.length(); j++) {
//
//                                emp1 = records1.getJSONObject(j);
//
////                                die_tool=emp.get("die_tool_no").toString();
////                                total_cost=emp.get("cost_maintenance").toString();
////                                emp1.get("MH_REF_UID").toString();
//                                String mh_id = emp1.get("MH_ID").toString();
//                                date1 = emp1.get("date_time").toString();
//                                employe = emp1.get("emp_id").toString();
//                                strokes_at = emp1.get("stroke_mt").toString();
//
//                                String load_emp, result_for_emp;
//                                load_emp = "employeeedit?EmployeePK=" + employe;
//                                result_for_emp = WebAPITester.prepareWebCall(load_emp, StaticValues.getHeader(), "");
//
//                                if (!result_for_emp.contains("not found")) {
//
//                                    JSONObject jsobje = new JSONObject(result_for_emp);
//                                    String page11 = jsobje.getJSONArray("data").toString();
//                                    JSONArray machinejsarray = new JSONArray(page11);
//
//                                    for (int p = 0; p < machinejsarray.length(); p++) {
//
//                                        JSONObject jsonobject = machinejsarray.getJSONObject(p);
//                                        emp_name = jsonobject.optString("EMP_NAME");
//                                    }
//                                }
//                                Date dt = new Date();
//                                String maintdate = "";
//                                SimpleDateFormat FormatYearTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                SimpleDateFormat FormatYr = new SimpleDateFormat("MMM dd, yyyy");
//                                try {
//                                    dt = FormatYearTime.parse(date1);
//                                    maintdate = FormatYr.format(dt);
//                                } catch (ParseException fg) {
//                                    fg.getMessage();
//                                }
//
//                                //data.put("" + l, new Object[]{temp, maintdate, emp_name, strokes_at, temp, temp, temp});
//                                dieExport.add(new DieExelData(temp,temp, maintdate, emp_name, strokes_at, temp, temp, temp));
//                                l = l + 1;
//
//                                String mhAPICall = "maint_history_logs?MH_REF_UID=" + mh_id;
//                                String tab1 = WebAPITester.prepareWebCall(mhAPICall, StaticValues.getHeader(), "");
//
//                                if (!tab1.contains("not found")) {
//                                    HashMap<String, Object> map2 = new HashMap<String, Object>();
//                                    JSONObject jObject2 = new JSONObject(tab1);
//                                    Iterator<?> keys2 = jObject2.keys();
//
//                                    while (keys2.hasNext()) {
//                                        String key2 = (String) keys2.next();
//                                        Object value2 = jObject2.get(key2);
//                                        map2.put(key2, value2);
//                                    }
//
//                                    JSONObject st2 = (JSONObject) map2.get("data");
//                                    JSONArray records2 = st2.getJSONArray("records");
//
//                                    JSONObject emp2 = null;
//
//                                    for (int k = 0; k < records2.length(); k++) {
//
//                                        emp2 = records2.getJSONObject(k);
//
//                                        //emp1.get("MH_REF_UID").toString();
//                                        activity_id = emp2.get("MH_ACT_ID").toString();
//                                        activity = emp2.get("MH_ACT_COMM").toString();
//                                        cost = emp2.get("MH_ACT_CST").toString();
//
//                                        //data.put("" + l, new Object[]{temp, temp, temp, temp, activity, cost, temp});
//                                        dieExport.add(new DieExelData(temp, temp,temp, temp, temp, activity, cost, temp));
//                                        l = l + 1;
//                                    }
//                                }
//                                
//                            }
//                        }
//                        //data.put("" + l, new Object[]{temp, temp, temp, temp, temp, temp, total_cost});
//                        dieExport.add(new DieExelData(temp, temp,temp, temp, temp, temp, temp, total_cost));
//                        l = l + 1;
//                    }
                }
                }
               hideDialog ();

     }
    
    
     
     
     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo1;
    private datechooser.beans.DateChooserCombo dateChooserCombo2;
    private datechooser.beans.DateChooserDialog dateChooserDialog1;
    private datechooser.beans.DateChooserDialog dateChooserDialog2;
    private datechooser.beans.DateChooserDialog dateChooserDialog3;
    private javax.swing.JButton dialogCreateUser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private com.qt.datapicker.TestDayPicker testDayPicker1;
    private com.qt.datapicker.TestDayPicker testDayPicker2;
    // End of variables declaration//GEN-END:variables

    byte[] image = null;

    class  CreateUserPanel extends JDialog
    {
        JPanel listPanel = null;
         
        public CreateUserPanel(int id,String name) {
           
            setLayout(null);

            btnsave = new javax.swing.JButton();
            btncancel = new javax.swing.JButton();
            
            btnsave.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            btnsave.setText("Create User");
            btnsave.setOpaque(false);
//            btnsave.addActionListener(new java.awt.event.ActionListener() {
//                public void actionPerformed(java.awt.event.ActionEvent evt) {
////                    btnsaveActionPerformed(evt);
//
//                }
//            });
//            add(btnsave);
            //btnsave.setBounds(280, 420, 180, 32);
         
            btncancel.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
            btncancel.setText("Discard & Close");
            btncancel.setOpaque(false);
//            btncancel.addActionListener(new java.awt.event.ActionListener() {
//                public void actionPerformed(java.awt.event.ActionEvent evt) {
////                    btncancelActionPerformed(evt);
//                 
//                }
//
//            });
            //add(btncancel);
           // btncancel.setBounds(140, 420, 140, 32);
            
            setBounds(350, 200, 650, 500);
            setVisible(true);
            setLayout(null);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setBackground(Color.WHITE);

//            listPanel = new JPanel();
//            listPanel.setBackground(Color.white);
//            listPanel.setBounds(0, 0, 650, 50);
//
//            listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
            //listPanel.setLayout (null );

//            jScrollPane1 = new JScrollPane(listPanel,
//                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//            jScrollPane1.setBounds(20, 70, 620, 350);
//            add(jScrollPane1);

            dialogCreateUserPanel labels = new dialogCreateUserPanel(id,name);
            labels.setBounds(0, 40, 600, 400);
            add(labels);
            
            //dispose();
        }

       
        
//            setCalculations();
//
//            //JOptionPane.showMessageDialog( null,  sb.toString ()   )  ;
       
        
        private javax.swing.JButton btnsave;
        private javax.swing.JButton btncancel;
    }
}
