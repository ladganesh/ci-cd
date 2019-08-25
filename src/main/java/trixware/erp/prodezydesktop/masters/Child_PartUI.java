/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import trixware.erp.prodezydesktop.Model.ChildPartModel;
import trixware.erp.prodezydesktop.components.SubAssembly;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Harshu
 * created by HArshali for update BOM of Assembled Parts 27-Jun-2019
 */
public class Child_PartUI extends javax.swing.JPanel {

    String addEmpAPICall;
    ArrayList<SubAssembly> child_model = null;
    ArrayList<ChildPartModel> all_part = null;
    ArrayList<ChildPartModel> all_rm = null;
    ArrayList<SubAssembly> bought_out = null;
    private JPanel panel_fg, panel_rm;
    int Selected_fgid;
    private JScrollPane scroll;

    /**
     * Creates new form Chid_fgPartUI
     */
    public Child_PartUI() 
    {

        
    }

    public Child_PartUI(int id) 
    {
        initComponents();
        Selected_fgid = id;

        panel_fg = new JPanel();
        panel_fg.setLayout(new BoxLayout(panel_fg, BoxLayout.Y_AXIS));

        scroll = new JScrollPane(panel_fg,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(20, 40, 400, 360);
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
        panel_rm = new JPanel();
        panel_rm.setLayout(new BoxLayout(panel_rm, BoxLayout.Y_AXIS));

        scroll = new JScrollPane(panel_rm,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(450, 40, 400, 360);
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
        loadcomponunt(id);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public void loadcomponunt(int fg_id) 
    {

           addEmpAPICall = "finishedgoods";
            String result = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
            if (!result.contains("not found") && result != null) 
            {
                HashMap<String, Object> map = new HashMap<String, Object>();
                JSONObject jObject = new JSONObject(result);
                Iterator<?> keys = jObject.keys();

                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    Object value = jObject.get(key);
                    map.put(key, value);
                }

                JSONObject st = (JSONObject) map.get("data");
                JSONArray records = st.getJSONArray("records");

                JSONObject emp = null;
                all_part = new ArrayList<>();
              
                for (int i = 0; i < records.length(); i++) 
                {
                    try {
                        emp = records.getJSONObject(i);
                        ChildPartModel sub = new ChildPartModel();
                        sub.setId(emp.getInt("FG_ID"));
                        sub.setParts(emp.getString("FG_PART_NO"));
                        sub.setPieces(0);
                       
                        all_part.add(sub);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
            }
            
            
          addEmpAPICall = "rawmaterials";
         result = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
        if (!result.contains("not found")) 
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            JSONObject jObject = new JSONObject(result);
            Iterator<?> keys = jObject.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                Object value = jObject.get(key);
                map.put(key, value);
            }

            JSONObject st = (JSONObject) map.get("data");
            JSONArray records = st.getJSONArray("records");
              JSONObject emp = null;
                all_rm = new ArrayList<>();
              
                for (int i = 0; i < records.length(); i++) 
                {
                    try {
                        emp = records.getJSONObject(i);
                        int flag = emp.getInt("bought_out");
                        if( flag == 1)
                        {  
                        ChildPartModel sub = new ChildPartModel();
                        sub.setId(emp.getInt("RM_ID"));
                        sub.setParts(emp.getString("RM_NAME"));
                        sub.setPieces(0);
                        all_rm.add(sub);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
            }
            

        
          
        String addEmpAPICall = "assembledparts?assembled_id=" + fg_id;
        String result2 = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");

        if (!result2.contains("not found") && result2 != null) 
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
            child_model = new ArrayList<>();
            panel_fg.setBounds(0, 0, 400, 40 * record.length());
            bought_out = new ArrayList<>();
            panel_rm.setBounds(0, 0, 400, 40 * record.length());
            JSONObject emp1 = null;
            // Aseem_list = new ArrayList<String[]>();
            ArrayList<Integer> id_fg=new ArrayList<>();
            ArrayList<Integer> id_rm=new ArrayList<>();
            int h=record.length();
            for (int j = 0; j < record.length(); j++) 
            {
                emp1 = record.getJSONObject(j);
                int flag = emp1.getInt("child_part_id");
                if( flag != 0)
                {   
                id_fg.add(emp1.getInt("child_part_id"));
                SubAssembly sub = new SubAssembly();
                sub.setBounds(0, (j * 40), 400, 40);
                sub.setRecord_id(emp1.getInt("assem_id"));
                sub.setId(emp1.getInt("child_part_id"));
                sub.setSub_parts(emp1.get("FG_PART_NO").toString());
                sub.setPieces(emp1.getInt("child_part_qty"));
                panel_fg.add(sub);
                panel_fg.revalidate();
                child_model.add(sub);
                
                
                }
                
                  int out = emp1.getInt("boughtout_rmid");
                    if (out != 0) 
                    {
                        SubAssembly sub = new SubAssembly();
                        id_rm.add(emp1.getInt("boughtout_rmid"));
                        sub.setBounds(0, (j * 40), 400, 40);
                        sub.setRecord_id(emp1.getInt("assem_id"));
                        sub.setId(emp1.getInt("boughtout_rmid"));
                        sub.setSub_parts(emp1.getString("RM_NAME"));
                        sub.setPieces(emp1.getInt("boughtout_rmqty"));
                        panel_rm.add(sub);
                        panel_rm.revalidate();
                        bought_out.add(sub);
                    }
            }
            
            
            
                      for(int j = 0; j < all_part.size();j++)
                        {
                          String abc= "true";
                          for(int k=0 ; k< id_fg.size() ; k++)
                          {
                            int a  =all_part.get(j).getId();
                            int b  =id_fg.get(k);
                            if(  a != b )
                            {
                              abc= abc + " true";
                            }
                            else
                            {
                              abc= abc + " false";
                            }
                          }
                          
                          if( !abc.contains("false"))
                          {
                            SubAssembly pane = new SubAssembly();
                            pane.setBounds(0, (h++ * 40), 280, 40);
                            pane.setRecord_id(0);
                            pane.setId(all_part.get(j).getId());
                            pane.setSub_parts(all_part.get(j).getParts());
                            pane.setPieces(0);
                            panel_fg.add(pane);
                            child_model.add(pane);
                          }
                        }
                        for(int j = 0; j < all_rm.size();j++)
                        {
                          String abc= "true";
                          for(int k=0 ; k< id_rm.size() ; k++)
                          {
                            int a  =all_rm.get(j).getId();
                            int b  =id_rm.get(k);
                            if(  a != b )
                            {
                              abc= abc + " true";
                            }
                            else
                            {
                              abc= abc + " false";
                            }
                          }
                          
                          if( !abc.contains("false"))
                          {
                            SubAssembly pane = new SubAssembly();
                            pane.setBounds(0, (h++ * 40), 280, 40);
                            pane.setRecord_id(0);
                            pane.setId(all_rm.get(j).getId());
                            pane.setSub_parts(all_rm.get(j).getParts());
                            pane.setPieces(0);
                            panel_rm.add(pane);
                            bought_out.add(pane);
                          }
                        }

            
        
            
        } else 
        {

                child_model = new ArrayList<>();
                panel_fg.setBounds(0, 0, 400, 40 * all_part.size());
                for (int i = 0; i < all_part.size(); i++) 
                {
                    try {
                       // emp = records.getJSONObject(i);
                        SubAssembly sub = new SubAssembly();
                        sub.setBounds(0, (i * 40), 400, 40);
                        
                        sub.setId(all_part.get(i).getId());
                        sub.setSub_parts(all_part.get(i).getParts());
                        sub.setPieces(0);
                        panel_fg.add(sub);
                        panel_fg.revalidate();
                        child_model.add(sub);
                    } catch (Exception e) 
                    {
                        System.out.println(e.getMessage());
                    }

                }
                
                 bought_out = new ArrayList<>();
                 panel_rm.setBounds(0, 0, 400, 40 * all_rm.size());
                  for (int i = 0; i < all_rm.size(); i++) 
                   { 
               
                        SubAssembly sub = new SubAssembly();
                        sub.setBounds(0, (i * 40), 400, 40);
                        sub.setId(all_rm.get(i).getId());
                        sub.setSub_parts(all_rm.get(i).getParts());
                        sub.setPieces(0);
                        panel_rm.add(sub);
                        panel_rm.revalidate();
                        bought_out.add(sub);
                    }
               

            
            
        }
        
    }
    ArrayList<ChildPartModel> selected_parts = new ArrayList<>();
    ArrayList<ChildPartModel> selected_rm = new ArrayList<>();

    public ArrayList<ChildPartModel> getchildpart() 
    {

        for (int i = 0; i < child_model.size(); i++) 
        {

            ChildPartModel cpm = new ChildPartModel();
            if (child_model.get(i).getPieces() > 0) 
            {  
                cpm.setRecord_id(child_model.get(i).getRecord_id());
                cpm.setId(child_model.get(i).getId());
                cpm.setParts(child_model.get(i).getSub_parts());
                cpm.setPieces(child_model.get(i).getPieces());
                selected_parts.add(cpm);
            }
        }

        ChildPartModel cpm2 = null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < selected_parts.size(); i++) {
            cpm2 = selected_parts.get(i);
            sb.append(cpm2.getRecord_id());
            sb.append(cpm2.getId());
            sb.append(cpm2.getParts());
            sb.append(cpm2.getPieces());
            sb.append("\n");
        }

        return selected_parts;
    }

    public ArrayList<ChildPartModel> getboughtRM() 
    {

        for (int i = 0; i < bought_out.size(); i++) 
        {

            ChildPartModel cpm = new ChildPartModel();
            if (bought_out.get(i).getPieces() > 0)
            { 
                cpm.setRecord_id(bought_out.get(i).getRecord_id());
                cpm.setId(bought_out.get(i).getId());
                cpm.setParts(bought_out.get(i).getSub_parts());
                cpm.setPieces(bought_out.get(i).getPieces());

                selected_rm.add(cpm);
            }
        }

        ChildPartModel cpm2 = null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < selected_rm.size(); i++) 
        {
            cpm2 = selected_rm.get(i);
            sb.append(cpm2.getRecord_id());
            sb.append(cpm2.getId());
            sb.append(cpm2.getParts());
            sb.append(cpm2.getPieces());
            sb.append("\n");
        }

        return selected_rm;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("Child Parts ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("Count");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addComponent(jLabel2))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("Bought Out RM");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("Count");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addComponent(jLabel4))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(417, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
     
    public void onsaveButton()
    {
       String result;
        ArrayList<ChildPartModel> getchid_fgpart_count = getchildpart();

        for (int i = 0; i < getchid_fgpart_count.size(); i++) 
        {
            if(getchid_fgpart_count.get(i).getRecord_id() == 0)
            {   
            addEmpAPICall = "assembled_add?ref_assembled_fgid=" + Selected_fgid
                    + "&child_part_id=" + getchid_fgpart_count.get(i).getId()
                    + "&child_part_qty=" + getchid_fgpart_count.get(i).getPieces();
            result = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
            JOptionPane.showMessageDialog(null, result);
            }else
            {
                 addEmpAPICall = "assembled_update?assem_id="+getchid_fgpart_count.get(i).getRecord_id() 
                    +"&ref_assembled_fgid=" + Selected_fgid
                    + "&child_part_id=" + getchid_fgpart_count.get(i).getId()
                    + "&child_part_qty=" + getchid_fgpart_count.get(i).getPieces();
            result = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
            JOptionPane.showMessageDialog(null, result);
            }

        }
        
        
        ArrayList<ChildPartModel> getboughtout_count = getboughtRM();
        for (int i = 0; i < getboughtout_count.size(); i++) 
        {
            if(getboughtout_count.get(i).getRecord_id() == 0)
            {
            addEmpAPICall = "assembled_add?ref_assembled_fgid=" + Selected_fgid
                    + "&boughtout_rmid=" + getboughtout_count.get(i).getId()
                    + "&boughtout_rmqty=" + getboughtout_count.get(i).getPieces();
            result = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
            JOptionPane.showMessageDialog(null, result);
            }
            else
            {
                 addEmpAPICall = "assembled_update?assem_id="+getboughtout_count.get(i).getRecord_id() 
                    +"&ref_assembled_fgid=" + Selected_fgid
                    + "&boughtout_rmid=" + getboughtout_count.get(i).getId()
                    + "&boughtout_rmqty=" + getboughtout_count.get(i).getPieces();
            result = WebAPITester.prepareWebCall(addEmpAPICall, StaticValues.getHeader(), "");
            JOptionPane.showMessageDialog(null, result);
            }
   
        }

     reset();
     
    }
    public void reset() 
		{

            for (int i = 0; i < child_model.size(); i++) 
            {

                if (child_model.get(i).getPieces()> 0) 
                {
                    child_model.get(i).setPieces(0);
                }
                
            }
             for (int i = 0; i < bought_out.size(); i++) 
            {

                if (bought_out.get(i).getPieces()> 0) 
                {
                    bought_out.get(i).setPieces(0);
                }
                
            }

        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
