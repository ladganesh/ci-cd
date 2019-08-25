/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rajesh
 */
public class Test1 {
    
    public static void main(String[] arg){
        
        try{
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:8090;" +  
   "databaseName=mydatabase;user=rajeshp;password=mp465901;");
            System.out.println("Conn create sucessfully");
            
//            PreparedStatement ps = con.prepareStatement ( "CREATE TABLE mytable (ID number(5), NAME varchar(50));");
//            ps.execute ();

            PreparedStatement ps = con.prepareStatement( "insert into mytable_one ( ID, NAME, EMAIL, PHONE ) values ( "+1+", 'Rajesh Pahadi','rajesh.pahadi@gmail.com', 9960535665  )" );
            ps.executeUpdate ();
            
            
            ps = con.prepareStatement( "select * from mytable_one"  );
            ResultSet rs  = ps.executeQuery ();
            
            String name = "", email = "", phone = "";
            
            while( rs.next() ){
                
                    name = rs.getString ( 2 );
                    email = rs.getString ( 3);
                    phone = String.valueOf(rs.getInt( 4 ));
            }
            
            StringBuilder sb = null ;
            
            sb = new StringBuilder();
            sb.append ( "The name : "+name).append ( "\r\n").append( "Email address : " + email );
            System.out.println( sb.toString ()  );

        }catch(SQLException e){
            System.out.println("Conn not create 1 "+e.getMessage ());  
        } catch ( ClassNotFoundException ex ) {
            Logger.getLogger ( Test1.class.getName() ).log ( Level.SEVERE , null , ex );
        }
    }
    
}
