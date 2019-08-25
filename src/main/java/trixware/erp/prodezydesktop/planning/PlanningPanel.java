/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.planning;

import trixware.erp.prodezydesktop.planning.planningModels.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import planning.planningModels.AllocationDetails;
import planning.planningModels.MachineDetails;
import planning.planningModels.ProcDetails;
import planning.planningModels.SampePlan1;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class PlanningPanel extends javax.swing.JPanel {

        Vector<String> firstColumnAvail = new Vector<String> () ;
        Vector<JComboBox> firstColumnAlloc = new Vector<JComboBox> () ;
        
        Vector<String> firstColumnAvailColumn = new Vector<String> () ;
        Vector<String> firstColumnAllocColumn = new Vector<String> () ;
        
        Vector<Vector<String>> avail ;
        Vector<Vector<JComboBox>> alloc ;
    
        ArrayList<String[]> processes = new ArrayList<String[]> () ;
        public static ArrayList<String[]> processMap = new ArrayList<String[]> () ;
        
        
        JTable availabilityTable ;
        JSONObject  planningData = null;
        int columns , no_of_days_for_planning = 0 ;
        
        ArrayList<SampePlan1> plan1 = new ArrayList<SampePlan1>() ;
        SampePlan1  sp = new SampePlan1() ;
        ProcDetails pd = new ProcDetails() ;
        AllocationDetails ad = new AllocationDetails ();
        MachineDetails md = new MachineDetails ();
        
    /**
     * Creates new form PlanningPanel
     */
    public PlanningPanel () {
        
    }    
        
    public PlanningPanel (  JSONObject  _planningData  ) {
        initComponents ();
        
        
        
        planningData = _planningData ;
        no_of_days_for_planning = Integer.parseInt( planningData.getJSONObject("PlantData").get( "PlanningDuration-Days").toString() ) ;
        
        
        
        String firstcallresponse =        getPartProcessMachineData ();
        
        getPartProcessMapData ( firstcallresponse );
        
        Synchronizer synchronizer = new Synchronizer(customScrollPane12, customScrollPane13);
        customScrollPane12.getVerticalScrollBar().addAdjustmentListener(synchronizer);
        //customScrollPane12.getHorizontalScrollBar().addAdjustmentListener(synchronizer);
        customScrollPane13.getVerticalScrollBar().addAdjustmentListener(synchronizer);
        //customScrollPane13.getHorizontalScrollBar().addAdjustmentListener(synchronizer);
        
        Synchronizer synchronizer2 = new Synchronizer(customScrollPane11, customScrollPane13);
        //customScrollPane12.getVerticalScrollBar().addAdjustmentListener(synchronizer);
        customScrollPane11.getHorizontalScrollBar().addAdjustmentListener(synchronizer2);
        //customScrollPane13.getVerticalScrollBar().addAdjustmentListener(synchronizer);
        customScrollPane13.getHorizontalScrollBar().addAdjustmentListener(synchronizer2);
        
        availabilityTable = new JTable() ;
        availabilityTable.setRowHeight ( 33 );
        
        avail  = new Vector<Vector<String>> () ;
        alloc  = new Vector<Vector<JComboBox>> () ;
        
        firstColumnAvailColumn = new Vector<String> () ;
        firstColumnAvail = new Vector<String> () ;

        for(  int i=0; i<processes.size(); i++      ){
            firstColumnAvailColumn.add(  processes.get(i)[ 2 ]   ) ;
        }
        //avail.add( firstColumnAvail);
        
        firstColumnAvail = new Vector<String> () ;        
        for(  int i=0; i<processes.size(); i++      ){
            firstColumnAvail.add(  processes.get(i)[ 6 ]  ) ;
        }
        avail.add( firstColumnAvail);        
        
        firstColumnAvail = new Vector<String> () ;        
        for(  int i=0; i<processes.size(); i++      ){
            if( !  processes.get( i )[7].equals ( "null") &&  !  processes.get( i )[7].equals ( "") ){
                firstColumnAvail.add (  ( Double.parseDouble( processes.get( i )[7]) * no_of_days_for_planning )+"" )     ;
            }else{
                firstColumnAvail.add (  ( Double.parseDouble( "0.0") * no_of_days_for_planning )+"" )     ;
            }
        }
        avail.add( firstColumnAvail);        

        firstColumnAvail = new Vector<String> () ;        
        for(  int i=0; i<processes.size(); i++      ){
            firstColumnAvail.add ( "" );
        }
        avail.add( firstColumnAvail);        
        
        firstColumnAvail = new Vector<String> () ;        
        for(  int i=0; i<processes.size(); i++      ){
            if( !  processes.get( i )[7].equals ( "null") &&  !  processes.get( i )[7].equals ( "") ){
                firstColumnAvail.add (  ( Double.parseDouble( processes.get( i )[7]) * no_of_days_for_planning )+"" )     ;
            }else{
                firstColumnAvail.add (  ( Double.parseDouble( "0.0") * no_of_days_for_planning )+"" )     ;
            }
        }
        avail.add( firstColumnAvail);
        
        availabilityTable.setModel(  new DefaultTableModel (avail, firstColumnAvailColumn  ) ) ;
        
        columns = availabilityTable.getColumnCount ();
        
        if( columns>8 ){
            availabilityTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
            for ( int i = 0 ; i < columns ; i ++ ) {
                availabilityTable.getColumnModel ().getColumn ( i ).setMaxWidth ( 101 );
                availabilityTable.getColumnModel ().getColumn ( i ).setMinWidth ( 100 );
            }
        }else{
            availabilityTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS );
        }
        
        customScrollPane11.setVerticalScrollBarPolicy ( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
        customScrollPane11.setHorizontalScrollBarPolicy ( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        customScrollPane11.setViewportView( availabilityTable ) ;
        
        
        PlanningPanelChildren p ;
        JPanel panel = new JPanel ();
        panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
        customScrollPane12.setVerticalScrollBarPolicy ( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
        customScrollPane12.setHorizontalScrollBarPolicy ( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        customScrollPane12.setViewportView( panel ) ;
        
        for(    int i=0 ;  i<processMap.size() ; i++   ){
            p = new PlanningPanelChildren() ;
            p.setBounds( 0,0,165,75     ) ;
            p.setPartNo ( ( processMap.get(i)[ 1 ]  ));
            p.setTotalCycleTime ( Double.parseDouble( processMap.get(i)[ 4] ) );
            p.setOrderQty ( Integer.parseInt( processMap.get(i)[ 2 ]    ) );
            p.setPartProcMap (  new JSONArray( processMap.get(i)[ 3 ] )    );
            panel.add( p ) ;
        }
        
        
        JPanel panel2 = new JPanel ();
        panel2.setLayout ( new BoxLayout ( panel2 , BoxLayout.Y_AXIS ) );
        customScrollPane13.setVerticalScrollBarPolicy ( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED );
        customScrollPane13.setHorizontalScrollBarPolicy ( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        customScrollPane13.setViewportView( panel2 ) ;
        
        
        for(    int j=0 ;  j<processMap.size() ; j++   ){
            boolean[] textFieldToggleArr = new boolean[ processes.size() ] ;
            for(    int i=0 ;  i<processes.size() ; i++   ){
                
                boolean processReq = false ;
                JSONArray currentProcessMap = new JSONArray( processMap.get( j )[3] );

                for(   int x =0;  x<currentProcessMap.length () ; x++    ){
                    JSONObject abc = new JSONObject(  currentProcessMap.get( x ).toString() ) ;

                    String prid1, prid2, prname1, prname2;
                        
                    if(   ( Integer.parseInt( abc.get( "prid" ).toString () ) ==  Integer.parseInt( processes.get( i )[1]  ) )   
                            &&     abc.get( "prnm" ).toString ().equals(   processes.get( i )[2]    )          ){
                        
                        prid1 = abc.get( "prid" ).toString () ;
                        prid2 = processes.get( i )[1] ;
                        
                        prname1 = abc.get( "prnm" ).toString () ;
                        prname2 = processes.get( i )[2] ;
                        
                        processReq = true ;
                        x = currentProcessMap.length () ;
                    }else{
                        
                        prid1 = abc.get( "prid" ).toString () ;
                        prid2 = processes.get( i )[1] ;
                        
                        prname1 = abc.get( "prnm" ).toString () ;
                        prname2 = processes.get( i )[2] ;
                        
                        processReq = false ;
                    }
                }

                if( processReq  ){
                    textFieldToggleArr[ i ] = true ;
                }else{
                    textFieldToggleArr[ i ] = false ;
                }
            }
            
            for ( int i = 0  ; i < textFieldToggleArr.length ; i ++ ) {
                if( i == textFieldToggleArr.length-1  )
                    System.out.println ( " "+textFieldToggleArr[i] );
                else
                    System.out.print ( " "+textFieldToggleArr[i] );
            }
            
            AllocHorizontalPanel ahp1 = new AllocHorizontalPanel ( textFieldToggleArr );
            panel2.add( ahp1 ) ;
        }

        

        
        System.out.println ( ""+plan1.size () );   
    }

    public String getPartProcessMachineData(){
        
        JSONArray records3 = null ;
        
        JSONArray machinesList = new JSONArray() ;
        JSONObject machinesData = new JSONObject() ;
        machinesData = planningData.getJSONObject ( "MachineData" ) ;
        machinesList = machinesData.getJSONArray(  "MachinessList"  ) ;
        
        
        String machineAvailabilityCall = "getCycleTimeMachine";
        String ordersAndCycleTime = WebAPITester.prepareWebCall ( machineAvailabilityCall , StaticValues.getHeader () , "" );
        
        if ( ordersAndCycleTime != null &&  ! ordersAndCycleTime.contains( "not found")) {

            JSONObject jObject2 = new JSONObject ( ordersAndCycleTime );
            ordersAndCycleTime = jObject2.get( "data" ).toString() ;
            jObject2 =  new JSONObject ( ordersAndCycleTime );
            JSONArray records = jObject2.getJSONArray ( "records" );
            
            JSONObject emp = null , mch = null;
            
            String[] partProcesMachine = new String[9] ;
            
            for ( int i = 0 ; i < records.length() ; i++ ) {
                    if(  processes.size() == 0   ){

                        emp = records.getJSONObject ( 0 );
                        mch = machinesList.getJSONObject ( 0 );

                        partProcesMachine = new String[9] ;
                        
                        partProcesMachine[0] =  emp.get("part").toString() ;                                        //  part id
                        partProcesMachine[1] =  emp.get("process").toString() ;                                     //  process Id
                        partProcesMachine[2] =  emp.get("process_name").toString() ;                            // process name
                        partProcesMachine[3] =  emp.get("cycle_time").toString() ;                                  //  cycle time
                        partProcesMachine[4] =  emp.get("machine_id").toString() ;                                  // machine id
                        partProcesMachine[5] =  emp.get("order_qty").toString() ;                                   // order qty
                        partProcesMachine[6] =  "" ;                                                                                //  machine nanme         
                        partProcesMachine[7] =  "" ;                                                                                //  machine availability
                        partProcesMachine[8] =  emp.get("pname").toString() ;                                         // part name
                                    

                        processes.add(  partProcesMachine );
                        
                    }else{

                        partProcesMachine = new String[9] ;
                        emp = records.getJSONObject ( i );
                        
                        int x = 0 ;
                        for ( int j = 0 ; j < processes.size() ; j ++ ) {
                             x = 0 ;
                            if( processes.get( j )[1].equals( emp.get("process").toString() ) )  {
                                if( processes.get( j )[4].equals( emp.get("machine_id").toString() ) )  {
                                    x++ ;
                                    j = processes.size() ;
                                }
                            }
                        }
                        
                        if( x == 0  ){
                                
                                partProcesMachine[0] =  emp.get("part").toString() ;
                                partProcesMachine[1] =  emp.get("process").toString() ;
                                partProcesMachine[2] =  emp.get("process_name").toString() ;
                                partProcesMachine[3] =  emp.get("cycle_time").toString() ;
                                partProcesMachine[4] =  emp.get("machine_id").toString() ;                
                                partProcesMachine[5] =  emp.get("order_qty").toString() ;                
                                partProcesMachine[6] =  "" ;                
                                partProcesMachine[7] =  "" ;                   
                                partProcesMachine[8] =  emp.get("pname").toString() ;                
                                
                                //processes.add(  new String[]{  emp.get("processes").toString() , emp.get("process_name").toString() , emp.get("cycle_time").toString() } );
                                processes.add( partProcesMachine  );
                        }
                    }
                 }
                 
                for ( int i = 0 ; i < processes.size() ; i ++ ) {
 
                    String[] partProcesMachine2 = processes.get( i ) ;
                    
                    for ( int j = 0 ; j < machinesList.length() ; j ++ ) {
                        mch = machinesList.getJSONObject ( j );
                        if( partProcesMachine2[ 4 ].equals( mch.get("mid").toString() ) )  {  
                            partProcesMachine2[6] =  mch.get("name").toString() ;
                            partProcesMachine2[7] =  mch.get("max-hours").toString() ;
                            
                            j = machinesList.length();
                        }
                    }
                }
                

                JSONObject jObject3 = new JSONObject ( ordersAndCycleTime );
                records3 = jObject3.getJSONArray ( "records" );
                for ( int i = 0 ; i < records3.length() ; i ++ ) {
                    jObject3 = records3.getJSONObject( i  ) ;
                    
                    for ( int j = 0 ; j < machinesList.length() ; j ++ ) {
                        mch = machinesList.getJSONObject ( j );
                            //if( Integer.parseInt( jObject3.get( "machine_id" ).toString() ) ==  Integer.parseInt(  mch.get("mid").toString() )  )  {  
                            if(  jObject3.get( "machine_id" ).toString().equals(  mch.get("mid").toString() )  )  {  
                                jObject3.put( "mchnm" ,  mch.get("name").toString() );
                                jObject3.put( "shtHrs" ,   mch.get("max-hours").toString()             );
                                //j = machinesList.length();
                                records3.remove ( i ) ;
                                records3.put ( i , jObject3 ) ;
                            }
                    }
                }
        }
        
       //return ordersAndCycleTime ;
       return records3.toString ();
    }
    
    public void getPartProcessMapData(  String apiData ){  

        String ordersAndCycleTime =  apiData ;  

        JSONArray records = null ;
        JSONObject emp = null ;
        
        if ( ordersAndCycleTime != null &&  ! ordersAndCycleTime.contains( "not found")) {

            JSONObject jObject2 = new JSONObject ( ordersAndCycleTime );
            records = jObject2.getJSONArray ( "records" );
            
            emp = null ;
            
            for ( int i = 0 ; i < records.length() ; i++ ) {
                emp = records.getJSONObject ( i );
                
                    if(  processMap.size() == 0   ){
                        processMap.add( new String[]{  emp.get( "part" ).toString() , emp.get( "pname" ).toString()  , emp.get( "order_qty" ).toString()   ,   "" ,""  }  );
                    }else{
                        int x = 0;
                        for ( int j = 0 ; j < processMap.size() ; j ++ ) {
                            x = 0;
                            if(  processMap.get( j )[0].equals( emp.get("part").toString() ) )  {
                                x++ ;
                            }
                        }
                        if( x==0 ){
                            processMap.add( new String[]{  emp.get( "part" ).toString() ,  emp.get( "pname" ).toString()   ,  emp.get( "order_qty" ).toString()   ,  ""   , ""}  );
                        }
                    }                    
            }
        }
        
  //      for ( int i = 0 ; i < processes.size() ; i++ ) {
  //          System.out.println ( ""+processes.get( i)[0]+"     "+processes.get( i)[1]+"          "+processes.get( i)[2]+"          "+processes.get( i)[3]+"          "+processes.get( i)[4]+"          "+processes.get( i)[5]+"          "+processes.get( i)[6]+"          "+processes.get( i)[7] +"          "+processes.get( i)[8]);  
   //     }
        
        
        for ( int i = 0 ; i < processMap.size() ; i++ ) {
            JSONArray processList = new JSONArray() ;
            double totalCycleTime = 0.0 ;
            for ( int j = 0 ; j < records.length() ; j++ ) {
                emp = records.getJSONObject ( j );
                if(   Integer.parseInt( processMap.get( i )[0] )  ==  Integer.parseInt(  emp.get( "part" ).toString() ))      {
                    JSONObject proc = new JSONObject() ;
                    proc.put( "prid"  ,  emp.get( "process" ).toString()   ) ;
                    proc.put( "prnm"  ,  emp.get( "process_name" ).toString()   ) ;
                    proc.put( "cyctm"  ,  emp.get( "cycle_time" ).toString()   ) ;
                    try{
                        totalCycleTime = totalCycleTime +  Double.parseDouble( emp.get( "cycle_time" ).toString() ) ;
                    }catch( NumberFormatException e ){
                        totalCycleTime = totalCycleTime +  (Integer.parseInt( emp.get( "cycle_time" ).toString() )*1.0) ;
                    }
                    //  max available time
                    //  booked time
                    //  available time
                    proc.put( "maxtm"  ,  Double.parseDouble(emp.get( "max-hours" ).toString() )*no_of_days_for_planning  ) ;
                    proc.put( "bkdtm"   ,  "0.0"    ) ;                    
                    proc.put( "avltm"    ,  Double.parseDouble(emp.get( "max-hours" ).toString() )*no_of_days_for_planning     ) ;
                    
                    System.out.print ( "        "+emp.get( "process" ).toString()  +"     "+emp.get( "process_name" ).toString() );
                    
                    processList.put(   proc.toString()   ) ;
                }
                processMap.get(i)[3] =  processList.toString ()   ;
                processMap.get(i)[4] =  totalCycleTime +""   ;
            }
        }  
        
        System.out.println ( "" );


 /*      for ( int i = 0 ; i < processMap.size() ; i++ ) {
        sp = new SampePlan1() ;
            JSONArray processList = new JSONArray() ;
            for ( int j = 0 ; j < processes.size() ; j++ ) {
                
                pd =  new  ProcDetails() ;
                md = new  MachineDetails () ;
                
                emp = records.getJSONObject ( j );
                
                String[] processData = processes.get( j ) ;
                if(   Integer.parseInt( processMap.get( i )[0] )  ==  Integer.parseInt(  processData[0] ))      {
                    
                    pd.setProcessid (  Integer.parseInt(  processData[1]  )  );
                    pd.setProcessname ( processData[2]  );
                    try{
                        pd.setCycletime (  Integer.parseInt(   processData[3]   ) *1.0  );
                    }catch( Exception e  ){
                        pd.setCycletime (  Double.parseDouble(   processData[3]   )  );
                    }
                    pd.setBookedtime ( 0.0 );
                    try{
                        pd.setAvailabletime (   Integer.parseInt(   processData[7]   ) *1.0     );
                    }catch( Exception e  ){
                        try{
                             pd.setAvailabletime (   Double.parseDouble(   processData[7]   )  );
                        }catch( Exception e1  ){
                            pd.setAvailabletime (    0.0    );
                        }
                    }
                    try{
                        pd.setMaxtime (    Integer.parseInt(   processData[7]   ) *1.0      );
                    }catch( Exception e  ){
                        try{
                             pd.setMaxtime (    Double.parseDouble(   processData[7]   )       );
                        }catch( Exception e1  ){
                             pd.setMaxtime (    0.0    );
                        }
                    }
                    
                    try{
                        md.setMachineid ( Integer.parseInt(   processData[4] )  );
                    }catch( Exception e  ){
                        md.setMachineid ( 0  );
                    }
                    md.setMachinename (    processData[4]     );
                    
                }
            }
            
            sp.setProcess ( pd );
            sp.setMachine ( md );
        }*/
       
       
        
        
        for ( int i = 0 ; i < processMap.size() ; i++ ) {
            //System.out.println ( ""+processMap.get( i)[0]+"     "+processMap.get( i)[1]+"          "+processMap.get( i)[2]+"          "+processMap.get( i)[3] );            
            sp.setPartid ( Integer.parseInt( processMap.get( i)[0] ) );
            sp.setPartname (  processMap.get( i)[1]  );
            sp.setReqqty ( Integer.parseInt(processMap.get( i)[2])*1.0  );
            sp.setProdqty ( 0.0 );
            
            ad = new AllocationDetails ();
            ad.setAssigned_qty (  0.0      );
            ad.setBookedtime   (  0.0     );
            ad.setFromdatetime (    null     );
            ad.setTodateime (     null        );
            ad.setShiftid (     0      );
            sp.setAllocation ( ad );
            
            plan1.add ( sp ) ;
        }
        
        System.out.println ( ""+plan1.size() );
    
    }    
    
    public void showPlan(){
        
        
        
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        customScrollPane13 = new components.CustomScrollPane1();
        customScrollPane11 = new components.CustomScrollPane1();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        customScrollPane12 = new components.CustomScrollPane1();
        jButton5 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(null);

        jLabel1.setText("Date");
        add(jLabel1);
        jLabel1.setBounds(6, 0, 480, 16);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Save Plan");
        add(jButton1);
        jButton1.setBounds(607, 460, 100, 40);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Save Data");
        add(jButton2);
        jButton2.setBounds(707, 460, 90, 40);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Reset");
        add(jButton3);
        jButton3.setBounds(797, 460, 90, 40);

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Discard");
        add(jButton4);
        jButton4.setBounds(890, 460, 80, 40);

        jLabel2.setText("Avilability");
        add(jLabel2);
        jLabel2.setBounds(10, 20, 150, 16);

        jLabel3.setText("Allocation");
        add(jLabel3);
        jLabel3.setBounds(10, 200, 110, 16);
        add(customScrollPane13);
        customScrollPane13.setBounds(171, 220, 829, 240);
        add(customScrollPane11);
        customScrollPane11.setBounds(171, 40, 830, 180);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("<html>Processes</html>");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(5, 0, 160, 30);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("<html>Machines</html>");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(5, 31, 160, 30);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("<html>Max Capacity</html>");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(5, 62, 160, 30);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("<html>Booked Capacity</html>");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(5, 93, 160, 30);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setText("<html>Available Capacity</html>");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(5, 124, 160, 30);

        add(jPanel1);
        jPanel1.setBounds(0, 40, 170, 160);
        add(customScrollPane12);
        customScrollPane12.setBounds(0, 220, 171, 240);

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Show Plan");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        add(jButton5);
        jButton5.setBounds(507, 460, 100, 40);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private components.CustomScrollPane1 customScrollPane11;
    private components.CustomScrollPane1 customScrollPane12;
    private components.CustomScrollPane1 customScrollPane13;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

class Synchronizer implements AdjustmentListener
{
    JScrollBar v1, h1, v2, h2;
  
    public Synchronizer(JScrollPane sp1, JScrollPane sp2)
    {
        v1 = sp1.getVerticalScrollBar();
        h1 = sp1.getHorizontalScrollBar();
        v2 = sp2.getVerticalScrollBar();
        h2 = sp2.getHorizontalScrollBar();
    }
  
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        JScrollBar scrollBar = (JScrollBar)e.getSource();
        int value = scrollBar.getValue();
        JScrollBar target = null;
  
        if(scrollBar == v1)
            target = v2;
        if(scrollBar == h1)
            target = h2;
        if(scrollBar == v2)
            target = v1;
        if(scrollBar == h2)
            target = h1;
  
        target.setValue(value);
    }
}

/*
 * 
 *         JComboBox orderCombo = new JComboBox() ;
        orderCombo.addItem ( new String("1 Order"));
        orderCombo.addItem ( new String("2 Order"));
        orderCombo.addItem ( new String("3 Order"));
        
        JComboBox partCombo1 = new JComboBox() ;
        orderCombo.addItem ( new String("1 Part"));
        orderCombo.addItem ( new String("2 Part"));
        
        JComboBox partCombo2 = new JComboBox() ;
        orderCombo.addItem ( new String("2 Part"));
        orderCombo.addItem ( new String("3 Part"));
        
       
        firstColumnAlloc = new Vector<JComboBox> () ;
        firstColumnAlloc.add(  orderCombo   ) ;
        firstColumnAlloc.add(  partCombo1  ) ;
      //  firstColumnAlloc.add(  new String("200")  ) ;
        alloc.add ( firstColumnAlloc ) ;

        firstColumnAlloc = new Vector<JComboBox> () ;
        firstColumnAlloc.add(  orderCombo   ) ;
        firstColumnAlloc.add(  partCombo2  ) ;
     //   firstColumnAlloc.add(  " 100"   ) ;
        alloc.add ( firstColumnAlloc ) ;

        firstColumnAlloc = new Vector<JComboBox> () ;
        firstColumnAlloc.add(  orderCombo   ) ;
        firstColumnAlloc.add(  partCombo2  ) ;
  //      firstColumnAlloc.add(  " 150"   ) ;
        alloc.add ( firstColumnAlloc ) ;
        
  //      jTable2.setModel(  new DefaultTableModel (alloc, firstColumnAllocColumn )     ) ;
  * 
  * 
  * 
  * 
  * 
  *         firstColumnAvailColumn = new Vector<String> () ;
        firstColumnAvail = new Vector<String> () ;
        
        firstColumnAvailColumn.add(  " 1 Process"   ) ;
        firstColumnAvailColumn.add(  " 1 Process"   ) ;
        firstColumnAvailColumn.add(  " 2 Process"   ) ;
        firstColumnAvailColumn.add(  " 3 Process"   ) ;
        firstColumnAvailColumn.add(  " 4 Process"   ) ;
        firstColumnAvailColumn.add(  " 5 Process"   ) ;
        firstColumnAvailColumn.add(  " 6 Process"   ) ;
        firstColumnAvailColumn.add(  " 7 Process"   ) ;
        firstColumnAvailColumn.add(  " 8 Process"   ) ;
        firstColumnAvailColumn.add(  " 9 Process"   ) ;
        
        for(  int i=0; i<processes.size(); i++      ){
            firstColumnAvailColumn.add(  processes.get(i)[ 2 ]   ) ;
            firstColumnAvail.add(  processes.get(i)[ 6 ]  ) ;
            
            
        }
        avail.add( firstColumnAvail);
        
       firstColumnAvail.add(  " 1 Machine"   ) ;
        firstColumnAvail.add(  " 2 Machine"   ) ;
        firstColumnAvail.add(  " 3 Machine"   ) ;
        firstColumnAvail.add(  " 4 Machine"   ) ;
        firstColumnAvail.add(  " 5 Machine"   ) ;
        firstColumnAvail.add(  " 6 Machine"   ) ;
        firstColumnAvail.add(  " 7 Machine"   ) ;
        firstColumnAvail.add(  " 8 Machine"   ) ;
        firstColumnAvail.add(  " 9 Machine"   ) ;
        firstColumnAvail.add(  " 10 Machine"   ) ;
        
        
        
        firstColumnAvail = new Vector<String> () ;
        firstColumnAvail.add(  ""   ) ;
        firstColumnAvail.add(  ""   ) ;
        firstColumnAvail.add(  ""   ) ;
        firstColumnAvail.add(  ""   ) ;
        firstColumnAvail.add(  ""   ) ;
        firstColumnAvail.add(  ""   ) ;
        avail.add( firstColumnAvail);
        
        firstColumnAvail = new Vector<String> () ;
        firstColumnAvail.add ( "" );
        firstColumnAvail.add ( "" );
        firstColumnAvail.add ( "" );
        firstColumnAvail.add ( "" );
        firstColumnAvail.add ( "" );
        firstColumnAvail.add(  ""   ) ;
                                                
        avail.add( firstColumnAvail);
        
        firstColumnAvail = new Vector<String> () ;
        firstColumnAvail.add(  ""   ) ;
        firstColumnAvail.add ( "" );
        firstColumnAvail.add ( "" );
        firstColumnAvail.add ( "" );
        firstColumnAvail.add ( "" );
        firstColumnAvail.add ( "" );
        avail.add( firstColumnAvail);
        
        availabilityTable.setModel(  new DefaultTableModel (avail, firstColumnAvailColumn  ) ) ;
 * 
 * 
 */

