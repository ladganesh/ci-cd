/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author Rajesh
 */
public class PDF_189 {

    
    static Font fontBold = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD, new BaseColor(0, 0, 0));
    Font fontTimesRoman = new Font(Font.FontFamily.TIMES_ROMAN, 13);
   static Phrase p1;
  

    public static PdfPCell createImageCell(Image path) throws DocumentException , IOException {
        Image img = Image.getInstance(path);
        PdfPCell cell = new PdfPCell(img, true);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setPaddingTop(5);
        cell.setPaddingRight(5);
        cell.setPaddingBottom(5);
        cell.setPaddingLeft(5);
        return cell;
    }

    //Generate retirement pdf

    public static void test( ) {

        
            //String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Premium_calculator";
        	String path = File.separator+"Policy Reports";
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            Long tsLong = System.currentTimeMillis() / 1000;
            String ts = tsLong.toString();
           
            File file = new File(dir, "pensionplan189_" + ts + ".pdf");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch ( FileNotFoundException ex ) {
                Logger.getLogger ( PDF_189.class.getName() ).log ( Level.SEVERE , null , ex );
            }
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, fileOutputStream);


            } catch (DocumentException e) {
                e.printStackTrace();
            }
            document.open();

            PdfPTable table78 = new PdfPTable(3);
            table78.setWidthPercentage(100);
            PdfPCell cell901 = new PdfPCell();
             p1 = new Phrase("Sachin Gawai\n8888698838", fontBold);//agent name n mobile
            cell901.addElement(p1);
            cell901.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell901.setBorder(PdfPCell.NO_BORDER);
            table78.addCell(cell901);
        try {   
            document.add(table78);
            document.add(Chunk.NEWLINE);
            
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setOffset(-2);
       
            document.add(lineSeparator);
            
            
        } catch ( DocumentException ex ) {
            Logger.getLogger ( PDF_189.class.getName() ).log ( Level.SEVERE , null , ex );
        }
        
                document.close();
              
        
            /*
             * PdfPTable table78 = new PdfPTable(3);
             * table78.setWidthPercentage(100);
             * PdfPCell cell901 = new PdfPCell();
             * Phrase p1 = new Phrase("Sachin Gawai\n8888698838", fontone);//agent name n mobile
             * cell901.addElement(p1);
             * cell901.setHorizontalAlignment(Element.ALIGN_CENTER);
             * cell901.setBorder(PdfPCell.NO_BORDER);
             * table78.addCell(cell901);
             * //
             * PdfPCell cell90 = new PdfPCell();
             * 
             * cell90.setHorizontalAlignment(Element.ALIGN_CENTER);
             * cell90.setBorder(PdfPCell.NO_BORDER);
             * table78.addCell(cell90);
             * table78.addCell(createImageCell(myImgtwo));
             * document.add(table78);
             * document.add(Chunk.NEWLINE);
             * document.add(Chunk.NEWLINE);
             * document.add(Chunk.NEWLINE);
             * document.add(Chunk.NEWLINE);
             * document.add(Chunk.NEWLINE);
             * LineSeparator lineSeparator = new LineSeparator();
             * lineSeparator.setOffset(-2);
             * document.add(lineSeparator);
             * 
             * document.add(Chunk.NEWLINE);
             * document.add(Chunk.NEWLINE);
             * document.add(Chunk.NEWLINE);
             * document.add(Chunk.NEWLINE);
             * document.add(Chunk.NEWLINE);
             * document.add(Chunk.NEWLINE);
             * 
             * PdfPTable innertable = new PdfPTable(2);
             * innertable.setWidths(new int[]{50, 20});
             * PdfPCell cell = new PdfPCell(new Phrase(agentPara));
             * cell.setBorder(Rectangle.NO_BORDER);
             * cell.setHorizontalAlignment(Element.ALIGN_BOTTOM);
             * innertable.addCell(cell);
             * 
             * cell.setColspan(2);
             * 
             * cell = new PdfPCell((myImgtwo));
             * cell.setPaddingLeft(2);
             * cell.setBorder(Rectangle.NO_BORDER);
             * cell.setColspan(2);
             * innertable.addCell(cell);
             * 
             * 
             * PdfPTable clienttbl = new PdfPTable(2);
             * PdfPCell clientcell = new PdfPCell(new Phrase(husnamePara));
             * clientcell.setHorizontalAlignment(Element.ALIGN_LEFT);
             * clientcell.disableBorderSide(Rectangle.BOX);
             * clienttbl.addCell(clientcell);
             * clientcell = new PdfPCell(new Phrase(wifenamePara));
             * clientcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
             * clientcell.disableBorderSide(Rectangle.BOX);
             * clienttbl.addCell(clientcell);
             * clientcell = new PdfPCell(new Phrase(huscurragePara));
             * clientcell.setHorizontalAlignment(Element.ALIGN_LEFT);
             * clientcell.disableBorderSide(Rectangle.BOX);
             * clienttbl.addCell(clientcell);
             * clientcell = new PdfPCell(new Phrase(wifecurragePara));
             * clientcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
             * clientcell.disableBorderSide(Rectangle.BOX);
             * clienttbl.addCell(clientcell);
             * clientcell = new PdfPCell(new Phrase(husretireagePara));
             * clientcell.setHorizontalAlignment(Element.ALIGN_LEFT);
             * clientcell.disableBorderSide(Rectangle.BOX);
             * clienttbl.addCell(clientcell);
             * 
             * clientcell = new PdfPCell(new Phrase());
             * clientcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
             * clientcell.disableBorderSide(Rectangle.BOX);
             * clienttbl.addCell(clientcell);
             * 
             * Paragraph clienttab = new Paragraph();
             * // clienttab.setPaddingTop(1000);
             * clienttab.add(clienttbl);
             * document.add(clienttab);
             * document.add(Chunk.NEWLINE);
             * document.add(Chunk.NEWLINE);
             * 
             * 
             * double rtfdsumassured = sumassured;
             * // int rtfdemergencyfund = retirementFoodArrayList.get(0).getRetfdEmergencyfund();
             * 
             * int rtfdterm = term;
             * 
             * double sumone = 0;
             * long netpremiumone = 0;
             * long nettaxsaveone = 0;
             * double taxsaveone = 0;
             * long taxsavingone =0;
             * double premiumone=0;
             * Paragraph namePara = new Paragraph();
             * 
             * 
             * float[] columnWidthprem = {1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};
             * PdfPTable tableprem = new PdfPTable(columnWidthprem);
             * tableprem.setWidthPercentage(110f);
             * 
             * insertCell(tableprem, "Year", Element.ALIGN_CENTER, 1, fontBold);
             * insertCell(tableprem, "Age", Element.ALIGN_CENTER, 1, fontBold);
             * insertCell(tableprem, "Normal Riscover", Element.ALIGN_CENTER, 1, fontBold);
             * insertCell(tableprem, "Accident Riscover", Element.ALIGN_CENTER, 1, fontBold);
             * insertCell(tableprem, "Annual Premium", Element.ALIGN_CENTER, 1, fontBold);
             * insertCell(tableprem, "Tax Saved", Element.ALIGN_CENTER, 1, fontBold);
             * insertCell(tableprem, "Net Premium", Element.ALIGN_CENTER, 1, fontBold);
             * insertCell(tableprem, "LIC Returns", Element.ALIGN_CENTER, 1, fontBold);
             * insertCell(tableprem, "Surrender Value", Element.ALIGN_CENTER, 1, fontBold);
             * insertCell(tableprem, "Loan Value", Element.ALIGN_CENTER, 1, fontBold);
             * tableprem.setHeaderRows(1);
             * SimpleDateFormat dateFormat = new SimpleDateFormat(
             * "yyyy", Locale.getDefault());
             * Date date = new Date();
             * int current_year = Integer.parseInt(dateFormat.format(date));
             * double annual_premium = 0;
             * double accidental_riskcover = 0;
             * int  current_age=0;
             * 
             * int loopcounter = 100 - age;
             * for (int i = 1; i <= loopcounter+1; i++) {
             * current_age=i;
             * insertCell(tableprem, "" + (current_year), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * insertCell(tableprem, "" + (age), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * 
             * insertCell(tableprem, "" + (Math.round(rtfdsumassured)), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * 
             * 
             * 
             * accidental_riskcover = rtfdsumassured;
             * insertCell(tableprem, "" + (Math.round(accidental_riskcover)), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * 
             * if (i == 1) {
             * premiumone=premium;
             * insertCell(tableprem, "" + (Math.round(premiumone)), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * if (premiumone <= 150000) {
             * taxsaveone = premiumone * 30 / 100;
             * insertCell(tableprem, "" + (Math.round(taxsaveone)), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * nettaxsaveone += taxsaveone;
             * } else {
             * taxsavingone = 150000 * 30 / 100;
             * insertCell(tableprem, "" + (Math.round(taxsavingone)), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * nettaxsaveone += taxsavingone;
             * }
             * } else {
             * 
             * insertCell(tableprem, "0", Element.ALIGN_CENTER, 1, fontTimesRoman);
             * insertCell(tableprem, "0", Element.ALIGN_CENTER, 1, fontTimesRoman);
             * }
             * 
             * 
             * if(i==1){
             * insertCell(tableprem, "" + (Math.round(premiumone - taxsaveone)), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * insertCell(tableprem, "" + (0), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * 
             * }else{
             * insertCell(tableprem, "" + (0), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * insertCell(tableprem, "" + (Math.round(annuity)), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * 
             * }
             * 
             * insertCell(tableprem, "" + (Math.round(0)), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * insertCell(tableprem, "" + (Math.round(0)), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * age++;
             * current_year++;
             * 
             * netpremiumone += premiumone;
             * 
             * }
             * 
             * 
             * 
             * insertCell(tableprem, "" + (""), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * insertCell(tableprem, "" + (""), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * insertCell(tableprem, "" + (""), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * insertCell(tableprem, "" + ("Total"), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * insertCell(tableprem, "" + (Math.round(premium)), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * insertCell(tableprem, "" + (nettaxsaveone), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * insertCell(tableprem, "" + (Math.round(premiumone - taxsaveone)), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * insertCell(tableprem, "" + (Math.round(annuity*current_age-1)), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * insertCell(tableprem, "" + (""), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * insertCell(tableprem, "" + (""), Element.ALIGN_CENTER, 1, fontTimesRoman);
             * 
             * 
             * Paragraph hlvTable = new Paragraph();
             * //hlvTable.setPaddingTop(1000);
             * hlvTable.add(tableprem);
             * document.add(hlvTable);
             * document.add(Chunk.NEWLINE);
             * 
             * 
             * document.close();
             * viewPdf(file);
             * 
             * 
             * } catch (FileNotFoundException e) {
             * e.printStackTrace();
             * } catch (MalformedURLException e) {
             * e.printStackTrace();
             * } catch (IOException e) {
             * e.printStackTrace();
             * } catch (DocumentException e) {
             * e.printStackTrace();
             * }
             * }
             * 
             * 
             * public Context getApplicationContext() {
             * return context;
             * }
             * 
             * public Context getContext() {
             * return context;
             * }
             * 
             * public void setContext(Context context) {
             * this.context = context;
             * }
             * 
             * @SuppressLint("NewApi")
             * private void viewPdf(File file) {
             * 
             * Intent intent = new Intent("android.intent.action.VIEW");
             * intent.setDataAndType(Uri.fromFile(file), "application/pdf");
             * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             * context.startActivity(intent);
             * 
             * }
             * 
             * 
             * private void insertCell(PdfPTable table, String text, int align,
             * int colSpan, Font font) {
             * PdfPCell cell = new PdfPCell(new Phrase(text, font));
             * // set the cell alignment
             * cell.setHorizontalAlignment(align);
             * // set the cell column span in case you want to merge two or more cells
             * cell.setColspan(colSpan);
             * // add the call to the table
             * table.addCell(cell);
             * }*/ 
}
    
}