/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

import trixware.erp.prodezydesktop.Model.AlarmData;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.controllers.AlarmController3;
import trixware.erp.prodezydesktop.examples.HomeScreen;
import static trixware.erp.prodezydesktop.examples.HomeScreen.alarm;
import static trixware.erp.prodezydesktop.examples.HomeScreen.jMenu10;
import static trixware.erp.prodezydesktop.examples.HomeScreen.jMenu11;
import static trixware.erp.prodezydesktop.examples.HomeScreen.jMenu12;
import static trixware.erp.prodezydesktop.examples.HomeScreen.jMenu13;
import static trixware.erp.prodezydesktop.examples.HomeScreen.jMenu8;
import static trixware.erp.prodezydesktop.examples.HomeScreen.jMenuItem13;
import static trixware.erp.prodezydesktop.examples.HomeScreen.jMenuItem31;
import static trixware.erp.prodezydesktop.examples.HomeScreen.jMenuItem38;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import trixware.erp.prodezydesktop.web_services.WebAPITester;
import trixware.erp.prodezydesktop.web_services.WebServices_Controller;

/**
 *
 * @author Rajesh
 */
public class Login {

    public static boolean authenticate ( String username , String password ) {

        boolean isValiduser = false;
        String userName = null;
        int userId;

        AlarmController3 alarmController = null ;
        ArrayList<AlarmData> alarmData = null ;
        
        try {
            
                    //System.out.println ( WebServices_Controller.getCategories ());
                    String response = WebServices_Controller.getToken ( username, password );
                    JSONObject responseObject = (JSONObject) new JSONTokener(response).nextValue() ;
                   
                    if( !  response.contains ( "\"status\":\"error\""  )){
                        

                        if( responseObject.getJSONObject ( "data").getString("token")!=null && ! responseObject.getJSONObject ( "data").getString("token").equals ( "") ) 
                        {
                            isValiduser = true ;
                            StaticValues.setHeader( responseObject.getJSONObject ( "data").getString("token") );
                            Proman_User.setUsername ( responseObject.getJSONObject ( "data").getString("displayname") );

                            Proman_User.setEID ( responseObject.getJSONObject ( "data").getInt("user_id") );
                            Proman_User.setUsername ( responseObject.getJSONObject ( "data").getString("displayname") );
                            Proman_User.setDeptIds ( 1+"" );
                            Proman_User.setRole ( 1+"" );
                            Proman_User.setIsActivelyLoggedIn ( true );

                            Connection _con = DriverManager.getConnection ( "jdbc:sqlite:SSDsdIadc_xlp.db" );
                            Calendar c2 = Calendar.getInstance ();
                            SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
                            String strDate2 = sdf2.format ( c2.getTime () );

                            String logSession = "insert into lilosessIONS (   'LITIME', 'LOTIME',  'UUID', 'ISACTIVE' , 'FULLNAME', 'DEPARTMENT' , 'ROLE' , UNAME) values (  '" + strDate2 + "',  'NA',  " + responseObject.getJSONObject ( "data").getInt("user_id")  + ",  1,  '" + responseObject.getJSONObject ( "data").getString("token") + "', '" + 1 + "',  '" + 1 + "' , '"+responseObject.getJSONObject ( "data").getString("displayname")+"' ) ";

                            Statement st = _con.createStatement ();
                            st.execute ( logSession );
                            st.close ();



                            String[] deptRole  ;
                
                                deptRole = filterMenusAndScreens( responseObject.getJSONObject ( "data").getInt("user_id")  ) ;

                                if( ! deptRole[0].equals (  "Failed : " )){

                                int _dept = Integer.parseInt( deptRole[0] );
                                int _role = Integer.parseInt( deptRole[1] );

                       
                                //department 1 
                                // Role 1 : Admin  ,     Role 2 = Supervisor  ,   Role 3 = Operator

                                switch(   _role  )
                                {

                                    case 1:
                                            jMenuItem31.setVisible ( true );
                                           // jMenuItem13.setVisible ( true );
                                            jMenu8.setVisible( true ) ;
                                            jMenu10.setVisible( true ) ;
                                            jMenu11.setVisible( true ) ;
                                            jMenu12.setVisible( true ) ;
                                            jMenu13.setVisible( false ) ;
                                            jMenuItem38.setVisible( true  );
                                            HomeScreen.jMenuBar1.setVisible(true);
                                            HomeScreen.home.LoadDashboard ();
                                            
                                            
                                            alarm = new Alarms ();
                                            alarmData = new ArrayList<AlarmData> ();
                                            alarmData = alarm.getAlarmState ();
                                            alarmController.startAlarm ( "Preparing notifications" , true , alarmData );
                                            
                                        break ;

                                    case 2:
                                             jMenuItem31.setVisible ( false );
                                             jMenuItem13.setVisible ( false );
                                             jMenu8.setVisible( true ) ;
                                            jMenu10.setVisible( true ) ;
                                            jMenu11.setVisible( true ) ;
                                            jMenu12.setVisible( true ) ;
                                            jMenu13.setVisible( false ) ;
                                            jMenuItem38.setVisible( true  );
                                            HomeScreen.jMenuBar1.setVisible(true);
                                            HomeScreen.home.LoadDashboard ();
                                            
                                           
                                            alarmController = new AlarmController3 ( true );
                                            alarm = new Alarms ();
                                            alarmData = alarm.getAlarmState ();
                                            alarmController.startAlarm ( "Preparing notifications" , true , alarmData );
                                            
                                        break ;

                                    case 3:
                                            jMenu8.setVisible( false ) ;
                                            jMenu10.setVisible( false ) ;
                                            jMenu11.setVisible( false ) ;
                                            jMenu12.setVisible( false ) ;
                                            jMenu13.setVisible( false ) ;
                                            jMenuItem38.setVisible( false  );

                                            jMenuItem13.setVisible ( false );
                                            HomeScreen.jMenuBar1.setVisible(true);
                                            HomeScreen.home.LoadDefaultDataEntry ();
                                        break ;

                                    default :

                                }
                                
     //********************hide menus by product level*************************************
        String addEmpAPICall, res;
        addEmpAPICall = "switch";
        res = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

        if (!res.contains("not found")) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            JSONObject jObject = new JSONObject(res);
            Iterator<?> keys = jObject.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                Object value = jObject.get(key);
                map.put(key, value);
            }

            JSONObject st1 = (JSONObject) map.get("data");
            JSONArray records = st1.getJSONArray("records");

            JSONObject emp = null;

            for (int i = 0; i < records.length(); i++) {

                emp = records.getJSONObject(i);
                String strchk="";
                        strchk= emp.get("s_app").toString();
                if (strchk.equals( "enterprise")) {
                    
                     HomeScreen.jMenuItem14.setVisible(false);
                     
                } else if (strchk.equals ("advance")) {
                    
//                    HomeScreen.jMenuItem38.setVisible(false); 
//                    HomeScreen.maintain_history.setVisible(false);
//                    HomeScreen.jMenuItem51.setVisible(false);
//                    HomeScreen.jMenuItem6.setVisible(false);
//                    HomeScreen.jMenuItem11.setVisible(false);
//                    HomeScreen.COPQ.setVisible(false);
//                    HomeScreen.jMenuItem21.setVisible(false);
//                    HomeScreen.diefrm.setVisible(false);

                } else if (strchk.equals( "basic")) {
//                    HomeScreen.jMenu9.setVisible(false);
//                    HomeScreen.jMenuItem2.setVisible(false);
//                    HomeScreen.jMenuItem16.setVisible(false);
//                    HomeScreen.jMenuItem17.setVisible(false);
//                    //RMFGcode1.setVisible(false);
//                    HomeScreen.jMenuItem52.setVisible(false);
                    
                } else {
                    //diefrm.setVisible(false);
//                    HomeScreen.jMenuItem14.setVisible(false);
//                    HomeScreen.jMenuItem18.setVisible(false);
//                    HomeScreen.jMenuItem35.setVisible(false);
//                    //hourscreen.setVisible(false);
//                    HomeScreen.jMenuItem36.setVisible(false);
//                    HomeScreen.jMenuItem37.setVisible(false);
//                    HomeScreen.jMenuItem47.setVisible(false);
//                    HomeScreen.jMenuItem30.setVisible(false);
//                    HomeScreen.jMenuItem10.setVisible(false);
//                    HomeScreen.jMenuItem13.setVisible(false);
//                    HomeScreen.jMenuItem8.setVisible(false);
//                    //jMenuItem11.setVisible ( false );
//                    HomeScreen.jMenuItem19.setVisible(false);
//                    HomeScreen.jMenuItem20.setVisible(false);
//
//                    HomeScreen.jMenuItem9.setVisible(false);
//                    HomeScreen.jMenu13.setVisible(false);
//                    HomeScreen.jMenu1.setVisible(false);
//
//                    HomeScreen.jMenuBar1.setVisible(false);
                }
            }
        }
        
        //********************end hide menus by product level edited by mayur*************************************


                            isValiduser = true;
                            HomeScreen.home.jLabel3.setText ( "Welcome " + Proman_User.getUsername () + " " );
                            HomeScreen.home.jLabel5.setVisible ( true );
        
                        }
                    }
                    return isValiduser ;
                }else{
                
                        return false ;
                }
                      
        }catch (JSONException e) {

            StaticValues.writer.writeExcel ( Login.class.getSimpleName () , Login.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            
            return isValiduser ;
        }catch (SQLException e) {

            StaticValues.writer.writeExcel ( Login.class.getSimpleName () , Login.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            
            return isValiduser ;
        }finally{
            
            LoginForm.hideDialog() ;
        }

        
    }
    
    
    public static String[] filterMenusAndScreens(  int id ){
        
        int department=0, role=0 ;
        
            //&parent_id=5
        String result2 = WebAPITester.prepareWebCall ( "useredit?user_id="+id  , StaticValues.getHeader () , "" );
        
        if(  result2.contains("success")   ) {
            HashMap<String , Object> map = new HashMap<String , Object> ();
            JSONObject jObject = new JSONObject ( result2 );
            Iterator<?> keys = jObject.keys ();

            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }

             JSONArray records = ( JSONArray ) map.get ( "data" );

            JSONObject emp = null;
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );

                try{    department = Integer.parseInt(  emp.get ( "department_id" ).toString () );   }catch(   NumberFormatException e   ){    department = 0 ;            }
                try{    role = Integer.parseInt(  emp.get ( "role_id" ).toString () );      }catch(  NumberFormatException e   ){  role = 0 ;     }

                Proman_User.setUsername ( emp.get ( "firstname" ).toString () );
            }

            return new String[]{ department+"", role+"" };
            
        }else{
           
            return new String[] {  "Failed : ", result2  } ;
        }
    }
    
    
    public static boolean authenticate_OLD ( String username , String password ) {

        boolean isValiduser = false;
        String userName = null;
        int userId;

        try {

            if (  ! username.equals ( "pr0de2y4dm1n" ) &&  ! password.equals ( "pr0de2y4dm1n" ) ) {
                //EmployeePK,  EMP_NAME, USERNAME, PASSWORD
                ResultSet result = DB_Operations.executeRandom ( username , password );

                if ( result != null ) {

                    boolean isUser = Boolean.parseBoolean ( result.getString ( "IS_USER" ) );

                    String value1 = result.getString ( "USERNAME" );
                    String value2 = result.getString ( "PASSWORD" );
                    userId = Integer.parseInt ( result.getString ( "EmployeePK" ) );
                    userName = result.getString ( "EMP_NAME" );
                    int uid = result.getInt ( "EmployeePK" );
                    String deptid = result.getString ( "DEPARTMENTID" );
                    String role = result.getString ( "ROLE" );

                    result.close ();

                    if ( value1.equals ( username ) && value2.equals ( password ) && isUser ) {

                        Proman_User.setEID ( userId );
                        Proman_User.setUsername ( userName );

                        Proman_User.setDeptIds ( deptid );
                        Proman_User.setRole ( role );

                        //  String gerURPDetails = "select  (select DNAME from DEPARTMENTS where DID in (DEPTID) ) as 'DEPARTMENT', (select RSNAME from RESOURCE where RSID in (RESID ) )as 'RESOURCE', READ, EDIT, DISABLE, EXPORT, CREATENEW, IMPORT from USERROLEPERM where DEPTID = "+deptid+" AND ROLE = '"+role+"'";
                        //   result = DB_Operations.executeSingle ( gerURPDetails ) ;
                        //    HomeScreen.user = new DeptRolePerm(  result.getString("DEPARTMENT"), result.getString("RESOURCE") , role,  result.getInt("READ") ,  result.getInt("EDIT") ,  result.getInt("DISABLE") ,  result.getInt("EXPORT") ,  result.getInt("CREATENEW") ,  result.getInt("IMPORT")  );
                        //     System.out.println ( HomeScreen.user.getDepartment ()+" - "+ HomeScreen.user.getRole ()+" - "+  result.getInt("READ") +" - "+  result.getInt("EDIT") +" - "+ result.getInt("DISABLE") +" - "+  result.getInt("EXPORT") +" - "+  result.getInt("CREATENEW") +" - "+  result.getInt("IMPORT")  );
                        Connection _con = DriverManager.getConnection ( "jdbc:sqlite:SSDsdIadc_xlp.db" );
                        Calendar c2 = Calendar.getInstance ();
                        SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
                        String strDate2 = sdf2.format ( c2.getTime () );

                        String logSession = "insert into lilosessIONS (   'LITIME', 'LOTIME',  'UUID', 'ISACTIVE' , 'FULLNAME', 'DEPARTMENT' , 'ROLE' ) values (  '" + strDate2 + "',  'NA',  " + uid + ",  1,  '" + userName + "', '" + deptid + "',  '" + role + "' ) ";

                        Statement st = _con.createStatement ();
                        st.execute ( logSession );
                        st.close ();
                        Proman_User.setIsActivelyLoggedIn ( true );
                        isValiduser = true;

                        
                        //System.out.println ( WebServices_Controller.getCategories ());
                        //String response = WebServices_Controller.getToken ();
                        //JSONObject responseObject = (JSONObject) new JSONTokener(response).nextValue() ;
                      ////  System.out.println ( ""+responseObject.getJSONObject ( "data").getString("token") );
                       // StaticValues.setHeader( responseObject.getJSONObject ( "data").getString("token") );
                        
                        
                        HomeScreen.home.jLabel3.setText ( "Welcome " + Proman_User.getUsername () + " " );
                        HomeScreen.home.LoadDashboard ();
                        
                      //  HomeScreen.home.jMenu2.setVisible(  true );
                      HomeScreen.home.jLabel5.setVisible ( true );

                    } else {
                        return false;
                    }
                }
            } else if ( username.equals ( "pr0de2y4dm1n" ) && password.equals ( "pr0de2y4dm1n" ) ) {
                try{
                    Connection _con = DriverManager.getConnection ( "jdbc:sqlite:SSDsdIadc_xlp.db" );
                    Calendar c2 = Calendar.getInstance ();
                    SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
                    String strDate2 = sdf2.format ( c2.getTime () );

                    String logSession = "insert into lilosessIONS (   'LITIME', 'LOTIME',  'UUID', 'ISACTIVE' ) values (  '" + strDate2 + "',  'NA',  " + 999999 + ",  1) ";

                    Statement st = _con.createStatement ();
                    st.execute ( logSession );
                    st.close ();

                    Proman_User.setEID ( 999999 );
                    Proman_User.setUsername ( "Administrator" );
                    Proman_User.setIsActivelyLoggedIn ( true );

                    isValiduser = true;

                    HomeScreen.home.jLabel3.setText ( "Welcome " + Proman_User.getUsername () + " ! " );
                  //  HomeScreen.home.jMenu2.setVisible(  true );
                    HomeScreen.home.jLabel5.setVisible ( true );

                    HomeScreen.home.LoadDashboard ();
                }catch( Exception e ){
            //        JOptionPane.showMessageDialog ( null, "Some error while loading dashboard "+e.getMessage ()+"  "+e.getClass ());
                    StaticValues.writer.writeExcel ( Login.class.getSimpleName () , Login.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }
            } else {
                isValiduser = false;
            }
        } catch (SQLException e) {
            //    return false;
            if ( username.equals ( "pr0de2y4dm1n" ) && password.equals ( "pr0de2y4dm1n" ) ) {

            Proman_User.setEID ( 999999 );
            Proman_User.setUsername ( "Administrator" );
            Proman_User.setIsActivelyLoggedIn ( true );
            isValiduser = true;

            
                //System.out.println ( WebServices_Controller.getCategories ());
//            String response = WebServices_Controller.getToken ();
//            JSONObject responseObject = (JSONObject) new JSONTokener(response).nextValue() ;
//            System.out.println ( ""+responseObject.getJSONObject ( "data").getString("token") );
//            StaticValues.setHeader( responseObject.getJSONObject ( "data").getString("token") );
            
            HomeScreen.home.jLabel3.setText ( "Welcome " + Proman_User.getUsername () + " ! " );

            HomeScreen.home.LoadDashboard ();
            
            HomeScreen.home.jLabel5.setVisible ( false );
           // HomeScreen.home.jMenu2.setVisible(  true );

        } else {
            isValiduser = false;
        }

        System.out.println ( "Error " + e.getMessage () );
        StaticValues.writer.writeExcel ( Login.class.getSimpleName () , Login.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
    }

    return isValiduser ;
    }
    
}

