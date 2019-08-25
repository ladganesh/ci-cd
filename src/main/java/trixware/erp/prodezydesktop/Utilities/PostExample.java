/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

/**
 *
 * @author Rajesh
 */
import java.net.URL;
import java.net.URLEncoder;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
  
//import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;
  
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
  
public class PostExample
{
Hashtable<String, String> parameters;
     
public PostExample()
{
parameters = new Hashtable<String, String>();
}
 
    public void makeRequest(String urlString)
    {
    try
    {
        URL url;
         
    url = new URL(urlString);
     
    addUrlParameter("url", urlString);
    addUrlParameter("username", "admin");
    addUrlParameter("&password", "YWRtaW4");
    addUrlParameter("&grant_type", "password");
     
           System.out.println("Sending request...");
           
           String urlParameters = buildUrlParameters();
            
           System.out.println(urlParameters);
            
    URLConnection conn = url.openConnection();
           HttpURLConnection httpConn = (HttpURLConnection)conn;
            
           httpConn.setRequestMethod("POST");
           //conn.setDoInput(true);
           httpConn.setDoOutput(true);
        httpConn.setUseCaches(false);
         
        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        httpConn.setRequestProperty("Content-Type", "multipart/form-data");
        //httpConn.setRequestProperty("Content-Length", Integer.toString( urlParameters.length() ) );
       
        OutputStreamWriter osw = new OutputStreamWriter( httpConn.getOutputStream(), "UTF-8");
         
        osw.write(urlParameters);
            osw.flush();
             
            //get the response
           System.out.println("Getting response...");
            
            BufferedReader br = new BufferedReader( new InputStreamReader( conn.getInputStream() ) ); 
     
        String line = null;
         
        while ( (line = br.readLine() ) != null)
        {
        System.out.println(line);
        }
    }
    catch(MalformedURLException murlex)
    {
    murlex.printStackTrace();
    }
    catch(IOException ioex)
    {
    ioex.printStackTrace();
    }
    }
     
    public void addUrlParameter(String parameterKey, String parameterValue)
    {   
        try
        {        
        String encodedKey = URLEncoder.encode(parameterKey, "UTF-8");
        String encodedValue = URLEncoder.encode(parameterValue, "UTF-8");
         
        System.out.println(parameterKey + "=" + parameterValue + " : " + encodedKey + "=" + encodedValue);
         
        //parameters.put(encodedKey, encodedValue);
        parameters.put(parameterKey, parameterValue);
        }
        catch(UnsupportedEncodingException ueex)
        {
        ueex.printStackTrace();
        }
    }
     
    public String buildUrlParameters()
    {
    String urlParameters = "";
     
        Enumeration enumeration = parameters.keys();
     
        String key = null;
        String value = null;
        String pair = null;
         
        boolean isFirstData = true;
         
        while ( enumeration.hasMoreElements() )
        {
        key = (String)enumeration.nextElement();
        value = parameters.get(key);
         
        pair = key + "=" + value;
         
        if (isFirstData) //isFirstData = false;
        {
        urlParameters += pair;
        }
        else
        {
            urlParameters += "&" + pair;
        }
         
        isFirstData = false;
        }
         
     
    return urlParameters;
    }
     
    public static void main(String[] args)
    {
    PostExample p = new PostExample();
    p.makeRequest("http://localhost:8081/projects/erp/public/auth/login");
    }
}
