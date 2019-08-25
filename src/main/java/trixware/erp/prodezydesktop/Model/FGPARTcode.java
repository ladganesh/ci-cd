/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

/**
 *
 * @author dell
 */
public class FGPARTcode {

    public FGPARTcode() {
    }
    
    public FGPARTcode( int BOM_ID,int FG_ID,int RM_ID,int NT_WT)
    {
        this.BOM_ID =BOM_ID;
        this.FG_ID=FG_ID;
        this.RM_ID=RM_ID;
        this.NT_WT=NT_WT;
    }
    
    public int BOM_ID;
    public int FG_ID;
    public int RM_ID;
    public int NT_WT;
}
