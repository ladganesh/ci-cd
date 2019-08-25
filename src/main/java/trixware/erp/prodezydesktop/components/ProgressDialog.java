/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.components;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JDialog;

/**
 *
 * @author Rajesh
 */
public class ProgressDialog extends JDialog{

    ProgressPanel progress = null ;
    
    public ProgressDialog ( JFrame parent  ) {
        super(parent, "Please wait...", true);
        
        
        
        progress = new ProgressPanel () ;
        getContentPane().add( progress  , BorderLayout.CENTER);
        //setUndecorated ( true );
       // pack();
        setResizable(false);
        setBounds(  0    , 0,  400,150 );
        setLocationRelativeTo(parent);
    //    setDefaultCloseOperation ( JDialog.DO_NOTHING_ON_CLOSE);
    }
 
    public void closeProgressWindow(){
         dispose();
    }
    
    public void updateTitle(String _title){
        progress.setTitle ( _title );
        progress.repaint ();
    }
    
    public void updateMessage( String _message ){
        progress.setMessage (  _message );
        progress.repaint ();
    }    
    
    public void openProgressWindow(){
        setVisible ( true );
    }
    
}

