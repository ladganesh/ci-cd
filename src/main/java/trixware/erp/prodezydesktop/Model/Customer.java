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
public class Customer {

    public Customer ( int CUST_ID , String CUST_NAME ) {
        this.CUST_ID = CUST_ID;
        this.CUST_NAME = CUST_NAME;
    }
    
    public Customer (  ) {
    }  
    
    public int CUST_ID ; 
    
    public String CUST_NAME ;
    
}
