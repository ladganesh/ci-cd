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
public class DieDR {
    
    public int DIE_ID;
    public String die_tool_no;
    public float cost_maintain;

    public DieDR() {
    }

    public DieDR(int DIE_ID, String die_tool_no,float cost_maintain) {
        this.DIE_ID = DIE_ID;
        this.die_tool_no = die_tool_no;
        this.cost_maintain=cost_maintain;
    }
    
    
}
