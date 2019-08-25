/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Utilities;

/**
 *
 * @author Rajesh
 */
import java.awt.*;
import javax.swing.*;
import trixware.erp.prodezydesktop.masters.BillOfMaterialUI;
 
public class UpdateBOMDialog extends JDialog {
 
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean succeeded;
 
    BillOfMaterialUI supplier ;
    
    public UpdateBOMDialog(Frame parent, int FG_ID) {
        super(parent, "Login", true);
        
       
        supplier = new BillOfMaterialUI( FG_ID );
        supplier.setBounds(0, 0, 420,500);
 
        getContentPane().add( supplier  , BorderLayout.CENTER);
        
        pack();
        setResizable(true);
        setBounds(0, 0, 420 , 500);
        
        setLocationRelativeTo(parent);
        
        
        
      //  this.getRootPane().setDefaultButton(btnLogin);
    }
    
    public void closeBOMWindow(){
         dispose();
    }
}