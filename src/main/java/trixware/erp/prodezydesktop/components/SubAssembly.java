/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.components;

/**
 *
 * @author Harshu
 * 
 * created by harshali for panel of child parts
 */
public class SubAssembly extends javax.swing.JPanel 
{

    int record_id;
        int id , pieces ;
    String sub_parts;

    public int getRecord_id() {
        return Integer.parseInt(jLabel_record_id.getText());
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
        jLabel_record_id.setText(record_id+"");
    }

    public int getId() {
        return Integer.parseInt(jLabel_id.getText());
    }

    public void setId(int id) {
        this.id = id;
        jLabel_id.setText(""+id);
    }

    public int getPieces() 
    {
        return Integer.parseInt(jTextField_nos.getText());
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
        jTextField_nos.setText(pieces+"");
    }

    public String getSub_parts() {
        return jLabel_child.getText();
    }

    public void setSub_parts(String sub_parts) {
        this.sub_parts = sub_parts;
        jLabel_child.setText(sub_parts);
    }
    /**
     * Creates new form SubAssembly
     */
    public SubAssembly() {
        initComponents();
        jLabel_id.setVisible(false);
        jLabel_record_id.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel_child = new javax.swing.JLabel();
        jTextField_nos = new javax.swing.JTextField();
        jLabel_id = new javax.swing.JLabel();
        jLabel_record_id = new javax.swing.JLabel();

        jLabel_child.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel_child.setText("jLabel1");

        jTextField_nos.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jTextField_nos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_nosActionPerformed(evt);
            }
        });

        jLabel_record_id.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_id, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel_child, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel_record_id)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jTextField_nos, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel_child, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField_nos, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel_record_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_nosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_nosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_nosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel_child;
    private javax.swing.JLabel jLabel_id;
    private javax.swing.JLabel jLabel_record_id;
    private javax.swing.JTextField jTextField_nos;
    // End of variables declaration//GEN-END:variables
}
