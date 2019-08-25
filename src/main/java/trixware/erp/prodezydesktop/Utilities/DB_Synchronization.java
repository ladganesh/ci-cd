/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

import trixware.erp.prodezydesktop.Model.StaticValues;
import static trixware.erp.prodezydesktop.Utilities.DB_Operations.createTriggerWIPfromDR;
import static trixware.erp.prodezydesktop.Utilities.DB_Operations.createView_RM_ABC_Analysis;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import javax.swing.JOptionPane;

/**
 *
 * @author Rajesh
 *
 *
 * This class should check if there is any pre-existing databases on the
 * dedicated databases folder path If yes, then it should check for each and
 * every database table whether there are all fields available as per existing
 * DB schema or not If not, then that particular table should be updated with
 * the fields not already available in it While this operation is in progress
 * user should be notified using a progress dialog, if possible percentage
 * completion status of the work being done
 *
 */
public class DB_Synchronization {

    StringBuilder errorString = null;

    public DB_Synchronization ( String dbName ) {

        errorString = new StringBuilder ();

        try {
            //  1.  Check if the database exists with the same name that has been selected / created by the user
            Connection _con = DriverManager.getConnection ( "jdbc:sqlite:" + dbName );

            //  2.  Get the list of all tables in the currently connected database 
            Statement st = _con.createStatement ();
            ResultSet rs = st.executeQuery ( "SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%' order by name" );
            ResultSet rs2 = null;
            Statement st2 = _con.createStatement ();
            //  3.  Check the number of columns is correponding tables and the latest count of fields  
            ArrayList<String[]> listOfTables = new ArrayList<String[]> ();
            String tableName = null;
            String columnsList = null;
            String[] listOfColumns = null;

            if ( rs != null && rs.isBeforeFirst () ) {
                while ( rs.next () ) {

                    // 4. get the name of first table from result and get all field for same from existing database and corresponding table
                    tableName = rs.getString ( 1 );

                    StringBuilder abc = new StringBuilder ();
                    abc.append ( tableName );
                    abc.append ( "," );
                    rs2 = st2.executeQuery ( "PRAGMA table_info(" + tableName + ")" );
                    if ( rs2 != null && rs2.isBeforeFirst () ) {
                        while ( rs2.next () ) {
                            abc.append ( rs2.getString ( "name" ) );
                            abc.append ( "," );
                        }
                        columnsList = abc.toString ();
                        //    columnsList = columnsList.replace ( columnsList.charAt ( columnsList.length ()-1), ' ');
                    }

                    try {
                        rs2.close ();
                    } catch ( Exception e ) {
                        System.out.println ( "" + e.getMessage () );        
                        StaticValues.writer.writeExcel ( DB_Synchronization.class.getSimpleName () , DB_Synchronization.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }

                    listOfTables.add ( columnsList.split ( "," ) );
                }

            }

            try {
                rs.close ();
                _con.close ();
            } catch ( Exception e ) {
                System.out.println ( "" + e.getMessage () );
                StaticValues.writer.writeExcel ( DB_Synchronization.class.getSimpleName () , DB_Synchronization.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }

            //5.  Check if the count of tables in present database is equal to the count of tables as per new installation version
            if ( listOfTables.size () < StaticValues.DBTablesDesc.size () ) {
                JOptionPane.showMessageDialog ( null , "Database update required. Please wait few seconds " );
            }

            // which tables are missing ?  Populate the list over here
            for ( int i = 0 ; i < StaticValues.DBTablesDesc.size () ; i ++ ) {

                int j;

                for ( j = 0 ; j < listOfTables.size () ; j ++ ) {

                    if ( StaticValues.DBTablesDesc.get ( i )[ 1 ].equalsIgnoreCase ( listOfTables.get ( j )[ 0 ] ) ) {

                        String[] table1 = StaticValues.DBTablesDesc.get ( i );
                        String[] table2 = listOfTables.get ( j );

                        if (  ! Arrays.equals ( table1 , table2 ) ) {
                            findMissing ( StaticValues.DBTablesDesc.get ( i )[ 1 ] , table1 , table2 , table1.length , table2.length );
                        }

                        break;
                    }
                }

                if ( j == listOfTables.size () ) {
                    try {
                        // if a column is missing from a table it will show here. If required an alter table query can be fired 

                        con = DriverManager.getConnection ( "jdbc:sqlite:" + StaticValues.dbName );
                        String queryAlterTable = StaticValues.DBTablesDesc.get ( i )[ 0 ];
                        Statement st1 = con.createStatement ();
                        st1.executeUpdate ( queryAlterTable );

                        st.close ();

                        con.close ();
                    } catch ( SQLException e ) {
                        System.out.println ( "Error while creating new table " + e.getMessage () );
                        StaticValues.writer.writeExcel ( DB_Synchronization.class.getSimpleName () , DB_Synchronization.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                    }
                }
            }

            _con = DriverManager.getConnection ( "jdbc:sqlite:" + dbName );
            st = _con.createStatement ();
            rs = st.executeQuery ( "SELECT name FROM sqlite_master WHERE type ='view' AND name NOT LIKE 'sqlite_%' order by name" );

            if (  ! rs.isBeforeFirst () ) {
                st.executeUpdate ( createView_RM_ABC_Analysis );
            }
            rs.close ();

            rs = st.executeQuery ( "SELECT name FROM sqlite_master WHERE type ='trigger' AND name NOT LIKE 'sqlite_%' order by name" );
            if (  ! rs.isBeforeFirst () ) {
                st.executeUpdate ( createTriggerWIPfromDR );
            }
            rs.close ();

            st.close ();

            //  JOptionPane.showMessageDialog (null, errorString.toString ());
        } catch ( SQLException e ) {
            System.out.println ( "" + e.getMessage () );
            StaticValues.writer.writeExcel ( DB_Synchronization.class.getSimpleName () , DB_Synchronization.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }
    }

    static Connection con;

    static void findMissing ( String tableName , String a[] , String b[] ,
            int n , int m ) {
        try {

            for ( int i = 2 ; i < n ; i ++ ) {
                int j;

                for ( j = 1 ; j < m ; j ++ ) {
                    if ( a[ i ].equalsIgnoreCase ( b[ j ] ) ) {
                        break;
                    }
                }

                if ( j == m ) {

                    con = DriverManager.getConnection ( "jdbc:sqlite:" + StaticValues.dbName );
                    // if a column is missing from a table it will show here. If required an alter table query can be fired 
                    String queryAlterTable = "ALTER TABLE " + tableName + " ADD COLUMN `" + a[ i ] + "` TEXT ;";
                    Statement st = con.createStatement ();
                    st.executeUpdate ( queryAlterTable );
                    System.out.println ( a[ i ] + " " + queryAlterTable );

                    st.close ();
                    con.close ();
                }
            }
        } catch ( SQLException e ) {
            System.out.println ( "Error while updaing table" );
            StaticValues.writer.writeExcel ( DB_Synchronization.class.getSimpleName () , DB_Synchronization.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }
    }
}
