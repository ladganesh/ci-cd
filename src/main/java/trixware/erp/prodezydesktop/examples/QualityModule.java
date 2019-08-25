/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import Model.QualityProcessDR;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.POrderDR;
import trixware.erp.prodezydesktop.Model.ProcessDR;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.QualityPartDR;
import trixware.erp.prodezydesktop.Model.QualityRMDR;
import trixware.erp.prodezydesktop.Model.RMBatchDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author WIN7
 */
public class QualityModule extends javax.swing.JPanel {

    private ArrayList<RMBatchDR> batches = null;
    private ArrayList<POrderDR> orders = null;
    private ArrayList<ProcessDR> processes = null;
    private ArrayList<ProductDR> products = null;
    private ArrayList<QualityProcessDR> proc=null;
    private ArrayList<QualityRMDR> rmqp=null;
    private ArrayList<QualityPartDR> prtqp=null;
    private Vector<Vector<Object>> data = null;
    private Vector<String> columnNames = null;
    private Vector<String> columnNames2 = null;
    
    /**
     * Creates new form QualityModule
     */
    public QualityModule() {
        initComponents();
        loadComponent();
        jComboBox1.addActionListener(comboBox1);
        loadComboBoxes();
        jTextField2.addKeyListener(keyListner);
        jComboBox3.addActionListener(acListner);
    }
    ActionListener acListner =new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            getRowMaterial();
        }
    };
            
    KeyListener keyListner=new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
           char enter = e.getKeyChar ();
            String dot = Character.toString ( enter );
            
            if (  ! ( Character.isDigit ( enter ) ) &&  ! dot.equals ( "." ) ) {
                e.consume ();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
          //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    private void loadComponent()
    {
        //load combo box of quality categories
        jComboBox1.removeAllItems();
        jComboBox1.addItem("Raw Material");
        jComboBox1.addItem("Process");
        jComboBox1.addItem("Part");   
        jTextField2.setText("");
        jComboBox2.removeAllItems();
        jComboBox3.removeAllItems();
        jComboBox4.removeAllItems();
        jComboBox5.removeAllItems();
        jLabel7.setText("");
     
    }
    public void getRowMaterial()
    {
        try{
        int rmid=batches.get(jComboBox3.getSelectedIndex()).RMID;
        //addEmpAPICall = "latestrwamstock?rmid="+rmids.get(j)[0
        System.err.println("RM_ID"+rmid);
        String addEmpAPICall = "rawmaterials?RM_ID="+rmid;
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        HashMap<String , Object> map = new HashMap<String, Object>();
        JSONObject jObject = new JSONObject(result2);
        Iterator<?> keys = jObject.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            Object value = jObject.get(key);
            map.put(key, value);
        }

        try {
            JSONObject st = (JSONObject) map.get("data");
            JSONArray records = st.getJSONArray("records");
            JSONObject emp = null;
            for (int i = 0; i < records.length(); i++) {
                emp = records.getJSONObject(i);
                jLabel9.setText(emp.get("RM_NAME").toString());
            }
        } catch (JSONException e) {
            System.out.println("" + e.getMessage());
        }
        }catch(Exception ex){}
    }
    public void getBatch(JComboBox jcombo)
    {
        jcombo.removeAllItems();
        //addEmpAPICall = "latestrwamstock?rmid="+rmids.get(j)[0];
        String addEmpAPICall = "batches";
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        HashMap<String , Object> map = new HashMap<String, Object>();
        JSONObject jObject = new JSONObject(result2);
        Iterator<?> keys = jObject.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            Object value = jObject.get(key);
            map.put(key, value);
        }

        try {
            JSONObject st = (JSONObject) map.get("data");
            JSONArray records = st.getJSONArray("records");
            JSONObject emp = null;
            batches = new ArrayList<RMBatchDR>();

            for (int i = 0; i < records.length(); i++) {
                emp = records.getJSONObject(i);

                int stockVal = 0;
                double stockVal2 = 0.0;
                try {
                    stockVal = Integer.parseInt(emp.get("BatchQty").toString());
                } catch (NumberFormatException e) {
                    stockVal2 = Double.parseDouble(emp.get("BatchQty").toString());
                }

                if (!emp.get("BatchId").toString().equals("null")) {

                    if (stockVal != 0 || stockVal2 != 0.0) {
                        jcombo.addItem(emp.get("BatchName").toString());
                        try {
                            //batches.add( new RMBatchDR ( Integer.parseInt(emp.get ( "RMStock_TR_ID" ).toString ()),  Integer.parseInt(emp.get ( "RMI_ID" ).toString ()),   emp.get ( "inward_batch_no" ).toString () ,  Integer.parseInt( rmids.get(j)[1] ),  emp.get( "CLOSING").toString() , emp.get ( "RM_CODE" ).toString () )); 
                            batches.add(new RMBatchDR(Integer.parseInt(emp.get("BatchId").toString()), emp.get("BatchName").toString(), Integer.parseInt(emp.get("ref_rm_id").toString()), 0, emp.get("BatchQty").toString()));
                        } catch (Exception e) {
                            System.out.println("" + e.getMessage());
                        }
                    }
                }
            }
        } catch (JSONException e) {
            System.out.println("" + e.getMessage());
        }
    }
    
    private void getProcess(JComboBox jcombo)
    {
        jcombo.removeAllItems();
        String addEmpAPICall = "processes";
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

        if (!result2.contains("not found")) {
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
            processes = new ArrayList<ProcessDR>();
            for (int i = 0; i < records.length(); i++) {

                emp = records.getJSONObject(i);
                //     jComboBox4.addItem ( emp.get ( "PROCESS_NAME" ).toString () );
                //     processes.add( new ProcessDR ( Integer.parseInt(emp.get ( "PROCESS_ID" ).toString ()), emp.get ( "PROCESS_NAME" ).toString ()   )); 
                jcombo.addItem(emp.get("PROCESS_NAME").toString());
                processes.add(new ProcessDR(Integer.parseInt(emp.get("PROCESS_ID").toString()), emp.get("PROCESS_NAME").toString()));
            }
        }
    }
    private void getOrder(JComboBox combox)
    {
       combox.removeAllItems();
       String addEmpAPICall = "salesorders";
       String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

        if (!result2.contains("not found")) {

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
            orders = new ArrayList<POrderDR>();
            for (int i = 0; i < records.length(); i++) {

                emp = records.getJSONObject(i);
                combox.addItem(emp.get("order_no").toString());
                orders.add(new POrderDR(Integer.parseInt(emp.get("sales_po_id").toString()), emp.get("order_no").toString(), Integer.parseInt(emp.get("so_customer_id").toString())));
            }
        }        
    }
    private void getPart(JComboBox combobox)
    {
        String addEmpAPICall = "finishedgoods";
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        if (!result2.contains("not found")) {

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
            products = new ArrayList<ProductDR>();
            for (int i = 0; i < records.length(); i++) {

                emp = records.getJSONObject(i);
                combobox.addItem(emp.get("FG_PART_NO").toString());
                //System.out.println("UOMMMM"+emp.get ( "FG_UOM_ID" ).toString ());
                products.add(new ProductDR(Integer.parseInt(emp.get("FG_ID").toString()), emp.get("PART_NAME").toString(), emp.get("FG_UOM_ID").toString()));
            }
        }
    }
    
    private void getPartMaster(JComboBox combobox)
    {
        String addEmpAPICall = "partqty_masters";
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        if (!result2.contains("not found")) {

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
            prtqp = new ArrayList<QualityPartDR>();
            for (int i = 0; i < records.length(); i++) {

                emp = records.getJSONObject(i);
                combobox.addItem(emp.get("part_param_name").toString());
                //System.out.println("UOMMMM"+emp.get ( "FG_UOM_ID" ).toString ());
                prtqp.add(new QualityPartDR(Integer.parseInt(emp.get("part_param_id").toString()), emp.get("part_param_name").toString()));
            }
        }
    }
    private void getQRMMaster(JComboBox combobox)
    {
        String addEmpAPICall = "rmqty_master";
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        if (!result2.contains("not found")) {

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
            rmqp = new ArrayList<QualityRMDR>();
            for (int i = 0; i < records.length(); i++) {

                emp = records.getJSONObject(i);
                combobox.addItem(emp.get("rm_param_name").toString());
                
                rmqp.add(new QualityRMDR(Integer.parseInt(emp.get("rm_param_id").toString()), emp.get("rm_param_name").toString()));
            }
        }
    }
    private void getProcessMaster(JComboBox combobox)
    {
        String addEmpAPICall = "proc_qty_master";
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        if (!result2.contains("not found")) {

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
            proc = new ArrayList<QualityProcessDR>();
            for (int i = 0; i < records.length(); i++) {

                emp = records.getJSONObject(i);
                combobox.addItem(emp.get("proc_param_name").toString());
                //System.out.println("UOMMMM"+emp.get ( "FG_UOM_ID" ).toString ());
                proc.add(new QualityProcessDR(Integer.parseInt(emp.get("proc_param_id").toString()), emp.get("proc_param_name").toString()));
            }
        }
    }
    private void loadComboBoxes()
    {
        jComboBox2.removeAllItems();
        jComboBox3.removeAllItems();
        jComboBox4.removeAllItems();
        jComboBox5.removeAllItems();

        int comboIndex=jComboBox1.getSelectedIndex();
        switch(comboIndex){
            case 0:
                jComboBox2.setEnabled(false);
                jComboBox4.setEnabled(false);
                jLabel7.setText("");
                getBatch(jComboBox3);
                getQRMMaster(jComboBox5);
                String result2 = WebAPITester.prepareWebCall("rmqty_data", StaticValues.getHeader(), "");
                getTable(result2);
                break;
            case 1:
                jComboBox2.setEnabled(true);
                jComboBox4.setEnabled(true);
                jLabel7.setText("Process");
 
                getOrder(jComboBox2);
                getBatch(jComboBox3);
                getProcess(jComboBox4);
                getProcessMaster(jComboBox5);
                String result = WebAPITester.prepareWebCall("proc_qty_data", StaticValues.getHeader(), "");
                getTable(result);

                break;
            case 2:
                jComboBox2.setEnabled(true);
                jComboBox4.setEnabled(true);   
                jLabel7.setText("Finished good");

                getOrder(jComboBox2);
                getBatch(jComboBox3);
                getPart(jComboBox4);
                getPartMaster(jComboBox5);
                String result1 = WebAPITester.prepareWebCall("partqty_data", StaticValues.getHeader(), "");
                getTable(result1);

                break;
            default:
                break;              
        }
        jTextField2.setText("");

    }

    ActionListener comboBox1=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadComboBoxes();
        }            
    };
    
    private void getTable(String api)
    {
        HashMap<String , Object> map = new HashMap<String , Object> ();
        JSONObject jObject = new JSONObject ( api );
        Iterator<?> keys = jObject.keys ();

        while ( keys.hasNext () ) {
            String key = ( String ) keys.next ();
            Object value = jObject.get ( key );
            map.put ( key , value );
        }

        
        JSONObject st = null;

            st = ( JSONObject ) map.get ( "data" );
            JSONArray records = st.getJSONArray ( "records" );
 
            data = new Vector<Vector<Object>> ();
            columnNames = new Vector<String> ();
            columnNames2 = new Vector<String> ();
            int index=jComboBox1.getSelectedIndex();
            switch(index)
            {
                case 0:
                columnNames.add ( "ref_batch_id" );
                columnNames2.add ( "Batch" );
                columnNames.add ( "insp_date" );
                columnNames2.add ( "Date" );
                columnNames.add ( "ref_rmqp_id" );                  
                columnNames2.add ( "Parameter" );
                columnNames.add ( "ref_rmpq_value" );                  
                columnNames2.add ( "Value" );
                break;
                case 1:
                columnNames.add ( "ref_order_id" );                 
                columnNames2.add ( "Order" );
                columnNames.add ( "ref_batch_id" );                  
                columnNames2.add ( "Batch" );
                columnNames.add ( "ref_proc_id" );           
                columnNames2.add ( "Process" );
                columnNames.add ( "insp_date" );           
                columnNames2.add ( "Date" );
                columnNames.add ( "ref_prcqp_id" );           
                columnNames2.add ( "Parameter" );
                columnNames.add ( "ref_prcqp_value" );           
                columnNames2.add ( "Value" );

                break;
                case 2:
                columnNames.add ( "ref_order_id" );                 
                columnNames2.add ( "Order" );
                columnNames.add ( "ref_batch_id" );                  
                columnNames2.add ( "Batch" );
                columnNames.add ( "ref_prt_id" );           
                columnNames2.add ( "Part" );
                columnNames.add ( "insp_date" );           
                columnNames2.add ( "Date" );
                columnNames.add ( "ref_prtqp_id" );           
                columnNames2.add ( "Parameter" );
                columnNames.add ( "ref_prtqp_value" );           
                columnNames2.add ( "Value" );
                break;
                default:
                    break;
            }
            

            for (int counter = 0 ; counter < records.length () ; counter ++ ) {
                Vector<Object> vector = new Vector<Object> ();

                int batchId=0;
                for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {
                    JSONObject emp = records.getJSONObject ( counter );
                     switch(index)
                    {
                        case 0:
                            if (columnIndex == 0) {
                                batchId = Integer.parseInt(emp.get(columnNames.get(columnIndex)).toString());
                                vector.add(getNameFromId(batchId, 1));
                            } else if (columnIndex == 1) {
                                vector.add(emp.get(columnNames.get(columnIndex)));
                            } else if (columnIndex == 2) {
                                int rmpqid = Integer.parseInt(emp.get(columnNames.get(columnIndex)).toString());
                                vector.add(getNameFromId(rmpqid, 6));
                            } else if (columnIndex == 3) {
                                vector.add(emp.get(columnNames.get(columnIndex)));
                            }
                            break;
                        case 1:
                            if (columnIndex == 0) {
                                int orderId = Integer.parseInt(emp.get(columnNames.get(columnIndex)).toString());
                                vector.add(getNameFromId(orderId, 2));
                            } else if (columnIndex == 1) {
                                batchId = Integer.parseInt(emp.get(columnNames.get(columnIndex)).toString());
                                vector.add(getNameFromId(batchId, 1));
                            } else if (columnIndex == 2) {
                                int processId = Integer.parseInt(emp.get(columnNames.get(columnIndex)).toString());
                                vector.add(getNameFromId(processId, 3));
                            } else if (columnIndex == 4) {
                                int procid = Integer.parseInt(emp.get(columnNames.get(columnIndex)).toString());
                                vector.add(getNameFromId(procid, 5));
                            }else{
                                vector.add(emp.get(columnNames.get(columnIndex)));
                            }
                            break;
                        case 2:
                            if (columnIndex == 0) {
                                int orderId = Integer.parseInt(emp.get(columnNames.get(columnIndex)).toString());
                                vector.add(getNameFromId(orderId, 2));
                            } else if (columnIndex == 1) {
                                batchId = Integer.parseInt(emp.get(columnNames.get(columnIndex)).toString());
                                vector.add(getNameFromId(batchId, 1));
                            } else if (columnIndex == 2) {
                                try{
                                int processId = Integer.parseInt(emp.get(columnNames.get(columnIndex)).toString());
                                vector.add(getNameFromId(processId, 4));
                                }catch(Exception e){vector.add(emp.get(columnNames.get(columnIndex)));}
                            } else if (columnIndex == 4) {
                            try{
                                int procid = Integer.parseInt(emp.get(columnNames.get(columnIndex)).toString());
                                vector.add(getNameFromId(procid, 7));
                             }catch(Exception e){vector.add(emp.get(columnNames.get(columnIndex)));}

                            }else{
                                vector.add(emp.get(columnNames.get(columnIndex)));
                            }
                            break;
                        default:
                            break;
                }
                    
                }
                data.add(vector);
            }
            
            jTable1.setModel(new DefaultTableModel(data, columnNames2));
            
    }
    public String getNameFromId(int id,int ch)
    {
        String name=null;
        //case 1 for batches
        //case 2 for orders
        //case 3 for processes
        //case 4 for products
        // case 5 for proc
        //case 6  for rmqp
        //case 7 for prtqp
        switch(ch)
        {
            case 1:
                for(RMBatchDR r:batches)
                {
                    if(r.BTC_ID==id)
                    {
                        name=r.BTC_NAME;
                    }
                }
                break;
            case 2:
                for(POrderDR r:orders)
                {
                    if(r.ORD_ID==id)
                    {
                        name=r.ORD_NAME;
                    }
                }
                
                break;
            case 3:
                for(ProcessDR r:processes)
                {
                    if(r.PRC_ID==id)
                    {
                        name=r.PRC_NAME;
                    }
                }                
                break;
            case 4:
                for(ProductDR r:products)
                {
                    if(r.FG_ID==id)
                    {
                        name=r.FG_PART_NO;
                    }
                }
                break;
            case 5:
                for(QualityProcessDR r:proc)
                {
                    if(r.PROC_ID==id)
                    {
                        name=r.PROC_NAME;
                    }
                }

                break;
            case 6:
                for(QualityRMDR r:rmqp)
                {
                    if(r.RMQP_ID==id)
                    {
                        name=r.RMQP_Name;
                    }
                }

                break;
            case 7:
                for(QualityPartDR r:prtqp)
                {
                    if(r.partId==id)
                    {
                        name=r.partName;
                    }
                }

                break;
                default:
                    break;
        }
       
        return name;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        dateChooserCombo3 = new datechooser.beans.DateChooserCombo();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(null);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(jComboBox3);
        jComboBox3.setBounds(980, 120, 180, 40);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(jComboBox1);
        jComboBox1.setBounds(980, 20, 180, 40);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(jComboBox2);
        jComboBox2.setBounds(980, 70, 180, 40);

        jLabel1.setText("Select Order");
        add(jLabel1);
        jLabel1.setBounds(840, 70, 140, 40);

        jLabel2.setText("Select Batch");
        add(jLabel2);
        jLabel2.setBounds(840, 120, 140, 40);
        add(jLabel3);
        jLabel3.setBounds(441, 184, 58, 0);

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(jComboBox5);
        jComboBox5.setBounds(980, 330, 180, 40);

        jLabel4.setText("Enter Value");
        add(jLabel4);
        jLabel4.setBounds(840, 380, 140, 30);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(jComboBox4);
        jComboBox4.setBounds(980, 230, 180, 40);

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        add(jTextField2);
        jTextField2.setBounds(980, 380, 180, 30);

        jButton1.setText("Submit");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(940, 430, 130, 30);

        dateChooserCombo3.setCalendarPreferredSize(new java.awt.Dimension(320, 220));
        add(dateChooserCombo3);
        dateChooserCombo3.setBounds(980, 280, 180, 40);

        jLabel5.setText("Select Date");
        add(jLabel5);
        jLabel5.setBounds(840, 280, 140, 40);

        jLabel6.setText("Quality Parameter");
        add(jLabel6);
        jLabel6.setBounds(840, 330, 140, 40);
        add(jLabel7);
        jLabel7.setBounds(840, 230, 140, 40);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 51));
        jLabel8.setText("*");
        add(jLabel8);
        jLabel8.setBounds(970, 380, 7, 10);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(30, 20, 770, 402);

        jLabel9.setText(" ");
        add(jLabel9);
        jLabel9.setBounds(980, 170, 140, 40);

        jLabel10.setText("Raw material");
        add(jLabel10);
        jLabel10.setBounds(840, 170, 140, 40);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        if(!jTextField2.getText().equals(""))
        {
            int indexNo=jComboBox1.getSelectedIndex();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String inspectDate = formatter.format ( dateChooserCombo3.getSelectedDate ().getTime () );
            String addEmpAPICall =null;
            String result2 =null;
            switch(indexNo)
            {
                case 0:
                    int rmqpid=rmqp.get(jComboBox5.getSelectedIndex()).RMQP_ID;
                    int batchId=batches.get(jComboBox3.getSelectedIndex()).BTC_ID;
                    Double rmpq_value=Double.parseDouble(jTextField2.getText());
      
                    addEmpAPICall = "rmqty_data_add?ref_batch_id=" + batchId + "&insp_date=" + inspectDate+"&ref_rmqp_id="+rmqpid+"&ref_rmpq_value="+rmpq_value;
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    JOptionPane.showMessageDialog ( null , result2);
                    
                    break;
                case 1:
                    int orderId=orders.get(jComboBox2.getSelectedIndex()).ORD_ID;
                    int batchid=batches.get(jComboBox3.getSelectedIndex()).BTC_ID;
                    int prcqp=proc.get(jComboBox5.getSelectedIndex()).PROC_ID;
                    int processid=processes.get(jComboBox4.getSelectedIndex()).PRC_ID;
                    Double prcqpValue=Double.parseDouble(jTextField2.getText().toString());
                
                    addEmpAPICall = "proc_qty_data_add?ref_order_id=" + orderId + "&ref_batch_id=" +  batchid + "&ref_proc_id=" + processid + "&insp_date=" + inspectDate+"&ref_prcqp_id="+prcqp+"&ref_prcqp_value="+prcqpValue;
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    JOptionPane.showMessageDialog ( null , result2);
                    break;
                case 2:
                    int orderId1=orders.get(jComboBox2.getSelectedIndex()).ORD_ID;
                    int batchid1=batches.get(jComboBox3.getSelectedIndex()).BTC_ID;
                    int prtq=prtqp.get(jComboBox5.getSelectedIndex()).partId;
                    int partid=products.get(jComboBox4.getSelectedIndex()).FG_ID;
                    Double prtqpValue=Double.parseDouble(jTextField2.getText().toString());
            
                    addEmpAPICall = "partqty_data_add?ref_order_id=" + orderId1 + "&ref_batch_id=" +  batchid1 + "&ref_prt_id=" + partid + "&insp_date=" + inspectDate+"&ref_prtqp_id="+prtq+"&ref_prtqp_value="+prtqpValue;
                    result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    JOptionPane.showMessageDialog ( null , result2);

                    break;
                default:
                    break;
            }
            loadComponent();
            loadComboBoxes();
        }else{
            JOptionPane.showMessageDialog ( null , "*Please filled all mandetory field!");
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserCombo3;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
