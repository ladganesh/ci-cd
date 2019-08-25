/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Rajesh
 */
public class PermissionMultiSelect extends JPanel {

        private JPanel panel;
        private JScrollPane scroll;
        public ArrayList<PermissionSelectionPanel> permissionsList = null ;

        public PermissionMultiSelect (  ) {

        }
        
        public JScrollPane getPermissionMultiSelectList(    ){
            setLayout ( new BorderLayout () );

            panel = new JPanel ();
            panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
            scroll = new JScrollPane ( panel ,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );

            add ( scroll , BorderLayout.CENTER );

            setVisible ( true );

            PermissionSelectionPanel pane = null;
            permissionsList = new ArrayList<PermissionSelectionPanel>();
            

                pane = new PermissionSelectionPanel ();
                pane.setDeptId ( 1 );
                pane.setDepartmentName (  "Read" ); 
                pane.setSelection (  false  );
                panel.add ( pane );
                panel.revalidate ();
                permissionsList.add( pane );

                pane = new PermissionSelectionPanel ();
                pane.setDeptId ( 2 );
                pane.setDepartmentName (  "Edit" ); 
                pane.setSelection (  false  );
                panel.add ( pane );
                panel.revalidate ();
                permissionsList.add( pane );
                
                pane = new PermissionSelectionPanel ();
                pane.setDeptId ( 3 );
                pane.setDepartmentName (  "Disable" );
                pane.setSelection (  false  );
                panel.add ( pane );panel.revalidate ();
                permissionsList.add( pane );
                
                pane = new PermissionSelectionPanel ();
                pane.setDeptId ( 4 );
                pane.setDepartmentName (  "Export" );
                pane.setSelection (  false  );
                panel.add ( pane );
                panel.revalidate ();
                permissionsList.add( pane );

                pane = new PermissionSelectionPanel ();
                pane.setDeptId ( 5 );
                pane.setDepartmentName (  "Add / Save" ); 
                pane.setSelection (  false  );
                panel.add ( pane );
                panel.revalidate ();
                permissionsList.add( pane );

                pane = new PermissionSelectionPanel ();
                pane.setDeptId ( 6 );
                pane.setDepartmentName (  "Import from file" ); 
                pane.setSelection (  false  );
                panel.add ( pane );
                panel.revalidate ();
                permissionsList.add( pane );

                pane = new PermissionSelectionPanel ();
                pane.setDeptId ( 7 );
                pane.setDepartmentName (  "Access from mobile app" );
                pane.setSelection (  false  );
                panel.add ( pane );
                panel.revalidate ();
                permissionsList.add( pane );
                
//                pane = new PermissionSelectionPanel ();
//                pane.setDeptId ( 8 );
//                pane.setDepartmentName (  "Export to PDF" );
//                pane.setSelection (  false  );
//                panel.add ( pane );
//                panel.revalidate ();
//                permissionsList.add( pane );
//            
            
            return scroll ;
        }
        
        
    }