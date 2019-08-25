/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.quotation;

/**
 *
 * @author Akshay
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

public class ExcelWriter {

    private static List<BomDr> employees =  new ArrayList<>();
    List<String> processColoumnName=new ArrayList<String>();
    List<String> coloumnList=new ArrayList<String>();
    private String enqNo;
    private String partType;
    private String AssemblyNo;
    private String AssemblyPartDesc;
    private String drwNo;
    private String drwRevNo;
    private String drwRevDate;
    

    // Initializing employees data to insert into the excel file

    public void exportDataInExcel(String enqId,String aslyId,String filePath){
        loadData(enqId,aslyId);
        loadColumns();
        try {
             Workbook workbook = new HSSFWorkbook();
//            Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
            /* CreationHelper helps us create instances of various things like DataFormat,
        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();
            // Create a Sheet
            Sheet sheet = workbook.createSheet("Employee");
            // Create a Font for styling header cells
//        Font headerFont = workbook.createFont();
//        headerFont.setBold(true);
//        headerFont.setFontHeightInPoints((short) 14);
//        headerFont.setColor(IndexedColors.RED.getIndex());

// Create a CellStyle with the font
//        CellStyle headerCellStyle = workbook.createCellStyle();
//        headerCellStyle.setFont(headerFont);
// Create a Row
         
        {
            Row enquiryRow= sheet.createRow(2);
            
            Cell cell =null; 
                    cell=enquiryRow.createCell(0);
                    cell.setCellValue("Enquiry No");
                    cell=enquiryRow.createCell(1);
                    cell.setCellValue(enqNo);

                    
            Row enquiryRow1= sheet.createRow(3);
                            
            Cell cell1 =null; 
                    cell1=enquiryRow1.createCell(0);
                    cell1.setCellValue("Part Type");
                    cell1=enquiryRow1.createCell(1);
                    cell1.setCellValue(partType);
            String assemblyNo="";
            if(partType.equals("Part/Component")){
                assemblyNo="Part No.";
            }else if(partType.equals("Assembly")){
                assemblyNo="Assembly No.";                
            }else if(partType.equals("SubAssembly")){
                assemblyNo="SubAssembly No.";
            }

            cell1 = enquiryRow1.createCell(3);

            cell1.setCellValue(assemblyNo);
            cell1 = enquiryRow1.createCell(4);
            cell1.setCellValue(AssemblyNo);
            cell1 = enquiryRow1.createCell(6);
            cell1.setCellValue("Assembly/Part Description");
            cell1 = enquiryRow1.createCell(7);
            cell1.setCellValue(AssemblyPartDesc);

            Row enquiryRow2= sheet.createRow(4);
            
            Cell cell2 = null;
            cell2 = enquiryRow2.createCell(0);
            cell2.setCellValue("Drw No");
            cell2 = enquiryRow2.createCell(1);
            cell2.setCellValue(drwNo);
            cell2 = enquiryRow2.createCell(3);
            cell2.setCellValue("Drw Revision No");
            cell2 = enquiryRow2.createCell(4);
            cell2.setCellValue(drwRevNo);
            cell2 = enquiryRow2.createCell(6);
            cell2.setCellValue("Drw Revision No");
            cell2 = enquiryRow2.createCell(7);
            cell2.setCellValue(drwRevDate);
//                            cell.setCellStyle(headerCellStyle);
        }           
            HSSFFont headerFont = (HSSFFont) workbook.createFont();
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headerFont.setFontHeightInPoints((short) 12);
           
            Row headerRow = sheet.createRow(6);
// Create Cell Style for formatting Date
            CellStyle cellStyle = workbook.createCellStyle();
//            cellStyle.setBorderBottom(BorderStyle.THIN);  
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle.setFont(headerFont);
            System.err.println((short)IndexedColors.ORANGE.getIndex());
            cellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND); 
// Create cells
            for (int i = 0; i < coloumnList.size(); i++) {
               // int a=sheet.addMergedRegion(new CellRangeAddress(5,7,i,i));
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(coloumnList.get(i));
                cellStyle.setFillForegroundColor((short)120);

//                cell
//                cell.setCellStyle(cellStyle);
            }
// Create Cell Style for formatting Date
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
            
// Create Other rows and cells with employees data
            int rowNum = 8;
            int cellCount=0;
            int startTableRow=9;
            for (BomDr bom : employees) {
                Row row = sheet.createRow(rowNum++);
                cellCount=0;
                row.createCell(cellCount).setCellValue(bom.getSrNo());
                row.createCell(++cellCount).setCellValue(bom.getPartNo());
                row.createCell(++cellCount).setCellValue(bom.getDrwNo());
                row.createCell(++cellCount).setCellValue(bom.getDrwRevNo());
                row.createCell(++cellCount).setCellValue(bom.getDrwRevDate());
                row.createCell(++cellCount).setCellValue(bom.getCategory());
                row.createCell(++cellCount).setCellValue(bom.getScope());
                row.createCell(++cellCount).setCellValue(bom.getQtyPerAssembly());
                row.createCell(++cellCount).setCellValue(bom.getMaterialAsPerDrg());
                row.createCell(++cellCount).setCellValue(bom.getEquvaentGrade());
                row.createCell(++cellCount).setCellValue(bom.getSpec());
                row.createCell(++cellCount).setCellValue(bom.getDiameter());
                row.createCell(++cellCount).setCellValue(bom.getCsArea());
                row.createCell(++cellCount).setCellValue(bom.getLength());
                row.createCell(++cellCount).setCellValue(bom.getDensity());
                row.createCell(++cellCount).setCellValue(bom.getWt());
                row.createCell(++cellCount).setCellValue(bom.getBasicRate());
                row.createCell(++cellCount).setCellValue(bom.getTransport());
                row.createCell(++cellCount).setCellValue(bom.getPackaging());
                row.createCell(++cellCount).setCellValue(bom.getInspection());
                row.createCell(++cellCount).setCellValue(bom.getTotalRmLanded());
                row.createCell(++cellCount).setCellValue(bom.getTotalRmCost());
                row.createCell(++cellCount).setCellValue(bom.getIcc());
                
                if(bom.getCategory().equals("Manufactured"))
                {
                ArrayList<BomProcessDr> bomprocess=bom.getBomProcess();
                for(String pcn:processColoumnName)
                {
                    String bomProcess="0";
                    String prName="";
                    for(BomProcessDr bprocess:bomprocess)
                    {
                        prName = bprocess.getProcessName();
                        if(prName.equals(pcn))
                        {
                            bomProcess=bprocess.getProcessCost();
                        }
                    }
                    row.createCell(++cellCount).setCellValue(bomProcess);
//                    cellCount++;
                }
                }else{
                    for(String pcn:processColoumnName)
                    {
                        String bomProcess = "0";
                        row.createCell(++cellCount).setCellValue(bomProcess);
//                        cellCount++;
                    }
                }
                row.createCell(++cellCount).setCellValue(bom.getStdBO());
                row.createCell(++cellCount).setCellValue(bom.getTotalBomCost());
                int quantity=Integer.parseInt(bom.getQtyPerAssembly());
                double bomCost=Double.parseDouble(bom.getTotalBomCost());
                double Total=bomCost*quantity;
                row.createCell(++cellCount).setCellValue(Total);



                
//                Cell dateOfBirthCell = row.createCell(2);
//                dateOfBirthCell.setCellValue(employee.getDateOfBirth());
//                dateOfBirthCell.setCellStyle(dateCellStyle);
//
//                row.createCell(3)
//                        .setCellValue(employee.getSalary());
            }
            int endTableRaw=rowNum;
            Row row = sheet.createRow(rowNum++);
            Cell cell=row.createCell(coloumnList.size()-1);
            cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
            String a="SUM(AB"+startTableRow+":"+"AB"+endTableRaw+")";
            cell.setCellFormula(a);
            cell.setCellStyle(cellStyle);
// Resize all columns to fit the content size
            for (int i = 0; i < coloumnList.size(); i++) {
                sheet.autoSizeColumn(i);
            }
    //            CellRangeAddress cRange=CellRangeAddress.valueOf("5,7,0,"+cellCount);
    // Write the output to a file
            FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
// Closing the workbook
            workbook.close();
        } catch (NoClassDefFoundError | IOException e) {
            System.err.println(e);
        }
    }
    public void loadData(String enqId,String asslyId)
    {
        {
            String addEmpAPICal = "enquiry_edit?enq_id=" + enqId;
            String result3 = WebAPITester.prepareWebCall(addEmpAPICal, StaticValues.getHeader(), "");
            if (!result3.contains("not found")) {
                if (result3 != null) {
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    JSONObject jObject1 = new JSONObject(result3);
                    Iterator<?> keys1 = jObject1.keys();

                    while (keys1.hasNext()) {
                        String key1 = (String) keys1.next();
                        Object value1 = jObject1.get(key1);
                        map1.put(key1, value1);
                    }

                    JSONArray records1 = (JSONArray) map1.get("data");

                    JSONObject um = null;//(JSONObject) map1.get("data");//st1.getJSONObject("CUSTOMER_NAME");
                    um = records1.getJSONObject(0);

                    enqNo = um.get("enq_no").toString();
                }
            }
        }
        {
            String addEmpAPICal = "assembly_edit?assly_id=" + asslyId;
            String result3 = WebAPITester.prepareWebCall(addEmpAPICal, StaticValues.getHeader(), "");
            if (!result3.contains("not found")) {
                if (result3 != null) {
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    JSONObject jObject1 = new JSONObject(result3);
                    Iterator<?> keys1 = jObject1.keys();

                    while (keys1.hasNext()) {
                        String key1 = (String) keys1.next();
                        Object value1 = jObject1.get(key1);
                        map1.put(key1, value1);
                    }

                    JSONArray records1 = (JSONArray) map1.get("data");

                    JSONObject um = null;//(JSONObject) map1.get("data");//st1.getJSONObject("CUSTOMER_NAME");
                    um = records1.getJSONObject(0);
                    AssemblyNo=um.get("assly_no").toString();
                    AssemblyPartDesc = um.get("assembly_desc").toString();
                    drwNo= um.get("drwg_no").toString();
                    drwRevNo= um.get("drwg_rev_no").toString();
                    drwRevDate= um.get("drwg_rev_date").toString();
                    partType= um.get("type").toString();
                    String quantity=um.get("quantity").toString();
                }
            }
        }
        String addEmpAPICall = "bompartdetails?assly_id=" + asslyId + "&enq_id="+enqId;
      
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
                employees=new ArrayList<BomDr>(); 
                processColoumnName=new ArrayList<String>();

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
                String equivalentGrade = "";
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
                String totalBomCost="";
                String scope="";
                String purchasedCost="";
                for (int i = 0; i < records.length(); i++) {
                    emp = records.getJSONObject(i);
                    BomDr bom=new BomDr();
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
                        equivalentGrade = emp.get("rm_type").toString();
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
                        rmtotal = emp.get("rm_total_cost").toString();
                        rmT=emp.get("total_rm_cost").toString();    
                        totalBomCost=emp.get("total_bom_cost").toString();
                        purchasedCost=emp.get("purchase_cost").toString();
//                        String FCWeight=emp.get("fc_weight").toString();
//                        String sc_staus=emp.get("scrap_status").toString();
//                        String sc_recovry=emp.get("scrap_recovery").toString();
//                        String sc_recovryPercent=emp.get("scrap_rec_percent").toString();
//                        String sc_weight=emp.get("scrap_weight").toString();
                        String csArea=emp.get("cs_area").toString();
                        scope=emp.get("scope").toString();
                        String packaging=emp.get("packaging").toString();
                        ArrayList<BomProcessDr> bomProcess=null; 
                        String stdBo="0";
                        if(!partDesc.equals(""))
                        {
                            if(category.equals("M"))
                            {
                                category="Manufactured";
                              
                                bomProcess=new ArrayList<BomProcessDr>();
                                
                                String addEmpAPICall1 = "bomprocessdetails?ref_bom_id=" + bomId;

                                String result4 = WebAPITester.prepareWebCall(addEmpAPICall1, StaticValues.getHeader(), "");
                                
                                if (!result4.contains("not found")) {
                                    if (result4 != null) {
                                        HashMap<String, Object> map1 = new HashMap<String, Object>();
                                        JSONObject jObject1 = new JSONObject(result4);
                                        Iterator<?> keys1 = jObject1.keys();

                                        while (keys1.hasNext()) {
                                            String key = (String) keys1.next();
                                            Object value = jObject1.get(key);
                                            map1.put(key, value);
                                        }
                                        JSONObject st1 = (JSONObject) map1.get("data");
                                        JSONArray records1 = (JSONArray) st1.getJSONArray("records");
                                        JSONObject emp1 = null;
                                        for (int i1 = 0; i1 < records1.length(); i1++) {
                                            BomProcessDr bomProcessDr=new BomProcessDr();
                                            emp1 = records1.getJSONObject(i1);
                                            int bomProcessId=Integer.parseInt(emp1.get("process_id").toString());
                                            String bomProName=emp1.get("process_name").toString();
                                            
                                            bomProcessDr.setProcessName(bomProName);
                                            bomProcessDr.setProcessId(bomProcessId);
                                            bomProcessDr.setProcessCost(emp1.get("pro_total_cost").toString());

                                            if(processColoumnName.isEmpty())
                                            {
                                                processColoumnName.add(bomProName);
                                            }else{
                                                boolean chk=true;

                                                for(String b:processColoumnName)
                                                {
                                                    if(bomProName.equals(b))
                                                    {
                                                        chk=false;
                                                    }
                                                }
                                                
                                                if(chk)
                                                {
                                                    processColoumnName.add(bomProName);
                                                }
                                            }
                                            bomProcess.add(bomProcessDr);
                                        }
                                    }
                                }
                            }else if(category.equals("P")){
                                category="Purchased";
                                stdBo=purchasedCost;
                            }

                            bom.setSrNo(i+"");
                            bom.setPartNo(partDesc);
                            bom.setDrwNo(drewNo);
                            bom.setDrwRevNo(drewRevNo);
                            bom.setDrwRevDate(revDate);
                            bom.setCategory(category);
                            bom.setScope(scope);
                            bom.setQtyPerAssembly(quant);
                            bom.setMaterialAsPerDrg(rmname);
                            bom.setEquvaentGrade(equivalentGrade);
                            bom.setSpec(rmshape);
                            bom.setDiameter(diameter);
                            bom.setCsArea(csArea);
                            bom.setLength(length);
                            bom.setDensity(density);
                            bom.setWt(weight);
                            bom.setBasicRate(rmrate);
                            bom.setTransport(transport);
                            bom.setPackaging(packaging);
                            bom.setInspection(insp);
                            bom.setTotalRmLanded(rmtotal);
                            bom.setTotalRmCost(rmT);
                            bom.setIcc(icc);
                            bom.setBomProcess(bomProcess);
                            bom.setTotalBomCost(totalBomCost);
                            bom.setStdBO(stdBo);
                        }
                    }catch(Exception e){}
                    employees.add(bom);
                }
            }
        }
    }
    public void loadColumns()
    {
        coloumnList=new ArrayList<String>();
        coloumnList.add("Sr. No.");
        coloumnList.add("Part No.");
        coloumnList.add("Drawing No.");
        coloumnList.add("Drawing Rev.");
        coloumnList.add("DrawingRev Date");
        coloumnList.add("Category.");
        coloumnList.add("Scope.");
        coloumnList.add("Qty/Assly");
        coloumnList.add("Material As per Drg.");
        coloumnList.add("Equivalent Gread Required.");
        coloumnList.add("SPEC.");
        coloumnList.add("Dia");
        coloumnList.add("c/s area");
        coloumnList.add("length");
        coloumnList.add("Density");
        coloumnList.add("wt");
        coloumnList.add("Basic Rate");
        coloumnList.add("Transport / Kg");
        coloumnList.add("Packing & Forwarding charges / Kg");
        coloumnList.add("Inspection Cost / kg");
        coloumnList.add("Total RM Landed Cost / kg");
        coloumnList.add("Total RM Cost / Pc");
        coloumnList.add("ICC 1.5% / month");
        for(String process:processColoumnName)
        {
            coloumnList.add(process);
        }
        coloumnList.add("Std B/O");
        coloumnList.add("Cost / pc");
        coloumnList.add("Total");

    }
    
    
    
    public static void exportExel()
    {
        Workbook workbook = new HSSFWorkbook();

                //Create a blank sheet
                HSSFSheet sheet = (HSSFSheet) workbook.createSheet("Customer Master");

                
                CellStyle style = workbook.createCellStyle();
                style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
                style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//                Font font = workbook.createFont();
//                font.setColor(IndexedColors.AUTOMATIC.getIndex());
//                style.setFont(font);
//                 data11[]={};
                //Iterate over data and write to sheet
//                Set<String> keyset = data11.keySet();
//                int rownum = 0;
//                for (String key : keyset) {
//                    Row row = sheet.createRow(rownum++);
//                    Object[] objArr = data11.get(key);
//                    int cellnum = 0;
//                    for (Object obj : objArr) {
//                        Cell cell = row.createCell(cellnum++);
//                        if (obj instanceof String) {
//                            cell.setCellValue((String) obj);
//                        } else if (obj instanceof Integer) {
//                            cell.setCellValue((Integer) obj);
//                        }
//                    }
//                }
//                
//                // Resize all columns to fit the content size
//                for (int i = 0; i < columns.length; i++) {
//                    sheet.autoSizeColumn(i);
//                }
//
//                File file = null;
//                try {
//
//                    Calendar c2 = Calendar.getInstance();
//                    SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMMyyyy_HH_mm_a");
//                    String strDate2 = sdf2.format(c2.getTime());
//
//                    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//                    jfc.setDialogTitle("Choose a directory to save your file: ");
//                    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//
//                    int returnValue = jfc.showSaveDialog(null);
//                    if (returnValue == JFileChooser.APPROVE_OPTION) {
//                        if (jfc.getSelectedFile().isDirectory()) {
//                            System.out.println("You selected the directory: " + jfc.getSelectedFile());
//                        }
//                    
//
//                    //Write the workbook in file system
//                    file = new File(jfc.getSelectedFile() + "\\" + "Customer Master Data " + strDate2 + ".xls");
//                    FileOutputStream fileop = new FileOutputStream(file);
//                    workbook.write(fileop);
//                    workbook.close();
//                    fileop.close();
//                    JOptionPane.showMessageDialog(null, "New File generated at " + file.getAbsolutePath());
//                    }
//                    
//                } catch (IOException Ie) {
//                    Ie.printStackTrace();
//                }

    }
}


