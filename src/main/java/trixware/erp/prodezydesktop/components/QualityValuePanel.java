/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 *
 * @author dell
 */
public class QualityValuePanel extends javax.swing.JPanel 
{
    private int id;
    private String param_name;
    private float value;

    public float getValue() {
        return Float.parseFloat(jTextFieldValue.getText());
    }

    public void setValue(float value) 
    {
        this.value = value;
        jTextFieldValue.setText(""+value);
    }

    public int getId() 
    {
        return Integer.parseInt(JlabelId.getText());
    }

    public void setId(int id) 
    {
        this.id = id;
        JlabelId.setText(""+id);
        
    }

    public String getParam_name() 
    {
        return jLabelName.getText();
    }

    public void setParam_name(String param_name) 
    {
        this.param_name = param_name;
        jLabelName.setText(param_name);
    }

    /**
     * Creates new form QualityValuePanel
     */
    public QualityValuePanel() {
        initComponents();
        jTextFieldValue.addFocusListener(f2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JlabelId = new javax.swing.JLabel();
        jLabelName = new javax.swing.JLabel();
        jTextFieldValue = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(260, 40));

        JlabelId.setText("L0");
        JlabelId.setPreferredSize(new java.awt.Dimension(11, 0));

        jLabelName.setText("jLabel2");

        jTextFieldValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldValueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JlabelId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabelName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addComponent(jTextFieldValue, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlabelId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelName)
                    .addComponent(jTextFieldValue, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldValueActionPerformed
       FocusListener f2 = new FocusListener () 
    {
        @Override
        public void focusGained ( FocusEvent e ) {
        //            throw new UnsupportedOperationException ( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
            JTextField jcb = ( JTextField ) e.getSource ();    
            jcb.setText("");
        }

        @Override
        public void focusLost ( FocusEvent e ) {
           JTextField jcb = ( JTextField ) e.getSource ();    
            String x = jcb.getText().trim ();
            
            if(x.equalsIgnoreCase ( ""))
            {
                x = "0.0";
            }
            
            try{    
                int num  = Integer.parseInt( String.valueOf( jcb.getText().toString() ) );
                    if( num<0  ){
                     jcb.setText(x);
                     
               
                }
            }catch(NumberFormatException ex1){
                    jcb.setText(x);
//                    StaticValues.writer.writeExcel (MaintenanceTypePanel.class.getSimpleName () , MaintenanceTypePanel.class.getSimpleName () , ex1.getClass ().toString () , Thread.currentThread ().getStackTrace ()[ 1 ].getLineNumber () + "" , ex1.getMessage () , StaticValues.sdf2.format ( Calendar.getInstance ().getTime () ) );            
            }
        }
    };

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JlabelId;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JTextField jTextFieldValue;
    // End of variables declaration//GEN-END:variables
}
