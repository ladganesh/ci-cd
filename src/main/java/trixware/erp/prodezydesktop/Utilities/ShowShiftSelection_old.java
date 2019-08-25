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
 
public class ShowShiftSelection_old extends JDialog {
 
    ShiftsList_old confirm ;
    
    public ShowShiftSelection_old(Frame parent) {
        super(parent, "Login", true);
        
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        confirm = new ShiftsList_old(  );
        confirm.setBounds(  0    , 0, 200,  225);
                
        getContentPane().add( confirm  , BorderLayout.CENTER);
        
        pack();
        setResizable(true);
        setBounds(  0    , 0,  200,225 );
            
        setLocationRelativeTo(parent);
        
        
        
      //  this.getRootPane().setDefaultButton(btnLogin);
    }
    
    public int getSelectedShift(){
        return confirm.getSelectedShiftId ();
    }
    
    public void closeBOMWindow(){
         dispose();
    }
}