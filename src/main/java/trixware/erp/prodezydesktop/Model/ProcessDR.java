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
public class ProcessDR {

    public ProcessDR ( int PRC_ID , String PRC_NAME ) {
        this.PRC_ID = PRC_ID;
        this.PRC_NAME = PRC_NAME;
    }
    
    public ProcessDR ( int PRC_ID , String PRC_NAME , int _machineType) {
        this.PRC_ID = PRC_ID;
        this.PRC_NAME = PRC_NAME;
        this.machine_type = _machineType ;
    }

    public ProcessDR (  ) {
        
    }
    
    public int PRC_ID;
    public String PRC_NAME;
    public int machine_type ;
}