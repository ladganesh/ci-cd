/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples.reports;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.FGPARTcode;
import trixware.erp.prodezydesktop.Model.ProductDR;
import trixware.erp.prodezydesktop.Model.Proman_User;
import trixware.erp.prodezydesktop.Model.RawMaterialDR;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author dell
 */
public class RMandPartCodeform extends javax.swing.JPanel {

    ArrayList<RawMaterialDR> rm = null;
    ArrayList<ProductDR> products = null;
    ArrayList<FGPARTcode> fg = null;
    private JScrollPane scroll;

    ArrayList<RMFGMap> data = new ArrayList<RMFGMap> ();
    ArrayList<RMFGMap> data2 = new ArrayList<RMFGMap> ();
    ArrayList<ArrayList<JTextField>> textFieldList = null ;
    
    JPanel jp1;

    //static int m=1,n=1,m1=1,fetch=1;
    static int n = 0, fetch = 1, m1 = 0;
    JTextField[][] jtf = new JTextField[ 30 ][ 30 ];

    ArrayList<Integer[]> addvalues = new ArrayList<> ();
    ArrayList<Integer[]> updatedvalues = new ArrayList<> ();
    JTextField[][] check = new JTextField[ 30 ][ 30 ];
    JButton[] jb = new JButton[ 30 ];
    Vector<String> columnNames = null;
    int SelectedID;

    //JButton jb1 =new JButton();
    /**
     * Creates new form RMandPartCodeform
     */
    public Thread t = new Thread () {

        public void run () {

            try {
                String addEmpAPICall = "billofmaterials";
                String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                HashMap<String , Object> map = new HashMap<String , Object> ();
                JSONObject jObject = new JSONObject ( result2 );
                Iterator<?> keys = jObject.keys ();

                while ( keys.hasNext () ) {
                    String key = ( String ) keys.next ();
                    Object value = jObject.get ( key );
                    map.put ( key , value );
                }

                JSONObject st = ( JSONObject ) map.get ( "data" );
                JSONArray records = st.getJSONArray ( "records" );
                JSONObject emp = null;

                columnNames = new Vector<String> ();
                columnNames.add ( "BOM_ID" );
                columnNames.add ( "FG_ID" );
                columnNames.add ( "RM_ID" );
                columnNames.add ( "TOTAL_WT" );
                data = new ArrayList<RMFGMap> ();
                data2 = new ArrayList<RMFGMap> ();
                for ( int i = 0 ; i < records.length () ; i ++ ) {
                    emp = records.getJSONObject ( i );
                    data.add ( new RMFGMap ( Integer.parseInt ( emp.get ( "BOM_ID" ).toString () ) , Integer.parseInt ( emp.get ( "RM_ID" ).toString () ) , Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) , Integer.parseInt ( emp.get ( "TOTAL_WT" ).toString () )  ) );
                 //   data2.add ( new RMFGMap ( Integer.parseInt ( emp.get ( "BOM_ID" ).toString () ) , Integer.parseInt ( emp.get ( "RM_ID" ).toString () ) , Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) , Integer.parseInt ( emp.get ( "TOTAL_WT" ).toString () )  ) );
                }

                System.out.println ( "total existing records "+data.size() );
//                for ( int x = 0 ; x < data.size () ; x ++ ) {
//                    for(  int rowCounter = 0; rowCounter < rm.size()    ; rowCounter++  ){
//                        for(  int colCounter = 0; colCounter  < products.size()  ;  colCounter++  ){
//
//                                if ( data.get ( x ).rmid != rm.get(rowCounter).RM_ID && data.get ( x ).fgid != products.get ( colCounter ).FG_ID ) {
//                                    data2.add ( new RMFGMap ( data.get(x).bomid , data.get(x).rmid , data.get(x).fgid , data.get ( x).count ));
//                                
//                                }else if ( data.get ( x ).rmid == rm.get(rowCounter).RM_ID && data.get ( x ).fgid == products.get ( colCounter ).FG_ID ) {
//                                    data2.add ( new RMFGMap ( 0 , rm.get(rowCounter).RM_ID , products.get ( colCounter ).FG_ID , 0  ) );
//                                }
//                        }
//                    }
//                }
                
            } catch ( Exception e ) {
                System.out.println ( "" + e.getMessage () );
            }

            textFieldList  = new ArrayList<ArrayList<JTextField>>(); 
            for ( int i = 0 ; i < rm.size () ; i ++ ) {

                int k1 = 0;
                int l;
                l = 1;

                JLabel rmName = new JLabel ();
                rmName.setText ( rm.get ( i ).RM_CLASS );
                rmName.setBounds ( 0 , ( 40 * i ) + 40 , 140 , 30 );
                add ( rmName );

                ArrayList<JTextField> valueFields = new ArrayList<JTextField> ()  ;
                
                for ( int j = 0 ; j < products.size () ; j ++ ) {

                    k1 = k1 + 65;
                    if ( i == 0 ) {

                        JLabel prodnm = new JLabel ();
                        prodnm.setText ( products.get ( j ).FG_PART_NO );
                        prodnm.setBounds ( k1 , 5 , 50 , 30 );
                        jp1.add ( prodnm );

                    }

                    //else {
                    JTextField abc = new JTextField() ;
                                    abc.setBounds ( k1 , ( i * 40 ) + 30 , 50 , 30 );
                                    abc.addKeyListener ( k );
                                    abc.addFocusListener ( f2 ); 
                                    valueFields.add(   abc ) ;
                                    
                                    
                                    
//                    jtf[ i ][ j ] = new JTextField ();
//                    jtf[ i ][ j ].setBounds ( k1 , ( i * 40 ) + 30 , 50 , 30 );
//                    jtf[ i ][ j ].addKeyListener ( k );
//                    jtf[ i ][ j ].addFocusListener ( f2 );    

                    for ( int x = 0 ; x < data.size () ; x ++ ) {
                        if ( data.get ( x ).rmid == rm.get ( i ).RM_ID && data.get ( x ).fgid == products.get ( j ).FG_ID ) {

                            //jtf[ i ][ j ].setText ( data.get ( x ).count +"");
                            abc.setText ( data.get ( x ).count +"");    
                            x = data.size ();
                        } 
                    }

                    //jp1.add ( jtf[ i ][ j ] );
                    jp1.add ( abc );
                    jp1.revalidate ();
                    jp1.repaint ();
                }
                textFieldList.add(  valueFields ) ;
                k1 = k1 + 65;
                jp1.setPreferredSize ( new Dimension ( ( 60 * ( products.size () + 3 ) ) , ( ( 35 * rm.size () ) + 50 ) ) );
                add ( scroll );
            }
            
            revalidate ();
            repaint() ;
        }

    };

    public RMandPartCodeform () {
        initComponents ();

        loadsizepartcode ();
        loadsizerm ();
        //loadtableshowrmpart();
        
        jp1 = new JPanel ();
        jp1.setLayout ( null );
        jp1.setBackground ( Color.WHITE);
        
        jtf = new JTextField[ rm.size () ][ products.size () ];
        check = new JTextField[ rm.size () ][ products.size () ];
        jb = new JButton[ rm.size () ];
        
        t.start ();

        scroll = new JScrollPane ( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        int height = ( 35 * rm.size () ) + 300;
        if ( height % 2 != 0 ) {
            height = height - 5 ;
        }

       // if(  height <=550 ){
            scroll.setBounds ( 100 , 10 , 1000 , height );
       // }else{
       //     scroll.setBounds ( 100 , 10 , 1000 , 550 );
        //}
        scroll.setViewportView ( jp1 );
        
        JButton submit = new JButton ();
        submit.setBounds ( 1150 , 550 , 100 , 33 );
        submit.setText ( "Submit" );
        submit.addActionListener ( submitData );
        add ( submit );

        setPreferredSize ( new Dimension ( 1230 , ( ( 35 * rm.size () ) + 300 ) ) );
        setBounds ( 10 , 10 , 1180 , ( ( 35 * rm.size () ) + 300 ) );

        //loadtableshowrmpart ();
        revalidate ();
        repaint ();
        setVisible ( true );
        setBackground ( Color.WHITE );

    }

    ActionListener submitAction = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {

//            if(e.getSource() instanceof JTextField)
//            {
//                String text=((JTextField)e.getSource()).getText();
//                JOptionPane.showMessageDialog(null, text);
//            }
            if ( n != 0 ) {
                int y = 1;
                for ( int i = 0 ; i <= rm.size () ; i ++ ) {
                    for ( int j = 0 ; j < products.size () ; j ++ ) {
                        if ( i != 0 ) {

                            String text = jtf[ i ][ j ].getText ().toString ();
                            int k = 1;
                            k = i - k;
                            int x = 1;
                            x = x + j;

//                            for (int t=0;t< updatedvalues.size();t++)
//                            {
//                              fgid                          //rmid
                            if ( i == updatedvalues.get ( m1 )[ 0 ] && x == updatedvalues.get ( m1 )[ 1 ] ) {

                                //JOptionPane.showMessageDialog(null, "Updated existing record"); 
                                System.out.println ( "updated value" );
                                m1 ++;
                            } else {
                                if ( text.length () != 0 ) {

                                    String addEmpAPICall = "bomadd?FG_ID=" + x + "&RM_ID=" + i + "&NT_WT=" + text;
                                    String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                                    JOptionPane.showMessageDialog ( null , result2 );
                                    //JOptionPane.showMessageDialog(null, "Enter the any onefield");
                                    //m1++;
                                }

                            }
                            //}
                        }
                    }
                    //String text=jtf[i+1].getText().toString();
                    //JOptionPane.showMessageDialog(null, text); 
                }
            }
            //billofmaterials
            //String  var=jtf[m-4].getText();
            //System.out.print(var);
            //JOptionPane.showMessageDialog(null, var);
        }
    };

    KeyListener k = new KeyListener () {
        @Override
        public void keyTyped ( KeyEvent e ) {
           char enter = e.getKeyChar();
            String dot = Character.toString ( enter );
            if (  ! ( Character.isDigit ( enter ) )  ) {
                    e.consume ();
            }
        }

        @Override
        public void keyPressed ( KeyEvent e ) {
        }

        @Override
        public void keyReleased ( KeyEvent e ) {
        }
    };
    
    FocusListener f2 = new FocusListener () {
        
        int prevVal = 0;
        
        @Override
        public void focusGained ( FocusEvent e ) {
//            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();    
            
            if( ! jcb.getText().equals ( "") )
            {    prevVal = Integer.parseInt( jcb.getText() );
                jcb.selectAll ();
            }
            //jcb.setText("");
        }

        @Override
        public void focusLost ( FocusEvent e ) {
        //    throw new UnsupportedOperationException ( "Not supported yet." ); //To Search Google or type URL
//change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();    
            String x = jcb.getText().trim ();
            
            if(x.equalsIgnoreCase ( "")){
                x = "0";
            }
            
            try{    
                int num  = Integer.parseInt( String.valueOf( jcb.getText().toString() ) );
                    if( num<0  ){
                     jcb.setText(prevVal+"" );
                }
            }catch(NumberFormatException ex1){
                    jcb.setText(x);
            }
        }
    };
    
    
    public void loadsizerm () {
        String EmpAPICall = "rawmaterials";
        String rmResult = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
        if (  ! rmResult.contains ( "not found" ) &&  ! rmResult.contains ( "http://" ) ) {

            HashMap<String , Object> map = new HashMap<String , Object> ();
            JSONObject jObject = new JSONObject ( rmResult );
            Iterator<?> keys = jObject.keys ();

            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }

            JSONObject st = ( JSONObject ) map.get ( "data" );
            JSONArray records = st.getJSONArray ( "records" );

            JSONObject emp = null;
            rm = new ArrayList<RawMaterialDR> ();
            for ( int i = 0 ; i < records.length () ; i ++ ) {

                emp = records.getJSONObject ( i );

                String dim = "" ;
                if(   !  emp.get ( "length" ).toString ().equals ( "null") &&  ! emp.get ( "width" ).toString ().equals ( "null")     &&      ! emp.get ( "thickness" ).toString ().equals ( "null") ){
                dim = emp.get ( "length" ).toString ()+"x"+
                                    emp.get ( "width" ).toString ()+"x"+
                                    emp.get ( "thickness" ).toString () ;
                }
                //jComboBox3.addItem ( emp.get ( "Category" ).toString () );
                rm.add ( new RawMaterialDR ( Integer.parseInt ( emp.get ( "RM_ID" ).toString () ) ,  "<html>"+emp.get ( "RM_CLASS" ).toString ()+"<br>"+dim+"</html>" ) );

                Collections.sort ( rm , new CompId () );
                //System.out.println("BY ID");
                for ( RawMaterialDR p : rm ) {
                    //System.out.println(p.toString());
                }
               // System.out.println ( "" + Integer.parseInt ( emp.get ( "RM_ID" ).toString () ) + "  " + emp.get ( "RM_CLASS" ).toString () );
            }
        }
    }

    public void loadsizepartcode () {
        //part code 
        String getFGList = "finishedgoods";
        String fgString = WebAPITester.prepareWebCall ( getFGList , StaticValues.getHeader () , "" );

        if (  ! fgString.contains ( "not found" ) ) {
            HashMap<String , Object> map = new HashMap<String , Object> ();
            JSONObject jObject = new JSONObject ( fgString );
            Iterator<?> keys = jObject.keys ();

            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }

            JSONObject st = ( JSONObject ) map.get ( "data" );
            JSONArray records = st.getJSONArray ( "records" );

            JSONObject emp = null;
            products = new ArrayList<ProductDR> ();
            for ( int i = 0 ; i < records.length () ; i ++ ) {

                emp = records.getJSONObject ( i );
                //jComboBox1.addItem ( emp.get ( "FG_PART_NO" ).toString () );
                products.add ( new ProductDR ( Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) , emp.get ( "FG_PART_NO" ).toString () , emp.get ( "FG_UOM_ID" ).toString () ) );

                Collections.sort ( products , new CompId1 () );
                //System.out.println("BY ID");
                for ( ProductDR p : products ) {
                    //System.out.println(p.toString());
                }
                //System.out.println ( "" + Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) + "       " + emp.get ( "FG_PART_NO" ).toString () + "    " + emp.get ( "FG_UOM_ID" ).toString () );
            }
        }
    }

    public void loadtableshowrmpart () {
        String EmpAPICall = "billofmaterials";
        String fgString = WebAPITester.prepareWebCall ( EmpAPICall , StaticValues.getHeader () , "" );
        // JOptionPane.showMessageDialog(null, fgString);
        if (  ! fgString.contains ( "not found" ) ) {
            HashMap<String , Object> map = new HashMap<String , Object> ();
            JSONObject jObject = new JSONObject ( fgString );
            Iterator<?> keys = jObject.keys ();

            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }

            JSONObject st = ( JSONObject ) map.get ( "data" );
            JSONArray records = st.getJSONArray ( "records" );

            JSONObject emp = null;
            fg = new ArrayList<FGPARTcode> ();
            for ( int i = 0 ; i < records.length () ; i ++ ) {

                emp = records.getJSONObject ( i );
                //jComboBox1.addItem ( emp.get ( "FG_PART_NO" ).toString () );
                String abc = ( emp.get ( "NT_WT" ).toString () );
                if ( abc.contains ( "null" ) ) {
                    abc = "0";
                }
                fg.add ( new FGPARTcode ( Integer.parseInt ( emp.get ( "BOM_ID" ).toString () ) , Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) , Integer.parseInt ( emp.get ( "RM_ID" ).toString () ) , Integer.parseInt ( abc ) ) );
                //SelectedID = Integer.parseInt ( emp.get ( "BOM_ID" ).toString ());

                int l = 1;
                for ( int p = 0 ; p <= rm.size () ; p ++ ) {
                    for ( int q = 0 ; q <= products.size () ; q ++ ) {
                        int fgid = ( Integer.parseInt ( emp.get ( "FG_ID" ).toString () ) );
                        int rmid = ( Integer.parseInt ( emp.get ( "RM_ID" ).toString () ) );
                        if ( q == fgid && p == rmid ) {

                            if ( abc == "0" ) {
                                abc = "";
                            }
                            l = q - l;
                            jtf[ p ][ l ].setText ( abc );

                            updatedvalues.add ( new Integer[] { fgid , rmid } );
                            //fetch++;
                        }
                    }
                }
                //System.out.println ( ""+Integer.parseInt ( emp.get ( "BOM_ID" ).toString () ) +"   "+ Integer.parseInt(emp.get ( "FG_ID" ).toString ())+ "   " +Integer.parseInt(emp.get ( "RM_ID" ).toString ())+"  "+Integer.parseInt(abc) );
            }
        }
    }

    class CompId implements Comparator<RawMaterialDR> {

        @Override
        public int compare ( RawMaterialDR arg0 , RawMaterialDR arg1 ) {
            return arg0.RM_ID - arg1.RM_ID;
        }
    }

    class CompId1 implements Comparator<ProductDR> {

        @Override
        public int compare ( ProductDR arg0 , ProductDR arg1 ) {
            return arg0.FG_ID - arg1.FG_ID;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(1200, 900));
        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents

    ActionListener submitData = new ActionListener () {
        @Override
        public void actionPerformed ( ActionEvent e ) {

            int noOfRow = rm.size ();
            int noOfColumns = products.size ();
            int totalNoEmptyRecords = 0 ;
            boolean recordExist = false ;
            String values = "";
            ArrayList<JTextField> readValues ;
            
            int rowCounter =0, colCounter = 0, x = 0, y=0 ;
            
            data2 = new ArrayList<RMFGMap> ();
            for(  int rc1 = 0 ; rc1 < rm.size ()    ; rc1++  ){
                readValues = textFieldList.get( rc1 );
                for(  int cc1 = 0 ; cc1 < products.size()  ;  cc1++  ){
                    if( ! readValues.get( cc1 ).getText ().equals ( "" ) ){
                        totalNoEmptyRecords ++ ;
                        data2.add ( new RMFGMap ( 0 , rm.get( rc1 ).RM_ID , products.get(cc1).FG_ID , Integer.parseInt( readValues.get( cc1 ).getText () )  ) );
                    }
                }
            }
            
            System.out.println ( "total non empty records"+totalNoEmptyRecords );
            int i=0;
            int j=0;
            for(   ; rowCounter < rm.size ()    ; rowCounter++  ){
                readValues = textFieldList.get( rowCounter );
                colCounter = 0 ;
                for(   ; colCounter < products.size()  ;  colCounter++  ){
                    
                        if( ! readValues.get( colCounter ).getText ().equals ( "" ) ){
                            int currentRMID, currentPartId , count;
                            currentRMID = rm.get( rowCounter ).RM_ID;
                            currentPartId = products.get(colCounter).FG_ID ;
                            //count = Integer.parseInt( jtf[rowCounter][colCounter].getText () ) ;
                            count = Integer.parseInt( readValues.get( colCounter ).getText () );
                            
                            if(    x < data.size()  ){
                                if ( data.get ( x ).rmid == currentRMID && data.get ( x ).fgid == currentPartId  && data.get ( x ).count == count) {
                                       // JOptionPane.showMessageDialog ( null , "Unchanged entry found FG_ID=" + currentPartId + "&RM_ID=" + currentRMID );
                                    x++ ;
                                    y++;
                                }else if ( data.get ( x ).rmid == currentRMID && data.get ( x ).fgid == currentPartId   && data.get ( x ).count != count  ){
                                    //JOptionPane.showMessageDialog ( null , "Updated     entry found FG_ID=" + currentPartId + "&RM_ID=" + currentRMID );
                                    try {
                                        String  addEmpAPICall = "bomupdate?BOM_ID="+data.get ( x ).bomid +"&FG_ID=" + data.get ( x ).fgid + "&RM_ID=" + data.get ( x ).rmid  + "&UOMID=" + products.get(colCounter).UOM + "&TOTAL_WT=" + count + "&EDITED_BY=" + URLEncoder.encode ( Proman_User.getEID () + "" , "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" ).format ( Calendar.getInstance ().getTime () ) , "UTF-8" )  ;
                                        String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                                        if( result2.contains ( "success") ){
//                                             JOptionPane.showMessageDialog ( null , " entry updated FG_ID=" + currentPartId + "&RM_ID=" + currentRMID +"  count ="+count+"\n"+result2);
                                                i++;
                                        }else{
                                            j++;
                                        }
                                     } catch ( UnsupportedEncodingException e1 ) {
                                            
                                     }
                                     x++ ;
                                     y++ ;

                                }else{
                                     try {
                                    
                                         if( count != 0){
                                            String checkBomExist = "billofmaterials?FG_ID=" + currentPartId + "&RM_ID=" + currentRMID;
                                            String checkBomExistResult = WebAPITester.prepareWebCall ( checkBomExist , StaticValues.getHeader () , "" );
                                            String addEmpAPICall;
                                            if(  checkBomExistResult.contains ( "not found")  && checkBomExistResult.contains ( "101")){
                                                addEmpAPICall = "bomadd?FG_ID=" + currentPartId + "&RM_ID=" + currentRMID + "&TOTAL_WT=" + count + "&UOMID=" + products.get(colCounter).UOM + "&CREATED_ON=" + URLEncoder.encode ( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" ).format ( Calendar.getInstance ().getTime () ) , "UTF-8" ) + "&EDITED_BY=" + URLEncoder.encode ( Proman_User.getEID () + "" , "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" ).format ( Calendar.getInstance ().getTime () ) , "UTF-8" );
                                                String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                                                if( result2.contains ( "success") ){
//                                                    JOptionPane.showMessageDialog ( null , "New  entry added FG_ID=" + currentPartId + "&RM_ID=" + currentRMID +"  count ="+count+"\n"+result2);
                                                      i++;               
                                                }else{
                                                    j++;
                                                }
                                            }
                                         }
                                    } catch ( UnsupportedEncodingException ex ) {

                                    }
                                    x++ ;
                                    y++;
                                }
                            }
                      }
                }
            }
            x = data.size() ;
            rowCounter =0;
            for(   ; rowCounter < rm.size ()    ; rowCounter++  ){
                readValues = textFieldList.get( rowCounter );
                colCounter = 0 ;
                for(   ; colCounter < products.size()  ;  colCounter++  ){
                    
                        if( ! readValues.get( colCounter ).getText ().equals ( "" ) ){
                            int currentRMID, currentPartId , count;
                            currentRMID = rm.get( rowCounter ).RM_ID;
                            currentPartId = products.get(colCounter).FG_ID ;
                            //count = Integer.parseInt( jtf[rowCounter][colCounter].getText () ) ;
                            count = Integer.parseInt( readValues.get( colCounter ).getText () );

                            if( x<data2.size() ){
                                if ( data2.get ( x ).rmid == currentRMID && data2.get ( x ).fgid == currentPartId    ){
                                        //JOptionPane.showMessageDialog ( null , "New     entry found FG_ID=" + currentPartId + "&RM_ID=" + currentRMID +"  count ="+count);

                                    String addEmpAPICall;
                                    try {

                                        if(count != 0){
                                            String checkBomExist = "billofmaterials?FG_ID=" + currentPartId + "&RM_ID=" + currentRMID;
                                            String checkBomExistResult = WebAPITester.prepareWebCall ( checkBomExist , StaticValues.getHeader () , "" );

                                            if(  checkBomExistResult.contains ( "not found")  && checkBomExistResult.contains ( "101")){

                                                addEmpAPICall = "bomadd?FG_ID=" + currentPartId + "&RM_ID=" + currentRMID + "&TOTAL_WT=" + count + "&UOMID=" + products.get(colCounter).UOM + "&CREATED_ON=" + URLEncoder.encode ( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" ).format ( Calendar.getInstance ().getTime () ) , "UTF-8" ) + "&EDITED_BY=" + URLEncoder.encode ( Proman_User.getEID () + "" , "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" ).format ( Calendar.getInstance ().getTime () ) , "UTF-8" );
                                                String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                                                if( result2.contains ( "success") ){
                                                    i++;
//                                                    JOptionPane.showMessageDialog ( null , "New  entry added FG_ID=" + currentPartId + "&RM_ID=" + currentRMID +"  count ="+count+"\n"+result2);
                                                    x++ ;
                                                    
                                                }else{
                                                    j++;
                                                }
                                            }
                                        }
                                    } catch ( UnsupportedEncodingException ex ) {

                                    }

                                }
                            }
                      }
                }
            }
            
            if(i>0&&j>0)
            {
                JOptionPane.showMessageDialog ( null , i+" Entry Successfully add!"+ j+" entry failed to add|");
            }else if(i>0&&j==0)
            {
                JOptionPane.showMessageDialog ( null , "Successfully add!");
                
            }else if(i==0&&j>0)
             {
                  JOptionPane.showMessageDialog ( null ,"Failed to add!");
             }
            if(values.equals ( "changed" )){
                t.start();
            }
            
        }
    };

    
    public boolean ifRecordExist( int currentRMID, int currentPartId , int count){
        
        int found = 0 ;
        
        for ( int x = 0 ; x < data.size () ; x ++ ) {

            if ( data.get ( x ).rmid == currentRMID && data.get ( x ).fgid == currentPartId ) {
                if ( data.get ( x ).count != count ) {
//                                        try {
//                                            String addEmpAPICall = "bomupdate?BOM_ID="+data.get ( x ).bomid+"&FG_ID=" + currentPartId + "&RM_ID=" + currentRMID + "&TOTAL_WT=" + jtf[rowCounter][colCounter].getText () + "&UOMID=" + products.get(colCounter).UOM + "&CREATED_ON=" + URLEncoder.encode ( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format ( Calendar.getInstance ().getTime () ) , "UTF-8" ) + "&EDITED_BY=" + URLEncoder.encode ( Proman_User.getEID () + "" , "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format ( Calendar.getInstance ().getTime () ) , "UTF-8" );
//                                            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
//                                            if( result2.contains ( "success") ){
//                                                  JOptionPane.showMessageDialog ( null , "Updated entry in Bill of Material master against part FG_ID=" + currentPartId + "&RM_ID=" + currentRMID );
//                                            }
//                                        } catch ( UnsupportedEncodingException e1 ) {
//                                        }
                    return true;
                }
                // x  = data.size()  ;
                
            }else if ( data.get ( x ).rmid == currentRMID && data.get ( x ).fgid == currentPartId ) {
                if ( data.get ( x ).count == count ) {
//                                        try {
//                                            String addEmpAPICall = "bomupdate?BOM_ID="+data.get ( x ).bomid+"&FG_ID=" + currentPartId + "&RM_ID=" + currentRMID + "&TOTAL_WT=" + jtf[rowCounter][colCounter].getText () + "&UOMID=" + products.get(colCounter).UOM + "&CREATED_ON=" + URLEncoder.encode ( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format ( Calendar.getInstance ().getTime () ) , "UTF-8" ) + "&EDITED_BY=" + URLEncoder.encode ( Proman_User.getEID () + "" , "UTF-8" ) + "&EDITED_ON=" + URLEncoder.encode ( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format ( Calendar.getInstance ().getTime () ) , "UTF-8" );
//                                            String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
//                                            if( result2.contains ( "success") ){
//                                                  JOptionPane.showMessageDialog ( null , "Updated entry in Bill of Material master against part FG_ID=" + currentPartId + "&RM_ID=" + currentRMID );
//                                            }
//                                        } catch ( UnsupportedEncodingException e1 ) {
//                                        }
                    return true;
                }
//             x  = data.size()  ;
            }
//              else if ( data.get ( x ).rmid != currentRMID && data.get ( x ).fgid != currentPartId ) {
//              recordExist = false ;
//              x = data.size() ;
//                found = x ;
//            }
        }

//        if( found != data.size()  ){
//            return true ;
//        }
        
        
        return false ;
    }
    
    class RMFGMap {

        int bomid;
        int rmid;
        int fgid;        int count;

        public RMFGMap () {
        }

        public RMFGMap ( int bomid , int rmid , int fgid , int count ) {
            this.bomid = bomid;
            this.rmid = rmid;
            this.fgid = fgid;
            this.count = count;
        }

       
        public int getBomid () {
            return bomid;
        }

        public void setBomid ( int bomid ) {
            this.bomid = bomid;
        }

        public int getRmid () {
            return rmid;
        }

        public void setRmid ( int rmid ) {
            this.rmid = rmid;
        }

        public int getFgid () {
            return fgid;
        }

        public void setFgid ( int fgid ) {
            this.fgid = fgid;
        }

        public int getCount () {
            return count;
        }

        public void setCount ( int count ) {
            this.count = count;
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
