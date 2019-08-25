/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

import trixware.erp.prodezydesktop.Model.DepartmentDR;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Rajesh
 */
//public class RoleMultiSelect {
    
//}
public class RolesMultiSelect extends JPanel {

        private JPanel panel;
        private JScrollPane scroll;
        ArrayList<DepartmentDR> departments = null;
        public ArrayList<RoleSelectionPanel> rolesList = null ;
        
        public RolesMultiSelect (  ) {

        }
        
        
        public JScrollPane  getRolesMultiSelectList(  String rs  ){
            
            setLayout ( new BorderLayout () );

            
             HashMap<String, Object> map = new HashMap<String, Object>();
                    JSONObject jObject = new JSONObject( rs );
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

            RoleSelectionPanel pane = null;
            rolesList = new ArrayList<RoleSelectionPanel>();
            
            
            try {

                for ( int i = 0 ; i < records.length () ; i ++ ) {
                    emp = records.getJSONObject ( i);
                    pane = new RoleSelectionPanel ();
                    pane.setDeptId (Integer.parseInt( emp.get ( "Role_ID" ).toString () ) );
                    pane.setDepartmentName (emp.get ( "RoleName" ).toString () );
                    pane.setSelection (  false  );

                    panel.add ( pane );
                    panel.revalidate ();
                    rolesList.add(pane);
                }
                
            }catch ( IndexOutOfBoundsException e ) {
                System.out.println ( " Error 3 " + e.getMessage () );
            }
            
            return scroll ;
        }
        
//        
//        public JScrollPane  getRolesMultiSelectList_OLD(  ResultSet rs  ){
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
//            RoleSelectionPanel pane = null;
//            rolesList = new ArrayList<RoleSelectionPanel>();
//            try {
//
//                while ( rs.next () ) {
//                    pane = new RoleSelectionPanel ();
//                    pane.setDeptId (rs.getInt("Role_ID") );
//                    pane.setDepartmentName (rs.getString("RoleName") );
//                    pane.setSelection (  false  );
//
//                    panel.add ( pane );
//                    panel.revalidate ();
//                    rolesList.add(pane);
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
//        
    }
