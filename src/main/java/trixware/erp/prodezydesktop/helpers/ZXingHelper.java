/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.helpers;

/**
 *
 * @author WIN7
 */
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.EncodeHintType;



public class ZXingHelper 
{
    public static byte [] getQRCodeImage(String text,int width,int height) 
    {
           try
           {
                QRCodeWriter qrCodeWriter=new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
                ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
                MatrixToImageWriter.writeToStream(bitMatrix, "png", byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            }
            catch(Exception e)
            {
                return null;
            }
      
           
    }  

    public static byte[] getQRCodeImage(String Uname, String Upass) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}


//         String filePath = "D:\\QRCODE\\"+image_name+".png";
//            String charset = "UTF-8"; // or "ISO-8859-1"
//            Map < EncodeHintType, ErrorCorrectionLevel > hintMap = new HashMap < EncodeHintType, ErrorCorrectionLevel > ();
//            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//            BitMatrix matrix = new MultiFormatWriter().encode(
//                new String(qrCodeData.getBytes(charset), charset),
//                BarcodeFormat.QR_CODE, 200, 200, hintMap);
//            MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
//                .lastIndexOf('.') + 1), new File(filePath));
//            System.out.println("QR Code image created successfully!");
