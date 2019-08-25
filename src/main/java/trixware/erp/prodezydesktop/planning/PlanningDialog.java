/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.planning;


import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JDialog;
import org.json.JSONObject;

/**
 *
 * @author Rajesh
 */
public class PlanningDialog extends JDialog{

    PlanningPanel progress = null ;
    
    public PlanningDialog ( JFrame parent  ) {
        super(parent, "Please wait...", true);
        
        
        
        progress = new PlanningPanel () ;
        getContentPane().add( progress  , BorderLayout.CENTER);
        //setUndecorated ( true );
        //pack();
        setResizable(false);
        setBounds(  0    , 0,  1010,550 );
        setLocationRelativeTo(parent);
        //setDefaultCloseOperation ( JDialog.DO_NOTHING_ON_CLOSE);
    }
    
        public PlanningDialog ( JFrame parent  ,  JSONObject planningData) {
        super(parent, "Please wait...", true);
                
        progress = new PlanningPanel ( planningData ) ;
        getContentPane().add( progress  , BorderLayout.CENTER);
        //setUndecorated ( true );
        //pack();
        setResizable(false);
        setBounds(  0    , 0,  1010,550 );
        setLocationRelativeTo(parent);
        //setDefaultCloseOperation ( JDialog.DO_NOTHING_ON_CLOSE);
    }
 
    public void closeProgressWindow(){
         dispose();
    }
    
    public void updateTitle(String _title){
        progress.repaint ();
    }
    
    public void updateMessage( String _message ){
        progress.repaint ();
    }    
    
    public void openProgressWindow(){
        setVisible ( true );
    }
    
}

