/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.examples;

import java.util.HashMap;
import java.util.Iterator;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.StaticValues;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author Rajesh
 */
public class AllDocumentsTypeWise extends javax.swing.JPanel {

    /**
     * Creates new form AllDocumentsTypeWise
     */
    public AllDocumentsTypeWise () {
        initComponents ();
        
        // Masters against which the documents types are available
        // All document Types
        // General documents types are available
        // All document Types
        // List of Documents for each type
        
        DefaultMutableTreeNode level0, level1, level2, leve3 ;
        
        level0 = new DefaultMutableTreeNode("List of all documents available on servers ");
        
        
        String addEmpAPICall = "rdm";
        String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");            
        if( !  result2.contains(  "not found"   ) ){
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
            for ( int i = 0 ; i < records.length () ; i ++ ) {
                emp = records.getJSONObject ( i);
                level1 = new DefaultMutableTreeNode(  emp.get("RDM_NAME").toString());
                
                
                String addEmpAPICall1 = "refdocs?RDM_UID="+emp.get ("RDM_UID").toString()  ;
                String result1 =  WebAPITester.prepareWebCall ( addEmpAPICall1, StaticValues.getHeader ()   , "");
                
                if( !  result1.contains(  "not found"   ) ){
                    HashMap<String, Object> map1 = new HashMap<String, Object>();
                    JSONObject jObject1 = new JSONObject( result1 );
                    Iterator<?> keys1 = jObject1.keys();
                    while( keys1.hasNext() ){
                        String key = (String)keys1.next();
                        Object value = jObject1.get ( key ) ; 
                        map1.put(key, value);
                    }
                    JSONObject st1 = (JSONObject) map1.get ( "data" );
                    JSONArray records1 = st1.getJSONArray ( "records");
           
                    JSONObject emp1 = null;
                    for ( int j = 0 ; j < records1.length () ; j ++ ) {

                        emp1 = records1.getJSONObject ( j );
                        try{
                            level2 = new DefaultMutableTreeNode(  emp1.get("RD_NAME").toString() );
                            level1.add( level2 );
                        }catch(  JSONException e   ){
                            level2 = new DefaultMutableTreeNode(  "No Available");
                            level1.add( level2 );
                        }
                    }
                    level0.add ( level1 );
                }
            }
        }
        
        TreeModel tm = new DefaultTreeModel(  level0 ) ;
        jTree1.setModel( tm );
        
/*        String[] docTypeIdsArr = docTypeIds.split(",") ;
        DefaultListModel<String> docsList = null ;
        for( int i=0; i<docTypeIdsArr.length; i++ ){
            
            if( ! docTypeIdsArr[i].equals( "" ) ){
                addEmpAPICall = "refdocs?RDM_UID="+docTypeIdsArr[i];
                result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                
                if( !  result2.contains(  "not found"   ) ){
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
                    //String[] docTypeIds = new String[];

                    JSONObject emp = null;
                    //docList = new ArrayList<String[]> ();
                    docsList = new DefaultListModel<String>() ;
                    for ( int j = 0 ; j < records.length () ; j ++ ) {

                        emp = records.getJSONObject ( j );
                        //docList.add( new String[]{ emp.get ("RD_UID").toString(),emp.get("RD_NAME").toString()  }   );
                        docsList.addElement( emp.getString ("RD_NAME")  );
                    }
                }
            }
        }*/
        
        
        //DefaultMutableTreeNode top = new DefaultMutableTreeNode("The Java Series");
        //createNodes(top);
        //TreeModel tm = new DefaultTreeModel(  top ) ;
        //jTree1.setModel( tm );
    }
 
    
    private void createNodes(DefaultMutableTreeNode top) {
    DefaultMutableTreeNode category = null;
    DefaultMutableTreeNode book = null;
    
    category = new DefaultMutableTreeNode("Books for Java Programmers");
    top.add(category);
    
    //original Tutorial
    book = new DefaultMutableTreeNode(new BookInfo
        ("The Java Tutorial: A Short Course on the Basics",
        "tutorial.html"));
    category.add(book);
    
    //Tutorial Continued
    book = new DefaultMutableTreeNode(new BookInfo
        ("The Java Tutorial Continued: The Rest of the JDK",
        "tutorialcont.html"));
    category.add(book);
    
    //Swing Tutorial
    book = new DefaultMutableTreeNode(new BookInfo
        ("The Swing Tutorial: A Guide to Constructing GUIs",
        "swingtutorial.html"));
    category.add(book);

    //...add more books for programmers...

    category = new DefaultMutableTreeNode("Books for Java Implementers");
    top.add(category);

    //VM
    book = new DefaultMutableTreeNode(new BookInfo
        ("The Java Virtual Machine Specification",
         "vm.html"));
    category.add(book);

    //Language Spec
    book = new DefaultMutableTreeNode(new BookInfo
        ("The Java Language Specification",
         "jls.html"));
    category.add(book);
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
        jTree1 = new javax.swing.JTree();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(null);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(500, 400));
        jScrollPane1.setViewportView(jTree1);

        add(jScrollPane1);
        jScrollPane1.setBounds(10, 10, 500, 400);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables

    
    class BookInfo{

        String one, two ;
        
        public BookInfo () {
        }

        public BookInfo ( String one , String two ) {
            this.one = one;
            this.two = two;
        }
        
        
        
    }

}

