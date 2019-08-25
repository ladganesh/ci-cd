/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

import java.io.File;

/**
 *
 * @author Rajesh
 */
public class MyCustomFilter extends javax.swing.filechooser.FileFilter {

    String typeRequired = null ;
    
    public MyCustomFilter(){
        
    }
    
    public MyCustomFilter(String _typeRequired){     
        
        typeRequired = _typeRequired ;
    }
   
    
    @Override
    public boolean accept(File file) {
        // Allow only directories, or files with ".txt" extension
        
        boolean type = false; 
        
        
        if(typeRequired.equals ( "image"))
            type = file.isDirectory() || file.getAbsolutePath().endsWith(".jpg");
        else if(typeRequired.equals ( "excel 97-2003"))
            type =  file.isDirectory() || file.getAbsolutePath().endsWith(".xls");
        else if(typeRequired.equals ( "excel"))
            type =  file.isDirectory() || file.getAbsolutePath().endsWith(".xlsx");
        else if(typeRequired.equals ( "document 97-2003"))
            type =  file.isDirectory() || file.getAbsolutePath().endsWith(".doc");
        else if(typeRequired.equals ( "document"))
            type =  file.isDirectory() || file.getAbsolutePath().endsWith(".docx");
        else if(typeRequired.equals ( "pdf"))
            type =  file.isDirectory() || file.getAbsolutePath().endsWith(".pdf");
        else
            type =  file.isDirectory() || file.getAbsolutePath().endsWith(".*");
        
        return type  ;
    }

    @Override
    public String getDescription() {
        // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        
        String type = "";
        
         if(typeRequired.equals ( "image"))
            type = "Image (*.jpg)" ;
        else if(typeRequired.equals ( "excel 97-2003"))
            type =  "Excel (*.xls)" ;
        else if(typeRequired.equals ( "excel"))
            type = "Excel (*.xlsx)" ;
        else if(typeRequired.equals ( "document 97-2003"))
            type =  "Document  (*.doc)" ;
        else if(typeRequired.equals ( "document"))
            type =  "Document (*.docx)" ;
        else if(typeRequired.equals ( "pdf"))
            type =  "Document (*.pdf)" ;
          else if(typeRequired.equals ( "*"))
            type =  "Document (*.*)" ;
        
        return type;
    }
}
