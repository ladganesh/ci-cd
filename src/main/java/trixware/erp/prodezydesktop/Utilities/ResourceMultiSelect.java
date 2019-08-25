/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import trixware.erp.prodezydesktop.Utilities.ResoureSelectionPanel;

/**
 *
 * @author Rajesh
 */
public class ResourceMultiSelect extends JPanel {

    private JPanel panel;
    private JScrollPane scroll;
    private JCheckBox jcbSelectAll;
    public ArrayList<ResoureSelectionPanel> resourcessList = null;

    public ResourceMultiSelect () {

    }

    public JScrollPane getResourcesMultiSelectList ( ) {
        setLayout ( new BorderLayout () );

        panel = new JPanel ();
        panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
        scroll = new JScrollPane ( panel ,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );

        jcbSelectAll = new JCheckBox ();
        jcbSelectAll.setActionCommand ( "Select All" );
        jcbSelectAll.setBounds ( 0 , 0 , 100 , 15 );
       // add ( jcbSelectAll );
        add ( scroll , BorderLayout.CENTER );

        setVisible ( true );

        ResoureSelectionPanel pane = null;
        resourcessList = new ArrayList<ResoureSelectionPanel> ();

        pane = new ResoureSelectionPanel ();
        pane.setDeptId ( 1 );
        pane.setDepartmentName ( "Masters" );
        pane.setSelection ( false );
        panel.add ( pane );
        panel.revalidate ();
        resourcessList.add ( pane );

        pane = new ResoureSelectionPanel ();
        pane.setDeptId ( 2 );
        pane.setDepartmentName ( "Inventory" );
        pane.setSelection ( false );
        panel.add ( pane );
        panel.revalidate ();
        resourcessList.add ( pane );

        pane = new ResoureSelectionPanel ();
        pane.setDeptId ( 3 );
        pane.setDepartmentName ( "Sales" );
        pane.setSelection ( false );
        panel.add ( pane );
        panel.revalidate ();
        resourcessList.add ( pane );

        pane = new ResoureSelectionPanel ();
        pane.setDeptId ( 4 );
        pane.setDepartmentName ( "Production" );
        pane.setSelection ( false );
        panel.add ( pane );
        panel.revalidate ();
        resourcessList.add ( pane );

        pane = new ResoureSelectionPanel ();
        pane.setDeptId ( 5 );
        pane.setDepartmentName ( "Reports" );
        pane.setSelection ( false );
        panel.add ( pane );
        panel.revalidate ();
        resourcessList.add ( pane );

        pane = new ResoureSelectionPanel ();
        pane.setDeptId ( 6 );
        pane.setDepartmentName ( "Documents" );
        pane.setSelection ( false );
        panel.add ( pane );
        panel.revalidate ();
        resourcessList.add ( pane );

        pane = new ResoureSelectionPanel ();
        pane.setDeptId ( 7 );
        pane.setDepartmentName ( "Dashboard" );
        pane.setSelection ( false );
        panel.add ( pane );
        panel.revalidate ();
        resourcessList.add ( pane );

        return scroll;
    }

    MouseListener click1 = new MouseListener () {
        @Override
        public void mouseClicked ( MouseEvent e ) {
            if ( jcbSelectAll.isSelected () ) {
                for ( int i = 0 ; i < resourcessList.size () ; i ++ ) {
                    if ( resourcessList.get ( i ).isSelected ) {
                        resourcessList.get ( i ).setSelection ( true );
                    }
                }
            } else {
                for ( int i = 0 ; i < resourcessList.size () ; i ++ ) {
                    if ( resourcessList.get ( i ).isSelected ) {
                        resourcessList.get ( i ).setSelection ( false );
                    }
                }
            }

            revalidate ();
            repaint ();
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

}
