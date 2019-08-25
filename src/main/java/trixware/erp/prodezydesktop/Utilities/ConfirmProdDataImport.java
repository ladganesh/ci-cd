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
import trixware.erp.prodezydesktop.Model.Proman_User;
import static trixware.erp.prodezydesktop.examples.HomeScreen.height;
import static trixware.erp.prodezydesktop.examples.HomeScreen.width;
import trixware.erp.prodezydesktop.masters.BillOfMaterialUI;
import trixware.erp.prodezydesktop.examples.HomeScreen3;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
 
public class ConfirmProdDataImport extends JDialog {
 
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean succeeded;
 
    ProdDataConformationScreen confirm ;
    
    public ConfirmProdDataImport(Frame parent) {
        super(parent, "Login", true);
        
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ();
                GraphicsDevice[] gs = ge.getScreenDevices ();
                for ( int i = 0 ; i < gs.length ; i ++ ) {
                    DisplayMode dm = gs[ i ].getDisplayMode ();
                    if ( width > dm.getWidth () || width == 0 ) {
                        width = dm.getWidth ();
                        height = dm.getHeight ();
                    }
                }
       
        confirm = new ProdDataConformationScreen(  );
        confirm.setBounds(  0    , 0, 1150,  500);
                
        getContentPane().add( confirm  , BorderLayout.CENTER);
        
        pack();
        setResizable(true);
        setBounds(  0    , 0,  1150,500 );
            
        setLocationRelativeTo(parent);
        

        
      //  this.getRootPane().setDefaultButton(btnLogin);
    }
    
    public void closeBOMWindow(){
         dispose();
    }
}