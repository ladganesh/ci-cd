/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import trixware.erp.prodezydesktop.Model.SelectedObjects;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class UtilitiyMasters extends javax.swing.JPanel 
{

    static String resource = "", resourceType = "";

    static ArrayList<TestData> rmt1 = new ArrayList<> ();
    static TestData data = new TestData ();

    static int selectedRowIndex;
    //json array fetch

    static String EmpAPICall = null;
    static String result2 = null;
    static JSONObject responseObject = null;

    
    
    
    /**
     * Creates new form UtilitiyMasters
     */
    public UtilitiyMasters () 
    {
        initComponents ();
        //  this.setDefaultButton(jButton9);

        resourceType = "Masters";
        resource = "UtilitiyMasters";

        
//        try {
//            StaticValues.checkUserRolePermission ( resourceType , resourceType );
//        } catch ( Exception e ) {
//            StaticValues.writer.writeExcel ( resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }

    }

    public static void loadContent ( String val ) 
    {
        //       ResultSet result = null;
//        try {
//
//            SelectedObjects ob;
//
//            //System.out.println( val );
//            
//            
//            result = DB_Operations.getMasters ( val );
//
//            if ( result != null ) {
//                jTable2.setModel ( buildTableModel ( result , val ) );
//
//                jTable2.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
//                jTable2.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
//            }
////      
//  //          System.out.println("test");
//            result.close ();
//        } catch ( SQLException e ) {
//
//            try {
//                result.close ();
//            } catch ( SQLException ex ) {
//                StaticValues.writer.writeExcel ( UtilitiyMasters.class.getSimpleName () , UtilitiyMasters.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//            }
//
//            System.out.println ( "" + e.getMessage () );
//            StaticValues.writer.writeExcel ( UtilitiyMasters.class.getSimpleName () , UtilitiyMasters.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }


        if(  val.equals ( "rdm" )  ){
            jRadioButton1.setBounds( 10, 40, 260 , 30 );
            jRadioButton2.setBounds( 10, 75, 260 , 30 );
            jRadioButton3.setBounds( 10, 110, 260 , 30 );
            jTextField6.setBounds( 10,140,270,30 );
            jLabel26.setBounds( 0, 140, 10, 10);
            jButton9.setBounds( 10, 180, 80, 30);
            jButton10.setBounds( 100, 180, 80, 30);
            jButton1.setBounds( 190, 180, 80, 30);
            jScrollPane2.setBounds(10, 230, 270, 210);
         }else{
            jRadioButton1.setBounds( 0, 0, 0 , 0 );
            jRadioButton2.setBounds( 0, 0, 0 , 0 );
            jRadioButton3.setBounds( 0, 0, 0 , 0 );            
            jTextField6.setBounds( 10,40,270,30 );
            jLabel26.setBounds( 0, 40, 10, 10);
            jButton9.setBounds( 10, 80, 80, 30);
            jButton10.setBounds( 100, 80, 80, 30);
            jButton1.setBounds( 190, 80, 80, 30);
            jScrollPane2.setBounds(10, 130, 270, 210);
         }

        switch ( val ) 
        {

            case "grade":

                try {
                    int id;
                    String g_id;
                    String g_name;
                    EmpAPICall = "grade";
                    rmt1.clear ();
                    
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                    if(  !  result2.contains(  "not found"  )    ){
                    

                        responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject ( "data" ).toString ();

                        responseObject = new JSONObject ( page1 );

                        JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                        for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                            JSONObject jsonobject = Productjsarray.getJSONObject ( i );

                            //g_id = jsonobject.getString("GradeID");
                            //g_name = jsonobject.getString("GradeName");
                            //rmtst = jsonobject.getString("status");
                            //JOptionPane.showMessageDialog(null,"\n gradeid="+ g_id + "\n  gradename="+g_name  );
                            TestData data = new TestData ();

                            data.setId ( jsonobject.optInt ( "GradeID" ) );
                            data.setName ( jsonobject.optString ( "GradeName" ) );
    //                          id= data.jsonobject.optInt("GradeID");
    //                            HashMap<String, String> map = new HashMap<>();
    //                            map.put("ID", "" + id);
    //                            map.put("NAME", "" + name);

                            rmt1.add ( data );
                            //System.out.println(rmt1.add(map));
                            //JOptionPane.showMessageDialog(null, "id="+id+"name"+name );
                        }
                    
                    defaulttable ();
                    
                    }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "designation":
                try {
                    String g_id;
                    String g_name;
                    EmpAPICall = "designations";
                    rmt1.clear ();
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                    if(  !  result2.contains(  "not found"  )    ){

                        responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject ( "data" ).toString ();

                        responseObject = new JSONObject ( page1 );

                        JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                        for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                            JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                            TestData data = new TestData ();

                            data.setId ( jsonobject.optInt ( "DesgID" ) );
                            data.setName ( jsonobject.optString ( "DesgTitle" ) );
                            rmt1.add ( data );

                        }
                        defaulttable ();
                        
                    }
                } catch ( Exception e ) {
                    e.getMessage ();
                }

                break;

            case "salaryband":
                try {
                    String g_id;
                    String g_name;
                    EmpAPICall = "salaryband";
                    rmt1.clear ();
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                    if(  !  result2.contains(  "not found"  )    ){
                    
                        responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject ( "data" ).toString ();

                        responseObject = new JSONObject ( page1 );

                        JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                        for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                            JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                            TestData data = new TestData ();

                            data.setId ( jsonobject.optInt ( "SB_ID" ) );
                            data.setName ( jsonobject.optString ( "SB_RANGE" ) );
                            rmt1.add ( data );

                        }
                        defaulttable ();
                    }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "employeenature":
                showDataInParticularList ( "employeenature" , "NATURE" );
                break;

            case "uom":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "unitmeas";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                    if(  !  result2.contains(  "not found"  )    ){
                    
                        responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject ( "data" ).toString ();

                        responseObject = new JSONObject ( page1 );

                        JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                        for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                            JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                            TestData data = new TestData ();

                            data.setId ( jsonobject.optInt ( "UOM_ID" ) );
                            data.setName ( jsonobject.optString ( "UOM" ) );
                            rmt1.add ( data );

                        }
                        defaulttable ();
                        
                    }
                    
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "pack_size":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "packsize";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){

                        responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject ( "data" ).toString ();

                        responseObject = new JSONObject ( page1 );

                        JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                        for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                            JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                            TestData data = new TestData ();

                            data.setId ( jsonobject.optInt ( "PackSize_ID" ) );
                            data.setName ( jsonobject.optString ( "PackSize" ) );
                            rmt1.add ( data );

                        }
                        defaulttable ();
                        
                     }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "raw_material_type":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "rmt";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                   
                     if(  !  result2.contains(  "not found"  )    ){

                        responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject ( "data" ).toString ();

                        responseObject = new JSONObject ( page1 );

                        JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                        for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                            JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                            TestData data = new TestData ();

                            data.setId ( jsonobject.optInt ( "RMType_ID" ) );
                            data.setName ( jsonobject.optString ( "RawMaterial" ) );
                            rmt1.add ( data );

                        }
                        defaulttable ();
                        
                     }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "category":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "category";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){
                    
                        responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject ( "data" ).toString ();

                        responseObject = new JSONObject ( page1 );

                        JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                        for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                            JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                            TestData data = new TestData ();

                            data.setId ( jsonobject.optInt ( "Category_ID" ) );
                            data.setName ( jsonobject.optString ( "Category" ) );
                            rmt1.add ( data );

                        }
                        defaulttable ();
                        
                     }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "segment":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "segment";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){

                            responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                            //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                            String page1 = responseObject.getJSONObject ( "data" ).toString ();

                            responseObject = new JSONObject ( page1 );

                            JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                            for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                                JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                                TestData data = new TestData ();

                                data.setId ( jsonobject.optInt ( "Segment_ID" ) );
                                data.setName ( jsonobject.optString ( "Segment" ) );
                                rmt1.add ( data );

                            }
                            defaulttable ();

                     }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "industry":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "industry";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){

                            responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                            //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                            String page1 = responseObject.getJSONObject ( "data" ).toString ();

                            responseObject = new JSONObject ( page1 );

                            JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                            for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                                JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                                TestData data = new TestData ();

                                data.setId ( jsonobject.optInt ( "Industry_ID" ) );
                                data.setName ( jsonobject.optString ( "Industry" ) );
                                rmt1.add ( data );

                            }
                            defaulttable ();
                            
                     }
                     
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "rejreasons":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "rejreasons";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){

                            responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                            //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                            String page1 = responseObject.getJSONObject ( "data" ).toString ();

                            responseObject = new JSONObject ( page1 );

                            JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                            for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                                JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                                TestData data = new TestData ();

                                data.setId ( jsonobject.optInt ( "RR_ID" ) );
                                data.setName ( jsonobject.optString ( "RR_DESC" ) );
                                rmt1.add ( data );

                            }
                            defaulttable ();
                            
                     }
                     
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "rdm":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "rdm";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){

                            responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                            //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                            String page1 = responseObject.getJSONObject ( "data" ).toString ();

                            responseObject = new JSONObject ( page1 );

                            JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                            for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                                JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                                TestData data = new TestData ();

                                data.setId ( jsonobject.optInt ( "RDM_UID" ) );
                                data.setName ( jsonobject.optString ( "RDM_NAME" ) );
                                rmt1.add ( data );

                            }
                            defaulttable ();
                        
                     }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "department":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "department";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){

                            responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                            //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                            String page1 = responseObject.getJSONObject ( "data" ).toString ();

                            responseObject = new JSONObject ( page1 );

                            JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                            for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                                JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                                TestData data = new TestData ();

                                data.setId ( jsonobject.optInt ( "DID" ) );
                                data.setName ( jsonobject.optString ( "DNAME" ) );
                                rmt1.add ( data );

                            }
                            defaulttable ();
                            
                     }
                     
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "resources":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "resources";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                   
                     if(  !  result2.contains(  "not found"  )    ){
                    
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                    String page1 = responseObject.getJSONObject ( "data" ).toString ();

                    responseObject = new JSONObject ( page1 );

                    JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                    for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                        JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                        TestData data = new TestData ();

                        data.setId ( jsonobject.optInt ( "RSID" ) );
                        data.setName ( jsonobject.optString ( "RSNAME" ) );
                        rmt1.add ( data );

                    }
                    defaulttable ();
                     }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "userroles":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "userroles";
                    
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){
                    
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                    String page1 = responseObject.getJSONObject ( "data" ).toString ();

                    responseObject = new JSONObject ( page1 );

                    JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                    for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                        JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                        TestData data = new TestData ();

                        data.setId ( jsonobject.optInt ( "Role_ID" ) );
                        data.setName ( jsonobject.optString ( "RoleName" ) );
                        rmt1.add ( data );

                    }
                    //Collections.sort(rmt1);
                    defaulttable ();
                    
                     }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;
                
                
                case "timelossres":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "timeloss_reason";
                    
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){
                    
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                    String page1 = responseObject.getJSONObject ( "data" ).toString ();

                    responseObject = new JSONObject ( page1 );

                    JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                    for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                        JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                        TestData data = new TestData ();

                        data.setId ( jsonobject.optInt ( "TLR_ID" ) );
                        data.setName ( jsonobject.optString ( "TLR_DESC" ) );
                        rmt1.add ( data );

                    }
                    //Collections.sort(rmt1);
                    defaulttable ();
                    
                     }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;
                
                
            case "mainttypes":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "maintenance_type";
                    
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){
                    
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                    String page1 = responseObject.getJSONObject ( "data" ).toString ();

                    responseObject = new JSONObject ( page1 );

                    JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                    for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                        JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                        TestData data = new TestData ();

                        data.setId ( jsonobject.optInt ( "MT_ID" ) );
                        data.setName ( jsonobject.optString ( "activity" ) );
                        rmt1.add ( data );

                    }
                    //Collections.sort(rmt1);
                    defaulttable ();
                    
                     }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;
                
                 //************Edited by Harshali 06 May 2019
            case "qualityrawmaterial":
                try {
                    String g_id;
                    String g_name;
                    EmpAPICall = "rmqty_u_master";
                    rmt1.clear();
                    result2 = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                    if (!result2.contains("not found")) 
                    {

                        responseObject = (JSONObject) new JSONTokener(result2).nextValue();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject("data").toString();

                        responseObject = new JSONObject(page1);

                        JSONArray Productjsarray = new JSONArray(responseObject.getJSONArray("records").toString());

                        for (int i = 0; i < Productjsarray.length(); i++) {

                            JSONObject jsonobject = Productjsarray.getJSONObject(i);
                            TestData data = new TestData();

                            data.setId(jsonobject.optInt("param_id"));
                            data.setName(jsonobject.optString("param_name"));
                            rmt1.add(data);

                        }
                        defaulttable();

                    }
                } catch (Exception e) {
                    e.getMessage();
                }

                break;
            case "qualityprocess":
                try {
                    String g_id;
                    String g_name;
                    EmpAPICall = "procqty_u_master";
                    rmt1.clear();
                    result2 = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                    if (!result2.contains("not found")) 
                    {

                        responseObject = (JSONObject) new JSONTokener(result2).nextValue();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject("data").toString();

                        responseObject = new JSONObject(page1);

                        JSONArray Productjsarray = new JSONArray(responseObject.getJSONArray("records").toString());

                        for (int i = 0; i < Productjsarray.length(); i++) {

                            JSONObject jsonobject = Productjsarray.getJSONObject(i);
                            TestData data = new TestData();

                            data.setId(jsonobject.optInt("param_id"));
                            data.setName(jsonobject.optString("param_name"));
                            rmt1.add(data);

                        }
                        defaulttable();

                    }
                } catch (Exception e) {
                    e.getMessage();
                }

                break;
            case "qualityproduct":
                
                try {
                    String g_id;
                    String g_name;
                    EmpAPICall = "partqty_u_masters";
                    rmt1.clear();
                    result2 = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                    if (!result2.contains("not found")) {

                        responseObject = (JSONObject) new JSONTokener(result2).nextValue();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject("data").toString();

                        responseObject = new JSONObject(page1);

                        JSONArray Productjsarray = new JSONArray(responseObject.getJSONArray("records").toString());

                        for (int i = 0; i < Productjsarray.length(); i++) {

                            JSONObject jsonobject = Productjsarray.getJSONObject(i);
                            TestData data = new TestData();

                            data.setId(jsonobject.optInt("param_id"));
                            data.setName(jsonobject.optString("param_name"));
                            rmt1.add(data);

                        }
                        defaulttable();

                    }
                } catch (Exception e) {
                    e.getMessage();
                }

                break;
           case "machinestypes":
                try {
                    String g_id;
                    String g_name;
                    EmpAPICall = "machinestypes";
                    rmt1.clear();
                    result2 = WebAPITester.prepareWebCall(EmpAPICall, StaticValues.getHeader(), "");

                    if (!result2.contains("not found")) {

                        responseObject = (JSONObject) new JSONTokener(result2).nextValue();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject("data").toString();

                        responseObject = new JSONObject(page1);

                        JSONArray Productjsarray = new JSONArray(responseObject.getJSONArray("records").toString());

                        for (int i = 0; i < Productjsarray.length(); i++) {

                            JSONObject jsonobject = Productjsarray.getJSONObject(i);
                            TestData data = new TestData();

                            data.setId(jsonobject.optInt("MC_TYPE_ID"));
                            data.setName(jsonobject.optString("MC_TYPE"));
                            rmt1.add(data);

                        }
                        defaulttable();

                    }
                } catch (Exception e) {
                    e.getMessage();
                }

                break;

                 //************Edited by Harshali 06 May 2019
        }

    }

    static  public void defaulttable () {

        //Collections.sort ( rmt1 , new CompId () );
        //System.out.println("BY ID");
        //for ( TestData p : rmt1 ) {
            //System.out.println(p.toString());
        //}
        DefaultTableModel model = new DefaultTableModel ();
        try {
            Vector<String> columns = new Vector<String> ();
            columns.add ( "Ref_ID" ); 
            columns.add ( "Master Name" );
            
            Vector<Vector<String>> row = new Vector<Vector<String>> ();
            // ArrayList<TestData> list=new ArrayList();
            //for( TestData detail : rmt1) 
            for ( int i = 0 ; i < rmt1.size () ; i ++ ) {

                //ArrayList<TestData> arr=new ArrayList<>();
//                    arr.addrow(detail.getId());
                Vector<String> value = new Vector<String> ();
                value.add ( rmt1.get ( i ).getId ()+"" );
                value.add ( rmt1.get ( i ).getName () );
                row.add ( value );
            }
           
            jTable2.repaint ();
            jTable2.revalidate ();
            jTable2.setModel ( new DefaultTableModel ( row , columns ) );
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(70);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(300);
           // jTable2.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
            //jTable2.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );

        } catch ( Exception e ) {
            e.getMessage ();
        }

        //jTable2 = new JTable( model );
        //JScrollPane scrollPane = new JScrollPane( jTable2 );    
    }

    public void deleteAllRows ( DefaultTableModel model ) 
    {
        for ( int i = model.getRowCount () - 1 ; i >= 0 ; i -- ) {
            model.removeRow ( i );
        }
    }

    public static DefaultTableModel buildTableModel ( ResultSet rs , String column_name )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();

        columnNames.add ( metaData.getColumnName ( 1 ) );

        switch ( column_name ) 
        {
            case "grade":
                columnNames.add ( "Grades" );
                break;

            case "designation":
                columnNames.add ( "Designations" );
                break;
            case "salaryband":
                columnNames.add ( "Salary Band" );
                break;
            case "employeenature":
                columnNames.add ( "Nature of Employement" );
                break;
            case "uom":
                columnNames.add ( "Unit of Measurements" );
                break;

            case "pack_size":
                columnNames.add ( "Pack Size" );
                break;

            case "raw_material_type":
                columnNames.add ( "Raw Material Type" );
                break;
            case "category":
                columnNames.add ( "Category" );
                break;
            case "segment":
                columnNames.add ( "Segment" );
                break;
            case "industry":
                columnNames.add ( "Industry" );
                break;

            case "rejectionReason":
                columnNames.add ( "RejectionReasons" );
                break;

            case "documentType":
                columnNames.add ( "DocumentType" );
                break;

            case "departments":
                columnNames.add ( "Departments" );
                break;

            case "resources":
                columnNames.add ( "Resource" );
                break;

            case "roles":
                columnNames.add ( "User Roles" );
                break;
                
            case "qualityrawmaterial":
                columnNames.add ( "Quality Raw material" );
                break;
            case "qualityprocess":
                columnNames.add ( "Quality Process" );
                break;
            case "qualityproduct":
                columnNames.add ( "Quality Product" );
                break;

        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        while ( rs.next () ) {
            Vector<Object> vector = new Vector<Object> ();

            vector.add ( rs.getObject ( 1 ) );
            vector.add ( rs.getObject ( 2 ) );

            //     System.out.println(rs.getObject(2) );
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTextField9 = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jList9 = new javax.swing.JList<>();
        jTextField6 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();

        jButton15.setText("Add");

        jButton16.setText("Edit");

        jList9.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "No Items Found" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane9.setViewportView(jList9);

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(286, 356));
        setLayout(null);

        jTextField6.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        add(jTextField6);
        jTextField6.setBounds(10, 140, 270, 30);

        jButton9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton9.setText("Add");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        add(jButton9);
        jButton9.setBounds(10, 180, 80, 30);

        jButton10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton10.setText("Edit");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        add(jButton10);
        jButton10.setBounds(100, 180, 80, 30);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Grades", "Designations", "Salary Band", "Unit of Measurement", "Pack Size", "Raw Material Type", "Category", "Segment", "Industry", "Rejection Reason", "Document Types", "Departments", "Resources", "User Roles",  "TimeLoss Reasons", "Maintenance Types","Machines Types" ,"Quality Raw Material","Quality Process","Quality Product"}));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        add(jComboBox1);
        jComboBox1.setBounds(10, 0, 270, 30);

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.setRowHeight(20);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        add(jScrollPane2);
        jScrollPane2.setBounds(10, 230, 270, 210);

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 0, 51));
        jLabel26.setText("*");
        add(jLabel26);
        jLabel26.setBounds(0, 140, 20, 10);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Delete");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(190, 180, 90, 32);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jRadioButton1.setText("Document For Parts / Item / Finished Good");
        jRadioButton1.setOpaque(false);
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        add(jRadioButton1);
        jRadioButton1.setBounds(10, 40, 259, 28);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jRadioButton2.setText("Document for Raw Material");
        jRadioButton2.setOpaque(false);
        add(jRadioButton2);
        jRadioButton2.setBounds(10, 70, 250, 28);

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jRadioButton3.setText("Others");
        jRadioButton3.setOpaque(false);
        add(jRadioButton3);
        jRadioButton3.setBounds(10, 100, 67, 28);
    }// </editor-fold>                        

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        addTODB ();
    }                                        

    public void addTODB () {

        String selectedMaster = jComboBox1.getSelectedItem ().toString ();

        String value = jTextField6.getText ().trim ();
        String res = "";

        
        
        String addEmpAPICall = null;
        String result2 = null;
        JSONObject responseObject = null;

        if (  ! value.equalsIgnoreCase ( "" ) ) {

            switch ( selectedMaster ) {

                case "Grades":
                    // res = DB_Operations.insertIntoGradesMaster ( value );
                    //showDataInParticularList ( "grade" , "GradeName" );

                    try{
                        addEmpAPICall = "gradeadd?GradeName=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "grade" , "GradeName" );
                    break;

                case "Designations":
                    //res = DB_Operations.insertIntoDesignationsMaster ( value );
                    //showDataInParticularList ( "designation" , "DesgTitle" );

                    try{
                        addEmpAPICall = "designationsadd?DesgTitle=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "designation" , "DesgTitle" );
                    break;

                case "Salary Band":
                    //res = DB_Operations.insertIntoSalaryMaster ( value );
                    //showDataInParticularList ( "salaryband" , "SB_RANGE" );

                    try{
                        addEmpAPICall = "salarybandadd?SB_RANGE=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                   
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "salaryband" , "SB_RANGE" );

                    break;

                case "Nature of Employment":
                    //res = DB_Operations.insertIntoEmpNatureMaster ( value );
                    //showDataInParticularList ( "employeenature" , "NATURE" );

                    showDataInParticularList ( "employeenature" , "NATURE" );

                    break;

                case "Unit of Measurement":
                    //res = DB_Operations.insertIntoUOMMaster ( value );
                    //showDataInParticularList ( "uom" , "UOM" );

                    try{
                        addEmpAPICall = "unitmeasadd?UOM=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                   
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "uom" , "UOM" );

                    break;

                case "Pack Size":
                    //res = DB_Operations.insertUtilitiesMaster ( "PackSize" , value );
                    //showDataInParticularList ( "pack_size" , "PackSize" );

                    try{
                        addEmpAPICall = "packsizeadd?PackSize=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                   
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "pack_size" , "PackSize" );

                    break;

                case "Raw Material Type":
                    //res = DB_Operations.insertUtilitiesMaster ( "RawMaterial" , value );
                    //showDataInParticularList ( "raw_material_type" , "RawMaterialType" );

                   try{
                        addEmpAPICall = "rmtadd?RawMaterial=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "raw_material_type" , "RawMaterial" );

                    break;

                case "Category":
                    //res = DB_Operations.insertUtilitiesMaster ( "Category" , value );
                    //showDataInParticularList ( "category" , "Category" );

                    try{
                        addEmpAPICall = "categoryadd?Category=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "category" , "Category" );

                    break;

                case "Segment":
                    // res = DB_Operations.insertUtilitiesMaster ( "Segment" , value );
                    //showDataInParticularList ( "segment" , "Segment" );

                    try{
                        addEmpAPICall = "segmentadd?Segment="  + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
               
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "segment" , "Segment" );

                    break;

                case "Industry":
                    //res = DB_Operations.insertUtilitiesMaster ( "Industry" , value );
                    //showDataInParticularList ( "industry" , "Industry" );

                     try{
                        addEmpAPICall = "industryadd?Industry=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "industry" , "Industry" );

                    break;

                case "Rejection Reason":
                    //res = DB_Operations.insertUtilitiesMaster ( "RejectionReason" , value );
                    //showDataInParticularList ( "rejectionReason" , "RejectionReasons" );

                     try{
                        addEmpAPICall = "rejreasonsadd?RR_DESC=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "rejreasons" , "RejectionReasons" );

                    break;

                case "Document Types":
                    //res = DB_Operations.insertUtilitiesMaster ( "DocumentType" , value );
                    //showDataInParticularList ( "documentType" , "DocumentType" );
   
                        if(  jRadioButton1.isSelected ()      ){
                            if( ! value.contains ( "Part - ")  )
                                value = "Part - "+ value ;
                        }else if( jRadioButton2.isSelected ()       ){
                            if( ! value.contains ( "RM - ")  )
                            value = "RM - "+ value ;
                        }else if( jRadioButton3.isSelected ()       ){

                        }

                     try{
                        addEmpAPICall = "rdmadd?RDM_NAME=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "rdm" , "DocumentType" );

                    break;

                case "Departments":
                    //res = DB_Operations.insertUtilitiesMaster ( "Departments" , value );
                    // showDataInParticularList ( "departments" , "Departments" );

                    try{
                        addEmpAPICall = "departmentadd?DNAME=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "department" , "Departments" );

                    break;

                case "Resources":
                    //res = DB_Operations.insertUtilitiesMaster ( "Resources" , value );
                    //showDataInParticularList ( "resources" , "Resources" );

                    try{
                        addEmpAPICall = "resourceadd?RSNAME=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "resources" , "Resources" );

                    break;

                case "User Roles":
                    //res = DB_Operations.insertUtilitiesMaster ( "User Roles" , value );
                    //showDataInParticularList ( "roles" , "User Roles" );

                    try{
                        addEmpAPICall = "userrolesadd?RoleName="  + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "userroles" , "User Roles" );

                    break;
                    
                    case "TimeLoss Reasons":
                    //res = DB_Operations.insertUtilitiesMaster ( "User Roles" , value );
                    //showDataInParticularList ( "roles" , "User Roles" );

                    try{
                        addEmpAPICall = "timeloss_reason_add?TLR_DESC="  + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "timelossres" , "TimeLoss Reasons" );

                    break;
                    
                    
                    case "Maintenance Types":
                    //res = DB_Operations.insertUtilitiesMaster ( "User Roles" , value );
                    //showDataInParticularList ( "roles" , "User Roles" );

                    try{
                        addEmpAPICall = "maintenance_type_add?activity="  + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "mainttypes" , "Maintenance Types" );

                    break;
                    
                    case "Machines Types":
                    //res = DB_Operations.insertUtilitiesMaster ( "User Roles" , value );
                    //showDataInParticularList ( "roles" , "User Roles" );

                    try{
                        addEmpAPICall = "machinestype_add?MC_TYPE="  + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "machinestypes" , "Machines Types" );

                    break;
                    
                case "Quality Raw Material":
                   try{
                        addEmpAPICall = "rmqty_u_master_add?param_name="  + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){}
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList("qualityrawmaterial", "Quality Raw Material");
                    break;
                case "Quality Process":
                   try{
                        addEmpAPICall = "procqty_u_master_add?param_name="  + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){}
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }
                    
                    showDataInParticularList("qualityprocess", "Quality Process");
                    break;
                case "Quality Product":
                 try{
                        addEmpAPICall = "partqty_u_master_add?param_name="  + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){}
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList("qualityproduct", "Quality Product");
                    break;
                    
                    //*******---------------
            }

        } else {
            JOptionPane.showMessageDialog ( null , "Please enter valid text" );
        }

        jTextField6.setText ( "" );
        jTextField6.requestFocus ();
    }

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:

        String selectedMaster = jComboBox1.getSelectedItem ().toString ();
        String grade;
        String value = jTextField6.getText ().trim ();
        grade = jTextField6.getText ();
        String res = "";
        String updateEmpAPICall = "";
        String result3 = "";
        JSONObject responseObject = null;

        switch ( selectedMaster ) {

            case "Grades":

                //res = DB_Operations.updateIntoGradesMaster ( grade , selectedRecordId );
                //showDataInParticularList ( "grade" , "GradeName" );
                try{
                        updateEmpAPICall = "gradeupdate?GradeID=" + selectedRecordId + "&GradeName=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "grade" , "GradeName" );

                break;

            case "Designations":
                //res = DB_Operations.updateIntoDesignationsMaster ( grade , selectedRecordId );
                // showDataInParticularList ( "designation" , "DesgTitle" );

                try{
                     
                        updateEmpAPICall = "designationsupdate?DesgID=" + selectedRecordId + "&DesgTitle=" + URLEncoder.encode( value, "UTF-8");
                  }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "designation" , "DesgTitle" );

                break;

            case "Salary Band":
                //res = DB_Operations.updateIntoSalaryMaster ( grade , selectedRecordId );
                //showDataInParticularList ( "salaryband" , "SB_RANGE" );
                try{
                updateEmpAPICall = "salarybandupdate?SB_ID=" + selectedRecordId + "&SB_RANGE=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "salaryband" , "SB_RANGE" );

                break;

            case "Nature of Employment":
                res = DB_Operations.updateIntoEmpNatureMaster ( grade , selectedRecordId );
                showDataInParticularList ( "employeenature" , "NATURE" );

                break;

            case "Unit of Measurement":
                //res = DB_Operations.updateIntoUOMMaster ( grade , selectedRecordId );
                //showDataInParticularList ( "uom" , "UOM" );
                try{
                updateEmpAPICall = "unitmeasupdate?UOM_ID=" + selectedRecordId + "&UOM=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "uom" , "UOM" );

                break;

            case "Pack Size":

                //res = DB_Operations.updateUtilitiesMaster ( "PackSize" , grade , selectedRecordId );
                //showDataInParticularList ( "pack_size" , "PackSize" );
                try{
                updateEmpAPICall = "packsizeupdate?PackSize_ID=" + selectedRecordId + "&PackSize=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "pack_size" , "PackSize" );

                break;

            case "Raw Material Type":
                //res = DB_Operations.updateUtilitiesMaster ( "RawMaterial" , grade , selectedRecordId );

                //   System.out.println(   grade + "  " + selectedRecordId );
                //showDataInParticularList ( "raw_material_type" , "RawMaterial" );
                try{
                updateEmpAPICall = "rmtupdate?RMType_ID=" + selectedRecordId + "&RawMaterial=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "raw_material_type" , "RawMaterial" );

                break;

            case "Category":
                //res = DB_Operations.updateUtilitiesMaster ( "Category" , grade , selectedRecordId );
                //showDataInParticularList ( "category" , "Category" );
try{
                updateEmpAPICall = "categoryupdate?Category_ID=" + selectedRecordId + "&Category=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "category" , "Category" );

                break;

            case "Segment":
                //res = DB_Operations.updateUtilitiesMaster ( "Segment" , grade , selectedRecordId );
                //showDataInParticularList ( "segment" , "Segment" );
try{
                updateEmpAPICall = "segmentupdate?Segment_ID=" + selectedRecordId + "&Segment=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "segment" , "Segment" );

                break;

            case "Industry":
                //res = DB_Operations.updateUtilitiesMaster ( "Industry" , grade , selectedRecordId );
                //showDataInParticularList ( "industry" , "Industry" );
try{
                updateEmpAPICall = "industryupdate?Industry_ID=" + selectedRecordId + "&Industry=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "industry" , "Industry" );

                break;

            case "Departments":
                //res = DB_Operations.updateUtilitiesMaster ( "Departments" , grade , selectedRecordId );
                //showDataInParticularList ( "departments" , "Departments" );
try{
                updateEmpAPICall = "departmentupdate?DID=" + selectedRecordId + "&DNAME=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "department" , "Departments" );

                break;
                
                case "Machines Types":
                //res = DB_Operations.updateUtilitiesMaster ( "Departments" , grade , selectedRecordId );
                //showDataInParticularList ( "departments" , "Departments" );
            try{
                updateEmpAPICall = "machinestype_update?MC_TYPE_ID=" + selectedRecordId + "&MC_TYPE=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){}
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "machinestypes" , "Machines Types" );

                break;

            case "Resources":
                //res = DB_Operations.updateUtilitiesMaster ( "Resources" , grade , selectedRecordId );
                //showDataInParticularList ( "resources" , "Resources" );
try{
                updateEmpAPICall = "resourceupdate?RSID=" + selectedRecordId + "&RSNAME=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "resources" , "Resources" );

                break;

            case "User Roles":
                //res = DB_Operations.updateUtilitiesMaster ( "User Roles" , grade , selectedRecordId );
                //showDataInParticularList ( "roles" , "User Roles" );
try{
                updateEmpAPICall = "userrolesupdate?Role_ID=" + selectedRecordId + "&RoleName=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "userroles" , "User Roles" );

                break;

            case "Rejection Reason":
try{
                updateEmpAPICall = "rejreasonsupdate?RR_ID=" + selectedRecordId + "&RR_DESC=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "rejreasons" , "RejectionReasons" );
                break;

                case "Document Types":
                
                try{
                    updateEmpAPICall = "rdmupdate?RDM_UID=" + selectedRecordId + "&RDM_NAME=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "rdm" , "DocumentType" );
                break;
                
                case "TimeLoss Reasons":
                
                try{
                    updateEmpAPICall = "timeloss_reason_update?TLR_ID=" + selectedRecordId + "&TLR_DESC=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        System.out.println ( ""+e.getMessage () );
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "timelossres" , "TimeLoss Reasons" );
                break;
          //                        -------------------------------------------    RESERVED FOR MAINTENANCE TYPES  ------------------------------------------------------------------      
                
                case "Maintenance Types" :
                try{
                    updateEmpAPICall = "maintenance_type_update?MT_ID=" + selectedRecordId + "&activity=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        System.out.println ( ""+e.getMessage () );
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "mainttypes" , "Maintenance Types" );
                break;
           ///  -----------------------------------------------------------   RESERVED FOR MAINTENANCE TYPES    --------------------------------------------------------------------     
                
                case "Quality Raw Material":
                
                try{
                    updateEmpAPICall = "rmqty_u_master_update?param_id=" + selectedRecordId + "&param_name=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "qualityrawmaterial" , "Quality Raw Material" );
                break;
            case "Quality Process":
                
                try{
                    updateEmpAPICall = "procqty_u_master_update?param_id=" + selectedRecordId + "&param_name=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "qualityprocess" , "Quality Process" );
                break;
            case "Quality Product":
                 
                try{
                    updateEmpAPICall = "partqty_u_master_update?param_id=" + selectedRecordId + "&param_name=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "qualityproduct" , "Quality Product" );
                break;
                
                 //************Edited by Harshali 06 May 2019

        }

        jTextField6.setText ( "" );
    }                                         

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {                                            
        // TODO add your handling code here:

        String selectedMaster = jComboBox1.getSelectedItem ().toString ();
        String res = "";

         jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
    
    
         if(  selectedMaster.equals ( "Document Types" )  ){
            jRadioButton1.setBounds( 10, 40, 260 , 30 );
            jRadioButton2.setBounds( 10, 75, 260 , 30 );
            jRadioButton3.setBounds( 10, 110, 260 , 30 );
            jTextField6.setBounds( 10,140,270,30 );
            jLabel26.setBounds( 0, 140, 10, 10);
            jButton9.setBounds( 10, 180, 80, 30);
            jButton10.setBounds( 100, 180, 80, 30);
            jButton1.setBounds( 190, 180, 80, 30);
            jScrollPane2.setBounds(10, 230, 270, 210);
         }else{
            jRadioButton1.setBounds( 0, 0, 0 , 0 );
            jRadioButton2.setBounds( 0, 0, 0 , 0 );
            jRadioButton3.setBounds( 0, 0, 0 , 0 );            
            jTextField6.setBounds( 10,40,270,30 );
            jLabel26.setBounds( 0, 40, 10, 10);
            jButton9.setBounds( 10, 80, 80, 30);
            jButton10.setBounds( 100, 80, 80, 30);
            jButton1.setBounds( 190, 80, 80, 30);
            jScrollPane2.setBounds(10, 130, 270, 210);
         }
         
         
        switch ( selectedMaster ) 
        {

            case "Grades":

                showDataInParticularList ( "grade" , "GradeName" );
                break;

            case "Designations":
                showDataInParticularList ( "designation" , "DesgTitle" );
                break;

            case "Salary Band":
                showDataInParticularList ( "salaryband" , "SB_RANGE" );
                break;

            case "Nature of Employment":
                showDataInParticularList ( "employeenature" , "NATURE" );
                break;

            case "Unit of Measurement":
                showDataInParticularList ( "uom" , "UOM" );
                break;

            case "Pack Size":
                showDataInParticularList ( "pack_size" , "PackSize" );
                break;

            case "Raw Material Type":
                showDataInParticularList ( "raw_material_type" , "RawMaterial" );
                break;

            case "Category":
                showDataInParticularList ( "category" , "Category" );
                break;

            case "Segment":
                showDataInParticularList ( "segment" , "Segment" );
                break;

            case "Industry":
                showDataInParticularList ( "industry" , "Industry" );
                break;

            case "Rejection Reason":
                showDataInParticularList ( "rejreasons" , "RejectionReasons" );
                break;

            case "Document Types":
                showDataInParticularList ( "rdm" , "DocumentType" );
                break;

            case "Departments":
                showDataInParticularList ( "department" , "Departments" );
                break;

            case "Resources":
                showDataInParticularList ( "resources" , "Resources" );
                break;

            case "User Roles":
                showDataInParticularList ( "userroles" , "User Roles" );
                break;
                
            case "TimeLoss Reasons":
                showDataInParticularList ( "timelossres" , "TimeLoss Reasons" );
                break;
                
            case "Maintenance Types":
                showDataInParticularList("mainttypes", "Maintenance Types");
                break;
            case "Quality Raw Material":
                showDataInParticularList("qualityrawmaterial", "Quality Raw Material");
                break;
            case "Quality Process":
                showDataInParticularList("qualityprocess", "Quality Process");
                break;
            case "Quality Product":
                showDataInParticularList("qualityproduct", "Quality Product");
                break;
            case "Machines Types":
                showDataInParticularList("machinestypes","Machines Types");
                break;

        }      
        
    }                                           

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
        DefaultTableModel model = ( DefaultTableModel ) jTable2.getModel ();
        // get the selected row index
        selectedRowIndex = jTable2.getSelectedRow ();
        jTextField6.setText ( model.getValueAt ( selectedRowIndex , 1 ).toString () );
        //selectedRowIndex += 1;
        selectedRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );
        System.out.println ( ""+selectedRecordId );
        
    }                                    

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    
    public static  String retreiveSuccessMessage( String json){
        String id = "Not able to fetch the response for your request. Please refresh you master form and try again";
        JSONObject jObject = new JSONObject ( json );
        id = jObject.getString("118" );
        return id ;
    }
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
        
         String selectedMaster = jComboBox1.getSelectedItem ().toString ();
        String grade;
        String value = jTextField6.getText ().trim ();
        grade = jTextField6.getText ();
        String res = "";
        String updateEmpAPICall = "";
        String result3 = "";
        JSONObject responseObject = null;

        switch ( selectedMaster ) {

            case "Grades":
                deleteMaster(  "gradedelete?GradeID="+selectedRecordId,   "grade" , "GradeName" );
                break;

            case "Designations":
                deleteMaster( "designationsdelete?DesgID=" + selectedRecordId ,  "designation" , "DesgTitle" );
                break;

            case "Salary Band":
                deleteMaster( "salarybanddelete?SB_ID=" + selectedRecordId   ,"salaryband" , "SB_RANGE" );
                break;

            case "Nature of Employment":
//               res = DB_Operations.updateIntoEmpNatureMaster ( grade , selectedRecordId );
//                showDataInParticularList ( "employeenature" , "NATURE" );
                break;

            case "Unit of Measurement":
                
                deleteMaster ( "unitmeasdelete?UOM_ID=" + selectedRecordId  , "uom" , "UOM" );
                break;

            case "Pack Size":
                deleteMaster ( "packsizedelete?PackSize_ID=" + selectedRecordId , "pack_size" , "PackSize" );
                break;

            case "Raw Material Type":
                deleteMaster ( "rmtdelete?RMType_ID=" + selectedRecordId  ,  "raw_material_type" , "RawMaterial" );
                break;

            case "Category":
                deleteMaster ( "categorydelete?Category_ID=" + selectedRecordId ,  "category" , "Category" );
                break;

            case "Segment":
                deleteMaster ( "segmentdelete?Segment_ID=" + selectedRecordId ,  "segment" , "Segment" );
                break;

            case "Industry":
                deleteMaster ( "industrydelete?Industry_ID="+ selectedRecordId ,  "industry" , "Industry" );
                break;

            case "Departments":
                deleteMaster (  "departmentdelete?DID=" + selectedRecordId , "department" , "Departments" );
                break;

            case "Resources":
                deleteMaster (  "resourcedelete?RSID=" + selectedRecordId , "resources" , "Resources" );
                break;
            case "User Roles":
                deleteMaster (   "userrolesdelete?Role_ID=" + selectedRecordId ,  "userroles" , "User Roles" );
                break;
            case "Machines Types":
                deleteMaster (   "machinestype_delete?MC_TYPE_ID=" + selectedRecordId ,  "machinestypes" , "Machines Types" );
                break;     
           

            case "Rejection Reason":
                deleteMaster(   "rejreasonsdelete?RR_ID=" + selectedRecordId , "rejreasons" , "RejectionReasons" );
                break;
                
               
                
                case "TimeLoss Reasons":
                deleteMaster (   "timeloss_reason_delete?TLR_ID=" + selectedRecordId ,  "timelossres" , "TimeLoss Reasons" );
                break;

            case "Maintenance Types":
                deleteMaster(   "maintenance_type_delete?MT_ID=" + selectedRecordId , "mainttypes" , "Maintenance Types" );
                break;

            case "Document Types":
                deleteMaster(  "rdmdelete?RDM_UID=" + selectedRecordId, "rdm" , "DocumentType");
                break;
                
                 //************Edited by Harshali 06 May 2019
            case "Quality Raw Material":
                deleteMaster(  "rmqty_u_master_delete?param_id=" + selectedRecordId, "qualityrawmaterial" , "Quality Raw Material");
                break;
            case "Quality Process":
                deleteMaster(  "procqty_u_master_delete?param_id=" + selectedRecordId, "qualityprocess" , "Quality Process");
                break;
            case "Quality Product":
                deleteMaster(  "partqty_u_master_delete?param_id=" + selectedRecordId, "qualityproduct" , "Quality Product");
                break;
                //************Edited by Harshali 06 May 2019 
        }

        jTextField6.setText ( "" );
        
        
    }                                     

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed
    public static void deleteMaster( String apiName, String masterShort,  String masterColumnName  ){

        // title optionType messageType
        
        int selection = JOptionPane.showConfirmDialog ( null , "<html>Deleting a record is not recommended<br>The <h3>Delete</h3> action may cause loss of any dependencies and may affect the transactional Data and Reports and make data inconsistent.\nPress 'No' to revert, Press 'Yes' to continue deleting.", "Are you sure about deleting this record ?", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE);
        if ( selection == JOptionPane.YES_OPTION ) {
            
            String response  = WebAPITester.prepareWebCall ( apiName,  StaticValues.getHeader () , "");
            JSONObject responseObject = ( JSONObject ) new JSONTokener ( response ).nextValue ();
            JOptionPane.showMessageDialog ( null , retreiveSuccessMessage ( responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () ) );
            showDataInParticularList ( masterShort , masterColumnName );
            
        }
    }

    public static void showDataInParticularList ( String Mastername , String keyName ) {

        loadContent ( Mastername );

        /*
         * try { SelectedObjects ob; ResultSet result =
         * DB_Operations.getMasters("grade"); if (result != null) { int length =
         * result.getFetchSize();
         *
         * while (result.next()) { ob = new SelectedObjects();
         * ob.setId(result.getInt("GradeID"));
         * ob.setValue(result.getString("GradeName")); dataList.addElement(ob);
         * length++; } // jList6.setModel(dataList); valuesList = new
         * JList<>(dataList); } } catch (SQLException e) {
         *
         * }
         */
    }


    // Variables declaration - do not modify                     
    private javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JButton jButton1;
    public static javax.swing.JButton jButton10;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    public static javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    public static javax.swing.JLabel jLabel26;
    private javax.swing.JList<String> jList9;
    public static javax.swing.JRadioButton jRadioButton1;
    public static javax.swing.JRadioButton jRadioButton2;
    public static javax.swing.JRadioButton jRadioButton3;
    public static javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane9;
    public static javax.swing.JTable jTable2;
    public static javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration                   

    DefaultListModel<SelectedObjects> dataList = new DefaultListModel<> ();
    int selectedIndex = -1, selectedRecordId = -1;
    JList<SelectedObjects> valuesList;

}

class CompId implements Comparator<TestData> {

    @Override
    public int compare ( TestData arg0 , TestData arg1 ) {
        return arg0.id - arg1.id;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
package masters;

import Model.SelectedObjects;
import Model.StaticValues;
import Utilities.DB_Operations;
import static masters.Employee_HR_Master.buildTableModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import web_services.WebAPITester;


public class UtilitiyMasters extends javax.swing.JPanel {

    static String resource = "", resourceType = "";

    static ArrayList<TestData> rmt1 = new ArrayList<> ();
    static TestData data = new TestData ();

    static int selectedRowIndex;
    //json array fetch

    static String EmpAPICall = null;
    static String result2 = null;
    static JSONObject responseObject = null;

    
    public UtilitiyMasters () {
        initComponents ();
        //  this.setDefaultButton(jButton9);

        resourceType = "Masters";
        resource = "UtilitiyMasters";

//        try {
//            StaticValues.checkUserRolePermission ( resourceType , resourceType );
//        } catch ( Exception e ) {
//            StaticValues.writer.writeExcel ( resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }

    }

    public static void loadContent ( String val ) {
        //       ResultSet result = null;
//        try {
//
//            SelectedObjects ob;
//
//            //System.out.println( val );
//            
//            
//            result = DB_Operations.getMasters ( val );
//
//            if ( result != null ) {
//                jTable2.setModel ( buildTableModel ( result , val ) );
//
//                jTable2.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
//                jTable2.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
//            }
////      
//  //          System.out.println("test");
//            result.close ();
//        } catch ( SQLException e ) {
//
//            try {
//                result.close ();
//            } catch ( SQLException ex ) {
//                StaticValues.writer.writeExcel ( UtilitiyMasters.class.getSimpleName () , UtilitiyMasters.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//            }
//
//            System.out.println ( "" + e.getMessage () );
//            StaticValues.writer.writeExcel ( UtilitiyMasters.class.getSimpleName () , UtilitiyMasters.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }

        if(  val.equals ( "rdm" )  ){
            jRadioButton1.setBounds( 10, 40, 260 , 30 );
            jRadioButton2.setBounds( 10, 75, 260 , 30 );
            jRadioButton3.setBounds( 10, 110, 260 , 30 );
            jTextField6.setBounds( 10,140,270,30 );
            jLabel26.setBounds( 0, 140, 10, 10);
            jButton9.setBounds( 10, 180, 80, 30);
            jButton10.setBounds( 100, 180, 80, 30);
            jButton1.setBounds( 190, 180, 80, 30);
            jScrollPane2.setBounds(10, 230, 270, 210);
         }else{
            jRadioButton1.setBounds( 0, 0, 0 , 0 );
            jRadioButton2.setBounds( 0, 0, 0 , 0 );
            jRadioButton3.setBounds( 0, 0, 0 , 0 );            
            jTextField6.setBounds( 10,40,270,30 );
            jLabel26.setBounds( 0, 40, 10, 10);
            jButton9.setBounds( 10, 80, 80, 30);
            jButton10.setBounds( 100, 80, 80, 30);
            jButton1.setBounds( 190, 80, 80, 30);
            jScrollPane2.setBounds(10, 130, 270, 210);
         }


        switch ( val ) {

            case "grade":

                try {
                    int id;
                    String g_id;
                    String g_name;
                    EmpAPICall = "grade";
                    rmt1.clear ();
                    
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                    if(  !  result2.contains(  "not found"  )    ){
                    

                        responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject ( "data" ).toString ();

                        responseObject = new JSONObject ( page1 );

                        JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                        for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                            JSONObject jsonobject = Productjsarray.getJSONObject ( i );

                            //g_id = jsonobject.getString("GradeID");
                            //g_name = jsonobject.getString("GradeName");
                            //rmtst = jsonobject.getString("status");
                            //JOptionPane.showMessageDialog(null,"\n gradeid="+ g_id + "\n  gradename="+g_name  );
                            TestData data = new TestData ();

                            data.setId ( jsonobject.optInt ( "GradeID" ) );
                            data.setName ( jsonobject.optString ( "GradeName" ) );
    //                          id= data.jsonobject.optInt("GradeID");
    //                            HashMap<String, String> map = new HashMap<>();
    //                            map.put("ID", "" + id);
    //                            map.put("NAME", "" + name);

                            rmt1.add ( data );
                            //System.out.println(rmt1.add(map));
                            //JOptionPane.showMessageDialog(null, "id="+id+"name"+name );
                        }
                    
                    defaulttable ();
                    
                    }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "designation":
                try {
                    String g_id;
                    String g_name;
                    EmpAPICall = "designations";
                    rmt1.clear ();
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                    if(  !  result2.contains(  "not found"  )    ){

                        responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject ( "data" ).toString ();

                        responseObject = new JSONObject ( page1 );

                        JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                        for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                            JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                            TestData data = new TestData ();

                            data.setId ( jsonobject.optInt ( "DesgID" ) );
                            data.setName ( jsonobject.optString ( "DesgTitle" ) );
                            rmt1.add ( data );

                        }
                        defaulttable ();
                        
                    }
                } catch ( Exception e ) {
                    e.getMessage ();
                }

                break;

            case "salaryband":
                try {
                    String g_id;
                    String g_name;
                    EmpAPICall = "salaryband";
                    rmt1.clear ();
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                    if(  !  result2.contains(  "not found"  )    ){
                    
                        responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject ( "data" ).toString ();

                        responseObject = new JSONObject ( page1 );

                        JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                        for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                            JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                            TestData data = new TestData ();

                            data.setId ( jsonobject.optInt ( "SB_ID" ) );
                            data.setName ( jsonobject.optString ( "SB_RANGE" ) );
                            rmt1.add ( data );

                        }
                        defaulttable ();
                    }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "employeenature":
                showDataInParticularList ( "employeenature" , "NATURE" );
                break;

            case "uom":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "unitmeas";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                    if(  !  result2.contains(  "not found"  )    ){
                    
                        responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject ( "data" ).toString ();

                        responseObject = new JSONObject ( page1 );

                        JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                        for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                            JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                            TestData data = new TestData ();

                            data.setId ( jsonobject.optInt ( "UOM_ID" ) );
                            data.setName ( jsonobject.optString ( "UOM" ) );
                            rmt1.add ( data );

                        }
                        defaulttable ();
                        
                    }
                    
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "pack_size":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "packsize";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){

                        responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject ( "data" ).toString ();

                        responseObject = new JSONObject ( page1 );

                        JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                        for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                            JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                            TestData data = new TestData ();

                            data.setId ( jsonobject.optInt ( "PackSize_ID" ) );
                            data.setName ( jsonobject.optString ( "PackSize" ) );
                            rmt1.add ( data );

                        }
                        defaulttable ();
                        
                     }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "raw_material_type":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "rmt";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                   
                     if(  !  result2.contains(  "not found"  )    ){

                        responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject ( "data" ).toString ();

                        responseObject = new JSONObject ( page1 );

                        JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                        for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                            JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                            TestData data = new TestData ();

                            data.setId ( jsonobject.optInt ( "RMType_ID" ) );
                            data.setName ( jsonobject.optString ( "RawMaterial" ) );
                            rmt1.add ( data );

                        }
                        defaulttable ();
                        
                     }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "category":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "category";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){
                    
                        responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                        //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                        String page1 = responseObject.getJSONObject ( "data" ).toString ();

                        responseObject = new JSONObject ( page1 );

                        JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                        for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                            JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                            TestData data = new TestData ();

                            data.setId ( jsonobject.optInt ( "Category_ID" ) );
                            data.setName ( jsonobject.optString ( "Category" ) );
                            rmt1.add ( data );

                        }
                        defaulttable ();
                        
                     }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "segment":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "segment";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){

                            responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                            //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                            String page1 = responseObject.getJSONObject ( "data" ).toString ();

                            responseObject = new JSONObject ( page1 );

                            JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                            for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                                JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                                TestData data = new TestData ();

                                data.setId ( jsonobject.optInt ( "Segment_ID" ) );
                                data.setName ( jsonobject.optString ( "Segment" ) );
                                rmt1.add ( data );

                            }
                            defaulttable ();

                     }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "industry":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "industry";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){

                            responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                            //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                            String page1 = responseObject.getJSONObject ( "data" ).toString ();

                            responseObject = new JSONObject ( page1 );

                            JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                            for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                                JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                                TestData data = new TestData ();

                                data.setId ( jsonobject.optInt ( "Industry_ID" ) );
                                data.setName ( jsonobject.optString ( "Industry" ) );
                                rmt1.add ( data );

                            }
                            defaulttable ();
                            
                     }
                     
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;
                
                
                case "maintenance_type":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "maintenance_type";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){

                            responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                            //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                            String page1 = responseObject.getJSONObject ( "data" ).toString ();

                            responseObject = new JSONObject ( page1 );

                            JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                            for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                                JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                                TestData data = new TestData ();

                                data.setId ( jsonobject.optInt ( "MT_ID" ) );
                                data.setName ( jsonobject.optString ( "activity" ) );
                                rmt1.add ( data );

                            }
                            defaulttable ();
                            
                     }
                     
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "rejreasons":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "rejreasons";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){

                            responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                            //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                            String page1 = responseObject.getJSONObject ( "data" ).toString ();

                            responseObject = new JSONObject ( page1 );

                            JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                            for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                                JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                                TestData data = new TestData ();

                                data.setId ( jsonobject.optInt ( "RR_ID" ) );
                                data.setName ( jsonobject.optString ( "RR_DESC" ) );
                                rmt1.add ( data );

                            }
                            defaulttable ();
                            
                     }
                     
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "rdm":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "rdm";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){

                            responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                            //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                            String page1 = responseObject.getJSONObject ( "data" ).toString ();

                            responseObject = new JSONObject ( page1 );

                            JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                            for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                                JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                                TestData data = new TestData ();

                                data.setId ( jsonobject.optInt ( "RDM_UID" ) );
                                data.setName ( jsonobject.optString ( "RDM_NAME" ) );
                                rmt1.add ( data );

                            }
                            defaulttable ();
                        
                     }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "department":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "department";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){

                            responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                            //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                            String page1 = responseObject.getJSONObject ( "data" ).toString ();

                            responseObject = new JSONObject ( page1 );

                            JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                            for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                                JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                                TestData data = new TestData ();

                                data.setId ( jsonobject.optInt ( "DID" ) );
                                data.setName ( jsonobject.optString ( "DNAME" ) );
                                rmt1.add ( data );

                            }
                            defaulttable ();
                            
                     }
                     
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "resources":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "resources";
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                   
                     if(  !  result2.contains(  "not found"  )    ){
                    
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                    String page1 = responseObject.getJSONObject ( "data" ).toString ();

                    responseObject = new JSONObject ( page1 );

                    JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                    for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                        JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                        TestData data = new TestData ();

                        data.setId ( jsonobject.optInt ( "RSID" ) );
                        data.setName ( jsonobject.optString ( "RSNAME" ) );
                        rmt1.add ( data );

                    }
                    defaulttable ();
                     }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

            case "userroles":
                try {
                    String g_id;
                    String g_name;
                    rmt1.clear ();
                    EmpAPICall = "userroles";
                    
                    result2 = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
                    
                     if(  !  result2.contains(  "not found"  )    ){
                    
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    //JOptionPane.showMessageDialog ( null ,  responseObject.getJSONObject ( "data").getJSONObject ("records").toString()  );
                    String page1 = responseObject.getJSONObject ( "data" ).toString ();

                    responseObject = new JSONObject ( page1 );

                    JSONArray Productjsarray = new JSONArray ( responseObject.getJSONArray ( "records" ).toString () );

                    for ( int i = 0 ; i < Productjsarray.length () ; i ++ ) {

                        JSONObject jsonobject = Productjsarray.getJSONObject ( i );
                        TestData data = new TestData ();

                        data.setId ( jsonobject.optInt ( "Role_ID" ) );
                        data.setName ( jsonobject.optString ( "RoleName" ) );
                        rmt1.add ( data );

                    }
                    //Collections.sort(rmt1);
                    defaulttable ();
                    
                     }
                } catch ( Exception e ) {
                    e.getMessage ();
                }
                break;

        }

    }

    static  public void defaulttable () {

        Collections.sort ( rmt1 , new CompId () );
        //System.out.println("BY ID");
        for ( TestData p : rmt1 ) {
            //System.out.println(p.toString());
        }
        DefaultTableModel model = new DefaultTableModel ();
        try {
            Vector<String> columns = new Vector<String> ();
            columns.add ( "ID" ); columns.add ( "Master Name" );
            
            Vector<Vector<String>> row = new Vector<Vector<String>> ();
            // ArrayList<TestData> list=new ArrayList();
            //for( TestData detail : rmt1) 
            for ( int i = 0 ; i < rmt1.size () ; i ++ ) {

                //ArrayList<TestData> arr=new ArrayList<>();
//                    arr.addrow(detail.getId());
                Vector<String> value = new Vector<String> ();
                value.add ( rmt1.get ( i ).getId ()+"" );
                value.add ( rmt1.get ( i ).getName () );
                row.add ( value );
            }
           
            jTable2.repaint ();
            jTable2.revalidate ();
            jTable2.setModel ( new DefaultTableModel ( row , columns ) );
            jTable2.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
            jTable2.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );

        } catch ( Exception e ) {
            e.getMessage ();
        }

        //jTable2 = new JTable( model );
        //JScrollPane scrollPane = new JScrollPane( jTable2 );    
    }

    public void deleteAllRows ( DefaultTableModel model ) {
        for ( int i = model.getRowCount () - 1 ; i >= 0 ; i -- ) {
            model.removeRow ( i );
        }
    }

    public static DefaultTableModel buildTableModel ( ResultSet rs , String column_name )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();

        columnNames.add ( metaData.getColumnName ( 1 ) );

        switch ( column_name ) {
            case "grade":
                columnNames.add ( "Grades" );
                break;

            case "designation":
                columnNames.add ( "Designations" );
                break;
            case "salaryband":
                columnNames.add ( "Salary Band" );
                break;
            case "employeenature":
                columnNames.add ( "Nature of Employement" );
                break;
            case "uom":
                columnNames.add ( "Unit of Measurements" );
                break;

            case "pack_size":
                columnNames.add ( "Pack Size" );
                break;

            case "raw_material_type":
                columnNames.add ( "Raw Material Type" );
                break;
            case "category":
                columnNames.add ( "Category" );
                break;
            case "segment":
                columnNames.add ( "Segment" );
                break;
            case "industry":
                columnNames.add ( "Industry" );
                break;

            case "rejectionReason":
                columnNames.add ( "RejectionReasons" );
                break;

            case "documentType":
                columnNames.add ( "DocumentType" );
                break;

            case "departments":
                columnNames.add ( "Departments" );
                break;

            case "resources":
                columnNames.add ( "Resource" );
                break;

            case "roles":
                columnNames.add ( "User Roles" );
                break;

        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        while ( rs.next () ) {
            Vector<Object> vector = new Vector<Object> ();

            vector.add ( rs.getObject ( 1 ) );
            vector.add ( rs.getObject ( 2 ) );

            //     System.out.println(rs.getObject(2) );
            data.add ( vector );
        }
        return new DefaultTableModel ( data , columnNames );
    }

    
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTextField9 = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jList9 = new javax.swing.JList<String>();
        jTextField6 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<String>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();

        jButton15.setText("Add");

        jButton16.setText("Edit");

        jList9.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "No Items Found" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane9.setViewportView(jList9);

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(286, 356));
        setLayout(null);

        jTextField6.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        add(jTextField6);
        jTextField6.setBounds(10, 140, 270, 30);

        jButton9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton9.setText("Add");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        add(jButton9);
        jButton9.setBounds(10, 180, 80, 30);

        jButton10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton10.setText("Edit");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        add(jButton10);
        jButton10.setBounds(100, 180, 80, 30);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jComboBox1.setMaximumRowCount(12);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Grades", "Designations", "Salary Band", "Unit of Measurement", "Pack Size", "Raw Material Type", "Category RM/Part", "Segment", "Industry", "Rejection Reason", "Document Types", "Departments", "Resources", "User Roles", "TimeLoss Reasons", "Maintenance Types", "Quality Raw Material", "Quality Process", "Quality Product", "Machines Types", " " }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        add(jComboBox1);
        jComboBox1.setBounds(10, 0, 270, 30);

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.setRowHeight(20);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        add(jScrollPane2);
        jScrollPane2.setBounds(10, 230, 370, 210);

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 0, 51));
        jLabel26.setText("*");
        add(jLabel26);
        jLabel26.setBounds(0, 140, 20, 10);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Delete");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(190, 180, 90, 25);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jRadioButton1.setText("Document For Parts / Item / Finished Good");
        jRadioButton1.setOpaque(false);
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        add(jRadioButton1);
        jRadioButton1.setBounds(10, 40, 259, 25);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jRadioButton2.setText("Document for Raw Material");
        jRadioButton2.setOpaque(false);
        add(jRadioButton2);
        jRadioButton2.setBounds(10, 70, 250, 25);

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jRadioButton3.setText("Others");
        jRadioButton3.setOpaque(false);
        add(jRadioButton3);
        jRadioButton3.setBounds(10, 100, 63, 25);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        addTODB ();
    }//GEN-LAST:event_jButton9ActionPerformed

    public void addTODB () {

        String selectedMaster = jComboBox1.getSelectedItem ().toString ();

        String value = jTextField6.getText ().trim ();
        String res = "";

        String addEmpAPICall = null;
        String result2 = null;
        JSONObject responseObject = null;

        if (  ! value.equalsIgnoreCase ( "" ) ) {

            switch ( selectedMaster ) {

                case "Grades":
                    // res = DB_Operations.insertIntoGradesMaster ( value );
                    //showDataInParticularList ( "grade" , "GradeName" );

                    try{
                        addEmpAPICall = "gradeadd?GradeName=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "grade" , "GradeName" );
                    break;

                case "Designations":
                    //res = DB_Operations.insertIntoDesignationsMaster ( value );
                    //showDataInParticularList ( "designation" , "DesgTitle" );

                    try{
                        addEmpAPICall = "designationsadd?DesgTitle=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "designation" , "DesgTitle" );
                    break;

                case "Salary Band":
                    //res = DB_Operations.insertIntoSalaryMaster ( value );
                    //showDataInParticularList ( "salaryband" , "SB_RANGE" );

                    try{
                        addEmpAPICall = "salarybandadd?SB_RANGE=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                   
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "salaryband" , "SB_RANGE" );

                    break;

                case "Nature of Employment":
                    //res = DB_Operations.insertIntoEmpNatureMaster ( value );
                    //showDataInParticularList ( "employeenature" , "NATURE" );

                    showDataInParticularList ( "employeenature" , "NATURE" );

                    break;

                case "Unit of Measurement":
                    //res = DB_Operations.insertIntoUOMMaster ( value );
                    //showDataInParticularList ( "uom" , "UOM" );

                    try{
                        addEmpAPICall = "unitmeasadd?UOM=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                   
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "uom" , "UOM" );

                    break;

                case "Pack Size":
                    //res = DB_Operations.insertUtilitiesMaster ( "PackSize" , value );
                    //showDataInParticularList ( "pack_size" , "PackSize" );

                    try{
                        addEmpAPICall = "packsizeadd?PackSize=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                   
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "pack_size" , "PackSize" );

                    break;

                case "Raw Material Type":
                    //res = DB_Operations.insertUtilitiesMaster ( "RawMaterial" , value );
                    //showDataInParticularList ( "raw_material_type" , "RawMaterialType" );

                   try{
                        addEmpAPICall = "rmtadd?RawMaterial=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "raw_material_type" , "RawMaterial" );

                    break;

                case "Category RM/Part":
                    //res = DB_Operations.insertUtilitiesMaster ( "Category" , value );
                    //showDataInParticularList ( "category" , "Category" );

                    try{
                        addEmpAPICall = "categoryadd?Category=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "category" , "Category" );

                    break;

                case "Segment":
                    // res = DB_Operations.insertUtilitiesMaster ( "Segment" , value );
                    //showDataInParticularList ( "segment" , "Segment" );

                    try{
                        addEmpAPICall = "segmentadd?Segment="  + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
               
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "segment" , "Segment" );

                    break;

                case "Industry":
                    //res = DB_Operations.insertUtilitiesMaster ( "Industry" , value );
                    //showDataInParticularList ( "industry" , "Industry" );

                     try{
                        addEmpAPICall = "industryadd?Industry=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "industry" , "Industry" );

                    break;

                case "Rejection Reason":
                    //res = DB_Operations.insertUtilitiesMaster ( "RejectionReason" , value );
                    //showDataInParticularList ( "rejectionReason" , "RejectionReasons" );

                     try{
                        addEmpAPICall = "rejreasonsadd?RR_DESC=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "rejreasons" , "RejectionReasons" );

                    break;

                case "Document Types":
                    //res = DB_Operations.insertUtilitiesMaster ( "DocumentType" , value );
                    //showDataInParticularList ( "documentType" , "DocumentType" );

                     try{
                        addEmpAPICall = "rdmadd?RDM_NAME=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "rdm" , "DocumentType" );

                    break;

                case "Departments":
                    //res = DB_Operations.insertUtilitiesMaster ( "Departments" , value );
                    // showDataInParticularList ( "departments" , "Departments" );

                    try{
                        addEmpAPICall = "departmentadd?DNAME=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "department" , "Departments" );

                    break;

                case "Resources":
                    //res = DB_Operations.insertUtilitiesMaster ( "Resources" , value );
                    //showDataInParticularList ( "resources" , "Resources" );

                    try{
                        addEmpAPICall = "resourceadd?RSNAME=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "resources" , "Resources" );

                    break;

                case "User Roles":
                    //res = DB_Operations.insertUtilitiesMaster ( "User Roles" , value );
                    //showDataInParticularList ( "roles" , "User Roles" );

                    try{
                        addEmpAPICall = "userrolesadd?RoleName="  + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "userroles" , "User Roles" );

                    break;
                    
                    case "Maintenance Type":
                    //res = DB_Operations.insertUtilitiesMaster ( "User Roles" , value );
                    //showDataInParticularList ( "roles" , "User Roles" );

                    try{
                        addEmpAPICall = "maintenance_type_add?activity="  + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                    
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    responseObject = ( JSONObject ) new JSONTokener ( result2 ).nextValue ();
                    
                    if( result2.contains ( "success") ){
                            JOptionPane.showMessageDialog ( null , "Successfuly created new entry in "+selectedMaster+" master" );
                    }

                    showDataInParticularList ( "maintenance_type" , "Maintainance Type" );

                    break;
            }

        } else {
            JOptionPane.showMessageDialog ( null , "Please enter valid text" );
        }

        jTextField6.setText ( "" );
        jTextField6.requestFocus ();
    }

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:

        String selectedMaster = jComboBox1.getSelectedItem ().toString ();
        String grade;
        String value = jTextField6.getText ().trim ();
        grade = jTextField6.getText ();
        String res = "";
        String updateEmpAPICall = "";
        String result3 = "";
        JSONObject responseObject = null;

        switch ( selectedMaster ) {

            case "Grades":

                //res = DB_Operations.updateIntoGradesMaster ( grade , selectedRecordId );
                //showDataInParticularList ( "grade" , "GradeName" );
                try{
                        updateEmpAPICall = "gradeupdate?GradeID=" + selectedRecordId + "&GradeName=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "grade" , "GradeName" );

                break;
                case "Maintenance Type":

                //res = DB_Operations.updateIntoGradesMaster ( grade , selectedRecordId );
                //showDataInParticularList ( "grade" , "GradeName" );
                try{
                        updateEmpAPICall = "maintenance_type_update?MT_ID=" + selectedRecordId + "&activity=" + URLEncoder.encode( value, "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        
                    }
                
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "maintenance_type" , "activity" );

                break;

            case "Designations":
                //res = DB_Operations.updateIntoDesignationsMaster ( grade , selectedRecordId );
                // showDataInParticularList ( "designation" , "DesgTitle" );

                try{
                     
                        updateEmpAPICall = "designationsupdate?DesgID=" + selectedRecordId + "&DesgTitle=" + URLEncoder.encode( value, "UTF-8");
                  }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "designation" , "DesgTitle" );

                break;

            case "Salary Band":
                //res = DB_Operations.updateIntoSalaryMaster ( grade , selectedRecordId );
                //showDataInParticularList ( "salaryband" , "SB_RANGE" );
                try{
                updateEmpAPICall = "salarybandupdate?SB_ID=" + selectedRecordId + "&SB_RANGE=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "salaryband" , "SB_RANGE" );

                break;

            case "Nature of Employment":
                res = DB_Operations.updateIntoEmpNatureMaster ( grade , selectedRecordId );
                showDataInParticularList ( "employeenature" , "NATURE" );

                break;

            case "Unit of Measurement":
                //res = DB_Operations.updateIntoUOMMaster ( grade , selectedRecordId );
                //showDataInParticularList ( "uom" , "UOM" );
                try{
                updateEmpAPICall = "unitmeasupdate?UOM_ID=" + selectedRecordId + "&UOM=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "uom" , "UOM" );

                break;

            case "Pack Size":

                //res = DB_Operations.updateUtilitiesMaster ( "PackSize" , grade , selectedRecordId );
                //showDataInParticularList ( "pack_size" , "PackSize" );
                try{
                updateEmpAPICall = "packsizeupdate?PackSize_ID=" + selectedRecordId + "&PackSize=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "pack_size" , "PackSize" );

                break;

            case "Raw Material Type":
                //res = DB_Operations.updateUtilitiesMaster ( "RawMaterial" , grade , selectedRecordId );

                //   System.out.println(   grade + "  " + selectedRecordId );
                //showDataInParticularList ( "raw_material_type" , "RawMaterial" );
                try{
                updateEmpAPICall = "rmtupdate?RMType_ID=" + selectedRecordId + "&RawMaterial=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "raw_material_type" , "RawMaterial" );

                break;

            case "Category RM/Part":
                //res = DB_Operations.updateUtilitiesMaster ( "Category" , grade , selectedRecordId );
                //showDataInParticularList ( "category" , "Category" );
try{
                updateEmpAPICall = "categoryupdate?Category_ID=" + selectedRecordId + "&Category=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "category" , "Category" );

                break;

            case "Segment":
                //res = DB_Operations.updateUtilitiesMaster ( "Segment" , grade , selectedRecordId );
                //showDataInParticularList ( "segment" , "Segment" );
try{
                updateEmpAPICall = "segmentupdate?Segment_ID=" + selectedRecordId + "&Segment=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "segment" , "Segment" );

                break;

            case "Industry":
                //res = DB_Operations.updateUtilitiesMaster ( "Industry" , grade , selectedRecordId );
                //showDataInParticularList ( "industry" , "Industry" );
try{
                updateEmpAPICall = "industryupdate?Industry_ID=" + selectedRecordId + "&Industry=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "industry" , "Industry" );

                break;

            case "Departments":
                //res = DB_Operations.updateUtilitiesMaster ( "Departments" , grade , selectedRecordId );
                //showDataInParticularList ( "departments" , "Departments" );
try{
                updateEmpAPICall = "departmentupdate?DID=" + selectedRecordId + "&DNAME=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "department" , "Departments" );

                break;

            case "Resources":
                //res = DB_Operations.updateUtilitiesMaster ( "Resources" , grade , selectedRecordId );
                //showDataInParticularList ( "resources" , "Resources" );
try{
                updateEmpAPICall = "resourceupdate?RSID=" + selectedRecordId + "&RSNAME=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "resources" , "Resources" );

                break;

            case "User Roles":
                //res = DB_Operations.updateUtilitiesMaster ( "User Roles" , grade , selectedRecordId );
                //showDataInParticularList ( "roles" , "User Roles" );
try{
                updateEmpAPICall = "userrolesupdate?Role_ID=" + selectedRecordId + "&RoleName=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "userroles" , "User Roles" );

                break;

            case "Rejection Reason":
try{
                updateEmpAPICall = "rejreasonsupdate?RR_ID=" + selectedRecordId + "&RR_DESC=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "rejreasons" , "RejectionReasons" );
                break;

            case "Document Types":
try{
                updateEmpAPICall = "rdmupdate?RDM_UID=" + selectedRecordId + "&RDM_NAME=" + URLEncoder.encode( value, "UTF-8");
                }catch(UnsupportedEncodingException e){
                        
                    }
                result3 = WebAPITester.prepareWebCall ( updateEmpAPICall , StaticValues.getHeader () , "" );
                responseObject = ( JSONObject ) new JSONTokener ( result3 ).nextValue ();
                JOptionPane.showMessageDialog ( null , responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () );

                showDataInParticularList ( "rdm" , "DocumentType" );
                break;

        }

        jTextField6.setText ( "" );
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:

        String selectedMaster = jComboBox1.getSelectedItem ().toString ();
        String res = "";

         jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        
        switch ( selectedMaster ) {

            case "Grades":

                showDataInParticularList ( "grade" , "GradeName" );
                break;

            case "Designations":
                showDataInParticularList ( "designation" , "DesgTitle" );
                break;

            case "Salary Band":
                showDataInParticularList ( "salaryband" , "SB_RANGE" );
                break;

            case "Nature of Employment":
                showDataInParticularList ( "employeenature" , "NATURE" );
                break;

            case "Unit of Measurement":
                showDataInParticularList ( "uom" , "UOM" );
                break;

            case "Pack Size":
                showDataInParticularList ( "pack_size" , "PackSize" );
                break;

            case "Raw Material Type":
                showDataInParticularList ( "raw_material_type" , "RawMaterial" );
                break;

            case "Category RM/Part":
                showDataInParticularList ( "category" , "Category" );
                break;

            case "Segment":
                showDataInParticularList ( "segment" , "Segment" );
                break;

            case "Industry":
                showDataInParticularList ( "industry" , "Industry" );
                break;

            case "Rejection Reason":
                showDataInParticularList ( "rejreasons" , "RejectionReasons" );
                break;

            case "Document Types":
                showDataInParticularList ( "rdm" , "DocumentType" );
                break;

            case "Departments":
                showDataInParticularList ( "department" , "Departments" );
                break;

            case "Resources":
                showDataInParticularList ( "resources" , "Resources" );
                break;

            case "User Roles":
                showDataInParticularList ( "userroles" , "User Roles" );
                break;
                
                 case "Maintenance Type":
                showDataInParticularList ( "maintenance_type" , "Maintenance Type" );
                break;

        }
        
       
            
        
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = ( DefaultTableModel ) jTable2.getModel ();
        // get the selected row index
        selectedRowIndex = jTable2.getSelectedRow ();
        jTextField6.setText ( model.getValueAt ( selectedRowIndex , 1 ).toString () );
        //selectedRowIndex += 1;
        selectedRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );
        System.out.println ( ""+selectedRecordId );
        
    }//GEN-LAST:event_jTable2MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    
    public static  String retreiveSuccessMessage( String json){
        String id = "Not able to fetch the response for your request. Please refresh you master form and try again";
        JSONObject jObject = new JSONObject ( json );
        id = jObject.getString("118" );
        return id ;
    }
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        
         String selectedMaster = jComboBox1.getSelectedItem ().toString ();
        String grade;
        String value = jTextField6.getText ().trim ();
        grade = jTextField6.getText ();
        String res = "";
        String updateEmpAPICall = "";
        String result3 = "";
        JSONObject responseObject = null;

        switch ( selectedMaster ) {

            case "Grades":
                deleteMaster(  "gradedelete?GradeID="+selectedRecordId,   "grade" , "GradeName" );
                break;

            case "Designations":
                deleteMaster( "designationsdelete?DesgID=" + selectedRecordId ,  "designation" , "DesgTitle" );
                break;

            case "Salary Band":
                deleteMaster( "salarybanddelete?SB_ID=" + selectedRecordId   ,"salaryband" , "SB_RANGE" );
                break;

            case "Nature of Employment":
//               res = DB_Operations.updateIntoEmpNatureMaster ( grade , selectedRecordId );
//                showDataInParticularList ( "employeenature" , "NATURE" );
                break;

            case "Unit of Measurement":
                
                deleteMaster ( "unitmeasdelete?UOM_ID=" + selectedRecordId  , "uom" , "UOM" );
                break;

            case "Pack Size":
                deleteMaster ( "packsizedelete?PackSize_ID=" + selectedRecordId , "pack_size" , "PackSize" );
                break;

            case "Raw Material Type":
                deleteMaster ( "rmtdelete?RMType_ID=" + selectedRecordId  ,  "raw_material_type" , "RawMaterial" );
                break;

            case "Category RM/Part":
                deleteMaster ( "categorydelete?Category_ID=" + selectedRecordId ,  "category" , "Category" );
                break;

            case "Segment":
                deleteMaster ( "segmentdelete?Segment_ID=" + selectedRecordId ,  "segment" , "Segment" );
                break;

            case "Industry":
                deleteMaster ( "industrydelete?Industry_ID="+ selectedRecordId ,  "industry" , "Industry" );
                break;

            case "Departments":
                deleteMaster (  "departmentdelete?DID=" + selectedRecordId , "department" , "Departments" );
                break;

            case "Resources":
                deleteMaster (  "resourcedelete?RSID=" + selectedRecordId , "resources" , "Resources" );
                break;
            case "User Roles":
                deleteMaster (   "userrolesdelete?Role_ID=" + selectedRecordId ,  "userroles" , "User Roles" );
                break;

            case "Rejection Reason":
                deleteMaster(   "rejreasonsdelete?RR_ID=" + selectedRecordId , "rejreasons" , "RejectionReasons" );
                break;

            case "Document Types":
                deleteMaster(  "rdmdelete?RDM_UID=" + selectedRecordId, "rdm" , "DocumentType");
                break;
                
                
        }

        jTextField6.setText ( "" );
        
        
    }//GEN-LAST:event_jButton1MouseClicked
    public static void deleteMaster( String apiName, String masterShort,  String masterColumnName  ){

        // title optionType messageType
        
        int selection = JOptionPane.showConfirmDialog ( null , "<html>Deleting a record is not recommended<br>The <h3>Delete</h3> action may cause loss of any dependencies and may affect the transactional Data and Reports and make data inconsistent.\nPress 'No' to revert, Press 'Yes' to continue deleting.", "Are you sure about deleting this record ?", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE);
        if ( selection == JOptionPane.YES_OPTION ) {
            
            String response  = WebAPITester.prepareWebCall ( apiName,  StaticValues.getHeader () , "");
            JSONObject responseObject = ( JSONObject ) new JSONTokener ( response ).nextValue ();
            JOptionPane.showMessageDialog ( null , retreiveSuccessMessage ( responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () ) );
            showDataInParticularList ( masterShort , masterColumnName );
            
        }
    }

    public static void showDataInParticularList ( String Mastername , String keyName ) {

        loadContent ( Mastername );

      
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JButton jButton1;
    public static javax.swing.JButton jButton10;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    public static javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    public static javax.swing.JLabel jLabel26;
    private javax.swing.JList<String> jList9;
    public static javax.swing.JRadioButton jRadioButton1;
    public static javax.swing.JRadioButton jRadioButton2;
    public static javax.swing.JRadioButton jRadioButton3;
    public static javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane9;
    public static javax.swing.JTable jTable2;
    public static javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

    DefaultListModel<SelectedObjects> dataList = new DefaultListModel<> ();
    int selectedIndex = -1, selectedRecordId = -1;
    JList<SelectedObjects> valuesList;

}

class CompId implements Comparator<TestData> {

    @Override
    public int compare ( TestData arg0 , TestData arg1 ) {
        return arg0.id - arg1.id;
    }
}


*/
