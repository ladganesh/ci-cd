/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

/**
 *
 * @author Rajesh
 */
public class AlarmsEntity{
    int index;
    String msg ;

    public AlarmsEntity ( int _index, String _msg) {
            index = _index;
            msg  = _msg;      
    }
    
    public String showMsg(){
        
        return  " "+msg ;
        
    }
    
}