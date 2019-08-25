/*//<<<<<<< HEAD

package masters;

import Model.StaticValues;
import Utilities.DB_Operations;
import Utilities.DateTimeFormatter;
import Utilities.MyCustomFilter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import static masters.UtilitiyMasters.deleteMaster;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import web_services.WebAPITester;


public class PlantMaster2 extends javax.swing.JPanel {

    String resource = "", resourceType = "";

    DateTimeFormatter dtf = new DateTimeFormatter ();
    Vector<Vector<Object>> shiftData = new Vector<Vector<Object>> ();

    
    public PlantMaster2 () {
        initComponents ();

        resourceType = "Masters";
        resource = "PlantMaster2";

//        try {
//            StaticValues.checkUserRolePermission ( resourceType , resourceType );
//        } catch ( Exception e ) {
//            StaticValues.writer.writeExcel ( resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }

        try {
            loadContentPlant ();
        } catch ( Exception e ) {

        }

        jTextField5.addKeyListener ( shiftListener );
        jTextField5.addFocusListener ( phoneFieldListener );

        jTextField7.addKeyListener ( shiftListener );
        jTextField7.addFocusListener ( phoneFieldListener );

        jTextField12.addKeyListener ( shiftListener );
        jTextField12.addFocusListener ( hourFieldListener );

        jTextField16.addKeyListener ( shiftListener );
        jTextField16.addFocusListener ( hourFieldListener );

        jTextField14.addKeyListener ( shiftListener );
        jTextField14.addFocusListener ( hourFieldListener );

        jTextField18.addKeyListener ( shiftListener );
        jTextField18.addFocusListener ( hourFieldListener );

        jTextField20.addKeyListener ( shiftListener );
        jTextField20.addFocusListener ( hourFieldListener );

        jTextField22.addKeyListener ( shiftListener );
        jTextField22.addFocusListener ( hourFieldListener );

        jTextField24.addKeyListener ( shiftListener );
        jTextField24.addFocusListener ( hourFieldListener );

        jTextField26.addKeyListener ( shiftListener );
        jTextField26.addFocusListener ( hourFieldListener );

        jTextField13.addKeyListener ( shiftListener );
        jTextField13.addFocusListener ( minuteFieldListener );

        jTextField17.addKeyListener ( shiftListener );
        jTextField17.addFocusListener ( minuteFieldListener );

        jTextField15.addKeyListener ( shiftListener );
        jTextField15.addFocusListener ( minuteFieldListener );

        jTextField19.addKeyListener ( shiftListener );
        jTextField19.addFocusListener ( minuteFieldListener );

        jTextField21.addKeyListener ( shiftListener );
        jTextField21.addFocusListener ( minuteFieldListener );

        jTextField23.addKeyListener ( shiftListener );
        jTextField23.addFocusListener ( minuteFieldListener );

        jTextField25.addKeyListener ( shiftListener );
        jTextField25.addFocusListener ( minuteFieldListener );

        jTextField27.addKeyListener ( shiftListener );
        jTextField27.addFocusListener ( minuteFieldListener );

        jTable1.addMouseListener ( m );
    }

    ArrayList<byte[]> files = new ArrayList<byte[]> ();

    public void loadContentPlant () {

        String addEmpAPICall = "plants";
        String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

        if ( result2 != null &&  ! result2.contains( "not found")) {

            buildTableModelfromJSON ( result2 , 0 );
//                    jTable1.setModel ( buildTableModelfromJSON (result2 ) );
//                    jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
//                    jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
        }

       
    }

    public void buildTableModelfromJSON ( String employeeJSONList , int refPlantId ) {

        Vector<String> columnNames = new Vector<String> ();
        Vector<String> columnNames2 = new Vector<String> ();

        int columnCount = 0;
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        Vector<Vector<Object>> data2 = new Vector<Vector<Object>> ();

        JSONArray jARR = null;
        JSONObject jOB = null;

        HashMap<String , Object> map = new HashMap<String , Object> ();
        JSONObject jObject = new JSONObject ( employeeJSONList );
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
        columnNames.add ( "plantid" );
        columnNames.add ( "plantname" );
        columnNames.add ( "plantcode" );
        columnNames.add ( "gstno" );
        columnNames.add ( "plantemailaddress" );
        columnNames.add ( "plantweekoff" );
        columnNames.add ( "plantcpname" );
        columnNames.add ( "plantcpno" );
        columnNames.add ( "plantcpemail" );
        columnNames.add ( "shifts" );
        columnNames.add ( "companyname" );
        columnNames.add ( "complogo" );

        columnNames2 = new Vector<String> ();
        columnNames2.add ( "shiftid" );
        columnNames2.add ( "refplantid" );
        columnNames2.add ( "shiftno" );
        columnNames2.add ( "shifttitle" );
        columnNames2.add ( "shiftfromtime" );
        columnNames2.add ( "shifttotime" );
        columnNames2.add ( "breakduration" );

        
        String nextPlantId ="" ;
         String plantid = "";
        // data of the table
        for ( int i = 0 ; i < records.length () ; i ++ ) {

            Vector<Object> vector =  new Vector<Object> ();
            Vector<Object> vector2 = new Vector<Object> ();

           
//            if ( refPlantId == 0 ) {
                for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {

                    emp = records.getJSONObject ( i );
                    plantid = emp.get ( columnNames.get ( 0 ) ).toString ();
                    vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                    
                }
                data.add ( vector );
                

                boolean sameplant = false;
                for ( int columnIndex = 0 ; columnIndex < columnNames2.size () ; columnIndex ++ ) {
                    
                    emp = records.getJSONObject ( i );
                    try {
                        if ( plantid.equals ( emp.get ( columnNames2.get ( 0 ) ).toString () ) )  {
                            sameplant = true;
                        }
                    } catch ( Exception e ) {
                    }
                    if ( sameplant ) {
                        vector2.add ( emp.get ( columnNames2.get ( columnIndex ) ) );
                    }
                }
                if ( sameplant == true ) {
                    data2.add ( vector2 );
                }

//            } else {
//
//                boolean sameplant = false;
//                for ( int columnIndex = 0 ; columnIndex < columnNames2.size () ; columnIndex ++ ) {
//
//                    emp = records.getJSONObject ( i );
//                    try {
//                        if ( String.valueOf ( refPlantId ).equals ( emp.get ( columnNames2.get ( 0 ) ).toString () ) ) {
//                            sameplant = true;
//                        }
//                    } catch ( Exception e ) {
//                    }
//                    if ( sameplant ) {
//                        vector2.add ( emp.get ( columnNames2.get ( columnIndex ) ) );
//                    }
//                }
//                if ( sameplant == true ) {
//                    data2.add ( vector2 );
//                }
//
//            }

        }

        jTable1.setModel ( new DefaultTableModel ( data , columnNames ) );
        jTable2.setModel ( new DefaultTableModel ( data2 , columnNames2 ) );
        jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
        jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
        jTable2.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
        jTable2.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );

        //return new DefaultTableModel ( data2 , columnNames2 );
    }

    public void loadContentPlant_old () {

        String query = "Select  `plantid`, `companyname` as 'C Name', `plantname` as 'P Name', `plantcode` as 'Code', `gstno` as 'GST No.', `plantaddress` as 'Address', `plantemailaddress`  as 'Email'  , `plantcontactno`  as '<html>Plant Contact No<html>' , `plantweekoff` as 'Week Off', `plantcpname` as '<html>Contact Person Name</html>', `plantcpno` as '<html>CP Number</html>', `plantcpemail` as '<html>CP Email</html>', `shifts` as 'Shifts',  `complogo` from PLANT_MASTER  ";
        ResultSet rs = null;
        try {
            rs = DB_Operations.executeSingle ( query );
            buildPlantTableModel ( rs );
            jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
            jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );

            rs.close ();
        } catch ( Exception e ) {

        }

        String query2 = "Select * from shifts";

        if ( selectedRecordId == 0 ) {
            query2 = "Select `shiftno` as 'Shift #', `shifttitle` as 'Shift Title', `shiftfromtime` as 'From Time', `shifttotime` as 'To Time', `breakduration` as 'Break' from shifts where refplantid = " + Integer.parseInt ( jTable1.getModel ().getValueAt ( 0 , 0 ).toString () );
        } else {
            query2 = "Select `shiftno` as 'Shift #', `shifttitle` as 'Shift Title', `shiftfromtime` as 'From Time', `shifttotime` as 'To Time', `breakduration` as 'Break' from shifts where refplantid = " + selectedRecordId;
        }

        ResultSet rs2 = DB_Operations.executeSingle ( query2 );
        //   resetShiftsForms ();
        try {
            buildShiftTableModel ( rs2 );
            rs2.close ();
        } catch ( Exception e ) {

        }
    }

    public void buildPlantTableModel ( ResultSet rs )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        int columnCount = metaData.getColumnCount ();
        for ( int column = 1 ; column <= columnCount ; column ++ ) {

            columnNames.add ( metaData.getColumnName ( column ) );
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        while ( rs.next () ) {
            Vector<Object> vector = new Vector<Object> ();

            for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {

                vector.add ( rs.getObject ( columnIndex ) );

            }
            data.add ( vector );
        }

        jTable1.setModel ( new DefaultTableModel ( data , columnNames ) );
    }

    public void buildShiftTableModel ( ResultSet rs )
            throws SQLException {

        shiftData = new Vector<Vector<Object>> ();

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        int columnCount = metaData.getColumnCount ();
        for ( int column = 1 ; column <= columnCount ; column ++ ) {

            columnNames.add ( metaData.getColumnName ( column ) );
        }

        // data of the table
        while ( rs.next () ) {
            Vector<Object> vector = new Vector<Object> ();

            for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {
                vector.add ( rs.getObject ( columnIndex ) );
            }
            shiftData.add ( vector );
        }

        jTable2.setModel ( new DefaultTableModel ( shiftData , columnNames ) );
    }

    FocusListener hourFieldListener = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {

            try {
                JTextField field = ( JTextField ) e.getSource ();
                int val = Integer.parseInt ( field.getText ().trim () );

                if ( val == 0 ) {
                    field.setText ( "" );
                }
            } catch ( NumberFormatException ne1 ) {

            }
        }

        @Override
        public void focusLost ( FocusEvent e ) {

            JTextField field = ( JTextField ) e.getSource ();
            try {
                int val = Integer.parseInt ( field.getText ().trim () );

                if ( val == 0 ) {
                    field.setText ( "" );
                }

                if ( val < 0 ) {
                    val = val * -1;
                }

                if ( val > 12 ) {
                    field.setText ( "0" );
                }
            } catch ( NumberFormatException ne2 ) {
                field.setText ( "0" );
            }
        }
    };

    FocusListener phoneFieldListener = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
            try {
                JTextField field = ( JTextField ) e.getSource ();
                int val = Integer.parseInt ( field.getText ().trim () );

                if ( val == 0 ) {
                    field.setText ( "" );
                }
            } catch ( NumberFormatException e1 ) {

            }
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            //           throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField field = ( JTextField ) e.getSource ();
            try {

                if ( field.getText ().length () < 9 ) {
                    field.setText ( "" );
                }

            } catch ( NumberFormatException e1 ) {
                field.setText ( "0" + e1.getMessage () );
            }

        }
    };

    FocusListener minuteFieldListener = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
            try {
                JTextField field = ( JTextField ) e.getSource ();
                int val = Integer.parseInt ( field.getText ().trim () );

                if ( val == 0 ) {
                    field.setText ( "" );
                }
            } catch ( NumberFormatException e1 ) {

            }
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            //           throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField field = ( JTextField ) e.getSource ();
            try {

                int val = Integer.parseInt ( field.getText ().trim () );

                if ( val < 0 ) {
                    val = val * -1;
                }

                if ( val > 59 ) {
                    field.setText ( "0" );
                }

            } catch ( NumberFormatException e1 ) {
                field.setText ( "0" );
            }

        }
    };

    KeyListener shiftListener = new KeyListener () {
        @Override
        public void keyTyped ( KeyEvent e ) {
            char enter = e.getKeyChar ();
            if (  ! ( Character.isDigit ( enter ) ) ) {
                e.consume ();
            }
        }

        @Override
        public void keyPressed ( KeyEvent e ) {
            //           throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyReleased ( KeyEvent e ) {
            //           throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }
    };

    
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jSpinner2 = new javax.swing.JSpinner();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField16 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTextField23 = new javax.swing.JTextField();
        jSpinner3 = new javax.swing.JSpinner();
        jTextField24 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jTextField26 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextField27 = new javax.swing.JTextField();
        jSpinner4 = new javax.swing.JSpinner();
        jTextField11 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jSpinner5 = new javax.swing.JSpinner();
        jSpinner6 = new javax.swing.JSpinner();
        jSpinner7 = new javax.swing.JSpinner();
        jSpinner8 = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        jTextField30 = new javax.swing.JTextField();
        jTextField31 = new javax.swing.JTextField();
        jTextField32 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jTextField33 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1310, 600));
        setLayout(null);

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1244, 603));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Company name");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(30, 30, 140, 16);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Plant Code");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(30, 90, 100, 16);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("GST No");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(30, 150, 60, 16);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Contact Person Name");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(360, 90, 190, 16);

        jTextField1.setNextFocusableComponent(jTextField2);
        jPanel1.add(jTextField1);
        jTextField1.setBounds(90, 50, 220, 30);

        jTextField2.setNextFocusableComponent(jTextField3);
        jPanel1.add(jTextField2);
        jTextField2.setBounds(90, 110, 220, 30);

        jTextField3.setNextFocusableComponent(jTextField7);
        jPanel1.add(jTextField3);
        jTextField3.setBounds(90, 170, 220, 30);

        jTextField4.setNextFocusableComponent(jTextField5);
        jPanel1.add(jTextField4);
        jTextField4.setBounds(420, 110, 220, 30);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("Contact No");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(360, 150, 150, 16);

        jTextField5.setNextFocusableComponent(jTextField6);
        jPanel1.add(jTextField5);
        jTextField5.setBounds(420, 170, 220, 30);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("Plant Address");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(360, 270, 120, 16);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setNextFocusableComponent(jTextField11);
        jScrollPane3.setViewportView(jTextArea2);

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(420, 290, 223, 90);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("Email Address");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(360, 210, 90, 16);

        jTextField6.setNextFocusableComponent(jTextArea2);
        jPanel1.add(jTextField6);
        jTextField6.setBounds(420, 230, 220, 30);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("Plant Contact NO");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(30, 210, 120, 16);

        jTextField7.setNextFocusableComponent(jTextField8);
        jPanel1.add(jTextField7);
        jTextField7.setBounds(90, 230, 220, 30);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setText("Plant Email Address");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(30, 270, 180, 16);

        jTextField8.setNextFocusableComponent(jTextField33);
        jPanel1.add(jTextField8);
        jTextField8.setBounds(90, 290, 220, 30);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("Weekly Off");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(30, 330, 200, 16);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", " " }));
        jComboBox1.setNextFocusableComponent(jTextField4);
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(90, 350, 220, 30);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("Shift Title");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(710, 20, 90, 16);

        jTextField9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField9.setNextFocusableComponent(jTextField24);
        jPanel1.add(jTextField9);
        jTextField9.setBounds(680, 170, 110, 30);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Edit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(1060, 320, 80, 32);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Close");
        jPanel1.add(jButton2);
        jButton2.setBounds(1150, 320, 80, 32);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Save");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(960, 320, 90, 32);

        jTextField12.setText("0");
        jTextField12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField12FocusLost(evt);
            }
        });
        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });
        jTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField12KeyTyped(evt);
            }
        });
        jPanel1.add(jTextField12);
        jTextField12.setBounds(810, 50, 40, 30);

        jTextField13.setText("0");
        jPanel1.add(jTextField13);
        jTextField13.setBounds(860, 50, 40, 30);

        jSpinner2.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner2.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner2.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner2);
        jSpinner2.setBounds(1080, 50, 50, 31);

        jTextField14.setText("0");
        jPanel1.add(jTextField14);
        jTextField14.setBounds(810, 90, 40, 30);

        jTextField15.setText("0");
        jPanel1.add(jTextField15);
        jTextField15.setBounds(860, 90, 40, 30);

        jSpinner1.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner1.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner1.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner1);
        jSpinner1.setBounds(1080, 90, 50, 31);

        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel13.setText("From Time");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(850, 20, 70, 16);

        jLabel14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel14.setText("To Time");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(1010, 20, 60, 16);

        jLabel16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel16.setText("Break Duration");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(1150, 10, 100, 20);
        jPanel1.add(jTextField10);
        jTextField10.setBounds(1150, 170, 70, 30);
        jTextField10.getAccessibleContext().setAccessibleDescription("Break duration can be enterd in minutes as 90 minutes instead of 1.5 hours or hours in case of round figures like 1 hour");

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 0, 51));
        jLabel28.setText("*");
        jPanel1.add(jLabel28);
        jLabel28.setBounds(20, 30, 30, 10);

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 0, 51));
        jLabel30.setText("*");
        jPanel1.add(jLabel30);
        jLabel30.setBounds(20, 90, 30, 10);

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 0, 51));
        jLabel31.setText("*");
        jPanel1.add(jLabel31);
        jLabel31.setBounds(20, 150, 30, 10);

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 0, 51));
        jLabel32.setText("*");
        jPanel1.add(jLabel32);
        jLabel32.setBounds(20, 210, 30, 10);

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 0, 51));
        jLabel33.setText("*");
        jPanel1.add(jLabel33);
        jLabel33.setBounds(1140, 20, 30, 10);

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 0, 51));
        jLabel34.setText("*");
        jPanel1.add(jLabel34);
        jLabel34.setBounds(20, 270, 30, 10);

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 0, 51));
        jLabel35.setText("*");
        jPanel1.add(jLabel35);
        jLabel35.setBounds(20, 330, 30, 10);

        jLabel36.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 0, 51));
        jLabel36.setText("*");
        jPanel1.add(jLabel36);
        jLabel36.setBounds(350, 30, 30, 10);

        jLabel38.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 0, 51));
        jLabel38.setText("*");
        jPanel1.add(jLabel38);
        jLabel38.setBounds(350, 150, 30, 10);

        jLabel39.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 0, 51));
        jLabel39.setText("*");
        jPanel1.add(jLabel39);
        jLabel39.setBounds(350, 210, 30, 20);

        jLabel17.setText(":");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(850, 100, 10, 20);

        jLabel18.setText(":");
        jPanel1.add(jLabel18);
        jLabel18.setBounds(850, 60, 10, 20);

        jTable1.setAutoCreateRowSorter(true);
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
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jTable1);

        jPanel1.add(jScrollPane4);
        jScrollPane4.setBounds(30, 410, 730, 180);

        jTable2.setAutoCreateRowSorter(true);
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
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable2);

        jPanel1.add(jScrollPane5);
        jScrollPane5.setBounds(790, 410, 440, 180);

        jTextField16.setText("0");
        jTextField16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField16FocusLost(evt);
            }
        });
        jTextField16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField16ActionPerformed(evt);
            }
        });
        jTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField16KeyTyped(evt);
            }
        });
        jPanel1.add(jTextField16);
        jTextField16.setBounds(980, 50, 40, 30);

        jLabel19.setText(":");
        jPanel1.add(jLabel19);
        jLabel19.setBounds(1020, 50, 15, 20);

        jTextField17.setText("0");
        jPanel1.add(jTextField17);
        jTextField17.setBounds(1030, 50, 40, 30);

        jTextField18.setText("0");
        jPanel1.add(jTextField18);
        jTextField18.setBounds(980, 90, 40, 30);

        jLabel20.setText(":");
        jPanel1.add(jLabel20);
        jLabel20.setBounds(1020, 90, 15, 20);

        jTextField19.setText("0");
        jPanel1.add(jTextField19);
        jTextField19.setBounds(1030, 90, 40, 30);

        jTextField20.setText("0");
        jTextField20.setNextFocusableComponent(jTextField21);
        jPanel1.add(jTextField20);
        jTextField20.setBounds(810, 130, 40, 30);

        jLabel21.setText(":");
        jPanel1.add(jLabel21);
        jLabel21.setBounds(850, 130, 10, 20);

        jTextField21.setText("0");
        jTextField21.setNextFocusableComponent(jTextField22);
        jPanel1.add(jTextField21);
        jTextField21.setBounds(860, 130, 40, 30);

        jTextField22.setText("0");
        jTextField22.setNextFocusableComponent(jTextField23);
        jPanel1.add(jTextField22);
        jTextField22.setBounds(980, 130, 40, 30);

        jLabel22.setText(":");
        jPanel1.add(jLabel22);
        jLabel22.setBounds(1020, 130, 15, 20);

        jTextField23.setText("0");
        jTextField23.setNextFocusableComponent(jTextField32);
        jPanel1.add(jTextField23);
        jTextField23.setBounds(1030, 130, 40, 30);

        jSpinner3.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner3.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner3.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner3);
        jSpinner3.setBounds(1080, 130, 50, 31);

        jTextField24.setText("0");
        jTextField24.setNextFocusableComponent(jTextField25);
        jPanel1.add(jTextField24);
        jTextField24.setBounds(810, 170, 40, 30);

        jLabel23.setText(":");
        jPanel1.add(jLabel23);
        jLabel23.setBounds(850, 170, 10, 20);

        jTextField25.setText("0");
        jTextField25.setNextFocusableComponent(jTextField26);
        jPanel1.add(jTextField25);
        jTextField25.setBounds(860, 170, 40, 30);

        jTextField26.setText("0");
        jTextField26.setNextFocusableComponent(jTextField27);
        jPanel1.add(jTextField26);
        jTextField26.setBounds(980, 170, 40, 30);

        jLabel24.setText(":");
        jPanel1.add(jLabel24);
        jLabel24.setBounds(1020, 170, 15, 20);

        jTextField27.setText("0");
        jTextField27.setNextFocusableComponent(jTextField10);
        jPanel1.add(jTextField27);
        jTextField27.setBounds(1030, 170, 40, 30);

        jSpinner4.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner4.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner4.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner4);
        jSpinner4.setBounds(1080, 170, 50, 31);

        jTextField11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jPanel1.add(jTextField11);
        jTextField11.setBounds(680, 50, 110, 30);

        jTextField28.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jPanel1.add(jTextField28);
        jTextField28.setBounds(680, 90, 110, 30);

        jTextField29.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField29.setNextFocusableComponent(jTextField20);
        jPanel1.add(jTextField29);
        jTextField29.setBounds(680, 130, 110, 30);

        jSpinner5.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner5.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner5.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner5);
        jSpinner5.setBounds(910, 170, 50, 31);

        jSpinner6.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner6.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner6.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner6);
        jSpinner6.setBounds(910, 130, 50, 31);

        jSpinner7.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner7.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner7.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner7);
        jSpinner7.setBounds(910, 90, 50, 31);

        jSpinner8.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner8.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner8.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner8);
        jSpinner8.setBounds(910, 50, 50, 31);

        jLabel15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel15.setText("Minutes");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(1150, 30, 43, 16);
        jPanel1.add(jTextField30);
        jTextField30.setBounds(1150, 50, 70, 30);

        jTextField31.setNextFocusableComponent(jTextField29);
        jPanel1.add(jTextField31);
        jTextField31.setBounds(1150, 90, 70, 30);

        jTextField32.setNextFocusableComponent(jTextField9);
        jPanel1.add(jTextField32);
        jTextField32.setBounds(1150, 130, 70, 30);

        jLabel37.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 0, 51));
        jLabel37.setText("*");
        jPanel1.add(jLabel37);
        jLabel37.setBounds(670, 230, 30, 10);

        jLabel40.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 0, 51));
        jLabel40.setText("*");
        jPanel1.add(jLabel40);
        jLabel40.setBounds(700, 20, 30, 10);

        jLabel41.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 0, 51));
        jLabel41.setText("*");
        jPanel1.add(jLabel41);
        jLabel41.setBounds(840, 20, 30, 10);

        jLabel42.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 0, 51));
        jLabel42.setText("*");
        jPanel1.add(jLabel42);
        jLabel42.setBounds(1000, 20, 30, 10);

        jLabel25.setText("Shifts");
        jPanel1.add(jLabel25);
        jLabel25.setBounds(880, 410, 33, 16);

        jLabel26.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel26.setText("Plants");
        jPanel1.add(jLabel26);
        jLabel26.setBounds(30, 390, 32, 16);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("Reset");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(960, 360, 90, 32);

        jButton5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton5.setText("Export");
        jPanel1.add(jButton5);
        jButton5.setBounds(1060, 360, 80, 32);

        jButton6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton6.setText("Select Logo");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jPanel1.add(jButton6);
        jButton6.setBounds(830, 230, 120, 50);

        jLabel27.setBackground(new java.awt.Color(0, 153, 153));
        jLabel27.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel27);
        jLabel27.setBounds(680, 230, 140, 120);

        jLabel43.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 0, 51));
        jLabel43.setText("*");
        jPanel1.add(jLabel43);
        jLabel43.setBounds(350, 90, 30, 10);

        jLabel44.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel44.setText("Plant Name");
        jPanel1.add(jLabel44);
        jLabel44.setBounds(360, 30, 100, 16);

        jTextField33.setNextFocusableComponent(jTextField4);
        jPanel1.add(jTextField33);
        jTextField33.setBounds(420, 50, 220, 30);

        jLabel45.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 0, 51));
        jLabel45.setText("*");
        jPanel1.add(jLabel45);
        jLabel45.setBounds(350, 270, 30, 10);

        jButton7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton7.setText("Delete");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        jPanel1.add(jButton7);
        jButton7.setBounds(1150, 360, 80, 32);

        add(jPanel1);
        jPanel1.setBounds(0, 0, 1330, 600);
    }// </editor-fold>                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:

    }                                        

    private void jTextField12FocusLost(java.awt.event.FocusEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void jTextField12KeyTyped(java.awt.event.KeyEvent evt) {                                      
        // TODO add your handling code here:
    }                                     

    SimpleDateFormat sdf1 = new SimpleDateFormat ( "HH:mm" );
    Calendar c3 = Calendar.getInstance ();

    int totalMinutes = 0;
    int maxTotalMinutes = 1440;

    public void validateHours ( String fromTime , String toTime , String breakDuration ) throws ParseException {
        try {
            Date d1 = sdf1.parse ( toTime );
            Date d2 = sdf1.parse ( fromTime );

            //  System.out.println ( "diff in hrs  "+(((( d1.getTime () - d2.getTime())/1000)/60)/60) );
            // System.out.println ( "diff in min  "+(((( d1.getTime () - d2.getTime())/1000)/60)%60) );
            // System.out.println ( "total min  "+((((( d1.getTime () - d2.getTime())/1000)/60))+Integer.parseInt( jTextField30.getText ().trim() )) );
            totalMinutes = totalMinutes + ( int ) ( ( ( ( ( d1.getTime () - d2.getTime () ) / 1000 ) / 60 ) ) + Integer.parseInt ( breakDuration ) );

            //  System.out.println ( " Total shifts duration  " + totalMinutes );
        } catch ( ParseException e ) {

        } catch ( NumberFormatException e ) {

        }

    }

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
        //  total hours with all shifs put together should not be greater than 24 for a single dat and date
        //  the provision for break also need to be added

        int newRecordId = 0;
        String PlantNAME = "", PLANTCODE = "", GSTNO = "", PLANTADDRESS = "", PLANTEMAIL = "", WEEKOFF = "", CPNAME = "", PLANTCONTACT = "", CPEMAIL = "", CPCONTACT = "", COMPNAME = "";

        COMPNAME = jTextField1.getText ().trim ();
        PlantNAME = jTextField33.getText ().trim ();
        PLANTCODE = jTextField2.getText ().trim ();
        GSTNO = jTextField3.getText ().trim ();
        PLANTADDRESS = jTextArea2.getText ().trim ();
        PLANTEMAIL = jTextField8.getText ().trim ();
        WEEKOFF = jComboBox1.getSelectedItem ().toString ();
        CPNAME = jTextField4.getText ().trim ();
        PLANTCONTACT = jTextField7.getText ().trim ();
        CPEMAIL = jTextField6.getText ().trim ();
        CPCONTACT = jTextField5.getText ().trim ();

        ArrayList<String[]> shifts = new ArrayList<String[]> ();

        String shift_title, fromTime, toTime, breakDuration;

        shift_title = jTextField11.getText ().trim ();
        //fromTime = jTextField12.getText().trim() +":"+jTextField13.getText().trim();

        if (  ! shift_title.equals ( "" ) ) {
            c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField12.getText ().trim () ) );
            c3.set ( Calendar.MINUTE , Integer.parseInt ( jTextField13.getText ().trim () ) );
            c3.set ( Calendar.AM_PM , ( jSpinner8.getValue ().toString ().equals ( "PM" ) ) ? Calendar.PM : Calendar.AM );
            fromTime = sdf1.format ( c3.getTime () );

            c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField16.getText ().trim () ) );
            c3.set ( Calendar.MINUTE , Integer.parseInt ( jTextField17.getText ().trim () ) );
            c3.set ( Calendar.AM_PM , ( jSpinner2.getValue ().toString ().equals ( "PM" ) ) ? Calendar.PM : Calendar.AM );
            toTime = sdf1.format ( c3.getTime () );

            breakDuration = jTextField30.getText ().trim ();

            try {
                validateHours ( fromTime , toTime , breakDuration );
            } catch ( ParseException e ) {

            }

            shifts.add ( new String[] { shift_title , fromTime , toTime , breakDuration } );
        }

        shift_title = jTextField28.getText ().trim ();
        //fromTime = jTextField12.getText().trim() +":"+jTextField13.getText().trim();
        if (  ! shift_title.equals ( "" ) ) {
            c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField14.getText ().trim () ) );
            c3.set ( Calendar.MINUTE , Integer.parseInt ( jTextField15.getText ().trim () ) );
            c3.set ( Calendar.AM_PM , ( jSpinner7.getValue ().toString ().equals ( "PM" ) ) ? Calendar.PM : Calendar.AM );
            fromTime = sdf1.format ( c3.getTime () );

            c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField18.getText ().trim () ) );
            c3.set ( Calendar.MINUTE , Integer.parseInt ( jTextField19.getText ().trim () ) );
            c3.set ( Calendar.AM_PM , ( jSpinner1.getValue ().toString ().equals ( "PM" ) ) ? Calendar.PM : Calendar.AM );
            toTime = sdf1.format ( c3.getTime () );

            breakDuration = jTextField31.getText ().trim ();

            try {
                validateHours ( fromTime , toTime , breakDuration );
            } catch ( ParseException e ) {

            }

            shifts.add ( new String[] { shift_title , fromTime , toTime , breakDuration } );
        }

        shift_title = jTextField29.getText ().trim ();
        //fromTime = jTextField12.getText().trim() +":"+jTextField13.getText().trim();

        if (  ! shift_title.equals ( "" ) ) {
            c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField20.getText ().trim () ) );
            c3.set ( Calendar.MINUTE , Integer.parseInt ( jTextField21.getText ().trim () ) );
            c3.set ( Calendar.AM_PM , ( jSpinner6.getValue ().toString ().equals ( "PM" ) ) ? Calendar.PM : Calendar.AM );
            fromTime = sdf1.format ( c3.getTime () );

            c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField22.getText ().trim () ) );
            c3.set ( Calendar.MINUTE , Integer.parseInt ( jTextField23.getText ().trim () ) );
            c3.set ( Calendar.AM_PM , ( jSpinner3.getValue ().toString ().equals ( "PM" ) ) ? Calendar.PM : Calendar.AM );
            toTime = sdf1.format ( c3.getTime () );

            breakDuration = jTextField32.getText ().trim ();

            try {
                validateHours ( fromTime , toTime , breakDuration );
            } catch ( ParseException e ) {

            }

            shifts.add ( new String[] { shift_title , fromTime , toTime , breakDuration } );
        }

        shift_title = jTextField9.getText ().trim ();
        //fromTime = jTextField12.getText().trim() +":"+jTextField13.getText().trim();
        if (  ! shift_title.equals ( "" ) ) {
            c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField24.getText ().trim () ) );
            c3.set ( Calendar.MINUTE , Integer.parseInt ( jTextField25.getText ().trim () ) );
            c3.set ( Calendar.AM_PM , ( jSpinner5.getValue ().toString ().equals ( "PM" ) ) ? Calendar.PM : Calendar.AM );
            fromTime = sdf1.format ( c3.getTime () );

            c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField26.getText ().trim () ) );
            c3.set ( Calendar.MINUTE , Integer.parseInt ( jTextField27.getText ().trim () ) );
            c3.set ( Calendar.AM_PM , ( jSpinner4.getValue ().toString ().equals ( "PM" ) ) ? Calendar.PM : Calendar.AM );
            toTime = sdf1.format ( c3.getTime () );

            breakDuration = jTextField10.getText ().trim ();

            try {
                validateHours ( fromTime , toTime , breakDuration );
            } catch ( ParseException e ) {

            }

            shifts.add ( new String[] { shift_title , fromTime , toTime , breakDuration } );
        }

        System.out.println ( " " + totalMinutes );

        // if( totalMinutes <= maxTotalMinutes ){
        if (  ! PlantNAME.equals ( "" ) &&  ! PLANTCODE.equals ( "" ) &&  ! GSTNO.equals ( "" ) &&  ! PLANTADDRESS.equals ( "" ) &&  ! PLANTEMAIL.equals ( "" ) &&  ! WEEKOFF.equals ( "" ) &&  ! CPNAME.equals ( "" ) &&  ! PLANTCONTACT.equals ( "" ) &&  ! CPEMAIL.equals ( "" ) &&  ! CPCONTACT.equals ( "" ) &&  ! shifts.isEmpty () ) {

            try {

                //String plantMasterQuery = "INSERT INTO PLANT_MASTER ( `plantname`,`plantcode`,`gstno`,`plantaddress`,`plantemailaddress`,`plantweekoff`,`plantcpname`,`plantcpno`,`plantcpemail`,  `plantcontactno`, 'companyname' , 'complogo')  VALUES   ( '" + PlantNAME + "', '" + PLANTCODE + "', '" + GSTNO + "',  '" + PLANTADDRESS + "', '" + PLANTEMAIL + "', '" + WEEKOFF + "', '" + CPNAME + "', '" + CPCONTACT + "', '" + CPEMAIL + "'  , '" + PLANTCONTACT + "' , '" + COMPNAME + "' , "+ image.toString() +" )";
                String plantMasterQuery = "INSERT INTO PLANT_MASTER ( `plantname`,`plantcode`,`gstno`,`plantaddress`,`plantemailaddress`,`plantweekoff`,`plantcpname`,`plantcpno`,`plantcpemail`,  `plantcontactno`, 'companyname' , 'complogo')  VALUES   ( ? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
                Connection con = DB_Operations.getPreparedStatement ();
                PreparedStatement pst = con.prepareStatement ( plantMasterQuery );

                pst.setString ( 1 , PlantNAME );
                pst.setString ( 2 , PLANTCODE );
                pst.setString ( 3 , GSTNO );
                pst.setString ( 4 , PLANTADDRESS );
                pst.setString ( 5 , PLANTEMAIL );
                pst.setString ( 6 , WEEKOFF );
                pst.setString ( 7 , CPNAME );
                pst.setString ( 8 , CPCONTACT );
                pst.setString ( 9 , CPEMAIL );
                pst.setString ( 10 , PLANTCONTACT );
                pst.setString ( 11 , COMPNAME );
                pst.setBytes ( 12 , image );

                newRecordId = DB_Operations.executeInsertRandom ( pst );

                try {
                    String addEmpAPICall = "plantadd?plantname=" + URLEncoder.encode ( PlantNAME , "UTF-8" ) + "&plantcode=" + URLEncoder.encode ( PLANTCODE , "UTF-8" ) + "&gstno=" + URLEncoder.encode ( GSTNO , "UTF-8" ) + "&plantaddress=" + URLEncoder.encode ( PLANTADDRESS , "UTF-8" ) + "&plantemailaddress=" + URLEncoder.encode ( PLANTEMAIL , "UTF-8" ) + "&plantweekoff=" + URLEncoder.encode ( WEEKOFF , "UTF-8" ) + "&plantcpname=" + URLEncoder.encode ( CPNAME , "UTF-8" ) + "&plantcpno=" + URLEncoder.encode ( CPCONTACT , "UTF-8" ) + "&plantcpemail=" + URLEncoder.encode ( CPEMAIL , "UTF-8" ) + "&plantcontactno=" + URLEncoder.encode ( PLANTCONTACT , "UTF-8" ) + "&companyname=" + URLEncoder.encode ( COMPNAME , "UTF-8" ) + "&complogo=" + URLEncoder.encode ( image + "" , "UTF-8" );
                    String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    JOptionPane.showMessageDialog ( null , result2 );
                } catch ( UnsupportedEncodingException e ) {

                }

            } catch ( SQLException e ) {

            }

            String shiftQuery = "INSERT INTO shifts ( shiftno, shifttitle, shiftfromtime, shifttotime, breakduration, refplantid ) values ( ?,?,?,?,?,?  )";
            for ( int i = 0 ; i < shifts.size () ; i ++ ) {
                String[] shiftValues = shifts.get ( i );
                shiftQuery = "INSERT INTO shifts ( shiftno, shifttitle, shiftfromtime, shifttotime, breakduration, refplantid ) values ( " + ( i + 1 ) + ",'" + shifts.get ( i )[ 0 ] + "','" + shifts.get ( i )[ 1 ] + "','" + shifts.get ( i )[ 2 ] + "'," + Integer.parseInt ( shifts.get ( i )[ 3 ] ) + "," + newRecordId + "  )";
                DB_Operations.executeInsertRandom ( shiftQuery );

                try {
                    String addEmpAPICall = "shiftadd?shiftno=" + URLEncoder.encode ( ( i + 1 ) + "" , "UTF-8" ) + "&shifttitle=" + URLEncoder.encode ( shifts.get ( i )[ 0 ] + "" , "UTF-8" ) + "&shiftfromtime=" + URLEncoder.encode ( shifts.get ( i )[ 1 ] + "" , "UTF-8" ) + "&shifttotime=" + URLEncoder.encode ( shifts.get ( i )[ 2 ] + "" , "UTF-8" ) + "&breakduration=" + URLEncoder.encode ( Integer.parseInt ( shifts.get ( i )[ 3 ] ) + "" , "UTF-8" ) + "&refplantid=" + URLEncoder.encode ( newRecordId + "" , "UTF-8" );
                    String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

                } catch ( UnsupportedEncodingException e ) {

                }

            }

            loadContentPlant ();
            resetFields ();

        } else {

            JOptionPane.showMessageDialog ( null , "Please fill all field marked with * with appropriate values" );
        }

        if ( newRecordId > 0 ) {
            JOptionPane.showMessageDialog ( null , "Successfully inserted new plant" );

        }
        //  }else{

        //                 JOptionPane.showMessageDialog ( null , "Total shift hours ( "+totalMinutes+" )exceeding maximum hours in a day " );
        //   }

    }                                     

    DefaultListModel shiftList = new DefaultListModel ();

    MouseListener m = new MouseListener () {
        @Override
        public void mouseClicked ( MouseEvent evt ) {

            DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();

            // get the selected row index
            int selectedRowIndex = jTable1.getSelectedRow ();

            selectedRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );

            // `plantid`, `companyname` , `plantname` as 'P Name', `plantcode` as 'Code', `gstno` as 'GST No.', `plantaddress` as 'Address', `plantemailaddress`  as 'Email'  , `plantcontactno`  as '<html>Plant Contact No<html>' , `plantweekoff` as 'Week Off', `plantcpname` as '<html>Contact Person Name</html>', `plantcpno` as '<html>CP Number</html>', `plantcpemail` as '<html>CP Email</html>', `shifts` as 'Shifts',  `complogo`
//        COMPNAME = 
            jTextField1.setText ( model.getValueAt ( selectedRowIndex , 1 ).toString () );
//        PlantNAME = 
            jTextField33.setText ( model.getValueAt ( selectedRowIndex , 2 ).toString () );
//        PLANTCODE =
            jTextField2.setText ( model.getValueAt ( selectedRowIndex , 3 ).toString () );
//        GSTNO = 
            jTextField3.setText ( model.getValueAt ( selectedRowIndex , 4 ).toString () );
//        PLANTADDRESS = 
            jTextArea2.setText ( model.getValueAt ( selectedRowIndex , 5 ).toString () );
//        PLANTEMAIL = 
            jTextField8.setText ( model.getValueAt ( selectedRowIndex , 6 ).toString () );
//        WEEKOFF = jComboBox1.getSelectedItem ().toString ();
//        CPNAME = 
            jTextField4.setText ( model.getValueAt ( selectedRowIndex , 9 ).toString () );
//        PLANTCONTACT = 
            jTextField7.setText ( model.getValueAt ( selectedRowIndex , 7 ).toString () );
//        CPEMAIL =
            jTextField6.setText ( model.getValueAt ( selectedRowIndex , 11 ).toString () );
//        CPCONTACT = jTextField5.getText ().trim ();
            jTextField5.setText ( model.getValueAt ( selectedRowIndex , 10 ).toString () );

          

            //loadContentPlant ();

            Vector<Object> item = null;

            String fromtime[] = null;

            String totime[] = null;

            for ( int i = 0 ; i < 4 ; i ++ ) {

                item = null;
                try {
                    item = shiftData.get ( i );
                    fromtime = new String[ 2 ];
                    fromtime = item.get ( 2 ).toString ().split ( ":" );
                    totime = new String[ 2 ];
                    totime = item.get ( 3 ).toString ().split ( ":" );

                } catch ( ArrayIndexOutOfBoundsException e ) {
                    item = null;
                    switch ( i ) {
                        case 0:
                            //11 12 13 16 17 30
                            jTextField11.setText ( "" );
                            jTextField12.setText ( "" );
                            jTextField13.setText ( "" );
                            jTextField16.setText ( "" );
                            jTextField17.setText ( "" );
                            jTextField30.setText ( "" );
                            break;

                        case 1:
                            // 28 14 15 18 19 31
                            jTextField28.setText ( "" );
                            jTextField14.setText ( "" );
                            jTextField15.setText ( "" );
                            jTextField18.setText ( "" );
                            jTextField19.setText ( "" );
                            jTextField31.setText ( "" );
                            break;

                        case 2:
                            // 29 20 21 22 23 32
                            jTextField29.setText ( "" );
                            jTextField20.setText ( "" );
                            jTextField21.setText ( "" );
                            jTextField22.setText ( "" );
                            jTextField23.setText ( "" );
                            jTextField32.setText ( "" );

                        case 3:

                            // 9 24 25 26 27 10
                            jTextField9.setText ( "" );
                            jTextField24.setText ( "" );
                            jTextField25.setText ( "" );
                            jTextField26.setText ( "" );
                            jTextField27.setText ( "" );
                            jTextField10.setText ( "" );

                            break;

                        default:

                    }
                }

                if ( item != null ) {
                    switch ( i ) {
                        case 0:
                            //11 12 13 16 17 30
                            jTextField11.setText ( item.get ( 1 ).toString () );
                            jTextField12.setText ( fromtime[ 0 ] );
                            jTextField13.setText ( fromtime[ 1 ] );
                            jTextField16.setText ( totime[ 0 ] );
                            jTextField17.setText ( totime[ 1 ] );
                            jTextField30.setText ( item.get ( 4 ).toString () );

                            break;

                        case 1:
                            // 28 14 15 18 19 31
                            jTextField28.setText ( item.get ( 1 ).toString () );
                            jTextField14.setText ( fromtime[ 0 ] );
                            jTextField15.setText ( fromtime[ 1 ] );
                            jTextField18.setText ( totime[ 0 ] );
                            jTextField19.setText ( totime[ 1 ] );
                            jTextField31.setText ( item.get ( 4 ).toString () );

                            break;

                        case 2:
                            // 29 20 21 22 23 32
                            jTextField29.setText ( item.get ( 1 ).toString () );
                            jTextField20.setText ( fromtime[ 0 ] );
                            jTextField21.setText ( fromtime[ 1 ] );
                            jTextField22.setText ( totime[ 0 ] );
                            jTextField23.setText ( totime[ 0 ] );
                            jTextField32.setText ( item.get ( 4 ).toString () );

                            break;

                        case 3:
                            // 9 24 25 26 27 10
                            jTextField9.setText ( item.get ( 1 ).toString () );
                            jTextField24.setText ( fromtime[ 0 ] );
                            jTextField25.setText ( fromtime[ 1 ] );
                            jTextField26.setText ( totime[ 0 ] );
                            jTextField27.setText ( totime[ 1 ] );
                            jTextField10.setText ( item.get ( 4 ).toString () );

                            break;

                        default:
                    }
                } else {

                    switch ( i ) {
                        case 0:
                            //11 12 13 16 17 30
                            jTextField11.setText ( "" );
                            jTextField12.setText ( "" );
                            jTextField13.setText ( "" );
                            jTextField16.setText ( "" );
                            jTextField17.setText ( "" );
                            jTextField30.setText ( "" );
                            break;

                        case 1:
                            // 28 14 15 18 19 31
                            jTextField28.setText ( "" );
                            jTextField14.setText ( "" );
                            jTextField15.setText ( "" );
                            jTextField18.setText ( "" );
                            jTextField19.setText ( "" );
                            jTextField31.setText ( "" );
                            break;

                        case 2:
                            // 29 20 21 22 23 32
                            jTextField29.setText ( "" );
                            jTextField20.setText ( "" );
                            jTextField21.setText ( "" );
                            jTextField22.setText ( "" );
                            jTextField23.setText ( "" );
                            jTextField32.setText ( "" );

                        case 3:

                            // 9 24 25 26 27 10
                            jTextField9.setText ( "" );
                            jTextField24.setText ( "" );
                            jTextField25.setText ( "" );
                            jTextField26.setText ( "" );
                            jTextField27.setText ( "" );
                            jTextField10.setText ( "" );

                            break;

                        default:

                    }
                }
            }

        }

        @Override
        public void mousePressed ( MouseEvent e ) {
        }

        @Override
        public void mouseReleased ( MouseEvent e ) {
        }

        @Override
        public void mouseEntered ( MouseEvent e ) {
        }

        @Override
        public void mouseExited ( MouseEvent e ) {
        }
    };
    
    MouseListener m2 = new MouseListener () {
        @Override
        public void mouseClicked ( MouseEvent evt ) {

            DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();

            // get the selected row index
            int selectedRowIndex = jTable1.getSelectedRow ();

            selectedRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );

            // `plantid`, `companyname` , `plantname` as 'P Name', `plantcode` as 'Code', `gstno` as 'GST No.', `plantaddress` as 'Address', `plantemailaddress`  as 'Email'  , `plantcontactno`  as '<html>Plant Contact No<html>' , `plantweekoff` as 'Week Off', `plantcpname` as '<html>Contact Person Name</html>', `plantcpno` as '<html>CP Number</html>', `plantcpemail` as '<html>CP Email</html>', `shifts` as 'Shifts',  `complogo`
//        COMPNAME = 
            jTextField1.setText ( model.getValueAt ( selectedRowIndex , 1 ).toString () );
//        PlantNAME = 
            jTextField33.setText ( model.getValueAt ( selectedRowIndex , 2 ).toString () );
//        PLANTCODE =
            jTextField2.setText ( model.getValueAt ( selectedRowIndex , 3 ).toString () );
//        GSTNO = 
            jTextField3.setText ( model.getValueAt ( selectedRowIndex , 4 ).toString () );
//        PLANTADDRESS = 
            jTextArea2.setText ( model.getValueAt ( selectedRowIndex , 5 ).toString () );
//        PLANTEMAIL = 
            jTextField8.setText ( model.getValueAt ( selectedRowIndex , 6 ).toString () );
//        WEEKOFF = jComboBox1.getSelectedItem ().toString ();
//        CPNAME = 
            jTextField4.setText ( model.getValueAt ( selectedRowIndex , 9 ).toString () );
//        PLANTCONTACT = 
            jTextField7.setText ( model.getValueAt ( selectedRowIndex , 7 ).toString () );
//        CPEMAIL =
            jTextField6.setText ( model.getValueAt ( selectedRowIndex , 11 ).toString () );
//        CPCONTACT = jTextField5.getText ().trim ();
            jTextField5.setText ( model.getValueAt ( selectedRowIndex , 10 ).toString () );

            try {

                ResultSet rs = DB_Operations.executeSingle ( "select complogo from PLANT_MASTER where plantid = " + selectedRecordId );
                ImageIcon img = new ImageIcon ( rs.getBytes ( 1 ) );
//            jLabel27.setIcon ( img );
//           ArrayList<byte[]> f = new ArrayList<byte[]> ();
//           f.add( serializeObject ( model.getValueAt ( selectedRowIndex , 12 ) ) );
//            ImageIcon img = new ImageIcon ( f.get(0) );
                jLabel27.setIcon ( img );
            } catch ( SQLException e ) {
                System.out.println ( "" + e.getMessage () );
                jLabel27.setIcon ( null );
            } catch ( NullPointerException e ) {
                System.out.println ( "" + e.getMessage () );
                jLabel27.setIcon ( null );
            }

            loadContentPlant ();

            Vector<Object> item = null;

            String fromtime[] = null;

            String totime[] = null;

            for ( int i = 0 ; i < 4 ; i ++ ) {

                item = null;
                try {
                    item = shiftData.get ( i );
                    fromtime = new String[ 2 ];
                    fromtime = item.get ( 2 ).toString ().split ( ":" );
                    totime = new String[ 2 ];
                    totime = item.get ( 3 ).toString ().split ( ":" );

                } catch ( ArrayIndexOutOfBoundsException e ) {
                    item = null;
                    switch ( i ) {
                        case 0:
                            //11 12 13 16 17 30
                            jTextField11.setText ( "" );
                            jTextField12.setText ( "" );
                            jTextField13.setText ( "" );
                            jTextField16.setText ( "" );
                            jTextField17.setText ( "" );
                            jTextField30.setText ( "" );
                            break;

                        case 1:
                            // 28 14 15 18 19 31
                            jTextField28.setText ( "" );
                            jTextField14.setText ( "" );
                            jTextField15.setText ( "" );
                            jTextField18.setText ( "" );
                            jTextField19.setText ( "" );
                            jTextField31.setText ( "" );
                            break;

                        case 2:
                            // 29 20 21 22 23 32
                            jTextField29.setText ( "" );
                            jTextField20.setText ( "" );
                            jTextField21.setText ( "" );
                            jTextField22.setText ( "" );
                            jTextField23.setText ( "" );
                            jTextField32.setText ( "" );

                        case 3:

                            // 9 24 25 26 27 10
                            jTextField9.setText ( "" );
                            jTextField24.setText ( "" );
                            jTextField25.setText ( "" );
                            jTextField26.setText ( "" );
                            jTextField27.setText ( "" );
                            jTextField10.setText ( "" );

                            break;

                        default:

                    }
                }

                if ( item != null ) {
                    switch ( i ) {
                        case 0:
                            //11 12 13 16 17 30
                            jTextField11.setText ( item.get ( 1 ).toString () );
                            jTextField12.setText ( fromtime[ 0 ] );
                            jTextField13.setText ( fromtime[ 1 ] );
                            jTextField16.setText ( totime[ 0 ] );
                            jTextField17.setText ( totime[ 1 ] );
                            jTextField30.setText ( item.get ( 4 ).toString () );

                            break;

                        case 1:
                            // 28 14 15 18 19 31
                            jTextField28.setText ( item.get ( 1 ).toString () );
                            jTextField14.setText ( fromtime[ 0 ] );
                            jTextField15.setText ( fromtime[ 1 ] );
                            jTextField18.setText ( totime[ 0 ] );
                            jTextField19.setText ( totime[ 1 ] );
                            jTextField31.setText ( item.get ( 4 ).toString () );

                            break;

                        case 2:
                            // 29 20 21 22 23 32
                            jTextField29.setText ( item.get ( 1 ).toString () );
                            jTextField20.setText ( fromtime[ 0 ] );
                            jTextField21.setText ( fromtime[ 1 ] );
                            jTextField22.setText ( totime[ 0 ] );
                            jTextField23.setText ( totime[ 0 ] );
                            jTextField32.setText ( item.get ( 4 ).toString () );

                            break;

                        case 3:
                            // 9 24 25 26 27 10
                            jTextField9.setText ( item.get ( 1 ).toString () );
                            jTextField24.setText ( fromtime[ 0 ] );
                            jTextField25.setText ( fromtime[ 1 ] );
                            jTextField26.setText ( totime[ 0 ] );
                            jTextField27.setText ( totime[ 1 ] );
                            jTextField10.setText ( item.get ( 4 ).toString () );

                            break;

                        default:
                    }
                } else {

                    switch ( i ) {
                        case 0:
                            //11 12 13 16 17 30
                            jTextField11.setText ( "" );
                            jTextField12.setText ( "" );
                            jTextField13.setText ( "" );
                            jTextField16.setText ( "" );
                            jTextField17.setText ( "" );
                            jTextField30.setText ( "" );
                            break;

                        case 1:
                            // 28 14 15 18 19 31
                            jTextField28.setText ( "" );
                            jTextField14.setText ( "" );
                            jTextField15.setText ( "" );
                            jTextField18.setText ( "" );
                            jTextField19.setText ( "" );
                            jTextField31.setText ( "" );
                            break;

                        case 2:
                            // 29 20 21 22 23 32
                            jTextField29.setText ( "" );
                            jTextField20.setText ( "" );
                            jTextField21.setText ( "" );
                            jTextField22.setText ( "" );
                            jTextField23.setText ( "" );
                            jTextField32.setText ( "" );

                        case 3:

                            // 9 24 25 26 27 10
                            jTextField9.setText ( "" );
                            jTextField24.setText ( "" );
                            jTextField25.setText ( "" );
                            jTextField26.setText ( "" );
                            jTextField27.setText ( "" );
                            jTextField10.setText ( "" );

                            break;

                        default:

                    }
                }
            }

        }

        @Override
        public void mousePressed ( MouseEvent e ) {
        }

        @Override
        public void mouseReleased ( MouseEvent e ) {
        }

        @Override
        public void mouseEntered ( MouseEvent e ) {
        }

        @Override
        public void mouseExited ( MouseEvent e ) {
        }
    };


    private void jTextField16FocusLost(java.awt.event.FocusEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void jTextField16KeyTyped(java.awt.event.KeyEvent evt) {                                      
        // TODO add your handling code here:
    }                                     

    public static byte[] serializeObject ( Object obj ) throws IOException {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream ();
        ObjectOutputStream oos = new ObjectOutputStream ( bytesOut );
        oos.writeObject ( obj );
        oos.flush ();
        byte[] bytes = bytesOut.toByteArray ();
        bytesOut.close ();
        oos.close ();
        return bytes;
    }

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
        resetFields ();

    }                                     

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser ();
        fileChooser.setFileFilter ( new MyCustomFilter ( "image" ) );
        fileChooser.setCurrentDirectory ( new File ( System.getProperty ( "user.home" ) ) );
        int result = fileChooser.showOpenDialog ( this );
        if ( result == JFileChooser.APPROVE_OPTION ) {
            File selectedFile = fileChooser.getSelectedFile ();
            //   System.out.println("Selected file: " + selectedFile.getAbsolutePath());

            BufferedImage image1;
            try {
                image1 = ImageIO.read ( new File ( selectedFile.getAbsolutePath () ) );

                BufferedImage inputImage = ImageIO.read ( selectedFile );

                BufferedImage outputImage = new BufferedImage ( jLabel27.getWidth () , jLabel27.getHeight () , inputImage.getType () );

                //  Image img = image1.getScaledInstance(jLabel23.getWidth(), jLabel23.getHeight(), Image.SCALE_SMOOTH);
                //  ImageIcon imageIcon = new ImageIcon(img);
                // scales the input image to the output image
                Graphics2D g2d = outputImage.createGraphics ();
                g2d.drawImage ( inputImage , 0 , 0 , jLabel27.getWidth () , jLabel27.getHeight () , null );
                g2d.dispose ();

                // extracts extension of output file
                String formatName = selectedFile.getAbsolutePath ().substring ( selectedFile.getAbsolutePath ().lastIndexOf ( "." ) + 1 );
                ImageIO.write ( outputImage , formatName , new File ( "new_Image_1.jpg" ) );

            } catch ( IOException ex ) {
                //Logger.getLogger(Employee_HR_Master.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println ( ex.getMessage () );
            }

            FileInputStream fis;
            ByteArrayOutputStream bos;
            try {
                //fis = new FileInputStream(selectedFile);
                fis = new FileInputStream ( new File ( "new_Image_1.jpg" ) );
                bos = new ByteArrayOutputStream ();
                byte[] buf = new byte[ 1024 ];

                for ( int readNum ; ( readNum = fis.read ( buf ) ) != -1 ; ) {
                    bos.write ( buf , 0 , readNum );
                }

                image = bos.toByteArray ();
                ImageIcon img = new ImageIcon ( image );
                jLabel27.setIcon ( img );

            } catch ( FileNotFoundException ex ) {
                Logger.getLogger ( Employee_HR_Master.class.getName () ).log ( Level.SEVERE , null , ex );
            } catch ( IOException ex ) {
                Logger.getLogger ( Employee_HR_Master.class.getName () ).log ( Level.SEVERE , null , ex );
            }

        }
    }                                     

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
        DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();

        // get the selected row index
        int selectedRowIndex = jTable1.getSelectedRow ();

        selectedRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );

        // `plantid`, `companyname` , `plantname` as 'P Name', `plantcode` as 'Code', `gstno` as 'GST No.', `plantaddress` as 'Address', `plantemailaddress`  as 'Email'  , `plantcontactno`  as '<html>Plant Contact No<html>' , `plantweekoff` as 'Week Off', `plantcpname` as '<html>Contact Person Name</html>', `plantcpno` as '<html>CP Number</html>', `plantcpemail` as '<html>CP Email</html>', `shifts` as 'Shifts',  `complogo`
//        COMPNAME = 
        jTextField1.setText ( model.getValueAt ( selectedRowIndex , 1 ).toString () );
//        PlantNAME = 
        jTextField33.setText ( model.getValueAt ( selectedRowIndex , 2 ).toString () );
//        PLANTCODE =
        jTextField2.setText ( model.getValueAt ( selectedRowIndex , 3 ).toString () );
//        GSTNO = 
        jTextField3.setText ( model.getValueAt ( selectedRowIndex , 4 ).toString () );
//        PLANTADDRESS = 
        jTextArea2.setText ( model.getValueAt ( selectedRowIndex , 5 ).toString () );
//        PLANTEMAIL = 
        jTextField8.setText ( model.getValueAt ( selectedRowIndex , 6 ).toString () );
//        WEEKOFF = jComboBox1.getSelectedItem ().toString ();
//        CPNAME = 
        jTextField4.setText ( model.getValueAt ( selectedRowIndex , 9 ).toString () );
//        PLANTCONTACT = 
        jTextField7.setText ( model.getValueAt ( selectedRowIndex , 7 ).toString () );
//        CPEMAIL =
        jTextField6.setText ( model.getValueAt ( selectedRowIndex , 11 ).toString () );
//        CPCONTACT = jTextField5.getText ().trim ();
        jTextField5.setText ( model.getValueAt ( selectedRowIndex , 10 ).toString () );
    }                                    

    public static  String retreiveSuccessMessage( String json){
        String id = "Not able to fetch the response for your request. Please refresh you master form and try again";
        JSONObject jObject = new JSONObject ( json );
        id = jObject.getString("118" );
        return id ;
    }
    
    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        
        int selection = JOptionPane.showConfirmDialog ( null , "<html>Deleting a record is not recommended<br>The <h3>Delete</h3> action may cause loss of any dependencies and may affect the transactional Data and Reports and make data inconsistent.\nPress 'No' to revert, Press 'Yes' to continue deleting.", "Are you sure about deleting this record ?", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE);
        if ( selection == JOptionPane.YES_OPTION ) { 
        
            deleteMaster(  "plantdelete?plantid="+selectedRecordId    ) ;    
            
        }
    }//GEN-LAST:event_jButton7MouseClicked

    public  void deleteMaster( String apiName  ){
            
        String response  = WebAPITester.prepareWebCall ( apiName,  StaticValues.getHeader () , "");
        JSONObject responseObject = ( JSONObject ) new JSONTokener ( response ).nextValue ();
        JOptionPane.showMessageDialog ( null , retreiveSuccessMessage ( responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () ) );
        try {
            loadContentPlant ();
        } catch ( Exception e ) {

        }
    }
    
    byte[] image = null;

    public void resetFields () {

        jTextField1.setText ( "" );
        jTextField2.setText ( "" );
        jTextField3.setText ( "" );
        jTextArea2.setText ( "" );
        jTextField8.setText ( "" );
        jTextField4.setText ( "" );
        jTextField7.setText ( "" );
        jTextField6.setText ( "" );
        jTextField5.setText ( "" );
        jTextField12.setText ( "0" );
        jTextField13.setText ( "0" );
        jTextField16.setText ( "0" );
        jTextField17.setText ( "0" );
        jTextField14.setText ( "0" );
        jTextField15.setText ( "0" );
        jTextField18.setText ( "0" );
        jTextField19.setText ( "0" );
        jTextField21.setText ( "0" );
        jTextField20.setText ( "0" );
        jTextField22.setText ( "0" );
        jTextField23.setText ( "0" );
        jTextField24.setText ( "0" );
        jTextField25.setText ( "0" );
        jTextField26.setText ( "0" );
        jTextField27.setText ( "0" );
        jTextField11.setText ( "" );
        jTextField28.setText ( "" );
        jTextField31.setText ( "" );
        jTextField30.setText ( "" );
        jTextField33.setText ( "" );
        jTextField29.setText ( "" );
        jTextField32.setText ( "" );
    }

    public void resetShiftsForms () {

        //11 12 13 16 17 30
        jTextField11.setText ( "" );
        jTextField12.setText ( "" );
        jTextField13.setText ( "" );
        jTextField16.setText ( "" );
        jTextField17.setText ( "" );
        jTextField30.setText ( "" );

        // 28 14 15 18 19 31
        jTextField28.setText ( "" );
        jTextField14.setText ( "" );
        jTextField15.setText ( "" );
        jTextField18.setText ( "" );
        jTextField19.setText ( "" );
        jTextField31.setText ( "" );

        // 29 20 21 22 23 32
        jTextField29.setText ( "" );
        jTextField20.setText ( "" );
        jTextField21.setText ( "" );
        jTextField22.setText ( "" );
        jTextField23.setText ( "" );
        jTextField32.setText ( "" );

        // 9 24 25 26 27 10
        jTextField9.setText ( "" );
        jTextField24.setText ( "" );
        jTextField25.setText ( "" );
        jTextField26.setText ( "" );
        jTextField27.setText ( "" );
        jTextField10.setText ( "" );

    }

    int selectedRecordId = 0;

    class Shifts {

        String shifTitle, fromTime, toTime;
        int breakduration, shiftno;

    }


    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JSpinner jSpinner4;
    private javax.swing.JSpinner jSpinner5;
    private javax.swing.JSpinner jSpinner6;
    private javax.swing.JSpinner jSpinner7;
    private javax.swing.JSpinner jSpinner8;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration                   
}*/


package trixware.erp.prodezydesktop.masters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.Utilities.DB_Operations;
import trixware.erp.prodezydesktop.Utilities.DateTimeFormatter;
import trixware.erp.prodezydesktop.Utilities.MyCustomFilter;
import trixware.erp.prodezydesktop.examples.HomeScreen;
import trixware.erp.prodezydesktop.web_services.WebAPITester;


public class PlantMaster2 extends javax.swing.JPanel {

    String resource = "", resourceType = "";

    DateTimeFormatter dtf = new DateTimeFormatter ();
    Vector<Vector<Object>> shiftData = new Vector<Vector<Object>> ();

    
    public PlantMaster2 () {
        initComponents ();

        resourceType = "Masters";
        resource = "PlantMaster2";

        JTableHeader header = jTable1.getTableHeader();
        //header.setForeground(Color.BLUE);
        header.setBackground(Color.DARK_GRAY);
        
        //header.setFont(Color.TRANSLUCENT);

        //jTable1.setTableHeader(Color.GRAY);
       // jTable1.getTableHeader().setBackground(Color.GRAY);
        //jTable2.getTableHeader().setBackground(Color.BLACK);
        jScrollPane3.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane3.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        jScrollPane4.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane4.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        jScrollPane5.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        jScrollPane5.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
//        try {
//            StaticValues.checkUserRolePermission ( resourceType , resourceType );
//        } catch ( Exception e ) {
//            StaticValues.writer.writeExcel ( resource , resource , e.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , e.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );
//        }

      //  try {
            loadContentPlant ();
    //    } catch ( Exception e ) {
    //            System.out.println ( ""+e.getMessage() );
    //    }

        jTextField5.addKeyListener ( shiftListener );
        jTextField5.addFocusListener ( phoneFieldListener );

        jTextField7.addKeyListener ( shiftListener );
        jTextField7.addFocusListener ( phoneFieldListener );

        jTextField12.addKeyListener ( shiftListener );
        jTextField12.addFocusListener ( hourFieldListener );

        jTextField16.addKeyListener ( shiftListener );
        jTextField16.addFocusListener ( hourFieldListener );

        jTextField14.addKeyListener ( shiftListener );
        jTextField14.addFocusListener ( hourFieldListener );

        jTextField18.addKeyListener ( shiftListener );
        jTextField18.addFocusListener ( hourFieldListener );

        jTextField20.addKeyListener ( shiftListener );
        jTextField20.addFocusListener ( hourFieldListener );

        jTextField22.addKeyListener ( shiftListener );
        jTextField22.addFocusListener ( hourFieldListener );

        jTextField24.addKeyListener ( shiftListener );
        jTextField24.addFocusListener ( hourFieldListener );

        jTextField26.addKeyListener ( shiftListener );
        jTextField26.addFocusListener ( hourFieldListener );

        jTextField13.addKeyListener ( shiftListener );
        jTextField13.addFocusListener ( minuteFieldListener );

        jTextField17.addKeyListener ( shiftListener );
        jTextField17.addFocusListener ( minuteFieldListener );

        jTextField15.addKeyListener ( shiftListener );
        jTextField15.addFocusListener ( minuteFieldListener );

        jTextField19.addKeyListener ( shiftListener );
        jTextField19.addFocusListener ( minuteFieldListener );

        jTextField21.addKeyListener ( shiftListener );
        jTextField21.addFocusListener ( minuteFieldListener );

        jTextField23.addKeyListener ( shiftListener );
        jTextField23.addFocusListener ( minuteFieldListener );

        jTextField25.addKeyListener ( shiftListener );
        jTextField25.addFocusListener ( minuteFieldListener );

        jTextField27.addKeyListener ( shiftListener );
        jTextField27.addFocusListener ( minuteFieldListener );

        jTable1.addMouseListener ( m );
        
        //jTable2.addMouseListener ( m2 );
    }

    ArrayList<byte[]> files = new ArrayList<byte[]> ();

    Vector<String> columnNames = new Vector<String> ();
    Vector<String> columnNames2 = new Vector<String> ();
    Vector<String> columnNames3 = new Vector<String> ();
    Vector<String> columnNames4 = new Vector<String> ();

    Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
    Vector<Vector<Object>> data2 = new Vector<Vector<Object>> ();
    
    public void loadContentPlant () {
        
        columnNames = new Vector<String> ();        columnNames3 = new Vector<String> ();
        columnNames.add ( "plantid" );              columnNames3.add ( "Plant ID" );
        columnNames.add ( "plantname" );            columnNames3.add ( "Plant Name" );
        columnNames.add ( "plantcode" );            columnNames3.add ( "Code" );
        columnNames.add ( "gstno" );                columnNames3.add ( "GST No" );
        columnNames.add ( "plantemailaddress" );    columnNames3.add ( "Email" );
        columnNames.add ( "plantweekoff" );         columnNames3.add ( "Week Off" );
        columnNames.add ( "plantcpname" );          columnNames3.add ( "Cont. Person" );
        columnNames.add ( "plantcpno" );            columnNames3.add ( "Cont Per. No" );
        columnNames.add ( "plantcpemail" );         columnNames3.add ( "Cont. Email" );
        columnNames.add ( "plantaddress" );         columnNames3.add ( "Plant Add." );
        columnNames.add ( "plantcontactno" );       columnNames3.add ( "Cont. No" );
        //columnNames.add ( "shifts" );
        columnNames.add ( "companyname" );          columnNames3.add ( "Company Name" );
        columnNames.add ( "complogo" );             columnNames3.add ( "Logo" );

        columnNames2 = new Vector<String> ();           columnNames4 = new Vector<String> ();
        columnNames2.add ( "shiftid" );                         
        columnNames2.add ( "refplantid" );                      
        columnNames2.add ( "shiftno" );                         columnNames4.add ( "Shift No" );
        columnNames2.add ( "shifttitle" );                       columnNames4.add ( "Title" );
        columnNames2.add ( "shiftfromtime" );               columnNames4.add ( "From Time" );
        columnNames2.add ( "shifttotime" );                     columnNames4.add ( "To Time" );
        columnNames2.add ( "breakduration" );                   columnNames4.add ( "Break Duration" );

        String addEmpAPICall = "plants";
        String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

        if ( result2 != null &&  ! result2.contains( "not found")) {

            buildTableModelfromJSON ( result2 , 0 );

        }
    }

    public void buildTableModelfromJSON ( String employeeJSONList , int refPlantId ) {

        data = new Vector<Vector<Object>> ();
        data2 = new Vector<Vector<Object>> ();
        
        String firstPlant = "";

        HashMap<String , Object> map = new HashMap<String , Object> ();
        JSONObject jObject = new JSONObject ( employeeJSONList );
        Iterator<?> keys = jObject.keys ();
        while ( keys.hasNext () ) {
            String key = ( String ) keys.next ();
            Object value = jObject.get ( key );
            map.put ( key , value );
        }
        JSONObject st = ( JSONObject ) map.get ( "data" );
        JSONArray records = st.getJSONArray ( "records" );
        JSONObject emp = null;

        for ( int i = 0 ; i < records.length () ; i ++ ) {

            Vector<Object> vector =  new Vector<Object> ();
           
                for ( int columnIndex = 0 ; columnIndex < columnNames.size () ; columnIndex ++ ) {
                    emp = records.getJSONObject ( i );
                    vector.add ( emp.get ( columnNames.get ( columnIndex ) ) );
                    
                    if(  columnIndex == 0   ){
                        firstPlant = emp.get ( columnNames.get ( columnIndex ) ).toString()  ;
                    }
                        
                }
                data.add ( vector );
        }
        //**********edited by mayur 
         String getShiftforPlant;
         if(refPlantId==0)
         {
          getShiftforPlant = "shifts?plant_id="+ records.getJSONObject ( 0 ).get( "plantid"  );
         }
         else
         {
             getShiftforPlant = "shifts?plant_id="+refPlantId;
         }
         //*****************************
        String shiftResult = WebAPITester.prepareWebCall ( getShiftforPlant , StaticValues.getHeader () , "" );

        if ( shiftResult != null && ! shiftResult.contains ( "not found" ) ) {

            HashMap<String , Object> map2 = new HashMap<String , Object> ();
            JSONObject jObject2 = new JSONObject ( shiftResult );
            Iterator<?> keys2 = jObject2.keys ();

            while ( keys2.hasNext () ) {
                String key = ( String ) keys2.next ();
                Object value = jObject2.get ( key );
                map2.put ( key , value );
            }

            
            JSONObject st2 = ( JSONObject ) map2.get ( "data" );
            JSONArray records2 = st2.getJSONArray ( "records" );
            

            JSONObject shifts = null;

             Vector<Object> vector2 = new Vector<Object> ();
            
             System.out.println ( "Shift Information" );
            for ( int i = 0 ; i < records2.length () ; i ++ ) {
                for ( int columnIndex = 2 ; columnIndex < columnNames2.size () ; columnIndex ++ ) {
                    shifts = records2.getJSONObject ( i );
                        if(columnIndex==4 || columnIndex==5)
                        {
                             String temp[]= (shifts.get ( columnNames2.get ( columnIndex )).toString().split(" "));
                             
                             vector2.add ( temp[1] );
                        }else{
                         vector2.add ( shifts.get ( columnNames2.get ( columnIndex ) ) );
                        }
                        System.out.println (  columnNames2.get ( columnIndex ) + "    -     " +shifts.get ( columnNames2.get ( columnIndex ) )  );
                   
                }
                data2.add ( vector2 );
            }
        }
        //JTableHeader header = jTable1.getTableHeader();header.setBackground(Color.cyan); 
        
        //jTable1.setBackground(Color.red);
//        jScrollPane4.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
//        jScrollPane5.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        
        jTable1.setModel ( new DefaultTableModel ( data , columnNames3 ) );
        jTable2.setModel ( new DefaultTableModel ( data2 , columnNames4 ) );
        jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
        jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );
        jTable2.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
        jTable2.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );

        //return new DefaultTableModel ( data2 , columnNames2 );
    }

    public void loadContentPlant_old () {

        String query = "Select  `plantid`, `companyname` as 'C Name', `plantname` as 'P Name', `plantcode` as 'Code', `gstno` as 'GST No.', `plantaddress` as 'Address', `plantemailaddress`  as 'Email'  , `plantcontactno`  as '<html>Plant Contact No<html>' , `plantweekoff` as 'Week Off', `plantcpname` as '<html>Contact Person Name</html>', `plantcpno` as '<html>CP Number</html>', `plantcpemail` as '<html>CP Email</html>', `shifts` as 'Shifts',  `complogo` from PLANT_MASTER  ";
        ResultSet rs = null;
        try {
            rs = DB_Operations.executeSingle ( query );
            buildPlantTableModel ( rs );
            jTable1.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
            jTable1.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );

            rs.close ();
        } catch ( Exception e ) {

        }

        String query2 = "Select * from shifts";

        if ( selectedRecordId == 0 ) {
            query2 = "Select `shiftno` as 'Shift #', `shifttitle` as 'Shift Title', `shiftfromtime` as 'From Time', `shifttotime` as 'To Time', `breakduration` as 'Break' from shifts where refplantid = " + Integer.parseInt ( jTable1.getModel ().getValueAt ( 0 , 0 ).toString () );
        } else {
            query2 = "Select `shiftno` as 'Shift #', `shifttitle` as 'Shift Title', `shiftfromtime` as 'From Time', `shifttotime` as 'To Time', `breakduration` as 'Break' from shifts where refplantid = " + selectedRecordId;
        }

        ResultSet rs2 = DB_Operations.executeSingle ( query2 );
        //   resetShiftsForms ();
        try {
            buildShiftTableModel ( rs2 );
            rs2.close ();
        } catch ( Exception e ) {

        }
    }

    public void buildPlantTableModel ( ResultSet rs )
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        int columnCount = metaData.getColumnCount ();
        for ( int column = 1 ; column <= columnCount ; column ++ ) {

            columnNames.add ( metaData.getColumnName ( column ) );
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>> ();
        while ( rs.next () ) {
            Vector<Object> vector = new Vector<Object> ();

            for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {

                vector.add ( rs.getObject ( columnIndex ) );

            }
            data.add ( vector );
        }

        jTable1.setModel ( new DefaultTableModel ( data , columnNames ) );
    }

    public void buildShiftTableModel ( ResultSet rs )
            throws SQLException {

        shiftData = new Vector<Vector<Object>> ();

        ResultSetMetaData metaData = rs.getMetaData ();

        // names of columns
        Vector<String> columnNames = new Vector<String> ();
        int columnCount = metaData.getColumnCount ();
        for ( int column = 1 ; column <= columnCount ; column ++ ) {

            columnNames.add ( metaData.getColumnName ( column ) );
        }

        // data of the table
        while ( rs.next () ) {
            Vector<Object> vector = new Vector<Object> ();

            for ( int columnIndex = 1 ; columnIndex <= columnCount ; columnIndex ++ ) {
                vector.add ( rs.getObject ( columnIndex ) );
            }
            shiftData.add ( vector );
        }

        jTable2.setModel ( new DefaultTableModel ( shiftData , columnNames ) );
    }

    FocusListener hourFieldListener = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {

            try {
                JTextField field = ( JTextField ) e.getSource ();
                int val = Integer.parseInt ( field.getText ().trim () );

                if ( val == 0 ) {
                    field.setText ( "" );
                }
            } catch ( NumberFormatException ne1 ) {

            }
        }

        @Override
        public void focusLost ( FocusEvent e ) {

            JTextField field = ( JTextField ) e.getSource ();
            try {
                int val = Integer.parseInt ( field.getText ().trim () );

                if ( val == 0 ) {
                    field.setText ( "" );
                }

                if ( val < 0 ) {
                    val = val * -1;
                }

                if ( val > 12 ) {
                    field.setText ( "0" );
                }
            } catch ( NumberFormatException ne2 ) {
                field.setText ( "0" );
            }
        }
    };

    FocusListener phoneFieldListener = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
            try {
                JTextField field = ( JTextField ) e.getSource ();
                int val = Integer.parseInt ( field.getText ().trim () );

                if ( val == 0 ) {
                    field.setText ( "" );
                }
            } catch ( NumberFormatException e1 ) {

            }
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            //           throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField field = ( JTextField ) e.getSource ();
            try {

                if ( field.getText ().length () < 9 ) {
                    field.setText ( "" );
                }

            } catch ( NumberFormatException e1 ) {
                field.setText ( "0" + e1.getMessage () );
            }

        }
    };

    FocusListener minuteFieldListener = new FocusListener () {
        @Override
        public void focusGained ( FocusEvent e ) {
            try {
                JTextField field = ( JTextField ) e.getSource ();
                int val = Integer.parseInt ( field.getText ().trim () );

                if ( val == 0 ) {
                    field.setText ( "" );
                }
            } catch ( NumberFormatException e1 ) {

            }
        }

        @Override
        public void focusLost ( FocusEvent e ) {
            //           throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField field = ( JTextField ) e.getSource ();
            try {

                int val = Integer.parseInt ( field.getText ().trim () );

                if ( val < 0 ) {
                    val = val * -1;
                }

                if ( val > 59 ) {
                    field.setText ( "0" );
                }

            } catch ( NumberFormatException e1 ) {
                field.setText ( "0" );
            }

        }
    };

    KeyListener shiftListener = new KeyListener () {
        @Override
        public void keyTyped ( KeyEvent e ) {
            char enter = e.getKeyChar ();
            if (  ! ( Character.isDigit ( enter ) ) ) {
                e.consume ();
            }
        }

        @Override
        public void keyPressed ( KeyEvent e ) {
            //           throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyReleased ( KeyEvent e ) {
            //           throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }
    };

    
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jSpinner2 = new javax.swing.JSpinner();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField16 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTextField23 = new javax.swing.JTextField();
        jSpinner3 = new javax.swing.JSpinner();
        jTextField24 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jTextField26 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextField27 = new javax.swing.JTextField();
        jSpinner4 = new javax.swing.JSpinner();
        jTextField11 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jSpinner5 = new javax.swing.JSpinner();
        jSpinner6 = new javax.swing.JSpinner();
        jSpinner7 = new javax.swing.JSpinner();
        jSpinner8 = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        jTextField30 = new javax.swing.JTextField();
        jTextField31 = new javax.swing.JTextField();
        jTextField32 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jTextField33 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1310, 600));
        setLayout(null);

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1244, 603));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Company name");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(30, 30, 140, 16);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Plant Code");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(30, 90, 100, 16);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("GST No");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(30, 150, 60, 16);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Contact Person Name");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(360, 90, 190, 16);

        jTextField1.setNextFocusableComponent(jTextField2);
        jPanel1.add(jTextField1);
        jTextField1.setBounds(90, 50, 220, 30);

        jTextField2.setNextFocusableComponent(jTextField3);
        jPanel1.add(jTextField2);
        jTextField2.setBounds(90, 110, 220, 30);

        jTextField3.setNextFocusableComponent(jTextField7);
        jPanel1.add(jTextField3);
        jTextField3.setBounds(90, 170, 220, 30);

        jTextField4.setNextFocusableComponent(jTextField5);
        jPanel1.add(jTextField4);
        jTextField4.setBounds(420, 110, 220, 30);

        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel6.setText("Contact No");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(360, 150, 150, 16);

        jTextField5.setNextFocusableComponent(jTextField6);
        jPanel1.add(jTextField5);
        jTextField5.setBounds(420, 170, 220, 30);

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel7.setText("Plant Address");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(360, 270, 120, 16);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setNextFocusableComponent(jTextField11);
        jScrollPane3.setViewportView(jTextArea2);

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(420, 290, 223, 90);

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel8.setText("Email Address");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(360, 210, 90, 16);

        jTextField6.setNextFocusableComponent(jTextArea2);
        jPanel1.add(jTextField6);
        jTextField6.setBounds(420, 230, 220, 30);

        jLabel9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel9.setText("Plant Contact NO");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(30, 210, 120, 16);

        jTextField7.setNextFocusableComponent(jTextField8);
        jPanel1.add(jTextField7);
        jTextField7.setBounds(90, 230, 220, 30);

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel10.setText("Plant Email Address");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(30, 270, 180, 16);

        jTextField8.setNextFocusableComponent(jTextField33);
        jPanel1.add(jTextField8);
        jTextField8.setBounds(90, 290, 220, 30);

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel11.setText("Weekly Off");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(30, 330, 200, 16);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", " " }));
        jComboBox1.setNextFocusableComponent(jTextField4);
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(90, 350, 220, 30);

        jLabel12.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel12.setText("Shift Title");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(710, 20, 90, 16);

        jTextField9.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField9.setNextFocusableComponent(jTextField24);
        jPanel1.add(jTextField9);
        jTextField9.setBounds(680, 170, 110, 30);

        jButton1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton1.setText("Edit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(1060, 320, 80, 32);

        jButton2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton2.setText("Close");
        jPanel1.add(jButton2);
        jButton2.setBounds(1150, 320, 80, 32);

        jButton3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton3.setText("Save");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(960, 320, 90, 32);

        jTextField12.setText("0");
        jTextField12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField12FocusLost(evt);
            }
        });
        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });
        jTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField12KeyTyped(evt);
            }
        });
        jPanel1.add(jTextField12);
        jTextField12.setBounds(810, 50, 40, 30);

        jTextField13.setText("0");
        jPanel1.add(jTextField13);
        jTextField13.setBounds(860, 50, 40, 30);

        jSpinner2.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner2.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner2.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner2);
        jSpinner2.setBounds(1080, 50, 50, 31);

        jTextField14.setText("0");
        jPanel1.add(jTextField14);
        jTextField14.setBounds(810, 90, 40, 30);

        jTextField15.setText("0");
        jPanel1.add(jTextField15);
        jTextField15.setBounds(860, 90, 40, 30);

        jSpinner1.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner1.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner1.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner1);
        jSpinner1.setBounds(1080, 90, 50, 31);

        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel13.setText("From Time");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(850, 20, 70, 16);

        jLabel14.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel14.setText("To Time");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(1010, 20, 60, 16);

        jLabel16.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel16.setText("Break Duration");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(1150, 10, 100, 20);
        jPanel1.add(jTextField10);
        jTextField10.setBounds(1150, 170, 70, 30);
        jTextField10.getAccessibleContext().setAccessibleDescription("Break duration can be enterd in minutes as 90 minutes instead of 1.5 hours or hours in case of round figures like 1 hour");

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 0, 51));
        jLabel28.setText("*");
        jPanel1.add(jLabel28);
        jLabel28.setBounds(20, 30, 30, 10);

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 0, 51));
        jLabel30.setText("*");
        jPanel1.add(jLabel30);
        jLabel30.setBounds(20, 90, 30, 10);

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 0, 51));
        jLabel31.setText("*");
        jPanel1.add(jLabel31);
        jLabel31.setBounds(20, 150, 30, 10);

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 0, 51));
        jLabel32.setText("*");
        jPanel1.add(jLabel32);
        jLabel32.setBounds(20, 210, 30, 10);

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 0, 51));
        jLabel33.setText("*");
        jPanel1.add(jLabel33);
        jLabel33.setBounds(1140, 20, 30, 10);

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 0, 51));
        jLabel34.setText("*");
        jPanel1.add(jLabel34);
        jLabel34.setBounds(20, 270, 30, 10);

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 0, 51));
        jLabel35.setText("*");
        jPanel1.add(jLabel35);
        jLabel35.setBounds(20, 330, 30, 10);

        jLabel36.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 0, 51));
        jLabel36.setText("*");
        jPanel1.add(jLabel36);
        jLabel36.setBounds(350, 30, 30, 10);

        jLabel38.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 0, 51));
        jLabel38.setText("*");
        jPanel1.add(jLabel38);
        jLabel38.setBounds(350, 150, 30, 10);

        jLabel39.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 0, 51));
        jLabel39.setText("*");
        jPanel1.add(jLabel39);
        jLabel39.setBounds(350, 210, 30, 20);

        jLabel17.setText(":");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(850, 100, 10, 20);

        jLabel18.setText(":");
        jPanel1.add(jLabel18);
        jLabel18.setBounds(850, 60, 10, 20);

        jTable1.setAutoCreateRowSorter(true);
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
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(jTable1);

        jPanel1.add(jScrollPane4);
        jScrollPane4.setBounds(30, 410, 730, 180);

        jTable2.setAutoCreateRowSorter(true);
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
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable2);

        jPanel1.add(jScrollPane5);
        jScrollPane5.setBounds(790, 410, 440, 180);

        jTextField16.setText("0");
        jTextField16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField16FocusLost(evt);
            }
        });
        jTextField16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField16ActionPerformed(evt);
            }
        });
        jTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField16KeyTyped(evt);
            }
        });
        jPanel1.add(jTextField16);
        jTextField16.setBounds(980, 50, 40, 30);

        jLabel19.setText(":");
        jPanel1.add(jLabel19);
        jLabel19.setBounds(1020, 50, 15, 20);

        jTextField17.setText("0");
        jPanel1.add(jTextField17);
        jTextField17.setBounds(1030, 50, 40, 30);

        jTextField18.setText("0");
        jPanel1.add(jTextField18);
        jTextField18.setBounds(980, 90, 40, 30);

        jLabel20.setText(":");
        jPanel1.add(jLabel20);
        jLabel20.setBounds(1020, 90, 15, 20);

        jTextField19.setText("0");
        jPanel1.add(jTextField19);
        jTextField19.setBounds(1030, 90, 40, 30);

        jTextField20.setText("0");
        jTextField20.setNextFocusableComponent(jTextField21);
        jPanel1.add(jTextField20);
        jTextField20.setBounds(810, 130, 40, 30);

        jLabel21.setText(":");
        jPanel1.add(jLabel21);
        jLabel21.setBounds(850, 130, 10, 20);

        jTextField21.setText("0");
        jTextField21.setNextFocusableComponent(jTextField22);
        jPanel1.add(jTextField21);
        jTextField21.setBounds(860, 130, 40, 30);

        jTextField22.setText("0");
        jTextField22.setNextFocusableComponent(jTextField23);
        jPanel1.add(jTextField22);
        jTextField22.setBounds(980, 130, 40, 30);

        jLabel22.setText(":");
        jPanel1.add(jLabel22);
        jLabel22.setBounds(1020, 130, 15, 20);

        jTextField23.setText("0");
        jTextField23.setNextFocusableComponent(jTextField32);
        jPanel1.add(jTextField23);
        jTextField23.setBounds(1030, 130, 40, 30);

        jSpinner3.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner3.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner3.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner3);
        jSpinner3.setBounds(1080, 130, 50, 31);

        jTextField24.setText("0");
        jTextField24.setNextFocusableComponent(jTextField25);
        jPanel1.add(jTextField24);
        jTextField24.setBounds(810, 170, 40, 30);

        jLabel23.setText(":");
        jPanel1.add(jLabel23);
        jLabel23.setBounds(850, 170, 10, 20);

        jTextField25.setText("0");
        jTextField25.setNextFocusableComponent(jTextField26);
        jPanel1.add(jTextField25);
        jTextField25.setBounds(860, 170, 40, 30);

        jTextField26.setText("0");
        jTextField26.setNextFocusableComponent(jTextField27);
        jPanel1.add(jTextField26);
        jTextField26.setBounds(980, 170, 40, 30);

        jLabel24.setText(":");
        jPanel1.add(jLabel24);
        jLabel24.setBounds(1020, 170, 15, 20);

        jTextField27.setText("0");
        jTextField27.setNextFocusableComponent(jTextField10);
        jPanel1.add(jTextField27);
        jTextField27.setBounds(1030, 170, 40, 30);

        jSpinner4.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner4.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner4.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner4);
        jSpinner4.setBounds(1080, 170, 50, 31);

        jTextField11.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jPanel1.add(jTextField11);
        jTextField11.setBounds(680, 50, 110, 30);

        jTextField28.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jPanel1.add(jTextField28);
        jTextField28.setBounds(680, 90, 110, 30);

        jTextField29.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jTextField29.setNextFocusableComponent(jTextField20);
        jPanel1.add(jTextField29);
        jTextField29.setBounds(680, 130, 110, 30);

        jSpinner5.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner5.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner5.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner5);
        jSpinner5.setBounds(910, 170, 50, 31);

        jSpinner6.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner6.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner6.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner6);
        jSpinner6.setBounds(910, 130, 50, 31);

        jSpinner7.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner7.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner7.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner7);
        jSpinner7.setBounds(910, 90, 50, 31);

        jSpinner8.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        jSpinner8.setModel(new javax.swing.SpinnerListModel(new String[] {"AM", "PM"}));
        jSpinner8.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel1.add(jSpinner8);
        jSpinner8.setBounds(910, 50, 50, 31);

        jLabel15.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel15.setText("Minutes");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(1150, 30, 43, 16);
        jPanel1.add(jTextField30);
        jTextField30.setBounds(1150, 50, 70, 30);

        jTextField31.setNextFocusableComponent(jTextField29);
        jPanel1.add(jTextField31);
        jTextField31.setBounds(1150, 90, 70, 30);

        jTextField32.setNextFocusableComponent(jTextField9);
        jPanel1.add(jTextField32);
        jTextField32.setBounds(1150, 130, 70, 30);

        jLabel37.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 0, 51));
        jLabel37.setText("*");
        jPanel1.add(jLabel37);
        jLabel37.setBounds(670, 230, 30, 10);

        jLabel40.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 0, 51));
        jLabel40.setText("*");
        jPanel1.add(jLabel40);
        jLabel40.setBounds(700, 20, 30, 10);

        jLabel41.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 0, 51));
        jLabel41.setText("*");
        jPanel1.add(jLabel41);
        jLabel41.setBounds(840, 20, 30, 10);

        jLabel42.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 0, 51));
        jLabel42.setText("*");
        jPanel1.add(jLabel42);
        jLabel42.setBounds(1000, 20, 30, 10);

        jLabel25.setText("Shifts");
        jPanel1.add(jLabel25);
        jLabel25.setBounds(880, 410, 33, 16);

        jLabel26.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel26.setText("Plants");
        jPanel1.add(jLabel26);
        jLabel26.setBounds(30, 390, 32, 16);

        jButton4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton4.setText("Reset");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(960, 360, 90, 32);

        jButton5.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton5.setText("Export");
        jPanel1.add(jButton5);
        jButton5.setBounds(1060, 360, 80, 32);

        jButton6.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton6.setText("Select Logo");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jPanel1.add(jButton6);
        jButton6.setBounds(830, 230, 120, 50);

        jLabel27.setBackground(new java.awt.Color(0, 153, 153));
        jLabel27.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel27);
        jLabel27.setBounds(680, 230, 140, 120);

        jLabel43.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 0, 51));
        jLabel43.setText("*");
        jPanel1.add(jLabel43);
        jLabel43.setBounds(350, 90, 30, 10);

        jLabel44.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel44.setText("Plant Name");
        jPanel1.add(jLabel44);
        jLabel44.setBounds(360, 30, 100, 16);

        jTextField33.setNextFocusableComponent(jTextField4);
        jPanel1.add(jTextField33);
        jTextField33.setBounds(420, 50, 220, 30);

        jLabel45.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 0, 51));
        jLabel45.setText("*");
        jPanel1.add(jLabel45);
        jLabel45.setBounds(350, 270, 30, 10);

        jButton7.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jButton7.setText("Delete");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7);
        jButton7.setBounds(1150, 360, 80, 32);

        add(jPanel1);
        jPanel1.setBounds(0, 0, 1240, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField12FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12FocusLost

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jTextField12KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField12KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12KeyTyped

    SimpleDateFormat sdf1 = new SimpleDateFormat ( "HH:mm" );
    Calendar c3 = Calendar.getInstance ();

    int totalMinutes = 0;
    int maxTotalMinutes = 1440;

    public void validateHours ( String fromTime , String toTime , String breakDuration ) throws ParseException {
        try {
            Date d1 = sdf1.parse ( toTime );
            Date d2 = sdf1.parse ( fromTime );

            //  System.out.println ( "diff in hrs  "+(((( d1.getTime () - d2.getTime())/1000)/60)/60) );
            // System.out.println ( "diff in min  "+(((( d1.getTime () - d2.getTime())/1000)/60)%60) );
            // System.out.println ( "total min  "+((((( d1.getTime () - d2.getTime())/1000)/60))+Integer.parseInt( jTextField30.getText ().trim() )) );
            totalMinutes = totalMinutes + ( int ) ( ( ( ( ( d1.getTime () - d2.getTime () ) / 1000 ) / 60 ) ) + Integer.parseInt ( breakDuration ) );

            //  System.out.println ( " Total shifts duration  " + totalMinutes );
        } catch ( ParseException e ) {

        } catch ( NumberFormatException e ) {

        }

    }
    
    public int iDFromJSON( String json){
        int id = 0;
       
        JSONObject jObject = new JSONObject ( json );
        JSONObject value = (JSONObject) jObject.get ( "data" );
        id = Integer.parseInt(value.get ("insert_id" ).toString());
        //    id = Integer.parseInt( st.getString ("insert_id" ));
        
        return id ;
    }

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        //  total hours with all shifs put together should not be greater than 24 for a single dat and date
        //  the provision for break also need to be added

        int newRecordId = 0;
        int newRecordForAPI = 0 ;
        
        String PlantNAME = "", PLANTCODE = "", GSTNO = "", PLANTADDRESS = "", PLANTEMAIL = "", WEEKOFF = "", CPNAME = "", PLANTCONTACT = "", CPEMAIL = "", CPCONTACT = "", COMPNAME = "";

        COMPNAME = jTextField1.getText ().trim ();
        PlantNAME = jTextField33.getText ().trim ();
        PLANTCODE = jTextField2.getText ().trim ();
        GSTNO = jTextField3.getText ().trim ();
        PLANTADDRESS = jTextArea2.getText ().trim ();
        PLANTEMAIL = jTextField8.getText ().trim ();
        WEEKOFF = jComboBox1.getSelectedItem ().toString ();
        CPNAME = jTextField4.getText ().trim ();
        PLANTCONTACT = jTextField7.getText ().trim ();
        CPEMAIL = jTextField6.getText ().trim ();
        CPCONTACT = jTextField5.getText ().trim ();

        ArrayList<String[]> shifts = new ArrayList<String[]> ();

        String shift_title, fromTime, toTime, breakDuration,frmTime=null,Totime=null;

        shift_title = jTextField11.getText ().trim ();
        //fromTime = jTextField12.getText().trim() +":"+jTextField13.getText().trim();

        if (  ! shift_title.equals ( "" ) ) {
            c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField12.getText ().trim () ) );
            c3.set ( Calendar.MINUTE , Integer.parseInt ( jTextField13.getText ().trim () ) );
            c3.set ( Calendar.AM_PM , ( jSpinner8.getValue ().toString ().equals ( "PM" ) ) ? Calendar.PM : Calendar.AM );
            fromTime = sdf1.format ( c3.getTime () );

            c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField16.getText ().trim () ) );
            c3.set ( Calendar.MINUTE , Integer.parseInt ( jTextField17.getText ().trim () ) );
            c3.set ( Calendar.AM_PM , ( jSpinner2.getValue ().toString ().equals ( "PM" ) ) ? Calendar.PM : Calendar.AM );
            toTime = sdf1.format ( c3.getTime () );

            breakDuration = jTextField30.getText ().trim ();

            try {
                validateHours ( fromTime , toTime , breakDuration );
            } catch ( ParseException e ) {

            }
            
            /*************Edited by mayur
             for proper date add***************/
            frmTime= "0000-00-00 "+fromTime+":00";
            Totime= "0000-00-00 "+toTime+":00";
            shifts.add ( new String[] { shift_title , frmTime , Totime , breakDuration } );
            /****************************/
            //shifts.add ( new String[] { shift_title , fromTime , toTime , breakDuration } );
        }

        shift_title = jTextField28.getText ().trim ();
        //fromTime = jTextField12.getText().trim() +":"+jTextField13.getText().trim();
        if (  ! shift_title.equals ( "" ) ) {
            c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField14.getText ().trim () ) );
            c3.set ( Calendar.MINUTE , Integer.parseInt ( jTextField15.getText ().trim () ) );
            c3.set ( Calendar.AM_PM , ( jSpinner7.getValue ().toString ().equals ( "PM" ) ) ? Calendar.PM : Calendar.AM );
            fromTime = sdf1.format ( c3.getTime () );

            c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField18.getText ().trim () ) );
            c3.set ( Calendar.MINUTE , Integer.parseInt ( jTextField19.getText ().trim () ) );
            c3.set ( Calendar.AM_PM , ( jSpinner1.getValue ().toString ().equals ( "PM" ) ) ? Calendar.PM : Calendar.AM );
            toTime = sdf1.format ( c3.getTime () );

            breakDuration = jTextField31.getText ().trim ();

            try {
                validateHours ( fromTime , toTime , breakDuration );
            } catch ( ParseException e ) {

            }
            /*************Edited by mayur
             for proper date add***************/
            frmTime= "0000-00-00 "+fromTime+":00";
            Totime= "0000-00-00 "+toTime+":00";
             shifts.add ( new String[] { shift_title , frmTime , Totime , breakDuration } );
            /****************************/
          
        }

        shift_title = jTextField29.getText ().trim ();
        //fromTime = jTextField12.getText().trim() +":"+jTextField13.getText().trim();

        if (  ! shift_title.equals ( "" ) ) {
            c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField20.getText ().trim () ) );
            c3.set ( Calendar.MINUTE , Integer.parseInt ( jTextField21.getText ().trim () ) );
            c3.set ( Calendar.AM_PM , ( jSpinner6.getValue ().toString ().equals ( "PM" ) ) ? Calendar.PM : Calendar.AM );
            fromTime = sdf1.format ( c3.getTime () );

            c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField22.getText ().trim () ) );
            c3.set ( Calendar.MINUTE , Integer.parseInt ( jTextField23.getText ().trim () ) );
            c3.set ( Calendar.AM_PM , ( jSpinner3.getValue ().toString ().equals ( "PM" ) ) ? Calendar.PM : Calendar.AM );
            toTime = sdf1.format ( c3.getTime () );

            breakDuration = jTextField32.getText ().trim ();

            try {
                validateHours ( fromTime , toTime , breakDuration );
            } catch ( ParseException e ) {

            }
            /*************Edited by mayur
             for proper date add***************/
            frmTime= "0000-00-00 "+fromTime+":00";
            Totime= "0000-00-00 "+toTime+":00";
             shifts.add ( new String[] { shift_title , frmTime , Totime , breakDuration } );
            /****************************/
        }

        shift_title = jTextField9.getText ().trim ();
        //fromTime = jTextField12.getText().trim() +":"+jTextField13.getText().trim();
        if (  ! shift_title.equals ( "" ) ) {
            c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField24.getText ().trim () ) );
            c3.set ( Calendar.MINUTE , Integer.parseInt ( jTextField25.getText ().trim () ) );
            c3.set ( Calendar.AM_PM , ( jSpinner5.getValue ().toString ().equals ( "PM" ) ) ? Calendar.PM : Calendar.AM );
            fromTime = sdf1.format ( c3.getTime () );

            c3.set ( Calendar.HOUR , Integer.parseInt ( jTextField26.getText ().trim () ) );
            c3.set ( Calendar.MINUTE , Integer.parseInt ( jTextField27.getText ().trim () ) );
            c3.set ( Calendar.AM_PM , ( jSpinner4.getValue ().toString ().equals ( "PM" ) ) ? Calendar.PM : Calendar.AM );
            toTime = sdf1.format ( c3.getTime () );

            breakDuration = jTextField10.getText ().trim ();

            try {
                validateHours ( fromTime , toTime , breakDuration );
            } catch ( ParseException e ) {

            }
           /*************Edited by mayur
             for proper date add***************/
            frmTime= "0000-00-00 "+fromTime+":00";
            Totime= "0000-00-00 "+toTime+":00";
             shifts.add ( new String[] { shift_title , frmTime , Totime , breakDuration } );
            /****************************/
            //shifts.add ( new String[] { shift_title , fromTime , toTime , breakDuration } );
        }

        System.out.println ( " " + totalMinutes );

        // if( totalMinutes <= maxTotalMinutes ){
        if (  ! PlantNAME.equals ( "" ) &&  ! PLANTCODE.equals ( "" ) &&  ! GSTNO.equals ( "" ) &&  ! PLANTADDRESS.equals ( "" ) &&  ! PLANTEMAIL.equals ( "" ) &&  ! WEEKOFF.equals ( "" ) &&  ! CPNAME.equals ( "" ) &&  ! PLANTCONTACT.equals ( "" ) &&  ! CPEMAIL.equals ( "" ) &&  ! CPCONTACT.equals ( "" ) &&  ! shifts.isEmpty () ) {

            try {

                //String plantMasterQuery = "INSERT INTO PLANT_MASTER ( `plantname`,`plantcode`,`gstno`,`plantaddress`,`plantemailaddress`,`plantweekoff`,`plantcpname`,`plantcpno`,`plantcpemail`,  `plantcontactno`, 'companyname' , 'complogo')  VALUES   ( '" + PlantNAME + "', '" + PLANTCODE + "', '" + GSTNO + "',  '" + PLANTADDRESS + "', '" + PLANTEMAIL + "', '" + WEEKOFF + "', '" + CPNAME + "', '" + CPCONTACT + "', '" + CPEMAIL + "'  , '" + PLANTCONTACT + "' , '" + COMPNAME + "' , "+ image.toString() +" )";
                String plantMasterQuery = "INSERT INTO PLANT_MASTER ( `plantname`,`plantcode`,`gstno`,`plantaddress`,`plantemailaddress`,`plantweekoff`,`plantcpname`,`plantcpno`,`plantcpemail`,  `plantcontactno`, 'companyname' , 'complogo')  VALUES   ( ? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
                Connection con = DB_Operations.getPreparedStatement ();
                PreparedStatement pst = con.prepareStatement ( plantMasterQuery );

                pst.setString ( 1 , PlantNAME );
                pst.setString ( 2 , PLANTCODE );
                pst.setString ( 3 , GSTNO );
                pst.setString ( 4 , PLANTADDRESS );
                pst.setString ( 5 , PLANTEMAIL );
                pst.setString ( 6 , WEEKOFF );
                pst.setString ( 7 , CPNAME );
                pst.setString ( 8 , CPCONTACT );
                pst.setString ( 9 , CPEMAIL );
                pst.setString ( 10 , PLANTCONTACT );
                pst.setString ( 11 , COMPNAME );
                pst.setBytes ( 12 , image );

                newRecordId = DB_Operations.executeInsertRandom ( pst );

                try {
                    String addEmpAPICall = "plantadd?plantname=" + URLEncoder.encode ( PlantNAME , "UTF-8" ) + "&plantcode=" + URLEncoder.encode ( PLANTCODE , "UTF-8" ) + "&gstno=" + URLEncoder.encode ( GSTNO , "UTF-8" ) + "&plantaddress=" + URLEncoder.encode ( PLANTADDRESS , "UTF-8" ) + "&plantemailaddress=" + URLEncoder.encode ( PLANTEMAIL , "UTF-8" ) + "&plantweekoff=" + URLEncoder.encode ( WEEKOFF , "UTF-8" ) + "&plantcpname=" + URLEncoder.encode ( CPNAME , "UTF-8" ) + "&plantcpno=" + URLEncoder.encode ( CPCONTACT , "UTF-8" ) + "&plantcpemail=" + URLEncoder.encode ( CPEMAIL , "UTF-8" ) + "&plantcontactno=" + PLANTCONTACT + "&companyname=" + URLEncoder.encode ( COMPNAME , "UTF-8" ) + "&complogo=" + URLEncoder.encode ( image + "" , "UTF-8" );
                    String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
                    JOptionPane.showMessageDialog ( null , result2 );
                    
                    newRecordForAPI = iDFromJSON ( result2 ) ;
                    
                } catch ( UnsupportedEncodingException e ) {

                }

            } catch ( SQLException e ) {

            }

            String shiftQuery = "INSERT INTO shifts ( shiftno, shifttitle, shiftfromtime, shifttotime, breakduration, refplantid ) values ( ?,?,?,?,?,?  )";
            for ( int i = 0 ; i < shifts.size () ; i ++ ) {
                String[] shiftValues = shifts.get ( i );
                shiftQuery = "INSERT INTO shifts ( shiftno, shifttitle, shiftfromtime, shifttotime, breakduration, refplantid ) values ( " + ( i + 1 ) + ",'" + shifts.get ( i )[ 0 ] + "','" + shifts.get ( i )[ 1 ] + "','" + shifts.get ( i )[ 2 ] + "'," + Integer.parseInt ( shifts.get ( i )[ 3 ] ) + "," + newRecordId + "  )";
                DB_Operations.executeInsertRandom ( shiftQuery );

                try {
                    String addEmpAPICall = "shiftadd?shiftno=" + URLEncoder.encode ( ( i + 1 ) + "" , "UTF-8" ) + "&shifttitle=" + URLEncoder.encode ( shifts.get ( i )[ 0 ] + "" , "UTF-8" ) + "&shiftfromtime=" + URLEncoder.encode ( shifts.get ( i )[ 1 ] + "" , "UTF-8" ) + "&shifttotime=" + URLEncoder.encode ( shifts.get ( i )[ 2 ] + "" , "UTF-8" ) + "&breakduration=" + URLEncoder.encode ( Integer.parseInt ( shifts.get ( i )[ 3 ] ) + "" , "UTF-8" ) + "&refplantid=" + URLEncoder.encode ( newRecordForAPI + "" , "UTF-8" );
                    String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );

                } catch ( UnsupportedEncodingException e ) {

                }

            }

            loadContentPlant ();
            resetFields ();

        } else {

            JOptionPane.showMessageDialog ( null , "Please fill all field marked with * with appropriate values" );
        }

        if ( newRecordForAPI > 0 ) {
            JOptionPane.showMessageDialog ( null , "Successfully inserted new plant" );

        }
        //  }else{

        //                 JOptionPane.showMessageDialog ( null , "Total shift hours ( "+totalMinutes+" )exceeding maximum hours in a day " );
        //   }

    }//GEN-LAST:event_jButton3MouseClicked

    DefaultListModel shiftList = new DefaultListModel ();

        MouseListener m = new MouseListener () {
        @Override
        public void mouseClicked ( MouseEvent evt ) {

            DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();

            // get the selected row index
            int selectedRowIndex = jTable1.getSelectedRow ();

            selectedRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );

            // `plantid`, `companyname` , `plantname` as 'P Name', `plantcode` as 'Code', `gstno` as 'GST No.', `plantaddress` as 'Address', `plantemailaddress`  as 'Email'  , `plantcontactno`  as '<html>Plant Contact No<html>' , `plantweekoff` as 'Week Off', `plantcpname` as '<html>Contact Person Name</html>', `plantcpno` as '<html>CP Number</html>', `plantcpemail` as '<html>CP Email</html>', `shifts` as 'Shifts',  `complogo`
//        COMPNAME = 
            jTextField1.setText ( model.getValueAt ( selectedRowIndex , 11 ).toString () );
//        PlantNAME = 
            jTextField33.setText ( model.getValueAt ( selectedRowIndex , 1 ).toString () );
//        PLANTCODE =
            jTextField2.setText ( model.getValueAt ( selectedRowIndex , 2 ).toString () );
//        GSTNO = 
            jTextField3.setText ( model.getValueAt ( selectedRowIndex , 3 ).toString () );
//        PLANTADDRESS = 
            jTextArea2.setText ( model.getValueAt ( selectedRowIndex , 9 ).toString () );
//        PLANTEMAIL = 
            jTextField8.setText ( model.getValueAt ( selectedRowIndex , 4 ).toString () );
//        WEEKOFF = jComboBox1.getSelectedItem ().toString ();
//        CPNAME = 
            jTextField4.setText ( model.getValueAt ( selectedRowIndex , 6 ).toString () );
//        PLANTCONTACT = 
            jTextField7.setText ( model.getValueAt ( selectedRowIndex , 10 ).toString () );
//        CPEMAIL =
            jTextField6.setText ( model.getValueAt ( selectedRowIndex , 8 ).toString () );
//        CPCONTACT = jTextField5.getText ().trim ();
            jTextField5.setText ( model.getValueAt ( selectedRowIndex , 7 ).toString () );

            
         
        
        data2 = new Vector<Vector<Object>> ();
        String getShiftforPlant = "shifts?plant_id=" + selectedRecordId ;
        String shiftResult = WebAPITester.prepareWebCall ( getShiftforPlant , StaticValues.getHeader () , "" );

        if ( shiftResult != null && ! shiftResult.contains ( "not found" ) ) {

            HashMap<String , Object> map2 = new HashMap<String , Object> ();
            JSONObject jObject2 = new JSONObject ( shiftResult );
            Iterator<?> keys2 = jObject2.keys ();

            while ( keys2.hasNext () ) {
                String key = ( String ) keys2.next ();
                Object value = jObject2.get ( key );
                map2.put ( key , value );
            }

            JSONObject st2 = ( JSONObject ) map2.get ( "data" );
        JSONArray records2 = st2.getJSONArray ( "records" );
            JSONObject shifts = null;
             
            data2 = new Vector<Vector<Object>> ();
             
            for ( int i = 0 ; i < records2.length () ; i ++ ) {
            Vector<Object> vector2 = new Vector<Object> ();
                for ( int columnIndex = 2 ; columnIndex < columnNames2.size () ; columnIndex ++ ) {

                    shifts = records2.getJSONObject ( i );
                    vector2.add ( shifts.get ( columnNames2.get ( columnIndex ) ) );

                }
                data2.add ( vector2 );
            }
        }

       
        jTable2.setModel ( new DefaultTableModel ( data2 , columnNames4 ) );
        jTable2.getColumnModel ().getColumn ( 0 ).setMinWidth ( 0 );
        jTable2.getColumnModel ().getColumn ( 0 ).setMaxWidth ( 0 );

            Vector<Object> item = null;

            int frtm;
            String tempFtime=null;
            String fromtime = null;
            String fromtimemin=null;

            int totm;
            String tempTtime=null;
            String totime = null;
            String totimemin=null;
            
            String frmspinnerdata=null,tospinnerdata=null;

            for ( int i = 0 ; i < 4 ; i ++ ) {

                item = null;
                try {
                    item = data2.get ( i );
                    //tempFtime = new String[ 2 ];
                    tempFtime = item.get ( 2 ).toString ();
                    
                    /************************formated by mayur
                     fetch correct time format****************************/
                    SimpleDateFormat datehour=new SimpleDateFormat("HH");
                    SimpleDateFormat datemin=new SimpleDateFormat("mm");
                    SimpleDateFormat FormatYearTime=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                    Date dt=new Date();
                    try{
                    dt=FormatYearTime.parse(tempFtime);
                    }catch(ParseException ex){JOptionPane.showMessageDialog(null, "Date error ");}
                    frtm=Integer.parseInt(datehour.format(dt));
                    //fromtimemin[2]=datemin.format(dt);
                    if(frtm >12)
                    {
                        frtm=frtm-12;
                        
                        fromtime=String.valueOf(frtm);
                        fromtimemin=String.valueOf(datemin.format(dt));
                        frmspinnerdata="PM";
                    }
                    else
                    {
                    fromtime=String.valueOf(frtm);
                    fromtimemin=(datemin.format(dt));
                        frmspinnerdata="AM";
                    }
                    /* **********************************************/
                    
                    //fromtime = item.get ( 2 ).toString ().split ( ":" );
                    tempTtime = item.get ( 3 ).toString ();
                     /************************formated by mayur
                     fetch correct time format****************************/
                    
                    try{
                    dt=FormatYearTime.parse(tempTtime);
                    }catch(ParseException ex){JOptionPane.showMessageDialog(null, "Date error ");}
                    totm=Integer.parseInt(datehour.format(dt));
                    //totimemin[2]=datemin.format(dt);
                    if(totm >12)
                    {
                        totm=totm-12;
                        totime=String.valueOf(totm);
                        totimemin=(datemin.format(dt));
                        tospinnerdata="PM";
                    }
                    else
                    {
                        totime=String.valueOf(totm);
                        totimemin=(datemin.format(dt));
                        tospinnerdata="AM";
                    }
                    
                    /* **********************************************/
                    
                    //totime = item.get ( 3 ).toString ().split ( ":" );

                } catch ( Exception e ) {
                    item = null;
                    switch ( i ) {
                        case 0:
                            //11 12 13 16 17 30
                            jTextField11.setText ( "" );
                            jTextField12.setText ( "" );
                            jTextField13.setText ( "" );
                            jTextField16.setText ( "" );
                            jTextField17.setText ( "" );
                            jTextField30.setText ( "" );
                            break;

                        case 1:
                            // 28 14 15 18 19 31
                            jTextField28.setText ( "" );
                            jTextField14.setText ( "" );
                            jTextField15.setText ( "" );
                            jTextField18.setText ( "" );
                            jTextField19.setText ( "" );
                            jTextField31.setText ( "" );
                            break;

                        case 2:
                            // 29 20 21 22 23 32
                            jTextField29.setText ( "" );
                            jTextField20.setText ( "" );
                            jTextField21.setText ( "" );
                            jTextField22.setText ( "" );
                            jTextField23.setText ( "" );
                            jTextField32.setText ( "" );

                        case 3:

                            // 9 24 25 26 27 10
                            jTextField9.setText ( "" );
                            jTextField24.setText ( "" );
                            jTextField25.setText ( "" );
                            jTextField26.setText ( "" );
                            jTextField27.setText ( "" );
                            jTextField10.setText ( "" );

                            break;

                        default:

                    }
                }

                if ( item != null ) {
                    switch ( i ) {
                        case 0:
                            //11 12 13 16 17 30
                            jTextField11.setText ( item.get ( 1 ).toString () );
                            jTextField12.setText ( fromtime );
                            //change by mayur
                            jTextField13.setText ( fromtimemin );
                            //jSpinner8.setValue(spinnerdata);
                            jSpinner8.getModel().setValue(frmspinnerdata);
                            jSpinner2.getModel().setValue(tospinnerdata);
                            
                            jTextField16.setText ( totime );
                            jTextField17.setText ( totimemin );
                            
                             //************
                            jTextField30.setText ( item.get ( 4 ).toString () );

                            break;

                        case 1:
                            // 28 14 15 18 19 31
                            jTextField28.setText ( item.get ( 1 ).toString () );
                            jTextField14.setText ( fromtime );
                            //change by mayur
                            jTextField15.setText ( fromtimemin );
                            
                            jSpinner7.getModel().setValue(frmspinnerdata);
                            jSpinner1.getModel().setValue(tospinnerdata);
                            
                            jTextField18.setText ( totime );
                            jTextField19.setText ( totimemin );
                            //************
                            jTextField31.setText ( item.get ( 4 ).toString () );

                            break;

                        case 2:
                            // 29 20 21 22 23 32
                            jTextField29.setText ( item.get ( 1 ).toString () );
                            jTextField20.setText ( fromtime );
                            //change by mayur
                            jTextField21.setText ( fromtimemin );
                            
                            jSpinner6.getModel().setValue(frmspinnerdata);
                            jSpinner3.getModel().setValue(tospinnerdata);
                            
                            jTextField22.setText ( totime );
                            jTextField23.setText ( totimemin );
                            //************
                            jTextField32.setText ( item.get ( 4 ).toString () );

                            break;

                        case 3:
                            // 9 24 25 26 27 10
                            jTextField9.setText ( item.get ( 1 ).toString () );
                            jTextField24.setText ( fromtime );
                            //change by mayur
                            jTextField25.setText ( fromtimemin);
                            
                            jSpinner8.getModel().setValue(frmspinnerdata);
                            jSpinner2.getModel().setValue(tospinnerdata);
                            
                            
                            jTextField26.setText ( totime );
                            jTextField27.setText ( totimemin );
                            //************
                            jTextField10.setText ( item.get ( 4 ).toString () );

                            break;

                        default:
                    }
                } else {

                    switch ( i ) {
                        case 0:
                            //11 12 13 16 17 30
                            jTextField11.setText ( "" );
                            jTextField12.setText ( "" );
                            jTextField13.setText ( "" );
                            jTextField16.setText ( "" );
                            jTextField17.setText ( "" );
                            jTextField30.setText ( "" );
                            break;

                        case 1:
                            // 28 14 15 18 19 31
                            jTextField28.setText ( "" );
                            jTextField14.setText ( "" );
                            jTextField15.setText ( "" );
                            jTextField18.setText ( "" );
                            jTextField19.setText ( "" );
                            jTextField31.setText ( "" );
                            break;

                        case 2:
                            // 29 20 21 22 23 32
                            jTextField29.setText ( "" );
                            jTextField20.setText ( "" );
                            jTextField21.setText ( "" );
                            jTextField22.setText ( "" );
                            jTextField23.setText ( "" );
                            jTextField32.setText ( "" );

                        case 3:

                            // 9 24 25 26 27 10
                            jTextField9.setText ( "" );
                            jTextField24.setText ( "" );
                            jTextField25.setText ( "" );
                            jTextField26.setText ( "" );
                            jTextField27.setText ( "" );
                            jTextField10.setText ( "" );

                            break;

                        default:

                    }
                }
            }
        //***********edited by mayur 
            String addEmpAPICall = "plants";
         String result2 = WebAPITester.prepareWebCall ( addEmpAPICall , StaticValues.getHeader () , "" );
         
            buildTableModelfromJSON(result2, selectedRecordId);
        //**********************************
        }


        @Override
        public void mousePressed ( MouseEvent e ) {
        }

        @Override
        public void mouseReleased ( MouseEvent e ) {
        }

        @Override
        public void mouseEntered ( MouseEvent e ) {
        }

        @Override
        public void mouseExited ( MouseEvent e ) {
        }
    };
    
    MouseListener m2 = new MouseListener () {
        @Override
        public void mouseClicked ( MouseEvent evt ) {

            DefaultTableModel model = ( DefaultTableModel ) jTable2.getModel ();

            // get the selected row index
            int selectedRowIndex = jTable2.getSelectedRow ();

            int selectedShiftId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );



            Vector<Object> item = null;

            String fromtime[] = null;

            String totime[] = null;

            for ( int i = 0 ; i < 4 ; i ++ ) {

                item = null;
                try {
                    item = shiftData.get ( i );
                    fromtime = new String[ 2 ];
                    fromtime = item.get ( 2 ).toString ().split ( ":" );
                    totime = new String[ 2 ];
                    totime = item.get ( 3 ).toString ().split ( ":" );

                } catch ( ArrayIndexOutOfBoundsException e ) {
                    item = null;
                    switch ( i ) {
                        case 0:
                            //11 12 13 16 17 30
                            jTextField11.setText ( "" );
                            jTextField12.setText ( "" );
                            jTextField13.setText ( "" );
                            jTextField16.setText ( "" );
                            jTextField17.setText ( "" );
                            jTextField30.setText ( "" );
                            break;

                        case 1:
                            // 28 14 15 18 19 31
                            jTextField28.setText ( "" );
                            jTextField14.setText ( "" );
                            jTextField15.setText ( "" );
                            jTextField18.setText ( "" );
                            jTextField19.setText ( "" );
                            jTextField31.setText ( "" );
                            break;

                        case 2:
                            // 29 20 21 22 23 32
                            jTextField29.setText ( "" );
                            jTextField20.setText ( "" );
                            jTextField21.setText ( "" );
                            jTextField22.setText ( "" );
                            jTextField23.setText ( "" );
                            jTextField32.setText ( "" );

                        case 3:

                            // 9 24 25 26 27 10
                            jTextField9.setText ( "" );
                            jTextField24.setText ( "" );
                            jTextField25.setText ( "" );
                            jTextField26.setText ( "" );
                            jTextField27.setText ( "" );
                            jTextField10.setText ( "" );

                            break;

                        default:

                    }
                }

                if ( item != null ) {
                    switch ( i ) {
                        case 0:
                            //11 12 13 16 17 30
                            jTextField11.setText ( item.get ( 1 ).toString () );
                            jTextField12.setText ( fromtime[ 0 ] );
                            jTextField13.setText ( fromtime[ 1 ] );
                            jTextField16.setText ( totime[ 0 ] );
                            jTextField17.setText ( totime[ 1 ] );
                            jTextField30.setText ( item.get ( 4 ).toString () );

                            break;

                        case 1:
                            // 28 14 15 18 19 31
                            jTextField28.setText ( item.get ( 1 ).toString () );
                            jTextField14.setText ( fromtime[ 0 ] );
                            jTextField15.setText ( fromtime[ 1 ] );
                            jTextField18.setText ( totime[ 0 ] );
                            jTextField19.setText ( totime[ 1 ] );
                            jTextField31.setText ( item.get ( 4 ).toString () );

                            break;

                        case 2:
                            // 29 20 21 22 23 32
                            jTextField29.setText ( item.get ( 1 ).toString () );
                            jTextField20.setText ( fromtime[ 0 ] );
                            jTextField21.setText ( fromtime[ 1 ] );
                            jTextField22.setText ( totime[ 0 ] );
                            jTextField23.setText ( totime[ 0 ] );
                            jTextField32.setText ( item.get ( 4 ).toString () );

                            break;

                        case 3:
                            // 9 24 25 26 27 10
                            jTextField9.setText ( item.get ( 1 ).toString () );
                            jTextField24.setText ( fromtime[ 0 ] );
                            jTextField25.setText ( fromtime[ 1 ] );
                            jTextField26.setText ( totime[ 0 ] );
                            jTextField27.setText ( totime[ 1 ] );
                            jTextField10.setText ( item.get ( 4 ).toString () );

                            break;

                        default:
                    }
                } else {

                    switch ( i ) {
                        case 0:
                            //11 12 13 16 17 30
                            jTextField11.setText ( "" );
                            jTextField12.setText ( "" );
                            jTextField13.setText ( "" );
                            jTextField16.setText ( "" );
                            jTextField17.setText ( "" );
                            jTextField30.setText ( "" );
                            break;

                        case 1:
                            // 28 14 15 18 19 31
                            jTextField28.setText ( "" );
                            jTextField14.setText ( "" );
                            jTextField15.setText ( "" );
                            jTextField18.setText ( "" );
                            jTextField19.setText ( "" );
                            jTextField31.setText ( "" );
                            break;

                        case 2:
                            // 29 20 21 22 23 32
                            jTextField29.setText ( "" );
                            jTextField20.setText ( "" );
                            jTextField21.setText ( "" );
                            jTextField22.setText ( "" );
                            jTextField23.setText ( "" );
                            jTextField32.setText ( "" );

                        case 3:

                            // 9 24 25 26 27 10
                            jTextField9.setText ( "" );
                            jTextField24.setText ( "" );
                            jTextField25.setText ( "" );
                            jTextField26.setText ( "" );
                            jTextField27.setText ( "" );
                            jTextField10.setText ( "" );

                            break;

                        default:

                    }
                }
            }

        }

        @Override
        public void mousePressed ( MouseEvent e ) {
        }

        @Override
        public void mouseReleased ( MouseEvent e ) {
        }

        @Override
        public void mouseEntered ( MouseEvent e ) {
        }

        @Override
        public void mouseExited ( MouseEvent e ) {
        }
    };


    private void jTextField16FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField16FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16FocusLost

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16ActionPerformed

    private void jTextField16KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16KeyTyped

    public static byte[] serializeObject ( Object obj ) throws IOException {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream ();
        ObjectOutputStream oos = new ObjectOutputStream ( bytesOut );
        oos.writeObject ( obj );
        oos.flush ();
        byte[] bytes = bytesOut.toByteArray ();
        bytesOut.close ();
        oos.close ();
        return bytes;
    }

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        resetFields ();

    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser ();
        fileChooser.setFileFilter ( new MyCustomFilter ( "image" ) );
        fileChooser.setCurrentDirectory ( new File ( System.getProperty ( "user.home" ) ) );
        int result = fileChooser.showOpenDialog ( this );
        if ( result == JFileChooser.APPROVE_OPTION ) {
            File selectedFile = fileChooser.getSelectedFile ();
            //   System.out.println("Selected file: " + selectedFile.getAbsolutePath());

            BufferedImage image1;
            try {
                image1 = ImageIO.read ( new File ( selectedFile.getAbsolutePath () ) );

                BufferedImage inputImage = ImageIO.read ( selectedFile );

                BufferedImage outputImage = new BufferedImage ( jLabel27.getWidth () , jLabel27.getHeight () , inputImage.getType () );

                //  Image img = image1.getScaledInstance(jLabel23.getWidth(), jLabel23.getHeight(), Image.SCALE_SMOOTH);
                //  ImageIcon imageIcon = new ImageIcon(img);
                // scales the input image to the output image
                Graphics2D g2d = outputImage.createGraphics ();
                g2d.drawImage ( inputImage , 0 , 0 , jLabel27.getWidth () , jLabel27.getHeight () , null );
                g2d.dispose ();

                // extracts extension of output file
                String formatName = selectedFile.getAbsolutePath ().substring ( selectedFile.getAbsolutePath ().lastIndexOf ( "." ) + 1 );
                ImageIO.write ( outputImage , formatName , new File ( "new_Image_1.jpg" ) );

            } catch ( IOException ex ) {
                //Logger.getLogger(Employee_HR_Master.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println ( ex.getMessage () );
            }

            FileInputStream fis;
            ByteArrayOutputStream bos;
            try {
                //fis = new FileInputStream(selectedFile);
                fis = new FileInputStream ( new File ( "new_Image_1.jpg" ) );
                bos = new ByteArrayOutputStream ();
                byte[] buf = new byte[ 1024 ];

                for ( int readNum ; ( readNum = fis.read ( buf ) ) != -1 ; ) {
                    bos.write ( buf , 0 , readNum );
                }

                image = bos.toByteArray ();
                ImageIcon img = new ImageIcon ( image );
                jLabel27.setIcon ( img );

            } catch ( FileNotFoundException ex ) {
                Logger.getLogger ( Employee_HR_Master.class.getName () ).log ( Level.SEVERE , null , ex );
            } catch ( IOException ex ) {
                Logger.getLogger ( Employee_HR_Master.class.getName () ).log ( Level.SEVERE , null , ex );
            }

        }
    }//GEN-LAST:event_jButton6MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
 /*       DefaultTableModel model = ( DefaultTableModel ) jTable1.getModel ();

        // get the selected row index
        int selectedRowIndex = jTable1.getSelectedRow ();

        selectedRecordId = Integer.parseInt ( model.getValueAt ( selectedRowIndex , 0 ).toString () );

        // `plantid`, `companyname` , `plantname` as 'P Name', `plantcode` as 'Code', `gstno` as 'GST No.', `plantaddress` as 'Address', `plantemailaddress`  as 'Email'  , `plantcontactno`  as '<html>Plant Contact No<html>' , `plantweekoff` as 'Week Off', `plantcpname` as '<html>Contact Person Name</html>', `plantcpno` as '<html>CP Number</html>', `plantcpemail` as '<html>CP Email</html>', `shifts` as 'Shifts',  `complogo`
//        COMPNAME = 
        jTextField1.setText ( model.getValueAt ( selectedRowIndex , 1 ).toString () );
//        PlantNAME = 
        jTextField33.setText ( model.getValueAt ( selectedRowIndex , 2 ).toString () );
//        PLANTCODE =
        jTextField2.setText ( model.getValueAt ( selectedRowIndex , 3 ).toString () );
//        GSTNO = 
        jTextField3.setText ( model.getValueAt ( selectedRowIndex , 4 ).toString () );
//        PLANTADDRESS = 
        jTextArea2.setText ( model.getValueAt ( selectedRowIndex , 5 ).toString () );
//        PLANTEMAIL = 
        jTextField8.setText ( model.getValueAt ( selectedRowIndex , 6 ).toString () );
//        WEEKOFF = jComboBox1.getSelectedItem ().toString ();
//        CPNAME = 
        jTextField4.setText ( model.getValueAt ( selectedRowIndex , 9 ).toString () );
//        PLANTCONTACT = 
        jTextField7.setText ( model.getValueAt ( selectedRowIndex , 7 ).toString () );
//        CPEMAIL =
        jTextField6.setText ( model.getValueAt ( selectedRowIndex , 11 ).toString () );
//        CPCONTACT = jTextField5.getText ().trim ();
        jTextField5.setText ( model.getValueAt ( selectedRowIndex , 10 ).toString () );*/
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyPressed

    
    JProgressBar dpb = new JProgressBar(0, 500);
    JDialog dlg = null ;
    
    public void createDialog(){
        dlg = new JDialog(  HomeScreen.home  , "Deleting Plants and corresponding shifts", true);
        dlg.add(BorderLayout.CENTER, dpb);
        dlg.add(BorderLayout.NORTH, new JLabel("Deleting records..."));
        dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dlg.setSize(300, 200);
        dlg.setLocationRelativeTo(HomeScreen.home);
    }
    
    
    
    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {                                      
        
        int selection = JOptionPane.showConfirmDialog ( null , "Deleting a record is not recommendedThe 'Delete' action may cause loss of any dependencies and may affect the transactional Data and Reports and make data inconsistent\nDeleting a plant will automatically delete all shifts for that plant\nPress 'No' to revert, Press 'Yes' to continue deleting", "Are you sure about deleting this record ?", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE);
        if ( selection == JOptionPane.YES_OPTION ) { 
            //createDialog();
            //dlg.setVisible(true);
            deleteMaster(  "plantdelete?plantid="+selectedRecordId    ) ;    
            
            String response  = WebAPITester.prepareWebCall ( "shifts?plant_id="+selectedRecordId,  StaticValues.getHeader () , "");
            HashMap<String , Object> map = new HashMap<String , Object> ();
            JSONObject jObject = new JSONObject ( response );
            Iterator<?> keys = jObject.keys ();
            while ( keys.hasNext () ) {
                String key = ( String ) keys.next ();
                Object value = jObject.get ( key );
                map.put ( key , value );
            }
            JSONObject st = ( JSONObject ) map.get ( "data" );
            JSONArray records = st.getJSONArray ( "records" );
            JSONObject emp = null;

            int count =0 ;
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i );
                deleteShifts(  "shiftdelete?shiftid="+emp.get("shiftid")    ) ;  
                count++ ;
            }
            
            if(count>0){
                JOptionPane.showMessageDialog ( null , " "+count+" shifts deleted successfuly" ) ;
            }
            
            try {
                loadContentPlant ();
            } catch ( Exception e ) {

            }
            
            //dlg.setVisible(false);
        }
    }                                     

    public  void deleteMaster( String apiName  ){
        String response  = WebAPITester.prepareWebCall ( apiName,  StaticValues.getHeader () , "");
        JSONObject responseObject = ( JSONObject ) new JSONTokener ( response ).nextValue ();
        JOptionPane.showMessageDialog ( null , retreiveSuccessMessage ( responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () ) );
    }
    
     public static  String retreiveSuccessMessage( String json){
        String id = "Not able to fetch the response for your request. Please refresh you master form and try again";
        JSONObject jObject = new JSONObject ( json );
        id = jObject.getString("118" );
        return id ;
    }

     public  String deleteShifts( String apiName  ){
        String response  = WebAPITester.prepareWebCall ( apiName,  StaticValues.getHeader () , "");
        JSONObject responseObject = ( JSONObject ) new JSONTokener ( response ).nextValue ();
        //JOptionPane.showMessageDialog ( null , retreiveSuccessMessage ( responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () ) );
        return retreiveSuccessMessage ( responseObject.getJSONObject ( "message" ).getJSONObject ( "success" ).toString () ) ;
    }
    
    
    byte[] image = null;
    public void resetFields () {

        jTextField1.setText ( "" );
        jTextField2.setText ( "" );
        jTextField3.setText ( "" );
        jTextArea2.setText ( "" );
        jTextField8.setText ( "" );
        jTextField4.setText ( "" );
        jTextField7.setText ( "" );
        jTextField6.setText ( "" );
        jTextField5.setText ( "" );
        jTextField12.setText ( "0" );
        jTextField13.setText ( "0" );
        jTextField16.setText ( "0" );
        jTextField17.setText ( "0" );
        jTextField14.setText ( "0" );
        jTextField15.setText ( "0" );
        jTextField18.setText ( "0" );
        jTextField19.setText ( "0" );
        jTextField21.setText ( "0" );
        jTextField20.setText ( "0" );
        jTextField22.setText ( "0" );
        jTextField23.setText ( "0" );
        jTextField24.setText ( "0" );
        jTextField25.setText ( "0" );
        jTextField26.setText ( "0" );
        jTextField27.setText ( "0" );
        jTextField11.setText ( "" );
        jTextField28.setText ( "" );
        jTextField31.setText ( "" );
        jTextField30.setText ( "" );
        jTextField33.setText ( "" );
        jTextField29.setText ( "" );
        jTextField32.setText ( "" );
    }
    public void resetShiftsForms () {

        //11 12 13 16 17 30
        jTextField11.setText ( "" );
        jTextField12.setText ( "" );
        jTextField13.setText ( "" );
        jTextField16.setText ( "" );
        jTextField17.setText ( "" );
        jTextField30.setText ( "" );

        // 28 14 15 18 19 31
        jTextField28.setText ( "" );
        jTextField14.setText ( "" );
        jTextField15.setText ( "" );
        jTextField18.setText ( "" );
        jTextField19.setText ( "" );
        jTextField31.setText ( "" );

        // 29 20 21 22 23 32
        jTextField29.setText ( "" );
        jTextField20.setText ( "" );
        jTextField21.setText ( "" );
        jTextField22.setText ( "" );
        jTextField23.setText ( "" );
        jTextField32.setText ( "" );

        // 9 24 25 26 27 10
        jTextField9.setText ( "" );
        jTextField24.setText ( "" );
        jTextField25.setText ( "" );
        jTextField26.setText ( "" );
        jTextField27.setText ( "" );
        jTextField10.setText ( "" );

    }
    int selectedRecordId = 0;
    class Shifts {
        String shifTitle, fromTime, toTime;
        int breakduration, shiftno;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JSpinner jSpinner4;
    private javax.swing.JSpinner jSpinner5;
    private javax.swing.JSpinner jSpinner6;
    private javax.swing.JSpinner jSpinner7;
    private javax.swing.JSpinner jSpinner8;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
//>>>>>>> login   
