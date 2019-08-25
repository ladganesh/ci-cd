/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

/**
 *
 * @created by harshali for create user form
 */
public class EmployeeDR_User {

    public EmployeeDR_User ( int EMP_ID , String EMP_NAME ,int department_id,String role) 
    {
        this.EMP_ID = EMP_ID;
        this.EMP_NAME = EMP_NAME;
        this.DEPART_ID = department_id;
        this.ROLE_ID = role;
    }

    public EmployeeDR_User (  ) {

    }
    
    public int EMP_ID,DEPART_ID;
    public String EMP_NAME,ROLE_ID;
}

