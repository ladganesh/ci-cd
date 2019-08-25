/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

import trixware.erp.prodezydesktop.components.ProductforCustPanel;
import trixware.erp.prodezydesktop.examples.SalesForm_New;
import trixware.erp.prodezydesktop.Model.Product_CustomerModel ;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @author dell
 */
public class ProductForCustomer extends javax.swing.JPanel {
    
//ProductforCustPanel pane = new ProductforCustPanel();

        ResultSet result = null;

        public ArrayList<ProductforCustPanel> ProductTypeList  = null ;
        private ArrayList<Product_CustomerModel> ProductCustDetailList  = null ;

        ArrayList<Product_CustomerModel> selectedmaintain_type = new ArrayList<Product_CustomerModel>() ;
        
        ArrayList<String > prod_ids= new ArrayList<String>();
         
        String load_Cust_prod,result_for_cust_prod;
        int selected_cust_id;
    
        ArrayList<ProductDR> products ;
       
        ProductforCustPanel pane;
        /**
         * Creates new form ProdDataConformationScreen
         */
        public ProductForCustomer (int cust_id) {
            initComponents ();
            selected_cust_id =cust_id;
            ProductTypeList panel = new ProductTypeList (selected_cust_id);
            panel.setBounds ( 0 , 0 , 315 , 260 );
            panel.setBackground ( Color.WHITE);
            add ( panel );
            
        }

        @SuppressWarnings( "unchecked" )
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents () {

            jButton1 = new javax.swing.JButton ();
            jButton2 = new javax.swing.JButton ();
            jLabel1 = new javax.swing.JLabel ();

            setBackground ( new java.awt.Color ( 255 , 255 , 255 ) );
            setBorder ( javax.swing.BorderFactory.createTitledBorder ( "" ) );
            setPreferredSize ( new java.awt.Dimension ( 260 , 260 ) );
            setLayout ( null );

            
        }// </editor-fold>                        

        public ArrayList<Product_CustomerModel> getMaintain_type ( ) {
            // TODO add your handling code here:
            Product_CustomerModel tlm = null;

            float totalmaintainCost  = 0 ;
            ProductCustDetailList = new ArrayList<Product_CustomerModel> () ;
            for ( int i = 0 ; i < ProductTypeList.size () ; i ++ ) {

                tlm = new Product_CustomerModel();
                if ( ProductTypeList.get ( i ).getTlQtyvalue()> 0 ) {
                    tlm.setProd_typeid(ProductTypeList.get ( i ).getProd_typeid());
                    tlm .setProd_nm(ProductTypeList.get ( i ).getProd_nm() );
                    tlm.setTlQtyvalue(ProductTypeList.get ( i ).getTlQtyvalue () );
                    tlm.setTotalQty(ProductTypeList.get ( i ).getTotalQty());
                    tlm.setProd_size(ProductTypeList.get(i).getProd_size());
                    
                    totalmaintainCost = totalmaintainCost + ProductTypeList.get ( i ).getTlQtyvalue();
                    
                    //lm.setReason ( rejectionList.get ( i ).getReasonStr () );
                   ProductCustDetailList.add ( tlm );
                }
            }
            
            
           // if(  totalmaintainCost == Integer.parseInt( tcm.getText().toString()) ){
                Product_CustomerModel tlm2 = null;
                StringBuilder sb = new StringBuilder ();
                for ( int i = 0 ; i < ProductCustDetailList.size () ; i ++ ) {
                    tlm2 = ProductCustDetailList.get ( i );

                    sb.append ( tlm2.getTlQtyvalue ());
                    sb.append ( tlm2.getProd_typeid());
                    sb.append ( tlm2.getProd_nm());
                    sb.append ( tlm2.getProd_size());
                    sb.append ( "\n" );
                }

                selectedmaintain_type= ProductCustDetailList;
                //setrejections (rejectionDetailList );
                
                System.out.println ( "filled maintenance activities size = "+ProductCustDetailList.size()+""); 
                
            //}else{
                
             //   JOptionPane.showMessageDialog (  null, "Total cost maintenance cannot be greater than "+ Integer.parseInt( tcm.getText() ) );
           // }
            
            return selectedmaintain_type ;
        }

        public void reset(){
            
             for ( int i = 0 ; i < ProductTypeList.size () ; i ++ ) {

                if ( ProductTypeList.get ( i ).getTlQtyvalue()> 0 ) {
                    ProductTypeList.get ( i ).setTlQtyvalue ( 0 ) ;
                }
            }
            
        }
        
        
        // Variables declaration - do not modify                     
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        // End of variables declaration                   

        class ProductTypeList extends JPanel {

            private JPanel panel;
            private JScrollPane scroll;
            private JButton btnAddType;
            
            ProductForCustomer prod_cust = null ;
            
           

            public ProductTypeList (int cust_id) {
                
                selected_cust_id=cust_id;

                setLayout ( new BorderLayout () );
                panel = new JPanel ();
                panel.setLayout ( new BoxLayout ( panel , BoxLayout.Y_AXIS ) );
                //panel.setLayout ( null );
                scroll = new JScrollPane ( panel ,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
                add ( scroll , BorderLayout.CENTER );
                setVisible ( true );
               // ProductforCustPanel pane = null;

                if( ProductTypeList == null  )
                {
                    
                    ProductTypeList = new ArrayList<ProductforCustPanel> ();
                    
                     //prod_ids.clear();
                //selected_cust_id =  Integer.parseInt(customerCombo.get ( jComboBox2.getSelectedIndex () )[0]);
        
                //load_data_prodCust(selected_cust_id);
                try
                {
                if(panel != null)
                {
                //pane.removeAll();
                panel.removeAll();
                }
                }catch(Exception e)
                {
                    e.getMessage();
                }
                
//                 if(products.size()!=0 && prod_ids.size()!=0)
//                {
//                    prod_cust.removeAll();
//                }
            prod_ids.clear();
            
        load_Cust_prod = "customermaps?CUSTOMER_ID="+selected_cust_id;
        result_for_cust_prod = WebAPITester.prepareWebCall ( load_Cust_prod , StaticValues.getHeader () , "" );
        
        if( !  result_for_cust_prod.contains( "not found"  )  ){
                    HashMap<String , Object> map = new HashMap<String , Object> ();
                    JSONObject jObject = new JSONObject ( result_for_cust_prod );
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

                        emp = records.getJSONObject ( i );
                        //jComboBox2.addItem ( emp.get ( "" ).toString() +" ( "+emp.get ("CUSTOMER_NAME" ).toString() +" )" );
                        //customerCombo.add ( new String[] { emp.get ( "CUSTOMER_ID" ).toString () , emp.get ( "PLANT_CODE" ).toString() +" ( "+emp.get ("CUSTOMER_NAME" ).toString() +" )" } );
                        prod_ids.add((emp.get("M_FG_ID")).toString());
                    }
                }
                 
        
                if(prod_ids.size()==0 )
                {
                    if(products.size()==0)
                    {
                        System.out.print("Product data is already null");
                        JOptionPane.showMessageDialog(null, "finish goods data not found");
                    }
                    else
                    {
                        products.clear();
                        prod_cust.removeAll();
                         //prod_cust=null;
                        JOptionPane.showMessageDialog(null, "finish goods data not found");
                    }
                }
                else
                {
                    
                    //final Container parent=ProductForCustomer.this.getParent();
//                    if(ProductTypeList.size()!=0)
//                    {
                    //parent.remove(ProductForCustomer.this);
                    //parent.repaint();
                   
                    //}
                    //prod_cust.removeAll();
                    products = new ArrayList<ProductDR> ();
                    for(int j=0;j<prod_ids.size();j++)
                    {
                        load_Cust_prod = "finishedgoodsedit?FG_ID="+prod_ids.get(j);
                        result_for_cust_prod = WebAPITester.prepareWebCall ( load_Cust_prod , StaticValues.getHeader () , "" );
                        try
                        {
                            if( !  result_for_cust_prod.contains( "not found"  )  )
                            {
                       
                                JSONObject jsobje = new JSONObject(result_for_cust_prod);
                                String page11 = jsobje.getJSONArray("data").toString();
                                JSONArray machinejsarray = new JSONArray(page11);
                                
                                
                                panel.setBounds(0,0,170,500);
                                //panel.setBounds(0,0,170,36*products.size());
                                
                                ProductDR pro =new ProductDR();
                                for (int i = 0; i < machinejsarray.length(); i++) 
                                {

                                    JSONObject jsonobject = machinejsarray.getJSONObject(i);
                     
                     
                                     pro.setFG_ID(jsonobject.optInt("FG_ID"));
                                     pro.setFG_PART_NO(jsonobject.optString("FG_PART_NO"));
                                     pro.setUOM(jsonobject.optString("UOM"));
                    
                                    products.add(pro);
                                    
                                    
                                    pane = new ProductforCustPanel();
                              
                                    pane.setBounds(0,(i*40),315,36);
                                    pane.setProd_typeid(jsonobject.optInt("FG_ID"));
                                    pane.setProd_nm( jsonobject.optString("FG_PART_NO"));
                                    pane.setProd_size(jsonobject.optString("UOM"));
                                    pane.setTlQtyvalue(0); 
                                    pane.setTotalQty(0 );
                                    pane.revalidate ();
                                    pane.repaint();
                                    panel.add ( pane );
                                    ProductTypeList.add ( pane );
                                    panel.revalidate ();
                                    panel.repaint();
                                    
                                }
                            //prod_cust=null;
                            //prod_cust.removeAll();
                            }
                        }catch(Exception e)
                        {e.getMessage(); } 
                    }
           //prod_cust=null;
           
//           prod_cust.removeAll();
           //prod_cust.revalidate();
           //prod_cust.repaint();
           
                }
                    

//                        JSONObject emp = null;
                       // products = new ArrayList<ProductDR> ();
                        //panel.setBounds(0,0,170,500);
                        //panel.setBounds(0,0,170,36*products.size());
                        
                    //for ( int i = 0 ; i < products.size() ; i ++ ) {
                            //emp = records.getJSONObject ( i);
                            //products.add( new ProductDR ( Integer.parseInt(emp.get ( "FG_ID" ).toString ()), emp.get ( "FG_PART_NO" ).toString (),emp.get ( "UOM" ).toString ()    )); 
                            //ProductDR pro=new ProductDR();
//                            pane = new ProductforCustPanel();
//                            pane.setBounds(0,(i*40),315,36);
//                            pane.setProd_typeid(products.get(i).FG_ID);
//                            pane.setProd_nm( products.get(i).FG_PART_NO);
//                            pane.setProd_size(products.get(i).UOM);
//                            pane.setTlQtyvalue(0); 
//                            pane.setTotalQty(0 );
//                            pane.revalidate ();
//                            pane.repaint();
//                            panel.add ( pane );
//                            ProductTypeList.add ( pane );
//                            panel.revalidate ();
//                            panel.repaint();
                         
                    //}
                }
            }
        }
        public void load_data_prodCust(int cust_ID)
        {
            prod_ids.clear();
            selected_cust_id = cust_ID;
        
         load_Cust_prod = "customermaps?CUSTOMER_ID="+selected_cust_id;
        result_for_cust_prod = WebAPITester.prepareWebCall ( load_Cust_prod , StaticValues.getHeader () , "" );
        
        if( !  result_for_cust_prod.contains( "not found"  )  ){
                    HashMap<String , Object> map = new HashMap<String , Object> ();
                    JSONObject jObject = new JSONObject ( result_for_cust_prod );
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

                        emp = records.getJSONObject ( i );
                        //jComboBox2.addItem ( emp.get ( "" ).toString() +" ( "+emp.get ("CUSTOMER_NAME" ).toString() +" )" );
                        //customerCombo.add ( new String[] { emp.get ( "CUSTOMER_ID" ).toString () , emp.get ( "PLANT_CODE" ).toString() +" ( "+emp.get ("CUSTOMER_NAME" ).toString() +" )" } );
                        prod_ids.add((emp.get("M_FG_ID")).toString());
                    }
            }
        }
}
    

