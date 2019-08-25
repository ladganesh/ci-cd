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
import java.util.ArrayList;
import javax.swing.*;
import trixware.erp.prodezydesktop.Model.TimeLossModel;
 
public class ShowTimeLossForm_old extends JDialog {
 
    
 
   private TimeLossList_old timeLoss ;
    
    public ShowTimeLossForm_old(Frame parent) {
        super(parent, "Login", true);
        
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        timeLoss = new TimeLossList_old(  );
        timeLoss.setBounds(  0    , 0, 400,  380);
                
        getContentPane().add( timeLoss  , BorderLayout.CENTER);
        
        pack();
        setResizable(false);
        setBounds(  0    , 0,  400,  380 );
            
        setLocationRelativeTo(parent);
        
      //  this.getRootPane().setDefaultButton(btnLogin);
    }
    
    public ArrayList<TimeLossModel> getTImeLossList(){
        
        return timeLoss.getTimeLossList ();
    }
    
    public void closeBOMWindow(){
         dispose();
    }
}