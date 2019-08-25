/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.quotation.partmaster;

import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.components.ProgressDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.quotation.RejectionDR;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Akshay
 */
public class ProcessRMData {
    public static ArrayList<RejectionDR> rejModel=null;
    public static ArrayList<String[]> processes=null;
    public static ArrayList<String[]> measunit=null;
    public static ArrayList<String[]> rawmaterial=null;

    public ArrayList<String[]> getRawMaterialData() {
        rawmaterial = new ArrayList<String[]>();
        String addEmpAPICall = "rm_quotation";
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(),"");
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

                for (int i = 0; i < records.length(); i++) {
                    emp = records.getJSONObject(i);
//                        System.err.println(emp);
                    String[] data = new String[21];
                    try {
                        data[0] = emp.get("RM_ID").toString();
                        data[1] = emp.get("RM_NAME").toString();
                        data[2] = emp.get("RM_RATE").toString();
                        data[3] = emp.get("RM_CODE").toString();
                        data[4] = emp.get("RM_TYPE").toString();
                        data[5] = emp.get("RM_CTG").toString();
                        data[6] = emp.get("length").toString();
                        data[7] = emp.get("thickness").toString();
                        data[8] = emp.get("diameter").toString();
                        data[9] = emp.get("wall").toString();
                        data[10] = emp.get("density").toString();
                        data[11] = emp.get("width").toString();
                        data[12] = emp.get("in_diameter").toString();
                        data[13] = emp.get("out_diameter").toString();
                        data[14] = emp.get("RMM_UOM_ID").toString();
                        data[15] = emp.get("height").toString();
                        data[16] = emp.get("radius").toString();
                        data[17] = emp.get("icc").toString();
                        data[18] = emp.get("scrap_rate").toString();
                        data[19] = emp.get("tax").toString();
                        data[20] = emp.get("shape").toString();
//                            System.err.println(data);
                        rawmaterial.add(data);
                    } catch (Exception e) {
                        System.out.println("sdfasdaf" + e.getMessage());
                    }
                }
            }
        }
        return rawmaterial;
    }
    public ArrayList<String[]> getMesUnit()
    {
        measunit = new ArrayList<String[]>();
        String addEmpAPICall = "unitmeas";
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(),"");
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

                for (int i = 0; i < records.length(); i++) {
                    emp = records.getJSONObject(i);
                    String[] data = new String[2];
                    data[0] = emp.get("UOM_ID").toString();
                    data[1] = emp.get("UOM").toString();
                    measunit.add(data);
                }
            }
        }
        return measunit;
    }

    public ArrayList<String[]> getProcesses()
    {
        processes = new ArrayList<String[]>();

        String addEmpAPICall = "process_qt";
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(),"");
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

                for (int i = 0; i < records.length(); i++) {
                    emp = records.getJSONObject(i);
                    String[] data = new String[4];
                    data[0] = emp.get("PROCESS_ID").toString();
                    data[1] = emp.get("PROCESS_NAME").toString();
                    data[2] = emp.get("rateperunit").toString();
                    String uom = emp.get("UOM").toString();
                    String uom_name = "";

                    processes.add(data);
                }
            }
        }
        return processes;
    }
    
    public ArrayList<RejectionDR> getRejModel()
    {
        rejModel = new ArrayList<RejectionDR>();
        String addEmpAPICall21 = "recoverys";

        String result21 = WebAPITester.prepareWebCall(addEmpAPICall21, StaticValues.getHeader(),"");
        System.err.println(result21);
        if (!result21.contains("not found")) {
            if (result21 != null) {
                String recovId = "";
                String from = "";
                String to = "";
                String recoveryPer = "";

                HashMap<String, Object> map = new HashMap<String, Object>();
                JSONObject jObject = new JSONObject(result21);
                Iterator<?> keys = jObject.keys();

                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    Object value = jObject.get(key);
                    map.put(key, value);
                }

                JSONObject st = (JSONObject) map.get("data");
                JSONArray records = st.getJSONArray("records");

                JSONObject enq = null;

                for (int i = 0; i < records.length(); i++) {
                    enq = records.getJSONObject(i);
                    recovId = enq.get("rej_recov_id").toString();
                    from = enq.get("quan_from").toString();
                    to = enq.get("quan_to").toString();
                    recoveryPer = enq.get("recovery").toString();

                    rejModel.add(new RejectionDR(Integer.parseInt(recovId), Integer.parseInt(from), Integer.parseInt(to), Double.parseDouble(recoveryPer)));
                }
            }
        }
        return rejModel;
    }

    public void getData(ProgressDialog progress){
        progress.updateMessage("<html>Loading.. Masters Data.<br> Please wait until it is completed.</html>");
        getMesUnit();
        progress.updateMessage("<html>Loading.. Rejection Master Data.<br> Please wait until it is completed.</html>");
        getRejModel();
        progress.updateMessage("<html>Loading..RM Master Data.<br> Please wait until it is completed.</html>");
        getRawMaterialData();
        progress.updateMessage("<html>Loading..Process Master Data.<br> Please wait until it is completed.</html>");
        getProcesses();        
    }
}
