/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.*;
import java.util.*;
import trixware.erp.prodezydesktop.Model.StaticValues;

/**
 *
 * @author Rajesh
 */
public class ImportExcel {

    
    public static void main ( String[] arg ) {

        
        
       
        
        
        ///////////////////////////////////////////////////////////////////////////////////////////////                   
        String url;
        try {

            url = "jdbc:sqlite:"+StaticValues.dbName;
            Connection con = DriverManager.getConnection ( url );
            Statement stm = con.createStatement ();
            PreparedStatement pst;

            
            
            
            FileInputStream fis;
            //String query = "INSERT INTO xl_import_test ( ID, name, address, phone, email) VALUES ( ?,?,?,?,?)";
            String query = "INSERT INTO FG_PROCESS_MACH_MAP (  FPM_ID,REF_FG_ID,REF_PROCESS_ID,REF_MCH_ID,SETUP_TIME,IDEAL_PROCESS_TIME,REMARKS,TG_OPT_HR,TG_OPT_SHT,TG_OPT_DAY,UOM_ID,VAL_AT_PROC ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?)";
            
            pst = con.prepareStatement ( query );

            
            fis = new FileInputStream ( new File ( "Process Map.xls" ) );

            HSSFWorkbook my_xls_workbook = new HSSFWorkbook ( fis );
            HSSFSheet my_worksheet = my_xls_workbook.getSheetAt ( 0 );

            Iterator<Row> rowIterator = my_worksheet.iterator ();
            while ( rowIterator.hasNext () ) {
                Row row = rowIterator.next ();
                Iterator<Cell> cellIterator = row.cellIterator ();
                int newCell = 1 ;
                while ( cellIterator.hasNext () ) {
                    Cell cell = cellIterator.next ();
                       try {
                            pst.setString ( newCell , cell.getStringCellValue () );
                            
                        } catch ( Exception e ) {
                            pst.setInt ( newCell , ( int ) Math.round ( ( cell.getNumericCellValue () ) ) );
                            
                        }
                        newCell++ ;
                }                
              //  pst.addBatch ();
            }
            
            int[] totalRecords = new int[ 2 ];
//            try {
//                totalRecords = pst.executeBatch ();
//            } catch ( BatchUpdateException e1 ) {
//                totalRecords = e1.getUpdateCounts ();
//            }
//            System.out.println ( "Total record Inserted " + totalRecords.length );
            fis.close ();
            pst.close ();

        } catch ( SQLException ex ) {
            System.out.println ( "SQL  " + ex.getMessage () );
        } catch ( IOException ex ) {
            System.out.println ( "IO  " + ex.getMessage () );
        }
        /////////////////////////////////////////////////////////////////////////////////////////////// 

    }

}

//Psudo Code for importing and exproting data to excel sheets - Replacing ids by values while exporting or importing excel / CSV

// 1.   Save all master values in arraylists while initializing the import or export excel functionality
// 2.   For import
// 2.1 Save all fields from excel to sql tabel as it is
// 2.2 Identify or predefine the columns that have ID values
// 2.3 Run loop for all columns having keys; replace corresponding id values with their respective column values wherever its matching
// 2.4 Show the resulting table to the user as a preview and review purpose
// 2.5 Provide approve / update  / finish button to make the table take effect
// 
// 