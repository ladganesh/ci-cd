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
public class EmployeeDR {

    public EmployeeDR ( int EMP_ID , String EMP_NAME ) {
        this.EMP_ID = EMP_ID;
        this.EMP_NAME = EMP_NAME;
    }

    public EmployeeDR (  ) {

    }
    
    public int EMP_ID;
    public String EMP_NAME;
}

