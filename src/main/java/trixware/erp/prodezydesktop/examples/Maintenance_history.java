/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import trixware.erp.prodezydesktop.Model.Maintain_typeDR;
import trixware.erp.prodezydesktop.components.IntegerInputField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.DieDR;
import trixware.erp.prodezydesktop.Model.EmployeeDR;
import trixware.erp.prodezydesktop.Model.Main_typeModel;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.components.ButtonForm;
import trixware.erp.prodezydesktop.components.MaintenanceTypePanel;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author dell
 */
public class Maintenance_history extends javax.swing.JPanel 
{

    
    int selectedemp_ID,selecteddie_ID;
    JTable jt=new JTable();
    JPanel jp1;
    JTabbedPane jtp;
    IntegerInputField strokes_maintain;
    //TextInputForm tcm ;
    datechooser.beans.DateChooserCombo datechoosercombo1;
    
    ArrayList<Maintain_typeDR> maintain_type = null;
    MaintainList maintain = null ;
    
    Maintain_typeDR maintain1=null;
    
    ArrayList<Main_typeModel> maintenance_type_DetailList=null;
    
    ArrayList<DieDR> die=null;
    ArrayList<EmployeeDR> employee=null;
      
    String x,x1;Date date=null;  
    /**
     * Creates new form Maintenance_history
     */
    public Maintenance_history() 
    {
        initComponents();
        
        loadcontent();
        
        
         JPanel jpmain=new JPanel();
        
        jtp=new JTabbedPane();
        jp1=new JPanel();
        JPanel jp2=new JPanel();
        
        
        jp1.setLayout(null);
        
        
        jtp.add(new JScrollPane(jp1),"Create new Maintenance History");
        jtp.add(new JScrollPane(jp2),"Data");
        jtp.setBounds(0, 0  , 1250, 550);
        
        setPreferredSize(new Dimension(1250, 550));
        add(jtp);
        
        JLabel lbl1=new JLabel("Date & Time");
         datechoosercombo1 = new datechooser.beans.DateChooserCombo();
         lbl1.setFont(new java.awt.Font("Leelawadee UI", 0, 12));
         lbl1.setBounds(280, 180, 220, 35);
         datechoosercombo1.setBounds(280, 220, 220, 35);
         jp1.add(lbl1);
         jp1.add(datechoosercombo1);
        
        //tcm=new TextInputForm("Cost of Maintenance", false, "Enter Cost maintenance");
        //die_no.setBounds(60,100,260 , 70);
        //tcm.setBounds(60,290,260 , 70);
        //jp1.add(tcm);
        
        //tcm.setEnabled(false);
        
        strokes_maintain=new IntegerInputField("Strokes at the time of maintenance", true, "Enter Cost maintenance");
        //die_no.setBounds(60,100,260 , 70);
        strokes_maintain.setBounds(270,280,260 , 70);
        jp1.add(strokes_maintain);
        
        
        JLabel lbl2=new JLabel("Activity");
        lbl2.setFont(new java.awt.Font("Leelawadee UI", 0, 12));
        maintain = new MaintainList ();
        lbl2.setBounds(650, 25, 220, 35);
        maintain.setBounds ( 650 , 60 , 260 , 260 );
        //JScrollPane sp1=new JScrollPane(maintain,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //sp1.setBounds ( 650 , 80 , 260 , 260 );
        jp1.add(lbl2);
        jp1.add( maintain );
        
        JLabel lbl3=new JLabel("Cost");
        lbl3.setFont(new java.awt.Font("Leelawadee UI", 0, 12));
        lbl3.setBounds(840, 25, 60, 35);
        jp1.add(lbl3);
         
        ButtonForm btnadd=new ButtonForm("Save", null);
        btnadd.setBounds(310, 370, 300, 50);
        //btnadd.addActionListener(save);
        jp1.add(btnadd);
        
        ButtonForm btnreset=new ButtonForm("Reset", null);
        btnreset.setBounds(610, 370, 300, 50);
        jp1.add(btnreset);
        
        ButtonForm btnedit=new ButtonForm("Edit", null);
        btnedit.setBounds(310, 420, 300, 50);
        jp1.add(btnedit);
        btnedit.setEnabled(false);
        
        ButtonForm btnexport=new ButtonForm("Export", null);
        btnexport.setBounds(610, 420, 300, 50);
        jp1.add(btnexport);
        btnexport.setEnabled(false);
        
        ButtonForm btnclose=new ButtonForm("Close", null);
        btnclose.setBounds(310, 470, 300, 50);
        jp1.add(btnclose);
        
       
        
        jp2.setLayout(null);
         //jt.setBounds(100, 100, 600, 300);
         JScrollPane sp=new JScrollPane(jt,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                           JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                  //sp.setViewportView(jt ) ;
         sp.setBounds(100, 100, 600, 300);
         //sp.setBounds(100, 100, 600, 300);    
         
         jp2.add(sp);
         
          selecteddie_ID  = (die.get ( jComboBox1.getSelectedIndex () ).DIE_ID);
          selectedemp_ID  = (employee.get ( jComboBox2.getSelectedIndex () ).EMP_ID);
        
          
    btnadd.AddActionListener(new ActionListener() 
    {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            
                
            
            ArrayList<Main_typeModel> maintenance_type_DetailList=null;
           
            String activities="";
            String DT_ID=null,date_time=null,cost_mt=null,stroke_mt=null,emp_id=null;
            
            //maintenance_type_DetailList.clear();
            
//                if( totalcost_maintain ==0 ){
//                   // JOptionPane.showMessageDialog ( null, "Total cost maintenance does not macth to total of all maintenance type cost");
//                   JOptionPane.showMessageDialog(null,"Maintenance of cost is empty" );
//                }
//                else
//                {
                    String result2="";

                    //date fetching function
                    callingdatefun();
                        date_time=x1;
                        //cost_mt=tcm.getText().toString();
                        stroke_mt=strokes_maintain.getText().toString();
                
                        selecteddie_ID  = (die.get ( jComboBox1.getSelectedIndex () ).DIE_ID);
                        selectedemp_ID  = (employee.get ( jComboBox2.getSelectedIndex () ).EMP_ID);
                        
                        maintenance_type_DetailList = maintain.getMaintain_type();
                
                        float totalcost_maintain = 0 ;
                        for(  int j=0 ;  j<maintenance_type_DetailList.size(); j++ )
                        { 
                                totalcost_maintain = totalcost_maintain + maintenance_type_DetailList.get(j).getTlcostmain();
                                if((maintenance_type_DetailList.get(j).getTlcostmain())!=0)
                                {
                                    activities=activities+maintenance_type_DetailList.get(j).getMain_TypeId()+",";
                                }
                            }
                //Maintenance history add with multiple activities and total cost 
                String temp="0";
                    if(!stroke_mt.isEmpty() && !stroke_mt.equals(temp) )
                    {  
                        if( maintenance_type_DetailList.size()!=0)
                        {
                        String addEmpAPICall=null ;
                        try{
                    addEmpAPICall = "maintenance_history_add?DT_ID="+URLEncoder.encode(selecteddie_ID+"", "UTF-8")+"&date_time="+URLEncoder.encode(date_time, "UTF-8")+"&cost_mt="+URLEncoder.encode(totalcost_maintain+"" , "UTF-8")+"&stroke_mt="+URLEncoder.encode(stroke_mt, "UTF-8")+"&emp_id="+URLEncoder.encode( selectedemp_ID+"", "UTF-8")+"&activities="+URLEncoder.encode( activities, "UTF-8");
                        }
                        catch(Exception fg){JOptionPane.showMessageDialog(null, "maintenance history add error");}
                    result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                    JOptionPane.showMessageDialog ( null , "Maintenance history record added Successfully..!");
                    
                    
                     int insertid=iDFromJSON ( result2 );
                     
                   //One by one cost and activity store in maintenance history logs table
                    String MH_REF_UID,MH_ACT_ID,MH_ACT_CST,MH_ACT_COMM="Spare part change",REF_DT_ID;
                   float totalcost_maintain12=0;
                    if(maintenance_type_DetailList.size()!=0)
                    {
                        
                    for(  int j=0 ;  j<maintenance_type_DetailList.size(); j++ ){ 
                        totalcost_maintain12 = totalcost_maintain12 + maintenance_type_DetailList.get(j).getTlcostmain();
                        if((maintenance_type_DetailList.get(j).getTlcostmain())!=0)
                        {
                            //activities=activities+maintenance_type_DetailList.get(j).getMain_TypeId()+",";
                            int actid;
                            float costs;
                            String act_comm;
                            costs=maintenance_type_DetailList.get(j).getTlcostmain();
                            actid=maintenance_type_DetailList.get(j).getMain_TypeId();
                            act_comm= maintenance_type_DetailList.get(j).getType_Str();   
//                            if(!costs.isEmpty())
//                            {
//                    if(maintenance_type_DetailList1.size()!=0)
//                    {
                           String EmpAPICall=null;
                           try{ 
                             EmpAPICall = "maint_history_logs_add?MH_REF_UID="+URLEncoder.encode(insertid+"", "UTF-8")+"&MH_ACT_ID="+URLEncoder.encode(actid+"", "UTF-8")+"&MH_ACT_CST="+URLEncoder.encode(costs+"", "UTF-8")+"&MH_ACT_COMM="+URLEncoder.encode(act_comm, "UTF-8")+"&REF_DT_ID="+URLEncoder.encode( selecteddie_ID+"", "UTF-8");
                           }
                           catch(Exception hh){ JOptionPane.showMessageDialog ( null , "maintenance history logs add error"); }
                             String result3 =  WebAPITester.prepareWebCall ( EmpAPICall, StaticValues.getHeader ()   , "");
                    //JOptionPane.showMessageDialog ( null , result3);
                            //}
                        }
//                            else
//                            {
//                                JOptionPane.showMessageDialog ( null ,"Please fill or select all fields marked with * ");
//                            }
                        }


                        //Die master table update the cost of maintenance  
                        addEmpAPICall = "die_tool";
                        result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

                        if( ! result2.contains( "not found" ))
                        {
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
                                die = new ArrayList<DieDR> ();
                                for ( int i = 0 ; i < records.length () ; i ++ ) {

                                    emp = records.getJSONObject ( i);
                                    //jComboBox1.addItem ( emp.get ( "die_tool_no" ).toString () );
                                    die.add( new DieDR ( Integer.parseInt(emp.get ( "DT_ID" ).toString ()), emp.get ( "die_tool_no" ).toString (), Float.parseFloat(emp.get ( "cost_maintenance" ).toString ()) )); 


                                    if((Integer.parseInt(emp.get ( "DT_ID" ).toString ()))==selecteddie_ID)
                                    {
                                       Float cst=Float.parseFloat(emp.get ( "cost_maintenance" ).toString ());


                                       cst= cst+(totalcost_maintain12);

                                       String updateapicall=null,resul;
                                      try{          
                                      updateapicall = "die_tool_update?DT_ID="+URLEncoder.encode((Integer.parseInt(emp.get ( "DT_ID" ).toString ()))+"", "UTF-8")+"&cost_maintenance="+URLEncoder.encode(cst+"", "UTF-8");
                                      }
                                      catch(Exception e23){e23.getMessage();}
                                      resul =  WebAPITester.prepareWebCall ( updateapicall, StaticValues.getHeader ()   , "");
                                JOptionPane.showMessageDialog ( null , "Die maintenance cost is updated");
                                    }

//                                emp = records.getJSONObject ( i);
//                                //jComboBox1.addItem ( emp.get ( "die_tool_no" ).toString () );
//                                die.add( new DieDR ( Integer.parseInt(emp.get ( "DT_ID" ).toString ()), emp.get ( "die_tool_no" ).toString (), Float.parseFloat(emp.get ( "cost_maintenance" ).toString ()) )); 
//                                
//                                
//                                if((Integer.parseInt(emp.get ( "DT_ID" ).toString ()))==selecteddie_ID)
//                                {
//                                   Float cst=Float.parseFloat(emp.get ( "cost_maintenance" ).toString ());
//                                   cst= cst+(totalcost_maintain12);
//                                   
//                                   String updateapicall=null,resul;
//                                 if(maintenance_type_DetailList.size()!=0)
//                                 {
//                                     try{
//                                  updateapicall = "die_tool_update?DT_ID="+URLEncoder.encode((Integer.parseInt(emp.get ( "DT_ID" ).toString ()))+"", "UTF-8")+"&cost_maintenance="+URLEncoder.encode(cst+"", "UTF-8");
//                                     }catch(Exception rr){ JOptionPane.showMessageDialog ( null , "die update error");}
//                                  resul =  WebAPITester.prepareWebCall ( updateapicall, StaticValues.getHeader ()   , "");
//                            JOptionPane.showMessageDialog ( null , "Die maintenance cost is updated");
//                                 }                             
 //                               }
                            }
                        }
                    loadcontent();
                    resetFields();
                    maintain.reset();
                    }
                    else{
                       JOptionPane.showMessageDialog ( null ,"Please fill Activity fields .."); 
                    }

                    //}
//                    else
//                    {
//                         JOptionPane.showMessageDialog ( null ,"Please fill or select all fields marked with * ");
//                    }

//                        try{
//                        //get Maintenance History ID 
//                            JSONObject id=null;
//                            JSONObject jObject = new JSONObject( result2 );
//                            JSONObject st = jObject.getJSONObject("data");
//                            insertid = st.getInt("insert_id");
//                            //insertid=id.toString();
//                            //JOptionPane.showMessageDialog(null, insertid);
//                        
//                        }catch(Exception js){js.getMessage();
//                        JOptionPane.showMessageDialog(null, "json exception id message");}

                //}
//                }catch(Exception e2){
//                    e2.getMessage();
//                    JOptionPane.showMessageDialog(null, "Maintenance entry not added");
                    }
                        else
                        {
                             JOptionPane.showMessageDialog ( null ,"Please add cost details for at least one maintenance activity"); 
                 
                        }
                    } 
                else{
                         JOptionPane.showMessageDialog ( null ,"Strokes field must be non zero(0)");                  
                         }
            }
        });
        
        btnedit.AddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            Date date=null;
//            
//            ArrayList<Main_typeModel> maintenance_type_DetailList=null;
//            //maintenance_type_DetailList.clear();
//               
//            String DT_ID=null,date_time=null,cost_mt=null,stroke_mt=null,emp_id=null,activities="";
               
            }
        });
        
        btnreset.AddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                resetFields();
                maintain.reset();
            }
        });
        
         btnclose.AddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             HomeScreen.home.removeForms ();
            }
        });
         
         
         jtp.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            int xc=jtp.getSelectedIndex();
                    if(xc==0)
                    {
                        jLabel1.setVisible(true);
                        jComboBox1.setVisible(true);
                        jLabel2.setVisible(true);
                        jComboBox2.setVisible(true);
                        jLabel3.setVisible(true);
                        jLabel5.setVisible(true);
                    }else
                    {
                        jLabel1.setVisible(false);
                        jComboBox1.setVisible(false);
                        jLabel2.setVisible(false);
                        jComboBox2.setVisible(false);
                        jLabel3.setVisible(false);
                        jLabel5.setVisible(false);
                    }  
            }
        });
         
    //    maintain.
        
          repaint();
          revalidate();
          jp1.setBackground(Color.WHITE);
          jp2.setBackground(Color.WHITE);
          setBackground(Color.WHITE);
          setVisible(true);
        
    }
    
     public void loadcontent()
    {
        ArrayList<Main_typeModel> maintenance_type_DetailList=null;
        MaintainList maintain = null ;
        
        String addEmpAPICall,result2;
              if ( jComboBox1.getItemCount () == 0 ) {
                    //result = DB_Operations.getMasters ( "raw_material_type" );
                   try{
                    addEmpAPICall = "die_tool";
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
                            die = new ArrayList<DieDR> ();
                            for ( int i = 0 ; i < records.length () ; i ++ ) {

                                emp = records.getJSONObject ( i);
                                jComboBox1.addItem ( emp.get ( "die_tool_no" ).toString () );
                                die.add( new DieDR ( Integer.parseInt(emp.get ( "DT_ID" ).toString ()), emp.get ( "die_tool_no" ).toString (), Float.parseFloat(emp.get ( "cost_maintenance" ).toString ()) )); 
                            }
                    }
                   }
                    catch(JSONException j){j.getMessage();}
                }
              
              if ( jComboBox2.getItemCount () == 0 ) {
                    //result = DB_Operations.getMasters ( "raw_material_type" );
                   try{
                    addEmpAPICall = "employees";
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
                            employee= new ArrayList<EmployeeDR> ();
                            for ( int i = 0 ; i < records.length () ; i ++ ) {

                                emp = records.getJSONObject ( i);
                                jComboBox2.addItem ( emp.get ( "EMP_NAME" ).toString () );
                                employee.add( new EmployeeDR ( Integer.parseInt(emp.get ( "EmployeePK" ).toString ()), emp.get ( "EMP_NAME" ).toString () )); 
                            }
                    }
                   }catch(JSONException j1){j1.getMessage();}
                }
              
         String EmpAPICall = "maint_history_logs";
         String tab =  WebAPITester.prepareWebCall ( EmpAPICall, StaticValues.getHeader ()   , "");

                if ( tab!= null    && ! tab.contains( "not found"    ) )  {
                     jt.setModel ( buildTableModelfromJSON (tab ) );
                     jt.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                     jt.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                }    
    }
     
     public static DefaultTableModel buildTableModelfromJSON ( String  JSONList )
             {

            Vector<String> columnNames = new Vector<String> ();
            int columnCount = 0;
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        
            JSONArray jARR = null;
            JSONObject jOB = null;
        
            HashMap<String, Object> map = new HashMap<String, Object>();
            JSONObject jObject = new JSONObject( JSONList );
            Iterator<?> keys = jObject.keys();

            while( keys.hasNext() ){
                String key = (String)keys.next();
                Object value = jObject.get ( key ) ; 
                map.put(key, value);
            }
            
            JSONObject st = (JSONObject) map.get ( "data" );
            JSONArray records = st.getJSONArray ( "records");
            
            JSONObject die = null;
            //String MH_REF_UID,MH_ACT_ID,MH_ACT_CST,MH_ACT_COMM="Spare part change",REF_DT_ID;
                columnNames = new Vector<String> ();
                columnNames.add( "MHL_ID"  );
                columnNames.add( "MH_REF_UID"  );
                columnNames.add( "MH_ACT_ID"  );
                columnNames.add( "MH_ACT_CST"  );
                columnNames.add( "MH_ACT_COMM"  );
                columnNames.add( "REF_DT_ID");
                
           

        // data of the table
        for( int i=0; i<records.length (); i++ ){
        Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 0 ; columnIndex <columnNames.size () ; columnIndex ++ ) {
                die = records.getJSONObject ( i);
                vector.add ( die.get ( columnNames.get ( columnIndex ) )  );
            }
            data.add ( vector );
        }
        return new DefaultTableModel ( data , columnNames );
    }
     
       public void resetFields () 
       {

        //tcm.setText ( "" );
        strokes_maintain.setText ( "" );
        maintain.reset();
      //  remove( maintain  );

       // maintain = new MaintainList ();
      //  maintain.setBounds( 650 , 80 , 260 , 260 );
      //  jp1.add( maintain );

    }
      //Getting date 
       public void callingdatefun()
       {
//           x=datechoosercombo1.getText().toString();
//                        SimpleDateFormat sdf211 = new SimpleDateFormat ( "dd/MM/yy" );
//                        try{
//                            date=sdf211.parse(x);
//                        }
//                        catch(Exception ec){JOptionPane.showMessageDialog(null, "date error");}
//                        
//                        SimpleDateFormat sdf21 = new SimpleDateFormat("yyyy-MM-dd");
//                        x1=sdf21.format(date);
//                        System.out.println(x1);
           
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    x1 = sdf.format(datechoosercombo1.getSelectedDate().getTime());
      
                    System.out.println(x1);
       }
     
       

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1100, 500));

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Die (Tool) NO :");

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Maintenance By (Employee form Master)");

        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("         *");

        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setText("         *");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(579, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(310, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    JSONObject jObject ;
    Iterator<?> keys = null;
    public int iDFromJSON( String json){
        int id = 0;
       
        jObject = new JSONObject ( json );
        keys = jObject.keys ();
    
        JSONObject value = (JSONObject) jObject.get ( "data" );
            
        id = Integer.parseInt(value.get ("insert_id" ).toString());
        //    id = Integer.parseInt( st.getString ("insert_id" ));
        
        return id ;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables

     ArrayList<Main_typeModel> selectedmaintain_type = new ArrayList<Main_typeModel>() ;
    
     class MaintainList extends javax.swing.JPanel 
     {

        ResultSet result = null;

        public ArrayList<MaintenanceTypePanel> maintainTypeList  = null ;
        private ArrayList<Main_typeModel> maintaintypeDetailList  = null ;

        /**
         * Creates new form ProdDataConformationScreen
         */
        public MaintainList ()
        {
            initComponents ();

            MaintenaceTypeList panel = new MaintenaceTypeList ();
            panel.setBounds ( 0 , 0 , 260 , 260 );
            panel.setBackground ( Color.white );
            add ( panel );

        }

        @SuppressWarnings( "unchecked" )
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents () {

            jButton1 = new javax.swing.JButton ();
            jButton2 = new javax.swing.JButton ();
            jLabel1 = new javax.swing.JLabel ();

            setBackground ( new java.awt.Color ( 255 , 255 , 255 ) );
            setBorder ( javax.swing.BorderFactory.createTitledBorder ( "" ) );
            setPreferredSize ( new java.awt.Dimension ( 260 , 260 ) );
            setLayout ( null );

            
        }// </editor-fold>                        

        public ArrayList<Main_typeModel> getMaintain_type ( ) 
        {
            // TODO add your handling code here:
            Main_typeModel tlm = null;

            float totalmaintainCost  = 0 ;
            maintaintypeDetailList = new ArrayList<Main_typeModel> () ;
            for ( int i = 0 ; i < maintainTypeList.size () ; i ++ ) {

                tlm = new Main_typeModel();
                if ( maintainTypeList.get ( i ).getTlcostmain()> 0.0 ) {
                    tlm.setMain_TypeId(maintainTypeList.get ( i ).getMain_TypeId());
                    tlm .setType_Str(maintainTypeList.get ( i ).getType_Str() );
                    tlm.setTlcostmain(maintainTypeList.get ( i ).getTlcostmain () );
                    tlm.setTotalmaintain(maintainTypeList.get ( i ).getTotalmaintain());
                    
                    totalmaintainCost = totalmaintainCost + maintainTypeList.get ( i ).getTlcostmain();
                    
                    //lm.setReason ( rejectionList.get ( i ).getReasonStr () );
                   maintaintypeDetailList.add ( tlm );
                }
            }
            
            
           // if(  totalmaintainCost == Integer.parseInt( tcm.getText().toString()) ){
                Main_typeModel tlm2 = null;
                StringBuilder sb = new StringBuilder ();
                for ( int i = 0 ; i < maintaintypeDetailList.size () ; i ++ ) {
                    tlm2 = maintaintypeDetailList.get ( i );

                    sb.append ( tlm2.getTlcostmain ());
                    sb.append ( tlm2.getMain_TypeId());
                    sb.append ( tlm2.getType_Str());
                    sb.append ( "\n" );
                }

                selectedmaintain_type= maintaintypeDetailList;
                //setrejections (rejectionDetailList );
                
                System.out.println ( "filled maintenance activities size = "+maintaintypeDetailList.size()+""); 
                
            //}else{
                
             //   JOptionPane.showMessageDialog (  null, "Total cost maintenance cannot be greater than "+ Integer.parseInt( tcm.getText() ) );
           // }
            
            return selectedmaintain_type ;
        }

        public void reset(){
            
             for ( int i = 0 ; i < maintainTypeList.size () ; i ++ ) {

                if ( maintainTypeList.get ( i ).getTlcostmain()> 0.0 ) {
                    maintainTypeList.get ( i ).setTlcostmain ( 0 ) ;
                }
            }
            
        }
        
        
        // Variables declaration - do not modify                     
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        // End of variables declaration                   

        class MaintenaceTypeList extends JPanel 
        {

            private JPanel panel;
            private JScrollPane scroll;
            private JButton btnAddType;

            public MaintenaceTypeList () 
            {

                setLayout ( new BorderLayout () );
                panel = new JPanel ();
                panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
                //panel.setLayout ( null );
                scroll = new JScrollPane ( panel ,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
                add ( scroll , BorderLayout.CENTER );
                setVisible ( true );
                MaintenanceTypePanel pane = null;

                if( maintainTypeList == null  )
                {
                    
                    maintainTypeList = new ArrayList<MaintenanceTypePanel> ();
                    
                    String EmpAPICall = "maintenance_type";
                    String     result2 =  WebAPITester.prepareWebCall ( EmpAPICall, StaticValues.getHeader ()   , "");

                        if( ! result2.contains( "not found" )   && result2.contains("success"))
                       {  
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
                        maintain_type = new ArrayList<Maintain_typeDR> ();
                        panel.setBounds(0,0,170,500);
                        panel.revalidate ();
                        panel.repaint();
                        //panel.setBounds(0,0,170,36*records.length ());
                        for ( int i = 0 ; i < records.length () ; i ++ ) 
                        {
                            emp = records.getJSONObject ( i);
                            maintain_type.add( new Maintain_typeDR ( Integer.parseInt(emp.get ( "MT_ID" ).toString ()), emp.get ( "activity" ).toString ()   )); 

                            pane = new MaintenanceTypePanel();
                            pane.setBounds(0,(i*40),260,36);
                            pane.setMain_TypeId(Integer.parseInt(emp.get ( "MT_ID" ).toString ()));
                            pane.setType_Str( emp.get ( "activity" ).toString () );
                            pane.setTlcostmain ( 0);
                            pane.setTotalmaintain(0 );
                            panel.add ( pane );
                            panel.revalidate ();
                            panel.repaint();
                            maintainTypeList.add ( pane );
                        }
                    }
                }
            }
        }
    }
}
