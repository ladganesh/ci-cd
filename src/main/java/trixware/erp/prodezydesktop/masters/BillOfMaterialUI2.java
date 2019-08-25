/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import trixware.erp.prodezydesktop.Model.BOMModel;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.UOMDR;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.RawMaterialDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONObject;
import org.json.JSONArray;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class BillOfMaterialUI2 extends javax.swing.JPanel {

    ArrayList<String> RMIDS;

    ArrayList<ProductDR> products = null;
    public ArrayList<RawMaterialDR> rawMaterials = null;
    ArrayList<UOMDR> UOMList = null;
    UOMDR uom = null;
    ProductDR prdr = null; 

    ArrayList< RMQtyPanel> rmBomList = null;
    ArrayList<BOMModel> dataList = null;
    AddRuleFrame panel = null;

    String resource = "", resourceType = "";

    /**
     * Creates new form BillOfMaterialUI
     */
    public BillOfMaterialUI2 ( int FG_ID ) {
        initComponents ();
        SelectedFG_ID = FG_ID;
        resourceType = "Masters";
        resource = "BillOfMaterialUI2";
        jButton3.setVisible ( false );
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane2.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
 
    }

    public BillOfMaterialUI2 () {
        initComponents ();
        try {
            loadContent ();
            //loadBomForSingleFG ();
            loadSingleFGBom ();
            jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane2.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));


        } catch ( Exception e ) {

        }

    }

    public void loadContent_OLD () {
        ResultSet result = null;

        String queryOne = "SELECT FG_ID, PART_NAME FROM finished_goods";

        ResultSet rs = null;

        try {
            rs = DB_Operations.executeSingle ( queryOne );
            //  products = new ArrayList<HashMap<String, String>> ();
            products = new ArrayList<ProductDR> ();
            while ( rs.next () ) {
                //   entity =  new  HashMap<String, String>();
                prdr = new ProductDR ();
                //    entity.put ( rs.getString(1) /*  String.valueOf( rs.getInt(1) ) */, rs.getString(2)) ;
                prdr.FG_ID = Integer.parseInt ( rs.getString ( 1 ) );
                prdr.FG_PART_NO = rs.getString ( 2 );
                products.add ( prdr );
                jComboBox5.addItem ( rs.getString ( 2 ) );
            }

            rs.close ();

            jComboBox5.addActionListener ( FGComboActionListener );
        } catch ( Exception e ) {
            System.out.println ( " Error 1 " + e.getMessage () );
            try {
                rs.close ();
            } catch ( SQLException ex ) {

            }
        }

        try {
            rs = DB_Operations.getMasters ( "uom" );

            UOMList = new ArrayList<UOMDR> ();
            while ( rs.next () ) {
                uom = new UOMDR ();
                uom.UOM_ID = Integer.parseInt ( rs.getString ( 1 ) );
                uom.UOM_NAME = rs.getString ( 2 );
                UOMList.add ( uom );
            }
            rs.close ();
        } catch ( Exception e ) {
            System.out.println ( " Error 1 " + e.getMessage () );
            try {
                rs.close ();
            } catch ( SQLException ex ) {

            }
        }

//        try {
//            result = DB_Operations.getMasters ( "raw_material" );
//            panel = new AddRuleFrame ( result , UOMList );
//            panel.setBounds ( 10 , 85 , 400 , 400 );
//            add ( panel );
//            result.close ();
//        } catch ( Exception e ) {
//            try {
//                result.close ();
//            } catch ( SQLException ex ) {
//
//            }
//        }

    }

    String addEmpAPICall, result2;

    HashMap<String , Object> map = null;
    JSONObject jObject = null;
    Iterator<?> keys = null;
    JSONObject st = null;
    JSONArray records = null;
    JSONObject emp = null;

    public void loadContent () {

        if ( jComboBox5.getItemCount () == 0 ) {
            //result = DB_Operations.getMasters ( "raw_material_type" );

            addEmpAPICall = "finishedgoods";
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            
            if(  !  result2.contains ( "not found" )){
                
                    map = new HashMap<String , Object> ();
                    jObject = new JSONObject ( result2 );
                    keys = jObject.keys ();

                    while ( keys.hasNext () ) {
                        String key = ( String ) keys.next ();
                        Object value = jObject.get ( key );
                        map.put ( key , value );
                    }

                    st = ( JSONObject ) map.get ( "data" );
                    records = st.getJSONArray ( "records" );

                    products = new ArrayList<ProductDR> ();
                    for ( int i = 0 ; i < records.length () ; i ++ ) {

                        emp = records.getJSONObject ( i );
                        jComboBox5.addItem ( emp.get ( "FG_PART_NO" ).toString () );
                        products.add ( new ProductDR ( Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) , emp.get ( "FG_PART_NO" ).toString () ,  emp.get ( "FG_UOM_ID" ).toString ()  ) );
                    }

                    jLabel3.setText ( StaticValues.getUOMName(  Integer.parseInt (  products.get ( jComboBox5.getSelectedIndex () ).UOM  ) ) );
                    jComboBox5.addActionListener ( productComboAction );
            }
        }

//        addEmpAPICall = "unitmeas";
//        result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
//        HashMap<String , Object> map = new HashMap<String , Object> ();
//        JSONObject jObject = new JSONObject ( result2 );
//        Iterator<?> keys = jObject.keys ();
//
//        while ( keys.hasNext () ) {
//            String key = ( String ) keys.next ();
//            Object value = jObject.get ( key );
//            map.put ( key , value );
//        }
//
//        st = ( JSONObject ) map.get ( "data" );
//        records = st.getJSONArray ( "records" );
//
//        products = new ArrayList<ProductDR> ();
//        for ( int i = 0 ; i < records.length () ; i ++ ) {
//
//            emp = records.getJSONObject ( i );
//            uom = new UOMDR ();
//            uom.UOM_ID = Integer.parseInt ( emp.get ( "UOM_ID" ).toString () );
//            uom.UOM_NAME = emp.get ( "UOM" ).toString ();
//            UOMList.add ( uom );
//        }

        try {

            addEmpAPICall = "rawmaterials";
            result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
            
            if(  ! result2.contains(  "not found")   ){
                //result = DB_Operations.getMasters ( "raw_material" );
                panel = new AddRuleFrame ( result2 , UOMList );
                panel.setBounds ( 10 , 85 , 400 , 400 );
                add ( panel );
            }

        } catch ( Exception e ) {
            System.out.println ( ""+e.getMessage () );
        }

//
//              try {
//            rs = DB_Operations.getMasters ( "uom" );
//
//            UOMList = new ArrayList<UOMDR> ();
//            while ( rs.next () ) {
//                uom = new UOMDR ();
//                uom.UOM_ID = Integer.parseInt ( rs.getString ( 1 ) );
//                uom.UOM_NAME = rs.getString ( 2 );
//                UOMList.add ( uom );
//            }
//            rs.close ();
//        } catch ( Exception e ) {
//            System.out.println ( " Error 1 " + e.getMessage () );
//            try {
//                rs.close ();
//            } catch ( SQLException ex ) {
//
//            }
//        }
//
//        try {
//            result = DB_Operations.getMasters ( "raw_material" );
//            panel = new AddRuleFrame ( result , UOMList );
//            panel.setBounds ( 10 , 85 , 400 , 400 );
//            add ( panel );
//            result.close ();
//        } catch ( Exception e ) {
//            try {
//                result.close ();
//            } catch ( SQLException ex ) {
//
//            }
//        }
    }

    
     ActionListener productComboAction = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
            jLabel3.setText(  StaticValues.getUOMName(  Integer.parseInt (  products.get ( jComboBox5.getSelectedIndex () ).UOM ) ) );
            loadSingleFGBom ();
        }
    };
    
    
    public void loadBomForSingleFG () {
        ResultSet rs = null;
        try {
            rs = DB_Operations.getSingleMasters ( "bomsingleFG" , products.get ( jComboBox5.getSelectedIndex () ).FG_ID );
            if ( rs != null ) {
                jTable2.setModel ( buildTableModel ( rs ) );
                jTable2.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                jTable2.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                jTable2.getColumnModel ().getColumn ( 2 ).setMinWidth ( 0 );
                jTable2.getColumnModel ().getColumn ( 2 ).setMaxWidth ( 0 );
            }
            rs.close ();
        } catch ( Exception e ) {
            try {
                rs.close ();
            } catch ( SQLException ex ) {

            }
        }
    }

    public void loadSingleFGBom () {
       
                SelectedFG_ID = products.get ( jComboBox5.getSelectedIndex () ).FG_ID ;
                String addEmpAPICall = "billofmaterials?FG_ID="+products.get ( jComboBox5.getSelectedIndex () ).FG_ID;
                String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");

                if ( result2!= null  && ! result2.contains ( "not found")) {
                    jTable2.setModel ( buildTableModelfromJSON (result2 ) );
                    jTable2.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                    jTable2.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                    jTable2.getColumnModel ().getColumn ( 2 ).setMinWidth ( 0 );
                    jTable2.getColumnModel ().getColumn ( 2 ).setMaxWidth ( 0 );
                    jTable2.getColumnModel ().getColumn ( 4 ).setMinWidth ( 0 );
                    jTable2.getColumnModel ().getColumn ( 4 ).setMaxWidth ( 0 );
                   
                    
                }else{

                    jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
                }


    }
    
    public  boolean fgmatched = false ;
    
        public  DefaultTableModel buildTableModelfromJSON ( String  employeeJSONList )
            {

            Vector<String> columnNames = new Vector<String> ();
            Vector<String> columnNames2 = new Vector<String> ();
            int columnCount = 0;
            Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        
            HashMap<String, Object> map = new HashMap<String, Object>();
            JSONObject jObject = new JSONObject( employeeJSONList );
            Iterator<?> keys = jObject.keys();

            while( keys.hasNext() ){
                String key = (String)keys.next();
                Object value = jObject.get ( key ) ; 
                map.put(key, value);
            }
            
            JSONObject st = (JSONObject) map.get ( "data" );
            JSONArray records = st.getJSONArray ( "records");
            
            JSONObject emp = null;
            
                columnNames = new Vector<String> ();
                columnNames2 = new Vector<String> ();
                columnNames.add( "BOM_ID"  );columnNames.add( "FG_ID"  );columnNames.add( "FG_ID"  );columnNames.add( "RM_ID"  );columnNames.add( "RM_ID"  );columnNames.add( "TOTAL_WT"  );
                columnNames2.add( "BOM_ID"  );columnNames2.add( "Part Code"  );columnNames2.add( "Part Code"  );columnNames2.add( "Raw Material Code"  );columnNames2.add( "RM Code"  );columnNames2.add( "Total Wt / Count"  );
           

        // data of the table
        for( int i=0; i<records.length (); i++ ){
        Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 0 ; columnIndex <columnNames.size () ; columnIndex ++ ) {
                emp = records.getJSONObject ( i);
                
                
                fgmatched = false ;
                try{
                    if( products.get ( jComboBox5.getSelectedIndex () ).FG_ID ==  Integer.parseInt( emp.get ( columnNames.get ( 1 ) ).toString()  )   ){
                        fgmatched = true ;
                    }
                }catch( NumberFormatException e ){
                    System.out.println ( ""+e.getMessage () );  
                }
                
                try{
                
                if( fgmatched ){
                   if( columnIndex == 1){
                        for( int j=0; j<products.size ();j++){
                                if( products.get ( j).FG_ID == Integer.parseInt( emp.get ( columnNames.get ( columnIndex ) ).toString() )){
                                    vector.add ( products.get ( j).FG_PART_NO );
                                }
                        }
                    }else if( columnIndex == 3){
                        for( int k=0; k<rawMaterials.size ();k++){
                            if( rawMaterials.get ( k).RM_ID == Integer.parseInt( emp.get ( columnNames.get ( columnIndex ) ).toString() )){
                                vector.add ( rawMaterials.get ( k).RM_CLASS );
                            }
                        }
                    } else{
                             if( columnIndex == 0 || columnIndex == 5 || columnIndex == 2|| columnIndex == 4)
                                vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                    }
                }
                }catch(Exception e){
                    System.out.println ( ""+e.getMessage () );
                }
            }
             if( fgmatched ){
                data.add ( vector );
             }
                
                
                
//                if( columnIndex == 0 ){
//                    vector.add ( emp.get ( columnNames.get ( columnIndex ) )  );
//                }
//                vector.add ( emp.get ( columnNames.get ( columnIndex ) )  );
//            }
//            data.add ( vector );
        }
        return new DefaultTableModel ( data , columnNames2 );
    }
    
    
    public void loadSingleFGBom_OLD () {
        ResultSet rs = null;
        try {
            rs = DB_Operations.getSingleMasters ( "bomsingleFG" , products.get ( jComboBox5.getSelectedIndex () ).FG_ID );

            if ( rs != null ) {
                jTable2.setModel ( buildTableModel ( rs ) );
                jTable2.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
                jTable2.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
                jTable2.getColumnModel ().getColumn ( 1 ).setMinWidth ( 0 );
                jTable2.getColumnModel ().getColumn ( 1 ).setMaxWidth ( 0 );
                jTable2.getColumnModel ().getColumn ( 2 ).setMinWidth ( 0 );
                jTable2.getColumnModel ().getColumn ( 2 ).setMaxWidth ( 0 );
            }
            rs.close ();
        } catch ( SQLException e ) {
            try {
                rs.close ();
            } catch ( SQLException ex ) {

            }
        }

    }
    

    public static DefaultTableModel buildTableModel ( ResultSet rs )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        int columnCount = metaData.getColumnCount ();
        for ( int column = 1 ; column <= 4 ; column ++ ) {
            columnNames.add ( metaData.getColumnName ( column ) );
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        while ( rs.next () ) {
            Vector<Object> vector = new Vector<Object> ();
            for ( int columnIndex = 1 ; columnIndex <= 4 ; columnIndex ++ ) {
                vector.add ( rs.getObject ( columnIndex ) );
            }
            data.add ( vector );
        }

        return new DefaultTableModel ( data , columnNames );

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

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

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(393, 474));
        setPreferredSize(new java.awt.Dimension(837, 488));
        setLayout(null);

        jLabel1.setText("BOM for Part Code\\No");
        add(jLabel1);
        jLabel1.setBounds(10, 0, 130, 20);
        add(jLabel2);
        jLabel2.setBounds(150, 10, 210, 0);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Save");
        jButton2.setNextFocusableComponent(jButton3);
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(450, 455, 70, 32);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Update");
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(520, 455, 80, 32);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("Close");
        jButton4.setNextFocusableComponent(jTable2);
        jButton4.setOpaque(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(600, 455, 70, 32);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        add(jScrollPane2);
        jScrollPane2.setBounds(450, 20, 370, 420);

        jComboBox5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        add(jComboBox5);
        jComboBox5.setBounds(10, 20, 350, 30);

        jLabel3.setText("jLabel3");
        add(jLabel3);
        jLabel3.setBounds(380, 30, 41, 20);

        jLabel4.setText("Unit");
        add(jLabel4);
        jLabel4.setBounds(380, 10, 50, 30);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 13)); // NOI18N
        jButton1.setText("Reset");
        jButton1.setOpaque(false);
        jButton1.setPreferredSize(new java.awt.Dimension(62, 32));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(670, 455, 70, 32);
    }// </editor-fold>//GEN-END:initComponents


    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        SelectedFG_ID = products.get ( jComboBox5.getSelectedIndex () ).FG_ID;
        BOMModel bom;
        RMQtyPanel bomPanel;

        SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm" );

        for ( int i = 0 ; i < rmBomList.size () ; i ++ ) {
            bomPanel = new RMQtyPanel ();
            bomPanel = rmBomList.get ( i );
            // System.out.println ( ""+ bomPanel.getRmid () + ", " + bomPanel.getQty () + ", " + bomPanel.getRMName ()+" , "+bomPanel.getUomList ()  );

            if ( bomPanel.getQty () > 0.0 ) {

                bom = new BOMModel ();
                bom.setFgid ( SelectedFG_ID );
                bom.setRmid ( bomPanel.getRmid () );
                bom.setQty ( bomPanel.getQty () );
                bom.setUom ( Integer.parseInt( products.get ( jComboBox5.getSelectedIndex () ).UOM ));

                String queryInsert = "insert into BillOfMaterial ( FG_ID, RM_ID,TOTAL_WT, UOMID, CREATED_ON, EDITED_BY, EDITED_ON ) values ( " + bom.getFgid () + ", " + bom.getRmid () + "," + bom.getQty () + ", " + bom.getUom () + ", '" + sdf.format ( Calendar.getInstance ().getTime () ) + "', '" + Proman_User.getUsername () + "' , '" + sdf.format ( Calendar.getInstance ().getTime () ) + "'  )";
                DB_Operations.executeInsertRandom ( queryInsert );

                try {
                    String addEmpAPICall = "bomadd?FG_ID=" + URLEncoder.encode ( bom.getFgid () + "" , "UTF-8" ) + "&RM_ID=" + URLEncoder.encode ( bom.getRmid () + "" , "UTF-8" ) + "&TOTAL_WT=" + URLEncoder.encode ( (int) bom.getQty () + "" , "UTF-8" ) + "&UOMID=" + URLEncoder.encode ( bom.getUom () + "" , "UTF-8" ) + "&CREATED_ON=" + URLEncoder.encode ( sdf.format ( Calendar.getInstance ().getTime () ) , "UTF-8" ) + "&EDITED_BY=" + URLEncoder.encode ( Proman_User.getEID () + "" , "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( sdf.format ( Calendar.getInstance ().getTime () ) , "UTF-8" );
                    String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    //JOptionPane.showMessageDialog ( null , result2 );
                    
                    if( result2.contains ( "success") ){
                        JOptionPane.showMessageDialog ( null , "Created a new enrty in Bill of Material master against part :"+ products.get ( jComboBox5.getSelectedIndex () ).FG_PART_NO  );
                        loadSingleFGBom();
                    }
                    
                } catch ( UnsupportedEncodingException e ) {

                }

            }
        }

        resetFields ();

    }//GEN-LAST:event_jButton2ActionPerformed

    int selectedBomID_ForPart = 0;
    int selectedRMID_ForPart = 0;
    
    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:

        ArrayList<String> values = new ArrayList<String> ();

        DefaultTableModel model = ( DefaultTableModel ) jTable2.getModel ();
        int columnCount = 0;
        int selectedRowIndex = jTable2.getSelectedRow ();
        
        selectedBomID_ForPart = Integer.parseInt( model.getValueAt ( selectedRowIndex, 0).toString() ) ;
        selectedRMID_ForPart = Integer.parseInt( model.getValueAt ( selectedRowIndex, 4).toString() ) ;
        
      //  JOptionPane.showMessageDialog ( null , "" + selectedBomID_ForPart + " " + SelectedFG_ID + " " + selectedRMID_ForPart );
        
        jButton2.setEnabled (  false );
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        BOMModel bom;
        RMQtyPanel bomPanel;

        SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm" );

        for ( int i = 0 ; i < rmBomList.size () ; i ++ ) {
            bomPanel = new RMQtyPanel ();
            bomPanel = rmBomList.get ( i );
            // System.out.println ( ""+ bomPanel.getRmid () + ", " + bomPanel.getQty () + ", " + bomPanel.getRMName ()+" , "+bomPanel.getUomList ()  );

            if ( bomPanel.getQty () > 0.0 ) {

                bom = new BOMModel ();
                bom.setQty ( bomPanel.getQty () );
                
                if(  selectedRMID_ForPart ==  rmBomList.get ( i ).rmid) 
                {
                    
                    try {
                       String  addEmpAPICall = "bomupdate?BOM_ID="+selectedBomID_ForPart+"FG_ID=" + SelectedFG_ID + "&RM_ID=" + selectedRMID_ForPart + "&TOTAL_WT=" + URLEncoder.encode ( (int) bom.getQty () + "" , "UTF-8" )  ;
                        String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );


                        if( result2.contains ( "success") ){
                        //    JOptionPane.showMessageDialog ( null , "Created a new enrty in Bill of Material master against part :"+ products.get ( jComboBox5.getSelectedIndex () ).FG_NAME  );
                            JOptionPane.showMessageDialog ( null , result2 );
                            loadSingleFGBom();
                        }else{
                            JOptionPane.showMessageDialog ( null , "Failed to update Part - Raw Material composition. Please try again (Error Code :"+result2+")" );
                        }

                    }catch ( UnsupportedEncodingException e ) {
                        JOptionPane.showMessageDialog ( null , "Failed to update Part - Raw Material composition. Please try again (Error Code :"+e.getMessage ()+")" );
                    }catch ( Exception e ) {
                        JOptionPane.showMessageDialog ( null , "Failed to update Part - Raw Material composition. Please try again (Error Code :"+e.getMessage ()+")" );
                    }
                }

            }
        }
        
       // JOptionPane.showMessageDialog ( null , "" + selectedBomID_ForPart + " " + SelectedFG_ID + " " + selectedRMID_ForPart );
        loadContent ();
        resetFields ();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        FinishedGoodMaster_old.bom.setVisible ( false );
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        jTable2.clearSelection ();
             
        try {
            loadContent ();
            loadSingleFGBom ();
        } catch ( Exception e ) {

        }
        
        jButton2.setEnabled (  true );
    }//GEN-LAST:event_jButton1MouseClicked

    ActionListener FGComboActionListener = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {
            int newSelFG = products.get ( jComboBox5.getSelectedIndex () ).FG_ID;
            if ( newSelFG != SelectedFG_ID ) {
                loadSingleFGBom ();
            }
        }
    };

    public void resetFields () {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables

    public static int selectedBOMId = -1;
    public static int selectedRMID = -1;
    public int SelectedFG_ID = -1;

    class AddRuleFrame extends JPanel {

        private JPanel panel;

        private JScrollPane scroll;
        private JButton btnAddType;

        ArrayList<UOMDR> UOMList = null;
        UOMDR uom = null;

        
        
//    public ArrayList<RMQtyPanel> rmBomList = null ;
        public AddRuleFrame ( String rs , ArrayList<UOMDR> uomList ) {
            // getContentPane().setLayout(new BorderLayout());       

            setLayout ( new BorderLayout () );

            rawMaterials = new ArrayList<RawMaterialDR>() ;
            
            panel = new JPanel ();
            panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
            scroll = new JScrollPane ( panel ,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );

            add ( scroll , BorderLayout.CENTER );
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        


            setVisible ( true );

            RMQtyPanel pane = null;

            try {

                jObject = new JSONObject ( result2 );
                keys = jObject.keys ();

                while ( keys.hasNext () ) {
                    String key = ( String ) keys.next ();
                    Object value = jObject.get ( key );
                    map.put ( key , value );
                }

                st = ( JSONObject ) map.get ( "data" );
                records = st.getJSONArray ( "records" );

                //products = new ArrayList<ProductDR> ();

                rmBomList = new ArrayList<RMQtyPanel> ();

                for ( int i = 0 ; i < records.length () ; i ++ ) {

                    emp = records.getJSONObject ( i );

                    String dim = "" ;
                    if(   !  emp.get ( "length" ).toString ().equals ( "null") &&  ! emp.get ( "width" ).toString ().equals ( "null")     &&      ! emp.get ( "thickness" ).toString ().equals ( "null") ){
                    dim = emp.get ( "length" ).toString ()+"x"+
                                        emp.get ( "width" ).toString ()+"x"+
                                        emp.get ( "thickness" ).toString () ;
                    }
                    
                    pane = new RMQtyPanel ();
                    pane.setRmid ( Integer.parseInt ( emp.get ( "RM_ID" ).toString () ) );
                    pane.setRMName ( "<html>"+ emp.get ( "RM_CLASS" ).toString ()+"<br>"+dim+"</html>" );
                    pane.setRmuom ( Integer.parseInt ( emp.get ( "RMM_UOM_ID" ).toString () ) );
                    pane.SetQty ( 0.0 );

                    rawMaterials.add(  new RawMaterialDR ( Integer.parseInt( emp.get ( "RM_ID" ).toString () ), emp.get ( "RM_CLASS" ).toString ()  )    )  ;
                            
                    
                    
                    
                    panel.add ( pane );
                    panel.revalidate ();
                    //   System.out.println ( pane.getRmid ()+" "+pane.getRMName ());
                    rmBomList.add ( pane );
                }
                System.out.println ( "" + rmBomList.size () );

            } catch ( IndexOutOfBoundsException e ) {
                System.out.println ( " Error 3 " + e.getMessage () );
            }

        }

        
//        public AddRuleFrame_OLD (  ) {
//            
//        }
//        
//        public AddRuleFrame_OLD ( ResultSet rs , ArrayList<UOMDR> uomList ) {
//            // getContentPane().setLayout(new BorderLayout());       
//
//            setLayout ( new BorderLayout () );
//
//            panel = new JPanel ();
//            panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
//            scroll = new JScrollPane ( panel ,
//                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
//                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
//
//            add ( scroll , BorderLayout.CENTER );
//
//            setVisible ( true );
//
//            RMQtyPanel pane = null;
//
//            try {
//
//                rmBomList = new ArrayList<RMQtyPanel> ();
//
//                while ( rs.next () ) {
//                    pane = new RMQtyPanel ();
//                    pane.setRmid ( Integer.parseInt ( rs.getString ( "RM_ID" ) ) );
//                    pane.setRMName ( rs.getString ( "RM_NAME" ) );
//                    pane.SetQty ( 0.0 );
//                    UOMList = uomList;
////                for ( int i = 0 ; i < UOMList.size() ; i ++ ) {
////                    uom = UOMList.get(i) ;
////                    pane.jComboBox1.addItem ( uom.UOM_NAME );
////                }
//                    pane.setUomList ( uomList );
//                    panel.add ( pane );
//                    panel.revalidate ();
//                    //   System.out.println ( pane.getRmid ()+" "+pane.getRMName ());
//                    rmBomList.add ( pane );
//                }
//                System.out.println ( "" + rmBomList.size () );
//                rs.close ();
//            } catch ( SQLException e ) {
//                System.out.println ( " Error 2 " + e.getMessage () );
//            } catch ( IndexOutOfBoundsException e ) {
//                System.out.println ( " Error 3 " + e.getMessage () );
//            }
//
//            return new AddRuleFrame () ;
//        }

    }
}
