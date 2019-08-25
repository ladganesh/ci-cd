/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.quotation;

import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.MyCustomFilter;
import trixware.erp.prodezydesktop.components.ProgressDialog;
import static trixware.erp.prodezydesktop.examples.HomeScreen.home;
import static trixware.erp.prodezydesktop.examples.HomeScreen.jLabel6;
import static trixware.erp.prodezydesktop.examples.HomeScreen.scroll;
import static trixware.erp.prodezydesktop.examples.HomeScreen.width;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.quotation.partmaster.ProcessRMData;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class CreateQuotationForm extends javax.swing.JPanel {

    JScrollPane jScrollPane1 = null ;
    JPanel  abc = null ;

    ArrayList<String[]> processes = new ArrayList<String[]>() ;
    ArrayList<String[]> measunit = new ArrayList<String[]>() ;
    ArrayList<String[]> rawmaterial = new ArrayList<String[]>() ;
    public static ArrayList<RejectionDR> rejModel =null;

    ArrayList<String[]> uploadData = new ArrayList<String[]>();
    ArrayList<PartDetailsPanel> parts = new ArrayList<PartDetailsPanel> () ;
    ArrayList<String[]> rawMData=new ArrayList<String[]>();
    ArrayList<String[]> processData=new ArrayList<String[]>();
    List<ArrayList<String[]>> pdata=new ArrayList<ArrayList<String[]>>();
    
    String[] refIdData;
    
    PartDetailsPanel p2 = null ;
    List<String> list=null; 
    /**
     * Creates new form EnquiriesForm
     */
    boolean newQuat;
    public CreateQuotationForm () {
        
        initComponents ();
        jButton5.setVisible(false);
        newQuat=true;
       // getData ();
        showDialog();
        abc = new JPanel() ;
        abc.setBounds( 0,0,1120, 450  ) ;
        abc.setLayout(null) ;
        abc.setBackground( Color.WHITE );
        
        abc.setLayout ( new BoxLayout ( abc , BoxLayout.Y_AXIS ) );
        
        jScrollPane1 = new JScrollPane( abc, 
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED   ) ;
        
        jScrollPane1.setBounds(  0, 60, 1140, 450   ) ;
        add(jScrollPane1) ;

        revalidate ();
        repaint ();
        
    }
    String enqID="";
    String asslyID="";

    public CreateQuotationForm (String enqId,String assId,String latestUpdate){
        initComponents ();
        this.enqID=enqId;
        this.asslyID=assId;
        
        newQuat=false;
        showDialog();
//        getData ();
        abc = new JPanel() ;
        abc.setBounds( 0,0,1120, 450  ) ;
        abc.setLayout(null) ;
        abc.setBackground( Color.WHITE );
        
        abc.setLayout ( new BoxLayout ( abc , BoxLayout.Y_AXIS ) );
        
        jScrollPane1 = new JScrollPane( abc, 
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED   ) ;
        
        jScrollPane1.setBounds(  0, 60, 1140, 450   ) ;
        add(jScrollPane1) ;

        revalidate ();
        repaint ();
        loadParts(enqId, assId, latestUpdate);
        
    }
    
    ProgressDialog progress = new ProgressDialog( null ) ;
    Thread getTableAndFilesContent = null ;
    public boolean showDialog(){
      
        getTableAndFilesContent = new Thread(){
            public void run(){
                progress.updateTitle("Please wait..");
                progress.updateMessage("<html>The task is being executed.<br> Please wait until it is completed.</html>");
                
                getData();
            }
        };
        getTableAndFilesContent.start();
        progress.openProgressWindow ();
        return true ;
    }
    
    public boolean hideDialog(){
        progress.updateTitle("");
        progress.updateMessage("");
        progress.closeProgressWindow ();
        return true ;
    }
    public void setList(List list)
    {
        this.list=list;
    }
    public void setList(String[] list)
    {
        this.refIdData=list;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();

        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("jButton3");

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1100, 550));
        setLayout(null);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Import Excel");
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(140, 0, 120, 30);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Part No");
        add(jLabel1);
        jLabel1.setBounds(30, 30, 160, 20);

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Drawing No");
        add(jLabel5);
        jLabel5.setBounds(150, 30, 90, 20);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Revision No");
        add(jLabel9);
        jLabel9.setBounds(240, 30, 70, 20);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Revision Date");
        add(jLabel10);
        jLabel10.setBounds(350, 30, 80, 20);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Total Cost / part");
        add(jLabel4);
        jLabel4.setBounds(1030, 30, 100, 20);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("<html>Purchase or Manufactured</html>");
        add(jLabel7);
        jLabel7.setBounds(490, 20, 100, 30);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("<html>Processing Cost</html>");
        add(jLabel11);
        jLabel11.setBounds(890, 30, 110, 20);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("Home");
        jButton4.setOpaque(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(20, 0, 100, 30);

        jButton5.setText("Export Excel");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        add(jButton5);
        jButton5.setBounds(280, 0, 130, 30);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("<html>RM Cost</html>");
        add(jLabel12);
        jLabel12.setBounds(780, 30, 90, 20);

        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel13.setText("<html>Scope</html>");
        add(jLabel13);
        jLabel13.setBounds(610, 30, 50, 20);

        jButton6.setText("Submit");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        add(jButton6);
        jButton6.setBounds(1010, 520, 110, 30);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        uploadData = new ArrayList<String[]>();
        
        File selectedFile = null;

        JFileChooser fileChooser = new JFileChooser ();
        fileChooser.setFileFilter ( new MyCustomFilter ( "excel 97-2003" ) );
        fileChooser.setCurrentDirectory ( new File ( System.getProperty (
                "user.home" ) ) );
       
        int result = fileChooser.showOpenDialog ( this );
        if ( result == JFileChooser.APPROVE_OPTION ) {
            selectedFile = fileChooser.getSelectedFile ();
        }

        FileChannel inputChannel = null;
        FileChannel outputChannel = null;

        File dir = new File ( "dataupload\\" );
        dir.mkdirs ();

        
        try{
            inputChannel = new FileInputStream(selectedFile).getChannel();
            outputChannel = new FileOutputStream (new File( dir, selectedFile.getName() )).getChannel ();
          
            outputChannel.transferFrom( inputChannel, 0, inputChannel.size() );
        
            inputChannel.close();
            outputChannel.close();
        }catch(FileNotFoundException fnfe){
        }catch(IOException ioe){
        }

        try {

           FileInputStream fis;
            
            fis = new FileInputStream ( new File ( "dataupload\\" + selectedFile.getName () ) );

            HSSFWorkbook my_xls_workbook = new HSSFWorkbook ( fis );
            HSSFSheet my_worksheet = my_xls_workbook.getSheetAt ( 0 );

            Iterator<Row> rowIterator = my_worksheet.iterator ();

            while ( rowIterator.hasNext () ) {
                Row row = rowIterator.next ();
                
                String[] dataRow = new String[ 6 ] ;
                
                try {
                    dataRow[0] = row.getCell ( 0 ).getStringCellValue () ;
                } catch ( Exception e2 ) {
                    try {
                        dataRow[0] = row.getCell ( 0 ).getNumericCellValue ()+"" ;
                    } catch ( Exception e1 ) {
                    }
                }
                try {
                    dataRow[1] = row.getCell ( 1 ).getStringCellValue () ;
                } catch ( Exception e2 ) {
                    try {
                        dataRow[1] = row.getCell ( 1 ).getNumericCellValue ()+"" ;
                    } catch ( Exception e1 ) {
                    }
                }
                try {
                    dataRow[2] = row.getCell ( 2 ).getStringCellValue () ;
                } catch ( Exception e2 ) {
                    try {
                        dataRow[2] = row.getCell ( 2 ).getNumericCellValue ()+"" ;
                    } catch ( Exception e1 ) {
                    }
                }
                try {
                    dataRow[3] = row.getCell ( 3 ).getStringCellValue () ;
                } catch ( Exception e2 ) {
                    try {
                        dataRow[3] = row.getCell ( 3 ).getNumericCellValue ()+"" ;
                    } catch ( Exception e1 ) {
                    }
                }
                try {
                    dataRow[4] = row.getCell ( 4 ).getStringCellValue () ;
                } catch ( Exception e2 ) {
                    try {
                        dataRow[4] = row.getCell ( 4 ).getNumericCellValue ()+"" ;
                    } catch ( Exception e1 ) {
                    }
                }
                try {
                    dataRow[5] = row.getCell ( 5 ).getStringCellValue () ;
                } catch ( Exception e2 ) {
                    try {
                        dataRow[5] = row.getCell ( 5 ).getNumericCellValue ()+"" ;
                    } catch ( Exception e1 ) {
                    }
                }

                uploadData.add( dataRow ) ;
            }
            fis.close ();
            
            parts = new ArrayList<PartDetailsPanel> () ;
            
            for ( int i = 0 ; i < uploadData.size () ; i ++ ) {
                String[] element = uploadData.get(i) ;
                p2 = new PartDetailsPanel( i+1 ,  element[0],element[1],element[2],element[3],element[4],element[5]  ,  processes,  measunit , rawmaterial   ) ;
                
                p2.setBounds (  50 , 0 , 1100, 60  ) ;
                abc.add(p2);
                parts.add( p2 );
                repaint() ;
            }
            
            revalidate();
            repaint() ;
            
        }  catch ( IOException ex ) {
            System.out.println ( "IO  " + ex.getMessage () );
        }
      
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

         EnquiriesForm process = new EnquiriesForm ( );
            //  RMEfficienctReport process = new RMEfficienctReport ( "Raw Material Efficiency" );
            scroll = new JScrollPane ( process ,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

            //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
            //scroll.setBounds ( 5 , 5 , width-25 , 605 );
            int leftMargin = ( int ) ( width - process.getPreferredSize ().width ) / 2;
            process.setBounds ( leftMargin , 5 , process.getPreferredSize ().width , 580 );
            scroll.setBounds ( leftMargin - 15 , 5 , width - leftMargin , 550 );

            scroll.setBackground ( Color.WHITE );
            scroll.setBorder ( BorderFactory.createEmptyBorder () );

            home.jPanel1.removeAll ();
            home.jPanel1.setLayout ( null );
            //home.jPanel1.add ( customer );
            home.jPanel1.add ( scroll , BorderLayout.CENTER );
            //home.jPanel1.add ( bp1 );
            jLabel6.setText ( "  Enquiries" );

            home.revalidate ();
            home.repaint ();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        exportExcel();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        onActionSubmitButton(evt);
    }//GEN-LAST:event_jButton6ActionPerformed
    public void exportExcel()
    {
        JFileChooser fileChooser = new JFileChooser ();
        FileFilter filter = new FileNameExtensionFilter("Files", ".xls");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        if(result==0)
        {
            String path=fileChooser.getSelectedFile().toString();
            if(path!=null)
            {
                new ExcelWriter().exportDataInExcel(enqID,asslyID,path);
            }
        }    
    }
    public void loadParts(String reEnqId,String refAsslyId,String revNo)
    {
        jButton1.setVisible(false);
        
        uploadData = new ArrayList<String[]>();

        String addEmpAPICall = "bompartdetails?assly_id=" + refAsslyId + "&enq_id="+reEnqId;
      
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        if (!result2.contains("not found")) {
            if (result2 != null) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                JSONObject jObject = new JSONObject(result2);
                Iterator<?> keys = jObject.keys();

                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    Object value = jObject.get(key);
                    map.put(key, value);
                }

                JSONObject st = (JSONObject) map.get("data");
                JSONArray records = st.getJSONArray("records");
                JSONObject emp = null;
                String bomId="";
                String partDesc="";
                String drewNo="";                
                String category="";
                String drewRevNo="";
                String revDate="";
                String quant="";
                
                String rmid = "";
                String rmname = "";
                String rmrate = "";
                String rmunit = "";
                String rmcode = "";
                String rmtype = "";
                String rmctg = "";
                String rmshape = "";
                String length = "";
                String thickness = "";
                String diameter = "";
                String wall = "";
                String density = "";
                String width = "";
                String indiameter = "";
                String outdiam = "";
                String volume = "";
                String weight = "";
                String transport = "";
                String insp = "";
                String rateFluct = "";
                String scrap = "";
                String icc = "";
                String other = "";
                String taxes = "";
                String rmtotal = "";
                String rmT="";
                String procerssName = "";
                String unitName = "";
                String processCost = "";
                String processLocationName = "";
                String effRate = "";
                String processOH = "";
                String totalProcessCost = "";                
                String totalBomCost="";
                String purchasedCost="";

                String isPurchase="";
                String purchasedCategory="";
                String purchasedSupplier="";
                
                for (int i = 0; i < records.length(); i++) {
                    emp = records.getJSONObject(i);
                    try {
                        bomId=emp.get("bom_id").toString();
                        partDesc = emp.get("part_desc").toString();
                        drewNo = emp.get("drwg_no").toString();
                        category = emp.get("type").toString();
                        drewRevNo = emp.get("drwg_rev_no").toString();
                        revDate = emp.get("drwg_rev_date").toString();
                        quant = emp.get("quantity").toString();    

                        rmid = emp.get("rm_id").toString();
                        rmname = emp.get("rm_name").toString();
                        rmrate = emp.get("rm_rate").toString();
                        rmunit = emp.get("rm_unit").toString();
                        rmcode = emp.get("rm_code").toString();
                        rmtype = emp.get("rm_type").toString();
                        rmctg = emp.get("rm_ctg").toString();
                        rmshape = emp.get("shape").toString();
                        length = emp.get("length").toString();
                        thickness = emp.get("thick").toString();
                        diameter = emp.get("diam").toString();
                        wall = emp.get("wall").toString();
                        density = emp.get("density").toString();
                        width = emp.get("width").toString();
                        indiameter = emp.get("ID").toString();
                        outdiam = emp.get("OD").toString();
                        volume = emp.get("volume").toString();
                        weight = emp.get("weight").toString();
                        transport = emp.get("transport").toString();
                        insp = emp.get("inspection").toString();
                        rateFluct = emp.get("rate_fluct").toString();
                        scrap = emp.get("scraprate").toString();
                        icc = emp.get("ICC").toString();
                        other = emp.get("other").toString();
                        taxes = emp.get("taxes").toString();
                        rmtotal = emp.get("total_rm_cost").toString();
                        rmT=emp.get("rm_total_cost").toString();    
                        totalBomCost=emp.get("total_bom_cost").toString();
                        purchasedCost=emp.get("purchase_cost").toString();
                        String FCWeight=emp.get("fc_weight").toString();
//'scrap_status','scrap_recovery','scrap_weight','scrap_rec_percent'
                        String sc_staus=emp.get("scrap_status").toString();
                        String sc_recovry=emp.get("scrap_recovery").toString();
                        String sc_recovryPercent=emp.get("scrap_rec_percent").toString();
                        String sc_weight=emp.get("scrap_weight").toString();
                        String packaging=emp.get("packaging").toString();
                        String scope=emp.get("scope").toString();
                        isPurchase=emp.get("is_purchased_master").toString();
                        purchasedCategory=emp.get("purchased_category").toString();
                        purchasedSupplier=emp.get("purchased_supplier").toString();
                        String iccMonth=emp.get("icc_month").toString();
                        String taxAmt=emp.get("taxes_amt").toString();
                        String rateFluctAmt=emp.get("rate_fluct_amt").toString();
                        String iccAmt=emp.get("icc_amt").toString();

//                        procerssName = emp.get("process_name").toString();
//                        unitName = emp.get("process_unit").toString();
//                        processCost = emp.get("pro_def_rate").toString();
//                        processLocationName = emp.get("pro_loc").toString();
//                        effRate = emp.get("pro_eff_rate").toString();
//                        processOH = emp.get("pro_OH").toString();
//                        totalProcessCost = emp.get("pro_total_cost").toString();
                        
                        if(!partDesc.equals(""))
                        {
                            if(category.equals("M"))
                            {
                                category="Manufactured";
                            }else if(category.equals("P")){
                                category="Purchased";
                            }
                            
                            String[] data={partDesc,drewNo,category,drewRevNo,revDate,quant,bomId,totalBomCost,purchasedCost,scope,isPurchase,purchasedCategory,purchasedSupplier};                         
                            String[] rmData={rmid,rmname,rmrate,rmunit,rmcode,rmtype,rmctg,rmshape,length,thickness
                            ,diameter ,wall,density,width,indiameter,outdiam,volume,weight,transport,insp,rateFluct
                            ,scrap,icc,other,taxes,rmtotal,rmT,FCWeight,sc_staus,sc_recovry,sc_recovryPercent,sc_weight,"",packaging,iccMonth,taxAmt,rateFluctAmt,iccAmt};
                            rawMData.add(rmData);
                            uploadData.add(data);
                        }else{
                            
                        }
                    } catch (Exception e) {
                        System.out.println("" + e.getMessage());
                    }
                }
            }
        }
        
        parts = new ArrayList<PartDetailsPanel> () ;
            
        for ( int i = 0 ; i < uploadData.size () ; i ++ ) {
            String[] element = uploadData.get(i) ;
            p2 = new PartDetailsPanel( i+1 ,  element[0],element[1],element[2],element[3],element[4],element[5],element[7]  ,  processes,  measunit , rawmaterial ,element[6],rawMData.get(i) ,element[8] ,element[9],element[10],element[11],element[12]) ;
            p2.setBounds (  50 , 0 , 1100, 60  ) ;
            abc.add(p2);
            parts.add( p2 );
            repaint() ;
        }

        revalidate();
        repaint() ;
        
        if(uploadData.size()==0)
        {
            jButton1.setVisible(true);
        }
    }
    
    public void getData(){    
        rejModel = ProcessRMData.rejModel;
        if(rejModel==null)
        {
            rejModel=new ProcessRMData().getRejModel();
        }

        processes = ProcessRMData.processes;
        if(processes==null)
        {
            processes=new ProcessRMData().getProcesses();
        }
        measunit = ProcessRMData.measunit;
        if(measunit==null)
        {
            measunit=new ProcessRMData().getMesUnit();
        }
        rawmaterial = ProcessRMData.rawmaterial;
        if(rawmaterial==null)
        {
            rawmaterial=new ProcessRMData().getRawMaterialData();
        }        
        hideDialog();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables

    private void onActionSubmitButton(ActionEvent e){
        boolean chk=true;
        for (PartDetailsPanel p : parts) {
            String category = p.getCategory();
            if (category.equals("Purchased")) {
                
            }else{
                ArrayList<String[]> rawMaterialData = p.getRMData();
                ArrayList<String[]> pdata1 = p.getProcessData();   
                
                if(rawMaterialData==null)
                {
                    chk=false;
                }
                
                if(pdata1==null)
                {
                    chk=false;
                }                
            }
        }
        if(chk)
        {
            if (parts != null) {
                String[] refData = refIdData;
                String ref_ass_id = refData[0];
                String ref_enq_id = refData[1];
                String part_desc = refData[2];
                String quantity = refData[3];
                String dwno = refData[4];
                String dwrevNo = refData[5];
                String dwrevDate = refData[6];
                String type = refData[7];

                if (newQuat == false) {
                    try {
                        String itemDesc = list.get(0);
                        String drwNo = list.get(1);
                        String revDate = list.get(3);
                        String assemblyNo = list.get(9);

                        String addEmpAPICall = "";

                        addEmpAPICall = "enquiry_update?enq_id=" + refData[1] + "&enq_latest_version=" + refData[5];
                        String result1 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

                        addEmpAPICall = "assembly_add?assly_no=" + URLEncoder.encode(assemblyNo, "UTF-8") + "&ref_enq_id=" + refData[1] + "&assembly_desc=" + URLEncoder.encode(itemDesc, "UTF-8") + "&quantity=" + quantity + "&drwg_no=" + URLEncoder.encode(drwNo, "UTF-8") + "&drwg_rev_no=" + refData[5] + "&drwg_rev_date=" + URLEncoder.encode(revDate, "UTF-8") + "&type=" + URLEncoder.encode(type, "UTF-8");
                        String result = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                        ref_ass_id = WebAPITester.getInsertedId(result);
                        asslyID = ref_ass_id;
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(CreateQuotationForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                for (PartDetailsPanel p : parts) {
                    String category = p.getCategory();
                    if (category.equals("Purchased")) {
                        try {
                            String purchastCost = p.getPurchasedCost();
                            String quant = p.getQuantity1();
                            String total = p.getTotal();
                            String drwNo = p.getDrNo();
                            String drwrevNo = p.getDrRevNo();
                            String drwrevDate = p.getDrRevDate();
                            String partDesc = p.getPartName();
                            String scope = p.getScope();
                            boolean chk1 = p.getPurchasedMaster();
                            String ispMaster = "n";
                            String purchasedCategory = "";
                            String purchasedSupplier = "";

                            if (chk1) {
                                ispMaster = "y";
                                purchasedSupplier = p.getPurchasedSupplier();
                                purchasedCategory = p.getPurchasedCategory();
                            }

                            String addEmpAPICall = "bom_detail_add?ref_assly_id=" + ref_ass_id + "&ref_enq_id=" + ref_enq_id + "&part_desc=" + partDesc
                                    + "&quantity=" + quant + "&drwg_no=" + drwNo + "&drwg_rev_no=" + drwrevNo + "&drwg_rev_date=" + URLEncoder.encode(drwrevDate, "UTF-8")
                                    + "&type=" + "p" + "&purchase_cost=" + purchastCost + "&total_bom_cost=" + total + "&scope=" + scope + "&is_purchased_master=" + URLEncoder.encode(ispMaster, "UTF-8") + "&purchased_category=" + URLEncoder.encode(purchasedCategory, "UTF-8") + "&purchased_supplier=" + URLEncoder.encode(purchasedSupplier, "UTF-8");
                            String result = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                            //  JOptionPane.showMessageDialog(  null,result);
                        } catch (UnsupportedEncodingException ex) {
                        }

                    } else {

                        ArrayList<String[]> rawMaterialData = p.getRMData();
                        for (String s[] : rawMaterialData) {
                            try {
                                String rmid = s[0];
                                String rmname = s[1];
                                String rmrate = s[2];
                                String rmunit = s[3];
                                String rmcode = s[4];
                                String rmtype = s[5];
                                String rmctg = s[6];
                                String rmshape = s[7];
                                String length = s[8];
                                String thickness = s[9];
                                String diameter = s[10];
                                String wall = s[11];
                                String density = s[12];
                                String width = s[13];
                                String indiameter = s[14];
                                String outdiam = s[15];
                                String volume = s[16];
                                String weight = s[17];
                                String transport = s[18];
                                String insp = s[19];
                                String rateFluct = s[20];
                                String scrap = s[21];
                                String icc = s[22];
                                String other = s[23];
                                String taxes = s[24];
                                String rmtotal = s[25];
                                String rTotal = s[26];
                                String fcWeight = s[27];
                                String sc_status = s[28];
                                String sc_recovery = s[29];
                                String sc_recovery_per = s[30];
                                String sc_weight = s[31].replace(" ", "");
                                String csArea = s[32];
                                String packaging = s[33];
                                String iccMonth=s[34];
                                String taxAmt=s[35];
                                String iccAmt=s[36];
                                String FluctAmt=s[37];

                                String quant = p.getQuantity1();
                                String total = p.getTotal();
                                String partDesc = p.getPartName();
                                String drwNo = p.getDrNo();
                                String drwrevNo = p.getDrRevNo();
                                String drwrevDate = p.getDrRevDate();
                                String scope = p.getScope();

                                String addEmpAPICall = "bom_detail_add?ref_assly_id=" + ref_ass_id + "&ref_enq_id=" + ref_enq_id + "&part_desc=" + URLEncoder.encode(partDesc, "UTF-8")
                                        + "&quantity=" + quant + "&drwg_no=" + drwNo + "&drwg_rev_no=" + drwrevNo + "&drwg_rev_date=" + URLEncoder.encode(drwrevDate, "UTF-8")
                                        + "&type=" + "m" + "&rm_id=" + rmid + "&rm_name=" + URLEncoder.encode(rmname, "UTF-8") + "&rm_rate=" + rmrate
                                        + "&rm_unit=" + rmunit + "&rm_code=" + URLEncoder.encode(rmcode, "UTF-8") + "&rm_type=" + URLEncoder.encode(rmtype, "UTF-8") + "&rm_ctg=" + URLEncoder.encode(rmctg, "UTF-8")
                                        + "&shape=" + rmshape + "&length=" + length + "&thick=" + thickness + "&diam=" + diameter
                                        + "&wall=" + wall + "&density=" + density + "&width=" + width + "&ID=" + indiameter + "&OD=" + outdiam + "&volume=" + volume
                                        + "&weight=" + weight + "&rm_total_cost=" + rTotal + "&transport=" + transport + "&inspection=" + insp + "&rate_fluct=" + rateFluct + "&scraprate=" + scrap + "&cs_area=" + csArea
                                        + "&ICC=" + icc + "&other=" + other + "&taxes=" + taxes + "&total_rm_cost=" + rmtotal + "&total_bom_cost=" + total + "&fc_weight=" + fcWeight + "&scrap_status=" + sc_status + "&scrap_recovery=" + sc_recovery + "&scrap_weight=" + sc_weight + "&scrap_rec_percent=" + sc_recovery_per + "&packaging=" + packaging + "&scope=" + scope + "&is_purchased_master=n"+"&taxes_amt="+taxAmt+"&rate_fluct_amt="+FluctAmt+"&icc_amt="+iccAmt+"&icc_month="+iccMonth;
                                String result = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
                                String bom_id = WebAPITester.getInsertedId(result);

                                ArrayList<String[]> pdata1 = p.getProcessData();

                                for (String s1[] : pdata1) {
                                    String procerssName = s1[0];
                                    String unitName = s1[1];
                                    String processCost = s1[2];
                                    String processLocationName = s1[3];
                                    String effRate = s1[4];
                                    String processOH = s1[5];
                                    String totalProcessCost = s1[6];
                                    String pid = s1[7];
                                    String otherExpence = s1[8];
                                    String setup_time = s1[9];
                                    String setup_cost = s1[10];
                                    String fixturing_cost = s1[11];
                                    String inspection_cost = s1[12];
                                    String transport_cost = s1[13];
                                    String handling_charges = s1[14];
                                    String handling_amt=s1[15];
                                    String fixturing_amt=s1[16];
                                    String setup_amt=s1[17];
                                    String localTrans_amt=s1[18];
                                    String rejection_amt=s1[19];
                                    String inspection_amt=s1[20];

                                    addEmpAPICall = "bomprocessdetail_add?ref_bom_id=" + bom_id + "&process_id=" + pid + "&process_name=" + URLEncoder.encode(procerssName, "UTF-8") + "&process_unit=" + unitName + "&process_rate=" + processCost
                                            + "&pro_loc=" + processLocationName + "&process_eff_rate=" + effRate + "&process_oh=" + processOH + "&pro_total_cost=" + totalProcessCost + "&setup_time=" + setup_time + "&setup_cost=" + setup_cost + "&fixturing_cost=" + fixturing_cost
                                            + "&inspection_cost=" + inspection_cost + "&local_transport=" + transport_cost + "&other_expences=" + otherExpence + "&handling_cost=" + handling_charges
                                            + "&handling_amt=" +handling_amt+"&fixturing_amt=" + fixturing_amt+"&inspection_amt=" + inspection_amt+"&local_trans_amt=" + localTrans_amt+"&rej_amt=" +rejection_amt+"&setup_amt=" + setup_amt;
                                    WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
//                                System.err.println(addEmpAPICall);
//                               'setup_time','setup_cost','fixturing_cost','inspection_cost','local_transport','other_expences','handling_cost','other_expences'
//   JOptionPane.showMessageDialog(  null,result);
                                }
                            } catch (UnsupportedEncodingException ex) {
                                Logger.getLogger(CreateQuotationForm.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
            exportExcel();
            EnquiriesForm process = new EnquiriesForm();
            //  RMEfficienctReport process = new RMEfficienctReport ( "Raw Material Efficiency" );
            scroll = new JScrollPane(process,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            //process.setBounds ( ( int ) ( width - process.getPreferredSize ().width ) / 2 , 5 , process.getPreferredSize ().width , process.getPreferredSize ().height );
            //scroll.setBounds ( 5 , 5 , width-25 , 605 );
            int leftMargin = (int) (width - process.getPreferredSize().width) / 2;
            process.setBounds(leftMargin, 5, process.getPreferredSize().width, 580);
            scroll.setBounds(leftMargin - 15, 5, width - leftMargin, 580);

            scroll.setBackground(Color.WHITE);
            scroll.setBorder(BorderFactory.createEmptyBorder());

            home.jPanel1.removeAll();
            home.jPanel1.setLayout(null);
            //home.jPanel1.add ( customer );
            home.jPanel1.add(scroll, BorderLayout.CENTER);
            //home.jPanel1.add ( bp1 );
            jLabel6.setText("  Enquiries");

            home.revalidate();
            home.repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Please filled data!");
        }
    }
}