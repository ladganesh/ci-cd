/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

import trixware.erp.prodezydesktop.Utilities.DateTimeFormatter;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.StaticValues;
import static trixware.erp.prodezydesktop.Utilities.DB_Operations.INSERT_FG_CUS_MAP;
import trixware.erp.prodezydesktop.masters.BillOfMaterialUI;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Rajesh
 */
public class DB_Operations {

    //NAMME OF THE DATABASE
    protected static String DB_NAME;
    
    // Connnectino String, Driver manager etc
    protected static Connection con;
    protected static Connection con2;
    //PrintWriter pw = null ; 
    
    
    public static  Thread t = new Thread(){
               
                public void run(){
                    createTables();
                }
    };
    
    public static Connection getPreparedStatement() {

        try {

           File f = new File(StaticValues.dbName);
            if (!f.exists()) {
                t.start (); 
            }
            
//            File f2 = new File("error.log.txt");
//            pw = new PrinterWriter( f2.  );
//            if (!f2.exists()) {
//                try {
//                    f2.createNewFile();
//                } catch ( IOException ex ) {
//                    
//                }
//            }

            //  con = DriverManager.getConnection("jdbc:sqlite:E:\\Projects\\Manufacturing ERP Modules\\Manufacturing\\Sai Rubber Ind\\srirubber-v2.db");
            con = DriverManager.getConnection("jdbc:sqlite:"+StaticValues.dbName);
            System.out.println ( ""+StaticValues.dbName );

            //     System.out.println(" "+StaticValues.dbName);
        } catch (SQLException ex) {
            //System.out.println ( ""+StaticValues.dbName );
            JOptionPane.showMessageDialog(null, "" + ex.getMessage());
            System.out.println("DB not Connected");
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , ex.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }

        return con;
    }

    public static void createTables() {
        try {
            Connection con2 = DriverManager.getConnection("jdbc:sqlite:"+StaticValues.dbName);

             System.out.println ( ""+StaticValues.dbName );
            
            //String createSalesTable = "CREATE TABLE IF NOT EXISTS sales (  )"
            PreparedStatement st = con2.prepareStatement(deignationsTable);
            st.execute();
            
            
            st = con2.prepareStatement(gradesTable);
            st.execute();
            st = con2.prepareStatement(calibration_masterTable);
            st.execute();
            st = con2.prepareStatement(contactPersonMasterTable);
            st.execute();
            st = con2.prepareStatement(customerMasterTable);
            st.execute();
            st = con2.prepareStatement(employeeTable);
            st.execute();
            st = con2.prepareStatement(finishedGoodsTable);
            st.execute();
            st = con2.prepareStatement(machineMasterTable);
            st.execute();
            st = con2.prepareStatement(raw_materialMaster);
            st.execute();
            st = con2.prepareStatement(supplierMasterTable);
            st.execute();
            st = con2.prepareStatement(createTestingTable);
            st.execute();
            st = con2.prepareStatement(createUserMaster);
            st.execute();
            st = con2.prepareStatement(createSalaryBandTable);
            st.execute();
            st = con2.prepareStatement(createEmpNatureTable);
            st.execute();
            st = con2.prepareStatement(createUOMTable);
            st.execute();
            st = con2.prepareStatement(createPackSizeTable);
            st.execute();
            st = con2.prepareStatement(createRawMaterialTable);
            st.execute();
            st = con2.prepareStatement(createCategoryTable);
            st.execute();
            st = con2.prepareStatement(createSegmentTable);
            st.execute();
            st = con2.prepareStatement(createIndustryTable);
            st.execute();
            st = con2.prepareStatement(createRejectionReasonTable);
            st.execute();
            st = con2.prepareStatement(create_RM_Inventory_Table);
            st.execute();
            st = con2.prepareStatement(create_Finished_Goods_Table);
            st.execute();
            st = con2.prepareStatement(create_Current_Stock_Table);
            st.execute();
            st = con2.prepareStatement(create_BOM_Table);
            st.execute();
            st = con2.prepareStatement(create_saledOrder_Table);
            st.execute();
            st = con2.prepareStatement(create_po_order_details_Table);
            st.execute();
            st = con2.prepareStatement(create_wip_Table);
            st.execute();
            st = con2.prepareStatement(create_processMaster_Table);
            st.execute();
            st = con2.prepareStatement(create_processMachine_Table);
            st.execute();
            st = con2.prepareStatement(create_FG_PR_MACH_MAP_Table);
            st.execute();
            st = con2.prepareStatement(create_FG_CUST_MAP_Table);
            st.execute();
            
            st = con2.prepareStatement(create_PROCESS_EMPLOYEE_MAP_Table);
            st.execute();    
            
            st = con2.prepareStatement(create_INSERT_DEPARTMENT);
            st.execute();    
            
            st = con2.prepareStatement(create_INSERT_RESOURCES);
            st.execute(); 
            //----------------------------------------------------------------
            st = con2.prepareStatement(BATCH_DETAILS);
            st.execute(); 
            
            st = con2.prepareStatement(BATCH_MASTER);
            st.execute(); 
            
            
            st = con2.prepareStatement(PLANT_MASTER);
            st.execute(); 
            
            st = con2.prepareStatement(REF_DOCUMENTS);
            st.execute(); 
            
            st = con2.prepareStatement(REF_DOC_MASTER);
            st.execute(); 
            
            st = con2.prepareStatement(dailySetupMaster );
            st.execute(); 
            
            st = con2.prepareStatement(dailyReport);
            st.execute(); 
            
            st = con2.prepareStatement(dailyReport3);
            st.execute(); 
            
            st = con2.prepareStatement(op_wip2);
            st.execute(); 
            
            st = con2.prepareStatement(shifts);
            st.execute(); 
            
            st = con2.prepareStatement( xl_import_test);
            st.execute(); 
            
            st = con2.prepareStatement( USERROLEPERM    );
            st.execute(); 
            
            st = con2.prepareStatement( createView_RM_ABC_Analysis    );
            st.execute(); 
            
             st = con2.prepareStatement( createView_FG_ABC_Analysis    );
            st.execute(); 
            
            st = con2.prepareStatement( createTriggerWIPfromDR    );
            st.execute();
            
            st = con2.prepareStatement( alarms_Setting  );
            st.execute();
            addToAlarmSettings() ;
            
            st = con2.prepareStatement( createRolesTable    );
            st.execute();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "" + e.getMessage());
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }
    }
    
    
    public static void addToAlarmSettings(){
        
            try{
                
                Connection con = getPreparedStatement () ;
                
                String recordsExist = "select count( ALARM_NAME ) from ALARMS_SETTINGS" ;
                ResultSet rs = DB_Operations.executeSingle ( recordsExist);
                int alarmCount = 0;
                try{
                    alarmCount = rs.getInt(1);
                    rs.close();
                }catch( Exception e ){
                    
                }
                
               
                if( alarmCount != 4 ){
                    String alarmsDefaultSettingQuery = "insert into ALARMS_SETTINGS (  ALARM_NAME , ALARM_STATE , ALARMS_FREQUENCY_IN_MS ) VALUES ( ?,?,? )" ;

                    PreparedStatement st = con.prepareStatement ( alarmsDefaultSettingQuery );

                    st.setString( 1, "PPM for the day" );
                    st.setString( 2, "true" );
                    st.setInt( 3, 10000 );
                    st.addBatch();

                    st.setString( 1, "Total production for the day" );
                    st.setString( 2, "true" );
                    st.setInt( 3, 10000 );
                    st.addBatch();

                    st.setString( 1, "Raw Materials stock close to ROL" );
                    st.setString( 2, "true" );
                    st.setInt( 3, 10000 );
                    st.addBatch();

                    st.setString( 1, "Rejections for the day" );
                    st.setString( 2, "true" );
                    st.setInt( 3, 10000 );
                    st.addBatch();

                    st.executeBatch ();
                }
            }catch( SQLException e  ){
                StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }        
   
    }
    
    
    
    // TABLES NAMES IN THE DATABASE
    protected static String TBL_CALIBRATION_MASTER = "calibration_master";
    protected static String TBL_CONTACT_PERSON_MASTER = "contact_person";
    protected static String TBL_CUSTOMER_MASTER = "customer";
    protected static String TBL_EMPLOYEE_MASTER = "employee";
    protected static String TBL_FINISHED_GOODS_MASTER = "finished_goods";
    protected static String TBL_MACHINE_MASTER = "machine";
    protected static String TBL_RAW_MATERIAL_MASTER = "raw_material";
    protected static String TBL_RAW_MATERIAL_TYPE_MASTER = "raw_material_type";
    protected static String TBL_SUPPLIER_MASTER = "supplier";
    protected static String TBL_TESTING_MASTER = "testing";
    protected static String TBL_GRADE_MASTER = "Grades";
    protected static String TBL_DESIGNATION_MASTER = "Designations";
    protected static String TBL_EMP_NATURE = "EMP_NATURE";
    protected static String TBL_SAL_BAND = "SalaryBand";
    protected static String TBL_UOM = "unit_meas";
    protected static String TBL_pack_Size = "pack_size";
    protected static String TBL_RawMaterial_Type = "raw_material_type";
    protected static String TBL_Category = "category";
    protected static String TBL_Segment = "segment";
    protected static String TBL_Industry = "industry";
    protected static String TBL_Rejection_Reason = "rejection_reasons";
    protected static String TBL_RM_STOCK = "RMStock";
    protected static String TBL_FG_STOCK = "FGStock";
    protected static String TBL_STOCK = "Stock";
    protected static String TBL_BOM = "BillOfMaterial";
    protected static String TBL_FG_CUS_MAP = "FG_CUSTOMER_MAP";
    protected static String TBL_PR_EMP_MAP = "PROCESS_EMPLOYEE_MAP";
    protected static String TBL_DOCTYPE_MAP = "REF_DOC_MASTER";
    
    // NAMES OF COLUMNS
    //LIST OF INSERT QUERIES
    protected static String INSERT_CALIBRATION_MASTER = "insert into " + TBL_CALIBRATION_MASTER + "( EQUIPPMENT_NAME,	CALIBRATION_DATE,	NEXT_DUE_DATE,	CALIBRATION_AGENCY,	CALIBRATION_FREQUENCY,	NABL_CERTIFICATE_NO,	CERTIFICATE_COPY,	AGENCY_CONTACT_DETAILS,	INSTR_LIST,     SUPPLIER,   EQP_MAKE,   CREATED_ON,	EDITED_ON,	EDITED_BY) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?  )";
    protected static String INSERT_CONTACT_PERSON_MASTER = "insert into " + TBL_CONTACT_PERSON_MASTER + "( CONT_EMAIL,	CONT_NO,	 CUST_ID,	SUPP_ID,	CONT_NAME, CREATED_ON,	EDITED_ON,	EDITED_BY) values ( ?,?,?,?,?,?,?,?)";
    protected static String INSERT_CUSTOMER_MASTER = "insert into " + TBL_CUSTOMER_MASTER + "( CUSTOMER_NAME, CUST_ADDRESS,	CONTACT_LL,	CONTACT_BRD,	WEEKLY_OFF,	GST_NO,	OUR_VENDOR_CODE,	PAYMENT_TERMS, CITY, SEGMENT, INDUSTRY, CATEGORY,	CREATED_ON,	EDITED_ON,	EDITED_BY) values ( ?,?,?,?,?,?,?,?,?,?, ?, ?,?,?,?)";
    protected static String INSERT_EMPLOYEE_MASTER = "insert into " + TBL_EMPLOYEE_MASTER + "( EMP_NAME, EMP_NO,	JOIN_DT,	PHOTO,	GENDER,	DEPARTMENTID,	ROLE,	EDUCATION,	CELL_NO,	DOB,	ADD_LOCAL,	ADD_PERM,	JOIN_SKILL,	CURR_SKILL,	AUTH_SKILL,	DISEASE,	PAN_NO,	AADHAR_NO,	PF_NO,	ESIC_NO,	SAL_BAND,   IS_USER, USERNAME, PASSWORD,   CREATED_ON, EDITED_ON, EDITED_BY) values (  ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?, ?, ? , ? , ?, ?, ? , ?, ?, ? , ?, ? )";
    protected static String INSERT_FINISHED_GOODS_MASTER = "insert into " + TBL_FINISHED_GOODS_MASTER + "( FG_PART_NO, FG_PART_UNIQUE_ID,	PART_NAME,	SALES_RATE,    CREATED_ON,	EDITED_ON,	EDITED_BY, NOTES, PROD_COST, FG_UOM_ID) values ( ?,?,?,?,?,?,?,?,? , ?)";
    protected static String INSERT_MACHINE_MASTER = "insert into " + TBL_MACHINE_MASTER + "( MACHINE_NO, MAKE,	BED_SIZE,	HEATER_QTY,	YR_OF_COMM,	CR_MAINT_RTG,	MCH_IMG,	CR_MCH_OWN, MACH_OWN_IMG,	MCH_BRKD_RCD,	PM_ELE,	PM_MEC,	PM_HYD,	PM_PNM,	PM_CLIT,	PRSS_GAUGE,	TIMER,	TEMP_CTRL,   OP_RATE_HR, AVL_HRS, DAVLHRS,   INSTR_LIST,   CREATED_ON, EDITED_ON, EDITED_BY) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    protected static String INSERT_RAW_MATERIAL_MASTER = "insert into " + TBL_RAW_MATERIAL_MASTER + "(	RM_TYPE, RM_NAME, RM_CTG,	RM_AUTH_SUPP,	RM_RATE,	RM_CRIT,	RM_EC_NO,	RM_CAS_NO,	GST_NO,	RM_CLASS, REORDER_LEVEL,  CREATED_ON,	EDITED_ON,	EDITED_BY, RMM_UOM_ID) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?)";
    protected static String INSERT_SUPPLIER_MASTER = "insert into " + TBL_SUPPLIER_MASTER + "( SUPP_NAME,	SUPP_ADD,	SUPP_CNT_LL,	SUPP_CNT_BRD,	WEEK_OFF,	GST_NO,	VENDOR_CODE,	PAYT_TERMS, CATEGORY, SEGMENT, INDUSTRY,  CITY, AUTHORIZED_RM,	CREATED_ON,	EDITED_ON,	EDITED_BY) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ? )";
    protected static String INSERT_TESTING_MASTER = "insert into " + TBL_TESTING_MASTER + "( RAW_MATERIAL,	BTC_NO,	TEST_DT,	TEST,	TEST_RQ,	TEST_FREQ,	CREATED_ON,	EDITED_ON,	EDITED_BY) values ( ?,?,?,?,?,?,?,?,? )";
    protected static String INSERT_GRADES_MASTER = "INSERT INTO " + TBL_GRADE_MASTER + " (GradeName )  values ( ? )";
    protected static String INSERT_DESIGNATIONS_MASTER = "INSERT INTO " + TBL_DESIGNATION_MASTER + " (DesgTitle )  values ( ? )";
    protected static String INSERT_SALARYBAND_MASTER = "INSERT INTO " + TBL_SAL_BAND + " (SB_RANGE )  values ( ? )";
    protected static String INSERT_UOM_MASTER = "INSERT INTO " + TBL_UOM + " (UOM )  values ( ? )";
    protected static String INSERT_EMPNATURE_MASTER = "INSERT INTO " + TBL_EMP_NATURE + " (NATURE )  values ( ? )";
    protected static String INSERT_PACKSIZE_MASTER = "INSERT INTO " + TBL_pack_Size + " (PackSize )  values ( ? )";
    protected static String INSERT_RAWMTYPE_MASTER = "INSERT INTO " + TBL_RawMaterial_Type + " (RawMaterial )  values ( ? )";
    protected static String INSERT_CATEGORY_MASTER = "INSERT INTO " + TBL_Category + " (Category )  values ( ? )";
    protected static String INSERT_SEGMENT_MASTER = "INSERT INTO " + TBL_Segment + " (Segment )  values ( ? )";
    protected static String INSERT_INDUSTRY_MASTER = "INSERT INTO " + TBL_Industry + " (Industry )  values ( ? )";
    protected static String INSERT_REJETIONREASON_MASTER = "INSERT INTO " + TBL_Rejection_Reason + " (RR_DESC)  values ( ? )";
    protected static String INSERT_DOC_TYPE_MASTER = "INSERT INTO " + TBL_DOCTYPE_MAP + " (RDM_NAME )  values ( ? )";
    protected static String INSERT_RM_STOCK_TRN = "INSERT INTO " + TBL_RM_STOCK + " ( `RMI_ID`, `RM_ITEM_ID`, `OPENING` ,`RECEIVED` ,`USED` ,`CLOSING` , `CREATED_ON`,`EDITED_ON`,`EDITED_BY`  )  values ( ?,?,?,?,?,?,?,?,? )";
    protected static String INSERT_FG_STOCK_TRN = "INSERT INTO " + TBL_FG_STOCK + " ( `FG_ID`, `FG_iTEM_ID`, `OPENING` ,`RECEIVED` ,`USED` ,`CLOSING` , `CREATED_ON`,`EDITED_ON`,`EDITED_BY`  )  values ( ?,?,?,?,?,?,?,?,? )";
    protected static String INSERT_BOM = "INSERT INTO " + TBL_BOM + " ( FG_ID , RM_ID, NT_WT, GS_WT, TOTAL_WT , CREATED_ON , EDITED_ON , EDITED_BY ) VALUES ( ?,?,?,?,?,?,?,? )";
    protected static String INSERT_FG_CUS_MAP = "INSERT INTO "+TBL_FG_CUS_MAP+" ( M_FG_ID, M_CUST_ID, SALES_RATE, S_UOM_ID, CREATED_ON, EDITED_ON, EDITED_BY ) VALUES ( ?,?,?,?,?,?,? )";
    protected static String INSERT_PR_EMP_MAP = "INSERT INTO "+TBL_PR_EMP_MAP+" ( M_PR_ID, M_EMP_ID, EFFICIENCY, CREATED_ON, EDITED_ON, EDITED_BY ) VALUES ( ?,?,?,?,?,? )";
    protected static String INSERT_PROCESS = "INSERT INTO PROCESS_MASTER (  'PROCESS_NAME' ,  'PROCESS_DETAILS' , 'IS_BATCH_WISE', 'IS_ITEM_WISE') VALUES ( ?,? ,?,?) ";
    protected static String INSERT_DEPARTMENT = "INSERT INTO 'DEPARTMENTS' ( `DNAME`) VALUES ( ? )" ;
    protected static String INSERT_RESOURCES = "INSERT INTO `RESOURCE` (  `RSNAME` ) VALUES (  ? )";
    protected static String INSERT_USER_ROLES = "INSERT INTO `userRoles` (  `RoleName` ) VALUES (  ? )";
    
    //LIST OF UPDATE QUERIES
    protected static String UPDATE_CALIBRATION_MASTER = "update " + TBL_CALIBRATION_MASTER + " set  EQUIPPMENT_NAME = ?,	CALIBRATION_DATE = ?,	NEXT_DUE_DATE = ?,	CALIBRATION_AGENCY = ?,	CALIBRATION_FREQUENCY = ?,	NABL_CERTIFICATE_NO = ?,	CERTIFICATE_COPY = ?,	AGENCY_CONTACT_DETAILS = ?,	INSTR_LIST = ?,     SUPPLIER  = ?,   EQP_MAKE  = ?,	EDITED_ON=?,	EDITED_BY=?  where EQUIPPMENT_ID = ?";
    protected static String UPDATE_CONTACT_PERSON_MASTER = "update " + TBL_CONTACT_PERSON_MASTER + " set  CONT_EMAIL = ?,	CONT_NO = ?, 	 CONT_NAME = ?, EDITED_ON = ?,	EDITED_BY = ?  WHERE CUST_ID = ?   AND   SUPP_ID = ?   AND	CONT_PER_ID = ?";
    protected static String UPDATE_CUSTOMER_MASTER = "update " + TBL_CUSTOMER_MASTER + " set  CUSTOMER_NAME = ?, CUST_ADDRESS = ? ,	CONTACT_LL = ? ,	CONTACT_BRD = ? ,	WEEKLY_OFF = ? ,	GST_NO = ? ,	OUR_VENDOR_CODE = ? ,	PAYMENT_TERMS = ? ,  CITY = ?, SEGMENT = ?, INDUSTRY = ?, CATEGORY = ?,	EDITED_ON = ? ,	EDITED_BY = ?  where CUSTOMER_ID = ?";
    protected static String UPDATE_EMPLOYEE_MASTER = "update " + TBL_EMPLOYEE_MASTER + " set  EMP_NAME = ? , EMP_NO = ?,	JOIN_DT = ?,	PHOTO = ?,	GENDER = ?,	DEPARTMENTID = ?,	ROLE = ?,	EDUCATION = ?,	CELL_NO = ?,	DOB = ?,	ADD_LOCAL = ?,	ADD_PERM = ?,	JOIN_SKILL = ?,	CURR_SKILL = ?,	AUTH_SKILL = ?,	DISEASE = ?,	PAN_NO = ?,	AADHAR_NO = ?,	PF_NO = ?,	ESIC_NO = ?,	SAL_BAND = ?,  IS_USER = ?, USERNAME = ?, PASSWORD = ?,     EDITED_ON = ?, EDITED_BY = ? where EmployeePK = ?";
    protected static String UPDATE_FINISHED_GOODS_MASTER = "update " + TBL_FINISHED_GOODS_MASTER + " set  FG_PART_NO=?, FG_PART_UNIQUE_ID=?,	PART_NAME=?, SALES_RATE=?,	EDITED_ON=?,	EDITED_BY=? , NOTES = ? , PROD_COST=?, FG_UOM_ID = ?  where FG_ID=? ";
    protected static String UPDATE_MACHINE_MASTER = "update " + TBL_MACHINE_MASTER + " set  MACHINE_NO = ? , MAKE = ? ,	BED_SIZE = ? ,	HEATER_QTY = ? ,	YR_OF_COMM = ? ,	CR_MAINT_RTG = ? ,	MCH_IMG = ? ,	CR_MCH_OWN = ? , MACH_OWN_IMG = ? ,	MCH_BRKD_RCD = ? , 	PM_ELE = ? ,	PM_MEC = ? ,	PM_HYD = ? ,	PM_PNM = ? ,	PM_CLIT = ? ,	PRSS_GAUGE = ? ,	TIMER = ? ,	TEMP_CTRL = ? ,  OP_RATE_HR =  ?, AVL_HRS  = ? , DAVLHRS  = ? ,  INSTR_LIST = ?,  EDITED_ON = ? , EDITED_BY = ?  where MCH_ID = ? ";
    protected static String UPDATE_RAW_MATERIAL_MASTER = "update " + TBL_RAW_MATERIAL_MASTER + " set RM_TYPE = ?, RM_NAME = ?, RM_CTG = ?,	RM_AUTH_SUPP = ?,	RM_RATE = ?,	RM_CRIT = ?,	RM_EC_NO = ?,	RM_CAS_NO = ?,	GST_NO = ?,	RM_CLASS = ?, REORDER_LEVEL = ?,  	EDITED_ON = ?,	EDITED_BY = ? , RMM_UOM_ID = ?  where RM_ID = ?";
    protected static String UPDATE_SUPPLIER_MASTER = "update " + TBL_SUPPLIER_MASTER + " set  SUPP_NAME = ?,	SUPP_ADD = ?,	SUPP_CNT_LL= ?,	SUPP_CNT_BRD= ?,	WEEK_OFF= ?,	GST_NO= ?,	VENDOR_CODE= ?,	PAYT_TERMS= ?,    SEGMENT = ?, INDUSTRY=?, CATEGORY=?, CITY = ?,	AUTHORIZED_RM= ?,	EDITED_ON= ?,	EDITED_BY= ? where SUPP_ID = ?";
    protected static String UPDATE_TESTING_MASTER = "update " + TBL_TESTING_MASTER + " set  RAW_MATERIAL = ?,	BTC_NO = ?,	TEST_DT = ?,	TEST = ?,	TEST_RQ = ?,	TEST_FREQ = ?,	EDITED_ON = ?,	EDITED_BY = ?  where TEST_ID = ? ";
    protected static String UPDATE_GRADES_MASTER = "update " + TBL_GRADE_MASTER + " set GradeName = ? where GradeID = ?";
    protected static String UPDATE_DESIGNATIONS_MASTER = "update " + TBL_DESIGNATION_MASTER + " SET DesgTitle = ? WHERE DesgID =  ? ";
    protected static String UPDATE_SALARYBAND_MASTER = "update " + TBL_SAL_BAND + " set SB_RANGE = ?  where SB_ID = ? ";
    protected static String UPDATE_UOM_MASTER = "update " + TBL_UOM + " set UOM = ? where  UOM_ID =  ? ";
    protected static String UPDATE_EMPNATURE_MASTER = "update " + TBL_EMP_NATURE + " SET NATURE  = ? where NATURE_ID =  ? ";
    protected static String UPDATE_PACKSIZE_MASTER = "update " + TBL_pack_Size + " set PackSize = ? where PackSize_ID = ?";
    protected static String UPDATE_RAWMTYPE_MASTER = "update " + TBL_RawMaterial_Type + " SET RawMaterial = ? WHERE RMType_ID =  ? ";
    protected static String UPDATE_CATEGORY_MASTER = "update " + TBL_Category + " set Category = ?  where Category_ID = ? ";
    protected static String UPDATE_SEGMENT_MASTER = "update " + TBL_Segment + " set Segment = ? where  Segment_ID =  ? ";
    protected static String UPDATE_INDUSTRY_MASTER = "update " + TBL_Industry + " SET Industry  = ? where Industry_ID =  ? ";
    protected static String UPDATE_REJECTIONREASONS_MASTER = "update " + TBL_Rejection_Reason + " SET RR_DESC  = ? where RR_ID =  ? ";
    protected static String UPDATE_DOC_TYPE_MASTER = "UPDATE " + TBL_DOCTYPE_MAP + " SET  RDM_NAME =  ?  WHERE RDM_UID = ?";
    protected static String UPDATE_STOCK = "update " + TBL_STOCK + " set   OPENING = ?, RECEIVED = ?,	USED = ?,	CLOSING = ?,   EDITED_ON = ?,	EDITED_BY = ?  where REF_ID = ? ";
    protected static String UPDATE_BOM = "update " + TBL_BOM + "  set NT_WT = ?, GS_WT = ? , TOTAL_WT = ? , EDITED_ON = ? , EDITED_BY =? where RM_ID = ? AND   BOM_ID = ?  AND FG_ID = ? ";
    protected static String UPDATE_RM_STOCK_TRN = "UPDATE " + TBL_RM_STOCK + " SET  `OPENING` = ? ,`RECEIVED` = ?,`USED` = ? ,`CLOSING` = ? , `CREATED_ON` = ?  ,`EDITED_ON` = ? ,`EDITED_BY` = ?  WHERE `RMI_ID` = ? ";
    protected static String UPDATE_FG_STOCK_TRN = "UPDATE " + TBL_FG_STOCK + " SET  `OPENING = ? ` ,`RECEIVED` = ? ,`USED` = ? ,`CLOSING` = ? , `CREATED_ON` = ?,`EDITED_ON` = ?,`EDITED_BY` = ? WHERE `FG_ID` = ? ";
    protected static String UPDATE_FG_CUS_MAP = "UPDATE FG_CUSTOMER_MAP SET M_FG_ID = ?, M_CUST_ID = ?, SALES_RATE = ?, S_UOM_ID = ?,  EDITED_ON = ?, EDITED_BY = ? WHERE FG_C_ID = ?";
    protected static String UPDATE_PR_EMP_MAP = "UPDATE PROCESS_EMPLOYEE_MAP SET M_PR_ID = ?, M_EMP_ID = ?, EFFICIENCY = ?,  EDITED_ON = ?, EDITED_BY  = ? WHERE PR_EMP_ID = ?";
    protected static String UPDATE_PROCESS = "UPDATE PROCESS_MASTER SET  PROCESS_NAME = ?,  PROCESS_DETAILS = ? , IS_BATCH_WISE = ?, IS_ITEM_WISE = ?  WHERE PROCESS_ID = ?" ; 
    protected static String UPDATE_DEPARTMENT = "UPDATE 'DEPARTMENTS'  SET `DNAME` = ? WHERE `DID` = ?" ;
    protected static String UPDATE_RESOURCES = "UPDATE `RESOURCE` SET `RSNAME` = ? WHERE  `RSID` = ?";
     protected static String UPDATE_ROLES = "UPDATE `userRoles` SET `RoleName` = ? WHERE  `Role_ID` = ?";

    
    
    //LIST OF SELECT QUERIES
    protected static String SELECT_EMPLOYEE_MASTER = "select *from " + TBL_EMPLOYEE_MASTER;
    protected static String SELECT_MACHINE_MASTER = "select * from " + TBL_MACHINE_MASTER;
    protected static String SELECT_CALIBRATION_MASTER = "select * from " + TBL_CALIBRATION_MASTER;
    protected static String SELECT_CONTACT_PERSON_MASTER = "select * from " + TBL_CONTACT_PERSON_MASTER;
    protected static String SELECT_CUSTOMER_MASTER = "select * from " + TBL_CUSTOMER_MASTER;
    protected static String SELECT_FINISHED_GOODS_MASTER = "select fg_id, FG_PART_NO as 'Part No', FG_PART_UNIQUE_ID as 'Part Id',	PART_NAME as 'FG Name', SALES_RATE as 'Sale rate / unit',  NOTES as 'Remarks/Notes', PROD_COST as 'Production Cost' , (SELECT UOM FROM unit_meas where UOM_ID in (FG_UOM_ID)) as 'FG_UOM_ID' from " + TBL_FINISHED_GOODS_MASTER;
    protected static String SELECT_RAW_MATERIAL_MASTER = "select RM_ID, RM_TYPE,  RM_NAME, RM_CTG, RM_RATE, REORDER_LEVEL, (SELECT UOM FROM unit_meas where UOM_ID in (RMM_UOM_ID)) as UOM from " + TBL_RAW_MATERIAL_MASTER;
    protected static String SELECT_RAW_MATERIAL_TYPE_MASTER = "select * from " + TBL_RAW_MATERIAL_TYPE_MASTER;
    protected static String SELECT_SUPPLIER_MASTER = "select * from " + TBL_SUPPLIER_MASTER;
    protected static String SELECT_TESTING_MASTER = "select * from " + TBL_TESTING_MASTER;
    protected static String SELECT_GRADE_MASTER = "select * from " + TBL_GRADE_MASTER;
    protected static String SELECT_DESIGNATION_MASTER = "select * from " + TBL_DESIGNATION_MASTER;
    protected static String SELECT_EMP_NATURE = "select * from " + TBL_EMP_NATURE;
    protected static String SELECT_SAL_BAND = "select * from " + TBL_SAL_BAND;
    protected static String SELECT_UOM = "select * from " + TBL_UOM;
    protected static String SELECT_PACKSIZE_MASTER = "select * from  " + TBL_pack_Size;
    protected static String SELECT_RAWMTYPE_MASTER = "select * from  " + TBL_RawMaterial_Type;
    protected static String SELECT_CATEGORY_MASTER = "select * from  " + TBL_Category;
    protected static String SELECT_SEGMENT_MASTER = "select * from  " + TBL_Segment;
    protected static String SELECT_INDUSTRY_MASTER = "select * from  " + TBL_Industry;
    protected static String SELECT_REJECTION_REASON_MASTER = "select * from  " + TBL_Rejection_Reason;
    protected static String SELECT_REF_DOC_MASTER = "select * from  " + TBL_DOCTYPE_MAP;
    protected static String SELECT_RM_STOCK_TRN = "select * from  " + TBL_RM_STOCK;
    protected static String SELECT_FG_STOCK_TRN = "select * from  " + TBL_FG_STOCK;
    protected static String SELECT_STOCK = "select * from  " + TBL_STOCK;
    protected static String SELECT_BOM = "select * from  " + TBL_BOM;
    protected static String SELECT_PROCESSES = "select * from  PROCESS_MASTER";
    protected static String SELECT_DEPARTMENTS = "select * from  DEPARTMENTS";
    protected static String SELECT_RESOURCES = "select * from  RESOURCE";
    protected static String SELECT_USERROLES = "select * from  userRoles";
    public static String SELECT_FG_CUS_MAP = "SELECT FG_C_ID,  ( SELECT PART_NAME FROM finished_goods  WHERE FG_ID IN (M_FG_ID)) as 'Poduct', ( SELECT CUSTOMER_NAME FROM customer WHERE CUSTOMER_ID IN (M_CUST_ID)) as 'Customer', SALES_RATE as 'Sale Rate', (SELECT UOM FROM unit_meas where UOM_ID in ( SELECT FG_UOM_ID FROM finished_goods A WHERE FG_ID IN (M_FG_ID))) as 'Unit of Measurement for Product' FROM "+TBL_FG_CUS_MAP ;
    public static String SELECT_PR_EMP_MAP = "SELECT PR_EMP_ID,  ( SELECT PROCESS_NAME FROM PROCESS_MASTER  WHERE PROCESS_ID IN (M_PR_ID)) as 'Processes', ( SELECT EMP_NAME FROM employee WHERE EmployeePK IN (M_EMP_ID)) as 'Employee', EFFICIENCY as 'Efficiency' FROM "+TBL_PR_EMP_MAP ;

    static String res = "record inserted successfuly !";

    
    public static ResultSet executeRandom(String u, String p) {
        String test = "select EmployeePK,  EMP_NAME, USERNAME, PASSWORD, DEPARTMENTID, ROLE, IS_USER from employee WHERE USERNAME =  ?  AND PASSWORD = ?";

        ResultSet rs;
        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(test);

            pst.setString(1, u);
            pst.setString(2, p);
            rs = pst.executeQuery();

            //    System.out.println( rs.getString("USERNAME") ); 
        } catch (SQLException e1) {
            res = "Error DBOP : " + e1.getMessage();
            System.out.println(res);
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            return null;
        }
        return rs;

    }

    /**
     * This method reads a random query from the classes and return a result set
     * with selective columns of the table
     *
     */
    public static ResultSet executeSingle(String q) {

        ResultSet rs = null;
        PreparedStatement pst  = null;
        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();
            pst = con.prepareStatement(q);
            rs = pst.executeQuery();
         
        } catch (SQLException e1) {
            res = e1.getMessage();
            System.out.println(res);
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            return null; 
        }finally{
          // try{ rs.close();   }catch(Exception e){ }
//           try{ pst.close(); 
//           }
//           catch(Exception e){
//               System.out.println( e.getMessage() );
//           }
        }
        return rs;
    }
    
   
        
    
    public static int executeInsertRandom(String q) {

       int result = -1;
       PreparedStatement pst = null; 
       ResultSet rs = null;  
        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();
            
            pst = con.prepareStatement(q);
            result = pst.executeUpdate();
            db= null;
            
             rs = pst.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getInt(1);
            }
            
        } catch (Exception e1) {
            res = e1.getMessage();
            System.out.println(res);
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }finally{    
            try{
                pst.close (); 
                rs.close();
            }catch( Exception e ){
                System.out.println(e.getMessage ());
//                StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }
        
        return result ;
    }
    
        public static int executeInsertRandom( PreparedStatement pst ) {

       int result = -1;
       
       ResultSet rs = null;  
        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();
            
            
            result = pst.executeUpdate();
            db= null;
            
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getInt(1);
            }
            
        } catch (Exception e1) {
            res = e1.getMessage();
            System.out.println(res);
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }finally{    
            try{
                pst.close (); 
                rs.close();
            }catch( Exception e ){
                System.out.println(e.getMessage ());
                StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            }
        }
        
        return result ;
    }
    
    
    public static int executeInsertRandomMulti(String q1, String q2) {

       int result = -1;
       PreparedStatement pst = null;
        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();
            
            pst = con.prepareStatement(q1);
            result = pst.executeUpdate();
           
            
        } catch (SQLException e1) {
            res = e1.getMessage();            System.out.println(res);
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }finally{
            
            try{             pst.close ();                            }catch( SQLException e ){                System.out.println(e.getMessage ());
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );}
        }
        
        try {
            
           
            pst = con.prepareStatement(q2);
            result = pst.executeUpdate();
            
        } catch (SQLException e1) {
            res = e1.getMessage();            System.out.println(res);
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
        }finally{
            
            try{             pst.close ();                          }catch( SQLException e ){                System.out.println(e.getMessage ());           }
        }
        
        
        return result ;
    }
    
    public static boolean executeSingleNR(String q) {

       PreparedStatement pst = null;
        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();
            pst = con.prepareStatement(q);
            pst.executeUpdate();
            pst.close ();
            
        } catch (SQLException e1) {
            res = e1.getMessage();
            System.out.println(res);
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
            return false;
        }finally{
            
            try{
                pst.close ();
            }catch( Exception e ){
                System.out.println ( ""+e.getMessage () );   
                StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
            }
        }
              
        return true;
    }

    // INSERT NEW RECORD INTO GRADES UTILITIES MASER
    public static String insertIntoGradesMaster(String grade) {

        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(INSERT_GRADES_MASTER);
            pst.setString(1, grade);
            pst.executeUpdate();
        } catch (SQLException e1) {
            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
        return res;
    }

    // UPDATE SELECTED RECORD IN GRADES UTILITIES MASTER
    public static String updateIntoGradesMaster(String GradeVal, int gradeID) {

        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(UPDATE_GRADES_MASTER);
            pst.setString(1, GradeVal);
            pst.setInt(2, gradeID);
            pst.executeUpdate();
        } catch (SQLException e1) {
            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
        return res;
    }

    // INSERT NEW RECORD INTO GRADES UTILITIES MASER
    public static String insertIntoDesignationsMaster(String VAL) {

        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(INSERT_DESIGNATIONS_MASTER);
            pst.setString(1, VAL);
            pst.executeUpdate();
        } catch (SQLException e1) {
            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
        return res;
    }

    // UPDATE SELECTED RECORD IN GRADES UTILITIES MASTER
    public static String updateIntoDesignationsMaster(String VAL, int ID) {

        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(UPDATE_DESIGNATIONS_MASTER);
            pst.setString(1, VAL);
            pst.setInt(2, ID);
            pst.executeUpdate();
        } catch (SQLException e1) {
            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
        return res;
    }

    // INSERT NEW RECORD INTO GRADES UTILITIES MASER
    public static String insertIntoEmpNatureMaster(String VAL) {

        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(INSERT_EMPNATURE_MASTER);
            pst.setString(1, VAL);
            pst.executeUpdate();
        } catch (SQLException e1) {
            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
        return res;
    }

    // UPDATE SELECTED RECORD IN GRADES UTILITIES MASTER
    public static String updateIntoEmpNatureMaster(String VAL, int ID) {

        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(UPDATE_EMPNATURE_MASTER);
            pst.setString(1, VAL);
            pst.setInt(2, ID);
            pst.executeUpdate();
        } catch (SQLException e1) {
            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
        return res;
    }

    // INSERT NEW RECORD INTO GRADES UTILITIES MASER
    public static String insertIntoUOMMaster(String VAL) {

        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(INSERT_UOM_MASTER);
            pst.setString(1, VAL);
            pst.executeUpdate();
        } catch (SQLException e1) {
            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
        return res;
    }

    // UPDATE SELECTED RECORD IN GRADES UTILITIES MASTER
    public static String updateIntoUOMMaster(String VAL, int ID) {

        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(UPDATE_UOM_MASTER);
            pst.setString(1, VAL);
            pst.setInt(2, ID);
            pst.executeUpdate();
        } catch (SQLException e1) {
            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
        return res;
    }

    // INSERT NEW RECORD INTO GRADES UTILITIES MASER
    public static String insertIntoSalaryMaster(String VAL) {

        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(INSERT_SALARYBAND_MASTER);
            pst.setString(1, VAL);
            pst.executeUpdate();
        } catch (SQLException e1) {
            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
        return res;
    }

    // UPDATE SELECTED RECORD IN GRADES UTILITIES MASTER
    /* The method provides the functionality to update the salary 
     * dfsdfsdfsdfsdfsdf
     * asdasdasdadasdasd
     */
    public static String updateIntoSalaryMaster(String VAL, int ID) {

        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(UPDATE_SALARYBAND_MASTER);
            pst.setString(1, VAL);
            pst.setInt(2, ID);
            pst.executeUpdate();
        } catch (SQLException e1) {
            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
        return res;
    }

    // INSERT NEW RECORD INTO GRADES UTILITIES MASER
    public static String insertUtilitiesMaster(String Master, String VAL) {

        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();

            PreparedStatement pst = null;

            switch (Master) {

                case "PackSize":
                    pst = con.prepareStatement(INSERT_PACKSIZE_MASTER);
                    break;

                case "RMType":
                    pst = con.prepareStatement(INSERT_RAWMTYPE_MASTER);
                    break;

                case "Category":
                    pst = con.prepareStatement(INSERT_CATEGORY_MASTER);
                    break;

                case "Segment":
                    pst = con.prepareStatement(INSERT_SEGMENT_MASTER);
                    break;

                case "Industry":
                    pst = con.prepareStatement(INSERT_INDUSTRY_MASTER);
                    break;
                    
                case "RawMaterial":
                pst = con.prepareStatement(INSERT_RAWMTYPE_MASTER);
                break;
                
                case "RejectionReason":
                pst = con.prepareStatement(INSERT_REJETIONREASON_MASTER);
                break;

                case "DocumentType":
                pst = con.prepareStatement(INSERT_DOC_TYPE_MASTER);
                break;
                
                case "Departments":
                pst = con.prepareStatement(INSERT_DEPARTMENT);
                break;
                
                case "Resources":
                pst = con.prepareStatement(INSERT_RESOURCES);
                break;

                case "User Roles":
                pst = con.prepareStatement(INSERT_USER_ROLES  );
                break;
                
            }

            pst.setString(1, VAL);
            pst.executeUpdate();
        } catch (SQLException e1) {
            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }catch (NullPointerException e1) {
            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
        
        return res;
    }

    // UPDATE SELECTED RECORD IN GRADES UTILITIES MASTER
    /* The method provides the functionality to update the salary 
     * dfsdfsdfsdfsdfsdf
     * asdasdasdadasdasd
     */
    public static String updateUtilitiesMaster(String Master, String VAL, int ID) {

        try {
            DB_Operations db = new DB_Operations();
            con = db.getPreparedStatement();

            PreparedStatement pst = null;

            switch (Master) {

                case "PackSize":
                    pst = con.prepareStatement(UPDATE_PACKSIZE_MASTER);
                    break;

                case "RawMaterial":
                    pst = con.prepareStatement(UPDATE_RAWMTYPE_MASTER);
                    break;

                case "Category":
                    pst = con.prepareStatement(UPDATE_CATEGORY_MASTER);
                    break;

                case "Segment":
                    pst = con.prepareStatement(UPDATE_SEGMENT_MASTER);
                    break;

                case "Industry":
                    pst = con.prepareStatement(UPDATE_INDUSTRY_MASTER);
                    break;
                    
                    
                    case "Departments":
                    pst = con.prepareStatement(UPDATE_DEPARTMENT);
                    break;
                    
                    case "Resources":
                    pst = con.prepareStatement(UPDATE_RESOURCES);
                    break;
                    
                    case "User Roles":
                    pst = con.prepareStatement(UPDATE_ROLES);
                    break;
                    
            }

            pst.setString(1, VAL);
            pst.setInt(2, ID);
            pst.executeUpdate();
        } catch (SQLException e1) {
            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }
        return res;
    }

    //public static String insertIntoEmployeeMaster( String emp_name, long emp_no, String joing_date, Image photo,  String gender, int height, int weight, String education, long cell_no, String dob, String add_local, String add_perm, String jn_skill, String cur_skill, String auth_skill, String disease, String pan_no, long aadhar_no, String esic_no, String pf_no, String sal_band ) 
    public static String insertIntoEmployeeMaster(String emp_name,
            String emp_no,
            String joing_date,
            byte[] photo,
            String gender,
            String height,
            String weight,
            String education,
            String cell_no,
            String dob,
            String add_local,
            String add_perm,
            String jn_skill,
            String cur_skill,
            String auth_skill,
            String disease,
            String pan_no,
            String aadhar_no,
            String esic_no,
            String pf_no,
            String sal_band,
            String isUser,
            String uname,
            String pword,
            String createdon,
            String editedon,
            int editedby
    ) {
        res = "record inserted successfully !";

        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(INSERT_EMPLOYEE_MASTER);
            pst.setString(1, emp_name);
            //pst.setLong(2, emp_no);
            pst.setString(2, emp_no);
            pst.setString(3, joing_date);
            // photo 
            pst.setBytes(4, photo);
            // pst.setString(4, "");
            pst.setString(5, gender);
            //pst.setInt(6, height);
            //pst.setInt(7, weight);
            pst.setString(6, height);
            pst.setString(7, weight);
            pst.setString(8, education);
            //pst.setLong(9, cell_no);
            pst.setString(9, cell_no);
            pst.setString(10, dob);

            pst.setString(11, add_local);
            pst.setString(12, add_perm);
            pst.setString(13, jn_skill);
            pst.setString(14, cur_skill);
            pst.setString(15, auth_skill);
            pst.setString(16, disease);
            pst.setString(17, pan_no);
            //pst.setLong(18,  aadhar_no);
            pst.setString(18, aadhar_no);

            pst.setString(19, pf_no);
            pst.setString(20, esic_no);

            pst.setString(21, sal_band);

            pst.setString(22, isUser);
            pst.setString(23, uname);
            pst.setString(24, pword);

            pst.setString(25, createdon);
            pst.setString(26, editedon);
            pst.setInt(27, editedby);
            
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                res = rs.getInt(1)+"";
            }
            rs.close();
        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return res;
    }

     public static String updateEmployeeMaster(
            String emp_name,
            String emp_no,
            String joing_date,
            byte[] photo,
            String gender,
            String height,
            String weight,
            String education,
            String cell_no,
            String dob,
            String add_local,
            String add_perm,
            String jn_skill,
            String cur_skill,
            String auth_skill,
            String disease,
            String pan_no,
            String aadhar_no,
            String esic_no,
            String pf_no,
            String sal_band,
            String isUser,
            String uname,
            String pword,
            String editedon,
            int editedby,
            int ID
    ) {
        res = "record updated successfully !";

        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(UPDATE_EMPLOYEE_MASTER);
            pst.setString(1, emp_name);
            //pst.setLong(2, emp_no);
            pst.setString(2, emp_no);
            pst.setString(3, joing_date);
            
            // photo 
            pst.setBytes(4, photo);
            // pst.setString(4, "");
            pst.setString(5, gender);
            //pst.setInt(6, height);
            //pst.setInt(7, weight);
            pst.setString(6, height);
            pst.setString(7, weight);
            pst.setString(8, education);
            //pst.setLong(9, cell_no);
            pst.setString(9, cell_no);
            pst.setString(10, dob);
            pst.setString(11, add_local);
            pst.setString(12, add_perm);
            pst.setString(13, jn_skill);
            pst.setString(14, cur_skill);
            pst.setString(15, auth_skill);
            pst.setString(16, disease);
            pst.setString(17, pan_no);
            //pst.setLong(18,  aadhar_no);
            pst.setString(18, aadhar_no);
            pst.setString(19, pf_no);
            pst.setString(20, esic_no);
            pst.setString(21, sal_band);
            
            pst.setString(22, isUser);
            pst.setString(23, uname);
            pst.setString(24, pword);
            
         
            pst.setString(25, editedon);
            pst.setInt(26, editedby);
            pst.setInt(27, ID);
            
            

            pst.executeUpdate();

        } catch (Exception e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return res;
    }

    public static String updateFinishedGoodsMaster(String FG_PART_NO, String FG_PART_UNIQUE_ID, String PART_NAME,
             String SALES_RATE,  String EDITED_ON, String EDITED_BY,  String PROD_COST, String NOTES, int unit,  int ID    ) {
        res = "record updated successfully !";

        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(UPDATE_FINISHED_GOODS_MASTER);

            pst.setString(1, FG_PART_NO);
            pst.setString(2, FG_PART_UNIQUE_ID);
            pst.setString(3, PART_NAME);
            pst.setString(4, SALES_RATE);
            pst.setString(5, EDITED_ON);
            pst.setString(6, EDITED_BY);
            pst.setString(7, NOTES);
            pst.setString(8, PROD_COST);
            pst.setInt(9, unit);
            pst.setInt(10, ID);

            pst.executeUpdate();

        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return res;
    }

    //  Edit Supplier Details ///////////////////////////////////////////////////////////
    public static String updateSuppliersMaster(String SUPP_NAME, String SUPP_ADD, String SUPP_CNT_LL,
            String SUPP_CNT_BRD, String WEEK_OFF, String GST_NO, String VENDOR_CODE, String PAYT_TERMS,
            String CITY, String SEGMENT, String INDUSTRY, String CATEGORY, String AUTHORIZED_RM,
            String EDITED_ON, String EDITED_BY, int ID
    ) {
        res = "record updated successfully !";

        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(UPDATE_SUPPLIER_MASTER);

            pst.setString(1, SUPP_NAME);
            pst.setString(2, SUPP_ADD);
            pst.setString(3, SUPP_CNT_LL);
            pst.setString(4, SUPP_CNT_BRD);
            pst.setString(5, WEEK_OFF);
            pst.setString(6, GST_NO);
            pst.setString(7, VENDOR_CODE);
            pst.setString(8, CATEGORY);
            pst.setString(9, PAYT_TERMS);
            pst.setString(10, INDUSTRY);
            pst.setString(11, SEGMENT);
            pst.setString(12, CITY);
            pst.setString(13, AUTHORIZED_RM);
            pst.setString(14, "EDITED_BY");
            pst.setString(15, "EDITED_ON");
            pst.setInt(16, ID);

            pst.executeUpdate();

        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return res;
    }

    public static String updateRawMaterialMaster(String RM_TYPE, String RM_NAME, String RM_CTG, String RM_RATE, String RM_CRIT,
            String RM_EC_NO, String RM_CAS_NO, String GST_NO, String RM_CLASS,
            String REORDER_LEVEL, String EDITED_ON, String EDITED_BY , String unit , int ID 
    ) {
        res = "record updated successfully !";

        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(UPDATE_RAW_MATERIAL_MASTER);

            pst.setString(1, RM_TYPE);
            pst.setString(2, RM_NAME);
            pst.setString(3, RM_CTG);
            pst.setString(4, "");
            pst.setString(5, RM_RATE);
            pst.setString(6, RM_CRIT);
            pst.setString(7, RM_EC_NO);
            pst.setString(8, RM_CAS_NO);
            pst.setString(9, GST_NO);
            pst.setString(10, RM_CLASS);
            pst.setString(11, REORDER_LEVEL);

            pst.setString(12, Proman_User.getEID ()+"");
            pst.setString(13, DateTimeFormatter.sdf2.format ( Calendar.getInstance ().getTime ()) +" "+DateTimeFormatter.sdf1.format ( Calendar.getInstance ().getTime ()));
            pst.setString(14, unit);
            
            
            pst.setInt(15, ID);

            pst.executeUpdate();

        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return res;
    }

    public static ResultSet getMasters(String tableName) {
        res = "record inserted successfully !";
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            

            switch (tableName) {

                case "employee":
                    pst = con.prepareStatement(SELECT_EMPLOYEE_MASTER);
                    break;

                case "machine":
                    pst = con.prepareStatement(SELECT_MACHINE_MASTER);
                    break;

                case "calibration":
                    pst = con.prepareStatement(SELECT_CALIBRATION_MASTER);
                    break;

                case "finished_goods":
                    pst = con.prepareStatement(SELECT_FINISHED_GOODS_MASTER);
                    break;

                case "contact person":
                    pst = con.prepareStatement(SELECT_CONTACT_PERSON_MASTER);
                    break;

                case "customer":
                    pst = con.prepareStatement(SELECT_CUSTOMER_MASTER);
                    break;

                case "raw_material":
                    pst = con.prepareStatement(SELECT_RAW_MATERIAL_MASTER);
                    break;
                    
                case "raw_material_type":
                    pst = con.prepareStatement(SELECT_RAW_MATERIAL_TYPE_MASTER);
                break;

                case "supplier":
                    pst = con.prepareStatement(SELECT_SUPPLIER_MASTER);
                    break;

                case "testing":
                    pst = con.prepareStatement(SELECT_TESTING_MASTER);
                    break;

                case "grade":
                    pst = con.prepareStatement(SELECT_GRADE_MASTER);
                    break;

                case "designation":
                    pst = con.prepareStatement(SELECT_DESIGNATION_MASTER);
                    break;

                case "salaryband":
                    pst = con.prepareStatement(SELECT_SAL_BAND);
                    break;

                case "employeenature":
                    pst = con.prepareStatement(SELECT_EMP_NATURE);
                    break;

                case "uom":
                    pst = con.prepareStatement(SELECT_UOM);
                    break;

                case "pack_size":
                    pst = con.prepareStatement(SELECT_PACKSIZE_MASTER);
                    break;

                
                case "category":
                    pst = con.prepareStatement(SELECT_CATEGORY_MASTER);
                    break;

                case "segment":
                    pst = con.prepareStatement(SELECT_SEGMENT_MASTER);
                    break;

                case "industry":
                    pst = con.prepareStatement(SELECT_INDUSTRY_MASTER);
                    break;

                case "bom":
                    pst = con.prepareStatement(SELECT_BOM);
                    break;
                    
                    case "rejectionReason":
                    pst = con.prepareStatement(SELECT_REJECTION_REASON_MASTER);
                    break;
                    
                    case "process":
                    pst = con.prepareStatement(SELECT_PROCESSES);
                    break;

                    case "documentType":
                    pst = con.prepareStatement(SELECT_REF_DOC_MASTER);
                    break;
                    
                     case "departments":
                    pst = con.prepareStatement(SELECT_DEPARTMENTS);
                    break;

                    case "resources":
                    pst = con.prepareStatement(SELECT_RESOURCES);
                    break;
                    
                    case "roles":
                    pst = con.prepareStatement(SELECT_USERROLES);
                    break;
                    
                    

                    
                default:
                    pst = null;
            }

            rs = pst.executeQuery();

        } catch (SQLException e1) {
            res = "Error : " + e1.getMessage();
StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );             
        }finally{
//            try{
//                pst.close();
//            }catch(SQLException e){    
//                System.out.println ( ""+e.getMessage() );            
//            }catch(Exception e1){        
//                System.out.println (  ""+e1.getMessage()  );        
//            }
        }

        return rs;
    }

    public static ResultSet getSingleMasters(String tableName, int recordId) {
        res = "record inserted successfully !";
        ResultSet rs = null;
        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = null;
            pst = con.prepareStatement(SELECT_EMPLOYEE_MASTER);

            switch (tableName) {

                case "employee":
                    pst = con.prepareStatement(SELECT_EMPLOYEE_MASTER + " WHERE EmployeePK=" + recordId);
                    break;

                case "machine":
                    pst = con.prepareStatement(SELECT_MACHINE_MASTER + " WHERE MCH_ID = " + recordId);
                    break;

                case "calibration":
                    pst = con.prepareStatement(SELECT_CALIBRATION_MASTER + " WHERE EQUIPPMENT_ID=" + recordId);
                    break;

                case "finished_goods":
                    pst = con.prepareStatement(SELECT_FINISHED_GOODS_MASTER + " WHERE FG_ID=" + recordId);
                    break;

                case "contact_person_customer":
                    pst = con.prepareStatement(SELECT_CONTACT_PERSON_MASTER + " WHERE CUST_ID=" + recordId);
                    break;

                case "contact_person_supplier":
                    pst = con.prepareStatement(SELECT_CONTACT_PERSON_MASTER + " WHERE SUPP_ID=" + recordId);
                    break;

                case "customer":
                    pst = con.prepareStatement(SELECT_CUSTOMER_MASTER + " WHERE CUSTOMER_ID=" + recordId);
                    break;

                case "raw_material":
                    pst = con.prepareStatement(SELECT_RAW_MATERIAL_MASTER + " WHERE RM_ID=" + recordId);
                    break;

                case "supplier":
                    pst = con.prepareStatement(SELECT_SUPPLIER_MASTER + " WHERE SUPP_ID=" + recordId);
                    break;

                case "testing":
                    pst = con.prepareStatement(SELECT_TESTING_MASTER + " WHERE TEST_ID=" + recordId);
                    break;

                case "grade":
                    pst = con.prepareStatement(SELECT_GRADE_MASTER + " WHERE GradeID=" + recordId);
                    break;

                case "designation":
                    pst = con.prepareStatement(SELECT_DESIGNATION_MASTER + " WHERE DesgID=" + recordId);
                    break;

                case "salaryband":
                    pst = con.prepareStatement(SELECT_SAL_BAND + " WHERE SB_ID=" + recordId);
                    break;

                case "employeenature":
                    pst = con.prepareStatement(SELECT_EMP_NATURE + " WHERE NATURE_ID=" + recordId);
                    break;

                case "uom":
                    pst = con.prepareStatement(SELECT_UOM + " WHERE UOM_ID=" + recordId);
                    break;

                case "pack_size":
                    pst = con.prepareStatement(SELECT_PACKSIZE_MASTER + " WHERE PackSize_ID=" + recordId);
                    break;

                case "raw_material_type":
                    pst = con.prepareStatement(SELECT_RAWMTYPE_MASTER + " WHERE RMType_ID=" + recordId);
                    break;

                case "category":
                    pst = con.prepareStatement(SELECT_CATEGORY_MASTER + " WHERE Category_ID=" + recordId);
                    break;

                case "segment":
                    pst = con.prepareStatement(SELECT_SEGMENT_MASTER + " WHERE Segment_ID=" + recordId);
                    break;

                case "industry":
                    pst = con.prepareStatement(SELECT_INDUSTRY_MASTER + " WHERE Industry_ID=" + recordId);
                    break;

                case "bomsingleRM":
                    pst = con.prepareStatement(SELECT_BOM + " WHERE RM_ID=" + recordId + "   AND  FG_ID= " + BillOfMaterialUI.SelectedFG_ID);

                    //System.out.println(  SELECT_BOM+ " WHERE RM_ID=" + recordId +"  AND  FG_ID= " + BillOfMaterialUI.SelectedFG_ID  );
                    break;

                case "bomsingleFG":
                    pst = con.prepareStatement( "SELECT (SELECT PART_NAME FROM finished_goods  where FG_ID  in ( "+recordId+" )) as 'Product/FG Name', (select RM_NAME from raw_material where RM_ID in ( RM_ID)) as 'Raw Material', TOTAL_WT as  Weight , (select UOM from unit_meas where UOM_ID in (UOMID)) as 'Unit' from BillOfMaterial WHERE FG_ID=" + recordId);
                    break;

                case "bomsingleBOM":
                    pst = con.prepareStatement(SELECT_BOM + " WHERE BOM_ID=" + recordId);
                    break;
                    
                case "processes":
                    pst = con.prepareStatement(SELECT_PROCESSES + " WHERE PROCESS_ID=" + recordId);
                break;
                
                case "departments":
                    pst = con.prepareStatement( "SELECT * from DEPARTMENTS WHERE DID=" + recordId);
                    break;
                    
                case "resources":
                    pst = con.prepareStatement( "SELECT * from RESOURCE WHERE RSID=" + recordId);
                break;

                case "roles":
                    pst = con.prepareStatement( "SELECT * from userRoles WHERE Role_ID=" + recordId);
                break;
                
                
                case "rejectionReason":
                    pst = con.prepareStatement( "SELECT * from rejection_reasons WHERE RR_ID=" + recordId);
                break;
                
                default:
                    pst = null;
            }

            rs = pst.executeQuery();
        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return rs;
    }

    /*  public static String insertIntoMachineMaster( 
            String machine_make, 
            long bed_size, 
            int heater_qty, 
            long yr_of_comm, 
            String c_maint_rt, macine image ,  
            String c_mach_owner, mech breakdown record,  
            String pm_f_ele, 
            String pm_f_mec, 
            String pm_f_hyd, 
            String pm_f_pnm, 
            String pm_f_clit, 
            String press_gg, 
            String timer, 
            String temp_ctrl )  */
    public static String insertIntoMachineMaster(
            String machine_no,
            String machine_make,
            String bed_size,
            String heater_qty,
            String yr_of_comm,
            String c_maint_rt,
            String machine_image,
            String c_mach_owner,
            String mach_ownr_img,
            String mech_breakdown_record,
            String pm_f_ele,
            String pm_f_mec,
            String pm_f_hyd,
            String pm_f_pnm,
            String pm_f_clit,
            String press_gg,
            String timer,
            String temp_ctrl,
            String operatingRateHrs,
            String availabilityhrs,
            String davailabilityhrs,
            String INSTR_LIST,
            String createdon,
            String editedon,
            String editedby) {

        res = "record inserted successfully !";
        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(INSERT_MACHINE_MASTER);
            pst.setString(1, machine_no);
            pst.setString(2, machine_make);
            //pst.setLong(2, bed_size);
            //pst.setInt(3, heater_qty);
            pst.setString(3, bed_size);
            pst.setString(4, heater_qty);
            // photo 
            //pst.setLong(4, yr_of_comm );
            pst.setString(5, yr_of_comm);
            pst.setString(6, c_maint_rt);
            pst.setString(7, machine_image);
            pst.setString(8, c_mach_owner);
            pst.setString(9, mach_ownr_img);
            pst.setString(10, mech_breakdown_record);

            pst.setString(11, pm_f_ele);
            pst.setString(12, pm_f_mec);
            pst.setString(13, pm_f_hyd);
            pst.setString(14, pm_f_pnm);
            pst.setString(15, pm_f_clit);

            pst.setString(16, press_gg);
            pst.setString(17, timer);
            pst.setString(18, temp_ctrl);

            pst.setString(19, operatingRateHrs);
            pst.setString(20, availabilityhrs);
            
            pst.setString(21, davailabilityhrs);
            
            pst.setString(22, INSTR_LIST);

            pst.setString(23, "CREATED_ON");
            pst.setString(24, "EDITED_BY");
            pst.setString(25, "EDITED_ON");

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                res = rs.getInt(1)+"";
            }
            rs.close();
            
        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            JOptionPane.showMessageDialog ( null,  "Machine Not added "+res);
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return res;
    }

    public static String updateMachineMaster(
            String machine_no,
            String machine_make,
            String bed_size,
            String heater_qty,
            String yr_of_comm,
            String c_maint_rt,
            String machine_image,
            String c_mach_owner,
            String mach_ownr_img,
            String mech_breakdown_record,
            String pm_f_ele,
            String pm_f_mec,
            String pm_f_hyd,
            String pm_f_pnm,
            String pm_f_clit,
            String press_gg,
            String timer,
            String temp_ctrl,
            String operatingRateHrs,
            String availabilityhrs,
            String davailabilityhrs,
            String INSTR_LIST,
            String editedon,
            String editedby,
            int ID) {

        res = "record updated successfully !";
        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(UPDATE_MACHINE_MASTER);
            pst.setString(1, machine_no);
            pst.setString(2, machine_make);
            pst.setString(3, bed_size);
            pst.setString(4, heater_qty);
            pst.setString(5, yr_of_comm);
            pst.setString(6, c_maint_rt);
            pst.setString(7, machine_image);
            pst.setString(8, c_mach_owner);
            pst.setString(9, mach_ownr_img);
            pst.setString(10, mech_breakdown_record);

            pst.setString(11, pm_f_ele);
            pst.setString(12, pm_f_mec);
            pst.setString(13, pm_f_hyd);
            pst.setString(14, pm_f_pnm);
            pst.setString(15, pm_f_clit);

            pst.setString(16, press_gg);
            pst.setString(17, timer);
            pst.setString(18, temp_ctrl);

            pst.setString(19, operatingRateHrs);
            pst.setString(20, availabilityhrs);
            pst.setString(21, davailabilityhrs);
            pst.setString(22, INSTR_LIST);

            pst.setString(23, "EDITED_BY");
            pst.setString(24, "EDITED_ON");

            pst.setInt(25, ID);

            pst.executeUpdate();

        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return res;
    }

    public static String insertIntoCalibrationMaster(String EQUIPPMENT_NAME, String CALIBRATION_DATE, String NEXT_DUE_DATE, String CALIBRATION_AGENCY, String CALIBRATION_FREQUENCY, String NABL_CERTIFICATE_NO, String CERTIFICATE_COPY, String AGENCY_CONTACT_DETAILS, String INSTR_LIST, String SUPPLIER, String EQP_MAKE, String CREATED_ON, String EDITED_ON, String EDITED_BY) {

        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(INSERT_CALIBRATION_MASTER);

            pst.setString(1, EQUIPPMENT_NAME);
            pst.setString(2, CALIBRATION_DATE);
            pst.setString(3, NEXT_DUE_DATE);
            pst.setString(4, CALIBRATION_AGENCY);
            pst.setString(5, CALIBRATION_FREQUENCY);
            pst.setString(6, NABL_CERTIFICATE_NO);
            pst.setString(7, CERTIFICATE_COPY);
            pst.setString(8, AGENCY_CONTACT_DETAILS);
            pst.setString(9, INSTR_LIST);
            pst.setString(10, SUPPLIER);
            pst.setString(11, EQP_MAKE);

            pst.setString(12, "CREATED_ON");
            pst.setString(13, "EDITED_BY");
            pst.setString(14, "EDITED_ON");

            pst.executeUpdate();
        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return res;
    }

    public static String updateCalibrationMaster(String EQUIPPMENT_NAME, String CALIBRATION_DATE, String NEXT_DUE_DATE, String CALIBRATION_AGENCY, String CALIBRATION_FREQUENCY, String NABL_CERTIFICATE_NO, String CERTIFICATE_COPY, String AGENCY_CONTACT_DETAILS, String INSTR_LIST, String SUPPLIER, String EQP_MAKE, String EDITED_ON, String EDITED_BY, int ID) {

        try {

            res = "record updated successfully !";

            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(UPDATE_CALIBRATION_MASTER);

            pst.setString(1, EQUIPPMENT_NAME);
            pst.setString(2, CALIBRATION_DATE);
            pst.setString(3, NEXT_DUE_DATE);
            pst.setString(4, CALIBRATION_AGENCY);
            pst.setString(5, CALIBRATION_FREQUENCY);
            pst.setString(6, NABL_CERTIFICATE_NO);
            pst.setString(7, CERTIFICATE_COPY);
            pst.setString(8, AGENCY_CONTACT_DETAILS);
            pst.setString(9, INSTR_LIST);
            pst.setString(10, SUPPLIER);
            pst.setString(11, EQP_MAKE);
            pst.setString(12, "EDITED_BY");
            pst.setString(13, "EDITED_ON");
            pst.setInt(14, ID);

            pst.executeUpdate();
        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return res;
    }

    public static String insertIntoFinishedGoodsMaster(String FG_PART_NO, String FG_PART_UNIQUE_ID, String PART_NAME, String SALES_RATE, String CREATED_ON, String EDITED_ON, String EDITED_BY, String NOTES,   String PROD_COST, int unit) {
        PreparedStatement pst = null ;
        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            pst = con.prepareStatement(INSERT_FINISHED_GOODS_MASTER);

//            pst.setString(1, FG_PART_NO);
//            pst.setString(2, FG_PART_UNIQUE_ID);
//            pst.setString(3, PART_NAME);
//            pst.setString(4, CUSTOMER);
//            pst.setString(5, MOQ);
//            pst.setString(6, SALES_RATE);
//            pst.setString(7, GST_RATE);
//            pst.setString(8, PACK_SIZE);
//            pst.setString(9, MLD_CAVIT);
//            pst.setString(10, TGT_OP_TXT);
//            pst.setString(11, PART_DRG);
//            pst.setString(12, RBR_BTC_NO);
//            pst.setString(13, NT_WGHT);
//            pst.setString(14, GRS_WGHT);
//            pst.setString(15, OP_SKILL_LEVEL);
//            pst.setString(16, IMPORTANCE);
//            pst.setString(17, PART_VALID_DOC);
//            pst.setString(18, PART_PPAP);
//            pst.setString(19, PART_IMG);
//            pst.setString(20, MODL_SOP);
//            pst.setString(21, TESTNG_ID);
//            pst.setString(22, INSP_FRQ);
//            pst.setString(23, PREF_MCH);
//            pst.setString(24, MTL_INST_IMG);
//            pst.setString(25, CREATED_ON);
//            pst.setString(26, EDITED_ON);
//            pst.setString(27, EDITED_BY);
//            pst.setString(28, NOTES);
//            pst.setString(29, PROD_COST);

            pst.setString(1, FG_PART_NO);
            pst.setString(2, FG_PART_UNIQUE_ID);
            pst.setString(3, PART_NAME);
            pst.setString(4, SALES_RATE);
            pst.setString(5, CREATED_ON);
            pst.setString(6, EDITED_ON);
            pst.setString(7, EDITED_BY);
            pst.setString(8, NOTES);
            pst.setString(9, PROD_COST);
            pst.setInt(10, unit);

            pst.executeUpdate();
        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
    }   
        return res;
    }

    public static String insertIntoTestingMaster(String RAW_MATERIAL, String BTC_NO, String TEST_DT, String TEST, String TEST_RQ, String TEST_FREQ, String CREATED_ON, String EDITED_ON, String EDITED_BY) {

        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(INSERT_TESTING_MASTER);

            pst.setString(1, RAW_MATERIAL);
            pst.setString(2, BTC_NO);
            pst.setString(3, TEST_DT);
            pst.setString(4, TEST);
            pst.setString(5, TEST_RQ);
            pst.setString(6, TEST_FREQ);
            pst.setString(7, "CREATED_ON");
            pst.setString(8, "EDITED_BY");
            pst.setString(9, "EDITED_ON");

            pst.executeUpdate();
        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return res;
    }

    public static String updateTestingMaster(String RAW_MATERIAL, String BTC_NO, String TEST_DT, String TEST, String TEST_RQ, String TEST_FREQ, String EDITED_ON, String EDITED_BY, int ID) {

        res = "record updated successfully !";

        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(UPDATE_TESTING_MASTER);

            pst.setString(1, RAW_MATERIAL);
            pst.setString(2, BTC_NO);
            pst.setString(3, TEST_DT);
            pst.setString(4, TEST);
            pst.setString(5, TEST_RQ);
            pst.setString(6, TEST_FREQ);
            pst.setString(7, "EDITED_BY");
            pst.setString(8, "EDITED_ON");
            pst.setInt(9, ID);

            pst.executeUpdate();
        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return res;
    }

    public static String insertIntoRawMaterialMaster(String RM_TYPE, String RM_NAME, String RM_CTG, String RM_RATE, String RM_CRIT, String RM_EC_NO, String RM_CAS_NO, String GST_NO, String RM_CLASS, String REORDER_LEVEL, String CREATED_ON, String EDITED_ON, String EDITED_BY, String UOM_ID) {

        int newRecordId = 0 ;
        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(INSERT_RAW_MATERIAL_MASTER);

            pst.setString(1, RM_TYPE);
            pst.setString(2, RM_NAME);
            pst.setString(3, RM_CTG);
            pst.setString(4, "");
            pst.setString(5, RM_RATE);
            pst.setString(6, RM_CRIT);
            pst.setString(7, RM_EC_NO);
            pst.setString(8, RM_CAS_NO);
            pst.setString(9, GST_NO);
            pst.setString(10, RM_CLASS);

            //pst.setString(10, AUTH_SUPP);
            pst.setString(11, REORDER_LEVEL);

            pst.setString(12, CREATED_ON);
            pst.setString(13, EDITED_BY);
            pst.setString(14, EDITED_ON);
            
            pst.setString(15, UOM_ID);

            pst.executeUpdate();
            
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                newRecordId = rs.getInt(1);
            }
            
        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return newRecordId+"";
    }

    public static int insertIntoCustomerMaster(String CUSTOMER_NAME, String CUST_ADDRESS, String CONTACT_LL, String CONTACT_BRD, String WEEKLY_OFF, String GST_NO, String OUR_VENDOR_CODE, String PAYMENT_TERMS, String CITY, String SEGMENT, String INDUSTRY, String CATEGORY, String CREATED_ON, String EDITED_ON, String EDITED_BY) {

        int id = 0;

        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(INSERT_CUSTOMER_MASTER);

            pst.setString(1, CUSTOMER_NAME);
            pst.setString(2, CUST_ADDRESS);
            pst.setString(3, CONTACT_LL);
            pst.setString(4, CONTACT_BRD);
            pst.setString(5, WEEKLY_OFF);
            pst.setString(6, OUR_VENDOR_CODE);
            pst.setString(7, GST_NO);
            pst.setString(8, PAYMENT_TERMS);

            pst.setString(9, CITY);
            pst.setString(10, SEGMENT);
            pst.setString(11, INDUSTRY);
            pst.setString(12, CATEGORY);

            pst.setString(13, CREATED_ON);
            pst.setString(14, EDITED_BY);
            pst.setString(15, "EDITED_ON");

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e1) {
            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return id;
    }

    public static String insertIntoContactMaster(String CONT_EMAIL, String CONT_NO, int CUST_ID, int SUPP_ID, String CONT_NAME, String CREATED_ON, String EDITED_ON, String EDITED_BY) {

        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(INSERT_CONTACT_PERSON_MASTER);

            pst.setString(1, CONT_EMAIL);
            pst.setString(2, CONT_NO);
            pst.setInt(3, CUST_ID);
            pst.setInt(4, SUPP_ID);
            pst.setString(5, CONT_NAME);
            pst.setString(6, CREATED_ON);
            pst.setString(7, EDITED_ON);
            pst.setString(8, "EDITED_BY");

            pst.executeUpdate();
        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return res;
    }

    public static String updateContactMaster(String CONT_EMAIL, String CONT_NO, String CONT_NAME, int CUST_ID, int SUPP_ID, int ID) {

        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(UPDATE_CONTACT_PERSON_MASTER);

            pst.setString(1, CONT_EMAIL);
            pst.setString(2, CONT_NO);
            pst.setString(3, CONT_NAME);
            pst.setString(4, "EDITED_ON");
            pst.setString(5, "EDITED_BY");
            pst.setInt(6, CUST_ID);
            pst.setInt(7, SUPP_ID);
            pst.setInt(8, ID);
            pst.executeUpdate();

        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return res;
    }
    
    public static String insertIntoProcessMaster(String PROCESS, String DESC, String IS_BATCH_WISE, String IS_ITEM_WISE) {

        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(INSERT_PROCESS);

            pst.setString(1, PROCESS);
            pst.setString(2, DESC);
            pst.setString(3, IS_BATCH_WISE);
            pst.setString(4, IS_ITEM_WISE);
           

            pst.executeUpdate();
        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return res;
    }

    public static String updateProcessMaster(String PROCESS, String DESC, String IS_BATCH_WISE, String IS_ITEM_WISE, int ID) {

        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(UPDATE_PROCESS);

            pst.setString(1, PROCESS);
            pst.setString(2, DESC);
            pst.setString(3, IS_BATCH_WISE);
            pst.setString(4, IS_ITEM_WISE);
            pst.setInt(5, ID);
            pst.executeUpdate();

        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return res;
    }

    public static String updateCustomerMaster(String CUSTOMER_NAME, String CUST_ADDRESS, String CONTACT_LL, String CONTACT_BRD, String WEEKLY_OFF, String GST_NO, String OUR_VENDOR_CODE, String PAYMENT_TERMS, String CITY, String SEGMENT, String INDUSTRY, String CATEGORY, String EDITED_ON, String EDITED_BY, int ID) {

        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(UPDATE_CUSTOMER_MASTER);

            pst.setString(1, CUSTOMER_NAME);
            pst.setString(2, CUST_ADDRESS);
            pst.setString(3, CONTACT_LL);
            pst.setString(4, CONTACT_BRD);
            pst.setString(5, WEEKLY_OFF);
            pst.setString(6, OUR_VENDOR_CODE);
            pst.setString(7, GST_NO);
            pst.setString(8, PAYMENT_TERMS);

            pst.setString(9, CITY);
            pst.setString(10, SEGMENT);
            pst.setString(11, INDUSTRY);
            pst.setString(12, CATEGORY);

            pst.setString(13, "EDITED_BY");
            pst.setString(14, "EDITED_ON");

            pst.setInt(15, ID);

            pst.executeUpdate();
        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return res;
    }

    public static int insertIntoSupplierMaster(String SUPP_NAME, String SUPP_ADD, String SUPP_CNT_LL, String SUPP_CNT_BRD, String WEEK_OFF, String GST_NO, String VENDOR_CODE, String PAYT_TERMS, String CITY, String SEGMENT, String INDUSTRY, String CATEGORY, String AUTHORIZED_RM, String CREATED_ON, String EDITED_ON, String EDITED_BY) {
        int ID = 0;
        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(INSERT_SUPPLIER_MASTER);

            pst.setString(1, SUPP_NAME);
            pst.setString(2, SUPP_ADD);
            pst.setString(3, SUPP_CNT_LL);
            pst.setString(4, SUPP_CNT_BRD);
            pst.setString(5, WEEK_OFF);
            pst.setString(6, GST_NO);
            pst.setString(7, VENDOR_CODE);
            pst.setString(8, CATEGORY);

            pst.setString(9, PAYT_TERMS);
            pst.setString(10, INDUSTRY);
            pst.setString(11, SEGMENT);
            pst.setString(12, CITY);
            pst.setString(13, AUTHORIZED_RM);

            pst.setString(14, CREATED_ON);
            pst.setString(15, EDITED_BY);
            pst.setString(16, EDITED_ON);

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                ID = rs.getInt(1);
            }

        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }

        return ID;
    }

    public static int insert_STOCK_TRN(String MASTERNAME, int ItemID, int ID, int OPENING, int RECEIVED, int USED, int CLOSING, String CREATED_ON, String EDITED_ON, String EDITED_BY) {

        ResultSet rs  = null; 
        PreparedStatement pst = null;
                
        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            
            //  pst = con.prepareStatement(INSERT_RM_STOCK_TRN);
            switch (MASTERNAME) {

                case "Raw Material":
                    pst = con.prepareStatement(INSERT_RM_STOCK_TRN);
                    break;

                case "Finished Goods":
                    pst = con.prepareStatement(INSERT_FG_STOCK_TRN);
                    break;
            }

            pst.setInt(1, ID);
            pst.setInt(2, ItemID);
            pst.setInt(3, OPENING);
            pst.setInt(4, RECEIVED);
            pst.setInt(5, USED);
            pst.setInt(6, CLOSING);

            pst.setString(7, CREATED_ON);
            pst.setString(9, EDITED_BY);
            pst.setString(8, EDITED_ON);

            pst.executeUpdate();
            
           
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                ID = rs.getInt(1);
            }

           
        } catch (SQLException e1) {

            res = "DB Operations Error 1: " + e1.getMessage();
            System.out.println( res  );
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
        }       
        
        return ID;
    }
    
        public static int update_STOCK_TRN(String MASTERNAME, int OPENING, int RECEIVED, int USED, int CLOSING, String CREATED_ON, String EDITED_ON, String EDITED_BY, int ItemID) {

        ResultSet rs  = null; 
        PreparedStatement pst = null;
        
        int result = 0;
                
        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            
            //  pst = con.prepareStatement(INSERT_RM_STOCK_TRN);
            switch (MASTERNAME) {

                case "Raw Material":
                    pst = con.prepareStatement(UPDATE_RM_STOCK_TRN);
                    break;

                case "Finished Goods":
                    pst = con.prepareStatement(UPDATE_FG_STOCK_TRN);
                    break;
            }

            pst.setInt(1, OPENING);
            pst.setInt(2, RECEIVED);
            pst.setInt(3, USED);
            pst.setInt(4, CLOSING);
            pst.setString(5, CREATED_ON);
            pst.setString(6, EDITED_BY);
            pst.setString(7, EDITED_ON);
            
            pst.setInt(8, ItemID);

            pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getInt(1);
            }

           
        } catch (SQLException e1) {

            res = "Error 1: " + e1.getMessage();
            System.out.println( res  );
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 

        }       
        
        return result;
    }

    public static String insertIntoBOMMaster(
            int FG_ID,
            int RM_ID,
            double NT_WT,
            double GS_WT,
            double TOTAL_WT,
            String createdon,
            String editedon,
            String editedby) {

        res = "record inserted successfully !";
        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(INSERT_BOM);
            pst.setInt(1, FG_ID);
            pst.setInt(2, RM_ID);
            pst.setDouble(3, NT_WT);
            pst.setDouble(4, GS_WT);
            pst.setDouble(5, TOTAL_WT);
            pst.setString(6, createdon);
            pst.setString(7, editedon);
            pst.setString(8, editedby);

            pst.executeUpdate();

        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
            
        }

        return res;
    }

    public static String updateBOMMaster(
            double NT_WT,
            double GS_WT,
            double TOTAL_WT,
            String editedon,
            String editedby,
            int ID1,
            int ID2,
            int ID3) {

        res = "record updated successfully !";
        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = con.prepareStatement(UPDATE_BOM);

            pst.setDouble(1, NT_WT);
            pst.setDouble(2, GS_WT);
            pst.setDouble(3, TOTAL_WT);
            pst.setString(4, editedon);
            pst.setString(5, editedby);

            pst.setInt(6, ID1);
            pst.setInt(7, ID2);
            pst.setInt(8, ID3);

            pst.executeUpdate();

        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 

        }

        return res;
    }

    //////////////////////////////////////////////////////  transaction table methods / code //////////////////////////////////////////////////////////////////////////////
    public static int insertIntoSalesOrderAndOrderDetails(int customer_id, String po_date, String po_copy_name, String order_no, ArrayList<String[]> product_ids_qtys, int OA, String orderQty) {
        int id = -1;
        PreparedStatement pst = null;
        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();

            if(OA ==0 )
            {
                pst = con.prepareStatement("INSERT INTO sales_orders ( so_customer_id , po_date, po_copy_name, order_no, order_ack, order_qty ) values ( ?,?, ?, ?, 1 , ?)");                
            }else{
                pst = con.prepareStatement("INSERT INTO sales_orders ( so_customer_id , po_date, po_copy_name, order_no, order_ack , order_qty) values ( ?,?, ?, ?, (SELECT MAX(order_ack) from sales_orders)+1, ? )");
            }

            
            pst.setInt(1, customer_id);
            pst.setString(2, po_date);
            pst.setString(3, po_copy_name);
            pst.setString(4, order_no);
            pst.setDouble (5, Double.parseDouble( orderQty));
            
            pst.executeUpdate();
            
              } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            System.out.println ( res );
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 

        }  catch (Exception e1) {

            res = "Error : " + e1.getMessage();
            System.out.println ( res );
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 

        }

//         try {
//                    ResultSet rs = pst.getGeneratedKeys();
//                    if (rs.next()) {
//                        id = rs.getInt(1);
//                    }
//               } catch (SQLException e1) {
//
//            res = "Error : " + e1.getMessage();
//            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
//
//        }
           
//         try {    
//            for( int i=0; i<product_ids_qtys.size(); i++ ){
//             pst = con.prepareStatement("INSERT INTO po_order_details (  product_id, product_qty, finished_qty ,  ref_po_id   ) values ( ?,?,?,? )");    
//                
//            pst.setInt(1, Integer.parseInt( product_ids_qtys.get(i)[0]  ) );
//            pst.setInt(2,  Integer.parseInt(  product_ids_qtys.get(i)[1] ) );
//            pst.setInt(3,  0 );
//            pst.setInt(4, id);
//                  
//            pst.executeUpdate();
//           
//            }
//    
//        } catch (SQLException e1) {
//
//            res = "Error : " + e1.getMessage();
//            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 
//
//        }

        return id;
    }

    
    public static ResultSet getTransactions(String tableName) {
        res = "record inserted successfully !";
        ResultSet rs = null;
        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            PreparedStatement pst = null;

            switch (tableName) {

                case "sales_orders":
                    pst = con.prepareStatement( "SELECT order_no, so_customer_id, product_id, product_qty FROM sales_orders a INNER JOIN po_order_details b ON a.sales_po_id=b.ref_po_id" );
                    break;
                    
                case "orders":
                    pst = con.prepareStatement( "SELECT distinct(sales_po_id), order_no, order_ack FROM sales_orders" );
                break;

                default:
                    pst = null;
            }

            rs = pst.executeQuery();

        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 

        }

        return rs;
    }

    public static DecimalFormat df = new DecimalFormat( "#.##" );
    
    public static String insertIntoFG_CUS_MAP( int FG_ID, int CUST_ID, double SALES_RATE,  int S_UOM_ID, String CREATED_ON, String EDITED_ON, int EDITED_BY ) {

        res = "Record Successfuly Added!" ;
        ResultSet rs = null ;
        try {
            
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            
            rs =  DB_Operations.executeSingle ( "SELECT * FROM "+TBL_FG_CUS_MAP+" WHERE M_FG_ID = "+FG_ID+" AND M_CUST_ID="+CUST_ID );
            
            if( ! rs.isBeforeFirst ()){
            
                PreparedStatement pst = con.prepareStatement(INSERT_FG_CUS_MAP);

                pst.setInt(1, FG_ID);
                pst.setInt(2, CUST_ID);
                pst.setDouble(3, Double.parseDouble(  df.format(  SALES_RATE ) ));
                pst.setInt(4, S_UOM_ID);
                pst.setString(5, CREATED_ON);
                pst.setString(6, EDITED_ON);
                pst.setInt(7, EDITED_BY);


                pst.executeUpdate();
                
                
            }else{
                res = "Record already exists !" ;
            }
        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 

        }
        try {
            rs.close();
        } catch ( SQLException ex ) {
        }
        return res;
    }

    public static String updateFG_CUS_MAP(int FG_ID, int CUST_ID, float SALES_RATE,  int S_UOM_ID, String EDITED_ON, int EDITED_BY,  int ID) {

        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            
            ResultSet rs =  DB_Operations.executeSingle ( "SELECT * FROM "+TBL_FG_CUS_MAP+" WHERE M_FG_ID = "+FG_ID+" AND M_CUST_ID="+CUST_ID );
            
            if( rs.isBeforeFirst ()){
                
                try{
                    rs.close();
                }catch(SQLException e){
                    
                }
            
                PreparedStatement pst = con.prepareStatement(UPDATE_FG_CUS_MAP);

                pst.setInt(1, FG_ID);
                pst.setInt(2, CUST_ID);
                pst.setFloat(3, SALES_RATE);
                pst.setInt(4, S_UOM_ID);
                pst.setString(5, EDITED_ON);
                pst.setInt(6, EDITED_BY);

                pst.setInt(7, ID);
                pst.executeUpdate();
                
            }else{
                res = "Failed to update sales value !" ;
            }

        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 

        }
        return res;
    }



    public static String insertIntoPR_EMP_MAP( int PR_ID, int EMP_ID, int EFFICIENCY,  String CREATED_ON, String EDITED_ON, int EDITED_BY ) {

        try {
            
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            
            ResultSet rs =  DB_Operations.executeSingle (   "SELECT * FROM "+TBL_PR_EMP_MAP+" WHERE M_PR_ID = "+PR_ID+" AND M_EMP_ID="+EMP_ID+" AND EFFICIENCY="+EFFICIENCY  );
            
            if( ! rs.isBeforeFirst ()){
            
                PreparedStatement pst = con.prepareStatement(INSERT_PR_EMP_MAP);

                pst.setInt(1, PR_ID);
                pst.setInt(2, EMP_ID);
                pst.setFloat(3, EFFICIENCY);
                pst.setString(4, CREATED_ON);
                pst.setString(5, EDITED_ON);
                pst.setInt(6, EDITED_BY);


                pst.executeUpdate();
            }else{
                res = "Record already exists !" ;
            }
        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 

        }

        return res;
    }

    public static String updatePR_EMP_MAP(int PR_ID, int EMP_ID, int EFFICIENCY,  String EDITED_ON, int EDITED_BY,  int ID) {

        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            
            ResultSet rs =  DB_Operations.executeSingle (  "SELECT * FROM "+TBL_PR_EMP_MAP+" WHERE M_PR_ID = "+PR_ID+" AND M_EMP_ID="+EMP_ID+" AND EFFICIENCY="+EFFICIENCY );
            
            if( ! rs.isBeforeFirst ()){
            
                PreparedStatement pst = con.prepareStatement(UPDATE_PR_EMP_MAP);

                pst.setInt(1, PR_ID);
                pst.setInt(2, EMP_ID);
                pst.setFloat(3, EFFICIENCY);
                pst.setString(4, EDITED_ON);
                pst.setInt(5, EDITED_BY);

                pst.setInt(6, ID);
                pst.executeUpdate();
                
            }else{
                res = "Record already exists !" ;
            }

        } catch (SQLException e1) {

            res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 

        }
        return res;
    }
    
    
    public static String updateAlarms( int[] values) {

        String[] titles = new String[]{ "REJECTION_LEVEL" , "TARGETS_ACHIEVED" , "MCH_MONITORING_CAP" , "PRCS_MONIROTING_CAP" , "ALARMS_STATE"  };
        
        try {
            DB_Operations db = new DB_Operations();
            Connection con = db.getPreparedStatement();
            
            
            PreparedStatement pst = null ;
            String query = "update ALARMS set ";
            
            for( int i=0; i<values.length -1; i++ ){
                query = query + " "+titles[i] +" = "+values[i] +" , ";
            }   
            query = query + " "+titles[ values.length -1 ] +" = "+values[ values.length -1 ] ;
            
            
            pst = con.prepareStatement( query  );
            
            pst.executeUpdate();
            
        } catch (Exception e1) {
           res = "Error : " + e1.getMessage();
            StaticValues.writer.writeExcel ( DB_Operations.class.getSimpleName () , DB_Operations.class.getSimpleName () , e1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) ); 

        }
        return res;
    }
    
   
    
    public static String          deignationsTable                     = "CREATE TABLE IF NOT EXISTS `Designations` (`DesgID`INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`DesgTitle` TEXT);";
    public static String          gradesTable                              = "CREATE TABLE IF NOT EXISTS `Grades` (`GradeID`INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`GradeName` TEXT);";
    public static String          calibration_masterTable          = "CREATE TABLE IF NOT EXISTS `calibration_master` (`EQUIPPMENT_ID`INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`EQUIPPMENT_NAME`TEXT,`CALIBRATION_DATE`TEXT,`NEXT_DUE_DATE`TEXT,`CALIBRATION_AGENCY`TEXT,`CALIBRATION_FREQUENCY`TEXT,`NABL_CERTIFICATE_NO`TEXT,`CERTIFICATE_COPY`TEXT,`AGENCY_CONTACT_DETAILS` TEXT,  'INSTR_LIST' TEXT, 'SUPPLIER' TEXT,   'EQP_MAKE' TEXT, `CREATED_ON`TEXT,`EDITED_ON`TEXT,`EDITED_BY`TEXT);";
    public static String          contactPersonMasterTable     = " CREATE TABLE IF NOT EXISTS `contact_person` (`CONT_PER_ID`INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`CONT_EMAIL`TEXT,`CONT_NO`TEXT,`CUST_ID`INTEGER,`SUPP_ID`INTEGER,  `CONT_NAME`TEXT, `CREATED_ON`TEXT,`EDITED_ON`TEXT,`EDITED_BY`TEXT);";
    public static String          customerMasterTable              = "CREATE TABLE IF NOT EXISTS `customer` (`CUSTOMER_ID`INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`CUSTOMER_NAME`TEXT,`CUST_ADDRESS`TEXT,`CONTACT_LL`TEXT,`CONTACT_BRD`TEXT,`WEEKLY_OFF`TEXT,`GST_NO`TEXT,`OUR_VENDOR_CODE`TEXT,`PAYMENT_TERMS`TEXT,`INDUSTRY`TEXT,`SEGMENT`TEXT,`CITY`TEXT,`CATEGORY`TEXT,`CREATED_ON` datetime,`EDITED_ON`datetime,`EDITED_BY`TEXT);";
    public static String          employeeTable                        = "CREATE TABLE IF NOT EXISTS `employee` (`EmployeePK`INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`EMP_NAME`TEXT,`EMP_NO`TEXT,`JOIN_DT`TEXT,`PHOTO`BLOB,`GENDER`TEXT,`DEPARTMENTID`INTEGER,`ROLE`TEXT,`EDUCATION`TEXT,`CELL_NO`TEXT,`DOB`TEXT,`ADD_LOCAL`TEXT,`ADD_PERM`TEXT,`JOIN_SKILL`TEXT,`CURR_SKILL`TEXT,`AUTH_SKILL`TEXT,`DISEASE`TEXT,`PAN_NO`TEXT,`AADHAR_NO`TEXT,`PF_NO`TEXT,`ESIC_NO`TEXT,`SAL_BAND`TEXT,  `IS_USER` TEXT, `USERNAME` TEXT,  `PASSWORD` TEXT,   `CREATED_ON`TEXT,`EDITED_ON`TEXT,`EDITED_BY`TEXT);";
   public static String           finishedGoodsTable                   = "CREATE TABLE IF NOT EXISTS finished_goods ( `FG_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `FG_PART_UNIQUE_ID` TEXT UNIQUE, `FG_PART_NO` TEXT, `PART_NAME` TEXT, `CUSTOMER` TEXT, `MOQ` NUMERIC, `SALES_RATE` TEXT, `GST_RATE` TEXT, `PACK_SIZE` TEXT, `MLD_CAVIT` TEXT, `TGT_OP_TXT` TEXT, `PART_DRG` TEXT, `RBR_BTC_NO` TEXT, `NT_WGHT` TEXT, `GRS_WGHT` TEXT, `OP_SKILL_LEVEL` TEXT, `IMPORTANCE` TEXT, `PART_VALID_DOC` TEXT, `PART_PPAP` TEXT, `PART_IMG` TEXT, `MODL_SOP` TEXT, `TESTNG_ID` TEXT, `INSP_FRQ` TEXT, `PREF_MCH` TEXT, `MTL_INST_IMG` TEXT, `CREATED_ON` datetime, `EDITED_ON` datetime, `EDITED_BY` TEXT, `NOTES` TEXT, `PROD_COST` NUMERIC, `FG_UOM_ID` INTEGER, FOREIGN KEY(`FG_UOM_ID`) REFERENCES `unit_meas`(`UOM_ID`) )";
    public static String          machineMasterTable               = "CREATE TABLE IF NOT EXISTS `machine` (`MCH_ID`INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`MACHINE_NO`TEXT,`MAKE`TEXT,`BED_SIZE`TEXT,`HEATER_QTY`TEXT,`YR_OF_COMM`TEXT,`CR_MAINT_RTG`TEXT,`MCH_IMG`TEXT,`CR_MCH_OWN`TEXT,`MACH_OWN_IMG`TEXT,`MCH_BRKD_RCD`TEXT,`PM_ELE`TEXT,`PM_MEC`TEXT,`PM_HYD`TEXT,`PM_PNM`TEXT,`PM_CLIT`REAL,`PRSS_GAUGE`TEXT,`TIMER`TEXT,`TEMP_CTRL`TEXT,  'OP_RATE_HR' TEXT  , 'AVL_HRS' TEXT, 'DAVLHRS' TEXT,   'INSTR_LIST'  TEXT, `CREATED_ON`TEXT,`EDITED_ON`TEXT,`EDITED_BY`TEXT);";
    public static String          raw_materialMaster               = "CREATE TABLE IF NOT EXISTS raw_material ( `RM_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `RM_TYPE` TEXT, `RM_NAME` TEXT, `RM_CTG` TEXT, `RM_AUTH_SUPP` TEXT, `RM_RATE` TEXT, `RM_CRIT` TEXT, `RM_EC_NO` TEXT, `RM_CAS_NO` TEXT, `GST_NO` TEXT, `RM_CLASS` TEXT, `REORDER_LEVEL` TEXT, `CREATED_ON` TEXT, `EDITED_ON` TEXT, `EDITED_BY` TEXT, `RMM_UOM_ID` INTEGER, FOREIGN KEY(`RMM_UOM_ID`) REFERENCES `unit_meas`(`UOM_ID`) )";
     public static String         supplierMasterTable              = "CREATE TABLE IF NOT EXISTS `supplier` (`SUPP_ID`INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`SUPP_NAME`TEXT,`SUPP_ADD`TEXT,`SUPP_CNT_LL`TEXT,`SUPP_CNT_BRD`TEXT,`WEEK_OFF`TEXT,`GST_NO`TEXT,`VENDOR_CODE`TEXT,`CATEGORY`TEXT,`PAYT_TERMS`TEXT,`INDUSTRY`TEXT,`SEGMENT`TEXT,`CITY`TEXT, `AUTHORIZED_RM`TEXT,  `CREATED_ON`TEXT,`EDITED_ON`TEXT,`EDITED_BY`TEXT);";
    public static String          createTestingTable               = "CREATE TABLE IF NOT EXISTS `testing` (	`TEST_ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `RAW_MATERIAL`	TEXT,`BTC_NO` TEXT,`TEST_DT`	TEXT,`TEST`	TEXT,`TEST_RQ`	TEXT,`TEST_FREQ`	TEXT,    `CREATED_ON`	TEXT,`EDITED_ON`	TEXT,`EDITED_BY`	TEXT);";
    public static String          createUserMaster                 = " CREATE TABLE IF NOT EXISTS 'users' ( 'USERS_USER_ID' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 'USERNAME' TEXT, 'PASSWORD' TEXT, 'NAME' TEXT, 'ADDRESS' TEXT, 'PHONE' TEXT, 'DESIGNATION' TEXT, 'NATUREOFEMP' TEXT , 'ROLE' TEXT , `CREATED_ON`	TEXT,`EDITED_ON`	TEXT,`EDITED_BY`	TEXT)  ; ";
     public static String         createSalaryBandTable = "CREATE TABLE IF NOT EXISTS `SalaryBand` (`SB_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `SB_RANGE` TEXT );";
    public static String          createEmpNatureTable = "CREATE TABLE IF NOT EXISTS `EMP_NATURE` (`NATURE_ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`NATURE` TEXT);";
    public static String          createUOMTable = "CREATE TABLE IF NOT EXISTS `unit_meas` (`UOM_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`UOM` TEXT);";
    public static String          createPackSizeTable = "CREATE TABLE IF NOT EXISTS `pack_size` (`PackSize_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`PackSize` TEXT);";
    public static String          createRawMaterialTable = "CREATE TABLE IF NOT EXISTS `raw_material_type` (`RMType_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`RawMaterial` TEXT);";
    public static String          createCategoryTable = "CREATE TABLE IF NOT EXISTS `category` (`Category_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`Category` TEXT);";
     public static String         createSegmentTable = "CREATE TABLE IF NOT EXISTS `segment` (`Segment_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`Segment` TEXT);";
    public static String          createIndustryTable = "CREATE TABLE IF NOT EXISTS `industry` (`Industry_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`Industry` TEXT);";
     public static String         createRejectionReasonTable = "CREATE TABLE IF NOT EXISTS `rejection_reasons` (`RR_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`RR_DESC` TEXT, 'RR_CODE' TEXT, `RR_SHORT` TEXT);";
    public static String          create_RM_Inventory_Table = "CREATE TABLE IF NOT EXISTS `RMStock` (`RMStock_TR_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `RMI_ID`INTEGER, `RM_ITEM_ID`INTEGER, `OPENING` INTEGER,`RECEIVED` INTEGER,`USED` INTEGER,`CLOSING` INTEGER, `CREATED_ON`	TEXT,`EDITED_ON`	TEXT,`EDITED_BY`	TEXT);";
    public static String          create_Finished_Goods_Table = "CREATE TABLE IF NOT EXISTS `FGStock` (`FGStock_TR_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 'FG_ID' INTEGER, `FG_ITEM_ID`INTEGER, `OPENING` INTEGER,`RECEIVED` INTEGER,`USED` INTEGER,`CLOSING` INTEGER, `CREATED_ON`	TEXT,`EDITED_ON`	TEXT,`EDITED_BY`	TEXT);";
    public static String          create_Current_Stock_Table = "CREATE TABLE IF NOT EXISTS `CurrentStock` (`Stock_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 'REF_ID' INTEGER, `OPENING` INTEGER,`RECEIVED` INTEGER,`USED` INTEGER,`CLOSING` INTEGER, `CREATED_ON`	TEXT,`EDITED_ON`	TEXT,`EDITED_BY`	TEXT);";
    public static String          create_BOM_Table = "CREATE TABLE IF NOT EXISTS `BillOfMaterial` (`BOM_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 'FG_ID' INTEGER, `RM_ID` INTEGER,`NT_WT` TEXT,`GS_WT` TEXT,`TOTAL_WT` TEXT, `CREATED_ON`	TEXT,`EDITED_ON`	TEXT,`EDITED_BY`	TEXT, 'UOMID' INTEGER);";
     public static String         create_saledOrder_Table = "CREATE TABLE IF NOT EXISTS `sales_orders` (	`sales_po_id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,	`so_customer_id`	INTEGER,	`product_ids`	TEXT,	`po_date`	datetime,	`po_copy_name`	TEXT,	`order_qty`	INTEGER,	`order_no`	TEXT  UNIQUE,	`order_ack`	INTEGER UNIQUE,   `CREATED_ON` TEXT);";
    public static String          create_po_order_details_Table = "CREATE TABLE IF NOT EXISTS `po_order_details` (	`oder_detail_id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,	`product_id`	INTEGER,	`product_qty`	INTEGER,	`ref_po_id`	INTEGER NOT NULL,	`finished_qty`	INTEGER , `CREATED_ON` TEXT );";
    public static String          create_wip_Table = "CREATE TABLE IF NOT EXISTS `op_wip` ( `P_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `PRODUCT` INTEGER, `REJECTED` INTEGER, `REASON` INTEGER, `FINISHED` INTEGER, `MOVEQTY` INTEGER, `MOVEDATE` datetime, `ORDERNO` INTEGER, `PROCESS_STAGE` INTEGER, `BALANCE` INTEGER, `REJECTIONDATE` datetime, `DRID` INTEGER );";
    public static String          create_processMaster_Table = "CREATE TABLE IF NOT EXISTS `PROCESS_MASTER` (	`PROCESS_ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,	`PROCESS_NAME`	TEXT,	`PROCESS_DETAILS`	TEXT, 'IS_BATCH_WISE' TEXT, 'IS_ITEM_WISE' TEXT);";
    public static String          create_processMachine_Table = "CREATE TABLE IF NOT EXISTS `PROCESS_MACH_MAP` (	`PMM_ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,	`REF_FPM_ID`	INTEGER,	`MACHINE_ID`	INTEGER,	`SETUP_TIME`	INTEGER,	`IDEAL_PROCESS_TIME`	INTEGER,	`REMARKS`	TEXT);";
    public static String          create_FG_PR_MACH_MAP_Table = "CREATE TABLE IF NOT EXISTS FG_PROCESS_MACH_MAP ( `FPM_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `REF_FG_ID` INTEGER, `REF_PROCESS_ID` INTEGER, `REF_MCH_ID` INTEGER, `SETUP_TIME` TEXT, `IDEAL_PROCESS_TIME` TEXT, `REMARKS` TEXT, `TG_OPT_HR` INTEGER, `TG_OPT_SHT` INTEGER, `TG_OPT_DAY` INTEGER, `UOM_ID` INTEGER, `VAL_AT_PROC` NUMERIC, `MAX_REJ_PERM` INTEGER );";            
    public static String          create_FG_CUST_MAP_Table = "CREATE TABLE IF NOT EXISTS FG_CUSTOMER_MAP ( 'FG_C_ID' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 'M_FG_ID' INTEGER NOT NULL, 'M_CUST_ID' INTEGER NOT NULL, 'SALES_RATE' FLOAT NOT NULL, 'S_UOM_ID' INTEGER, 'CREATED_ON' datetime, 'EDITED_ON' datetime, 'EDITED_BY' INTEGER);";
   public static String           create_PROCESS_EMPLOYEE_MAP_Table = "CREATE TABLE IF NOT EXISTS PROCESS_EMPLOYEE_MAP ( 'PR_EMP_ID' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 'M_PR_ID' INTEGER NOT NULL, 'M_EMP_ID' INTEGER NOT NULL, 'EFFICIENCY' INTEGER NOT NULL, 'CREATED_ON' datetime, 'EDITED_ON' datetime, 'EDITED_BY' INTEGER);";           
   public static String           create_INSERT_DEPARTMENT = "CREATE TABLE IF NOT EXISTS  'DEPARTMENTS' ( `DID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `DNAME` TEXT NOT NULL )" ;
   public static String           create_INSERT_RESOURCES = "CREATE TABLE IF NOT EXISTS  `RESOURCE` ( `RSID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `RSNAME` TEXT NOT NULL )"; 
                    ///  BATCH_DETAILS
     public static String         BATCH_DETAILS = "CREATE TABLE  IF NOT EXISTS `BATCH_DETAILS` ( `BD_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `BM_ID` INTEGER NOT NULL, `RM_ID` INTEGER NOT NULL, `RM_QTY` REAL NOT NULL, `RM_UOM` INTEGER NOT NULL, FOREIGN KEY(`BM_ID`) REFERENCES `BATCH_MASTER`(`BatchId`) )";
            //BATCH_MASTER
      public static String        BATCH_MASTER = "CREATE TABLE  IF NOT EXISTS `BATCH_MASTER` ( `BatchId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `BatchName` TEXT NOT NULL, `BatchQty` REAL NOT NULL, `BatchUOM_ID` INTEGER NOT NULL, `BatchCreateDate` datetime NOT NULL, `BatchExpireDate` datetime NOT NULL  )";
            //PLANT_MASTER
     public static String         PLANT_MASTER= "CREATE TABLE IF NOT EXISTS  'PLANT_MASTER' ( `plantid` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `plantname` TEXT, `plantcode` TEXT, `gstno` TEXT, `plantaddress` TEXT, `plantemailaddress` TEXT, `plantweekoff` TEXT, `plantcpname` BLOB, `plantcpno` TEXT, `plantcpemail` TEXT, `shifts` TEXT, `companyname` TEXT, `complogo` BLOB, `plantcontactno` INTEGER )";
            //REF_DOCUMENTS
    public static String          REF_DOCUMENTS = "CREATE TABLE  IF NOT EXISTS   'REF_DOCUMENTS' ( `RD_UID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `RDM_UID` INTEGER NOT NULL, `RD_NAME` TEXT, `CREATION_DATE` INTEGER, FOREIGN KEY(`RDM_UID`) REFERENCES `REF_DOC_MASTER`(`RDM_UID`) )";
            //REF_DOC_MASTER
    public static String          REF_DOC_MASTER = "CREATE TABLE IF NOT EXISTS  'REF_DOC_MASTER' ( `RDM_UID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `RDM_NAME` TEXT )";
            //USERROLEPERM
     public static String         USERROLEPERM = "CREATE TABLE  IF NOT EXISTS 'USERROLEPERM' ( `URP_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `DEPTID` INTEGER NOT NULL, `RESID` INTEGER NOT NULL, `ROLE` TEXT NOT NULL, `READ` INTEGER NOT NULL, `EDIT` INTEGER NOT NULL, `DISABLE` INTEGER NOT NULL, `EXPORT` INTEGER NOT NULL, `CREATENEW` INTEGER NOT NULL, `IMPORT` INTEGER NOT NULL, `ACCESS_ON_MOBILE` INTEGER NOT NULL, FOREIGN KEY(`DEPTID`) REFERENCES `DEPARTMENTS`(`DID`) )";
            //dailySetupMaster
      public static String        dailySetupMaster = "CREATE TABLE IF NOT EXISTS  'dailySetupMaster' ( `SM_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `employeeId` INTEGER NOT NULL, `fgId` INTEGER NOT NULL, `shiftId` INTEGER NOT NULL, `rawMatId` INTEGER NOT NULL, `rawMatQty` REAL NOT NULL, `isMouldClean` TEXT NOT NULL, `isPersonSkilled` TEXT NOT NULL, `isMachineClit` TEXT NOT NULL, `areToolsAvl` TEXT NOT NULL, `setupDate` datetime, `setupTime` INTEGER, `machineId` INTEGER, FOREIGN KEY(`shiftId`) REFERENCES `shifts`(`shiftid`), FOREIGN KEY(`fgId`) REFERENCES `finished_goods`(`FG_ID`), FOREIGN KEY(`employeeId`) REFERENCES `employee`(`EmployeePK`) )";
            //dailyReport
    public static String          dailyReport = "CREATE TABLE IF NOT EXISTS  'dailyreport' ( `rid` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `rdate` datetime NOT NULL, `rtdate` datetime NOT NULL, `showrdate` datetime NOT NULL, `showrtdate` datetime NOT NULL, `fgid` INTEGER NOT NULL, `processid` INTEGER NOT NULL, `machineid` INTEGER NOT NULL, `starttime` datetime NOT NULL, `stoptime` datetime NOT NULL, `qtyin` INTEGER NOT NULL, `qtyout` INTEGER NOT NULL, `rejection` INTEGER NOT NULL, `empid` INTEGER NOT NULL, `showFromTime` datetime NOT NULL, `showToTime` datetime NOT NULL, `batchno` TEXT NOT NULL, `rej_reason` INTEGER, `act_stp_min` INTEGER, `customer_id` INTEGER, `shift_id` INTEGER, `SMADID` INTEGER, `actual_min` INTEGER, `total_min` INTEGER, `actual_hours` REAL, `total_hours` REAL, `alloted_rm` REAL )";
            //dailyReport3
     public static String         dailyReport3 = "CREATE TABLE IF NOT EXISTS  'dailyreport3' ( `rid` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `rdate` datetime NOT NULL, `rtdate` datetime NOT NULL, `showrdate` datetime NOT NULL, `showrtdate` datetime NOT NULL, `fgid` INTEGER NOT NULL, `processid` INTEGER NOT NULL, `machineid` INTEGER NOT NULL, `starttime` datetime NOT NULL, `stoptime` datetime NOT NULL, `qtyin` INTEGER NOT NULL, `qtyout` INTEGER NOT NULL, `rejection` INTEGER NOT NULL, `empid` INTEGER NOT NULL, `showFromTime` datetime NOT NULL, `showToTime` datetime NOT NULL, `batchno` TEXT NOT NULL, `rej_reason` INTEGER, `act_stp_min` INTEGER, `customer_id` INTEGER, `shift_id` INTEGER, `SMADID` INTEGER, `actual_min` INTEGER, `total_min` INTEGER, `actual_hours` REAL, `total_hours` REAL, `alloted_rm` REAL )";
            //op_wip2
     public static String         op_wip2 = "CREATE TABLE  IF NOT EXISTS  'op_wip2' ( `P_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `PRODUCT` INTEGER, `REJECTED` INTEGER, `REASON` INTEGER, `FINISHED` INTEGER, `MOVEQTY` INTEGER, `MOVEDATE` datetime, `ORDERNO` INTEGER, `PROCESS_STAGE` INTEGER, `BALANCE` INTEGER, `REJECTIONDATE` datetime, `DRID` INTEGER, `machine` INTEGER NOT NULL, `empid` INTEGER )";
            //shifts
     public static String         shifts = "CREATE TABLE IF NOT EXISTS  'shifts' ( `shiftid` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `shiftno` INTEGER, `shifttitle` TEXT, `shiftfromtime` datetime, `shifttotime` datetime, `breakduration` INTEGER, `refplantid` INTEGER NOT NULL ) ";
            //xl_import_test
     public static String         xl_import_test ="CREATE TABLE IF NOT EXISTS  'xl_import_test' ( `ID` TEXT, `name` TEXT, `address` TEXT, `phone` TEXT, `email` TEXT )";
    
     public static String alarms_Setting = "CREATE TABLE IF NOT EXISTS ALARMS_SETTINGS ( AS_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE , ALARM_NAME TEXT, ALARM_STATE TEXT, ALARMS_FREQUENCY_IN_MS INTEGER  )";
     
     public static String createView_RM_ABC_Analysis = "CREATE VIEW IF NOT EXISTS RMABCVIEW AS SELECT RM_NAME as 'NAME', REORDER_LEVEL ||' '||(select UOM from unit_meas where UOM_ID in (RMM_UOM_ID)) as 'ROL', RMStock.CLOSING ||' '||(select UOM from unit_meas where UOM_ID in (RMM_UOM_ID)) as 'Stock' , RM_RATE ||' / '||(select UOM from unit_meas where UOM_ID in (RMM_UOM_ID)) as 'Rate' , (RMStock.CLOSING*RM_RATE) as 'Stock Value' FROM raw_material INNER JOIN RMStock ON RMStock.RMI_ID  = raw_material.RM_ID WHERE   RMStock_TR_ID = (SELECT MAX(RMStock_TR_ID) FROM RMStock where RMI_ID = raw_material.RM_ID)";
     
     public static String createTriggerWIPfromDR = "CREATE TRIGGER IF NOT EXISTS ADD_WIP AFTER INSERT ON dailyreport \n" +
        "begin INSERT INTO op_wip2 ( \n" +
        "`PRODUCT` , `REJECTED` , `REASON` , `FINISHED` , `MOVEQTY` , `MOVEDATE` , `PROCESS_STAGE` , `BALANCE`  ) VALUES ( \n" +
        "NEW.fgid , NEW.rejection , NEW.rej_reason,  NEW.qtyout, NEW.qtyin, NEW.rdate, NEW.processid, (NEW.qtyin - NEW.qtyout)  ) ; end";
     
     public static String createRolesTable = "CREATE TABLE IF NOT EXISTS  `userRoles` (`Role_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,`RoleName` TEXT);" ;
     
     public static String createView_FG_ABC_Analysis = "CREATE VIEW IF NOT EXISTS FGABCVIEW AS\n" +
        "SELECT \n" +
        "PART_NAME as 'NAME', \n" +
        "FGStock.CLOSING ||' '||(select UOM from unit_meas where UOM_ID in (FG_UOM_ID)) as 'Stock' , \n" +
        "SALES_RATE ||' / '||(select UOM from unit_meas where UOM_ID in (FG_UOM_ID)) as 'Rate' , \n" +
        "(FGStock.CLOSING*SALES_RATE) as 'Stock Value' \n" +
        "FROM finished_goods INNER JOIN FGStock ON FGStock.FG_ID = finished_goods.FG_ID \n" +
        "WHERE FGStock_TR_ID = (SELECT MAX(FGStock_TR_ID) FROM FGStock where FG_ID = finished_goods.FG_ID)";
     
     public static String createRejectionDataTable = "CREATE TABLE `rejectionData` (	`rdID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,	`ref_pID`	INTEGER NOT NULL,	`rrID`	INTEGER NOT NULL,	`rQty`	INTEGER NOT NULL);";

     public static String createTimeLossDaaTable = "CREATE TABLE `timeLossData` (	`tldID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,	`ref_pID`	INTEGER NOT NULL,	`tlrID`	INTEGER NOT NULL,	`tlQty`	INTEGER NOT NULL);";

     public static String createTimeLossReasonMasterTable =  "CREATE TABLE `timeLossReason_master` ( `tlrm_ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `tlrm_Title` INTEGER NOT NULL );";
     
}


/*
 *                          --------------------                    Queries regarding FG_CUSTOMER_MAP               --------------------------------------------------
 * CREATE TABLE IF NOT EXISTS FG_CUSTOMER_MAP ( 'FG_C_ID' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 'FG_ID' INTEGER NOT NULL, 'CUST_ID' INTEGER NOT NULL, 'SALES_RATE' FLOAT NOT NULL, 'S_UOM_ID' INTEGER, 'CREATED_ON' datetime, 'EDITED_ON' datetime, 'EDITED_BY' INTEGER);
INSERT INTO FG_CUSTOMER_MAP ( M_FG_ID, M_CUST_ID, SALES_RATE, S_UOM_ID, CREATED_ON, EDITED_ON, EDITED_BY ) VALUES ( ?,?,?,?,?,?,? );
INSERT INTO FG_CUSTOMER_MAP ( M_FG_ID, M_CUST_ID, SALES_RATE, S_UOM_ID, CREATED_ON, EDITED_ON, EDITED_BY ) VALUES ( 1,1,3215.22,'2018-03-01 00:00:00','2018-03-01 00:00:00',1,1 );
UPDATE FG_CUSTOMER_MAP SET M_FG_ID = ?, M_CUST_ID = ?, SALES_RATE = ?, S_UOM_ID = ?, CREATED_ON = ?, EDITED_ON = ?, EDITED_BY = ? ;
UPDATE FG_CUSTOMER_MAP SET M_FG_ID = 2, M_CUST_ID = 2, SALES_RATE = 1111.10, S_UOM_ID = 2, CREATED_ON = '2018-05-03 00:00:00', EDITED_ON = '2018-05-03 00:00:00', EDITED_BY = 2 WHERE FG_C_ID = 1;
SELECT ( SELECT PART_NAME FROM finished_goods  WHERE FG_ID IN (M_FG_ID)), ( SELECT CUSTOMER_NAME FROM customer WHERE CUSTOMER_ID IN (M_CUST_ID)), SALES_RATE, ( SELECT UOM FROM unit_meas A WHERE UOM_ID IN (S_UOM_ID)) FROM FG_CUSTOMER_MAP
 */