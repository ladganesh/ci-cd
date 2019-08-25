/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Rajesh
 */

public class Test {
    
   static Connection con = null ;
    
    public static void main(String[] arg){
        
 /*        try{
//            Class.forName("oracle.jdbc.driver.OracleDriver");  
//            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:8888:production","system","mp465901");
//            System.out.println("Conn create sucessfully");
            
//            PreparedStatement ps = con.prepareStatement ( "CREATE TABLE mytable (ID number(5), NAME varchar(50));");
//            ps.execute ();


         con = DriverManager.getConnection("jdbc:sqlite:srirubber-v3.db");

         PreparedStatement ps = null ;
         for( int i =1521 ; i<=1776; i++ ){
             ps = con.prepareStatement("update dailyreport set qtyin = qtyout where qtyin = '#N/A' " );
             ps.executeUpdate ();
             
             
             
             System.out.println(i) ;
         }

        }catch(SQLException e){
            System.out.println("Conn not create 1 "+e.getMessage ());  
        }
        * 
        * 
        * */
 
      //      getData();
 
       //     postData();
       //     getData();
       
      // getSingleData ();
      updateData();
    }
 
    public static void getData(){
        
        
           try {

		URL url = new URL("http://localhost:8080/myuser");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }

    }
    
    public static void postData(){
         try {
                                URL url = new URL("http://localhost:8080/myuser");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		String input = "{\"name\":\"Atul Ghumare\",\"email\":\"atul@gmail.com\",\"phone\":\"123456795\"}";

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();

                        
                
	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 }
    }
    
    
    public static void getSingleData(){
        
        
           try {

		URL url = new URL("http://localhost:8080/myuser/13");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");

                
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }

    }
    
    
    public static void updateData(){
         try {
                                URL url = new URL("http://localhost:8080/myuser/15");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");

		String input = "{\"name\":\"PR\",\"email\":\"rp@gmail.com\",\"phone\":\"0000000000\"}";

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();

                        
                
	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 }
    }
    
}
