/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

import trixware.erp.prodezydesktop.Model.DepartmentDR;
import java.awt.BorderLayout;
import java.awt.Dimension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.json.JSONArray;
import org.json.JSONObject;

    import trixware.erp.prodezydesktop.Utilities.DeptSelectionPanel ;
        

/**
 *
 * @author Rajesh
 */
public class DepartmentMultiSelect extends JPanel {

        private JPanel panel;
        private JScrollPane scroll;
        public ArrayList<DeptSelectionPanel> departmentsList = null ;

        ArrayList<DepartmentDR> departments = null;
        
        public DepartmentMultiSelect (  ) {
        }
        
        
        public JScrollPane getDeptMultiSelectList(  String JSON  ){
            setLayout ( new BorderLayout () );

            
            HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jObject = new JSONObject( JSON );
                    Iterator<?> keys = jObject.keys();

                    while( keys.hasNext() ){
                        String key = (String)keys.next();
                        Object value = jObject.get ( key ) ; 
                        map.put(key, value);
                    }
                    
                    JSONObject st = (JSONObject) map.get ( "data" );
                    JSONArray records = st.getJSONArray ( "records");

                    JSONObject emp = null;

            
            panel = new JPanel ();
            panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
            scroll = new JScrollPane ( panel ,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
            scroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));

            add ( scroll , BorderLayout.CENTER );

            setVisible ( true );

            DeptSelectionPanel pane = null;
            departmentsList = new ArrayList<DeptSelectionPanel>();
            try {

                for ( int i = 0 ; i < records.length () ; i ++ ) {
                    emp = records.getJSONObject ( i);
                    pane = new DeptSelectionPanel ();
                    pane.setDeptId ( Integer.parseInt( emp.get ( "DID" ).toString ()));
                    pane.setDepartmentName (  emp.get ( "DNAME" ).toString ()  );
                    pane.setSelection (  false  );
                    
                    panel.add ( pane );
                    panel.revalidate ();
                    departmentsList.add( pane );
                }
            }  catch ( IndexOutOfBoundsException e ) {
                System.out.println ( " Error 3 " + e.getMessage () );
            }
            
            return scroll ;
        }
        
//        public JScrollPane getDeptMultiSelectList_OLD(  ResultSet rs  ){
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
//            DeptSelectionPanel pane = null;
//            departmentsList = new ArrayList<DeptSelectionPanel>();
//            try {
//
//                while ( rs.next () ) {
//                    pane = new DeptSelectionPanel ();
//                    pane.setDeptId ( rs.getInt("DID") );
//                    pane.setDepartmentName (  rs.getString("DNAME") );
//                    pane.setSelection (  false  );
//                    
//                    panel.add ( pane );
//                    panel.revalidate ();
//                    departmentsList.add( pane );
//                }
//                
//                rs.close ();
//            } catch ( SQLException e ) {
//                System.out.println ( " Error 2 " + e.getMessage () );
//            } catch ( IndexOutOfBoundsException e ) {
//                System.out.println ( " Error 3 " + e.getMessage () );
//            }
//            
//            return scroll ;
//        }
        
        
        
    }