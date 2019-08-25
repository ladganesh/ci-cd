/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

import trixware.erp.prodezydesktop.Model.StaticValues;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Graphics2D;


import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author Rajesh
 */
public class Export_Print_Utility {

    static Font titlefontBold = new Font ( Font.FontFamily.TIMES_ROMAN , 16 , Font.BOLD  );
    static Font fontBold = new Font ( Font.FontFamily.TIMES_ROMAN , 14 , Font.BOLD  );
    static Font fontTimesRoman = new Font ( Font.FontFamily.TIMES_ROMAN , 12 , Font.NORMAL , new BaseColor ( 0 , 0 , 0 ) );
    static Paragraph p1;
    public static final String IMG = "src\\Utilities\\assets\\trixware_logo.jpg";
    public static final String COMPANY_LOGO="src\\Utilities\\assets\\company-Logo";
    public static  Image img = null ;
    public static  BufferedImage bimage = null ;
    
    public static String companyname,  plantaddress, gstno , plantemail, plantcpemail, plantcontactno, plantcpcontactno;
    public static byte[] complogo ;
    static Document document = new Document ( PageSize.A4.rotate () , 10 , 10 , 10 , 10 );

    public static void writeChartToPDF ( JFreeChart chart , int width , int height , Vector<Vector<Object>> data , Vector<String> columns , String fileName ) {

//        String path = File.separator + "Policy Reports";
//        File dir = new File ( path );
//        if (  ! dir.exists () ) {
//            dir.mkdirs ();
//        }
//        Long tsLong = System.currentTimeMillis () / 1000;
//        String ts = tsLong.toString ();
//
//        File file = new File ( dir , "pensionplan189_" + ts + ".pdf" );
//        FileOutputStream fileOutputStream = null;
//        try {
//            fileOutputStream = new FileOutputStream ( file );
//        } catch ( FileNotFoundException ex ) {
//            Logger.getLogger ( PDF_189.class.getName () ).log ( Level.SEVERE , null , ex );
//        }
//
//        try {
//            PdfWriter.getInstance ( document , fileOutputStream );
//
//        } catch ( DocumentException e ) {
//            e.printStackTrace ();
//        }
//        document.open ();
//
//        PdfPTable table78 = new PdfPTable ( 3 );
//        table78.setWidthPercentage ( 100 );
//        PdfPCell cell901 = new PdfPCell ();
//        p1 = new Phrase ( "Sachin Gawai\n8888698838" , fontBold );//agent name n mobile
//        cell901.addElement ( p1 );
//        cell901.setHorizontalAlignment ( Element.ALIGN_CENTER );
//        cell901.setBorder ( PdfPCell.NO_BORDER );
//        table78.addCell ( cell901 );
//        try {
//            document.add ( table78 );
//            document.add ( Chunk.NEWLINE );
//
//            LineSeparator lineSeparator = new LineSeparator ();
//            lineSeparator.setOffset ( -2 );
//
//            document.add ( lineSeparator );
//
//        } catch ( DocumentException ex ) {
//            System.out.println ( "Document Exception  "+ex.getMessage () );
//        }
//
//        document.close();


        String ReportHeaderQuery = "select companyname, complogo, plantaddress, gstno, plantcpno, plantcontactno, plantcpemail, plantemailaddress, MAX(plantid) from PLANT_MASTER " ;

        ResultSet rs = DB_Operations.executeSingle ( ReportHeaderQuery) ;
        
        try{
            
            companyname = rs.getString ( "companyname");     
            complogo= rs.getBytes ( "complogo");
            plantaddress= rs.getString ( "plantaddress");
            gstno= rs.getString ( "gstno");
            
             plantemail =        rs.getString ( "plantemailaddress" );
             plantcpemail =     rs.getString ( "plantcpemail");
             plantcontactno =  rs.getString ( "plantcontactno" );
             plantcpcontactno = rs.getString ( "plantcpno" );
            
             img=Image.getInstance(COMPANY_LOGO);
           
            
        }catch(SQLException | IOException | BadElementException e){
            StaticValues.writer.writeExcel ( Export_Print_Utility.class.getSimpleName () , Export_Print_Utility.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }
        
        document = new Document ( PageSize.A4.rotate () , 10 , 10 , 10 , 10 );

        PdfWriter writer = null;

        try{
            File f = new File ( fileName );
            if (  ! f.exists () ) {
                f.createNewFile ();
            }
            writer = PdfWriter.getInstance ( document , new FileOutputStream ( fileName ) );
            document.open ();
            
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
        PdfPTable table = new PdfPTable ( 3 );
        table.setWidthPercentage ( 100 );
        table.setWidths ( new float[]{20, 40, 40});
        table.setHorizontalAlignment ( Element.ALIGN_CENTER );
        PdfPCell cell901 = null;
        Paragraph p = null;

            //Adding cells to the table
            
            
            
            
            cell901 = new PdfPCell ();
            cell901.setRowspan ( 4);
            cell901.setHorizontalAlignment ( Element.ALIGN_CENTER );
            cell901.setVerticalAlignment ( Element.ALIGN_TOP );
            cell901.addElement (img);
            table.addCell ( cell901 );
            
            p = new Paragraph ( companyname , titlefontBold );
            cell901 = new PdfPCell ();
            cell901.setColspan ( 2);
            cell901.setHorizontalAlignment ( Element.ALIGN_CENTER );
            cell901.setVerticalAlignment ( Element.ALIGN_TOP );
            cell901.setFixedHeight ( 50f);
            cell901.setPadding ( 5f);
            cell901.addElement ( p );
            table.addCell ( cell901 );
  
            p = new Paragraph ( "Address :  "+plantaddress , fontBold );
            cell901 = new PdfPCell ();
            cell901.setRowspan ( 2);
            cell901.setHorizontalAlignment ( Element.ALIGN_CENTER );
            cell901.setVerticalAlignment ( Element.ALIGN_TOP );
            cell901.addElement ( p );
            table.addCell ( cell901 );
            
             p = new Paragraph ( "Email :  "+plantemail+", "+plantcpemail , fontBold );
            cell901 = new PdfPCell ();
            cell901.setHorizontalAlignment ( Element.ALIGN_CENTER );
            cell901.setVerticalAlignment ( Element.ALIGN_TOP );
            cell901.addElement ( p );
            table.addCell ( cell901 );
            
            p = new Paragraph ( "Phone : "+plantcontactno+", "+plantcpcontactno , fontBold );
            cell901 = new PdfPCell ();
            cell901.setHorizontalAlignment (  Element.ALIGN_TOP  );
            cell901.setVerticalAlignment ( Element.ALIGN_CENTER );
            cell901.addElement ( p );
            table.addCell ( cell901 );
            
            p = new Paragraph ( "GSTNo :  "+gstno , fontBold );
            cell901 = new PdfPCell ();
             cell901.setColspan ( 2);
            cell901.setHorizontalAlignment (  Element.ALIGN_TOP  );
            cell901.setVerticalAlignment ( Element.ALIGN_CENTER );
            cell901.addElement ( p );
            table.addCell ( cell901 );
            
          
  
        try {
            //Adding Table to document
            document.add ( table );
        } catch ( DocumentException ex ) {
            try{
            StaticValues.writer.writeExcel ( Export_Print_Utility.class.getSimpleName () , Export_Print_Utility.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }catch(Exception e){System.err.println(e);}
        }
                    
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
            
            

            PdfContentByte contentByte = writer.getDirectContent ();
            PdfTemplate template = contentByte.createTemplate ( width , height );
            Graphics2D graphics2d = template.createGraphics ( width , height ,
                    new DefaultFontMapper () );
            Rectangle2D rectangle2d = new Rectangle2D.Double ( 0 , 0 , width ,
                    height );

            chart.draw ( graphics2d , rectangle2d );

            graphics2d.dispose ();
            contentByte.addTemplate ( template , 0 , 0 );

            document.newPage ();

        } catch ( FileNotFoundException ex ) {
            //Logger.getLogger ( Export_Print_Utility.class.getName () ).log ( Level.SEVERE , null , ex );
            StaticValues.writer.writeExcel ( Export_Print_Utility.class.getSimpleName () , Export_Print_Utility.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } catch ( DocumentException ex ) {
            //Logger.getLogger ( Export_Print_Utility.class.getName () ).log ( Level.SEVERE , null , ex );
                        StaticValues.writer.writeExcel ( Export_Print_Utility.class.getSimpleName () , Export_Print_Utility.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );

        } catch ( IOException ex ) {
//            Logger.getLogger ( Export_Print_Utility.class.getName () ).log ( Level.SEVERE , null , ex );
            StaticValues.writer.writeExcel ( Export_Print_Utility.class.getSimpleName () , Export_Print_Utility.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );

        }

        PdfPTable table = new PdfPTable ( columns.size () );
        table.setWidthPercentage ( 100 );
        PdfPCell cell901 = null;
        Paragraph p = null;

        for ( int i = 0 ; i < columns.size () ; i ++ ) {
            //Adding cells to the table 
            p = new Paragraph ( columns.get ( i ) , fontBold );
            p.setAlignment ( Element.ALIGN_CENTER | Element.ALIGN_TOP);
            cell901 = new PdfPCell ();
            cell901.setHorizontalAlignment ( Element.ALIGN_CENTER );
            cell901.setVerticalAlignment ( Element.ALIGN_MIDDLE );
            cell901.setPadding (4f);
            cell901.setFixedHeight ( 30f);
            cell901.addElement ( p );

            //    cell901.setBorder ( PdfPCell.NO_BORDER );
            table.addCell ( cell901 );
        }

        for ( int i = 0 ; i < data.size () ; i ++ ) {

            Vector<Object> row = data.get ( i );

            for ( int j = 0 ; j < row.size () ; j ++ ) {
                //Adding cells to the table 
                try {
                    p = new Paragraph ( row.get ( j ).toString () , fontTimesRoman );
                } catch ( NullPointerException e ) {
                    p = new Paragraph ( "--" , fontTimesRoman );
                                StaticValues.writer.writeExcel ( Export_Print_Utility.class.getSimpleName () , Export_Print_Utility.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
                }

                cell901 = new PdfPCell ();
                cell901.setHorizontalAlignment ( Element.ALIGN_CENTER );
                cell901.setVerticalAlignment ( Element.ALIGN_MIDDLE );
                cell901.setFixedHeight ( 25f);
                cell901.addElement ( p );
                //     cell901.setBorder ( PdfPCell.NO_BORDER );
                table.addCell ( cell901 );
            }
        }

        try {
            //Adding Table to document
            document.add ( table );
        } catch ( DocumentException ex ) {
                StaticValues.writer.writeExcel ( Export_Print_Utility.class.getSimpleName () , Export_Print_Utility.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }
        document.close ();
    }

    static class Rotate extends PdfPageEventHelper {

        protected PdfNumber orientation = PdfPage.PORTRAIT;

        public void setOrientation ( PdfNumber orientation ) {
            this.orientation = orientation;
        }

        @Override
        public void onStartPage ( PdfWriter writer , Document document ) {
            writer.addPageDictEntry ( PdfName.ROTATE , orientation );
        }
    }
}