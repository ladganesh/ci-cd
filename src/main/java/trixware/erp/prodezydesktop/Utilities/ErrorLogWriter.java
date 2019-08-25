/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

import trixware.erp.prodezydesktop.Model.StaticValues;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Rajesh
 */
 public class ErrorLogWriter {

    File file = new File ( System.getProperty ( "user.home" ) + "\\AppData\\Local\\Prodezy\\"+"app_log.xls" );
    Workbook wb = new HSSFWorkbook ();
    FileOutputStream os = null;
    Cell c = null;
    Row row4 = null ;
    FileWriter fw = null;
    Sheet sheet1 = null;
        

    LogManager lm = LogManager.getLogManager ();
    
    Logger logger
            = lm.getLogger ( ErrorLogWriter.class.getName () );

    public void write ( String data ) {
        try {
            fw = new FileWriter ( new File ( System.getProperty ( "user.home" ) + "\\AppData\\Local\\Prodezy\\app_log.txt" ) );
            fw.write ( data );
            fw.close ();
        } catch ( IOException e ) {
            //logger.fatal ( "Unable to open file." , e );
            StaticValues.writer.writeExcel ( ErrorLogWriter.class.getSimpleName () , ErrorLogWriter.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }
    }
        
    public void writeExcel ( String className, String filename, String exceptionName, String lineNo, String msg, String datetime ) {
      try{
        FileInputStream fis = null ;
        try{
             fis = new FileInputStream ( new File ( System.getProperty ( "user.home" ) + "\\AppData\\Local\\Prodezy\\app_log.xls" ) );
       
//            wb = new HSSFWorkbook ( fis );
        } catch ( IOException ex ) {
           // writeExcel ( ErrorLogWriter.class.getSimpleName () , ErrorLogWriter.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }
        
        sheet1 = wb.getSheet( "app_log" );
        //sheet1 = wb.getSheetAt( 1 );
        //sheet1.setColumnWidth ( 0 , ( 15 * 500 ) );
        
        row4 = sheet1.createRow ( sheet1.getLastRowNum ()+1 );
        
        c = row4.createCell ( 0 );
        c.setCellValue ( className );

        c = row4.createCell ( 1 );
        c.setCellValue ( filename );

        c = row4.createCell ( 2 );
        c.setCellValue ( exceptionName );

        c = row4.createCell ( 3 );
        c.setCellValue ( lineNo );

        c = row4.createCell ( 4 );
        c.setCellValue ( msg );

        c = row4.createCell ( 5 );
        c.setCellValue ( datetime );


        try {
            os = new FileOutputStream ( file );
            wb.write ( os );
            wb.close ();
            os.close ();

        } catch ( IOException e ) {
            JOptionPane.showMessageDialog ( null , "Error " + e.getMessage () );
            StaticValues.writer.writeExcel ( ErrorLogWriter.class.getSimpleName () , ErrorLogWriter.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } finally {

        }
      }catch(Exception e)
      {
          System.err.println(e);
      }
    }
}
