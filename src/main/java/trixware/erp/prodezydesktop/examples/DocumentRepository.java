/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import trixware.erp.prodezydesktop.Model.SelectedObjects;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.RawMaterialDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.MyCustomFilter;
import trixware.erp.prodezydesktop.web_services.WebAPITester;


/**
 *
 * @author Rajesh
 */
public class DocumentRepository extends javax.swing.JPanel {

    byte[] image = null;
    ArrayList<ProductDR> products = null;
    ArrayList<RawMaterialDR> rawmaterials = null;
    String apiCallReuslt = "" ;
    
    ArrayList<String[]> docTypeMasterList = new ArrayList<String[]>();
    
    /**
     * Creates new form DocumentRepository
     */
    public DocumentRepository () {
        initComponents ();
        loadContent ( "documentType" );
        createFilesList ();
        
        
        if( jComboBox1.getItemCount()  >  0 ){
            
            jComboBox1.addActionListener ( new ActionListener () {
                @Override
                public void actionPerformed ( ActionEvent e ) {
                    
                    selectDocumentMasterType ();
                }
            });
            
            loadSelectedDocTypeList();  
        
            
            
        }else{
            
        }
  
            
            apiCallReuslt = WebAPITester.prepareWebCall ( "finishedgoods" , StaticValues.getHeader () , "" );
            if( ! apiCallReuslt.contains(   "not found" )  ){                  
                    HashMap<String , Object> map = new HashMap<String , Object> ();
                    JSONObject jObject = new JSONObject ( apiCallReuslt );
                    Iterator<?> keys = jObject.keys ();
                    while ( keys.hasNext () ) {
                        String key = ( String ) keys.next ();
                        Object value = jObject.get ( key );
                        map.put ( key , value );
                    }
                    JSONObject st = ( JSONObject ) map.get ( "data" );
                    JSONArray records = st.getJSONArray ( "records" );

                    JSONObject emp = null;
                    products = new ArrayList<ProductDR> ();
                    for ( int i = 0 ; i < records.length () ; i ++ ) {
                        emp = records.getJSONObject ( i );
                        products.add ( new ProductDR ( Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) , emp.get ( "FG_PART_NO" ).toString () , emp.get ( "FG_UOM_ID" ).toString () ) );
                    }
                    
            }

            
            
            apiCallReuslt = WebAPITester.prepareWebCall ( "rawmaterials" , StaticValues.getHeader () , "" );
            if( ! apiCallReuslt.contains(   "not found" )  ){                  
                    HashMap<String , Object> map = new HashMap<String , Object> ();
                    JSONObject jObject = new JSONObject ( apiCallReuslt );
                    Iterator<?> keys = jObject.keys ();
                    while ( keys.hasNext () ) {
                        String key = ( String ) keys.next ();
                        Object value = jObject.get ( key );
                        map.put ( key , value );
                    }
                    JSONObject st = ( JSONObject ) map.get ( "data" );
                    JSONArray records = st.getJSONArray ( "records" );

                    JSONObject emp = null;
                    rawmaterials = new ArrayList<RawMaterialDR> ();
                    for ( int i = 0 ; i < records.length () ; i ++ ) {
                        emp = records.getJSONObject ( i );
                        rawmaterials.add ( new RawMaterialDR ( Integer.parseInt ( emp.get ( "RM_ID" ).toString () ) , emp.get ( "RM_NAME" ).toString ()  ) );
                    }
            }
            
            String selectedDocMasterType = jComboBox1.getSelectedItem().toString() ; 

            jComboBox2.removeAllItems ();
            jComboBox2.addItem( "-- Select All --" );
            if( selectedDocMasterType.contains ( "Part - ")  ){
                for ( int i = 0 ; i < products.size(); i ++ ) {
                    jComboBox2.addItem( products.get(i).FG_PART_NO );
                }
            }else if( selectedDocMasterType.contains ( "RM - ")  ){
                for ( int i = 0 ; i < rawmaterials.size() ; i ++ ) {
                    jComboBox2.addItem( rawmaterials.get(i).RM_CLASS  );
                }
            }
            
    }

    String[] directories = null;
    String[] files = null;
    
    DefaultListModel orderDetailsList = new DefaultListModel();
    
    public void createFilesList () {

        File file = new File ( "myfiles" );
        directories = file.list ( new FilenameFilter () {
            @Override
            public boolean accept ( File current , String name ) {

                return new File ( current , name ).isDirectory ();
            }
        } );
        //System.out.println(Arrays.toString(directories));
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(849, 389));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Select Document");
        add(jLabel1);
        jLabel1.setBounds(60, 50, 120, 16);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Browse");
        jButton1.setOpaque(false);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(60, 70, 110, 40);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Save");
        jButton2.setOpaque(false);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(230, 280, 90, 40);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Selected Document : ");
        add(jLabel2);
        jLabel2.setBounds(200, 50, 130, 16);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("<< selcted file path here >>");
        add(jLabel3);
        jLabel3.setBounds(200, 70, 600, 40);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Select Document Type");
        add(jLabel4);
        jLabel4.setBounds(60, 120, 150, 16);

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        add(jComboBox1);
        jComboBox1.setBounds(60, 140, 260, 40);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.setPreferredSize(new java.awt.Dimension(0, 0));
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        add(jScrollPane2);
        jScrollPane2.setBounds(460, 350, 0, 0);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(350, 120, 453, 200);

        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });
        add(jComboBox2);
        jComboBox2.setBounds(60, 210, 260, 40);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setText("Select Part / Raw Materials");
        add(jLabel5);
        jLabel5.setBounds(60, 190, 200, 16);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Show All Documents");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(640, 330, 170, 40);
    }// </editor-fold>//GEN-END:initComponents

    File selectedFile = null;
    String encodedString = "";
    String formatName = "" ;
    byte[] fileContent = null;

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        
        JFileChooser fileChooser = new JFileChooser (  );
        fileChooser.setFileFilter ( new MyCustomFilter ("*") );
        fileChooser.setCurrentDirectory ( new File ( System.getProperty ( "user.home" ) ) );
        int result = fileChooser.showOpenDialog ( this );
        if ( result == JFileChooser.APPROVE_OPTION ) {

            selectedFile = fileChooser.getSelectedFile ();
            jLabel3.setText ( selectedFile.getAbsolutePath () );
            
            File selectedFile = fileChooser.getSelectedFile ();
            
//            BufferedImage image1;
//            try {
//                image1 = ImageIO.read ( new File ( selectedFile.getAbsolutePath () ) );
//
//                BufferedImage inputImage = ImageIO.read ( selectedFile );
//
//                BufferedImage outputImage = new BufferedImage ( inputImage.getWidth () , inputImage.getHeight () , inputImage.getType () );
//
//                Graphics2D g2d = outputImage.createGraphics ();
//                g2d.drawImage ( inputImage , 0 , 0 , inputImage.getWidth () , inputImage.getHeight () , null );
//                g2d.dispose ();
//
//                // extracts extension of output file
//                formatName = selectedFile.getAbsolutePath ().substring ( selectedFile.getAbsolutePath ().lastIndexOf ( "." ) + 1 );
//                ImageIO.write ( outputImage , formatName , new File ( "temp_doc."+formatName ) );
//                FileUtils.writeByteArrayToFile ( new File ( "temp_doc."+formatName )  , image);
//
//            } catch ( IOException ex ) {
//                //Logger.getLogger(Employee_HR_Master.class.getName()).log(Level.SEVERE, null, ex);
//                System.out.println ( ex.getMessage () );
//
//            }       
            
            
            FileInputStream fis;
            ByteArrayOutputStream bos;
            try {
                fis = new FileInputStream(selectedFile);
                //fis = new FileInputStream ( new File ( "temp_doc."+formatName ) );
                bos = new ByteArrayOutputStream ();
                byte[] buf = new byte[ 1024 ];

                for ( int readNum ; ( readNum = fis.read ( buf ) ) != -1 ; ) {
                    bos.write ( buf , 0 , readNum );
                }

                formatName = selectedFile.getAbsolutePath ().substring ( selectedFile.getAbsolutePath ().lastIndexOf ( "." ) + 1 );
                image = bos.toByteArray ();
                
                byte[] encoded = Base64.encodeBase64 (image);
                fileContent = Base64.encodeBase64 (image);
                encodedString = new String(encoded);
               
                
                // fileContent = FileUtils.readFileToByteArray(  new File ( "temp_doc."+formatName )    ).;
                // encodedString =  Base64.encodeBase64(fileContent).toString();
                // encodedString =   Base64.encodeBase64( bos.toByteArray () ).toString();
                
 
                String tempFileName = "TestConvertFile."+formatName ;
                File downloaded = new File( tempFileName  ) ;
                encoded = Base64.decodeBase64 (encodedString );
                FileUtils.writeByteArrayToFile(      downloaded   , encoded ) ;
                
                
            } catch ( FileNotFoundException ex ) {
                System.out.println ( "Cannot process file "+ex.getMessage() );
            } catch ( IOException ex ) {
                System.out.println ( "Cannot process file "+ex.getMessage() );
            }

        }
    }

    public void loadContent ( String val ) {
        //refdocs
        String addEmpAPICall = "rdm";
        String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

        if (  ! result2.contains ( "not found" )  && ! result2.contains ( "http" )) {
            if ( result2 != null ) {
             
                HashMap<String , Object> map = new HashMap<String , Object> ();
                JSONObject jObject = new JSONObject ( result2 );
                Iterator<?> keys = jObject.keys ();

                while ( keys.hasNext () ) {
                    String key = ( String ) keys.next ();
                    Object value = jObject.get ( key );
                    map.put ( key , value );
                }

                JSONObject st = ( JSONObject ) map.get ( "data" );
                JSONArray records = st.getJSONArray ( "records" );

                JSONObject emp = null;

                for ( int i = 0 ; i < records.length () ; i ++ ) {
                        emp = records.getJSONObject ( i );
                        docTypeMasterList.add(     new String[]{ emp.get("RDM_UID").toString(), emp.get("RDM_NAME").toString()   }      );
                        jComboBox1.addItem( emp.get("RDM_NAME").toString() );
                }
            }
        }
    }
    
    public static DefaultTableModel buildTableModelfromJSON ( String  employeeJSONList )
            {

            Vector<String> columnNames = new Vector<String> ();
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        
            HashMap<String, Object> map = new HashMap<String, Object>();
            JSONObject jObject = new JSONObject( employeeJSONList );
            Iterator<?> keys = jObject.keys();

            while( keys.hasNext() ){
                String key = (String)keys.next();
                Object value = jObject.get ( key ) ; 
                map.put(key, value);
            }
            
            JSONObject st = (JSONObject) map.get ( "data" );
            JSONArray records = st.getJSONArray ( "records");
            
            JSONObject emp = null;
            
            columnNames = new Vector<String> ();
            columnNames.add( "RD_UID"  );columnNames.add( "RD_NAME"  );
           

        // data of the table
        for( int i=0; i<records.length (); i++ ){
        Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 0 ; columnIndex <columnNames.size () ; columnIndex ++ ) {
                emp = records.getJSONObject ( i );
                
                if(   columnIndex == 1  ){
                    String FileName = emp.get ( columnNames.get ( columnIndex ) ).toString() ;
                    if( FileName.contains("-:-") )
                    {
                        vector.add (  FileName.substring ( FileName.indexOf ( "-:-")+3 , FileName.length ())  );
                    }else{
                        vector.add (  FileName  );
                    }
                }else{
                    vector.add (  emp.get ( columnNames.get ( columnIndex ) )  );
                }
            }
            data.add ( vector );
        }
        return new DefaultTableModel ( data , columnNames );
    }
    
    public void loadContent_old ( String val ) {

        try {

            SelectedObjects ob;

            //System.out.println( val );
            ResultSet result = DB_Operations.getMasters ( val );

            if ( result != null && result.isBeforeFirst () ) {
                while ( result.next () ) {
                    jComboBox1.addItem ( result.getString ( 2 ) );
                }
            }

        } catch ( SQLException e ) {
            StaticValues.writer.writeExcel ( DocumentRepository.class.getSimpleName () , DocumentRepository.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }
    }//GEN-LAST:event_jButton1MouseClicked

    String selectedFilePath = null;

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:

        addDocumentAgainstMaster(  );
        
        
/*        Calendar c2 = Calendar.getInstance ();
        SimpleDateFormat sdf2 = new SimpleDateFormat ( "ddMMMyyyy_HH_mm_a" );
        String strDate2 = sdf2.format ( c2.getTime () );

        FileChannel inputChannel = null;
        FileChannel outputChannel = null;

        File dir = new File ( "myfiles\\" + jComboBox1.getSelectedItem ().toString () );
        dir.mkdirs ();

        selectedFilePath = jComboBox1.getSelectedItem ().toString () + "_(" + selectedFile.getName () + ") _saved_on" + strDate2 + selectedFile.getName ().substring ( selectedFile.getName ().lastIndexOf ( '.' ) );

        try {
            inputChannel = new FileInputStream ( selectedFile ).getChannel ();
            outputChannel = new FileOutputStream ( new File ( dir , selectedFilePath ) ).getChannel ();
            outputChannel.transferFrom ( inputChannel , 0 , inputChannel.size () );

            inputChannel.close ();
            outputChannel.close ();

            loadContent ( "documentType" );
            
        } catch ( FileNotFoundException e1 ) {
            StaticValues.writer.writeExcel ( DocumentRepository.class.getSimpleName () , DocumentRepository.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        } catch ( IOException e2 ) {
            StaticValues.writer.writeExcel ( DocumentRepository.class.getSimpleName () , DocumentRepository.class.getSimpleName () , e2.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e2.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }       */

    }

    public void addDocumentAgainstMaster(  ){
        
        
        String fileName = "" ;
            
        if(  jComboBox2.getItemCount () >1     ){
            if( ! jComboBox2.getSelectedItem ().toString ().equals ( "-- Select All --")   ){
            
                 String selectedDocMasterType = jComboBox1.getSelectedItem().toString() ; 
         
//                if(  selectedDocMasterType.contains ( "RM - ")  ){
//                   fileName = fileName +rawmaterials.get( jComboBox2.getSelectedIndex()-1 ).RM_ID +  "rmid"+ selectedFile.getAbsoluteFile ().getName () ;
//                }else if(  selectedDocMasterType.contains ( "Part - ")  ){
//                    fileName = fileName +  products.get( jComboBox2.getSelectedIndex()-1 ).FG_ID +  "pid"+ selectedFile.getAbsoluteFile ().getName () ;
//                }
                fileName = fileName + selectedFile.getAbsoluteFile ().getName () ;
            
            }else{
                fileName = selectedFile.getAbsoluteFile ().getName ()  ;
            }
        }else{
                fileName = selectedFile.getAbsoluteFile ().getName ()  ;
        }
        
        try {
            
            if( encodedString!=null &&  !encodedString.equals("")  &&  selectedFile !=null  && ! formatName.equals(""))
            {
//                String addEmpAPICall = "refdocadd?binarydata=" + URLEncoder.encode(encodedString, "UTF-8") +"&RD_NAME="+URLEncoder.encode( fileName  , "UTF-8")+"&RDM_UID="+URLEncoder.encode(  docTypeMasterList.get( jComboBox1.getSelectedIndex ()  )[0] , "UTF-8")+"&ext="+URLEncoder.encode(  formatName  , "UTF-8") ;
//                String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "" );
//                System.out.println( result2);
//                String addEmpAPICall = "refdocadd?binarydata=" + URLEncoder.encode(encodedString, "UTF-8") +"&RD_NAME="+URLEncoder.encode( fileName  , "UTF-8")+"&RDM_UID="+URLEncoder.encode(  docTypeMasterList.get( jComboBox1.getSelectedIndex ()  )[0] , "UTF-8")+"&ext="+URLEncoder.encode(  formatName  , "UTF-8") ;
                String result2 = "";
                
                String selectedDocMasterType = jComboBox1.getSelectedItem().toString() ; 
                
                if(  selectedDocMasterType.contains ( "RM - ")  ){
                    result2 =  WebAPITester.prepareWebCall3 ( "refdocadd",   StaticValues.getHeader ()   , "RD_NAME="+URLEncoder.encode( fileName  , "UTF-8")+"&RDM_UID="+URLEncoder.encode(  docTypeMasterList.get( jComboBox1.getSelectedIndex ()  )[0] , "UTF-8")+"&ext="+URLEncoder.encode(  formatName  , "UTF-8") + "&item_id=" + URLEncoder.encode( rawmaterials.get( jComboBox2.getSelectedIndex()-1 ).RM_ID +"", "UTF-8")+ "&binarydata=" + URLEncoder.encode(encodedString, "UTF-8")  );
                }else if(  selectedDocMasterType.contains ( "Part - ")  ){
                    result2 =  WebAPITester.prepareWebCall3 ( "refdocadd",   StaticValues.getHeader ()   , "RD_NAME="+URLEncoder.encode( fileName  , "UTF-8")+"&RDM_UID="+URLEncoder.encode(  docTypeMasterList.get( jComboBox1.getSelectedIndex ()  )[0] , "UTF-8")+"&ext="+URLEncoder.encode(  formatName  , "UTF-8") + "&item_id=" + URLEncoder.encode( products.get( jComboBox2.getSelectedIndex()-1 ).FG_ID+"", "UTF-8")+ "&binarydata=" + URLEncoder.encode(encodedString, "UTF-8")  );
                }else{
                    result2 =  WebAPITester.prepareWebCall3 ( "refdocadd",   StaticValues.getHeader ()   , "RD_NAME="+URLEncoder.encode( fileName  , "UTF-8")+"&RDM_UID="+URLEncoder.encode(  docTypeMasterList.get( jComboBox1.getSelectedIndex ()  )[0] , "UTF-8")+"&ext="+URLEncoder.encode(  formatName  , "UTF-8") + "&item_id=" + URLEncoder.encode( "0", "UTF-8")+ "&binarydata=" + URLEncoder.encode(encodedString, "UTF-8")  );
                }
                
                System.out.println( result2);
                
                try{
                    JSONObject response = new JSONObject( result2  );
                    String responseStr = response.get( "message" ).toString() ;

                    if(  responseStr.contains( "success" )  ){
                        response =  new JSONObject( responseStr  );           
                        response = response.getJSONObject( "success" );
                        JOptionPane.showMessageDialog( null, response.get( "104" ).toString()  ); 
                        loadContent ( "documentType" );
                    }
                }catch( JSONException e  ){
                        
                                        
                }
                
                
                
            }
        } catch ( UnsupportedEncodingException ex ) {
        
        }
        
    }
    
    
    public void openFile ( String filename ) {

        if (  ! filename.equals ( "" ) ) {
            try {
                String fileName = filename;

             
                File file = new File (  fileName );

                Desktop desktop = Desktop.getDesktop ();
                if ( file.exists () ) {
                    desktop.open ( file );
                }
            } catch ( IOException ex ) {
                //    Logger.getLogger(FinishedGoodMaster.class.getName()).log(Level.SEVERE, null, ex);
                StaticValues.writer.writeExcel ( DocumentRepository.class.getSimpleName () , DocumentRepository.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }

    }//GEN-LAST:event_jButton2MouseClicked

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    public void selectDocumentMasterType(   ){
        
         loadSelectedDocTypeList();  
        
        String selectedDocMasterType = jComboBox1.getSelectedItem().toString() ; 
       
        
        jComboBox2.removeAllItems ();
        jComboBox2.addItem( "-- Select All --" );
        if( selectedDocMasterType.contains ( "Part - ")  ){
            for ( int i = 0 ; i < products.size(); i ++ ) {
                jComboBox2.addItem( products.get(i).FG_PART_NO );
            }
        }else if( selectedDocMasterType.contains ( "RM - ")  ){
            for ( int i = 0 ; i < rawmaterials.size() ; i ++ ) {
                jComboBox2.addItem( rawmaterials.get(i).RM_CLASS  );
            }
        }
        
    }
    
    
    public void loadSelectedDocTypeList(){
            
        jList1.removeAll ();
            
         //   int selectedDocumentTypeMasterId = 
        
        if( docTypeMasterList.get( jComboBox1.getSelectedIndex ()  )[1].contains( "Part - " )  ){
            
                        
            
        }else   if( docTypeMasterList.get( jComboBox1.getSelectedIndex ()  )[1].contains( "RM - " )  ){
            
        }
         
        String addEmpAPICall = "refdocs?RDM_UID="+docTypeMasterList.get( jComboBox1.getSelectedIndex ()  )[0];
            String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

            if( !  result2.contains(  "not found"   )  ){
                if ( result2!= null ) {
                    jTable1.setModel ( buildTableModelfromJSON (result2 ) );
                    jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                    jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                }
            }
        }


        public void loadSelectedDocTypeList_old(){
            
            jList1.removeAll ();
            File file = new File ( "myfiles\\" + jComboBox1.getSelectedItem().toString() );
            files = file.list ( new FilenameFilter () {
                @Override
                public boolean accept ( File current , String name ) {
                    return new File ( current , name ).isFile ();
                }
            } );

            if(files!=null ){
                if(files.length>=1){
                    orderDetailsList.clear ();
                    for(int i=0; i<files.length; i++){
                        orderDetailsList.addElement ( files[i]);
                    }
                    jList1.setModel ( orderDetailsList );
                }
            }else{
                orderDetailsList.clear ();
                jList1.setModel ( orderDetailsList );
            }
    }

    
    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        openFile ( "myfiles\\"+jComboBox1.getSelectedItem ().toString()+"\\"+jList1.getSelectedValue () );
    }//GEN-LAST:event_jList1MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();
        // get the selected row index
        int selectedRowIndex = jTable1.getSelectedRow ();
        //jTextField6.setText ( model.getValueAt ( selectedRowIndex , 1 ).toString () );
        //selectedRowIndex += 1;
        int selectedRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );
        
        String addEmpAPICall = "readrmdoc?RD_UID="+selectedRecordId;
            String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

        if( !  result2.contains(  "not found"   )  ){
            if ( result2!= null ) {
                
                try {
                
                String filedata =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                JSONObject responseObject = (JSONObject) new JSONTokener(  filedata   ).nextValue() ;
                String fileData = responseObject.getJSONObject ( "data").getString("binarydata") ;
                byte[] encoded = Base64.decodeBase64 (fileData );
                
                String tempFileName = responseObject.getJSONObject ( "data").getString("name") ;
                File downloaded = new File( tempFileName  ) ;
                FileUtils.writeByteArrayToFile(      downloaded   , encoded) ;
                Desktop desktop = Desktop.getDesktop ();
                desktop.open ( downloaded );
                
            } catch ( IOException ex ) {
                //    Logger.getLogger(FinishedGoodMaster.class.getName()).log(Level.SEVERE, null, ex);
                StaticValues.writer.writeExcel ( DocumentRepository.class.getSimpleName () , DocumentRepository.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
                
                
            }
        }       
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        
        
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
                ShowAllFiles showAllFiles = new ShowAllFiles (null ) ;
                showAllFiles.openProgressWindow ();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}


 class ShowAllFiles extends JDialog{

    AllDocumentsTypeWise progress = null ;
    
    public ShowAllFiles ( JFrame parent  ) {
        super(parent, "Please wait...", true);
        
        
        
        progress = new AllDocumentsTypeWise () ;
        getContentPane().add( progress  , BorderLayout.CENTER);
        //setUndecorated ( true );
       // pack();
        setResizable(false);
        setBounds(  0    , 0,  530 , 440 );
        setLocationRelativeTo(parent);
        setDefaultCloseOperation ( JDialog.DISPOSE_ON_CLOSE);
    }
 
    public void closeProgressWindow(){
         dispose();
    }
   
    public void openProgressWindow(){
        setVisible ( true );
    }
    
}
