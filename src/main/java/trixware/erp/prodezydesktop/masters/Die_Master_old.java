/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.components.ButtonForm;
import trixware.erp.prodezydesktop.components.SpinnerForm;
import trixware.erp.prodezydesktop.components.TextInputForm;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author dell
 */
public class Die_Master_old extends javax.swing.JPanel {

    ArrayList<String> machines = null;
    /**
     * Creates new form Die_Master
     */
    public Die_Master_old() {
        initComponents();
        
        JPanel jpmain=new JPanel();
        
        JTabbedPane jtp=new JTabbedPane();
        JPanel jp1=new JPanel();
        JPanel jp2=new JPanel();
        
        
        jp1.setLayout(null);
        
        
        jtp.add(new JScrollPane(jp1),"Form");
        jtp.add(new JScrollPane(jp2),"Data");
        jtp.setBounds(0, 0  , 1250, 550);
        
        setPreferredSize(new Dimension(1250, 550));
        add(jtp);
        
        
        TextInputForm die_no1=new TextInputForm("Die(Tool) No", true, "Enter Die ID");
        //die_no.setBounds(60,100,260 , 70);
        die_no1.setBounds(30,30,260 , 70);
        jp1.add(die_no1);
        
        TextInputForm die_name1=new TextInputForm("Die(Tool) Name", true, "Enter Die name");
        //die_name.setBounds(60,170,260 , 70);
        die_name1.setBounds(330,30,260 , 70);
        jp1.add(die_name1);
        
        TextInputForm total_strokes1=new TextInputForm("Total Strokes(Tool Life)", true, "total stroke");
        //total_strokes.setBounds(60, 240, 260, 70);
        total_strokes1.setBounds(630, 30, 260, 70);
        jp1.add(total_strokes1);
        
        TextInputForm main_stroke1=new TextInputForm("Maintainance Strokes", true, "maintainance stroke");
        //main_stroke.setBounds(60, 310, 260, 70);
        main_stroke1.setBounds(30, 100, 260, 70);
        jp1.add(main_stroke1);
        
        TextInputForm rem_count1=new TextInputForm("Reminder Count", true, "Reminder count");
        rem_count1.setBounds(330, 100, 260, 70);
        jp1.add(rem_count1);
        
        TextInputForm date_wise_main1=new TextInputForm("Date Wise Maintainance", true, "Date wise maintainance");
        //date_wise_main.setBounds(370, 100, 260, 70);
        date_wise_main1.setBounds(630, 100, 260, 70);
        jp1.add(date_wise_main1);
        
        TextInputForm rem_date1=new TextInputForm("Reminder Date", true, "Reminder date");
        //rem_date.setBounds(370, 170, 260, 70);
        rem_date1.setBounds(30, 170, 260, 70);
        jp1.add(rem_date1);
        
        TextInputForm date_purchase1=new TextInputForm("Date of Purchase", false, "");
        //date_purchase.setBounds(370, 240, 260, 70);
        date_purchase1.setBounds(330, 170, 260, 70);
        jp1.add(date_purchase1);
        
        TextInputForm cost_purchase1=new TextInputForm("Cost of Purchase", true, "Cost Of purchase"); 
        //cost_purchase.setBounds(370, 310, 260, 70);
        cost_purchase1.setBounds( 630, 170, 260, 70);
        jp1.add(cost_purchase1);
        
        TextInputForm shut_height1=new TextInputForm("Shut Height", false, "Shut Height");
        //shut_height.setBounds(370, 380, 260, 70);
        shut_height1.setBounds(30, 240, 260, 70);
        jp1.add(shut_height1);
        
        TextInputForm tonnage1=new TextInputForm("Tonnage", false, "Tonnage");   
        //tonnage.setBounds(700, 100, 260, 70);
        tonnage1.setBounds(330, 240, 260, 70);
        jp1.add(tonnage1);
        
        TextInputForm dimensions1=new TextInputForm("Dimensions", false, "Dimensions");
        //dimensions.setBounds(700, 170, 260, 70);
        dimensions1.setBounds(630, 240, 260, 70);
        jp1.add(dimensions1);
        
        TextInputForm cost_maintainance1=new TextInputForm("Maintainance Cost", true, "Cost of Maintainance");
        //cost_maintainance.setBounds(700, 240, 260, 70);
        //cost_maintainance.setBounds(30, 310, 260, 70);
        cost_maintainance1.setBounds(960, 30, 260 , 70);
        jp1.add(cost_maintainance1);
        
         
        TextInputForm owned_by1=new TextInputForm("Owned BY", true, "Enter Owner name");
        //cost_maintainance.setBounds(700, 240, 260, 70);
        //cost_maintainance.setBounds(30, 310, 260, 70);
        owned_by1.setBounds(960, 100, 260 , 70);
        jp1.add(owned_by1);
        
        
        SpinnerForm machine1=new SpinnerForm("Machine", true, "Select machine",machines);
        //TextAreaInputForm machine1=new TextAreaInputForm("Machine", false, "");
        //machine.setBounds(700, 310, 260, 150);
        //machine.setBounds(30, 380, 260, 150);
        machine1.setBounds(960, 170, 260, 150);
        jp1.add(machine1);
        
         
        ButtonForm btnadd=new ButtonForm("Save", null);
        btnadd.setBounds(170, 370, 300, 50);
        //btnadd.addActionListener(save);
        jp1.add(btnadd);
        
        ButtonForm btnreset=new ButtonForm("Reset", null);
        btnreset.setBounds(470, 370, 300, 50);
        jp1.add(btnreset);
        
        ButtonForm btnedit=new ButtonForm("Edit", null);
        btnedit.setBounds(170, 420, 300, 50);
        jp1.add(btnedit);
        
        ButtonForm btnexport=new ButtonForm("Export", null);
        btnexport.setBounds(470, 420, 300, 50);
        jp1.add(btnexport);
        
        ButtonForm btnclose=new ButtonForm("Close", null);
        btnclose.setBounds(770, 420, 300, 50);
        jp1.add(btnclose);
        
        //JLabel print=new JLabel("Print/Export Die");
        //print.setBounds(60, 470, 120, 50);
        
        ButtonForm btnprint=new ButtonForm("Print/Export History",null);
        btnprint.setBounds(770, 370, 300, 50);
        jp1.add(btnprint);
        
        JLabel tab=new JLabel("Table Value");
        //tab.setBounds(60, 520, 120, 50);
        
        ButtonForm btntab=new ButtonForm("Print Table",null);
        //btntab.setBounds(370, 520, 300, 50);
        
        jp2.setLayout(null);
         String data[][]={ {"","",""},    
                          {"","",""},    
                          {"","",""}};    
         String column[]={"Die ID","Die NAME","Total Stroke"};     
         JTable jt=new JTable();
         jt.setModel(   new DefaultTableModel( data, column )   );
         
         jt.setBounds(20, 20, 400, 400);
         JScrollPane sp=new JScrollPane();
                  sp.setViewportView(jt ) ;
         
         jp2.add(jt);
         
         jt.addMouseListener(new MouseAdapter() {
             public void mouseClicked(MouseEvent e)
                 {
                     DefaultTableModel model = ( DefaultTableModel ) jt.getModel ();

                    // get the selected row index
                     int selectedRowIndex = jt.getSelectedRow ();
                     //jTextField6.setText ( model.getValueAt ( selectedRowIndex , 1 ).toString () );
                     jtp.setSelectedIndex(0);
                   //selectedRowIndex += 1;
                 //selectedRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );
                 //System.out.println ( ""+selectedRecordId );
                 }
            });
         
         

        btnadd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                
            //Calendar c2 = Calendar.getInstance();
            //SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss" );
            //String strDate2 = sdf2.format(c2.getTime());   
            
            String die_tool_no, die_name,total_strokes,cost_maintenance,maintenance_strokes,reminder_count,date_maintenance,owner_id,reminder_date,date_purchase,cost_purchase,shut_height,tonnage,dimension,machine_id;
            
            die_tool_no=die_no1.getText();
            die_name=die_name1.getText();
            total_strokes=total_strokes1.getText();
            cost_maintenance=cost_maintainance1.getText();
            maintenance_strokes=main_stroke1.getText();
            reminder_count=rem_count1.getText();
            date_maintenance=date_wise_main1.getText();
            //date_maintenance="2022-04-01";
            owner_id=owned_by1.getText();
            reminder_date=rem_date1.getText();
            //reminder_date="2022-04-01";
            date_purchase=date_purchase1.getText();
            //date_purchase="2022-04-01";
            cost_purchase=cost_purchase1.getText();
            machine_id="machine1";
            //machine_id="5555";
            shut_height=shut_height1.getText();
            tonnage=tonnage1.getText();
            dimension=dimensions1.getText();
            
            try{
                    
                    String addEmpAPICall = "die_tool_add?die_tool_no="+URLEncoder.encode(die_tool_no, "UTF-8")+"&die_name="+URLEncoder.encode(die_name, "UTF-8")+"&total_strokes="+URLEncoder.encode(total_strokes , "UTF-8")+"&cost_maintenance="+URLEncoder.encode(cost_maintenance, "UTF-8")+"&maintenance_strokes="+URLEncoder.encode( maintenance_strokes, "UTF-8")+"&reminder_count="+URLEncoder.encode( reminder_count, "UTF-8")+"&date_maintenance="+URLEncoder.encode( date_maintenance, "UTF-8")+"&owner_id="+URLEncoder.encode( owner_id, "UTF-8")+"&reminder_date="+URLEncoder.encode( reminder_date, "UTF-8")+"&date_purchase="+URLEncoder.encode( date_purchase, "UTF-8")+"&cost_purchase="+URLEncoder.encode( cost_purchase, "UTF-8")+"&machine_id="+URLEncoder.encode( machine_id, "UTF-8")+"&shut_height="+URLEncoder.encode( shut_height, "UTF-8")+"&tonnage="+URLEncoder.encode( tonnage, "UTF-8")+"&dimension="+URLEncoder.encode(dimension, "UTF-8");
                    String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    JOptionPane.showMessageDialog ( null , result2);
                
                }catch(UnsupportedEncodingException e1){
                    e1.getMessage();
                }
            }
        });
        
        btnreset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                JOptionPane.showMessageDialog ( null , "ewser q131");
            }
            
        });
        
        //setBackground(new java.awt.Color(255, 255, 255)); 
          repaint();
          revalidate();
          //setSize(1400,700);
          jp1.setBackground(Color.WHITE);
          setBackground(Color.WHITE);
          //setLayout(null);
          setVisible(true);
    }
    public void loadcontent()
    {
                String addEmpAPICall,result2;
                    //result = DB_Operations.getMasters ( "raw_material_type" );
                   
                    addEmpAPICall = "machines";
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    
                    if( ! result2.contains( "not found" )){
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            JSONObject jObject = new JSONObject( result2 );
                            Iterator<?> keys = jObject.keys();

                            while( keys.hasNext() ){
                                String key = (String)keys.next();
                                Object value = jObject.get ( key ) ; 
                                map.put(key, value);
                            }

                            JSONObject st = (JSONObject) map.get ( "data" );
                            JSONArray records = st.getJSONArray ( "records");

                            JSONObject emp = null;
                            machines = new ArrayList<String> ();
                            for ( int i = 0 ; i < records.length () ; i ++ ) {

                                emp = records.getJSONObject ( i);
                                //jComboBox3.addItem ( emp.get ( "MACHINE_NO" ).toString () );
                                //machines.add( new MachineDR ( Integer.parseInt(emp.get ( "MCH_ID" ).toString ()), emp.get ( "MACHINE_NO" ).toString ()   )); 
                            }
                    }
                
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
