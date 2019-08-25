/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Harshu
 * created by HArshali for update BOM of Assembled Parts 28-Jun-2019
 */
public class AssembledParts extends javax.swing.JPanel {

    String addEmpAPICall;

    /**
     * Creates new form AssembledParts
     */
    public AssembledParts() {
        initComponents();
        load();
    }

    public void load() {

        addEmpAPICall = "finishedgoods";
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

        if (!result2.contains("not found")) {

            if (result2 != null) 
            {
                jTable1.setModel(buildTableModelfromJSON(result2));
                 jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                  jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                                
            }

        }

    }

    public static DefaultTableModel buildTableModelfromJSON(String assem_parts_List) 
    {
        ArrayList<String[]> Aseem_list = null;
        //String addEmpAPICall = "finishedgoods";
        //  String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

        HashMap<String, Object> map = new HashMap<String, Object>();
        JSONObject jObject = new JSONObject(assem_parts_List);
        Iterator<?> keys = jObject.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            Object value = jObject.get(key);
            map.put(key, value);
        }

        JSONObject st = (JSONObject) map.get("data");
        JSONArray records = st.getJSONArray("records");

        JSONObject emp = null;
        Aseem_list = new ArrayList<String[]>();
        for (int i = 0; i < records.length(); i++) {

            emp = records.getJSONObject(i);
            int flag = emp.getInt("ASSEMBLED");
            if (flag == 1) {
                Aseem_list.add(new String[]{emp.get("FG_ID").toString(), emp.get("FG_PART_NO").toString()});

            }
        }

        Vector<String> columnNames = new Vector<String>();
        int columnCount = 0;
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        if (Aseem_list.size() != 0) 
        {

            JSONArray jARR = null;
            JSONObject jOB = null;

            columnNames = new Vector<String>();
            columnNames.add("assem_id");
            columnNames.add("Assembled Part");
            columnNames.add("Child Part");
            columnNames.add("Child Part Quantity");
            columnNames.add("Bought Out RM");
            columnNames.add("Bought Out RM Quantity");

            for (int i = 0; i < Aseem_list.size(); i++) 
            {
                String addEmpAPICall = "assembledparts?assembled_id=" + Aseem_list.get(i)[0];
                String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

                if (!result2.contains("not found")) 
                {

                    if (result2 != null) 
                    {
                        
                        try
                        {
                        HashMap<String, Object> map1 = new HashMap<String, Object>();
                        JSONObject jObject1 = new JSONObject(result2);
                        Iterator<?> keys1 = jObject1.keys();

                        while (keys1.hasNext()) {
                            String key = (String) keys1.next();
                            Object value = jObject1.get(key);
                            map1.put(key, value);
                        }

                        JSONObject st1 = (JSONObject) map1.get("data");
                        JSONArray record = st1.getJSONArray("records");

                        JSONObject emp1 = null;
                        // Aseem_list = new ArrayList<String[]>();
                        for (int j = 0; j < record.length(); j++) 
                        {
                            Vector<Object> vector = new Vector<Object> ();
                            emp1 = record.getJSONObject ( j);
                            for ( int columnIndex = 0 ; columnIndex <columnNames.size () ; columnIndex ++ ) 
                            {
                                if( columnIndex == 1)
                                {
                                    if(j == 0)
                                    {
                                    vector.add (Aseem_list.get(i)[1]);
                                    }
                                    else{
                                      vector.add(null);
                                    }
                                    
                                }
                                else
                                {  
                                 if( columnNames.get(columnIndex) == "assem_id")  
                                 {  
                                  vector.add (  emp1.get ( "assem_id")) ;
                                 }
                                 if( columnNames.get(columnIndex) == "Child Part")
                                 {
                                     if(emp1.get ( "FG_PART_NO").toString() == "null" )
                                     {
                                         vector.add( " - ");
                                     }
                                     else
                                     {
                                     vector.add ( emp1.get ( "FG_PART_NO"));
                                     }
                                 }
                                 if( columnNames.get(columnIndex) == "Child Part Quantity")
                                 {
                                     if( emp1.getInt( "child_part_qty") == 0 )
                                     {
                                        vector.add( " - ");
                                     }
                                     else
                                     {
                                     vector.add ( emp1.get ( "child_part_qty"));
                                     }
                                 }
                                 
                                 if( columnNames.get(columnIndex) == "Bought Out RM")
                                 {
                                     if(emp1.get ( "RM_NAME").toString() == "null" )
                                     {
                                         vector.add( " - ");
                                     }
                                     else
                                     {
                                     
                                     vector.add ( emp1.get ( "RM_NAME"));
                                     }
                                 }
                                 if( columnNames.get(columnIndex) == "Bought Out RM Quantity")
                                 {
                                     if( emp1.getInt( "boughtout_rmqty") == 0 )
                                     {
                                        vector.add( " - ");
                                     }
                                     else
                                     {
                                     vector.add ( emp1.get ( "boughtout_rmqty"));
                                     }
                                 }
                                }
                                
                            }
                                         
                            
                           data.add(vector);
                        }
                        }catch(Exception e )
                        {
                            System.err.println(e);
                        }
                    }
               
                }

                data.add(null);
            }

           
        }

        return new DefaultTableModel(data, columnNames);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

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
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
