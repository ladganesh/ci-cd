/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.quotation;

/**
 *
 * @author Rajesh
 */
public class CustomerDR {

    public int CUS_ID;
    public String CUS_NAME;
    public String CUS_CITY;
    public String CUS_ADDRESS;
    public CustomerDR()
    {}
    public CustomerDR(int customerId,String custName,String custCity,String custAddress)
    {
        this.CUS_ID=customerId;
        this.CUS_NAME=custName;
        this.CUS_CITY=custCity;
        this.CUS_ADDRESS=custAddress;
    }
}


