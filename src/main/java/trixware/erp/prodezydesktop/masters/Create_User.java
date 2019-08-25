/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.masters;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;
import trixware.erp.prodezydesktop.Model.EmployeeDR_User;
import trixware.erp.prodezydesktop.Model.StaticValues;

import trixware.erp.prodezydesktop.web_services.WebAPITester;

/**
 *
 * @created by harshali
 */
public class Create_User extends javax.swing.JPanel {

    ArrayList<EmployeeDR_User> employees=null;
    public Create_User() 
    {
        initComponents();
      
        repaint();
        revalidate();
     
    }

   public void loadcontent()
    {
             String addEmpAPICall = "employees";
                String result2 =  WebAPITester.prepareWebCall ( addEmpAPICall, StaticValues.getHeader ()   , "");
                  System.out.println(result2);
                if( !  result2.contains(  "not found"   )  )
                {
                    if ( result2!= null ) 
                    {
                          HashMap<String, Object> map = new HashMap<String, Object>();
                            JSONObject jObject = new JSONObject( result2 );
                            Iterator<?> keys = jObject.keys();

                            while( keys.hasNext() )
                            {
                                String key = (String)keys.next();
                                Object value = jObject.get ( key ) ; 
                                map.put(key, value);
                            }

          
                            JSONObject st = (JSONObject) map.get ( "data" );
                            JSONArray records = st.getJSONArray ( "records");

                            JSONObject emp = null;
                            employees = new ArrayList<EmployeeDR_User> ();
                            for ( int i = 0 ; i < records.length () ; i ++ ) 
                            {
                                  emp = records.getJSONObject ( i);
                                EmployeeDR_User   empdr = new EmployeeDR_User ();
                                empdr.EMP_ID =  emp.getInt("EmployeePK" ) ;
                                empdr.EMP_NAME = emp.getString ( "EMP_NAME" );
                                empdr.DEPART_ID = emp.getInt("DEPARTMENTID");
                                empdr.ROLE_ID = emp.getString("ROLE");
                                employees.add ( empdr );
                                jComboBox1.addItem ( emp.getString ( "EMP_NAME" ) );    
                             }
                }
        
                }    
                
                if(employees.size()==0)
                {
                    btn_create_user.setEnabled(false);
                    JOptionPane.showMessageDialog ( null , "Employee not found to Create user. Please add at least 1 employee " );        
                }
              
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_user = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btn_create_user = new javax.swing.JButton();
        pass = new javax.swing.JPasswordField();
        re_pass = new javax.swing.JPasswordField();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(500, 500));
        setLayout(null);

        jComboBox1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        add(jComboBox1);
        jComboBox1.setBounds(160, 30, 280, 29);

        jLabel1.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel1.setText("Employees");
        jLabel1.setMaximumSize(new java.awt.Dimension(51, 20));
        jLabel1.setMinimumSize(new java.awt.Dimension(51, 20));
        jLabel1.setPreferredSize(new java.awt.Dimension(51, 20));
        add(jLabel1);
        jLabel1.setBounds(30, 30, 118, 29);

        jLabel2.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel2.setText("Username");
        jLabel2.setMaximumSize(new java.awt.Dimension(51, 20));
        jLabel2.setMinimumSize(new java.awt.Dimension(51, 20));
        jLabel2.setPreferredSize(new java.awt.Dimension(51, 20));
        add(jLabel2);
        jLabel2.setBounds(30, 90, 118, 29);

        txt_user.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        txt_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_userActionPerformed(evt);
            }
        });
        add(txt_user);
        txt_user.setBounds(160, 90, 280, 29);

        jLabel3.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel3.setText("Password");
        jLabel3.setMaximumSize(new java.awt.Dimension(51, 20));
        jLabel3.setMinimumSize(new java.awt.Dimension(51, 20));
        jLabel3.setPreferredSize(new java.awt.Dimension(51, 20));
        add(jLabel3);
        jLabel3.setBounds(30, 160, 118, 25);

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        jLabel4.setText("Re-Password");
        jLabel4.setMaximumSize(new java.awt.Dimension(51, 20));
        jLabel4.setMinimumSize(new java.awt.Dimension(51, 20));
        jLabel4.setPreferredSize(new java.awt.Dimension(51, 20));
        add(jLabel4);
        jLabel4.setBounds(30, 230, 118, 30);

        jLabel5.setText("*");
        add(jLabel5);
        jLabel5.setBounds(6, 33, 0, 14);

        btn_create_user.setBackground(new java.awt.Color(255, 255, 255));
        btn_create_user.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        btn_create_user.setText("Submit");
        btn_create_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_create_userActionPerformed(evt);
            }
        });
        add(btn_create_user);
        btn_create_user.setBounds(170, 290, 140, 33);

        pass.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passActionPerformed(evt);
            }
        });
        add(pass);
        pass.setBounds(160, 160, 280, 29);

        re_pass.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        re_pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                re_passActionPerformed(evt);
            }
        });
        add(re_pass);
        re_pass.setBounds(160, 230, 280, 29);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_userActionPerformed

    private void btn_create_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_create_userActionPerformed
        String emp_name = null,user_name,password,re_password;
        String  role=null;
        int department_id=0;
        int Emp_id= employees.get ( jComboBox1.getSelectedIndex () ).EMP_ID ;
        emp_name =  employees.get ( jComboBox1.getSelectedIndex () ).EMP_NAME;
        department_id = employees.get ( jComboBox1.getSelectedIndex () ).DEPART_ID;
        role = employees.get ( jComboBox1.getSelectedIndex () ).ROLE_ID;
        user_name = txt_user.getText();
        password = pass.getText();
        re_password = re_pass.getText();
        
        if( !user_name.contains ( " ")  && !password.contains( " " ) )
           {
              String[] names ;
              String createUserAPICall = null;
               if(  emp_name.contains(" "))
                 {
                  names = emp_name.split(" ");
                    try {
                     createUserAPICall = "useradd?username="+URLEncoder.encode(user_name,"UTF-8")+"&password="+URLEncoder.encode(password,"UTF-8")+"&password_confirmation="+URLEncoder.encode(password,"UTF-8")+"&acc_expiry_option=n&email=example@example.com&firstname="+URLEncoder.encode(names[0],"UTF-8")+"&lastname="+URLEncoder.encode(names[1],"UTF-8")+"&email=example@example.com&role_id="+URLEncoder.encode(role+"","UTF-8")+"&user_type=operator&contactno=&theme=&profile_photo=&department_id=4,3&designation_id="+URLEncoder.encode(department_id+"","UTF-8")+"&acc_expiry_option=&expiry_date=&ref_emp_id="+URLEncoder.encode(""+Emp_id,"UTF-8");                    
                    } catch (UnsupportedEncodingException ex) 
                        {
                         Logger.getLogger(Create_User.class.getName()).log(Level.SEVERE, null, ex);
                        }
                  }else
                   {
                    try {
                      createUserAPICall = "useradd?username="+URLEncoder.encode(user_name,"UTF-8")+"&password="+URLEncoder.encode(password,"UTF-8")+"&password_confirmation="+URLEncoder.encode(password,"UTF-8")+"&acc_expiry_option=n&email=example@example.com&firstname="+URLEncoder.encode(emp_name,"UTF-8")+"&lastname="+URLEncoder.encode(emp_name,"UTF-8")+"&email=example@example.com&role_id="+URLEncoder.encode(role+"","UTF-8")+"&user_type=operator&contactno=&theme=&profile_photo=&department_id=4,3&designation_id="+URLEncoder.encode(department_id+"","UTF-8")+"&acc_expiry_option=&expiry_date=&ref_emp_id="+URLEncoder.encode(""+Emp_id,"UTF-8");
                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(Create_User.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                              
                       String  result2 =  WebAPITester.prepareWebCall ( createUserAPICall , StaticValues.getHeader ()   , "");
                           System.out.println ( "" + result2 );  
                         JOptionPane.showMessageDialog ( null , result2);
                         
                        
                      }else{
                                JOptionPane.showMessageDialog ( null , "Please enter appropriate username and password " );        
                            }
                   txt_user.setText ( "" );
                         pass.setText ( "" );
                         re_pass.setText("");
                         jComboBox1.setSelectedItem ( 0 );
        
        
    }//GEN-LAST:event_btn_create_userActionPerformed

    private void re_passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_re_passActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_re_passActionPerformed

    private void passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_create_user;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPasswordField pass;
    private javax.swing.JPasswordField re_pass;
    private javax.swing.JTextField txt_user;
    // End of variables declaration//GEN-END:variables
}
