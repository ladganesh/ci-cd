/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.web_services;

import trixware.erp.prodezydesktop.Model.StaticValues;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Rajesh
 */
public class WebServices_Controller {

    private static URL myUrl = null;
    private static HttpURLConnection connection = null;
    private static final String REQUEST_METHOD = "POST";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;

    private static String result;
    private static String inputLine;

    public static String header = "eyJpdiI6ImFWcWJDUDF4OWhlazRWZ2VibDNickE9PSIsInZhbHVlIjoiTVhVUERlVGYrQXFlMW1CdzFmK09KcXoyUElxSmlDU01VeHpmNzJha2hZM0swRXYyaTdcL2ViSE5qQk0xK0ZSM08yNVFNNlM3ek1GRFwvOCtQUG5Hc0JqVEsrR0JPNDN2N3g4c0xIZGcwZGJXK2JFcGVvZUpsdHhHdjdQSVkxOHNYSHVaUVZpdkVhbGNEV3I3UUU1WUVwT1BSRWdhMTRWWFgyUklZK3FUR3R6aVwvRm9YSW1sRXE4a01ZVFkwc3RwT25ERTVUNGM4TVBkYVM3aUtyaCtvU2JDOFRPT0tPTXhKUFMwTjZEVnNCcGdVMWhYc0x5RHorUWhvajQzT1lXZmp5VSIsIm1hYyI6ImEzMzk4NmE1ZWNmNzYyYTQyYjgzNjY2ODI1M2ViNGUxMTliNmEwNmQzZGU4MThjYTY5YTA2YWE2OWFlOTUyOWQifQ==";

    public static String getCategories () {

        //String url = "http://192.168.0.2:8081/projects/erp/public/category";
        String url = "http://192.168.0.2:8081/projects/erp/public/finishedgoods";

        try {
            myUrl = new URL ( url );

            connection = ( HttpURLConnection ) myUrl.openConnection ();
            connection.setRequestMethod ( REQUEST_METHOD );
            connection.setReadTimeout ( READ_TIMEOUT );
            connection.setConnectTimeout ( CONNECTION_TIMEOUT );
            connection.setDoInput ( true );
            connection.setDoOutput ( true );

            connection.addRequestProperty ( "Authorization" , "encoded " + header );
            connection.addRequestProperty ( "Content-Type" , "application/x-www-form-urlencoded; charset=UTF-8" );

            connection.connect ();

            DataOutputStream request = new DataOutputStream (
                    connection.getOutputStream () );

            request.flush ();
            request.close ();

            connection.connect ();

            InputStreamReader streamReader = new InputStreamReader ( connection.getInputStream () );

            BufferedReader reader = new BufferedReader ( streamReader );
            StringBuilder stringBuilder = new StringBuilder ();

            while ( ( inputLine = reader.readLine () ) != null ) {
                stringBuilder.append ( inputLine );
            }

            reader.close ();
            streamReader.close ();

            result = stringBuilder.toString ();

            JOptionPane.showMessageDialog ( null , result );

        } catch ( Exception ex ) {
             JOptionPane.showMessageDialog ( null , ex.getMessage ()) ;
        
        }

        return result;
    }

    public static String getToken ( String username, String password) {

       String url = "";
       
       
       
        if( ! StaticValues.getApiURL ().equals ( "")  )
        {   try{
                url = "http://"+StaticValues.getApiURL ()+"/projects/erp/public/auth/login?username="+URLEncoder.encode(username, "UTF-8")+"&password="+URLEncoder.encode(password+"", "UTF-8")+"&grant_type=password";
            }catch( UnsupportedEncodingException e){
                    System.out.println ( "" );   
            }
        }else{
            try{
                Connection _con = DriverManager.getConnection ( "jdbc:sqlite:SSDsdIadc_xlp.db" );
                String selectAllSettings = "select * from appSSJ_22154sdl_dsk_021";
                PreparedStatement st = _con.prepareStatement ( selectAllSettings );
                ResultSet rs = st.executeQuery ();
                String apiURL = rs.getString ( "svn" ) ;
                StaticValues.setApiURL ( apiURL );
                rs.close();
                rs = null ;
                st.close();
                st = null ;
                _con.close();
                _con = null ;
                try{
                    url = "http://"+StaticValues.getApiURL ()+"/projects/erp/public/auth/login?username="+URLEncoder.encode(username, "UTF-8")+"&password="+URLEncoder.encode(password+"", "UTF-8")+"&grant_type=password";
                }catch( UnsupportedEncodingException e){
                    System.out.println ( "" );
                }
                
            }catch(  SQLException e   ){
                System.out.println ( "" );
            }
        }
        
        
        /* */
        

        try {
            myUrl = new URL ( url );
        } catch ( MalformedURLException e1 ) {
            System.out.println ( ""+e1.getMessage () );
        }

        try {
            connection = ( HttpURLConnection ) myUrl.openConnection ();
            connection.setRequestMethod ( REQUEST_METHOD );
            connection.setReadTimeout ( READ_TIMEOUT );
            connection.setConnectTimeout ( CONNECTION_TIMEOUT );
            connection.setDoInput ( true );
            connection.setDoOutput ( true );
            connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //connection.addRequestProperty ( "User-Agent" , "Mozilla" );

            
            connection.connect ();

            DataOutputStream request = new DataOutputStream ( connection.getOutputStream () );

            request.flush ();
            request.close ();

            connection.connect ();

            InputStreamReader streamReader = new InputStreamReader ( connection.getInputStream () );

            BufferedReader reader = new BufferedReader ( streamReader );
            StringBuilder stringBuilder = new StringBuilder ();

            while ( ( inputLine = reader.readLine () ) != null ) {
                stringBuilder.append ( inputLine );
            }

            reader.close ();
            streamReader.close ();

            result = stringBuilder.toString ();

     //       JOptionPane.showMessageDialog ( null , result );

            return result;

        } catch ( IOException e2 ) {

            JOptionPane.showMessageDialog ( null , "Login failed, please check connection/verify user details & try again" );
            return e2.getMessage ();

        }

    }

    public static String addCategories () {

        
        String url = "http://'"+StaticValues.getApiURL ()+"'/projects/erp/public/categoryadd?Categoy_ID=rawMaterialCategory1";
        
        
        try {
            myUrl = new URL ( url );

            connection = ( HttpURLConnection ) myUrl.openConnection ();
            connection.setRequestMethod ( REQUEST_METHOD );
            connection.setReadTimeout ( READ_TIMEOUT );
            connection.setConnectTimeout ( CONNECTION_TIMEOUT );
            connection.setDoInput ( true );
            connection.setDoOutput ( true );

            connection.addRequestProperty ( "Authorization" , "encoded " + header );
            connection.addRequestProperty ( "Content-Type" , "application/x-www-form-urlencoded; charset=UTF-8" );

            connection.connect ();

            DataOutputStream request = new DataOutputStream (
                    connection.getOutputStream () );

            request.flush ();
            request.close ();

            connection.connect ();

            InputStreamReader streamReader = new InputStreamReader ( connection.getInputStream () );

            BufferedReader reader = new BufferedReader ( streamReader );
            StringBuilder stringBuilder = new StringBuilder ();

            while ( ( inputLine = reader.readLine () ) != null ) {
                stringBuilder.append ( inputLine );
            }

            reader.close ();
            streamReader.close ();

            result = stringBuilder.toString ();

            JOptionPane.showMessageDialog ( null , result );

        } catch ( Exception ex ) {

        }

        return result;
    }

    
    
    
    /*
     * public static String loginToServer () {
     *
     * try { myUrl = new URL (
     * "http://192.168.0.2:8081/projects/erp/public/auth/login" ); } catch (
     * MalformedURLException ex ) {
     *
     * }
     *
     * try { connection = ( HttpURLConnection ) myUrl.openConnection ();
     * connection.setRequestMethod ( REQUEST_METHOD ); connection.setReadTimeout
     * ( READ_TIMEOUT ); connection.setConnectTimeout ( CONNECTION_TIMEOUT );
     * connection.setRequestProperty ( "Content-Type" ,
     * "application/x-www-form-urlencoded" );
     *
     * connection.setDoInput ( true ); connection.setDoOutput ( true );
     *
     * // List<NameValuePair> params = new ArrayList<NameValuePair>(); //
     * params.add(new BasicNameValuePair( "username","admin" )); //
     * params.add(new BasicNameValuePair( "password","YWRtaW4=" )); //
     * params.add(new BasicNameValuePair( "grant_type","password" )); // //
     * OutputStream os = connection.getOutputStream(); // BufferedWriter writer
     * = new BufferedWriter( // new OutputStreamWriter(os, "UTF-8")); //
     * System.out.println ( ""+params.toString() ); // writer.write(
     * params.toString () ); // writer.flush(); // writer.close(); //
     * os.close(); DataOutputStream request = new DataOutputStream (
     * connection.getOutputStream () );
     *
     * request.writeBytes ( "username=admin" ); request.writeBytes ( "&" );
     * request.writeBytes ( "password=YWRtaW4=" ); request.writeBytes ( "&" );
     * request.writeBytes ( "grant_type=password" );
     *
     * request.flush (); request.close ();
     *
     * connection.connect ();
     *
     * HttpClient httpclient = new DefaultHttpClient ();
     *
     *
     * //Create a new InputStreamReader InputStreamReader streamReader = new
     * InputStreamReader ( connection.getInputStream () );
     *
     * //Create a new buffered reader and String Builder BufferedReader reader =
     * new BufferedReader ( streamReader ); StringBuilder stringBuilder = new
     * StringBuilder ();
     *
     * //Check if the line we are reading is not null while ( ( inputLine =
     * reader.readLine () ) != null ) { stringBuilder.append ( inputLine ); }
     *
     * //Close our InputStream and Buffered reader reader.close ();
     * streamReader.close ();
     *
     * //Set our result equal to our stringBuilder result =
     * stringBuilder.toString ();
     *
     * JOptionPane.showMessageDialog ( null , result );
     *
     * } catch ( IOException ex ) { JOptionPane.showMessageDialog ( null ,
     * ex.getMessage () ); }
     *
     * return ""; }
     *
     * public static String loginToServer2 () throws ClientProtocolException ,
     * IOException {
     *
     * String url = "http://192.168.0.2:8081/projects/erp/public/auth/login?";
     * String charset = "UTF-8"; // Or in Java 7 and later, use the constant:
     * java.nio.charset.StandardCharsets.UTF_8.name() String param1 = "admin";
     * String param2 = "YWRtaW4="; String param3 = "password";
     *
     * String query = String.format ( "username=%s&password=%s&grant_type=%s" ,
     * URLEncoder.encode ( param1 , charset ) , URLEncoder.encode ( param2 ,
     * charset ) , URLEncoder.encode ( param3 , charset ) );
     *
     * HttpURLConnection httpConnection = ( HttpURLConnection ) new URL ( url
     * ).openConnection (); httpConnection.setRequestMethod ( "POST" );
     *
     * URLConnection connection = new URL ( url ).openConnection ();
     * connection.setDoOutput ( true ); // Triggers POST.
     * connection.setRequestProperty ( "Accept-Charset" , charset );
     * connection.setRequestProperty ( "Content-Type" ,
     * "application/x-www-form-urlencoded;charset=" + charset );
     *
     * try ( OutputStream output = connection.getOutputStream () ) {
     * output.write ( query.getBytes ( charset ) ); }
     *
     * InputStream response = connection.getInputStream ();
     *
     * InputStreamReader streamReader = new InputStreamReader ( response );
     *
     * //Create a new buffered reader and String Builder BufferedReader reader =
     * new BufferedReader ( streamReader ); StringBuilder stringBuilder = new
     * StringBuilder ();
     *
     * //Check if the line we are reading is not null while ( ( inputLine =
     * reader.readLine () ) != null ) { stringBuilder.append ( inputLine ); }
     *
     * //Close our InputStream and Buffered reader reader.close ();
     * streamReader.close ();
     *
     * //Set our result equal to our stringBuilder result =
     * stringBuilder.toString ();
     *
     * System.out.println ( "" + result ); return "" + result; }
     *
     * public static String loginToServer3 () {
     *
     * String responseStr = "";
     *
     * //try { HttpClient httpClient = new DefaultHttpClient ();
     *
     * // replace with your url HttpPost httpPost = new HttpPost (
     * "http://192.168.0.2:8081/projects/erp/public/auth/login?username=admin&password=YWRtaW4=&grant_type=password"
     * ); // Login API //StringEntity se = new
     * StringEntity("username=admin&password=YWRtaW4=&grant_type=password");
     *
     * //se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "text/xml"));
     * HttpParams httpParameters = new BasicHttpParams ();
     *
     * int timeoutConnection = 120000; HttpConnectionParams.setConnectionTimeout
     * ( httpParameters , timeoutConnection );
     *
     * int timeoutSocket = 120000; HttpConnectionParams.setSoTimeout (
     * httpParameters , timeoutSocket );
     *
     * httpPost.setParams ( httpParameters );
     *
     * //httpPost.setEntity(se); try {
     *
     * HttpResponse httpResponse = httpClient.execute ( httpPost ); responseStr
     * = httpResponse.getStatusLine ().getReasonPhrase (); //responseStr =
     * httpResponse.getEntity ().getContent ();
     *
     * InputStreamReader streamReader = new InputStreamReader (
     * httpResponse.getEntity ().getContent () );
     *
     * //Create a new buffered reader and String Builder BufferedReader reader =
     * new BufferedReader ( streamReader ); StringBuilder stringBuilder = new
     * StringBuilder ();
     *
     * //Check if the line we are reading is not null while ( ( inputLine =
     * reader.readLine () ) != null ) { stringBuilder.append ( inputLine ); }
     *
     * //Close our InputStream and Buffered reader reader.close ();
     * streamReader.close ();
     *
     * //Set our result equal to our stringBuilder result =
     * stringBuilder.toString ();
     *
     * System.out.println ( "" + result );
     *
     * } catch ( ClientProtocolException e ) { // Log exception
     * e.printStackTrace (); } catch ( IOException e ) { // Log exception
     * e.printStackTrace (); } //	} catch (UnsupportedEncodingException e1) { //
     * // TODO Auto-generated catch block //	e1.printStackTrace(); //	} finally
     * { // //	}
     *
     * return responseStr;
     *
     * }
     *
     * public static String sendRequest () { String params = ""; try { params =
     * "username=" + URLEncoder.encode ( "admin" , "UTF-8" ) + "&password=" +
     * URLEncoder.encode ( "YWRtaW4=" , "UTF-8" ) + "&grant_type=" +
     * URLEncoder.encode ( "password" , "UTF-8" ); } catch (
     * UnsupportedEncodingException ex ) {
     *
     * }
     * try { String request =
     * "http://192.168.0.2:8081/projects/erp/public/auth/login"; URL url = new
     * URL ( request ); HttpURLConnection connection = ( HttpURLConnection )
     * url.openConnection (); connection.setDoOutput ( true );
     * connection.setInstanceFollowRedirects ( false );
     * connection.setRequestMethod ( "POST" ); connection.setRequestProperty (
     * "Content-Type" , "application/x-www-form-urlencoded" );
     * connection.setRequestProperty ( "charset" , "utf-8" ); connection.connect
     * ();
     *
     * OutputStreamWriter outputWriter = new OutputStreamWriter (
     * connection.getOutputStream () ); outputWriter.write ( params );//Write
     * POST Parameters outputWriter.flush (); outputWriter.close ();
     *
     * InputStream response = connection.getInputStream (); InputStreamReader
     * streamReader = new InputStreamReader ( response );
     *
     * BufferedReader reader = new BufferedReader ( streamReader );
     * StringBuilder stringBuilder = new StringBuilder ();
     *
     * while ( ( inputLine = reader.readLine () ) != null ) {
     * stringBuilder.append ( inputLine ); }
     *
     * reader.close (); streamReader.close ();
     *
     * result = stringBuilder.toString ();
     *
     * } catch ( MalformedURLException ex ) {
     *
     * } catch ( IOException ex ) { } return result; }
     */
}

/*
if ( Proman_User.isActivelyLoggedIn () ) {
            

	 } else if ( showLogin () ) {


	 } 
 
  
  
  
 */