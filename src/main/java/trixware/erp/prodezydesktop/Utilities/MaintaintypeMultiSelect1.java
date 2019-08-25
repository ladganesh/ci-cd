/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

import trixware.erp.prodezydesktop.Model.Maintain_typeDR;
import java.awt.BorderLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Utilities.TypeSelectionPanel;

/**
 *
 * @author Rajesh
 */
public class MaintaintypeMultiSelect1 extends JPanel {

        private JPanel panel;
        private JScrollPane scroll;
        public ArrayList<TypeSelectionPanel> maintainhistoryList = null ;

        ArrayList<Maintain_typeDR> type = null;
        
        public MaintaintypeMultiSelect1 (  ) {
        }
        
        
        public JScrollPane gettypeMultiSelectList(  String JSON  ){
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

            add ( scroll , BorderLayout.CENTER );

            setVisible ( true );

            TypeSelectionPanel pane = null;
            maintainhistoryList = new ArrayList<TypeSelectionPanel>();
            try {

                for ( int i = 0 ; i < records.length () ; i++ ) {
                    emp = records.getJSONObject ( i);
                    pane = new TypeSelectionPanel ();
                    pane.settypeId ( Integer.parseInt( emp.get ( "MT_ID" ).toString ()));
                    pane.settypeName (  emp.get ( "activity" ).toString ()  );
                    pane.setSelection (  false  );
                   // pane.setQty();
                    
                    panel.add ( pane );
                    panel.revalidate ();
                    maintainhistoryList.add( pane );
                }
            }  catch ( IndexOutOfBoundsException e ) {
                System.out.println ( " Error 3 " + e.getMessage () );
            }
            
            return scroll ;
        } 
    }