/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples.reports;

import Model.ResourceDR;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.DepartmentDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.DepartmentMultiSelect;
import trixware.erp.prodezydesktop.Utilities.DeptSelectionPanel;
import trixware.erp.prodezydesktop.Utilities.PermissionMultiSelect;
import trixware.erp.prodezydesktop.Utilities.ResourceMultiSelect;
import trixware.erp.prodezydesktop.Utilities.RoleSelectionPanel;
import trixware.erp.prodezydesktop.Utilities.RolesMultiSelect;
import trixware.erp.prodezydesktop.examples.HomeScreen;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class UserRolePermissionMaster extends javax.swing.JPanel {

    
    ArrayList<DepartmentDR> departments = null;
    ArrayList<ResourceDR> resources = null;
    
    DepartmentMultiSelect  deptpanel = new DepartmentMultiSelect();
    RolesMultiSelect  rolepanel = new RolesMultiSelect() ;
    PermissionMultiSelect permpanel = new PermissionMultiSelect ();
    ResourceMultiSelect resourcepanel = new ResourceMultiSelect () ;
    
    public static DepartmentDR dept = null;
    public static ResourceDR res = null;
    
    
    String addEmpAPICall, result2 ;
    
    /**
     * Creates new form UserRolePermissionMaster
     */
    public UserRolePermissionMaster () {
        initComponents ();
        
        jButton3.addActionListener ( listenCloseAction );
        
        jComboBox1.setVisible ( false );
        jCheckBox8.setVisible ( false);
        jCheckBox9.setVisible ( false);
        jComboBox2.setVisible ( false);
        
        jCheckBox1.setVisible ( false);
        jCheckBox2.setVisible ( false);
        jCheckBox3.setVisible ( false);
        jCheckBox4.setVisible ( false);
        jCheckBox5.setVisible ( false);
        jCheckBox6.setVisible ( false);
        jCheckBox7.setVisible ( false);
        jCheckBox10.setVisible ( false);
        
        try{
        
            loadExistingRecords();
        }catch( Exception e){
                StaticValues.writer.writeExcel ( UserRolePermissionMaster.class.getSimpleName () , UserRolePermissionMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
        
//        String queryTwo = "SELECT * FROM DEPARTMENTS";
//        ResultSet rs = null ;
//        try {
//            rs = DB_Operations.executeSingle ( queryTwo );
//            // employees = new ArrayList<HashMap<String, String>> ();
//
//            departments = new ArrayList<DepartmentDR> ();
//            while ( rs.next () ) {
//                dept = new DepartmentDR ();
//
//                dept.DID = Integer.parseInt ( rs.getString ( 1 ) );
//                dept.DNAME = rs.getString ( 2 );
//                departments.add ( dept );
//                jComboBox1.addItem ( rs.getString ( 2 ) );
//            }
//
//        } catch ( Exception e ) {
//            System.out.println ( " Error 2 " + e.getMessage () );
//            StaticValues.writer.writeExcel ( UserRolePermissionMaster.class.getSimpleName () , UserRolePermissionMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
//        }   
        
        
    ///    queryTwo = "SELECT * FROM RESOURCE";
        
     //       rs = DB_Operations.executeSingle ( queryTwo );
            
//            resources = new ArrayList<ResourceDR> ();
//            while ( rs.next () ) {
//                res = new ResourceDR ();
//
//                res.RSID = Integer.parseInt ( rs.getString ( 1 ) );
//                res.RSNAME = rs.getString ( 2 );
//                resources.add ( res );
//                jComboBox2.addItem ( rs.getString ( 2 ) );
//            }
        
        try {
                try {
                    JScrollPane resourcePanel = resourcepanel.getResourcesMultiSelectList (   );
                    resourcePanel.setBounds ( 450 , 130 , 220 , 110 );
                    add ( resourcePanel );
                } catch ( Exception e ) {
                }
            
   //         rs.close();
             
        try {
               // rs = DB_Operations.getMasters ( "departments" );
               addEmpAPICall = "department";
                result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                JScrollPane deptPanel = deptpanel.getDeptMultiSelectList ( result2 ) ;
                deptPanel.setBounds ( 110 , 10 , 220 , 110 );
                add ( deptPanel );
                
            } catch ( Exception e ) {
               
            }


            try {
                //rs = DB_Operations.getMasters ( "roles" );
                addEmpAPICall = "userroles";
                result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                JScrollPane rolePanel = rolepanel.getRolesMultiSelectList ( result2 ) ;
                rolePanel.setBounds ( 110 , 130 , 220 , 110 );
                add ( rolePanel );
                
            } catch ( Exception e ) {
               
            }
            
            
            try {
                JScrollPane permPanel = permpanel.getPermissionMultiSelectList ( ) ;
                permPanel.setBounds ( 450 , 10 , 220 , 110 );
                add ( permPanel );
            } catch ( Exception e ) {
            }
            
            

        } catch ( Exception e ) {
            System.out.println ( " Error 2 " + e.getMessage () );
            StaticValues.writer.writeExcel ( UserRolePermissionMaster.class.getSimpleName () , UserRolePermissionMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }   

    }

       public void loadExistingRecords(){
    /*    
        String selectAllRecords = " select URP_ID, \n" +
        "        (select DNAME from DEPARTMENTS WHERE DID in ( DEPTID )) as 'Department' ,\n" +
        "        (select RSNAME from RESOURCE WHERE RSID in ( RESID )) as 'Resource' , (select RoleName from userRoles WHERE Role_ID in ( ROLE ))  AS 'Role' ,	\n" +
        "        CASE         WHEN `READ` = 1  THEN 'Yes'         ELSE 'No'    END AS 'Can Read' ,	CASE         WHEN `EDIT` = 1  THEN 'Yes'         ELSE 'No'    END AS 'Can Edit' ,	CASE \n" +
        "         WHEN `DISABLE` = 1  THEN 'Yes'         ELSE 'No'    END AS 'Can Disable' ,	CASE WHEN `EXPORT` = 1  THEN 'Yes' ELSE 'No'    END AS 'Can Export' ,	CASE \n" +
        "         WHEN `CREATENEW` = 1 THEN 'Yes'         ELSE 'No'    END AS 'Can Create' ,	CASE WHEN `IMPORT` = 1  THEN 'Yes'  ELSE 'No'    END AS 'Can Import', CASE WHEN `ACCESS_ON_MOBILE` = 1  THEN 'Yes'  ELSE 'No'    END AS 'Can Access Mobile' from USERROLEPERM  " ;

        ResultSet rs = DB_Operations.executeSingle ( selectAllRecords ) ;
        
        try{
            
                ResultSetMetaData metaData = rs.getMetaData ();
                // names of columns
                Vector<String> columnNames = new Vector<String> ();
                int columnCount = metaData.getColumnCount ();
                for ( int column = 1 ; column <= columnCount ; column ++ ) {
                    columnNames.add ( metaData.getColumnName ( column ) );
                }

                // data of the table
                Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
                while ( rs.next () ) {

                    Vector<Object> vector = new Vector<Object> ();
                    for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {
                        vector.add ( rs.getObject ( columnIndex ) );
                    }
                    data.add ( vector );
                }
                
                jTable1.setModel(  new DefaultTableModel ( data, columnNames  ) ) ;
                jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );        */
    
    
                String addEmpAPICall = "userroleperms";
                String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

                if ( result2 != null &&  ! result2.contains( "not found")) {

                    buildTableModelfromJSON ( result2  );
                       
                          
                }
                
                
                
      //  }catch(SQLException e){
       //     StaticValues.writer.writeExcel ( UserRolePermissionMaster.class.getSimpleName () , UserRolePermissionMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
      //  }
    }
    
       
          public void buildTableModelfromJSON ( String employeeJSONList ) {

        Vector<String> columnNames = new Vector<String> ();
        Vector<String> columnNames2 = new Vector<String> ();

        int columnCount = 0;
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        Vector<Vector<Object>> data2 = new Vector<Vector<Object>> ();

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
        columnNames.add ( "URP_ID" );
        columnNames.add ( "RESID" );
        columnNames.add ( "ROLE" );
        columnNames.add ( "READ" );
        columnNames.add ( "EDIT" );
        columnNames.add ( "DISABLE" );
        columnNames.add ( "EXPORT" );
        columnNames.add ( "CREATENEW" );
        columnNames.add ( "IMPORT" );
        columnNames.add ( "ACCESS_ON_MOBILE" );
       
         for ( int i = 0 ; i < records.length () ; i ++ ) {

            Vector<Object> vector =  new Vector<Object> ();
            for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {
                emp = records.getJSONObject ( i );
                vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
            }
            data.add ( vector );
        }

        jTable1.setModel ( new DefaultTableModel ( data , columnNames ) );
        jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
        jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
        

        //return new DefaultTableModel ( data2 , columnNames2 );
    }
       
       
       
    public void loadExistingRecords_old(){
        
//        String selectAllRecords = " select \n" +
//"        (select DNAME from DEPARTMENTS WHERE DID in ( DEPTID )) as 'Department' ,\n" +
//"        (select RSNAME from RESOURCE WHERE RSID in ( RESID )) as 'Resource' ,CASE         WHEN `ROLE` = 1  THEN 'Checker'         ELSE 'Maker'    END AS 'Role' ,	\n" +
//"        CASE         WHEN `READ` = 1  THEN 'Yes'         ELSE 'No'    END AS 'Can Read' ,	CASE         WHEN `EDIT` = 1  THEN 'Yes'         ELSE 'No'    END AS 'Can Edit' ,	CASE \n" +
//"         WHEN `DISABLE` = 1  THEN 'Yes'         ELSE 'No'    END AS 'Can Disable' ,	CASE WHEN `EXPORT` = 1  THEN 'Yes' ELSE 'No'    END AS 'Can Export' ,	CASE \n" +
//"         WHEN `CREATENEW` = 1 THEN 'Yes'         ELSE 'No'    END AS 'Can Create' ,	CASE WHEN `IMPORT` = 1  THEN 'Yes'  ELSE 'No'    END AS 'Can Import', CASE WHEN `ACCESS_ON_MOBILE` = 1  THEN 'Yes'  ELSE 'No'    END AS 'Can Access Mobile' from USERROLEPERM  " ;

String selectAllRecords = " select URP_ID, \n" +
"        (select DNAME from DEPARTMENTS WHERE DID in ( DEPTID )) as 'Department' ,\n" +
"        (select RSNAME from RESOURCE WHERE RSID in ( RESID )) as 'Resource' , (select RoleName from userRoles WHERE Role_ID in ( ROLE ))  AS 'Role' ,	\n" +
"        CASE         WHEN `READ` = 1  THEN 'Yes'         ELSE 'No'    END AS 'Can Read' ,	CASE         WHEN `EDIT` = 1  THEN 'Yes'         ELSE 'No'    END AS 'Can Edit' ,	CASE \n" +
"         WHEN `DISABLE` = 1  THEN 'Yes'         ELSE 'No'    END AS 'Can Disable' ,	CASE WHEN `EXPORT` = 1  THEN 'Yes' ELSE 'No'    END AS 'Can Export' ,	CASE \n" +
"         WHEN `CREATENEW` = 1 THEN 'Yes'         ELSE 'No'    END AS 'Can Create' ,	CASE WHEN `IMPORT` = 1  THEN 'Yes'  ELSE 'No'    END AS 'Can Import', CASE WHEN `ACCESS_ON_MOBILE` = 1  THEN 'Yes'  ELSE 'No'    END AS 'Can Access Mobile' from USERROLEPERM  " ;

        ResultSet rs = DB_Operations.executeSingle ( selectAllRecords ) ;
        
        try{
            
                ResultSetMetaData metaData = rs.getMetaData ();
                // names of columns
                Vector<String> columnNames = new Vector<String> ();
                int columnCount = metaData.getColumnCount ();
                for ( int column = 1 ; column <= columnCount ; column ++ ) {
                    columnNames.add ( metaData.getColumnName ( column ) );
                }

                // data of the table
                Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
                while ( rs.next () ) {

                    Vector<Object> vector = new Vector<Object> ();
                    for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {
                        vector.add ( rs.getObject ( columnIndex ) );
                    }
                    data.add ( vector );
                }
                
                jTable1.setModel(  new DefaultTableModel ( data, columnNames  ) ) ;
                    jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
            jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                
                
        }catch(SQLException e){
            StaticValues.writer.writeExcel ( UserRolePermissionMaster.class.getSimpleName () , UserRolePermissionMaster.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(903, 484));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Department");
        add(jLabel1);
        jLabel1.setBounds(10, 10, 90, 20);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Permissions");
        add(jLabel3);
        jLabel3.setBounds(360, 10, 80, 20);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jComboBox1);
        jComboBox1.setBounds(110, 10, 210, 30);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Resource");
        add(jLabel4);
        jLabel4.setBounds(360, 130, 80, 20);

        jComboBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        add(jComboBox2);
        jComboBox2.setBounds(480, 110, 210, 30);

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(10, 270, 880, 260);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Add");
        jButton1.setOpaque(false);
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
        jButton1.setBounds(770, 50, 90, 40);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Update");
        jButton2.setOpaque(false);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(770, 100, 90, 40);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Close");
        jButton3.setOpaque(false);
        add(jButton3);
        jButton3.setBounds(770, 150, 90, 40);

        jCheckBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox1.setText("Add");
        jCheckBox1.setOpaque(false);
        add(jCheckBox1);
        jCheckBox1.setBounds(790, 50, 50, 24);

        jCheckBox2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox2.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox2.setText("Read");
        jCheckBox2.setOpaque(false);
        add(jCheckBox2);
        jCheckBox2.setBounds(790, 70, 60, 24);

        jCheckBox3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox3.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox3.setText("Update");
        jCheckBox3.setOpaque(false);
        add(jCheckBox3);
        jCheckBox3.setBounds(790, 30, 66, 24);

        jCheckBox4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox4.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox4.setText("Disable");
        jCheckBox4.setOpaque(false);
        add(jCheckBox4);
        jCheckBox4.setBounds(790, 150, 66, 24);

        jCheckBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox5.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox5.setText("Export to file");
        jCheckBox5.setOpaque(false);
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });
        add(jCheckBox5);
        jCheckBox5.setBounds(790, 90, 94, 24);

        jCheckBox6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox6.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox6.setText("Import from file");
        jCheckBox6.setOpaque(false);
        add(jCheckBox6);
        jCheckBox6.setBounds(790, 110, 112, 24);

        jCheckBox7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox7.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox7.setText("Select All");
        jCheckBox7.setOpaque(false);
        jCheckBox7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBox7MouseClicked(evt);
            }
        });
        jCheckBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox7ActionPerformed(evt);
            }
        });
        add(jCheckBox7);
        jCheckBox7.setBounds(790, 10, 76, 24);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Role");
        add(jLabel2);
        jLabel2.setBounds(10, 130, 90, 20);

        jCheckBox8.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox8.setText("Checker");
        jCheckBox8.setOpaque(false);
        jCheckBox8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox8ItemStateChanged(evt);
            }
        });
        add(jCheckBox8);
        jCheckBox8.setBounds(110, 180, 76, 20);

        jCheckBox9.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox9.setText("Maker");
        jCheckBox9.setOpaque(false);
        add(jCheckBox9);
        jCheckBox9.setBounds(110, 180, 64, 20);

        jCheckBox10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jCheckBox10.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox10.setText("Access from mobile app");
        jCheckBox10.setOpaque(false);
        add(jCheckBox10);
        jCheckBox10.setBounds(790, 130, 170, 24);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void jCheckBox7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBox7MouseClicked
        // TODO add your handling code here:
        
        if(jCheckBox7.isSelected ()){
            jCheckBox1.setSelected ( true);
            jCheckBox2.setSelected ( true);
            jCheckBox3.setSelected ( true);
            jCheckBox4.setSelected ( true);
            jCheckBox5.setSelected ( true);
            jCheckBox6.setSelected ( true);
            jCheckBox10.setSelected ( true);
        }else{
            jCheckBox1.setSelected ( false);
            jCheckBox2.setSelected ( false);
            jCheckBox3.setSelected ( false);
            jCheckBox4.setSelected ( false);
            jCheckBox5.setSelected ( false);
            jCheckBox6.setSelected ( false);       
            jCheckBox10.setSelected ( false);
        }
    }//GEN-LAST:event_jCheckBox7MouseClicked

    private void jCheckBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox7ActionPerformed
    }//GEN-LAST:event_jCheckBox7ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        
        StringBuilder sb = new StringBuilder();
        sb.append (  "INSERT INTO USERROLEPERM ( `DEPTID`, `RESID`, `ROLE`, `READ`, `EDIT`, `DISABLE`, `EXPORT`, `CREATENEW`, `IMPORT`, `ACCESS_ON_MOBILE`  ) VALUES ( " );
        
        ResultSet rs = null ;
        
        
        //   read and process all inputs from respective controls
        //1.  Department   2. Resource  3. Role 4. Permissions
        
        int deptId=0, resId=0, readPermission=0, addPermission=0, updatePermission=0, disablePermission=0, importPermission=0, exportPermission=0, accessOnMobile =0 ;
        
    //    deptId =  departments.get( jComboBox1.getSelectedIndex () ).DID ;
    //    resId = resources.get( jComboBox2.getSelectedIndex () ).RSID ;
        
        String department = "" ;
        DeptSelectionPanel dept ;
        
        for(  int i=0; i<deptpanel.departmentsList.size (); i++  ){
                      
                if( deptpanel.departmentsList.get ( i ).getSelection () ){
                    department = department + deptpanel.departmentsList.get ( i ).getDeptId ()  + " -  " + deptpanel.departmentsList.get ( i ).getDepartmentName () +"," ; 
                }
        }
        if( department.length ()>0 ){
            department = department.substring ( 0,   department.length ()-1 ) ;
        }
        
        String role = "";
        RoleSelectionPanel roles ;
        for(  int i=0; i<rolepanel.rolesList.size (); i++  ){
            if( rolepanel.rolesList.get ( i ).getSelection () ){
                    role = role + rolepanel.rolesList.get ( i ).getDeptId ()  + " -  " + rolepanel.rolesList.get ( i ).getDepartmentName () +","; 
            }
        }
        if(  role.length ()>0 ){
            role = role.substring (  0,   role.length ()-1  ) ;
        }
    
        String permissions = "";
        for(  int i=0; i<permpanel.permissionsList.size (); i++  ){
            if( permpanel.permissionsList.get ( i ).isSelected ){
                    permissions = permissions + permpanel.permissionsList.get ( i ).getDeptId () + " -  " + permpanel.permissionsList.get ( i ).getDepartmentName () + ","; 
            }
        }
        if(  permissions.length ()>0 ){
            permissions = permissions.substring (  0,   permissions.length ()-1  ) ;
        }
        
        String resources = "";
        for(  int i=0; i<resourcepanel.resourcessList.size (); i++  ){
            if( resourcepanel.resourcessList.get ( i ).isSelected ){
                    resources = resources + resourcepanel.resourcessList.get ( i ).getDeptId () + " -  " + resourcepanel.resourcessList.get ( i ).getDepartmentName () + ","; 
            }
        }
        if(  resources.length ()>0 ){
            resources = resources.substring (  0,   resources.length ()-1  ) ;
        }
        
    //    JOptionPane.showMessageDialog ( null, role + "\n"+permissions+ "\n"+department+ "\n"+resources  );
       
    
//        if( jCheckBox1.isSelected ()  ){
//            readPermission = 1 ;
//        }
//
//        if( jCheckBox2.isSelected ()  ){
//            addPermission = 1 ;
//        }
//        
//        if( jCheckBox3.isSelected ()  ){
//            updatePermission = 1 ;
//        }
//                
//        if( jCheckBox4.isSelected ()  ){
//            disablePermission = 1 ;
//        }
//                        
//        if( jCheckBox5.isSelected ()  ){
//            importPermission = 1 ;
//        }
//                                
//        if( jCheckBox6.isSelected ()  ){
//            exportPermission = 1 ;
//        }
//        
//        if( jCheckBox10.isSelected ()  ){
//            accessOnMobile = 1 ;
//        }
        
        
        // for each selected departments from departments list
        //  for each selected roles from resources list
        //   for each selected resource 
        //    for each selected permission
        //          add current department, current role, current resource and current permission to the database 
        
        
        for ( int i = 0 ; i < deptpanel.departmentsList.size () ; i ++ ) {
            if ( deptpanel.departmentsList.get ( i ).getSelection () ) {
                sb.append ( deptpanel.departmentsList.get ( i ).getDeptId () + "," );

                for ( int j = 0 ; j < resourcepanel.resourcessList.size () ; j ++ ) {
                    if ( resourcepanel.resourcessList.get ( j ).getSelection () ) {
                        sb.append ( resourcepanel.resourcessList.get ( j ).getDeptId () + "," );

                        for ( int k = 0 ; k < rolepanel.rolesList.size () ; k ++ ) {
                            if ( rolepanel.rolesList.get ( k ).getSelection () ) {
                                sb.append ( rolepanel.rolesList.get ( k ).getDeptId () + "," );
                                int recordExist = 0;
                                System.out.println ( "select `URP_ID`  from USERROLEPERM where  `DEPTID` = '"+ deptpanel.departmentsList.get ( i ).getDeptId ()+"' AND `RESID`= '"+resourcepanel.resourcessList.get ( j ).getDeptId () +"' AND `ROLE`= '"+rolepanel.rolesList.get ( k ).getDeptId () +"'" );
                                try{
                                    ResultSet checkExistingRecord =  DB_Operations.executeSingle ( "select `URP_ID`  from USERROLEPERM where  `DEPTID` = '"+ deptpanel.departmentsList.get ( i ).getDeptId ()+"' AND `RESID`= '"+resourcepanel.resourcessList.get ( j ).getDeptId () +"' AND `ROLE`= '"+rolepanel.rolesList.get ( k ).getDeptId () +"'");
                                    while(  checkExistingRecord.next ()  ){
                                        recordExist = Integer.parseInt( checkExistingRecord.getString("URP_ID")) ;
                                    }
                                    checkExistingRecord.close();
                                    //checkExistingRecord = null ;
                                }catch(SQLException e){
                                    
                                }
                               
                                 String addQuery = "" ;
                                         
                                try{
                                   addQuery = "userrolepermadd?DEPTID="+URLEncoder.encode(  deptpanel.departmentsList.get ( i ).getDeptId ()+""  , "UTF-8")+"&RESID="+URLEncoder.encode(  resourcepanel.resourcessList.get ( j ).getDeptId ()+""  ,"UTF-8")+"&ROLE="+URLEncoder.encode( rolepanel.rolesList.get ( k ).getDeptId ()+""   , "UTF-8")  ;
                                }catch( UnsupportedEncodingException e ){
                                    
                                }
                                    
                                if(recordExist <= 0 ){
                                    for ( int l = 0 ; l < permpanel.permissionsList.size () ; l ++ ) {

                                        if ( permpanel.permissionsList.get ( l ).getSelection () ) {
                                            sb.append ( "1" + "," );
                                            
                                            try{
                                                if( l==0 ) { addQuery = addQuery + "&READ="+URLEncoder.encode(  "1"  , "UTF-8") ; }
                                                if( l==1 ) { addQuery = addQuery +"&EDIT="+URLEncoder.encode( "1"   , "UTF-8"); }
                                                if( l==2 ) { addQuery = addQuery +"&DISABLE="+URLEncoder.encode(  "1"  , "UTF-8") ;}
                                                if( l==3 ) { addQuery = addQuery +"&EXPORT="+URLEncoder.encode(  "1"  , "UTF-8"); }
                                                if( l==4 ) { addQuery = addQuery +"&CREATENEW="+URLEncoder.encode( "1"   , "UTF-8") ;}
                                                if( l==5 ) { addQuery = addQuery +"&IMPORT="+URLEncoder.encode(  "1"  , "UTF-8");  }
                                                if( l==6 ) {  addQuery = addQuery +"&ACCESS_ON_MOBILE="+URLEncoder.encode(  "1"  , "UTF-8"); } 
                                            
                                             }catch( UnsupportedEncodingException e ){
                                    
                                            }   
                                            
                                        } else {
                                            sb.append ( "0" + "," );
                                            
                                            try{
                                                if( l==0 ) { addQuery = addQuery + "&READ="+URLEncoder.encode(  "0"  , "UTF-8") ; }
                                                if( l==1 ) { addQuery = addQuery +"&EDIT="+URLEncoder.encode( "0"   , "UTF-8"); }
                                                if( l==2 ) { addQuery = addQuery +"&DISABLE="+URLEncoder.encode(  "0"  , "UTF-8") ;}
                                                if( l==3 ) { addQuery = addQuery +"&EXPORT="+URLEncoder.encode(  "0"  , "UTF-8"); }
                                                if( l==4 ) { addQuery = addQuery +"&CREATENEW="+URLEncoder.encode( "0"   , "UTF-8") ;}
                                                if( l==5 ) { addQuery = addQuery +"&IMPORT="+URLEncoder.encode(  "0"  , "UTF-8");  }
                                                if( l==6 ) {  addQuery = addQuery +"&ACCESS_ON_MOBILE="+URLEncoder.encode(  "0"  , "UTF-8"); } 
                                            
                                             }catch( UnsupportedEncodingException e ){
                                    
                                            }   
                                            
                                        }
                                    }
                                    String query = sb.toString ();
                                    query = query.substring ( 0 , query.length () - 1 );
                                    query = query + " ) ";
                                    //System.out.println ( "Add permission query  " + query );
                                
                                    String result2 =  WebAPITester.prepareWebCall ( addQuery, StaticValues.getHeader ()   , "");
                                    //JOptionPane.showMessageDialog ( null , result2);
                                   
                                    
                                    DB_Operations.executeSingleNR ( query ) ;

                                    //JOptionPane.showMessageDialog ( null , query );
                                    
                                    
                                }else{
                                    JOptionPane.showMessageDialog ( null, "One or more permissions already exists. Please uncheck existing permissions request & resubmit ");
                                }
                                sb = new StringBuilder ();
                                sb.append ( "INSERT INTO USERROLEPERM ( `DEPTID`, `RESID`, `ROLE`, `READ`, `EDIT`, `DISABLE`, `EXPORT`, `CREATENEW`, `IMPORT`, `ACCESS_ON_MOBILE`  ) VALUES ( " );
                                sb.append ( deptpanel.departmentsList.get ( i ).getDeptId () + "," );
                                sb.append ( resourcepanel.resourcessList.get ( j ).getDeptId () + "," );
                                    
                            }else if( k==rolepanel.rolesList.size () - 1 ){
 
                            }
                        }
                                           
                    }else if( j==resourcepanel.resourcessList.size () - 1 ){
                            
                     }
                    sb = new StringBuilder ();
                    sb.append ( "INSERT INTO USERROLEPERM ( `DEPTID`, `RESID`, `ROLE`, `READ`, `EDIT`, `DISABLE`, `EXPORT`, `CREATENEW`, `IMPORT`, `ACCESS_ON_MOBILE`  ) VALUES ( " );
                    sb.append ( deptpanel.departmentsList.get ( i ).getDeptId () + "," );
                         
                }
            }else if( i==deptpanel.departmentsList.size ()-1 ){

                
            }
            sb = new StringBuilder ();
            sb.append ( "INSERT INTO USERROLEPERM ( `DEPTID`, `RESID`, `ROLE`, `READ`, `EDIT`, `DISABLE`, `EXPORT`, `CREATENEW`, `IMPORT`, `ACCESS_ON_MOBILE`  ) VALUES ( " );
                  
        }
       
        
        
//        if(     jCheckBox8.isSelected () || jCheckBox9.isSelected ()      ){
//        
//                if(  jCheckBox8.isSelected ()  ){
//                    sb.append ( deptId +","+ resId +", 'Checker' , "+readPermission+","+ updatePermission+","+ disablePermission+","+ exportPermission+","+ addPermission+","+ importPermission+","+ accessOnMobile );
//                    sb.append ( " ) ");
//                    String query = sb.toString() ;
//                    System.out.println ( "Checker Query  "+query );
//
//                    DB_Operations.executeSingleNR ( query ) ;
//                    
//                }
//
//                sb = new StringBuilder();
//                sb.append (  "INSERT INTO USERROLEPERM ( DEPTID, RESID, ROLE, READ, EDIT, DISABLE, EXPORT, CREATENEW, IMPORT , ACCESS_ON_MOBILE ) VALUES ( " );
//
//               if(  jCheckBox9.isSelected ()  ){
//                    sb.append ( deptId +","+ resId +",'Maker',"+ readPermission+","+ updatePermission+","+ disablePermission+","+ exportPermission+","+ addPermission+","+ importPermission );
//                    sb.append ( " ) ");
//                    String query2 = sb.toString() ;
//                    System.out.println ( "Maker Query  "+query2 );
//
//                    DB_Operations.executeSingleNR ( query2 ) ;
//               }
//               
//               loadExistingRecords ();
//        }


            

       
    }//GEN-LAST:event_jButton1MouseClicked

    private void jCheckBox8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox8ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox8ItemStateChanged

    int selectedFGId ;
    
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel() ;
        int selectedRowIndex = jTable1.getSelectedRow ();
        selectedFGId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () ); 
        String dept, res, role ;
        dept =  model.getValueAt ( selectedRowIndex , 1 ).toString () ; 
        res = model.getValueAt ( selectedRowIndex , 2 ).toString () ; 
        role =  model.getValueAt ( selectedRowIndex , 3 ).toString () ; 
    
        for( int i=0; i < deptpanel.departmentsList.size (); i++  ){
                deptpanel.departmentsList.get ( i ).setSelection ( false );
        }
    
        for( int i=0; i < resourcepanel.resourcessList.size (); i++  ){
                resourcepanel.resourcessList.get ( i ).setSelection ( false );
        }
                
        for( int i=0; i < rolepanel.rolesList.size (); i++  ){
                rolepanel.rolesList.get ( i ).setSelection ( false );
        }
        
        for( int i=0, j=4; i < permpanel.permissionsList.size (); i++, j++  ){
            permpanel.permissionsList.get ( i ).setSelection ( false );
        }
          
        for( int i=0; i < deptpanel.departmentsList.size (); i++  ){
            if( deptpanel.departmentsList.get ( i ).getDepartmentName ().equals ( dept )){
                deptpanel.departmentsList.get ( i ).setSelection ( true );
            }
        }
    
        for( int i=0; i < resourcepanel.resourcessList.size (); i++  ){
            if( resourcepanel.resourcessList.get ( i ).getDepartmentName ().equals ( res )){
                resourcepanel.resourcessList.get ( i ).setSelection ( true );
            }
        }
                
        for( int i=0; i < rolepanel.rolesList.size (); i++  ){
            if( rolepanel.rolesList.get ( i ).getDepartmentName ().equals ( role )){
                rolepanel.rolesList.get ( i ).setSelection ( true );
            }
        }
        
        for( int i=0, j=4; i < permpanel.permissionsList.size (); i++, j++  ){
            System.out.println ( ""+model.getValueAt ( selectedRowIndex , j ).toString ()  );
            if(  model.getValueAt ( selectedRowIndex , j ).toString ().equals(  "Yes") ){
                permpanel.permissionsList.get ( i ).setSelection ( true );
            }
        }
        
        permpanel.revalidate ();
        permpanel.repaint (); 
         
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        
        
        //get URP id, and permission name
        
        String query = "update USERROLEPERM set ";
        StringBuilder sb = new StringBuilder();
        
        for ( int l = 0 ; l < permpanel.permissionsList.size () ; l ++ ) {
            String permission = "" ;
            switch( l ){
                case 0 :   permission  = "READ" ;                           break ;
                case 1 :   permission  = "EDIT" ;                           break ;
                case 2 :   permission  = "DISABLE" ;                        break ;
                case 3 :   permission  = "EXPORT" ;                         break ;
                case 4 :   permission  = "CREATENEW" ;                 break ;
                case 5 :   permission  = "IMPORT" ;                         break ;
                case 6 :   permission  = "ACCESS_ON_MOBILE" ;       break ;
                
                default :
            }
            
            if ( permpanel.permissionsList.get ( l ).getSelection () ) {
                sb.append ( permission + " =  1" + "," );
            } else {
                sb.append ( permission + " =  0"  + "," );
            }
        }
        query = query+sb.toString ();
        query = query.substring ( 0 , query.length () - 1 );
        query = query + " where URP_ID = "+ selectedFGId ;
        System.out.println ( "Update permission query  " + query );

        DB_Operations.executeSingleNR ( query ) ;
        
        loadExistingRecords ();
    }//GEN-LAST:event_jButton2MouseClicked

    ActionListener listenCloseAction = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
            HomeScreen.home.removeForms ();
        }
    };
    
    public void closeForm(){
        HomeScreen.home.removeForms ();
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
